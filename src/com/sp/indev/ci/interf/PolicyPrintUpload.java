package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCIInsureDemand;
import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.platform.bl.facade.BLPrpDagentICCardFacade;
import com.sp.platform.bl.facade.BLPrpDuserFacade;
import com.sp.platform.dto.domain.PrpDagentICCardDto;
import com.sp.platform.dto.domain.PrpDuserDto;
import com.sp.prpall.blsvr.cb.BLPrpCmain;
import com.sp.prpall.blsvr.cb.BLPrpCitemCar;
import com.sp.prpall.pubfun.PubTools;
import com.sp.utility.SysConfig;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;


public class PolicyPrintUpload {

	private static Logger logger = Logger.getLogger(PolicyPrintUpload.class);

	public void upload(String policyNo, String comLevel, String IP, String UsbKey, String UserCode) throws Exception {
		BLPrpCmain blPrpCmain = new BLPrpCmain();
		blPrpCmain.getData(policyNo);
		String strComCode = blPrpCmain.getArr(0).getComCode();

		BLCIInsureDemand blCIInsureDemand = new BLCIInsureDemand();
		BLCIInsureValid blCIInsureValid = new BLCIInsureValid();
		
		String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
		String iWherePart = "";
		
		String iWherePart1 = "";
		
		
		ArrayList iWhereValue=new ArrayList();
		if(!"1".equals(strSwitch)){
		    iWherePart = "policyNo = ?";
		    iWhereValue.add(blPrpCmain.getArr(0).getPolicyNo());
		  
		    iWherePart1 = "policyNo = '"+blPrpCmain.getArr(0).getPolicyNo()+"'";
		  
		}else{
		    BLPrpCitemCar blPrpCitemCar = new BLPrpCitemCar();
		    blPrpCitemCar.getData(blPrpCmain.getArr(0).getPolicyNo());
		    iWherePart = " DemandNo = ?";
		    iWhereValue.add(blPrpCitemCar.getArr(0).getDemandNo());
		  
		    iWherePart1 = "DemandNo = '"+blPrpCitemCar.getArr(0).getDemandNo()+"'";
		  
		}
		
		blCIInsureDemand.query(iWherePart,iWhereValue);
		
		
		blCIInsureValid.query(iWherePart1);
		
		if ("8".equals(comLevel)) {
			BLPrpDagentICCardFacade blPrpDagentICCardFacade = new BLPrpDagentICCardFacade();
			PrpDagentICCardDto prpDagentICCardDto = blPrpDagentICCardFacade.getICInfoByAgentCode(blPrpCmain.getArr(0).getAgentCode());
			if (UsbKey.equals(blCIInsureDemand.getArr(0).getUsbKey())) {
				blCIInsureValid.getArr(0).setUsbKey(UsbKey);
			} else {
				throw new Exception("校验USBKEY失败,插入UsbKey："+UsbKey+" 与XX查询上传UsbKey："+blCIInsureDemand.getArr(0).getUsbKey()+" 不一致!");
			}
			
			if (prpDagentICCardDto!=null&&UsbKey.equals(prpDagentICCardDto.getCardNo())){
				blCIInsureValid.getArr(0).setUsbKey(UsbKey);
			} else {
				throw new Exception("校验USBKEY失败,插入UsbKey与平台配置UsbKey不一致或未维护!");
			}

		} else {
			PrpDuserDto prpDuserDto = new BLPrpDuserFacade().findByPrimaryKey(UserCode);
			String ipScope = prpDuserDto.getIpScope();
			if (ipScope == null || ipScope.length() == 0) {
				throw new Exception("登陆用户的网段未配置,请联系管理员维护!");
			}
			if (!PubTools.checkIpScope(prpDuserDto.getIpScope(), IP)) {
				throw new Exception("计算机的IP地址不在登陆用户允许的网段范围内,请联系管理员维护!");
			}
			if (blCIInsureDemand.getArr(0).getIP().length() == 0 && blCIInsureDemand.getArr(0).getUsbKey().length() > 0) {
				throw new Exception("校验失败,代理网点出单不允许在公司营业网点打印!");
			}
			blCIInsureValid.getArr(0).setIP(IP);
		}
		String requestXML = encode(blCIInsureValid, strComCode);
		logger.info("[XX上传发送报文]:" + requestXML);
		String responseXML = EbaoProxy.getInstance().request(requestXML, strComCode);
		logger.info("[XX上传返回报文]:" + responseXML);
		decode(blCIInsureValid, responseXML);
	}

	protected void addXMLHead(StringBuffer buf) {
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
	}

	protected void addPacket(StringBuffer buf, BLCIInsureValid blCIInsureValid, String strComCode) throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
		addHead(buf, strComCode);
		addBody(buf, blCIInsureValid);
		buf.append("</PACKET>");

	}

	protected void addHead(StringBuffer buf, String strComCode) throws Exception {
		buf.append("<HEAD>");
		addNode(buf, "REQUEST_TYPE", "A4");
		addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_USER"));
		addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_PASSWORD"));
		buf.append("</HEAD>");
	}

	protected void addBody(StringBuffer buf, BLCIInsureValid blCIInsureValid) throws Exception {
		buf.append("<BODY>");
		addNode(buf, "CONFIRM_SEQUENCE_NO", blCIInsureValid.getArr(0).getValidNo());
		addNode(buf, "USBKEY", blCIInsureValid.getArr(0).getUsbKey());
		addNode(buf, "COMPUTER_IP", blCIInsureValid.getArr(0).getIP());
		buf.append("</BODY>");
	}

	public String encode(BLCIInsureValid blCIInsureValid, String strComCode) throws Exception {
		StringBuffer buf = new StringBuffer(1000);
		addXMLHead(buf);
		addPacket(buf, blCIInsureValid, strComCode);
		return buf.toString();
	}

	/**
	 * XX上传解码XML
	 * 
	 * @param blCIInsureValid
	 * @param xmlStr
	 * @throws Exception
	 */
	public void decode(BLCIInsureValid blCIInsureValid, String xmlStr) throws Exception {
		InputStream in = new ByteArrayInputStream(xmlStr.getBytes());
		Document document = XMLUtils.parse(in);
		String errorMessage = "";
		String responseCode = "";
		try {

			Node node = XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD");
			responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE"); 

			if (!responseCode.equals("1")) {
				errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE"); 

			}
		} catch (Exception ex1) {
			throw new Exception("解析平台返回串错误。XX号：" + blCIInsureValid.getArr(0).getPolicyNo() + "，请与管理员联系！" + ex1.getMessage());
		}

		if (errorMessage != null && errorMessage.length() > 60) {
			errorMessage = errorMessage.substring(0, 60);
		}
		blCIInsureValid.getArr(0).setRemark(errorMessage);
		blCIInsureValid.update();
		
		if (!responseCode.equals("1")) {
			throw new Exception(errorMessage);
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
}
