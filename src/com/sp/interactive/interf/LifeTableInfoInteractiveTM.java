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
import com.sp.phonesale.schema.TargetMarketInfoSchema;
import com.sp.phonesale.service.LifeTableQuery;
import com.sp.platform.bl.facade.BLPrpDSellFeeByComcodeFacade;
import com.sp.platform.bl.facade.BLPrpDUNFeeRuleConfigFacade;
import com.sp.platform.bl.facade.BLPrpDagreeDetailFacadeBase;
import com.sp.platform.bl.facade.BLPrpDprofitMarginFacade;
import com.sp.platform.bl.facade.BLPrpDsellFeeByServCodeFacade;
import com.sp.platform.bl.facade.BLPrpdfeeruleconfigFacade;
import com.sp.platform.bl.facade.BLTargetMarketFacade;
import com.sp.platform.dto.custom.TargetMarketDto;
import com.sp.platform.dto.domain.PrpDSellFeeByComcodeDto;
import com.sp.platform.dto.domain.PrpDUNFeeRuleConfigDto;
import com.sp.platform.dto.domain.PrpDagreeDetailDto;
import com.sp.platform.dto.domain.PrpDbusinessClassDetailDto;
import com.sp.platform.dto.domain.PrpDbusinessKindDto;
import com.sp.platform.dto.domain.PrpDcompanyDto;
import com.sp.platform.dto.domain.PrpDriskConfigDto;
import com.sp.platform.dto.domain.PrpDsellFeeByServCodeDto;
import com.sp.platform.dto.domain.PrpdfeeruleconfigDto;
import com.sp.platform.ui.control.action.UIPrpDriskConfigAction;
import com.sp.platform.ui.model.PrpDcompanyFindByPrimaryKeyCommand;
import com.sp.prpall.blsvr.misc.BLPrpMotorcade;
import com.sp.prpall.blsvr.misc.BLPrpdMotorcadeExpense;
import com.sp.prpall.blsvr.misc.BLPrpmotorcadeDeclare;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTexpense;
import com.sp.prpall.pubfun.LifeTableInterf;
import com.sp.prpall.schema.PremCIInsureDemandSchema;
import com.sp.prpall.schema.PrpLifeTableInfoSchema;
import com.sp.prpall.schema.PrpMotorcadeSchema;
import com.sp.prpall.schema.PrpTexpenseSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utility.StringConvertor;
import com.sp.utility.SysConfig;
import com.sp.utility.Transfer;
import com.sp.utility.UtiPower;
import com.sp.utility.string.ChgData;


public class LifeTableInfoInteractiveTM {
	
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
    			blCIInsureDemand.query( "demandno = '" + strProPosalDemandNo + "'");
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
			
		}
		prpTexpenseSchema.setRiskCode(strRiskCode);
	    
	    blprpTexpense.setArr(prpTexpenseSchema);
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
                    String strWhere = "AgreementNo='"+blProposal.getBLPrpTmain().getArr(0).getAgreementNo()+"'" + "and RiskCode in ('0507','0508','0521')";
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
	
	
	
	public TargetMarketInfoSchema lifeTableInfoTM(BLProposal blProposal,Map paramMap) throws Exception{
	     LifeTableInterf lifeTableInterf = null;
	     lifeTableInterf = new LifeTableInterf();
	     TargetMarketInfoSchema targetMarketInfoSchema = null;
	     BLPrpDcode blPrpDcode = new BLPrpDcode();
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
        
        String channelTypeOrigin =  blProposal.getBLPrpTmain().getArr(0).getChannelType();
        blProposal.getBLPrpTmain().getArr(0).setChannelTypeOrigin(channelTypeOrigin);
        
        
        if(blProposal.getBLPrpTmain().getArr(0).getChannelType().startsWith("N07")){
        	blProposal.getBLPrpTmain().getArr(0).setChannelType("N07");
        }
        
		String strLifeTableConfig = "";
		PrpDriskConfigDto configDto = UIPrpDriskConfigAction.queryRiskConfig(strMakeCom,strRiskcode,"LIFETABLE_SWITCH");
		if(configDto!=null){
			strLifeTableConfig = StringConvertor.changeNullToEmpty(configDto.getConfigValue());
		}		
		
		if("".equals(strLifeTableConfig) || "2".equals(strLifeTableConfig) || "0".equals(strLifeTableConfig)){ 
			targetMarketInfoSchema = this.calculateLifeTableToOld(blProposal, lifeTableInterf, blPrpDcode);  
		}else{
			targetMarketInfoSchema = this.calculateLifeTableToNew(blProposal, lifeTableInterf, blPrpDcode);  
		}
		paramMap.put("PayRateCarKind", blProposal.getBLPrpTitemCarExt().getArr(0).getPayRateCarKind());   
		
		
		paramMap.put("FixedFeeRate", blProposal.getBLPrpTmain().getArr(0).getFixedFeeRate()); 
		

		return targetMarketInfoSchema;
	}
	   
    public TargetMarketInfoSchema calculateLifeTableToOld(BLProposal blProposal,LifeTableInterf lifeTableInterf, BLPrpDcode blPrpDcode)throws Exception{
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
        
        lifeTableInterf.calculateNewDX(blProposal);
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
        
        lifeTableInterf.setZeroProfitPremium(blProposal);
        targetMarketInfoSchema.setZeroProfitsPremium(blProposal.getBLPrpTmain().getArr(0).getZeroProfitPremium());
        targetMarketInfoSchema.setFixedProfitMargin(prpTmainSchema.getFixedProfitMargin());
        
        
        
        
        return targetMarketInfoSchema;
    }
    
    public TargetMarketInfoSchema calculateLifeTableToNew(BLProposal blProposal,LifeTableInterf lifeTableInterf, BLPrpDcode blPrpDcode)throws Exception{
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
        
        lifeTableInterf.calculateNewDX(blProposal);
        PrpTmainSchema prpTmainSchema =blProposal.getBLPrpTmain().getArr(0);
        businessClass=prpTmainSchema.getBusinessClass();
        
        businessClassName = blPrpDcode.translateCode("PayBusinessClass",businessClass,true);
        kind=prpTmainSchema.getBusinessKind().split("@");
        if(kind!=null&&kind.length>=2){
           businessKind=kind[0];
           businessKindName=kind[1];
        }
        
        
        
        lifeTableInterf.calProfitMarginBC(prpTmainSchema, profitMarginSub, riskPremiumSub, sumPremium);
        
        
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
        
        lifeTableInterf.setZeroProfitPremium(blProposal);
        targetMarketInfoSchema.setZeroProfitsPremium(blProposal.getBLPrpTmain().getArr(0).getZeroProfitPremium());
        targetMarketInfoSchema.setFixedProfitMargin(prpTmainSchema.getFixedProfitMargin());
        
        
        targetMarketInfoSchema.setDepositRate(blProposal.getBLPrpTmain().getArr(0).getDepositRate());
        targetMarketInfoSchema.setFinalPayRate(blProposal.getBLPrpTmain().getArr(0).getFinalPayRate());
        targetMarketInfoSchema.setFinalBusinessClass(blProposal.getBLPrpTmain().getArr(0).getFinalBusinessClass());
        targetMarketInfoSchema.setProfitMarginBC(blProposal.getBLPrpTmain().getArr(0).getProfitMarginBC());
        targetMarketInfoSchema.setProfitBusinessClassBC(blProposal.getBLPrpTmain().getArr(0).getProfitBusinessClassBC());
        targetMarketInfoSchema.setFinalBusinessClassBC(blProposal.getBLPrpTmain().getArr(0).getFinalBusinessClassBC());
        
        
        String customTypeCode = Transfer.getTransfer("lifetable", "ProfitBusinessClass", targetMarketInfoSchema.getProfitBusinessClassBC());
        String customTypeName = blPrpDcode.translateCode("CustomType", customTypeCode, true);
        targetMarketInfoSchema.setCustomTypeCode(customTypeCode);
        targetMarketInfoSchema.setCustomTypeName(customTypeName);
        
        
        targetMarketInfoSchema.setCostMargin(blProposal.getBLPrpTmain().getArr(0).getCostMargin());
        targetMarketInfoSchema.setCostBusinessClass(blProposal.getBLPrpTmain().getArr(0).getCostBusinessClass());
        targetMarketInfoSchema.setCostMarginBC(blProposal.getBLPrpTmain().getArr(0).getCostMarginBC());
        targetMarketInfoSchema.setCostBusinessClassBC(blProposal.getBLPrpTmain().getArr(0).getCostBusinessClassBC());
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        



















































        
        
        
        
        
        
        
        
        LifeTableQuery lifeTableQuery=new LifeTableQuery();
        lifeTableQuery.QueryFee(blProposal, targetMarketInfoSchema);
        
        
        targetMarketInfoSchema.setFinalPayRateBC(blProposal.getBLPrpTmain().getArr(0).getFinalPayRateBC());
        
        double FeeRate = lifeTableInterf.getFeeRateByRule(blProposal);
        targetMarketInfoSchema.setFeeRate(new DecimalFormat("0.0000").format(FeeRate));
		
        return targetMarketInfoSchema;
    }
    
    public void QueryFee(BLProposal blProposal,TargetMarketInfoSchema targetMarketInfoSchema)throws Exception{
        String ChannelType = blProposal.getBLPrpTmain().getArr(0).getChannelType();   
        String ServiceArea = blProposal.getBLPrpTmain().getArr(0).getServiceArea();   
        String ComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();    
        String Flag = blProposal.getBLPrpTmain().getArr(0).getFlag();    
        String RiskCode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();  
        String BusinessModel = "";    
        if("0".equals(Flag)){
        	BusinessModel = "1";    
        }else{
        	BusinessModel = "0";    
        }
    	
        







        double SumExpenseRate = QuerySellFee(ComCode,ChannelType,ServiceArea,RiskCode);
        
        
        List prpdFeeRuleConfigList = QueryFeeRule(ComCode,ChannelType,ServiceArea,BusinessModel);
        PrpdfeeruleconfigDto  prpdFeeRuleConfigDto = null;
        double OperationCostRate = 0.0d;
        double SalesExpenseRate = 0.0d;
        double SalesExpenseThreshold = 0.0d;
        double UNSettlementRate = 0.0d;
        if(prpdFeeRuleConfigList.size()>0){
        	prpdFeeRuleConfigDto = (PrpdfeeruleconfigDto)prpdFeeRuleConfigList.get(0);
            OperationCostRate = prpdFeeRuleConfigDto.getOperationcostrate();
            SalesExpenseRate = prpdFeeRuleConfigDto.getSalesexpenserate();
            SalesExpenseThreshold = prpdFeeRuleConfigDto.getSalesexpensethreshold();
            UNSettlementRate = prpdFeeRuleConfigDto.getUNsettlementrate();
        }
        
        
        
        List prpdUNfeeruleconfigList = QueryComUNRate (ComCode,RiskCode,ServiceArea);
        
        double ComUNRate = 0.0d;
        PrpDUNFeeRuleConfigDto  prpdUNFeeRuleConfigDto = null;
        if(prpdUNfeeruleconfigList.size()>0){
        	prpdUNFeeRuleConfigDto = (PrpDUNFeeRuleConfigDto)prpdUNfeeruleconfigList.get(0);
        	ComUNRate = prpdUNFeeRuleConfigDto.getComCodeUNRate();
        }
        
        
        targetMarketInfoSchema.setSumExpenseRate(new DecimalFormat("0.0000").format(SumExpenseRate));
        targetMarketInfoSchema.setOperationCostRate(new DecimalFormat("0.0000").format(OperationCostRate));
        targetMarketInfoSchema.setSalesExpenseRate(new DecimalFormat("0.0000").format(SalesExpenseRate));
        targetMarketInfoSchema.setSalesExpenseThreshold(new DecimalFormat("0.0000").format(SalesExpenseThreshold));
        targetMarketInfoSchema.setUNSettlementRate(new DecimalFormat("0.0000").format(UNSettlementRate));
        
        targetMarketInfoSchema.setComUNRate(new DecimalFormat("0.0000").format(ComUNRate));
        
    }
    
    /**
     * @author zhaoyingchao-ghq
     * @createDate 2013-11-11
     * @description 查询非销售推动费用率
     * @param ComCode
     * @param ChannelType
     * @return prpdLifeFeeConfigDtos
     * @throws Exception
     * @modifyDate 2014-02-26
     * @modifyReason 增加查询条件“服务渠道类型”
     */
    public double QuerySellFee (String ComCode,String ChannelType,String ServiceArea,String RiskCode)throws Exception{
    		


































    		
        
    	try{
    		
    		double SumExpenseRate = 0.0d;
    		
    		double ManageFeeRate = 0.0d;
    		
    		double IndirectExpenseRate = 0.0d;
    		
    		double IndirectExpenseReservesRate = 0.0d;
    		
    		double NetSalesDepExpRate = 0.0d;
    		
    		double DepSharingExpRate = 0.0d;
    		
    		double CustomerExpRate = 0.0d;
    		
    		double SalesHumanExpRate = 0.0d;
    		PrpDSellFeeByComcodeDto prpDSellFeeByComcodeDto = new PrpDSellFeeByComcodeDto();
    		
    		List prpdSellFeeConfigByComCodeList = QuerySellFeeByComCode (ComCode,ChannelType,ServiceArea,RiskCode);
    		if(prpdSellFeeConfigByComCodeList.size()>0){
    			prpDSellFeeByComcodeDto = (PrpDSellFeeByComcodeDto)prpdSellFeeConfigByComCodeList.get(0);
                DepSharingExpRate = prpDSellFeeByComcodeDto.getDepSharingExpRate();
                IndirectExpenseRate = prpDSellFeeByComcodeDto.getIndirectExpenseRate();
                IndirectExpenseReservesRate = prpDSellFeeByComcodeDto.getIndirectExpenseReservesRate();
                ManageFeeRate = prpDSellFeeByComcodeDto.getManageFeeRate();
                NetSalesDepExpRate = prpDSellFeeByComcodeDto.getNetSalesDepExpRate();
            }
    		
    		
    		PrpDsellFeeByServCodeDto prpDsellFeeByServCodeDto = new PrpDsellFeeByServCodeDto();
    	    String strWherePartServCode = "";
    	    strWherePartServCode = "ServerAreaCode=? and validStatus=? and validDate <= SysDate order by CreateDate desc";
    	    ArrayList arrWhereValueServCode = new ArrayList();
    	    arrWhereValueServCode.add(ServiceArea);
    	    arrWhereValueServCode.add("1");
    	    BLPrpDsellFeeByServCodeFacade blPrpDsellFeeByServCodeFacade = new BLPrpDsellFeeByServCodeFacade();
    	    List PrpDsellFeeConfigByServCodeList = (List)blPrpDsellFeeByServCodeFacade.findByConditions(strWherePartServCode,arrWhereValueServCode);
    	    if(PrpDsellFeeConfigByServCodeList.size()>0){
    	    	prpDsellFeeByServCodeDto = (PrpDsellFeeByServCodeDto)PrpDsellFeeConfigByServCodeList.get(0);
    	    	CustomerExpRate = prpDsellFeeByServCodeDto.getGetCustomerExpRate();
    	    	SalesHumanExpRate = prpDsellFeeByServCodeDto.getSalesHumanExpRate();
            }
    	    
    	    
    	    SumExpenseRate = (ManageFeeRate + IndirectExpenseRate + IndirectExpenseReservesRate - NetSalesDepExpRate) + DepSharingExpRate + CustomerExpRate + SalesHumanExpRate;
            return SumExpenseRate;
        
    	}catch (Exception e) {
            e.printStackTrace();
            throw new Exception("调用平台配置系统查询错误："+e.getMessage());
       }	
    }
    
    /**
     * @author zhaoyingchao-ghq
     * @createDate 2014-03-31
     * @description 查询机构UN结算费用率
     * @param ComCode
     * @param RiskCode
     * @return prpdunfeeruleconfig
     * @throws Exception
     */
public List QueryComUNRate (String ComCode,String RiskCode,String ServiceArea)throws Exception{
    	
    	try{
    		










    		String strWherePart = "";
    	    strWherePart = " ComCode=? and RiskCode=? and serverAreaCode=?";
    	    ArrayList arrWhereValue = new ArrayList();
    	    arrWhereValue.add(ComCode);
    	    arrWhereValue.add(RiskCode);
    	    arrWhereValue.add(ServiceArea);
            BLPrpDUNFeeRuleConfigFacade blPrpdUNfeeruleconfigFacade = new BLPrpDUNFeeRuleConfigFacade(); 
            List  prpdUNfeeruleconfigList = (List)blPrpdUNfeeruleconfigFacade.findByConditions(strWherePart,arrWhereValue,0);
            
            if (prpdUNfeeruleconfigList.size() == 0) {
            	if(!"".equals(ComCode) && ComCode != null){
            		PrpDcompanyFindByPrimaryKeyCommand command3 = new PrpDcompanyFindByPrimaryKeyCommand(
            				ComCode);
    				PrpDcompanyDto pdto = (PrpDcompanyDto) command3.execute();
    				if (!pdto.getUpperComCode().equals(ComCode)) {
    					ComCode = pdto.getUpperComCode();
    					prpdUNfeeruleconfigList = QueryComUNRate (ComCode,RiskCode,ServiceArea);
    				}
            	}
			}
            return  prpdUNfeeruleconfigList;
            }catch (Exception e) {
            e.printStackTrace();
            throw new Exception("调用平台配置系统查询“机构UN结算费用率”错误："+e.getMessage());
       }	
    }
/**
 * @author zhaoyingchao-ghq
 * @createDate 2013-11-11
 * @description 查询预设经营成本率、同业销售推动费用率、销售推动费用率阀值
 * @param ComCode
 * @param ChannelType
 * @param ServiceArea
 * @param BusinessModel
 * @return prpdSellFeeConfigDtos
 * @throws Exception
 */
public List QueryFeeRule (String ComCode,String ChannelType,String ServiceArea,String BusinessModel)throws Exception{
	
	try{
        StringBuffer bufferFeeRule = new StringBuffer();
        bufferFeeRule.append(" ComCode= '");
        bufferFeeRule.append(ComCode);
        bufferFeeRule.append("' and ChannelType='");
        bufferFeeRule.append(ChannelType);
        bufferFeeRule.append("' and ServerAreaCode='");
        bufferFeeRule.append(ServiceArea);
        bufferFeeRule.append("' and BusinessModel='");
        bufferFeeRule.append(BusinessModel);
        bufferFeeRule.append("' and ValidStatus = '1' ");
        bufferFeeRule.append(" and TO_DATE(startdate,'YYYY-MM-DD HH24:MI') <= sysdate ");
        bufferFeeRule.append(" and TO_DATE(enddate,'YYYY-MM-DD HH24:MI') >= sysdate ");
        bufferFeeRule.append("order by CreateDate desc");
        BLPrpdfeeruleconfigFacade blPrpdfeeruleconfigFacade = new BLPrpdfeeruleconfigFacade();  
        List  prpdFeeRuleConfigList = (List)blPrpdfeeruleconfigFacade.findByConditions(bufferFeeRule.toString());
        if (prpdFeeRuleConfigList.size() == 0) {
        	if(!"".equals(ComCode) && ComCode != null){
        		PrpDcompanyFindByPrimaryKeyCommand command3 = new PrpDcompanyFindByPrimaryKeyCommand(
        				ComCode);
				PrpDcompanyDto pdto = (PrpDcompanyDto) command3.execute();
				if (!pdto.getUpperComCode().equals(ComCode)) {
					ComCode = pdto.getUpperComCode();
					prpdFeeRuleConfigList = QueryFeeRule (ComCode,ChannelType,ServiceArea,BusinessModel);
				}
        	}
		}
        return  prpdFeeRuleConfigList;
        }catch (Exception e) {
        e.printStackTrace();
        throw new Exception("调用平台配置系统查询错误："+e.getMessage());
   }	
}
/**
 * @author zhaoyingchao-ghq
 * @createDate 2014-06-13
 * @description 查询非销售推动费用率因子（根据机构查询）
 * @param ComCode
 * @param RiskCode
 * @return prpDSellFeeByComcodeconfigList
 * @throws Exception
 */
public List QuerySellFeeByComCode (String ComCode,String ChannelType,String ServiceArea,String RiskCode)throws Exception{
	String strWherePart = "";
    strWherePart = " ComCode=? and ChannelType=? and RiskCode=? and ValidStatus =? and ValidDate <=SysDate order by CreateDate desc";
    ArrayList arrWhereValue = new ArrayList();
    arrWhereValue.add(ComCode);
    arrWhereValue.add(ChannelType);
    
    arrWhereValue.add(RiskCode);
    arrWhereValue.add("1");
    BLPrpDSellFeeByComcodeFacade blprpDSellFeeByComcodeFacade = new BLPrpDSellFeeByComcodeFacade(); 
    List  prpDSellFeeByComcodeconfigList = (List)blprpDSellFeeByComcodeFacade.findByConditions(strWherePart,arrWhereValue);
    if (prpDSellFeeByComcodeconfigList.size() == 0) {
    	if(!"".equals(ComCode) && ComCode != null){
    		PrpDcompanyFindByPrimaryKeyCommand command3 = new PrpDcompanyFindByPrimaryKeyCommand(
    				ComCode);
			PrpDcompanyDto pdto = (PrpDcompanyDto) command3.execute();
			if (!pdto.getUpperComCode().equals(ComCode)) {
				ComCode = pdto.getUpperComCode();
				prpDSellFeeByComcodeconfigList = QuerySellFeeByComCode (ComCode,ChannelType,ServiceArea,RiskCode);
			}
    	}
	}
	return prpDSellFeeByComcodeconfigList;
}
    
    
	

 }
