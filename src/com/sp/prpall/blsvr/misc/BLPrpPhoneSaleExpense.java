package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpPhoneSaleExpense;
import com.sp.prpall.schema.PrpPhoneSaleExpenseSchema;

/**
 * ����PrpPhoneSaleExpense-���ĵ������ñ��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-10-11</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLPrpPhoneSaleExpense{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpPhoneSaleExpense(){
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
     *����һ��PrpPhoneSaleExpenseSchema��¼
     *@param iPrpPhoneSaleExpenseSchema PrpPhoneSaleExpenseSchema
     *@throws Exception
     */
    public void setArr(PrpPhoneSaleExpenseSchema iPrpPhoneSaleExpenseSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpPhoneSaleExpenseSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpPhoneSaleExpenseSchema��¼
     *@param index �±�
     *@return һ��PrpPhoneSaleExpenseSchema����
     *@throws Exception
     */
    public PrpPhoneSaleExpenseSchema getArr(int index) throws Exception
    {
      PrpPhoneSaleExpenseSchema prpPhoneSaleExpenseSchema = null;
       try
       {
         prpPhoneSaleExpenseSchema = (PrpPhoneSaleExpenseSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpPhoneSaleExpenseSchema;
     }
    /**
     *ɾ��һ��PrpPhoneSaleExpenseSchema��¼
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
      DBPrpPhoneSaleExpense dbPrpPhoneSaleExpense = new DBPrpPhoneSaleExpense();
      if (iLimitCount > 0 && dbPrpPhoneSaleExpense.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPhoneSaleExpense.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpPhoneSaleExpense WHERE " + iWherePart; 
        schemas = dbPrpPhoneSaleExpense.findByConditions(strSqlStatement);
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
      DBPrpPhoneSaleExpense dbPrpPhoneSaleExpense = new DBPrpPhoneSaleExpense();
      if (iLimitCount > 0 && dbPrpPhoneSaleExpense.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPhoneSaleExpense.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpPhoneSaleExpense WHERE " + iWherePart; 
        schemas = dbPrpPhoneSaleExpense.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpPhoneSaleExpense dbPrpPhoneSaleExpense = new DBPrpPhoneSaleExpense();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpPhoneSaleExpense.setSchema((PrpPhoneSaleExpenseSchema)schemas.get(i));
        dbPrpPhoneSaleExpense.insert(dbpool);
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
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
     *@param iEndorseNo ������
     *@return ��
     */
    public void cancel(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpPhoneSaleExpense WHERE ProposalNo= '" + iProposalNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iEndorseNo ������
     *@return ��
     */
    public void cancel(String iProposalNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iProposalNo);
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
     * ��dbpool���������Ż�ȡ����
     *@param iEndorseNo ������
     *@return ��
     */
    public void getData(String iProposalNo) throws Exception
    {
        
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			getData(dbpool, iProposalNo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbpool.close();
		}
		
    }
      
    /**
     * ����dbpool���������Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iEndorseNo ������
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
     *@author zhanghaoyu 20141020 
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iWhereValue ��ѯ�������ֶ�ֵ
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,ArrayList iWhereValue,int iLimitCount) throws UserException,Exception
    {
      if(iWherePart.indexOf("'null'")>-1){
          throw new Exception("��ѯ�����쳣������ϵϵͳ����Ա��");
      }
      String strSqlStatement = "";
      DBPrpPhoneSaleExpense dbPrpPhoneSaleExpense = new DBPrpPhoneSaleExpense();
      if (iLimitCount > 0 && dbPrpPhoneSaleExpense.getCount(dbpool, iWherePart, iWhereValue) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPhoneSaleExpense.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpPhoneSaleExpense WHERE " + iWherePart; 
        schemas = dbPrpPhoneSaleExpense.findByConditions(dbpool,strSqlStatement, iWhereValue);
      }
    }    
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
