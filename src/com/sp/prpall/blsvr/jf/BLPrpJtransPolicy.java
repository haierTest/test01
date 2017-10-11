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
     * ���캯��
     */
    public BLPrpJtransPolicy(){
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
     *����һ��PrpJtransPolicySchema��¼
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
     *����һ��PrpJtransPolicySchema��¼
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
     *�õ�һ��PrpJpayRecSchema��¼
     *@param index �±�
     *@return һ��PrpJpayRecSchema����
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
     *ɾ��һ��PrpJtransPolicySchema��¼
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
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
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
     *��dbpool��save����
     *@param ��
     *@return ��
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
     *����dbpool��XXXXX�淽��
     *@param ��
     *@return ��
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
