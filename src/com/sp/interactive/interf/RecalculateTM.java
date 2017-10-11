package com.sp.interactive.interf;

import java.text.DecimalFormat;
import java.util.Map;

import com.sp.phonesale.schema.TargetMarketInfoSchema;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.quotation.pub.QuotationUtils;

public class RecalculateTM {
	public void recalculatePremium(BLProposal blProposal,String lowDiscount,Map paramMap) throws Exception{
		
		DecimalFormat df2 = new DecimalFormat("0.00");
		String sumDiscount = blProposal.getBLPrpTmain().getArr(0).getSumDiscount();
		
		String discount = blProposal.getBLPrpTitemKind().getArr(0).getDiscount();
		double AdjustRate = (Double.parseDouble(lowDiscount)/Double.parseDouble(discount));
		
		if(AdjustRate >= 1){
		java.math.BigDecimal b4 = new java.math.BigDecimal(AdjustRate);
		AdjustRate = b4.setScale(4, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
		double sumPremium = 0.0;
		for (int i = 0;i<blProposal.getBLPrpTitemKind().getSize();i++){
			PrpTitemKindSchema prpItemKindSchema = blProposal.getBLPrpTitemKind().getArr(i);
			double vPremium = 0;
			String BenchMarkPremium = prpItemKindSchema.getBenchMarkPremium();
			String Discount = prpItemKindSchema.getDiscount();
			
		/*	if(prpItemKindSchema.getKindCode().equals("B")||prpItemKindSchema.getKindCode().equals("B1")){
				 



				vPremium = Double.parseDouble(BenchMarkPremium) *
				Double.parseDouble(Discount) * 
				AdjustRate;
			}else{
				 




				String ShortRate = prpItemKindSchema.getShortRate();
				vPremium = Double.parseDouble(BenchMarkPremium) *
				Double.parseDouble(ShortRate) / 100 *
				Double.parseDouble(Discount) * 
				AdjustRate;

				
			
			
			
			

















			if(prpItemKindSchema.getFlag().length()>=6 &&  prpItemKindSchema.getFlag().substring(5,6).equals("2") ){
				
		      
		      if (vPremium>Double.parseDouble(BenchMarkPremium)*4) {
		        vPremium = Double.parseDouble(BenchMarkPremium)*4;
		      }
		      java.math.BigDecimal b = new java.math.BigDecimal(vPremium);  
		      vPremium = b.setScale(2, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();  
			}else{
				
				java.math.BigDecimal b = new java.math.BigDecimal(vPremium);  
				vPremium = b.setScale(2, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();  
			}
			blProposal.getBLPrpTitemKind().getArr(i).setAdjustRate(AdjustRate+"");
			System.out.println(vPremium);
			blProposal.getBLPrpTitemKind().getArr(i).setPremium((vPremium+""));
			sumPremium += vPremium;
		}
		blProposal.getBLPrpTmain().getArr(0).setSumPremium(df2.format(sumPremium)+"");
		blProposal.getBLPrpTmain().getArr(0).setAdjustRate(AdjustRate+"");
		
	    
		LifeTableInfoInteractiveTM LifeTableInfoInteractiveTM = new LifeTableInfoInteractiveTM();

		boolean isRoughQuotation = false;
		if(((String)paramMap.get(QuotationUtils.QUTATIONTYPE)).equals(QuotationUtils.ROUGHQUOTATION)){
			isRoughQuotation = true;
		}
		TargetMarketInfoSchema targetMarketInfoSchemaBI = new TargetMarketInfoSchema();
		
		
			
			
		
			targetMarketInfoSchemaBI = LifeTableInfoInteractiveTM.lifeTableInfoTM(blProposal,paramMap);
				    	
		
		paramMap.put("targetMarketInfoSchemaBI",targetMarketInfoSchemaBI);
		
	    
	}
	}
}
