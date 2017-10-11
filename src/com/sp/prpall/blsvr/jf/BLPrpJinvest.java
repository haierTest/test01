package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.account.blsvr.BLAccBookBranch;
import com.sp.account.blsvr.BLAccMainVoucher;
import com.sp.account.blsvr.BLAccMonthTrace;
import com.sp.account.blsvr.BLAccSubVoucher;
import com.sp.account.blsvr.BLAccVoucher;
import com.sp.account.dbsvr.DBAccBankAccount;
import com.sp.account.schema.AccMainVoucherSchema;
import com.sp.account.schema.AccSubVoucherSchema;
import com.sp.platform.dto.domain.BankInterFace_DetailDto;
import com.sp.platform.dto.domain.PrpDagentDto;
import com.sp.platform.resource.dtofactory.domain.DBPrpDagent;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPrpCplan;
import com.sp.prpall.blsvr.misc.BLPrpCommission;
import com.sp.prpall.blsvr.pg.BLPrpPfee;
import com.sp.prpall.blsvr.pg.BLPrpPplan;
import com.sp.prpall.dbsvr.cb.DBPrpCinsured;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.cb.DBPrpCmainInvest;
import com.sp.prpall.dbsvr.cb.DBPrpCplan;
import com.sp.prpall.dbsvr.cb.DBPrpCrenewal;
import com.sp.prpall.dbsvr.jf.DBPrpJinvest;
import com.sp.prpall.dbsvr.jf.DBPrpJinvestIncome;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefDetail;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefVoucher;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;
import com.sp.prpall.dbsvr.pg.DBPrpPmainInvest;
import com.sp.prpall.dbsvr.pg.DBPrpPplan;
import com.sp.prpall.pubfun.Bill;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpCinsuredSchema;
import com.sp.prpall.schema.PrpCmainInvestSchema;
import com.sp.prpall.schema.PrpCmainSchema;
import com.sp.prpall.schema.PrpJinvestIncomeSchema;
import com.sp.prpall.schema.PrpJinvestSchema;
import com.sp.prpall.schema.PrpJpayRefMainSchema;
import com.sp.prpall.schema.PrpJpayRefRecSchema;
import com.sp.prpall.schema.PrpJplanCommissionSchema;
import com.sp.prpall.schema.PrpJplanFeeSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDcompany;
import com.sp.utiall.dbsvr.DBPrpDcode;
import com.sp.utiall.dbsvr.DBPrpDuser;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Date;
import com.sp.utility.string.Str;

/**
 * 定义PrpJinvest的BL类
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-08-04</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJinvest{
    private Vector schemas = new Vector();
    private BLAccMainVoucher blAccMainVoucher = new BLAccMainVoucher();
    private BLPrpJpayRefDetail blPrpJpayRefDetail = new BLPrpJpayRefDetail();
	protected final Log logger = LogFactory.getLog(getClass());
    /**
     * 构造函数
     */
    public BLPrpJinvest(){
    }

    /**
     *初始化记录
     *@param 无
     *@return 无
     *@throws Exception
     */
    public void initArr() throws Exception
    {
       schemas = new Vector();
     }
    /**
     *增加一条PrpJinvestSchema记录
     *@param iPrpJinvestSchema PrpJinvestSchema
     *@throws Exception
     */
    public void setArr(PrpJinvestSchema iPrpJinvestSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJinvestSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpJinvestSchema记录
     *@param index 下标
     *@return 一个PrpJinvestSchema对象
     *@throws Exception
     */
    public PrpJinvestSchema getArr(int index) throws Exception
    {
     PrpJinvestSchema prpJinvestSchema = null;
       try
       {
        prpJinvestSchema = (PrpJinvestSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJinvestSchema;
     }
    /**
     *删除一条PrpJinvestSchema记录
     *@param index 下标
     *@throws Exception
     */
    public void remove(int index) throws Exception
    {
       try
       {
         this.schemas.remove(index);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到schemas记录数
     *@return schemas记录数
     *@throws Exception
     */
    public int getSize() throws Exception
    {
        return this.schemas.size();
    }
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart) throws UserException,Exception
    {
       this.query(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJinvest dbPrpJinvest = new DBPrpJinvest();
      if (iLimitCount > 0 && dbPrpJinvest.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJinvest.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJinvest WHERE " + iWherePart;
        schemas = dbPrpJinvest.findByConditions(strSqlStatement);
      }
    }
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJinvest dbPrpJinvest = new DBPrpJinvest();
      if (iLimitCount > 0 && dbPrpJinvest.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJinvest.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJinvest WHERE " + iWherePart;
        schemas = dbPrpJinvest.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJinvest dbPrpJinvest = new DBPrpJinvest();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJinvest.setSchema((PrpJinvestSchema)schemas.get(i));
      dbPrpJinvest.insert(dbpool);
      }
    }

    /**
     *不带dbpool的XXXXX存方法
     *@param 无
     *@return 无
     */
    public void save() throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        save(dbpool);
        dbpool.commitTransaction();
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally {
        dbpool.close();
      }
    }
    /**
     * @author xushaojiang 20071113 富安居退XXXXX送prpjinvest、prpjinvestincome表数据
     * @param iCertiType 业务类型
     * @param iCertiNo 业务号
     * @throws UserException
     * @throws Exception
     */
    
    public void transData(String iCertiType,String iCertiNo) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
      try 
      {
    	  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          dbpool.beginTransaction();
          this.transData(dbpool, iCertiType, iCertiNo);
          dbpool.commitTransaction();
      }
      catch(UserException ue)
      {
    	  dbpool.rollbackTransaction();
    	  throw ue;
      }
      catch(Exception e)
      {
          dbpool.rollbackTransaction();
          throw e;
      }
      finally {
          dbpool.close();
      }
    }
    
    /**
     * @author zhanglingjian 20080806 中转接口
     * @param dbpool 数据库连接池
     * @param iCertiType 业务类型
     * @param iCertiNo 业务号
     * @throws UserException
     * @throws Exception
     */
    
    public void transData(DbPool dbpool,String iCertiType,String iCertiNo) throws UserException,Exception
    {
      
      if(iCertiType.equals("P"))
        this.transPolicy(dbpool,iCertiType,iCertiNo);
      
      else if(iCertiType.equals("E"))
      	this.transEndor(dbpool,iCertiType,iCertiNo);
      else
        throw new UserException(-98,-1167,"BLPrpJinvest.transData","没有此业务类型："+iCertiType);
    }
    
    /**
     * @author zhanglingjian 20080806 富安居生成XX接口
     * @param dbpool 数据库连接池
     * @param iCertiType 业务类型
     * @param iCertiNo 业务号
     * @throws UserException
     * @throws Exception
     */
    
    public void transPolicy(DbPool dbpool,String iCertiType,String iPolicyNo) throws UserException,Exception
    {
    	BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
    	DBPrpCmain dbPrpCmain = new DBPrpCmain();
    	DBPrpCrenewal dbPrpCrenewal = new DBPrpCrenewal();
    	DBPrpJinvest dbPrpJinvest = new DBPrpJinvest();
    	DBPrpCmainInvest dbPrpCmainInvest = new DBPrpCmainInvest();
    	DBPrpCinsured dbPrpCinsured = new DBPrpCinsured();
    	PrpJinvestSchema prpJinvestSchema = new PrpJinvestSchema();
    	
    	int intReturn = dbPrpCmain.getInfo(dbpool,iPolicyNo);
        if(intReturn == 100)
        	throw new UserException( -98, -1167, "BLPrpJinvest.transPolicy", "无此XX信息："+iPolicyNo);
        intReturn = dbPrpCmainInvest.getInfo(dbpool, iPolicyNo);
        if(intReturn == 100)
        	throw new UserException( -98, -1167, "BLPrpJinvest.transPolicy", "此XX无本金信息："+iPolicyNo);
        intReturn = dbPrpCinsured.getInfo(dbpool, iPolicyNo, "1");
        if(intReturn == 100)
        	throw new UserException( -98, -1167, "BLPrpJinvest.transPolicy", "此XX无被XX人信息："+iPolicyNo);
        
        
        String strCenterCode = blPrpDcompany.getCenterCodeNew(dbpool,dbPrpCmain.getComCode());
        if(strCenterCode.equals("")||strCenterCode.trim().length()==0){
        	throw new UserException( -98, -1167, "BLPrpJinvest.transPolicy", "核算单位取值失败！");
        }
        
        
        DateTime currentDate = new DateTime().current();
        DateTime investDate = new DateTime();
        String strInvestDate = "";
        String strUnderWriteEndDate = dbPrpCmain.getUnderWriteEndDate();
        if(strUnderWriteEndDate.trim().length() == 0) {
            strUnderWriteEndDate = new Integer(currentDate.getYear()).toString() + "-"
            + new Integer(currentDate.getMonth()).toString() + "-"
            + new Integer(currentDate.getDay()).toString();
        }
        if(dbPrpCmain.getStartDate().trim().length() > 0) {
            investDate = new DateTime(dbPrpCmain.getStartDate()).addDay(-1);
            strInvestDate = new Integer(investDate.getYear()).toString() + "-"
                          + new Integer(investDate.getMonth()).toString() + "-"
                          + new Integer(investDate.getDay()).toString();
        }
        
        
        DBManager dbManager= new DBManager();
		dbManager = dbpool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);
		DBPrpDagent dbPrpDagent = new DBPrpDagent(dbManager);
		PrpDagentDto prpDagentDto = dbPrpDagent.findByPrimaryKey(dbPrpCmain.getAgentCode());
		if (prpDagentDto==null){
			throw new UserException(-98, -1167,
					"BLPrpJpayInvest.transData", "XX：" + iPolicyNo
							+ "的代理银行没有在平台系统做配置！");
		}else if(prpDagentDto.getAgentBankCode()==null||prpDagentDto.getAgentBankCode().equals("")){
			throw new UserException(-98, -1167,
					"BLPrpJpayInvest.transData", "XX：" + iPolicyNo
							+ "的代理银行没有在平台系统做“银行名称”的配置！");
		}
        
        
        if(dbPrpCmain.getOthFlag() != null && dbPrpCmain.getOthFlag().substring(0, 1).equals("1")){
        	intReturn = dbPrpCrenewal.getInfo(dbpool, iPolicyNo);
        	if(intReturn == 100){
            	throw new UserException( -98, -1167, "BLPrpJinvest.transPolicy", 
            			"续XXXXXXX"+iPolicyNo+"在PrpCrenewal了中没有关联信息");
        	}
        	dbPrpJinvest.getInfo(dbpool, dbPrpCrenewal.getOldPolicyNo(), "0");
        	dbPrpJinvest.setReNewalFlag("1");
        	dbPrpJinvest.setReNewalPolicyNo(iPolicyNo);
        	dbPrpJinvest.setReNewalPayment(dbPrpCmainInvest.getInvestment());
        	dbPrpJinvest.update(dbpool);
        	
            prpJinvestSchema.setProcSeq(dbPrpCmain.getPolicyNo()); 
            prpJinvestSchema.setCertiNo(dbPrpCmain.getPolicyNo()); 
            prpJinvestSchema.setCertiType("P"); 
            prpJinvestSchema.setPolicyNo(dbPrpCmain.getPolicyNo()); 
            prpJinvestSchema.setSerialNo("0"); 
            prpJinvestSchema.setPayRefReason("R01"); 
            prpJinvestSchema.setClassCode(dbPrpCmain.getClassCode()); 
            prpJinvestSchema.setRiskCode(dbPrpCmain.getRiskCode()); 
            prpJinvestSchema.setContractNo(dbPrpCmain.getContractNo()); 
            prpJinvestSchema.setAppliCode(dbPrpCmain.getAppliCode()); 
            prpJinvestSchema.setAppIdno(dbPrpCinsured.getIdentifyNumber()); 
            prpJinvestSchema.setAppliName(dbPrpCmain.getAppliName()); 
            prpJinvestSchema.setInsuredCode(dbPrpCmain.getInsuredCode()); 
            prpJinvestSchema.setInsuredName(dbPrpCmain.getInsuredName()); 
            prpJinvestSchema.setStartDate(dbPrpCmain.getStartDate()); 
            prpJinvestSchema.setEndDate(dbPrpCmain.getEndDate()); 
            prpJinvestSchema.setCurrency1("CNY"); 
            prpJinvestSchema.setPlanFee(dbPrpCmainInvest.getInvestment()); 
            prpJinvestSchema.setInvestCount(dbPrpCmainInvest.getQuantity()); 
            prpJinvestSchema.setLeftInvestMent(dbPrpCmainInvest.getInvestment());
            prpJinvestSchema.setLeftQuantity(dbPrpCmainInvest.getQuantity()); 
            prpJinvestSchema.setComCode(dbPrpCmain.getComCode()); 
            prpJinvestSchema.setMakeCom(dbPrpCmain.getMakeCom()); 
            prpJinvestSchema.setAgentCode(dbPrpCmain.getAgentCode()); 
            prpJinvestSchema.setHandler1Code(dbPrpCmain.getHandler1Code()); 
            prpJinvestSchema.setHandlerCode(dbPrpCmain.getHandlerCode()); 
            prpJinvestSchema.setUnderWriteDate(strUnderWriteEndDate); 
            prpJinvestSchema.setIncometimes("0"); 
            prpJinvestSchema.setIncomeFlag("0"); 
            prpJinvestSchema.setPayBankCode(prpDagentDto.getAgentBankCode()); 
            prpJinvestSchema.setPayAccountNo(dbPrpCinsured.getAccount()); 
            prpJinvestSchema.setInvestDate(strInvestDate); 
            prpJinvestSchema.setExchangeRate("1"); 
            prpJinvestSchema.setPlanFeeCNY(dbPrpCmainInvest.getInvestment()); 
            prpJinvestSchema.setProposalNo(dbPrpCmain.getProposalNo()); 
            prpJinvestSchema.setBaseIncomeRate(dbPrpCmainInvest.getInterestRate()); 
            prpJinvestSchema.setFlag("E"); 
            prpJinvestSchema.setPaymentFlag("00");
            prpJinvestSchema.setCenterCode(strCenterCode);
            prpJinvestSchema.setBusinessNature(dbPrpCmain.getBusinessNature());
            
            prpJinvestSchema.setChannelType(dbPrpCmain.getChannelType());
            
            prpJinvestSchema.setToCenterCode(dbPrpJinvest.getToCenterCode());
            prpJinvestSchema.setToComCode(dbPrpJinvest.getToComCode());
            prpJinvestSchema.setReNewalFlag("2");
            prpJinvestSchema.setReNewalPolicyNo("");
            prpJinvestSchema.setReNewalPayment("0");
            this.setArr(prpJinvestSchema);
        	
        }else{
        	throw new UserException( -98, -1167, "BLPrpJinvest.transPolicy", 
        			"XX"+iPolicyNo+"为普通XX，此接口暂时没有开发，请联系管理员！");
        }
        this.save(dbpool);
    	
    }

    /**
     * @author xushaojiang 20071113 富安居退XXXXX送prpjinvest、prpjinvestincome表数据
     * @param dbpool 数据库连接池
     * @param iCertiType 业务类型
     * @param iCertiNo 业务号
     * @throws UserException
     * @throws Exception
     */
    
    public void transEndor(DbPool dbpool,String iCertiType,String iCertiNo) throws UserException,Exception
    {
    	DBPrpPmainInvest dbPrpPmainInvest = new DBPrpPmainInvest();
    	DBPrpCmain dbPrpCmain = new DBPrpCmain();
    	DBPrpJinvest dbPrpJinvest = new DBPrpJinvest();
    	DBPrpPhead dbPrpPhead = new DBPrpPhead();
    	BLPrpJinvestIncome blPrpJinvestIncome = new BLPrpJinvestIncome();
    	PrpJinvestSchema  PrpJinvestSchemaP02 = new PrpJinvestSchema();           
    	PrpJinvestSchema  PrpJinvestSchemaP01 = new PrpJinvestSchema();           
    	PrpJinvestSchema  PrpJinvestSchemaR04 = new PrpJinvestSchema();           
    	int intReturn =0;
    	
    	StringBuffer updateBuffer = null  ;  
    	
    	
    	intReturn = dbPrpPhead.getInfo(dbpool, iCertiNo);
    	if (intReturn==100)
			throw new UserException(-98, -1167, "BLPrpJjnvest.transData",
					"PrpPhead表中没有批单"+iCertiNo+"对应XX数据" );  
		
    	if(dbPrpPhead.getEndorType().indexOf("60")>=0){
    		
    		DBPrpCinsured dbPrpCinsured = new DBPrpCinsured();
    		intReturn = dbPrpCinsured.getInfo(dbpool, dbPrpPhead.getPolicyNo(),"1");
        	if (intReturn==100)
    			throw new UserException(-98, -1167, "BLPrpJjnvest.transData",
    					"PrpCinsured表中没有批单"+iCertiNo+"对应XXXX人数据" );  
        	if (!dbPrpCinsured.getInsuredFlag().equals("2"))
    			throw new UserException(-98, -1167, "BLPrpJjnvest.transData",
    					"PrpCinsured表中XX"+dbPrpCinsured.getPolicyNo()+"的Serialno和InsuredFlag对应关系不对" ); 
        	
        	
        	updateBuffer = new StringBuffer();
        	updateBuffer.append(" Update PrpjInvest set  ");
        	updateBuffer.append(" AppliName ='"+dbPrpCinsured.getInsuredName() + "', ");
        	updateBuffer.append(" AppidNo ='"+dbPrpCinsured.getIdentifyNumber()+ "', ");
        	updateBuffer.append(" PayAccountNo ='"+dbPrpCinsured.getAccount()+ "'    ");
        	updateBuffer.append(" Where PolicyNo ='"+dbPrpCinsured.getPolicyNo()+ "' ");
        	dbpool.update(updateBuffer.toString());
        	
        	
        	updateBuffer = new StringBuffer();        	
        	updateBuffer.append(" Update PrpJplanFee set  ");
        	updateBuffer.append(" Appliname ='"+dbPrpCinsured.getInsuredName() + "'  ");
        	updateBuffer.append(" Where PolicyNo ='"+dbPrpCinsured.getPolicyNo()+ "' ");
        	dbpool.update(updateBuffer.toString());
        	
        	
        	updateBuffer = new StringBuffer();
        	updateBuffer.append(" Update PrpjPayRefRec set  ");
        	updateBuffer.append(" Appliname ='"+dbPrpCinsured.getInsuredName() + "'  ");
        	updateBuffer.append(" Where PolicyNo ='"+dbPrpCinsured.getPolicyNo()+ "' ");
        	dbpool.update(updateBuffer.toString());
        	
    	}
		
    	if(dbPrpPhead.getEndorType().indexOf("04")>=0){
    		      	
    		DBPrpCinsured dbPrpCinsured = new DBPrpCinsured();
    		intReturn = dbPrpCinsured.getInfo(dbpool, dbPrpPhead.getPolicyNo(),"2");
        	if (intReturn==100)
    			throw new UserException(-98, -1167, "BLPrpJjnvest.transData",
    					"PrpCinsured表中没有批单"+iCertiNo+"对应XX被XX人数据" );  
        	if (!dbPrpCinsured.getInsuredFlag().equals("1"))
    			throw new UserException(-98, -1167, "BLPrpJjnvest.transData",
    					"PrpCinsured表中XX"+dbPrpCinsured.getPolicyNo()+"的Serialno和InsuredFlag对应关系不对" );  
        	
        	
        	updateBuffer = new StringBuffer();
        	updateBuffer.append(" Update PrpjInvest set  ");
        	updateBuffer.append(" InsuredName ='"+dbPrpCinsured.getInsuredName() + "' ");
        	updateBuffer.append(" Where PolicyNo ='"+dbPrpCinsured.getPolicyNo()+ "'  ");
        	dbpool.update(updateBuffer.toString());
        	
        	
        	updateBuffer = new StringBuffer();
        	updateBuffer.append(" Update PrpjInvestIncome set  ");
        	updateBuffer.append(" InsuredName ='"+dbPrpCinsured.getInsuredName() + "' ");
        	updateBuffer.append(" Where PolicyNo ='"+dbPrpCinsured.getPolicyNo()+ "'  ");
        	dbpool.update(updateBuffer.toString());
        	
        	
        	updateBuffer = new StringBuffer();
        	updateBuffer.append(" Update PrpJplanFee set  ");
        	updateBuffer.append(" InsuredName ='"+dbPrpCinsured.getInsuredName() + "' ");
        	updateBuffer.append(" Where PolicyNo ='"+dbPrpCinsured.getPolicyNo()+ "'  ");
        	dbpool.update(updateBuffer.toString());
        	
        	
        	updateBuffer = new StringBuffer();
        	updateBuffer.append(" Update PrpjPayRefRec set  ");
        	updateBuffer.append(" InsuredName ='"+dbPrpCinsured.getInsuredName() + "' ");
        	updateBuffer.append(" Where PolicyNo ='"+dbPrpCinsured.getPolicyNo()+ "'  ");
        	dbpool.update(updateBuffer.toString());
        	
    	}
		
    	if (dbPrpPhead.getEndorType().indexOf("21")>=0){

        	
        	intReturn = dbPrpPmainInvest.getInfo(dbpool, iCertiNo);
        	if (intReturn==100)
    			throw new UserException(-98, -1167, "BLPrpJjnvest.transData",
    					"PrpPmainInvest表中没有批单"+iCertiNo+"对应数据" );  
        	
        	
        	intReturn = dbPrpCmain.getInfo(dbpool, dbPrpPmainInvest.getPolicyNo());
        	if (intReturn==100)
    			throw new UserException(-98, -1167, "BLPrpJjnvest.transData",
    					"PrpCmain表中没有批单"+iCertiNo+"对应XX数据" );   
        	
        	
        	intReturn = dbPrpJinvest.getInfo(dbpool,dbPrpPmainInvest.getPolicyNo(), "0");
        	if (intReturn==100)
    			throw new UserException(-98, -1167, "BLPrpJjnvest.transData",
    					"PrpJinvest表中没有XX"+dbPrpPmainInvest.getPolicyNo()+"对应数据" );
        	
        	
        	if(dbPrpJinvest.getReNewalFlag() != null && dbPrpJinvest.getReNewalFlag().equals("1")){
        		throw new UserException(-98, -1167, "BLPrpJjnvest.transData",
    					"续XXXXX原XX"+dbPrpJinvest.getPolicyNo()+"不允许做退XXXXX" );
        	}
        	

        	
        	PrpJinvestSchemaP02=this.PrpJinvestTransToP02(dbpool,dbPrpCmain,dbPrpPhead,dbPrpPmainInvest,iCertiType,iCertiNo);
        	this.setArr(PrpJinvestSchemaP02);
        	
        	PrpJinvestSchemaP01=this.PrpJinvestTransToP02(dbpool,dbPrpCmain,dbPrpPhead,dbPrpPmainInvest,iCertiType,iCertiNo);
        	PrpJinvestSchemaP01.setSerialNo("2");
        	PrpJinvestSchemaP01.setPayRefReason("P01");
        	PrpJinvestSchemaP01.setPlanFee(PrpJinvestSchemaP01.getIncomeFee());
        	PrpJinvestSchemaP01.setFlag("TBSY");
        	this.setArr(PrpJinvestSchemaP01);
        	
        	PrpJinvestSchemaR04 = this.PrpJinvestTransToP02(dbpool,dbPrpCmain,dbPrpPhead,dbPrpPmainInvest,iCertiType,iCertiNo);
        	PrpJinvestSchemaR04.setSerialNo("3");
        	PrpJinvestSchemaR04.setPayRefReason("R04");
        	PrpJinvestSchemaR04.setPlanFee(PrpJinvestSchemaP01.getFellBackFee());
        	PrpJinvestSchemaR04.setFlag("TBWYJ");
        	this.setArr(PrpJinvestSchemaR04);

        	
        	String strhasincomeFee = dbPrpJinvest.getSumIncomeFee();
        	dbPrpJinvest.setIncometimes(PrpJinvestSchemaP01.getIncometimes());
        	dbPrpJinvest.setSumIncomeFee(PrpJinvestSchemaP01.getPlanFee());
        	dbPrpJinvest.setEndDate(dbPrpCmain.getEndDate());
        	dbPrpJinvest.setIncomeDate(dbPrpCmain.getEndDate());
        	dbPrpJinvest.setFlag("TB");
        	dbPrpJinvest.update(dbpool);
        	
        	
        	dbPrpJinvest.setIncomeFee(strhasincomeFee); 
        	blPrpJinvestIncome = blPrpJinvestIncome.drawIncomeData(dbpool, dbPrpJinvest);
        	blPrpJinvestIncome.save(dbpool);
        	this.save(dbpool);
    	}
    	
    	
    	if(dbPrpPhead.getEndorType().indexOf("73") >= 0)
    	{
    		this.transEndorQuantityData(dbpool,iCertiType,iCertiNo);
    	}
    	
    	
    	if(dbPrpPhead.getEndorType().indexOf("19") >= 0)
    	{
    		this.transCancelReNewal(dbpool,iCertiType,dbPrpPhead.getPolicyNo());
    	}
    	
    }
    
    /**
     * @author zhanglingjian 20080613 富安居退XXXXX送prpjinvest、prpjinvestincome表数据
     * @param dbpool 数据库连接池
     * @param iCertiType 业务类型
     * @param iCertiNo 业务号
     * @throws UserException
     * @throws Exception
     */
    
    public void transEndorQuantityData(DbPool dbpool,String iCertiType,String iCertiNo) throws UserException,Exception
    {
    	DBPrpPmainInvest dbPrpPmainInvest = new DBPrpPmainInvest();
    	DBPrpCmain dbPrpCmain = new DBPrpCmain();
    	DBPrpJinvest dbPrpJinvest = new DBPrpJinvest();
    	DBPrpPhead dbPrpPhead = new DBPrpPhead();
    	BLPrpJinvest blPrpJinvest = new BLPrpJinvest();
    	BLPrpJinvestIncome blPrpJinvestIncome = new BLPrpJinvestIncome();
    	PrpJinvestSchema  PrpJinvestSchemaP02 = new PrpJinvestSchema();           
    	PrpJinvestSchema  PrpJinvestSchemaP01 = new PrpJinvestSchema();           
    	PrpJinvestSchema  PrpJinvestSchemaR04 = new PrpJinvestSchema();           
    	int intReturn =0;
    	
    	
    	intReturn = dbPrpPhead.getInfo(dbpool, iCertiNo);
    	if (intReturn==100)
			throw new UserException(-98, -1167, "BLPrpJjnvest.transData",
					"PrpPhead表中没有批单"+iCertiNo+"对应XX数据" );
		
    	if (dbPrpPhead.getEndorType().indexOf("73") >= 0){

        	
        	intReturn = dbPrpPmainInvest.getInfo(dbpool, iCertiNo);
        	if (intReturn==100)
    			throw new UserException(-98, -1167, "BLPrpJjnvest.transData",
    					"PrpPmainInvest表中没有批单"+iCertiNo+"对应数据" );  
        	
        	
        	intReturn = dbPrpCmain.getInfo(dbpool, dbPrpPmainInvest.getPolicyNo());
        	if (intReturn==100)
    			throw new UserException(-98, -1167, "BLPrpJjnvest.transData",
    					"PrpCmain表中没有批单"+iCertiNo+"对应XX数据" );   
        	
        	
        	intReturn = dbPrpJinvest.getInfo(dbpool,dbPrpPmainInvest.getPolicyNo(), "0");
        	if (intReturn==100)
    			throw new UserException(-98, -1167, "BLPrpJjnvest.transData",
    					"PrpJinvest表中没有XX"+dbPrpPmainInvest.getPolicyNo()+"对应数据" );  
        	
        	
        	if(dbPrpJinvest.getReNewalFlag() != null && dbPrpJinvest.getReNewalFlag().equals("1")){
        		throw new UserException(-98, -1167, "BLPrpJjnvest.transData",
    					"续XXXXX原XX"+dbPrpJinvest.getPolicyNo()+"不允许做退XXXXX" );
        	}
        	

        	
        	PrpJinvestSchemaP02 = blPrpJinvest.PrpJinvestTransToP02(dbpool,dbPrpCmain,dbPrpPhead,dbPrpPmainInvest,iCertiType,iCertiNo);
        	blPrpJinvest.setArr(PrpJinvestSchemaP02);
        	
        	PrpJinvestSchemaP01 = blPrpJinvest.PrpJinvestTransToP02(dbpool,dbPrpCmain,dbPrpPhead,dbPrpPmainInvest,iCertiType,iCertiNo);
        	PrpJinvestSchemaP01.setSerialNo("2");
        	PrpJinvestSchemaP01.setPayRefReason("P01");
        	PrpJinvestSchemaP01.setPlanFee(PrpJinvestSchemaP01.getIncomeFee());
        	PrpJinvestSchemaP01.setFlag("TBSY");
        	blPrpJinvest.setArr(PrpJinvestSchemaP01);
        	
        	PrpJinvestSchemaR04 = blPrpJinvest.PrpJinvestTransToP02(dbpool,dbPrpCmain,dbPrpPhead,dbPrpPmainInvest,iCertiType,iCertiNo);
        	PrpJinvestSchemaR04.setSerialNo("3");
        	PrpJinvestSchemaR04.setPayRefReason("R04");
        	PrpJinvestSchemaR04.setPlanFee(PrpJinvestSchemaP01.getFellBackFee());
        	PrpJinvestSchemaR04.setFlag("TBWYJ");
        	blPrpJinvest.setArr(PrpJinvestSchemaR04);

        	
        	double strhasincomeFee = 0;
        	if(dbPrpJinvest.getSumIncomeFee() != null && !dbPrpJinvest.getSumIncomeFee().equals(""))
        	{
        		strhasincomeFee = Double.parseDouble(dbPrpJinvest.getSumIncomeFee());
        	}
        	
        	double dbThisTimeIncomeFee = strhasincomeFee*(Math.abs(Double.parseDouble(dbPrpPmainInvest.getChgQuantity()))
        			/Double.parseDouble(dbPrpJinvest.getLeftQuantity()));
        	
        	
        	dbPrpJinvest.setLeftInvestMent("" + (Double.parseDouble(dbPrpJinvest.getLeftInvestMent())
        			- Math.abs(Double.parseDouble(dbPrpPmainInvest.getChgInvestment()))));
        	dbPrpJinvest.setLeftQuantity("" + (Integer.parseInt(dbPrpJinvest.getLeftQuantity())
        			- Math.abs(Integer.parseInt(dbPrpPmainInvest.getChgQuantity()))));
        	dbPrpJinvest.setIncometimes(PrpJinvestSchemaP01.getIncometimes());
        	dbPrpJinvest.setSumIncomeFee("" + (strhasincomeFee - dbThisTimeIncomeFee));
        	
        	if(dbPrpJinvest.getIncomeDate() == null || dbPrpJinvest.getIncomeDate().equals(""))
        	{
        		DateTime dateTime = new DateTime(dbPrpJinvest.getStartDate());
        		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        		Calendar c = Calendar.getInstance();
        		c.setTimeInMillis(dateTime.getTime()-1*24*60*60*1000);
        		dbPrpJinvest.setIncomeDate("" + df.format(c.getTime()));
        	}
        	
        	
        	
        	
        	dbPrpJinvest.update(dbpool);
        	
        	
    		com.sp.utility.string.Date sinosoftDate = new com.sp.utility.string.Date();
    	    String strOperatorDate = new ChgDate().toFormat(sinosoftDate.getString(sinosoftDate.YEAR+sinosoftDate.MONTH+sinosoftDate.DATE));
        	PrpJinvestIncomeSchema iPrpJinvestIncomeSchemaJT = new PrpJinvestIncomeSchema();
    	    iPrpJinvestIncomeSchemaJT = blPrpJinvestIncome.evaluate(dbPrpJinvest);
    	    iPrpJinvestIncomeSchemaJT.setPayRefReason("R02");
    	    iPrpJinvestIncomeSchemaJT.setIncomeStartDate(dbPrpJinvest.getStartDate());
    	    iPrpJinvestIncomeSchemaJT.setIncomeEndDate(strOperatorDate);
    	    iPrpJinvestIncomeSchemaJT.setIncomeFee(PrpJinvestSchemaP01.getIncomeFee());
    	    iPrpJinvestIncomeSchemaJT.setCurrency2(dbPrpJinvest.getCurrency1());
    	    iPrpJinvestIncomeSchemaJT.setExchangeRate("1");
    	    iPrpJinvestIncomeSchemaJT.setIncomeFeeCny("0");
    		iPrpJinvestIncomeSchemaJT.setOperatorCode(dbPrpJinvest.getHandlerCode());
    		iPrpJinvestIncomeSchemaJT.setOperatorDate(strOperatorDate);
    		blPrpJinvestIncome.setArr(iPrpJinvestIncomeSchemaJT);
    		
    		iPrpJinvestIncomeSchemaJT = new PrpJinvestIncomeSchema();
    	    iPrpJinvestIncomeSchemaJT = blPrpJinvestIncome.evaluate(dbPrpJinvest);
    	    iPrpJinvestIncomeSchemaJT.setPayRefReason("R02");
    	    iPrpJinvestIncomeSchemaJT.setIncomeStartDate(dbPrpJinvest.getStartDate());
    	    iPrpJinvestIncomeSchemaJT.setIncomeEndDate(strOperatorDate);
    	    iPrpJinvestIncomeSchemaJT.setIncomeFee("" + (-dbThisTimeIncomeFee));
    	    iPrpJinvestIncomeSchemaJT.setCurrency2(dbPrpJinvest.getCurrency1());
    	    iPrpJinvestIncomeSchemaJT.setExchangeRate("1");
    	    iPrpJinvestIncomeSchemaJT.setIncomeFeeCny("0");
    		iPrpJinvestIncomeSchemaJT.setOperatorCode(dbPrpJinvest.getHandlerCode());
    		iPrpJinvestIncomeSchemaJT.setOperatorDate(strOperatorDate);
    		iPrpJinvestIncomeSchemaJT.setIncometimes("" + (Integer.parseInt(iPrpJinvestIncomeSchemaJT.getIncometimes())-1));
    		blPrpJinvestIncome.setArr(iPrpJinvestIncomeSchemaJT);
    		
    		blPrpJinvestIncome.save(dbpool);
        	blPrpJinvest.save(dbpool);
    	}
    }
    
    /**
     * @author zhanglingjian 20080811 富安居续XXXXX注销
     * @param dbpool 数据库连接池
     * @param iCertiType 业务类型
     * @param iPolicyNo XX号
     * @throws UserException
     * @throws Exception
     */
    
    public void transCancelReNewal(DbPool dbpool,String iCertiType,String iPolicyNo) throws UserException,Exception
    {
    	DBPrpJinvest dbPrpJinvest = new DBPrpJinvest();
    	DBPrpJinvest dbPrpJinvestOld = new DBPrpJinvest();
    	BLPrpJinvest blPrpJinvest = new BLPrpJinvest();
    	int intReturn =0;
    	
    	intReturn = dbPrpJinvest.getInfo(dbpool, iPolicyNo, "0");
    	if (intReturn==100){
			throw new UserException(-98, -1167, "BLPrpJinvest.transCancelReNewal",
					"PrpJinvest表中没有续XXXXX新XX"+iPolicyNo+"对应XX数据" );
    	}
    	
    	
    	
    	if(dbPrpJinvest.getPayRefDate() != null && !dbPrpJinvest.getPayRefDate().equals("")){
    		throw new UserException(-98, -1167, "BLPrpJinvest.transCancelReNewal",
					"续XXXXX新XX"+iPolicyNo+"已经做到款确认，不允许做注销！" );
    	}
    	
    	
    	String sql = " SERIALNO=0 AND RENEWALPOLICYNO='" + iPolicyNo + "' ";
    	blPrpJinvest.query(dbpool, sql);
    	if(blPrpJinvest.getSize() > 0){
    		if(!blPrpJinvest.getArr(0).getPaymentFlag().equals("00")){
    			throw new UserException(-98, -1167, "BLPrpJinvest.transCancelReNewal",
    					"续XXXXX原XX"+blPrpJinvest.getArr(0).getPolicyNo()+"已经进入满期给付流程！" );
    		}
    		if(!blPrpJinvest.getArr(0).getFlag().equals("E")){
    			throw new UserException(-98, -1167, "BLPrpJinvest.transCancelReNewal",
    					"续XXXXX原XX"+blPrpJinvest.getArr(0).getPolicyNo()+"已经退XXXXX！" );
    		}
    	}else{
    		throw new UserException(-98, -1167, "BLPrpJinvest.transCancelReNewal",
					"续XXXXX新XX"+iPolicyNo+"没有原XX信息！" );
    	}
    	
    	dbPrpJinvestOld.setSchema(blPrpJinvest.getArr(0));
    	dbPrpJinvestOld.setReNewalFlag("0");
    	dbPrpJinvestOld.setReNewalPolicyNo("");
    	dbPrpJinvestOld.setReNewalPayment("0");
    	dbPrpJinvestOld.update(dbpool);
    	
    	dbPrpJinvest.setFlag("TB");
    	dbPrpJinvest.update(dbpool);
		
    }
    
    /**
     * 生成退XXXXX批减本金的收付数据 xushaojiang  20071113
     * @param PrpJinvestSchemaP02 对应XX的收付数据
     * @param dbPrpCmain 
     * @param dbPrpPhead 
     * @param dbPrpPmainInvest 
     * @param iCertiType  业务类型 
     * @param iCertiNo  业务号
     * @throws UserException
     * @throws Exception
     */    
    public PrpJinvestSchema  PrpJinvestTransToP02(DbPool dbpool,DBPrpCmain dbPrpCmain,DBPrpPhead dbPrpPhead,
    		DBPrpPmainInvest dbPrpPmainInvest,String iCertiType,String iCertiNo)throws UserException,Exception{
    	DBPrpJinvest iDBPrpJinvest = new DBPrpJinvest();
    	iDBPrpJinvest.getInfo(dbpool,dbPrpCmain.getPolicyNo(), "0");
    	PrpJinvestSchema PrpJinvestSchema= iDBPrpJinvest ;
    	PrpJinvestSchema.setCertiNo(iCertiNo);
    	PrpJinvestSchema.setCertiType(iCertiType);
    	PrpJinvestSchema.setPolicyNo(iDBPrpJinvest.getPolicyNo());
    	PrpJinvestSchema.setSerialNo("1");
    	PrpJinvestSchema.setPayRefReason("P02");
    	PrpJinvestSchema.setEndDate(dbPrpCmain.getEndDate());
    	PrpJinvestSchema.setPlanFee(Math.abs(Double.parseDouble(dbPrpPmainInvest.getChgInvestment()))+"");
    	if(iDBPrpJinvest.getIncometimes()== null||iDBPrpJinvest.getIncometimes().equals("")){
    		PrpJinvestSchema.setIncometimes("1");	
    	}else if(Double.parseDouble(iDBPrpJinvest.getIncometimes())==0){
    		PrpJinvestSchema.setIncometimes("1");	
    	}else{
    		PrpJinvestSchema.setIncometimes((Integer.parseInt(iDBPrpJinvest.getIncometimes())+2)+"");
    	}
    	PrpJinvestSchema.setIncomeFlag("1");
    	PrpJinvestSchema.setValidDate(dbPrpPhead.getUnderWriteEndDate());
    	PrpJinvestSchema.setUnderWriteDate(dbPrpPhead.getUnderWriteEndDate());
    	PrpJinvestSchema.setIncomeRate("0");
    	double IncomeFee = Math.abs(Double.parseDouble(dbPrpPmainInvest.getChgBackAmount()))
    	                   +Math.abs(Double.parseDouble(dbPrpPmainInvest.getFellBackFee()))
    	                   -Math.abs(Double.parseDouble(dbPrpPmainInvest.getChgInvestment()));
    	PrpJinvestSchema.setIncomeFee(IncomeFee+"");
    	PrpJinvestSchema.setSumIncomeFee(IncomeFee+"");
    	PrpJinvestSchema.setIncomeDate(dbPrpCmain.getEndDate());
    	PrpJinvestSchema.setLeftIncomeFee("0");
    	PrpJinvestSchema.setFellBackFee(Math.abs(Double.parseDouble(dbPrpPmainInvest.getFellBackFee()))+"");
    	
    	
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strCenterCode = blPrpDcompany.getCenterCode(dbpool,dbPrpCmain.getComCode());
        if(strCenterCode.equals("")||strCenterCode.trim().length()==0){
          	throw new UserException( -98, -1167, "BLPrpJinvest.PrpJinvestTransToP02()", "核算单位取值失败！");
        }
    	PrpJinvestSchema.setCenterCode(strCenterCode);
    	PrpJinvestSchema.setBusinessNature(dbPrpCmain.getBusinessNature());
        
    	PrpJinvestSchema.setChannelType(dbPrpCmain.getChannelType());
        
    	PrpJinvestSchema.setBranchCode("");
    	PrpJinvestSchema.setAccBookType("");
    	PrpJinvestSchema.setAccBookCode("");
    	PrpJinvestSchema.setYearMonth("");
    	PrpJinvestSchema.setVoucherNo("");
    	PrpJinvestSchema.setExchangeRate("1");
    	PrpJinvestSchema.setPlanFeeCNY("0");
    	PrpJinvestSchema.setPayRefDate("");
    	PrpJinvestSchema.setPayRefFee("0");
    	PrpJinvestSchema.setRealPayRefFee("0");
    	PrpJinvestSchema.setCurrency2("");
    	PrpJinvestSchema.setFlag("TBBJ");
    	PrpJinvestSchema.setRealPayRefNo("");
    	PrpJinvestSchema.setLeftInvestMent("0");
    	PrpJinvestSchema.setLeftQuantity("0");
    	return PrpJinvestSchema;
    }
    /**
     * 生成退XXXXX批减本金的收付数据 xushaojiang  20071113
     * @param PrpJinvestSchemaP02 对应XX的收付数据
     * @param dbPrpCmain 
     * @param dbPrpPhead 
     * @param dbPrpPmainInvest 
     * @param iCertiType  业务类型 
     * @param iCertiNo  业务号
     * @throws UserException
     * @throws Exception
     */    
    public  void PrpJinvestIncomeTrans(PrpJinvestSchema PrpJinvestSchemaP02,DBPrpCmain dbPrpCmain,DBPrpPhead dbPrpPhead,
    		DBPrpPmainInvest dbPrpPmainInvest,String iCertiType,String iCertiNo)throws UserException,Exception{
    	PrpJinvestSchemaP02.setCertiNo(iCertiNo);
    	PrpJinvestSchemaP02.setCertiType(iCertiType);
    	PrpJinvestSchemaP02.setSerialNo("1");
    	PrpJinvestSchemaP02.setPayRefReason("P02");
    	PrpJinvestSchemaP02.setEndDate(dbPrpCmain.getEndDate());
    	PrpJinvestSchemaP02.setPlanFee(Math.abs(Double.parseDouble(dbPrpPmainInvest.getChgInvestment()))+"");
    	if(PrpJinvestSchemaP02.getIncometimes()== null||PrpJinvestSchemaP02.getIncometimes().equals(""))
    		PrpJinvestSchemaP02.setIncometimes("0");
    	PrpJinvestSchemaP02.setIncometimes((Integer.parseInt(PrpJinvestSchemaP02.getIncometimes()+2))+"");
    	PrpJinvestSchemaP02.setValidDate(dbPrpPhead.getUnderWriteEndDate());
    	PrpJinvestSchemaP02.setUnderWriteDate(dbPrpPhead.getUnderWriteEndDate());
    	PrpJinvestSchemaP02.setIncomeRate("0");
    	PrpJinvestSchemaP02.setIncomeFee(Math.abs(Double.parseDouble(dbPrpPmainInvest.getChgBackAmount()))+"");
    	PrpJinvestSchemaP02.setIncomeDate(dbPrpCmain.getEndDate());
    	PrpJinvestSchemaP02.setLeftIncomeFee("0");
    	PrpJinvestSchemaP02.setFellBackFee(Math.abs(Double.parseDouble(dbPrpPmainInvest.getFellBackFee()))+"");
    	PrpJinvestSchemaP02.setCenterCode("");
    	PrpJinvestSchemaP02.setBranchCode("");
    	PrpJinvestSchemaP02.setAccBookType("");
    	PrpJinvestSchemaP02.setAccBookCode("");
    	PrpJinvestSchemaP02.setVoucherNo("");
    	PrpJinvestSchemaP02.setExchangeRate("1");
    	PrpJinvestSchemaP02.setPlanFeeCNY("");
    	PrpJinvestSchemaP02.setPayRefDate("");
    	PrpJinvestSchemaP02.setPayRefFee("");
    	PrpJinvestSchemaP02.setRealPayRefFee("");
    	PrpJinvestSchemaP02.setCurrency2("");
    	PrpJinvestSchemaP02.setFlag("TBBJ");
    }    
    /**
     * 投资金到款确认生成凭证 SangMingqian 20060804
     * @param arrCertiNo XX号码/批单号码
     * @param arrSerialNo 投资收益计提序号
     * @param strPayRefCode 收付员代码
     * @param strPayRefDate 收付日期
     * @return
     * @throws UserException
     * @throws Exception
     */
    public BLAccMainVoucher InvestRefVerify(String[] arrCertiNo,String[] arrSerialNo,
                                          String strPayRefCode,String strPayRefDate,
                                          String strAccountNoInput,String strComCode,String strYearMonth) throws UserException,Exception{

      DbPool dbpool = new DbPool();
      BLAccMainVoucher blAccMainVoucher = new BLAccMainVoucher();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE); 
        dbpool.beginTransaction();

        String[] arrBankAccount = Str.split(strAccountNoInput,"/");
        String strBankCode= arrBankAccount[0];
        String strAccountNo = arrBankAccount[1];

        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strCenterCode = blPrpDcompany.getCenterCode(dbpool,strComCode);
        if(strCenterCode.equals("")||strCenterCode.trim().length()==0){
        	throw new UserException( -98, -1167, "BLPrpJinvest.InvestRefVerify()", "核算单位取值失败！");
        }
        String strBranchCode = blPrpDcompany.getBranchCode(strComCode);
        if(strBranchCode.equals(""))
            strBranchCode = strCenterCode;
        String strAccBookType = SysConst.getProperty("ACCBOOKTYPE");
        String strAccBookCode = "";
        if(strAccBookType.equals("02"))
            strAccBookCode = SysConst.getProperty("ACCBOOKCODE");

        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(strAccBookType);
        accMainVoucherSchema.setAccBookCode(strAccBookCode);
        accMainVoucherSchema.setCenterCode(strCenterCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setAuxNumber(""+this.getSize());
        accMainVoucherSchema.setOperatorCode(strPayRefCode);
        accMainVoucherSchema.setOperatorBranch(strBranchCode);
        accMainVoucherSchema.setVoucherDate(strPayRefDate);
        accMainVoucherSchema.setVoucherType("1");
        accMainVoucherSchema.setGenerateWay("9");
        accMainVoucherSchema.setVoucherFlag("1");
        accMainVoucherSchema.setYearMonth(strYearMonth);

        BLAccVoucher blAccVoucher = new BLAccVoucher();
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        String strCondition = "";
        BLPrpJinvest blPrpJinvest = new BLPrpJinvest();
        for (int i = 0; i < arrCertiNo.length; i++) {
            strCondition = " CertiNo='"+arrCertiNo[i]+"'"
                           + " AND SerialNo='"+arrSerialNo[i]+"'";
            blPrpJinvest.query(dbpool,strCondition);
            this.setArr(blPrpJinvest.getArr(0));
        }

        AccSubVoucherSchema accSubVoucherSchema = null;
        BLAccSubVoucher blAccSubVoucher = new BLAccSubVoucher();
        String strItemCode = "";
        DBAccBankAccount dbAccBankAccount = new DBAccBankAccount();
        for(int i=0;i<this.getSize();i++){
            
            accSubVoucherSchema = new AccSubVoucherSchema();
            accSubVoucherSchema.setAccBookType(strAccBookType);
            accSubVoucherSchema.setAccBookCode(strAccBookCode);
            accSubVoucherSchema.setCenterCode(strCenterCode);
            accSubVoucherSchema.setYearMonth(strYearMonth);
            accSubVoucherSchema.setCurrency(this.getArr(i).getCurrency1());
            accSubVoucherSchema.setF01(this.getArr(i).getClassCode());
            accSubVoucherSchema.setF05(this.getArr(i).getRiskCode());
            accSubVoucherSchema.setF28(this.getArr(i).getComCode());
            accSubVoucherSchema.setF27(this.getArr(i).getHandler1Code());
            accSubVoucherSchema.setF26(strBranchCode);
            accSubVoucherSchema.setF25(strBankCode);
            accSubVoucherSchema.setF29(strAccountNo);
            if(!accSubVoucherSchema.getF29().equals(""))
            {
              dbAccBankAccount = new DBAccBankAccount();
              dbAccBankAccount.getInfo(accSubVoucherSchema.getF29(),strCenterCode);
              accSubVoucherSchema.setF09("0"+dbAccBankAccount.getSaveNature());
            }
            accSubVoucherSchema.setF03(strPayRefDate);
            accSubVoucherSchema.setRemark( strPayRefDate + "投资金到款确认");
            accSubVoucherSchema.setItemCode("1002");
            accSubVoucherSchema.setDirectionIdx(accSubVoucherSchema.getF09() +
                                                 "/" + accSubVoucherSchema.getF25() +
                                                 "/" + accSubVoucherSchema.getF29() +
                                                 "/");
            accSubVoucherSchema.setDirectionOther("XJ0103/");
            
            double dblExchRate = 1;
            dblExchRate = PubTools.getExchangeRate(accSubVoucherSchema.getCurrency(),
                                                   "CNY",
                                                   accMainVoucherSchema.getVoucherDate());
            accSubVoucherSchema.setExchangeRate(""+dblExchRate);
            accSubVoucherSchema.setDebitSource(""+Math.abs(Double.parseDouble(this.getArr(i).getPlanFee())));
            double dblDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
            dblDebitDest = Str.round(Str.round(dblDebitDest,8),2);
            accSubVoucherSchema.setDebitDest(""+Math.abs(dblDebitDest));
            blAccSubVoucher.setArr(accSubVoucherSchema);

            
            
            accSubVoucherSchema = new AccSubVoucherSchema();
            accSubVoucherSchema.setAccBookType(strAccBookType);
            accSubVoucherSchema.setAccBookCode(strAccBookCode);
            accSubVoucherSchema.setCenterCode(strCenterCode);
            accSubVoucherSchema.setYearMonth(strYearMonth);
            accSubVoucherSchema.setCurrency(this.getArr(i).getCurrency1());
            accSubVoucherSchema.setF01(this.getArr(i).getClassCode());
            accSubVoucherSchema.setF05(this.getArr(i).getRiskCode());
            accSubVoucherSchema.setF28(this.getArr(i).getComCode());
            accSubVoucherSchema.setF27(this.getArr(i).getHandler1Code());
            accSubVoucherSchema.setF26(strBranchCode);
            accSubVoucherSchema.setF03(strPayRefDate);
            accSubVoucherSchema.setRemark("投资金到款确认");
            accSubVoucherSchema.setItemCode("2149");
            accSubVoucherSchema.setDirectionIdx("14/01/");


            
            
            dblExchRate = 1;
            dblExchRate = PubTools.getExchangeRate(accSubVoucherSchema.getCurrency(),
                                                   "CNY",
                                                   accMainVoucherSchema.getVoucherDate());
            accSubVoucherSchema.setExchangeRate(""+dblExchRate);
            accSubVoucherSchema.setCreditSource(""+Math.abs(Double.parseDouble(this.getArr(i).getPlanFee())));
            double dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
            dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
            accSubVoucherSchema.setCreditDest(""+Math.abs(dblCreditDest));
            strItemCode = accSubVoucherSchema.getItemCode()+"/"+accSubVoucherSchema.getDirectionIdx();
            blAccSubVoucher.setArr(accSubVoucherSchema);
        }


        int suffixNo = 0;
        int ii = 0;
        int jj = 0;
        AccSubVoucherSchema schema1 = new AccSubVoucherSchema();
        AccSubVoucherSchema schema2 = new AccSubVoucherSchema();

        for (ii = 0; ii < blAccSubVoucher.getSize(); ii++)
        {
            schema1 = blAccSubVoucher.getArr(ii);
            for (jj = 0; jj < ii; jj++)
            {
                schema2 = blAccSubVoucher.getArr(jj);
                if (schema2.getItemCode().equals(schema1.getItemCode())
                    && schema2.getDirectionIdx().equals(schema1.getDirectionIdx())
                    && schema2.getDirectionOther().equals(schema1.getDirectionOther())
                    /** MODIFY LIJIBIN BEING 2003-11-18**/
                    
                    
                   
                    && schema2.getCurrency().equals(schema1.getCurrency()) 
                   
                    && (Double.parseDouble(schema1.getDebitDest()) == 0
                        && Double.parseDouble(schema2.getDebitDest()) == 0
                        || Double.parseDouble(schema1.getCreditDest()) == 0
                        && Double.parseDouble(schema2.getCreditDest()) == 0
                        
                        || schema1.getItemCode().equals("1001") || schema1.getItemCode().equals("1002")
                       )
                    /** MODIFY LIJIBIN END 2003-11-18* */
                    )
                {


                    schema1.setDebitDest("" + (Double.parseDouble(schema1.getDebitDest())
                                               + Double.parseDouble(schema2.getDebitDest())));
                    schema1.setDebitSource("" + (Double.parseDouble(schema1.getDebitSource())
                                                 + Double.parseDouble(schema2.getDebitSource())));
                    schema1.setCreditDest("" + (Double.parseDouble(schema1.getCreditDest())
                                                + Double.parseDouble(schema2.getCreditDest())));
                    schema1.setCreditSource("" + (Double.parseDouble(schema1.getCreditSource())
                                                  + Double.parseDouble(schema2.getCreditSource())));
                    
                    blAccSubVoucher.remove(jj);
                    

                    jj--;
                    ii--;
                }
            }
            suffixNo++;
            schema1.setSuffixNo("" + suffixNo);
            if (schema1.getDebitDest() != null && Double.parseDouble(schema1.getDebitDest()) > 0)
                schema1.setFlag("1");
            else
                schema1.setFlag("2");
            blAccSubVoucher.setArr(schema1, ii);
        }

        
        for(int i=0; i<blAccSubVoucher.getSize(); i++)
        {
          blAccSubVoucher.getArr(i).setSuffixNo("" + (i + 1));
          blAccSubVoucher.createRawArticle(blAccSubVoucher.getArr(i),strItemCode);
        }
        blAccSubVoucher.voucherComp("101");
        blAccSubVoucher.voucherOrder();
        blAccVoucher.setBLAccSubVoucher(blAccSubVoucher);

        for(int j=0;j<blAccSubVoucher.genVoucherArticle().getSize();j++){
        }

        blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());
        blAccVoucher.save(dbpool);
        blAccMainVoucher = blAccVoucher.getBLAccMainVoucher();

        DBPrpJinvest dbPrpJinvest = null;
        
        for(int i=0;i<this.getSize();i++){
            dbPrpJinvest = new DBPrpJinvest();
            dbPrpJinvest.getInfo(dbpool,this.getArr(i).getCertiNo(),this.getArr(i).getSerialNo());
            dbPrpJinvest.setAccBookType(strAccBookType);
            
            dbPrpJinvest.setCenterCode(strCenterCode);
            dbPrpJinvest.setBranchCode(strBranchCode);
            dbPrpJinvest.setAccBookCode(strAccBookCode);
            dbPrpJinvest.setYearMonth(strYearMonth);
            dbPrpJinvest.setVoucherNo(blAccMainVoucher.getArr(0).getVoucherNo());
            dbPrpJinvest.setPayRefDate(strPayRefDate);
            dbPrpJinvest.setCurrency2(this.getArr(i).getCurrency1());
            dbPrpJinvest.setPayRefFee(this.getArr(i).getPlanFee());
            dbPrpJinvest.setRealPayRefFee(this.getArr(i).getPlanFee());
            dbPrpJinvest.update(dbpool);
        }
        dbpool.commitTransaction();
      }
      catch(UserException userexception){
        dbpool.rollbackTransaction();
        throw userexception;
      }
      catch(SQLException sqlexception){
        dbpool.rollbackTransaction();
        throw sqlexception;
      }
      catch(Exception exception){
        dbpool.rollbackTransaction();
        throw exception;
      }
      finally {
        dbpool.close();
      }
      return blAccMainVoucher;
    }

    /**
     *  投资金付款确认生成凭证 SangMingqian 20060822
     * @param strPayRefReason 业务类型 P02-退XXXXX P03-满期给付
     * @param arrCertiNo XX号码/批单号码
     * @param arrSerialNo 投资收益计提序号
     * @param strPayRefCode 收付员代码
     * @param strPayRefDate 收付日期
     * @param strAccountNoInput
     * @param strComCode
     * @return
     * @throws UserException
     * @throws Exception
     */
    public BLAccMainVoucher InvestPayVerify(String strPayRefReason,String[] arrCertiNo,String[] arrSerialNo,
                                              String strPayRefCode,String strPayRefDate,
                                              String strAccountNoInput,String strComCode) throws UserException,Exception{

        DbPool dbpool = new DbPool();
        BLAccMainVoucher blAccMainVoucher = new BLAccMainVoucher();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE); 
            dbpool.beginTransaction();

            String[] arrBankAccount = Str.split(strAccountNoInput,"/");
            String strBankCode= arrBankAccount[0];
            String strAccountNo = arrBankAccount[1];

            BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
            String strCenterCode = blPrpDcompany.getCenterCode(dbpool,strComCode);
            if(strCenterCode.equals("")||strCenterCode.trim().length()==0){
            	throw new UserException( -98, -1167, "BLPrpJinvest.InvestPayVerify()", "核算单位取值失败！");
            }
            String strBranchCode = blPrpDcompany.getBranchCode(strComCode);
            if(strBranchCode.equals(""))
                strBranchCode = strCenterCode;
            String strAccBookType = SysConst.getProperty("ACCBOOKTYPE");
            String strAccBookCode = "";
            if(strAccBookType.equals("02"))
                strAccBookCode = SysConst.getProperty("ACCBOOKCODE");

            
            BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
            String strSQL = "";
            String strYearMonth = "";
            strSQL = " CenterCode='"+strCenterCode
                     + "' AND AccBookType='"+strAccBookType
                     + "' AND AccBookCode='"+strAccBookCode
                     + "' AND AccMonthStat='3'";
            blAccMonthTrace.query(strSQL);
            if(blAccMonthTrace.getSize()==0)
                throw new UserException( -98, -1167, "BLPrpJpayRefMain.genAccVoucher",
                                                        "核算单位" + strCenterCode + "还没有初始化");
            else
                strYearMonth = blAccMonthTrace.getArr(0).getYearMonth();

            
            AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
            accMainVoucherSchema.setAccBookType(strAccBookType);
            accMainVoucherSchema.setAccBookCode(strAccBookCode);
            accMainVoucherSchema.setCenterCode(strCenterCode);
            accMainVoucherSchema.setBranchCode(strBranchCode);
            accMainVoucherSchema.setAuxNumber(""+this.getSize());
            accMainVoucherSchema.setOperatorCode(strPayRefCode);
            accMainVoucherSchema.setOperatorBranch(strBranchCode);
            accMainVoucherSchema.setVoucherDate(strPayRefDate);
            accMainVoucherSchema.setVoucherType("1");
            accMainVoucherSchema.setGenerateWay("9");
            accMainVoucherSchema.setVoucherFlag("1");
            accMainVoucherSchema.setYearMonth(strYearMonth);

            BLAccVoucher blAccVoucher = new BLAccVoucher();
            blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

            String strCondition = "";
            BLPrpJinvest blPrpJinvest = new BLPrpJinvest();
            for (int i = 0; i < arrCertiNo.length; i++) {
                strCondition = " CertiNo='"+arrCertiNo[i]+"'"
                             + " AND SerialNo='"+arrSerialNo[i]+"'";
                blPrpJinvest.query(dbpool,strCondition);
                this.setArr(blPrpJinvest.getArr(0));
            }

            AccSubVoucherSchema accSubVoucherSchema = null;
            BLAccSubVoucher blAccSubVoucher = new BLAccSubVoucher();
            String strItemCode = "";
            DBAccBankAccount dbAccBankAccount = new DBAccBankAccount();
            for(int i=0;i<this.getSize();i++){
                if(strPayRefReason.equals("P02")){
                    
                    accSubVoucherSchema = new AccSubVoucherSchema();
                    accSubVoucherSchema.setAccBookType(strAccBookType);
                    accSubVoucherSchema.setAccBookCode(strAccBookCode);
                    accSubVoucherSchema.setCenterCode(strCenterCode);
                    accSubVoucherSchema.setYearMonth(strYearMonth);
                    accSubVoucherSchema.setCurrency(this.getArr(i).getCurrency1());
                    accSubVoucherSchema.setF01(this.getArr(i).getClassCode());
                    accSubVoucherSchema.setF05(this.getArr(i).getRiskCode());
                    accSubVoucherSchema.setF28(this.getArr(i).getComCode());
                    accSubVoucherSchema.setF27(this.getArr(i).getHandler1Code());
                    accSubVoucherSchema.setF26(strBranchCode);
                    accSubVoucherSchema.setF25(strBankCode);
                    accSubVoucherSchema.setF29(strAccountNo);
                    if(!accSubVoucherSchema.getF29().equals("")){
                        dbAccBankAccount = new DBAccBankAccount();
                        dbAccBankAccount.getInfo(accSubVoucherSchema.getF29(),strCenterCode);
                        accSubVoucherSchema.setF09("0"+dbAccBankAccount.getSaveNature());
                    }
                    accSubVoucherSchema.setF03(strPayRefDate);
                    accSubVoucherSchema.setRemark("退XXXXX付款确认");
                    accSubVoucherSchema.setItemCode("1002");
                    accSubVoucherSchema.setDirectionIdx(accSubVoucherSchema.getF09() +
                                                        "/" + accSubVoucherSchema.getF25() +
                                                        "/" + accSubVoucherSchema.getF29() +
                                                        "/");
                    
                    double dblExchRate = 1;
                    dblExchRate = PubTools.getExchangeRate(accSubVoucherSchema.getCurrency(),
                                                           "CNY",
                                                           accMainVoucherSchema.getVoucherDate());
                    accSubVoucherSchema.setExchangeRate(""+dblExchRate);
                    accSubVoucherSchema.setCreditSource(""+Math.abs(Double.parseDouble(this.getArr(i).getPlanFee())));
                    double dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
                    dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
                    accSubVoucherSchema.setCreditDest(""+Math.abs(dblCreditDest));
                    blAccSubVoucher.setArr(accSubVoucherSchema);

                    
                    accSubVoucherSchema = new AccSubVoucherSchema();
                    accSubVoucherSchema.setAccBookType(strAccBookType);
                    accSubVoucherSchema.setAccBookCode(strAccBookCode);
                    accSubVoucherSchema.setCenterCode(strCenterCode);
                    accSubVoucherSchema.setYearMonth(strYearMonth);
                    accSubVoucherSchema.setCurrency(this.getArr(i).getCurrency1());
                    accSubVoucherSchema.setCurrency(this.getArr(i).getCurrency1());
                    accSubVoucherSchema.setF01(this.getArr(i).getClassCode());
                    accSubVoucherSchema.setF05(this.getArr(i).getRiskCode());
                    accSubVoucherSchema.setF28(this.getArr(i).getComCode());
                    accSubVoucherSchema.setF27(this.getArr(i).getHandler1Code());
                    accSubVoucherSchema.setF26(strBranchCode);
                    accSubVoucherSchema.setF03(strPayRefDate);
                    accSubVoucherSchema.setRemark("退XXXXX付款确认");
                    accSubVoucherSchema.setItemCode("4109");
                    accSubVoucherSchema.setDirectionIdx("07/");
                    
                    dblExchRate = 1;
                    dblExchRate = PubTools.getExchangeRate(accSubVoucherSchema.getCurrency(),
                                                           "CNY",
                                                           accMainVoucherSchema.getVoucherDate());
                    accSubVoucherSchema.setExchangeRate(""+dblExchRate);
                    accSubVoucherSchema.setCreditSource(""+Math.abs(Double.parseDouble(this.getArr(i).getFellBackFee())));
                    dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
                    dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
                    accSubVoucherSchema.setCreditDest(""+Math.abs(dblCreditDest));
                    blAccSubVoucher.setArr(accSubVoucherSchema);

                    
                    accSubVoucherSchema = new AccSubVoucherSchema();
                    accSubVoucherSchema.setAccBookType(strAccBookType);
                    accSubVoucherSchema.setAccBookCode(strAccBookCode);
                    accSubVoucherSchema.setCenterCode(strCenterCode);
                    accSubVoucherSchema.setYearMonth(strYearMonth);
                    accSubVoucherSchema.setCurrency(this.getArr(i).getCurrency1());
                    accSubVoucherSchema.setF01(this.getArr(i).getClassCode());
                    accSubVoucherSchema.setF05(this.getArr(i).getRiskCode());
                    accSubVoucherSchema.setF28(this.getArr(i).getComCode());
                    accSubVoucherSchema.setF27(this.getArr(i).getHandler1Code());
                    accSubVoucherSchema.setF26(strBranchCode);
                    accSubVoucherSchema.setF03(strPayRefDate);
                    accSubVoucherSchema.setRemark("退XXXXX付款确认");
                    accSubVoucherSchema.setItemCode("2149");
                    accSubVoucherSchema.setDirectionIdx("14/01/");
                    
                    dblExchRate = 1;
                    dblExchRate = PubTools.getExchangeRate(accSubVoucherSchema.getCurrency(),
                                                           "CNY",
                                                            accMainVoucherSchema.getVoucherDate());
                    accSubVoucherSchema.setExchangeRate(""+dblExchRate);
                    accSubVoucherSchema.setDebitSource(""+Math.abs(Double.parseDouble(this.getArr(i).getPlanFee())+Double.parseDouble(this.getArr(i).getFellBackFee())));
                    double dblDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
                    dblDebitDest = Str.round(Str.round(dblDebitDest,8),2);
                    accSubVoucherSchema.setDebitDest(""+Math.abs(dblDebitDest));
                    strItemCode = accSubVoucherSchema.getItemCode()+"/"+accSubVoucherSchema.getDirectionIdx();
                    blAccSubVoucher.setArr(accSubVoucherSchema);
                }else if(strPayRefReason.equals("P03")){
                    
                    accSubVoucherSchema = new AccSubVoucherSchema();
                    accSubVoucherSchema.setAccBookType(strAccBookType);
                    accSubVoucherSchema.setAccBookCode(strAccBookCode);
                    accSubVoucherSchema.setCenterCode(strCenterCode);
                    accSubVoucherSchema.setYearMonth(strYearMonth);
                    accSubVoucherSchema.setCurrency(this.getArr(i).getCurrency1());
                    accSubVoucherSchema.setF26(strBranchCode);
                    accSubVoucherSchema.setF25(strBankCode);
                    accSubVoucherSchema.setF29(strAccountNo);
                    if(!accSubVoucherSchema.getF29().equals("")){
                        dbAccBankAccount = new DBAccBankAccount();
                        dbAccBankAccount.getInfo(accSubVoucherSchema.getF29(),strCenterCode);
                        accSubVoucherSchema.setF09("0"+dbAccBankAccount.getSaveNature());
                    }
                    accSubVoucherSchema.setF03(strPayRefDate);
                    accSubVoucherSchema.setRemark("满期给付付款确认");
                    accSubVoucherSchema.setItemCode("1002");
                    accSubVoucherSchema.setDirectionIdx(accSubVoucherSchema.getF09() +
                                                        "/" + accSubVoucherSchema.getF25() +
                                                        "/" + accSubVoucherSchema.getF29() +
                                                        "/");
                    
                    double dblExchRate = 1;
                    dblExchRate = PubTools.getExchangeRate(accSubVoucherSchema.getCurrency(),
                                                           "CNY",
                                                           accMainVoucherSchema.getVoucherDate());
                    accSubVoucherSchema.setExchangeRate(""+dblExchRate);
                    accSubVoucherSchema.setCreditSource(""+Math.abs(Double.parseDouble(this.getArr(i).getPlanFee())+Double.parseDouble(this.getArr(i).getSumIncomeFee())));
                    double dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
                    dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
                    accSubVoucherSchema.setCreditDest(""+Math.abs(dblCreditDest));
                    blAccSubVoucher.setArr(accSubVoucherSchema);

                    
                    accSubVoucherSchema = new AccSubVoucherSchema();
                    accSubVoucherSchema.setAccBookType(strAccBookType);
                    accSubVoucherSchema.setAccBookCode(strAccBookCode);
                    accSubVoucherSchema.setCenterCode(strCenterCode);
                    accSubVoucherSchema.setYearMonth(strYearMonth);
                    accSubVoucherSchema.setCurrency(this.getArr(i).getCurrency1());
                    accSubVoucherSchema.setF01(this.getArr(i).getClassCode());
                    accSubVoucherSchema.setF05(this.getArr(i).getRiskCode());
                    accSubVoucherSchema.setF28(this.getArr(i).getComCode());
                    accSubVoucherSchema.setF27(this.getArr(i).getHandler1Code());
                    accSubVoucherSchema.setF26(strBranchCode);
                    accSubVoucherSchema.setF03(strPayRefDate);
                    accSubVoucherSchema.setRemark("满期给付付款确认");
                    accSubVoucherSchema.setItemCode("2149");
                    accSubVoucherSchema.setDirectionIdx("14/01/");
                    
                    dblExchRate = 1;
                    dblExchRate = PubTools.getExchangeRate(accSubVoucherSchema.getCurrency(),
                                                           "CNY",
                                                            accMainVoucherSchema.getVoucherDate());
                    accSubVoucherSchema.setExchangeRate(""+dblExchRate);
                    accSubVoucherSchema.setDebitSource(""+Math.abs(Double.parseDouble(this.getArr(i).getPlanFee())));
                    double dblDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
                    dblDebitDest = Str.round(Str.round(dblDebitDest,8),2);
                    accSubVoucherSchema.setDebitDest(""+Math.abs(dblDebitDest));
                    strItemCode = accSubVoucherSchema.getItemCode()+"/"+accSubVoucherSchema.getDirectionIdx();
                    blAccSubVoucher.setArr(accSubVoucherSchema);

                    
                    accSubVoucherSchema = new AccSubVoucherSchema();
                    accSubVoucherSchema.setAccBookType(strAccBookType);
                    accSubVoucherSchema.setAccBookCode(strAccBookCode);
                    accSubVoucherSchema.setCenterCode(strCenterCode);
                    accSubVoucherSchema.setYearMonth(strYearMonth);
                    accSubVoucherSchema.setCurrency(this.getArr(i).getCurrency1());
                    accSubVoucherSchema.setF01(this.getArr(i).getClassCode());
                    accSubVoucherSchema.setF05(this.getArr(i).getRiskCode());
                    accSubVoucherSchema.setF28(this.getArr(i).getComCode());
                    accSubVoucherSchema.setF27(this.getArr(i).getHandler1Code());
                    accSubVoucherSchema.setF26(strBranchCode);
                    accSubVoucherSchema.setF03(strPayRefDate);
                    accSubVoucherSchema.setRemark("满期给付付款确认");
                    accSubVoucherSchema.setItemCode("2149");
                    accSubVoucherSchema.setDirectionIdx("14/02/");
                    
                    dblExchRate = 1;
                    dblExchRate = PubTools.getExchangeRate(accSubVoucherSchema.getCurrency(),
                                                           "CNY",
                                                            accMainVoucherSchema.getVoucherDate());
                    accSubVoucherSchema.setExchangeRate(""+dblExchRate);
                    accSubVoucherSchema.setDebitSource(""+Math.abs(Double.parseDouble(this.getArr(i).getSumIncomeFee())));
                    dblDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
                    dblDebitDest = Str.round(Str.round(dblDebitDest,8),2);
                    accSubVoucherSchema.setDebitDest(""+Math.abs(dblDebitDest));
                    strItemCode = accSubVoucherSchema.getItemCode()+"/"+accSubVoucherSchema.getDirectionIdx();
                    blAccSubVoucher.setArr(accSubVoucherSchema);
                }
            }
            
            for(int i=0; i<blAccSubVoucher.getSize(); i++){
                blAccSubVoucher.getArr(i).setSuffixNo("" + (i + 1));
                blAccSubVoucher.createRawArticle(blAccSubVoucher.getArr(i),strItemCode);
            }
            blAccSubVoucher.voucherComp("101");
            blAccSubVoucher.voucherOrder();
            blAccVoucher.setBLAccSubVoucher(blAccSubVoucher);
            blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());
            blAccVoucher.save(dbpool);
            blAccMainVoucher = blAccVoucher.getBLAccMainVoucher();

            DBPrpJinvest dbPrpJinvest = null;
            
            for(int i=0;i<this.getSize();i++){
                dbPrpJinvest = new DBPrpJinvest();
                dbPrpJinvest.getInfo(dbpool,this.getArr(i).getCertiNo(),this.getArr(i).getSerialNo());
                dbPrpJinvest.setAccBookType(strAccBookType);
                dbPrpJinvest.setAccBookCode(strAccBookCode);
                dbPrpJinvest.setYearMonth(strYearMonth);
                dbPrpJinvest.setVoucherNo(blAccMainVoucher.getArr(0).getVoucherNo());
                dbPrpJinvest.setPayRefDate(strPayRefDate);
                dbPrpJinvest.setCurrency2(this.getArr(i).getCurrency1());
                dbPrpJinvest.setPayRefFee(this.getArr(i).getPlanFee());
                dbPrpJinvest.setRealPayRefFee(this.getArr(i).getPlanFee());
                dbPrpJinvest.update(dbpool);
            }
            dbpool.commitTransaction();
        }
        catch(UserException userexception){
            dbpool.rollbackTransaction();
            throw userexception;
        }
        catch(SQLException sqlexception){
            dbpool.rollbackTransaction();
            throw sqlexception;
        }
        catch(Exception exception){
            dbpool.rollbackTransaction();
            throw exception;
        }
        finally {
            dbpool.close();
        }
        return blAccMainVoucher;
    }


    /**
     * @desc 自动计提投资收益，并且生成XXXXX凭证
     * @author liufengyao
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套编码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iIncomeDate 计提时间
     * @param iOperatorCode 操作员
     * @throws UserException
     * @throws Exception
     */
    public void transIncome(String iAccBookType,String iAccBookCode, String iCenterCode,
                                String iBranchCode, String iIncomeDate, String iOperatorCode)
    throws UserException,Exception{
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            this.transIncome(dbpool, iAccBookType, iAccBookCode, iCenterCode, iBranchCode, iIncomeDate, iOperatorCode);
        } catch (Exception e) {
        }
        finally {
            dbpool.close();
        }
    }
    /**
     * @desc 自动计提投资收益，并且生成XXXXX凭证
     * @author liufengyao
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套编码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iIncomeDate 计提时间
     * @param iOperatorCode 操作员
     * @throws UserException
     * @throws Exception
     */
    public void transIncome(DbPool dbpool,String iAccBookType,String iAccBookCode, String iCenterCode,
                                String iBranchCode, String iIncomeDate, String iOperatorCode)
    throws UserException,Exception{
        try {
            BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
            
            String strWherePart = " CenterFlag IN ('1','2') AND ValidStatus='1'";
            strWherePart += Str.convertString("ComCode",iCenterCode,"=");
            strWherePart += " ORDER BY ComCode";
            blPrpDcompany.query(dbpool,strWherePart,0);
            BLPrpJinvest iBLPrpJincome = new BLPrpJinvest();
            for(int i=0; i<blPrpDcompany.getSize(); i++){
              iCenterCode = blPrpDcompany.getArr(i).getComCode();
                try{
                    dbpool.beginTransaction();
                    
                    iBLPrpJincome = drawIncomeData(dbpool,iCenterCode,iIncomeDate);





              
              iBLPrpJincome.save(dbpool); 
                    dbpool.commitTransaction();
                }
                catch(UserException ue){
                    dbpool.rollbackTransaction();
                    ue.printStackTrace();
                    throw ue;
                }
                catch(Exception e){
                    dbpool.rollbackTransaction();
                    e.printStackTrace();
                    throw e;
                }
            }
        } catch (Exception e) {
        }
        finally {
        }
    }
    /**
     * @desc 自动计提投资收益
     * @author liufengyao
     * @param dbpool
     * @param iCenterCode 核算单位
     * @param iIncomeDate 计提时间
     * @throws UserException
     * @throws Exception
     */
    public BLPrpJinvest drawIncomeData(DbPool dbpool, String iCenterCode, String iIncomeDate)
    throws UserException,Exception{
      
      StringBuffer comBuffer = new StringBuffer();
      String strComcodeWhere = "";
      BLPrpDcompany iBLPrpDcompany = new BLPrpDcompany();
        String[] arrComCode = iBLPrpDcompany.getLowerComCodeNew(dbpool,iCenterCode);
        comBuffer.append("('"+iCenterCode+"'");
        for(int j=0; j<arrComCode.length; j++){
          comBuffer.append(",'"+arrComCode[j]+"'");
        }
        comBuffer.append(")");
        strComcodeWhere = comBuffer.toString();
        BLPrpJinvest iBLPrpJinvest = new BLPrpJinvest();
        BLPrpJinvest iBLPrpJIncome = new BLPrpJinvest();
        PrpJinvestSchema iPrpJinvestSchema = null;
        PrpJinvestSchema iPrpJincomeSchema = null;
        DBPrpJinvest iDBPrpJinvest = null;
      
      StringBuffer conditionBuffer = new StringBuffer();
      String strCondition = "";
      conditionBuffer.append(" certitype IN ('P','E')                                            ");
      conditionBuffer.append(" AND riskcode = '2901'                                             ");        
      conditionBuffer.append(" AND (incomedate IS NULL OR incomedate < '"+iIncomeDate+"')        ");
      conditionBuffer.append(" AND decode(certitype,'E',validdate,startdate) <= '"+iIncomeDate+"'");
      conditionBuffer.append(" AND payrefreason = 'R01'                                          ");
      conditionBuffer.append(" AND instr(flag,'E') > 0                                           ");
      conditionBuffer.append(" AND incomeflag = 0                                                ");
      conditionBuffer.append(" AND realpayreffee > 0                                             ");
    
      
      
      conditionBuffer.append(" AND payrefdate <= '"+iIncomeDate+"'                               ");
      
      conditionBuffer.append(" AND comcode in "+strComcodeWhere+"                                ");
      strCondition = conditionBuffer.toString();
      iBLPrpJinvest.query(dbpool,strCondition,0);
      for(int i=0;i<iBLPrpJinvest.getSize();i++){
        iPrpJinvestSchema = iBLPrpJinvest.getArr(i);
        iPrpJincomeSchema = new PrpJinvestSchema();
        iPrpJincomeSchema.setProcSeq(iPrpJinvestSchema.getProcSeq());
        iPrpJincomeSchema.setCertiNo(iPrpJinvestSchema.getCertiNo());
        iPrpJincomeSchema.setCertiType(iPrpJinvestSchema.getCertiType());
        iPrpJincomeSchema.setPolicyNo(iPrpJinvestSchema.getPolicyNo());
        iPrpJincomeSchema.setSerialNo("1");
        iPrpJincomeSchema.setPayRefReason("R02");
        iPrpJincomeSchema.setClassCode(iPrpJinvestSchema.getClassCode());
        iPrpJincomeSchema.setRiskCode(iPrpJinvestSchema.getRiskCode());
        iPrpJincomeSchema.setContractNo(iPrpJinvestSchema.getContractNo());
        iPrpJincomeSchema.setAppliCode(iPrpJinvestSchema.getAppliCode());
        iPrpJincomeSchema.setAppIdno(iPrpJinvestSchema.getAppIdno());
        iPrpJincomeSchema.setAppliName(iPrpJinvestSchema.getAppliName());
        iPrpJincomeSchema.setInsuredCode(iPrpJinvestSchema.getInsuredCode());
        iPrpJincomeSchema.setInsuredName(iPrpJinvestSchema.getInsuredName());
        iPrpJincomeSchema.setStartDate(iPrpJinvestSchema.getStartDate());
        iPrpJincomeSchema.setEndDate(iPrpJinvestSchema.getEndDate());
        iPrpJincomeSchema.setValidDate(iPrpJinvestSchema.getValidDate());
        iPrpJincomeSchema.setCurrency1(iPrpJinvestSchema.getCurrency1());
        iPrpJincomeSchema.setPlanFee(iPrpJinvestSchema.getPlanFee());
        iPrpJincomeSchema.setInvestCount(iPrpJinvestSchema.getInvestCount());
        iPrpJincomeSchema.setComCode(iPrpJinvestSchema.getComCode());
        iPrpJincomeSchema.setMakeCom(iPrpJinvestSchema.getMakeCom());
        iPrpJincomeSchema.setAgentCode(iPrpJinvestSchema.getAgentCode());
        iPrpJincomeSchema.setHandler1Code(iPrpJinvestSchema.getHandler1Code());
        iPrpJincomeSchema.setHandlerCode(iPrpJinvestSchema.getHandlerCode());
        iPrpJincomeSchema.setUnderWriteDate(iPrpJinvestSchema.getUnderWriteDate());
        iPrpJincomeSchema.setInvestBankCode(iPrpJinvestSchema.getInvestBankCode());
        iPrpJincomeSchema.setInvestAccountNo(iPrpJinvestSchema.getInvestAccountNo());
        iPrpJincomeSchema.setPayBankCode(iPrpJinvestSchema.getPayBankCode());
        iPrpJincomeSchema.setPayAccountNo(iPrpJinvestSchema.getPayAccountNo());
        
        iPrpJincomeSchema.setChannelType(iPrpJinvestSchema.getChannelType());
        
        
        iPrpJincomeSchema.setBusinessNature(iPrpJinvestSchema.getBusinessNature());
        iPrpJincomeSchema.setCenterCode(iPrpJinvestSchema.getCenterCode());
        
        
        iPrpJincomeSchema.setBaseIncomeRate(iPrpJinvestSchema.getBaseIncomeRate());
          double dBaseIncomeRate = Double.parseDouble(ChgData.chgStrZero(iPrpJinvestSchema.getBaseIncomeRate()));
          dBaseIncomeRate = dBaseIncomeRate/100;
          
          if(dBaseIncomeRate==0){
            
            
            logger.info("dBaseIncomeRate="+dBaseIncomeRate);
            
            continue;
          }
        Date startDate  = new Date(iPrpJinvestSchema.getStartDate());
        Date endDate    = new Date(iPrpJinvestSchema.getEndDate());
        Date incomeDate     =  new Date(iIncomeDate);
        Date lastIncomeDate = null;
        if(iPrpJinvestSchema.getIncomeDate() != null && !iPrpJinvestSchema.getIncomeDate().equals(""))
          lastIncomeDate =  new Date(iPrpJinvestSchema.getIncomeDate());
        
        int iPeriodDates = PubTools.getDayMinus(startDate,0,endDate,24);
        int iPeriodSumIncomeDate = 0; 
        double dSumIncomeRate = 0.00; 
        double dSumIncomeData = 0.00; 
        double dIncomeData = 0.00;    
        double dLeftIncomeData = 0.00;
        int iSumIncomeTimes = 0;      
        String strIncomeFlag = "0";   
        
        String strIncomeNextEndDate = this.getNextMonthEndDate(iIncomeDate, 1);
        if(PubTools.compareDate(iIncomeDate, iPrpJinvestSchema.getEndDate()) >= 0){
          iPeriodSumIncomeDate = PubTools.getDayMinus(startDate, 0, endDate, 24);
        }else{
          iPeriodSumIncomeDate = PubTools.getDayMinus(startDate, 0, incomeDate, 24);
        }
        
        dSumIncomeRate = Str.round(dBaseIncomeRate*((double)iPeriodSumIncomeDate/iPeriodDates),6);
        
        if(PubTools.compareDate(iIncomeDate, iPrpJinvestSchema.getEndDate()) >= 0){

          
          dIncomeData = Str.round(dBaseIncomeRate*Double.parseDouble(ChgData.chgStrZero(iPrpJinvestSchema.getPlanFee()))-
                        Double.parseDouble(ChgData.chgStrZero(iPrpJinvestSchema.getSumIncomeFee())),2);
          iSumIncomeTimes = Integer.parseInt(iPrpJinvestSchema.getIncometimes())+1;
          strIncomeFlag = "1";
        }else if (iPrpJinvestSchema.getIncometimes() != null && !iPrpJinvestSchema.getIncometimes().equals("0")
            && lastIncomeDate != null){
          dIncomeData = Str.round(dBaseIncomeRate*Double.parseDouble(ChgData.chgStrZero(iPrpJinvestSchema.getPlanFee()))*
                    ((double)PubTools.getDayMinus(lastIncomeDate, 0, incomeDate, 0)/iPeriodDates),2);
          iSumIncomeTimes = Integer.parseInt(iPrpJinvestSchema.getIncometimes())+1;
        }else{
          
          dIncomeData = Str.round(dBaseIncomeRate*Double.parseDouble(ChgData.chgStrZero(iPrpJinvestSchema.getPlanFee()))*
                    ((double)PubTools.getDayMinus(startDate, 0, incomeDate, 24)/iPeriodDates),2);
          iSumIncomeTimes = 1;
        }
        
        dSumIncomeData = Str.round(Double.parseDouble(ChgData.chgStrZero(iPrpJinvestSchema.getSumIncomeFee()))+dIncomeData,2);
        
        dLeftIncomeData = dBaseIncomeRate*Double.parseDouble(ChgData.chgStrZero(iPrpJinvestSchema.getPlanFee()))-dSumIncomeData;
            
        iPrpJincomeSchema.setIncomeRate(Double.toString(dSumIncomeRate));
        iPrpJincomeSchema.setSumIncomeFee(Double.toString(dSumIncomeData));
        iPrpJincomeSchema.setIncometimes(Integer.toString(iSumIncomeTimes));
        iPrpJincomeSchema.setIncomeFlag("1");
        iPrpJincomeSchema.setIncomeDate(iIncomeDate);
        iPrpJincomeSchema.setIncomeFee(Double.toString(dIncomeData));
        iPrpJincomeSchema.setLeftIncomeFee(Double.toString(dLeftIncomeData));
        iPrpJincomeSchema.setFellBackFee("0");
          
          double dblExchRate = 1;
          double dblPlanFeeCNY = 0;
          dblExchRate = PubTools.getExchangeRate(dbpool,iPrpJincomeSchema.getCurrency1(),"CNY",iIncomeDate);
          iPrpJincomeSchema.setExchangeRate(""+dblExchRate);
          dblPlanFeeCNY = dIncomeData*dblExchRate;
          dblPlanFeeCNY = Str.round(Str.round(dblPlanFeeCNY,8),2);
          iPrpJincomeSchema.setPlanFeeCNY(Double.toString(dblPlanFeeCNY));
          
          iPrpJincomeSchema.setVoucherNo("0");
          
        iBLPrpJIncome.setArr(iPrpJincomeSchema);
            
        iDBPrpJinvest = new DBPrpJinvest();
        iDBPrpJinvest.setSchema(iPrpJinvestSchema);
        
        iDBPrpJinvest.setIncometimes(Integer.toString(iSumIncomeTimes));
        
        iDBPrpJinvest.setIncomeDate(iIncomeDate);
        
        iDBPrpJinvest.setIncomeFlag(strIncomeFlag);
        
        iDBPrpJinvest.setIncomeRate(Double.toString(dSumIncomeRate));
        
        iDBPrpJinvest.setSumIncomeFee(Double.toString(dSumIncomeData));
        
        iDBPrpJinvest.setLeftIncomeFee(Double.toString(dLeftIncomeData));
        iDBPrpJinvest.update(dbpool);
      }
      return iBLPrpJIncome;
    }
    /**
     * @desc 投资收益制证
     * @author liufengyao
     * @param dbpool
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套编码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iIncomeDate 计提时间
     * @param iOperatorCode 操作员
     * @throws UserException
     * @throws Exception
     */
    public void voucherIncomeData(DbPool dbpool, String iAccBookType,
                             String iAccBookCode, String iCenterCode,
                             String iBranchCode, String iIncomeDate,
                             String iOperatorCode,BLPrpJinvest iBLPrpJinvest)
    throws UserException,Exception{
      String strWherePart = " 1=1";
      if(iAccBookType.equals("")){
        iAccBookType = SysConst.getProperty("ACCBOOKTYPE");
      }
      strWherePart += Str.convertString("AccBookType",iAccBookType,"=");
        strWherePart += Str.convertString("AccBookCode",iAccBookCode,"=");
        strWherePart += Str.convertString("CenterCode",iCenterCode,"=");
        strWherePart += Str.convertString("BranchCode",iBranchCode,"=");
        BLAccBookBranch blAccBookBranch = new BLAccBookBranch();
        blAccBookBranch.query(dbpool,strWherePart,0);
        for(int i=0; i<blAccBookBranch.getSize(); i++){
          
            BLAccVoucher blAccVoucher = new BLAccVoucher();
            
            AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
            accMainVoucherSchema.setAccBookType(blAccBookBranch.getArr(i).getAccBookType());
            accMainVoucherSchema.setAccBookCode(blAccBookBranch.getArr(i).getAccBookCode());
            accMainVoucherSchema.setCenterCode(blAccBookBranch.getArr(i).getCenterCode());
            accMainVoucherSchema.setBranchCode(blAccBookBranch.getArr(i).getBranchCode());
            accMainVoucherSchema.setVoucherType("1");
            accMainVoucherSchema.setGenerateWay("6");
            accMainVoucherSchema.setVoucherDate(iIncomeDate);
            accMainVoucherSchema.setOperatorBranch(blAccBookBranch.getArr(i).getBranchCode());
            accMainVoucherSchema.setOperatorCode(iOperatorCode);
            accMainVoucherSchema.setVoucherFlag("1");
            
            String strYearMonth = iIncomeDate.substring(0,4)+ iIncomeDate.substring(5,7);
            accMainVoucherSchema.setAuxNumber("0");
            accMainVoucherSchema.setYearMonth(strYearMonth);
            accMainVoucherSchema.setVoucherNo("");
            
            blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);
            
            String strRemark = "自动计提" +iIncomeDate+"投联XXXXX投资收益";
            AccSubVoucherSchema accSubVoucherSchema = null;
            for (int j = 0; j < iBLPrpJinvest.getSize(); j++){
              for(int k=0;k<2;k++){
                    accSubVoucherSchema = new AccSubVoucherSchema();
                    accSubVoucherSchema.setAccBookType(accMainVoucherSchema.getAccBookType());
                    accSubVoucherSchema.setAccBookCode(accMainVoucherSchema.getAccBookCode());
                    accSubVoucherSchema.setCenterCode(accMainVoucherSchema.getCenterCode());
                    accSubVoucherSchema.setYearMonth(accMainVoucherSchema.getYearMonth());
                    accSubVoucherSchema.setF26(accMainVoucherSchema.getBranchCode());
                    accSubVoucherSchema.setVoucherNo(accMainVoucherSchema.getVoucherNo());
                    accSubVoucherSchema.setSuffixNo("" + (2 * j + k + 1));
                    accSubVoucherSchema.setCurrency(iBLPrpJinvest.getArr(i).getCurrency1());
                    accSubVoucherSchema.setF01(iBLPrpJinvest.getArr(j).getClassCode());
                    accSubVoucherSchema.setF05(iBLPrpJinvest.getArr(j).getRiskCode());
                    accSubVoucherSchema.setF27(iBLPrpJinvest.getArr(j).getHandler1Code());
                    accSubVoucherSchema.setF28(iBLPrpJinvest.getArr(j).getComCode());
                    accSubVoucherSchema.setRemark(strRemark);

                    if(k==0){
                        
                        accSubVoucherSchema.setItemCode("6521");
                        accSubVoucherSchema.setDirectionIdx("01/");
                        accSubVoucherSchema.setDebitSource(iBLPrpJinvest.getArr(j).getIncomeFee());
                        accSubVoucherSchema.setExchangeRate(iBLPrpJinvest.getArr(j).getExchangeRate());
                        accSubVoucherSchema.setDebitDest(iBLPrpJinvest.getArr(j).getPlanFeeCNY());
                        accSubVoucherSchema.setCreditSource("0");
                        accSubVoucherSchema.setCreditDest("0");

                    }else{
                      
                        accSubVoucherSchema.setItemCode("2251");
                        accSubVoucherSchema.setDirectionIdx("00");
                        accSubVoucherSchema.setDebitSource("0");
                        accSubVoucherSchema.setDebitDest("0");
                        accSubVoucherSchema.setCreditSource(iBLPrpJinvest.getArr(j).getIncomeFee());
                        accSubVoucherSchema.setExchangeRate(iBLPrpJinvest.getArr(j).getExchangeRate());
                        accSubVoucherSchema.setCreditDest(iBLPrpJinvest.getArr(j).getPlanFeeCNY());
                    }
                    blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
              }
            }
            BLAccSubVoucher blAccSubVoucher = blAccVoucher.getBLAccSubVoucher();
            if(blAccSubVoucher.getSize()==0){
              
              logger.info("没有生成凭证子表信息！");
              
              
                return;
            }
            
            for (int j = 0; j < blAccSubVoucher.getSize(); j++){
              blAccSubVoucher.getArr(j).setSuffixNo(""+(j+1));
              blAccSubVoucher.createRawArticle(dbpool, blAccSubVoucher.getArr(j), "");
            }
            
            blAccSubVoucher.voucherComp("111");
            
            blAccSubVoucher.voucherOrder();
            blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());
            
            blAccVoucher.save(dbpool);
            
            for (int j = 0; j < iBLPrpJinvest.getSize(); j++){
              iBLPrpJinvest.getArr(j).setAccBookCode(blAccVoucher.getBLAccMainVoucher().getArr(0).getAccBookCode());
              iBLPrpJinvest.getArr(j).setAccBookType(blAccVoucher.getBLAccMainVoucher().getArr(0).getAccBookType());
              iBLPrpJinvest.getArr(j).setCenterCode(blAccVoucher.getBLAccMainVoucher().getArr(0).getCenterCode());
              iBLPrpJinvest.getArr(j).setBranchCode(blAccVoucher.getBLAccMainVoucher().getArr(0).getBranchCode());
              iBLPrpJinvest.getArr(j).setYearMonth(blAccVoucher.getBLAccMainVoucher().getArr(0).getYearMonth());
              iBLPrpJinvest.getArr(j).setVoucherNo(blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo());
              iBLPrpJinvest.getArr(j).setPayRefFee(iBLPrpJinvest.getArr(j).getIncomeFee());
              iBLPrpJinvest.getArr(j).setRealPayRefFee(iBLPrpJinvest.getArr(j).getIncomeFee());
            }
            this.blAccMainVoucher.setArr(blAccVoucher.getBLAccMainVoucher().getArr(0));
            String strVoucherNo = blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
            
            logger.info("生成计提收益凭证 "+strVoucherNo);
            
        }
    }
    /**
     * @desc 投联XXXXX本金到款确认、XX应收、实收确认入口
     * @param arrCertiNo
     * @param arrSerialNo
     * @param arrPayRefReason
     * @param strPayRefCode
     * @param strPayRefDate
     * @param strAccountNo
     * @return
     * @throws UserException
     * @throws Exception
     */
    public String[] createInvestVoucher(String[] arrCertiNo,String[] arrSerialNo,String[] arrPayRefReason,
            String strPayRefCode,String operComCode,String strPayRefDate,String strAccountNo,String strHomeCenterCode)
    throws UserException,Exception{
      String[] arrVouch = null;
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            
            arrVouch = this.createInvestVoucher(dbpool,arrCertiNo,arrSerialNo,arrPayRefReason,
                    strPayRefCode,operComCode,strPayRefDate,strAccountNo,strHomeCenterCode);
            dbpool.commitTransaction();
        }
        catch(Exception e) {
          e.printStackTrace();
            dbpool.rollbackTransaction();
            throw e;
        }finally {
          dbpool.close();
        }
        return arrVouch;

    }
    /**
     * @desc 投联XXXXX本金到款确认、XX应收、实收确认入口
     * @param dbpool
     * @param arrCertiNo      业务号码
     * @param arrSerialNo     业务序列号
     * @param arrPayRefReason 收付原因
     * @param strPayRefCode   收付员代码
     * @param strPayRefDate   收付日期
     * @param strAccountNo    收款银行代码、帐号
     * @return
     * @throws UserException
     * @throws Exception
     */
    public String[] createInvestVoucher(DbPool dbpool,String[] arrCertiNo,String[] arrSerialNo,String[] arrPayRefReason,
            String strPayRefCode,String strPackUnit,String strPayRefDate,String strAccountInfo,String strHomeCenterCode)
    throws UserException,Exception{

    String[] strVoucher = new String[3];
    try
    {
      
      String strSQL = "";
      StringBuffer sqlBuffer = new StringBuffer();
      sqlBuffer.append(" (CertiNo= '"+arrCertiNo[0]+"' AND SerialNo='"+arrSerialNo[0]+"' AND PayRefReason='"+arrPayRefReason[0]+"') ");
      for (int i = 1; i < arrCertiNo.length; i++)
        sqlBuffer.append(" or (CertiNo= '"+arrCertiNo[i]+"' AND SerialNo='"+arrSerialNo[i]+"' AND PayRefReason='"+arrPayRefReason[i]+"') ");
            strSQL = sqlBuffer.toString();
            this.initArr();
      this.query(dbpool,strSQL);
      if(this.getSize() == 0){
        
        logger.info("没有进行到款确认的数据，请重新选择到款确认记录！");
        
        return strVoucher;
      }
      String strBankCode ="";
      String strAccountNo ="";
      if(!strAccountInfo.equals("")){
    	  String[] arrBankAccount = Str.split(strAccountInfo,"/");
    	  
    	  strBankCode  = arrBankAccount[0];
    	  
    	  strAccountNo = arrBankAccount[1];
      }
      String strComCode = this.getArr(0).getComCode();
      BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
      String strCenterCode = blPrpDcompany.getCenterCode(dbpool,strComCode);
      if(strCenterCode.equals("")||strCenterCode.trim().length()==0){
      	throw new UserException( -98, -1167, "BLPrpJinvest.createInvestVoucher()", "核算单位取值失败！");
      }
      String strBranchCode = blPrpDcompany.getBranchCode(strComCode);
      if(strBranchCode.equals("")){
        strBranchCode = strCenterCode;
      }


















        /****************************生成本金到款确认凭证*******************************/
        
      
      DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
      String strRealPayRefNo = dbPrpJpayRefVoucher.genRealPayRefNo();
      dbPrpJpayRefVoucher.setRealPayRefNo(strRealPayRefNo);
      dbPrpJpayRefVoucher.setPayRefNoType("A");
      dbPrpJpayRefVoucher.setVoucherDate(strPayRefDate);
      dbPrpJpayRefVoucher.setComCode(strBranchCode);
      dbPrpJpayRefVoucher.setPayRefCode(strPayRefCode);
      if(strHomeCenterCode.equals("0")){
    	  dbPrpJpayRefVoucher.setCenterCode(strCenterCode);
    	  dbPrpJpayRefVoucher.setToCenterCode("0");
      }else{
    	  dbPrpJpayRefVoucher.setCenterCode(strHomeCenterCode);
    	  dbPrpJpayRefVoucher.setToCenterCode(strCenterCode);
      }
      dbPrpJpayRefVoucher.setAttribute2("0");
      dbPrpJpayRefVoucher.insert(dbpool);
      


      





























































































      
      DBPrpJinvest dbPrpJinvest = null;
      double payRefFeeSum = 0;
      for(int i=0;i<this.getSize();i++){
        dbPrpJinvest = new DBPrpJinvest();
        dbPrpJinvest.setSchema(this.getArr(i));


        dbPrpJinvest.setCenterCode(strCenterCode);
        dbPrpJinvest.setBranchCode("0");



        dbPrpJinvest.setPayRefDate(strPayRefDate);
        dbPrpJinvest.setCurrency2(this.getArr(i).getCurrency1());
        dbPrpJinvest.setPayRefFee(this.getArr(i).getPlanFee());
        dbPrpJinvest.setRealPayRefFee(this.getArr(i).getPlanFee());
        
        dbPrpJinvest.setRealPayRefNo(strRealPayRefNo);
        payRefFeeSum += Double.parseDouble(this.getArr(i).getPlanFee());
        
        dbPrpJinvest.update(dbpool);
      }
      
      
      payRefFeeSum = Str.round(Str.round(payRefFeeSum, 8), 2);
      DBPrpJpayRefDetail dbPrpJpayRefDetail = new DBPrpJpayRefDetail();
      dbPrpJpayRefDetail.setPayWay(this.blPrpJpayRefDetail.getArr(0).getPayWay());
      dbPrpJpayRefDetail.setPayRefNo(strRealPayRefNo);
      dbPrpJpayRefDetail.setPayRefDate(strPayRefDate);
      dbPrpJpayRefDetail.setPayRefFee(payRefFeeSum+"");
      dbPrpJpayRefDetail.setCurrency2(this.getArr(0).getCurrency1());
      dbPrpJpayRefDetail.setSerialNo("1");
      dbPrpJpayRefDetail.setRealPayRefNo(strRealPayRefNo);
      if(dbPrpJpayRefDetail.getPayWay().substring(0,1).equals("4")){
    	  dbPrpJpayRefDetail.setComCode(strComCode);
      }
      dbPrpJpayRefDetail.setBankCode(strBankCode);
      dbPrpJpayRefDetail.setAccountNo(strAccountNo);
      dbPrpJpayRefDetail.insert(dbpool);
      
      BLPrpJpaymentVoucher blPrpJpaymentVoucher = new BLPrpJpaymentVoucher();
      strVoucher[0] = blPrpJpaymentVoucher.callProcedure(dbpool,strRealPayRefNo);
      
      
      
      /****************************由本金数据生成XX应收应付数据*******************************/
      
      BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
      BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
      BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
      BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
      
            this.transInvestToPayRef(dbpool,blPrpJplanFee,blPrpJpayRefRec,blPrpJpayRefMain,strPackUnit);
      
      if((blPrpJplanFee.getSize() != 0) && (blPrpJplanFee.getArr(0).getCertiType().equals("P")))
      {
    	blPrpJplanCommission.transInvestCommission(dbpool,blPrpJplanFee, blPrpJpayRefRec.getArr(0).getPayRefDate());
      }
      
      /****************************生成XX应收凭证******************************************/


















































































      
      for(int i=0;i<blPrpJplanFee.getSize();i++){
        blPrpJplanFee.getArr(i).setCenterCode(strCenterCode);
        blPrpJplanFee.getArr(i).setBranchCode(strBranchCode);




        blPrpJplanFee.getArr(i).setVoucherNo("0");
        blPrpJplanFee.getArr(i).setPlanFeeCNY("0");
        blPrpJplanFee.getArr(i).setPayRefFee(blPrpJplanFee.getArr(i).getPlanFee());
        blPrpJplanFee.getArr(i).setRealPayRefFee(blPrpJplanFee.getArr(i).getPlanFee());
        blPrpJplanFee.getArr(i).setPayRefReason("R55");
      }

      /****************************生成XX实收凭证******************************************/
      
      dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
      String strRealPayRefNo1 = String.valueOf(Long.parseLong(strRealPayRefNo)+1);
      dbPrpJpayRefVoucher.setRealPayRefNo(strRealPayRefNo1);
      dbPrpJpayRefVoucher.setPayRefNoType("2");
      dbPrpJpayRefVoucher.setCenterCode(strCenterCode);
      dbPrpJpayRefVoucher.setVoucherDate(strPayRefDate);
      dbPrpJpayRefVoucher.setComCode(strBranchCode);
      dbPrpJpayRefVoucher.setPayRefCode(strPayRefCode);
      dbPrpJpayRefVoucher.setToCenterCode("0");
      dbPrpJpayRefVoucher.setAttribute2("0");
      dbPrpJpayRefVoucher.insert(dbpool);
      

      

















      for(int i=0;i<blPrpJpayRefRec.getSize();i++){















































        blPrpJpayRefRec.getArr(i).setOperatorCode(strPayRefCode);
        blPrpJpayRefRec.getArr(i).setOperateUnit(strPackUnit);
        blPrpJpayRefRec.getArr(i).setPayRefDate(strPayRefDate);
        
        blPrpJpayRefRec.getArr(i).setIdentifyType("1");
        blPrpJpayRefRec.getArr(i).setIdentifyNumber("1");
        

        
        blPrpJpayRefRec.getArr(i).setRealPayRefNo(strRealPayRefNo1);
        
        blPrpJpayRefRec.getArr(i).setPayRefReason("R55");
      }
      













      

















      
      












      
      
      
      blPrpJplanFee.save(dbpool);
      blPrpJpayRefRec.save(dbpool);

      
      for(int i=0;i<blPrpJplanFee.getSize();i++){
        DBPrpCplan dbPrpCplan = new DBPrpCplan();
        dbPrpCplan.getInfo(blPrpJplanFee.getArr(i).getPolicyNo(),
            blPrpJplanFee.getArr(i).getSerialNo());
        dbPrpCplan.setDelinquentFee("0");
        dbPrpCplan.update(dbpool);
      }
      
      BLPrpJpaymentVoucher blPrpJpaymentVoucher2 = new BLPrpJpaymentVoucher();
      strVoucher[2] = blPrpJpaymentVoucher2.callProcedure(dbpool,strRealPayRefNo1);
    }
    catch(Exception ex){
      ex.printStackTrace();
        throw ex;
    }
    return strVoucher;
  }
    /**
     * @desc 投联XXXXX退XXXXX确认入口
     * @param arrPolicyNo     XX号码
     * @param strPayRefDate   收付日期
     * @param strPackUnit     登陆机构
     * @param strOperatorCode 操作员代码
     * @param strBankItemCode 银行帐号对应的明细科目代码
     * @param strCheckType    结算单类型 
     * @param strCheckNo      结算单号
     * @returnR
     * @throws UserException
     * @throws Exception
     */
    public String[] InvestPolicyChangeVoucher(String[] arrPolicyNo,String strPayRefDate,String strPackUnit,
    										  String strOperatorCode,String strBankItemCode,
    										  String strAuxNumber,String strCheckType,String strCheckNo)
    throws UserException,Exception{
      String[] arrVouch = null;
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            
            arrVouch = this.InvestPolicyChangeVoucher(dbpool,arrPolicyNo,strPayRefDate,strPackUnit,strOperatorCode,
            		strBankItemCode,strAuxNumber,strCheckType,strCheckNo);
            dbpool.commitTransaction();
        }
        catch(Exception e) {
            e.printStackTrace();
            dbpool.rollbackTransaction();
            throw e;
        }finally {
          dbpool.close();
        }
        return arrVouch;

    }    
    /**
     * @desc 投联XXXXX退XXXXX确认入口
     * @param dbpool
     * @param arrCertiNo     业务号码
     * @param strPayRefDate   收付日期
     * @param strPackUnit     登陆机构
     * @param strOperatorCode 操作员代码
     * @param strBankItemCode 银行帐号对应的明细科目代码
     * @param strCheckType    结算单类型 
     * @param strCheckNo      结算单号
     * @returnR
     * @throws UserException
     * @throws Exception
     */
    public String[] InvestPolicyChangeVoucher(DbPool dbpool,String[] arrCertiNo,String strPayRefDate,String strPackUnit,
			  String strOperatorCode,String strBankItemCode,String strAuxNumber,String strCheckType,String strCheckNo)
    throws UserException,Exception{

    String[] strVoucher = new String[3];
    try
    {
      BLPrpDcompany        blPrpDcompany        = new BLPrpDcompany();
      BLPrpJinvestIncome   blPrpJinvestIncome   = new BLPrpJinvestIncome();      

      String strSQL = "";
      StringBuffer sqlBuffer = new StringBuffer();
      StringBuffer sqlBuffer1 = new StringBuffer();     
      
      sqlBuffer.append("  PayRefReason in('R04','P02','P01') and (CertiNo= '"+arrCertiNo[0]+"'  ");
      for (int i = 1; i < arrCertiNo.length; i++)
        sqlBuffer.append(" or CertiNo= '"+arrCertiNo[i]+"' ");
      sqlBuffer.append(")");
      sqlBuffer.append(" order by CertiNo,serialno ");
      strSQL = sqlBuffer.toString();
      this.initArr();
      this.query(dbpool,strSQL,0);
      
      if(this.getSize() == 0){
        
        logger.info("没有进行退XXXXX付款确认的数据，请重新选择付款确认记录！");
        
        return strVoucher;
      }
      





      
      String strComCode     = this.getArr(0).getComCode();
      String strCenterCode  = blPrpDcompany.getCenterCodeNew(dbpool,strComCode);
      if(strCenterCode.equals("")||strCenterCode.trim().length()==0){
        	throw new UserException( -98, -1167, "BLPrpJinvest.InvestPolicyChangeVoucher()", "核算单位取值失败！");
      }
      String strBranchCode  = strCenterCode;
















        /****************************生成退XXXXX付款确认凭证*******************************/
      
      DBAccBankAccount dbAccBankAccount = new DBAccBankAccount();
      String strBankCode = "";
      String strAccountNo ="";
      
      if (!strBankItemCode.equals("")&& strBankItemCode.trim().length() != 0) {
       	  String[] arrBankAccount = Str.split(strBankItemCode, "/");
       	  
       	  strBankCode = arrBankAccount[0];
       	  
       	  strAccountNo = arrBankAccount[1];
       	  
       	  dbAccBankAccount.getInfo(dbpool, strAccountNo, strCenterCode);
       	  String strSaveNature = dbAccBankAccount.getSaveNature();
       	  
       	  strBankItemCode = "0" + strSaveNature + "/" + strBankItemCode + "/";
	  }
      DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
      String strRealPayRefNo = dbPrpJpayRefVoucher.genRealPayRefNo();
      dbPrpJpayRefVoucher.setRealPayRefNo(strRealPayRefNo);
      dbPrpJpayRefVoucher.setPayRefNoType("A");
      dbPrpJpayRefVoucher.setCenterCode(strCenterCode);
      dbPrpJpayRefVoucher.setVoucherDate(strPayRefDate);
      dbPrpJpayRefVoucher.setComCode(strBranchCode);
      dbPrpJpayRefVoucher.setPayRefCode(strOperatorCode);
      dbPrpJpayRefVoucher.setToCenterCode("0");
      dbPrpJpayRefVoucher.setAttribute2(strAuxNumber);
      dbPrpJpayRefVoucher.insert(dbpool);
      
      




































































































      
      DBPrpJinvest dbPrpJinvest = null;
      double payRefFeeSum = 0;
      for(int i=0;i<this.getSize();i++){
        dbPrpJinvest = new DBPrpJinvest();
        dbPrpJinvest.setSchema(this.getArr(i));

        dbPrpJinvest.setCenterCode(strCenterCode);
        dbPrpJinvest.setBranchCode(strBranchCode);



        dbPrpJinvest.setPayRefDate(strPayRefDate);
        dbPrpJinvest.setCurrency2(this.getArr(i).getCurrency1());
        dbPrpJinvest.setPayRefFee(this.getArr(i).getPlanFee());
        dbPrpJinvest.setRealPayRefFee(this.getArr(i).getPlanFee());
        
        dbPrpJinvest.setRealPayRefNo(strRealPayRefNo);
        if (this.getArr(i).getPayRefReason().startsWith("P")){
            payRefFeeSum += Double.parseDouble(this.getArr(i).getPlanFee());
        }else if (this.getArr(i).getPayRefReason().startsWith("R")){
            payRefFeeSum -= Double.parseDouble(this.getArr(i).getPlanFee());
        }

        
        dbPrpJinvest.update(dbpool);
      }
      
      
      payRefFeeSum = Str.round(Str.round(payRefFeeSum, 8), 2);
      DBPrpJpayRefDetail dbPrpJpayRefDetail = new DBPrpJpayRefDetail();
      dbPrpJpayRefDetail.setPayWay(this.blPrpJpayRefDetail.getArr(0).getPayWay());
      dbPrpJpayRefDetail.setPayRefNo(strRealPayRefNo);
      dbPrpJpayRefDetail.setPayRefDate(strPayRefDate);
      dbPrpJpayRefDetail.setPayRefFee(payRefFeeSum+"");
      dbPrpJpayRefDetail.setCurrency2(this.getArr(0).getCurrency1());
      dbPrpJpayRefDetail.setSerialNo("1");
      dbPrpJpayRefDetail.setRealPayRefNo(strRealPayRefNo);
      if(dbPrpJpayRefDetail.getPayWay().substring(0,1).equals("4")){
    	  dbPrpJpayRefDetail.setComCode(strComCode);
      }
      dbPrpJpayRefDetail.setBankCode(strBankCode);
      dbPrpJpayRefDetail.setAccountNo(strAccountNo);
      dbPrpJpayRefDetail.insert(dbpool);
      
      
      BLPrpJpaymentVoucher blPrpJpaymentVoucher = new BLPrpJpaymentVoucher();
      strVoucher[0] = blPrpJpaymentVoucher.callProcedure(dbpool,strRealPayRefNo);
      
      /****************************由本金数据生成XX应收应付数据*******************************/
      
      BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
      BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
      BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
      
            this.transInvestToPayRef(dbpool,blPrpJplanFee,blPrpJpayRefRec,blPrpJpayRefMain,strPackUnit);

      /****************************生成XX实收凭证******************************************/
      
      dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
      String strRealPayRefNo1 = String.valueOf(Long.parseLong(strRealPayRefNo)+1);
      dbPrpJpayRefVoucher.setRealPayRefNo(strRealPayRefNo1);
      dbPrpJpayRefVoucher.setPayRefNoType("2");
      dbPrpJpayRefVoucher.setCenterCode(strCenterCode);
      dbPrpJpayRefVoucher.setVoucherDate(strPayRefDate);
      dbPrpJpayRefVoucher.setComCode(strBranchCode);
      dbPrpJpayRefVoucher.setPayRefCode(strOperatorCode);
      dbPrpJpayRefVoucher.setToCenterCode("0");
      dbPrpJpayRefVoucher.setAttribute2("0");
      dbPrpJpayRefVoucher.insert(dbpool);
      



















      for(int i=0;i<blPrpJpayRefRec.getSize();i++){












































        blPrpJpayRefRec.getArr(i).setOperatorCode(strOperatorCode);
        blPrpJpayRefRec.getArr(i).setOperateUnit(strPackUnit);
        blPrpJpayRefRec.getArr(i).setPayRefDate(strPayRefDate);
        
        blPrpJpayRefRec.getArr(i).setIdentifyType("1");
        blPrpJpayRefRec.getArr(i).setIdentifyNumber("1");
        

        
        blPrpJpayRefRec.getArr(i).setRealPayRefNo(strRealPayRefNo1);
        
        blPrpJpayRefRec.getArr(i).setPayRefReason("R55");
      }
      














      
      for(int i=0;i<blPrpJplanFee.getSize();i++){
        blPrpJplanFee.getArr(i).setCenterCode(strCenterCode);
        blPrpJplanFee.getArr(i).setBranchCode(strBranchCode);




        blPrpJplanFee.getArr(i).setVoucherNo("0");
        blPrpJplanFee.getArr(i).setPayRefFee(blPrpJplanFee.getArr(i).getPlanFee());
        blPrpJplanFee.getArr(i).setRealPayRefFee(blPrpJplanFee.getArr(i).getPlanFee());
        blPrpJplanFee.getArr(i).setPayRefReason("R55");
      }      
      

















      
      












      
      
      blPrpJplanFee.save(dbpool);
      blPrpJpayRefRec.save(dbpool);

      
      for(int i=0;i<blPrpJplanFee.getSize();i++){
    	  
    	DBPrpCplan dbPrpCplan = new DBPrpCplan();
    	dbPrpCplan.getInfo(dbpool,blPrpJplanFee.getArr(i).getPolicyNo(),
            blPrpJplanFee.getArr(i).getSerialNo());
    	dbPrpCplan.setDelinquentFee("0");           
    	dbPrpCplan.update(dbpool);
      }
      
      BLPrpJpaymentVoucher blPrpJpaymentVoucher2 = new BLPrpJpaymentVoucher();
      strVoucher[1] = blPrpJpaymentVoucher2.callProcedure(dbpool,strRealPayRefNo1);
      /****************************生成投资收益计提凭证******************************************/


































































































    }
    catch(Exception ex){
      ex.printStackTrace();
        throw ex;
    }
    return strVoucher;
  }
    
    /**
     * @desc 投联XXXXX退XXXXX确认入口  （代付） zhanglingjian 20080527
     * @param arrCertiNo     业务号码
     * @param strPayRefDate   收付日期
     * @param strPackUnit     登陆机构
     * @param strOperatorCode 操作员代码
     * @param strBankItemCode 银行帐号对应的明细科目代码
     * @param strCheckType    结算单类型 
     * @param strCheckNo      结算单号
     * @param homeCenterCode  我方核算单位
     * @param toCenterCode    往来单位
     * @returnR
     * @throws UserException
     * @throws Exception
     */
    public String[] InvestPolicyChangeVoucherCurrent(String[] arrCertiNo,String strPayRefDate,String strPackUnit,
    										  String strOperatorCode,String strBankItemCode,
    										  String strAuxNumber,String strCheckType,String strCheckNo,
    										  String homeCenterCode,String toCenterCode)
    throws UserException,Exception{
      String[] arrVouch = null;
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            
            arrVouch = this.InvestPolicyChangeVoucherCurrent(dbpool,arrCertiNo,strPayRefDate,strPackUnit,strOperatorCode,
            		strBankItemCode,strAuxNumber,strCheckType,strCheckNo,homeCenterCode,toCenterCode);
            dbpool.commitTransaction();
        }
        catch(Exception e) {
            e.printStackTrace();
            dbpool.rollbackTransaction();
            throw e;
        }finally {
          dbpool.close();
        }
        return arrVouch;

    }
    
    /**
     * @desc 投联XXXXX退XXXXX确认入口  （代付） zhanglingjian 20080527
     * @param dbpool
     * @param arrCertiNo     批单号码
     * @param strPayRefDate   收付日期
     * @param strPackUnit     登陆机构
     * @param strOperatorCode 操作员代码
     * @param strBankItemCode 银行帐号对应的明细科目代码
     * @param strCheckType    结算单类型 
     * @param strCheckNo      结算单号
     * @param homeCenterCode  我方核算单位
     * @param toCenterCode    往来单位
     * @returnR
     * @throws UserException
     * @throws Exception
     */
    public String[] InvestPolicyChangeVoucherCurrent(DbPool dbpool,String[] arrCertiNo,String strPayRefDate,String strPackUnit,
			  String strOperatorCode,String strBankItemCode,String strAuxNumber,String strCheckType,String strCheckNo,
			  String homeCenterCode,String toCenterCode)
    throws UserException,Exception{
    String[] strVoucher = new String[3];
    try
    {
      String strSQL = "";
      StringBuffer sqlBuffer = new StringBuffer();   
      
      sqlBuffer.append("  PayRefReason in('R04','P02','P01') and (CertiNo= '"+arrCertiNo[0]+"'  ");
      for (int i = 1; i < arrCertiNo.length; i++)
        sqlBuffer.append(" or CertiNo= '"+arrCertiNo[i]+"' ");
      sqlBuffer.append(")");
      sqlBuffer.append(" order by CertiNo,serialno ");
      strSQL = sqlBuffer.toString();
      this.initArr();
      this.query(dbpool,strSQL,0);
      
      String strComCode = this.getArr(0).getComCode();
      
      if(this.getSize() == 0){
        
        logger.info("没有进行退XXXXX付款确认的数据，请重新选择付款确认记录！");
        
        return strVoucher;
      }
      
      /****************************生成退XXXXX付款确认凭证*******************************/
      
      DBAccBankAccount dbAccBankAccount = new DBAccBankAccount();
      String strBankCode = "";
      String strAccountNo ="";
      
      if (!strBankItemCode.equals("")&& strBankItemCode.trim().length() != 0) {
       	  String[] arrBankAccount = Str.split(strBankItemCode, "/");
       	  
       	  strBankCode = arrBankAccount[0];
       	  
       	  strAccountNo = arrBankAccount[1];
       	  
       	  dbAccBankAccount.getInfo(dbpool, strAccountNo, homeCenterCode);
       	  String strSaveNature = dbAccBankAccount.getSaveNature();
       	  
       	  strBankItemCode = "0" + strSaveNature + "/" + strBankItemCode + "/";
	  }
      DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
      String strRealPayRefNo = dbPrpJpayRefVoucher.genRealPayRefNo();
      dbPrpJpayRefVoucher.setRealPayRefNo(strRealPayRefNo);
      dbPrpJpayRefVoucher.setPayRefNoType("A");
      dbPrpJpayRefVoucher.setCenterCode(homeCenterCode);
      dbPrpJpayRefVoucher.setVoucherDate(strPayRefDate);
      dbPrpJpayRefVoucher.setComCode(homeCenterCode);
      dbPrpJpayRefVoucher.setPayRefCode(strOperatorCode);
      dbPrpJpayRefVoucher.setToCenterCode(toCenterCode);
      dbPrpJpayRefVoucher.setAttribute2(strAuxNumber);
      dbPrpJpayRefVoucher.insert(dbpool);
      
      
      DBPrpJinvest dbPrpJinvest = null;
      double payRefFeeSum = 0;
      for(int i=0;i<this.getSize();i++){
        dbPrpJinvest = new DBPrpJinvest();
        dbPrpJinvest.setSchema(this.getArr(i));
        dbPrpJinvest.setPayRefDate(strPayRefDate);
        dbPrpJinvest.setCurrency2(this.getArr(i).getCurrency1());
        dbPrpJinvest.setPayRefFee(this.getArr(i).getPlanFee());
        dbPrpJinvest.setRealPayRefFee(this.getArr(i).getPlanFee());
        
        dbPrpJinvest.setRealPayRefNo(strRealPayRefNo);
        if (this.getArr(i).getPayRefReason().startsWith("P")){
            payRefFeeSum += Double.parseDouble(this.getArr(i).getPlanFee());
        }else if (this.getArr(i).getPayRefReason().startsWith("R")){
            payRefFeeSum -= Double.parseDouble(this.getArr(i).getPlanFee());
        }

        
        dbPrpJinvest.update(dbpool);
      }
      
      
      payRefFeeSum = Str.round(Str.round(payRefFeeSum, 8), 2);
      DBPrpJpayRefDetail dbPrpJpayRefDetail = new DBPrpJpayRefDetail();
      dbPrpJpayRefDetail.setPayWay(this.blPrpJpayRefDetail.getArr(0).getPayWay());
      dbPrpJpayRefDetail.setPayRefNo(strRealPayRefNo);
      dbPrpJpayRefDetail.setPayRefDate(strPayRefDate);
      dbPrpJpayRefDetail.setPayRefFee(payRefFeeSum+"");
      dbPrpJpayRefDetail.setCurrency2(this.getArr(0).getCurrency1());
      dbPrpJpayRefDetail.setSerialNo("1");
      dbPrpJpayRefDetail.setRealPayRefNo(strRealPayRefNo);
      if(dbPrpJpayRefDetail.getPayWay().substring(0,1).equals("4")){
    	  dbPrpJpayRefDetail.setComCode(strComCode);
      }
      dbPrpJpayRefDetail.setBankCode(strBankCode);
      dbPrpJpayRefDetail.setAccountNo(strAccountNo);
      dbPrpJpayRefDetail.insert(dbpool);
      
      
      BLPrpJpaymentVoucher blPrpJpaymentVoucher = new BLPrpJpaymentVoucher();
      strVoucher[0] = blPrpJpaymentVoucher.callProcedure(dbpool,strRealPayRefNo);
      
      /****************************由本金数据生成XX应收应付数据*******************************/
      
      BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
      BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
      BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
      
      this.transInvestToPayRef(dbpool,blPrpJplanFee,blPrpJpayRefRec,blPrpJpayRefMain,strPackUnit);

      /****************************生成XX实收凭证******************************************/
      
      dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
      String strRealPayRefNo1 = String.valueOf(Long.parseLong(strRealPayRefNo)+1);
      dbPrpJpayRefVoucher.setRealPayRefNo(strRealPayRefNo1);
      dbPrpJpayRefVoucher.setPayRefNoType("2");
      dbPrpJpayRefVoucher.setCenterCode(toCenterCode);
      dbPrpJpayRefVoucher.setVoucherDate(strPayRefDate);
      dbPrpJpayRefVoucher.setComCode(homeCenterCode);
      dbPrpJpayRefVoucher.setPayRefCode(strOperatorCode);
      dbPrpJpayRefVoucher.setToCenterCode("0");
      dbPrpJpayRefVoucher.setAttribute2("0");
      dbPrpJpayRefVoucher.insert(dbpool);
      
      for(int i=0;i<blPrpJpayRefRec.getSize();i++){
        blPrpJpayRefRec.getArr(i).setOperatorCode(strOperatorCode);
        blPrpJpayRefRec.getArr(i).setOperateUnit(strPackUnit);
        blPrpJpayRefRec.getArr(i).setPayRefDate(strPayRefDate);
        blPrpJpayRefRec.getArr(i).setIdentifyType("1");
        blPrpJpayRefRec.getArr(i).setIdentifyNumber("1");
        blPrpJpayRefRec.getArr(i).setRealPayRefNo(strRealPayRefNo1);
        blPrpJpayRefRec.getArr(i).setPayRefReason("R55");
      }

      
      for(int i=0;i<blPrpJplanFee.getSize();i++){
        blPrpJplanFee.getArr(i).setVoucherNo("0");
        blPrpJplanFee.getArr(i).setPayRefFee(blPrpJplanFee.getArr(i).getPlanFee());
        blPrpJplanFee.getArr(i).setRealPayRefFee(blPrpJplanFee.getArr(i).getPlanFee());
        blPrpJplanFee.getArr(i).setPayRefReason("R55");
      }
      
      blPrpJplanFee.save(dbpool);
      blPrpJpayRefRec.save(dbpool);
      
      
      for(int i=0;i<blPrpJplanFee.getSize();i++){
    	DBPrpCplan dbPrpCplan = new DBPrpCplan();
    	dbPrpCplan.getInfo(dbpool,blPrpJplanFee.getArr(i).getPolicyNo(),
            blPrpJplanFee.getArr(i).getSerialNo());
    	dbPrpCplan.setDelinquentFee("0");
    	dbPrpCplan.update(dbpool);
      }
      
      BLPrpJpaymentVoucher blPrpJpaymentVoucher2 = new BLPrpJpaymentVoucher();
      strVoucher[1] = blPrpJpaymentVoucher2.callProcedure(dbpool,strRealPayRefNo1);
    }
    catch(Exception ex){
      ex.printStackTrace();
        throw ex;
    }
    return strVoucher;
  }
    
    /**
     * @desc 将本金对应XX收付信息写入收付表中
     * @param blPrpJinvest
     * @param blPrpJplanFee
     * @param blPrpJpayRefRec
     * @param blPrpJpayRefMain
     * @throws UserException
     * @throws Exception
     */
    public void transInvestToPayRef(DbPool dbpool,BLPrpJplanFee blPrpJplanFee,
                                BLPrpJpayRefRec blPrpJpayRefRec,BLPrpJpayRefMain blPrpJpayRefMain,String strPackUnit)
    throws UserException,Exception{
      try{
      ChgDate chgDate  = new ChgDate();
        String currentDate = chgDate.getCurrentTime("yyyy-MM-dd");
        PrpJplanFeeSchema prpJplanFeeSchema = null;
        PrpJpayRefRecSchema blPrpJpayRefRecSchema = null;
        double dSumPlanFee = 0;
        int j = 0;
        for(int i=0;i<this.getSize();i++){
        
        if(this.getArr(i).getCertiType().equals("P")){
          
          prpJplanFeeSchema = transPremiumPolicy(dbpool,this.getArr(i).getCertiNo(),"1");
          
          prpJplanFeeSchema.setPayRefDate(currentDate);
          
          blPrpJplanFee.setArr(prpJplanFeeSchema);
        }else if (this.getArr(i).getCertiType().equals("E")){
        	if(this.getArr(i).getPayRefReason().equals("P02")){
        		prpJplanFeeSchema = this.transPremiumEndor(dbpool,this.getArr(i).getCertiNo(),"2");
        		
        		prpJplanFeeSchema.setPayRefDate(currentDate);
        		
        		blPrpJplanFee.setArr(prpJplanFeeSchema);
        	}
        }
        else
          throw new UserException(-98,-1167,"BLPrpJinvset.transInvestToPayRef",
                  "本金数据传入收付错误，没有此业务类型："+this.getArr(i).getCertiType()+"处理方式！");
            
      if(this.getArr(i).getCertiType().equals("E")
    		  &&(this.getArr(i).getPayRefReason().equals("R04")
    		  ||this.getArr(i).getPayRefReason().equals("P01"))){
    	  continue;
      }
      blPrpJpayRefRecSchema = transToPayRefRec(prpJplanFeeSchema);
      blPrpJpayRefRec.setArr(blPrpJpayRefRecSchema);     
      dSumPlanFee += Double.parseDouble(blPrpJpayRefRec.getArr(j).getPlanFee());
      j++;
    }
    dSumPlanFee = Str.round(Str.round(dSumPlanFee, 8), 2);
    
        












        }catch(Exception e){
        e.printStackTrace();
        throw e;
      }
    }
    /**
     * @desc 生成XX应收表信息
     * @param dbpool
     * @param iPolicyNo
     * @param iSerialNo
     * @throws UserException
     * @throws Exception
     */
    public PrpJplanFeeSchema transPremiumPolicy(DbPool dbpool,String iPolicyNo,String iSerialNo)
        throws UserException,Exception{
      try{
            DBPrpCmain dbPrpCmain = new DBPrpCmain();
            BLPrpCplan blPrpCplan = new BLPrpCplan();
            String strWherePart = "";
            int intReturn = 0;
            intReturn = dbPrpCmain.getInfo(dbpool,iPolicyNo);
            if(intReturn == 100)
              throw new UserException( -98, -1167, "BLPrpJplanFee.transPolicy", "无此XX信息："+iPolicyNo);

            strWherePart = "PolicyNo='"+iPolicyNo+"'"+" AND SerialNo='"+iSerialNo+"'"+" AND (EndorseNo IS NULL OR EndorseNo='')";
            blPrpCplan.query(dbpool,strWherePart,0);
            
            BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
            String strCenterCode = blPrpDcompany.getCenterCode(dbpool,dbPrpCmain.getComCode());
            if(strCenterCode.equals("")||strCenterCode.trim().length()==0){
              	throw new UserException( -98, -1167, "BLPrpJinvest.transPolicy()", "核算单位取值失败！");
            }
            
            PrpJplanFeeSchema schema = new PrpJplanFeeSchema();
            schema.setCertiType("P");
            schema.setCertiNo(iPolicyNo);
            schema.setSerialNo(blPrpCplan.getArr(0).getSerialNo());
            schema.setPolicyNo(iPolicyNo);
            schema.setPayRefReason(blPrpCplan.getArr(0).getPayReason());
            schema.setClassCode(dbPrpCmain.getClassCode());
            schema.setRiskCode(dbPrpCmain.getRiskCode());
            schema.setContractNo(dbPrpCmain.getContractNo());
            schema.setAppliCode(dbPrpCmain.getAppliCode());
            schema.setAppliName(dbPrpCmain.getAppliName());
            schema.setInsuredCode(dbPrpCmain.getInsuredCode());
            schema.setInsuredName(dbPrpCmain.getInsuredName());
            schema.setStartDate(dbPrpCmain.getStartDate());
            schema.setEndDate(dbPrpCmain.getEndDate());
            schema.setPayNo(blPrpCplan.getArr(0).getPayNo());
            schema.setCurrency1(blPrpCplan.getArr(0).getCurrency());
            schema.setPlanFee(blPrpCplan.getArr(0).getPlanFee());
            schema.setPlanDate(blPrpCplan.getArr(0).getPlanDate());
            schema.setComCode(dbPrpCmain.getComCode());
            schema.setMakeCom(dbPrpCmain.getMakeCom());
            schema.setAgentCode(dbPrpCmain.getAgentCode());
            schema.setHandler1Code(dbPrpCmain.getHandler1Code());
            schema.setHandlerCode(dbPrpCmain.getHandlerCode());
            schema.setUnderWriteDate(dbPrpCmain.getUnderWriteEndDate());
            schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
            schema.setExchangeRate("1.0");
            schema.setPlanFeeCNY(blPrpCplan.getArr(0).getPlanFee());
            schema.setPayRefFee(blPrpCplan.getArr(0).getPlanFee());
            schema.setRealPayRefFee("0");
            
            if("1".equals(dbPrpCmain.getAllinsFlag())){
            	schema.setFlag("01");
            }
            else{
            	schema.setFlag("00");
            }
            
            
            schema.setCenterCode(strCenterCode);
            schema.setBusinessNature(dbPrpCmain.getBusinessNature());
            
            
            schema.setChannelType(dbPrpCmain.getChannelType());
            
            return schema;
      }catch(Exception e){
        e.printStackTrace();
        throw e;
      }
    }
    /**
     * @desc 生成批单应收表信息
     * auther xushaojiang
     * @param dbpool
     * @param iPolicyNo
     * @param iSerialNo
     * @throws UserException
     * @throws Exception
     */
    public PrpJplanFeeSchema transPremiumEndor(DbPool dbpool,String iCertiNo,String iSerialNo)
        throws UserException,Exception{
      try{
            DBPrpCmain dbPrpCmain = new DBPrpCmain();
            BLPrpCplan blPrpCplan = new BLPrpCplan();
            DBPrpPhead dbPrpPhead = new DBPrpPhead();
            String strWherePart = "";
            int intReturn = 0;
            intReturn = dbPrpPhead.getInfo(dbpool,iCertiNo);            
            if(intReturn == 100)
              throw new UserException( -98, -1167, "BLPrpJinvest.transEndor", "无此批单信息："+iCertiNo);
            intReturn = dbPrpCmain.getInfo(dbpool,dbPrpPhead.getPolicyNo());            
            
            if(intReturn == 100)
              throw new UserException( -98, -1167, "BLPrpJplanFee.transEndor", "无此批单对应XX信息："+iCertiNo);

            
            strWherePart = " EndorseNo='"+iCertiNo+"' ";
            
            blPrpCplan.query(dbpool,strWherePart,0);
            if(blPrpCplan.getSize()==0)
                throw new UserException( -98, -1167, "BLPrpJplanFee.transEndor", "无此批单XX缴费计划信息："+iCertiNo);
            
            BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
            String strCenterCode = blPrpDcompany.getCenterCode(dbpool,dbPrpCmain.getComCode());
            if(strCenterCode.equals("")||strCenterCode.trim().length()==0){
            	throw new UserException( -98, -1167, "BLPrpJinvest.transEndor()", "核算单位取值失败！");
          }
            
            PrpJplanFeeSchema schema = new PrpJplanFeeSchema();
            schema.setCertiType("E");
            schema.setCertiNo(iCertiNo);
            
            schema.setSerialNo(blPrpCplan.getArr(0).getSerialNo());
            
            schema.setPolicyNo(dbPrpCmain.getPolicyNo());
            schema.setPayRefReason(blPrpCplan.getArr(0).getPayReason());
            schema.setClassCode(dbPrpCmain.getClassCode());
            schema.setRiskCode(dbPrpCmain.getRiskCode());
            schema.setContractNo(dbPrpCmain.getContractNo());
            schema.setAppliCode(dbPrpCmain.getAppliCode());
            schema.setAppliName(dbPrpCmain.getAppliName());
            schema.setInsuredCode(dbPrpCmain.getInsuredCode());
            schema.setInsuredName(dbPrpCmain.getInsuredName());
            schema.setStartDate(dbPrpPhead.getValidDate());
            schema.setValidDate(dbPrpPhead.getValidDate());            
            schema.setEndDate(dbPrpCmain.getEndDate());
            schema.setPayNo(blPrpCplan.getArr(0).getPayNo());
            schema.setCurrency1(blPrpCplan.getArr(0).getCurrency());
            schema.setPlanFee(blPrpCplan.getArr(0).getPlanFee());
            schema.setPlanDate(blPrpCplan.getArr(0).getPlanDate());
            schema.setComCode(dbPrpCmain.getComCode());
            schema.setMakeCom(dbPrpCmain.getMakeCom());
            schema.setAgentCode(dbPrpCmain.getAgentCode());
            schema.setHandler1Code(dbPrpCmain.getHandler1Code());
            schema.setHandlerCode(dbPrpCmain.getHandlerCode());
            schema.setUnderWriteDate(dbPrpCmain.getUnderWriteEndDate());
            schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
            schema.setExchangeRate("1.0");
            schema.setPlanFeeCNY(blPrpCplan.getArr(0).getPlanFee());
            schema.setPayRefFee(blPrpCplan.getArr(0).getPlanFee());
            schema.setRealPayRefFee(blPrpCplan.getArr(0).getPlanFee());
            
            if("1".equals(dbPrpCmain.getAllinsFlag())){
            	schema.setFlag("01");
            }
            else{
            	schema.setFlag("00");
            }
            
            
            schema.setCenterCode(strCenterCode);
            schema.setBusinessNature(dbPrpCmain.getBusinessNature());
            
            
            schema.setChannelType(dbPrpCmain.getChannelType());
            
            return schema;
      }catch(Exception e){
        e.printStackTrace();
        throw e;
      }
    }
    /**
     * @desc 生成XX实收表信息
     * @param dbpool
     * @param iPolicyNo
     * @param iSerialNo
     * @throws UserException
     * @throws Exception
     */
    public PrpJpayRefRecSchema transToPayRefRec(PrpJplanFeeSchema blPrpJplanFeeSchema) throws UserException,Exception
    {
      ChgDate chgDate = new ChgDate();
        String currentDate = chgDate.getCurrentTime("yyyy-MM-dd");
      PrpJpayRefRecSchema blPrpJpayRefRecSchema = new PrpJpayRefRecSchema();
    blPrpJpayRefRecSchema.setPayRefTimes("1");
    blPrpJpayRefRecSchema.setOperateDate(currentDate);
    blPrpJpayRefRecSchema.setCertiType(blPrpJplanFeeSchema.getCertiType());
    blPrpJpayRefRecSchema.setCertiNo(blPrpJplanFeeSchema.getCertiNo());
    blPrpJpayRefRecSchema.setPolicyNo(blPrpJplanFeeSchema.getPolicyNo());
    blPrpJpayRefRecSchema.setSerialNo(blPrpJplanFeeSchema.getSerialNo());
    blPrpJpayRefRecSchema.setPayRefReason(blPrpJplanFeeSchema.getPayRefReason());
    blPrpJpayRefRecSchema.setClaimNo(blPrpJplanFeeSchema.getClaimNo());
    blPrpJpayRefRecSchema.setClassCode(blPrpJplanFeeSchema.getClassCode());
    blPrpJpayRefRecSchema.setRiskCode(blPrpJplanFeeSchema.getRiskCode());
    blPrpJpayRefRecSchema.setContractNo(blPrpJplanFeeSchema.getContractNo());
    blPrpJpayRefRecSchema.setAppliCode(blPrpJplanFeeSchema.getAppliCode());
    blPrpJpayRefRecSchema.setAppliName(blPrpJplanFeeSchema.getAppliName());
    blPrpJpayRefRecSchema.setInsuredCode(blPrpJplanFeeSchema.getInsuredCode());
    blPrpJpayRefRecSchema.setInsuredName(blPrpJplanFeeSchema.getInsuredName());
    blPrpJpayRefRecSchema.setStartDate(blPrpJplanFeeSchema.getStartDate());
    blPrpJpayRefRecSchema.setEndDate(blPrpJplanFeeSchema.getEndDate());
    blPrpJpayRefRecSchema.setValidDate(blPrpJplanFeeSchema.getValidDate());
    blPrpJpayRefRecSchema.setPayNo(blPrpJplanFeeSchema.getPayNo());
    blPrpJpayRefRecSchema.setCurrency1(blPrpJplanFeeSchema.getCurrency1());
    blPrpJpayRefRecSchema.setPlanFee(blPrpJplanFeeSchema.getPlanFee());
    blPrpJpayRefRecSchema.setPlanDate(blPrpJplanFeeSchema.getPlanDate());
    blPrpJpayRefRecSchema.setComCode(blPrpJplanFeeSchema.getComCode());
    blPrpJpayRefRecSchema.setMakeCom(blPrpJplanFeeSchema.getMakeCom());
    blPrpJpayRefRecSchema.setAgentCode(blPrpJplanFeeSchema.getAgentCode());
    blPrpJpayRefRecSchema.setHandler1Code(blPrpJplanFeeSchema.getHandler1Code());
    blPrpJpayRefRecSchema.setHandlerCode(blPrpJplanFeeSchema.getHandlerCode());
    blPrpJpayRefRecSchema.setUnderWriteDate(blPrpJplanFeeSchema.getUnderWriteDate());
    blPrpJpayRefRecSchema.setCoinsFlag(blPrpJplanFeeSchema.getCoinsFlag());
    blPrpJpayRefRecSchema.setCoinsCode(blPrpJplanFeeSchema.getCoinsCode());
    blPrpJpayRefRecSchema.setCoinsName(blPrpJplanFeeSchema.getCoinsName());
    blPrpJpayRefRecSchema.setCoinsType(blPrpJplanFeeSchema.getCoinsType());
    blPrpJpayRefRecSchema.setExchangeRate(blPrpJplanFeeSchema.getExchangeRate());
    blPrpJpayRefRecSchema.setPayRefFee(blPrpJplanFeeSchema.getPayRefFee());
    blPrpJpayRefRecSchema.setCarNatureCode(blPrpJplanFeeSchema.getCarNatureCode());
    blPrpJpayRefRecSchema.setUseNatureCode(blPrpJplanFeeSchema.getUseNatureCode());
    blPrpJpayRefRecSchema.setCarProperty(blPrpJplanFeeSchema.getCarProperty());
    blPrpJpayRefRecSchema.setCurrency2("CNY");
    blPrpJpayRefRecSchema.setExchangeRate("1");
    
    blPrpJpayRefRecSchema.setIdentifyType("1");
    blPrpJpayRefRecSchema.setIdentifyNumber("1");
    blPrpJpayRefRecSchema.setFlag("0");
    
    blPrpJpayRefRecSchema.setPayRefDate(blPrpJplanFeeSchema.getPayRefDate());
    
    blPrpJpayRefRecSchema.setCenterCode(blPrpJplanFeeSchema.getCenterCode());
    blPrpJpayRefRecSchema.setBusinessNature(blPrpJplanFeeSchema.getBusinessNature());
    
    
    blPrpJpayRefRecSchema.setChannelType(blPrpJplanFeeSchema.getChannelType());
    
        return blPrpJpayRefRecSchema;    
    }
    /**
     * @desc 查找核算单位所有代理银行
     * @param strCondition
     * @return
     * @throws UserException
     * @throws Exception
     */
    public ArrayList getAgentBankCode(String strCondition)
     throws UserException,Exception{
      ArrayList arrBankList = new ArrayList();
      DbPool dbpool = new DbPool();
      ResultSet rs = null;
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            String strSQL = " select bankcode,bankname from prpdbank where "+strCondition;
            rs = dbpool.query(strSQL);
            while(rs.next()){
              arrBankList.add(rs.getString("BankCode")+"-"+rs.getString("BankName"));
            }
        } catch (Exception e) {
        }
        finally {
            try {
                rs.close();
            } catch(Exception e1) {
            }
            try {
                dbpool.close();
            } catch(Exception e2) {
            }
        }
        return arrBankList;
    }
    /**
     * @desc 返回blAccMainVoucher
     * @return
     * @throws Exception
     */
    public BLAccMainVoucher getBLAccMainVoucher() throws Exception
    {
      return this.blAccMainVoucher;
    }
    
    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成收付接口表信息对象BLPrpJinvest add by zhanglingjian 20080613
     * @param bankInterFace_DetailDto
     * @param blPolicy
     * @return blPrpJinvest
     * @throws Exception
     */
    public void generateObjectOfJinvest(BankInterFace_DetailDto bankInterFace_DetailDto, BLPolicy blPolicy) throws
        Exception,SQLException,NamingException{
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            generateObjectOfJinvest(dbpool,bankInterFace_DetailDto,blPolicy);
            dbpool.close();
        }
        catch(SQLException sqlException){
            dbpool.close();
            throw sqlException;
        }
        catch(NamingException namingException){
            dbpool.close();
            throw namingException;
        }
        finally {
            dbpool.close();
        }
    }

    /**
     * @desc 投联XXXXX由接口表数据生成XX数据：生成收付接口表信息对象BLPrpJinvest
     * @param dbpool
     * @param bankInterFace_DetailDto
     * @param blPolicy
     * @return blPrpJinvest
     * @throws Exception
     */
    public void generateObjectOfJinvest(DbPool dbpool,BankInterFace_DetailDto bankInterFace_DetailDto, BLPolicy blPolicy) throws Exception {
        PrpJinvestSchema prpJinvestSchema = new PrpJinvestSchema();

        PrpCmainSchema prpCmainSchema = new PrpCmainSchema();
        PrpCinsuredSchema prpCinsuredSchema = new PrpCinsuredSchema();
        PrpCmainInvestSchema prpCmainInvestSchema = new PrpCmainInvestSchema();

        DateTime currentDate = new DateTime().current();
        DateTime investDate = null;

        String strCurrentDate = "";
        String strUnderWriteEndDate = "";
        String strInvestDate = "";
        String strProcSeq = "";
        String strCenterCode = "";
        String strToCenterCode = "";
        String strToComCode = "";
        
        if(blPolicy != null && blPolicy.getBLPrpCmain().getSize() > 0) {
            prpCmainSchema = blPolicy.getBLPrpCmain().getArr(0);
            
            
            BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
            strCenterCode = blPrpDcompany.getCenterCodeNew(dbpool,prpCmainSchema.getComCode());
            if(strCenterCode.equals("")||strCenterCode.trim().length()==0){
            	throw new UserException( -98, -1167, "BLPrpJinvest.generateObjectOfJinvest(BankInterFace_DetailDto,BLPolicy)", "核算单位取值失败！");
            }
        }
        if(blPolicy != null && blPolicy.getBLPrpCinsured().getSize() > 0) {
            prpCinsuredSchema = blPolicy.getBLPrpCinsured().getArr(0); 
        }
        if(blPolicy != null && blPolicy.getBLPrpCmainInvest().getSize() > 0) {
            prpCmainInvestSchema = blPolicy.getBLPrpCmainInvest().getArr(0); 
        }
        
        
        if(bankInterFace_DetailDto != null)
        {
        	strToComCode = bankInterFace_DetailDto.getRevolutionFlag();
        	if(strToComCode != null && !strToComCode.equals(""))
        	{
        		BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
                strToCenterCode = blPrpDcompany.getCenterCodeNew(dbpool,strToComCode);
        	}
        }

        strUnderWriteEndDate = prpCmainSchema.getUnderWriteEndDate();
        strCurrentDate = new Integer(currentDate.getYear()).toString() + "-"
                       + new Integer(currentDate.getMonth()).toString() + "-"
                       + new Integer(currentDate.getDay()).toString();
        if(strUnderWriteEndDate.trim().length() == 0) {
            strUnderWriteEndDate = strCurrentDate;
        }
        if(prpCmainSchema.getStartDate().trim().length() > 0) {
            investDate = new DateTime(prpCmainSchema.getStartDate()).addDay(-1);
            strInvestDate = new Integer(investDate.getYear()).toString() + "-"
                          + new Integer(investDate.getMonth()).toString() + "-"
                          + new Integer(investDate.getDay()).toString();
        }
        
        
        DBManager dbManager= new DBManager();
		dbManager = dbpool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);
		DBPrpDagent dbPrpDagent = new DBPrpDagent(dbManager);
		PrpDagentDto prpDagentDto = dbPrpDagent.findByPrimaryKey(prpCmainSchema.getAgentCode());
		if (prpDagentDto==null){
			throw new UserException(-98, -1167,
					"BLPrpJpayInvest.transData", "XX：" + prpCmainSchema.getPolicyNo()
							+ "的代理银行没有在平台系统做配置！");
		}else if(prpDagentDto.getAgentBankCode()==null||prpDagentDto.getAgentBankCode().equals("")){
			throw new UserException(-98, -1167,
					"BLPrpJpayInvest.transData", "XX：" + prpCmainSchema.getPolicyNo()
							+ "的代理银行没有在平台系统做“银行名称”的配置！");
		}
        
        strProcSeq = bankInterFace_DetailDto.getUploadfileSeq();
        prpJinvestSchema.setProcSeq(strProcSeq); 
        prpJinvestSchema.setCertiNo(prpCmainSchema.getPolicyNo()); 
        prpJinvestSchema.setCertiType("P"); 
        prpJinvestSchema.setPolicyNo(prpCmainSchema.getPolicyNo()); 
        prpJinvestSchema.setSerialNo("0"); 
        prpJinvestSchema.setPayRefReason("R01"); 
        prpJinvestSchema.setClassCode(prpCmainSchema.getClassCode()); 
        prpJinvestSchema.setRiskCode(prpCmainSchema.getRiskCode()); 
        prpJinvestSchema.setContractNo(prpCmainSchema.getContractNo()); 
        prpJinvestSchema.setAppliCode(prpCmainSchema.getAppliCode()); 
        prpJinvestSchema.setAppIdno(prpCinsuredSchema.getIdentifyNumber()); 
        prpJinvestSchema.setAppliName(prpCmainSchema.getAppliName()); 
        prpJinvestSchema.setInsuredCode(prpCmainSchema.getInsuredCode()); 
        prpJinvestSchema.setInsuredName(prpCmainSchema.getInsuredName()); 
        prpJinvestSchema.setStartDate(prpCmainSchema.getStartDate()); 
        prpJinvestSchema.setEndDate(prpCmainSchema.getEndDate()); 
        prpJinvestSchema.setCurrency1("CNY"); 
        prpJinvestSchema.setPlanFee(prpCmainInvestSchema.getInvestment()); 
        prpJinvestSchema.setInvestCount(prpCmainInvestSchema.getQuantity()); 
        
        prpJinvestSchema.setLeftInvestMent(prpCmainInvestSchema.getInvestment());
        prpJinvestSchema.setLeftQuantity(prpCmainInvestSchema.getQuantity()); 
        
        prpJinvestSchema.setComCode(prpCmainSchema.getComCode()); 
        prpJinvestSchema.setMakeCom(prpCmainSchema.getMakeCom()); 
        prpJinvestSchema.setAgentCode(prpCmainSchema.getAgentCode()); 
        prpJinvestSchema.setHandler1Code(prpCmainSchema.getHandler1Code()); 
        prpJinvestSchema.setHandlerCode(prpCmainSchema.getHandlerCode()); 
        prpJinvestSchema.setUnderWriteDate(strUnderWriteEndDate); 
        prpJinvestSchema.setIncometimes("0"); 
        prpJinvestSchema.setIncomeFlag("0"); 
        
        prpJinvestSchema.setPayBankCode(prpDagentDto.getAgentBankCode()); 
        
        prpJinvestSchema.setPayAccountNo(prpCinsuredSchema.getAccount()); 
        prpJinvestSchema.setInvestDate(strInvestDate); 
        prpJinvestSchema.setExchangeRate("1"); 
        prpJinvestSchema.setPlanFeeCNY(prpCmainInvestSchema.getInvestment()); 
        prpJinvestSchema.setProposalNo(prpCmainSchema.getProposalNo()); 
        prpJinvestSchema.setBaseIncomeRate(prpCmainInvestSchema.getInterestRate()); 
        prpJinvestSchema.setFlag("E"); 
        
        prpJinvestSchema.setPaymentFlag("00");
        
        
        prpJinvestSchema.setCenterCode(strCenterCode);
        prpJinvestSchema.setBusinessNature(prpCmainSchema.getBusinessNature());
        
        
        prpJinvestSchema.setChannelType(prpCmainSchema.getChannelType());
        
        prpJinvestSchema.setToCenterCode(strToCenterCode);
        prpJinvestSchema.setToComCode(strToComCode);
        
        
        prpJinvestSchema.setReNewalFlag("0");
        prpJinvestSchema.setReNewalPolicyNo("");
        prpJinvestSchema.setReNewalPayment("0");
        
        
        this.setArr(prpJinvestSchema);
    }
    
    public void generateObjectOfJinvest(BLPolicy blPolicy) throws Exception {
        BankInterFace_DetailDto bankInterFace_DetailDto = new BankInterFace_DetailDto();

        if(blPolicy == null || blPolicy.getBLPrpCmain().getSize() == 0) {
            throw new UserException( -98, -1167, "BLPrpJinvest.generateObjectOfJinvest(BLPolicy)", "投连XXXXXXX单转XX时没有有效的XX对象！");
        } else {
            generateObjectOfJinvest(bankInterFace_DetailDto, blPolicy); 
        }
    }
    /**
     * @desc 计算n个月后的最后一天
     * @param strDate
     * @param intCount
     * @return
     */
    public  String getNextMonthEndDate(String strDate, int intCount) {
        String strReturn = "";
        int intYear = 0;
        int intMonth = 0;
        int intDate = 0;
        int month = 0;
        Date tempDate = new Date(strDate);
        month = tempDate.get(Date.MONTH) + intCount;
        intYear = month / 12;
        intMonth = month % 12;
        if(intMonth==0)
        	intMonth=1;
        intYear = intYear + tempDate.get(Date.YEAR);

        if (intMonth == 1 || intMonth == 3 || intMonth == 5 || intMonth == 7
                || intMonth == 8 || intMonth == 10 || intMonth == 12)
            intDate = 31;
        else if (intMonth == 4 || intMonth == 6 || intMonth == 9
                || intMonth == 11)
            intDate = 30;
        else  if(intMonth==2&&intYear%4!=0)
        	intDate = 28;
        else if(intMonth==2&&intYear%4==0)
            intDate = 29;
        
        if (intMonth < 10)
            strReturn = Integer.toString(intYear) + "-" + "0"
                    + Integer.toString(intMonth) + "-"
                    + Integer.toString(intDate);
        else
            strReturn = Integer.toString(intYear) + "-"
                    + Integer.toString(intMonth) + "-"
                    + Integer.toString(intDate);
        return strReturn;
    }
    
    public void setBLPrpJpayRefDetail(BLPrpJpayRefDetail blPrpJpayRefDetail){
    	this.blPrpJpayRefDetail = blPrpJpayRefDetail;
    }
    
    /**
     * @desc 续XXXXX新XX起XXXXX当日系统自动做到款确认 20081203 yanglei
     * @param iDate
     * @param iCenterCode
     * @param iOperatorCode
     * @return
     * @throws UserException
     * @throws Exception
     */
    public void autoCreateInvest(String iCenterCode, String iDate,
			String iOperatorCode) throws UserException, Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			this.autoCreateInvest(dbpool, iCenterCode, iDate, iOperatorCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    /**
     * @desc 续XXXXX新XX起XXXXX当日系统自动做到款确认 20080812 zhanglingjian
     * @param iDate
     * @param iCenterCode
     * @param iOperatorCode
     * @return
     * @throws UserException
     * @throws Exception
     */
    public void autoCreateInvest(DbPool dbpool,String iCenterCode, String iDate, String iOperatorCode)
    throws UserException,Exception{
    	BLPrpDcompany blPrpDcompany =new BLPrpDcompany();
    	if(iDate.equals("")){
    		DateTime dateTime = new DateTime(DateTime.current());
    		iDate = dateTime.addDay(-1).toString(DateTime.YEAR_TO_DAY);
    	}
        try {
            
            
            String strWherePart = " CenterFlag='1' AND ValidStatus='1'";
            strWherePart += Str.convertString("ComCode",iCenterCode,"=");
            strWherePart += " ORDER BY ComCode";
            blPrpDcompany.query(dbpool,strWherePart,0);
            for(int i=0; i<blPrpDcompany.getSize(); i++){
            	try
            	{
            		dbpool.beginTransaction();
            		this.autoCreateInvestVoucher(dbpool,blPrpDcompany.getArr(i).getComCode(),iDate,iOperatorCode);
                    dbpool.commitTransaction();
            	}catch(Exception e){
                    e.printStackTrace();
                    dbpool.rollbackTransaction();
                }
            }
        }
        catch(Exception e) {
        	e.printStackTrace();
            dbpool.rollbackTransaction();
            throw e;
        }finally {
          dbpool.close();
        }
    }
    
    /**
     * @desc 续XXXXX新XX起XXXXX当日系统自动做到款确认 20080812 zhanglingjian
     * @param dbpool
     * @param iCenterCode
     * @param iDate 到款日期
     * @param iOperatorCode 操作员代码
     * @return
     * @throws UserException
     * @throws Exception
     */
    public String[] autoCreateInvestVoucher(String iCenterCode,String iDate, String iOperatorCode)
    throws UserException,Exception{
    	String[] arrVouch = null;
    	DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            
            this.autoCreateInvestVoucher(dbpool,iCenterCode,iDate,iOperatorCode);
            dbpool.commitTransaction();
        }
        catch(Exception e) {
            e.printStackTrace();
            dbpool.rollbackTransaction();
            throw e;
        }finally {
          dbpool.close();
        }
        return arrVouch;

    }   
    
    /**
     * @desc 续XXXXX新XX起XXXXX当日系统自动做到款确认 20080812 zhanglingjian
     * @param dbpool
     * @param iCenterCode
     * @param iDate 到款日期
     * @param iOperatorCode 操作员代码
     * @return
     * @throws UserException
     * @throws Exception
     */
    public void autoCreateInvestVoucher(DbPool dbpool,String iCenterCode,String iDate, String iOperatorCode)
        throws UserException,Exception{
    	try
    	{
    		String strPackUnit = "00000000";
    		String strPayRefCode = "00000000";
    		
    		StringBuffer sqlBuffer = new StringBuffer();
    		String sql = "";
    		sqlBuffer.append(" centercode='" + iCenterCode + "' ");
    		sqlBuffer.append(" AND renewalflag=2 AND flag='E' AND realpayreffee=0 ");
    		sqlBuffer.append(" AND startdate<='" + iDate + "' ");
    		sql = sqlBuffer.toString();
            this.initArr();
            this.query(dbpool,sql,0);
            if(this.getSize() != 0){
            	
            	String sqlString = " update prpjinvest set payrefdate='" + iDate + "',currency2=currency1," +
            			"payreffee=planfee,realpayreffee=planfee where " + sql;
            	dbpool.executeUpdate(sqlString);
                
                /****************************由本金数据生成XX应收应付数据*******************************/
                
                BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
                BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
                BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
                BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
                
                this.transInvestToPayRef(dbpool,blPrpJplanFee,blPrpJpayRefRec,blPrpJpayRefMain,strPackUnit);
                
                if((blPrpJplanFee.getSize() != 0) && (blPrpJplanFee.getArr(0).getCertiType().equals("P")))
                {
                	blPrpJplanCommission.transInvestCommission(dbpool,blPrpJplanFee, blPrpJpayRefRec.getArr(0).getPayRefDate());
                }
                /****************************生成XX应收凭证******************************************/

                
                for(int i=0;i<blPrpJplanFee.getSize();i++){
                	blPrpJplanFee.getArr(i).setVoucherNo("0");
                	blPrpJplanFee.getArr(i).setPlanFeeCNY("0");
                	blPrpJplanFee.getArr(i).setPayRefFee(blPrpJplanFee.getArr(i).getPlanFee());
                	blPrpJplanFee.getArr(i).setRealPayRefFee(blPrpJplanFee.getArr(i).getPlanFee());
                	blPrpJplanFee.getArr(i).setPayRefReason("R55");
                }

                /****************************生成XX实收凭证******************************************/
                
                DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
                String strRealPayRefNo = dbPrpJpayRefVoucher.genRealPayRefNo();
                dbPrpJpayRefVoucher.setRealPayRefNo(strRealPayRefNo);
                dbPrpJpayRefVoucher.setPayRefNoType("2");
                dbPrpJpayRefVoucher.setCenterCode(iCenterCode);
                dbPrpJpayRefVoucher.setVoucherDate(iDate);
                dbPrpJpayRefVoucher.setComCode(iCenterCode);
                dbPrpJpayRefVoucher.setPayRefCode(strPayRefCode);
                dbPrpJpayRefVoucher.setToCenterCode("0");
                dbPrpJpayRefVoucher.setAttribute2("0");
                dbPrpJpayRefVoucher.insert(dbpool);
                for(int i=0;i<blPrpJpayRefRec.getSize();i++){
                	blPrpJpayRefRec.getArr(i).setOperatorCode(strPayRefCode);
                	blPrpJpayRefRec.getArr(i).setOperateUnit(strPackUnit);
                	blPrpJpayRefRec.getArr(i).setPayRefDate(iDate);
                	blPrpJpayRefRec.getArr(i).setIdentifyType("1");
                	blPrpJpayRefRec.getArr(i).setIdentifyNumber("1");
                	blPrpJpayRefRec.getArr(i).setRealPayRefNo(strRealPayRefNo);
                	blPrpJpayRefRec.getArr(i).setPayRefReason("R55");
                }
                
                blPrpJplanFee.save(dbpool);
                blPrpJpayRefRec.save(dbpool);
                
                for(int i=0;i<blPrpJplanFee.getSize();i++){
                	DBPrpCplan dbPrpCplan = new DBPrpCplan();
                	dbPrpCplan.getInfo(dbpool,blPrpJplanFee.getArr(i).getPolicyNo(),
                      blPrpJplanFee.getArr(i).getSerialNo());
                	dbPrpCplan.setDelinquentFee("0");
                	dbPrpCplan.update(dbpool);
                }
                
                BLPrpJpaymentVoucher blPrpJpaymentVoucher2 = new BLPrpJpaymentVoucher();
                blPrpJpaymentVoucher2.callProcedure(dbpool,strRealPayRefNo);
            }
         }
    	catch(Exception ex){
    		ex.printStackTrace();
    		throw ex;
    	}
    }
    
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        


    }
	/**
	 * @desc 日志输出
	 * @param iLog
	 * @throws Exception
	 */
	public void logPrintln(String iLog) throws Exception {
		ChgDate chgDate = new ChgDate();
		logger
				.info(chgDate.getCurrentTime("yyyy-MM-dd hh:mm:ss") + "><"
						+ iLog);
	}
}
