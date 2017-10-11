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
import com.sp.utility.string.Str;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class TransProposal {
	
	private final Log logger = LogFactory.getLog(getClass());
	
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
    public String getTenageType(ArrayList tmlInsuredList ,String gread){
        if(tmlInsuredList.size()<1){
            return "7";
        }else{
            
            
            if(gread.equals("B")){
                ArrayList tempCardList =(ArrayList)tmlInsuredList.get(0);
                String idno=(String)tempCardList.get(2);
                if(idno!=null&&!idno.equals("")&&!idno.equals("null")){
                    return "6";
                }else{
                    return "3";
                }
            }else{
                String idFlag1="";
                String idFlag2="";
                if(tmlInsuredList.size()==1){
                    ArrayList tempCardList =(ArrayList)tmlInsuredList.get(0);
                    String idno=(String)tempCardList.get(2);
                    if (idno != null&&!idno.equals("") && !idno.equals("null")
                             ) {
                        return "5";
                    }else{
                        return "2";
                    }
                }else{
                    for (int i = 0; i < tmlInsuredList.size(); i++) {
                        ArrayList tempCardList = (ArrayList) tmlInsuredList
                                .get(i);
                        String idno = (String) tempCardList.get(2);
                        if (i == 0) {
                            if (idno != null&&!idno.equals("") && !idno.equals("null")
                                     ) {
                                idFlag1 = "1";
                            }
                        } else {
                            if (idno != null&&!idno.equals("") && !idno.equals("null")
                                    ) {
                                idFlag2 = "1";
                            }
                        }
                    }
                    if (idFlag1.equals("1") && idFlag2.equals("1")) {
                        return "4";
                    }else if(idFlag1.equals("1")&&idFlag2.equals("")){
                        return "8";
                    }else if(idFlag1.equals("")&&idFlag2.equals("1")){
                        return "9";
                    }else if(idFlag1.equals("")&&idFlag2.equals("")){
                        return "1";
                    }else {
                        return "";
                    }
                }
            }
        }
        
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
    public String getPayMode(String payMode){
        if(payMode.equals("1"))
            return "3";
        else if(payMode.equals("6"))
            return "2";
        
        else if(payMode.equals("8"))
        	return "4";
        
        else
            return "1";
    }
    public String getPayMode1(String payMode){
        if(payMode.equals("1"))
            return "1";
        else if(payMode.equals("6"))
            return "6";
        
        else if(payMode.equals("8"))
        	return "8";
        
        else
            return "2";
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
    
    
    public void trans()throws Exception{
        ArrayList  TMLCContList     = new ArrayList();   
        DbPool          dbpool      = new DbPool();
        DbPool          tmldbpool      = new DbPool();
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
        
        
        TMLCContList=blGetData.getTMLCContList(tmldbpool,"3004");
        
        String policyNo="";
        
        ArrayList mainItemList = new ArrayList();
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
				ArrayList  TMCardInfoList       = new ArrayList();   
				
				ArrayList BillCoreCardList  = new ArrayList();
				
				String contNo = "";
				String PayTimes="";
				String PolicyType="";
				
				String comCode="";
				String operCode="";
				String grade="";
				String startDate="";
				String endDate="";
				String newpaymode="";
				String extpaymode="";
				String myheadPayMode="";
				String MainPolicyNo=""; 
				
				try{
				
				mainItemList=(ArrayList)TMLCContList.get(i);
				
				contNo=(String)mainItemList.get(0);
				policyNo=contNo;
				comCode=(String)mainItemList.get(7);
				operCode=(String)mainItemList.get(5);
				PayTimes=(String)mainItemList.get(9);
				newpaymode=(String)mainItemList.get(14);
				extpaymode=(String)mainItemList.get(15);
				myheadPayMode=getPayMode1(newpaymode);
				newpaymode=getPayMode(newpaymode);
				if(extpaymode!=null&&!extpaymode.equals(""))
				extpaymode=getPayMode(extpaymode);
				else
				    extpaymode="";
				
				PermiumAmountSchema permiumAmountSchema = new PermiumAmountSchema();
				
				
				
				
				
				
				String SubOnePolicyNo=new String();
				String SubTowPolicyNo=new String();
				MainPolicyNo=getProposalno(dbpool,"3004",comCode);
				SubOnePolicyNo=getProposalno(dbpool,"2700",comCode);
				SubTowPolicyNo=getProposalno(dbpool,"0301",comCode);
				
				PayTimes=getPayType(PayTimes); 
				startDate=(String)mainItemList.get(4);
				
				java.util.Date enddatetemp1=PubTools.stringToUtilDate((String)mainItemList.get(4));
				java.util.Calendar   calendar   =   java.util.Calendar.getInstance();   
				
				calendar.setTime(enddatetemp1);   
				calendar.add(Calendar.YEAR   ,   1);
				calendar.add(Calendar.DATE,-1);
				int   year   =   calendar.get(Calendar.YEAR);   
				int   month   =   calendar.get(Calendar.MONTH)+1;   
				int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
				endDate=year+"-"+month+"-"+day;
				
				
				
				
				
				
				
				
				
				BillCoreCardList = blGetData.getBillCoreCardList(tmldbpool, contNo);
				if(BillCoreCardList.size()<1){
					
				}else{
					transBillCoreCard(blPrpBillCoreCard,BillCoreCardList,"T",MainPolicyNo);
				}
				
				TMLCPolList =blGetData.getTMLCPolList(tmldbpool,contNo);
				if(TMLCPolList.size()<1){
				    dbGetData.exceUpdate("update TMLCCont set posted='3' where contno='"+contNo+"'",tmldbpool);
				    continue;
				}else{
				    ArrayList polItem=(ArrayList)TMLCPolList.get(0);
				    grade=(String)polItem.get(0);
				    if("TM-A".equals(grade)){
				        grade="A";
				    }else if("TM-B".equals(grade)){
				        grade="B";
				    }else{
				        
				    }
				}
				TMLCAppntList=blGetData.getTMLCAppntList(tmldbpool,contNo);
				if(TMLCAppntList.size()<1){
				    dbGetData.exceUpdate("update TMLCCont set posted='1' where contno='"+contNo+"'",tmldbpool);
				    continue;
				}else{
				    transInsured(blMainProposal, blSubOneProposal, blSubTowProposal,TMLCAppntList,"1",1,comCode,operCode, MainPolicyNo,SubOnePolicyNo,SubTowPolicyNo,mainItemList);
				}
				TMLCInsuredList=blGetData.getTMLCInsuredList(tmldbpool,contNo);
				if(TMLCInsuredList.size()<1){
				    dbGetData.exceUpdate("update TMLCCont set posted='2' where contno='"+contNo+"'",tmldbpool);
				    continue;
				}else{
				    transInsured( blMainProposal, blSubOneProposal, blSubTowProposal,TMLCInsuredList,"2",TMLCAppntList.size(),comCode,operCode, MainPolicyNo,SubOnePolicyNo,SubTowPolicyNo,mainItemList );
				}
				
				TMLCBnfList=blGetData.getTMLCBnfList(tmldbpool,contNo);
				if(TMLCBnfList.size()>0){
				    transInsured(blMainProposal, blSubOneProposal, blSubTowProposal,TMLCBnfList,"3",TMLCAppntList.size()+TMLCInsuredList.size(),comCode,operCode, MainPolicyNo,SubOnePolicyNo,SubTowPolicyNo,mainItemList);
				}
				TMCardInfoList=blGetData.getTMCardInfoList(tmldbpool,contNo);
				if(TMCardInfoList.size()>0){
				    transInsured(blMainProposal, blSubOneProposal, blSubTowProposal,TMCardInfoList,"4",TMLCAppntList.size()+TMLCInsuredList.size(),comCode,operCode, MainPolicyNo,SubOnePolicyNo,SubTowPolicyNo,mainItemList);
				}
				PolicyType=getPolicyType(blMainProposal);
                
				int boys=0;
				int parents=0;
				for(int t=0;t<TMLCInsuredList.size();t++){
				    ArrayList insuredList = new ArrayList();
				    insuredList=(ArrayList)TMLCInsuredList.get(t);
				    
				    
				    String insurFlag=getInsuredIdentityNew((String)insuredList.get(16));
				    
				    if(insurFlag.equals("40")){
				        boys=boys+1;
				    }
				}
				
				transTitemKind(blMainProposal,blMainProposal.getBLPrpTinsured(), PolicyType, grade, PayTimes, startDate, endDate, permiumAmountSchema,  MainPolicyNo,boys,TMCardInfoList.size(),TMCardInfoList);
				transTitemKindSub( blMainProposal, blSubOneProposal, blSubTowProposal,  permiumAmountSchema, SubOnePolicyNo, SubTowPolicyNo);
				
				
				
				transTmain(blMainProposal,blSubOneProposal,blSubTowProposal,mainItemList,permiumAmountSchema,comCode,operCode,MainPolicyNo,SubOnePolicyNo,SubTowPolicyNo,  PolicyType,(TMLCInsuredList.size()+TMCardInfoList.size())+"",myheadPayMode);
				
				
				   
				    
				
				transTexpense(blMainProposal,blSubOneProposal,blSubTowProposal);
				transTfee(blMainProposal,blSubOneProposal,blSubTowProposal);
				transTengageMain(blMainProposal,TMCardInfoList,grade,PayTimes);
				transTengageSub(blMainProposal,blSubOneProposal,blSubTowProposal);
				
				
				transTmainSub(blSubOneProposal,blSubTowProposal,MainPolicyNo);
				transTplan(blMainProposal,blSubOneProposal,blSubTowProposal,PayTimes, startDate,newpaymode,extpaymode);
				transTmainCasualty(blMainProposal,blSubOneProposal, grade);
				TMLCIinsuredPCList=blGetData.getTMLCAddressList(tmldbpool,contNo);
				
				}catch(Exception epp){
					
					logger.info("单号:"+contNo+""+ExceptionUtils.getExceptionStackTraceString(epp));
					
					epp.printStackTrace();
				    dbGetData.exceUpdate("update TMLCCont set posted='E' where contno='"+contNo+"'",tmldbpool);
				    continue;
				}
				if(TMLCIinsuredPCList.size()<0){
					
					logger.info(contNo+": 被XXXXX人个数有误！");
					
					dbGetData.exceUpdate("update TMLCCont set posted='9' where contno='"+contNo+"'",tmldbpool);
				    continue;
				}else{
				    ArrayList tempAddress=(ArrayList)TMLCIinsuredPCList.get(0);
				    String postCode=(String)tempAddress.get(2);
				    String addressName=(String)tempAddress.get(1);
				    transTaddress(blMainProposal,blSubTowProposal,postCode,addressName);
				}
				dbGetData.exceUpdate("update TMLCCont set posted='Y' where contno='"+contNo+"'",tmldbpool);
				dbpool.beginTransaction();
				String isFlag="";
				try{
				    
					
				    
				    
				    
				    if(blPrpBillCoreCard.getSize()>0){
				        double amount  = Double.parseDouble(blPrpBillCoreCard.getArr(0).getAmount());
				        if(blMainProposal.getBLPrpTplan().getSize()==12){  
				        	double planfee1 = Double.parseDouble(blMainProposal.getBLPrpTplan().getArr(0).getPlanFee());
				        	double planfee2 = Double.parseDouble(blMainProposal.getBLPrpTplan().getArr(1).getPlanFee());
				        	double planfee = planfee1 + planfee2;
				        	if(planfee!=amount){
				        		
				        		logger.info(contNo+": 快钱XX不一致即消费金额同缴费计划不一致(期缴)！");
				        		
				        		
				        		dbGetData.exceUpdate("update TMLCCont set posted='8' where contno='"+contNo+"'",tmldbpool);
				        		continue;
				        		
				        		
				        	}
				        }else{
				        	double planfee = Double.parseDouble(blMainProposal.getBLPrpTplan().getArr(0).getPlanFee());
				            if(planfee!=amount){
				            	
				            	logger.info(contNo+": 快钱XX不一致即消费金额同缴费计划不一致(年缴)！");
				            	
				            	
				            	dbGetData.exceUpdate("update TMLCCont set posted='8' where contno='"+contNo+"'",tmldbpool);
				            	continue;
				            	
				            	
				            }
				        }
				        blPrpBillCoreCard.save(dbpool);
				    }
				    
				    blMainProposal.save(dbpool, "I", false);
				    blSubOneProposal.save(dbpool, "I", false);
				    blSubTowProposal.save(dbpool, "I", false);
				    dbpool.commitTransaction();
				}catch(Exception e2){
					
					logger.info("单号: "+contNo+ExceptionUtils.getExceptionStackTraceString(e2));
					
					e2.printStackTrace();
				    isFlag="1";
				    dbpool.rollbackTransaction();
				    dbGetData.exceUpdate("update TMLCCont set posted='E' where contno='"+contNo+"'",tmldbpool);
				}
				com.sp.undwrt.bl.facade.BLTaskFacade blTaskFacade = new com.sp.undwrt.bl.facade.BLTaskFacade();
				if(isFlag.equals("")){
				    try{
				        blTaskFacade.start("11","T",MainPolicyNo,"3004",
				            "30",comCode,comCode,
				            operCode,operCode,operCode,""); 
				    }catch(Exception ee){
				    	
				    	logger.info("单号: "+contNo+ExceptionUtils.getExceptionStackTraceString(ee));
				    	
				    	dbpool.rollbackTransaction();
				        dbGetData.exceUpdate("update TMLCCont set posted='U' where contno='"+contNo+"'",tmldbpool);
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
    
    
    public String getPolicyType(BLProposal blMainProposal) throws Exception{
        
        
        
        
        
        String fuqiFlag="";
        String ernvFlag="";
        String strBenefitFlag="";
        String PolicyType="";
        if(blMainProposal.getBLPrpTinsured().getSize()==2){
            PolicyType="25";
        }else{
            for(int j=0;j<blMainProposal.getBLPrpTinsured().getSize();j++){
            	
            	if(!"1".equals(blMainProposal.getBLPrpTinsured().getArr(j).getInsuredFlag()))
            		continue;
            	
                strBenefitFlag=blMainProposal.getBLPrpTinsured().getArr(j).getBenefitFlag();
                if(blMainProposal.getBLPrpTinsured().getArr(j).getInsuredIdentity().equals("10")&&strBenefitFlag.equals("N")){
                    fuqiFlag="1";
                }else if(blMainProposal.getBLPrpTinsured().getArr(j).getInsuredIdentity().trim().equals("40")&&strBenefitFlag.equals("N")){
                    ernvFlag="1";
                }else if(blMainProposal.getBLPrpTinsured().getArr(j).getInsuredIdentity().equals("50")&&strBenefitFlag.equals("N")){
                    PolicyType="29";
                }
            }
        }
        if(fuqiFlag.equals("1")&&ernvFlag.equals("1")){
            PolicyType="27";
        }
        if(fuqiFlag.equals("1")&&!ernvFlag.equals("1")){
            PolicyType="26";
        }
        if(!fuqiFlag.equals("1")&&ernvFlag.equals("1")){
            PolicyType="28";
        }
        if(!fuqiFlag.equals("1")&&!ernvFlag.equals("1")){
            PolicyType="25";
        }
        return PolicyType;
        
    }
    
    public void transTmain(BLProposal blMainProposal,BLProposal blSubOneProposal,BLProposal blSubTowProposal,ArrayList tmlCAppntList,
                            PermiumAmountSchema permiumAmountSchema,String comCode,String operCode,
                            String MainPolicyNo,String SubOnePolicyNo,String SubTowPolicyNo,String policyType,String insuredSize,String newpaymode) throws Exception{
        String tempFlag="";
        BLPrpTinsured blPrpTinsured = new BLPrpTinsured();
        
        PrpTinsuredSchema       prpTinsuredSchemaApp       = new PrpTinsuredSchema();
        PrpTinsuredSchema       prpTinsuredSchemaInsured       = new PrpTinsuredSchema();
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
        PrpTmainSchema          prpTmainSchemaSubOne          = new PrpTmainSchema();
        PrpTmainSchema          prpTmainSchemaSubTow          = new PrpTmainSchema();
        
        BLPrpTmain blPrpTmain= new BLPrpTmain();
        BLPrpTmain blPrpTmainSubOne= new BLPrpTmain();
        BLPrpTmain blPrpTmainSubTow= new BLPrpTmain();
        prpTmainSchema.setProposalNo(MainPolicyNo);
        prpTmainSchema.setClassCode("30");
        prpTmainSchema.setRiskCode("3004");
        prpTmainSchema.setPolicySort("4");
        prpTmainSchema.setPrintNo(printNo);    
        
        
        prpTmainSchema.setBusinessNature((String)mainTemp.get(17));
        prpTmainSchema.setChannelType((String)mainTemp.get(16)); 
        
        prpTmainSchema.setLanguage("C");
        prpTmainSchema.setPolicyType(policyType);
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
        prpTmainSchema.setAutoTransRenewFlag(newpaymode);
        prpTmainSchema.setArgueSolution("1");
        prpTmainSchema.setPayTimes(payTimes);
        prpTmainSchema.setEndorseTimes("0");
        prpTmainSchema.setClaimTimes("0");
        prpTmainSchema.setMakeCom(comCode);
        prpTmainSchema.setComCode(comCode);
        prpTmainSchema.setPolicyNo((String)mainTemp.get(0));
        prpTmainSchema.setHandlerCode(operCode);
        prpTmainSchema.setHandler1Code(operCode);
        prpTmainSchema.setOperatorCode(operCode);
        prpTmainSchema.setInputDate((String)mainTemp.get(3));
        prpTmainSchema.setInputHour("0");
        prpTmainSchema.setCoinsFlag("0");
        prpTmainSchema.setAllinsFlag("2");
        prpTmainSchema.setUnderWriteFlag("0");
        prpTmainSchema.setOthFlag("000000YY000000000000");
        prpTmainSchema.setDisRate1("0.0000");
        prpTmainSchema.setBusinessFlag("0");
        prpTmainSchema.setUpdaterCode(operCode);
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
        blPrpTmain.setArr(prpTmainSchema);
        blMainProposal.setBLPrpTmain(blPrpTmain);
        
        prpTmainSchemaSubOne.setProposalNo(SubOnePolicyNo);
        prpTmainSchemaSubOne.setClassCode("27");
        prpTmainSchemaSubOne.setRiskCode("2700");
        prpTmainSchemaSubOne.setPolicySort("4");
        prpTmainSchemaSubOne.setPrintNo("");
        
        
        prpTmainSchemaSubOne.setBusinessNature(prpTmainSchema.getBusinessNature());
        prpTmainSchemaSubOne.setChannelType(prpTmainSchema.getChannelType()); 
        
        prpTmainSchemaSubOne.setLanguage("C");
        prpTmainSchemaSubOne.setPolicyType(policyType);
        prpTmainSchemaSubOne.setAppliCode(prpTinsuredSchemaApp.getInsuredCode());
        prpTmainSchemaSubOne.setAppliName(prpTinsuredSchemaApp.getInsuredName());
        prpTmainSchemaSubOne.setAppliAddress(prpTinsuredSchemaApp.getInsuredAddress());
        prpTmainSchemaSubOne.setInsuredName(prpTinsuredSchemaInsured.getInsuredName());
        prpTmainSchemaSubOne.setInsuredCode(prpTinsuredSchemaInsured.getInsuredCode());
        prpTmainSchemaSubOne.setInsuredAddress(prpTinsuredSchemaInsured.getInsuredAddress());
        prpTmainSchemaSubOne.setOperateDate((String)mainTemp.get(3));
        prpTmainSchemaSubOne.setStartDate((String)mainTemp.get(4));
        
        
        
        prpTmainSchemaSubOne.setStartHour("0");
        
        prpTmainSchemaSubOne.setEndDate(year+"-"+month+"-"+day);
        prpTmainSchemaSubOne.setEndHour("24");
        prpTmainSchemaSubOne.setPureRate("0.0000");
        prpTmainSchemaSubOne.setDisRate("0.0000");
        prpTmainSchemaSubOne.setDiscount("100.0000");
        prpTmainSchemaSubOne.setCurrency("CNY");
        prpTmainSchemaSubOne.setSumValue("0.00");
        prpTmainSchemaSubOne.setSumAmount(permiumAmountSchema.getSum27Amount());
        prpTmainSchemaSubOne.setSumPremium(permiumAmountSchema.getSum27Permium());
        prpTmainSchemaSubOne.setSumQuantity(insuredSize);
        prpTmainSchemaSubOne.setJudicalScope("中华人民共和国管辖(港澳台除外)");
        prpTmainSchemaSubOne.setAutoTransRenewFlag(newpaymode);
        prpTmainSchemaSubOne.setArgueSolution("1");
        prpTmainSchemaSubOne.setPayTimes(payTimes);
        prpTmainSchemaSubOne.setEndorseTimes("0");
        prpTmainSchemaSubOne.setClaimTimes("0");
        prpTmainSchemaSubOne.setMakeCom(comCode);
        prpTmainSchemaSubOne.setComCode(comCode);
        prpTmainSchemaSubOne.setHandlerCode(operCode);
        prpTmainSchemaSubOne.setHandler1Code(operCode);
        prpTmainSchemaSubOne.setOperatorCode(operCode);
        prpTmainSchemaSubOne.setInputDate((String)mainTemp.get(3));
        prpTmainSchemaSubOne.setInputHour("0");
        prpTmainSchemaSubOne.setCoinsFlag("0");
        prpTmainSchemaSubOne.setAllinsFlag("2");
        prpTmainSchemaSubOne.setUnderWriteFlag("0");
        prpTmainSchemaSubOne.setOthFlag("000000YY000000000000");
        prpTmainSchemaSubOne.setDisRate1("0.0000");
        prpTmainSchemaSubOne.setBusinessFlag("0");
        prpTmainSchemaSubOne.setUpdaterCode(operCode);
        prpTmainSchemaSubOne.setUpdateDate((String)mainTemp.get(3));
        prpTmainSchemaSubOne.setUpdateHour("0");
        prpTmainSchemaSubOne.setSignDate((String)mainTemp.get(3));
        prpTmainSchemaSubOne.setShareHolderFlag("0");
        prpTmainSchemaSubOne.setPayMode("1");
        prpTmainSchemaSubOne.setRemark((String)mainTemp.get(10));
        prpTmainSchemaSubOne.setManualType("");
        prpTmainSchemaSubOne.setProductCode("P00034");
        blPrpTmainSubOne.setArr(prpTmainSchemaSubOne);
        blSubOneProposal.setBLPrpTmain(blPrpTmainSubOne);
        
        
        prpTmainSchemaSubTow.setProposalNo(SubTowPolicyNo);
        prpTmainSchemaSubTow.setClassCode("03");
        prpTmainSchemaSubTow.setRiskCode("0301");
        prpTmainSchemaSubTow.setPolicySort("4");
        prpTmainSchemaSubTow.setPrintNo("");
        
        
        prpTmainSchemaSubTow.setBusinessNature(prpTmainSchema.getBusinessNature());
        prpTmainSchemaSubTow.setChannelType(prpTmainSchema.getChannelType()); 
        
        prpTmainSchemaSubTow.setLanguage("C");
        prpTmainSchemaSubTow.setPolicyType(policyType);
        prpTmainSchemaSubTow.setAppliCode(prpTinsuredSchemaApp.getInsuredCode());
        prpTmainSchemaSubTow.setAppliName(prpTinsuredSchemaApp.getInsuredName());
        prpTmainSchemaSubTow.setAppliAddress(prpTinsuredSchemaApp.getInsuredAddress());
        prpTmainSchemaSubTow.setInsuredName(prpTinsuredSchemaInsured.getInsuredName());
        prpTmainSchemaSubTow.setInsuredCode(prpTinsuredSchemaInsured.getInsuredCode());
        prpTmainSchemaSubTow.setInsuredAddress(prpTinsuredSchemaInsured.getInsuredAddress());
        prpTmainSchemaSubTow.setOperateDate((String)mainTemp.get(3));
        prpTmainSchemaSubTow.setStartDate((String)mainTemp.get(4));
        
        
        
        prpTmainSchemaSubTow.setStartHour("0");
        prpTmainSchemaSubTow.setEndDate(year+"-"+month+"-"+day);
        
        prpTmainSchemaSubTow.setEndHour("24");
        prpTmainSchemaSubTow.setPureRate("0.0000");
        prpTmainSchemaSubTow.setDisRate("0.0000");
        prpTmainSchemaSubTow.setDiscount("100.0000");
        prpTmainSchemaSubTow.setCurrency("CNY");
        prpTmainSchemaSubTow.setSumValue("0.00");
        prpTmainSchemaSubTow.setSumAmount(permiumAmountSchema.getSum03Amount());
        prpTmainSchemaSubTow.setSumPremium(permiumAmountSchema.getSum03Permium());
        prpTmainSchemaSubTow.setSumQuantity(insuredSize);
        prpTmainSchemaSubTow.setJudicalScope("中华人民共和国管辖(港澳台除外)");
        prpTmainSchemaSubTow.setAutoTransRenewFlag(newpaymode);
        prpTmainSchemaSubTow.setArgueSolution("1");
        prpTmainSchemaSubTow.setPayTimes(payTimes);
        prpTmainSchemaSubTow.setEndorseTimes("0");
        prpTmainSchemaSubTow.setClaimTimes("0");
        prpTmainSchemaSubTow.setMakeCom(comCode);
        prpTmainSchemaSubTow.setComCode(comCode);
        prpTmainSchemaSubTow.setHandlerCode(operCode);
        prpTmainSchemaSubTow.setHandler1Code(operCode);
        prpTmainSchemaSubTow.setOperatorCode(operCode);
        prpTmainSchemaSubTow.setInputDate((String)mainTemp.get(3));
        prpTmainSchemaSubTow.setInputHour("0");
        prpTmainSchemaSubTow.setCoinsFlag("0");
        prpTmainSchemaSubTow.setAllinsFlag("2");
        prpTmainSchemaSubTow.setUnderWriteFlag("0");
        prpTmainSchemaSubTow.setOthFlag("000000YY000000000000");
        prpTmainSchemaSubTow.setDisRate1("0.0000");
        prpTmainSchemaSubTow.setBusinessFlag("0");
        prpTmainSchemaSubTow.setUpdaterCode(operCode);
        prpTmainSchemaSubTow.setUpdateDate((String)mainTemp.get(3));
        prpTmainSchemaSubTow.setUpdateHour("0");
        prpTmainSchemaSubTow.setSignDate((String)mainTemp.get(3));
        prpTmainSchemaSubTow.setShareHolderFlag("0");
        prpTmainSchemaSubTow.setPayMode("1");
        prpTmainSchemaSubTow.setRemark((String)mainTemp.get(10));
        prpTmainSchemaSubTow.setManualType("");
        prpTmainSchemaSubTow.setDomesticFlag("0");
        prpTmainSchemaSubTow.setVentureFlag("0");
        prpTmainSchemaSubTow.setBidFlag("0");
        blPrpTmainSubTow.setArr(prpTmainSchemaSubTow);
        blSubTowProposal.setBLPrpTmain(blPrpTmainSubTow);
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
    
    
    public String getPhoneInsuredIdentity(String tmlInsuredIdentity){
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
    
    public String getInsuredIdentityNew(String tmlInsuredIdentity){
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
    public String getCardInsuredIdentity(String relation){
        if(relation!=null&&relation.trim().equals("父母")){
            return "50";
        }else if(relation!=null&&relation.trim().equals("父亲")){
            return "51";
        }else if(relation!=null&&relation.trim().equals("母亲")){
            return "52";
        }else if(relation!=null&&relation.trim().equals("岳父")){
            return "53";
        }else if(relation!=null&&relation.trim().equals("岳母")){
            return "54";
        }else if(relation!=null&&relation.trim().equals("公公")){
            return "55";
        }else if(relation!=null&&relation.trim().equals("婆婆")){
            return "56";
        }else if(relation!=null&&relation.trim().equals("本人")){
            return "01";
        }else if(relation!=null&&relation.trim().equals("配偶")){
            return "10";
        }else if(relation!=null&&relation.trim().equals("其他")){
            return "99";
        }else {
            return "99";
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
    
    
    public void transInsured(BLProposal blMainProposal,BLProposal blSubOneProposal,BLProposal blSubTowProposal,ArrayList tmlInsuredList,String insuredFlag,int appAndinsredSize,
                              String comCode,String operCode, String MainPolicyNo,String SubOnePolicyNo,String SubTowPolicyNo,ArrayList contList)throws Exception{
        
    	BLPrpDcustomerIdvNew blPrpDcustomerIdvNew = new BLPrpDcustomerIdvNew();
    	
        if("1".equals(insuredFlag)){
            PrpTinsuredSchema       prpTinsuredSchema       = new PrpTinsuredSchema();
            PrpTinsuredSchema       prpTinsuredSchemaSubOne       = new PrpTinsuredSchema();
            PrpTinsuredSchema       prpTinsuredSchemaSubTow       = new PrpTinsuredSchema();
            BLPrpTinsured           blPrpTinsured           = new BLPrpTinsured();
            BLPrpTinsured           blPrpTinsuredSubOne           = new BLPrpTinsured();
            BLPrpTinsured           blPrpTinsuredSubTow           = new BLPrpTinsured();
            PrpTinsuredNatureSchema prpTinsuredNatureSchema = new PrpTinsuredNatureSchema();
            PrpTinsuredNatureSchema prpTinsuredNatureSchemaOne = new PrpTinsuredNatureSchema();
            PrpTinsuredNatureSchema prpTinsuredNatureSchemaTow = new PrpTinsuredNatureSchema();
            BLPrpTinsuredNature           blPrpTinsuredNature           = new BLPrpTinsuredNature();
            BLPrpTinsuredNature           blPrpTinsuredNatureSubOne           = new BLPrpTinsuredNature();
            BLPrpTinsuredNature           blPrpTinsuredNatureSubTow           = new BLPrpTinsuredNature();
            
            
            ArrayList appList=(ArrayList)tmlInsuredList.get(0);
            
            prpTinsuredSchema.setProposalNo(MainPolicyNo);
            prpTinsuredSchema.setRiskCode("3004");
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
            
            
            
            
            prpTinsuredSchemaSubOne.setProposalNo(SubOnePolicyNo);
            prpTinsuredSchemaSubOne.setRiskCode("2700");
            prpTinsuredSchemaSubOne.setSerialNo("1");
            prpTinsuredSchemaSubOne.setLanguage("C");
            prpTinsuredSchemaSubOne.setInsuredType("1");
            
            prpTinsuredSchemaSubOne.setInsuredName((String)appList.get(0));
            
            prpTinsuredSchemaSubOne.setInsuredAddress((String)appList.get(13));
            
            prpTinsuredSchemaSubOne.setInsuredNature("3");
            prpTinsuredSchemaSubOne.setInsuredFlag("2");
            
            
            prpTinsuredSchemaSubOne.setInsuredIdentity(getPhoneInsuredIdentity((String)appList.get(11)));
            
            prpTinsuredSchemaSubOne.setRelateSerialNo("");
            prpTinsuredSchemaSubOne.setIdentifyType(getIdtype((String)appList.get(3)));
            prpTinsuredSchemaSubOne.setIdentifyNumber((String)appList.get(4));
            prpTinsuredSchemaSubOne.setOccupationCode((String)appList.get(9));
            prpTinsuredSchemaSubOne.setPostCode((String)appList.get(14));
            prpTinsuredSchemaSubOne.setEmail((String)appList.get(12));
            prpTinsuredSchemaSubOne.setPhoneNumber((String)appList.get(15));
            prpTinsuredSchemaSubOne.setBank((String)contList.get(11));
            prpTinsuredSchemaSubOne.setAccount((String)contList.get(12));
            prpTinsuredSchemaSubOne.setAccountName((String)contList.get(13));
            prpTinsuredSchemaSubOne.setBenefitFlag("N");
            prpTinsuredSchemaSubOne.setBenefitRate("0.00");
            prpTinsuredSchemaSubOne.setInsuredCode(prpTinsuredSchema.getInsuredCode());
            
            
            prpTinsuredNatureSchemaOne.setProposalNo(SubOnePolicyNo);
            prpTinsuredNatureSchemaOne.setSerialNo("1");
            prpTinsuredNatureSchemaOne.setInsuredFlag("2");
            prpTinsuredNatureSchemaOne.setSex(getSexCode((String)appList.get(1)));
            prpTinsuredNatureSchemaOne.setAge("");
            prpTinsuredNatureSchemaOne.setBirthday((String)appList.get(2));
            prpTinsuredNatureSchemaOne.setUnit("");
            prpTinsuredNatureSchemaOne.setUnitPostCode("");
            prpTinsuredNatureSchemaOne.setUnitType("");
            prpTinsuredNatureSchemaOne.setLocalPoliceStation("");
            prpTinsuredNatureSchemaOne.setRoomAddress((String)appList.get(13));
            prpTinsuredNatureSchemaOne.setRoomPostCode((String)appList.get(14));
            prpTinsuredNatureSchemaOne.setRoomPhone((String)appList.get(15));
            prpTinsuredNatureSchemaOne.setMobile("");
            prpTinsuredNatureSchemaOne.setMarriage((String)appList.get(5));
            
            blPrpTinsuredSubOne.setArr(prpTinsuredSchemaSubOne);
            blSubOneProposal.setBLPrpTinsured(blPrpTinsuredSubOne);
            blPrpTinsuredNatureSubOne.setArr(prpTinsuredNatureSchemaOne);
            blSubOneProposal.setBLPrpTinsuredNature(blPrpTinsuredNatureSubOne);
            
            
            
            
            
            prpTinsuredSchemaSubTow.setProposalNo(SubTowPolicyNo);
            prpTinsuredSchemaSubTow.setRiskCode("0301");
            prpTinsuredSchemaSubTow.setSerialNo("1");
            prpTinsuredSchemaSubTow.setLanguage("C");
            prpTinsuredSchemaSubTow.setInsuredType("1");
            
            prpTinsuredSchemaSubTow.setInsuredName((String)appList.get(0));
            
            prpTinsuredSchemaSubTow.setInsuredAddress((String)appList.get(13));
            
            prpTinsuredSchemaSubTow.setInsuredNature("3");
            prpTinsuredSchemaSubTow.setInsuredFlag("2");
            
            
            prpTinsuredSchemaSubTow.setInsuredIdentity(getPhoneInsuredIdentity((String)appList.get(11)));
            
            prpTinsuredSchemaSubTow.setRelateSerialNo("");
            prpTinsuredSchemaSubTow.setIdentifyType(getIdtype((String)appList.get(3)));
            prpTinsuredSchemaSubTow.setIdentifyNumber((String)appList.get(4));
            prpTinsuredSchemaSubTow.setOccupationCode((String)appList.get(9));
            prpTinsuredSchemaSubTow.setPostCode((String)appList.get(14));
            prpTinsuredSchemaSubTow.setEmail((String)appList.get(12));
            prpTinsuredSchemaSubTow.setPhoneNumber((String)appList.get(15));
            prpTinsuredSchemaSubTow.setBank((String)contList.get(11));
            prpTinsuredSchemaSubTow.setAccount((String)contList.get(12));
            prpTinsuredSchemaSubTow.setAccountName((String)contList.get(13));
            prpTinsuredSchemaSubTow.setBenefitFlag("N");
            prpTinsuredSchemaSubTow.setBenefitRate("0.00");
            prpTinsuredSchemaSubTow.setInsuredCode(prpTinsuredSchema.getInsuredCode());
            
            
            prpTinsuredNatureSchemaTow.setProposalNo(SubTowPolicyNo);
            prpTinsuredNatureSchemaTow.setSerialNo("1");
            prpTinsuredNatureSchemaTow.setInsuredFlag("2");
            prpTinsuredNatureSchemaTow.setSex(getSexCode((String)appList.get(1)));
            prpTinsuredNatureSchemaTow.setAge("");
            prpTinsuredNatureSchemaTow.setBirthday((String)appList.get(2));
            prpTinsuredNatureSchemaTow.setUnit("");
            prpTinsuredNatureSchemaTow.setUnitPostCode("");
            prpTinsuredNatureSchemaTow.setUnitType("");
            prpTinsuredNatureSchemaTow.setLocalPoliceStation("");
            prpTinsuredNatureSchemaTow.setRoomAddress((String)appList.get(13));
            prpTinsuredNatureSchemaTow.setRoomPostCode((String)appList.get(14));
            prpTinsuredNatureSchemaTow.setRoomPhone((String)appList.get(15));
            prpTinsuredNatureSchemaTow.setMobile("");
            prpTinsuredNatureSchemaTow.setMarriage((String)appList.get(5));
            blPrpTinsuredSubTow.setArr(prpTinsuredSchemaSubTow);
            blSubTowProposal.setBLPrpTinsured(blPrpTinsuredSubTow);
            blPrpTinsuredNatureSubTow.setArr(prpTinsuredNatureSchemaTow);
            blSubTowProposal.setBLPrpTinsuredNature(blPrpTinsuredNatureSubTow);
        }else if("2".equals(insuredFlag)){
            
            String tempBenRenFlag="";
            String tempFuQiFlag="";
            String tempZiNvFlag="";
            
            String childrenNames="";
            for(int i=0;i<tmlInsuredList.size();i++){
                ArrayList insuredList = new ArrayList();
                insuredList=(ArrayList)tmlInsuredList.get(i);
                String insurFlag=getInsuredIdentity((String)insuredList.get(10));
                String insuredNo=getInsuredIdentityNew((String)insuredList.get(16));
                
                if(!insuredNo.equals("")){
                    insurFlag=insuredNo;
                }
                if(insurFlag.equals("40")){
                    if(i==tmlInsuredList.size()-1)
                      childrenNames=childrenNames+(String)insuredList.get(0);
                    else
                      childrenNames=childrenNames+(String)insuredList.get(0)+" ";  
                }
                
            }
            
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
                    PrpTinsuredSchema       prpTinsuredSchemaSubOne       = new PrpTinsuredSchema();
                    PrpTinsuredSchema       prpTinsuredSchemaSubTow       = new PrpTinsuredSchema();
                    PrpTinsuredNatureSchema prpTinsuredNatureSchema = new PrpTinsuredNatureSchema();
                    PrpTinsuredNatureSchema prpTinsuredNatureSchemaOne = new PrpTinsuredNatureSchema();
                    PrpTinsuredNatureSchema prpTinsuredNatureSchemaTow = new PrpTinsuredNatureSchema();
                    prpTinsuredSchema.setProposalNo(MainPolicyNo);
                    prpTinsuredSchema.setRiskCode("3004");
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
                    prpTinsuredSchemaSubOne.setProposalNo(SubOnePolicyNo);
                    prpTinsuredSchemaSubOne.setRiskCode("2700");
                    prpTinsuredSchemaSubOne.setSerialNo((blSubOneProposal.getBLPrpTinsured().getSize()+1)+"");
                    prpTinsuredSchemaSubOne.setLanguage("C");
                    prpTinsuredSchemaSubOne.setInsuredType("1");
                    
                    prpTinsuredSchemaSubOne.setInsuredName((String)insuredList.get(0));
                    prpTinsuredSchemaSubOne.setInsuredAddress((String)insuredList.get(12));
                    prpTinsuredSchemaSubOne.setInsuredNature("3");
                    prpTinsuredSchemaSubOne.setInsuredFlag("1");
                    prpTinsuredSchemaSubOne.setInsuredIdentity(insurFlag);
                    prpTinsuredSchemaSubOne.setRelateSerialNo("");
                    prpTinsuredSchemaSubOne.setIdentifyType(getIdtype((String)insuredList.get(3)));
                    prpTinsuredSchemaSubOne.setIdentifyNumber((String)insuredList.get(4));
                    prpTinsuredSchemaSubOne.setOccupationCode((String)insuredList.get(8));
                    
                    prpTinsuredSchemaSubOne.setEmail((String)insuredList.get(11));
                    
                    prpTinsuredSchemaSubOne.setBenefitFlag("N");
                    prpTinsuredSchemaSubOne.setBenefitRate("0.00");
                    
                      
                    
                        prpTinsuredSchemaSubOne.setInsuredCode(prpTinsuredSchema.getInsuredCode());
                    
                    prpTinsuredNatureSchemaOne.setProposalNo(SubOnePolicyNo);
                    prpTinsuredNatureSchemaOne.setSerialNo((blSubOneProposal.getBLPrpTinsuredNature().getSize()+1)+"");
                    prpTinsuredNatureSchemaOne.setInsuredFlag("1");
                    prpTinsuredNatureSchemaOne.setSex(getSexCode((String)insuredList.get(1)));
                    prpTinsuredNatureSchemaOne.setAge((String)insuredList.get(15));
                    prpTinsuredNatureSchemaOne.setBirthday((String)insuredList.get(2));
                    prpTinsuredNatureSchemaOne.setUnit("");
                    prpTinsuredNatureSchemaOne.setUnitPostCode("");
                    prpTinsuredNatureSchemaOne.setUnitType("");
                    prpTinsuredNatureSchemaOne.setLocalPoliceStation("");
                    prpTinsuredNatureSchemaOne.setRoomAddress((String)insuredList.get(12));
                    prpTinsuredNatureSchemaOne.setRoomPostCode((String)insuredList.get(13));
                    prpTinsuredNatureSchemaOne.setRoomPhone((String)insuredList.get(14));
                    prpTinsuredNatureSchemaOne.setMobile("");
                    prpTinsuredNatureSchemaOne.setMarriage((String)insuredList.get(5));
                    blSubOneProposal.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchemaOne);
                    
                    blSubOneProposal.getBLPrpTinsured().setArr(prpTinsuredSchemaSubOne);
                    
                    
                    
                    
                    prpTinsuredSchemaSubTow.setProposalNo(SubTowPolicyNo);
                    prpTinsuredSchemaSubTow.setRiskCode("0301");
                    prpTinsuredSchemaSubTow.setSerialNo((blSubTowProposal.getBLPrpTinsured().getSize()+1)+"");
                    prpTinsuredSchemaSubTow.setLanguage("C");
                    prpTinsuredSchemaSubTow.setInsuredType("1");
                    
                    prpTinsuredSchemaSubTow.setInsuredName((String)insuredList.get(0));
                    prpTinsuredSchemaSubTow.setInsuredAddress((String)insuredList.get(12));
                    prpTinsuredSchemaSubTow.setInsuredNature("3");
                    prpTinsuredSchemaSubTow.setInsuredFlag("1");
                    prpTinsuredSchemaSubTow.setInsuredIdentity(insurFlag);
                    prpTinsuredSchemaSubTow.setRelateSerialNo("");
                    prpTinsuredSchemaSubTow.setIdentifyType(getIdtype((String)insuredList.get(3)));
                    prpTinsuredSchemaSubTow.setIdentifyNumber((String)insuredList.get(4));
                    prpTinsuredSchemaSubTow.setOccupationCode((String)insuredList.get(8));
                    
                    prpTinsuredSchemaSubTow.setEmail((String)insuredList.get(11));
                    
                    prpTinsuredSchemaSubTow.setBenefitFlag("N");
                    prpTinsuredSchemaSubTow.setBenefitRate("0.00");
                    
                       
                    
                        prpTinsuredSchemaSubTow.setInsuredCode(prpTinsuredSchema.getInsuredCode());
                    
                    prpTinsuredNatureSchemaTow.setProposalNo(SubTowPolicyNo);
                    prpTinsuredNatureSchemaTow.setSerialNo((blSubTowProposal.getBLPrpTinsuredNature().getSize()+1)+"");
                    prpTinsuredNatureSchemaTow.setInsuredFlag("1");
                    prpTinsuredNatureSchemaTow.setSex(getSexCode((String)insuredList.get(1)));
                    prpTinsuredNatureSchemaTow.setAge((String)insuredList.get(15));
                    prpTinsuredNatureSchemaTow.setBirthday((String)insuredList.get(2));
                    prpTinsuredNatureSchemaTow.setUnit("");
                    prpTinsuredNatureSchemaTow.setUnitPostCode("");
                    prpTinsuredNatureSchemaTow.setUnitType("");
                    prpTinsuredNatureSchemaTow.setLocalPoliceStation("");
                    prpTinsuredNatureSchemaTow.setRoomAddress((String)insuredList.get(12));
                    prpTinsuredNatureSchemaTow.setRoomPostCode((String)insuredList.get(13));
                    prpTinsuredNatureSchemaTow.setRoomPhone((String)insuredList.get(14));
                    prpTinsuredNatureSchemaTow.setMobile("");
                    prpTinsuredNatureSchemaTow.setMarriage((String)insuredList.get(5));
                    blSubTowProposal.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchemaTow);
                    
                    blSubTowProposal.getBLPrpTinsured().setArr(prpTinsuredSchemaSubTow);
                    
                }else if(insurFlag.equals("10")&&tempFuQiFlag.equals("")){
                    tempFuQiFlag="1";
                    PrpTinsuredSchema       prpTinsuredSchema       = new PrpTinsuredSchema();
                    PrpTinsuredSchema       prpTinsuredSchemaSubOne       = new PrpTinsuredSchema();
                    PrpTinsuredSchema       prpTinsuredSchemaSubTow       = new PrpTinsuredSchema();
                    PrpTinsuredNatureSchema prpTinsuredNatureSchema = new PrpTinsuredNatureSchema();
                    PrpTinsuredNatureSchema prpTinsuredNatureSchemaOne = new PrpTinsuredNatureSchema();
                    PrpTinsuredNatureSchema prpTinsuredNatureSchemaTow = new PrpTinsuredNatureSchema();
                    prpTinsuredSchema.setProposalNo(MainPolicyNo);
                    prpTinsuredSchema.setRiskCode("3004");
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
                    prpTinsuredSchemaSubOne.setProposalNo(SubOnePolicyNo);
                    prpTinsuredSchemaSubOne.setRiskCode("2700");
                    prpTinsuredSchemaSubOne.setSerialNo((blSubOneProposal.getBLPrpTinsured().getSize()+1)+"");
                    prpTinsuredSchemaSubOne.setLanguage("C");
                    prpTinsuredSchemaSubOne.setInsuredType("1");
                    
                    prpTinsuredSchemaSubOne.setInsuredName((String)insuredList.get(0));
                    prpTinsuredSchemaSubOne.setInsuredAddress((String)insuredList.get(12));
                    prpTinsuredSchemaSubOne.setInsuredNature("3");
                    prpTinsuredSchemaSubOne.setInsuredFlag("1");
                    prpTinsuredSchemaSubOne.setInsuredIdentity(insurFlag);
                    prpTinsuredSchemaSubOne.setRelateSerialNo("");
                    prpTinsuredSchemaSubOne.setIdentifyType(getIdtype((String)insuredList.get(3)));
                    prpTinsuredSchemaSubOne.setIdentifyNumber((String)insuredList.get(4));
                    prpTinsuredSchemaSubOne.setOccupationCode((String)insuredList.get(8));
                    
                    prpTinsuredSchemaSubOne.setEmail((String)insuredList.get(11));
                    
                    prpTinsuredSchemaSubOne.setBenefitFlag("N");
                    prpTinsuredSchemaSubOne.setBenefitRate("0.00");
                    
                      
                    
                        prpTinsuredSchemaSubOne.setInsuredCode(prpTinsuredSchema.getInsuredCode());
                    
                    prpTinsuredNatureSchemaOne.setProposalNo(SubOnePolicyNo);
                    prpTinsuredNatureSchemaOne.setSerialNo((blSubOneProposal.getBLPrpTinsuredNature().getSize()+1)+"");
                    prpTinsuredNatureSchemaOne.setInsuredFlag("1");
                    prpTinsuredNatureSchemaOne.setSex(getSexCode((String)insuredList.get(1)));
                    prpTinsuredNatureSchemaOne.setAge((String)insuredList.get(15));
                    prpTinsuredNatureSchemaOne.setBirthday((String)insuredList.get(2));
                    prpTinsuredNatureSchemaOne.setUnit("");
                    prpTinsuredNatureSchemaOne.setUnitPostCode("");
                    prpTinsuredNatureSchemaOne.setUnitType("");
                    prpTinsuredNatureSchemaOne.setLocalPoliceStation("");
                    prpTinsuredNatureSchemaOne.setRoomAddress((String)insuredList.get(12));
                    prpTinsuredNatureSchemaOne.setRoomPostCode((String)insuredList.get(13));
                    prpTinsuredNatureSchemaOne.setRoomPhone((String)insuredList.get(14));
                    prpTinsuredNatureSchemaOne.setMobile("");
                    prpTinsuredNatureSchemaOne.setMarriage((String)insuredList.get(5));
                    blSubOneProposal.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchemaOne);
                    
                    blSubOneProposal.getBLPrpTinsured().setArr(prpTinsuredSchemaSubOne);
                    
                    
                    
                    
                    prpTinsuredSchemaSubTow.setProposalNo(SubTowPolicyNo);
                    prpTinsuredSchemaSubTow.setRiskCode("0301");
                    prpTinsuredSchemaSubTow.setSerialNo((blSubTowProposal.getBLPrpTinsured().getSize()+1)+"");
                    prpTinsuredSchemaSubTow.setLanguage("C");
                    prpTinsuredSchemaSubTow.setInsuredType("1");
                    
                    prpTinsuredSchemaSubTow.setInsuredName((String)insuredList.get(0));
                    prpTinsuredSchemaSubTow.setInsuredAddress((String)insuredList.get(12));
                    prpTinsuredSchemaSubTow.setInsuredNature("3");
                    prpTinsuredSchemaSubTow.setInsuredFlag("1");
                    prpTinsuredSchemaSubTow.setInsuredIdentity(insurFlag);
                    prpTinsuredSchemaSubTow.setRelateSerialNo("");
                    prpTinsuredSchemaSubTow.setIdentifyType(getIdtype((String)insuredList.get(3)));
                    prpTinsuredSchemaSubTow.setIdentifyNumber((String)insuredList.get(4));
                    prpTinsuredSchemaSubTow.setOccupationCode((String)insuredList.get(8));
                    
                    prpTinsuredSchemaSubTow.setEmail((String)insuredList.get(11));
                    
                    prpTinsuredSchemaSubTow.setBenefitFlag("N");
                    prpTinsuredSchemaSubTow.setBenefitRate("0.00");
                    
                      
                    
                        prpTinsuredSchemaSubTow.setInsuredCode(prpTinsuredSchema.getInsuredCode());
                    
                    prpTinsuredNatureSchemaTow.setProposalNo(SubTowPolicyNo);
                    prpTinsuredNatureSchemaTow.setSerialNo((blSubTowProposal.getBLPrpTinsuredNature().getSize()+1)+"");
                    prpTinsuredNatureSchemaTow.setInsuredFlag("1");
                    prpTinsuredNatureSchemaTow.setSex(getSexCode((String)insuredList.get(1)));
                    prpTinsuredNatureSchemaTow.setAge((String)insuredList.get(15));
                    prpTinsuredNatureSchemaTow.setBirthday((String)insuredList.get(2));
                    prpTinsuredNatureSchemaTow.setUnit("");
                    prpTinsuredNatureSchemaTow.setUnitPostCode("");
                    prpTinsuredNatureSchemaTow.setUnitType("");
                    prpTinsuredNatureSchemaTow.setLocalPoliceStation("");
                    prpTinsuredNatureSchemaTow.setRoomAddress((String)insuredList.get(12));
                    prpTinsuredNatureSchemaTow.setRoomPostCode((String)insuredList.get(13));
                    prpTinsuredNatureSchemaTow.setRoomPhone((String)insuredList.get(14));
                    prpTinsuredNatureSchemaTow.setMobile("");
                    prpTinsuredNatureSchemaTow.setMarriage((String)insuredList.get(5));
                    blSubTowProposal.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchemaTow);
                    
                    blSubTowProposal.getBLPrpTinsured().setArr(prpTinsuredSchemaSubTow);
                    
                }else if(insurFlag.equals("40")&&tempZiNvFlag.equals("")){
                    tempZiNvFlag="1";
                    PrpTinsuredSchema       prpTinsuredSchema       = new PrpTinsuredSchema();
                    PrpTinsuredSchema       prpTinsuredSchemaSubOne       = new PrpTinsuredSchema();
                    PrpTinsuredSchema       prpTinsuredSchemaSubTow       = new PrpTinsuredSchema();
                    PrpTinsuredNatureSchema prpTinsuredNatureSchema = new PrpTinsuredNatureSchema();
                    PrpTinsuredNatureSchema prpTinsuredNatureSchemaOne = new PrpTinsuredNatureSchema();
                    PrpTinsuredNatureSchema prpTinsuredNatureSchemaTow = new PrpTinsuredNatureSchema();
                    prpTinsuredSchema.setProposalNo(MainPolicyNo);
                    prpTinsuredSchema.setRiskCode("3004");
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
                    
                      
                    
                    

                    prpTinsuredSchema.setInsuredCode(blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema,comCode,operCode,operCode));
                    
                    
                    prpTinsuredNatureSchema.setProposalNo(MainPolicyNo);
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
                    prpTinsuredSchemaSubOne.setProposalNo(SubOnePolicyNo);
                    prpTinsuredSchemaSubOne.setRiskCode("2700");
                    prpTinsuredSchemaSubOne.setSerialNo((blSubOneProposal.getBLPrpTinsured().getSize()+1)+"");
                    prpTinsuredSchemaSubOne.setLanguage("C");
                    prpTinsuredSchemaSubOne.setInsuredType("1");
                    
                    prpTinsuredSchemaSubOne.setInsuredName((String)insuredList.get(0));
                    prpTinsuredSchemaSubOne.setInsuredAddress((String)insuredList.get(12));
                    prpTinsuredSchemaSubOne.setInsuredNature("3");
                    prpTinsuredSchemaSubOne.setInsuredFlag("1");
                    prpTinsuredSchemaSubOne.setInsuredIdentity(insurFlag);
                    prpTinsuredSchemaSubOne.setRelateSerialNo("");
                    prpTinsuredSchemaSubOne.setIdentifyType(getIdtype((String)insuredList.get(3)));
                    prpTinsuredSchemaSubOne.setIdentifyNumber((String)insuredList.get(4));
                    prpTinsuredSchemaSubOne.setOccupationCode((String)insuredList.get(8));
                    
                    prpTinsuredSchemaSubOne.setEmail((String)insuredList.get(11));
                    
                    prpTinsuredSchemaSubOne.setBenefitFlag("N");
                    prpTinsuredSchemaSubOne.setBenefitRate("0.00");
                    
                      
                    
                        prpTinsuredSchemaSubOne.setInsuredCode(prpTinsuredSchema.getInsuredCode());
                    
                    prpTinsuredNatureSchemaOne.setProposalNo(SubOnePolicyNo);
                    prpTinsuredNatureSchemaOne.setSerialNo((blSubOneProposal.getBLPrpTinsuredNature().getSize()+1)+"");
                    prpTinsuredNatureSchemaOne.setInsuredFlag("1");
                    prpTinsuredNatureSchemaOne.setSex(getSexCode((String)insuredList.get(1)));
                    prpTinsuredNatureSchemaOne.setAge((String)insuredList.get(15));
                    prpTinsuredNatureSchemaOne.setBirthday((String)insuredList.get(2));
                    prpTinsuredNatureSchemaOne.setUnit("");
                    prpTinsuredNatureSchemaOne.setUnitPostCode("");
                    prpTinsuredNatureSchemaOne.setUnitType("");
                    prpTinsuredNatureSchemaOne.setLocalPoliceStation("");
                    prpTinsuredNatureSchemaOne.setRoomAddress((String)insuredList.get(12));
                    prpTinsuredNatureSchemaOne.setRoomPostCode((String)insuredList.get(13));
                    prpTinsuredNatureSchemaOne.setRoomPhone((String)insuredList.get(14));
                    prpTinsuredNatureSchemaOne.setMobile("");
                    prpTinsuredNatureSchemaOne.setMarriage((String)insuredList.get(5));
                    blSubOneProposal.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchemaOne);
                    
                    blSubOneProposal.getBLPrpTinsured().setArr(prpTinsuredSchemaSubOne);
                    
                    
                    
                    
                    prpTinsuredSchemaSubTow.setProposalNo(SubTowPolicyNo);
                    prpTinsuredSchemaSubTow.setRiskCode("0301");
                    prpTinsuredSchemaSubTow.setSerialNo((blSubTowProposal.getBLPrpTinsured().getSize()+1)+"");
                    prpTinsuredSchemaSubTow.setLanguage("C");
                    prpTinsuredSchemaSubTow.setInsuredType("1");
                    
                    prpTinsuredSchemaSubTow.setInsuredName((String)insuredList.get(0));
                    prpTinsuredSchemaSubTow.setInsuredAddress((String)insuredList.get(12));
                    prpTinsuredSchemaSubTow.setInsuredNature("3");
                    prpTinsuredSchemaSubTow.setInsuredFlag("1");
                    prpTinsuredSchemaSubTow.setInsuredIdentity(insurFlag);
                    prpTinsuredSchemaSubTow.setRelateSerialNo("");
                    prpTinsuredSchemaSubTow.setIdentifyType(getIdtype((String)insuredList.get(3)));
                    prpTinsuredSchemaSubTow.setIdentifyNumber((String)insuredList.get(4));
                    prpTinsuredSchemaSubTow.setOccupationCode((String)insuredList.get(8));
                    
                    prpTinsuredSchemaSubTow.setEmail((String)insuredList.get(11));
                    
                    prpTinsuredSchemaSubTow.setBenefitFlag("N");
                    prpTinsuredSchemaSubTow.setBenefitRate("0.00");
                    
                      
                    
                        prpTinsuredSchemaSubTow.setInsuredCode(prpTinsuredSchema.getInsuredCode());
                    
                    prpTinsuredNatureSchemaTow.setProposalNo(SubTowPolicyNo);
                    prpTinsuredNatureSchemaTow.setSerialNo((blSubTowProposal.getBLPrpTinsuredNature().getSize()+1)+"");
                    prpTinsuredNatureSchemaTow.setInsuredFlag("1");
                    prpTinsuredNatureSchemaTow.setSex(getSexCode((String)insuredList.get(1)));
                    prpTinsuredNatureSchemaTow.setAge((String)insuredList.get(15));
                    prpTinsuredNatureSchemaTow.setBirthday((String)insuredList.get(2));
                    prpTinsuredNatureSchemaTow.setUnit("");
                    prpTinsuredNatureSchemaTow.setUnitPostCode("");
                    prpTinsuredNatureSchemaTow.setUnitType("");
                    prpTinsuredNatureSchemaTow.setLocalPoliceStation("");
                    prpTinsuredNatureSchemaTow.setRoomAddress((String)insuredList.get(12));
                    prpTinsuredNatureSchemaTow.setRoomPostCode((String)insuredList.get(13));
                    prpTinsuredNatureSchemaTow.setRoomPhone((String)insuredList.get(14));
                    prpTinsuredNatureSchemaTow.setMobile("");
                    prpTinsuredNatureSchemaTow.setMarriage((String)insuredList.get(5));
                    blSubTowProposal.getBLPrpTinsuredNature().setArr(prpTinsuredNatureSchemaTow);
                    
                    blSubTowProposal.getBLPrpTinsured().setArr(prpTinsuredSchemaSubTow);
                }
                
            }
            
        }else if("3".equals(insuredFlag)){
            for(int i=0;i<tmlInsuredList.size();i++){
                PrpTinsuredSchema       prpTinsuredSchema       = new PrpTinsuredSchema();
                PrpTinsuredSchema       prpTinsuredSchemaSubOne       = new PrpTinsuredSchema();
                PrpTinsuredSchema       prpTinsuredSchemaSubTow       = new PrpTinsuredSchema();
                PrpTinsuredNatureSchema prpTinsuredNatureSchema = new PrpTinsuredNatureSchema();
                PrpTinsuredNatureSchema prpTinsuredNatureSchemaOne = new PrpTinsuredNatureSchema();
                PrpTinsuredNatureSchema prpTinsuredNatureSchemaTow = new PrpTinsuredNatureSchema();
                BLPrpTinsuredNature           blPrpTinsuredNature           = blMainProposal.getBLPrpTinsuredNature();
                BLPrpTinsuredNature           blPrpTinsuredNatureSubOne           = blSubOneProposal.getBLPrpTinsuredNature();
                BLPrpTinsuredNature           blPrpTinsuredNatureSubTow           = blSubTowProposal.getBLPrpTinsuredNature();
                BLPrpTinsured           blPrpTinsured           =  blMainProposal.getBLPrpTinsured();
                BLPrpTinsured           blPrpTinsuredSubOne           = blSubOneProposal.getBLPrpTinsured();
                BLPrpTinsured           blPrpTinsuredSubTow           = blSubTowProposal.getBLPrpTinsured();
                ArrayList bnfList = new ArrayList();
                
                bnfList=(ArrayList)tmlInsuredList.get(i);
                
                
                
                prpTinsuredSchema.setProposalNo(MainPolicyNo);
                prpTinsuredSchema.setRiskCode("3004");
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
                
                  
                
                

                prpTinsuredSchema.setInsuredCode(blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema,comCode,operCode,operCode));
                
                
                
                prpTinsuredNatureSchema.setProposalNo(MainPolicyNo);
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
                blPrpTinsuredNature.setArr(prpTinsuredNatureSchema);
                blMainProposal.setBLPrpTinsuredNature(blPrpTinsuredNature);
                blPrpTinsured.setArr(prpTinsuredSchema);
                blMainProposal.setBLPrpTinsured(blPrpTinsured);
                
                
                
                prpTinsuredSchemaSubOne.setProposalNo(SubOnePolicyNo);
                prpTinsuredSchemaSubOne.setRiskCode("2700");
                prpTinsuredSchemaSubOne.setSerialNo((blSubOneProposal.getBLPrpTinsured().getSize()+1)+"");
                prpTinsuredSchemaSubOne.setLanguage("C");
                prpTinsuredSchemaSubOne.setInsuredType("1");
                prpTinsuredSchemaSubOne.setInsuredName((String)bnfList.get(3));
                prpTinsuredSchemaSubOne.setInsuredNature("3");
                prpTinsuredSchemaSubOne.setInsuredFlag("9");
                prpTinsuredSchemaSubOne.setInsuredIdentity(getInsuredIdentity((String)bnfList.get(2)));
                prpTinsuredSchemaSubOne.setRelateSerialNo("2");
                prpTinsuredSchemaSubOne.setIdentifyType(getIdtype((String)bnfList.get(6)));
                prpTinsuredSchemaSubOne.setIdentifyNumber((String)bnfList.get(7));
                prpTinsuredSchemaSubOne.setBenefitRate("100.00");
                
                  
                
                    prpTinsuredSchemaSubOne.setInsuredCode(prpTinsuredSchema.getInsuredCode());
                
                prpTinsuredNatureSchemaOne.setProposalNo(SubOnePolicyNo);
                prpTinsuredNatureSchemaOne.setSerialNo((blSubOneProposal.getBLPrpTinsuredNature().getSize()+1)+"");
                prpTinsuredNatureSchemaOne.setInsuredFlag("9");
                prpTinsuredNatureSchemaOne.setSex(getSexCode((String)bnfList.get(4)));
                prpTinsuredNatureSchemaOne.setAge("");
                prpTinsuredNatureSchemaOne.setBirthday("");
                prpTinsuredNatureSchemaOne.setUnit("");
                prpTinsuredNatureSchemaOne.setUnitPostCode("");
                prpTinsuredNatureSchemaOne.setUnitType("");
                prpTinsuredNatureSchemaOne.setLocalPoliceStation("");
                prpTinsuredNatureSchemaOne.setRoomAddress("");
                prpTinsuredNatureSchemaOne.setRoomPostCode("");
                prpTinsuredNatureSchemaOne.setRoomPhone("");
                prpTinsuredNatureSchemaOne.setMobile("");
                prpTinsuredNatureSchemaOne.setMarriage("");
                prpTinsuredNatureSchemaOne.setWeight("");
                prpTinsuredNatureSchemaOne.setStature("");
                blPrpTinsuredNatureSubOne.setArr(prpTinsuredNatureSchemaOne);
                blSubOneProposal.setBLPrpTinsuredNature(blPrpTinsuredNatureSubOne);
                blPrpTinsuredSubOne.setArr(prpTinsuredSchemaSubOne);
                blSubOneProposal.setBLPrpTinsured(blPrpTinsuredSubOne);
                
                
                
                
                prpTinsuredSchemaSubTow.setProposalNo(SubTowPolicyNo);
                prpTinsuredSchemaSubTow.setRiskCode("0301");
                prpTinsuredSchemaSubTow.setSerialNo((blSubTowProposal.getBLPrpTinsured().getSize()+1)+"");
                prpTinsuredSchemaSubTow.setLanguage("C");
                prpTinsuredSchemaSubTow.setInsuredType("1");
                
                prpTinsuredSchemaSubTow.setInsuredName((String)bnfList.get(3));
                prpTinsuredSchemaSubTow.setInsuredNature("3");
                prpTinsuredSchemaSubTow.setInsuredFlag("9");
                prpTinsuredSchemaSubTow.setInsuredIdentity(getInsuredIdentity((String)bnfList.get(2)));
                prpTinsuredSchemaSubTow.setRelateSerialNo("2");
                prpTinsuredSchemaSubTow.setIdentifyType(getIdtype((String)bnfList.get(6)));
                prpTinsuredSchemaSubTow.setIdentifyNumber((String)bnfList.get(7));
                prpTinsuredSchemaSubTow.setBenefitRate("100.00");
                
                  
                
                    prpTinsuredSchemaSubTow.setInsuredCode(prpTinsuredSchema.getInsuredCode());
                
                prpTinsuredNatureSchemaTow.setProposalNo(SubTowPolicyNo);
                prpTinsuredNatureSchemaTow.setSerialNo((blSubTowProposal.getBLPrpTinsuredNature().getSize()+1)+"");
                prpTinsuredNatureSchemaTow.setInsuredFlag("9");
                prpTinsuredNatureSchemaTow.setSex(getSexCode((String)bnfList.get(4)));
                prpTinsuredNatureSchemaTow.setAge("");
                prpTinsuredNatureSchemaTow.setBirthday("");
                prpTinsuredNatureSchemaTow.setUnit("");
                prpTinsuredNatureSchemaTow.setUnitPostCode("");
                prpTinsuredNatureSchemaTow.setUnitType("");
                prpTinsuredNatureSchemaTow.setLocalPoliceStation("");
                prpTinsuredNatureSchemaTow.setRoomAddress("");
                prpTinsuredNatureSchemaTow.setRoomPostCode("");
                prpTinsuredNatureSchemaTow.setRoomPhone("");
                prpTinsuredNatureSchemaTow.setMobile("");
                prpTinsuredNatureSchemaTow.setMarriage("");
                prpTinsuredNatureSchemaTow.setWeight("");
                prpTinsuredNatureSchemaTow.setStature("");
                blPrpTinsuredNatureSubTow.setArr(prpTinsuredNatureSchemaTow);
                blSubTowProposal.setBLPrpTinsuredNature(blPrpTinsuredNatureSubTow);
                blPrpTinsuredSubTow.setArr(prpTinsuredSchemaSubTow);
                blSubTowProposal.setBLPrpTinsured(blPrpTinsuredSubTow);
            }
        }else if("4".equals(insuredFlag)){
            for(int i=0;i<tmlInsuredList.size();i++){
                PrpTinsuredSchema       prpTinsuredSchema       = new PrpTinsuredSchema();
                PrpTinsuredSchema       prpTinsuredSchemaSubOne       = new PrpTinsuredSchema();
                PrpTinsuredSchema       prpTinsuredSchemaSubTow       = new PrpTinsuredSchema();
                PrpTinsuredNatureSchema prpTinsuredNatureSchema = new PrpTinsuredNatureSchema();
                PrpTinsuredNatureSchema prpTinsuredNatureSchemaOne = new PrpTinsuredNatureSchema();
                PrpTinsuredNatureSchema prpTinsuredNatureSchemaTow = new PrpTinsuredNatureSchema();
                BLPrpTinsuredNature           blPrpTinsuredNature           = blMainProposal.getBLPrpTinsuredNature();
                BLPrpTinsuredNature           blPrpTinsuredNatureSubOne           = blSubOneProposal.getBLPrpTinsuredNature();
                BLPrpTinsuredNature           blPrpTinsuredNatureSubTow           = blSubTowProposal.getBLPrpTinsuredNature();
                BLPrpTinsured           blPrpTinsured           =  blMainProposal.getBLPrpTinsured();
                BLPrpTinsured           blPrpTinsuredSubOne           = blSubOneProposal.getBLPrpTinsured();
                BLPrpTinsured           blPrpTinsuredSubTow           = blSubTowProposal.getBLPrpTinsured();
                ArrayList bnfList = new ArrayList();
                
                bnfList=(ArrayList)tmlInsuredList.get(i);
                prpTinsuredSchema.setProposalNo(MainPolicyNo);
                prpTinsuredSchema.setRiskCode("3004");
                prpTinsuredSchema.setSerialNo((blMainProposal.getBLPrpTinsured().getSize()+1)+"");
                prpTinsuredSchema.setLanguage("C");
                prpTinsuredSchema.setInsuredType("1");
                prpTinsuredSchema.setInsuredName((String)bnfList.get(1));
                prpTinsuredSchema.setInsuredNature("3");
                prpTinsuredSchema.setInsuredFlag("1");
                prpTinsuredSchema.setInsuredIdentity(getCardInsuredIdentity((String)bnfList.get(4)));
                prpTinsuredSchema.setRelateSerialNo("");
                prpTinsuredSchema.setIdentifyType("01");
                prpTinsuredSchema.setIdentifyNumber((String)bnfList.get(2));
                prpTinsuredSchema.setBenefitRate("");
                prpTinsuredSchema.setBenefitFlag("N");
                prpTinsuredSchema.setBenefitRate("0.00");
                prpTinsuredSchema.setPossessNature("1");
                
                  
                
                

                prpTinsuredSchema.setInsuredCode(blPrpDcustomerIdvNew.createCustomer(prpTinsuredSchema,comCode,operCode,operCode));
                
                
                
                prpTinsuredNatureSchema.setProposalNo(MainPolicyNo);
                prpTinsuredNatureSchema.setSerialNo((blMainProposal.getBLPrpTinsuredNature().getSize() + 1)+ "");
                prpTinsuredNatureSchema.setInsuredFlag("1");
                prpTinsuredNatureSchema.setAge((String)bnfList.get(5));
                blPrpTinsuredNature.setArr(prpTinsuredNatureSchema);
                blMainProposal.setBLPrpTinsuredNature(blPrpTinsuredNature);
                blPrpTinsured.setArr(prpTinsuredSchema);
                blMainProposal.setBLPrpTinsured(blPrpTinsured);
                
                
                
                prpTinsuredSchemaSubOne.setProposalNo(SubOnePolicyNo);
                prpTinsuredSchemaSubOne.setRiskCode("2700");
                prpTinsuredSchemaSubOne.setSerialNo((blSubOneProposal.getBLPrpTinsured().getSize()+1)+"");
                prpTinsuredSchemaSubOne.setLanguage("C");
                prpTinsuredSchemaSubOne.setInsuredType("1");
                prpTinsuredSchemaSubOne.setInsuredName((String)bnfList.get(1));
                prpTinsuredSchemaSubOne.setInsuredNature("3");
                prpTinsuredSchemaSubOne.setInsuredFlag("1");
                prpTinsuredSchemaSubOne.setInsuredIdentity(getCardInsuredIdentity((String)bnfList.get(4)));
                prpTinsuredSchemaSubOne.setRelateSerialNo("");
                prpTinsuredSchemaSubOne.setIdentifyType("01");
                prpTinsuredSchemaSubOne.setIdentifyNumber((String)bnfList.get(2));
                prpTinsuredSchemaSubOne.setBenefitRate("");
                prpTinsuredSchemaSubOne.setBenefitFlag("N");
                prpTinsuredSchemaSubOne.setBenefitRate("0.00");
                
                  
                
                    prpTinsuredSchemaSubOne.setInsuredCode(prpTinsuredSchema.getInsuredCode());
                
                prpTinsuredNatureSchemaOne.setProposalNo(SubOnePolicyNo);
                prpTinsuredNatureSchemaOne.setSerialNo((blSubOneProposal.getBLPrpTinsuredNature().getSize() + 1)+ "");
                prpTinsuredNatureSchemaOne.setInsuredFlag("1");
                prpTinsuredNatureSchemaOne.setAge((String)bnfList.get(5));
                blPrpTinsuredNatureSubOne.setArr(prpTinsuredNatureSchemaOne);
                blSubOneProposal.setBLPrpTinsuredNature(blPrpTinsuredNatureSubOne);
                blPrpTinsuredSubOne.setArr(prpTinsuredSchemaSubOne);
                blSubOneProposal.setBLPrpTinsured(blPrpTinsuredSubOne);
                
                
                
                
                prpTinsuredSchemaSubTow.setProposalNo(SubTowPolicyNo);
                prpTinsuredSchemaSubTow.setRiskCode("0301");
                prpTinsuredSchemaSubTow.setSerialNo((blSubTowProposal.getBLPrpTinsured().getSize()+1)+"");
                prpTinsuredSchemaSubTow.setLanguage("C");
                prpTinsuredSchemaSubTow.setInsuredType("1");
                
                prpTinsuredSchemaSubTow.setInsuredName((String)bnfList.get(1));
                prpTinsuredSchemaSubTow.setInsuredNature("3");
                prpTinsuredSchemaSubTow.setInsuredFlag("1");
                prpTinsuredSchemaSubTow.setInsuredIdentity(getCardInsuredIdentity((String)bnfList.get(4)));
                prpTinsuredSchemaSubTow.setRelateSerialNo("");
                prpTinsuredSchemaSubTow.setIdentifyType("01");
                prpTinsuredSchemaSubTow.setIdentifyNumber((String)bnfList.get(2));
                prpTinsuredSchemaSubTow.setBenefitRate("");
                prpTinsuredSchemaSubTow.setBenefitFlag("N");
                prpTinsuredSchemaSubTow.setBenefitRate("0.00");
                
                  
                
                    prpTinsuredSchemaSubTow.setInsuredCode(prpTinsuredSchema.getInsuredCode());
                
                prpTinsuredNatureSchemaTow.setProposalNo(SubTowPolicyNo);
                prpTinsuredNatureSchemaTow.setSerialNo((blSubTowProposal.getBLPrpTinsuredNature().getSize() + 1 )+ "");
                prpTinsuredNatureSchemaTow.setInsuredFlag("1");
                prpTinsuredNatureSchemaTow.setAge((String)bnfList.get(5));
                blPrpTinsuredNatureSubTow.setArr(prpTinsuredNatureSchemaTow);
                blSubTowProposal.setBLPrpTinsuredNature(blPrpTinsuredNatureSubTow);
                blPrpTinsuredSubTow.setArr(prpTinsuredSchemaSubTow);
                blSubTowProposal.setBLPrpTinsured(blPrpTinsuredSubTow);
            }
        }
        
    }
    
    public void transTengageSub(BLProposal blMainProposal,BLProposal blSubOneProposal,BLProposal blSubTowProposal) throws Exception{
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
            
            PrpTengageSchemaTow.setRiskCode("0301");
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
 
    public void transTengageMain(BLProposal blMainProposal,ArrayList TMCardInfoList,String grade,String payType) throws Exception{
        String tengagetype=getTenageType(TMCardInfoList,grade);
        String name1="";
        String name2="";
        String age1="";
        String age2="";
        String relation1="";
        String relation2="";
        String id1="";
        String id2="";
        String appname="";
        
        if(TMCardInfoList.size()>0){
            for(int i=0;i<TMCardInfoList.size();i++){
                ArrayList tempCardInfo=(ArrayList)TMCardInfoList.get(i);
                appname=(String)tempCardInfo.get(8);
                if(i==0){
                    name1=(String)tempCardInfo.get(1);
                    age1=(String)tempCardInfo.get(5);
                    relation1=(String)tempCardInfo.get(4);
                    id1=(String)(String)tempCardInfo.get(2);
                }else{
                    name2=(String)tempCardInfo.get(1);
                    age2=(String)tempCardInfo.get(5);
                    relation2=(String)tempCardInfo.get(4);
                    id2=(String)(String)tempCardInfo.get(2);
                }
            }
        }
        PrpTengageSchema prpTengageSchema = new PrpTengageSchema();
        prpTengageSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTengageSchema.setRiskCode("3004");
        prpTengageSchema.setSerialNo("1");
        prpTengageSchema.setLineNo("1");
        prpTengageSchema.setClauseCode("T9999");
        prpTengageSchema.setClauses("其他");
        prpTengageSchema.setTitleFlag("0");
        prpTengageSchema.setFlag(" 0");
        blMainProposal.getBLPrpTengage().setArr(prpTengageSchema);
        
        PrpTengageSchema prpTengageSchema1 = new PrpTengageSchema();
        prpTengageSchema1.setProposalNo(blMainProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema1.setRiskCode("3004");
        prpTengageSchema1.setSerialNo("1");
        prpTengageSchema1.setLineNo("2");
        prpTengageSchema1.setClauseCode("T9999");
        prpTengageSchema1.setClauses("XX人要求解除本XX合同的，XX人根据约定按下列公式计算应退还的未满期净XX费。");
        prpTengageSchema1.setTitleFlag("1");
        prpTengageSchema1.setFlag(" 1");
        blMainProposal.getBLPrpTengage().setArr(prpTengageSchema1);
        
        PrpTengageSchema prpTengageSchema2 = new PrpTengageSchema();
        prpTengageSchema2.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTengageSchema2.setRiskCode("3004");
        prpTengageSchema2.setSerialNo("1");
        prpTengageSchema2.setLineNo("3");
        prpTengageSchema2.setClauseCode("T9999");
        prpTengageSchema2.setClauses("未满期净XX费=XX费×[1-（XX已经过天数/XX期间天数）]×（1-25%）");
        prpTengageSchema2.setTitleFlag("1");
        prpTengageSchema2.setFlag(" 1");
        blMainProposal.getBLPrpTengage().setArr(prpTengageSchema2);
        
        PrpTengageSchema prpTengageSchema3 = new PrpTengageSchema();
        prpTengageSchema3.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTengageSchema3.setRiskCode("3004");
        prpTengageSchema3.setSerialNo("2");
        prpTengageSchema3.setLineNo("1");
        prpTengageSchema3.setClauseCode("T9999");
        prpTengageSchema3.setClauses("其他");
        prpTengageSchema3.setTitleFlag("0");
        prpTengageSchema3.setFlag(" 0");
        blMainProposal.getBLPrpTengage().setArr(prpTengageSchema3);
        
        PrpTengageSchema prpTengageSchema4 = new PrpTengageSchema();
        prpTengageSchema4.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTengageSchema4.setRiskCode("3004");
        prpTengageSchema4.setSerialNo("2");
        prpTengageSchema4.setLineNo("2");
        prpTengageSchema4.setClauseCode("T9999");
        prpTengageSchema4.setClauses("此XX意外伤害XXXXX障配偶和子女作为连带被XX人。如被XX人有多名子女，则每名子女各");
        prpTengageSchema4.setTitleFlag("1");
        prpTengageSchema4.setFlag(" 1");
        blMainProposal.getBLPrpTengage().setArr(prpTengageSchema4);
        
        PrpTengageSchema prpTengageSchema5 = new PrpTengageSchema();
        prpTengageSchema5.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTengageSchema5.setRiskCode("3004");
        prpTengageSchema5.setSerialNo("2");
        prpTengageSchema5.setLineNo("3");
        prpTengageSchema5.setClauseCode("T9999");
        prpTengageSchema5.setClauses("项意外伤害XX金额等于XX所载的相应的各项子女XX金额除以子女人数。（即每名子女");
        prpTengageSchema5.setTitleFlag("1");
        prpTengageSchema5.setFlag(" 1");
        blMainProposal.getBLPrpTengage().setArr(prpTengageSchema5);
        
        
        
        PrpTengageSchema prpTengageSchema6 = new PrpTengageSchema();
        prpTengageSchema6.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTengageSchema6.setRiskCode("3004");
        prpTengageSchema6.setSerialNo("2");
        prpTengageSchema6.setLineNo("4");
        prpTengageSchema6.setClauseCode("T9999");
        prpTengageSchema6.setClauses("意外伤害XX金额为5万元/n，飞机、火车和轮船意外伤害XX金额分别为5万元/n，意外伤");
        prpTengageSchema6.setTitleFlag("1");
        prpTengageSchema6.setFlag(" 1");
        blMainProposal.getBLPrpTengage().setArr(prpTengageSchema6);
        
        PrpTengageSchema prpTengageSchema7 = new PrpTengageSchema();
        prpTengageSchema7.setProposalNo(blMainProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema7.setRiskCode("3004");
        prpTengageSchema7.setSerialNo("2");
        prpTengageSchema7.setLineNo("5");
        prpTengageSchema7.setClauseCode("T9999");
        prpTengageSchema7.setClauses("害医疗XX金为0.5万元/n，其中n为子女人数。）");
        prpTengageSchema7.setTitleFlag("1");
        prpTengageSchema7.setFlag(" 1");
        blMainProposal.getBLPrpTengage().setArr(prpTengageSchema7);
        
        PrpTengageSchema prpTengageSchema8 = new PrpTengageSchema();
        prpTengageSchema8.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTengageSchema8.setRiskCode("3004");
        prpTengageSchema8.setSerialNo("3");
        prpTengageSchema8.setLineNo("1");
        prpTengageSchema8.setClauseCode("T9999");
        prpTengageSchema8.setClauses("其他");
        prpTengageSchema8.setTitleFlag("0");
        prpTengageSchema8.setFlag(" 0");
        blMainProposal.getBLPrpTengage().setArr(prpTengageSchema8);
        
        PrpTengageSchema prpTengageSchema9 = new PrpTengageSchema();
        prpTengageSchema9.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTengageSchema9.setRiskCode("3004");
        prpTengageSchema9.setSerialNo("3");
        prpTengageSchema9.setLineNo("2");
        prpTengageSchema9.setClauseCode("T9999");
        prpTengageSchema9.setClauses("a");
        prpTengageSchema9.setTitleFlag("1");
        prpTengageSchema9.setFlag(" 1");
        blMainProposal.getBLPrpTengage().setArr(prpTengageSchema9);
        
        PrpTengageSchema prpTengageSchema10 = new PrpTengageSchema();
        prpTengageSchema10.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTengageSchema10.setRiskCode("3004");
        prpTengageSchema10.setSerialNo("3");
        prpTengageSchema10.setLineNo("3");
        prpTengageSchema10.setClauseCode("T9999");
        prpTengageSchema10.setClauses("a");
        prpTengageSchema10.setTitleFlag("1");
        prpTengageSchema10.setFlag(" 1");
        blMainProposal.getBLPrpTengage().setArr(prpTengageSchema10);
        
        PrpTengageSchema prpTengageSchema11 = new PrpTengageSchema();
        prpTengageSchema11.setProposalNo(blMainProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema11.setRiskCode("3004");
        prpTengageSchema11.setSerialNo("3");
        prpTengageSchema11.setLineNo("4");
        prpTengageSchema11.setClauseCode("T9999");
        prpTengageSchema11.setClauses("”。");
        prpTengageSchema11.setTitleFlag("1");
        prpTengageSchema11.setFlag(" 1");
        blMainProposal.getBLPrpTengage().setArr(prpTengageSchema11);
        
        if(tengagetype.equals("1")){
            PrpTengageSchema prpTengageSchema12 = new PrpTengageSchema();
            prpTengageSchema12.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema12.setRiskCode("3004");
            prpTengageSchema12.setSerialNo("4");
            prpTengageSchema12.setLineNo("1");
            prpTengageSchema12.setClauseCode("T9999");
            prpTengageSchema12.setClauses("其他");
            prpTengageSchema12.setTitleFlag("0");
            prpTengageSchema12.setFlag(" 0");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema12);
            
            PrpTengageSchema prpTengageSchema13 = new PrpTengageSchema();
            prpTengageSchema13.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema13.setRiskCode("3004");
            prpTengageSchema13.setSerialNo("4");
            prpTengageSchema13.setLineNo("2");
            prpTengageSchema13.setClauseCode("T9999");
            prpTengageSchema13.setClauses("f");
            prpTengageSchema13.setTitleFlag("1");
            prpTengageSchema13.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema13);
            
            PrpTengageSchema prpTengageSchema17 = new PrpTengageSchema();
            prpTengageSchema17.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema17.setRiskCode("3004");
            prpTengageSchema17.setSerialNo("4");
            prpTengageSchema17.setLineNo("3");
            prpTengageSchema17.setClauseCode("T9999");
            prpTengageSchema17.setClauses("被XX人"+name1+"，年龄"+age1+"岁，与XX人"+appname+"关系"+relation1+",XX1万元；");
            prpTengageSchema17.setTitleFlag("1");
            prpTengageSchema17.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema17);
            
            PrpTengageSchema prpTengageSchema14 = new PrpTengageSchema();
            prpTengageSchema14.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema14.setRiskCode("3004");
            prpTengageSchema14.setSerialNo("4");
            prpTengageSchema14.setLineNo("4");
            prpTengageSchema14.setClauseCode("T9999");
            prpTengageSchema14.setClauses("被XX人"+name2+"，年龄"+age2+"岁，与XX人"+appname+"关系"+relation2+" ，XX1万元；");
            prpTengageSchema14.setTitleFlag("1");
            prpTengageSchema14.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema14);
            
            PrpTengageSchema prpTengageSchema15 = new PrpTengageSchema();
            prpTengageSchema15.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema15.setRiskCode("3004");
            prpTengageSchema15.setSerialNo("4");
            prpTengageSchema15.setLineNo("5");
            prpTengageSchema15.setClauseCode("T9999");
            prpTengageSchema15.setClauses("a");
            prpTengageSchema15.setTitleFlag("1");
            prpTengageSchema15.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema15);
            
            PrpTengageSchema prpTengageSchema16 = new PrpTengageSchema();
            prpTengageSchema16.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema16.setRiskCode("3004");
            prpTengageSchema16.setSerialNo("4");
            prpTengageSchema16.setLineNo("6");
            prpTengageSchema16.setClauseCode("T9999");
            prpTengageSchema16.setClauses("f");
            prpTengageSchema16.setTitleFlag("1");
            prpTengageSchema16.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema16);
        }else if(tengagetype.equals("2")){
            PrpTengageSchema prpTengageSchema12 = new PrpTengageSchema();
            prpTengageSchema12.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema12.setRiskCode("3004");
            prpTengageSchema12.setSerialNo("4");
            prpTengageSchema12.setLineNo("1");
            prpTengageSchema12.setClauseCode("T9999");
            prpTengageSchema12.setClauses("其他");
            prpTengageSchema12.setTitleFlag("0");
            prpTengageSchema12.setFlag(" 0");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema12);
            
            PrpTengageSchema prpTengageSchema13 = new PrpTengageSchema();
            prpTengageSchema13.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema13.setRiskCode("3004");
            prpTengageSchema13.setSerialNo("4");
            prpTengageSchema13.setLineNo("2");
            prpTengageSchema13.setClauseCode("T9999");
            prpTengageSchema13.setClauses("3");
            prpTengageSchema13.setTitleFlag("1");
            prpTengageSchema13.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema13);
            
            PrpTengageSchema prpTengageSchema17 = new PrpTengageSchema();
            prpTengageSchema17.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema17.setRiskCode("3004");
            prpTengageSchema17.setSerialNo("4");
            prpTengageSchema17.setLineNo("3");
            prpTengageSchema17.setClauseCode("T9999");
            prpTengageSchema17.setClauses("1"+name1+"，年龄"+age1+"岁，与XX人"+appname+"关系"+relation1+",XX2万元；");
            prpTengageSchema17.setTitleFlag("1");
            prpTengageSchema17.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema17);
            
            
            
            PrpTengageSchema prpTengageSchema15 = new PrpTengageSchema();
            prpTengageSchema15.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema15.setRiskCode("3004");
            prpTengageSchema15.setSerialNo("4");
            prpTengageSchema15.setLineNo("4");
            prpTengageSchema15.setClauseCode("T9999");
            prpTengageSchema15.setClauses("具体XX责任详见“阳光关爱老人骨折医疗XX条款”，");
            prpTengageSchema15.setTitleFlag("1");
            prpTengageSchema15.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema15);
            
            PrpTengageSchema prpTengageSchema16 = new PrpTengageSchema();
            prpTengageSchema16.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema16.setRiskCode("3004");
            prpTengageSchema16.setSerialNo("4");
            prpTengageSchema16.setLineNo("5");
            prpTengageSchema16.setClauseCode("T9999");
            prpTengageSchema16.setClauses("XXXXX时必须提供老益壮被XX人和XX人身份及关系证明。");
            prpTengageSchema16.setTitleFlag("1");
            prpTengageSchema16.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema16);
        }else if(tengagetype.equals("3")){
            PrpTengageSchema prpTengageSchema12 = new PrpTengageSchema();
            prpTengageSchema12.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema12.setRiskCode("3004");
            prpTengageSchema12.setSerialNo("4");
            prpTengageSchema12.setLineNo("1");
            prpTengageSchema12.setClauseCode("T9999");
            prpTengageSchema12.setClauses("其他");
            prpTengageSchema12.setTitleFlag("0");
            prpTengageSchema12.setFlag(" 0");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema12);
            
            PrpTengageSchema prpTengageSchema13 = new PrpTengageSchema();
            prpTengageSchema13.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema13.setRiskCode("3004");
            prpTengageSchema13.setSerialNo("4");
            prpTengageSchema13.setLineNo("2");
            prpTengageSchema13.setClauseCode("T9999");
            prpTengageSchema13.setClauses("1");
            prpTengageSchema13.setTitleFlag("1");
            prpTengageSchema13.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema13);
            
            PrpTengageSchema prpTengageSchema17 = new PrpTengageSchema();
            prpTengageSchema17.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema17.setRiskCode("3004");
            prpTengageSchema17.setSerialNo("4");
            prpTengageSchema17.setLineNo("3");
            prpTengageSchema17.setClauseCode("T9999");
            prpTengageSchema17.setClauses("2"+name1+"，年龄"+age1+"岁，与XX人"+appname+"关系"+relation1+",XX1万元；");
            prpTengageSchema17.setTitleFlag("1");
            prpTengageSchema17.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema17);
            
            
            
            PrpTengageSchema prpTengageSchema15 = new PrpTengageSchema();
            prpTengageSchema15.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema15.setRiskCode("3004");
            prpTengageSchema15.setSerialNo("4");
            prpTengageSchema15.setLineNo("4");
            prpTengageSchema15.setClauseCode("T9999");
            prpTengageSchema15.setClauses("3");
            prpTengageSchema15.setTitleFlag("1");
            prpTengageSchema15.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema15);
            
            PrpTengageSchema prpTengageSchema16 = new PrpTengageSchema();
            prpTengageSchema16.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema16.setRiskCode("3004");
            prpTengageSchema16.setSerialNo("4");
            prpTengageSchema16.setLineNo("5");
            prpTengageSchema16.setClauseCode("T9999");
            prpTengageSchema16.setClauses("1");
            prpTengageSchema16.setTitleFlag("1");
            prpTengageSchema16.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema16);
        }else if(tengagetype.equals("4")){
            PrpTengageSchema prpTengageSchema12 = new PrpTengageSchema();
            prpTengageSchema12.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema12.setRiskCode("3004");
            prpTengageSchema12.setSerialNo("4");
            prpTengageSchema12.setLineNo("1");
            prpTengageSchema12.setClauseCode("T9999");
            prpTengageSchema12.setClauses("其他");
            prpTengageSchema12.setTitleFlag("0");
            prpTengageSchema12.setFlag(" 0");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema12);
            
            PrpTengageSchema prpTengageSchema13 = new PrpTengageSchema();
            prpTengageSchema13.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema13.setRiskCode("3004");
            prpTengageSchema13.setSerialNo("4");
            prpTengageSchema13.setLineNo("2");
            prpTengageSchema13.setClauseCode("T9999");
            prpTengageSchema13.setClauses("2");
            prpTengageSchema13.setTitleFlag("1");
            prpTengageSchema13.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema13);
            
            PrpTengageSchema prpTengageSchema17 = new PrpTengageSchema();
            prpTengageSchema17.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema17.setRiskCode("3004");
            prpTengageSchema17.setSerialNo("4");
            prpTengageSchema17.setLineNo("3");
            prpTengageSchema17.setClauseCode("T9999");
            prpTengageSchema17.setClauses("被XX人"+name1+"，证件号码"+id1+"，XX1万元；被XX人"+name2+"，证件号码"+id2+"，XX1万元");
            prpTengageSchema17.setTitleFlag("1");
            prpTengageSchema17.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema17);
            
            
            PrpTengageSchema prpTengageSchema15 = new PrpTengageSchema();
            prpTengageSchema15.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema15.setRiskCode("3004");
            prpTengageSchema15.setSerialNo("4");
            prpTengageSchema15.setLineNo("4");
            prpTengageSchema15.setClauseCode("T9999");
            prpTengageSchema15.setClauses("1");
            prpTengageSchema15.setTitleFlag("1");
            prpTengageSchema15.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema15);
            
            
        }else if(tengagetype.equals("5")){
            PrpTengageSchema prpTengageSchema12 = new PrpTengageSchema();
            prpTengageSchema12.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema12.setRiskCode("3004");
            prpTengageSchema12.setSerialNo("4");
            prpTengageSchema12.setLineNo("1");
            prpTengageSchema12.setClauseCode("T9999");
            prpTengageSchema12.setClauses("其他");
            prpTengageSchema12.setTitleFlag("0");
            prpTengageSchema12.setFlag(" 0");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema12);
            
            PrpTengageSchema prpTengageSchema13 = new PrpTengageSchema();
            prpTengageSchema13.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema13.setRiskCode("3004");
            prpTengageSchema13.setSerialNo("4");
            prpTengageSchema13.setLineNo("2");
            prpTengageSchema13.setClauseCode("T9999");
            prpTengageSchema13.setClauses("w");
            prpTengageSchema13.setTitleFlag("1");
            prpTengageSchema13.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema13);
            
            PrpTengageSchema prpTengageSchema17 = new PrpTengageSchema();
            prpTengageSchema17.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema17.setRiskCode("3004");
            prpTengageSchema17.setSerialNo("4");
            prpTengageSchema17.setLineNo("3");
            prpTengageSchema17.setClauseCode("T9999");
            prpTengageSchema17.setClauses("被XX人"+name1+"，证件号码"+id1+"，XX2万元；");
            prpTengageSchema17.setTitleFlag("1");
            prpTengageSchema17.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema17);
            
            
            PrpTengageSchema prpTengageSchema15 = new PrpTengageSchema();
            prpTengageSchema15.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema15.setRiskCode("3004");
            prpTengageSchema15.setSerialNo("4");
            prpTengageSchema15.setLineNo("4");
            prpTengageSchema15.setClauseCode("T9999");
            prpTengageSchema15.setClauses("f");
            prpTengageSchema15.setTitleFlag("1");
            prpTengageSchema15.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema15);
            
            
        }else if(tengagetype.equals("6")){
            PrpTengageSchema prpTengageSchema12 = new PrpTengageSchema();
            prpTengageSchema12.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema12.setRiskCode("3004");
            prpTengageSchema12.setSerialNo("4");
            prpTengageSchema12.setLineNo("1");
            prpTengageSchema12.setClauseCode("T9999");
            prpTengageSchema12.setClauses("其他");
            prpTengageSchema12.setTitleFlag("0");
            prpTengageSchema12.setFlag(" 0");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema12);
            
            PrpTengageSchema prpTengageSchema13 = new PrpTengageSchema();
            prpTengageSchema13.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema13.setRiskCode("3004");
            prpTengageSchema13.setSerialNo("4");
            prpTengageSchema13.setLineNo("2");
            prpTengageSchema13.setClauseCode("T9999");
            prpTengageSchema13.setClauses("f");
            prpTengageSchema13.setTitleFlag("1");
            prpTengageSchema13.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema13);
            
            PrpTengageSchema prpTengageSchema17 = new PrpTengageSchema();
            prpTengageSchema17.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema17.setRiskCode("3004");
            prpTengageSchema17.setSerialNo("4");
            prpTengageSchema17.setLineNo("3");
            prpTengageSchema17.setClauseCode("T9999");
            prpTengageSchema17.setClauses("被XX人"+name1+"，证件号码"+id1+"，XX1万元；");
            prpTengageSchema17.setTitleFlag("1");
            prpTengageSchema17.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema17);
            
            
            PrpTengageSchema prpTengageSchema15 = new PrpTengageSchema();
            prpTengageSchema15.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema15.setRiskCode("3004");
            prpTengageSchema15.setSerialNo("4");
            prpTengageSchema15.setLineNo("4");
            prpTengageSchema15.setClauseCode("T9999");
            prpTengageSchema15.setClauses("s");
            prpTengageSchema15.setTitleFlag("1");
            prpTengageSchema15.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema15);
            
            
        }else if(tengagetype.equals("7")){
            PrpTengageSchema prpTengageSchema12 = new PrpTengageSchema();
            prpTengageSchema12.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema12.setRiskCode("3004");
            prpTengageSchema12.setSerialNo("4");
            prpTengageSchema12.setLineNo("1");
            prpTengageSchema12.setClauseCode("T9999");
            prpTengageSchema12.setClauses("其他");
            prpTengageSchema12.setTitleFlag("0");
            prpTengageSchema12.setFlag(" 0");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema12);
            
            PrpTengageSchema prpTengageSchema13 = new PrpTengageSchema();
            prpTengageSchema13.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema13.setRiskCode("3004");
            prpTengageSchema13.setSerialNo("4");
            prpTengageSchema13.setLineNo("2");
            prpTengageSchema13.setClauseCode("T9999");
            prpTengageSchema13.setClauses("经与XX人在XX时协商确认，本XX不承担老益壮XX责任。");
            prpTengageSchema13.setTitleFlag("1");
            prpTengageSchema13.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema13);
        }else if(tengagetype.equals("8")){
            PrpTengageSchema prpTengageSchema12 = new PrpTengageSchema();
            prpTengageSchema12.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema12.setRiskCode("3004");
            prpTengageSchema12.setSerialNo("4");
            prpTengageSchema12.setLineNo("1");
            prpTengageSchema12.setClauseCode("T9999");
            prpTengageSchema12.setClauses("其他");
            prpTengageSchema12.setTitleFlag("0");
            prpTengageSchema12.setFlag(" 0");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema12);
            
            PrpTengageSchema prpTengageSchema13 = new PrpTengageSchema();
            prpTengageSchema13.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema13.setRiskCode("3004");
            prpTengageSchema13.setSerialNo("4");
            prpTengageSchema13.setLineNo("2");
            prpTengageSchema13.setClauseCode("T9999");
            prpTengageSchema13.setClauses("a");
            prpTengageSchema13.setTitleFlag("1");
            prpTengageSchema13.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema13);
            
            PrpTengageSchema prpTengageSchema17 = new PrpTengageSchema();
            prpTengageSchema17.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema17.setRiskCode("3004");
            prpTengageSchema17.setSerialNo("4");
            prpTengageSchema17.setLineNo("3");
            prpTengageSchema17.setClauseCode("T9999");
            prpTengageSchema17.setClauses("被XX人"+name1+"，证件号码"+id1+"，XX1万元；");
            prpTengageSchema17.setTitleFlag("1");
            prpTengageSchema17.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema17);
            
            
            PrpTengageSchema prpTengageSchema14 = new PrpTengageSchema();
            prpTengageSchema14.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema14.setRiskCode("3004");
            prpTengageSchema14.setSerialNo("4");
            prpTengageSchema14.setLineNo("4");
            prpTengageSchema14.setClauseCode("T9999");
            prpTengageSchema14.setClauses("被XX人"+name2+"，年龄"+age2+"岁，与XX人"+appname+"关系"+relation2+",XX1万元；");
            prpTengageSchema14.setTitleFlag("1");
            prpTengageSchema14.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema14);
            
            PrpTengageSchema prpTengageSchema15 = new PrpTengageSchema();
            prpTengageSchema15.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema15.setRiskCode("3004");
            prpTengageSchema15.setSerialNo("4");
            prpTengageSchema15.setLineNo("5");
            prpTengageSchema15.setClauseCode("T9999");
            prpTengageSchema15.setClauses("a");
            prpTengageSchema15.setTitleFlag("1");
            prpTengageSchema15.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema15);
            
            PrpTengageSchema prpTengageSchema16 = new PrpTengageSchema();
            prpTengageSchema16.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema16.setRiskCode("3004");
            prpTengageSchema16.setSerialNo("4");
            prpTengageSchema16.setLineNo("6");
            prpTengageSchema16.setClauseCode("T9999");
            prpTengageSchema16.setClauses("XXXXX时必须提供老益壮被XX人和XX人身份及关系证明。");
            prpTengageSchema16.setTitleFlag("1");
            prpTengageSchema16.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema16);
        }else if(tengagetype.equals("9")){
            PrpTengageSchema prpTengageSchema12 = new PrpTengageSchema();
            prpTengageSchema12.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema12.setRiskCode("3004");
            prpTengageSchema12.setSerialNo("4");
            prpTengageSchema12.setLineNo("1");
            prpTengageSchema12.setClauseCode("T9999");
            prpTengageSchema12.setClauses("其他");
            prpTengageSchema12.setTitleFlag("0");
            prpTengageSchema12.setFlag(" 0");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema12);
            
            PrpTengageSchema prpTengageSchema13 = new PrpTengageSchema();
            prpTengageSchema13.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema13.setRiskCode("3004");
            prpTengageSchema13.setSerialNo("4");
            prpTengageSchema13.setLineNo("2");
            prpTengageSchema13.setClauseCode("T9999");
            prpTengageSchema13.setClauses("a");
            prpTengageSchema13.setTitleFlag("1");
            prpTengageSchema13.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema13);
            
            PrpTengageSchema prpTengageSchema14 = new PrpTengageSchema();
            prpTengageSchema14.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema14.setRiskCode("3004");
            prpTengageSchema14.setSerialNo("4");
            prpTengageSchema14.setLineNo("3");
            prpTengageSchema14.setClauseCode("T9999");
            prpTengageSchema14.setClauses("被XX人"+name1+"，年龄"+age1+"岁，与XX人"+appname+"关系"+relation1+",XX1万元；");
            prpTengageSchema14.setTitleFlag("1");
            prpTengageSchema14.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema14);
            
            PrpTengageSchema prpTengageSchema17 = new PrpTengageSchema();
            prpTengageSchema17.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema17.setRiskCode("3004");
            prpTengageSchema17.setSerialNo("4");
            prpTengageSchema17.setLineNo("4");
            prpTengageSchema17.setClauseCode("T9999");
            prpTengageSchema17.setClauses("被XX人"+name2+"，证件号码"+id2+"，XX1万元；");
            prpTengageSchema17.setTitleFlag("1");
            prpTengageSchema17.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema17);
            
            
            
            
            PrpTengageSchema prpTengageSchema15 = new PrpTengageSchema();
            prpTengageSchema15.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema15.setRiskCode("3004");
            prpTengageSchema15.setSerialNo("4");
            prpTengageSchema15.setLineNo("5");
            prpTengageSchema15.setClauseCode("T9999");
            prpTengageSchema15.setClauses("a”。");
            prpTengageSchema15.setTitleFlag("1");
            prpTengageSchema15.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema15);
            
            PrpTengageSchema prpTengageSchema16 = new PrpTengageSchema();
            prpTengageSchema16.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema16.setRiskCode("3004");
            prpTengageSchema16.setSerialNo("4");
            prpTengageSchema16.setLineNo("6");
            prpTengageSchema16.setClauseCode("T9999");
            prpTengageSchema16.setClauses("b");
            prpTengageSchema16.setTitleFlag("1");
            prpTengageSchema16.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema16);
        }
        
        
        if(payType.equals("3")){
            
            PrpTengageSchema prpTengageSchema17 = new PrpTengageSchema();
            prpTengageSchema17.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema17.setRiskCode("3004");
            prpTengageSchema17.setSerialNo("5");
            prpTengageSchema17.setLineNo("1");
            prpTengageSchema17.setClauseCode("T9999");
            prpTengageSchema17.setClauses("其他");
            prpTengageSchema17.setTitleFlag("0");
            prpTengageSchema17.setFlag(" 0");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema17);
            
            PrpTengageSchema prpTengageSchema18 = new PrpTengageSchema();
            prpTengageSchema18.setProposalNo(blMainProposal.getBLPrpTengage().getArr(0).getProposalNo());
            prpTengageSchema18.setRiskCode("3004");
            prpTengageSchema18.setSerialNo("5");
            prpTengageSchema18.setLineNo("2");
            prpTengageSchema18.setClauseCode("T9999");
            prpTengageSchema18.setClauses("本XX合同采取季缴的方式交付XX，XX人应按本XX单交费计划表中载明的日期逐");
            prpTengageSchema18.setTitleFlag("1");
            prpTengageSchema18.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema18);
            
            PrpTengageSchema prpTengageSchema19 = new PrpTengageSchema();
            prpTengageSchema19.setProposalNo(blMainProposal.getBLPrpTengage().getArr(0).getProposalNo());
            prpTengageSchema19.setRiskCode("3004");
            prpTengageSchema19.setSerialNo("5");
            prpTengageSchema19.setLineNo("3");
            prpTengageSchema19.setClauseCode("T9999");
            prpTengageSchema19.setClauses("季缴付XX费，XX人承担对应XX责任。XX人如到期未交纳XX费，");
            prpTengageSchema19.setTitleFlag("1");
            prpTengageSchema19.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema19);
            
            PrpTengageSchema prpTengageSchema20 = new PrpTengageSchema();
            prpTengageSchema20.setProposalNo(blMainProposal.getBLPrpTengage().getArr(0).getProposalNo());
            prpTengageSchema20.setRiskCode("3004");
            prpTengageSchema20.setSerialNo("5");
            prpTengageSchema20.setLineNo("4");
            prpTengageSchema20.setClauseCode("T9999");
            prpTengageSchema20.setClauses("本XX合同自逾期之日零时起自动解除，合同效力自逾期之日零时起自动终止。");
            prpTengageSchema20.setTitleFlag("1");
            prpTengageSchema20.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema20);
        }else if (payType.equals("2")){
            
            PrpTengageSchema prpTengageSchema17 = new PrpTengageSchema();
            prpTengageSchema17.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema17.setRiskCode("3004");
            prpTengageSchema17.setSerialNo("5");
            prpTengageSchema17.setLineNo("1");
            prpTengageSchema17.setClauseCode("T9999");
            prpTengageSchema17.setClauses("其他");
            prpTengageSchema17.setTitleFlag("0");
            prpTengageSchema17.setFlag(" 0");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema17);
            
            PrpTengageSchema prpTengageSchema18 = new PrpTengageSchema();
            prpTengageSchema18.setProposalNo(blMainProposal.getBLPrpTengage().getArr(0).getProposalNo());
            prpTengageSchema18.setRiskCode("3004");
            prpTengageSchema18.setSerialNo("5");
            prpTengageSchema18.setLineNo("2");
            prpTengageSchema18.setClauseCode("T9999");
            prpTengageSchema18.setClauses("本XX合同采取月缴的方式交付XX，XX人应按本XX单交费计划表中载明的日期逐");
            prpTengageSchema18.setTitleFlag("1");
            prpTengageSchema18.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema18);
            
            PrpTengageSchema prpTengageSchema19 = new PrpTengageSchema();
            prpTengageSchema19.setProposalNo(blMainProposal.getBLPrpTengage().getArr(0).getProposalNo());
            prpTengageSchema19.setRiskCode("3004");
            prpTengageSchema19.setSerialNo("5");
            prpTengageSchema19.setLineNo("3");
            prpTengageSchema19.setClauseCode("T9999");
            prpTengageSchema19.setClauses("月缴付XX费，XX人承担对应XX责任。XX人如到期未交纳XX费，");
            prpTengageSchema19.setTitleFlag("1");
            prpTengageSchema19.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema19);
            
            PrpTengageSchema prpTengageSchema20 = new PrpTengageSchema();
            prpTengageSchema20.setProposalNo(blMainProposal.getBLPrpTengage().getArr(0).getProposalNo());
            prpTengageSchema20.setRiskCode("3004");
            prpTengageSchema20.setSerialNo("5");
            prpTengageSchema20.setLineNo("4");
            prpTengageSchema20.setClauseCode("T9999");
            prpTengageSchema20.setClauses("本XX合同自逾期之日零时起自动解除，合同效力自逾期之日零时起自动终止。");
            prpTengageSchema20.setTitleFlag("1");
            prpTengageSchema20.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema20);
        }else{
            PrpTengageSchema prpTengageSchema17 = new PrpTengageSchema();
            prpTengageSchema17.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prpTengageSchema17.setRiskCode("3004");
            prpTengageSchema17.setSerialNo("5");
            prpTengageSchema17.setLineNo("1");
            prpTengageSchema17.setClauseCode("T9999");
            prpTengageSchema17.setClauses("其他");
            prpTengageSchema17.setTitleFlag("0");
            prpTengageSchema17.setFlag(" 0");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema17);
            
            PrpTengageSchema prpTengageSchema18 = new PrpTengageSchema();
            prpTengageSchema18.setProposalNo(blMainProposal.getBLPrpTengage().getArr(0).getProposalNo());
            prpTengageSchema18.setRiskCode("3004");
            prpTengageSchema18.setSerialNo("5");
            prpTengageSchema18.setLineNo("2");
            prpTengageSchema18.setClauseCode("T9999");
            prpTengageSchema18.setClauses("XX人应自起XXXXX之日起15日内交费，如未按约定交费，");
            prpTengageSchema18.setTitleFlag("1");
            prpTengageSchema18.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema18);
            
            PrpTengageSchema prpTengageSchema19 = new PrpTengageSchema();
            prpTengageSchema19.setProposalNo(blMainProposal.getBLPrpTengage().getArr(0).getProposalNo());
            prpTengageSchema19.setRiskCode("3004");
            prpTengageSchema19.setSerialNo("5");
            prpTengageSchema19.setLineNo("3");
            prpTengageSchema19.setClauseCode("T9999");
            prpTengageSchema19.setClauses("本XX合同自逾期之日起自动解除，本XX公司自始不承担XX责任。");
            prpTengageSchema19.setTitleFlag("1");
            prpTengageSchema19.setFlag(" 1");
            blMainProposal.getBLPrpTengage().setArr(prpTengageSchema19);
        }
       
    }
 
    public void transTengageSubOne(BLProposal blSubOneProposal) throws Exception{
        PrpTengageSchema prpTengageSchema = new PrpTengageSchema();
        prpTengageSchema.setProposalNo(blSubOneProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema.setRiskCode("2700");
        prpTengageSchema.setSerialNo("1");
        prpTengageSchema.setLineNo("1");
        prpTengageSchema.setClauseCode("T9999");
        prpTengageSchema.setClauses("其他");
        prpTengageSchema.setTitleFlag("0");
        prpTengageSchema.setFlag(" 0");
        blSubOneProposal.getBLPrpTengage().setArr(prpTengageSchema);
        
        PrpTengageSchema prpTengageSchema1 = new PrpTengageSchema();
        prpTengageSchema1.setProposalNo(blSubOneProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema1.setRiskCode("2700");
        prpTengageSchema1.setSerialNo("1");
        prpTengageSchema1.setLineNo("2");
        prpTengageSchema1.setClauseCode("T9999");
        prpTengageSchema1.setClauses("XX人要求解除本XX合同的，XX人根据约定按下列公式计算应退还的未满期净XX费。");
        prpTengageSchema1.setTitleFlag("1");
        prpTengageSchema1.setFlag(" 1");
        blSubOneProposal.getBLPrpTengage().setArr(prpTengageSchema1);
        
        PrpTengageSchema prpTengageSchema2 = new PrpTengageSchema();
        prpTengageSchema2.setProposalNo(blSubOneProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema2.setRiskCode("2700");
        prpTengageSchema2.setSerialNo("1");
        prpTengageSchema2.setLineNo("3");
        prpTengageSchema2.setClauseCode("T9999");
        prpTengageSchema2.setClauses("未满期净XX费=XX费×[1-（XX已经过天数/XX期间天数）]×（1-25%）");
        prpTengageSchema2.setTitleFlag("1");
        prpTengageSchema2.setFlag(" 1");
        blSubOneProposal.getBLPrpTengage().setArr(prpTengageSchema2);
        
        PrpTengageSchema prpTengageSchema3 = new PrpTengageSchema();
        prpTengageSchema3.setProposalNo(blSubOneProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema3.setRiskCode("2700");
        prpTengageSchema3.setSerialNo("2");
        prpTengageSchema3.setLineNo("1");
        prpTengageSchema3.setClauseCode("T9999");
        prpTengageSchema3.setClauses("其他");
        prpTengageSchema3.setTitleFlag("0");
        prpTengageSchema3.setFlag(" 0");
        blSubOneProposal.getBLPrpTengage().setArr(prpTengageSchema3);
        
        PrpTengageSchema prpTengageSchema4 = new PrpTengageSchema();
        prpTengageSchema4.setProposalNo(blSubOneProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema4.setRiskCode("2700");
        prpTengageSchema4.setSerialNo("2");
        prpTengageSchema4.setLineNo("2");
        prpTengageSchema4.setClauseCode("T9999");
        prpTengageSchema4.setClauses("此XX意外伤害XXXXX障配偶和子女作为连带被XX人。如被XX人有多名子女，则每名子女各");
        prpTengageSchema4.setTitleFlag("1");
        prpTengageSchema4.setFlag(" 1");
        blSubOneProposal.getBLPrpTengage().setArr(prpTengageSchema4);
        
        PrpTengageSchema prpTengageSchema5 = new PrpTengageSchema();
        prpTengageSchema5.setProposalNo(blSubOneProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema5.setRiskCode("2700");
        prpTengageSchema5.setSerialNo("2");
        prpTengageSchema5.setLineNo("3");
        prpTengageSchema5.setClauseCode("T9999");
        prpTengageSchema5.setClauses("项意外伤害XX金额等于XX所载的相应的各项子女XX金额除以子女人数。（即每名子女");
        prpTengageSchema5.setTitleFlag("1");
        prpTengageSchema5.setFlag(" 1");
        blSubOneProposal.getBLPrpTengage().setArr(prpTengageSchema5);
        
        
        
        PrpTengageSchema prpTengageSchema6 = new PrpTengageSchema();
        prpTengageSchema6.setProposalNo(blSubOneProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema6.setRiskCode("2700");
        prpTengageSchema6.setSerialNo("2");
        prpTengageSchema6.setLineNo("4");
        prpTengageSchema6.setClauseCode("T9999");
        prpTengageSchema6.setClauses("意外伤害XX金额为5万元/n，飞机、火车和轮船意外伤害XX金额分别为5万元/n，意外伤");
        prpTengageSchema6.setTitleFlag("1");
        prpTengageSchema6.setFlag(" 1");
        blSubOneProposal.getBLPrpTengage().setArr(prpTengageSchema6);
        
        PrpTengageSchema prpTengageSchema7 = new PrpTengageSchema();
        prpTengageSchema7.setProposalNo(blSubOneProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema7.setRiskCode("2700");
        prpTengageSchema7.setSerialNo("2");
        prpTengageSchema7.setLineNo("5");
        prpTengageSchema7.setClauseCode("T9999");
        prpTengageSchema7.setClauses("害医疗XX金为0.5万元/n，其中n为子女人数。）");
        prpTengageSchema7.setTitleFlag("1");
        prpTengageSchema7.setFlag(" 1");
        blSubOneProposal.getBLPrpTengage().setArr(prpTengageSchema7);
        
        PrpTengageSchema prpTengageSchema8 = new PrpTengageSchema();
        prpTengageSchema8.setProposalNo(blSubOneProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema8.setRiskCode("2700");
        prpTengageSchema8.setSerialNo("3");
        prpTengageSchema8.setLineNo("1");
        prpTengageSchema8.setClauseCode("T9999");
        prpTengageSchema8.setClauses("其他");
        prpTengageSchema8.setTitleFlag("0");
        prpTengageSchema8.setFlag(" 0");
        blSubOneProposal.getBLPrpTengage().setArr(prpTengageSchema8);
        
        PrpTengageSchema prpTengageSchema9 = new PrpTengageSchema();
        prpTengageSchema9.setProposalNo(blSubOneProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema9.setRiskCode("2700");
        prpTengageSchema9.setSerialNo("3");
        prpTengageSchema9.setLineNo("2");
        prpTengageSchema9.setClauseCode("T9999");
        prpTengageSchema9.setClauses("a”、“b");
        prpTengageSchema9.setTitleFlag("1");
        prpTengageSchema9.setFlag(" 1");
        blSubOneProposal.getBLPrpTengage().setArr(prpTengageSchema9);
        
        PrpTengageSchema prpTengageSchema10 = new PrpTengageSchema();
        prpTengageSchema10.setProposalNo(blSubOneProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema10.setRiskCode("2700");
        prpTengageSchema10.setSerialNo("3");
        prpTengageSchema10.setLineNo("3");
        prpTengageSchema10.setClauseCode("T9999");
        prpTengageSchema10.setClauses("a”、“b");
        prpTengageSchema10.setTitleFlag("1");
        prpTengageSchema10.setFlag(" 1");
        blSubOneProposal.getBLPrpTengage().setArr(prpTengageSchema10);
        
        PrpTengageSchema prpTengageSchema11 = new PrpTengageSchema();
        prpTengageSchema11.setProposalNo(blSubOneProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema11.setRiskCode("2700");
        prpTengageSchema11.setSerialNo("3");
        prpTengageSchema11.setLineNo("4");
        prpTengageSchema11.setClauseCode("T9999");
        prpTengageSchema11.setClauses("”。");
        prpTengageSchema11.setTitleFlag("1");
        prpTengageSchema11.setFlag(" 1");
        blSubOneProposal.getBLPrpTengage().setArr(prpTengageSchema11);
        
        
    }
    
    public void transTengageSubTow(BLProposal blSubTowProposal) throws Exception{
        PrpTengageSchema prpTengageSchema = new PrpTengageSchema();
        prpTengageSchema.setProposalNo(blSubTowProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema.setRiskCode("0301");
        prpTengageSchema.setSerialNo("1");
        prpTengageSchema.setLineNo("1");
        prpTengageSchema.setClauseCode("T9999");
        prpTengageSchema.setClauses("其他");
        prpTengageSchema.setTitleFlag("0");
        prpTengageSchema.setFlag(" 0");
        blSubTowProposal.getBLPrpTengage().setArr(prpTengageSchema);
        
        PrpTengageSchema prpTengageSchema1 = new PrpTengageSchema();
        prpTengageSchema1.setProposalNo(blSubTowProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema1.setRiskCode("0301");
        prpTengageSchema1.setSerialNo("1");
        prpTengageSchema1.setLineNo("2");
        prpTengageSchema1.setClauseCode("T9999");
        prpTengageSchema1.setClauses("XX人要求解除本XX合同的，XX人根据约定按下列公式计算应退还的未满期净XX费。");
        prpTengageSchema1.setTitleFlag("1");
        prpTengageSchema1.setFlag(" 1");
        blSubTowProposal.getBLPrpTengage().setArr(prpTengageSchema1);
        
        PrpTengageSchema prpTengageSchema2 = new PrpTengageSchema();
        prpTengageSchema2.setProposalNo(blSubTowProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema2.setRiskCode("0301");
        prpTengageSchema2.setSerialNo("1");
        prpTengageSchema2.setLineNo("3");
        prpTengageSchema2.setClauseCode("T9999");
        prpTengageSchema2.setClauses("未满期净XX费=XX费×[1-（XX已经过天数/XX期间天数）]×（1-25%）");
        prpTengageSchema2.setTitleFlag("1");
        prpTengageSchema2.setFlag(" 1");
        blSubTowProposal.getBLPrpTengage().setArr(prpTengageSchema2);
        
        PrpTengageSchema prpTengageSchema3 = new PrpTengageSchema();
        prpTengageSchema3.setProposalNo(blSubTowProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema3.setRiskCode("0301");
        prpTengageSchema3.setSerialNo("2");
        prpTengageSchema3.setLineNo("1");
        prpTengageSchema3.setClauseCode("T9999");
        prpTengageSchema3.setClauses("其他");
        prpTengageSchema3.setTitleFlag("0");
        prpTengageSchema3.setFlag(" 0");
        blSubTowProposal.getBLPrpTengage().setArr(prpTengageSchema3);
        
        PrpTengageSchema prpTengageSchema4 = new PrpTengageSchema();
        prpTengageSchema4.setProposalNo(blSubTowProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema4.setRiskCode("0301");
        prpTengageSchema4.setSerialNo("2");
        prpTengageSchema4.setLineNo("2");
        prpTengageSchema4.setClauseCode("T9999");
        prpTengageSchema4.setClauses("此XX意外伤害XXXXX障配偶和子女作为连带被XX人。如被XX人有多名子女，则每名子女各");
        prpTengageSchema4.setTitleFlag("1");
        prpTengageSchema4.setFlag(" 1");
        blSubTowProposal.getBLPrpTengage().setArr(prpTengageSchema4);
        
        PrpTengageSchema prpTengageSchema5 = new PrpTengageSchema();
        prpTengageSchema5.setProposalNo(blSubTowProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema5.setRiskCode("0301");
        prpTengageSchema5.setSerialNo("2");
        prpTengageSchema5.setLineNo("3");
        prpTengageSchema5.setClauseCode("T9999");
        prpTengageSchema5.setClauses("项意外伤害XX金额等于XX所载的相应的各项子女XX金额除以子女人数。（即每名子女");
        prpTengageSchema5.setTitleFlag("1");
        prpTengageSchema5.setFlag(" 1");
        blSubTowProposal.getBLPrpTengage().setArr(prpTengageSchema5);
        
        
        
        PrpTengageSchema prpTengageSchema6 = new PrpTengageSchema();
        prpTengageSchema6.setProposalNo(blSubTowProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema6.setRiskCode("0301");
        prpTengageSchema6.setSerialNo("2");
        prpTengageSchema6.setLineNo("4");
        prpTengageSchema6.setClauseCode("T9999");
        prpTengageSchema6.setClauses("意外伤害XX金额为5万元/n，飞机、火车和轮船意外伤害XX金额分别为5万元/n，意外伤");
        prpTengageSchema6.setTitleFlag("1");
        prpTengageSchema6.setFlag(" 1");
        blSubTowProposal.getBLPrpTengage().setArr(prpTengageSchema6);
        
        PrpTengageSchema prpTengageSchema7 = new PrpTengageSchema();
        prpTengageSchema7.setProposalNo(blSubTowProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema7.setRiskCode("0301");
        prpTengageSchema7.setSerialNo("2");
        prpTengageSchema7.setLineNo("5");
        prpTengageSchema7.setClauseCode("T9999");
        prpTengageSchema7.setClauses("害医疗XX金为0.5万元/n，其中n为子女人数。）");
        prpTengageSchema7.setTitleFlag("1");
        prpTengageSchema7.setFlag(" 1");
        blSubTowProposal.getBLPrpTengage().setArr(prpTengageSchema7);
        
        PrpTengageSchema prpTengageSchema8 = new PrpTengageSchema();
        prpTengageSchema8.setProposalNo(blSubTowProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema8.setRiskCode("0301");
        prpTengageSchema8.setSerialNo("3");
        prpTengageSchema8.setLineNo("1");
        prpTengageSchema8.setClauseCode("T9999");
        prpTengageSchema8.setClauses("其他");
        prpTengageSchema8.setTitleFlag("0");
        prpTengageSchema8.setFlag(" 0");
        blSubTowProposal.getBLPrpTengage().setArr(prpTengageSchema8);
        
        PrpTengageSchema prpTengageSchema9 = new PrpTengageSchema();
        prpTengageSchema9.setProposalNo(blSubTowProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema9.setRiskCode("0301");
        prpTengageSchema9.setSerialNo("3");
        prpTengageSchema9.setLineNo("2");
        prpTengageSchema9.setClauseCode("T9999");
        prpTengageSchema9.setClauses("a”、“b");
        prpTengageSchema9.setTitleFlag("1");
        prpTengageSchema9.setFlag(" 1");
        blSubTowProposal.getBLPrpTengage().setArr(prpTengageSchema9);
        
        PrpTengageSchema prpTengageSchema10 = new PrpTengageSchema();
        prpTengageSchema10.setProposalNo(blSubTowProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema10.setRiskCode("0301");
        prpTengageSchema10.setSerialNo("3");
        prpTengageSchema10.setLineNo("3");
        prpTengageSchema10.setClauseCode("T9999");
        prpTengageSchema10.setClauses("XXXXX”、“a”、“b");
        prpTengageSchema10.setTitleFlag("1");
        prpTengageSchema10.setFlag(" 1");
        blSubTowProposal.getBLPrpTengage().setArr(prpTengageSchema10);
        
        PrpTengageSchema prpTengageSchema11 = new PrpTengageSchema();
        prpTengageSchema11.setProposalNo(blSubTowProposal.getBLPrpTengage().getArr(0).getProposalNo());
        prpTengageSchema11.setRiskCode("0301");
        prpTengageSchema11.setSerialNo("3");
        prpTengageSchema11.setLineNo("4");
        prpTengageSchema11.setClauseCode("T9999");
        prpTengageSchema11.setClauses("”。");
        prpTengageSchema11.setTitleFlag("1");
        prpTengageSchema11.setFlag(" 1");
        blSubTowProposal.getBLPrpTengage().setArr(prpTengageSchema11);
        
        
    }    
    
    public void transTplan(BLProposal blMainProposal,BLProposal blSubOneProposal,BLProposal blSubTowProposal,String payTimes,String startDate,String newpaymode,String extpaymode) throws Exception{
        
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd"); 
        double fontfee=0.0;
        
        if(payTimes.equals("1")){
            PrpTplanSchema          prptplanSchema          = new PrpTplanSchema();
            prptplanSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prptplanSchema.setSerialNo("1");
            prptplanSchema.setPayNo("1");
            prptplanSchema.setPayReason("R10");
            
            java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
            calendar.setTime( PubTools.stringToUtilDate(startDate));   
            calendar.add(Calendar.DATE,+0);
            int   year   =   calendar.get(Calendar.YEAR);   
            int   month   =   calendar.get(Calendar.MONTH)+1;   
            int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
            prptplanSchema.setPlanStartDate(startDate);
            prptplanSchema.setPlanDate(year+"-"+month+"-"+day);
            
            
            prptplanSchema.setCurrency("CNY");
            prptplanSchema.setFlag("  "+newpaymode);
            prptplanSchema.setPlanFee(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium());
            prptplanSchema.setDelinquentFee(blMainProposal.getBLPrpTmain().getArr(0).getSumPremium());
            
            blMainProposal.getBLPrpTplan().setArr(prptplanSchema);
        }else if(payTimes.equals("3")){
            for(int i=0;i<4;i++){
                PrpTplanSchema          prptplanSchema          = new PrpTplanSchema();
                prptplanSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
                prptplanSchema.setSerialNo((i+1)+"");
                prptplanSchema.setPayNo(prptplanSchema.getSerialNo());
                prptplanSchema.setPayReason("R10");
                
                if(i==0){
                    prptplanSchema.setFlag("  "+newpaymode);
                }else{
                    prptplanSchema.setFlag("  "+extpaymode);
                    prptplanSchema.setPayReason("R20");
                }
                
                
                
                
                java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
                calendar.setTime( PubTools.stringToUtilDate(startDate));   
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
                    prptplanSchema.setFlag("  "+newpaymode);
                }else{
                    prptplanSchema.setFlag("  "+extpaymode);
                    prptplanSchema.setPayReason("R20");
                }
                
                java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
                calendar.setTime( PubTools.stringToUtilDate(startDate));   
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
        if(payTimes.equals("1")){
            PrpTplanSchema          prptplanSchemaSubOne          = new PrpTplanSchema();
            prptplanSchemaSubOne.setProposalNo(blSubOneProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prptplanSchemaSubOne.setSerialNo("1");
            prptplanSchemaSubOne.setPayNo("1");
            prptplanSchemaSubOne.setPayReason("R10");
            prptplanSchemaSubOne.setFlag("  "+newpaymode);
            
            
            java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
            calendar.setTime( PubTools.stringToUtilDate(startDate));   
            calendar.add(Calendar.DATE,+0);
            int   year   =   calendar.get(Calendar.YEAR);   
            int   month   =   calendar.get(Calendar.MONTH)+1;   
            int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
            prptplanSchemaSubOne.setPlanStartDate(startDate);
            prptplanSchemaSubOne.setPlanDate(year+"-"+month+"-"+day);
            
            
            prptplanSchemaSubOne.setCurrency("CNY");
            prptplanSchemaSubOne.setPlanFee(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium());
            prptplanSchemaSubOne.setDelinquentFee(blSubOneProposal.getBLPrpTmain().getArr(0).getSumPremium());
            
            blSubOneProposal.getBLPrpTplan().setArr(prptplanSchemaSubOne);
        }else if(payTimes.equals("3")){
            for(int i=0;i<4;i++){
                PrpTplanSchema          prptplanSchemaSubOne          = new PrpTplanSchema();
                prptplanSchemaSubOne.setProposalNo(blSubOneProposal.getBLPrpTmain().getArr(0).getProposalNo());
                prptplanSchemaSubOne.setSerialNo((i+1)+"");
                prptplanSchemaSubOne.setPayNo(prptplanSchemaSubOne.getSerialNo());
                prptplanSchemaSubOne.setPayReason("R10");
                
                if(i==0){
                    prptplanSchemaSubOne.setFlag("  "+newpaymode);
                }else{
                    prptplanSchemaSubOne.setFlag("  "+extpaymode);
                    prptplanSchemaSubOne.setPayReason("R20");
                }
                java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
                calendar.setTime( PubTools.stringToUtilDate(startDate));   
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
                    prptplanSchemaSubOne.setFlag("  "+newpaymode);
                }else{
                    prptplanSchemaSubOne.setFlag("  "+extpaymode);
                    prptplanSchemaSubOne.setPayReason("R20");
                }
                
                
                
                
                
                
                
                java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
                calendar.setTime( PubTools.stringToUtilDate(startDate));   
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
        if(payTimes.equals("1")){
            PrpTplanSchema          prptplanSchemaSubTow          = new PrpTplanSchema();
            prptplanSchemaSubTow.setProposalNo(blSubTowProposal.getBLPrpTmain().getArr(0).getProposalNo());
            prptplanSchemaSubTow.setSerialNo("1");
            prptplanSchemaSubTow.setPayNo("1");
            prptplanSchemaSubTow.setPayReason("R10");
            prptplanSchemaSubTow.setFlag("  "+newpaymode);
           
            
            
            
            java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
            calendar.setTime( PubTools.stringToUtilDate(startDate));   
            calendar.add(Calendar.DATE,+0);
            int   year   =   calendar.get(Calendar.YEAR);   
            int   month   =   calendar.get(Calendar.MONTH)+1;   
            int   day   =   calendar.get(Calendar.DAY_OF_MONTH);
            prptplanSchemaSubTow.setPlanStartDate(startDate);
            prptplanSchemaSubTow.setPlanDate(year+"-"+month+"-"+day);
            prptplanSchemaSubTow.setCurrency("CNY");
            prptplanSchemaSubTow.setPlanFee(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium());
            prptplanSchemaSubTow.setDelinquentFee(blSubTowProposal.getBLPrpTmain().getArr(0).getSumPremium());
            
            blSubTowProposal.getBLPrpTplan().setArr(prptplanSchemaSubTow);
        }else if(payTimes.equals("3")){
            for(int i=0;i<4;i++){
                PrpTplanSchema          prptplanSchemaSubTow          = new PrpTplanSchema();
                
                prptplanSchemaSubTow.setProposalNo(blSubTowProposal.getBLPrpTmain().getArr(0).getProposalNo());
                prptplanSchemaSubTow.setSerialNo((i+1)+"");
                prptplanSchemaSubTow.setPayNo(prptplanSchemaSubTow.getSerialNo());
                prptplanSchemaSubTow.setPayReason("R10");
                
                if(i==0){
                    prptplanSchemaSubTow.setFlag("  "+newpaymode);
                }else{
                    prptplanSchemaSubTow.setPayReason("R20");
                    prptplanSchemaSubTow.setFlag("  "+extpaymode);
                }
                
                
                
                
                
                
                
                java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
                calendar.setTime( PubTools.stringToUtilDate(startDate));   
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
                    prptplanSchemaSubTow.setFlag("  "+newpaymode);
                }else{
                    prptplanSchemaSubTow.setFlag("  "+extpaymode);
                    prptplanSchemaSubTow.setPayReason("R20");
                }
                
                
                
                
                
                
                
                java.util.Calendar   calendar   =   java.util.Calendar.getInstance(); 
                calendar.setTime( PubTools.stringToUtilDate(startDate));   
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
    
    public void transTfee(BLProposal blMainProposal,BLProposal blSubOneProposal,BLProposal blSubTowProposal) throws Exception{
        PrpTfeeSchema           prpTfeeSchema           = new PrpTfeeSchema();
        prpTfeeSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTfeeSchema.setRiskCode("3004");
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
        prpTfeeSchemaSubTow.setRiskCode("0301");
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
    
    public void transTexpense(BLProposal blMainProposal,BLProposal blSubOneProposal,BLProposal blSubTowProposal) throws Exception{
        PrpTexpenseSchema       prpTexpenseSchema       = new PrpTexpenseSchema();
        prpTexpenseSchema.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTexpenseSchema.setRiskCode("3004");
        prpTexpenseSchema.setFlag("I1");
        blMainProposal.getBLPrpTexpense().setArr(prpTexpenseSchema);
        PrpTexpenseSchema       prpTexpenseSchemaSubOne       = new PrpTexpenseSchema();
        prpTexpenseSchemaSubOne.setProposalNo(blSubOneProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTexpenseSchemaSubOne.setRiskCode("2700");
        prpTexpenseSchemaSubOne.setFlag("I1");
        blSubOneProposal.getBLPrpTexpense().setArr(prpTexpenseSchemaSubOne);
        PrpTexpenseSchema       prpTexpenseSchemaSubTow       = new PrpTexpenseSchema();
        prpTexpenseSchemaSubTow.setProposalNo(blSubTowProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTexpenseSchemaSubTow.setRiskCode("0301");
        prpTexpenseSchemaSubTow.setFlag("I1");
        blSubTowProposal.getBLPrpTexpense().setArr(prpTexpenseSchemaSubTow);
    }
    
    public void transTmainSub(BLProposal blSubOneProposal,BLProposal blSubTowProposal,String mainPolicyno) throws Exception{
        PrpTmainSubSchema       prpTmainSubSchemaSubOne       = new PrpTmainSubSchema();
        prpTmainSubSchemaSubOne.setProposalNo(blSubOneProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTmainSubSchemaSubOne.setMainPolicyNo(mainPolicyno);
        blSubOneProposal.getBLPrpTmainSub().setArr(prpTmainSubSchemaSubOne);
        
        PrpTmainSubSchema       prpTmainSubSchemaSubTow       = new PrpTmainSubSchema();
        prpTmainSubSchemaSubTow.setProposalNo(blSubTowProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTmainSubSchemaSubTow.setMainPolicyNo(mainPolicyno);
        blSubTowProposal.getBLPrpTmainSub().setArr(prpTmainSubSchemaSubTow);
    }
    
    public void transTmainCasualty(BLProposal blMainProposal, BLProposal blSubOneProposal,String grade) throws Exception{
        
        PrpTmainCasualtySchema  prpTmainCasualtySchemaMain  = new PrpTmainCasualtySchema();
        prpTmainCasualtySchemaMain.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTmainCasualtySchemaMain.setRiskCode("3004");
        if(grade.equals("A")){
            prpTmainCasualtySchemaMain.setBusinessGrade("1");  
        }else{
            prpTmainCasualtySchemaMain.setBusinessGrade("2");  
        }
        blMainProposal.getBLPrpTmainCasualty().setArr(prpTmainCasualtySchemaMain);
        
        
        PrpTmainCasualtySchema  prpTmainCasualtySchema  = new PrpTmainCasualtySchema();
        prpTmainCasualtySchema.setProposalNo(blSubOneProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTmainCasualtySchema.setRiskCode("2700");
        if(grade.equals("A")){
           prpTmainCasualtySchema.setBusinessGrade("1");  
        }else{
           prpTmainCasualtySchema.setBusinessGrade("2");  
        }
        blSubOneProposal.getBLPrpTmainCasualty().setArr(prpTmainCasualtySchema);
    }
    
    public void transTaddress(BLProposal blMainProposal ,BLProposal blSubTowProposal,String addresscode,String addressname) throws Exception{
        
        
        PrpTaddressSchema       prpTaddressSchemaMain       = new PrpTaddressSchema();
        prpTaddressSchemaMain.setProposalNo(blMainProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTaddressSchemaMain.setRiskCode("3004");
        prpTaddressSchemaMain.setAddressNo("1");
        prpTaddressSchemaMain.setAddressCode(addresscode);
        prpTaddressSchemaMain.setAddressName(addressname);
        blMainProposal.getBLPrpTaddress().setArr(prpTaddressSchemaMain);
        
        PrpTaddressSchema       prpTaddressSchema       = new PrpTaddressSchema();
        prpTaddressSchema.setProposalNo(blSubTowProposal.getBLPrpTmain().getArr(0).getProposalNo());
        prpTaddressSchema.setRiskCode("0301");
        prpTaddressSchema.setAddressNo("1");
        prpTaddressSchema.setAddressCode(addresscode);
        prpTaddressSchema.setAddressName(addressname);
        
        blSubTowProposal.getBLPrpTaddress().setArr(prpTaddressSchema);
    }
    
    public String getSubKindCode(String MainKindCode){
        if(MainKindCode.equals("111")){
            return "118";
        }else if(MainKindCode.equals("308")){
            return "114";
        }else if(MainKindCode.equals("310")){
            return "157";
        }else if(MainKindCode.equals("120")){
            return "149";
        }else if(MainKindCode.equals("121")){
            return "121";
        }else {
            return "";
        }
        
    }
    public String getSubItemCode(String MainItemCode){
        if(MainItemCode.equals("0007")){
            return "0077";
        }else if(MainItemCode.equals("0008")){
            return "0053";
        }else if(MainItemCode.equals("0009")){
            return "0041";
        }else if(MainItemCode.equals("0011")){
            return "0070";
        }else if(MainItemCode.equals("0012")){
            return "0071";
        }else if(MainItemCode.equals("0013")){
            return "0072";
        }else if(MainItemCode.equals("0039")){
            return "0039";
        }else{
            return "";
        }
        
        
    }
    public void transTitemKindSub(BLProposal blMainProposal,BLProposal blSubOneProposal,BLProposal blSubTowProposal,PermiumAmountSchema permiumAmountSchema,String SubOnePolicyNo,String SubTowPolicyNo) throws Exception{
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
            if(prpTitemKindSchema.getKindCode().equals("101")
                    ||prpTitemKindSchema.getKindCode().equals("102")
                    ||prpTitemKindSchema.getKindCode().equals("103")
                    ||prpTitemKindSchema.getKindCode().equals("104")
                    ||prpTitemKindSchema.getKindCode().equals("105")
                    ||prpTitemKindSchema.getKindCode().equals("106")
                    ||prpTitemKindSchema.getKindCode().equals("107")
                    ||prpTitemKindSchema.getKindCode().equals("108")
                    ||prpTitemKindSchema.getKindCode().equals("001")){
                prpTitemKindSchemaSubTow.setProposalNo(SubTowPolicyNo);
                prpTitemKindSchemaSubTow.setRiskCode("0301");
                prpTitemKindSchemaSubTow.setItemKindNo(prpTitemKindSchema.getItemKindNo());
                prpTitemKindSchemaSubTow.setFamilyNo(prpTitemKindSchema.getFamilyNo());
                prpTitemKindSchemaSubTow.setFamilyName(prpTitemKindSchema.getFamilyName());
                prpTitemKindSchemaSubTow.setKindCode(prpTitemKindSchema.getKindCode());
                prpTitemKindSchemaSubTow.setKindName(prpTitemKindSchema.getKindName());
                
                if(prpTitemKindSchema.getKindCode().equals("001")&&prpTitemKindSchema.getItemCode().equals("0003")){
                    prpTitemKindSchemaSubTow.setItemCode("0004");
                }else{
                    prpTitemKindSchemaSubTow.setItemCode(prpTitemKindSchema.getItemCode());
                }
                
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
                prpTitemKindSchemaSubOne.setProposalNo(SubOnePolicyNo);
                prpTitemKindSchemaSubOne.setRiskCode("2700");
                prpTitemKindSchemaSubOne.setItemKindNo(prpTitemKindSchema.getItemKindNo());
                prpTitemKindSchemaSubOne.setFamilyNo(prpTitemKindSchema.getFamilyNo());
                prpTitemKindSchemaSubOne.setFamilyName(prpTitemKindSchema.getFamilyName());
                prpTitemKindSchemaSubOne.setKindCode(getSubKindCode(prpTitemKindSchema.getKindCode()));
                prpTitemKindSchemaSubOne.setKindName(prpTitemKindSchema.getKindName());
                prpTitemKindSchemaSubOne.setItemCode(getSubItemCode(prpTitemKindSchema.getItemCode()));
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
    
    
    public void transTitemKind(BLProposal blMainProposal,BLPrpTinsured blPrpInsured,String PolicyType,String grade,String payType,String startDate,String endDate,PermiumAmountSchema permiumAmountSchema,String MainPolicyNo,int boys,int parents,ArrayList TMCardInfoList) throws Exception{
        
        
        
        
        
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
    	
    	ArrayList tempCardinfo= new ArrayList();
        int muti=0;
        if(TMCardInfoList.size()>0){
            tempCardinfo=(ArrayList)TMCardInfoList.get(0);
            String quatily=(String)tempCardinfo.get(6);
            muti=Integer.parseInt(quatily);
        }
        
        String rationType=getRationType(grade,payType,PolicyType,"01");
        BLPrpDration prpdration = new BLPrpDration();
        DBPrpDkind dbPrpDkind= null;
        BLPrpDitem blPrpDitem=null;
        dbPrpDkind = new DBPrpDkind();
        double sumAmount=0.00d;
        double sumPermium=0.0d;
        prpdration.query(" riskcode='3004' and rationtype='"+rationType+"'");
        BLPrpTitemKind blPrpTitemKind = new BLPrpTitemKind();
        BLPrpDkind blPrpDkind = new BLPrpDkind();
        blPrpDkind.query(" riskcode='3004' ");
        String CalculateFlagString ="";
        for(int n=0;n<blPrpDkind.getSize();n++){
            if(blPrpDkind.getArr(n).getCalculateFlag().substring(0,1).equals("Y")){
                CalculateFlagString=CalculateFlagString+blPrpDkind.getArr(n).getKindCode()+",";
            }
        }

        for(int j=0;j<prpdration.getSize();j++){
            String tempKindCode=prpdration.getArr(j).getKindCode();
            if(tempKindCode.equals("111")
                ||tempKindCode.equals("308")
                ||tempKindCode.equals("310")
                ||tempKindCode.equals("120")){
                PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
                prpTitemKindSchema.setProposalNo(MainPolicyNo);
                prpTitemKindSchema.setRiskCode("3004");
                prpTitemKindSchema.setItemKindNo((blPrpTitemKind.getSize() + 1) + "");
                
                prpTitemKindSchema.setFamilyNo("2");
                
                
                prpTitemKindSchema.setFamilyName(selfName);
                
                prpTitemKindSchema.setKindCode(prpdration.getArr(j).getKindCode());
                dbPrpDkind.getInfo("3004",prpdration.getArr(j).getKindCode());
                String strKindName = dbPrpDkind.getKindCName();
                blPrpDitem = new BLPrpDitem();
                String strItemDetailName = blPrpDitem.translateCode("3004",prpdration.getArr(j).getItemCode(),true);
                prpTitemKindSchema.setKindName(strKindName);
                prpTitemKindSchema.setItemCode(prpdration.getArr(j).getItemCode());
                prpTitemKindSchema.setItemDetailName(strItemDetailName);
                prpTitemKindSchema.setStartDate(startDate);
                prpTitemKindSchema.setStartHour("0");
                prpTitemKindSchema.setEndDate(endDate);
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
                if(prpTitemKindSchema.getItemCode().equals("9999")||prpTitemKindSchema.getItemCode().equals("0003")||prpTitemKindSchema.getItemCode().equals("0001")){
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
                }else if(prpTitemKindSchema.getKindCode().equals("101")
                        ||prpTitemKindSchema.getKindCode().equals("102")
                        ||prpTitemKindSchema.getKindCode().equals("103")
                        ||prpTitemKindSchema.getKindCode().equals("104")
                        ||prpTitemKindSchema.getKindCode().equals("105")
                        ||prpTitemKindSchema.getKindCode().equals("106")
                        ||prpTitemKindSchema.getKindCode().equals("107")
                        ||prpTitemKindSchema.getKindCode().equals("108")){
                    prpTitemKindSchema.setFlag(" 4");
                }else if(prpTitemKindSchema.getKindCode().equals("120")
                        ||prpTitemKindSchema.getKindCode().equals("111")){
                    prpTitemKindSchema.setFlag(" 1");
                }else if(prpTitemKindSchema.getKindCode().equals("308")
                        ||prpTitemKindSchema.getKindCode().equals("310")){
                    prpTitemKindSchema.setFlag(" 2");
                }else if(prpTitemKindSchema.getKindCode().equals("121")){
                    prpTitemKindSchema.setFlag(" 1");
                }
                blPrpTitemKind.setArr(prpTitemKindSchema);
            }
                
        }
        
        for(int j=0;j<prpdration.getSize();j++){
            String tempKindCode=prpdration.getArr(j).getKindCode();
            if(!tempKindCode.equals("111")
                &&!tempKindCode.equals("308")
                &&!tempKindCode.equals("310")
                &&!tempKindCode.equals("120")){
                PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
                prpTitemKindSchema.setProposalNo(MainPolicyNo);
                prpTitemKindSchema.setRiskCode("3004");
                prpTitemKindSchema.setItemKindNo((blPrpTitemKind.getSize() + 1) + "");
                prpTitemKindSchema.setFamilyNo("");
                
                prpTitemKindSchema.setKindCode(prpdration.getArr(j).getKindCode());
                dbPrpDkind.getInfo("3004",prpdration.getArr(j).getKindCode());
                String strKindName = dbPrpDkind.getKindCName();
                blPrpDitem = new BLPrpDitem();
                String strItemDetailName = blPrpDitem.translateCode("3004",prpdration.getArr(j).getItemCode(),true);
                prpTitemKindSchema.setKindName(strKindName);
                prpTitemKindSchema.setItemCode(prpdration.getArr(j).getItemCode());
                prpTitemKindSchema.setItemDetailName(strItemDetailName);
                prpTitemKindSchema.setStartDate(startDate);
                prpTitemKindSchema.setStartHour("0");
                prpTitemKindSchema.setEndDate(endDate);
                prpTitemKindSchema.setEndHour("24");
                /*if(prpTitemKindSchema.getKindCode().equals("101")
                   ||prpTitemKindSchema.getKindCode().equals("102")
                   ||prpTitemKindSchema.getKindCode().equals("103")
                   ||prpTitemKindSchema.getKindCode().equals("104")
                   ||prpTitemKindSchema.getKindCode().equals("105")
                   ||prpTitemKindSchema.getKindCode().equals("106")
                   ||prpTitemKindSchema.getKindCode().equals("107")
                   ||prpTitemKindSchema.getKindCode().equals("108")){
                   prpTitemKindSchema.setCalculateFlag("N"); 
                }else{
                   prpTitemKindSchema.setCalculateFlag("Y");
                }*/
                
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
                if(prpTitemKindSchema.getItemCode().equals("9999")||prpTitemKindSchema.getItemCode().equals("0003")||prpTitemKindSchema.getItemCode().equals("0001")){
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
                }else if(prpTitemKindSchema.getKindCode().equals("101")
                        ||prpTitemKindSchema.getKindCode().equals("102")
                        ||prpTitemKindSchema.getKindCode().equals("103")
                        ||prpTitemKindSchema.getKindCode().equals("104")
                        ||prpTitemKindSchema.getKindCode().equals("105")
                        ||prpTitemKindSchema.getKindCode().equals("106")
                        ||prpTitemKindSchema.getKindCode().equals("107")
                        ||prpTitemKindSchema.getKindCode().equals("108")){
                    prpTitemKindSchema.setFlag(" 4");
                }else if(prpTitemKindSchema.getKindCode().equals("120")
                        ||prpTitemKindSchema.getKindCode().equals("111")){
                    prpTitemKindSchema.setFlag(" 1");
                }else if(prpTitemKindSchema.getKindCode().equals("308")
                        ||prpTitemKindSchema.getKindCode().equals("310")){
                    prpTitemKindSchema.setFlag(" 2");
                }else if(prpTitemKindSchema.getKindCode().equals("121")){
                    prpTitemKindSchema.setFlag(" 1");
                }
                blPrpTitemKind.setArr(prpTitemKindSchema);
            }
        }
        
        if(PolicyType.equals("26")){
            rationType=getRationType(grade,payType,PolicyType,"10");
            prpdration.query(" riskcode='3004' and  rationtype='"+rationType+"'");
            
            for(int j=0;j<prpdration.getSize();j++){
                if(prpdration.getArr(j).getKindCode().equals("111")||prpdration.getArr(j).getKindCode().equals("308")||prpdration.getArr(j).getKindCode().equals("120")){
                    PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
                    prpTitemKindSchema.setProposalNo(MainPolicyNo);
                    prpTitemKindSchema.setRiskCode("3004");
                    prpTitemKindSchema.setItemKindNo((blPrpTitemKind.getSize() + 1) + "");
                    prpTitemKindSchema.setFamilyNo("3");
                    
                    
                    prpTitemKindSchema.setFamilyName(wifeName);
                    
                    prpTitemKindSchema.setKindCode(prpdration.getArr(j).getKindCode());
                    dbPrpDkind.getInfo("3004",prpdration.getArr(j).getKindCode());
                    String strKindName = dbPrpDkind.getKindCName();
                    blPrpDitem = new BLPrpDitem();
                    String strItemDetailName = blPrpDitem.translateCode("3004",prpdration.getArr(j).getItemCode(),true);
                    prpTitemKindSchema.setKindName(strKindName);
                    prpTitemKindSchema.setItemCode(prpdration.getArr(j).getItemCode());
                    prpTitemKindSchema.setItemDetailName(strItemDetailName);
                    prpTitemKindSchema.setStartDate(startDate);
                    prpTitemKindSchema.setStartHour("0");
                    prpTitemKindSchema.setEndDate(endDate);
                    prpTitemKindSchema.setEndHour("24");
                    /*if(prpTitemKindSchema.getKindCode().equals("101")
                       ||prpTitemKindSchema.getKindCode().equals("102")
                       ||prpTitemKindSchema.getKindCode().equals("103")
                       ||prpTitemKindSchema.getKindCode().equals("104")
                       ||prpTitemKindSchema.getKindCode().equals("105")
                       ||prpTitemKindSchema.getKindCode().equals("106")
                       ||prpTitemKindSchema.getKindCode().equals("107")
                       ||prpTitemKindSchema.getKindCode().equals("108")){
                       prpTitemKindSchema.setCalculateFlag("N"); 
                    }else{
                       prpTitemKindSchema.setCalculateFlag("Y");
                    }*/
                    
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
                    if(prpTitemKindSchema.getItemCode().equals("9999")||prpTitemKindSchema.getItemCode().equals("0003")||prpTitemKindSchema.getItemCode().equals("0001")){
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
                    }else if(prpTitemKindSchema.getKindCode().equals("101")
                            ||prpTitemKindSchema.getKindCode().equals("102")
                            ||prpTitemKindSchema.getKindCode().equals("103")
                            ||prpTitemKindSchema.getKindCode().equals("104")
                            ||prpTitemKindSchema.getKindCode().equals("105")
                            ||prpTitemKindSchema.getKindCode().equals("106")
                            ||prpTitemKindSchema.getKindCode().equals("107")
                            ||prpTitemKindSchema.getKindCode().equals("108")){
                        prpTitemKindSchema.setFlag(" 4");
                    }else if(prpTitemKindSchema.getKindCode().equals("120")
                            ||prpTitemKindSchema.getKindCode().equals("111")){
                        prpTitemKindSchema.setFlag(" 1");
                    }else if(prpTitemKindSchema.getKindCode().equals("308")
                            ||prpTitemKindSchema.getKindCode().equals("310")){
                        prpTitemKindSchema.setFlag(" 2");
                    }else if(prpTitemKindSchema.getKindCode().equals("121")){
                        prpTitemKindSchema.setFlag(" 1");
                    }
                    blPrpTitemKind.setArr(prpTitemKindSchema);
               }    
            }
        }else if(PolicyType.equals("27")){
            
            /*String fuQiFamilyNo="";
            String ziNvFamiliNo="";
            for(int w=0;w<blMainProposal.getBLPrpTinsured().getSize();w++){
                if(blMainProposal.getBLPrpTinsured().getArr(w).getInsuredIdentity().equals("10")){
                    fuQiFamilyNo=blMainProposal.getBLPrpTinsured().getArr(w).getSerialNo();
                }else if(blMainProposal.getBLPrpTinsured().getArr(w).getInsuredIdentity().equals("40")){
                    ziNvFamiliNo=blMainProposal.getBLPrpTinsured().getArr(w).getSerialNo();
                }
            }*/
            
            rationType=getRationType(grade,payType,PolicyType,"10");
            prpdration.query(" riskcode='3004' and  rationtype='"+rationType+"'");
            for(int j=0;j<prpdration.getSize();j++){
                if(prpdration.getArr(j).getKindCode().equals("111")||prpdration.getArr(j).getKindCode().equals("308")||prpdration.getArr(j).getKindCode().equals("120")){
                    PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
                    prpTitemKindSchema.setProposalNo(MainPolicyNo);
                    prpTitemKindSchema.setRiskCode("3004");
                    prpTitemKindSchema.setItemKindNo((blPrpTitemKind.getSize() + 1) + "");
                    
                    
                    
                    prpTitemKindSchema.setFamilyNo(wifeFamilyNo);
                    prpTitemKindSchema.setFamilyName(wifeName);
                    
                    prpTitemKindSchema.setKindCode(prpdration.getArr(j).getKindCode());
                    dbPrpDkind.getInfo("3004",prpdration.getArr(j).getKindCode());
                    String strKindName = dbPrpDkind.getKindCName();
                    blPrpDitem = new BLPrpDitem();
                    String strItemDetailName = blPrpDitem.translateCode("3004",prpdration.getArr(j).getItemCode(),true);
                    prpTitemKindSchema.setKindName(strKindName);
                    prpTitemKindSchema.setItemCode(prpdration.getArr(j).getItemCode());
                    prpTitemKindSchema.setItemDetailName(strItemDetailName);
                    prpTitemKindSchema.setStartDate(startDate);
                    prpTitemKindSchema.setStartHour("0");
                    prpTitemKindSchema.setEndDate(endDate);
                    prpTitemKindSchema.setEndHour("24");
                    /*if(prpTitemKindSchema.getKindCode().equals("101")
                       ||prpTitemKindSchema.getKindCode().equals("102")
                       ||prpTitemKindSchema.getKindCode().equals("103")
                       ||prpTitemKindSchema.getKindCode().equals("104")
                       ||prpTitemKindSchema.getKindCode().equals("105")
                       ||prpTitemKindSchema.getKindCode().equals("106")
                       ||prpTitemKindSchema.getKindCode().equals("107")
                       ||prpTitemKindSchema.getKindCode().equals("108")){
                       prpTitemKindSchema.setCalculateFlag("N"); 
                    }else{
                       prpTitemKindSchema.setCalculateFlag("Y");
                    }*/
                    
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
                    if(prpTitemKindSchema.getItemCode().equals("9999")||prpTitemKindSchema.getItemCode().equals("0003")||prpTitemKindSchema.getItemCode().equals("0001")){
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
                    }else if(prpTitemKindSchema.getKindCode().equals("101")
                            ||prpTitemKindSchema.getKindCode().equals("102")
                            ||prpTitemKindSchema.getKindCode().equals("103")
                            ||prpTitemKindSchema.getKindCode().equals("104")
                            ||prpTitemKindSchema.getKindCode().equals("105")
                            ||prpTitemKindSchema.getKindCode().equals("106")
                            ||prpTitemKindSchema.getKindCode().equals("107")
                            ||prpTitemKindSchema.getKindCode().equals("108")){
                        prpTitemKindSchema.setFlag(" 4");
                    }else if(prpTitemKindSchema.getKindCode().equals("120")
                            ||prpTitemKindSchema.getKindCode().equals("111")){
                        prpTitemKindSchema.setFlag(" 1");
                    }else if(prpTitemKindSchema.getKindCode().equals("308")
                            ||prpTitemKindSchema.getKindCode().equals("310")){
                        prpTitemKindSchema.setFlag(" 2");
                    }else if(prpTitemKindSchema.getKindCode().equals("121")){
                        prpTitemKindSchema.setFlag(" 1");
                    }
                    blPrpTitemKind.setArr(prpTitemKindSchema);
               }
                
            }
            rationType=getRationType(grade,payType,PolicyType,"40");
            prpdration.query("  riskcode='3004' and  rationtype='"+rationType+"'");
            for(int j=0;j<prpdration.getSize();j++){
                if(prpdration.getArr(j).getKindCode().equals("111")||prpdration.getArr(j).getKindCode().equals("308")||prpdration.getArr(j).getKindCode().equals("120")){
                    PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
                    prpTitemKindSchema.setProposalNo(MainPolicyNo);
                    prpTitemKindSchema.setRiskCode("3004");
                    prpTitemKindSchema.setItemKindNo((blPrpTitemKind.getSize() + 1) + "");
                    
                    
                    
                    prpTitemKindSchema.setFamilyNo(childFamilyNo);
                    prpTitemKindSchema.setFamilyName(childName);
                    
                    prpTitemKindSchema.setKindCode(prpdration.getArr(j).getKindCode());
                    dbPrpDkind.getInfo("3004",prpdration.getArr(j).getKindCode());
                    String strKindName = dbPrpDkind.getKindCName();
                    blPrpDitem = new BLPrpDitem();
                    String strItemDetailName = blPrpDitem.translateCode("3004",prpdration.getArr(j).getItemCode(),true);
                    prpTitemKindSchema.setKindName(strKindName);
                    prpTitemKindSchema.setItemCode(prpdration.getArr(j).getItemCode());
                    prpTitemKindSchema.setItemDetailName(strItemDetailName);
                    prpTitemKindSchema.setStartDate(startDate);
                    prpTitemKindSchema.setStartHour("0");
                    prpTitemKindSchema.setEndDate(endDate);
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
                    if(prpTitemKindSchema.getItemCode().equals("9999")||prpTitemKindSchema.getItemCode().equals("0003")||prpTitemKindSchema.getItemCode().equals("0001")){
                       prpTitemKindSchema.setShortRateFlag("1");
                    }else{
                    	
                    	
                        
                    	unitAmount = Str.round(Double.parseDouble(prpdration.getArr(j).getAmount())/boys,2);
                        prpTitemKindSchema.setUnitAmount(String.valueOf(unitAmount));
                        
                        prpTitemKindSchema.setQuantity(boys+"");
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
                    }else if(prpTitemKindSchema.getKindCode().equals("101")
                            ||prpTitemKindSchema.getKindCode().equals("102")
                            ||prpTitemKindSchema.getKindCode().equals("103")
                            ||prpTitemKindSchema.getKindCode().equals("104")
                            ||prpTitemKindSchema.getKindCode().equals("105")
                            ||prpTitemKindSchema.getKindCode().equals("106")
                            ||prpTitemKindSchema.getKindCode().equals("107")
                            ||prpTitemKindSchema.getKindCode().equals("108")){
                        prpTitemKindSchema.setFlag(" 4");
                    }else if(prpTitemKindSchema.getKindCode().equals("120")
                            ||prpTitemKindSchema.getKindCode().equals("111")){
                        prpTitemKindSchema.setFlag(" 1");
                    }else if(prpTitemKindSchema.getKindCode().equals("308")
                            ||prpTitemKindSchema.getKindCode().equals("310")){
                        prpTitemKindSchema.setFlag(" 2");
                    }else if(prpTitemKindSchema.getKindCode().equals("121")){
                        prpTitemKindSchema.setFlag(" 1");
                    }
                    blPrpTitemKind.setArr(prpTitemKindSchema);
               }    
            }
            
        }else if(PolicyType.equals("28")){
            rationType=getRationType(grade,payType,PolicyType,"40");
            prpdration.query(" riskcode='3004' and  rationtype='"+rationType+"'");
            for(int j=0;j<prpdration.getSize();j++){
                if(prpdration.getArr(j).getKindCode().equals("111")||prpdration.getArr(j).getKindCode().equals("308")||prpdration.getArr(j).getKindCode().equals("120")){
                    PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
                    prpTitemKindSchema.setProposalNo(MainPolicyNo);
                    prpTitemKindSchema.setRiskCode("3004");
                    prpTitemKindSchema.setItemKindNo((blPrpTitemKind.getSize() + 1) + "");
                    prpTitemKindSchema.setFamilyNo("3");
                    
                    prpTitemKindSchema.setFamilyName(childName);
                    
                    prpTitemKindSchema.setKindCode(prpdration.getArr(j).getKindCode());
                    dbPrpDkind.getInfo("3004",prpdration.getArr(j).getKindCode());
                    String strKindName = dbPrpDkind.getKindCName();
                    blPrpDitem = new BLPrpDitem();
                    String strItemDetailName = blPrpDitem.translateCode("3004",prpdration.getArr(j).getItemCode(),true);
                    prpTitemKindSchema.setKindName(strKindName);
                    prpTitemKindSchema.setItemCode(prpdration.getArr(j).getItemCode());
                    prpTitemKindSchema.setItemDetailName(strItemDetailName);
                    prpTitemKindSchema.setStartDate(startDate);
                    prpTitemKindSchema.setStartHour("0");
                    prpTitemKindSchema.setEndDate(endDate);
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
                    if(prpTitemKindSchema.getItemCode().equals("9999")||prpTitemKindSchema.getItemCode().equals("0003")||prpTitemKindSchema.getItemCode().equals("0001")){
                       prpTitemKindSchema.setShortRateFlag("1");
                    }else{
                    	
                    	
                        
                    	unitAmount = Str.round(Double.parseDouble(prpdration.getArr(j).getAmount())/boys,2);
                        prpTitemKindSchema.setUnitAmount(String.valueOf(unitAmount));
                        
                        prpTitemKindSchema.setQuantity(boys+"");
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
                    }else if(prpTitemKindSchema.getKindCode().equals("101")
                            ||prpTitemKindSchema.getKindCode().equals("102")
                            ||prpTitemKindSchema.getKindCode().equals("103")
                            ||prpTitemKindSchema.getKindCode().equals("104")
                            ||prpTitemKindSchema.getKindCode().equals("105")
                            ||prpTitemKindSchema.getKindCode().equals("106")
                            ||prpTitemKindSchema.getKindCode().equals("107")
                            ||prpTitemKindSchema.getKindCode().equals("108")){
                        prpTitemKindSchema.setFlag(" 4");
                    }else if(prpTitemKindSchema.getKindCode().equals("120")
                            ||prpTitemKindSchema.getKindCode().equals("111")){
                        prpTitemKindSchema.setFlag("11");
                    }else if(prpTitemKindSchema.getKindCode().equals("308")
                            ||prpTitemKindSchema.getKindCode().equals("310")){
                        prpTitemKindSchema.setFlag("22");
                    }else if(prpTitemKindSchema.getKindCode().equals("121")){
                        prpTitemKindSchema.setFlag(" 1");
                    }
                    blPrpTitemKind.setArr(prpTitemKindSchema);
               }    
            }
        }
        for(int k=0;k<blMainProposal.getBLPrpTinsured().getSize();k++){
            if(blMainProposal.getBLPrpTinsured().getArr(k).getPossessNature().equals("1"))
            {
                blMainProposal.getBLPrpTinsured().getArr(k).setPossessNature("");
        
            
            prpdration.query(" riskcode='3004' and  rationtype='99'");
            for(int j=0;j<prpdration.getSize();j++){
                if(prpdration.getArr(j).getKindCode().equals("121")){
                    PrpTitemKindSchema prpTitemKindSchema = new PrpTitemKindSchema();
                    prpTitemKindSchema.setProposalNo(MainPolicyNo);
                    prpTitemKindSchema.setRiskCode("3004");
                    prpTitemKindSchema.setItemKindNo((blPrpTitemKind.getSize() + 1) + "");
                    prpTitemKindSchema.setFamilyNo(blMainProposal.getBLPrpTinsured().getArr(k).getSerialNo());
                    
                    
                    prpTitemKindSchema.setFamilyName(blMainProposal.getBLPrpTinsured().getArr(k).getInsuredName());
                    
                    prpTitemKindSchema.setKindCode(prpdration.getArr(j).getKindCode());
                    dbPrpDkind.getInfo("3004",prpdration.getArr(j).getKindCode());
                    String strKindName = dbPrpDkind.getKindCName();
                    blPrpDitem = new BLPrpDitem();
                    String strItemDetailName = blPrpDitem.translateCode("3004",prpdration.getArr(j).getItemCode(),true);
                    prpTitemKindSchema.setKindName(strKindName);
                    prpTitemKindSchema.setItemCode(prpdration.getArr(j).getItemCode());
                    prpTitemKindSchema.setItemDetailName(strItemDetailName);
                    prpTitemKindSchema.setStartDate(startDate);
                    prpTitemKindSchema.setStartHour("0");
                    prpTitemKindSchema.setEndDate(endDate);
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
                    if(prpTitemKindSchema.getItemCode().equals("9999")||prpTitemKindSchema.getItemCode().equals("0003")||prpTitemKindSchema.getItemCode().equals("0001")){
                       prpTitemKindSchema.setShortRateFlag("1");
                    }else{
                        prpTitemKindSchema.setUnitAmount(prpdration.getArr(j).getAmount());
                        prpTitemKindSchema.setQuantity("1");
                        prpTitemKindSchema.setValue(1*muti+"");
                        prpTitemKindSchema.setDiscount("100.0000");
                        prpTitemKindSchema.setShortRateFlag("3");
                    }
                    prpTitemKindSchema.setAmount(Double.parseDouble(prpdration.getArr(j).getAmount())*muti+"");
                    prpTitemKindSchema.setRate(""+(1000.0*Double.parseDouble(prpdration.getArr(j).getRate())));
                    prpTitemKindSchema.setShortRate("100");
                    prpTitemKindSchema.setPremium(prpdration.getArr(j).getPremium());
                    if(prpTitemKindSchema.getCalculateFlag().equals("Y")){
                        sumAmount=sumAmount+Double.parseDouble(Double.parseDouble(prpdration.getArr(j).getAmount())*muti+"");
                    }
                    
                    sumPermium=sumPermium+Double.parseDouble(prpdration.getArr(j).getPremium());
                    if(prpTitemKindSchema.getKindCode().equals("001")){
                       prpTitemKindSchema.setFlag(" 3");
                    }else if(prpTitemKindSchema.getKindCode().equals("101")
                            ||prpTitemKindSchema.getKindCode().equals("102")
                            ||prpTitemKindSchema.getKindCode().equals("103")
                            ||prpTitemKindSchema.getKindCode().equals("104")
                            ||prpTitemKindSchema.getKindCode().equals("105")
                            ||prpTitemKindSchema.getKindCode().equals("106")
                            ||prpTitemKindSchema.getKindCode().equals("107")
                            ||prpTitemKindSchema.getKindCode().equals("108")){
                        prpTitemKindSchema.setFlag(" 4");
                    }else if(prpTitemKindSchema.getKindCode().equals("120")
                            ||prpTitemKindSchema.getKindCode().equals("111")){
                        prpTitemKindSchema.setFlag("11");
                    }else if(prpTitemKindSchema.getKindCode().equals("308")
                            ||prpTitemKindSchema.getKindCode().equals("310")){
                        prpTitemKindSchema.setFlag("22");
                    }else if(prpTitemKindSchema.getKindCode().equals("121")){
                        prpTitemKindSchema.setFlag(" 1");
                    }
                    blPrpTitemKind.setArr(prpTitemKindSchema);
               }    
            }
            
            }
        }
        
        permiumAmountSchema.setSumAmount(""+sumAmount);
        permiumAmountSchema.setSumPremium(""+sumPermium);
        blMainProposal.setBLPrpTitemKind(blPrpTitemKind);
    }
    
    public String getRationType(String BusinessGrade,String strPayType ,String PolicyType, String InsuredIdentity){
        String rationType="";

        if(BusinessGrade.equals("A")&&strPayType.equals("1")&&PolicyType.equals("25")&&(InsuredIdentity.equals("01")))
        {
          rationType = "1";    
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("1")&&PolicyType.equals("26")&&(InsuredIdentity.equals("01")))
        {
          rationType = "2";
        }
         else if(BusinessGrade.equals("A")&&strPayType.equals("1")&&PolicyType.equals("26")&&(InsuredIdentity.equals("10")))
        {
          rationType = "3";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("1")&&PolicyType.equals("27")&&(InsuredIdentity.equals("01")))
        {
          rationType = "4";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("1")&&PolicyType.equals("27")&&(InsuredIdentity.equals("10")))
        {
          rationType = "5";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("1")&&PolicyType.equals("27")&&(InsuredIdentity.equals("40")))
        {
          rationType = "6";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("1")&&PolicyType.equals("28")&&(InsuredIdentity.equals("01")))
        {
          rationType = "7";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("1")&&PolicyType.equals("28")&&(InsuredIdentity.equals("40")))
        {
          rationType = "8";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("3")&&PolicyType.equals("25")&&(InsuredIdentity.equals("01")))
        {
          rationType = "9";    
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("3")&&PolicyType.equals("26")&&(InsuredIdentity.equals("01")))
        {
          rationType = "10";
        }
         else if(BusinessGrade.equals("A")&&strPayType.equals("3")&&PolicyType.equals("26")&&(InsuredIdentity.equals("10")))
        {
          rationType = "11";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("3")&&PolicyType.equals("27")&&(InsuredIdentity.equals("01")))
        {
          rationType = "12";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("3")&&PolicyType.equals("27")&&(InsuredIdentity.equals("10")))
        {
          rationType = "13";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("3")&&PolicyType.equals("27")&&(InsuredIdentity.equals("40")))
        {
          rationType = "14";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("3")&&PolicyType.equals("28")&&(InsuredIdentity.equals("01")))
        {
          rationType = "15";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("3")&&PolicyType.equals("28")&&(InsuredIdentity.equals("40")))
        {
          rationType = "16";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("2")&&PolicyType.equals("25")&&(InsuredIdentity.equals("01")))
        {
          rationType = "17";    
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("2")&&PolicyType.equals("26")&&(InsuredIdentity.equals("01")))
        {
          rationType = "18";
        }
         else if(BusinessGrade.equals("A")&&strPayType.equals("2")&&PolicyType.equals("26")&&(InsuredIdentity.equals("10")))
        {
          rationType = "19";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("2")&&PolicyType.equals("27")&&(InsuredIdentity.equals("01")))
        {
          rationType = "20";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("2")&&PolicyType.equals("27")&&(InsuredIdentity.equals("10")))
        {
          rationType = "21";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("2")&&PolicyType.equals("27")&&(InsuredIdentity.equals("40")))
        {
          rationType = "22";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("2")&&PolicyType.equals("28")&&(InsuredIdentity.equals("01")))
        {
          rationType = "23";
        }
        else if(BusinessGrade.equals("A")&&strPayType.equals("2")&&PolicyType.equals("28")&&(InsuredIdentity.equals("40")))
        {
          rationType = "24";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("1")&&PolicyType.equals("25")&&(InsuredIdentity.equals("01")))
        {
          rationType = "25";    
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("1")&&PolicyType.equals("26")&&(InsuredIdentity.equals("01")))
        {
          rationType = "26";
        }
         else if(BusinessGrade.equals("B")&&strPayType.equals("1")&&PolicyType.equals("26")&&(InsuredIdentity.equals("10")))
        {
          rationType = "27";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("1")&&PolicyType.equals("27")&&(InsuredIdentity.equals("01")))
        {
          rationType = "28";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("1")&&PolicyType.equals("27")&&(InsuredIdentity.equals("10")))
        {
          rationType = "29";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("1")&&PolicyType.equals("27")&&(InsuredIdentity.equals("40")))
        {
          rationType = "30";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("1")&&PolicyType.equals("28")&&(InsuredIdentity.equals("01")))
        {
          rationType = "31";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("1")&&PolicyType.equals("28")&&(InsuredIdentity.equals("40")))
        {
          rationType = "32";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("3")&&PolicyType.equals("25")&&(InsuredIdentity.equals("01")))
        {
          rationType = "33";    
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("3")&&PolicyType.equals("26")&&(InsuredIdentity.equals("01")))
        {
          rationType = "34";
        }
         else if(BusinessGrade.equals("B")&&strPayType.equals("3")&&PolicyType.equals("26")&&(InsuredIdentity.equals("10")))
        {
          rationType = "35";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("3")&&PolicyType.equals("27")&&(InsuredIdentity.equals("01")))
        {
          rationType = "36";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("3")&&PolicyType.equals("27")&&(InsuredIdentity.equals("10")))
        {
          rationType = "37";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("3")&&PolicyType.equals("27")&&(InsuredIdentity.equals("40")))
        {
          rationType = "38";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("3")&&PolicyType.equals("28")&&(InsuredIdentity.equals("01")))
        {
          rationType = "39";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("3")&&PolicyType.equals("28")&&(InsuredIdentity.equals("40")))
        {
          rationType = "40";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("2")&&PolicyType.equals("25")&&(InsuredIdentity.equals("01")))
        {
          rationType = "41";    
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("2")&&PolicyType.equals("26")&&(InsuredIdentity.equals("01")))
        {
          rationType = "42";
        }
         else if(BusinessGrade.equals("B")&&strPayType.equals("2")&&PolicyType.equals("26")&&(InsuredIdentity.equals("10")))
        {
          rationType = "43";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("2")&&PolicyType.equals("27")&&(InsuredIdentity.equals("01")))
        {
          rationType = "44";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("2")&&PolicyType.equals("27")&&(InsuredIdentity.equals("10")))
        {
          rationType = "45";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("2")&&PolicyType.equals("27")&&(InsuredIdentity.equals("40")))
        {
          rationType = "46";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("2")&&PolicyType.equals("28")&&(InsuredIdentity.equals("01")))
        {
          rationType = "47";
        }
        else if(BusinessGrade.equals("B")&&strPayType.equals("2")&&PolicyType.equals("28")&&(InsuredIdentity.equals("40")))
        {
          rationType = "48";
        }

        else if(InsuredIdentity.equals("50"))
        {
          rationType = "99";
        }
        return rationType;

    }
    /*
     * @param blPrpBillCoreCard快钱业务类
     * @param billCoreCardList快钱结果集数组
     * @bizType 业务类型(TXX单)
     * @bizNo   业务号
     * @author zhengxiaoluo 20090512
     */
    public void transBillCoreCard(BLPrpBillCoreCard blPrpBillCoreCard,ArrayList billCoreCardList,String bizType,String bizNo) throws Exception{
    	ArrayList item = (ArrayList)billCoreCardList.get(0);
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
