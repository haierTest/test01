package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.schema.CIInsureDemandLossSchema;
import com.sp.indiv.ci.schema.CIInsureDemandPaySchema;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.prpall.blsvr.jf.BLPrpJpoaInfo;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFeePre;
import com.sp.prpall.schema.PrpJplanFeePreSchema;
import com.sp.prpall.schema.PrpJpoaInfoSchema;
import com.sp.sysframework.common.util.DataUtils;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ����XX�ɷ��������ݵĽ�����
 * 
 */
public class PolicyPayRecDecoder {

    public static void main(String[] args) 
    	throws Exception 
    {
    }
    
    String errorMessage = "";
    ArrayList arrayList = new ArrayList();

    /**
     * ����
     * @return XX�ɷ�����XML��ʽ��
     * @throws Exception
     */
	public void decode(DbPool dbPool, BLPrpJpoaInfo blPrpJpoaInfo,
			String content) throws Exception 
    {
    	InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        processHead(blPrpJpoaInfo,XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
        processBody(blPrpJpoaInfo, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
        
        saveData(dbPool, blPrpJpoaInfo);
    }

    /**
     * XXXXX������
     * @param blPrpJpoaInfo
     * @throws Exception
     */
    protected void saveData(DbPool dbPool, 
    		BLPrpJpoaInfo blPrpJpoaInfo) 
    	throws Exception 
    {
        	blPrpJpoaInfo.save(dbPool);
        	for(int i=0;i<blPrpJpoaInfo.getBLPrpJplanFeePre().getSize();i++){
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
    protected void processHead(BLPrpJpoaInfo blPrpJpoaInfo,Node node) throws Exception {
        String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
        errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
        blPrpJpoaInfo.getArr(0).setRemark(responseCode);
        blPrpJpoaInfo.getArr(0).setErrorMessage(errorMessage);
        /*Modify liuyongxin 2009-03-02 �Ϻ����ѳ�����XX�ɷѽӿڵ��� begin */
        if (!responseCode.equals("0")&&!responseCode.equals("1")&&!responseCode.equals("2")) {
            throw new Exception(errorMessage);
        }
        /*Modify liuyongxin 2009-03-02 �Ϻ����ѳ�����XX�ɷѽӿڵ��� end */
    }

    /**
     * ����BODY��
     * @param node Node
     * @throws Exception
     */
    protected void processBody(BLPrpJpoaInfo blPrpJpoaInfo,Node node) 
    	throws Exception 
    {
    	if(node==null){
    		throw new Exception(errorMessage);
    	}
        processBasePart(blPrpJpoaInfo, XMLUtils.getChildNodeByTagName(node, "BASE_PART"));
    }

    /**
     * ����BASE_PART��
     * @param node Node
     * @throws Exception
     */
    protected void processBasePart(BLPrpJpoaInfo blPrpJpoaInfo, Node node)
			throws Exception {
		String responseCode = blPrpJpoaInfo.getArr(0).getRemark();
		if (responseCode.equals("1") || responseCode.equals("2")) {
			processSucceedGroup(blPrpJpoaInfo, XMLUtils.getChildNodeByTagName(node, "SUCCEED_GROUP"));
		}
		if (responseCode.equals("0") || responseCode.equals("2")) {
			processLostGroup(blPrpJpoaInfo, XMLUtils.getChildNodeByTagName(node, "LOST_GROUP"));
		}
	}
    /**
	 * ����SUCCEED_GROUP��
	 * @param node Node
	 * @throws Exception
	 */
    protected void processSucceedGroup(BLPrpJpoaInfo blPrpJpoaInfo,Node node)throws Exception{
    	String strCircPaymentNo = XMLUtils.getChildNodeValue(node, "CIRC_PAYMENT_NO");
    	String strSumPremium = XMLUtils.getChildNodeValue(node, "SUM_PREMIUM");
    	String strPaymentStartDate = XMLUtils.getChildNodeValue(node, "PAYMENT_START_DATE");
    	String strPaymentEndDate = XMLUtils.getChildNodeValue(node, "PAYMENT_END_DATE");
    	strPaymentStartDate = correctDateTime(strPaymentStartDate);
    	strPaymentEndDate = correctDateTime(strPaymentEndDate);
    	blPrpJpoaInfo.getArr(0).setPoaCode(strCircPaymentNo);
    	blPrpJpoaInfo.getArr(0).setTotalFee(strSumPremium);
    	blPrpJpoaInfo.getArr(0).setPaymentStartDate(strPaymentStartDate);
    	blPrpJpoaInfo.getArr(0).setPaymentEndDate(strPaymentEndDate);
    	if(blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getCertiType().equals("T")){
    		
    		processPolicySucceedList(blPrpJpoaInfo,XMLUtils.getChildNodeByTagName(node, "POLICY_SUCCEED_LIST"));
    	}else if (blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getCertiType().equals("E")){
    		
    		processAmendSucceedList(blPrpJpoaInfo,XMLUtils.getChildNodeByTagName(node, "AMEND_SUCCEED_LIST"));
    	}else{
    		throw new UserException(-98, -1167, this.getClass().getName(),
					"���ܶԴ�ҵ�����ͽ��д���" + blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getCertiType());
    	}
    }
    /**
     * ����POLICY_SUCCEED_LIST��
     * @param node Node
     * @throws Exception
     */
    protected void processPolicySucceedList(BLPrpJpoaInfo blPrpJpoaInfo,Node node)throws Exception{
    	String proConfirmSequenceNo = "";
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "PROCONFIRM_SEQUENCE_NO");
    	for (int i = 0; i < nodes.length; i++) {
    		if (nodes[i].getFirstChild() != null) {
    			proConfirmSequenceNo = nodes[i].getFirstChild().getNodeValue();
    			for (int j=0;j<blPrpJpoaInfo.getBLPrpJplanFeePre().getSize();j++){
    				if(blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).getProConfirmSequenceNo().equals(proConfirmSequenceNo)){
    					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).setCertiStatus("1");
    					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).setPoaType(blPrpJpoaInfo.getArr(0).getPoaType());
    					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).setPoaCode(blPrpJpoaInfo.getArr(0).getPoaCode());
    					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).setPaymentStartDate(blPrpJpoaInfo.getArr(0).getPaymentStartDate());
    					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).setPaymentEndDate(blPrpJpoaInfo.getArr(0).getPaymentEndDate());
    				}
    			}
            }
    	}
    }
    
    /**
     * ����AMEND_SUCCEED_LIST��
     * @param node Node
     * @throws Exception
     */
    protected void processAmendSucceedList(BLPrpJpoaInfo blPrpJpoaInfo,Node node)throws Exception{
    	String amendQueryNo = "";
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "AMEND_QUERY_NO");
    	for (int i = 0; i < nodes.length; i++) {
    		if (nodes[i].getFirstChild() != null) {
    			amendQueryNo = nodes[i].getFirstChild().getNodeValue();
    			for (int j=0;j<blPrpJpoaInfo.getBLPrpJplanFeePre().getSize();j++){
    				if(blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).getProConfirmSequenceNo().equals(amendQueryNo)){
    					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).setCertiStatus("1");
    					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).setPoaCode(blPrpJpoaInfo.getArr(0).getPoaCode());
    					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).setPaymentStartDate(blPrpJpoaInfo.getArr(0).getPaymentStartDate());
    					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).setPaymentEndDate(blPrpJpoaInfo.getArr(0).getPaymentEndDate());
    				}
    			}
            }
    	}
    }
    
    /**
     * ����LOST_GROUP��
     * @param node Node
     * @throws Exception
     */
    protected void processLostGroup(BLPrpJpoaInfo blPrpJpoaInfo,Node node)throws Exception{
    	/*Modify liuyongxin 2009-03-02 �Ϻ����ѳ�����XX�ɷѽӿڵ��� begin */
    	if(blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getCertiType().equals("T")){
    		
    		processPolicyLostList(blPrpJpoaInfo, XMLUtils.getChildNodeByTagName(node, "POLICY_LOST_LIST"));
    	}else if (blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getCertiType().equals("E")){
    		
    		processPolicyLostList(blPrpJpoaInfo, XMLUtils.getChildNodeByTagName(node, "AMEND_LOST_LIST"));
    	}else{
    		throw new UserException(-98, -1167, this.getClass().getName(),
					"���ܶԴ�ҵ�����ͽ��д���" + blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getCertiType());
    	}
    	/*Modify liuyongxin 2009-03-02 �Ϻ����ѳ�����XX�ɷѽӿڵ��� end */
    	
    }
    
    /**
     * ����POLICY_LOST_LIST��
     * @param node Node
     * @throws Exception
     */
    protected void processPolicyLostList(BLPrpJpoaInfo blPrpJpoaInfo, Node node)
			throws Exception {
		Node[] nodes = XMLUtils.getChildNodesByTagName(node, "LOST_LIST");
		LostList lostList = new LostList();
		LostList lostListOld = new LostList();
    	String msg=" ";
    	boolean check=false;
		
		for (int i = 0; i < nodes.length; i++) {
			processLostList(blPrpJpoaInfo,nodes[i]);
		}

		/*Modify liuyongxin 2009-03-02 �Ϻ����ѳ�����XX�ɷѽӿڵ��� begin */
		if(blPrpJpoaInfo.getArr(0).getRemark().equals("0")){ 
			if(!arrayList.isEmpty()){
				for(int i=0;i<arrayList.size();i++){ 
					lostList=(LostList)arrayList.get(i);
					if(i>0){
						if(lostList.getStrCircPaymentNo()==null || lostList.getStrCircPaymentNo().length()==0){
							msg+="Ԥȷ���룺"+lostList.getStrProConFirmSequenceNo()+"-���׺�Ϊ��";
							check=true;
						}else{ 
							if(!lostListOld.getStrCircPaymentNo().equals(lostList.getStrCircPaymentNo())){
								msg+=" Ԥȷ����:"+lostList.getStrProConFirmSequenceNo()+"-���׺�:"+lostList.getStrCircPaymentNo();
								msg+="=Ԥȷ���룺"+lostListOld.getStrProConFirmSequenceNo()+"-���׺ţ�"+lostListOld.getStrCircPaymentNo();
								check=true;
							}
						}
					}
					if(i==0&&(lostList.getStrCircPaymentNo()==null || lostList.getStrCircPaymentNo().length()==0)){
						msg+="\nԤȷ���룺"+lostList.getStrProConFirmSequenceNo()+"-���׺�Ϊ��";
						check=true;
					}
					lostListOld=lostList;
				}
				if(check){
		    		throw new UserException(-98, -1167, this.getClass().getName(),
							"����XXԤȷ�����ƽ̨�����������������ύ����Ա��" + msg);
				}
				/*
				
				lostList=(LostList)arrayList.get(0);
				double sumPremium = Str.round(Double.parseDouble(lostList.getStrSumPremium()),2);
				double sumTotalFee = 0L; 
				double sumTotalFeestart = 0L;
				double sumTotalFeeend = 0L;
				for(int j=0;j<blPrpJpoaInfo.getBLPrpJplanFeePre().getSize();j++){
					sumTotalFee+=Str.round(Double.parseDouble(blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).getPlanFee()),2);
				}
				
				sumTotalFeestart=sumTotalFee-1.00;
				sumTotalFeeend=sumTotalFee+1.00;
				if((sumPremium>=sumTotalFeestart)&&(sumPremium<=sumTotalFeeend)){
					blPrpJpoaInfo.getArr(0).setPoaCode(lostList.getStrCircPaymentNo());
			    	blPrpJpoaInfo.getArr(0).setTotalFee(lostList.getStrSumPremium());
			    	blPrpJpoaInfo.getArr(0).setPaymentStartDate(lostList.getStrPaymentStartDate());
			    	blPrpJpoaInfo.getArr(0).setPaymentEndDate(lostList.getStrPaymentEndDate());
				}else{
					msg="���׺ţ�"+lostList.getStrCircPaymentNo()+"-���ܽ�"+lostList.getStrSumPremium();
		    		throw new UserException(-98, -1167, this.getClass().getName(),
							"ƽ̨�������Ӧ�Ļ��ܽ�������������ύ����Ա��" + msg);
				}*/
			}
		}
		if(blPrpJpoaInfo.getArr(0).getRemark().equals("2")){ 
			if(!arrayList.isEmpty()){
				msg="";
				for(int i=0;i<arrayList.size();i++){
					lostList=(LostList)arrayList.get(i);
				 	for (int j = 0; j < blPrpJpoaInfo.getBLPrpJplanFeePre().getSize(); j++) {
		    			if (blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).getProConfirmSequenceNo().equals(lostList.getStrProConFirmSequenceNo())){
		    				if(lostList.getStrCircPaymentNo()==null||lostList.getStrCircPaymentNo().length()==0){
		    					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).setCertiStatus("0");
		    				}else{
		    					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).setCertiStatus("0");
		    					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).setErrorMessage("���׺�:"+lostList.getStrCircPaymentNo());
		    				}
		    			}
				 	}
					
				}
			}
    		
		}
		/*Modify liuyongxin 2009-03-02 �Ϻ����ѳ�����XX�ɷѽӿڵ��� end */
	}
    
    /**
	 * ����LOST_LIST��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
    protected void processLostList(BLPrpJpoaInfo blPrpJpoaInfo,Node node)throws Exception{
    	String strErrorMessage	= XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
    	String strProConFirmSequenceNo	= XMLUtils.getChildNodeValue(node, "PROCONFIRM_SEQUENCE_NO");
        LostList lostList = new LostList();
    	/*Modify liuyongxin 2009-03-02 �Ϻ����ѳ�����XX�ɷѽӿڵ��� begin */
    	String strCircPaymentNo	= XMLUtils.getChildNodeValue(node, "CIRC_PAYMENT_NO");  
    	String strSumPremium	= XMLUtils.getChildNodeValue(node, "SUM_PREMIUM");      
    	String strPaymentStartDate	= XMLUtils.getChildNodeValue(node, "PAYMENT_START_DATE"); 
    	String strPaymentEndDate	= XMLUtils.getChildNodeValue(node, "PAYMENT_END_DATE");   
    	strPaymentStartDate = correctDateTime(strPaymentStartDate);
    	strPaymentEndDate = correctDateTime(strPaymentEndDate);
    	
        if(strProConFirmSequenceNo!=null&&strProConFirmSequenceNo.length()>0){
        	lostList.setStrCircPaymentNo(strCircPaymentNo);
        	lostList.setStrProConFirmSequenceNo(strProConFirmSequenceNo);
        	lostList.setStrSumPremium(strSumPremium);
        	lostList.setStrPaymentStartDate(strPaymentStartDate);
        	lostList.setStrPaymentEndDate(strPaymentEndDate);
        	arrayList.add(lostList);
        }

    	for (int i = 0; i < blPrpJpoaInfo.getBLPrpJplanFeePre().getSize(); i++) {
    		if(blPrpJpoaInfo.getArr(0).getRemark().equals("2")){ 
    			if (blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).getProConfirmSequenceNo().equals(strProConFirmSequenceNo)){
    				blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).setErrorMessage(strErrorMessage);
    				blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).setCertiStatus("0");
    			}
    		}else if(blPrpJpoaInfo.getArr(0).getRemark().equals("0")){ 
				if (blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).getProConfirmSequenceNo().equals(strProConFirmSequenceNo)
						&& (strCircPaymentNo!=null && !strCircPaymentNo.equals(""))){
					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).setPoaCode(strCircPaymentNo);	
					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).setCertiStatus("1");
					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).setPaymentStartDate(strPaymentStartDate);
					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).setPaymentEndDate(strPaymentEndDate);
				}
    		}
		}
    	/*Modify liuyongxin 2009-03-02 �Ϻ����ѳ�����XX�ɷѽӿڵ��� end */
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

class LostList{
	private String strProConFirmSequenceNo = "";
	private String strCircPaymentNo	= "";       
	private String strSumPremium = "";          
	private String strPaymentStartDate = "";    
	private String strPaymentEndDate = "";      
	
	public String getStrProConFirmSequenceNo() {
		return strProConFirmSequenceNo;
	}
	public void setStrProConFirmSequenceNo(String strProConFirmSequenceNo) {
		this.strProConFirmSequenceNo = strProConFirmSequenceNo;
	}
	public String getStrCircPaymentNo() {
		return strCircPaymentNo;
	}
	public void setStrCircPaymentNo(String strCircPaymentNo) {
		this.strCircPaymentNo = strCircPaymentNo;
	}
	public String getStrSumPremium() {
		return strSumPremium;
	}
	public void setStrSumPremium(String strSumPremium) {
		this.strSumPremium = strSumPremium;
	}
	public String getStrPaymentStartDate() {
		return strPaymentStartDate;
	}
	public void setStrPaymentStartDate(String strPaymentStartDate) {
		this.strPaymentStartDate = strPaymentStartDate;
	}
	public String getStrPaymentEndDate() {
		return strPaymentEndDate;
	}
	public void setStrPaymentEndDate(String strPaymentEndDate) {
		this.strPaymentEndDate = strPaymentEndDate;
	}
	
}
