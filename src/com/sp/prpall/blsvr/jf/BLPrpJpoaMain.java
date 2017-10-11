package com.sp.prpall.blsvr.jf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.sp.prpall.dbsvr.jf.DBPrpJpayRefVoucher;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaMain;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaTrace;
import com.sp.prpall.schema.PrpJpayRefDetailSchema;
import com.sp.prpall.schema.PrpJpoaMainSchema;
import com.sp.prpall.schema.PrpJpoaTraceSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utiall.dbsvr.DBPrpDcustomerUnitNew;
import com.sp.utiall.dbsvr.DBPrpDuser;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * @version 1.0
 */
public class BLPrpJpoaMain {
	private BLPrpJpayRefDetail blPrpJpayRefDetail = new BLPrpJpayRefDetail();

	private BLPrpJpoaTrace blPrpJpoaTrace = new BLPrpJpoaTrace();

	private Vector schemas = new Vector();

	/**
	 * 构造函数
	 */
	public BLPrpJpoaMain() {
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
	 * 增加一条PrpjPoaMainSchema记录
	 * @param PrpjPoaMainSchema PrpjPoaMainSchema
	 * @throws Exception
	 */
	public void setArr(PrpJpoaMainSchema prpJpoaMainSchema) throws Exception {
		try {
			schemas.add(prpJpoaMainSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到一条PrpJpoaMainSchema记录
	 * @param index 下标
	 * @return 一个PrpJpoaMainSchema对象
	 * @throws Exception
	 */
	public PrpJpoaMainSchema getArr(int index) throws Exception {
		PrpJpoaMainSchema prpJpoaMainSchema = null;
		try {
			prpJpoaMainSchema = (PrpJpoaMainSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJpoaMainSchema;
	}

	/**
	 * 删除一条PrpJcommAllocSchema记录
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
		DBPrpJpoaMain dbPrpjPoaMain = new DBPrpJpoaMain();
		if (iLimitCount > 0 && dbPrpjPoaMain.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJpoaMain.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJpoaMain WHERE " + iWherePart;
			schemas = dbPrpjPoaMain.findByConditions(strSqlStatement);
		}
	}

	/**
	 * Add By ZhaoPengfei 20101122 使用绑定变量查询方法 
	 * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * @param iWherePart 查询条件(包括排序字句)
	 * @param bindValues 传入的绑定参数
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(StringBuffer iWherePart,ArrayList bindValues) throws UserException,Exception
    {
		String strSqlStatement = "";
		DBPrpJpoaMain dbPrpjPoaMain = new DBPrpJpoaMain();		
		initArr();
		strSqlStatement = " SELECT * FROM PrpJpoaMain WHERE " + iWherePart;
		schemas = dbPrpjPoaMain.findByConditions(strSqlStatement, bindValues);
    }
	
	/**
	 * 得到记录数
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int getCount(String condition) throws Exception {
		DBPrpJpoaMain dbPrpjPoaMain = new DBPrpJpoaMain();
		return dbPrpjPoaMain.getCount(condition);
	}

	/**
	 * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * @param dbpool 全局池
	 * @param iWherePart 查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart) throws UserException, Exception {
		this.query(dbpool, iWherePart, Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT")
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
		DBPrpJpoaMain dbPrpjPoaMain = new DBPrpJpoaMain();
		if (iLimitCount > 0 && dbPrpjPoaMain.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJpoaMain.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJpoaMain WHERE " + iWherePart;
			schemas = dbPrpjPoaMain.findByConditions(dbpool, strSqlStatement);
		}
	}

	/**
	 * 带dbpool的save方法
	 * @param 无
	 * @return 无
	 */
	public void save(DbPool dbpool) throws Exception {
		DBPrpJpoaMain dbPrpjPoaMain = new DBPrpJpoaMain();

		int i = 0;

		for (i = 0; i < schemas.size(); i++) {
			dbPrpjPoaMain.setSchema((PrpJpoaMainSchema) schemas.get(i));
			dbPrpjPoaMain.insert(dbpool);
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
			
			
			String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
			if("0".equals(payment_switch)){
				dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			}else{
				dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
			}
			
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
	 * @param null null
	 * @return 无
	 */
	public void cancel(DbPool dbpool, String PoaCode) throws Exception {




		String strSqlStatement = " DELETE FROM PrpJpoaMain WHERE PoaCode= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, PoaCode);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
	}

	/**
	 * 不带dbpool的删除方法
	 * @param null null
	 * @return 无
	 */
	public void cancel(String PoaCode) throws Exception {
		DbPool dbpool = new DbPool();

		try {
			
			
			String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
			if("0".equals(payment_switch)){
				dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			}else{
				dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
			}
			
			dbpool.beginTransaction();
			cancel(dbpool, null);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}

	/**
	 * @desc 给私有对象BLPrpJpayRefDetail赋值
	 * @return
	 * @throws Exception
	 */
	public void setBLPrpJpayRefDetail(BLPrpJpayRefDetail iBLPrpJpayRefDetail) throws Exception {
		this.blPrpJpayRefDetail = iBLPrpJpayRefDetail;
	}

	/**
	 * @desc 得到一条BLPrpJpayRefDetail对象
	 * @return 一个BLPrpJpayRefDetail对象
	 * @throws Exception
	 */
	public BLPrpJpayRefDetail getBLPrpJpayRefDetail() throws Exception {
		return this.blPrpJpayRefDetail;
	}

	/**
	 * @desc 给私有对象BLPrpJpoaTrace赋值
	 * @return
	 * @throws Exception
	 */
	public void setBLPrpJpoaTrace(BLPrpJpoaTrace iBLPrpJpoaTrace) throws Exception {
		this.blPrpJpoaTrace = iBLPrpJpoaTrace;
	}

	/**
	 * @desc 得到一条BLPrpJpoaTrace对象
	 * @return 一个BLPrpJpoaTrace对象
	 * @throws Exception
	 */
	public BLPrpJpoaTrace getBLPrpJpoaTrace() throws Exception {
		return this.blPrpJpoaTrace;
	}

	/**
	 * 带dbpool根据null获取数据
	 * @param null null
	 * @return 无
	 */
	public void getData(String PoaCode) throws Exception {
		DbPool dbpool = new DbPool();
		
		try {
			
			
			String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
			if("0".equals(payment_switch)){
				dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			}else{
				dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
			}
			
			getData(dbpool, null);
		} catch (Exception e) {
			
		} finally {
			dbpool.close();
		}
		
	}

	/**
	 * 不带dbpool根据null获取数据
	 * @param dbpool 连接池
	 * @param null null
	 * @return 无
	 */
	public void getData(DbPool dbpool, String PoaCode) throws Exception {
		String strWherePart = "";
		strWherePart = "null= ' " + null + "'";
		query(dbpool, strWherePart, 0);
	}
	
	/**
	 * 不带dbpool预收款存入
	 * @param flag 操作标志
	 * @return 无 *
	 * @throws Exception
	 */
	public String savePrpJpoa(String flag,String iAuxNumber) throws Exception {
		DbPool dbpool = new DbPool();
		String strVoucherno = "";
		try {
			
			
			String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
			if("0".equals(payment_switch)){
				dbpool.open(SysConfig.CONST_DDCCDATASOURCE); 
			}else{
				dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
			}
			
			dbpool.beginTransaction();
			strVoucherno = savePrpJpoa(dbpool, flag, iAuxNumber);
			dbpool.commitTransaction();
		} catch (UserException userexception) {
			dbpool.rollbackTransaction();
			throw userexception;
		} catch (SQLException sqlexception) {
			dbpool.rollbackTransaction();
			throw sqlexception;
		} catch (Exception exception) {
			dbpool.rollbackTransaction();
			throw exception;
		} finally {
			dbpool.close();
		}
		return strVoucherno;
	}

	/**
	 * 带dbpool预收款存入
	 * @param dbpool 连接池
	 * @param flag 操作标志
	 * @return 无
	 * @throws Exception
	 */
	public String savePrpJpoa(DbPool dbpool, String flag, String iAuxNumber) throws Exception {
		
		DBPrpJpayRefVoucher dBPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
		String strRealPayRefNo = dBPrpJpayRefVoucher.genRealPayRefNo();
		DateTime now = new DateTime(new Date(), DateTime.YEAR_TO_DAY);
		String userCode = this.getArr(0).getOperatorcode();
		DBPrpDuser dbPrpDuser = new DBPrpDuser();
		dbPrpDuser.getInfo(dbpool, userCode);
		dBPrpJpayRefVoucher.setRealPayRefNo(strRealPayRefNo);
		dBPrpJpayRefVoucher.setPayRefNoType("E");
		dBPrpJpayRefVoucher.setCenterCode(this.getArr(0).getCentercode());
		dBPrpJpayRefVoucher.setVoucherDate(this.blPrpJpayRefDetail.getArr(0).getPayRefDate());
		dBPrpJpayRefVoucher.setComCode(dbPrpDuser.getComCode());
		dBPrpJpayRefVoucher.setPayRefCode(userCode);
		
		dBPrpJpayRefVoucher.setAttribute2(iAuxNumber);
		
		dBPrpJpayRefVoucher.insert(dbpool);
		
		if (this.blPrpJpayRefDetail.getSize() == 0) {
			throw new UserException(-96, -1167, "BLPrpJpoaMain.savePrpJpoa()",
					"PrpJpayRefDetail表生成的数据错误！");
		} else {
			for (int i = 0; i < this.blPrpJpayRefDetail.getSize(); i++) {
				this.blPrpJpayRefDetail.getArr(i).setRealPayRefNo(strRealPayRefNo);
				this.blPrpJpayRefDetail.getArr(i).setPayRefNo(strRealPayRefNo);
			}
			this.blPrpJpayRefDetail.save(dbpool);
		}
		
		if (this.blPrpJpoaTrace.getSize() == 0) {
			throw new UserException(-96, -1167, "BLPrpJpoaMain.savePrpJpoa()",
					"PrpJpoaTrace表生成的数据错误！");
		}
		
		for (int i = 0; i < this.getSize(); i++) {
			DBPrpJpoaMain dbPrpJpoaMain = new DBPrpJpoaMain();
			PrpJpoaMainSchema prpJpoaMainSchema = this.getArr(i);
			dbPrpJpoaMain.setSchema(prpJpoaMainSchema);
			if (flag.equals("update")) {
				double dblDeliqueeFee = dbPrpJpoaMain.getDeliqueefee();
				String strStartDate = dbPrpJpoaMain.getStartdate();
				String strStopDate = dbPrpJpoaMain.getStopdate();
				if (dbPrpJpoaMain.getValiddate().equals("0")) {
					throw new UserException(-96, -1167, "BLPrpJpoaMain.voucherVerifyPoa()",
							"预收款支付失败：预收款帐号无效！");
				}
				if (!strStartDate.equals("")
						&& now.before(new DateTime(strStartDate, DateTime.YEAR_TO_DAY))) {
					throw new UserException(-96, -1167, "BLPrpJpoaMain.voucherVerifyPoa()",
							"预收款支付失败：预收款帐号未启用！");
				}
				if (!strStopDate.equals("")
						&& now.after(new DateTime(strStopDate, DateTime.YEAR_TO_DAY))) {
					throw new UserException(-96, -1167, "BLPrpJpoaMain.voucherVerifyPoa()",
							"预收款支付失败：预收款帐号已停用！");
				}
				
				
				
				dbPrpJpoaMain.update(dbpool);
				for (int j = 0; j < this.blPrpJpayRefDetail.getSize(); j++) {
					this.blPrpJpoaTrace.getArr(j).setRealpayrefno(strRealPayRefNo);
				}
			} else if (flag.equals("insert")) {
				String strCondition = "Select * from prpjpoamain where COMCODE='"
						+ dbPrpJpoaMain.getComcode() + "' And AGENTCODE ='"
						+ dbPrpJpoaMain.getAgentcode() + "' And HANDLERCODE ='"
						+ dbPrpJpoaMain.getHandlercode() + "' And CURRENCY ='"
						+ dbPrpJpoaMain.getCurrency() + "'";
				
				if (dbPrpJpoaMain.findByConditions(dbpool, strCondition).size() != 0) {
					throw new UserException(-96, -1167, "BLPrpJpoaMain.savePrpJpoa()",
							"PrpJpoaMain表生成的数据错误！数据已经存在！");
				}
				dbPrpJpoaMain.setStartdate(now.toString());
				dbPrpJpoaMain.insert(dbpool);
				prpJpoaMainSchema = (PrpJpoaMainSchema) dbPrpJpoaMain.findByConditions(dbpool,
						strCondition).get(0);
				
				String poaCodeNew = prpJpoaMainSchema.getPoacode();
				this.getArr(i).setPoacode(poaCodeNew);
				for (int j = 0; j < this.blPrpJpayRefDetail.getSize(); j++) {
					this.blPrpJpoaTrace.getArr(j).setPoacode(poaCodeNew);
					this.blPrpJpoaTrace.getArr(j).setRealpayrefno(strRealPayRefNo);
				}
			}
			this.blPrpJpoaTrace.save(dbpool);
		}
		BLPrpJpaymentVoucher blPrpJpaymentVoucher = new BLPrpJpaymentVoucher();
		String strVoucherNo = blPrpJpaymentVoucher.callProcedure(dbpool,strRealPayRefNo);
		return strVoucherNo;
	}
	/**
	 * 收付款确认时预收款处理
	 * @param dbpool 连接池
	 * @param flag 操作标志
	 * @return 无
	 * @throws Exception
	 */
	public void voucherVerifyPoa(DbPool dbpool, PrpJpayRefDetailSchema prpJpayRefDetailSchema)
			throws Exception {
		BLPrpDcode blPrpDcode = new BLPrpDcode();
		String strPoaCode = prpJpayRefDetailSchema.getFlag1();
		DateTime now = new DateTime(new Date(), DateTime.YEAR_TO_DAY);
		
		double dblPayRefFee = Double.parseDouble(prpJpayRefDetailSchema.getPayRefFee());
		DBPrpJpoaMain dbPrpJpoaMain = new DBPrpJpoaMain();
		dbPrpJpoaMain.getInfo(dbpool, strPoaCode);
		PrpJpoaTraceSchema prpJpoaTraceSchema = new PrpJpoaTraceSchema();
		int intIoType = 1;
		if (prpJpayRefDetailSchema.getPayWay().substring(1,2).equals("1")) {
			prpJpoaTraceSchema.setIotype("1");
			intIoType = -1;
		} else {
			prpJpoaTraceSchema.setIotype("0");
		}
		double dblDeliqueeFee = dbPrpJpoaMain.getDeliqueefee() + dblPayRefFee * intIoType;
		String strStartDate = dbPrpJpoaMain.getStartdate();
		String strStopDate = dbPrpJpoaMain.getStopdate();
		if (dbPrpJpoaMain.getValiddate().equals("0")) {
			throw new UserException(-96, -1167, "BLPrpJpoaMain.voucherVerifyPoa()",
					"预收款支付失败：预收款帐号无效！");
		}
		if (!strStartDate.equals("")
				&& now.before(new DateTime(strStartDate, DateTime.YEAR_TO_DAY))) {
			throw new UserException(-96, -1167, "BLPrpJpoaMain.voucherVerifyPoa()",
					"预收款支付失败：预收款帐号未启用！");
		}
		if (!strStopDate.equals("") && now.after(new DateTime(strStopDate, DateTime.YEAR_TO_DAY))) {
			throw new UserException(-96, -1167, "BLPrpJpoaMain.voucherVerifyPoa()",
					"预收款支付失败：预收款帐号已停用！");
		}
		if (dblDeliqueeFee < 0 && prpJpayRefDetailSchema.getPayWay().substring(1,2).equals("1")) {
            
            DBPrpDcustomerUnitNew customerUnitNew = new DBPrpDcustomerUnitNew();
            customerUnitNew.getInfo(dbPrpJpoaMain.getAccmasterno());
            String strOrganizeCode=customerUnitNew.getOrganizeCode();
            String strOrganizeCodeList = SysConfig.getProperty("SWITCH_OF_POAMAIN");
            boolean isFlag = false;
            if (strOrganizeCodeList == null || "".equals(strOrganizeCodeList) || 
            		strOrganizeCode == null || "".equals(strOrganizeCode)) {
            	isFlag = true;
            } else if (strOrganizeCodeList.indexOf(strOrganizeCode) == -1) {
                isFlag = true;
            }
            if (isFlag) {
            	throw new UserException(-96, -1167, "BLPrpJpoaMain.voucherVerifyPoa()", "预收款支付失败：余额不足！");
            }
            
		}
		if (!dbPrpJpoaMain.getCurrency().equals(prpJpayRefDetailSchema.getCurrency2())) {
			throw new UserException(-96, -1167, "BLPrpJpoaMain.voucherVerifyPoa()",
					"预收款支付失败：币种不一致！");
		}
		dbPrpJpoaMain.setDeliqueefee(dblDeliqueeFee);
		dbPrpJpoaMain.update(dbpool);
		
		prpJpayRefDetailSchema.setComCode(dbPrpJpoaMain.getComcode());
		prpJpayRefDetailSchema.setHandler1Code(dbPrpJpoaMain.getHandlercode());
		
		DBPrpJpoaTrace dbPrpJpoaTrace = new DBPrpJpoaTrace();
		int maxSerailNo = Integer.parseInt(dbPrpJpoaTrace.getMaxSerailNo(dbpool, strPoaCode));
		prpJpoaTraceSchema.setPoacode(strPoaCode);
		prpJpoaTraceSchema.setCentercode(dbPrpJpoaMain.getCentercode());
		prpJpoaTraceSchema.setPoaType(dbPrpJpoaMain.getPoatype());
		prpJpoaTraceSchema.setSerailno(String.valueOf(maxSerailNo + 1));
		prpJpoaTraceSchema.setComcode(dbPrpJpoaMain.getComcode());
		prpJpoaTraceSchema.setAgentcode(dbPrpJpoaMain.getAgentcode());
		prpJpoaTraceSchema.setHandlercode(dbPrpJpoaMain.getHandlercode());
		prpJpoaTraceSchema.setInputdate(String.valueOf(new DateTime(new Date(),
				DateTime.YEAR_TO_SECOND)));
		prpJpoaTraceSchema.setCurrency(dbPrpJpoaMain.getCurrency());
		prpJpoaTraceSchema.setPayfee(dblPayRefFee);
		prpJpoaTraceSchema.setRealpayrefno(prpJpayRefDetailSchema.getRealPayRefNo());
		prpJpoaTraceSchema.setRemark(blPrpDcode.translateCode("PayWay", prpJpayRefDetailSchema.getPayWay(), true));
		dbPrpJpoaTrace.setSchema(prpJpoaTraceSchema);
		dbPrpJpoaTrace.insert(dbpool);

	}
	
	/**
	 * 主函数
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		
	}
}
