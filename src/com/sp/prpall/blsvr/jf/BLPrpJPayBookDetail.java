package com.sp.prpall.blsvr.jf;

import java.util.Vector;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.schema.PrpJPayBookDetailSchema;
import com.sp.prpall.schema.PrpJPayBookRateItemSchema;
import com.sp.prpall.schema.PrpJplanFeeSchema;
import com.sp.prpall.dbsvr.jf.DBPrpJPayBookDetail;
import com.sp.prpall.dbsvr.jf.DBPrpJPayBookRateItem;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFee;

/**
 * ����PrpJPayBook��BL�� <p> Copyright: Copyright (c) 2005 </p> <p>
 * @createdate 2007-12-27 </p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJPayBookDetail {
	private Vector schemas = new Vector();

	/**
	 * ���캯��
	 */
	public BLPrpJPayBookDetail() {
	}

	/**
	 * ��ʼ����¼
	 * @param ��
	 * @return ��
	 * @throws Exception
	 */
	public void initArr() throws Exception {
		schemas = new Vector();
	}

	/**
	 * ����һ��PrpJPayBookDetailSchema��¼
	 * @param PrpJPayBookDetailSchema PrpJPayBookDetailSchema
	 * @throws Exception
	 */
	public void setArr(PrpJPayBookDetailSchema iPrpJPayBookDetailSchema) throws Exception {
		try {
			schemas.add(iPrpJPayBookDetailSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �õ�һ��PrpJPayBookDetailSchema��¼
	 * @param index �±�
	 * @return һ��PrpJPayBookDetailSchema����
	 * @throws Exception
	 */
	public PrpJPayBookDetailSchema getArr(int index) throws Exception {
		PrpJPayBookDetailSchema prpJPayBookDetailSchema = null;
		try {
			prpJPayBookDetailSchema = (PrpJPayBookDetailSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJPayBookDetailSchema;
	}

	/**
	 * ɾ��һ��PrpJPayBookDetailSchema��¼
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
	 * @return schemas��¼��
	 * @throws Exception
	 */
	public int getSize() throws Exception {
		return this.schemas.size();
	}

	/**
	 * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
	 * @param iWherePart ��ѯ����(���������־�)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart) throws UserException, Exception {
		this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * @param iWherePart ��ѯ����(���������־�)
	 * @param iLimitCount ��¼������(iLimitCount=0: ������)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart, int iLimitCount) throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpJPayBookDetail dbPrpJPayBookDetail = new DBPrpJPayBookDetail();
		if (iLimitCount > 0 && dbPrpJPayBookDetail.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "DBPrpJPayBookDetail.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJPayBookDetail WHERE " + iWherePart;
			schemas = dbPrpJPayBookDetail.findByConditions(strSqlStatement);
		}
	}

	/**
	 * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
	 * @param dbpool ȫ�ֳ�
	 * @param iWherePart ��ѯ����(���������־�)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart) throws UserException, Exception {
		this.query(dbpool, iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT")
				.trim()));
	}

	/**
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * @param dbpool ȫ�ֳ�
	 * @param iWherePart ��ѯ����(���������־�)
	 * @param iLimitCount ��¼������(iLimitCount=0: ������)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, int iLimitCount) throws UserException,
			Exception {
		String strSqlStatement = "";
		DBPrpJPayBookDetail dbPrpJPayBookDetail = new DBPrpJPayBookDetail();
		if (iLimitCount > 0 && dbPrpJPayBookDetail.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJPayBookDetail.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJPayBookDetail WHERE " + iWherePart;
			schemas = dbPrpJPayBookDetail.findByConditions(dbpool, strSqlStatement);
		}
	}
	
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
    	DBPrpJPayBookDetail dbPrpJPayBookDetail = new DBPrpJPayBookDetail();
    	int i = 0;
    	for(i = 0; i< schemas.size(); i++)
    	{
    		dbPrpJPayBookDetail.setSchema((PrpJPayBookDetailSchema)schemas.get(i));
    		dbPrpJPayBookDetail.insert(dbpool);
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
    	try {
    		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
    		dbpool.beginTransaction();
    		save(dbpool);
    		dbpool.commitTransaction();
    	}
    	catch (Exception e)
    	{
    		dbpool.rollbackTransaction();
    	}
    	finally {
    		dbpool.close();
    	}
    }

    /**
     * ������
     */
	public static void main(String args[]) throws UserException, Exception {
		
	}
}
