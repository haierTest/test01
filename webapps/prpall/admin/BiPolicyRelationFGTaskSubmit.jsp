<%--
****************************************************************************
* Description:		交强和商业XX关联关系上传(详细处理)
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
				<%=strOperateDate%>的商业XXXXX平台返回没有关联失败的信息，无需补传！
				<input class="button" type=button name=buttonClose alt=" 关 闭 " value="关 闭"  onclick="javascript:window.close()">
<%
				return;
			}
			else
			{
				logger.info("交强和商业XX关联关系失败的信息补传->开始");
				
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
						logger.error("BiPolicyRelationFGTask "+comCName+"商业XXXXX平台交互时数据库出现异常！！！", e);
%>
						<%=comCName%>商业XXXXX平台交互数据库出现异常！<br>
<%
						errorNum++;
						continue;
					}
					catch(Exception e){
						String comCName = new BLPrpDcode().translateCode("AssessArea",comCodeList.get(i)[0],true);
						logger.error("BiPolicyRelationFGTask "+comCName+"商业XXXXX平台交互出现异常！！！", e);
%>
						<%=comCName%>商业XXXXX平台交互出现异常！<br>
<%
						errorNum++;
						continue;
					}
				}
				logger.info("交强和商业XX关联关系失败的信息补传->结束");
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
			<%=strOperateDate%>的失败信息补传已完成！
<%
		}
		else if(errorNum < comCodeNum)
		{
%>
			<%=strOperateDate%>的其它机构补传已完成！
<%
		}
	}
	
	else if("2".equals(taskFlg))
	{
		biPolicyRelationFGTask.OPERATEDATE = strOperateDate;
		try{
			logger.info("交强和商业XX关联关系上传->开始");
			biPolicyRelationFGTask.doTask();
			logger.info("交强和商业XX关联关系上传->结束");
		}
		catch(Exception e){
			throw e;
		}
%>
		<%=strOperateDate%>的全体XX信息上传已完成！
<%
	}
%>
<br>日志信息见后台！<br>
<input class="button" type=button name=buttonClose alt=" 关 闭 " value="关 闭"  onclick="javascript:window.close()">
