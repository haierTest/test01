package com.sp.interactive.interf;

import java.util.Map;

import com.sp.interactive.Service.QuotationZHUtils;
import com.sp.prpall.blsvr.cb.BLPrpCTrafficDetail;
import com.sp.prpall.blsvr.cb.BLPrpCcarDriver;
import com.sp.prpall.blsvr.cb.BLPrpCexpense;
import com.sp.prpall.blsvr.cb.BLPrpCitemCar;
import com.sp.prpall.blsvr.cb.BLPrpCitemKind;
import com.sp.prpall.blsvr.cb.BLPrpClifeTableInfo;
import com.sp.prpall.blsvr.cb.BLPrpCmain;
import com.sp.prpall.blsvr.cb.BLPrpCprofitDetail;
import com.sp.prpall.blsvr.misc.BLPrpIntefInfo;
import com.sp.prpall.blsvr.misc.BLPrpLifeTableInfo;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTTrafficDetail;
import com.sp.prpall.blsvr.tb.BLPrpTcarDriver;
import com.sp.prpall.blsvr.tb.BLPrpTexpense;
import com.sp.prpall.blsvr.tb.BLPrpTitemCar;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.blsvr.tb.BLPrpTprofitDetail;
import com.sp.prpall.schema.PrpTmainSubSchema;
import com.sp.prpcar.blsvr.BLProposalCombCarObject;
import com.sp.utility.StringConvertor;


public class PrpRelationUtilTM {
	public void prpRelation(BLProposal blProposalCI,BLProposal blProposalBI) throws Exception{
		String inputRiskCI = "";
		String inputRiskBI = "";
		inputRiskCI = blProposalCI  == null?"0":"1";
		inputRiskBI = blProposalBI == null?"0":"1";
		String errorFlag = "0"; 
		String discribe = "";   
		String errorCheckSaveFlagCI="0"; 
		String errorCheckSaveFlagBI="0"; 
		Object ruleArgu=null;
		String ruleDesc="";
		BLProposal blProposal = null;
		
		String strProposalNoCI=StringConvertor.changeNullToEmpty(null);
		String strProposalNoBI=StringConvertor.changeNullToEmpty(null);
		String strPolicyNoCI=StringConvertor.changeNullToEmpty(null);
		String strPolicyNoBI=StringConvertor.changeNullToEmpty(null);
		BLPrpCmain blPrpCmain=new BLPrpCmain();
		BLPrpCitemCar blPrpCitemCar = new BLPrpCitemCar();
		BLPrpCitemKind blPrpCitemKind = new BLPrpCitemKind();
		BLPrpCprofitDetail blPrpCprofitDetail = new BLPrpCprofitDetail();
		BLPrpCTrafficDetail blPrpCtrafficDetail = new BLPrpCTrafficDetail();
		BLPrpTmain blPrpTmain=new BLPrpTmain();
		BLPrpTitemCar blPrpTitemCar = new BLPrpTitemCar();
		BLPrpTitemKind blPrpTitemKind = new BLPrpTitemKind();
		BLPrpTprofitDetail blPrpTprofitDetail = new BLPrpTprofitDetail();
		BLPrpTTrafficDetail blPrpTtrafficDetail = new BLPrpTTrafficDetail();
		BLPrpIntefInfo blPrpIntefInfo = new BLPrpIntefInfo();
		
		BLPrpTexpense blPrpTexpense = new BLPrpTexpense();
		BLPrpCexpense blPrpCexpense = new BLPrpCexpense();
		
		
		BLPrpClifeTableInfo blPrpClifeTableInfo = new BLPrpClifeTableInfo();
		BLPrpLifeTableInfo blPrpLifeTableInfo = new BLPrpLifeTableInfo();
		
		
		if(inputRiskCI.equals("1")&&inputRiskBI.equals("0")){
			
			if(blProposalCI.getBLPrpTmainSub().getSize()<1){
				blProposalCI.getBLPrpTmainSub().setArr(new PrpTmainSubSchema());
			}
			if((!"".equals(strProposalNoBI)||!"".equals(strPolicyNoBI))&&blProposalCI.getBLPrpTmainSub().getSize()>0){
				if(blProposalCI.getBLPrpTmain().getSize()>0){
		      	  blProposalCI.getBLPrpTmain().getArr(0).setCombine(true);
		        }
		        
		        
		        if(!"".equals(strPolicyNoBI)){
		        	blPrpCmain.getData(strPolicyNoBI);
					if (blPrpCmain.getSize() > 0) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().setPrpCmainSchema(
						blPrpCmain.getArr(0));
					}
					
					blPrpCitemCar.getData(strPolicyNoBI);
					for (int i = 0; i < blPrpCitemCar.getSize(); i++) {
						if (blPrpCitemCar.getArr(i) != null) {
							blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCitemCar().add(
							blPrpCitemCar.getArr(i));
						}
					}
					
					blPrpCexpense.getData(strPolicyNoBI);
					for (int i = 0; i < blPrpCexpense.getSize(); i++) {
						if (blPrpCexpense.getArr(i) != null) {
							blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCexpense().add(
							blPrpCexpense.getArr(i));
						}
					}
					
					blPrpCitemKind.getData(strPolicyNoBI);
					for (int i = 0; i < blPrpCitemKind.getSize(); i++) {
						if (blPrpCitemKind.getArr(i) != null) {
							blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCitemKind().add(
							blPrpCitemKind.getArr(i));
						}
					}
					blPrpCprofitDetail.getData(strPolicyNoBI);
					for (int i = 0; i < blPrpCprofitDetail.getSize(); i++) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCprofitDetail().add(
						blPrpCprofitDetail.getArr(i));
					}
					String business_str = " PolicyNo='" + strPolicyNoBI + "'";
					blPrpCtrafficDetail.query(business_str);
					for (int i = 0; i < blPrpCtrafficDetail.getSize(); i++) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCtrafficDetail().add(
						blPrpCtrafficDetail.getArr(i));
					}
					blPrpIntefInfo.getData(strPolicyNoBI);
					for (int i = 0; i < blPrpIntefInfo.getSize(); i++) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpIntefInfo().add(
						blPrpIntefInfo.getArr(i));
					}	
					
					blPrpClifeTableInfo.getData(strPolicyNoBI);
					if (blPrpClifeTableInfo.getSize() > 0) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().setPrpClifeTableInfoSchema(
						blPrpClifeTableInfo.getArr(0));
					}
					
		        }
		        else{
		        	blPrpTmain.getData(strProposalNoBI);
					if (blPrpTmain.getSize() > 0) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().setPrpCmainSchema(
						blPrpCmain.Evaluate(blPrpTmain.getArr(0),""));
					}
					
					blPrpTitemCar.getData(strProposalNoBI);
					for (int i = 0; i < blPrpTitemCar.getSize(); i++) {
						if (blPrpTitemCar.getArr(i) != null) {
							blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCitemCar().add(
							blPrpCitemCar.Evaluate(blPrpTitemCar.getArr(i),""));
						}
					}
					
					blPrpTexpense.getData(strProposalNoBI);
					for (int i = 0; i < blPrpTexpense.getSize(); i++) {
						if (blPrpTexpense.getArr(i) != null) {
							blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCexpense().add(
							blPrpCexpense.Evaluate(blPrpTexpense.getArr(i),""));
						}
					}
					
					blPrpTitemKind.getData(strProposalNoBI);
					for (int i = 0; i < blPrpTitemKind.getSize(); i++) {
						if (blPrpTitemKind.getArr(i) != null) {
							blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCitemKind().add(
							blPrpCitemKind.Evaluate(blPrpTitemKind.getArr(i),""));
						}
					}
					blPrpTprofitDetail.getData(strProposalNoBI);
					for (int i = 0; i < blPrpTprofitDetail.getSize(); i++) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCprofitDetail().add(
						blPrpCprofitDetail.Evaluate(blPrpTprofitDetail.getArr(i),""));
					}
					String business_str = " ProposalNo='" + strProposalNoBI + "'";
					blPrpTtrafficDetail.query(business_str);
					for (int i = 0; i < blPrpTtrafficDetail.getSize(); i++) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCtrafficDetail().add(
						blPrpCtrafficDetail.Evaluate(blPrpTtrafficDetail.getArr(i),""));
					}
					blPrpIntefInfo.getData(strProposalNoBI);
					for (int i = 0; i < blPrpIntefInfo.getSize(); i++) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpIntefInfo().add(
						blPrpIntefInfo.getArr(i));
					}        
					
					blPrpLifeTableInfo.getData(strProposalNoBI);
					if (blPrpLifeTableInfo.getSize() > 0) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().setPrpClifeTableInfoSchema(
						blPrpLifeTableInfo.Evaluate(blPrpLifeTableInfo.getArr(0), ""));
					}
					
		        }
			}	
		}else if(inputRiskCI.equals("0")&&inputRiskBI.equals("1")){
			
			
			if(blProposalBI.getBLPrpTmainSub().getSize()<1){
				blProposalBI.getBLPrpTmainSub().setArr(new PrpTmainSubSchema());
			}		
			if((!"".equals(strProposalNoCI)||!"".equals(strPolicyNoCI))&&blProposalBI.getBLPrpTmainSub().getSize()>0){
				if(blProposalBI.getBLPrpTmain().getSize()>0){
		      	  blProposalBI.getBLPrpTmain().getArr(0).setCombine(true);
		        }
		        
		        
		        if(!"".equals(strPolicyNoCI)){
		        	blPrpCmain.getData(strPolicyNoCI);
					if (blPrpCmain.getSize() > 0) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().setPrpCmainSchema(
						blPrpCmain.getArr(0));
					}
					
					blPrpCitemCar.getData(strPolicyNoCI);
					for (int i = 0; i < blPrpCitemCar.getSize(); i++) {
						if (blPrpCitemCar.getArr(i) != null) {
							blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCitemCar().add(
							blPrpCitemCar.getArr(i));
						}
					}
					
					blPrpCexpense.getData(strPolicyNoCI);
					for (int i = 0; i < blPrpCexpense.getSize(); i++) {
						if (blPrpCexpense.getArr(i) != null) {
							blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCexpense().add(
							blPrpCexpense.getArr(i));
						}
					}
					
					blPrpCitemKind.getData(strPolicyNoCI);
					for (int i = 0; i < blPrpCitemKind.getSize(); i++) {
						if (blPrpCitemKind.getArr(i) != null) {
							blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCitemKind().add(
							blPrpCitemKind.getArr(i));
						}
					}
					blPrpCprofitDetail.getData(strPolicyNoCI);
					for (int i = 0; i < blPrpCprofitDetail.getSize(); i++) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCprofitDetail().add(
						blPrpCprofitDetail.getArr(i));
					}
					String business_str = " PolicyNo='" + strPolicyNoCI + "'";
					blPrpCtrafficDetail.query(business_str);
					for (int i = 0; i < blPrpCtrafficDetail.getSize(); i++) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCtrafficDetail().add(
						blPrpCtrafficDetail.getArr(i));
					}
					blPrpIntefInfo.getData(strPolicyNoCI);
					for (int i = 0; i < blPrpIntefInfo.getSize(); i++) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpIntefInfo().add(
						blPrpIntefInfo.getArr(i));
					}
					
					blPrpClifeTableInfo.getData(strPolicyNoCI);
					if (blPrpClifeTableInfo.getSize() > 0) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().setPrpClifeTableInfoSchema(
						blPrpClifeTableInfo.getArr(0));
					}
					
		        }
		        else{
		        	blPrpTmain.getData(strProposalNoCI);
					if (blPrpTmain.getSize() > 0) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().setPrpCmainSchema(
						blPrpCmain.Evaluate(blPrpTmain.getArr(0),""));
					}
					
					blPrpTitemCar.getData(strProposalNoCI);
					for (int i = 0; i < blPrpTitemCar.getSize(); i++) {
						if (blPrpTitemCar.getArr(i) != null) {
							blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCitemCar().add(
							blPrpCitemCar.Evaluate(blPrpTitemCar.getArr(i),""));
						}
					}
					
					blPrpTexpense.getData(strProposalNoCI);
					for (int i = 0; i < blPrpTexpense.getSize(); i++) {
						if (blPrpTexpense.getArr(i) != null) {
							blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCexpense().add(
							blPrpCexpense.Evaluate(blPrpTexpense.getArr(i),""));
						}
					}
					
					blPrpTitemKind.getData(strProposalNoCI);
					for (int i = 0; i < blPrpTitemKind.getSize(); i++) {
						if (blPrpTitemKind.getArr(i) != null) {
							blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCitemKind().add(
							blPrpCitemKind.Evaluate(blPrpTitemKind.getArr(i),""));
						}
					}
					blPrpTprofitDetail.getData(strProposalNoCI);
					for (int i = 0; i < blPrpTprofitDetail.getSize(); i++) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCprofitDetail().add(
						blPrpCprofitDetail.Evaluate(blPrpTprofitDetail.getArr(i),""));
					}
					String business_str = " ProposalNo='" + strProposalNoCI + "'";
					blPrpTtrafficDetail.query(business_str);
					for (int i = 0; i < blPrpTtrafficDetail.getSize(); i++) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCtrafficDetail().add(
						blPrpCtrafficDetail.Evaluate(blPrpTtrafficDetail.getArr(i),""));
					}
					blPrpIntefInfo.getData(strProposalNoCI);
					for (int i = 0; i < blPrpIntefInfo.getSize(); i++) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpIntefInfo().add(
						blPrpIntefInfo.getArr(i));
					}	      
					
					blPrpLifeTableInfo.getData(strProposalNoCI);
					if (blPrpLifeTableInfo.getSize() > 0) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().setPrpClifeTableInfoSchema(
						blPrpLifeTableInfo.Evaluate(blPrpLifeTableInfo.getArr(0), ""));
					}
					
		        }
			}	
		}else if(inputRiskCI.equals("1")&&inputRiskBI.equals("1")){
			
	        if(blProposalCI.getBLPrpTmain().getSize()>0){
	      	  blProposalCI.getBLPrpTmain().getArr(0).setCombine(true);
	        }
	        if(blProposalCI.getBLPrpTmainSub().getSize()<=0){
				blProposalCI.getBLPrpTmainSub().setArr(new PrpTmainSubSchema());
			}
			if(blProposalCI.getBLPrpTmainSub().getSize()>0){
				if(blProposalBI.getBLPrpTmain().getSize()>0){
					blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().setPrpCmainSchema(
					blPrpCmain.Evaluate(blProposalBI.getBLPrpTmain().getArr(0),""));	
				}
				
				for (int i = 0; i < blProposalBI.getBLPrpTitemCar().getSize(); i++) {
					if (blProposalBI.getBLPrpTitemCar().getArr(i) != null) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCitemCar().add(
						blPrpCitemCar.Evaluate(blProposalBI.getBLPrpTitemCar().getArr(i),""));
					}
				}
				
				for (int i = 0; i < blProposalBI.getBLPrpTexpense().getSize(); i++) {
					if (blProposalBI.getBLPrpTexpense().getArr(i) != null) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCexpense().add(
						blPrpCexpense.Evaluate(blProposalBI.getBLPrpTexpense().getArr(i),""));
					}
				}
				
				for (int i = 0; i < blProposalBI.getBLPrpTitemKind().getSize(); i++) {
					if (blProposalBI.getBLPrpTitemKind().getArr(i) != null) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCitemKind().add(
						blPrpCitemKind.Evaluate(blProposalBI.getBLPrpTitemKind().getArr(i),""));
					}
				}
				for (int i = 0; i < blProposalBI.getBLPrpTprofitDetail().getSize(); i++) {
					if (blProposalBI.getBLPrpTprofitDetail().getArr(i) != null) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCprofitDetail().add(
						blPrpCprofitDetail.Evaluate(blProposalBI.getBLPrpTprofitDetail().getArr(i),""));
					}
				}
				for (int i = 0; i < blProposalBI.getBLPrpTTrafficDetail().getSize(); i++) {
					if (blProposalBI.getBLPrpTTrafficDetail().getArr(i) != null) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCtrafficDetail().add(
						blPrpCtrafficDetail.Evaluate(blProposalBI.getBLPrpTTrafficDetail().getArr(i),""));
					}
				}
				for (int i = 0; i < blProposalBI.getBLPrpIntefInfo().getSize(); i++) {
					if (blProposalBI.getBLPrpIntefInfo().getArr(i) != null) {
						blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpIntefInfo().add(
						blProposalBI.getBLPrpIntefInfo().getArr(i));
					}
				}	
				
				if (blProposalBI.getBlPrpLifeTableInfo().getSize() > 0) {
					blProposalCI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().setPrpClifeTableInfoSchema(
					blPrpLifeTableInfo.Evaluate(blProposalBI.getBlPrpLifeTableInfo().getArr(0), ""));
				}
			}
			if(blProposalBI.getBLPrpTmain().getSize()>0){
	      	  blProposalBI.getBLPrpTmain().getArr(0).setCombine(true);
	        }	
	        if(blProposalBI.getBLPrpTmainSub().getSize()<=0){	
				blProposalBI.getBLPrpTmainSub().setArr(new PrpTmainSubSchema());
			}
			if(blProposalBI.getBLPrpTmainSub().getSize()>0){
				if(blProposalCI.getBLPrpTmain().getSize()>0){
					blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().setPrpCmainSchema(
					blPrpCmain.Evaluate(blProposalCI.getBLPrpTmain().getArr(0),""));	
				}
				
				for (int i = 0; i < blProposalCI.getBLPrpTitemCar().getSize(); i++) {
					if (blProposalCI.getBLPrpTitemCar().getArr(i) != null) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCitemCar().add(
						blPrpCitemCar.Evaluate(blProposalCI.getBLPrpTitemCar().getArr(i),""));
					}
				}
				
				for (int i = 0; i < blProposalCI.getBLPrpTexpense().getSize(); i++) {
					if (blProposalCI.getBLPrpTexpense().getArr(i) != null) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCexpense().add(
						blPrpCexpense.Evaluate(blProposalCI.getBLPrpTexpense().getArr(i),""));
					}
				}
				
				for (int i = 0; i < blProposalCI.getBLPrpTitemKind().getSize(); i++) {
					if (blProposalCI.getBLPrpTitemKind().getArr(i) != null) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCitemKind().add(
						blPrpCitemKind.Evaluate(blProposalCI.getBLPrpTitemKind().getArr(i),""));
					}
				}
				for (int i = 0; i < blProposalCI.getBLPrpTprofitDetail().getSize(); i++) {
					if (blProposalCI.getBLPrpTprofitDetail().getArr(i) != null) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCprofitDetail().add(
						blPrpCprofitDetail.Evaluate(blProposalCI.getBLPrpTprofitDetail().getArr(i),""));
					}
				}
				for (int i = 0; i < blProposalCI.getBLPrpTTrafficDetail().getSize(); i++) {
					if (blProposalCI.getBLPrpTTrafficDetail().getArr(i) != null) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpCtrafficDetail().add(
						blPrpCtrafficDetail.Evaluate(blProposalCI.getBLPrpTTrafficDetail().getArr(i),""));
					}
				}
				for (int i = 0; i < blProposalCI.getBLPrpIntefInfo().getSize(); i++) {
					if (blProposalCI.getBLPrpIntefInfo().getArr(i) != null) {
						blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().getPrpIntefInfo().add(
						blProposalCI.getBLPrpIntefInfo().getArr(i));
					}
				}		
				
				if (blProposalCI.getBlPrpLifeTableInfo().getSize() > 0) {
					blProposalBI.getBLPrpTmainSub().getArr(0).getPrpRelationPolicySchema().setPrpClifeTableInfoSchema(
					blPrpLifeTableInfo.Evaluate(blProposalCI.getBlPrpLifeTableInfo().getArr(0), ""));
				}
				
				
			}	
		}
	}
}
