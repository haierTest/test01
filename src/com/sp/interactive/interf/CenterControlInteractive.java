package com.sp.interactive.interf;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.nutz.ssdb4j.SSDB2m;
import org.nutz.ssdb4j.spi.Response;

import com.asn1c.core.Identification;
import com.sp.interactive.Service.CalculationPremiumTM;
import com.sp.interactive.Service.CarQueryService;
import com.sp.interactive.Service.IdentityCardStorage;
import com.sp.interactive.Service.IdentityVerification;
import com.sp.interactive.Service.ObjectiveCarInfoInterf;
import com.sp.interactive.Service.InformationCollection;
import com.sp.interactive.Service.ProposalQueryService;
import com.sp.interactive.Service.CreateOrderMessageService;
import com.sp.interactive.Service.QuotationZHUtils;
import com.sp.interactive.Service.StateBomInOut;
import com.sp.interactive.Service.UnderwriteInteractiveService;
import com.sp.interactive.Service.UndwrtAndUndwrtInfo;
import com.sp.phonesale.interf.BasicInfoQueryServiceEncoder;
import com.sp.phonesale.interf.CheckProposalUniqueEncoder;
import com.sp.phonesale.interf.EndorseCarLicenseNoEncoder;
import com.sp.phonesale.interf.InsureCarLuggageEncoder;
import com.sp.phonesale.schema.TargetMarketInfoSchema;
import com.sp.phonesale.service.BasicInfoQueryService;
import com.sp.phonesale.service.CheckProposalUnique;
import com.sp.phonesale.service.EndorseCarLicenseNo;
import com.sp.phonesale.service.InsureCarLuggage;
import com.sp.phonesale.service.LifeTableQuery;
import com.sp.phonesale.service.ProposalVerify;
import com.sp.phonesale.service.ProposalVerifyEncoder;
import com.sp.prpall.blsvr.cb.BLPrpBasicInfoCaChe;
import com.sp.prpall.blsvr.misc.BLPrpRuleUndwrtInfo;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTexpense;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.schema.PrpTexpenseSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.quotation.blsvr.PremiumCalService;
import com.sp.quotation.interf.TBPlatformXMLEncoder;
import com.sp.quotation.pub.CheckMsgUtility;
import com.sp.quotation.pub.QuotationUtils;
import com.sp.utiall.blsvr.BLPrpDcarModel;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;
import com.sp.utility.SysConfig;
import com.sp.prpall.pubfun.LifeTableInterf;
import com.sp.prpall.pubfun.PremiumCalCarNew;
import com.sunshine.ruleapp.bomPreUwrt.BomRuleResult;
import com.sunshine.ruleapp.bomPreUwrt.BomPolicy;
import com.sunshine.ruleapp.client.RuleResultClient;
import com.sinosig.rulesevapp.client.RuleSevResultClient;
import com.sinosig.utils.BusinessKey;
import com.sp.interactive.Service.GetBomPolicyUtil;
import com.sinosig.utils.SpringUtils;
import com.sp.prpall.pubfun.prpallSSDB;
import com.sp.interactive.Service.CalculationPureRiskPremium;
/**
 * 集中处理类
 * @author qilin
 * */

public class CenterControlInteractive {
	    
		public String serviceProcess(String content)throws Exception{

	    	Document doc = DocumentHelper.parseText(content);
	    	Element root = doc.getRootElement();
	    	
	    	String requestType = "";
	    	if(root.element("HEAD")!=null){
	    		requestType = root.element("HEAD").elementText("REQUESTTYPE");
	    		
	    		Element head = root.element("HEAD");
	    		if(head.element("TRANSTYPE") != null){
	    			requestType = head.elementText("TRANSTYPE");
	    		}
	    		
	    	}
	    	if(requestType==null || "".equals(requestType)){
	    		if(root.element("Request_Header")!=null){
	    			requestType = root.element("Request_Header").elementText("Trans_Type");
	    		}
	    	}
	    	
	        DbPool dbPool = new DbPool();
	        
	        Map blProposalMap = new HashMap();
	        Map blPolicyMap = new HashMap();
	        Map paramMap = new HashMap();
	        Map headerMap = new HashMap();
	        Map resultMap = null;
	        Exception quotationE=null;
	        
	        try{
	            
	        	CarQueryService carQueryService = new CarQueryService();
	          
	           if("01".equals(requestType)){
	                
	                return carQueryService.queryCarInfo(dbPool,doc);
	            }else if("02".equals(requestType)){
	            	
    				
    				if(!"".equals(root.element("BODY").elementText("STARTDATECI").trim())){
    					blProposalMap.put(QuotationZHUtils.BLPROPOSALCI, new BLProposal());
    				}
    				
    				if(!"".equals(root.element("BODY").elementText("STARTDATEBI").trim())){
    					blProposalMap.put(QuotationZHUtils.BLPROPOSALBI, new BLProposal());
    				}
					
    				TBQuotationZHXMLDecoder decoder = new TBQuotationZHXMLDecoder();
    				decoder.decode(doc, paramMap, blProposalMap);
    				
    				String requestIP = decoder.request_ip;  
    				String requestIP_Scope = "";
    				String OperateSite = "";
    				
    				String OperateSiteBC = "";
    				String InfoTransNo = (String)paramMap.get("InfoTransNo");
    				String RiskCodeBI = (String)paramMap.get("RiskCodeBI");
    				OperateSiteBC = (String)paramMap.get("OperateSiteBC");
    				
    				BLProposal blProposalCI = (BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALCI);
    				BLProposal blProposalBI = (BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI);
    				
    				BLPrpBasicInfoCaChe blPrpBasicInfoCaCheCI = new BLPrpBasicInfoCaChe(); 
    				BLPrpBasicInfoCaChe blPrpBasicInfoCaCheBI = new BLPrpBasicInfoCaChe(); 
    				CheckMsgUtility checkMsgUtility = new CheckMsgUtility();
    				if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
    					
    					if(!paramMap.get("SPECIALISTRATE").equals("") && paramMap.get("SPECIALISTRATE") != null ){
    						if(blProposalBI != null){
    							BLPrpTitemKind blPrpTitemKind = blProposalBI.getBLPrpTitemKind();
    							for(int i = 0;i<blPrpTitemKind.getSize();i++){
    								if(blPrpTitemKind.getArr(i).getKindCode().equals("Q3")){
    									blPrpTitemKind.getArr(i).setRate((String)paramMap.get("SPECIALISTRATE"));
    								}
    							}
    						}
    					}
    					
    					
    					if(blProposalCI != null&&blProposalCI.getBLPrpTmain()!=null&&blProposalCI.getBLPrpTmain().getSize()>0&&blProposalBI == null){
    						if(blProposalCI.getBLPrpTitemCar()!=null&&blProposalCI.getBLPrpTitemCar().getSize()>0){
    							blProposalCI.getBLPrpTitemCar().getArr(0).setRelievingAreaCode("6");
    						}
    					}else if(blProposalBI != null&&blProposalBI.getBLPrpTmain()!=null&&blProposalBI.getBLPrpTmain().getSize()>0&&blProposalCI == null){
    						if(blProposalBI.getBLPrpTitemCar()!=null&&blProposalBI.getBLPrpTitemCar().getSize()>0){
    							blProposalBI.getBLPrpTitemCar().getArr(0).setRelievingAreaCode("1");
    						}
    					}else if(blProposalCI != null&&blProposalCI.getBLPrpTmain()!=null&&blProposalCI.getBLPrpTmain().getSize()>0&&blProposalBI != null&&blProposalBI.getBLPrpTmain()!=null&&blProposalBI.getBLPrpTmain().getSize()>0){
    						if(blProposalCI.getBLPrpTitemCar()!=null&&blProposalCI.getBLPrpTitemCar().getSize()>0){
    							blProposalCI.getBLPrpTitemCar().getArr(0).setRelievingAreaCode("8");
    						}
    						if(blProposalBI.getBLPrpTitemCar()!=null&&blProposalBI.getBLPrpTitemCar().getSize()>0){
    							blProposalBI.getBLPrpTitemCar().getArr(0).setRelievingAreaCode("8");
    						}
    					}
    					
    					
    					if(blProposalCI != null){
    						
    						String riskcodeCI = blProposalCI.getBLPrpTmain().getArr(0).getRiskCode();
    						
    						blPrpBasicInfoCaCheCI.getData(InfoTransNo, riskcodeCI);
    						
    						
    						
    						Response	ruleInfoCI=prpallSSDB.hget("prpBasicInfoCaChe",blPrpBasicInfoCaCheCI.getArr(0).getRuleInfoXml());
    						
    						
    						
    						CheckRuleInfo checkRule = new CheckRuleInfo();
    						
    						checkRule.checkRule(blProposalCI,ruleInfoCI,paramMap);
    						
    						
    						
    						Response	responseCI=prpallSSDB.hget("prpBasicInfoCaChe",blPrpBasicInfoCaCheCI.getArr(0).getPrpInfo());
    						
    						
    						String checkXMLCI = checkMsgUtility.generateCheckXMLApp((BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALCI),"20037");
    						Map checkMapCI = checkMsgUtility.CheckXMLApp(checkXMLCI,responseCI.asString());
    						if(checkMapCI.size() > 0){
    							String Error_Code = "100000";
    							StringBuffer sb = new StringBuffer();
    							Iterator it = checkMapCI.entrySet().iterator();
    							while (it.hasNext()){
    								Map.Entry entry = (Entry) it.next();
    								String key = (String) entry.getKey();
    								String value = (String) entry.getValue();
    								sb.append(key+" ");
    								sb.append(value+" ");
    							}
    							paramMap.put("Error_Code",Error_Code);
        						paramMap.put("Error_Message",sb.toString());
        						throw new Exception();
    						}
    					}
    					
    					if(blProposalBI != null){
    						Response ruleInfoBI =null;
    						Response responseBI =null;
    						
    						 blProposalBI.getBLPrpTmain().getArr(0).setRiskCode(RiskCodeBI);
    						
    						blPrpBasicInfoCaCheBI.getData(InfoTransNo, RiskCodeBI);
    						
    						
    						
    							ruleInfoBI=prpallSSDB.hget("prpBasicInfoCaChe",blPrpBasicInfoCaCheBI.getArr(0).getRuleInfoXml());
    						
    						
    						CheckRuleInfo checkRule = new CheckRuleInfo();
    						
    						checkRule.checkRule(blProposalBI,ruleInfoBI,paramMap);
    						
    							responseBI=prpallSSDB.hget("prpBasicInfoCaChe",blPrpBasicInfoCaCheBI.getArr(0).getPrpInfo());
    						
    						String checkXMLBI = checkMsgUtility.generateCheckXMLApp((BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI),"20037");
    						Map checkMapBI = checkMsgUtility.CheckXMLApp(checkXMLBI,responseBI.asString());
    						if(checkMapBI.size() > 0){
    							String Error_Code = "100000";
    							StringBuffer sb = new StringBuffer();
    							Iterator it = checkMapBI.entrySet().iterator();
    							while (it.hasNext()){
    								Map.Entry entry = (Entry) it.next();
    								String key = (String) entry.getKey();
    								String value = (String) entry.getValue();
    								sb.append(key+" ");
    								sb.append(value+" ");
    							}
    							paramMap.put("Error_Code",Error_Code);
        						paramMap.put("Error_Message",sb.toString());
        						throw new Exception();
    						}
    					}
    				}
    				
    				
    				BLPrpDcode blPrpDcode = new BLPrpDcode();
    				if(!"".equals(requestIP) && requestIP !=null){
    					String[] ip = requestIP.split("\\.");
        				requestIP_Scope = ip[0]+"."+ip[1]+"."+ip[2];   
        				if(!"".equals(requestIP_Scope) && requestIP_Scope != null){
    						OperateSite = blPrpDcode.translateCode("AgencyOperateSite",requestIP_Scope, false);
    						if(OperateSite==null||"".equals(OperateSite)){
    							throw new Exception("用户的网段:"+requestIP_Scope+"未配置,请联系管理员维护!");
    						}
        				}
					}
    				if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
    					OperateSite = OperateSiteBC;
    				}
    				
    				

    				paramMap.put(QuotationUtils.QUTATIONTYPE,QuotationUtils.INSUREQUOTATION);
    				paramMap.put(QuotationUtils.IS_PRPALL,"0");
    				
    				Datadeal datadeal = new Datadeal();
       	   			


    				Map paramMapCar = new HashMap();
					BLPrpDcarModel blPrpDcarModel = new BLPrpDcarModel();
					if(SysConfig.getProperty("InteractiveCarAuto").indexOf(","+OperateSite+",")>-1){
	    				if(blProposalMap.get(QuotationZHUtils.BLPROPOSALBI)!=null){
	    					
	    					blProposalBI.getBLPrpTmain().getArr(0).setRiskCode(UtiPower.ChangeRiskCode0528("0508",blProposalBI.getBLPrpTmain().getArr(0).getComCode()));
	    					
	    					blPrpDcarModel = datadeal.CarInfoQuery(blProposalBI,paramMapCar);
	    					datadeal.processBLPrpTitemCar(blProposalBI, blPrpDcarModel, paramMapCar);
	    					if(blProposalMap.get(QuotationZHUtils.BLPROPOSALCI)!=null){
	        					datadeal.processBLPrpTitemCar(blProposalCI, blPrpDcarModel, paramMapCar);
	        				}
	    				}else if(blProposalMap.get(QuotationZHUtils.BLPROPOSALCI)!=null){
	    					blProposalCI.getBLPrpTmain().getArr(0).setRiskCode("0507");
	    					blPrpDcarModel = datadeal.CarInfoQuery(blProposalCI,paramMapCar);
	    					datadeal.processBLPrpTitemCar(blProposalCI, blPrpDcarModel, paramMapCar);
	    				}
					}
    				
    				
    				
    				PremiumCalService premiumCalService = new PremiumCalService();
    				if(blProposalMap.get(QuotationZHUtils.BLPROPOSALCI)!=null){
    					
    					
    					paramMap.put("RiskCode", "0507");
    					blProposalCI.getBLPrpTmain().getArr(0).setRiskCode("0507");
    					
    					blProposalCI.getBLPrpTmain().getArr(0).setOperateSite(OperateSite);
    					
           	   			datadeal.DataDealFirst(blProposalCI,paramMap);          
           	   			blProposalCI =premiumCalService.caculate(paramMap, (BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALCI));
    					datadeal.DataDealSecound(blProposalCI);
    					
    				}
    				if(blProposalMap.get(QuotationZHUtils.BLPROPOSALBI)!=null){
    					
    					
    					
    					if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
    						paramMap.put("RiskCode", RiskCodeBI);
    						blProposalBI.getBLPrpTmain().getArr(0).setRiskCode(RiskCodeBI);
    					}else{
    					
    					String comCode = blProposalBI.getBLPrpTmain().getArr(0).getComCode();	
    					paramMap.put("RiskCode", UtiPower.ChangeRiskCode0528("0508",comCode));
    					blProposalBI.getBLPrpTmain().getArr(0).setRiskCode(UtiPower.ChangeRiskCode0528("0508",comCode));   
    					
    					
    					}
    					
    					blProposalBI.getBLPrpTmain().getArr(0).setOperateSite(OperateSite);
    					
    					datadeal.DataDealFirst(blProposalBI,paramMap); 
           	   		    datadeal.queryKindMainSub(blProposalBI, paramMap);
           	   		blProposalBI = premiumCalService.caculate(paramMap, (BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI)); 
    					datadeal.DataDealSecound(blProposalBI);
    				}
    				
    				if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1){
    					
    					PrpRelationUtil prpRelationUtil = new PrpRelationUtil();
    					prpRelationUtil.prpRelation(blProposalCI,blProposalBI);
    					
    					OthObjSchSetValue othObjSchSetValue = new OthObjSchSetValue();
    					othObjSchSetValue.SchSetValue(blProposalCI,blProposalBI,"","",paramMap,resultMap);
    					LifeTableInfoInteractive lifeTableInfoInteractive = new LifeTableInfoInteractive();
						
    					if(blProposalCI !=null){
    						String ChannelType = blProposalCI.getBLPrpTmain().getArr(0).getChannelType();
    						
    						
    						if(ChannelType.startsWith("N07") || ChannelType.startsWith("N10")){
    							
    							blProposalCI.getBLPrpTmain().getArr(0).setChannelTypeOrigin(ChannelType);
    						}    						
    					}
    					
    					if(blProposalBI !=null){
    						String ChannelType = blProposalBI.getBLPrpTmain().getArr(0).getChannelType();
    						
    						
    						if(ChannelType.startsWith("N07") || ChannelType.startsWith("N10")){
    							
    							blProposalBI.getBLPrpTmain().getArr(0).setChannelTypeOrigin(ChannelType);
    						}    						
    					}
    					
    					
    					if(blProposalMap.get(QuotationZHUtils.BLPROPOSALCI)!=null){
    					    lifeTableInfoInteractive.lifeTableInfoApp((BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALCI), "0507");
    					    
    					    TargetMarketInfoSchema targetMarketInfoSchemaCI=new TargetMarketInfoSchema();
    					    String  RENEWALCIFLAG = (String) paramMap.get("RENEWALCIFLAG");
    					    
    					    if("1".equals(RENEWALCIFLAG)){
    					    	blProposalCI.getBLPrpTmain().getArr(0).setFlag("0");
    					    }else{
    					    	blProposalCI.getBLPrpTmain().getArr(0).setFlag("2");

    					    }
    					    LifeTableQuery lifeTableQuery=new LifeTableQuery();
    					    lifeTableQuery.QueryFee((BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALCI), targetMarketInfoSchemaCI);
    					    paramMap.put("targetMarketInfoSchemaCI", targetMarketInfoSchemaCI);
    					    
    					}
    					
    					if(blProposalMap.get(QuotationZHUtils.BLPROPOSALBI)!=null){
    						lifeTableInfoInteractive.lifeTableInfoApp((BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI), RiskCodeBI);
    						 
    						TargetMarketInfoSchema targetMarketInfoSchemaBI=new TargetMarketInfoSchema();
    						String RENEWALBIFLAG=(String) paramMap.get("RENEWALBIFLAG");
    						 
    						if("1".equals(RENEWALBIFLAG)){
    							blProposalBI.getBLPrpTmain().getArr(0).setFlag("0");
    						}else{
    							blProposalBI.getBLPrpTmain().getArr(0).setFlag("2");
    						}
     					    LifeTableQuery lifeTableQuery=new LifeTableQuery();
     					    lifeTableQuery.QueryFee((BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI), targetMarketInfoSchemaBI);
    						paramMap.put("targetMarketInfoSchemaBI", targetMarketInfoSchemaBI);
    						 
    					}
    					
    				     LifeTableInterf lifetableinterf = new LifeTableInterf();
    				     BLProposal blProposalCItemp = new BLProposal();
    				     BLProposal blProposalBItemp = new BLProposal();
    				     blProposalCItemp = (BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALCI);
    				     blProposalBItemp = (BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI);
    				     if(blProposalCItemp!=null && !"".equals(blProposalCItemp) &&  blProposalBItemp!=null && !"".equals(blProposalBItemp)){
    				    	 lifetableinterf.calProfitMarginBC(blProposalBItemp.getBLPrpTmain().getArr(0),blProposalCItemp.getBLPrpTmain().getArr(0).getProfitMargin(),
    				    			 blProposalCItemp.getBLPrpTmain().getArr(0).getRiskPremium(),blProposalCItemp.getBLPrpTmain().getArr(0).getSumPremium());
    				    	 blProposalCItemp.getBLPrpTmain().getArr(0).setCostMarginBC(blProposalBItemp.getBLPrpTmain().getArr(0).getCostMarginBC());
    				    	 blProposalCItemp.getBLPrpTmain().getArr(0).setCostBusinessClassBC(blProposalBItemp.getBLPrpTmain().getArr(0).getCostBusinessClassBC());
    				    	 blProposalCItemp.getBLPrpTmain().getArr(0).setFinalPayRateBC(blProposalBItemp.getBLPrpTmain().getArr(0).getFinalPayRate());
    				    	 blProposalCItemp.getBLPrpTmain().getArr(0).setFinalBusinessClassBC(blProposalBItemp.getBLPrpTmain().getArr(0).getFinalBusinessClassBC());
    				    	 blProposalCItemp.getBLPrpTmain().getArr(0).setProfitMarginBC(blProposalBItemp.getBLPrpTmain().getArr(0).getProfitMargin());
    				    	 blProposalCItemp.getBLPrpTmain().getArr(0).setProfitBusinessClassBC(blProposalBItemp.getBLPrpTmain().getArr(0).getProfitBusinessClassBC());
    				     }
    				     
    					GetBomPolicyUtil bomPolicyUtil = new GetBomPolicyUtil();
    					BomRuleResult ruleResultCI = new BomRuleResult();
    					
    					try{
    						if(blProposalMap.get(QuotationZHUtils.BLPROPOSALCI)!=null){
    	    					String policyNoCI = blProposalCI.getBLPrpTmain().getArr(0).getSendLastPolicyNo();
    	   					    BomPolicy bomPolicyCI = new BomPolicy();
								try {
									bomPolicyCI = bomPolicyUtil.blProToBomP(blProposalCI,policyNoCI,blProposalBI, "1");
								} catch (Exception e) {
									
									e.printStackTrace();
								}
    	    					Map resultCI=((RuleSevResultClient)(SpringUtils.init2()
    	    							.getBean("ruleResultClient")))
    	    							.getRuleResult(bomPolicyCI, BusinessKey. UNDWRT);
    	    					if(resultCI.get("retCode").equals("-1")){
    	    						throw new Exception("规则校验错误信息……规则通信失败");
    	    					}
    	    					ruleResultCI = (BomRuleResult) resultCI.get("ruleResult");
    	    					
    	    					boolean bIsUndwrt=ruleResultCI.isUndwrt();									
    	    					List<String> strUndwrtInfo = ruleResultCI.getUndwrtInfo();					
    	    					UndwrtAndUndwrtInfo undwrt=new UndwrtAndUndwrtInfo();
    	    					undwrt.addUndwrtAndUndwrtInfo(bIsUndwrt, strUndwrtInfo,blProposalCI, "2","2");
    							BLPrpRuleUndwrtInfo blPrpRuleUndwrtInfo=new BLPrpRuleUndwrtInfo();
    							ArrayList bindValues=new ArrayList();
    							bindValues.add(blProposalBI.getBLPrpTmain().getArr(0).getInfoTransNo());
    							bindValues.add(blProposalBI.getBLPrpTmain().getArr(0).getRiskCode());
    							bindValues.add("1");
    							blPrpRuleUndwrtInfo.query(" proposalno=? and riskcode=? and flag=?", bindValues);
    							blProposalCI=StateBomInOut.inOut(blProposalCI, ruleResultCI, bomPolicyCI);
    	    					paramMap.put("undwrtInfoCI", blPrpRuleUndwrtInfo);
    							paramMap.put("bIsUndwrtCI", bIsUndwrt);
    	    					
    	    					}
    						
    					}catch (Exception e) {
    						String Error_Code = "200000";
    						String Error_Message = "规则校验错误信息……规则通信失败";
    						paramMap.put("Error_Code", Error_Code);
    						paramMap.put("Error_Message",Error_Message);
    						throw new Exception("规则校验错误信息……规则通信失败");
							
						}
    					
    					 
    					BomRuleResult ruleResultBI = new BomRuleResult();
    					try{
    						if(blProposalMap.get(QuotationZHUtils.BLPROPOSALBI)!=null){
    							String policyNo = blProposalBI.getBLPrpTmain().getArr(0).getSendLastPolicyNo();
    							BomPolicy bomPolicyBI = bomPolicyUtil.blProToBomP(blProposalBI,policyNo,blProposalCI, "1");
    							Map resultBI=((RuleSevResultClient)(SpringUtils.init2()
    							.getBean("ruleResultClient")))
    							.getRuleResult(bomPolicyBI, BusinessKey.UNDWRT);
    							if(resultBI.get("retCode").equals("-1")){
    	    						throw new Exception("规则校验错误信息……规则通信失败");
    	    					}
    							ruleResultBI = (BomRuleResult) resultBI.get("ruleResult");
    							
    							boolean bIsUndwrt=ruleResultBI.isUndwrt();									
    							List<String> strUndwrtInfo = ruleResultBI.getUndwrtInfo();					
    							UndwrtAndUndwrtInfo undwrt=new UndwrtAndUndwrtInfo();
    							undwrt.addUndwrtAndUndwrtInfo(bIsUndwrt, strUndwrtInfo,blProposalBI, "2","3");
    							BLPrpRuleUndwrtInfo blPrpRuleUndwrtInfo=new BLPrpRuleUndwrtInfo();
    							ArrayList bindValues=new ArrayList();
    							bindValues.add(blProposalBI.getBLPrpTmain().getArr(0).getInfoTransNo());
    							bindValues.add(blProposalBI.getBLPrpTmain().getArr(0).getRiskCode());
    							bindValues.add("1");
    							blPrpRuleUndwrtInfo.query(" proposalno=? and riskcode=? and flag=?", bindValues);
    							blProposalBI=StateBomInOut.inOut(blProposalBI, ruleResultBI, bomPolicyBI);
    							paramMap.put("undwrtInfoBI", blPrpRuleUndwrtInfo);
    							paramMap.put("bIsUndwrtBI", bIsUndwrt);
    							
    						}
    					}catch (Exception e) {
    						String Error_Code = "200000";
    						String Error_Message = "规则校验错误信息……规则通信失败";
    						paramMap.put("Error_Code", Error_Code);
    						paramMap.put("Error_Message",Error_Message);
    						throw new Exception("规则校验错误信息……规则通信失败");
							
						}
    					boolean isLegalCI = false;
    					boolean isLegalBI = false;

    					isLegalCI = ruleResultCI.isLegal();
    					isLegalBI = ruleResultBI.isLegal();
    					
    					if(blProposalBI!=null){
    						if(isLegalBI){
        						String carCheckStatus = ruleResultBI.getCarCheckStatus();
        						if(carCheckStatus != null && !carCheckStatus.equals("")){
        							blProposalBI.getBLPrpTitemCar().getArr(0).setCarCheckStatus(carCheckStatus);
        						}else{
        							blProposalBI.getBLPrpTitemCar().getArr(0).setCarCheckStatus("2");
        						}
        					}
    					}
    					
    					
    					if(blProposalCI!=null){
        						boolean isRepeatInsurance = ruleResultCI.isRepeatInsurance();
        						if(isRepeatInsurance){
        							paramMap.put("isRepeatInsuranceCI","1");
        						}else{
        							paramMap.put("isRepeatInsuranceCI","0");
        						}
    					}
    					

    					if(blProposalBI!=null){
        						boolean isRepeatInsurance = ruleResultCI.isRepeatInsurance();
        						if(isRepeatInsurance){
        							paramMap.put("isRepeatInsuranceBI","1");
        						}else{
        							paramMap.put("isRepeatInsuranceBI","0");
        						}
    					}
    					

    					if(isLegalCI == false && isLegalBI == true){
    						paramMap.put("Error_Code", "110001");
    						Set ruleinfos = ruleResultCI.getRuleInfos();
    						Iterator it = ruleinfos.iterator();
    						StringBuffer strRuleinfos = new StringBuffer();
    						strRuleinfos.append("--");
    						while(it.hasNext()){
    							String s = (String)it.next();
    							strRuleinfos.append(s+"--");
    						}
    						paramMap.put("Error_Message","交强XXXXX不合规--"+strRuleinfos);
    						throw new Exception("交强XXXXX不合规--"+strRuleinfos);
    					}else if(isLegalCI == true && isLegalBI == false){
    						paramMap.put("Error_Code", "110002");
    						Set ruleinfos = ruleResultBI.getRuleInfos();
    						Iterator it = ruleinfos.iterator();
    						StringBuffer strRuleinfos = new StringBuffer();
    						strRuleinfos.append("--");
    						while(it.hasNext()){
    							String s = (String)it.next();
    							strRuleinfos.append(s+"--");
    						}
    						paramMap.put("Error_Message","商业XXXXX不合规--"+strRuleinfos);
    						throw new Exception("商业XXXXX不合规--"+strRuleinfos);
    					}else if(isLegalCI == false && isLegalBI == false){
    						paramMap.put("Error_Code", "110000");
    						Set ruleinfosCI = ruleResultCI.getRuleInfos();
    						Iterator itCI = ruleinfosCI.iterator();
    						StringBuffer strRuleinfos = new StringBuffer();
    						strRuleinfos.append("交强:");
    						while(itCI.hasNext()){
    							String s = (String)itCI.next();
    							strRuleinfos.append(s+"--");
    						}
    						Set ruleinfosBI = ruleResultBI.getRuleInfos();
    						Iterator itBI = ruleinfosBI.iterator();
    						StringBuffer strRuleinfosBI = new StringBuffer();
    						strRuleinfos.append("商业:");
    						while(itBI.hasNext()){
    							String s = (String)itBI.next();
    							strRuleinfos.append(s+"--");
    						}
    						paramMap.put("Error_Message","交强XXXXX、商业XXXXX都不合规--"+strRuleinfos);
    						throw new Exception("交强XXXXX、商业XXXXX都不合规--"+strRuleinfos);
    					}
    					
    					
    					
    					
    					
    					






    					
    					if(blProposalBI !=null){
    					Object[] lowDiscounts = ruleResultBI.getLowDiscounts().toArray();
    				    Arrays.sort(lowDiscounts);
    				    int length = lowDiscounts.length;
    				    if(length>0){
    				    Object lowDiscount = lowDiscounts[length-1];
    				    			blProposalBI.getBLPrpTmain().getArr(0).setAdjustRate(lowDiscount.toString());
    				    			Recalculate recalculate = new Recalculate();
    				    			
    				    			recalculate.recalculatePremium(blProposalBI, lowDiscount.toString(),paramMap);
    				    			

    				    }
    				    }
    					
    					DecimalFormat df4 = new DecimalFormat("0.0000");
    					
                 	  if(blProposalCI !=null){
                 	    
                 	  blPrpBasicInfoCaCheCI.getData(InfoTransNo,"0507");
                 	  String checkXMLCI = checkMsgUtility.generateCheckXMLApp((BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALCI),"02");
                 	  
                 	  
                 	 
                 	
                 	
                	 String sumdiscountCI = blProposalCI.getBLPrpTmain().getArr(0).getDiscount();
                	 blProposalCI.getBLPrpTmain().getArr(0).setDiscount(df4.format(1-Double.parseDouble(sumdiscountCI)));
                	 BLProposal  bLProposal11 =(BLProposal) blProposalMap.get(QuotationZHUtils.BLPROPOSALCI);
                	
                   	BLProposal retBLProposalCI =(BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALCI);
                   	if(retBLProposalCI!=null&&retBLProposalCI.getBLPrpTitemKind()!=null&&retBLProposalCI.getBLPrpTitemKind().getSize()>0){
                   		for(int i=0;i< retBLProposalCI.getBLPrpTitemKind().getSize();i++){
                   			PrpTitemKindSchema PrpTitemKindSchema = retBLProposalCI.getBLPrpTitemKind().getArr(i);
                   			String strShortRate = PrpTitemKindSchema.getShortRate();
                   			if(strShortRate!=null&&!"".equals(strShortRate)){
                   				double shortrate = Double.parseDouble(strShortRate)/100;
                   				retBLProposalCI.getBLPrpTitemKind().getArr(i).setShortRate(shortrate+"");
                   			}
                   		}
                   	}
                   	 
                 	 
                   	
                  
                   	if(retBLProposalCI !=null){
						String ChannelType = retBLProposalCI.getBLPrpTmain().getArr(0).getChannelType();
						
						if(ChannelType.startsWith("N07") || ChannelType.startsWith("N10")  ){
						
							retBLProposalCI.getBLPrpTmain().getArr(0).setChannelType(retBLProposalCI.getBLPrpTmain().getArr(0).getChannelTypeOrigin());
						}    						
					}
                  
                 	 String checkXMLQLCI = checkMsgUtility.generateCheckXMLApp(retBLProposalCI,"00");
              	     
                 	
                 	
                 	 
                 	
                 	  
                 	 String sumpremiumci = blProposalCI.getBLPrpTmain().getArr(0).getSumPremium();
                 	 blPrpBasicInfoCaCheCI.getArr(0).setSumPremium(sumpremiumci);
                 	 
                 	 
                 	 String comCode = blProposalCI.getBLPrpTmain().getArr(0).getComCode();
                 	 String sumPayTax = "";
                 	 if(!"10".equals(comCode.substring(0,2))){
                 		 sumPayTax = blProposalCI.getBLPrpTcarshipTax().getArr(0).getSumPayTax();
                 	 }else{
                 		 sumPayTax = blProposalCI.getBLPrpTcarshipTaxTJ().getArr(0).getSumPayTax();
                 	 }
                 	
                 	 
                 	 
                 	 blPrpBasicInfoCaCheCI.getArr(0).setTax(sumPayTax);
                 	 
                 	 SimpleDateFormat dfCI = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                 	 String UpdateDateCI = dfCI.format(new Date()).toString();
                 	 blPrpBasicInfoCaCheCI.getArr(0).setUpdateDate(UpdateDateCI);
                 	 DbPool dbpool = new DbPool();
                 	 dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
                 	  try
                      {
                        dbpool.beginTransaction();
                        blPrpBasicInfoCaCheCI.cancel(dbpool,InfoTransNo,"0507");
                        blPrpBasicInfoCaCheCI.save(dbpool);
                        dbpool.commitTransaction(); 
                      }
                 	 catch (Exception e)
                 	 
                     {
                 		 e.printStackTrace();

                       dbpool.rollbackTransaction();
                     
                     }
                     finally
                     {
                       dbpool.close();
                     
                     }
                 	
                 		  prpallSSDB.hset("prpBasicInfoCaChe",blPrpBasicInfoCaCheCI.getArr(0).getProposalXml(),checkXMLQLCI);
                 		 prpallSSDB.hset("prpBasicInfoCaChe",blPrpBasicInfoCaCheCI.getArr(0).getPrpInfo(),checkXMLCI);
                 	
                  }
                 	 
                 	  if(blProposalBI !=null){
                 		  
                 	  blPrpBasicInfoCaCheBI.getData(InfoTransNo,RiskCodeBI);
                 	  String checkXMLBI = checkMsgUtility.generateCheckXMLApp((BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI),"02");
                 	  
                 	
                 	
                  	 
                  	
                     String sumdiscountBI = blProposalBI.getBLPrpTmain().getArr(0).getDiscount();
                 	 blProposalBI.getBLPrpTmain().getArr(0).setDiscount(df4.format(1-Double.parseDouble(sumdiscountBI)));
                  	 
                  	BLProposal retBLProposalBI =(BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI);
                  	if(retBLProposalBI!=null&&retBLProposalBI.getBLPrpTitemKind()!=null&&retBLProposalBI.getBLPrpTitemKind().getSize()>0){
                  		for(int i=0;i< retBLProposalBI.getBLPrpTitemKind().getSize();i++){
                  			PrpTitemKindSchema PrpTitemKindSchema = retBLProposalBI.getBLPrpTitemKind().getArr(i);
                  			String strShortRate = PrpTitemKindSchema.getShortRate();
                  			if(strShortRate!=null&&!"".equals(strShortRate)){
                  				double shortrate = Double.parseDouble(strShortRate)/100;
                  				retBLProposalBI.getBLPrpTitemKind().getArr(i).setShortRate(shortrate+"");
                  			}
                  		}
                  	}
                  	 
                  	
                	
                   	if(retBLProposalBI !=null){
						String ChannelType = retBLProposalBI.getBLPrpTmain().getArr(0).getChannelType();
						
						if(ChannelType.startsWith("N07") || ChannelType.startsWith("N10")){
						
							retBLProposalBI.getBLPrpTmain().getArr(0).setChannelType(retBLProposalBI.getBLPrpTmain().getArr(0).getChannelTypeOrigin());
						}    						
					}
                  
                  	 
                  	 String checkXMLQLBI = checkMsgUtility.generateCheckXMLApp(retBLProposalBI,"00");
                	  
                  	
                	
                 	
                 	
                  	 
                  	 
                 	  
                 	 String sumpremiumBI = blProposalBI.getBLPrpTmain().getArr(0).getSumPremium();
                 	 blPrpBasicInfoCaCheBI.getArr(0).setSumPremium(sumpremiumBI);
                 	 
                 	 SimpleDateFormat dfBI = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                 	 String UpdateDateBI = dfBI.format(new Date()).toString();
                 	 blPrpBasicInfoCaCheBI.getArr(0).setUpdateDate(UpdateDateBI);
                 	 DbPool dbpool = new DbPool();
                 	 dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
                	  try
                     {
                       dbpool.beginTransaction();
                       blPrpBasicInfoCaCheBI.cancel(dbpool,InfoTransNo,RiskCodeBI);
                       blPrpBasicInfoCaCheBI.save(dbpool);
                       dbpool.commitTransaction(); 
                     }
                	 catch (Exception e)
                    { 
                         e.printStackTrace();
                         dbpool.rollbackTransaction();
                    }
                    finally
                    {
                      dbpool.close();
                    
                    }
                	
                		  prpallSSDB.hset("prpBasicInfoCaChe",blPrpBasicInfoCaCheBI.getArr(0).getProposalXml(),checkXMLQLBI);
                		  prpallSSDB.hset("prpBasicInfoCaChe",blPrpBasicInfoCaCheBI.getArr(0).getPrpInfo(),checkXMLBI);
                	
                 	  }
    				}
					
    				
    				if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")<= -1){
    					
       	   			    ProposalSave  proposalSave = new ProposalSave();	       	   			
	       	   			proposalSave.proposalSave(blProposalMap,paramMap,resultMap);
	       	   			
	       	   			if(blProposalMap.get(QuotationZHUtils.BLPROPOSALBI)!=null){
	       	   				UtiPower utiPower=new UtiPower();
	       	   				String strRiskCode = ((BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI)).getBLPrpTmain().getArr(0).getRiskCode();
	       	   				String strComCode = ((BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI)).getBLPrpTmain().getArr(0).getComCode();
	       	   				String strContractNo = ((BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI)).getBLPrpTmain().getArr(0).getContractNo();
	       	   				if(strContractNo!=null && !"".equals(strContractNo)
	       	   						&&!utiPower.checkCIInsureBJ(strRiskCode,strComCode)
	       	   						&&!utiPower.checkBIInsureXM(strRiskCode,strComCode)
	       	   						&&SysConfig.getProperty("Motorcade_Profit_Com").indexOf(strComCode.substring(0, 2)) < 0){
	       	   					datadeal.DataDealForMotor(((BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI)));
	       	   				}
	       	   			}
	       	   			
	       	   			
	       	   			if(SysConfig.getProperty("InteractiveUnderAuto").indexOf(","+OperateSite+",")>-1){
	       	   				String message = "";
	       	   				if(blProposalMap.get(QuotationZHUtils.BLPROPOSALBI)!=null){
	       	   					String returnMessage = new UnderwriteInteractiveService().UnderwriteService(((BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI)).getBLPrpTmain().getArr(0).getProposalNo());
	       	   					if(!"".equals(returnMessage)){
	       	   						message += "商业XXXXX"+returnMessage+";";
	       	   					}
	       	   				}
	       	   				if(blProposalMap.get(QuotationZHUtils.BLPROPOSALCI)!=null){
	       	   					String returnMessage = new UnderwriteInteractiveService().UnderwriteService(((BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALCI)).getBLPrpTmain().getArr(0).getProposalNo());
	       	   					if(!"".equals(returnMessage)){
	       	   						message += "交强XXXXX"+returnMessage+";";
	       	   					}
	       	   				}
	       	   				if(!"".equals(message)){
	       	   					throw new Exception(message);
	       	   				}
	       	   			}
	       	   			
    					
    				}
       	   				
	            }else if("03".equals(requestType)){
	            	
	            	UnderwriteInteractiveService uwrInteractiveService = new UnderwriteInteractiveService();
	            	uwrInteractiveService.UnderwriteService(paramMap,doc);
	            
	            }
	            
	            else if ("05".equals(requestType)){
	            	ProposalQueryService proposalQueryService = new ProposalQueryService();
	            	return proposalQueryService.queryProposalInfo(doc);
	            }
	            
	            
	            else if ("06".equals(requestType)){
	            	CreateOrderMessageService createOrderMessageService = new CreateOrderMessageService();
	            	return createOrderMessageService.createOrderMessage(doc);
	            }
	            
	            else if ("20030".equals(requestType)) {
	            	ProposalVerify proposalVerify = new ProposalVerify();
	            	proposalVerify.proposalVerify(paramMap, content);
	            
	            }else if ("20031".equals(requestType)) {
	                CheckProposalUnique checkProposalUnique = new CheckProposalUnique();
	                checkProposalUnique.doCheck(content,paramMap,blProposalMap);
	            
                }else if ("20034".equals(requestType)) {
                	EndorseCarLicenseNo endorseCarLicenseNo = new EndorseCarLicenseNo();
                	endorseCarLicenseNo.doEndorse(content,paramMap,blPolicyMap,headerMap);
	            
	            
                }else if ("20035".equals(requestType)) {
                	InsureCarLuggage insureCarLuggage = new InsureCarLuggage();
                	insureCarLuggage.doInsure(content,paramMap,blProposalMap);
	            
	            
                
                }else if ("20036".equals(requestType)) {
	                BasicInfoQueryService basicInfoQueryService = new BasicInfoQueryService();
	                basicInfoQueryService.queryBasicInfo(content,paramMap,blProposalMap);
                 
                
                }else if("20037".equals(requestType)){
                	if(!"".equals(root.element("BODY").elementText("CISTARTDATE").trim())){
        				blProposalMap.put(QuotationZHUtils.BLPROPOSALCI, new BLProposal());
        			}
        			if(!"".equals(root.element("BODY").elementText("BISTARTDATE").trim())){
        				blProposalMap.put(QuotationZHUtils.BLPROPOSALBI, new BLProposal());
        			}
        			QuotationObjectiveInfoDecoder decoder = new QuotationObjectiveInfoDecoder();
        			decoder.decode(doc, paramMap, blProposalMap);
        			ObjectiveCarInfoInterf objectiveCarInfoInterf = new ObjectiveCarInfoInterf();
        			objectiveCarInfoInterf.doObjectInfo(paramMap, blProposalMap);
                }
                
				else if("20038".equals(requestType)){
                	InformationCollection informationCollection = new InformationCollection();
                	return informationCollection.dochange(content,paramMap,blProposalMap); 
                	
                }else if("20039".equals(requestType)){
              CalculationPremiumTM calculationPremiumTM = new CalculationPremiumTM();
              calculationPremiumTM.dochange(blProposalMap, paramMap, resultMap, headerMap, dbPool, root, requestType, doc);
                }
				
			      else if ("20040".equals(requestType)) {
			    	    IdentityVerification identityVerification = new IdentityVerification();
			    	    identityVerification.doSend(content,paramMap,blProposalMap);
			        }
	           
	           
			        else if ("20041".equals(requestType)) {
			        	IdentityCardStorage identityCardStorage = new IdentityCardStorage();
			        	identityCardStorage.doInsert(content,paramMap,blProposalMap);
			        }
	          
           	
	        
                else if ("20042".equals(requestType)) {
                	CalculationPureRiskPremium calculationPureRiskPremium = new CalculationPureRiskPremium();
                	calculationPureRiskPremium.doQuery(blProposalMap, paramMap, resultMap, headerMap, dbPool, root, requestType, doc);
                	
                }
	        
	            else{
            		throw new Exception("没有对应的服务！");
                }
	            
		    }catch(Exception e){
		    	e.printStackTrace();
		    	quotationE = e;
		    }
	    
		    
			TBQuotationZHXMLEncoder encoder = new TBQuotationZHXMLEncoder();
			ProValidInteractiveEncoder encoderVal =  new ProValidInteractiveEncoder();
			String responseXML=null;
			try {
				if("02".equals(requestType)){
					if(quotationE==null){
						responseXML = encoder.encode(paramMap, blProposalMap);
					}else{
						responseXML = encoder.encodeException(paramMap, blProposalMap,quotationE);
					}
					return responseXML;
				}else if("03".equals(requestType)){
					if(quotationE==null){

					    responseXML = encoderVal.encode(paramMap);
					}else{

						responseXML = encoderVal.encodeException(paramMap, blProposalMap, quotationE);
					}
					return responseXML;
				
				}else if("20030".equals(requestType)){
					ProposalVerifyEncoder proposalVerifyEncoder = new ProposalVerifyEncoder();
					responseXML = proposalVerifyEncoder.encode(paramMap);
					return responseXML;
			    
				}else if("20031".equals(requestType)){
					CheckProposalUniqueEncoder Encoder = new CheckProposalUniqueEncoder();
					if(quotationE==null){
						responseXML = Encoder.encode(paramMap,blProposalMap);
					}else{
						responseXML = Encoder.encodeException(paramMap, blProposalMap, quotationE);
					}
				    return responseXML;
				
			    }else if("20034".equals(requestType)){
			    	EndorseCarLicenseNoEncoder Encoder = new EndorseCarLicenseNoEncoder();
			    	String ErrorCode = (String)headerMap.get("Error_Code");
					if("000000".equals(ErrorCode)){
						responseXML = Encoder.encode(paramMap,blPolicyMap);
					}else{
						responseXML = Encoder.encodeException(paramMap, blPolicyMap, headerMap);
					}
				    return responseXML;
			    
				
			    }else if("20035".equals(requestType)){
			    	InsureCarLuggageEncoder Encoder = new InsureCarLuggageEncoder();
			    	String ErrorCode = (String)paramMap.get("Error_Code");
					if("000000".equals(ErrorCode)){
						responseXML = Encoder.encode(paramMap,blProposalMap);
					}else{
						responseXML = Encoder.encodeException(paramMap, blProposalMap);
					}
				    return responseXML;
				
				
			    }else if("20036".equals(requestType)){
				    BasicInfoQueryServiceEncoder Encoder = new BasicInfoQueryServiceEncoder();
					responseXML = Encoder.responseCode(paramMap,blProposalMap);
				    return responseXML;
                
				
			    
				}else if("20037".equals(requestType)){
			    	QuotationObjectiveInfoEncoder Encoder = new QuotationObjectiveInfoEncoder();
			    	String ErrorCode = (String)paramMap.get("Error_Code");
			    	if("000000".equals(ErrorCode)){
						responseXML = Encoder.encode(paramMap,blProposalMap);
					}else{
						responseXML = Encoder.encodeException(paramMap, blProposalMap, quotationE);
					}
			    	return responseXML;
					
			    	
			    }else if("20039".equals(requestType)){
			    	TBQuotationTMXMLEncoder encoder1 = new TBQuotationTMXMLEncoder();
					if(quotationE==null){
						
						BLProposal blProposal = new BLProposal();
						if(blProposalMap.get(QuotationUtils.BLPROPOSALCI)!=null){
							blProposal = (BLProposal)blProposalMap.get(QuotationUtils.BLPROPOSALCI);
						}else{
							blProposal = (BLProposal)blProposalMap.get(QuotationUtils.BLPROPOSALBI);
						}
						
						if ("1".equals(blProposal.getBLPrpTmain().getArr(0).getCCSITestFlag())){
							TBPlatformXMLEncoder platformEncoder = new TBPlatformXMLEncoder();
							responseXML = platformEncoder.encode(paramMap, blProposalMap);
						}else{
							responseXML = encoder1.encode(paramMap, blProposalMap);
						}
						
					}else{
						responseXML = encoder1.encodeException(paramMap, blProposalMap,quotationE);
					}
					return responseXML;
				}
				
				else if("20040".equals(requestType)){
					IdentityVerificationEncoder Encoder = new IdentityVerificationEncoder();
					if(quotationE==null){
						responseXML = Encoder.encode(paramMap, blProposalMap);
					}else{
						responseXML = Encoder.encodeException(paramMap, blProposalMap,quotationE);
					}
					return responseXML;
					
			        
				}else if("20041".equals(requestType)){
					IdentityCardStorageEncoder Encoder = new IdentityCardStorageEncoder();
					if(quotationE==null){
						responseXML = Encoder.encode(paramMap, blProposalMap);
					}else{
						responseXML = Encoder.encodeException(paramMap, blProposalMap,quotationE);
					}
					return responseXML;
				}
		           
				 
			    else if("20042".equals(requestType)){
			    	ProposalInfoQueryPureRiskEncoder encode = new ProposalInfoQueryPureRiskEncoder();
				if(quotationE==null){
					responseXML = encode.encode(paramMap, blProposalMap);
				}else{
					responseXML = encode.encodeException(paramMap, blProposalMap,quotationE);
				}
				return responseXML;
				
			}
				 
			} catch (Exception e) {
				
				e.printStackTrace();
			}			
			return null;
	    }
}
