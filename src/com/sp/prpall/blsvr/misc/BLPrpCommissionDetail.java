package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.blsvr.cb.BLPrpCfee;
import com.sp.prpall.blsvr.cb.BLPrpCitemKind;
import com.sp.prpall.blsvr.pg.BLPrpPfee;
import com.sp.prpall.blsvr.pg.BLPrpPitemKind;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.misc.DBPrpCommissionDetail;
import com.sp.prpall.dbsvr.pg.DBPrpPmain;
import com.sp.prpall.schema.PrpCitemKindSchema;
import com.sp.prpall.schema.PrpCommissionDetailSchema;
import com.sp.prpall.schema.PrpCommissionSchema;
import com.sp.prpall.schema.PrpMiddleCostDetailSchema;
import com.sp.prpall.schema.PrpPitemKindSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.Str;

/**
 * 定义PrpCommissionDetail-手续费明细表的BL类
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-01-19</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpCommissionDetail{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */
    public BLPrpCommissionDetail(){
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
     *增加一条PrpCommissionDetailSchema记录
     *@param iPrpCommissionDetailSchema PrpCommissionDetailSchema
     *@throws Exception
     */
    public void setArr(PrpCommissionDetailSchema iPrpCommissionDetailSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpCommissionDetailSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpCommissionDetailSchema记录
     *@param index 下标
     *@return 一个PrpCommissionDetailSchema对象
     *@throws Exception
     */
    public PrpCommissionDetailSchema getArr(int index) throws Exception
    {
     PrpCommissionDetailSchema prpCommissionDetailSchema = null;
       try
       {
        prpCommissionDetailSchema = (PrpCommissionDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpCommissionDetailSchema;
     }
    /**
    *增加手续费明细的getdata方法
    * @param dbpool
    * @param certiNo
    * @throws Exception
    */
    public void getData(DbPool dbpool, String certiNo) throws Exception {
        
        
        
        
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
      DBPrpCommissionDetail dbPrpCommissionDetail = new DBPrpCommissionDetail();
      if (iLimitCount > 0 && dbPrpCommissionDetail.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
      {
          throw new UserException(-98,-1003,"BLPrpCommissionDetail.query");
      }else
      {
          initArr();
          strSqlStatement = " SELECT * FROM PrpCommissionDetail WHERE " + iWherePart;
          schemas = dbPrpCommissionDetail.findByConditions(dbpool,strSqlStatement,iWhereValue);
      }
  }
    /**
     *删除一条PrpCommissionDetailSchema记录
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
      DBPrpCommissionDetail dbPrpCommissionDetail = new DBPrpCommissionDetail();
      if (iLimitCount > 0 && dbPrpCommissionDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCommissionDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCommissionDetail WHERE " + iWherePart;
        schemas = dbPrpCommissionDetail.findByConditions(strSqlStatement);
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
      DBPrpCommissionDetail dbPrpCommissionDetail = new DBPrpCommissionDetail();
      if (iLimitCount > 0 && dbPrpCommissionDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCommissionDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCommissionDetail WHERE " + iWherePart;
        schemas = dbPrpCommissionDetail.findByConditions(dbpool,strSqlStatement);
      }
    }
    /**
     *手续费明细表按标的序号累积求和，用于计算批改时当前XX的手续费金额
     *@param iWherePart
     *@return 无
     */
    public void sumByItemKindNo(String iWherePart) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpCommissionDetail dbPrpCommissionDetail = new DBPrpCommissionDetail();
      initArr();
      strSqlStatement = " SELECT SUM(DISFEE) AS DISFEE,CURRENCY,ITEMKINDNO  FROM PrpCommissionDetail WHERE " + iWherePart+" GROUP BY CURRENCY,ITEMKINDNO ORDER BY ITEMKINDNO";
      schemas = dbPrpCommissionDetail.sumByCurrency(strSqlStatement);
    }
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpCommissionDetail dbPrpCommissionDetail = new DBPrpCommissionDetail();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpCommissionDetail.setSchema((PrpCommissionDetailSchema)schemas.get(i));
      dbPrpCommissionDetail.insert(dbpool);
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
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param certiNo 批单号
     *@return 无
     */
    public void cancel(DbPool dbpool,String certiNo) throws Exception
    {
      DBPrpCommissionDetail dbPrpCommissionDetail = new DBPrpCommissionDetail();
      dbPrpCommissionDetail.delete(dbpool,certiNo);
    }
    /**
     * @desc 核XXXXX通过后自动生成一张手续费明细
     * @throws UserException
     * @throws Exception
     */
    public void createCommissionCDetail(DbPool dbpool, BLPrpCommission blPrpCommission,DBPrpCmain dbPrpCmain)
    throws UserException,Exception {

      double  dbSelfRate = 0;
      double  dbDisRate  = 0;
      double  dbDisRate1 = 0;
      double  dbDisFee   = 0;
      double  dbDisFee1   = 0;
      double  dbExchangeRate = 0;
      double  dbSumDisFee= 0;
      double  dbPremium  = 0;

      BLPrpCitemKind            blPrpCitemKind            = new BLPrpCitemKind();
      BLPrpCfee                 blPrpCfee                 = new BLPrpCfee();
      BLPrpMiddleCostDetail     blPrpMiddleCostDetail     = new BLPrpMiddleCostDetail();

      PrpCitemKindSchema        prpCitemKindSchema        = new PrpCitemKindSchema();
      PrpCommissionSchema       prpCommissionSchema       = new PrpCommissionSchema();
      PrpMiddleCostDetailSchema prpMiddleCostDetailSchema = new PrpMiddleCostDetailSchema();
      PrpCommissionDetailSchema prpCommissionDetailSchema = new PrpCommissionDetailSchema();
      if(blPrpCommission.getSize()<1 || dbPrpCmain.getPolicyNo().equals("") )
        return;

     prpCommissionSchema = blPrpCommission.getArr(0);
     blPrpCitemKind.query(dbpool,"PolicyNo = '"+ dbPrpCmain.getPolicyNo() +"'",0);
     blPrpCfee.query(dbpool,"PolicyNo = '"+ dbPrpCmain.getPolicyNo() +"'",0);
     blPrpMiddleCostDetail.query(dbpool,"CertiNo = '"+ dbPrpCmain.getPolicyNo() +"'",0);

     if(blPrpCitemKind.getSize()<1 || blPrpCfee.getSize()<1 )
       return;

     dbSelfRate = Double.parseDouble(Str.chgStrZero(prpCommissionSchema.getSelfRate()));
     if(dbSelfRate==0)
       dbSelfRate = 100;

     for(int i=0;i<blPrpCitemKind.getSize();i++)
     {

        prpCitemKindSchema = blPrpCitemKind.getArr(i);
        prpCommissionDetailSchema = new PrpCommissionDetailSchema();

        prpCommissionDetailSchema.setCertiNo(prpCommissionSchema.getCertiNo());
        prpCommissionDetailSchema.setCertiType(prpCommissionSchema.getCertiType());
        prpCommissionDetailSchema.setRiskCode(prpCommissionSchema.getRiskCode());
        prpCommissionDetailSchema.setPolicyNo(prpCommissionSchema.getPolicyNo());
        prpCommissionDetailSchema.setCurrency(prpCommissionSchema.getCurrency());
        prpCommissionDetailSchema.setItemKindNo(prpCitemKindSchema.getItemKindNo());
        prpCommissionDetailSchema.setKindCode(prpCitemKindSchema.getKindCode());
        prpCommissionDetailSchema.setItemCode(prpCitemKindSchema.getItemCode());

        if(prpCitemKindSchema.getFlag().length()>=6&&
           prpCitemKindSchema.getFlag().substring(5,6).equals("1"))
        {
           dbDisRate = Double.parseDouble(Str.chgStrZero(dbPrpCmain.getPureRate()));
        }
        else
        {
          dbDisRate = Double.parseDouble(Str.chgStrZero(dbPrpCmain.getDisRate()));
        }

        if(prpCitemKindSchema.getCurrency().trim().equals(prpCommissionSchema.getCurrency().trim()))
        {
           dbPremium = Double.parseDouble(Str.chgStrZero(prpCitemKindSchema.getPremium()));
        }
        else
        {
          for(int j=0;j<blPrpCfee.getSize();j++)
          {
            dbExchangeRate = 0;
            if(blPrpCfee.getArr(j).getCurrency().trim().equals(prpCitemKindSchema.getCurrency().trim())
               && blPrpCfee.getArr(j).getCurrency1().trim().equals(prpCommissionSchema.getCurrency().trim()))
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

        dbDisFee1 = 0;
        for(int n=0;n<blPrpMiddleCostDetail.getSize();n++)
        {
        	prpMiddleCostDetailSchema = blPrpMiddleCostDetail.getArr(n);
        	if(prpMiddleCostDetailSchema.getItemKindNo().trim().equals(prpCitemKindSchema.getItemKindNo().trim()))
          {
            dbDisFee1 = Double.parseDouble(Str.chgStrZero(prpMiddleCostDetailSchema.getDisFee()));
            break;
          }
        }

        dbDisFee = (dbPremium - dbDisFee1) * dbDisRate / 100 * dbSelfRate/100;
        dbDisFee = Str.round(Str.round(dbDisFee, 8), 2);
        dbSumDisFee = Str.round(dbSumDisFee+dbDisFee,2);
        if(!prpCommissionSchema.getClassCode().trim().equals("05") && i==(blPrpCitemKind.getSize()-1)
           && dbSumDisFee != Double.parseDouble(Str.chgStrZero(prpCommissionSchema.getDisFee())))
        {
          dbDisFee = Str.round(dbDisFee + Double.parseDouble(Str.chgStrZero(prpCommissionSchema.getDisFee()))- dbSumDisFee,2);
        }

        prpCommissionDetailSchema.setDisRate(""+dbDisRate);
        prpCommissionDetailSchema.setDisFee(""+dbDisFee);
        this.setArr(prpCommissionDetailSchema);
      }
      if(prpCommissionSchema.getClassCode().trim().equals("05"))
      {
        prpCommissionSchema.setDisFee(""+dbSumDisFee);
      }

    }
    
    
    /**
     * @desc 针对3006XXXXX种核XXXXX通过后自动生成一张手续费明细
     * @throws UserException
     * @throws Exception
     */
    public void createCommissionCDetailFor3006(DbPool dbpool, PrpCommissionSchema prpCommissionSchema,DBPrpCmain dbPrpCmain)
    throws UserException,Exception {

      double  dbSelfRate = 0;
      double  dbDisRate  = 0;
      double  dbDisRate1 = 0;
      double  dbDisFee   = 0;
      double  dbDisFee1   = 0;
      double  dbExchangeRate = 0;
      double  dbSumDisFee= 0;
      double  dbPremium  = 0;

      BLPrpCitemKind            blPrpCitemKind            = new BLPrpCitemKind();
      BLPrpCfee                 blPrpCfee                 = new BLPrpCfee();
      BLPrpMiddleCostDetail     blPrpMiddleCostDetail     = new BLPrpMiddleCostDetail();

      PrpCitemKindSchema        prpCitemKindSchema        = new PrpCitemKindSchema();
      PrpMiddleCostDetailSchema prpMiddleCostDetailSchema = new PrpMiddleCostDetailSchema();
      PrpCommissionDetailSchema prpCommissionDetailSchema = new PrpCommissionDetailSchema();
      if(prpCommissionSchema == null || dbPrpCmain.getPolicyNo().equals("") )
        return;
     blPrpCitemKind.query(dbpool,"PolicyNo = '"+ dbPrpCmain.getPolicyNo() +"'",0);
     blPrpCfee.query(dbpool,"PolicyNo = '"+ dbPrpCmain.getPolicyNo() +"'",0);
     blPrpMiddleCostDetail.query(dbpool,"CertiNo = '"+ dbPrpCmain.getPolicyNo() +"'",0);
     if(blPrpCitemKind.getSize()<1 || blPrpCfee.getSize()<1 )
       return;

     dbSelfRate = Double.parseDouble(Str.chgStrZero(prpCommissionSchema.getSelfRate()));
     if(dbSelfRate==0)
       dbSelfRate = 100;
     
     for(int i=0;i<blPrpCitemKind.getSize();i++)
     {
    	
        prpCitemKindSchema = blPrpCitemKind.getArr(i);
        prpCommissionDetailSchema = new PrpCommissionDetailSchema();

        prpCommissionDetailSchema.setCertiNo(prpCommissionSchema.getCertiNo());
        prpCommissionDetailSchema.setCertiType(prpCommissionSchema.getCertiType());
        prpCommissionDetailSchema.setRiskCode(prpCommissionSchema.getRiskCode());
        prpCommissionDetailSchema.setPolicyNo(prpCommissionSchema.getPolicyNo());
        prpCommissionDetailSchema.setCurrency(prpCommissionSchema.getCurrency());
        prpCommissionDetailSchema.setItemKindNo(prpCitemKindSchema.getItemKindNo());
        prpCommissionDetailSchema.setKindCode(prpCitemKindSchema.getKindCode());
        prpCommissionDetailSchema.setItemCode(prpCitemKindSchema.getItemCode());

        if(prpCitemKindSchema.getFlag().length()>=6&&
           prpCitemKindSchema.getFlag().substring(5,6).equals("1"))
        {
           dbDisRate = Double.parseDouble(Str.chgStrZero(dbPrpCmain.getPureRate()));
        }
        else
        {
          dbDisRate = Double.parseDouble(Str.chgStrZero(dbPrpCmain.getDisRate()));
        }

        if(prpCitemKindSchema.getCurrency().trim().equals(prpCommissionSchema.getCurrency().trim()))
        {
           dbPremium = Double.parseDouble(Str.chgStrZero(prpCitemKindSchema.getPremium()));
        }
        else
        {
          for(int j=0;j<blPrpCfee.getSize();j++)
          {
            dbExchangeRate = 0;
            if(blPrpCfee.getArr(j).getCurrency().trim().equals(prpCitemKindSchema.getCurrency().trim())
               && blPrpCfee.getArr(j).getCurrency1().trim().equals(prpCommissionSchema.getCurrency().trim()))
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

        dbDisFee1 = 0;
        for(int n=0;n<blPrpMiddleCostDetail.getSize();n++)
        {
        	prpMiddleCostDetailSchema = blPrpMiddleCostDetail.getArr(n);
        	if(prpMiddleCostDetailSchema.getItemKindNo().trim().equals(prpCitemKindSchema.getItemKindNo().trim()))
          {
            dbDisFee1 = Double.parseDouble(Str.chgStrZero(prpMiddleCostDetailSchema.getDisFee()));
            break;
          }
        }

        dbDisFee = (dbPremium - dbDisFee1) * dbDisRate / 100 * dbSelfRate/100;
        dbDisFee = Str.round(Str.round(dbDisFee, 8), 2);
        dbSumDisFee = Str.round(dbSumDisFee+dbDisFee,2);
        if(!prpCommissionSchema.getClassCode().trim().equals("05") && i==(blPrpCitemKind.getSize()-1)
           && dbSumDisFee != Double.parseDouble(Str.chgStrZero(prpCommissionSchema.getDisFee())))
        {
          dbDisFee = Str.round(dbDisFee + Double.parseDouble(Str.chgStrZero(prpCommissionSchema.getDisFee()))- dbSumDisFee,2);
        }

        prpCommissionDetailSchema.setDisRate(""+dbDisRate);
        prpCommissionDetailSchema.setDisFee(""+dbDisFee);
        this.setArr(prpCommissionDetailSchema);
      }
    }
    
    
    /**
     * @desc 批改手续费后生成明细
     * @throws UserException
     * @throws Exception
     */
    public void createCommissionPDetail(BLPrpCommission blPrpCommission,String strPureRate,BLPrpMiddleCostDetail blPrpMiddleCostDetail_cur)
    throws UserException,Exception {

        double  dbSelfRate = 0;
        double  dbDisRate  = 0;
        double  dbDisRate1 = 0;
        double  dbDisFee   = 0;
        double  dbDisFee1   = 0;
        double  dbDisFee1_cur   = 0;
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
        PrpCommissionSchema       prpCommissionSchema       = new PrpCommissionSchema();
        PrpMiddleCostDetailSchema prpMiddleCostDetailSchema = new PrpMiddleCostDetailSchema();
        PrpMiddleCostDetailSchema prpMiddleCostDetailSchema1 = new PrpMiddleCostDetailSchema();
        PrpCommissionDetailSchema prpCommissionDetailSchema = new PrpCommissionDetailSchema();
        PrpCommissionDetailSchema prpCommissionDetailSchema1 = new PrpCommissionDetailSchema();
        prpCommissionSchema = blPrpCommission.getArr(0);
        BLPrpCommissionDetail blPrpCommissionDetail = new BLPrpCommissionDetail();
        dbPrpPmain.getInfo(prpCommissionSchema.getCertiNo());
        dbPrpCmain.getInfo(prpCommissionSchema.getPolicyNo());

        if(blPrpCommission.getSize()<1 || dbPrpCmain.getPolicyNo().equals("") )
          return;
        
       
       blPrpCitemKind.query("PolicyNo = '"+ dbPrpCmain.getPolicyNo() +"'",0);
       
       blPrpCfee.query("PolicyNo = '"+ dbPrpCmain.getPolicyNo() +"'",0);
       
       blPrpMiddleCostDetail.sumByItemKindNo("PolicyNo = '"+ dbPrpCmain.getPolicyNo() +"'");
       
       
       blPrpCommissionDetail.sumByItemKindNo(" POLICYNO = '"+ dbPrpCmain.getPolicyNo() +"'");
       
       if(blPrpCitemKind.getSize()<1 || blPrpCfee.getSize()<1 )
         return;

       dbSelfRate = Double.parseDouble(Str.chgStrZero(prpCommissionSchema.getSelfRate()));
       if(dbSelfRate==0)
         dbSelfRate = 100;

       for(int i=0;i<blPrpCitemKind.getSize();i++)
       {

          prpCitemKindSchema = blPrpCitemKind.getArr(i);
          prpCommissionDetailSchema = new PrpCommissionDetailSchema();

          prpCommissionDetailSchema.setCertiNo(prpCommissionSchema.getCertiNo());
          prpCommissionDetailSchema.setCertiType(prpCommissionSchema.getCertiType());
          prpCommissionDetailSchema.setRiskCode(prpCommissionSchema.getRiskCode());
          prpCommissionDetailSchema.setPolicyNo(prpCommissionSchema.getPolicyNo());
          prpCommissionDetailSchema.setCurrency(prpCommissionSchema.getCurrency());
          prpCommissionDetailSchema.setItemKindNo(prpCitemKindSchema.getItemKindNo());
          prpCommissionDetailSchema.setKindCode(prpCitemKindSchema.getKindCode());
          prpCommissionDetailSchema.setItemCode(prpCitemKindSchema.getItemCode());

          if(prpCitemKindSchema.getFlag().length()>=6&&
             prpCitemKindSchema.getFlag().substring(5,6).equals("1"))
          {
             dbDisRate = Double.parseDouble(Str.chgStrZero(strPureRate));
          }
          else
          {
            dbDisRate = Double.parseDouble(Str.chgStrZero(prpCommissionSchema.getDisRate())); 
          }

          if(prpCitemKindSchema.getCurrency().trim().equals(prpCommissionSchema.getCurrency().trim()))
          {
             dbPremium = Double.parseDouble(Str.chgStrZero(prpCitemKindSchema.getPremium()));
          }
          else
          {
            for(int j=0;j<blPrpCfee.getSize();j++)
            {
              dbExchangeRate = 0;
              if(blPrpCfee.getArr(j).getCurrency().trim().equals(prpCitemKindSchema.getCurrency().trim())
                 && blPrpCfee.getArr(j).getCurrency1().trim().equals(prpCommissionSchema.getCurrency().trim()))
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
          
          dbDisFee1 = 0;
          dbDisFee1_cur = 0;
          dbDisFee2 = 0;
          
          for(int n=0;n<blPrpMiddleCostDetail.getSize();n++)
          {
          	prpMiddleCostDetailSchema = blPrpMiddleCostDetail.getArr(n);
          	if(prpMiddleCostDetailSchema.getItemKindNo().trim().equals(prpCitemKindSchema.getItemKindNo().trim()))
            {
              dbDisFee1 = Double.parseDouble(Str.chgStrZero(prpMiddleCostDetailSchema.getDisFee()));
              break;
            }
          }
          
          for(int m=0;m<blPrpMiddleCostDetail_cur.getSize();m++)
          {
          	prpMiddleCostDetailSchema1 = blPrpMiddleCostDetail_cur.getArr(m);
          	if(prpMiddleCostDetailSchema1.getItemKindNo().trim().equals(prpCitemKindSchema.getItemKindNo().trim()))
            {
              dbDisFee1_cur = Double.parseDouble(Str.chgStrZero(prpMiddleCostDetailSchema1.getDisFee()));
              break;
            }
          }
          
          for(int n=0;n<blPrpCommissionDetail.getSize();n++)
          {
          	prpCommissionDetailSchema1 = blPrpCommissionDetail.getArr(n);
          	if(prpCommissionDetailSchema1.getItemKindNo().trim().equals(prpCitemKindSchema.getItemKindNo().trim()))
            {
              dbDisFee2 = Double.parseDouble(Str.chgStrZero(prpCommissionDetailSchema1.getDisFee()));
              break;
            }
          }
          
          dbDisFee = (dbPremium - dbDisFee1 - dbDisFee1_cur) * dbDisRate / 100 * dbSelfRate/100 - dbDisFee2;
          dbDisFee = Str.round(Str.round(dbDisFee, 8), 2);
          dbSumDisFee = Str.round(dbSumDisFee+dbDisFee,2);
                    
          if(!prpCommissionSchema.getClassCode().trim().equals("05") && i==(blPrpCitemKind.getSize()-1)
             && dbSumDisFee != Double.parseDouble(Str.chgStrZero(prpCommissionSchema.getDisFee())))
          {
            dbDisFee = Str.round(dbDisFee + Double.parseDouble(Str.chgStrZero(prpCommissionSchema.getDisFee()))- dbSumDisFee,2);
          }

          prpCommissionDetailSchema.setDisRate(""+dbDisRate);
          prpCommissionDetailSchema.setDisFee(""+dbDisFee);
          this.setArr(prpCommissionDetailSchema);
        }
        if(prpCommissionSchema.getClassCode().trim().equals("05"))
        {
          prpCommissionSchema.setDisFee(""+dbSumDisFee);
        }
    }

    /**
     * @desc 核批通过后自动生成一张手续费明细
     * @throws UserException
     * @throws Exception
     */
    public void createCommissionPDetail(DbPool dbpool, BLPrpCommission blPrpCommission,DBPrpPmain dbPrpPmain)
    throws UserException,Exception {

      double  dbSelfRate = 0;
      double  dbDisRate  = 0;
      double  dbDisRate1 = 0;
      double  dbDisFee   = 0;
      double  dbDisFee1   = 0;
      double  dbExchangeRate = 0;
      double  dbSumDisFee= 0;
      double  dbPremium  = 0;

      BLPrpPitemKind            blPrpPitemKind            = new BLPrpPitemKind();
      BLPrpPfee                 blPrpPfee                 = new BLPrpPfee();
      BLPrpMiddleCostDetail     blPrpMiddleCostDetail     = new BLPrpMiddleCostDetail();

      PrpPitemKindSchema        prpPitemKindSchema        = new PrpPitemKindSchema();
      PrpCommissionSchema       prpCommissionSchema       = new PrpCommissionSchema();
      PrpMiddleCostDetailSchema prpMiddleCostDetailSchema = new PrpMiddleCostDetailSchema();
      PrpCommissionDetailSchema prpCommissionDetailSchema = new PrpCommissionDetailSchema();
      if(blPrpCommission.getSize()<1 || dbPrpPmain.getPolicyNo().equals("") )
        return;

     prpCommissionSchema = blPrpCommission.getArr(0);
     blPrpPitemKind.query(dbpool,"EndorseNo = '"+ dbPrpPmain.getEndorseNo() +"'",0);
     blPrpPfee.query(dbpool,"EndorseNo = '"+ dbPrpPmain.getEndorseNo() +"'",0);
     blPrpMiddleCostDetail.query(dbpool,"CertiNo = '"+ dbPrpPmain.getEndorseNo() +"'",0);

     if(blPrpPitemKind.getSize()<1 || blPrpPfee.getSize()<1 )
       return;

     dbSelfRate = Double.parseDouble(Str.chgStrZero(prpCommissionSchema.getSelfRate()));
     if(dbSelfRate==0)
       dbSelfRate = 100;

     for(int i=0;i<blPrpPitemKind.getSize();i++)
     {

        prpPitemKindSchema = blPrpPitemKind.getArr(i);
        prpCommissionDetailSchema = new PrpCommissionDetailSchema();

        prpCommissionDetailSchema.setCertiNo(prpCommissionSchema.getCertiNo());
        prpCommissionDetailSchema.setCertiType(prpCommissionSchema.getCertiType());
        prpCommissionDetailSchema.setRiskCode(prpCommissionSchema.getRiskCode());
        prpCommissionDetailSchema.setPolicyNo(prpCommissionSchema.getPolicyNo());
        prpCommissionDetailSchema.setCurrency(prpCommissionSchema.getCurrency());
        prpCommissionDetailSchema.setItemKindNo(prpPitemKindSchema.getItemKindNo());
        prpCommissionDetailSchema.setKindCode(prpPitemKindSchema.getKindCode());
        prpCommissionDetailSchema.setItemCode(prpPitemKindSchema.getItemCode());

        if(prpPitemKindSchema.getFlag().length()>=6&&
           prpPitemKindSchema.getFlag().substring(5,6).equals("1"))
        {
           dbDisRate = Double.parseDouble(Str.chgStrZero(dbPrpPmain.getPureRate()));
        }
        else
        {
          dbDisRate = Double.parseDouble(Str.chgStrZero(dbPrpPmain.getDisRate()));
        }

        if(prpPitemKindSchema.getCurrency().trim().equals(prpCommissionSchema.getCurrency().trim()))
        {
           dbPremium = Double.parseDouble(Str.chgStrZero(prpPitemKindSchema.getChgPremium()));
        }
        else
        {
          for(int j=0;j<blPrpPfee.getSize();j++)
          {
            dbExchangeRate = 0;
            if(blPrpPfee.getArr(j).getCurrency().trim().equals(prpPitemKindSchema.getCurrency().trim())
               && blPrpPfee.getArr(j).getCurrency1().trim().equals(prpCommissionSchema.getCurrency().trim()))
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

        dbDisFee1 = 0;
        for(int n=0;n<blPrpMiddleCostDetail.getSize();n++)
        {
        	prpMiddleCostDetailSchema = blPrpMiddleCostDetail.getArr(n);
        	if(prpMiddleCostDetailSchema.getItemKindNo().trim().equals(prpPitemKindSchema.getItemKindNo().trim()))
          {
            dbDisFee1 = Double.parseDouble(Str.chgStrZero(prpMiddleCostDetailSchema.getDisFee()));
            break;
          }
        }

        dbDisFee = (dbPremium - dbDisFee1) * dbDisRate / 100 * dbSelfRate/100;
        dbDisFee = Str.round(Str.round(dbDisFee, 8), 2);
        dbSumDisFee = Str.round(dbSumDisFee+dbDisFee,2);
        if(!prpCommissionSchema.getClassCode().trim().equals("05") && i==(blPrpPitemKind.getSize()-1)
           && dbSumDisFee != Double.parseDouble(Str.chgStrZero(prpCommissionSchema.getDisFee())))
        {
          dbDisFee = Str.round(dbDisFee + Double.parseDouble(Str.chgStrZero(prpCommissionSchema.getDisFee()))- dbSumDisFee,2);
        }

        prpCommissionDetailSchema.setDisRate(""+dbDisRate);
        prpCommissionDetailSchema.setDisFee(""+dbDisFee);
        this.setArr(prpCommissionDetailSchema);
      }
      if(prpCommissionSchema.getClassCode().trim().equals("05"))
      {
        prpCommissionSchema.setDisFee(""+dbSumDisFee);
      }

    }

    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
