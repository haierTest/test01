package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCIEndorValid;
import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.schema.CIEndorValidSchema;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.prpall.blsvr.pg.BLPrpPhead;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.schema.PrpPheadSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgDate;
import com.sp.utility.error.UserException;


/**
 * 发送XX注销请求数据的解码器
 * 
 */
public class PolicyCancelDecoder {

	public static void main(String[] args) 
		throws Exception 
	{













































	}

	/**
	 * 解码
	 * 
	 * @return 批改确认请求XML格式串
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, 
					   BLEndorse blEndorse, 
					   String content)
		throws SQLException, UserException, Exception 
	{
		InputStream in    = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		processHead(dbPool, blEndorse, XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
	}

	/**
	 * 处理HEAD节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processHead(DbPool dbPool, BLEndorse blEndorse, Node node)
			throws SQLException, UserException, Exception 
	{
		
		int type            = -1;
		String errorMessage = "";
		try
		{
			String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");

			if(!responseCode.equals("1")) {
				errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE"); 	
				type = 0;
			} 
			else {
				type = 1;
			}
		}
		catch(Exception ex1)
		{
			throw new Exception("解析平台返回串错误。批单号：" + blEndorse.getBLPrpPmain().getArr(0).getEndorseNo()
			             + "，请与管理员联系！" + ex1.getMessage());
		}
		
		
		if(type != 1)
		{
			throw new Exception(errorMessage);
		}
		
		
		try
		{
			updateCIInsureValid(dbPool, blEndorse, type);
		}
		catch(Exception ex2)
		{
			throw new Exception("与平台交互成功。但" + ex2.getMessage() + " 批单号：" + blEndorse.getBLPrpPmain().getArr(0).getEndorseNo());
		}
	}

	/*
	 * 更改XX确认表信息，如果成功将Flag的第2位标志成1，反之为0
	 * 
	 * @param dbPool @param blEndorse @throws Exception
	 */
	private void updateCIInsureValid(DbPool dbPool, 
			  						 BLEndorse blEndorse,
			  						 int type) 
	  throws SQLException, UserException, Exception 
	{
		if (type == 1)
		{
			type = 0;
		}else{
			type = 1;
		}
		
		
		
		String updateSQL = " UPDATE CIInsureValid SET Flag = SUBSTR(Flag, 1, 1) || '"
							+ type + "' || SUBSTR(Flag, 3, LENGTH(Flag))" + " WHERE PolicyNo = '"
							+ blEndorse.getBLPrpPmain().getArr(0).getPolicyNo() + "'";
		
		
		
		try
		{
			if(dbPool != null) 
			{
				
				dbPool.update(updateSQL);
			}
			else
			{
				throw new Exception("回写CIInsureValid表时dbPool为空。请务必马上与管理员联系处理数据！");
			}
		}
		catch(SQLException sqlex)
		{
			throw new Exception("操作SQL错误。" + sqlex.getMessage());
		}
		catch(Exception ex)
		{
			throw ex;
		}
		
	}
}
