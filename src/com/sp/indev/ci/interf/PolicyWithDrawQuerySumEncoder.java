package com.sp.indiv.ci.interf;

import java.util.Vector;

import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.dbsvr.DBCIInsureValid;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.Str;

/**
 * 发送退XXXXX查询请求数据的编码器
 *
 */
public class PolicyWithDrawQuerySumEncoder {

	/**
	 * 编码
	 *
	 * @return 退XXXXX查询请求XML格式串
	 * @throws Exception
	 */
	public String encode(DbPool dbPool,
						 BLEndorse blEndorse, 
						 String cancelReason, 
						 String startdate) 
		throws Exception 
	{
		
		StringBuffer buf = new StringBuffer(4000);
		getCIInsureValid(dbPool, blEndorse);		
		addXMLHead(buf);
		addPacket(buf, blEndorse,cancelReason,startdate);
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
	protected void addPacket(StringBuffer buf, BLEndorse blEndorse,String cancelReason,String startdate)
			throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");

		addHead(buf,blEndorse);
		addBody(buf, blEndorse,cancelReason,startdate);

		buf.append("</PACKET>");

	}

	/**
	 * 添加HEAD节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addHead(StringBuffer buf, BLEndorse blEndorse) throws Exception {
		
		
		
		
		

		String strComCode = blEndorse.getBLPrpPhead().getArr(0).getComCode();
    	buf.append("<HEAD>");
        addNode(buf, "REQUEST_TYPE", "13");
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
	protected void addBody(StringBuffer buf, BLEndorse blEndorse,String cancelReason,String startdate)
			throws Exception {
		buf.append("<BODY>");
		addBasePart(buf, blEndorse,cancelReason,startdate);
		
		if(new UtiPower().checkCIInsureSH(blEndorse.getBLPrpPhead().getArr(0).getRiskCode()
				, blEndorse.getBLPrpPhead().getArr(0).getComCode())){
		    addCancelList(buf, blEndorse);
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
	protected void addBasePart(StringBuffer buf, BLEndorse blEndorse,String cancelReason,String startdate)
			throws Exception {
		
		
		
		
		
		
		String validNo 			= ""; 
		String strcancelReason 	= ""; 
		String cancelPremium 	= ""; 
		String validDate 		= ""; 

		validNo = blEndorse.getBLCIInsureValid().getArr(0).getValidNo();
		strcancelReason = cancelReason;
	
	
	
		
		
		
		
		
	  
	    
		String temp = startdate;
		String strComCode  =  blEndorse.getBLPrpPhead().getArr(0).getComCode();
		
		if(strComCode.substring(0,2).equals("01") || strComCode.substring(0,2).equals("07"))
		{
			validDate = temp.substring(0,4) + temp.substring(5,7) + temp.substring(8,10);
		}
		else
		{
			validDate = temp.substring(0,4) + temp.substring(5,7) + temp.substring(8,10)+temp.substring(10,12);
		}
		

		buf.append("<BASE_PART>");
		addNode(buf, "CONFIRM_SEQUENCE_NO", validNo);
		addNode(buf, "CANCEL_REASON",strcancelReason);
		
		addNode(buf, "EFFECTIVE_TIME", validDate);
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














    	
    	String strCoverageType  = ""; 
    	String strCoverageCode  = ""; 
    	String strKindCode      = ""; 
    	String strChgPremium    = ""; 
    	String strRiskCode      = ""; 
    	int iKindCount = blEndorse.getBLPrpPitemKind().getSize();
    	for(int i = 0;i<iKindCount;i++){
    		if(!blEndorse.getBLPrpPhead().getArr(0).getRiskCode().equals("0507")){
    			strChgPremium = blEndorse.getBLPrpPitemKind().getArr(i).getChgPremium();
    			strChgPremium = "" + Str.round(Double.parseDouble(strChgPremium), 2);
    		}
    		strKindCode   = blEndorse.getBLPrpPitemKind().getArr(i).getKindCode();
    		strRiskCode   = blEndorse.getBLPrpPitemKind().getArr(i).getRiskCode();
    		strCoverageType = encodeCoverageType(strKindCode);
    		strCoverageCode = encodeCoverageCode(strKindCode, strRiskCode);

    		buf.append("<CANCEL_LIST>");
            addNode(buf, "COVERAGE_TYPE", strCoverageType); 
            addNode(buf, "COVERAGE_CODE", strCoverageCode); 
            addNode(buf, "COM_COVERAGE_CODE", strKindCode); 
            addNode(buf, "BUSI_CANCEL_PREMIUM", strChgPremium); 
            buf.append("</CANCEL_LIST>");
    	}
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
		try {
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
    
}
