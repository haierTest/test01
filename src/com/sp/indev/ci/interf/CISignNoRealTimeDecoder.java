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
 * 发送退XXXXX确认请求数据的解码器
 * 
 */
public class CISignNoRealTimeDecoder 
{
	Log logger = LogFactory.getLog(getClass());
	public static void main(String[] args) throws Exception {
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	/**
	 * 解码
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, BLPolicy blPolicy, String content)
			throws Exception 
	{
		InputStream in 		= new ByteArrayInputStream(content.getBytes());
		Document document 	= XMLUtils.parse(in);

		
		
		String  type = "3";
		

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
	 * 处理HEAD节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected String processHead(Node node, BLPolicy blPolicy) throws Exception {
		
		
		
		
		

		
		String type = "3";
		

		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE"); 

		if (responseCode.equals("1"))  
		{
			type = "0";
		}
		else if (responseCode.equals("0")) 
		{
			type = "1";
		}
		else
		{
			type = responseCode;
		}
		return type;
	}

	/*
	 * 更新CIInsureValid表的第四位为0,确认已经发送过。
	 */
	private void updateCIInsureValid(DbPool dbPool, BLPolicy blPolicy, String type)
			throws Exception 
	{
		if (blPolicy == null)
		{
			return;
		}
		
		String strUpdate 	= "";
		String policyNo 	= "";
		String flag 		= "";
		CIInsureValidSchema schema = null;
		schema = blPolicy.getBLCIInsureValid().getArr(0);
		
		if(!type.trim().equals("0"))
		{
			
			logger.info("=====XX标志实时上传错误单号-policyNo: " + schema.getPolicyNo());
			
		}
		
		if(schema != null) 
		{
			policyNo = schema.getPolicyNo();
			flag = schema.getFlag();

			
			if (flag.length() == 0) 
			{
				flag = "0  " + type + " ";
			} 
			else if (flag.length() == 1) 
			{
				flag = flag + "  " + type + " ";
			} 
			else if (flag.length() == 2) 
			{
				flag = flag + " " + type + " ";
			} 
			else if (flag.length() == 3) 
			{
				flag = flag + type + " ";
			} 
			else if (flag.length() == 4) 
			{
				flag = flag.substring(0, 3) + type + " ";
			} 
			else 
			{
				flag = flag.substring(0, 3) + type + flag.substring(5, flag.length());
			}

			strUpdate = " UPDATE CIInsureValid SET flag = '" + flag
						+ "', REMARK = 'XX标识实时上传' WHERE PolicyNo = '" + policyNo + "'";
			
			logger.info("====XX标志实时上传成功后回写标志位的SQL语句: " + strUpdate);
			
			dbPool.update(strUpdate);
		}
	}
}

