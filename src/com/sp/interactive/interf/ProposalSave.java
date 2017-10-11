package com.sp.interactive.interf;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import org.dom4j.Element;

import com.sp.interactive.Service.QuotationZHUtils;
import com.sp.interactive.blsvr.BLPrpInterActiveInfo;
import com.sp.interactive.schema.PrpInterActiveInfoSchema;
import com.sp.platform.dto.domain.PrpDriskConfigDto;
import com.sp.platform.ui.control.action.UIPrpDriskConfigAction;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTexpense;
import com.sp.prpall.pubfun.LifeTableInterf;
import com.sp.prpall.pubfun.PrpGenerateEngage;
import com.sp.prpall.schema.PrpTTrafficDetailSchema;
import com.sp.prpall.schema.PrpTcarDriverSchema;
import com.sp.prpall.schema.PrpTcarshipTaxSchema;
import com.sp.prpall.schema.PrpTexpenseSchema;
import com.sp.prpall.schema.PrpTfeeSchema;
import com.sp.prpall.schema.PrpTimeRegisterSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTitemCarExtSchema;
import com.sp.prpall.schema.PrpTitemCarSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTprofitDetailSchema;
import com.sp.prpall.schema.PrpTprofitSchema;
import com.sp.prpcar.blsvr.BLProposalCombCarObject;
import com.sp.prpcar.pub.BLCombCarConvertor;
import com.sp.prpcar.pub.PrpCarConstants;
import com.sp.prpcar.schema.PrpItemKindSchema;
import com.sp.prpcar.schema.PrpPlanSchema;
import com.sp.prpcar.schema.PrpProfitDetailSchema;
import com.sp.utility.StringConvertor;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;

/**
 * XXXXX存XX信息方法
 * @author qilin
 * */

public class ProposalSave {
	
	public void proposalSave(Map blProposalMap,Map paramMap, Map resultMap) throws Exception{
		
		BLProposal blProposalCI = (BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALCI);
	    BLProposal blProposalBI = (BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI);	
		BLPrpInterActiveInfo blPrpInterActiveInfo = new BLPrpInterActiveInfo();
	    try{
	    	String comCode="";	
	    if(blProposalCI!=null){
		    comCode=blProposalCI.getBLPrpTmain().getArr(0).getComCode();
	    }
	    if(blProposalBI!=null){
	    	comCode=blProposalBI.getBLPrpTmain().getArr(0).getComCode();
	    }
		
		String comcodehead = comCode.substring(0, 4);
		SimpleDateFormat formatedate= new SimpleDateFormat("yyyyMMdd");
		Date currentDate=new Date();
		String dateString=formatedate.format(currentDate);
		String priceNo = "T"+comcodehead+"05"+dateString+getCharAndNumr(6);
		paramMap.put("PriceNo", priceNo);
		
		
		RandomString randomString = new RandomString();
		String sessionID=randomString.getRandomString(15);
		
		String bizType = PrpCarConstants.BIZTYPE_T;
		BLProposalCombCarObject blProposalCombCarObject = new BLProposalCombCarObject();
		String riskCodeCI="";
		String riskCodeBI="";
		String bizNoCI="";
		String bizNoBI="";
		if(blProposalCI!=null){
		     riskCodeCI=blProposalCI.getBLPrpTmain().getArr(0).getRiskCode();
		     bizNoCI =blProposalCombCarObject.generateBizNo(comCode,riskCodeCI,sessionID,bizType, "1");
		}
		if(blProposalBI!=null){
		    riskCodeBI=blProposalBI.getBLPrpTmain().getArr(0).getRiskCode();
		    bizNoBI =blProposalCombCarObject.generateBizNo(comCode,riskCodeBI,sessionID,bizType, "1");
		}

		
		
		OthObjSchSetValue othObjSchSetValue = new OthObjSchSetValue();
		othObjSchSetValue.SchSetValue(blProposalCI,blProposalBI,bizNoCI,bizNoBI,paramMap,resultMap);
		
		if(blProposalCI!=null&&"0507".equals(riskCodeCI)){
						
			
			Hashtable feeHashTable = new Hashtable();
			int length = blProposalCI.getBLPrpTitemKind().getSize();
    		if (length > 0) {
	    		for (int i = 0; i < length; i++) {
	    			PrpTitemKindSchema  KindSchema = blProposalCI.getBLPrpTitemKind().getArr(i);
	    			PrpTfeeSchema prpTfeeSchema = (PrpTfeeSchema) feeHashTable.get(KindSchema.getCurrency());
	    			 if (prpTfeeSchema == null)
	    	                prpTfeeSchema = new PrpTfeeSchema();
	    			
	    			prpTfeeSchema.setProposalNo(bizNoCI);
	    			prpTfeeSchema.setRiskCode(riskCodeCI);
	    			prpTfeeSchema.setCurrency(KindSchema.getCurrency());
	    			
	    			 
	                if (KindSchema.getCalculateFlag().trim().equals("Y")){
	                    prpTfeeSchema.setAmount(String.valueOf(Double.parseDouble(KindSchema.getAmount())
	                            + Double.parseDouble(ChgData.chgStrZero(prpTfeeSchema.getAmount()))));
	                }else{
	                    prpTfeeSchema.setAmount(String.valueOf(Double.parseDouble(ChgData.chgStrZero(prpTfeeSchema.getAmount()))));
	                }
	                

	                prpTfeeSchema.setPremium(String.valueOf(Double.parseDouble(ChgData.chgStrZero(KindSchema.getPremium()))
	                        + Double.parseDouble(ChgData.chgStrZero(prpTfeeSchema.getPremium()))));
	                prpTfeeSchema.setFlag("");
	                
	                prpTfeeSchema.setCurrency1(prpTfeeSchema.getCurrency());
	                prpTfeeSchema.setExchangeRate1("1");
	                prpTfeeSchema.setAmount1(prpTfeeSchema.getAmount());
	                prpTfeeSchema.setPremium1(prpTfeeSchema.getPremium());
	                prpTfeeSchema.setCurrency2(prpTfeeSchema.getCurrency());
	                prpTfeeSchema.setExchangeRate2("1");
	                prpTfeeSchema.setAmount2(prpTfeeSchema.getAmount());
	                prpTfeeSchema.setPremium2(prpTfeeSchema.getPremium());
	                feeHashTable.put(KindSchema.getCurrency(), prpTfeeSchema);
	                if (blProposalCI.getBLPrpTmain().getArr(0).getCurrency().equals(
	                    prpTfeeSchema.getCurrency())) {
	                	blProposalCI.getBLPrpTmain().getArr(0).setSumAmount(prpTfeeSchema.getAmount());
	                }
	                Object[] arrObject = feeHashTable.values().toArray();
	                for (int index = 0; index < arrObject.length; index++) {
	                	blProposalCI.getBLPrpTfee().setArr((PrpTfeeSchema) arrObject[index]);
	                }
			  }
			
    	 }
    	
    		PrpPlanSchema prpPlanSchema = null; 
            Collection PrpPlanSchemaList = new ArrayList();
            int intPayTimes=1;
            int i;
            for(i=0;i<intPayTimes;i++){
            prpPlanSchema = new PrpPlanSchema();
            prpPlanSchema.setProposalNo(bizNoCI);
            prpPlanSchema.setSerialNo("1");
            prpPlanSchema.setPayNo("1");
            if(i==0){
            	prpPlanSchema.setPayReason("R10");	
            }else{
            	prpPlanSchema.setPayReason("R20");
            }
            prpPlanSchema.setPlanDate(blProposalCI.getBLPrpTitemKind().getArr(0).getStartDate());
            prpPlanSchema.setCurrency("CNY");
            prpPlanSchema.setPlanFee(blProposalCI.getBLPrpTmain().getArr(0).getSumPremium());
            prpPlanSchema.setDelinquentFee(blProposalCI.getBLPrpTmain().getArr(0).getSumPremium());
            prpPlanSchema.setFlag("");
            blProposalCI.getBLPrpTplan().setArr(prpPlanSchema);
            }
            
         
         
         

            PrpTprofitSchema prpTprofitSchema = null;
            prpTprofitSchema = new PrpTprofitSchema();
            prpTprofitSchema.setProposalNo(bizNoCI);
            prpTprofitSchema.setRiskCode(blProposalCI.getBLPrpTitemKind().getArr(0).getRiskCode());
            prpTprofitSchema.setProfitType("1");
            prpTprofitSchema.setItemKindNo(blProposalCI.getBLPrpTitemKind().getArr(0).getItemKindNo());
            prpTprofitSchema.setKindCode(blProposalCI.getBLPrpTitemKind().getArr(0).getKindCode());
            prpTprofitSchema.setCurrency(blProposalCI.getBLPrpTitemKind().getArr(0).getCurrency());
            prpTprofitSchema.setDiscount(ChgData.chgStrZero(blProposalCI.getBLPrpTitemKind().getArr(0).getDiscount()));
            prpTprofitSchema.setMinusFlag("Y");
            prpTprofitSchema.setHandlerCode(blProposalCI.getBLPrpTmain().getArr(0).getHandlerCode());
            prpTprofitSchema.setOperatorCode(blProposalCI.getBLCIInsureDemand().getArr(0).getOperatorCode());
            prpTprofitSchema.setInputDate(blProposalCI.getBLPrpTmain().getArr(0).getOperateDate());
            PrpTitemKindSchema prpTitemKindSchema = blProposalCI.getBLPrpTitemKind().getArr(0);
            if(prpTitemKindSchema.getFlag().length()>0){
            	prpTprofitSchema.setFlag(prpTitemKindSchema.getFlag().charAt(0)+"");
            }else{
            	prpTprofitSchema.setFlag("");
            }
         
            blProposalCI.getBLPrpTprofit().setArr(prpTprofitSchema);
            prpTprofitSchema = new PrpTprofitSchema();
            
            prpTprofitSchema.setProposalNo(bizNoCI);
            prpTprofitSchema.setRiskCode(blProposalCI.getBLPrpTitemKind().getArr(0).getRiskCode());
            prpTprofitSchema.setProfitType("2");
            prpTprofitSchema.setItemKindNo(blProposalCI.getBLPrpTitemKind().getArr(0).getItemKindNo());
            prpTprofitSchema.setKindCode(blProposalCI.getBLPrpTitemKind().getArr(0).getKindCode());
            prpTprofitSchema.setCurrency(blProposalCI.getBLPrpTitemKind().getArr(0).getCurrency());
            prpTprofitSchema.setDiscount(ChgData.chgStrZero(blProposalCI.getBLPrpTitemKind().getArr(0).getAdjustRate()));
            prpTprofitSchema.setMinusFlag("Y");
            prpTprofitSchema.setHandlerCode(blProposalCI.getBLPrpTmain().getArr(0).getHandlerCode());
            prpTprofitSchema.setOperatorCode(blProposalCI.getBLCIInsureDemand().getArr(0).getOperatorCode());
            prpTprofitSchema.setInputDate(blProposalCI.getBLPrpTmain().getArr(0).getOperateDate());
            if(prpTitemKindSchema.getFlag().length()>0){
            	prpTprofitSchema.setFlag(prpTitemKindSchema.getFlag().charAt(0)+"");
            }else{
            	prpTprofitSchema.setFlag("");
            }
         
            blProposalCI.getBLPrpTprofit().setArr(prpTprofitSchema);
           
            
            ChgDate chgDate1 = new ChgDate();
            String strinputTime = chgDate1.getCurrentTime("yyyy-MM-dd HH:mm:ss");
            String strSaveTime = chgDate1.getCurrentTime("yyyy-MM-dd HH:mm:ss");
            PrpTimeRegisterSchema prpTimeRegisterSchema = new PrpTimeRegisterSchema();
        	prpTimeRegisterSchema.setBusinessNo(bizNoCI);
        	paramMap.put("InputDateCI", strinputTime);
        	prpTimeRegisterSchema.setInputTime(strinputTime);
        	prpTimeRegisterSchema.setSaveTime(strSaveTime);
        	prpTimeRegisterSchema.setPolicyType("T");
        	prpTimeRegisterSchema.setSerialNo("1");
        	blProposalCI.getBLPrpTimeRegister().setArr(prpTimeRegisterSchema);

    	    
 
        	
        	
        	 String otherFlagCI = "";             
             otherFlagCI = "000000YY000000000000";
        	blProposalCI.getBLPrpTmain().getArr(0).setUnderWriteFlag("0");
        	
        	if(paramMap.get("RENEWALCIFLAG")!=null&&!"".equals(paramMap.get("RENEWALCIFLAG"))){
        	    otherFlagCI = "1" + otherFlagCI.substring(1);
        	    blProposalCI.getBLPrpTmain().getArr(0).setOthFlag(otherFlagCI);
        	}else{
        		 blProposalCI.getBLPrpTmain().getArr(0).setOthFlag(otherFlagCI);
        	}
        	
        	
        	
        	
    		PrpInterActiveInfoSchema prpInterActiveInfoSchema = new PrpInterActiveInfoSchema();
    		prpInterActiveInfoSchema.setPriceNo(priceNo);
    		prpInterActiveInfoSchema.setProposalNo(bizNoCI);
    		prpInterActiveInfoSchema.setCIPremium(blProposalCI.getBLPrpTmain().getArr(0).getSumPremium());
    		prpInterActiveInfoSchema.setBIPremium("");
    		prpInterActiveInfoSchema.setCarShipTax(blProposalCI.getBLPrpTcarshipTax().getArr(0).getSumPayTax());
    		prpInterActiveInfoSchema.setFlag("");
    		prpInterActiveInfoSchema.setISSendSuccessFlag("");
    		prpInterActiveInfoSchema.setPliocyNo("");
    		prpInterActiveInfoSchema.setRiskCode(riskCodeCI);
    		blPrpInterActiveInfo.setArr(prpInterActiveInfoSchema);
    		
    		
        	BLPrpTexpense blprpTexpense = blProposalCI.getBLPrpTexpense();
            PrpTexpenseSchema prpTexpenseSchema = null;
            if (blprpTexpense.getSize() > 0) {
                throw new UserException(-97, -1200, this.getClass().getName(),
                    "生成XX单对象赋值顺序错误，PrpTexpenseSchema对象已经生成！");
            }
            prpTexpenseSchema = new PrpTexpenseSchema();
            prpTexpenseSchema.setProposalNo(bizNoCI);
            prpTexpenseSchema.setRiskCode(riskCodeCI);
            

            
            blprpTexpense.setArr(prpTexpenseSchema);
            
    		
    		LifeTableInfoInteractive lifeTableInfoInteractive = new LifeTableInfoInteractive();
    		lifeTableInfoInteractive.lifeTableInfoSave(blProposalCI,bizNoCI,riskCodeCI);
    		
       	
    		
            prpTexpenseSchema = blProposalCI.getBLPrpTexpense().getArr(0);
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            String manageFeeRate = prpTexpenseSchema.getManageFeeRate();
            String salesSalaryRate = prpTexpenseSchema.getSalesSalaryRate();
            String expenseSpace = blProposalCI.getBLPrpTmain().getArr(0).getExpenseSpace();
            String disRate = prpTexpenseSchema.getDisRate();

            prpTexpenseSchema.setMaxManageFeeRate(new DecimalFormat("0.00").format(Double
					.parseDouble(ChgData.chgStrZero(manageFeeRate))));
            
            prpTexpenseSchema.setMaxSalesSalaryRate(new DecimalFormat("0.00").format(Double
					.parseDouble(ChgData.chgStrZero(salesSalaryRate))));
            prpTexpenseSchema.setMaxDisRate(new DecimalFormat("0.00").format(Double
					.parseDouble(ChgData.chgStrZero(disRate))));
            
            String configPowerManageFeeRateCI = "0";
            String isInitRiskCodeCI="";
            String expensesFlag2 = "";
            if(!"".equals(blProposalCI.getBLPrpTmain().getArr(0).getStartDate())){
            	isInitRiskCodeCI = "1";
            }else{
            	isInitRiskCodeCI = "0";
            }
            PrpDriskConfigDto prpDriskConfigDto = null;
            String strConfigValue = "";
            if("1".equals(isInitRiskCodeCI)){
            	prpDriskConfigDto = UIPrpDriskConfigAction.queryRiskConfig(comCode, riskCodeCI,
                        "SWITCH_MANAGEFEERATE");
            	if (prpDriskConfigDto != null) {
                    strConfigValue = prpDriskConfigDto.getConfigValue();
                    if (strConfigValue.equals("1"))
                        configPowerManageFeeRateCI = "1";
                    else
                        configPowerManageFeeRateCI = "0";
                  }
            	
            }
          
            configPowerManageFeeRateCI=StringConvertor.changeNullToEmpty(configPowerManageFeeRateCI);
            double dblDisRate1 = 0.00;
            if("1".equals(configPowerManageFeeRateCI)){
            	if(Double.parseDouble(ChgData.chgStrZero(manageFeeRate))>0){
            		expensesFlag2="3"; 
            	}else{
            		expensesFlag2="2"; 
            	}
            }else{
            	if(dblDisRate1 > 0){
            		expensesFlag2="0"; 
            	}else{
            		expensesFlag2="1"; 
            	}
            }
          
            String expensesFlag = "I" + expensesFlag2;
            prpTexpenseSchema.setFlag(expensesFlag);
            PrpTmainSchema prpTmainSchema = blProposalCI.getBLPrpTmain().getArr(0);
            prpTmainSchema.setDisRate(prpTexpenseSchema.getDisRate());
            
            
            UtiPower utiPower = new UtiPower();
            String strLifeTableConfig = "";
        	PrpDriskConfigDto configDto = UIPrpDriskConfigAction.queryRiskConfig(
        			                     blProposalCI.getBLPrpTmain().getArr(0).getComCode(),
        			                     blProposalCI.getBLPrpTmain().getArr(0).getRiskCode(),
        										"LIFETABLE_SWITCH");
        	if(configDto!=null){
        		strLifeTableConfig = StringConvertor.changeNullToEmpty(configDto.getConfigValue());
        		if(("1".equals(strLifeTableConfig)||utiPower.checkLifeTable0910(blProposalCI.getBLPrpTmain().getArr(0).getComCode()))
        		&&"8A".equals(blProposalCI.getBLPrpTitemCar().getArr(0).getUseNatureCode())){
        			new LifeTableInterf().setZeroProfitPremium(blProposalCI);
        		}
        	}
            
    		
        	Datadeal dataDeal = new Datadeal();
            
        	String operateDate = blProposalCI.getBLPrpTmain().getArr(0).getOperateDate();
        	if(utiPower.checkEngage(comCode, operateDate, "T")){
        		PrpGenerateEngage.generateEngageByRule(blProposalCI);
        	}else{
        		dataDeal.SpecialAgreement(blProposalCI, paramMap, bizNoCI);        		
        	}
        	
	  }
		
		if(blProposalBI!=null&&("0508".equals(riskCodeBI) || "0528".equals(riskCodeBI))){
			
		  
		
			Hashtable feeHashTable = new Hashtable();
			int kindlength = blProposalBI.getBLPrpTitemKind().getSize();
  		if (kindlength > 0) {
	    		for (int i = 0; i < kindlength; i++) {
	    			PrpTitemKindSchema  KindSchema = blProposalBI.getBLPrpTitemKind().getArr(i);
	    			PrpTfeeSchema prpTfeeSchema = (PrpTfeeSchema) feeHashTable.get(KindSchema.getCurrency());
	    			 if (prpTfeeSchema == null)
	    	         prpTfeeSchema = new PrpTfeeSchema();
	    			
	    			prpTfeeSchema.setProposalNo(bizNoBI);
	    			prpTfeeSchema.setRiskCode(riskCodeBI);
	    			prpTfeeSchema.setCurrency(KindSchema.getCurrency());
	    			
	    			 
	                if (KindSchema.getCalculateFlag().trim().equals("Y")){
	                    prpTfeeSchema.setAmount(String.valueOf(Double.parseDouble(KindSchema.getAmount())
	                            + Double.parseDouble(ChgData.chgStrZero(prpTfeeSchema.getAmount()))));
	                }else{
	                    prpTfeeSchema.setAmount(String.valueOf(Double.parseDouble(ChgData.chgStrZero(prpTfeeSchema.getAmount()))));
	                }
	                

	                prpTfeeSchema.setPremium(String.valueOf(Double.parseDouble(ChgData.chgStrZero(KindSchema.getPremium()))
	                        + Double.parseDouble(ChgData.chgStrZero(prpTfeeSchema.getPremium()))));
	                prpTfeeSchema.setFlag("");
	                
	                prpTfeeSchema.setCurrency1(prpTfeeSchema.getCurrency());
	                prpTfeeSchema.setExchangeRate1("1");
	                prpTfeeSchema.setAmount1(prpTfeeSchema.getAmount());
	                prpTfeeSchema.setPremium1(prpTfeeSchema.getPremium());
	                prpTfeeSchema.setCurrency2(prpTfeeSchema.getCurrency());
	                prpTfeeSchema.setExchangeRate2("1");
	                prpTfeeSchema.setAmount2(prpTfeeSchema.getAmount());
	                prpTfeeSchema.setPremium2(prpTfeeSchema.getPremium());
	                feeHashTable.put(KindSchema.getCurrency(), prpTfeeSchema);
	                if (blProposalBI.getBLPrpTmain().getArr(0).getCurrency().equals(
	                    prpTfeeSchema.getCurrency())) {
	                	blProposalBI.getBLPrpTmain().getArr(0).setSumAmount(prpTfeeSchema.getAmount());
	                }
	               
	                }
			  
  		           Object[] arrObject = feeHashTable.values().toArray();
                   for (int index = 0; index < arrObject.length; index++) {
         	       blProposalBI.getBLPrpTfee().setArr((PrpTfeeSchema) arrObject[index]);
                 }
  	         }
  	    
  		  PrpPlanSchema prpPlanSchema = null; 
          Collection PrpPlanSchemaList = new ArrayList();
          int intPayTimes=1;
          int i;
          for(i=0;i<intPayTimes;i++){
          prpPlanSchema = new PrpPlanSchema();
          prpPlanSchema.setProposalNo(bizNoBI);
          prpPlanSchema.setSerialNo("1");
          prpPlanSchema.setPayNo("1");
          if(i==0){
          	prpPlanSchema.setPayReason("R10");	
          }else{
          	prpPlanSchema.setPayReason("R20");
          }
          prpPlanSchema.setPlanDate(blProposalBI.getBLPrpTitemKind().getArr(0).getStartDate());
          prpPlanSchema.setCurrency("CNY");
          prpPlanSchema.setPlanFee(blProposalBI.getBLPrpTmain().getArr(0).getSumPremium());
          prpPlanSchema.setDelinquentFee(blProposalBI.getBLPrpTmain().getArr(0).getSumPremium());
          prpPlanSchema.setFlag("");
          blProposalBI.getBLPrpTplan().setArr(prpPlanSchema);
          }
          
       
          for(int m=0;m<kindlength;m++){

          PrpTitemKindSchema  PrpTitemKindSchema = blProposalBI.getBLPrpTitemKind().getArr(m);
          
            	 PrpTprofitSchema prpTprofitSchema = blProposalBI.getBLPrpTprofit().getArr(m*2);
            	 
                 
                 if("1".equals(prpTprofitSchema.getProfitType())){
               	  
               	  prpTprofitSchema.setProposalNo(bizNoBI);
                     prpTprofitSchema.setRiskCode(blProposalBI.getBLPrpTitemKind().getArr(m).getRiskCode());
                     prpTprofitSchema.setProfitType("1");
                     prpTprofitSchema.setItemKindNo(blProposalBI.getBLPrpTitemKind().getArr(m).getItemKindNo());
                     prpTprofitSchema.setKindCode(blProposalBI.getBLPrpTitemKind().getArr(m).getKindCode());
                     prpTprofitSchema.setCurrency(blProposalBI.getBLPrpTitemKind().getArr(m).getCurrency());
                     prpTprofitSchema.setDiscount(ChgData.chgStrZero(blProposalBI.getBLPrpTitemKind().getArr(m).getDiscount()));

                     prpTprofitSchema.setMinusFlag("Y");
                     prpTprofitSchema.setHandlerCode(blProposalBI.getBLPrpTmain().getArr(0).getHandlerCode());
                     prpTprofitSchema.setOperatorCode(blProposalBI.getBLCIInsureDemand().getArr(0).getOperatorCode());
                     prpTprofitSchema.setInputDate(blProposalBI.getBLPrpTmain().getArr(0).getOperateDate());
                     PrpTitemKindSchema prpTitemKindSchema = blProposalBI.getBLPrpTitemKind().getArr(m);
                     if(prpTitemKindSchema.getFlag().length()>0){
                     	prpTprofitSchema.setFlag(prpTitemKindSchema.getFlag().charAt(0)+"");
                     }else{
                     	prpTprofitSchema.setFlag("");
                     }
                 }

                 PrpTprofitSchema prpTprofitSchema1 = blProposalBI.getBLPrpTprofit().getArr(m*2+1);
                 if("2".equals(prpTprofitSchema1.getProfitType())){
               	  
                	 prpTprofitSchema1.setProposalNo(bizNoBI);
                	 prpTprofitSchema1.setRiskCode(blProposalBI.getBLPrpTitemKind().getArr(m).getRiskCode());
                	 prpTprofitSchema1.setProfitType("2");
                	 prpTprofitSchema1.setItemKindNo(blProposalBI.getBLPrpTitemKind().getArr(m).getItemKindNo());
                	 prpTprofitSchema1.setKindCode(blProposalBI.getBLPrpTitemKind().getArr(m).getKindCode());
                	 prpTprofitSchema1.setCurrency(blProposalBI.getBLPrpTitemKind().getArr(m).getCurrency());
                	 prpTprofitSchema1.setDiscount(ChgData.chgStrZero(blProposalBI.getBLPrpTitemKind().getArr(m).getAdjustRate()));

                	 prpTprofitSchema1.setMinusFlag("Y");
                	 prpTprofitSchema1.setHandlerCode(blProposalBI.getBLPrpTmain().getArr(0).getHandlerCode());
                	 prpTprofitSchema1.setOperatorCode(blProposalBI.getBLCIInsureDemand().getArr(0).getOperatorCode());
                	 prpTprofitSchema1.setInputDate(blProposalBI.getBLPrpTmain().getArr(0).getOperateDate());
                     PrpTitemKindSchema prpTitemKindSchema = blProposalBI.getBLPrpTitemKind().getArr(m);
                     if(prpTitemKindSchema.getFlag().length()>0){
                     	prpTprofitSchema.setFlag(prpTitemKindSchema.getFlag().charAt(0)+"");
                     }else{
                     	prpTprofitSchema.setFlag("");
                     }

             }
         
		}
          
          ChgDate chgDate1 = new ChgDate();
          String strinputTime = chgDate1.getCurrentTime("yyyy-MM-dd HH:mm:ss");
          String strSaveTime = chgDate1.getCurrentTime("yyyy-MM-dd HH:mm:ss");
          PrpTimeRegisterSchema prpTimeRegisterSchema = new PrpTimeRegisterSchema();
      	  prpTimeRegisterSchema.setBusinessNo(bizNoBI);
      	  paramMap.put("InputDateBI", strinputTime);
      	  prpTimeRegisterSchema.setInputTime(strinputTime);
      	  prpTimeRegisterSchema.setSaveTime(strSaveTime);
      	  prpTimeRegisterSchema.setPolicyType("T");
      	  prpTimeRegisterSchema.setSerialNo("1");
      	  blProposalBI.getBLPrpTimeRegister().setArr(prpTimeRegisterSchema);
      	  
      
      	  
      	  
      	   String otherFlagBI = "";
      	   otherFlagBI = "000000YY000000000000";
      	   blProposalBI.getBLPrpTmain().getArr(0).setUnderWriteFlag("0");
      	   if(paramMap.get("RENEWALBIFLAG")!=null&&!"".equals(paramMap.get("RENEWALBIFLAG"))){
      		 otherFlagBI = "1" + otherFlagBI.substring(1);
      	     blProposalBI.getBLPrpTmain().getArr(0).setOthFlag(otherFlagBI);
      	   }else{
      		 blProposalBI.getBLPrpTmain().getArr(0).setOthFlag(otherFlagBI);
      	   }
      	   
      	   
           
      	  
	      	
	  		PrpInterActiveInfoSchema prpInterActiveInfoSchema = new PrpInterActiveInfoSchema();
	  		prpInterActiveInfoSchema.setPriceNo(priceNo);
	  		prpInterActiveInfoSchema.setProposalNo(bizNoBI);
	  		prpInterActiveInfoSchema.setCIPremium("");
	  		prpInterActiveInfoSchema.setBIPremium(blProposalBI.getBLPrpTmain().getArr(0).getSumPremium());
	  		prpInterActiveInfoSchema.setCarShipTax("");
	  		prpInterActiveInfoSchema.setFlag("");
	  		prpInterActiveInfoSchema.setISSendSuccessFlag("");
	  		prpInterActiveInfoSchema.setPliocyNo("");
	  		prpInterActiveInfoSchema.setRiskCode(riskCodeBI);
	  		blPrpInterActiveInfo.setArr(prpInterActiveInfoSchema);
	  		
	  	   
	      	BLPrpTexpense blprpTexpense = blProposalBI.getBLPrpTexpense();
	          PrpTexpenseSchema prpTexpenseSchema = null;
	          if (blprpTexpense.getSize() > 0) {
	              throw new UserException(-97, -1200, this.getClass().getName(),
	                  "生成XX单对象赋值顺序错误，PrpTexpenseSchema对象已经生成！");
	          }
	          prpTexpenseSchema = new PrpTexpenseSchema();;
	          prpTexpenseSchema.setProposalNo(bizNoBI);
	          prpTexpenseSchema.setRiskCode(riskCodeBI);
	          
	          blprpTexpense.setArr(prpTexpenseSchema);
	  		
	  		
	  		LifeTableInfoInteractive lifeTableInfoInteractive = new LifeTableInfoInteractive();
			lifeTableInfoInteractive.lifeTableInfoSave(blProposalBI,bizNoBI,riskCodeBI);
			

			
			  prpTexpenseSchema = blProposalBI.getBLPrpTexpense().getArr(0);
	          DecimalFormat decimalFormat = new DecimalFormat("0.00");
	          String manageFeeRate = prpTexpenseSchema.getManageFeeRate();
	          String salesSalaryRate = prpTexpenseSchema.getSalesSalaryRate();
	          String expenseSpace = blProposalBI.getBLPrpTmain().getArr(0).getExpenseSpace();
	          String disRate = prpTexpenseSchema.getDisRate();
	          

	          prpTexpenseSchema.setMaxManageFeeRate(new DecimalFormat("0.00").format(Double
						.parseDouble(ChgData.chgStrZero(manageFeeRate))));
	          
	          prpTexpenseSchema.setMaxSalesSalaryRate(new DecimalFormat("0.00").format(Double
						.parseDouble(ChgData.chgStrZero(salesSalaryRate))));
	          prpTexpenseSchema.setMaxDisRate(new DecimalFormat("0.00").format(Double
						.parseDouble(ChgData.chgStrZero(disRate))));
	          
	          
	            String configPowerManageFeeRateBI = "0";
	            String isInitRiskCodeBI="";
	            String expensesFlag2 = "";
	            if(!"".equals(blProposalBI.getBLPrpTmain().getArr(0).getStartDate())){
	            	isInitRiskCodeBI = "1";
	            }else{
	            	isInitRiskCodeBI = "0";
	            }
	            PrpDriskConfigDto prpDriskConfigDto = null;
	            String strConfigValue = "";
	            if("1".equals(isInitRiskCodeBI)){
	            	prpDriskConfigDto = UIPrpDriskConfigAction.queryRiskConfig(comCode, riskCodeCI,
	                        "SWITCH_MANAGEFEERATE");
	            	if (prpDriskConfigDto != null) {
	                    strConfigValue = prpDriskConfigDto.getConfigValue();
	                    if (strConfigValue.equals("1"))
	                        configPowerManageFeeRateBI = "1";
	                    else
	                        configPowerManageFeeRateBI = "0";
	                  }
	            	
	            }
	            configPowerManageFeeRateBI=StringConvertor.changeNullToEmpty(configPowerManageFeeRateBI);
	            double dblDisRate1 = 0.00;
	            if("1".equals(configPowerManageFeeRateBI)){
	            	if(Double.parseDouble(ChgData.chgStrZero(manageFeeRate))>0){
	            		expensesFlag2="3"; 
	            	}else{
	            		expensesFlag2="2"; 
	            	}
	            }else{
	            	if(dblDisRate1 > 0){
	            		expensesFlag2="0"; 
	            	}else{
	            		expensesFlag2="1"; 
	            	}
	            }
	           
	            String expensesFlag = "I" + expensesFlag2;
	            prpTexpenseSchema.setFlag(expensesFlag);
	            PrpTmainSchema prpTmainSchema = blProposalBI.getBLPrpTmain().getArr(0);
	            prpTmainSchema.setDisRate(prpTexpenseSchema.getDisRate());
	          
	          
	            UtiPower utiPower = new UtiPower();
	            String strLifeTableConfig = "";
	        	PrpDriskConfigDto configDto = UIPrpDriskConfigAction.queryRiskConfig(
	        			                     blProposalBI.getBLPrpTmain().getArr(0).getComCode(),
	        			                     blProposalBI.getBLPrpTmain().getArr(0).getRiskCode(),
	        										"LIFETABLE_SWITCH");
	        	if(configDto!=null){
	        		strLifeTableConfig = StringConvertor.changeNullToEmpty(configDto.getConfigValue());
	        		if(("1".equals(strLifeTableConfig)||utiPower.checkLifeTable0910(blProposalBI.getBLPrpTmain().getArr(0).getComCode()))
	        		&&"8A".equals(blProposalBI.getBLPrpTitemCar().getArr(0).getUseNatureCode())){
	        			new LifeTableInterf().setZeroProfitPremium(blProposalBI);
	        		}
	        	}
			
	        	
	        	Datadeal dataDeal = new Datadeal();
	            
	        	String operateDate = blProposalBI.getBLPrpTmain().getArr(0).getOperateDate();
	        	if(utiPower.checkEngage(comCode, operateDate, "T")){
	        		PrpGenerateEngage.generateEngageByRule(blProposalBI);
	        	}else{	
	        		dataDeal.SpecialAgreement(blProposalBI, paramMap, bizNoBI);
	        	}
	        	
	        	
	        	for(int m=0;m<blProposalBI.getBLPrpTitemKind().getSize();m++){
	        		String kindCode=blProposalBI.getBLPrpTitemKind().getArr(m).getKindCode();
	        		if("Q3".equals(kindCode)){
	        			blProposalBI.getBLPrpTitemKind().getArr(m).setModel("");
	        		}
	        	}
	        	
	       }		
        
		String errorMessageCI = "";
		String errorMessageBI = "";
		String errorMessage = "";
        
        PreUnderwriting preUnderwriting = new PreUnderwriting();
        
        
        String operateSiteCI = "";
        String operateSiteBI = "";
        if(blProposalCI!=null){
        	operateSiteCI = blProposalCI.getBLPrpTmain().getArr(0).getOperateSite();
        }
        if(blProposalBI!=null){
        	operateSiteBI = blProposalBI.getBLPrpTmain().getArr(0).getOperateSite();
        }
        if(blProposalCI!=null && "0507".equals(riskCodeCI)
        		&& SysConfig.getProperty("CarProSave_Backward_OperateSite").indexOf(","+operateSiteCI+",")==-1){
        
        	errorMessageCI = preUnderwriting.getRuleResult(blProposalCI);
        	if(!"".equals(errorMessageCI)){
        		errorMessage += "交强XXXXX" + preUnderwriting.getRuleResult(blProposalCI)+"；";
        	}
        }
        
        
      
        if(blProposalBI!=null &&( "0508".equals(riskCodeBI) || "0528".equals(riskCodeBI))
        		
        		&& SysConfig.getProperty("CarProSave_Backward_OperateSite").indexOf(","+operateSiteBI+",")==-1){
        
        	errorMessageBI = preUnderwriting.getRuleResult(blProposalBI);
        	if(!"".equals(errorMessageBI)){
        		errorMessage += "商业XXXXX" + preUnderwriting.getRuleResult(blProposalBI)+"；";
        	}
        }
        if(!"".equals(errorMessage)){
        	throw new Exception("规则引擎异常："+errorMessage);
        }
        
		
         }catch(Exception e1){

			throw e1;
			
		}
		
         
	
		
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			String rule = SysConfig.getProperty("Transaction_BeStripped");
			if("1".equals(rule)){
				if(blProposalCI!=null){
					blProposalCI.CallsubsettleForPrpall();
				}
				if(blProposalBI!=null){	
					blProposalBI.CallsubsettleForPrpall();
				}				
			}
			
			dbpool.beginTransaction();
			String strSaveFlagCI = "I";
			String strSaveFlagBI = "I";
			if(blProposalCI!=null){
			
				blProposalCI.save(strSaveFlagCI, false, dbpool);
				
			}
			if(blProposalBI!=null){
		
				blProposalBI.save(strSaveFlagBI, false, dbpool);
				
			}
			
			blPrpInterActiveInfo.save(dbpool);
			
			dbpool.commitTransaction();
		  }catch(Exception e){
			e.printStackTrace();
			dbpool.rollbackTransaction();
            throw new Exception("核心XXXXX存有异常，请重新报价！");
		  }finally{
			dbpool.close();
		  }
		
		
	   
  }
 

	private String getCharAndNumr(int length) {
		 String val = "";     
		    Random random = new Random();     
		    for(int i = 0; i < length; i++)     
		    {     
		        String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; 
		        if("char".equalsIgnoreCase(charOrNum)) 
		        {     
		            int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; 
		            val += (char) (choice + random.nextInt(26));     
		        }     
		        else if("num".equalsIgnoreCase(charOrNum)) 
		        {     
		            val += String.valueOf(random.nextInt(10));     
		        }     
		    }     
		    return val; 
	}
}
