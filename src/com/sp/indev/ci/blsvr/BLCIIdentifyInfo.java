package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBCIIdentifyInfo;
import com.sp.indiv.ci.schema.CIIdentifyInfoSchema;

/**
 * ����CIIdentifyInfo��BL��
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-01-11</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCIIdentifyInfo{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCIIdentifyInfo(){
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
     *����һ��CIIdentifyInfoSchema��¼
     *@param iCIIdentifyInfoSchema CIIdentifyInfoSchema
     *@throws Exception
     */
    public void setArr(CIIdentifyInfoSchema iCIIdentifyInfoSchema) throws Exception
    {
       try
       {
         schemas.add(iCIIdentifyInfoSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��CIIdentifyInfoSchema��¼
     *@param index �±�
     *@return һ��CIIdentifyInfoSchema����
     *@throws Exception
     */
    public CIIdentifyInfoSchema getArr(int index) throws Exception
    {
     CIIdentifyInfoSchema cIIdentifyInfoSchema = null;
       try
       {
        cIIdentifyInfoSchema = (CIIdentifyInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIIdentifyInfoSchema;
     }
    /**
     *ɾ��һ��CIIdentifyInfoSchema��¼
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
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();
      if (iLimitCount > 0 && dbCIIdentifyInfo.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIIdentifyInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIIdentifyInfo WHERE " + iWherePart; 
        schemas = dbCIIdentifyInfo.findByConditions(strSqlStatement, bindValues);
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
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();
      if (iLimitCount > 0 && dbCIIdentifyInfo.getCount(dbpool,iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIIdentifyInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIIdentifyInfo WHERE " + iWherePart; 
        schemas = dbCIIdentifyInfo.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCIIdentifyInfo.setSchema((CIIdentifyInfoSchema)schemas.get(i));
      dbCIIdentifyInfo.insert(dbpool);
      }
    }
    /**
     *����dbpool��XXXXX�淽��
     *@param ��
     *@return ��
     */
    public void save(String Flag) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      
      try
      {
    	String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
             dbpool.open("platformNewDataSource");            
        } else {
             dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        }
        dbpool.beginTransaction();
        save(dbpool,Flag);
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
     *��dbpool��save����
     *@param ��
     *@return ��
     *@ע��flagΪ"CP",��������C�����ݺ�CP������
     */
    public void save(DbPool dbpool,String Flag) throws Exception
    {
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();
      
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
    	  CIIdentifyInfoSchema ciIdentifyInfoSchema=(CIIdentifyInfoSchema)schemas.get(i);
    	  ciIdentifyInfoSchema.setFlag(Flag);
    	  dbCIIdentifyInfo.setSchema((CIIdentifyInfoSchema)schemas.get(i));
    	  dbCIIdentifyInfo.insert(dbpool);
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
    	String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
             dbpool.open("platformNewDataSource");            
        } else {
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
     *��dbpool��update����
     *@param ��
     *@return ��
     */
    public void update(DbPool dbpool) throws Exception
    {
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
    	  dbCIIdentifyInfo.setSchema((CIIdentifyInfoSchema)schemas.get(i));
    	  dbCIIdentifyInfo.update(dbpool);
      }
    }

    /**
     *����dbpool�ĸ��·���
     *@param ��
     *@return ��
     */
    public void update() throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
              dbpool.open("platformNewDataSource");            
        } else {
              dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        }
        dbpool.beginTransaction();
        update(dbpool);
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
     *@param businessNo BusinessNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancel(DbPool dbpool, String businessNo, String serialNo) throws Exception
    {
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();
      dbCIIdentifyInfo.delete(dbpool,businessNo,serialNo);
    }
    /**
     * ����dbpool��ɾ������
     *@param businessNo BusinessNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancelByFlag(String businessNo, String Flag) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
    	  String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
               dbpool.open("platformNewDataSource");            
          } else {
               dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          }
        dbpool.beginTransaction();
        cancelByFlag(dbpool, businessNo, Flag);
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
     *@param businessNo BusinessNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancelByFlag(DbPool dbpool, String businessNo, String Flag) throws Exception
    {
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();
      dbCIIdentifyInfo.deleteByFlag(dbpool,businessNo,Flag);
    }
    /**
     * ����dbpool��ɾ������
     *@param businessNo BusinessNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancel(String businessNo, String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
    	  String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
               dbpool.open("platformNewDataSource");            
          } else {
               dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          }
        dbpool.beginTransaction();
        cancel(dbpool, businessNo, serialNo);
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
     *@param businessNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancel(DbPool dbpool, String businessNo) throws Exception
    {
      DBCIIdentifyInfo dbCIIdentifyInfo = new DBCIIdentifyInfo();
      dbCIIdentifyInfo.delete(dbpool,businessNo);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param businessNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancel(String businessNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
    	  String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
          if ("1".equals(strSwitch)) {
               dbpool.open("platformNewDataSource");            
          } else {
               dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          }
        dbpool.beginTransaction();
        cancel(dbpool, businessNo);
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
     *@param businessNo BusinessNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void getData(String businessNo, String serialNo,String Flag) throws Exception
    {
      DbPool dbpool = new DbPool();
      try{
	      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
	      if ("1".equals(strSwitch)) {
	           dbpool.open("platformNewDataSource");            
	      } else {
	           dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	      }
	      getData(dbpool, businessNo, serialNo, Flag);
      }catch(Exception e){
    	  
      }finally{
    	  dbpool.close();
      }
    }
    /**
     * ����dbpool����������ȡ����
     *@param businessNo BusinessNo
     *@param
     *@return ��
     */
    public void getData(String businessNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try{
	      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
	      if ("1".equals(strSwitch)) {
	           dbpool.open("platformNewDataSource");            
	      } else {
	           dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	      }
	      getData(dbpool, businessNo);
      }catch(Exception e){
    	  
      }finally{
    	  dbpool.close();
      }
    }
    /**
     * ��dbpool����businessNo��ȡ����
     *@param dbpool ���ӳ�
     *@param businessNo BusinessNo
     *@return ��
     */
    public void getData(DbPool dbpool, String businessNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "businessNo=?";
	   ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(businessNo);
       query(dbpool, strWherePart, 0, arrWhereValue);
    } 
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param businessNo BusinessNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void getData(DbPool dbpool, String businessNo, String serialNo,String Flag) throws Exception
    {
       String strWherePart = "";
       strWherePart = "businessNo=? AND serialNo=? AND Flag=?";
	   ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(businessNo);
       arrWhereValue.add(serialNo);
       arrWhereValue.add(Flag);
       query(dbpool, strWherePart, 0, arrWhereValue);
    }
    /**
     * ����dbpool����������ȡ����
     *@param businessNo BusinessNo
     *@param
     *@return ��
     */
    public void getDataByFlag(String businessNo, String Flag) throws Exception
    {
      DbPool dbpool = new DbPool();
      try{
	      String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
	      if ("1".equals(strSwitch)) {
	           dbpool.open("platformNewDataSource");            
	      } else {
	           dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	      }
	      getDataByFlag(dbpool, businessNo,Flag);
      }catch(Exception e){
    	  
      }finally{
    	  dbpool.close();
      }
    }
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param businessNo BusinessNo
     *@param serialNo SerialNo
     *@return ��
     */
    public void getDataByFlag(DbPool dbpool, String businessNo, String Flag) throws Exception
    {
       String strWherePart = "";
       strWherePart = "businessNo=? AND Flag=?";
	   ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(businessNo);
       arrWhereValue.add(Flag);
       query(dbpool, strWherePart, 0, arrWhereValue);
    }
    /**
	  * t��¼ת��Ϊc��¼
	      *@param
	      *@param iPolicyNo XX��
	  *@return ��
	  */
    public CIIdentifyInfoSchema Evaluate(CIIdentifyInfoSchema tCIIdentifyInfoSchema,String strPolicyNo)throws Exception{
    	CIIdentifyInfoSchema cCIIdentifyInfoSchema = new CIIdentifyInfoSchema();
    	
    	cCIIdentifyInfoSchema.setBusinessNo(strPolicyNo);
		cCIIdentifyInfoSchema.setSerialNo(tCIIdentifyInfoSchema.getSerialNo());
		cCIIdentifyInfoSchema.setIssueCode(tCIIdentifyInfoSchema.getIssueCode());
		cCIIdentifyInfoSchema.setUploadDate(tCIIdentifyInfoSchema.getUploadDate());
		cCIIdentifyInfoSchema.setNation(tCIIdentifyInfoSchema.getNation());
		cCIIdentifyInfoSchema.setSigner(tCIIdentifyInfoSchema.getSigner());
		cCIIdentifyInfoSchema.setCollectorCode(tCIIdentifyInfoSchema.getCollectorCode());
		cCIIdentifyInfoSchema.setIdentifyNumber(tCIIdentifyInfoSchema.getIdentifyNumber());
		cCIIdentifyInfoSchema.setFlag(tCIIdentifyInfoSchema.getFlag());
		
		return cCIIdentifyInfoSchema;
    	
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
