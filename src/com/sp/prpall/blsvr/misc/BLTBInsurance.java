package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBTBInsurance;
import com.sp.prpall.schema.TBInsuranceSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����TB_INSURANCE��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-11-06</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 */
public class BLTBInsurance{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLTBInsurance(){
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
     *����һ��TBInsuranceSchema��¼
     *@param iTBInsuranceSchema TBInsuranceSchema
     *@throws Exception
     */
    public void setArr(TBInsuranceSchema iTBInsuranceSchema) throws Exception
    {
       try
       {
         schemas.add(iTBInsuranceSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��TBInsuranceSchema��¼
     *@param index �±�
     *@return һ��TBInsuranceSchema����
     *@throws Exception
     */
    public TBInsuranceSchema getArr(int index) throws Exception
    {
     TBInsuranceSchema tbInsuranceSchema = null;
       try
       {
    	   tbInsuranceSchema = (TBInsuranceSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return tbInsuranceSchema;
     }
    /**
     *ɾ��һ��TBInsuranceSchema��¼
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
      DBTBInsurance dbTBInsurance = new DBTBInsurance();
      if (iLimitCount > 0 && dbTBInsurance.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTBInsurance.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_INSURANCE WHERE " + iWherePart; 
        schemas = dbTBInsurance.findByConditions(strSqlStatement);
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
      DBTBInsurance dbTBInsurance = new DBTBInsurance();
      if (iLimitCount > 0 && dbTBInsurance.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTBInsurance.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_INSURANCE WHERE " + iWherePart; 
        schemas = dbTBInsurance.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTBInsurance dbTBInsurance = new DBTBInsurance();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
    	  dbTBInsurance.setSchema((TBInsuranceSchema)schemas.get(i));
    	  dbTBInsurance.insert(dbpool);
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
     *@param iPolicyNo PolicyNo
     *@return ��
     */
    public void cancel(DbPool dbpool,String iPolicyNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM TB_INSURANCE WHERE PolicyNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iPolicyNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iPolicyNo PolicyNo
     *@return ��
     */
    public void cancel(String iPolicyNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
     * ��dbpool����PolicyNo��ȡ����
     *@param iPolicyNo PolicyNo
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
     * ����dbpool����PolicyNo��ȡ����
     *@param dbpool ���ӳ�
     *@param iPolicyNo PolicyNo
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
        DBTBInsurance dbTBInsurance = new DBTBInsurance();
        if (iLimitCount > 0 && dbTBInsurance.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLTBInsurance.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM TB_INSURANCE WHERE " + iWherePart;
            schemas = dbTBInsurance.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
