package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.blsvr.cb.BLPrpCagentSub;
import com.sp.prpall.blsvr.cb.BLPrpCcoins;
import com.sp.prpall.blsvr.pg.BLPrpPagentSub;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.misc.DBPrpCommissionSub;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;
import com.sp.prpall.dbsvr.pg.DBPrpPmain;
import com.sp.prpall.schema.PrpCommissionSubSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义PrpCommissionSub的BL类 从pdm中取数据库信息,根据数据库表生成对应的BL类
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
public class BLPrpCommissionSub {
	private Vector schemas = new Vector();

	/**
	 * 构造函数
	 */
	public BLPrpCommissionSub() {
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
	 * 增加一条PrpCommissionSubSchema记录
	 * @param iPrpCommissionSubSchema  PrpCommissionSubSchema
	 * @throws Exception
	 */
	public void setArr(PrpCommissionSubSchema iPrpCommissionSubSchema) throws Exception {
		try {
			schemas.add(iPrpCommissionSubSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到一条PrpCommissionSubSchema记录
	 * @param index 下标
	 * @return 一个PrpCommissionSubSchema对象
	 * @throws Exception
	 */
	public PrpCommissionSubSchema getArr(int index) throws Exception {
		PrpCommissionSubSchema prpCommissionSubSchema = null;
		try {
			prpCommissionSubSchema = (PrpCommissionSubSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpCommissionSubSchema;
	}

	/**
	 * 删除一条PrpCommissionSubSchema记录
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
		DBPrpCommissionSub dbPrpCommissionSub = new DBPrpCommissionSub();
		if (iLimitCount > 0 && dbPrpCommissionSub.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpCommissionSub.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpCommissionSub WHERE " + iWherePart;
			schemas = dbPrpCommissionSub.findByConditions(strSqlStatement);
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
		DBPrpCommissionSub dbPrpCommissionSub = new DBPrpCommissionSub();
		if (iLimitCount > 0 && dbPrpCommissionSub.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpCommissionSub.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpCommissionSub WHERE " + iWherePart;
			schemas = dbPrpCommissionSub.findByConditions(dbpool, strSqlStatement);
		}
	}

	/**
	 * 带dbpool的save方法
	 * @param 无
	 * @return 无
	 */
	public void save(DbPool dbpool) throws Exception {
		DBPrpCommissionSub dbPrpCommissionSub = new DBPrpCommissionSub();

		int i = 0;

		for (i = 0; i < schemas.size(); i++) {
			dbPrpCommissionSub.setSchema((PrpCommissionSubSchema) schemas.get(i));
			dbPrpCommissionSub.insert(dbpool);
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
		strSqlStatement = " DELETE FROM PrpCommissionSub WHERE PolicyNo= ?";
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
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 * @param iWherePart 查询条件,传入条件均已绑定变量形式，问号个数与属性值个数一致
	 * @param iWhereValue 查询条件各字段值
	 * @param iLimitCount 记录数限制(iLimitCount=0: 无限制)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart, ArrayList iWhereValue, int iLimitCount) throws UserException, Exception {
		DbPool dbpool = new DbPool();

		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			this.query(dbpool, iWherePart, iWhereValue, iLimitCount);
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
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
		DBPrpCommissionSub dbPrpCommissionSub = new DBPrpCommissionSub();
		if (iLimitCount > 0 && dbPrpCommissionSub.getCount(dbpool, iWherePart, iWhereValue) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpCommissonSub.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpCommissionSub WHERE " + iWherePart;
			schemas = dbPrpCommissionSub.findByConditions(dbpool, strSqlStatement, iWhereValue);
		}
	}

	/**
	 * @desc 生成手续/经纪费信息
	 * @param iCertiType
	 * @param iCertiNo
	 * @throws UserException
	 * @throws Exception
	 */
	public void createCommissionSub(String iCertiType, String iCertiNo) throws UserException, Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			this.createCommissionSub(dbpool, iCertiType, iCertiNo);
			dbpool.commitTransaction();
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
	 * @desc 生成手续/经纪费信息
	 * @param iPolicyNo
	 * @throws UserException
	 * @throws Exception
	 */
	public void createCommissionSub(DbPool dbpool, String iCertiType, String iCertiNo) throws UserException, Exception {
		if (iCertiType.equals("P"))
			this.createCommissionSubC(dbpool, iCertiNo);
		if (iCertiType.equals("E"))
			this.createCommissionSubP(dbpool, iCertiNo);
	}

	/**
	 * @desc 生成XX手续/经纪费信息
	 * @param dbpool
	 * @param iPolicyNo
	 * @throws UserException
	 * @throws Exception
	 */
	public void createCommissionSubC(DbPool dbpool, String iPolicyNo) throws UserException, Exception {
		DBPrpCmain dbPrpCmain = new DBPrpCmain();
		dbPrpCmain.getInfo(dbpool, iPolicyNo);
		
		BLPrpCcoins blPrpCcoins = new BLPrpCcoins();
		BLPrpCommissionDetailSub blPrpCommissionDetailSub = new BLPrpCommissionDetailSub();
		double dblSelfRate = 100; 
		dblSelfRate = Str.round(Double.parseDouble(Str.chgStrZero(blPrpCcoins.getSelfRate(dbpool, iPolicyNo, "1"))), 4);
		double dblSelfRate1 = 100; 
		dblSelfRate1 = Str.round(Double.parseDouble(Str.chgStrZero(blPrpCcoins.getSelfRate(dbpool, iPolicyNo, "2"))), 4);
		double dblCommissionSubRate = 0;
		double dblCommissionSub = 0;

		
		String strSQL = "SELECT Currency,SUM(PlanFee) AS PlanFee FROM PrpCplan WHERE PolicyNo='" + iPolicyNo + "' AND EndorseNo IS NULL GROUP BY Currency";
		String strCurrency = "";
		double dblSumPremium = 0;
		ResultSet rs = dbpool.query(strSQL);
		while (rs.next()) {
			strCurrency = rs.getString("Currency");
			dblSumPremium += rs.getDouble("PlanFee");
		}
		rs.close();
		if (dblSumPremium == 0) {
			strCurrency = dbPrpCmain.getCurrency();
			dblSumPremium = Double.parseDouble(dbPrpCmain.getSumPremium());
		}
		dblSumPremium = Str.round(dblSumPremium, 2);
		BLPrpAgentSub blPrpAgentSub = new BLPrpAgentSub();
		blPrpAgentSub.getData(dbpool, iPolicyNo);
		for (int i = 0; i < blPrpAgentSub.getSize(); i++) {

			dblCommissionSubRate = Double.parseDouble(Str.chgStrZero(blPrpAgentSub.getArr(i).getDisRate()));
			if (dblCommissionSubRate == 0 && Double.parseDouble(Str.chgStrZero(dbPrpCmain.getPureRate())) == 0)
				return;

			
			double dblDisRate = Double.parseDouble(Str.chgStrZero(dbPrpCmain.getDisRate1()));
			double dblDisPremium = dblSumPremium * dblDisRate / 100;
			
			dblDisPremium = Str.round(Str.round(dblDisPremium, 8), 2);
			
			dblDisPremium = Str.round(Str.round(dblDisPremium * dblSelfRate1 / 100, 4), 2);

			
			dblCommissionSub = (dblSumPremium - dblDisPremium) * dblCommissionSubRate / 100;
			dblCommissionSub = Str.round(Str.round(dblCommissionSub, 8), 2);
			
			dblCommissionSub = Str.round(Str.round(dblCommissionSub * dblSelfRate / 100, 4), 2);
			ChgDate chgDate = new ChgDate();
			PrpCommissionSubSchema prpCommissionSubSchema = new PrpCommissionSubSchema();
			prpCommissionSubSchema.setClassCode(dbPrpCmain.getClassCode());
			prpCommissionSubSchema.setRiskCode(dbPrpCmain.getRiskCode());
			prpCommissionSubSchema.setCertiNo(iPolicyNo);
			prpCommissionSubSchema.setAgentCode(blPrpAgentSub.getArr(i).getAgentCode());
			prpCommissionSubSchema.setCertiType("P");
			prpCommissionSubSchema.setPolicyNo(iPolicyNo);
			prpCommissionSubSchema.setDisRate("" + dblCommissionSubRate);
			prpCommissionSubSchema.setCurrency(strCurrency);
			prpCommissionSubSchema.setDisFee("" + dblCommissionSub);
			prpCommissionSubSchema.setGenerateDate(chgDate.getCurrentTime("yyyy-MM-dd"));
			prpCommissionSubSchema.setGenerateTime(chgDate.getCurrentTime("HH:mm"));
			prpCommissionSubSchema.setSelfRate(String.valueOf(dblSelfRate));
			this.setArr(prpCommissionSubSchema);
		}
		
		BLPrpCagentSub blPrpCAgentSub = new BLPrpCagentSub();
		blPrpCAgentSub.getData(dbpool, iPolicyNo);
		if(blPrpCAgentSub.getSize()!=0){
			for (int i = 0; i < blPrpCAgentSub.getSize(); i++) {

				dblCommissionSubRate = Double.parseDouble(Str.chgStrZero(blPrpCAgentSub.getArr(i).getDisRate()));
				if (dblCommissionSubRate == 0 && Double.parseDouble(Str.chgStrZero(dbPrpCmain.getPureRate())) == 0)
					return;

				
				double dblDisRate = Double.parseDouble(Str.chgStrZero(dbPrpCmain.getDisRate1()));
				double dblDisPremium = dblSumPremium * dblDisRate / 100;
				
				dblDisPremium = Str.round(Str.round(dblDisPremium, 8), 2);
				
				dblDisPremium = Str.round(Str.round(dblDisPremium * dblSelfRate1 / 100, 4), 2);

				
				dblCommissionSub = (dblSumPremium - dblDisPremium) * dblCommissionSubRate / 100;
				dblCommissionSub = Str.round(Str.round(dblCommissionSub, 8), 2);
				
				dblCommissionSub = Str.round(Str.round(dblCommissionSub * dblSelfRate / 100, 4), 2);
				ChgDate chgDate = new ChgDate();
				PrpCommissionSubSchema prpCommissionSubSchema = new PrpCommissionSubSchema();
				prpCommissionSubSchema.setClassCode(dbPrpCmain.getClassCode());
				prpCommissionSubSchema.setRiskCode(dbPrpCmain.getRiskCode());
				prpCommissionSubSchema.setCertiNo(iPolicyNo);
				prpCommissionSubSchema.setAgentCode(blPrpCAgentSub.getArr(i).getAgentCode());
				prpCommissionSubSchema.setCertiType("P");
				prpCommissionSubSchema.setPolicyNo(iPolicyNo);
				prpCommissionSubSchema.setDisRate("" + dblCommissionSubRate);
				prpCommissionSubSchema.setCurrency(strCurrency);
				prpCommissionSubSchema.setDisFee("" + dblCommissionSub);
				prpCommissionSubSchema.setGenerateDate(chgDate.getCurrentTime("yyyy-MM-dd"));
				prpCommissionSubSchema.setGenerateTime(chgDate.getCurrentTime("HH:mm"));
				prpCommissionSubSchema.setSelfRate(String.valueOf(dblSelfRate));
				this.setArr(prpCommissionSubSchema);
			}
			
		}
		
		blPrpCommissionDetailSub.createCommissionCDetailSub(dbpool, this, dbPrpCmain);
		this.save(dbpool);
		blPrpCommissionDetailSub.save(dbpool);
	}

	/**
	 * @desc 生成XX手续/经纪费信息
	 * @param dbpool
	 * @param iPolicyNo
	 * @throws UserException
	 * @throws Exception
	 */
	public void createCommissionSubP(DbPool dbpool, String iEndorseNo) throws UserException, Exception {
		DBPrpPmain dbPrpPmain = new DBPrpPmain();
		dbPrpPmain.getInfo(dbpool, iEndorseNo);
		BLPrpCommissionDetailSub blPrpCommissionDetailSub = new BLPrpCommissionDetailSub();

		double dblCommissionSubRate = 0;
		double dblCommissionSub = 0;

		
		DBPrpPhead dbPrpPhead = new DBPrpPhead();
		dbPrpPhead.getInfo(dbpool, iEndorseNo);
		if (dbPrpPhead.getEndorType().trim().equals(SysConfig.getProperty("EDITTYPE_MIDDLECOST")) || dbPrpPhead.getEndorType().trim().equals(SysConfig.getProperty("EDITTYPE_COMMISSION")))
			return;

		String strSQL = "SELECT Currency1,SUM(ChgPremium1) AS ChgPremium1 FROM PrpPfee WHERE EndorseNo='" + iEndorseNo + "' GROUP BY Currency1";
		String strCurrency = "";
		double dblSumPremium = 0;
		ResultSet rs = dbpool.query(strSQL);
		while (rs.next()) {
			strCurrency = rs.getString("Currency1");
			dblSumPremium += rs.getDouble("ChgPremium1");
		}
		rs.close();
		if (dblSumPremium == 0)
			return;

		BLPrpAgentSub blPrpAgentSub = new BLPrpAgentSub();
		blPrpAgentSub.getData(dbpool, dbPrpPmain.getPolicyNo());
		for (int i = 0; i < blPrpAgentSub.getSize(); i++) {
			
			dblCommissionSubRate = Double.parseDouble(blPrpAgentSub.getArr(i).getDisRate());
			if (dblCommissionSubRate == 0 && Double.parseDouble(Str.chgStrZero(dbPrpPmain.getPureRate())) == 0)
				return;
			
			double dblDisRate = Double.parseDouble(Str.chgStrZero(dbPrpPmain.getDisRate1()));
			double dblDisPremium = dblSumPremium * dblDisRate / 100;
			
			dblDisPremium = Str.round(Str.round(dblDisPremium, 8), 2);

			
			dblCommissionSub = (dblSumPremium - dblDisPremium) * dblCommissionSubRate / 100;
			dblCommissionSub = Str.round(Str.round(dblCommissionSub, 8), 2);

			ChgDate chgDate = new ChgDate();
			PrpCommissionSubSchema prpCommissionSubSchema = new PrpCommissionSubSchema();
			prpCommissionSubSchema.setClassCode(dbPrpPmain.getClassCode());
			prpCommissionSubSchema.setRiskCode(dbPrpPmain.getRiskCode());
			prpCommissionSubSchema.setCertiNo(iEndorseNo);
			prpCommissionSubSchema.setAgentCode(blPrpAgentSub.getArr(i).getAgentCode());
			prpCommissionSubSchema.setCertiType("E");
			prpCommissionSubSchema.setPolicyNo(dbPrpPmain.getPolicyNo());
			prpCommissionSubSchema.setDisRate("" + dblCommissionSubRate);
			prpCommissionSubSchema.setCurrency(strCurrency);
			prpCommissionSubSchema.setDisFee("" + dblCommissionSub);
			prpCommissionSubSchema.setGenerateDate(chgDate.getCurrentTime("yyyy-MM-dd"));
			prpCommissionSubSchema.setGenerateTime(chgDate.getCurrentTime("HH:mm"));
			this.setArr(prpCommissionSubSchema);
		}
		
		if(blPrpAgentSub.getSize()==0){
			BLPrpPagentSub blPrpPAgentSub = new BLPrpPagentSub();
			blPrpPAgentSub.getData(dbpool, iEndorseNo);
			for (int i = 0; i < blPrpPAgentSub.getSize(); i++) {

				dblCommissionSubRate = Double.parseDouble(blPrpPAgentSub.getArr(i).getDisRate());
				if (dblCommissionSubRate == 0 && Double.parseDouble(Str.chgStrZero(dbPrpPmain.getPureRate())) == 0)
					return;
				
				double dblDisRate = Double.parseDouble(Str.chgStrZero(dbPrpPmain.getDisRate1()));
				double dblDisPremium = dblSumPremium * dblDisRate / 100;
				
				dblDisPremium = Str.round(Str.round(dblDisPremium, 8), 2);

				
				dblCommissionSub = (dblSumPremium - dblDisPremium) * dblCommissionSubRate / 100;
				dblCommissionSub = Str.round(Str.round(dblCommissionSub, 8), 2);

				ChgDate chgDate = new ChgDate();
				PrpCommissionSubSchema prpCommissionSubSchema = new PrpCommissionSubSchema();
				prpCommissionSubSchema.setClassCode(dbPrpPmain.getClassCode());
				prpCommissionSubSchema.setRiskCode(dbPrpPmain.getRiskCode());
				prpCommissionSubSchema.setCertiNo(iEndorseNo);
				prpCommissionSubSchema.setAgentCode(blPrpPAgentSub.getArr(i).getAgentCode());
				prpCommissionSubSchema.setCertiType("E");
				prpCommissionSubSchema.setPolicyNo(dbPrpPmain.getPolicyNo());
				prpCommissionSubSchema.setDisRate("" + dblCommissionSubRate);
				prpCommissionSubSchema.setCurrency(strCurrency);
				prpCommissionSubSchema.setDisFee("" + dblCommissionSub);
				prpCommissionSubSchema.setGenerateDate(chgDate.getCurrentTime("yyyy-MM-dd"));
				prpCommissionSubSchema.setGenerateTime(chgDate.getCurrentTime("HH:mm"));
				this.setArr(prpCommissionSubSchema);
			}
			
		}
		
		blPrpCommissionDetailSub.createCommissionPDetailSub(dbpool, this, dbPrpPmain);
		this.save(dbpool);
		blPrpCommissionDetailSub.save(dbpool);
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
