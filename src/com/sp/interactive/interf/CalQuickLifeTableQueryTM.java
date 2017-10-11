package com.sp.interactive.interf;

import java.util.HashMap;
import java.util.Map;

import com.sp.phonesale.schema.HeadSchema;
import com.sp.phonesale.schema.TargetMarketInfoSchema;
import com.sp.phonesale.service.LifeTableQuery;
import com.sp.platform.bl.facade.BLPrpDprofitMarginFacade;
import com.sp.platform.dto.domain.PrpDriskConfigDto;
import com.sp.platform.ui.control.action.UIPrpDriskConfigAction;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.pubfun.CalQuickLifeTableInterf;
import com.sp.prpall.schema.PremCIInsureDemandSchema;
import com.sp.prpall.schema.PrpTexpenseSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utility.StringConvertor;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;

public class CalQuickLifeTableQueryTM {
    public TargetMarketInfoSchema calculateLifeTable(BLProposal blProposal,Map paramMap) throws Exception{
			
    	   String channelTypeOrigin =  blProposal.getBLPrpTmain().getArr(0).getChannelType();
           blProposal.getBLPrpTmain().getArr(0).setChannelTypeOrigin(channelTypeOrigin);
			

           CalQuickLifeTableInterf calQuickLifeTableInterf = null;
           calQuickLifeTableInterf = new CalQuickLifeTableInterf();
    	   TargetMarketInfoSchema targetMarketInfoSchema = null;
           BLPrpDcode blPrpDcode = new BLPrpDcode();
           Map map = new HashMap();
           if(blProposal.getBLPrpTexpense().getSize()==0){
               PrpTexpenseSchema prpTexpenseSchema = new PrpTexpenseSchema(); 
               blProposal.getBLPrpTexpense().setArr(prpTexpenseSchema);
           }
           if(blProposal.getBLPrpCIInsureDemand().getSize()>0){
               if(blProposal.getBLPrpCIInsureDemand().getSize()==0){
                   PremCIInsureDemandSchema premCIInsureDemandSchema = new PremCIInsureDemandSchema();
                   premCIInsureDemandSchema.setDemandNo(blProposal.getBLCIInsureDemand().getArr(0).getDemandNo());
                   blProposal.getBLPrpCIInsureDemand().setArr(premCIInsureDemandSchema);
               }
           }
         
	        String strRiskcode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
	        String strMakeCom=blProposal.getBLPrpTmain().getArr(0).getMakeCom();  
	        
	        if(blProposal.getBLPrpTmain().getArr(0).getChannelType().startsWith("N07")){
	        	blProposal.getBLPrpTmain().getArr(0).setChannelType("N07");
	        }
	        
			String strLifeTableConfig = "";
			PrpDriskConfigDto configDto = UIPrpDriskConfigAction.queryRiskConfig(strMakeCom,strRiskcode,"LIFETABLE_SWITCH");
			if(configDto!=null){
				strLifeTableConfig = StringConvertor.changeNullToEmpty(configDto.getConfigValue());
			}		
			
			if("".equals(strLifeTableConfig) || "2".equals(strLifeTableConfig) || "0".equals(strLifeTableConfig)){ 
				targetMarketInfoSchema = this.calculateLifeTableToOld(blProposal, calQuickLifeTableInterf, blPrpDcode);  
			}else{
				targetMarketInfoSchema = this.calculateLifeTableToNew(blProposal, calQuickLifeTableInterf, blPrpDcode);  
			}
			paramMap.put("PayRateCarKind", blProposal.getBLPrpTitemCarExt().getArr(0).getPayRateCarKind());   
			
			
			paramMap.put("FixedFeeRate", blProposal.getBLPrpTmain().getArr(0).getFixedFeeRate()); 
			
			

		return targetMarketInfoSchema;
    }
    public TargetMarketInfoSchema calculateLifeTableToOld(BLProposal blProposal,CalQuickLifeTableInterf calQuickLifeTableInterf, BLPrpDcode blPrpDcode)throws Exception{

        String businessClass="";
        String businessClassName="";
        String businessKind="";
        String businessKindName="";
        String feeClass="";
        String[] kind=null;
        String profitBusinessKind="";
        
        if(blProposal.getBLPrpTmain().getArr(0).getChannelType().startsWith("N07")){
        	blProposal.getBLPrpTmain().getArr(0).setChannelType("N07");
        }
        
        calQuickLifeTableInterf.calculateNewDX(blProposal);
        PrpTmainSchema prpTmainSchema =blProposal.getBLPrpTmain().getArr(0);
        businessClass=prpTmainSchema.getBusinessClass();
        
        businessClassName = blPrpDcode.translateCode("PayBusinessClass",businessClass,true);
        kind=prpTmainSchema.getBusinessKind().split("@");
        if(kind!=null&&kind.length>=2){
           businessKind=kind[0];
           businessKindName=kind[1];
        }
        
        feeClass=prpTmainSchema.getFeeClass();
        profitBusinessKind=prpTmainSchema.getProfitBusinessKind();
        
        PrpTexpenseSchema prpTexpenseSchema=blProposal.getBLPrpTexpense().getArr(0);
        
        TargetMarketInfoSchema targetMarketInfoSchema = new TargetMarketInfoSchema();
        targetMarketInfoSchema.setDisRate(prpTexpenseSchema.getDisRate());
        targetMarketInfoSchema.setManageFeeRate(prpTexpenseSchema.getManageFeeRate());
        targetMarketInfoSchema.setSalesSalaryRate(prpTexpenseSchema.getSalesSalaryRate());
        targetMarketInfoSchema.setTeamFeeRate(prpTexpenseSchema.getTeamFeeRate());
        targetMarketInfoSchema.setOfficeFeeRate(prpTexpenseSchema.getOfficeFeeRate());
        targetMarketInfoSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
        targetMarketInfoSchema.setMaxExpenseRate(prpTexpenseSchema.getMaxExpenseRate());
        targetMarketInfoSchema.setProfitMargin(prpTmainSchema.getProfitMargin());
        targetMarketInfoSchema.setExpenseSpace(prpTmainSchema.getExpenseSpace());
        targetMarketInfoSchema.setRiskPermium(prpTmainSchema.getRiskPremium());
        targetMarketInfoSchema.setBusinessClassCode(businessClass);
        targetMarketInfoSchema.setBusinessClassName(businessClassName);
        
        targetMarketInfoSchema.setBusinessKindCode(businessKind);
        targetMarketInfoSchema.setBusinessKindName(businessKindName);
        targetMarketInfoSchema.setFeeClass(feeClass);
        targetMarketInfoSchema.setProfitBusinessKind(profitBusinessKind);
        
        calQuickLifeTableInterf.setZeroProfitPremium(blProposal);
        targetMarketInfoSchema.setZeroProfitsPremium(blProposal.getBLPrpTmain().getArr(0).getZeroProfitPremium());
        targetMarketInfoSchema.setFixedProfitMargin(prpTmainSchema.getFixedProfitMargin());
        
        return targetMarketInfoSchema;
    }
    /**
     */
    public TargetMarketInfoSchema calculateLifeTableToNew(BLProposal blProposal,CalQuickLifeTableInterf calQuickLifeTableInterf, BLPrpDcode blPrpDcode)throws Exception{
        String businessClass="";
        String businessClassName="";
        String businessKind="";
        String businessKindName="";
        String feeClass="";
        String[] kind=null;
        String profitBusinessKind="";
        
        String profitMarginSub = blProposal.getBLPrpTmain().getArr(0).getProfitMarginSub();
        String riskPremiumSub = blProposal.getBLPrpTmain().getArr(0).getRiskPremiumSub();
        
        String sumPremium =  blProposal.getBLPrpTmain().getArr(0).getSumSubPrem();
        
        if(blProposal.getBLPrpTmain().getArr(0).getChannelType().startsWith("N07")){
        	blProposal.getBLPrpTmain().getArr(0).setChannelType("N07");
        }
        
        calQuickLifeTableInterf.calculateNewDX(blProposal);
        PrpTmainSchema prpTmainSchema =blProposal.getBLPrpTmain().getArr(0);
        businessClass=prpTmainSchema.getBusinessClass();
        
        businessClassName = blPrpDcode.translateCode("PayBusinessClass",businessClass,true);
        kind=prpTmainSchema.getBusinessKind().split("@");
        if(kind!=null&&kind.length>=2){
           businessKind=kind[0];
           businessKindName=kind[1];
        }
        
        
        
        calQuickLifeTableInterf.calProfitMarginBC(prpTmainSchema, profitMarginSub, riskPremiumSub, sumPremium);
        
        
        feeClass=prpTmainSchema.getFeeClass();
        profitBusinessKind=prpTmainSchema.getProfitBusinessKind();
        
        PrpTexpenseSchema prpTexpenseSchema=blProposal.getBLPrpTexpense().getArr(0);
        
        TargetMarketInfoSchema targetMarketInfoSchema = new TargetMarketInfoSchema();
        targetMarketInfoSchema.setDisRate(prpTexpenseSchema.getDisRate());
        targetMarketInfoSchema.setManageFeeRate(prpTexpenseSchema.getManageFeeRate());
        targetMarketInfoSchema.setSalesSalaryRate(prpTexpenseSchema.getSalesSalaryRate());
        targetMarketInfoSchema.setTeamFeeRate(prpTexpenseSchema.getTeamFeeRate());
        targetMarketInfoSchema.setOfficeFeeRate(prpTexpenseSchema.getOfficeFeeRate());
        targetMarketInfoSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
        targetMarketInfoSchema.setMaxExpenseRate(prpTexpenseSchema.getMaxExpenseRate());
        targetMarketInfoSchema.setProfitMargin(prpTmainSchema.getProfitMargin());
        targetMarketInfoSchema.setExpenseSpace(prpTmainSchema.getExpenseSpace());
        targetMarketInfoSchema.setRiskPermium(prpTmainSchema.getRiskPremium());
        targetMarketInfoSchema.setBusinessClassCode(businessClass);
        targetMarketInfoSchema.setBusinessClassName(businessClassName);
        
        targetMarketInfoSchema.setBusinessKindCode(businessKind);
        targetMarketInfoSchema.setBusinessKindName(businessKindName);
        targetMarketInfoSchema.setFeeClass(feeClass);
        targetMarketInfoSchema.setProfitBusinessKind(profitBusinessKind);
        
        calQuickLifeTableInterf.setZeroProfitPremium(blProposal);
        targetMarketInfoSchema.setZeroProfitsPremium(blProposal.getBLPrpTmain().getArr(0).getZeroProfitPremium());
        targetMarketInfoSchema.setFixedProfitMargin(prpTmainSchema.getFixedProfitMargin());
        
        
        targetMarketInfoSchema.setDepositRate(blProposal.getBLPrpTmain().getArr(0).getDepositRate());
        targetMarketInfoSchema.setFinalPayRate(blProposal.getBLPrpTmain().getArr(0).getFinalPayRate());
        targetMarketInfoSchema.setFinalBusinessClass(blProposal.getBLPrpTmain().getArr(0).getFinalBusinessClass());
        targetMarketInfoSchema.setProfitMarginBC(blProposal.getBLPrpTmain().getArr(0).getProfitMarginBC());
        targetMarketInfoSchema.setProfitBusinessClassBC(blProposal.getBLPrpTmain().getArr(0).getProfitBusinessClassBC());
        targetMarketInfoSchema.setFinalBusinessClassBC(blProposal.getBLPrpTmain().getArr(0).getFinalBusinessClassBC());
        
        
        BLPrpDprofitMarginFacade blPrpDprofitMarginFacade = new BLPrpDprofitMarginFacade();
		double douProfitMargin = Double.parseDouble(ChgData.chgStrZero(prpTmainSchema.getProfitMargin()));
		String customTypeCode = blPrpDprofitMarginFacade.getBusinessClass(
				prpTmainSchema.getComCode(), prpTmainSchema.getRiskCode(),douProfitMargin, "3");
		String customTypeName = blPrpDcode.translateCode("CustomType",customTypeCode, true);
		targetMarketInfoSchema.setCustomTypeCode(customTypeCode);
		targetMarketInfoSchema.setCustomTypeName(customTypeName);
		
        targetMarketInfoSchema.setCostMargin(blProposal.getBLPrpTmain().getArr(0).getCostMargin());
        targetMarketInfoSchema.setCostBusinessClass(blProposal.getBLPrpTmain().getArr(0).getCostBusinessClass());
        targetMarketInfoSchema.setCostMarginBC(blProposal.getBLPrpTmain().getArr(0).getCostMarginBC());
        targetMarketInfoSchema.setCostBusinessClassBC(blProposal.getBLPrpTmain().getArr(0).getCostBusinessClassBC());
        LifeTableQuery query = new LifeTableQuery();
        query.QueryFee(blProposal,targetMarketInfoSchema);
        
        
        targetMarketInfoSchema.setFinalPayRateBC(blProposal.getBLPrpTmain().getArr(0).getFinalPayRateBC());
        
        return targetMarketInfoSchema;
    }


}
