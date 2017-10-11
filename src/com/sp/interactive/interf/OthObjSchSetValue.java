package com.sp.interactive.interf;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.sp.sysframework.reference.DBManager;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool; 
import com.sp.utility.string.ChgData;
import com.sp.utility.string.Date;

import com.sp.indiv.ci.blsvr.BLCICarShipTaxQGDemand;
import com.sp.platform.dto.domain.PrpDriskConfigDto;
import com.sp.platform.ui.control.action.UIPrpDriskConfigAction;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpCIInsureDemand;
import com.sp.prpall.pubfun.Premium;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PremCIInsureDemandSchema;
import com.sp.prpall.schema.PrpClimitSchema;
import com.sp.prpall.schema.PrpIntefInfoSchema;
import com.sp.prpall.schema.PrpTTrafficDetailSchema;
import com.sp.prpall.schema.PrpTcarshipTaxSchema;
import com.sp.prpall.schema.PrpTinsuredNatureSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTitemCarExtSchema;
import com.sp.prpall.schema.PrpTitemCarSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTlimitSchema;
import com.sp.prpall.schema.PrpTmainAgriSchema;
import com.sp.prpall.schema.PrpTmainBankSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTmainSubSchema;
import com.sp.prpall.schema.PrpTprofitDetailSchema;
import com.sp.prpcar.pub.PrpCarConstants;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utiall.blsvr.BLPrpDcurrency;
import com.sp.utiall.blsvr.BLPrpDcustomerIdvNew;
import com.sp.utiall.blsvr.BLPrpDdeprecateRate;
import com.sp.utiall.blsvr.BLPrpDkind;
import com.sp.utiall.blsvr.BLPrpDlimitCar;
import com.sp.utiall.schema.PrpDlimitCarSchema;
import com.sp.utility.string.Str;

/**
 * 给部分表的部分字段赋值
 * @author qilin
 * */

public class OthObjSchSetValue {
       public void SchSetValue(BLProposal blProposalCI,BLProposal blProposalBI,String bizNoCI,String bizNoBI,Map paramMap,Map resultMap) throws Exception{ 
    	   
    	   if(blProposalCI!=null){
    	 
    	    String riskCodeCI=blProposalCI.getBLPrpTmain().getArr(0).getRiskCode();
    	    String strComcode = blProposalCI.getBLPrpTmain().getArr(0).getComCode();
    	    String isNewFlagCI="";
   		    if(blProposalCI.getBLPrpTmainExtras().getSize()>0){
   			    isNewFlagCI= blProposalCI.getBLPrpTmainExtras().getArr(0).getNewFlag();
   		    }
   		    String OperateSiteBC = blProposalCI.getBLPrpTmain().getArr(0).getOperateSite();
    	
			PrpTmainSchema prpTmainSchema = blProposalCI.getBLPrpTmain().getArr(0);
			prpTmainSchema.setProposalNo(bizNoCI);
			if("07".equals(prpTmainSchema.getComCode().substring(0, 2))){
				prpTmainSchema.setPayMethod("01");
			}
			prpTmainSchema.setCurrency("CNY");
			prpTmainSchema.setJudicalScope("中国(港澳台除外)司法管辖");
			prpTmainSchema.setPayTimes("1");
			
			prpTmainSchema.setSumQuantity("1");
			prpTmainSchema.setArgueSolution("1");
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String strSysDate = dateFormat.format(new java.util.Date()); 
			prpTmainSchema.setStatisticsYM(strSysDate);
			prpTmainSchema.setUpdateDate(strSysDate);
			 
			 SimpleDateFormat formate= new SimpleDateFormat("HH:mm:ss");
			 String timeString=formate.format(new java.util.Date());
			prpTmainSchema.setUpdateHour(timeString.substring(0, 2));
			prpTmainSchema.setUpdaterCode(prpTmainSchema.getOperatorCode());
			
	
			 String strCustomerCode = "";
		     String strCustomerCode1 = "";
		     BLPrpDcustomerIdvNew blPrpDcustomerIdvNew = new BLPrpDcustomerIdvNew();
			for(int i=0;i<blProposalCI.getBLPrpTinsured().getSize();i++){
				
				PrpTinsuredSchema prpTinsuredSchema = blProposalCI.getBLPrpTinsured().getArr(i);
				if("1".equals(prpTinsuredSchema.getInsuredFlag())){
				 
				prpTinsuredSchema.setProposalNo(bizNoCI);
				prpTinsuredSchema.setRiskCode(riskCodeCI);
				prpTinsuredSchema.setSerialNo("1");
				prpTinsuredSchema.setLanguage("C");
				prpTinsuredSchema.setLinkerName(prpTinsuredSchema.getInsuredName());
				   if("3".equals(prpTinsuredSchema.getInsuredNature())){
					  
					   PrpTinsuredNatureSchema prpInsuredNatureSchema  = blProposalCI.getBLPrpTinsuredNature().getArr(0);
					   prpInsuredNatureSchema.setProposalNo(bizNoCI);
					   prpInsuredNatureSchema.setSerialNo("1");
				   }
				   String strOperCode = blProposalCI.getBLPrpTmain().getArr(0).getOperatorCode();
				   System.out.println("strOperCode----"+strOperCode);
				   String strHandler1Code = blProposalCI.getBLPrpTmain().getArr(0).getHandler1Code();
				   strCustomerCode1 = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema, strComcode, strOperCode,strHandler1Code); 
				   prpTinsuredSchema.setInsuredCode(strCustomerCode1);
				   blProposalCI.getBLPrpTmain().getArr(0).setInsuredCode(strCustomerCode1);
				   blProposalCI.getBLPrpTmain().getArr(0).setInsuredName(prpTinsuredSchema.getInsuredName());
				   blProposalCI.getBLPrpTmain().getArr(0).setInsuredAddress(prpTinsuredSchema.getInsuredAddress());
				}
				
				if ("2".equals(prpTinsuredSchema.getInsuredFlag())){
				
				
				prpTinsuredSchema.setProposalNo(bizNoCI);
				prpTinsuredSchema.setRiskCode(riskCodeCI);
				prpTinsuredSchema.setSerialNo("2");
				prpTinsuredSchema.setLanguage("C");
				prpTinsuredSchema.setLinkerName(prpTinsuredSchema.getInsuredName());
				
				String strOperCode = blProposalCI.getBLPrpTmain().getArr(0).getOperatorCode();
				String strHandler1Code = blProposalCI.getBLPrpTmain().getArr(0).getHandler1Code();
				strCustomerCode = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema, strComcode, strOperCode,strHandler1Code);
				prpTinsuredSchema.setInsuredCode(strCustomerCode);
				blProposalCI.getBLPrpTmain().getArr(0).setAppliCode(strCustomerCode);
				blProposalCI.getBLPrpTmain().getArr(0).setAppliName(prpTinsuredSchema.getInsuredName());
				blProposalCI.getBLPrpTmain().getArr(0).setAppliAddress(prpTinsuredSchema.getInsuredAddress());
				}
				
			}
			
            
			












			
	
			PrpTitemCarSchema prpTitemCarSchema = blProposalCI.getBLPrpTitemCar().getArr(0);
			prpTitemCarSchema.setProposalNo(bizNoCI);
			prpTitemCarSchema.setRiskCode(riskCodeCI);
			prpTitemCarSchema.setItemNo("1");
			prpTitemCarSchema.setClauseType("F40");
			prpTitemCarSchema.setNewDeviceFlag("0");
			prpTitemCarSchema.setAgreeDriverFlag("0");
			
			
			
			
			if(!(UtiPower.isDXswitch(OperateSiteBC, isNewFlagCI)||SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1)){
		    
				if(blProposalBI!=null&&blProposalCI!=null){
					 
					 prpTitemCarSchema.setRelievingAreaCode("8");
				 }else if(blProposalCI!=null&&blProposalBI==null){
					 
					 prpTitemCarSchema.setRelievingAreaCode("7");
				 }else if(blProposalCI==null&&blProposalBI!=null){
					 
					 if("".equals(paramMap.get("cipolicyNo"))||"".equals(paramMap.get("cicompanyCode"))){
						 
						 prpTitemCarSchema.setRelievingAreaCode("3");
					 }else{
						 
						 prpTitemCarSchema.setRelievingAreaCode("2");
					 }
				 }
			}
			
			
			 prpTitemCarSchema.setSafeDevice("1");	
			 
			 
			
			 prpTitemCarSchema.setCurrency("CNY");
			
			  	String strPurchasePriceNoTaxFlag = "" ;
			  	PrpDriskConfigDto prpDriskConfigDto = UIPrpDriskConfigAction.queryRiskConfig(strComcode,riskCodeCI,"PURCHASEPRICE_NOTAX_SWITCH");
			 
			  	if(prpDriskConfigDto!=null){
			  	
			  		strPurchasePriceNoTaxFlag = prpDriskConfigDto.getConfigValue();
			  
			  	}
			  	prpTitemCarSchema.setPurchasePriceNoTaxFlag(strPurchasePriceNoTaxFlag);
			  	
			  	
















				 
				 prpTitemCarSchema.setSViolatedTimes("0");
				 prpTitemCarSchema.setLViolatedTimes("0");
				 
				 if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")<=-1){
					 prpTitemCarSchema.setChgOwnerFlag("");
					 prpTitemCarSchema.setChgOwnerDate("");
				 }
				 

				 
				 

			
			
				PrpTitemCarExtSchema prpTitemCarExtSchema = blProposalCI.getBLPrpTitemCarExt().getArr(0);
				prpTitemCarExtSchema.setProposalNo(bizNoCI);
				prpTitemCarExtSchema.setRiskCode(riskCodeCI);
				prpTitemCarExtSchema.setItemNo("1");
				prpTitemCarExtSchema.setNoClaimFavorType("1");
	            
				String PurchasePrice = prpTitemCarSchema.getPurchasePrice();
				String SeatCount = prpTitemCarSchema.getSeatCount();
				String UseNatureCode = prpTitemCarSchema.getUseNatureCode();
				String CarKindCode = prpTitemCarSchema.getCarKindCode();
				if (PubTools.checkLuxuryCar(PurchasePrice,SeatCount,UseNatureCode,CarKindCode)) {
					prpTitemCarExtSchema.setLuxuryCarFlag("1");
			    }else{
			    	prpTitemCarExtSchema.setLuxuryCarFlag("0");
			    }
				String strShieldFirewallFlag = "";
				 if(new UtiPower().checkChannelFirewall(strComcode)){
				    	strShieldFirewallFlag = "1".equals(prpTitemCarExtSchema.getShieldFirewall())?"是":"否";
				    	if("否".equals(strShieldFirewallFlag)){
				    		strShieldFirewallFlag="0";
				    	}
				    	if("是".equals(strShieldFirewallFlag)){
				    		strShieldFirewallFlag="1";
				    	}
				 }
				 prpTitemCarExtSchema.setShieldFirewall(strShieldFirewallFlag);
				 if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1|| UtiPower.isDXswitch(OperateSiteBC, isNewFlagCI)){
        		 }else{
        			 prpTitemCarExtSchema.setNoneFluctuateFlag("N1");        			 
        		 }
				 if("".equals(prpTitemCarExtSchema.getInDoorFlag())||prpTitemCarExtSchema.getInDoorFlag()==null){
		   				prpTitemCarExtSchema.setInDoorFlag("2");
		   		 }
				 prpTitemCarExtSchema.setThisOffenceCI("0");
				 prpTitemCarExtSchema.setDamFloatRatioCI("0");
				 prpTitemCarExtSchema.setOffFloatRatioCI("0");
				 






     		     
     		     String controlFlag = "000000000";
        		 String ServiceAreaFlagBC = "0";
        		 
        		 
        		
        		 
        		if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1|| UtiPower.isDXswitch(OperateSiteBC, isNewFlagCI)){
        		 }else{
        			 if(SysConfig.getProperty("setServiceAreaForZH")!=null&&!"".equals(SysConfig.getProperty("setServiceAreaForZH"))){
        				 ServiceAreaFlagBC = "1";
        				 prpTmainSchema.setServiceArea(SysConfig.getProperty("setServiceAreaForZH"));
        			 }
        		 }
        		
        		 
        		 String LastChgOwnerFlagBI = "0";
                 controlFlag = ServiceAreaFlagBC + controlFlag.substring(1,9);          
                 if(new UtiPower().checkLastChgOwner(prpTmainSchema.getOperateDate(), prpTmainSchema.getComCode(), riskCodeCI)){
                
                	 controlFlag = controlFlag.substring(0,1) + LastChgOwnerFlagBI + controlFlag.substring(2,9);
                 } 
                 prpTitemCarExtSchema.setControlFlag(controlFlag);
           

     	 
				String strStartDate = blProposalCI.getBLPrpTmain().getArr(0).getStartDate();
				String strEndDate = blProposalCI.getBLPrpTmain().getArr(0).getEndDate();
				String intStartHour = blProposalCI.getBLPrpTmain().getArr(0).getStartHour();
				String intEndHour = blProposalCI.getBLPrpTmain().getArr(0).getEndHour();
				String itemNo = "1";
				String   strItemCode          = "0001";
				 String   strItemDetailName    = "车辆";
				PrpTitemKindSchema prpTitemKindSchema = blProposalCI.getBLPrpTitemKind().getArr(0);
				prpTitemKindSchema.setProposalNo(bizNoCI);
				prpTitemKindSchema.setRiskCode(riskCodeCI);
				prpTitemKindSchema.setRate("1");
				prpTitemKindSchema.setKindCode (prpTitemKindSchema.getKindCode());
				prpTitemKindSchema.setItemNo   ("" + itemNo);
				prpTitemKindSchema.setItemCode (strItemCode);
				prpTitemKindSchema.setItemDetailName(strItemDetailName);
				prpTitemKindSchema.setStartDate(strStartDate);
		        prpTitemKindSchema.setEndDate  (strEndDate);
		        prpTitemKindSchema.setStartHour("" + intStartHour);
		        prpTitemKindSchema.setEndHour  ("" + intEndHour);
		        prpTitemKindSchema.setKindName ("机动车交通事故责任强制XXXXX");
		        prpTitemKindSchema.setCalculateFlag("Y");
		        
		        if(blProposalCI.getBLCIInsureDemand().getSize()>0){
		        	prpTitemKindSchema.setAmount(blProposalCI.getBLCIInsureDemand().getArr(0).getAmount());
		        }
		        
				if("".equals(prpTitemKindSchema.getAmount().trim())||prpTitemKindSchema.getAmount().trim()==null){
					prpTitemKindSchema.setAmount("0.00");
				}
				prpTitemKindSchema.setRatePeriod("");
				
			
			if(blProposalCI.getBLPrpTprofitDetail().getSize()==0){				
				PrpTprofitDetailSchema prpProfitDetailSchema = new PrpTprofitDetailSchema();
				prpProfitDetailSchema.setProposalNo(bizNoCI);
				prpProfitDetailSchema.setRiskCode(riskCodeCI);
	            prpProfitDetailSchema.setProfitType("2");
	            prpProfitDetailSchema.setProfitPeriod("1");
	            prpProfitDetailSchema.setItemKindNo(prpTitemKindSchema.getItemKindNo());
	            prpProfitDetailSchema.setKindCode(prpTitemKindSchema.getKindCode());
	            prpProfitDetailSchema.setKindName(prpTitemKindSchema.getKindName());
	            prpProfitDetailSchema.setProfitCode("13");
	            prpProfitDetailSchema.setProfitName("无赔款优待");
	            prpProfitDetailSchema.setSerialNo("1");
	            prpProfitDetailSchema.setCondition("无赔款优待");
	            prpProfitDetailSchema.setProfitRate("1.00000");
	                    
	            
	            if (prpTitemKindSchema.getFlag().length() > 0){
	                prpProfitDetailSchema.setFlag(prpTitemKindSchema.getFlag().charAt(0) + "");
	            }
	            else{
	                prpProfitDetailSchema.setFlag("");
	            }
	            blProposalCI.getBLPrpTprofitDetail().setArr(prpProfitDetailSchema);
			}else{
	            for(int m = 0;m<blProposalCI.getBLPrpTprofitDetail().getSize();m++){
				PrpTprofitDetailSchema prpProfitDetailSchema = blProposalCI.getBLPrpTprofitDetail().getArr(m);
				prpProfitDetailSchema.setProposalNo(bizNoCI);
				String profitRate  = blProposalCI.getBLPrpTprofitDetail().getArr(m).getProfitRate();
				String profiteRateNew = String.valueOf(Str.round(Double.parseDouble(profitRate)/100,4));
				prpProfitDetailSchema.setProfitRate(profiteRateNew);
	            }
			}

		
				PrpTcarshipTaxSchema prpTcarshipTaxSchema = blProposalCI.getBLPrpTcarshipTax().getArr(0);
				BLCICarShipTaxQGDemand  blCICarshipTaxDemand = blProposalCI.getBLCICarShipTaxQGDemand();
				prpTcarshipTaxSchema.setProposalNo(bizNoCI);
				prpTcarshipTaxSchema.setSerialNo("1");
				prpTcarshipTaxSchema.setRiskCode(riskCodeCI);
				prpTcarshipTaxSchema.setLicenseNo(prpTitemCarSchema.getLicenseNo());
				prpTcarshipTaxSchema.setLicenseType(prpTitemCarSchema.getLicenseKindCode());
				prpTcarshipTaxSchema.setCarKindCode(prpTitemCarSchema.getCarKindCode());
				if("01".equals(strComcode.substring(0, 2))||"07".equals(strComcode.substring(0, 2))){
					prpTcarshipTaxSchema.setCalculateFlag("1");
					String taxFlag = blProposalCI.getBLCICarshipTaxDemand().getArr(0).getTaxFlag();
					if("01".equals(taxFlag)){
						prpTcarshipTaxSchema.setTaxRelifFlag("2");
					}
					if("02".equals(taxFlag)){
						prpTcarshipTaxSchema.setTaxRelifFlag("3");
					}
					if("03".equals(taxFlag)){
						prpTcarshipTaxSchema.setTaxRelifFlag("0");
					}
					if("04".equals(taxFlag)){
						prpTcarshipTaxSchema.setTaxRelifFlag("4");
					}
					if("05".equals(taxFlag)){
						prpTcarshipTaxSchema.setTaxRelifFlag("5");
					}
				}
				
				



				
				prpTcarshipTaxSchema.setPayTaxTimes("1");
				
			
			
				PrpIntefInfoSchema prpIntefInfoSchema  = blProposalCI.getBLPrpIntefInfo().getArr(0);
				prpIntefInfoSchema.setBusinessNo(bizNoCI);
			
	            
				String strDemandNo = "";
				if(blProposalCI.getBLCIInsureDemand().getSize()>0){
					strDemandNo= blProposalCI.getBLCIInsureDemand().getArr(0).getDemandNo();
				}
		        
				PremCIInsureDemandSchema blPrpCIInsureDemand = new PremCIInsureDemandSchema();
				blPrpCIInsureDemand.setDemandNo(strDemandNo);
				blProposalCI.getBLPrpCIInsureDemand().setArr(blPrpCIInsureDemand);
				
				prpTitemCarSchema.setDemandNo(strDemandNo);
				
			
			
				
				if(blProposalCI.getBLPrpTmainAgri().getSize()>0){
					int length = blProposalCI.getBLPrpTmainAgri().getSize();
					for(int i = blProposalCI.getBLPrpTmainAgri().getSize()-1;i>=0;i--){
						blProposalCI.getBLPrpTmainAgri().remove(i);
					}
				}
				PrpTmainAgriSchema prpTmainAgriSchema = new PrpTmainAgriSchema();
				prpTmainAgriSchema.setProposalNo(bizNoCI);
				prpTmainAgriSchema.setRiskCode(riskCodeCI);
				blProposalCI.getBLPrpTmainAgri().setArr(prpTmainAgriSchema);
			
			 












			
			
				
				if(blProposalCI.getBLPrpTlimit().getSize()>0){
					int length = blProposalCI.getBLPrpTlimit().getSize();
					for(int i = blProposalCI.getBLPrpTlimit().getSize()-1;i>=0;i--){
						blProposalCI.getBLPrpTlimit().remove(i);
					}
				}
				
				PrpTlimitSchema prptlimitSchema = null;
				BLPrpDcode blPrpDcode = new BLPrpDcode();
				BLPrpDcurrency blPrpDcurrency = new BLPrpDcurrency();
				Collection limitSchemaList = new ArrayList();
				
	            String sql = " RiskCode='0507' AND KindCode='BZ' AND LimitPeriod='2'";
	            BLPrpDlimitCar blPrpDlimitCar = new BLPrpDlimitCar();
	            blPrpDlimitCar.query(sql);
	            PrpDlimitCarSchema prpDlimitCarSchema = null;
	            for (int i = 0; i < blPrpDlimitCar.getSize(); i++) {
	                prpDlimitCarSchema = blPrpDlimitCar.getArr(i);
	                prptlimitSchema = new PrpTlimitSchema();
	                prptlimitSchema.setProposalNo(bizNoCI);
	                prptlimitSchema.setRiskCode(riskCodeCI);
	                prptlimitSchema.setLimitFlag("0");
	                prptlimitSchema.setLimitNo("1"); 
	                prptlimitSchema.setLimitType(prpDlimitCarSchema.getLimitCode());
	                prptlimitSchema.setLimitGrade("2"); 
	                prptlimitSchema.setCurrency("CNY");
	                prptlimitSchema.setLimitFee(prpDlimitCarSchema.getLimitFee());
	                prptlimitSchema.setCalculateFlag(prpDlimitCarSchema.getFlag());
	                blProposalCI.getBLPrpTlimit().setArr(prptlimitSchema);
	            }
	
    	   }
    	   
    	   if(blProposalBI!=null){
    		   String riskCodeBI=blProposalBI.getBLPrpTmain().getArr(0).getRiskCode();
    		   String strComcode = blProposalBI.getBLPrpTmain().getArr(0).getComCode();
    		   String OperateSiteBC = blProposalBI.getBLPrpTmain().getArr(0).getOperateSite();
          	   String isNewFlagBI="";
          	   if(blProposalBI.getBLPrpTmainExtras().getSize()>0){
          		   isNewFlagBI=blProposalBI.getBLPrpTmainExtras().getArr(0).getNewFlag();
          	   }
    	 
    			PrpTmainSchema prpTmainSchema = blProposalBI.getBLPrpTmain().getArr(0);
    			prpTmainSchema.setProposalNo(bizNoBI);
    			if("07".equals(prpTmainSchema.getComCode().substring(0, 2))){
    				prpTmainSchema.setPayMethod("01");
    			}
    			prpTmainSchema.setCurrency("CNY");
    			prpTmainSchema.setJudicalScope("中国(港澳台除外)司法管辖");
    			prpTmainSchema.setPayTimes("1");
    			prpTmainSchema.setSumQuantity("1");
    			prpTmainSchema.setArgueSolution("1");
    			
    			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    			String strSysDate = dateFormat.format(new java.util.Date()); 
    			prpTmainSchema.setStatisticsYM(strSysDate);
    			prpTmainSchema.setUpdateDate(strSysDate);
    			 
    			 SimpleDateFormat formate= new SimpleDateFormat("HH:mm:ss");
    			 String timeString=formate.format(new java.util.Date());
    			prpTmainSchema.setUpdateHour(timeString.substring(0, 2));
    			prpTmainSchema.setUpdaterCode(prpTmainSchema.getOperatorCode());
    			
    			String startdateBI=blProposalBI.getBLPrpTmain().getArr(0).getStartDate();
    			String enddateBI=blProposalBI.getBLPrpTmain().getArr(0).getEndDate();
    			for(int i=0;i<blProposalBI.getBLPrpTitemKind().getSize();i++){
    				blProposalBI.getBLPrpTitemKind().getArr(i).setStartDate(startdateBI);
    				blProposalBI.getBLPrpTitemKind().getArr(i).setEndDate(enddateBI);
    			}
    			
    			
    	
   			    String strCustomerCode = "";
		        String strCustomerCode1 = "";
		        BLPrpDcustomerIdvNew blPrpDcustomerIdvNew = new BLPrpDcustomerIdvNew();
    			for(int j=0;j<blProposalBI.getBLPrpTinsured().getSize();j++){
	    			
	    			PrpTinsuredSchema prpTinsuredSchema = blProposalBI.getBLPrpTinsured().getArr(j);
	    			if("1".equals(prpTinsuredSchema.getInsuredFlag())){
	    			 
	    			prpTinsuredSchema.setProposalNo(bizNoBI);
	    			prpTinsuredSchema.setRiskCode(riskCodeBI);
	    			prpTinsuredSchema.setSerialNo("1");
	    			prpTinsuredSchema.setLanguage("C");
	    			prpTinsuredSchema.setLinkerName(prpTinsuredSchema.getInsuredName());
	    			 if("3".equals(prpTinsuredSchema.getInsuredNature())){
						  
						   PrpTinsuredNatureSchema prpInsuredNatureSchema  = blProposalBI.getBLPrpTinsuredNature().getArr(0);
						   prpInsuredNatureSchema.setProposalNo(bizNoBI);
						   prpInsuredNatureSchema.setSerialNo("1");
					   }

					   String strOperCode = blProposalBI.getBLPrpTmain().getArr(0).getOperatorCode();
					   String strHandler1Code = blProposalBI.getBLPrpTmain().getArr(0).getHandler1Code();
					   strCustomerCode1 = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema, strComcode, strOperCode,strHandler1Code); 
					   prpTinsuredSchema.setInsuredCode(strCustomerCode1);
					   blProposalBI.getBLPrpTmain().getArr(0).setInsuredCode(strCustomerCode1);
					   blProposalBI.getBLPrpTmain().getArr(0).setInsuredName(prpTinsuredSchema.getInsuredName());
					   blProposalBI.getBLPrpTmain().getArr(0).setInsuredAddress(prpTinsuredSchema.getInsuredAddress());
	    			
	    			}
	    			if ("2".equals(prpTinsuredSchema.getInsuredFlag())){
	    			
	    			
	    			prpTinsuredSchema.setProposalNo(bizNoBI);
	    			prpTinsuredSchema.setRiskCode(riskCodeBI);
	    			prpTinsuredSchema.setSerialNo("2");
	    			prpTinsuredSchema.setLanguage("C");
	    			prpTinsuredSchema.setLinkerName(prpTinsuredSchema.getInsuredName());

					String strOperCode = blProposalBI.getBLPrpTmain().getArr(0).getOperatorCode();
					String strHandler1Code = blProposalBI.getBLPrpTmain().getArr(0).getHandler1Code();
					strCustomerCode = blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema, strComcode, strOperCode,strHandler1Code);
					prpTinsuredSchema.setInsuredCode(strCustomerCode);
					blProposalBI.getBLPrpTmain().getArr(0).setAppliCode(strCustomerCode);
					blProposalBI.getBLPrpTmain().getArr(0).setAppliName(prpTinsuredSchema.getInsuredName());
					blProposalBI.getBLPrpTmain().getArr(0).setAppliAddress(prpTinsuredSchema.getInsuredAddress());
	    			}
	    			
    			}
    			












    	
    	
    			PrpTitemCarSchema prpTitemCarSchema = blProposalBI.getBLPrpTitemCar().getArr(0);
    			prpTitemCarSchema.setProposalNo(bizNoBI);
    			prpTitemCarSchema.setRiskCode(riskCodeBI);
    			prpTitemCarSchema.setItemNo("1");
    			
    			if("04".equals(prpTitemCarSchema.getRunAreaCode())){
    				prpTitemCarSchema.setRunAreaName("中国境内（不含港、澳、台）");
    			}
    			if("03".equals(prpTitemCarSchema.getRunAreaCode())){
    				prpTitemCarSchema.setRunAreaName("本省（市、区）");
    			}
    			prpTitemCarSchema.setNewDeviceFlag("0");
    			prpTitemCarSchema.setAgreeDriverFlag("0");
    			
    			
    			
    			
    			if(!(UtiPower.isDXswitch(OperateSiteBC, isNewFlagBI)||SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1)){
        		
    				if(blProposalCI!=null&&blProposalBI!=null){
       				    
       				    prpTitemCarSchema.setRelievingAreaCode("8");
       			    }else if(blProposalCI!=null&&blProposalBI==null){
       				    
       				    prpTitemCarSchema.setRelievingAreaCode("7");
       			    }else if(blProposalCI==null&&blProposalBI!=null){
       				    
       				    if("".equals(paramMap.get("cipolicyNo"))||"".equals(paramMap.get("cicompanyCode"))){
       					
       					prpTitemCarSchema.setRelievingAreaCode("3");
       				    }else{
       					    
       					    prpTitemCarSchema.setRelievingAreaCode("2");
       				    }
       			    }
    			}
    			
    			
    			 prpTitemCarSchema.setSafeDevice("1");	
    			 
    			
    			
    			 prpTitemCarSchema.setCurrency("CNY");
    			 prpTitemCarSchema.setCarCheckStatus("2");
    			 dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    		     strSysDate = dateFormat.format(new java.util.Date()); 
    			 prpTitemCarSchema.setCarCheckTime(strSysDate);
    			
 			  	String strPurchasePriceNoTaxFlag = "" ;
 			  	PrpDriskConfigDto prpDriskConfigDto = UIPrpDriskConfigAction.queryRiskConfig(strComcode,riskCodeBI,"PURCHASEPRICE_NOTAX_SWITCH");
 			  	if(prpDriskConfigDto!=null){
 	
 			  		strPurchasePriceNoTaxFlag = prpDriskConfigDto.getConfigValue();

 			  	}
 			  	prpTitemCarSchema.setPurchasePriceNoTaxFlag(strPurchasePriceNoTaxFlag);
 			 
 			  	 































				 
				 if("01".equals(strComcode.substring(0, 1))||"07".equals(strComcode.substring(0, 1))){
				     prpTitemCarSchema.setChgOwnerFlag("");
				 }
 			  	
    			
    	
    			PrpTitemCarExtSchema prpTitemCarExtSchema = blProposalBI.getBLPrpTitemCarExt().getArr(0);
    			prpTitemCarExtSchema.setProposalNo(bizNoBI);
    			prpTitemCarExtSchema.setRiskCode(riskCodeBI);
    			prpTitemCarExtSchema.setItemNo("1");
    			 
    			String PurchasePrice = prpTitemCarSchema.getPurchasePrice();
    			String SeatCount = prpTitemCarSchema.getSeatCount();
    			String UseNatureCode = prpTitemCarSchema.getUseNatureCode();
    			String CarKindCode = prpTitemCarSchema.getCarKindCode();
    			if (PubTools.checkLuxuryCar(PurchasePrice,SeatCount,UseNatureCode,CarKindCode)) {
    				prpTitemCarExtSchema.setLuxuryCarFlag("1");
    		    }else{
    		    	prpTitemCarExtSchema.setLuxuryCarFlag("0");
    		    }
    			String strShieldFirewallFlag = "";
   			    if(new UtiPower().checkChannelFirewall(strComcode)){
   			    	strShieldFirewallFlag = "1".equals(prpTitemCarExtSchema.getShieldFirewall())?"是":"否";
   			    	if("否".equals(strShieldFirewallFlag)){
			    		strShieldFirewallFlag="0";
			    	}
			    	if("是".equals(strShieldFirewallFlag)){
			    		strShieldFirewallFlag="1";
			    	}
   			     }
   			     prpTitemCarExtSchema.setShieldFirewall(strShieldFirewallFlag);
   			
   			  if("".equals(prpTitemCarExtSchema.getInDoorFlag())||prpTitemCarExtSchema.getInDoorFlag()==null){
   				prpTitemCarExtSchema.setInDoorFlag("2");
   			  }
			   






       		   
       		   prpTitemCarExtSchema.setBrandECode(blProposalBI.getBLPrpTitemCar().getArr(0).getModelCode());
       		   String controlFlag = "000000000";
       		   String ServiceAreaFlagBC = "0";
       		   
       		 
       		    
       		 
       		
       		if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSiteBC+",")>-1|| UtiPower.isDXswitch(OperateSiteBC, isNewFlagBI)){
   			 
       		}else{
       		   if(SysConfig.getProperty("setServiceAreaForZH")!=null&&!"".equals(SysConfig.getProperty("setServiceAreaForZH"))){
       			   ServiceAreaFlagBC = "1";
       			   prpTmainSchema.setServiceArea(SysConfig.getProperty("setServiceAreaForZH"));
       		   }
    	   }
       	
       		   
       		   String LastChgOwnerFlagBI = "0";
               controlFlag = ServiceAreaFlagBC + controlFlag.substring(1,9);          
                if(new UtiPower().checkLastChgOwner(prpTmainSchema.getOperateDate(), prpTmainSchema.getComCode(), riskCodeBI)){
                  controlFlag = controlFlag.substring(0,1) + LastChgOwnerFlagBI + controlFlag.substring(2,9);
                } 
               prpTitemCarExtSchema.setControlFlag(controlFlag);

    	
    			int i;
    			
    			
    			Map<String,String> dkindTreeMapFG = new HashMap<String,String>();
    		    if(riskCodeBI !=null && "0528".equals(riskCodeBI)){
    		        dkindTreeMapFG.put("A","机动车损失XX");		
    		        dkindTreeMapFG.put("B","第三者责任XX");	  
    		        dkindTreeMapFG.put("D3","车上人员责任XX（驾驶员）");
    		        dkindTreeMapFG.put("D4","车上人员责任XX（乘客）");
    		        dkindTreeMapFG.put("G1","全车盗抢XX");	  
    		        dkindTreeMapFG.put("F","玻璃单独破碎XXXXX"); 		
    		        dkindTreeMapFG.put("L","车身划痕损失XXXXX"); 		
    		        dkindTreeMapFG.put("R","精 神损害抚慰金责任XXXXX");	
    		        dkindTreeMapFG.put("M","不计免赔率XXXXX");		  
    		        dkindTreeMapFG.put("X","新增加设备损失XXXXX");
    		        dkindTreeMapFG.put("X1","发动机涉水损失XXXXX");  
    		        dkindTreeMapFG.put("D2","车上货物责任XXXXX");
    		        dkindTreeMapFG.put("Z","自燃损失XXXXX");		  
    		        dkindTreeMapFG.put("Z2","修理期间费用补偿XXXXX"); 
    		        dkindTreeMapFG.put("Z3","机动车损失XX无法找到第三方特约XXXXX");
    		        dkindTreeMapFG.put("Q3","指定修理厂XXXXX");
    		    	dkindTreeMapFG.put("K1","起重、装卸、挖掘车辆损失扩展条款");
    		    	dkindTreeMapFG.put("K2","特种车辆固定设备、仪器损坏扩展条款");
    		    	
    		    }
    			
    			
    			for(i=0;i<blProposalBI.getBLPrpTitemKind().getSize();i++){
    				String kindCode = blProposalBI.getBLPrpTitemKind().getArr(i).getKindCode();
    				PrpTitemKindSchema prpTitemKindSchema = blProposalBI.getBLPrpTitemKind().getArr(i); 
    				
    				String   strItemCode          = "0001";
    				 String   strItemDetailName    = "车辆";
    				if(kindCode!=null&&!"".equals(kindCode)){
		    			prpTitemKindSchema.setProposalNo(bizNoBI);
		    			prpTitemKindSchema.setRiskCode(riskCodeBI);
		    			BLPrpDcode blPrpDcode = new BLPrpDcode();
		    			String iWherePart = "codetype = 'KindCode'";
		    			String codeCode = "";
		    			String codeCname ="";
		    			
		    			 if(riskCodeBI !=null && "0528".equals(riskCodeBI)){
    						 prpTitemKindSchema.setKindName(dkindTreeMapFG.get(prpTitemKindSchema.getKindCode()));

		    			 }else{
		    				 blPrpDcode.query(iWherePart);
		    				 for(int n = 0;n<blPrpDcode.getSize();n++){		    					 
		    					 codeCode = blPrpDcode.getArr(n).getCodeCode();
		    					 codeCname = blPrpDcode.getArr(n).getCodeCName();
		    					 if(kindCode.equals(codeCode)||codeCode==kindCode){			    		
		    						 prpTitemKindSchema.setKindName(codeCname);
		    					 }		    
		    				 }
		    				 
		    			 }
		    			
		    			prpTitemKindSchema.setItemNo("1");
		    			prpTitemKindSchema.setItemCode(strItemCode);
		    			prpTitemKindSchema.setItemDetailName(strItemDetailName);
		    			if("".equals(prpTitemKindSchema.getAmount().trim())||prpTitemKindSchema.getAmount().trim()==null){
		    				prpTitemKindSchema.setAmount("0.00");
		    			}
		    			prpTitemKindSchema.setRatePeriod("");
		    			if("".equals(prpTitemKindSchema.getRiskPremium())||prpTitemKindSchema.getRiskPremium()==null){
		    			prpTitemKindSchema.setRiskPremium("0.00");
		    			}
		    			if(kindCode=="B"||"B".equals(kindCode)){
		    				prpTitemKindSchema.setModeCode("0");
		    			}
		    			if(kindCode=="F"||"F".equals(kindCode)){
		    				if("2".equals(prpTitemKindSchema.getModeCode())){
		    					prpTitemKindSchema.setModeName("进口玻璃");
		    				}
		    				if("1".equals(prpTitemKindSchema.getModeCode())){
		    					prpTitemKindSchema.setModeName("国产玻璃");
		    				}
		    			}
    				}
    			}

    			 
    			    DbPool dbpool = new DbPool();
    		    	try{
    		    	DBManager dbManager = new DBManager();
    		    	dbManager.open(SysConfig.CONST_DDCCDATASOURCE);
    		        dbManager.beginTransaction();
    		        dbpool.setDBManager(dbManager);
    		        BLPrpDkind blPrpDkind = new BLPrpDkind();	    	
    		        blPrpDkind.query("riskcode='"+riskCodeBI+"'" );
    		        for(int j=0;i<blProposalBI.getBLPrpTitemKind().getSize();j++){

    		        String kindCode = blProposalBI.getBLPrpTitemKind().getArr(j).getKindCode();
    		        String calculateFlag = blProposalBI.getBLPrpTitemKind().getArr(j).getCalculateFlag();
    			        if("A,B,D3,D4,G1".indexOf(kindCode)>-1){
    		
    			        	blProposalBI.getBLPrpTitemKind().getArr(j).setCalculateFlag("Y");
    			        }else{
    			        	blProposalBI.getBLPrpTitemKind().getArr(j).setCalculateFlag("N");
    			        }
    		        }
    				 dbpool.commitTransaction();
    				 
    			  }catch(Exception e){
    				e.printStackTrace();
    				dbpool.rollbackTransaction();
    			  }finally{
    				dbpool.close();
    			  }    		    
    	        

    	
                for(int m = 0;m<blProposalBI.getBLPrpTprofitDetail().getSize();m++){
    			PrpTprofitDetailSchema prpProfitDetailSchema = blProposalBI.getBLPrpTprofitDetail().getArr(m);   			
    			prpProfitDetailSchema.setProposalNo(bizNoBI);
    			String profitRate  = blProposalBI.getBLPrpTprofitDetail().getArr(m).getProfitRate();
    			String profiteRateNew = String.valueOf(Str.round(Double.parseDouble(profitRate)/100,4));
    			prpProfitDetailSchema.setProfitRate(profiteRateNew);
    			String kindcode="";
    			String kindName="";
    			String kindCodeDetail = prpProfitDetailSchema.getKindCode();
    			String kindNameDetail="";
    			    for(int p=0;p<blProposalBI.getBLPrpTitemKind().getSize();p++){
    			    	kindcode = blProposalBI.getBLPrpTitemKind().getArr(p).getKindCode();
    			    	kindName = blProposalBI.getBLPrpTitemKind().getArr(p).getKindName();
    			    	if(kindCodeDetail.equals(kindcode)||kindCodeDetail==kindcode){
        			    	kindNameDetail = kindName;
        			    }
    			    }
    			   
    			    prpProfitDetailSchema.setKindName(kindNameDetail); 

                }
                for(int n=0;n<blProposalBI.getBLPrpTitemKind().getSize();n++){
                	PrpTprofitDetailSchema prpProfitDetailSchema=new PrpTprofitDetailSchema();
                	PrpTitemKindSchema prpTitemKindSchema = blProposalBI.getBLPrpTitemKind().getArr(n); 
                	prpProfitDetailSchema.setProposalNo(bizNoBI);
                	 prpProfitDetailSchema.setRiskCode(riskCodeBI);
                     prpProfitDetailSchema.setProfitType("2");
                     prpProfitDetailSchema.setProfitPeriod("1");
                     prpProfitDetailSchema.setItemKindNo(prpTitemKindSchema.getItemKindNo());
                     prpProfitDetailSchema.setKindCode(prpTitemKindSchema.getKindCode());
                     prpProfitDetailSchema.setKindName(prpTitemKindSchema.getKindName());
                     prpProfitDetailSchema.setProfitCode("13");
                     prpProfitDetailSchema.setProfitName("无赔款优待");
                     prpProfitDetailSchema.setSerialNo("1");
                     prpProfitDetailSchema.setCondition("无赔款优待");
                     prpProfitDetailSchema.setProfitRate(""
                             + Double.parseDouble(ChgData.chgStrZero(prpTitemKindSchema.getAdjustRate()).trim()));
                     
                     if (prpTitemKindSchema.getFlag().length() > 0)
                         prpProfitDetailSchema.setFlag(prpTitemKindSchema.getFlag().charAt(0) + "");
                     else
                         prpProfitDetailSchema.setFlag("");
                     blProposalBI.getBLPrpTprofitDetail().setArr(prpProfitDetailSchema);
                }


    	
    			PrpIntefInfoSchema prpIntefInfoSchema  = blProposalBI.getBLPrpIntefInfo().getArr(0);
    			prpIntefInfoSchema.setBusinessNo(bizNoBI);
    	
        
    			
    			
    			String strDemandNo = "";
    			if(blProposalBI.getBLCIInsureDemand().getSize()>0){
    				strDemandNo = blProposalBI.getBLCIInsureDemand().getArr(0).getDemandNo();
    			}
    			
    			PremCIInsureDemandSchema blPrpCIInsureDemand = new PremCIInsureDemandSchema();
    			blPrpCIInsureDemand.setDemandNo(strDemandNo);
    			blProposalBI.getBLPrpCIInsureDemand().setArr(blPrpCIInsureDemand);
    			
				prpTitemCarSchema.setDemandNo(strDemandNo);
				
    			
    			







































































    			
    			 
    			 
    			 String relUndwrtPriorityFlag = "1";
                 String CIpolicyNo = (String) paramMap.get("cipolicyNo");
                 String CIcompanyCode = (String) paramMap.get("cicompanyCode");
            	 PrpTmainSubSchema prpTmainSubSchema = new PrpTmainSubSchema();
    			 if(blProposalBI!=null&&blProposalCI!=null){
                     
    				 prpTmainSubSchema.setProposalNo(bizNoBI);
    				 prpTmainSubSchema.setMainPolicyNo(bizNoCI);
    				 prpTmainSubSchema.setFlag("10" + relUndwrtPriorityFlag);
    			 }else if(blProposalBI!=null&&blProposalCI==null){
    				 if(!"".equals(CIpolicyNo)||!"".equals(CIcompanyCode)){
    			     
    				 prpTmainSubSchema.setProposalNo(bizNoBI);
    				 prpTmainSubSchema.setMainPolicyNo(CIpolicyNo);
    				 prpTmainSubSchema.setFlag("00" + relUndwrtPriorityFlag);
    				 prpTmainSubSchema.setRemark(CIcompanyCode);
    			     }else{
    			    	
    			     }
    			 }
    			 if(prpTmainSubSchema!=null||!"".equals(prpTmainSubSchema)){
    				 blProposalBI.getBLPrpTmainSub().setArr(prpTmainSubSchema);
    			 }

    			
 				
 				if(blProposalBI.getBLPrpTmainAgri().getSize()>0){
 					int length = blProposalBI.getBLPrpTmainAgri().getSize();
 					for(int j = blProposalBI.getBLPrpTmainAgri().getSize()-1;j>=0;j--){
 						blProposalBI.getBLPrpTmainAgri().remove(j);
 					}
 				}
    				PrpTmainAgriSchema prpTmainAgriSchema = new PrpTmainAgriSchema();
    				prpTmainAgriSchema.setProposalNo(bizNoBI);
    				prpTmainAgriSchema.setRiskCode(riskCodeBI);
    				blProposalBI.getBLPrpTmainAgri().setArr(prpTmainAgriSchema);
    				
    		    
    				if(blProposalBI.getBLPrpTmainBank().getSize()>0){
    					int length = blProposalBI.getBLPrpTmainBank().getSize();
    					for(int j = blProposalBI.getBLPrpTmainBank().getSize()-1;j>=0;j--){
    						blProposalBI.getBLPrpTmainBank().remove(j);
    					}
    				}
    				
    				PrpTmainBankSchema prpTmainBankSchema = new PrpTmainBankSchema();
    				prpTmainBankSchema.setProposalNo(bizNoBI);
    				prpTmainBankSchema.setRiskCode(riskCodeBI);
    				prpTmainBankSchema.setClassCode("05");
    				blProposalBI.getBLPrpTmainBank().setArr(prpTmainBankSchema);
    				
    				if(blProposalBI.getBLPrpTcarDriver()!=null && blProposalBI.getBLPrpTcarDriver().getSize()>0){
    					for(int num=0;num<blProposalBI.getBLPrpTcarDriver().getSize();num++){
    						blProposalBI.getBLPrpTcarDriver().getArr(num).setProposalNo(bizNoBI);
    					}
    				}
    				
    	   }
    	   
       }
}
