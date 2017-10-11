package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBIntfPosDriver;
import com.sp.prpall.schema.IntfPosDriverSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����intfPosDriver��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-03-31</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 */
public class BLIntfPosDriver{  
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLIntfPosDriver(){
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
     *����һ��IntfPosDriverSchema��¼
     *@param iIntfPosDriverSchema IntfPosDriverSchema
     *@throws Exception
     */
    public void setArr(IntfPosDriverSchema iIntfPosDriverSchema) throws Exception
    {
       try
       {
         schemas.add(iIntfPosDriverSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��IntfPosDriverSchema��¼
     *@param index �±�
     *@return һ��IntfPosDriverSchema����
     *@throws Exception
     */
    public IntfPosDriverSchema getArr(int index) throws Exception
    {
     IntfPosDriverSchema intfPosDriverSchema = null;
       try
       {
        intfPosDriverSchema = (IntfPosDriverSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return intfPosDriverSchema;
     }
    /**
     *ɾ��һ��IntfPosDriverSchema��¼
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
      DBIntfPosDriver dbIntfPosDriver = new DBIntfPosDriver();
      if (iLimitCount > 0 && dbIntfPosDriver.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntfPosDriver.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntfPosDriver WHERE " + iWherePart; 
        schemas = dbIntfPosDriver.findByConditions(strSqlStatement);
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
      DBIntfPosDriver dbIntfPosDriver = new DBIntfPosDriver();
      if (iLimitCount > 0 && dbIntfPosDriver.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntfPosDriver.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntfPosDriver WHERE " + iWherePart; 
        schemas = dbIntfPosDriver.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBIntfPosDriver dbIntfPosDriver = new DBIntfPosDriver();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbIntfPosDriver.setSchema((IntfPosDriverSchema)schemas.get(i));
      dbIntfPosDriver.insert(dbpool);
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
        dbpool.open(SysConst.getProperty("POSDATASOURCE"));
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
     *@param ipolicyNo policyNo
     *@return ��
     */
    public void cancel(DbPool dbpool,String ipolicyNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM IntfPosDriver WHERE policyNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, ipolicyNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param ipolicyNo policyNo
     *@return ��
     */
    public void cancel(String ipolicyNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("POSDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,ipolicyNo);
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
     * ��dbpool����policyNo��ȡ����
     *@param ipolicyNo policyNo
     *@return ��
     */
    public void getData(String ipolicyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("POSDATASOURCE"));
      getData(dbpool,ipolicyNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����policyNo��ȡ����
     *@param dbpool ���ӳ�
     *@param ipolicyNo policyNo
     *@return ��
     */
    public void getData(DbPool dbpool,String ipolicyNo) throws Exception
    {
        
        
        
        
        String strWherePart = " policyNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(ipolicyNo);
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
        DBIntfPosDriver dbIntfPosDriver = new DBIntfPosDriver();
        if (iLimitCount > 0 && dbIntfPosDriver.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLIntfPosDriver.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM IntfPosDriver WHERE " + iWherePart;
            schemas = dbIntfPosDriver.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
