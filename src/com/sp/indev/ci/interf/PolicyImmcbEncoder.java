package com.sp.indiv.ci.interf;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.blsvr.BLCIInsureDemand;
import com.sp.indiv.ci.dbsvr.DBCIInsureDemand;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPrpCTrafficDetail;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpCTrafficDetailSchema;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;
import com.sp.sysframework.common.util.PlatformUtils;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 发送直接XX请求数据的编码器
 *
 */
public class PolicyImmcbEncoder {

	Log logger = LogFactory.getLog(getClass());
	/**
	 * 编码
	 *r
	 * @return 直接XX请求XML格式串
	 * @throws Exception
	 */
	public String encode(DbPool dbPool, BLPolicy blpolicy)
		throws Exception 
	{
		
		StringBuffer buf = new StringBuffer(4000);
		
		addXMLHead(buf);
		addPacket(buf, blpolicy);
		return buf.toString();
	}

	/**
	 * 添加XML文件头
	 *
	 * @param buf
	 *            StringBuffer
	 */
	protected void addXMLHead(StringBuffer buf) throws Exception
	{
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
	}

	/**
	 * 添加PACKET节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addPacket(StringBuffer buf, BLPolicy blPolicy)
			throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
		addHead(buf, blPolicy);
		addBody(buf, blPolicy);
		buf.append("</PACKET>");

	}

	/**
	 * 添加HEAD节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addHead(StringBuffer buf, BLPolicy blPolicy) 
		throws Exception 
	{
		String strComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();

		buf.append("<HEAD>");
		addNode(buf, "REQUEST_TYPE", "08");
		addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_USER"));
        addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_PASSWORD"));
        buf.append("</HEAD>");
	}

	/**
	 * 添加BODY节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBody(StringBuffer buf, BLPolicy blPolicy)
		throws Exception 
	{
		buf.append("<BODY>");
		addBasePart(buf, blPolicy);
		buf.append("</BODY>");
	}

	/**
	 * 添加BASE_PART节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBasePart(StringBuffer buf, BLPolicy blPolicy)
		throws Exception
	{	
		String querySequenceNo   = "";         
		String policyNo          = "";         
		String salesChannel      = "";         
		String sumPremium        = "";         
		String startdate         = "";         
		String enddate           = "";         
		
		
		String strStartHour		= ""; 			
        String strEndHour		= ""; 			
        
        if(strStartHour.length() != 2)
        {
        	if(strStartHour.equals("0") || strStartHour.equals(""))
        	{
        		strStartHour = "00";
        	}
        	else
        	{
        		strStartHour = "0" + strStartHour;
        	}
        }
        if(strEndHour.length() != 2)
        {
        	if(strEndHour.equals("0") || strEndHour.equals(""))
        	{
        		strEndHour = "00";
        	}
        	else
        	{
        		strEndHour = "0" + strEndHour;
        	}
        }
		
		
		policyNo = blPolicy.getBLPrpCmain().getArr(0).getPolicyNo();
		startdate = blPolicy.getBLPrpCmain().getArr(0).getStartDate();
		enddate   = blPolicy.getBLPrpCmain().getArr(0).getEndDate();
		startdate = correctDate(startdate);
		enddate   = correctDate(enddate);
		salesChannel = encodeSalesChannel(blPolicy);
		sumPremium=blPolicy.getBLPrpCmain().getArr(0).getSumPremium();

        String strCarMark 			= ""; 
        String strVehicleType 		= ""; 
        String strVehicleCategory 	= ""; 
        String strUseType 			= ""; 
        String strEngineNo 			= ""; 
        String strRackNo 			= ""; 
        String strUseAges 			= ""; 
        String strMileages 			= ""; 
        String strNewVehicleFlag 	= ""; 
        String strEcdemicVehicleFlag = ""; 
        String strMadeFactory 		= ""; 
        String strVehicleBrand 		= ""; 
        String strVehicleModel 		= ""; 
        String strDriverNum 		= ""; 
        String strComCode 			= ""; 
        
        String strBillDate			= "";	
        String strSpecialCarFlag    = "";   
        
       
        strCarMark = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseNo();
        if("新车".equals(strCarMark.trim()) || 
           "".equals(strCarMark.trim()))
        {
        	 strCarMark = "新车";
        }
        strUseType = encoderUseType(blPolicy);
        strVehicleCategory = encoderVehicleCategory(blPolicy);
        strEngineNo = blPolicy.getBLPrpCitemCar().getArr(0).getEngineNo();
        strUseAges = blPolicy.getBLPrpCitemCar().getArr(0).getUseYears();
        strMileages = "" + ((int) Double.parseDouble(blPolicy.getBLPrpCitemCar().getArr(0).getRunMiles()));
        strVehicleBrand = blPolicy.getBLPrpCitemCar().getArr(0).getBrandName();
        strVehicleModel = blPolicy.getBLPrpCitemCar().getArr(0).getModelCode();
        strVehicleType = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseKindCode();
        strDriverNum = "0";
        strRackNo = blPolicy.getBLPrpCitemCar().getArr(0).getFrameNo();

        
        
        if (SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+strCarMark.trim()+",") > -1) 
        
        {
            strNewVehicleFlag = "1";
        } 
        else 
        {
            strNewVehicleFlag = "0";
        }
        
        strComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();
        if(strComCode.substring(0,2).equals("07"))
        {
           if("沪".equals(strCarMark.trim().substring(0,1)) ||
        	  "新".equals(strCarMark.trim().substring(0,1))) 
           {
              strEcdemicVehicleFlag = "0";
           } 
           else 
           {
              strEcdemicVehicleFlag = "1";
           }
        }
        
        if(strComCode.substring(0,2).equals("01"))
        {
           if("京".equals(strCarMark.substring(0,1)) || 
        	  "新".equals(strCarMark.trim().substring(0,1))) 
           {
              strEcdemicVehicleFlag = "0";
           } 
           else 
           {
              strEcdemicVehicleFlag = "1";
           }
        }
         
        if(strComCode.substring(0,2).equals("19"))
        {
           if("浙".equals(strCarMark.substring(0,1)) ||  
        	  "新".equals(strCarMark.trim().substring(0,1)))
           {
              strEcdemicVehicleFlag = "0";
           } 
           else 
           {
              strEcdemicVehicleFlag = "1";
           }
        }
        if(strComCode.substring(0,2).equals("03"))
        {
           if("苏".equals(strCarMark.substring(0,1)) || 
        	  "新".equals(strCarMark.trim().substring(0,1)))
           {
              strEcdemicVehicleFlag = "0";
           } 
           else 
           {
              strEcdemicVehicleFlag = "1";
           }
        }
        
        if(strComCode.substring(0,2).equals("18"))
        {
           if("湘".equals(strCarMark.substring(0,1)) || 
        	  "新".equals(strCarMark.trim().substring(0,1)))
           {
              strEcdemicVehicleFlag = "0";
           } 
           else 
           {
              strEcdemicVehicleFlag = "1";
           }
        }
        if( strComCode.substring(0,2).equals("31") )
             {
                if("辽B".equals(strCarMark.substring(0,1)) ||
             	  "新".equals(strCarMark.trim().substring(0,1)))
                {
                   strEcdemicVehicleFlag = "0";
                } 
                else 
                {
                   strEcdemicVehicleFlag = "1";
                }
             }
        if(strComCode.substring(0,2).equals("29"))
             {
                if("浙B".equals(strCarMark.substring(0,1)) || 
             	  "新".equals(strCarMark.trim().substring(0,1)))
                {
                   strEcdemicVehicleFlag = "0";
                } 
                else 
                {
                   strEcdemicVehicleFlag = "1";
                }
             }
        
        
        
        
        if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+strCarMark.trim()+",") > -1){
        
        	strCarMark = "";
        }
        
        
        if(strComCode.substring(0,2).equals("01") ||
           strComCode.substring(0,2).equals("01"))
        {
        	
        }
        else
        {
        	strBillDate = correctDate(blPolicy.getBLPrpCmain().getArr(0).getOperateDate()) + "00";
        	
        	if(blPolicy.getBLPrpCitemCarExt().getArr(0) != null)
        	{
        		if(blPolicy.getBLPrpCitemCarExt().getArr(0).getNoneFluctuateFlag() == null)
        		{
        			strSpecialCarFlag = "";
        		}
        		else
        		{
        			strSpecialCarFlag = blPolicy.getBLPrpCitemCarExt().getArr(0).getNoneFluctuateFlag();
        			if(strSpecialCarFlag.trim().equals(""))
            		{
        				strSpecialCarFlag = "";
            		}
        			
                	if(strSpecialCarFlag.trim().equals("N1") ||
                	   strSpecialCarFlag.trim().equals("N2") || 
                	   strSpecialCarFlag.trim().equals("N3") ||
                	   strSpecialCarFlag.trim().equals("N6") ||
                	   strSpecialCarFlag.trim().equals("N7") ||
                	   strSpecialCarFlag.trim().equals("N8"))
                	{
                		strSpecialCarFlag = "";
                	}
                	
                	else if(strSpecialCarFlag.trim().equals("N4"))
                	{
                		strSpecialCarFlag = "1";
                	}
                	
                	else if(strSpecialCarFlag.trim().equals("N5"))
                	{
                		strSpecialCarFlag = "2";
                	}
        		}
        	}
        }
        
        
        
        
        logger.info("=====上海XXXXX强三平台测试=使用性质：" + strUseType);
        logger.info("=====上海XXXXX强三平台测试=车辆识别代号：" + strRackNo);
        logger.info("=====上海XXXXX强三平台测试=车辆种类代码：" + strVehicleCategory);
        logger.info("=====上海XXXXX强三平台测试=新车标志为 (1)，旧车为(0)：" + strNewVehicleFlag);
        logger.info("=====上海XXXXX强三平台测试=本地车标志为(0),外地车为(1)：" + strEcdemicVehicleFlag);
        logger.info("=====上海XXXXX强三平台测试=车牌号：" + strCarMark);
        

		buf.append("<BASE_PART>");
		addNode(buf, "CONFIRM_SEQUENCE_NO", querySequenceNo);
		addNode(buf, "POLICY_NO", policyNo);
		addNode(buf, "TP_MARK", "");
		addNode(buf, "SALES_CHANNEL", salesChannel);
		
		
        if(strComCode.substring(0,2).equals("01") ||
           strComCode.substring(0,2).equals("07"))
        {
        	
        	addNode(buf, "BILL_DATE", "");
        }
        else
        {
        	addNode(buf, "BILL_DATE", strBillDate);
        	addNode(buf, "SPECIAL_CAR_FLAG", strSpecialCarFlag);
        }
        
		
		addNode(buf, "ASSUMPTION", "");
		addNode(buf, "AGENT", "");
		addNode(buf, "OPERATOR", "");
		addNode(buf, "CURRENCY", "");
		
        if(strComCode.substring(0,2).equals("01") || 
           strComCode.substring(0,2).equals("07"))
        {
        	addNode(buf, "START_DATE", startdate);
        	addNode(buf, "END_DATE", enddate);
        }
        else 
        {
        	
        	addNode(buf, "START_DATE", (startdate + strStartHour));
        	addNode(buf, "END_DATE", (enddate + strEndHour));
        }
		
        
		addNode(buf, "LIMIT_AMOUNT", "");
		addNode(buf, "ACTUAL_PREMIUM", sumPremium);
		addNode(buf, "REASON","9");
		addNode(buf, "CHANGE_REASON_CODE","");
		addNode(buf, "CHANGE_RENSON_DESC","");
		
		String claimAdjustReason    = "";   
		BLPrpCTrafficDetail blprpctrafficdetail = new BLPrpCTrafficDetail();
    	PrpCTrafficDetailSchema prpctrafficdetailschema = new PrpCTrafficDetailSchema();
		for(int j = 0; j < blprpctrafficdetail.getSize(); j++) {
    		prpctrafficdetailschema = blprpctrafficdetail.getArr(j);
    		if("1".equals(prpctrafficdetailschema.getTrafficType())) {
    	    	claimAdjustReason = prpctrafficdetailschema.getTrafficCode();
    	    	if(claimAdjustReason.equals("A0"))
    	    		claimAdjustReason = "";
    		}
    		else if("2".equals(prpctrafficdetailschema.getTrafficType())) {
    			claimAdjustReason = "";
    		}
    		else {
            	claimAdjustReason = "";
    		}
    	}
		
		addNode(buf, "PECCANCY_ADJUST_VALUE","0.00");
		addNode(buf, "CLAIM_ADJUST_VALUE",new DecimalFormat("0.00").format(Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCitemCarExt().getArr(0).getDamFloatRatioCI()))));
		addNode(buf, "DRIVER_RATE","");
		addNode(buf, "DISTRICT_RATE","");
		addNode(buf, "ADDITION_RATE","");
		addNode(buf, "PECCANCY_ADJUST_REASON","");
		addNode(buf, "CLAIM_ADJUST_REASON",claimAdjustReason);
		addNode(buf, "DRIVER_RATE_REASON","");
		addNode(buf, "DISTRICT_RATE_REASON","");
		addNode(buf, "RATE_FLOAT_FLAG",getNoneFluctuateFlag(blPolicy.getBLPrpCitemCarExt().getArr(0).getNoneFluctuateFlag()));
		
		addNode(buf, "DISTRICT_CODE", "");
		addNode(buf, "CAR_MARK", strCarMark);
		addNode(buf, "VEHICLE_TYPE", strVehicleType);
		addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);
		addNode(buf, "USE_TYPE", strUseType);
		addNode(buf, "ENGINE_NO", strEngineNo);
    	addNode(buf, "RACK_NO", strRackNo);
    	addNode(buf, "USE_AGES", strUseAges);
    	addNode(buf, "MILEAGES", strMileages);
    	addNode(buf, "NEW_VEHICLE_FLAG", strNewVehicleFlag);
    	addNode(buf, "ECDEMIC_VEHICLE_FLAG", strEcdemicVehicleFlag);
    	addNode(buf, "MADE_FACTORY", strMadeFactory);
    	addNode(buf, "VEHICLE_BRAND", strVehicleBrand);
    	addNode(buf, "VEHICLE_MODEL", strVehicleModel);
    	addNode(buf, "DRIVER_NUM", strDriverNum);
		buf.append("</BASE_PART>");
		
		buf.append("<DRIVER_LIST>");
		buf.append("<DRIVER>");
		addNode(buf,"LICENSE_NO","");
		addNode(buf,"CERTI_TYPE","");
		addNode(buf,"IS_MASTER","");
		addNode(buf,"GENDER","");
		addNode(buf,"DRIVER_PERIOD","");
		addNode(buf,"AGE","");
		addNode(buf,"DRIVER_TYPE","");
		buf.append("</DRIVER>");
		buf.append("</DRIVER_LIST>");
		
		buf.append("<OWNER_LIST>");
		buf.append("<OWNER_DATA>");
		addNode(buf,"OWNER_NAME","");
		addNode(buf,"OWNER_TYPE","");
		addNode(buf,"CERTI_TYPE","");
		addNode(buf,"CERTI_CODE","");
		addNode(buf,"ADDRESS","");
		addNode(buf,"MAIL_ADDRESS","");
		addNode(buf,"ZIP","");
		addNode(buf,"TELEPHONE","");
		addNode(buf,"COMPANY_TYPE","");
		buf.append("</OWNER_DATA>");
		buf.append("</OWNER_LIST>");
		
		buf.append("<PH_LIST>");
		buf.append("<PH_DATA>");
		addNode(buf,"POLICY_HOLDER","");
		addNode(buf,"PH_TYPE","");
		addNode(buf,"CERTI_TYPE","");
		addNode(buf,"CERTI_CODE","");
		addNode(buf,"ADDRESS","");
		addNode(buf,"MAIL_ADDRESS","");
		addNode(buf,"ZIP","");
		addNode(buf,"TELEPHONE","");
		addNode(buf,"COMPANY_TYPE","");
		buf.append("</PH_DATA>");
		buf.append("</PH_LIST>");
		
		buf.append("<INSURED_LIST>");
		buf.append("<INSURED_DATA>");
		addNode(buf,"INSURED_NAME","");
		addNode(buf,"INSURED_TYPE","");
		addNode(buf,"CERTI_TYPE","");
		addNode(buf,"CERTI_CODE","");
		addNode(buf,"ADDRESS","");
		addNode(buf,"MAIL_ADDRESS","");
		addNode(buf,"ZIP","");
		addNode(buf,"TELEPHONE","");
		addNode(buf,"COMPANY_TYPE","");
		buf.append("</INSURED_DATA>");
		buf.append("</INSURED_LIST>");
		
		buf.append("<BUSI_COVER_LIST>");
		buf.append("<BUSI_COVER_DATA>");
		addNode(buf,"COVERAGE_CODE","");
		addNode(buf,"POLICY_NO","");
		addNode(buf,"START_DATE","");
		addNode(buf,"END_DATE","");
		addNode(buf,"LIMIT_AMOUNT","");
		addNode(buf,"PREMIUM","");
		buf.append("</BUSI_COVER_DATA>");
		buf.append("</BUSI_COVER_LIST>");
	}

	public static void addNode(StringBuffer buffer, String name, String value) {
		value = StringUtils.replace(value, "<", "&lt;");
		value = StringUtils.replace(value, ">", "&gt;");
		value = StringUtils.replace(value, "&", "&amp;");
		value = StringUtils.replace(value, "'", "&apos;");
		value = StringUtils.replace(value, "\"", "&quot;");

		buffer.append("<");
		buffer.append(name);
		buffer.append(">");
		buffer.append(value);
		buffer.append("</");
		buffer.append(name);
		buffer.append(">");
		buffer.append("\r\n");
	}

	
	private String encodeSalesChannel(BLPolicy blPolicy) 
		throws Exception 
	{  
		String salesChannel = ""; 
        
		
		
		
		String businessNature = "";
		businessNature = blPolicy.getBLPrpCmain().getArr(0)
				.getBusinessNature();
		if("0".equals(businessNature)){
			  salesChannel = "10";
		}else if("1".equals(businessNature) || "I".equals(businessNature)){
          salesChannel = "22";
		}else if("2".equals(businessNature)){
		  salesChannel = "25";
		}else if("3".equals(businessNature)){
		  salesChannel = "23";
		}else if("4".equals(businessNature)){
	      salesChannel = "26";
		}else if("5".equals(businessNature)){
	      salesChannel = "26";
		}else if("6".equals(businessNature)||"J".equals(businessNature)){
		  salesChannel = "11";
		}else if("7".equals(businessNature)){
		  salesChannel = "10";
		}else if("8".equals(businessNature)){
		  salesChannel = "12";
		}else if("9".equals(businessNature)){
		  salesChannel = "99";
		}else if("A".equals(businessNature)){
		  salesChannel = "23";
		}else if("B".equals(businessNature)){
		  salesChannel = "23";
		}else if("D".equals(businessNature)){
		  salesChannel = "10";
		}else if("E".equals(businessNature)){
	      salesChannel = "10";
		}else if("G".equals(businessNature)){
		  salesChannel = "21";
		}else if("H".equals(businessNature)){
	      salesChannel = "21";
		}else if("F".equals(businessNature)){
		  salesChannel = "24";
	   	}
		
		
		
		
		/*
		switch (businessNature) 
		{
		case 0: {
			salesChannel = "10";
			break;
		}
		case 1: {
			salesChannel = "22";
			break;
		}
		case 2: {
			salesChannel = "25";
			break;
		}
		case 3: {
			 salesChannel = "23";
			break;
		}
		case 4: {
			 salesChannel = "26";
			break;
		}
		case 5: {
			 salesChannel = "26";
			break;
		}
		case 6: {
			salesChannel = "11";
			break;
		}
		case 7: {
			 salesChannel = "10";
			break;
		}
		case 8: {
			salesChannel = "12";
			break;
		}
		case 9: {
			salesChannel = "99";
			break;
		}
		}
		*/
		return salesChannel;
	}

	/**
	 * 获得XX查询主信息
	 * @param blPolicy
	 * @throws Exception
	 */
	private void getCIInsureDemand(DbPool dbPool, BLPolicy blPolicy) throws Exception {
		try {
			
			
							  
			
			
			BLCIInsureDemand blciInsureDemand = new BLCIInsureDemand();
			
			String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
			if(!"1".equals(strSwitch)){
			    blciInsureDemand.getData(dbPool, blPolicy.getBLPrpCmain().getArr(0).getProposalNo());
			}else{
				if (blPolicy.getBLPrpCitemCar().getSize() > 0) {
					
					ArrayList iWhereValue=new ArrayList();
					iWhereValue.add(blPolicy.getBLPrpCitemCar().getArr(0).getDemandNo());
					blciInsureDemand.query(" DemandNo = ?",iWhereValue);
					
				}
			}
			
			CIInsureDemandSchema cIInsureDemandSchema = null;
			if(blciInsureDemand.getSize()>0){
				cIInsureDemandSchema = blciInsureDemand.getArr(0);
				BLCIInsureDemand blCIInsureDemand = new BLCIInsureDemand();
				blCIInsureDemand.setArr(cIInsureDemandSchema);
				blPolicy.setBLCIInsureDemand(blCIInsureDemand);
			}
			
		} 
		catch (Exception ex) 
		{
			throw ex;
		}
	}

   
    private String encoderUseType(BLPolicy blPolicy) throws Exception {
        String strUseType = "";
        String intUseType = "";
        intUseType = blPolicy.getBLPrpCitemCar().getArr(0).getUseNatureCode();
        String strCarKindCode = strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
        
        if(strCarKindCode.equals("A0"))
        {
        	if(intUseType.trim().equals("8B"))   		
        	{
	            strUseType = "220";
	    	}
	    	else if(intUseType.trim().equals("8C"))	 	
	    	{
	            strUseType = "230";
	    	}
	    	else if(intUseType.trim().equals("8A")) 	
	    	{
	            strUseType = "210";
	    	}
	    	else if(intUseType.trim().equals("9B"))		
	    	{
	        	strUseType = "102";
	    	}
	    	else if(intUseType.trim().equals("9C"))		
	    	{
	        	strUseType = "103";
	    	}
	    	else if(intUseType.trim().equals("9A"))		
	    	{
	        	strUseType = "101";
	    	}
	    	else if(intUseType.trim().equals("86")) 	
	    	{
	            strUseType = "100";
	    	}
	    	else if(intUseType.trim().equals("87"))		
	    	{
	            strUseType = "101";
	    	}
	    	else if(intUseType.trim().equals("9D"))		
	    	{
	            
	    		
	    	}
	    	else if(intUseType.trim().equals("9E"))		
	    	{
	    		
	    		if(PubTools.compareDate(blPolicy.getBLPrpCmain().getArr(0).getOperateDate().trim(), "2007-04-13") < 0)
	    		{
		    		strUseType = "102";					
	    		}
	    		else
	    		{
	    			strUseType = "103";					
	    		}
	    		
	    	}
     	}
        
	    else if(strCarKindCode.equals("H0") ||
	    		strCarKindCode.equals("H1") ||
	    		strCarKindCode.equals("H2") ||
	            strCarKindCode.equals("G0"))
	    {
	    	if(intUseType.trim().equals("86") ||   	
	    	   intUseType.trim().equals("87") ||	
	    	   intUseType.trim().equals("9D") ||	
	    	   intUseType.trim().equals("9E") ||	
	    	   intUseType.trim().equals("9A") ||	
	    	   intUseType.trim().equals("9B") ||	
	    	   intUseType.trim().equals("9C") 		
	    	   )
	    	{
	    		strUseType = "100";		
	    	}
	    	else if(
	    			intUseType.trim().equals("8B")   ||		
	    			intUseType.trim().equals("8C")   ||		
	    			intUseType.trim().equals("8D")          
	    			
	    			)
	    	{
	    		strUseType = "200";		
	    	}

	    }
        
	    else
	    {
	    	strUseType = "000";
	    }

        return strUseType;
    }
    
    
     private String encoderVehicleCategory(BLPolicy blPolicy) throws Exception {
        String strVehicleCategory = "";
        String strCarKindCode = "";
        double exhaustScale = 0;
        double toncount = 0;
        int seatcount = 0;
        String intUseType = "";
        intUseType = blPolicy.getBLPrpCitemCar().getArr(0).getUseNatureCode();
        seatcount = new Integer(blPolicy.getBLPrpCitemCar().getArr(0).getSeatCount()).intValue();
        toncount = Double.parseDouble(blPolicy.getBLPrpCitemCar().getArr(0).getTonCount());
        exhaustScale = Double.parseDouble(blPolicy.getBLPrpCitemCar().getArr(0).getExhaustScale());
        strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();

        if (strCarKindCode.equals("A0")) {
            if (seatcount >= 0 && seatcount < 6) {
                strVehicleCategory = "11";
            } else if (seatcount >= 6 && seatcount < 10) {
                strVehicleCategory = "12";
            } else if (seatcount >= 10 && seatcount < 20) {
                strVehicleCategory = "13";
            } else if (seatcount >= 20 && seatcount < 36) {
                strVehicleCategory = "14";
            } else if (seatcount >= 36) {
                strVehicleCategory = "15";
            }

        }
        else if (strCarKindCode.equals("H0") ||
        		 strCarKindCode.equals("H1") ||
        		 strCarKindCode.equals("H2"))	
        {
            if (toncount >= 0 && toncount < 2) {
                strVehicleCategory = "21";
            } else if (toncount >= 2 && toncount < 5) {
                strVehicleCategory = "22";
            } else if (toncount >= 5 && toncount < 10) {
                strVehicleCategory = "23";
            } else if (toncount >= 10) {
                strVehicleCategory = "24";
            }

        }
        else if (strCarKindCode.equals("G0"))		
        {
        	if (toncount >= 0 && toncount < 2) {
                strVehicleCategory = "25";
            } else if (toncount >= 2 && toncount < 5) {
                strVehicleCategory = "26";
            } else if (toncount >= 5 && toncount < 10) {
                strVehicleCategory = "27";
            } else if (toncount >= 10) {
                strVehicleCategory = "28";
            }
        }
        else if (strCarKindCode.equals("TP") ||
        		 strCarKindCode.equals("TQ") ||
        		 strCarKindCode.equals("TR") ||
                 strCarKindCode.equals("TS")) {
            
            strVehicleCategory = "30";
        } else if (strCarKindCode.equals("T1") ||
        		   strCarKindCode.equals("T2") ||
        		   strCarKindCode.equals("T3") ||
                   strCarKindCode.equals("T5")||
                   strCarKindCode.equals("TE")||
                   strCarKindCode.equals("TF")||
                   strCarKindCode.equals("TG")||
                   strCarKindCode.equals("TH")||
                   strCarKindCode.equals("TI")||
                   strCarKindCode.equals("TJ")||
                   strCarKindCode.equals("TK")||
                   strCarKindCode.equals("TL")||
                   strCarKindCode.equals("TM")||
                   strCarKindCode.equals("TN")||
                   strCarKindCode.equals("TD")||
                   strCarKindCode.equals("T7")) {
            
            
            strVehicleCategory = "40";
        } else if (strCarKindCode.equals("T4") ||
        		   strCarKindCode.equals("T6") ||
        		   strCarKindCode.equals("T8") ||
        		   strCarKindCode.equals("T9")||
        		   strCarKindCode.equals("TO")||
               strCarKindCode.equals("TU")||
               strCarKindCode.equals("TV")||
               strCarKindCode.equals("TW")||
               strCarKindCode.equals("TX")||
               strCarKindCode.equals("TY")||
               strCarKindCode.equals("TZ")||
               strCarKindCode.equals("TC")) {
            
            
            strVehicleCategory = "50";
        } else if (strCarKindCode.equals("TT")) {
            
            strVehicleCategory = "60";
        } else if (strCarKindCode.equals("M0")||
                   strCarKindCode.equals("M1")||
                   strCarKindCode.equals("M3")||
                   strCarKindCode.equals("M4")) {
            
            if (exhaustScale >= 0 && exhaustScale <= 0.05) {
                strVehicleCategory = "71";
            } else if (exhaustScale > 0.05 && exhaustScale <= 0.25) {
                strVehicleCategory = "72";
            } else if (exhaustScale > 0.25) {
                strVehicleCategory = "73";
            }
        } else if (strCarKindCode.equals("M2")) {
            
            strVehicleCategory = "73";            
        } else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J1")||strCarKindCode.equals("J2")||strCarKindCode.equals("J3"))&&intUseType.trim().equals("7A")) {
            
            
            if (exhaustScale >= 0 && exhaustScale <= 14.7) {
                strVehicleCategory = "81";
            } else if (exhaustScale > 14.7) {
                strVehicleCategory = "82";
            }
        }
        else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J1")||strCarKindCode.equals("J2")||strCarKindCode.equals("J3"))&&intUseType.trim().equals("7B")) {
            
            if (exhaustScale >= 0 && exhaustScale <= 14.7) {
                strVehicleCategory = "91";
            } else if (exhaustScale > 14.7) {
                strVehicleCategory = "92";
            }
        }else if (strCarKindCode.equals("G1"))          
		   {
			   strVehicleCategory = "31";
		   }

        return strVehicleCategory;
    }

      /**
	 * 纠正日期格式
	 *
	 * @param dateString
	 *            8个字符的日期
	 * @return YYYYMMDD形式的日期
	 */
	private String correctDate(String dateString) {
		String result = StringUtils.replace(dateString, "-", "");
		return result;
	}

	/**
	 * 纠正日期时间格式
	 *
	 * @param dateString
	 *            修正前的日期时间
	 * @return 修正后的日期时间
	 */
	private String correctDateTime(String dateString) {
		String result = correctDate(dateString);
		result = StringUtils.replace(dateString, " ", "");
		result = StringUtils.replace(dateString, ":", "");
		return result;
	}    
	
	/**
	    * 获取本不浮动原因
	    * @param peccancyAdjustReason
	    * @return
	    */
	    public static String getNoneFluctuateFlag(String noneFluctuateFlag)
	    {
	 		String strNoneFluctuateFlag="";
	 		if(noneFluctuateFlag==null||noneFluctuateFlag.trim().length()==0)
	 		{
	 			strNoneFluctuateFlag = "99";
	 		}
	 		else if(noneFluctuateFlag.equals("N2")) {
	 			strNoneFluctuateFlag = "01";
	 		}
	 		else if(noneFluctuateFlag.equals("N3")) {
	 			strNoneFluctuateFlag = "02";
	 		}
	 		else if(noneFluctuateFlag.equals("N4")) {
	 			strNoneFluctuateFlag = "03";
	 		}
	 		else if(noneFluctuateFlag.equals("N5")) {
	 			strNoneFluctuateFlag = "04";
	 		}
	 		else if(noneFluctuateFlag.equals("N8")) {
	 			strNoneFluctuateFlag = "05";
	 		}
	 		else if(noneFluctuateFlag.equals("N7") || noneFluctuateFlag.equals("N6")) {
	 			strNoneFluctuateFlag = "06";
	 		}
	 		else if(noneFluctuateFlag.equals("N1")) {
	 			strNoneFluctuateFlag = "06";
	 		}
	 		return strNoneFluctuateFlag;
	    }
    
}
