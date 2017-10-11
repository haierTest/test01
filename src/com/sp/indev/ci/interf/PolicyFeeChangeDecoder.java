package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.schema.CIInsureDemandLossSchema;
import com.sp.indiv.ci.schema.CIInsureDemandPaySchema;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.prpall.blsvr.jf.BLPrpJpoaInfo;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFeePre;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaInfo;
import com.sp.prpall.schema.PrpJplanFeePreSchema;
import com.sp.prpall.schema.PrpJpoaInfoSchema;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgDate;

/**
 * ���ͽɷѸ����������ݵĽ�����
 * 
 */
public class PolicyFeeChangeDecoder {

    public static void main(String[] args) 
    	throws Exception 
    {
    }
    
    String errorMessage = "";

    /**
     * ����
     * @return �ɷѸ�������XML��ʽ��
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
			throw new Exception("����ƽ̨���ش�����ƽ̨���׺ţ�" + blPrpJpoaInfo.getArr(0).getPoaCode()	
		             + "���������Ա��ϵ��" + e.getMessage());
		}
		
		if(type != 1)
		{
			throw new Exception(blPrpJpoaInfo.getArr(0).getErrorMessage());
		}
		saveData(dbPool, blPrpJpoaInfo);
    }
	
    /**
     * XXXXX������
     * @param blPrpJpoaInfo
     * @throws Exception
     */
    protected void saveData(DbPool dbPool, BLPrpJpoaInfo blPrpJpoaInfo) 
    	throws Exception 
    {
    	
    	blPrpJpoaInfo.getArr(0).setAccFlag("3");
    	DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
		dbPrpJpoaInfo.setSchema(blPrpJpoaInfo.getArr(0));
		dbPrpJpoaInfo.update(dbPool);
    	for(int i=0;i<blPrpJpoaInfo.getBLPrpJplanFeePre().getSize();i++){
    		
    		blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).setCertiStatus("0");
    		
    		blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).setPoaType("");
    		DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
    		dbPrpJplanFeePre.setSchema(blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i));
    		dbPrpJplanFeePre.update(dbPool);
    	}
	}
    
    /**
     * ����HEAD��
     * @param node Node
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
     * �������ڸ�ʽ
     * @param dateString 8���ַ�������
     * @return YYYY-MM-DD��ʽ������
     */
    private String correctDate(String dateString) {
    	if (dateString == null || dateString.equals("")){
    		return "";
    	}
        if (dateString.length() < 8) {
            throw new IllegalArgumentException(dateString + "�����ڸ�ʽ���ԣ�����Ϊ����8λ�Ĵ����ֵ��ַ���");
        }
        String result = dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-"
                + dateString.substring(6, 8);
        return result;
    }

    /**
     * ��������ʱ���ʽ
     * @param dateString ����ǰ������ʱ��
     * @return �����������ʱ��
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
