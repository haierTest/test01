package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBAgDBranchMap;
import com.sp.prpall.schema.AgDBranchMapSchema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sp.utiall.dbsvr.DBPrpDkind;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.Str;
/**
 * ����ҵ��������BL��
 * �����м�ҵ��ƽ̨��agdbranchmap��ִ���м�ҵ��ƽ̨ת��ʱ������־����ʱ����XXXXXͨ����ת��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-05-27</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 *              5.getData��������try��catch��finally�쳣����;
 */
public class BLAgDBranchMap{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLAgDBranchMap(){
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
     *����һ��AgDBranchMapSchema��¼
     *@param iAgDBranchMapSchema AgDBranchMapSchema
     *@throws Exception
     */
    public void setArr(AgDBranchMapSchema iAgDBranchMapSchema) throws Exception
    {
      try
      {
        schemas.add(iAgDBranchMapSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��AgDBranchMapSchema��¼
     *@param index �±�
     *@return һ��AgDBranchMapSchema����
     *@throws Exception
     */
    public AgDBranchMapSchema getArr(int index) throws Exception
    {
      AgDBranchMapSchema agDBranchMapSchema = null;
       try
       {
         agDBranchMapSchema = (AgDBranchMapSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return agDBranchMapSchema;
     }
    /**
     *ɾ��һ��AgDBranchMapSchema��¼
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
    public Vector query(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBAgDBranchMap dbAgDBranchMap = new DBAgDBranchMap();
      if (iLimitCount > 0 && dbAgDBranchMap.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLAgDBranchMap.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM AgDBranchMap WHERE 1=1 " + iWherePart; 
        schemas = dbAgDBranchMap.findByConditions(strSqlStatement);
        return schemas;
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
      DBAgDBranchMap dbAgDBranchMap = new DBAgDBranchMap();
      if (iLimitCount > 0 && dbAgDBranchMap.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLAgDBranchMap.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM AgDBranchMap WHERE " + iWherePart; 
        schemas = dbAgDBranchMap.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBAgDBranchMap dbAgDBranchMap = new DBAgDBranchMap();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbAgDBranchMap.setSchema((AgDBranchMapSchema)schemas.get(i));
        dbAgDBranchMap.insert(dbpool);
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
        dbpool.open(SysConst.getProperty("agentDataSource"));
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
     *@param iExtEnterpCode ��ҵ��ҵ����
     *@return ��
     */
    public void cancel(DbPool dbpool,String iExtEnterpCode) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM AgDBranchMap WHERE ExtEnterpCode= '" + iExtEnterpCode + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iExtEnterpCode ��ҵ��ҵ����
     *@return ��
     */
    public void cancel(String iExtEnterpCode ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("agentDataSource"));
        dbpool.beginTransaction();
        cancel(dbpool,iExtEnterpCode);
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
     * ��dbpool������ҵ��ҵ�����ȡ����
     *@param iExtEnterpCode ��ҵ��ҵ����
     *@return ��
     */
    public void getData(String iExtEnterpCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        dbpool.open(SysConst.getProperty("agentDataSource"));
        getData(dbpool,iExtEnterpCode);
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
     * ����dbpool������ҵ��ҵ�����ȡ����
     *@param dbpool ���ӳ�
     *@param iExtEnterpCode ��ҵ��ҵ����
     *@return ��
     */
    public void getData(DbPool dbpool,String iExtEnterpCode) throws Exception
    {
      String strWherePart = "";
      strWherePart = "ExtEnterpCode= '" + iExtEnterpCode + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
