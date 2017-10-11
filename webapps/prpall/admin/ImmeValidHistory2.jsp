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

<%!

Log logger = LogFactory.getLog("RunTime");

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
		
		if(dbPrpCmain.getInfo(dbPool,iPolicyNo)==0){ 
			strImmeValidStartDate=PubTools.getDateToFormat(new Date(dbPrpCmain.getStartDate()), "yyyy-MM-dd 00:00:00");
			strImmeValidEndDate=PubTools.getDateToFormat(new Date(dbPrpCmain.getEndDate()), "yyyy-MM-dd 23:59:59");
			
			dbPrpCmain.setImmeValidStartDate(strImmeValidStartDate);  
			dbPrpCmain.setImmeValidEndDate(strImmeValidEndDate);  
			dbPrpCmain.update(dbPool);
		}
		
		
		if(dbPrpTmain.getInfo(dbPool,iProposalNo)==0){
			
			dbPrpTmain.setImmeValidStartDate(strImmeValidStartDate);      
			dbPrpTmain.setImmeValidEndDate(strImmeValidEndDate); 
			dbPrpTmain.update(dbPool);
		}
		
		if(dbPrpCmainOrigin.getInfo(dbPool,iPolicyNo)==0){
			dbPrpCmainOrigin.setImmeValidStartDate(strImmeValidStartDate);
			dbPrpCmainOrigin.setImmeValidEndDate(strImmeValidEndDate);
			dbPrpCmainOrigin.update(dbPool);
		}
		if(dbPrpCPmain.getInfo(dbPool,iPolicyNo)==0){
			dbPrpCPmain.setImmeValidStartDate(strImmeValidStartDate);
			dbPrpCPmain.setImmeValidEndDate(strImmeValidEndDate);
			dbPrpCPmain.update(dbPool);
		}
		blPrpPmain.query(dbPool,"policyno='"+iPolicyNo+"'");
		if(blPrpPmain.getSize()>0){
			for(int k=0;k<blPrpPmain.getSize();k++){
				if(dbPrpPmain.getInfo(dbPool,blPrpPmain.getArr(k).getEndorseNo())==0){
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
	
	String configPath = application.getRealPath("/") + separator + "admin" + separator+"ImmeValidHistory2.log";
		
	String startFlag= request.getParameter("startFlag");
	
	logger.info(startFlag);
	

	DBPrpCmain dbPrpCmain = new DBPrpCmain();
	int count = 0;
	int forNum = 0 ;
	forNum = count/200+1;
	
	BLPrpCmain blPrpCmain = new BLPrpCmain();
	
	
	PrpCmainSchema prpCmainSchema = null;

	String strImmeValidStartDate = null;
	
	String BJImmeValidDate = SysConfig.getProperty("BJ_ImmeValid_DATE");
	
	java.util.Date date = new java.util.Date(109,6,15);
	java.text.SimpleDateFormat sdf =new java.text.SimpleDateFormat("yyyy-MM-dd");
		
	if("1".equals(startFlag)){
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			for(int i = 0; i<95;i++){
				
				
				logger.info("----------count:::come in"+sdf.format(date));
				

				DBPrpCmain dbPrpCmain1 = new DBPrpCmain();
				java.util.Vector vector = new java.util.Vector();
				
				String sql = "";
				if(i==0){
					sql="select * from prpcmain where policyno in ('1285205072008004861','1285205072009000878','1285205072009002047','1285205072009001929','1296605072009000260')";
				}else{
					sql="SELECT * FROM PrpCmain WHERE riskcode='0507' and immevalidflag='1' and inputdate=to_date('"+sdf.format(date)+"','yyyy-MM-dd')";
					date.setTime(date.getTime()-86400000);
				}
				
				try{
					
					logger.info("sql=="+sql);
					

					vector = dbPrpCmain1.findByConditions(dbpool,sql);
				}catch(Exception e){
				}
				
				
				logger.info("----------count:::");
				

				dbpool.beginTransaction();
				for(int j = 0; j<vector.size();j++){
					prpCmainSchema = (PrpCmainSchema)vector.get(j);
					
					logger.info("----------prpCmainSchema:::" + prpCmainSchema.getPolicyNo());
					

					if(!"01".equals(prpCmainSchema.getComCode().substring(0,2))){
						DBPrpCjfcdLogMsg dbPrpCjfcdLogMsg = new DBPrpCjfcdLogMsg();
						if(dbPrpCjfcdLogMsg.getInfo(dbpool,prpCmainSchema.getPolicyNo()) == 0){
							
							strImmeValidStartDate = dbPrpCjfcdLogMsg.getConfirmDate();
						}else{
							
							DBPrpTimeRegister dbPrpTimeRegister = new DBPrpTimeRegister();
							if(dbPrpTimeRegister.getInfo(dbpool,prpCmainSchema.getProposalNo(),"1","T") == 0 ){
								strImmeValidStartDate = dbPrpTimeRegister.getPassTime();
							}
						}
					}else{
						
						if(PubTools.compareDate(prpCmainSchema.getOperateDate(),BJImmeValidDate)>=0){
							BLCIInsureValid blCIInsureValid = new BLCIInsureValid();
							blCIInsureValid.query(dbpool,"proposalno = '"+prpCmainSchema.getProposalNo()+"'");
							if(blCIInsureValid.getSize()>0&&blCIInsureValid.getArr(0).getImmeValidFlag().equals("1")){
								strImmeValidStartDate = blCIInsureValid.getArr(0).getImmeValidDate();
							}
						}else{
							
							DBPrpCjfcdLogMsg dbPrpCjfcdLogMsg = new DBPrpCjfcdLogMsg();
							if(dbPrpCjfcdLogMsg.getInfo(dbpool,prpCmainSchema.getPolicyNo()) == 0){
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
			}
		}catch (Exception e){
			dbpool.rollbackTransaction();
			e.printStackTrace();
			myLog(configPath,e.getMessage());
		}finally{
			dbpool.close();
			myLog(configPath,"over!!!!!!!");
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
<form name="fm" action="/prpall/admin/ImmeValidHistory2.jsp">
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