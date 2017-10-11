package com.sp.indiv.ci.interf;

import org.apache.log4j.Logger;

import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.prpall.pubfun.TransCodeCI;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utiall.blsvr.BLPrpDcustomerIdvNew;
import com.sp.utiall.schema.PrpDcustomerIdvNewSchema;
import com.sp.utility.Transfer;
import com.sp.utility.UtiPower;
import com.sp.utility.error.UserException;

public class EndorseIdentityEncoder {

	private static Logger logger = Logger.getLogger(EndorseIdentityEncoder.class);
	
	/**
	 * 
	 * @param blProposal
	 * @return 身份证采集发送XML格式串
	 * @throws Exception
	 */
	public String encode(BLPolicy iPolicy, BLEndorse iEndorse) throws Exception {
		StringBuffer buf = new StringBuffer(4000);
		addXMLHead(buf);
		addPacket(buf,iPolicy,iEndorse);
		logger.info("[批改身份证采集发送报文]:"+buf.toString());
		return buf.toString();
	}
	
	/**
	 * 
	 * @param buf
	 * @throws Exception
	 */
	protected void addXMLHead(StringBuffer buf) throws Exception {
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
	}
	
	protected void addPacket(StringBuffer buf, BLPolicy iPolicy, BLEndorse iEndorse)
			throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
		addHead(buf, iPolicy, iEndorse);
		addBody(buf, iPolicy, iEndorse);
		buf.append("</PACKET>");

	}
	
	protected void addHead(StringBuffer buf, BLPolicy iPolicy, BLEndorse iEndorse)
			throws Exception {
		String strComCode = iPolicy.getBLPrpCmain().getArr(0).getComCode();
		buf.append("<HEAD>");
		addNode(buf, "REQUEST_TYPE", "B3");
		addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_USER"));
		addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_PASSWORD"));
		buf.append("</HEAD>");
	}
	
	protected void addBody(StringBuffer buf,BLPolicy iPolicy, BLEndorse iEndorse)
			throws Exception {
		buf.append("<BODY>");
		addBasePart(buf, iPolicy, iEndorse);
		addHolderList(buf, iPolicy, iEndorse);
		addInsuredList(buf, iPolicy, iEndorse);
		buf.append("</BODY>");
	}
	
	/**
	 * 基础信息
	 * @param buf
	 * @param blProposal
	 * @throws Exception
	 */
	protected void addBasePart(StringBuffer buf,BLPolicy iPolicy, BLEndorse iEndorse)
			throws Exception {	
		String strType = "";
		String strAmendQueryNo = "";
		String strCancelQueryNo = "";
		String strClaimCode = "";
		String strMachineCode = "";
		String strSalesChannel = "";
		
		strType = "2";
		strAmendQueryNo = iPolicy.getBLCIEndorValid().getArr(0).getDemandNo();
		if(iPolicy.getBlCIIdentifyInfo().getSize()>0){
			if(iPolicy.getBlCIIdentifyInfo().getArr(0)!=null&&iPolicy.getBlCIIdentifyInfo().getArr(0).getCollectorCode()!=null&&!"".equals(iPolicy.getBlCIIdentifyInfo().getArr(0).getCollectorCode())){
				strMachineCode = iPolicy.getBlCIIdentifyInfo().getArr(0).getCollectorCode();
			}else if(iPolicy.getBlCIIdentifyInfo().getArr(1)!=null&&iPolicy.getBlCIIdentifyInfo().getArr(1).getCollectorCode()!=null&&!"".equals(iPolicy.getBlCIIdentifyInfo().getArr(1).getCollectorCode())){
				strMachineCode = iPolicy.getBlCIIdentifyInfo().getArr(1).getCollectorCode();
			} 
		}
		if (new UtiPower().checkSinoCommission(iPolicy.getBLPrpCmain().getArr(0).getComCode(),iPolicy.getBLPrpCmain().getArr(0).getOperateDate())) {
			strSalesChannel = Transfer.getTransfer("SinoCommission", "BusinessNature", iPolicy.getBLPrpCmain().getArr(0).getBusinessNature());
		}else if(new UtiPower().checkSinoCommissionHis(iPolicy.getBLPrpCmain().getArr(0).getComCode(),iPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
			strSalesChannel = Transfer.getTransfer("SinoCommission", "BusinessNatureCIHis", iPolicy.getBLPrpCmain().getArr(0).getBusinessNature());
		}else{
			if (new UtiPower().checkSalesChannelCISwitch(iPolicy.getBLPrpCmain().getArr(0).getComCode())) {
				strSalesChannel = Transfer.getTransfer("SinoCommission", "BusinessNature", iPolicy.getBLPrpCmain().getArr(0).getBusinessNature());
			}else{
				strSalesChannel = TransCodeCI.getTransferCI(iPolicy.getBLPrpCmain().getArr(0).getComCode(), "BusinessNature", iPolicy.getBLPrpCmain().getArr(0).getBusinessNature());
			}
		}
		
		buf.append("<BASE_PART>");
		addNode(buf, "TYPE", strType);
		addNode(buf, "AMEND_QUERY_NO", strAmendQueryNo);
		addNode(buf, "CANCEL_QUERY_NO", strCancelQueryNo);
		addNode(buf, "CLAIM_CODE", strClaimCode);
		addNode(buf, "MACHINE_CODE", strMachineCode);
		addNode(buf, "SALES_CHANNEL", strSalesChannel);
		buf.append("</BASE_PART>");
	}
	
	/**
	 * XX人信息
	 * @param buf
	 * @param blProposal
	 * @throws Exception
	 */
	protected void addHolderList(StringBuffer buf,BLPolicy iPolicy, BLEndorse iEndorse)
			throws Exception {
		BLPrpDcustomerIdvNew blPrpDcustomerIdvNew = new BLPrpDcustomerIdvNew();
		PrpDcustomerIdvNewSchema prpDcustomerIdvNewSchema = new PrpDcustomerIdvNewSchema();
		String strCName = "";
		String strCGender = "";
		String strCNation = "";
		String strCBirthDatr = "";
		String strCAdderss = "";
		String strCCertiCode = "";
		String strCIssuer = "";
		String strCCertiStartDate = "";
		String strCCertiEndDate = "";
		String strCTelephone = "";
		String strCCustomerType = "";
		for (int i = 0; i < iPolicy.getBLPrpCinsured().getSize(); i++) {
			
			if("2".equals(iPolicy.getBLPrpCinsured().getArr(i).getInsuredFlag())
					&& "01".equals(iPolicy.getBLPrpCinsured().getArr(i).getIdentifyType())){
				strCName = iPolicy.getBLPrpCinsured().getArr(i).getInsuredName();
				strCAdderss = iPolicy.getBLPrpCinsured().getArr(i).getInsuredAddress();
				strCCertiCode = iPolicy.getBLPrpCinsured().getArr(i).getIdentifyNumber();
				strCTelephone = iPolicy.getBLPrpCinsured().getArr(i).getMobile();
				strCCustomerType = "2";
				blPrpDcustomerIdvNew.query("customerCode='"+iPolicy.getBLPrpCinsured().getArr(i).getInsuredCode()+"'");
				if(blPrpDcustomerIdvNew.getSize()>0){
					if(strCCertiCode == null || "".equals(strCCertiCode)){
						strCCertiCode=blPrpDcustomerIdvNew.getArr(0).getIdentifyNumber();
					}
					if(strCTelephone==null||"".equals(strCTelephone)){
						strCTelephone=blPrpDcustomerIdvNew.getArr(0).getMobile();
					}
				}
				if(strCTelephone==null||"".equals(strCTelephone)){
					strCTelephone="11111111111";
				}
				if(strCCertiCode == null || "".equals(strCCertiCode)){
					throw new Exception("身份证号码为空!");
				}else{
					blPrpDcustomerIdvNew.query("identifytype='01' and identifynumber = '" + strCCertiCode + "'");
					if(blPrpDcustomerIdvNew.getSize() <= 0){
						throw new Exception("XXXXX信息为空!");
					}
				}
				prpDcustomerIdvNewSchema = blPrpDcustomerIdvNew.getArr(0);
				strCCertiStartDate = prpDcustomerIdvNewSchema.getIdentifyUsefullifeStart();
				if(strCCertiStartDate==null||"".equals(strCCertiStartDate)){
					strCCertiStartDate="2010-04-30";
				}
				strCCertiEndDate = prpDcustomerIdvNewSchema.getIdentifyUsefullife();
				if(strCCertiEndDate==null||"".equals(strCCertiEndDate)){
					strCCertiEndDate="2099-04-30";
				}
				strCGender = prpDcustomerIdvNewSchema.getSex();
				strCBirthDatr = prpDcustomerIdvNewSchema.getBirthDate();
				
				for (int j = 0; j < iPolicy.getBlCIIdentifyInfo().getSize(); j++) {
					if("2".equals(iPolicy.getBlCIIdentifyInfo().getArr(j).getSerialNo())){
						strCNation = iPolicy.getBlCIIdentifyInfo().getArr(j).getNation();
						strCIssuer = iPolicy.getBlCIIdentifyInfo().getArr(j).getSigner();
					}
				}
			}else if("2".equals(iPolicy.getBLPrpCinsured().getArr(i).getInsuredFlag())
					&&!"01".equals(iPolicy.getBLPrpCinsured().getArr(i).getIdentifyType())){
				strCName = iPolicy.getBLPrpCinsured().getArr(i).getInsuredName();
				strCAdderss = iPolicy.getBLPrpCinsured().getArr(i).getInsuredAddress();
				strCCertiCode = iPolicy.getBLPrpCinsured().getArr(i).getIdentifyNumber();
				blPrpDcustomerIdvNew.query("customerCode='"+iPolicy.getBLPrpCinsured().getArr(i).getInsuredCode()+"'");
				if(strCCertiCode == null || "".equals(strCCertiCode)){
					if(blPrpDcustomerIdvNew.getSize()>0){
						strCCertiCode=blPrpDcustomerIdvNew.getArr(0).getIdentifyNumber();
					}
				}
				strCTelephone = iPolicy.getBLPrpCinsured().getArr(i).getMobile();
				strCCustomerType = "1";
			}
			
			if(UtiPower.isIdentifyCheck(iPolicy.getBLPrpCmain().getArr(0).getComCode(), iPolicy.getBLPrpCmain().getArr(0).getRiskCode())
					&& "2".equals(iPolicy.getBLPrpCinsured().getArr(i).getInsuredFlag()))
			{
				
				if("2".equals(iPolicy.getBLPrpCinsured().getArr(i).getInsuredType()))
				{
					strCCustomerType = "1";	
				}
				else if("1".equals(iPolicy.getBLPrpCinsured().getArr(i).getInsuredType()))
				{
					
					if("01".equals(iPolicy.getBLPrpCinsured().getArr(i).getIdentifyType()))
					{
						strCCustomerType = "2";	
					}
					else if("04".equals(iPolicy.getBLPrpCinsured().getArr(i).getIdentifyType()))
					{
						strCCustomerType = "3";	
					}
					else if("03".equals(iPolicy.getBLPrpCinsured().getArr(i).getIdentifyType()))
					{
						strCCustomerType = "4";	
					}
				}
			}
			
		}
		
		buf.append("<HOLDER_LIST>");
		for (int i = 0; i < iEndorse.getBLPrpPinsured().getSize(); i++) {
			
			if("2".equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredFlag())
					&&!(iPolicy.getBLPrpCinsured().getArr(1).getInsuredCode().equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredCode())||iPolicy.getBLPrpCinsured().getArr(1).getInsuredName().equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredName())&&iPolicy.getBLPrpCinsured().getArr(1).getIdentifyNumber().equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyNumber()))){
				BLPrpDcustomerIdvNew blPrpDcustomerIdvNew1 = new BLPrpDcustomerIdvNew();
				PrpDcustomerIdvNewSchema prpDcustomerIdvNewSchema1 = new PrpDcustomerIdvNewSchema();
				String strCName1 = "";
				String strCGender1 = "";
				String strCNation1 = "";
				String strCBirthDatr1 = "";
				String strCAdderss1 = "";
				String strCCertiCode1 = "";
				String strCIssuer1 = "";
				String strCCertiStartDate1 = "";
				String strCCertiEndDate1 = "";
				String strCTelephone1 = "";
				String strCCustomerType1 = "";
				if("01".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType())){
					strCName1 = iEndorse.getBLPrpPinsured().getArr(i).getInsuredName();
					strCAdderss1 = iEndorse.getBLPrpPinsured().getArr(i).getInsuredAddress();
					strCCertiCode1 = iEndorse.getBLPrpPinsured().getArr(i).getIdentifyNumber();
					strCTelephone1 = iEndorse.getBLPrpPinsured().getArr(i).getMobile();
					if(strCTelephone1==null||"".equals(strCTelephone1)){
						strCTelephone1="11111111111";
					}
					strCCustomerType1 = "2";
					blPrpDcustomerIdvNew1.query("customerCode='"+iEndorse.getBLPrpPinsured().getArr(i).getInsuredCode()+"'");
					if(strCCertiCode1 == null || "".equals(strCCertiCode1)){
						if(blPrpDcustomerIdvNew1.getSize()>0){
							strCCertiCode1=blPrpDcustomerIdvNew1.getArr(0).getIdentifyNumber();
						}
					}
					if(strCCertiCode1 == null || "".equals(strCCertiCode1)){
						throw new Exception("身份证号码为空!");
					}else{
						blPrpDcustomerIdvNew1.query("identifytype='01' and identifynumber = '" + strCCertiCode1 + "'");
						if(blPrpDcustomerIdvNew1.getSize() <= 0){
							throw new Exception("XXXXX信息为空!");
						}
					}
					prpDcustomerIdvNewSchema1 = blPrpDcustomerIdvNew1.getArr(0);
					strCCertiStartDate1 = prpDcustomerIdvNewSchema1.getIdentifyUsefullifeStart();
					if(strCCertiStartDate1==null||"".equals(strCCertiStartDate1)){
						strCCertiStartDate1="2010-04-30";
					}
					strCCertiEndDate1 = prpDcustomerIdvNewSchema1.getIdentifyUsefullife();
					if(strCCertiEndDate1==null||"".equals(strCCertiEndDate1)){
						strCCertiEndDate1="2099-04-30";
					}
					strCGender1 = prpDcustomerIdvNewSchema1.getSex();
					strCBirthDatr1 = prpDcustomerIdvNewSchema1.getBirthDate();
					
					for (int j = 0; j < iEndorse.getBlCIIdentifyInfo().getSize(); j++) {
						if("2".equals(iEndorse.getBlCIIdentifyInfo().getArr(j).getSerialNo())){
							strCNation1 = iEndorse.getBlCIIdentifyInfo().getArr(j).getNation();
							strCIssuer1 = iEndorse.getBlCIIdentifyInfo().getArr(j).getSigner();
						}
					}
				}else if(!"01".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType())){
					strCName1 = iEndorse.getBLPrpPinsured().getArr(i).getInsuredName();
					strCAdderss1 = iEndorse.getBLPrpPinsured().getArr(i).getInsuredAddress();
					strCCertiCode1 = iEndorse.getBLPrpPinsured().getArr(i).getIdentifyNumber();
					blPrpDcustomerIdvNew1.query("customerCode='"+iEndorse.getBLPrpPinsured().getArr(i).getInsuredCode()+"'");
					if(strCCertiCode1 == null || "".equals(strCCertiCode1)){
						if(blPrpDcustomerIdvNew1.getSize()>0){
							strCCertiCode1=blPrpDcustomerIdvNew1.getArr(0).getIdentifyNumber();
						}
					}
					strCTelephone1 = iEndorse.getBLPrpPinsured().getArr(i).getMobile();
					strCCustomerType1 = "1";
				}
				
				if(UtiPower.isIdentifyCheck(iEndorse.getBLPrpPmain().getArr(0).getComCode(), iEndorse.getBLPrpPmain().getArr(0).getRiskCode())
						&& "2".equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredFlag()))
				{
					
					if("2".equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredType()))
					{
						strCCustomerType1 = "1";	
					}
					else if("1".equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredType()))
					{
						
						if("01".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType()))
						{
							strCCustomerType1 = "2";	
						}
						else if("04".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType()))
						{
							strCCustomerType1 = "3";	
						}
						else if("03".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType()))
						{
							strCCustomerType1 = "4";	
						}
					}
				}
				
				buf.append("<HOLDER_DATA>");
				addNode(buf, "NAME", strCName1);
				addNode(buf, "GENDER", strCGender1);
				addNode(buf, "NATION", strCNation1);
				addNode(buf, "BIRTH_DATE", checkStringDate(strCBirthDatr1));
				addNode(buf, "ADDRESS", strCAdderss1);
				addNode(buf, "CERTI_CODE", strCCertiCode1);
				addNode(buf, "ISSUER", strCIssuer1);
				addNode(buf, "CERTI_START_DATE", checkStringDate(strCCertiStartDate1));
				addNode(buf, "CERTI_END_DATE", checkStringDate(strCCertiEndDate1));
				addNode(buf, "TELEPHONE", strCTelephone1);
				addNode(buf, "CUSTOMER_TYPE", strCCustomerType1);
				buf.append("</HOLDER_DATA>");
			}
		}
			buf.append("<HOLDER_DATA>");
			addNode(buf, "NAME", strCName);
			addNode(buf, "GENDER", strCGender);
			addNode(buf, "NATION", strCNation);
			addNode(buf, "BIRTH_DATE", checkStringDate(strCBirthDatr));
			addNode(buf, "ADDRESS", strCAdderss);
			addNode(buf, "CERTI_CODE", strCCertiCode);
			addNode(buf, "ISSUER", strCIssuer);
			addNode(buf, "CERTI_START_DATE", checkStringDate(strCCertiStartDate));
			addNode(buf, "CERTI_END_DATE", checkStringDate(strCCertiEndDate));
			addNode(buf, "TELEPHONE", strCTelephone);
			addNode(buf, "CUSTOMER_TYPE", strCCustomerType);
			buf.append("</HOLDER_DATA>");
		buf.append("</HOLDER_LIST>");
	}
	
	/**
	 * 被XX人信息
	 * @param buf
	 * @param blProposal
	 * @throws Exception
	 */
	protected void addInsuredList(StringBuffer buf,BLPolicy iPolicy, BLEndorse iEndorse)
			throws Exception {
		BLPrpDcustomerIdvNew blPrpDcustomerIdvNew = new BLPrpDcustomerIdvNew();
		PrpDcustomerIdvNewSchema prpDcustomerIdvNewSchema = new PrpDcustomerIdvNewSchema();
		String strCName = "";
		String strCGender = "";
		String strCNation = "";
		String strCBirthDatr = "";
		String strCAdderss = "";
		String strCCertiCode = "";
		String strCIssuer = "";
		String strCCertiStartDate = "";
		String strCCertiEndDate = "";
		String strCTelephone = "";
		String strCCustomerType = "";
		for (int i = 0; i < iPolicy.getBLPrpCinsured().getSize(); i++) {
			
			if("1".equals(iPolicy.getBLPrpCinsured().getArr(i).getInsuredFlag())
					&& "01".equals(iPolicy.getBLPrpCinsured().getArr(i).getIdentifyType())){
				strCName = iPolicy.getBLPrpCinsured().getArr(i).getInsuredName();
				strCAdderss = iPolicy.getBLPrpCinsured().getArr(i).getInsuredAddress();
				strCCertiCode = iPolicy.getBLPrpCinsured().getArr(i).getIdentifyNumber();
				strCTelephone = iPolicy.getBLPrpCinsured().getArr(i).getMobile();
				strCCustomerType = "2";
				blPrpDcustomerIdvNew.query("customerCode='"+iPolicy.getBLPrpCinsured().getArr(i).getInsuredCode()+"'");
				if(blPrpDcustomerIdvNew.getSize()>0){
					if(strCCertiCode == null || "".equals(strCCertiCode)){
						strCCertiCode=blPrpDcustomerIdvNew.getArr(0).getIdentifyNumber();
					}
					if(strCTelephone==null||"".equals(strCTelephone)){
						strCTelephone=blPrpDcustomerIdvNew.getArr(0).getMobile();
					}
				}
				if(strCTelephone==null||"".equals(strCTelephone)){
					strCTelephone="11111111111";
				}
				if(strCCertiCode == null || "".equals(strCCertiCode)){
					throw new Exception("身份证号码为空!");
				}else{
					blPrpDcustomerIdvNew.query("identifytype='01' and identifynumber = '" + strCCertiCode + "'");
					if(blPrpDcustomerIdvNew.getSize() <= 0){
						throw new Exception("XXXXX信息为空!");
					}
				}
				prpDcustomerIdvNewSchema = blPrpDcustomerIdvNew.getArr(0);
				strCCertiStartDate = prpDcustomerIdvNewSchema.getIdentifyUsefullifeStart();
				if(strCCertiStartDate==null||"".equals(strCCertiStartDate)){
					strCCertiStartDate="2010-04-30";
				}
				strCCertiEndDate = prpDcustomerIdvNewSchema.getIdentifyUsefullife();
				if(strCCertiEndDate==null||"".equals(strCCertiEndDate)){
					strCCertiEndDate="2099-04-30";
				}
				strCGender = prpDcustomerIdvNewSchema.getSex();
				strCBirthDatr = prpDcustomerIdvNewSchema.getBirthDate();
				
				for (int j = 0; j < iPolicy.getBlCIIdentifyInfo().getSize(); j++) {
					if("1".equals(iPolicy.getBlCIIdentifyInfo().getArr(j).getSerialNo())){
						strCNation = iPolicy.getBlCIIdentifyInfo().getArr(j).getNation();
						strCIssuer = iPolicy.getBlCIIdentifyInfo().getArr(j).getSigner();
					}
				}
			}else if("1".equals(iPolicy.getBLPrpCinsured().getArr(i).getInsuredFlag())
					&&!"01".equals(iPolicy.getBLPrpCinsured().getArr(i).getIdentifyType())){
				strCName = iPolicy.getBLPrpCinsured().getArr(i).getInsuredName();
				strCAdderss = iPolicy.getBLPrpCinsured().getArr(i).getInsuredAddress();
				strCCertiCode = iPolicy.getBLPrpCinsured().getArr(i).getIdentifyNumber();
				blPrpDcustomerIdvNew.query("customerCode='"+iPolicy.getBLPrpCinsured().getArr(i).getInsuredCode()+"'");
				if(strCCertiCode == null || "".equals(strCCertiCode)){
					if(blPrpDcustomerIdvNew.getSize()>0){
						strCCertiCode=blPrpDcustomerIdvNew.getArr(0).getIdentifyNumber();
					}
				}
				strCTelephone = iPolicy.getBLPrpCinsured().getArr(i).getMobile();
				strCCustomerType= "1";
			}
			
			if(UtiPower.isIdentifyCheck(iPolicy.getBLPrpCmain().getArr(0).getComCode(), iPolicy.getBLPrpCmain().getArr(0).getRiskCode())
					&& "1".equals(iPolicy.getBLPrpCinsured().getArr(i).getInsuredFlag()))
			{
				
				if("2".equals(iPolicy.getBLPrpCinsured().getArr(i).getInsuredType()))
				{
					strCCustomerType = "1";	
				}
				else if("1".equals(iPolicy.getBLPrpCinsured().getArr(i).getInsuredType()))
				{
					
					if("01".equals(iPolicy.getBLPrpCinsured().getArr(i).getIdentifyType()))
					{
						strCCustomerType = "2";	
					}
					else if("04".equals(iPolicy.getBLPrpCinsured().getArr(i).getIdentifyType()))
					{
						strCCustomerType = "3";	
					}
					else if("03".equals(iPolicy.getBLPrpCinsured().getArr(i).getIdentifyType()))
					{
						strCCustomerType = "4";	
					}
				}
			}
			
		}

		buf.append("<INSURED_LIST>");
		for (int i = 0; i < iEndorse.getBLPrpPinsured().getSize(); i++) {
			
			if("1".equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredFlag())
					&&!(iPolicy.getBLPrpCinsured().getArr(0).getInsuredCode().equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredCode())||iPolicy.getBLPrpCinsured().getArr(0).getInsuredName().equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredName())&&iPolicy.getBLPrpCinsured().getArr(0).getIdentifyNumber().equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyNumber()))){
				BLPrpDcustomerIdvNew blPrpDcustomerIdvNew1 = new BLPrpDcustomerIdvNew();
				PrpDcustomerIdvNewSchema prpDcustomerIdvNewSchema1 = new PrpDcustomerIdvNewSchema();
				String strCName1 = "";
				String strCGender1 = "";
				String strCNation1 = "";
				String strCBirthDatr1 = "";
				String strCAdderss1 = "";
				String strCCertiCode1 = "";
				String strCIssuer1 = "";
				String strCCertiStartDate1 = "";
				String strCCertiEndDate1 = "";
				String strCTelephone1 = "";
				String strCCustomerType1 = "";
				if("01".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType())){
					strCName1 = iEndorse.getBLPrpPinsured().getArr(i).getInsuredName();
					strCAdderss1 = iEndorse.getBLPrpPinsured().getArr(i).getInsuredAddress();
					strCCertiCode1 = iEndorse.getBLPrpPinsured().getArr(i).getIdentifyNumber();
					strCTelephone1 = iEndorse.getBLPrpPinsured().getArr(i).getMobile();
					if(strCTelephone1==null||"".equals(strCTelephone1)){
						strCTelephone1="11111111111";
					}
					strCCustomerType1 = "2";
					blPrpDcustomerIdvNew1.query("customerCode='"+iEndorse.getBLPrpPinsured().getArr(i).getInsuredCode()+"'");
					if(strCCertiCode1 == null || "".equals(strCCertiCode1)){
						if(blPrpDcustomerIdvNew1.getSize()>0){
							strCCertiCode1=blPrpDcustomerIdvNew1.getArr(0).getIdentifyNumber();
						}
					}
					if(strCCertiCode1 == null || "".equals(strCCertiCode1)){
						throw new Exception("身份证号码为空!");
					}else{
						blPrpDcustomerIdvNew1.query("identifytype='01' and identifynumber = '" + strCCertiCode1 + "'");
						if(blPrpDcustomerIdvNew1.getSize() <= 0){
							throw new Exception("XXXXX信息为空!");
						}
					}
					prpDcustomerIdvNewSchema1 = blPrpDcustomerIdvNew1.getArr(0);
					strCCertiStartDate1 = prpDcustomerIdvNewSchema1.getIdentifyUsefullifeStart();
					if(strCCertiStartDate1==null||"".equals(strCCertiStartDate1)){
						strCCertiStartDate1="2010-04-30";
					}
					strCCertiEndDate1 = prpDcustomerIdvNewSchema1.getIdentifyUsefullife();
					if(strCCertiEndDate1==null||"".equals(strCCertiEndDate1)){
						strCCertiEndDate1="2099-04-30";
					}
					strCGender1 = prpDcustomerIdvNewSchema1.getSex();
					strCBirthDatr1 = prpDcustomerIdvNewSchema1.getBirthDate();
					
					for (int j = 0; j < iEndorse.getBlCIIdentifyInfo().getSize(); j++) {
						if("1".equals(iEndorse.getBlCIIdentifyInfo().getArr(j).getSerialNo())){
							strCNation1 = iEndorse.getBlCIIdentifyInfo().getArr(j).getNation();
							strCIssuer1 = iEndorse.getBlCIIdentifyInfo().getArr(j).getSigner();
						}
					}
				}else if(!"01".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType())){
					strCName1 = iEndorse.getBLPrpPinsured().getArr(i).getInsuredName();
					strCAdderss1 = iEndorse.getBLPrpPinsured().getArr(i).getInsuredAddress();
					strCCertiCode1 = iEndorse.getBLPrpPinsured().getArr(i).getIdentifyNumber();
					blPrpDcustomerIdvNew1.query("customerCode='"+iEndorse.getBLPrpPinsured().getArr(i).getInsuredCode()+"'");
					if(strCCertiCode1 == null || "".equals(strCCertiCode1)){
						if(blPrpDcustomerIdvNew1.getSize()>0){
							strCCertiCode1=blPrpDcustomerIdvNew1.getArr(0).getIdentifyNumber();
						}
					}
					strCTelephone1 = iEndorse.getBLPrpPinsured().getArr(i).getMobile();
					strCCustomerType1 = "1";
				}
				
				if(UtiPower.isIdentifyCheck(iEndorse.getBLPrpPmain().getArr(0).getComCode(), iEndorse.getBLPrpPmain().getArr(0).getRiskCode())
						&& "1".equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredFlag()))
				{
					
					if("2".equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredType()))
					{
						strCCustomerType1 = "1";	
					}
					else if("1".equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredType()))
					{
						
						if("01".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType()))
						{
							strCCustomerType1 = "2";	
						}
						else if("04".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType()))
						{
							strCCustomerType1 = "3";	
						}
						else if("03".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType()))
						{
							strCCustomerType1 = "4";	
						}
					}
				}
				
				buf.append("<INSURED_DATA>");
				addNode(buf, "NAME", strCName1);
				addNode(buf, "GENDER", strCGender1);
				addNode(buf, "NATION", strCNation1);
				addNode(buf, "BIRTH_DATE", checkStringDate(strCBirthDatr1));
				addNode(buf, "ADDRESS", strCAdderss1);
				addNode(buf, "CERTI_CODE", strCCertiCode1);
				addNode(buf, "ISSUER", strCIssuer1);
				addNode(buf, "CERTI_START_DATE", checkStringDate(strCCertiStartDate1));
				addNode(buf, "CERTI_END_DATE", checkStringDate(strCCertiEndDate1));
				addNode(buf, "TELEPHONE", strCTelephone1);
				addNode(buf, "CUSTOMER_TYPE", strCCustomerType1);
				buf.append("</INSURED_DATA>");
			}
		}
			buf.append("<INSURED_DATA>");
			addNode(buf, "NAME", strCName);
			addNode(buf, "GENDER", strCGender);
			addNode(buf, "NATION", strCNation);
			addNode(buf, "BIRTH_DATE", checkStringDate(strCBirthDatr));
			addNode(buf, "ADDRESS", strCAdderss);
			addNode(buf, "CERTI_CODE", strCCertiCode);
			addNode(buf, "ISSUER", strCIssuer);
			addNode(buf, "CERTI_START_DATE", checkStringDate(strCCertiStartDate));
			addNode(buf, "CERTI_END_DATE", checkStringDate(strCCertiEndDate));
			addNode(buf, "TELEPHONE", strCTelephone);
			addNode(buf, "CUSTOMER_TYPE", strCCustomerType);
			buf.append("</INSURED_DATA>");
		buf.append("</INSURED_LIST>");
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
    
    public static String checkStringDate(String str){
    	str = StringUtils.replace(str,"-","");
    	return str;
    }
}
