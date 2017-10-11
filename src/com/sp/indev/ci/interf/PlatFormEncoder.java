package com.sp.indiv.ci.interf;

import org.w3c.dom.Node;

import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;

public class PlatFormEncoder {
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
	 * 添加HEAD节
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addHead(StringBuffer buf, String requestType, String strComCode) throws Exception {		
		buf.append("<HEAD>");
		addNode(buf, "REQUEST_TYPE", requestType);
        addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_USER"));
        addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_PASSWORD"));
        buf.append("</HEAD>");
	}
	
	/**
	 * 处理HEAD节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected int processHead(Node node) throws Exception {
		
		int type = 1;
		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
		String reponseMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
		if (!responseCode.equals("1")) 
		{
			type = 0;
			throw new Exception("平台交互失败，返回信息为:\n"+"错误代码为: "+responseCode
					            +"\n"+"错误原因:"+reponseMessage);
		}
		return type;
	}
	/**
	 * 添加OWNER_LIST节
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addOwnerList(StringBuffer buf, BLPolicy blPolicy)
			throws Exception {
		buf.append("<OWNER_LIST>");
		addOwnerData(buf, blPolicy);
		buf.append("</OWNER_LIST>");
	}

	/**
	 * 添加Owner_Data节
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addOwnerData(StringBuffer buf, BLPolicy blPolicy)
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
		
		ownerName=blPolicy.getBLPrpCitemCar().getArr(0).getCarOwner();
		for(int a =0;a<blPolicy.getBLPrpCinsured().getSize();a++){			
			if(blPolicy.getBLPrpCinsured().getArr(a).getInsuredFlag().equals("1")){
				ownerCertiCode=blPolicy.getBLPrpCinsured().getArr(a).getIdentifyNumber();
				ownerAddress=blPolicy.getBLPrpCinsured().getArr(a).getPostAddress();
				ownerMailAddress=blPolicy.getBLPrpCinsured().getArr(a).getPostAddress();
				ownerPostCode=blPolicy.getBLPrpCinsured().getArr(a).getPostCode();
				ownerTelephone=blPolicy.getBLPrpCinsured().getArr(a).getPhoneNumber();
				ownerCompanyType="1"; 
			}
			
		}		
		buf.append("<OWNER_DATA>");
		addNode(buf, "OWNER", ownerName);
		addNode(buf, "OWNER_TYPE", "1");  
		addNode(buf, "CERTI_TYPE", "01");   
		addNode(buf, "CERTI_CODE", "null");   
		addNode(buf, "ADDRESS", "null");      
		addNode(buf, "MAIL_ADDRESS", "null"); 
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
	protected void addPhList(StringBuffer buf, BLPolicy blPolicy)
			throws Exception {
		buf.append("<PH_LIST>");
		
		addPhData(buf, blPolicy);
		buf.append("</PH_LIST>");
	}

	/**
	 * 添加PH_DATA节
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addPhData(StringBuffer buf, BLPolicy blPolicy)
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
		
		for(int a =0;a<blPolicy.getBLPrpCinsured().getSize();a++){			
			if(blPolicy.getBLPrpCinsured().getArr(a).getInsuredFlag().equals("2")){
				appName=blPolicy.getBLPrpCinsured().getArr(a).getInsuredName();
				appCertiCode=blPolicy.getBLPrpCinsured().getArr(a).getIdentifyNumber();
				appAddress=blPolicy.getBLPrpCinsured().getArr(a).getPostAddress();
				appMailAddress=blPolicy.getBLPrpCinsured().getArr(a).getPostAddress();
				appPostCode=blPolicy.getBLPrpCinsured().getArr(a).getPostCode();
				appTelephone=blPolicy.getBLPrpCinsured().getArr(a).getPhoneNumber();
				appCompanyType="1"; 
			}			
		}
		buf.append("<PH_DATA>");
		addNode(buf, "POLICY_HOLDER", appName);
		addNode(buf, "PH_TYPE", "1");
		addNode(buf, "CERTI_TYPE", "01");
		addNode(buf, "CERTI_CODE", "null");
		addNode(buf, "ADDRESS", "null");
		addNode(buf, "MAIL_ADDRESS", "null");
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
	protected void addInsuredList(StringBuffer buf, BLPolicy blPolicy)
			throws Exception {
		buf.append("<INSURED_LIST>");
		
		addInsuredDate(buf, blPolicy);
		buf.append("</INSURED_LIST>");
	}

	/**
	 * 添加INSURED_DATA节
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addInsuredDate(StringBuffer buf, BLPolicy blPolicy)
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
		
		for(int a =0;a<blPolicy.getBLPrpCinsured().getSize();a++){
			if(blPolicy.getBLPrpCinsured().getArr(a).getInsuredFlag().equals("1")){
				insuredName=blPolicy.getBLPrpCinsured().getArr(a).getInsuredName();
				insuredCertiCode=blPolicy.getBLPrpCinsured().getArr(a).getIdentifyNumber();
				insuredAddress=blPolicy.getBLPrpCinsured().getArr(a).getPostAddress();
				insuredMailAddress=blPolicy.getBLPrpCinsured().getArr(a).getPostAddress();
				insuredPostCode=blPolicy.getBLPrpCinsured().getArr(a).getPostCode();
				insuredTelephone=blPolicy.getBLPrpCinsured().getArr(a).getPhoneNumber();
				insuredCompanyType="1"; 
			}
			
		}
		buf.append("<INSURED_DATA>");
		addNode(buf, "INSURED_NAME", insuredName);
		addNode(buf, "INSURED_TYPE", "1");
		addNode(buf, "CERTI_TYPE", "01");
		addNode(buf, "CERTI_CODE", "null");
		addNode(buf, "ADDRESS",  "null");
		addNode(buf, "MAIL_ADDRESS",  "null");
		addNode(buf, "ZIP", insuredPostCode);
		addNode(buf, "TELEPHONE", insuredTelephone);
		addNode(buf, "COMPANY_TYPE", insuredCompanyType);
		buf.append("</INSURED_DATA>");
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
