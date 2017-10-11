package com.sp.indiv.ci.interf;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sp.indiv.ci.blsvr.BLCIInsureDemand;
import com.sp.indiv.ci.dbsvr.DBCIInsureDemand;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.pubfun.TransCode;
import com.sp.prpall.pubfun.TransCodeCI;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utiall.blsvr.BLPrpDuser;
import com.sp.utiall.dbsvr.DBPrpDagent;
import com.sp.utiall.schema.PrpDuserSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.Transfer;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

public class ProposalValidEncoder {

	
	private static Logger logger = Logger.getLogger(ProposalValidEncoder.class);
	
	/**
	 * 编码
	 *
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
	public String encode(DbPool dbPool, BLProposal blProposal) throws Exception {
			
			
			String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
			String strRiskCode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
			if(new UtiPower().checkCIInsureSH(strRiskCode, strComCode)){
				ProposalPreAffirmEncoder proposalPreAffirmEncoder = new ProposalPreAffirmEncoder();
				return proposalPreAffirmEncoder.encode(dbPool,blProposal);	
			}else{
			StringBuffer buf = new StringBuffer(4000);
			getCIInsureDemand(dbPool, blProposal);
			addXMLHead(buf);
			addPacket(buf, blProposal);
			
			logger.info("[XX确认发送报文]:"+buf.toString());
			
			return buf.toString();
			}
			
	}

	/**
	 * 添加XML文件头
	 *
	 * @param buf
	 *            StringBuffer
	 */
	protected void addXMLHead(StringBuffer buf) {
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
			throws Exception {
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
	protected void addHead(StringBuffer buf, BLProposal blProposal) throws Exception {
		
		
		
		
		

		String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();

		buf.append("<HEAD>");
		addNode(buf, "REQUEST_TYPE", "02");
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
			throws Exception {
	  
		buf.append("<BODY>");
		addBasePart(buf, blProposal);	
		
		if(new UtiPower().checkSinoCarPlatformSZ(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
			addCheckItem(buf, blProposal);
			addVehicle(buf, blProposal);
		}
		
		
		if(new UtiPower().checkInterAgreementSX(blProposal.getBLPrpTmain().getArr(0).getComCode())){
			addCheckItemCar(buf, blProposal);
		}
		
		addOwnerList(buf, blProposal);
		addPhList(buf, blProposal);
		addInsuredList(buf, blProposal);		
		addBusiCoverList(buf, blProposal);
		
		addVehicleTaxation(buf, blProposal);
		
		
		String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
		String strRiskCode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
		if(new UtiPower().checkCIInsureBJ(strRiskCode, strComCode)&&!"0507".equals(strRiskCode)){
			addCoverageList(buf, blProposal);
		}
		
		
        if(new UtiPower().checkSinoCommission(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
        	addCommissionAgent(buf, blProposal);
        	addCommission(buf, blProposal);
        }
        
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
			throws Exception {
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		String querySequenceNo = "";
		String policyNo = "";
		String salesChannel = "";
		String sumPremium="";       
		String proposalNo="";       
		
		querySequenceNo = blProposal.getBLCIInsureDemand().getArr(0)
				.getDemandNo();
		policyNo = blProposal.getBLPrpTmain().getArr(0).getPolicyNo();
		proposalNo = blProposal.getBLPrpTmain().getArr(0).getProposalNo();
		
		
		String iBusinessNature =  blProposal.getBLPrpTmain().getArr(0).getBusinessNature();
		String iComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
		
		
		if (new UtiPower().checkSinoCommission(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())) {
			salesChannel = Transfer.getTransfer("SinoCommission", "BusinessNature", iBusinessNature);
		}else if(new UtiPower().checkSinoCommissionHis(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
			salesChannel = Transfer.getTransfer("SinoCommission", "BusinessNatureCIHis", iBusinessNature);
		}else{
			
			if (new UtiPower().checkSalesChannelCISwitch(blProposal.getBLPrpTmain().getArr(0).getComCode())) {
				salesChannel = Transfer.getTransfer("SinoCommission", "BusinessNature", iBusinessNature);
			}else{
				salesChannel = TransCodeCI.getTransferCI(iComCode, "BusinessNature", iBusinessNature);
			}
			
		}
		
		
		
		sumPremium=blProposal.getBLPrpTmain().getArr(0).getSumPremium();
		
		
		String handlerCode = ""; 
		handlerCode = blProposal.getBLPrpTmain().getArr(0).getHandler1Code();
		
		
		String     strCode    = "";
		String     strName    = "";
		String     affirmant  = "";
		String     strComCode = "";
		strComCode  =  blProposal.getBLPrpTmain().getArr(0).getComCode();
		
		
		String strBillDate = "";	
		if(strComCode.substring(0,2).equals("01") || 
		   strComCode.substring(0,2).equals("07"))
		{
			
		}
		else
		{
        	
        	
        	strBillDate			= correctDate(blProposal.getBLPrpTmain().getArr(0).getOperateDate());
        	
		}
		
    
    
    if(strComCode.substring(0,2).equals("07")){
     
       BLPrpDuser blPrpDuser   = new BLPrpDuser();
       
       strCode     =   Str.encode(blProposal.getBLPrpTmain().getArr(0).getHandler1Code());
       
       strName     =   blPrpDuser.translateCode(strCode,true);
       affirmant   =   strName;
    }
    

		buf.append("<BASE_PART>");
		addNode(buf, "QUERY_SEQUENCE_NO", querySequenceNo);
		
        
		/*if(strComCode.substring(0,2).equals("19") || strComCode.substring(0,2).equals("03"))
		{
			addNode(buf, "PROPOSAL_NO", proposalNo);
	    }*/
		
		if(strComCode.substring(0,2).equals("01") || strComCode.substring(0,2).equals("07"))
		{
			
			addNode(buf, "BILL_DATE", "");
	    }else{
	    	addNode(buf, "PROPOSAL_NO", proposalNo);
	    	
	    	
	    	addNode(buf, "BILL_DATE", strBillDate);			
	    	
	    }
        
		addNode(buf, "POLICY_NO", proposalNo);
		addNode(buf, "TP_MARK", "");
		addNode(buf, "SALES_CHANNEL", salesChannel);
		
		if(new UtiPower().checkInterfaceHLJ(strComCode)){
			String businessAgent = "";
			if((SysConfig.getProperty("BYBUSINESSAGENT"+strComCode.substring(0, 2)).trim()).indexOf(blProposal.getBLPrpTmain().getArr(0).getBusinessNature())>-1){
	    		businessAgent = Transfer.getTransfer("SinoCommission", "ByBusinessAgentHLJ", blProposal.getBLPrpTmain().getArr(0).getBusinessNature());
	    	}
			addNode(buf, "BY_BUSINESS_AGENT", businessAgent);
		}
		
		addNode(buf, "ASSUMPTION", "");
		
		if(strComCode.substring(0,2).equals("01"))
		{
			
			
			
		}else {
			addNode(buf, "AGENT", "");
		}
		
		addNode(buf, "OPERATOR", "");
		addNode(buf, "CURRENCY", "");
		addNode(buf, "ACTUAL_PREMIUM", sumPremium);
		
		
		if(strComCode.substring(0,2).equals("07")||strComCode.substring(0, 2).equals("01")){
			addNode(buf, "AFFIRMANT", affirmant);
	    }
		
		

		
		if(strComCode.substring(0,2).equals("01")){
			addNode(buf, "DUMMY_CONFIRM_FLAG", "1");
		}
		
		
		if(SysConfig.getProperty("CI_SINOSOFT_0806").trim().indexOf(
				strComCode.substring(0, 2).trim()) > -1){
			addNode(buf, "INSURE_CONFORM_FLAG", "0");
		}
		
		
		if(new UtiPower().checkInterfaceHLJ(strComCode)){
			String cityCode = "";
			String countyCode = "";
			cityCode = PubTools.getCityAreaFlag(strComCode);
			countyCode = PubTools.getCountyAreaFlag(strComCode);
			addNode(buf, "CITY_CODE",cityCode);
			addNode(buf, "COUNTY_CODE",countyCode);
		}
		
		
		if(new UtiPower().checkInterAgreementSX(strComCode)){
		
			
			String strStartDate = blProposal.getBLPrpTmain().getArr(0).getStartDate();
			String strEndDate = blProposal.getBLPrpTmain().getArr(0).getEndDate();
			
			
			String strStartHour     = blProposal.getBLPrpTmain().getArr(0).getStartHour();
	        String strEndHour       = blProposal.getBLPrpTmain().getArr(0).getEndHour();
	        if("0507".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())&&"1".equals(blProposal.getBLPrpTmain().getArr(0).getImmeValidFlag())
	        		&& new UtiPower().checkIntoEffect(strComCode)){
	        	strStartDate 			= blProposal.getBLPrpTmain().getArr(0).getImmeValidStartDate().substring(0,10);
	            strEndDate 				= blProposal.getBLPrpTmain().getArr(0).getImmeValidEndDate().substring(0,10);
	            strStartHour            = blProposal.getBLPrpTmain().getArr(0).getImmeValidStartDate().substring(11,13);
	            strEndHour              = blProposal.getBLPrpTmain().getArr(0).getImmeValidEndDate().substring(11,13);
	        }else if("1".equals(blProposal.getBLPrpTmain().getArr(0).getImmeValidFlag())
	                   && new UtiPower().checkImmeValidJS(strComCode,blProposal.getBLPrpTmain().getArr(0).getOperateDate(),blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
	                strStartDate        = blProposal.getBLPrpTmain().getArr(0).getImmeValidStartDate().substring(0,10);
	                strEndDate          = blProposal.getBLPrpTmain().getArr(0).getImmeValidEndDate().substring(0,10);
	                strStartHour        = blProposal.getBLPrpTmain().getArr(0).getImmeValidStartDate().substring(11,13);
	                strEndHour          = blProposal.getBLPrpTmain().getArr(0).getImmeValidEndDate().substring(11,13);
	            }
	            strStartDate            = correctDate(strStartDate);
	            strEndDate              = correctDate(strEndDate);
	            if(strStartHour.length() != 2)
	            {
	                if(strStartHour.equals("0") || strStartHour.equals(""))
	                {
	                    strStartHour = "00";
	                }
	                else
	                {
	                    strStartHour = "0" + strStartHour;
	                }
	            }
	            
	            if(strEndHour.length() != 2)
	            {
	                if(strEndHour.equals("0") || strEndHour.equals(""))
	                {
	                    strEndHour = "00";
	                }
	                else
	                {
	                    strEndHour = "0" + strEndHour;
	                }
	            }
			
			
	        addNode(buf, "START_DATE", strStartDate+strStartHour);
	        addNode(buf, "END_DATE", strEndDate+strEndHour);
	        
		
		}
		
		
		ProposalCarShipTaxValidEncoder carShipTaxValidEncoder = new ProposalCarShipTaxValidEncoder();
		
		carShipTaxValidEncoder.encode(blProposal, buf, "BASE_PART");
		
		
		
		
		if(new UtiPower().checkSinoCIversion3(strComCode,blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
			addNode(buf, "AREA_FLAG", PubTools.getAreaFlag(strComCode));
		}
		
		
		
		String strRiskCode=blProposal.getBLPrpTmain().getArr(0).getRiskCode();
		if(new UtiPower().checkCIInsureBJ(strRiskCode,strComCode)){
			String strModeCode=blProposal.getBLPrpTitemCar().getArr(0).getModelCode();  
			String strPurchasePrice=blProposal.getBLPrpTitemCar().getArr(0).getPurchasePrice();  
			String strLicenseCategory = blProposal.getBLPrpTitemCar().getArr(0).getLicenseCategory();  
			String strLimitLoad =  blProposal.getBLPrpTitemCar().getArr(0).getTonCount();  
            strLimitLoad = new DecimalFormat("0").format(Double.parseDouble(ChgData.chgStrZero(strLimitLoad))*1000);
			String strLimitLoadPerson =  blProposal.getBLPrpTitemCar().getArr(0).getSeatCount(); 
			String strRackNo 			= blProposal.getBLPrpTitemCar().getArr(0).getFrameNo();
			String strEngineNo 		= blProposal.getBLPrpTitemCar().getArr(0).getEngineNo();  
			String strEnrollDate = blProposal.getBLPrpTitemCar().getArr(0).getEnrollDate();  
			       strEnrollDate = correctDate(strEnrollDate);
			       
			String strMileages 	= "" + ((int) Double.parseDouble(blProposal.getBLPrpTitemCar().getArr(0).getRunMiles()));
			
			String strAmout="";
			String strUseType          = encoderUseTypeNew(blProposal); 
			
			
			
			
				strAmout=blProposal.getBLPrpTitemCar().getArr(0).getActualValue();  
			
			
			if(!blProposal.getBLPrpTmain().getArr(0).getRiskCode().equals("0507")){
				addNode(buf, "CONFIRM_SEQUENCE_NO","");   
				addNode(buf, "VEHICLE_CODE",strModeCode);
				addNode(buf, "POLICY_VEHICLE_PRICE",strPurchasePrice);
				addNode(buf, "BUSI_PREFERENTIAL_PREMIUM","0");
				addNode(buf, "VEHICLE_STYLE",strLicenseCategory);
				addNode(buf, "USE_TYPE", strUseType);
				addNode(buf, "LIMIT_LOAD", strLimitLoad);
				addNode(buf, "LIMIT_LOAD_PERSON", strLimitLoadPerson);
				addNode(buf, "RACK_NO", strRackNo);
				addNode(buf, "ENGINE_NO", strEngineNo);
				addNode(buf, "VEHICLE_REGISTER_DATE", strEnrollDate);
				addNode(buf, "AVERAGE_MILE",strMileages);
				addNode(buf, "ACTUAL_VEHICLE_PRICE",strAmout);
			}
			
			addNode(buf, "UNDERWRITING_NAME",blProposal.getBLPrpTmain().getArr(0).getUnderWriteName());
			addNode(buf, "UNDERWRITING_CODE",blProposal.getBLPrpTmain().getArr(0).getUnderWriteCode());
			
			
			if("1,2,3,4,9,A,G,H,M,N".indexOf(blProposal.getBLPrpTmain().getArr(0).getBusinessNature())>-1
					&& !"".equals(blProposal.getBLCIInsureDemand().getArr(0).getIP())){
				
				PrpDuserSchema prpDuserSchema = TransCode.getApprove(strComCode);
				addNode(buf, "APPROVE_CODE",prpDuserSchema.getUserCode());
				addNode(buf, "APPROVE_NAME",prpDuserSchema.getUserName());
			}
			
			String operatorCode = blProposal.getBLPrpTmain().getArr(0).getOperatorCode();
			String operatorName =  new BLPrpDuser().translateCode(operatorCode,true);
			addNode(buf, "AGENT", operatorName);
			addNode(buf, "AGENT_NAME",operatorCode);
			String disRate = new DecimalFormat("0.00").format(Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTmain().getArr(0).getDisRate()))/100);
			addNode(buf, "COMMISSION_RATE",disRate);
			
		}
		

		buf.append("</BASE_PART>");
	}

	/**
	 * 添加OWNER_LIST节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addOwnerList(StringBuffer buf, BLProposal blProposal)
			throws Exception {
		
		
		
		
		
		buf.append("<OWNER_LIST>");
		addOwnerData(buf, blProposal);
		buf.append("</OWNER_LIST>");
	}

	/**
	 * 添加Owner_Data节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addOwnerData(StringBuffer buf, BLProposal blProposal)
			throws Exception {
		
		
		
		
		
		
		
		
		
		
		

		String ownerName="";  
		String ownerType="";  
		String ownerCertiType="";  
		String ownerCertiCode="";  
		String ownerAddress  ="";  
		String ownerMailAddress=""; 
		String ownerPostCode  ="";  
		String ownerTelephone ="";  
		String ownerCompanyType="";  
		ownerName=blProposal.getBLPrpTitemCar().getArr(0).getCarOwner();

		for(int a =0;a<blProposal.getBLPrpTinsured().getSize();a++){

			if(blProposal.getBLPrpTinsured().getArr(a).getInsuredFlag().equals("1")){
				
				
				
				ownerCertiCode=blProposal.getBLPrpTinsured().getArr(a).getIdentifyNumber();
				ownerAddress=blProposal.getBLPrpTinsured().getArr(a).getInsuredAddress();
				ownerMailAddress=blProposal.getBLPrpTinsured().getArr(a).getInsuredAddress();
				ownerPostCode=blProposal.getBLPrpTinsured().getArr(a).getPostCode();
				ownerTelephone=blProposal.getBLPrpTinsured().getArr(a).getPhoneNumber();
				
				ownerCompanyType="1"; 
			}

		}
		
		if(!"01".equals(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0,2))&&
				!"07".equals(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0,2))){
			ownerType=blProposal.getBLPrpTitemCar().getArr(0).getCarOwnerNature();
			ownerCertiType=blProposal.getBLPrpTitemCar().getArr(0).getInsuredTypeCode();
			ownerCertiCode=blProposal.getBLPrpTitemCar().getArr(0).getOwnerAddress();
			if("1".equals(ownerType)){
				ownerType="2";	
				ownerCertiType="10";
			}
			else if("2".equals(ownerType)){
				ownerType="3";			
				ownerCertiType="10";
			}
			else{
				ownerType="1";	
			}
			if("01".equals(ownerCertiType)){
			}
			else if("10".equals(ownerCertiType)){
			}
			else if("03".equals(ownerCertiType)){
				ownerCertiType="02";
			}
			else if("04".equals(ownerCertiType)){
				ownerCertiType="03";
			}
			else{
				ownerCertiType="99";
			}
		}
		else{
			ownerType="1";	
			ownerCertiType="01";
		}
		
	    if("".equals(ownerCertiCode)){
	    	ownerCertiCode = "null";
	    }
	    if("".equals(ownerAddress)){
	    	ownerAddress = "null";
	    }
	    if("".equals(ownerMailAddress)){
	    	ownerMailAddress = "null";
	    }
	    
	    String currentDate=new ChgDate().getCurrentTime("yyyy-MM-dd");
	    if(new UtiPower().checkCarShipTaxBJ(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0,2), currentDate) 
	    		&& "0507".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
	    	ownerCertiType=translate(blProposal.getBLPrpTitemCar().getArr(0).getInsuredTypeCode());
	    	ownerCertiCode=blProposal.getBLPrpTitemCar().getArr(0).getOwnerAddress();
	    }
	    
		buf.append("<OWNER_DATA>");









		addNode(buf, "OWNER_NAME", ownerName);
		
		addNode(buf, "OWNER_TYPE", ownerType);  
		addNode(buf, "CERTI_TYPE", ownerCertiType);   
		addNode(buf, "CERTI_CODE", ownerCertiCode);   
		
		addNode(buf, "ADDRESS", ownerAddress);      
		addNode(buf, "MAIL_ADDRESS", ownerMailAddress); 
		addNode(buf, "ZIP", ownerPostCode);          
		addNode(buf, "TELEPHONE", ownerTelephone);    
		addNode(buf, "COMPANY_TYPE", ownerCompanyType);
		buf.append("</OWNER_DATA>");
	}

	/**
	 * 添加PH_LIST节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addPhList(StringBuffer buf, BLProposal blProposal)
			throws Exception {
		
		
		
		
		buf.append("<PH_LIST>");
		
		addPhData(buf, blProposal);
		buf.append("</PH_LIST>");
	}

	/**
	 * 添加PH_DATA节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addPhData(StringBuffer buf, BLProposal blProposal)
			throws Exception {
		
		
		
		
		
		
		
		
		
		
		
		String appName="";  
		String appType="";  
		String appCertiType="";  
		String appCertiCode="";  
		String appAddress  ="";  
		String appMailAddress=""; 
		String appPostCode  ="";  
		String appTelephone ="";  
		String appCompanyType="";  

		for(int a =0;a<blProposal.getBLPrpTinsured().getSize();a++){

			if(blProposal.getBLPrpTinsured().getArr(a).getInsuredFlag().equals("2")){
				appName=blProposal.getBLPrpTinsured().getArr(a).getInsuredName();
				
				
				
				appCertiCode=blProposal.getBLPrpTinsured().getArr(a).getIdentifyNumber();
				appAddress=blProposal.getBLPrpTinsured().getArr(a).getInsuredAddress();
				appMailAddress=blProposal.getBLPrpTinsured().getArr(a).getInsuredAddress();
				appPostCode=blProposal.getBLPrpTinsured().getArr(a).getPostCode();
				
				if(UtiPower.isIdentifyCheck(blProposal.getBLPrpTmain().getArr(0).getMakeCom(), blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
					appTelephone=blProposal.getBLPrpTinsured().getArr(a).getMobile();
				}else{
					appTelephone=blProposal.getBLPrpTinsured().getArr(a).getPhoneNumber();
				}
				
				
				appCompanyType="1"; 
			}

		}

    if("".equals(appCertiCode)){
    	appCertiCode = "null";
    }
    if("".equals(appAddress)){
    	appAddress = "null";
    }
    if("".equals(appMailAddress)){
    	appMailAddress = "null";
    }
    
    logger.info("XX人信息：appCertiCode="+appCertiCode);
    logger.info("XX人信息：appAddress="+appAddress);
    logger.info("XX人信息：appMailAddress="+appMailAddress);
    

		buf.append("<PH_DATA>");









		addNode(buf, "POLICY_HOLDER", appName);
		addNode(buf, "PH_TYPE", "1");
		addNode(buf, "CERTI_TYPE", "01");
		addNode(buf, "CERTI_CODE", appCertiCode);
		addNode(buf, "ADDRESS", appAddress);
		addNode(buf, "MAIL_ADDRESS", appMailAddress);
		addNode(buf, "ZIP", appPostCode);
		addNode(buf, "TELEPHONE", appTelephone);
		addNode(buf, "COMPANY_TYPE", appCompanyType);
		buf.append("</PH_DATA>");
	}

	/**
	 * 添加INSURED_LIST节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addInsuredList(StringBuffer buf, BLProposal blProposal)
			throws Exception {
		
		
		
		

		buf.append("<INSURED_LIST>");
		
		addInsuredDate(buf, blProposal);
		buf.append("</INSURED_LIST>");
	}

	/**
	 * 添加INSURED_DATA节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addInsuredDate(StringBuffer buf, BLProposal blProposal)
			throws Exception {
		
		
		
		
		
		
		
		
		
		
		
		String insuredName="";  
		String insuredType="";  
		String insuredCertiType="";  
		String insuredCertiCode="";  
		String insuredAddress  ="";  
		String insuredMailAddress=""; 
		String insuredPostCode  ="";  
		String insuredTelephone ="";  
		String insuredCompanyType="";  

		for(int a =0;a<blProposal.getBLPrpTinsured().getSize();a++){

			if(blProposal.getBLPrpTinsured().getArr(a).getInsuredFlag().equals("1")){
				insuredName=blProposal.getBLPrpTinsured().getArr(a).getInsuredName();
				
				
				
				insuredCertiCode=blProposal.getBLPrpTinsured().getArr(a).getIdentifyNumber();
				insuredAddress=blProposal.getBLPrpTinsured().getArr(a).getInsuredAddress();
				insuredMailAddress=blProposal.getBLPrpTinsured().getArr(a).getInsuredAddress();
				insuredPostCode=blProposal.getBLPrpTinsured().getArr(a).getPostCode();
				
				if(UtiPower.isIdentifyCheck(blProposal.getBLPrpTmain().getArr(0).getMakeCom(), blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
					insuredTelephone=blProposal.getBLPrpTinsured().getArr(a).getMobile();
				}else{
					insuredTelephone=blProposal.getBLPrpTinsured().getArr(a).getPhoneNumber();
				}
				
				
				insuredCompanyType="1"; 
			}

		}

    if("".equals(insuredCertiCode)){
    	insuredCertiCode = "null";
    }
    if("".equals(insuredAddress)){
    	insuredAddress = "null";
    }
    if("".equals(insuredMailAddress)){
    	insuredMailAddress = "null";
    }
    
    logger.info("被XX人信息：insuredCertiCode="+insuredCertiCode);
    logger.info("被XX人信息：insuredAddress="+insuredAddress);
    logger.info("被XX人信息：insuredMailAddress="+insuredMailAddress);
    
		buf.append("<INSURED_DATA>");









		addNode(buf, "INSURED_NAME", insuredName);
        
        
		
        addNode(buf, "INSURED_TYPE", encoderInsuredType(blProposal));
        
        addNode(buf, "CERTI_TYPE", "01");
		addNode(buf, "CERTI_CODE", insuredCertiCode);
		addNode(buf, "ADDRESS",  insuredAddress);
		addNode(buf, "MAIL_ADDRESS",  insuredMailAddress);
		addNode(buf, "ZIP", insuredPostCode);
		addNode(buf, "TELEPHONE", insuredTelephone);
		addNode(buf, "COMPANY_TYPE", insuredCompanyType);
		buf.append("</INSURED_DATA>");
	}

	/**
	 * 添加BUSI_COVER_LIST节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBusiCoverList(StringBuffer buf, BLProposal blProposal)
			throws Exception {
		
		
		
		
		
		

		buf.append("<BUSI_COVER_LIST>");
		
		addBusiCoverDate(buf, blProposal);
		buf.append("</BUSI_COVER_LIST>");
	}

	/**
	 * 添加BUSI_COVER_DATA节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBusiCoverDate(StringBuffer buf, BLProposal blProposal)
			throws Exception {
		
		
		
		
		
		
		
		

		buf.append("<BUSI_COVER_DATA>");
		addNode(buf, "COVERAGE_CODE", "");
		addNode(buf, "POLICY_NO", "");
		addNode(buf, "START_DATE", "");
		addNode(buf, "END_DATE", "");
		addNode(buf, "LIMIT_AMOUT", "");
		addNode(buf, "PREMIUM", "");
		buf.append("</BUSI_COVER_DATA>");
	}

	
	protected void addVehicleTaxation(StringBuffer buf, BLProposal blProposal)throws Exception{
		ProposalCarShipTaxValidEncoder carShipTaxValidEncoder = new ProposalCarShipTaxValidEncoder();
		carShipTaxValidEncoder.encode(blProposal, buf, "VehicleTaxation");
	}
	
	
	
	/**
	 * 添加COVERAGE_LIST节
	 * @param buf StringBuffer
	 * @throws Exception
	 */
    protected void addCoverageList(StringBuffer buf, BLProposal blProposal)throws Exception {
    	buf.append("<COVERAGE_LIST>");
    	addCoverageDate(buf, blProposal);
		buf.append("</COVERAGE_LIST>");
    }
    
    
    /**
	 * 添加Commission_Agent节
	 * @param buf StringBuffer
	 * @throws Exception
	 */
    protected void addCommissionAgent(StringBuffer buf, BLProposal blProposal)throws Exception {
    	buf.append("<COMMISSION_AGENT>");
    	String strAgentCode = blProposal.getBLPrpTmain().getArr(0).getAgentCode();
    	String salesChannel = Transfer.getTransfer("SinoCommission", "BusinessNature", blProposal.getBLPrpTmain().getArr(0).getBusinessNature());
		if(strAgentCode!=null&&!strAgentCode.equals("")
				&&"51,52,53,54".indexOf(salesChannel)>-1
				&&!("M".indexOf(blProposal.getBLPrpTitemCar().getArr(0).getMainCarKindCode())>-1)){
			DBPrpDagent dbPrpDagent = new DBPrpDagent();
			if(dbPrpDagent.getInfo(strAgentCode)==0){
				
				if(new UtiPower().checkSinoCommissionZJ(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
					addNode(buf, "CERTIFICATE_NO", dbPrpDagent.getIdentifyNumber());
				}else{
					addNode(buf, "CERTIFICATE_NO", dbPrpDagent.getPermitNo());
				}
				
				addNode(buf, "INDIVIDUAL_PRODUCER_CODE", dbPrpDagent.getAgentCode());
			}
		}else{
			addNode(buf, "CERTIFICATE_NO", "");
			addNode(buf, "INDIVIDUAL_PRODUCER_CODE", "");
		}
		buf.append("</COMMISSION_AGENT>");
    }
    
    protected void addCommission(StringBuffer buf, BLProposal blProposal)throws Exception {
		DecimalFormat df = new DecimalFormat("0.0000");
    	buf.append("<COMMISSION>");
    	String salesChannel = Transfer.getTransfer("SinoCommission", "BusinessNature", blProposal.getBLPrpTmain().getArr(0).getBusinessNature());
		if(!("M".indexOf(blProposal.getBLPrpTitemCar().getArr(0).getMainCarKindCode())>-1)){
			double DisRate = Double.parseDouble(blProposal.getBLPrpTmain().getArr(0).getDisRate())/100;
			addNode(buf, "COMMISSION_RATE", df.format(DisRate)+"");
		}else{
			addNode(buf, "COMMISSION_RATE", "");
		}
    	buf.append("</COMMISSION>");
    }
    
    
    protected void addCheckItemCar(StringBuffer buf, BLProposal blProposal)throws Exception {
    	String strCarMark = "";
    	String strVehicleCategory 	= ""; 
    	String strEngineNo = "";
    	String strRackNo = "";
    	String strNewVehicleFlag = "";
    	String strVehicleType = "";
    	String strEnrollDate = "";
    	String strLicenseCategory = "";
    	String strLimitLoadPerson = "";
    	String strLimitLoad = "";
    	String strWholeWeight = "";
    	String fuelType = "";      
    	
    	strCarMark = blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo();
    	if(new UtiPower().checkStartUp0802(blProposal.getBLPrpTmain().getArr(0).getRiskCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
            strVehicleCategory 	= encoderVehicleCategoryNew(blProposal);
         }
         else{
            strVehicleCategory  = encoderVehicleCategory(blProposal); 
         }
    	strEngineNo = blProposal.getBLPrpTitemCar().getArr(0).getEngineNo();
    	strRackNo = blProposal.getBLPrpTitemCar().getArr(0).getFrameNo();
    	strVehicleType = blProposal.getBLPrpTitemCar().getArr(0).getLicenseKindCode();
    	String strEcdemicVehicleFlag = TransCodeCI.getEcdemicVehicleFlag(blProposal.getBLPrpTmain().getArr(0).getComCode(), strCarMark.trim());
    	
    	
    	if (SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+strCarMark.trim()+",") > -1) 
    	
        {
            strNewVehicleFlag = "1";
            
            if(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0,2).equals("01") || blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0,2).equals("07"))
            {}else
            {
            	strVehicleType = "";	
            }
        } else {
            strNewVehicleFlag = "0";
        }
    	strEnrollDate = blProposal.getBLPrpTitemCar().getArr(0).getEnrollDate();
	    strEnrollDate = correctDate(strEnrollDate);
	    ProposalCarShipTaxQueryEncoder proposalCarShipTaxQueryEncoder = new ProposalCarShipTaxQueryEncoder();
	    if(blProposal.getBLPrpTcarshipTax().getSize()>0){
		
		if(new UtiPower().CarCategorySwitch(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
	    		strLicenseCategory = blProposal.getBLPrpTitemCar().getArr(0).getLicenseCategory();
		}else{
		
	    	if(blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2()!=null&&(!"".equals(blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2().trim()))){
	    		strLicenseCategory =  blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2();
    		}else{
                if("A".equals(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxItemCode()))
                	strLicenseCategory =  proposalCarShipTaxQueryEncoder.vehicleStyleEncoder(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxItemDetailCode(),Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount())));
                else
                	strLicenseCategory =  proposalCarShipTaxQueryEncoder.vehicleStyleEncoder(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxItemCode(),Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount())));
            		}
             }
	    }else{
	    	if(new UtiPower().CarCategorySwitch(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
	    		strLicenseCategory = blProposal.getBLPrpTitemCar().getArr(0).getLicenseCategory();
    		}else{
	            if("A".equals(blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode().substring(0,1)))
	            	strLicenseCategory = proposalCarShipTaxQueryEncoder.vehicleStyleEncoder(proposalCarShipTaxQueryEncoder.generateTaxItem(blProposal.getBLPrpTitemCar().getArr(0)),Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount())));
	            else
	            	strLicenseCategory =  proposalCarShipTaxQueryEncoder.vehicleStyleEncoder(proposalCarShipTaxQueryEncoder.generateTaxItem(blProposal.getBLPrpTitemCar().getArr(0)),Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount())));
	    	}
	    }
	    
	    
	    if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+strCarMark.trim()+",") > -1){
	    
      	  strCarMark = "";
       }
	    
		
    	String strSpecialCarFlag = blProposal.getBLPrpTitemCarExt().getArr(0).getNoneFluctuateFlag();
    	
    	String strNoDamageYears= blProposal.getBLPrpTitemCarExt().getArr(0).getNoDamageYears();
    	
    	
    	
    	if(strSpecialCarFlag.trim().equals("N1") ||
    	   strSpecialCarFlag.trim().equals("N2") || 
    	   strSpecialCarFlag.trim().equals("N3") ||
    	   strSpecialCarFlag.trim().equals("N6") ||
    	   strSpecialCarFlag.trim().equals("N7") ||
    	   strSpecialCarFlag.trim().equals("N8"))
    	{
    		strSpecialCarFlag = "";
    	}
    	
    	else if(strSpecialCarFlag.trim().equals("N4"))
    	{
    		strSpecialCarFlag = "1";
    	}
    	
    	else if(strSpecialCarFlag.trim().equals("N5")){
    		
    		strSpecialCarFlag = "2";
    	}else if(strSpecialCarFlag.trim().equals("N9")){
    		
    		strSpecialCarFlag = "3";
    	}else if(strSpecialCarFlag.trim().equals("N10")){
    		
    		strSpecialCarFlag = "4";
    	}else if(strSpecialCarFlag.trim().equals("N11")){
    		
    		strSpecialCarFlag = "5";
    	}
    	
	    
	    strLimitLoadPerson =  blProposal.getBLPrpTitemCar().getArr(0).getSeatCount();
	    DecimalFormat ideciamlFormat = new DecimalFormat("0");
	    strLimitLoad =  blProposal.getBLPrpTitemCar().getArr(0).getTonCount();
    	strLimitLoad = ideciamlFormat.format(Double.parseDouble(ChgData.chgStrZero(strLimitLoad))*1000);
    	strWholeWeight = ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getCompleteKerbMass());
    	fuelType = blProposal.getBLPrpTitemCarExt().getArr(0).getFuelType();   
    	buf.append("<CHECKITEM_CAR>");
		addNode(buf, "CAR_MARK", strCarMark);
		addNode(buf, "VEHICLE_TYPE", strVehicleType);
		addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);
		addNode(buf, "USE_TYPE", encoderUseTypeNew(blProposal));
		addNode(buf, "ENGINE_NO", strEngineNo);
		addNode(buf, "RACK_NO", strRackNo);
		addNode(buf, "NEW_VEHICLE_FLAG", strNewVehicleFlag);
		addNode(buf, "ECDEMIC_VEHICLE_FLAG", strEcdemicVehicleFlag);
		
		addNode(buf, "SPECIAL_CAR_FLAG", strSpecialCarFlag);
		
		addNode(buf, "VEHICLE_REGISTER_DATE", strEnrollDate);
		
		
		addNode(buf, "NO_DAMAGE_YEARS", strNoDamageYears);
		
		addNode(buf, "VEHICLE_STYLE", strLicenseCategory);
		addNode(buf, "LIMIT_LOAD_PERSON", strLimitLoadPerson);
		addNode(buf, "LIMIT_LOAD", strLimitLoad);
		addNode(buf, "WHOLE_WEIGHT", strWholeWeight);
		addNode(buf, "DISPLACEMENT", ""+(new Double(1000*Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getExhaustScale()))).intValue()));
		addNode(buf, "POWER", ""+(new Double(1000*Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getExhaustScale()))).intValue()));
		
		
		addNode(buf, "FUEL_TYPE", fuelType);
		
    	buf.append("</CHECKITEM_CAR>");
    }
    
    
    /**
	 * 添加COVERAGE_DATA
	 * @param buf StringBuffer
	 * @throws Exception
	 */
	protected void addCoverageDate(StringBuffer buf, BLProposal blProposal) throws Exception {
		String strKindCode = "";
		String strCoverageType=""; 
		String strCoverageCode=""; 
		
		String strLimitAmount = ""; 
		String strBasedPremium = ""; 
		String strStandardPremium = ""; 
		String strNonclaimAdjust = ""; 
		String strMulitCoverageAdjust = ""; 
		String strAverageMileAdjust = ""; 
		String strVehicleLossAdjust = ""; 
		String strTotalAdjust = ""; 
		strNonclaimAdjust = "1";
		strMulitCoverageAdjust = "1";
		strAverageMileAdjust = "1";
		strVehicleLossAdjust = "1";
		
		String strRiskCode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
		String strStartDate = blProposal.getBLPrpTmain().getArr(0).getStartDate();
		String strEndDate = blProposal.getBLPrpTmain().getArr(0).getEndDate();
		strStartDate = correctDate(strStartDate);
		strEndDate = correctDate(strEndDate);

		for (int i = 0; i < blProposal.getBLPrpTitemKind().getSize(); i++) {
			strKindCode = blProposal.getBLPrpTitemKind().getArr(i).getKindCode();
			strLimitAmount = blProposal.getBLPrpTitemKind().getArr(i).getAmount(); 
			strBasedPremium = blProposal.getBLPrpTitemKind().getArr(i).getBenchMarkPremium();
			strStandardPremium = blProposal.getBLPrpTitemKind().getArr(i).getPremium();

			for (int m = 0; m < blProposal.getBLPrpTprofitDetail().getSize(); m++) {
				if ("1".equals(blProposal.getBLPrpTprofitDetail().getArr(m).getProfitType())
						&& strKindCode.equals(blProposal.getBLPrpTprofitDetail().getArr(m).getKindCode())) {
					int flagMulitCover = 0;
					int flagAverageMile = 0;
					int flagNonclaim = 0;
					int flagVehicleLoss = 0;
					double dbProfitRate = Double.parseDouble(blProposal.getBLPrpTprofitDetail().getArr(m).getProfitRate());
					if ("C01".equals(blProposal.getBLPrpTprofitDetail().getArr(m).getProfitCode())) {
						flagMulitCover = 1;
					} else if ("C27".equals(blProposal.getBLPrpTprofitDetail().getArr(m).getProfitCode())) {
						flagAverageMile = 1;
					} else if ("C29".equals(blProposal.getBLPrpTprofitDetail().getArr(m).getProfitCode())) {
						flagNonclaim = 1;
					} else if ("C32".equals(blProposal.getBLPrpTprofitDetail().getArr(m).getProfitCode())) {
						flagVehicleLoss = 1;
					}
					
					if (flagMulitCover > 0) {
						
						strMulitCoverageAdjust = new BigDecimal("1").add(new BigDecimal(dbProfitRate+"")).toString();
					} else if (flagAverageMile > 0) {
						
						strAverageMileAdjust = new BigDecimal("1").add(new BigDecimal(dbProfitRate+"")).toString();
					} else if (flagNonclaim > 0) {
						
						strNonclaimAdjust = new BigDecimal("1").add(new BigDecimal(dbProfitRate+"")).toString();
					} else if (flagVehicleLoss > 0) {
						
						strVehicleLossAdjust = new BigDecimal("1").add(new BigDecimal(dbProfitRate+"")).toString();
					}
					
				}
			}
			double dbTotalAdjust = Double.parseDouble(strMulitCoverageAdjust) 
									* Double.parseDouble(strAverageMileAdjust) 
									* Double.parseDouble(strNonclaimAdjust)
									* Double.parseDouble(strVehicleLossAdjust);
			strTotalAdjust = new DecimalFormat("0.0000").format(dbTotalAdjust);

			strCoverageType = encodeCoverageType(strKindCode);
			strCoverageCode = encodeCoverageCodeBJ(strKindCode, strRiskCode);
			buf.append("<COVERAGE_DATA>");
			addNode(buf, "COVERAGE_TYPE", strCoverageType);
			addNode(buf, "COVERAGE_CODE", strCoverageCode);
			addNode(buf, "COM_COVERAGE_CODE", strKindCode);
			addNode(buf, "LIMIT_AMOUNT", strLimitAmount);
			addNode(buf, "START_DATE", strStartDate);
			addNode(buf, "END_DATE", strEndDate);
			addNode(buf, "BASED_PREMIUM", strBasedPremium);
			addNode(buf, "STANDARD_PREMIUM", strStandardPremium);
			addNode(buf, "NONCLAIM_ADJUST", strNonclaimAdjust);
			addNode(buf, "MULTI_COVERAGE_ADJUST", strMulitCoverageAdjust);
			addNode(buf, "AVERAGE_MILE_ADJUST", strAverageMileAdjust);
			addNode(buf, "VEHICLE_LOSS_ADJUST", strVehicleLossAdjust);
			addNode(buf, "TOTAL_ADJUST", strTotalAdjust);
			buf.append("</COVERAGE_DATA>");
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

	/**
	 * 纠正日期格式
	 *
	 * @param dateString
	 *            8个字符的日期
	 * @return YYYYMMDD形式的日期
	 */
	private String correctDate(String dateString) {
		String result = StringUtils.replace(dateString, "-", "");
		return result;
	}

	/**
	 * 纠正日期时间格式
	 *
	 * @param dateString
	 *            修正前的日期时间
	 * @return 修正后的日期时间
	 */
	private String correctDateTime(String dateString) {
		String result = correctDate(dateString);
		result = StringUtils.replace(dateString, " ", "");
		result = StringUtils.replace(dateString, ":", "");
		return result;
	}































































































	
	/**
	 * 获得XX查询主信息
	 * @param blProposal
	 * @throws Exception
	 */
	private void getCIInsureDemand(DbPool dbPool, BLProposal blProposal) 
		throws Exception 
	{
		try 
		{
			
			
							
			
			
			BLCIInsureDemand blciInsureDemand = new BLCIInsureDemand();
			
			String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
			if(!"1".equals(strSwitch)){
			    blciInsureDemand.getData(dbPool, blProposal.getBLPrpTmain().getArr(0).getProposalNo());
			}else{
			  
				ArrayList iWhereValue=new ArrayList();
				iWhereValue.add(blProposal.getBLPrpTitemCar().getArr(0).getDemandNo());
				blciInsureDemand.query(dbPool," DemandNo = ?",iWhereValue,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
				
			}
			
			CIInsureDemandSchema cIInsureDemandSchema = null;
			if(blciInsureDemand.getSize()>0){
				cIInsureDemandSchema = blciInsureDemand.getArr(0);
				BLCIInsureDemand blCIInsureDemand = new BLCIInsureDemand();
				blCIInsureDemand.setArr(cIInsureDemandSchema);
				blProposal.setBLCIInsureDemand(blCIInsureDemand);
			}
			
		}catch (Exception ex) 
		{
			throw ex;
		}
	}

	private String encoderCertiType(BLProposal blProposal,String certiType) throws Exception {
		String appCertiType="";
		int certinum =0;
		for (int i=0;i<blProposal.getBLPrpTinsured().getSize();i++){
			if(blProposal.getBLPrpTinsured().getArr(i).getInsuredFlag().equals(certiType)){
		     certinum= new Integer (blProposal.getBLPrpTinsured().getArr(i).getIdentifyType()).intValue();
		     }
			}
			
			logger.info("---certinum="+certinum);
			
	        switch (certinum) {
	        case 1: {
	        	appCertiType = "1";
	            break;
	        }
	        case 2: {
	        	appCertiType = "99";
	            break;
	        }
	        case 3: {
	        	appCertiType = "2";
	            break;
	        }
	        case 4: {
	        	appCertiType = "3";
	            break;
	        }
	        case 5:
	        {
	        	appCertiType = "99";
	            break;
	        }
	        case 6:
	        {
	        	appCertiType = "99";
	            break;
	        }
	        case 99:
	        {
	        	appCertiType = "99";
	            break;
	        }

	        }
		return appCertiType;
	}
    
    
    /**
     * 被XX人类别代码转换
     *
     * @param 核心业务被XX人与车辆的关系
     *           
     * @return 行业平台XX人类别代码
     * @throws Exception 
     */
    public String encoderInsuredType(BLProposal blProposal) throws Exception{
        if(blProposal.getBLPrpTitemCar().getSize()>0){
            String insuredType=blProposal.getBLPrpTitemCar().getArr(0).getCarInsuredRelation();
            if("1".equals(insuredType))
                return "1";
            else if("2".equals(insuredType))
                return "3";
            else if("3".equals(insuredType)){
                return "2";
            }else{
                return "1";
            }
        }else{
            return "1";
        }
    }
    
    
    
    
    private String encodeCoverageType(String strKindCode) throws Exception{
    	String strCoverageType ="";
    	if("BZ".equals(strKindCode)){
     		 strCoverageType = "1";        
     	 }else if("B".equals(strKindCode)){
     		 strCoverageType = "2";
     	 }else if("A".equals(strKindCode) || "D3".equals(strKindCode) || "D4".equals(strKindCode) || "G1".equals(strKindCode)){
     		 strCoverageType = "3";
     	 }else{
     		 strCoverageType = "9";
     	 }
    	return strCoverageType;
    }

    private String encodeCoverageCodeBJ(String strKindCode, String strRiskCode) throws Exception{
    	String strCoverageCode = "";
    	if("0509".equals(strRiskCode)){
    		if("BZ".equals(strKindCode)){
        		strCoverageCode = "CLIBJ2";
        	 }else if("A".equals(strKindCode)){
        		strCoverageCode = "C11";
        	 }else if("B".equals(strKindCode)){
        		strCoverageCode = "B11";
        	 }else if("G1".equals(strKindCode)){
        		strCoverageCode = "D11";
        	 }else if("D3".equals(strKindCode) || "D4".equals(strKindCode)){
        		strCoverageCode = "E11";
        	 
        	 }else if("Z".equals(strKindCode)){
    		    strCoverageCode = "G11";
    		 
    	     }else{
        		strCoverageCode = "F11";
        	 }
    	}else{
    	     if("BZ".equals(strKindCode)){
    		    strCoverageCode = "CLIBJ2";
    	     }else if("A".equals(strKindCode)){
    		    strCoverageCode = "C01";
    	     }else if("B".equals(strKindCode)){
    		    strCoverageCode = "B01";
    	     }else if("G1".equals(strKindCode)){
    		    strCoverageCode = "D01";
    	     }else if("D3".equals(strKindCode) || "D4".equals(strKindCode)){
    		    strCoverageCode = "E01";
    	     }else if("Z".equals(strKindCode)){
    		    strCoverageCode = "G01";
    	     }else{
    		    strCoverageCode = "F01";
    	     }
    	}
    	return strCoverageCode;
    }


    
    private String encoderUseTypeNew(BLProposal blProposal) throws Exception {
        String strUseType = "";
        String intUseType = "";
        intUseType = blProposal.getBLPrpTitemCar().getArr(0).getUseNatureCode();
        String strCarKindCode = blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode();
        
        if(strCarKindCode.equals("A0")||
           strCarKindCode.equals("A1")||
           strCarKindCode.equals("A2")||
           strCarKindCode.equals("A3")||
           strCarKindCode.equals("A4"))
        {
            if(intUseType.trim().equals("8B"))          
            {
                strUseType = "220";
            }
            else if(intUseType.trim().equals("8E")||intUseType.trim().equals("8F"))
            {
                strUseType = "230";
            }
            else if(intUseType.trim().equals("8A"))     
            {
                strUseType = "210";
            }
            else if(intUseType.trim().equals("9B"))     
            {
                strUseType = "102";
            }
            else if(intUseType.trim().equals("9C"))     
            {
                strUseType = "103";
            }
            else if(intUseType.trim().equals("9F")||intUseType.trim().equals("9G"))     
            {
                strUseType = "101";
            }
            else if(intUseType.trim().equals("86"))     
            {
                strUseType = "100";
            }
            else if(intUseType.trim().equals("87"))     
            {
                strUseType = "101";
            }
            else if(intUseType.trim().equals("9D"))     
            {
                
                
            }
            else if(intUseType.trim().equals("9E"))     
            {
                
                if(PubTools.compareDate(blProposal.getBLPrpTmain().getArr(0).getOperateDate().trim(), "2007-04-13") < 0)
                {
                    strUseType = "102";                 
                }
                else
                {
                    strUseType = "103";                 
                }
                
            }
        }
        
        else if(strCarKindCode.equals("H0")||
                 strCarKindCode.equals("H3")||
                 strCarKindCode.equals("H4")||
                 strCarKindCode.equals("H5")||
                 strCarKindCode.equals("H6")||
                 strCarKindCode.equals("H7")||
                 strCarKindCode.equals("H8")||
                 strCarKindCode.equals("G0"))
        {
            if(intUseType.trim().equals("86") ||    
               intUseType.trim().equals("87") ||    
               intUseType.trim().equals("9D") ||    
               intUseType.trim().equals("9E") ||    
               intUseType.trim().equals("9F") ||    
               intUseType.trim().equals("9G") ||    
               intUseType.trim().equals("9B") ||    
               intUseType.trim().equals("9C")       
               )
            {
                strUseType = "100";     
            }
            else if(
                    intUseType.trim().equals("8B") ||       
                    intUseType.trim().equals("8E") ||       
                    intUseType.trim().equals("8F") ||       
                    intUseType.trim().equals("8D")          
                    )
            {
                strUseType = "200";     
            }

        }
        
        
        else
        {
        	
        	if(new UtiPower().checkSinoCIversion5(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2))){
				if("T".equals(strCarKindCode.substring(0,1))||strCarKindCode.equals("H1")||strCarKindCode.equals("H2")){
					if(intUseType.trim().equals("86") ||    
				   	intUseType.trim().equals("87") ||    
				   	intUseType.trim().equals("9D") ||    
				   	intUseType.trim().equals("9E") ||    
				   	intUseType.trim().equals("9F") ||    
				   	intUseType.trim().equals("9G") ||    
				   	intUseType.trim().equals("9B") ||    
				   	intUseType.trim().equals("9C")       
				   )
				   {
				            strUseType = "100";     
				   }
				 else if(
				   intUseType.trim().equals("8B") ||       
				   intUseType.trim().equals("8E") ||       
				   intUseType.trim().equals("8F") ||       
				   intUseType.trim().equals("8D")          
				   )
				   {
				            strUseType = "200";     
					   }
				}
	    		else{
	    			strUseType = "000";
	    		}
        	}else{
        		strUseType = "000";
        	}
        	
	}

        return strUseType;
    }
    
    
    private String translate(String InsuredTypeCode) throws Exception{
    	String strInsuredTypeCode = InsuredTypeCode;
    	if("01".equals(InsuredTypeCode)){
    		strInsuredTypeCode = "01";
    	}else if("03".equals(InsuredTypeCode)){
    		strInsuredTypeCode = "02";
    	}else if("04".equals(InsuredTypeCode)){
    		strInsuredTypeCode = "03";
    	}else{
    		strInsuredTypeCode = "99";
    	}
    	return strInsuredTypeCode;
    }
    
    
    protected void addCheckItem(StringBuffer buf, BLProposal blProposal)throws Exception {
    	buf.append("<CHECK_ITEM>");
    	addCheckVehicle(buf, blProposal);
    	addDriverList(buf, blProposal);
    	addCheckCoverageList(buf, blProposal);
    	buf.append("</CHECK_ITEM>");
    }
    protected void addVehicle(StringBuffer buf, BLProposal blProposal)throws Exception {
    	String strVehicleCategory 	= ""; 
    	String strVehicleModel 		= ""; 
    	String strLimitLoadPerson =  blProposal.getBLPrpTitemCar().getArr(0).getSeatCount();
    	DecimalFormat ideciamlFormat = new DecimalFormat("0");
    	String strLimitLoad =  blProposal.getBLPrpTitemCar().getArr(0).getTonCount();
    	strLimitLoad = ideciamlFormat.format(Double.parseDouble(ChgData.chgStrZero(strLimitLoad))*1000);
    	String strWholeWeight = ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getCompleteKerbMass());
    	if(new UtiPower().checkStartUp0802(blProposal.getBLPrpTmain().getArr(0).getRiskCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
            strVehicleCategory 	= encoderVehicleCategoryNew(blProposal);
         }
         else{
            strVehicleCategory  = encoderVehicleCategory(blProposal); 
         }
    	strVehicleModel 	= blProposal.getBLPrpTitemCar().getArr(0).getModelCode();
    	
    	if(new UtiPower().savePlatformAdjustSwitchJS(blProposal.getBLPrpTmain().getArr(0).getComCode())){
    		strVehicleModel = blProposal.getBLPrpTitemCar().getArr(0).getBrandName();
        }
    	
    	
    	buf.append("<VEHICLE>");
		addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);
		addNode(buf, "VEHICLE_MODEL", strVehicleModel);
		addNode(buf, "LIMIT_LOAD_PERSON", strLimitLoadPerson);
		addNode(buf, "LIMIT_LOAD", strLimitLoad);
		addNode(buf, "WHOLE_WEIGHT", strWholeWeight);
		addNode(buf, "DISPLACEMENT", ""+(new Double(1000*Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getExhaustScale()))).intValue()));
    	buf.append("</VEHICLE>");
    }
    protected void addCheckVehicle(StringBuffer buf, BLProposal blProposal)throws Exception {
    	String strSendLastPolicyNo = blProposal.getBLPrpTmain().getArr(0).getSendLastPolicyNo();
    	String strCarMark = blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo();
    	String strVehicleType = blProposal.getBLPrpTitemCar().getArr(0).getLicenseKindCode();
    	String strMileages 			= ""; 
    	String strNewVehicleFlag = "";
    	String strUseType 			= ""; 
    	String strEcdemicVehicleFlag = ""; 
    	String businessAgent = "";
    	strEcdemicVehicleFlag = TransCodeCI.getEcdemicVehicleFlag(blProposal.getBLPrpTmain().getArr(0).getComCode(), strCarMark.trim());





		
		if(blProposal.getBLPrpTmain().getArr(0).getRiskCode().equals("0507")){
	           strMileages 	= "" + ((int) Double.parseDouble(blProposal.getBLPrpTitemCar().getArr(0).getRunMiles()));
	    }
		
		
		
		if (SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+strCarMark.trim()+",") > -1)
		
        {
            strNewVehicleFlag = "1";
            
            if(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0,2).equals("01") || blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0,2).equals("07"))
            {
            	
            }
            else
            {
            	
            	
            	strVehicleType = "";	
            }
            
        } else {
            strNewVehicleFlag = "0";
        }
		
    	String strSpecialCarFlag = blProposal.getBLPrpTitemCarExt().getArr(0).getNoneFluctuateFlag();
    	
    	
    	if(strSpecialCarFlag.trim().equals("N1") ||
    	   strSpecialCarFlag.trim().equals("N2") || 
    	   strSpecialCarFlag.trim().equals("N3") ||
    	   strSpecialCarFlag.trim().equals("N6") ||
    	   strSpecialCarFlag.trim().equals("N7") ||
    	   strSpecialCarFlag.trim().equals("N8"))
    	{
    		strSpecialCarFlag = "";
    	}
    	
    	else if(strSpecialCarFlag.trim().equals("N4"))
    	{
    		strSpecialCarFlag = "1";
    	}
    	
    	else if(strSpecialCarFlag.trim().equals("N5")){
    		
    		strSpecialCarFlag = "2";
    	}else if(strSpecialCarFlag.trim().equals("N9")){
    		
    		strSpecialCarFlag = "3";
    	}else if(strSpecialCarFlag.trim().equals("N10")){
    		
    		strSpecialCarFlag = "4";
    	}else if(strSpecialCarFlag.trim().equals("N11")){
    		
    		strSpecialCarFlag = "5";
    	}
    	if("Y".equals(blProposal.getBLPrpTitemCar().getArr(0).getHKFlag())){
    		strEcdemicVehicleFlag = "2";
    		strCarMark = blProposal.getBLPrpTitemCar().getArr(0).getHKLicenseNo();
    	}
    	
    	
    	if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+strCarMark.trim()+",") > -1){
    	
        	  strCarMark = "";
         }
    	String strNum = "0";  
    	if(blProposal.getBLPrpTitemCar().getArr(0).getFrameNo().length()<17){
    		strNum = "1";
    	}
    	String strEnrollDate = blProposal.getBLPrpTitemCar().getArr(0).getEnrollDate();
	    strEnrollDate = correctDate(strEnrollDate);
    	buf.append("<CHECK_VEHICLE>");
    	addNode(buf, "LASTPOLICYNO", "");
    	addNode(buf, "DISTRICT_CODE", "");
    	addNode(buf, "CAR_MARK", strCarMark);
		addNode(buf, "VEHICLE_TYPE", strVehicleType);  
		addNode(buf, "USE_TYPE", encoderUseTypeNew(blProposal));   
		addNode(buf, "ENGINE_NO", blProposal.getBLPrpTitemCar().getArr(0).getEngineNo());     
		addNode(buf, "RACK_NO", blProposal.getBLPrpTitemCar().getArr(0).getFrameNo()); 
		addNode(buf, "USE_AGES", blProposal.getBLPrpTitemCar().getArr(0).getUseYears()); 
		addNode(buf, "MILEAGES", strMileages);  
		addNode(buf, "NEW_VEHICLE_FLAG", strNewVehicleFlag);
		addNode(buf, "ECDEMIC_VEHICLE_FLAG", strEcdemicVehicleFlag);
		addNode(buf, "MADE_FACTORY", blProposal.getBLPrpTitemCar().getArr(0).getMadeFactory());
		addNode(buf, "VEHICLE_BRAND", blProposal.getBLPrpTitemCar().getArr(0).getBrandName());
		addNode(buf, "DRIVER_NUM", "0");
		addNode(buf, "SPECIAL_CAR_FLAG", strSpecialCarFlag);
		addNode(buf, "VEHICLE_REGISTER_DATE", strEnrollDate);
		addNode(buf, "NO_DAMAGE_YEARS", blProposal.getBLPrpTitemCarExt().getArr(0).getNoDamageYears());
		ProposalCarShipTaxQueryEncoder proposalCarShipTaxQueryEncoder = new ProposalCarShipTaxQueryEncoder();
		
		
		if(new UtiPower().savePlatformAdjustSwitchJS(blProposal.getBLPrpTmain().getArr(0).getComCode())
				&&!"M".equals(blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode().substring(0,1))){
			addNode(buf, "VEHICLE_STYLE",blProposal.getBLPrpTitemCar().getArr(0).getLicenseCategory());
		}else
		
		if(blProposal.getBLPrpTcarshipTax().getSize()>0){
        	
        	boolean carShipTaxQGFlag = new UtiPower().checkCICarShipTaxQG(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode());
        	
        	if("M".equals(blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode().substring(0,1)) && !carShipTaxQGFlag){
        		 addNode(buf, "VEHICLE_STYLE", proposalCarShipTaxQueryEncoder.vehicleStyleEncoder(proposalCarShipTaxQueryEncoder.generateTaxItem(blProposal.getBLPrpTitemCar().getArr(0)),Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount()))));
        	}else{
        		if(new UtiPower().CarCategorySwitch(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
        			addNode(buf, "VEHICLE_STYLE",blProposal.getBLPrpTitemCar().getArr(0).getLicenseCategory());
        		}else{
	        		if(blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2()!=null&&(!"".equals(blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2().trim()))){
	                    addNode(buf, "VEHICLE_STYLE", blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2());
	        		}else{
	                    if("A".equals(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxItemCode()))
	                        addNode(buf, "VEHICLE_STYLE", proposalCarShipTaxQueryEncoder.vehicleStyleEncoder(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxItemDetailCode(),Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount()))));
	                    else
	                        addNode(buf, "VEHICLE_STYLE", proposalCarShipTaxQueryEncoder.vehicleStyleEncoder(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxItemCode(),Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount()))));
	                 }
        		}
        	}
        	
        	
        }else{
    		if(new UtiPower().CarCategorySwitch(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    			addNode(buf, "VEHICLE_STYLE",blProposal.getBLPrpTitemCar().getArr(0).getLicenseCategory());
    		}else{
	            if("A".equals(blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode().substring(0,1)))
	                addNode(buf, "VEHICLE_STYLE", proposalCarShipTaxQueryEncoder.vehicleStyleEncoder(proposalCarShipTaxQueryEncoder.generateTaxItem(blProposal.getBLPrpTitemCar().getArr(0)),Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount()))));
	            else
	                addNode(buf, "VEHICLE_STYLE", proposalCarShipTaxQueryEncoder.vehicleStyleEncoder(proposalCarShipTaxQueryEncoder.generateTaxItem(blProposal.getBLPrpTitemCar().getArr(0)),Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount()))));
	    	}
        }
		
		addNode(buf, "POWER", ""+(new Double(1000*Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getExhaustScale()))).intValue()));
		addNode(buf, "RACK_NO_FLAG", strNum);
    	
		if(!"09".equals(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2))
    			&&(SysConfig.getProperty("BYBUSINESSAGENT29").trim()).indexOf(blProposal.getBLPrpTmain().getArr(0).getBusinessNature())>-1){
    		businessAgent = Transfer.getTransfer("SinoCommission", "ByBusinessAgent", blProposal.getBLPrpTmain().getArr(0).getBusinessNature());
    	}
    	else if("09".equals(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2))
    			&&(SysConfig.getProperty("SZBYBUSINESSAGENT09").trim()).indexOf(blProposal.getBLPrpTmain().getArr(0).getBusinessNature())>-1){
			businessAgent = Transfer.getTransfer("SinoCommission", "ByBusinessAgentBISZ", blProposal.getBLPrpTmain().getArr(0).getBusinessNature());
		}
    	
		addNode(buf, "BY_BUSINESS_AGENT", businessAgent);
		addNode(buf, "SUBORDINATE_CODE", blProposal.getBLPrpTmain().getArr(0).getComCode());
		
		if(new UtiPower().savePlatformAdjustSwitchJS(blProposal.getBLPrpTmain().getArr(0).getComCode())){
			String transferDate = correctDate(blProposal.getBLPrpTitemCar().getArr(0).getChgOwnerDate());
			addNode(buf, "TRANSFER_DATE",transferDate);
		}
		
		buf.append("</CHECK_VEHICLE>");
    }
    protected void addDriverList(StringBuffer buf, BLProposal blProposal)throws Exception {
		buf.append("<CHECK_DRIVER_LIST>");
		
		addDriver(buf, blProposal);
		buf.append("</CHECK_DRIVER_LIST>");
    }
    protected void addDriver(StringBuffer buf, BLProposal blProposal)throws Exception {
    	String strLicenseNo 	= ""; 
        String strCertiType 	= ""; 
        String strIsMaster 		= ""; 
        String strArea 			= ""; 
        String strGender 		= ""; 
        String strDriverPeriod 	= ""; 
        String strAge 			= ""; 
        String strDriverType 	= ""; 
        strLicenseNo 			= blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo();
        
        for (int i = 0; i < blProposal.getBLPrpTcarDriver().getSize(); i++) {
		if(blProposal.getBLPrpTcarDriver().getArr(i).getIdentifyNumber()!=null 
				&& !"".equals(blProposal.getBLPrpTcarDriver().getArr(i).getIdentifyNumber())){
			strCertiType = carDriverIdentifyTypeEncoder(blProposal.getBLPrpTcarDriver().getArr(i).getIdentifyType());
			strLicenseNo = blProposal.getBLPrpTcarDriver().getArr(i).getIdentifyNumber();
			strIsMaster = (i==0)?"1":"2";
			strGender = blProposal.getBLPrpTcarDriver().getArr(i).getSex();
			strDriverPeriod = blProposal.getBLPrpTcarDriver().getArr(i).getDrivingYears();
			strAge = blProposal.getBLPrpTcarDriver().getArr(i).getAge();
			strDriverType = blProposal.getBLPrpTcarDriver().getArr(i).getDrivingCarType();
		}
		
    	buf.append("<CHECK_DRIVER>");
    	addNode(buf, "LICENSE_NO", strLicenseNo);
    	addNode(buf, "CERTI_TYPE", strCertiType);
    	addNode(buf, "IS_MASTER", strIsMaster);
    	addNode(buf, "AREA", strArea);
    	addNode(buf, "GENDER", strGender);
    	addNode(buf, "DRIVER_PERIOD", strDriverPeriod);
    	addNode(buf, "AGE", strAge);
    	addNode(buf, "DRIVER_TYPE", strDriverType);
    	buf.append("</CHECK_DRIVER>");
        
        if(blProposal.getBLPrpTcarDriver().getArr(i).getIdentifyNumber()==null 
				|| "".equals(blProposal.getBLPrpTcarDriver().getArr(i).getIdentifyNumber())){
        	break;
        }
        }
        
    }
    protected void addCheckCoverageList(StringBuffer buf, BLProposal blProposal)throws Exception {
		buf.append("<CHECK_COVERAGE_LIST>");
		
		addCoverage(buf, blProposal);
		buf.append("</CHECK_COVERAGE_LIST>");
    }
    protected void addCoverage(StringBuffer buf, BLProposal blProposal)throws Exception {
    	String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
    	String strCoverageType=""; 
    	strCoverageType			= "1"; 
    	String strCoverageCode 	= ""; 
    	String strLimitAmount 	= ""; 
    	String strStartDate = "";
    	String strEndDate = "";
    	String strStartHour		= ""; 
    	String strEndHour		= ""; 
    	String strBillDate			= "";	
    	if(new UtiPower().checkStartUp0802(blProposal.getBLPrpTmain().getArr(0).getRiskCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())) {
        	strLimitAmount 			= AppConfig.get("sysconst.CI0802_INSURED_LIMIT_" + strComCode.substring(0, 2) + "_AMOUNT");
        }
        else {
        	strLimitAmount 			= AppConfig.get("sysconst.CI_INSURED_LIMIT_" + strComCode.substring(0, 2) + "_AMOUNT");
        }
    	
    	strStartDate 			= blProposal.getBLPrpTmain().getArr(0).getStartDate();
        strEndDate 				= blProposal.getBLPrpTmain().getArr(0).getEndDate();
        strStartHour            = blProposal.getBLPrpTmain().getArr(0).getStartHour();
        strEndHour				= blProposal.getBLPrpTmain().getArr(0).getEndHour();
        if("1".equals(blProposal.getBLPrpTmain().getArr(0).getImmeValidFlag())
        		&& new UtiPower().checkImmeValidJS(strComCode,blProposal.getBLPrpTmain().getArr(0).getOperateDate(),blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
        	strStartDate 		= blProposal.getBLPrpTmain().getArr(0).getImmeValidStartDate().substring(0,10);
            strEndDate 			= blProposal.getBLPrpTmain().getArr(0).getImmeValidEndDate().substring(0,10);
            strStartHour        = blProposal.getBLPrpTmain().getArr(0).getImmeValidStartDate().substring(11,13);
            strEndHour          = blProposal.getBLPrpTmain().getArr(0).getImmeValidEndDate().substring(11,13);
        }
        strStartDate            = correctDate(strStartDate);
        strEndDate              = correctDate(strEndDate);
        
        if(strStartHour.length() != 2)
        {
        	if(strStartHour.equals("0") || strStartHour.equals(""))
        	{
        		strStartHour = "00";
        	}
        	else
        	{
        		strStartHour = "0" + strStartHour;
        	}
        }
        
        if(strEndHour.length() != 2)
        {
        	if(strEndHour.equals("0") || strEndHour.equals(""))
        	{
        		strEndHour = "00";
        	}
        	else
        	{
        		strEndHour = "0" + strEndHour;
        	}
        }
        
        strBillDate			= correctDate(blProposal.getBLPrpTmain().getArr(0).getOperateDate());;
    	buf.append("<CHECK_COVERAGE>");
    	addNode(buf, "COVERAGE_TYPE", strCoverageType);
		addNode(buf, "COVERAGE_CODE", strCoverageCode);
		addNode(buf, "LIMIT_AMOUNT", strLimitAmount);
		addNode(buf, "START_DATE", (strStartDate + strStartHour));
    	addNode(buf, "END_DATE", (strEndDate + strEndHour));
		addNode(buf, "BILL_DATE", strBillDate);
    	buf.append("</CHECK_COVERAGE>");
    }
  
    private String encoderVehicleCategoryNew(BLProposal blProposal) 
    	throws Exception 
    {
        String strVehicleCategory = "";
        String strCarKindCode = "";
        double exhaustScale = 0;
        double toncount = 0;
        int seatcount = 0;
        String intUseType = "";
        intUseType = blProposal.getBLPrpTitemCar().getArr(0).getUseNatureCode();
        seatcount = new Integer(blProposal.getBLPrpTitemCar().getArr(0).getSeatCount()).intValue();
        toncount = Double.parseDouble(blProposal.getBLPrpTitemCar().getArr(0).getTonCount());
        exhaustScale = Double.parseDouble(blProposal.getBLPrpTitemCar().getArr(0).getExhaustScale());
        strCarKindCode = blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode();
        
		    String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
		    
        
        if (strCarKindCode.equals("A0")||
            strCarKindCode.equals("A1")||
            strCarKindCode.equals("A2")||
            strCarKindCode.equals("A3")||
            strCarKindCode.equals("A4")) {
            if (seatcount >= 0 && seatcount < 6) {
                strVehicleCategory = "11";
            } else if (seatcount >= 6 && seatcount < 10) {
                strVehicleCategory = "12";
            } else if (seatcount >= 10 && seatcount < 20) {
                strVehicleCategory = "13";
            } else if (seatcount >= 20 && seatcount < 36) {
                strVehicleCategory = "14";
            } else if (seatcount >= 36) {
                strVehicleCategory = "15";
            }
        }
        
        
        
        else if (strCarKindCode.equals("H0")||
                  strCarKindCode.equals("H3")||
                  strCarKindCode.equals("H4")||
                  strCarKindCode.equals("H5")||
                  strCarKindCode.equals("H6")||
                  strCarKindCode.equals("H7")||
                  strCarKindCode.equals("H8"))	
        {
            if (toncount >= 0 && toncount < 2) {
                strVehicleCategory = "21";
            } else if (toncount >= 2 && toncount < 5) {
                strVehicleCategory = "22";
            } else if (toncount >= 5 && toncount < 10) {
                strVehicleCategory = "23";
            } else if (toncount >= 10) {
                strVehicleCategory = "24";
            }

        }
        
        else if (strCarKindCode.equals("H1")||
                  strCarKindCode.equals("H2"))
        {
        	
        	if(new UtiPower().checkCIStartUp1001(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate()))
        	{
        		if(strCarKindCode.equals("H1")){
        			if(exhaustScale <=14.7){
        				strVehicleCategory = "BA";
        			}else if( exhaustScale > 14.7 && exhaustScale <= 17.6){
        				strVehicleCategory = "BB" ;
        			}else if(exhaustScale >17.6 && exhaustScale <= 50){
        				strVehicleCategory = "BC";
        			}else if(exhaustScale >50 && exhaustScale <= 80){
        				strVehicleCategory = "BD";
        			}else if(exhaustScale > 80){
        				strVehicleCategory = "BE";
        			}
        		}else if(strCarKindCode.equals("H2")){
        			if(exhaustScale <= 14.7){
        				strVehicleCategory = "CA";
        			}else if(exhaustScale >14.7 && exhaustScale <=17.6){
        				strVehicleCategory = "CB";
        			}else if(exhaustScale >17.6 && exhaustScale <=50){
        				strVehicleCategory = "CC";
        			}else if(exhaustScale > 50 && exhaustScale <= 80){
        				strVehicleCategory = "CD";
        			}else if(exhaustScale > 80){
        				strVehicleCategory = "CE";
        			}
        		}
        	}else{
            
			      if(SysConfig.getProperty("CI_SINOSOFT_0806").trim().indexOf(
					    strComCode.substring(0, 2).trim()) > -1){
				      if(strCarKindCode.equals("H1")){	
					      strVehicleCategory = "93";
				      }else if(strCarKindCode.equals("H2")){
					      strVehicleCategory = "94";
				      }
			      }else{
				      strVehicleCategory = "92";
			      }
			      
        	}
        	
        } 
        
        else if (strCarKindCode.equals("H11") || 
                strCarKindCode.equals("H12") ||
                strCarKindCode.equals("H21")||
                strCarKindCode.equals("H22"))   
        {
			if(strCarKindCode.equals("H11")){	
				strVehicleCategory = "BF";
			}else if(strCarKindCode.equals("H12")){
				strVehicleCategory = "BG";
			}else if(strCarKindCode.equals("H21")){
				strVehicleCategory = "CF";
			}else if(strCarKindCode.equals("H22")){
				strVehicleCategory = "CG";
			}
        }
        
        
        else if (strCarKindCode.equals("G0"))		
        {
        	if (toncount >= 0 && toncount < 2) {
                strVehicleCategory = "25";
            } else if (toncount >= 2 && toncount < 5) {
                strVehicleCategory = "26";
            } else if (toncount >= 5 && toncount < 10) {
                strVehicleCategory = "27";
            } else if (toncount >= 10) {
                strVehicleCategory = "28";
            }
        }
        
        else if (strCarKindCode.equals("TP") ||
        		  strCarKindCode.equals("TQ") ||
        		  strCarKindCode.equals("TR")){
            strVehicleCategory = "30";
        }
        
        
        else if ( strCarKindCode.equals("T10")||
                   strCarKindCode.equals("T11")||
                   strCarKindCode.equals("T1") ||
        		   strCarKindCode.equals("T2") ||
        		   strCarKindCode.equals("T7") ||
                   strCarKindCode.equals("TD") ||
                   strCarKindCode.equals("TE") ||
                   strCarKindCode.equals("TF") ||
                   strCarKindCode.equals("TG") ||
                   strCarKindCode.equals("TH") ||
                   strCarKindCode.equals("TK") ||
                   strCarKindCode.equals("TS") ||
                   strCarKindCode.equals("T12")||
                   strCarKindCode.equals("TL") ||
                   strCarKindCode.equals("TN")) {
            strVehicleCategory = "40";
        }
        
        
        else if ( strCarKindCode.equals("T8")||
        		   strCarKindCode.equals("T6")||
        		   strCarKindCode.equals("T4")||
        		   strCarKindCode.equals("T5")||
        		   strCarKindCode.equals("T3")||
        		   strCarKindCode.equals("TC")||
        		   strCarKindCode.equals("TO")||
        		   strCarKindCode.equals("TI")||
        		   strCarKindCode.equals("TV")||
        		   strCarKindCode.equals("TW")||
        		   strCarKindCode.equals("TX")||
                   strCarKindCode.equals("TY")) {
            strVehicleCategory = "50";
            
            UtiPower utiPower = new UtiPower();
            if(utiPower.checkStartUp0805(blProposal.getBLPrpTmain().getArr(0).getOperateDate())&&strCarKindCode.equals("T3")){
                strVehicleCategory = "40";
            }
            
        } 
        else if (strCarKindCode.equals("TT")) {
            
            strVehicleCategory = "60";
        } 
        
        
        else if ( strCarKindCode.equals("M1")||
                   strCarKindCode.equals("M5")) {
            
            if (exhaustScale >= 0 && exhaustScale <= 0.05) {
                strVehicleCategory = "71";
            } else if (exhaustScale > 0.05 && exhaustScale <= 0.25) {
                strVehicleCategory = "72";
            } else if (exhaustScale > 0.25) {
                strVehicleCategory = "73";
            }
            
            if("19".equals(strComCode.substring(0, 2))){
            	if(exhaustScale == 0.25){
            		strVehicleCategory = "73";
            	}
            }
            
        }
        
        else if(strCarKindCode.equals("M6"))
        {
            strVehicleCategory = "73";
        }
        
        
        
        else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J2"))&&intUseType.trim().equals("7A")) {   
            if (exhaustScale >= 0 && exhaustScale <= 14.7) {
                strVehicleCategory = "81";
            } else if (exhaustScale > 14.7) {
                strVehicleCategory = "82";
            }
        }
        else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J2"))&&intUseType.trim().equals("7B")) {
            
            if (exhaustScale >= 0 && exhaustScale <= 14.7) {
                strVehicleCategory = "91";
            } else if (exhaustScale > 14.7) {
                strVehicleCategory = "92";
            }
        }
        
        
        
        else if(strCarKindCode.equals("J3")){
        	if(exhaustScale <= 14.7){
        		strVehicleCategory = "9A";
        	}else if(exhaustScale >14.7 && exhaustScale <= 17.6){
        		strVehicleCategory = "9B";
        	}
        }
        
        else if(strCarKindCode.equals("J4")){
        	if(exhaustScale <= 14.7 ){
        		strVehicleCategory = "AA";
        	}else if(exhaustScale >14.7 && exhaustScale<= 17.6){
        		strVehicleCategory = "AB";
        	}else if(exhaustScale > 17.6 && exhaustScale <= 50){
        		strVehicleCategory = "AC";
        	}else if(exhaustScale > 50 && exhaustScale <=80){
        		strVehicleCategory = "AD";
        	}else if(exhaustScale > 80){
        		strVehicleCategory = "AE";
        	}
        }
        
        else if (strCarKindCode.equals("G1"))
        {
            strVehicleCategory = "31";
        }
        
        else if(strCarKindCode.equals("G2"))
        {
            strVehicleCategory = "41";
            
            
        }else if(strCarKindCode.equals("G3"))
        {
            strVehicleCategory = "51";
        }
        
        
        
        /* delete by luogang 20100106 begin reason:按原规则计算*/
        if(new UtiPower().checkCIInsureBJ(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), strComCode)){
        	if(strCarKindCode.equals("T12")){

        	}else if(strCarKindCode.equals("H1")){      
        		strVehicleCategory = "29";
        	}else if(strCarKindCode.equals("TS")){        

        	}
        }
        /*delete by luogang 20100106 end */
       
       
        return strVehicleCategory;
    }
    
    private String encoderVehicleCategory(BLProposal blProposal) 
    throws Exception 
    {
        String strVehicleCategory = "";
        String strCarKindCode = "";
        double exhaustScale = 0;
        double toncount = 0;
        int seatcount = 0;
        String intUseType = "";
        intUseType = blProposal.getBLPrpTitemCar().getArr(0).getUseNatureCode();
        seatcount = new Integer(blProposal.getBLPrpTitemCar().getArr(0).getSeatCount()).intValue();
        toncount = Double.parseDouble(blProposal.getBLPrpTitemCar().getArr(0).getTonCount());
        
        
        
        exhaustScale = Double.parseDouble(blProposal.getBLPrpTitemCar().getArr(0).getExhaustScale());
        strCarKindCode = blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode();
        
		    String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
		    

        if (strCarKindCode.equals("A0")) {
            if (seatcount >= 0 && seatcount < 6) {
                strVehicleCategory = "11";
            } else if (seatcount >= 6 && seatcount < 10) {
                strVehicleCategory = "12";
            } else if (seatcount >= 10 && seatcount < 20) {
                strVehicleCategory = "13";
            } else if (seatcount >= 20 && seatcount < 36) {
                strVehicleCategory = "14";
            } else if (seatcount >= 36) {
                strVehicleCategory = "15";
            }

        }
        else if (strCarKindCode.equals("H0") || 
                 strCarKindCode.equals("H1") ||
                 strCarKindCode.equals("H2"))   
        {
        	  
			      if(SysConfig.getProperty("CI_SINOSOFT_0806").trim().indexOf(
					    strComCode.substring(0, 2).trim()) > -1 && (strCarKindCode.equals("H1")
				    || strCarKindCode.equals("H2"))){
				      if(strCarKindCode.equals("H1")){	
					      strVehicleCategory = "93";
				      }else if(strCarKindCode.equals("H2")){
					      strVehicleCategory = "94";
				      }
			      } else if (toncount >= 0 && toncount < 2) {
                strVehicleCategory = "21";
            } else if (toncount >= 2 && toncount < 5) {
                strVehicleCategory = "22";
            } else if (toncount >= 5 && toncount < 10) {
                strVehicleCategory = "23";
            } else if (toncount >= 10) {
                strVehicleCategory = "24";
            }
            

        }
        else if (strCarKindCode.equals("G0"))       
        {
            if (toncount >= 0 && toncount < 2) {
                strVehicleCategory = "25";
            } else if (toncount >= 2 && toncount < 5) {
                strVehicleCategory = "26";
            } else if (toncount >= 5 && toncount < 10) {
                strVehicleCategory = "27";
            } else if (toncount >= 10) {
                strVehicleCategory = "28";
            }
        }
        else if (strCarKindCode.equals("TP") ||
                 strCarKindCode.equals("TQ") ||
                 strCarKindCode.equals("TR") ||
                 strCarKindCode.equals("TS")) {
            
            strVehicleCategory = "30";
        } else if (strCarKindCode.equals("T1") ||
                   strCarKindCode.equals("T2") ||
                   strCarKindCode.equals("T3") ||
                   strCarKindCode.equals("T5")||
                   strCarKindCode.equals("TE")||
                   strCarKindCode.equals("TF")||
                   strCarKindCode.equals("TG")||
                   strCarKindCode.equals("TH")||
                   strCarKindCode.equals("TI")||
                   strCarKindCode.equals("TJ")||
                   strCarKindCode.equals("TK")||
                   strCarKindCode.equals("TL")||
                   strCarKindCode.equals("TM")||
                   strCarKindCode.equals("TN")||
                   strCarKindCode.equals("TD")||
                   strCarKindCode.equals("T7")) {
            
            
            strVehicleCategory = "40";
        } else if (strCarKindCode.equals("T4") ||
                   strCarKindCode.equals("T6") ||
                   strCarKindCode.equals("T8") ||
                   strCarKindCode.equals("T9") ||
                   strCarKindCode.equals("TO") ||
                   strCarKindCode.equals("TU")||
                   strCarKindCode.equals("TV")||
                   strCarKindCode.equals("TW")||
                   strCarKindCode.equals("TX")||
                   strCarKindCode.equals("TY")||
                   strCarKindCode.equals("TZ")||
                   strCarKindCode.equals("TC")) {
            
            
            strVehicleCategory = "50";
        } else if (strCarKindCode.equals("TT")) {
            
            strVehicleCategory = "60";
        } else if (strCarKindCode.equals("M0")||
                   strCarKindCode.equals("M1")||
                   strCarKindCode.equals("M3")||
                   strCarKindCode.equals("M4")) {
            
            if (exhaustScale >= 0 && exhaustScale <= 0.05) {
                strVehicleCategory = "71";
            } else if (exhaustScale > 0.05 && exhaustScale <= 0.25) {
                strVehicleCategory = "72";
            } else if (exhaustScale > 0.25) {
                strVehicleCategory = "73";
            }
        } else if (strCarKindCode.equals("M2")) {
            
            strVehicleCategory = "73";            
        } else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J1")||strCarKindCode.equals("J2")||strCarKindCode.equals("J3"))&&intUseType.trim().equals("7A")) {
            
            
            if (exhaustScale >= 0 && exhaustScale <= 14.7) {
                strVehicleCategory = "81"; 
            } else if (exhaustScale > 14.7) {
                strVehicleCategory = "82";
            }
        }
        else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J1")||strCarKindCode.equals("J2")||strCarKindCode.equals("J3"))&&intUseType.trim().equals("7B")) {
            
            if (exhaustScale >= 0 && exhaustScale <= 14.7) {
                strVehicleCategory = "91";
            } else if (exhaustScale > 14.7) {
                strVehicleCategory = "92";
            }
        }else if (strCarKindCode.equals("G1"))          
           {
               strVehicleCategory = "31";
           }
        else if(strCarKindCode.equals("G2"))
        {
            strVehicleCategory = "41";
            
            
        }else if(strCarKindCode.equals("G3"))
        {
            strVehicleCategory = "51";
        }
           

        /*
         * switch (seatcount){ case 0: {
         *
         * break; } }
         */

        return strVehicleCategory;
    }
    
	
	public String carDriverIdentifyTypeEncoder(String IdentifyType){
		if("03".equals(IdentifyType)){
			return "02";
		}else if("04".equals(IdentifyType)){
			return "03";
		}else if("02,05,06,07".indexOf(IdentifyType)>-1){
			return "99";
		}
		return IdentifyType;
	}
	
}
