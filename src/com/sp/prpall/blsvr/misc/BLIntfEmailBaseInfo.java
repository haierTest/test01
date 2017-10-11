package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBIntfEmailBaseInfo;
import com.sp.prpall.schema.IntfEmailBaseInfoSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����IntfEmailBaseInfo�ʼ���Ϣ���BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-03-31</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.1
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 */
public class BLIntfEmailBaseInfo{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLIntfEmailBaseInfo(){
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
     *����һ��IntfEmailBaseInfoSchema��¼
     *@param iIntfEmailBaseInfoSchema IntfEmailBaseInfoSchema
     *@throws Exception
     */
    public void setArr(IntfEmailBaseInfoSchema iIntfEmailBaseInfoSchema) throws Exception
    {
      try
      {
        schemas.add(iIntfEmailBaseInfoSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��IntfEmailBaseInfoSchema��¼
     *@param index �±�
     *@return һ��IntfEmailBaseInfoSchema����
     *@throws Exception
     */
    public IntfEmailBaseInfoSchema getArr(int index) throws Exception
    {
      IntfEmailBaseInfoSchema intfEmailBaseInfoSchema = null;
       try
       {
         intfEmailBaseInfoSchema = (IntfEmailBaseInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return intfEmailBaseInfoSchema;
     }
    /**
     *ɾ��һ��IntfEmailBaseInfoSchema��¼
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
      DBIntfEmailBaseInfo dbIntfEmailBaseInfo = new DBIntfEmailBaseInfo();
      if (iLimitCount > 0 && dbIntfEmailBaseInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntfEmailBaseInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntfEmailBaseInfo WHERE " + iWherePart; 
        schemas = dbIntfEmailBaseInfo.findByConditions(strSqlStatement);
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
      DBIntfEmailBaseInfo dbIntfEmailBaseInfo = new DBIntfEmailBaseInfo();
      if (iLimitCount > 0 && dbIntfEmailBaseInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntfEmailBaseInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntfEmailBaseInfo WHERE " + iWherePart; 
        schemas = dbIntfEmailBaseInfo.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBIntfEmailBaseInfo dbIntfEmailBaseInfo = new DBIntfEmailBaseInfo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbIntfEmailBaseInfo.setSchema((IntfEmailBaseInfoSchema)schemas.get(i));
        dbIntfEmailBaseInfo.insert(dbpool);
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
     *@param iSerialNo ���к�
     *@return ��
     */
    public void cancel(DbPool dbpool,String iSerialNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM IntfEmailBaseInfo WHERE SerialNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iSerialNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iSerialNo ���к�
     *@return ��
     */
    public void cancel(String iSerialNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iSerialNo);
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
     * ��dbpool�������кŻ�ȡ����
     *@param iSerialNo ���к�
     *@return ��
     */
    public void getData(String iSerialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iSerialNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool�������кŻ�ȡ����
     *@param dbpool ���ӳ�
     *@param iSerialNo ���к�
     *@return ��
     */
    public void getData(DbPool dbpool,String iSerialNo) throws Exception
    {
        
        
        
        
        String strWherePart = " SerialNo=? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iSerialNo);
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
        DBIntfEmailBaseInfo dbIntfEmailBaseInfo = new DBIntfEmailBaseInfo();
        if (iLimitCount > 0 && dbIntfEmailBaseInfo.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLIntfEmailBaseInfo.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM IntfEmailBaseInfo WHERE " + iWherePart;
            schemas = dbIntfEmailBaseInfo.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
