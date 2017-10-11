package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.Str;
import com.sp.prpall.blsvr.cb.BLPrpCfee;
import com.sp.prpall.blsvr.cb.BLPrpCitemKind;
import com.sp.prpall.blsvr.pg.BLPrpPfee;
import com.sp.prpall.blsvr.pg.BLPrpPitemKind;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.cb.DBPrpCplan;
import com.sp.prpall.dbsvr.misc.DBPrpCommissionDetailSub;
import com.sp.prpall.dbsvr.pg.DBPrpPmain;
import com.sp.prpall.schema.PrpCitemKindSchema;
import com.sp.prpall.schema.PrpCommissionDetailSubSchema;
import com.sp.prpall.schema.PrpCommissionSubSchema;
import com.sp.prpall.schema.PrpMiddleCostDetailSchema;
import com.sp.prpall.schema.PrpPitemKindSchema;

/**
 * 定义PrpCommissionDetailSub的BL类 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-03-01</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 */
public class BLPrpCommissionDetailSub {
	private Vector schemas = new Vector();

	/**
	 * 构造函数
	 */
	public BLPrpCommissionDetailSub() {
	}

	/**
	 * 初始化记录
	 * 
	 * @param 无
	 * @return 无
	 * @throws Exception
	 */
	public void initArr() throws Exception {
		schemas = new Vector();
	}

	/**
	 * 增加一条PrpCommissionDetailSubSchema记录
	 * @param iPrpCommissionDetailSubSchema PrpCommissionDetailSubSchema
	 * @throws Exception
	 */
	public void setArr(PrpCommissionDetailSubSchema iPrpCommissionDetailSubSchema) throws Exception {
		try {
			schemas.add(iPrpCommissionDetailSubSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到一条PrpCommissionDetailSubSchema记录
	 * @param index 下标
	 * @return 一个PrpCommissionDetailSubSchema对象
	 * @throws Exception
	 */
	public PrpCommissionDetailSubSchema getArr(int index) throws Exception {
		PrpCommissionDetailSubSchema prpCommissionDetailSubSchema = null;
		try {
			prpCommissionDetailSubSchema = (PrpCommissionDetailSubSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpCommissionDetailSubSchema;
	}

	/**
	 * 删除一条PrpCommissionDetailSubSchema记录
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
		DBPrpCommissionDetailSub dbPrpCommissionDetailSub = new DBPrpCommissionDetailSub();
		if (iLimitCount > 0 && dbPrpCommissionDetailSub.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpCommissionDetailSub.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpCommissionDetailSub WHERE " + iWherePart;
			schemas = dbPrpCommissionDetailSub.findByConditions(strSqlStatement);
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
		this.query(dbpool, iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 * @param dbpool 全局池
	 * @param iWherePart 查询条件(包括排序字句)
	 * @param iLimitCount 记录数限制(iLimitCount=0: 无限制)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, int iLimitCount) throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpCommissionDetailSub dbPrpCommissionDetailSub = new DBPrpCommissionDetailSub();
		if (iLimitCount > 0 && dbPrpCommissionDetailSub.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpCommissionDetailSub.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpCommissionDetailSub WHERE " + iWherePart;
			schemas = dbPrpCommissionDetailSub.findByConditions(dbpool, strSqlStatement);
		}
	}

	/**
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 * @param dbpool 全局池
	 * @param iWherePart 查询条件,传入条件均已绑定变量形式，问号个数与属性值个数一致
	 * @param iWhereValue 查询条件各字段值
	 * @param iLimitCount 记录数限制(iLimitCount=0: 无限制)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, ArrayList iWhereValue, int iLimitCount) throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpCommissionDetailSub dbPrpCommissionDetailSub = new DBPrpCommissionDetailSub();
		if (iLimitCount > 0 && dbPrpCommissionDetailSub.getCount(dbpool, iWherePart, iWhereValue) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpCommissionDetailSub.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpCommissionDetailSub WHERE " + iWherePart;
			schemas = dbPrpCommissionDetailSub.findByConditions(dbpool, strSqlStatement, iWhereValue);
		}
	}

	/**
	 * 带dbpool的save方法
	 * @param 无
	 * @return 无
	 */
	public void save(DbPool dbpool) throws Exception {
		DBPrpCommissionDetailSub dbPrpCommissionDetailSub = new DBPrpCommissionDetailSub();

		int i = 0;

		for (i = 0; i < schemas.size(); i++) {
			dbPrpCommissionDetailSub.setSchema((PrpCommissionDetailSubSchema) schemas.get(i));
			dbPrpCommissionDetailSub.insert(dbpool);
		}
	}

	/**
	 * 不带dbpool的XXXXX存方法
	 * @param 无
	 * @return 无
	 */
	public void save() throws Exception {
		DbPool dbpool = new DbPool();

		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
	 * @param dbpool 连接池
	 * @param iPolicyNo XX号
	 * @return 无
	 */
	public void cancel(DbPool dbpool, String iPolicyNo) throws Exception {
		String strSqlStatement = "";
		strSqlStatement = " DELETE FROM PrpCommissionDetailSub WHERE PolicyNo= ?";
		dbpool.prepareInnerStatement(strSqlStatement);
		dbpool.setString(1, iPolicyNo);
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
	}

	/**
	 * 不带dbpool的删除方法
	 * @param iPolicyNo XX号
	 * @return 无
	 */
	public void cancel(String iPolicyNo) throws Exception {
		DbPool dbpool = new DbPool();

		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			dbpool.beginTransaction();
			cancel(dbpool, iPolicyNo);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}

	/**
	 * 带dbpool根据XX号获取数据
	 * @param iPolicyNo XX号
	 * @return 无
	 */
	public void getData(String iPolicyNo) throws Exception {
		DbPool dbpool = new DbPool();
		dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
		getData(dbpool, iPolicyNo);
		dbpool.close();
	}

	/**
	 * 不带dbpool根据XX号获取数据
	 * @param dbpool 连接池
	 * @param iPolicyNo XX号
	 * @return 无
	 */
	public void getData(DbPool dbpool, String iPolicyNo) throws Exception {
		String strWherePart = " PolicyNo= ? ";
		ArrayList arrWhereValue = new ArrayList();
		arrWhereValue.add(iPolicyNo);
		query(dbpool, strWherePart, arrWhereValue, 0);
	}

	/**
	 * @desc 核XXXXX通过后自动生成一张手续费明细
	 * @throws UserException
	 * @throws Exception
	 */
	public void createCommissionCDetailSub(DbPool dbpool, BLPrpCommissionSub blPrpCommissionSub, DBPrpCmain dbPrpCmain) throws UserException, Exception {

		double dbSelfRate = 0;
		double dbDisRate = 0;
		double dbDisRate1 = 0;
		double dbDisFee = 0;
		double dbDisFee1 = 0;
		double dbExchangeRate = 0;
		double dbSumDisFee = 0;
		double dbPremium = 0;

		BLPrpCitemKind blPrpCitemKind = new BLPrpCitemKind();
		BLPrpCfee blPrpCfee = new BLPrpCfee();
		BLPrpMiddleCostDetail blPrpMiddleCostDetail = new BLPrpMiddleCostDetail();

		PrpCitemKindSchema prpCitemKindSchema = new PrpCitemKindSchema();
		PrpCommissionSubSchema prpCommissionSubSchema = new PrpCommissionSubSchema();
		PrpMiddleCostDetailSchema prpMiddleCostDetailSchema = new PrpMiddleCostDetailSchema();
		PrpCommissionDetailSubSchema prpCommissionDetailSubSchema = new PrpCommissionDetailSubSchema();
		if (blPrpCommissionSub.getSize() < 1 || dbPrpCmain.getPolicyNo().equals(""))
			return;

		String strWherePart = "";
		ArrayList arrWhereValue = new ArrayList();
		strWherePart = "PolicyNo = ?";
		arrWhereValue.add(dbPrpCmain.getPolicyNo());
		blPrpCitemKind.query(dbpool, strWherePart, arrWhereValue, 0);
		blPrpCfee.query(dbpool, strWherePart, arrWhereValue, 0);
		strWherePart = "CertiNo = ?";
		blPrpMiddleCostDetail.query(dbpool, strWherePart, arrWhereValue, 0);

		if (blPrpCitemKind.getSize() < 1 || blPrpCfee.getSize() < 1)
			return;

		dbSelfRate = Double.parseDouble(Str.chgStrZero(prpCommissionSubSchema.getSelfRate()));
		if (dbSelfRate == 0)
			dbSelfRate = 100;
		for (int index = 0; index < blPrpCommissionSub.getSize(); index++) {
			prpCommissionSubSchema = blPrpCommissionSub.getArr(index);
			dbSumDisFee = 0;
			for (int i = 0; i < blPrpCitemKind.getSize(); i++) {

				prpCitemKindSchema = blPrpCitemKind.getArr(i);
				prpCommissionDetailSubSchema = new PrpCommissionDetailSubSchema();

				prpCommissionDetailSubSchema.setCertiNo(prpCommissionSubSchema.getCertiNo());
				prpCommissionDetailSubSchema.setCertiType(prpCommissionSubSchema.getCertiType());
				prpCommissionDetailSubSchema.setAgentCode(prpCommissionSubSchema.getAgentCode());
				prpCommissionDetailSubSchema.setRiskCode(prpCommissionSubSchema.getRiskCode());
				prpCommissionDetailSubSchema.setPolicyNo(prpCommissionSubSchema.getPolicyNo());
				prpCommissionDetailSubSchema.setCurrency(prpCommissionSubSchema.getCurrency());
				prpCommissionDetailSubSchema.setItemKindNo(prpCitemKindSchema.getItemKindNo());
				prpCommissionDetailSubSchema.setKindCode(prpCitemKindSchema.getKindCode());
				prpCommissionDetailSubSchema.setItemCode(prpCitemKindSchema.getItemCode());

				if (prpCitemKindSchema.getFlag().length() >= 6 && prpCitemKindSchema.getFlag().substring(5, 6).equals("1")) {
					dbDisRate = Double.parseDouble(Str.chgStrZero(dbPrpCmain.getPureRate()));
				} else {
					dbDisRate = Double.parseDouble(Str.chgStrZero(prpCommissionSubSchema.getDisRate()));
				}

				if (prpCitemKindSchema.getCurrency().trim().equals(prpCommissionSubSchema.getCurrency().trim())) {
					dbPremium = Double.parseDouble(Str.chgStrZero(prpCitemKindSchema.getPremium()));
				} else {
					for (int j = 0; j < blPrpCfee.getSize(); j++) {
						dbExchangeRate = 0;
						if (blPrpCfee.getArr(j).getCurrency().trim().equals(prpCitemKindSchema.getCurrency().trim())
								&& blPrpCfee.getArr(j).getCurrency1().trim().equals(prpCommissionSubSchema.getCurrency().trim())) {
							dbExchangeRate = Double.parseDouble(Str.chgStrZero(blPrpCfee.getArr(j).getExchangeRate1()));
							break;
						}
					}
					if (dbExchangeRate == 0)
						dbExchangeRate = 1;
					dbPremium = Double.parseDouble(Str.chgStrZero(prpCitemKindSchema.getPremium()));
					dbPremium = dbPremium * dbExchangeRate;
				}

				dbDisFee1 = 0;
				for (int n = 0; n < blPrpMiddleCostDetail.getSize(); n++) {
					prpMiddleCostDetailSchema = blPrpMiddleCostDetail.getArr(n);
					if (prpMiddleCostDetailSchema.getItemKindNo().trim().equals(prpCitemKindSchema.getItemKindNo().trim())) {
						dbDisFee1 = Double.parseDouble(Str.chgStrZero(prpMiddleCostDetailSchema.getDisFee()));
						break;
					}
				}

				dbDisFee = (dbPremium - dbDisFee1) * dbDisRate / 100 * dbSelfRate / 100;
				dbDisFee = Str.round(Str.round(dbDisFee, 8), 2);
				dbSumDisFee = Str.round(dbSumDisFee + dbDisFee, 2);
				if (!prpCommissionSubSchema.getClassCode().trim().equals("05") && i == (blPrpCitemKind.getSize() - 1)
						&& dbSumDisFee != Double.parseDouble(Str.chgStrZero(prpCommissionSubSchema.getDisFee()))) {
					dbDisFee = Str.round(dbDisFee + Double.parseDouble(Str.chgStrZero(prpCommissionSubSchema.getDisFee())) - dbSumDisFee, 2);
				}

				prpCommissionDetailSubSchema.setDisRate("" + dbDisRate);
				prpCommissionDetailSubSchema.setDisFee("" + dbDisFee);
				this.setArr(prpCommissionDetailSubSchema);
				
				if (prpCommissionSubSchema.getClassCode().trim().equals("05")) {
					prpCommissionSubSchema.setDisFee("" + dbSumDisFee);
				}
			}
		}

	}

	/**
	 * @desc 核批通过后自动生成一张手续费明细
	 * @throws UserException
	 * @throws Exception
	 */
	public void createCommissionPDetailSub(DbPool dbpool, BLPrpCommissionSub blPrpCommissionSub, DBPrpPmain dbPrpPmain) throws UserException, Exception {

		double dbSelfRate = 0;
		double dbDisRate = 0;
		double dbDisRate1 = 0;
		double dbDisFee = 0;
		double dbDisFee1 = 0;
		double dbExchangeRate = 0;
		double dbSumDisFee = 0;
		double dbPremium = 0;

		BLPrpPitemKind blPrpPitemKind = new BLPrpPitemKind();
		BLPrpPfee blPrpPfee = new BLPrpPfee();
		BLPrpMiddleCostDetail blPrpMiddleCostDetail = new BLPrpMiddleCostDetail();

		PrpPitemKindSchema prpPitemKindSchema = new PrpPitemKindSchema();
		PrpCommissionSubSchema prpCommissionSubSchema = new PrpCommissionSubSchema();
		PrpMiddleCostDetailSchema prpMiddleCostDetailSchema = new PrpMiddleCostDetailSchema();
		PrpCommissionDetailSubSchema prpCommissionDetailSubSchema = new PrpCommissionDetailSubSchema();
		if (blPrpCommissionSub.getSize() < 1 || dbPrpPmain.getPolicyNo().equals(""))
			return;

		String strWherePart = "";
		ArrayList arrWhereValue = new ArrayList();
		strWherePart = "EndorseNo = ?";
		arrWhereValue.add(dbPrpPmain.getEndorseNo());
		blPrpPitemKind.query(dbpool, strWherePart, arrWhereValue, 0);
		blPrpPfee.query(dbpool, strWherePart, arrWhereValue, 0);
		strWherePart = "CertiNo = ?";
		blPrpMiddleCostDetail.query(dbpool, strWherePart, arrWhereValue, 0);

		if (blPrpPitemKind.getSize() < 1 || blPrpPfee.getSize() < 1)
			return;

		dbSelfRate = Double.parseDouble(Str.chgStrZero(prpCommissionSubSchema.getSelfRate()));
		if (dbSelfRate == 0)
			dbSelfRate = 100;
		for (int index = 0; index < blPrpCommissionSub.getSize(); index++) {
			prpCommissionSubSchema = blPrpCommissionSub.getArr(index);
			dbSumDisFee = 0;
			for (int i = 0; i < blPrpPitemKind.getSize(); i++) {

				prpPitemKindSchema = blPrpPitemKind.getArr(i);
				prpCommissionDetailSubSchema = new PrpCommissionDetailSubSchema();

				prpCommissionDetailSubSchema.setCertiNo(prpCommissionSubSchema.getCertiNo());
				prpCommissionDetailSubSchema.setCertiType(prpCommissionSubSchema.getCertiType());
				prpCommissionDetailSubSchema.setAgentCode(prpCommissionSubSchema.getAgentCode());
				prpCommissionDetailSubSchema.setRiskCode(prpCommissionSubSchema.getRiskCode());
				prpCommissionDetailSubSchema.setPolicyNo(prpCommissionSubSchema.getPolicyNo());
				prpCommissionDetailSubSchema.setCurrency(prpCommissionSubSchema.getCurrency());
				prpCommissionDetailSubSchema.setItemKindNo(prpPitemKindSchema.getItemKindNo());
				prpCommissionDetailSubSchema.setKindCode(prpPitemKindSchema.getKindCode());
				prpCommissionDetailSubSchema.setItemCode(prpPitemKindSchema.getItemCode());

				if (prpPitemKindSchema.getFlag().length() >= 6 && prpPitemKindSchema.getFlag().substring(5, 6).equals("1")) {
					dbDisRate = Double.parseDouble(Str.chgStrZero(dbPrpPmain.getPureRate()));
				} else {
					dbDisRate = Double.parseDouble(Str.chgStrZero(prpCommissionSubSchema.getDisRate()));
				}

				if (prpPitemKindSchema.getCurrency().trim().equals(prpCommissionSubSchema.getCurrency().trim())) {
					dbPremium = Double.parseDouble(Str.chgStrZero(prpPitemKindSchema.getChgPremium()));
				} else {
					for (int j = 0; j < blPrpPfee.getSize(); j++) {
						dbExchangeRate = 0;
						if (blPrpPfee.getArr(j).getCurrency().trim().equals(prpPitemKindSchema.getCurrency().trim())
								&& blPrpPfee.getArr(j).getCurrency1().trim().equals(prpCommissionSubSchema.getCurrency().trim())) {
							dbExchangeRate = Double.parseDouble(Str.chgStrZero(blPrpPfee.getArr(j).getExchangeRate1()));
							break;
						}
					}
					if (dbExchangeRate == 0)
						dbExchangeRate = 1;
					dbPremium = Double.parseDouble(Str.chgStrZero(prpPitemKindSchema.getChgPremium()));
					dbPremium = dbPremium * dbExchangeRate;
				}

				dbDisFee1 = 0;
				for (int n = 0; n < blPrpMiddleCostDetail.getSize(); n++) {
					prpMiddleCostDetailSchema = blPrpMiddleCostDetail.getArr(n);
					if (prpMiddleCostDetailSchema.getItemKindNo().trim().equals(prpPitemKindSchema.getItemKindNo().trim())) {
						dbDisFee1 = Double.parseDouble(Str.chgStrZero(prpMiddleCostDetailSchema.getDisFee()));
						break;
					}
				}

				dbDisFee = (dbPremium - dbDisFee1) * dbDisRate / 100 * dbSelfRate / 100;
				dbDisFee = Str.round(Str.round(dbDisFee, 8), 2);
				dbSumDisFee = Str.round(dbSumDisFee + dbDisFee, 2);
				if (!prpCommissionSubSchema.getClassCode().trim().equals("05") && i == (blPrpPitemKind.getSize() - 1)
						&& dbSumDisFee != Double.parseDouble(Str.chgStrZero(prpCommissionSubSchema.getDisFee()))) {
					dbDisFee = Str.round(dbDisFee + Double.parseDouble(Str.chgStrZero(prpCommissionSubSchema.getDisFee())) - dbSumDisFee, 2);
				}

				prpCommissionDetailSubSchema.setDisRate("" + dbDisRate);
				prpCommissionDetailSubSchema.setDisFee("" + dbDisFee);
				this.setArr(prpCommissionDetailSubSchema);
				
				if (prpCommissionSubSchema.getClassCode().trim().equals("05")) {
					prpCommissionSubSchema.setDisFee("" + dbSumDisFee);
				}
			}
		}

	}

	/**
	 * 手续费明细表按标的序号累积求和，用于计算批改时当前XX的手续费金额
	 * @param iWherePart
	 * @return 无
	 */
	public void sumByItemKindNo(String iWherePart) throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpCommissionDetailSub dbPrpCommissionDetailSub = new DBPrpCommissionDetailSub();
		initArr();
		strSqlStatement = " SELECT SUM(DISFEE) AS DISFEE,CURRENCY,ITEMKINDNO  FROM PrpCommissionDetailSub WHERE " + iWherePart + " GROUP BY CURRENCY,ITEMKINDNO ORDER BY ITEMKINDNO";
		schemas = dbPrpCommissionDetailSub.sumByCurrency(strSqlStatement);
	}

	/**
	 * 主函数
	 * 
	 * @param args
	 *            参数列表
	 */
	public static void main(String[] args) {
		
	}
}
