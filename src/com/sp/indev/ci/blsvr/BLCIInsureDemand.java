package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCIInsureDemand;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����XX��ѯ����-CIInsureDemand��BL��
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCIInsureDemand{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCIInsureDemand(){
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
     *����һ��CIInsureDemandSchema��¼
     *@param iCIInsureDemandSchema CIInsureDemandSchema
     *@throws Exception
     */
    public void setArr(CIInsureDemandSchema iCIInsureDemandSchema) throws Exception
    {
       try
       {
         schemas.add(iCIInsureDemandSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CIInsureDemandSchema��¼
     *@param index �±�
     *@return һ��CIInsureDemandSchema����
     *@throws Exception
     */
    public CIInsureDemandSchema getArr(int index) throws Exception
    {
     CIInsureDemandSchema cIInsureDemandSchema = null;
       try
       {
        cIInsureDemandSchema = (CIInsureDemandSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsureDemandSchema;
     }
    /**
     *ɾ��һ��CIInsureDemandSchema��¼
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
     *���ݲ�ѯSQL���ð󶨱�������
     *@param iWherePart ��ѯ����(���������־�)
     *@param iWhereValue ��ѯ����ֵ
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart,ArrayList iWhereValue) throws UserException,Exception
    {
   	   DbPool dbpool = new DbPool();
       try {
           
           String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
           if ("1".equals(strSwitch)) {
               dbpool.open("platformNewDataSource");            
           } else {
               dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
           }
           
           query(dbpool, iWherePart, iWhereValue, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
       } catch (Exception e) 
       {
       	throw e;
       } finally {
           dbpool.close();
       }    
    }
    /**
     * ����XX�Ż�ȡ����,���ð󶨱�������
     *@param dbpool ���ӳ�
     *@param iPolicyNo XX��
     *@return ��
     */
    public void query(int iLimitCount,String iPolicyNo) throws Exception
    {
    	DbPool dbpool = new DbPool();
        try {
            
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");            
            } else {
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            
            String strWherePart = " PolicyNo= ? ";
            ArrayList arrWhereValue = new ArrayList();
            arrWhereValue.add(iPolicyNo);
            query(dbpool, strWherePart, arrWhereValue, iLimitCount);
        } catch (Exception e) 
        {
        	throw e;
        } finally {
            dbpool.close();
        }    
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
      DBCIInsureDemand dbCIInsureDemand = new DBCIInsureDemand();
      if (iLimitCount > 0 && dbCIInsureDemand.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemand WHERE " + iWherePart; 
        schemas = dbCIInsureDemand.findByConditions(strSqlStatement);
      }
    }
    
    
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas���󣬴���ʷ��Ϣ���ȡ
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart) throws UserException,Exception
    {
       this.queryHistory(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�����󶨱���
     *@param iWherePart ��ѯ����(���������־�)
     *@param iWhereValue ��ѯ����ֵ
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart,ArrayList iWhereValue) throws UserException,Exception
    {
       DbPool dbpool = new DbPool();
       
       try {
           
           String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
           if ("1".equals(strSwitch)) {
               dbpool.open("platformNewDataSource");            
           } else {
               dbpool.open(SysConfig.CONST_HISTORYDATASOURCE);
           }
           
           query(dbpool,iWherePart,iWhereValue,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
        }catch (Exception e){
           	throw e;
        }finally{
            dbpool.close();
        } 
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas���󣬴���ʷ��Ϣ���ȡ
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBCIInsureDemand dbCIInsureDemand = new DBCIInsureDemand();
      if (iLimitCount > 0 && dbCIInsureDemand.getCountHistory(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemand WHERE " + iWherePart; 
        schemas = dbCIInsureDemand.findByConditionsHistory(strSqlStatement);
      }
    }
       
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas�����ȴ��������ȡ�����Ϣ�����δȡ�����ٴ���ʷ��Ϣ���ȡ��
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart,int iLimitCount) throws UserException,Exception
    {
       
       this.query(iWherePart,iLimitCount);
       
       if(this.getSize() == 0){
    	   this.queryHistory(iWherePart,iLimitCount);
       }
    }
    
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas�����ȴ��������ȡ�����Ϣ�����δȡ�����ٴ���ʷ��Ϣ���ȡ��
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart) throws UserException,Exception
    {
       
       this.query(iWherePart);
       
       if(this.getSize() == 0){
    	   this.queryHistory(iWherePart);
       }
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�����󶨱�����
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart,ArrayList iWhereValue) throws UserException,Exception
    {
       
       this.query(iWherePart,iWhereValue);
       
       if(this.getSize() == 0){
    	   this.queryHistory(iWherePart,iWhereValue);
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
      DBCIInsureDemand dbCIInsureDemand = new DBCIInsureDemand();
      if (iLimitCount > 0 && dbCIInsureDemand.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIInsureDemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIInsureDemand WHERE " + iWherePart; 
        schemas = dbCIInsureDemand.findByConditions(dbpool,strSqlStatement);
      }
    }
    
    public void updateByProposalNo(DbPool dbpool, String strWhere) throws Exception
    {
    	DBCIInsureDemand dbCIInsureDemand = new DBCIInsureDemand();
        
        int i = 0;
        
        for(i = 0; i< schemas.size(); i++)
        {
        dbCIInsureDemand.setSchema((CIInsureDemandSchema)schemas.get(i));
        dbCIInsureDemand.update(dbpool, strWhere);
        }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIInsureDemand dbCIInsureDemand = new DBCIInsureDemand();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCIInsureDemand.setSchema((CIInsureDemandSchema)schemas.get(i));
      dbCIInsureDemand.insert(dbpool);
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
      
        try {
            
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpool.open("platformNewDataSource");            
            } else {
                dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            }
            
        dbpool.beginTransaction();
        save(dbpool);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally {
        dbpool.close();
      }
    }
    
    
    
    
    
    /**
     * ��dbpool����XX���Ż�ȡ����
     *@param iProposalNo XX����
     *@return ��
     */
      public void getData(String iProposalNo) throws Exception
      {
          DbPool dbpool = new DbPool();
          
          try {
              
              String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
              if ("1".equals(strSwitch)) {
                  dbpool.open("platformNewDataSource");            
              } else {
                  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
              }
              
              getData(dbpool, iProposalNo);
          } catch (Exception e) {
          } finally {
              dbpool.close();
          }
          
      }

    /**
     * ����dbpool����XX���Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iProposalNo XX����
     *@return ��
     */
    public void getData(DbPool dbpool,String iProposalNo) throws Exception
    {
      
      
      
      
      String strWherePart = " ProposalNo= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iProposalNo);
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
        DBCIInsureDemand dbCIInsureDemand = new DBCIInsureDemand();
        if (iLimitCount > 0 && dbCIInsureDemand.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIInsureDemand.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIInsureDemand WHERE " + iWherePart;
            schemas = dbCIInsureDemand.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    public void update(DbPool dbpool,String iDemandNo,String iProconfirmSequenceNo,String iStartDate,String iEndDate) throws Exception
    {
      
      String strSqlStatement = "";

      strSqlStatement = " UPDATE CIInsureDemand SET ProconfirmSequenceNo = '" + iProconfirmSequenceNo + "',ProconfirmStartDate = to_date('" + 
                        iStartDate +"','yyyy-mm-dd hh24:mi:ss'),ProconfirmEndDate = to_date('"+ iEndDate +"','yyyy-mm-dd hh24:mi:ss')" +
      					" WHERE DemandNo = '" + iDemandNo + "' ";
      dbpool.delete(strSqlStatement);
    }
    public void updateFlag(DbPool dbpool) throws Exception
    {
      String strSqlStatement = "";
      strSqlStatement = "Update CIInsureDemand Set Flag = '" + this.getArr(0).getFlag() + "' Where DemandNo = '" + this.getArr(0).getDemandNo()+ "'";
      dbpool.update(strSqlStatement);
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
