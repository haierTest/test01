package com.sp.indiv.ci.blsvr;

import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCICarShipTaxPayMsg;
import com.sp.indiv.ci.schema.CICarShipTaxPayMsgSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;


/**
 * �����ѯ���ػ��ϴ������˰��Ϣ���BL��
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-21</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCICarShipTaxPayMsg{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCICarShipTaxPayMsg(){
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
     *����һ��CICarShipTaxPayMsgSchema��¼
     *@param iCICarShipTaxPayMsgSchema CICarShipTaxPayMsgSchema
     *@throws Exception
     */
    public void setArr(CICarShipTaxPayMsgSchema iCICarShipTaxPayMsgSchema) throws Exception
    {
       try
       {
         schemas.add(iCICarShipTaxPayMsgSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CICarShipTaxPayMsgSchema��¼
     *@param index �±�
     *@return һ��CICarShipTaxPayMsgSchema����
     *@throws Exception
     */
    public CICarShipTaxPayMsgSchema getArr(int index) throws Exception
    {
    	CICarShipTaxPayMsgSchema iCICarShipTaxPayMsgSchema = null;
       try
       {
    	   iCICarShipTaxPayMsgSchema = (CICarShipTaxPayMsgSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return iCICarShipTaxPayMsgSchema;
     }
    /**
     *ɾ��һ��CICarShipTaxPayMsgSchema��¼
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
      DBCICarShipTaxPayMsg dbCICarShipTaxPayMsg = new DBCICarShipTaxPayMsg();
      if (iLimitCount > 0 && dbCICarShipTaxPayMsg.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCICarShipTaxPayMsg.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICarShipTaxPayMsg WHERE " + iWherePart; 
        schemas = dbCICarShipTaxPayMsg.findByConditions(strSqlStatement);
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
      DBCICarShipTaxPayMsg dbCICarShipTaxPayMsg = new DBCICarShipTaxPayMsg();
      if (iLimitCount > 0 && dbCICarShipTaxPayMsg.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"DBCICarShipTaxPayMsg.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CICarShipTaxPayMsg WHERE " + iWherePart; 
        schemas = dbCICarShipTaxPayMsg.findByConditions(dbpool,strSqlStatement);
      }
    }
    
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
    	DBCICarShipTaxPayMsg dbCICarShipTaxPayMsg = new DBCICarShipTaxPayMsg();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
    	  dbCICarShipTaxPayMsg.setSchema((CICarShipTaxPayMsgSchema)schemas.get(i));
    	  dbCICarShipTaxPayMsg.insert(dbpool);
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
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}

