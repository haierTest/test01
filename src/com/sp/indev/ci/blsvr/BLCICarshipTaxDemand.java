package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCICarshipTaxDemand;
import com.sp.indiv.ci.schema.CICarshipTaxDemandSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����cicarshiptaxdemand��BL��
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-06-21</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCICarshipTaxDemand{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCICarshipTaxDemand(){
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
     *����һ��CicarshiptaxdemandSchema��¼
     *@param iCicarshiptaxdemandSchema CicarshiptaxdemandSchema
     *@throws Exception
     */
    public void setArr(CICarshipTaxDemandSchema iCicarshiptaxdemandSchema) throws Exception
    {
       try
       {
         schemas.add(iCicarshiptaxdemandSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CicarshiptaxdemandSchema��¼
     *@param index �±�
     *@return һ��CicarshiptaxdemandSchema����
     *@throws Exception
     */
    public CICarshipTaxDemandSchema getArr(int index) throws Exception
    {
    	CICarshipTaxDemandSchema cicarshiptaxdemandSchema = null;
       try
       {
        cicarshiptaxdemandSchema = (CICarshipTaxDemandSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cicarshiptaxdemandSchema;
     }
    /**
     *ɾ��һ��CicarshiptaxdemandSchema��¼
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
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas���󣨰󶨱�����
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
		}catch (Exception e) 
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
      DBCICarshipTaxDemand dbCicarshiptaxdemand = new DBCICarshipTaxDemand();
      if (iLimitCount > 0 && dbCicarshiptaxdemand.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCicarshiptaxdemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Cicarshiptaxdemand WHERE " + iWherePart; 
        schemas = dbCicarshiptaxdemand.findByConditions(strSqlStatement);
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
      DBCICarshipTaxDemand dbCicarshiptaxdemand = new DBCICarshipTaxDemand();
      if (iLimitCount > 0 && dbCicarshiptaxdemand.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCicarshiptaxdemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Cicarshiptaxdemand WHERE " + iWherePart; 
        schemas = dbCicarshiptaxdemand.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCICarshipTaxDemand dbCicarshiptaxdemand = new DBCICarshipTaxDemand();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCicarshiptaxdemand.setSchema((CICarshipTaxDemandSchema)schemas.get(i));
      dbCicarshiptaxdemand.insert(dbpool);
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

      
      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
      if ("1".equals(strSwitch)) {
          dbpool.open("platformNewDataSource");
      } else {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      }
      
      
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
     *@param null null
     *@return ��
     */
    public void cancel(DbPool dbpool,String demandno) throws Exception
    {




    	String strSqlStatement = " DELETE FROM Cicarshiptaxdemand WHERE demandno= ?"; 
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, demandno);
	    dbpool.executePreparedUpdate();
	    dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param null null
     *@return ��
     */
    public void cancel(String demandno ) throws Exception
    {
      DbPool dbpool = new DbPool();

      
      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
      if ("1".equals(strSwitch)) {
          dbpool.open("platformNewDataSource");
      } else {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      }
      
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,demandno);
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
     * ��dbpool����null��ȡ����
     *@param null null
     *@return ��
     */
    public void getData(String demandno) throws Exception
    {
      DbPool dbpool = new DbPool();

    	
		try {
			
			String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
			if ("1".equals(strSwitch)) {
				dbpool.open("platformNewDataSource");
			} else {
				dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			}
			
			getData(dbpool,demandno);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbpool.close();
		}
		
    }
      
    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param null null
     *@return ��
     */
    public void getData(DbPool dbpool,String demandno) throws Exception
    {
        
        
        
        
        String strWherePart = " demandno= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(demandno);
        query(dbpool, strWherePart, arrWhereValue, 0);
        
    }
    
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@author yangxiaodong 20100602
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
        DBCICarshipTaxDemand dbCICarshipTaxDemand = new DBCICarshipTaxDemand();
        if (iLimitCount > 0 && dbCICarshipTaxDemand.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCICarshipTaxDemand.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CICarshipTaxDemand WHERE " + iWherePart;
            schemas = dbCICarshipTaxDemand.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param null null
     *@return ��
     */
    public void cancelByProposalno(DbPool dbpool,String Proposalno) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM CICarshipTaxDemand WHERE ProposalNo= '" + Proposalno + "'";
      
      dbpool.delete(strSqlStatement);
    }
    
    /**
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param iProposalNo XX����
     **@param iProposalNo XX��ѯ��
     *@return ��
     */
    public void cancel(DbPool dbpool,String iProposalNo, String iDemandNo) throws Exception
    {
      String strSqlStatement = "";

      strSqlStatement = " DELETE FROM CICarshipTaxDemand WHERE ProposalNo= '" + iProposalNo + "'" + 
      					" AND DemandNo != '" + iDemandNo + "' ";
      
      dbpool.delete(strSqlStatement);
    }
    
    /**
     *��dbpool��update����
     *@param dbpool    ���ӳ�
     *@param iProposalNo XX����
     **@param iDemandNo XX��ѯ��
     *@return ��
     *@author zhaoyingchao-ghq
     *@description ϵͳ�Ż������XX�޸�XXXXX��ʱ��XX��ѯ������
     */
    public void update(DbPool dbpool,String iProposalNo, String iDemandNo) throws Exception
    {
      String strSqlStatement = "";

      strSqlStatement = " UPDATE CICarshipTaxDemand SET ProposalNo = '' " +  
      					" WHERE ProposalNo = '" + iProposalNo + "' and DemandNo != '" + iDemandNo + "' ";
      
      dbpool.update(strSqlStatement);
    }
    
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void ProposalUpdate(DbPool dbpool, String iProposalNo, String iDemandNo) 
    	throws Exception
    {
      String strSqlStatement = "";

      strSqlStatement = " UPDATE CICarshipTaxDemand SET ProposalNo = '" + iProposalNo + "' " +  
      					" WHERE DemandNo = '" + iDemandNo + "' ";
      
      dbpool.delete(strSqlStatement);
    }
    
    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param null null
     *@return ��
     */
    public void getDataByProposalno(DbPool dbpool,String Proposalno) throws Exception
    {
      String strWherePart = "";
      strWherePart = "PROPOSALNO= '" + Proposalno + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param null null
     *@return ��
     */
    public void getDataByProposalno(String Proposalno) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
      if ("1".equals(strSwitch)) {
          dbpool.open("platformNewDataSource");
      } else {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      }
      
      getDataByProposalno(dbpool,Proposalno);
      dbpool.close();
    }
    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param null null
     *@return ��
     */
    public void getDataByPolicyno(DbPool dbpool,String Policyno) throws Exception
    {
      String strWherePart = "";
      strWherePart = "POLICYNO= '" + Policyno + "'";
      query(dbpool,strWherePart,0);
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
