
<%@page import="com.sp.prpall.pubfun.InternetPrpallProperties"%>
  <%--****************************************************************************************
 * DESC       ����ҳ,��ʼ������
* Author     : X
 * CREATEDATE ��2002-07-12
 * MODIFYLIST ��   Name       Date            Reason/Contents
 *
 *****************************************************************************************--%>

<%@ page import="com.sp.utility.Transfer"%>
<%@ page import="com.sp.utility.SysConfig"%>
<%@ page import="com.sp.prpall.ui.UIPrpDclauseKind"%>
<%@ page import="com.sp.prpall.ui.UIPrpDclauseKind1"%>
<%@ page import="com.sp.prpall.ui.UIPrpDclauseKind2"%>
<%@ page import="com.sp.prpall.ui.UIPrpDclauseKind3"%>
<%@ page import="com.sp.prpall.ui.UIRefCache"%>
<%@ page import="com.sp.utiall.blsvr.BLPrpDcode"%>
<%--add by liuweichang 20141110 begin reason : �ϲ�¼���Ż�--%>
<%@page import="com.sp.platform.bl.facade.BLPrpDriskConfigFacade"%>
<%--add by liuweichang 20141110 end reason : �ϲ�¼���Ż�--%>
<%--add by liuweichang 20141110 begin reason : �ϲ�¼���Ż�--%>
<%@page import="com.sp.prpall.pubfun.PubTools"%>
<%--add by liuweichang 20141110 end reason : �ϲ�¼���Ż�--%>

<%@ page errorPage="/UIErrorPage"%>

<%
  String separator = java.io.File.separator;
  String ddccallwebHome = System.getProperty("ddccallwebHome");

  String configPath = ddccallwebHome + separator + "config";
  String sysConstConfig         = configPath + separator + "SysConstConfig.xml";
  String userExceptionConfig    = configPath + separator + "UserExceptionConfig.xml";
  String UICentralControlConfig = configPath + separator + "UICentralControlConfig.xml";

    
	UIPrpDclauseKind.init(false);
    UIPrpDclauseKind1.init(false);
    UIPrpDclauseKind2.init(false);
    UIPrpDclauseKind3.init(false);
	
	Transfer.clear();
	
	
	BLPrpDcode.clear(); 
	
	
	UIRefCache.clear();
	
	SysConfig.clear(); 
	
	InternetPrpallProperties.clear();
	
	
	
	BLPrpDriskConfigFacade.clear();
	
	
	
	PubTools.clear();
	
	
	out.println("��ջ���ɹ�");
%>

