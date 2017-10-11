package com.sp.interactive.interf;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import com.sp.phonesale.common.DoubleFormat;
import com.sp.platform.dto.domain.PrpDriskConfigDto;
import com.sp.platform.ui.control.action.UIPrpDriskConfigAction;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLProposalBatch;
import com.sp.prpall.blsvr.misc.BLPrpMotorcade;
import com.sp.prpall.blsvr.misc.BLProfitDetailChoose;
import com.sp.prpall.pubfun.Premium;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.pubfun.ProfitCalNew;
import com.sp.indiv.bi.blsvr.BLBICarModel;
import com.sp.indiv.bi.schema.BICarModelSchema;
import com.sp.indiv.bi.schema.VehicleModelSchema;
import com.sp.indiv.ci.blsvr.BLCICarModel;
import com.sp.indiv.ci.interf.SaleChannelHandle;
import com.sp.indiv.ci.schema.CICarModelSchema;
import com.sp.interactive.Service.QuotationZHUtils;
import com.sp.interactive.interf.DataDealForCal;
import com.sp.prpall.schema.PrpIntefInfoSchema;
import com.sp.prpall.schema.PrpTitemCarSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.ProfitDetailChooseSchema;
import com.sp.prpall.ui.UIKindInfo;
import com.sp.prpcar.schema.PrpEngageSchema;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDcarModel;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utiall.blsvr.BLPrpDdeprecateRate;
import com.sp.utiall.blsvr.BLPrpDkind;
import com.sp.utiall.blsvr.BLPrpDprofit;
import com.sp.utiall.schema.PrpDcarModelSchema;
import com.sp.utiall.schema.PrpDprofitSchema;
import com.sp.utility.UtiPower;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.Date;
import com.sp.utility.string.Str;


/**
 * 处理类
 * */

public class Datadeal {
	
	public void DataDealFirst(BLProposal blProposal,Map paramMap) throws Exception{
		
		
		
		if("".equals(blProposal.getBLPrpTitemCar().getArr(0).getTonCount().trim())){
			blProposal.getBLPrpTitemCar().getArr(0).setTonCount("0.0000");
		}
		String noLicenseFlag="";
		String licenseNo = blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo();
		
		
		if ("".equals(licenseNo.trim()) || SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+licenseNo.trim()+",") > -1){
		
    		noLicenseFlag = "1";
    	} else {
    		noLicenseFlag = "0";
    	}
		blProposal.getBLPrpTitemCar().getArr(0).setNoLicenseFlag(noLicenseFlag);
		String UseYears= new PubTools().calculateCarYears(blProposal.getBLPrpTmain().getArr(0).getComCode(),
				blProposal.getBLPrpTmain().getArr(0).getStartDate(),
				blProposal.getBLPrpTitemCar().getArr(0).getEnrollDate());
		blProposal.getBLPrpTitemCar().getArr(0).setUseYears(UseYears);
		blProposal.getBLPrpTitemCar().getArr(0).setOtherNature("1001 10");
		if("0507".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
			blProposal.getBLPrpTitemCar().getArr(0).setClauseType("F40");
			new DataDealForCal().generateTaxItem(blProposal);
			
			 String otherNature1CI = " ";
			 String otherNature2CI = " ";
			 String otherNature3CI = " ";
			 String otherNature4CI = " ";
			 String otherNature5CI = " ";
			 String otherNature6CI = " ";
			 String otherNature7CI = " ";
		     String otherNatureCI = "";
		     if("".equals((String) paramMap.get("RENEWALCIFLAG"))||((String) paramMap.get("RENEWALCIFLAG"))==null){
		    	 otherNature2CI="0";
		     }else{
			 otherNature2CI = (String) paramMap.get("RENEWALCIFLAG");
		     }
		     
		     if(blProposal.getBLPrpTmain().getArr(0).getContractNo()!=null && !"".equals(blProposal.getBLPrpTmain().getArr(0).getContractNo())){
		    	 otherNature4CI = "3";
		     }else{
		    	 otherNature4CI = "1";
		     }
		     
			 otherNatureCI = otherNature1CI+otherNature2CI+otherNature3CI+otherNature4CI+otherNature5CI+otherNature6CI+otherNature7CI;
			 blProposal.getBLPrpTitemCar().getArr(0).setOtherNature(otherNatureCI);
			
		}else{
			String ClauseType = new DataDealForCal().getClauseType(blProposal.getBLPrpTitemCar().getArr(0).getUseNatureCode(),
					blProposal.getBLPrpTitemCar().getArr(0).getMainCarKindCode(),
					blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode());
			blProposal.getBLPrpTitemCar().getArr(0).setClauseType(ClauseType);
			
			 String otherNature1BI = " ";
			 String otherNature2BI = " ";
			 String otherNature3BI = " ";
			 String otherNature4BI = " ";
			 String otherNature5BI = " ";
			 String otherNature6BI = " ";
			 String otherNature7BI = " ";
		     String otherNatureBI = "";
		     otherNature1BI = "1";

		     if("".equals((String) paramMap.get("RENEWALCIFLAG"))||((String) paramMap.get("RENEWALCIFLAG"))==null){
		    	 otherNature2BI = "0";
		     }else{
			 otherNature2BI = (String) paramMap.get("RENEWALCIFLAG");
		     }

			 otherNature3BI = "0";
			 
		     if(blProposal.getBLPrpTmain().getArr(0).getContractNo()!=null && !"".equals(blProposal.getBLPrpTmain().getArr(0).getContractNo())){
		    	 otherNature4BI = "3";
		     }else{
		    	 otherNature4BI = "1";
		     }
		     
			 otherNature6BI = "1";
			 if("".equals(((String)paramMap.get("ESPECIALLYCARTYPE")))||((String)paramMap.get("ESPECIALLYCARTYPE"))==null){
				 otherNature7BI = "0";
			 }else{
			 otherNature7BI =(String) paramMap.get("ESPECIALLYCARTYPE");
			 }

			 
			 otherNatureBI = otherNature1BI+otherNature2BI+otherNature3BI+otherNature4BI+otherNature5BI+otherNature6BI+otherNature7BI;
			 blProposal.getBLPrpTitemCar().getArr(0).setOtherNature(otherNatureBI);
			
		}
		
		
		for(int i=0;i<blProposal.getBLPrpTitemKind().getSize();i++){
			blProposal.getBLPrpTitemKind().getArr(i).setStartHour("00");
			blProposal.getBLPrpTitemKind().getArr(i).setEndHour("24");
			blProposal.getBLPrpTitemKind().getArr(i).setCurrency("CNY");
			blProposal.getBLPrpTitemKind().getArr(i).setShortRateFlag("3");
			blProposal.getBLPrpTitemKind().getArr(i).setDiscount("1");
			blProposal.getBLPrpTitemKind().getArr(i).setBenchMarkPremium("0.0");
			
			
			String OperateSite = blProposal.getBLPrpTmain().getArr(0).getOperateSite();
			blProposal.getBLPrpTmain().getArr(0).getOperateSite();
			if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSite+",")>-1){
			
			}else{
				if (!"".equals(blProposal.getBLPrpTitemKind().getArr(i).getFlag())
						&& "1".equals(blProposal.getBLPrpTitemKind().getArr(i).getFlag().substring(4, 5))) {
					blProposal.getBLPrpTitemKind().getArr(i).setFlag(" 1  1");
				} else {
					blProposal.getBLPrpTitemKind().getArr(i).setFlag(" 1  0");
				}
			}
			
			if("F".equals(blProposal.getBLPrpTitemKind().getArr(i).getKindCode().substring(0, 1))){
				blProposal.getBLPrpTitemKind().getArr(i).setKindCode("F");
			}
			if("07".equals(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2))){
				if(blProposal.getBLPrpTitemKind().getArr(i).getAmount()==null || 
						"".equals(blProposal.getBLPrpTitemKind().getArr(i).getAmount())){
					blProposal.getBLPrpTitemKind().getArr(i).setAmount("0.00");
				}
			}
		}
		
	    if("0507".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
			if("01".equals(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2))){
				blProposal.getBLPrpTcarshipTax().getArr(0).setExtendChar2(blProposal.getBLPrpTitemCar().getArr(0).getLicenseCategory());
				blProposal.getBLPrpTcarshipTax().getArr(0).setCompleteKerbMass(blProposal.getBLPrpTitemCar().getArr(0).getCompleteKerbMass());
				blProposal.getBLPrpTcarshipTax().getArr(0).setCertificateDate(blProposal.getBLPrpTitemCar().getArr(0).getEnrollDate());
				blProposal.getBLPrpTitemCarExt().getArr(0).setBrandECode(blProposal.getBLPrpTitemCar().getArr(0).getModelCode());
			}
			if("07".equals(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2))){
				blProposal.getBLPrpTcarshipTax().getArr(0).setExtendChar2(blProposal.getBLPrpTitemCar().getArr(0).getLicenseCategory());
			}
			
			blProposal.getBLPrpTitemCar().getArr(0).setRunAreaCode("11");
			
		}else if("0508".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode()) || "0528".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
			
			if("02".equals(blProposal.getBLPrpTitemCar().getArr(0).getRunAreaCode())){
				blProposal.getBLPrpTitemCar().getArr(0).setRunAreaCode("04");
			}
			
		}
	  
	    
	    
	    String riskCode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
	  
	    if("0508".equals(riskCode) || "0528".equals(riskCode)){
	    	

		    DbPool dbpool = new DbPool();
	    	try{

	    	DBManager dbManager = new DBManager();
	    	dbManager.open(SysConfig.CONST_DDCCDATASOURCE);
	        dbManager.beginTransaction();
	        dbpool.setDBManager(dbManager);
	        BLPrpDkind blPrpDkind = new BLPrpDkind();	    	
	        blPrpDkind.query("riskcode='"+riskCode+"'" );
	        for(int i=0;i<blProposal.getBLPrpTitemKind().getSize();i++){

	        String kindCode = blProposal.getBLPrpTitemKind().getArr(i).getKindCode();
	        String calculateFlag = blProposal.getBLPrpTitemKind().getArr(i).getCalculateFlag();
		        if("A,B,D3,D4,G1".indexOf(kindCode)>-1){
	
		        	blProposal.getBLPrpTitemKind().getArr(i).setCalculateFlag("Y");
		        }else{
		        	blProposal.getBLPrpTitemKind().getArr(i).setCalculateFlag("N");
		        }
	        }
			 dbpool.commitTransaction();
			 
		  }catch(Exception e){
			e.printStackTrace();
			dbpool.rollbackTransaction();
		  }finally{
			dbpool.close();
		  }
	    }
        
	    
	    for(int j=0;j<blProposal.getBLPrpTinsured().getSize();j++){
	    String insuredNature = blProposal.getBLPrpTinsured().getArr(j).getInsuredNature();

		    if("0".equals(insuredNature)){
		    	blProposal.getBLPrpTinsured().getArr(j).setInsuredNature("3");
		    }
		    if("2".equals(insuredNature)||"1".equals(insuredNature)){
		    	blProposal.getBLPrpTinsured().getArr(j).setInsuredNature("4");
		    }
	    }
	  
	    
	   
	    
	    
	  
	  
	    if("0508".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode()) || "0528".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
	    	
		    PrpTitemCarSchema prpTitemCarSchema = blProposal.getBLPrpTitemCar().getArr(0);
		    String PurchasePrice = prpTitemCarSchema.getPurchasePrice();
		    BLPrpDdeprecateRate blPrpDdeprecateRate = new BLPrpDdeprecateRate();
		    double dblPerYearRate           = 0 ; 
		    double dblPerMonthRate          = 0 ; 
		    double dblSumDepreciationRate   = 0; 
		    double actualValueCount = 0;  
		    double baseAmount = 0;        
		    double actualAmount = 0;      
		    int    intUseMonths  = 0; 
		    String comCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
		    String startDate = blProposal.getBLPrpTmain().getArr(0).getStartDate();
		    String UseYears1 = "";
		    String EnrollDate = blProposal.getBLPrpTitemCar().getArr(0).getEnrollDate();
		    String strOperateDate = blProposal.getBLPrpTmain().getArr(0).getOperateDate();
		    String strCarClauseChgDate = "2004/10/25";
		    Premium premium = new Premium();
	        String clauseTypeBI=blProposal.getBLPrpTitemCar().getArr(0).getClauseType();
		 
		    prpTitemCarSchema.setClauseType(clauseTypeBI);
		    premium.getDeprecateRateNew(blProposal);
		 
		 
		 PubTools pubTools = new PubTools();
		 UseYears1 = PubTools.calculateCarYears(comCode,startDate,EnrollDate);
		  blPrpDdeprecateRate = premium.getBLPrpDdeprecateRate();
		  dblPerYearRate  = Double.parseDouble(blPrpDdeprecateRate.getArr(0).getPerYearRate());
		  dblPerMonthRate = Double.parseDouble(blPrpDdeprecateRate.getArr(0).getPerMonthRate()); 
		
		  if(clauseTypeBI.equals("F54") || clauseTypeBI.equals("F62") || clauseTypeBI.equals("F63") ||
				
				  clauseTypeBI.equals("F55")|| clauseTypeBI.equals("F56") || clauseTypeBI.equals("F57") || clauseTypeBI.equals("F61"))
		  {
		 
		    if(EnrollDate.equals(""))
		      intUseMonths = 12 * Integer.parseInt(UseYears1);
		    else
		    {
		      EnrollDate = EnrollDate.replace('-','/');
		      Date dateEnroll = new Date(EnrollDate);
		      Date dateStart = new Date(startDate);
		      intUseMonths = pubTools.getMonthMinus(dateEnroll,0,dateStart,0);
		      if(dateStart.get(Date.DATE)-dateEnroll.get(Date.DATE)!=0)
		      {
		        intUseMonths-=1;
		      }
		      if(intUseMonths<0) intUseMonths = 0;
		    }
		    dblSumDepreciationRate = intUseMonths * dblPerMonthRate;
		  }
		  else 
		  {
		    dblSumDepreciationRate = Integer.parseInt(UseYears1) * dblPerYearRate;
		  }

		  
		  if(dblSumDepreciationRate>0.8)
		  {
		  	dblSumDepreciationRate=0.8;
		  }
		  baseAmount = Double.parseDouble(PurchasePrice);

		  
		  if(new Date(strOperateDate).toUtilDate().getTime()>=new Date(strCarClauseChgDate).toUtilDate().getTime())
		  {
		    actualAmount = baseAmount * (1 - dblSumDepreciationRate);
		  }
		  else
		  {
		    actualAmount = (baseAmount - actualValueCount) * (1 - dblSumDepreciationRate);
		    actualAmount += actualValueCount;   
		  }
		 String carActualValue =  new DecimalFormat("0.00").format(actualAmount);    
		 blProposal.getBLPrpTitemCar().getArr(0).setActualValue(carActualValue);
		    for(int i=0;i<blProposal.getBLPrpTitemKind().getSize();i++){
		    	String kindCode=blProposal.getBLPrpTitemKind().getArr(i).getKindCode();
		    	String amount = blProposal.getBLPrpTitemKind().getArr(i).getAmount();
		    	if("A".equals(kindCode)){
					if(Double.parseDouble(amount)>Double.parseDouble(PurchasePrice)){
						throw new Exception("车辆损失XXXXXXX金额(" + amount + ")不能大于基础XX金额(" + PurchasePrice+")");
					}
				}
		    	if("Z".equals(kindCode)){
					if(Double.parseDouble(amount)>Double.parseDouble(PurchasePrice)){
						throw new Exception("附加自燃损失XXXXX责任限额(" + amount + ")不能大于基础XX金额(" + PurchasePrice+")");
					}
					String useNatureCode = blProposal.getBLPrpTitemCar().getArr(0).getUseNatureCode();
					if(!"8A".equals(useNatureCode)){
						throw new Exception("自燃损失XXXXX只能是家用车XX");
					}
				}
		    	if("G1".equals(kindCode)){
		    		if(Double.parseDouble(amount)>Double.parseDouble(carActualValue)){
						throw new Exception("盗抢XXXXX的XX金额不得超过车辆实际价值"+ carActualValue +"!");
					}
		    	}
    	        
		    	if(("F54".equals(clauseTypeBI)  ||"F55".equals(clauseTypeBI)  ||"F56".equals(clauseTypeBI)  ||		    	       
		    	        "F61".equals(clauseTypeBI)  ||"F60".equals(clauseTypeBI)  ||
		    	        "F57".equals(clauseTypeBI)) &&("B".equals(kindCode)    ||"W".equals(kindCode)))
		    	    {
		    	    	if(Integer.parseInt(amount)!=50000 && Integer.parseInt(amount)!=100000 &&Integer.parseInt(amount)!=150000 &&
		    	    			Integer.parseInt(amount)!=200000 &&Integer.parseInt(amount)!=300000 &&
		    	    					Integer.parseInt(amount)!=500000 && (int)(Integer.parseInt(amount)/500000)!=Integer.parseInt(amount)/500000)
		    	       {
		    	    		throw new Exception("第三者XXXXX责任限额为5万元、10万元、15万元、20万元、30万元、50万元、100万元和100万元以上不超过1000万元的档次。选择100万元以上的限额档次，必须是50万元的整倍数");
		    	         
		    		    }
		    			else if(Double.parseDouble(amount) >= 10000000)
		    			{
		    				   throw new Exception("录入第三者责任XX的XX金额大于等于1000万，您确定要录入吗？");	
		    			}
		    	    }
		    
		    	
		    }
	    
	    }
	    
	    
	}
	
	public void DataDealSecound(BLProposal blProposal) throws Exception{
		String renewalPolicyNo = "";
		PrpIntefInfoSchema prpIntefInfoSchema=blProposal.getBLPrpIntefInfo().getArr(0);
		 if(renewalPolicyNo==null||"".equals(renewalPolicyNo)){
	        	renewalPolicyNo="新XXXXX";     
	        }
		 




		
		 
		 blProposal.getBLPrpIntefInfo().getArr(0).setPolicyNo(renewalPolicyNo);
		 blProposal.getBLPrpIntefInfo().getArr(0).getSchemaTransCode();
		
		 blProposal.getBLPrpTmain().getArr(0).setInputDate(blProposal.getBLPrpTmain().getArr(0).getOperateDate());
		 SimpleDateFormat formate= new SimpleDateFormat("HH:mm:ss");
		 String timeString=formate.format(new java.util.Date());
		 blProposal.getBLPrpTmain().getArr(0).setInputHour(timeString.substring(0, 2));
		 blProposal.getBLPrpTmain().getArr(0).setClassCode("05");
		 blProposal.getBLPrpTmain().getArr(0).setPolicySort("2");
		 blProposal.getBLPrpTmain().getArr(0).setLanguage("C");
		 blProposal.getBLPrpTmain().getArr(0).setPolicyType("99");
		 blProposal.getBLPrpTmain().getArr(0).setPureRate("0.0");
		 blProposal.getBLPrpTmain().getArr(0).setDisRate("0.0");
		 if("0507".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
		 blProposal.getBLPrpTmain().getArr(0).setSumSubPrem(blProposal.getBLPrpTitemKind().getArr(0).getPremium());
		 }
		 blProposal.getBLPrpTmain().getArr(0).setCoinsFlag("0");
		 blProposal.getBLPrpTmain().getArr(0).setReinsFlag("0");
		 blProposal.getBLPrpTmain().getArr(0).setAllinsFlag("0");
		 blProposal.getBLPrpTmain().getArr(0).setUnderWriteFlag("0");
		 blProposal.getBLPrpTmain().getArr(0).setOthFlag("000000YY000000000000");
		 blProposal.getBLPrpTmain().getArr(0).setFlag("2");
		 blProposal.getBLPrpTmain().getArr(0).setDisRate1("0.0");
		 blProposal.getBLPrpTmain().getArr(0).setSignDate(blProposal.getBLPrpTmain().getArr(0).getOperateDate());
		 blProposal.getBLPrpTmain().getArr(0).setShareHolderFlag("0");
		 blProposal.getBLPrpTmain().getArr(0).setAgriFlag("0");
		 blProposal.getBLPrpTmain().getArr(0).setGroupPurchaseFlag("0");

	}
	
	public void DatadealClauseType(Map paramMap,BLPrpDcarModel blPrpDcarModel,CICarModelSchema ciCarModelSchema){
		
		  
		  String MainCarKindCode = ((String) paramMap.get("CarKindCode")).substring(0, 1);
		  
		  String CarKindCode = (String) paramMap.get("CarKindCode");
		  
		  String UseNatureCode = (String) paramMap.get("UserType");
	  
		  /* 车辆种类和条款校验*/
		  
		  String clauseType = "";
		  if("8A".equals(UseNatureCode) &&"A".equals(MainCarKindCode)){
			  clauseType="F54";
		  } 
		  
		  if("8B".equals(UseNatureCode)
		     || "8C".equals(UseNatureCode)
		     || "8D".equals(UseNatureCode)
		     || "8E".equals(UseNatureCode)
		     || "8F".equals(UseNatureCode)){
		     
		     if(("A".equals(MainCarKindCode) ||"H".equals(MainCarKindCode))
		        || ("G".equals(MainCarKindCode) && "G0".equals(CarKindCode))){
		    	 clauseType="F55";
		       }
		       
		     
		     if(("G".equals(MainCarKindCode) &&!"G0".equals(CarKindCode))
		         || "T1".equals(MainCarKindCode)
		         || "T2".equals(MainCarKindCode)
		         || "T3".equals(MainCarKindCode)
		         || "T4".equals(MainCarKindCode) ){
		    	 clauseType="F57";
		        }
		  }
		  
		  if("9A".equals(UseNatureCode)
		     || "9B".equals(UseNatureCode)
		     || "9C".equals(UseNatureCode)
		     || "9D".equals(UseNatureCode)
		     || "9E".equals(UseNatureCode)
		     || "9F".equals(UseNatureCode)
		     || "9G".equals(UseNatureCode)){
		     
		     
		     if((!"9D".equals(UseNatureCode) && "A".equals(MainCarKindCode))
		         || ("9D".equals(UseNatureCode) &&"G".equals(MainCarKindCode) &&"G0".equals(CarKindCode))
		         || ("9D".equals(UseNatureCode) && "H".equals(MainCarKindCode))){
		    	 clauseType="F56";
		         }
		     
		     if(("G".equals(MainCarKindCode) &&  !"G0".equals(CarKindCode) && ("9A".equals(UseNatureCode) ||"9D".equals(UseNatureCode)))
		         ||(( !"9B".equals(UseNatureCode) && !"9C".equals(UseNatureCode) && ! "9E".equals(UseNatureCode)) 
		             && ("T1".equals(MainCarKindCode) ||"T2".equals(MainCarKindCode) ||"T3".equals(MainCarKindCode) ||"T4".equals(MainCarKindCode)))){
		    	 clauseType="F57";
		       }     
		     }   
              
		     if("M".equals(MainCarKindCode)||"J".equals(MainCarKindCode)){
		    	 clauseType="F61";
		     }

		     paramMap.put("ClauseType", clauseType);
	  	}
	
	    public void RealPurchasePrice(Map paramMap,PrpDcarModelSchema prpDcarModelSchema,CICarModelSchema ciCarModelSchema){
	    	
	    	try{
			     BLPrpDdeprecateRate blPrpDdeprecateRate = new BLPrpDdeprecateRate();
			  double dblPerYearRate           = 0 ; 
			  double dblPerMonthRate          = 0 ; 
			  double dblSumDepreciationRate   = 0; 
			  double actualValueCount = 0;  
			  double baseAmount = 0;        
			  double actualAmount = 0;      
			  int    intUseMonths  = 0; 
			  Map blProposalMap = new HashMap();
			  blProposalMap.put(QuotationZHUtils.BLPROPOSALBI, new BLProposal());
			  BLProposal blProposalBI = (BLProposal)blProposalMap.get(QuotationZHUtils.BLPROPOSALBI);
			  PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
			  PrpTitemCarSchema prpTitemCarSchema = new PrpTitemCarSchema();
			  prpTmainSchema.setComCode(ciCarModelSchema.getComCode());
              

			  SimpleDateFormat formatedate= new SimpleDateFormat("yyyy-MM-dd");
			  
			  String startDate = (String) paramMap.get("StartDate");
			  prpTmainSchema.setStartDate(startDate);
			  prpTitemCarSchema.setEnrollDate(ciCarModelSchema.getRegisterDate());
			  prpTmainSchema.setOperateDate(formatedate.format(new java.util.Date()));
			  prpTmainSchema.setRiskCode("0508");

			  
			  blProposalBI.getBLPrpTmain().setArr(prpTmainSchema);
			  String comCode = blProposalBI.getBLPrpTmain().getArr(0).getComCode();
			  startDate = blProposalBI.getBLPrpTmain().getArr(0).getStartDate();
			  String UseYears = "";
			 
			  String strOperateDate = blProposalBI.getBLPrpTmain().getArr(0).getOperateDate();
			  String strCarClauseChgDate = "2004/10/25";
			 Premium premium = new Premium();
		     String clauseType=(String) paramMap.get("ClauseType");

		     String CarKindCode = (String) paramMap.get("CarKindCode");
     	     String purchasePrice="";
		     DecimalFormat decimalFormat =new DecimalFormat("0");
	
			 if("07".equals(ciCarModelSchema.getComCode().substring(0, 2))){
				 
			     purchasePrice = prpDcarModelSchema.getPurchasePrice().trim();
		     }else if("08".equals(ciCarModelSchema.getComCode().substring(0, 2))||"01".equals(ciCarModelSchema.getComCode().substring(0, 2))){
				 purchasePrice = prpDcarModelSchema.getPurchasePriceNotax().trim();
		     }
			 prpTitemCarSchema.setSeatCount(prpDcarModelSchema.getSeatCount());
			 if("".equals( prpDcarModelSchema.getTonCount().trim())){
				 prpTitemCarSchema.setTonCount("0");
						   
			 }else{
				 prpTitemCarSchema.setTonCount(decimalFormat.format(Double.parseDouble(prpDcarModelSchema.getTonCount().trim())));
			 }
			     prpTitemCarSchema.setClauseType(clauseType);
				 prpTitemCarSchema.setUseNatureCode((String)paramMap.get("UserType"));
				 prpTitemCarSchema.setCarKindCode(CarKindCode);
				  
			     blProposalBI.getBLPrpTitemCar().setArr(prpTitemCarSchema);
				 premium.getDeprecateRateNew(blProposalBI);
						 
			 
		     String EnrollDate = blProposalBI.getBLPrpTitemCar().getArr(0).getEnrollDate();
			 PubTools pubTools = new PubTools();
			 UseYears = PubTools.calculateCarYears(comCode,startDate,EnrollDate);
			  blPrpDdeprecateRate = premium.getBLPrpDdeprecateRate();
			  dblPerYearRate  = Double.parseDouble(blPrpDdeprecateRate.getArr(0).getPerYearRate());
			  dblPerMonthRate = Double.parseDouble(blPrpDdeprecateRate.getArr(0).getPerMonthRate()); 
			 
			  if(clauseType.equals("F54") || clauseType.equals("F55")|| clauseType.equals("F56") || clauseType.equals("F57") || clauseType.equals("F61"))
			  {
			 
			    if(EnrollDate.equals(""))
			      intUseMonths = 12 * Integer.parseInt(UseYears);
			    else
			    {
			      EnrollDate = EnrollDate.replace('-','/');
			      Date dateEnroll = new Date(EnrollDate);
			      Date dateStart = new Date(startDate);
			      intUseMonths = pubTools.getMonthMinus(dateEnroll,0,dateStart,0);
			      if(dateStart.get(Date.DATE)-dateEnroll.get(Date.DATE)!=0)
			      {
			        intUseMonths-=1;
			      }
			      if(intUseMonths<0) intUseMonths = 0;
			    }
			    dblSumDepreciationRate = intUseMonths * dblPerMonthRate;
			  }
			  else 
			  {
			    dblSumDepreciationRate = Integer.parseInt(UseYears) * dblPerYearRate;
			  }

			  
			  if(dblSumDepreciationRate>0.8)
			  {
			  	dblSumDepreciationRate=0.8;
			  }
			  baseAmount = Double.parseDouble(purchasePrice);

			  
			  if(new Date(strOperateDate).toUtilDate().getTime()>=new Date(strCarClauseChgDate).toUtilDate().getTime())
			  {
			    actualAmount = baseAmount * (1 - dblSumDepreciationRate);
			  }
			  else
			  {
			    actualAmount = (baseAmount - actualValueCount) * (1 - dblSumDepreciationRate);
			    actualAmount += actualValueCount;   
			  }
			 String carActualValue =  new DecimalFormat("0.00").format(actualAmount);    
			 paramMap.put("ActualValue", carActualValue);
			 blProposalBI.getBLPrpTitemCar().getArr(0).setActualValue(carActualValue);
	
			
	      }catch(Exception e){
	    	  e.printStackTrace();
	    	 
	      }
	    }
	    /**
	     * 根据条款来判断XXXXX别
	     * */
	    public void queryKindMainSub(BLProposal blProposal,Map paramMap) throws Exception{
	    	try {
				String clauseType = blProposal.getBLPrpTitemCar().getArr(0).getClauseType();
				UIKindInfo[] arrKindInfo;
				  Vector vector = new Vector();
				  UIPrpDclauseKindInterActive uiPrpDclauseKindInterActive = new UIPrpDclauseKindInterActive();
				  
				  uiPrpDclauseKindInterActive.init(true);
				
				arrKindInfo = uiPrpDclauseKindInterActive.queryKindMain(clauseType);
			
				String arrKindCode1 ="";
				for(int i=0;i<arrKindInfo.length;i++){
					String arrKindCode = arrKindInfo[i].getKindCode();
					arrKindCode1 =arrKindCode1+arrKindCode+",";
	             
				}
								
			    String kindMainCode1 = "";
			    String[] kindMainCode2 = null;
			    String kindMainCode5 = "";
				for(int j=0;j<blProposal.getBLPrpTitemKind().getSize();j++){
				    String calculateFlag = blProposal.getBLPrpTitemKind().getArr(j).getCalculateFlag();
				
					String kindMainCode = blProposal.getBLPrpTitemKind().getArr(j).getKindCode();

					if("Y".equals(calculateFlag)&&arrKindCode1.indexOf(kindMainCode)>-1){
						kindMainCode1 = kindMainCode1+kindMainCode+",";
						
				    }
					else if("Y".equals(calculateFlag)&&arrKindCode1.indexOf(kindMainCode)==-1){
						kindMainCode5 = kindMainCode5+kindMainCode+",";
					}


				}
				if(kindMainCode1.endsWith(",")){
					kindMainCode1 = kindMainCode1.substring(0, kindMainCode1.length()-1);
				}

				kindMainCode2 = kindMainCode1.split(",");
				if(!"".equals(kindMainCode5)){
					throw new Exception("该条款下没有主XXXXX"+kindMainCode5);
				}
				
				
					 arrKindInfo = uiPrpDclauseKindInterActive.queryKindSub(clauseType,kindMainCode2);
                   
                     String arrKindCode2="";
					 for(int i=0;i<arrKindInfo.length;i++){
							String arrKindCode = arrKindInfo[i].getKindCode();
							arrKindCode2 = arrKindCode2+arrKindCode+",";
						

					}
					 String arrKindCode3 = "";
					 String arrKindCode4 = "";
					 String kindSubNameArray = "";
					for(int j=0;j<blProposal.getBLPrpTitemKind().getSize();j++){
					    String calculateSubFlag = blProposal.getBLPrpTitemKind().getArr(j).getCalculateFlag();
						String kindSubCode = blProposal.getBLPrpTitemKind().getArr(j).getKindCode();
						arrKindCode3 = arrKindCode3+kindSubCode+",";

						if("N".equals(calculateSubFlag)&&arrKindCode2.indexOf(kindSubCode)>-1){
				
					    }
						else if("N".equals(calculateSubFlag)&&arrKindCode2.indexOf(kindSubCode)==-1){
							BLPrpDcode blPrpDcode = new BLPrpDcode();
							
							String iWherePart = "codetype = 'KindCode' and codecode = '"+kindSubCode+"'";
							blPrpDcode.query(iWherePart);
							String kindSubName = blPrpDcode.getArr(0).getCodeCName();
							kindSubNameArray = kindSubNameArray+"'"+kindSubName+"'"+",";
							arrKindCode4=arrKindCode4+kindSubCode+",";
					    }
						
					}
					 if(arrKindCode4.endsWith(",")){
                    	 arrKindCode4= arrKindCode4.substring(0, arrKindCode4.length()-1);
                     }
                     if(arrKindCode3.endsWith(",")){
                    	 arrKindCode3 = arrKindCode3.substring(0, arrKindCode3.length()-1);
                     }
                     if(kindSubNameArray.endsWith(",")){
                    	 kindSubNameArray = kindSubNameArray.substring(0, kindSubNameArray.length()-1);
                     }
					if(!"".equals(arrKindCode4)){
                      
						if("F,L,Z,X1,M".indexOf(arrKindCode4)>-1&&arrKindCode3.indexOf("A")==-1){
							throw new Exception("没有选择附加XXXXX"+kindSubNameArray+"对应的主XXXXX:机动车辆损失XXXXX");
						}
						throw new Exception("该条款下没有附加XXXXX："+kindSubNameArray);
					}
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
				throw e;
			}
	    }
	    
/**
 * 增加特别约定
 * @author qilin
 *
 * */	    
	    public void SpecialAgreement(BLProposal blProposal,Map paramMap,String bizNo) throws Exception{
	    	PrpEngageSchema prpEngageSchema = null; 
	    	int j=0;
	    	
	    	if(!"01".equals(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2))){
	    		
	    		if("0508".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())
	    				|| "0528".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
	    			
	    			
			    	
	    			if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo()+",") > -1){
	    			
			    		  j++;
			    		
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T0026");
				    	  prpEngageSchema.setClauses("无正式号牌车辆XX盗抢XXXXX特别约定");
				    	  prpEngageSchema.setTitleFlag("0");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
		
				    	
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T0026");
				    	  prpEngageSchema.setClauses("盗抢XXXXX自领取正式牌照并到本公司办理牌照批改之日起生效，XX止期不变；");
				    	  prpEngageSchema.setTitleFlag("1");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
			    	}  
	    		}
	    	}
	    	
	    	if("01".equals(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2))){
		    	
		    	
	    		
	    		if("0508".equals(blProposal.getBLPrpTitemCar().getArr(0).getRiskCode())
	    				|| "0528".equals(blProposal.getBLPrpTitemCar().getArr(0).getRiskCode())){
	    			
	    			
			    	
	    			if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo()+",") > -1){
	    			
			    		j++;
			    		
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T0026");
				    	  prpEngageSchema.setClauses("无正式号牌车辆XX盗抢XXXXX特别约定");
				    	  prpEngageSchema.setTitleFlag("0");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
	
				    	
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T0026");
				    	  prpEngageSchema.setClauses("盗抢XXXXX自领取正式牌照并到本公司办理牌照批改之日起生效，XX止期不变；");
				    	  prpEngageSchema.setTitleFlag("1");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
			    	}
	    	    }
		    	
		    	String insuredName="";
		    	for(int i=0;i<blProposal.getBLPrpTinsured().getSize();i++){
		    		if("1".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredFlag())){
		    			insuredName=blProposal.getBLPrpTinsured().getArr(i).getInsuredName();

		    		}
		    	}
		    	String carOwner=blProposal.getBLPrpTitemCar().getArr(0).getCarOwner();
		    	if(!insuredName.equals(carOwner)){
		    		j++;
		    		
			    	  prpEngageSchema = new PrpEngageSchema();
			    	  prpEngageSchema.setProposalNo(bizNo);
			    	  prpEngageSchema.setSerialNo(""+j);
			    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
			    	  prpEngageSchema.setClauseCode("T0028");
			    	  prpEngageSchema.setClauses("车主约定");
			    	  prpEngageSchema.setTitleFlag("0");
			    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);

			    	
			    	  prpEngageSchema = new PrpEngageSchema();
			    	  prpEngageSchema = new PrpEngageSchema();
			    	  prpEngageSchema.setProposalNo(bizNo);
			    	  prpEngageSchema.setSerialNo(""+j);
			    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
			    	  prpEngageSchema.setClauseCode("T0028");
			    	  prpEngageSchema.setClauses("被XX人与行驶证车主不符：被XX人为"+insuredName+"，行驶证车主为"+carOwner+"。");
			    	  prpEngageSchema.setTitleFlag("1");
			    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
		    	}
		    	
		    	for(int i=0;i<blProposal.getBLPrpTitemKind().getSize();i++){
		    		String kindCode = blProposal.getBLPrpTitemKind().getArr(i).getKindCode();
		    		if("Q3".equals(kindCode)){
		    			String model = blProposal.getBLPrpTitemKind().getArr(i).getModel();
		    			j++;
			    		
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T0059");
				    	  prpEngageSchema.setClauses("汽车专修厂特别约定");
				    	  prpEngageSchema.setTitleFlag("0");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);

				    	
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T0059");
				    	  prpEngageSchema.setClauses("车损XXXXX及车身划痕XXXXX选择汽车专修厂："+model);
				    	  prpEngageSchema.setTitleFlag("1");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
		    		}
		    	}
		    	
		    	
		    	String seatCount = blProposal.getBLPrpTitemCar().getArr(0).getSeatCount();
		    	String useNatureCode = blProposal.getBLPrpTitemCar().getArr(0).getUseNatureCode();
		    	if(9 <= Integer.parseInt(seatCount)&&Integer.parseInt(seatCount) <= 19&&"8A".equals(useNatureCode)||
		    			9 <= Integer.parseInt(seatCount)&&"8B".equals(useNatureCode)){
		    		j++;
		    		
			    	  prpEngageSchema = new PrpEngageSchema();
			    	  prpEngageSchema.setProposalNo(bizNo);
			    	  prpEngageSchema.setSerialNo(""+j);
			    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
			    	  prpEngageSchema.setClauseCode("T0064");
			    	  prpEngageSchema.setClauses("非营业车辆特别约定");
			    	  prpEngageSchema.setTitleFlag("0");
			    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);

			    	
			    	  prpEngageSchema = new PrpEngageSchema();
			    	  prpEngageSchema = new PrpEngageSchema();
			    	  prpEngageSchema.setProposalNo(bizNo);
			    	  prpEngageSchema.setSerialNo(""+j);
			    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
			    	  prpEngageSchema.setClauseCode("T0064");
			    	  prpEngageSchema.setClauses("非营业车辆如从事营业性运输，发生事故，本公司不负责赔偿。");
			    	  prpEngageSchema.setTitleFlag("1");
			    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
		    		
		    	}

		    	
		    	
		    	if("0508".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())
		    			|| "0528".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
		    		
		    		for(int i=0;i<blProposal.getBLPrpTitemKind().getSize();i++){
		    			String kindCode=blProposal.getBLPrpTitemKind().getArr(i).getKindCode();
		    			String modeCode=blProposal.getBLPrpTitemKind().getArr(i).getModeCode();
		    			String modeName="";
		    			BLPrpDcode blPrpDcode = new BLPrpDcode();
		    			String iWherePart = "codetype = 'GlassType'";
		    			blPrpDcode.query(iWherePart);
		    			String codeCode = "";
		    			String codeCname ="";
		    			for(int n = 0;n<blPrpDcode.getSize();n++){

		    				codeCode = blPrpDcode.getArr(n).getCodeCode();
			    			codeCname = blPrpDcode.getArr(n).getCodeCName();
			    			if(modeCode.equals(codeCode)){
			    				modeName = codeCname;
			    			}
			    				    
		    			}
		    			if("F".equals(kindCode)){
		    				j++;
		    				
					    	  prpEngageSchema = new PrpEngageSchema();
					    	  prpEngageSchema.setProposalNo(bizNo);
					    	  prpEngageSchema.setSerialNo(""+j);
					    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
					    	  prpEngageSchema.setClauseCode("T0068");
					    	  prpEngageSchema.setClauses("玻璃特别约定");
					    	  prpEngageSchema.setTitleFlag("0");
					    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);

					    	
					    	  prpEngageSchema = new PrpEngageSchema();
					    	  prpEngageSchema = new PrpEngageSchema();
					    	  prpEngageSchema.setProposalNo(bizNo);
					    	  prpEngageSchema.setSerialNo(""+j);
					    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
					    	  prpEngageSchema.setClauseCode("T0068");
					    	  prpEngageSchema.setClauses("本车按"+modeName+"XX，出XXXXX时按"+modeName+"赔付。");
					    	  prpEngageSchema.setTitleFlag("1");
					    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
		    			}
		    		}
		    	}
		    	
		    	
		    	
		    	
		    	
		    	
		    	String brandName = blProposal.getBLPrpTitemCar().getArr(0).getBrandName();
		    	String carUsage = blProposal.getBLPrpTitemCar().getArr(0).getCarUsage();
		    	if("0508".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())&&
		    			"A".equals(blProposal.getBLPrpTitemCar().getArr(0).getCountryNature())){
			    	if(!brandName.equals(carUsage)){
			    		j++;
			    		
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T9999");
				    	  prpEngageSchema.setClauses("其他");
				    	  prpEngageSchema.setTitleFlag("0");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);

				    	
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T9999");
				    	  prpEngageSchema.setClauses("行驶本厂牌型号为："+brandName);
				    	  prpEngageSchema.setTitleFlag("1");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
			    	}
		    	}
		    	
	    	}
	    	
	    	if("07".equals(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2))){
	    	
	    	String kindCode="";
	    	String amount="";
	    	String purchasePrice = blProposal.getBLPrpTitemCar().getArr(0).getPurchasePrice();
	    	for(int i=0;i<blProposal.getBLPrpTitemKind().getSize();i++){
	    	    kindCode=blProposal.getBLPrpTitemKind().getArr(i).getKindCode();
	    	    if("A".equals(kindCode)){
		    		amount  = blProposal.getBLPrpTitemKind().getArr(i).getAmount();
		    		if(Double.parseDouble(amount)<Double.parseDouble(purchasePrice)){
			    		j++;
			    		
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T0066");
				    	  prpEngageSchema.setClauses("实际价值特别约定");
				    	  prpEngageSchema.setTitleFlag("0");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);

				    	
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T0066");
				    	  prpEngageSchema.setClauses("车损XXXXX为不足额XX，出XXXXX时按比例赔偿。");
				    	  prpEngageSchema.setTitleFlag("1");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
			    	}
		    	}
	    	}
	    	
		    	if("0508".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
			    	if(blProposal.getBLPrpTcarDriver().getSize()>0){
			    		j++;
			    		
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T0087");
				    	  prpEngageSchema.setClauses("指定驾驶人特别约定");
				    	  prpEngageSchema.setTitleFlag("0");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
	
				    	
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T0087");
				    	  prpEngageSchema.setClauses("XX时指定驾驶人XX事故发生时为非指定驾驶人使用被XX机动车的，盗抢XXXXX增加免赔率5%，其它XXXXX别增加免赔率10%。");
				    	  prpEngageSchema.setTitleFlag("1");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
			    	}
	    	   }
	    	
		    	if("0508".equals(blProposal.getBLPrpTitemCar().getArr(0).getRiskCode())){
		    		if(!"04".equals(blProposal.getBLPrpTitemCar().getArr(0).getRunAreaCode())){
		    			j++;
			    		
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T0088");
				    	  prpEngageSchema.setClauses("约定行驶区域特别约定");
				    	  prpEngageSchema.setTitleFlag("0");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
	
				    	
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T0088");
				    	  prpEngageSchema.setClauses("XX时约定行驶区域，XX事故发生在约定行驶区域以外的，增加免赔率10%。");
				    	  prpEngageSchema.setTitleFlag("1");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
			    	}
		    		
		    	}
	    	
	    	}
	    	
	    	
	    	if("08".equals(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2))){
	    		
	    		String useYears = blProposal.getBLPrpTitemCar().getArr(0).getUseYears();
	    		for(int i=0;i<blProposal.getBLPrpTitemKind().getSize();i++){
	    			String kindCode = blProposal.getBLPrpTitemKind().getArr(i).getKindCode();
	    			if("A".equals(kindCode)&&Integer.parseInt(useYears)>10){
	    				j++;
	    				
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T9999");
				    	  prpEngageSchema.setClauses("其他");
				    	  prpEngageSchema.setTitleFlag("0");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);

				    	
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema = new PrpEngageSchema();
				    	  prpEngageSchema.setProposalNo(bizNo);
				    	  prpEngageSchema.setSerialNo(""+j);
				    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
				    	  prpEngageSchema.setClauseCode("T9999");
				    	  prpEngageSchema.setClauses("车辆发生XX事故，车辆的维修费用或损失超过车辆实际价值（市场整车价格）的70%，XX人按车辆实际价值的70%赔偿损失。");
				    	  prpEngageSchema.setTitleFlag("1");
				    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
	    			}
	    		}
	    		
		    	
	    		
		    	
    			if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo()+",") > -1){
    			
	    			j++;
	    			
			    	  prpEngageSchema = new PrpEngageSchema();
			    	  prpEngageSchema.setProposalNo(bizNo);
			    	  prpEngageSchema.setSerialNo(""+j);
			    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
			    	  prpEngageSchema.setClauseCode("T9999");
			    	  prpEngageSchema.setClauses("其他");
			    	  prpEngageSchema.setTitleFlag("0");
			    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);

			    	
			    	  prpEngageSchema = new PrpEngageSchema();
			    	  prpEngageSchema = new PrpEngageSchema();
			    	  prpEngageSchema.setProposalNo(bizNo);
			    	  prpEngageSchema.setSerialNo(""+j);
			    	  prpEngageSchema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
			    	  prpEngageSchema.setClauseCode("T9999");
			    	  prpEngageSchema.setClauses("此车领取正式牌照后请及时到我司办理车牌号码变更。");
			    	  prpEngageSchema.setTitleFlag("1");
			    	  blProposal.getBLPrpTengage().setArr(prpEngageSchema);
	    		}

	    	}
	    	
	    	
	    }
	

    public void DataDealForMotor(BLProposal blProposal) throws Exception{
    	  
    	  PrpTmainSchema prpTmainSchema = blProposal.getBLPrpTmain().getArr(0);
    	  String strContractNo = prpTmainSchema.getContractNo();
    	  char   strOption     = 'P';
    	  String strUserCode   = prpTmainSchema.getOperatorCode();
    	  String strRiskCode   = prpTmainSchema.getRiskCode();
    	  String strComCode    = prpTmainSchema.getComCode();
    	  
    	  
    	  int index1 = 0;
    	  BLProposalBatch blProposalBatch = new BLProposalBatch();
    	  BLPrpMotorcade blPrpMotorcade = new BLPrpMotorcade();
    	  BLProfitDetailChoose blProfitDetailChoose = new BLProfitDetailChoose();
    	  ProfitDetailChooseSchema profitDetailChooseSchema = null;
    	  PrpDprofitSchema prpDprofitSchema = null;
    	  String strMessage = "XXXXX存成功！";
    	  String strResult =""; 
    	  blPrpMotorcade.query("ContractNo='"+ strContractNo +"'",0);
    	  String strMinusFlag  = blPrpMotorcade.getArr(0).getMinusFlag();

    	  BLPrpDprofit   blPrpDprofit   = new BLPrpDprofit();
    	    blPrpDprofit = new ProfitCalNew().getProfitRateForMuti(Integer.parseInt(blPrpMotorcade.getArr(0).getCarCount()),
    	    		Double.parseDouble(prpTmainSchema.getSumPremium()),"",strMinusFlag,strRiskCode);

    	    
    	    for(int index=0;index<blPrpDprofit.getSize();index++)
    	    {
    	    	prpDprofitSchema = blPrpDprofit.getArr(index);
    	    	BLPrpDcode blPrpDcode = new BLPrpDcode();
    	    	if(SysConfig.getProperty("InteractiveMotorProfit").indexOf(","+prpDprofitSchema.getFactorCode()+",")>-1)
    	    	{
    	    		String InteractiveMotorProfit = blPrpDcode.translateCode("MotorProfit",strContractNo, false);
    	    		String minProfitRate = new DecimalFormat("0.0000").format(Double.parseDouble(prpDprofitSchema.getLowerRate())/100);
    	    		String maxProfitRate = new DecimalFormat("0.0000").format(Double.parseDouble(prpDprofitSchema.getUpperRate())/100);

    	    profitDetailChooseSchema = new ProfitDetailChooseSchema();
    	    blProfitDetailChoose.setArr(profitDetailChooseSchema);
    	    profitDetailChooseSchema.setChooseFlag      ( "0" );
    	    profitDetailChooseSchema.setSerialNo        ( "1" );
    	    profitDetailChooseSchema.setMinProfitRate   ( ""+Double.parseDouble(ChgData.chgStrZero(minProfitRate)) );
    	    profitDetailChooseSchema.setMaxProfitRate   ( ""+Double.parseDouble(ChgData.chgStrZero(maxProfitRate)) );
    	    profitDetailChooseSchema.setProfitPeriod    ( ""+Integer.parseInt(ChgData.chgStrZero(prpDprofitSchema.getRatePeriod())) );
    	    profitDetailChooseSchema.setProfitCode      ( prpDprofitSchema.getFactorCode() );
    	    profitDetailChooseSchema.setProfitRate      ( InteractiveMotorProfit );
    	    profitDetailChooseSchema.setCondition       ( Str.encode(prpDprofitSchema.getRateDesc()) );
    	    	}
    	  }
    	  
    	    double BenchMarkPremium = 0;
    	    double Premium = 0;
    	    for(int i = 0;i<blProposal.getBLPrpTitemKind().getSize();i++){
    	    	BenchMarkPremium += Double.parseDouble(blProposal.getBLPrpTitemKind().getArr(i).getBenchMarkPremium());
    	    	Premium += Double.parseDouble(blProposal.getBLPrpTitemKind().getArr(i).getPremium());
    	    }
    	  prpTmainSchema.setSumDiscount( ""+(BenchMarkPremium - Premium));
    	  prpTmainSchema.setSumPremium ( ""+Premium );
    	    
    	  
    	  try
    	  {
    		  strResult = blProposalBatch.settle(strOption,strUserCode,strRiskCode,blProposal.getBLPrpTmain(),blProfitDetailChoose,strMinusFlag);
    	  }
    	  catch(Exception e)
    	  {
    	    e.printStackTrace();
    	    strMessage = "XXXXX存失败！";
    	  }
    	  if(!"XXXXX存成功！".equals(strMessage)){
    		  throw new Exception("团车给优惠失败："+strMessage);
    	  }
    }
 /**
* 增加特别约定
* @author zhaoyingchao-ghq
* @param XX单大对象blProposal
* @description  车价查询方法
* @date 2014-03-27
* */	
	    public BLPrpDcarModel CarInfoQuery(BLProposal blProposal,Map paramMap) throws Exception{
	    	PrpTitemCarSchema prpTitemCarSchema = new PrpTitemCarSchema();
	    	prpTitemCarSchema = blProposal.getBLPrpTitemCar().getArr(0);
			BLCICarModel blCICarModel = new BLCICarModel();
			BLBICarModel blBICarModel = new BLBICarModel();
			UtiPower utiPower = new UtiPower();
			BLPrpDcarModel blPrpDcarModel = new BLPrpDcarModel();
			String comcode=blProposal.getBLPrpTmain().getArr(0).getComCode();
			String riskCode=blProposal.getBLPrpTmain().getArr(0).getRiskCode();
			String businessNature = blProposal.getBLPrpTmain().getArr(0).getBusinessNature();
			String AgentCode = blProposal.getBLPrpTmain().getArr(0).getAgentCode();
			try{
				if("01".equals(comcode.substring(0, 2))){
		        	
					CICarModelSchema ciCarModelSchema = new CICarModelSchema();
					processCICarModelSchema(blProposal,ciCarModelSchema,paramMap);
					DatadealClauseType(paramMap, blPrpDcarModel, ciCarModelSchema);
			        blCICarModel.query(ciCarModelSchema,comcode);
			        paramMap.put("SearchSequenceNo", ciCarModelSchema.getSearchSequenceNo());
			        blPrpDcarModel = blCICarModel.generateBLPrpDcarModel(ciCarModelSchema);
		        }else if("07".equals(comcode.substring(0, 2))){
		        	
		        	CICarModelSchema ciCarModelSchema = new CICarModelSchema();
		        	processCICarModelSchema(blProposal,ciCarModelSchema,paramMap);
					DatadealClauseType(paramMap, blPrpDcarModel, ciCarModelSchema);
		        	String strSalesChannelCode = new SaleChannelHandle().returnSaleChannelCode(businessNature,comcode,AgentCode);
		            ciCarModelSchema.setSalesChannelCode(strSalesChannelCode);
		            blCICarModel.query(ciCarModelSchema,comcode);
		            paramMap.put("SearchSequenceNo", ciCarModelSchema.getSearchSequenceNo());
		            blPrpDcarModel = blCICarModel.generateBLPrpDcarModel(ciCarModelSchema);
		        }else if(utiPower.checkBICarQuery(riskCode, comcode)){
		        	
		        	BICarModelSchema biCarModelSchema = new BICarModelSchema();
		        	VehicleModelSchema vehicleModelSchema = new VehicleModelSchema();
		        	processBICarModelSchema(blProposal,biCarModelSchema,paramMap);
		        	blBICarModel.query(biCarModelSchema,null,vehicleModelSchema,comcode);
		        	blPrpDcarModel = blBICarModel.generateBLPrpDcarModel(vehicleModelSchema);
		        }else{
		        	
		        	CICarModelSchema ciCarModelSchema = new CICarModelSchema();
		        	CarInfoQueryNative carInfoQueryNative = new CarInfoQueryNative();
		        	processCICarModelSchema(blProposal,ciCarModelSchema,paramMap);
		        	carInfoQueryNative.query(ciCarModelSchema,blPrpDcarModel,comcode,paramMap);
		        	String strPurchasePriceNoTaxFlag = "";
	                if(!"".equals(riskCode)&&!"".equals(comcode)){
					    PrpDriskConfigDto prpDriskConfigDto = new UIPrpDriskConfigAction().queryRiskConfig(comcode,riskCode,"PURCHASEPRICE_NOTAX_SWITCH");
	                    if(prpDriskConfigDto!=null){
	                    strPurchasePriceNoTaxFlag = prpDriskConfigDto.getConfigValue();
	                    }
	                }
	                if("1".equals(strPurchasePriceNoTaxFlag)){
	                	for(int i=0; i<blPrpDcarModel.getSize();i++){
	                		blPrpDcarModel.getArr(i).setPurchasePrice(blPrpDcarModel.getArr(i).getPurchasePriceNotax());
	                	}
	                }
		        }
				return blPrpDcarModel;
			}catch(Exception e){
	        	e.printStackTrace();
	        	throw e;
	        }
	    }
/**
* @author zhaoyingchao-ghq
* @param CICarModelSchema
* @description  封装车价查询条件_XXXXX、上海
* @date 2014-03-27
* */
	    protected void processCICarModelSchema(BLProposal blProposal,CICarModelSchema ciCarModelSchema,Map paramMap)throws Exception {
	    	PrpTitemCarSchema prpTitemCarSchema = new PrpTitemCarSchema();
	    	prpTitemCarSchema = blProposal.getBLPrpTitemCar().getArr(0);
			BLPrpDcarModel blPrpDcarModel = new BLPrpDcarModel();
			BLCICarModel blCICarModel = new BLCICarModel();
			BLBICarModel blBICarModel = new BLBICarModel();
			UtiPower utiPower = new UtiPower();
			String comcode=blProposal.getBLPrpTmain().getArr(0).getComCode();
			String riskCode=blProposal.getBLPrpTmain().getArr(0).getRiskCode();
			String licenseNo=prpTitemCarSchema.getLicenseNo();
			if("".equals(licenseNo.trim())){
				throw new Exception("车牌号不能为空，如果是未上牌车，请将车牌号写为“新车”！");
			}
			
			
			if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+licenseNo+",") > -1){
			
				licenseNo="";
			}
			String modelCode = prpTitemCarSchema.getModelCode();
			String brandName=prpTitemCarSchema.getBrandName();
			String carKindCode=prpTitemCarSchema.getCarKindCode();
			String enginNo=prpTitemCarSchema.getEngineNo();
			String enrollDate=prpTitemCarSchema.getEnrollDate();
			String newVehicleFlag=prpTitemCarSchema.getNewCarFlag();
			String ecdemicVehicleFlag=prpTitemCarSchema.getOutLandCarFlag();
			String licenseType=prpTitemCarSchema.getLicenseKindCode();
			String frameNo=prpTitemCarSchema.getFrameNo();
			String seatCount=prpTitemCarSchema.getSeatCount();
			String vehicleTonnage=prpTitemCarSchema.getTonCount();
			String vehicleStyleCode=prpTitemCarSchema.getCarKindCode();
	        String userType=prpTitemCarSchema.getUseNatureCode();
			String companyName=prpTitemCarSchema.getMadeFactory();
			ciCarModelSchema.setCarMark(licenseNo);
			ciCarModelSchema.setVenicleModel(brandName);
			paramMap.put("ModelCode", modelCode);
			paramMap.put("CarKindCode", carKindCode);
			paramMap.put("UserType", userType);
			ciCarModelSchema.setEngineNo(enginNo);
			ciCarModelSchema.setRegisterDate(enrollDate);
			ciCarModelSchema.setNewVehicleFlag(newVehicleFlag);
			ciCarModelSchema.setEcdemicVehicleFlag(ecdemicVehicleFlag);
			ciCarModelSchema.setVehicleType(licenseType);
			ciCarModelSchema.setRackNo(frameNo);
			ciCarModelSchema.setVehicleBrand(brandName);
			ciCarModelSchema.setVehicleStyleDesc(vehicleStyleCode);
			ciCarModelSchema.setMadeFactory(companyName);
			ciCarModelSchema.setLimitLoadPerson(seatCount);
			ciCarModelSchema.setLimitLoad(vehicleTonnage);
	    }
/**
* @author zhaoyingchao-ghq
* @param BICarModelSchema
* @description  封装车价查询条件_中科软平台
* @date 2014-03-27
* */
	    protected void processBICarModelSchema(BLProposal blProposal,BICarModelSchema biCarModelSchema,Map paramMap)throws Exception {
	    	PrpTitemCarSchema prpTitemCarSchema = new PrpTitemCarSchema();
	    	prpTitemCarSchema = blProposal.getBLPrpTitemCar().getArr(0);
			BLPrpDcarModel blPrpDcarModel = new BLPrpDcarModel();
			BLCICarModel blCICarModel = new BLCICarModel();
			BLBICarModel blBICarModel = new BLBICarModel();
			UtiPower utiPower = new UtiPower();
			String ComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
			String RiskCode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
			String LicenseNo=prpTitemCarSchema.getLicenseNo();
	        String LicenseType = prpTitemCarSchema.getLicenseColorCode();
	        String EngineNo = prpTitemCarSchema.getEngineNo();
	        String FrameNo = prpTitemCarSchema.getFrameNo();
	        String MakeDate = prpTitemCarSchema.getMakeDate();
	        String NewCarFlag = prpTitemCarSchema.getNewCarFlag();
	        String Completekerbmass  = prpTitemCarSchema.getCompleteKerbMass();
	        String EnrollDate = prpTitemCarSchema.getEnrollDate();
	        String Toncount   = prpTitemCarSchema.getTonCount();
	        String Seatcount   = prpTitemCarSchema.getSeatCount();
	        String Exhaustscale   = prpTitemCarSchema.getExhaustScale();
	        String MadeFactory = prpTitemCarSchema.getMadeFactory();
	        String ModelName = prpTitemCarSchema.getBrandName();
	        String ModelCode = prpTitemCarSchema.getModelCode();
	        String CarKindCode = prpTitemCarSchema.getCarKindCode();
	        String UserType = prpTitemCarSchema.getUseNatureCode();
	        
	        
	        if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+LicenseNo+",") > -1)LicenseNo="";
	        
	        biCarModelSchema.setCarMark(LicenseNo);
	        biCarModelSchema.setVehicleType(LicenseType);
	        biCarModelSchema.setEngineNo(EngineNo);
	        biCarModelSchema.setRackNo(FrameNo);
	        biCarModelSchema.setModel(ModelName);
	        biCarModelSchema.setLfDate(MakeDate);
	        biCarModelSchema.setMadeFactory(MadeFactory);
	        biCarModelSchema.setTonnage(Toncount);
	        biCarModelSchema.setRatedPassengerCapacity(Seatcount);
	        biCarModelSchema.setWholeWeight(Completekerbmass);
	        biCarModelSchema.setDisplacement(Exhaustscale);
	        biCarModelSchema.setPmVehicleType("");
	        biCarModelSchema.setFirstRegisterDate(EnrollDate);
	        biCarModelSchema.setComCode(ComCode);
	        biCarModelSchema.setRiskCode(RiskCode);
	        biCarModelSchema.setNewCarFlag(NewCarFlag);
	        paramMap.put("ModelCode", ModelCode);
			paramMap.put("CarKindCode", CarKindCode);
			paramMap.put("UserType", UserType);
	    }
/**
* @author zhaoyingchao-ghq
* @param XX单大对象blProposal.BLPrpTitemCar
* @description  封装车价查询结果
* @date 2014-03-27
* */
	    protected void processBLPrpTitemCar(BLProposal blProposal,BLPrpDcarModel blPrpDcarModel,Map paramMap)throws Exception {
	    	PrpTitemCarSchema prpTitemCarSchema = blProposal.getBLPrpTitemCar().getArr(0);
	    	String PurchasePrice = prpTitemCarSchema.getPurchasePrice();
	    	if(!"".equals(PurchasePrice)&&PurchasePrice!=null){
	    		DoubleFormat doubleFormat =new DoubleFormat();
	    		String PurchasePriceNotFound = "";
	    		int num = 0;
	    		for(int i=0; i<blPrpDcarModel.getSize();i++){
		    		PrpDcarModelSchema iPrpDcarModelSchema = blPrpDcarModel.getArr(i);
		    		String iPurchasePrice = iPrpDcarModelSchema.getPurchasePrice();
		    		if(!"".equals(iPurchasePrice)&&iPurchasePrice!=null){
		    			if(Double.parseDouble(ChgData.chgStrZero(iPurchasePrice)) == Double.parseDouble(ChgData.chgStrZero(PurchasePrice))){
		    				prpTitemCarSchema.setModelCode(iPrpDcarModelSchema.getModelCode());
		    				prpTitemCarSchema.setSeatCount(iPrpDcarModelSchema.getSeatCount());
	    	    	    	iPrpDcarModelSchema.getModelName();
	    	    	    	prpTitemCarSchema.setTonCount(iPrpDcarModelSchema.getTonCount());
	    	    	    	prpTitemCarSchema.setTonCountLB(iPrpDcarModelSchema.getTonCountLB());
	    	    	    	prpTitemCarSchema.setExhaustScale(iPrpDcarModelSchema.getExHaustScale());
	    	    	    	prpTitemCarSchema.setExhaustScaleLB(iPrpDcarModelSchema.getExhaustScaleLB());
	    	    	    	prpTitemCarSchema.setPurchasePrice(iPrpDcarModelSchema.getPurchasePrice());	
	    	    	    	prpTitemCarSchema.setPurchasePriceLB(iPrpDcarModelSchema.getPurchasePriceLB());
	    	    	    	if(!"".equals(paramMap.get("SearchSequenceNo")) && paramMap.get("SearchSequenceNo")!= null){
	    	    	    		prpTitemCarSchema.setSearchSequenceNo(paramMap.get("SearchSequenceNo").toString());
	    	    	    	}
	    	    	    	num++;
		    			}
		    			PurchasePriceNotFound = iPurchasePrice+ ","+PurchasePriceNotFound;
		    		}
		    	}
	    		if(num==0){
	    			throw new Exception("新车购置价录入有误！新车购置价应为："+PurchasePriceNotFound+"之一");
	    		}
	    	}else{
	    		throw new Exception("新车购置价不能为空！");
	    	}
	    }
}
