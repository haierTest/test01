package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBIntfPosMain;
import com.sp.prpall.schema.IntfPosMainSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����IntfPosMain��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-11-23</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 */
public class BLIntfPosMain{
    private Vector schemas = new Vector();  
    /**
     * ���캯��
     */       
    public BLIntfPosMain(){
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
     *����һ��IntfPosMainSchema��¼
     *@param iIntfPosMainSchema IntfPosMainSchema
     *@throws Exception
     */
    public void setArr(IntfPosMainSchema iIntfPosMainSchema) throws Exception
    {
       try
       {
         schemas.add(iIntfPosMainSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��IntfPosMainSchema��¼
     *@param index �±�
     *@return һ��IntfPosMainSchema����
     *@throws Exception
     */
    public IntfPosMainSchema getArr(int index) throws Exception
    {
     IntfPosMainSchema intfPosMainSchema = null;
       try
       {
        intfPosMainSchema = (IntfPosMainSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return intfPosMainSchema;
     }
    /**
     *ɾ��һ��IntfPosMainSchema��¼
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
      DBIntfPosMain dbIntfPosMain = new DBIntfPosMain();
      if (iLimitCount > 0 && dbIntfPosMain.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntfPosMain.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntfPosMain WHERE " + iWherePart; 
        schemas = dbIntfPosMain.findByConditions(strSqlStatement);
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
      DBIntfPosMain dbIntfPosMain = new DBIntfPosMain();
      if (iLimitCount > 0 && dbIntfPosMain.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntfPosMain.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntfPosMain WHERE " + iWherePart; 
        schemas = dbIntfPosMain.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBIntfPosMain dbIntfPosMain = new DBIntfPosMain();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbIntfPosMain.setSchema((IntfPosMainSchema)schemas.get(i));
      dbIntfPosMain.insert(dbpool);
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
        dbpool.open(SysConst.getProperty("POSDATASOURCE"));
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
     *@param ipolicyNo policyNo
     *@return ��
     */
    public void cancel(DbPool dbpool,String ipolicyNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM IntfPosMain WHERE policyNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, ipolicyNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param ipolicyNo policyNo
     *@return ��
     */
    public void cancel(String ipolicyNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("POSDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,ipolicyNo);
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
     * ��dbpool����policyNo��ȡ����
     *@param ipolicyNo policyNo
     *@return ��
     */
    public void getData(String ipolicyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("POSDATASOURCE"));
      getData(dbpool,ipolicyNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����policyNo��ȡ����
     *@param dbpool ���ӳ�
     *@param ipolicyNo policyNo
     *@return ��
     */
    public void getData(DbPool dbpool,String ipolicyNo) throws Exception
    {
        
        
        
        
        String strWherePart = " policyNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(ipolicyNo);
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
        DBIntfPosMain dbIntfPosMain = new DBIntfPosMain();
        if (iLimitCount > 0 && dbIntfPosMain.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLIntfPosMain.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM IntfPosMain WHERE " + iWherePart;
            schemas = dbIntfPosMain.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
