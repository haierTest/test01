package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBTbRequestFund;
import com.sp.prpall.schema.TbRequestFundSchema;

/**
 * ����TbRequestFund��BL��
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-10-23</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLTbRequestFund{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLTbRequestFund(){
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
     *����һ��TbRequestFundSchema��¼
     *@param iTbRequestFundSchema TbRequestFundSchema
     *@throws Exception
     */
    public void setArr(TbRequestFundSchema iTbRequestFundSchema) throws Exception
    {
       try
       {
         schemas.add(iTbRequestFundSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��TbRequestFundSchema��¼
     *@param index �±�
     *@return һ��TbRequestFundSchema����
     *@throws Exception
     */
    public TbRequestFundSchema getArr(int index) throws Exception
    {
     TbRequestFundSchema tbRequestFundSchema = null;
       try
       {
        tbRequestFundSchema = (TbRequestFundSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return tbRequestFundSchema;
     }
    /**
     *ɾ��һ��TbRequestFundSchema��¼
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
      DBTbRequestFund dbTbRequestFund = new DBTbRequestFund();
      if (iLimitCount > 0 && dbTbRequestFund.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTbRequestFund.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Tb_RequestFund WHERE " + iWherePart; 
        schemas = dbTbRequestFund.findByConditions(strSqlStatement, bindValues);
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
      DBTbRequestFund dbTbRequestFund = new DBTbRequestFund();
      if (iLimitCount > 0 && dbTbRequestFund.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTbRequestFund.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Tb_RequestFund WHERE " + iWherePart; 
        schemas = dbTbRequestFund.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTbRequestFund dbTbRequestFund = new DBTbRequestFund();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbTbRequestFund.setSchema((TbRequestFundSchema)schemas.get(i));
      dbTbRequestFund.insert(dbpool);
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
     *@param policyNo PolicyNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancel(DbPool dbpool, String policyNo, String serialNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM Tb_RequestFund WHERE policyNo='" + policyNo + "', AND serialNo='" + serialNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param policyNo PolicyNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancel(String policyNo, String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, policyNo, serialNo);
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
     *@param policyNo PolicyNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void getData(String policyNo, String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool, policyNo, serialNo);
      dbpool.close();
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param policyNo PolicyNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void getData(DbPool dbpool, String policyNo, String serialNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "policyNo='" + policyNo + "', AND serialNo='" + serialNo + "'";
       query(dbpool, strWherePart, 0, null);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
