package com.sp.phonesale.trans;
import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import oracle.jdbc.driver.OracleDriver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DataToHeXinJC extends TimerTask{
	protected final Log logger = LogFactory.getLog(getClass());
    
    public static void main(String[] args) {
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate((TimerTask) new DataToHeXinJC(), 2, 1000*60); 
    }
    
    public  void run() {
        
        logger.info("GETTMLDATA IS RUNNING!");
        
        Connection conn1 =null;
        Connection conn2 =null;
        PreparedStatement psTML = null;
        ResultSet rsTML = null;
        PreparedStatement psInterFaceTML = null;
        try{
            java.util.Properties syspro = new java.util.Properties();
            try {
                syspro.load(this.getClass().getResourceAsStream(
                        "ApplicationResourcesJC.properties"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            
            try {
                Class.forName(syspro.getProperty("DRIVER")).newInstance();
            } catch (java.lang.ClassNotFoundException e) {
                
                logger.error("ClassNotFoundException: " + e.getMessage());
                
            }
            
            String URL = syspro.getProperty("MSSQLURL");
            String user = syspro.getProperty("MSSQLUSER");
            String password = syspro.getProperty("MSSQLPASSWORD");
            conn1 = DriverManager.getConnection(URL, user, password);
            
            
            
            try {
                Class.forName("oracle.jdbc.driver.OracleDriver")
                        .newInstance();
            } catch (java.lang.ClassNotFoundException e) {
                
                logger.error("ClassNotFoundException: "
                        + e.getMessage());
                
            }
            
            try {
                syspro.load(this.getClass().getResourceAsStream(
                        "ApplicationResourcesJC.properties"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            String URL1 = syspro.getProperty("ORACLEURL");
            String user1 = syspro.getProperty("ORACLEUSER");
            String password1 = syspro.getProperty("ORACLEPASSWORD");
            conn2 = DriverManager.getConnection(URL1, user1, password1);
            
            
            StringBuffer sqlTMLCCont = new StringBuffer();
            StringBuffer sqlUpTMLCCont = new StringBuffer();
            StringBuffer sqlInTMLCCont = new StringBuffer();
            sqlTMLCCont.append(syspro.getProperty("QUTMLCCONTSQL"));
            sqlInTMLCCont.append(syspro.getProperty("INTMLCCONTSQL"));
            sqlUpTMLCCont.append(syspro.getProperty("UPTMLCCONTSQL"));
            
            psTML = (PreparedStatement) conn1.prepareStatement(sqlTMLCCont.toString());
            rsTML = psTML.executeQuery();
            ArrayList TMLCContList = new ArrayList();
            
            
            while (rsTML.next()) {
                HashMap row = new HashMap();
                String ContNo           = (String)rsTML.getString("ContNo");
                String PrtNo            = (String)rsTML.getString("PrtNo");
                String PolApplyDate     = (String)rsTML.getString("POLAPPLYDATE_NEW");
                String CValiDate        = (String)rsTML.getString("CVALIDATE_NEW");
                String AgentCode        = (String)rsTML.getString("AgentCode");
                String AgentName        = (String)rsTML.getString("AgentName");
                String AgentCom         = (String)rsTML.getString("AgentCom");
                String AgentGroup       = (String)rsTML.getString("AgentGroup");
                String AgentRelatoAppnt = (String)rsTML.getString("AgentRelatoAppnt");
                String ManageCom        = (String)rsTML.getString("ManageCom");
                String SaleChnl         = (String)rsTML.getString("SaleChnl");
                String GetPolDate       = (String)rsTML.getString("GETPOLDATE_NEW");
                String GetPolTime       = (String)rsTML.getString("GetPolTime");
                String CustomGetPolDate = (String)rsTML.getString("CUSTOMGETPOLDATE_NEW");
                String PayLocation      = (String)rsTML.getString("PayLocation");
                String BankCode         = (String)rsTML.getString("BankCode");
                String BankAccNo        = (String)rsTML.getString("BankAccNo");
                String AccName          = (String)rsTML.getString("AccName");
                String NewPayMode       = (String)rsTML.getString("NewPayMode");
                String NewBankCode      = (String)rsTML.getString("NewBankCode");
                String NewBankAccNo     = (String)rsTML.getString("NewBankAccNo");
                String NewAccName       = (String)rsTML.getString("NewAccName");
                String AgentBankCode    = (String)rsTML.getString("AgentBankCode");
                String BankAgent        = (String)rsTML.getString("BankAgent");
                String Remark           = (String)rsTML.getString("Remark");
                String State            = (String)rsTML.getString("State");
                
                String CustomerNo       = (String)rsTML.getString("CustomerNo");
                String YearPrem         = (String)rsTML.getString("YearPrem");
                String PayIntv          = (String)rsTML.getString("PayIntv");
                String polstate         = (String)rsTML.getString("polstate");
                String posted           = (String)rsTML.getString("posted");
                String extpaymode       = (String)rsTML.getString("extpaymode");
                
                String riskCode         = (String)rsTML.getString("RISKCODE");
                String sumAmount        = (String)rsTML.getString("SUMAMOUNT");
                
                
                String channelType      = (String)rsTML.getString("channelType");
                
                row.put("ContNo",ContNo);
                row.put("PrtNo",PrtNo);
                row.put("PolApplyDate",PolApplyDate);
                row.put("CValiDate",CValiDate);
                row.put("AgentCode",AgentCode);
                row.put("AgentName",AgentName);
                row.put("AgentCom",AgentCom);
                row.put("AgentGroup",AgentGroup);
                row.put("AgentRelatoAppnt",AgentRelatoAppnt);
                row.put("ManageCom",ManageCom);
                row.put("SaleChnl",SaleChnl);
                row.put("GetPolDate",GetPolDate);
                row.put("GetPolTime",GetPolTime);
                row.put("CustomGetPolDate",CustomGetPolDate);
                row.put("PayLocation",PayLocation);
                row.put("BankCode",BankCode);
                row.put("BankAccNo",BankAccNo);
                row.put("AccName",AccName);
                row.put("NewPayMode",NewPayMode);
                row.put("NewBankCode",NewBankCode);
                row.put("NewBankAccNo",NewBankAccNo);
                row.put("NewAccName",NewAccName);
                row.put("AgentBankCode",AgentBankCode);
                row.put("BankAgent",BankAgent);
                row.put("Remark",Remark);
                row.put("State",State);
                row.put("CustomerNo",CustomerNo);
                row.put("YearPrem",YearPrem);
                row.put("PayIntv",PayIntv);
                row.put("polstate",polstate);
                row.put("posted",posted);
                row.put("extpaymode",extpaymode);
                
                row.put("RiskCode",riskCode);
                row.put("SumAmount",sumAmount);
                
                
                row.put("channelType",channelType);
                
                TMLCContList.add(row);
            }
            
            for(int j=0;j<TMLCContList.size();j++){
                HashMap mapTemp1 = new HashMap();
                mapTemp1 = (HashMap) TMLCContList.get(j);
                String ContNoTemp           = (String)mapTemp1.get("ContNo");
                
                logger.info("....."+ContNoTemp);
                
                
                StringBuffer sqlTMLCAppnt = new StringBuffer();
                StringBuffer sqlInTMLCAppnt = new StringBuffer();
                sqlTMLCAppnt.append(syspro.getProperty("QUTMLCAPPNTSQL"));
                sqlTMLCAppnt.append("  and ContNo='"+ContNoTemp+"'");
                sqlInTMLCAppnt.append(syspro.getProperty("INTMLCAPPNTSQL"));
                ArrayList TMLCAppntList = new ArrayList();
                psTML = (PreparedStatement) conn1.prepareStatement(sqlTMLCAppnt.toString());
                rsTML = psTML.executeQuery();
                while (rsTML.next()) {
                  
                  HashMap row = new HashMap();
                  String ContNo         = (String)rsTML.getString("ContNo");        
                  String CustomerNo     = (String)rsTML.getString("CustomerNo");    
                  String AppntName      = (String)rsTML.getString("AppntName");     
                  String AppntSex       = (String)rsTML.getString("AppntSex");      
                  String AppntBirthday  = (String)rsTML.getString("APPNTBIRTHDAY_NEW"); 
                  String IDType         = (String)rsTML.getString("IDType");        
                  String IDNo           = (String)rsTML.getString("IDNo");          
                  String Marriage       = (String)rsTML.getString("Marriage");      
                  String Degree         = (String)rsTML.getString("Degree");        
                  String NativePlace    = (String)rsTML.getString("NativePlace");   
                  String RgtAddress     = (String)rsTML.getString("RgtAddress");    
                  String Nationality    = (String)rsTML.getString("Nationality");   
                  String WorkType       = (String)rsTML.getString("WorkType");      
                  String OccupationType = (String)rsTML.getString("OccupationType");
                  String OccupationCode = (String)rsTML.getString("OccupationCode");
                  String OccupationName = (String)rsTML.getString("OccupationName");
                  String PluralityType  = (String)rsTML.getString("PluralityType"); 
                  String License        = (String)rsTML.getString("License");       
                  String LicenseType    = (String)rsTML.getString("LicenseType");
                  
                  String RelationToInsured    = (String)rsTML.getString("RelationToInsured");
                  String EMail                = (String)rsTML.getString("EMail");
                  String CAddress             = (String)rsTML.getString("caddressall");
                  String CZipCode             = (String)rsTML.getString("CZipCode");
                  String CPhone               = (String)rsTML.getString("CPhone");
                  String polstate             = "06";
                  String posted               = (String)rsTML.getString("posted");
                  row.put("ContNo",ContNo);
                  row.put("CustomerNo",CustomerNo);
                  row.put("AppntName",AppntName);
                  row.put("AppntSex",AppntSex);
                  row.put("AppntBirthday",AppntBirthday);
                  row.put("IDType",IDType);
                  row.put("IDNo",IDNo);
                  row.put("Marriage",Marriage);
                  row.put("Degree",Degree);
                  row.put("NativePlace",NativePlace);
                  row.put("RgtAddress",RgtAddress);
                  row.put("Nationality",Nationality);
                  row.put("WorkType",WorkType);
                  row.put("OccupationType",OccupationType);
                  row.put("OccupationCode",OccupationCode);
                  row.put("OccupationName",OccupationName);
                  row.put("PluralityType",PluralityType);
                  row.put("License",License);
                  row.put("LicenseType",LicenseType);
                  row.put("RelationToInsured",RelationToInsured);
                  row.put("EMail",EMail);
                  row.put("CAddress",CAddress);
                  row.put("CZipCode",CZipCode);
                  row.put("CPhone",CPhone);
                  row.put("polstate",polstate);
                  row.put("posted",posted);
                  TMLCAppntList.add(row);
                }
                
                
                ArrayList TMLCInsuredList = new ArrayList();
                StringBuffer sqlTMLCInsured = new StringBuffer();
                StringBuffer sqlInTMLCInsured = new StringBuffer();
                sqlTMLCInsured.append(syspro.getProperty("QUTMLCINSUREDSQL"));
                sqlTMLCInsured.append("  and ContNo='"+ContNoTemp+"'");
                sqlInTMLCInsured.append(syspro.getProperty("INTMLCINSUREDSQL"));
                psTML = (PreparedStatement) conn1.prepareStatement(sqlTMLCInsured.toString());
                rsTML = psTML.executeQuery();
                while (rsTML.next()) {
                  HashMap row = new HashMap();
                  String ContNo                 = (String)rsTML.getString("ContNo");               
                  String CustomerNo             = (String)rsTML.getString("CustomerNo");           
                  String Name                   = (String)rsTML.getString("Name");                 
                  String Sex                    = (String)rsTML.getString("Sex");                  
                  String Birthday               = (String)rsTML.getString("BIRTHDAY_NEW");             
                  String IDType                 = (String)rsTML.getString("IDType");               
                  String IDNo                   = (String)rsTML.getString("IDNo");                 
                  String Marriage               = (String)rsTML.getString("Marriage");             
                  String Degree                 = (String)rsTML.getString("Degree");               
                  String NativePlace            = (String)rsTML.getString("NativePlace");          
                  String RgtAddress             = (String)rsTML.getString("RgtAddress");           
                  String Nationality            = (String)rsTML.getString("Nationality");          
                  String WorkType               = (String)rsTML.getString("WorkType");             
                  String OccupationType         = (String)rsTML.getString("OccupationType");       
                  String OccupationCode         = (String)rsTML.getString("OccupationCode");       
                  String OccupationName         = (String)rsTML.getString("OccupationName");       
                  String PluralityType          = (String)rsTML.getString("PluralityType");        
                  String License                = (String)rsTML.getString("License");              
                  String LicenseType            = (String)rsTML.getString("LicenseType");          
                  String SequenceNo             = (String)rsTML.getString("SequenceNo");           
                  String RelationToMainInsured  = (String)rsTML.getString("RelationToMainInsured");
                  String RelationToAppnt        = (String)rsTML.getString("RelationToAppnt"); 
                  String EMail                  = (String)rsTML.getString("EMail");
                  String CAddress               = (String)rsTML.getString("caddressall");
                  String CZipCode               = (String)rsTML.getString("CZipCode");
                  String CPhone                 = (String)rsTML.getString("CPhone");
                  String polstate               = "06";
                  String posted                 = (String)rsTML.getString("posted");
                  String insuredAge             = "";
                  String insuredNo             =  (String)rsTML.getString("insuredno");
                  row.put("ContNo",ContNo);
                  row.put("CustomerNo",CustomerNo);
                  row.put("Name",Name);
                  row.put("Sex",Sex);
                  row.put("Birthday",Birthday);
                  row.put("IDType",IDType);
                  row.put("IDNo",IDNo);
                  row.put("Marriage",Marriage);
                  row.put("Degree",Degree);
                  row.put("NativePlace",NativePlace);
                  row.put("RgtAddress",RgtAddress);
                  row.put("Nationality",Nationality);
                  row.put("WorkType",WorkType);
                  row.put("OccupationType",OccupationType);
                  row.put("OccupationCode",OccupationCode);
                  row.put("OccupationName",OccupationName);
                  row.put("PluralityType",PluralityType);
                  row.put("License",License);
                  row.put("LicenseType",LicenseType);
                  row.put("SequenceNo",SequenceNo);
                  row.put("RelationToMainInsured",RelationToMainInsured);
                  row.put("RelationToAppnt",RelationToAppnt);
                  row.put("EMail",EMail);
                  row.put("CAddress",CAddress);
                  row.put("CZipCode",CZipCode);
                  row.put("CPhone",CPhone);
                  row.put("polstate",polstate);
                  row.put("posted",posted);
                  row.put("insuredAge",insuredAge);
                  row.put("insuredNo",insuredNo);
                  TMLCInsuredList.add(row);
                }
                
                
                ArrayList TMLCPolList = new ArrayList();
                StringBuffer sqlTMLCPol = new StringBuffer();
                StringBuffer sqlInTMLCPol = new StringBuffer();
                sqlTMLCPol.append(syspro.getProperty("QUTMLCPOLSQL"));
                sqlTMLCPol.append("  and ContNo='"+ContNoTemp+"'");
                sqlInTMLCPol.append(syspro.getProperty("INTMLCPOLSQL"));   
                psTML = (PreparedStatement) conn1.prepareStatement(sqlTMLCPol.toString());
                rsTML = psTML.executeQuery();
                while (rsTML.next()) {
                  HashMap row = new HashMap();
                  String ContNo           = (String)rsTML.getString("ContNo");        
                  String RiskCode         = (String)rsTML.getString("RiskCode");      
                  String MainRiskCode     = (String)rsTML.getString("MainRiskCode");  
                  String PolNo            = (String)rsTML.getString("PolNo");         
                  String Amnt             = (String)rsTML.getString("Amnt");          
                  String Prem             = (String)rsTML.getString("Prem");          
                  String Mult             = (String)rsTML.getString("Mult");          
                  String RnewFlag         = (String)rsTML.getString("RnewFlag");      
                  String PayIntv          = (String)rsTML.getString("PayIntv");       
                  String PayYears         = (String)rsTML.getString("PayYears");      
                  String InsuYearFlag     = (String)rsTML.getString("InsuYearFlag");  
                  String InsuYear         = (String)rsTML.getString("InsuYear");      
                  String PayEndYearFlag   = (String)rsTML.getString("PayEndYearFlag");
                  String PayEndYear       = (String)rsTML.getString("PayEndYear");    
                  String GetBankCode      = (String)rsTML.getString("GetBankCode");   
                  String GetBankAccNo     = (String)rsTML.getString("GetBankAccNo");  
                  String GetAccName       = (String)rsTML.getString("GetAccName");    
                  String AutoPayFlag      = (String)rsTML.getString("AutoPayFlag");   
                  String BonusGetMode     = (String)rsTML.getString("BonusGetMode");  
                  String SubFlag          = (String)rsTML.getString("SubFlag");       
                  String CustomerNo       = (String)rsTML.getString("CustomerNo");    
                  row.put("ContNo",ContNo);
                  row.put("RiskCode",RiskCode);
                  row.put("MainRiskCode",MainRiskCode);
                  row.put("PolNo",PolNo);
                  row.put("Amnt",Amnt);
                  row.put("Prem",Prem);
                  row.put("Mult",Mult);
                  row.put("RnewFlag",RnewFlag);
                  row.put("PayIntv",PayIntv);
                  row.put("PayYears",PayYears);
                  row.put("InsuYearFlag",InsuYearFlag);
                  row.put("InsuYear",InsuYear);
                  row.put("PayEndYearFlag",PayEndYearFlag);
                  row.put("PayEndYear",PayEndYear);
                  row.put("GetBankCode",GetBankCode);
                  row.put("GetBankAccNo",GetBankAccNo);
                  row.put("GetAccName",GetAccName);
                  row.put("AutoPayFlag",AutoPayFlag);
                  row.put("BonusGetMode",BonusGetMode);
                  row.put("SubFlag",SubFlag);
                  row.put("CustomerNo",CustomerNo);
                  TMLCPolList.add(row);
                }
                
                
                ArrayList TMLCBnfList = new ArrayList();
                StringBuffer sqlTMLCBnf = new StringBuffer();
                StringBuffer sqlInTMLCBnf = new StringBuffer();
                sqlTMLCBnf.append(syspro.getProperty("QUTMLCBNFSQL"));
                sqlTMLCBnf.append("  and ContNo='"+ContNoTemp+"'");
                sqlInTMLCBnf.append(syspro.getProperty("INTMLCBNFSQL"));   
                psTML = (PreparedStatement) conn1.prepareStatement(sqlTMLCBnf.toString());
                rsTML = psTML.executeQuery();
                while (rsTML.next()) {
                  HashMap row = new HashMap();
                  String ContNo               = (String)rsTML.getString("ContNo");
                  String PolNo                = (String)rsTML.getString("PolNo");
                  String BnfType              = (String)rsTML.getString("BnfType");
                  String BnfNo                = (String)rsTML.getString("BnfNo");
                  String CustomerNo           = (String)rsTML.getString("CustomerNo");
                  String RelationToInsured    = (String)rsTML.getString("RelationToInsured");
                  String Name                 = (String)rsTML.getString("Name");
                  String Sex                  = (String)rsTML.getString("Sex");
                  String Birthday             = (String)rsTML.getString("BIRTHDAY_NEW");
                  String IDType               = (String)rsTML.getString("IDType");
                  String IDNo                 = (String)rsTML.getString("IDNo");
                  String BnfGrade             = (String)rsTML.getString("BnfGrade");
                  String BnfLot               = (String)rsTML.getString("BnfLot");
                  row.put("ContNo",ContNo);
                  row.put("PolNo",PolNo);
                  row.put("BnfType",BnfType);
                  row.put("BnfNo",BnfNo);
                  row.put("CustomerNo",CustomerNo);
                  row.put("RelationToInsured",RelationToInsured);
                  row.put("Name",Name);
                  row.put("Sex",Sex);
                  row.put("Birthday",Birthday);
                  row.put("IDType",IDType);
                  row.put("IDNo",IDNo);
                  row.put("BnfGrade",BnfGrade);
                  row.put("BnfLot",BnfLot);
                  TMLCBnfList.add(row);
                }
                
                
                
                ArrayList TMLCinuredPCList = new ArrayList();
                StringBuffer sqlTMLCinuredPC = new StringBuffer();
                StringBuffer sqlInTMLCinuredPC = new StringBuffer();
                sqlTMLCinuredPC.append(syspro.getProperty("QUTMLCINSUREDPCSQL"));
                sqlTMLCinuredPC.append("  and ContNo='"+ContNoTemp+"'");
                sqlInTMLCinuredPC.append(syspro.getProperty("INTMLCINSUREDPCSQL"));   
                psTML = (PreparedStatement) conn1.prepareStatement(sqlTMLCinuredPC.toString());
                rsTML = psTML.executeQuery();
                while (rsTML.next()) {
                  HashMap row = new HashMap();
                  String contno                 = (String)rsTML.getString("contno");
                  String address                = (String)rsTML.getString("address");
                  String postCode               = (String)rsTML.getString("postCode");
                  row.put("contno",contno);
                  row.put("address",address);
                  row.put("postCode",postCode);
                  TMLCinuredPCList.add(row);
                }
                
                






























                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                try{
                    conn2.setAutoCommit(false);
                    HashMap mapTempCont = new HashMap();
                    mapTempCont = (HashMap) TMLCContList.get(j);
                    String mainContNo = (String) mapTempCont.get("ContNo");
                    String PrtNo = (String) mapTempCont.get("PrtNo");
                    String PolApplyDate = (String) mapTempCont.get("PolApplyDate");
                    String CValiDate = (String) mapTempCont.get("CValiDate");
                    String AgentCode = (String) mapTempCont.get("AgentCode");
                    String AgentName = (String) mapTempCont.get("AgentName");
                    String AgentCom = (String) mapTempCont.get("AgentCom");
                    String AgentGroup = (String) mapTempCont.get("AgentGroup");
                    String AgentRelatoAppnt = (String) mapTempCont.get("AgentRelatoAppnt");
                    String ManageCom = (String) mapTempCont.get("ManageCom");
                    String SaleChnl = (String) mapTempCont.get("SaleChnl");
                    String GetPolDate = (String) mapTempCont.get("GetPolDate");
                    String GetPolTime = (String) mapTempCont.get("GetPolTime");
                    String CustomGetPolDate = (String) mapTempCont.get("CustomGetPolDate");
                    String PayLocation = (String) mapTempCont.get("PayLocation");
                    String BankCode = (String) mapTempCont.get("BankCode");
                    String BankAccNo = (String) mapTempCont.get("BankAccNo");
                    String AccName = (String) mapTempCont.get("AccName");
                    String NewPayMode = (String) mapTempCont.get("NewPayMode");
                    String NewBankCode = (String) mapTempCont.get("NewBankCode");
                    String NewBankAccNo = (String) mapTempCont.get("NewBankAccNo");
                    String NewAccName = (String) mapTempCont.get("NewAccName");
                    String AgentBankCode = (String) mapTempCont.get("AgentBankCode");
                    String BankAgent = (String) mapTempCont.get("BankAgent");
                    String Remark = (String) mapTempCont.get("Remark");
                    String State = (String) mapTempCont.get("State");

                    String mainCustomerNo = (String) mapTempCont.get("CustomerNo");
                    String YearPrem = (String) mapTempCont.get("YearPrem");
                    String mainPayIntv = (String) mapTempCont.get("PayIntv");
                    String mainpolstate = (String) mapTempCont.get("polstate");
                    String mainposted = (String) mapTempCont.get("posted");
                    String extpaymode = (String) mapTempCont.get("extpaymode");
                    
                    String riskcode = (String) mapTempCont.get("RiskCode");
                    String sumamount = (String) mapTempCont.get("SumAmount");
                    
                    
                    String channelType = (String) mapTempCont.get("channelType");
                    
                    psInterFaceTML = (PreparedStatement) conn2.prepareStatement(sqlInTMLCCont.toString());
                    psInterFaceTML.setString(1, mainContNo);
                    psInterFaceTML.setString(2, PrtNo);
                    psInterFaceTML.setString(3, PolApplyDate);
                    psInterFaceTML.setString(4, CValiDate);
                    psInterFaceTML.setString(5, AgentCode);
                    psInterFaceTML.setString(6, AgentName);
                    psInterFaceTML.setString(7, AgentCom);
                    psInterFaceTML.setString(8, AgentGroup);
                    psInterFaceTML.setString(9, AgentRelatoAppnt);
                    psInterFaceTML.setString(10, ManageCom);
                    psInterFaceTML.setString(11, SaleChnl);
                    psInterFaceTML.setString(12, GetPolDate);
                    psInterFaceTML.setString(13, GetPolTime);
                    psInterFaceTML.setString(14, CustomGetPolDate);
                    psInterFaceTML.setString(15, PayLocation);
                    psInterFaceTML.setString(16, BankCode);
                    psInterFaceTML.setString(17, BankAccNo);
                    psInterFaceTML.setString(18, AccName);
                    psInterFaceTML.setString(19, NewPayMode);
                    psInterFaceTML.setString(20, NewBankCode);
                    psInterFaceTML.setString(21, NewBankAccNo);
                    psInterFaceTML.setString(22, NewAccName);
                    psInterFaceTML.setString(23, AgentBankCode);
                    psInterFaceTML.setString(24, BankAgent);
                    psInterFaceTML.setString(25, Remark);
                    psInterFaceTML.setString(26, State);
                    psInterFaceTML.setString(27, mainCustomerNo);
                    psInterFaceTML.setString(28, YearPrem);
                    psInterFaceTML.setString(29, mainPayIntv);
                    psInterFaceTML.setString(30, mainpolstate);
                    psInterFaceTML.setString(31, mainposted);
                    psInterFaceTML.setString(32, extpaymode);
                    
                    psInterFaceTML.setString(33, riskcode);
                    psInterFaceTML.setString(34, sumamount);
                    
                    
                    psInterFaceTML.setString(35, channelType); 
                    
                    psInterFaceTML.execute();

                    
                    for (int i = 0; i < TMLCAppntList.size(); i++) {
                        HashMap mapTemp = new HashMap();
                        mapTemp = (HashMap) TMLCAppntList.get(i);
                        String ContNo = (String) mapTemp.get("ContNo");
                        String CustomerNo = (String) mapTemp.get("CustomerNo");
                        String AppntName = (String) mapTemp.get("AppntName");
                        String AppntSex = (String) mapTemp.get("AppntSex");
                        String AppntBirthday = (String) mapTemp.get("AppntBirthday");
                        String IDType = (String) mapTemp.get("IDType");
                        String IDNo = (String) mapTemp.get("IDNo");
                        String Marriage = (String) mapTemp.get("Marriage");
                        String Degree = (String) mapTemp.get("Degree");
                        String NativePlace = (String) mapTemp.get("NativePlace");
                        String RgtAddress = (String) mapTemp.get("RgtAddress");
                        String Nationality = (String) mapTemp.get("Nationality");
                        String WorkType = (String) mapTemp.get("WorkType");
                        String OccupationType = (String) mapTemp.get("OccupationType");
                        String OccupationCode = (String) mapTemp.get("OccupationCode");
                        String OccupationName = (String) mapTemp.get("OccupationName");
                        String PluralityType = (String) mapTemp.get("PluralityType");
                        String License = (String) mapTemp.get("License");
                        String LicenseType = (String) mapTemp.get("LicenseType");

                        String RelationToInsured = (String) mapTemp.get("RelationToInsured");
                        String EMail = (String) mapTemp.get("EMail");
                        String CAddress = (String) mapTemp.get("CAddress");
                        String CZipCode = (String) mapTemp.get("CZipCode");
                        String CPhone = (String) mapTemp.get("CPhone");
                        String polstate = (String) mapTemp.get("polstate");
                        String posted = (String) mapTemp.get("posted");
                        psInterFaceTML = (PreparedStatement) conn2.prepareStatement(sqlInTMLCAppnt.toString());
                        psInterFaceTML.setString(1, ContNo);
                        psInterFaceTML.setString(2, CustomerNo);
                        psInterFaceTML.setString(3, AppntName);
                        psInterFaceTML.setString(4, AppntSex);
                        psInterFaceTML.setString(5, AppntBirthday);
                        psInterFaceTML.setString(6, IDType);
                        psInterFaceTML.setString(7, IDNo);
                        psInterFaceTML.setString(8, Marriage);
                        psInterFaceTML.setString(9, Degree);
                        psInterFaceTML.setString(10, NativePlace);
                        psInterFaceTML.setString(11, RgtAddress);
                        psInterFaceTML.setString(12, Nationality);
                        psInterFaceTML.setString(13, WorkType);
                        psInterFaceTML.setString(14, OccupationType);
                        psInterFaceTML.setString(15, OccupationCode);
                        psInterFaceTML.setString(16, OccupationName);
                        psInterFaceTML.setString(17, PluralityType);
                        psInterFaceTML.setString(18, License);
                        psInterFaceTML.setString(19, LicenseType);

                        psInterFaceTML.setString(20, RelationToInsured);
                        psInterFaceTML.setString(21, EMail);
                        psInterFaceTML.setString(22, CAddress);
                        psInterFaceTML.setString(23, CZipCode);
                        psInterFaceTML.setString(24, CPhone);
                        psInterFaceTML.setString(25, polstate);
                        psInterFaceTML.setString(26, posted);
                        psInterFaceTML.execute();
                    }
                    
                    for (int i = 0; i < TMLCInsuredList.size(); i++) {
                        HashMap mapTemp = new HashMap();
                        mapTemp = (HashMap) TMLCInsuredList.get(i);
                        String ContNo = (String) mapTemp.get("ContNo");
                        String CustomerNo = (String) mapTemp.get("CustomerNo");
                        String Name = (String) mapTemp.get("Name");
                        String Sex = (String) mapTemp.get("Sex");
                        String Birthday = (String) mapTemp.get("Birthday");
                        String IDType = (String) mapTemp.get("IDType");
                        String IDNo = (String) mapTemp.get("IDNo");
                        String Marriage = (String) mapTemp.get("Marriage");
                        String Degree = (String) mapTemp.get("Degree");
                        String NativePlace = (String) mapTemp.get("NativePlace");
                        String RgtAddress = (String) mapTemp.get("RgtAddress");
                        String Nationality = (String) mapTemp.get("Nationality");
                        String WorkType = (String) mapTemp.get("WorkType");
                        String OccupationType = (String) mapTemp.get("OccupationType");
                        String OccupationCode = (String) mapTemp.get("OccupationCode");
                        String OccupationName = (String) mapTemp.get("OccupationName");
                        String PluralityType = (String) mapTemp.get("PluralityType");
                        String License = (String) mapTemp.get("License");
                        String LicenseType = (String) mapTemp.get("LicenseType");
                        String SequenceNo = (String) mapTemp.get("SequenceNo");
                        String RelationToMainInsured = (String) mapTemp .get("RelationToMainInsured");
                        String RelationToAppnt = (String) mapTemp.get("RelationToAppnt");
                        String EMail = (String) mapTemp.get("EMail");
                        String CAddress = (String) mapTemp.get("CAddress");
                        String CZipCode = (String) mapTemp.get("CZipCode");
                        String CPhone = (String) mapTemp.get("CPhone");
                        String polstate = (String) mapTemp.get("polstate");
                        String posted = (String) mapTemp.get("posted");
                        String insuredAge = (String) mapTemp.get("insuredAge");
                        String strinsuredNo  = (String) mapTemp.get("insuredNo");
                        psInterFaceTML = (PreparedStatement) conn2.prepareStatement(sqlInTMLCInsured.toString());
                        psInterFaceTML.setString(1, ContNo);
                        psInterFaceTML.setString(2, CustomerNo);
                        psInterFaceTML.setString(3, Name);
                        psInterFaceTML.setString(4, Sex);
                        psInterFaceTML.setString(5, Birthday);
                        psInterFaceTML.setString(6, IDType);
                        psInterFaceTML.setString(7, IDNo);
                        psInterFaceTML.setString(8, Marriage);
                        psInterFaceTML.setString(9, Degree);
                        psInterFaceTML.setString(10, NativePlace);
                        psInterFaceTML.setString(11, RgtAddress);
                        psInterFaceTML.setString(12, Nationality);
                        psInterFaceTML.setString(13, WorkType);
                        psInterFaceTML.setString(14, OccupationType);
                        psInterFaceTML.setString(15, OccupationCode);
                        psInterFaceTML.setString(16, OccupationName);
                        psInterFaceTML.setString(17, PluralityType);
                        psInterFaceTML.setString(18, License);
                        psInterFaceTML.setString(19, LicenseType);
                        psInterFaceTML.setString(20, SequenceNo);
                        psInterFaceTML.setString(21, RelationToMainInsured);
                        psInterFaceTML.setString(22, RelationToAppnt);

                        psInterFaceTML.setString(23, EMail);
                        psInterFaceTML.setString(24, CAddress);
                        psInterFaceTML.setString(25, CZipCode);
                        psInterFaceTML.setString(26, CPhone);
                        psInterFaceTML.setString(27, polstate);
                        psInterFaceTML.setString(28, posted);
                        psInterFaceTML.setString(29, insuredAge);
                        psInterFaceTML.setString(30, strinsuredNo);
                        psInterFaceTML.execute();
                    }
                    
                    for (int i = 0; i < TMLCPolList.size(); i++) {
                        HashMap mapTemp = new HashMap();
                        mapTemp = (HashMap) TMLCPolList.get(i);
                        String ContNo = (String) mapTemp.get("ContNo");
                        String RiskCode = (String) mapTemp.get("RiskCode");
                        String MainRiskCode = (String) mapTemp.get("MainRiskCode");
                        String PolNo = (String) mapTemp.get("PolNo");
                        String Amnt = (String) mapTemp.get("Amnt");
                        String Prem = (String) mapTemp.get("Prem");
                        String Mult = (String) mapTemp.get("Mult");
                        String RnewFlag = (String) mapTemp.get("RnewFlag");
                        String PayIntv = (String) mapTemp.get("PayIntv");
                        String PayYears = (String) mapTemp.get("PayYears");
                        String InsuYearFlag = (String) mapTemp.get("InsuYearFlag");
                        String InsuYear = (String) mapTemp.get("InsuYear");
                        String PayEndYearFlag = (String) mapTemp.get("PayEndYearFlag");
                        String PayEndYear = (String) mapTemp.get("PayEndYear");
                        String GetBankCode = (String) mapTemp.get("GetBankCode");
                        String GetBankAccNo = (String) mapTemp.get("GetBankAccNo");
                        String GetAccName = (String) mapTemp.get("GetAccName");
                        String AutoPayFlag = (String) mapTemp.get("AutoPayFlag");
                        String BonusGetMode = (String) mapTemp.get("BonusGetMode");
                        String SubFlag = (String) mapTemp.get("SubFlag");
                        String CustomerNo = (String) mapTemp.get("CustomerNo");
                        psInterFaceTML = (PreparedStatement) conn2.prepareStatement(sqlInTMLCPol.toString());
                        psInterFaceTML.setString(1, ContNo);
                        psInterFaceTML.setString(2, RiskCode);
                        psInterFaceTML.setString(3, MainRiskCode);
                        psInterFaceTML.setString(4, PolNo);
                        psInterFaceTML.setString(5, Amnt);
                        psInterFaceTML.setString(6, Prem);
                        psInterFaceTML.setString(7, Mult);
                        psInterFaceTML.setString(8, RnewFlag);
                        psInterFaceTML.setString(9, PayIntv);
                        psInterFaceTML.setString(10, PayYears);
                        psInterFaceTML.setString(11, InsuYearFlag);
                        psInterFaceTML.setString(12, InsuYear);
                        psInterFaceTML.setString(13, PayEndYearFlag);
                        psInterFaceTML.setString(14, PayEndYear);
                        psInterFaceTML.setString(15, GetBankCode);
                        psInterFaceTML.setString(16, GetBankAccNo);
                        psInterFaceTML.setString(17, GetAccName);
                        psInterFaceTML.setString(18, AutoPayFlag);
                        psInterFaceTML.setString(19, BonusGetMode);
                        psInterFaceTML.setString(20, SubFlag);
                        psInterFaceTML.setString(21, CustomerNo);
                        psInterFaceTML.execute();

                    }
                    
                    for (int i = 0; i < TMLCBnfList.size(); i++) {
                        HashMap mapTemp = new HashMap();
                        mapTemp = (HashMap) TMLCBnfList.get(i);
                        String ContNo = (String) mapTemp.get("ContNo");
                        String PolNo = (String) mapTemp.get("PolNo");
                        String BnfType = (String) mapTemp.get("BnfType");
                        String BnfNo = (String) mapTemp.get("BnfNo");
                        String CustomerNo = (String) mapTemp.get("CustomerNo");
                        String RelationToInsured = (String) mapTemp.get("RelationToInsured");
                        String Name = (String) mapTemp.get("Name");
                        String Sex = (String) mapTemp.get("Sex");
                        String Birthday = (String) mapTemp.get("Birthday");
                        String IDType = (String) mapTemp.get("IDType");
                        String IDNo = (String) mapTemp.get("IDNo");
                        String BnfGrade = (String) mapTemp.get("BnfGrade");
                        String BnfLot = (String) mapTemp.get("BnfLot");
                        psInterFaceTML = (PreparedStatement) conn2.prepareStatement(sqlInTMLCBnf.toString());
                        psInterFaceTML.setString(1, ContNo);
                        psInterFaceTML.setString(2, PolNo);
                        psInterFaceTML.setString(3, BnfType);
                        psInterFaceTML.setString(4, BnfNo);
                        psInterFaceTML.setString(5, CustomerNo);
                        psInterFaceTML.setString(6, RelationToInsured);
                        psInterFaceTML.setString(7, Name);
                        psInterFaceTML.setString(8, Sex);
                        psInterFaceTML.setString(9, Birthday);
                        psInterFaceTML.setString(10, IDType);
                        psInterFaceTML.setString(11, IDNo);
                        psInterFaceTML.setString(12, BnfGrade);
                        psInterFaceTML.setString(13, BnfLot);
                        psInterFaceTML.execute();

                    }

                    
                    for (int i = 0; i < TMLCinuredPCList.size(); i++) {
                        HashMap mapTemp = new HashMap();
                        mapTemp = (HashMap) TMLCinuredPCList.get(i);
                        String ContNo = (String) mapTemp.get("contno");
                        String address = (String) mapTemp.get("address");
                        String postCode = (String) mapTemp.get("postCode");
                        psInterFaceTML = (PreparedStatement) conn2.prepareStatement(sqlInTMLCinuredPC.toString());
                        psInterFaceTML.setString(1, ContNo);
                        psInterFaceTML.setString(2, address);
                        psInterFaceTML.setString(3, postCode);
                        
                        psInterFaceTML.execute();

                    }

                    

























                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    conn2.commit();
                    psTML = (PreparedStatement) conn1.prepareStatement(sqlUpTMLCCont.toString());
                    psTML.setString(1,ContNoTemp);
                    int k = psTML.executeUpdate();
                }catch(Exception ee){
                    
                    logger.error("Contno:"+ContNoTemp);
                    logger.error(ee.toString());
                    
                    ee.printStackTrace();
                    conn2.rollback();
                    
                }
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
            
                
                
            
	          
	            	
	            	
	            	
	            	
	            	
	            	
	            	
	            	
	            	
	            	
	            	
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                  
                    
            	   
            	   
                 
                 
            	
               
               
            	
           
            
            
            if(rsTML!=null)
                rsTML.close();
            if(psTML!=null)
                psTML.close();
            if(psInterFaceTML!=null)
                psInterFaceTML.close();
            if(conn1!=null)
                conn1.close();
            if(conn2!=null)
                conn2.close();
            
        }catch(Exception e){
            e.printStackTrace();
            try{
                if(rsTML!=null)
                    rsTML.close();
                if(psTML!=null)
                    psTML.close();
                if(psInterFaceTML!=null)
                    psInterFaceTML.close();
                if(conn1!=null)
                    conn1.close();
                if(conn2!=null)
                    conn2.close();
            }catch(Exception exp){
                exp.printStackTrace();
            }
        }
    }
    
    
    public Connection getConnection1(){
        return null;
    }
    
    
    public Connection getConnection2(){
        return null;
    }
    
    
    public ArrayList getTMLCContList(){
        return null;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}