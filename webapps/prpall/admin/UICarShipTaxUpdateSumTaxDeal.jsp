<%@page import="com.sp.indiv.ci.schema.CICarshipTaxDemandSchema"%>
<%@page import="com.sp.prpall.schema.PrpTcarshipTaxSchema"%>
<%@page import="com.sp.prpall.schema.PrpTmainSchema"%>
<%@page import="com.sp.utility.string.ChgData"%>
<%-- 错误处理 --%>
<%@page errorPage="/UIErrorPage"%>
<%@page contentType="text/xml;charset=GBK"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.sp.utility.database.DbPool"%>
<%@page import="com.sp.utility.*"%>
<%@page import="com.sp.prpall.blsvr.tb.*" %>
<%@page import="com.sp.indiv.ci.blsvr.*" %>
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="org.apache.commons.logging.LogFactory"%>

<%-- 公用函数 --%>
<%
  Log logger = LogFactory.getLog("RunTime");
  String errorMsg = "";

  DecimalFormat idecimalFormat = new DecimalFormat("0.00");
  BLPrpTmain blPrpTmain = new BLPrpTmain();
  BLPrpTcarshipTax blPrpTcarshipTax = new BLPrpTcarshipTax();
  BLCICarshipTaxDemand blCICarshipTaxDemand = new BLCICarshipTaxDemand();
  PrpTmainSchema prpTmainSchema = null;
  PrpTcarshipTaxSchema prpTcarshipTaxSchema = null;
  CICarshipTaxDemandSchema ciCarshipTaxDemandSchema = null;

  DbPool dbPool = new DbPool();
  try{
	String strProposalno = request.getParameter("proposalno");
	
	if(strProposalno==null || "".equals(strProposalno)){
		throw new Exception("录入的XX单号为空！");
	}
	String strWhere = " proposalno='" + strProposalno + "' ";
	dbPool.open(SysConfig.CONST_DDCCDATASOURCE);
	dbPool.beginTransaction();
	blPrpTmain.query(dbPool, strWhere);
	if(blPrpTmain.getSize()<1){
		throw new Exception("录入的XX单号不存在！");
	}
	prpTmainSchema = blPrpTmain.getArr(0);
	if(!"0507".equals(prpTmainSchema.getRiskCode())){
		throw new Exception("该XX单不是交强XXXXXXX单！");
	}
	if(!"01".equals(prpTmainSchema.getComCode().substring(0,2))){
		throw new Exception("该XX单不是XXXXX分公司XX单！");
	}
	if(!("1".equals(prpTmainSchema.getUnderWriteFlag())||"3".equals(prpTmainSchema.getUnderWriteFlag()))){
		throw new Exception("该XX单没有核XXXXX通过！");
	}
	blPrpTcarshipTax.query(dbPool, strWhere);
	if(blPrpTcarshipTax.getSize()<1){
		throw new Exception("该XX单没有车船税相关数据！");
	}
	prpTcarshipTaxSchema = blPrpTcarshipTax.getArr(0);
	if(!"0".equals(prpTcarshipTaxSchema.getTaxRelifFlag()) || !"03".equals(prpTcarshipTaxSchema.getTaxFlag())){
		throw new Exception("该XX单不是纳税情况不是\"同意缴税\" 或 平台返回的纳税情况不是\"未完税\"！");
	}
	
	
	
	
	
	
	
	
	
	
	String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
	
	ArrayList iWhereValue=new ArrayList();
	if("1".equals(strSwitch)){
		BLPrpTitemCar blPrpTitemCar = new BLPrpTitemCar();
		blPrpTitemCar.getData(strProposalno);
		strWhere = " DemandNo=?";
		iWhereValue.add(blPrpTitemCar.getArr(0).getDemandNo());
	}
	blCICarshipTaxDemand.query(strWhere,iWhereValue);
	
	
	if(blCICarshipTaxDemand.getSize()<1){
		throw new Exception("该XX单与平台交互的车船税信息不存在或数据已丢失！");
	}
	ciCarshipTaxDemandSchema = blCICarshipTaxDemand.getArr(0);
	double dblTaxActual = Double.parseDouble(ChgData.chgStrZero(ciCarshipTaxDemandSchema.getTaxActual()));
	double dblPreviousPay = Double.parseDouble(ChgData.chgStrZero(ciCarshipTaxDemandSchema.getPreviousPay()));
	double dblLateFee = Double.parseDouble(ChgData.chgStrZero(ciCarshipTaxDemandSchema.getLateFee()));
	double dblSumPayTax = Double.parseDouble(idecimalFormat.format(dblTaxActual + dblPreviousPay + dblLateFee));
	double dblSumPayTax2 = Double.parseDouble(ChgData.chgStrZero(ciCarshipTaxDemandSchema.getSumTax()));
	if(dblSumPayTax2!=dblSumPayTax){
		throw new Exception("该平台车船税总计错误！");
	}
	
	StringBuffer sb = new StringBuffer();
	sb.append(" update prptcarshiptax set SumPayTax='"+dblSumPayTax+"',");
	sb.append(" TaxActual='"+dblTaxActual+"',");
	sb.append(" PreviousPay='"+dblPreviousPay+"',");
	sb.append(" LateFee='"+dblLateFee+"'");
	sb.append(" where proposalno='" + strProposalno + "'");
	
	logger.info("更新prptcarshiptax--sql："+sb.toString());
	

	dbPool.update(sb.toString());
	
    dbPool.commitTransaction();
  }catch(Exception e){
	  dbPool.rollbackTransaction();
	  e.printStackTrace();
	  errorMsg = e.getMessage();
  }finally{
	  dbPool.close();
  }
  if("".equals(errorMsg)){
	  errorMsg = "更新成功！";
  }
  out.print("<?xml version=\"1.0\" encoding=\"GBK\"?>"); 
  out.print("<root>");
  out.print(errorMsg);
  out.print("</root>");
%>