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
 * 发送XX缴费请求数据的解码器
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
     * 解码
     * @return XX缴费请求XML格式串
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
     * XXXXX存数据
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
     * 处理HEAD节
     * @param node Node
     * @throws Exception
     */
    protected void processHead(BLPrpJpoaInfo blPrpJpoaInfo,Node node) throws Exception {
        String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
        errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
        blPrpJpoaInfo.getArr(0).setRemark(responseCode);
        blPrpJpoaInfo.getArr(0).setErrorMessage(errorMessage);
        /*Modify liuyongxin 2009-03-02 上海见费出单，XX缴费接口调整 begin */
        if (!responseCode.equals("0")&&!responseCode.equals("1")&&!responseCode.equals("2")) {
            throw new Exception(errorMessage);
        }
        /*Modify liuyongxin 2009-03-02 上海见费出单，XX缴费接口调整 end */
    }

    /**
     * 处理BODY节
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
     * 处理BASE_PART节
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
	 * 处理SUCCEED_GROUP节
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
					"不能对此业务类型进行处理：" + blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getCertiType());
    	}
    }
    /**
     * 处理POLICY_SUCCEED_LIST节
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
     * 处理AMEND_SUCCEED_LIST节
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
     * 处理LOST_GROUP节
     * @param node Node
     * @throws Exception
     */
    protected void processLostGroup(BLPrpJpoaInfo blPrpJpoaInfo,Node node)throws Exception{
    	/*Modify liuyongxin 2009-03-02 上海见费出单，XX缴费接口调整 begin */
    	if(blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getCertiType().equals("T")){
    		
    		processPolicyLostList(blPrpJpoaInfo, XMLUtils.getChildNodeByTagName(node, "POLICY_LOST_LIST"));
    	}else if (blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getCertiType().equals("E")){
    		
    		processPolicyLostList(blPrpJpoaInfo, XMLUtils.getChildNodeByTagName(node, "AMEND_LOST_LIST"));
    	}else{
    		throw new UserException(-98, -1167, this.getClass().getName(),
					"不能对此业务类型进行处理：" + blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(0).getCertiType());
    	}
    	/*Modify liuyongxin 2009-03-02 上海见费出单，XX缴费接口调整 end */
    	
    }
    
    /**
     * 处理POLICY_LOST_LIST节
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

		/*Modify liuyongxin 2009-03-02 上海见费出单，XX缴费接口调整 begin */
		if(blPrpJpoaInfo.getArr(0).getRemark().equals("0")){ 
			if(!arrayList.isEmpty()){
				for(int i=0;i<arrayList.size();i++){ 
					lostList=(LostList)arrayList.get(i);
					if(i>0){
						if(lostList.getStrCircPaymentNo()==null || lostList.getStrCircPaymentNo().length()==0){
							msg+="预确认码："+lostList.getStrProConFirmSequenceNo()+"-交易号为空";
							check=true;
						}else{ 
							if(!lostListOld.getStrCircPaymentNo().equals(lostList.getStrCircPaymentNo())){
								msg+=" 预确认码:"+lostList.getStrProConFirmSequenceNo()+"-交易号:"+lostList.getStrCircPaymentNo();
								msg+="=预确认码："+lostListOld.getStrProConFirmSequenceNo()+"-交易号："+lostListOld.getStrCircPaymentNo();
								check=true;
							}
						}
					}
					if(i==0&&(lostList.getStrCircPaymentNo()==null || lostList.getStrCircPaymentNo().length()==0)){
						msg+="\n预确认码："+lostList.getStrProConFirmSequenceNo()+"-交易号为空";
						check=true;
					}
					lostListOld=lostList;
				}
				if(check){
		    		throw new UserException(-98, -1167, this.getClass().getName(),
							"下列XX预确认码和平台交易码错误，请把数据提交管理员：" + msg);
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
					msg="交易号："+lostList.getStrCircPaymentNo()+"-汇总金额："+lostList.getStrSumPremium();
		    		throw new UserException(-98, -1167, this.getClass().getName(),
							"平台交易码对应的汇总金额错误，请把数据提交管理员：" + msg);
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
		    					blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(j).setErrorMessage("交易号:"+lostList.getStrCircPaymentNo());
		    				}
		    			}
				 	}
					
				}
			}
    		
		}
		/*Modify liuyongxin 2009-03-02 上海见费出单，XX缴费接口调整 end */
	}
    
    /**
	 * 处理LOST_LIST节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
    protected void processLostList(BLPrpJpoaInfo blPrpJpoaInfo,Node node)throws Exception{
    	String strErrorMessage	= XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
    	String strProConFirmSequenceNo	= XMLUtils.getChildNodeValue(node, "PROCONFIRM_SEQUENCE_NO");
        LostList lostList = new LostList();
    	/*Modify liuyongxin 2009-03-02 上海见费出单，XX缴费接口调整 begin */
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
    	/*Modify liuyongxin 2009-03-02 上海见费出单，XX缴费接口调整 end */
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
