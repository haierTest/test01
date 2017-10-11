package com.sp.prpall.blsvr.jf;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.jf.DBPrpJtransPolicy;
import com.sp.prpall.schema.PrpJtransPolicySchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 */
public class BLPrpJtransPolicy{
    private Vector schemas = new Vector();
    
    /**
     * 构造函数
     */
    public BLPrpJtransPolicy(){
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
     *增加一条PrpJtransPolicySchema记录
     *@param PrpJtransPolicySchema PrpJtransPolicySchema
     *@throws Exception
     */
    public void setArr(PrpJtransPolicySchema prpJtransPolicySchema) throws Exception
    {
       try
       {
         schemas.add(prpJtransPolicySchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }

    /**
     *增加一条PrpJtransPolicySchema记录
     *@param iPrpJtransPolicySchema PrpJtransPolicySchema
     *@throws Exception
     */
    public void setArr(PrpJtransPolicySchema prpJtransPolicySchema,int index) throws Exception
    {
       try
       {
         schemas.setElementAt(prpJtransPolicySchema,index);
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
    public PrpJtransPolicySchema getArr(int index) throws Exception
    {
        PrpJtransPolicySchema prpJtransPolicySchema = null;
       try
       {
           prpJtransPolicySchema = (PrpJtransPolicySchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJtransPolicySchema;
     }
    /**
     *删除一条PrpJtransPolicySchema记录
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
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart,ArrayList bindValues) throws UserException,Exception
    {
        String strSqlStatement = "";
        DBPrpJtransPolicy dbPrpJtransPolicy = new DBPrpJtransPolicy();
        initArr();
        strSqlStatement = " SELECT * FROM PrpJtransPolicy WHERE " + iWherePart;
        schemas = dbPrpJtransPolicy.findByConditions(strSqlStatement, bindValues);

    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJtransPolicy dbPrpJtransPolicy = new DBPrpJtransPolicy();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJtransPolicy.setSchema((PrpJtransPolicySchema)schemas.get(i));
      dbPrpJtransPolicy.insert(dbpool);
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
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
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
}
