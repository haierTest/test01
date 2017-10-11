package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBTBLimit;
import com.sp.prpall.dbsvr.misc.DBTbItemDevice;
import com.sp.prpall.schema.TbItemDeviceSchema;

/**
 * ����TbItemDevice��BL��
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-02-11</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLTbItemDevice{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLTbItemDevice(){
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
     *����һ��TbItemDeviceSchema��¼
     *@param iTbItemDeviceSchema TbItemDeviceSchema
     *@throws Exception
     */
    public void setArr(TbItemDeviceSchema iTbItemDeviceSchema) throws Exception
    {
       try
       {
         schemas.add(iTbItemDeviceSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��TbItemDeviceSchema��¼
     *@param index �±�
     *@return һ��TbItemDeviceSchema����
     *@throws Exception
     */
    public TbItemDeviceSchema getArr(int index) throws Exception
    {
     TbItemDeviceSchema tbItemDeviceSchema = null;
       try
       {
        tbItemDeviceSchema = (TbItemDeviceSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return tbItemDeviceSchema;
     }
    /**
     *ɾ��һ��TbItemDeviceSchema��¼
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
      DBTbItemDevice dbTbItemDevice = new DBTbItemDevice();
      if (iLimitCount > 0 && dbTbItemDevice.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTbItemDevice.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_ITEMDEVICE WHERE " + iWherePart; 
        schemas = dbTbItemDevice.findByConditions(strSqlStatement, bindValues);
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
      DBTbItemDevice dbTbItemDevice = new DBTbItemDevice();
      if (iLimitCount > 0 && dbTbItemDevice.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTbItemDevice.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_ITEMDEVICE WHERE " + iWherePart; 
        schemas = dbTbItemDevice.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTbItemDevice dbTbItemDevice = new DBTbItemDevice();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbTbItemDevice.setSchema((TbItemDeviceSchema)schemas.get(i));
      dbTbItemDevice.insert(dbpool);
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
     *@param policylNo PolicylNo
     *@param itemNo ItemNo
     *@return ��
     */
    public void cancel(DbPool dbpool, String policylNo, String itemNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM TB_ITEMDEVICE WHERE policylNo='" + policylNo + "', AND itemNo='" + itemNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param policylNo PolicylNo
     *@param itemNo ItemNo
     *@return ��
     */
    public void cancel(String policylNo, String itemNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, policylNo, itemNo);
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
     * ��dbpool����PolicyNo��ȡ����
     *@param iPolicyNo PolicyNo
     *@return ��
     */
    public void getData(String iPolicyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        getData(dbpool,iPolicyNo);
      }
      catch (Exception e)
      {
        
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     * ����dbpool����PolicyNo��ȡ����
     *@param dbpool ���ӳ�
     *@param iPolicyNo PolicyNo
     *@return ��
     */
    public void getData(DbPool dbpool,String iPolicyNo) throws Exception
    {
      String strWherePart = " PolicyNo= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iPolicyNo);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@author liuweichang 20140212
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
        DBTbItemDevice dbTbItemDevice = new DBTbItemDevice();
        if (iLimitCount > 0 && dbTbItemDevice.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLTbItemDevice.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM TB_ITEMDEVICE WHERE " + iWherePart;
            schemas = dbTbItemDevice.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
