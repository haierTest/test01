package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpIntefInfo;
import com.sp.prpall.schema.PrpIntefInfoSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����PrpIntefInfo��BL��
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-06-01</p>
 * @author zhengxiaoluo
 * @version 1.0
 */
public class BLPrpIntefInfo{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpIntefInfo(){
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
     *����һ��PrpIntefInfoSchema��¼
     *@param iPrpIntefInfoSchema PrpIntefInfoSchema
     *@throws Exception
     */
    public void setArr(PrpIntefInfoSchema iPrpIntefInfoSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpIntefInfoSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpIntefInfoSchema��¼
     *@param index �±�
     *@return һ��PrpIntefInfoSchema����
     *@throws Exception
     */
    public PrpIntefInfoSchema getArr(int index) throws Exception
    {
     PrpIntefInfoSchema prpIntefInfoSchema = null;
       try
       {
        prpIntefInfoSchema = (PrpIntefInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpIntefInfoSchema;
     }
    /**
     *ɾ��һ��PrpIntefInfoSchema��¼
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
      DBPrpIntefInfo dbPrpIntefInfo = new DBPrpIntefInfo();
      if (iLimitCount > 0 && dbPrpIntefInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpIntefInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpIntefInfo WHERE " + iWherePart; 
        schemas = dbPrpIntefInfo.findByConditions(strSqlStatement);
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
      DBPrpIntefInfo dbPrpIntefInfo = new DBPrpIntefInfo();
      if (iLimitCount > 0 && dbPrpIntefInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpIntefInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpIntefInfo WHERE " + iWherePart; 
        schemas = dbPrpIntefInfo.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpIntefInfo dbPrpIntefInfo = new DBPrpIntefInfo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpIntefInfo.setSchema((PrpIntefInfoSchema)schemas.get(i));
      dbPrpIntefInfo.insert(dbpool);
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
     *@param iBusinessNo ҵ���
     *@return ��
     */
    public void cancel(DbPool dbpool,String iBusinessNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpIntefInfo WHERE BusinessNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iBusinessNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iBusinessNo ҵ���
     *@return ��
     */
    public void cancel(String iBusinessNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
     * ��dbpool����null��ȡ����
     *@param iBusinessNo ҵ���
     *@return ��
     */
    public void getData(String iBusinessNo) throws Exception
    {
    	
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			getData(dbpool, iBusinessNo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbpool.close();
		}
		
    }
      
    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param iBusinessNo ҵ���
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
        DBPrpIntefInfo dbPrpIntefInfo = new DBPrpIntefInfo();
        if (iLimitCount > 0 && dbPrpIntefInfo.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpIntefInfo.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpIntefInfo WHERE " + iWherePart;
            schemas = dbPrpIntefInfo.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
