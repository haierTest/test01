package com.sp.prpall.blsvr.misc;

import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBTBMainProp;
import com.sp.prpall.schema.TBMainPropSchema;

/**
 * ����TB_MainProp��BL��
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>@createdate 2013-07-05</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLTBMainProp{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLTBMainProp(){
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
     *����һ��TB_MainPropSchema��¼
     *@param iTB_MainPropSchema TB_MainPropSchema
     *@throws Exception
     */
    public void setArr(TBMainPropSchema iTB_MainPropSchema) throws Exception
    {
       try
       {
         schemas.add(iTB_MainPropSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��TB_MainPropSchema��¼
     *@param index �±�
     *@return һ��TB_MainPropSchema����
     *@throws Exception
     */
    public TBMainPropSchema getArr(int index) throws Exception
    {
     TBMainPropSchema tB_MainPropSchema = null;
       try
       {
        tB_MainPropSchema = (TBMainPropSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return tB_MainPropSchema;
     }
    /**
     *ɾ��һ��TB_MainPropSchema��¼
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
      DBTBMainProp dbTB_MainProp = new DBTBMainProp();
      if (iLimitCount > 0 && dbTB_MainProp.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTB_MainProp.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_MainProp WHERE " + iWherePart; 
        schemas = dbTB_MainProp.findByConditions(strSqlStatement, bindValues);
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
      DBTBMainProp dbTB_MainProp = new DBTBMainProp();
      if (iLimitCount > 0 && dbTB_MainProp.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLTB_MainProp.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM TB_MainProp WHERE " + iWherePart; 
        schemas = dbTB_MainProp.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBTBMainProp dbTB_MainProp = new DBTBMainProp();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbTB_MainProp.setSchema((TBMainPropSchema)schemas.get(i));
      dbTB_MainProp.insert(dbpool);
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



    	
      	String strSqlStatement = "DELETE FROM TB_MainProp WHERE policyNo= ?";
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



    	
        String strWherePart = " PolicyNo= ? ";
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
        DBTBMainProp dbTBMainProp = new DBTBMainProp();
        if (iLimitCount > 0 && dbTBMainProp.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLTBMainProp.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM TB_MainProp WHERE " + iWherePart;
            schemas = dbTBMainProp.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
