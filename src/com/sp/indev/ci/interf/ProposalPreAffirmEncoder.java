package com.sp.indiv.ci.interf;

import org.apache.log4j.Logger;

import com.sp.indiv.ci.blsvr.BLCIMotorcadeDeclare;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.pubfun.TransCodeCI;
import com.sp.prpall.schema.PrpTprofitDetailSchema;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;



/**
 * 发送XX预确认请求数据的编码器
 *
 */
public class ProposalPreAffirmEncoder{
    
	
	private static Logger logger = Logger.getLogger(ProposalPreAffirmEncoder.class);
	
	
    /**
	 * 编码 
	 *
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
    public String encode(DbPool dbPool, BLProposal blProposal) 
    	throws Exception 
    {
    	
        StringBuffer buf = new StringBuffer(4000);
        addXMLHead(buf);
        addPacket(buf, blProposal);
        
        logger.info("[上海XX单预确认发送报文]:"+buf.toString());
        
        return buf.toString();
    }

    /**
	 * 添加XML文件头
	 *
	 * @param buf
	 *            StringBuffer
	 */
    protected void addXMLHead(StringBuffer buf) 
    	throws Exception
    {
        buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    }

    /**
	 * 添加PACKET节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addPacket(StringBuffer buf, BLProposal blProposal) 
    	throws Exception 
    {
    	buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
        addHead(buf, blProposal);
        addBody(buf, blProposal);
        buf.append("</PACKET>");

    }

    /**
	 * 添加HEAD节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addHead(StringBuffer buf, BLProposal blProposal) 
    	throws Exception 
    {
        String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
    	buf.append("<HEAD>");
        addNode(buf, "REQUEST_TYPE", "17");
        addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_USER"));
        addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_PASSWORD"));
        buf.append("</HEAD>");
    }

    /**
	 * 添加BODY节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addBody(StringBuffer buf, BLProposal blProposal) 
    	throws Exception 
    {
        buf.append("<BODY>");
        addBasePart(buf, blProposal);
        buf.append("</BODY>");
    }

    /**
	 * 添加BASE_PART节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addBasePart(StringBuffer buf, BLProposal blProposal) 
    	throws Exception 
    {
        String strQuerySequenceNo 	= ""; 
        String strPayMethod         = ""; 
        String strSalesChannel      = ""; 
        String strBusinessNature    = "";
        String strRiskCode         = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
        strQuerySequenceNo = blProposal.getBLCIInsureDemand().getArr(0).getDemandNo();
        strPayMethod       = blProposal.getBLPrpTmain().getArr(0).getPayMethod();
        strBusinessNature    = blProposal.getBLPrpTmain().getArr(0).getBusinessNature();        
        
        
        String iComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
        strSalesChannel = TransCodeCI.getTransferCI(iComCode, "BusinessNature", strBusinessNature);
        
        buf.append("<BASE_PART>");
        addNode(buf, "BOOKING_CODE", "");
        addNode(buf, "QUERY_SEQUENCE_NO", strQuerySequenceNo);
        addNode(buf, "PAY_METHOD", strPayMethod);
        addNode(buf, "SALES_CHANNEL", strSalesChannel);
        if(!"0507".equals(strRiskCode)){
          addPolicyList(buf, blProposal);
        }
        buf.append("</BASE_PART>");
    }

    /**
	 * 添加POLICY_LIST节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addPolicyList(StringBuffer buf, BLProposal blProposal) throws Exception {
    	String strQuerySequenceNo  = blProposal.getBLCIInsureDemand().getArr(0).getDemandNo();
    	
    	String strOtherNature2 = "";
    	if(blProposal.getBLPrpTitemCar().getArr(0).getOtherNature()!=null && blProposal.getBLPrpTitemCar().getArr(0).getOtherNature().length()>2){
    		strOtherNature2 = blProposal.getBLPrpTitemCar().getArr(0).getOtherNature().substring(1,2);
    	}
    	
        buf.append("<POLICY_LIST>");
        addNode(buf, "QUERY_SEQUENCE_NO", strQuerySequenceNo);       
        
        if(new UtiPower().checkCIInsureSH(blProposal.getBLPrpTmain().getArr(0).getRiskCode(),blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	addNode(buf, "VEHICLE_CODE", blProposal.getBLPrpTitemCar().getArr(0).getModelCode());
        	
        	String operateDate=blProposal.getBLPrpTmain().getArr(0).getInputDate();
        	String contractNo=blProposal.getBLPrpTmain().getArr(0).getContractNo();
        	String vehicleAmountAdjust="1.0";   
        	double douVehicleAmountAdjust=1.0D;
        	String experienceAdjust="1.0";      
        	double douExperienceAdjust=1.0D;
        	String motorcadeMangeAdjust="1.0";   
        	double douMotorcadeMangeAdjust=1.0D;
        	String groupCode="";              
        	PrpTprofitDetailSchema  prpTprofitDetailSchema=null;
        	BLCIMotorcadeDeclare blCIMotorcadeDeclare=new BLCIMotorcadeDeclare();
        	blCIMotorcadeDeclare.query(" contractNo='"+contractNo+"'");	
			if(blCIMotorcadeDeclare.getSize()>0){
				groupCode=blCIMotorcadeDeclare.getArr(0).getGroupCode();
			}
        	if((contractNo!=null&&!"".equals(contractNo))
        		&&(new UtiPower().checkSHDeclareQuery(operateDate))
        		&&(groupCode!=null&&!"".equals(groupCode))){
	        			for (int i = 0; i < blProposal.getBLPrpTprofitDetail().getSize(); i++) {
	        				prpTprofitDetailSchema=blProposal.getBLPrpTprofitDetail().getArr(i);
		        			if("C10".equals(prpTprofitDetailSchema.getProfitCode())){  
		        				vehicleAmountAdjust=prpTprofitDetailSchema.getProfitRate();
		        				if(vehicleAmountAdjust!=null&&!"".equals(vehicleAmountAdjust)){
		        					douVehicleAmountAdjust=Double.parseDouble(vehicleAmountAdjust);
		        					douVehicleAmountAdjust=1+douVehicleAmountAdjust;
		        				}
		        			}else if("C11".equals(prpTprofitDetailSchema.getProfitCode())){  
		        				experienceAdjust=prpTprofitDetailSchema.getProfitRate();
		        				if(experienceAdjust!=null&&!"".equals(experienceAdjust)){
		        					douExperienceAdjust=Double.parseDouble(experienceAdjust);
		        					douExperienceAdjust=1+douExperienceAdjust;
		        				}
		        			}else if("C12".equals(prpTprofitDetailSchema.getProfitCode())){  
		        				motorcadeMangeAdjust=prpTprofitDetailSchema.getProfitRate();
		        				if(motorcadeMangeAdjust!=null&&!"".equals(motorcadeMangeAdjust)){
		        					douMotorcadeMangeAdjust=Double.parseDouble(motorcadeMangeAdjust);
		        					douMotorcadeMangeAdjust=1+douMotorcadeMangeAdjust;
		        				}
		        			}
						}
	        			
	        			
	        			if("1".equals(blProposal.getBLPrpTitemCar().getArr(0).getOtherNature().substring(3, 4))){
	        				addNode(buf, "SAFE_ADJUST", encodeSafeAdjust(blProposal.getBLPrpTitemCar().getArr(0).getAddonCount()));
		          	        addNode(buf, "NONCLAIM_ADJUST", encodeNonclaimAdjust(blProposal.getBLPrpTitemCarExt().getArr(0).getDamagedFactorGrade()));
		          	        addNode(buf, "LOYALTY_ADJUST", encodeLoyaltyAdjust(strOtherNature2));
		          	        addNode(buf, "VEHICLE_AMOUNT_ADJUST", "");
		        	        addNode(buf, "EXPERIENCE_ADJUST", "");
		        	        addNode(buf, "MOTORCADE_MANAGE_ADJUST", "");
	        			}else{
	        				addNode(buf, "SAFE_ADJUST", "1.0");
		        	        addNode(buf, "NONCLAIM_ADJUST", "1.0");
		        	        addNode(buf, "LOYALTY_ADJUST", "1.0");
		        	        addNode(buf, "VEHICLE_AMOUNT_ADJUST", ""+douVehicleAmountAdjust);
		        	        addNode(buf, "EXPERIENCE_ADJUST", ""+douExperienceAdjust);
		        	        addNode(buf, "MOTORCADE_MANAGE_ADJUST", ""+douMotorcadeMangeAdjust);
	        			}
	        			
	        	        
	        	        
	        	        addNode(buf, "MULTI_COVERAGE_ADJUST", "1.0");
	        	        addNode(buf, "AVERAGE_MILE_ADJUST", "1.0");
	        	        addNode(buf, "THE_AREA_ADJUST", "1.0");
	        	        
	        	        addNode(buf, "THE_DRIVER_ADJUST", "1.0");
	        	        addNode(buf, "DRIVER_GENDER_ADJUST", "1.0");
	        	        addNode(buf, "DRIVER_PERIOD_ADJUST", "1.0");
	        	        addNode(buf, "DRIVER_AGE_ADJUST", "1.0");
	        	        
	        	        addNode(buf, "MANAGE_LEVEL_ADJUST", "");
	        	        addNode(buf, "VEHICLE_LOSS_ADJUST", "1.0");
	        	        addNode(buf, "ABSOLUTE_FRANCHISE_ADJUST", "1.0");
	        	        
	        	        addNode(buf, "MOTORCADE_ADJUST", "");
	        	        addNode(buf, "SALE_CHANNEL_ADJUST", "1.0");
	        	        addNode(buf, "GROUP_BUY_ADJUST", "1.0");
	        	        addNode(buf, "DETAILS_ADJUST", "1.0");
	        	        addNode(buf, "CROSS_SALE_ADJUST", "1.0");
	        	        addNode(buf, "SERVICE_LOG_ADJUST", "1.0");
	        	        addNode(buf, "GROUP_CODE", groupCode);  
	        	        
        			}else{
	        			
	          	        addNode(buf, "SAFE_ADJUST", encodeSafeAdjust(blProposal.getBLPrpTitemCar().getArr(0).getAddonCount()));
	          	        addNode(buf, "NONCLAIM_ADJUST", encodeNonclaimAdjust(blProposal.getBLPrpTitemCarExt().getArr(0).getDamagedFactorGrade()));
	          	        addNode(buf, "LOYALTY_ADJUST", encodeLoyaltyAdjust(strOtherNature2));
	          	        addNode(buf, "MULTI_COVERAGE_ADJUST", "");
	          	        addNode(buf, "AVERAGE_MILE_ADJUST", "");
	          	        addNode(buf, "THE_AREA_ADJUST", "");
	          	        addNode(buf, "VEHICLE_AMOUNT_ADJUST", "");
	          	        addNode(buf, "THE_DRIVER_ADJUST", "");
	    	  	        addNode(buf, "DRIVER_GENDER_ADJUST", "");
	    	  	        addNode(buf, "DRIVER_PERIOD_ADJUST", "");
	    	  	        addNode(buf, "DRIVER_AGE_ADJUST", "");
	    	  	        addNode(buf, "EXPERIENCE_ADJUST", "");
	    	  	        addNode(buf, "MANAGE_LEVEL_ADJUST", "");
	    	  	        addNode(buf, "VEHICLE_LOSS_ADJUST", "");
	    	  	        addNode(buf, "ABSOLUTE_FRANCHISE_ADJUST", "");
	    	  	        addNode(buf, "MOTORCADE_MANAGE_ADJUST", "");
	    	  	        addNode(buf, "MOTORCADE_ADJUST", "");
	    	  	        addNode(buf, "SALE_CHANNEL_ADJUST", "");
	    	  	        addNode(buf, "GROUP_BUY_ADJUST", "");
	    	  	        addNode(buf, "DETAILS_ADJUST", "");
	    	  	        addNode(buf, "CROSS_SALE_ADJUST", "");
	    	  	        addNode(buf, "SERVICE_LOG_ADJUST", "");
	    	  	        addNode(buf, "GROUP_CODE", "");  
        			}
        	
        }
        
        addCoverageList(buf, blProposal);
        buf.append("</POLICY_LIST>");
    }

    /**
	 * 添加COVERAGE_LIST节点
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addCoverageList(StringBuffer buf, BLProposal blProposal) throws Exception {
        
        buf.append("<COVERAGE_LIST>");
        addCoverage(buf, blProposal);
        buf.append("</COVERAGE_LIST>");
    }


    /**
	 * 添加COVERAGE节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addCoverage(StringBuffer buf, BLProposal blProposal) throws Exception {
    	String strCoverageType      ="";
    	String strCoverageCode      ="";
    	String strKindCode          ="";   
    	String strAmount            ="";   
    	String StartDate            ="";   
    	String EndDate              ="";   
    	String strStartDate         ="";   
    	String strEndDate           ="";   
    	
    	String strPremium           ="";   
    	String strPurchasePrice     = "";
    	
    	
    	String strBasePremium           ="";   
  	    
    	String strRiskCode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
    	for(int i =0;i<blProposal.getBLPrpTitemKind().getSize();i++){
    	  strKindCode = blProposal.getBLPrpTitemKind().getArr(i).getKindCode();
    	  strAmount   = blProposal.getBLPrpTitemKind().getArr(i).getAmount();
    	  StartDate= blProposal.getBLPrpTitemKind().getArr(i).getStartDate();
    	  EndDate  = blProposal.getBLPrpTitemKind().getArr(i).getEndDate();
    	  
    	  strPremium = blProposal.getBLPrpTitemKind().getArr(i).getPremium();
    	  
    	  
    	  
    	  strBasePremium = blProposal.getBLPrpTitemKind().getArr(i).getBenchMarkPremium();
    	  
    	  strPurchasePrice = blProposal.getBLPrpTitemCar().getArr(0).getPurchasePrice();
    	  
    	
    	  strCoverageType=encodeCoverageType(strKindCode);
    	  strCoverageCode = encodeCoverageCode(strKindCode,strRiskCode);
    	  strEndDate = StringUtils.replace(EndDate, "-", "");
    	  strStartDate = StringUtils.replace(StartDate, "-", "");
          buf.append("<COVERAGE>");
          addNode(buf, "COVERAGE_TYPE", strCoverageType);
          addNode(buf, "COVERAGE_CODE", strCoverageCode);
          addNode(buf, "COM_COVERAGE_CODE", strKindCode);
          addNode(buf, "LIMIT_AMOUNT", strAmount);
          addNode(buf, "START_DATE", strStartDate);
          addNode(buf, "END_DATE", strEndDate);
          
          addNode(buf, "STANDARD_PREMIUM", strPremium);
          
          
          addNode(buf, "BASED_PREMIUM", strBasePremium);
          
          if(strCoverageCode.equals("C11") || strCoverageCode.equals("C01")){
        	  double purchasePrice = Double.parseDouble(ChgData.chgStrZero(strPurchasePrice));
        	  double amount = Double.parseDouble(ChgData.chgStrZero(strAmount));
        	  double actualValue = Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getActualValue()));
        	  if(purchasePrice>amount){
        		  addNode(buf, "DEFICIT_CODE", "02");
        		  if(SysConfig.getProperty("BIINSUREDSUM").trim().equals("1")){
	        		  if(amount==actualValue){
	        			  addNode(buf, "SUM_INSURED_TYPE", "02");
	        		  }else{
	        			  addNode(buf, "SUM_INSURED_TYPE", "03");
	        		  }
        		  }
        	  }else{
        		  addNode(buf, "DEFICIT_CODE", "01");
        		  if(SysConfig.getProperty("BIINSUREDSUM").trim().equals("1")){
        		    addNode(buf, "SUM_INSURED_TYPE", "01");  
        		  }
        	  }
          }
          
          
          buf.append("</COVERAGE>");
    	}
    }

    public static void addNode(StringBuffer buffer, String name, String value) {
        value = StringUtils.replace(value, "<", "&lt;");
        value = StringUtils.replace(value, ">", "&gt;");
        value = StringUtils.replace(value, "&", "&amp;");
        value = StringUtils.replace(value, "'", "&apos;");
        value = StringUtils.replace(value, "\"", "&quot;");

        buffer.append("<");
        buffer.append(name);
        buffer.append(">");
        buffer.append(value);
        buffer.append("</");
        buffer.append(name);
        buffer.append(">");
        buffer.append("\r\n");
    }
    private String encodeCoverageCode(String strKindCode, String strRiskCode) throws Exception{
    	String strCoverageCode = "";
    	if("0509".equals(strRiskCode)){
    		if("BZ".equals(strKindCode)){
        		strCoverageCode = "A02";
        	 }else if("A".equals(strKindCode)){
        		strCoverageCode = "C11";
        	 }else if("B".equals(strKindCode)){
        		strCoverageCode = "B11";
        	 }else if("G1".equals(strKindCode)){
        		strCoverageCode = "D11";
        	 }else if("D3".equals(strKindCode) || "D4".equals(strKindCode)){
        		strCoverageCode = "E11";
        	 }else{
        		strCoverageCode = "F11";
        	 }
    	}else{
    	     if("BZ".equals(strKindCode)){
    		    strCoverageCode = "A02";
    	     }else if("A".equals(strKindCode)){
    		    strCoverageCode = "C01";
    	     }else if("B".equals(strKindCode)){
    		    strCoverageCode = "B01";
    	     }else if("G1".equals(strKindCode)){
    		    strCoverageCode = "D01";
    	     }else if("D3".equals(strKindCode) || "D4".equals(strKindCode)){
    		    strCoverageCode = "E01";
    	     }else{
    		    strCoverageCode = "F01";
    	     }
    	}
    	return strCoverageCode;
    }





































     private String encodeCoverageType(String strKindCode) throws Exception{
	    	String strCoverageType ="";
	    	if("BZ".equals(strKindCode)){
	     		 strCoverageType = "";        
	     	 }else if("B".equals(strKindCode)){
	     		 strCoverageType = "2";
	     	 }else if("A".equals(strKindCode) || "D3".equals(strKindCode) || "D4".equals(strKindCode)|| "G1".equals(strKindCode)){
	     		 strCoverageType = "3";
	     	 }else{
	     		 strCoverageType = "9";
	     	 }
	    	return strCoverageType;
	    }
     
     private String encodeSafeAdjust(String strAddonCount){
    	 if("1".equals(strAddonCount)){
    		 return "0.9";
    	 }else {
    		 return "1.0";
    	 }
     }
     private String encodeNonclaimAdjust(String strDamagedFactorGrade){
    	 if("11".equals(strDamagedFactorGrade)){
    		 return "0.7";
    	 }else if("12".equals(strDamagedFactorGrade)){
    		 return "0.8";
    	 }else if("13".equals(strDamagedFactorGrade)){
    		 return "0.9";
    	 }else if("14".equals(strDamagedFactorGrade)){
    		 return "1.0";
    	 }else if("15".equals(strDamagedFactorGrade)){
    		 return "1.1";
    	 }else if("16".equals(strDamagedFactorGrade)){
    		 return "1.2";
    	 }else if("17".equals(strDamagedFactorGrade)){
    		 return "1.3";
    	 }else{
    		 return "";
    	 }
     }
     private String encodeLoyaltyAdjust(String strOtherNature2){
    	 if("1".equals(strOtherNature2)){
    		 return "0.9";
    	 }else{
    		 return "1.0";
    	 }
     }
     
}
