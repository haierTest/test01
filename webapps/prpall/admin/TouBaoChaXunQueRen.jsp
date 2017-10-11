<%@ page import="com.sp.utility.*"%>
<%@ page import="com.sp.utility.error.*"%>
<%@ page import="com.sp.prpall.ui.UICentralControl"%>
<%@ page import="com.sp.prpall.ui.*"%>
<%@ page import="com.sp.indiv.ci.interf.*"%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.utility.error.*"%>
<%@page import="com.sp.utiall.dbsvr.*"%>
<%@page import="com.sp.utiall.dbsvr.*"%>
<%@page import="com.sp.prpall.blsvr.tb.*"%>
<%@page import="com.sp.prpall.blsvr.cb.*"%>
<%@page import="com.sp.prpall.blsvr.pg.*"%>
<%@page import="com.sp.prpall.blsvr.misc.*"%>
<%@page import="com.sp.prpall.dbsvr.misc.*"%>
<%@page import="com.sp.prpall.pubfun.*"%>
<%@page import="com.sp.prpall.schema.*"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.indiv.sh.util.*"%>
<%@page import="com.sp.indiv.sh.blsvr.*"%>
<%@page import="com.sp.indiv.sh.schema.*"%>
<%@page import="com.sp.indiv.ci.blsvr.*"%>
<%@page import="com.sp.indiv.ci.dbsvr.*"%>
<%@page import="com.sp.indiv.ci.interf.*"%>
<%@page import="com.sp.indiv.ci.schema.*"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.sp.utility.database.DbPool"%>
<%@page import="com.sp.sysframework.common.util.StringUtils"%>
<%@page import="com.sp.utility.SysConst"%>
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="org.apache.commons.logging.LogFactory"%>

<%@ page errorPage="/UIErrorPage"%>
<%
	Log logger = LogFactory.getLog("RunTime");
    String strPolicyNo    = (String)request.getParameter("strPolicyNo");
    String strProposalNo  = (String)request.getParameter("strProposalNo");
    String strType        = (String)request.getParameter("strType");
    
    String b = "";
    String c = "";
    
    DbPool dbpool = new DbPool();
    dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
    
    try
      {
        dbpool.beginTransaction();
        
        if(strType.equals("XX查询"))
        {
           BLProposal blProposal = new BLProposal();
           blProposal.getData(strProposalNo);
           ProposalQueryEncoder proposalQueryEncoder = new ProposalQueryEncoder();
        
           String requestXML = proposalQueryEncoder.encode(blProposal);
           String response1XML = EbaoProxy.getInstance().request(requestXML, blProposal.getBLPrpTmain().getArr(0).getComCode());
        
			
			logger.info("====XXXXX上海强三平台测试-XX发给ebao平台的XML串：" + requestXML);
			logger.info("====XXXXX上海强三平台测试-XXebao平台返回的XML串：" + response1XML);
			

           response1XML = StringUtils.replace(response1XML, "GBK", "GB2312");
           ProposalQueryDecoder decoder = new ProposalQueryDecoder();
           decoder.decode(dbpool, blProposal,response1XML, "U", strProposalNo);
        
           
        
           
        }
        else if(strType.equals("XX确认"))
        {
          
	      	String context = "";
	      	String response1 = "";
	      	String strPolicySql = "";
	      	String strEndorseSql = "";
	      	String iRiskCode = "";
	      	String iComCode = "";
	      	PrpCmainSchema prpCmainSchema = new PrpCmainSchema();
	      	com.sp.prpall.dbsvr.cb.DBPrpCmain dbPrpCmain = new com.sp.prpall.dbsvr.cb.DBPrpCmain();
	      	com.sp.prpall.blsvr.cb.BLPrpCmain blPrpCmain = new com.sp.prpall.blsvr.cb.BLPrpCmain();
	      	BLPolicy blPolicy = new BLPolicy();
          blPolicy.getData(strPolicyNo);
	        boolean utiPowerFlag = true;
	        iRiskCode = blPolicy.getBLPrpCmain().getArr(0).getRiskCode();
	        iComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();
	        UtiPower utiPower = new UtiPower();
	        utiPowerFlag = utiPower.checkCIInsure(iRiskCode, iComCode);
	        if (utiPowerFlag) 
	        {
	          PolicyValidEncoder policyValidEncoder = new PolicyValidEncoder();
	          PolicyValidDecoder policyValidDecoder = new PolicyValidDecoder();
	          context = policyValidEncoder.encode(dbpool, blPolicy);
	          if (context != null) 
	          {
				 
				 logger.info("------ruquest--=" + context);
				 

	             response1 = EbaoProxy.getInstance().request(context,	iComCode);
	             response1 = StringUtils.replace(response1, "GBK", "GB2312");
				 
				 logger.info("------response1--=" + response1);
				 

	             policyValidDecoder.decode(dbpool, blPolicy, response1);
               b=response1;
               c=context;
            }
          }
        }
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally
      {
        dbpool.close();
      }
%>
    发送数据：
    <%=c%>
  接收数据：
  <%=b%>