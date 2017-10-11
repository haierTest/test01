package com.sp.indiv.ci.interf;

import java.text.DecimalFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.dbsvr.DBCIInsureValid;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.cb.BLPrpCitemCar;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.Str;

/**
 * ������XXXXX��ѯ�������ݵı�����
 *
 */
public class PolicyWithDrawQueryEncoder {

	
	private static Logger logger = Logger.getLogger(PolicyWithDrawQueryEncoder.class);
	
	
	/**
	 * ����
	 *
	 * @return ��XXXXX��ѯ����XML��ʽ��
	 * @throws Exception
	 */
	public String encode(DbPool dbPool, 
						 BLEndorse blEndorse,
			 			 String cancelReason) 
		throws Exception 
	{
		
		StringBuffer buf = new StringBuffer(4000);
		getCIInsureValid(dbPool, blEndorse);	
		addXMLHead(buf);
		addPacket(buf, blEndorse,cancelReason);
		
		logger.info("[XX��XXXXX��ѯ���ͱ���]:"+buf.toString());
		
		return buf.toString();
	}

	/**
	 * ���XML�ļ�ͷ
	 *
	 * @param buf
	 *            StringBuffer
	 */
	protected void addXMLHead(StringBuffer buf) {
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
	}

	/**
	 * ���PACKET��
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addPacket(StringBuffer buf, BLEndorse blEndorse,String cancelReason)
			throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");

		addHead(buf,blEndorse);
		addBody(buf, blEndorse,cancelReason);

		buf.append("</PACKET>");

	}

	/**
	 * ���HEAD��
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addHead(StringBuffer buf, 
						   BLEndorse blEndorse) 
		throws Exception 
	{
		String strComCode = blEndorse.getBLPrpPhead().getArr(0).getComCode();
    	buf.append("<HEAD>");
        addNode(buf, "REQUEST_TYPE", "13");
        addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_USER"));
        addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_PASSWORD"));
        buf.append("</HEAD>");
	}

	/**
	 * ���BODY��
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBody(StringBuffer buf, BLEndorse blEndorse,String cancelReason)
			throws Exception {
		buf.append("<BODY>");
		addBasePart(buf, blEndorse,cancelReason);
		
		
		String strRiskCode = blEndorse.getBLPrpPhead().getArr(0).getRiskCode();
		String strComCode = blEndorse.getBLPrpPhead().getArr(0).getComCode();
		UtiPower utiPower = new UtiPower();
		if(utiPower.checkCIInsureSH(strRiskCode,strComCode)||utiPower.checkCIInsureBJ(strRiskCode,strComCode)){
		    addCancelList(buf, blEndorse);
		}
		
		
		buf.append("</BODY>");
	}

	/**
	 * ���BASE_PART��
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBasePart(StringBuffer buf, BLEndorse blEndorse,String cancelReason)
			throws Exception {
		
		
		
		
		
		
		String strComCode		= "";		
		String validNo 			= ""; 		
		String strcancelReason 	= ""; 		
		String cancelPremium 	= ""; 		
		String validDate 		= ""; 		
		
		String strRestricFlag   = "";       
		
		
		strComCode	= blEndorse.getBLPrpPhead().getArr(0).getComCode();
		validNo 	= blEndorse.getBLCIInsureValid().getArr(0).getValidNo();
		strcancelReason = cancelReason;
	
	
	
		String temp = blEndorse.getBLPrpPhead().getArr(0).getValidDate();	
		validDate 	= temp.substring(0,4) + temp.substring(5,7) + temp.substring(8,10);
		
		
		BLPrpCitemCar blPrpCitemCar = new BLPrpCitemCar();
		blPrpCitemCar.getData(blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
		strRestricFlag = blPrpCitemCar.getArr(0).getRestricFlag();
		if(strRestricFlag!=null && strRestricFlag.trim().equals("00")){
			strRestricFlag = "";
        }
		

		buf.append("<BASE_PART>");
		addNode(buf, "CONFIRM_SEQUENCE_NO", validNo);	
		addNode(buf, "CANCEL_REASON",strcancelReason);	
		
		
		
		if(strComCode.substring(0,2).equals("01") || 
           strComCode.substring(0,2).equals("07"))
		{
			addNode(buf, "EFFECTIVE_TIME", validDate);
		}
		else 
		{
			
			addNode(buf, "EFFECTIVE_TIME", validDate + "00");
		}
		
		
		if(strComCode.substring(0,2).equals("01")){
			addNode(buf, "RESTRIC_FLAG", strRestricFlag);  
		}
		
		buf.append("</BASE_PART>");
	}

	
	/**
     * ���CANCEL_LIST��
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
    	
    	
    	String strPremium       = ""; 
    	int iKindCount = blEndorse.getBLPrpPitemKind().getSize();
    	for(int i = 0;i<iKindCount;i++){
    		if(!blEndorse.getBLPrpPhead().getArr(0).getRiskCode().equals("0507")){
    			strChgPremium = blEndorse.getBLPrpPitemKind().getArr(i).getChgPremium();
    			strPremium    = blEndorse.getBLPrpPitemKind().getArr(i).getPremium();
    			if(Double.parseDouble(strChgPremium) == 0 && Double.parseDouble(strPremium) == 0){
        			continue;
        		}
    			strChgPremium = "" + Str.round(Double.parseDouble(strChgPremium), 2);
    		}
    		strKindCode   = blEndorse.getBLPrpPitemKind().getArr(i).getKindCode();
    		strRiskCode   = blEndorse.getBLPrpPitemKind().getArr(i).getRiskCode();
    		strCoverageType = encodeCoverageType(strKindCode);
    		
    		if(new UtiPower().checkCIInsureBJ(strRiskCode, blEndorse.getBLPrpPmain().getArr(0).getComCode())){
				strCoverageCode = encodeCoverageCodeBJ(strKindCode, strRiskCode);
			}else{
				strCoverageCode = encodeCoverageCode(strKindCode, strRiskCode);
			}
    		
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
	 * ���XXȷ������Ϣ
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
	
	
	/**
     * ƽ̨XXXXX�ִ���ת��
     * @param strKindCode XX��XXXXX��
     * @param strRiskCode XXXXX��
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
     * ƽ̨XXXXX�ִ���ת��
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
    

}
