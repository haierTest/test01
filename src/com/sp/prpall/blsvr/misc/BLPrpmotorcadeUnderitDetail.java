package com.sp.prpall.blsvr.misc;

import java.util.Vector;

import org.apache.commons.logging.LogFactory;

import com.sp.prpall.dbsvr.misc.DBPrpmotorcadeCoef;
import com.sp.prpall.dbsvr.misc.DBPrpmotorcadeUnderitDetail;
import com.sp.prpall.schema.PrpmotorcadeCoefSchema;
import com.sp.prpall.schema.PrpmotorcadeUnderitDetailSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����PRPMOTORCADEUNDEWRITDETAIL��BL��
 * PRPMOTORCADEUNDEWRITDETAIL���ų���XXXXX������ϸ��Ϣ��
 */
public class BLPrpmotorcadeUnderitDetail{
    private final org.apache.commons.logging.Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpmotorcadeUnderitDetail(){
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
    public void setArr(PrpmotorcadeUnderitDetailSchema iPrpmotorcadeUnderitDetailSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpmotorcadeUnderitDetailSchema);
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
    public PrpmotorcadeUnderitDetailSchema getArr(int index) throws Exception
    {
	PrpmotorcadeUnderitDetailSchema prpmotorcadeUnderitDetailSchema = null;
       try
       {
	   prpmotorcadeUnderitDetailSchema = (PrpmotorcadeUnderitDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpmotorcadeUnderitDetailSchema;
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
      DBPrpmotorcadeUnderitDetail dbPrpmotorcadeUnderitDetail = new DBPrpmotorcadeUnderitDetail();
      if (iLimitCount > 0 && dbPrpmotorcadeUnderitDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpmotorcadeUnderitDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PRPMOTORCADEUNDEWRITDETAIL WHERE " + iWherePart; 
        schemas = dbPrpmotorcadeUnderitDetail.findByConditions(strSqlStatement);
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
      DBPrpmotorcadeUnderitDetail dbPrpmotorcadeUnderitDetail = new DBPrpmotorcadeUnderitDetail();
      if (iLimitCount > 0 && dbPrpmotorcadeUnderitDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpmotorcadeUnderitDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PRPMOTORCADEUNDEWRITDETAIL WHERE " + iWherePart; 
        schemas = dbPrpmotorcadeUnderitDetail.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
	DBPrpmotorcadeUnderitDetail dbPrpmotorcadeUnderitDetail = new DBPrpmotorcadeUnderitDetail();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
	  dbPrpmotorcadeUnderitDetail.setSchema((PrpmotorcadeUnderitDetailSchema)schemas.get(i));
	  dbPrpmotorcadeUnderitDetail.insert(dbpool);
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
    public void cancel(DbPool dbpool,String contractno) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PRPMOTORCADEUNDEWRITDETAIL WHERE contractno= '" + contractno + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iSerialNo SERIALNO
     *@return ��
     */
    public void cancel(String contractno) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,contractno);
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
    public void getData(String contractno,String motorcadetype,String riskfactorcode) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,contractno,motorcadetype,riskfactorcode);
      dbpool.close();
    }
      
    /**
     * ����dbpool����SERIALNO��ȡ����
     *@param dbpool ���ӳ�
     *@param iSerialNo SERIALNO
     *@return ��
     */
    public void getData(DbPool dbpool,String contractno,String motorcadetype,String riskfactorcode) throws Exception
    {
      String strWherePart = "";
      strWherePart = "contractno= '" + contractno + "' and motorcadetype= '"+motorcadetype+"' and riskfactorcode= '"+riskfactorcode+"'";
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
	    DBPrpmotorcadeUnderitDetail dbPrpmotorcadeUnderitDetail = new DBPrpmotorcadeUnderitDetail();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
		    dbPrpmotorcadeUnderitDetail.setSchema((PrpmotorcadeUnderitDetailSchema)schemas.get(i));
		    dbPrpmotorcadeUnderitDetail.update(dbpool);
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
		    DBPrpmotorcadeUnderitDetail dbPrpmotorcadeUnderitDetail = new DBPrpmotorcadeUnderitDetail();
			int i = 0;
			for(i = 0; i< schemas.size(); i++){
			    dbPrpmotorcadeUnderitDetail.setSchema((PrpmotorcadeUnderitDetailSchema)schemas.get(i));
			    dbPrpmotorcadeUnderitDetail.updateByContractNo(dbpool);
		    }
		}
	
}
