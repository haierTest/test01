<%--
****************************************************************************
* Description:		��ǿ����ҵXX������ϵ�ϴ�(��ϸ����)
* Author:			  pengyuming
* Date:				  2015.5.8				  	  
****************************************************************************
--%>
<%@ page language="java" pageEncoding="GBK" %>
<%@ page import="com.sp.indiv.bi.interf.BiPolicyRelationFGTask"%>
<%@ page import="com.sp.utiall.blsvr.BLPrpDcode"%>
<%@ page import="com.sp.utility.database.DbPool" %>
<%@ page import="com.sp.utility.error.UserException"%>
<%@ page import="com.sp.utility.SysConfig"%>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.logging.Log" %>
<%@ page errorPage="/UIErrorPage"%>
<%
	String strOperateDate = request.getParameter("operateDate");
	String taskFlg = request.getParameter("taskFlg");
	
	BiPolicyRelationFGTask biPolicyRelationFGTask = new BiPolicyRelationFGTask();
	biPolicyRelationFGTask.TASKFLG = "1";		
	Log logger = biPolicyRelationFGTask.logger;	

	
	if("1".equals(taskFlg))
	{
		int errorNum = 0;	
		int comCodeNum = 0;	
		DbPool dbPool = new DbPool();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			
			dbPool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
			
			StringBuffer sbComcode = new StringBuffer(150);
			sbComcode.append("select comcode ");
			sbComcode.append("from BIRelationError ");
			sbComcode.append("where operatedate = to_date('" +strOperateDate+ "','yyyy-MM-dd') ");
			sbComcode.append("group by comcode order by comcode");
			pstmt = dbPool.prepareStatement(sbComcode.toString());
			rs = pstmt.executeQuery();
			List<String[]> comCodeList = new ArrayList<String[]>();
			while (rs.next()) {
				String[] comcode = new String[1];
				comcode[0] = rs.getString("comcode");	
				comCodeList.add(comcode);
			}
			comCodeNum = comCodeList.size();	
			if(comCodeNum == 0)
			{
%>
				<%=strOperateDate%>����ҵXXXXXƽ̨����û�й���ʧ�ܵ���Ϣ�����貹����
				<input class="button" type=button name=buttonClose alt=" �� �� " value="�� ��"  onclick="javascript:window.close()">
<%
				return;
			}
			else
			{
				logger.info("��ǿ����ҵXX������ϵʧ�ܵ���Ϣ����->��ʼ");
				
				for(int i=0;i<comCodeNum;i++)
				{
					try{
						
						StringBuffer sbPolicy = new StringBuffer(150);
						sbPolicy.append("select * ");
						sbPolicy.append("from BIRelationError ");
						sbPolicy.append("where operatedate = to_date('" +strOperateDate+ "','yyyy-MM-dd') and comcode = '"+comCodeList.get(i)[0]+"' ");
						sbPolicy.append("order by comcode");
						pstmt = dbPool.prepareStatement(sbPolicy.toString());
						rs = pstmt.executeQuery();
						List<String[]> policyList = new ArrayList<String[]>();
						while (rs.next()) {
							String[] policy = new String[4];
							policy[0] = rs.getString("caconfirmsequenceno");	
							policy[1] = rs.getString("capolicyno");				
							policy[2] = rs.getString("ciconfirmsequenceno");	
							policy[3] = rs.getString("cipolicyno");				
							policyList.add(policy);
						}
						
						
						biPolicyRelationFGTask.upLoad(policyList,comCodeList.get(i)[0],strOperateDate);
					}
					catch(SQLException e){
						String comCName = new BLPrpDcode().translateCode("AssessArea",comCodeList.get(i)[0],true);
						logger.error("BiPolicyRelationFGTask "+comCName+"��ҵXXXXXƽ̨����ʱ���ݿ�����쳣������", e);
%>
						<%=comCName%>��ҵXXXXXƽ̨�������ݿ�����쳣��<br>
<%
						errorNum++;
						continue;
					}
					catch(Exception e){
						String comCName = new BLPrpDcode().translateCode("AssessArea",comCodeList.get(i)[0],true);
						logger.error("BiPolicyRelationFGTask "+comCName+"��ҵXXXXXƽ̨���������쳣������", e);
%>
						<%=comCName%>��ҵXXXXXƽ̨���������쳣��<br>
<%
						errorNum++;
						continue;
					}
				}
				logger.info("��ǿ����ҵXX������ϵʧ�ܵ���Ϣ����->����");
			}
		}
		catch(Exception e){
			throw e;
		}
		finally{
			if(pstmt != null){
				pstmt.close();
				pstmt = null;
			}
			if(rs != null){
				rs.close();
				rs = null;
			}
			dbPool.close();
		}
		if(errorNum == 0)
		{
%>
			<%=strOperateDate%>��ʧ����Ϣ��������ɣ�
<%
		}
		else if(errorNum < comCodeNum)
		{
%>
			<%=strOperateDate%>������������������ɣ�
<%
		}
	}
	
	else if("2".equals(taskFlg))
	{
		biPolicyRelationFGTask.OPERATEDATE = strOperateDate;
		try{
			logger.info("��ǿ����ҵXX������ϵ�ϴ�->��ʼ");
			biPolicyRelationFGTask.doTask();
			logger.info("��ǿ����ҵXX������ϵ�ϴ�->����");
		}
		catch(Exception e){
			throw e;
		}
%>
		<%=strOperateDate%>��ȫ��XX��Ϣ�ϴ�����ɣ�
<%
	}
%>
<br>��־��Ϣ����̨��<br>
<input class="button" type=button name=buttonClose alt=" �� �� " value="�� ��"  onclick="javascript:window.close()">
