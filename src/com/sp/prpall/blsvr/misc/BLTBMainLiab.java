package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBTBMainLiab;
import com.sp.prpall.schema.TBMainLiabSchema;

/**
 * ����TBMainLiab��BL��
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
public class BLTBMainLiab{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLTBMainLiab(){
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
     *����һ��TBMainLiabSchema��¼
     *@param iTBMainLiabSchema TBMainLiabSchema
     *@throws Exception
     */
    public void setArr(TBMainLiabSchema iTBMainLiabSchema) throws Exception
    {
      try
      {
        schemas.add(iTBMainLiabSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��TBMainLiabSchema��¼
     *@param index �±�
     *@return һ��TBMainLiabSchema����
     *@throws Exception
     */
    public TBMainLiabSchema getArr(int index) throws Exception
    {
      TBMainLiabSchema TBMainLiabSchema = null;
       try
       {
         TBMainLiabSchema = (TBMainLiabSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return TBMainLiabSchema;
     }
    /**
     *ɾ��һ��TBMainLiabSchema��¼
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
      DBTBMainLiab dbTBMainLiab = new DBTBMainLiab();
      if (iLimitCount > 0 && dbTBMainLiab.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTBMainLiab.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_MainLiab WHERE " + iWherePart; 
        schemas = dbTBMainLiab.findByConditions(strSqlStatement);
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
      DBTBMainLiab dbTBMainLiab = new DBTBMainLiab();
      if (iLimitCount > 0 && dbTBMainLiab.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTBMainLiab.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_MainLiab WHERE " + iWherePart; 
        schemas = dbTBMainLiab.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTBMainLiab dbTBMainLiab = new DBTBMainLiab();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbTBMainLiab.setSchema((TBMainLiabSchema)schemas.get(i));
        dbTBMainLiab.insert(dbpool);
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
      String strSqlStatement = " DELETE FROM TB_MAINLIAB WHERE PolicyNo= ?";
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
        DBTBMainLiab dbTBMainLiab = new DBTBMainLiab();
        if (iLimitCount > 0 && dbTBMainLiab.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLTBMAINLIAB.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM TB_MAINLIAB WHERE " + iWherePart;
            schemas = dbTBMainLiab.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
