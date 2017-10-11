/******************************************************************************
* DESC       : 长期XXXXXXX结算类
* AUTHOR     ：LIJIBIN
* CREATEDATE : 2004-04-19
* MODIFYLIST ：Name       Date            Reason/Contents
******************************************************************************/
package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import java.text.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.*;
import com.sp.prpall.dbsvr.jf.DBPrpJpremBalance;
import com.sp.prpall.schema.PrpJpremBalanceSchema;
import com.sp.prpall.blsvr.cb.*;
import com.sp.prpall.blsvr.pg.*;
import com.sp.prpall.pubfun.*;
import com.sp.prpall.dbsvr.cb.*;
import com.sp.prpall.dbsvr.pg.*;
import com.sp.prpall.schema.*;
import com.sp.utiall.blsvr.*;
import com.sp.utiall.dbsvr.*;
import com.sp.account.blsvr.*;
import com.sp.account.schema.*;

/**
 * 定义PrpJpremBalance的BL类
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>@createdate 2004-04-19</p>
 * @Author     : X
 * @version 1.0
 */
public class BLPrpJpremBalance{
    private Vector schemas = new Vector();
    private String comCond = "";
    private String riskCond = "";
    private String currentDate = "";
    private int lastYear = 0;
    private String yearMonth = "";
    private String yearMonthFirstDay = "";
    private String yearMonthEndDay = "";
    /**
     * 构造函数
     */
    public BLPrpJpremBalance(){
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
     *增加一条PrpJpremBalanceSchema记录
     *@param iPrpJpremBalanceSchema PrpJpremBalanceSchema
     *@throws Exception
     */
    public void setArr(PrpJpremBalanceSchema iPrpJpremBalanceSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJpremBalanceSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
    }

    /**
     *替换一条PrpJpremBalanceSchema记录
     *@param iPrpJpremBalanceSchema PrpJpremBalanceSchema
     *@param index
     *@throws Exception
     */
    public void setArr(PrpJpremBalanceSchema iPrpJpremBalanceSchema,int index) throws Exception
    {
       try
       {
         schemas.setElementAt(iPrpJpremBalanceSchema,index);
       }
       catch(Exception e)
       {
         throw e;
       }
    }

    /**
     *得到一条PrpJpremBalanceSchema记录
     *@param index 下标
     *@return 一个PrpJpremBalanceSchema对象
     *@throws Exception
     */
    public PrpJpremBalanceSchema getArr(int index) throws Exception
    {
     PrpJpremBalanceSchema prpJpremBalanceSchema = null;
       try
       {
        prpJpremBalanceSchema = (PrpJpremBalanceSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJpremBalanceSchema;
    }

    /**
     *删除一条PrpJpremBalanceSchema记录
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
       this.query(iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBPrpJpremBalance dbPrpJpremBalance = new DBPrpJpremBalance();
      if (iLimitCount > 0 && dbPrpJpremBalance.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpremBalance.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpremBalance WHERE " + iWherePart;
        schemas = dbPrpJpremBalance.findByConditions(strSqlStatement);
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
       this.query(dbpool,iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBPrpJpremBalance dbPrpJpremBalance = new DBPrpJpremBalance();
      if (iLimitCount > 0 && dbPrpJpremBalance.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpremBalance.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpremBalance WHERE " + iWherePart;
        schemas = dbPrpJpremBalance.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJpremBalance dbPrpJpremBalance = new DBPrpJpremBalance();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJpremBalance.setSchema((PrpJpremBalanceSchema)schemas.get(i));
      dbPrpJpremBalance.insert(dbpool);
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
     *@Author     : X
     *@desc 带dbpool的saveOrUpdate方法
     *@param 无
     *@return 无
     */
    public void saveOrUpdate(DbPool dbpool) throws Exception
    {
      DBPrpJpremBalance dbPrpJpremBalance = new DBPrpJpremBalance();
      String strSQL = "";

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpJpremBalance.setSchema((PrpJpremBalanceSchema)schemas.get(i));
        int intReturn = dbPrpJpremBalance.getInfo(dbpool,dbPrpJpremBalance.getYearMonth(),dbPrpJpremBalance.getCertiNo());
        if(intReturn == 100)
          dbPrpJpremBalance.insert(dbpool);
        else
        {
          dbPrpJpremBalance.setSchema((PrpJpremBalanceSchema)schemas.get(i));
          strSQL = "UPDATE PrpJpremBalance SET SumPremium=SumPremium+"+dbPrpJpremBalance.getSumPremium()+","
              + "ThisPremium=ThisPremium+"+dbPrpJpremBalance.getThisPremium()+","
              + "ThisOthFee=ThisOthFee+"+dbPrpJpremBalance.getThisOthFee()+","
              + "LeftPremium=LeftPremium+"+dbPrpJpremBalance.getLeftPremium()
              + " WHERE YearMonth='"+dbPrpJpremBalance.getYearMonth()+"' AND CertiNo='"+dbPrpJpremBalance.getCertiNo()+"'";
          dbpool.update(strSQL);
        }
      }
    }


    /**
     * @desc  长期XXXXXXX结算，生成凭证
     * @param iCenterCode  核算单位
     * @param iBranchCode  基层单位
     * @param iUserCode    操作员
     * @param iFlag   生成凭证标志：0.不生成 1.生成
     * @return strVoucherNo 生成的凭证号
     * @throws Exception
     */
    public String balance(String iCenterCode,String iBranchCode,String iYearMonth,String iUserCode,String iFlag) throws Exception
    {
      String riskList = SysConfig.getProperty("SECULAR_RISK");
      
      

      BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
      
      String[] arrComCode = null;
      arrComCode = blPrpDcompany.getLowerComCode(iBranchCode);
      this.comCond = "'"+iBranchCode+"'";
      for (int j = 0; j < arrComCode.length; j++)
      {
        this.comCond += ",'" + arrComCode[j] + "'";
      }
      ChgDate chgDate = new ChgDate();
      currentDate = chgDate.getCurrentTime("yyyy-MM-dd");
      this.lastYear = Integer.parseInt(iYearMonth.substring(0,4))-1;

      this.yearMonth = iYearMonth;
      BLAccMonthTrace blAcMonthTrace = new BLAccMonthTrace();
      this.yearMonthFirstDay = iYearMonth.substring(0,4)+"-"+iYearMonth.substring(4,6)+"-01";
      this.yearMonthEndDay = blAcMonthTrace.getMonthEndDay(iYearMonth);

      String strVoucherNo = "";
      try
      {
        
        if(riskList.indexOf("JAK")>=0)
          this.riskCond = "'JAK'";
        else
          this.riskCond = "";
        this.prepareCB(iBranchCode,iUserCode);
        this.preparePGNoEnd(iBranchCode,iUserCode);
        this.preparePGIsEnd(iBranchCode,iUserCode);
        this.balanceLast(iUserCode);
        this.balance200407(iUserCode);  
        
        if(riskList.indexOf("ECU")>=0)
          this.riskCond = "'ECU'";
        else
          this.riskCond = "";
        this.prepareECUCB(iBranchCode,iUserCode);
        this.prepareECUPGNoEnd(iBranchCode,iUserCode);
        this.prepareECUPGIsEnd(iBranchCode,iUserCode);
        this.balanceECULast(iUserCode);
        if(iFlag.equals("1"))
          strVoucherNo = this.createVoucher(iCenterCode,iBranchCode,iYearMonth,iUserCode);
      }
      catch(Exception e)
      {
        throw e;
      }
      return strVoucherNo;
    }

    /**
     * @desc  从PrpCmainOrigin提取XX原始数据到长期XXXXXXX结算表中
     * @param iBranchCode
     * @param iUserCode
     * @throws Exception
     */
    public void prepareCB(String iBranchCode,String iUserCode) throws Exception,UserException
    {
      BLPrpCmain blPrpCmain = new BLPrpCmain();
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      BLAccRawVoucher blAccRawVoucher = new BLAccRawVoucher();
      DbPool dbpool = new DbPool();

      String strSQL = "SELECT RawVoucher.*,PrpCmain.ClassCode,PrpCmain.RiskCode,PrpCmain.StartDate,PrpCmain.EndDate,"
          + " PrpCmain.UnderWriteEndDate,PrpCmain.ComCode,PrpCmain.MakeCom,PrpCmain.Handler1Code"
          + " FROM PrpCmain,"
          + "(SELECT SerialNo,'CURR',PolicyNo1,Currency,SumMoney FROM AccRawVoucher"
          + "  WHERE F05 IN ("+riskCond+")"     
          + "  AND F26='"+iBranchCode+"'"       
          + "  AND ReceiptType='R50'"           
          + "  AND PolicyType1='P'"             
          + "  AND VoucherFlag='4'"             
          + "  AND (SUBSTR(Flag,3,1)!='1' OR SUBSTR(Flag,3,1) IS NULL)"       
          + " UNION "
          + "  SELECT SerialNo,'HIS',PolicyNo1,Currency,SumMoney FROM AccRawVoucherHis"
          + "  WHERE F05 IN ("+riskCond+")"	
          + "  AND ReceiptType='R50'" 	        
          + "  AND F26='"+iBranchCode+"'"       
          + "  AND PolicyType1='P'" 		
          + "  AND VoucherFlag='4'" 		
          + "  AND (SUBSTR(Flag,3,1)!='1' OR SUBSTR(Flag,3,1) IS NULL)"	
          + ") RawVoucher"
          + " WHERE PrpCmain.PolicyNo=RawVoucher.PolicyNo1"
          
          
          + " AND PrpCmain.StartDate<='"+this.yearMonthEndDay+"'"
          
          
          
          
          ;

        try {
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        Vector cmainSchemas = new Vector();
        PrpCmainSchema prpCmainSchema = null;
        AccRawVoucherSchema accRawVoucherSchema = null;
        ResultSet rs = dbpool.query(strSQL);
        while(rs.next())
        {
          prpCmainSchema = new PrpCmainSchema();
          accRawVoucherSchema = new AccRawVoucherSchema();
          prpCmainSchema.setPolicyNo(rs.getString("PolicyNo1"));
          prpCmainSchema.setCurrency(rs.getString("Currency"));
          prpCmainSchema.setClassCode(rs.getString("ClassCode"));
          prpCmainSchema.setRiskCode(rs.getString("RiskCode"));
          prpCmainSchema.setStartDate(""+rs.getDate("StartDate"));
          prpCmainSchema.setEndDate(""+rs.getDate("EndDate"));
          prpCmainSchema.setUnderWriteEndDate(""+rs.getDate("UnderWriteEndDate"));
          prpCmainSchema.setHandler1Code(rs.getString("Handler1Code"));
          prpCmainSchema.setComCode(rs.getString("ComCode"));
          prpCmainSchema.setMakeCom(rs.getString("MakeCom"));
          prpCmainSchema.setSumPremium(""+rs.getDouble("SumMoney"));
          cmainSchemas.add(prpCmainSchema);
          accRawVoucherSchema.setSerialNo(""+rs.getInt("SerialNo"));  
          accRawVoucherSchema.setFlag(rs.getString("CURR"));
          blAccRawVoucher.setArr(accRawVoucherSchema);
        }
        rs.close();

        this.initArr();
        PrpJpremBalanceSchema prpJpremBalanceSchema = null;
        for(int i=0; i<cmainSchemas.size(); i++)
        {
          prpCmainSchema = (PrpCmainSchema)cmainSchemas.get(i);
          prpJpremBalanceSchema = new PrpJpremBalanceSchema();

          prpJpremBalanceSchema.setYearMonth(this.yearMonth);
          prpJpremBalanceSchema.setClassCode(prpCmainSchema.getClassCode());
          prpJpremBalanceSchema.setRiskCode(prpCmainSchema.getRiskCode());
          prpJpremBalanceSchema.setPolicyNo(prpCmainSchema.getPolicyNo());
          prpJpremBalanceSchema.setCertiNo(prpCmainSchema.getPolicyNo());
          prpJpremBalanceSchema.setStartDate(prpCmainSchema.getStartDate());
          prpJpremBalanceSchema.setEndDate(prpCmainSchema.getEndDate());
          com.sp.utility.string.Date startDate = new com.sp.utility.string.Date(prpCmainSchema.getStartDate());
          com.sp.utility.string.Date endDate = new com.sp.utility.string.Date(prpCmainSchema.getEndDate());
          prpJpremBalanceSchema.setDays(""+PubTools.getDayMinus(startDate,0,endDate,24));
          prpJpremBalanceSchema.setYears(""+PubTools.getYearMinus(startDate,0,endDate,24));
          prpJpremBalanceSchema.setUnderWriteEndDate(prpCmainSchema.getUnderWriteEndDate());
          prpJpremBalanceSchema.setBalanceStartDate(prpCmainSchema.getStartDate());
          
          com.sp.utility.string.Date balanceEndDate = new com.sp.utility.string.Date(prpCmainSchema.getStartDate());
          balanceEndDate.set(balanceEndDate.YEAR,balanceEndDate.get(balanceEndDate.YEAR)+1);
          balanceEndDate.set(balanceEndDate.DATE,balanceEndDate.get(balanceEndDate.DATE)-1);
          prpJpremBalanceSchema.setBalanceEndDate(balanceEndDate.getString(balanceEndDate.YEAR+balanceEndDate.MONTH+balanceEndDate.DATE));
          prpJpremBalanceSchema.setCurrency(prpCmainSchema.getCurrency());
          prpJpremBalanceSchema.setSumPremium(prpCmainSchema.getSumPremium());
          
          double dblThisPremium = 0;
          if (PubTools.compareDate(prpJpremBalanceSchema.getBalanceEndDate(),prpJpremBalanceSchema.getEndDate())>=0)
          {
            prpJpremBalanceSchema.setBalanceEndDate(prpJpremBalanceSchema.getEndDate());
            dblThisPremium = Double.parseDouble(prpJpremBalanceSchema.getSumPremium());
          }
          else
          {
            dblThisPremium = Str.round( Double.parseDouble(prpJpremBalanceSchema.getSumPremium())
                                      * PubTools.getDayMinus(startDate,0,balanceEndDate,24)
                                      / PubTools.getDayMinus(startDate,0,endDate,24),2);
          }
          prpJpremBalanceSchema.setThisPremium(""+dblThisPremium);
          prpJpremBalanceSchema.setThisOthFee("0");
          prpJpremBalanceSchema.setLeftPremium(""+(Double.parseDouble(prpJpremBalanceSchema.getSumPremium())-dblThisPremium));
          prpJpremBalanceSchema.setHandler1Code(prpCmainSchema.getHandler1Code());
          prpJpremBalanceSchema.setComCode(prpCmainSchema.getComCode());
          prpJpremBalanceSchema.setMakeCom(prpCmainSchema.getMakeCom());
          prpJpremBalanceSchema.setOperatorCode(iUserCode);
          prpJpremBalanceSchema.setTransFlag("0");
          prpJpremBalanceSchema.setInputDate(currentDate);
          prpJpremBalanceSchema.setEndBalanceFlag("0");
          this.setArr(prpJpremBalanceSchema);
        }
        dbpool.beginTransaction();
        this.saveOrUpdate(dbpool);
        
        this.updateRawVoucher(dbpool,blAccRawVoucher);
        dbpool.commitTransaction();
      }
      catch(SQLException se)
      {
        dbpool.rollbackTransaction();
        throw se;
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
     * @desc  提取普通批改（除退XXXXX）数据
     * @param iBranchCode
     * @param iUserCode
     * @throws Exception
     */
    public void preparePGNoEnd(String iBranchCode,String iUserCode) throws Exception
    {
      BLPrpPmain blPrpPmain = new BLPrpPmain();
      DBPrpPmain dbPrpPmain = new DBPrpPmain();
      BLAccRawVoucher blAccRawVoucher = new BLAccRawVoucher();
      DbPool dbpool = new DbPool();

      String strSQL = "SELECT RawVoucher.*,PrpPhead.PolicyNo,PrpPmain.ClassCode,PrpPmain.RiskCode,PrpPhead.ValidDate,"
          + " PrpPmain.EndDate,PrpPHead.UnderWriteEndDate,PrpPmain.ComCode,PrpPmain.MakeCom,PrpPmain.Handler1Code"
          + " FROM PrpPmain,PrpPhead,"
          + "(SELECT SerialNo,'CURR',PolicyNo1,Currency,SumMoney FROM AccRawVoucher"
          + "  WHERE F05 IN ("+riskCond+")"     
          + "  AND F26='"+iBranchCode+"'"       
          + "  AND ReceiptType IN ('R50','P20')"
          + "  AND PolicyType1='E'"             
          + "  AND VoucherFlag='4'"             
          + "  AND (SUBSTR(Flag,3,1)!='1' OR SUBSTR(Flag,3,1) IS NULL)"       
          + " UNION "
          + "  SELECT SerialNo,'HIS',PolicyNo1,Currency,SumMoney FROM AccRawVoucherHis"
          + "  WHERE F05 IN ("+riskCond+")"	
          + "  AND ReceiptType IN ('R50','P20')"
          + "  AND F26='"+iBranchCode+"'"       
          + "  AND PolicyType1='E'" 		
          + "  AND VoucherFlag='4'" 		
          + "  AND (SUBSTR(Flag,3,1)!='1' OR SUBSTR(Flag,3,1) IS NULL)"	
          + ") RawVoucher"
          + " WHERE PrpPmain.EndorseNo=RawVoucher.PolicyNo1"
          + " AND PrpPhead.EndorseNo=PrpPmain.EndorseNo"
          + " AND PrpPhead.ValidDate<='"+this.yearMonthEndDay+"'"
          + " AND PrpPhead.EndorType!='21'"             
          ;
      /*
      String strSQL = "SELECT PrpPmain.* FROM PrpPmain,PrpPhead,PrpCmain"
          + " WHERE PrpPmain.EndorseNo=PrpPhead.EndorseNo"
          + " AND PrpPmain.PolicyNo=PrpCmain.PolicyNo"
          + " AND PrpPhead.UnderWriteFlag IN ('1','3')"
          + " AND (PrpPhead.ValidDate >=PrpPhead.UnderWriteEndDate AND PrpPhead.ValidDate<='"+lastYear+"-12-31'"
          + " OR PrpPhead.ValidDate<PrpPhead.UnderWriteEndDate AND PrpPhead.UnderWriteEndDate<='"+lastYear+"-12-31')"
          + " AND PrpPhead.EndorseNo NOT IN (SELECT DISTINCT CertiNo FROM PrpJpremBalance)"
          + " AND PrpPmain.ChgPremium!=0 "
          + " AND PrpPhead.EndorType!='21'"             
          + " AND SUBSTR(PrpCmain.OthFlag,4,1)!='1'"    
          + " AND PrpPmain.ComCode IN ("+comCond+")"
          + " AND PrpPmain.RiskCode IN ("+riskCond+")";
       */

        try {
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        Vector pmainSchemas = new Vector();
        PrpPmainSchema prpPmainSchema = null;
        AccRawVoucherSchema accRawVoucherSchema = new AccRawVoucherSchema();

        ResultSet rs = dbpool.query(strSQL);
        while(rs.next())
        {
          prpPmainSchema = new PrpPmainSchema();
          accRawVoucherSchema = new AccRawVoucherSchema();
          prpPmainSchema.setPolicyNo(rs.getString("PolicyNo"));
          prpPmainSchema.setEndorseNo(rs.getString("PolicyNo1"));
          prpPmainSchema.setCurrency(rs.getString("Currency"));
          prpPmainSchema.setClassCode(rs.getString("ClassCode"));
          prpPmainSchema.setRiskCode(rs.getString("RiskCode"));
          prpPmainSchema.setStartDate(""+rs.getDate("ValidDate"));   
          prpPmainSchema.setEndDate(""+rs.getDate("EndDate"));
          prpPmainSchema.setUnderWriteEndDate(""+rs.getDate("UnderWriteEndDate"));
          prpPmainSchema.setHandler1Code(rs.getString("Handler1Code"));
          prpPmainSchema.setComCode(rs.getString("ComCode"));
          prpPmainSchema.setMakeCom(rs.getString("MakeCom"));
          prpPmainSchema.setChgPremium(""+rs.getDouble("SumMoney"));
          pmainSchemas.add(prpPmainSchema);

          accRawVoucherSchema.setSerialNo(""+rs.getInt("SerialNo"));  
          accRawVoucherSchema.setFlag(rs.getString("CURR"));
          blAccRawVoucher.setArr(accRawVoucherSchema);
        }
        rs.close();

        this.initArr();
        PrpJpremBalanceSchema prpJpremBalanceSchema = null;
        for(int i=0; i<pmainSchemas.size(); i++)
        {
          prpPmainSchema = (PrpPmainSchema)pmainSchemas.get(i);
          prpJpremBalanceSchema = new PrpJpremBalanceSchema();

          prpJpremBalanceSchema.setYearMonth(this.yearMonth);
          prpJpremBalanceSchema.setClassCode(prpPmainSchema.getClassCode());
          prpJpremBalanceSchema.setRiskCode(prpPmainSchema.getRiskCode());
          prpJpremBalanceSchema.setPolicyNo(prpPmainSchema.getPolicyNo());
          prpJpremBalanceSchema.setCertiNo(prpPmainSchema.getEndorseNo());
          prpJpremBalanceSchema.setStartDate(prpPmainSchema.getStartDate());
          prpJpremBalanceSchema.setEndDate(prpPmainSchema.getEndDate());
          com.sp.utility.string.Date startDate = new com.sp.utility.string.Date(prpPmainSchema.getStartDate());
          com.sp.utility.string.Date endDate = new com.sp.utility.string.Date(prpPmainSchema.getEndDate());
          prpJpremBalanceSchema.setDays(""+PubTools.getDayMinus(startDate,0,endDate,24));
          prpJpremBalanceSchema.setYears(""+PubTools.getYearMinus(startDate,0,endDate,0));
          prpJpremBalanceSchema.setUnderWriteEndDate(prpPmainSchema.getUnderWriteEndDate());
          prpJpremBalanceSchema.setBalanceStartDate(prpPmainSchema.getStartDate());
          
          
          com.sp.utility.string.Date balanceEndDate = new com.sp.utility.string.Date(prpPmainSchema.getStartDate());
          balanceEndDate.set(balanceEndDate.YEAR,balanceEndDate.get(balanceEndDate.YEAR)+1);
          balanceEndDate.set(balanceEndDate.DATE,balanceEndDate.get(balanceEndDate.DATE)-1);
          prpJpremBalanceSchema.setBalanceEndDate(balanceEndDate.getString(balanceEndDate.YEAR+balanceEndDate.MONTH+balanceEndDate.DATE));
          
          prpJpremBalanceSchema.setCurrency(prpPmainSchema.getCurrency());
          prpJpremBalanceSchema.setSumPremium(prpPmainSchema.getChgPremium());
          
          double dblThisPremium = 0;
          if (PubTools.compareDate(prpJpremBalanceSchema.getBalanceEndDate(),prpJpremBalanceSchema.getEndDate())>=0)
          {
            prpJpremBalanceSchema.setBalanceEndDate(prpJpremBalanceSchema.getEndDate());
            dblThisPremium = Double.parseDouble(prpJpremBalanceSchema.getSumPremium());
          }
          else
          {
            dblThisPremium = Str.round( Double.parseDouble(prpJpremBalanceSchema.getSumPremium())
                                      * PubTools.getDayMinus(startDate,0,balanceEndDate,24)
                                      / PubTools.getDayMinus(startDate,0,endDate,24),2);
          }
          prpJpremBalanceSchema.setThisPremium(""+dblThisPremium);
          prpJpremBalanceSchema.setThisOthFee("0");
          prpJpremBalanceSchema.setLeftPremium(""+(Double.parseDouble(prpJpremBalanceSchema.getSumPremium())-dblThisPremium));
          prpJpremBalanceSchema.setHandler1Code(prpPmainSchema.getHandler1Code());
          prpJpremBalanceSchema.setComCode(prpPmainSchema.getComCode());
          prpJpremBalanceSchema.setMakeCom(prpPmainSchema.getMakeCom());
          prpJpremBalanceSchema.setOperatorCode(iUserCode);
          prpJpremBalanceSchema.setTransFlag("0");
          prpJpremBalanceSchema.setInputDate(currentDate);
          prpJpremBalanceSchema.setEndBalanceFlag("0");
          this.setArr(prpJpremBalanceSchema);
        }
        dbpool.beginTransaction();
        this.saveOrUpdate(dbpool);
        
        this.updateRawVoucher(dbpool,blAccRawVoucher);
        dbpool.commitTransaction();
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
     * @desc  提取全单退XXXXX数据
     * @param iBranchCode
     * @param iUserCode
     * @throws Exception
     */
    public void preparePGIsEnd(String iBranchCode,String iUserCode) throws UserException,Exception
    {
      BLPrpPmain blPrpPmain = new BLPrpPmain();
      DBPrpPmain dbPrpPmain = new DBPrpPmain();
      BLAccRawVoucher blAccRawVoucher = new BLAccRawVoucher();

      double dblCommRate = 0.25;  

      /*String strSQL = "SELECT PrpPmain.*"
          + " FROM PrpPmain,PrpPhead,PrpCmain"
          + " WHERE PrpPmain.EndorseNo=PrpPhead.EndorseNo"
          + " AND PrpPmain.PolicyNo=PrpCmain.PolicyNo"
          + " AND PrpPhead.UnderWriteFlag IN ('1','3')"
          + " AND (PrpPhead.ValidDate >=PrpPhead.UnderWriteEndDate AND PrpPhead.ValidDate<='"+lastYear+"-12-31'"
          + " OR PrpPhead.ValidDate<PrpPhead.UnderWriteEndDate AND PrpPhead.UnderWriteEndDate<='"+lastYear+"-12-31')"
          + " AND PrpPhead.EndorseNo NOT IN (SELECT DISTINCT CertiNo FROM PrpJpremBalance)"
          + " AND PrpPhead.EndorType='21'"             
          + " AND SUBSTR(PrpCmain.OthFlag,4,1)!='1'"    
          + " AND PrpPmain.ComCode IN ("+comCond+")"
          + " AND PrpPmain.RiskCode IN ("+riskCond+")";
      */

      String strSQL = "SELECT RawVoucher.*,PrpPhead.PolicyNo,PrpPmain.ClassCode,PrpPmain.RiskCode,"
          + " PrpPhead.ValidDate,PrpPmain.StartDate,PrpPmain.EndDate,PrpPHead.UnderWriteEndDate,"
          + " PrpPmain.ComCode,PrpPmain.MakeCom,PrpPmain.Handler1Code"
          + " FROM PrpPmain,PrpPhead,"
          + "(SELECT SerialNo,'CURR',PolicyNo1,Currency,SumMoney FROM AccRawVoucher"
          + "  WHERE F05 IN ("+riskCond+")"     
          + "  AND F26='"+iBranchCode+"'"       
          + "  AND ReceiptType IN ('R50','P20')"
          + "  AND PolicyType1='E'"             
          + "  AND VoucherFlag='4'"             
          + "  AND (SUBSTR(Flag,3,1)!='1' OR SUBSTR(Flag,3,1) IS NULL)"       
          + " UNION "
          + "  SELECT SerialNo,'HIS',PolicyNo1,Currency,SumMoney FROM AccRawVoucherHis"
          + "  WHERE F05 IN ("+riskCond+")"	
          + "  AND ReceiptType IN ('R50','P20')"
          + "  AND F26='"+iBranchCode+"'"       
          + "  AND PolicyType1='E'" 		
          + "  AND VoucherFlag='4'" 		
          + "  AND (SUBSTR(Flag,3,1)!='1' OR SUBSTR(Flag,3,1) IS NULL)"	
          + ") RawVoucher"
          + " WHERE PrpPmain.EndorseNo=RawVoucher.PolicyNo1"
          + " AND PrpPhead.EndorseNo=PrpPmain.EndorseNo"
          + " AND PrpPhead.ValidDate<='"+this.yearMonthEndDay+"'"
          + " AND PrpPhead.EndorType='21'"             
          ;

      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        Vector pmainSchemas = new Vector();
        PrpPmainSchema prpPmainSchema = null;
        AccRawVoucherSchema accRawVoucherSchema = null;
        ResultSet rs = dbpool.query(strSQL);
        while(rs.next())
        {
          prpPmainSchema = new PrpPmainSchema();
          accRawVoucherSchema = new AccRawVoucherSchema();
          prpPmainSchema.setPolicyNo(rs.getString("PolicyNo"));
          prpPmainSchema.setEndorseNo(rs.getString("PolicyNo1"));
          prpPmainSchema.setCurrency(rs.getString("Currency"));
          prpPmainSchema.setClassCode(rs.getString("ClassCode"));
          prpPmainSchema.setRiskCode(rs.getString("RiskCode"));
          prpPmainSchema.setStartDate(""+rs.getDate("StartDate"));
          prpPmainSchema.setEndDate(""+rs.getDate("EndDate"));
          prpPmainSchema.setInputDate(""+rs.getDate("ValidDate"));   
          prpPmainSchema.setUnderWriteEndDate(""+rs.getDate("UnderWriteEndDate"));  
          prpPmainSchema.setHandler1Code(rs.getString("Handler1Code"));
          prpPmainSchema.setComCode(rs.getString("ComCode"));
          prpPmainSchema.setMakeCom(rs.getString("MakeCom"));
          prpPmainSchema.setChgPremium(""+rs.getDouble("SumMoney"));
          pmainSchemas.add(prpPmainSchema);

          accRawVoucherSchema.setSerialNo(""+rs.getInt("SerialNo"));  
          accRawVoucherSchema.setFlag(rs.getString("CURR"));
          blAccRawVoucher.setArr(accRawVoucherSchema);
        }
        rs.close();

        this.initArr();
        dbpool.beginTransaction();
        PrpJpremBalanceSchema prpJpremBalanceSchema = null;
        for(int i=0; i<pmainSchemas.size(); i++)
        {
          prpPmainSchema = (PrpPmainSchema)pmainSchemas.get(i);
          prpJpremBalanceSchema = new PrpJpremBalanceSchema();

          prpJpremBalanceSchema.setYearMonth(this.yearMonth);
          prpJpremBalanceSchema.setClassCode(prpPmainSchema.getClassCode());
          prpJpremBalanceSchema.setRiskCode(prpPmainSchema.getRiskCode());
          prpJpremBalanceSchema.setPolicyNo(prpPmainSchema.getPolicyNo());
          prpJpremBalanceSchema.setCertiNo(prpPmainSchema.getEndorseNo());
          prpJpremBalanceSchema.setStartDate(prpPmainSchema.getStartDate());
          prpJpremBalanceSchema.setEndDate(prpPmainSchema.getInputDate());
          com.sp.utility.string.Date startDate = new com.sp.utility.string.Date(prpPmainSchema.getStartDate());
          com.sp.utility.string.Date validDate = new com.sp.utility.string.Date(prpPmainSchema.getInputDate());
          prpJpremBalanceSchema.setDays(""+PubTools.getDayMinus(startDate,0,validDate,0));
          prpJpremBalanceSchema.setYears(""+PubTools.getYearMinus(startDate,0,validDate,0));
          prpJpremBalanceSchema.setUnderWriteEndDate(prpPmainSchema.getUnderWriteEndDate());
          prpJpremBalanceSchema.setBalanceStartDate(prpPmainSchema.getStartDate());
          prpJpremBalanceSchema.setBalanceEndDate(prpJpremBalanceSchema.getEndDate());
          prpJpremBalanceSchema.setCurrency(prpPmainSchema.getCurrency());
          prpJpremBalanceSchema.setSumPremium(prpPmainSchema.getChgPremium());
          
          strSQL = "SELECT SUM(SumPremium) FROM PrpJpremBalance"
             + " WHERE PolicyNo=CertiNo AND PolicyNo='"+prpPmainSchema.getPolicyNo()+"'"
             
             + " AND EndBalanceFlag='0'";  
          double dblSumPremiumP = this.getSumValue(dbpool,strSQL);
          
          strSQL = "SELECT SUM(SumPremium) FROM PrpJpremBalance"
             + " WHERE PolicyNo!=CertiNo AND PolicyNo='"+prpPmainSchema.getPolicyNo()+"'"
             
             + " AND EndBalanceFlag='0'";  
          double dblSumPremiumE = this.getSumValue(dbpool,strSQL);
          
          com.sp.utility.string.Date endDate = new com.sp.utility.string.Date(prpPmainSchema.getEndDate());
          double dblNoDutyRate = (double)PubTools.getDayMinus(validDate,0,endDate,24)
                                 /PubTools.getDayMinus(startDate,0,endDate,24);
          
          double dblThisOthFee = Str.round((dblSumPremiumP*dblCommRate+dblSumPremiumE)*dblNoDutyRate,2);
          
          strSQL = "SELECT SUM(LeftPremium) FROM PrpJpremBalance"
             + " WHERE PolicyNo='"+prpPmainSchema.getPolicyNo()+"'"
             
             + " AND EndBalanceFlag='0'";  
          double dblLeftPremium = this.getSumValue(dbpool,strSQL);
          
          double dblThisPremium = dblLeftPremium + Double.parseDouble(prpPmainSchema.getChgPremium()) - dblThisOthFee;
          prpJpremBalanceSchema.setThisPremium(""+dblThisPremium);
          prpJpremBalanceSchema.setThisOthFee(""+dblThisOthFee);
          prpJpremBalanceSchema.setLeftPremium("0");
          prpJpremBalanceSchema.setHandler1Code(prpPmainSchema.getHandler1Code());
          prpJpremBalanceSchema.setComCode(prpPmainSchema.getComCode());
          prpJpremBalanceSchema.setMakeCom(prpPmainSchema.getMakeCom());
          prpJpremBalanceSchema.setOperatorCode(iUserCode);
          prpJpremBalanceSchema.setTransFlag("0");
          prpJpremBalanceSchema.setInputDate(currentDate);
          prpJpremBalanceSchema.setEndBalanceFlag("0");
          this.setArr(prpJpremBalanceSchema);
          
          strSQL = "UPDATE PrpJpremBalance SET EndBalanceFlag='1' "
             + " WHERE PolicyNo='"+prpPmainSchema.getPolicyNo()+"'"
             
             + " AND EndBalanceFlag='0'";  
          dbpool.update(strSQL);
        }
        this.saveOrUpdate(dbpool);
        
        this.updateRawVoucher(dbpool,blAccRawVoucher);
        dbpool.commitTransaction();
      }
      catch(SQLException se)
      {
        dbpool.rollbackTransaction();
        throw se;
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
     * @Author     : X
     * @param dbpool
     * @param iBLAccRawVoucher
     * @throws Exception
     */
    private void updateRawVoucher(DbPool dbpool,BLAccRawVoucher iBLAccRawVoucher) throws Exception
    {
      AccRawVoucherSchema accRawVoucherSchema = new AccRawVoucherSchema();
      String strSQL = "";
      String strSerialNo = "";
      String strSerialNoHis = "";
      for(int i=0; i<iBLAccRawVoucher.getSize(); i++)
      {
        accRawVoucherSchema = iBLAccRawVoucher.getArr(i);
        if(accRawVoucherSchema.getFlag().equals("CURR"))
          strSerialNo += accRawVoucherSchema.getSerialNo()+",";
        if(accRawVoucherSchema.getFlag().equals("HIS"))
          strSerialNoHis += accRawVoucherSchema.getSerialNo()+",";
      }

      
      if(strSerialNo.length()>0)
      {
        strSerialNo = strSerialNo.substring(0, strSerialNo.length() - 1);
        strSQL = "UPDATE AccRawVoucher SET Flag=SUBSTR(Flag,1,1)||'11' WHERE SerialNo IN ("+strSerialNo+")";
        dbpool.update(strSQL);
      }
      if(strSerialNoHis.length()>0)
      {
        strSerialNoHis = strSerialNoHis.substring(0, strSerialNoHis.length() - 1);
        strSQL = "UPDATE AccRawVoucherHis SET Flag=SUBSTR(Flag,1,1)||'11' WHERE SerialNo IN ("+strSerialNoHis+")";
        dbpool.update(strSQL);
      }
    }

    /**
     * @desc  查询一个数值
     * @param dbpool
     * @param iSQL
     * @return dblSumValue 查询出来的一个double值
     * @throws SQLException
     * @throws Exception
     */
    private double getSumValue(DbPool dbpool,String iSQL) throws SQLException,Exception
    {
      double dblSumValue = 0;
      ResultSet rs = dbpool.query(iSQL);
      if(rs.next())
      {
        dblSumValue = rs.getDouble(1);
        rs.close();
      }
      return dblSumValue;
    }

    /**
     * @desc  长期XXXXXXX结算表中前年度数据结转到上年度，同时该前年度数据结束结转
     * @param iUserCode
     * @throws Exception
     */
    public void balanceLast(String iUserCode) throws Exception
    {
      String strWherePart = "";
      PrpJpremBalanceSchema prpJpremBalanceSchema = null;
      this.initArr();
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        
        
        strWherePart = " BalanceEndDate BETWEEN '"+this.yearMonthFirstDay+"' AND '"+this.yearMonthEndDay+"'"
                     + " AND EndBalanceFlag='0' AND ComCode IN ("+this.comCond+")"
                     + " AND YearMonth<'"+this.yearMonth+"' AND RiskCode IN ("+this.riskCond+")";

        this.query(dbpool,strWherePart,0);
        for(int i=0; i<this.getSize(); i++)
        {
          prpJpremBalanceSchema = this.getArr(i);
          prpJpremBalanceSchema.setYearMonth(this.yearMonth);
          
          com.sp.utility.string.Date balanceStartDate = new com.sp.utility.string.Date(prpJpremBalanceSchema.getBalanceEndDate());
          balanceStartDate.set(balanceStartDate.DATE,balanceStartDate.get(balanceStartDate.DATE)+1);
          prpJpremBalanceSchema.setBalanceStartDate(balanceStartDate.getString(balanceStartDate.YEAR+balanceStartDate.MONTH+balanceStartDate.DATE));
          
          
          com.sp.utility.string.Date balanceEndDate = new com.sp.utility.string.Date(prpJpremBalanceSchema.getBalanceEndDate());
          balanceEndDate.set(balanceEndDate.YEAR,balanceEndDate.get(balanceEndDate.YEAR)+1);
          
          prpJpremBalanceSchema.setBalanceEndDate(balanceEndDate.getString(balanceEndDate.YEAR+balanceEndDate.MONTH+balanceEndDate.DATE));
          
          double dblThisPremium = 0;
          if (PubTools.compareDate(prpJpremBalanceSchema.getBalanceEndDate(),prpJpremBalanceSchema.getEndDate())>=0)
          {
            prpJpremBalanceSchema.setBalanceEndDate(prpJpremBalanceSchema.getEndDate());
            dblThisPremium = Double.parseDouble(prpJpremBalanceSchema.getLeftPremium());
          }
          else
          {
            dblThisPremium = Str.round( Double.parseDouble(prpJpremBalanceSchema.getSumPremium())
                                      * PubTools.getDayMinus(balanceStartDate,0,balanceEndDate,24)
                                      / Double.parseDouble(prpJpremBalanceSchema.getDays()),2);
          }
          prpJpremBalanceSchema.setThisPremium(""+dblThisPremium);
          prpJpremBalanceSchema.setThisOthFee("0");
          prpJpremBalanceSchema.setLeftPremium(""+(Double.parseDouble(prpJpremBalanceSchema.getLeftPremium())-dblThisPremium));
          prpJpremBalanceSchema.setTransFlag("0");
          prpJpremBalanceSchema.setOperatorCode(iUserCode);
          prpJpremBalanceSchema.setInputDate(this.currentDate);
          this.setArr(prpJpremBalanceSchema,i);
        }
        dbpool.beginTransaction();
        this.saveOrUpdate(dbpool);
        
        dbpool.update("UPDATE PrpJpremBalance SET EndBalanceFlag='1' WHERE "+strWherePart);
        dbpool.commitTransaction();
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
     * @desc  长期XXXXXXX结算表中前年度数据结转到上年度，同时该前年度数据结束结转
     * @param iUserCode
     * @throws Exception
     */
    public void balance200407(String iUserCode) throws Exception
    {
      String strWherePart = "";
      PrpJpremBalanceSchema prpJpremBalanceSchema = null;
      this.initArr();
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        
        strWherePart = " YearMonth='200407'"
                     + " AND EndBalanceFlag='0' AND ComCode IN ("+this.comCond+")";
        this.query(dbpool,strWherePart,0);
        for(int i=0; i<this.getSize(); i++)
        {
          prpJpremBalanceSchema = this.getArr(i);
          prpJpremBalanceSchema.setYearMonth(this.yearMonth);
          
          com.sp.utility.string.Date balanceStartDate = new com.sp.utility.string.Date(prpJpremBalanceSchema.getStartDate());
          balanceStartDate.set(balanceStartDate.DATE,balanceStartDate.get(balanceStartDate.DATE)+1);
          prpJpremBalanceSchema.setBalanceStartDate(prpJpremBalanceSchema.getStartDate());
          
          
          com.sp.utility.string.Date balanceEndDate = new com.sp.utility.string.Date(prpJpremBalanceSchema.getStartDate());
          balanceEndDate.set(balanceEndDate.YEAR,balanceEndDate.get(balanceEndDate.YEAR)+1);
          balanceEndDate.set(balanceEndDate.DATE,balanceEndDate.get(balanceEndDate.DATE)-1);
          prpJpremBalanceSchema.setBalanceEndDate(balanceEndDate.getString(balanceEndDate.YEAR+balanceEndDate.MONTH+balanceEndDate.DATE));
          
          double dblThisPremium = 0;
          if (PubTools.compareDate(prpJpremBalanceSchema.getBalanceEndDate(),prpJpremBalanceSchema.getEndDate())>=0)
          {
            prpJpremBalanceSchema.setBalanceEndDate(prpJpremBalanceSchema.getEndDate());
            dblThisPremium = Double.parseDouble(prpJpremBalanceSchema.getLeftPremium());
          }
          else
          {
            dblThisPremium = Str.round( Double.parseDouble(prpJpremBalanceSchema.getSumPremium())
                                      * PubTools.getDayMinus(balanceStartDate,0,balanceEndDate,24)
                                      / Double.parseDouble(prpJpremBalanceSchema.getDays()),2)
                           - Double.parseDouble(prpJpremBalanceSchema.getThisPremium());
          }
          prpJpremBalanceSchema.setThisPremium(""+dblThisPremium);
          prpJpremBalanceSchema.setThisOthFee("0");
          prpJpremBalanceSchema.setLeftPremium(""+(Double.parseDouble(prpJpremBalanceSchema.getLeftPremium())-dblThisPremium));
          prpJpremBalanceSchema.setTransFlag("0");
          prpJpremBalanceSchema.setOperatorCode(iUserCode);
          prpJpremBalanceSchema.setInputDate(this.currentDate);
          this.setArr(prpJpremBalanceSchema,i);
        }
        dbpool.beginTransaction();
        this.save(dbpool);
        
        dbpool.update("UPDATE PrpJpremBalance SET EndBalanceFlag='1' WHERE "+strWherePart);
        dbpool.commitTransaction();
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
     * @desc  从PrpCmainOrigin提取XX原始数据到长期XXXXXXX结算表中
     * @param iBranchCode
     * @param iUserCode
     * @throws Exception
     */
    public void prepareECUCB(String iBranchCode,String iUserCode) throws Exception,UserException
    {
      BLPrpCmain blPrpCmain = new BLPrpCmain();
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      BLAccRawVoucher blAccRawVoucher = new BLAccRawVoucher();
      DbPool dbpool = new DbPool();

      String strSQL = "SELECT RawVoucher.*,PrpCmain.ClassCode,PrpCmain.RiskCode,PrpCmain.StartDate,PrpCmainLoan.LoanYear,"
          + " PrpCmain.UnderWriteEndDate,PrpCmain.ComCode,PrpCmain.MakeCom,PrpCmain.Handler1Code"
          + " FROM PrpCmain,PrpCmainLoan,"
          + "(SELECT SerialNo,'CURR',PolicyNo1,Currency,SumMoney FROM AccRawVoucher"
          + "  WHERE F05 IN ("+riskCond+")"     
          + "  AND F26='"+iBranchCode+"'"       
          + "  AND ReceiptType='R50'"           
          + "  AND PolicyType1='P'"             
          + "  AND VoucherFlag='4'"             
          + "  AND (SUBSTR(Flag,3,1)!='1' OR SUBSTR(Flag,3,1) IS NULL)"       
          + " UNION "
          + "  SELECT SerialNo,'HIS',PolicyNo1,Currency,SumMoney FROM AccRawVoucherHis"
          + "  WHERE F05 IN ("+riskCond+")"	
          + "  AND ReceiptType='R50'" 	        
          + "  AND F26='"+iBranchCode+"'"       
          + "  AND PolicyType1='P'" 		
          + "  AND VoucherFlag='4'" 		
          + "  AND (SUBSTR(Flag,3,1)!='1' OR SUBSTR(Flag,3,1) IS NULL)"	
          + ") RawVoucher"
          + " WHERE PrpCmain.PolicyNo=RawVoucher.PolicyNo1"
          + " AND PrpCmain.PolicyNo=PrpCmainLoan.PolicyNo"
          
          
          + " AND PrpCmain.StartDate<='"+this.yearMonthEndDay+"'"
          
          
          
          
          ;

        try {
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        Vector cmainSchemas = new Vector();
        PrpCmainSchema prpCmainSchema = null;
        AccRawVoucherSchema accRawVoucherSchema = null;
        ResultSet rs = dbpool.query(strSQL);
        while(rs.next())
        {
          prpCmainSchema = new PrpCmainSchema();
          accRawVoucherSchema = new AccRawVoucherSchema();
          prpCmainSchema.setPolicyNo(rs.getString("PolicyNo1"));
          prpCmainSchema.setCurrency(rs.getString("Currency"));
          prpCmainSchema.setClassCode(rs.getString("ClassCode"));
          prpCmainSchema.setRiskCode(rs.getString("RiskCode"));
          prpCmainSchema.setStartDate(""+rs.getDate("StartDate"));
          prpCmainSchema.setEndDate(""+rs.getDate("StartDate"));
          prpCmainSchema.setEndHour(""+rs.getInt("LoanYear"));
          prpCmainSchema.setUnderWriteEndDate(""+rs.getDate("UnderWriteEndDate"));
          prpCmainSchema.setHandler1Code(rs.getString("Handler1Code"));
          prpCmainSchema.setComCode(rs.getString("ComCode"));
          prpCmainSchema.setMakeCom(rs.getString("MakeCom"));
          prpCmainSchema.setSumPremium(""+rs.getDouble("SumMoney"));
          cmainSchemas.add(prpCmainSchema);
          accRawVoucherSchema.setSerialNo(""+rs.getInt("SerialNo"));  
          accRawVoucherSchema.setFlag(rs.getString("CURR"));
          blAccRawVoucher.setArr(accRawVoucherSchema);
        }
        rs.close();

        this.initArr();
        PrpJpremBalanceSchema prpJpremBalanceSchema = null;
        for(int i=0; i<cmainSchemas.size(); i++)
        {
          prpCmainSchema = (PrpCmainSchema)cmainSchemas.get(i);
          prpJpremBalanceSchema = new PrpJpremBalanceSchema();

          prpJpremBalanceSchema.setYearMonth(this.yearMonth);
          prpJpremBalanceSchema.setClassCode(prpCmainSchema.getClassCode());
          prpJpremBalanceSchema.setRiskCode(prpCmainSchema.getRiskCode());
          prpJpremBalanceSchema.setPolicyNo(prpCmainSchema.getPolicyNo());
          prpJpremBalanceSchema.setCertiNo(prpCmainSchema.getPolicyNo());
          prpJpremBalanceSchema.setStartDate(prpCmainSchema.getStartDate());
          com.sp.utility.string.Date startDate = new com.sp.utility.string.Date(prpCmainSchema.getStartDate());
          com.sp.utility.string.Date endDate = new com.sp.utility.string.Date(prpCmainSchema.getEndDate());
          endDate.set(endDate.MONTH,endDate.get(endDate.MONTH)+Integer.parseInt(prpCmainSchema.getEndHour()));
          endDate.set(endDate.DATE,endDate.get(endDate.DATE)-1);
          prpJpremBalanceSchema.setEndDate(endDate.getString(endDate.YEAR+endDate.MONTH+endDate.DATE));
          prpJpremBalanceSchema.setDays(""+PubTools.getDayMinus(startDate,0,endDate,24));
          int intMonth = PubTools.getMonthMinus(startDate,0,endDate,24);
          int intYear = intMonth/12;
          if(intMonth%12 >= 7)
            intYear++;
          prpJpremBalanceSchema.setYears(""+intYear);
          prpJpremBalanceSchema.setUnderWriteEndDate(prpCmainSchema.getUnderWriteEndDate());
          prpJpremBalanceSchema.setBalanceStartDate(prpCmainSchema.getStartDate());
          
          com.sp.utility.string.Date balanceEndDate = new com.sp.utility.string.Date(prpCmainSchema.getStartDate());
          balanceEndDate.set(balanceEndDate.YEAR,balanceEndDate.get(balanceEndDate.YEAR)+1);
          balanceEndDate.set(balanceEndDate.DATE,balanceEndDate.get(balanceEndDate.DATE)-1);
          prpJpremBalanceSchema.setBalanceEndDate(balanceEndDate.getString(balanceEndDate.YEAR+balanceEndDate.MONTH+balanceEndDate.DATE));
          prpJpremBalanceSchema.setCurrency(prpCmainSchema.getCurrency());
          prpJpremBalanceSchema.setSumPremium(prpCmainSchema.getSumPremium());
          
          double dblThisPremium = 0;
          if (PubTools.compareDate(prpJpremBalanceSchema.getBalanceEndDate(),prpJpremBalanceSchema.getEndDate())>=0
              ||intYear == 1)
          {
            prpJpremBalanceSchema.setBalanceEndDate(prpJpremBalanceSchema.getEndDate());
            dblThisPremium = Double.parseDouble(prpJpremBalanceSchema.getSumPremium());
          }
          else
          {
            intYear = intYear*100+PubTools.getYearMinus(startDate,0,balanceEndDate,24);
            DBPrpDrateLoan dbPrpDrateLoan = new DBPrpDrateLoan();
            
            int intReturn = dbPrpDrateLoan.getInfo(dbpool,"00000000","ECU","001",""+intYear,"1");
            if(intReturn==100)
              throw new UserException(-1198,-98,"BLPrpJpremBalance.prepareECUCB()","没有找到XX分摊比例，不能结转！");
            dblThisPremium = Str.round( Double.parseDouble(prpJpremBalanceSchema.getSumPremium())
                                      * Double.parseDouble(dbPrpDrateLoan.getCoefficient())/100,2);
          }
          prpJpremBalanceSchema.setThisPremium(""+dblThisPremium);
          prpJpremBalanceSchema.setThisOthFee("0");
          prpJpremBalanceSchema.setLeftPremium(""+(Double.parseDouble(prpJpremBalanceSchema.getSumPremium())-dblThisPremium));
          prpJpremBalanceSchema.setHandler1Code(prpCmainSchema.getHandler1Code());
          prpJpremBalanceSchema.setComCode(prpCmainSchema.getComCode());
          prpJpremBalanceSchema.setMakeCom(prpCmainSchema.getMakeCom());
          prpJpremBalanceSchema.setOperatorCode(iUserCode);
          prpJpremBalanceSchema.setTransFlag("0");
          prpJpremBalanceSchema.setInputDate(currentDate);
          prpJpremBalanceSchema.setEndBalanceFlag("0");
          this.setArr(prpJpremBalanceSchema);
        }
        dbpool.beginTransaction();
        this.saveOrUpdate(dbpool);
        
        this.updateRawVoucher(dbpool,blAccRawVoucher);
        dbpool.commitTransaction();
      }
      catch(SQLException se)
      {
        dbpool.rollbackTransaction();
        throw se;
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
     * @desc  提取普通批改（除退XXXXX）数据
     * @param iBranchCode
     * @param iUserCode
     * @throws Exception
     */
    public void prepareECUPGNoEnd(String iBranchCode,String iUserCode) throws Exception
    {
      BLPrpPmain blPrpPmain = new BLPrpPmain();
      DBPrpPmain dbPrpPmain = new DBPrpPmain();
      BLAccRawVoucher blAccRawVoucher = new BLAccRawVoucher();
      DbPool dbpool = new DbPool();

      String strSQL = "SELECT RawVoucher.*,PrpPhead.PolicyNo,PrpPmain.ClassCode,PrpPmain.RiskCode,PrpPhead.ValidDate,"
          + " PrpPmain.StartDate,PrpCmainLoan.LoanYear,PrpPHead.UnderWriteEndDate,PrpPmain.ComCode,PrpPmain.MakeCom,PrpPmain.Handler1Code"
          + " FROM PrpPmain,PrpPhead,PrpCmainLoan,"
          + "(SELECT SerialNo,'CURR',PolicyNo1,Currency,SumMoney FROM AccRawVoucher"
          + "  WHERE F05 IN ("+riskCond+")"     
          + "  AND F26='"+iBranchCode+"'"       
          + "  AND ReceiptType IN ('R50','P20')"
          + "  AND PolicyType1='E'"             
          + "  AND VoucherFlag='4'"             
          + "  AND (SUBSTR(Flag,3,1)!='1' OR SUBSTR(Flag,3,1) IS NULL)"       
          + " UNION "
          + "  SELECT SerialNo,'HIS',PolicyNo1,Currency,SumMoney FROM AccRawVoucherHis"
          + "  WHERE F05 IN ("+riskCond+")"	
          + "  AND ReceiptType IN ('R50','P20')"
          + "  AND F26='"+iBranchCode+"'"       
          + "  AND PolicyType1='E'" 		
          + "  AND VoucherFlag='4'" 		
          + "  AND (SUBSTR(Flag,3,1)!='1' OR SUBSTR(Flag,3,1) IS NULL)"	
          + ") RawVoucher"
          + " WHERE PrpPmain.EndorseNo=RawVoucher.PolicyNo1"
          + " AND PrpPhead.EndorseNo=PrpPmain.EndorseNo"
          + " AND PrpPhead.PolicyNo=PrpCmainLoan.PolicyNo"
          + " AND PrpPhead.ValidDate<='"+this.yearMonthEndDay+"'"
          + " AND PrpPhead.EndorType!='21'"             
          ;
      /*
      String strSQL = "SELECT PrpPmain.* FROM PrpPmain,PrpPhead,PrpCmain"
          + " WHERE PrpPmain.EndorseNo=PrpPhead.EndorseNo"
          + " AND PrpPmain.PolicyNo=PrpCmain.PolicyNo"
          + " AND PrpPhead.UnderWriteFlag IN ('1','3')"
          + " AND (PrpPhead.ValidDate >=PrpPhead.UnderWriteEndDate AND PrpPhead.ValidDate<='"+lastYear+"-12-31'"
          + " OR PrpPhead.ValidDate<PrpPhead.UnderWriteEndDate AND PrpPhead.UnderWriteEndDate<='"+lastYear+"-12-31')"
          + " AND PrpPhead.EndorseNo NOT IN (SELECT DISTINCT CertiNo FROM PrpJpremBalance)"
          + " AND PrpPmain.ChgPremium!=0 "
          + " AND PrpPhead.EndorType!='21'"             
          + " AND SUBSTR(PrpCmain.OthFlag,4,1)!='1'"    
          + " AND PrpPmain.ComCode IN ("+comCond+")"
          + " AND PrpPmain.RiskCode IN ("+riskCond+")";
       */

        try {
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        Vector pmainSchemas = new Vector();
        PrpPmainSchema prpPmainSchema = null;
        AccRawVoucherSchema accRawVoucherSchema = new AccRawVoucherSchema();

        ResultSet rs = dbpool.query(strSQL);
        while(rs.next())
        {
          prpPmainSchema = new PrpPmainSchema();
          accRawVoucherSchema = new AccRawVoucherSchema();
          prpPmainSchema.setPolicyNo(rs.getString("PolicyNo"));
          prpPmainSchema.setEndorseNo(rs.getString("PolicyNo1"));
          prpPmainSchema.setCurrency(rs.getString("Currency"));
          prpPmainSchema.setClassCode(rs.getString("ClassCode"));
          prpPmainSchema.setRiskCode(rs.getString("RiskCode"));
          prpPmainSchema.setStartDate(""+rs.getDate("ValidDate"));   
          prpPmainSchema.setEndDate(""+rs.getDate("StartDate"));
          prpPmainSchema.setEndHour(""+rs.getInt("LoanYear"));
          prpPmainSchema.setUnderWriteEndDate(""+rs.getDate("UnderWriteEndDate"));
          prpPmainSchema.setHandler1Code(rs.getString("Handler1Code"));
          prpPmainSchema.setComCode(rs.getString("ComCode"));
          prpPmainSchema.setMakeCom(rs.getString("MakeCom"));
          prpPmainSchema.setChgPremium(""+rs.getDouble("SumMoney"));
          pmainSchemas.add(prpPmainSchema);

          accRawVoucherSchema.setSerialNo(""+rs.getInt("SerialNo"));  
          accRawVoucherSchema.setFlag(rs.getString("CURR"));
          blAccRawVoucher.setArr(accRawVoucherSchema);
        }
        rs.close();

        this.initArr();
        PrpJpremBalanceSchema prpJpremBalanceSchema = null;
        for(int i=0; i<pmainSchemas.size(); i++)
        {
          prpPmainSchema = (PrpPmainSchema)pmainSchemas.get(i);
          prpJpremBalanceSchema = new PrpJpremBalanceSchema();

          prpJpremBalanceSchema.setYearMonth(this.yearMonth);
          prpJpremBalanceSchema.setClassCode(prpPmainSchema.getClassCode());
          prpJpremBalanceSchema.setRiskCode(prpPmainSchema.getRiskCode());
          prpJpremBalanceSchema.setPolicyNo(prpPmainSchema.getPolicyNo());
          prpJpremBalanceSchema.setCertiNo(prpPmainSchema.getEndorseNo());
          prpJpremBalanceSchema.setStartDate(prpPmainSchema.getStartDate());
          com.sp.utility.string.Date startDate = new com.sp.utility.string.Date(prpPmainSchema.getStartDate());
          com.sp.utility.string.Date endDate = new com.sp.utility.string.Date(prpPmainSchema.getEndDate());
          endDate.set(endDate.MONTH,endDate.get(endDate.MONTH)+Integer.parseInt(prpPmainSchema.getEndHour()));
          endDate.set(endDate.DATE,endDate.get(endDate.DATE)-1);
          prpJpremBalanceSchema.setEndDate(endDate.getString(endDate.YEAR+endDate.MONTH+endDate.DATE));
          prpJpremBalanceSchema.setDays(""+PubTools.getDayMinus(startDate,0,endDate,24));
          int intMonth = PubTools.getMonthMinus(startDate,0,endDate,24);
          int intYear = intMonth/12;
          if(intMonth%12 >= 7)
            intYear++;
          prpJpremBalanceSchema.setYears(""+intYear);
          prpJpremBalanceSchema.setUnderWriteEndDate(prpPmainSchema.getUnderWriteEndDate());
          prpJpremBalanceSchema.setBalanceStartDate(prpPmainSchema.getStartDate());
          
          
          com.sp.utility.string.Date balanceEndDate = new com.sp.utility.string.Date(prpPmainSchema.getStartDate());
          balanceEndDate.set(balanceEndDate.YEAR,balanceEndDate.get(balanceEndDate.YEAR)+1);
          balanceEndDate.set(balanceEndDate.DATE,balanceEndDate.get(balanceEndDate.DATE)-1);
          prpJpremBalanceSchema.setBalanceEndDate(balanceEndDate.getString(balanceEndDate.YEAR+balanceEndDate.MONTH+balanceEndDate.DATE));
          
          prpJpremBalanceSchema.setCurrency(prpPmainSchema.getCurrency());
          prpJpremBalanceSchema.setSumPremium(prpPmainSchema.getChgPremium());
          
          double dblThisPremium = 0;
          if (PubTools.compareDate(prpJpremBalanceSchema.getBalanceEndDate(),prpJpremBalanceSchema.getEndDate())>=0
              ||intYear == 1)
          {
            prpJpremBalanceSchema.setBalanceEndDate(prpJpremBalanceSchema.getEndDate());
            dblThisPremium = Double.parseDouble(prpJpremBalanceSchema.getSumPremium());
          }
          else
          {
            intYear = intYear*100+PubTools.getYearMinus(startDate,0,balanceEndDate,24);
            DBPrpDrateLoan dbPrpDrateLoan = new DBPrpDrateLoan();
            
            int intReturn = dbPrpDrateLoan.getInfo(dbpool,"00000000","ECU","001",""+intYear,"1");
            if(intReturn==100)
              throw new UserException(-1198,-98,"BLPrpJpremBalance.prepareECUPGNoEnd()","没有找到XX分摊比例，不能结转！");
            dblThisPremium = Str.round( Double.parseDouble(prpJpremBalanceSchema.getSumPremium())
                                      * Double.parseDouble(dbPrpDrateLoan.getCoefficient())/100,2);
          }
          prpJpremBalanceSchema.setThisPremium(""+dblThisPremium);
          prpJpremBalanceSchema.setThisOthFee("0");
          prpJpremBalanceSchema.setLeftPremium(""+(Double.parseDouble(prpJpremBalanceSchema.getSumPremium())-dblThisPremium));
          prpJpremBalanceSchema.setHandler1Code(prpPmainSchema.getHandler1Code());
          prpJpremBalanceSchema.setComCode(prpPmainSchema.getComCode());
          prpJpremBalanceSchema.setMakeCom(prpPmainSchema.getMakeCom());
          prpJpremBalanceSchema.setOperatorCode(iUserCode);
          prpJpremBalanceSchema.setTransFlag("0");
          prpJpremBalanceSchema.setInputDate(currentDate);
          prpJpremBalanceSchema.setEndBalanceFlag("0");
          this.setArr(prpJpremBalanceSchema);
        }
        dbpool.beginTransaction();
        this.saveOrUpdate(dbpool);
        
        this.updateRawVoucher(dbpool,blAccRawVoucher);
        dbpool.commitTransaction();
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
     * @desc  提取全单退XXXXX数据
     * @param iBranchCode
     * @param iUserCode
     * @throws Exception
     */
    public void prepareECUPGIsEnd(String iBranchCode,String iUserCode) throws UserException,Exception
    {
      BLPrpPmain blPrpPmain = new BLPrpPmain();
      DBPrpPmain dbPrpPmain = new DBPrpPmain();
      BLAccRawVoucher blAccRawVoucher = new BLAccRawVoucher();

      double dblCommRate = 0.30;  

      /*String strSQL = "SELECT PrpPmain.*"
          + " FROM PrpPmain,PrpPhead,PrpCmain"
          + " WHERE PrpPmain.EndorseNo=PrpPhead.EndorseNo"
          + " AND PrpPmain.PolicyNo=PrpCmain.PolicyNo"
          + " AND PrpPhead.UnderWriteFlag IN ('1','3')"
          + " AND (PrpPhead.ValidDate >=PrpPhead.UnderWriteEndDate AND PrpPhead.ValidDate<='"+lastYear+"-12-31'"
          + " OR PrpPhead.ValidDate<PrpPhead.UnderWriteEndDate AND PrpPhead.UnderWriteEndDate<='"+lastYear+"-12-31')"
          + " AND PrpPhead.EndorseNo NOT IN (SELECT DISTINCT CertiNo FROM PrpJpremBalance)"
          + " AND PrpPhead.EndorType='21'"             
          + " AND SUBSTR(PrpCmain.OthFlag,4,1)!='1'"    
          + " AND PrpPmain.ComCode IN ("+comCond+")"
          + " AND PrpPmain.RiskCode IN ("+riskCond+")";
      */

      String strSQL = "SELECT RawVoucher.*,PrpPhead.PolicyNo,PrpPmain.ClassCode,PrpPmain.RiskCode,"
          + " PrpPhead.ValidDate,PrpPmain.StartDate,PrpPmain.EndDate,PrpPHead.UnderWriteEndDate,"
          + " PrpPmain.ComCode,PrpPmain.MakeCom,PrpPmain.Handler1Code"
          + " FROM PrpPmain,PrpPhead,"
          + "(SELECT SerialNo,'CURR',PolicyNo1,Currency,SumMoney FROM AccRawVoucher"
          + "  WHERE F05 IN ("+riskCond+")"     
          + "  AND F26='"+iBranchCode+"'"       
          + "  AND ReceiptType IN ('R50','P20')"
          + "  AND PolicyType1='E'"             
          + "  AND VoucherFlag='4'"             
          + "  AND (SUBSTR(Flag,3,1)!='1' OR SUBSTR(Flag,3,1) IS NULL)"       
          + " UNION "
          + "  SELECT SerialNo,'HIS',PolicyNo1,Currency,SumMoney FROM AccRawVoucherHis"
          + "  WHERE F05 IN ("+riskCond+")"	
          + "  AND ReceiptType IN ('R50','P20')"
          + "  AND F26='"+iBranchCode+"'"       
          + "  AND PolicyType1='E'" 		
          + "  AND VoucherFlag='4'" 		
          + "  AND (SUBSTR(Flag,3,1)!='1' OR SUBSTR(Flag,3,1) IS NULL)"	
          + ") RawVoucher"
          + " WHERE PrpPmain.EndorseNo=RawVoucher.PolicyNo1"
          + " AND PrpPhead.EndorseNo=PrpPmain.EndorseNo"
          + " AND PrpPhead.ValidDate<='"+this.yearMonthEndDay+"'"
          + " AND PrpPhead.EndorType='21'"             
          ;

      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        Vector pmainSchemas = new Vector();
        PrpPmainSchema prpPmainSchema = null;
        AccRawVoucherSchema accRawVoucherSchema = null;
        ResultSet rs = dbpool.query(strSQL);
        while(rs.next())
        {
          prpPmainSchema = new PrpPmainSchema();
          accRawVoucherSchema = new AccRawVoucherSchema();
          prpPmainSchema.setPolicyNo(rs.getString("PolicyNo"));
          prpPmainSchema.setEndorseNo(rs.getString("PolicyNo1"));
          prpPmainSchema.setCurrency(rs.getString("Currency"));
          prpPmainSchema.setClassCode(rs.getString("ClassCode"));
          prpPmainSchema.setRiskCode(rs.getString("RiskCode"));
          prpPmainSchema.setStartDate(""+rs.getDate("StartDate"));
          prpPmainSchema.setEndDate(""+rs.getDate("EndDate"));
          prpPmainSchema.setInputDate(""+rs.getDate("ValidDate"));   
          prpPmainSchema.setUnderWriteEndDate(""+rs.getDate("UnderWriteEndDate"));  
          prpPmainSchema.setHandler1Code(rs.getString("Handler1Code"));
          prpPmainSchema.setComCode(rs.getString("ComCode"));
          prpPmainSchema.setMakeCom(rs.getString("MakeCom"));
          prpPmainSchema.setChgPremium(""+rs.getDouble("SumMoney"));
          pmainSchemas.add(prpPmainSchema);

          accRawVoucherSchema.setSerialNo(""+rs.getInt("SerialNo"));  
          accRawVoucherSchema.setFlag(rs.getString("CURR"));
          blAccRawVoucher.setArr(accRawVoucherSchema);
        }
        rs.close();

        this.initArr();
        dbpool.beginTransaction();
        PrpJpremBalanceSchema prpJpremBalanceSchema = null;
        for(int i=0; i<pmainSchemas.size(); i++)
        {
          prpPmainSchema = (PrpPmainSchema)pmainSchemas.get(i);
          prpJpremBalanceSchema = new PrpJpremBalanceSchema();

          prpJpremBalanceSchema.setYearMonth(this.yearMonth);
          prpJpremBalanceSchema.setClassCode(prpPmainSchema.getClassCode());
          prpJpremBalanceSchema.setRiskCode(prpPmainSchema.getRiskCode());
          prpJpremBalanceSchema.setPolicyNo(prpPmainSchema.getPolicyNo());
          prpJpremBalanceSchema.setCertiNo(prpPmainSchema.getEndorseNo());
          prpJpremBalanceSchema.setStartDate(prpPmainSchema.getStartDate());
          prpJpremBalanceSchema.setEndDate(prpPmainSchema.getInputDate());
          com.sp.utility.string.Date startDate = new com.sp.utility.string.Date(prpPmainSchema.getStartDate());
          com.sp.utility.string.Date validDate = new com.sp.utility.string.Date(prpPmainSchema.getInputDate());
          prpJpremBalanceSchema.setDays(""+PubTools.getDayMinus(startDate,0,validDate,0));
          int intMonth = PubTools.getMonthMinus(startDate,0,validDate,24);
          int intYear = intMonth/12;
          if(intMonth%12 >= 7)
            intYear++;
          prpJpremBalanceSchema.setYears(""+intYear);
          prpJpremBalanceSchema.setUnderWriteEndDate(prpPmainSchema.getUnderWriteEndDate());
          prpJpremBalanceSchema.setBalanceStartDate(prpPmainSchema.getStartDate());
          prpJpremBalanceSchema.setBalanceEndDate(prpJpremBalanceSchema.getEndDate());
          prpJpremBalanceSchema.setCurrency(prpPmainSchema.getCurrency());
          prpJpremBalanceSchema.setSumPremium(prpPmainSchema.getChgPremium());
          
          strSQL = "SELECT SUM(SumPremium) FROM PrpJpremBalance"
             + " WHERE PolicyNo=CertiNo AND PolicyNo='"+prpPmainSchema.getPolicyNo()+"'"
             
             + " AND EndBalanceFlag='0'";  
          double dblSumPremiumP = this.getSumValue(dbpool,strSQL);
          
          strSQL = "SELECT SUM(SumPremium) FROM PrpJpremBalance"
             + " WHERE PolicyNo!=CertiNo AND PolicyNo='"+prpPmainSchema.getPolicyNo()+"'"
             
             + " AND EndBalanceFlag='0'";  
          double dblSumPremiumE = this.getSumValue(dbpool,strSQL);
          
          com.sp.utility.string.Date endDate = new com.sp.utility.string.Date(prpPmainSchema.getEndDate());
          double dblNoDutyRate = (double)PubTools.getDayMinus(validDate,0,endDate,24)
                                 /PubTools.getDayMinus(startDate,0,endDate,24);
          
          double dblThisOthFee = Str.round((dblSumPremiumP*dblCommRate+dblSumPremiumE)*dblNoDutyRate,2);
          
          strSQL = "SELECT SUM(LeftPremium) FROM PrpJpremBalance"
             + " WHERE PolicyNo='"+prpPmainSchema.getPolicyNo()+"'"
             
             + " AND EndBalanceFlag='0'";  
          double dblLeftPremium = this.getSumValue(dbpool,strSQL);
          
          double dblThisPremium = dblLeftPremium + Double.parseDouble(prpPmainSchema.getChgPremium()) - dblThisOthFee;
          prpJpremBalanceSchema.setThisPremium(""+dblThisPremium);
          prpJpremBalanceSchema.setThisOthFee(""+dblThisOthFee);
          prpJpremBalanceSchema.setLeftPremium("0");
          prpJpremBalanceSchema.setHandler1Code(prpPmainSchema.getHandler1Code());
          prpJpremBalanceSchema.setComCode(prpPmainSchema.getComCode());
          prpJpremBalanceSchema.setMakeCom(prpPmainSchema.getMakeCom());
          prpJpremBalanceSchema.setOperatorCode(iUserCode);
          prpJpremBalanceSchema.setTransFlag("0");
          prpJpremBalanceSchema.setInputDate(currentDate);
          prpJpremBalanceSchema.setEndBalanceFlag("0");
          this.setArr(prpJpremBalanceSchema);
          
          strSQL = "UPDATE PrpJpremBalance SET EndBalanceFlag='1' "
             + " WHERE PolicyNo='"+prpPmainSchema.getPolicyNo()+"'"
             
             + " AND EndBalanceFlag='0'";  
          dbpool.update(strSQL);
        }
        this.saveOrUpdate(dbpool);
        
        this.updateRawVoucher(dbpool,blAccRawVoucher);
        dbpool.commitTransaction();
      }
      catch(SQLException se)
      {
        dbpool.rollbackTransaction();
        throw se;
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
     * @desc  长期XXXXXXX结算表中前年度数据结转到上年度，同时该前年度数据结束结转
     * @param iUserCode
     * @throws Exception
     */
    public void balanceECULast(String iUserCode) throws Exception
    {
      String strWherePart = "";
      PrpJpremBalanceSchema prpJpremBalanceSchema = null;
      this.initArr();
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        
        
        strWherePart = " BalanceEndDate BETWEEN '"+this.yearMonthFirstDay+"' AND '"+this.yearMonthEndDay+"'"
                     + " AND EndBalanceFlag='0' AND ComCode IN ("+this.comCond+")"
                     + " AND YearMonth<'"+this.yearMonth+"' AND RiskCode IN ("+this.riskCond+")";

        this.query(dbpool,strWherePart,0);
        for(int i=0; i<this.getSize(); i++)
        {
          prpJpremBalanceSchema = this.getArr(i);
          prpJpremBalanceSchema.setYearMonth(this.yearMonth);
          
          com.sp.utility.string.Date balanceStartDate = new com.sp.utility.string.Date(prpJpremBalanceSchema.getBalanceEndDate());
          balanceStartDate.set(balanceStartDate.DATE,balanceStartDate.get(balanceStartDate.DATE)+1);
          prpJpremBalanceSchema.setBalanceStartDate(balanceStartDate.getString(balanceStartDate.YEAR+balanceStartDate.MONTH+balanceStartDate.DATE));
          
          
          com.sp.utility.string.Date balanceEndDate = new com.sp.utility.string.Date(prpJpremBalanceSchema.getBalanceEndDate());
          balanceEndDate.set(balanceEndDate.YEAR,balanceEndDate.get(balanceEndDate.YEAR)+1);
          
          prpJpremBalanceSchema.setBalanceEndDate(balanceEndDate.getString(balanceEndDate.YEAR+balanceEndDate.MONTH+balanceEndDate.DATE));
          
          double dblThisPremium = 0;
          if (PubTools.compareDate(prpJpremBalanceSchema.getBalanceEndDate(),prpJpremBalanceSchema.getEndDate())>=0)
          {
            prpJpremBalanceSchema.setBalanceEndDate(prpJpremBalanceSchema.getEndDate());
            dblThisPremium = Double.parseDouble(prpJpremBalanceSchema.getLeftPremium());
          }
          else
          {
            int intYear = Integer.parseInt(prpJpremBalanceSchema.getYears())*100;
            com.sp.utility.string.Date startDate = new com.sp.utility.string.Date(prpJpremBalanceSchema.getStartDate());
            intYear += PubTools.getYearMinus(startDate,0,balanceEndDate,24);
            DBPrpDrateLoan dbPrpDrateLoan = new DBPrpDrateLoan();
            
            int intReturn = dbPrpDrateLoan.getInfo(dbpool,"00000000","ECU","001",""+intYear,"1");
            if(intReturn==100)
              throw new UserException(-1198,-98,"BLPrpJpremBalance.prepareECUPGNoEnd()","没有找到XX分摊比例，不能结转！");
            dblThisPremium = Str.round( Double.parseDouble(prpJpremBalanceSchema.getSumPremium())
                                      * Double.parseDouble(dbPrpDrateLoan.getCoefficient())/100,2);
          }
          prpJpremBalanceSchema.setThisPremium(""+dblThisPremium);
          prpJpremBalanceSchema.setThisOthFee("0");
          prpJpremBalanceSchema.setLeftPremium(""+(Double.parseDouble(prpJpremBalanceSchema.getLeftPremium())-dblThisPremium));
          prpJpremBalanceSchema.setTransFlag("0");
          prpJpremBalanceSchema.setOperatorCode(iUserCode);
          prpJpremBalanceSchema.setInputDate(this.currentDate);
          this.setArr(prpJpremBalanceSchema,i);
        }
        dbpool.beginTransaction();
        this.saveOrUpdate(dbpool);
        
        dbpool.update("UPDATE PrpJpremBalance SET EndBalanceFlag='1' WHERE "+strWherePart);
        dbpool.commitTransaction();
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
     * @desc  生成凭证
     * @param iCenterCode  核算单位
     * @param iBranchCode  基层单位
     * @param iYearMonth   会计期间
     * @param iUserCode    操作员
     * @return strVoucherNo 生成的凭证号
     * @throws UserException
     * @throws Exception
     */
    public String createVoucher(String iCenterCode,String iBranchCode,String iYearMonth,String iUserCode) throws UserException,Exception
    {
      String strWherePart = "";
      String strSQL = "";
      BLAccVoucher blAccVoucher = null;
      PrpJpremBalanceSchema prpJpremBalanceSchema = null;
      AccMainVoucherSchema accMainVoucherSchema = null;
      AccSubVoucherSchema accSubVoucherSchema = null;
      AccVoucherArticleSchema accVoucherArticleSchema = null;
      String strVoucherNo = "";

      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        
        
        
        strWherePart = " (YearMonth='"+iYearMonth+"' OR YearMonth='200407') AND TransFlag='0'"
                     + " AND ComCode IN ("+this.comCond+")";
        
        strSQL = "SELECT DISTINCT Currency FROM PrpJpremBalance WHERE " + strWherePart;
        ArrayList arrCurrency = new ArrayList();
        ResultSet rs = dbpool.query(strSQL);
        int i = 0;
        while(rs.next())
        {
          arrCurrency.add(rs.getString(1));
        }
        rs.close();

        dbpool.beginTransaction();
        
        for(i=0; i<arrCurrency.size(); i++)
        {
          blAccVoucher = new BLAccVoucher();
          
          accMainVoucherSchema = new AccMainVoucherSchema();
          accMainVoucherSchema.setVoucherType("1");
          accMainVoucherSchema.setGenerateWay("7");
          accMainVoucherSchema.setAccBookType("01");
          accMainVoucherSchema.setCenterCode(iCenterCode);
          accMainVoucherSchema.setBranchCode(iBranchCode);
          accMainVoucherSchema.setYearMonth(iYearMonth);
          accMainVoucherSchema.setVoucherDate(currentDate);
          accMainVoucherSchema.setAuxNumber("0");
          accMainVoucherSchema.setOperatorCode(iUserCode);
          accMainVoucherSchema.setVoucherFlag("1");
          
          DBPrpDcurrency dbPrpDcurrency = new DBPrpDcurrency();
          dbPrpDcurrency.getInfo(dbpool,(String)arrCurrency.get(i));
          if(dbPrpDcurrency.getAccBookCode()==null||
             dbPrpDcurrency.getAccBookCode()!=null&&dbPrpDcurrency.getAccBookCode().equals(""))
            throw new UserException(-98,-2,"BLPrpJpremBalance.createVoucher","无法根据币别["+(String)arrCurrency.get(i)+"]取得帐套信息");
          accMainVoucherSchema.setAccBookCode(dbPrpDcurrency.getAccBookCode());
          
          DBPrpDuser dbPrpDuser = new DBPrpDuser();
          dbPrpDuser.getInfo(dbpool,iUserCode);
          accMainVoucherSchema.setOperatorBranch(dbPrpDuser.getComCode());

          blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

          
          strSQL = "SELECT ClassCode,RiskCode,ComCode,Handler1Code,"
              + " SUM(ThisPremium) AS ThisPremium,SUM(ThisOthFee) AS ThisOthFee"
              + " FROM PrpJpremBalance WHERE "+strWherePart
              + " AND Currency='"+(String)arrCurrency.get(i)+"'"
              + " GROUP BY ClassCode,RiskCode,ComCode,Handler1Code"
              + " ORDER BY ClassCode,RiskCode,ComCode,Handler1Code";

          this.initArr();
          rs = dbpool.query(strSQL);
          while(rs.next())
          {
            prpJpremBalanceSchema = new PrpJpremBalanceSchema();
            prpJpremBalanceSchema.setClassCode(rs.getString("ClassCode"));
            prpJpremBalanceSchema.setRiskCode(rs.getString("RiskCode"));
            prpJpremBalanceSchema.setComCode(rs.getString("ComCode"));
            prpJpremBalanceSchema.setHandler1Code(rs.getString("Handler1Code"));
            prpJpremBalanceSchema.setThisPremium(""+rs.getDouble("ThisPremium"));
            prpJpremBalanceSchema.setThisOthFee(""+rs.getDouble("ThisOthFee"));
            this.setArr(prpJpremBalanceSchema);
          }
          rs.close();
          
          BLAccRawToVoucher blAccRawToVoucher = new BLAccRawToVoucher();
          
          String strRemark = "结转"+this.yearMonth+"长期XXXXX预收XX";

          
          for(int j=0; j<this.getSize(); j++)
          {
            prpJpremBalanceSchema = this.getArr(j);
            double dblDest = Double.parseDouble(prpJpremBalanceSchema.getThisPremium())
                           + Double.parseDouble(prpJpremBalanceSchema.getThisOthFee());
            if(dblDest == 0) continue;
            accSubVoucherSchema = new AccSubVoucherSchema();
            accSubVoucherSchema.setAccBookCode(accMainVoucherSchema.getAccBookCode());
            accSubVoucherSchema.setAccBookType(accMainVoucherSchema.getAccBookType());
            accSubVoucherSchema.setCenterCode(accMainVoucherSchema.getCenterCode());
            accSubVoucherSchema.setYearMonth(iYearMonth);
            accSubVoucherSchema.setCurrency((String)arrCurrency.get(i));
            accSubVoucherSchema.setExchangeRate("1");
            accSubVoucherSchema.setSuffixNo("0");
            accSubVoucherSchema.setItemCode("2121");
            accSubVoucherSchema.setF01(prpJpremBalanceSchema.getClassCode());
            accSubVoucherSchema.setF05(prpJpremBalanceSchema.getRiskCode());
            accSubVoucherSchema.setF26(accMainVoucherSchema.getBranchCode());
            accSubVoucherSchema.setF27(prpJpremBalanceSchema.getHandler1Code());
            accSubVoucherSchema.setF28(prpJpremBalanceSchema.getComCode());
            accSubVoucherSchema.setDebitDest(""+dblDest);
            accSubVoucherSchema.setCreditDest("0");
            accSubVoucherSchema.setDebitSource(accSubVoucherSchema.getDebitDest());
            accSubVoucherSchema.setCreditSource(accSubVoucherSchema.getCreditDest());
            accSubVoucherSchema.setRemark(strRemark);
            
            accSubVoucherSchema = blAccRawToVoucher.findDirection(dbpool,accSubVoucherSchema);
            blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            
            blAccVoucher.getBLAccSubVoucher().createRawArticle(accSubVoucherSchema,accSubVoucherSchema.getItemCode());
          }

          
          for(int j=0; j<this.getSize(); j++)
          {
            prpJpremBalanceSchema = this.getArr(j);
            if(Double.parseDouble(prpJpremBalanceSchema.getThisPremium())==0)
              continue;
            accSubVoucherSchema = new AccSubVoucherSchema();
            accSubVoucherSchema.setAccBookCode(accMainVoucherSchema.getAccBookCode());
            accSubVoucherSchema.setAccBookType(accMainVoucherSchema.getAccBookType());
            accSubVoucherSchema.setCenterCode(accMainVoucherSchema.getCenterCode());
            accSubVoucherSchema.setYearMonth(iYearMonth);
            accSubVoucherSchema.setCurrency((String)arrCurrency.get(i));
            accSubVoucherSchema.setExchangeRate("1");
            accSubVoucherSchema.setSuffixNo("0");
            accSubVoucherSchema.setItemCode("4101");
            accSubVoucherSchema.setF01(prpJpremBalanceSchema.getClassCode());
            accSubVoucherSchema.setF05(prpJpremBalanceSchema.getRiskCode());
            accSubVoucherSchema.setF26(accMainVoucherSchema.getBranchCode());
            accSubVoucherSchema.setF27(prpJpremBalanceSchema.getHandler1Code());
            accSubVoucherSchema.setF28(prpJpremBalanceSchema.getComCode());
            accSubVoucherSchema.setDebitDest("0");
            accSubVoucherSchema.setCreditDest(prpJpremBalanceSchema.getThisPremium());
            accSubVoucherSchema.setDebitSource(accSubVoucherSchema.getDebitDest());
            accSubVoucherSchema.setCreditSource(accSubVoucherSchema.getCreditDest());
            accSubVoucherSchema.setRemark(strRemark);
            
            accSubVoucherSchema = blAccRawToVoucher.findDirection(dbpool,accSubVoucherSchema);
            blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            
            blAccVoucher.getBLAccSubVoucher().createRawArticle(accSubVoucherSchema,accSubVoucherSchema.getItemCode());
          }

          
          for(int j=0; j<this.getSize(); j++)
          {
            prpJpremBalanceSchema = this.getArr(j);
            if(Double.parseDouble(prpJpremBalanceSchema.getThisOthFee())==0)
              continue;
            accSubVoucherSchema = new AccSubVoucherSchema();
            accSubVoucherSchema.setAccBookCode(accMainVoucherSchema.getAccBookCode());
            accSubVoucherSchema.setAccBookType(accMainVoucherSchema.getAccBookType());
            accSubVoucherSchema.setCenterCode(accMainVoucherSchema.getCenterCode());
            accSubVoucherSchema.setYearMonth(iYearMonth);
            accSubVoucherSchema.setCurrency((String)arrCurrency.get(i));
            accSubVoucherSchema.setExchangeRate("1");
            accSubVoucherSchema.setSuffixNo("0");
            accSubVoucherSchema.setItemCode("4109");
            accSubVoucherSchema.setF01(prpJpremBalanceSchema.getClassCode());
            accSubVoucherSchema.setF05(prpJpremBalanceSchema.getRiskCode());
            accSubVoucherSchema.setF26(accMainVoucherSchema.getBranchCode());
            accSubVoucherSchema.setF27(prpJpremBalanceSchema.getHandler1Code());
            accSubVoucherSchema.setF28(prpJpremBalanceSchema.getComCode());
            accSubVoucherSchema.setDebitDest("0");
            accSubVoucherSchema.setCreditDest(prpJpremBalanceSchema.getThisOthFee());
            accSubVoucherSchema.setDebitSource(accSubVoucherSchema.getDebitDest());
            accSubVoucherSchema.setCreditSource(accSubVoucherSchema.getCreditDest());
            accSubVoucherSchema.setRemark(strRemark);
            
            accSubVoucherSchema = blAccRawToVoucher.findDirection(dbpool,accSubVoucherSchema);
            blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            
            blAccVoucher.getBLAccSubVoucher().createRawArticle(accSubVoucherSchema,accSubVoucherSchema.getItemCode());
          }

          
          blAccVoucher.getBLAccSubVoucher().voucherComp("111");

          
          for(int j=0; j<blAccVoucher.getBLAccSubVoucher().getSize(); j++)
          {
            accSubVoucherSchema = blAccVoucher.getBLAccSubVoucher().getArr(j);
            accSubVoucherSchema.setSuffixNo(""+(j+1));
            String[] arrArticleCode = blAccVoucher.getBLAccSubVoucher().getItemArticle(j);
            String strDirectionIdx = accSubVoucherSchema.getItemCode();
            if (!accSubVoucherSchema.getDirectionIdx().equals("00"))
              strDirectionIdx += "/" + accSubVoucherSchema.getDirectionIdx();
            String directionOther = "";
            for(int jj=1; jj<arrArticleCode.length; jj++)
            {
              accVoucherArticleSchema = new AccVoucherArticleSchema();

              accVoucherArticleSchema.setCenterCode(accMainVoucherSchema.getCenterCode());
              accVoucherArticleSchema.setAccBookType(accMainVoucherSchema.getAccBookType());
              accVoucherArticleSchema.setAccBookCode(accMainVoucherSchema.getAccBookCode());
              accVoucherArticleSchema.setYearMonth(accMainVoucherSchema.getYearMonth());
              accVoucherArticleSchema.setSuffixNo(accSubVoucherSchema.getSuffixNo());
              accVoucherArticleSchema.setItemCode(accSubVoucherSchema.getItemCode());
              accVoucherArticleSchema.setDirectionIdx(strDirectionIdx);

              if (arrArticleCode[jj]!=null&&!arrArticleCode[jj].trim().equals("")) {
                accVoucherArticleSchema.setArticleCode(arrArticleCode[jj].trim());
                blAccVoucher.getBLAccVoucherArticle().setArr(accVoucherArticleSchema);
                directionOther += arrArticleCode[jj].trim() + "/";
              }
            }
            accSubVoucherSchema.setDirectionOther(directionOther);
            blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema, j);
          }

          
          blAccVoucher.save(dbpool);
          if(i==0)
            strVoucherNo = arrCurrency.get(i)+":"+blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
          else
            strVoucherNo += ","+arrCurrency.get(i)+":"+blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
          
          strSQL = "UPDATE PrpJpremBalance SET TransFlag='1',"
              + "CenterCode='"+accMainVoucherSchema.getCenterCode()+"',"
              + "AccBookCode='"+accMainVoucherSchema.getAccBookCode()+"',"
              + "VoucherYearMonth='"+accMainVoucherSchema.getYearMonth()+"',"
              + "VoucherNo="+blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo()+" "
              + "WHERE "+strWherePart+" AND Currency='"+arrCurrency.get(i)+"'";
          dbpool.update(strSQL);
        } 

        
        strSQL = "UPDATE PrpJpremBalance SET EndBalanceFlag='1' WHERE BalanceEndDate>=EndDate"
            + " AND YearMonth='"+this.yearMonth+"' AND ComCode IN ("+this.comCond+")";
        dbpool.update(strSQL);
        dbpool.commitTransaction();
      }
      catch(Exception e)
      {
        dbpool.rollbackTransaction();
        throw e;
      }
      finally {
        dbpool.close();
      }
      return strVoucherNo;
    }

    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
