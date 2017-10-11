package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpInquire;
import com.sp.prpall.schema.PrpInquireSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����PrpInquire��BL��
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>@createdate 2005-05-18</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpInquire{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */
    public BLPrpInquire(){
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
     *����һ��PrpInquireSchema��¼
     *@param iPrpInquireSchema PrpInquireSchema
     *@throws Exception
     */
    public void setArr(PrpInquireSchema iPrpInquireSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpInquireSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpInquireSchema��¼
     *@param index �±�
     *@return һ��PrpInquireSchema����
     *@throws Exception
     */
    public PrpInquireSchema getArr(int index) throws Exception
    {
     PrpInquireSchema prpInquireSchema = null;
       try
       {
        prpInquireSchema = (PrpInquireSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpInquireSchema;
     }
    /**
     *ɾ��һ��PrpInquireSchema��¼
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
      DBPrpInquire dbPrpInquire = new DBPrpInquire();
      if (iLimitCount > 0 && dbPrpInquire.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpInquire.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpInquire WHERE " + iWherePart;
        schemas = dbPrpInquire.findByConditions(strSqlStatement);
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
      DBPrpInquire dbPrpInquire = new DBPrpInquire();
      if (iLimitCount > 0 && dbPrpInquire.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpInquire.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpInquire WHERE " + iWherePart;
        schemas = dbPrpInquire.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpInquire dbPrpInquire = new DBPrpInquire();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpInquire.setSchema((PrpInquireSchema)schemas.get(i));
      dbPrpInquire.insert(dbpool);
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
     *@param null null
     *@return ��
     */
    public void cancel(DbPool dbpool,String CertiNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpInquire WHERE CertiNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, CertiNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }

    /**
     * ����dbpool��ɾ������
     *@param null null
     *@return ��
     */
    public void cancel(String CertiNo ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,null);
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
     * ��dbpool����null��ȡ����
     *@param null null
     *@return ��
     */
    public void getData(String CertiNo) throws Exception
    {
        DbPool dbpool = new DbPool();

        try {
  		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  		getData(dbpool, null);
  	} catch (Exception e) {
  		
  	}finally {      
  	dbpool.close();
      }

      }

    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param null null
     *@return ��
     */
    public PrpInquireSchema Evaluate(PrpInquireSchema iPrpInquireSchema,String strPolicyNo) throws Exception
    {
    	PrpInquireSchema prpInquireSchema = new PrpInquireSchema();
    	 prpInquireSchema.setCertiNo(strPolicyNo);
    	 if(!iPrpInquireSchema.getAnswerContent().equals("")){
    	 prpInquireSchema.setAnswerContent(iPrpInquireSchema.getAnswerContent());
    	 }
    	 if(!iPrpInquireSchema.getColumnCode().equals("")){
    	 prpInquireSchema.setColumnCode(iPrpInquireSchema.getColumnCode());
    	 }
    	 if(!iPrpInquireSchema.getDetailno().equals("")){
    	 prpInquireSchema.setDetailno(iPrpInquireSchema.getDetailno());
    	 }
    	 if(!iPrpInquireSchema.getFlag().equals("")){
    	 prpInquireSchema.setFlag(iPrpInquireSchema.getFlag());
    	 }
    	 if(!iPrpInquireSchema.getNumeric().equals("")){
    	 prpInquireSchema.setNumeric(iPrpInquireSchema.getNumeric());
    	 }
    	 if(!iPrpInquireSchema.getQuestionContent().equals("")){
    	 prpInquireSchema.setQuestionContent(iPrpInquireSchema.getQuestionContent());
         }
    	 if(!iPrpInquireSchema.getRiskCode().equals("")){
    	 prpInquireSchema.setRiskCode(iPrpInquireSchema.getRiskCode());
         }
    	 if(!iPrpInquireSchema.getSerialno().equals("")){
    	 prpInquireSchema.setSerialno(iPrpInquireSchema.getSerialno());
         }

        return prpInquireSchema;
    }
    public void getData(DbPool dbpool,String CertiNo) throws Exception
    {
        
        
        
        
        String strWherePart = " CertiNo=? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(CertiNo);
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
      DBPrpInquire dbPrpInquire = new DBPrpInquire();
      if (iLimitCount > 0 && dbPrpInquire.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
      {
          throw new UserException(-98,-1003,"BLPrpInquire.query");
      }else
      {
          initArr();
          strSqlStatement = " SELECT * FROM PrpInquire WHERE " + iWherePart;
          schemas = dbPrpInquire.findByConditions(dbpool,strSqlStatement,iWhereValue);
      }
  }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
