package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import com.sp.indiv.ci.schema.*;
import com.sp.indiv.ci.blsvr.*;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgDate;

public class CIInsureCompareDecoder 
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
	public int decode(DbPool dbPool, BLPolicy blPolicy, String content, int iMaxSerialNo, String iComCode)
			throws Exception 
	{
		
		InputStream in 		= new ByteArrayInputStream(content.getBytes());
		Document document 	= XMLUtils.parse(in);

		
		int type = 2;
		
		if(content != null && !content.equals("")) 
		{
			type = processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"), blPolicy);
		}
		else
		{
			throw new Exception("===============��ǿXXXXXƽ̨-���ݱȶԽӿ�-û�н��ܵ���������");
		}
		
		if(blPolicy == null 	|| 
		  (type != 0 && type != 1))
		{
			throw new Exception("===============��ǿXXXXXƽ̨-���ݱȶԽӿ�-��CIInsureCompare���д����ʧ�ܣ�(Policy������߷���Type����)");
		}
		
		
		if(type == 0)
		{
			processBody(XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"), blPolicy);
		}

		if(dbPool != null) 
		{
			
			updateCIInsureCompare(dbPool, blPolicy, iComCode);
		}
		else 
		{
			throw new Exception("===============��ǿXXXXXƽ̨-���ݱȶԽӿ�-����Դ����ʧ�ܣ�");
		}
		return type;
	}

	/**
	 * ����HEAD��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected int processHead(Node node, BLPolicy blPolicy) 
		throws Exception 
	{
		BLCIInsureCompare bLCIInsureCompare = null;
		CIInsureCompareSchema cIInsureCompareSchema = null;
		bLCIInsureCompare = blPolicy.getBLCIInsureCompare();
		cIInsureCompareSchema = bLCIInsureCompare.getArr(0);
		String errorMessage = "";
		int type = 2; 
		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE"); 
		cIInsureCompareSchema.setResponseCode(responseCode);
		if(responseCode.trim().equals("1"))
		{
			type = 0;
			cIInsureCompareSchema.setResponseMessage("�ɹ�");
		}
		else if(responseCode.trim().equals("0"))
		{
			type = 1;
			errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
        	cIInsureCompareSchema.setResponseMessage(errorMessage);
		}
		return type;
	}
	
	/**
     * ����BODY��
     * @param node Node
     * @throws Exception
     */
    protected void processBody(Node node, BLPolicy blPolicy) 
    	throws Exception 
    {
        processBasePart(blPolicy, XMLUtils.getChildNodeByTagName(node, "BASE_PART"));
    }
    
    /**
     * ����BASE_PART��
     * @param node Node
     * @throws Exception
     */
    protected void processBasePart(BLPolicy blPolicy, Node node)
    	throws Exception 
    {
        CIInsureCompareSchema cIInsureCompareSchema = null;
        cIInsureCompareSchema = blPolicy.getBLCIInsureCompare().getArr(0);
        
        String compareResult 		= XMLUtils.getChildNodeValue(node, "COMPARE_RESULT");	
        String getConfirmTotal		= XMLUtils.getChildNodeValue(node, "PT_CONFIRM");		
        String getCancleTotal 		= XMLUtils.getChildNodeValue(node, "PT_CANCEL");		
        String getWithDrawTotal		= XMLUtils.getChildNodeValue(node, "PT_SURRENDER");		
        String getReportTotal		= XMLUtils.getChildNodeValue(node, "PT_REPORT");		
        String getRegistration		= XMLUtils.getChildNodeValue(node, "PT_REGISTRATION");	
        String getEndClaim			= XMLUtils.getChildNodeValue(node, "PT_ENDCASE");		
        String getCancleClaim		= XMLUtils.getChildNodeValue(node, "PT_CANCELCLAIM");	
        
        
        cIInsureCompareSchema.setGetConfirm(getConfirmTotal);
        cIInsureCompareSchema.setGetCancel(getCancleTotal);
        cIInsureCompareSchema.setGetWithDraw(getWithDrawTotal);
        cIInsureCompareSchema.setGetReport(getReportTotal);
        cIInsureCompareSchema.setGetRegistration(getRegistration);
        cIInsureCompareSchema.setGetEndClaim(getEndClaim);
        cIInsureCompareSchema.setGetCancelClaim(getCancleClaim);
        cIInsureCompareSchema.setConfirmResult(compareResult);
    }

	/*
	 * ��CIInsureCompare���������
	 */
	private void updateCIInsureCompare(DbPool dbPool, BLPolicy blPolicy, String iComCode)
		throws Exception 
	{
		BLCIInsureCompare bLCIInsureCompare 		= null;
		CIInsureCompareSchema cIInsureCompareSchema = null;
		bLCIInsureCompare = blPolicy.getBLCIInsureCompare();
		cIInsureCompareSchema = blPolicy.getBLCIInsureCompare().getArr(0);

		if(cIInsureCompareSchema != null)
		{
			bLCIInsureCompare.save(dbPool);
		}
	}
}
