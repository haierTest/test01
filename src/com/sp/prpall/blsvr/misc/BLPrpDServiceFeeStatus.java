package com.sp.prpall.blsvr.misc;


import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpDServiceFeeStatus;
import com.sp.prpall.schema.PrpDServiceFeeStatusSchema;

/**
 * ������������״̬���BL��
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-05-27</p>
 * @author BLGenerator 
 * @version 1.0
 */
public class BLPrpDServiceFeeStatus{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpDServiceFeeStatus(){
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
     *����һ��PrpDServiceFeeStatusSchema��¼
     *@param iPrpDServiceFeeStatusSchema PrpDServiceFeeStatusSchema
     *@throws Exception
     */
    public void setArr(PrpDServiceFeeStatusSchema iPrpDServiceFeeStatusSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpDServiceFeeStatusSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpDServiceFeeStatusSchema��¼
     *@param index �±�
     *@return һ��PrpDServiceFeeStatusSchema����
     *@throws Exception
     */
    public PrpDServiceFeeStatusSchema getArr(int index) throws Exception
    {
     PrpDServiceFeeStatusSchema prpDServiceFeeStatusSchema = null;
       try
       {
        prpDServiceFeeStatusSchema = (PrpDServiceFeeStatusSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpDServiceFeeStatusSchema;
     }
    /**
     *ɾ��һ��PrpDServiceFeeStatusSchema��¼
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
      DBPrpDServiceFeeStatus dbPrpDServiceFeeStatus = new DBPrpDServiceFeeStatus();
      if (iLimitCount > 0 && dbPrpDServiceFeeStatus.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpDServiceFeeStatus.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpDServiceFeeStatus WHERE " + iWherePart; 
        schemas = dbPrpDServiceFeeStatus.findByConditions(strSqlStatement, bindValues);
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
      DBPrpDServiceFeeStatus dbPrpDServiceFeeStatus = new DBPrpDServiceFeeStatus();
      if (iLimitCount > 0 && dbPrpDServiceFeeStatus.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpDServiceFeeStatus.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpDServiceFeeStatus WHERE " + iWherePart; 
        schemas = dbPrpDServiceFeeStatus.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpDServiceFeeStatus dbPrpDServiceFeeStatus = new DBPrpDServiceFeeStatus();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpDServiceFeeStatus.setSchema((PrpDServiceFeeStatusSchema)schemas.get(i));
      dbPrpDServiceFeeStatus.insert(dbpool);
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
      	
      	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
      		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
      	}else{       		
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      	}
      	
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
     *@param serverAreaCode ����������
     *@return ��
     */
    public void cancel(DbPool dbpool, String serverAreaCode) throws Exception
    {
      String strSqlStatement = "";
      strSqlStatement = " DELETE FROM PrpDServiceFeeStatus WHERE serverAreaCode= ?";
      dbpool.prepareInnerStatement(strSqlStatement);
      dbpool.setString(1, serverAreaCode);
	  dbpool.executePreparedUpdate();
	  dbpool.closePreparedStatement();
    }
      
    /**
     * ����dbpool��ɾ������
     *@param serverAreaCode ����������
     *@return ��
     */
    public void cancel(String serverAreaCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
      	
      	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
      		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
      	}else{       		
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      	}
      	
        dbpool.beginTransaction();
        cancel(dbpool, serverAreaCode);
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
     *@param serverAreaCode ����������
     *@return ��
     */
    public void getData(String serverAreaCode) throws Exception
    {
	  	DbPool dbpool = new DbPool();
	    try
	    {
		  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	      dbpool.beginTransaction();
	      getData(dbpool, serverAreaCode);
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
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param serverAreaCode ����������
     *@return ��
     */
    public void getData(DbPool dbpool, String serverAreaCode) throws Exception
    {
       String strWherePart = "";
       strWherePart = "serverAreaCode= ?";
       ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(serverAreaCode);
       query(dbpool, strWherePart, 0, arrWhereValue);
    }
    
    /**
     * ���ղ�ѯ������ҳ��ѯ�õ�һ���¼�������������¼����schemas����
     * @param iWherePart ��ѯ����
     * @param intPageNum ��ǰҳ��
     * @param intPageCount ÿҳ��¼����
     * @throws UserException
     * @throws Exception
     */
	public void query(String iWherePart, int intPageNum, int intPageCount) throws UserException, Exception {
		this.query(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()) , intPageNum, intPageCount);
	}
    
    
    /**
     * ���ղ�ѯ������ҳ��ѯ�õ�һ���¼�������������¼����schemas����
     * @param iWherePart ��ѯ����
     * @param iLimitCount��¼������(iLimitCount=0: ������)
     * @param intPageNum ��ǰҳ��
     * @param intPageCount ÿҳ��¼����
     * @throws UserException
     * @throws Exception
     */
	public void query(String iWherePart,int iLimitCount ,int intPageNum, int intPageCount)
			throws UserException, Exception {
		String strSqlStatement = "";
		int intStartNum = 0;
		int intEndNum = 0;

		intStartNum = (intPageNum - 1) * intPageCount;
		intEndNum = intPageNum * intPageCount;

		DBPrpDServiceFeeStatus dbPrpDServiceFeeStatus = new DBPrpDServiceFeeStatus();
		if (iLimitCount > 0
				&& dbPrpDServiceFeeStatus.getServiceAreaFeeCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpDServiceFeeStatus.query");
		} else {
			initArr();
			strSqlStatement = "select * from ("
					+ "SELECT RowNum as LineNum,T.* FROM("
					+ "select c.comcode,"
					+ "c.serverareacode,"
					+ "c.cardealername,"
					+ "s.creatercode,"
					+ "s.createtime,"
					+ "s.disposecode,"
					+ "s.disposetime,"
					+ "s.updatercode,"
					+ "s.updatedate,"
					+ "s.count,"
					+ "s.totalfee,"
					+ "s.statusflag,"
					+ "s.validstatus,"
					+ "s.feestatus,"
					+ "s.feeobtainflag,"
					+ "s.isDirect,"
					+ "s.beforetotalfee,"
					+ "s.beforecreatercode,"
					+ "s.beforedisposecode,"
					+ "s.beforedisposetime,"
					
					+ "s.SubmitContent"
					
					+ " from prpdcardealerinfo c"
					+ " left join prpdservicefeestatus s"
					+ " on c.serverareacode = s.serverareacode"
					+ " where c.validstatus = '1') T"
					+ " WHERE " + iWherePart 
					+ " and RowNum <= " + intEndNum 
					+ ") where LineNum > "+intStartNum;
			schemas = dbPrpDServiceFeeStatus.findByConditions(strSqlStatement,
					null);
		}
	}
	
    /**
     * ��dbpool��update����
     * @param dbpool ����Դ
     * @return ��
     */
	public void update(DbPool dbpool) throws Exception{
		DBPrpDServiceFeeStatus dbPrpDServiceFeeStatus = new DBPrpDServiceFeeStatus();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
			dbPrpDServiceFeeStatus.setSchema((PrpDServiceFeeStatusSchema)schemas.get(i));
			dbPrpDServiceFeeStatus.update(dbpool);
	    }
	}
	
   /**
    *����dbpool�ĸ��·���
    *@param ��
    *@return ��
    */
	public void update() throws Exception{
		DbPool dbpool = new DbPool();
		try{
        	
        	if("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))){
        		dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
        	}else{       		
		  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        	}
        	
	      dbpool.beginTransaction();
	      update(dbpool);
	      dbpool.commitTransaction();
	    }catch (Exception e){
	      dbpool.rollbackTransaction();
	      throw e;
	    }finally{
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
