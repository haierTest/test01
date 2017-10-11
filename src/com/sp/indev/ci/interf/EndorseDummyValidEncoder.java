package com.sp.indiv.ci.interf;

import java.text.DecimalFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sp.indiv.bi.pub.TransCode;
import com.sp.indiv.ci.blsvr.BLCIEndorValid;
import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.dbsvr.DBCIEndorValid;
import com.sp.indiv.ci.dbsvr.DBCIInsureValid;
import com.sp.indiv.ci.schema.CIEndorValidSchema;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.cb.BLCPolicy;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPrpCPcarshipTax;
import com.sp.prpall.blsvr.cb.BLPrpCPitemCar;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.prpall.blsvr.pg.BLPrpPcarshipTax;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.pubfun.TransCodeCI;
import com.sp.prpall.schema.PrpCPitemCarSchema;
import com.sp.prpall.schema.PrpCitemCarSchema;
import com.sp.prpall.schema.PrpPitemCarSchema;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;

/**
 * 发送批改确认数据的编码器
 *
 */
public class EndorseDummyValidEncoder {
	
	
	
	BLPrpCPitemCar blPrpCPitemCar = new BLPrpCPitemCar();
	
	private static Logger logger = Logger.getLogger(EndorseDummyValidEncoder.class);
	

	/**
	 * 编码
	 *
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
	public String encode(DbPool dbPool, BLEndorse blEndorse) throws Exception {
		
		StringBuffer buf = new StringBuffer(4000);

		
		getCIEndorValid(dbPool, blEndorse);
		
		if(new UtiPower().checkSinoCarPlatformSZ(blEndorse.getBLPrpPhead().getArr(0).getComCode(),blEndorse.getBLPrpPhead().getArr(0).getEndorDate())){
			getCIInsureValid(dbPool, blEndorse);
			blPrpCPitemCar.getData(dbPool, blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
		}
		
		addXMLHead(buf);
		addPacket(dbPool,buf, blEndorse);
		
		
		logger.info("[批改预确认发送报文]:"+buf.toString());
		
        
		return buf.toString();
	}

	/**
	 * 添加XML文件头
	 *
	 * @param buf
	 *            StringBuffer
	 */
	protected void addXMLHead(StringBuffer buf) {
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
	}

	/**
	 * 添加PACKET节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addPacket(DbPool dbPool,StringBuffer buf, BLEndorse blEndorse)
			throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");

		addHead(buf,blEndorse);
		addBody(dbPool,buf, blEndorse);
		buf.append("</PACKET>");
	}

	/**
	 * 添加HEAD节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addHead(StringBuffer buf,BLEndorse blEndorse) throws Exception {
		
		
		
		
		

		String strComCode = blEndorse.getBLPrpPmain().getArr(0).getComCode();

		buf.append("<HEAD>");
        addNode(buf, "REQUEST_TYPE", "05");
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
	protected void addBody(DbPool dbPool,StringBuffer buf, BLEndorse blEndorse)
			throws Exception {
		buf.append("<BODY>");
		addBasePart(dbPool,buf, blEndorse);
		
		if(new UtiPower().checkSinoCarPlatformSZ(blEndorse.getBLPrpPhead().getArr(0).getComCode(),blEndorse.getBLPrpPhead().getArr(0).getEndorDate())){
			addCheckItem(buf, blEndorse);
			addVehicle(buf, blEndorse);
		}
		
		
		if(new UtiPower().checkInterAgreementSX(blEndorse.getBLPrpPhead().getArr(0).getComCode())){
			addCheckItemCar(buf, blEndorse);
		}
		
        
        
        addVehicleTaxation(dbPool,buf,blEndorse);
        
        
		if(new UtiPower().checkCIInsureBJ(blEndorse.getBLPrpPhead().getArr(0).getRiskCode(),blEndorse.getBLPrpPhead().getArr(0).getComCode())){
			addAmendList(buf, blEndorse);
		}
		
		
		if(UtiPower.isIdentifyCheck(blEndorse.getBLPrpPhead().getArr(0).getComCode(), blEndorse.getBLPrpPhead().getArr(0).getRiskCode())){
			boolean chgPremium=false;
			for(int i=0;i<blEndorse.getBLPrpPitemKind().getSize();i++){
				if(Double.parseDouble(blEndorse.getBLPrpPitemKind().getArr(i).getChgPremium())<0){
					chgPremium=true;
				}
			}
			if(chgPremium||blEndorse.getBLPrpPinsured().getSize()>0){
				addPHList(buf, blEndorse);
	        	addInsuredList(buf, blEndorse);
			}
        }
		
		buf.append("</BODY>");
	}

	/**
	 * 添加BASE_PART节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBasePart(DbPool dbPool,StringBuffer buf, BLEndorse blEndorse)
			throws Exception {
		
		
		
		
		
		
		
		String strDemandNo = "";
		String strPolicyno = "";
		String strChgPremium = "";

		
		String strComCode  = blEndorse.getBLPrpPmain().getArr(0).getComCode();
		
		strDemandNo=blEndorse.getBLCIEndorValid().getArr(0).getDemandNo();
		strPolicyno=blEndorse.getBLCIEndorValid().getArr(0).getPolicyNo();
		strChgPremium=blEndorse.getBLCIEndorValid().getArr(0).getChgPremium();

		buf.append("<BASE_PART>");
		addNode(buf,"AMEND_QUERY_NO",strDemandNo);
		addNode(buf,"AMEND_CONFIRM_NO","");
		addNode(buf,"AMEND_POLICY_NO",strPolicyno);
		addNode(buf,"ACTUAL_AMEND_PREMIUM",strChgPremium);
		if(blEndorse.getBLPrpPmain().getArr(0).getComCode().substring(0, 2).trim().equals("01")) {
			addNode(buf,"DUMMY_AMEND_FLAG","1");
		}
		
		if(SysConfig.getProperty("CI_SINOSOFT_0806").trim().indexOf(
				strComCode.substring(0, 2).trim()) > -1){
			addNode(buf,"ENDOR_CONFORM_FLAG","0");
		}
		
        
        EndorseDummyCarShipTaxValidEncoder endorseDummyCarShipTaxValidEncoder = new EndorseDummyCarShipTaxValidEncoder();
        endorseDummyCarShipTaxValidEncoder.encode(dbPool,blEndorse,buf,"BASE_PART");
        
        
        
        
        if (new UtiPower().checkSendEffectiveHour(blEndorse.getBLPrpPmain().getArr(0).getComCode())
        		|| new UtiPower().checkInterAgreementSX(blEndorse.getBLPrpPmain().getArr(0).getComCode())
        		|| new UtiPower().checkInterfaceHLJ(blEndorse.getBLPrpPmain().getArr(0).getComCode())) {
            String strEffectiveTime = TransCode.correctDate(blEndorse.getBLPrpPhead().getArr(0).getValidDate());
            String strEffectiveHour = blEndorse.getBLPrpPhead().getArr(0).getValidHour();
            if (strEffectiveHour.length() != 2) {
                if (strEffectiveHour.equals("0") || strEffectiveHour.equals("")) {
                    strEffectiveHour = "00";
                } else {
                    strEffectiveHour = "0" + strEffectiveHour;
                }
            }
            addNode(buf, "EFFECTIVE_DATE", strEffectiveTime + strEffectiveHour);
        }
        
        
        
		buf.append("</BASE_PART>");
	}
	
	
	/**
     * 添加AMEND_LIST节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addAmendList(StringBuffer buf, BLEndorse blEndorse)
            throws Exception {
    	String strCoverageType  = ""; 
    	String strCoverageCode  = ""; 
    	String strKindCode      = ""; 
    	String strChgPremium    = ""; 
    	String strRiskCode      = ""; 
    	int iKindCount = blEndorse.getBLPrpPitemKind().getSize();
    	for(int i = 0;i<iKindCount;i++){
    		strKindCode   = blEndorse.getBLPrpPitemKind().getArr(i).getKindCode();
    		strRiskCode   = blEndorse.getBLPrpPitemKind().getArr(i).getRiskCode();
    		strCoverageCode = encodeCoverageCodeBJ(strKindCode, strRiskCode);
    		strCoverageType = encodeCoverageType(strKindCode);
    		strChgPremium = blEndorse.getBLPrpPitemKind().getArr(i).getChgPremium();
    		double douChgPremium= Double.parseDouble(ChgData.chgStrZero(strChgPremium));
    		if("0507".equals(strRiskCode)&&douChgPremium==0){
    			continue;
    		}
    		buf.append("<AMEND_LIST>");
            addNode(buf, "COVERAGE_TYPE", strCoverageType); 
            addNode(buf, "COVERAGE_CODE", strCoverageCode); 
            addNode(buf, "COM_COVERAGE_CODE", strKindCode); 
            addNode(buf, "AMEND_AMOUNT", strChgPremium);    
            buf.append("</AMEND_LIST>");
    	}
    }
    
    
    /**
     * 添加CHECKITEM_CAR节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addCheckItemCar(StringBuffer buf, BLEndorse blEndorse)
            throws Exception {
    	String strUseType = "";
    	String strVehicleCategory   = ""; 
    	blPrpCPitemCar.getData(blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
    	if(new UtiPower().checkStartUp0802(blEndorse.getBLPrpPhead().getArr(0).getRiskCode(),blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
            strUseType      = encoderUseTypeNew(blEndorse);
        }else{
            strUseType      = encoderUseType(blEndorse);
        }
    	if(new UtiPower().checkStartUp0802(blEndorse.getBLPrpPhead().getArr(0).getRiskCode(),blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
            strVehicleCategory = encoderVehicleCategoryNew(blEndorse);
        }else{
            strVehicleCategory = encoderVehicleCategory(blEndorse);            
        }
    	buf.append("<CHECKITEM_CAR>");
        addNode(buf, "ENGINE_NO", blPrpCPitemCar.getArr(0).getEngineNo()); 
        addNode(buf, "RACK_NO", blPrpCPitemCar.getArr(0).getFrameNo()); 
        addNode(buf, "USE_TYPE", strUseType); 
        addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);  
        BLPrpCPcarshipTax blPrpCPcarshipTax = new BLPrpCPcarshipTax();
    	blPrpCPcarshipTax.getData(blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
    	if(blPrpCPcarshipTax.getSize()>0){
	        if(blPrpCPcarshipTax.getArr(0).getExtendChar2()!=null&&(!"".equals(blPrpCPcarshipTax.getArr(0).getExtendChar2().trim())))
	        	addNode(buf, "VEHICLE_STYLE", blPrpCPcarshipTax.getArr(0).getExtendChar2());
	    	else{
	        	if("A".equals(blPrpCPcarshipTax.getArr(0).getTaxItemCode())){
	        		addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(blPrpCPcarshipTax.getArr(0).getTaxItemDetailCode(),Double.parseDouble(ChgData.chgStrZero(blPrpCPitemCar.getArr(0).getTonCount()))));
	        	}else{
	        		addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(blPrpCPcarshipTax.getArr(0).getTaxItemCode(),Double.parseDouble(ChgData.chgStrZero(blPrpCPitemCar.getArr(0).getTonCount()))));
	        	}
	    	}
    	}else{
    		if("A".equals(blPrpCPitemCar.getArr(0).getCarKindCode().substring(0,1)))
                addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(generateTaxItem(blPrpCPitemCar.getArr(0)),Double.parseDouble(ChgData.chgStrZero(blPrpCPitemCar.getArr(0).getTonCount()))));
            else
                addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(generateTaxItem(blPrpCPitemCar.getArr(0)),Double.parseDouble(ChgData.chgStrZero(blPrpCPitemCar.getArr(0).getTonCount()))));
    	}
        buf.append("</CHECKITEM_CAR>");
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

	/**
	 * 获得批改确认表信息
	 *
	 * @param blEndorse
	 * @throws Exception
	 */
	private void getCIEndorValid(DbPool dbPool, 
								 BLEndorse blEndorse) 
		throws Exception 
	{
		try {
			String sqlWhere = " Select * from CIEndorValid WHERE EndorseNo = '"
							  + blEndorse.getBLPrpPhead().getArr(0).getEndorseNo() + "'";
			DBCIEndorValid dbCIEndorValid = new DBCIEndorValid();
			Vector vector = dbCIEndorValid.findByConditions(dbPool, sqlWhere);
			CIEndorValidSchema cIEndorValidSchema = (CIEndorValidSchema) vector .get(0);
			BLCIEndorValid blCIEndorValid = new BLCIEndorValid();
			blCIEndorValid.setArr(cIEndorValidSchema);
			blEndorse.setBLCIEndorValid(blCIEndorValid);
		} 
		catch (Exception ex) 
		{
			throw ex;
		}
	}
	
	private void getCIInsureValid(DbPool dbPool, BLEndorse blEndorse) throws Exception 
	{
		try {
			String sqlWhere = " Select * from CIInsureValid WHERE PolicyNo = '"
				+ blEndorse.getBLPrpPhead().getArr(0).getPolicyNo() + "'";
			DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
			Vector vector = dbCIInsureValid.findByConditions(dbPool, sqlWhere);
			CIInsureValidSchema cIInsureValidSchema = (CIInsureValidSchema) vector .get(0);
			BLCIInsureValid blCIInsureValid = new BLCIInsureValid();
			blCIInsureValid.setArr(cIInsureValidSchema);
			blEndorse.setBLCIInsureValid(blCIInsureValid);
		} 
		catch (Exception ex) 
		{
			throw ex;
		}
	}
	
    
    public void addVehicleTaxation(DbPool dbPool,StringBuffer buf, BLEndorse blEndorse) throws Exception {
        EndorseDummyCarShipTaxValidEncoder endorseDummyCarShipTaxValidEncoder = new EndorseDummyCarShipTaxValidEncoder();
        endorseDummyCarShipTaxValidEncoder.encode(dbPool,blEndorse,buf,"VehicleTaxation");
    }
    
    
    
	/**
     * 平台XXXXX种代码转换
     * @param strKindCode
     * @return
     * @throws Exception
     */
    private String encodeCoverageType(String strKindCode) throws Exception{
    	String strCoverageType ="";
    	 if("BZ".equals(strKindCode)){
     		 strCoverageType = "1";        
     	 }else if("B".equals(strKindCode)){
     		 strCoverageType = "2";
     	 }else if("A".equals(strKindCode) || "D3".equals(strKindCode) 
     			 || "D4".equals(strKindCode) || "G1".equals(strKindCode)){
     		 strCoverageType = "3";
     	 }else{
     		 strCoverageType = "9";
     	 }
    	return strCoverageType;
    }
    
    
    private String encodeCoverageCodeBJ(String strKindCode, String strRiskCode) throws Exception{
    	String strCoverageCode = "";
    	if("0509".equals(strRiskCode)){
    		if("BZ".equals(strKindCode)){
        		strCoverageCode = "CLIBJ2";
        	 }else if("A".equals(strKindCode)){
        		strCoverageCode = "C11";
        	 }else if("B".equals(strKindCode)){
        		strCoverageCode = "B11";
        	 }else if("G1".equals(strKindCode)){
        		strCoverageCode = "D11";
        	 }else if("D3".equals(strKindCode) || "D4".equals(strKindCode)){
        		strCoverageCode = "E11";
           	 
        	 }else if("Z".equals(strKindCode)){
    		    strCoverageCode = "G11";
    		 
        	 }else{
        		strCoverageCode = "F11";
        	 }
    	}else{
    	     if("BZ".equals(strKindCode)){
    		    strCoverageCode = "CLIBJ2";
    	     }else if("A".equals(strKindCode)){
    		    strCoverageCode = "C01";
    	     }else if("B".equals(strKindCode)){
    		    strCoverageCode = "B01";
    	     }else if("G1".equals(strKindCode)){
    		    strCoverageCode = "D01";
    	     }else if("D3".equals(strKindCode) || "D4".equals(strKindCode)){
    		    strCoverageCode = "E01";
    	     }else if("Z".equals(strKindCode)){
    		    strCoverageCode = "G01";
    	     }else{
    		    strCoverageCode = "F01";
    	     }
    	}
    	return strCoverageCode;
    }
   
    
    protected void addCheckItem(StringBuffer buf, BLEndorse blEndorse) throws Exception {
    	buf.append("<CHECK_ITEM>");
    	addCheckVehicle(buf, blEndorse);
    	addDriverList(buf, blEndorse);
    	buf.append("</CHECK_ITEM>");
    }
    protected void addVehicle(StringBuffer buf, BLEndorse blEndorse) throws Exception {
    	String strVehicleCategory   = ""; 
    	String strTonCount = "";
    	if(new UtiPower().checkStartUp0802(blEndorse.getBLPrpPmain().getArr(0).getRiskCode(),blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
            strVehicleCategory = encoderVehicleCategoryNew(blEndorse);
        }else{
            strVehicleCategory = encoderVehicleCategory(blEndorse);            
        }
    	strTonCount = blPrpCPitemCar.getArr(0).getTonCount();
    	DecimalFormat ideciamlFormat = new DecimalFormat("0");
    	strTonCount = ideciamlFormat.format(Double.parseDouble(strTonCount)*1000);
    	buf.append("<VEHICLE>");
    	addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);
    	addNode(buf, "LIMIT_LOAD_PERSON", blPrpCPitemCar.getArr(0).getSeatCount());
    	addNode(buf, "LIMIT_LOAD", strTonCount);
    	if(blEndorse.getBLPrpPcarshipTax().getSize()>0){
    		if(!"".equals(blPrpCPitemCar.getArr(0).getCompleteKerbMass()))
        		addNode(buf, "WHOLE_WEIGHT", ChgData.chgStrZero(blPrpCPitemCar.getArr(0).getCompleteKerbMass()));
             else
                addNode(buf, "WHOLE_WEIGHT", "0");
        }else{
                addNode(buf, "WHOLE_WEIGHT", ChgData.chgStrZero(blPrpCPitemCar.getArr(0).getCompleteKerbMass()));
        }
    	addNode(buf, "DISPLACEMENT", ""+(new Double(1000*Double.parseDouble(ChgData.chgStrZero(blPrpCPitemCar.getArr(0).getExhaustScale()))).intValue()));
    	
    	if(new UtiPower().savePlatformAdjustSwitchJS(blEndorse.getBLPrpPmain().getArr(0).getComCode())){
    		String strVehicleBrand = blPrpCPitemCar.getArr(0).getBrandName();
    		addNode(buf, "VEHICLE_MODEL", strVehicleBrand);
    	}
    	
    	buf.append("</VEHICLE>");
    }
    protected void addCheckVehicle(StringBuffer buf, BLEndorse blEndorse) throws Exception {
    	String strUseType = "";
    	String strVehicleCategory = "";
    	String strLicenseNo         = ""; 
    	String strVehicleType       = ""; 
    	String strEndorType         = "";
    	if(new UtiPower().checkStartUp0802(blEndorse.getBLPrpPhead().getArr(0).getRiskCode(),blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
            strUseType      = encoderUseTypeNew(blEndorse);
        }else{
            strUseType      = encoderUseType(blEndorse);
        }
    	strLicenseNo    = blPrpCPitemCar.getArr(0).getLicenseNo(); 
    	
    	
        
    	if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+strLicenseNo.trim()+",") > -1 || 
    	
           "".equals(strLicenseNo.trim()))
        {
            strLicenseNo = "";
        }
        strVehicleType  = blPrpCPitemCar.getArr(0).getLicenseKindCode();
        strEndorType = blEndorse.getBLCIEndorValid().getArr(0).getEndorType();
        if(strEndorType.equals("71"))
        {
            strEndorType = "01";
        }else{
        	strEndorType = "";
        }
        String strNum = "0";
    	if(blPrpCPitemCar.getArr(0).getFrameNo().length()<17){
    		strNum = "1";
    	}
    	buf.append("<CHECK_VEHICLE>");
    	addNode(buf, "CONFIRM_SEQUENCE_NO", blEndorse.getBLCIInsureValid().getArr(0).getValidNo()); 
    	addNode(buf, "AMEND_QUERY_NO", blEndorse.getBLCIEndorValid().getArr(0).getDemandNo()); 
    	addNode(buf, "EFFECTIVE_DATE", correctDate(blEndorse.getBLPrpPhead().getArr(0).getValidDate())+"00"); 
    	addNode(buf, "USE_TYPE", strUseType); 
    	addNode(buf, "CAR_MARK", strLicenseNo); 
    	addNode(buf, "VEHICLE_TYPE", strVehicleType);
    	addNode(buf, "ENGINE_NO", blPrpCPitemCar.getArr(0).getEngineNo());
    	addNode(buf, "RACK_NO", blPrpCPitemCar.getArr(0).getFrameNo());
    	addNode(buf, "OWNER_NAME", blPrpCPitemCar.getArr(0).getCarOwner());
    	if("0".equals(blPrpCPitemCar.getArr(0).getCarOwnerNature())){
    		addNode(buf, "CERTI_TYPE", translate(blPrpCPitemCar.getArr(0).getInsuredTypeCode()));
    	}else{
    		addNode(buf, "CERTI_TYPE", "10");
    	}
    	addNode(buf, "CERTI_CODE", "");
    	addNode(buf, "AMEND_DRIVER", "0");
    	addNode(buf, "DRIVER_NUM", "0");
    	addNode(buf, "ENDOR_TYPE", strEndorType);
    	BLPrpCPcarshipTax blPrpCPcarshipTax = new BLPrpCPcarshipTax();
    	blPrpCPcarshipTax.getData(blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
    	
    	
    	if(new UtiPower().savePlatformAdjustSwitchJS(blEndorse.getBLPrpPmain().getArr(0).getComCode())
    			&& "0507".equals(blEndorse.getBLPrpPmain().getArr(0).getRiskCode())
    			&& !"M".equals(blPrpCPitemCar.getArr(0).getCarKindCode().substring(0,1))){
    		addNode(buf, "VEHICLE_STYLE", blPrpCPitemCar.getArr(0).getLicenseCategory());
    	}else
    	
    	if(blPrpCPcarshipTax.getSize()>0){
        	
        	boolean carShipTaxQGFlag = new UtiPower().checkCICarShipTaxQG(blEndorse.getBLPrpPmain().getArr(0).getRiskCode(), blEndorse.getBLPrpPmain().getArr(0).getComCode());
        	
        	if("M".equals(blPrpCPitemCar.getArr(0).getCarKindCode().substring(0,1)) && !carShipTaxQGFlag){
        		addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(generateTaxItem(blPrpCPitemCar.getArr(0)),Double.parseDouble(ChgData.chgStrZero(blPrpCPitemCar.getArr(0).getTonCount()))));
        	}else{
        		if(new UtiPower().CarCategorySwitch(blEndorse.getBLPrpPmain().getArr(0).getComCode(),blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
        			addNode(buf, "VEHICLE_STYLE",blPrpCPitemCar.getArr(0).getLicenseCategory());
        		}else{
        			if(blPrpCPcarshipTax.getArr(0).getExtendChar2()!=null&&(!"".equals(blPrpCPcarshipTax.getArr(0).getExtendChar2().trim())))
                    	addNode(buf, "VEHICLE_STYLE", blPrpCPcarshipTax.getArr(0).getExtendChar2());
                	else{
                    	if("A".equals(blPrpCPcarshipTax.getArr(0).getTaxItemCode())){
                    		addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(blPrpCPcarshipTax.getArr(0).getTaxItemDetailCode(),Double.parseDouble(ChgData.chgStrZero(blPrpCPitemCar.getArr(0).getTonCount()))));
                    	}else{
                    		addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(blPrpCPcarshipTax.getArr(0).getTaxItemCode(),Double.parseDouble(ChgData.chgStrZero(blPrpCPitemCar.getArr(0).getTonCount()))));
                    	}
                	}
        		}
        	}
        }else{
    		if(new UtiPower().CarCategorySwitch(blEndorse.getBLPrpPmain().getArr(0).getComCode(),blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
    			addNode(buf, "VEHICLE_STYLE",blPrpCPitemCar.getArr(0).getLicenseCategory());
    		}else{
            if("A".equals(blPrpCPitemCar.getArr(0).getCarKindCode().substring(0,1)))
                addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(generateTaxItem(blPrpCPitemCar.getArr(0)),Double.parseDouble(ChgData.chgStrZero(blPrpCPitemCar.getArr(0).getTonCount()))));
            else
                addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(generateTaxItem(blPrpCPitemCar.getArr(0)),Double.parseDouble(ChgData.chgStrZero(blPrpCPitemCar.getArr(0).getTonCount()))));
    		}
        }
    	
    	addNode(buf, "POWER", ""+(new Double(1000*Double.parseDouble(ChgData.chgStrZero(blPrpCPitemCar.getArr(0).getExhaustScale()))).intValue()));
    	addNode(buf, "VEHICLE_REGISTER_DATE", correctDate(blPrpCPitemCar.getArr(0).getEnrollDate()));
    	addNode(buf, "RACK_NO_FLAG", strNum);
    	
    	if (new UtiPower().savePlatformAdjustSwitchJS(blEndorse.getBLPrpPmain().getArr(0).getComCode())) {
    		String strEcdemicVehicleFlag = "";
    		String strCarMark = blPrpCPitemCar.getArr(0).getLicenseNo();
    		strEcdemicVehicleFlag = TransCodeCI.getEcdemicVehicleFlag(blEndorse.getBLPrpPmain().getArr(0).getComCode(), strCarMark.trim());
    		String strChgOwnerDate = blPrpCPitemCar.getArr(0).getChgOwnerDate();
    		addNode(buf, "ECDEMIC_VEHICLE_FLAG", strEcdemicVehicleFlag);
            addNode(buf, "TRANSFER_DATE", correctDate(TransCode.correctDate(strChgOwnerDate)));
    	}
    	
    	buf.append("</CHECK_VEHICLE>");
    }
    protected void addDriverList(StringBuffer buf, BLEndorse blEndorse) throws Exception {
    	buf.append("<CHECK_DRIVER_LIST>");
    	
		addDriver(buf, blEndorse);
    	buf.append("</CHECK_DRIVER_LIST>");
    }
    protected void addDriver(StringBuffer buf, BLEndorse blEndorse) throws Exception {
    	String strLicenseNo     = ""; 
        String strCertiType     = ""; 
        String strIsMaster      = ""; 
        String strArea          = ""; 
        String strGender        = ""; 
        String strDriverPeriod  = ""; 
        String strAge           = ""; 
        String strDriverType    = ""; 
        
        
        
        
        for (int i = 0; i < blEndorse.getBLPrpPcarDriver().getSize(); i++) {
		if(blEndorse.getBLPrpPcarDriver().getArr(i).getIdentifyNumber()!=null 
				&& !"".equals(blEndorse.getBLPrpPcarDriver().getArr(i).getIdentifyNumber())){
			strCertiType = carDriverIdentifyTypeEncoder(blEndorse.getBLPrpPcarDriver().getArr(i).getIdentifyType());
			strLicenseNo = blEndorse.getBLPrpPcarDriver().getArr(i).getIdentifyNumber();
			strIsMaster = (i==0)?"1":"2";
			strGender = blEndorse.getBLPrpPcarDriver().getArr(i).getSex();
			strDriverPeriod = blEndorse.getBLPrpPcarDriver().getArr(i).getDrivingYears();
			strAge = blEndorse.getBLPrpPcarDriver().getArr(i).getAge();
			strDriverType = blEndorse.getBLPrpPcarDriver().getArr(i).getDrivingCarType();
		}
		
        buf.append("<CHECK_DRIVER>");
        addNode(buf, "LICENSE_NO", strLicenseNo); 
        addNode(buf, "CERTI_TYPE", strCertiType); 
        addNode(buf, "IS_MASTER", strIsMaster); 
        addNode(buf, "AREA", strArea); 
        addNode(buf, "GENDER", strGender); 
        addNode(buf, "DRIVER_PERIOD", strDriverPeriod); 
        addNode(buf, "AGE", strDriverPeriod); 
        addNode(buf, "DRIVER_TYPE", strDriverType); 
        buf.append("</CHECK_DRIVER>");
        
        if(blEndorse.getBLPrpPcarDriver().getArr(i).getIdentifyNumber()==null 
				|| "".equals(blEndorse.getBLPrpPcarDriver().getArr(i).getIdentifyNumber())){
        	break;
        }
        }
        
    }
    private String encoderUseType(BLEndorse blEndorse) throws Exception {
        String strUseType = "";
        String intUseType = "";
        intUseType = blPrpCPitemCar.getArr(0).getUseNatureCode();
        String strCarKindCode = blPrpCPitemCar.getArr(0).getCarKindCode();
        
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
                
                if(PubTools.compareDate(blEndorse.getBLPrpPmain().getArr(0).getOperateDate().trim(), "2007-04-13") < 0)
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
                    intUseType.trim().equals("8B") ||       
                    intUseType.trim().equals("8C") ||           
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

    
    private String encoderUseTypeNew(BLEndorse blEndorse) throws Exception {
        String strUseType = "";
        String intUseType = "";
        intUseType = blPrpCPitemCar.getArr(0).getUseNatureCode();
        String strCarKindCode = blPrpCPitemCar.getArr(0).getCarKindCode();
        
        if(strCarKindCode.equals("A0")||
           strCarKindCode.equals("A1")||
           strCarKindCode.equals("A2")||
           strCarKindCode.equals("A3")||
           strCarKindCode.equals("A4"))
        {
            if(intUseType.trim().equals("8B"))          
            {
                strUseType = "220";
            }
            else if(intUseType.trim().equals("8E")||intUseType.trim().equals("8F"))
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
            else if(intUseType.trim().equals("9F")||intUseType.trim().equals("9G"))     
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
                
                if(PubTools.compareDate(blEndorse.getBLPrpPmain().getArr(0).getOperateDate().trim(), "2007-04-13") < 0)
                {
                    strUseType = "102";                 
                }
                else
                {
                    strUseType = "103";                 
                }
                
            }
        }
        
        else if(strCarKindCode.equals("H0")||
                 strCarKindCode.equals("H3")||
                 strCarKindCode.equals("H4")||
                 strCarKindCode.equals("H5")||
                 strCarKindCode.equals("H6")||
                 strCarKindCode.equals("H7")||
                 strCarKindCode.equals("H8")||
                 strCarKindCode.equals("G0"))
        {
            if(intUseType.trim().equals("86") ||    
               intUseType.trim().equals("87") ||    
               intUseType.trim().equals("9D") ||    
               intUseType.trim().equals("9E") ||    
               intUseType.trim().equals("9F") ||    
               intUseType.trim().equals("9G") ||    
               intUseType.trim().equals("9B") ||    
               intUseType.trim().equals("9C")       
               )
            {
                strUseType = "100";     
            }
            else if(
                    intUseType.trim().equals("8B") ||       
                    intUseType.trim().equals("8E") ||       
                    intUseType.trim().equals("8F") ||       
                    intUseType.trim().equals("8D")      
                    )
            {
                strUseType = "200";     
            }

        }
        
        else
        {
        	
        	if(new UtiPower().checkSinoCIversion5(blEndorse.getBLPrpPmain().getArr(0).getComCode().substring(0, 2))){
	    		if("T".equals(strCarKindCode.substring(0,1))||strCarKindCode.equals("H1")||strCarKindCode.equals("H2")){
	    			if(intUseType.trim().equals("86") ||    
	    			   intUseType.trim().equals("87") ||    
	    			   intUseType.trim().equals("9D") ||    
	    			   intUseType.trim().equals("9E") ||    
	    			   intUseType.trim().equals("9F") ||    
	    			   intUseType.trim().equals("9G") ||    
	    			   intUseType.trim().equals("9B") ||    
	    			   intUseType.trim().equals("9C")       
	    			   )
	    			   {
	    			            strUseType = "100";     
	    			   }
	    			 else if(
	    			   intUseType.trim().equals("8B") ||       
	    			   intUseType.trim().equals("8E") ||       
	    			   intUseType.trim().equals("8F") ||       
	    			   intUseType.trim().equals("8D")          
	    			   )
	    			   {
	    			            strUseType = "200";     
	    			   }
	    		}
	    		else{
	    			strUseType = "000";
	    		}
	    	}else{
	    		strUseType = "000";
	    	}
        	
        	
        }

        return strUseType;
    }    
    
  
    public String generateTaxItem(PrpCPitemCarSchema prpCPitemCarSchema)
    {
        String carKindCode  = prpCPitemCarSchema.getCarKindCode();
        String seatCount    = prpCPitemCarSchema.getSeatCount();
        String exhaustScale = prpCPitemCarSchema.getExhaustScale();
        String taxItemCode = "";
        String taxItemDetailCode = "";
        
        if(carKindCode == null || "".equals(carKindCode.trim())){
            return "";
        }
        
        else if("H4".equals(carKindCode)){
            taxItemCode = "K";
        }
        else if("A".equals(carKindCode.substring(0,1))) {
            if((seatCount == null || "".equals(seatCount.trim()) || exhaustScale == null || "".equals(exhaustScale.trim()))){
                return "";
            }
            else if(Double.parseDouble(exhaustScale) <=1 ){
                taxItemDetailCode = "A0";
            }
            else if(Integer.parseInt(seatCount) <= 9){
                taxItemDetailCode = "A1";
            }
            else if(Integer.parseInt(seatCount) > 9 && Integer.parseInt(seatCount) < 20){
                taxItemDetailCode = "A2";
            }
            else if(Integer.parseInt(seatCount) >= 20){
                taxItemDetailCode = "A3";
            }
            taxItemCode = "A";
        }
        else if("G0,G1,G2,H0,TM,TP,TQ,TR,TS,TT,H3,H5,H6,H7,H8".indexOf(carKindCode) > -1 || carKindCode == "T11" || carKindCode == "T12"){
            taxItemCode = "H";
        }
        
        
        else if("M".equals(carKindCode.substring(0,1))){
            taxItemCode = carKindCode;
        }
        
        else if("T2,TF,TG,TH,TI,TJ".indexOf(carKindCode) > -1){
            taxItemCode = "T";
        }
        else if("G3,T1,T3,T4,T5,T6,T7,T8,T9,TC,TD,TE,TK,TL,TN,TO,TU,TV,TW,TX,TY,TZ,T10".indexOf(carKindCode) > -1){
            taxItemCode = "Z";
        }
        
        else if("H1,H11,H12".indexOf(carKindCode) > -1){
            taxItemCode = "D";
        }
        else if("H2,H21,H22".indexOf(carKindCode) > -1){
            taxItemCode = "S";
        }
        
        
        else if("J".equals(carKindCode.substring(0,1))){ 
        
            taxItemCode = "W";
        }
        
        if("A".equals(carKindCode.substring(0,1)))
            return taxItemDetailCode;
        else
            return taxItemCode;
    }
    
    private String correctDate(String dateString) {
        String result = StringUtils.replace(dateString, "-", "");
        return result;
    }
    private String encoderVehicleCategoryNew(BLEndorse blEndorse) throws Exception {

        
        String strVehicleCategory   = "";
        String strCarKindCode       = "";
        double exhaustScale         = 0;
        double toncount             = 0;
        int seatcount               = 0;
        String intUseType           = "";
        intUseType  = blPrpCPitemCar.getArr(0).getUseNatureCode();
        seatcount   = new Integer(blPrpCPitemCar.getArr(0).getSeatCount()).intValue();
        toncount = Double.parseDouble(blPrpCPitemCar.getArr(0).getTonCount());
        exhaustScale = Double.parseDouble(blPrpCPitemCar.getArr(0).getExhaustScale());
        strCarKindCode = blPrpCPitemCar.getArr(0).getCarKindCode();
		
		String strComCode = blEndorse.getBLPrpPhead().getArr(0).getComCode();
		
        
        if (strCarKindCode.equals("A0")||
            strCarKindCode.equals("A1")||
            strCarKindCode.equals("A2")||
            strCarKindCode.equals("A3")||
            strCarKindCode.equals("A4")) {
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
        
        
        
        else if (strCarKindCode.equals("H0")||
                  strCarKindCode.equals("H3")||
                  strCarKindCode.equals("H4")||
                  strCarKindCode.equals("H5")||
                  strCarKindCode.equals("H6")||
                  strCarKindCode.equals("H7")||
                  strCarKindCode.equals("H8")) {
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
        
        else if (strCarKindCode.equals("H1")||
                  strCarKindCode.equals("H2"))
        {
        	
        	if(new UtiPower().checkCIStartUp1001(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate()))
        	{
        		if(strCarKindCode.equals("H1")){
        			if(exhaustScale <=14.7){
        				strVehicleCategory = "BA";
        			}else if( exhaustScale > 14.7 && exhaustScale <= 17.6){
        				strVehicleCategory = "BB" ;
        			}else if(exhaustScale >17.6 && exhaustScale <= 50){
        				strVehicleCategory = "BC";
        			}else if(exhaustScale >50 && exhaustScale <= 80){
        				strVehicleCategory = "BD";
        			}else if(exhaustScale > 80){
        				strVehicleCategory = "BE";
        			}
        		}else if(strCarKindCode.equals("H2")){
        			if(exhaustScale <= 14.7){
        				strVehicleCategory = "CA";
        			}else if(exhaustScale >14.7 && exhaustScale <=17.6){
        				strVehicleCategory = "CB";
        			}else if(exhaustScale >17.6 && exhaustScale <=50){
        				strVehicleCategory = "CC";
        			}else if(exhaustScale > 50 && exhaustScale <= 80){
        				strVehicleCategory = "CD";
        			}else if(exhaustScale > 80){
        				strVehicleCategory = "CE";
        			}
        		}
        	}else{
            
			      if(SysConfig.getProperty("CI_SINOSOFT_0806").trim().indexOf(
					    strComCode.substring(0, 2).trim()) > -1){
				      if(strCarKindCode.equals("H1")){	
					      strVehicleCategory = "93";
				      }else if(strCarKindCode.equals("H2")){
					      strVehicleCategory = "94";
				      }
			      }else{
				      strVehicleCategory = "92";
			      }
			      
        	}
        	
        }
        
        else if (strCarKindCode.equals("H11") || 
                strCarKindCode.equals("H12") ||
                strCarKindCode.equals("H21")||
                strCarKindCode.equals("H22"))   
        {
			if(strCarKindCode.equals("H11")){	
				strVehicleCategory = "BF";
			}else if(strCarKindCode.equals("H12")){
				strVehicleCategory = "BG";
			}else if(strCarKindCode.equals("H21")){
				strVehicleCategory = "CF";
			}else if(strCarKindCode.equals("H22")){
				strVehicleCategory = "CG";
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
                 strCarKindCode.equals("TQ")  || 
                 strCarKindCode.equals("TR")) {
            strVehicleCategory = "30";  
        }
        
        
        else if ( strCarKindCode.equals("T10")||
                   strCarKindCode.equals("T11")||
                   strCarKindCode.equals("T1") ||
                   strCarKindCode.equals("T2") ||
                   strCarKindCode.equals("T7") ||
                   strCarKindCode.equals("TD") ||
                   strCarKindCode.equals("TE") ||
                   strCarKindCode.equals("TF") ||
                   strCarKindCode.equals("TG") ||
                   strCarKindCode.equals("TH") ||
                   strCarKindCode.equals("TK") ||
                   strCarKindCode.equals("TS") ||
                   strCarKindCode.equals("T12")||
                   strCarKindCode.equals("TL") ||
                   strCarKindCode.equals("TN")) {
            strVehicleCategory = "40";
        }
        
        
        else if ( strCarKindCode.equals("T8")||
                   strCarKindCode.equals("T6")||
                   strCarKindCode.equals("T4")||
                   strCarKindCode.equals("T5")||
                   strCarKindCode.equals("T3")||
                   strCarKindCode.equals("TC")||
                   strCarKindCode.equals("TO")||
                   strCarKindCode.equals("TI")||
                   strCarKindCode.equals("TV")||
                   strCarKindCode.equals("TW")||
                   strCarKindCode.equals("TX")||
                   strCarKindCode.equals("TY")) {
            strVehicleCategory = "50";
            
            UtiPower utiPower = new UtiPower();
            if(utiPower.checkStartUp0805(blEndorse.getBLPrpPmain().getArr(0).getOperateDate())&&strCarKindCode.equals("T3")){
                strVehicleCategory = "40";
            }
            
        }
        else if (strCarKindCode.equals("TT")) {
            
            strVehicleCategory = "60";
        } 
        
        
        else if ( strCarKindCode.equals("M1")||
                   strCarKindCode.equals("M5")) {
            
            if (exhaustScale >= 0 && exhaustScale <= 0.05) {
                strVehicleCategory = "71";
            } else if (exhaustScale > 0.05 && exhaustScale <= 0.25) {
                strVehicleCategory = "72";
            } else if (exhaustScale > 0.25) {
                strVehicleCategory = "73";
            }
            
            if("19".equals(strComCode.substring(0, 2))){
            	if(exhaustScale == 0.25){
            		strVehicleCategory = "73";
            	}
            }
            
        }
        
        else if(strCarKindCode.equals("M6"))
        {
            strVehicleCategory = "73";
        }
        
        
        
        else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J2"))&&intUseType.trim().equals("7A")) {   
            if (exhaustScale >= 0 && exhaustScale <= 14.7) {
                strVehicleCategory = "81";
            } else if (exhaustScale > 14.7) {
                strVehicleCategory = "82";
            }
        }
        else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J2"))&&intUseType.trim().equals("7B")) {
            
            if (exhaustScale >= 0 && exhaustScale <= 14.7) {
                strVehicleCategory = "91";
            } else if (exhaustScale > 14.7) {
                strVehicleCategory = "92";
            }
        }
        
        
        
        else if(strCarKindCode.equals("J3")){
        	if(exhaustScale <= 14.7){
        		strVehicleCategory = "9A";
        	}else if(exhaustScale >14.7 && exhaustScale <= 17.6){
        		strVehicleCategory = "9B";
        	}
        }
        
        else if(strCarKindCode.equals("J4")){
        	if(exhaustScale <= 14.7 ){
        		strVehicleCategory = "AA";
        	}else if(exhaustScale >14.7 && exhaustScale<= 17.6){
        		strVehicleCategory = "AB";
        	}else if(exhaustScale > 17.6 && exhaustScale <= 50){
        		strVehicleCategory = "AC";
        	}else if(exhaustScale > 50 && exhaustScale <=80){
        		strVehicleCategory = "AD";
        	}else if(exhaustScale > 80){
        		strVehicleCategory = "AE";
        	}
        }
        
        else if (strCarKindCode.equals("G1"))
        {
            strVehicleCategory = "31";
        }
        else if(strCarKindCode.equals("G2"))
        {
            strVehicleCategory = "41";
            
            
        }else if(strCarKindCode.equals("G3"))
        {
            strVehicleCategory = "51";
        }
		String strRiskCode = blEndorse.getBLPrpPmain().getArr(0).getRiskCode();
        if(new UtiPower().checkCIInsureBJ(strRiskCode, strComCode)){      
        	if(strCarKindCode.equals("T12")){
        	}else if(strCarKindCode.equals("H1")){      
        		strVehicleCategory = "29";
        	}else if(strCarKindCode.equals("TS")){        
        	}
        }
        return strVehicleCategory;
    }
    private String encoderVehicleCategory(BLEndorse blEndorse) throws Exception {

        String strVehicleCategory   = "";
        String strCarKindCode       = "";
        double exhaustScale         = 0;
        double toncount             = 0;
        int seatcount               = 0;
        String intUseType           = "";
		
		String strComCode = blEndorse.getBLPrpPmain().getArr(0).getComCode();
		
        intUseType  = blPrpCPitemCar.getArr(0).getUseNatureCode();
        seatcount   = new Integer(blPrpCPitemCar.getArr(0).getSeatCount()).intValue();
        toncount = Double.parseDouble(blPrpCPitemCar.getArr(0).getTonCount());
        
        
        
        exhaustScale = Double.parseDouble(blPrpCPitemCar.getArr(0).getExhaustScale());
        strCarKindCode = blPrpCPitemCar.getArr(0).getCarKindCode();
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

        } else if (strCarKindCode.equals("H0") ||
                   strCarKindCode.equals("H1") ||
                   strCarKindCode.equals("H2")) {
        	
			if(SysConfig.getProperty("CI_SINOSOFT_0806").trim().indexOf(
					strComCode.substring(0, 2).trim()) > -1 && (strCarKindCode.equals("H1")
				|| strCarKindCode.equals("H2"))){
				if(strCarKindCode.equals("H1")){	
					strVehicleCategory = "93";
				}else if(strCarKindCode.equals("H2")){
					strVehicleCategory = "94";
				}
			} else if (toncount >= 0 && toncount < 2) {
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
        else if (strCarKindCode.equals("TP") || strCarKindCode.equals("TQ")
                || strCarKindCode.equals("TR") || strCarKindCode.equals("TS")) {
            
            strVehicleCategory = "30";  
            
          
            
            
            
            
            
            
            
          
            
            
         }else if (strCarKindCode.equals("T1") ||
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
            
            if (exhaustScale >= 0 && exhaustScale < 14.7) {
                strVehicleCategory = "81";
            } else if (exhaustScale >= 14.7) {
                strVehicleCategory = "82";
            }
        }else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J1")||strCarKindCode.equals("J2")||strCarKindCode.equals("J3"))&&intUseType.trim().equals("7B")) {
            
            if (exhaustScale >= 0 && exhaustScale <= 14.7) {
                strVehicleCategory = "91";
            } else if (exhaustScale > 14.7) {
                strVehicleCategory = "92";
            }

        }else if (strCarKindCode.equals("G1"))          
        {
            strVehicleCategory = "31";
        }
        else if(strCarKindCode.equals("G2"))
        {
            strVehicleCategory = "41";
            
            
        }else if(strCarKindCode.equals("G3"))
        {
            strVehicleCategory = "51";
        }
        
        /*
         * switch (seatcount){ case 0: {
         *
         * break; } }
         */

        return strVehicleCategory;
    }
    private String translate(String InsuredTypeCode) throws Exception{
    	String strInsuredTypeCode = InsuredTypeCode;
    	if("01".equals(InsuredTypeCode)){
    		strInsuredTypeCode = "01";
    	}else if("03".equals(InsuredTypeCode)){
    		strInsuredTypeCode = "02";
    	}else if("04".equals(InsuredTypeCode)){
    		strInsuredTypeCode = "03";
    	}else if("99".equals(InsuredTypeCode)){
    		strInsuredTypeCode = "10";
    	}else{
    		strInsuredTypeCode = "99";
    	}
    	return strInsuredTypeCode;
    }
    public String vehicleStyleEncoder(String vehicleStyleEncoder,double TonCount){
        if("A0".equals(vehicleStyleEncoder)){
            return "K33";
        }else if("D".equals(vehicleStyleEncoder)){
            return "H51";
        }else if("H".equals(vehicleStyleEncoder)){
            if(TonCount<6)
               return "H31";
            else if(TonCount>=6&&TonCount<14)
               return "H21";
            else
               return "H11";
        }else if("K".equals(vehicleStyleEncoder)){
            return "H31";
        
        }else if("M1".equals(vehicleStyleEncoder)){
            return "M21";
        }else if("M5".equals(vehicleStyleEncoder)){
            return "M11";
        }else if("M6".equals(vehicleStyleEncoder)){
            return "M15";
        
        }else if("S".equals(vehicleStyleEncoder)){
            return "N11";
        }else if("T".equals(vehicleStyleEncoder)){
            return "J11";
        }else if("Z".equals(vehicleStyleEncoder)){
            return "Z11";
        }else if("A1".equals(vehicleStyleEncoder)){
            return "K31";
        }else if("A2".equals(vehicleStyleEncoder)){
            return "K21";
        }else if("A3".equals(vehicleStyleEncoder)){
            return "K11";
        
        }else if("A4,A5,A6,A7,A8,A9,A10".indexOf((vehicleStyleEncoder))>-1){
            return "K31";
        }else if("G".equals(vehicleStyleEncoder)){
            return "H11";
        }
        
        
        else if ("W".equals(vehicleStyleEncoder)){
        	return "X99";
        }
        
        return vehicleStyleEncoder;
    }
    
	
	public String carDriverIdentifyTypeEncoder(String IdentifyType){
		if("03".equals(IdentifyType)){
			return "02";
		}else if("04".equals(IdentifyType)){
			return "03";
		}else if("02,05,06,07".indexOf(IdentifyType)>-1){
			return "99";
		}
		return IdentifyType;
	}
	
	
	protected void addPHList(StringBuffer buf, BLEndorse blEndorse)throws Exception {
		String strPolicyHolder = "";
		String strCertiType = "";
		String strCertiCode = "";
		String strTelephone = "";
		BLCPolicy blPolicy= new BLCPolicy();
		blPolicy.getData(blEndorse.getBLPrpPmain().getArr(0).getPolicyNo());
		for (int i = 0; i < blPolicy.getBLPrpCPinsured().getSize(); i++) {
			
			if("2".equals(blPolicy.getBLPrpCPinsured().getArr(i).getInsuredFlag())){
				strPolicyHolder = blPolicy.getBLPrpCPinsured().getArr(i).getInsuredName();
				strCertiType = translate(blPolicy.getBLPrpCPinsured().getArr(i).getIdentifyType());
				strCertiCode = blPolicy.getBLPrpCPinsured().getArr(i).getIdentifyNumber();
				strTelephone = blPolicy.getBLPrpCPinsured().getArr(i).getMobile();
			}
		}
		
		buf.append("<PH_LIST> <PH_DATA>");
        addNode(buf, "POLICY_HOLDER", strPolicyHolder);     
        addNode(buf, "CERTI_TYPE", strCertiType);   
        addNode(buf, "CERTI_CODE", strCertiCode);   
        addNode(buf, "TELEPHONE", strTelephone);
        buf.append("</PH_DATA> </PH_LIST>");
	}
	protected void addInsuredList(StringBuffer buf, BLEndorse blEndorse)throws Exception {
		String strInsuredName = "";
		String strCertiType = "";
		String strCertiCode = "";
		String strTelephone = "";
		BLCPolicy blPolicy= new BLCPolicy();
		blPolicy.getData(blEndorse.getBLPrpPmain().getArr(0).getPolicyNo());
		for (int i = 0; i < blPolicy.getBLPrpCPinsured().getSize(); i++) {
			
			if("1".equals(blPolicy.getBLPrpCPinsured().getArr(i).getInsuredFlag())){
				strInsuredName = blPolicy.getBLPrpCPinsured().getArr(i).getInsuredName();
				strCertiType = translate(blPolicy.getBLPrpCPinsured().getArr(i).getIdentifyType());
				strCertiCode = blPolicy.getBLPrpCPinsured().getArr(i).getIdentifyNumber();
				strTelephone = blPolicy.getBLPrpCPinsured().getArr(i).getMobile();
			}
		}
		
		buf.append("<INSURED_LIST> <INSURED_DATA>");
        addNode(buf, "INSURED_NAME", strInsuredName);     
        addNode(buf, "CERTI_TYPE", strCertiType);   
        addNode(buf, "CERTI_CODE", strCertiCode);   
        addNode(buf, "TELEPHONE", strTelephone);
        buf.append("</INSURED_DATA> </INSURED_LIST>");
	}
	
}