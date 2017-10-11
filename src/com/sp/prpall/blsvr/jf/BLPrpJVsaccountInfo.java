package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.sp.prpall.dbsvr.jf.DBPrpJVsaccountInfo;
import com.sp.prpall.schema.PrpJVsaccountInfoSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.visa.bl.action.domain.BLVsaccountinfoAction;

/**
 * �����ո������֤���BL��
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-12-26</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJVsaccountInfo{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpJVsaccountInfo(){
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
     *����һ��PrpJVsaccountInfoSchema��¼
     *@param iPrpJVsaccountInfoSchema PrpJVsaccountInfoSchema
     *@throws Exception
     */
    public void setArr(PrpJVsaccountInfoSchema iPrpJVsaccountInfoSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJVsaccountInfoSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpJVsaccountInfoSchema��¼
     *@param index �±�
     *@return һ��PrpJVsaccountInfoSchema����
     *@throws Exception
     */
    public PrpJVsaccountInfoSchema getArr(int index) throws Exception
    {
     PrpJVsaccountInfoSchema prpJVsaccountInfoSchema = null;
       try
       {
        prpJVsaccountInfoSchema = (PrpJVsaccountInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJVsaccountInfoSchema;
     }
    /**
     *ɾ��һ��PrpJVsaccountInfoSchema��¼
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
      DBPrpJVsaccountInfo dbPrpJVsaccountInfo = new DBPrpJVsaccountInfo();
      if (iLimitCount > 0 && dbPrpJVsaccountInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJVsaccountInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJVsaccountInfo WHERE " + iWherePart; 
        schemas = dbPrpJVsaccountInfo.findByConditions(strSqlStatement, bindValues);
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
      DBPrpJVsaccountInfo dbPrpJVsaccountInfo = new DBPrpJVsaccountInfo();
      if (iLimitCount > 0 && dbPrpJVsaccountInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJVsaccountInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJVsaccountInfo WHERE " + iWherePart; 
        schemas = dbPrpJVsaccountInfo.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJVsaccountInfo dbPrpJVsaccountInfo = new DBPrpJVsaccountInfo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJVsaccountInfo.setSchema((PrpJVsaccountInfoSchema)schemas.get(i));
      dbPrpJVsaccountInfo.insert(dbpool);
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
      
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      
      try
      {
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
     *@param serialNo ���
     *@return ��
     */
    public void cancel(DbPool dbpool, String serialNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpJVsaccountInfo WHERE serialNo='" + serialNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param serialNo ���
     *@return ��
     */
    public void cancel(String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, serialNo);
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
     *@param serialNo ���
     *@return ��
     */
    public void getData(String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      getData(dbpool, serialNo);
      dbpool.close();
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param serialNo ���
     *@return ��
     */
    public void getData(DbPool dbpool, String serialNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "serialNo='" + serialNo + "'";
       query(dbpool, strWherePart, 0, null);
    }
    
    /**
     * ��Ԥ�տ���Ϣ�洢�����ñ�
     *@param listAccount �洢��Ԥ�տ���Ϣ
     *@return ��
     */
    public void dorecevieeAccount(List<Map> listAccount) throws Exception {
		String visaCode = "";
		String comCode = "";
		String payDate = "";
		String payOperatorId = "";
		String account = "";
		String proposalNo = "";
		BLVsaccountinfoAction blVsaccountinfoAction = new BLVsaccountinfoAction();
		DbPool dbpool=new DbPool();
		DBPrpJVsaccountInfo dbPrpJVsaccountInfo = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			for (Map vsaccount : listAccount) {
				visaCode = (String) vsaccount.get("visaCode");
				if ((visaCode == null) || (visaCode.equals(""))) {
					throw new UserException(-98, -1200,
							"UIVsAccountInfoAction", "��֤���Ͳ���Ϊ�գ�");
				}
				account=(String) vsaccount.get("account");
				if (Integer.parseInt(account) == 0) {
					throw new UserException(-98, -1200,
							"UIVsAccountInfoAction", "ԤԼ���������Ϊ�գ�");
				}

				proposalNo = (String) vsaccount.get("proposalNo");
				if ((proposalNo == "") || (proposalNo.equals(""))) {
					throw new UserException(-98, -1200,
							"UIVsAccountInfoAction", "XX���Ų���Ϊ�գ�");
				}
				
				String strSequence = getIDSequence(dbpool);
				comCode = (String) vsaccount.get("comCode");
				payDate = (String) vsaccount.get("payDate");
				payOperatorId = (String) vsaccount.get("payOperatorId");
				dbPrpJVsaccountInfo = new DBPrpJVsaccountInfo();
				dbPrpJVsaccountInfo.setSerialNo(strSequence);
				dbPrpJVsaccountInfo.setPayDate(payDate);
				dbPrpJVsaccountInfo.setComCode(comCode);
				dbPrpJVsaccountInfo.setProposalNo(proposalNo);
				dbPrpJVsaccountInfo.setAcount(account);
				dbPrpJVsaccountInfo.setVsaCode(visaCode);
				dbPrpJVsaccountInfo.setFlag("0");
				dbPrpJVsaccountInfo.setPaypersonnel(payOperatorId);
				dbPrpJVsaccountInfo.insert(dbpool);
			}
			dbpool.commitTransaction();
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
	}
    
    /**
     * ��ȡ��������
     * @param dbpool
     * @return
     * @throws Exception
     */
    public String getIDSequence(DbPool dbpool) 
    throws Exception{
       int strCount = 0;
       String strSQL = "";
       ResultSet resultSet = null;
       strSQL=" SELECT SEQ_PRPJVSACCOUNTINFO_SERIALNO.NEXTVAL FROM dual";
       try{
           resultSet = dbpool.query(strSQL);
           if(resultSet.next()){
               strCount = resultSet.getInt(1);
           }
           resultSet.close();
       }catch(Exception e){
           e.printStackTrace();
           throw e;
       } finally {
           if(resultSet != null)
               resultSet.close();          
       }
       return "V"+strCount;
   }
    
      
     
     
    public static void main(String[] args){	
    }
}
