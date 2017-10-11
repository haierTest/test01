package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * ������XXXXXȷ���������ݵĽ�����
 * 
 */
public class CISignNoDecoder {

	Log logger = LogFactory.getLog(getClass());
	public static void main(String[] args) throws Exception {
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	/**
	 * ����
	 * 
	 * @return XX��ѯ����XML��ʽ��
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, BLPolicy blPolicy, String content)
			throws Exception 
	{
		InputStream in 		= new ByteArrayInputStream(content.getBytes());
		Document document 	= XMLUtils.parse(in);

		
		
		int type = 1;
		

		if(content != null && !content.equals("")) 
		{
			type = processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"), blPolicy);
		}

		if(dbPool != null) 
		{
			
			updateCIInsureValid(dbPool, blPolicy, type);
		} 
		else 
		{
			DbPool newDbPool = new DbPool();
			try 
			{
				newDbPool.open(SysConfig.CONST_DDCCDATASOURCE);
				newDbPool.beginTransaction();

				
				updateCIInsureValid(newDbPool, blPolicy, type);

				newDbPool.commitTransaction();
			} catch (Exception exception) 
			{
				newDbPool.rollbackTransaction();
				exception.printStackTrace();
			} finally 
			{
				newDbPool.close();
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
	protected int processHead(Node node, BLPolicy blPolicy) throws Exception {
		
		
		
		
		

		
		
		int type = 1;
		

		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE"); 

		if (!responseCode.equals("1")) {
			type = 0;
		}
		return type;
	}

	/*
	 * ����CIInsureValid��ĵ���λΪ0,ȷ���Ѿ����͹���
	 */
	private void updateCIInsureValid(DbPool dbPool, BLPolicy blPolicy, int type)
			throws Exception 
	{
		if (blPolicy == null) {
			return;
		}

		
		if (type == 0)
		{
			type = 1;
		} else 
		{
			type = 0;
		}

		String strUpdate = "";
		String policyNo = "";
		String flag = "";

		CIInsureValidSchema schema = null;

		schema = blPolicy.getBLCIInsureValid().getArr(0);
		
		if(type == 1)
		{
			
			logger.info("=====���ϴ�XX��־���󵥺�-policyNo: " + schema.getPolicyNo());
			
		}
		

		if (schema != null) 
		{
			policyNo = schema.getPolicyNo();
			flag = schema.getFlag();

			
			if (flag.length() == 0) 
			{
				flag = "0  " + type;
			} 
			else if (flag.length() == 1) 
			{
				flag = flag + "  " + type;
			} 
			else if (flag.length() == 2) 
			{
				flag = flag + " " + type;
			} 
			else if (flag.length() == 3) 
			{
				flag = flag + type;
			} 
			else if (flag.length() == 4) 
			{
				flag = flag.substring(0, 3) + type;
			} 
			else 
			{
				flag = flag.substring(0, 3) + type + flag.substring(5, flag.length());
			}

			strUpdate = " UPDATE CIInsureValid SET flag = '" + flag
					+ "' WHERE PolicyNo = '" + policyNo + "'";
			
			logger.info("====XX��ʶ�ϴ��ɹ����д��־λ��SQL���: " + strUpdate);
			
			dbPool.update(strUpdate);
		}
	}
}
