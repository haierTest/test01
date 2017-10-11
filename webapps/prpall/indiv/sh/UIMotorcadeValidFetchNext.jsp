<%--
****************************************************************************
* DESC       ：法定三者确认请求提交页面
* Author     : X
* CREATEDATE ：2004-03-04
* MODIFYLIST ：   Name       Date            Reason/Contents
****************************************************************************
--%>
<%-- 错误处理 --%>
<%@page errorPage="/UIErrorPage"%>

<%-- 引入bean类部分 --%>
<%@page import="java.util.*"%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.utility.error.*"%>
<%@page import="com.sp.indiv.sh.util.*"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.schema.*"%> 
<%@page import="com.sp.prpall.blsvr.misc.*"%>
<%@page import="com.sp.prpall.blsvr.tb.BLPrpTmain"%>
<%@page import="com.sp.prpall.schema.*"%>    

<%
  
  String[] arrValidNo = request.getParameterValues("CheckedValidNo");
  int carCount = arrValidNo.length;
  Vector vector = new Vector();
  String strBizType = SysConfig.getProperty("BIZTYPE_PROPOSAL");
  String strContinueInputFlag = request.getParameter("ContinueInputFlag");
  String strContractNo = request.getParameter("ContractNo");
  
  int i = 0;
  for(i=0;i<arrValidNo.length ;i++)
  {
    vector.add(arrValidNo[i]);
  }
  
  session.setAttribute("ArrayValidNo",vector);
  if( !strContinueInputFlag.equals("1") )
  {
%>
  <%--新车队录入页面流转--%>
  <jsp:forward page="/0501/tbcb/UIPrpslPoli0501MotorcadeInput.jsp">
    <jsp:param name="BIZTYPE" value="<%=strBizType%>"/>
    <jsp:param name="FromThird" value="YES"/>
    <jsp:param name="CarCount" value="<%=carCount%>"/>
  </jsp:forward>
<%
  }
  else
  {
    String EditType = SysConfig.getProperty("EDITTYPE_NEW");
    BLPrpMotorcade blPrpMotorcade = new BLPrpMotorcade();
    blPrpMotorcade.query("ContractNo='"+ strContractNo +"'",0);

    if(blPrpMotorcade.getSize()==0)
    {
      throw new UserException(-98,-2,"UIMotorcaseValidFetchNext.jsp","无此车队信息");
    }
    if(blPrpMotorcade.getSize()>1)
    {
      throw new UserException(-98,-2,"UIMotorcaseValidFetchNext.jsp","车队信息查询出现异常");
    }
    BLPrpTmain blPrpTmain = new BLPrpTmain();
    blPrpTmain.query("ContractNo='"+ strContractNo +"'",0);
    BLTMotorcade blTMotorcade = blPrpMotorcade.generateBLTMotorcade(blPrpTmain,strContractNo);    
%>
  <%--继续录入车队页面流转--%>
  <jsp:forward page='/0501/tbcbpg/UIPrPoEn0501Input.jsp'>
    <jsp:param name='<%=SysConfig.getProperty("EDITTYPE")%>' value='<%=EditType%>' />
    <jsp:param name='<%=SysConfig.getProperty("BIZTYPE")%>' value='<%=strBizType%>' />
    <jsp:param name='BizNo' value='' />
    <jsp:param name='DataNo' value='' />
    <jsp:param name='ContractNo' value='<%=strContractNo%>' />
    <jsp:param name='AppliCode' value='<%=blTMotorcade.getArr(0).getAppliCode()%>' />
    <jsp:param name='AppliName' value='<%=blTMotorcade.getArr(0).getAppliName()%>' />
    <jsp:param name='AppliAddress' value='<%=blTMotorcade.getArr(0).getAppliAddress()%>' />
    <jsp:param name='MinusFlag' value='<%=blTMotorcade.getArr(0).getMinusFlag()%>' />
    <jsp:param name='PayTimes' value='<%=blTMotorcade.getArr(0).getPayTimes()%>'/>
    <jsp:param name='FromThird' value='YES'/>
    <jsp:param name='ValidNo' value='<%=vector.get(0)%>'/>
  </jsp:forward>
<%
  }
%>