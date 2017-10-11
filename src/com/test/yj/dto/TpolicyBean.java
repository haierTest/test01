package com.test.yj.dto;

import java.util.*;
import com.sp.prpall.schema.*;
import com.sp.prpall.dbsvr.tb.*;
import com.sp.utiall.dbsvr.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import com.sp.utiall.schema.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.test.euse.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class TpolicyBean{
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
	private String Customer1;           /*XX人和被XXXXX人代码*/
	private String Customer2;           /*收益人代码*/
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
	private String C_Handler1Code;
    
	protected final Log logger = LogFactory.getLog(getClass());
    

	
	
	
	
	
	
	
	
    /**
     * 插入一条记录
     * @throws Exception
     */
	
	/*t套表写入数据*/
    public ArrayList createTpolicys(ArrayList list)throws Exception{
    	ArrayList listItem=null;
    	ArrayList returnList=new ArrayList();
    	ArrayList returnListItem = null;
        for(int i=0;i<list.size();i++){
        	returnListItem = new ArrayList();
        	listItem        = (ArrayList)list.get(i);
			C_CRT_TM        = (String)listItem.get(0);
			C_CRT_CDE       = (String)listItem.get(1);
			C_PLY_NO        = (String)listItem.get(2);
			C_INSRNC_CDE    = (String)listItem.get(3);
			C_BSNS_TYP      = (String)listItem.get(4);
			C_DPT_CDE       = (String)listItem.get(5);
			C_SLS_CDE       = (String)listItem.get(6);
			       
			C_AGT_CDE       = (String)listItem.get(7);
			C_AGT_NO        = (String)listItem.get(8);
			C_APP_CNM       = (String)listItem.get(9);
			C_INSRNT_CNM    = (String)listItem.get(10);
			C_INSRNT_ID     = (String)listItem.get(11);
			C_INSRNT_TEL    = (String)listItem.get(12);
			C_BNFC_CNM      = (String)listItem.get(13);
			              
			D_APP_DATE      = (String)listItem.get(14);
			D_SIGN_DATE     = (String)listItem.get(15);
			D_INSBGN_DATE   = (String)listItem.get(16);
			D_INSEND_DATE   = (String)listItem.get(17);
			D_FLIGHT_DATE   = (String)listItem.get(18);
			C_FLIGHT_NO     = (String)listItem.get(19);
			N_AMT           = (String)listItem.get(20);
			              
			N_PRM           = (String)listItem.get(21);
			N_CMM_PROP      = (String)listItem.get(22);
			N_RATIO         = (String)listItem.get(23);
			N_COUNT         = (String)listItem.get(24);
			C_STTL_CDE      = (String)listItem.get(25);
			C_REMARK        = (String)listItem.get(26);
			C_VCH_TYP       = (String)listItem.get(27);
			              
			C_VCH_NO        = (String)listItem.get(28);
			D_USE_DATE      = (String)listItem.get(29);
			C_BNFC_TEL      = (String)listItem.get(30);	
			C_BNFC_ADD      = (String)listItem.get(31);
			C_SALEGRP_CDE   = (String)listItem.get(32);
			C_Handler1Code  = (String)listItem.get(33);
			Customer1=getCustomer(C_DPT_CDE);
        	if(C_BNFC_ADD!=null&&C_BNFC_ADD.length()>0&&C_BNFC_ADD.equals("null")==false){
        		long l=Long.parseLong(Customer1.substring(7,Customer1.length()));
        		l=l+1;
        		Customer2=Customer1.substring(0,Customer1.length()-(""+l).length());
        		Customer2=Customer2+l;
        	}else{
        		Customer2="";
        	}
        	
        	DbPool dbpool = new DbPool();
        	DbPool dbpoolNew = new DbPool();
        	List listTemp = null;
        	
        	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
        		dbpoolNew.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
        	}
        	dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));
        
        	
        	try{
        		dbpool.beginTransaction();
        		
        		if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
        		dbpoolNew.beginTransaction();
        		}
        		
        		
        		
	        	dbprpTmain.setSchema(createTmain());
	            
	        	logger.info("111111111111:"+dbprpTmain.getProposalNo());
	            
	        	dbprpTmain.insert(dbpool);
	        	
	        	
	        	listTemp=createPrpDcustomer();
	        	for(int j=0;j<listTemp.size();j++){
	        		dbPrpDcustomer.setSchema((PrpDcustomerSchema)listTemp.get(j));
	        	    
	        		logger.info(dbPrpDcustomer.getCustomerCode());
	        	    
	        		
	            	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
	            		dbPrpDcustomer.insert(dbpoolNew);
	            	}else{       		    		
	        		dbPrpDcustomer.insert(dbpool);
	            	}
	            	
	        	}
	        	
	        	listTemp=createDcustomerIdv();
	        	for(int j=0;j<listTemp.size();j++){
	        		dbPrpDcustomerIdv.setSchema((PrpDcustomerIdvSchema)listTemp.get(j));
	        	    
	        		logger.info(dbPrpDcustomerIdv.getCustomerCode());
	        	    
	        		
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
	        	
	        	updateAccidentPolicy(dbpool,C_PLY_NO);
	        	
	        	
	        	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
	        	dbpoolNew.commitTransaction();
	        	}
	        	
	        	dbpool.commitTransaction();
	        	returnListItem.add(C_PLY_NO);
	        	returnListItem.add(C_APP_CNM);
	        	returnListItem.add(C_INSRNT_ID);
	        	returnListItem.add(C_INSRNC_CDE);
	        	returnListItem.add(C_VCH_NO);
	        	returnListItem.add("成功");
	        	returnList.add(returnListItem);	        	
                dbpool.close();
        	}catch(Exception exception){
        		
        		if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
        		dbpoolNew.rollbackTransaction();
        		}
        		
	            dbpool.rollbackTransaction();
                dbpool.update("Update t_accident_policy Set C_CHG_FLAG ='2' where C_PLY_NO='"+C_PLY_NO+"'");
	            returnListItem.add(C_PLY_NO);
	        	returnListItem.add(C_APP_CNM);
	        	returnListItem.add(C_INSRNT_ID);
	        	returnListItem.add(C_INSRNC_CDE);
	        	returnListItem.add(C_VCH_NO);
	        	returnListItem.add("失败");
	        	returnList.add(returnListItem);
	            dbpool.close();
	            
	        }finally{
	        	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
	        	dbpoolNew.close();
	        	}
	            dbpool.close();
	          
	        }
	    }
	    return returnList;
    }
    /*获取一个XXXXXid*/
    public String getCustomer(String makecom){
    	String strsql="select to_char(to_number(nvl(max(c.customercode), '5'||substr('"+makecom+"',0,6)||'000000000'))+1)"
                     +" from prpdcustomer c"
                     +" where c.customercode like '5'||substr('"+makecom+"',0,6)||'%'";
    	ArrayList list = db.queryAgtManager(strsql);
    	ArrayList listItem=(ArrayList)list.get(0);
    	return (String)listItem.get(0);
    }
    
    
    /*获取接口表中数据*/
    public ArrayList getBaseInfo(){	
    	String strsql= "select " 
    	              +"to_char(C_CRT_TM,'yyyy-mm-dd'),C_CRT_CDE,C_PLY_NO,C_INSRNC_CDE,'3',C_DPT_CDE,'01110006',"
    				  +"C_AGT_CDE,C_AGT_NO,C_APP_CNM,C_INSRNT_CNM,C_INSRNT_ID,"
    				  +"decode(C_INSRNT_TEL,'1','01','2','04','5','03','6','06','99'),C_BNFC_CNM,"
    				  +"to_char(D_APP_DATE,'yyyy-mm-dd'),to_char(D_SIGN_DATE,'yyyy-mm-dd'),"
    				  +"to_char(D_INSBGN_DATE,'yyyy-mm-dd'),to_char(D_INSEND_DATE,'yyyy-mm-dd'),"
    				  +"to_char(D_FLIGHT_DATE,'yyyy-mm-dd'),C_FLIGHT_NO,to_char(N_AMT,'999999999.99'),"
    				  +"to_char(N_PRM,'999999999.99'),'20.0000','0.00','1','1',C_REMARK,C_VCH_TYP,"
    				  +"C_VCH_NO,to_char(D_USE_DATE,'yyyy-mm-dd'),decode(C_BNFC_TEL,'1','01','2','04','5','03','6','06','99'),C_BNFC_ADD,"
    				  +"decode(C_SALEGRP_CDE,'本人','01','配偶','10','丈夫','11','妻子','12','儿子','20','女儿','30','儿女','40','父母','50','父亲','51','母亲','52','单位','80','其它','99','99'),'01531105'" 
    	              +" from t_accident_policy where substr(C_DPT_CDE,0,2)='01'" 
    	              +" and C_INSRNC_COMPNY_CDE is not null  and C_CHG_FLAG='0'"
    	              +" and C_VCH_STAT='2'";
    	ArrayList list=db.queryAgtManager(strsql); 
    	return list;  
    	        
    }
    public void updateAccidentPolicy(DbPool dbpool,String policyno) throws Exception{
        String strSQL = " Update t_accident_policy Set C_CHG_FLAG ='1' where C_PLY_NO='"+policyno+"'";
        dbpool.update(strSQL);
        
    }    
    public PrpTexpenseSchema createPrpTexpense(){
    	PrpTexpenseSchema prpTespense = new PrpTexpenseSchema();
    	prpTespense.setProposalNo(C_PLY_NO);
    	prpTespense.setRiskCode(C_INSRNC_CDE);
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
    	dbprpTfee1.setProposalNo(C_PLY_NO);                  /*XX单号*/
        dbprpTfee1.setRiskCode(C_INSRNC_CDE);                /*XXXXX种代码*/
        dbprpTfee1.setCurrency("CNY");                       /*币种*/
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
    /*向TmainCasualty写数据*/
    public PrpTmainCasualtySchema createTmainCasualty(){
    	PrpTmainCasualtySchema dbprpTmainCasualty1 = new PrpTmainCasualtySchema();
    	dbprpTmainCasualty1.setProposalNo(C_PLY_NO);         /*XX单号*/
    	dbprpTmainCasualty1.setRiskCode(C_INSRNC_CDE);       /*XXXXX种代码*/
    	dbprpTmainCasualty1.setBusinessGrade("1");           /*XX等级*/
    	return dbprpTmainCasualty1;     
    }
    
    public List createTItemKind(){
    	List titemKinds = new ArrayList();
    	if(C_INSRNC_CDE.equals("2708")){
    		PrpTitemKindSchema prpItemKind1 = new PrpTitemKindSchema();
    		prpItemKind1.setProposalNo(C_PLY_NO);
            prpItemKind1.setRiskCode("2708");
            prpItemKind1.setItemKindNo("1");
            prpItemKind1.setFamilyNo("2");
    		prpItemKind1.setKindCode("106");
            prpItemKind1.setKindName("航空旅客意外伤害XX");
            prpItemKind1.setItemCode("0001");
            prpItemKind1.setItemDetailName("意外伤害");
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
    	}else{
    		PrpTitemKindSchema prpItemKind1 = new PrpTitemKindSchema();
    		prpItemKind1.setProposalNo(C_PLY_NO);
            prpItemKind1.setRiskCode("2721");
            prpItemKind1.setItemKindNo("1");
            prpItemKind1.setFamilyNo("2");
    		prpItemKind1.setKindCode("107");
            prpItemKind1.setKindName("a");
            prpItemKind1.setItemCode("0001");
            prpItemKind1.setItemDetailName("飞机意外伤害意外伤害	");
            prpItemKind1.setStartDate(D_INSBGN_DATE);
            prpItemKind1.setStartHour("0");
            prpItemKind1.setEndDate(D_INSEND_DATE);
            prpItemKind1.setEndHour("24");
            prpItemKind1.setCalculateFlag("Y");
            prpItemKind1.setCurrency("CNY");
            prpItemKind1.setUnitAmount("500000.00");
            prpItemKind1.setQuantity("1.00");
            prpItemKind1.setValue("1.00");
            prpItemKind1.setAmount("500000.00");
            prpItemKind1.setRate("0.04000");
            prpItemKind1.setShortRateFlag("3");
            prpItemKind1.setShortRate("100.0000");	
            prpItemKind1.setDiscount("100.0000");
            prpItemKind1.setPremium(N_PRM);
            prpItemKind1.setFlag("11");    		
    		titemKinds.add(prpItemKind1);
    		
    		
    		PrpTitemKindSchema prpItemKind2 = new PrpTitemKindSchema();
    		prpItemKind2.setProposalNo(C_PLY_NO);
            prpItemKind2.setRiskCode("2721");
            prpItemKind2.setItemKindNo("2");
            prpItemKind2.setFamilyNo("2");
    		prpItemKind2.setKindCode("107");
            prpItemKind2.setKindName("b");
            prpItemKind2.setItemCode("0002");
            prpItemKind2.setItemDetailName("火车（含地铁、轻轨）意外伤害");
            prpItemKind2.setStartDate(D_INSBGN_DATE);
            prpItemKind2.setStartHour("0");
            prpItemKind2.setEndDate(D_INSEND_DATE);
            prpItemKind2.setEndHour("24");
            prpItemKind2.setCalculateFlag("N");
            prpItemKind2.setCurrency("CNY");
            prpItemKind2.setUnitAmount("300000.00");
            prpItemKind2.setQuantity("1.00");
            prpItemKind2.setValue("1.00");
            prpItemKind2.setAmount("300000.00");
            prpItemKind2.setRate("0.00000");
            prpItemKind2.setShortRateFlag("3");
            prpItemKind2.setShortRate("100.0000");
            prpItemKind2.setDiscount("100.0000");
            prpItemKind2.setPremium("0.00");
            prpItemKind2.setFlag("11");    		
    		titemKinds.add(prpItemKind2);
    		
    		
    		
    		PrpTitemKindSchema prpItemKind3 = new PrpTitemKindSchema();
    		prpItemKind3.setProposalNo(C_PLY_NO);
            prpItemKind3.setRiskCode("2721");
            prpItemKind3.setItemKindNo("3");
            prpItemKind3.setFamilyNo("2");
    		prpItemKind3.setKindCode("107");
            prpItemKind3.setKindName("b");
            prpItemKind3.setItemCode("0003");
            prpItemKind3.setItemDetailName("船舶（指客船、渡船、游船）意外伤害");
            prpItemKind3.setStartDate(D_INSBGN_DATE);
            prpItemKind3.setStartHour("0");
            prpItemKind3.setEndDate(D_INSEND_DATE);
            prpItemKind3.setEndHour("24");
            prpItemKind3.setCalculateFlag("N");
            prpItemKind3.setCurrency("CNY");
            prpItemKind3.setUnitAmount("200000.00");
            prpItemKind3.setQuantity("1.00");
            prpItemKind3.setValue("1.00");
            prpItemKind3.setAmount("200000.00");
            prpItemKind3.setRate("0.00000");
            prpItemKind3.setShortRateFlag("3");
            prpItemKind3.setShortRate("100.0000");	
            prpItemKind3.setDiscount("100.0000");
            prpItemKind3.setPremium("0.00");
            prpItemKind3.setFlag("11");    		
    		titemKinds.add(prpItemKind3);
    		
    		
    		PrpTitemKindSchema prpItemKind4 = new PrpTitemKindSchema();
    		prpItemKind4.setProposalNo(C_PLY_NO);
            prpItemKind4.setRiskCode("2721");
            prpItemKind4.setItemKindNo("4");
            prpItemKind4.setFamilyNo("2");
    		prpItemKind4.setKindCode("107");
            prpItemKind4.setKindName("b");	
            prpItemKind4.setItemCode("0004");
            prpItemKind4.setItemDetailName("汽车（含电车、有轨电车）意外伤害");
            prpItemKind4.setStartDate(D_INSBGN_DATE);
            prpItemKind4.setStartHour("0");
            prpItemKind4.setEndDate(D_INSEND_DATE);
            prpItemKind4.setEndHour("24");
            prpItemKind4.setCalculateFlag("N");
            prpItemKind4.setCurrency("CNY");
            prpItemKind4.setUnitAmount("10000.00");
            prpItemKind4.setQuantity("1.00");
            prpItemKind4.setValue("1.00");
            prpItemKind4.setAmount("10000.00");
            prpItemKind4.setRate("0.00000");
            prpItemKind4.setShortRateFlag("3");
						prpItemKind4.setShortRate("100.0000");
            prpItemKind4.setDiscount("100.0000");
            prpItemKind4.setPremium("0.00");
            prpItemKind4.setFlag("11");
    		titemKinds.add(prpItemKind4);
    	}
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
    /*向tmain写数据*/
    public PrpTmainSchema createTmain(){
    	PrpTmainSchema dbprpTmain1 = new PrpTmainSchema();
    	dbprpTmain1.setProposalNo(C_PLY_NO);                 /*XX单号*/
    	dbprpTmain1.setClassCode("27");                      /*XXXXX类*/
    	dbprpTmain1.setRiskCode(C_INSRNC_CDE);               /*XXXXX种代码*/
    	
    	dbprpTmain1.setPolicySort("2");                      /*XX种类*/
    	dbprpTmain1.setPrintNo(C_VCH_NO);                    
    	dbprpTmain1.setBusinessNature("3");                    /*业务来源*/
    	dbprpTmain1.setLanguage("C");  	                     /*语种*/
        dbprpTmain1.setPolicyType("01");                     /*XX类型*/
        dbprpTmain1.setAppliCode(Customer1);                 /*XX人代码*/
        
        dbprpTmain1.setAppliName(C_APP_CNM);                 /*XX人姓名*/
        
        dbprpTmain1.setInsuredCode(Customer1);               /*被XX人代码*/
        dbprpTmain1.setInsuredName(C_APP_CNM);               /*被XX人姓名*/
        
        dbprpTmain1.setOperateDate(D_SIGN_DATE);             /*签单日期*/
        dbprpTmain1.setStartDate(D_INSBGN_DATE);             /*XX起期*/
        dbprpTmain1.setStartHour("0");                       /*起XXXXX小时*/
        dbprpTmain1.setEndDate(D_INSEND_DATE);               /*XX止期*/
        dbprpTmain1.setEndHour("24");                        /*终XXXXX小时*/
        
        dbprpTmain1.setPureRate("0.0000");                   /*净费率*/
        dbprpTmain1.setDisRate(N_CMM_PROP);                  /*手续费比率*/                        
        dbprpTmain1.setDiscount("100.0000");                 /*总折扣率*/
        dbprpTmain1.setCurrency("CNY");                      /*币别*/
        dbprpTmain1.setSumValue("0.00");                     /*XX价值*/
        dbprpTmain1.setSumAmount(N_AMT);                     /*XX*/
        dbprpTmain1.setSumDiscount("0.00");                  /*折扣金额*/
        dbprpTmain1.setSumPremium(N_PRM);                    /*XX*/
        dbprpTmain1.setSumSubPrem("0.00");                   /*俯加XXXXXXX*/
        dbprpTmain1.setSumQuantity("1");                     /*被XX人数*/
        
        dbprpTmain1.setJudicalCode("");                      /**/	
        dbprpTmain1.setJudicalScope("中华人民共和国管辖(港澳台除外)");/*司法管辖*/
        dbprpTmain1.setAutoTransRenewFlag("1");              /*缴费方式*/
        dbprpTmain1.setArgueSolution("1");                   /*争议解决方式*/
        
        dbprpTmain1.setPayTimes("1");                        /*缴费次数*/
        dbprpTmain1.setEndorseTimes("0");                    /*批改次数*/
        dbprpTmain1.setClaimTimes("0");                      /*XXXXX次数*/
        dbprpTmain1.setMakeCom(C_DPT_CDE);                   /*出单机构*/
        dbprpTmain1.setOperateSite("YJDJ");                    /*签单地点*/
        
        dbprpTmain1.setComCode(C_DPT_CDE);                   /*业务归属*/
        dbprpTmain1.setHandlerCode(C_SLS_CDE);               /*经办人代码*/
        dbprpTmain1.setHandler1Code(C_Handler1Code);         /*归属业务员代码*/
        dbprpTmain1.setApproverCode(C_SLS_CDE);              /*复合人代码*/
        
        
        dbprpTmain1.setOperatorCode(C_SLS_CDE);              /*操作员代码*/
        dbprpTmain1.setInputDate(C_CRT_TM);                  /*计算机出单日期*/
        dbprpTmain1.setInputHour("0");                       /*计算机出单小时*/
        
        	
        
        dbprpTmain1.setAgentCode(C_AGT_CDE);                 /*代理人编码*/
        dbprpTmain1.setCoinsFlag("0");                       /*联共XXXXX标志*/
        
        dbprpTmain1.setAllinsFlag("2");                      /*统XXXXX标志*/
        dbprpTmain1.setUnderWriteFlag("0");                  /*核XXXXX标志*/
        dbprpTmain1.setOthFlag("000000YY000000000000");      /*其他标志字段*/
        
        dbprpTmain1.setDisRate1("0.0000");                   /*超出部分手续费比例*/
        dbprpTmain1.setBusinessFlag("0");                    /*业务类型*/
        
        dbprpTmain1.setUpdaterCode(C_SLS_CDE);               /*最近更新人*/
        dbprpTmain1.setUpdateDate(D_SIGN_DATE);              /*最近更新日期*/
        dbprpTmain1.setUpdateHour("0");                      /*最近更新小时*/
        dbprpTmain1.setSignDate(D_SIGN_DATE);                /**/
        dbprpTmain1.setShareHolderFlag("0");                 /**/
        dbprpTmain1.setAgreementNo(C_AGT_NO);                /*代理协议号*/
        
        dbprpTmain1.setPayMode("1");                         /*支付方式*/
        if(C_REMARK!=null){
        	dbprpTmain1.setRemark(C_REMARK);                 /*备注*/
        }
        
        
        
        dbprpTmain1.setVisaCode(C_VCH_TYP);                  /*单证类型*/
        dbprpTmain1.setManualType("0");                      /**/
        return dbprpTmain1;
    }
}