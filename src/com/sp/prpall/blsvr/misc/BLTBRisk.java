package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBTBRisk;
import com.sp.prpall.schema.TBRiskSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����TBRisk��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-09-03</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 *              5.getData��������try��catch��finally�쳣����;
 */
public class BLTBRisk{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLTBRisk(){
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
     *����һ��TBRiskSchema��¼
     *@param iTBRiskSchema TBRiskSchema
     *@throws Exception
     */
    public void setArr(TBRiskSchema iTBRiskSchema) throws Exception
    {
      try
      {
        schemas.add(iTBRiskSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��TBRiskSchema��¼
     *@param index �±�
     *@return һ��TBRiskSchema����
     *@throws Exception
     */
    public TBRiskSchema getArr(int index) throws Exception
    {
      TBRiskSchema tBRiskSchema = null;
       try
       {
         tBRiskSchema = (TBRiskSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return tBRiskSchema;
     }
    /**
     *ɾ��һ��TBRiskSchema��¼
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
      DBTBRisk dbTBRisk = new DBTBRisk();
      if (iLimitCount > 0 && dbTBRisk.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTBRisk.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_Risk WHERE " + iWherePart; 
        schemas = dbTBRisk.findByConditions(strSqlStatement);
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
      DBTBRisk dbTBRisk = new DBTBRisk();
      if (iLimitCount > 0 && dbTBRisk.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTBRisk.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_Risk WHERE " + iWherePart; 
        schemas = dbTBRisk.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTBRisk dbTBRisk = new DBTBRisk();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbTBRisk.setSchema((TBRiskSchema)schemas.get(i));
        dbTBRisk.insert(dbpool);
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
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM TB_Risk WHERE PolicyNo= '" + iPolicyNo + "'";
      
      dbpool.delete(strSqlStatement);
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
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        getData(dbpool,iPolicyNo);
      }
      catch (Exception e)
      {
        
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     * ����dbpool����PolicyNo��ȡ����
     *@param dbpool ���ӳ�
     *@param iPolicyNo PolicyNo
     *@return ��
     */
    public void getData(DbPool dbpool,String iPolicyNo) throws Exception
    {
      String strWherePart = "PolicyNo= ?";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iPolicyNo);
      query(dbpool,strWherePart,arrWhereValue,0);
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart    ��ѯ����(���������־�)
     *@param arrWhereValue ��ѯ�������ֶ�ֵ
     *@param iLimitCount   ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,ArrayList arrWhereValue, int iLimitCount
    		) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBTBRisk dbTBRisk = new DBTBRisk();
      if (iLimitCount > 0 && dbTBRisk.getCount(dbpool,iWherePart,arrWhereValue) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTBRisk.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_Risk WHERE " + iWherePart; 
        schemas = dbTBRisk.findByConditions(dbpool,strSqlStatement,arrWhereValue);
      }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
