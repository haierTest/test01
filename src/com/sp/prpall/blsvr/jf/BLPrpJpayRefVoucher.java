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
 * ����BLPrpJpayRefVoucher��BL�� <p> Copyright: Copyright (c) 2005 </p> <p>
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
	 * ���캯��
	 */
	public BLPrpJpayRefVoucher() {
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
	 * ����һ��PrpJpayRefVoucherSchema��¼
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
	 * �õ�һ��PrpJpayRefVoucherSchema��¼
	 * @param index �±�
	 * @return һ��PrpJpayRefVoucherSchema����
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
	 * ɾ��һ��PrpJpayRefVoucherSchema��¼
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
	 * ƾ֤���˲�ѯ�б��嵥
	 * @param iWherePart ��ѯ����
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
	 * ����dbpooƾ֤�������˷���cteated by yanglei 2007-12-28
	 * @param wherePart
	 * @return size ���˳ɹ���
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
	 * ��dbpool ƾ֤�������˷���cteated by yanglei 2007-12-28
	 * @param dbpool ���ݿ����ӳ�
	 * @param wherePart ��ѯ���� *
	 * @param updatedBy ������
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
							"�ո�������Ϣ�����ڴ�ƾ֤��" + VoucherNo);
				} else if (dbPrpJpayRefVoucher.getVoucherStatus().equals("2")) {
					throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.checkAllVoucher()",
							"ƾ֤" + dbPrpJpayRefVoucher.getVoucherNo() + "�Ѵ�أ�");
				} else if (dbPrpJpayRefVoucher.getVoucherFlag().equals("1")) {
					throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.checkAllVoucher()",
							"ƾ֤" + dbPrpJpayRefVoucher.getVoucherNo() + "�Ѽ��ʣ�");
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
								"BLPrpJpayRefVoucher.checkAllVoucher()", "ƾ֤"
										+ intFVoucherSchema.getVoucherNo() + "�Ѹ��ˣ�");
					} else if (intFVoucherSchema.getUploadStatus().equals("1")) {
						throw new UserException(-96, -1167,
								"BLPrpJpayRefVoucher.checkAllVoucher()", "ƾ֤"
										+ intFVoucherSchema.getVoucherNo() + "�Ѵ������ʣ�");
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
	 * ����dbpool��ƾ֤���˳������� cteated by yanglei 2007-01-11
	 * @param ��
	 * @return ��
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
	 * ��dbpool��ƾ֤���˳������� cteated by yanglei 2007-01-11
	 * @param ��
	 * @return ��
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
						"�ո�������Ϣ�����ڴ�ƾ֤��" + VoucherNo);
			} else if (dbPrpJpayRefVoucher.getVoucherFlag().equals("1")) {
				throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.uncheck()", "ƾ֤"
						+ dbPrpJpayRefVoucher.getVoucherNo() + "�Ѽ��ʣ�");
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
							"BLPrpJpayRefVoucher.checkAllVoucher()", "ƾ֤"
									+ intFVoucherSchema.getVoucherNo() + "δ���ˣ�");
				} else if (intFVoucherSchema.getUploadStatus().equals("1")) {
					throw new UserException(-96, -1167,
							"BLPrpJpayRefVoucher.checkAllVoucher()", "ƾ֤"
									+ intFVoucherSchema.getVoucherNo() + "�Ѵ������ʣ�");
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
	 * ����dbpool�ĵ���ƾ֤���˷��� cteated by yanglei 2007-01-11
	 * @param Flag 1-����ȷ�ϣ�0-���˴��
	 * @return ��
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
	 * ��dbpool�ĵ���ƾ֤���˷��� cteated by yanglei 2007-01-11
	 * @param Flag 1-����ȷ�ϣ�0-���˴��
	 * @return ��
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
							"�ո�������Ϣ�����ڴ�ƾ֤��" + VoucherNo);
				} else if (dbPrpJpayRefVoucher.getVoucherFlag().equals("1")) {
					throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.check()", "ƾ֤"
							+ dbPrpJpayRefVoucher.getVoucherNo() + "�Ѽ��ʣ�");
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
								"BLPrpJpayRefVoucher.checkAllVoucher()", "ƾ֤"
										+ intFVoucherSchema.getVoucherNo() + "�Ѹ��ˣ�");
					} else if (intFVoucherSchema.getUploadStatus().equals("1")) {
						throw new UserException(-96, -1167,
								"BLPrpJpayRefVoucher.checkAllVoucher()", "ƾ֤"
										+ intFVoucherSchema.getVoucherNo() + "�Ѵ������ʣ�");
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
							"�ո�������Ϣ�����ڴ�ƾ֤��" + VoucherNo);
				} else if (!dbPrpJpayRefVoucher.getVoucherStatus().equals("0")) {
					throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.check()", "ƾ֤"
							+ dbPrpJpayRefVoucher.getVoucherNo() + "�Ѹ��ˣ�");
				} else if (dbPrpJpayRefVoucher.getVoucherFlag().equals("1")) {
					throw new UserException(-96, -1167, "BLPrpJpayRefVoucher.check()", "ƾ֤"
							+ dbPrpJpayRefVoucher.getVoucherNo() + "�Ѽ��ʣ�");
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
								"BLPrpJpayRefVoucher.checkAllVoucher()", "ƾ֤"
										+ intFVoucherSchema.getVoucherNo() + "�Ѹ��ˣ�");
					} else if (intFVoucherSchema.getUploadStatus().equals("1")) {
						throw new UserException(-96, -1167,
								"BLPrpJpayRefVoucher.checkAllVoucher()", "ƾ֤"
										+ intFVoucherSchema.getVoucherNo() + "�Ѵ������ʣ�");
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
	 * ����DbPool��ȡConnection cteated by yanglei 2007-01-15
	 * @param dbpool���ݿ����ӳ�
	 * @return Connection ���ݿ�����
	 * @throws SQLException ���ݿ�����쳣��
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
	 * �������ʽӿڵĴ洢���� cteated by yanglei 2007-01-15
	 * @param dbpool���ݿ����ӳ�
	 * @param realPayRefNoʵ���ո���
	 * @param voucherNo ƾ֤��
	 * @param centerCode ���㵥λ
	 * @param voucherDate ��֤����
	 * @return String
	 * @throws �쳣��
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
					"�ո���ʽ�޸�ʧ�ܣ�" + messageDesc);
		}
		return messageDesc;
	}

	/**
	 * �ո���ʽ�޸ģ���������ƾ֤ cteated by yanglei 2007-01-15
	 * @return returnMessage ������Ϣ
	 * @throws �쳣��
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
						"�޸��ո���ʽʧ�ܣ�" + prpJpayRefVoucherSchema.getVoucherNo() + "δ��أ�");
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
								"Ԥ���֧��ʧ�ܣ�ԭԤ����ʺ�" + strPoaCode + " ���㣡");
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
						"PrpJpayRefDetail�����ɵ����ݴ���");
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
	 * �����ո����ͺ�ʵ���ո��Ų�ѯҵ����ո�����֮�� cteated by yanglei 2008-03-18
	 * @return strSumPayRefFee �ո�����֮��
	 * @throws �쳣��
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
	 * ƾ֤��ѯ�͸��˹��������ý���ѯ����,
	 * ����ʾƾ֤�б������ӽ����ʾ�� cteated by kangyutao 2008-07-21
	 * @return 
	 * @throws �쳣��
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
