package com.sp.indiv.ci.interf;

import org.apache.log4j.Logger;

import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.pubfun.TransCodeCI;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utiall.blsvr.BLPrpDcustomerIdvNew;
import com.sp.utiall.schema.PrpDcustomerIdvNewSchema;
import com.sp.utility.Transfer;
import com.sp.utility.UtiPower;
import com.sp.utility.error.UserException;

public class ProposalIdentityEncoder {

	private static Logger logger = Logger.getLogger(ProposalIdentityEncoder.class);
	
	/**
	 * 
	 * @param blProposal
	 * @return 身份证采集发送XML格式串
	 * @throws Exception
	 */
	public String encode(BLProposal blProposal) throws Exception {
		StringBuffer buf = new StringBuffer(4000);
		addXMLHead(buf);
		addPacket(buf,blProposal);
		logger.info("[XX身份证采集发送报文]:"+buf.toString());
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
	
	protected void addPacket(StringBuffer buf, BLProposal blProposal)
			throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
		addHead(buf,blProposal);
		addBody(buf,blProposal);
		buf.append("</PACKET>");

	}
	
	protected void addHead(StringBuffer buf, BLProposal blProposal)
			throws Exception {
		String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
		buf.append("<HEAD>");
		addNode(buf, "REQUEST_TYPE", "B3");
		addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_USER"));
		addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_PASSWORD"));
		buf.append("</HEAD>");
	}
	
	protected void addBody(StringBuffer buf, BLProposal blProposal)
			throws Exception {
		buf.append("<BODY>");
		addBasePart(buf,blProposal);
		addHolderList(buf,blProposal);
		addInsuredList(buf,blProposal);
		buf.append("</BODY>");
	}
	
	/**
	 * 基础信息
	 * @param buf
	 * @param blProposal
	 * @throws Exception
	 */
	protected void addBasePart(StringBuffer buf, BLProposal blProposal)
			throws Exception {	
		String strType = "";
		String strAmendQueryNo = "";
		String strCancelQueryNo = "";
		String strClaimCode = "";
		String strMachineCode = "";
		String strSalesChannel = "";
		
		strType = "1";
		if(blProposal.getBlCIIdentifyInfo().getSize()>0){
			if(blProposal.getBlCIIdentifyInfo().getArr(0)!=null&&blProposal.getBlCIIdentifyInfo().getArr(0).getCollectorCode()!=null&&!"".equals(blProposal.getBlCIIdentifyInfo().getArr(0).getCollectorCode())){
				strMachineCode = blProposal.getBlCIIdentifyInfo().getArr(0).getCollectorCode();
			}else if(blProposal.getBlCIIdentifyInfo().getArr(1)!=null&&blProposal.getBlCIIdentifyInfo().getArr(1).getCollectorCode()!=null&&!"".equals(blProposal.getBlCIIdentifyInfo().getArr(1).getCollectorCode())){
				strMachineCode = blProposal.getBlCIIdentifyInfo().getArr(1).getCollectorCode();
			}
		}
		if (new UtiPower().checkSinoCommission(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())) {
			strSalesChannel = Transfer.getTransfer("SinoCommission", "BusinessNature", blProposal.getBLPrpTmain().getArr(0).getBusinessNature());
		}else if(new UtiPower().checkSinoCommissionHis(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
			strSalesChannel = Transfer.getTransfer("SinoCommission", "BusinessNatureCIHis", blProposal.getBLPrpTmain().getArr(0).getBusinessNature());
		}else{
			if (new UtiPower().checkSalesChannelCISwitch(blProposal.getBLPrpTmain().getArr(0).getComCode())) {
				strSalesChannel = Transfer.getTransfer("SinoCommission", "BusinessNature", blProposal.getBLPrpTmain().getArr(0).getBusinessNature());
			}else{
				strSalesChannel = TransCodeCI.getTransferCI(blProposal.getBLPrpTmain().getArr(0).getComCode(), "BusinessNature", blProposal.getBLPrpTmain().getArr(0).getBusinessNature());
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
	protected void addHolderList(StringBuffer buf, BLProposal blProposal)
			throws Exception {
		String strName = "";
		String strGender = "";
		String strNation = "";
		String strBirthDatr = "";
		String strAdderss = "";
		String strCertiCode = "";
		String strIssuer = "";
		String strCertiStartDate = "";
		String strCertiEndDate = "";
		String strTelephone = "";
		String strCustomerType = "";
		BLPrpDcustomerIdvNew blPrpDcustomerIdvNew = new BLPrpDcustomerIdvNew();
		PrpDcustomerIdvNewSchema prpDcustomerIdvNewSchema = new PrpDcustomerIdvNewSchema();
		for (int i = 0; i < blProposal.getBLPrpTinsured().getSize(); i++) {
			
			if("2".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredFlag())
					&& "01".equals(blProposal.getBLPrpTinsured().getArr(i).getIdentifyType())){
				strName = blProposal.getBLPrpTinsured().getArr(i).getInsuredName();
				strAdderss = blProposal.getBLPrpTinsured().getArr(i).getInsuredAddress();
				strCertiCode = blProposal.getBLPrpTinsured().getArr(i).getIdentifyNumber();
				strTelephone = blProposal.getBLPrpTinsured().getArr(i).getMobile();
				blPrpDcustomerIdvNew.query("customerCode='"+blProposal.getBLPrpTinsured().getArr(i).getInsuredCode()+"'");
				if(blPrpDcustomerIdvNew.getSize()>0){
					if(strTelephone==null||"".equals(strTelephone)){
						strTelephone=blPrpDcustomerIdvNew.getArr(0).getMobile();
					}
				}
				strCustomerType = "2";
				
				if(strCertiCode == null || "".equals(strCertiCode)){
					throw new Exception("身份证号码为空!");
				}else{
					blPrpDcustomerIdvNew.query("identifytype='01' and identifynumber = '" + strCertiCode + "'");
					if(blPrpDcustomerIdvNew.getSize() <= 0){
						throw new Exception("XXXXX信息为空!");
					}
				}
				prpDcustomerIdvNewSchema = blPrpDcustomerIdvNew.getArr(0);
				strCertiStartDate = prpDcustomerIdvNewSchema.getIdentifyUsefullifeStart();
				if(strCertiStartDate==null||"".equals(strCertiStartDate)){
					strCertiStartDate="2010-04-30";
				}
				strCertiEndDate = prpDcustomerIdvNewSchema.getIdentifyUsefullife();
				if(strCertiEndDate==null||"".equals(strCertiEndDate)){
					strCertiEndDate="2099-04-30";
				}
				strGender = prpDcustomerIdvNewSchema.getSex();
				strBirthDatr = prpDcustomerIdvNewSchema.getBirthDate();
				
				for (int j = 0; j < blProposal.getBlCIIdentifyInfo().getSize(); j++) {
					if("2".equals(blProposal.getBlCIIdentifyInfo().getArr(j).getSerialNo())){
						strNation = blProposal.getBlCIIdentifyInfo().getArr(j).getNation();
						strIssuer = blProposal.getBlCIIdentifyInfo().getArr(j).getSigner();
					}
				}
			}else if("2".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredFlag())
					&& !"01".equals(blProposal.getBLPrpTinsured().getArr(i).getIdentifyType())){
				strName = blProposal.getBLPrpTinsured().getArr(i).getInsuredName();
				strAdderss = blProposal.getBLPrpTinsured().getArr(i).getInsuredAddress();
				strCertiCode = blProposal.getBLPrpTinsured().getArr(i).getIdentifyNumber();
				strTelephone = blProposal.getBLPrpTinsured().getArr(i).getMobile();
				strCustomerType = "1";
			}
			
			if(UtiPower.isIdentifyCheck(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getRiskCode())
					&& "2".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredFlag()))
			{
				
				if("2".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredType()))
				{
					strCustomerType = "1";	
				}
				else if("1".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredType()))
				{
					
					if("01".equals(blProposal.getBLPrpTinsured().getArr(i).getIdentifyType()))
					{
						strCustomerType = "2";	
					}
					else if("04".equals(blProposal.getBLPrpTinsured().getArr(i).getIdentifyType()))
					{
						strCustomerType = "3";	
					}
					else if("03".equals(blProposal.getBLPrpTinsured().getArr(i).getIdentifyType()))
					{
						strCustomerType = "4";	
					}
				}
			}
			
		}
		
		buf.append("<HOLDER_LIST>");
		buf.append("<HOLDER_DATA>");
		addNode(buf, "NAME", strName);
		addNode(buf, "GENDER", strGender);
		addNode(buf, "NATION", strNation);
		addNode(buf, "BIRTH_DATE", checkStringDate(strBirthDatr));
		addNode(buf, "ADDRESS", strAdderss);
		addNode(buf, "CERTI_CODE", strCertiCode);
		addNode(buf, "ISSUER", strIssuer);
		addNode(buf, "CERTI_START_DATE", checkStringDate(strCertiStartDate));
		addNode(buf, "CERTI_END_DATE", checkStringDate(strCertiEndDate));
		addNode(buf, "TELEPHONE", strTelephone);
		addNode(buf, "CUSTOMER_TYPE", strCustomerType);
		buf.append("</HOLDER_DATA>");
		buf.append("</HOLDER_LIST>");
	}
	
	/**
	 * 被XX人信息
	 * @param buf
	 * @param blProposal
	 * @throws Exception
	 */
	protected void addInsuredList(StringBuffer buf, BLProposal blProposal)
			throws Exception {
		String strName = "";
		String strGender = "";
		String strNation = "";
		String strBirthDatr = "";
		String strAdderss = "";
		String strCertiCode = "";
		String strIssuer = "";
		String strCertiStartDate = "";
		String strCertiEndDate = "";
		String strTelephone = "";
		String strCustomerType = "";
		BLPrpDcustomerIdvNew blPrpDcustomerIdvNew = new BLPrpDcustomerIdvNew();
		PrpDcustomerIdvNewSchema prpDcustomerIdvNewSchema = new PrpDcustomerIdvNewSchema();
		for (int i = 0; i < blProposal.getBLPrpTinsured().getSize(); i++) {
			
			if("1".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredFlag())
					&& "01".equals(blProposal.getBLPrpTinsured().getArr(i).getIdentifyType())){
				strName = blProposal.getBLPrpTinsured().getArr(i).getInsuredName();
				strAdderss = blProposal.getBLPrpTinsured().getArr(i).getInsuredAddress();
				strCertiCode = blProposal.getBLPrpTinsured().getArr(i).getIdentifyNumber();
				strTelephone = blProposal.getBLPrpTinsured().getArr(i).getMobile();
				blPrpDcustomerIdvNew.query("customerCode='"+blProposal.getBLPrpTinsured().getArr(i).getInsuredCode()+"'");
				if(blPrpDcustomerIdvNew.getSize()>0){
					if(strTelephone==null||"".equals(strTelephone)){
						strTelephone=blPrpDcustomerIdvNew.getArr(0).getMobile();
					}
				}
				strCustomerType = "2";
				
				if(strCertiCode == null || "".equals(strCertiCode)){
					throw new Exception("身份证号码为空!");
				}else{
					blPrpDcustomerIdvNew.query("identifytype='01' and identifynumber = '" + strCertiCode + "'");
					if(blPrpDcustomerIdvNew.getSize() <= 0){
						throw new Exception("XXXXX信息为空!");
					}
				}
				prpDcustomerIdvNewSchema = blPrpDcustomerIdvNew.getArr(0);
				strCertiStartDate = prpDcustomerIdvNewSchema.getIdentifyUsefullifeStart();
				if(strCertiStartDate==null||"".equals(strCertiStartDate)){
					strCertiStartDate="2010-04-30";
				}
				strCertiEndDate = prpDcustomerIdvNewSchema.getIdentifyUsefullife();
				if(strCertiEndDate==null||"".equals(strCertiEndDate)){
					strCertiEndDate="2099-04-30";
				}
				strGender = prpDcustomerIdvNewSchema.getSex();
				strBirthDatr = prpDcustomerIdvNewSchema.getBirthDate();
				
				for (int j = 0; j < blProposal.getBlCIIdentifyInfo().getSize(); j++) {
					if("1".equals(blProposal.getBlCIIdentifyInfo().getArr(j).getSerialNo())){
						strNation = blProposal.getBlCIIdentifyInfo().getArr(j).getNation();
						strIssuer = blProposal.getBlCIIdentifyInfo().getArr(j).getSigner();
					}
				}
			}else if ("1".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredFlag())
					&& !"01".equals(blProposal.getBLPrpTinsured().getArr(i).getIdentifyType())){
				strName = blProposal.getBLPrpTinsured().getArr(i).getInsuredName();
				strAdderss = blProposal.getBLPrpTinsured().getArr(i).getInsuredAddress();
				strCertiCode = blProposal.getBLPrpTinsured().getArr(i).getIdentifyNumber();
				strTelephone = blProposal.getBLPrpTinsured().getArr(i).getMobile();
				strCustomerType = "1";
			}
			
			if(UtiPower.isIdentifyCheck(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getRiskCode())
					&& "1".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredFlag()))
			{
				
				if("2".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredType()))
				{
					strCustomerType = "1";	
				}
				else if("1".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredType()))
				{
					
					if("01".equals(blProposal.getBLPrpTinsured().getArr(i).getIdentifyType()))
					{
						strCustomerType = "2";	
					}
					else if("04".equals(blProposal.getBLPrpTinsured().getArr(i).getIdentifyType()))
					{
						strCustomerType = "3";	
					}
					else if("03".equals(blProposal.getBLPrpTinsured().getArr(i).getIdentifyType()))
					{
						strCustomerType = "4";	
					}
				}
			}
			
		}
		
		buf.append("<INSURED_LIST>");
		buf.append("<INSURED_DATA>");
		addNode(buf, "NAME", strName);
		addNode(buf, "GENDER", strGender);
		addNode(buf, "NATION", strNation);
		addNode(buf, "BIRTH_DATE", checkStringDate(strBirthDatr));
		addNode(buf, "ADDRESS", strAdderss);
		addNode(buf, "CERTI_CODE", strCertiCode);
		addNode(buf, "ISSUER", strIssuer);
		addNode(buf, "CERTI_START_DATE", checkStringDate(strCertiStartDate));
		addNode(buf, "CERTI_END_DATE", checkStringDate(strCertiEndDate));
		addNode(buf, "TELEPHONE", strTelephone);
		addNode(buf, "CUSTOMER_TYPE", strCustomerType);
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
