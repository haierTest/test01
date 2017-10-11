package com.sp.prpall.blsvr.jf;

import java.util.Vector;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.schema.PrpJPayBookDetailSchema;
import com.sp.prpall.schema.PrpJPayBookRateItemSchema;
import com.sp.prpall.dbsvr.jf.DBPrpJPayBookDetail;
import com.sp.prpall.dbsvr.jf.DBPrpJPayBookRateItem;

/**
 * ����PrpJPayBook��BL�� <p> Copyright: Copyright (c) 2005 </p> <p>
 * @createdate 2007-12-27 </p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJPayBookRateItem {
	private Vector schemas = new Vector();

	/**
	 * ���캯��
	 */
	public BLPrpJPayBookRateItem() {
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
	 * ����һ��PrpJPayBookRateItemSchema��¼
	 * @param PrpJPayBookRateItemSchema PrpJPayBookRateItemSchema
	 * @throws Exception
	 */
	public void setArr(PrpJPayBookRateItemSchema iPrpJPayBookRateItemSchema) throws Exception {
		try {
			schemas.add(iPrpJPayBookRateItemSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �õ�һ��PrpJPayBookRateItemSchema��¼
	 * @param index �±�
	 * @return һ��PrpJPayBookRateItemSchema����
	 * @throws Exception
	 */
	public PrpJPayBookRateItemSchema getArr(int index) throws Exception {
		PrpJPayBookRateItemSchema prpJPayBookRateItemSchema = null;
		try {
			prpJPayBookRateItemSchema = (PrpJPayBookRateItemSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJPayBookRateItemSchema;
	}

	/**
	 * ɾ��һ��PrpJPayBookRateItemSchema��¼
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
		DBPrpJPayBookRateItem dbPrpJPayBookRateItem = new DBPrpJPayBookRateItem();
		if (iLimitCount > 0 && dbPrpJPayBookRateItem.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "DBPrpJPayBookRateItem.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJPayBookRateItem WHERE " + iWherePart;
			schemas = dbPrpJPayBookRateItem.findByConditions(strSqlStatement);
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
		DBPrpJPayBookRateItem dbPrpJPayBookRateItem = new DBPrpJPayBookRateItem();
		if (iLimitCount > 0 && dbPrpJPayBookRateItem.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJPayBookRateItem.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJPayBookRateItem WHERE " + iWherePart;
			schemas = dbPrpJPayBookRateItem.findByConditions(dbpool, strSqlStatement);
		}
	}
	
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
    	DBPrpJPayBookRateItem dbPrpJPayBookRateItem = new DBPrpJPayBookRateItem();
    	int i = 0;
    	for(i = 0; i< schemas.size(); i++)
    	{
    		dbPrpJPayBookRateItem.setSchema((PrpJPayBookRateItemSchema)schemas.get(i));
    		dbPrpJPayBookRateItem.insert(dbpool);
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
