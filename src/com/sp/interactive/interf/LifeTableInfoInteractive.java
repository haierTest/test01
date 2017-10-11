package com.sp.interactive.interf;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sp.indiv.ci.blsvr.BLCIInsureDemand;
import com.sp.interactive.Service.QuotationZHUtils;
import com.sp.interactive.blsvr.BLPrpInterActiveInfo;
import com.sp.platform.bl.facade.BLPrpDagreeDetailFacadeBase;
import com.sp.platform.bl.facade.BLPrpDprofitMarginFacade;
import com.sp.platform.bl.facade.BLTargetMarketFacade;
import com.sp.platform.dto.custom.TargetMarketDto;
import com.sp.platform.dto.domain.PrpDagreeDetailDto;
import com.sp.platform.dto.domain.PrpDbusinessClassDetailDto;
import com.sp.platform.dto.domain.PrpDbusinessKindDto;
import com.sp.prpall.blsvr.misc.BLPrpMotorcade;
import com.sp.prpall.blsvr.misc.BLPrpdMotorcadeExpense;
import com.sp.prpall.blsvr.misc.BLPrpmotorcadeDeclare;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTexpense;
import com.sp.prpall.pubfun.LifeTableInterf;
import com.sp.prpall.schema.PrpLifeTableInfoSchema;
import com.sp.prpall.schema.PrpMotorcadeSchema;
import com.sp.prpall.schema.PrpTexpenseSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utility.StringConvertor;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.string.ChgData;

/**
 * */

public class LifeTableInfoInteractive {
	
	public void lifeTableInfoSave(BLProposal blProposal,String proposalNo,String RiskCode) throws Exception {

		String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
		String strRiskCode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
		
		String depositRate = "0";
		String finalBusinessClass = ""; 
		String finalBusinessClassName = ""; 
		String finalPayRate = "0"; 
		String finalPayRateBC = "0"; 
		String profitMarginBC = "0"; 
		String finalBusinessClassBC = "";
		String finalBusinessClassNameBC = "";
		String profitBusinessClassBC= ""; 
		String profitBusinessClassNameBC= ""; 

		
		String costMargin = "0";
		String costBusinessClass = "";
		String costBusinessClassName = "";
		String costMarginBC = "0";
		String costBusinessClassBC = "";
		String costBusinessClassNameBC = "";

		
		String customType = "";
		String customTypeName = "";

		
		String salesFeeRate="0";
		String maxFeeRate = "0";
		
		String maxFeeRateBC = "0";
		String fixedProfitMargin = "0"; 
		String riskPremium = "0";
		String profitMargin = "0"; 
		String businessClass = ""; 
		String businessClassName = ""; 
		String feeClass="";          
		String profitBusinessKind="";  
		String profitBusinessKindName=""; 
		String businessKind="";       
		String businessKindName="";
		String[] businessKinds=null;
		String manualFlag="0";
		String errorFlag = "0";
		String strContractNo="";   
		
		String disRate = "0"; 
		String manageFeeRate = "0"; 
		String salesSalaryRate = "0"; 
		String maxExpenseRate = "0"; 
		String listFlag="";
		TargetMarketDto targetMarketDto = null;
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		boolean LifeTableOldFlag = false ;
		PrpTmainSchema prpTmainSchema = blProposal.getBLPrpTmain().getArr(0);	    
		String expenseType = "";
		Map mapResult = null; 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strSysDate = dateFormat.format(new java.util.Date()); 
		BLTargetMarketFacade bLTargetMarketFacade = new BLTargetMarketFacade();
		PrpDbusinessClassDetailDto dto = null;
		 String strType = "0";  
		 String tempRiskPremium="";
	     String tempProfitMargin="";
	     String tempSumPremium="";
	    
	    
		if(blProposal != null && blProposal.getBLPrpTmain().getSize() > 0 && blProposal.getBLPrpTitemCar().getSize()>0) {
		    prpTmainSchema = blProposal.getBLPrpTmain().getArr(0);
		    String strLifetableFlag=new UtiPower().checkLifeTable(prpTmainSchema,blProposal.getBLPrpTitemCar().getArr(0));
			if("1".equals(strLifetableFlag)){
				LifeTableOldFlag = true;
			}
		}
		
		if (blProposal != null && blProposal.getBLPrpTmain().getSize() > 0) {
			try {
				LifeTableInterf lifetableinterf = new LifeTableInterf();
				String strProPosalDemandNo=blProposal.getBLCIInsureDemand().getArr(0).getDemandNo();
				BLCIInsureDemand blCIInsureDemand = new BLCIInsureDemand();
    			
				ArrayList iWhereValue=new ArrayList();
				iWhereValue.add(strProPosalDemandNo);
				blCIInsureDemand.query(" DemandNo = ?",iWhereValue);
				
    			if(blCIInsureDemand.getSize()>0){
    				blProposal.getBLCIInsureDemand().setArr(blCIInsureDemand.getArr(0));
    			}
    			
    			
    			
    			String strBusinessKindNew="";
    			String strBusinessKindNameNew="";
    			String strBusinessClass="";
    			String strManualFlag="0";

    			lifetableinterf.calculateInterActive(blProposal,strType,strBusinessKindNew,strBusinessKindNameNew,strBusinessClass,strManualFlag);
    			
    			businessKind = blProposal.getBLPrpTmain().getArr(0).getBusinessKind();
    			
    			businessKinds=businessKind.split("@");
				if(businessKinds!=null&&businessKinds.length>=2){
					businessKind=businessKinds[0];
					businessKindName=businessKinds[1];	
				}
				blProposal.getBLPrpTmain().getArr(0).setBusinessKind(businessKind);
				
				lifetableinterf.calProfitMarginBC(blProposal.getBLPrpTmain().getArr(0),tempProfitMargin,tempRiskPremium,tempSumPremium);
				if(!"".equals(blProposal.getBLPrpTmain().getArr(0).getDepositRate())){
					depositRate = blProposal.getBLPrpTmain().getArr(0).getDepositRate();
				}
				if(!"".equals(blProposal.getBLPrpTmain().getArr(0).getFinalPayRate())){
					finalPayRate = blProposal.getBLPrpTmain().getArr(0).getFinalPayRate();
				}else{
					finalPayRate = "";
				}		
				finalBusinessClass=blProposal.getBLPrpTmain().getArr(0).getFinalBusinessClass();
				costMargin = blProposal.getBLPrpTmain().getArr(0).getCostMargin();	
				costBusinessClass=blProposal.getBLPrpTmain().getArr(0).getCostBusinessClass();
				
				salesFeeRate=blProposal.getBLPrpTmain().getArr(0).getSalesFeeRate();
				
				maxFeeRate=blProposal.getBLPrpTmain().getArr(0).getMaxFeeRate();
				
				expenseType=blProposal.getBLPrpTmain().getArr(0).getExpenseType();
				
			    profitMarginBC = blProposal.getBLPrpTmain().getArr(0).getProfitMarginBC();
			    profitBusinessClassBC = blProposal.getBLPrpTmain().getArr(0).getProfitBusinessClassBC();
			    finalBusinessClassBC = blProposal.getBLPrpTmain().getArr(0).getFinalBusinessClassBC();
			    
			    finalPayRateBC=blProposal.getBLPrpTmain().getArr(0).getFinalPayRateBC();		
			    costMarginBC = blProposal.getBLPrpTmain().getArr(0).getCostMarginBC();
			    costBusinessClassBC=blProposal.getBLPrpTmain().getArr(0).getCostBusinessClassBC();
				
				PrpLifeTableInfoSchema prpLifeTableInfoSchema = new PrpLifeTableInfoSchema();
				prpLifeTableInfoSchema.setProposalNo(proposalNo);
				prpLifeTableInfoSchema.setDepositRate(depositRate);
				prpLifeTableInfoSchema.setFinalPayRate(finalPayRate);
				prpLifeTableInfoSchema.setFinalBusinessClass(finalBusinessClass);
				prpLifeTableInfoSchema.setCostMargin(costMargin);
				prpLifeTableInfoSchema.setCostBusinessClass(costBusinessClass);
				prpLifeTableInfoSchema.setSalesFeeRate(salesFeeRate);
				prpLifeTableInfoSchema.setMaxFeeRate(maxFeeRate);
				prpLifeTableInfoSchema.setExpenseType(expenseType);
				prpLifeTableInfoSchema.setProfitMarginBC(profitMarginBC);
				prpLifeTableInfoSchema.setProfitBusinessClassBC(profitBusinessClassBC);
				prpLifeTableInfoSchema.setFinalBusinessClassBC(finalBusinessClassBC);
				prpLifeTableInfoSchema.setFinalPayRateBC(finalPayRateBC);
				prpLifeTableInfoSchema.setCostMarginBC(costMarginBC);
				prpLifeTableInfoSchema.setCostBusinessClassBC(costBusinessClassBC);
				
				blProposal.getBlPrpLifeTableInfo().setArr(prpLifeTableInfoSchema);

    			
			}catch(Exception e){
				e.printStackTrace();
				throw e;
				
			}
		}
	}
	
	public void lifeTableInfoApp(BLProposal blProposal,String strRiskCode) throws Exception{
		
		PrpLifeTableInfoSchema prpLifeTableInfoSchema = new PrpLifeTableInfoSchema();
		blProposal.getBlPrpLifeTableInfo().setArr(prpLifeTableInfoSchema);
		BLPrpTexpense blprpTexpense = blProposal.getBLPrpTexpense();
		PrpTexpenseSchema prpTexpenseSchema = null;
		if(blProposal.getBLPrpTexpense().getSize()> 0){
			prpTexpenseSchema = blprpTexpense.getArr(0);
		}else{
			prpTexpenseSchema = new PrpTexpenseSchema();
			
			blprpTexpense.setArr(prpTexpenseSchema);
			
		}
		prpTexpenseSchema.setRiskCode(strRiskCode);
	    
	   
		PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
		prpTmainSchema = blProposal.getBLPrpTmain().getArr(0);
		
		String finalBusinessClass = ""; 
        String finalBusinessClassName = ""; 
        String finalPayRate = "0"; 
        String finalPayRateBC = "0"; 
        String profitMarginBC = "0"; 
        String finalBusinessClassBC = "";
        String finalBusinessClassNameBC = "";
        String profitBusinessClassBC= ""; 
        String profitBusinessClassNameBC= ""; 
        
        String customType = "";
        String customTypeName = "";
        
        String costMargin = "0";
        String costBusinessClass = "";
        String costBusinessClassName = "";
        String costMarginBC = "0";
        String costBusinessClassBC = "";
        String costBusinessClassNameBC = "";
        String salesFeeRate="0";
        String maxFeeRate="0";
        String maxFeeRateBC="0";
        String adjustFactor= "1";
        String adFinalPayRate = "0";
        String adFinalPayRateBC = "0";
        String adProfitMargin = "0";
        String adProfitMarginBC = "0";
        String adCostMargin = "0";
        String adCostMarginBC = "0";
        
        String profitMargin = "0"; 
        String businessClass = ""; 
        String businessClassName = ""; 
        String feeClass="";          
        String profitBusinessKind="";  
        String profitBusinessKindName=""; 
        String businessKind="";       
        String businessKindName="";
        String[] businessKinds=null;
        String manualFlag="0";
        String errorFlag = "0";
        String strContractNo="";   
        
        String disRate = "0"; 
        String manageFeeRate = "0"; 
        String salesSalaryRate = "0"; 
        String maxExpenseRate = "0"; 
        String listFlag="";
        String discribe = "";
        String fixedProfitMargin = "0"; 
        String riskPremium = "0";
        
        String expenseType = "";
        
        String expenseTypeName = "";
        
        double feeRate = 0.0d;
        boolean LifeTableOldFlag = false ;
        String depositRate = "0";
		List list=new ArrayList();
		LifeTableInterf lifetableinterf = new LifeTableInterf();
		
		String strContractno = prpTmainSchema.getContractNo();
		BLPrpMotorcade blPrpMotorcade1 = new BLPrpMotorcade();
		blPrpMotorcade1.query(" ContractNo = '" + strContractno + "'"); 
		String strSinglecarexpenseflag = "";
		if(blPrpMotorcade1.getSize()>0){
		    PrpMotorcadeSchema prpMotorcadeSchema = blPrpMotorcade1.getArr(0);
		    strSinglecarexpenseflag = prpMotorcadeSchema.getSingleCarExpenseFlag();
		}
		   if(blProposal != null && blProposal.getBLPrpTmain().getSize() > 0 && blProposal.getBLPrpTitemCar().getSize()>0) {
		        prpTmainSchema = blProposal.getBLPrpTmain().getArr(0);
		        
		        String strLifetableFlag=new UtiPower().checkLifeTable(prpTmainSchema,blProposal.getBLPrpTitemCar().getArr(0));
		        if("1".equals(strLifetableFlag)){
		            LifeTableOldFlag = true;
		            
		            if(prpTmainSchema.getContractNo()!=null&&!"".equals(prpTmainSchema.getContractNo()))
		            {
		                BLPrpMotorcade blPrpMotorcade=new BLPrpMotorcade();
		                blPrpMotorcade.query(" ContractNo='" + prpTmainSchema.getContractNo() + "'");
		                if(blPrpMotorcade.getSize()>0)
		                {
		                    String SingleCarExpenseFlag = blPrpMotorcade.getArr(0).getSingleCarExpenseFlag();
		                    if(SingleCarExpenseFlag==null||"".equals(SingleCarExpenseFlag))
		                    {
		                        SingleCarExpenseFlag="0";
		                    }
		                    if("0".equals(SingleCarExpenseFlag)){
		                        LifeTableOldFlag = false;
		                    }
		                }
		            }
		            
		        }
		    }
		   
		   if(blProposal.getBLPrpTmain().getArr(0).getChannelType().startsWith("N07")){
           	blProposal.getBLPrpTmain().getArr(0).setChannelType("N07");
           }
		
        blProposal.getBLPrpTexpense().getArr(0).setSalvationRate("");
		    list=lifetableinterf.calculateNew(blProposal,"0","","","","");
		    
        
		    if(list!=null && list.size()>0){
		        list=lifetableinterf.calculateNew(blProposal,"1","OTHER99999","其他","101BLUE","1");  
		}
        if(list==null||list.size()<1)
        {
            listFlag="0";
            maxExpenseRate = blProposal.getBLPrpTexpense().getArr(0).getMaxExpenseRate();
            disRate = blProposal.getBLPrpTexpense().getArr(0).getDisRate();
            manageFeeRate = blProposal.getBLPrpTexpense().getArr(0).getManageFeeRate();
            salesSalaryRate = blProposal.getBLPrpTexpense().getArr(0).getSalesSalaryRate();
            profitMargin = blProposal.getBLPrpTmain().getArr(0).getProfitMargin();
            feeClass=blProposal.getBLPrpTmain().getArr(0).getFeeClass();
            profitBusinessKind=blProposal.getBLPrpTmain().getArr(0).getProfitBusinessKind();
            strContractNo=blProposal.getBLPrpTmain().getArr(0).getContractNo();
            businessKind=blProposal.getBLPrpTmain().getArr(0).getBusinessKind();
            
                businessKinds=businessKind.split("@");
                if(businessKinds!=null&&businessKinds.length>=2){
                    businessKind=businessKinds[0];
                    businessKindName=businessKinds[1];  
                }
            businessClass=blProposal.getBLPrpTmain().getArr(0).getBusinessClass();
            
            feeRate = lifetableinterf.getFeeRateByRule(blProposal);
            
            manualFlag=blProposal.getBLPrpTmain().getArr(0).getManualFlag();
            if (businessClass != null && !businessClass.equals("")) {
                BLPrpDcode blPrpDcode = new BLPrpDcode();
                businessClassName = blPrpDcode.translateCode(
                        "PayBusinessClass", businessClass, true);
            }
            
            if (profitBusinessKind != null && !profitBusinessKind.equals("")) {
                BLPrpDcode blPrpDcode = new BLPrpDcode();
                if(LifeTableOldFlag){
                    profitBusinessKindName = blPrpDcode.translateCode(
                            "BusinessClass", profitBusinessKind, true);
                }else{
                    profitBusinessKindName = blPrpDcode.translateCode(
                            "ProfitBusinessClass", profitBusinessKind, true);
                }
            }
            
            if(!"".equals(blProposal.getBLPrpTmain().getArr(0).getFixedProfitMargin())){
                fixedProfitMargin = blProposal.getBLPrpTmain().getArr(0).getFixedProfitMargin();
            }
            if(!"".equals(blProposal.getBLPrpTmain().getArr(0).getRiskPremium())){
                riskPremium = blProposal.getBLPrpTmain().getArr(0).getRiskPremium();
            }
                    
            
            if(!"".equals(blProposal.getBLPrpTmain().getArr(0).getDepositRate())){
                depositRate = blProposal.getBLPrpTmain().getArr(0).getDepositRate();
            }
            if(!"".equals(blProposal.getBLPrpTmain().getArr(0).getFinalPayRate())){
                finalPayRate = blProposal.getBLPrpTmain().getArr(0).getFinalPayRate();
            }
            else{
                finalPayRate = "";
            }                   
            finalBusinessClass=blProposal.getBLPrpTmain().getArr(0).getFinalBusinessClass();
            if (finalBusinessClass != null && !finalBusinessClass.equals("")) {
                finalBusinessClassName = new BLPrpDcode().translateCode("PayBusinessClass", finalBusinessClass, true);
            }   
            
            costMargin = blProposal.getBLPrpTmain().getArr(0).getCostMargin();  
            costBusinessClass=blProposal.getBLPrpTmain().getArr(0).getCostBusinessClass();
            if (costBusinessClass != null && !costBusinessClass.equals("")) {
                costBusinessClassName = new BLPrpDcode().translateCode("ProfitBusinessClass", costBusinessClass, true);
            }
            
            salesFeeRate=blProposal.getBLPrpTmain().getArr(0).getSalesFeeRate();
            
            
            maxFeeRate=blProposal.getBLPrpTmain().getArr(0).getMaxFeeRate();
            
            
            if(!"".equals(blProposal.getBLPrpTmain().getArr(0).getAdFinalPayRate())){
                adFinalPayRate = blProposal.getBLPrpTmain().getArr(0).getAdFinalPayRate();
            }
            if(!"".equals(blProposal.getBLPrpTmain().getArr(0).getAdProfitMargin())){
                adProfitMargin = blProposal.getBLPrpTmain().getArr(0).getAdProfitMargin();
            }
            if(!"".equals(blProposal.getBLPrpTmain().getArr(0).getAdCostMargin())){
                adCostMargin = blProposal.getBLPrpTmain().getArr(0).getAdCostMargin();  
            }
            if(!"".equals(blProposal.getBLPrpTmain().getArr(0).getAdjustFactor())){
                adjustFactor = blProposal.getBLPrpTmain().getArr(0).getAdjustFactor();  
            }
            
            
            expenseType=blProposal.getBLPrpTmain().getArr(0).getExpenseType();
            
            if(expenseType!=null && !"".equals(expenseType)){
                expenseTypeName = new BLPrpDcode().translateCode("ChargingMode", expenseType, true);
            }
            double FinalPayRateM = 0;
            double CostMarginM = 0;
            String FinalBusinessClassM="";
            String CostBusinessClassM="";
            double FinalPayRateBCM = 0;
            double CostMarginBCM = 0;
            String FinalBusinessClassBCM = "";
            String CostBusinessClassBCM = "";
            if("03".equals(expenseType)){
                    BLPrpMotorcade blPrpMotorcade = new BLPrpMotorcade();
                    BLPrpmotorcadeDeclare blPrpmotorcadeDeclare = new BLPrpmotorcadeDeclare();
                    BLPrpDprofitMarginFacade blPrpDprofitMarginFacade = new BLPrpDprofitMarginFacade();
                    blPrpmotorcadeDeclare.getData(strContractNo);
                    blPrpMotorcade.query(" contractno='"+strContractNo+"' ");
                    if(blPrpmotorcadeDeclare.getSize()>0 && blPrpMotorcade.getSize()>0){
                        for(int k=0;k<blPrpmotorcadeDeclare.getSize();k++){
                            FinalPayRateBCM += Double.parseDouble(ChgData.chgStrZero(blPrpmotorcadeDeclare.getArr(k).getFinalPayRate()))
                                        *Double.parseDouble(ChgData.chgStrZero(blPrpmotorcadeDeclare.getArr(k).getPremiumProp()))/100;
                            CostMarginBCM += Double.parseDouble(ChgData.chgStrZero(blPrpmotorcadeDeclare.getArr(k).getCostRate()))
                                        *Double.parseDouble(ChgData.chgStrZero(blPrpmotorcadeDeclare.getArr(k).getPremiumProp()))/100;
                            String strWhere = "ContractNo = '" + strContractNo + "' and MotorcadeType ='99' and riskcode='" + strRiskCode + "'";
                            BLPrpdMotorcadeExpense blPrpDmotorcadeExpense = new BLPrpdMotorcadeExpense ();
                            blPrpDmotorcadeExpense.query(strWhere);
                            if (blPrpDmotorcadeExpense.getSize() > 0) {
                            FinalPayRateM = Double.parseDouble(ChgData.chgStrZero (blPrpDmotorcadeExpense.getArr(0).getPayRate()));
                            CostMarginM = Double.parseDouble(ChgData.chgStrZero (blPrpDmotorcadeExpense.getArr(0).getCostRate()));
                            FinalBusinessClassM=blPrpDprofitMarginFacade.getBusinessClass (blPrpMotorcade.getArr(0).getComCodeM(),strRiskCode,FinalPayRateM,"2");
                            CostBusinessClassM=blPrpDprofitMarginFacade.getBusinessClass (blPrpMotorcade.getArr(0).getComCodeM(),strRiskCode,CostMarginM,"4");
                            }
                        }
                        FinalBusinessClassBCM = blPrpDprofitMarginFacade.getBusinessClass(blPrpMotorcade.getArr(0).getComCodeM(),
                                "0508",FinalPayRateBCM,"2");
                        CostBusinessClassBCM = blPrpDprofitMarginFacade.getBusinessClass(blPrpMotorcade.getArr(0).getComCodeM(),
                                "0508",CostMarginBCM,"4");
                        blProposal.getBlPrpLifeTableInfo().getArr(0).setFinalPayRateBCM(new DecimalFormat("0.00").format(FinalPayRateBCM));
                        blProposal.getBlPrpLifeTableInfo().getArr(0).setFinalBusinessClassBCM(FinalBusinessClassBCM);
                        blProposal.getBlPrpLifeTableInfo().getArr(0).setCostMarginBCM(new DecimalFormat("0.00").format(CostMarginBCM));
                        blProposal.getBlPrpLifeTableInfo().getArr(0).setCostBusinessClassBCM(CostBusinessClassBCM);
                        blProposal.getBlPrpLifeTableInfo().getArr(0).setFinalPayRateM(new DecimalFormat("0.00").format(FinalPayRateM));
                        blProposal.getBlPrpLifeTableInfo().getArr(0).setFinalBusinessClassM(FinalBusinessClassM);
                        blProposal.getBlPrpLifeTableInfo().getArr(0).setCostMarginM(new DecimalFormat("0.00").format(CostMarginM));
                        blProposal.getBlPrpLifeTableInfo().getArr(0).setCostBusinessClassM(CostBusinessClassM);
                    }
                }
                else if("02".equals(expenseType)){
                    BLPrpDprofitMarginFacade blPrpDprofitMarginFacade = new BLPrpDprofitMarginFacade();
                    String strWhere = "AgreementNo='"+blProposal.getBLPrpTmain().getArr(0).getAgreementNo()+"'" + "and RiskCode in ('0507','0508','0521','0528')";
                    BLPrpDagreeDetailFacadeBase bLPrpDagreeDetailFacadeBase=new BLPrpDagreeDetailFacadeBase();
                    Collection coll= bLPrpDagreeDetailFacadeBase.findByConditions(strWhere);
                    for (Iterator iter =coll.iterator(); iter.hasNext();) {
                        PrpDagreeDetailDto prpDagreeDetailDto=(PrpDagreeDetailDto) iter.next();
                        String strDRiskcode=prpDagreeDetailDto.getRiskCode();
                        double dblAmount1 = prpDagreeDetailDto.getAmount1();
                        double dblAmount2 = prpDagreeDetailDto.getAmount2();
                        double dblDiscount1 = prpDagreeDetailDto.getDiscount1();
                        FinalPayRateBCM += dblAmount1 * dblDiscount1;
                        CostMarginBCM += dblAmount2 * dblDiscount1;
                        if(strRiskCode.equals(strDRiskcode)){
                            FinalPayRateM =dblAmount1;
                            CostMarginM = dblAmount2;
                            FinalBusinessClassM=blPrpDprofitMarginFacade.getBusinessClass (blProposal.getBLPrpTmain().getArr(0).getComCode(),strRiskCode,FinalPayRateM,"2");
                            CostBusinessClassM=blPrpDprofitMarginFacade.getBusinessClass (blProposal.getBLPrpTmain().getArr(0).getComCode(),strRiskCode,CostMarginM,"4");
                            blProposal.getBlPrpLifeTableInfo().getArr(0).setFinalPayRateM(new DecimalFormat("0.00").format(FinalPayRateM));
                            blProposal.getBlPrpLifeTableInfo().getArr(0).setFinalBusinessClassM(FinalBusinessClassM);
                            blProposal.getBlPrpLifeTableInfo().getArr(0).setCostMarginM(new DecimalFormat("0.00").format(CostMarginM));
                            blProposal.getBlPrpLifeTableInfo().getArr(0).setCostBusinessClassM(CostBusinessClassM);
                         }
                     }
                        FinalBusinessClassBCM = blPrpDprofitMarginFacade.getBusinessClass (blProposal.getBLPrpTmain().getArr(0).getComCode(),"0508",FinalPayRateBCM,"2");
                        CostBusinessClassBCM = blPrpDprofitMarginFacade.getBusinessClass (blProposal.getBLPrpTmain().getArr(0).getComCode(),"0508",CostMarginBCM,"4");
                        blProposal.getBlPrpLifeTableInfo().getArr(0).setFinalPayRateBCM(new DecimalFormat("0.00").format(FinalPayRateBCM));
                        blProposal.getBlPrpLifeTableInfo().getArr(0).setFinalBusinessClassBCM(FinalBusinessClassBCM);
                        blProposal.getBlPrpLifeTableInfo().getArr(0).setCostMarginBCM(new DecimalFormat("0.00").format(CostMarginBCM));
                        blProposal.getBlPrpLifeTableInfo().getArr(0).setCostBusinessClassBCM(CostBusinessClassBCM);
                 }
                else if("01".equals(expenseType) || "07".equals(expenseType) || "05".equals(expenseType)){
                    System.out.println("a");
                    blProposal.getBlPrpLifeTableInfo().getArr(0).setFinalPayRateM("");
                    blProposal.getBlPrpLifeTableInfo().getArr(0).setFinalBusinessClassM("");
                    blProposal.getBlPrpLifeTableInfo().getArr(0).setCostMarginM("");
                    blProposal.getBlPrpLifeTableInfo().getArr(0).setCostBusinessClassM("");
                    blProposal.getBlPrpLifeTableInfo().getArr(0).setFinalPayRateBCM("");
                    blProposal.getBlPrpLifeTableInfo().getArr(0).setFinalBusinessClassBCM("");
                    blProposal.getBlPrpLifeTableInfo().getArr(0).setCostMarginBCM("");
                    blProposal.getBlPrpLifeTableInfo().getArr(0).setCostBusinessClassBCM("");
                }
            
            String tempRiskPremium="";
            String tempProfitMargin="";
            String tempSumPremium="";
            
            String tempMaxFeeRate="";
            
            
            String tempAdProfitMargin = "";
            
            
            
            if(new UtiPower().checkCalPayrateBC(blProposal.getBLPrpTmain().getArr(0),blProposal.getBLPrpTitemCar().getArr(0))){
                String strHTProposalNo="";
                if(blProposal.getBlPrpLifeTableInfo().getSize()>0){
                    strHTProposalNo=StringConvertor.changeNullToEmpty(blProposal.getBlPrpLifeTableInfo().getArr(0).getRemark());
                }
                if(!"".equals(strHTProposalNo)){
                    lifetableinterf.calProfitMarginBCForHT(blProposal,tempProfitMargin,tempRiskPremium,tempSumPremium,strHTProposalNo);
                }
                else{
                lifetableinterf.calProfitMarginBC(blProposal.getBLPrpTmain().getArr(0),tempProfitMargin,tempRiskPremium,tempSumPremium);
                }
                
                if(expenseType!=null&&"06".equals(expenseType)){
                    lifetableinterf.calMaxFeeRateBC(blProposal.getBLPrpTmain().getArr(0),tempMaxFeeRate,tempSumPremium);
                }
                maxFeeRateBC=blProposal.getBLPrpTmain().getArr(0).getMaxFeeRateBC();
                
                
                lifetableinterf.calAdProfitMarginBC(blProposal.getBLPrpTmain().getArr(0),tempAdProfitMargin,tempRiskPremium,tempSumPremium);
                adProfitMarginBC = blProposal.getBLPrpTmain().getArr(0).getAdProfitMarginBC();
                adFinalPayRateBC = blProposal.getBLPrpTmain().getArr(0).getAdFinalPayRateBC();
                adCostMarginBC = blProposal.getBLPrpTmain().getArr(0).getAdCostMarginBC();
                
                profitMarginBC=blProposal.getBLPrpTmain().getArr(0).getProfitMarginBC();
                
                finalPayRateBC=blProposal.getBLPrpTmain().getArr(0).getFinalPayRateBC();
                profitBusinessClassBC=blProposal.getBLPrpTmain().getArr(0).getProfitBusinessClassBC();
                finalBusinessClassBC=blProposal.getBLPrpTmain().getArr(0).getFinalBusinessClassBC();
                if (profitBusinessClassBC != null && !profitBusinessClassBC.equals("")) {
                    profitBusinessClassNameBC = new BLPrpDcode().translateCode("ProfitBusinessClass", profitBusinessClassBC, true);
                }   
                if (finalBusinessClassBC != null && !finalBusinessClassBC.equals("")) {
                    finalBusinessClassNameBC = new BLPrpDcode().translateCode("PayBusinessClass", finalBusinessClassBC, true);
                }
                
                costMarginBC = blProposal.getBLPrpTmain().getArr(0).getCostMarginBC();  
                costBusinessClassBC=blProposal.getBLPrpTmain().getArr(0).getCostBusinessClassBC();
                if (costBusinessClassBC != null && !costBusinessClassBC.equals("")) {
                    costBusinessClassNameBC = new BLPrpDcode().translateCode("ProfitBusinessClass", costBusinessClassBC, true);
                }
                
                if(strContractNo!=null && !"".equals(strContractNo)){
                    BLPrpMotorcade blPrpMotorcade=new BLPrpMotorcade();
                    blPrpMotorcade.query(" ContractNo='" + strContractNo + "'");
                    String carCount = "";
                    if(blPrpMotorcade.getSize()>0){
                         carCount = blPrpMotorcade.getArr(0).getCarCount();
                    }
                    if(carCount!=null && !"".equals(carCount)){
                        if(Integer.parseInt(carCount)<Integer.parseInt(SysConfig.getProperty("ContractCarCount"))){
                            customType = "03";
                            customTypeName = "XXXXX"; 
                        }else{
                            BLPrpdMotorcadeExpense blPrpdMotorcadeExpense = new BLPrpdMotorcadeExpense();
                            blPrpdMotorcadeExpense.query("contractno='"+strContractNo+"' and  motorcadetype = '99'");
                            if(blPrpdMotorcadeExpense.getSize()>0){
                                double profitContractAll = 0.0d;
                                double profitContract = 0.0d;
                               for(int a=0;a<blPrpdMotorcadeExpense.getSize();a++){
                                   profitContract = Double.parseDouble(blPrpdMotorcadeExpense.getArr(a).getPremiumProp())*
                                   Double.parseDouble(blPrpdMotorcadeExpense.getArr(a).getCostRate())/10000;
                                   profitContractAll+=profitContract;
                               } 
                               if((1-profitContractAll)>Double.parseDouble(SysConfig.getProperty("customerLevelMax"))){
                                    customType = "01";
                                    customTypeName = "蓝色VIPXXXXX";
                               }else if((1-profitContractAll)<=Double.parseDouble(SysConfig.getProperty("customerLevelMax")) &&
                                       (1-profitContractAll)>=Double.parseDouble(SysConfig.getProperty("customerLevelMin"))){
                                    customType = "02";
                                    customTypeName = "黄色VIPXXXXX";
                               }else if((1-profitContractAll)<Double.parseDouble(SysConfig.getProperty("customerLevelMin"))){
                                    customType = "03";
                                    customTypeName = "XXXXX";
                               }
                            }
                        }
                    }
                    
                }else{
                
                
                    if (profitMarginBC != null && !profitMarginBC.equals("")) {
                        customType = new BLPrpDprofitMarginFacade().getBusinessClass(blProposal.getBLPrpTmain().getArr(0).getComCode(),
                            blProposal.getBLPrpTmain().getArr(0).getRiskCode(),Double.parseDouble(profitMarginBC),"3");
                        if(customType!=null&&!"".equals(customType)){
                            customTypeName=new BLPrpDcode().translateCode("CustomType", customType, true);
                        }           
                    }
                    if(customTypeName==null||"".equals(customTypeName)){
                        customType = "03";
                        customTypeName = "XXXXX";        
                    }
                }
            }
            else{
                profitMarginBC="";
                
                finalPayRateBC="";
                customType = "03";
                customTypeName = "XXXXX";
                costMarginBC="";
                
                adProfitMarginBC= "";
                adFinalPayRateBC= "";
                adCostMarginBC="";
                
            }                                           
        }
        
      
        blProposal.getBLPrpTmain().getArr(0).setBusinessKind(businessKind);
        blProposal.getBLPrpTmain().getArr(0).setBusinessClass(businessClass);
        blProposal.getBLPrpTmain().getArr(0).setProfitBusinessKind(businessKind);
        blProposal.getBLPrpTmain().getArr(0).setProfitMargin(new DecimalFormat("0.00").format(Double
                .parseDouble(profitMargin)));
        blProposal.getBLPrpTmain().getArr(0).setFeeClass(feeClass);
        blProposal.getBLPrpTmain().getArr(0).setManualFlag(manualFlag);
        blProposal.getBLPrpTmain().getArr(0).setFixedProfitMargin(new DecimalFormat("0.00").format(Double
                .parseDouble(fixedProfitMargin)));
        blProposal.getBLPrpTmain().getArr(0).setDisRate(blProposal.getBLPrpTexpense().getArr(0).getDisRate());
        blProposal.getBLPrpTmain().getArr(0).setRiskPremium(new DecimalFormat("0.00").format(Double.parseDouble(riskPremium)));
        blProposal.getBLPrpTmain().getArr(0).setBusinessRemark1(customType);
        prpTmainSchema = blProposal.getBLPrpTmain().getArr(0);
        
        blProposal.getBlPrpLifeTableInfo().getArr(0).setFinalPayRate(finalPayRate);
        blProposal.getBlPrpLifeTableInfo().getArr(0).setFinalBusinessClass(finalBusinessClass);
        blProposal.getBlPrpLifeTableInfo().getArr(0).setCostMargin(costMargin);
        blProposal.getBlPrpLifeTableInfo().getArr(0).setCostBusinessClass(costBusinessClass);
        blProposal.getBlPrpLifeTableInfo().getArr(0).setSalesFeeRate(salesFeeRate);
        blProposal.getBlPrpLifeTableInfo().getArr(0).setMaxFeeRate(maxFeeRate);
        blProposal.getBlPrpLifeTableInfo().getArr(0).setAdjustFactor(adjustFactor);
        blProposal.getBlPrpLifeTableInfo().getArr(0).setAdProfitMargin(new DecimalFormat("0.00").format(Double
                .parseDouble(adProfitMargin)));
        blProposal.getBlPrpLifeTableInfo().getArr(0).setAdFinalPayRate(adFinalPayRate);
        blProposal.getBlPrpLifeTableInfo().getArr(0).setAdCostMargin(adCostMargin);
        blProposal.getBlPrpLifeTableInfo().getArr(0).setExpenseType(expenseType);
        
        blProposal.getBLPrpTexpense().getArr(0).setMaxDisRate(new DecimalFormat("0.00").format(Double
                .parseDouble(disRate)));
        blProposal.getBLPrpTexpense().getArr(0).setManageFeeRate(new DecimalFormat("0.00").format(Double
                .parseDouble(manageFeeRate)));
        blProposal.getBLPrpTexpense().getArr(0).setMaxManageFeeRate(new DecimalFormat("0.00").format(Double
                .parseDouble(manageFeeRate)));
        blProposal.getBLPrpTexpense().getArr(0).setSalesSalaryRate(new DecimalFormat("0.00").format(Double
                .parseDouble(salesSalaryRate)));
        blProposal.getBLPrpTexpense().getArr(0).setMaxSalesSalaryRate(new DecimalFormat("0.00").format(Double
                .parseDouble(salesSalaryRate)));
        if(LifeTableOldFlag){
            if(strRiskCode!=null && "0507".equals(strRiskCode)){
                blProposal.getBLPrpTexpense().getArr(0).setMaxDisRate("4");
            }else{
                blProposal.getBLPrpTexpense().getArr(0).setMaxDisRate("30");
            }
            blProposal.getBLPrpTexpense().getArr(0).setMaxManageFeeRate("35");
            blProposal.getBLPrpTexpense().getArr(0).setMaxSalesSalaryRate("15");
        }
	}
	
	

 }
