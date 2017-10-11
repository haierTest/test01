package com.sp.interactive.interf;
import java.text.DecimalFormat;

import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.utility.UtiPower;
public class DataDealForCal {
	public void generateTaxItem(BLProposal blProposal) throws Exception{
		
		String carKindCode  =blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode();
		String seatCount    =blProposal.getBLPrpTitemCar().getArr(0).getSeatCount();
		String exhaustScale = blProposal.getBLPrpTitemCar().getArr(0).getExhaustScale();
		String tonCount     = blProposal.getBLPrpTitemCar().getArr(0).getTonCount();

		
		String taxItemCode = "";
		String taxItemName = "";
		String taxItemDetailCode = "";
		String taxItemDetailName = "";
		
		if(carKindCode == null || "".equals(carKindCode)){
			return;
		}
		else if("A".equals(carKindCode.substring(0,1)) ) {
			if((seatCount == null || "".equals(seatCount) || exhaustScale == null ||"".equals(exhaustScale)) ){
				return;
			}
			else if(Double.parseDouble(exhaustScale) <=1 && Integer.parseInt(seatCount) <= 9){
				taxItemDetailCode = "A4";
				taxItemDetailName = "1.0升（含）以下的乘用车";
			}
			else if( Double.parseDouble(exhaustScale) >1 && Double.parseDouble(exhaustScale) <=1.6 && Integer.parseInt(seatCount) <= 9){
				taxItemDetailCode = "A5";
				taxItemDetailName = "1.0升以上至1.6升(含)的乘用车";
			}
			else if( Double.parseDouble(exhaustScale) >1.6 && Double.parseDouble(exhaustScale) <=2 && Integer.parseInt(seatCount) <= 9){
				taxItemDetailCode = "A6";
				taxItemDetailName = "1.6升以上至2.0升(含)的乘用车";
			}
			else if( Double.parseDouble(exhaustScale) >2 && Double.parseDouble(exhaustScale) <=2.5 && Integer.parseInt(seatCount) <= 9){
				taxItemDetailCode = "A7";
				taxItemDetailName = "2.0升以上至2.5升(含)的乘用车";
			}
			else if( Double.parseDouble(exhaustScale) >2.5 && Double.parseDouble(exhaustScale) <=3 && Integer.parseInt(seatCount) <= 9){
				taxItemDetailCode = "A8";
				taxItemDetailName = "2.5升以上至3.0升(含)的乘用车";
			}
			else if( Double.parseDouble(exhaustScale) >3 && Double.parseDouble(exhaustScale) <=4 && Integer.parseInt(seatCount) <= 9){
				taxItemDetailCode = "A9";
				taxItemDetailName = "3.0升以上至4.0升(含)的乘用车";
			}
			else if(  Double.parseDouble(exhaustScale) >4 && Integer.parseInt(seatCount) <= 9){
				taxItemDetailCode = "A10";
				taxItemDetailName = "4.0升以上的乘用车";
			}
			else if( Integer.parseInt(seatCount) >= 20){
				taxItemDetailCode = "A3";
				taxItemDetailName = "大型汽车";
			}
			else if( Integer.parseInt(seatCount) > 9 && Integer.parseInt(seatCount) < 20){
				taxItemDetailCode = "A2";
				taxItemDetailName = "中型汽车";
			}
			taxItemCode = "A";
			taxItemName = "载客汽车";
		}
		else if("H0,TM,TP,TQ,TR,TS,TT,H3,H4,H5,H6,H7,H8".indexOf(carKindCode) > -1 || "T11" .equals(carKindCode) ||  "T12" .equals(carKindCode) ){
			taxItemCode = "H";
			taxItemName = "载货汽车";
		}
		else if("G0,G1,G2,G3".indexOf(carKindCode) > -1){
			taxItemCode = "G";
			taxItemName = "挂车";
		}
		else if( "M" .equals(carKindCode.substring(0,1)) ){
			taxItemCode = "M";
			taxItemName = "摩托车";
		}
		else if("T2,TF,TG,TH,TI,TJ".indexOf(carKindCode) > -1){
			taxItemCode = "T";
			taxItemName = "轮式专用机械车";
		}
		else if("T1,T3,T4,T5,T6,T7,T8,T9,TC,TD,TE,TK,TL,TN,TO,TU,TV,TW,TX,TY,TZ,T10".indexOf(carKindCode) > -1){
			taxItemCode = "Z";
			taxItemName = "专项作业车";
		}
		else if("H1,H11,H12".indexOf(carKindCode) > -1){
			taxItemCode = "D";
			taxItemName = "低速货车";
		}
		else if("H2,H21,H22".indexOf(carKindCode) > -1){
			taxItemCode = "S";
			taxItemName = "三轮汽车";
		}
		else if("J".equals(carKindCode.substring(0,1))){
			taxItemCode = "W";
			taxItemName = "无";
		}

		blProposal.getBLPrpTcarshipTax().getArr(0).setTaxItemCode(taxItemCode);
		blProposal.getBLPrpTcarshipTax().getArr(0).setTaxItemName(taxItemName);
		blProposal.getBLPrpTcarshipTax().getArr(0).setTaxItemDetailCode(taxItemDetailCode);
		blProposal.getBLPrpTcarshipTax().getArr(0).setTaxItemDetailName(taxItemDetailName);

		  
		  setTaxUnitCS(blProposal);

		}


		public void setTaxUnitCS(BLProposal blProposal) throws Exception {
			String taxItemCode=blProposal.getBLPrpTcarshipTax().getArr(0).getTaxItemCode();
			String strcompleteKerbMass=blProposal.getBLPrpTitemCar().getArr(0).getCompleteKerbMass();
			DecimalFormat decimalFormat =new DecimalFormat("0.0000");
			DecimalFormat decimalFormat1 =new DecimalFormat("0.0");
			UtiPower utiPower =new UtiPower();
			 String taxUnitCS = "";
		  	if(!"".equals(taxItemCode) && ("A".equals(taxItemCode) || "M".equals(taxItemCode))){
		  		taxUnitCS ="1";
		  		 
		  	}else{
		  	   if(!"".equals(strcompleteKerbMass)){
		  		 
		  		   String completeKerbMass =  decimalFormat.format(Double.parseDouble(strcompleteKerbMass)/1000);
                    if(utiPower.checkRoundTaxUnit(blProposal.getBLPrpTmain().getArr(0).getComCode())){
                    	taxUnitCS =new DecimalFormat("0.00").format(Double.parseDouble(completeKerbMass));
                    	
                    } else if(!"1".equals(utiPower.carShipLocalSwitch(blProposal.getBLPrpTmain().getArr(0).getComCode(),
                    		blProposal.getBLPrpTmain().getArr(0).getOperateDate()))||
                    		utiPower.checkNoPreciseTaxUnit(blProposal.getBLPrpTmain().getArr(0).getComCode())){
		  			    String iCompleteKerbMass = completeKerbMass.substring(completeKerbMass.indexOf(".")+1);
		  			    if(Double.parseDouble(completeKerbMass) <= 1)
		  			    	taxUnitCS = "1.0";
		  			    else if(Double.parseDouble(iCompleteKerbMass) == 0)
		  			    	taxUnitCS = decimalFormat1.format(Double.parseDouble(completeKerbMass));
		  			    else if(Double.parseDouble("0." + iCompleteKerbMass) <= 0.5 && Double.parseDouble("0." + iCompleteKerbMass) > 0)
		  			    	taxUnitCS = decimalFormat1.format(Double.parseDouble(completeKerbMass.substring(0,completeKerbMass.indexOf(".")) + ".5"));
		  			    else if(Double.parseDouble("0." + iCompleteKerbMass) > 0.5)
		  			    	taxUnitCS = decimalFormat1.format((Integer.parseInt(completeKerbMass.substring(0,completeKerbMass.indexOf("."))) + 1));
		  			}else{
		  				taxUnitCS = completeKerbMass;
		  			}
		  			
		  		}
		  	
		  	}
		  	blProposal.getBLPrpTcarshipTax().getArr(0).setTaxUnit(taxUnitCS);
		}
		public static String getClauseType(String UseNatureCode, String MainCarKindCode,
				String CarKindCode) throws Exception {
			String ClauseType = "";
			if("8A".equals(UseNatureCode) && "A".equals(MainCarKindCode)){
				ClauseType="F54";
			}
			if("8B,8C,8D,8E,8F".indexOf(UseNatureCode)>-1){
				if("A,H".indexOf(MainCarKindCode)>-1
						||("G".equals(MainCarKindCode) && "G0".equals(CarKindCode))){
					ClauseType="F55";
				}
				if(("G".equals(MainCarKindCode) && !"G0".equals(CarKindCode))
						|| "T1,T2,T3,T4".indexOf(MainCarKindCode)>-1){
					ClauseType="F57";
				}
			}
			if("9A,9B,9C,9D,9E,9F,9G".indexOf(UseNatureCode)>-1){
				if((!"9D".equals(UseNatureCode) && "A".equals(MainCarKindCode))
						||("9D".equals(UseNatureCode) && "G".equals(MainCarKindCode) && "G0".equals(CarKindCode))
						||("9D".equals(UseNatureCode) && "H".equals(MainCarKindCode))){
					ClauseType="F56";
				}
				if(("G".equals(MainCarKindCode) && !"G0".equals(CarKindCode)&&("9A".equals(UseNatureCode)||"9D".equals(UseNatureCode)))
						||("9B,9C,9E".indexOf(UseNatureCode)<0 && "T1,T2,T3,T4".indexOf(MainCarKindCode)>-1)){
					ClauseType="F57";
				}
			}
			if("M,J".indexOf(MainCarKindCode)>-1){
				ClauseType="F61";
			}
			return ClauseType;
		}

}
