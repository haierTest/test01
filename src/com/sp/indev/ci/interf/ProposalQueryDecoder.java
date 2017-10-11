package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.bi.dbsvr.DBCIInsureDemandRisk;
import com.sp.indiv.bi.schema.CIInsureDemandRiskSchema;
import com.sp.indiv.ci.dbsvr.DBCICarShipTaxQGDemand;
import com.sp.indiv.ci.dbsvr.DBCICarshipTaxDemand;
import com.sp.indiv.ci.dbsvr.DBCIInsureDemand;
import com.sp.indiv.ci.dbsvr.DBCIInsureDemandLoss;
import com.sp.indiv.ci.dbsvr.DBCIInsureDemandPay;
import com.sp.indiv.ci.schema.CIInsureDemandLossSchema;
import com.sp.indiv.ci.schema.CIInsureDemandPaySchema;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;

/**
 * 发送XX查询请求数据的解码器
 * 
 */
public class ProposalQueryDecoder {

    public static void main(String[] args) 
    	throws Exception 
    {
    }
    
    
	private static Logger logger = Logger.getLogger(ProposalQueryDecoder.class);
	
	
    String errorMessage = "";
	
    /**
     * 解码
     * @return XX查询请求XML格式串
     * @throws Exception
     */
    public void decode(DbPool dbPool,
    				   BLProposal blProposal, 
    				   String content, 
    				   String editType, 
    				   String proposalNo) 
    	throws Exception 
    {
    	
		logger.info("[XX查询返回报文]:"+content);
		
    	InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        
        if(!"1".equals(blProposal.getBLPrpTmain().getArr(0).getCCSITestFlag())){
        	processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
        }
        
        processBody(blProposal, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
        
        
    	UtiPower utiPower = new UtiPower();
        if(utiPower.checkComCheckCode(blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	if(!"1".equals(blProposal.getBLCIInsureDemand().getArr(0).getRenewalFlag())){
        		saveRequestLog(dbPool, blProposal, editType, proposalNo);
        	}
        }else{
        	saveRequestLog(dbPool, blProposal, editType, proposalNo);
        }
        
    }
    
    
   /**
    * 解码不XXXXX存
    * @author liuweichang-ghq 20140616 与平台交互优化
    * @param blProposal XX单大对象
    * @param content 平台返回报文
    * @throws Exception
    */
    public void decodeProcess(BLProposal blProposal, 
    		String content) 
    throws Exception 
    {
    	
    	logger.info("[XX查询返回报文]:"+content);
    	InputStream in = new ByteArrayInputStream(content.getBytes());
    	Document document = XMLUtils.parse(in);
    	
    	if(!"1".equals(blProposal.getBLPrpTmain().getArr(0).getCCSITestFlag())){
    		processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
    	}
    	processBody(blProposal, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
    	
    }
    
   /**
    * 解码后XXXXX存
    * @author liuweichang-ghq 20140616 与平台交互优化
    * @param dbPool 
    * @param blProposal 大对象
    * @param editType 编辑类型[T/P/E]
    * @param proposalNo XX单号
    * @throws Exception
    */
    public void decodeSave(DbPool dbPool,
    				   BLProposal blProposal, 
    				   String editType, 
    				   String proposalNo) 
    	throws Exception 
    {
    	
    	UtiPower utiPower = new UtiPower();
        if(utiPower.checkComCheckCode(blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	if(!"1".equals(blProposal.getBLCIInsureDemand().getArr(0).getRenewalFlag())){
        		saveRequestLog(dbPool, blProposal, editType, proposalNo);
        	}
        }else{
        	saveRequestLog(dbPool, blProposal, editType, proposalNo);
        }
    }
    

    /**
     * XXXXX存请求日志
     * @param blProposal
     * @throws Exception
     */
    protected void saveRequestLog(DbPool dbPool, 
    							  BLProposal blProposal, 
    							  String editType, 
    							  String proposalNo) 
    	throws Exception 
    {
    	
    	ProposalCarShipTaxQueryDecoder proposalCarShipTaxQueryDecoder = new ProposalCarShipTaxQueryDecoder();
    	
        try 
        {
        	UtiPower utiPower = new UtiPower();

        		
        		if(blProposal.getBLCIInsureDemand().getSize()>0){
	        		String demandNo = blProposal.getBLCIInsureDemand().getArr(0).getDemandNo();
					
					ArrayList arrWhereValue = new ArrayList();
					arrWhereValue.add(demandNo);
					
					
	        		if(utiPower.checkCICarshipTaxSH(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())
	            			||utiPower.checkCICarshipTaxBJ(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())){
	            		
	    	        	DBCICarshipTaxDemand dbCICarshipTaxDemand = new  DBCICarshipTaxDemand();
	            		
	    	        	
	            		
	    	        	if(dbCICarshipTaxDemand.getCount(dbPool, "DEMANDNO=? ",arrWhereValue)>0){
	            		
	            			
	            			dbCICarshipTaxDemand.deleteByDemandNo(dbPool, demandNo);
	            		}
	            	}else if(utiPower.checkCICarShipTaxQG(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())
	            			||utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
	            		
	    	        	DBCICarShipTaxQGDemand dbCICarshipTaxQGDemand = new DBCICarShipTaxQGDemand();
	            		
	    	        	
	            		
	            		if(dbCICarshipTaxQGDemand.getCount(dbPool, "DEMANDNO=? ",arrWhereValue)>0){
	            		
	            		
	            			dbCICarshipTaxQGDemand.deleteByDemandNo(dbPool, demandNo);
	            		}
	            	}
	        		DBCIInsureDemandLoss dbCIInsureDemandLoss = new DBCIInsureDemandLoss();
	        		
					
					if(dbCIInsureDemandLoss.getCount(dbPool, "DEMANDNO=? ",arrWhereValue)>0){
	        		
	        			dbCIInsureDemandLoss.deleteByDemandNo(dbPool, demandNo);
	        		}
	        		
	        		
					
	        		DBCIInsureDemandPay dbCIInsureDemandPay = new DBCIInsureDemandPay();
	        		
	        		if(dbCIInsureDemandPay.getCount(dbPool,  "DEMANDNO=? ",arrWhereValue)>0){
	        			dbCIInsureDemandPay.deleteByDemandNo(dbPool, demandNo);
	        		}
	        		DBCIInsureDemand dbCIInsureDemand = new DBCIInsureDemand();
	        		
	        		if(dbCIInsureDemand.getCount(dbPool,  "DEMANDNO=? ",arrWhereValue)>0){
	        			dbCIInsureDemand.delete(dbPool, demandNo);
	        		}
	        		
					
                    
                    DBCIInsureDemandRisk dbCIInsureDemandRisk = new DBCIInsureDemandRisk();
                    
                    
                    if (dbCIInsureDemandRisk.getCount(dbPool, "DEMANDNO=? ",arrWhereValue)>0){
                        dbCIInsureDemandRisk.deleteByDemandNo(dbPool, demandNo);
                    }
                    
                    
 
        		}
        		
            	
	            blProposal.getBLCIInsureDemand().save(dbPool);
	            blProposal.getBLCIInsureDemandLoss().save(dbPool);
	            blProposal.getBLCIInsureDemandPay().save(dbPool);
	            
                
                blProposal.getBlCIInsureDemandRisk().save(dbPool);
                
	            proposalCarShipTaxQueryDecoder.saveRequestLog(blProposal,dbPool);
	            

        }
        catch (Exception e) 
        {
            throw e;
        }
    }

    /**
     * 处理HEAD节
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
     * 处理BODY节
     * @param node Node
     * @throws Exception
     */
    protected void processBody(BLProposal blProposal, Node node) 
    	throws Exception 
    {
        
    	UtiPower utiPower = new UtiPower();
        if(utiPower.checkComCheckCode(blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	processBasePartQD(blProposal, XMLUtils.getChildNodeByTagName(node, "BASE_PART"));
        	if(!"1".equals(blProposal.getBLCIInsureDemand().getArr(0).getRenewalFlag())){
        		processCar(blProposal, XMLUtils.getChildNodeByTagName(node, "CAR"));
        	}
        }else{
            processBasePart(blProposal, XMLUtils.getChildNodeByTagName(node, "BASE_PART"));
        }
        
        processCoverageList(blProposal, XMLUtils.getChildNodeByTagName(node, "COVERAGE_LIST"));
        processPeccList(blProposal, XMLUtils.getChildNodeByTagName(node, "PECC_LIST"));
        processClaimList(blProposal, XMLUtils.getChildNodeByTagName(node, "CLAIM_LIST"));
        
        
        
        
        if(utiPower.checkCIInsureSH(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode()) 
        		&& !"0507".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
        	processBusiClaimList(blProposal, XMLUtils.getChildNodeByTagName(node, "BUSI_CLAIM_LIST"));
        }

        if(utiPower.checkCIInsureBJ(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())
        		                   && !"0507".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
        	processBusiClaimList(blProposal, XMLUtils.getChildNodeByTagName(node, "BUSI_CLAIM_LIST"));
        	
        }
        
        
        processDriverList(blProposal, XMLUtils.getChildNodeByTagName(node, "DRIVER_LIST"));
        
        
		
        if(utiPower.checkCICarShipTaxQG(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())
		    ||utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
        	
        	if(utiPower.isCarShipTaxNB(blProposal.getBLPrpTmain().getArr(0).getComCode())){
        		processVehicleTaxation(blProposal, XMLUtils.getChildNodeByTagName(node, "VehicleTaxation_NB"));
        	}else{
        		processVehicleTaxation(blProposal, XMLUtils.getChildNodeByTagName(node, "VehicleTaxation"));
        	}
        	
    	}
		
        
        
        processRiskWarning(blProposal, node);
        
        
        if(utiPower.checkSinoCommission(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
        	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "COMMISSION");
        	if (nodes.length > 0){
				processCommission(blProposal, XMLUtils.getChildNodeByTagName(node, "COMMISSION"));
			}
        }
        
        
        
        if(utiPower.checkSinoCIversion3(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate()) 
        		|| utiPower.checkSinoCarPlatformSZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
        	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "OWNER_MESSAGE");
        	if(nodes !=null && nodes.length > 0){
        		processOwnerMessage(blProposal,XMLUtils.getChildNodeByTagName(node, "OWNER_MESSAGE"));
        	}
        }
        
        
        
        if (utiPower.checkCIInsureSH(blProposal.getBLPrpTmain().getArr(0)
                .getRiskCode(), blProposal.getBLPrpTmain().getArr(0)
                .getComCode())) {
            processDuplicatedList(blProposal, XMLUtils.getChildNodeByTagName(node, "DUPLICATED_LIST"));
        }
        
        
        if(utiPower.savePlatformAdjustSwitchJS(blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "USE_TYPE_MESSAGE");
        	if(nodes !=null && nodes.length > 0){
            	processUseTypeMessage(blProposal, XMLUtils.getChildNodeByTagName(node, "USE_TYPE_MESSAGE"));
        	}
        }
        
    }

    /**
     * 处理BASE_PART节
     * @param node Node
     * @throws Exception
     */
    protected void processBasePart(BLProposal blProposal, Node node) 
    	throws Exception 
    {
        
    	String reinsureFlag 		= "";
        
        String restricFlag = "";
        String preferentialPremium = "";
        String preferentialFormula = "";
        String preferentialDay     = "";
        
        
        String lastYearStartDate = "";
        String lastYearEndDate = "";
        
        
        String safeAdjust           = "";
        String nonclaimAdjust       = "";
        String loyaltyAdjust 		= "";
        
        
        String strTansferFlag = ""; 
        String strHighRiskFlag = ""; 
        String strEffectReason = ""; 
        String strLastyearCompanyId = ""; 
        
        
        String wholeweight = "";
        String displacement = "";
        String chgOwnerDate = "";
        String producerType = "";
        String salePrice = "";
        String pmFuelType = "";
        String vehicleCategory = "";
        
        
        
        String busiLastYearEndDate = "";
        String busiAdjustStart = "";
        String busiAdjustEnd = "";
        

    	ChgDate chgDate      = new ChgDate();
        String currentDate   = chgDate.getCurrentTime("yyyy-MM-dd HH:mm");  
        UtiPower utiPower = new UtiPower();
        
        String querySequenceNo 		= XMLUtils.getChildNodeValue(node, "QUERY_SEQUENCE_NO");
        String carMark 				= XMLUtils.getChildNodeValue(node, "CAR_MARK");
        String vehicleType 			= XMLUtils.getChildNodeValue(node, "VEHICLE_TYPE");
        
        String useType 				= "";
        if(utiPower.savePlatformAdjustSwitchJS(blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	useType 				= XMLUtils.getChildNodeValue(node, "PM_USE_TYPE");
        }else{
        	useType 				= XMLUtils.getChildNodeValue(node, "USE_TYPE");
        }
        
        String engineNo 			= XMLUtils.getChildNodeValue(node, "ENGINE_NO");
        String rackNo 				= XMLUtils.getChildNodeValue(node, "RACK_NO");
        
        String color 				= "";
        String owner 				= "";
        if(utiPower.savePlatformAdjustSwitchJS(blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	color 				= XMLUtils.getChildNodeValue(node, "VEHICLE_COLOR");
        	owner 				= XMLUtils.getChildNodeValue(node, "OWNER_NAME");
        }else{
            color 				= XMLUtils.getChildNodeValue(node, "COLOR");
            owner 				= XMLUtils.getChildNodeValue(node, "OWNER");
        }
        
        String vehicleRegisterDate 	= XMLUtils.getChildNodeValue(node, "VEHICLE_REGISTER_DATE");
        String madeDate 			= XMLUtils.getChildNodeValue(node, "MADE_DATE");
        String limitLoadPerson 		= XMLUtils.getChildNodeValue(node, "LIMIT_LOAD_PERSON");
        String limitLoad 			= XMLUtils.getChildNodeValue(node, "LIMIT_LOAD");
        if(!"".equals(limitLoad)){
        	limitLoad = "" + Double.parseDouble(limitLoad)/1000;
        }
        String ineffectualDate 		= XMLUtils.getChildNodeValue(node, "INEFFECTUAL_DATE");
        String madeFactory 			= XMLUtils.getChildNodeValue(node, "MADE_FACTORY");
        String vehicleModel 		= XMLUtils.getChildNodeValue(node, "VEHICLE_MODEL");
        String vehicleBrand1 		= XMLUtils.getChildNodeValue(node, "VEHICLE_BRAND_1");
        String vehicleBrand2 		= XMLUtils.getChildNodeValue(node, "VEHICLE_BRAND_2");
        String vehicleStyle 		= XMLUtils.getChildNodeValue(node, "VEHICLE_STYLE");
        String lastCheckDate 		= XMLUtils.getChildNodeValue(node, "LAST_CHECK_DATE");
        String rejectDate 			= XMLUtils.getChildNodeValue(node, "REJECT_DATE");
        String status 				= XMLUtils.getChildNodeValue(node, "STATUS");
        String haulage 				= XMLUtils.getChildNodeValue(node, "HAULAGE");
        
        if(utiPower.savePlatformAdjustSwitchJS(blProposal.getBLPrpTmain().getArr(0).getComCode())){
            wholeweight = XMLUtils.getChildNodeValue(node, "WHOLE_WEIGHT");
            displacement = XMLUtils.getChildNodeValue(node, "DISPLACEMENT");
            chgOwnerDate = XMLUtils.getChildNodeValue(node, "TRANSFER_DATE");
            producerType = XMLUtils.getChildNodeValue(node, "PRODUCER_TYPE");
            salePrice = XMLUtils.getChildNodeValue(node, "SALE_PRICE");
            pmFuelType = XMLUtils.getChildNodeValue(node, "PM_FUEL_TYPE");
            vehicleCategory = XMLUtils.getChildNodeValue(node, "VEHICLE_CATEGORY");
        }
        
        
        if(utiPower.checkCIInsureSH(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode()) && !"0507".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
        	safeAdjust           = XMLUtils.getChildNodeValue(node, "SAFE_ADJUST");
        	nonclaimAdjust            = XMLUtils.getChildNodeValue(node, "NONCLAIM_ADJUST");
        	loyaltyAdjust            = XMLUtils.getChildNodeValue(node, "LOYALTY_ADJUST");
        }
        
        
        if (utiPower.checkCIInsureSH(blProposal.getBLPrpTmain().getArr(0)
                .getRiskCode(), blProposal.getBLPrpTmain().getArr(0)
                .getComCode())) {
            strTansferFlag = XMLUtils.getChildNodeValue(node, "TRANSFER_FLAG");
            strHighRiskFlag = XMLUtils.getChildNodeValue(node, "HIGH_RISK_FLAG");
            strEffectReason = XMLUtils.getChildNodeValue(node, "EFFECT_REASON");
            strLastyearCompanyId = XMLUtils.getChildNodeValue(node, "LASTYEAR_COMPANY_ID");
        }
        
        if(blProposal.getBLPrpTmain().getArr(0).getComCode().trim().substring(0, 2).equals("01")) {
        	reinsureFlag = XMLUtils.getChildNodeValue(node, "REPEAT_INSURANCE_FLAG");
        	
            if (reinsureFlag != null && reinsureFlag.equals("1")) {
            	throw new Exception(errorMessage);
            }
            
            restricFlag = XMLUtils.getChildNodeValue(node, "RESTRIC_FLAG");
            preferentialPremium = XMLUtils.getChildNodeValue(node, "PREFERENTIAL_PREMIUM");
            preferentialFormula = XMLUtils.getChildNodeValue(node, "PREFERENTIAL_FORMULA");
            preferentialDay     = XMLUtils.getChildNodeValue(node, "PREFERENTIAL_DAY");
            
            
            lastYearStartDate = correctDate(XMLUtils.getChildNodeValue(node, "LASTYEAR_START_DATE"));
            lastYearEndDate = correctDate(XMLUtils.getChildNodeValue(node, "LASTYEAR_END_DATE"));
            
        }
        
        if(utiPower.checkCICarShipTaxQG(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())){
            lastYearStartDate = correctDate(XMLUtils.getChildNodeValue(node, "LASTYEAR_START_DATE"));
            lastYearEndDate = correctDate(XMLUtils.getChildNodeValue(node, "LASTYEAR_END_DATE"));
        }
        
        
        ProposalCarShipTaxQueryDecoder proposalCarShipTaxQueryDecoder =  new ProposalCarShipTaxQueryDecoder();
        proposalCarShipTaxQueryDecoder.decode(blProposal,node);
        

        
        String strFundRate = "";
        String strFundAmount = "";
        if(utiPower.addFundRescueInfo(blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	strFundRate = XMLUtils.getChildNodeValue(node, "FUND_RATE");
        	strFundAmount = XMLUtils.getChildNodeValue(node, "FUND_AMOUNT");
        }
        
        
        if(utiPower.checkCIInsureBJ(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())
        		                   && !"0507".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
            

        	nonclaimAdjust=XMLUtils.getChildNodeValue(node,"NONCLAIM_ADJUST");   
        	busiLastYearEndDate = correctDate(XMLUtils.getChildNodeValue(node,"BUSILASTYEAR_END_DATE"));  
        	busiAdjustStart = correctDate(XMLUtils.getChildNodeValue(node,"BUSI_ADJUST_START"));   
        	busiAdjustEnd = correctDate(XMLUtils.getChildNodeValue(node,"BUSI_ADJUST_END"));   
        	
        }
        

        
        vehicleRegisterDate 		= correctDate(vehicleRegisterDate);
        madeDate 					= correctDate(madeDate);
        ineffectualDate 			= correctDate(ineffectualDate);
        lastCheckDate 				= correctDate(lastCheckDate);
        rejectDate 					= correctDate(rejectDate);
        
        
        if("".endsWith(carMark)){
        	carMark = blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo();
        }
        if("".endsWith(rackNo)){
        	rackNo = blProposal.getBLPrpTitemCar().getArr(0).getFrameNo();
        }
        if("".endsWith(engineNo)){
        	engineNo = blProposal.getBLPrpTitemCar().getArr(0).getEngineNo();
        }
        
        
        
        CIInsureDemandSchema schema = new CIInsureDemandSchema();
        schema.setDemandNo(querySequenceNo); 	
        schema.setLicenseNo(carMark); 			
        schema.setLicenseType(vehicleType); 	
        schema.setUseNatureCode(useType); 		
        schema.setFrameNo(rackNo); 				
        schema.setEngineNo(engineNo); 			
        schema.setLicenseColorCode(color); 		
        schema.setCarOwner(owner); 				
        schema.setEnrollDate(vehicleRegisterDate); 	
        schema.setMakeDate(madeDate); 				
        schema.setSeatCount(limitLoadPerson); 		
        schema.setTonCount(limitLoad); 				
        schema.setValidCheckDate(ineffectualDate); 	
        schema.setManufacturerName(madeFactory); 	
        schema.setModelCode(vehicleModel); 			
        schema.setBrandCName(vehicleBrand1); 		
        schema.setBrandName(vehicleBrand2); 		
        schema.setCarKindCode(vehicleStyle); 		
        schema.setCheckDate(lastCheckDate); 		
        schema.setEndValidDate(rejectDate); 		
        schema.setCarStatus(status); 				
        schema.setHaulage(haulage); 				
        
        schema.setSafeAdjust(safeAdjust);
        schema.setNonclaimAdjust(nonclaimAdjust);
        schema.setLoyaltyAdjust(loyaltyAdjust);
        
        
        if(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2).equals("07")){
          schema.setUseTypeSource(XMLUtils.getChildNodeValue(node, "USE_TYPE_SOURCE"));
        }
        
        
        schema.setRestricFlag(restricFlag);
        schema.setPreferentialPremium(preferentialPremium);
        schema.setPreferentialFormula(preferentialFormula);
        schema.setPreferentialDay(preferentialDay);
        
        
        schema.setLastYearStartDate(lastYearStartDate);
        schema.setLastYearEndDate(lastYearEndDate);
        
        schema.setComCode(blProposal.getBLPrpTmain().getArr(0).getComCode());  			 
        schema.setOperatorCode(blProposal.getBLPrpTmain().getArr(0).getOperatorCode());  
        schema.setDemandTime(currentDate);
        if(utiPower.checkStartUp0802(blProposal.getBLPrpTmain().getArr(0).getRiskCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())) {
        	schema.setAmount(AppConfig.get("sysconst.CI0802_INSURED_LIMIT_" + blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2) + "_AMOUNT"));
        }
        else {
        	schema.setAmount(AppConfig.get("sysconst.CI_INSURED_LIMIT_" + blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2) + "_AMOUNT"));
        }
        schema.setStartDate(blProposal.getBLPrpTmain().getArr(0).getStartDate());
        schema.setEndDate(blProposal.getBLPrpTmain().getArr(0).getEndDate());
        if(blProposal.getBLPrpTmain().getArr(0).getComCode().trim().substring(0, 2).equals("01")) {
            
            if(this.errorMessage!=null && !"成功".equals(this.errorMessage.trim())){
            	schema.setErrorMessage(errorMessage);
            }
        }
        
        if(utiPower.checkCIInsureSH(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	schema.setSearchSequenceNo(blProposal.getBLPrpTitemCar().getArr(0).getSearchSequenceNo());
        }
        
        
        if(utiPower.checkCIInsureBJ(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	if(!blProposal.getBLPrpTmain().getArr(0).getRiskCode().equals("0507")){
        		schema.setSearchSequenceNo(blProposal.getBLPrpTitemCar().getArr(0).getSearchSequenceNo());
        		schema.setBusiLastYearEndDate(busiLastYearEndDate);  
        		schema.setBusiAdjustStart(busiAdjustStart);         
        		schema.setBusiAdjustEnd(busiAdjustEnd);             
        	}
        	
        	schema.setIP(blProposal.getBLPrpTmain().getArr(0).getIP());
        	schema.setUsbKey(blProposal.getBLPrpTmain().getArr(0).getUsbKey());
        	
        }
        schema.setPoWeight(XMLUtils.getChildNodeValue(node, "PO_WEIGHT"));
        
        
        schema.setSendLastPolicyNo(blProposal.getBLPrpTmain().getArr(0).getSendLastPolicyNo());
        
        
        if(new UtiPower().checkCarShipTaxBJ(blProposal.getBLPrpTmain().getArr(0).getComCode(), currentDate)
        		&& "0507".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
        	schema.setFuelType(XMLUtils.getChildNodeValue(node, "FUEL_TYPE"));
        }
        
    	
    	if(new UtiPower().CarShipTaxCheckQueryBJ(blProposal.getBLPrpTmain().getArr(0).getComCode(), currentDate)){
    		String ExhaustScale = XMLUtils.getChildNodeValue(node, "EXHAUST_CAPACITY");
    		if(!"".equals(ExhaustScale)){
    			schema.setExhaustScale(new DecimalFormat("0.000").format(Double.parseDouble(ChgData.chgStrZero(ExhaustScale))/1000));
    		}
    	}
    	
    	
    	if(utiPower.addFundRescueInfo(blProposal.getBLPrpTmain().getArr(0).getComCode())){
    		schema.setFundRate(strFundRate);
    		schema.setFundAmount(strFundAmount);
    	}
    	
        
        if (utiPower.checkCIInsureSH(blProposal.getBLPrpTmain().getArr(0)
                .getRiskCode(), blProposal.getBLPrpTmain().getArr(0)
                .getComCode())) {
            schema.setTransferFlag(strTansferFlag);
            schema.setHighRiskFlag(strHighRiskFlag);
            schema.setEffectReason(strEffectReason);
            schema.setLastyearCompanyId(strLastyearCompanyId);
        }
        
        
        schema.setChannelType(blProposal.getBLPrpTmain().getArr(0).getChannelType());
        
        
        if(utiPower.savePlatformAdjustSwitchJS(blProposal.getBLPrpTmain().getArr(0).getComCode())){
            schema.setWholeweight(wholeweight);
            schema.setDisplacement(displacement);
            schema.setChgOwnerDate(chgOwnerDate);
            schema.setProducerType(producerType);
            schema.setSalePrice(salePrice);
            schema.setPmFuelType(pmFuelType);
            schema.setVehicleCategory(vehicleCategory);
        }
        
        
        blProposal.getBLCIInsureDemand().setArr(schema);
    }
    /**
     * 处理青岛平台BASE_PART节
     * @param node Node
     * @throws Exception
     */
    protected void processBasePartQD(BLProposal blProposal, Node node) 
    	throws Exception 
    {
    	String strQuerySequenceNo 		= XMLUtils.getChildNodeValue(node, "QUERY_SEQUENCE_NO");
    	String strRenewalFlag 		= XMLUtils.getChildNodeValue(node, "RENEWAL_FLAG");
    	String strCheckCode 		= XMLUtils.getChildNodeValue(node, "CHECK_CODE");
    	String strQuestion   		= XMLUtils.getChildNodeValue(node, "QUESTION");
        
        CIInsureDemandSchema schema = new CIInsureDemandSchema();
        schema.setDemandNo(strQuerySequenceNo);
        schema.setRenewalFlag(strRenewalFlag);
        schema.setCheckCode(strCheckCode);
        schema.setQuestion(strQuestion);
        
        schema.setChannelType(blProposal.getBLPrpTmain().getArr(0).getChannelType());
        
        
        blProposal.getBLCIInsureDemand().setArr(schema);
    }
    /**
     * 处理Car节
     * @param node Node
     * @throws Exception
     */
    protected void processCar(BLProposal blProposal, Node node) 
    	throws Exception 
    {
    	ChgDate chgDate      = new ChgDate();
        String currentDate   = chgDate.getCurrentTime("yyyy-MM-dd HH:mm");  
        UtiPower utiPower = new UtiPower();
        
        String carMark 				= XMLUtils.getChildNodeValue(node, "CAR_MARK");
        String vehicleType 			= XMLUtils.getChildNodeValue(node, "VEHICLE_TYPE");
        String useType 				= XMLUtils.getChildNodeValue(node, "USE_TYPE");
        String rackNo 				= XMLUtils.getChildNodeValue(node, "RACK_NO");
        String engineNo 			= XMLUtils.getChildNodeValue(node, "ENGINE_NO");
        String color 				= XMLUtils.getChildNodeValue(node, "COLOR");
        String owner 				= XMLUtils.getChildNodeValue(node, "OWNER");
        String vehicleRegisterDate 	= XMLUtils.getChildNodeValue(node, "VEHICLE_REGISTER_DATE");
        String madeDate 			= XMLUtils.getChildNodeValue(node, "MADE_DATE");
        String limitLoadPerson 		= XMLUtils.getChildNodeValue(node, "LIMIT_LOAD_PERSON");
        String limitLoad 			= XMLUtils.getChildNodeValue(node, "LIMIT_LOAD");
        if(!"".equals(limitLoad)){
        	limitLoad = "" + Double.parseDouble(limitLoad)/1000;
        }
        String ineffectualDate 		= XMLUtils.getChildNodeValue(node, "INEFFECTUAL_DATE");
        String madeFactory 			= XMLUtils.getChildNodeValue(node, "MADE_FACTORY");
        String vehicleModel 		= XMLUtils.getChildNodeValue(node, "VEHICLE_MODEL");
        String vehicleBrand1 		= XMLUtils.getChildNodeValue(node, "VEHICLE_BRAND_1");
        String vehicleBrand2 		= XMLUtils.getChildNodeValue(node, "VEHICLE_BRAND_2");
        String vehicleStyle 		= XMLUtils.getChildNodeValue(node, "VEHICLE_STYLE");
        String lastCheckDate 		= XMLUtils.getChildNodeValue(node, "LAST_CHECK_DATE");
        String rejectDate 			= XMLUtils.getChildNodeValue(node, "REJECT_DATE");
        String status 				= XMLUtils.getChildNodeValue(node, "STATUS");
        String haulage 				= XMLUtils.getChildNodeValue(node, "HAULAGE");
        
        
        vehicleRegisterDate 		= correctDate(vehicleRegisterDate);
        madeDate 					= correctDate(madeDate);
        ineffectualDate 			= correctDate(ineffectualDate);
        lastCheckDate 				= correctDate(lastCheckDate);
        rejectDate 					= correctDate(rejectDate);
        
        
        if("".endsWith(carMark)){
        	carMark = blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo();
        }
        if("".endsWith(rackNo)){
        	rackNo = blProposal.getBLPrpTitemCar().getArr(0).getFrameNo();
        }
        if("".endsWith(engineNo)){
        	engineNo = blProposal.getBLPrpTitemCar().getArr(0).getEngineNo();
        }
        
        
        
        CIInsureDemandSchema schema = blProposal.getBLCIInsureDemand().getArr(0);
        schema.setLicenseNo(carMark); 			
        schema.setLicenseType(vehicleType); 	
        schema.setUseNatureCode(useType); 		
        schema.setFrameNo(rackNo); 				
        schema.setEngineNo(engineNo); 			
        schema.setLicenseColorCode(color); 		
        schema.setCarOwner(owner); 				
        schema.setEnrollDate(vehicleRegisterDate); 	
        schema.setMakeDate(madeDate); 				
        schema.setSeatCount(limitLoadPerson); 		
        schema.setTonCount(limitLoad); 				
        schema.setValidCheckDate(ineffectualDate); 	
        schema.setManufacturerName(madeFactory); 	
        schema.setModelCode(vehicleModel); 			
        schema.setBrandCName(vehicleBrand1); 		
        schema.setBrandName(vehicleBrand2); 		
        schema.setCarKindCode(vehicleStyle); 		
        schema.setCheckDate(lastCheckDate); 		
        schema.setEndValidDate(rejectDate); 		
        schema.setCarStatus(status); 				
        schema.setHaulage(haulage); 				

        schema.setComCode(blProposal.getBLPrpTmain().getArr(0).getComCode());  			 
        schema.setOperatorCode(blProposal.getBLPrpTmain().getArr(0).getOperatorCode());  
        schema.setDemandTime(currentDate);
        if(utiPower.checkStartUp0802(blProposal.getBLPrpTmain().getArr(0).getRiskCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())) {
        	schema.setAmount(AppConfig.get("sysconst.CI0802_INSURED_LIMIT_" + blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2) + "_AMOUNT"));
        }
        else {
        	schema.setAmount(AppConfig.get("sysconst.CI_INSURED_LIMIT_" + blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2) + "_AMOUNT"));
        }
        schema.setStartDate(blProposal.getBLPrpTmain().getArr(0).getStartDate());
        schema.setEndDate(blProposal.getBLPrpTmain().getArr(0).getEndDate());
        
        schema.setSendLastPolicyNo(blProposal.getBLPrpTmain().getArr(0).getSendLastPolicyNo());
        
        
    }

    /**
     * 处理DRIVER_LIST节
     * @param node Node
     */
    protected void processDriverList(BLProposal blProposal, Node node) 
    	throws Exception
    {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "DRIVER_DATA");
        
        for (int i = 0; i < nodes.length; i++) {
            processDriverData(blProposal, nodes[i]);
        }
    }

    /**
     * 处理DRIVER节
     * @param node Node
     */
    protected void processDriverData(BLProposal blProposal, Node node) 
    	throws Exception
    {
         
        String driverName 	= XMLUtils.getChildNodeValue(node, "DRIVER_NAME");
        String gender 		= XMLUtils.getChildNodeValue(node, "GENDER");
        String birthDate 	= XMLUtils.getChildNodeValue(node, "BIRTH_DATE");
        String nationality 	= XMLUtils.getChildNodeValue(node, "NATIONALITY");
        String certiType 	= XMLUtils.getChildNodeValue(node, "CERTI_TYPE");
        String certiCode 	= XMLUtils.getChildNodeValue(node, "CERTI_CODE");
        String districtCode = XMLUtils.getChildNodeValue(node, "DISTRICT_CODE");
        String driverRegisterDate 	= XMLUtils.getChildNodeValue(node, "DRIVER_REGISTER_DATE");
        String effectualDate 		= XMLUtils.getChildNodeValue(node, "EFFECTUAL_DATE");
        String checkDate 			= XMLUtils.getChildNodeValue(node, "CHECK_DATE");
        String driverType 			= XMLUtils.getChildNodeValue(node, "DRIVER_TYPE");
        String status 				= XMLUtils.getChildNodeValue(node, "STATUS");
        
        birthDate 			= correctDate(birthDate);
        driverRegisterDate 	= correctDate(driverRegisterDate);
        effectualDate 		= correctDate(effectualDate);
        checkDate 			= correctDate(checkDate);
        
    }

    /**
     * 处理COVERAGE_LIST节
     * @param node Node
     */
    protected void processCoverageList(BLProposal blProposal, Node node)
    	throws Exception
    {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "COVERAGE");
        
        for (int i = 0; i < nodes.length; i++) {
            processCoverage(blProposal, nodes[i]);
        }
    }

    /**
     * 处理COVERAGE节
     * @param node Node
     */
    protected void processCoverage(BLProposal blProposal, Node node)
    	throws Exception
    {
        
    	String strComCode = "";
    	try
        {
    		String coverageType = XMLUtils.getChildNodeValue(node, "COVERAGE_TYPE");
    		String coverageCode = XMLUtils.getChildNodeValue(node, "COVERAGE_CODE");
    		
    		if(new UtiPower().checkCIInsureSH(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())){
    			String strKindCode  = XMLUtils.getChildNodeValue(node, "COM_COVERAGE_CODE");
    			String strLimitAmount = XMLUtils.getChildNodeValue(node, "LIMIT_AMOUNT");          
    			String strStartDate = XMLUtils.getChildNodeValue(node, "START_DATE");
    			String strEndDate   = XMLUtils.getChildNodeValue(node, "END_DATE");
    		}
    		
    		String standPremium = XMLUtils.getChildNodeValue(node, "STANDARD_PREMIUM");
    		String p1 			= XMLUtils.getChildNodeValue(node, "P1");
    		String peccancyAdjustValue 	= XMLUtils.getChildNodeValue(node, "PECCANCY_ADJUST_VALUE");
    		String claimAdjustValue 	= XMLUtils.getChildNodeValue(node, "CLAIM_ADJUST_VALUE");
    		String peccancyDriverCount 	= XMLUtils.getChildNodeValue(node, "PECCANCY_DRIVER_COUNT");
    		String driverRate 			= XMLUtils.getChildNodeValue(node, "DRIVER_RATE");
    		String districtRate 		= XMLUtils.getChildNodeValue(node, "DISTRICT_RATE");
    		String additionRate 		= XMLUtils.getChildNodeValue(node, "ADDITION_RATE");
    		String basedPremium 		= XMLUtils.getChildNodeValue(node, "BASED_PREMIUM");
    		String peccancyAdjustPremium = XMLUtils.getChildNodeValue(node, "PECCANCY_ADJUST_PREMIUM");
    		
    		
    		 
    		strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
    		String peccancyAdjustReason = "";
    		String claimAdjustReason	= "";
    		String driverRateReason		= "";
    		String districtRateReason	= "";
    		String rateFloatFlag		= "";
    		String strReInsureFlag 		= "";	
            String strLastBillDate 		= "";	
            
            String strLastyearStartDate = "";
            String strLastyearEndDate   = "";
            
            
            
            String strAdjustStartDate	= "";	
            String strAdjustEndDate		= "";	
            String policyAdjustValue    ="";
            
            
    		if(strComCode.trim().substring(0, 2).equals("01") ||
    		   strComCode.trim().substring(0, 2).equals("07"))
    		{
    			
    			
    			if(strComCode.trim().substring(0, 2).equals("01"))
    			{
    				strAdjustStartDate = XMLUtils.getChildNodeValue(node, "ADJUST_START");
        			strAdjustEndDate = XMLUtils.getChildNodeValue(node, "ADJUST_END");
        			
            	    policyAdjustValue = XMLUtils.getChildNodeValue(node, "POLICY_ADJUST_VALUE");
            		
    			}
    			
    		}
    		else
    		{
    			peccancyAdjustReason 	= XMLUtils.getChildNodeValue(node, "PECCANCY_ADJUST_REASON");
        		claimAdjustReason 		= XMLUtils.getChildNodeValue(node, "CLAIM_ADJUST_REASON");
        		driverRateReason 		= XMLUtils.getChildNodeValue(node, "DRIVER_RATE_REASON");
        		districtRateReason 		= XMLUtils.getChildNodeValue(node, "DISTRICT_RATE_REASON");
        		rateFloatFlag 			= XMLUtils.getChildNodeValue(node, "RATE_FLOAT_FLAG");
        		strReInsureFlag			= XMLUtils.getChildNodeValue(node, "REINSURE_FLAG");
            	strLastBillDate			= XMLUtils.getChildNodeValue(node, "LAST_BILL_DATE");
            	
            	strLastyearStartDate    = correctDate(XMLUtils.getChildNodeValue(node, "LAST_START_DATE"));
            	
            	strLastyearEndDate      = correctDateTime(XMLUtils.getChildNodeValue(node, "LAST_END_DATE"));
            	
            	
    		}
    		
    		
    		
    		
    		
    		CIInsureDemandSchema schema = blProposal.getBLCIInsureDemand().getArr(0);  
    		schema.setBasePremium(basedPremium);			
    		schema.setBenchmarkPremium(standPremium);		
    		schema.setPremium(standPremium);				
    		schema.setPeccancyCoeff(peccancyAdjustValue);	
    		schema.setClaimCoeff(claimAdjustValue);			
    		schema.setDriverCoeff(driverRate);				
    		schema.setDistrictCoeff(districtRate);			
    		schema.setCommissionRate(additionRate);			
    		
    		
    		
    		if(strComCode.trim().substring(0, 2).equals("01") ||
    	       strComCode.trim().substring(0, 2).equals("07"))
    	    {
    			
    			
    			if(strComCode.trim().substring(0, 2).equals("01"))
    			{
    				schema.setLastBillDate(strAdjustStartDate);
    				schema.setBillDate(strAdjustEndDate);
    				
    				schema.setPolicyCoeff(policyAdjustValue);
            		
    			}
    			
    	    }
    		else
    		{
    			schema.setPeccancyAdjustReason(peccancyAdjustReason);	
        		schema.setClaimAdjustReason(claimAdjustReason);			
        		schema.setDriverRateReason(driverRateReason);			
        		schema.setDistrictRateReason(districtRateReason);		
        		schema.setRateFloatFlag(rateFloatFlag);					
        		schema.setReinsureFlag(strReInsureFlag);				
        		/* add by wanghe 20100830 begin reason:当平台准许重复XX标志为开时，交易返回正常，但是需要将警告信息提示用户
              	0	非重复XX
              	1	与本公司重复XX
              	2	与其他公司重复XX
              	3	未如实告知车辆信息，无法判断
         	    */
        		if(!"".equals(strReInsureFlag)&&!strReInsureFlag.equals("0")){
        			schema.setErrorMessage(errorMessage);
        		}
        		
            	schema.setLastBillDate(strLastBillDate);				
            	
            	schema.setLastYearStartDate(strLastyearStartDate);
            	schema.setLastYearEndDate(strLastyearEndDate);
            	
    		}
    		
        }
        catch(Exception e)
        {
        	throw e;
        }
    }

    /**
     * 处理PECC_LIST节
     * @param node Node
     * @throws Exception
     */
    protected void processPeccList(BLProposal blProposal, Node node) 
    	throws Exception 
    {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "PECC_DATA");
        
        
        String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
        
        
        if(new UtiPower().checkDrinkCom(strComCode)){
        
          for (int i = 0; i < nodes.length; i++) {
               processPeccDataNew(blProposal, nodes[i]);
          }
        }else{
          for (int i = 0; i < nodes.length; i++) {
               processPeccData(blProposal, nodes[i]);
          }
        }
        
    }

    /**
     * 处理PECC_DATA节
     * @param node Node
     * @throws Exception
     */
    protected void processPeccData(BLProposal blProposal, Node node) 
    	throws Exception 
    {
        
        String peccancyTime 	= XMLUtils.getChildNodeValue(node, "PECCANCY_TIME");
        String peccancyPlace 	= XMLUtils.getChildNodeValue(node, "PECCANCY_PLACE");
        String peccancyCode 	= XMLUtils.getChildNodeValue(node, "PECCANCY_CODE");
        String adjustRate 		= XMLUtils.getChildNodeValue(node, "ADJUST_RATE");
        String peccancyType 	= XMLUtils.getChildNodeValue(node, "PECCANCY_TYPE");
        String certiType 		= XMLUtils.getChildNodeValue(node, "CERTI_TYPE");
        String certiCode 		= XMLUtils.getChildNodeValue(node, "CERTI_CODE");
        String acceptDate 		= XMLUtils.getChildNodeValue(node, "ACCEPT_DATE");
        String remark     		= XMLUtils.getChildNodeValue(node, "PECCANCY_DESC");
        
        String adjustFlag       = "";
        if(new UtiPower().checkCIInsureSH(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	adjustFlag = XMLUtils.getChildNodeValue(node, "ADJUST_FLAG");
        }
        
      
        
        peccancyTime 			= correctDateTime(peccancyTime);
        acceptDate   			= correctDateTime(acceptDate);
        
                
        String demageNo = blProposal.getBLCIInsureDemand().getArr(0).getDemandNo();
        String serialNo = "" + (blProposal.getBLCIInsureDemandLoss().getSize() + 1);
        
        CIInsureDemandLossSchema schema = new CIInsureDemandLossSchema();
        schema.setLossTime(peccancyTime); 			
        schema.setLossAddress(peccancyPlace); 		
        schema.setLossAction(peccancyCode); 		
        schema.setCoeff(adjustRate); 				
        schema.setLossType(peccancyType); 			
        schema.setIdentifyType(certiType); 			
        schema.setIdentifyNumber(certiCode); 		
        schema.setAcceptDate(acceptDate);    		
        schema.setRemark(remark);            		
        
        schema.setDemandNo(demageNo);				
        schema.setSerialNo(serialNo);				
        
        schema.setAdjustFlag(adjustFlag);
        
   
        
        blProposal.getBLCIInsureDemandLoss().setArr(schema);
    }
    /**
     * 处理PECC_DATA节
     * @param node Node
     * @throws Exception
     */
    protected void processPeccDataNew(BLProposal blProposal, Node node) 
    	throws Exception 
    {
        
        
        String strViolationCode            = XMLUtils.getChildNodeValue(node, "PECCANCY_ID");
        String strDecisionCode             = XMLUtils.getChildNodeValue(node, "DECISION_ID");
        String strDecisionTypeCode         = XMLUtils.getChildNodeValue(node, "DECISION_TYPE");
        String peccancyTime 	= XMLUtils.getChildNodeValue(node, "PECCANCY_TIME");
        String acceptDate                  = XMLUtils.getChildNodeValue(node, "MANAGE_TIME");
        String peccancyPlace 	= XMLUtils.getChildNodeValue(node, "PECCANCY_PLACE");
        String peccancyCode 	= XMLUtils.getChildNodeValue(node, "PECCANCY_CODE"); 
        String adjustRate 		= XMLUtils.getChildNodeValue(node, "ADJUST_RATE");
        String strDriverLicenseNo          = XMLUtils.getChildNodeValue(node, "DRIVING_LICENCE");
        
        String strProcessingStatus      		= XMLUtils.getChildNodeValue(node, "PROCESSING_STATUS");
        
        
        String strDriverName      		= XMLUtils.getChildNodeValue(node, "DRIVER_NAME");
        String strPeccancyDetail      		= XMLUtils.getChildNodeValue(node, "PECCANCY_DETAIL");
        
        
        peccancyTime 			= correctDateTime(peccancyTime);
        acceptDate   			= correctDateTime(acceptDate);
        
                
        String demageNo = blProposal.getBLCIInsureDemand().getArr(0).getDemandNo();
        String serialNo = "" + (blProposal.getBLCIInsureDemandLoss().getSize() + 1);
        
        CIInsureDemandLossSchema schema = new CIInsureDemandLossSchema();
        
        schema.setLossTime(peccancyTime); 			
        schema.setLossAddress(peccancyPlace); 		
        schema.setLossAction(peccancyCode); 		
        schema.setCoeff(adjustRate); 				
        schema.setAcceptDate(acceptDate);    		
        schema.setPeccancyNumber(strViolationCode);
        schema.setDecisionCode(strDecisionCode);
        schema.setDecisionTypeCode(strDecisionTypeCode);
        schema.setIdentifyNumber(strDriverLicenseNo);
      
        schema.setDemandNo(demageNo);				
        schema.setSerialNo(serialNo);				
        
        schema.setProcessingStatus(strProcessingStatus);
        
        
        schema.setDriverName(strDriverName);
        schema.setPeccancyDetail(strPeccancyDetail);
        
        
        blProposal.getBLCIInsureDemandLoss().setArr(schema);
    }

    /**
     * 处理CLAIM_LIST节
     * @param node Node
     * @throws Exception
     */
    protected void processClaimList(BLProposal blProposal, Node node) 
       throws Exception 
    {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CLAIM_DATA");
        
        for (int i = 0; i < nodes.length; i++) {
            processClaimData(blProposal, nodes[i]);
        }
    }
    
    /**
     * 处理BUSI_CLAIM_LIST节
     * @param node Node
     * @throws Exception
     */
    protected void processBusiClaimList(BLProposal blProposal, Node node) 
       throws Exception 
    {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "BUSI_CLAIM_DATA");
        
        for (int i = 0; i < nodes.length; i++) {
            processClaimData(blProposal, nodes[i]);
        }
    }
    

    /**
     * 处理CLAIM_DATA节
     * @param node Node
     * @throws Exception
     */
    protected void processClaimData(BLProposal blProposal, Node node) 
       throws Exception 
    {
        
        String companyId 		= XMLUtils.getChildNodeValue(node, "COMPANY_ID");
        
        
        String claimNo = "";
        if(!new UtiPower().checkInterAgreementSX(blProposal.getBLPrpTmain().getArr(0).getComCode())
        		&&!new UtiPower().checkInterfaceHLJ(blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	claimNo 			= XMLUtils.getChildNodeValue(node, "CLAIM_NO");
        }
        
        
        String regisreationNo 	= XMLUtils.getChildNodeValue(node, "REGISREATION_NO");
        String accidentTime 	= XMLUtils.getChildNodeValue(node, "ACCIDENT_TIME");
        String endCaseTime 		= XMLUtils.getChildNodeValue(node, "ENDCASE_TIME");
        
        String optionType = "";
        String totalAmount = "";
        
        
        String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
        String accidentDeathFlag = "";	
        if(strComCode.trim().substring(0, 2).equals("01") ||
           strComCode.trim().substring(0, 2).equals("07"))
        {
        	
        	if(blProposal.getBLPrpTmain().getArr(0).getRiskCode().equals("0507")){
        		accidentDeathFlag = XMLUtils.getChildNodeValue(node, "CLAIM_TYPE");
        		totalAmount = XMLUtils.getChildNodeValue(node, "TOTAL_AMOUNT");
        	}else{
        		optionType = XMLUtils.getChildNodeValue(node, "OPTION_TYPE");
        		totalAmount = XMLUtils.getChildNodeValue(node, "TOTAL_AMOUNT");
        	}
        	
        }
        else
        {
        	accidentDeathFlag = XMLUtils.getChildNodeValue(node, "ACCIDENT_DEATH");
        }
        
        
        
        accidentTime 			= correctDateTime(accidentTime);
        endCaseTime 			= correctDate(endCaseTime);
        
        CIInsureDemandPaySchema schema = new CIInsureDemandPaySchema();
        schema.setPayCompany(companyId);	
        
        schema.setCompensateNo(claimNo); 	
        schema.setLossTime(accidentTime);	
        schema.setEndCaseTime(endCaseTime);	
        
        schema.setOptionType(optionType);
        schema.setTotalAmount(totalAmount);
        
        
        if(strComCode.trim().substring(0, 2).equals("01") ||
           strComCode.trim().substring(0, 2).equals("07"))
        {
           
           
           
           
        	   schema.setAccidentDeathFlag(accidentDeathFlag);	
           
        }
        else
        {
        	schema.setAccidentDeathFlag(accidentDeathFlag);	
        }
        
        
        
        if(new UtiPower().checkSendEffectiveHour(strComCode)||new UtiPower().checkInterAgreementSX(strComCode)
        		|| new UtiPower().checkInterfaceHLJ(strComCode)){
            String claimCode        = XMLUtils.getChildNodeValue(node, "CLAIM_CODE");
            String policyNo         = XMLUtils.getChildNodeValue(node, "POLICY_NO");
            String claimAmount      = XMLUtils.getChildNodeValue(node, "CLAIM_AMOUNT");
            schema.setCompensateNo(claimCode);    
            schema.setTotalAmount(claimAmount);  
            schema.setPolicyNo(policyNo);   
        }
        
        
        
        
        
        
        
        if(!"01".equals(strComCode.trim().substring(0, 2))&&!"07".equals(strComCode.trim().substring(0, 2)) 
                && !new UtiPower().checkSendEffectiveHour(strComCode)&&!new UtiPower().checkInterAgreementSX(strComCode)
                && !new UtiPower().checkInterfaceHLJ(strComCode)){
        	
        	
        
        	processClaimCoverListForLossFee(blProposal, schema, XMLUtils.getChildNodeByTagName(node, "CLAIM_COVER_LIST"));
        }
        
        
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CLAIM_COVER_DATA");
        
        
        if(nodes.length > 0)
        {
        	processClaimCoverList(blProposal, schema, XMLUtils.getChildNodeByTagName(node, "CLAIM_COVER_LIST"));
        }
        else
        {
            
        	schema.setDemandNo(blProposal.getBLCIInsureDemand().getArr(0).getDemandNo());
        	schema.setSerialNo("" + (blProposal.getBLCIInsureDemandPay().getSize() + 1));

            String strAccidentDeathFlag = "";
            if(strComCode.trim().substring(0, 2).equals("01") ||
                    strComCode.trim().substring(0, 2).equals("07"))
            {
            	
            	
            	
            		strAccidentDeathFlag = schema.getAccidentDeathFlag();
            	
            }
            else
            {
            	strAccidentDeathFlag = schema.getAccidentDeathFlag();
            }
            schema.setAccidentDeathFlag(strAccidentDeathFlag);  
            blProposal.getBLCIInsureDemandPay().setArr(schema);
        }
    }

    /**
     * 处理CLAIM_COVER_LIST节
     * @param node Node
     * @throws Exception
     */
    protected void processClaimCoverList(BLProposal blProposal, 
    		                             final CIInsureDemandPaySchema schema, 
    		                             Node node)
      throws Exception {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CLAIM_COVER_DATA");
        
        for (int i = 0; i < nodes.length; i++) {
            processClaimCoverData(blProposal, schema, nodes[i]);
        }
    }
    /**
     * 处理CLAIM_COVER_DATA节
     * @param node Node
     * @throws Exception
     */
    protected void processClaimCoverData(BLProposal blProposal, 
    		                             final CIInsureDemandPaySchema ciInsureDemandPaySchema,
                                         Node node) 
       throws Exception 
    {
        
        String policyNo 	= XMLUtils.getChildNodeValue(node, "POLICY_NO");
        String coverageType = XMLUtils.getChildNodeValue(node, "COVERAGE_TYPE");
        String claimAmount 	= XMLUtils.getChildNodeValue(node, "CLAIM_AMOUNT");
        
        String companyId 	= ciInsureDemandPaySchema.getPayCompany();
        String compensateNo = ciInsureDemandPaySchema.getCompensateNo();
        String lossTime 	= ciInsureDemandPaySchema.getLossTime();
        String endCaseTime  = ciInsureDemandPaySchema.getEndCaseTime();
        String demageNo 	= blProposal.getBLCIInsureDemand().getArr(0).getDemandNo();
        String serialNo	 	= "" + (blProposal.getBLCIInsureDemandPay().getSize() + 1);
        
        
        String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
        String strAccidentDeathFlag = "";
        if(strComCode.trim().substring(0, 2).equals("01") ||
                strComCode.trim().substring(0, 2).equals("07"))
        {
        	
        	
        	
        		strAccidentDeathFlag = ciInsureDemandPaySchema.getAccidentDeathFlag();
        	
        }
        else
        {
        	strAccidentDeathFlag = ciInsureDemandPaySchema.getAccidentDeathFlag();
        }
        
        
        CIInsureDemandPaySchema schema = new CIInsureDemandPaySchema();
        schema.setPayCompany(companyId);		
        schema.setCompensateNo(compensateNo); 	
        schema.setLossTime(lossTime);			
        schema.setEndCaseTime(endCaseTime);		
        schema.setPolicyNo(policyNo);			
        schema.setPayType(coverageType);		
        schema.setLossFee(claimAmount);			
        schema.setDemandNo(demageNo);			
        schema.setSerialNo(serialNo);			
        
        
        schema.setOptionType(ciInsureDemandPaySchema.getOptionType());
        schema.setTotalAmount(ciInsureDemandPaySchema.getTotalAmount());
        
        
        
        schema.setAccidentDeathFlag(strAccidentDeathFlag);  
        
        
        blProposal.getBLCIInsureDemandPay().setArr(schema);
    }
    
    /**
     * 处理CLAIM_COVER_LIST节
     * @param node Node
     * @throws Exception
     */
    protected void processClaimCoverListForLossFee(BLProposal blProposal, 
    		                             final CIInsureDemandPaySchema schema, 
    		                             Node node)
      throws Exception {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CLAIM_COVER_DATA");
        
        double dbTotalAmount =0.0d;
        for (int i = 0; i < nodes.length; i++) {
            String claimAmount 	= XMLUtils.getChildNodeValue(nodes[i], "CLAIM_AMOUNT");
            dbTotalAmount +=Double.parseDouble(ChgData.chgStrZero(claimAmount));
        }
        schema.setLossFee(""+dbTotalAmount);
        schema.setTotalAmount(""+dbTotalAmount);
    }
    
    protected void processVehicleTaxation(BLProposal blProposal, Node node)throws Exception{
        ProposalCarShipTaxQueryDecoder proposalCarShipTaxQueryDecoder =  new ProposalCarShipTaxQueryDecoder();
        proposalCarShipTaxQueryDecoder.processVehicleTaxation(blProposal,node);
    }
    
    
    /**
     * 处理VEHICLE_PRICE_LIST节
     * @param node Node
     * @throws Exception
     */
    protected void processVehiclePriceList(BLProposal blProposal,Node node)throws Exception {
    	 Node[] nodes = XMLUtils.getChildNodesByTagName(node, "VEHICLE_PRICE_DATA");
         
         for (int i = 0; i < nodes.length; i++) {
        	 processVehiclePrice(blProposal, nodes[i]);
         }
    }
    
    /**
     * 处理VEHICLE_PRICE节
     * @param node Node
     * @throws Exception
     */
    protected void processVehiclePrice(BLProposal blProposal,Node node)throws Exception {
	   	String strVehicleCode=XMLUtils.getChildNodeValue(node, "VEHICLE_CODE");           
	   	String strVehicleName=XMLUtils.getChildNodeValue(node, "R_VEHICLE_NAME");           
	   	String strVehicleFamily=XMLUtils.getChildNodeValue(node, "R_VEHICLE_FAMILY");         
	   	String strVehicleDescription=XMLUtils.getChildNodeValue(node, "VEHICLE_DESCRIPTION");   
	   	String strMarket_Date=XMLUtils.getChildNodeValue(node, "R_MARKET_DATE");           
	   	String strVehiclePrice=XMLUtils.getChildNodeValue(node, "VEHICLE_PRICE");         
	   	String strRefcode1=XMLUtils.getChildNodeValue(node, "REFCODE1");            
	   	String strRefcode2=XMLUtils.getChildNodeValue(node, "REFCODE2");            
   }
   
    
    protected void processRiskWarning(BLProposal blProposal,Node node)throws Exception{
	   	 Node[] nodes = XMLUtils.getChildNodesByTagName(node, "RISK_WARNING");
	     
	     for (int i = 0; i < nodes.length; i++) {
	    	 processRiskWarningList(blProposal, nodes[i]);
	     }    	
    	
    }
    
    protected void processCommission(BLProposal blProposal,Node node)throws Exception{
    	String strCommissionRateUpper = XMLUtils.getChildNodeValue(node, "COMMISSION_RATE_UPPER");
    	CIInsureDemandSchema schema = blProposal.getBLCIInsureDemand().getArr(0);
    	schema.setCommissionrateupper(strCommissionRateUpper);
    }
    
    protected void processRiskWarningList(BLProposal blProposal,Node node)throws Exception {
	   	String strRiskWarningType=XMLUtils.getChildNodeValue(node, "RISK_WARNING_TYPE");
	   	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "RISK_WARNING_CLAIM_ITEM");
	     
	    for (int i = 0; i < nodes.length; i++) {
	    	 processRiskWarningClaimItem(blProposal, nodes[i],strRiskWarningType);
	    } 
    }
    protected void processRiskWarningClaimItem(BLProposal blProposal,Node node,String strRiskWarningType)throws Exception {
    	String strLossTime = XMLUtils.getChildNodeValue(node, "ACCIDENT_TIME");
    	String strlossArea = XMLUtils.getChildNodeValue(node, "ACCIDENT_PLACE");
    	String strPayCompany = XMLUtils.getChildNodeValue(node, "COMPANY_ID");
    	String strCompensateNo = XMLUtils.getChildNodeValue(node, "CLAIM_CODE");
    	
    	strLossTime 			= correctDateTime(strLossTime);
    	CIInsureDemandPaySchema schema = new CIInsureDemandPaySchema();
    	schema.setDemandNo(blProposal.getBLCIInsureDemand().getArr(0).getDemandNo());
    	schema.setSerialNo("" + (blProposal.getBLCIInsureDemandPay().getSize() + 1));
    	schema.setLossTime(strLossTime);
    	schema.setLossArea(strlossArea);
    	schema.setPayCompany(strPayCompany);
    	schema.setCompensateNo(strCompensateNo);
    	schema.setRiskWarningType(strRiskWarningType);
    	blProposal.getBLCIInsureDemandPay().setArr(schema);
    	
    }
    
    
    protected void processOwnerMessage(BLProposal blProposal,Node node)throws Exception {
    	String strOwnerMessageType = XMLUtils.getChildNodeValue(node, "OWNER_MESSAGE_TYPE");
    	CIInsureDemandSchema schema = blProposal.getBLCIInsureDemand().getArr(0);
    	schema.setCarOwnerMessage(strOwnerMessageType);
    }
    

    
    /**
     * 处理DUPLICATED_LIST节
     * 
     * @param iSchema 大对象
     * @param node Node节点
     * @throws Exception
     */
    protected void processDuplicatedList(BLProposal iSchema, Node node)
            throws Exception {
        int intSerialNo = 1;
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "DUPLICATED_DATA");
        iSchema.getBlCIInsureDemandRisk().initArr();
        for (int i = 0; i < nodes.length; i++) {
            intSerialNo = processDuplicatedData(iSchema, nodes[i], intSerialNo);
        }
    }

    /**
     * 处理DUPLICATED_DATA节、COVERAGE_LIST节
     * 
     * @param iSchema BLProposal大对象
     * @param node Node节点
     * @param serialFirst 序号
     * @throws Exception
     */
    protected int processDuplicatedData(BLProposal iSchema, Node iNode, int iSerialNo)
            throws Exception {
        Node coListNode = XMLUtils.getChildNodeByTagName(iNode, "COVERAGE_LIST");
        Node[] nodes = XMLUtils.getChildNodesByTagName(coListNode,
                "COVERAGE_DATA");
        String strDemandNo = iSchema.getBLCIInsureDemand().getArr(0).getDemandNo();
        for (int i = 0; i < nodes.length; i++) {
            CIInsureDemandRiskSchema demandRiskSchema = new CIInsureDemandRiskSchema();
            demandRiskSchema.setDemandNo(strDemandNo);
            demandRiskSchema.setSerialNo(String.valueOf(iSerialNo++));
            demandRiskSchema.setCompanyID(XMLUtils.getChildNodeValue(iNode, "COMPANY_ID"));
            demandRiskSchema.setStartDate(correctDate(XMLUtils.getChildNodeValue(iNode, "START_DATE")));
            demandRiskSchema.setEndDate(correctDate(XMLUtils.getChildNodeValue(iNode, "END_DAT")));
            demandRiskSchema.setOwner(XMLUtils.getChildNodeValue(iNode, "OWNER"));
            processCoverageData(demandRiskSchema, nodes[i]);
            iSchema.getBlCIInsureDemandRisk().setArr(demandRiskSchema);
        }
        return iSerialNo;
    }
    
    
    private void processUseTypeMessage(BLProposal iSchema, Node iNode) throws Exception {
    	String strUseTypeMessageType = XMLUtils.getChildNodeValue(iNode, "USE_TYPE_MESSAGE_TYPE");
    	CIInsureDemandSchema schema = iSchema.getBLCIInsureDemand().getArr(0);
    	schema.setUsageTypeMessage(strUseTypeMessageType);
    }
    

    /**
     * 处理COVERAGE_DATA节
     * 
     * @param iSchema CIInsureDemandRiskSchema对象
     * @param node Node节点
     * @throws Exception
     */
    protected void processCoverageData(CIInsureDemandRiskSchema iSchema,
            Node node) throws Exception {
        iSchema.setComCoverageName(XMLUtils.getChildNodeValue(node,
                "COM_COVERAGE_NAME"));
        iSchema.setLimitAmount(XMLUtils.getChildNodeValue(node, "LIMIT_AMOUNT"));
        iSchema.setKindStartDate(correctDate(XMLUtils.getChildNodeValue(
                node, "START_DATE")));
        iSchema.setKindEndDate(correctDate(XMLUtils.getChildNodeValue(node,
                "END_DATE")));
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
