package com.test.yj.dto;

import java.util.*;

import com.sp.platform.bl.facade.BLPrpDcompanyFacade;
import com.sp.platform.dto.domain.PrpDcompanyDto;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.*;
import com.sp.prpall.blsvr.tb.BLPrpTdangerItem;
import com.sp.prpall.blsvr.tb.BLPrpTdangerPlan;
import com.sp.prpall.blsvr.tb.BLPrpTdangerTot;
import com.sp.prpall.blsvr.tb.BLPrpTdangerUnit;
import com.sp.prpall.blsvr.tb.BLPrpTexpense;
import com.sp.prpall.blsvr.tb.BLPrpTfee;
import com.sp.prpall.blsvr.tb.BLPrpTinsured;
import com.sp.prpall.blsvr.tb.BLPrpTinsuredNature;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.blsvr.tb.BLPrpTmainCasualty;
import com.sp.prpall.blsvr.tb.BLPrpTplan;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.tb.*;
import com.sp.sysframework.reference.AppConfig;
import com.sp.taskmng.util.TaskMngUtil;
import com.sp.utiall.dbsvr.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import com.sp.utiall.schema.*;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.undwrt.bl.facade.BLTaskFacade;
import com.test.euse.*;
import com.test.huaan.dto.*;
import java.text.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class CreateTpolicys{
	private String updateSql="";
	private HAdbsvr                 hadb                     = HAdbsvr.getHAdbsvr();
	String strFlowId ="";
	private String C_CRT_TM;            
	private String C_CRT_CDE;           
	private String C_PLY_NO;            
	private String C_INSRNC_CDE;        
	private String C_BSNS_TYP;          
	private String C_DPT_CDE;           
	private String C_SLS_CDE;           
	
	private String C_AGT_CDE;           
	private String C_AGT_NO;            
	private String C_APP_CNM;           
	private String C_INSRNT_CNM;        
	private String C_INSRNT_ID;         
	private String C_INSRNT_TEL;        
	private String C_BNFC_CNM;          
	
	private String D_APP_DATE;          
	private String D_SIGN_DATE;         
	private String D_INSBGN_DATE;       
	private String D_INSEND_DATE;       
	private String D_FLIGHT_DATE;       
	private String C_FLIGHT_NO;         
	private String N_AMT;               
	
	private String N_PRM;               
	private String N_CMM_PROP;          
	private String N_RATIO;             
	private String N_COUNT;             
	private String C_STTL_CDE;          
	private String C_REMARK;            
	private String C_VCH_TYP;           
	private String Customer1;           /*XX�˺ͱ�XXXXX�˴���*/
	private String Customer2;           /*�����˴���*/
	private String C_VCH_NO;            
	private String D_USE_DATE;          
	private String C_CHG_FLAG;          
	private String C_VCH_STAT;          
	private String C_BNFC_TEL;			
	private String C_BNFC_ADD;		    
	private String C_SALEGRP_CDE;		
	
	private DBPrpTmain              dbprpTmain               = new DBPrpTmain();
    private DBPrpTmainCasualty      dbprpTmainCasualty       = new DBPrpTmainCasualty();
    private DBPrpTfee               dbprpTfee                = new DBPrpTfee();
    private DBPrpTplan              dbprpTplan               = new DBPrpTplan();
    private DBPrpTinsured           dbprpTinsured            = new DBPrpTinsured();
    private DBPrpTinsuredNature     dbprpTinsureNature       = new DBPrpTinsuredNature();
    private DBPrpTitemKind          dbprpTitemKind           = new DBPrpTitemKind();
    private DBPrpTdangerUnit        dbprpTdangerUnit         = new DBPrpTdangerUnit();
    private DBPrpDcustomer          dbPrpDcustomer           = new DBPrpDcustomer();
    private DBPrpDcustomerIdv       dbPrpDcustomerIdv        = new DBPrpDcustomerIdv();
    private DBPrpTexpense           dbPrpTexpense            = new DBPrpTexpense();
    private DB                      db                       = DB.getDB();    
	private String C_Handler1Code="00200003";
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	private String TransactionCode="";
    
	protected final Log logger = LogFactory.getLog(getClass());
    
    /**
     * ����һ����¼
     * @throws Exception
     */
    public ArrayList getBaseInfo(){	
    	String strsql="select to_char(OperateTime,'yyyymmddhh24miss'),OfficeCode,a.InsureNo,PassengerName,PassengerID,"
                   +"BenefitorName,to_char(FlightDate,'yyyymmdd'),FlightNumber,BenefitorID,TransactionCode,"
                   +"flag1,flag2 from hl_policyschedule a,(select InsureNo from  hl_policyschedule group by InsureNo"
                   +" having count(InsureNo||Flightnumber)>1) b"
                   +" where TransactionCode='TKTS' and a.InsureNo=b.InsureNo(+)"
                   +" and b.InsureNo is null and a.flag1 is null";
        /*modi by liujia start 2009-02-24
         * reason ÿ�δ������ļ�ȡ��������*/
        String maxRow = AppConfig.get("sysconst.HLMAXROW");
        strsql=strsql+ " and rownum<"+maxRow +" order by a.InsureNo asc ";
        
    	ArrayList list=hadb.queryAgtManager(strsql); 
    	return list;       
    }	
	/*t�ױ�д������*/
    public  void  createTpolicys(ArrayList list)throws Exception{
    	
    	DbPool dbpool = new DbPool();
    	DbPool dbpoolNew = new DbPool();
    	
    	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
    		dbpoolNew.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
    	}
	    dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
    	
    	
    	ArrayList listItem=null;
    	ArrayList returnList=new ArrayList();
    	ArrayList returnListItem = null;
    	N_CMM_PROP      = getDropRate(dbpool);
    	if(N_CMM_PROP==null){
    	    N_CMM_PROP="0.0000";
    	}
    	try{
	        for(int i=0;i<list.size();i++){
	        	try{
	        	
	        	returnListItem = new ArrayList();
	        	listItem        = (ArrayList)list.get(i);
	        	String tempinputdate=(String)listItem.get(0);/*OperateTime*/
	        	
	        	tempinputdate=tempinputdate.substring(0,8);
	        	tempinputdate=tempinputdate.substring(0,4)+"-"+tempinputdate.substring(4,6)+"-"+tempinputdate.substring(6,8);
				C_CRT_TM        = tempinputdate;
				C_CRT_CDE       = (String)listItem.get(1);/*OfficeCode*/
				C_PLY_NO        = (String)listItem.get(2);/*InsureNo*/
				C_INSRNC_CDE    = "2708";
				C_BSNS_TYP      = "2";/*ҵ����Դ----------------------------------*/
				C_DPT_CDE       = "00200000";
				C_SLS_CDE       = "00200003";/*����,����,����---------------------*/
				       
				C_AGT_CDE       = "M00200000046";/*�������-----------------------*/
				C_AGT_NO        = "M00200000046-01";/*����Э���------------------*/
				C_APP_CNM       = (String)listItem.get(3);/*XX����PassengerName*/
				C_INSRNT_CNM    = (String)listItem.get(3);/*��XXXXX����PassengerName*/
				C_INSRNT_ID     = (String)listItem.get(4);/*PassengerID*/
				if(C_INSRNT_ID.length()==15||C_INSRNT_ID.length()==18){
					C_INSRNT_TEL    = "01";
				}else{
					C_INSRNT_TEL    = "99";
				}
				
				C_BNFC_CNM      = (String)listItem.get(5);/*BenefitorName	*/
			    if(C_BNFC_CNM.length()<2){
			         C_BNFC_CNM="����";	
			    }	              
				D_APP_DATE      = tempinputdate;
				D_SIGN_DATE     = tempinputdate;
				D_INSBGN_DATE   = (String)listItem.get(6);/*FlightDate*/
				
				D_INSBGN_DATE=D_INSBGN_DATE.substring(0,4)+"-"+D_INSBGN_DATE.substring(4,6)+"-"+D_INSBGN_DATE.substring(6,8);
				String []riqi=D_INSBGN_DATE.split("-");
				int endYear=Integer.parseInt(riqi[0]);
	      		int endMonth=Integer.parseInt(riqi[1]);
	      		int endDay=Integer.parseInt(riqi[2]);
	      		java.util.Date date = new java.util.Date(endYear-1900,endMonth-1,endDay);
	      		date.setDate(date.getDate()+1);
	      		
				D_INSEND_DATE   = formatter.format(date);
				D_FLIGHT_DATE   = D_INSBGN_DATE;
				C_FLIGHT_NO     = (String)listItem.get(7);/*FlightNumber*/
				N_AMT           = "400000.00";
				N_PRM           = "20.00";
				N_RATIO         = "0.00";
				N_COUNT         = "1";
				C_STTL_CDE      = "1";
				C_REMARK        = "";
				C_VCH_TYP       = "";/*��֤����----------------------ABK097A32007A*/
				              
				C_VCH_NO        = "";
				D_USE_DATE      = "";
				C_BNFC_ADD      = (String)listItem.get(8);/*BenefitorID*/
				if(C_BNFC_ADD.length()>2){
					C_BNFC_TEL      = "ָ��";
				}else{
					C_BNFC_TEL      = "����";
				}
					
				
				if(C_BNFC_ADD.length()<3){
					C_BNFC_ADD="";
				}
				C_SALEGRP_CDE   = "99";
				C_Handler1Code  = "01110012";
				TransactionCode      = (String)listItem.get(9);/*TransactionCode*/
				}catch(Exception ess){
					ess.printStackTrace();
					
		            TaskMngUtil.insertMidDataLog(C_PLY_NO, ess.getMessage(), ess, TaskMngUtil.TransCons.Trans_MyTask_JobName);
				}
				
					Customer1=getCustomer(C_DPT_CDE,dbpool);
		        	if(C_BNFC_ADD!=null&&C_BNFC_ADD.length()>0&&C_BNFC_ADD.equals("null")==false){
		        		long l=Long.parseLong(Customer1.substring(7,Customer1.length()));
		        		l=l+1;
		        		Customer2=Customer1.substring(0,Customer1.length()-(""+l).length());
		        		Customer2=Customer2+l;
		        	}else{
		        		Customer2="";
		        	}
		        	
		        	
					DBPrpCmain dpc = new DBPrpCmain();
					int a = dpc.getCount(dbpool, " policyno='"+C_PLY_NO+"' ");
					if(a>0){
						updateSql="update HL_POLICYSCHEDULE set flag1='1' where InsureNo='"+C_PLY_NO+"' and TransactionCode='TKTS'"; 
						hadb.exceUpdate(updateSql);
					}else{					
		        	List listTemp = null;
					delete(dbpool);
		        	try{
		        		dbpool.beginTransaction();
		        		
		        		if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
		        		dbpoolNew.beginTransaction();
		        		}
		        		
		        		
			        	dbprpTmain.setSchema(createTmain());
			    		
					    
					    PubTools.setChannelTypeSub(dbprpTmain);






















			    		
			    		
			        	dbprpTmain.insert(dbpool);
			        	
			        	
			        	listTemp=createPrpDcustomer();
			        	for(int j=0;j<listTemp.size();j++){
			        		dbPrpDcustomer.setSchema((PrpDcustomerSchema)listTemp.get(j));
			        		
			            	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
			            		dbPrpDcustomer.insert(dbpoolNew);
			            	}else{       		    		
			        		dbPrpDcustomer.insert(dbpool);
			            	}
			            	
			        	}
			        	
			        	listTemp=createDcustomerIdv();
			        	for(int j=0;j<listTemp.size();j++){
			        		dbPrpDcustomerIdv.setSchema((PrpDcustomerIdvSchema)listTemp.get(j));
			        		
			            	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
			            		dbPrpDcustomerIdv.insert(dbpoolNew);
			            	}else{       		    		
			        		dbPrpDcustomerIdv.insert(dbpool);
			            	}
			            	
			        	}
			        	
			        	dbprpTmainCasualty.setSchema(createTmainCasualty());
			        	dbprpTmainCasualty.insert(dbpool);
			        	
			        	dbprpTfee.setSchema(createTfee());
			        	dbprpTfee.insert(dbpool);
			        	
			        	dbprpTplan.setSchema(createTplan());
			        	dbprpTplan.insert(dbpool);
			        	
			        	listTemp=createTinsured();
			        	for(int j=0;j<listTemp.size();j++){
			        		dbprpTinsured.setSchema((PrpTinsuredSchema)listTemp.get(j));
			        		dbprpTinsured.insert(dbpool);
			        	}
			        	
			        	listTemp=createTinsuredNature();
			        	for(int j=0;j<listTemp.size();j++){
			        		dbprpTinsureNature.setSchema((PrpTinsuredNatureSchema)listTemp.get(j));
			        		dbprpTinsureNature.insert(dbpool);
			        	}
			        	
			        	listTemp=createTItemKind();
			        	for(int j=0;j<listTemp.size();j++){
			        		dbprpTitemKind.setSchema((PrpTitemKindSchema)listTemp.get(j));
			        		dbprpTitemKind.insert(dbpool);
			        	}
			        	
			        	dbPrpTexpense.setSchema(createPrpTexpense());
			        	dbPrpTexpense.insert(dbpool);
			        	
			        	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
			        	dbpoolNew.commitTransaction();
			        	}
			        	
			        	dbpool.commitTransaction();
			        	/*update huaa*/
			        	updateSql="update HL_POLICYSCHEDULE set flag1='1' where InsureNo='"+C_PLY_NO+"' and TransactionCode='TKTS'"; 
			        }catch(Exception exception){
		        		exception.printStackTrace();
		        		
		        		if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
		        		dbpoolNew.rollbackTransaction();
		        		}
		        		
			            dbpool.rollbackTransaction();
			            /*update huaa*/
			            updateSql="update HL_POLICYSCHEDULE set flag1='2' where InsureNo='"+C_PLY_NO+"' and TransactionCode='TKTS'"; 
			            
			            TaskMngUtil.insertMidDataLog(C_PLY_NO, exception.getMessage(), exception, TaskMngUtil.TransCons.Trans_MyTask_JobName);
			        }
			        try{
			        	hadb.exceUpdate(updateSql);
			        }catch(Exception haeor){
			        	haeor.printStackTrace();
			        	
			            TaskMngUtil.insertMidDataLog(C_PLY_NO, haeor.getMessage(), haeor, TaskMngUtil.TransCons.Trans_MyTask_JobName);
			        }
			        
			        try{	
			        	com.sp.undwrt.bl.facade.BLTaskFacade blTaskFacade = new com.sp.undwrt.bl.facade.BLTaskFacade();
			        	strFlowId=blTaskFacade.start("11","T",C_PLY_NO,"2708",
				                           "27",C_DPT_CDE,C_DPT_CDE,
                                           C_Handler1Code,C_Handler1Code,C_Handler1Code,"");
		        	}catch(Exception ex2){
		        		ex2.printStackTrace(); 
		        		
			            TaskMngUtil.insertMidDataLog(C_PLY_NO, ex2.getMessage(), ex2, TaskMngUtil.TransCons.Trans_MyTask_JobName);
			        }
				}
		        
		    }
	     }catch(Exception e){
	     	  e.printStackTrace();
	     }finally{
	    	 if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
	    	  dbpoolNew.close();
	    	 }
			  dbpool.close();
			  
		 }
    }

    public void delete(DbPool dbpool) throws SQLException{
    	try{
		dbpool.beginTransaction();
	    
		logger.info("---------------del	start------------");
    	logger.info("del1:"+C_PLY_NO);   
        
    	BLPrpTfee blprptfee = new BLPrpTfee();
    	blprptfee.cancel(dbpool,  C_PLY_NO);
        
    	logger.info("del2");	        	
        

    	BLPrpTplan blprptplan = new BLPrpTplan();
    	blprptplan.cancel(dbpool,  C_PLY_NO);
        
    	logger.info("del3");	        	
        
    	BLPrpTinsuredNature blprptinsurenature = new BLPrpTinsuredNature();
    	blprptinsurenature.cancel(dbpool,  C_PLY_NO);
        
    	logger.info("del4");	        	
        
    	BLPrpTinsured blprptinsured = new BLPrpTinsured();
    	blprptinsured.cancel(dbpool,  C_PLY_NO);
        
    	logger.info("del5");	        	
        
       	BLPrpTexpense blprptexpense = new BLPrpTexpense();
       	blprptexpense.cancel(dbpool,  C_PLY_NO);
        
    	logger.info("del6");	        	
        
 
       	BLPrpTitemKind blprptitemkind = new BLPrpTitemKind();
       	blprptitemkind.cancel(dbpool,  C_PLY_NO);
        
    	logger.info("del7");	        	
        
    	BLPrpTdangerUnit blunit = new BLPrpTdangerUnit();
    	blunit.cancel(dbpool, C_PLY_NO);
        
    	logger.info("del8");  
        
    	BLPrpTmainCasualty blptca = new BLPrpTmainCasualty();
    	blptca.cancel(dbpool, C_PLY_NO);
        
    	logger.info("del9");
        
    	
    	
    	BLPrpTmain blptmain = new BLPrpTmain();
    	blptmain.cancel(dbpool, C_PLY_NO);    	
    	dbpool.commitTransaction();
        
    	logger.info("---------------del		OK------------");
        
    	}catch(Exception e){
    		dbpool.rollbackTransaction();
    	}
    }    
    /*��ȡһ��XXXXXid*/
    public String getCustomer(String makecom,DbPool dbpool){
    	String strsql="select to_char(to_number(nvl(max(c.customercode), '5'||substr('"+makecom+"',0,6)||'000000000'))+1)"
                     +" from prpdcustomer c"
                     +" where c.customercode like '5'||substr('"+makecom+"',0,6)||'%'";
    	ArrayList list = db.queryAgtManager(strsql,dbpool);
    	ArrayList listItem=(ArrayList)list.get(0);
    	return (String)listItem.get(0);
    }
    /*��ȡ�����ѱ���*/
    public String getDropRate(DbPool dbpool){
    	try{
    	
    	String strsql="select TOPCOMMISSION "
                     +" from PRPDAGREEDETAIL"
                     +" where AGENTCODE='"+C_AGT_CDE+"' and AGREEMENTNO='"+C_AGT_NO+"' and RISKCODE='2708'";
        
    	ArrayList list = null;
        DbPool dbpoolNew = new DbPool();
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpoolNew.open("platformNewDataSource");
            	list = db.queryAgtManager(strsql,dbpool);
            } else {
            	list = db.queryAgtManager(strsql,dbpool);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            dbpoolNew.close();
        }
        
    	ArrayList listItem=(ArrayList)list.get(0);
    	return (String)listItem.get(0);
    	}catch(Exception e){
    		return "20.0000";
    	}
    }

    public void updateAccidentPolicy(DbPool dbpool,String policyno) throws Exception{
        String strSQL = " Update t_accident_policy Set C_CHG_FLAG ='1' where C_PLY_NO='"+policyno+"'";
        dbpool.update(strSQL);
    }
    public PrpTexpenseSchema createPrpTexpense(){
    	PrpTexpenseSchema prpTespense = new PrpTexpenseSchema();
    	prpTespense.setProposalNo(C_PLY_NO);
    	prpTespense.setRiskCode(C_INSRNC_CDE);
    	
		
    	prpTespense.setDisRate(N_CMM_PROP);
		
    	
    	prpTespense.setManageFeeRate("0.0000");
    	prpTespense.setMaxManageFeeRate("100.00");
    	prpTespense.setFlag("I2");
    	return prpTespense;
    }
    
    public List createPrpDcustomer(){
    	List customers = new ArrayList();
    	PrpDcustomerSchema prpDcustomer1 = new PrpDcustomerSchema();
    	prpDcustomer1.setCustomerType("1");
    	prpDcustomer1.setCustomerCode(Customer1);
    	prpDcustomer1.setCustomerCName(C_APP_CNM);
    	prpDcustomer1.setValidStatus("1");
    	customers.add(prpDcustomer1);
    	if(C_BNFC_ADD!=null&&C_BNFC_ADD.length()>0&&C_BNFC_ADD.equals("null")==false){
    		PrpDcustomerSchema prpDcustomer2 = new PrpDcustomerSchema();
    		prpDcustomer2.setCustomerType("1");
	    	prpDcustomer2.setCustomerCode(Customer2);
	    	prpDcustomer2.setCustomerCName(C_BNFC_CNM);
	    	prpDcustomer2.setValidStatus("1");
	    	customers.add(prpDcustomer2);
    	}
    	return customers;
    }
    
    public List createDcustomerIdv(){
    	List dcustomerIdvs = new ArrayList();
    	PrpDcustomerIdvSchema  prpDcustomerIdv1 = new PrpDcustomerIdvSchema();
    	prpDcustomerIdv1.setCustomerCode(Customer1);	
        prpDcustomerIdv1.setCustomerCName(C_APP_CNM);
        prpDcustomerIdv1.setIdentifyType(C_INSRNT_TEL);
        prpDcustomerIdv1.setIdentifyNumber(C_INSRNT_ID);
        prpDcustomerIdv1.setNewCustomerCode(Customer1);
        prpDcustomerIdv1.setValidStatus("1");
        prpDcustomerIdv1.setLowerViewFlag("0");
        prpDcustomerIdv1.setOperatorCode(C_SLS_CDE);
        prpDcustomerIdv1.setInputDate(D_SIGN_DATE);
        prpDcustomerIdv1.setUpdaterCode(C_SLS_CDE);
        prpDcustomerIdv1.setUpdateDate(D_SIGN_DATE);
        prpDcustomerIdv1.setComcode(C_DPT_CDE);
        dcustomerIdvs.add(prpDcustomerIdv1);
        if(C_BNFC_ADD!=null&&C_BNFC_ADD.length()>0&&C_BNFC_ADD.equals("null")==false){
        	PrpDcustomerIdvSchema  prpDcustomerIdv2 = new PrpDcustomerIdvSchema();
	    	prpDcustomerIdv2.setCustomerCode(Customer2);	
	        prpDcustomerIdv2.setCustomerCName(C_BNFC_CNM);
	        prpDcustomerIdv2.setIdentifyType(C_BNFC_TEL);
	        prpDcustomerIdv2.setIdentifyNumber(C_BNFC_ADD);
	        prpDcustomerIdv2.setNewCustomerCode(Customer2);
	        prpDcustomerIdv2.setValidStatus("1");
	        prpDcustomerIdv2.setLowerViewFlag("0");
	        prpDcustomerIdv2.setOperatorCode(C_SLS_CDE);
	        prpDcustomerIdv2.setInputDate(D_SIGN_DATE);
	        prpDcustomerIdv2.setUpdaterCode(C_SLS_CDE);
	        prpDcustomerIdv2.setUpdateDate(D_SIGN_DATE);
	        prpDcustomerIdv2.setComcode(C_DPT_CDE);
	        dcustomerIdvs.add(prpDcustomerIdv2);        	
        }
        return dcustomerIdvs;
    }

    /**/
    public PrpTfeeSchema createTfee(){
    	PrpTfeeSchema dbprpTfee1=new PrpTfeeSchema();
    	dbprpTfee1.setProposalNo(C_PLY_NO);                  /*XX����*/
        dbprpTfee1.setRiskCode(C_INSRNC_CDE);                /*XXXXX�ִ���*/
        dbprpTfee1.setCurrency("CNY");                       /*����*/
        dbprpTfee1.setAmount(N_AMT);                         /*XX*/
        dbprpTfee1.setPremium(N_PRM);                        /*XX*/
        
        dbprpTfee1.setCurrency1("CNY");
        dbprpTfee1.setExchangeRate1("1.00000000");
        dbprpTfee1.setAmount1(N_AMT);
        dbprpTfee1.setPremium1(N_PRM);
        dbprpTfee1.setCurrency2("CNY");
        dbprpTfee1.setExchangeRate2("1.00000000");
        dbprpTfee1.setAmount2(N_AMT);
        dbprpTfee1.setPremium2(N_PRM);
        return dbprpTfee1;

    }
    /**/
    public PrpTplanSchema createTplan(){
    	PrpTplanSchema dbprpTplan1 = new PrpTplanSchema();
        dbprpTplan1.setProposalNo(C_PLY_NO);
        
        dbprpTplan1.setSerialNo("1");
        dbprpTplan1.setPayNo("1");
        dbprpTplan1.setPayReason("R10");
        dbprpTplan1.setPlanDate(D_SIGN_DATE);
        dbprpTplan1.setCurrency("CNY");
        dbprpTplan1.setPlanFee(N_PRM);
        dbprpTplan1.setDelinquentFee(N_PRM);
        
        dbprpTplan1.setPlanStartDate(D_SIGN_DATE);
        return dbprpTplan1;
    }
    /*��TmainCasualtyд����*/
    public PrpTmainCasualtySchema createTmainCasualty(){
    	PrpTmainCasualtySchema dbprpTmainCasualty1 = new PrpTmainCasualtySchema();
    	dbprpTmainCasualty1.setProposalNo(C_PLY_NO);         /*XX����*/
    	dbprpTmainCasualty1.setRiskCode(C_INSRNC_CDE);       /*XXXXX�ִ���*/
    	dbprpTmainCasualty1.setBusinessGrade("1");           /*XX�ȼ�*/
    	return dbprpTmainCasualty1;
    }
    
    public List createTItemKind(){
    	List titemKinds = new ArrayList();
    	
    		PrpTitemKindSchema prpItemKind1 = new PrpTitemKindSchema();
    		prpItemKind1.setProposalNo(C_PLY_NO);
            prpItemKind1.setRiskCode("2708");
            prpItemKind1.setItemKindNo("1");
            prpItemKind1.setFamilyNo("2");
    		prpItemKind1.setKindCode("106");
            prpItemKind1.setKindName("�����ÿ������˺�XX");
            prpItemKind1.setItemCode("0001");
            prpItemKind1.setItemDetailName("�����˺�");
            prpItemKind1.setStartDate(D_INSBGN_DATE);
            prpItemKind1.setStartHour("0");
            prpItemKind1.setEndDate(D_INSEND_DATE);
            prpItemKind1.setEndHour("24");
            prpItemKind1.setCalculateFlag("Y");
            prpItemKind1.setCurrency("CNY");
            prpItemKind1.setUnitAmount(N_AMT);
            prpItemKind1.setQuantity("1.00");
            prpItemKind1.setValue("1.00");
            prpItemKind1.setAmount(N_AMT);
            prpItemKind1.setRate("0.05000");
            prpItemKind1.setShortRateFlag("3");
            prpItemKind1.setShortRate("100.0000");
            prpItemKind1.setDiscount("100.0000");
            prpItemKind1.setPremium("20.00");
            prpItemKind1.setFlag("11");
            titemKinds.add(prpItemKind1);
    	
    	return titemKinds; 
    }
    
    public List createTinsuredNature(){
    	List tinsuredNatures = new ArrayList();
    	PrpTinsuredNatureSchema prpTinsureNature1 = new  PrpTinsuredNatureSchema();
    	prpTinsureNature1.setProposalNo(C_PLY_NO);
    	prpTinsureNature1.setSerialNo("1");
    	prpTinsureNature1.setInsuredFlag("2");
        prpTinsureNature1.setUnitType("00");
    	tinsuredNatures.add(prpTinsureNature1);

    	PrpTinsuredNatureSchema prpTinsureNature2 = new  PrpTinsuredNatureSchema();
    	prpTinsureNature2.setProposalNo(C_PLY_NO);
    	prpTinsureNature2.setSerialNo("2");
    	prpTinsureNature2.setInsuredFlag("1");
        
    	tinsuredNatures.add(prpTinsureNature2);
    	
    	if(C_BNFC_ADD!=null&&C_BNFC_ADD.length()>0&&C_BNFC_ADD.equals("null")==false){
    		PrpTinsuredNatureSchema prpTinsureNature3 = new  PrpTinsuredNatureSchema();
	    	prpTinsureNature3.setProposalNo(C_PLY_NO);
	    	prpTinsureNature3.setSerialNo("3");
	    	prpTinsureNature3.setInsuredFlag("9");
	        prpTinsureNature3.setUnitType("00");
    		tinsuredNatures.add(prpTinsureNature3);
    	}
    	return tinsuredNatures;
    }
    
    public List createTinsured(){
    	List tinsureds = new ArrayList();
    	PrpTinsuredSchema prpTinsuredSchema1 = new PrpTinsuredSchema();
    	prpTinsuredSchema1.setProposalNo(C_PLY_NO);
        prpTinsuredSchema1.setRiskCode(C_INSRNC_CDE);
        prpTinsuredSchema1.setSerialNo("1");
        prpTinsuredSchema1.setLanguage("C");
        prpTinsuredSchema1.setInsuredType("1");
        prpTinsuredSchema1.setInsuredCode(Customer1);
        prpTinsuredSchema1.setInsuredName(C_APP_CNM);
        prpTinsuredSchema1.setInsuredNature("3");
        prpTinsuredSchema1.setInsuredFlag("2");
        prpTinsuredSchema1.setInsuredIdentity("01");/**/
        prpTinsuredSchema1.setIdentifyType(C_INSRNT_TEL);
        prpTinsuredSchema1.setIdentifyNumber(C_INSRNT_ID);
        prpTinsuredSchema1.setBenefitFlag("N");
        prpTinsuredSchema1.setBenefitRate("0.00");
    	tinsureds.add(prpTinsuredSchema1);
    	
    	
    	
    	PrpTinsuredSchema prpTinsuredSchema2 = new PrpTinsuredSchema();
    	prpTinsuredSchema2.setProposalNo(C_PLY_NO);
        prpTinsuredSchema2.setRiskCode(C_INSRNC_CDE);
        prpTinsuredSchema2.setSerialNo("2");
        prpTinsuredSchema2.setLanguage("C");
        prpTinsuredSchema2.setInsuredType("1");
        prpTinsuredSchema2.setInsuredCode(Customer1);
        prpTinsuredSchema2.setInsuredName(C_APP_CNM);
        prpTinsuredSchema2.setInsuredNature("3");
        prpTinsuredSchema2.setInsuredFlag("1");
        prpTinsuredSchema2.setInsuredIdentity("01");/**/
        prpTinsuredSchema2.setRelateSerialNo("0");
        prpTinsuredSchema2.setIdentifyType(C_INSRNT_TEL);
        prpTinsuredSchema2.setIdentifyNumber(C_INSRNT_ID);
        prpTinsuredSchema2.setBenefitFlag("N");
        prpTinsuredSchema2.setBenefitRate("0.00");
    	tinsureds.add(prpTinsuredSchema2);
    	if(C_BNFC_ADD!=null&&C_BNFC_ADD.length()>0&&C_BNFC_ADD.equals("null")==false){
    		PrpTinsuredSchema prpTinsuredSchema3 = new PrpTinsuredSchema();
	    	prpTinsuredSchema3.setProposalNo(C_PLY_NO);
	        prpTinsuredSchema3.setRiskCode(C_INSRNC_CDE);
	        prpTinsuredSchema3.setSerialNo("3");
	        prpTinsuredSchema3.setLanguage("C");
	        prpTinsuredSchema3.setInsuredType("1");
	        prpTinsuredSchema3.setInsuredCode(Customer2);
	        prpTinsuredSchema3.setInsuredName(C_BNFC_CNM);
	        prpTinsuredSchema3.setInsuredNature("3");
	        prpTinsuredSchema3.setInsuredFlag("9");
	        prpTinsuredSchema3.setInsuredIdentity(C_SALEGRP_CDE);/**/
	        prpTinsuredSchema3.setRelateSerialNo("2");
	        prpTinsuredSchema3.setIdentifyType(C_BNFC_TEL);
	        prpTinsuredSchema3.setIdentifyNumber(C_BNFC_ADD);
	        prpTinsuredSchema3.setBenefitRate("100.00");    	    		
    		tinsureds.add(prpTinsuredSchema3);
    	}
    	
    	return tinsureds;
    } 
    /*��tmainд����*/
    public PrpTmainSchema createTmain(){
    	PrpTmainSchema dbprpTmain1 = new PrpTmainSchema();
    	dbprpTmain1.setProposalNo(C_PLY_NO);                 /*XX����*/
    	dbprpTmain1.setClassCode("27");                      /*XXXXX��*/
    	dbprpTmain1.setRiskCode(C_INSRNC_CDE);               /*XXXXX�ִ���*/
    	
    	dbprpTmain1.setPolicySort("2");                      /*XX����*/
    	dbprpTmain1.setPrintNo(C_VCH_NO);                    
    	dbprpTmain1.setBusinessNature("3");                    /*ҵ����Դ*/
    	dbprpTmain1.setLanguage("C");  	                     /*����*/
        dbprpTmain1.setPolicyType("01");                     /*XX����*/
        dbprpTmain1.setAppliCode(Customer1);                 /*XX�˴���*/
        
        dbprpTmain1.setAppliName(C_APP_CNM);                 /*XX������*/
        
        dbprpTmain1.setInsuredCode(Customer1);               /*��XX�˴���*/
        dbprpTmain1.setInsuredName(C_APP_CNM);               /*��XX������*/
        
        dbprpTmain1.setOperateDate(D_SIGN_DATE);             /*ǩ������*/
        dbprpTmain1.setStartDate(D_INSBGN_DATE);             /*XX����*/
        dbprpTmain1.setStartHour("0");                       /*��XXXXXСʱ*/
        dbprpTmain1.setEndDate(D_INSEND_DATE);               /*XXֹ��*/
        dbprpTmain1.setEndHour("24");                        /*��XXXXXСʱ*/
        
        dbprpTmain1.setPureRate("0.0000");                   /*������*/
        dbprpTmain1.setDisRate(N_CMM_PROP);                  /*�����ѱ���*/                   
        dbprpTmain1.setDiscount("100.0000");                 /*���ۿ���*/
        dbprpTmain1.setCurrency("CNY");                      /*�ұ�*/
        dbprpTmain1.setSumValue("0.00");                     /*XX��ֵ*/
        dbprpTmain1.setSumAmount(N_AMT);                     /*XX*/
        dbprpTmain1.setSumDiscount("0.00");                  /*�ۿ۽��*/
        dbprpTmain1.setSumPremium(N_PRM);                    /*XX*/
        dbprpTmain1.setSumSubPrem("0.00");                   /*����XXXXXXX*/
        dbprpTmain1.setSumQuantity("1");                     /*��XX����*/
        
        dbprpTmain1.setJudicalCode("");                      /**/	
        dbprpTmain1.setJudicalScope("�л����񹲺͹���Ͻ(�۰�̨����)");/*˾����Ͻ*/
        dbprpTmain1.setAutoTransRenewFlag("1");              /*�ɷѷ�ʽ*/
        dbprpTmain1.setArgueSolution("1");                   /*��������ʽ*/
        
        dbprpTmain1.setPayTimes("1");                        /*�ɷѴ���*/
        dbprpTmain1.setEndorseTimes("0");                    /*���Ĵ���*/
        dbprpTmain1.setClaimTimes("0");                      /*XXXXX����*/
        dbprpTmain1.setMakeCom(C_DPT_CDE);                   /*��������*/
        dbprpTmain1.setOperateSite("YJDJ");                  /*ǩ���ص�*/
        
        dbprpTmain1.setComCode(C_DPT_CDE);                   /*ҵ�����*/
        dbprpTmain1.setHandlerCode(C_SLS_CDE);               /*�����˴���*/
        dbprpTmain1.setHandler1Code(C_Handler1Code);         /*����ҵ��Ա����*/
        dbprpTmain1.setApproverCode("");                     /*�����˴���*/
        
        
        dbprpTmain1.setOperatorCode(C_SLS_CDE);              /*����Ա����*/
        dbprpTmain1.setInputDate(C_CRT_TM);                  /*�������������*/
        dbprpTmain1.setInputHour("0");                       /*���������Сʱ*/
        
        	
        
        dbprpTmain1.setAgentCode(C_AGT_CDE);                 /*�����˱���*/
        dbprpTmain1.setCoinsFlag("0");                       /*����XXXXX��־*/
        
        dbprpTmain1.setAllinsFlag("2");                      /*ͳXXXXX��־*/
        dbprpTmain1.setUnderWriteFlag("0");                  /*��XXXXX��־*/
        dbprpTmain1.setOthFlag("000000YY000000000000");      /*������־�ֶ�*/
        
        dbprpTmain1.setDisRate1("0.0000");                   /*�������������ѱ���*/
        dbprpTmain1.setBusinessFlag("0");                    /*ҵ������*/
        
        dbprpTmain1.setUpdaterCode(C_SLS_CDE);               /*���������*/
        dbprpTmain1.setUpdateDate(D_SIGN_DATE);              /*�����������*/
        dbprpTmain1.setUpdateHour("0");                      /*�������Сʱ*/
        dbprpTmain1.setSignDate(D_SIGN_DATE);                /**/
        dbprpTmain1.setShareHolderFlag("0");                 /**/
        dbprpTmain1.setAgreementNo(C_AGT_NO);                /*����Э���*/
        
        dbprpTmain1.setPayMode("1");                         /*֧����ʽ*/
        if(C_REMARK!=null){
        	dbprpTmain1.setRemark(C_REMARK);                 /*��ע*/
        }
        
        dbprpTmain1.setPolicyNo(C_PLY_NO);                       /*XX��*/
        
        dbprpTmain1.setVisaCode(C_VCH_TYP);                  /*��֤����*/
        dbprpTmain1.setManualType("1");                      /**/
        return dbprpTmain1;
    } 
}