package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpAgentSub;
import com.sp.prpall.schema.PrpAgentSubSchema;

/**
 * ����PrpAgentSub��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-03-01</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 */
public class BLPrpAgentSub{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpAgentSub(){
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
     *����һ��PrpAgentSubSchema��¼
     *@param iPrpAgentSubSchema PrpAgentSubSchema
     *@throws Exception
     */
    public void setArr(PrpAgentSubSchema iPrpAgentSubSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpAgentSubSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpAgentSubSchema��¼
     *@param index �±�
     *@return һ��PrpAgentSubSchema����
     *@throws Exception
     */
    public PrpAgentSubSchema getArr(int index) throws Exception
    {
     PrpAgentSubSchema prpAgentSubSchema = null;
       try
       {
        prpAgentSubSchema = (PrpAgentSubSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpAgentSubSchema;
     }
    /**
     *ɾ��һ��PrpAgentSubSchema��¼
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
      DBPrpAgentSub dbPrpAgentSub = new DBPrpAgentSub();
      if (iLimitCount > 0 && dbPrpAgentSub.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpAgentSub.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpAgentSub WHERE " + iWherePart; 
        schemas = dbPrpAgentSub.findByConditions(strSqlStatement);
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
      DBPrpAgentSub dbPrpAgentSub = new DBPrpAgentSub();
      if (iLimitCount > 0 && dbPrpAgentSub.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpAgentSub.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpAgentSub WHERE " + iWherePart; 
        schemas = dbPrpAgentSub.findByConditions(dbpool,strSqlStatement);
      }
    }
    /**
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * @param dbpool ȫ�ֳ�
	 * @param iWherePart ��ѯ����,�����������Ѱ󶨱�����ʽ���ʺŸ���������ֵ����һ��
	 * @param iWhereValue ��ѯ�������ֶ�ֵ
	 * @param iLimitCount ��¼������(iLimitCount=0: ������)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, ArrayList iWhereValue, int iLimitCount) throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpAgentSub dbPrpAgentSub = new DBPrpAgentSub();
		if (iLimitCount > 0 && dbPrpAgentSub.getCount(dbpool, iWherePart, iWhereValue) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpAgentSub.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpAgentSub WHERE " + iWherePart;
			schemas = dbPrpAgentSub.findByConditions(dbpool, strSqlStatement, iWhereValue);
		}
	}
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpAgentSub dbPrpAgentSub = new DBPrpAgentSub();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpAgentSub.setSchema((PrpAgentSubSchema)schemas.get(i));
      dbPrpAgentSub.insert(dbpool);
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
     *@param iPolicyNO PolicyNO
     *@return ��
     */
    public void cancel(DbPool dbpool,String iPolicyNO) throws Exception
    {
      String strSqlStatement = "";
      strSqlStatement = " DELETE FROM PrpAgentSub WHERE PolicyNo= ?";
      dbpool.prepareInnerStatement(strSqlStatement);
      dbpool.setString(1, iPolicyNO);
      dbpool.executePreparedUpdate();
      dbpool.closePreparedStatement();
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iPolicyNO PolicyNO
     *@return ��
     */
    public void cancel(String iPolicyNO ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iPolicyNO);
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
     * ��dbpool����PolicyNO��ȡ����
     *@param iPolicyNO PolicyNO
     *@return ��
     */
    public void getData(String iPolicyNO) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iPolicyNO);
      dbpool.close();
    }
      
    /**
     * ����dbpool����PolicyNO��ȡ����
     *@param dbpool ���ӳ�
     *@param iPolicyNO PolicyNO
     *@return ��
     */
    public void getData(DbPool dbpool,String iPolicyNO) throws Exception
    {
      String strWherePart = " PolicyNo= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iPolicyNO);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
