package com.sp.interactive.interf;
import com.sp.indiv.ci.schema.CICarModelSchema;
import com.sp.prpall.pubfun.PurchasePriceDeal;
import com.sp.utiall.blsvr.BLPrpDcarModel;
import com.sp.utiall.schema.PrpDcarModelSchema;
import com.sp.utility.UtiPower;
import com.sp.utility.string.ChgData;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * 本地车价查询
 * @author qilin
 * */

public class CarInfoQueryNative {

	public void query(CICarModelSchema ciCarModelSchema,BLPrpDcarModel blPrpDcarModel,String comcode,Map paramMap){

		PrpDcarModelSchema prpDcarModelSchema = null;
		String strPurchasePriceNoTaxFlag = "" ;
		String strMainCarKind = ((String) paramMap.get("CarKindCode")).substring(0, 1);	
		String modelCode = ((String) paramMap.get("ModelCode")).trim();
		String strUseNature=((String) paramMap.get("UserType"));
		
		String strRiskCode="0508";
		strRiskCode = UtiPower.ChangeRiskCode0528(strRiskCode,comcode);
		
		String StrWhere = " 1=1 ";
		try{

		if("".equals(modelCode)){

         if(!ciCarModelSchema.getVenicleModel().trim().equals("")){

        	 StrWhere = StrWhere+ " and MODELNAME like  '%" + ciCarModelSchema.getVenicleModel()+ "%'";
         }
         blPrpDcarModel.query(StrWhere);

		}else{
			 if(!ciCarModelSchema.getVenicleModel().trim().equals("")){
	        	 StrWhere = StrWhere+ " and MODELNAME like  '%" + ciCarModelSchema.getVenicleModel()+ "%'";
	         }
	         if(!ciCarModelSchema.getVehicleBrand().trim().equals("")){
	        	 StrWhere = StrWhere+ " and modelCode =  '" + modelCode+ "'";
	         }
	         blPrpDcarModel.query(StrWhere);

		}
             
             for( int i = 0 ; i < blPrpDcarModel.getSize() ; i++ )
             {
             	prpDcarModelSchema = blPrpDcarModel.getArr(i);
             	 if(new UtiPower().checkNoTaxPriceByCarKind(comcode,strRiskCode,prpDcarModelSchema.getCarKind())){
             	      strPurchasePriceNoTaxFlag="1";
             	    }
             	if(comcode!=null&&comcode.length()>2&&"06".equals(comcode.substring(0,2))
             		  	  &&"轿车类".equals(prpDcarModelSchema.getCarKind())
             		  	  &&Integer.parseInt(ChgData.chgStrZero(prpDcarModelSchema.getSeatCount()))<6){
             		  
             		  		prpDcarModelSchema.setPurchasePrice(new DecimalFormat("0.00").format(Double.parseDouble(ChgData.chgStrZero(prpDcarModelSchema.getPurchasePrice()))*0.95));
             		  		prpDcarModelSchema.setPurchasePriceNotax(new DecimalFormat("0.00").format(Double.parseDouble(ChgData.chgStrZero(prpDcarModelSchema.getPurchasePriceNotax()))*0.95));
             		  	}	
             		
             		  	if(new UtiPower().checkPriceByCarKind(comcode,strRiskCode,strMainCarKind,strUseNature)){
             		  		String PurchasePrice = PurchasePriceDeal.formatDeciaml2(new DecimalFormat("0.00").format(Double.parseDouble(ChgData.chgStrZero(prpDcarModelSchema.getPurchasePrice()))*0.9),100);
             		  		String PurchasePriceNotax = PurchasePriceDeal.formatDeciaml2(new DecimalFormat("0.00").format(Double.parseDouble(ChgData.chgStrZero(prpDcarModelSchema.getPurchasePriceNotax()))*0.9),100);
             		  		prpDcarModelSchema.setPurchasePrice(PurchasePrice);
             		  		prpDcarModelSchema.setPurchasePriceNotax(PurchasePriceNotax);
             		  	} 	
             }
             
         }catch(Exception e){
             e.printStackTrace();
         }
        
		
	}
	
	
}
