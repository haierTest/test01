package com.sp.prpall.blsvr.jf;

import java.util.ArrayList;
import java.util.List;

import com.sp.prpall.dbsvr.jf.DBPrpJBankCheckDetail;
import com.sp.prpall.schema.PrpJBankCheckDetailSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����PrpJBankCheckDetail-���ж�����Ϣ��ϸ���BL��
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-12-21</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJBankCheckDetail{
    private List schemas = new ArrayList();
    /**
     * ���캯��
     */       
    public BLPrpJBankCheckDetail(){
    }

    /**
     *��ʼ����¼
     *@param ��
     *@return ��
     *@throws Exception
     */
    public void initArr() throws Exception
    {
       schemas = new ArrayList();
     }
    /**
     *����һ��PrpJBankCheckDetailSchema��¼
     *@param iPrpJBankCheckDetailSchema PrpJBankCheckDetailSchema
     *@throws Exception
     */
    public void setArr(PrpJBankCheckDetailSchema iPrpJBankCheckDetailSchema)
			throws Exception {
		try {
			schemas.add(iPrpJBankCheckDetailSchema);
		} catch (Exception e) {
			throw e;
		}
	}
    /**
	 * �õ�һ��PrpJBankCheckDetailSchema��¼
	 * 
	 * @param index �±�
	 * @return һ��PrpJBankCheckDetailSchema����
	 * @throws Exception
	 */
    public PrpJBankCheckDetailSchema getArr(int index) throws Exception {
		PrpJBankCheckDetailSchema prpJBankCheckDetailSchema = null;
		try {
			prpJBankCheckDetailSchema = (PrpJBankCheckDetailSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJBankCheckDetailSchema;
	}
    /**
	 * ɾ��һ��PrpJBankCheckDetailSchema��¼
	 * 
	 * @param index �±�
	 * @throws Exception
	 */
    public void remove(int index) throws Exception {
		try {
			this.schemas.remove(index);
		} catch (Exception e) {
			throw e;
		}
	}
    /**
	 * �õ�schemas��¼��
	 * 
	 * @return schemas��¼��
	 * @throws Exception
	 */
    public int getSize() throws Exception {
		return this.schemas.size();
	}
    /**
	 * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
	 * 
	 * @param iWherePart ��ѯ����(���������־�)
	 * @throws UserException
	 * @throws Exception
	 */
    public void query(String iWherePart) throws UserException, Exception {
		this.query(iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount) throws UserException,
			Exception {
		String strSqlStatement = "";
		DBPrpJBankCheckDetail dbPrpJBankCheckDetail = new DBPrpJBankCheckDetail();
		if (iLimitCount > 0 && dbPrpJBankCheckDetail.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJBankCheckDetail.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJBankCheckDetail WHERE "+ iWherePart;
			schemas = dbPrpJBankCheckDetail.findByConditions(strSqlStatement);
		}
	}
    /**
	 * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
	 * 
	 * @param dbpool ȫ�ֳ�
	 * @param iWherePart ��ѯ����(���������־�)
	 * @throws UserException
	 * @throws Exception
	 */
    public void query(DbPool dbpool, String iWherePart) throws UserException,
			Exception {
		this.query(dbpool, iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount)
			throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpJBankCheckDetail dbPrpJBankCheckDetail = new DBPrpJBankCheckDetail();
		if (iLimitCount > 0 && dbPrpJBankCheckDetail.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJBankCheckDetail.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJBankCheckDetail WHERE " + iWherePart;
			schemas = dbPrpJBankCheckDetail.findByConditions(dbpool,strSqlStatement);
		}
	}
      
    /**
	 * ��dbpool��save����
	 * 
	 * @param ��
	 * @return ��
	 */
	public void save(DbPool dbpool) throws Exception {
		DBPrpJBankCheckDetail dbPrpJBankCheckDetail = new DBPrpJBankCheckDetail();
		int i = 0;
		for (i = 0; i < schemas.size(); i++) {
			dbPrpJBankCheckDetail.setSchema((PrpJBankCheckDetailSchema) schemas.get(i));
			dbPrpJBankCheckDetail.insert(dbpool);
		}
	}
      
    /**
	 * ����dbpool��XXXXX�淽��
	 * 
	 * @param ��
	 * @return ��
	 */
	public void save() throws Exception {
		DbPool dbpool = new DbPool();
		
		dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		
		try {
			dbpool.beginTransaction();
			save(dbpool);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}
      
    /**
	 * ��dbpool��ɾ������
	 * 
	 * @param dbpool ���ӳ�
	 * @param billNo billNo
	 * @return ��
	 */
    public void cancel(DbPool dbpool, String billNo) throws Exception {





    	String strSqlStatement = " DELETE FROM PrpJBankCheckDetail WHERE BillNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, billNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
	}
      
    /**
	 * ����dbpool��ɾ������
	 * 
	 * @param billNo billNo
	 * @return ��
	 */
    public void cancel(String billNo) throws Exception {
		DbPool dbpool = new DbPool();
		
		dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		
		try {
			dbpool.beginTransaction();
			cancel(dbpool, billNo);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}
      
    /**
	 * ��dbpool����billNo��serialNo��ȡ����
	 * 
	 * @param billNo billNo
	 * @param serialNo serialNo
	 * @return ��
	 */
    public void getData(String billNo, String serialNo) throws Exception {
		DbPool dbpool = new DbPool();
		
		dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		
		try{
			getData(dbpool, billNo, serialNo);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			dbpool.close();
		}
	}
      
    /**
	 * ����dbpool����billNo��serialNo��ȡ����
	 * 
	 * @param dbpool ���ӳ�
	 * @param billNo billNo
	 * @param serialNo serialNo
	 * @return ��
	 */
	public void getData(DbPool dbpool, String billNo, String serialNo)
			throws Exception {
		String strWherePart = "";
		strWherePart = "BillNo= '" + billNo + "' AND SerialNo = '" + serialNo+ "'";
		query(dbpool, strWherePart, 0);
	}
    /**
     * @desc  ��ѯ���˵���ϸ��Ϣ
     * @param iBillNo
     * @param iShowType �Ƿ����δ�˶Ի���ȷ���� 0 ������ 1 ����
     * @throws Exception
     */  
    public void billDetailShow(String iBillNo,String iShowType)
    throws Exception{
		DbPool dbpool = new DbPool();
        
        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        
		try{
			billDetailShow(dbpool, iBillNo, iShowType);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			dbpool.close();
		}
    }
    /**
     * @desc  ��ѯ���˵���ϸ��Ϣ
     * @param iBillNo
     * @param iShowType �Ƿ����δ�˶Ի���ȷ���� 0 ������ 1 ����
     * @throws Exception
     */  
    public void billDetailShow(DbPool dbpool,String iBillNo,String iShowType)
    throws Exception{
    	StringBuffer strBuffer = new StringBuffer();
    	strBuffer.append(" BillNo = '");
    	strBuffer.append(iBillNo);
    	strBuffer.append("' ");
    	if(iShowType.equals("0")){
    		
    		strBuffer.append(" AND CheckFlag = '2'");
    	}
    	strBuffer.append(" Order by SerialNo");
    	this.query(dbpool, strBuffer.toString(), 0);
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
