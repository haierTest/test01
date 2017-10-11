package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBIntfTransConfig;
import com.sp.prpall.schema.IntfTransConfigSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����INTF_TRANS_CONFIG��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-11-12</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 */
public class BLIntfTransConfig{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLIntfTransConfig(){
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
     *����һ��IntfTransConfigSchema��¼
     *@param iIntfTransConfigSchema IntfTransConfigSchema
     *@throws Exception
     */
    public void setArr(IntfTransConfigSchema iIntfTransConfigSchema) throws Exception
    {
       try
       {
         schemas.add(iIntfTransConfigSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��IntfTransConfigSchema��¼
     *@param index �±�
     *@return һ��IntfTransConfigSchema����
     *@throws Exception
     */
    public IntfTransConfigSchema getArr(int index) throws Exception
    {
     IntfTransConfigSchema intfTransConfigSchema = null;
       try
       {
        intfTransConfigSchema = (IntfTransConfigSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return intfTransConfigSchema;
     }
    /**
     *ɾ��һ��IntfTransConfigSchema��¼
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
      DBIntfTransConfig dbIntfTransConfig = new DBIntfTransConfig();
      if (iLimitCount > 0 && dbIntfTransConfig.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntfTransConfig.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM INTF_TRANS_CONFIG WHERE " + iWherePart; 
        schemas = dbIntfTransConfig.findByConditions(strSqlStatement);
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
      DBIntfTransConfig dbIntfTransConfig = new DBIntfTransConfig();
      if (iLimitCount > 0 && dbIntfTransConfig.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntfTransConfig.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM INTF_TRANS_CONFIG WHERE " + iWherePart; 
        schemas = dbIntfTransConfig.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBIntfTransConfig dbIntfTransConfig = new DBIntfTransConfig();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
    	  dbIntfTransConfig.setSchema((IntfTransConfigSchema)schemas.get(i));
    	  dbIntfTransConfig.insert(dbpool);
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
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param iTransID TransID
     *@return ��
     */
    public void cancel(DbPool dbpool,String iTransID) throws Exception
    {




    	String strSqlStatement = " DELETE FROM INTF_TRANS_CONFIG WHERE TransID=  ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iTransID);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iTransID TransID
     *@return ��
     */
    public void cancel(String iTransID ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iTransID);
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
     * ��dbpool����TransID��ȡ����
     *@param iTransID TransID
     *@return ��
     */
    public void getData(String iTransID) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iTransID);
      dbpool.close();
    }
      
    /**
     * ����dbpool����TransID��ȡ����
     *@param dbpool ���ӳ�
     *@param iTransID TransID
     *@return ��
     */
    public void getData(DbPool dbpool,String iTransID) throws Exception
    {
        
        
        
        
        String strWherePart = " TransID=? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iTransID);
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
        DBIntfTransConfig dbIntfTransConfig = new DBIntfTransConfig();
        if (iLimitCount > 0 && dbIntfTransConfig.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLIntfTransConfig.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM IntfTransConfig WHERE " + iWherePart;
            schemas = dbIntfTransConfig.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}