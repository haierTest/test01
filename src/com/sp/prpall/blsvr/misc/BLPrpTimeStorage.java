package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpTimeStorage;
import com.sp.prpall.schema.PrpTimeStorageSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

public class BLPrpTimeStorage {
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpTimeStorage(){
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
     *����һ��PrpTimeRegisterSchema��¼
     *@param iPrpTimeRegisterSchema PrpTimeRegisterSchema
     *@throws Exception
     */
    public void setArr(PrpTimeStorageSchema iPrpTimeStorageSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpTimeStorageSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpTimeStorageSchema��¼
     *@param index �±�
     *@return һ��PrpTimeStorageSchema����
     *@throws Exception
     */
    public PrpTimeStorageSchema getArr(int index) throws Exception
    {
     PrpTimeStorageSchema PrpTimeStorageSchema = null;
       try
       {
        PrpTimeStorageSchema = (PrpTimeStorageSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return PrpTimeStorageSchema;
     }
    /**
     *ɾ��һ��PrpTimeStorageSchema��¼
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
      DBPrpTimeStorage dbPrpTimeStorage = new DBPrpTimeStorage();
      if (iLimitCount > 0 && dbPrpTimeStorage.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpTimeStorage.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpTimeStorage WHERE " + iWherePart; 
        schemas = dbPrpTimeStorage.findByConditions(strSqlStatement);
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
      DBPrpTimeStorage dbPrpTimeStorage = new DBPrpTimeStorage();
      
      if (iLimitCount > 0 && dbPrpTimeStorage.getCount(dbpool, iWherePart) > iLimitCount)
      
      {
        throw new UserException(-98,-1003,"BLPrpTimeStorage.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpTimeStorage WHERE " + iWherePart; 
        schemas = dbPrpTimeStorage.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpTimeStorage dbPrpTimeStorage = new DBPrpTimeStorage();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpTimeStorage.setSchema((PrpTimeStorageSchema)schemas.get(i));
      dbPrpTimeStorage.insert(dbpool);
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
      
      dbpool.open("platformNewDataSource");
      
      try
      {
        dbpool.beginTransaction();
        save(dbpool);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
        e.printStackTrace();
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
     *@param iProposalNo ҵ���
     *@return ��
     */
	public void cancel(DbPool dbpool, String iBusinessNo) throws Exception {

		String strSqlStatement = " DELETE FROM PrpTimeStorage WHERE BusinessNo= ?";
		dbpool.prepareInnerStatement(strSqlStatement);
		dbpool.setString(1, iBusinessNo);
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
	}
      
    /**
     * ����dbpool��ɾ������
     *@param iProposalNo ҵ���
     *@return ��
     */
    public void cancel(String iBusinessNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open("platformNewDataSource");
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iBusinessNo);
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
     * ����dbpool�ĸ��·���
     *@param iPolicyNo XX��
     *@return ��
     */
    public void update() throws Exception
    {
      DbPool dbpool = new DbPool();
        try {
        dbpool.open("platformNewDataSource");
        dbpool.beginTransaction();
        update(dbpool);
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
      *��dbpool�ĸ��·���
      *@param dbpool    ���ӳ�
      *@param iPolicyNo XX��
      *@return ��
      */
     public void update(DbPool dbpool) throws Exception
     {
    	 DBPrpTimeStorage dbPrpTimeStorage = new DBPrpTimeStorage();

       int i = 0;

       for(i = 0; i< schemas.size(); i++)
       {
    	   dbPrpTimeStorage.setSchema((PrpTimeStorageSchema)schemas.get(i));
    	   dbPrpTimeStorage.update(dbpool);
        }
     }
    /**
     * ��dbpool����XX���Ż�ȡ����
     *@param iProposalNo ҵ���
     *@return ��
     */
    public void getData(String iBusinessNo) throws Exception
 {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open("platformNewDataSource");
			getData(dbpool, iBusinessNo);

		} catch (Exception e) {
			System.out.println("fffffffffffffffffffffffffff");
			e.printStackTrace();
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}
      
    /**
     * ����dbpool����XX���Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iProposalNo ҵ���
     *@return ��
     */
    public void getData(DbPool dbpool,String iBusinessNo) throws Exception
    {
        
        
        
        
        String strWherePart = " BusinessNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iBusinessNo);
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
        DBPrpTimeStorage dbPrpTimeStorage = new DBPrpTimeStorage();
        if (iLimitCount > 0 && dbPrpTimeStorage.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpTimeStorage.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpTimeStorage WHERE " + iWherePart;
            schemas = dbPrpTimeStorage.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
