package com.sp.indiv.ci.interf;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.dbsvr.DBCIInsureValid;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
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

public class CancelIdentityEncoder {

	private static Logger logger = Logger.getLogger(CancelIdentityEncoder.class);
	
	/**
	 * 
	 * @param blProposal
	 * @return 身份证采集发送XML格式串
	 * @throws Exception
	 */
	public String encode(BLPolicy iPolicy, BLEndorse iEndorse) throws Exception {
		StringBuffer buf = new StringBuffer(4000);
		getCIInsureValid(iEndorse);
		addXMLHead(buf);
		addPacket(buf,iPolicy,iEndorse);
		logger.info("[退XXXXX身份证采集发送报文]:"+buf.toString());
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
		String strComCode = iEndorse.getBLPrpPmain().getArr(0).getComCode();
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
		
		strType = "4";
		strCancelQueryNo = iPolicy.getBLCIEndorValid().getArr(0).getDemandNo();
		if(iEndorse.getBlCIIdentifyInfo().getSize()>0){
			if(iEndorse.getBlCIIdentifyInfo().getArr(0)!=null&&iEndorse.getBlCIIdentifyInfo().getArr(0).getCollectorCode()!=null&&!"".equals(iEndorse.getBlCIIdentifyInfo().getArr(0).getCollectorCode())){
				strMachineCode = iEndorse.getBlCIIdentifyInfo().getArr(0).getCollectorCode();
			}else if(iEndorse.getBlCIIdentifyInfo().getArr(1)!=null&&iEndorse.getBlCIIdentifyInfo().getArr(1).getCollectorCode()!=null&&!"".equals(iEndorse.getBlCIIdentifyInfo().getArr(1).getCollectorCode())){
				strMachineCode = iEndorse.getBlCIIdentifyInfo().getArr(1).getCollectorCode();
			}
		}
		if (new UtiPower().checkSinoCommission(iEndorse.getBLPrpPmain().getArr(0).getComCode(),iEndorse.getBLPrpPmain().getArr(0).getOperateDate())) {
			strSalesChannel = Transfer.getTransfer("SinoCommission", "BusinessNature", iEndorse.getBLPrpPmain().getArr(0).getBusinessNature());
		}else if(new UtiPower().checkSinoCommissionHis(iEndorse.getBLPrpPmain().getArr(0).getComCode(),iEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
			strSalesChannel = Transfer.getTransfer("SinoCommission", "BusinessNatureCIHis", iEndorse.getBLPrpPmain().getArr(0).getBusinessNature());
		}else{
			if (new UtiPower().checkSalesChannelCISwitch(iEndorse.getBLPrpPmain().getArr(0).getComCode())) {
				strSalesChannel = Transfer.getTransfer("SinoCommission", "BusinessNature", iEndorse.getBLPrpPmain().getArr(0).getBusinessNature());
			}else{
				strSalesChannel = TransCodeCI.getTransferCI(iEndorse.getBLPrpPmain().getArr(0).getComCode(), "BusinessNature", iEndorse.getBLPrpPmain().getArr(0).getBusinessNature());
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
		for (int i = 0; i < iEndorse.getBLPrpPinsured().getSize(); i++) {
			
			if("2".equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredFlag())
					&& "01".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType())){
				strName = iEndorse.getBLPrpPinsured().getArr(i).getInsuredName();
				strAdderss = iEndorse.getBLPrpPinsured().getArr(i).getInsuredAddress();
				strCertiCode = iEndorse.getBLPrpPinsured().getArr(i).getIdentifyNumber();
				strTelephone = iEndorse.getBLPrpPinsured().getArr(i).getMobile();
				if(strTelephone==null||"".equals(strTelephone)){
					strTelephone="11111111111";
				}
				strCustomerType = "2";
				blPrpDcustomerIdvNew.query("customerCode='"+iEndorse.getBLPrpPinsured().getArr(i).getInsuredCode()+"'");
				if(strCertiCode == null || "".equals(strCertiCode)){
					if(blPrpDcustomerIdvNew.getSize()>0){
						strCertiCode=blPrpDcustomerIdvNew.getArr(0).getIdentifyNumber();
					}
				}
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
				
				for (int j = 0; j < iEndorse.getBlCIIdentifyInfo().getSize(); j++) {
					if("2".equals(iEndorse.getBlCIIdentifyInfo().getArr(j).getSerialNo())){
						strNation = iEndorse.getBlCIIdentifyInfo().getArr(j).getNation();
						strIssuer = iEndorse.getBlCIIdentifyInfo().getArr(j).getSigner();
					}
				}
			}else if("2".equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredFlag())
					&& !"01".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType())){
				strName = iEndorse.getBLPrpPinsured().getArr(i).getInsuredName();
				strAdderss = iEndorse.getBLPrpPinsured().getArr(i).getInsuredAddress();
				strCertiCode = iEndorse.getBLPrpPinsured().getArr(i).getIdentifyNumber();
				strTelephone = iEndorse.getBLPrpPinsured().getArr(i).getMobile();
				strCustomerType = "1";
			}
			
			if(UtiPower.isIdentifyCheck(iEndorse.getBLPrpPmain().getArr(0).getComCode(), iEndorse.getBLPrpPmain().getArr(0).getRiskCode())
					&& "2".equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredFlag()))
			{
				
				if("2".equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredType()))
				{
					strCustomerType = "1";	
				}
				else if("1".equals(iEndorse.getBLPrpPinsured().getArr(i).getInsuredType()))
				{
					
					if("01".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType()))
					{
						strCustomerType = "2";	
					}
					else if("04".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType()))
					{
						strCustomerType = "3";	
					}
					else if("03".equals(iEndorse.getBLPrpPinsured().getArr(i).getIdentifyType()))
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
    
	/**
	 * 获得退XXXXX确认主信息
	 *
	 * @param BLEndorse
	 * @throws Exception
	 */
	private void getCIInsureValid(BLEndorse blEndorse) throws Exception {
		try {
			String sqlWhere = " Select * from CIInsureValid WHERE PolicyNo = '"
							  + blEndorse.getBLPrpPhead().getArr(0).getPolicyNo() + "'";
			DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
			Vector vector = dbCIInsureValid.findByConditions(sqlWhere);
			CIInsureValidSchema cIInsureValidSchema = (CIInsureValidSchema) vector .get(0);
			BLCIInsureValid blCIInsureValid = new BLCIInsureValid();
			blCIInsureValid.setArr(cIInsureValidSchema);
			blEndorse.setBLCIInsureValid(blCIInsureValid);
		}
		catch (Exception ex) 
		{
			throw ex;
		}
	}
}
