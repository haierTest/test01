package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;
import com.sp.prpall.dbsvr.misc.DBTBItemCar;
import com.sp.prpall.schema.TBItemCarSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;


/**
 * ����TB_ITEMCAR��BL��
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>@createdate 2012-09-25</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLTBItemCar{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLTBItemCar(){
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
     *����һ��TBItemCarSchema��¼
     *@param iTBItemCarSchema TBItemCarSchema
     *@throws Exception
     */
    public void setArr(TBItemCarSchema iTBItemCarSchema) throws Exception
    {
       try
       {
         schemas.add(iTBItemCarSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��TBItemCarSchema��¼
     *@param index �±�
     *@return һ��TBItemCarSchema����
     *@throws Exception
     */
    public TBItemCarSchema getArr(int index) throws Exception
    {
     TBItemCarSchema tBItemCarSchema = null;
       try
       {
    	   tBItemCarSchema = (TBItemCarSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return tBItemCarSchema;
     }
    /**
     *ɾ��һ��TBItemCarSchema��¼
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
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()), bindValues);
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBTBItemCar dbTBItemCar = new DBTBItemCar();
      if (iLimitCount > 0 && dbTBItemCar.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTBItemCar.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_ITEMCAR WHERE " + iWherePart; 
        schemas = dbTBItemCar.findByConditions(strSqlStatement, bindValues);
      }
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()),bindValues);
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBTBItemCar dbTBItemCar = new DBTBItemCar();
      if (iLimitCount > 0 && dbTBItemCar.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTBItemCar.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_ITEMCAR WHERE " + iWherePart; 
        schemas = dbTBItemCar.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTBItemCar dbTBItemCar = new DBTBItemCar();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
          dbTBItemCar.setSchema((TBItemCarSchema)schemas.get(i));
    	  dbTBItemCar.insert(dbpool);
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
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      
      try
      {
        dbpool.beginTransaction();
        save(dbpool);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     * ��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param iPolicyNo POLICYNO
     *@param iSerialNo SERIALNO
     *@return ��
     */
    public void cancel(DbPool dbpool, String iPolicyNo, String iSerialNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM TB_ITEMCAR WHERE PolicyNo='" + iPolicyNo + "', AND SerialNo='" + iSerialNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param pOLICYNO POLICYNO
     *@param sERIALNO SERIALNO
     *@return ��
     */
    public void cancel(String iPolicyNo, String iSerialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, iPolicyNo, iSerialNo);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     * ����dbpool����������ȡ����
     *@param iPolicyNo POLICYNO
     *@param iSerialNo SERIALNO
     *@return ��
     */
    public void getData(String iPolicyNo, String iSerialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool, iPolicyNo, iSerialNo);
      dbpool.close();
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param iPolicyNo POLICYNO
     *@param iSerialNo SERIALNO
     *@return ��
     */
    public void getData(DbPool dbpool, String iPolicyNo, String iSerialNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "PolicyNo='" + iPolicyNo + "', AND SerialNo='" + iSerialNo + "'";
       query(dbpool, strWherePart, 0, null);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
