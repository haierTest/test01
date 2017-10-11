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
 * ����XXע���������ݵĽ�����
 * 
 */
public class PolicyCancelDecoder {

	public static void main(String[] args) 
		throws Exception 
	{













































	}

	/**
	 * ����
	 * 
	 * @return ����ȷ������XML��ʽ��
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
	 * ����HEAD��
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
			throw new Exception("����ƽ̨���ش����������ţ�" + blEndorse.getBLPrpPmain().getArr(0).getEndorseNo()
			             + "���������Ա��ϵ��" + ex1.getMessage());
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
			throw new Exception("��ƽ̨�����ɹ�����" + ex2.getMessage() + " �����ţ�" + blEndorse.getBLPrpPmain().getArr(0).getEndorseNo());
		}
	}

	/*
	 * ����XXȷ�ϱ���Ϣ������ɹ���Flag�ĵ�2λ��־��1����֮Ϊ0
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
				throw new Exception("��дCIInsureValid��ʱdbPoolΪ�ա���������������Ա��ϵ�������ݣ�");
			}
		}
		catch(SQLException sqlex)
		{
			throw new Exception("����SQL����" + sqlex.getMessage());
		}
		catch(Exception ex)
		{
			throw ex;
		}
		
	}
}
