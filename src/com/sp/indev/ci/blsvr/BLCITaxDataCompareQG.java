package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCITaxDataCompareQG;
import com.sp.indiv.ci.schema.CITaxDataCompareQGSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * ����CITaxDataCompareQG��BL��
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-06-16</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCITaxDataCompareQG{
    private Vector schemas = new Vector();
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    /**
     * ���캯��
     */       
    public BLCITaxDataCompareQG(){
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
     *����һ��CITaxDataCompareQGSchema��¼
     *@param iCITaxDataCompareQGSchema CITaxDataCompareQGSchema
     *@throws Exception
     */
    public void setArr(CITaxDataCompareQGSchema iCITaxDataCompareQGSchema) throws Exception
    {
       try
       {
         schemas.add(iCITaxDataCompareQGSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CITaxDataCompareQGSchema��¼
     *@param index �±�
     *@return һ��CITaxDataCompareQGSchema����
     *@throws Exception
     */
    public CITaxDataCompareQGSchema getArr(int index) throws Exception
    {
     CITaxDataCompareQGSchema cITaxDataCompareQGSchema = null;
       try
       {
        cITaxDataCompareQGSchema = (CITaxDataCompareQGSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cITaxDataCompareQGSchema;
     }
    /**
     *ɾ��һ��CITaxDataCompareQGSchema��¼
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
      DBCITaxDataCompareQG dbCITaxDataCompareQG = new DBCITaxDataCompareQG();
      if (iLimitCount > 0 && dbCITaxDataCompareQG.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCITaxDataCompareQG.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT TYPE,CONFIRMNO,TAXTOTALPAYIA,TAXTOTALPAYLT,DECLARESTATUSIA,DECLARESTATUSLT,TAXTOTALPAYIC,DECLARESTATUSIC,"
            +" ISAMENDMENT,EXTENDCHAR1,EXTENDCHAR2,to_char(STARTDATE,'YYYY-MM-DD') STARTDATE,to_char(ENDDATE,'YYYY-MM-DD') ENDDATE,COMPARENO"
            +" FROM CITaxDataCompareQG WHERE " + iWherePart; 
        schemas = dbCITaxDataCompareQG.findByConditions(strSqlStatement);
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
      DBCITaxDataCompareQG dbCITaxDataCompareQG = new DBCITaxDataCompareQG();
      if (iLimitCount > 0 && dbCITaxDataCompareQG.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCITaxDataCompareQG.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT TYPE,CONFIRMNO,TAXTOTALPAYIA,TAXTOTALPAYLT,DECLARESTATUSIA,DECLARESTATUSLT,TAXTOTALPAYIC,DECLARESTATUSIC,"
            +" ISAMENDMENT,EXTENDCHAR1,EXTENDCHAR2,to_char(STARTDATE,'YYYY-MM-DD') STARTDATE,to_char(ENDDATE,'YYYY-MM-DD') ENDDATE,COMPARENO"
            +" FROM CITaxDataCompareQG WHERE " + iWherePart; 
        schemas = dbCITaxDataCompareQG.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCITaxDataCompareQG dbCITaxDataCompareQG = new DBCITaxDataCompareQG();
      
      int i = 0;
      
      logger.info(schemas.size());
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCITaxDataCompareQG.setSchema((CITaxDataCompareQGSchema)schemas.get(i));
      dbCITaxDataCompareQG.insert(dbpool);
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
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param iCompareNo �ԱȺ�
     *@return ��
     */
    public void cancel(DbPool dbpool,String iCompareNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM CITaxDataCompareQG WHERE CompareNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iCompareNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iCompareNo �ԱȺ�
     *@return ��
     */
    public void cancel(String iCompareNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iCompareNo);
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
     * ��dbpool����iCompareNo��ȡ����
     *@param iCompareNo �ԱȺ�
     *@return ��
     */
    public void getData(String iCompareNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iCompareNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����iCompareNo��ȡ����
     *@param dbpool ���ӳ�
     *@param iCompareNo �ԱȺ�
     *@return ��
     */
    public void getData(DbPool dbpool,String iCompareNo) throws Exception
    {
        
        
        
        
        String strWherePart = " CompareNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iCompareNo);
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
        DBCITaxDataCompareQG dbCITaxDataCompareQG = new DBCITaxDataCompareQG();
        if (iLimitCount > 0 && dbCITaxDataCompareQG.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCITaxDataCompareQG.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CITaxDataCompareQG WHERE " + iWherePart;
            schemas = dbCITaxDataCompareQG.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
