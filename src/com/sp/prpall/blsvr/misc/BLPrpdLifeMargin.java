package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpdLifeMargin;
import com.sp.prpall.schema.PrpdLifeMarginSchema;

/**
 * ����PrpdLifeMargin��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>@createdate 2012-03-15</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLPrpdLifeMargin{
    
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpdLifeMargin(){
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
     *����һ��PrpdLifeMarginSchema��¼
     *@param iPrpdLifeMarginSchema PrpdLifeMarginSchema
     *@throws Exception
     */
    public void setArr(PrpdLifeMarginSchema iPrpdLifeMarginSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpdLifeMarginSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpdLifeMarginSchema��¼
     *@param index �±�
     *@return һ��PrpdLifeMarginSchema����
     *@throws Exception
     */
    public PrpdLifeMarginSchema getArr(int index) throws Exception
    {
      PrpdLifeMarginSchema prpdLifeMarginSchema = null;
       try
       {
         prpdLifeMarginSchema = (PrpdLifeMarginSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpdLifeMarginSchema;
     }
    /**
     *ɾ��һ��PrpdLifeMarginSchema��¼
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
      DBPrpdLifeMargin dbPrpdLifeMargin = new DBPrpdLifeMargin();
      if (iLimitCount > 0 && dbPrpdLifeMargin.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpdLifeMargin.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpdLifeMargin WHERE " + iWherePart; 
        schemas = dbPrpdLifeMargin.findByConditions(strSqlStatement);
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
      DBPrpdLifeMargin dbPrpdLifeMargin = new DBPrpdLifeMargin();
      if (iLimitCount > 0 && dbPrpdLifeMargin.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpdLifeMargin.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpdLifeMargin WHERE " + iWherePart; 
        schemas = dbPrpdLifeMargin.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpdLifeMargin dbPrpdLifeMargin = new DBPrpdLifeMargin();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpdLifeMargin.setSchema((PrpdLifeMarginSchema)schemas.get(i));
        dbPrpdLifeMargin.insert(dbpool);
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
      	
      	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
      		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
      	}else{       		
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      	}
      	
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
     *@param iSERIALNO ���
     *@return ��
     */
    public void cancel(DbPool dbpool,String iSERIALNO) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpdLifeMargin WHERE SERIALNO= '" + iSERIALNO + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iSERIALNO ���
     *@return ��
     */
    public void cancel(String iSERIALNO ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
      	
      	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
      		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
      	}else{       		
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      	}
      	
        dbpool.beginTransaction();
        cancel(dbpool,iSERIALNO);
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
     * ��dbpool������Ż�ȡ����
     *@param iSERIALNO ���
     *@return ��
     */
    public void getData(String iSERIALNO) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iSERIALNO);
      dbpool.close();
    }
      
    /**
     * ����dbpool������Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iSERIALNO ���
     *@return ��
     */
    public void getData(DbPool dbpool,String iSERIALNO) throws Exception
    {
      String strWherePart = "";
      strWherePart = "SERIALNO= '" + iSERIALNO + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
