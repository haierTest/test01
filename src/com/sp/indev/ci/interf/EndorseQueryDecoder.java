package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.bi.pub.TransCode;
import com.sp.indiv.ci.schema.CICarShipTaxQGDemandSchema;
import com.sp.indiv.ci.schema.CICarShipTaxQGEndorseSchema;

import com.sp.indiv.ci.schema.CIEndorValidSchema;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.indiv.ci.schema.PrpCIEndorClaimDetailSchema;
import com.sp.indiv.ci.schema.PrpCIEndorClaimSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgDate;


/**
 * �������Ĳ�ѯ�������ݵĽ�����
 * 
 */
public class EndorseQueryDecoder {
	/*modi by liujia start
	 * reason:��¼ƽ̨������Ϣ����ƽ̨׼���ظ�XX����Ϊ��ʱ����������������XXҪ��������Ϣ��¼���׳��쳣*/
    String  errorMessage="";
	/*modi by liujia end*/
	public static void main(String[] args) throws Exception 
	{
	}
	
	private static Logger logger = Logger.getLogger(EndorseQueryDecoder.class);
	
	/**
	 * ����
	 * 
	 * @return XX��ѯ����XML��ʽ��
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, 
					   BLPolicy blPolicy, 
					   String content)
		throws Exception 
	{
		
		logger.info("[���Ĳ�ѯ���ر���]:"+content);
		
	
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"), blPolicy);
		processBody(blPolicy, XMLUtils.getChildNodeByPath(document,"/PACKET/BODY"));
		
		saveRequestLog(dbPool, blPolicy);
	}
	
	/**
	 * ����
	 * @author liuweichang-ghq 20140624 ���Ӳ���dbpool�Ľ��뷽��
	 * @return XX��ѯ����XML��ʽ��
	 * @throws Exception
	 */
	public void decodeProcess(BLPolicy blPolicy, 
			String content)
	throws Exception 
	{
		
		logger.info("[���Ĳ�ѯ���ر���]:"+content);
		
		
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"), blPolicy);
		processBody(blPolicy, XMLUtils.getChildNodeByPath(document,"/PACKET/BODY"));
	}
	
	/**
	 * XXXXX��ƽ̨���ؽ��
	 * @author liuweichang-ghq 20140624 ����XXXXX��ƽ̨���ؽ������
	 * @return XX��ѯ����XML��ʽ��
	 * @throws Exception
	 */
	public void decodeSave(DbPool dbPool, 
			BLPolicy blPolicy)
	throws Exception 
	{
		
		saveRequestLog(dbPool, blPolicy);
	}
	
	

	/**
	 * XXXXX��������־
	 * 
	 * @param blProposal
	 * @throws Exception
	 */
	protected void saveRequestLog(DbPool dbPool, BLPolicy blPolicy)
			throws Exception 
	{
		
		blPolicy.getBLCIEndorValid().save(dbPool);
		/*modi by liujia start
		 * reason:XX��ѯ������������*/
		blPolicy.getBLPrpCIEndorClaim().save(dbPool);
		blPolicy.getBLPrpCIEndorClaimDetail().save(dbPool);
        UtiPower utiPower = new UtiPower();
        if(utiPower.checkCICarShipTaxQG(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())
        		||utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(),blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
        	blPolicy.getBLCICarShipTaxQGEndorse().save(dbPool);
        }
		/*modi by liujia end*/
	}

	/**
	 * ����HEAD��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processHead(Node node, BLPolicy blPolicy) 
		throws Exception 
	{
        String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
        /*modi by liujia start
         * reason:��ԭ����������Ƴ�������Ϊƽ̨�ظ�XX����Ϊ��ʱ�������ظ�XX����Ҳ��������,���ǻ��о�����ϢҪ���û���*/
        errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
        if (!responseCode.equals("1")) {
            throw new Exception(errorMessage);
        }
       
	}

	/**
	 * ����BODY��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBody(BLPolicy blPolicy, Node node) throws Exception {
		processBasePart(blPolicy, XMLUtils.getChildNodeByTagName(node, "BASE_PART"));
        /**<<<<<< added by harry on 24/08/07 ��������XXXXX�б��ظ�XX��־**/
		/*modi by liujia start
		 * reason:������ü��ж������XXXXX�Ϻ����е�����*/
		String tempComcode=blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2);
		if(blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2).trim().equals("01") ||
				   blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2).trim().equals("07"))
		{
					
		}else{
			if(SysConfig.getProperty("EXPERIMENTALCOM").trim().indexOf(tempComcode.substring(0,2))>-1){
		      processCoverageList(blPolicy, XMLUtils.getChildNodeByTagName(node, "COVERAGE_LIST"));
              processClaimList(blPolicy, XMLUtils.getChildNodeByTagName(node, "CLAIM_LIST"));
              
              UtiPower utiPower = new UtiPower();
              
              if(utiPower.checkCICarShipTaxQG(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())
                      &&blPolicy.getBLCIInsureQuery().getSize()>0
                      &&blPolicy.getBLCIInsureQuery().getArr(0).getIsAmendTax().equals("1")){
                  
                  if(utiPower.isCarShipTaxNB(blPolicy.getBLPrpCmain().getArr(0).getComCode())){
                	  processVehicleTaxation(blPolicy, XMLUtils.getChildNodeByTagName(node, "VehicleTaxation_NB"));
                  }else{
                	  processVehicleTaxation(blPolicy, XMLUtils.getChildNodeByTagName(node, "VehicleTaxation"));
                  }
                  
                  
              }
              if(utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
            	  processVehicleTaxation(blPolicy, XMLUtils.getChildNodeByTagName(node, "VehicleTaxation"));
            	  blPolicy.getBLCIEndorValid().getArr(0).setTaxAmendDeclare(blPolicy.getBLCICarShipTaxQGEndorse().getArr(0).getDeclareStatusIA());
            	  blPolicy.getBLCIEndorValid().getArr(0).setTaxAmendPremium(blPolicy.getBLCICarShipTaxQGEndorse().getArr(0).getSumTax());
              }
             
              
              
              
              SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
              String nowTime=format.format(new Date());
              if(utiPower.checkSinoCIversion3(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate()) 
            		  || utiPower.checkSinoCarPlatformSZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), nowTime)){
            	  Node[] nodes = XMLUtils.getChildNodesByTagName(node, "OWNER_MESSAGE");
            	  if(nodes !=null && nodes.length > 0){
            		  processOwnerMessage(blPolicy,XMLUtils.getChildNodeByTagName(node, "OWNER_MESSAGE"));
            	  }
              }
              
              
			}
        }
		/*modi by liujia end
		* reason:������ü��ж������XXXXX�Ϻ����е�����*/
        /** added by harry on 24/08/07 ��������XXXXX�б��ظ�XX��־ >>>>>>**/
	    
		if (new UtiPower().savePlatformAdjustSwitchJS(tempComcode)) {
			Node[] nodes = XMLUtils.getChildNodesByTagName(node, "PM_VEHICLE");
			if(nodes !=null && nodes.length > 0){
				processPmVehicle(blPolicy,XMLUtils.getChildNodeByTagName(node, "PM_VEHICLE"));
  			}
			nodes = XMLUtils.getChildNodesByTagName(node, "USE_TYPE_MESSAGE");
			if(nodes !=null && nodes.length > 0){
				processUseTypeMessage(blPolicy,XMLUtils.getChildNodeByTagName(node, "USE_TYPE_MESSAGE"));
  			}
		}
	    
	}
    
    /**<<<<<<go added by harry on 24/08/07 ��������XXXXX�б�**/
    /**
     * ����COVERAGE_LIST�ڵ�
     */
    private void processCoverageList(BLPolicy blPolicy, Node node) throws Exception
    {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "COVERAGE");
        
        for (int i = 0; i < nodes.length; i++) {
            processCoverage(blPolicy, nodes[i]);
        }
        
    }
    /**
     * ����COVERAGE�ڵ�
     * @param blPolicy
     * @param node
     * @throws Exception
     */
    private void processCoverage(BLPolicy blPolicy, Node node) throws Exception
    {
        String coverageType = XMLUtils.getChildNodeValue(node, "COVERAGE_TYPE");
        String coverageCode = XMLUtils.getChildNodeValue(node, "COVERAGE_CODE");
        String peccancyAdjustValue = XMLUtils.getChildNodeValue(node, "PECCANCY_ADJUST_VALUE");
        String claimAdjustValue = XMLUtils.getChildNodeValue(node, "CLAIM_ADJUST_VALUE");
        String driverRate = XMLUtils.getChildNodeValue(node, "DRIVER_RATE");
        String districtRate = XMLUtils.getChildNodeValue(node, "DISTRICT_RATE");
        String additionRate = XMLUtils.getChildNodeValue(node, "ADDITION_RATE");
        String peccancyAdjustReason = XMLUtils.getChildNodeValue(node, "PECCANCY_ADJUST_REASON");
        String claimAdjustReason = XMLUtils.getChildNodeValue(node, "CLAIM_ADJUST_REASON");
        String driverRateReason = XMLUtils.getChildNodeValue(node, "DRIVER_RATE_REASON");
        String districtRateReason = XMLUtils.getChildNodeValue(node, "DISTRICT_RATE_REASON");
        String rateFloatFlag = XMLUtils.getChildNodeValue(node, "RATE_FLOAT_FLAG");
        String reinsureFlag = XMLUtils.getChildNodeValue(node, "REINSURE_FLAG");
        
        UtiPower utiPower = new UtiPower();
        String startDate = "";
        String endDate = "";
        if(utiPower.checkStopCom(blPolicy.getBLPrpCmain().getArr(0).getComCode())){
        	startDate = XMLUtils.getChildNodeValue(node, "START_DATE");
        	endDate = XMLUtils.getChildNodeValue(node, "END_DATE");
        }
        
        
        /*
         * modi by liujia start
         * reason�����ƽ̨׼���ظ�XX��־����Ϊ���Ļ���
         * ��ƽ̨����������������ﻹҪȡһ�η��ص���Ϣ���û���
         * ����ظ�XX���ܺ�ƽ̨���׷����Ƿ�������ֱ���׳��쳣����ҵ����Ҫ��
         */
        
        /*if (reinsureFlag!=null&&!reinsureFlag.equals("0")) {
        	throw new Exception(errorMessage);
        }*/
        String lastBillDate = XMLUtils.getChildNodeValue(node, "LAST_BILL_DATE");

        

        try {
        	blPolicy.getBLCIEndorValid().getArr(0).setCoverageType(coverageType);
        	blPolicy.getBLCIEndorValid().getArr(0).setCoverageCode(coverageCode);
        	blPolicy.getBLCIEndorValid().getArr(0).setPeccancyAdjustValue(peccancyAdjustValue);
        	blPolicy.getBLCIEndorValid().getArr(0).setClaimAdjustValue(claimAdjustValue);
        	blPolicy.getBLCIEndorValid().getArr(0).setDistrictRate(driverRate);
        	blPolicy.getBLCIEndorValid().getArr(0).setDistrictRate(districtRate);
        	blPolicy.getBLCIEndorValid().getArr(0).setAdditionRate(additionRate);
        	blPolicy.getBLCIEndorValid().getArr(0).setPeccancyAdjustReason(peccancyAdjustReason);
        	blPolicy.getBLCIEndorValid().getArr(0).setClaimAdjustReason(claimAdjustReason);
        	blPolicy.getBLCIEndorValid().getArr(0).setDriverRateReason(driverRateReason);
        	blPolicy.getBLCIEndorValid().getArr(0).setDistrictRateReason(districtRateReason);
        	blPolicy.getBLCIEndorValid().getArr(0).setRateFloatFlag(rateFloatFlag);
        	
        	
        	if(utiPower.checkStopCom(blPolicy.getBLPrpCmain().getArr(0).getComCode())){
        	    blPolicy.getBLCIEndorValid().getArr(0).setStartDate(TransCode.correctDateTime(startDate));
        	    blPolicy.getBLCIEndorValid().getArr(0).setEndDate(TransCode.correctDateTime(endDate));
        	}
        	
            
            
        	/*
        	 * modi by liujia start
        	 * reason :����ǽ�����ֱ�������ڴ�����У��������¶�����һ������
        	 * ǰ������������Ѿ�����һ�����Ĳ�ѯȷ�϶����ʵ���ˣ�������Ǹ�ʵ��ȡ��������һ������
        	 * */
        	
        	blPolicy.getBLCIEndorValid().getArr(0).setQReinsureFlag(reinsureFlag);
        	if (lastBillDate.length() < 8) {
                throw new IllegalArgumentException(lastBillDate + "�����ڸ�ʽ���ԣ�����Ϊ����8λ�Ĵ����ֵ��ַ���");
            }
        	lastBillDate=lastBillDate.substring(0,4)+"-"+lastBillDate.substring(4,6)+"-"+lastBillDate.substring(6,8);
        	
        	blPolicy.getBLCIEndorValid().getArr(0).setLastBillDate(lastBillDate);
        	/*
        	 * modi by liujia end 
        	 * */
        	
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    /**
     * ����CLAIM_LIST�ڵ�
     * @param blPolicy
     * @param node
     * @throws Exception
     */
	private void processClaimList(BLPolicy blPolicy, Node node) throws Exception 
    {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CLAIM_DATA");
        
        for (int i = 0; i < nodes.length; i++) {
            processClaimData(blPolicy,nodes[i]);
        }
        
    }

    /**
     * ����DRIVER_DATA�ڵ�
     * @param blPolicy
     * @param node
     * @throws Exception
     */
    private void processClaimData(BLPolicy blPolicy,Node node) throws Exception
    {
        String DemandNo = blPolicy.getBLCIEndorValid().getArr(0).getDemandNo();   
        String COMPANY_ID      = XMLUtils.getChildNodeValue(node, "COMPANY_ID");
        String REGISREATION_NO = XMLUtils.getChildNodeValue(node, "REGISREATION_NO");
        
        
        String CLAIM_NO        = "";
        if(!new UtiPower().checkInterAgreementSX(blPolicy.getBLPrpCmain().getArr(0).getComCode())
        		|| !new UtiPower().checkInterfaceHLJ(blPolicy.getBLPrpCmain().getArr(0).getComCode())){
        	CLAIM_NO        = XMLUtils.getChildNodeValue(node, "CLAIM_NO");
        }
        
        
        String ACCIDENT_TIME   = XMLUtils.getChildNodeValue(node, "ACCIDENT_TIME");
        String ENDCASE_TIME    = XMLUtils.getChildNodeValue(node, "ENDCASE_TIME");
        String ACCIDENT_DEATH  = XMLUtils.getChildNodeValue(node, "ACCIDENT_DEATH");

        
        ACCIDENT_TIME = correctDate(ACCIDENT_TIME);
        ENDCASE_TIME  = correctDate(ENDCASE_TIME);
        
        
        PrpCIEndorClaimSchema claimSchema = new PrpCIEndorClaimSchema();
        claimSchema.setDemandNo(DemandNo);           
        claimSchema.setSerialNo("" + (blPolicy.getBLPrpCIEndorClaim().getSize() + 1)); 
        claimSchema.setPayCompany(COMPANY_ID);       
        claimSchema.setClaimNo(REGISREATION_NO);     
        claimSchema.setCompensateNo(CLAIM_NO);       
        claimSchema.setLossTime(ACCIDENT_TIME);      
        claimSchema.setEndCaseTime(ENDCASE_TIME);    
        claimSchema.setPersonPayType(ACCIDENT_DEATH);
        /*modi by liujia start
         * reason:XX�Ŵ�bl�����ȡ
         */
        claimSchema.setPolicyNo(blPolicy.getBLPrpCitemCar().getArr(0).getPolicyNo());
        /*modi by liujia end*/
        
        
        
        
        
        
        
        
        
        
        if(new UtiPower().checkSendEffectiveHour(blPolicy.getBLPrpCmain().getArr(0).getComCode())
        		||new UtiPower().checkInterAgreementSX(blPolicy.getBLPrpCmain().getArr(0).getComCode())
        		||new UtiPower().checkInterfaceHLJ(blPolicy.getBLPrpCmain().getArr(0).getComCode())){
            String claimCode        = XMLUtils.getChildNodeValue(node, "CLAIM_CODE");
            String policyNo         = XMLUtils.getChildNodeValue(node, "POLICY_NO");
            String claimAmount      = XMLUtils.getChildNodeValue(node, "CLAIM_AMOUNT");
            claimSchema.setCompensateNo(claimCode);    
            claimSchema.setTotalAmount(claimAmount);    
            claimSchema.setPolicyNo(policyNo);   
        }
        
        
        blPolicy.getBLPrpCIEndorClaim().setArr(claimSchema);
        
        
        if(!new UtiPower().checkInterfaceHLJ(blPolicy.getBLPrpCmain().getArr(0).getComCode())){
        processClaimCoverList(blPolicy, XMLUtils.getChildNodeByTagName(node, "CLAIM_COVER_LIST"), claimSchema.getSerialNo());
        }
        
        
    }

    /**
     * ����CLAIM_COVER_LIST�ڵ�
     * @param blPolicy
     * @param node
     * @param serialNo 
     * @throws Exception
     */
    private void processClaimCoverList(BLPolicy blPolicy,Node node, String serialNo) throws Exception
    {
    	
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CLAIM_COVER_DATA");
        
        
        for (int i = 0; i < nodes.length; i++) {
        	
            processCLAIM_COVER_DATA(blPolicy, nodes[i], serialNo);
        }
        
    }

    /**
     * ����CLAIM_COVER_DATA�ڵ�
     * @param blPolicy
     * @param node
     * @param serialNo 
     * @throws Exception
     */
    private void processCLAIM_COVER_DATA(BLPolicy blPolicy,Node node, String serialNo) throws Exception
    {
    	
        String DemandNo      = blPolicy.getBLCIEndorValid().getArr(0).getDemandNo();   
        
        String POLICY_NO     = XMLUtils.getChildNodeValue(node, "POLICY_NO");
        
        String COVERAGE_TYPE = XMLUtils.getChildNodeValue(node, "COVERAGE_TYPE");
        String CLAIM_AMOUNT  = XMLUtils.getChildNodeValue(node, "CLAIM_AMOUNT");
        
        
        PrpCIEndorClaimDetailSchema claimDetailSchema = new PrpCIEndorClaimDetailSchema();
        claimDetailSchema.setDemandNo(DemandNo);     
        claimDetailSchema.setFSerialNo(serialNo);    
        claimDetailSchema.setSerialNo("" + (blPolicy.getBLPrpCIEndorClaimDetail().getSize() + 1)); 
        claimDetailSchema.setPolicyNo(POLICY_NO);    
        claimDetailSchema.setKindCode(COVERAGE_TYPE);
        claimDetailSchema.setLossFee(CLAIM_AMOUNT);  
        
        blPolicy.getBLPrpCIEndorClaimDetail().setArr(claimDetailSchema);
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
    
    /** added by harry on 24/08/07 ��������XXXXX�б� over>>>>>>**/

    /**
	 * ����BASE_PART��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBasePart(BLPolicy blPolicy, Node node)
			throws Exception {










		ChgDate date = new ChgDate();
		String currentDate = date.getCurrentTime("yyyy-MM-dd HH:mm");
		String amendQueryNo = XMLUtils.getChildNodeValue(node, "AMEND_QUERY_NO"); 	
		String amendPremium = XMLUtils.getChildNodeValue(node, "AMEND_PREMIUM"); 	
		String formula 		= XMLUtils.getChildNodeValue(node, "FORMULA"); 			
		
		String currentTax = "";
		String formerTax  = "";
		String lateFee    = "";
		if(blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2).trim().equals("07")){
			currentTax    = XMLUtils.getChildNodeValue(node, "CURRENT_TAX"); 		
			formerTax     = XMLUtils.getChildNodeValue(node, "FORMER_TAX"); 		
			lateFee       = XMLUtils.getChildNodeValue(node, "LATE_FEE"); 		    
		}
		
		
		String strAmendBasedPremium = "";
		String strAmendStardardPremium = "";
        
        String strTaxAmendPermium="0";
        String strTaxAmendDeclare="";
        UtiPower utiPower = new UtiPower();
        
		if(blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2).trim().equals("01") ||
		   blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2).trim().equals("07"))
		{
			
		}
		else
		{
			strAmendBasedPremium = XMLUtils.getChildNodeValue(node, "AMEND_BASED_PREMIUM"); 
			strAmendStardardPremium = XMLUtils.getChildNodeValue(node, "AMEND_STANDARD_PREMIUM"); 
            
            if(utiPower.checkCICarShipTaxQG(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())){
               strTaxAmendPermium= XMLUtils.getChildNodeValue(node, "TAX_AMEND_PREMIUM"); 
               strTaxAmendDeclare= XMLUtils.getChildNodeValue(node, "TAX_AMEND_DECLARE"); 
            }
            
		}
		
		
        String strFundAmount = "";
        if(utiPower.addFundRescueInfo(blPolicy.getBLPrpCmain().getArr(0).getComCode())){
        	strFundAmount = XMLUtils.getChildNodeValue(node, "FUND_AMOUNT");
        }
        
		
		
		CIEndorValidSchema schema = new CIEndorValidSchema();
		schema.setDemandNo(amendQueryNo);		
		schema.setChgPremium(amendPremium); 	
		schema.setValidTime(currentDate); 		
		schema.setPtext(formula);				
		schema.setPolicyNo(blPolicy.getBLPrpCitemCar().getArr(0).getPolicyNo());	
		schema.setValidStatus("0");				
		
        
		if(blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2).trim().equals("07")){
			schema.setCurrentTax(currentTax);
			schema.setFormerTax(formerTax);
			schema.setLateFee(lateFee);
			
			schema.setSearchSequenceNo(blPolicy.getBLPrpCitemCar().getArr(0).getSearchSequenceNo());
			
		}
        
		
		String tempComcode=blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2);
		
		if(blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2).trim().equals("01") ||
		   blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2).trim().equals("07"))
		{
			
		}
		else
		{
			schema.setAmendBasedPremium(strAmendBasedPremium);
			schema.setAmendStandArdPremium(strAmendStardardPremium);
			/*modi by liujia start
			 * reasonȡ�Ƿ������־*/
			
			if(blPolicy.getBLCIInsureQuery().getSize() >0&&SysConfig.getProperty("EXPERIMENTALCOM").trim().indexOf(tempComcode.substring(0,2))>-1){
				schema.setEndorType(blPolicy.getBLCIInsureQuery().getArr(0).getChangOwnerFlag());
			}
			
			/*modi by liujia end*/
            
            if(utiPower.checkCICarShipTaxQG(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())){
                schema.setTaxAmendPremium(strTaxAmendPermium);
                schema.setTaxAmendDeclare(strTaxAmendDeclare);
                schema.setIsAmendTax(blPolicy.getBLCIInsureQuery().getArr(0).getIsAmendTax());
            }
            
		}
		
		
        /*reason:ƽ̨׼���ظ�XX��־Ϊ��ʱ�����������������Ͻ����񲻵�errorMessage��Ϣ��
		������ֻ�ܴӶ���õ�ƽ̨ƽ̨������Ϣ�����ƽ̨���׷���Ϊ�ɹ�����������µ�
		errorMessage�д���ǡ��ɹ���������ʹ�ľ�����Ϣ���������Ϊ�ظ�XX�ˡ�
		
		*/
		schema.setErrorMessage(errorMessage);
		
		
    	if(utiPower.addFundRescueInfo(blPolicy.getBLPrpCmain().getArr(0).getComCode())){
    		schema.setFundAmount(strFundAmount);
    	}
    	
		blPolicy.getBLCIEndorValid().setArr(schema);	
	}
    
    protected void processVehicleTaxation(BLPolicy blPolicy, Node node)throws Exception{
        EndorseCarShipTaxQueryDecoder endorseCarShipTaxQueryDecoder =  new EndorseCarShipTaxQueryDecoder();
        endorseCarShipTaxQueryDecoder.addVehicleTaxation(blPolicy,node);
    }
    
    
  
    protected void processOwnerMessage(BLPolicy blPolicy,Node node)throws Exception {
    	String strOwnerMessageType = XMLUtils.getChildNodeValue(node, "OWNER_MESSAGE_TYPE");
    	CIEndorValidSchema schema = blPolicy.getBLCIEndorValid().getArr(0);
    	schema.setCarOwnerMessage(strOwnerMessageType);
    }
    
    
    protected void processPmVehicle(BLPolicy blPolicy,Node node)throws Exception {
    	String strDemandNo = blPolicy.getBLCIEndorValid().getArr(0).getDemandNo();
    	String strCarmark = XMLUtils.getChildNodeValue(node, "CAR_MARK");
    	String strVehicleType = XMLUtils.getChildNodeValue(node, "VEHICLE_TYPE");
    	String strRackno = XMLUtils.getChildNodeValue(node, "RACK_NO");
    	String strEngineno = XMLUtils.getChildNodeValue(node, "ENGINE_NO");
    	String strVehicleStyle = XMLUtils.getChildNodeValue(node, "VEHICLE_STYLE");
    	String strPmuseType = XMLUtils.getChildNodeValue(node, "PM_USE_TYPE");
    	String strIneffectualDate = XMLUtils.getChildNodeValue(node, "INEFFECTUAL_DATE");
    	String strRejectDate = XMLUtils.getChildNodeValue(node, "REJECT_DATE");
    	String strVehicleRegisterDate = XMLUtils.getChildNodeValue(node, "VEHICLE_REGISTER_DATE");
    	String strLastcheckDate = XMLUtils.getChildNodeValue(node, "LAST_CHECK_DATE");
    	String strTransferDate = XMLUtils.getChildNodeValue(node, "TRANSFER_DATE");
    	String strWholeWeight = XMLUtils.getChildNodeValue(node, "WHOLE_WEIGHT");
    	String strLimitLoadPerson = XMLUtils.getChildNodeValue(node, "LIMIT_LOAD_PERSON");
    	String strLimitLoad = XMLUtils.getChildNodeValue(node, "LIMIT_LOAD");
    	String strDisplacement = XMLUtils.getChildNodeValue(node, "DISPLACEMENT");
    	String strMadeFactory = XMLUtils.getChildNodeValue(node, "MADE_FACTORY");
    	String strVehicleModel = XMLUtils.getChildNodeValue(node, "VEHICLE_MODEL");
    	String strProducerType = XMLUtils.getChildNodeValue(node, "PRODUCER_TYPE");
    	String strVehiclebrandCH = XMLUtils.getChildNodeValue(node, "VEHICLE_BRAND_1");
    	String strVehiclebrandEN = XMLUtils.getChildNodeValue(node, "VEHICLE_BRAND_2");
    	String strHaulage = XMLUtils.getChildNodeValue(node, "HAULAGE");
    	String strVehicleColour = XMLUtils.getChildNodeValue(node, "VEHICLE_COLOUR");
    	String strSalePrice = XMLUtils.getChildNodeValue(node, "SALE_PRICE");
    	String strPmfuelType = XMLUtils.getChildNodeValue(node, "PM_FUEL_TYPE");
    	String strStatus = XMLUtils.getChildNodeValue(node, "STATUS");
    	String strVehicleCategory = XMLUtils.getChildNodeValue(node, "VEHICLE_CATEGORY");
    	String strOwnerName = XMLUtils.getChildNodeValue(node, "OWNER_NAME");
    	CIEndorValidSchema schema = blPolicy.getBLCIEndorValid().getArr(0);
    	schema.setDemandNo(strDemandNo);
    	schema.setLicensePlateNo(strCarmark);
    	schema.setLicensePlateType(strVehicleType);
    	schema.setVIN(strRackno);
    	schema.setEngineNo(strEngineno);
    	schema.setVehicleType(strVehicleStyle);
    	schema.setPmMotorUsageTypeCode(strPmuseType);
    	schema.setIneffectualDate(strIneffectualDate);
    	schema.setRejectDate(strRejectDate);
    	schema.setFirstRegisterDate(strVehicleRegisterDate);
    	schema.setLastCheckDate(strLastcheckDate);
    	schema.setTransferDate(strTransferDate);
    	schema.setWholeWeight(strWholeWeight);
    	schema.setRatedPassengerCapacity(strLimitLoadPerson);
    	schema.setTonnage(strLimitLoad);
    	schema.setDisplacement(strDisplacement);
    	schema.setMadeFactory(strMadeFactory);
    	schema.setModel(strVehicleModel);
    	schema.setProducerType(strProducerType);
    	schema.setBrandCN(strVehiclebrandCH);
    	schema.setBrandEN(strVehiclebrandEN);
    	schema.setHaulage(strHaulage);
    	schema.setVehicleColour(strVehicleColour);
    	schema.setSalePrice(strSalePrice);
    	schema.setPmFuelType(strPmfuelType);
    	schema.setStatus(strStatus);
    	schema.setMotorTypeCode(strVehicleCategory);
    	schema.setVehicleOwnerName(strOwnerName);
    }
    protected void processUseTypeMessage(BLPolicy blPolicy,Node node)throws Exception {
    	String strUseTypeMessageType = XMLUtils.getChildNodeValue(node, "USE_TYPE_MESSAGE_TYPE");
    	CIEndorValidSchema schema = blPolicy.getBLCIEndorValid().getArr(0);
    	schema.setUsageTypeMessage(strUseTypeMessageType);
    }
    
}
