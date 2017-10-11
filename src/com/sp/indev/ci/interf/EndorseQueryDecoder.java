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
 * 发送批改查询返回数据的解码器
 * 
 */
public class EndorseQueryDecoder {
	/*modi by liujia start
	 * reason:记录平台返回信息，当平台准许重复XX开关为开时，交易正常，但是XX要将返回消息记录，抛出异常*/
    String  errorMessage="";
	/*modi by liujia end*/
	public static void main(String[] args) throws Exception 
	{
	}
	
	private static Logger logger = Logger.getLogger(EndorseQueryDecoder.class);
	
	/**
	 * 解码
	 * 
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, 
					   BLPolicy blPolicy, 
					   String content)
		throws Exception 
	{
		
		logger.info("[批改查询返回报文]:"+content);
		
	
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"), blPolicy);
		processBody(blPolicy, XMLUtils.getChildNodeByPath(document,"/PACKET/BODY"));
		
		saveRequestLog(dbPool, blPolicy);
	}
	
	/**
	 * 解码
	 * @author liuweichang-ghq 20140624 增加不带dbpool的解码方法
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
	public void decodeProcess(BLPolicy blPolicy, 
			String content)
	throws Exception 
	{
		
		logger.info("[批改查询返回报文]:"+content);
		
		
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"), blPolicy);
		processBody(blPolicy, XMLUtils.getChildNodeByPath(document,"/PACKET/BODY"));
	}
	
	/**
	 * XXXXX存平台返回结果
	 * @author liuweichang-ghq 20140624 增加XXXXX存平台返回结果方法
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
	public void decodeSave(DbPool dbPool, 
			BLPolicy blPolicy)
	throws Exception 
	{
		
		saveRequestLog(dbPool, blPolicy);
	}
	
	

	/**
	 * XXXXX存请求日志
	 * 
	 * @param blProposal
	 * @throws Exception
	 */
	protected void saveRequestLog(DbPool dbPool, BLPolicy blPolicy)
			throws Exception 
	{
		
		blPolicy.getBLCIEndorValid().save(dbPool);
		/*modi by liujia start
		 * reason:XX查询新增加两个表*/
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
	 * 处理HEAD节
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
         * reason:将原来两句代码移出来，因为平台重复XX开关为开时就算是重复XX交易也返回正常,但是会有警告信息要给用户看*/
        errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
        if (!responseCode.equals("1")) {
            throw new Exception(errorMessage);
        }
       
	}

	/**
	 * 处理BODY节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBody(BLPolicy blPolicy, Node node) throws Exception {
		processBasePart(blPolicy, XMLUtils.getChildNodeByTagName(node, "BASE_PART"));
        /**<<<<<< added by harry on 24/08/07 新增返回XXXXX列表及重复XX标志**/
		/*modi by liujia start
		 * reason:这里因该加判断如果非XXXXX上海才有的数据*/
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
		* reason:这里因该加判断如果非XXXXX上海才有的数据*/
        /** added by harry on 24/08/07 新增返回XXXXX列表及重复XX标志 >>>>>>**/
	    
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
    
    /**<<<<<<go added by harry on 24/08/07 新增返回XXXXX列表**/
    /**
     * 处理COVERAGE_LIST节点
     */
    private void processCoverageList(BLPolicy blPolicy, Node node) throws Exception
    {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "COVERAGE");
        
        for (int i = 0; i < nodes.length; i++) {
            processCoverage(blPolicy, nodes[i]);
        }
        
    }
    /**
     * 处理COVERAGE节点
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
         * reason如果在平台准许重复XX标志开关为开的话，
         * 与平台交互正常，因此这里还要取一次返回的信息给用户看
         * 如果重复XX不管和平台交易返回是否正常都直接抛出异常，（业务部门要求）
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
        	 * reason :因该是将属性直接设置在大对象中，上面重新定义了一个对象
        	 * 前面这个对象中已经存在一个批改查询确认对象的实例了，这个把那个实例取出来再设一个属性
        	 * */
        	
        	blPolicy.getBLCIEndorValid().getArr(0).setQReinsureFlag(reinsureFlag);
        	if (lastBillDate.length() < 8) {
                throw new IllegalArgumentException(lastBillDate + "的日期格式不对，必须为大于8位的纯数字的字符串");
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
     * 处理CLAIM_LIST节点
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
     * 处理DRIVER_DATA节点
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
         * reason:XX号从bl大对象取
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
     * 处理CLAIM_COVER_LIST节点
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
     * 处理CLAIM_COVER_DATA节点
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
    
    /** added by harry on 24/08/07 新增返回XXXXX列表 over>>>>>>**/

    /**
	 * 处理BASE_PART节
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
			 * reason取是否过户标志*/
			
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
		
		
        /*reason:平台准许重复XX标志为开时，交易正常，界面上将捕获不到errorMessage信息，
		界面上只能从对象得到平台平台返回信息，如果平台交易返回为成功，正常情况下的
		errorMessage中存的是“成功”，否则就存的警告信息，这种情况为重复XX了。
		
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
