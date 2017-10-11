<%--/*****************************************************************************
 * DESC       �����������޸����У��XX����ҳ��
* Author     : X
 * CREATEDATE ��2004-02-13
 * MODIFYLIST ��   Name       Date            Reason/Contents
 *          ------------------------------------------------------
 ****************************************************************************/
--%> 
 
<%-- ������ --%>
<%@page errorPage="/UIErrorPage"%> 

<%@page import="java.text.*"%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page import="com.sp.utility.error.UserException"%>
<%@page import="com.sp.prpall.dbsvr.tb.*"%>
<%@page import="com.sp.prpall.dbsvr.cb.DBPrpCmain"%>
<%@page import="com.sp.prpall.schema.*"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.schema.*"%>

<%
  
  BLPrpExtraBValid   blPrpExtraBValid  = new BLPrpExtraBValid();
  BLValid blValid     = new BLValid();
  BLDemand  blDemand  = new BLDemand();

  PrpExtraBValidSchema   prpExtraBValidSchema               = null;

  DBPrpTmain       dbPrpTmain   = new DBPrpTmain();
  PrpTmainSchema prpTmainSchema = null;
  
  DBPrpCmain dbPrpCmain = null;

  String strValidTime   = "";
  String strValidNoOld  = "";
  String strDemandNoOld = "";
  String strRenewalFlag	= "";		
  int    intCount       = 0;
  
  
  String strEditType    = request.getParameter("EDITTYPE");
  String strBizType     = request.getParameter("BIZTYPE");
  String strDataNo      = request.getParameter("DataNo");
  String strValidNo     = request.getParameter("ValidNo");
  String strHandlerCode = (String)session.getAttribute("UserCode");
  
  
  String isql = "";

  
  blPrpExtraBValid.getInfo(strValidNo);

  if(blPrpExtraBValid.getSize() == 0)
  {
    throw new UserException(-98,-1003,"UIPrpslPoliDaaThirdInputIni","ȷ�������");
  }
  else
  {
    prpExtraBValidSchema = blPrpExtraBValid.getArr(0);
    
    strValidTime = prpExtraBValidSchema.getValidTime();
  }

  
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  java.util.Date       validDate    = simpleDateFormat.parse(strValidTime);
  java.util.Date       currentDate  = new java.util.Date();
  
  long Margin = (currentDate.getTime()-validDate.getTime())/3600000;
  if(Margin > 72)
  {
    throw new UserException(-98,-1003,"","ȷ����Ϣ����ʱ�䳬��72Сʱ��������XX��ѯ");
  }
  
  
  if(strEditType.equals(SysConfig.getProperty("EDITTYPE_UPDATE")))
  {
    dbPrpTmain.getInfo(strDataNo);
    String     ProposalNo_CK = dbPrpTmain.getProposalNo();
    String   OperatorCode_CK = dbPrpTmain.getOperatorCode();
    String   ApproverCode_CK = dbPrpTmain.getApproverCode();
    String UnderWriteFlag_CK = dbPrpTmain.getUnderWriteFlag();

    if( ProposalNo_CK == null || ProposalNo_CK.equals("") )
    {
      throw new UserException(-98,-1003,"UIPropsalCheck.jsp","��XX�������ڣ�");
    }
    else
    {
      if( !OperatorCode_CK.equals(strHandlerCode) )
      {
        throw new UserException(-98,-1003,"UIPropsalCheck.jsp","��XX���Ǳ�����Ա¼�룬������Ա�����޸ģ�");
      }
      else
      {
        if( !UnderWriteFlag_CK.equals("0") && !UnderWriteFlag_CK.equals("2"))
        {
          throw new UserException(-98,-1003,"UIPropsalCheck.jsp","��XX���Ѿ���XXXXXͨ���������XXXXX�����޸ģ�");
        }
        else
        {
          if( UnderWriteFlag_CK.equals("0"))
          {
            if( !ApproverCode_CK.equals(""))
            {
              throw new UserException(-98,-1003,"UIPropsalCheck.jsp","��XX���Ѿ����˲����޸ģ�");
            }
            else
            {
%>
  <jsp:forward page="/0501/tbcbpg/UIPrPoEn0501Input.jsp">
    <jsp:param name="EDITTYPE" value="<%=strEditType%>"/>
    <jsp:param name="BIZTYPE" value="<%=strBizType%>"/>
    <jsp:param name="BizNo" value="<%=strDataNo%>"/>
    <jsp:param name="FromThird" value="YES"/>
    <jsp:param name="DataNo" value="<%=strDataNo%>"/>
    <jsp:param name="ValidNo" value="<%=strValidNo%>"/>
  </jsp:forward>

<%
          }  
          }
        }
      }
    }
  }
  else
  {
    if(strEditType.equals(SysConfig.getProperty("EDITTYPE_RENEWAL")))
    {
      String strCondition = "PolicyNo = '" + strDataNo + "'";
      
      
      dbPrpCmain = new DBPrpCmain();
      intCount = dbPrpCmain.getCount(strCondition);
      if(intCount==0)
      {
        throw new UserException(-98,-998,"UIProposalCheck","XX"+ strDataNo +"������");
      }

      dbPrpCmain.getInfo(strDataNo);
      
      if(!dbPrpCmain.getUnderWriteFlag().equals("1") &&
         !dbPrpCmain.getUnderWriteFlag().equals("3")
        )
      {
        throw new UserException(-98,-998,"UIProposalCheck","XX"+ strDataNo +"��û��XXXXXͨ����������XXXXX");
      }
      
      
      strRenewalFlag = dbPrpCmain.getOthFlag().substring(1,2);
      if(strRenewalFlag.equals("1"))
      {
        throw new UserException(-98,-998,"UIProposalCheck","XX"+ strDataNo +"�Ѿ���XXXXX����������XXXXX");
      }
    }
    if(strEditType.equals(SysConfig.getProperty("EDITTYPE_COPY_PROPOSAL")))
    {
      String strCondition = "ProposalNo = '" + strDataNo + "'";
      
      
      dbPrpTmain = new DBPrpTmain();
      intCount = dbPrpTmain.getCount(strCondition);
      if(intCount==0)
      {
        throw new UserException(-98,-998,"UIProposalCheck","XX��"+ strDataNo +"������");
      }
    }
%>
  <jsp:forward page="/0501/tbcbpg/UIPrPoEn0501Input.jsp">
    <jsp:param name="EDITTYPE" value="<%=strEditType%>"/>
    <jsp:param name="BIZTYPE" value="<%=strBizType%>"/>
    <jsp:param name="BizNo" value="<%=strDataNo%>"/>
    <jsp:param name="FromThird" value="YES"/>
    <jsp:param name="DataNo" value="<%=strDataNo%>"/>
    <jsp:param name="ValidNo" value="<%=strValidNo%>"/>
  </jsp:forward>
<%
  }
%>