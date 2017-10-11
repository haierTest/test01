package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.w3c.dom.*;
import org.w3c.dom.*;
import com.sp.indiv.ci.blsvr.BLCIInsureCompareDetail;
import com.sp.indiv.ci.schema.CIInsureCompareDetailSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.database.DbPool;

public class CIInsureCompareDetailDecoder 
{
	public static void main(String[] args) 
		throws Exception 
	{
	}
	
	/**
	 * ����
	 * @return XX��ѯ����XML��ʽ��
	 * @throws Exception
	 */
	public int decode(DbPool dbPool, 
					  BLPolicy blPolicy, 
					  String content, 
					  String iRequestType, 
					  int iMaxSerialNo, 
					  String iComCode,
					  String iCurrentDate, 
					  String iCompareDate)
		throws Exception 
	{
		InputStream in 		= new ByteArrayInputStream(content.getBytes());
		Document document 	= XMLUtils.parse(in);
		
		
		int iResponseCode = 2;
		String strResponseType = "";
		String strResponseErrorMessage = "";
		
		if(content != null && !content.equals("")) 
		{
			strResponseType = processHeadTypeType(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"), blPolicy);
			iResponseCode= processHeadTypeCode(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"), blPolicy);
			strResponseErrorMessage = processHeadErrorMessage(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"), blPolicy);
		}
		else
		{
			throw new Exception("===============��ǿXXXXXƽ̨-���ݱȶԽӿ�-û�н��ܵ���������");
		}
		
		if(blPolicy == null 	|| 
		  (iResponseCode != 0 && iResponseCode != 1))
		{
			throw new Exception("===============��ǿXXXXXƽ̨-���ݱȶԽӿ�-��CIInsureCompare���д����ʧ�ܣ�(Policy������߷���Type����)");
		}
		
		
		if(iResponseCode == 0)
		{
			processBody(XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"), blPolicy, 
						iComCode, iRequestType, iMaxSerialNo, 
						iCurrentDate, iCompareDate, 
						strResponseType, iResponseCode, strResponseErrorMessage);
		}
		return iResponseCode;
	}
	
	/**
	 * ����HEAD�ڵķ�������
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected String processHeadTypeType(Node node, BLPolicy blPolicy) 
		throws Exception 
	{

		String errorMessage = "";
		String responseType = XMLUtils.getChildNodeValue(node, "REQUEST_TYPE"); 

		return responseType;
	}
	
	/**
	 * ����HEAD�ڵķ�������
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected int processHeadTypeCode(Node node, BLPolicy blPolicy) 
		throws Exception 
	{

		String errorMessage = "";
		int type = 2; 
		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE"); 

		if(responseCode.trim().equals("1"))
		{
			type = 0;
			errorMessage = "�ɹ�";
		}
		else if(responseCode.trim().equals("0"))
		{
			type = 1;
			errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
		}
		return type;
	}
	
	/**
	 * ����HEAD�ڵķ��ش�����Ϣ
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected String processHeadErrorMessage(Node node, BLPolicy blPolicy) 
		throws Exception 
	{

		String errorMessage = "";
		int type 			= 2; 
		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE"); 

		if(responseCode.trim().equals("1"))
		{
			type = 0;
			errorMessage = "�ɹ�";
		}
		else if(responseCode.trim().equals("0"))
		{
			type = 1;
			errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
		}
		return errorMessage;
	}
	
	/**
	 * ����BODY��
	 * @param node Node
	 * @throws Exception
	 */
	protected void processBody(Node node, BLPolicy blPolicy, String iComCode, String iRequestType, 
							   int iMaxSerialNo, String iCurrentDate, String iCompareDate, 
							   String strResponseType, int iResponseCode,  
							   String strResponseErrorMessage) 
		throws Exception 
	{
	    processBasePart(blPolicy, XMLUtils.getChildNodeByTagName(node, "BASE_PART"), 
	    				iComCode, iRequestType, iMaxSerialNo, iCurrentDate, iCompareDate, 
	    				strResponseType, iResponseCode, strResponseErrorMessage);
	}
	
	/**
	 * ����BASE_PART��
	 * @param node Node
	 * @throws Exception
	 */
	protected void processBasePart(BLPolicy blPolicy, Node node, String iComCode, String iRequestType, 
								   int iMaxSerialNo, String iCurrentDate,  String iCompareDate, 
								   String strResponseType, int iResponseCode,  
								   String strResponseErrorMessage)
		throws Exception 
	{
		BLCIInsureCompareDetail bLCIInsureCompareDetail = blPolicy.getBLCIInsureCompareDetail();
		String strTotalNumber = XMLUtils.getChildNodeValue(node, "TOTAL_NUMBER");
	    
		node = XMLUtils.getChildNodeByTagName(node, "QUERY_DATA_LIST");
		
		
		if(strTotalNumber.trim().equals("0"))
		{
			CIInsureCompareDetailSchema cIInsureCompareDetailSchema = new CIInsureCompareDetailSchema();
			cIInsureCompareDetailSchema.setComCode(iComCode);
			cIInsureCompareDetailSchema.setSerialNoCom(String.valueOf(iMaxSerialNo));
			cIInsureCompareDetailSchema.setOperateDate(iCurrentDate);
			cIInsureCompareDetailSchema.setCompareDate(iCompareDate);
			cIInsureCompareDetailSchema.setReturnTotalNum(strTotalNumber);
			cIInsureCompareDetailSchema.setRequestType(iRequestType);
			cIInsureCompareDetailSchema.setResponseCode(String.valueOf(iResponseCode));
			cIInsureCompareDetailSchema.setResponseMessage(strResponseErrorMessage);
			bLCIInsureCompareDetail.setArr(cIInsureCompareDetailSchema);
		}
		
		
	    if(node.hasChildNodes())
	    {
	    	NodeList children = node.getChildNodes();
	    	for(int j = 0; j < children.getLength(); j++)
	    	{
	    		CIInsureCompareDetailSchema cIInsureCompareDetailSchema = new CIInsureCompareDetailSchema();
	    		Node queryDateNode = children.item(j);
	    		String strSerialNo 		= "";
	    		String strBusinessCode 	= "";
	    		String strPolicyNo		= "";
	    		String strOperationTime = "";
	    		if(queryDateNode.toString().length() != 0)
	    		{
	    			strSerialNo 	= XMLUtils.getChildNodeValue(queryDateNode, "SERIAL_NO");
	    			strBusinessCode = XMLUtils.getChildNodeValue(queryDateNode, "BUSINESS_CODE");
	    			strPolicyNo		= XMLUtils.getChildNodeValue(queryDateNode, "POLICY_NO");
	    			strOperationTime = XMLUtils.getChildNodeValue(queryDateNode, "OPERATION_TIME");
	    			
	    			cIInsureCompareDetailSchema.setComCode(iComCode);
	    			cIInsureCompareDetailSchema.setSerialNo(strSerialNo);
	    			cIInsureCompareDetailSchema.setSerialNoCom(String.valueOf(iMaxSerialNo));
	    			cIInsureCompareDetailSchema.setBusinessNo(strBusinessCode);
	    			cIInsureCompareDetailSchema.setPolicyNo(strPolicyNo);
	    			cIInsureCompareDetailSchema.setOperateDate(iCurrentDate);
	    			cIInsureCompareDetailSchema.setCompareDate(iCompareDate);
	    			cIInsureCompareDetailSchema.setReturnTotalNum(strTotalNumber);
	    			cIInsureCompareDetailSchema.setRequestType(iRequestType);
	    			cIInsureCompareDetailSchema.setResponseCode(String.valueOf(iResponseCode));
	    			cIInsureCompareDetailSchema.setResponseMessage(strResponseErrorMessage);
	    		}
	    		bLCIInsureCompareDetail.setArr(cIInsureCompareDetailSchema);
	    	}
	    }
	}
}
