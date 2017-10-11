package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpInsuredIntegral;
import com.sp.prpall.schema.IntegralErrorLogSchema;
import com.sp.prpall.schema.PrpInsuredIntegralSchema;

/**
 * ����prpInsuredIntegral��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>@createdate 2013-05-02</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLPrpInsuredIntegral{
    
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpInsuredIntegral(){
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
     *����һ��PrpInsuredIntegralSchema��¼
     *@param iPrpInsuredIntegralSchema PrpInsuredIntegralSchema
     *@throws Exception
     */
    public void setArr(PrpInsuredIntegralSchema iPrpInsuredIntegralSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpInsuredIntegralSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpInsuredIntegralSchema��¼
     *@param index �±�
     *@return һ��PrpInsuredIntegralSchema����
     *@throws Exception
     */
    public PrpInsuredIntegralSchema getArr(int index) throws Exception
    {
      PrpInsuredIntegralSchema prpInsuredIntegralSchema = null;
       try
       {
         prpInsuredIntegralSchema = (PrpInsuredIntegralSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpInsuredIntegralSchema;
     }
    /**
     *ɾ��һ��PrpInsuredIntegralSchema��¼
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
      DBPrpInsuredIntegral dbPrpInsuredIntegral = new DBPrpInsuredIntegral();
      if (iLimitCount > 0 && dbPrpInsuredIntegral.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpInsuredIntegral.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpInsuredIntegral WHERE " + iWherePart; 
        schemas = dbPrpInsuredIntegral.findByConditions(strSqlStatement);
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
      DBPrpInsuredIntegral dbPrpInsuredIntegral = new DBPrpInsuredIntegral();
      if (iLimitCount > 0 && dbPrpInsuredIntegral.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpInsuredIntegral.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpInsuredIntegral WHERE " + iWherePart; 
        schemas = dbPrpInsuredIntegral.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpInsuredIntegral dbPrpInsuredIntegral = new DBPrpInsuredIntegral();
      
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpInsuredIntegral.setSchema((PrpInsuredIntegralSchema)schemas.get(i));
        dbPrpInsuredIntegral.insert(dbpool);
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
        dbpool.open(SysConfig.CONST_INTEGRALDATASOURCE);
        dbpool.beginTransaction();
        save(dbpool);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
        throw e;
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param icertiNo certiNo
     *@return ��
     */
    public void cancel(DbPool dbpool,String icertiNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpInsuredIntegral WHERE certiNo= '" + icertiNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param icertiNo certiNo
     *@return ��
     */
    public void cancel(String icertiNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConfig.CONST_INTEGRALDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,icertiNo);
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
     * ��dbpool����certiNo��ȡ����
     *@param icertiNo certiNo
     *@return ��
     */
    public void getData(String icertiNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConfig.CONST_INTEGRALDATASOURCE);
      getData(dbpool,icertiNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����certiNo��ȡ����
     *@param dbpool ���ӳ�
     *@param icertiNo certiNo
     *@return ��
     */
    public void getData(DbPool dbpool,String icertiNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "certiNo= '" + icertiNo + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
