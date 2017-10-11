<%@page import = "com.sp.utility.error.UserException"%>
<%@page import = "com.sp.utility.string.*"%>
<%@page import = "com.sp.utility.*"%>
<%@page import = "com.sp.utility.database.*"%>
<%@page import = "com.sp.utility.log.*"%>
<%@page import = "java.sql.SQLException"%>
<%@page import = "java.sql.ResultSet"%>
<%@page import = "java.util.*"%>
<%@page import = "com.sp.account.blsvr.*"%>
<%@page import = "com.sp.account.dbsvr.*"%>
<%@page import = "com.sp.account.schema.*"%>
<%@page import = "com.sp.utiall.blsvr.BLPrpDcompany"%>
<%@page import="com.sp.prpall.blsvr.jf.BLPrpQueryPaymentService"%>
<%
  String separator = java.io.File.separator;
  String piccallwebHome = System.getProperty("ddccallwebHome");

  String configPath = piccallwebHome + separator + "config";
  String sysConstConfig         = configPath + separator + "SysConstConfig.xml";
  String userExceptionConfig    = configPath + separator + "UserExceptionConfig.xml";

  try
  {
    
    Log.init("datacheck.log","datacheck.log",true);
    if(SysConst.isInited()==false)
      SysConst.init(sysConstConfig,false);
    if(UserException.isInited()==false)
      UserException.init(userExceptionConfig,false);
  }
  catch (Exception e)
  {
    out.println("��ʼ�����ó���,����ϵͳ����Ա��ϵ.  The data returned with the error is "
        + e.getMessage());
  }

  
  ArrayList arrSQL = new ArrayList();
  arrSQL.add(new String[]{
    "----0.���PrpTfee.Currency*Ϊ��",
    "select proposalno from prptfee where currency is null or currency1 is null or currency2 is null"});
  arrSQL.add(new String[]{
    "----1.���PrpTfee.XX�ͻ���ֵ��",
    "select proposalno from prptfee where (abs(premium*exchangerate1 - premium1)>=0.02)" +
    " or currency=currency1 and exchangerate1!=1"});
  arrSQL.add(new String[]{
    "----2.���PrpTmain��PrpTfee",
    "select a.proposalno"+
    " from prptmain a,(select proposalno,currency2,sum(amount2) amount2,sum(premium2) premium2 from prptfee"+
    " group by proposalno,currency2) b where substr(a.othflag,4,1)<>2 and a.proposalno = b.proposalno"+
    " and abs(a.sumpremium-b.premium2)>=0.02"});
  arrSQL.add(new String[]{
    "----3.���PrpTfee��PrpTitemKind",
    "select a.proposalno"+
    " from prptfee a,prptmain c,(select proposalno,currency,sum(premium) premium from prptitemkind"+
    " group by proposalno,currency) b where substr(c.othflag,4,1)<>2 and a.proposalno = b.proposalno and a.proposalno = c.proposalno"+
    " and a.currency = b.currency and abs(a.premium-b.premium)>=0.02"});
  arrSQL.add(new String[]{
    "----4.���PrpTfee��PrpTplan",
    "select a.proposalno"+
    " from prptfee a,(select proposalno,currency,sum(planfee) premium from prptplan"+
    " group by proposalno,currency) b,prptmain c where substr(c.othflag,4,1)<>2 and a.proposalno = b.proposalno  and a.proposalno = c.proposalno"+
    " and a.premium1!=0 and a.currency1 = b.currency and abs(a.premium1-b.premium)>=0.01"});
  arrSQL.add(new String[]{
    "----5.���PrpCfee�ֶε�һ����",
    "select policyno from prpcfee where currency is null or currency1 is null or currency2 is null"});
  arrSQL.add(new String[]{
    "----6.���PrpCfee�ֶε�һ����",
    "select policyno"+
    " from prpcfee where (abs(premium*exchangerate1 - premium1)>=0.01)"+
    " or currency=currency1 and exchangerate1!=1"});
  arrSQL.add(new String[]{
    "----7.���PrpCfee��PrpCitemKind",
    "select a.policyno"+
    " from prpcfee a,(select policyno,currency,sum(premium) premium from prpcitemkind"+
    " group by policyno,currency) b"+
    " where a.policyno = b.policyno and a.currency = b.currency and abs(a.premium-b.premium)>0.02"});
  arrSQL.add(new String[]{
    "----8.���PrpCmain��PrpCfee",
    "select a.policyno"+
    " from prpcmain a,(select policyno,currency2,sum(amount2) amount2,sum(premium2) premium2 from prpcfee"+
    " group by policyno,currency2) b"+
    " where a. policyno = b. policyno and abs(a.sumpremium-b.premium2)>0.02"});
  arrSQL.add(new String[]{
    "----9.���PrpCfee��PrpCplan",
    "select a.policyno"+
    " from prpcfee a,(select policyno,currency,sum(planfee) premium from prpcplan"+
    " group by policyno,currency) b"+
    " where a.policyno=b.policyno and a.premium1!=0 and a.currency1 = b.currency and abs(a.premium1-b.premium)>=0.01"});
  arrSQL.add(new String[]{
    "----10.���PrpCfeeOrigin����һ����",
    "select policyno from prpcfeeorigin where currency is null or currency1 is null or currency2 is null"});
  arrSQL.add(new String[]{
    "----11.���PrpCfeeOrigin����������",
    "select policyno from prpcfeeorigin where currency is null or currency1 is null or currency2 is null"});
  arrSQL.add(new String[]{
    "----12.���PrpCfeeOriginXXһ����",
    "select a.policyno from prpcfeeorigin a where abs(a.premium*a.exchangerate1 - a.premium1)>0.02"});
  arrSQL.add(new String[]{
    "----13.���PrpCfeeOrigin��PrpCitemKindOriginXXһ����",
    "select a.policyno from prpcfeeorigin a,"+
    " (select policyno,currency,sum(premium) premium from prpcitemkindorigin group by policyno,currency) b"+
    " where a.policyno = b.policyno and a.currency = b.currency and abs(a.premium-b.premium)>0.02"});
  arrSQL.add(new String[]{
    "----14.���PrpCmainOrigin��PrpCfeeOriginXXһ����",
    "select a.policyno from prpcmainorigin a,"+
    " (select policyno,currency2,sum(amount2) amount2,sum(premium2) premium2 from prpcfeeorigin"+
    " group by policyno,currency2) b"+
    " where a. policyno = b. policyno and abs(a.sumpremium-b.premium2)>0.02"});
  arrSQL.add(new String[]{
    "----15.���PrpPfee�ֶεı���������",
    "select endorseno from prppfee where currency is null or currency1 is null or currency2 is null"});
  arrSQL.add(new String[]{
    "----16.���PrpPfee�ֶεı���������",
    "select endorseno from prppfee where currency is null or currency1 is null or currency2 is null"});
  arrSQL.add(new String[]{
    "----17.���PrpPfee�ֶε�XXһ����",
    "select endorseno from prppfee a where abs(a.premium*a.exchangerate1 - a.premium1)>0.02"});
  arrSQL.add(new String[]{
    "----18.���PrpPfee��PrpPitemKindXXһ����",
    "select a.endorseno from prppfee a,"+
    " (select endorseno,currency,sum(chgpremium) chgpremium from prppitemkind group by endorseno,currency) b"+
    " where a. endorseno = b. endorseno and a.currency = b.currency and abs(a.chgpremium-b.chgpremium)>0.02"});
  arrSQL.add(new String[]{
    "----19.���PrpLclaim��PrpLclaimLoss������һ����",
    "select a.claimno from prplclaim a,"+
    " (select claimno,currency,sum(sumclaim) sumclaim from prplclaimfee group by claimno,currency) b"+
    " where a.claimno = b.claimno and abs(a.sumclaim-b.sumclaim)<>0"});
  arrSQL.add(new String[]{
    "----20.���PrpLclaim��PrpLcompensate�Ѿ����һ����",
    "select a.claimno from prplclaim a,"+
    " (select claimno,currency,sum(sumdutypaid) sumpaid from prplcompensate where underwriteflag in ('1','3') group by claimno,currency) b"+
    " where a.claimno = b.claimno and abs(a.sumpaid-b.sumpaid)<>0"});
  
  
  
  
  arrSQL.add(new String[]{
    "----21.���PrpLcompensate�������û����XXXXX",
    "select compensateno from prplcompensate where underwriteflag in ('1','3') and underwriteenddate is not null"+
    " and not exists (select 'x' from prpjplanfee where certitype='C' and certino=prplcompensate.compensateno)"});
  arrSQL.add(new String[]{
    "----22.�����������ı������ϼ��Ƿ�������ֱұ�ϼƱ��е������ϼ�",
    "select a.compensateno from prplcompensate a,"+
    " (select compensateno,currency,sum(sumpaid) sumpaid from prplcfee group by compensateno,currency ) b"+
    " where a.compensateno = b.compensateno and abs(a.sumthispaid - b.sumpaid)<>0"});
  
  
  
  
  
  arrSQL.add(new String[]{
    "----23.���û�������м�ɱ����ݵ�XX",
    "select policyno from prpcmainorigin where disrate1!=0 and riskcode!='YAB0'"+
    " and not exists (select 'x' from prpmiddlecost where certino=prpcmainorigin.policyno)"});
  arrSQL.add(new String[]{
    "----24.���ҵ���XXXXX����һ����",
    "select a.policyno from prpcplan a,prpjplanfee b where a.policyno=b.certino and a.serialno=b.serialno"+
    " and b.certitype='P' and a.planfee!=b.planfee"});
  arrSQL.add(new String[]{
    "----25.���PrpCmainOrigin�����Ƿ�ȱʧ",
    "select policyno from prpcmain where underwriteflag='1' "+
    "and not exists (select 'x' from prpcmainorigin where policyno=prpcmain.policyno)"});
  arrSQL.add(new String[]{
    "----26.���ҵ��XXXX��XXXXXXXXX��һ�£�δ���������Ѹ���XX��",
    "select a.policyno from "+
    "(select policyno,sum(premium1) as premium1 from prpcfee group by policyno ) a,"+
    "(select policyno,sum(planfee) as planfee from prpjplanfee where certitype in ('P','E')"+
    " and payrefreason not in ('R81','R82','P81','P82','P40','P41') group by policyno) b"+
    " where a.policyno=b.policyno and a.premium1!=b.planfee"});
  arrSQL.add(new String[]{
    "----27.���PrpJplanFee�����Ƿ�ȱʧ",
    "select policyno from prpcmain where underwriteflag='1' "+
    "and not exists (select 'x' from prpjplanfee where certitype='P' and policyno=prpcmain.policyno)"});
  arrSQL.add(new String[]{
    "----28.���ͬһ��XX��ת����XX�����(CmainOrigin)",
    "select proposalno from prpcmainorigin where proposalno is not null group by proposalno having count(*) >1"});
  arrSQL.add(new String[]{
    "----29.���ͬһ��XX��ת����XX�����(Cmain)",
    "select proposalno from prpcmain where proposalno is not null and substr(OthFlag,4,1)<>'1' group by proposalno having count(*) >1"
    });
  arrSQL.add(new String[]{
    "----30.���XX��һ�µ����(XXXXXXX)",
    "select a.policyno,a.sumamount,b.amount1 from prpcmainorigin a ,prpcfeeorigin b "+
    " where a.policyno=b.policyno and a.riskcode like '05%' and a.sumamount !=b.amount1"
    });
  arrSQL.add(new String[]{
    "----31.���XX��һ�µ����(XXXXX����)",
    "select a.endorseno,a.chgamount,b.chgamount1 from prppmain a ,prppfee b "+
    " where a.endorseno=b.endorseno and a.riskcode like '05%' and a.chgamount !=b.chgamount1"
    });
   

%>
  <title>���������Զ����</title>
  <table style="font-size: 9pt; width: 760; border-collapse:collapse"  border="1" align="center" cellspacing="0" bordercolor="#000000">
    <tr>
      <td width="150" align=center><b>�����Ŀ</b></td>
      <td width="400" align=center><b>���SQL</b></td>
      <td width="60" align=center><b>��鵥��</b></td>
      <td width="150" align=center><b>�����</b></td>
    </tr>

<%
  
  
  out.println("----"+new ChgDate().getCurrentTime("yyyy-MM-dd HH:mm:ss")+" begin to check data automatically----");
  DbPool dbpool = new DbPool();
  
  DbPool paymentdbPool = new DbPool();
  String strSwitch = BLPrpQueryPaymentService.queryMirrorSwitch();
  try
  {
  if("1".equals(strSwitch)){
  	BLPrpQueryPaymentService.querySwitch(paymentdbPool,1);
  }else{
  	paymentdbPool.open(SysConfig.CONST_DDCCDATASOURCE);
  }
    dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  
    String strSQL = "";
    String strMessage = "";
    String strErr = "";
    int intCount = 0;
    ResultSet rs = null;
    for(int i=0; i<arrSQL.size(); i++)
    {
      intCount = 0;
      strMessage = ((String[])arrSQL.get(i))[0];
      strSQL = ((String[])arrSQL.get(i))[1];
      strErr = "";
      
	  if("1".equals(strSwitch)&&(i==21||i==24||i==26||i==27)){
	  	rs = paymentdbPool.query(strSQL);
	  }else{
      	rs = dbpool.query(strSQL);
      }
      
      while(rs.next())
      {
        
        intCount ++;
        strErr += rs.getString(1)+"<br>";
      }
      rs.close();

      if(strErr.equals(""))
        strErr = "OK";
      
      
        out.println("<tr><td>"+strMessage+"</td>");
        out.println("<td>"+strSQL+"</td>");
        out.println("<td align=center>"+intCount+"</td>");
        
        out.println("<td>"+strErr+"</td></tr>");
      
    }
  }
  catch(Exception e)
  {
    out.println("*******������Ϣ��"+e.getMessage()+"<br>");
    e.printStackTrace();
  }
  finally
  {
    dbpool.close();
    paymentdbPool.close();
  }
  
  out.println("----"+new ChgDate().getCurrentTime("yyyy-MM-dd HH:mm:ss")+" end to check data automatically----\n\n");
%>

  </table>
