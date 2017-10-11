package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBTBLimit;
import com.sp.prpall.schema.TBLimitSchema;

/**
 * ����TBLimit��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>@createdate 2012-04-25</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 *              5.getData��������try��catch��finally�쳣����;
 */
public class BLTBLimit{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLTBLimit(){
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
     *����һ��TBLimitSchema��¼
     *@param iTBLimitSchema TBLimitSchema
     *@throws Exception
     */
    public void setArr(TBLimitSchema iTBLimitSchema) throws Exception
    {
      try
      {
        schemas.add(iTBLimitSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��TBLimitSchema��¼
     *@param index �±�
     *@return һ��TBLimitSchema����
     *@throws Exception
     */
    public TBLimitSchema getArr(int index) throws Exception
    {
      TBLimitSchema TBLimitSchema = null;
       try
       {
         TBLimitSchema = (TBLimitSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return TBLimitSchema;
     }
    /**
     *ɾ��һ��TBLimitSchema��¼
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
      DBTBLimit dbTBLimit = new DBTBLimit();
      if (iLimitCount > 0 && dbTBLimit.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTBLimit.query");
      }
      else
      {
        initArr();
        
        strSqlStatement = " SELECT * FROM TB_LIMIT WHERE " + iWherePart; 
        
        schemas = dbTBLimit.findByConditions(strSqlStatement);
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
      DBTBLimit dbTBLimit = new DBTBLimit();
      if (iLimitCount > 0 && dbTBLimit.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTBLimit.query");
      }
      else
      {
        initArr();
        
        strSqlStatement = " SELECT * FROM TB_LIMIT WHERE " + iWherePart; 
        
        schemas = dbTBLimit.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTBLimit dbTBLimit = new DBTBLimit();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbTBLimit.setSchema((TBLimitSchema)schemas.get(i));
        dbTBLimit.insert(dbpool);
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
      String strSqlStatement = " DELETE FROM TB_LIMIT WHERE PolicyNo= ?";
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
      String strWherePart = " PolicyNo= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iPolicyNo);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@author zhanghao 20120426
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
        DBTBLimit dbTBLimit = new DBTBLimit();
        if (iLimitCount > 0 && dbTBLimit.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLTBLimit.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM TB_LIMIT WHERE " + iWherePart;
            schemas = dbTBLimit.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
