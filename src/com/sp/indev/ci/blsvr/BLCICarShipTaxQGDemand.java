package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCICarShipTaxQGDemand;
import com.sp.indiv.ci.schema.CICarShipTaxQGDemandSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����CICarShipTaxQGDemand-ȫ������˰��������XX��ѯ���BL��
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-04-09</p>
 * @author Zhouxianli(JavaTools v1.0)
 * @updateauthor yangkun(JavaTools v1.1 - v1.2)
 * @lastversion v1.2
 */
public class BLCICarShipTaxQGDemand{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCICarShipTaxQGDemand(){
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
     *����һ��CICarShipTaxQGDemandSchema��¼
     *@param iCICarShipTaxQGDemandSchema CICarShipTaxQGDemandSchema
     *@throws Exception
     */
    public void setArr(CICarShipTaxQGDemandSchema iCICarShipTaxQGDemandSchema) throws Exception
    {
       try
       {
         schemas.add(iCICarShipTaxQGDemandSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CICarShipTaxQGDemandSchema��¼
     *@param index �±�
     *@return һ��CICarShipTaxQGDemandSchema����
     *@throws Exception
     */
    public CICarShipTaxQGDemandSchema getArr(int index) throws Exception
    {
      CICarShipTaxQGDemandSchema ciCarShipTaxQGDemandSchema = null;
       try
       {
        ciCarShipTaxQGDemandSchema = (CICarShipTaxQGDemandSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return ciCarShipTaxQGDemandSchema;
     }
    /**
     *ɾ��һ��CICarShipTaxQGDemandSchema��¼
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
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBCICarShipTaxQGDemand dbCICarShipTaxQGDemand = new DBCICarShipTaxQGDemand();
      if (iLimitCount > 0 && dbCICarShipTaxQGDemand.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICarShipTaxQGDemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICarShipTaxQGDemand WHERE " + iWherePart; 
        schemas = dbCICarShipTaxQGDemand.findByConditions(strSqlStatement);
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
      DBCICarShipTaxQGDemand dbCICarShipTaxQGDemand = new DBCICarShipTaxQGDemand();
      if (iLimitCount > 0 && dbCICarShipTaxQGDemand.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICarShipTaxQGDemand.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICarShipTaxQGDemand WHERE " + iWherePart; 
        schemas = dbCICarShipTaxQGDemand.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCICarShipTaxQGDemand dbCICarShipTaxQGDemand = new DBCICarShipTaxQGDemand();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCICarShipTaxQGDemand.setSchema((CICarShipTaxQGDemandSchema)schemas.get(i));
      dbCICarShipTaxQGDemand.insert(dbpool);
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
     *@param iPolicyNo XX��
     *@return ��
     */
    public void cancel(DbPool dbpool,String iDemandNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM CICarShipTaxQGDemand WHERE DemandNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iDemandNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();	
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iPolicyNo XX��
     *@return ��
     */
    public void cancel(String iDemandNo ) throws Exception
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
     * ��dbpool����XX�Ż�ȡ����
     *@param iPolicyNo XX��
     *@return ��
     */
    public void getData(String iDemandNo) throws Exception
    {
      DbPool dbpool = new DbPool();
  	  
		try {
		      
		      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
		      if ("1".equals(strSwitch)) {
		          dbpool.open("platformNewDataSource");
		      } else {
		          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
		      }
		      
			getData(dbpool,iDemandNo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbpool.close();
		}
		
    }
      
    /**
     * ����dbpool����XX�Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iPolicyNo XX��
     *@return ��
     */
    public void getData(DbPool dbpool,String iDemandNo) throws Exception
    {
        
        
        
        
        
        String strWherePart = " DemandNo=? ";
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
        DBCICarShipTaxQGDemand dbCICarShipTaxQGDemand = new DBCICarShipTaxQGDemand();
        if (iLimitCount > 0 && dbCICarShipTaxQGDemand.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCICarShipTaxQGDemand.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CICarShipTaxQGDemand WHERE " + iWherePart;
            schemas = dbCICarShipTaxQGDemand.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param null null
     *@return ��
     */
    public void cancelByProposalno(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM CICarShipTaxQGDemand WHERE ProposalNo= '" + iProposalNo + "'";
      
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

      strSqlStatement = " DELETE FROM CICarShipTaxQGDemand WHERE ProposalNo= '" + iProposalNo + "'" + 
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

      strSqlStatement = " UPDATE CICarShipTaxQGDemand SET ProposalNo = '' " +  
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

      strSqlStatement = " UPDATE CICarShipTaxQGDemand SET ProposalNo = '" + iProposalNo + "' " +  
      					" WHERE DemandNo = '" + iDemandNo + "' ";
      
      dbpool.delete(strSqlStatement);
    }
    
    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param null null
     *@return ��
     */
    public void getDataByProposalNo(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "ProposalNo = '" + iProposalNo + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param null null
     *@return ��
     */
    public void getDataByProposalNo(String iProposalNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
      if ("1".equals(strSwitch)) {
          dbpool.open("platformNewDataSource");
      } else {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      }
      
      getDataByProposalNo(dbpool,iProposalNo);
      dbpool.close();
    }
    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param null null
     *@return ��
     */
    public void getDataByPolicyNo(DbPool dbpool,String iPolicyNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "PolicyNo = '" + iPolicyNo + "'";
      query(dbpool,strWherePart,0);
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
