<%@page contentType="text/html;charset=GBK"%>

<%@ page import="java.io.*"%>
<%@ page import="com.sp.utility.SysConfig"%>
<%@page import="com.sp.utility.database.DbPool"%>
<%@page import="com.sp.utility.string.Date"%>
<%@page import="com.sp.indiv.ci.blsvr.BLCIInsureValid"%>
<%@page import="com.sp.prpall.pubfun.PubTools"%>
<%@page import="com.sp.prpall.schema.PrpCmainSchema"%>
<%@page import="com.sp.prpall.blsvr.cb.BLPrpCmain"%>
<%@page import="com.sp.prpall.dbsvr.misc.DBPrpTimeRegister"%>
<%@page import="com.sp.prpall.dbsvr.cb.DBPrpCjfcdLogMsg"%>
<%@page import="com.sp.prpall.dbsvr.cb.DBPrpCmain"%>
<%@page import="com.sp.prpall.dbsvr.tb.DBPrpTmain"%>
<%@page import="com.sp.prpall.dbsvr.cb.DBPrpCmainOrigin"%>
<%@page import="com.sp.prpall.dbsvr.pg.DBPrpPmain"%>
<%@page import="com.sp.prpall.dbsvr.cb.DBPrpCPmain"%>
<%@page import="com.sp.prpall.blsvr.pg.BLPrpPmain"%>
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="org.apache.commons.logging.LogFactory"%>
<%
Log logger = LogFactory.getLog("RunTime");
%>
<%!
	private void myLog(String configPath,String param) throws Exception{
		FileWriter in = new FileWriter(configPath,true);
		in.write(param+"\n");
		in.close();
	}
%>

<%!
	/**
	 * 更新历史数据及XXXXX存日志
	 */
	public void echoImmeDate(DbPool dbPool,String iProposalNo,String iPolicyNo,String iPassTime,String configPath)throws Exception{
		DBPrpTmain dbPrpTmain = new DBPrpTmain();
		DBPrpCmain dbPrpCmain = new DBPrpCmain();
		DBPrpCmainOrigin dbPrpCmainOrigin = new DBPrpCmainOrigin();
		BLPrpPmain blPrpPmain = new BLPrpPmain();
		DBPrpCPmain dbPrpCPmain = new DBPrpCPmain();
		DBPrpPmain dbPrpPmain = new DBPrpPmain();
		
		Date ImmeValidEndDate=null;
		String strImmeValidStartDate=null;
		String strImmeValidEndDate=null;
		
		if(dbPrpTmain.getInfo(dbPool,iProposalNo)==0&&"1".equals(dbPrpTmain.getImmeValidFlag())){
			
			if(dbPrpTmain.getComCode().substring(0,2).equals("01")){
				StringBuffer bf=new StringBuffer(20);
				bf.append(iPassTime.substring(0,4));
				bf.append("-");
				bf.append(iPassTime.substring(4, 6));
				bf.append("-");
				bf.append(iPassTime.substring(6,8));
				bf.append(" ");
				bf.append(iPassTime.substring(8,10));
				bf.append(":");
				bf.append(iPassTime.substring(10,12));
				bf.append(":00");   
				strImmeValidStartDate=bf.toString();
				
				ImmeValidEndDate=PubTools.getImmeValidDate(new Date(dbPrpTmain.getStartDate()),	
															Integer.parseInt(dbPrpTmain.getStartHour()), 
															new Date(dbPrpTmain.getEndDate()), 
															Integer.parseInt(dbPrpTmain.getEndHour()),
															new Date(strImmeValidStartDate));
				
				strImmeValidEndDate=PubTools.getDateToFormat(ImmeValidEndDate, "yyyy-MM-dd 23:59:59");
			}else{
				strImmeValidStartDate=PubTools.getDateToFormat(PubTools.getDateAddHours(new Date(iPassTime),1),"yyyy-MM-dd HH:00:00");
				
				ImmeValidEndDate=PubTools.getImmeValidDate(new Date(dbPrpTmain.getStartDate()),	
															Integer.parseInt(dbPrpTmain.getStartHour()), 
															new Date(dbPrpTmain.getEndDate()), 
															Integer.parseInt(dbPrpTmain.getEndHour()),
															new Date(strImmeValidStartDate));
				strImmeValidEndDate=PubTools.getDateToFormat(ImmeValidEndDate, "yyyy-MM-dd HH:mm:ss");
			}

			dbPrpTmain.setImmeValidStartDate(strImmeValidStartDate);      
			dbPrpTmain.setImmeValidEndDate(strImmeValidEndDate); 
			dbPrpTmain.update(dbPool);
		}
		
		if(dbPrpCmain.getInfo(dbPool,iPolicyNo)==0&&"1".equals(dbPrpCmain.getImmeValidFlag())){ 
			dbPrpCmain.setImmeValidStartDate(strImmeValidStartDate);  
			dbPrpCmain.setImmeValidEndDate(strImmeValidEndDate);  
			dbPrpCmain.update(dbPool);
		}
		if(dbPrpCmainOrigin.getInfo(dbPool,iPolicyNo)==0&&"1".equals(dbPrpCmainOrigin.getImmeValidFlag())){
			dbPrpCmainOrigin.setImmeValidStartDate(strImmeValidStartDate);
			dbPrpCmainOrigin.setImmeValidEndDate(strImmeValidEndDate);
			dbPrpCmainOrigin.update(dbPool);
		}
		if(dbPrpCPmain.getInfo(dbPool,iPolicyNo)==0&&"1".equals(dbPrpCPmain.getImmeValidFlag())){
			dbPrpCPmain.setImmeValidStartDate(strImmeValidStartDate);
			dbPrpCPmain.setImmeValidEndDate(strImmeValidEndDate);
			dbPrpCPmain.update(dbPool);
		}
		blPrpPmain.query(dbPool,"policyno='"+iPolicyNo+"'");
		if(blPrpPmain.getSize()>0){
			for(int k=0;k<blPrpPmain.getSize();k++){
				if(dbPrpPmain.getInfo(dbPool,blPrpPmain.getArr(k).getEndorseNo())==0&&"1".equals(dbPrpCmainOrigin.getImmeValidFlag())){
					dbPrpPmain.setImmeValidStartDate(strImmeValidStartDate);
					dbPrpPmain.setImmeValidEndDate(strImmeValidEndDate);
					dbPrpPmain.update(dbPool);
				}
			}
		}
		myLog(configPath,"ProposalNo:"+iProposalNo+",PolicyNo:"+iPolicyNo
				+",ImmeValidStartDate:"+strImmeValidStartDate+",strImmeValidEndDate:"+strImmeValidEndDate);
	}
%>

<%
	String separator = java.io.File.separator;
	
	String configPath = application.getRealPath("/") + separator + "admin" + separator+"ImmeValidHistory.log";
		
	String startFlag= request.getParameter("startFlag");
	
	logger.info(startFlag);
	

	DBPrpCmain dbPrpCmain = new DBPrpCmain();
	int count = dbPrpCmain.getCount(" riskcode='0507' and immevalidflag='1' and immevalidstartdate is null ");
	int forNum = 0 ;
	forNum = count/200+1;
	
	BLPrpCmain blPrpCmain = new BLPrpCmain();
	PrpCmainSchema prpCmainSchema = null;

	String strImmeValidStartDate = null;
	
	String BJImmeValidDate = SysConfig.getProperty("BJ_ImmeValid_DATE");
	
  
	if("1".equals(startFlag)){
		for(int i = 0; i<forNum;i++){
			blPrpCmain.query(" riskcode='0507' and immevalidflag='1' and immevalidstartdate is null and rownum<200 ");
		
			DbPool dbpool = new DbPool();
			try {
				dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
				dbpool.beginTransaction();
				for(int j = 0; j<blPrpCmain.getSize();j++){
					prpCmainSchema = blPrpCmain.getArr(j);
			
					if(!"01".equals(prpCmainSchema.getComCode().substring(0,2))){
						DBPrpCjfcdLogMsg dbPrpCjfcdLogMsg = new DBPrpCjfcdLogMsg();
						if(dbPrpCjfcdLogMsg.getInfo(prpCmainSchema.getPolicyNo()) == 0){
							
							strImmeValidStartDate = dbPrpCjfcdLogMsg.getConfirmDate();
						}else{
							
							DBPrpTimeRegister dbPrpTimeRegister = new DBPrpTimeRegister();
							if(dbPrpTimeRegister.getInfo(prpCmainSchema.getProposalNo(),"1","T") == 0 ){
								strImmeValidStartDate = dbPrpTimeRegister.getPassTime();
							}
						}
					}else{
						
						if(PubTools.compareDate(prpCmainSchema.getOperateDate(),BJImmeValidDate)>=0){
							BLCIInsureValid blCIInsureValid = new BLCIInsureValid();
							blCIInsureValid.query("proposalno = '"+prpCmainSchema.getProposalNo()+"'");
							if(blCIInsureValid.getSize()>0&&blCIInsureValid.getArr(0).getImmeValidFlag().equals("1")){
								strImmeValidStartDate = blCIInsureValid.getArr(0).getImmeValidDate();
							}
						}else{
							
							DBPrpCjfcdLogMsg dbPrpCjfcdLogMsg = new DBPrpCjfcdLogMsg();
							if(dbPrpCjfcdLogMsg.getInfo(prpCmainSchema.getPolicyNo()) == 0){
								strImmeValidStartDate = PubTools.getDateToFormat(new Date(dbPrpCjfcdLogMsg.getConfirmDate()),"yyyyMMddHHmmss");
							}
						}
					}
					
					logger.info("ProposalNo:"+prpCmainSchema.getProposalNo());
					logger.info("PolicyNo:"+prpCmainSchema.getPolicyNo());
					logger.info("ImmeValidStartDate:"+strImmeValidStartDate);
					

					echoImmeDate(dbpool,prpCmainSchema.getProposalNo(),prpCmainSchema.getPolicyNo(),strImmeValidStartDate,configPath);
				}
				dbpool.commitTransaction();
			}catch (Exception e){
				dbpool.rollbackTransaction();
				e.printStackTrace();
				myLog(configPath,e.getMessage());
			}finally{
				dbpool.close();
			}
		}
	}
%>
<html>
<head>
<title>XX即时生效历史数据补数</title>
<script type="text/javascript">
function startSubmit(){
	if(confirm("您确认要对即时生效历史数据补数么？请不要重复提交")){
		fm.startFlag.value='1';
		fm.submit();
		return true;
	}else{
		return false;
	}
}
</script>
</head>
<body>
<form name="fm" action="/prpall/admin/ImmeValidHistory.jsp">
	<input type="hidden" name="startFlag" value="0"/>
	<table align="center" cellpadding="5" cellspacing="1">
		<tr>
			<td>prpcmain表中历史数据有：<%=count %>条</td>
		</tr>
		<tr>
			<td>当前历史程序补数路径：<%=configPath%></td>
		</tr>
		<tr>
			<td><input type="button" value="即时生效补数" onclick="startSubmit()"></td>
		</tr>
	</table>
</form>
</body>
</html>