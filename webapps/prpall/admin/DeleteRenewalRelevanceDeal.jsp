<%@page errorPage="/UIErrorPage"%>
<%@page contentType="text/xml;charset=GBK"%>
<%@page import="com.sp.utility.database.DbPool"%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCrenewal" %>
<%@page import="com.sp.prpall.dbsvr.cb.DBPrpCmain" %>
<%@page import="com.sp.prpall.dbsvr.cb.DBPrpCrenewalPolicy" %>
<%
  
  String errorMsg = "";
  DbPool dbPool = new DbPool();
  BLPrpCrenewal blPrpCrenewal = new BLPrpCrenewal();
  DBPrpCmain dbPrpCmain = new DBPrpCmain();
  DBPrpCrenewalPolicy dbPrpCrenewalPolicy = new DBPrpCrenewalPolicy();
  try{
	dbPool.open(SysConfig.CONST_DDCCDATASOURCE);
	dbPool.beginTransaction();
	String strPolicyno = request.getParameter("policyno");
	if(strPolicyno==null || "".equals(strPolicyno)){
		throw new Exception("!");
	}
	String strDeleteFlag = request.getParameter("deleteFlag");
	if(strDeleteFlag==null || "".equals(strDeleteFlag)){
		throw new Exception("ɾ����ʽ����Ϊ�գ�");
	}
	if("1".equals(strDeleteFlag)){
		String strWhere = " policyno='" + strPolicyno + "' ";
		blPrpCrenewal.query(dbPool, strWhere);
		if(blPrpCrenewal.getSize()<1){
			throw new Exception("¼���XX������XXXXX��Ϣ��");
		}
		blPrpCrenewal.cancel(dbPool, strPolicyno);
	}else if("2".equals(strDeleteFlag)){
		int result = dbPrpCmain.getInfo(dbPool, strPolicyno);
		if(result==0){
	   		String othFlag = dbPrpCmain.getOthFlag();
	   		dbPrpCmain.setOthFlag(othFlag.substring(0, 1)+"0"+othFlag.substring(2));
	    	dbPrpCmain.update(dbPool);
	   	}else if (result==100){
	   		throw new Exception("¼���XX���޻�����Ϣ��");
	   	}
	}else if("3".equals(strDeleteFlag)){
		int result = dbPrpCrenewalPolicy.getInfo(dbPool, strPolicyno);
		if(result==0){
	   		String othFlag = dbPrpCrenewalPolicy.getOthFlag();
	   		dbPrpCrenewalPolicy.setOthFlag(othFlag.substring(0, 1)+"0"+othFlag.substring(2));
	    	dbPrpCrenewalPolicy.update(dbPool,strPolicyno);
	   	}else if (result==100){
	   		throw new Exception("¼���XX������XXXXX�����Ϣ��");
	   	}
	}else{
		throw new Exception("ɾ����ʽ��������");
	}
    dbPool.commitTransaction();
  }catch(Exception e){
	  dbPool.rollbackTransaction();
	  e.printStackTrace();
	  errorMsg = e.getMessage();
  }finally{
	  dbPool.close();
  }
  if("".equals(errorMsg)){
	  errorMsg = "���³ɹ���";
  }
  out.print("<?xml version=\"1.0\" encoding=\"GBK\"?>"); 
  out.print("<root>");
  out.print(errorMsg);
  out.print("</root>");
%>