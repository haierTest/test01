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
 * 定义PrpJPayBook的BL类 <p> Copyright: Copyright (c) 2005 </p> <p>
 * @createdate 2007-12-27 </p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJPayBookRateItem {
	private Vector schemas = new Vector();

	/**
	 * 构造函数
	 */
	public BLPrpJPayBookRateItem() {
	}

	/**
	 * 初始化记录
	 * @param 无
	 * @return 无
	 * @throws Exception
	 */
	public void initArr() throws Exception {
		schemas = new Vector();
	}

	/**
	 * 增加一条PrpJPayBookRateItemSchema记录
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
	 * 得到一条PrpJPayBookRateItemSchema记录
	 * @param index 下标
	 * @return 一个PrpJPayBookRateItemSchema对象
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
	 * 删除一条PrpJPayBookRateItemSchema记录
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
	 * @return schemas记录数
	 * @throws Exception
	 */
	public int getSize() throws Exception {
		return this.schemas.size();
	}

	/**
	 * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * @param iWherePart 查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart) throws UserException, Exception {
		this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 * @param iWherePart 查询条件(包括排序字句)
	 * @param iLimitCount 记录数限制(iLimitCount=0: 无限制)
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
	 * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * @param dbpool 全局池
	 * @param iWherePart 查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart) throws UserException, Exception {
		this.query(dbpool, iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT")
				.trim()));
	}

	/**
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 * @param dbpool 全局池
	 * @param iWherePart 查询条件(包括排序字句)
	 * @param iLimitCount 记录数限制(iLimitCount=0: 无限制)
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
     *带dbpool的save方法
     *@param 无
     *@return 无
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
     *不带dbpool的XXXXX存方法
     *@param 无
     *@return 无
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
     * 主函数
     */
	public static void main(String args[]) throws UserException, Exception {
		
	}
}
