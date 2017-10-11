package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.prpall.dbsvr.misc.DBCYBComPara;
import com.sp.prpall.schema.CYBComParaSchema;

/**
 * ����CYBComPara��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>@createdate 2012-08-03</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLCYBComPara{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCYBComPara(){
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
     *����һ��CYBComParaSchema��¼
     *@param iCYBComParaSchema CYBComParaSchema
     *@throws Exception
     */
    public void setArr(CYBComParaSchema iCYBComParaSchema) throws Exception
    {
      try
      {
        schemas.add(iCYBComParaSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��CYBComParaSchema��¼
     *@param index �±�
     *@return һ��CYBComParaSchema����
     *@throws Exception
     */
    public CYBComParaSchema getArr(int index) throws Exception
    {
      CYBComParaSchema cYBComParaSchema = null;
       try
       {
         cYBComParaSchema = (CYBComParaSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cYBComParaSchema;
     }
    /**
     *ɾ��һ��CYBComParaSchema��¼
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
      DBCYBComPara dbCYBComPara = new DBCYBComPara();
      if (iLimitCount > 0 && dbCYBComPara.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCYBComPara.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM T_SPIS_CYBCOMPARA WHERE " + iWherePart; 
        schemas = dbCYBComPara.findByConditions(strSqlStatement);
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
      DBCYBComPara dbCYBComPara = new DBCYBComPara();
      if (iLimitCount > 0 && dbCYBComPara.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCYBComPara.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM T_SPIS_CYBCOMPARA WHERE " + iWherePart; 
        schemas = dbCYBComPara.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCYBComPara dbCYBComPara = new DBCYBComPara();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbCYBComPara.setSchema((CYBComParaSchema)schemas.get(i));
        dbCYBComPara.insert(dbpool);
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
     *��dbpool��update����
     *@param ��
     *@return ��
     */
    public void update(DbPool dbpool) throws Exception
    {
      DBCYBComPara dbCYBComPara = new DBCYBComPara();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbCYBComPara.setSchema((CYBComParaSchema)schemas.get(i));
        dbCYBComPara.update(dbpool);
      }
    }
      
    /**
     *����dbpool��update����
     *@param ��
     *@return ��
     */
    public void update() throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        update(dbpool);
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
     *��dbpool��delete����
     *@param ��
     *@return ��
     */
    public void delete(DbPool dbpool,String strComCode,String strQueryYear,String strChannelType,String strServiceArea) throws Exception
    {
      DBCYBComPara dbCYBComPara = new DBCYBComPara();
      dbCYBComPara.delete(dbpool,strComCode,strQueryYear,strChannelType,strServiceArea);
    }
    
    /**
     *����dbpool��delete����
     *@param ��
     *@return ��
     */
    public void delete(String strComCode,String strQueryYear,String strChannelType,String strServiceArea) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        delete(dbpool,strComCode,strQueryYear,strChannelType,strServiceArea);
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
     *@param iSEQNO SEQNO
     *@return ��
     */
    public void cancel(DbPool dbpool,String iSEQNO) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM T_SPIS_CYBCOMPARA WHERE SEQNO= '" + iSEQNO + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iSEQNO SEQNO
     *@return ��
     */
    public void cancel(String iSEQNO ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iSEQNO);
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
     * ��dbpool����SEQNO��ȡ����
     *@param iSEQNO SEQNO
     *@return ��
     */
    public void getData(String iSEQNO) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iSEQNO);
      dbpool.close();
    }
      
    /**
     * ����dbpool����SEQNO��ȡ����
     *@param dbpool ���ӳ�
     *@param iSEQNO SEQNO
     *@return ��
     */
    public void getData(DbPool dbpool,String iSEQNO) throws Exception
    {
      String strWherePart = "";
      strWherePart = "SEQNO= '" + iSEQNO + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
