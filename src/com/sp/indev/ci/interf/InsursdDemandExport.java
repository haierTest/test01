package com.sp.indiv.ci.interf;

import java.sql.ResultSet;
import java.util.Vector;

import com.sp.indiv.health.interf.TransCode;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

public class InsursdDemandExport {

	private static int COUNT = 0;
	private static Vector RECORD = new Vector();

	public String encode(String comCode, String flag, String startDate, String endDate) throws Exception {
		StringBuffer buf = new StringBuffer();
		query(comCode, flag, startDate, endDate);
		addXMLHead(buf);
		addPacket(buf, comCode, flag, startDate, endDate);
		return buf.toString();
	}

	protected void query(String comCode, String flag, String startDate, String endDate) throws Exception {
		DbPool dbpool = new DbPool();
		ResultSet resultSet = null;
		String strSQL = "";
		try {
			dbpool.open(SysConfig.getProperty("ggSunDBDataSource"));
			if ("0001".equals(flag)) {
				strSQL = "select '<RECORD><DISTRICT_CODE>"+translateComCode(comCode)+"</DISTRICT_CODE>"
						+ "<COMPANY_CODE>YGBX</COMPANY_CODE>"
						+ "<QUERY_SEQUENCE_NO>'||a.demandno||'</QUERY_SEQUENCE_NO>"
						+ "<QUERY_DATE>'||to_char(a.demandtime,'yyyymmdd')||'</QUERY_DATE>"
						+ "<BILL_DATE>'||to_char(a.billdate,'yyyymmdd')||'</BILL_DATE>"
						+ "<START_DATE>'||to_char(a.STARTDATE,'yyyymmdd')||'</START_DATE>"
						+ "<END_DATE>'||to_char(a.ENDDATE,'yyyymmdd')||'</END_DATE>"
						+ "<LICENSE_TYPE>'||a.licensetype||'</LICENSE_TYPE>"
						+ "<MOTOR_TYPE_CODE>'||a.modelcode||'</MOTOR_TYPE_CODE>"
						+ "<USE_NATURE_CODE>'||a.usenaturecode||'</USE_NATURE_CODE>"
						+ "<LICENSE_NO>'||a.licenseno||'</LICENSE_NO>"
						+ "<FRAME_NO>'||a.frameno||'</FRAME_NO>"
						+ "<ENGINE_NO>'||a.engineno||'</ENGINE_NO>"
						+ "<PREMIUM>'||a.premium||'</PREMIUM></RECORD>' as record from ciinsuredemand a "
						+ "where a.comcode like '"+comCode.substring(0, 2)+"%' "
						+ "and a.demandtime>=to_date('"+startDate+"','YYYY-MM-DD')"
						+ "and a.demandtime<=to_date('"+endDate+"','YYYY-MM-DD')"
						+ "and a.demandno like '0%' ";
			} else if ("0002".equals(flag)) {
				strSQL = "select '<RECORD><DISTRICT_CODE>"+translateComCode(comCode)+"</DISTRICT_CODE>"
						+ "<COMPANY_CODE>YGBX</COMPANY_CODE>"
						+ "<POLICY_NO>'||a.policyno||'</POLICY_NO>"
						+ "<QUERY_SEQUENCE_NO>'||a.demandno||'</QUERY_SEQUENCE_NO>"
						+ "<CONFIRMSEQUENCE_NO>'||a.validno||'</CONFIRMSEQUENCE_NO>"
						+ "<CONFIRM_DATE>'||to_char(a.validtime,'yyyymmdd')||'</CONFIRM_DATE>"
						+ "<BILL_DATE>'||to_char(b.billdate,'yyyymmdd')||'</BILL_DATE>"
						+ "<START_DATE>'||to_char(b.STARTDATE,'yyyymmdd')||'</START_DATE>"
						+ "<END_DATE>'||to_char(b.ENDDATE,'yyyymmdd')||'</END_DATE>"
						+ "<LICENSE_TYPE>'||b.licensetype||'</LICENSE_TYPE>"
						+ "<MOTOR_TYPE_CODE>'||b.modelcode||'</MOTOR_TYPE_CODE>"
						+ "<USE_NATURE_CODE>'||b.usenaturecode||'</USE_NATURE_CODE>"
						+ "<LICENSE_NO>'||b.licenseno||'</LICENSE_NO>"
						+ "<FRAME_NO>'||b.frameno||'</FRAME_NO>"
						+ "<ENGINE_NO>'||b.engineno||'</ENGINE_NO>"
						+ "<PREMIUM>'||b.premium||'</PREMIUM></RECORD>' as record from ciinsurevalid a,ciinsuredemand b "
						+ "where a.demandno=b.demandno and a.comcode like '"+comCode.substring(0, 2)+"%' "
						+ "and a.validtime>=to_date('"+startDate+"','YYYY-MM-DD')"
						+ "and a.validtime<=to_date('"+endDate+"','YYYY-MM-DD')"
						+ "and a.validno like '0%'";
			} else if ("V0001".equals(flag)) {
				strSQL = "select '<RECORD><DISTRICT_CODE>"+translateComCode(comCode)+"</DISTRICT_CODE>"
						+ "<COMPANY_CODE>YGBX</COMPANY_CODE>"
						+ "<QUERY_SEQUENCE_NO>'||a.demandno||'</QUERY_SEQUENCE_NO>"
						+ "<QUERY_DATE>'||to_char(a.demandtime,'yyyymmdd')||'</QUERY_DATE>"
						+ "<BILL_DATE>'||to_char(a.billdate,'yyyymmdd')||'</BILL_DATE>"
						+ "<START_DATE>'||to_char(a.STARTDATE,'yyyymmdd')||'</START_DATE>"
						+ "<END_DATE>'||to_char(a.ENDDATE,'yyyymmdd')||'</END_DATE>"
						+ "<LICENSE_TYPE>'||a.licensetype||'</LICENSE_TYPE>"
						+ "<MOTOR_TYPE_CODE>'||a.modelcode||'</MOTOR_TYPE_CODE>"
						+ "<USE_NATURE_CODE>'||a.usenaturecode||'</USE_NATURE_CODE>"
						+ "<LICENSE_NO>'||a.licenseno||'</LICENSE_NO>"
						+ "<FRAME_NO>'||a.frameno||'</FRAME_NO>"
						+ "<ENGINE_NO>'||a.engineno||'</ENGINE_NO>"
						+ "<PREMIUM>'||a.premium||'</PREMIUM></RECORD>' as record from ciinsuredemand a "
						+ "where a.comcode like '"+comCode.substring(0, 2)+"%' "
						+ "and a.demandtime>=to_date('"+startDate+"','YYYY-MM-DD')"
						+ "and a.demandtime<=to_date('"+endDate+"','YYYY-MM-DD')"
						+ "and a.demandno like 'V%' ";
			} else if ("V0002".equals(flag)) {
				strSQL = "select '<RECORD><DISTRICT_CODE>"+translateComCode(comCode)+"</DISTRICT_CODE>"
						+ "<COMPANY_CODE>YGBX</COMPANY_CODE>"
						+ "<POLICY_NO>'||a.policyno||'</POLICY_NO>"
						+ "<QUERY_SEQUENCE_NO>'||a.demandno||'</QUERY_SEQUENCE_NO>"
						+ "<CONFIRMSEQUENCE_NO>'||a.validno||'</CONFIRMSEQUENCE_NO>"
						+ "<CONFIRM_DATE>'||to_char(a.validtime,'yyyymmdd')||'</CONFIRM_DATE>"
						+ "<BILL_DATE>'||to_char(b.billdate,'yyyymmdd')||'</BILL_DATE>"
						+ "<START_DATE>'||to_char(b.STARTDATE,'yyyymmdd')||'</START_DATE>"
						+ "<END_DATE>'||to_char(b.ENDDATE,'yyyymmdd')||'</END_DATE>"
						+ "<LICENSE_TYPE>'||b.licensetype||'</LICENSE_TYPE>"
						+ "<MOTOR_TYPE_CODE>'||b.modelcode||'</MOTOR_TYPE_CODE>"
						+ "<USE_NATURE_CODE>'||b.usenaturecode||'</USE_NATURE_CODE>"
						+ "<LICENSE_NO>'||b.licenseno||'</LICENSE_NO>"
						+ "<FRAME_NO>'||b.frameno||'</FRAME_NO>"
						+ "<ENGINE_NO>'||b.engineno||'</ENGINE_NO>"
						+ "<PREMIUM>'||b.premium||'</PREMIUM></RECORD>' as record from ciinsurevalid a,ciinsuredemand b "
						+ "where a.demandno=b.demandno "
						+ "and a.comcode like '"+comCode.substring(0, 2)+"%' "
						+ "and a.validtime>=to_date('"+startDate+"','YYYY-MM-DD')"
						+ "and a.validtime<=to_date('"+endDate+"','YYYY-MM-DD')"
						+ "and a.validno like 'V%'";
			}
			resultSet = dbpool.query(strSQL);
			while (resultSet.next()) {
				RECORD.add(resultSet.getString("record"));
				COUNT++;
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dbpool.close();
		}
	}

	protected void addXMLHead(StringBuffer buf) throws Exception {
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
	}

	protected void addPacket(StringBuffer buf, String comCode, String flag, String startDate, String endDate) throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
		addHead(buf, comCode, flag, endDate);
		addBody(buf);
		buf.append("</PACKET>");
	}

	protected void addHead(StringBuffer buf, String comCode, String flag, String endDate) throws Exception {
		buf.append("<HEAD>");
		addNode(buf, "PACKAGE_SQUENCE_NO", translateName(comCode, endDate, flag));
		addNode(buf, "DATA_INSERT_COUNT", COUNT + "");
		addNode(buf, "DATA_DELETE_COUNT", "0");
		addNode(buf, "REQUEST_TYPE", flag);
		addNode(buf, "YEARMONTH ", correctDate(endDate).substring(0, 6));
		buf.append("</HEAD>");
	}

	protected void addBody(StringBuffer buf) throws Exception {
		buf.append("<DATA>");
		buf.append("<DATA_INSERT_LIST>");
		for (int i = 0; i < COUNT; i++) {
			buf.append((String) RECORD.get(i));
		}
		COUNT = 0;
		RECORD = new Vector();
		buf.append("</DATA_INSERT_LIST>");
		buf.append("<DATA_DELETE_LIST>");
		buf.append("</DATA_DELETE_LIST>");
		buf.append("</DATA>");
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

	private String correctDate(String dateString) {
		String result = StringUtils.replace(dateString, "-", "");
		return result;
	}

	private String translateComCode(String comCode) throws Exception {
		String strCode = comCode;
		
		
		strCode = new BLPrpDcode().translateNewCode(comCode.substring(0, 2), "PlatformAreaCode");
		
		
















































		
		return strCode;
	}
	public String translateName(String comCode, String endDate, String flag) throws Exception {
		return correctDate(endDate) + "_" + translateComCode(comCode) + "_YGBX_" + flag + "_01";
	}
}
