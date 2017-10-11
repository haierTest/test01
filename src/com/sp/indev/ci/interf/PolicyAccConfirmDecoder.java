package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.prpall.blsvr.jf.BLPrpJpoaInfo;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaInfo;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.database.DbPool;

/**
 * 发送到账确认请求数据的解码器
 * 
 */
public class PolicyAccConfirmDecoder {

    public static void main(String[] args) 
    	throws Exception 
    {
    }
    
    String errorMessage = "";

    /**
     * 解码
     * @return 到账确认请求XML格式串
     * @throws Exception
     */
	public void decode(DbPool dbPool, BLPrpJpoaInfo blPrpJpoaInfo,
			String content) throws Exception 
    {
    	InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        int type = -1;
		try
		{
			type = processHead(blPrpJpoaInfo, XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
		}
		catch(Exception e)
		{
			throw new Exception("解析平台返回串错误。平台交易号：" + blPrpJpoaInfo.getArr(0).getPoaCode()	
		             + "，请与管理员联系！" + e.getMessage());
		}
		
		if(type != 1)
		{
			throw new Exception(blPrpJpoaInfo.getArr(0).getErrorMessage());
		}
		saveData(dbPool,blPrpJpoaInfo);
    }
	
    /**
     * XXXXX存数据
     * @param blPrpJpoaInfo
     * @throws Exception
     */
    protected void saveData(DbPool dbPool, 
    		BLPrpJpoaInfo blPrpJpoaInfo) 
    	throws Exception 
    {
        DateTime currentDate = new DateTime(DateTime.current(),DateTime.YEAR_TO_SECOND);
    	blPrpJpoaInfo.getArr(0).setAccDate(currentDate.toString());
    	
		blPrpJpoaInfo.getArr(0).setAccFlag("1");
		DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
		dbPrpJpoaInfo.setSchema(blPrpJpoaInfo.getArr(0));
		dbPrpJpoaInfo.update(dbPool);
	}
    
    /**
	 * 处理HEAD节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
    protected int processHead(BLPrpJpoaInfo blPrpJpoaInfo,Node node) throws Exception {
    	int type = 1;
        String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
        if (!responseCode.equals("1")) {
        	errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
        	blPrpJpoaInfo.getArr(0).setErrorMessage(errorMessage);
        	type = 0;
        }
        return type ;
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
