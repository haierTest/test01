package com.sp.interactive.interf;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;


import com.sp.indiv.bi.pub.TransCode;
import com.sp.indiv.bj.NewCarRecordQueryEncoder;
import com.sp.indiv.ci.interf.ProposalQueryEncoder;
import com.sp.prpall.blsvr.cb.BLPrpDCarModelInfo;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.schema.NewCarRecordSchema;
import com.sp.prpall.schema.PrpTitemCarSchema;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.string.ChgData;


public class NewProposalInfoQueryEncoder {
	private static Logger logger = Logger.getLogger(ProposalQueryEncoder.class);
	/**
	 * 
	 * @param bLProposal
	 * @return
	 * @throws Exception
	 */
	
	public  String encode( Map paramMap,BLProposal  blProposal)throws Exception{
		   StringBuffer buf = new StringBuffer(4000);
	        addXMLHead(buf);
	        addPacket(buf, blProposal,paramMap);
	        
	        logger.info("[风XXXXXXX查询发送报文]:"+buf.toString());
	        
	        return buf.toString();
	
	}
	 /**
		 * 添加XML文件头
		 *
		 * @param buf
		 *            StringBuffer
		 */
	    protected void addXMLHead(StringBuffer buf) 
	    	throws Exception
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
	    protected void addPacket(StringBuffer buf, BLProposal blProposal,Map paramMap) 
	    	throws Exception 
	    {
	    	buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
	        addHead(buf, blProposal,paramMap);
	        addBody(buf, blProposal,paramMap);
	        buf.append("</PACKET>");

	    }
	    /**
		 * 添加HEAD节
		 *
		 * @param buf
		 *            StringBuffer
		 * @throws Exception
		 */
	    protected void addHead(StringBuffer buf,BLProposal blProposal, Map paramMap) 
	    	throws Exception 
	    {
	       
	    	String strComCode=(String)paramMap.get("COMCODE");
	    	buf.append("<HEAD>");
	        addNode(buf, "RequestType", "V43");
	        addNode(buf, "User", AppConfig.get("sysconst.BI_INSURED_" + strComCode.substring(0, 2) + "_USER"));
	        addNode(buf, "Password", AppConfig.get("sysconst.BI_INSURED_" + strComCode.substring(0, 2) + "_PASSWORD"));
	        buf.append("</HEAD>");
	    }

	    /**
		 * 添加BODY节
		 *
		 * @param buf
		 *            StringBuffer
		 * @throws Exception
		 */
	    protected void addBody(StringBuffer buf, BLProposal blProposal,Map paramMap) 
	    	throws Exception 
	    {
	        buf.append("<BODY>");
	        addBasePart(buf, blProposal,paramMap);
	        CoverageItem(buf, blProposal,paramMap);
	        buf.append("</BODY>");
	    }
	    protected void addBasePart(StringBuffer buf, BLProposal blProposal,Map paramMap) 
	        	throws Exception 
	        {
	    	String strModelCode = "";  
	    	String strBasicRateCode = "";  
	    	String strLicensePlateNo = "";  
	    	String strLicensePlateType = "";  
	    	String strEngineNo = "";  
	    	String strVIN = "";  
	    	String strMotorUsageTypeCode = "";  
	    	String strMotorTypeCode = "";  
	    	String strFirstRegisterDate = "";  
	    	String strEffectiveDate="";
	    	String strTonnage = "";  
	    	String strRatedPassengerCapacity = "";  
	    	String strNoticeType = "";  
	    	String strCarName = "";  
	    	String strReplacementValue="";
	    	
	    	strModelCode=(String)paramMap.get("VEHICLECODE");
            
			strBasicRateCode = "";
			PrpTitemCarSchema prpTitemCarSchema = new PrpTitemCarSchema();
			prpTitemCarSchema = blProposal.getBLPrpTitemCar().getArr(0);
            strLicensePlateNo=prpTitemCarSchema.getLicenseNo();
            strLicensePlateType=prpTitemCarSchema.getLicenseKindCode();
	    	strEngineNo=prpTitemCarSchema.getEngineNo();
	    	strVIN=prpTitemCarSchema.getVINNo();
	    	strMotorUsageTypeCode = TransCode.encoderUseTypeFG(blProposal);
	    	strMotorTypeCode=TransCode.encoderVehicleCategoryFG(blProposal);
	    	strFirstRegisterDate=correctDate(prpTitemCarSchema.getEnrollDate());
            strEffectiveDate=correctDate(blProposal.getBLPrpTmain().getArr(0).getStartDate())+"0000";
            strTonnage = ChgData.chgStrZero(prpTitemCarSchema.getTonCount()); 
    		if(strTonnage==null||"".equals(strTonnage)){
    			strTonnage="0";
    		}	
    		double doubleTonnage = Double.parseDouble(strTonnage) * 1000;
    		strTonnage = new DecimalFormat("0").format(doubleTonnage);
            strRatedPassengerCapacity=prpTitemCarSchema.getSeatCount();
			
	    	
            BLPrpDCarModelInfo blPrpDCarModelInfo = new BLPrpDCarModelInfo();
            blPrpDCarModelInfo.getData(strModelCode);
			if(blPrpDCarModelInfo.getSize()>0){
                strNoticeType = blPrpDCarModelInfo.getArr(0).getNoticeType();
				strCarName = blPrpDCarModelInfo.getArr(0).getCarName();
              }
	    	
	    	strReplacementValue=prpTitemCarSchema.getPurchasePrice();
	    	if("".equals(strReplacementValue)){
	    		strReplacementValue="0.00";
	    	}
	    	buf.append("<BasePart>");
	    	 addNode(buf, "ModelCode", strModelCode);
	         addNode(buf, "BasicRateCode", strBasicRateCode);
	         addNode(buf, "LicensePlateNo", strLicensePlateNo);
	         addNode(buf, "LicensePlateType", strLicensePlateType);
	         addNode(buf, "EngineNo", strEngineNo);
             addNode(buf, "VIN", strVIN);
	         addNode(buf, "MotorUsageTypeCode", strMotorUsageTypeCode);
	         addNode(buf, "MotorTypeCode", strMotorTypeCode);
	         addNode(buf, "FirstRegisterDate", strFirstRegisterDate);
	         addNode(buf, "EffectiveDate", strEffectiveDate);
	         addNode(buf, "Tonnage", strTonnage);
	         addNode(buf, "RatedPassengerCapacity", strRatedPassengerCapacity);
	         addNode(buf, "NoticeType", strNoticeType);
	         addNode(buf, "CarName", strCarName);
	         addNode(buf, "ReplacementValue", strReplacementValue);
	         buf.append("</BasePart>");
	        }
	    protected void CoverageItem(StringBuffer buf, BLProposal blProposal,Map paramMap) 
	        	throws Exception 
	        {
	    	    String strCoverageCode="";
	    	    buf.append("<CoverageItem>");
	    	    addNode(buf, "CoverageCode", "0101200");
	    	    buf.append("</CoverageItem>");
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
}
