package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.*;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;

import com.sp.indiv.ci.blsvr.*;
import com.sp.indiv.ci.dbsvr.*;
import com.sp.indiv.ci.schema.*;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Date;
import com.sp.indiv.ci.blsvr.BLCIInsureDemand;
import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPrpCmain;
import com.sp.prpall.schema.PrpCmainSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 发送直接XX请求数据的解码器
 * 
 */
public class PolicyImmcbDecoder{

	

	/**
	 * 解码
	 * 
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, 
					   BLPolicy blPolicy, 
					   String content)
		throws Exception 
	{
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		int type = 1;
		type = processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
		if (type == 1) 
		{
			processBody(blPolicy, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
		}
		else 
		{
			throw new Exception("解析平台直接XX接口返回串错误。XX号：" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo() 
		             + "，请与管理员联系！");
		}
		
		try
		{
			saveCIInsureValid(dbPool, blPolicy);
		}
		catch(Exception ex)
		{
			throw new Exception("与平台交互成功。但" + ex.getMessage() + " XX号：" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
		}
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
		if (!responseCode.equals("1")) {
			type = 0;
		}
		return type;
	}

	/**
	 * 处理BODY节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBody(BLPolicy blPolicy, Node node) throws Exception {
		processBasePart(blPolicy, XMLUtils.getChildNodeByTagName(node,
				"BASE_PART"));
	}

	/**
	 * 处理BASE_PART节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBasePart(BLPolicy blPolicy, Node node)
		throws Exception 
	{	
		String confirmSequenceNo = XMLUtils.getChildNodeValue(node, "CONFIRM_SEQUENCE_NO");
		DateTime validTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND);
		
		
		String strReInsureFlag = "";
		if(blPolicy.getBLPrpCmain().getArr(0).getComCode().trim().substring(0, 2).equals("01") ||
		   blPolicy.getBLPrpCmain().getArr(0).getComCode().trim().substring(0, 2).equals("07"))
		{
			
		}
		else
		{
			strReInsureFlag = XMLUtils.getChildNodeValue(node, "REINSURE_FLAG");
		}
		
		

		
		CIInsureValidSchema schema = new CIInsureValidSchema();
		schema.setValidNo(confirmSequenceNo);				
		schema.setDemandNo(confirmSequenceNo);				
		schema.setProposalNo(blPolicy.getBLPrpCmain().getArr(0).getProposalNo());	
		schema.setPolicyNo(blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());		
		schema.setComCode(blPolicy.getBLPrpCmain().getArr(0).getComCode());			
		schema.setValidTime("" + validTime.toString());								
		
		
		if(blPolicy.getBLPrpCmain().getArr(0).getComCode().trim().substring(0, 2).equals("01") ||
		   blPolicy.getBLPrpCmain().getArr(0).getComCode().trim().substring(0, 2).equals("07"))
		{
			
		}
		else
		{
			schema.setReinsureFlag(strReInsureFlag);	
		}
		
		
		
		
		schema.setFlag("0    ");													
		
		
		
		blPolicy.getBLCIInsureValid().setArr(schema);
	}
	
	
	/**
	 * XXXXX存XX确认主信息
	 * 
	 * @param dbPool
	 * @param blpolicy
	 * @throws Exception
	 */
	private void saveCIInsureValid(DbPool dbPool, BLPolicy blPolicy)
			throws Exception 
	{
		BLCIInsureValid blCIInsureValid = blPolicy.getBLCIInsureValid();
		blCIInsureValid.save(dbPool);	
	}
}
