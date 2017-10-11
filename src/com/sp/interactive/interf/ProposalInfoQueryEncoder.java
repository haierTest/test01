package com.sp.interactive.interf;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Vector;
import java.util.ArrayList;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.sp.payment.blsvr.jf.BLPrpJplanFeePre;
import com.sp.payment.schema.PrpJplanFeePreSchema;
import com.sp.prpall.blsvr.cb.BLPrpCmain;
import com.sp.prpall.blsvr.jf.BLPrpQueryPaymentService;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.utiall.blsvr.BLPrpDagent;
import com.sp.indiv.ci.blsvr.BLCIInsureDemandPay;
import com.sp.indiv.ci.blsvr.BLCIInsureDemandLoss;
import com.sp.indiv.ci.schema.CIInsureDemandLossSchema;
import com.sp.indiv.ci.schema.CIInsureDemandPaySchema;
import com.sp.prpall.schema.PrpTcarDeviceSchema;
import com.sp.prpall.schema.PrpTcarDriverSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTitemCarSchema;
import com.sp.prpall.schema.PrpTitemCarExtSchema;
import com.sp.prpall.schema.PrpTcarshipTaxSchema;
import com.sp.prpall.schema.PrpTengageSchema;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * XX信息查询返回封装报文
 **/

public class ProposalInfoQueryEncoder {

    public String encode(BLProposal blProposalCI, BLProposal blProposalBI) throws Exception{
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("PACKET");
        root.addAttribute("type", "RESPONSE");
        root.addAttribute("version", "1.0");
        addHead(root);
        addBody(root, blProposalCI, blProposalBI);
        return formateXml(doc.asXML());
        
    }
    
    private void addHead(Element root) {
        Element head = root.addElement("HEAD");
        Element REQUESTTYPE = head.addElement("REQUESTTYPE");
        Element responseCode = head.addElement("RESPONSECODE");
        Element responseMessage = head.addElement("RESPONSEMESSAGE");
        REQUESTTYPE.addText("05");
        responseCode.addText("000000");
        responseMessage.addText("成功");
    }

    private void addBody(Element root, BLProposal blProposalCI, BLProposal blProposalBI) throws Exception {
        Element body = root.addElement("BODY");
        Element CIProposalFlag       = body.addElement("CIProposalFlag"     );
        Element BIProposalFlag       = body.addElement("BIProposalFlag"     );
        Element CIProposalNo         = body.addElement("CIProposalNo"       );
        Element BIProposalNo         = body.addElement("BIProposalNo"       );
        Element CIPolicy_No          = body.addElement("CIPolicy_No"        );
        Element BIPolicy_No          = body.addElement("BIPolicy_No"        );
        Element SumBenchMarkPremium  = body.addElement("SumBenchMarkPremium");
        Element SumPremium           = body.addElement("SumPremium"         );
        Element CIDisRate            = body.addElement("CIDisRate"          );
        Element BIDisRate            = body.addElement("BIDisRate"          );
        Element CIBenchMarkPremium   = body.addElement("CIBenchMarkPremium" );
        Element CIPremium            = body.addElement("CIPremium"          );
        Element BIBenchMarkPremium   = body.addElement("BIBenchMarkPremium" );
        Element BIPremium            = body.addElement("BIPremium"          );
        Element BIStartDate          = body.addElement("BIStartDate"        );
        Element BIEndDate            = body.addElement("BIEndDate"          );
        Element BIStartHour          = body.addElement("BIStartHour"        );
        Element BIEndHour            = body.addElement("BIEndHour"          );
        Element RenewalFlag          = body.addElement("RenewalFlag"        );
        Element RenewalCIFlag        = body.addElement("RenewalCIFlag"      );
        Element RenewalCIPolicyNo    = body.addElement("RenewalCIPolicyNo"  );
        Element RenewalBIPolicyNo    = body.addElement("RenewalBIPolicyNo"  );
        Element CINoDamageYears      = body.addElement("CINoDamageYears"    );
        Element BINoDamageYears      = body.addElement("BINoDamageYears"    );
        Element CIStartDate          = body.addElement("CIStartDate"        );
        Element CIEndDate            = body.addElement("CIEndDate"          );
        Element CIStartHour          = body.addElement("CIStartHour"        );
        Element CIEndHour            = body.addElement("CIEndHour"          );
        Element CIPolicyNo           = body.addElement("CIPolicyNo"         );
        Element CICompanyCode        = body.addElement("CICompanyCode"      );
        Element CIUnProposalReason   = body.addElement("CIUnProposalReason" );
        Element AgentCode            = body.addElement("AgentCode"          );
        Element AgentName            = body.addElement("AgentName"          );
        Element AgreementNo          = body.addElement("AgreementNo"        );
        Element OperatorCode         = body.addElement("OperatorCode"       );
        Element HandlerCode          = body.addElement("HandlerCode"        );
        Element BussinessNature      = body.addElement("BussinessNature"    );
        
        double sumBenchMarkPremium = 0;
        double sumPremium = 0;
        
        if(blProposalCI!=null){
        	String strCIProposalFlag = "";
        	String strCIPolicyNo = "";
        	String strOthFlag = blProposalCI.getBLPrpTmain().getArr(0).getOthFlag();
        	String strUnderWriteFlag = blProposalCI.getBLPrpTmain().getArr(0).getUnderWriteFlag();
        	if(strOthFlag.charAt(3)!='2'){
            	if(strUnderWriteFlag.trim().equals("0")){
            		strCIProposalFlag = "0";
            	}else if(strUnderWriteFlag.trim().equals("1")||strUnderWriteFlag.trim().equals("3")){
            		strCIProposalFlag = "2";
            		if(strOthFlag.charAt(17)=='4'){
            			strCIProposalFlag = "5";
            			BLPrpCmain blPrpCmain = new BLPrpCmain();
            			blPrpCmain.queryJX(" ProposalNo='"+blProposalCI.getBLPrpTmain().getArr(0).getProposalNo()+"'");
            			strCIPolicyNo = blPrpCmain.getSize()>0?blPrpCmain.getArr(0).getPolicyNo():"";
            		}else{
            			BLPrpJplanFeePre blprpjplanfeepre=new BLPrpJplanFeePre();
            			
            			String strSwitch = BLPrpQueryPaymentService.queryMirrorSwitch();  
            			if ("1".equals(strSwitch)) 
            			{
        		    		String strSqlStatement = " SELECT * FROM PrpJplanFeePre WHERE CertiNo= '" + blProposalCI.getBLPrpTmain().getArr(0).getProposalNo() + "' AND SerialNo= 0";
        		    		Vector<PrpJplanFeePreSchema> prpJplanFeePreSchemas = BLPrpQueryPaymentService.queryByServlet(BLPrpQueryPaymentService.PrpJplanFeePre, strSqlStatement);
        	     			if(prpJplanFeePreSchemas.size()>0)
        	     			{
                				String strCurrentTime = new ChgDate().getCurrentTime("yyyy-MM-dd HH:mm:ss");
                				String strValidDate = prpJplanFeePreSchemas.get(0).getValidDate();
                				if(strValidDate.length()<=10)
                				{
                					strValidDate = strValidDate+" 00:00:00";
                				}
                				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                				java.util.Date firstDate = null;
                		        java.util.Date secondDate = null;
                	            Str.replace(strCurrentTime, "/", "-");
                	            Str.replace(strValidDate, "/", "-");
                	            firstDate = format.parse(strCurrentTime);
                	            secondDate = format.parse(strValidDate);
                	            if(firstDate.after(secondDate)){
                	            	strCIProposalFlag = "4";
                	            }
        	     			}
        	     			else
        	     			{
                				strCIProposalFlag = "4";
                			}
        	     			
            			}
            			else
            			{
                 			blprpjplanfeepre.getData(blProposalCI.getBLPrpTmain().getArr(0).getProposalNo(), "0");
                			if(blprpjplanfeepre.getSize()>0){
                				String strCurrentTime = new ChgDate().getCurrentTime("yyyy-MM-dd HH:mm:ss");
                				String strValidDate = blprpjplanfeepre.getArr(0).getValidDate();
                				if(strValidDate.length()<=10){
                					strValidDate = strValidDate+" 00:00:00";
                				}
                				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                				java.util.Date firstDate = null;
                		        java.util.Date secondDate = null;
                	            Str.replace(strCurrentTime, "/", "-");
                	            Str.replace(strValidDate, "/", "-");
                	            firstDate = format.parse(strCurrentTime);
                	            secondDate = format.parse(strValidDate);
                	            if(firstDate.after(secondDate)){
                	            	strCIProposalFlag = "4";
                	            }
                			}else{
                				strCIProposalFlag = "4";
                			}
            			}
            			
            		}
            	}else if(strUnderWriteFlag.trim().equals("2")){
            		strCIProposalFlag = "3";
            	}else if(strUnderWriteFlag.trim().equals("9")){
            		strCIProposalFlag = "1";
            	}
            }else{
            	strCIProposalFlag = "6";
            }
        	
            int size = blProposalCI.getBLPrpTitemKind().getSize();
            double ciBenchMarkPremium = 0;
            for(int i = 0; i < size; i++){
            	PrpTitemKindSchema prpTitemKindSchema = blProposalCI.getBLPrpTitemKind().getArr(i);
            	ciBenchMarkPremium += Double.parseDouble(ChgData.chgStrZero(prpTitemKindSchema.getBenchMarkPremium()));
            	sumBenchMarkPremium += Double.parseDouble(ChgData.chgStrZero(prpTitemKindSchema.getBenchMarkPremium()));
            }
            sumPremium += Double.parseDouble(ChgData.chgStrZero(blProposalCI.getBLPrpTmain().getArr(0).getSumPremium()));
            
            CIProposalFlag    .addText(strCIProposalFlag);
            CIProposalNo      .addText(blProposalCI.getBLPrpTmain().getArr(0).getProposalNo());
            CIPolicy_No       .addText(strCIPolicyNo);
            CIDisRate         .addText(blProposalCI.getBLPrpTmain().getArr(0).getDisRate());
            CIBenchMarkPremium.addText(new DecimalFormat("0.00").format(ciBenchMarkPremium));
            CIPremium         .addText(blProposalCI.getBLPrpTmain().getArr(0).getSumPremium());
            RenewalCIFlag     .addText(blProposalCI.getBLPrpTmain().getArr(0).getOthFlag().substring(0, 1));
            RenewalCIPolicyNo .addText(blProposalCI.getBLPrpTrenewal().getSize()>0?blProposalCI.getBLPrpTrenewal().getArr(0).getOldPolicyNo():"");
            CINoDamageYears   .addText(blProposalCI.getBLPrpTitemCarExt().getArr(0).getNoDamageYears());
            CIStartDate       .addText(blProposalCI.getBLPrpTmain().getArr(0).getStartDate());
            CIEndDate         .addText(blProposalCI.getBLPrpTmain().getArr(0).getEndDate());
            CIStartHour       .addText(blProposalCI.getBLPrpTmain().getArr(0).getStartHour());
            CIEndHour         .addText(blProposalCI.getBLPrpTmain().getArr(0).getEndHour());
            CIPolicyNo        .addText("");
            CICompanyCode     .addText("");
            CIUnProposalReason.addText("");
            if(blProposalBI==null){
	            AgentCode         .addText(blProposalCI.getBLPrpTmain().getArr(0).getAgentCode());
	            BLPrpDagent blPrpDagent = new BLPrpDagent();
	            blPrpDagent.query("AgentCode = '" +blProposalCI.getBLPrpTmain().getArr(0).getAgentCode()+ "'");;
	            AgentName         .addText(blPrpDagent.getSize()>0?blPrpDagent.getArr(0).getAgentName():"");
	            AgreementNo       .addText(blProposalCI.getBLPrpTmain().getArr(0).getAgreementNo());
	            OperatorCode      .addText(blProposalCI.getBLPrpTmain().getArr(0).getOperatorCode());
	            HandlerCode       .addText(blProposalCI.getBLPrpTmain().getArr(0).getHandler1Code());
	            BussinessNature   .addText(blProposalCI.getBLPrpTmain().getArr(0).getBusinessNature());
            }
        }
        
        if(blProposalBI!=null){
        	String strBIProposalFlag = "";
        	String strBIPolicyNo = "";
        	String strOthFlag = blProposalBI.getBLPrpTmain().getArr(0).getOthFlag();
        	String strUnderWriteFlag = blProposalBI.getBLPrpTmain().getArr(0).getUnderWriteFlag();
        	if(strOthFlag.charAt(3)!='2'){
            	if(strUnderWriteFlag.trim().equals("0")){
            		strBIProposalFlag = "0";
            	}else if(strUnderWriteFlag.trim().equals("1")||strUnderWriteFlag.trim().equals("3")){
            		strBIProposalFlag = "2";
            		if(strOthFlag.charAt(17)=='4'){
            			strBIProposalFlag = "5";
            			BLPrpCmain blPrpCmain = new BLPrpCmain();
            			blPrpCmain.queryJX(" ProposalNo='"+blProposalBI.getBLPrpTmain().getArr(0).getProposalNo()+"'");
            			strBIPolicyNo = blPrpCmain.getSize()>0?blPrpCmain.getArr(0).getPolicyNo():"";
            		}else{
            			BLPrpJplanFeePre blprpjplanfeepre=new BLPrpJplanFeePre();
            			blprpjplanfeepre.getData(blProposalBI.getBLPrpTmain().getArr(0).getProposalNo(), "0");
            			if(blprpjplanfeepre.getSize()>0){
            				String strCurrentTime = new ChgDate().getCurrentTime("yyyy-MM-dd HH:mm:ss");
            				String strValidDate = blprpjplanfeepre.getArr(0).getValidDate();
            				if(strValidDate.length()<=10){
            					strValidDate = strValidDate+" 00:00:00";
            				}
            				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            				java.util.Date firstDate = null;
            		        java.util.Date secondDate = null;
            	            Str.replace(strCurrentTime, "/", "-");
            	            Str.replace(strValidDate, "/", "-");
            	            firstDate = format.parse(strCurrentTime);
            	            secondDate = format.parse(strValidDate);
            	            if(firstDate.after(secondDate)){
            	            	strBIProposalFlag = "4";
            	            }
            			}else{
            				strBIProposalFlag = "4";
            			}
            		}
            	}else if(strUnderWriteFlag.trim().equals("2")){
            		strBIProposalFlag = "3";
            	}else if(strUnderWriteFlag.trim().equals("9")){
            		strBIProposalFlag = "1";
            	}
            }else{
            	strBIProposalFlag = "6";
            }
        	
        	int size = blProposalBI.getBLPrpTitemKind().getSize();
            double biBenchMarkPremium = 0;
            for(int i = 0; i < size; i++){
            	PrpTitemKindSchema prpTitemKindSchema = blProposalBI.getBLPrpTitemKind().getArr(i);
            	biBenchMarkPremium += Double.parseDouble(ChgData.chgStrZero(prpTitemKindSchema.getBenchMarkPremium()));
            	sumBenchMarkPremium += Double.parseDouble(ChgData.chgStrZero(prpTitemKindSchema.getBenchMarkPremium()));
            }
            sumPremium += Double.parseDouble(ChgData.chgStrZero(blProposalBI.getBLPrpTmain().getArr(0).getSumPremium()));
        	
        	BIProposalFlag    .addText(strBIProposalFlag);
        	BIProposalNo      .addText(blProposalBI.getBLPrpTmain().getArr(0).getProposalNo());
        	BIPolicy_No       .addText(strBIPolicyNo);
            BIDisRate         .addText(blProposalBI.getBLPrpTmain().getArr(0).getDisRate());
            BIBenchMarkPremium.addText(new DecimalFormat("0.00").format(biBenchMarkPremium));
            BIPremium         .addText(blProposalBI.getBLPrpTmain().getArr(0).getSumPremium());
            RenewalFlag       .addText(blProposalBI.getBLPrpTmain().getArr(0).getOthFlag().substring(0, 1));
            RenewalBIPolicyNo .addText(blProposalBI.getBLPrpTrenewal().getSize()>0?blProposalBI.getBLPrpTrenewal().getArr(0).getOldPolicyNo():"");
            BINoDamageYears   .addText(blProposalBI.getBLPrpTitemCarExt().getArr(0).getNoDamageYears());
            BIStartDate       .addText(blProposalBI.getBLPrpTmain().getArr(0).getStartDate());
            BIEndDate         .addText(blProposalBI.getBLPrpTmain().getArr(0).getEndDate());
            BIStartHour       .addText(blProposalBI.getBLPrpTmain().getArr(0).getStartHour());
            BIEndHour         .addText(blProposalBI.getBLPrpTmain().getArr(0).getEndHour());
            AgentCode         .addText(blProposalBI.getBLPrpTmain().getArr(0).getAgentCode());
            BLPrpDagent blPrpDagent = new BLPrpDagent();
            blPrpDagent.query("AgentCode = '" +blProposalBI.getBLPrpTmain().getArr(0).getAgentCode()+ "'");;
            AgentName         .addText(blPrpDagent.getSize()>0?blPrpDagent.getArr(0).getAgentName():"");
            AgreementNo       .addText(blProposalBI.getBLPrpTmain().getArr(0).getAgreementNo());
            OperatorCode      .addText(blProposalBI.getBLPrpTmain().getArr(0).getOperatorCode());
            HandlerCode       .addText(blProposalBI.getBLPrpTmain().getArr(0).getHandler1Code());
            BussinessNature   .addText(blProposalBI.getBLPrpTmain().getArr(0).getBusinessNature());
        }
        
        if(blProposalCI!=null){
        	if(blProposalCI.getBLPrpTcarshipTax().getSize()>0){
        		sumBenchMarkPremium += Double.parseDouble(ChgData.chgStrZero(blProposalCI.getBLPrpTcarshipTax().getArr(0).getSumPayTax()));
        		sumPremium += Double.parseDouble(ChgData.chgStrZero(blProposalCI.getBLPrpTcarshipTax().getArr(0).getSumPayTax()));
        	}
        }
        SumBenchMarkPremium .addText(new DecimalFormat("0.00").format(sumBenchMarkPremium));
        SumPremium          .addText(new DecimalFormat("0.00").format(sumPremium));
        
        Element CarModelInfo = body.addElement("CarModelInfo");
        addCarModelInfo(CarModelInfo, blProposalCI, blProposalBI);
        
        Element CarShipTaxInfo = body.addElement("CarShipTaxInfo");
        addCarShipTaxInfo(CarShipTaxInfo, blProposalCI);
        
        Element InsuredList = body.addElement("InsuredList");
        addInsuredList(InsuredList, blProposalCI, blProposalBI);
        
        Element ItemKindList = body.addElement("ItemKindList");
        addItemKindList(ItemKindList, blProposalCI, blProposalBI);
        
        Element CarDeviceList = body.addElement("CarDeviceList");
        addCarDeviceList(CarDeviceList, blProposalBI);
        
        Element CarDriverList = body.addElement("CarDriverList");
        addCarDriverList(CarDriverList, blProposalBI);
        
        Element ClaimList = body.addElement("ClaimList");
        addCliamList(ClaimList, blProposalCI, blProposalBI);
        
        Element PeccancyList = body.addElement("PeccancyList");
        addPeccancyList(PeccancyList, blProposalCI, blProposalBI);
        
        Element EngageList = body.addElement("EngageList");
        addEngageList(EngageList, blProposalCI, blProposalBI);
    }

    private void addCarModelInfo(Element CarModelInfo, BLProposal blProposalCI, BLProposal blProposalBI) throws Exception {
        Element LicenseNo           =CarModelInfo.addElement("LicenseNo"          );
        Element CarKindCode         =CarModelInfo.addElement("CarKindCode"        );
        Element LicenseType         =CarModelInfo.addElement("LicenseType"        );
        Element FrameNo             =CarModelInfo.addElement("FrameNo"            );
        Element EnginNo             =CarModelInfo.addElement("EnginNo"            );
        Element CarOwner            =CarModelInfo.addElement("CarOwner"           );
        Element EnrollDate          =CarModelInfo.addElement("EnrollDate"         );
        Element CarCertificateDate  =CarModelInfo.addElement("CarCertificateDate" );
        Element ColorCode           =CarModelInfo.addElement("ColorCode"          );
        Element LicenseColor        =CarModelInfo.addElement("LicenseColor"       );
        Element NewVehicleFlag      =CarModelInfo.addElement("NewVehicleFlag"     );
        Element EcdemicVehicleFlag  =CarModelInfo.addElement("EcdemicVehicleFlag" );
        Element ChgOwnerFlag        =CarModelInfo.addElement("ChgOwnerFlag"       );
        Element FirstRegisterDate   =CarModelInfo.addElement("FirstRegisterDate"  );
        Element IsInDoor            =CarModelInfo.addElement("IsInDoor"           );
        Element LoanVehicleFlag     =CarModelInfo.addElement("LoanVehicleFlag"    );
        Element PeccancyAdjust      =CarModelInfo.addElement("PeccancyAdjust"     );
        Element UseType             =CarModelInfo.addElement("UseType"            );
        Element VehicleType         =CarModelInfo.addElement("VehicleType"        );
        Element HKFlag              =CarModelInfo.addElement("HKFlag"             );
        Element HKLicenseNo         =CarModelInfo.addElement("HKLicenseNo"        );
        Element RunMile             =CarModelInfo.addElement("RunMile"            );
        Element AreaCode            =CarModelInfo.addElement("AreaCode"           );
        Element BrandName           =CarModelInfo.addElement("BrandName"          );
        Element RBCode              =CarModelInfo.addElement("RBCode"             );
        Element SpecialCarFlag      =CarModelInfo.addElement("SpecialCarFlag"     );
        Element VehicleBrand1       =CarModelInfo.addElement("VehicleBrand1"      );
        Element MadeFactory         =CarModelInfo.addElement("MadeFactory"        );
        Element MakeDate            =CarModelInfo.addElement("MakeDate"           );
        Element PurchasePrice       =CarModelInfo.addElement("PurchasePrice"      );
        Element PurchasePriceModi   =CarModelInfo.addElement("PurchasePriceModi"  );
        Element SeatCount           =CarModelInfo.addElement("SeatCount"          );
        Element VehicleWeight       =CarModelInfo.addElement("VehicleWeight"      );
        Element VehicleTonnage      =CarModelInfo.addElement("VehicleTonnage"     );
        Element ExhaustCapacity     =CarModelInfo.addElement("ExhaustCapacity"    );
        Element FuleType            =CarModelInfo.addElement("FuleType"           );
        Element ImportFlag          =CarModelInfo.addElement("ImportFlag"         );
        Element ABSFlag             =CarModelInfo.addElement("ABSFlag"            );
        Element AirBagNum           =CarModelInfo.addElement("AirBagNum"          );
        Element AlarmFlag           =CarModelInfo.addElement("AlarmFlag"          );
        Element RefCode1            =CarModelInfo.addElement("RefCode1"           );
        Element RefCode2            =CarModelInfo.addElement("RefCode2"           );
        
        if(blProposalBI!=null){
        	PrpTitemCarSchema prpTitemCarSchema = blProposalBI.getBLPrpTitemCar().getArr(0);
        	PrpTitemCarExtSchema prpTitemCarExtSchema = blProposalBI.getBLPrpTitemCarExt().getArr(0);
	        LicenseNo          .addText(prpTitemCarSchema.getLicenseNo());
	        CarKindCode        .addText(prpTitemCarSchema.getCarKindCode());
	        LicenseType        .addText(prpTitemCarSchema.getLicenseKindCode());
	        FrameNo            .addText(prpTitemCarSchema.getFrameNo());
	        EnginNo            .addText(prpTitemCarSchema.getEngineNo());
	        CarOwner           .addText(prpTitemCarSchema.getCarOwner());
	        EnrollDate         .addText(prpTitemCarSchema.getEnrollDate());
	        CarCertificateDate .addText("");
	        ColorCode          .addText(prpTitemCarSchema.getColorCode());
	        LicenseColor       .addText(prpTitemCarSchema.getLicenseColorCode());
	        NewVehicleFlag     .addText(prpTitemCarSchema.getNewCarFlag());
	        EcdemicVehicleFlag .addText(prpTitemCarExtSchema.getEcdemicVehicleKind());
	        ChgOwnerFlag       .addText(prpTitemCarSchema.getChgOwnerFlag());
	        FirstRegisterDate  .addText(prpTitemCarSchema.getChgOwnerDate());
	        IsInDoor           .addText(prpTitemCarExtSchema.getInDoorFlag());
	        LoanVehicleFlag    .addText(prpTitemCarSchema.getLoanVehicleFlag());
	        PeccancyAdjust     .addText(prpTitemCarSchema.getAddonCount());
	        UseType            .addText(prpTitemCarSchema.getUseNatureCode());
	        VehicleType        .addText(prpTitemCarExtSchema.getVerificationCode());
	        HKFlag             .addText(prpTitemCarSchema.getHKFlag());
	        HKLicenseNo        .addText(prpTitemCarSchema.getHKLicenseNo());
	        RunMile            .addText(prpTitemCarSchema.getRunMiles());
	        AreaCode           .addText(prpTitemCarSchema.getRunAreaCode());
	        BrandName          .addText(prpTitemCarSchema.getBrandName());
	        RBCode             .addText(prpTitemCarSchema.getModelCode());
	        SpecialCarFlag     .addText("");
	        VehicleBrand1      .addText(prpTitemCarSchema.getBrandName());
	        MadeFactory        .addText(prpTitemCarSchema.getMadeFactory());
	        MakeDate           .addText(prpTitemCarSchema.getMakeDate());
	        PurchasePrice      .addText(prpTitemCarSchema.getPurchasePriceLB());
	        PurchasePriceModi  .addText(prpTitemCarSchema.getPurchasePrice());
	        SeatCount          .addText(prpTitemCarSchema.getSeatCount());
	        VehicleWeight      .addText(prpTitemCarSchema.getCompleteKerbMass());
	        VehicleTonnage     .addText(prpTitemCarSchema.getTonCount());
	        ExhaustCapacity    .addText(prpTitemCarSchema.getExhaustScale());
	        FuleType           .addText(prpTitemCarExtSchema.getFuelType());
	        ImportFlag         .addText(prpTitemCarSchema.getCountryNature());
	        ABSFlag            .addText("");
	        AirBagNum          .addText("");
	        AlarmFlag          .addText("");
	        RefCode1           .addText("");
	        RefCode2           .addText("");
        }
        if(blProposalCI!=null && blProposalBI==null){
        	PrpTitemCarSchema prpTitemCarSchema = blProposalCI.getBLPrpTitemCar().getArr(0);
        	PrpTitemCarExtSchema prpTitemCarExtSchema = blProposalCI.getBLPrpTitemCarExt().getArr(0);
	        LicenseNo          .addText(prpTitemCarSchema.getLicenseNo());
	        CarKindCode        .addText(prpTitemCarSchema.getCarKindCode());
	        LicenseType        .addText(prpTitemCarSchema.getLicenseKindCode());
	        FrameNo            .addText(prpTitemCarSchema.getFrameNo());
	        EnginNo            .addText(prpTitemCarSchema.getEngineNo());
	        CarOwner           .addText(prpTitemCarSchema.getCarOwner());
	        EnrollDate         .addText(prpTitemCarSchema.getEnrollDate());
	        CarCertificateDate .addText("");
	        ColorCode          .addText(prpTitemCarSchema.getColorCode());
	        LicenseColor       .addText(prpTitemCarSchema.getLicenseColorCode());
	        NewVehicleFlag     .addText(prpTitemCarSchema.getNewCarFlag());
	        EcdemicVehicleFlag .addText(prpTitemCarExtSchema.getEcdemicVehicleKind());
	        ChgOwnerFlag       .addText(prpTitemCarSchema.getChgOwnerFlag());
	        FirstRegisterDate  .addText(prpTitemCarSchema.getChgOwnerDate());
	        IsInDoor           .addText(prpTitemCarExtSchema.getInDoorFlag());
	        LoanVehicleFlag    .addText(prpTitemCarSchema.getLoanVehicleFlag());
	        PeccancyAdjust     .addText(prpTitemCarSchema.getAddonCount());
	        UseType            .addText(prpTitemCarSchema.getUseNatureCode());
	        VehicleType        .addText(prpTitemCarExtSchema.getVerificationCode());
	        HKFlag             .addText(prpTitemCarSchema.getHKFlag());
	        HKLicenseNo        .addText(prpTitemCarSchema.getHKLicenseNo());
	        RunMile            .addText(prpTitemCarSchema.getRunMiles());
	        AreaCode           .addText(prpTitemCarSchema.getRunAreaCode());
	        BrandName          .addText(prpTitemCarSchema.getBrandName());
	        RBCode             .addText(prpTitemCarSchema.getModelCode());
	        SpecialCarFlag     .addText("");
	        VehicleBrand1      .addText(prpTitemCarSchema.getBrandName());
	        MadeFactory        .addText(prpTitemCarSchema.getMadeFactory());
	        MakeDate           .addText(prpTitemCarSchema.getMakeDate());
	        PurchasePrice      .addText(prpTitemCarSchema.getPurchasePriceLB());
	        PurchasePriceModi  .addText(prpTitemCarSchema.getPurchasePrice());
	        SeatCount          .addText(prpTitemCarSchema.getSeatCount());
	        VehicleWeight      .addText(prpTitemCarSchema.getCompleteKerbMass());
	        VehicleTonnage     .addText(prpTitemCarSchema.getTonCount());
	        ExhaustCapacity    .addText(prpTitemCarSchema.getExhaustScale());
	        FuleType           .addText(prpTitemCarExtSchema.getFuelType());
	        ImportFlag         .addText(prpTitemCarSchema.getCountryNature());
	        ABSFlag            .addText("");
	        AirBagNum          .addText("");
	        AlarmFlag          .addText("");
	        RefCode1           .addText("");
	        RefCode2           .addText("");
        }
    }
    
    private void addCarShipTaxInfo(Element CarShipTaxInfo, BLProposal blProposalCI) throws Exception {
    	Element TaxFlag                = CarShipTaxInfo.addElement("TaxFlag"               );
        Element TaxPayerName           = CarShipTaxInfo.addElement("TaxPayerName"          );
        Element TaxPayerCertiType      = CarShipTaxInfo.addElement("TaxPayerCertiType"     );
        Element TaxPayerCertiCode      = CarShipTaxInfo.addElement("TaxPayerCertiCode"     );
        Element BuyCertificateDate     = CarShipTaxInfo.addElement("BuyCertificateDate"    );
        Element FuelType               = CarShipTaxInfo.addElement("FuelType"              );
        Element CertificateType        = CarShipTaxInfo.addElement("CertificateType"       );
        Element CertificateNo          = CarShipTaxInfo.addElement("CertificateNo"         );
        Element CertificateDate        = CarShipTaxInfo.addElement("CertificateDate"       );
        Element PayNo                  = CarShipTaxInfo.addElement("PayNo"                 );
        Element FreeNo                 = CarShipTaxInfo.addElement("FreeNo"                );
        Element KTaxComName            = CarShipTaxInfo.addElement("KTaxComName"           );
        Element KTaxComCode            = CarShipTaxInfo.addElement("KTaxComCode"           );
        Element STaxComCode            = CarShipTaxInfo.addElement("STaxComCode"           );
        Element STaxComName            = CarShipTaxInfo.addElement("STaxComName"           );
        Element TaxStartDate           = CarShipTaxInfo.addElement("TaxStartDate"          );
        Element TaxEndDate             = CarShipTaxInfo.addElement("TaxEndDate"            );
        Element DeductionDueType       = CarShipTaxInfo.addElement("DeductionDueType"      );
        Element DeductionDueCode       = CarShipTaxInfo.addElement("DeductionDueCode"      );
        Element Deduction              = CarShipTaxInfo.addElement("Deduction"             );
        Element DeductionDueProportion = CarShipTaxInfo.addElement("DeductionDueProportion");
        Element FreeRateText           = CarShipTaxInfo.addElement("FreeRateText"          );
        Element HisFeeFlag             = CarShipTaxInfo.addElement("HisFeeFlag"            );
        Element LateFeeFlag            = CarShipTaxInfo.addElement("LateFeeFlag"           );
        Element PayLastYear            = CarShipTaxInfo.addElement("PayLastYear"           );
        Element HisPolicyEndDate       = CarShipTaxInfo.addElement("HisPolicyEndDate"      );
        Element PayBalanceFee          = CarShipTaxInfo.addElement("PayBalanceFee"         );
        Element LateFeeStartDate       = CarShipTaxInfo.addElement("LateFeeStartDate"      );
        Element TaxActual              = CarShipTaxInfo.addElement("TaxActual"             );
        Element PreviousPay            = CarShipTaxInfo.addElement("PreviousPay"           );
        Element LateFee                = CarShipTaxInfo.addElement("LateFee"               );
        Element SumPayTax              = CarShipTaxInfo.addElement("SumPayTax"             );
        
        if(blProposalCI!=null){
        	PrpTcarshipTaxSchema prpTcarshipTaxSchema = blProposalCI.getBLPrpTcarshipTax().getSize()>0?
        			blProposalCI.getBLPrpTcarshipTax().getArr(0):null;
            if(prpTcarshipTaxSchema!=null){
            	TaxFlag                .addText(prpTcarshipTaxSchema.getTaxRelifFlag());
                TaxPayerName           .addText(prpTcarshipTaxSchema.getTaxpayerName());
                TaxPayerCertiType      .addText("");
                TaxPayerCertiCode      .addText(prpTcarshipTaxSchema.getTaxpayerCode());
                BuyCertificateDate     .addText(blProposalCI.getBLPrpTitemCarExt().getArr(0).getBuyCarDate());
                FuelType               .addText(blProposalCI.getBLPrpTitemCarExt().getArr(0).getFuelType());
                CertificateType        .addText("");
                CertificateNo          .addText("");
                CertificateDate        .addText(prpTcarshipTaxSchema.getCertificateDate());
                PayNo                  .addText("2".equals(prpTcarshipTaxSchema.getTaxRelief())||"3".equals(prpTcarshipTaxSchema.getTaxRelief())?prpTcarshipTaxSchema.getPaidFreeCertificate():"");
                FreeNo                 .addText("4".equals(prpTcarshipTaxSchema.getTaxRelief())?prpTcarshipTaxSchema.getPaidFreeCertificate():"");
                KTaxComName            .addText(prpTcarshipTaxSchema.getTaxComName());
                KTaxComCode            .addText(prpTcarshipTaxSchema.getTaxComCode());
                STaxComCode            .addText("");
                STaxComName            .addText("");
                TaxStartDate           .addText(prpTcarshipTaxSchema.getPayStartDate());
                TaxEndDate             .addText(prpTcarshipTaxSchema.getPayEndDate());
                DeductionDueType       .addText(prpTcarshipTaxSchema.getBaseTaxation());
                DeductionDueCode       .addText(prpTcarshipTaxSchema.getRelifReason());
                Deduction              .addText(prpTcarshipTaxSchema.getTaxRelief());
                DeductionDueProportion .addText(prpTcarshipTaxSchema.getFreeRate());
                FreeRateText           .addText(prpTcarshipTaxSchema.getFreeRateText());
                HisFeeFlag             .addText(prpTcarshipTaxSchema.getCalFeeFlag().length()>1?prpTcarshipTaxSchema.getCalFeeFlag().substring(0, 1):"");
                LateFeeFlag            .addText(prpTcarshipTaxSchema.getCalFeeFlag().length()>1?prpTcarshipTaxSchema.getCalFeeFlag().substring(1, 2):"");
                PayLastYear            .addText(prpTcarshipTaxSchema.getPayLastYear());
                HisPolicyEndDate       .addText(prpTcarshipTaxSchema.getHisPolicyEndDate());
                PayBalanceFee          .addText(prpTcarshipTaxSchema.getPayBalanceFee());
                LateFeeStartDate       .addText(prpTcarshipTaxSchema.getLateFeeStartDate());
                TaxActual              .addText(prpTcarshipTaxSchema.getTaxActual());
                PreviousPay            .addText(prpTcarshipTaxSchema.getPreviousPay());
                LateFee                .addText(prpTcarshipTaxSchema.getLateFee());
                SumPayTax              .addText(prpTcarshipTaxSchema.getSumPayTax());
            }
        }
    }
    
    private void addInsuredList(Element InsuredList, BLProposal blProposalCI, BLProposal blProposalBI) throws Exception {
    	if(blProposalBI!=null){
    		for(int i = 0 ;i < blProposalBI.getBLPrpTinsured().getSize(); i++ ){
    			Element InsuredData      = InsuredList.addElement("InsuredData");
    			
    			Element PersonType       = InsuredData.addElement("PersonType"      );
    	    	Element PersonClass      = InsuredData.addElement("PersonClass"     );
    	    	Element PersonName       = InsuredData.addElement("PersonName"      );
    	    	Element Sex              = InsuredData.addElement("Sex"             );
    	    	Element IdentifyType     = InsuredData.addElement("IdentifyType"    );
    	    	Element IdentifyNumber   = InsuredData.addElement("IdentifyNumber"  );
    	    	Element Mobile           = InsuredData.addElement("Mobile"          );
    	    	Element Email            = InsuredData.addElement("Email"           );
    	    	Element PostAddress      = InsuredData.addElement("PostAddress"     );
    	    	Element PostCode         = InsuredData.addElement("PostCode"        );
    	    	Element PersonTypeClass  = InsuredData.addElement("PersonTypeClass" );
    	    	Element PersonGrade      = InsuredData.addElement("PersonGrade"     );
    	    	Element IndividualType   = InsuredData.addElement("IndividualType"  );
	        
    	    	PersonType      .addText(blProposalBI.getBLPrpTinsured().getArr(i).getInsuredFlag());
    	    	PersonClass     .addText(blProposalBI.getBLPrpTinsured().getArr(i).getInsuredType());
    	    	PersonName      .addText(blProposalBI.getBLPrpTinsured().getArr(i).getInsuredName());
    	    	for(int j = 0 ;j < blProposalBI.getBLPrpTinsuredNature().getSize(); j++ ){
    	    		if(blProposalBI.getBLPrpTinsured().getArr(i).getInsuredFlag().equals(blProposalBI.getBLPrpTinsuredNature().getArr(j).getInsuredFlag())){
    	    			Sex             .addText(blProposalBI.getBLPrpTinsuredNature().getArr(j).getSex());
    	    		}
    	    	}
    	    	IdentifyType    .addText(blProposalBI.getBLPrpTinsured().getArr(i).getIdentifyType());
    	    	IdentifyNumber  .addText(blProposalBI.getBLPrpTinsured().getArr(i).getIdentifyNumber());
    	    	Mobile          .addText(blProposalBI.getBLPrpTinsured().getArr(i).getMobile());
    	    	Email           .addText(blProposalBI.getBLPrpTinsured().getArr(i).getEmail());
    	    	PostAddress     .addText(blProposalBI.getBLPrpTinsured().getArr(i).getPostAddress());
    	    	PostCode        .addText(blProposalBI.getBLPrpTinsured().getArr(i).getPostCode());
    	    	PersonTypeClass .addText("");
    	    	PersonGrade     .addText("");
    	    	IndividualType  .addText("");
	        }
    		
    		Element InsuredData      = InsuredList.addElement("InsuredData"     );
    		Element PersonType       = InsuredData.addElement("PersonType"      );
	    	Element PersonClass      = InsuredData.addElement("PersonClass"     );
	    	Element PersonName       = InsuredData.addElement("PersonName"      );
	    	Element Sex              = InsuredData.addElement("Sex"             );
	    	Element IdentifyType     = InsuredData.addElement("IdentifyType"    );
	    	Element IdentifyNumber   = InsuredData.addElement("IdentifyNumber"  );
	    	Element Mobile           = InsuredData.addElement("Mobile"          );
	    	Element Email            = InsuredData.addElement("Email"           );
	    	Element PostAddress      = InsuredData.addElement("PostAddress"     );
	    	Element PostCode         = InsuredData.addElement("PostCode"        );
	    	Element PersonTypeClass  = InsuredData.addElement("PersonTypeClass" );
	    	Element PersonGrade      = InsuredData.addElement("PersonGrade"     );
	    	Element IndividualType   = InsuredData.addElement("IndividualType"  );
	    	
	    	PersonType      .addText("0");
	    	PersonClass     .addText("0".equals(blProposalBI.getBLPrpTitemCar().getArr(0).getCarOwnerNature())?"1":"2");
	    	PersonName      .addText(blProposalBI.getBLPrpTitemCar().getArr(0).getCarOwner());
	    	Sex             .addText("");
	    	IdentifyType    .addText(blProposalBI.getBLPrpTitemCar().getArr(0).getInsuredTypeCode());
	    	IdentifyNumber  .addText(blProposalBI.getBLPrpTitemCar().getArr(0).getOwnerAddress());
	    	Mobile          .addText("");
	    	Email           .addText("");
	    	PostAddress     .addText("");
	    	PostCode        .addText("");
	    	PersonTypeClass .addText("");
	    	PersonGrade     .addText("");
	    	IndividualType  .addText("");
    	}
    	if(blProposalCI!=null && blProposalBI==null){
    		for(int i = 0 ;i < blProposalCI.getBLPrpTinsured().getSize(); i++ ){
    			Element InsuredData      = InsuredList.addElement("InsuredData");
    			
    			Element PersonType       = InsuredData.addElement("PersonType"      );
    	    	Element PersonClass      = InsuredData.addElement("PersonClass"     );
    	    	Element PersonName       = InsuredData.addElement("PersonName"      );
    	    	Element Sex              = InsuredData.addElement("Sex"             );
    	    	Element IdentifyType     = InsuredData.addElement("IdentifyType"    );
    	    	Element IdentifyNumber   = InsuredData.addElement("IdentifyNumber"  );
    	    	Element Mobile           = InsuredData.addElement("Mobile"          );
    	    	Element Email            = InsuredData.addElement("Email"           );
    	    	Element PostAddress      = InsuredData.addElement("PostAddress"     );
    	    	Element PostCode         = InsuredData.addElement("PostCode"        );
    	    	Element PersonTypeClass  = InsuredData.addElement("PersonTypeClass" );
    	    	Element PersonGrade      = InsuredData.addElement("PersonGrade"     );
    	    	Element IndividualType   = InsuredData.addElement("IndividualType"  );
	        
    	    	PersonType      .addText(blProposalCI.getBLPrpTinsured().getArr(i).getInsuredFlag());
    	    	PersonClass     .addText(blProposalCI.getBLPrpTinsured().getArr(i).getInsuredType());
    	    	PersonName      .addText(blProposalCI.getBLPrpTinsured().getArr(i).getInsuredName());
    	    	for(int j = 0 ;j < blProposalCI.getBLPrpTinsuredNature().getSize(); j++ ){
    	    		if(blProposalCI.getBLPrpTinsured().getArr(i).getInsuredFlag().equals(blProposalCI.getBLPrpTinsuredNature().getArr(j).getInsuredFlag())){
    	    			Sex             .addText(blProposalCI.getBLPrpTinsuredNature().getArr(j).getSex());
    	    		}
    	    	}
    	    	IdentifyType    .addText(blProposalCI.getBLPrpTinsured().getArr(i).getIdentifyType());
    	    	IdentifyNumber  .addText(blProposalCI.getBLPrpTinsured().getArr(i).getIdentifyNumber());
    	    	Mobile          .addText(blProposalCI.getBLPrpTinsured().getArr(i).getMobile());
    	    	Email           .addText(blProposalCI.getBLPrpTinsured().getArr(i).getEmail());
    	    	PostAddress     .addText(blProposalCI.getBLPrpTinsured().getArr(i).getPostAddress());
    	    	PostCode        .addText(blProposalCI.getBLPrpTinsured().getArr(i).getPostCode());
    	    	PersonTypeClass .addText("");
    	    	PersonGrade     .addText("");
    	    	IndividualType  .addText("");
	        }
    		
    		Element InsuredData      = InsuredList.addElement("InsuredData"     );
    		Element PersonType       = InsuredData.addElement("PersonType"      );
	    	Element PersonClass      = InsuredData.addElement("PersonClass"     );
	    	Element PersonName       = InsuredData.addElement("PersonName"      );
	    	Element Sex              = InsuredData.addElement("Sex"             );
	    	Element IdentifyType     = InsuredData.addElement("IdentifyType"    );
	    	Element IdentifyNumber   = InsuredData.addElement("IdentifyNumber"  );
	    	Element Mobile           = InsuredData.addElement("Mobile"          );
	    	Element Email            = InsuredData.addElement("Email"           );
	    	Element PostAddress      = InsuredData.addElement("PostAddress"     );
	    	Element PostCode         = InsuredData.addElement("PostCode"        );
	    	Element PersonTypeClass  = InsuredData.addElement("PersonTypeClass" );
	    	Element PersonGrade      = InsuredData.addElement("PersonGrade"     );
	    	Element IndividualType   = InsuredData.addElement("IndividualType"  );
	    	
	    	PersonType      .addText("0");
	    	PersonClass     .addText(blProposalCI.getBLPrpTitemCar().getArr(0).getCarOwnerNature());
	    	PersonName      .addText(blProposalCI.getBLPrpTitemCar().getArr(0).getCarOwner());
	    	Sex             .addText("");
	    	IdentifyType    .addText(blProposalCI.getBLPrpTitemCar().getArr(0).getInsuredTypeCode());
	    	IdentifyNumber  .addText(blProposalCI.getBLPrpTitemCar().getArr(0).getOwnerAddress());
	    	Mobile          .addText("");
	    	Email           .addText("");
	    	PostAddress     .addText("");
	    	PostCode        .addText("");
	    	PersonTypeClass .addText("");
	    	PersonGrade     .addText("");
	    	IndividualType  .addText("");
    	}
    }
    private void addItemKindList(Element ItemKindList, BLProposal blProposalCI, BLProposal blProposalBI) throws Exception {
    	if(blProposalCI!=null){
    		PrpTitemKindSchema prpTitemKindSchema = blProposalCI.getBLPrpTitemKind().getArr(0);
    		
	    	Element ItemKindData        = ItemKindList.addElement("ItemKindData");
	    	
		    Element RiskCode            = ItemKindData.addElement("RiskCode"           );
			Element KindCode            = ItemKindData.addElement("KindCode"           );
			Element KindName            = ItemKindData.addElement("KindName"           );
			Element ModeCode            = ItemKindData.addElement("ModeCode"           );
			Element ModeName            = ItemKindData.addElement("ModeName"           );
			Element ValueAmount         = ItemKindData.addElement("ValueAmount"        );
			Element UnitAmount          = ItemKindData.addElement("UnitAmount"         );
			Element Quantity            = ItemKindData.addElement("Quantity"           );
			Element Amount              = ItemKindData.addElement("Amount"             );
			Element DeductibleAmount    = ItemKindData.addElement("DeductibleAmount"   );
			Element DeductibleRate      = ItemKindData.addElement("DeductibleRate"     );
			Element ShortRate           = ItemKindData.addElement("ShortRate"          );
			Element AdjustRate          = ItemKindData.addElement("AdjustRate"         );
			Element FloatRate           = ItemKindData.addElement("FloatRate"          );
			Element Flag                = ItemKindData.addElement("Flag"               );
			Element Vehiclerepairplant  = ItemKindData.addElement("Vehiclerepairplant" );
			Element BenchMarkPremium    = ItemKindData.addElement("BenchMarkPremium"   );
			Element Premium             = ItemKindData.addElement("Premium"            );
			Element DisCount            = ItemKindData.addElement("DisCount"           );
			RiskCode           .addText(prpTitemKindSchema.getRiskCode());
			KindCode           .addText(prpTitemKindSchema.getKindCode());
			KindName           .addText(prpTitemKindSchema.getKindName());
			ModeCode           .addText(prpTitemKindSchema.getModeCode());
			ModeName           .addText(prpTitemKindSchema.getModeName());
			ValueAmount        .addText("");
			UnitAmount         .addText(prpTitemKindSchema.getUnitAmount());
			Quantity           .addText(prpTitemKindSchema.getQuantity());
			Amount             .addText(prpTitemKindSchema.getAmount());
			DeductibleAmount   .addText(prpTitemKindSchema.getValue());
			DeductibleRate     .addText(prpTitemKindSchema.getDeductibleRate());
			ShortRate          .addText(prpTitemKindSchema.getShortRate());
			AdjustRate         .addText(prpTitemKindSchema.getAdjustRate());
			FloatRate          .addText("");
			Flag               .addText(prpTitemKindSchema.getFlag());
			Vehiclerepairplant .addText(prpTitemKindSchema.getModel());
			BenchMarkPremium   .addText(prpTitemKindSchema.getBenchMarkPremium());
			Premium            .addText(prpTitemKindSchema.getPremium());
			DisCount           .addText(prpTitemKindSchema.getDiscount());
    	}
    	if(blProposalBI!=null){
    		for(int i = 0 ;i < blProposalBI.getBLPrpTitemKind().getSize() ; i++ ){
        		PrpTitemKindSchema prpTitemKindSchema = blProposalBI.getBLPrpTitemKind().getArr(i);
        		
    	    	Element ItemKindData        = ItemKindList.addElement("ItemKindData");
    	    	
    		    Element RiskCode            = ItemKindData.addElement("RiskCode"           );
    			Element KindCode            = ItemKindData.addElement("KindCode"           );
    			Element KindName            = ItemKindData.addElement("KindName"           );
    			Element ModeCode            = ItemKindData.addElement("ModeCode"           );
    			Element ModeName            = ItemKindData.addElement("ModeName"           );
    			Element ValueAmount         = ItemKindData.addElement("ValueAmount"        );
    			Element UnitAmount          = ItemKindData.addElement("UnitAmount"         );
    			Element Quantity            = ItemKindData.addElement("Quantity"           );
    			Element Amount              = ItemKindData.addElement("Amount"             );
    			Element DeductibleAmount    = ItemKindData.addElement("DeductibleAmount"   );
    			Element DeductibleRate      = ItemKindData.addElement("DeductibleRate"     );
    			Element ShortRate           = ItemKindData.addElement("ShortRate"          );
    			Element AdjustRate          = ItemKindData.addElement("AdjustRate"         );
    			Element FloatRate           = ItemKindData.addElement("FloatRate"          );
    			Element Flag                = ItemKindData.addElement("Flag"               );
    			Element Vehiclerepairplant  = ItemKindData.addElement("Vehiclerepairplant" );
    			Element BenchMarkPremium    = ItemKindData.addElement("BenchMarkPremium"   );
    			Element Premium             = ItemKindData.addElement("Premium"            );
    			Element DisCount            = ItemKindData.addElement("DisCount"           );
    			
    			RiskCode           .addText(prpTitemKindSchema.getRiskCode());
    			KindCode           .addText(prpTitemKindSchema.getKindCode());
    			KindName           .addText(prpTitemKindSchema.getKindName());
    			ModeCode           .addText(prpTitemKindSchema.getModeCode());
    			ModeName           .addText(prpTitemKindSchema.getModeName());
    			ValueAmount        .addText("");
    			UnitAmount         .addText(prpTitemKindSchema.getUnitAmount());
    			Quantity           .addText(prpTitemKindSchema.getQuantity());
    			Amount             .addText(prpTitemKindSchema.getAmount());
    			DeductibleAmount   .addText(prpTitemKindSchema.getValue());
    			DeductibleRate     .addText(prpTitemKindSchema.getDeductibleRate());
    			ShortRate          .addText(prpTitemKindSchema.getShortRate());
    			AdjustRate         .addText(prpTitemKindSchema.getAdjustRate());
    			FloatRate          .addText("");
    			Flag               .addText(prpTitemKindSchema.getFlag());
    			Vehiclerepairplant .addText(prpTitemKindSchema.getModel());
    			BenchMarkPremium   .addText(prpTitemKindSchema.getBenchMarkPremium());
    			Premium            .addText(prpTitemKindSchema.getPremium());
    			DisCount           .addText(prpTitemKindSchema.getDiscount());
    		}
    	}
    }
    
    private void addCarDeviceList(Element CarDeviceList, BLProposal blProposalBI) throws Exception {
    	if(blProposalBI!=null){
    		for(int i = 0 ;i < blProposalBI.getBLPrpTcarDevice().getSize() ; i++ ){
    			PrpTcarDeviceSchema prpTcarDeviceSchema = blProposalBI.getBLPrpTcarDevice().getArr(i);
    			
    			Element CarDeviceData= CarDeviceList.addElement("CarDeviceData");
    			
    			Element DeviceName   = CarDeviceData.addElement("DeviceName"  );
    			Element Count        = CarDeviceData.addElement("Count"       );
    			Element Price        = CarDeviceData.addElement("Price"       );
    			Element PurchaseDate = CarDeviceData.addElement("PurchaseDate");
    			Element ActualValue  = CarDeviceData.addElement("ActualValue" );
    			
    			DeviceName  .addText(prpTcarDeviceSchema.getDeviceName());
    			Count       .addText(prpTcarDeviceSchema.getQuantity());
    			Price       .addText(prpTcarDeviceSchema.getPurchasePrice());
    			PurchaseDate.addText(prpTcarDeviceSchema.getBuyDate());
    			ActualValue .addText(prpTcarDeviceSchema.getActualValue());
    		}
    	}
    }
    
    private void addCarDriverList(Element CarDriverList, BLProposal blProposalBI) throws Exception {
    	if(blProposalBI!=null){
    		for(int i = 0 ;i < blProposalBI.getBLPrpTcarDriver().getSize() ; i++ ){
    			PrpTcarDriverSchema prpTcarDriverSchema = blProposalBI.getBLPrpTcarDriver().getArr(i);
    			
    			Element CarDriverData     = CarDriverList.addElement("CarDriverData");
    			
    			Element DriverName        = CarDriverData.addElement("DriverName"       );
    			Element IdentifyNumber    = CarDriverData.addElement("IdentifyNumber"   );
    			Element Sex               = CarDriverData.addElement("Sex"              );
    			Element Age               = CarDriverData.addElement("Age"              );
    			Element MarriageFlag      = CarDriverData.addElement("MarriageFlag"     );
    			Element DrivingCarType    = CarDriverData.addElement("DrivingCarType"   );
    			Element AcceptLicenseDate = CarDriverData.addElement("AcceptLicenseDate");
    			Element DrivingYears      = CarDriverData.addElement("DrivingYears"     );
    			
    			DriverName        .addText(prpTcarDriverSchema.getDriverName());
    			IdentifyNumber    .addText(prpTcarDriverSchema.getIdentifyNumber());
    			Sex               .addText(prpTcarDriverSchema.getSex());
    			Age               .addText(prpTcarDriverSchema.getAge());
    			MarriageFlag      .addText(prpTcarDriverSchema.getMarriage());
    			DrivingCarType    .addText(prpTcarDriverSchema.getDrivingCarType());
    			AcceptLicenseDate .addText(prpTcarDriverSchema.getAcceptLicenseDate());
    			DrivingYears      .addText(prpTcarDriverSchema.getDrivingYears());
    		}
    	}
    }
    
    private void addCliamList(Element ClaimList, BLProposal blProposalCI, BLProposal blProposalBI) throws Exception {
    	if(blProposalBI!=null){
    		if(blProposalBI.getBLPrpTitemCar().getSize()>0){
    			BLCIInsureDemandPay blCIInsureDemandPay = new BLCIInsureDemandPay();
    			
				ArrayList<String> iWhereValue=new ArrayList<String>();
				iWhereValue.add(blProposalBI.getBLPrpTitemCar().getArr(0).getDemandNo());
				blCIInsureDemandPay.query(" DemandNo = ?",iWhereValue);
				
    			for(int i = 0 ;i < blCIInsureDemandPay.getSize() ; i++ ){
        			CIInsureDemandPaySchema ciInsureDemandPaySchema = blCIInsureDemandPay.getArr(i);
        			
        			Element CliamData        = ClaimList.addElement("CliamData");
        			
        			Element InsurerCode         = CliamData.addElement("InsurerCode"        );
        			Element PolicyNo            = CliamData.addElement("PolicyNo"           );
        			Element EffectiveDate       = CliamData.addElement("EffectiveDate"      );
        			Element ExpireDate          = CliamData.addElement("ExpireDate"         );
        			Element ClaimSequenceNo     = CliamData.addElement("ClaimSequenceNo"    );
        			Element ClaimNotificationNo = CliamData.addElement("ClaimNotificationNo");
        			Element ClaimRegistrationNo = CliamData.addElement("ClaimRegistrationNo");
        			Element CaseID              = CliamData.addElement("CaseID"             );
        			Element LossTime            = CliamData.addElement("LossTime"           );
        			Element ClaimCloseTime      = CliamData.addElement("ClaimCloseTime"     );
        			Element ClaimAmount         = CliamData.addElement("ClaimAmount"        );
        			Element ClaimDesc           = CliamData.addElement("ClaimDesc"          );
        			Element RiskFlag            = CliamData.addElement("RiskFlag"           );
        			
        			InsurerCode        .addText(ciInsureDemandPaySchema.getPayCompany());
        			PolicyNo           .addText(ciInsureDemandPaySchema.getPolicyNo());
        			EffectiveDate      .addText(ciInsureDemandPaySchema.getEffectiveDate());
        			ExpireDate         .addText(ciInsureDemandPaySchema.getExpireDate());
        			ClaimSequenceNo    .addText(ciInsureDemandPaySchema.getCompensateNo());
        			ClaimNotificationNo.addText(ciInsureDemandPaySchema.getClaimNotificationNo());
        			ClaimRegistrationNo.addText(ciInsureDemandPaySchema.getClaimRegistrationNo());
        			CaseID             .addText(ciInsureDemandPaySchema.getCaseID());
        			LossTime           .addText(ciInsureDemandPaySchema.getLossTime());
        			ClaimCloseTime     .addText(ciInsureDemandPaySchema.getEndCaseTime());
        			ClaimAmount        .addText(ciInsureDemandPaySchema.getTotalAmount());
        			ClaimDesc          .addText(ciInsureDemandPaySchema.getLossArea());
        			RiskFlag           .addText("1");
        		}
    		}
    	}
    	if(blProposalCI!=null){
    		if(blProposalCI.getBLPrpTitemCar().getSize()>0){
    			BLCIInsureDemandPay blCIInsureDemandPay = new BLCIInsureDemandPay();
    			
				ArrayList<String> iWhereValue=new ArrayList<String>();
				iWhereValue.add(blProposalCI.getBLPrpTitemCar().getArr(0).getDemandNo());
				blCIInsureDemandPay.query(" DemandNo = ?",iWhereValue);
				
	    		for(int i = 0 ;i < blCIInsureDemandPay.getSize() ; i++ ){
	    			CIInsureDemandPaySchema ciInsureDemandPaySchema = blCIInsureDemandPay.getArr(i);
	    			
	    			Element CliamData        = ClaimList.addElement("CliamData");
	    			
	    			Element InsurerCode         = CliamData.addElement("InsurerCode"        );
	    			Element PolicyNo            = CliamData.addElement("PolicyNo"           );
	    			Element EffectiveDate       = CliamData.addElement("EffectiveDate"      );
	    			Element ExpireDate          = CliamData.addElement("ExpireDate"         );
	    			Element ClaimSequenceNo     = CliamData.addElement("ClaimSequenceNo"    );
	    			Element ClaimNotificationNo = CliamData.addElement("ClaimNotificationNo");
	    			Element ClaimRegistrationNo = CliamData.addElement("ClaimRegistrationNo");
	    			Element CaseID              = CliamData.addElement("CaseID"             );
	    			Element LossTime            = CliamData.addElement("LossTime"           );
	    			Element ClaimCloseTime      = CliamData.addElement("ClaimCloseTime"     );
	    			Element ClaimAmount         = CliamData.addElement("ClaimAmount"        );
	    			Element ClaimDesc           = CliamData.addElement("ClaimDesc"          );
	    			Element RiskFlag            = CliamData.addElement("RiskFlag"           );
	    			
	    			InsurerCode        .addText(ciInsureDemandPaySchema.getPayCompany());
	    			PolicyNo           .addText(ciInsureDemandPaySchema.getPolicyNo());
	    			EffectiveDate      .addText(ciInsureDemandPaySchema.getEffectiveDate());
	    			ExpireDate         .addText(ciInsureDemandPaySchema.getExpireDate());
	    			ClaimSequenceNo    .addText(ciInsureDemandPaySchema.getCompensateNo());
	    			ClaimNotificationNo.addText(ciInsureDemandPaySchema.getClaimNotificationNo());
	    			ClaimRegistrationNo.addText(ciInsureDemandPaySchema.getClaimRegistrationNo());
	    			CaseID             .addText(ciInsureDemandPaySchema.getCaseID());
	    			LossTime           .addText(ciInsureDemandPaySchema.getLossTime());
	    			ClaimCloseTime     .addText(ciInsureDemandPaySchema.getEndCaseTime());
	    			ClaimAmount        .addText(ciInsureDemandPaySchema.getTotalAmount());
	    			ClaimDesc          .addText(ciInsureDemandPaySchema.getLossArea());
	    			RiskFlag           .addText("2");
	    		}
    		}
    	}
    }
    
    private void addPeccancyList(Element PeccancyList, BLProposal blProposalCI, BLProposal blProposalBI) throws Exception {
    	if(blProposalBI!=null){
    		if(blProposalBI.getBLPrpTitemCar().getSize()>0){
    			BLCIInsureDemandLoss blCIInsureDemandLoss = new BLCIInsureDemandLoss();
    			
    			ArrayList<String> iWhereValue=new ArrayList<String>();
    			iWhereValue.add(blProposalBI.getBLPrpTitemCar().getArr(0).getDemandNo());
    			blCIInsureDemandLoss.query(" demandno = ?",iWhereValue);
    			
	    		for(int i = 0 ;i < blCIInsureDemandLoss.getSize() ; i++ ){
	    			CIInsureDemandLossSchema ciInsureDemandLossSchema = blCIInsureDemandLoss.getArr(i);
	    			
	    			Element PeccancyData     = PeccancyList.addElement("PeccancyData");
	    			
	    			Element ViolationCode       = PeccancyData.addElement("ViolationCode"      );
	    			Element PeccancyType        = PeccancyData.addElement("PeccancyType"       );
	    			Element SerialNo            = PeccancyData.addElement("SerialNo"           );
	    			Element ViolationRecordCode = PeccancyData.addElement("ViolationRecordCode");
	    			Element PeccancyDesc        = PeccancyData.addElement("PeccancyDesc"       );
	    			Element PeccancyCateDesc    = PeccancyData.addElement("PeccancyCateDesc"   );
	    			Element DecisionCode        = PeccancyData.addElement("DecisionCode"       );
	    			Element DecisionTypeCode    = PeccancyData.addElement("DecisionTypeCode"   );
	    			Element LicenseNo           = PeccancyData.addElement("LicenseNo"          );
	    			Element LicenseCode         = PeccancyData.addElement("LicenseCode"        );
	    			Element ViolationPlace      = PeccancyData.addElement("ViolationPlace"     );
	    			Element ViolationTime       = PeccancyData.addElement("ViolationTime"      );
	    			Element RecognitionDate     = PeccancyData.addElement("RecognitionDate"    );
	    			Element JurisdictionAgency  = PeccancyData.addElement("JurisdictionAgency" );
	    			Element PeccancySort        = PeccancyData.addElement("PeccancySort"       );
	    			Element AdjustRate          = PeccancyData.addElement("AdjustRate"         );
	    			Element CertiType           = PeccancyData.addElement("CertiType"          );
	    			Element DriverNo            = PeccancyData.addElement("DriverNo"           );
	    			Element PeccancyStatus      = PeccancyData.addElement("PeccancyStatus"     );
	    			Element AdjustFlag          = PeccancyData.addElement("AdjustFlag"         );
	    			Element RiskFlag            = PeccancyData.addElement("RiskFlag"           );
	    			
	    			ViolationCode      .addText(ciInsureDemandLossSchema.getPeccancyNumber());
	    			PeccancyType       .addText(ciInsureDemandLossSchema.getLossType());
	    			SerialNo           .addText(ciInsureDemandLossSchema.getSerialNo());
	    			ViolationRecordCode.addText(ciInsureDemandLossSchema.getLossAction());
	    			PeccancyDesc       .addText("");
	    			PeccancyCateDesc   .addText("");
	    			DecisionCode       .addText("");
	    			DecisionTypeCode   .addText("");
	    			LicenseNo          .addText(blProposalBI.getBLPrpTitemCar().getArr(0).getLicenseNo());
	    			LicenseCode        .addText(blProposalBI.getBLPrpTitemCar().getArr(0).getLicenseKindCode());
	    			ViolationPlace     .addText(ciInsureDemandLossSchema.getLossAddress());
	    			ViolationTime      .addText(ciInsureDemandLossSchema.getLossTime());
	    			RecognitionDate    .addText(ciInsureDemandLossSchema.getSanctionDate());
	    			JurisdictionAgency .addText("");
	    			PeccancySort       .addText("");
	    			AdjustRate         .addText(ciInsureDemandLossSchema.getCoeff());
	    			CertiType          .addText("");
	    			DriverNo           .addText("");
	    			PeccancyStatus     .addText(ciInsureDemandLossSchema.getProcessingStatus());
	    			AdjustFlag         .addText(ciInsureDemandLossSchema.getAdjustFlag());
	    			RiskFlag           .addText("1");
	    		}
    		}
    	}
    	if(blProposalCI!=null){
    		if(blProposalCI.getBLPrpTitemCar().getSize()>0){
    			BLCIInsureDemandLoss blCIInsureDemandLoss = new BLCIInsureDemandLoss();
    			
    			ArrayList<String> iWhereValue=new ArrayList<String>();
    			iWhereValue.add(blProposalCI.getBLPrpTitemCar().getArr(0).getDemandNo());
    			blCIInsureDemandLoss.query(" demandno = ?",iWhereValue);
    			
	    		for(int i = 0 ;i < blCIInsureDemandLoss.getSize() ; i++ ){
	    			CIInsureDemandLossSchema ciInsureDemandLossSchema = blCIInsureDemandLoss.getArr(i);
	    			
	    			Element PeccancyData     = PeccancyList.addElement("PeccancyData");
	    			
	    			Element ViolationCode       = PeccancyData.addElement("ViolationCode"      );
	    			Element PeccancyType        = PeccancyData.addElement("PeccancyType"       );
	    			Element SerialNo            = PeccancyData.addElement("SerialNo"           );
	    			Element ViolationRecordCode = PeccancyData.addElement("ViolationRecordCode");
	    			Element PeccancyDesc        = PeccancyData.addElement("PeccancyDesc"       );
	    			Element PeccancyCateDesc    = PeccancyData.addElement("PeccancyCateDesc"   );
	    			Element DecisionCode        = PeccancyData.addElement("DecisionCode"       );
	    			Element DecisionTypeCode    = PeccancyData.addElement("DecisionTypeCode"   );
	    			Element LicenseNo           = PeccancyData.addElement("LicenseNo"          );
	    			Element LicenseCode         = PeccancyData.addElement("LicenseCode"        );
	    			Element ViolationPlace      = PeccancyData.addElement("ViolationPlace"     );
	    			Element ViolationTime       = PeccancyData.addElement("ViolationTime"      );
	    			Element RecognitionDate     = PeccancyData.addElement("RecognitionDate"    );
	    			Element JurisdictionAgency  = PeccancyData.addElement("JurisdictionAgency" );
	    			Element PeccancySort        = PeccancyData.addElement("PeccancySort"       );
	    			Element AdjustRate          = PeccancyData.addElement("AdjustRate"         );
	    			Element CertiType           = PeccancyData.addElement("CertiType"          );
	    			Element DriverNo            = PeccancyData.addElement("DriverNo"           );
	    			Element PeccancyStatus      = PeccancyData.addElement("PeccancyStatus"     );
	    			Element AdjustFlag          = PeccancyData.addElement("AdjustFlag"         );
	    			Element RiskFlag            = PeccancyData.addElement("RiskFlag"           );
	    			
	    			ViolationCode      .addText(ciInsureDemandLossSchema.getPeccancyNumber());
	    			PeccancyType       .addText(ciInsureDemandLossSchema.getLossType());
	    			SerialNo           .addText(ciInsureDemandLossSchema.getSerialNo());
	    			ViolationRecordCode.addText(ciInsureDemandLossSchema.getLossAction());
	    			PeccancyDesc       .addText("");
	    			PeccancyCateDesc   .addText("");
	    			DecisionCode       .addText("");
	    			DecisionTypeCode   .addText("");
	    			LicenseNo          .addText(blProposalCI.getBLPrpTitemCar().getArr(0).getLicenseNo());
	    			LicenseCode        .addText(blProposalCI.getBLPrpTitemCar().getArr(0).getLicenseKindCode());
	    			ViolationPlace     .addText(ciInsureDemandLossSchema.getLossAddress());
	    			ViolationTime      .addText(ciInsureDemandLossSchema.getLossTime());
	    			RecognitionDate    .addText(ciInsureDemandLossSchema.getSanctionDate());
	    			JurisdictionAgency .addText("");
	    			PeccancySort       .addText("");
	    			AdjustRate         .addText(ciInsureDemandLossSchema.getCoeff());
	    			CertiType          .addText("");
	    			DriverNo           .addText("");
	    			PeccancyStatus     .addText(ciInsureDemandLossSchema.getProcessingStatus());
	    			AdjustFlag         .addText(ciInsureDemandLossSchema.getAdjustFlag());
	    			RiskFlag           .addText("2");
	    		}
    		}
    	}
    }
    
    private void addEngageList(Element EngageList, BLProposal blProposalCI, BLProposal blProposalBI) throws Exception {
    	if(blProposalBI!=null){
    		for(int i = 0 ;i < blProposalBI.getBLPrpTengage().getSize() ; i++ ){
    			PrpTengageSchema prpTengageSchema = blProposalBI.getBLPrpTengage().getArr(i);
    			
    			Element EngageData = EngageList.addElement("EngageData");
    			
    			Element ClauseCode = EngageData.addElement("ClauseCode");
    			Element Serialno   = EngageData.addElement("Serialno"  );
    			Element Lineno     = EngageData.addElement("Lineno"    );
    			Element TitleFlag  = EngageData.addElement("TitleFlag" );
    			Element ClauseName = EngageData.addElement("ClauseName");
    			Element Content    = EngageData.addElement("Content"   );
    			Element RiskFlag   = EngageData.addElement("RiskFlag"  );
    			
    			ClauseCode.addText(prpTengageSchema.getClauseCode());
    			Serialno  .addText(prpTengageSchema.getSerialNo());
    			Lineno    .addText(prpTengageSchema.getLineNo());
    			TitleFlag .addText(prpTengageSchema.getTitleFlag());
    			if("0".equals(prpTengageSchema.getTitleFlag())){
    				ClauseName.addText(prpTengageSchema.getClauses());
    			}else{
    				ClauseName.addText("");
    			}
    			Content   .addText(prpTengageSchema.getClauses());
    			RiskFlag  .addText("1");
    		}
    	}
    	if(blProposalCI!=null){
    		for(int i = 0 ;i < blProposalCI.getBLPrpTengage().getSize() ; i++ ){
    			PrpTengageSchema prpTengageSchema = blProposalCI.getBLPrpTengage().getArr(i);
    			
    			Element EngageData = EngageList.addElement("EngageData");
    			
    			Element ClauseCode = EngageData.addElement("ClauseCode");
    			Element Serialno   = EngageData.addElement("Serialno"  );
    			Element Lineno     = EngageData.addElement("Lineno"    );
    			Element TitleFlag  = EngageData.addElement("TitleFlag" );
    			Element ClauseName = EngageData.addElement("ClauseName");
    			Element Content    = EngageData.addElement("Content"   );
    			Element RiskFlag   = EngageData.addElement("RiskFlag"  );
    			
    			ClauseCode.addText(prpTengageSchema.getClauseCode());
    			Serialno  .addText(prpTengageSchema.getSerialNo());
    			Lineno    .addText(prpTengageSchema.getLineNo());
    			TitleFlag .addText(prpTengageSchema.getTitleFlag());
    			if("0".equals(TitleFlag)){
    				ClauseName.addText(prpTengageSchema.getClauses());
    			}else{
    				ClauseName.addText("");
    			}
    			Content   .addText(prpTengageSchema.getClauses());
    			RiskFlag  .addText("2");
    		}
    	}
    }
    
    public String encodeException(String errorMessage) throws IOException, DocumentException{
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("PACKET");
		root.addAttribute("type", "RESPONSE");
		root.addAttribute("version", "1.0");
		
		Element head = root.addElement("HEAD");
		Element REQUESTTYPE = head.addElement("REQUESTTYPE");
		REQUESTTYPE.addText("05");
		
		Element responseCode = head.addElement("RESPONSECODE");
		Element responseMessage = head.addElement("RESPONSEMESSAGE");
		
		responseCode.addText("100000");
		responseMessage.addText(errorMessage);
			
		return formateXml(doc.asXML());
	}

    public String formateXml(String xmlStr) throws IOException, DocumentException{
           String encoding = "GBK";
           Document doc = DocumentHelper.parseText(xmlStr);

           StringWriter writer = new StringWriter();
           OutputFormat format = OutputFormat.createPrettyPrint();
           format.setTrimText(false);
           format.setEncoding(encoding);
           format.setExpandEmptyElements(true);
          
           XMLWriter xmlwriter = new XMLWriter(writer, format);
           xmlwriter.write(doc);
           return writer.toString().replaceAll("&lt;", "＜");
    }
}
