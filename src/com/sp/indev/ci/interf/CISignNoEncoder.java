package com.sp.indiv.ci.interf;

import java.sql.ResultSet;
import java.util.Vector;

import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.dbsvr.DBCIInsureValid;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

/**
 * 发送强三标志号码的编码器
 * 
 */
public class CISignNoEncoder {

	/**
	 * 编码
	 * 
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
	public String encode(DbPool dbPool, BLPolicy blPolicy) throws Exception {
		
		StringBuffer buf = new StringBuffer(4000);

		if (dbPool != null) {
			getCIInsureValid(dbPool, blPolicy);
			addXMLHead(buf);
			addPacket(buf, blPolicy, dbPool);
		} else {
			DbPool newDbPool = new DbPool();
			try {
				newDbPool.open(SysConfig.CONST_DDCCDATASOURCE);
				newDbPool.beginTransaction();

				getCIInsureValid(newDbPool, blPolicy);
				addXMLHead(buf);
				addPacket(buf, blPolicy, newDbPool);

				newDbPool.commitTransaction();
			} catch (Exception exception) {
				newDbPool.rollbackTransaction();
				exception.printStackTrace();
			} finally {
				newDbPool.close();
			}
		}
		return buf.toString();
	}

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
	 * 添加PACKET节
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addPacket(StringBuffer buf, BLPolicy blPolicy, DbPool dbPool)
			throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");

		addHead(buf, blPolicy);
		addBody(buf, blPolicy, dbPool);

		buf.append("</PACKET>");

	}

	/**
	 * 添加HEAD节
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addHead(StringBuffer buf, BLPolicy blPolicy)
			throws Exception {
		
		
		
		
		

		String strComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();

		buf.append("<HEAD>");
		addNode(buf, "REQUEST_TYPE", "70");
		addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_"
				+ strComCode.substring(0, 2) + "_USER"));
		addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_"
				+ strComCode.substring(0, 2) + "_PASSWORD"));
		buf.append("</HEAD>");
	}

	/**
	 * 添加BODY节
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBody(StringBuffer buf, BLPolicy blPolicy , DbPool dbPool)
			throws Exception {
		buf.append("<BODY>");
		addBasePart(buf, blPolicy, dbPool);
		buf.append("</BODY>");
	}

	/**
	 * 添加BASE_PART节
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBasePart(StringBuffer buf, BLPolicy blPolicy, DbPool dbPool)
			throws Exception {
		
		

		buf.append("<BASE_PART>");
		addPolicyList(buf, blPolicy, dbPool);
		buf.append("</BASE_PART>");
	}

	/**
	 * 增加Policy_List节
	 * 
	 * @param buf
	 * @param blPolicy
	 * @throws Exception 
	 */
	protected void addPolicyList(StringBuffer buf, BLPolicy blPolicy, DbPool dbPool) throws Exception {
		
		
		buf.append("<POLICY_LIST>");
		addPolicyData(buf, blPolicy, dbPool);
		buf.append("</POLICY_LIST>");
	}

	protected void addPolicyData(StringBuffer buf, BLPolicy blPolicy, DbPool dbPool) throws Exception {
		
		
		
		
		
		CIInsureValidSchema sechema = null;
		
		if (blPolicy != null){
			sechema = blPolicy.getBLCIInsureValid().getArr(0);
		}
		
		buf.append("<POLICY_DATA>");
		
		if (sechema != null){
			addNode(buf, "CONFIRM_SEQUENCE_NO", sechema.getValidNo());
			addNode(buf, "RELATIVE_POLICY_NO", sechema.getPolicyNo());
			addNode(buf, "TP_MARK", getCISignNo(dbPool,sechema.getPolicyNo()));
		}
		
		buf.append("</POLICY_DATA>");
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

	/**
	 * 获得XX确认主信息
	 * 
	 * @param blEndorse
	 * @throws Exception
	 */
	private void getCIInsureValid(DbPool dbPool, BLPolicy blPolicy)
			throws Exception {
		String sqlWhere = " Select * from CIInsureValid WHERE PolicyNo = '"
				+ blPolicy.getBLPrpCmain().getArr(0).getPolicyNo() + "'";

		DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
		Vector vector = dbCIInsureValid.findByConditions(dbPool, sqlWhere);

		CIInsureValidSchema cIInsureValidSchema = (CIInsureValidSchema) vector
				.get(0);

		BLCIInsureValid blCIInsureValid = new BLCIInsureValid();
		blCIInsureValid.setArr(cIInsureValidSchema);
		blPolicy.setBLCIInsureValid(blCIInsureValid);
	}

	/*
	 * 获得强三标志号码
	 */
	private String getCISignNo(DbPool dbPool, String policyNo) throws Exception {
		String signNo = "";
		String strSQL = " select cardealercode from prpcitemcar where policyno = '"+ policyNo +"'";

		ResultSet resultSet = dbPool.query(strSQL);
		
		while (resultSet.next()) {
			signNo = resultSet.getString("cardealercode");
		}
		return signNo;
	}
}
