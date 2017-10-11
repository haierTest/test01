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
	 * 解码
	 * 
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, 
					   BLProposal BLProposal, 
					   String content)
		throws SQLException, UserException, Exception 
	{
		
		logger.info("[XX确认返回报文]:"+content);
		
		
        
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
			throw new Exception("解析平台返回串错误。已核XX单号：" + BLProposal.getBLPrpTmain().getArr(0).getProposalNo() 
		             + "，请与管理员联系！" + ex1.getMessage());
		}
		
		if(type != 1)
		{
			throw new Exception(BLProposal.getBLCIInsureValid().getArr(0).getErrorMessage());
		}
		
	 }
	}
	/**
	 * 处理HEAD节
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
	 * 纠正日期格式
	 * 
	 * @param dateString
	 *            8个字符的日期
	 * @return YYYY-MM-DD形式的日期
	 */
	private String correctDate(String dateString) {
		if (dateString.length() < 8) {
			throw new IllegalArgumentException(dateString
					+ "的日期格式不对，必须为大于8位的纯数字的字符串");
		}
		String result = dateString.substring(0, 4) + "-"
				+ dateString.substring(4, 6) + "-" + dateString.substring(6, 8);
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
