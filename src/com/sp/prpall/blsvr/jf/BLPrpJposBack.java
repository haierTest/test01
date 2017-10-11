package com.sp.prpall.blsvr.jf;

import java.util.ArrayList;
import java.util.List;

import com.sp.prpall.dbsvr.jf.DBPrpJposBack;
import com.sp.prpall.schema.PrpJposBackSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����PrpJposBack-POSˢ���˿���BL��
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * 
 * @createdate 2007-11-30
 *             </p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJposBack {
	private List schemas = new ArrayList();

	/**
	 * ���캯��
	 */
	public BLPrpJposBack() {
	}

	/**
	 * ��ʼ����¼
	 * 
	 * @param ��
	 * @return ��
	 * @throws Exception
	 */
	public void initArr() throws Exception {
		schemas = new ArrayList();
	}

	/**
	 * ����һ��PrpJposBackSchema��¼
	 * 
	 * @param iPrpJposBackSchema PrpJposBackSchema
	 * @throws Exception
	 */
	public void setArr(PrpJposBackSchema iPrpJposBackSchema) throws Exception {
		try {
			schemas.add(iPrpJposBackSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �õ�һ��PrpJposBackSchema��¼
	 * 
	 * @param index �±�
	 * @return һ��PrpJposBackSchema����
	 * @throws Exception
	 */
	public PrpJposBackSchema getArr(int index) throws Exception {
		PrpJposBackSchema prpJposBackSchema = null;
		try {
			prpJposBackSchema = (PrpJposBackSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJposBackSchema;
	}

	/**
	 * ɾ��һ��PrpJposBackSchema��¼
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
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * 
	 * @param iWherePart ��ѯ����(���������־�)
	 * @param iLimitCount ��¼������(iLimitCount=0: ������)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart, int iLimitCount) throws UserException,
			Exception {
		String strSqlStatement = "";
		DBPrpJposBack dbPrpJposBack = new DBPrpJposBack();
		if (iLimitCount > 0 && dbPrpJposBack.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJposBack.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJposBack WHERE " + iWherePart;
			schemas = dbPrpJposBack.findByConditions(strSqlStatement);
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
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * 
	 * @param dbpool ȫ�ֳ�
	 * @param iWherePart ��ѯ����(���������־�)
	 * @param iLimitCount ��¼������(iLimitCount=0: ������)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, int iLimitCount)
			throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpJposBack dbPrpJposBack = new DBPrpJposBack();
		if (iLimitCount > 0 && dbPrpJposBack.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJposBack.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJposBack WHERE " + iWherePart;
			schemas = dbPrpJposBack.findByConditions(dbpool, strSqlStatement);
		}
	}

	/**
	 * ��dbpool��save����
	 * 
	 * @param ��
	 * @return ��
	 */
	public void save(DbPool dbpool) throws Exception {
		DBPrpJposBack dbPrpJposBack = new DBPrpJposBack();

		int i = 0;

		for (i = 0; i < schemas.size(); i++) {
			dbPrpJposBack.setSchema((PrpJposBackSchema) schemas.get(i));
			dbPrpJposBack.insert(dbpool);
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

		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);

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
	 * @param poaCode POSˢ���վݺ�
	 * @return ��
	 */
	public void cancel(DbPool dbpool, String poaCode) throws Exception {
		DBPrpJposBack dbPrpJposBack = new DBPrpJposBack();
		dbPrpJposBack.delete(poaCode);
	}

	/**
	 * ����dbpool��ɾ������
	 * 
	 * @param poaCode POSˢ���վݺ�
	 * @return ��
	 */
	public void cancel(String poaCode) throws Exception {
		DbPool dbpool = new DbPool();

		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
		try {
			dbpool.beginTransaction();
			cancel(dbpool, poaCode);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}

	/**
	 * ��dbpool����poaCode��ȡ����
	 * 
	 * @param poaCode POSˢ���վݺ�
	 * @return ��
	 */
	public void getData(String poaCode) throws Exception {
		DbPool dbpool = new DbPool();
		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
		getData(dbpool, poaCode);
		dbpool.close();
	}

	/**
	 * ����dbpool����poaCode��ȡ����
	 * 
	 * @param dbpool ���ӳ�
	 * @param poaCode POSˢ���վݺ�
	 * @return ��
	 */
	public void getData(DbPool dbpool, String poaCode) throws Exception {
		DBPrpJposBack dbPrpJposBack = new DBPrpJposBack();
		dbPrpJposBack.getInfo(poaCode);
	}

	/**
	 * ������
	 * 
	 * @param args �����б�
	 */
	public static void main(String[] args) {
		
	}
}
