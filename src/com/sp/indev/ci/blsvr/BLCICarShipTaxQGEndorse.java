package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCICarShipTaxQGEndorse;
import com.sp.indiv.ci.schema.CICarShipTaxQGEndorseSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����CICarShipTaxQGEndorse-ȫ������˰�����������Ĳ�ѯ���BL��
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-04-09</p>
 * @author Zhouxianli(JavaTools v1.0)
 * @updateauthor yangkun(JavaTools v1.1 - v1.2)
 * @lastversion v1.2
 */
public class BLCICarShipTaxQGEndorse{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCICarShipTaxQGEndorse(){
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
     *����һ��CICarShipTaxQGEndorseSchema��¼
     *@param iCICarShipTaxQGEndorseSchema CICarShipTaxQGEndorseSchema
     *@throws Exception
     */
    public void setArr(CICarShipTaxQGEndorseSchema iCICarShipTaxQGEndorseSchema) throws Exception
    {
       try
       {
         schemas.add(iCICarShipTaxQGEndorseSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CICarShipTaxQGEndorseSchema��¼
     *@param index �±�
     *@return һ��CICarShipTaxQGEndorseSchema����
     *@throws Exception
     */
    public CICarShipTaxQGEndorseSchema getArr(int index) throws Exception
    {
       CICarShipTaxQGEndorseSchema ciCarShipTaxQGEndorseSchema = null;
       try
       {
         ciCarShipTaxQGEndorseSchema = (CICarShipTaxQGEndorseSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return ciCarShipTaxQGEndorseSchema;
     }
    /**
     *ɾ��һ��CICarShipTaxQGEndorseSchema��¼
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
      DBCICarShipTaxQGEndorse dbCICarShipTaxQGEndorse = new DBCICarShipTaxQGEndorse();
      if (iLimitCount > 0 && dbCICarShipTaxQGEndorse.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICarShipTaxQGEndorse.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICarShipTaxQGEndorse WHERE " + iWherePart; 
        schemas = dbCICarShipTaxQGEndorse.findByConditions(strSqlStatement);
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
      DBCICarShipTaxQGEndorse dbCICarShipTaxQGEndorse = new DBCICarShipTaxQGEndorse();
      if (iLimitCount > 0 && dbCICarShipTaxQGEndorse.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICarShipTaxQGEndorse.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICarShipTaxQGEndorse WHERE " + iWherePart; 
        schemas = dbCICarShipTaxQGEndorse.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCICarShipTaxQGEndorse dbCICarShipTaxQGEndorse = new DBCICarShipTaxQGEndorse();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCICarShipTaxQGEndorse.setSchema((CICarShipTaxQGEndorseSchema)schemas.get(i));
      dbCICarShipTaxQGEndorse.insert(dbpool);
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
      
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      
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
     *@param iEndorseNo XX��
     *@return ��
     */
    
    public void cancel(DbPool dbpool,String iEndorseNo) throws Exception
    {




    	String strSqlStatement = "DELETE FROM CICarShipTaxQGEndorse WHERE EndorseNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iEndorseNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
    
      
    /**
     * ����dbpool��ɾ������
     *@param iEndorseNo XX��
     *@return ��
     */
    public void cancel(String iDemandNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      try
      {
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
     * ����dbpool����XX�Ż�ȡ����
     *@param iEndorseNo XX��
     *@return ��
     */
    public void getData(String iDemandNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      getData(dbpool,iDemandNo);
      dbpool.close();
    }
      
    /**
     * ��dbpool����XX�Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iEndorseNo XX��
     *@return ��
     */
    public void getData(DbPool dbpool,String iDemandNo) throws Exception
    {
        
        
        
        
        String strWherePart = " DemandNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iDemandNo);
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
        DBCICarShipTaxQGEndorse dbCICarShipTaxQGEndorse = new DBCICarShipTaxQGEndorse();
        if (iLimitCount > 0 && dbCICarShipTaxQGEndorse.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCICarShipTaxQGEndorse.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CICarShipTaxQGEndorse WHERE " + iWherePart;
            schemas = dbCICarShipTaxQGEndorse.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
 
    /**
     * ����dbpool����XX�Ż�ȡ����
     *@param iEndorseNo XX��
     *@return ��
     */
    public void getDataByEndorseNo(String iEndorseNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      getDataByEndorseNo(dbpool,iEndorseNo);
      dbpool.close();
    }
      
    /**
     * ��dbpool����XX�Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iEndorseNo XX��
     *@return ��
     */
    public void getDataByEndorseNo(DbPool dbpool,String iEndorseNo) throws Exception
    {
    String strWherePart = "";
    strWherePart = "EndorseNo= '" + iEndorseNo + "'";
    query(dbpool,strWherePart,0);
    }
    

    /**
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param null null
     *@return ��
     */
    public void cancelByEndorseNo(DbPool dbpool,String iEndorseNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM CICarShipTaxQGDemand WHERE EndorseNo= '" + iEndorseNo + "'";
      
      String strDataSourceSwitch=SysConfig.getProperty("DB_SPLIT_SWITCH");
      DbPool dbpool1=new DbPool();
      try {
          if("1".equals(strDataSourceSwitch)){
              dbpool1.open("platformNewDataSource");
              dbpool1.beginTransaction();
              dbpool1.delete(strSqlStatement);
              dbpool1.commitTransaction();
          }else{
              dbpool.delete(strSqlStatement);
          }
      } catch (Exception e) {
          if("1".equals(strDataSourceSwitch)){
              dbpool1.rollbackTransaction();
          }
          throw e;
      }finally{
          dbpool1.close();
      }
      
    }
    
    /**
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param iProposalNo ������
     **@param iProposalNo ���Ĳ�ѯ��
     *@return ��
     */
    public void cancel(DbPool dbpool,String iEndorseNo, String iDemandNo) throws Exception
    {
      String strSqlStatement = "";

      strSqlStatement = " DELETE FROM CICarShipTaxQGDemand WHERE EndorseNo= '" + iEndorseNo + "'" + 
      					" AND DemandNo != '" + iDemandNo + "' ";
      
      String strDataSourceSwitch=SysConfig.getProperty("DB_SPLIT_SWITCH");
      DbPool dbpool1=new DbPool();
      try {
          if("1".equals(strDataSourceSwitch)){
              dbpool1.open("platformNewDataSource");
              dbpool1.beginTransaction();
              dbpool1.delete(strSqlStatement);
              dbpool1.commitTransaction();
          }else{
              dbpool.delete(strSqlStatement);
          }
      } catch (Exception e) {
          if("1".equals(strDataSourceSwitch)){
              dbpool1.rollbackTransaction();
          }
          throw e;
      }finally{
          dbpool1.close();
      }
      
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
