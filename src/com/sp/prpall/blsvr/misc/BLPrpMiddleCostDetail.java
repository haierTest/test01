package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.blsvr.cb.BLPrpCfee;
import com.sp.prpall.blsvr.cb.BLPrpCitemKind;
import com.sp.prpall.blsvr.pg.BLPrpPfee;
import com.sp.prpall.blsvr.pg.BLPrpPitemKind;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.misc.DBPrpMiddleCostDetail;
import com.sp.prpall.dbsvr.pg.DBPrpPmain;
import com.sp.prpall.schema.PrpCitemKindSchema;
import com.sp.prpall.schema.PrpMiddleCostDetailSchema;
import com.sp.prpall.schema.PrpMiddleCostSchema;
import com.sp.prpall.schema.PrpPitemKindSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.Str;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 定义PrpMiddleCostDetail-特殊因子明细表的BL类
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-01-19</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpMiddleCostDetail{
    private Vector schemas = new Vector();
    protected final Log logger = LogFactory.getLog(getClass());
    /**
     * 构造函数
     */
    public BLPrpMiddleCostDetail(){
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
     *增加一条PrpMiddleCostDetailSchema记录
     *@param iPrpMiddleCostDetailSchema PrpMiddleCostDetailSchema
     *@throws Exception
     */
    public void setArr(PrpMiddleCostDetailSchema iPrpMiddleCostDetailSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpMiddleCostDetailSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpMiddleCostDetailSchema记录
     *@param index 下标
     *@return 一个PrpMiddleCostDetailSchema对象
     *@throws Exception
     */
    public PrpMiddleCostDetailSchema getArr(int index) throws Exception
    {
     PrpMiddleCostDetailSchema prpMiddleCostDetailSchema = null;

       try
       {
        prpMiddleCostDetailSchema = (PrpMiddleCostDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpMiddleCostDetailSchema;
     }
    /**
     *删除一条PrpMiddleCostDetailSchema记录
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
     *特殊因子明细表按标的序号累积求和，用于计算批改时当前XX的手续费金额
     *@param iWherePart
     *@return 无
     */
    public void sumByItemKindNo(String iWherePart) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpMiddleCostDetail dbPrpMiddleCostDetail = new DBPrpMiddleCostDetail();
      initArr();
      strSqlStatement = " SELECT SUM(DISFEE) AS DISFEE,CURRENCY,ITEMKINDNO  FROM PrpMiddleCostDetail WHERE " + iWherePart+" GROUP BY CURRENCY,ITEMKINDNO ORDER BY ITEMKINDNO";
      schemas = dbPrpMiddleCostDetail.sumByCurrency(strSqlStatement);
    }
    /**
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param iEndorseNo 批单号
     *@return 无
     */
    public void cancel(DbPool dbpool,String certiNo) throws Exception
    {
      DBPrpMiddleCostDetail dbPrpMiddleCostDetail = new DBPrpMiddleCostDetail();
      dbPrpMiddleCostDetail.delete(dbpool,certiNo);
    }
    /**
    * 中间成本明细拆分
    * @param dbpool
    * @param certiNo
    * @throws Exception
    */
    public void getData(DbPool dbpool, String certiNo) throws Exception
    {
        
        
        
        
        String strWherePart = " CertiNo=? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(certiNo);
        query(dbpool, strWherePart, arrWhereValue, 0);
        
  }
  
  /**
   *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
   *@author wangchuanzhong 20100602
   *@param dbpool      全局池
   *@param iWherePart  查询条件,传入条件均已绑定变量形式，问号个数与属性值个数一致
   *@param iWhereValue 查询条件各字段值
   *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
   *@throws UserException
   *@throws Exception
   */
  public void query(DbPool dbpool,String iWherePart,ArrayList iWhereValue, int iLimitCount) throws UserException,Exception
  {
      String strSqlStatement = "";
      DBPrpMiddleCostDetail dbPrpMiddleCostDetail = new DBPrpMiddleCostDetail();
      if (iLimitCount > 0 && dbPrpMiddleCostDetail.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
      {
          throw new UserException(-98,-1003,"BLPrpMiddleCostDetail.query");
      }else
      {
          initArr();
          strSqlStatement = " SELECT * FROM PrpMiddleCostDetail WHERE " + iWherePart;
          schemas = dbPrpMiddleCostDetail.findByConditions(dbpool,strSqlStatement,iWhereValue);
      }
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
      DBPrpMiddleCostDetail dbPrpMiddleCostDetail = new DBPrpMiddleCostDetail();
      if (iLimitCount > 0 && dbPrpMiddleCostDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpMiddleCostDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpMiddleCostDetail WHERE " + iWherePart;
        schemas = dbPrpMiddleCostDetail.findByConditions(strSqlStatement);
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
      DBPrpMiddleCostDetail dbPrpMiddleCostDetail = new DBPrpMiddleCostDetail();
      if (iLimitCount > 0 && dbPrpMiddleCostDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpMiddleCostDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpMiddleCostDetail WHERE " + iWherePart;
        schemas = dbPrpMiddleCostDetail.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpMiddleCostDetail dbPrpMiddleCostDetail = new DBPrpMiddleCostDetail();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpMiddleCostDetail.setSchema((PrpMiddleCostDetailSchema)schemas.get(i));
      dbPrpMiddleCostDetail.insert(dbpool);
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
     * @desc 核XXXXX通过后自动生成一张中间成本明细
     * @throws UserException
     * @throws Exception
     */
    public void createPdisPremiumDetail(DbPool dbpool, BLPrpMiddleCost blPrpMiddleCost,DBPrpCmain dbPrpCmain)
    throws UserException,Exception {

      double  dbDisRate  = 0;
      double  dbDisFee   = 0;
      double  dbPremium  = 0;
      double  dbExchangeRate = 0;
      double  dbSelfRate = 0;
      double  dbSumDisFee= 0;

      BLPrpCitemKind blPrpCitemKind = new BLPrpCitemKind();
      BLPrpCfee      blPrpCfee      = new BLPrpCfee();

      PrpCitemKindSchema        prpCitemKindSchema        = new PrpCitemKindSchema();
      PrpMiddleCostSchema       prpMiddleCostSchema       = new PrpMiddleCostSchema();
      PrpMiddleCostDetailSchema prpMiddleCostDetailSchema = new PrpMiddleCostDetailSchema();

      if(blPrpMiddleCost.getSize()<1 || dbPrpCmain.getPolicyNo().equals("") )
        return;

      prpMiddleCostSchema=blPrpMiddleCost.getArr(0);
      blPrpCitemKind.query(dbpool,"PolicyNo = '"+ dbPrpCmain.getPolicyNo() +"'",0);
      blPrpCfee.query(dbpool,"PolicyNo = '"+ dbPrpCmain.getPolicyNo() +"'",0);

      if(blPrpCitemKind.getSize()<1 || blPrpCfee.getSize()<1 )
        return;

      dbSelfRate = Double.parseDouble(Str.chgStrZero(prpMiddleCostSchema.getSelfRate()));
      if(dbSelfRate==0)
        dbSelfRate = 100;
      for(int i=0;i<blPrpCitemKind.getSize();i++)
      {

        prpCitemKindSchema = blPrpCitemKind.getArr(i);
        prpMiddleCostDetailSchema = new PrpMiddleCostDetailSchema();


        prpMiddleCostDetailSchema.setCertiNo(prpMiddleCostSchema.getCertiNo());
        prpMiddleCostDetailSchema.setCertiType(prpMiddleCostSchema.getCertiType());
        prpMiddleCostDetailSchema.setRiskCode(prpMiddleCostSchema.getRiskCode());
        prpMiddleCostDetailSchema.setPolicyNo(prpMiddleCostSchema.getPolicyNo());
        prpMiddleCostDetailSchema.setEndorseNo(prpMiddleCostSchema.getEndorseNo());
        prpMiddleCostDetailSchema.setCurrency(prpMiddleCostSchema.getCurrency());
        prpMiddleCostDetailSchema.setItemKindNo(prpCitemKindSchema.getItemKindNo());
        prpMiddleCostDetailSchema.setKindCode(prpCitemKindSchema.getKindCode());
        prpMiddleCostDetailSchema.setItemCode(prpCitemKindSchema.getItemCode());
        if(prpCitemKindSchema.getFlag().length()>=6&&
           prpCitemKindSchema.getFlag().substring(5,6).equals("1"))
        {
           dbDisRate = 0;
        }
        else
        {
          dbDisRate = Double.parseDouble(Str.chgStrZero(dbPrpCmain.getDisRate1()));
        }

        if(prpCitemKindSchema.getCurrency().trim().equals(prpMiddleCostSchema.getCurrency().trim()))
        {
           dbPremium = Double.parseDouble(Str.chgStrZero(prpCitemKindSchema.getPremium()));
        }
        else
        {
          for(int j=0;j<blPrpCfee.getSize();j++)
          {
            dbExchangeRate = 0;
            if(blPrpCfee.getArr(j).getCurrency().trim().equals(prpCitemKindSchema.getCurrency().trim())
               && blPrpCfee.getArr(j).getCurrency1().trim().equals(prpMiddleCostSchema.getCurrency().trim()))
            {
               dbExchangeRate = Double.parseDouble(Str.chgStrZero(blPrpCfee.getArr(j).getExchangeRate1()));
               break;
            }
          }
          if(dbExchangeRate==0)
            dbExchangeRate = 1;
          dbPremium = Double.parseDouble(Str.chgStrZero(prpCitemKindSchema.getPremium()));
          dbPremium = dbPremium*dbExchangeRate;
        }
        dbDisFee = dbPremium * dbDisRate / 100 * dbSelfRate/100;
        dbDisFee = Str.round(Str.round(dbDisFee, 8), 2);
        
        logger.info(dbSelfRate);
        
        dbSumDisFee = Str.round(dbSumDisFee+dbDisFee,2);

        if(!prpMiddleCostSchema.getClassCode().trim().equals("05") && i==(blPrpCitemKind.getSize()-1)
           && dbSumDisFee != Double.parseDouble(Str.chgStrZero(prpMiddleCostSchema.getDisFee())))
        {
          dbDisFee = Str.round(dbDisFee + Double.parseDouble(Str.chgStrZero(prpMiddleCostSchema.getDisFee()))- dbSumDisFee,2);
        }
        prpMiddleCostDetailSchema.setDisRate(""+dbDisRate);
        prpMiddleCostDetailSchema.setDisFee(""+dbDisFee);
        this.setArr(prpMiddleCostDetailSchema);
      }
      if(prpMiddleCostSchema.getClassCode().trim().equals("05"))
      {
        prpMiddleCostSchema.setDisFee(""+dbSumDisFee);
      }
    }
    /**
     * @desc 批改手续费后生成明细
     * @throws UserException
     * @throws Exception
     */
    public void createEdisPremiumDetail(BLPrpMiddleCost blPrpMiddleCost)
    throws UserException,Exception {

        double  dbSelfRate = 0;
        double  dbDisRate  = 0;
        double  dbDisRate1 = 0;
        double  dbDisFee   = 0;
        double  dbDisFee1   = 0;
        double  dbDisFee2   = 0;        
        double  dbExchangeRate = 0;
        double  dbSumDisFee= 0;
        double  dbPremium  = 0;
        

        BLPrpCitemKind            blPrpCitemKind            = new BLPrpCitemKind();
        BLPrpCfee                 blPrpCfee                 = new BLPrpCfee();
        BLPrpMiddleCostDetail     blPrpMiddleCostDetail     = new BLPrpMiddleCostDetail();
        DBPrpPmain                dbPrpPmain                = new DBPrpPmain();
        DBPrpCmain                dbPrpCmain                = new DBPrpCmain();
        
 
        
        PrpCitemKindSchema        prpCitemKindSchema        = new PrpCitemKindSchema();
        PrpMiddleCostSchema       prpMiddleCostSchema       = new PrpMiddleCostSchema();
        PrpMiddleCostDetailSchema prpMiddleCostDetailSchema = new PrpMiddleCostDetailSchema();
        
        PrpMiddleCostDetailSchema prpMiddleCostDetailSchema1 = new PrpMiddleCostDetailSchema();
        prpMiddleCostSchema = blPrpMiddleCost.getArr(0);
        
        dbPrpPmain.getInfo(prpMiddleCostSchema.getCertiNo());
        dbPrpCmain.getInfo(prpMiddleCostSchema.getPolicyNo());

        if(blPrpMiddleCost.getSize()<1 || dbPrpCmain.getPolicyNo().equals("") )
          return;
        
       
       blPrpCitemKind.query("PolicyNo = '"+ dbPrpCmain.getPolicyNo() +"'",0);
       
       blPrpCfee.query("PolicyNo = '"+ dbPrpCmain.getPolicyNo() +"'",0);
       
       blPrpMiddleCostDetail.sumByItemKindNo("PolicyNo = '"+ dbPrpCmain.getPolicyNo() +"'");
       
       
       
       
       if(blPrpCitemKind.getSize()<1 || blPrpCfee.getSize()<1 )
         return;

       dbSelfRate = Double.parseDouble(Str.chgStrZero(prpMiddleCostSchema.getSelfRate()));
       if(dbSelfRate==0)
         dbSelfRate = 100;

       for(int i=0;i<blPrpCitemKind.getSize();i++)
       {

          prpCitemKindSchema = blPrpCitemKind.getArr(i);
          prpMiddleCostDetailSchema = new PrpMiddleCostDetailSchema();
          prpMiddleCostDetailSchema.setCertiNo(prpMiddleCostSchema.getCertiNo());
          prpMiddleCostDetailSchema.setCertiType(prpMiddleCostSchema.getCertiType());
          prpMiddleCostDetailSchema.setRiskCode(prpMiddleCostSchema.getRiskCode());
          prpMiddleCostDetailSchema.setPolicyNo(prpMiddleCostSchema.getPolicyNo());
          prpMiddleCostDetailSchema.setEndorseNo(prpMiddleCostSchema.getEndorseNo());
          prpMiddleCostDetailSchema.setCurrency(prpMiddleCostSchema.getCurrency());
          prpMiddleCostDetailSchema.setItemKindNo(prpCitemKindSchema.getItemKindNo());
          prpMiddleCostDetailSchema.setKindCode(prpCitemKindSchema.getKindCode());
          prpMiddleCostDetailSchema.setItemCode(prpCitemKindSchema.getItemCode());
          if(prpCitemKindSchema.getFlag().length()>=6&&
                prpCitemKindSchema.getFlag().substring(5,6).equals("1"))
             {
                dbDisRate = 0;
             }
             else
             {
               dbDisRate = Double.parseDouble(Str.chgStrZero(prpMiddleCostSchema.getDisRate()));
             }

          if(prpCitemKindSchema.getCurrency().trim().equals(prpMiddleCostSchema.getCurrency().trim()))
          {
             dbPremium = Double.parseDouble(Str.chgStrZero(prpCitemKindSchema.getPremium()));
          }
          else
          {
            for(int j=0;j<blPrpCfee.getSize();j++)
            {
              dbExchangeRate = 0;
              if(blPrpCfee.getArr(j).getCurrency().trim().equals(prpCitemKindSchema.getCurrency().trim())
                 && blPrpCfee.getArr(j).getCurrency1().trim().equals(prpMiddleCostSchema.getCurrency().trim()))
              {
                 dbExchangeRate = Double.parseDouble(Str.chgStrZero(blPrpCfee.getArr(j).getExchangeRate1()));
                 break;
              }
            }
            if(dbExchangeRate==0)
              dbExchangeRate = 1;
            dbPremium = Double.parseDouble(Str.chgStrZero(prpCitemKindSchema.getPremium()));
            dbPremium = dbPremium*dbExchangeRate;
          }

          
          dbDisFee2 = 0;
          
          for(int n=0;n<blPrpMiddleCostDetail.getSize();n++)
          {
          	prpMiddleCostDetailSchema1 = blPrpMiddleCostDetail.getArr(n);
          	if(prpMiddleCostDetailSchema1.getItemKindNo().trim().equals(prpCitemKindSchema.getItemKindNo().trim()))
            {
              dbDisFee2 = Double.parseDouble(Str.chgStrZero(prpMiddleCostDetailSchema1.getDisFee()));
              break;
            }
          }
          
          
          
          
          
          
          
          
          
          dbDisFee = dbPremium  * dbDisRate / 100 * dbSelfRate/100 - dbDisFee2;
          dbDisFee = Str.round(Str.round(dbDisFee, 8), 2);

          dbSumDisFee = Str.round(dbSumDisFee+dbDisFee,2);

          if(!prpMiddleCostSchema.getClassCode().trim().equals("05") && i==(blPrpCitemKind.getSize()-1)
             && dbSumDisFee != Double.parseDouble(Str.chgStrZero(prpMiddleCostSchema.getDisFee())))
          {
            dbDisFee = Str.round(dbDisFee + Double.parseDouble(Str.chgStrZero(prpMiddleCostSchema.getDisFee()))- dbSumDisFee,2);
          }

          prpMiddleCostDetailSchema.setDisRate(""+dbDisRate);
          prpMiddleCostDetailSchema.setDisFee(""+dbDisFee);
          this.setArr(prpMiddleCostDetailSchema);
        }
        if(prpMiddleCostSchema.getClassCode().trim().equals("05"))
        {
          prpMiddleCostSchema.setDisFee(""+dbSumDisFee);
        }
    }
    /**
     * @desc 核批通过后自动生成一张中间成本明细
     * @throws UserException
     * @throws Exception
     */
    public void createEdisPremiumDetail(DbPool dbpool, BLPrpMiddleCost blPrpMiddleCost,DBPrpPmain dbPrpPmain)
    throws UserException,Exception {

      double  dbDisRate  = 0;
      double  dbDisFee   = 0;
      double  dbPremium  = 0;
      double  dbExchangeRate = 0;
      double  dbSelfRate = 0;
      double  dbSumDisFee= 0;

      BLPrpPitemKind blPrpPitemKind = new BLPrpPitemKind();
      BLPrpPfee      blPrpPfee      = new BLPrpPfee();
      
      PrpPitemKindSchema        prpPitemKindSchema        = new PrpPitemKindSchema();
      PrpMiddleCostSchema       prpMiddleCostSchema       = new PrpMiddleCostSchema();
      PrpMiddleCostDetailSchema prpMiddleCostDetailSchema = new PrpMiddleCostDetailSchema();

      if(blPrpMiddleCost.getSize()<1 || dbPrpPmain.getEndorseNo().equals("") )
        return;

      prpMiddleCostSchema=blPrpMiddleCost.getArr(0);
      blPrpPitemKind.query(dbpool,"EndorseNo = '"+ dbPrpPmain.getEndorseNo() +"'",0);
      blPrpPfee.query(dbpool,"EndorseNo = '"+ dbPrpPmain.getEndorseNo() +"'",0);

      if(blPrpPitemKind.getSize()<1 || blPrpPfee.getSize()<1 )
        return;

      dbSelfRate = Double.parseDouble(Str.chgStrZero(prpMiddleCostSchema.getSelfRate()));
      if (dbSelfRate==0)
        dbSelfRate = 100;

      for(int i=0;i<blPrpPitemKind.getSize();i++)
      {

        prpPitemKindSchema = blPrpPitemKind.getArr(i);
        prpMiddleCostDetailSchema = new PrpMiddleCostDetailSchema();


        prpMiddleCostDetailSchema.setCertiNo(prpMiddleCostSchema.getCertiNo());
        prpMiddleCostDetailSchema.setCertiType(prpMiddleCostSchema.getCertiType());
        prpMiddleCostDetailSchema.setRiskCode(prpMiddleCostSchema.getRiskCode());
        prpMiddleCostDetailSchema.setPolicyNo(prpMiddleCostSchema.getPolicyNo());
        prpMiddleCostDetailSchema.setEndorseNo(prpMiddleCostSchema.getEndorseNo());
        prpMiddleCostDetailSchema.setCurrency(prpMiddleCostSchema.getCurrency());
        prpMiddleCostDetailSchema.setItemKindNo(prpPitemKindSchema.getItemKindNo());
        prpMiddleCostDetailSchema.setKindCode(prpPitemKindSchema.getKindCode());
        prpMiddleCostDetailSchema.setItemCode(prpPitemKindSchema.getItemCode());
        if(prpPitemKindSchema.getFlag().length()>=6&&
           prpPitemKindSchema.getFlag().substring(5,6).equals("1"))
        {
           dbDisRate = 0;
        }
        else
        {
          dbDisRate = Double.parseDouble(Str.chgStrZero(dbPrpPmain.getDisRate1()));
        }

        if(prpPitemKindSchema.getCurrency().trim().equals(prpMiddleCostSchema.getCurrency().trim()))
        {
           dbPremium = Double.parseDouble(Str.chgStrZero(prpPitemKindSchema.getChgPremium()));
        }
        else
        {
          for(int j=0;j<blPrpPfee.getSize();j++)
          {
            dbExchangeRate = 0;
            if(blPrpPfee.getArr(j).getCurrency().trim().equals(prpPitemKindSchema.getCurrency().trim())
               && blPrpPfee.getArr(j).getCurrency1().trim().equals(prpMiddleCostSchema.getCurrency().trim()))
            {
               dbExchangeRate = Double.parseDouble(Str.chgStrZero(blPrpPfee.getArr(j).getExchangeRate1()));
               break;
            }
          }
          if(dbExchangeRate==0)
            dbExchangeRate = 1;
          dbPremium = Double.parseDouble(Str.chgStrZero(prpPitemKindSchema.getChgPremium()));
          dbPremium = dbPremium*dbExchangeRate;
        }
        dbDisFee = dbPremium * dbDisRate / 100 * dbSelfRate/100;
        dbDisFee = Str.round(Str.round(dbDisFee, 8), 2);
        dbSumDisFee = Str.round(dbSumDisFee+dbDisFee,2);

        if(!prpMiddleCostSchema.getClassCode().trim().equals("05") && i==(blPrpPitemKind.getSize()-1)
           && dbSumDisFee != Double.parseDouble(Str.chgStrZero(prpMiddleCostSchema.getDisFee())))
        {
          dbDisFee = Str.round(dbDisFee + Double.parseDouble(Str.chgStrZero(prpMiddleCostSchema.getDisFee()))- dbSumDisFee,2);
        }
        prpMiddleCostDetailSchema.setDisRate(""+dbDisRate);
        prpMiddleCostDetailSchema.setDisFee(""+dbDisFee);
        this.setArr(prpMiddleCostDetailSchema);
      }
      if(prpMiddleCostSchema.getClassCode().trim().equals("05"))
      {
        prpMiddleCostSchema.setDisFee(""+dbSumDisFee);
      }
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
