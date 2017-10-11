package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefKind;
import com.sp.prpall.schema.PrpJpayRefKindSchema;

/**
 * ����PrpJpayRefKind��BL��
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-07-17</p>
 * @Author     : X
 * @version 1.0
 */
public class BLPrpJpayRefKind{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */
    public BLPrpJpayRefKind(){
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
     *����һ��PrpJpayRefKindSchema��¼
     *@param iPrpJpayRefKindSchema PrpJpayRefKindSchema
     *@throws Exception
     */
    public void setArr(PrpJpayRefKindSchema iPrpJpayRefKindSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJpayRefKindSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpJpayRefKindSchema��¼
     *@param index �±�
     *@return һ��PrpJpayRefKindSchema����
     *@throws Exception
     */
    public PrpJpayRefKindSchema getArr(int index) throws Exception
    {
     PrpJpayRefKindSchema prpJpayRefKindSchema = null;
       try
       {
        prpJpayRefKindSchema = (PrpJpayRefKindSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJpayRefKindSchema;
     }
    /**
     *ɾ��һ��PrpJpayRefKindSchema��¼
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
      DBPrpJpayRefKind dbPrpJpayRefKind = new DBPrpJpayRefKind();
      if (iLimitCount > 0 && dbPrpJpayRefKind.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefKind.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRefKind WHERE " + iWherePart;
        schemas = dbPrpJpayRefKind.findByConditions(strSqlStatement);
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
      DBPrpJpayRefKind dbPrpJpayRefKind = new DBPrpJpayRefKind();
      if (iLimitCount > 0 && dbPrpJpayRefKind.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefKind.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRefKind WHERE " + iWherePart;
        schemas = dbPrpJpayRefKind.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJpayRefKind dbPrpJpayRefKind = new DBPrpJpayRefKind();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJpayRefKind.setSchema((PrpJpayRefKindSchema)schemas.get(i));
      dbPrpJpayRefKind.insert(dbpool);
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
     *@param null null
     *@return ��
     */
    public void cancel(DbPool dbpool,String strcertino) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpJpayRefKind WHERE certino= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, strcertino);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }

    /**
     * ����dbpool��ɾ������
     *@param null null
     *@return ��
     */
    public void cancel(String strcertino ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,null);
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
     * ��dbpool����null��ȡ����
     *@param null null
     *@return ��
     */
    public void getData(String strcertino) throws Exception
    {
        DbPool dbpool = new DbPool();

  	try {
  		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  		getData(dbpool, null);
  	} catch (Exception e) {
  		
  	}finally {      
  		dbpool.close();
  	}

    }

    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param null null
     *@return ��
     */
    public void getData(DbPool dbpool,String strcertino) throws Exception
    {
    String strWherePart = "";
    strWherePart = "null= ' " + null + "'";
    query(dbpool,strWherePart,0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }

  /**
   * ȡ��XXXXX���̯������
   *@param iCertiNo ҵ���
   *@param iSerialNo ���
   *@throws UserException
   *@throws Exception
   */
  public void getData(String iCertiNo,int iSerialNo)
    throws UserException,Exception
    {
    	String strwherepart;
        strwherepart="CertiNo='"+iCertiNo+"' AND SerialNo='"+
                 iSerialNo+"'";
       this.query(strwherepart);
    	}



}
