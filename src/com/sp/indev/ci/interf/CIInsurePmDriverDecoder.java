package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import com.sp.indiv.ci.schema.CIInsurePmDriverSchema;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.indiv.ci.blsvr.BLCIInsurePmDriver;

/**
 * 发送XX查询请求数据的解码器
 * add by fanjiangtao 2014-01-26
 */
public class CIInsurePmDriverDecoder {

	private static Logger logger = Logger.getLogger(CIInsurePmDriverDecoder.class);
    String errorMessage = "";
	
    /**
     * 解码
     * @return XX查询请求XML格式串
     * @throws Exception
     */
    public void decode(BLProposal blProposal,String content,String operateSite)throws Exception
    {
		logger.info("[交管驾驶员信息查询返回报文]:"+content);
    	InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        processHead(XMLUtils.getChildNodeByPath(document, "/Packet/Head"));
        processBody(blProposal, XMLUtils.getChildNodeByPath(document, "/Packet/Body"));
        saveRequestLog(blProposal,operateSite);
    }

    /**
     * XXXXX存请求日志
     * @param blProposal
     * @throws Exception
     */
    protected void saveRequestLog(BLProposal blProposal,String operateSite) throws Exception
    {
        try 
        {
        	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String strInsertDate = sf.format(new Date());
        	blProposal.getBlCIInsurePmDriver().getArr(0).setValidFlag("1");
        	blProposal.getBlCIInsurePmDriver().getArr(0).setInsertDate(strInsertDate);
        	blProposal.getBlCIInsurePmDriver().getArr(0).setFlag("T");
        	blProposal.getBlCIInsurePmDriver().getArr(0).setOperateSite(operateSite);
        	BLCIInsurePmDriver blCIInsurePmDriver = new BLCIInsurePmDriver();
        	blCIInsurePmDriver.getData(blProposal.getBlCIInsurePmDriver().getArr(0).getLicenseNo());
        	if(blCIInsurePmDriver.getSize()>0){
        		blProposal.getBlCIInsurePmDriver().cancel(blProposal.getBlCIInsurePmDriver().getArr(0).getLicenseNo());
        	}
        	blProposal.getBlCIInsurePmDriver().save();        		
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
            throw e;
        }
    }

    /**
     * 处理HEAD节
     * @param node Node
     * @throws Exception
     */
    protected void processHead(Node node) throws Exception {
        String responseCode = XMLUtils.getChildNodeValue(node, "ResponseCode");
        errorMessage = XMLUtils.getChildNodeValue(node, "ErrorMessage");
        if (!responseCode.equals("1")) {
            throw new Exception(errorMessage);
        }
    }

    /**
     * 处理BODY节
     * @param node Node
     * @throws Exception
     */
    protected void processBody(BLProposal blProposal, Node node) 
    	throws Exception 
    {
        processPmSpecifiedDriverDate(blProposal, XMLUtils.getChildNodeByTagName(node, "PmSpecifiedDriver"));
    }


    /**
     * 
     * @param blProposal
     * @param node
     * @throws Exception
     */
    protected void processPmSpecifiedDriverDate(BLProposal blProposal, Node node)
    	throws Exception
    {
        processPmSpecifiedDriver(blProposal, node);
    }

    /**
     * 处理PM_SPECIFIED_DRIVER节
     * @param BLProposal blProposal
     * @param node Node
     */
    protected void processPmSpecifiedDriver(BLProposal blProposal, Node node)
    	throws Exception
    {
    	try
        {
    		String strName = XMLUtils.getChildNodeValue(node, "Name");
    		String strDriverTypeCode = XMLUtils.getChildNodeValue(node, "DriverTypeCode");
    		String strLicenseNo = XMLUtils.getChildNodeValue(node, "LicenseNo");
    		String strLicenseStatusCode = XMLUtils.getChildNodeValue(node, "LicenseStatusCode");
    		String strLicensedDate = XMLUtils.getChildNodeValue(node, "LicensedDate");
    		
    		CIInsurePmDriverSchema schema = new CIInsurePmDriverSchema();  
    		schema.setName(strName);			
    		schema.setDriverTypeCode(strDriverTypeCode);			
    		schema.setLicenseNo(strLicenseNo);			
    		schema.setLicenseStatusCode(strLicenseStatusCode);			
    		schema.setLicensedDate(correctDate(strLicensedDate));
    		blProposal.getBlCIInsurePmDriver().setArr(schema);  
    		
        }
        catch(Exception e)
        {
        	throw e;
        }
    }

    /**
     * 纠正日期格式
     * @param dateString 8个字符的日期
     * @return YYYY-MM-DD形式的日期
     */
    private String correctDate(String dateString) {
    	if (dateString == null || dateString.equals("")){
    		return "";
    	}
        if (dateString.length() < 8) {
            throw new IllegalArgumentException(dateString + "的日期格式不对，必须为大于8位的纯数字的字符串");
        }
        String result = dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-"
                + dateString.substring(6, 8);
        return result;
    }

    /**
     * 纠正日期时间格式
     * @param dateString 修正前的日期时间
     * @return 修正后的日期时间
     */
    private String correctDateTime(String dateString) {
        String result = correctDate(dateString);
        if (dateString.length() >= 10) {
            result += " " + dateString.substring(8, 10);
        }
        if (dateString.length() >= 12) {
            result += ":" + dateString.substring(10, 12);
        }
        if (dateString.length() >= 14) {
            result += ":" + dateString.substring(12, 14);
        }
        return result;
    }
}
