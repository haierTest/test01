package com.sp.client.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.sp.client.Util.XmlUtil;
import com.sp.sysframework.exceptionlog.UserException;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.dbsvr.DBPrpDclientInfo;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * 下载基础信息和签到
 */
public class BLGetBasicInfo extends HttpServlet {
	Log logger = LogFactory.getLog(getClass());

	/**
	 * Constructor of the object.
	 */
	public BLGetBasicInfo() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); 
		
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		try {
			InputSource is = new InputSource(request.getInputStream());
			java.io.InputStream ins = is.getByteStream();
			Element element = XmlUtil.getRootElement(is);
			Element e[] = XmlUtil.getChildElements(element);
			String stDATATYPE = null;
			for (int i = 0; i < e.length; i++) {
				if (e[i].getNodeName().equals("DATA_TYPE")) {
					stDATATYPE = XmlUtil.getElenentValue(e[i]);
				}
			}
			if (stDATATYPE.equals("DPT")) {
				getDept(request, response, element);
			}
			if (stDATATYPE.equals("EMP")) {
				getEmp(request, response, element);
			}
			if (stDATATYPE.equals("AGT")) {
				getAgt(request, response, element);
			}
			if (stDATATYPE.equals("PCNo")) {
				getPCNo(request, response, element);
			}
			if (stDATATYPE.equals("AGTNO")) {
				getAgtNo(request, response, element);
			}
			if (stDATATYPE.equals("BRND")) {
				getCarModel(request, response, element);
			}
			if (stDATATYPE.equals("VCHTYP")) {
				getVisa(request, response, element);
			}
			
		} catch (UserException usee) {
			usee.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		
	}

	/*
	 * 获得机构信息
	 */
	public void getDept(HttpServletRequest request,
			HttpServletResponse response, Element element)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		DBManager dbManager = new DBManager();
		StringBuffer stbBeginCompany = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GB2312\"?>");
		try {
			InputSource is = new InputSource(request.getInputStream());
			java.io.InputStream ins = is.getByteStream();
			
			Element e[] = XmlUtil.getChildElements(element);
			StringBuffer stDeptCode = null;
			for (int i = 0; i < e.length; i++) {
				if (e[i].getNodeName().equals("DPT_CDE")) {
					stDeptCode = new StringBuffer(XmlUtil.getElenentValue(e[i]));
				}
			}
			int iStrlen = stDeptCode.length();
			for (int i = 0; i < 8 - iStrlen; i++) {
				stDeptCode.append("0");
			}
			
			dbManager.open("undwrtDataSource");
			String stQueryCondition = " select comcode,comcname,comlevel,"
					+ "nvl(addresscname,' ') addresscname,nvl(postcode,' ') postcode,"
					+ " nvl(reportphone,' ') phonenumber,insurername "
					+ "from prpdcompany where validstatus = 1 connect by uppercomcode = prior comcode start with comcode = '"
					+ stDeptCode + "'";
			
			logger.info(stQueryCondition);
			
			ResultSet rs = dbManager.executeQuery(stQueryCondition);
			stbBeginCompany.append("<DPT_LIST>");
			while (rs.next()) {
				stbBeginCompany.append("<DPT_INFO>");
				
				stbBeginCompany.append("<C_DPT_CDE>");
				stbBeginCompany.append(rs.getString("comcode"));
				stbBeginCompany.append("</C_DPT_CDE>");
				
				stbBeginCompany.append("<C_DPT_CNM>");
				stbBeginCompany.append(rs.getString("comcname"));
				stbBeginCompany.append("</C_DPT_CNM>");
				
				stbBeginCompany.append("<C_DPT_LVL>");
				stbBeginCompany.append(rs.getString("comlevel"));
				stbBeginCompany.append("</C_DPT_LVL>");
				
				stbBeginCompany.append("<C_DPT_ADDR>");
				stbBeginCompany.append(rs.getString("addresscname"));
				stbBeginCompany.append("</C_DPT_ADDR>");
				
				stbBeginCompany.append("<C_ZIP_CDE>");
				stbBeginCompany.append(rs.getString("postcode"));
				stbBeginCompany.append("</C_ZIP_CDE>");
				
				stbBeginCompany.append("<C_DPT_TEL>");
				stbBeginCompany.append(rs.getString("phonenumber"));
				stbBeginCompany.append("</C_DPT_TEL>");
				
				stbBeginCompany.append("<C_RPT_ADDR>");
				stbBeginCompany.append(rs.getString("addresscname"));
				stbBeginCompany.append("</C_RPT_ADDR>");
				
				stbBeginCompany.append("<C_RPT_TEL>");
				stbBeginCompany.append(rs.getString("phonenumber"));
				stbBeginCompany.append("</C_RPT_TEL>");
				
				stbBeginCompany.append("<C_INS_CNM>");
				stbBeginCompany.append(rs.getString("insurername"));
				stbBeginCompany.append("</C_INS_CNM>");
				stbBeginCompany.append("</DPT_INFO>");
			}
			rs.close();
			stbBeginCompany.append("</DPT_LIST>");
			out.println(stbBeginCompany);
			
		} catch (UserException usee) {
			usee.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				dbManager.close();
				
				logger.info("close");
				
			} catch (Exception dbe) {
			}
		}
	}

	/*
	 * 获得操作员信息
	 */
	public void getEmp(HttpServletRequest request,
			HttpServletResponse response, Element element)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		DBManager dbManager = new DBManager();
		DbPool dbpool = new DbPool();
		StringBuffer stbBeginUser = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GB2312\"?>");
		try {
			InputSource is = new InputSource(request.getInputStream());
			java.io.InputStream ins = is.getByteStream();
			
			Element e[] = XmlUtil.getChildElements(element);
			StringBuffer stDeptCode = null;
			for (int i = 0; i < e.length; i++) {
				if (e[i].getNodeName().equals("DPT_CDE")) {
					stDeptCode = new StringBuffer(XmlUtil.getElenentValue(e[i]));
				}
			}
			int iStrlen = stDeptCode.length();
			for (int i = 0; i < 8 - iStrlen; i++) {
				stDeptCode.append("0");
			}
			
			dbManager.open("undwrtDataSource");
			
			dbpool.setDBManager(dbManager);
			String stQueryCondition = " select a.usercode,a.username,a.comcode,b.insurername "
					+ "from prpduser a,prpdcompany b where a.validstatus = 1 and a.comcode = b.comcode "
					+ "and a.comcode in ( select comcode from prpdcompany connect by uppercomcode = prior comcode start with comcode = '"
					+ stDeptCode + "')";
			
			logger.info(stQueryCondition);
			
			ResultSet rs = dbManager.executeQuery(stQueryCondition);
			stbBeginUser.append("<EMP_LIST>");
			while (rs.next()) {
				stbBeginUser.append("<EMP_INFO>");
				
				stbBeginUser.append("<C_EMP_CDE>");
				stbBeginUser.append(rs.getString("usercode"));
				stbBeginUser.append("</C_EMP_CDE>");
				
				stbBeginUser.append("<C_EMP_CNM>");
				stbBeginUser.append(rs.getString("username"));
				stbBeginUser.append("</C_EMP_CNM>");
				
				stbBeginUser.append("<C_DPT_CDE>");
				stbBeginUser.append(rs.getString("comcode"));
				stbBeginUser.append("</C_DPT_CDE>");
				stbBeginUser.append("<C_SEL_DPT>");
				stbBeginUser.append(rs.getString("insurername"));
				stbBeginUser.append("</C_SEL_DPT>");
				stbBeginUser.append("</EMP_INFO>");
			}
			rs.close();
			stbBeginUser.append("</EMP_LIST>");
			
		} catch (UserException usee) {
			usee.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				dbpool.close();
			} catch (Exception dbe) {
			}
			try {
				dbManager.close();
			} catch (Exception dbe) {
			}
		}
		out.println(stbBeginUser);
	}

	/*
	 * 获得代理人信息
	 */
	public void getAgt(HttpServletRequest request,
			HttpServletResponse response, Element element)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		DBManager dbManager = new DBManager();
		DbPool dbpool = new DbPool();
		StringBuffer stbAgent = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GB2312\"?>");
		try {
			Element e[] = XmlUtil.getChildElements(element);
			StringBuffer stDeptCode = null;
			for (int i = 0; i < e.length; i++) {
				if (e[i].getNodeName().equals("DPT_CDE")) {
					stDeptCode = new StringBuffer(XmlUtil.getElenentValue(e[i]));
				}
			}
			int iStrlen = stDeptCode.length();
			for (int i = 0; i < 8 - iStrlen; i++) {
				stDeptCode.append("0");
			}
			
			dbManager.open("undwrtDataSource");
			
			dbpool.setDBManager(dbManager);
			stbAgent.append("<AGT_LIST>");
			String stQueryCondition = null;
			
			stQueryCondition = " select agentcode,agentname,'" + stDeptCode
					+ "' as comcode " + "from prpdagent where validstatus = 1 "
					+ " and lowerviewflag = 'Y' "
					+ " and comcode in ( select uppercomcode as comcode "
					+ "		from prpdcompany " + "		start with  comcode='"
					+ stDeptCode
					+ "'	connect by nocycle prior uppercomcode = comcode )";
			ResultSet rs = dbManager.executeQuery(stQueryCondition);
			while (rs.next()) {
				stbAgent.append("<AGT_INFO>");
				stbAgent.append("<C_AGT_CDE>");
				stbAgent.append(rs.getString("agentcode"));
				stbAgent.append("</C_AGT_CDE>");
				stbAgent.append("<C_AGT_CNM>");
				stbAgent.append(rs.getString("agentname"));
				stbAgent.append("</C_AGT_CNM>");
				stbAgent.append("<C_DPT_CDE>");
				stbAgent.append(rs.getString("comcode"));
				stbAgent.append("</C_DPT_CDE>");
				stbAgent.append("</AGT_INFO>");
			}
			
			stQueryCondition = " select agentcode,agentname,comcode "
					+ "from prpdagent where validstatus = 1 "
					+ " and comcode in ( select comcode from prpdcompany connect by uppercomcode = prior comcode start with comcode = '"
					+ stDeptCode + "')";
			rs = dbManager.executeQuery(stQueryCondition);
			while (rs.next()) {
				stbAgent.append("<AGT_INFO>");
				stbAgent.append("<C_AGT_CDE>");
				stbAgent.append(rs.getString("agentcode"));
				stbAgent.append("</C_AGT_CDE>");
				stbAgent.append("<C_AGT_CNM>");
				stbAgent.append(rs.getString("agentname"));
				stbAgent.append("</C_AGT_CNM>");
				stbAgent.append("<C_DPT_CDE>");
				stbAgent.append(rs.getString("comcode"));
				stbAgent.append("</C_DPT_CDE>");
				stbAgent.append("</AGT_INFO>");
			}
			rs.close();
			stbAgent.append("</AGT_LIST>");
			
			out.println(stbAgent);
		} catch (UserException usee) {
			usee.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				dbpool.close();
			} catch (Exception dbe) {
			}
			try {
				dbManager.close();
			} catch (Exception dbe) {
			}
		}
		
	}

	/*
	 * 签到获取机器码
	 */
	public void getPCNo(HttpServletRequest request,
			HttpServletResponse response, Element element)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		DbPool dbpool = new DbPool();
		StringBuffer strComCode = new StringBuffer("");
		String strPCNo = "";
		String strUserCode = "";
		String strPassWord = "";
		String strMACaddress = "";
		String strMessage = "";
		String stQueryCondition = null;
		String flag = null;
		StringBuffer stbPCInfo = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GB2312\"?>");
		try {
			InputSource is = new InputSource(request.getInputStream());
			java.io.InputStream ins = is.getByteStream();
			Element e[] = XmlUtil.getChildElements(element);
			for (int i = 0; i < e.length; i++) {
				if (e[i].getNodeName().equals("C_DPT_CDE")) {
					strComCode.append(XmlUtil.getElenentValue(e[i]));
				}
				if (e[i].getNodeName().equals("C_PC_NO")) {
					strPCNo = XmlUtil.getElenentValue(e[i]);
				}
				if (e[i].getNodeName().equals("C_EMP_CDE")) {
					strUserCode = XmlUtil.getElenentValue(e[i]);
				}
				if (e[i].getNodeName().equals("C_EMP_PWD")) {
					strPassWord = XmlUtil.getElenentValue(e[i]);
				}
				if (e[i].getNodeName().equals("C_MAC_STR")) {
					strMACaddress = XmlUtil.getElenentValue(e[i]);
				}
			}

			int iStrlen = strComCode.length();
			for (int i = 0; i < 8 - iStrlen; i++) {
				strComCode.append("0");
			}
			
			dbpool.open("ddccDataSource");
			if (strPCNo == null || strPCNo.equals("")) {
				
				stQueryCondition = " SELECT nvl(MAX(PCNo),0)+1 PCNo "
						+ "FROM PrpDclientInfo WHERE comcode = '" + strComCode
						+ "'";
				
				logger.info(stQueryCondition);
				
				ResultSet rs = dbpool.executeQuery(stQueryCondition);
				if (rs.next()) {
					strPCNo = rs.getString("PCNo");
				}
				if (!strPCNo.equals("1")) {
					
					stQueryCondition = " SELECT flag "
							+ "FROM PrpDclientInfo WHERE comcode = '"
							+ strComCode + "'";
					rs = dbpool.executeQuery(stQueryCondition);
					if (rs.next()) {
						flag = rs.getString("flag");
						if ("N".equals(flag)) {
							strMessage = "该机构没有单机版权限！";
						} else {
							strMessage = "";
						}
					}
				} else {
					
					flag = "N";
					strMessage = "该机构没有单机版权限！";
				}

				DBPrpDclientInfo dbPrpDclientInfo = new DBPrpDclientInfo();
				dbPrpDclientInfo.setComCode(strComCode.toString());
				dbPrpDclientInfo.setPCNo(strPCNo);
				dbPrpDclientInfo.setUserCode(strUserCode);
				dbPrpDclientInfo.setPassWord(strPassWord);
				dbPrpDclientInfo.setFlag(flag);
				dbPrpDclientInfo.insert();
				rs.close();
				stbPCInfo.append(strBcreateRetMsg(strComCode.toString(),
						strPCNo, strUserCode, strPassWord, strMACaddress,
						strMessage));
			} else {
				
				
				stQueryCondition = " SELECT flag FROM PrpDclientInfo "
						+ "WHERE ComCode = '" + strComCode + "' "
						+ "AND PCNo='" + strPCNo + "' AND UserCode='"
						+ strUserCode + "'";
				
				logger.info(stQueryCondition);
				
				ResultSet rs = dbpool.executeQuery(stQueryCondition);
				if (rs.next()) {
					
					strMessage = "";
					flag = rs.getString("flag");
					if (flag.equals("N")) {
						strMessage = "该机构没有单机版权限！";
					} else {
						strMessage = "";
					}
				} else {
					
					
					stQueryCondition = " SELECT nvl(MAX(PCNo),0)+1 PCNo "
							+ "FROM PrpDclientInfo WHERE comcode = '"
							+ strComCode + "'";
					ResultSet rs1 = dbpool.executeQuery(stQueryCondition);
					if (rs1.next()) {
						strPCNo = rs1.getString("PCNo");
					}
					if (!strPCNo.equals("1")) {
						
						
						stQueryCondition = " SELECT flag "
								+ "FROM PrpDclientInfo WHERE comcode = '"
								+ strComCode + "'";
						rs1 = dbpool.executeQuery(stQueryCondition);
						if (rs1.next()) {
							flag = rs1.getString("flag");
							if ("N".equals(flag)) {
								strMessage = "该机构没有单机版权限！";
							} else {
								strMessage = "";
							}
						}
					} else {
						
						flag = "N";
						strMessage = "该机构没有单机版权限！";
					}
					
					stQueryCondition = " SELECT count(1) cnt "
							+ "FROM PrpDclientInfo WHERE  ComCode = '"
							+ strComCode + "' " + "AND UserCode='"
							+ strUserCode + "'";
					rs1 = dbpool.executeQuery(stQueryCondition);
					int cnt = 0;
					if (rs1.next()) {
						cnt = rs1.getInt(1);
					}
					
					logger.info(cnt);
					
					if (cnt == 0) {
						
						DBPrpDclientInfo dbPrpDclientInfo = new DBPrpDclientInfo();
						dbPrpDclientInfo.setComCode(strComCode.toString());
						dbPrpDclientInfo.setPCNo(strPCNo);
						dbPrpDclientInfo.setUserCode(strUserCode);
						dbPrpDclientInfo.setPassWord(strPassWord);
						dbPrpDclientInfo.setFlag(flag);
						dbPrpDclientInfo.insert();
					}
					rs1.close();
				}
				rs.close();

				stbPCInfo.append(strBcreateRetMsg(strComCode.toString(),
						strPCNo, strUserCode, strPassWord, strMACaddress,
						strMessage));
			}
			
			out.println(stbPCInfo);
		} catch (UserException usee) {
			usee.printStackTrace();
			strMessage = "登录失败1！";
			stbPCInfo.append(strBcreateRetMsg(strComCode.toString(), strPCNo,
					strUserCode, strPassWord, strMACaddress, strMessage));
			
			logger.error(usee.getMessage());
			
			out.println(stbPCInfo);
		} catch (Exception e) {
			e.printStackTrace();
			strMessage = "登录失败2！";
			stbPCInfo.append(strBcreateRetMsg(strComCode.toString(), strPCNo,
					strUserCode, strPassWord, strMACaddress, strMessage));
			
			logger.error(stbPCInfo.toString());
			logger.error(strMessage);
			
			out.println(stbPCInfo);
		}
		finally {
			try {
				dbpool.close();
			} catch (Exception dbe) {
			}
		}
		
	}

	public StringBuffer strBcreateRetMsg(String strComCode, String strPCNo,
			String strUserCode, String strPassWord, String strMACaddress,
			String strMessage) {
		StringBuffer stbPCInfo = new StringBuffer("");
		stbPCInfo.append("<C_DPT_CDE>");
		stbPCInfo.append(strComCode);
		stbPCInfo.append("</C_DPT_CDE>");

		stbPCInfo.append("<C_PC_NO>");
		stbPCInfo.append(strPCNo);
		stbPCInfo.append("</C_PC_NO>");

		stbPCInfo.append("<C_EMP_CDE>");
		stbPCInfo.append(strUserCode);
		stbPCInfo.append("</C_EMP_CDE>");

		stbPCInfo.append("<C_EMP_PWD>");
		stbPCInfo.append(strPassWord);
		stbPCInfo.append("</C_EMP_PWD>");

		stbPCInfo.append("<C_MAC_STR>");
		stbPCInfo.append(strMACaddress);
		stbPCInfo.append("</C_MAC_STR>");

		stbPCInfo.append("<C_ERR_MSG>");
		stbPCInfo.append(strMessage);
		stbPCInfo.append("</C_ERR_MSG>");
		return stbPCInfo;
	}

	/*
	 * 获得代理协议
	 */
	public void getAgtNo(HttpServletRequest request,
			HttpServletResponse response, Element element)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		DbPool dbpool = new DbPool();
		String strComCode = "";
		String strPCNo = "";
		String strUserCode = "";
		String strPassWord = "";
		StringBuffer stbPCInfo = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GB2312\"?>");
		try {
			InputSource is = new InputSource(request.getInputStream());
			java.io.InputStream ins = is.getByteStream();
			
			StringBuffer stDeptCode = null;
			Element e[] = XmlUtil.getChildElements(element);
			for (int i = 0; i < e.length; i++) {
				if (e[i].getNodeName().equals("DPT_CDE")) {
					stDeptCode = new StringBuffer(XmlUtil.getElenentValue(e[i]));
				}
			}
			int iStrlen = stDeptCode.length();
			for (int i = 0; i < 8 - iStrlen; i++) {
				stDeptCode.append("0");
			}
			
			dbpool.open("ddccDataSource");
			String stQueryCondition = null;
			stQueryCondition = " select a.comcode,a.agreementno,"
					+ "a.agentcode,b.agentname,to_char(a.startdate,'yyyy-mm-dd') startdate,to_char(a.enddate,'yyyy-mm-dd') enddate "
					+ "from prpdagreement a , "
					+ " ( select agentcode,agentname,comcode "
					+ " from prpdagent where validstatus = 1 "
					+ " and lowerviewflag = 'Y' "
					+ " and comcode in ( select uppercomcode as comcode "
					+ " 	from prpdcompany 	start with  comcode='"
					+ stDeptCode
					+ "'"
					+ " connect by nocycle prior uppercomcode = comcode ) "
					+ "  union select agentcode,agentname,comcode "
					+ " from prpdagent where validstatus = 1 "
					+ " and comcode in ( select comcode from prpdcompany connect by uppercomcode = prior comcode start with comcode = '"
					+ stDeptCode + "'))b "
					+ " where a.agentcode = b.agentcode "
					+ " and a.validstatus = 1 ";
			ResultSet rs = dbpool.executeQuery(stQueryCondition);
			stbPCInfo.append("<AGTNO_LIST>");
			while (rs.next()) {
				stbPCInfo.append("<AGTNO_INFO>");
				stbPCInfo.append("<C_DPT_CDE>");
				stbPCInfo.append(rs.getString("comcode"));
				stbPCInfo.append("</C_DPT_CDE>");
				stbPCInfo.append("<C_AGT_NO>");
				stbPCInfo.append(rs.getString("agreementno"));
				stbPCInfo.append("</C_AGT_NO>");
				stbPCInfo.append("<N_AGT_SUBNO>");
				stbPCInfo.append("");
				stbPCInfo.append("</N_AGT_SUBNO>");
				stbPCInfo.append("<C_AGT_CDE>");
				stbPCInfo.append(rs.getString("agentcode"));
				stbPCInfo.append("</C_AGT_CDE>");
				stbPCInfo.append("<C_AGT_CNM>");
				stbPCInfo.append(rs.getString("agentname"));
				stbPCInfo.append("</C_AGT_CNM>");
				stbPCInfo.append("<D_START_DATE>");
				stbPCInfo.append(rs.getString("startdate"));
				stbPCInfo.append("</D_START_DATE>");
				stbPCInfo.append("<D_END_DATE>");
				stbPCInfo.append(rs.getString("enddate"));
				stbPCInfo.append("</D_END_DATE>");
				stbPCInfo.append("</AGTNO_INFO>");
			}
			rs.close();
			stbPCInfo.append("</AGTNO_LIST>");
			

			
			out.println(stbPCInfo);
		} catch (UserException usee) {
			usee.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				dbpool.close();
			} catch (Exception dbe) {
			}
		}
		
	}

	public void getCarModel(HttpServletRequest request,
			HttpServletResponse response, Element element)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		DBManager dbManager = new DBManager();
		DbPool dbpool = new DbPool();
		String strBeginDate = "";
		String strEndDate = "";
		StringBuffer stbAgent = new StringBuffer("<?xml version=" + "\""
				+ "1.0" + "\"" + " encoding=" + "\"" + "GB2312" + "\"" + "?>");
		try {
			InputSource is = new InputSource(request.getInputStream());
			java.io.InputStream ins = is.getByteStream();
			out.println(stbAgent);
			
			Element e[] = XmlUtil.getChildElements(element);
			for (int i = 0; i < e.length; i++) {
				if (e[i].getNodeName().equals("DATE_BEGIN")) {
					strBeginDate = XmlUtil.getElenentValue(e[i]);
				}
				if (e[i].getNodeName().equals("DATE_END")) {
					strEndDate = XmlUtil.getElenentValue(e[i]);
				}
			}

			
			dbManager.open("ddccDataSource");
			
			dbpool.setDBManager(dbManager);
			String stQueryCondition = " SELECT ModelCode,ModelName,Factory,PurchasePrice,Exhaustscale,SeatCount,TonCount "
					+ "From PrpDcarModel "
					+ "where validstatus = 1 "
					+ "and VALIDDATE between '"
					+ strBeginDate
					+ "' AND '"
					+ strEndDate + "'";
			
			logger.info(stQueryCondition);
			
			
			ResultSet rs = null;
			String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
			DbPool dbpoolNew = new DbPool();
			try {
			if ("1".equals(strSwitch)) {
			    dbpoolNew.open("platformNewDataSource");
			    rs = dbpoolNew.executeQuery(stQueryCondition);
			} else {
				rs = dbManager.executeQuery(stQueryCondition);
			}
			
			out.println("<BRND_LIST>");
			StringBuffer stbCar = null;
			while (rs.next()) {
				stbCar = new StringBuffer("");
				stbCar.append("<BRND_INFO>\r\n");
				stbCar.append("<C_BRND_CDE>");
				stbCar.append(rs.getString("ModelCode"));
				stbCar.append("</C_BRND_CDE>\r\n");

				stbCar.append("<C_BRND_CNM>");
				stbCar.append(rs.getString("ModelName"));
				stbCar.append("</C_BRND_CNM>\r\n");

				stbCar.append("<C_FACT_CNM>");
				stbCar.append(rs.getString("Factory"));
				stbCar.append("</C_FACT_CNM>\r\n");

				stbCar.append("<N_VHL_VAL>");
				stbCar.append(rs.getString("PurchasePrice"));
				stbCar.append("</N_VHL_VAL>\r\n");

				stbCar.append("<N_EXT_MSR>");
				stbCar.append(rs.getString("Exhaustscale"));
				stbCar.append("</N_EXT_MSR>\r\n");

				stbCar.append("<N_SET_NUM>");
				stbCar.append(rs.getString("SeatCount"));
				stbCar.append("</N_SET_NUM>\r\n");

				stbCar.append("<N_TON_NUM>");
				stbCar.append(rs.getString("TonCount"));
				stbCar.append("</N_TON_NUM>\r\n");
				stbCar.append("</BRND_INFO>");
				out.println(stbCar);
				out.flush();
			}
			rs.close();
			} catch (Exception ex) {
			    ex.printStackTrace();
			    throw ex;
			} finally {
			   	dbpoolNew.close();
			}
			
			out.println("</BRND_LIST>");
			

		} catch (UserException usee) {
			usee.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				dbpool.rollbackTransaction();
				dbpool.close();
			} catch (Exception dbe) {
			}
			try {
				dbManager.close();
			} catch (Exception dbe) {
			}
		}
		
	}

	/*
	 * 获得单证类型
	 */
	public void getVisa(HttpServletRequest request,
			HttpServletResponse response, Element element)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=gb2312");
		PrintWriter out = response.getWriter();
		DBManager dbManager = new DBManager();
		DbPool dbpool = new DbPool();
		StringBuffer stbAgent = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GB2312\"?>");
		ResultSet rs = null;
		ResultSet rs1 = null;
		try {
			Element e[] = XmlUtil.getChildElements(element);
			StringBuffer stDeptCode = null;
			for (int i = 0; i < e.length; i++) {
				if (e[i].getNodeName().equals("DPT_CDE")) {
					stDeptCode = new StringBuffer(XmlUtil.getElenentValue(e[i]));
				}
			}
			int iStrlen = stDeptCode.length();
			for (int i = 0; i < 8 - iStrlen; i++) {
				stDeptCode.append("0");
			}
			
			dbManager.open("undwrtDataSource");
			
			dbpool.setDBManager(dbManager);
			String stQueryCondition = " select certitype,visacode,visaname,rationtype "
					+ "from VsCodeSet "
					+ " where validstatus = 1 "
					+ " and ( riskcode = '0507' or riskcode = '0000' and certitype = 'R') "
					+ " and comcode like substr('" + stDeptCode + "',1,2)||'%'";
			rs = dbManager.executeQuery(stQueryCondition);
			ArrayList vsList = new ArrayList();
			while (rs.next()) {
				Map vsMap = new HashMap();
				vsMap.put("certitype", rs.getString("certitype"));
				vsMap.put("visacode", rs.getString("visacode"));
				vsMap.put("visaname", rs.getString("visaname"));
				vsMap.put("rationtype", rs.getString("rationtype"));
				vsList.add(vsMap);
			}
			rs.close();
			Iterator it = vsList.iterator();
			stbAgent.append("<VCH_LIST>");
			while (it.hasNext()) {
				Map resultMap = (Map) it.next();
				String stQueryVch = null;
				stbAgent.append("<VCH_INFO>");
				stbAgent.append("<C_VCH_NO>");
				String stVisaCode = (String) resultMap.get("visacode");
				String stVisaName = (String) resultMap.get("visaname");
				stQueryVch = " select codeename from prpdcode where codetype='PrintLab' "
						+ " and codecode = '"
						+ (String) resultMap.get("certitype") + "'";
		        
		        DbPool dbpoolNew = new DbPool();
		        try {
		            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
		            if ("1".equals(strSwitch)) {
		                dbpoolNew.open("platformNewDataSource");
		                rs1 = dbpoolNew.executeQuery(stQueryVch);
		            } else {
		            	rs1 = dbManager.executeQuery(stQueryVch);
		            }
					if (rs1.next()) {
						stbAgent.append(rs1.getString("codeename"));
					} else {
						stQueryVch = " select codeename from prpdcode where codetype='PrintLab' "
								+ " and codecode = '"
								+ (String) resultMap.get("rationtype") + "'";
				        
				        try {
				            if ("1".equals(strSwitch)) {
				                dbpoolNew.open("platformNewDataSource");
				                rs1 = dbpoolNew.executeQuery(stQueryVch);
				            } else {
				            	rs1 = dbManager.executeQuery(stQueryVch);
				            }
				        } catch (Exception ex) {
				            ex.printStackTrace();
				            throw ex;
				        }
				        
						if (rs1.next()) {
							stbAgent.append(rs1.getString("codeename"));
						}
					}
					rs1.close();
				} catch (Exception ex) {
		            ex.printStackTrace();
		            throw ex;
		        } finally {
		            dbpoolNew.close();
		        }
		        
				stbAgent.append("</C_VCH_NO>");
				stbAgent.append("<C_VCH_CDE>");
				stbAgent.append(stVisaCode);
				stbAgent.append("</C_VCH_CDE>");
				stbAgent.append("<C_VCH_CNM>");
				stbAgent.append(stVisaName);
				stbAgent.append("</C_VCH_CNM>");
				stbAgent.append("</VCH_INFO>");
			}
			stbAgent.append("</VCH_LIST>");
			
			out.println(stbAgent);
		} catch (UserException usee) {
			usee.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {

			}
			try {
				if (rs1 != null) {
					rs1.close();
				}
			} catch (Exception e) {

			}
			try {
				dbpool.close();
			} catch (Exception dbe) {
			}
			try {
				dbManager.close();
			} catch (Exception dbe) {
			}
		}
		
	}
}
