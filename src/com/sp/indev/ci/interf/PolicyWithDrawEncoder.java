package com.sp.indiv.ci.interf;

import java.text.SimpleDateFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sp.indiv.ci.blsvr.*;
import com.sp.indiv.ci.dbsvr.*;
import com.sp.indiv.ci.schema.*;
import com.sp.prpall.blsvr.cb.BLPrpCPitemKind;
import com.sp.prpall.blsvr.cb.BLPrpCmain;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpCmainSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;

/**
 * 发送退XXXXX确认请求数据的编码器
 *
 */
public class PolicyWithDrawEncoder {
	
	
	private DbPool dbPool = null;
	
	
	private static Logger logger = Logger.getLogger(PolicyWithDrawEncoder.class);
	

	/**
	 * 编码
	 *
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
	public String encode(DbPool dbPool, BLEndorse blEndorse) throws Exception {
		
		this.dbPool = dbPool;
		
		
		StringBuffer buf = new StringBuffer(4000);
		
		getCIEndorValid(dbPool, blEndorse);		    
		addXMLHead(buf);
		addPacket(buf, blEndorse);
		
		
		logger.info("[XX退XXXXX发送报文]:"+buf.toString());
		
        
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
	protected void addPacket(StringBuffer buf, BLEndorse blEndorse)
			throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
		addHead(buf,blEndorse);
		addBody(buf, blEndorse);
		buf.append("</PACKET>");

	}

	/**
	 * 添加HEAD节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addHead(StringBuffer buf, 
						   BLEndorse blEndorse) 
		throws Exception 
	{

		String strComCode = blEndorse.getBLPrpPmain().getArr(0).getComCode();
    	buf.append("<HEAD>");
        addNode(buf, "REQUEST_TYPE", "14");
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
	protected void addBody(StringBuffer buf, BLEndorse blEndorse)
			throws Exception {
		buf.append("<BODY>");
		addBasePart(buf, blEndorse);
		
		
		if(new UtiPower().checkCIInsureSH(blEndorse.getBLPrpPhead().getArr(0).getRiskCode(), blEndorse.getBLPrpPhead().getArr(0).getComCode())
			||new UtiPower().checkCIInsureBJ(blEndorse.getBLPrpPhead().getArr(0).getRiskCode(), blEndorse.getBLPrpPhead().getArr(0).getComCode())){
		    addCancelList(buf, blEndorse);
		}
		
		
		
		BLPrpCmain blPrpCmain = new BLPrpCmain();
		blPrpCmain.getData(blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
		String strOperateDate = blPrpCmain.getArr(0).getOperateDate();
		if(new UtiPower().checkCarShipTaxJZ(blEndorse.getBLPrpPhead().getArr(0).getComCode(), strOperateDate)){
			 addVehicleTaxation(dbPool,buf,blEndorse);
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
	protected void addBasePart(StringBuffer buf, BLEndorse blEndorse)
			throws Exception 
    {
		UtiPower utiPower    = new UtiPower();
		String strDemanNo    = "";   
		String validNo       = "";  
		String strEndorseNo  = "";  
		String cancelPremium = "";  
		String strReason1    = "";  
		String strReason2    = "";  
		
		
		String strActualCancelTax = blEndorse.getBLCIEndorValid().getArr(0).getCancelTax();
		
		
		strDemanNo = blEndorse.getBLCIEndorValid().getArr(0).getDemandNo();        
		strEndorseNo = blEndorse.getBLCIEndorValid().getArr(0).getEndorseNo();     
		cancelPremium = "" + new Double(blEndorse.getBLCIEndorValid().getArr(0) .getChgPremium()).doubleValue();                               
		
		
		
		
		
		
		buf.append("<BASE_PART>");
		addNode(buf, "CANCEL_QUERY_NO", strDemanNo);             
		addNode(buf, "CANCEL_CONFIRM_NO", validNo);              
		addNode(buf, "CANCEL_POLICY_NO", strEndorseNo);          
		addNode(buf, "ACTUAL_CANCEL_PREMIUM", cancelPremium);    
		addNode(buf, "CHANGE_REASON_CODE", strReason1);          
		addNode(buf, "CHANGE_RENSON_DESC", strReason2);          
		
		String strRiskCode = blEndorse.getBLPrpPhead().getArr(0).getRiskCode();
		String strComCode  = blEndorse.getBLPrpPhead().getArr(0).getComCode();
		if(new UtiPower().checkCIInsureSH(strRiskCode, strComCode)){
			addNode(buf, "ACTUAL_CANCEL_TAX", strActualCancelTax);
		}
		
		
		if(utiPower.checkCICarshipTaxBJ(blEndorse.getBLPrpPhead().getArr(0).getRiskCode(), blEndorse.getBLPrpPhead().getArr(0).getComCode())){
			addNode(buf, "COMPUTER_CODE", AppConfig.get("sysconst.CI_INSURED_01_COMPUTER_CODE"));
		}
		
        
        if(utiPower.checkCICarShipTaxQG(blEndorse.getBLPrpPhead().getArr(0).getRiskCode(), blEndorse.getBLPrpPhead().getArr(0).getComCode().substring(0,2))){
        	
        	if(blEndorse.getBLPrpPcarshipTax().getSize()>0 
        			&& ("M".equals(blEndorse.getBLPrpPcarshipTax().getArr(0).getCarKindCode().substring(0, 1))
        					|| "J".equals(blEndorse.getBLPrpPcarshipTax().getArr(0).getCarKindCode().substring(0, 1)))
        			&& "Y".equals(blEndorse.getBLPrpPcarshipTax().getArr(0).getFinalFlag())){
        		addNode(buf,"TAX_ACTUAL_CANCEL_PREMIUM","0");
        	}else{
        		addNode(buf,"TAX_ACTUAL_CANCEL_PREMIUM",blEndorse.getBLCIEndorValid().getArr(0).getTaxAmendPremium());
        	}
            
        }
        
		buf.append("</BASE_PART>");
	}
	
	
	/**
     * 添加CANCEL_LIST节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addCancelList(StringBuffer buf, BLEndorse blEndorse)
            throws Exception {














    	
    	UtiPower utiPower = new UtiPower();
    	boolean isCIInsureBJ = utiPower.checkCIInsureBJ(blEndorse.getBLPrpPmain().getArr(0).getRiskCode(), 
    			blEndorse.getBLPrpPmain().getArr(0).getComCode());
    	
    	
    	String strCoverageType  = ""; 
    	String strCoverageCode  = ""; 
    	String strKindCode      = ""; 
    	String strChgPremium    = "0.0"; 
    	String strRiskCode      = ""; 
    	
    	String strCPPremium     = "";
    	
    	BLPrpCPitemKind blPrpCPitemKind = new BLPrpCPitemKind();
    	blPrpCPitemKind.getData(blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
    	int iKindCount = blPrpCPitemKind.getSize();
    	for(int i = 0;i<iKindCount;i++){
    		strKindCode   = blPrpCPitemKind.getArr(i).getKindCode();
    		strRiskCode   = blPrpCPitemKind.getArr(i).getRiskCode();
    		strCoverageType = encodeCoverageType(strKindCode);
    		
    		if(isCIInsureBJ){
				strCoverageCode = encodeCoverageCodeBJ(strKindCode, strRiskCode);
			}else{
				strCoverageCode = encodeCoverageCode(strKindCode, strRiskCode);
			}
    		strCPPremium = blPrpCPitemKind.getArr(i).getPremium();
    		if(!strRiskCode.equals("0507") && strCPPremium != null && !strCPPremium.equals("") && Double.parseDouble(strCPPremium) == 0){
    			
    			PrpCmainSchema prpCmainSchema = new PrpCmainSchema();
    			BLPrpCmain blPrpCmain = new BLPrpCmain();
    			blPrpCmain.getData(blPrpCPitemKind.getArr(i).getPolicyNo());
    			prpCmainSchema = blPrpCmain.getArr(0);
    			
    			String strPassTime = prpCmainSchema.getUnderWriteEndDate();
    			
    			DateTime passTime = new DateTime(strPassTime);
    			passTime = passTime.addDay(2);
    			String todayDate = blEndorse.getBLCIEndorValid().getArr(0).getValidTime(); 
    			if(!(prpCmainSchema.getComCode()!=null&&!"".equals(prpCmainSchema.getComCode())
    	    			&&("07").equals(prpCmainSchema.getComCode().substring(0, 2).trim())
    	    			&&prpCmainSchema.getChannelType()!=null&&!"".equals(prpCmainSchema.getChannelType())
    	    			&&(("N07").equals(prpCmainSchema.getChannelType().substring(0, 3).trim())
    	    			||("N10").equals(prpCmainSchema.getChannelType().substring(0, 3).trim()))
    	    			&&(PubTools.compareDate(todayDate, passTime.toString())<=0))
    			){
        			continue;
    			}
    			
    		}
    		
    		for(int j = 0;j<blEndorse.getBLPrpPitemKind().getSize();j++){
    			if(blPrpCPitemKind.getArr(i).getItemKindNo().equals(blEndorse.getBLPrpPitemKind().getArr(j).getItemKindNo())){
    				strChgPremium = blEndorse.getBLPrpPitemKind().getArr(j).getChgPremium();
    			}
    		}
    		buf.append("<CANCEL_LIST>");
            addNode(buf, "COVERAGE_TYPE", strCoverageType);            
            addNode(buf, "COVERAGE_CODE", strCoverageCode);            
            addNode(buf, "COM_COVERAGE_CODE", strKindCode);            
            addNode(buf, "ACTUAL_BUSI_CANCEL_PREMIUM", strChgPremium); 
            buf.append("</CANCEL_LIST>");
            strChgPremium = "0.0";
    	}
    	/*int iKindCount = blEndorse.getBLPrpPitemKind().getSize();
    	for(int i = 0;i<iKindCount;i++){
    		strKindCode   = blEndorse.getBLPrpPitemKind().getArr(i).getKindCode();
    		strRiskCode   = blEndorse.getBLPrpPitemKind().getArr(i).getRiskCode();
    		strCoverageType = encodeCoverageType(strKindCode);
    		strCoverageCode = encodeCoverageCode(strKindCode, strRiskCode);
    		strChgPremium = blEndorse.getBLPrpPitemKind().getArr(i).getChgPremium();
    		
    		buf.append("<CANCEL_LIST>");
            addNode(buf, "COVERAGE_TYPE", strCoverageType);            
            addNode(buf, "COVERAGE_CODE", strCoverageCode);            
            addNode(buf, "COM_COVERAGE_CODE", strKindCode);            
            addNode(buf, "ACTUAL_BUSI_CANCEL_PREMIUM", strChgPremium); 
            buf.append("</CANCEL_LIST>");
    	}*/
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
	 * 获得XX确认主信息
	 *
	 * @param BLEndorse
	 * @throws Exception
	 */
	private void getCIInsureValid(DbPool dbPool, 
								  BLEndorse blEndorse) 
		throws Exception 
	{
		try 
		{
			String sqlWhere = " Select * from CIInsureValid WHERE PolicyNo = '"
							  + blEndorse.getBLPrpPhead().getArr(0).getPolicyNo() + "'";
			DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
			Vector vector = dbCIInsureValid.findByConditions(dbPool, sqlWhere);
			CIInsureValidSchema cIInsureValidSchema = (CIInsureValidSchema) vector.get(0);
			BLCIInsureValid blCIInsureValid = new BLCIInsureValid();
			blCIInsureValid.setArr(cIInsureValidSchema);
			blEndorse.setBLCIInsureValid(blCIInsureValid);
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	private void getCIEndorValid(DbPool dbPool, 
			  BLEndorse blEndorse)
		throws Exception 
	{
		try 
		{
			String sqlWhere = " Select * from CIEndorValid WHERE EndorseNo = '"
							  + blEndorse.getBLPrpPhead().getArr(0).getEndorseNo() + "'";
			DBCIEndorValid dbCIEndorValid = new DBCIEndorValid();
			Vector vector = dbCIEndorValid.findByConditions(dbPool, sqlWhere);
			CIEndorValidSchema cIEndorValidSchema = (CIEndorValidSchema) vector.get(0);
			BLCIEndorValid blCIEndorValid = new BLCIEndorValid();
			blCIEndorValid.setArr(cIEndorValidSchema);
			blEndorse.setBLCIEndorValid(blCIEndorValid);
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			throw ex;
		}
	}
	
	
	/**
     * 平台XXXXX种代码转换
     * @param strKindCode XX的XXXXX别
     * @param strRiskCode XXXXX种
     * @return
     * @throws Exception
     */
    private String encodeCoverageCode(String strKindCode, String strRiskCode) throws Exception{
    	String strCoverageCode = "";
    	if("0509".equals(strRiskCode)){
    		if("BZ".equals(strKindCode)){
        		strCoverageCode = "A02";
        	 }else if("A".equals(strKindCode)){
        		strCoverageCode = "C11";
        	 }else if("B".equals(strKindCode)){
        		strCoverageCode = "B11";
        	 }else if("G1".equals(strKindCode)){
        		strCoverageCode = "D11";
        	 }else if("D3".equals(strKindCode) || "D4".equals(strKindCode)){
        		strCoverageCode = "E11";
        	 }else{
        		strCoverageCode = "F11";
        	 }
    	}else{
    	     if("BZ".equals(strKindCode)){
    		    strCoverageCode = "A02";
    	     }else if("A".equals(strKindCode)){
    		    strCoverageCode = "C01";
    	     }else if("B".equals(strKindCode)){
    		    strCoverageCode = "B01";
    	     }else if("G1".equals(strKindCode)){
    		    strCoverageCode = "D01";
    	     }else if("D3".equals(strKindCode) || "D4".equals(strKindCode)){
    		    strCoverageCode = "E01";
    	     }else{
    		    strCoverageCode = "F01";
    	     }
    	}
    	return strCoverageCode;
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
    
    public void addVehicleTaxation(DbPool dbPool,StringBuffer buf, BLEndorse blEndorse) throws Exception {
        EndorseCarShipTaxValidEncoder endorseCarShipTaxValidEncoder = new EndorseCarShipTaxValidEncoder();
        endorseCarShipTaxValidEncoder.encode(dbPool,blEndorse,buf,"VehicleTaxation");
    }
    
    

}
