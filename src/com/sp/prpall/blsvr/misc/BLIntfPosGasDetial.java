package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBIntfPosGasDetial;
import com.sp.prpall.schema.IntfPosGasDetialSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����IntfPosGasDetial��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-12-19</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 */
public class BLIntfPosGasDetial{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLIntfPosGasDetial(){
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
     *����һ��IntfPosGasDetialSchema��¼
     *@param iIntfPosGasDetialSchema IntfPosGasDetialSchema
     *@throws Exception
     */
    public void setArr(IntfPosGasDetialSchema iIntfPosGasDetialSchema) throws Exception
    {
       try
       {
         schemas.add(iIntfPosGasDetialSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��IntfPosGasDetialSchema��¼
     *@param index �±�
     *@return һ��IntfPosGasDetialSchema����
     *@throws Exception
     */
    public IntfPosGasDetialSchema getArr(int index) throws Exception
    {
     IntfPosGasDetialSchema intfPosGasDetialSchema = null;
       try
       {
        intfPosGasDetialSchema = (IntfPosGasDetialSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return intfPosGasDetialSchema;
     }
    /**
     *ɾ��һ��IntfPosGasDetialSchema��¼
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
      DBIntfPosGasDetial dbIntfPosGasDetial = new DBIntfPosGasDetial();
      if (iLimitCount > 0 && dbIntfPosGasDetial.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntfPosGasDetial.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntfPosGasDetial WHERE " + iWherePart; 
        schemas = dbIntfPosGasDetial.findByConditions(strSqlStatement);
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
      DBIntfPosGasDetial dbIntfPosGasDetial = new DBIntfPosGasDetial();
      if (iLimitCount > 0 && dbIntfPosGasDetial.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntfPosGasDetial.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntfPosGasDetial WHERE " + iWherePart; 
        schemas = dbIntfPosGasDetial.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBIntfPosGasDetial dbIntfPosGasDetial = new DBIntfPosGasDetial();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbIntfPosGasDetial.setSchema((IntfPosGasDetialSchema)schemas.get(i));
      dbIntfPosGasDetial.insert(dbpool);
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
     *@param iPolicyNo PolicyNo
     *@return ��
     */
    public void cancel(DbPool dbpool,String iPolicyNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM IntfPosGasDetial WHERE PolicyNo= ?";
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
        dbpool.open(SysConst.getProperty("POSDATASOURCE"));
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
      dbpool.open(SysConst.getProperty("POSDATASOURCE"));
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
        DBIntfPosGasDetial dbIntfPosGasDetial = new DBIntfPosGasDetial();
        if (iLimitCount > 0 && dbIntfPosGasDetial.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLIntfPosGasDetial.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM IntfPosGasDetial WHERE " + iWherePart;
            schemas = dbIntfPosGasDetial.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
