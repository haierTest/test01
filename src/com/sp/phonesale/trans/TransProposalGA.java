package com.sp.phonesale.trans;  

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import com.sp.phonesale.blsvr.BLGetData;
import com.sp.phonesale.dbsvr.DBGetData;
import com.sp.prpall.blsvr.misc.BLPrpBillCoreCard;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTinsured;
import com.sp.prpall.blsvr.tb.BLPrpTinsuredNature;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.pubfun.Bill;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpBillCoreCardSchema;
import com.sp.prpall.schema.PrpTaddressSchema;
import com.sp.prpall.schema.PrpTengageSchema;
import com.sp.prpall.schema.PrpTexpenseSchema;
import com.sp.prpall.schema.PrpTfeeSchema;
import com.sp.prpall.schema.PrpTinsuredNatureSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainCasualtySchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTmainSubSchema;
import com.sp.prpall.schema.PrpTplanSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.ExceptionUtils;
import com.sp.utiall.blsvr.BLPrpDcustomerIdvNew;
import com.sp.utiall.blsvr.BLPrpDitem;
import com.sp.utiall.blsvr.BLPrpDkind;
import com.sp.utiall.blsvr.BLPrpDration;
import com.sp.utiall.dbsvr.DBPrpDkind;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.Str;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class TransProposalGA {
	private final Log logger = LogFactory.getLog(getClass());
    /**
     * @param args
     */
    public static void main(String[] args) {
  
    }  
    /**
     * @Author     刘佳
     * @description 四舍五入函数
     * @param        需要四舍五入的数值
     * @return       XXXXX留两位小数后的值
     */ 
    public String  format(double d){
        double Dight=0.00;
        Dight  =  (double) (Math.round  (d*Math.pow(10,2))/Math.pow(10,2));  
        String s=""+Dight;
        return s;
    }
    /**
     * @Author     刘佳
     * @description 生成XX单号
     * @param        dbPool数据库连接池
     * @param        riskCodeXXXXX种代码
     * @param        comCode归属机构
     * @return       XX单号
     */ 
    public String getProposalno(DbPool  dbPool,
                                 String riskCode,
                                 String comCode){  
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

    /**
     * @Author     刘佳
     * @description 性别代码转换
     * @param        sex电销性别代码
     * @return       核心性别代码
     */ 
    public String getSexCode(String sex)throws  Exception{
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
    
    /**
     * @Author     刘佳
     * @description *plan模块缴费方式代码转换
     * @param        payMode电销缴费方式
     * @return       核心*plan模块缴费方式代码
     */ 
    public String getPayMode(String payMode)throws  Exception{
        if(payMode.equals("1"))
            return "3";
        else if(payMode.equals("6"))
            return "2";
        else if(payMode.equals("8"))
            return "4";
        else
            return "1";
    }
    

    /**
     * @Author     刘佳
     * @description myhead模块缴费方式代码转换
     * @param        payMode电销缴费方式
     * @return       核心myhead模块缴费方式代码
     */ 
    public String getPayMode1(String payMode)throws  Exception{
        if(payMode.equals("1"))
            return "1";
        else if(payMode.equals("6"))
            return "6";
        else if(payMode.equals("8"))
            return "8";
        else
            return "2";
    }

    /**
     * @Author     刘佳
     * @description 创建XXXXX
     * @param        prpTinsuredSchema关系人对象
     * @param        comCode机构
     * @param        operCode操作人
     * @return       XXXXX代码
     * @throws Exception 
     */    
    /*public String createCustomer(PrpTinsuredSchema prpTinsuredSchema,
                                  String comCode,
                                  String operCode)throws Exception{
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
    
    
    /**
     * @Author     
     * @description 校验数据完整性方法
     * @param        TMLCAppntListXX人信息集合
     * @param        TMLCInsuredList被XXXXX人信息集合
     * @param        TMLCPolListXXXXX种信息集合
     * @param        transSchema转数关键数据
     * @param        dbGetData接口表数据库操作对象
     * @param        contNo合同号
     * @param        tmldbpool连接库连接池对象
     * @return       boolean
     * @throws Exception 
     */ 
    public boolean checkData(ArrayList  TMLCAppntList,
                               ArrayList  TMLCInsuredList,
                               ArrayList  TMLCPolList,
                               ArrayList  TMLCIinsuredPCList,
                               DBGetData  dbGetData,
                               String     contNo,
                               DbPool     tmldbpool
                               )throws Exception{
        if(TMLCPolList.size()<1){
            dbGetData.exceUpdate("update TMLCCont set posted='3' where contno='"+contNo+"'",tmldbpool);
            return false;
        }
        if(TMLCAppntList.size()<1){
            dbGetData.exceUpdate("update TMLCCont set posted='1' where contno='"+contNo+"'",tmldbpool);
            return false;
        }
        if(TMLCInsuredList.size()<1){
            dbGetData.exceUpdate("update TMLCCont set posted='2' where contno='"+contNo+"'",tmldbpool);
            return false;
        }
        if(TMLCPolList.size()>1&&TMLCIinsuredPCList.size()<1){
            dbGetData.exceUpdate("update TMLCCont set posted='9' where contno='"+contNo+"'",tmldbpool);
            return false;
        }
        return true;
    }
    
    /**
     * @Author     
     * @description 校验转数关键数据方法
     * @param        blMainProposal大XX单对象
     * @param        transSchema转数关键对象
     * @param        dbGetData接口表数据库操作对象
     * @param        tmldbpool连接库连接池对象
     * @return       boolean
     * @throws Exception 
     */   
    public boolean checkAmountPermium(BLProposal blMainProposal,
                                        TransSchema transSchema,
                                        DBGetData dbGetData,
                                        DbPool tmldbpool) throws  Exception{
        double sumPremiumTm  = Double.parseDouble(transSchema.getSumPremium());
        double sumPremium    = Double.parseDouble(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium());
        if(sumPremiumTm!=sumPremium){
            dbGetData.exceUpdate("update TMLCCont set posted='6' where contno='"+transSchema.getContNo()+"'",tmldbpool);
            return false;
        }
        return true;
    }
    /**
     * @Author     
     * @description 校验转数关键数据方法
     * @param        transSchema转数关键数据
     * @param        dbGetData接口表数据库操作对象
     * @param        contNo合同号
     * @param        tmldbpool连接库连接池对象
     * @return       boolean
     * @throws Exception 
     */
    public boolean checkDataItem(TransSchema transSchema,
                                   ArrayList TMLCInsuredList,
                                   DBGetData dbGetData,
                                   String contNo,
                                   DbPool tmldbpool)throws Exception{
        if(transSchema.getGrade()==null||"".equals(transSchema.getGrade())){
            dbGetData.exceUpdate("update TMLCCont set posted='7' where contno='"+transSchema.getContNo()+"'",tmldbpool);
            return false;
        }
        if(transSchema.getPolicyType()==null||"".equals(transSchema.getPolicyType())){
            dbGetData.exceUpdate("update TMLCCont set posted='7' where contno='"+transSchema.getContNo()+"'",tmldbpool);
            return false;
        }
        String benrenFlag="";
        String peiouFlag="";
        String zinvFalg="";
        String insuredPoliyType="";
        for(int t=0;t<TMLCInsuredList.size();t++){
            ArrayList insuredList = new ArrayList();
            insuredList=(ArrayList)TMLCInsuredList.get(t);
            String insurFlag=getInsuredIdentityNew((String)insuredList.get(16));
            if("01".equals(insurFlag)){
                benrenFlag="1";
            }else if("10".equals(insurFlag)){
                peiouFlag="1";
            }else if("40".equals(insurFlag)){
                zinvFalg="1";
            }
        }
        if(peiouFlag.equals("1")&&zinvFalg.equals("1")&&benrenFlag.equals("1")){
            insuredPoliyType="27";
        }
        if(peiouFlag.equals("1")&&!zinvFalg.equals("1")&&benrenFlag.equals("1")){
            insuredPoliyType="26";
        }
        if(!peiouFlag.equals("1")&&!zinvFalg.equals("1")&&benrenFlag.equals("1")){
            insuredPoliyType="25";
        }
        if(!peiouFlag.equals("1")&&zinvFalg.equals("1")&&benrenFlag.equals("1")){
            insuredPoliyType="28";
        }
        if(!insuredPoliyType.equals(transSchema.getPolicyType())){
            dbGetData.exceUpdate("update TMLCCont set posted='5' where contno='"+transSchema.getContNo()+"'",tmldbpool);
            return false;
        }
        if("25".equals(transSchema.getPolicyType())){
            if(TMLCInsuredList.size()!=1){
                dbGetData.exceUpdate("update TMLCCont set posted='5' where contno='"+transSchema.getContNo()+"'",tmldbpool);
                return false;
            }
        }else if("26".equals(transSchema.getPolicyType())){
            if(TMLCInsuredList.size()!=2){
                dbGetData.exceUpdate("update TMLCCont set posted='5' where contno='"+transSchema.getContNo()+"'",tmldbpool);
                return false; 
            }
        }else if("27".equals(transSchema.getPolicyType())){
            if(TMLCInsuredList.size()<3){
                dbGetData.exceUpdate("update TMLCCont set posted='5' where contno='"+transSchema.getContNo()+"'",tmldbpool);
                return false; 
            }
        }else if("28".equals(transSchema.getPolicyType())){
            if(TMLCInsuredList.size()<2){
                dbGetData.exceUpdate("update TMLCCont set posted='5' where contno='"+transSchema.getContNo()+"'",tmldbpool);
                return false; 
            }
        }
        return true;
    }
    
    /**
     * @param tmldbpool 
     * @param dbGetData 
     * @Author     刘佳
     * @description 快钱信息校验
     * @param        blMainProposalXX单大对象
     * @param        blPrpBillCoreCard快钱信息
     * @param        transSchema转数关键信息
     * @return       boolean
     * @throws       Exception  
     */
    public boolean checkBillCoreCard(BLProposal blMainProposal,
                                       BLPrpBillCoreCard blPrpBillCoreCard,
                                       TransSchema transSchema, DBGetData dbGetData, DbPool tmldbpool) throws  Exception{
        if(blPrpBillCoreCard.getSize()>0){
            double amount  = Double.parseDouble(blPrpBillCoreCard.getArr(0).getAmount());
            if(blMainProposal.getBLPrpTplan().getSize()==12){  
                double planfee1 = Double.parseDouble(blMainProposal.getBLPrpTplan().getArr(0).getPlanFee());
                double planfee2 = Double.parseDouble(blMainProposal.getBLPrpTplan().getArr(1).getPlanFee());
                double planfee = planfee1 + planfee2;
                if(planfee!=amount){
                	dbGetData.exceUpdate("update TMLCCont set posted='8' where contno='"+transSchema.getContNo()+"'",tmldbpool);
                	return false;
                } 
            }else{
            	double planfee = Double.parseDouble(blMainProposal.getBLPrpTplan().getArr(0).getPlanFee());
                if(planfee!=amount){
                	dbGetData.exceUpdate("update TMLCCont set posted='8' where contno='"+transSchema.getContNo()+"'",tmldbpool);
                	return false;
                }
            }
            
        }
        return true;
    }
    
     
    /**
     * @Author     
     * @description 设置关键数据值
     * @param        transSchema转数关键数据
     * @param        mainItemList主表信息数据集合
     * @param        TMLCPolListXXXXX别信息集合
     * @param        TMLCInsuredList被XXXXX人信息集合
     * @throws Exception 
     */
    public void setTransData(TransSchema transSchema,
                              ArrayList mainItemList,
                              ArrayList TMLCPolList,
                              ArrayList TMLCInsuredList)throws Exception{
        
        if(TMLCPolList.size()>1){
            transSchema.setRiskFlag("1");
        }else{
            transSchema.setRiskFlag("0");
        }
        transSchema.setComCode((String)mainItemList.get(7));
        transSchema.setSumPremium((String)mainItemList.get(8));
        transSchema.setOperCode((String)mainItemList.get(5));
        String newpaymode=(String)mainItemList.get(14);
        String extpaymode=(String)mainItemList.get(15);
        String myheadPayMode=getPayMode1(newpaymode);
        String PayTimes=(String)mainItemList.get(9);
        String startDate=(String)mainItemList.get(4);
        transSchema.setNewpaymode(getPayMode(newpaymode));
        if(extpaymode!=null&&!extpaymode.equals(""))
            transSchema.setExtpaymode(getPayMode(extpaymode));
        else
            transSchema.setExtpaymode("");
        transSchema.setPayTimes(getPayType(PayTimes));
        transSchema.setMyheadPayMode(myheadPayMode);
        java.util.Date enddatetemp1     =   PubTools.stringToUtilDate(startDate);
        java.util.Calendar   calendar   =   java.util.Calendar.getInstance();   
        calendar.setTime(enddatetemp1);   
        calendar.add(Calendar.YEAR   ,   1);
        calendar.add(Calendar.DATE,-1);
        int   year        =   calendar.get(Calendar.YEAR);   
        int   month       =   calendar.get(Calendar.MONTH)+1;   
        int   day         =   calendar.get(Calendar.DAY_OF_MONTH);
        String endDate    = year+"-"+month+"-"+day;
        String grade      = "";
        for(int i=0;i<TMLCPolList.size();i++){
            ArrayList polItem     =(ArrayList)TMLCPolList.get(i);
            if(((String)polItem.get(0)).trim().length()>6)
                grade=(String)polItem.get(0);
        }
        String PolicyType = getPolicyType(grade.substring(5,6));
        if("A".equals(grade.substring(6,7))){
            grade="1";
        }else if("B".equals(grade.substring(6,7))){
            grade="2";
        }else{  
        }
        transSchema.setGrade(grade);
        transSchema.setStartDate(startDate);
        transSchema.setEndDate(endDate);
        transSchema.setPolicyType(PolicyType);
        int boys=0;
        String childrenNames="";
        for(int t=0;t<TMLCInsuredList.size();t++){
            ArrayList insuredList = new ArrayList();
            insuredList=(ArrayList)TMLCInsuredList.get(t);
            String insurFlag=getInsuredIdentityNew((String)insuredList.get(16));
            if(insurFlag.equals("40")){
                boys=boys+1;
                if(t==TMLCInsuredList.size()-1)
                    childrenNames=childrenNames+(String)insuredList.get(0);
                  else
                    childrenNames=childrenNames+(String)insuredList.get(0)+"、";
            }
        }
        transSchema.setChildrenNames(childrenNames);
        transSchema.setChildrenCount(boys+"");
    }
    
    /**
     * @Author     
     * @description transSchema转数关键数据对象
     * @param        dbpool核心连接池
     * @throws Exception
     */
    public void  setCertino(TransSchema transSchema ,
                             DbPool dbpool)throws Exception{
        String MainPolicyNo=new String();
        String SubOnePolicyNo=new String();
        String SubTowPolicyNo=new String();
        MainPolicyNo   = getProposalno(dbpool,"3009",transSchema.getComCode());
        SubOnePolicyNo = getProposalno(dbpool,"2700",transSchema.getComCode());
        if("1".equals(transSchema.getRiskFlag()))
           SubTowPolicyNo=getProposalno(dbpool,"0302",transSchema.getComCode());
        transSchema.setMainProposalNo(MainPolicyNo);
        transSchema.setSubOneProposalNo(SubOnePolicyNo);
        transSchema.setSubTowProposalNo(SubTowPolicyNo);
    }
    
    /**
     * @Author     
     * @description 转数入口方法
     * @throws Exception 
     */ 
    public void trans()throws Exception{
        ArrayList  TMLCContList     = new ArrayList();   
        DbPool          dbpool      = new DbPool();
        DbPool          tmldbpool   = new DbPool();
        try{
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            tmldbpool.open("TMLDDCCDATASOURCE");
        }catch(Exception e){
        	dbpool.close();
        	tmldbpool.close();
        	logger.info(ExceptionUtils.getExceptionStackTraceString(e));
            throw new Exception("连接数据库异常!");
        }
        DBGetData       dbGetData   = new DBGetData();
        BLGetData       blGetData   = new BLGetData();
        TMLCContList=blGetData.getTMLCContList(tmldbpool,"3009");
        String policyNo="";
        try{
            for (int i=0;i<TMLCContList.size();i++){
                try {
					
					BLProposal blMainProposal   = new BLProposal();  
					BLProposal blSubOneProposal = new BLProposal();  
					BLProposal blSubTowProposal = new BLProposal();  
					BLPrpBillCoreCard blPrpBillCoreCard = new BLPrpBillCoreCard();
					
					ArrayList  TMLCAppntList    = new ArrayList();     
					ArrayList  TMLCInsuredList  = new ArrayList();     
					ArrayList  TMLCPolList      = new ArrayList();     
					ArrayList  TMLCBnfList      = new ArrayList();     
					ArrayList  TMLCIinsuredPCList   = new ArrayList(); 
					ArrayList  BillCoreCardList  = new ArrayList();    
					ArrayList mainItemList=(ArrayList)TMLCContList.get(i);
					TransSchema transSchema = new TransSchema();       
					transSchema.setContNo((String)mainItemList.get(0));
					policyNo=(String)mainItemList.get(0);
					
					TMLCPolList        = blGetData.getTMLCPolList(tmldbpool,transSchema.getContNo());
					TMLCAppntList      = blGetData.getTMLCAppntList(tmldbpool,transSchema.getContNo());
					TMLCInsuredList    = blGetData.getTMLCInsuredList(tmldbpool,transSchema.getContNo());
					TMLCBnfList        = blGetData.getTMLCBnfList(tmldbpool,transSchema.getContNo());
					BillCoreCardList   = blGetData.getBillCoreCardList(tmldbpool, transSchema.getContNo());
					TMLCIinsuredPCList = blGetData.getTMLCAddressList(tmldbpool,transSchema.getContNo());
					PermiumAmountSchema permiumAmountSchema = new PermiumAmountSchema();
					
					if(!checkData(TMLCAppntList,TMLCInsuredList,TMLCPolList,TMLCIinsuredPCList,dbGetData,transSchema.getContNo(),tmldbpool))
					    continue;
					
					setTransData(transSchema,mainItemList,TMLCPolList,TMLCInsuredList);
					
					if(!checkDataItem(transSchema,TMLCInsuredList,dbGetData,transSchema.getContNo(),tmldbpool)){
						logger.info(transSchema.getContNo()+": 关键数据校验不通过！");
						continue; 
					}
					setCertino( transSchema , dbpool);
					try{
					    transBillCoreCard(blPrpBillCoreCard,BillCoreCardList,"T",transSchema.getMainProposalNo());
					    transInsured(blMainProposal,  TMLCAppntList,"1",transSchema,mainItemList);
					    transInsured(blMainProposal,  TMLCInsuredList,"2",transSchema,mainItemList );
					    transInsured(blMainProposal,  TMLCBnfList,"3",transSchema,mainItemList);
					    transInsuredSub(blMainProposal,blSubOneProposal,blSubTowProposal, transSchema);
					    transTitemKindMain(blMainProposal,blMainProposal.getBLPrpTinsured(),transSchema,permiumAmountSchema);
					    transTitemKindSub( blMainProposal, blSubOneProposal, blSubTowProposal,  permiumAmountSchema,transSchema);
					    transTmain(blMainProposal,blSubOneProposal,blSubTowProposal,mainItemList,permiumAmountSchema,transSchema,TMLCInsuredList.size()+"");
					    transTexpense(blMainProposal,blSubOneProposal,blSubTowProposal);
					    transTfee(blMainProposal,blSubOneProposal,blSubTowProposal);
					    transTengageMain(blMainProposal,transSchema.getChildrenNames(),Integer.parseInt(transSchema.getChildrenCount()));
					    transTengageSub(blMainProposal,blSubOneProposal,blSubTowProposal);
					    transTmainSub(blSubOneProposal,blSubTowProposal,transSchema.getMainProposalNo(),transSchema);
					    transTplan(blMainProposal,blSubOneProposal,blSubTowProposal,transSchema);
					    transTmainCasualty(blMainProposal,blSubOneProposal, transSchema.getGrade());
					    if("1".equals(transSchema.getRiskFlag()))
					       transTaddress(blMainProposal,blSubTowProposal,TMLCIinsuredPCList);
					}catch(Exception epp){
						logger.info("单号: "+transSchema.getContNo()+ExceptionUtils.getExceptionStackTraceString(epp));
						epp.printStackTrace();
					    dbGetData.exceUpdate("update TMLCCont set posted='E' where contno='"+transSchema.getContNo()+"'",tmldbpool);
					    continue;
					}
					
					dbpool.beginTransaction();
					String isFlag="";
					try{
					    
					    if(!checkAmountPermium(blMainProposal,transSchema,dbGetData,tmldbpool)){
							logger.info(transSchema.getContNo()+": XXXX不一致！");
					    	continue;
					    }
					    if(!checkBillCoreCard(blMainProposal,blPrpBillCoreCard,transSchema,dbGetData,tmldbpool)){
							logger.info(transSchema.getContNo()+": 快钱业务数据XXXX不一致！");
					    	continue;
					    }
					    blPrpBillCoreCard.save(dbpool);
					    blMainProposal.save(dbpool, "I", false);
					    blSubOneProposal.save(dbpool, "I", false);
					    if("1".equals(transSchema.getRiskFlag()))
					        blSubTowProposal.save(dbpool, "I", false);
					    dbpool.commitTransaction();
					}catch(Exception e2){
						logger.info("单号: "+transSchema.getContNo()+ExceptionUtils.getExceptionStackTraceString(e2));
						e2.printStackTrace();
					    isFlag="1";
					    dbpool.rollbackTransaction();
					    dbGetData.exceUpdate("update TMLCCont set posted='E' where contno='"+transSchema.getContNo()+"'",tmldbpool);
					}
					com.sp.undwrt.bl.facade.BLTaskFacade blTaskFacade = new com.sp.undwrt.bl.facade.BLTaskFacade();
					if(isFlag.equals("")){
					    try{
					        blTaskFacade.start("11","T",transSchema.getMainProposalNo(),"3009",
					                "30",transSchema.getComCode(),transSchema.getComCode(),
					                transSchema.getOperCode(),transSchema.getOperCode(),transSchema.getOperCode(),""); 
					        dbGetData.exceUpdate("update TMLCCont set posted='Y' where contno='"+transSchema.getContNo()+"'",tmldbpool);
					    }catch(Exception ee){
							logger.info("单号: "+transSchema.getContNo()+ExceptionUtils.getExceptionStackTraceString(ee));
							dbGetData.exceUpdate("update TMLCCont set posted='U' where contno='"+transSchema.getContNo()+"'",tmldbpool);
					        ee.printStackTrace();
					    }
					}
				} catch (Exception e) {
					
					logger.info(ExceptionUtils.getExceptionStackTraceString(e));
					
					e.printStackTrace();
				} 
				
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            dbpool.close();
            tmldbpool.close();
        }
    }
    
    
    /**
     * @Author     刘佳
     * @description 获取XX类型
     * @param        phonePolicyType电销XX类型
     * @return       XX类型
     * @throws Exception 
     */
    public String getPolicyType(String phonePolicyType)throws  Exception{
        if("P".equals(phonePolicyType))
            return "25";
        else if("S".equals(phonePolicyType))
            return "26";
        else if("F".equals(phonePolicyType))
            return "27";
        
        else if("D".equals(phonePolicyType))
            return "28";
        
        else 
            return "";
    }
    
    /**
     * @Author     刘佳
     * @description 获取XX类型
     * @param        blMainProposal大XX单对象
     * @return       XX类型
     * @throws Exception 
     */ 
    public String getPolicyType(BLProposal blMainProposal) throws Exception{
        
        String fuqiFlag="";
        String ernvFlag="";
        String strBenefitFlag="";
        String PolicyType="";
        if(blMainProposal.getBLPrpTinsured().getSize()==2){
            PolicyType="25";
        }else{
            for(int j=0;j<blMainProposal.getBLPrpTinsured().getSize();j++){
                strBenefitFlag=blMainProposal.getBLPrpTinsured().getArr(j).getBenefitFlag();
                if(blMainProposal.getBLPrpTinsured().getArr(j).getInsuredIdentity().equals("10")&&strBenefitFlag.equals("N")){
                    fuqiFlag="1";
                }else if(blMainProposal.getBLPrpTinsured().getArr(j).getInsuredIdentity().trim().equals("40")&&strBenefitFlag.equals("N")){
                    ernvFlag="1";
                }
            }
        }
        if(fuqiFlag.equals("1")&&ernvFlag.equals("1")){
            PolicyType="27";
        }
        if(fuqiFlag.equals("1")&&!ernvFlag.equals("1")){
            PolicyType="26";
        }
        if(!fuqiFlag.equals("1")&&!ernvFlag.equals("1")){
            PolicyType="25";
        }
        return PolicyType;
    }
    
    /**
     * @Author     刘佳
     * @description 转XX单主表数据主方法
     * @param        blMainProposal大XX单对象
     * @param        tmlCAppntListXX人信息
     * @param        PermiumAmountSchemaXXXX对象
     * @param        transSchema转数关键要素
     * @param        insuredSize被XX总人数
     * @throws Exception 
     */ 
    public void generatePrpTmain(BLProposal blMainProposal,
                                  ArrayList tmlCAppntList,
                                  PermiumAmountSchema permiumAmountSchema,
                                  TransSchema transSchema,
                                  String insuredSize)throws Exception{
        String tempFlag="";
        BLPrpTinsured blPrpTinsured = new BLPrpTinsured();
        PrpTinsuredSchema       prpTinsuredSchemaApp       = new PrpTinsuredSchema();
        PrpTinsuredSchema       prpTinsuredSchemaInsured   = new PrpTinsuredSchema();
        int SumQuantity =0;
        for(int i=0;i<blMainProposal.getBLPrpTinsured().getSize();i++){
            SumQuantity=SumQuantity+1;
            blPrpTinsured=blMainProposal.getBLPrpTinsured();
            if(blPrpTinsured.getArr(i).getInsuredFlag().equals("2")){
                prpTinsuredSchemaApp=blPrpTinsured.getArr(i);
            }else if(blPrpTinsured.getArr(i).getInsuredFlag().equals("1")&&tempFlag.equals("")){
                prpTinsuredSchemaInsured=blPrpTinsured.getArr(i);
                tempFlag="1";
            }
        }
        String    printNo="";
        ArrayList mainTemp = new ArrayList();
        mainTemp  = tmlCAppntList;
        String payTypeFlag=(String)mainTemp.get(9);
        payTypeFlag=getPayType(payTypeFlag);
        String payTimes="";
        if(payTypeFlag.equals("1")){
            payTimes="1";
        }else if(payTypeFlag.equals("2")){
            payTimes="12";
        }else{
            payTimes="4";
        }
        PrpTmainSchema          prpTmainSchema          = new PrpTmainSchema();
        BLPrpTmain blPrpTmain= new BLPrpTmain();
        prpTmainSchema.setProposalNo(transSchema.getMainProposalNo());
        prpTmainSchema.setClassCode("30");
        prpTmainSchema.setRiskCode("3009");
        prpTmainSchema.setPolicySort("4");
        prpTmainSchema.setPrintNo(printNo);  
        
        
        prpTmainSchema.setBusinessNature((String)mainTemp.get(17));
        prpTmainSchema.setChannelType((String)mainTemp.get(16)); 
        
        prpTmainSchema.setLanguage("C");
        prpTmainSchema.setPolicyType(transSchema.getPolicyType());
        prpTmainSchema.setAppliCode(prpTinsuredSchemaApp.getInsuredCode());
        prpTmainSchema.setAppliName(prpTinsuredSchemaApp.getInsuredName());
        prpTmainSchema.setAppliAddress(prpTinsuredSchemaApp.getInsuredAddress());
        prpTmainSchema.setInsuredName(prpTinsuredSchemaInsured.getInsuredName());
        prpTmainSchema.setInsuredCode(prpTinsuredSchemaInsured.getInsuredCode());
        prpTmainSchema.setInsuredAddress(prpTinsuredSchemaInsured.getInsuredAddress());
        prpTmainSchema.setOperateDate((String)mainTemp.get(3));
        prpTmainSchema.setStartDate((String)mainTemp.get(4)); 
        prpTmainSchema.setStartHour("0");
        java.util.Date enddatetemp1=PubTools.stringToUtilDate((String)mainTemp.get(4));
        java.util.Calendar   calendar   =   java.util.Calendar.getInstance();   
        calendar.setTime(enddatetemp1);   
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
        prpTmainSchema.setSumAmount(permiumAmountSchema.getSumAmount());
        prpTmainSchema.setSumPremium(permiumAmountSchema.getSumPremium());
        prpTmainSchema.setSumQuantity(insuredSize);
        prpTmainSchema.setJudicalScope("中华人民共和国管辖(港澳台除外)");
        prpTmainSchema.setAutoTransRenewFlag(transSchema.getMyheadPayMode());
        prpTmainSchema.setArgueSolution("1");
        prpTmainSchema.setPayTimes(payTimes);
        prpTmainSchema.setEndorseTimes("0");
        prpTmainSchema.setClaimTimes("0");
        prpTmainSchema.setMakeCom(transSchema.getComCode());
        prpTmainSchema.setComCode(transSchema.getComCode());
        prpTmainSchema.setPolicyNo((String)mainTemp.get(0));
        prpTmainSchema.setHandlerCode(transSchema.getOperCode());
        prpTmainSchema.setHandler1Code(transSchema.getOperCode());
        prpTmainSchema.setOperatorCode(transSchema.getOperCode());
        prpTmainSchema.setInputDate((String)mainTemp.get(3));
        prpTmainSchema.setInputHour("0");
        prpTmainSchema.setCoinsFlag("0");
        prpTmainSchema.setAllinsFlag("2");
        prpTmainSchema.setOthFlag("000000YY000000000000");
        prpTmainSchema.setDisRate1("0.0000");
        prpTmainSchema.setBusinessFlag("0");
        prpTmainSchema.setUpdaterCode(transSchema.getOperCode());
        prpTmainSchema.setUpdateDate((String)mainTemp.get(3));
        prpTmainSchema.setUpdateHour("0");
        prpTmainSchema.setSignDate((String)mainTemp.get(3));
        prpTmainSchema.setShareHolderFlag("0");
        prpTmainSchema.setPayMode("1");
        prpTmainSchema.setRemark((String)mainTemp.get(10));
        prpTmainSchema.setManualType("0");
        prpTmainSchema.setDomesticFlag("0");
        prpTmainSchema.setVentureFlag("0");
        prpTmainSchema.setBidFlag("0");
        prpTmainSchema.setUnderWriteFlag("4");
        blPrpTmain.setArr(prpTmainSchema);
        blMainProposal.setBLPrpTmain(blPrpTmain);
    }
    
    /**
     * @Author     刘佳
     * @description 转2700XX单主表数据方法
     * @param        blMainProposal大XX单对象
     * @param        blSubOneProposal2700XX单对象
     * @param        PermiumAmountSchemaXXXX对象
     * @param        SubOnePolicyNo2700XX单号
     * @throws Exception 
     */  
    public void generatePrpTmainOne(BLProposal blMainProposal,
                                     BLProposal blSubOneProposal,
                                     PermiumAmountSchema permiumAmountSchema,
                                     String SubOnePolicyNo) throws Exception{
        PrpTmainSchema          prpTmainSchemaSubOne          = new PrpTmainSchema();
        BLPrpTmain blPrpTmainSubOne= new BLPrpTmain();
        prpTmainSchemaSubOne.setProposalNo(SubOnePolicyNo);
        prpTmainSchemaSubOne.setClassCode("27");
        prpTmainSchemaSubOne.setRiskCode("2700");
        prpTmainSchemaSubOne.setPolicySort("4");
        prpTmainSchemaSubOne.setPrintNo("");
        
        
        prpTmainSchemaSubOne.setBusinessNature(blMainProposal.getBLPrpTmain().getArr(0).getBusinessNature());
        prpTmainSchemaSubOne.setChannelType(blMainProposal.getBLPrpTmain().getArr(0).getChannelType());
        
        prpTmainSchemaSubOne.setLanguage("C");
        prpTmainSchemaSubOne.setPolicyType(blMainProposal.getBLPrpTmain().getArr(0).getPolicyType());
        prpTmainSchemaSubOne.setAppliCode(blMainProposal.getBLPrpTmain().getArr(0).getInsuredCode());
        prpTmainSchemaSubOne.setAppliName(blMainProposal.getBLPrpTmain().getArr(0).getInsuredName());
        prpTmainSchemaSubOne.setAppliAddress(blMainProposal.getBLPrpTmain().getArr(0).getInsuredAddress());
        prpTmainSchemaSubOne.setInsuredName(blMainProposal.getBLPrpTmain().getArr(0).getInsuredName());
        prpTmainSchemaSubOne.setInsuredCode(blMainProposal.getBLPrpTmain().getArr(0).getInsuredCode());
        prpTmainSchemaSubOne.setInsuredAddress(blMainProposal.getBLPrpTmain().getArr(0).getInsuredAddress());
        prpTmainSchemaSubOne.setOperateDate(blMainProposal.getBLPrpTmain().getArr(0).getOperateDate());
        prpTmainSchemaSubOne.setStartDate(blMainProposal.getBLPrpTmain().getArr(0).getStartDate()); 
        prpTmainSchemaSubOne.setStartHour("0");
        prpTmainSchemaSubOne.setEndDate(blMainProposal.getBLPrpTmain().getArr(0).getEndDate());
        prpTmainSchemaSubOne.setEndHour("24");
        prpTmainSchemaSubOne.setPureRate("0.0000");
        prpTmainSchemaSubOne.setDisRate("0.0000");
        prpTmainSchemaSubOne.setDiscount("100.0000");
        prpTmainSchemaSubOne.setCurrency("CNY");
        prpTmainSchemaSubOne.setSumValue("0.00");
        prpTmainSchemaSubOne.setSumAmount(permiumAmountSchema.getSum27Amount());
        prpTmainSchemaSubOne.setSumPremium(permiumAmountSchema.getSum27Permium());
        prpTmainSchemaSubOne.setSumQuantity(blMainProposal.getBLPrpTmain().getArr(0).getSumQuantity());
        prpTmainSchemaSubOne.setJudicalScope("中华人民共和国管辖(港澳台除外)");
        prpTmainSchemaSubOne.setAutoTransRenewFlag(blMainProposal.getBLPrpTmain().getArr(0).getAutoTransRenewFlag());
        prpTmainSchemaSubOne.setArgueSolution("1");
        prpTmainSchemaSubOne.setPayTimes(blMainProposal.getBLPrpTmain().getArr(0).getPayTimes());
        prpTmainSchemaSubOne.setEndorseTimes("0");
        prpTmainSchemaSubOne.setClaimTimes("0");
        prpTmainSchemaSubOne.setMakeCom(blMainProposal.getBLPrpTmain().getArr(0).getMakeCom());
        prpTmainSchemaSubOne.setComCode(blMainProposal.getBLPrpTmain().getArr(0).getComCode());
        prpTmainSchemaSubOne.setHandlerCode(blMainProposal.getBLPrpTmain().getArr(0).getHandlerCode());
        prpTmainSchemaSubOne.setHandler1Code(blMainProposal.getBLPrpTmain().getArr(0).getHandler1Code());
        prpTmainSchemaSubOne.setOperatorCode(blMainProposal.getBLPrpTmain().getArr(0).getOperatorCode());
        prpTmainSchemaSubOne.setInputDate(blMainProposal.getBLPrpTmain().getArr(0).getInputDate());
        prpTmainSchemaSubOne.setInputHour("0");
        prpTmainSchemaSubOne.setCoinsFlag("0");
        prpTmainSchemaSubOne.setAllinsFlag("2");
        prpTmainSchemaSubOne.setUnderWriteFlag("0");
        prpTmainSchemaSubOne.setOthFlag("000000YY000000000000");
        prpTmainSchemaSubOne.setDisRate1("0.0000");
        prpTmainSchemaSubOne.setBusinessFlag("0");
        prpTmainSchemaSubOne.setUpdaterCode(blMainProposal.getBLPrpTmain().getArr(0).getUpdaterCode());
        prpTmainSchemaSubOne.setUpdateDate(blMainProposal.getBLPrpTmain().getArr(0).getUpdateDate());
        prpTmainSchemaSubOne.setUpdateHour("0");
        prpTmainSchemaSubOne.setSignDate(blMainProposal.getBLPrpTmain().getArr(0).getSignDate());
        prpTmainSchemaSubOne.setShareHolderFlag("0");
        prpTmainSchemaSubOne.setPayMode("1");
        prpTmainSchemaSubOne.setRemark(blMainProposal.getBLPrpTmain().getArr(0).getRemark());
        prpTmainSchemaSubOne.setManualType("");
        prpTmainSchemaSubOne.setProductCode("P00046");
        blPrpTmainSubOne.setArr(prpTmainSchemaSubOne);
        blSubOneProposal.setBLPrpTmain(blPrpTmainSubOne);
    }
    
    /**
     * @Author     刘佳
     * @description 转0302XX单主表数据方法
     * @param        blMainProposal大XX单对象
     * @param        blSubTowProposal0302XX单对象
     * @param        PermiumAmountSchemaXXXX对象
     * @param        SubTowPolicyNo0302XX单号
     * @throws Exception 
     */     
    public void generatePrpTmainTow(BLProposal blMainProposal,
                                     BLProposal blSubTowProposal,
                                     PermiumAmountSchema permiumAmountSchema,
                                     String SubTowPolicyNo) throws Exception{
        PrpTmainSchema          prpTmainSchemaSubTow          = new PrpTmainSchema();
        BLPrpTmain blPrpTmainSubTow= new BLPrpTmain();
        prpTmainSchemaSubTow.setProposalNo(SubTowPolicyNo);
        prpTmainSchemaSubTow.setClassCode("03");
        prpTmainSchemaSubTow.setRiskCode("0302");
        prpTmainSchemaSubTow.setPolicySort("4");
        prpTmainSchemaSubTow.setPrintNo("");      
        
        
        prpTmainSchemaSubTow.setBusinessNature(blMainProposal.getBLPrpTmain().getArr(0).getBusinessNature());
        prpTmainSchemaSubTow.setChannelType(blMainProposal.getBLPrpTmain().getArr(0).getChannelType());
        
        prpTmainSchemaSubTow.setLanguage("C");
        prpTmainSchemaSubTow.setPolicyType(blMainProposal.getBLPrpTmain().getArr(0).getPolicyType());
        prpTmainSchemaSubTow.setAppliCode(blMainProposal.getBLPrpTmain().getArr(0).getInsuredCode());
        prpTmainSchemaSubTow.setAppliName(blMainProposal.getBLPrpTmain().getArr(0).getInsuredName());
        prpTmainSchemaSubTow.setAppliAddress(blMainProposal.getBLPrpTmain().getArr(0).getInsuredAddress());
        prpTmainSchemaSubTow.setInsuredName(blMainProposal.getBLPrpTmain().getArr(0).getInsuredName());
        prpTmainSchemaSubTow.setInsuredCode(blMainProposal.getBLPrpTmain().getArr(0).getInsuredCode());
        prpTmainSchemaSubTow.setInsuredAddress(blMainProposal.getBLPrpTmain().getArr(0).getInsuredAddress());
        prpTmainSchemaSubTow.setOperateDate(blMainProposal.getBLPrpTmain().getArr(0).getOperateDate());
        prpTmainSchemaSubTow.setStartDate(blMainProposal.getBLPrpTmain().getArr(0).getStartDate());
        prpTmainSchemaSubTow.setStartHour("0");
        prpTmainSchemaSubTow.setEndDate(blMainProposal.getBLPrpTmain().getArr(0).getEndDate());
        prpTmainSchemaSubTow.setEndHour("24");
        prpTmainSchemaSubTow.setPureRate("0.0000");
        prpTmainSchemaSubTow.setDisRate("0.0000");
        prpTmainSchemaSubTow.setDiscount("100.0000");
        prpTmainSchemaSubTow.setCurrency("CNY");
        prpTmainSchemaSubTow.setSumValue("0.00");
        prpTmainSchemaSubTow.setSumAmount(permiumAmountSchema.getSum03Amount());
        prpTmainSchemaSubTow.setSumPremium(permiumAmountSchema.getSum03Permium());
        prpTmainSchemaSubTow.setSumQuantity(blMainProposal.getBLPrpTmain().getArr(0).getSumQuantity());
        prpTmainSchemaSubTow.setJudicalScope("中华人民共和国管辖(港澳台除外)");
        prpTmainSchemaSubTow.setAutoTransRenewFlag(blMainProposal.getBLPrpTmain().getArr(0).getAutoTransRenewFlag());
        prpTmainSchemaSubTow.setArgueSolution("1");
        prpTmainSchemaSubTow.setPayTimes(blMainProposal.getBLPrpTmain().getArr(0).getPayTimes());
        prpTmainSchemaSubTow.setEndorseTimes("0");
        prpTmainSchemaSubTow.setClaimTimes("0");
        prpTmainSchemaSubTow.setMakeCom(blMainProposal.getBLPrpTmain().getArr(0).getMakeCom());
        prpTmainSchemaSubTow.setComCode(blMainProposal.getBLPrpTmain().getArr(0).getComCode());
        prpTmainSchemaSubTow.setHandlerCode(blMainProposal.getBLPrpTmain().getArr(0).getHandlerCode());
        prpTmainSchemaSubTow.setHandler1Code(blMainProposal.getBLPrpTmain().getArr(0).getHandler1Code());
        prpTmainSchemaSubTow.setOperatorCode(blMainProposal.getBLPrpTmain().getArr(0).getOperatorCode());
        prpTmainSchemaSubTow.setInputDate(blMainProposal.getBLPrpTmain().getArr(0).getInputDate());
        prpTmainSchemaSubTow.setInputHour("0");
        prpTmainSchemaSubTow.setCoinsFlag("0");
        prpTmainSchemaSubTow.setAllinsFlag("2");
        prpTmainSchemaSubTow.setUnderWriteFlag("0");
        prpTmainSchemaSubTow.setOthFlag("000000YY000000000000");
        prpTmainSchemaSubTow.setDisRate1("0.0000");
        prpTmainSchemaSubTow.setBusinessFlag("0");
        prpTmainSchemaSubTow.setUpdaterCode(blMainProposal.getBLPrpTmain().getArr(0).getUpdaterCode());
        prpTmainSchemaSubTow.setUpdateDate(blMainProposal.getBLPrpTmain().getArr(0).getUpdateDate());
        prpTmainSchemaSubTow.setUpdateHour("0");
        prpTmainSchemaSubTow.setSignDate(blMainProposal.getBLPrpTmain().getArr(0).getSignDate());
        prpTmainSchemaSubTow.setShareHolderFlag("0");
        prpTmainSchemaSubTow.setPayMode("1");
        prpTmainSchemaSubTow.setRemark(blMainProposal.getBLPrpTmain().getArr(0).getRemark());
        prpTmainSchemaSubTow.setManualType("");
        prpTmainSchemaSubTow.setDomesticFlag("0");
        prpTmainSchemaSubTow.setVentureFlag("0");
        prpTmainSchemaSubTow.setBidFlag("0");
        blPrpTmainSubTow.setArr(prpTmainSchemaSubTow);
        blSubTowProposal.setBLPrpTmain(blPrpTmainSubTow);
    }
    
    /**
     * @Author     刘佳
     * @description 转XX单主表数据主方法
     * @param        blMainProposal大XX单对象
     * @param        blSubOneProposal2700XX单对象
     * @param        blSubTowProposal0302XX单对象
     * @param        tmlCAppntListXX人信息
     * @param        PermiumAmountSchemaXXXX对象
     * @param        transSchema转数关键要素
     * @param        insuredSize被XX总人数
     * @throws Exception 
     */ 
    public void transTmain(BLProposal blMainProposal,
                            BLProposal blSubOneProposal,
                            BLProposal blSubTowProposal,
                            ArrayList tmlCAppntList,
                            PermiumAmountSchema permiumAmountSchema,
                            TransSchema transSchema,
                            String insuredSize) throws Exception{
        generatePrpTmain   ( blMainProposal, tmlCAppntList, permiumAmountSchema,transSchema, insuredSize);
        generatePrpTmainOne( blMainProposal, blSubOneProposal, permiumAmountSchema,transSchema.getSubOneProposalNo());
        generatePrpTmainTow( blMainProposal, blSubTowProposal, permiumAmountSchema,transSchema.getSubTowProposalNo());
    }
    
    /**
     * @Author     刘佳
     * @description 证件类型转换
     * @param        tmlidType电销证件类型代码
     * @return       核心证件类型代码
     * @throws Exception 
     */ 
    public String getIdtype(String tmlidType)throws  Exception{
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
  
    
    public String getPhoneInsuredIdentity(String tmlInsuredIdentity)throws  Exception{
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
        }else if("05".equals(tmlInsuredIdentity))
        {
        	return "60"; 
        }else if("08".equals(tmlInsuredIdentity))
        {
        	return "71"; 
        }else if("09".equals(tmlInsuredIdentity))
        {
        	return "72"; 
        }else if("10".equals(tmlInsuredIdentity))
        {
        	return "73"; 
        }else if("11".equals(tmlInsuredIdentity))
        {
        	return "74"; 
        }else if("12".equals(tmlInsuredIdentity))
        {
        	return "53"; 
        }else if("13".equals(tmlInsuredIdentity))
        {
        	return "54"; 
        }else if("14".equals(tmlInsuredIdentity))
        {
        	return "41"; 
        }else if("15".equals(tmlInsuredIdentity))
        {
        	return "21"; 
        }else if("16".equals(tmlInsuredIdentity))
        {
        	return "31"; 
        }else
        {
        	return "99"; 
        }
        
    }
    
    /**
     * @Author     刘佳
     * @description 被XXXXX人受益人与XX人关系转换 废弃
     * @param        tmlInsuredIdentity电销被XXXXX人受益人与XX人关系代码
     * @return       核心被XXXXX人受益人与XX人关系代码
     * @throws Exception 
     */ 
    public String getInsuredIdentity(String tmlInsuredIdentity)throws  Exception{
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
    
    /**
     * @Author     刘佳
     * @description 被XXXXX人受益人与XX人关系转换
     * @param        tmlInsuredIdentity被XXXXX人受益人与XX人关系转码
     * @return       核心被XXXXX人受益人与XX人关系代码
     * @throws Exception 
     */ 
    
    public String getInsuredIdentityNew(String tmlInsuredIdentity)throws  Exception{
        if(tmlInsuredIdentity==null)
            tmlInsuredIdentity="";
        if("1".equals(tmlInsuredIdentity)){
            return "01";
        }else if("2".equals(tmlInsuredIdentity)){
            return "10";
        }else if("3".equals(tmlInsuredIdentity)){
            return "40";
        }else if ("50".equals(tmlInsuredIdentity)){
            return "50";
        }else{
            return "";
        }
        
    }    
    

    /**
     * @Author     刘佳
     * @description 缴费类型 年缴，月缴，嫉缴转换
     * @param        PayIntv电销缴费类型
     * @return       核心缴费类型
     * @throws Exception 
     */ 
    public String getPayType(String PayIntv)throws  Exception{
        if(PayIntv.equals("0")||PayIntv.equals("12"))
            return "1";
        else if(PayIntv.equals("3"))
            return "3";
        else
            return "2";
    }
    
    /**
     * @Author     刘佳
     * @description 大XX关系人信息转储
     * @param        blMainProposal大XX单对象
     * @param        tmlInsuredListXX人/被XXXXX人/受益人信息
     * @param        insuredFlag关系人标识
     * @param        transSchema转数关键要素
     * @param        contList主表信息
     * @throws Exception 
     */
    public void transInsuredMain(BLProposal blMainProposal,
                                  ArrayList tmlInsuredList,
                                  String insuredFlag,
                                  TransSchema transSchema,
                                  ArrayList contList)throws Exception{
    	
    	BLPrpDcustomerIdvNew blPrpDcustomerIdvNew = new BLPrpDcustomerIdvNew();
    	
        if("1".equals(insuredFlag)){
            PrpTinsuredSchema       prpTinsuredSchema       = new PrpTinsuredSchema();
            PrpTinsuredNatureSchema prpTinsuredNatureSchema = new PrpTinsuredNatureSchema(); 
            
            ArrayList appList=(ArrayList)tmlInsuredList.get(0);
            prpTinsuredSchema.setProposalNo(transSchema.getMainProposalNo());
            prpTinsuredSchema.setRiskCode("3009");
            prpTinsuredSchema.setSerialNo("1");
            prpTinsuredSchema.setLanguage("C");
            prpTinsuredSchema.setInsuredType("1");
            prpTinsuredSchema.setInsuredName((String)appList.get(0));
            prpTinsuredSchema.setInsuredAddress((String)appList.get(13));
            prpTinsuredSchema.setInsuredNature("3");
            prpTinsuredSchema.setInsuredFlag("2");
            
            
            prpTinsuredSchema.setInsuredIdentity(getPhoneInsuredIdentity((String)appList.get(11)));
            
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
            prpTinsuredSchema.setBenefitFlag("N");
            prpTinsuredSchema.setBenefitRate("0.00");
            

            prpTinsuredSchema.setInsuredCode(blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema,
            		transSchema.getComCode(),transSchema.getOperCode(),transSchema.getOperCode()));
            
            prpTinsuredNatureSchema.setProposalNo(transSchema.getMainProposalNo());
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
            blMainProposal.getBLPrpTinsured().setArr(prpTinsuredSchema);
            blMainProposal.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchema);

        }else if("2".equals(insuredFlag)){
            String tempBenRenFlag="";
            String tempFuQiFlag="";
            String tempZiNvFlag="";
            String childrenNames="";
            childrenNames=transSchema.getChildrenNames();
            
            for(int i=0;i<tmlInsuredList.size();i++){
                ArrayList insuredList = new ArrayList();
                insuredList=(ArrayList)tmlInsuredList.get(i);
                String insurFlag=getInsuredIdentity((String)insuredList.get(10));
                String insuredNo=getInsuredIdentityNew((String)insuredList.get(16));
                
                if(!insuredNo.equals("")){
                    insurFlag=insuredNo;
                }
                if(insurFlag.equals("01")&&tempBenRenFlag.equals("")){
                    tempBenRenFlag="1";
                    PrpTinsuredSchema       prpTinsuredSchema       = new PrpTinsuredSchema();
                    PrpTinsuredNatureSchema prpTinsuredNatureSchema = new PrpTinsuredNatureSchema();
                    prpTinsuredSchema.setProposalNo(transSchema.getMainProposalNo());
                    prpTinsuredSchema.setRiskCode("3009");
                    prpTinsuredSchema.setSerialNo((blMainProposal.getBLPrpTinsured().getSize()+1)+"");
                    prpTinsuredSchema.setLanguage("C");
                    prpTinsuredSchema.setInsuredType("1");
                    prpTinsuredSchema.setInsuredName((String)insuredList.get(0));
                    prpTinsuredSchema.setInsuredAddress((String)insuredList.get(12));
                    prpTinsuredSchema.setInsuredNature("3");
                    prpTinsuredSchema.setInsuredFlag("1");
                    prpTinsuredSchema.setInsuredIdentity(insurFlag);
                    prpTinsuredSchema.setRelateSerialNo("");
                    prpTinsuredSchema.setIdentifyType(getIdtype((String)insuredList.get(3)));
                    prpTinsuredSchema.setIdentifyNumber((String)insuredList.get(4));
                    prpTinsuredSchema.setOccupationCode((String)insuredList.get(8));
                    prpTinsuredSchema.setEmail((String)insuredList.get(11));
                    prpTinsuredSchema.setBenefitFlag("N");
                    prpTinsuredSchema.setBenefitRate("0.00");
                    

                    prpTinsuredSchema.setInsuredCode(blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema,
                    		transSchema.getComCode(),transSchema.getOperCode(),transSchema.getOperCode()));
                    
                    prpTinsuredNatureSchema.setProposalNo(transSchema.getMainProposalNo());
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
                }else if(insurFlag.equals("10")&&tempFuQiFlag.equals("")){
                    tempFuQiFlag="1";
                    PrpTinsuredSchema       prpTinsuredSchema       = new PrpTinsuredSchema();
                    PrpTinsuredNatureSchema prpTinsuredNatureSchema = new PrpTinsuredNatureSchema();
                    prpTinsuredSchema.setProposalNo(transSchema.getMainProposalNo());
                    prpTinsuredSchema.setRiskCode("3009");
                    prpTinsuredSchema.setSerialNo((blMainProposal.getBLPrpTinsured().getSize()+1)+"");
                    prpTinsuredSchema.setLanguage("C");
                    prpTinsuredSchema.setInsuredType("1");
                    prpTinsuredSchema.setInsuredName((String)insuredList.get(0));
                    prpTinsuredSchema.setInsuredAddress((String)insuredList.get(12));
                    prpTinsuredSchema.setInsuredNature("3");
                    prpTinsuredSchema.setInsuredFlag("1");
                    prpTinsuredSchema.setInsuredIdentity(insurFlag);
                    prpTinsuredSchema.setRelateSerialNo("");
                    prpTinsuredSchema.setIdentifyType(getIdtype((String)insuredList.get(3)));
                    prpTinsuredSchema.setIdentifyNumber((String)insuredList.get(4));
                    prpTinsuredSchema.setOccupationCode((String)insuredList.get(8));
                    prpTinsuredSchema.setEmail((String)insuredList.get(11));
                    prpTinsuredSchema.setBenefitFlag("N");
                    prpTinsuredSchema.setBenefitRate("0.00");
                    

                    prpTinsuredSchema.setInsuredCode(blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema,
                    		transSchema.getComCode(),transSchema.getOperCode(),transSchema.getOperCode()));
                    
                    prpTinsuredNatureSchema.setProposalNo(transSchema.getMainProposalNo());
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

                    
                }else if(insurFlag.equals("40")&&tempZiNvFlag.equals("")){
                    tempZiNvFlag="1";
                    PrpTinsuredSchema       prpTinsuredSchema       = new PrpTinsuredSchema();
                    PrpTinsuredNatureSchema prpTinsuredNatureSchema = new PrpTinsuredNatureSchema();
                    prpTinsuredSchema.setProposalNo(transSchema.getMainProposalNo());
                    prpTinsuredSchema.setRiskCode("3009");
                    prpTinsuredSchema.setSerialNo((blMainProposal.getBLPrpTinsured().getSize()+1)+"");
                    prpTinsuredSchema.setLanguage("C");
                    prpTinsuredSchema.setInsuredType("1");
                    prpTinsuredSchema.setInsuredName((String)insuredList.get(0));
                    prpTinsuredSchema.setInsuredAddress((String)insuredList.get(12));
                    prpTinsuredSchema.setInsuredNature("3");
                    prpTinsuredSchema.setInsuredFlag("1");
                    prpTinsuredSchema.setInsuredIdentity(insurFlag);
                    prpTinsuredSchema.setRelateSerialNo("");
                    prpTinsuredSchema.setIdentifyType(getIdtype((String)insuredList.get(3)));
                    prpTinsuredSchema.setIdentifyNumber((String)insuredList.get(4));
                    prpTinsuredSchema.setOccupationCode((String)insuredList.get(8));
                    prpTinsuredSchema.setEmail((String)insuredList.get(11));
                    prpTinsuredSchema.setBenefitFlag("N");
                    prpTinsuredSchema.setBenefitRate("0.00");
                    

                    prpTinsuredSchema.setInsuredCode(blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema,
                    		transSchema.getComCode(),transSchema.getOperCode(),transSchema.getOperCode()));
                    
                    prpTinsuredNatureSchema.setProposalNo(transSchema.getMainProposalNo());
                    prpTinsuredNatureSchema.setSerialNo((blMainProposal.getBLPrpTinsuredNature().getSize()+1)+"");
                    prpTinsuredNatureSchema.setInsuredFlag("1");
                    prpTinsuredNatureSchema.setSex(getSexCode((String)insuredList.get(1)));
                    prpTinsuredNatureSchema.setAge((String)insuredList.get(15));
                    prpTinsuredNatureSchema.setBirthday((String)insuredList.get(2));
                    prpTinsuredNatureSchema.setIncomeSource(childrenNames);
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
            
        }else if("3".equals(insuredFlag)){
            for(int i=0;i<tmlInsuredList.size();i++){
                PrpTinsuredSchema       prpTinsuredSchema       = new PrpTinsuredSchema();
                PrpTinsuredNatureSchema prpTinsuredNatureSchema = new PrpTinsuredNatureSchema();
                ArrayList bnfList = new ArrayList();
                bnfList=(ArrayList)tmlInsuredList.get(i);
                prpTinsuredSchema.setProposalNo(transSchema.getMainProposalNo());
                prpTinsuredSchema.setRiskCode("3009");
                prpTinsuredSchema.setSerialNo((blMainProposal.getBLPrpTinsured().getSize()+1)+"");
                prpTinsuredSchema.setLanguage("C");
                prpTinsuredSchema.setInsuredType("1");
                prpTinsuredSchema.setInsuredName((String)bnfList.get(3));
                prpTinsuredSchema.setInsuredNature("3");
                prpTinsuredSchema.setInsuredFlag("9");
                prpTinsuredSchema.setInsuredIdentity(getInsuredIdentity((String)bnfList.get(2)));
                prpTinsuredSchema.setRelateSerialNo("2");
                prpTinsuredSchema.setIdentifyType(getIdtype((String)bnfList.get(6)));
                prpTinsuredSchema.setIdentifyNumber((String)bnfList.get(7));
                prpTinsuredSchema.setBenefitRate("100.00");
                

                prpTinsuredSchema.setInsuredCode(blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema,
                		transSchema.getComCode(),transSchema.getOperCode(),transSchema.getOperCode()));
                
                
                prpTinsuredNatureSchema.setProposalNo(transSchema.getMainProposalNo());
                prpTinsuredNatureSchema.setSerialNo((blMainProposal.getBLPrpTinsuredNature().getSize()+1)+"");
                prpTinsuredNatureSchema.setInsuredFlag("9");
                prpTinsuredNatureSchema.setSex(getSexCode((String)bnfList.get(4)));
                prpTinsuredNatureSchema.setAge("");
                prpTinsuredNatureSchema.setBirthday("");
                prpTinsuredNatureSchema.setUnit("");
                prpTinsuredNatureSchema.setUnitPostCode("");
                prpTinsuredNatureSchema.setUnitType("");
                prpTinsuredNatureSchema.setLocalPoliceStation("");
                prpTinsuredNatureSchema.setRoomAddress("");
                prpTinsuredNatureSchema.setRoomPostCode("");
                prpTinsuredNatureSchema.setRoomPhone("");
                prpTinsuredNatureSchema.setMobile("");
                prpTinsuredNatureSchema.setMarriage("");
                prpTinsuredNatureSchema.setWeight("");
                prpTinsuredNatureSchema.setStature("");
                blMainProposal.getBLPrpTinsured().setArr(prpTinsuredSchema);
                blMainProposal.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchema);

            }
        }
    }
    
    /**
     * @Author     刘佳
     * @description 2700关系人信息转储
     * @param        blMainProposal大XX单对象
     * @param        blSubOneProposal2700XX单对象
     * @param        SubOnePolicyNo2700XX单号
     * @throws Exception 
     */
    public void transInsuredOne(BLProposal blMainProposal,
                                 BLProposal blSubOneProposal,
                                 String SubOnePolicyNo)throws Exception{
        for(int i=0;i<blMainProposal.getBLPrpTinsured().getSize();i++){
            PrpTinsuredSchema       prpTinsuredSchemaSubOne       = new PrpTinsuredSchema();
            PrpTinsuredNatureSchema prpTinsuredNatureSchemaOne = new PrpTinsuredNatureSchema();
            prpTinsuredSchemaSubOne.setProposalNo(SubOnePolicyNo);
            prpTinsuredSchemaSubOne.setRiskCode("2700");
            prpTinsuredSchemaSubOne.setSerialNo(blMainProposal.getBLPrpTinsured().getArr(i).getSerialNo());
            prpTinsuredSchemaSubOne.setLanguage("C");
            prpTinsuredSchemaSubOne.setBank(blMainProposal.getBLPrpTinsured().getArr(i).getBank());
            prpTinsuredSchemaSubOne.setAccount(blMainProposal.getBLPrpTinsured().getArr(i).getAccount());
            prpTinsuredSchemaSubOne.setAccountName(blMainProposal.getBLPrpTinsured().getArr(i).getAccountName());
            prpTinsuredSchemaSubOne.setInsuredType(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredType());
            prpTinsuredSchemaSubOne.setInsuredName(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredName());
            prpTinsuredSchemaSubOne.setInsuredAddress(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredAddress());
            prpTinsuredSchemaSubOne.setInsuredNature(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredNature());
            prpTinsuredSchemaSubOne.setInsuredFlag(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredFlag());
            prpTinsuredSchemaSubOne.setInsuredIdentity(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredIdentity());
            prpTinsuredSchemaSubOne.setRelateSerialNo(blMainProposal.getBLPrpTinsured().getArr(i).getRelateSerialNo());
            prpTinsuredSchemaSubOne.setIdentifyType(blMainProposal.getBLPrpTinsured().getArr(i).getIdentifyType());
            prpTinsuredSchemaSubOne.setIdentifyNumber(blMainProposal.getBLPrpTinsured().getArr(i).getIdentifyNumber());
            prpTinsuredSchemaSubOne.setOccupationCode(blMainProposal.getBLPrpTinsured().getArr(i).getOccupationCode());
            prpTinsuredSchemaSubOne.setPostCode(blMainProposal.getBLPrpTinsured().getArr(i).getPostCode());
            prpTinsuredSchemaSubOne.setEmail(blMainProposal.getBLPrpTinsured().getArr(i).getEmail());
            prpTinsuredSchemaSubOne.setPhoneNumber(blMainProposal.getBLPrpTinsured().getArr(i).getPhoneNumber());
            prpTinsuredSchemaSubOne.setBenefitFlag(blMainProposal.getBLPrpTinsured().getArr(i).getBenefitFlag());
            prpTinsuredSchemaSubOne.setBenefitRate(blMainProposal.getBLPrpTinsured().getArr(i).getBenefitRate());
            prpTinsuredSchemaSubOne.setInsuredCode(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredCode());
            prpTinsuredNatureSchemaOne.setProposalNo(SubOnePolicyNo);
            prpTinsuredNatureSchemaOne.setSerialNo(blMainProposal.getBLPrpTinsuredNature().getArr(i).getSerialNo());
            prpTinsuredNatureSchemaOne.setInsuredFlag(blMainProposal.getBLPrpTinsuredNature().getArr(i).getInsuredFlag());
            prpTinsuredNatureSchemaOne.setSex(blMainProposal.getBLPrpTinsuredNature().getArr(i).getSex());
            prpTinsuredNatureSchemaOne.setAge(blMainProposal.getBLPrpTinsuredNature().getArr(i).getAge());
            prpTinsuredNatureSchemaOne.setBirthday(blMainProposal.getBLPrpTinsuredNature().getArr(i).getBirthday());
            prpTinsuredNatureSchemaOne.setIncomeSource(blMainProposal.getBLPrpTinsuredNature().getArr(i).getIncomeSource());
            prpTinsuredNatureSchemaOne.setUnit("");
            prpTinsuredNatureSchemaOne.setUnitPostCode("");
            prpTinsuredNatureSchemaOne.setUnitType("");
            prpTinsuredNatureSchemaOne.setLocalPoliceStation("");
            prpTinsuredNatureSchemaOne.setRoomAddress(blMainProposal.getBLPrpTinsuredNature().getArr(i).getRoomAddress());
            prpTinsuredNatureSchemaOne.setRoomPostCode(blMainProposal.getBLPrpTinsuredNature().getArr(i).getRoomPostCode());
            prpTinsuredNatureSchemaOne.setRoomPhone(blMainProposal.getBLPrpTinsuredNature().getArr(i).getRoomPhone());
            prpTinsuredNatureSchemaOne.setMobile("");
            prpTinsuredNatureSchemaOne.setMarriage(blMainProposal.getBLPrpTinsuredNature().getArr(i).getMarriage());
            blSubOneProposal.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchemaOne);
            blSubOneProposal.getBLPrpTinsured().setArr(prpTinsuredSchemaSubOne);
        }
    }
    
    /**
     * @Author     刘佳
     * @description 0302关系人信息转储
     * @param        blMainProposal大XX单对象
     * @param        blSubTowProposal0302XX单对象
     * @param        SubTowPolicyNo0302XX单号
     * @throws Exception 
     */
    public void transInsuredTow(BLProposal blMainProposal,
                                 BLProposal blSubTowProposal,
                                 String SubTowPolicyNo)throws Exception{
        for(int i=0;i<blMainProposal.getBLPrpTinsured().getSize();i++){
            PrpTinsuredSchema       prpTinsuredSchemaSubTow       = new PrpTinsuredSchema();
            PrpTinsuredNatureSchema prpTinsuredNatureSchemaTow = new PrpTinsuredNatureSchema();
            prpTinsuredSchemaSubTow.setProposalNo(SubTowPolicyNo);
            prpTinsuredSchemaSubTow.setRiskCode("0302");
            prpTinsuredSchemaSubTow.setSerialNo(blMainProposal.getBLPrpTinsured().getArr(i).getSerialNo());
            prpTinsuredSchemaSubTow.setLanguage("C");
            prpTinsuredSchemaSubTow.setBank(blMainProposal.getBLPrpTinsured().getArr(i).getBank());
            prpTinsuredSchemaSubTow.setAccount(blMainProposal.getBLPrpTinsured().getArr(i).getAccount());
            prpTinsuredSchemaSubTow.setAccountName(blMainProposal.getBLPrpTinsured().getArr(i).getAccountName());
            prpTinsuredSchemaSubTow.setInsuredType(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredType());
            prpTinsuredSchemaSubTow.setInsuredName(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredName());
            prpTinsuredSchemaSubTow.setInsuredAddress(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredAddress());
            prpTinsuredSchemaSubTow.setInsuredNature(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredNature());
            prpTinsuredSchemaSubTow.setInsuredFlag(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredFlag());
            prpTinsuredSchemaSubTow.setInsuredIdentity(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredIdentity());
            prpTinsuredSchemaSubTow.setRelateSerialNo(blMainProposal.getBLPrpTinsured().getArr(i).getRelateSerialNo());
            prpTinsuredSchemaSubTow.setIdentifyType(blMainProposal.getBLPrpTinsured().getArr(i).getIdentifyType());
            prpTinsuredSchemaSubTow.setIdentifyNumber(blMainProposal.getBLPrpTinsured().getArr(i).getIdentifyNumber());
            prpTinsuredSchemaSubTow.setOccupationCode(blMainProposal.getBLPrpTinsured().getArr(i).getOccupationCode());
            prpTinsuredSchemaSubTow.setPostCode(blMainProposal.getBLPrpTinsured().getArr(i).getPostCode());
            prpTinsuredSchemaSubTow.setEmail(blMainProposal.getBLPrpTinsured().getArr(i).getEmail());
            prpTinsuredSchemaSubTow.setPhoneNumber(blMainProposal.getBLPrpTinsured().getArr(i).getPhoneNumber());
            prpTinsuredSchemaSubTow.setBenefitFlag(blMainProposal.getBLPrpTinsured().getArr(i).getBenefitFlag());
            prpTinsuredSchemaSubTow.setBenefitRate(blMainProposal.getBLPrpTinsured().getArr(i).getBenefitRate());
            prpTinsuredSchemaSubTow.setInsuredCode(blMainProposal.getBLPrpTinsured().getArr(i).getInsuredCode());
            prpTinsuredNatureSchemaTow.setProposalNo(SubTowPolicyNo);
            prpTinsuredNatureSchemaTow.setSerialNo(blMainProposal.getBLPrpTinsuredNature().getArr(i).getSerialNo());
            prpTinsuredNatureSchemaTow.setInsuredFlag(blMainProposal.getBLPrpTinsuredNature().getArr(i).getInsuredFlag());
            prpTinsuredNatureSchemaTow.setSex(blMainProposal.getBLPrpTinsuredNature().getArr(i).getSex());
            prpTinsuredNatureSchemaTow.setAge(blMainProposal.getBLPrpTinsuredNature().getArr(i).getAge());
            prpTinsuredNatureSchemaTow.setBirthday(blMainProposal.getBLPrpTinsuredNature().getArr(i).getBirthday());
            prpTinsuredNatureSchemaTow.setIncomeSource(blMainProposal.getBLPrpTinsuredNature().getArr(i).getIncomeSource());
            prpTinsuredNatureSchemaTow.setUnit("");
            prpTinsuredNatureSchemaTow.setUnitPostCode("");
            prpTinsuredNatureSchemaTow.setUnitType("");
            prpTinsuredNatureSchemaTow.setLocalPoliceStation("");
            prpTinsuredNatureSchemaTow.setRoomAddress(blMainProposal.getBLPrpTinsuredNature().getArr(i).getRoomAddress());
            prpTinsuredNatureSchemaTow.setRoomPostCode(blMainProposal.getBLPrpTinsuredNature().getArr(i).getRoomPostCode());
            prpTinsuredNatureSchemaTow.setRoomPhone(blMainProposal.getBLPrpTinsuredNature().getArr(i).getRoomPhone());
            prpTinsuredNatureSchemaTow.setMobile(blMainProposal.getBLPrpTinsuredNature().getArr(i).getMobile());
            prpTinsuredNatureSchemaTow.setMarriage(blMainProposal.getBLPrpTinsuredNature().getArr(i).getMarriage());
            blSubTowProposal.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchemaTow);;
            blSubTowProposal.getBLPrpTinsured().setArr(prpTinsuredSchemaSubTow);
        }
    }
    
    
    /**
     * @Author     刘佳
     * @description 关系人信息转储入口方法
     * @param        blMainProposal大XX单对象
     * @param        tmlInsuredListXX人/被XXXXX人/受益人信息
     * @param        insuredFlag关系人标识
     * @param        transSchema转数关键要素
     * @param        contList主表信息
     * @throws Exception 
     */
    public void transInsured(BLProposal blMainProposal,
                              ArrayList tmlInsuredList,
                              String insuredFlag,
                              TransSchema transSchema,
                              ArrayList contList)throws Exception{
        transInsuredMain( blMainProposal, tmlInsuredList, insuredFlag,  transSchema,contList);

    }
    
    /**
     * @Author     刘佳
     * @description 关系人信息转储入口方法
     * @param        blMainProposal大XX单对象
     * @param        blSubOneProposal2700XX单对象
     * @param        blSubTowProposal0302XX单对象
     * @param        transSchema转数关键要素
     * @throws Exception 
     */    
    public void transInsuredSub(BLProposal blMainProposal,
                              BLProposal blSubOneProposal,
                              BLProposal blSubTowProposal,
                              TransSchema transSchema)throws Exception{
        transInsuredOne ( blMainProposal, blSubOneProposal, transSchema.getSubOneProposalNo());
        if("1".equals(transSchema.getRiskFlag()))
            transInsuredTow ( blMainProposal, blSubTowProposal, transSchema.getSubTowProposalNo());
        
    }
    /**
     * @Author     刘佳
     * @description 关系人信息转储入口方法
     * @param        blMainProposal大XX单对象
     * @param        blSubOneProposal2700XX单对象
     * @param        blSubTowProposal0302XX单对象
     * @param        transSchema转数关键要素
     * @throws Exception 
     */
    public void transTplan(BLProposal blMainProposal,
                            BLProposal blSubOneProposal,
                            BLProposal blSubTowProposal,
                            TransSchema transSchema) throws Exception{
        
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd"); 
        double fontfee=0.0;
        
        if("1".equals(transSchema.getPayTimes())){
            PrpTplanSchema          prptplanSchema          = new PrpTplanSchema();
            prptplanSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prptplanSchema.setSerialNo("1");
            prptplanSchema.setPayNo("1");
            prptplanSchema.setPayReason("R10");
            
            java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
            calendar.setTime( PubTools.stringToUtilDate(transSchema.getStartDate()));   
            calendar.add(Calendar.DATE,+0);
            int   year   =   calendar.get(Calendar.YEAR);   
            int   month   =   calendar.get(Calendar.MONTH)+1;   
            int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
            prptplanSchema.setPlanStartDate(transSchema.getStartDate());
            prptplanSchema.setPlanDate(year+"-"+month+"-"+day);
            prptplanSchema.setCurrency("CNY");
            prptplanSchema.setFlag("  "+transSchema.getNewpaymode());
            prptplanSchema.setPlanFee(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium());
            prptplanSchema.setDelinquentFee(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium());
            blMainProposal.getBLPrpTplan().setArr(prptplanSchema);
        }else if("3".equals(transSchema.getPayTimes())){
            for(int i=0;i<4;i++){
                PrpTplanSchema          prptplanSchema          = new PrpTplanSchema();
                prptplanSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
                prptplanSchema.setSerialNo((i+1)+"");
                prptplanSchema.setPayNo(prptplanSchema.getSerialNo());
                prptplanSchema.setPayReason("R10");
                
                if(i==0){
                    prptplanSchema.setFlag("  "+transSchema.getNewpaymode());
                }else{
                    prptplanSchema.setPayReason("R20");
                    prptplanSchema.setFlag("  "+transSchema.getExtpaymode());
                }
                java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
                calendar.setTime( PubTools.stringToUtilDate(transSchema.getStartDate()));   
                calendar.add(Calendar.MONTH,+i*3);
                int   year   =   calendar.get(Calendar.YEAR);   
                int   month   =   calendar.get(Calendar.MONTH)+1;   
                int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
                prptplanSchema.setPlanStartDate(year+"-"+month+"-"+day);
                prptplanSchema.setCurrency("CNY");
                calendar.add(Calendar.DATE,+0);
                year   =   calendar.get(Calendar.YEAR);   
                month   =   calendar.get(Calendar.MONTH)+1;   
                day   =   calendar.get(Calendar.DAY_OF_MONTH);
                prptplanSchema.setPlanDate(year+"-"+month+"-"+day);
                if(i<3){
                    fontfee= fontfee+ Double.parseDouble(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium())/4;
                    
                    prptplanSchema.setPlanFee(Double.parseDouble(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium())/4+""); 
                    prptplanSchema.setDelinquentFee(Double.parseDouble(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium())/4+""); 
                }else{
                    prptplanSchema.setPlanFee((Double.parseDouble(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium())-fontfee)+"");
                    prptplanSchema.setDelinquentFee((Double.parseDouble(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium())-fontfee)+"");
                    
                }
                blMainProposal.getBLPrpTplan().setArr(prptplanSchema);
            }
        }else{
            for(int i=0;i<12;i++){
                PrpTplanSchema          prptplanSchema          = new PrpTplanSchema();
                prptplanSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
                prptplanSchema.setSerialNo((i+1)+"");
                prptplanSchema.setPayNo(prptplanSchema.getSerialNo());
                prptplanSchema.setPayReason("R10");
                
                if(i<2){
                    prptplanSchema.setFlag("  "+transSchema.getNewpaymode());
                }else{
                    prptplanSchema.setPayReason("R20");
                    prptplanSchema.setFlag("  "+transSchema.getExtpaymode());
                }
                java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
                calendar.setTime( PubTools.stringToUtilDate(transSchema.getStartDate()));   
                calendar.add(Calendar.MONTH,+i);
                int   year   =   calendar.get(Calendar.YEAR);   
                int   month   =   calendar.get(Calendar.MONTH)+1;   
                int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
                prptplanSchema.setPlanStartDate(year+"-"+month+"-"+day);
                prptplanSchema.setCurrency("CNY");
                calendar.add(Calendar.DATE,+0);
                year   =   calendar.get(Calendar.YEAR);   
                month   =   calendar.get(Calendar.MONTH)+1;   
                day   =   calendar.get(Calendar.DAY_OF_MONTH);
                prptplanSchema.setPlanDate(year+"-"+month+"-"+day);
                if(i<11){
                    fontfee= fontfee+ Double.parseDouble(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium())/12;
                    
                    prptplanSchema.setPlanFee(Double.parseDouble(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium())/12+""); 
                    prptplanSchema.setDelinquentFee(Double.parseDouble(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium())/12+""); 
                }else{
                    prptplanSchema.setPlanFee((Double.parseDouble(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium())-fontfee)+"");
                    prptplanSchema.setDelinquentFee((Double.parseDouble(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium())-fontfee)+"");
                }
                blMainProposal.getBLPrpTplan().setArr(prptplanSchema);
            }
        }
        
        fontfee=0.0d;
        if("1".equals(transSchema.getPayTimes())){
            PrpTplanSchema          prptplanSchemaSubOne          = new PrpTplanSchema();
            prptplanSchemaSubOne.setProposalNo(blSubOneProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prptplanSchemaSubOne.setSerialNo("1");
            prptplanSchemaSubOne.setPayNo("1");
            prptplanSchemaSubOne.setPayReason("R10");
            prptplanSchemaSubOne.setFlag("  "+transSchema.getNewpaymode());
            java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
            calendar.setTime( PubTools.stringToUtilDate(transSchema.getStartDate()));   
            calendar.add(Calendar.DATE,+0);
            int   year   =   calendar.get(Calendar.YEAR);   
            int   month   =   calendar.get(Calendar.MONTH)+1;   
            int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
            prptplanSchemaSubOne.setPlanStartDate(transSchema.getStartDate());
            prptplanSchemaSubOne.setPlanDate(year+"-"+month+"-"+day);
            prptplanSchemaSubOne.setCurrency("CNY");
            prptplanSchemaSubOne.setPlanFee(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium());
            prptplanSchemaSubOne.setDelinquentFee(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium());
            blSubOneProposal.getBLPrpTplan().setArr(prptplanSchemaSubOne);
        }else if("3".equals(transSchema.getPayTimes())){
            for(int i=0;i<4;i++){
                PrpTplanSchema          prptplanSchemaSubOne          = new PrpTplanSchema();
                prptplanSchemaSubOne.setProposalNo(blSubOneProposal.getBLPrpTmain().getArr(0).getProposalNo());
                prptplanSchemaSubOne.setSerialNo((i+1)+"");
                prptplanSchemaSubOne.setPayNo(prptplanSchemaSubOne.getSerialNo());
                prptplanSchemaSubOne.setPayReason("R10");
                
                if(i==0){
                    prptplanSchemaSubOne.setFlag("  "+transSchema.getNewpaymode());
                }else{
                    prptplanSchemaSubOne.setPayReason("R20");
                    prptplanSchemaSubOne.setFlag("  "+transSchema.getExtpaymode());
                }
                java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
                calendar.setTime( PubTools.stringToUtilDate(transSchema.getStartDate()));   
                calendar.add(Calendar.MONTH,+i*3);
                int   year   =   calendar.get(Calendar.YEAR);   
                int   month   =   calendar.get(Calendar.MONTH)+1;   
                int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
                prptplanSchemaSubOne.setPlanStartDate(year+"-"+month+"-"+day);
                prptplanSchemaSubOne.setCurrency("CNY");
                calendar.add(Calendar.DATE,+0);
                year   =   calendar.get(Calendar.YEAR);   
                month   =   calendar.get(Calendar.MONTH)+1;   
                day   =   calendar.get(Calendar.DAY_OF_MONTH);
                prptplanSchemaSubOne.setPlanDate(year+"-"+month+"-"+day);
                if(i<3){
                    fontfee= fontfee+ Double.parseDouble(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium())/4;
                    
                    prptplanSchemaSubOne.setPlanFee(Double.parseDouble(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium())/4+""); 
                    prptplanSchemaSubOne.setDelinquentFee(Double.parseDouble(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium())/4+""); 
                }else{
                    prptplanSchemaSubOne.setPlanFee((Double.parseDouble(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium())-fontfee)+"");
                    prptplanSchemaSubOne.setDelinquentFee((Double.parseDouble(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium())-fontfee)+"");
                    
                }
                blSubOneProposal.getBLPrpTplan().setArr(prptplanSchemaSubOne);
            }
        }else{
            for(int i=0;i<12;i++){
                PrpTplanSchema          prptplanSchemaSubOne          = new PrpTplanSchema();
                prptplanSchemaSubOne.setProposalNo(blSubOneProposal.getBLPrpTmain().getArr(0).getProposalNo());
                prptplanSchemaSubOne.setSerialNo((i+1)+"");
                prptplanSchemaSubOne.setPayNo(prptplanSchemaSubOne.getSerialNo());
                prptplanSchemaSubOne.setPayReason("R10");
                
                if(i<2){
                    prptplanSchemaSubOne.setFlag("  "+transSchema.getNewpaymode());
                }else{
                    prptplanSchemaSubOne.setFlag("  "+transSchema.getExtpaymode());
                    prptplanSchemaSubOne.setPayReason("R20");
                }
                java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
                calendar.setTime( PubTools.stringToUtilDate(transSchema.getStartDate()));   
                calendar.add(Calendar.MONTH,+i);
                int   year   =   calendar.get(Calendar.YEAR);   
                int   month   =   calendar.get(Calendar.MONTH)+1;   
                int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
                prptplanSchemaSubOne.setPlanStartDate(year+"-"+month+"-"+day);
                prptplanSchemaSubOne.setCurrency("CNY");
                calendar.add(Calendar.DATE,+0);
                year   =   calendar.get(Calendar.YEAR);   
                month   =   calendar.get(Calendar.MONTH)+1;   
                day   =   calendar.get(Calendar.DAY_OF_MONTH);
                prptplanSchemaSubOne.setPlanDate(year+"-"+month+"-"+day);
                if(i<11){
                    fontfee= fontfee+ Double.parseDouble(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium())/12;
                    
                    prptplanSchemaSubOne.setPlanFee(Double.parseDouble(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium())/12+""); 
                    prptplanSchemaSubOne.setDelinquentFee(Double.parseDouble(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium())/12+""); 
                }else{
                    prptplanSchemaSubOne.setPlanFee((Double.parseDouble(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium())-fontfee)+"");
                    prptplanSchemaSubOne.setDelinquentFee((Double.parseDouble(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium())-fontfee)+"");
                }
                blSubOneProposal.getBLPrpTplan().setArr(prptplanSchemaSubOne);
            }
        }        
        
        fontfee=0.0d;
        if("1".equals(transSchema.getPayTimes())){
            PrpTplanSchema          prptplanSchemaSubTow          = new PrpTplanSchema();
            prptplanSchemaSubTow.setProposalNo(blSubTowProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prptplanSchemaSubTow.setSerialNo("1");
            prptplanSchemaSubTow.setPayNo("1");
            prptplanSchemaSubTow.setPayReason("R10");
            prptplanSchemaSubTow.setFlag("  "+transSchema.getNewpaymode());
            java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
            calendar.setTime( PubTools.stringToUtilDate(transSchema.getStartDate()));   
            calendar.add(Calendar.DATE,+0);
            int   year   =   calendar.get(Calendar.YEAR);   
            int   month   =   calendar.get(Calendar.MONTH)+1;   
            int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
            prptplanSchemaSubTow.setPlanStartDate(transSchema.getStartDate());
            prptplanSchemaSubTow.setPlanDate(year+"-"+month+"-"+day);
            prptplanSchemaSubTow.setCurrency("CNY");
            prptplanSchemaSubTow.setPlanFee(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium());
            prptplanSchemaSubTow.setDelinquentFee(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium());
            blSubTowProposal.getBLPrpTplan().setArr(prptplanSchemaSubTow);
        }else if("3".equals(transSchema.getPayTimes())){
            for(int i=0;i<4;i++){
                PrpTplanSchema          prptplanSchemaSubTow          = new PrpTplanSchema();
                prptplanSchemaSubTow.setProposalNo(blSubTowProposal.getBLPrpTmain().getArr(0).getProposalNo());
                prptplanSchemaSubTow.setSerialNo((i+1)+"");
                prptplanSchemaSubTow.setPayNo(prptplanSchemaSubTow.getSerialNo());
                prptplanSchemaSubTow.setPayReason("R10");
                
                if(i==0){
                    prptplanSchemaSubTow.setFlag("  "+transSchema.getNewpaymode());
                }else{
                    prptplanSchemaSubTow.setFlag("  "+transSchema.getExtpaymode());
                    prptplanSchemaSubTow.setPayReason("R20");
                }
                java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
                calendar.setTime( PubTools.stringToUtilDate(transSchema.getStartDate()));   
                calendar.add(Calendar.MONTH,+i*3);
                int   year   =   calendar.get(Calendar.YEAR);   
                int   month   =   calendar.get(Calendar.MONTH)+1;   
                int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
                prptplanSchemaSubTow.setPlanStartDate(year+"-"+month+"-"+day);
                prptplanSchemaSubTow.setCurrency("CNY");
                calendar.add(Calendar.DATE,+0);
                year   =   calendar.get(Calendar.YEAR);   
                month   =   calendar.get(Calendar.MONTH)+1;   
                day   =   calendar.get(Calendar.DAY_OF_MONTH);
                prptplanSchemaSubTow.setPlanDate(year+"-"+month+"-"+day);
                if(i<3){
                    fontfee= fontfee+ Double.parseDouble(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium())/4;
                    
                    prptplanSchemaSubTow.setPlanFee(Double.parseDouble(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium())/4+""); 
                    prptplanSchemaSubTow.setDelinquentFee(Double.parseDouble(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium())/4+""); 
                }else{
                    prptplanSchemaSubTow.setPlanFee((Double.parseDouble(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium())-fontfee)+"");
                    prptplanSchemaSubTow.setDelinquentFee((Double.parseDouble(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium())-fontfee)+"");
                    
                }
                blSubTowProposal.getBLPrpTplan().setArr(prptplanSchemaSubTow);
            }
        }else{
            for(int i=0;i<12;i++){
                PrpTplanSchema          prptplanSchemaSubTow          = new PrpTplanSchema();
                prptplanSchemaSubTow.setProposalNo(blSubTowProposal.getBLPrpTmain().getArr(0).getProposalNo());
                prptplanSchemaSubTow.setSerialNo((i+1)+"");
                prptplanSchemaSubTow.setPayNo(prptplanSchemaSubTow.getSerialNo());
                prptplanSchemaSubTow.setPayReason("R10");
                
                if(i<2){
                    prptplanSchemaSubTow.setFlag("  "+transSchema.getNewpaymode());
                }else{
                    prptplanSchemaSubTow.setFlag("  "+transSchema.getExtpaymode());
                    prptplanSchemaSubTow.setPayReason("R20");
                }
                java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
                calendar.setTime( PubTools.stringToUtilDate(transSchema.getStartDate()));   
                calendar.add(Calendar.MONTH,+i);
                int   year   =   calendar.get(Calendar.YEAR);   
                int   month   =   calendar.get(Calendar.MONTH)+1;   
                int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
                prptplanSchemaSubTow.setPlanStartDate(year+"-"+month+"-"+day);
                prptplanSchemaSubTow.setCurrency("CNY");
                calendar.add(Calendar.DATE,+0);
                year   =   calendar.get(Calendar.YEAR);   
                month   =   calendar.get(Calendar.MONTH)+1;   
                day   =   calendar.get(Calendar.DAY_OF_MONTH);
                prptplanSchemaSubTow.setPlanDate(year+"-"+month+"-"+day);
                if(i<11){
                    fontfee= fontfee+ Double.parseDouble(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium())/12;
                    
                    prptplanSchemaSubTow.setPlanFee(Double.parseDouble(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium())/12+""); 
                    prptplanSchemaSubTow.setDelinquentFee(Double.parseDouble(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium())/12+""); 
                }else{
                    prptplanSchemaSubTow.setPlanFee((Double.parseDouble(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium())-fontfee)+"");
                    prptplanSchemaSubTow.setDelinquentFee((Double.parseDouble(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium())-fontfee)+"");
                }
                blSubTowProposal.getBLPrpTplan().setArr(prptplanSchemaSubTow);
            }
        }
    }
    
    /**
     * @Author     刘佳
     * @description 费用信息转储
     * @param        blMainProposal大XX单对象
     * @param        blSubOneProposal2700XX单对象
     * @param        blSubTowProposal0302XX单对象
     * @throws Exception 
     */
    public void transTfee(BLProposal blMainProposal,
                           BLProposal blSubOneProposal,
                           BLProposal blSubTowProposal) throws Exception{
        PrpTfeeSchema           prpTfeeSchema           = new PrpTfeeSchema();
        prpTfeeSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTfeeSchema.setRiskCode("3009");
        prpTfeeSchema.setCurrency("CNY");
        prpTfeeSchema.setAmount(blMainProposal.getBLPrpTmain().getArr(0).getSumAmount());
        prpTfeeSchema.setPremium(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium());
        prpTfeeSchema.setCurrency1("CNY");
        prpTfeeSchema.setExchangeRate1("1.00000000");
        prpTfeeSchema.setAmount1(blMainProposal.getBLPrpTmain().getArr(0).getSumAmount());
        prpTfeeSchema.setPremium1(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium());
        prpTfeeSchema.setCurrency2("CNY");
        prpTfeeSchema.setExchangeRate2("1.00000000");
        prpTfeeSchema.setAmount2(blMainProposal.getBLPrpTmain().getArr(0).getSumAmount());
        prpTfeeSchema.setPremium2(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium());
        blMainProposal.getBLPrpTfee().setArr(prpTfeeSchema);
        
        PrpTfeeSchema           prpTfeeSchemaSubOne           = new PrpTfeeSchema();
        prpTfeeSchemaSubOne.setProposalNo(blSubOneProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTfeeSchemaSubOne.setRiskCode("2700");
        prpTfeeSchemaSubOne.setCurrency("CNY");
        prpTfeeSchemaSubOne.setAmount(blSubOneProposal.getBLPrpTmain().getArr(0).getSumAmount());
        prpTfeeSchemaSubOne.setPremium(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium());
        prpTfeeSchemaSubOne.setCurrency1("CNY");
        prpTfeeSchemaSubOne.setExchangeRate1("1.00000000");
        prpTfeeSchemaSubOne.setAmount1(blSubOneProposal.getBLPrpTmain().getArr(0).getSumAmount());
        prpTfeeSchemaSubOne.setPremium1(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium());
        prpTfeeSchemaSubOne.setCurrency2("CNY");
        prpTfeeSchemaSubOne.setExchangeRate2("1.00000000");
        prpTfeeSchemaSubOne.setAmount2(blSubOneProposal.getBLPrpTmain().getArr(0).getSumAmount());
        prpTfeeSchemaSubOne.setPremium2(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium());
        blSubOneProposal.getBLPrpTfee().setArr(prpTfeeSchemaSubOne);
        
        PrpTfeeSchema           prpTfeeSchemaSubTow           = new PrpTfeeSchema();
        prpTfeeSchemaSubTow.setProposalNo(blSubTowProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTfeeSchemaSubTow.setRiskCode("0302");
        prpTfeeSchemaSubTow.setCurrency("CNY");
        prpTfeeSchemaSubTow.setAmount(blSubTowProposal.getBLPrpTmain().getArr(0).getSumAmount());
        prpTfeeSchemaSubTow.setPremium(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium());
        prpTfeeSchemaSubTow.setCurrency1("CNY");
        prpTfeeSchemaSubTow.setExchangeRate1("1.00000000");
        prpTfeeSchemaSubTow.setAmount1(blSubTowProposal.getBLPrpTmain().getArr(0).getSumAmount());
        prpTfeeSchemaSubTow.setPremium1(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium());
        prpTfeeSchemaSubTow.setCurrency2("CNY");
        prpTfeeSchemaSubTow.setExchangeRate2("1.00000000");
        prpTfeeSchemaSubTow.setAmount2(blSubTowProposal.getBLPrpTmain().getArr(0).getSumAmount());
        prpTfeeSchemaSubTow.setPremium2(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium());
        blSubTowProposal.getBLPrpTfee().setArr(prpTfeeSchemaSubTow);
    }
    
    /**
     * @Author     刘佳
     * @description 手续飞信息转储
     * @param        blMainProposal大XX单对象
     * @param        blSubOneProposal2700XX单对象
     * @param        blSubTowProposal0302XX单对象
     * @throws Exception 
     */
    public void transTexpense(BLProposal blMainProposal,
                               BLProposal blSubOneProposal,
                               BLProposal blSubTowProposal) throws Exception{
        PrpTexpenseSchema       prpTexpenseSchema       = new PrpTexpenseSchema();
        prpTexpenseSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTexpenseSchema.setRiskCode("3009");
        prpTexpenseSchema.setFlag("I1");
        blMainProposal.getBLPrpTexpense().setArr(prpTexpenseSchema);
        PrpTexpenseSchema       prpTexpenseSchemaSubOne       = new PrpTexpenseSchema();
        prpTexpenseSchemaSubOne.setProposalNo(blSubOneProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTexpenseSchemaSubOne.setRiskCode("2700");
        prpTexpenseSchemaSubOne.setFlag("I1");
        blSubOneProposal.getBLPrpTexpense().setArr(prpTexpenseSchemaSubOne);
        PrpTexpenseSchema       prpTexpenseSchemaSubTow       = new PrpTexpenseSchema();
        prpTexpenseSchemaSubTow.setProposalNo(blSubTowProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTexpenseSchemaSubTow.setRiskCode("0302");
        prpTexpenseSchemaSubTow.setFlag("I1");
        blSubTowProposal.getBLPrpTexpense().setArr(prpTexpenseSchemaSubTow);
    }
    
    /**
     * @Author     刘佳
     * @description 关联信息转储
     * @param        blSubOneProposal2700XX单对象
     * @param        blSubTowProposal0302XX单对象
     * @param        mainPolicyno大XX单号
     * @throws Exception 
     */
    public void transTmainSub(BLProposal blSubOneProposal,
                               BLProposal blSubTowProposal,
                               String mainPolicyno,TransSchema transSchema) throws Exception{
        PrpTmainSubSchema       prpTmainSubSchemaSubOne       = new PrpTmainSubSchema();
        prpTmainSubSchemaSubOne.setProposalNo(blSubOneProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTmainSubSchemaSubOne.setMainPolicyNo(mainPolicyno);
        
        
        PrpTmainSubSchema       prpTmainSubSchemaSubTow       = new PrpTmainSubSchema();
        prpTmainSubSchemaSubTow.setProposalNo(blSubTowProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTmainSubSchemaSubTow.setMainPolicyNo(mainPolicyno);
        
        
        if("1".equals(transSchema.getRiskFlag())){
            prpTmainSubSchemaSubOne.setFlag("2");
            prpTmainSubSchemaSubTow.setFlag("2");
        }else{
            prpTmainSubSchemaSubOne.setFlag("1");
        }
        blSubOneProposal.getBLPrpTmainSub().setArr(prpTmainSubSchemaSubOne);
        blSubTowProposal.getBLPrpTmainSub().setArr(prpTmainSubSchemaSubTow);
    }
    
    /**
     * @Author     刘佳
     * @description 方案表信息转储
     * @param        blMainProposal3004XX单对象
     * @param        blSubOneProposal2700XX单对象
     * @param        grade方案
     * @throws Exception 
     */
    public void transTmainCasualty(BLProposal blMainProposal, 
                                    BLProposal blSubOneProposal,
                                    String grade) throws Exception{
        
        PrpTmainCasualtySchema  prpTmainCasualtySchemaMain  = new PrpTmainCasualtySchema();
        prpTmainCasualtySchemaMain.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTmainCasualtySchemaMain.setRiskCode("3009");
        prpTmainCasualtySchemaMain.setBusinessGrade(grade);  
        blMainProposal.getBLPrpTmainCasualty().setArr(prpTmainCasualtySchemaMain);
        PrpTmainCasualtySchema  prpTmainCasualtySchema  = new PrpTmainCasualtySchema();
        prpTmainCasualtySchema.setProposalNo(blSubOneProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTmainCasualtySchema.setRiskCode("2700");
        prpTmainCasualtySchema.setBusinessGrade(grade);  
        blSubOneProposal.getBLPrpTmainCasualty().setArr(prpTmainCasualtySchema);
    }
    
    /**
     * @Author     刘佳
     * @description 地址信表信息转储
     * @param        blMainProposal3004XX单对象
     * @param        blSubOneProposal2700XX单对象
     * @param        TMLCIinsuredPCList地址信息集合
     * @throws Exception 
     */
    public void transTaddress(BLProposal blMainProposal ,
                               BLProposal  blSubTowProposal,
                               ArrayList  TMLCIinsuredPCList) throws Exception{
        
        ArrayList tempAddress=(ArrayList)TMLCIinsuredPCList.get(0);
        String postCode=(String)tempAddress.get(2);
        String addressName=(String)tempAddress.get(1);
        PrpTaddressSchema       prpTaddressSchemaMain       = new PrpTaddressSchema();
        prpTaddressSchemaMain.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTaddressSchemaMain.setRiskCode("3009");
        prpTaddressSchemaMain.setAddressNo("1");
        prpTaddressSchemaMain.setAddressCode(postCode);
        prpTaddressSchemaMain.setAddressName(addressName);
        blMainProposal.getBLPrpTaddress().setArr(prpTaddressSchemaMain);
        PrpTaddressSchema       prpTaddressSchema       = new PrpTaddressSchema();
        prpTaddressSchema.setProposalNo(blSubTowProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTaddressSchema.setRiskCode("0302");
        prpTaddressSchema.setAddressNo("1");
        prpTaddressSchema.setAddressCode(postCode);
        prpTaddressSchema.setAddressName(addressName);
        
        blSubTowProposal.getBLPrpTaddress().setArr(prpTaddressSchema);
    }
    

    /**
     * @Author     刘佳
     * @description 小XX单XXXXX别信息转入
     * @param        blMainProposal大XX单对象
     * @param        blSubOneProposal2700XX关系人信息对象
     * @param        blSubTowProposal0302转数关键要素信息
     * @param        permiumAmountSchemaXXXX对象
     * * @param      transSchema转数关键要素信息
     * @throws Exception 
     */
    public void transTitemKindSub(BLProposal blMainProposal,
                                   BLProposal blSubOneProposal,
                                   BLProposal blSubTowProposal,
                                   PermiumAmountSchema permiumAmountSchema,
                                   TransSchema transSchema) throws Exception{
        PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
        BLPrpTitemKind blPrpTitemKind = blMainProposal.getBLPrpTitemKind();
        double sum27Amount=0.0d;
        double sum03Amount=0.0d;
        double sum27Permium=0.0d;
        double sum03Permium=0.0d;
        BLPrpTitemKind BLPrpTitemKindSubOne = new BLPrpTitemKind();
        BLPrpTitemKind BLPrpTitemKindSubTow = new BLPrpTitemKind();
        for(int i=0;i<blMainProposal.getBLPrpTitemKind().getSize();i++){
            PrpTitemKindSchema prpTitemKindSchemaSubOne = new PrpTitemKindSchema();
            PrpTitemKindSchema prpTitemKindSchemaSubTow = new PrpTitemKindSchema();
            blPrpTitemKind =blMainProposal.getBLPrpTitemKind();
            prpTitemKindSchema=blPrpTitemKind.getArr(i);
            if(prpTitemKindSchema.getKindCode().equals("001")){
                prpTitemKindSchemaSubTow.setProposalNo(transSchema.getSubTowProposalNo());
                prpTitemKindSchemaSubTow.setRiskCode("0302");
                prpTitemKindSchemaSubTow.setItemKindNo(prpTitemKindSchema.getItemKindNo());
                prpTitemKindSchemaSubTow.setFamilyNo(prpTitemKindSchema.getFamilyNo());
                prpTitemKindSchemaSubTow.setFamilyName(prpTitemKindSchema.getFamilyName());
                prpTitemKindSchemaSubTow.setKindCode(prpTitemKindSchema.getKindCode());
                prpTitemKindSchemaSubTow.setKindName(prpTitemKindSchema.getKindName());
                prpTitemKindSchemaSubTow.setItemCode(prpTitemKindSchema.getItemCode());
                prpTitemKindSchemaSubTow.setItemDetailName(prpTitemKindSchema.getItemDetailName());
                prpTitemKindSchemaSubTow.setStartDate(prpTitemKindSchema.getStartDate());
                prpTitemKindSchemaSubTow.setStartHour("0");
                prpTitemKindSchemaSubTow.setEndDate(prpTitemKindSchema.getEndDate());
                prpTitemKindSchemaSubTow.setEndHour("24");
                prpTitemKindSchemaSubTow.setCalculateFlag(prpTitemKindSchema.getCalculateFlag());
                prpTitemKindSchemaSubTow.setCurrency("CNY");
                prpTitemKindSchemaSubTow.setUnitAmount(prpTitemKindSchema.getUnitAmount());
                prpTitemKindSchemaSubTow.setQuantity(prpTitemKindSchema.getQuantity());
                prpTitemKindSchemaSubTow.setValue(prpTitemKindSchema.getValue());
                prpTitemKindSchemaSubTow.setAmount(prpTitemKindSchema.getAmount());
                prpTitemKindSchemaSubTow.setRate(prpTitemKindSchema.getRate());
                prpTitemKindSchemaSubTow.setShortRateFlag(prpTitemKindSchema.getShortRateFlag());
                prpTitemKindSchemaSubTow.setShortRate(prpTitemKindSchema.getShortRate());
                prpTitemKindSchemaSubTow.setDiscount(prpTitemKindSchema.getDiscount());
                prpTitemKindSchemaSubTow.setPremium(prpTitemKindSchema.getPremium());
                if(prpTitemKindSchema.getCalculateFlag().equals("Y"))
                {
                    sum03Amount=sum03Amount+Double.parseDouble(prpTitemKindSchema.getAmount());
                
                }
                sum03Permium=sum03Permium+Double.parseDouble(prpTitemKindSchemaSubTow.getPremium());
                prpTitemKindSchemaSubTow.setFlag(prpTitemKindSchema.getFlag());
                BLPrpTitemKindSubTow.setArr(prpTitemKindSchemaSubTow);
                blSubTowProposal.setBLPrpTitemKind(BLPrpTitemKindSubTow);
            }else{
                prpTitemKindSchemaSubOne.setProposalNo(transSchema.getSubOneProposalNo());
                prpTitemKindSchemaSubOne.setRiskCode("2700");
                prpTitemKindSchemaSubOne.setItemKindNo(prpTitemKindSchema.getItemKindNo());
                prpTitemKindSchemaSubOne.setFamilyNo(prpTitemKindSchema.getFamilyNo());
                prpTitemKindSchemaSubOne.setFamilyName(prpTitemKindSchema.getFamilyName());
                prpTitemKindSchemaSubOne.setKindCode(prpTitemKindSchema.getKindCode());
                prpTitemKindSchemaSubOne.setKindName(prpTitemKindSchema.getKindName());
                prpTitemKindSchemaSubOne.setItemCode(prpTitemKindSchema.getItemCode());
                prpTitemKindSchemaSubOne.setItemDetailName(prpTitemKindSchema.getItemDetailName());
                prpTitemKindSchemaSubOne.setStartDate(prpTitemKindSchema.getStartDate());
                prpTitemKindSchemaSubOne.setStartHour("0");
                prpTitemKindSchemaSubOne.setEndDate(prpTitemKindSchema.getEndDate());
                prpTitemKindSchemaSubOne.setEndHour("24");
                prpTitemKindSchemaSubOne.setCalculateFlag(prpTitemKindSchema.getCalculateFlag());
                prpTitemKindSchemaSubOne.setCurrency("CNY");
                prpTitemKindSchemaSubOne.setUnitAmount(prpTitemKindSchema.getUnitAmount());
                prpTitemKindSchemaSubOne.setQuantity(prpTitemKindSchema.getQuantity());
                prpTitemKindSchemaSubOne.setValue(prpTitemKindSchema.getValue());
                prpTitemKindSchemaSubOne.setAmount(prpTitemKindSchema.getAmount());
                prpTitemKindSchemaSubOne.setRate(prpTitemKindSchema.getRate());
                prpTitemKindSchemaSubOne.setShortRateFlag(prpTitemKindSchema.getShortRateFlag());
                prpTitemKindSchemaSubOne.setShortRate(prpTitemKindSchema.getShortRate());
                prpTitemKindSchemaSubOne.setDiscount(prpTitemKindSchema.getDiscount());
                prpTitemKindSchemaSubOne.setPremium(prpTitemKindSchema.getPremium());
                prpTitemKindSchemaSubOne.setFlag(prpTitemKindSchema.getFlag());
                if(prpTitemKindSchema.getCalculateFlag().equals("Y")){
                    sum27Amount=sum27Amount+Double.parseDouble(prpTitemKindSchema.getAmount());
                }
                sum27Permium=sum27Permium+Double.parseDouble(prpTitemKindSchemaSubOne.getPremium());
                BLPrpTitemKindSubOne.setArr(prpTitemKindSchemaSubOne);
                blSubOneProposal.setBLPrpTitemKind(BLPrpTitemKindSubOne);
            }
            permiumAmountSchema.setSum27Amount(sum27Amount+"");
            permiumAmountSchema.setSum03Amount(sum03Amount+"") ;
            permiumAmountSchema.setSum27Permium(sum27Permium+"");
            permiumAmountSchema.setSum03Permium(sum03Permium+"");  
        }
    }
    
    /**
     * @Author     刘佳
     * @description 大XX单XXXXX别信息转入
     * @param        blMainProposal大XX单对象
     * @param        blPrpInsured大XX单关系人信息对象
     * @param        transSchema转数关键要素信息
     * @param        permiumAmountSchemaXXXX对象
     * @throws Exception 
     */
    public void transTitemKindMain(BLProposal blMainProposal,
                                    BLPrpTinsured blPrpInsured,
                                    TransSchema transSchema,
                                    PermiumAmountSchema permiumAmountSchema) throws Exception{
        String selfName = "";
        String wifeName = "";
        String childName = "";
        String selfFamilyNo = "";
        String wifeFamilyNo = "";
        String childFamilyNo = "";
        
    	double unitAmount = 0.00;
    	
        PrpTinsuredSchema insuredSchema = null;
        for(int i=0;i<blMainProposal.getBLPrpTinsured().getSize();i++){
            insuredSchema = blMainProposal.getBLPrpTinsured().getArr(i);
            if("1".equals(insuredSchema.getInsuredFlag())){
                if("01".equals(insuredSchema.getInsuredIdentity())){
                    selfName = insuredSchema.getInsuredName();
                    selfFamilyNo = insuredSchema.getSerialNo();
                }
                else if("10".equals(insuredSchema.getInsuredIdentity())){
                    wifeName = insuredSchema.getInsuredName();
                    wifeFamilyNo = insuredSchema.getSerialNo();
                }
                else if("40".equals(insuredSchema.getInsuredIdentity())){
                    childName = insuredSchema.getInsuredName();   
                    childFamilyNo = insuredSchema.getSerialNo();
                }
            }
        }
        
        String rationType=transSchema.getPolicyType()+transSchema.getGrade()+"01";
        BLPrpDration prpdration = new BLPrpDration();
        DBPrpDkind dbPrpDkind= null;
        BLPrpDitem blPrpDitem=null;
        dbPrpDkind = new DBPrpDkind();
        double sumAmount=0.00d;
        double sumPermium=0.0d;
        prpdration=getRateGroup(rationType,"01",transSchema.getRiskFlag());
        BLPrpTitemKind blPrpTitemKind = new BLPrpTitemKind();
        BLPrpDkind blPrpDkind = new BLPrpDkind();
        blPrpDkind.query(" riskcode='3009' ");
        String CalculateFlagString ="";
        for(int n=0;n<blPrpDkind.getSize();n++){
            if(blPrpDkind.getArr(n).getCalculateFlag().substring(0,1).equals("Y")){
                CalculateFlagString=CalculateFlagString+blPrpDkind.getArr(n).getKindCode()+",";
            }
        }
        for(int j=0;j<prpdration.getSize();j++){
            String tempKindCode=prpdration.getArr(j).getKindCode();
            if(!tempKindCode.equals("001")){
                PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
                prpTitemKindSchema.setProposalNo(transSchema.getMainProposalNo());
                prpTitemKindSchema.setRiskCode("3009");
                prpTitemKindSchema.setItemKindNo((blPrpTitemKind.getSize() + 1) + "");
                prpTitemKindSchema.setFamilyNo("2");
                prpTitemKindSchema.setFamilyName(selfName);
                prpTitemKindSchema.setKindCode(prpdration.getArr(j).getKindCode());
                dbPrpDkind.getInfo("3009",prpdration.getArr(j).getKindCode());
                String strKindName = dbPrpDkind.getKindCName();
                blPrpDitem = new BLPrpDitem();
                String strItemDetailName = blPrpDitem.translateCode("3009",prpdration.getArr(j).getItemCode(),true);
                prpTitemKindSchema.setKindName(strKindName);
                prpTitemKindSchema.setItemCode(prpdration.getArr(j).getItemCode());
                prpTitemKindSchema.setItemDetailName(strItemDetailName);
                prpTitemKindSchema.setStartDate(transSchema.getStartDate());
                prpTitemKindSchema.setStartHour("0");
                prpTitemKindSchema.setEndDate(transSchema.getEndDate());
                prpTitemKindSchema.setEndHour("24");
                
                
				if (prpdration.getArr(j).getAmountFlag() != null
						&& prpdration.getArr(j).getAmountFlag().toString().trim()
								.length() > 0) {
					if ("0".equals(prpdration.getArr(j).getAmountFlag())) {
						prpTitemKindSchema.setCalculateFlag("N");
					} else {
						prpTitemKindSchema.setCalculateFlag("Y");
					}
				} else {
				
					if (CalculateFlagString.indexOf(prpTitemKindSchema
							.getKindCode()) > -1) {
						prpTitemKindSchema.setCalculateFlag("Y");
					} else {
						prpTitemKindSchema.setCalculateFlag("N");
					}
				}
                prpTitemKindSchema.setCurrency("CNY");
                if(prpTitemKindSchema.getKindCode().equals("001")){
                   prpTitemKindSchema.setShortRateFlag("1");
                }else{
                    prpTitemKindSchema.setUnitAmount(prpdration.getArr(j).getAmount());
                    prpTitemKindSchema.setQuantity("1.00");
                    prpTitemKindSchema.setValue("1.00");
                    prpTitemKindSchema.setDiscount("100.0000");
                    prpTitemKindSchema.setShortRateFlag("3");
                }
                prpTitemKindSchema.setAmount(prpdration.getArr(j).getAmount());
                prpTitemKindSchema.setRate(""+(1000.0*Double.parseDouble(prpdration.getArr(j).getRate())));
                prpTitemKindSchema.setShortRate("100");
                prpTitemKindSchema.setPremium(prpdration.getArr(j).getPremium());
                if(prpTitemKindSchema.getCalculateFlag().equals("Y")){
                    sumAmount=sumAmount+Double.parseDouble(prpdration.getArr(j).getAmount());
                }
                sumPermium=sumPermium+Double.parseDouble(prpdration.getArr(j).getPremium());
                if(prpTitemKindSchema.getKindCode().equals("001")){
                    prpTitemKindSchema.setFlag(" 3");
                }else if(prpTitemKindSchema.getKindCode().equals("182")||prpTitemKindSchema.getKindCode().equals("165")){
                     prpTitemKindSchema.setFlag(" 2");
                }else{
                     prpTitemKindSchema.setFlag(" 1");
                }
                blPrpTitemKind.setArr(prpTitemKindSchema);
            }
                
        }
        
        for(int j=0;j<prpdration.getSize();j++){
            String tempKindCode=prpdration.getArr(j).getKindCode();
            if(tempKindCode.equals("001")){
                PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
                prpTitemKindSchema.setProposalNo(transSchema.getMainProposalNo());
                prpTitemKindSchema.setRiskCode("3009");
                prpTitemKindSchema.setItemKindNo((blPrpTitemKind.getSize() + 1) + "");
                prpTitemKindSchema.setFamilyNo("");
                prpTitemKindSchema.setKindCode(prpdration.getArr(j).getKindCode());
                dbPrpDkind.getInfo("3009",prpdration.getArr(j).getKindCode());
                String strKindName = dbPrpDkind.getKindCName();
                blPrpDitem = new BLPrpDitem();
                String strItemDetailName = blPrpDitem.translateCode("3009",prpdration.getArr(j).getItemCode(),true);
                prpTitemKindSchema.setKindName(strKindName);
                prpTitemKindSchema.setItemCode(prpdration.getArr(j).getItemCode());
                prpTitemKindSchema.setItemDetailName(strItemDetailName);
                prpTitemKindSchema.setStartDate(transSchema.getStartDate());
                prpTitemKindSchema.setStartHour("0");
                prpTitemKindSchema.setEndDate(transSchema.getEndDate());
                prpTitemKindSchema.setEndHour("24");
                
				if (prpdration.getArr(j).getAmountFlag() != null
						&& prpdration.getArr(j).getAmountFlag().toString().trim()
								.length() > 0) {
					if ("0".equals(prpdration.getArr(j).getAmountFlag())) {
						prpTitemKindSchema.setCalculateFlag("N");
					} else {
						prpTitemKindSchema.setCalculateFlag("Y");
					}
				} else {
				
					if (CalculateFlagString.indexOf(prpTitemKindSchema
							.getKindCode()) > -1) {
						prpTitemKindSchema.setCalculateFlag("Y");
					} else {
						prpTitemKindSchema.setCalculateFlag("N");
					}
				}
                prpTitemKindSchema.setCurrency("CNY");
                if(prpTitemKindSchema.getKindCode().equals("001")){
                   prpTitemKindSchema.setShortRateFlag("1");
                   prpTitemKindSchema.setQuantity("");
                   prpTitemKindSchema.setValue("");
                   prpTitemKindSchema.setDiscount("");
                   prpTitemKindSchema.setShortRateFlag("3");                
                }else{
                    prpTitemKindSchema.setUnitAmount(prpdration.getArr(j).getAmount());
                    
                    prpTitemKindSchema.setQuantity("1.00");
                    prpTitemKindSchema.setValue("1.00");
                    prpTitemKindSchema.setDiscount("100.0000");
                    prpTitemKindSchema.setShortRateFlag("3");
                }
                prpTitemKindSchema.setAmount(prpdration.getArr(j).getAmount());
                prpTitemKindSchema.setRate(""+(1000.0*Double.parseDouble(prpdration.getArr(j).getRate())));
                prpTitemKindSchema.setShortRate("100");
                prpTitemKindSchema.setPremium(prpdration.getArr(j).getPremium());
                if(prpTitemKindSchema.getCalculateFlag().equals("Y")){
                    sumAmount=sumAmount+Double.parseDouble(prpdration.getArr(j).getAmount());
                }
                sumPermium=sumPermium+Double.parseDouble(prpdration.getArr(j).getPremium());
                if(prpTitemKindSchema.getKindCode().equals("001")){
                    prpTitemKindSchema.setFlag(" 3");
                }else if(prpTitemKindSchema.getKindCode().equals("182")||prpTitemKindSchema.getKindCode().equals("165")){
                     prpTitemKindSchema.setFlag(" 2");
                }else{
                     prpTitemKindSchema.setFlag(" 1");
                }
                blPrpTitemKind.setArr(prpTitemKindSchema);
            }
                
        }
        if("26".equals(transSchema.getPolicyType())){
            rationType=transSchema.getPolicyType()+transSchema.getGrade()+"10";
            prpdration=getRateGroup(rationType,"10",transSchema.getRiskFlag());
            for(int j=0;j<prpdration.getSize();j++){
                if(!prpdration.getArr(j).getKindCode().equals("001")){
                    PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
                    prpTitemKindSchema.setProposalNo(transSchema.getMainProposalNo());
                    prpTitemKindSchema.setRiskCode("3009");
                    prpTitemKindSchema.setItemKindNo((blPrpTitemKind.getSize() + 1) + "");
                    prpTitemKindSchema.setFamilyNo("3");
                    prpTitemKindSchema.setFamilyName(wifeName);
                    prpTitemKindSchema.setKindCode(prpdration.getArr(j).getKindCode());
                    dbPrpDkind.getInfo("3009",prpdration.getArr(j).getKindCode());
                    String strKindName = dbPrpDkind.getKindCName();
                    blPrpDitem = new BLPrpDitem();
                    String strItemDetailName = blPrpDitem.translateCode("3009",prpdration.getArr(j).getItemCode(),true);
                    prpTitemKindSchema.setKindName(strKindName);
                    prpTitemKindSchema.setItemCode(prpdration.getArr(j).getItemCode());
                    prpTitemKindSchema.setItemDetailName(strItemDetailName);
                    prpTitemKindSchema.setStartDate(transSchema.getStartDate());
                    prpTitemKindSchema.setStartHour("0");
                    prpTitemKindSchema.setEndDate(transSchema.getEndDate());
                    prpTitemKindSchema.setEndHour("24");

                    
    				if (prpdration.getArr(j).getAmountFlag() != null
    						&& prpdration.getArr(j).getAmountFlag().toString().trim()
    								.length() > 0) {
    					if ("0".equals(prpdration.getArr(j).getAmountFlag())) {
    						prpTitemKindSchema.setCalculateFlag("N");
    					} else {
    						prpTitemKindSchema.setCalculateFlag("Y");
    					}
    				} else {
    				
    					if (CalculateFlagString.indexOf(prpTitemKindSchema
    							.getKindCode()) > -1) {
    						prpTitemKindSchema.setCalculateFlag("Y");
    					} else {
    						prpTitemKindSchema.setCalculateFlag("N");
    					}
    				}
                    prpTitemKindSchema.setCurrency("CNY");
                    if(prpTitemKindSchema.getKindCode().equals("001")){
                       prpTitemKindSchema.setShortRateFlag("1");
                    }else{
                        prpTitemKindSchema.setUnitAmount(prpdration.getArr(j).getAmount());
                        prpTitemKindSchema.setQuantity("1.00");
                        prpTitemKindSchema.setValue("1.00");
                        prpTitemKindSchema.setDiscount("100.0000");
                        prpTitemKindSchema.setShortRateFlag("3");
                    }
                    prpTitemKindSchema.setAmount(prpdration.getArr(j).getAmount());
                    prpTitemKindSchema.setRate(""+(1000.0*Double.parseDouble(prpdration.getArr(j).getRate())));
                    prpTitemKindSchema.setShortRate("100");
                    prpTitemKindSchema.setPremium(prpdration.getArr(j).getPremium());
                    if(prpTitemKindSchema.getCalculateFlag().equals("Y")){
                        sumAmount=sumAmount+Double.parseDouble(prpdration.getArr(j).getAmount());
                    }
                    sumPermium=sumPermium+Double.parseDouble(prpdration.getArr(j).getPremium());
                    if(prpTitemKindSchema.getKindCode().equals("001")){
                        prpTitemKindSchema.setFlag(" 3");
                    }else if(prpTitemKindSchema.getKindCode().equals("182")||prpTitemKindSchema.getKindCode().equals("165")){
                         prpTitemKindSchema.setFlag(" 2");
                    }else{
                         prpTitemKindSchema.setFlag(" 1");
                    }
                    blPrpTitemKind.setArr(prpTitemKindSchema);
               }    
            }
        }else if("27".equals(transSchema.getPolicyType())){
            rationType=transSchema.getPolicyType()+transSchema.getGrade()+"10";
            prpdration=getRateGroup(rationType,"10",transSchema.getRiskFlag());
            for(int j=0;j<prpdration.getSize();j++){
                if(!prpdration.getArr(j).getKindCode().equals("001")){
                    PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
                    prpTitemKindSchema.setProposalNo(transSchema.getMainProposalNo());
                    prpTitemKindSchema.setRiskCode("3009");
                    prpTitemKindSchema.setItemKindNo((blPrpTitemKind.getSize() + 1) + "");
                    prpTitemKindSchema.setFamilyNo(wifeFamilyNo);
                    prpTitemKindSchema.setFamilyName(wifeName);
                    prpTitemKindSchema.setKindCode(prpdration.getArr(j).getKindCode());
                    dbPrpDkind.getInfo("3009",prpdration.getArr(j).getKindCode());
                    String strKindName = dbPrpDkind.getKindCName();
                    blPrpDitem = new BLPrpDitem();
                    String strItemDetailName = blPrpDitem.translateCode("3009",prpdration.getArr(j).getItemCode(),true);
                    prpTitemKindSchema.setKindName(strKindName);
                    prpTitemKindSchema.setItemCode(prpdration.getArr(j).getItemCode());
                    prpTitemKindSchema.setItemDetailName(strItemDetailName);
                    prpTitemKindSchema.setStartDate(transSchema.getStartDate());
                    prpTitemKindSchema.setStartHour("0");
                    prpTitemKindSchema.setEndDate(transSchema.getEndDate());
                    prpTitemKindSchema.setEndHour("24");
                    
    				if (prpdration.getArr(j).getAmountFlag() != null
    						&& prpdration.getArr(j).getAmountFlag().toString().trim()
    								.length() > 0) {
    					if ("0".equals(prpdration.getArr(j).getAmountFlag())) {
    						prpTitemKindSchema.setCalculateFlag("N");
    					} else {
    						prpTitemKindSchema.setCalculateFlag("Y");
    					}
    				} else {
    				
    					if (CalculateFlagString.indexOf(prpTitemKindSchema
    							.getKindCode()) > -1) {
    						prpTitemKindSchema.setCalculateFlag("Y");
    					} else {
    						prpTitemKindSchema.setCalculateFlag("N");
    					}
    				}
                    prpTitemKindSchema.setCurrency("CNY");
                    if(prpTitemKindSchema.getKindCode().equals("001")){
                       prpTitemKindSchema.setShortRateFlag("1");
                    }else{
                        prpTitemKindSchema.setUnitAmount(prpdration.getArr(j).getAmount());
                        prpTitemKindSchema.setQuantity("1.00");
                        prpTitemKindSchema.setValue("1.00");
                        prpTitemKindSchema.setDiscount("100.0000");
                        prpTitemKindSchema.setShortRateFlag("3");
                    }
                    prpTitemKindSchema.setAmount(prpdration.getArr(j).getAmount());
                    prpTitemKindSchema.setRate(""+(1000.0*Double.parseDouble(prpdration.getArr(j).getRate())));
                    prpTitemKindSchema.setShortRate("100");
                    prpTitemKindSchema.setPremium(prpdration.getArr(j).getPremium());
                    if(prpTitemKindSchema.getCalculateFlag().equals("Y")){
                        sumAmount=sumAmount+Double.parseDouble(prpdration.getArr(j).getAmount());
                    }
                    sumPermium=sumPermium+Double.parseDouble(prpdration.getArr(j).getPremium());
                    if(prpTitemKindSchema.getKindCode().equals("001")){
                        prpTitemKindSchema.setFlag(" 3");
                    }else if(prpTitemKindSchema.getKindCode().equals("182")||prpTitemKindSchema.getKindCode().equals("165")){
                         prpTitemKindSchema.setFlag(" 2");
                    }else{
                         prpTitemKindSchema.setFlag(" 1");
                    }
                    blPrpTitemKind.setArr(prpTitemKindSchema);
               }   
            }
            rationType=transSchema.getPolicyType()+transSchema.getGrade()+"40";
            prpdration=getRateGroup(rationType,"40",transSchema.getRiskFlag());
            for(int j=0;j<prpdration.getSize();j++){
                if(!prpdration.getArr(j).getKindCode().equals("001")){
                    PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
                    prpTitemKindSchema.setProposalNo(transSchema.getMainProposalNo());
                    prpTitemKindSchema.setRiskCode("3009");
                    prpTitemKindSchema.setItemKindNo((blPrpTitemKind.getSize() + 1) + "");
                    prpTitemKindSchema.setFamilyNo(childFamilyNo);
                    prpTitemKindSchema.setFamilyName(childName);
                    prpTitemKindSchema.setKindCode(prpdration.getArr(j).getKindCode());
                    dbPrpDkind.getInfo("3009",prpdration.getArr(j).getKindCode());
                    String strKindName = dbPrpDkind.getKindCName();
                    blPrpDitem = new BLPrpDitem();
                    String strItemDetailName = blPrpDitem.translateCode("3009",prpdration.getArr(j).getItemCode(),true);
                    prpTitemKindSchema.setKindName(strKindName);
                    prpTitemKindSchema.setItemCode(prpdration.getArr(j).getItemCode());
                    prpTitemKindSchema.setItemDetailName(strItemDetailName);
                    prpTitemKindSchema.setStartDate(transSchema.getStartDate());
                    prpTitemKindSchema.setStartHour("0");
                    prpTitemKindSchema.setEndDate(transSchema.getEndDate());
                    prpTitemKindSchema.setEndHour("24");
                    
    				if (prpdration.getArr(j).getAmountFlag() != null
    						&& prpdration.getArr(j).getAmountFlag().toString().trim()
    								.length() > 0) {
    					if ("0".equals(prpdration.getArr(j).getAmountFlag())) {
    						prpTitemKindSchema.setCalculateFlag("N");
    					} else {
    						prpTitemKindSchema.setCalculateFlag("Y");
    					}
    				} else {
    				
    					if (CalculateFlagString.indexOf(prpTitemKindSchema
    							.getKindCode()) > -1) {
    						prpTitemKindSchema.setCalculateFlag("Y");
    					} else {
    						prpTitemKindSchema.setCalculateFlag("N");
    					}
    				}
                    prpTitemKindSchema.setCurrency("CNY");
                    if(prpTitemKindSchema.getKindCode().equals("001")){
                       prpTitemKindSchema.setShortRateFlag("1");
                    }else{
                    	
                    	
                        
                    	unitAmount = Str.round(Double.parseDouble(prpdration.getArr(j).getAmount())/Integer.parseInt(transSchema.getChildrenCount()),2);
                        prpTitemKindSchema.setUnitAmount(String.valueOf(unitAmount));
                        
                        prpTitemKindSchema.setQuantity(transSchema.getChildrenCount());
                        prpTitemKindSchema.setValue("1.00");
                        prpTitemKindSchema.setDiscount("100.0000");
                        prpTitemKindSchema.setShortRateFlag("3");
                    } 
                    prpTitemKindSchema.setAmount(prpdration.getArr(j).getAmount());
                    prpTitemKindSchema.setRate(""+(1000.0*Double.parseDouble(prpdration.getArr(j).getRate())));
                    prpTitemKindSchema.setShortRate("100");
                    prpTitemKindSchema.setPremium(prpdration.getArr(j).getPremium());
                    if(prpTitemKindSchema.getCalculateFlag().equals("Y")){
                        sumAmount=sumAmount+Double.parseDouble(prpdration.getArr(j).getAmount());
                    }
                    sumPermium=sumPermium+Double.parseDouble(prpdration.getArr(j).getPremium());
                    if(prpTitemKindSchema.getKindCode().equals("001")){
                        prpTitemKindSchema.setFlag(" 3");
                    }else if(prpTitemKindSchema.getKindCode().equals("182")||prpTitemKindSchema.getKindCode().equals("165")){
                         prpTitemKindSchema.setFlag(" 2");
                    }else{
                         prpTitemKindSchema.setFlag(" 1");
                    }
                    blPrpTitemKind.setArr(prpTitemKindSchema);
               }    
            } 
            
        }else if("28".equals(transSchema.getPolicyType())){
            rationType=transSchema.getPolicyType()+transSchema.getGrade()+"40";
            
            prpdration=getRateGroup(rationType,"40",transSchema.getRiskFlag());
            for(int j=0;j<prpdration.getSize();j++){
                if(!prpdration.getArr(j).getKindCode().equals("001")){
                    PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
                    prpTitemKindSchema.setProposalNo(transSchema.getMainProposalNo());
                    prpTitemKindSchema.setRiskCode("3009");
                    prpTitemKindSchema.setItemKindNo((blPrpTitemKind.getSize() + 1) + "");
                    prpTitemKindSchema.setFamilyNo("3");
                    
                    
                    prpTitemKindSchema.setFamilyName(childName);
                    
                    prpTitemKindSchema.setKindCode(prpdration.getArr(j).getKindCode());
                    dbPrpDkind.getInfo("3009",prpdration.getArr(j).getKindCode());
                    String strKindName = dbPrpDkind.getKindCName();
                    blPrpDitem = new BLPrpDitem();
                    String strItemDetailName = blPrpDitem.translateCode("3009",prpdration.getArr(j).getItemCode(),true);
                    prpTitemKindSchema.setKindName(strKindName);
                    prpTitemKindSchema.setItemCode(prpdration.getArr(j).getItemCode());
                    prpTitemKindSchema.setItemDetailName(strItemDetailName);
                    prpTitemKindSchema.setStartDate(transSchema.getStartDate());
                    prpTitemKindSchema.setStartHour("0");
                    prpTitemKindSchema.setEndDate(transSchema.getEndDate());
                    prpTitemKindSchema.setEndHour("24");

                    
    				if (prpdration.getArr(j).getAmountFlag() != null
    						&& prpdration.getArr(j).getAmountFlag().toString().trim()
    								.length() > 0) {
    					if ("0".equals(prpdration.getArr(j).getAmountFlag())) {
    						prpTitemKindSchema.setCalculateFlag("N");
    					} else {
    						prpTitemKindSchema.setCalculateFlag("Y");
    					}
    				} else {
    				
    					if (CalculateFlagString.indexOf(prpTitemKindSchema
    							.getKindCode()) > -1) {
    						prpTitemKindSchema.setCalculateFlag("Y");
    					} else {
    						prpTitemKindSchema.setCalculateFlag("N");
    					}
    				}
                    prpTitemKindSchema.setCurrency("CNY");
                    if(prpTitemKindSchema.getKindCode().equals("001")){
                       prpTitemKindSchema.setShortRateFlag("1");
                    }else{
                    	
                    	
                        
                        
                    	
                    	unitAmount = Str.round(Double.parseDouble(prpdration.getArr(j).getAmount())/Integer.parseInt(transSchema.getChildrenCount()),2);
                        prpTitemKindSchema.setUnitAmount(String.valueOf(unitAmount));
                        
                        prpTitemKindSchema.setQuantity(transSchema.getChildrenCount());
                        
                        prpTitemKindSchema.setValue("1.00");
                        prpTitemKindSchema.setDiscount("100.0000");
                        prpTitemKindSchema.setShortRateFlag("3");
                    }
                    prpTitemKindSchema.setAmount(prpdration.getArr(j).getAmount());
                    prpTitemKindSchema.setRate(""+(1000.0*Double.parseDouble(prpdration.getArr(j).getRate())));
                    prpTitemKindSchema.setShortRate("100");
                    prpTitemKindSchema.setPremium(prpdration.getArr(j).getPremium());
                    if(prpTitemKindSchema.getCalculateFlag().equals("Y")){
                        sumAmount=sumAmount+Double.parseDouble(prpdration.getArr(j).getAmount());
                    }
                    sumPermium=sumPermium+Double.parseDouble(prpdration.getArr(j).getPremium());
                    if(prpTitemKindSchema.getKindCode().equals("001")){
                        prpTitemKindSchema.setFlag(" 3");
                    }else if(prpTitemKindSchema.getKindCode().equals("182")||prpTitemKindSchema.getKindCode().equals("165")){
                         prpTitemKindSchema.setFlag(" 2");
                    }else{
                         prpTitemKindSchema.setFlag(" 1");
                    }
                    blPrpTitemKind.setArr(prpTitemKindSchema);
               }    
               
            }
        }
        
        permiumAmountSchema.setSumAmount(""+format(sumAmount));
        permiumAmountSchema.setSumPremium(""+format(sumPermium));
        
        blMainProposal.setBLPrpTitemKind(blPrpTitemKind);
    }
    
    /**
     * @param blMainProposal大XX单对象
     * @param childrenNames子女名称
     * @param childrenCount子女数
     * @author 刘佳 
     */
    public void transTengageMain(BLProposal blMainProposal,
                                  String childrenNames,
                                  int childrenCount) throws Exception{
        if(childrenCount>1){
            PrpTengageSchema prpTengageSchema = new PrpTengageSchema();
            prpTengageSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema.setRiskCode("3004");
            prpTengageSchema.setSerialNo("1");
            prpTengageSchema.setLineNo("1");
            prpTengageSchema.setClauseCode("T9996");
            prpTengageSchema.setClauses("其他");
            prpTengageSchema.setTitleFlag("0");
            prpTengageSchema.setFlag(" 0");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema);
            
            PrpTengageSchema prpTengageSchema1 = new PrpTengageSchema();
            prpTengageSchema1.setProposalNo(blMainProposal.getBLPrpTengage().getArr(0).getProposalNo());
            prpTengageSchema1.setRiskCode("3004");
            prpTengageSchema1.setSerialNo("1");
            prpTengageSchema1.setLineNo("2");
            prpTengageSchema1.setClauseCode("T9996");
            prpTengageSchema1.setClauses("本XX项下XX的被XX人子女为"+childrenNames+"。");
            prpTengageSchema1.setTitleFlag("1");
            prpTengageSchema1.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema1);
        }
    }
    
    /**
     * @param blMainProposal大XX单对象
     * @param blSubOneProposal2700小XX单对象
     * @param blSubTowProposal0302小XX单对象
     * @author 刘佳 
     */
    public void transTengageSub(BLProposal blMainProposal,
                                 BLProposal blSubOneProposal,
                                 BLProposal blSubTowProposal) throws Exception{
        for(int i=0;i<blMainProposal.getBLPrpTengage().getSize();i++){
            PrpTengageSchema PrpTengageSchemaOne = new PrpTengageSchema();
            PrpTengageSchema PrpTengageSchemaTow = new PrpTengageSchema();
            PrpTengageSchemaOne.setRiskCode("2700");
            PrpTengageSchemaOne.setProposalNo(blSubOneProposal.getBLPrpTmain().getArr(0).getProposalNo());
            PrpTengageSchemaOne.setFlag(blMainProposal.getBLPrpTengage().getArr(i).getFlag());
            PrpTengageSchemaOne.setLineNo(blMainProposal.getBLPrpTengage().getArr(i).getLineNo());
            PrpTengageSchemaOne.setClauses(blMainProposal.getBLPrpTengage().getArr(i).getClauses());
            PrpTengageSchemaOne.setClauseCode(blMainProposal.getBLPrpTengage().getArr(i).getClauseCode());
            PrpTengageSchemaOne.setSerialNo(blMainProposal.getBLPrpTengage().getArr(i).getSerialNo());
            PrpTengageSchemaOne.setTitleFlag(blMainProposal.getBLPrpTengage().getArr(i).getTitleFlag());
            blSubOneProposal.getBLPrpTengage().setArr(PrpTengageSchemaOne);
            
            PrpTengageSchemaTow.setRiskCode("0302");
            PrpTengageSchemaTow.setProposalNo(blSubTowProposal.getBLPrpTmain().getArr(0).getProposalNo());
            PrpTengageSchemaTow.setFlag(blMainProposal.getBLPrpTengage().getArr(i).getFlag());
            PrpTengageSchemaTow.setLineNo(blMainProposal.getBLPrpTengage().getArr(i).getLineNo());
            PrpTengageSchemaTow.setClauses(blMainProposal.getBLPrpTengage().getArr(i).getClauses());
            PrpTengageSchemaTow.setClauseCode(blMainProposal.getBLPrpTengage().getArr(i).getClauseCode());
            PrpTengageSchemaTow.setSerialNo(blMainProposal.getBLPrpTengage().getArr(i).getSerialNo());
            PrpTengageSchemaTow.setTitleFlag(blMainProposal.getBLPrpTengage().getArr(i).getTitleFlag());
            blSubTowProposal.getBLPrpTengage().setArr(PrpTengageSchemaTow);
        }
    }
    
    /**
     * @param blPrpBillCoreCard快钱业务类
     * @param billCoreCardList快钱结果集数组
     * @bizType 业务类型(TXX单)
     * @bizNo   业务号
     * @author 刘佳 
     */
    public void transBillCoreCard(BLPrpBillCoreCard blPrpBillCoreCard,
                                   ArrayList billCoreCardList,
                                   String bizType,
                                   String bizNo) throws Exception{
        if(billCoreCardList.size()>0){
            for(int i=0;i<billCoreCardList.size();i++){
                ArrayList item = (ArrayList)billCoreCardList.get(i);
                PrpBillCoreCardSchema prpBillCoreCardSchema = new PrpBillCoreCardSchema();
                prpBillCoreCardSchema.setBusinessNo(bizNo);
                prpBillCoreCardSchema.setCertiType(bizType);
                prpBillCoreCardSchema.setContNo((String)item.get(0));
                prpBillCoreCardSchema.setTxnType((String)item.get(1));
                prpBillCoreCardSchema.setOrderId((String)item.get(2));
                prpBillCoreCardSchema.setCardNo((String)item.get(3));
                prpBillCoreCardSchema.setCardCvv((String)item.get(4));
                prpBillCoreCardSchema.setCardExpiredDate((String)item.get(5));
                prpBillCoreCardSchema.setAmount((String)item.get(6));
                prpBillCoreCardSchema.setMerchantId((String)item.get(7));
                prpBillCoreCardSchema.setTerminalId((String)item.get(8));
                prpBillCoreCardSchema.setStorableCardNo((String)item.get(9));
                prpBillCoreCardSchema.setRefNumber((String)item.get(10));
                prpBillCoreCardSchema.setCreateTime((String)item.get(11));
                prpBillCoreCardSchema.setVersion((String)item.get(12));
                prpBillCoreCardSchema.setOrignalTxnType((String)item.get(13));
                prpBillCoreCardSchema.setAuthorizationCode((String)item.get(14));
                prpBillCoreCardSchema.setEntryTime((String)item.get(15));
                prpBillCoreCardSchema.setTransTime((String)item.get(16));
            	
            	prpBillCoreCardSchema.setSigNature((String)item.get(17));
            	prpBillCoreCardSchema.setIsSuer((String)item.get(18));
            	
                blPrpBillCoreCard.setArr(prpBillCoreCardSchema);
            }
        }
    }
    
    /**
    * @Author     刘佳
    * @description 根据费率类型，返回被XXXXX人一组费率
    * @param        费率类型
    * @param        与主被人关系
    * @return       一个被XXXXX人的一组费率
     * @throws Exception 
     * @throws UserException 
    */
    public BLPrpDration getRateGroup(String rationType,
                                      String relationToInsured,
                                      String riskFlag) throws UserException, Exception{
        BLPrpDration blPrpDration = new BLPrpDration();
        if("01".equals(relationToInsured)&&"1".equals(riskFlag)){
            blPrpDration.query(" riskcode='3009' and rationtype='"+rationType+"'");
            return blPrpDration;
        }else{
            
            blPrpDration.query(" riskcode='3009' and rationtype='"+rationType+"' and kindcode!='001'");
            return blPrpDration;
        }
    }
}
