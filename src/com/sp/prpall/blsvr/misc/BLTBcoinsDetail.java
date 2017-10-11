package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBTBcoinsDetail;
import com.sp.prpall.schema.TBcoinsDetailSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

public class BLTBcoinsDetail {
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLTBcoinsDetail(){
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
     *����һ��PrpTcoinsDetailSchema��¼
     *@param iPrpTcoinsDetailSchema PrpTcoinsDetailSchema
     *@throws Exception
     */
    public void setArr(TBcoinsDetailSchema iPrpTcoinsDetailSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpTcoinsDetailSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpTcoinsDetailSchema��¼
     *@param index �±�
     *@return һ��PrpTcoinsDetailSchema����
     *@throws Exception
     */
    public TBcoinsDetailSchema getArr(int index) throws Exception
    {
    	TBcoinsDetailSchema prpTcoinsDetailSchema = null;
       try
       {
        prpTcoinsDetailSchema = (TBcoinsDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpTcoinsDetailSchema;
     }
    /**
     *ɾ��һ��PrpTcoinsDetailSchema��¼
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
       this.query(iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBTBcoinsDetail dbPrpTcoinsDetail = new DBTBcoinsDetail();
      if (iLimitCount > 0 && dbPrpTcoinsDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpTcoinsDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM tb_coinsdetail WHERE " + iWherePart; 
        schemas = dbPrpTcoinsDetail.findByConditions(strSqlStatement);
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
       this.query(dbpool,iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBTBcoinsDetail dbPrpTcoinsDetail = new DBTBcoinsDetail();
      if (iLimitCount > 0 && dbPrpTcoinsDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpTcoinsDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM tb_coinsdetail WHERE " + iWherePart; 
        schemas = dbPrpTcoinsDetail.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
    	DBTBcoinsDetail dbPrpTcoinsDetail = new DBTBcoinsDetail();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpTcoinsDetail.setSchema((TBcoinsDetailSchema)schemas.get(i));
      dbPrpTcoinsDetail.insert(dbpool);
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
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param iProposalNo XX����
     *@return ��
     */
    public void cancel(DbPool dbpool,String iProposalNo) throws Exception
    {

    	String strSqlStatement = " DELETE FROM tb_coinsdetail WHERE PolicyNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iProposalNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iProposalNo XX����
     *@return ��
     */
    public void cancel(String iProposalNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,iProposalNo);
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
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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

        String strWherePart = " PolicyNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iProposalNo);
        query(dbpool, strWherePart, arrWhereValue, 0);

    }

    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@author wangchuanzhong 20100603
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
        DBTBcoinsDetail dbPrpTcoinsDetail = new DBTBcoinsDetail();
        if (iLimitCount > 0 && dbPrpTcoinsDetail.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpTcoinsDetail.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM tb_coinsdetail WHERE " + iWherePart;
            schemas = dbPrpTcoinsDetail.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
