package com.sp.prpall.blsvr.jf;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Vector;
import javax.naming.NamingException;

import com.sp.account.blsvr.BLAccSubVoucher;
import com.sp.prpall.dbsvr.jf.DBIntFVoucher;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefDetail;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefVoucher;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaMain;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaTrace;
import com.sp.prpall.schema.IntFVoucherSchema;
import com.sp.prpall.schema.PrpJpayRefDetailSchema;
import com.sp.prpall.schema.PrpJpayRefVoucherSchema;
import com.sp.prpall.schema.PrpJpoaTraceSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 定义BLPrpJpayRefVoucher的BL类 <p> Copyright: Copyright (c) 2005 </p> <p>
 * @createdate 2007-12-27 </p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJpayRefVoucher {
	private Vector schemas = new Vector();
	protected final Log logger = LogFactory.getLog(getClass());

	private BLPrpJpayRefDetail blPrpJpayRefDetail = new BLPrpJpayRefDetail();

	private BLIntFVoucher blIntFVoucher = new BLIntFVoucher();

	private static Connection conn = null;

	private static CallableStatement cstmt = null;

	/**
	 * 构造函数
	 */
	public BLPrpJpayRefVoucher() {
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
	 * 增加一条PrpJpayRefVoucherSchema记录
	 * @param iPrpJpayRefVoucherSchema PrpJpayRefVoucherSchema
	 * @throws Exception
	 */
	public void setArr(PrpJpayRefVoucherSchema iPrpJpayRefVoucherSchema) throws Exception {
		try {
			schemas.add(iPrpJpayRefVoucherSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到一条PrpJpayRefVoucherSchema记录
	 * @param index 下标
	 * @return 一个PrpJpayRefVoucherSchema对象
	 * @throws Exception
	 */
	public PrpJpayRefVoucherSchema getArr(int index) throws Exception {
		PrpJpayRefVoucherSchema prpJpayRefVoucherSchema = null;
		try {
			prpJpayRefVoucherSchema = (PrpJpayRefVoucherSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJpayRefVoucherSchema;
	}

	/**
	 * 删除一条PrpJpayRefVoucherSchema记录
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
		DBPrpJpayRefVoucher dbPrpJpayRefVcouhder = new DBPrpJpayRefVoucher();
		if (iLimitCount > 0 && dbPrpJpayRefVcouhder.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJpayRefVoucher.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJpayRefVoucher WHERE " + iWherePart;
			schemas = dbPrpJpayRefVcouhder.findByConditions(strSqlStatement);
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
		DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
		if (iLimitCount > 0 && dbPrpJpayRefVoucher.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJpayRefVoucher.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJpayRefVoucher WHERE " + iWherePart;
			schemas = dbPrpJpayRefVoucher.findByConditions(dbpool, strSqlStatement);
		}
	}

	public void setBLIntFVoucher(BLIntFVoucher blIntFVoucherTemp) throws Exception {
		try {
			this.blIntFVoucher = blIntFVoucherTemp;
		} catch (Exception e) {
			throw e;
		}
	}

	public BLPrpJpayRefDetail getBLPrpJpayRefDetail() throws Exception {
		try {
			return this.blPrpJpayRefDetail;
		} catch (Exception e) {
			throw e;
		}
	}

	public void setBLPrpJpayRefDetail(BLPrpJpayRefDetail blPrpJpayRefDetailTemp) throws Exception {
		try {
			this.blPrpJpayRefDetail = blPrpJpayRefDetailTemp;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 凭证复核查询列表清单
	 * @param iWherePart 查询条件
	 * @throws Exception
	 */
	public void queryWithDetail(String iWherePart) throws Exception {
		String strSqlStatement = "";
		strSqlStatement = "SELECT PrpJpayRefDetail.PayRefNo,"
				+ " SUM(decode(PrpJpayRefDetail.PayWay,'420',-1,1)*PrpJpayRefDetail.PayRefFee) as sumPayRefFee,PrpJpayRefDetail.Currency2,"
				+ " PrpJpayRefVoucher.RealPayRefNo,PrpJpayRefVoucher.PayRefNoType,"
				+ " PrpJpayRefVoucher.CenterCode,PrpJpayRefVoucher.AccBookType,"
				+ " PrpJpayRefVoucher.AccBookCode,PrpJpayRefVoucher.YearMonth,"
				+ " PrpJpayRefVoucher.VoucherNo,PrpJpayRefVoucher.VoucherDate,"
				+ " PrpJpayRefVoucher.VoucherFlag,PrpJpayRefVoucher.VoucherStatus,"
				+ " PrpJpayRefVoucher.ToCenterCode,PrpJpayRefVoucher.PayRefCode, "
				+ " PrpJpayRefVoucher.ComCode,PrpJpayRefVoucher.Attribute1 "
				+ " FROM PrpJpayRefVoucher,PrpJpayRefDetail "
				+ " WHERE PrpJpayRefVoucher.RealPayRefNo=PrpJpayRefDetail.RealPayRefNo(+) AND "
				+ iWherePart + " GROUP BY PrpJpayRefDetail.PayRefNo,PrpJpayRefDetail.Currency2,"
				+ " PrpJpayRefVoucher.RealPayRefNo,PrpJpayRefVoucher.PayRefNoType,"
				+ " PrpJpayRefVoucher.CenterCode,PrpJpayRefVoucher.AccBookType,"
				+ " PrpJpayRefVoucher.AccBookCode,PrpJpayRefVoucher.YearMonth,"
				+ " PrpJpayRefVoucher.VoucherNo,PrpJpayRefVoucher.VoucherDate,"
				+ " PrpJpayRefVoucher.VoucherFlag,PrpJpayRefVoucher.VoucherStatus,"
				+ " PrpJpayRefVoucher.ToCenterCode,PrpJpayRefVoucher.PayRefCode, "
				+ " PrpJpayRefVoucher.ComCode,PrpJpayRefVoucher.Attribute1 ";
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			PrpJpayRefVoucherSchema prpJpayRefVoucherSchema = null;
			PrpJpayRefDetailSchema prpJpayRefDetailSchema = null;
			ResultSet resultSet = dbpool.query(strSqlStatement);
			while (resultSet.next()) {
				prpJpayRefVoucherSchema = new PrpJpayRefVoucherSchema();
				prpJpayRefDetailSchema = new PrpJpayRefDetailSchema();
				prpJpayRefVoucherSchema.setRealPayRefNo(resultSet.getString("RealPayRefNo"));
				prpJpayRefVoucherSchema.setPayRefNoType(resultSet.getString("PayRefNoType"));
				prpJpayRefVoucherSchema.setCenterCode(resultSet.getString("CenterCode"));
				prpJpayRefVoucherSchema.setAccBookType(resultSet.getString("AccBookType"));
				prpJpayRefVoucherSchema.setAccBookCode(resultSet.getString("AccBookCode"));
				prpJpayRefVoucherSchema.setYearMonth(resultSet.getString("YearMonth"));
				prpJpayRefVoucherSchema.setVoucherNo(resultSet.getString("VoucherNo"));
				prpJpayRefVoucherSchema.setVoucherDate("" + resultSet.getDate("VoucherDate"));
				prpJpayRefVoucherSchema.setVoucherFlag(resultSet.getString("VoucherFlag"));
				prpJpayRefVoucherSchema.setVoucherStatus(resultSet.getString("VoucherStatus"));
				prpJpayRefVoucherSchema.setToCenterCode(resultSet.getString("ToCenterCode"));
				prpJpayRefVoucherSchema.setPayRefCode(resultSet.getString("PayRefCode"));
				prpJpayRefVoucherSchema.setComCode(resultSet.getString("ComCode"));
				prpJpayRefVoucherSchema.setAttribute1(resultSet.getString("Attribute1"));
				prpJpayRefDetailSchema.setPayRefFee("" + resultSet.getDouble("sumPayRefFee"));
				prpJpayRefDetailSchema.setCurrency2(resultSet.getString("Currency2"));
				prpJpayRefDetailSchema.setPayRefNo(resultSet.getString("PayRefNo"));

				this.blPrpJpayRefDetail.setArr(prpJpayRefDetailSchema);
				this.setArr(prpJpayRefVoucherSchema);
			}
			resultSet.close();
			dbpool.close();
		} catch (SQLException sqlException) {
			dbpool.close();
			throw sqlException;
		} catch (NamingException namingException) {
			dbpool.close();
			throw namingException;
		} catch (Exception e) {
			dbpool.close();
			throw e;
		} finally {
			dbpool.close();
		}
	}

	/**
	 * 不带dbpoo凭证批量复核方法cteated by yanglei 2007-12-28
	 * @param wherePart
	 * @return size 复核成功数
	 * @throws Exception
	 */
	public int checkAllVoucher() throws UserException, Exception {
		DbPool dbpool = new DbPool();
		int zize = 0;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			zize = this.checkAllVoucher(dbpool);
			dbpool.commitTransaction();
			return zize;
		} catch (UserException ue) {
			dbpool.rollbackTransaction();
			throw ue;
		} catch (Exception e) {
			dbpool.rollbackTransaction();
			throw e;
		} finally {
			dbpool.close();
		}
	}

	/**
	 * 带dbpool 凭证批量复核方法cteated by yanglei 2007-12-28
	 * @param dbpool 数据库连接池
	 * @param wherePart 查询条件 *
	 * @param updatedBy 复核人
	 * @throws Exception
	 */
	public int checkAllVoucher(DbPool dbpool) throws UserException, Exception {
		int size = 0;
		String VoucherNo = "";
		DateTime now = new DateTime(new Date(), DateTime.YEAR_TO_SECOND);
		try {
			for (int i = 0; i < this.getSize(); i++) {
				VoucherNo = this.blIntFVoucher.getArr(i).getVoucherNo();
				DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
				int iResult = dbPrpJpayRefVoucher.getInfo(dbpool, this.getArr(i).getRealPayRefNo());
				if (iResult == 100) {
					throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.checkAllVoucher()",
							"收付对照信息不存在此凭证：" + VoucherNo);
				} else if (dbPrpJpayRefVoucher.getVoucherStatus().equals("2")) {
					throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.checkAllVoucher()",
							"凭证" + dbPrpJpayRefVoucher.getVoucherNo() + "已打回！");
				} else if (dbPrpJpayRefVoucher.getVoucherFlag().equals("1")) {
					throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.checkAllVoucher()",
							"凭证" + dbPrpJpayRefVoucher.getVoucherNo() + "已记帐！");
				}
				
				BLIntFVoucher blIntFVoucher = new BLIntFVoucher();
				DBIntFVoucher dbIntFVoucher = new DBIntFVoucher();
				String strWhere = " RealPayNo = '" + this.getArr(i).getRealPayRefNo() + "'";
				blIntFVoucher.query(dbpool,strWhere);
				for (int j = 0; j < blIntFVoucher.getSize(); j++) {
					IntFVoucherSchema intFVoucherSchema = new IntFVoucherSchema();
					intFVoucherSchema = blIntFVoucher.getArr(j);
					if (!intFVoucherSchema.getJournalStatus().equals("0")) {
						throw new UserException(-96, -1167,
								"BLPrpJpayRefVoucher.checkAllVoucher()", "凭证"
										+ intFVoucherSchema.getVoucherNo() + "已复核！");
					} else if (intFVoucherSchema.getUploadStatus().equals("1")) {
						throw new UserException(-96, -1167,
								"BLPrpJpayRefVoucher.checkAllVoucher()", "凭证"
										+ intFVoucherSchema.getVoucherNo() + "已传入总帐！");
					}
					intFVoucherSchema.setJournalStatus("1");
					intFVoucherSchema.setUpdatedBy(this.blIntFVoucher.getArr(i).getUpdatedBy());
					intFVoucherSchema.setUpdatedDate(now.toString(DateTime.YEAR_TO_DAY));
					dbIntFVoucher.setSchema(intFVoucherSchema);
					dbIntFVoucher.update(dbpool);
				}
				size++;
			}
			return size;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 不带dbpool的凭证复核撤销方法 cteated by yanglei 2007-01-11
	 * @param 无
	 * @return 无
	 */
	public void uncheck() throws UserException, Exception {
		DbPool dbpool = new DbPool();
		dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
		try {
			dbpool.beginTransaction();
			uncheck(dbpool);
			dbpool.commitTransaction();
		} catch (UserException ue) {
			dbpool.rollbackTransaction();
			ue.printStackTrace();
			throw ue;
		} catch (Exception e) {
			dbpool.rollbackTransaction();
			e.printStackTrace();
			throw e;
		} finally {
			dbpool.close();
		}
	}

	/**
	 * 带dbpool的凭证复核撤销方法 cteated by yanglei 2007-01-11
	 * @param 无
	 * @return 无
	 */
	public void uncheck(DbPool dbpool) throws UserException, Exception {
		String VoucherNo = "";
		DateTime now = new DateTime(new Date(), DateTime.YEAR_TO_SECOND);
		try {
			VoucherNo = this.blIntFVoucher.getArr(0).getVoucherNo();
			DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
			int iResult = dbPrpJpayRefVoucher.getInfo(dbpool, this.getArr(0).getRealPayRefNo());
			if (iResult == 100) {
				throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.uncheck()",
						"收付对照信息不存在此凭证：" + VoucherNo);
			} else if (dbPrpJpayRefVoucher.getVoucherFlag().equals("1")) {
				throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.uncheck()", "凭证"
						+ dbPrpJpayRefVoucher.getVoucherNo() + "已记帐！");
			}
			
			dbPrpJpayRefVoucher.setVoucherStatus("0");
			dbPrpJpayRefVoucher.update(dbpool);
			
			BLIntFVoucher blIntFVoucher = new BLIntFVoucher();
			DBIntFVoucher dbIntFVoucher = new DBIntFVoucher();
			String strWhere = " RealPayNo = '" + this.getArr(0).getRealPayRefNo() + "'";
			blIntFVoucher.query(dbpool,strWhere);
			for (int j = 0; j < blIntFVoucher.getSize(); j++) {
				IntFVoucherSchema intFVoucherSchema = new IntFVoucherSchema();
				intFVoucherSchema = blIntFVoucher.getArr(j);
				if (intFVoucherSchema.getJournalStatus().equals("0")) {
					throw new UserException(-96, -1167,
							"BLPrpJpayRefVoucher.checkAllVoucher()", "凭证"
									+ intFVoucherSchema.getVoucherNo() + "未复核！");
				} else if (intFVoucherSchema.getUploadStatus().equals("1")) {
					throw new UserException(-96, -1167,
							"BLPrpJpayRefVoucher.checkAllVoucher()", "凭证"
									+ intFVoucherSchema.getVoucherNo() + "已传入总帐！");
				}
				intFVoucherSchema.setJournalStatus("0");
				intFVoucherSchema.setUpdatedBy(this.blIntFVoucher.getArr(0).getUpdatedBy());
				intFVoucherSchema.setUpdatedDate(now.toString(DateTime.YEAR_TO_DAY));
				dbIntFVoucher.setSchema(intFVoucherSchema);
				dbIntFVoucher.update(dbpool);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 不带dbpool的单张凭证复核方法 cteated by yanglei 2007-01-11
	 * @param Flag 1-复核确认，0-复核打回
	 * @return 无
	 */
	public void check(String Flag) throws UserException, Exception {
		DbPool dbpool = new DbPool();
		dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
		try {
			dbpool.beginTransaction();
			check(dbpool, Flag);
			dbpool.commitTransaction();
		} catch (UserException ue) {
			dbpool.rollbackTransaction();
			ue.printStackTrace();
			throw ue;
		} catch (Exception e) {
			dbpool.rollbackTransaction();
			e.printStackTrace();
			throw e;
		} finally {
			dbpool.close();
		}
	}

	/**
	 * 带dbpool的单张凭证复核方法 cteated by yanglei 2007-01-11
	 * @param Flag 1-复核确认，0-复核打回
	 * @return 无
	 */
	public void check(DbPool dbpool, String Flag) throws UserException, Exception {
		String VoucherNo = "";
		DateTime now = new DateTime(new Date(), DateTime.YEAR_TO_SECOND);
		try {
			int iResult = 0;
			VoucherNo = this.blIntFVoucher.getArr(0).getVoucherNo();
			if (Flag.equals("1")) {
				DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
				iResult = dbPrpJpayRefVoucher.getInfo(dbpool, this.getArr(0).getRealPayRefNo());
				if (iResult == 100) {
					throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.check()",
							"收付对照信息不存在此凭证：" + VoucherNo);
				} else if (dbPrpJpayRefVoucher.getVoucherFlag().equals("1")) {
					throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.check()", "凭证"
							+ dbPrpJpayRefVoucher.getVoucherNo() + "已记帐！");
				}
				
				BLIntFVoucher blIntFVoucher = new BLIntFVoucher();
				DBIntFVoucher dbIntFVoucher = new DBIntFVoucher();
				String strWhere = " RealPayNo = '" + this.getArr(0).getRealPayRefNo() + "'";
				blIntFVoucher.query(dbpool,strWhere);
				for (int j = 0; j < blIntFVoucher.getSize(); j++) {
					IntFVoucherSchema intFVoucherSchema = new IntFVoucherSchema();
					intFVoucherSchema = blIntFVoucher.getArr(j);
					if (!intFVoucherSchema.getJournalStatus().equals("0")) {
						throw new UserException(-96, -1167,
								"BLPrpJpayRefVoucher.checkAllVoucher()", "凭证"
										+ intFVoucherSchema.getVoucherNo() + "已复核！");
					} else if (intFVoucherSchema.getUploadStatus().equals("1")) {
						throw new UserException(-96, -1167,
								"BLPrpJpayRefVoucher.checkAllVoucher()", "凭证"
										+ intFVoucherSchema.getVoucherNo() + "已传入总帐！");
					}
					intFVoucherSchema.setJournalStatus("1");
					intFVoucherSchema.setUpdatedBy(this.blIntFVoucher.getArr(0).getUpdatedBy());
					intFVoucherSchema.setUpdatedDate(now.toString(DateTime.YEAR_TO_DAY));
					dbIntFVoucher.setSchema(intFVoucherSchema);
					dbIntFVoucher.update(dbpool);
				}
			} else {
				DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
				iResult = dbPrpJpayRefVoucher.getInfo(dbpool, this.getArr(0).getRealPayRefNo());
				if (iResult == 100) {
					throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.check()",
							"收付对照信息不存在此凭证：" + VoucherNo);
				} else if (!dbPrpJpayRefVoucher.getVoucherStatus().equals("0")) {
					throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.check()", "凭证"
							+ dbPrpJpayRefVoucher.getVoucherNo() + "已复核！");
				} else if (dbPrpJpayRefVoucher.getVoucherFlag().equals("1")) {
					throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.check()", "凭证"
							+ dbPrpJpayRefVoucher.getVoucherNo() + "已记帐！");
				}
				
				dbPrpJpayRefVoucher.setVoucherStatus("2");
				
				dbPrpJpayRefVoucher.setAttribute1(this.getArr(0).getAttribute1());
				dbPrpJpayRefVoucher.update(dbpool);
				
				BLIntFVoucher blIntFVoucher = new BLIntFVoucher();
				DBIntFVoucher dbIntFVoucher = new DBIntFVoucher();
				String strWhere = " RealPayNo = '" + this.getArr(0).getRealPayRefNo() + "'";
				blIntFVoucher.query(dbpool,strWhere);
				for (int j = 0; j < blIntFVoucher.getSize(); j++) {
					IntFVoucherSchema intFVoucherSchema = new IntFVoucherSchema();
					intFVoucherSchema = blIntFVoucher.getArr(j);
					if (!intFVoucherSchema.getJournalStatus().equals("0")) {
						throw new UserException(-96, -1167,
								"BLPrpJpayRefVoucher.checkAllVoucher()", "凭证"
										+ intFVoucherSchema.getVoucherNo() + "已复核！");
					} else if (intFVoucherSchema.getUploadStatus().equals("1")) {
						throw new UserException(-96, -1167,
								"BLPrpJpayRefVoucher.checkAllVoucher()", "凭证"
										+ intFVoucherSchema.getVoucherNo() + "已传入总帐！");
					}
					intFVoucherSchema.setJournalStatus("2");
					intFVoucherSchema.setUpdatedBy(this.blIntFVoucher.getArr(0).getUpdatedBy());
					intFVoucherSchema.setUpdatedDate(now.toString(DateTime.YEAR_TO_DAY));
					dbIntFVoucher.setSchema(intFVoucherSchema);
					dbIntFVoucher.update(dbpool);
				}
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 根据DbPool获取Connection cteated by yanglei 2007-01-15
	 * @param dbpool数据库连接池
	 * @return Connection 数据库连接
	 * @throws SQLException 数据库操作异常类
	 */
	private Connection getConnection(DbPool dbpool) {
		Connection connection = null;
		try {
			DBManager dbManager = dbpool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);
			connection = dbManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 调用总帐接口的存储过程 cteated by yanglei 2007-01-15
	 * @param dbpool数据库连接池
	 * @param realPayRefNo实际收付号
	 * @param voucherNo 凭证号
	 * @param centerCode 核算单位
	 * @param voucherDate 制证日期
	 * @return String
	 * @throws 异常类
	 */
	public String callProcedure(DbPool dbpool, String realPayRefNo, String voucherNo,
			String centerCode, String voucherDate) throws Exception {
		String messageCode = "";
		String messageDesc = "";
		conn = this.getConnection(dbpool);
		cstmt = conn.prepareCall("{call sunshvoucrt_pkg.payment_voucher_modify(?,?,?,?,?,?)}");
		cstmt.registerOutParameter(5, Types.VARCHAR);
		cstmt.registerOutParameter(6, Types.VARCHAR);
		cstmt.setString(1, realPayRefNo);
		cstmt.setString(2, voucherNo);
		cstmt.setString(3, centerCode);
		cstmt.setString(4, voucherDate);
		cstmt.setString(5, messageCode);
		cstmt.setString(6, messageDesc);
		cstmt.execute();
		messageCode = cstmt.getString(5);
		messageDesc = cstmt.getString(6);
		if(!messageCode.equals("0")){
			throw new UserException(-96, -1167, "BLPrpJpaymantVoucher.callProcedure()",
					"收付方式修改失败：" + messageDesc);
		}
		return messageDesc;
	}

	/**
	 * 收付方式修改，重新生成凭证 cteated by yanglei 2007-01-15
	 * @return returnMessage 返回信息
	 * @throws 异常类
	 */
	public String changePayWay(String strAuxNumber) throws UserException, Exception {
		String returnMessage = "";
		DbPool dbpool = new DbPool();
		dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
		try {
			dbpool.beginTransaction();
			String strRealPayRefNo = this.getArr(0).getRealPayRefNo();
			BLPrpDcode blPrpDcode = new BLPrpDcode();
			DBPrpJpayRefVoucher dBPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
			PrpJpayRefVoucherSchema prpJpayRefVoucherSchema = new PrpJpayRefVoucherSchema();
			prpJpayRefVoucherSchema = this.getArr(0);
			if (!prpJpayRefVoucherSchema.getVoucherStatus().equals("2")) {
				throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.changePayWay()",
						"修改收付方式失败：" + prpJpayRefVoucherSchema.getVoucherNo() + "未打回！");
			}
			
			prpJpayRefVoucherSchema.setVoucherStatus("0");
			prpJpayRefVoucherSchema.setAttribute1("");
			prpJpayRefVoucherSchema.setAttribute2(strAuxNumber);
			dBPrpJpayRefVoucher.setSchema(prpJpayRefVoucherSchema);
			dBPrpJpayRefVoucher.update(dbpool);

			String strCondition = "RealPayRefNo = '" + strRealPayRefNo + "'";
			BLPrpJpayRefDetail blPrpJpayRefDetailTemp = new BLPrpJpayRefDetail();
			blPrpJpayRefDetailTemp.query(dbpool, strCondition);
			for (int i = 0; i < blPrpJpayRefDetailTemp.getSize(); i++) {
				if (blPrpJpayRefDetailTemp.getArr(i).getPayWay().substring(0, 1).equals("4")
						&&!blPrpJpayRefDetailTemp.getArr(i).getPayWay().equals("421")) {
					String strPoaCode = blPrpJpayRefDetailTemp.getArr(i).getFlag1();
					DBPrpJpoaMain dbPrpJpoaMainTemp = new DBPrpJpoaMain();
					dbPrpJpoaMainTemp.getInfo(dbpool, strPoaCode);
					double dblPayRefFee = Double.parseDouble(blPrpJpayRefDetailTemp.getArr(i)
							.getPayRefFee());
					double dblDeliqueeFee = dbPrpJpoaMainTemp.getDeliqueefee() + dblPayRefFee;
					if (dblDeliqueeFee < 0) {
						throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.changePayWay()",
								"预存款支付失败：原预存款帐号" + strPoaCode + " 余额不足！");
					}
					dbPrpJpoaMainTemp.setDeliqueefee(dblDeliqueeFee);
					dbPrpJpoaMainTemp.update(dbpool);
				}
				
				DBPrpJpayRefDetail dbPrpJpayDetailTemp = new DBPrpJpayRefDetail();
				dbPrpJpayDetailTemp.delete(dbpool, blPrpJpayRefDetailTemp.getArr(i).getPayRefNo(),
						blPrpJpayRefDetailTemp.getArr(i).getSerialNo());
			}
			
			BLPrpJpoaTrace blPrpJpoaTrace = new BLPrpJpoaTrace();
			blPrpJpoaTrace.query(dbpool, strCondition);
			for (int i = 0; i < blPrpJpoaTrace.getSize(); i++) {
				DBPrpJpoaTrace dbPrpJpoaTraceTemp = new DBPrpJpoaTrace();
				dbPrpJpoaTraceTemp.delete(dbpool, blPrpJpoaTrace.getArr(i).getPoacode(),
						blPrpJpoaTrace.getArr(i).getSerailno());
			}
			
			if (this.blPrpJpayRefDetail.getSize() == 0) {
				throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.changePayWay()",
						"PrpJpayRefDetail表生成的数据错误！");
			}
			for (int i = 0; i < this.blPrpJpayRefDetail.getSize(); i++) {
				this.blPrpJpayRefDetail.getArr(i).setRealPayRefNo(strRealPayRefNo);
				this.blPrpJpayRefDetail.getArr(i).setPayRefFee(
						""+ Math.abs(Double.parseDouble(this.blPrpJpayRefDetail.getArr(i).getPayRefFee())));
				
				if (this.blPrpJpayRefDetail.getArr(i).getPayWay().substring(0, 1).equals("4")
						&&!blPrpJpayRefDetail.getArr(i).getPayWay().equals("421")) {
					BLPrpJpoaMain blPrpJpoaMain = new BLPrpJpoaMain();
					blPrpJpoaMain.voucherVerifyPoa(dbpool,this.blPrpJpayRefDetail.getArr(i));
				}
			}
			this.blPrpJpayRefDetail.save(dbpool);
			
			returnMessage = callProcedure(dbpool, strRealPayRefNo, prpJpayRefVoucherSchema
					.getVoucherNo(), prpJpayRefVoucherSchema.getCenterCode(),
					prpJpayRefVoucherSchema.getVoucherDate());
			dbpool.commitTransaction();
			return returnMessage;
		} catch (UserException ue) {
			dbpool.rollbackTransaction();
			ue.printStackTrace();
			throw ue;
		} catch (Exception e) {
			dbpool.rollbackTransaction();
			e.printStackTrace();
			throw e;
		} finally {
			dbpool.close();
		}
	}
	
	/**
	 * 根据收付类型和实际收付号查询业务表收付费用之和 cteated by yanglei 2008-03-18
	 * @return strSumPayRefFee 收付费用之和
	 * @throws 异常类
	 */
	public String querySumPayRefFee(String strRealPayRefNo,String strPayRefNoType) throws UserException, Exception {
		String strSumPayRefFee = "0";
		String tableName = "";
		String sql = "";
		DbPool dbpool = new DbPool();
		
		dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));
		try {
			if (strPayRefNoType.equals("A")) {
				tableName = "PRPJINVEST";
			} else if (strPayRefNoType.equals("B")) {
				tableName = "PRPJPAYINVEST";
			} else if (strPayRefNoType.equals("3")) {
				tableName = "PRPJPAYCOMMISSION";
			} else {
				tableName = "PRPJPAYREFREC";
			}
			sql = "SELECT SUM(PAYREFFEE) SUMPAYREFFEE FROM " + tableName
					+ " WHERE REALPAYREFNO = '" + strRealPayRefNo + "'";
			ResultSet resultSet = dbpool.query(sql);
			while (resultSet.next()) {
				strSumPayRefFee = resultSet.getString("SUMPAYREFFEE");
			}
			resultSet.close();
		} catch (Exception exception) {
			throw exception;
		} finally {
			dbpool.close();
		}
		return strSumPayRefFee;
	}
	/**
	 * 凭证查询和复核功能下设置金额查询功能,
	 * 在显示凭证列表中增加金额显示列 cteated by kangyutao 2008-07-21
	 * @return 
	 * @throws 异常类
	 */
	public Vector queryAmount(String strwhere) throws UserException, Exception {
		String sqlcondition = "";
		DbPool dbpool = new DbPool();
		
		dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));
		Vector resultVector = new Vector();
		String ststrwhere[]=strwhere.split(":");
		try {
			sqlcondition = "select voucherno,segment1,sum(entereddr) amount from IntFVoudtl";
			String groupstr = "group by voucherno,segment1";
			IntFVoucherSchema intFVoucherSchema = null;
			if(strwhere != null){
				sqlcondition +=ststrwhere[0];
			}
			if(ststrwhere.length==2){
				groupstr += (" having "+ststrwhere[1]);
			}
			if(ststrwhere.length==3){
				groupstr += (" having "+ststrwhere[1]);
				groupstr += (" and "+ststrwhere[2]);
			}			
			
			logger.info("sql:"+sqlcondition+groupstr);
			
			ResultSet resultSet = dbpool.query(sqlcondition+groupstr);
			while (resultSet.next()) {
				intFVoucherSchema = new IntFVoucherSchema();
				intFVoucherSchema.setVoucherNo(resultSet.getString("VOUCHERNO"));
				intFVoucherSchema.setSegment1(resultSet.getString("SEGMENT1"));
				intFVoucherSchema.setRealPayNo(resultSet.getString("AMOUNT"));
				resultVector.add(intFVoucherSchema);
			}
			resultSet.close();
			return resultVector;
		} catch (Exception exception) {
			throw exception;
		} finally {
			dbpool.close();
		}
	}	
}
