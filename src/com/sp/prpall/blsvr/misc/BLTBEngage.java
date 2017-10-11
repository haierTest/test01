package com.sp.prpall.blsvr.misc;

import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBTBEngage;
import com.sp.prpall.schema.TBEngageSchema;

/**
 * ����TB_Engage��BL��
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>@createdate 2013-07-05</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLTBEngage{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLTBEngage(){
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
     *����һ��TB_EngageSchema��¼
     *@param iTB_EngageSchema TB_EngageSchema
     *@throws Exception
     */
    public void setArr(TBEngageSchema iTB_EngageSchema) throws Exception
    {
       try
       {
         schemas.add(iTB_EngageSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��TB_EngageSchema��¼
     *@param index �±�
     *@return һ��TB_EngageSchema����
     *@throws Exception
     */
    public TBEngageSchema getArr(int index) throws Exception
    {
     TBEngageSchema tB_EngageSchema = null;
       try
       {
        tB_EngageSchema = (TBEngageSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return tB_EngageSchema;
     }
    /**
     *ɾ��һ��TB_EngageSchema��¼
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
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()), bindValues);
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBTBEngage dbTB_Engage = new DBTBEngage();
      if (iLimitCount > 0 && dbTB_Engage.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTB_Engage.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_Engage WHERE " + iWherePart; 
        schemas = dbTB_Engage.findByConditions(strSqlStatement, bindValues);
      }
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()),bindValues);
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBTBEngage dbTB_Engage = new DBTBEngage();
      if (iLimitCount > 0 && dbTB_Engage.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTB_Engage.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_Engage WHERE " + iWherePart; 
        schemas = dbTB_Engage.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTBEngage dbTB_Engage = new DBTBEngage();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbTB_Engage.setSchema((TBEngageSchema)schemas.get(i));
      dbTB_Engage.insert(dbpool);
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
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
     * ��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param policyNo PolicyNo
     *@return ��
     */
    public void cancel(DbPool dbpool, String policyNo) throws Exception
    {



    	
    	String strSqlStatement = " DELETE FROM TB_Engage WHERE policyNo= ? ";
        dbpool.prepareInnerStatement(strSqlStatement);
        dbpool.setString(1, policyNo);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
    }
      
    /**
     * ����dbpool��ɾ������
     *@param policyNo PolicyNo
     *@return ��
     */
    public void cancel(String policyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool, policyNo);
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
     * ����dbpool����������ȡ����
     *@param policyNo PolicyNo
     *@return ��
     */
    public void getData(String policyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
    	getData(dbpool, policyNo);
      }
      catch (Exception e)
      {
    	  e.printStackTrace();
      }
      finally
      {
        dbpool.close();
      }
      
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param policyNo PolicyNo
     *@return ��
     */
    public void getData(DbPool dbpool, String policyNo) throws Exception
    {



    	
        String strWherePart = " PolicyNo= ? Order By PolicyNo,SerialNo,LineNo";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(policyNo);
        query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@author liuweichang 20130705
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
        DBTBEngage dbTBEngage = new DBTBEngage();
        if (iLimitCount > 0 && dbTBEngage.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLTBEngage.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM TB_Engage WHERE " + iWherePart;
            schemas = dbTBEngage.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
