package com.sp.prpall.blsvr.misc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpdMotorcadeExpenseDetail;
import com.sp.prpall.schema.PrpdMotorcadeExpenseDetailSchema;

public class BLPrpdMotorcadeExpenseDetail{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpdMotorcadeExpenseDetail(){
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
     *����һ��PrpdMotorcadeExpenseDetailSchema��¼
     *@param PrpdMotorcadeExpenseDetailSchema PrpdMotorcadeExpenseDetailSchema
     *@throws Exception
     */
    public void setArr(PrpdMotorcadeExpenseDetailSchema iPrpdMotorcadeExpenseDetailSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpdMotorcadeExpenseDetailSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpdMotorcadeExpenseDetailSchema��¼
     *@param index �±�
     *@return һ��PrpdMotorcadeExpenseDetailSchema����
     *@throws Exception
     */
    public PrpdMotorcadeExpenseDetailSchema getArr(int index) throws Exception
    {
        PrpdMotorcadeExpenseDetailSchema prpdMotorcadeExpenseDetailSchema = null;
       try
       {
         prpdMotorcadeExpenseDetailSchema = (PrpdMotorcadeExpenseDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpdMotorcadeExpenseDetailSchema;
     }
    /**
     *ɾ��һ��PrpdMotorcadeExpenseDetailSchema��¼
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
      DBPrpdMotorcadeExpenseDetail dbPrpdMotorcadeExpenseDetail = new DBPrpdMotorcadeExpenseDetail();
      if (iLimitCount > 0 && dbPrpdMotorcadeExpenseDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpdMotorcadeExpenseDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpdMotorcadeExpenseDetail WHERE " + iWherePart; 
        schemas = dbPrpdMotorcadeExpenseDetail.findByConditions(strSqlStatement);
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
      DBPrpdMotorcadeExpenseDetail dbPrpdMotorcadeExpenseDetail = new DBPrpdMotorcadeExpenseDetail();
      if (iLimitCount > 0 && dbPrpdMotorcadeExpenseDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpdMotorcadeExpenseDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpdMotorcadeExpenseDetail WHERE " + iWherePart; 
        schemas = dbPrpdMotorcadeExpenseDetail.findByConditions(dbpool,strSqlStatement);
      }
    }
    
      /**
       *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
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
          DBPrpdMotorcadeExpenseDetail dbPrpdMotorcadeExpenseDetail = new DBPrpdMotorcadeExpenseDetail();
          if (iLimitCount > 0 && dbPrpdMotorcadeExpenseDetail.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
          {
              throw new UserException(-98,-1003,"BLPrpdMotorcadeExpenseDetail.query");
          }else
          {
              initArr();
              strSqlStatement = " SELECT * FROM PrpdMotorcadeExpenseDetail WHERE " + iWherePart;
              schemas = dbPrpdMotorcadeExpenseDetail.findByConditions(dbpool,strSqlStatement,iWhereValue);
          }
      }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpdMotorcadeExpenseDetail dbPrpdMotorcadeExpenseDetail = new DBPrpdMotorcadeExpenseDetail();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpdMotorcadeExpenseDetail.setSchema((PrpdMotorcadeExpenseDetailSchema)schemas.get(i));
        dbPrpdMotorcadeExpenseDetail.insert(dbpool);
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
     *@param iContractNo CONTRACTNO
     *@return ��
     */
    public void cancel(DbPool dbpool,String iContractNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpdMotorcadeExpenseDetail WHERE ContractNo= '" + iContractNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iContractNo CONTRACTNO
     *@return ��
     */
    public void cancel(String iContractNo ) throws Exception
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
        cancel(dbpool,iContractNo);
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
     * ��dbpool����CONTRACTNO��ȡ����
     *@param iContractNo CONTRACTNO
     *@return ��
     */
    public void getData(String iContractNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iContractNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����CONTRACTNO��ȡ����
     *@param dbpool ���ӳ�
     *@param iContractNo CONTRACTNO
     *@return ��
     */
    public void getData(DbPool dbpool,String iContractNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "ContractNo= '" + iContractNo + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     *��dbpool��update����
     *@param ��
     *@return ��
     */
    public void update(DbPool dbpool) throws Exception{
        DBPrpdMotorcadeExpenseDetail dbPrpdMotorcadeExpenseDetail = new DBPrpdMotorcadeExpenseDetail();
        int i = 0;
        for(i = 0; i< schemas.size(); i++){
            dbPrpdMotorcadeExpenseDetail.setSchema((PrpdMotorcadeExpenseDetailSchema)schemas.get(i));
            dbPrpdMotorcadeExpenseDetail.update(dbpool);
        }
    }
    
   /**
    *����dbpool�ĸ��·���
    *@param ��
    *@return ��
    */
    public void update() throws Exception{
        DbPool dbpool = new DbPool();
        try{
        	
        	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
        		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
        	}else{       		
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        	}
        	
          dbpool.beginTransaction();
          update(dbpool);
          dbpool.commitTransaction();
        }catch (Exception e){
          dbpool.rollbackTransaction();
          throw e;
        }finally{
          dbpool.close();
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
