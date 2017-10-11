package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCIEndorValidLoss;
import com.sp.indiv.ci.schema.CIEndorValidLossSchema;

/**
 * ����CIEndorValidLoss��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-11-05</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLCIEndorValidLoss{
    
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCIEndorValidLoss(){
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
     *����һ��CIEndorValidLossSchema��¼
     *@param iCIEndorValidLossSchema CIEndorValidLossSchema
     *@throws Exception
     */
    public void setArr(CIEndorValidLossSchema iCIEndorValidLossSchema) throws Exception
    {
      try
      {
        schemas.add(iCIEndorValidLossSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��CIEndorValidLossSchema��¼
     *@param index �±�
     *@return һ��CIEndorValidLossSchema����
     *@throws Exception
     */
    public CIEndorValidLossSchema getArr(int index) throws Exception
    {
      CIEndorValidLossSchema cIEndorValidLossSchema = null;
       try
       {
         cIEndorValidLossSchema = (CIEndorValidLossSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIEndorValidLossSchema;
     }
    /**
     *ɾ��һ��CIEndorValidLossSchema��¼
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
      DBCIEndorValidLoss dbCIEndorValidLoss = new DBCIEndorValidLoss();
      if (iLimitCount > 0 && dbCIEndorValidLoss.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIEndorValidLoss.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIEndorValidLoss WHERE " + iWherePart; 
        schemas = dbCIEndorValidLoss.findByConditions(strSqlStatement);
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
      DBCIEndorValidLoss dbCIEndorValidLoss = new DBCIEndorValidLoss();
      if (iLimitCount > 0 && dbCIEndorValidLoss.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIEndorValidLoss.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIEndorValidLoss WHERE " + iWherePart; 
        schemas = dbCIEndorValidLoss.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIEndorValidLoss dbCIEndorValidLoss = new DBCIEndorValidLoss();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbCIEndorValidLoss.setSchema((CIEndorValidLossSchema)schemas.get(i));
        dbCIEndorValidLoss.insert(dbpool);
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
     *@param iDemandNo DemandNo
     *@return ��
     */
    public void cancel(DbPool dbpool,String iDemandNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM CIEndorValidLoss WHERE DemandNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iDemandNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iDemandNo DemandNo
     *@return ��
     */
    public void cancel(String iDemandNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iDemandNo);
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
     * ��dbpool����DemandNo��ȡ����
     *@param iDemandNo DemandNo
     *@return ��
     */
    public void getData(String iDemandNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iDemandNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����DemandNo��ȡ����
     *@param dbpool ���ӳ�
     *@param iDemandNo DemandNo
     *@return ��
     */
    public void getData(DbPool dbpool,String iDemandNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "DemandNo= '" + iDemandNo + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
