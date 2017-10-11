package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import java.text.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.*;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRec;
import com.sp.prpall.schema.PrpJpayRecSchema;
import com.sp.prpall.pubfun.Bill;
import com.sp.account.interf.*;
import com.sp.prpall.blsvr.cb.BLPrpCplan;
import com.sp.prpall.dbsvr.cb.DBPrpCplan;
import com.sp.prpall.blsvr.cb.BLPrpCcoins;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;
import com.sp.account.dbsvr.DBAccRawVoucher;
import com.sp.prpall.dbsvr.jf.DBPrpJrefRec;

/**
 * 定义PrpJpayRec的BL类
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-07-24</p>
 * @Author     : X
 * @version 1.0
 */
public class BLPrpJpayRec{
    private Vector schemas = new Vector();
    private Vector schemasTmp = new Vector();
    /**
     * 构造函数
     */
    public BLPrpJpayRec(){
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
       schemasTmp = new Vector();
     }
    /**
     *增加一条PrpJpayRecSchema记录
     *@param iPrpJpayRecSchema PrpJpayRecSchema
     *@throws Exception
     */
    public void setArr(PrpJpayRecSchema iPrpJpayRecSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJpayRecSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }

    /**
     *增加一条PrpJpayRecSchema记录
     *@param iPrpJpayRecSchema PrpJpayRecSchema
     *@throws Exception
     */
    public void setArr(PrpJpayRecSchema iPrpJpayRecSchema,int index) throws Exception
    {
       try
       {
         schemas.setElementAt(iPrpJpayRecSchema,index);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpJpayRecSchema记录
     *@param index 下标
     *@return 一个PrpJpayRecSchema对象
     *@throws Exception
     */
    public PrpJpayRecSchema getArr(int index) throws Exception
    {
     PrpJpayRecSchema prpJpayRecSchema = null;
       try
       {
        prpJpayRecSchema = (PrpJpayRecSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJpayRecSchema;
     }
    /**
     *删除一条PrpJpayRecSchema记录
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
      DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();
      if (iLimitCount > 0 && dbPrpJpayRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRec WHERE " + iWherePart;
        schemas = dbPrpJpayRec.findByConditions(strSqlStatement);
      }
    }
    
    
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象,从历史信息库查询
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart) throws UserException,Exception
    {
       this.queryHistory(iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象，从历史信息库查询
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();
      if (iLimitCount > 0 && dbPrpJpayRec.getCountHistory(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRec WHERE " + iWherePart;
        schemas = dbPrpJpayRec.findByConditionsHistory(strSqlStatement);
      }
    }
        
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象。先从生产库获取相关信息，如果未取到，再从历史信息库获取。
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart) throws UserException,Exception
    {
       
       query(iWherePart);
       
       if(this.getSize() == 0){
    	   queryHistory(iWherePart);
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
      DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();
      if (iLimitCount > 0 && dbPrpJpayRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRec WHERE " + iWherePart;
        schemas = dbPrpJpayRec.findByConditions(dbpool,strSqlStatement);
      }
    }

    public void query(DbPool dbpool, String iWherePart, int intPageNum,
                      int intPageCount) throws UserException, Exception {
      String strSqlStatement = "";
      int intStartNum = 0;
      int intEndNum = 0;

      intStartNum = (intPageNum - 1) * intPageCount;
      intEndNum = intPageNum * intPageCount;

      DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();
      initArr();
      /*
           strSqlStatement = " SELECT * FROM PrpJpayRec WHERE RowId In ( " +
         "Select rid From (Select T.RowId rid,RowNum As LineNum From (" +
         "Select RowId From PrpJpayRec Where " + iWherePart +
         ") T Where RowNum<=" + intEndNum + ") Where LineNum>" + intStartNum +
         ") Order By RegistNo DESC";
       */
      strSqlStatement = " SELECT * FROM ( " +
          "Select RowNum As LineNum,T.* From ( " +
          "Select * From PrpJpayRec Where " + iWherePart +
          ") T Where RowNum<=" + intEndNum + ") Where LineNum>" +
          intStartNum + " Order By ReceiptNo DESC";

      schemas = dbPrpJpayRec.findByConditions(dbpool, strSqlStatement);
    }

    public void query(String iWherePart, int intPageNum, int intPageCount) throws
        UserException, Exception {
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        this.query(dbpool, iWherePart, intPageNum, intPageCount);
      }
      catch (Exception e) {
        throw e;
      }
      finally {
        dbpool.close();
      }
    }


    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryPrpJpayRec(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      boolean blFlag = false;
      DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();
      PrpJpayRecSchema prpJpayRecSchema = new PrpJpayRecSchema();
      PrpJpayRecSchema prpJpayRecSchemaTmp = new PrpJpayRecSchema();
      if (iLimitCount > 0 && dbPrpJpayRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRec WHERE " + iWherePart;
        schemasTmp = dbPrpJpayRec.findByConditions(strSqlStatement);
      }
      for(int i=0;i<schemasTmp.size();i++)
      {
        prpJpayRecSchema = new PrpJpayRecSchema();
        prpJpayRecSchema = (PrpJpayRecSchema)schemasTmp.get(i);
        if(this.getSize()>0)
        {
          blFlag = false;
          for (int j = 0; j < this.getSize(); j++) {
            prpJpayRecSchemaTmp = new PrpJpayRecSchema();
            prpJpayRecSchemaTmp = this.getArr(j);
           if(prpJpayRecSchema.getContractNo().trim().equals(prpJpayRecSchemaTmp.getContractNo().trim()))
            {
               prpJpayRecSchemaTmp.setPayFee("" +
                  (Double.parseDouble(prpJpayRecSchemaTmp.getPayFee())+
                  Double.parseDouble(prpJpayRecSchema.getPayFee())));
              prpJpayRecSchemaTmp.setRealPayFee("" +
                  (Double.parseDouble(prpJpayRecSchemaTmp.getRealPayFee())+
                  Double.parseDouble(prpJpayRecSchema.getRealPayFee())));
              schemas.setElementAt(prpJpayRecSchemaTmp,j);
              blFlag = true;
              break;
            }
          }
          if(!blFlag)
          {
            schemas.add(prpJpayRecSchema);
          }
        }
        else
        {
          schemas.add(prpJpayRecSchema);
        }
      }
    }

    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     * 如果应收币别和实收币别不一致则将应收币别置为实收币别，将实收币别置空，将折合应收币别的金额作为应收金额。
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryPrpJpayRecAddReal(String iWherePart,String strBelongTo,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      boolean blFlag = false;
      DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();
      BLPrpJpayRec blPrpJpayRec = new BLPrpJpayRec();
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      DBPrpCplan dbPrpCplan = new DBPrpCplan();
      BLPrpCcoins blPrpCcoins = null;
      String strWherePart = "";
      double dbRate = 0.0;

      try
      {
      PrpJpayRecSchema prpJpayRecSchema = new PrpJpayRecSchema();
      PrpJpayRecSchema prpJpayRecSchemaTmp = new PrpJpayRecSchema();
      if (iLimitCount > 0 && dbPrpJpayRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRec WHERE " + iWherePart;
        schemasTmp = dbPrpJpayRec.findByConditions(strSqlStatement);
      }

      prpJpayRecSchemaTmp = new PrpJpayRecSchema();
      prpJpayRecSchemaTmp = (PrpJpayRecSchema)schemasTmp.get(0);

      double payFee = 0;
      String Currency = "";
      for(int i=0;i<schemasTmp.size();i++)
      {
        prpJpayRecSchema = (PrpJpayRecSchema)schemasTmp.get(i);
        if(!prpJpayRecSchema.getCurrency2().equals(""))
        {
          double payFeeTemp = Double.parseDouble(prpJpayRecSchema.getPayFee()) *
              Double.parseDouble(prpJpayRecSchema.getExchangeRate());
          
          
          dbPrpCmain = new DBPrpCmain();
          dbPrpCmain.getInfo(prpJpayRecSchema.getPolicyNo());
          if((dbPrpCmain.getCoinsFlag().trim().equals("1")||dbPrpCmain.getCoinsFlag().trim().equals("3"))&&strBelongTo!=null&&strBelongTo.equals("ALL"))
          {
            dbRate = 0;
            blPrpCcoins = new BLPrpCcoins();
            strWherePart = " CoinsType='1' And PolicyNo='" +
                dbPrpCmain.getPolicyNo().trim() + "'";
            blPrpCcoins.query(strWherePart, 0);
            if (blPrpCcoins.getSize() <= 0) {
              throw new UserException( -98, -1003,
                                      "BLPrpJpayRec.queryPrpJpayRecAddReal",
                                      "XX" + dbPrpCmain.getPolicyNo().trim() +
                                      "没有共XXXXX信息，请确认!");
            }
            else
              dbRate = Double.parseDouble(blPrpCcoins.getArr(0).getCoinsRate().
                                          trim()) / 100;

            dbPrpCplan.getInfo(prpJpayRecSchema.getPolicyNo().trim(),
                               prpJpayRecSchema.getSerialNo().trim());
            if(Double.parseDouble(dbPrpCplan.getDelinquentFee().trim()) == 0)
            {
              strWherePart = " SerialNo='" + prpJpayRecSchema.getSerialNo().trim()
                  + "' And CertiNo='" + prpJpayRecSchema.getCertiNo().trim()
                  + "' And SubStr(Flag,1,1)<>'1' Order By InputDate,InputTime DESC";
              blPrpJpayRec.query(strWherePart,0);
              PrpJpayRecSchema prpJpayRecSchema1 = new PrpJpayRecSchema();
              prpJpayRecSchema1 = blPrpJpayRec.getArr(0);
              if(blPrpJpayRec.getSize()==1)
              {
                payFeeTemp = Double.parseDouble(dbPrpCplan.getPlanFee().trim()) *
                    Double.parseDouble(prpJpayRecSchema.getExchangeRate());
              }
              else if(prpJpayRecSchema1.getInputDate().equals(prpJpayRecSchema.getInputDate())&&
                      prpJpayRecSchema1.getInputTime().equals(prpJpayRecSchema.getInputTime()))
              {
                payFeeTemp = Double.parseDouble(dbPrpCplan.getPlanFee().trim());
                for(int j=1;j<blPrpJpayRec.getSize();j++)
                {
                  prpJpayRecSchema1 = blPrpJpayRec.getArr(j);
                  payFeeTemp -= Str.round(Double.parseDouble(prpJpayRecSchema1.getPayFee())/dbRate,2);
                }
                payFeeTemp = Str.round(payFeeTemp *
                    Double.parseDouble(prpJpayRecSchema.getExchangeRate()),2);
              }
              else
              {
                payFeeTemp = Str.round(payFeeTemp / dbRate, 2);
              }
            }
            else
            {
              payFeeTemp = Str.round(payFeeTemp / dbRate, 2);
            }
          }
          
          
          payFeeTemp = Double.parseDouble(new DecimalFormat("0.00").format(payFeeTemp));
          payFee += payFeeTemp;
          Currency = prpJpayRecSchema.getCurrency2();
        }else
        {
          double payFeeTemp = Double.parseDouble(prpJpayRecSchema.getPayFee());
          
          
          dbPrpCmain = new DBPrpCmain();
          dbPrpCmain.getInfo(prpJpayRecSchema.getPolicyNo());
          if ((dbPrpCmain.getCoinsFlag().trim().equals("1")||dbPrpCmain.getCoinsFlag().trim().equals("3")) &&strBelongTo!=null&&
              strBelongTo.equals("ALL")) {
            dbRate = 0;
            blPrpCcoins = new BLPrpCcoins();
            strWherePart = " CoinsType='1' And PolicyNo='" +
                dbPrpCmain.getPolicyNo().trim() + "'";
            blPrpCcoins.query(strWherePart, 0);
            if (blPrpCcoins.getSize() <= 0) {
              throw new UserException( -98, -1003,
                                      "BLPrpJpayRec.queryPrpJpayRecAddReal",
                                      "XX" + dbPrpCmain.getPolicyNo().trim() +
                                      "没有共XXXXX信息，请确认!");
            }
            else
              dbRate = Double.parseDouble(blPrpCcoins.getArr(0).getCoinsRate().
                                          trim()) / 100;

            dbPrpCplan.getInfo(prpJpayRecSchema.getPolicyNo().trim(),
                               prpJpayRecSchema.getSerialNo().trim());
            if (Double.parseDouble(dbPrpCplan.getDelinquentFee().trim()) == 0)
            {
              strWherePart = " SerialNo='" + prpJpayRecSchema.getSerialNo().trim()
                  + "' And CertiNo='" + prpJpayRecSchema.getCertiNo().trim()
                  + "' And SubStr(Flag,1,1)<>'1' ' Order By InputDate,InputTime DESC";
              blPrpJpayRec.query(strWherePart, 0);
              PrpJpayRecSchema prpJpayRecSchema1 = new PrpJpayRecSchema();
              prpJpayRecSchema1 = blPrpJpayRec.getArr(0);
              if (blPrpJpayRec.getSize() == 1) {
                payFeeTemp = Double.parseDouble(dbPrpCplan.getPlanFee().trim());
              }
              else if (prpJpayRecSchema1.getInputDate().equals(prpJpayRecSchema.getInputDate()) &&
                       prpJpayRecSchema1.getInputTime().equals(prpJpayRecSchema.getInputTime()))
              {
                payFeeTemp = Double.parseDouble(dbPrpCplan.getPlanFee().trim());
                for (int j = 1; j < blPrpJpayRec.getSize(); j++) {
                  prpJpayRecSchema1 = blPrpJpayRec.getArr(j);
                  payFeeTemp -= Str.round(Double.parseDouble(prpJpayRecSchema1.getPayFee())/dbRate, 2);
                }
                payFeeTemp = Str.round(payFeeTemp,2);
              }
              else {
                payFeeTemp = Str.round(payFeeTemp / dbRate, 2);
              }
            }
            else {
              payFeeTemp = Str.round(payFeeTemp / dbRate, 2);
            }
          }
          
          
          payFee += payFeeTemp;
          Currency = prpJpayRecSchema.getCurrency1();
        }
      }
      prpJpayRecSchemaTmp.setPayFee(new DecimalFormat("0.00").format(payFee));
      prpJpayRecSchemaTmp.setCurrency1(Currency);
      schemas.add(prpJpayRecSchemaTmp);
      }
      catch(Exception e)
      {
        throw e;
      }
    }


    public void queryPrpJpayRec(String strReceiptNoType,String[] arrReceiptNo,String strBelongTo,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      boolean blFlag = false;
      DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();
      BLPrpJpayRec blPrpJpayRec = new BLPrpJpayRec();
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      DBPrpCplan dbPrpCplan = new DBPrpCplan();
      BLPrpCcoins blPrpCcoins = null;
      String strWherePart = "";
      double dbRate = 0.0;
      boolean bflag = false;

      try
      {
        String iWherePart = "";
        if(strReceiptNoType.equals("S"))
        {
          iWherePart = " ReceiptNo In ('";
        }else if(strReceiptNoType.equals("A"))
        {
          iWherePart = " ContractNo In ('";
        }else
        {
          throw new UserException(-98,-1167,"BLPrpJpayRec.queryPrpJpayRec()","无效的strReceiptNoType");
        }

        for(int i=0; i<arrReceiptNo.length; i++)
        {
          if(i == 0)
          {
            iWherePart += arrReceiptNo[i] + "'";
          }else
          {
            iWherePart += ",'" + arrReceiptNo[i] + "'";
          }
        }
        iWherePart += ") ";

        PrpJpayRecSchema prpJpayRecSchema = new PrpJpayRecSchema();
        PrpJpayRecSchema prpJpayRecSchemaTmp = new PrpJpayRecSchema();
        if (iLimitCount > 0 && dbPrpJpayRec.getCount(iWherePart) > iLimitCount)
        {
          throw new UserException(-98,-1003,"BLPrpJpayRec.query");
        }
        else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpJpayRec WHERE " + iWherePart;
            schemasTmp = dbPrpJpayRec.findByConditions(strSqlStatement);
        }
        double payFee = 0;
        String Currency = "";

        if(schemasTmp.size() > 0)
        {
          bflag = true;
          prpJpayRecSchemaTmp = new PrpJpayRecSchema();
          prpJpayRecSchemaTmp = (PrpJpayRecSchema) schemasTmp.get(0);

          for (int i = 0; i < schemasTmp.size(); i++) {
            prpJpayRecSchema = (PrpJpayRecSchema) schemasTmp.get(i);
            if (!prpJpayRecSchema.getCurrency2().equals("")) {
              double payFeeTemp = Double.parseDouble(prpJpayRecSchema.getPayFee()) *
                  Double.parseDouble(prpJpayRecSchema.getExchangeRate());
              
              
              dbPrpCmain = new DBPrpCmain();
              dbPrpCmain.getInfo(prpJpayRecSchema.getPolicyNo());
              if ((dbPrpCmain.getCoinsFlag().trim().equals("1")||dbPrpCmain.getCoinsFlag().trim().equals("3")) && strBelongTo != null &&
                  strBelongTo.equals("ALL")) {
                dbRate = 0;
                blPrpCcoins = new BLPrpCcoins();
                strWherePart = " CoinsType='1' And PolicyNo='" +
                    dbPrpCmain.getPolicyNo().trim() + "'";
                blPrpCcoins.query(strWherePart, 0);
                if (blPrpCcoins.getSize() <= 0) {
                  throw new UserException( -98, -1003,
                                          "BLPrpJpayRec.queryPrpJpayRecAddReal",
                                          "XX" + dbPrpCmain.getPolicyNo().trim() +
                                          "没有共XXXXX信息，请确认!");
                }
                else
                  dbRate = Double.parseDouble(blPrpCcoins.getArr(0).getCoinsRate().
                                              trim()) / 100;

                dbPrpCplan.getInfo(prpJpayRecSchema.getPolicyNo().trim(),
                                   prpJpayRecSchema.getSerialNo().trim());
                if (Double.parseDouble(dbPrpCplan.getDelinquentFee().trim()) == 0) {
                  strWherePart = " SerialNo='" +
                      prpJpayRecSchema.getSerialNo().trim()
                      + "' And CertiNo='" + prpJpayRecSchema.getCertiNo().trim()
                      +
                      "' And SubStr(Flag,1,1)<>'1' Order By InputDate,InputTime DESC";
                  blPrpJpayRec.query(strWherePart, 0);
                  PrpJpayRecSchema prpJpayRecSchema1 = new PrpJpayRecSchema();
                  prpJpayRecSchema1 = blPrpJpayRec.getArr(0);
                  if (blPrpJpayRec.getSize() == 1) {
                    payFeeTemp = Double.parseDouble(dbPrpCplan.getPlanFee().trim()) *
                        Double.parseDouble(prpJpayRecSchema.getExchangeRate());
                  }
                  else if (prpJpayRecSchema1.getInputDate().equals(
                      prpJpayRecSchema.getInputDate()) &&
                           prpJpayRecSchema1.getInputTime().equals(
                      prpJpayRecSchema.getInputTime())) {
                    payFeeTemp = Double.parseDouble(dbPrpCplan.getPlanFee().trim());
                    for (int j = 1; j < blPrpJpayRec.getSize(); j++) {
                      prpJpayRecSchema1 = blPrpJpayRec.getArr(j);
                      payFeeTemp -=
                          Str.round(Double.parseDouble(prpJpayRecSchema1.
                          getPayFee()) / dbRate, 2);
                    }
                    payFeeTemp = Str.round(payFeeTemp *
                                           Double.parseDouble(prpJpayRecSchema.
                        getExchangeRate()), 2);
                  }
                  else {
                    payFeeTemp = Str.round(payFeeTemp / dbRate, 2);
                  }
                }
                else {
                  payFeeTemp = Str.round(payFeeTemp / dbRate, 2);
                }
              }
              
              
              payFeeTemp = Double.parseDouble(new DecimalFormat("0.00").format(
                  payFeeTemp));
              payFee += payFeeTemp;
              Currency = prpJpayRecSchema.getCurrency2();
            }
            else {
              double payFeeTemp = Double.parseDouble(prpJpayRecSchema.getPayFee());
              
              
              dbPrpCmain = new DBPrpCmain();
              dbPrpCmain.getInfo(prpJpayRecSchema.getPolicyNo());
              if ((dbPrpCmain.getCoinsFlag().trim().equals("1")||dbPrpCmain.getCoinsFlag().trim().equals("3")) && strBelongTo != null &&
                  strBelongTo.equals("ALL")) {
                dbRate = 0;
                blPrpCcoins = new BLPrpCcoins();
                strWherePart = " CoinsType='1' And PolicyNo='" +
                    dbPrpCmain.getPolicyNo().trim() + "'";
                blPrpCcoins.query(strWherePart, 0);
                if (blPrpCcoins.getSize() <= 0) {
                  throw new UserException( -98, -1003,
                                          "BLPrpJpayRec.queryPrpJpayRecAddReal",
                                          "XX" + dbPrpCmain.getPolicyNo().trim() +
                                          "没有共XXXXX信息，请确认!");
                }
                else
                  dbRate = Double.parseDouble(blPrpCcoins.getArr(0).getCoinsRate().
                                              trim()) / 100;

                dbPrpCplan.getInfo(prpJpayRecSchema.getPolicyNo().trim(),
                                   prpJpayRecSchema.getSerialNo().trim());
                if (Double.parseDouble(dbPrpCplan.getDelinquentFee().trim()) == 0) {
                  strWherePart = " SerialNo='" +
                      prpJpayRecSchema.getSerialNo().trim()
                      + "' And CertiNo='" + prpJpayRecSchema.getCertiNo().trim()
                      +
                      "' And SubStr(Flag,1,1)<>'1' ' Order By InputDate,InputTime DESC";
                  blPrpJpayRec.query(strWherePart, 0);
                  PrpJpayRecSchema prpJpayRecSchema1 = new PrpJpayRecSchema();
                  prpJpayRecSchema1 = blPrpJpayRec.getArr(0);
                  if (blPrpJpayRec.getSize() == 1) {
                    payFeeTemp = Double.parseDouble(dbPrpCplan.getPlanFee().trim());
                  }
                  else if (prpJpayRecSchema1.getInputDate().equals(
                      prpJpayRecSchema.getInputDate()) &&
                           prpJpayRecSchema1.getInputTime().equals(
                      prpJpayRecSchema.getInputTime())) {
                    payFeeTemp = Double.parseDouble(dbPrpCplan.getPlanFee().trim());
                    for (int j = 1; j < blPrpJpayRec.getSize(); j++) {
                      prpJpayRecSchema1 = blPrpJpayRec.getArr(j);
                      payFeeTemp -=
                          Str.round(Double.parseDouble(prpJpayRecSchema1.
                          getPayFee()) / dbRate, 2);
                    }
                    payFeeTemp = Str.round(payFeeTemp, 2);
                  }
                  else {
                    payFeeTemp = Str.round(payFeeTemp / dbRate, 2);
                  }
                }
                else {
                  payFeeTemp = Str.round(payFeeTemp / dbRate, 2);
                }
              }
              
              
              payFee += payFeeTemp;
              Currency = prpJpayRecSchema.getCurrency1();
            }
          }
        }

        DBPrpJrefRec dbPrpJrefRec = new DBPrpJrefRec();
        if (iLimitCount > 0 && dbPrpJrefRec.getCount(iWherePart) > iLimitCount)
        {
          throw new UserException(-98,-1003,"BLPrpJpayRec.query");
        }
        else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpJrefRec WHERE " + iWherePart;
            schemasTmp = dbPrpJpayRec.findByConditions(strSqlStatement);
        }
        if(schemasTmp.size() > 0)
        {
          if(!bflag)
          {
            prpJpayRecSchemaTmp = (PrpJpayRecSchema)schemasTmp.get(0);
          }
          for(int i=0; i<schemasTmp.size(); i++)
          {
            prpJpayRecSchema = (PrpJpayRecSchema) schemasTmp.get(i);
            if(!prpJpayRecSchema.getCurrency2().equals(""))
            {
              double payFeeTemp = Double.parseDouble(prpJpayRecSchema.getPayFee())
                  * Double.parseDouble(prpJpayRecSchema.getExchangeRate());
              payFeeTemp = Str.round(payFeeTemp,2);
              payFee -= payFeeTemp;
              Currency = prpJpayRecSchema.getCurrency2();
            }else
            {
              double payFeeTemp = Double.parseDouble(prpJpayRecSchema.getPayFee());
              payFeeTemp = Str.round(payFeeTemp,2);
              payFee -= payFeeTemp;
              Currency = prpJpayRecSchema.getCurrency1();
            }
          }
        }

        prpJpayRecSchemaTmp.setPayFee(new DecimalFormat("0.00").format(payFee));
        prpJpayRecSchemaTmp.setCurrency1(Currency);
        schemas.add(prpJpayRecSchemaTmp);
      }
      catch(Exception e)
      {
        e.printStackTrace();
        throw e;
      }
    }




    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJpayRec.setSchema((PrpJpayRecSchema)schemas.get(i));
      dbPrpJpayRec.insert(dbpool);
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
   *@modify LIJIBIN 增加返回参数 receiptno
   *@descXXXXX存收据
   *@Author     : X
   *@param
   *@return strReceiptNo 收据号
   */
  public String saveReceiptAll(String iRiskCode,
                            String iSessionID, String iCancelFlag) throws
      Exception {
    DbPool dbpool = new DbPool();
    String iComCode = "";
    String[] strReceiptNo = null;
    String strWherePart = "";
    double dbRate = 0;
    double dbPlanFeeTmp = 0;
    double dbDelinquentFeeTmp = 0;

    Bill bill = new Bill();
    BLSffToAccount blSffToAccount = new BLSffToAccount();
    ChgDate chgDate = new ChgDate();

    PrpJpayRecSchema prpJpayRecSchema = new PrpJpayRecSchema();
    DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();
    DBPrpCmain dbPrpCmain = new DBPrpCmain();
    BLPrpJpayRec blPrpJpayRec = null;
    BLPrpCcoins blPrpCcoins = null;
    BLPrpCplan blPrpCplan = null;
    DBPrpCplan dbPrpCplan = null;
    boolean blnFlag = false;   

    prpJpayRecSchema = this.getArr(0);
    if (iCancelFlag.equals("1")) {
      this.cancelReceiptAll(iCancelFlag);
      return prpJpayRecSchema.getContractNo();
    }
    if (prpJpayRecSchema.getRealPayDate().length() > 1) {
      dbPrpJpayRec.getInfo(prpJpayRecSchema.getReceiptNo());
      if (dbPrpJpayRec.getFlag().length() > 0 &&
          dbPrpJpayRec.getFlag().substring(0, 1).equals("1")) {
        throw new Exception("该收据已经注销，不能再作任何处理！");
      }
      if (dbPrpJpayRec.getTransFlag().equals("1")) {
        throw new Exception("该收据已经做过到款确认，无需再做！");
      }
    }
    try {
      strReceiptNo = new String[this.getSize()];
      for(int i=0;i<this.getSize();i++)
      {
        prpJpayRecSchema = this.getArr(i);
        iComCode = prpJpayRecSchema.getComCode().trim();
        strReceiptNo[i] = prpJpayRecSchema.getReceiptNo();
        if (strReceiptNo[i] == null || strReceiptNo[i].length() == 0) {
          blnFlag = true;
          int intYear = Integer.parseInt(chgDate.getCurrentTime("yyyy"));
          strReceiptNo[i] = bill.getNo("prpjpayrec", iRiskCode, iComCode, intYear,
                                    iSessionID);
          prpJpayRecSchema.setReceiptNo(strReceiptNo[i]);
          prpJpayRecSchema.setContractNo(strReceiptNo[0]);
          this.setArr(prpJpayRecSchema,i);
        }
      }
    }
    catch (Exception e) {
      throw e;
    }

    try {
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      dbpool.beginTransaction();
      
      for(int i=0;i<this.getSize();i++)
      {
        prpJpayRecSchema = this.getArr(i);
        
        if (prpJpayRecSchema.getRealPayDate().length() > 1)
        {
          dbPrpJpayRec.getInfoForUpdate(dbpool,prpJpayRecSchema.getReceiptNo());
          if (dbPrpJpayRec.getFlag().length() > 0 &&
              dbPrpJpayRec.getFlag().substring(0, 1).equals("1"))
          {
            throw new Exception("该收据已经注销，不能再作任何处理！");
          }
          if (dbPrpJpayRec.getTransFlag().equals("1")) {
            throw new Exception("该收据已经做过到款确认，无需再做！");
          }
        }
        
        if (prpJpayRecSchema.getRealPayFee() == null ||
            Double.parseDouble(Str.chgStrZero(prpJpayRecSchema.getRealPayFee()))==0)
        {
          if (!prpJpayRecSchema.getCertiType().equals("Z"))
          {
            
            dbPrpCmain = new DBPrpCmain();
            blPrpCplan = new BLPrpCplan();
            dbPrpCmain.getInfo(dbpool,prpJpayRecSchema.getPolicyNo());
            if(dbPrpCmain.getCoinsFlag().trim().equals("1")||dbPrpCmain.getCoinsFlag().trim().equals("3"))
            {
              dbRate = 0;
              blPrpCcoins = new BLPrpCcoins();
              blPrpJpayRec = new BLPrpJpayRec();
              dbPrpCplan = new DBPrpCplan();
              dbPrpCplan.getInfo(dbpool,prpJpayRecSchema.getPolicyNo(),prpJpayRecSchema.getSerialNo());
              strWherePart = " CoinsType='1' And PolicyNo='" +
                  dbPrpCmain.getPolicyNo().trim() + "'";
              blPrpCcoins.query(dbpool,strWherePart, 0);
              if(blPrpCcoins.getSize()<=0)
              {
                throw new UserException( -98, -1003,
                                        "BLPrpJpayRec.saveReceiptAll",
                                        "XX" + dbPrpCmain.getPolicyNo().trim() +
                                        "没有共XXXXX信息，请确认!");
              }
              else
                dbRate = Double.parseDouble(blPrpCcoins.getArr(0).getCoinsRate().
                                          trim()) / 100;
              dbPlanFeeTmp = Double.parseDouble(dbPrpCplan.getPlanFee().trim()) *
                  dbRate;
              dbDelinquentFeeTmp = Double.parseDouble(dbPrpCplan.getPlanFee().
                  trim()) * dbRate;
              strWherePart = " SubStr(Flag,1,1)<>'1' And PayFee Is Not NULL And PolicyNo='" +
                  prpJpayRecSchema.getPolicyNo().trim() + "' And CertiNo='" +
                  prpJpayRecSchema.getCertiNo().trim() + "' And SerialNo='" +
                  prpJpayRecSchema.getSerialNo().trim() + "'";
              blPrpJpayRec.query(dbpool,strWherePart, 0);
              if (blPrpJpayRec.getSize() > 0) {
                for (int m = 0; m < blPrpJpayRec.getSize(); m++) {
                  dbDelinquentFeeTmp = dbDelinquentFeeTmp -
                      Double.parseDouble(blPrpJpayRec.getArr(m).getPayFee().trim());
                }
              }
              dbDelinquentFeeTmp = Str.round(dbDelinquentFeeTmp,2);
              if((Double.parseDouble(dbPrpCplan.getDelinquentFee().trim())
                  - Double.parseDouble(prpJpayRecSchema.getPayFee()) / dbRate)<=0.03)
              {
                blPrpCplan.setDelinquentFee(dbpool,prpJpayRecSchema.getPolicyNo(),
                                            prpJpayRecSchema.getSerialNo(),
                                            dbPrpCplan.getDelinquentFee());
              }
              else
              {
                dbDelinquentFeeTmp = Str.round(
                    Double.parseDouble(prpJpayRecSchema.getPayFee()) / dbRate,2);
                blPrpCplan.setDelinquentFee(dbpool,prpJpayRecSchema.getPolicyNo(),
                                            prpJpayRecSchema.getSerialNo(),
                                            "" + dbDelinquentFeeTmp);
              }
            }
            else
            {
              blPrpCplan.setDelinquentFee(dbpool, prpJpayRecSchema.getPolicyNo().trim(),
                                          prpJpayRecSchema.getSerialNo().trim(),
                                          prpJpayRecSchema.getPayFee());
            }
            
          }
        }
        else {
          String strPayReason = prpJpayRecSchema.getPayReason();  
          /****LIJIBIN ADD BEGIN 判断是否预收XX XX和批单都有 20031205****/
          if (!prpJpayRecSchema.getCertiType().equals("Z")&&
              !prpJpayRecSchema.getPayReason().substring(0,2).equals("R5"))
          {
            
            com.sp.utility.string.Date date1 = new com.sp.utility.string.Date();
            if(prpJpayRecSchema.getCertiType().equals("E"))
            {
              DBPrpPhead dbPrpPhead = new DBPrpPhead();
              dbPrpPhead.getInfo(dbpool,prpJpayRecSchema.getCertiNo());
              date1 = new com.sp.utility.string.Date(dbPrpPhead.getValidDate());
            }
            else
            {
              dbPrpCmain.getInfo(dbpool, prpJpayRecSchema.getPolicyNo());
              date1 = new com.sp.utility.string.Date(dbPrpCmain.getStartDate());
            }
            
            com.sp.utility.string.Date date2 = new com.sp.utility.
                string.Date(prpJpayRecSchema.getRealPayDate());

            if (date1.get(date1.YEAR) > date2.get(date2.YEAR) ||
                date1.get(date1.YEAR) == date2.get(date2.YEAR) &&
                date1.get(date1.MONTH) > date2.get(date2.MONTH))
              prpJpayRecSchema.setPayReason("R50");
          }
          /****LIJIBIN ADD END   判断是否预收XX XX和批单都有 20031205****/
          blSffToAccount.payToRawVoucher(dbpool, prpJpayRecSchema, iCancelFlag);
          prpJpayRecSchema.setTransFlag("1"); 
          prpJpayRecSchema.setPayReason(strPayReason);
          prpJpayRecSchema.setTransDate(chgDate.getCurrentTime("yyyy/MM/dd"));
          prpJpayRecSchema.setTransTime(chgDate.getCurrentTime("HH:mm:ss"));
        }
        this.cancel(dbpool, prpJpayRecSchema.getReceiptNo());
      }
      for(int i=0;i<this.getSize();i++)
      {
        prpJpayRecSchema = this.getArr(i);
        if(prpJpayRecSchema.getPolicyNo().trim().equals(""))
          continue;
        
        dbPrpCplan = new DBPrpCplan();
        dbPrpCplan.getInfo(dbpool,prpJpayRecSchema.getPolicyNo().trim(),prpJpayRecSchema.getSerialNo().trim());
        if(Double.parseDouble(dbPrpCplan.getPlanFee())==Double.parseDouble(dbPrpCplan.getDelinquentFee()))
          throw new UserException( -98, -1003, "BLPrpJpayRec.saveReceipt",
                        "XX" + dbPrpCmain.getPolicyNo().trim() +
                        "没有回写缴费计划表信息，请重新签发!");
        
        dbPrpJpayRec.setSchema(prpJpayRecSchema);
        dbPrpJpayRec.insert(dbpool);
      }

      dbpool.commitTransaction();
      if(blnFlag)
      {
        for (int i = 0; i < this.getSize(); i++) {
          bill.deleteNo("prpjpayrec", strReceiptNo[i]);
        }
      }
    }

    catch (Exception e) {
      dbpool.rollbackTransaction();
      if(blnFlag)
      {
        for (int i = 0; i < this.getSize(); i++) {
          bill.putNo("prpjpayrec", strReceiptNo[i]);
        }
      }
      throw e;
    }
    finally {
      dbpool.close();
    }
    return strReceiptNo[0];
  }



    /**
     *@modify LIJIBIN 增加返回参数 receiptno
     *@descXXXXX存收据
     *@Author     : X
     *@param
     *@return strReceiptNo 收据号
     */
    public String saveReceipt(String iComCode,String iRiskCode,String iSessionID,String iCancelFlag) throws Exception
    {
      String strReceiptNo = "";
      Bill bill = new Bill();
      BLSffToAccount blSffToAccount =new BLSffToAccount();
      ChgDate chgDate = new ChgDate();
      String strWherePart = "";
      double dbRate = 0;
      double dbPlanFeeTmp = 0;
      double dbDelinquentFeeTmp = 0;
      /*
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        for(int i=0; i<this.getSize(); i++)
        {
          strReceiptNo = this.getArr(i).getReceiptNo();
          if(strReceiptNo==null||strReceiptNo.length()==0)
          {
            ChgDate chgDate = new ChgDate();
            PrpJpayRecSchema prpJpayRecSchema = new PrpJpayRecSchema();
            int intYear = Integer.parseInt(chgDate.getCurrentTime("yyyy"));
            strReceiptNo = bill.getNo("prpjpayrec",iRiskCode,iComCode,intYear,iSessionID);
            prpJpayRecSchema = this.getArr(i);
            prpJpayRecSchema.setReceiptNo(strReceiptNo);

            this.setArr(prpJpayRecSchema,i);
          }
          else
          {
            PrpJpayRecSchema prpJpayRecSchema = new PrpJpayRecSchema();
            prpJpayRecSchema = this.getArr(i);
            if (!prpJpayRecSchema.getRealPayFee().equals(""))
            {
               blSffToAccount.payToRawVoucher(dbpool,prpJpayRecSchema);
               prpJpayRecSchema.setTransFlag("1"); 
            }

          }
           this.cancel(dbpool,strReceiptNo);
        }
        save(dbpool);

        dbpool.commitTransaction();
      } */


      PrpJpayRecSchema prpJpayRecSchema = new PrpJpayRecSchema();
      DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      BLPrpJpayRec blPrpJpayRec = null;
      BLPrpCcoins blPrpCcoins = null;
      BLPrpCplan blPrpCplan = null;
      DBPrpCplan dbPrpCplan = null;

      prpJpayRecSchema = this.getArr(0);
      if(iCancelFlag.equals("1"))
      {
        this.cancelReceipt(iCancelFlag);
        return prpJpayRecSchema.getReceiptNo();
      }

      boolean blnFlag = false;
      try
      {
        strReceiptNo = prpJpayRecSchema.getReceiptNo();
        if(strReceiptNo==null||strReceiptNo.length()==0)
        {
          int intYear = Integer.parseInt(chgDate.getCurrentTime("yyyy"));
          strReceiptNo = bill.getNo("prpjpayrec",iRiskCode,iComCode,intYear,iSessionID);
          prpJpayRecSchema.setReceiptNo(strReceiptNo);
          this.setArr(prpJpayRecSchema,0);
          blnFlag = true;
        }
      }
      catch(Exception e)
      {
        throw e;
      }

      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        dbPrpJpayRec.getInfoForUpdate(dbpool,prpJpayRecSchema.getReceiptNo());
        if(dbPrpJpayRec.getFlag().length()>0&&dbPrpJpayRec.getFlag().substring(0,1).equals("1"))
        {
          throw new Exception("该收据已经注销，不能再作任何处理！");
        }
        if(dbPrpJpayRec.getTransFlag().equals("1"))
        {
          throw new Exception("该收据已经做过到款确认，无需再做！");
        }

        
        if(prpJpayRecSchema.getRealPayFee()==null||
           Double.parseDouble(Str.chgStrZero(prpJpayRecSchema.getRealPayFee()))==0)
        {
          if(!prpJpayRecSchema.getCertiType().equals("Z"))
          {
            
            dbPrpCmain = new DBPrpCmain();
            blPrpCplan = new BLPrpCplan();

            dbPrpCmain.getInfo(dbpool,prpJpayRecSchema.getPolicyNo());
            if(dbPrpCmain.getCoinsFlag().trim().equals("1")||dbPrpCmain.getCoinsFlag().trim().equals("3"))
            {
              dbRate = 0;
              blPrpCcoins = new BLPrpCcoins();
              blPrpJpayRec = new BLPrpJpayRec();
              dbPrpCplan = new DBPrpCplan();
              dbPrpCplan.getInfo(dbpool,prpJpayRecSchema.getPolicyNo(),prpJpayRecSchema.getSerialNo());
              strWherePart = " CoinsType='1' And PolicyNo='" +
                  dbPrpCmain.getPolicyNo().trim() + "'";
              blPrpCcoins.query(dbpool,strWherePart, 0);
              if(blPrpCcoins.getSize()<=0)
              {
                throw new UserException( -98, -1003, "BLPrpJpayRec.saveReceipt",
                                        "XX" + dbPrpCmain.getPolicyNo().trim() +
                                        "没有共XXXXX信息，请确认!");
              }
              else
                dbRate = Double.parseDouble(blPrpCcoins.getArr(0).getCoinsRate().
                                            trim()) / 100;
              dbPlanFeeTmp = Double.parseDouble(dbPrpCplan.getPlanFee().trim()) *
                  dbRate;
              dbDelinquentFeeTmp = Double.parseDouble(dbPrpCplan.getPlanFee().
                  trim()) * dbRate;
              strWherePart = " SubStr(Flag,1,1)<>'1' And PayFee Is Not NULL And PolicyNo='" +
                  prpJpayRecSchema.getPolicyNo().trim() + "' And CertiNo='" +
                  prpJpayRecSchema.getCertiNo().trim() + "' And SerialNo='" +
                  prpJpayRecSchema.getSerialNo().trim() + "'";
              blPrpJpayRec.query(dbpool,strWherePart, 0);
               if (blPrpJpayRec.getSize() > 0) {
                for (int m = 0; m < blPrpJpayRec.getSize(); m++) {
                  dbDelinquentFeeTmp = dbDelinquentFeeTmp -
                      Double.parseDouble(blPrpJpayRec.getArr(m).getPayFee().trim());
                }
              }
              dbDelinquentFeeTmp = Str.round(dbDelinquentFeeTmp,2);
              if((Double.parseDouble(dbPrpCplan.getDelinquentFee().trim())
                  - Double.parseDouble(prpJpayRecSchema.getPayFee()) / dbRate)<=0.03)
              {
                blPrpCplan.setDelinquentFee(dbpool,prpJpayRecSchema.getPolicyNo(),
                                            prpJpayRecSchema.getSerialNo(),
                                            dbPrpCplan.getDelinquentFee());
              }
              else
              {
                dbDelinquentFeeTmp = Str.round(
                    Double.parseDouble(prpJpayRecSchema.getPayFee()) / dbRate,2);
                blPrpCplan.setDelinquentFee(dbpool,prpJpayRecSchema.getPolicyNo(),
                                            prpJpayRecSchema.getSerialNo(),
                                            "" + dbDelinquentFeeTmp);
              }
            }
            else
            {
              blPrpCplan.setDelinquentFee(dbpool, prpJpayRecSchema.getPolicyNo().trim(),
                                          prpJpayRecSchema.getSerialNo().trim(),
                                          prpJpayRecSchema.getPayFee());
            }
            
          }
        }
        else
        {
          String strPayReason = prpJpayRecSchema.getPayReason();  
          /****LIJIBIN ADD BEGIN 判断是否预收XX XX和批单都有 20031205****/
          if(!prpJpayRecSchema.getCertiType().equals("Z")&&
             !prpJpayRecSchema.getPayReason().substring(0,2).equals("R5"))
          {
            
            com.sp.utility.string.Date date1 = new com.sp.utility.string.Date();
            if(prpJpayRecSchema.getCertiType().equals("E"))
            {
              DBPrpPhead dbPrpPhead = new DBPrpPhead();
              dbPrpPhead.getInfo(dbpool,prpJpayRecSchema.getCertiNo());
              date1 = new com.sp.utility.string.Date(dbPrpPhead.getValidDate());
            }
            else
            {
              dbPrpCmain.getInfo(dbpool, prpJpayRecSchema.getPolicyNo());
              date1 = new com.sp.utility.string.Date(dbPrpCmain.getStartDate());
            }
            
            com.sp.utility.string.Date date2 = new com.sp.utility.string.Date(prpJpayRecSchema.getRealPayDate());

            if(date1.get(date1.YEAR)>date2.get(date2.YEAR)||date1.get(date1.YEAR)==date2.get(date2.YEAR)&&date1.get(date1.MONTH)>date2.get(date2.MONTH))
              prpJpayRecSchema.setPayReason("R50");
          }
          /****LIJIBIN ADD END   判断是否预收XX XX和批单都有 20031205****/
          blSffToAccount.payToRawVoucher(dbpool,prpJpayRecSchema,iCancelFlag);
          prpJpayRecSchema.setTransFlag("1"); 
          prpJpayRecSchema.setPayReason(strPayReason); 
          prpJpayRecSchema.setTransDate(chgDate.getCurrentTime("yyyy/MM/dd"));
          prpJpayRecSchema.setTransTime(chgDate.getCurrentTime("HH:mm:ss"));

          
          if(this.getSize()>1)
            blSffToAccount.payToRawVoucher(dbpool,this.getArr(1),iCancelFlag);
        }
        
        dbPrpCplan = new DBPrpCplan();
        dbPrpCplan.getInfo(dbpool,prpJpayRecSchema.getPolicyNo().trim(),prpJpayRecSchema.getSerialNo().trim());
        if(Double.parseDouble(dbPrpCplan.getPlanFee())==Double.parseDouble(dbPrpCplan.getDelinquentFee()))
          throw new UserException( -98, -1003, "BLPrpJpayRec.saveReceipt",
                        "XX" + dbPrpCmain.getPolicyNo().trim() +
                        "没有回写缴费计划表信息，请重新签发!");
        
        this.cancel(dbpool,prpJpayRecSchema.getReceiptNo());
        dbPrpJpayRec.setSchema(prpJpayRecSchema);
        dbPrpJpayRec.insert(dbpool);
        dbpool.commitTransaction();
        if(blnFlag)
          bill.deleteNo("prpjpayrec",strReceiptNo);
      }

      catch (Exception e)
      {
        dbpool.rollbackTransaction();
        
        if(blnFlag)
          bill.putNo("prpjpayrec",strReceiptNo);
        
        throw e;
      }
      finally {
        dbpool.close();
      }
      return strReceiptNo;
    }

    /**
     *@desc   注销收据
     *@Author     : X
     *@param
     *@return
     */
    public void cancelReceiptAll(String iCancelFlag) throws Exception {
      DbPool dbpool = new DbPool();
      DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      BLPrpCcoins blPrpCcoins = new BLPrpCcoins();
      PrpJpayRecSchema prpJpayRecSchema = new PrpJpayRecSchema();
      BLSffToAccount blSffToAccount = new BLSffToAccount();
      BLPrpCplan blPrpCplan = null;
      String strWherePart = "";
      int intLength = 0;
      double payFee = 0.0;
      double dbRate = 0.0;

      try {
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        for(int i=0;i<this.getSize();i++)
        {
          prpJpayRecSchema = new PrpJpayRecSchema();
          dbPrpJpayRec = new DBPrpJpayRec();
          prpJpayRecSchema = this.getArr(i);
          dbPrpJpayRec.getInfo(dbpool, prpJpayRecSchema.getReceiptNo());
          if (dbPrpJpayRec.getFlag().length() > 0 &&
              dbPrpJpayRec.getFlag().substring(0,1).equals("1")) {
            throw new Exception("已经做过注销，无需再做！");
          }

          if (dbPrpJpayRec.getTransFlag().equals("1"))
          {
            blSffToAccount.payToRawVoucher(dbpool, prpJpayRecSchema, iCancelFlag);
          }

          if (!dbPrpJpayRec.getRealPayDate().trim().equals(""))
          {
            dbPrpJpayRec.setRealPayDate("");
            dbPrpJpayRec.setRealPayFee("");
            dbPrpJpayRec.setTransFlag("0");
            dbPrpJpayRec.setTransDate("");
            dbPrpJpayRec.setTransTime("");
          }
          else
          {
            
            intLength = dbPrpJpayRec.getFlag().length();
            if (intLength < 2)
              dbPrpJpayRec.setFlag("1");
            else
              dbPrpJpayRec.setFlag("1"+dbPrpJpayRec.getFlag().substring(1, intLength));
            
            payFee = -Double.parseDouble(dbPrpJpayRec.getPayFee());
            blPrpCplan = new BLPrpCplan();
            
            dbPrpCmain = new DBPrpCmain();
            dbPrpCmain.getInfo(dbpool, prpJpayRecSchema.getPolicyNo());
            if (dbPrpCmain.getCoinsFlag().trim().equals("1")||dbPrpCmain.getCoinsFlag().trim().equals("3")) {
              dbRate = 0;
              blPrpCcoins = new BLPrpCcoins();
              strWherePart = " CoinsType='1' And PolicyNo='" +
                  dbPrpCmain.getPolicyNo().trim() + "'";
              blPrpCcoins.query(strWherePart, 0);
              if(blPrpCcoins.getSize()<=0)
              {
                throw new UserException( -98, -1003, "BLPrpJpayRec.cancelReceiptAll",
                                        "XX" + dbPrpCmain.getPolicyNo().trim() +
                                        "没有共XXXXX信息，请确认!");
              }
              else
                dbRate = Double.parseDouble(blPrpCcoins.getArr(0).getCoinsRate().
                                            trim()) / 100;
              payFee = Str.round(payFee / dbRate, 2);
            }
            

            blPrpCplan.setDelinquentFee(dbpool, dbPrpJpayRec.getPolicyNo(),
                                        dbPrpJpayRec.getSerialNo(), "" + payFee);
          }
          dbPrpJpayRec.update(dbpool);

        }
        dbpool.commitTransaction();
      }
      catch (Exception e) {
        dbpool.rollbackTransaction();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }


    /**
     *@desc   注销收据
     *@Author     : X
     *@param
     *@return
     */
    public void cancelReceipt(String iCancelFlag) throws Exception
    {
      DbPool dbpool = new DbPool();
      DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      BLPrpCcoins blPrpCcoins = new BLPrpCcoins();
      PrpJpayRecSchema prpJpayRecSchema = new PrpJpayRecSchema();
      BLSffToAccount blSffToAccount = new BLSffToAccount();
      BLPrpCplan blPrpCplan = null;
      String strWherePart = "";
      int intLength = 0;
      double payFee = 0.0;
      double dbRate = 0.0;

      try {
    	  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        prpJpayRecSchema = this.getArr(0);
        dbPrpJpayRec.getInfoForUpdate(dbpool,prpJpayRecSchema.getReceiptNo());
        if(dbPrpJpayRec.getFlag().length()>0&&dbPrpJpayRec.getFlag().substring(0,1).equals("1"))
        {
          throw new Exception("已经做过注销，无需再做！");
        }

        
        if(dbPrpJpayRec.getTransFlag().equals("1"))
        {
          blSffToAccount.payToRawVoucher(dbpool,prpJpayRecSchema,iCancelFlag);
        }

        if (!dbPrpJpayRec.getRealPayDate().trim().equals(""))   
        {
          
          dbPrpJpayRec.setRealPayDate("");
          dbPrpJpayRec.setRealPayFee("");
          dbPrpJpayRec.setTransFlag("0");
          dbPrpJpayRec.setTransDate("");
          dbPrpJpayRec.setTransTime("");
        }
        else    
        {
          
          intLength = dbPrpJpayRec.getFlag().length();
          if (intLength < 2)
            dbPrpJpayRec.setFlag("1");
          else
            dbPrpJpayRec.setFlag("1"+dbPrpJpayRec.getFlag().substring(1, intLength));
          
          payFee = -Double.parseDouble(dbPrpJpayRec.getPayFee());
          blPrpCplan = new BLPrpCplan();
          
          dbPrpCmain = new DBPrpCmain();
          dbPrpCmain.getInfo(dbpool,prpJpayRecSchema.getPolicyNo());
          if(dbPrpCmain.getCoinsFlag().trim().equals("1")||dbPrpCmain.getCoinsFlag().trim().equals("3"))
          {
            dbRate = 0;
            blPrpCcoins = new BLPrpCcoins();
            strWherePart = " CoinsType='1' And PolicyNo='" +
                dbPrpCmain.getPolicyNo().trim() + "'";
            blPrpCcoins.query(strWherePart, 0);
            if(blPrpCcoins.getSize()<=0)
            {
              throw new UserException(-98,-1003,"BLPrpJpayRec.cancelReceipt",
                                      "XX" + dbPrpCmain.getPolicyNo().trim() +
                                      "没有共XXXXX信息，请确认!");
            }
            else
              dbRate = Double.parseDouble(blPrpCcoins.getArr(0).getCoinsRate().
                                        trim()) / 100;
            payFee = Str.round(payFee/dbRate,2);
          }
          
          blPrpCplan.setDelinquentFee(dbpool,dbPrpJpayRec.getPolicyNo(),dbPrpJpayRec.getSerialNo(),""+payFee);
        }
        dbPrpJpayRec.update(dbpool);

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
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param null null
     *@return 无
     */
    public void cancel(DbPool dbpool,String strReceiptNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpJpayRec WHERE ReceiptNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, strReceiptNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }

    /**
     * 不带dbpool的删除方法
     *@param null null
     *@return 无
     */
    public void cancel(String strReceiptNo ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,strReceiptNo);
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
     * 带dbpool根据null获取数据
     *@param null null
     *@return 无
     */
    public void getData(String strReceiptNo) throws Exception
    {
        DbPool dbpool = new DbPool();

  	try {
  		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  		getData(dbpool, strReceiptNo);
  	} catch (Exception e) {
  		
  	}finally {
  		dbpool.close();
  	}

    }

    /**
     * 不带dbpool根据null获取数据
     *@param dbpool 连接池
     *@param null null
     *@return 无
     */
    public void getData(DbPool dbpool,String strReceiptNo) throws Exception
    {
    String strWherePart = "";
    strWherePart = "ReceiptNo= '" + strReceiptNo + "'";
    query(dbpool,strWherePart,0);
    }

    /* modify by liruijuan add begin 030822 */
    
    public void update() throws UserException,Exception
    {
      if (this.schemas == null)
      {
        throw new UserException(-98,-1005,"BLPrpJpayRec.update");
      }
      PrpJpayRecSchema   prpJpayRecSchema = new PrpJpayRecSchema();
      prpJpayRecSchema   = this.getArr(0);
      this.cancel(prpJpayRecSchema.getReceiptNo());
      this.save();
    }

    /**
     *@desc  获取每个XX的总实收XX
     *@Author     : X
     *@param iWherePart 查询条件
     *@return 无
     */
    public void getSumFeeByPolicyNo(String iWherePart) throws Exception
    {
      String strSQL = "";
      strSQL = "SELECT SUM(RealPayFee) SumPayFee,PolicyNo FROM PrpJpayRec WHERE "
            + iWherePart + " GROUP BY PolicyNo";
      DbPool dbpool = new DbPool();
      PrpJpayRecSchema prpJpayRecSchema = null;
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
          prpJpayRecSchema = new PrpJpayRecSchema();
          prpJpayRecSchema.setRealPayFee(""+resultSet.getDouble("SumPayFee"));
          prpJpayRecSchema.setPolicyNo(resultSet.getString("PolicyNo"));
          this.setArr(prpJpayRecSchema);
        }
        resultSet.close();
      }
      catch(Exception e)
      {
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     *@desc   合收据分摊应缴XX
     *@Author     : X
     *@param  iSumPayFee 合XX
     */
    public void premAlloc(double iSumPayFee) throws Exception
    {
      double dblSumPayFee  = 0.0;   
      double dblUnitPayFee = 0.0;   
      double dblMaxPayFee  = 0.0;   
      DecimalFormat decimalFormat = new DecimalFormat("0.00");
      try
      {
        for(int i=0; i<this.getSize(); i++)
        {
          if(!this.getArr(i).getPayFee().equals(""))
            dblSumPayFee += Double.parseDouble(this.getArr(i).getPayFee());
        }
        dblUnitPayFee = iSumPayFee/dblSumPayFee;
        dblSumPayFee = 0.0;
        double dblPayFee = 0.0;
        for(int i=0; i<this.getSize(); i++)
        {
          dblPayFee = 0.0;
          if(!this.getArr(i).getPayFee().equals(""))
            dblPayFee = Double.parseDouble(this.getArr(i).getPayFee())*dblUnitPayFee;
          this.getArr(i).setPayFee(decimalFormat.format(dblPayFee));
          dblSumPayFee += Double.parseDouble(this.getArr(i).getPayFee());
        }
        
        if(Math.abs(iSumPayFee-dblSumPayFee)>0.005)
        {
          dblPayFee = Double.parseDouble(this.getArr(0).getPayFee());
          dblPayFee += iSumPayFee-dblSumPayFee;
          this.getArr(0).setPayFee(decimalFormat.format(dblPayFee));
        }
      }
      catch(Exception e)
      {
        throw e;
      }

    }

    /**
     *@Author     : X
     *@desc   修改收据
     *@param  无
     *@return 无
     */
    public void modifyReceipt() throws Exception
    {
      DbPool dbpool = new DbPool();
      DBPrpJpayRec dbPrpJpayRec = null;
      PrpJpayRecSchema prpJpayRecSchema = null;
      BLPrpCplan blPrpCplan = new BLPrpCplan();
      BLSffToAccount blSffToAccount = new BLSffToAccount();

      try {
    	  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        for(int i=0; i<this.getSize(); i++)
        {
          prpJpayRecSchema = new PrpJpayRecSchema();
          prpJpayRecSchema = this.getArr(i);
          dbPrpJpayRec = new DBPrpJpayRec();
          dbPrpJpayRec.getInfo(prpJpayRecSchema.getReceiptNo());

          dbPrpJpayRec.setPayWay(prpJpayRecSchema.getPayWay());
          dbPrpJpayRec.setReceiptName(prpJpayRecSchema.getReceiptName());
          dbPrpJpayRec.setCurrency2(prpJpayRecSchema.getCurrency2());
          dbPrpJpayRec.setExchangeRate(prpJpayRecSchema.getExchangeRate());
          dbPrpJpayRec.setInvoiceNo(prpJpayRecSchema.getInvoiceNo());
          dbPrpJpayRec.setRemark(prpJpayRecSchema.getRemark());
          
          if(!prpJpayRecSchema.getRealPayDate().trim().equals(""))
          {
            dbPrpJpayRec.setIdentifyType(prpJpayRecSchema.getIdentifyType());
            dbPrpJpayRec.setIdentifyNumber(prpJpayRecSchema.getIdentifyNumber());
            dbPrpJpayRec.setBankCode(prpJpayRecSchema.getBankCode());
            dbPrpJpayRec.setAccountNo(prpJpayRecSchema.getAccountNo());
            dbPrpJpayRec.setCheckNo(prpJpayRecSchema.getCheckNo());
            dbPrpJpayRec.setRealPayFee(prpJpayRecSchema.getRealPayFee());
          }
          
          if(Double.parseDouble(dbPrpJpayRec.getPayFee())!=Double.parseDouble(prpJpayRecSchema.getPayFee()))
          {
            double dblDiffFee = Double.parseDouble(prpJpayRecSchema.getPayFee())
                              - Double.parseDouble(dbPrpJpayRec.getPayFee());
            blPrpCplan.setDelinquentFee(dbpool, dbPrpJpayRec.getPolicyNo(),
                                        dbPrpJpayRec.getSerialNo(),""+dblDiffFee);
          }

          dbPrpJpayRec.update(dbpool);

          
          if(dbPrpJpayRec.getTransFlag().equals("1"))
          {
            blSffToAccount.modifyRawVoucher(dbpool,prpJpayRecSchema);
            if(i==0)
              blSffToAccount.modifyPrePayRaw(dbpool,prpJpayRecSchema);   
          }
        }
        dbpool.commitTransaction();
      }
      catch (Exception e) {
        dbpool.rollbackTransaction();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }


    /* modify by  liruijuan add end 030822 */

       /* add by liuning begin 20040114 */
     /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryPaySum(String iWherePart,String iOtherCondition,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();
      if (iLimitCount > 0 && dbPrpJpayRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRec.queryPaySum");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT RealPayDate,Currency2,OperatorCode,RiskCode ,PayWay,sum(RealPayFee) as RealPayFee FROM PrpJpayRec WHERE " + iWherePart+iOtherCondition;
        schemas = dbPrpJpayRec.findPaySumByConditions(strSqlStatement);
      }
    }
      /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryPaySum(DbPool dbpool,String iWherePart,String iOtherCondition,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();
      if (iLimitCount > 0 && dbPrpJpayRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRec.queryPaySum");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT RealPayDate,Currency2,OperatorCode,RiskCode ,PayWay,sum(RealPayFee) as RealPayFee FROM PrpJpayRec WHERE " + iWherePart+iOtherCondition;
        schemas = dbPrpJpayRec.findPaySumByConditions(dbpool,strSqlStatement);
      }
    }
    /* add by liuning end 20040114 */
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
