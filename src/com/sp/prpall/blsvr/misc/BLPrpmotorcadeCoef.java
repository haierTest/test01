package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

import org.apache.commons.logging.LogFactory;

import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.log.Log;
import com.sp.prpall.dbsvr.misc.DBPrpMotorcade;
import com.sp.prpall.dbsvr.misc.DBPrpmotorcadeCoef;
import com.sp.prpall.schema.PrpMotorcadeSchema;
import com.sp.prpall.schema.PrpmotorcadeCoefSchema;

/**
 * ����PRPMOTORCADECOEF��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-12-21</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLPrpmotorcadeCoef{
    private final org.apache.commons.logging.Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpmotorcadeCoef(){
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
     *����һ��PrpmotorcadeCoefSchema��¼
     *@param iPrpmotorcadeCoefSchema PrpmotorcadeCoefSchema
     *@throws Exception
     */
    public void setArr(PrpmotorcadeCoefSchema iPrpmotorcadeCoefSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpmotorcadeCoefSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpmotorcadeCoefSchema��¼
     *@param index �±�
     *@return һ��PrpmotorcadeCoefSchema����
     *@throws Exception
     */
    public PrpmotorcadeCoefSchema getArr(int index) throws Exception
    {
      PrpmotorcadeCoefSchema prpmotorcadeCoefSchema = null;
       try
       {
         prpmotorcadeCoefSchema = (PrpmotorcadeCoefSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpmotorcadeCoefSchema;
     }
    /**
     *ɾ��һ��PrpmotorcadeCoefSchema��¼
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
      DBPrpmotorcadeCoef dbPrpmotorcadeCoef = new DBPrpmotorcadeCoef();
      if (iLimitCount > 0 && dbPrpmotorcadeCoef.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpmotorcadeCoef.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpmotorcadeCoef WHERE " + iWherePart; 
        schemas = dbPrpmotorcadeCoef.findByConditions(strSqlStatement);
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
      DBPrpmotorcadeCoef dbPrpmotorcadeCoef = new DBPrpmotorcadeCoef();
      if (iLimitCount > 0 && dbPrpmotorcadeCoef.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpmotorcadeCoef.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpmotorcadeCoef WHERE " + iWherePart; 
        schemas = dbPrpmotorcadeCoef.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpmotorcadeCoef dbPrpmotorcadeCoef = new DBPrpmotorcadeCoef();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpmotorcadeCoef.setSchema((PrpmotorcadeCoefSchema)schemas.get(i));
        dbPrpmotorcadeCoef.insert(dbpool);
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
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
     *@param iSerialNo SERIALNO
     *@return ��
     */
    public void cancel(DbPool dbpool,String iSerialNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpmotorcadeCoef WHERE SerialNo= '" + iSerialNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iSerialNo SERIALNO
     *@return ��
     */
    public void cancel(String iSerialNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iSerialNo);
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
     * ��dbpool����SERIALNO��ȡ����
     *@param iSerialNo SERIALNO
     *@return ��
     */
    public void getData(String iSerialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iSerialNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����SERIALNO��ȡ����
     *@param dbpool ���ӳ�
     *@param iSerialNo SERIALNO
     *@return ��
     */
    public void getData(DbPool dbpool,String iSerialNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "SerialNo= '" + iSerialNo + "'";
      query(dbpool,strWherePart,0);
    }
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
    
    /**
     *��dbpool��update����
     *@param ��
     *@return ��
     */
	public void update(DbPool dbpool) throws Exception{
		DBPrpmotorcadeCoef dBPrpmotorcadeCoef = new DBPrpmotorcadeCoef();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
			dBPrpmotorcadeCoef.setSchema((PrpmotorcadeCoefSchema)schemas.get(i));
			dBPrpmotorcadeCoef.update(dbpool);
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
		  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
     *��dbpool��update����
     *@param ��
     *@return ��
     */
	public void updateByContractNo(DbPool dbpool) throws Exception{
		DBPrpmotorcadeCoef dBPrpmotorcadeCoef = new DBPrpmotorcadeCoef();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
			dBPrpmotorcadeCoef.setSchema((PrpmotorcadeCoefSchema)schemas.get(i));
			dBPrpmotorcadeCoef.updateByContractNo(dbpool);
	    }
	}
}
