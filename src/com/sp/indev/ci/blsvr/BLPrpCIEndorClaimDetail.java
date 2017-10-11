package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBPrpCIEndorClaimDetail;
import com.sp.indiv.ci.schema.PrpCIEndorClaimDetailSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����PrpCIEndorClaimDetail��BL��
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-08-23</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpCIEndorClaimDetail{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpCIEndorClaimDetail(){
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
     *����һ��PrpCIEndorClaimDetailSchema��¼
     *@param iPrpCIEndorClaimDetailSchema PrpCIEndorClaimDetailSchema
     *@throws Exception
     */
    public void setArr(PrpCIEndorClaimDetailSchema iPrpCIEndorClaimDetailSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpCIEndorClaimDetailSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpCIEndorClaimDetailSchema��¼
     *@param index �±�
     *@return һ��PrpCIEndorClaimDetailSchema����
     *@throws Exception
     */
    public PrpCIEndorClaimDetailSchema getArr(int index) throws Exception
    {
     PrpCIEndorClaimDetailSchema prpCIEndorClaimDetailSchema = null;
       try
       {
        prpCIEndorClaimDetailSchema = (PrpCIEndorClaimDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpCIEndorClaimDetailSchema;
     }
    /**
     *ɾ��һ��PrpCIEndorClaimDetailSchema��¼
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
      DBPrpCIEndorClaimDetail dbPrpCIEndorClaimDetail = new DBPrpCIEndorClaimDetail();
      if (iLimitCount > 0 && dbPrpCIEndorClaimDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCIEndorClaimDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCIEndorClaimDetail WHERE " + iWherePart; 
        schemas = dbPrpCIEndorClaimDetail.findByConditions(strSqlStatement);
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
      DBPrpCIEndorClaimDetail dbPrpCIEndorClaimDetail = new DBPrpCIEndorClaimDetail();
      if (iLimitCount > 0 && dbPrpCIEndorClaimDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCIEndorClaimDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCIEndorClaimDetail WHERE " + iWherePart; 
        schemas = dbPrpCIEndorClaimDetail.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpCIEndorClaimDetail dbPrpCIEndorClaimDetail = new DBPrpCIEndorClaimDetail();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpCIEndorClaimDetail.setSchema((PrpCIEndorClaimDetailSchema)schemas.get(i));
      dbPrpCIEndorClaimDetail.insert(dbpool);
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
     *@param iPolicyNo XX��
     *@return ��
     */
    public void cancel(DbPool dbpool,String Demandno) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpCIEndorClaimDetail WHERE Demandno= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, Demandno);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iPolicyNo XX��
     *@return ��
     */
    public void cancel(String iPolicyNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iPolicyNo);
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
    public void getData(String iPolicyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iPolicyNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����XX�Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iPolicyNo XX��
     *@return ��
     */
    public void getData(DbPool dbpool,String iPolicyNo) throws Exception
    {
        
        
        
        
        String strWherePart = " PolicyNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iPolicyNo);
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
        DBPrpCIEndorClaimDetail dbPrpCIEndorClaimDetail = new DBPrpCIEndorClaimDetail();
        if (iLimitCount > 0 && dbPrpCIEndorClaimDetail.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpCIEndorClaimDetail.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpCIEndorClaimDetail WHERE " + iWherePart;
            schemas = dbPrpCIEndorClaimDetail.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
