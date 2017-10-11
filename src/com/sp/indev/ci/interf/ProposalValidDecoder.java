package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

public class ProposalValidDecoder {

	
	private static Logger logger = Logger.getLogger(ProposalValidDecoder.class);
	
	/**
	 * ����
	 * 
	 * @return XX��ѯ����XML��ʽ��
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, 
					   BLProposal BLProposal, 
					   String content)
		throws SQLException, UserException, Exception 
	{
		
		logger.info("[XXȷ�Ϸ��ر���]:"+content);
		
		
        
    	String strComCode = BLProposal.getBLPrpTmain().getArr(0).getComCode();
    	String strRiskCode = BLProposal.getBLPrpTmain().getArr(0).getRiskCode();
    	if(new UtiPower().checkCIInsureSH(strRiskCode, strComCode)){
    		ProposalPreAffirmDecoder proposalPreAffirmDecoder = new ProposalPreAffirmDecoder();
		    proposalPreAffirmDecoder.decode(dbPool, BLProposal, content);	
		}else{
        
		InputStream in    = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		
		int type = -1;
		
		try
		{
			type = processHead(BLProposal, XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
		}
		catch(Exception ex1)
		{
			throw new Exception("����ƽ̨���ش������Ѻ�XX���ţ�" + BLProposal.getBLPrpTmain().getArr(0).getProposalNo() 
		             + "���������Ա��ϵ��" + ex1.getMessage());
		}
		
		if(type != 1)
		{
			throw new Exception(BLProposal.getBLCIInsureValid().getArr(0).getErrorMessage());
		}
		
	 }
	}
	/**
	 * ����HEAD��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	
	protected int processHead(BLProposal BLProposal, Node node) throws Exception 
	{
		
		int type = 1;
		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");	
		if (!responseCode.equals("1")) {
			String errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE"); 	
			CIInsureValidSchema schema = new CIInsureValidSchema();
			schema.setErrorMessage(errorMessage);
			BLProposal.getBLCIInsureValid().setArr(schema);
			type = 0;
		}
		return type;
	}
	

	/**
	 * �������ڸ�ʽ
	 * 
	 * @param dateString
	 *            8���ַ�������
	 * @return YYYY-MM-DD��ʽ������
	 */
	private String correctDate(String dateString) {
		if (dateString.length() < 8) {
			throw new IllegalArgumentException(dateString
					+ "�����ڸ�ʽ���ԣ�����Ϊ����8λ�Ĵ����ֵ��ַ���");
		}
		String result = dateString.substring(0, 4) + "-"
				+ dateString.substring(4, 6) + "-" + dateString.substring(6, 8);
		return result;
	}

	/**
	 * ��������ʱ���ʽ
	 * 
	 * @param dateString
	 *            ����ǰ������ʱ��
	 * @return �����������ʱ��
	 */
	private String correctDateTime(String dateString) {
		String result = correctDate(dateString);
		if (dateString.length() >= 10) {
			result += " " + dateString.substring(8, 10);
		}
		if (dateString.length() >= 12) {
			result += ":" + dateString.substring(10, 12);
		}
		if (dateString.length() >= 14) {
			result += ":" + dateString.substring(12, 14);
		}
		return result;
	}

}
