package com.sp.prpall.blsvr.misc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpPlanFeeInfo;
import com.sp.prpall.schema.PrpPlanFeeInfoSchema;

/**
 * ����PrpPlanFeeInfo��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-07-06</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLPrpPlanFeeInfo{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpPlanFeeInfo(){
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
     *����һ��PrpPlanFeeInfoSchema��¼
     *@param iPrpPlanFeeInfoSchema PrpPlanFeeInfoSchema
     *@throws Exception
     */
    public void setArr(PrpPlanFeeInfoSchema iPrpPlanFeeInfoSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpPlanFeeInfoSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpPlanFeeInfoSchema��¼
     *@param index �±�
     *@return һ��PrpPlanFeeInfoSchema����
     *@throws Exception
     */
    public PrpPlanFeeInfoSchema getArr(int index) throws Exception
    {
      PrpPlanFeeInfoSchema prpPlanFeeInfoSchema = null;
       try
       {
         prpPlanFeeInfoSchema = (PrpPlanFeeInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpPlanFeeInfoSchema;
     }
    /**
     *ɾ��һ��PrpPlanFeeInfoSchema��¼
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
      DBPrpPlanFeeInfo dbPrpPlanFeeInfo = new DBPrpPlanFeeInfo();
      if (iLimitCount > 0 && dbPrpPlanFeeInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPlanFeeInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpPlanFeeInfo WHERE " + iWherePart; 
        schemas = dbPrpPlanFeeInfo.findByConditions(strSqlStatement);
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
      DBPrpPlanFeeInfo dbPrpPlanFeeInfo = new DBPrpPlanFeeInfo();
      if (iLimitCount > 0 && dbPrpPlanFeeInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPlanFeeInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpPlanFeeInfo WHERE " + iWherePart; 
        schemas = dbPrpPlanFeeInfo.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpPlanFeeInfo dbPrpPlanFeeInfo = new DBPrpPlanFeeInfo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpPlanFeeInfo.setSchema((PrpPlanFeeInfoSchema)schemas.get(i));
        dbPrpPlanFeeInfo.insert(dbpool);
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
     *@param iEndorseNo ������
     *@return ��
     */
    public void cancel(DbPool dbpool,String iBankSeqNo,String iSerialNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpPlanFeeInfo WHERE BankSeqNo= '" + iBankSeqNo + "' and SerialNo='"+iSerialNo+"'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iEndorseNo ������
     *@return ��
     */
    public void cancel(String iBankSeqNo ,String iSerialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iBankSeqNo,iSerialNo);
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
     * ��dbpool���������Ż�ȡ����
     *@param iEndorseNo ������
     *@return ��
     */
    public void getData(String iBankSeqNo,String iSerialNo) throws Exception
    {
    	DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			getData(dbpool, iBankSeqNo, iSerialNo);
		} catch (Exception e) {
			
		} finally {
			dbpool.close();
		}
    }
      
    /**
     * ����dbpool���������Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iEndorseNo ������
     *@return ��
     */
    public void getData(DbPool dbpool,String iBankSeqNo,String iSerialNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = " BankSeqNo = ? and SerialNo = ? ";
      ArrayList arrWhereValue = new ArrayList(2);
      arrWhereValue.add(iBankSeqNo);
      arrWhereValue.add(iSerialNo);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    /**
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * 
	 * @param dbpool
	 *            ȫ�ֳ�
	 * @param iWherePart
	 *            ��ѯ����,�����������Ѱ󶨱�����ʽ���ʺŸ���������ֵ����һ��
	 * @param iWhereValue
	 *            ��ѯ�������ֶ�ֵ
	 * @param iLimitCount
	 *            ��¼������(iLimitCount=0: ������)
	 * @throws UserException
	 * @throws Exception
	 */
    public void query(DbPool dbpool, String iWherePart, ArrayList iWhereValue, int iLimitCount) throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpPlanFeeInfo dbPrpPlanFeeInfo = new DBPrpPlanFeeInfo();
		if (iLimitCount > 0 && dbPrpPlanFeeInfo.getCount(dbpool, iWherePart, iWhereValue) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpPlanFeeInfo.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM prpPlanFeeInfo WHERE " + iWherePart;
			schemas = dbPrpPlanFeeInfo.findByConditions(dbpool, strSqlStatement, iWhereValue);
		}
	}
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
