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
 * 定义PrpJposBack-POS刷卡退款表的BL类
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
	 * 构造函数
	 */
	public BLPrpJposBack() {
	}

	/**
	 * 初始化记录
	 * 
	 * @param 无
	 * @return 无
	 * @throws Exception
	 */
	public void initArr() throws Exception {
		schemas = new ArrayList();
	}

	/**
	 * 增加一条PrpJposBackSchema记录
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
	 * 得到一条PrpJposBackSchema记录
	 * 
	 * @param index 下标
	 * @return 一个PrpJposBackSchema对象
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
	 * 删除一条PrpJposBackSchema记录
	 * 
	 * @param index 下标
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
	 * 得到schemas记录数
	 * 
	 * @return schemas记录数
	 * @throws Exception
	 */
	public int getSize() throws Exception {
		return this.schemas.size();
	}

	/**
	 * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param iWherePart 查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart) throws UserException, Exception {
		this.query(iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param iWherePart 查询条件(包括排序字句)
	 * @param iLimitCount 记录数限制(iLimitCount=0: 无限制)
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
	 * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool 全局池
	 * @param iWherePart 查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart) throws UserException,
			Exception {
		this.query(dbpool, iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool 全局池
	 * @param iWherePart 查询条件(包括排序字句)
	 * @param iLimitCount 记录数限制(iLimitCount=0: 无限制)
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
	 * 带dbpool的save方法
	 * 
	 * @param 无
	 * @return 无
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
	 * 不带dbpool的XXXXX存方法
	 * 
	 * @param 无
	 * @return 无
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
	 * 带dbpool的删除方法
	 * 
	 * @param dbpool 连接池
	 * @param poaCode POS刷卡收据号
	 * @return 无
	 */
	public void cancel(DbPool dbpool, String poaCode) throws Exception {
		DBPrpJposBack dbPrpJposBack = new DBPrpJposBack();
		dbPrpJposBack.delete(poaCode);
	}

	/**
	 * 不带dbpool的删除方法
	 * 
	 * @param poaCode POS刷卡收据号
	 * @return 无
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
	 * 带dbpool根据poaCode获取数据
	 * 
	 * @param poaCode POS刷卡收据号
	 * @return 无
	 */
	public void getData(String poaCode) throws Exception {
		DbPool dbpool = new DbPool();
		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
		getData(dbpool, poaCode);
		dbpool.close();
	}

	/**
	 * 不带dbpool根据poaCode获取数据
	 * 
	 * @param dbpool 连接池
	 * @param poaCode POS刷卡收据号
	 * @return 无
	 */
	public void getData(DbPool dbpool, String poaCode) throws Exception {
		DBPrpJposBack dbPrpJposBack = new DBPrpJposBack();
		dbPrpJposBack.getInfo(poaCode);
	}

	/**
	 * 主函数
	 * 
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		
	}
}
