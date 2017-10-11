package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpBankToCompany;
import com.sp.prpall.schema.PrpBankToCompanySchema;

/**
 * ����PrpBankToCompany��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-07-07</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLPrpBankToCompany{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpBankToCompany(){
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
     *����һ��PrpBankToCompanySchema��¼
     *@param iPrpBankToCompanySchema PrpBankToCompanySchema
     *@throws Exception
     */
    public void setArr(PrpBankToCompanySchema iPrpBankToCompanySchema) throws Exception
    {
      try
      {
        schemas.add(iPrpBankToCompanySchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpBankToCompanySchema��¼
     *@param index �±�
     *@return һ��PrpBankToCompanySchema����
     *@throws Exception
     */
    public PrpBankToCompanySchema getArr(int index) throws Exception
    {
      PrpBankToCompanySchema prpBankToCompanySchema = null;
       try
       {
         prpBankToCompanySchema = (PrpBankToCompanySchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpBankToCompanySchema;
     }
    /**
     *ɾ��һ��PrpBankToCompanySchema��¼
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
      DBPrpBankToCompany dbPrpBankToCompany = new DBPrpBankToCompany();
      if (iLimitCount > 0 && dbPrpBankToCompany.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpBankToCompany.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpBankToCompany WHERE " + iWherePart; 
        schemas = dbPrpBankToCompany.findByConditions(strSqlStatement);
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
      DBPrpBankToCompany dbPrpBankToCompany = new DBPrpBankToCompany();
      if (iLimitCount > 0 && dbPrpBankToCompany.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpBankToCompany.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpBankToCompany WHERE " + iWherePart; 
        schemas = dbPrpBankToCompany.findByConditions(dbpool,strSqlStatement);
      }
    }
    /**
     * ���ݲ�ѯ��������ѯSysConfig.CONST_SUNQUERYDATASOURCE����Դ��Ӧ������.���������¼����schemas����
     * @param iWherePart ��ѯ����
     * @param intPageNum ҳ��
     * @param intLineNumPage ÿҳ����
     * @return ��
     * @throws Exception
     */
    public void query(String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
    {
      try {
        this.query(iWherePart,intPageNum,intLineNumPage,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
      }
      catch (Exception e)
      {
        throw e;
      }
    }
    
    /**
     *���ղ�ѯ�����õ�һ���¼�������ڷ�ҳ��ѯ�����������¼����schemas����
     *@param iWherePart ��ѯ����(�����������־�)
     *@param intPageNum ҳ��
     *@param intLineNumPage ÿҳ����
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@return ��
     *@throws Exception
     */
    public void query(String iWherePart,int intPageNum,int intLineNumPage,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      int intStartNum = 0;
      int intEndNum = 0;
      
      intStartNum = (intPageNum - 1) * intLineNumPage;
      
      intEndNum = intPageNum * intLineNumPage;

      DBPrpBankToCompany dbPrpBankToCompany = new DBPrpBankToCompany();
      if (iLimitCount > 0 && dbPrpBankToCompany.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpBankToCompany.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM ( " +
         "Select RowNum As LineNum,T.* From ( " +
         "Select * From PrpBankToCompany Where " + iWherePart +
         ") T Where RowNum<=" + intEndNum + ") Where LineNum>" +
         intStartNum ;
        schemas = dbPrpBankToCompany.findByConditions(strSqlStatement);
      }
    }
    /**
     *��dbpool��save���� 
     *@param �� 
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpBankToCompany dbPrpBankToCompany = new DBPrpBankToCompany();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpBankToCompany.setSchema((PrpBankToCompanySchema)schemas.get(i));
        dbPrpBankToCompany.insert(dbpool);
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
     *��dbpool��update����
     *@param ��
     *@return ��
     */
    public void update(DbPool dbpool) throws Exception
    {
    	DBPrpBankToCompany dbPrpBankToCompany = new DBPrpBankToCompany();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
    	 dbPrpBankToCompany.setSchema((PrpBankToCompanySchema)schemas.get(i));
    	 dbPrpBankToCompany.update(dbpool);
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

      dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));

      try
      {
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
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param iBankSeqNo BankSeqNo
     *@return ��
     */
    public void cancel(DbPool dbpool,String iBankSeqNo ,String iSerialNo,String riskCode) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpBankToCompany WHERE BankSeqNo= '" + iBankSeqNo + "' and SerialNo='"+iSerialNo +  "' and riskCode='"+riskCode+"'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iBankSeqNo BankSeqNo
     *@return ��
     */
    public void cancel(String iBankSeqNo ,String iSerialNo,String riskCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iBankSeqNo,iSerialNo,riskCode);
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
     * ��dbpool����BankSeqNo��ȡ����
     *@param iBankSeqNo BankSeqNo
     *@return ��
     */
    public void getData(String iBankSeqNo,String iSerialNo,String riskCode) throws Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			getData(dbpool, iBankSeqNo, iSerialNo,riskCode);
		} catch (Exception e) {
			
		} finally {
			dbpool.close();
		}
	}
      
    /**
	 * ����dbpool����BankSeqNo��ȡ����
	 * 
	 * @param dbpool
	 *            ���ӳ�
	 * @param iBankSeqNo
	 *            BankSeqNo
	 * @return ��
	 */
    public void getData(DbPool dbpool,String iBankSeqNo,String iSerialNo,String riskCode) throws Exception
    {
      String strWherePart = "";
      strWherePart = " BankSeqNo = ? and SerialNo = ? and riskCode = ?";
      ArrayList arrWhereValue = new ArrayList(2);
      arrWhereValue.add(iBankSeqNo);
      arrWhereValue.add(iSerialNo);
      arrWhereValue.add(riskCode);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    public void getDataByProposalNo(String iProposalNo) throws Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			getDataByProposalNo(dbpool, iProposalNo);
		} catch (Exception e) {
			
		} finally {
			dbpool.close();
		}
	}
    public void getDataByProposalNo(DbPool dbpool, String iProposalNo) throws Exception {
		String strWherePart = " ProposalNo= ? ";
		ArrayList arrWhereValue = new ArrayList(1);
		arrWhereValue.add(iProposalNo);
		query(dbpool, strWherePart, arrWhereValue, 0);
	}
    public void getDataByPolicyNo(String iPolicyNo) throws Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			getDataByPolicyNo(dbpool, iPolicyNo);
		} catch (Exception e) {
			
		} finally {
			dbpool.close();
		}
	}

	public void getDataByPolicyNo(DbPool dbpool, String iPolicyNo) throws Exception {
		String strWherePart = " PolicyNo= ? ";
		ArrayList arrWhereValue = new ArrayList();
		arrWhereValue.add(iPolicyNo);
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
		DBPrpBankToCompany dbPrpBankToCompany = new DBPrpBankToCompany();
		if (iLimitCount > 0 && dbPrpBankToCompany.getCount(dbpool, iWherePart, iWhereValue) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpBankToCompany.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM prpBankToCompany WHERE " + iWherePart;
			schemas = dbPrpBankToCompany.findByConditions(dbpool, strSqlStatement, iWhereValue);
		}
	}
    /**
	 * ������
	 * 
	 * @param args
	 *            �����б�
	 */
    public static void main(String[] args){
        
    }
}