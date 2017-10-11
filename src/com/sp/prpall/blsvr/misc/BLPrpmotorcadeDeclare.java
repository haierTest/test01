package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpMotorcade;
import com.sp.prpall.dbsvr.misc.DBPrpmotorcadeDeclare;
import com.sp.prpall.schema.PrpMotorcadeSchema;
import com.sp.prpall.schema.PrpmotorcadeDeclareSchema;

/**
 * ����PRPMOTORCADEDECLARE��BL��
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
public class BLPrpmotorcadeDeclare{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpmotorcadeDeclare(){
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
     *����һ��PrpmotorcadeDeclareSchema��¼
     *@param iPrpmotorcadeDeclareSchema PrpmotorcadeDeclareSchema
     *@throws Exception
     */
    public void setArr(PrpmotorcadeDeclareSchema iPrpmotorcadeDeclareSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpmotorcadeDeclareSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpmotorcadeDeclareSchema��¼
     *@param index �±�
     *@return һ��PrpmotorcadeDeclareSchema����
     *@throws Exception
     */
    public PrpmotorcadeDeclareSchema getArr(int index) throws Exception
    {
      PrpmotorcadeDeclareSchema prpmotorcadeDeclareSchema = null;
       try
       {
         prpmotorcadeDeclareSchema = (PrpmotorcadeDeclareSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpmotorcadeDeclareSchema;
     }
    /**
     *ɾ��һ��PrpmotorcadeDeclareSchema��¼
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
      DBPrpmotorcadeDeclare dbPrpmotorcadeDeclare = new DBPrpmotorcadeDeclare();
      if (iLimitCount > 0 && dbPrpmotorcadeDeclare.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpmotorcadeDeclare.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpmotorcadeDeclare WHERE " + iWherePart; 
        schemas = dbPrpmotorcadeDeclare.findByConditions(strSqlStatement);
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
      DBPrpmotorcadeDeclare dbPrpmotorcadeDeclare = new DBPrpmotorcadeDeclare();
      if (iLimitCount > 0 && dbPrpmotorcadeDeclare.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpmotorcadeDeclare.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpmotorcadeDeclare WHERE " + iWherePart; 
        schemas = dbPrpmotorcadeDeclare.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpmotorcadeDeclare dbPrpmotorcadeDeclare = new DBPrpmotorcadeDeclare();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpmotorcadeDeclare.setSchema((PrpmotorcadeDeclareSchema)schemas.get(i));
        dbPrpmotorcadeDeclare.insert(dbpool);
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
     *@param iContractNo CONTRACTNO
     *@return ��
     */
    public void cancel(DbPool dbpool,String iContractNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpmotorcadeDeclare WHERE ContractNo= '" + iContractNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iContractNo CONTRACTNO
     *@return ��
     */
    public void cancel(String iContractNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iContractNo);
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
     * ��dbpool����CONTRACTNO��ȡ����
     *@param iContractNo CONTRACTNO
     *@return ��
     */
    public void getData(String iContractNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iContractNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����CONTRACTNO��ȡ����
     *@param dbpool ���ӳ�
     *@param iContractNo CONTRACTNO
     *@return ��
     */
    public void getData(DbPool dbpool,String iContractNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "ContractNo= '" + iContractNo + "'";
      query(dbpool,strWherePart,0);
    }  
	
    /**
     *��dbpool��update����
     *@param ��
     *@return ��
     */
	public void update(DbPool dbpool) throws Exception{
		DBPrpmotorcadeDeclare dbPrpmotorcadeDeclare = new DBPrpmotorcadeDeclare();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
			dbPrpmotorcadeDeclare.setSchema((PrpmotorcadeDeclareSchema)schemas.get(i));
			dbPrpmotorcadeDeclare.update(dbpool);
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
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
