package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;

import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgDate;


/**
 * ����XX��ѯ�������ݵĽ�����
 * 
 */
public class ProposalPreAffirmDecoder {

    public static void main(String[] args) 
    	throws Exception 
    {

    }
    
    String errorMessage = "";

    /**
     * ����
     * @return XX��ѯ����XML��ʽ��
     * @throws Exception
     */
    public void decode(DbPool dbPool,
    				   BLProposal blProposal, 
    				   String content) 
    	throws Exception 
    {
    	InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
        processBody(dbPool,blProposal, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY/BASE_PART/SUCCEED_GROUP"));
    }
   
    /**
     * ����HEAD��
     * @param node Node
     * @throws Exception
     */
    protected void processHead(Node node) throws Exception {
        
        String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
        errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
        if (!responseCode.equals("1")) {
            throw new Exception(errorMessage);
        }
    }

    /**
     * ����BODY��
     * @param node Node
     * @throws Exception
     */
    protected void processBody(DbPool dbPool,BLProposal blProposal, Node node) 
    	throws Exception 
    {
        processSucceedList(dbPool,blProposal,XMLUtils.getChildNodeByTagName(node, "SUCCEED_LIST"));
    }
    /**
     * ����SUCCEED_LIST�ڵ�
     * @param node Node
     * @throws Exception
     */
    protected void processSucceedList(DbPool dbPool,BLProposal blProposal, Node node) throws Exception
    {
       String ProconfirmSequenceNo = XMLUtils.getChildNodeValue(node, "PROCONFIRM_SEQUENCE_NO");
       String ProconfirmStartDate  = XMLUtils.getChildNodeValue(node, "PROCONFIRM_START_DATE");
       String ProconfirmEndDate    = XMLUtils.getChildNodeValue(node, "PROCONFIRM_END_DATE"); 
       String strStartDate         = "";
       String strEndDate           = "";
       strStartDate                = correctDateTime(ProconfirmStartDate);
       strEndDate                  = correctDateTime(ProconfirmEndDate);
       
       CIInsureDemandSchema Schema = blProposal.getBLCIInsureDemand().getArr(0);
       Schema.setProconfirmSequenceNo(ProconfirmSequenceNo);
       Schema.setProconfirmStartDate(strStartDate);
       Schema.setProconfirmEndDate(strEndDate);
       blProposal.getBLCIInsureDemand().setArr(Schema);
       String DemandNo = blProposal.getBLCIInsureDemand().getArr(0).getDemandNo();
       
       String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
       if ("1".equals(strSwitch)) {
    	   DbPool dbpoolNew = new DbPool();
    	   try {
    		   dbpoolNew.open("platformNewDataSource");
    		   dbpoolNew.beginTransaction();
    	       blProposal.getBLCIInsureDemand().update(dbpoolNew, DemandNo,ProconfirmSequenceNo,strStartDate,strEndDate);
    	       dbpoolNew.commitTransaction();
    	   } catch (Exception e) {
    		   dbpoolNew.rollbackTransaction();
    		   e.printStackTrace();
    		   throw e;
    	   } finally {
    		   dbpoolNew.close();
    	   }
       } else {
           blProposal.getBLCIInsureDemand().update(dbPool, DemandNo,ProconfirmSequenceNo,strStartDate,strEndDate);
       }
       
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