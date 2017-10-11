package com.sp.prpall.blsvr.misc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpBankToCompanyCar;
import com.sp.prpall.schema.PrpBankToCompanyCarSchema;

/**
 * ����prpBankToCompanyCar��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-10-09</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLPrpBankToCompanyCar{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpBankToCompanyCar(){
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
     *����һ��PrpBankToCompanyCarSchema��¼
     *@param iPrpBankToCompanyCarSchema PrpBankToCompanyCarSchema
     *@throws Exception
     */
    public void setArr(PrpBankToCompanyCarSchema iPrpBankToCompanyCarSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpBankToCompanyCarSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpBankToCompanyCarSchema��¼
     *@param index �±�
     *@return һ��PrpBankToCompanyCarSchema����
     *@throws Exception
     */
    public PrpBankToCompanyCarSchema getArr(int index) throws Exception
    {
      PrpBankToCompanyCarSchema prpBankToCompanyCarSchema = null;
       try
       {
         prpBankToCompanyCarSchema = (PrpBankToCompanyCarSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpBankToCompanyCarSchema;
     }
    /**
     *ɾ��һ��PrpBankToCompanyCarSchema��¼
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
      DBPrpBankToCompanyCar dbPrpBankToCompanyCar = new DBPrpBankToCompanyCar();
      if (iLimitCount > 0 && dbPrpBankToCompanyCar.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpBankToCompanyCar.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpBankToCompanyCar WHERE " + iWherePart; 
        schemas = dbPrpBankToCompanyCar.findByConditions(strSqlStatement);
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
      DBPrpBankToCompanyCar dbPrpBankToCompanyCar = new DBPrpBankToCompanyCar();
      if (iLimitCount > 0 && dbPrpBankToCompanyCar.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpBankToCompanyCar.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpBankToCompanyCar WHERE " + iWherePart; 
        schemas = dbPrpBankToCompanyCar.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpBankToCompanyCar dbPrpBankToCompanyCar = new DBPrpBankToCompanyCar();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpBankToCompanyCar.setSchema((PrpBankToCompanyCarSchema)schemas.get(i));
        dbPrpBankToCompanyCar.insert(dbpool);
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
     *@param iBankSeqNo ������ˮ��
     *@return ��
     */
    public void cancel(DbPool dbpool,String iBankSeqNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpBankToCompanyCar WHERE BankSeqNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iBankSeqNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iBankSeqNo ������ˮ��
     *@return ��
     */
    public void cancel(String iBankSeqNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iBankSeqNo);
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
     * ��dbpool����������ˮ�Ż�ȡ����
     *@param iBankSeqNo ������ˮ��
     *@return ��
     */
    public void getData(String iBankSeqNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iBankSeqNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����������ˮ�Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iBankSeqNo ������ˮ��
     *@return ��
     */
    public void getData(DbPool dbpool,String iBankSeqNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "BankSeqNo= '" + iBankSeqNo + "'";
      query(dbpool,strWherePart,0);
    }
    
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
