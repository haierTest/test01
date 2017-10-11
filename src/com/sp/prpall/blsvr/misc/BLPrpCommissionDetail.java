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
 * ����PrpCommissionDetail-��������ϸ���BL��
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-01-19</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpCommissionDetail{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */
    public BLPrpCommissionDetail(){
    }

    /**
     *��ʼ����¼
     *@param ��
     *@return ��
     *@throws Exception
     */
    public void initArr() throws Exception
    {
       schemas = new Vector();
     }
    /**
     *����һ��PrpCommissionDetailSchema��¼
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
     *�õ�һ��PrpCommissionDetailSchema��¼
     *@param index �±�
     *@return һ��PrpCommissionDetailSchema����
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
    *������������ϸ��getdata����
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
   *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
   *@author wangchuanzhong 20100602
   *@param dbpool      ȫ�ֳ�
   *@param iWherePart  ��ѯ����,�����������Ѱ󶨱�����ʽ���ʺŸ���������ֵ����һ��
   *@param iWhereValue ��ѯ�������ֶ�ֵ
   *@param iLimitCount ��¼������(iLimitCount=0: ������)
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
     *ɾ��һ��PrpCommissionDetailSchema��¼
     *@param index �±�
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
     *�õ�schemas��¼��
     *@return schemas��¼��
     *@throws Exception
     */
    public int getSize() throws Exception
    {
        return this.schemas.size();
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart) throws UserException,Exception
    {
       this.query(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
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
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
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
     *��������ϸ���������ۻ���ͣ����ڼ�������ʱ��ǰXX�������ѽ��
     *@param iWherePart
     *@return ��
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
     *��dbpool��save����
     *@param ��
     *@return ��
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
     *����dbpool��XXXXX�淽��
     *@param ��
     *@return ��
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
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param certiNo ������
     *@return ��
     */
    public void cancel(DbPool dbpool,String certiNo) throws Exception
    {
      DBPrpCommissionDetail dbPrpCommissionDetail = new DBPrpCommissionDetail();
      dbPrpCommissionDetail.delete(dbpool,certiNo);
    }
    /**
     * @desc ��XXXXXͨ�����Զ�����һ����������ϸ
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
     * @desc ���3006XXXXX�ֺ�XXXXXͨ�����Զ�����һ����������ϸ
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
     * @desc ���������Ѻ�������ϸ
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
     * @desc ����ͨ�����Զ�����һ����������ϸ
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
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
