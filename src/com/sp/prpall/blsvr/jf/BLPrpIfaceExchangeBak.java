package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpIfaceExchangeBak;
import com.sp.prpall.schema.PrpIfaceExchangeBakSchema;

/**
 * ����XXXXX�ӿ����ݽ������ݱ��BL��
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>@createdate 2004-12-30</p>
 * @author Īչ��
 * @version 1.0
 */
public class BLPrpIfaceExchangeBak{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */
    public BLPrpIfaceExchangeBak(){
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
     *����һ��PrpIfaceExchangeBakSchema��¼
     *@param iPrpIfaceExchangeBakSchema PrpIfaceExchangeBakSchema
     *@throws Exception
     */
    public void setArr(PrpIfaceExchangeBakSchema iPrpIfaceExchangeBakSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpIfaceExchangeBakSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpIfaceExchangeBakSchema��¼
     *@param index �±�
     *@return һ��PrpIfaceExchangeBakSchema����
     *@throws Exception
     */
    public PrpIfaceExchangeBakSchema getArr(int index) throws Exception
    {
     PrpIfaceExchangeBakSchema prpIfaceExchangeBakSchema = null;
       try
       {
        prpIfaceExchangeBakSchema = (PrpIfaceExchangeBakSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpIfaceExchangeBakSchema;
     }
    /**
     *ɾ��һ��PrpIfaceExchangeBakSchema��¼
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
       this.query(iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBPrpIfaceExchangeBak dbPrpIfaceExchangeBak = new DBPrpIfaceExchangeBak();
      if (iLimitCount > 0 && dbPrpIfaceExchangeBak.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpIfaceExchangeBak.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Iface_Exchange_Bak WHERE " + iWherePart;
        schemas = dbPrpIfaceExchangeBak.findByConditions(strSqlStatement);
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
       this.query(dbpool,iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBPrpIfaceExchangeBak dbPrpIfaceExchangeBak = new DBPrpIfaceExchangeBak();
      if (iLimitCount > 0 && dbPrpIfaceExchangeBak.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpIfaceExchangeBak.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Iface_Exchange_Bak WHERE " + iWherePart;
        schemas = dbPrpIfaceExchangeBak.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpIfaceExchangeBak dbPrpIfaceExchangeBak = new DBPrpIfaceExchangeBak();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpIfaceExchangeBak.setSchema((PrpIfaceExchangeBakSchema)schemas.get(i));
      dbPrpIfaceExchangeBak.insert(dbpool);
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

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        save(dbpool);
        dbpool.commitTransaction();
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally {
        dbpool.close();
      }
    }

    /**
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param id ID��
     *@return ��
     */
    public void cancel(DbPool dbpool,String id) throws Exception
    {




    	String strSqlStatement = " Delete From Iface_Exchange_Bak Where Id = ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, id);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }

    /**
     * ����dbpool��ɾ������
     *@param id Id��
     *@return ��
     */
    public void cancel(String id ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,id);
        dbpool.commitTransaction();
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally {
        dbpool.close();
      }
    }

    /**
     * ��dbpool����Id�Ż�ȡ����
     *@param id ID��
     *@return ��
     */
    public void getData(String id) throws Exception
    {
        DbPool dbpool = new DbPool();

  	try {
  		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  		getData(dbpool, id);
  	} catch (Exception e) {
  		
  	}finally {
  		dbpool.close();
  	}

    }

    /**
     * ����dbpool����Id�Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param id Id��
     *@return ��
     */
    public void getData(DbPool dbpool,String id) throws Exception
    {
    String strWherePart = "";
    strWherePart = "Id= '" + id + "'";
    query(dbpool,strWherePart,0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
