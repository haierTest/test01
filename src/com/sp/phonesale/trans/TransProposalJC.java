package com.sp.phonesale.trans;  

import java.util.ArrayList;
import java.util.Calendar;

import com.sp.phonesale.blsvr.BLGetData;
import com.sp.phonesale.dbsvr.DBGetData;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTaddress;
import com.sp.prpall.blsvr.tb.BLPrpTexpense;
import com.sp.prpall.blsvr.tb.BLPrpTfee;
import com.sp.prpall.blsvr.tb.BLPrpTinsured;
import com.sp.prpall.blsvr.tb.BLPrpTinsuredNature;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.blsvr.tb.BLPrpTplan;
import com.sp.prpall.pubfun.Bill;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpTaddressSchema;
import com.sp.prpall.schema.PrpTexpenseSchema;
import com.sp.prpall.schema.PrpTfeeSchema;
import com.sp.prpall.schema.PrpTinsuredNatureSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTplanSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.taskmng.util.TaskMngUtil;



import com.sp.utiall.blsvr.BLPrpDcustomerIdvNew;
import com.sp.utiall.blsvr.BLPrpDkind;




import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TransProposalJC {
	protected final Log logger = LogFactory.getLog(getClass());

    /**
     * @param args
     */
    public static void main(String[] args) {
        

    }
    
    public String getProposalno(DbPool  dbPool,String riskCode,String comCode){
        Bill bill = new Bill();
        String strBizNo="";
        try{
               DateTime dateTime = new DateTime().current();
               String strYear = dateTime.toString().substring(0, 4);
               strBizNo = bill.getNo(dbPool,"prptmain", riskCode,comCode, Integer.parseInt(strYear), "");
               if(strBizNo.equals("")||strBizNo.length()<19){
                   
                   
                   logger.info("获取失败");
                   
               }
        }catch(Exception e){    
            
            logger.error("获取失败");
            
            return "";
        }
        return strBizNo;
    }
    
 public void trans()throws Exception{
        
        ArrayList  TMLCContList     = new ArrayList();   
        DbPool          dbpool      = new DbPool();
        DbPool          tmldbpool      = new DbPool();
        DBGetData       dbGetData   = new DBGetData();
        BLGetData       blGetData   = new BLGetData();
        ArrayList mainItemList = new ArrayList();
        
        /*
        try{
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            tmldbpool.open("TMLDDCCDATASOURCE");
        }catch(Exception e){
            throw new Exception("连接数据库异常!");
        }*/
        
        
        try{
        	
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            tmldbpool.open("TMLDDCCDATASOURCE");
            TMLCContList=blGetData.getTMLCContList(tmldbpool,"0302");
            
        	for (int i=0;i<TMLCContList.size();i++){
        		BLProposal blMainProposal   = new BLProposal();
        		ArrayList  TMLCAppntList    = new ArrayList();   
                ArrayList  TMLCInsuredList  = new ArrayList();   
                ArrayList  TMLCPolList      = new ArrayList();   
                ArrayList  TMLCIinsuredPCList = new ArrayList();
                String contNo = "";
                String comCode="";
                String operCode="";
                String planpaymode="";
                String value ="" ;
                mainItemList=(ArrayList)TMLCContList.get(i);
                contNo=(String)mainItemList.get(0);
                comCode=(String)mainItemList.get(7);
                operCode=(String)mainItemList.get(5);
                String MainPolicyNo=new String();
                MainPolicyNo=getProposalno(dbpool,"0302",comCode);
                planpaymode=getPayModePlan((String)mainItemList.get(14));
                
                
                TMLCPolList =blGetData.getTMLCPolList(tmldbpool,contNo);
                if(TMLCPolList.size()<1){
                    dbGetData.exceUpdate("update TMLCCont set posted='3' where contno='"+contNo+"'",tmldbpool);
                  
                    TaskMngUtil.insertMidDataLog(contNo, contNo+"不满足校验条件在TMLCPol中无数据  TransProposalJC.trans()",null, TaskMngUtil.TransCons.Trans_JCTask_JobName);
                    
                    continue;
                }else{
                	ArrayList tmlcpol = (ArrayList)TMLCPolList.get(0);
                	value = (String)tmlcpol.get(1);
                }
                TMLCAppntList=blGetData.getTMLCAppntList(tmldbpool,contNo);
                if(TMLCAppntList.size()<1){
                    dbGetData.exceUpdate("update TMLCCont set posted='1' where contno='"+contNo+"'",tmldbpool);
                    
                    TaskMngUtil.insertMidDataLog(contNo, contNo+"不满足校验条件在tmlcappnt中无数据  TransProposalJC.trans()",null, TaskMngUtil.TransCons.Trans_JCTask_JobName);
                    
                    continue;
                }else{
                    transInsured(blMainProposal,TMLCAppntList,"2",comCode,operCode, MainPolicyNo,mainItemList);
                }
                TMLCInsuredList=blGetData.getTMLCInsuredList(tmldbpool,contNo);
                if(TMLCInsuredList.size()<1){
                    dbGetData.exceUpdate("update TMLCCont set posted='2' where contno='"+contNo+"'",tmldbpool);
                    
                    TaskMngUtil.insertMidDataLog(contNo, contNo+"不满足校验条件在TMLCInsured中无数据  TransProposalJC.trans()",null, TaskMngUtil.TransCons.Trans_JCTask_JobName);
                    
                    continue;
                }else{
                    transInsured( blMainProposal,TMLCInsuredList,"1",comCode,operCode, MainPolicyNo,mainItemList );
                }
                
                try{
                  transTmain(blMainProposal,MainPolicyNo,mainItemList);
                  transTitemKind(blMainProposal,MainPolicyNo,value);
                  transTexpense(blMainProposal);
                  transTfee(blMainProposal);
                  transTplan(blMainProposal,planpaymode);
                  TMLCIinsuredPCList=blGetData.getTMLCAddressList(tmldbpool,contNo);
                }catch(Exception e1){
                	e1.printStackTrace();
                	dbGetData.exceUpdate("update TMLCCont set posted='E' where contno='"+contNo+"'",tmldbpool);
                	
                    TaskMngUtil.insertMidDataLog(contNo, e1.getMessage(), e1, TaskMngUtil.TransCons.Trans_JCTask_JobName);
                    continue;
                }
                if(TMLCIinsuredPCList.size()<0){
                    dbGetData.exceUpdate("update TMLCCont set posted='9' where contno='"+contNo+"'",tmldbpool);
                  
                    TaskMngUtil.insertMidDataLog(contNo, contNo+"不满足校验条件在TMLCINSUREDPC中无数据  TransProposalJC.trans()",null, TaskMngUtil.TransCons.Trans_JCTask_JobName);
                    
                    continue;
                }else{
                    ArrayList tempAddress=(ArrayList)TMLCIinsuredPCList.get(0);
                    String postCode=(String)tempAddress.get(2);
                    String addressName=(String)tempAddress.get(1);
                    transTaddress(blMainProposal,postCode,addressName);
                }
                dbGetData.exceUpdate("update TMLCCont set posted='Y' where contno='"+contNo+"'",tmldbpool);
                dbpool.beginTransaction();
                String isFlag="";
                try{
                	blMainProposal.save(dbpool, "I", false);
                	dbpool.commitTransaction();
                }catch(Exception e2){
                    e2.printStackTrace();
                    isFlag="1";
                    dbpool.rollbackTransaction();
                    dbGetData.exceUpdate("update TMLCCont set posted='E' where contno='"+contNo+"'",tmldbpool);
                    
                    TaskMngUtil.insertMidDataLog(contNo, e2.getMessage(), e2, TaskMngUtil.TransCons.Trans_JCTask_JobName);
                }
                com.sp.undwrt.bl.facade.BLTaskFacade blTaskFacade = new com.sp.undwrt.bl.facade.BLTaskFacade();
                if(isFlag.equals("")){
                    try{
                        blTaskFacade.start("11","T",MainPolicyNo,"0302",
                            "03",comCode,comCode,
                            operCode,operCode,operCode,""); 
                    }catch(Exception ee){
                        dbpool.rollbackTransaction();
                        dbGetData.exceUpdate("update TMLCCont set posted='U' where contno='"+contNo+"'",tmldbpool);
                        ee.printStackTrace();
                        
                        TaskMngUtil.insertMidDataLog(contNo, ee.getMessage(), ee, TaskMngUtil.TransCons.Trans_JCTask_JobName);
                    }
                }
        	}
        }catch(Exception ex){
            
            ex.printStackTrace();
            
            TaskMngUtil.insertMidDataLog("TransProposalJC.trans", ex.getMessage(), ex, TaskMngUtil.TransCons.Trans_JCTask_JobName);
        }finally{
            dbpool.close();
            tmldbpool.close();
        }
 }
 

 public void transTaddress(BLProposal blMainProposal, String iAddressCode,String iAddressName) throws Exception{
	 
	 PrpTaddressSchema       prpTaddressSchema       = new PrpTaddressSchema();
	 BLPrpTaddress  blPrpTaddress = new BLPrpTaddress();
	 prpTaddressSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
	 prpTaddressSchema.setRiskCode(blMainProposal.getBLPrpTmain().getArr(0).getRiskCode());
	 prpTaddressSchema.setAddressNo("1");
	 prpTaddressSchema.setAddressCode(iAddressCode);
	 prpTaddressSchema.setAddressName(iAddressName);
	 blPrpTaddress.setArr(prpTaddressSchema);
	 blMainProposal.setBLPrpTaddress(blPrpTaddress);
 }
 
 public void transTplan(BLProposal blMainProposal,String planpaymode) throws Exception {
	 PrpTplanSchema prpTplanSchema = new PrpTplanSchema();
	 BLPrpTplan blPrpTplan = new BLPrpTplan();
	 String strFlag = "  "+planpaymode;
	 prpTplanSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
	 prpTplanSchema.setSerialNo("1");
	 prpTplanSchema.setPayNo("1");
	 prpTplanSchema.setPayReason("R10");
	 prpTplanSchema.setPlanStartDate(blMainProposal.getBLPrpTmain().getArr(0).getStartDate());
	 prpTplanSchema.setCurrency(blMainProposal.getBLPrpTmain().getArr(0).getCurrency());

	 java.util.Date   enddate   =  PubTools.stringToUtilDate(blMainProposal.getBLPrpTmain().getArr(0).getStartDate());
	 java.util.Calendar   calendar   =   java.util.Calendar.getInstance();
     calendar.setTime(enddate);   
     calendar.add(Calendar.DATE, +15);
     int   year   =   calendar.get(Calendar.YEAR);   
     int   month   =   calendar.get(Calendar.MONTH)+1;   
     int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
     
	 prpTplanSchema.setPlanDate(year+"-"+month+"-"+day);
	 prpTplanSchema.setPlanFee(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium());
	 prpTplanSchema.setDelinquentFee(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium());
	 prpTplanSchema.setFlag(strFlag);
	 
	 blPrpTplan.setArr(prpTplanSchema);
	 blMainProposal.setBLPrpTplan(blPrpTplan);
 }
 public void transTfee (BLProposal blMainProposal) throws Exception {
	 PrpTfeeSchema prpTfeeSchema = new PrpTfeeSchema();
	 BLPrpTfee  blPrpTfee = new BLPrpTfee();
	 prpTfeeSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
	 prpTfeeSchema.setRiskCode(blMainProposal.getBLPrpTmain().getArr(0).getRiskCode());
	 prpTfeeSchema.setCurrency(blMainProposal.getBLPrpTmain().getArr(0).getCurrency());
	 prpTfeeSchema.setAmount(blMainProposal.getBLPrpTmain().getArr(0).getSumAmount());
	 prpTfeeSchema.setPremium(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium());
	 prpTfeeSchema.setCurrency1(blMainProposal.getBLPrpTmain().getArr(0).getCurrency());
	 prpTfeeSchema.setAmount1(blMainProposal.getBLPrpTmain().getArr(0).getSumAmount());
	 prpTfeeSchema.setPremium1(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium());
	 prpTfeeSchema.setExchangeRate1("1.00000000");
	 prpTfeeSchema.setCurrency2(blMainProposal.getBLPrpTmain().getArr(0).getCurrency());
	 prpTfeeSchema.setAmount2(blMainProposal.getBLPrpTmain().getArr(0).getSumAmount());
	 prpTfeeSchema.setPremium2(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium());
	 prpTfeeSchema.setExchangeRate2("1.00000000");
	 
	 blPrpTfee.setArr(prpTfeeSchema);
	 blMainProposal.setBLPrpTfee(blPrpTfee);
 }
 public void transTexpense(BLProposal blMainProposal) throws Exception {
	 
	 PrpTexpenseSchema prpTexpenseSchema = new PrpTexpenseSchema();
	 BLPrpTexpense blPrptexpense = new BLPrpTexpense();
	 prpTexpenseSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
	 prpTexpenseSchema.setRiskCode(blMainProposal.getBLPrpTmain().getArr(0).getRiskCode());
	 prpTexpenseSchema.setFlag("I2");
	 blPrptexpense.setArr(prpTexpenseSchema);
	 blMainProposal.setBLPrpTexpense(blPrptexpense);
 }
 public void transTmain(BLProposal blMainProposal,String MainPolicyNo,ArrayList contList) throws Exception{
	 PrpTmainSchema  prpTmainSchema  = new PrpTmainSchema();
	 BLPrpTmain blPrptmain     = new BLPrpTmain();
	 PrpTinsuredSchema  prpTinsuredSchemaApp = new PrpTinsuredSchema();
	 PrpTinsuredSchema  prpTinsuredSchemaIns = new PrpTinsuredSchema();
	 String ipaytimes = getPayType((String)contList.get(9));
	 String payTimes="";
     if(ipaytimes.equals("1")){
         payTimes="1";
     }else if(ipaytimes.equals("2")){
         payTimes="12";
     }else{
         payTimes="4";
     }
	 for(int i=0; i<blMainProposal.getBLPrpTinsured().getSize();i++){
		 if(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredFlag().equals("2")){
			 prpTinsuredSchemaApp=blMainProposal.getBLPrpTinsured().getArr(i);
		 }else if(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredFlag().equals("1")
				 &&blMainProposal.getBLPrpTinsured().getArr(i).getSerialNo().equals("2")){
			 prpTinsuredSchemaIns=blMainProposal.getBLPrpTinsured().getArr(i);
		 }
	 }
	 
	 prpTmainSchema.setProposalNo(MainPolicyNo);
	 prpTmainSchema.setRiskCode("0302");
	 prpTmainSchema.setClassCode("03");
	 prpTmainSchema.setPolicySort("2");
	 
	 
	 prpTmainSchema.setBusinessNature((String)contList.get(19));
	 prpTmainSchema.setChannelType((String)contList.get(18));
	 
	 prpTmainSchema.setLanguage("C");      
	 prpTmainSchema.setPolicyType("01"); 
	 prpTmainSchema.setAppliCode(prpTinsuredSchemaApp.getInsuredCode());
	 prpTmainSchema.setAppliName(prpTinsuredSchemaApp.getInsuredName());
	 prpTmainSchema.setAppliAddress(prpTinsuredSchemaApp.getInsuredAddress());
	 prpTmainSchema.setInsuredCode(prpTinsuredSchemaIns.getInsuredCode());
	 prpTmainSchema.setInsuredName(prpTinsuredSchemaIns.getInsuredName());
	 prpTmainSchema.setInsuredAddress(prpTinsuredSchemaIns.getInsuredAddress());
	 prpTmainSchema.setOperateDate((String)contList.get(3));
	 prpTmainSchema.setStartDate((String)contList.get(4));
	 prpTmainSchema.setStartHour("0");
	 
	 java.util.Date enddate=PubTools.stringToUtilDate((String)contList.get(4));
	 java.util.Calendar   calendar   =   java.util.Calendar.getInstance();
     calendar.setTime(enddate);   
     calendar.add(Calendar.YEAR   ,   1);
     calendar.add(Calendar.DATE,-1);
     int   year   =   calendar.get(Calendar.YEAR);   
     int   month   =   calendar.get(Calendar.MONTH)+1;   
     int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
     prpTmainSchema.setEndDate(year+"-"+month+"-"+day);
	 prpTmainSchema.setEndHour("24");
	 prpTmainSchema.setPureRate("0.0000");
     prpTmainSchema.setDisRate("0.0000");
     prpTmainSchema.setDiscount("100.0000");
     prpTmainSchema.setCurrency("CNY");
     prpTmainSchema.setSumValue("0.00");
     prpTmainSchema.setSumAmount((String)contList.get(17));
     prpTmainSchema.setSumPremium((String)contList.get(8));
     prpTmainSchema.setSumQuantity("1");
     prpTmainSchema.setJudicalScope("中华人民共和国管辖(港澳台除外)");
     prpTmainSchema.setAutoTransRenewFlag(getPayModeMain((String)contList.get(14)));
     prpTmainSchema.setPayTimes(payTimes);
     prpTmainSchema.setEndorseTimes("0");
     prpTmainSchema.setClaimTimes("0");
     prpTmainSchema.setMakeCom((String)contList.get(7));
     prpTmainSchema.setComCode((String)contList.get(7));
     prpTmainSchema.setHandlerCode((String)contList.get(5));
     prpTmainSchema.setHandler1Code((String)contList.get(5));
     prpTmainSchema.setOperatorCode((String)contList.get(5));
     prpTmainSchema.setInputDate((String)contList.get(3));
     prpTmainSchema.setInputHour("0");
     prpTmainSchema.setCoinsFlag("0");
     prpTmainSchema.setAllinsFlag("2");
     prpTmainSchema.setUnderWriteFlag("4");
     prpTmainSchema.setOthFlag("000000YY000000000000");
     prpTmainSchema.setDisRate1("0.0000");
     prpTmainSchema.setBusinessFlag("0");
     prpTmainSchema.setUpdaterCode((String)contList.get(5));
     prpTmainSchema.setUpdateDate((String)contList.get(3));
     prpTmainSchema.setUpdateHour("0");
     prpTmainSchema.setSignDate((String)contList.get(3));
     prpTmainSchema.setShareHolderFlag("0");
     prpTmainSchema.setPayMode("0");
     prpTmainSchema.setManualType("1");
     prpTmainSchema.setDomesticFlag("0");
     prpTmainSchema.setVentureFlag("0");
     prpTmainSchema.setBidFlag("0");
     prpTmainSchema.setAgriFlag("0");
     prpTmainSchema.setPolicyNo((String)contList.get(0));
     
     blPrptmain.setArr(prpTmainSchema);
     blMainProposal.setBLPrpTmain(blPrptmain);
 }
 public void transTitemKind(BLProposal blMainProposal,String MainPolicyNo,String ivalue) throws Exception{
	 
	 PrpTitemKindSchema prpTitemKindSchema =  new PrpTitemKindSchema();
	 BLPrpTitemKind     blPrpTitemKind     =  new BLPrpTitemKind();
	 BLPrpDkind blPrpDkind = new BLPrpDkind();
     blPrpDkind.query(" riskcode='0302' and substr(calculateflag,3,1)='1' ");
	 prpTitemKindSchema.setProposalNo(MainPolicyNo);
	 prpTitemKindSchema.setRiskCode("0302");
	 prpTitemKindSchema.setItemNo("0");
	 prpTitemKindSchema.setKindCode(blPrpDkind.getArr(0).getKindCode());
	 prpTitemKindSchema.setKindName(blPrpDkind.getArr(0).getKindCName());
	 prpTitemKindSchema.setItemKindNo("0");
	 prpTitemKindSchema.setItemCode("9998");
	 prpTitemKindSchema.setItemDetailName("虚拟标的");
	 prpTitemKindSchema.setStartDate(blMainProposal.getBLPrpTmain().getArr(0).getStartDate());
	 prpTitemKindSchema.setEndDate(blMainProposal.getBLPrpTmain().getArr(0).getEndDate());
	 prpTitemKindSchema.setStartHour(blMainProposal.getBLPrpTmain().getArr(0).getStartHour());
	 prpTitemKindSchema.setEndHour(blMainProposal.getBLPrpTmain().getArr(0).getEndHour());
	 prpTitemKindSchema.setCurrency("CNY");
	 prpTitemKindSchema.setCalculateFlag(blPrpDkind.getArr(0).getCalculateFlag().substring(0, 1));
	 prpTitemKindSchema.setAmount(blMainProposal.getBLPrpTmain().getArr(0).getSumAmount());
	 prpTitemKindSchema.setPremium(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium());
	 prpTitemKindSchema.setFlag(" 1");
	 prpTitemKindSchema.setValue(ivalue);
	 
	 blPrpTitemKind.setArr(prpTitemKindSchema);
	 blMainProposal.setBLPrpTitemKind(blPrpTitemKind);
	 
 }
 public void transInsured(BLProposal blMainProposal,ArrayList tmlInsuredList,String insuredFlag,String comCode,String operCode, String MainPolicyNo,ArrayList contList) throws Exception{
	 
 	 BLPrpDcustomerIdvNew blPrpDcustomerIdvNew = new BLPrpDcustomerIdvNew();
	 
	 if("2".equals(insuredFlag)){
		 PrpTinsuredSchema       prpTinsuredSchema       = new PrpTinsuredSchema();
		 BLPrpTinsured           blPrpTinsured           = new BLPrpTinsured();
		 PrpTinsuredNatureSchema prpTinsuredNatureSchema = new PrpTinsuredNatureSchema();
		 BLPrpTinsuredNature           blPrpTinsuredNature           = new BLPrpTinsuredNature();
		 ArrayList appList=(ArrayList)tmlInsuredList.get(0);
		 prpTinsuredSchema.setProposalNo(MainPolicyNo);
		 prpTinsuredSchema.setRiskCode("0302"); 
         prpTinsuredSchema.setSerialNo("1");
         prpTinsuredSchema.setLanguage("C");
         prpTinsuredSchema.setInsuredType("1");     
         prpTinsuredSchema.setInsuredName((String)appList.get(0));
         prpTinsuredSchema.setInsuredAddress((String)appList.get(13));
         prpTinsuredSchema.setInsuredNature("3");   
         prpTinsuredSchema.setInsuredFlag("2");     
         prpTinsuredSchema.setInsuredIdentity(getInsuredIdentity((String)appList.get(11))); 
         prpTinsuredSchema.setRelateSerialNo("");
         prpTinsuredSchema.setIdentifyType(getIdtype((String)appList.get(3)));
         prpTinsuredSchema.setIdentifyNumber((String)appList.get(4));
         prpTinsuredSchema.setOccupationCode((String)appList.get(9));
         prpTinsuredSchema.setPostCode((String)appList.get(14));
         prpTinsuredSchema.setEmail((String)appList.get(12));
         prpTinsuredSchema.setPhoneNumber((String)appList.get(15));
         
         prpTinsuredSchema.setBank((String)contList.get(11));
         prpTinsuredSchema.setAccount((String)contList.get(12));
         prpTinsuredSchema.setAccountName((String)contList.get(13));
         
         prpTinsuredSchema.setBenefitFlag("");
         prpTinsuredSchema.setBenefitRate("");
         

         prpTinsuredSchema.setInsuredCode(blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema,comCode,operCode,operCode));
         
         
         prpTinsuredNatureSchema.setProposalNo(MainPolicyNo);
         prpTinsuredNatureSchema.setSerialNo("1");
         prpTinsuredNatureSchema.setInsuredFlag("2");
         prpTinsuredNatureSchema.setSex(getSexCode((String)appList.get(1)));
         prpTinsuredNatureSchema.setAge("");
         prpTinsuredNatureSchema.setBirthday((String)appList.get(2));
         prpTinsuredNatureSchema.setUnit("");
         prpTinsuredNatureSchema.setUnitPostCode("");
         prpTinsuredNatureSchema.setUnitType("");
         prpTinsuredNatureSchema.setLocalPoliceStation("");
         prpTinsuredNatureSchema.setRoomAddress((String)appList.get(13));
         prpTinsuredNatureSchema.setRoomPostCode((String)appList.get(14));
         prpTinsuredNatureSchema.setRoomPhone((String)appList.get(15));
         prpTinsuredNatureSchema.setMobile("");
         prpTinsuredNatureSchema.setMarriage((String)appList.get(5));

         blPrpTinsured.setArr(prpTinsuredSchema);
         blPrpTinsuredNature.setArr(prpTinsuredNatureSchema);
         blMainProposal.setBLPrpTinsured(blPrpTinsured);
         blMainProposal.setBLPrpTinsuredNature(blPrpTinsuredNature);
	 }else if("1".equals(insuredFlag)){
		 for (int i=0;i<tmlInsuredList.size();i++){
			 PrpTinsuredSchema       prpTinsuredSchema       = new PrpTinsuredSchema();
			 BLPrpTinsured           blPrpTinsured           = new BLPrpTinsured();
			 PrpTinsuredNatureSchema prpTinsuredNatureSchema = new PrpTinsuredNatureSchema();
			 BLPrpTinsuredNature           blPrpTinsuredNature           = new BLPrpTinsuredNature();
			 ArrayList insuredList=(ArrayList)tmlInsuredList.get(i);
			 prpTinsuredSchema.setProposalNo(MainPolicyNo);
			 prpTinsuredSchema.setRiskCode("0302");
			 prpTinsuredSchema.setSerialNo((blMainProposal.getBLPrpTinsured().getSize()+1)+"");
			 prpTinsuredSchema.setLanguage("C");
	         prpTinsuredSchema.setInsuredType("1");     
	         prpTinsuredSchema.setInsuredName((String)insuredList.get(0));
	         prpTinsuredSchema.setInsuredAddress((String)insuredList.get(12));
	         prpTinsuredSchema.setInsuredNature("3");
             prpTinsuredSchema.setInsuredFlag("1");
             prpTinsuredSchema.setInsuredIdentity(getInsuredIdentity((String)insuredList.get(10))); 
             prpTinsuredSchema.setRelateSerialNo("");
             prpTinsuredSchema.setIdentifyType(getIdtype((String)insuredList.get(3)));
             prpTinsuredSchema.setIdentifyNumber((String)insuredList.get(4));
             prpTinsuredSchema.setOccupationCode((String)insuredList.get(8));
             prpTinsuredSchema.setPostCode((String)insuredList.get(13));
             prpTinsuredSchema.setEmail((String)insuredList.get(11));
             prpTinsuredSchema.setPhoneNumber((String)insuredList.get(14));
             prpTinsuredSchema.setBenefitFlag("");
             prpTinsuredSchema.setBenefitRate("");
             

             prpTinsuredSchema.setInsuredCode(blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema,comCode,operCode,operCode));
             
             
             prpTinsuredNatureSchema.setProposalNo(MainPolicyNo);
             prpTinsuredNatureSchema.setSerialNo((blMainProposal.getBLPrpTinsuredNature().getSize()+1)+"");
             prpTinsuredNatureSchema.setInsuredFlag("1");
             prpTinsuredNatureSchema.setSex(getSexCode((String)insuredList.get(1)));
             prpTinsuredNatureSchema.setAge((String)insuredList.get(15));
             prpTinsuredNatureSchema.setBirthday((String)insuredList.get(2));
             prpTinsuredNatureSchema.setUnit("");
             prpTinsuredNatureSchema.setUnitPostCode("");
             prpTinsuredNatureSchema.setUnitType("");
             prpTinsuredNatureSchema.setLocalPoliceStation("");
             prpTinsuredNatureSchema.setRoomAddress((String)insuredList.get(12));
             prpTinsuredNatureSchema.setRoomPostCode((String)insuredList.get(13));
             prpTinsuredNatureSchema.setRoomPhone((String)insuredList.get(14));
             prpTinsuredNatureSchema.setMobile("");
             prpTinsuredNatureSchema.setMarriage((String)insuredList.get(5));
             blMainProposal.getBLPrpTinsured().setArr(prpTinsuredSchema);
             blMainProposal.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchema);
		 }
	 }
	 
 }
 
 public String getPayModeMain(String payMode){
     if(payMode.equals("1"))
         return "1";
     else if(payMode.equals("6"))
         return "6";
     else
         return "2";
 }
 public String getPayModePlan(String payMode){
     if(payMode.equals("1"))
         return "3";
     else if(payMode.equals("6"))
         return "2";
     else
         return "1";
 }
 public String getSexCode(String sex){
     if(sex==null){
         sex="";
     }
     if(sex.equals("0")){
         return "1";
     }else if(sex.equals("1")){
         return "2";
     }else{
         return "";
     }
 }
 public String getPayType(String PayIntv){
     if(PayIntv.equals("0")||PayIntv.equals("12"))
         return "1";
     else if(PayIntv.equals("3"))
         return "3";
     else
         return "2";
 }

  public String getInsuredIdentity(String tmlInsuredIdentity){
     if("00".equals(tmlInsuredIdentity)){
         return "01";
     }else if("07".equals(tmlInsuredIdentity)){
         return "10";
     }else if(("01".equals(tmlInsuredIdentity))||
               ("02".equals(tmlInsuredIdentity))||
               ("03".equals(tmlInsuredIdentity))||
               ("04".equals(tmlInsuredIdentity))){
         return "40";
     }else if ("50".equals(tmlInsuredIdentity)){
         return "50";
     }else{
         return "10";
     }
     
 }
  
  public String getIdtype(String tmlidType){
      if("0".equals(tmlidType)){
          return "01";
      }else if("1".equals(tmlidType)){
          return "03";
      }else if("2".equals(tmlidType)){
          return "04";
      }else if("5".equals(tmlidType)){
          return "02";
      }else if("8".equals(tmlidType)){
          return "99";
      }else {
          return "99";
      }
      
  }
  
  /*public String createCustomer(PrpTinsuredSchema prpTinsuredSchema,String comCode,String operCode)throws Exception{
      PrpDcustomerSchema prpDcustomerSchema = new PrpDcustomerSchema();
      BLPrpDcustomer blPrpDcustomer = new BLPrpDcustomer();
      prpDcustomerSchema.setCustomerType("1");
      prpDcustomerSchema.setCustomerCode("");
      prpDcustomerSchema.setCustomerCName(prpTinsuredSchema.getInsuredName());

      prpDcustomerSchema.setAddressCName(prpTinsuredSchema.getInsuredAddress());

      prpDcustomerSchema.setValidStatus("1");
      String strCustomerCode1 = blPrpDcustomer.addCustomer(prpDcustomerSchema,comCode);
      DBPrpDcustomerIdv dbPrpDcustomerIdv = new DBPrpDcustomerIdv();
      dbPrpDcustomerIdv.setCustomerCode(strCustomerCode1);

      dbPrpDcustomerIdv.setCustomerCName(prpTinsuredSchema.getInsuredName());
      dbPrpDcustomerIdv.setAddressCName(prpTinsuredSchema.getInsuredAddress());

      dbPrpDcustomerIdv.setIdentifyType(prpTinsuredSchema.getIdentifyType());
      dbPrpDcustomerIdv.setIdentifyNumber(prpTinsuredSchema.getIdentifyNumber());
      dbPrpDcustomerIdv.setPhoneNumber(prpTinsuredSchema.getPhoneNumber());
      dbPrpDcustomerIdv.setMobile(prpTinsuredSchema.getMobile());
      dbPrpDcustomerIdv.setLinkAddress(prpTinsuredSchema.getInsuredAddress());
      dbPrpDcustomerIdv.setPostCode(prpTinsuredSchema.getPostCode());
      dbPrpDcustomerIdv.setNewCustomerCode(strCustomerCode1);
      DateTime dateTime = new DateTime().current();
      String strDate = dateTime.toString().substring(0, 10);
      dbPrpDcustomerIdv.setValidStatus("1");
      dbPrpDcustomerIdv.setLowerViewFlag("0");
      dbPrpDcustomerIdv.setOperatorCode(operCode);
      dbPrpDcustomerIdv.setInputDate(strDate);
      dbPrpDcustomerIdv.setUpdaterCode(operCode);
      dbPrpDcustomerIdv.setUpdateDate(strDate);
      dbPrpDcustomerIdv.setComcode(comCode);
      dbPrpDcustomerIdv.insert();
      return strCustomerCode1;  
  }*/
  
}