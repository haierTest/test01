package com.sp.indiv.ci.interf;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sp.indiv.ci.blsvr.BLCIMotorcadeDeclare;
import com.sp.platform.bl.facade.BLPrpDagentICCardFacade;
import com.sp.platform.dto.domain.PrpDagentICCardDto;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.pubfun.TransCodeCI;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utiall.blsvr.BLPrpDcodeTransfer;
import com.sp.utiall.dbsvr.DBPrpDagent;
import com.sp.utility.SysConfig;
import com.sp.utility.Transfer;
import com.sp.utility.UtiPower;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;
/**
 * 发送XX查询请求数据的编码器
 *
 */
public class ProposalQueryEncoder {
    
	
	private static Logger logger = Logger.getLogger(ProposalQueryEncoder.class);
	
	
    /**
	 * 
	 *
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
    public String encode(BLProposal blProposal) 
    	throws Exception 
    {
    	
        StringBuffer buf = new StringBuffer(4000);
        addXMLHead(buf);
        addPacket(buf, blProposal);
        
        logger.info("[XX查询发送报文]:"+buf.toString());
        
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
        addNode(buf, "REQUEST_TYPE", "01");
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
        addDriverList(buf, blProposal);
        addCoverageList(buf, blProposal);
        
        addPhList(buf,blProposal);
        addInsuredList(buf,blProposal);
        
        
        addVehicleTaxation(buf, blProposal);
        
        
        if(new UtiPower().checkSinoCommission(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
        	addCommissionAgent(buf, blProposal);
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
    	throws Exception 
    {
        
        String strQuerySequenceNo 	= ""; 
        String strDistrictCode 		= ""; 
        String strCarMark 			= ""; 
        String strVehicleType 		= ""; 
        String strVehicleCategory 	= ""; 
        String strUseType 			= ""; 
        String strEngineNo 			= ""; 
        String strRackNo 			= ""; 
        String strUseAges 			= ""; 
        String strMileages 			= ""; 
        String strNewVehicleFlag 	= ""; 
        String strEcdemicVehicleFlag = ""; 
        String strMadeFactory 		= ""; 
        String strVehicleBrand 		= ""; 
        String strVehicleModel 		= ""; 
        String strDriverNum 		= ""; 
        String strComCode 			= ""; 
        String strRestricFlag       = ""; 
        String strRiskCode          = ""; 
        /*modi by liujia start*/
        /*reason:用于处理大连对为上牌过户车XX特殊处理*/
        String changOwnerFlag       = ""; 
        /*modi by liujia end*/
        
        String strSpecialCarFlag    = "";	
        
        
		String strEnrollDate = ""; 
		String strNoDamageYears = "";
		
		
        String strCarOwner          = "";
        strCarOwner = blProposal.getBLPrpTitemCar().getArr(0).getCarOwner();
        
        UtiPower utiPower = new UtiPower();
        strCarMark = blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo();
        
        strRiskCode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
        
        
        strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
        
        
        if("新车".equals(strCarMark.trim()) || 
           "".equals(strCarMark.trim()))
        {
        	 strCarMark = "新车";
        }
        
        if(utiPower.checkStartUp0802(blProposal.getBLPrpTmain().getArr(0).getRiskCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
           strUseType 			= encoderUseTypeNew(blProposal);
           strVehicleCategory 	= encoderVehicleCategoryNew(blProposal);
        }
        else{
           strUseType          = encoderUseType(blProposal); 
           strVehicleCategory  = encoderVehicleCategory(blProposal); 
        }
        
        strEngineNo 		= blProposal.getBLPrpTitemCar().getArr(0).getEngineNo();
        strUseAges 			= blProposal.getBLPrpTitemCar().getArr(0).getUseYears();
        
        if(strRiskCode.equals("0507")){
           strMileages 	= "" + ((int) Double.parseDouble(blProposal.getBLPrpTitemCar().getArr(0).getRunMiles()));
        }
        
        strVehicleBrand 	= blProposal.getBLPrpTitemCar().getArr(0).getBrandName();
        strVehicleModel 	= blProposal.getBLPrpTitemCar().getArr(0).getModelCode();
        
        
        if(new UtiPower().checkCIInsureSH(strRiskCode, strComCode)||new UtiPower().checkCIInsureBJ(strRiskCode, strComCode)){
        	strVehicleModel = strVehicleBrand;
        	
        	if("0507".equals(strRiskCode)&&"01".equals(strComCode.substring(0,2))){
        		if("M,J".indexOf(blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode().substring(0, 1)) < 0){
        			if("1".equals(new PubTools().checkEcdemicVehicleFlag(blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo()))
        					|| "新".equals(blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo().substring(0, 1))){
        				strVehicleModel = blProposal.getBLPrpTitemCarExt().getArr(0).getBrandECode();
        			}
        		}
        	}
        	
        	
        	if("0507".equals(strRiskCode)&&"07".equals(strComCode.substring(0,2)) && !"".equals(strVehicleBrand) && strVehicleBrand != null){
        		String iVehicleBrand = "";
        		try{
        			iVehicleBrand = Transfer.getTransfer("VehicleCodeSH", "VehicleCode", strVehicleBrand);
        		}catch (Exception e){
        			
        			e.printStackTrace();
        		}
        		if(!"".equals(iVehicleBrand) && iVehicleBrand != null){
        			strVehicleBrand = iVehicleBrand;
        			strVehicleModel = strVehicleBrand;
        		}
        	}
        	
        }
        
        
    	if(new UtiPower().checkCarShipTaxCuts_EnergySaving_JZ(strComCode,blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    		strVehicleModel = strVehicleBrand;
    	}
    	
        
        if(utiPower.savePlatformAdjustSwitchJS(strComCode) && "0507".equals(strRiskCode)){
        	String strPmModelCode = blProposal.getBLPrpTitemCar().getArr(0).getBrandName();
        	if (strPmModelCode!=null&&!"".equals(strPmModelCode)) {
        		strVehicleModel = strPmModelCode;
        	}
        }
        
        
        strVehicleType 		= blProposal.getBLPrpTitemCar().getArr(0).getLicenseKindCode();
        strDriverNum 		= "0";
        strRackNo 			= blProposal.getBLPrpTitemCar().getArr(0).getFrameNo();
        
        strRestricFlag      = blProposal.getBLPrpTitemCar().getArr(0).getRestricFlag();
        
        
        if(strRestricFlag!=null && strRestricFlag.trim().equals("00")){
        	strRestricFlag = "";
        }
        
        
		    strEnrollDate = blProposal.getBLPrpTitemCar().getArr(0).getEnrollDate();
		    strEnrollDate = correctDate(strEnrollDate);
		    
        
        
        
		
		strMadeFactory = blProposal.getBLPrpTitemCar().getArr(0).getMadeFactory();
		
        
		
        
		if (SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+strCarMark.trim()+",") > -1)
		
        {
            strNewVehicleFlag = "1";
            
            if(strComCode.substring(0,2).equals("01") || strComCode.substring(0,2).equals("07"))
            {
            	
            }
            else
            {
            	
            	
            	strVehicleType = "";	
            }
            
        } else {
            strNewVehicleFlag = "0";
        }
        
        

























































































































































        
        	strEcdemicVehicleFlag = TransCodeCI.getEcdemicVehicleFlag(strComCode, strCarMark.trim());
        	  /*  大连机构特殊处理
        	   *  reason:因大连交警要求，对于过户未上牌车辆需要在车牌号码域中“过户”字样，需要处理成空传给平台！
               *  大连车辆过户时车牌号码需填写如下信息.1、外地转入； 2、本地转出；3、本地过户； 4、过户车辆。
               *  将车牌号码为以上四种情况的，处理为空，送给平台。处理方法与“新车”相同。
               */
            if( strComCode.substring(0,2).equals("31") ){
              if("外地转入".equals(strCarMark.trim())||"本地转出".equals(strCarMark.trim())||
                 "本地过户".equals(strCarMark.trim())||"过户车辆".equals(strCarMark.trim())){
                	 strNewVehicleFlag = "1";
                     strVehicleType = "";
                     changOwnerFlag="1";
               }
        	}
           
           
           
            if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+strCarMark.trim()+",") > -1||changOwnerFlag.equals("1")){
           
          	  strCarMark = "";
           }
        
        
        if(strComCode.substring(0, 2).equals("01") || 
           strComCode.substring(0, 2).equals("07"))
        {
        	
        }
        else
        {
        	
        	strSpecialCarFlag = blProposal.getBLPrpTitemCar().getArr(0).getNewDeviceFlag();
        	
        	
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
        	/*
        	
        	else if(strSpecialCarFlag.trim().equals("N5"))
        	{
        		strSpecialCarFlag = "2";
        	}*/
        	
        	
        	
        	if(changOwnerFlag.equals("1"))
        	{
        		strSpecialCarFlag = "1";
        	}
        	
        	
        	if("Y".equals(blProposal.getBLPrpTitemCar().getArr(0).getHKFlag())){
        		strEcdemicVehicleFlag = "2";
        		strCarMark = blProposal.getBLPrpTitemCar().getArr(0).getHKLicenseNo();
        	}
        	
        }
        
        


        buf.append("<BASE_PART>");
        
        addNode(buf, "QUERY_SEQUENCE_NO", strQuerySequenceNo);
        if(utiPower.checkComCheckCode(strComCode)){
            	addNode(buf, "CHECK_TYPE", "1");
        }
        
        
        if(utiPower.sendLastPolicyNoSwitch(strComCode)){ 
        	String strSendLastPolicyNo = blProposal.getBLPrpTmain().getArr(0).getSendLastPolicyNo();
        	if(!"".equals(strSendLastPolicyNo)){
        		addNode(buf, "LASTPOLICYNO", strSendLastPolicyNo);
        	}
        }
        
        addNode(buf, "DISTRICT_CODE", strDistrictCode);
        addNode(buf, "CAR_MARK", strCarMark);
        addNode(buf, "VEHICLE_TYPE", strVehicleType);
        addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);
        addNode(buf, "USE_TYPE", strUseType);
        addNode(buf, "ENGINE_NO", strEngineNo);
        addNode(buf, "RACK_NO", strRackNo);
        addNode(buf, "USE_AGES", strUseAges);
        addNode(buf, "MILEAGES", strMileages);
        addNode(buf, "NEW_VEHICLE_FLAG", strNewVehicleFlag);
        addNode(buf, "ECDEMIC_VEHICLE_FLAG", strEcdemicVehicleFlag);
        addNode(buf, "MADE_FACTORY", strMadeFactory);
        addNode(buf, "VEHICLE_BRAND", strVehicleBrand);
        addNode(buf, "VEHICLE_MODEL", strVehicleModel);
        addNode(buf, "DRIVER_NUM", strDriverNum);
        
        if(strComCode.substring(0, 2).equals("01") && utiPower.checkCIInsurePrintUploadBJ(blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
        	String strBusinessNature = blProposal.getBLPrpTmain().getArr(0).getBusinessNature();
        	addNode(buf, "BUSINESS_CHANNEL", TransCodeCI.getTransferCI(strComCode, "BusinessChannel", strBusinessNature));
        	String strAgentCode = blProposal.getBLPrpTmain().getArr(0).getAgentCode();
        	if(strAgentCode!=null&&!strAgentCode.equals("")){
        		DBPrpDagent dbPrpDagent = new DBPrpDagent();
        	    if(dbPrpDagent.getInfo(strAgentCode)==0){
        	    	addNode(buf, "AGENCY_NAME", dbPrpDagent.getAgentName());
                	addNode(buf, "AGENCY_CODE", dbPrpDagent.getPermitNo());
        	    }
        	}
        	if(blProposal.getBLPrpTmain().getArr(0).getComLevel().equals("8")){
        		BLPrpDagentICCardFacade blPrpDagentICCardFacade = new BLPrpDagentICCardFacade();
        		PrpDagentICCardDto prpDagentICCardDto = blPrpDagentICCardFacade.getICInfoByAgentCode(strAgentCode);
        		String strUsbKey = blProposal.getBLPrpTmain().getArr(0).getUsbKey();
        		if(prpDagentICCardDto!=null&&prpDagentICCardDto.getCardNo().equals(strUsbKey)){
        			addNode(buf, "USBKEY", blProposal.getBLPrpTmain().getArr(0).getUsbKey());
        		}else{
        			throw new Exception("USBKEY证书不正确或未维护!");
        		}
        	}else{
        		addNode(buf, "COMPUTER_IP", blProposal.getBLPrpTmain().getArr(0).getIP());
        	}
        }
        
        
        if(strComCode.substring(0, 2).equals("01") || 
                strComCode.substring(0, 2).equals("07"))
        {
        	
        }
        else
        {
        	addNode(buf, "SPECIAL_CAR_FLAG", strSpecialCarFlag);
        }
        
        
		    if (SysConfig.getProperty("CI_SINOSOFT_0806").trim().indexOf(
				  strComCode.substring(0, 2).trim()) > -1) {
			    addNode(buf, "VEHICLE_REGISTER_DATE", strEnrollDate);
			    
			    strNoDamageYears= blProposal.getBLPrpTitemCarExt().getArr(0).getNoDamageYears();
			    
			    addNode(buf, "NO_DAMAGE_YEARS", strNoDamageYears);
		    }
		
        
        ProposalCarShipTaxQueryEncoder proposalCarShipTaxQueryEncoder = new ProposalCarShipTaxQueryEncoder();
        
        proposalCarShipTaxQueryEncoder.encode(blProposal,buf,"BASE_PART");
        
        
        
        if(new UtiPower().checkCIInsureSH(strRiskCode, strComCode)){
        	
            String strMadeDate = "";
            String strRegisterDate = blProposal.getBLPrpTitemCar().getArr(0).getEnrollDate();
            String strLicenseCategory = blProposal.getBLPrpTitemCar().getArr(0).getLicenseCategory();
            BLPrpDcode blPrpDcode = new BLPrpDcode();
            String strVehicleStyleDesc = blPrpDcode.translateCode("LicenseCategory",strLicenseCategory,true);
            String strLimitLoad =  blProposal.getBLPrpTitemCar().getArr(0).getTonCount();
            String strLimitLoadPerson =  blProposal.getBLPrpTitemCar().getArr(0).getSeatCount();
            String strExhaustCapacity =  blProposal.getBLPrpTitemCar().getArr(0).getExhaustScale();
            
            
            String businessNature = blProposal.getBLPrpTmain().getArr(0).getBusinessNature();
            String comCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
            String agentCode = blProposal.getBLPrpTmain().getArr(0).getAgentCode();
            String strSalesChannelCode = new SaleChannelHandle().returnSaleChannelCode(businessNature, comCode, agentCode);
            
            
            String strSearchSequenceNo = blProposal.getBLPrpTitemCar().getArr(0).getSearchSequenceNo();
        	addNode(buf, "OWNER", strCarOwner);
        	addNode(buf, "MADE_DATE", strMadeDate);
        	addNode(buf, "REGISTER_DATE", correctDate(strRegisterDate));
        	if("0507".equals(strRiskCode)){
        		strVehicleStyleDesc = blPrpDcode.translateCode("LicenseCategory",blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2(),true);
        	}
        	addNode(buf, "VEHICLE_STYLE_DESC", strVehicleStyleDesc);
        	addNode(buf, "LIMIT_LOAD", strLimitLoad);
        	addNode(buf, "LIMIT_LOAD_PERSON", strLimitLoadPerson);
        	addNode(buf, "EXHAUST_CAPACITY", strExhaustCapacity);
        	addNode(buf, "SALES_CHANNEL_CODE", strSalesChannelCode);
        	addNode(buf, "SEARCH_SEQUENCE_NO", strSearchSequenceNo);
        	
        	
        	addNode(buf, "SALES_CHANNEL_REFER_CODE", "");
        	
        	
        	String operateDate=blProposal.getBLPrpTmain().getArr(0).getInputDate();
        	String contractNo=blProposal.getBLPrpTmain().getArr(0).getContractNo();
        	String groupCode="";
        	if(contractNo!=null&&!"".equals(contractNo)){
	        	BLCIMotorcadeDeclare  blCIMotorcadeDeclare=new BLCIMotorcadeDeclare(); 
	        	if(new UtiPower().checkSHDeclare(strComCode,strRiskCode,operateDate)){
	        		blCIMotorcadeDeclare.query(" contractNo='"+contractNo+"'");
	        		if(blCIMotorcadeDeclare.getSize()>0){
	        			groupCode=blCIMotorcadeDeclare.getArr(0).getGroupCode();
	        		}
	        	}
        	}
        	addNode(buf, "GROUP_CODE", groupCode);
        	
        }
        
        
        if(new UtiPower().checkCIInsureBJ(strRiskCode, strComCode)){
        	BLPrpDcode blPrpDcode = new BLPrpDcode();
        	String strLicenseCategory = blProposal.getBLPrpTitemCar().getArr(0).getLicenseCategory();
        	String strVehicleStyleDesc = blPrpDcode.translateCode("LicenseCategoryBJ",strLicenseCategory,true);
        	String strLimitLoadPerson =  blProposal.getBLPrpTitemCar().getArr(0).getSeatCount();
        	String strExhaustCapacity =  blProposal.getBLPrpTitemCar().getArr(0).getExhaustScale();
        	String strMakeDate =  blProposal.getBLPrpTitemCar().getArr(0).getMakeDate();
        	
        	DecimalFormat ideciamlFormat = new DecimalFormat("0");
        	if(!"".equals(strExhaustCapacity)){
    			
        		strExhaustCapacity = ideciamlFormat.format(Double.parseDouble(ChgData.chgStrZero(strExhaustCapacity))*1000);
    		}
        	String strLimitLoad =  blProposal.getBLPrpTitemCar().getArr(0).getTonCount();
        	strLimitLoad = ideciamlFormat.format(Double.parseDouble(ChgData.chgStrZero(strLimitLoad))*1000);
        	String strSearchSequenceNo = blProposal.getBLPrpTitemCar().getArr(0).getSearchSequenceNo();
        	String CompleteKerbMass=blProposal.getBLPrpTitemCar().getArr(0).getCompleteKerbMass();
        	if(!strRiskCode.equals("0507")){
        		addNode(buf, "PC_VEHICLE_CATEGORY", strLicenseCategory);  
        		addNode(buf,"OWNER",strCarOwner);     
        		addNode(buf, "LIMIT_LOAD_PERSON", strLimitLoadPerson);  
        		addNode(buf,"PO_WEIGHT",CompleteKerbMass);     
        		addNode(buf, "EXHAUST_CAPACITY", strExhaustCapacity);  
        		addNode(buf, "VEHICLE_REGISTER_DATE", strEnrollDate);  
        		addNode(buf,"CERTIFICATE_DATE","");      
        		addNode(buf,"POLICY_FLAG","02");   
        		addNode(buf,"MADE_DATE",correctDate(strMakeDate));    
        		addNode(buf, "VEHICLE_STYLE_DESC", strVehicleStyleDesc);  
        		addNode(buf, "LIMIT_LOAD", strLimitLoad);           
        		addNode(buf, "SEARCH_SEQUENCE_NO", strSearchSequenceNo);  
         	}else{
         		addNode(buf,"POLICY_FLAG","01");   
        	}
        }
        

        
        
        if(strComCode.substring(0, 2).equals("01")){
        addNode(buf, "RESTRIC_FLAG", strRestricFlag);  
        }
        
        
        
        if(utiPower.checkSinoCommission(strComCode, blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
        	String businessAgent = "";
        	String salesChannel = Transfer.getTransfer("SinoCommission", "BusinessNature", blProposal.getBLPrpTmain().getArr(0).getBusinessNature());
        	if(!"09".equals(strComCode.substring(0, 2))&&(SysConfig.getProperty("BYBUSINESSAGENT29").trim()).indexOf(blProposal.getBLPrpTmain().getArr(0).getBusinessNature())>-1){
        		businessAgent = Transfer.getTransfer("SinoCommission", "ByBusinessAgent", blProposal.getBLPrpTmain().getArr(0).getBusinessNature());
        	}
        	else if("09".equals(strComCode.substring(0, 2))&&(SysConfig.getProperty("SZBYBUSINESSAGENT09").trim()).indexOf(blProposal.getBLPrpTmain().getArr(0).getBusinessNature())>-1){
        		businessAgent = Transfer.getTransfer("SinoCommission", "ByBusinessAgentBISZ", blProposal.getBLPrpTmain().getArr(0).getBusinessNature());
        	}
        	addNode(buf, "SALES_CHANNEL", salesChannel);
        	addNode(buf, "BY_BUSINESS_AGENT", businessAgent);
        	addNode(buf, "SUBORDINATE_CODE", strComCode);
        }
        
        
        if(utiPower.checkSinoCarPlatformSZ(strComCode, blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
        	String strNum = "0";
        	if(blProposal.getBLPrpTitemCar().getArr(0).getFrameNo().length()<17){
        		strNum = "1";
        	}
        	addNode(buf, "RACK_NO_FLAG", strNum);
        }
        
        
        
        String currentDate=new ChgDate().getCurrentTime("yyyy-MM-dd");
        
        
        if((new UtiPower().checkCarShipTaxBJ(strComCode, currentDate)||new UtiPower().addFuelTypeCom(strComCode)) && "0507".equals(strRiskCode)){
        
        	String fuelType = blProposal.getBLPrpTitemCarExt().getArr(0).getFuelType();        	
        	String OperatorCode = blProposal.getBLPrpTmain().getArr(0).getOperatorCode();
        	String CarOwnerIdentifyType = translate(blProposal.getBLPrpTitemCar().getArr(0).getInsuredTypeCode());
        	String CarOwnerIdentifyNumber = blProposal.getBLPrpTitemCar().getArr(0).getOwnerAddress();
        	String InsuredName = "";
        	for (int i = 0; i < blProposal.getBLPrpTinsured().getSize(); i++) {
				if("1".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredFlag())){
					InsuredName=blProposal.getBLPrpTinsured().getArr(i).getInsuredName();
					break;
				}
        	}
        	addNode(buf, "FUEL_TYPE", fuelType);  
        	addNode(buf, "AGENT_NAME", OperatorCode);  
        	addNode(buf, "CERTI_TYPE", CarOwnerIdentifyType);  
        	addNode(buf, "CERTI_CODE", CarOwnerIdentifyNumber);  
        	addNode(buf, "INSURED_NAME", InsuredName);  
        }
        
        
        if (utiPower.savePlatformAdjustSwitchJS(strComCode) && "0507".equals(strRiskCode)) {
        	String strChgOwnerDate = blProposal.getBLPrpTitemCar().getArr(0).getChgOwnerDate();
        	addNode(buf, "TRANSFER_DATE", correctDate(strChgOwnerDate));
        }
        
        buf.append("</BASE_PART>");
    }

    /**
	 * 添加DRIVER_LIST节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addDriverList(StringBuffer buf, BLProposal blProposal) throws Exception {
        buf.append("<DRIVER_LIST>");
        addDriver(buf, blProposal);
        buf.append("</DRIVER_LIST>");
    }

    /**
	 * 添加DRIVER节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addDriver(StringBuffer buf, BLProposal blProposal) throws Exception {
        
        
        
        
        
        
        
        
        
        
        
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
		
        
        buf.append("<DRIVER>");
        addNode(buf, "LICENSE_NO", strLicenseNo);
        addNode(buf, "CERTI_TYPE", strCertiType);
        addNode(buf, "IS_MASTER", strIsMaster);
        addNode(buf, "AREA", strArea);
        addNode(buf, "GENDER", strGender);
        addNode(buf, "DRIVER_PERIOD", strDriverPeriod);
        addNode(buf, "AGE", strAge);
        addNode(buf, "DRIVER_TYPE", strDriverType);
        buf.append("</DRIVER>");
        
        if(blProposal.getBLPrpTcarDriver().getArr(i).getIdentifyNumber()==null 
				|| "".equals(blProposal.getBLPrpTcarDriver().getArr(i).getIdentifyNumber())){
        	break;
        }
        }
        
    }

    /**
	 * 添加COVERAGE_LIST节
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
        
        
        
        
        
        
        
    	UtiPower utiPower = new UtiPower();
    	String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
        String strCoverageType 	= ""; 
        String strCoverageCode 	= ""; 
        String strLimitAmount 	= ""; 
        String strStartDate 	= ""; 
        
        String strStartHour		= ""; 
        String strEndHour		= ""; 
        String strEndDate 		= ""; 
        
        
        String strDateHour      = "";
        strStartHour            = blProposal.getBLPrpTmain().getArr(0).getStartHour();
        strEndHour				= blProposal.getBLPrpTmain().getArr(0).getEndHour();
        if("0507".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())&&"1".equals(blProposal.getBLPrpTmain().getArr(0).getImmeValidFlag())
        		&&utiPower.checkIntoEffect(strComCode)){
        	strStartDate 			= blProposal.getBLPrpTmain().getArr(0).getImmeValidStartDate().substring(0,10);
            strEndDate 				= blProposal.getBLPrpTmain().getArr(0).getImmeValidEndDate().substring(0,10);
            strDateHour        = blProposal.getBLPrpTmain().getArr(0).getImmeValidStartDate().substring(11,13);
        }else if("1".equals(blProposal.getBLPrpTmain().getArr(0).getImmeValidFlag())
        		&& utiPower.checkImmeValidJS(strComCode,blProposal.getBLPrpTmain().getArr(0).getOperateDate(),blProposal.getBLPrpTmain().getArr(0).getRiskCode())){
        	strStartDate 			= blProposal.getBLPrpTmain().getArr(0).getImmeValidStartDate().substring(0,10);
            strEndDate 				= blProposal.getBLPrpTmain().getArr(0).getImmeValidEndDate().substring(0,10);
            strStartHour 			= blProposal.getBLPrpTmain().getArr(0).getImmeValidStartDate().substring(11,13);
            strEndHour 				= blProposal.getBLPrpTmain().getArr(0).getImmeValidEndDate().substring(11,13);
        }else {
        	strStartDate 			= blProposal.getBLPrpTmain().getArr(0).getStartDate();
            strEndDate 				= blProposal.getBLPrpTmain().getArr(0).getEndDate();
		}
        
        
        
        String strBillDate			= "";	
        
         
        
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
        
        
        if(utiPower.checkStartUp0802(blProposal.getBLPrpTmain().getArr(0).getRiskCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())) {
        	strLimitAmount 			= AppConfig.get("sysconst.CI0802_INSURED_LIMIT_" + strComCode.substring(0, 2) + "_AMOUNT");
        }
        else {
        	strLimitAmount 			= AppConfig.get("sysconst.CI_INSURED_LIMIT_" + strComCode.substring(0, 2) + "_AMOUNT");
        }
        
        strCoverageType			= "1"; 
        strStartDate			= correctDate(strStartDate);
        strEndDate 				= correctDate(strEndDate);
        
        
        if(strComCode.substring(0, 2).equals("01") || 
           strComCode.substring(0, 2).equals("07"))
        {
        	
        }
        else
        {
        	
        	
        	
        	strBillDate			= correctDate(blProposal.getBLPrpTitemCar().getArr(0).getMakeDate());
        	
        }
        
        
        
        if(new UtiPower().checkCIInsureBJ(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), strComCode)){
        	if(blProposal.getBLPrpTmain().getArr(0).getRiskCode().equals("0507")){
        		strCoverageType=encodeCoverageType(blProposal.getBLPrpTitemKind().getArr(0).getKindCode());
                strCoverageCode=encodeCoverageCodeBJ(blProposal.getBLPrpTitemKind().getArr(0).getKindCode(),
                										blProposal.getBLPrpTmain().getArr(0).getRiskCode());
        		buf.append("<COVERAGE>");
        	    addNode(buf, "COVERAGE_TYPE", strCoverageType);
        	    addNode(buf, "COVERAGE_CODE", strCoverageCode);
        	    addNode(buf, "LIMIT_AMOUNT", strLimitAmount);
        	    addNode(buf, "START_DATE", strStartDate);
        	    addNode(buf, "END_DATE", strEndDate);
        	    buf.append("</COVERAGE>");
        	}
        }else if(new UtiPower().checkCIInsureSH(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), strComCode)){
        	for(int i =0;i<blProposal.getBLPrpTitemKind().getSize();i++){
            	String strKindCode = blProposal.getBLPrpTitemKind().getArr(i).getKindCode();
            	String strRiskCode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
            	String strPremium = blProposal.getBLPrpTitemKind().getArr(i).getPremium();
            	String strAmount   = blProposal.getBLPrpTitemKind().getArr(i).getAmount();  
            	strCoverageType=encodeCoverageType(strKindCode);
            	strCoverageCode=encodeCoverageCode(strKindCode,strRiskCode);
            	buf.append("<COVERAGE>");
            	addNode(buf, "COVERAGE_TYPE", strCoverageType);
            	addNode(buf, "COVERAGE_CODE", strCoverageCode);
            	addNode(buf, "COM_COVERAGE_CODE", strKindCode);
            	if("0507".equals(strRiskCode)){
            		addNode(buf, "LIMIT_AMOUNT", strLimitAmount);
            	}else{
            	    addNode(buf, "LIMIT_AMOUNT", strAmount);
            	}
            	addNode(buf, "START_DATE", strStartDate);
            	addNode(buf, "END_DATE", strEndDate);
            	
            	addNode(buf, "BASED_PREMIUM", blProposal.getBLPrpTitemKind().getArr(i).getBenchMarkPremium());
            	
            	addNode(buf, "STANDARD_PREMIUM", strPremium);
            	buf.append("</COVERAGE>");
        	}
        }else{
        	buf.append("<COVERAGE>");
            addNode(buf, "COVERAGE_TYPE", strCoverageType);
            addNode(buf, "COVERAGE_CODE", strCoverageCode);
            addNode(buf, "LIMIT_AMOUNT", strLimitAmount);
            
            if("0507".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())
            		&&"1".equals(blProposal.getBLPrpTmain().getArr(0).getImmeValidFlag())
            		&&utiPower.checkIntoEffect(strComCode)){
            	addNode(buf, "START_DATE", (strStartDate+strDateHour));
            	addNode(buf, "END_DATE", (strEndDate+strDateHour));
            }else {
            	addNode(buf, "START_DATE", (strStartDate + strStartHour));
            	addNode(buf, "END_DATE", (strEndDate + strEndHour));
			}
            
  	        addNode(buf, "BILL_DATE", strBillDate);
  	        
  	        if(new UtiPower().checkSinoCIversion3(strComCode,blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
  	        	addNode(buf, "AREA_FLAG", PubTools.getAreaFlag(strComCode));
  	        }
  	        
            buf.append("</COVERAGE>");
        }
        
        /**
        if(!new UtiPower().checkCIInsureSH(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), strComCode)){                
        buf.append("<COVERAGE>");
        addNode(buf, "COVERAGE_TYPE", strCoverageType);
        addNode(buf, "COVERAGE_CODE", strCoverageCode);
        addNode(buf, "LIMIT_AMOUNT", strLimitAmount);
        
        if(strComCode.substring(0,2).equals("01") || 
        		strComCode.substring(0,2).equals("07"))
        {
        	addNode(buf, "START_DATE", strStartDate);
        	addNode(buf, "END_DATE", strEndDate);
        }
        else 
        {
        	
        	addNode(buf, "START_DATE", (strStartDate + strStartHour));
        	addNode(buf, "END_DATE", (strEndDate + strEndHour));
        }
        
        
        
        if(strComCode.substring(0, 2).equals("01") || 
                strComCode.substring(0, 2).equals("07"))
        {
        	
        }
        else
        {
        	addNode(buf, "BILL_DATE", strBillDate);
        }
        
        buf.append("</COVERAGE>");

        }else{                                                     
          for(int i =0;i<blProposal.getBLPrpTitemKind().getSize();i++){
        	String strKindCode = blProposal.getBLPrpTitemKind().getArr(i).getKindCode();
        	String strRiskCode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
        	
        	
        	String strPremium = blProposal.getBLPrpTitemKind().getArr(i).getPremium();
        	
        	String strAmount   = blProposal.getBLPrpTitemKind().getArr(i).getAmount();  
        	strCoverageType=encodeCoverageType(strKindCode);
        	strCoverageCode=encodeCoverageCode(strKindCode,strRiskCode);
        	buf.append("<COVERAGE>");
        	addNode(buf, "COVERAGE_TYPE", strCoverageType);
        	addNode(buf, "COVERAGE_CODE", strCoverageCode);
        	addNode(buf, "COM_COVERAGE_CODE", strKindCode);
        	if("0507".equals(strRiskCode)){
        		addNode(buf, "LIMIT_AMOUNT", strLimitAmount);
        	}else{
        	    addNode(buf, "LIMIT_AMOUNT", strAmount);
        	}
        	addNode(buf, "START_DATE", strStartDate);
        	addNode(buf, "END_DATE", strEndDate);
        	
        	addNode(buf, "BASED_PREMIUM", blProposal.getBLPrpTitemKind().getArr(i).getBasePremium());
        	
        	
        	
        	addNode(buf, "STANDARD_PREMIUM", strPremium);
        	
        	buf.append("</COVERAGE>");
          }
        }
        
         * 
         */
    }

    
    public void addVehicleTaxation(StringBuffer buf, BLProposal blProposal) throws Exception {
        ProposalCarShipTaxQueryEncoder proposalCarShipTaxQueryEncoder = new ProposalCarShipTaxQueryEncoder();
        proposalCarShipTaxQueryEncoder.encode(blProposal,buf,"VehicleTaxation");
    }
    
    
    public void addCommissionAgent(StringBuffer buf, BLProposal blProposal) throws Exception{
    	buf.append("<COMMISSION_AGENT>");
    	String salesChannel = Transfer.getTransfer("SinoCommission", "BusinessNature", blProposal.getBLPrpTmain().getArr(0).getBusinessNature());
		String strAgentCode = blProposal.getBLPrpTmain().getArr(0).getAgentCode();
    	if("51,52,53,54".indexOf(salesChannel)>-1 
    			&& !("M".indexOf(blProposal.getBLPrpTitemCar().getArr(0).getMainCarKindCode())>-1) 
				&& strAgentCode!=null && !strAgentCode.equals("")){
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
    		addNode(buf, "CERTIFICATE_NO","");
    		addNode(buf, "INDIVIDUAL_PRODUCER_CODE","");
    	}
    	buf.append("</COMMISSION_AGENT>");
    }
    
    
    public void addPhList(StringBuffer buf,BLProposal blProposal)throws Exception{
    	String operateDate=blProposal.getBLPrpTmain().getArr(0).getInputDate();
    	String contractNo=blProposal.getBLPrpTmain().getArr(0).getContractNo();
    	String riskCode=blProposal.getBLPrpTmain().getArr(0).getRiskCode();
    	String comCode=blProposal.getBLPrpTmain().getArr(0).getComCode();
    	String appliName="";
    	if(new UtiPower().checkSHDeclare(comCode,riskCode,operateDate)){
	    		if(contractNo!=null&&!"".equals(contractNo)){
	    			for (int i = 0; i < blProposal.getBLPrpTinsured().getSize(); i++) {
	    				if("2".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredFlag())){
	    					appliName=blProposal.getBLPrpTinsured().getArr(i).getInsuredName();
	    					break;
	    				}
	    			}
			        buf.append("<PH_LIST>");
			        buf.append("<PH_DATA>");
			        addNode(buf, "POLICY_HOLDER", appliName);
			        buf.append("</PH_DATA>");
			        buf.append("</PH_LIST>");
	    		}
    	}		
    }
    public void addInsuredList(StringBuffer buf,BLProposal blProposal)throws Exception{
    	String operateDate=blProposal.getBLPrpTmain().getArr(0).getInputDate();
    	String contractNo=blProposal.getBLPrpTmain().getArr(0).getContractNo();
    	String riskCode=blProposal.getBLPrpTmain().getArr(0).getRiskCode();
    	String comCode=blProposal.getBLPrpTmain().getArr(0).getComCode();
    	String insuredName="";
    	if(new UtiPower().checkSHDeclare(comCode,riskCode,operateDate)){
	    		if(contractNo!=null&&!"".equals(contractNo)){
	    			for (int i = 0; i < blProposal.getBLPrpTinsured().getSize(); i++) {
	    				if("1".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredFlag())){
	    					insuredName=blProposal.getBLPrpTinsured().getArr(i).getInsuredName();
	    					break;
	    				}
	    			}
			        buf.append("<INSURED_LIST>");
			        buf.append("<INSURED_DATA>");
			        addNode(buf, "INSURED_NAME", insuredName);
			        buf.append("</INSURED_DATA>");
			        buf.append("</INSURED_LIST>");
	    		}
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
    
    
    
    private String encoderUseType(BLProposal blProposal) throws Exception {
        String strUseType = "";
        String intUseType = "";
        intUseType = blProposal.getBLPrpTitemCar().getArr(0).getUseNatureCode();
        String strCarKindCode = strCarKindCode = blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode();
        
        if(strCarKindCode.equals("A0"))
        {
        	if(intUseType.trim().equals("8B"))   		
        	{
	            strUseType = "220";
	    	}
	    	else if(intUseType.trim().equals("8C"))	 	
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
	    	else if(intUseType.trim().equals("9A"))		
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
        
	    else if(strCarKindCode.equals("H0") || 
	    		strCarKindCode.equals("H1") || 
	    		strCarKindCode.equals("H2") ||
	            strCarKindCode.equals("G0"))
	    {
	    	if(intUseType.trim().equals("86") ||   	
	    	   intUseType.trim().equals("87") ||	
	    	   intUseType.trim().equals("9D") ||	
	    	   intUseType.trim().equals("9E") ||	
	    	   intUseType.trim().equals("9A") ||	
	    	   intUseType.trim().equals("9B") ||	
	    	   intUseType.trim().equals("9C") 		
	    	   )
	    	{
	    		strUseType = "100";		
	    	}
	    	else if(
	    			intUseType.trim().equals("8B") ||		
	    			intUseType.trim().equals("8C") ||		
	    			intUseType.trim().equals("8D")      	
	    			)
	    	{
	    		strUseType = "200";		
	    	}

	    }
        
	    else
	    {
	    	strUseType = "000";
	    }

        return strUseType;
    }

    
	private String encoderUseTypeNew(BLProposal blProposal) throws Exception {
	    String strUseType = "";
	    String intUseType = "";
	    intUseType = blProposal.getBLPrpTitemCar().getArr(0).getUseNatureCode();
	    String strCarKindCode = strCarKindCode = blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode();
	    
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

