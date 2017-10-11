package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCICheckCarShipTaxQG;
import com.sp.indiv.ci.schema.CICheckCarShipTaxQGSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����CICheckCarShipTaxQG��BL��
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-06-02</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCICheckCarShipTaxQG{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCICheckCarShipTaxQG(){
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
     *����һ��CICheckCarShipTaxQGSchema��¼
     *@param iCICheckCarShipTaxQGSchema CICheckCarShipTaxQGSchema
     *@throws Exception
     */
    public void setArr(CICheckCarShipTaxQGSchema iCICheckCarShipTaxQGSchema) throws Exception
    {
       try
       {
         schemas.add(iCICheckCarShipTaxQGSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CICheckCarShipTaxQGSchema��¼
     *@param index �±�
     *@return һ��CICheckCarShipTaxQGSchema����
     *@throws Exception
     */
    public CICheckCarShipTaxQGSchema getArr(int index) throws Exception
    {
     CICheckCarShipTaxQGSchema cICheckCarShipTaxQGSchema = null;
       try
       {
        cICheckCarShipTaxQGSchema = (CICheckCarShipTaxQGSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cICheckCarShipTaxQGSchema;
     }
    /**
     *ɾ��һ��CICheckCarShipTaxQGSchema��¼
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
      DBCICheckCarShipTaxQG dbCICheckCarShipTaxQG = new DBCICheckCarShipTaxQG();
      if (iLimitCount > 0 && dbCICheckCarShipTaxQG.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICheckCarShipTaxQG.query");
      }
      else
      {
        initArr();
        
        strSqlStatement = " Select checkNo,comCode,to_char(startDate,'yyyy-mm-dd') startDate,to_char(endDate,'yyyy-mm-dd') endDate, "
            +" taxDeclareQueryNo,taxDeclearConfirmNo,taxPolicyCount,taxPolicyMoney,taxAmendCount,taxAmendMoney,"
            +" mTaxPolicyCount,mTaxPolicyMoney,mTaxAmendCount,mTaxAmendMoney,to_char(operateDate,'yyyy-mm-dd') operateDate,"
            +" to_char(confirmOperateDate,'yyyy-mm-dd') confirmOperateDate,operateCode,updateCode,extendChar1,extendChar2,"
            +" corpDelayPay,corpDelayPayReason,flag From CICheckCarShipTaxQG Where ";
        strSqlStatement = strSqlStatement + iWherePart; 
        schemas = dbCICheckCarShipTaxQG.findByConditions(strSqlStatement);
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
      DBCICheckCarShipTaxQG dbCICheckCarShipTaxQG = new DBCICheckCarShipTaxQG();
      if (iLimitCount > 0 && dbCICheckCarShipTaxQG.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICheckCarShipTaxQG.query");
      }
      else
      {
        initArr();
        strSqlStatement = " Select checkNo,comCode,to_char(startDate,'yyyy-mm-dd') startDate,to_char(endDate,'yyyy-mm-dd') endDate, "
            +" taxDeclareQueryNo,taxDeclearConfirmNo,taxPolicyCount,taxPolicyMoney,taxAmendCount,taxAmendMoney,"
            +" mTaxPolicyCount,mTaxPolicyMoney,mTaxAmendCount,mTaxAmendMoney,to_char(operateDate,'yyyy-mm-dd') operateDate,"
            +" to_char(confirmOperateDate,'yyyy-mm-dd') confirmOperateDate,operateCode,updateCode,extendChar1,extendChar2,"
            +" corpDelayPay,corpDelayPayReason,flag From CICheckCarShipTaxQG Where ";
        strSqlStatement = strSqlStatement + iWherePart; 
        schemas = dbCICheckCarShipTaxQG.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCICheckCarShipTaxQG dbCICheckCarShipTaxQG = new DBCICheckCarShipTaxQG();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCICheckCarShipTaxQG.setSchema((CICheckCarShipTaxQGSchema)schemas.get(i));
      dbCICheckCarShipTaxQG.insert(dbpool);
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
     *@param iTaxDeclareQueryNo �걨��ѯ��
     *@return ��
     */
    public void cancel(DbPool dbpool,String iTaxDeclareQueryNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM CICheckCarShipTaxQG WHERE TaxDeclareQueryNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iTaxDeclareQueryNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iTaxDeclareQueryNo �걨��ѯ��
     *@return ��
     */
    public void cancel(String iTaxDeclareQueryNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iTaxDeclareQueryNo);
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
     * ��dbpool����iTaxDeclareQueryNo��ȡ����
     *@param iTaxDeclareQueryNo �걨��ѯ��
     *@return ��
     */
    public void getData(String iTaxDeclareQueryNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iTaxDeclareQueryNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����iTaxDeclareQueryNo��ȡ����
     *@param dbpool ���ӳ�
     *@param iTaxDeclareQueryNo �걨��ѯ��
     *@return ��
     */
    public void getData(DbPool dbpool,String iTaxDeclareQueryNo) throws Exception
    {
        
        
        
        
        
        String strWherePart = " TaxDeclareQueryNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iTaxDeclareQueryNo);
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
        DBCICheckCarShipTaxQG dbCICheckCarShipTaxQG = new DBCICheckCarShipTaxQG();
        if (iLimitCount > 0 && dbCICheckCarShipTaxQG.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCICheckCarShipTaxQG.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CICheckCarShipTaxQG WHERE " + iWherePart;
            schemas = dbCICheckCarShipTaxQG.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
