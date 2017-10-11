package com.sp.prpall.blsvr.jf;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashSet;
import java.util.Vector;

import com.sp.account.blsvr.BLAccMainVoucher;
import com.sp.account.blsvr.BLAccVoucher;
import com.sp.prpall.blsvr.cb.BLPrpCPplan;
import com.sp.prpall.blsvr.cb.BLPrpCplan;
import com.sp.prpall.dbsvr.cb.*;
import com.sp.prpall.dbsvr.jf.*;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.*;
import com.sp.reins.blsvr.BLFjSettle;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utility.*;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BLPrpJpaymentVoucher {
	protected final Log logger = LogFactory.getLog(getClass());
	private Vector schemas = new Vector();

	private Vector coinsSchemas = new Vector();

	private BLPrpJpayRefDetail blPrpJpayRefDetail = new BLPrpJpayRefDetail();

	private BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();

	private BLPrpJpayRefRec blPrpJpayRefRecCoins = new BLPrpJpayRefRec();

	private BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();

	private boolean blPackageFlag = false;

	private static Connection conn = null;

	private static CallableStatement cstmt = null;

	/**
	 * 构造函数
	 */
	public BLPrpJpaymentVoucher() {
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

	public void initCoinsArr() throws Exception {
		coinsSchemas = new Vector();
	}

	/**
	 * 增加一条PrpJpayRefMainSchema记录
	 * @param iPrpJpayRefMainSchema PrpJpayRefMainSchema
	 * @throws Exception
	 */
	public void setArr(PrpJpayRefMainSchema iPrpJpayRefMainSchema) throws Exception {
		try {
			schemas.add(iPrpJpayRefMainSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	public void setCoinsArr(PrpJpayRefMainSchema iPrpJpayRefMainSchema) throws Exception {
		try {
			coinsSchemas.add(iPrpJpayRefMainSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到一条PrpJpayRefMainSchema记录
	 * @param index 下标
	 * @return 一个PrpJpayRefMainSchema对象
	 * @throws Exception
	 */
	public PrpJpayRefMainSchema getArr(int index) throws Exception {
		PrpJpayRefMainSchema prpJpayRefMainSchema = null;
		try {
			prpJpayRefMainSchema = (PrpJpayRefMainSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJpayRefMainSchema;
	}

	public PrpJpayRefMainSchema getCoinsArr(int index) throws Exception {
		PrpJpayRefMainSchema prpJpayRefMainSchema = null;
		try {
			prpJpayRefMainSchema = (PrpJpayRefMainSchema) this.coinsSchemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJpayRefMainSchema;
	}

	/**
	 * 删除一条PrpJpayRefMainSchema记录
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

	public int getCoinsSize() throws Exception {
		return this.coinsSchemas.size();
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
	 * @desc 给私有对象BLPrpJpayRefRec赋值
	 * @return
	 * @throws Exception
	 */
	public void setBLPrpJpayRefRec(BLPrpJpayRefRec iBLPrpJpayRefRec) throws Exception {
		this.blPrpJpayRefRec = iBLPrpJpayRefRec;
	}

	/**
	 * @desc 得到一条BLPrpJpayRefRec对象
	 * @return 一个BLPrpJpayRefRec对象
	 * @throws Exception
	 */
	public BLPrpJpayRefRec getBLPrpJpayRefRec() throws Exception {
		return this.blPrpJpayRefRec;
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
		DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
		if (iLimitCount > 0 && dbPrpJpayRefMain.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJpayRefMain.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJpayRefMain WHERE " + iWherePart;
			schemas = dbPrpJpayRefMain.findByConditions(strSqlStatement);
		}
	}

	/**
	 * 根据DbPool获取Connection
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
	 * 调用总帐接口的存储过程
	 * @param dbpool数据库连接池
	 * @param realPayRefNo实际收付号
	 * @return String 凭证号
	 * @throws 异常类
	 */
	public String callProcedure(DbPool dbpool, String realPayRefNo) throws Exception {
		String messageCode = "";
		String messageDesc = "";
		conn = this.getConnection(dbpool);
		cstmt = conn.prepareCall("{call sunshvoucrt_pkg.payment_voucher(?,?,?)}");
		cstmt.registerOutParameter(2, Types.VARCHAR);
		cstmt.registerOutParameter(3, Types.VARCHAR);
		cstmt.setString(1, realPayRefNo);
		cstmt.setString(2, messageCode);
		cstmt.setString(3, messageDesc);
		cstmt.execute();
		messageCode = cstmt.getString(2);
		messageDesc = cstmt.getString(3);
		if (!messageCode.equals("0")) {
			throw new UserException(-96, -1167, "BLPrpJpaymantVoucher.callProcedure()", "收付款确认失败："
					+ messageDesc);
		}
		return messageDesc;
	}

	/**
	 * 调用XXXXX接口
	 * @param dbpool数据库连接池
	 * @param
	 * @return
	 * @throws 异常类
	 */
	public void callClaimProcedure(DbPool dbpool, String claimNo, String compensateNo,
			double amount, String payDate, String payerCode, String paycomCode, String classCode,
			String riskCode, String handlerCode, String centerCode) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		conn = this.getConnection(dbpool);
		cstmt = conn.prepareCall("{call claimrealpay_proc(?,?,?,?,?,?,?,?,?,?)}");
		cstmt.setString(1, claimNo);
		cstmt.setString(2, compensateNo);
		cstmt.setDouble(3, amount);
		cstmt.setString(4, payDate);
		cstmt.setString(5, payerCode);
		cstmt.setString(6, paycomCode);
		cstmt.setString(7, classCode);
		cstmt.setString(8, riskCode);
		cstmt.setString(9, handlerCode);
		cstmt.setString(10, centerCode);
		cstmt.execute();
	}
	/**
	 * 收付款确认
	 * @throws 异常类
	 */
	public String paymentVoucher(PrpJpayRefVoucherSchema iPrpJpayRefVoucherSchema) throws Exception {
		String strVoucherNo = "";
		String strWherePart = "";
		int intNoCount = 1; 
		DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
		BLPrpDcode blPrpDcode = new BLPrpDcode();
		
		if (blPackageFlag == false) {
			this.blPrpJpayRefRec.queryInvioceCoins(this.schemas);
		}
		
		strWherePart = " PayRefNo IN ('" + this.getArr(0).getPayRefNo() + "'";
		String strPayRefNoTmp = this.getArr(0).getPayRefNo();
		for (int i = 1; i < this.getSize(); i++) {
			
			if (this.getArr(i).getPayRefNo().equals(strPayRefNoTmp)) {
				continue;
			} else {
				intNoCount++;
				strPayRefNoTmp = this.getArr(i).getPayRefNo();
				strWherePart += ",'" + this.getArr(i).getPayRefNo() + "'";
			}
		}
		strWherePart += ") ";
		String strOrderWhere = " ORDER BY PayRefNo";
		String strRefRecWherePart = strWherePart + " and payreftimes <1000 " + strOrderWhere;
		this.blPrpJpayRefRec.query(strRefRecWherePart, 0);
		if (this.blPrpJpayRefRec.getSize() == 0) {
			throw new UserException(-96, -1167, "BLPrpJpaymantVoucher.paymentVoucher()",
					"查询PrpJpayRefRec失败！");
		}
		
		String strPayRefDate = this.getArr(0).getPayRefDate();
		String strPayRefCode = this.getArr(0).getPayRefCode();
		String strPayRefNoType = this.getArr(0).getPayRefNoType();
		if ((strPayRefDate.equals("") || strPayRefDate == null)
				&& (strPayRefCode.equals("") || strPayRefCode == null)
				&& (strPayRefNoType.equals("") || strPayRefNoType == null)) {
			throw new UserException(-96, -1167, "BLPrpJpaymantVoucher.paymentVoucher()",
					"strPayRefDate、strPayRefCode、strPayRefNoType字段为空！");
		}
		String strCenterCode = this.getArr(0).getCenterCode();
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE); 
			dbpool.beginTransaction();
			
			ChgDate iDate = new ChgDate();
			String strOperateDate = iDate.getCurrentTime("yyyy-MM-dd");
			String strYearMonth = strOperateDate.substring(0,4)+strOperateDate.substring(5,7);
			DBPrpJpayRefVoucher dBPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
			String strRealPayRefNo = dBPrpJpayRefVoucher.genRealPayRefNo();
			dBPrpJpayRefVoucher.setRealPayRefNo(strRealPayRefNo);
			dBPrpJpayRefVoucher.setPayRefNoType(strPayRefNoType);
			dBPrpJpayRefVoucher.setCenterCode(strCenterCode);
			dBPrpJpayRefVoucher.setVoucherDate(this.getArr(0).getPayRefDate());
			dBPrpJpayRefVoucher.setComCode(this.getArr(0).getPayRefUnit());
			dBPrpJpayRefVoucher.setPayRefCode(this.getArr(0).getPayRefCode());
			dBPrpJpayRefVoucher.setToCenterCode(this.getArr(0).getBranchCode());
			dBPrpJpayRefVoucher.setAttribute2(iPrpJpayRefVoucherSchema.getAttribute2());
			dBPrpJpayRefVoucher.setAttribute3(iPrpJpayRefVoucherSchema.getAttribute3());
			dBPrpJpayRefVoucher.insert(dbpool);
			
			
			if (this.getSize() == 0) {
				throw new UserException(-96, -1167, "BLPrpJpaymantVoucher.paymentVoucher()",
						"PrpJpayRefMain表生成的数据错误！");
			}
			for (int i = 0; i < this.getSize(); i++) {
				dbPrpJpayRefMain.setSchema(this.getArr(i));
				
				dbPrpJpayRefMain.setYearMonth(strYearMonth);
				dbPrpJpayRefMain.setRealPayRefNo(strRealPayRefNo);
				
				dbPrpJpayRefMain.setIdentifyType("1");
				dbPrpJpayRefMain.update(dbpool);
			}
			
			
			if (this.blPrpJpayRefDetail.getSize() == 0) {
				throw new UserException(-96, -1167, "BLPrpJpaymantVoucher.paymentVoucher()",
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
				if (blPrpJpayRefDetail.getArr(i).getPayWay().equals("421")) {
					this.blPrpJpayRefDetail.getArr(i).setComCode(
							this.blPrpJpayRefRec.getArr(0).getComCode());
					this.blPrpJpayRefDetail.getArr(i).setHandler1Code(
							this.blPrpJpayRefRec.getArr(0).getHandler1Code());
				}
			}
			this.blPrpJpayRefDetail.save(dbpool);
			
			
			
			PrpJpayRefRecSchema prpJpayRefRecSchema = null;
			DBPrpJpayRefRec dbPrpJpayRefRec = null;
			DBPrpJplanFee dbPrpJplanFee = null;
			
			
			for (int i = 0; i < this.blPrpJpayRefRec.getSize(); i++) {
				prpJpayRefRecSchema = blPrpJpayRefRec.getArr(i);
				
				if (prpJpayRefRecSchema.getCertiType().equals("E")
						&& Double.parseDouble(prpJpayRefRecSchema.getPlanFee()) < 0) {
					PrpJpayRefRecSchema prpJpayRefRecSchema1 = null;
					
					double cPlanFee = 0;
					
					for (int k = 0; k < this.blPrpJpayRefRec.getSize(); k++) {
						prpJpayRefRecSchema1 = blPrpJpayRefRec.getArr(k);
						if ((prpJpayRefRecSchema1.getCertiType().equals("P") && prpJpayRefRecSchema1
								.getCertiNo().equals(prpJpayRefRecSchema.getPolicyNo()))
								|| (prpJpayRefRecSchema1.getCertiType().equals("E")
										&& prpJpayRefRecSchema1.getPolicyNo().equals(
												prpJpayRefRecSchema.getPolicyNo()) && !prpJpayRefRecSchema1
										.getCertiNo().equals(prpJpayRefRecSchema.getCertiNo()))) {
							if (prpJpayRefRecSchema1.getPayRefReason().equals("R82")
									|| prpJpayRefRecSchema1.getPayRefReason().equals("R42")
									|| prpJpayRefRecSchema1.getPayRefReason().equals("P82")
									|| prpJpayRefRecSchema1.getPayRefReason().equals("P92")) {
							} else {
								cPlanFee += Double.parseDouble(prpJpayRefRecSchema1.getPlanFee());
							}
						}
					}
					
					String strConditionFee = " (CertiType='P' AND CertiNo='"
							+ prpJpayRefRecSchema.getPolicyNo() + "' AND RealPayRefFee<>0 )"
							+ " OR (CertiType='E' AND PolicyNo='"
							+ prpJpayRefRecSchema.getPolicyNo()
							+ "' AND RealPayRefFee<>0 ) AND PayRefReason !='P40' ";
					this.blPrpJplanFee.querySum(dbpool, strConditionFee);
					if (this.blPrpJplanFee.getSize() > 0) {
						cPlanFee += Double.parseDouble(blPrpJplanFee.getArr(0).getPlanFee());
					}
					if (cPlanFee == 0) {
						throw new UserException(-96, -1167,
								"BLPrpJpaymantVoucher.paymentVoucher()", prpJpayRefRecSchema
										.getCertiNo()
										+ "对应的XX未做收付款确认！");
					}
					if (Str.round(cPlanFee, 2) < Math.abs(Double.parseDouble(prpJpayRefRecSchema
							.getPlanFee()))) {
						throw new UserException(-96, -1167,
								"BLPrpJpaymantVoucher.paymentVoucher()", prpJpayRefRecSchema
										.getCertiNo()
										+ "批单退费不能超过XX收费(" + cPlanFee + ")！");
					}
				}
				
				if (prpJpayRefRecSchema.getCertiType().equals("C")
						&& Double.parseDouble(prpJpayRefRecSchema.getPlanFee()) > 0) {
					double cPlanFee = 0;
					String strConditionFee = " (CertiType='P' AND CertiNo='"
							+ prpJpayRefRecSchema.getPolicyNo() + "' AND RealPayRefFee<>0 )"
							+ " OR (CertiType='E' AND PolicyNo='"
							+ prpJpayRefRecSchema.getPolicyNo() + "' AND RealPayRefFee<>0 )";
					this.blPrpJplanFee.querySum(dbpool, strConditionFee);
					if (this.blPrpJplanFee.getSize() > 0) {
						cPlanFee += Double.parseDouble(blPrpJplanFee.getArr(0).getPlanFee());
					}
					if (cPlanFee == 0) {
						throw new UserException(-96, -1167,
								"BLPrpJpaymantVoucher.paymentVoucher()", prpJpayRefRecSchema
										.getCertiNo()
										+ "对应的XX未做收付款确认！");
					}
				}
				if (prpJpayRefRecSchema.getCertiType().equals("S")
						&& !prpJpayRefRecSchema.getPayRefReason().equals("P93")
						&& Double.parseDouble(prpJpayRefRecSchema.getPlanFee()) > 0) {
					double cPlanFee = 0;
					String strConditionFee = " CertiType IN ('P','E') AND CertiNo='"
							+ prpJpayRefRecSchema.getCertiNo()
							+ "' AND RealPayRefFee<>0 AND PayRefReason not in('R72','R73','R74')";
					this.blPrpJplanFee.querySum(dbpool, strConditionFee);
					if (this.blPrpJplanFee.getSize() > 0) {
						cPlanFee += Double.parseDouble(blPrpJplanFee.getArr(0).getPlanFee());
					}
					if (cPlanFee == 0) {
						throw new UserException(-96, -1167,
								"BLPrpJpaymantVoucher.paymentVoucher()", prpJpayRefRecSchema
										.getCertiNo()
										+ "对应的XX未做收付款确认！");
					}
				}
				
				
				if (prpJpayRefRecSchema.getCertiType().equals("S")
						&& prpJpayRefRecSchema.getPayRefReason().equals("P93")
						&& Double.parseDouble(prpJpayRefRecSchema.getPlanFee()) > 0) {
					double cPlanFee = 0;
					String strConditionFee = " CertiType ='P' AND CertiNo='"
							+ prpJpayRefRecSchema.getPolicyNo()
							+ "' AND RealPayRefFee<>0 AND PayRefReason not in('R72','R73','R74')";
					this.blPrpJplanFee.querySum(dbpool, strConditionFee);
					if (this.blPrpJplanFee.getSize() > 0) {
						cPlanFee += Double.parseDouble(blPrpJplanFee.getArr(0).getPlanFee());
					}
					if (cPlanFee == 0) {
						throw new UserException(-96, -1167,
								"BLPrpJpaymantVoucher.paymentVoucher()", prpJpayRefRecSchema
										.getCertiNo()
										+ "对应的XX未做收付款确认！");
					}
				}
			}
			
			BLPrpJpayRefRec blPrpjPayRefRecSettle = new BLPrpJpayRefRec();
			BLPrpJsettle blPrpJsettleTmp = new BLPrpJsettle();
			
			
			for (int i = 0; i < this.blPrpJpayRefRec.getSize(); i++) {
				prpJpayRefRecSchema = blPrpJpayRefRec.getArr(i);
				
				dbPrpJpayRefRec = new DBPrpJpayRefRec();
				dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
				dbPrpJpayRefRec.setPayRefDate(strPayRefDate);
				dbPrpJpayRefRec.setIdentifyType("1");
				dbPrpJpayRefRec.setRealPayRefNo(strRealPayRefNo);
				dbPrpJpayRefRec.update(dbpool);
				
				
				if ((prpJpayRefRecSchema.getCertiType().equals("P")
						|| prpJpayRefRecSchema.getCertiType().equals("E"))
						&&!prpJpayRefRecSchema.getPayRefReason().equals("R72")
						&&!prpJpayRefRecSchema.getPayRefReason().equals("R73")
						&&!prpJpayRefRecSchema.getPayRefReason().equals("R74")) {
					
					DBPrpJmanageFee dbPrpJmanageFee = new DBPrpJmanageFee();
					
					
					
					int intResult = dbPrpJmanageFee.getInfoNew(dbpool, "0", prpJpayRefRecSchema
							.getCertiNo(), prpJpayRefRecSchema.getSerialNo());
					
					if (intResult == 100) {
					    
						logger.info("未找到批单号" + prpJpayRefRecSchema.getCertiNo() + "对应的管理费用记录！");
					    
					} else {
						dbPrpJmanageFee.setPayRefDate(strPayRefDate);
						dbPrpJmanageFee.setPayRefFee(prpJpayRefRecSchema.getPayRefFee());
						dbPrpJmanageFee.update(dbpool);
					}
				}
				
				if (prpJpayRefRecSchema.getPayRefReason().equals("P6B")) {
					BLPrpJUnInsurer blPrpJunInsurer = new BLPrpJUnInsurer();
					blPrpJunInsurer.insert4Tables(dbpool, prpJpayRefRecSchema);
				}
				

				
				if (prpJpayRefRecSchema.getPayRefReason().equals("P6C")) {
					BLPrpJUnInsurer blPrpJunInsurer = new BLPrpJUnInsurer();
					blPrpJunInsurer.writeBackPrpjuninsure(dbpool, prpJpayRefRecSchema);
				}
				

				
				
				if (prpJpayRefRecSchema.getCertiType().equals("D")
						&& prpJpayRefRecSchema.getPayRefReason().equals("P60")) {
					BLPrpJUnInsurer blPrpJunInsurer = new BLPrpJUnInsurer();
					blPrpJunInsurer.writeBackPrpjuninsure1(dbpool, prpJpayRefRecSchema);
				}
				
				
				if (prpJpayRefRecSchema.getCertiType().equals("C")
						&& prpJpayRefRecSchema.getClassCode().equals("05")
						&& prpJpayRefRecSchema.getPayRefReason().equals("P60")) {
					this.callClaimProcedure(dbpool, prpJpayRefRecSchema.getClaimNo(),
							prpJpayRefRecSchema.getCertiNo(), Double
									.parseDouble(prpJpayRefRecSchema.getPayRefFee()),
							strPayRefDate, dBPrpJpayRefVoucher.getPayRefCode(), dBPrpJpayRefVoucher
									.getComCode(), prpJpayRefRecSchema.getClassCode(),
							prpJpayRefRecSchema.getRiskCode(), prpJpayRefRecSchema
									.getHandler1Code(),strCenterCode);
				}
				
				if (prpJpayRefRecSchema.getCertiType().equals("R"))
					continue;
				
				dbPrpJplanFee = new DBPrpJplanFee();
				dbPrpJplanFee.getInfoForUpdate(dbpool, prpJpayRefRecSchema.getCertiType(),
						prpJpayRefRecSchema.getCertiNo(), prpJpayRefRecSchema.getSerialNo(),
						prpJpayRefRecSchema.getPayRefReason());
				dbPrpJplanFee
						.setRealPayRefFee(""
								+ (Double.parseDouble(Str.chgStrZero(dbPrpJplanFee
										.getRealPayRefFee())) + Double
										.parseDouble(prpJpayRefRecSchema.getPlanFee())));
				
				
				
				if ((dbPrpJplanFee.getCertiType().equals("C") || dbPrpJplanFee.getCertiType()
						.equals("Y"))
						&& Double.parseDouble(Str.chgStrZero(dbPrpJplanFee.getPlanFeeCNY())) == 0) {
					
					if (dbPrpJpayRefRec.getCurrency2().equals("CNY")) {
						dbPrpJplanFee.setExchangeRate(dbPrpJpayRefRec.getExchangeRate());
					} else {
						
						dbPrpJplanFee.setExchangeRate(""
								+ PubTools.getExchangeRate(dbpool, dbPrpJplanFee.getCurrency1(),
										"CNY", strPayRefDate));
					}
					double dblPlanFeeCNY = Double.parseDouble(dbPrpJplanFee.getPlanFee())
							* Double.parseDouble(dbPrpJplanFee.getExchangeRate());
					dblPlanFeeCNY = Str.round(Str.round(dblPlanFeeCNY, 8), 2);
					dbPrpJplanFee.setPlanFeeCNY("" + dblPlanFeeCNY);
				}
				
				dbPrpJplanFee.update(dbpool);
				
				
				BLPrpJtaxSettle blPrpJtaxSettle = new BLPrpJtaxSettle();
				DBPrpJtaxSettle dbPrpJtaxSettle = new DBPrpJtaxSettle();
				if (prpJpayRefRecSchema.getRiskCode().equals("0507")
						&& (prpJpayRefRecSchema.getPayRefReason().equals("R72")
								|| prpJpayRefRecSchema.getPayRefReason().equals("R73")
								|| prpJpayRefRecSchema.getPayRefReason().equals("R74") || prpJpayRefRecSchema
								.getCertiType().equals("P"))) {
					String sql = " policyno='" + prpJpayRefRecSchema.getPolicyNo()
							+ "' and serialno='" + prpJpayRefRecSchema.getSerialNo() + "'";
					blPrpJtaxSettle.query(dbpool, sql);
					for (int j = 0; j < blPrpJtaxSettle.getSize(); j++) {
						dbPrpJtaxSettle.setSchema(blPrpJtaxSettle.getArr(j));
						if (prpJpayRefRecSchema.getCertiType().equals("P")
								&& !prpJpayRefRecSchema.getPayRefReason().equals("R72")
								&& !prpJpayRefRecSchema.getPayRefReason().equals("R73")
								&& !prpJpayRefRecSchema.getPayRefReason().equals("R74")
								&& !dbPrpJtaxSettle.getTaxRelifFlag().equals("2")
								&& !dbPrpJtaxSettle.getTaxRelifFlag().equals("4")
								&& !dbPrpJtaxSettle.getTaxRelifFlag().equals("5")) {
							continue;
						}
						if (dbPrpJtaxSettle.getPayRefReason().equals(
								prpJpayRefRecSchema.getPayRefReason())) {
							dbPrpJtaxSettle.setPayRefFee(""
									+ (Double.parseDouble(dbPrpJtaxSettle.getPayRefFee()) + Double
											.parseDouble(prpJpayRefRecSchema.getPlanFee())));
						}
						dbPrpJtaxSettle.setPayRefDate(strPayRefDate);
						
						
						dbPrpJtaxSettle.update(dbpool);
					}
				}
				
				
				
				if (prpJpayRefRecSchema.getPayRefReason().equals("R82")
						|| prpJpayRefRecSchema.getPayRefReason().equals("R42")
						|| prpJpayRefRecSchema.getPayRefReason().equals("P82")
						|| prpJpayRefRecSchema.getPayRefReason().equals("P92")
						|| prpJpayRefRecSchema.getPayRefReason().equals("P81")
						|| prpJpayRefRecSchema.getPayRefReason().equals("R81")
						|| prpJpayRefRecSchema.getPayRefReason().equals("R72")
						|| prpJpayRefRecSchema.getPayRefReason().equals("R73")
						|| prpJpayRefRecSchema.getPayRefReason().equals("R74")) {

				} else {
					if (prpJpayRefRecSchema.getCertiType().equals("P")
							|| prpJpayRefRecSchema.getCertiType().equals("E")) {
						
						DBPrpCplan dbPrpCplan = new DBPrpCplan();
						int intReturn = 0;
						if (prpJpayRefRecSchema.getCertiType().equals("P")) {
							
							String serialNo = prpJpayRefRecSchema.getPayNo();
							if(prpJpayRefRecSchema.getPayRefReason().equals("R14")
									|| prpJpayRefRecSchema.getPayRefReason().equals("R15")
									|| prpJpayRefRecSchema.getPayRefReason().equals("R16")
									|| prpJpayRefRecSchema.getPayRefReason().equals("R17"))
							{
								serialNo = prpJpayRefRecSchema.getSerialNo();
							}
							intReturn = dbPrpCplan.getInfo(dbpool,
									prpJpayRefRecSchema.getCertiNo(),serialNo);
							if (intReturn == 0) {
								
								if (Double.parseDouble(ChgData.chgStrZero(dbPrpCplan
										.getDelinquentFee())) == 0) {
									
									
									
									
									dbPrpCplan.setDelinquentFee("" + 0);
									dbPrpCplan.update(dbpool);
								} else {
									
									double DelinquentFeeP = Double.parseDouble(ChgData
											.chgStrZero(dbPrpCplan.getDelinquentFee()))
											- Double.parseDouble(ChgData
													.chgStrZero(prpJpayRefRecSchema.getPlanFee()));
									if (DelinquentFeeP == Double.parseDouble(ChgData
											.chgStrZero(dbPrpCplan.getDelinquentFee()))) {
										throw new UserException(-96, -1167,
												"BLPrpJpaymantVoucher.paymentVoucher()",
												prpJpayRefRecSchema.getCertiNo() + "回写PrpCplan表失败！");
									}
									dbPrpCplan.setDelinquentFee(""
											+ (Double.parseDouble(ChgData.chgStrZero(dbPrpCplan
													.getDelinquentFee())) - Double
													.parseDouble(ChgData
															.chgStrZero(prpJpayRefRecSchema
																	.getPlanFee()))));
									dbPrpCplan.update(dbpool);
								}
							}
						} else {
							BLPrpCplan blPrpCplan = new BLPrpCplan();
							String strWhere = " PolicyNo='"+prpJpayRefRecSchema.getPolicyNo().trim()+"' and EndorseNo='" + prpJpayRefRecSchema.getCertiNo() + "' ";
							blPrpCplan.query(dbpool, strWhere, 0);
							if (blPrpCplan.getSize() > 0) {
								dbPrpCplan.setSchema(blPrpCplan.getArr(0));
								
								if (Double.parseDouble(ChgData.chgStrZero(dbPrpCplan
										.getDelinquentFee())) == 0) {
									
									
									
									
									dbPrpCplan.setDelinquentFee("" + 0);
									dbPrpCplan.update(dbpool);
								} else {
									double DelinquentFeeE = Double.parseDouble(ChgData
											.chgStrZero(dbPrpCplan.getDelinquentFee()))
											- Double.parseDouble(ChgData
													.chgStrZero(prpJpayRefRecSchema.getPlanFee()));
									if (DelinquentFeeE == Double.parseDouble(ChgData
											.chgStrZero(dbPrpCplan.getDelinquentFee()))) {
										throw new UserException(-96, -1167,
												"BLPrpJpaymantVoucher.paymentVoucher()",
												prpJpayRefRecSchema.getCertiNo() + "回写PrpCplan表失败！");
									}
									dbPrpCplan.setDelinquentFee(""
											+ (Double.parseDouble(ChgData.chgStrZero(dbPrpCplan
													.getDelinquentFee())) - Double
													.parseDouble(ChgData
															.chgStrZero(prpJpayRefRecSchema
																	.getPlanFee()))));
									dbPrpCplan.update(dbpool);
								}
							}
						}
						
						DBPrpCPplan dbPrpCPplan = new DBPrpCPplan();
						if (prpJpayRefRecSchema.getCertiType().equals("P")) {
							
							String serialNo = prpJpayRefRecSchema.getPayNo();
							if(prpJpayRefRecSchema.getPayRefReason().equals("R14")
									|| prpJpayRefRecSchema.getPayRefReason().equals("R15")
									|| prpJpayRefRecSchema.getPayRefReason().equals("R16")
									|| prpJpayRefRecSchema.getPayRefReason().equals("R17"))
							{
								serialNo = prpJpayRefRecSchema.getSerialNo();
							}
							intReturn = dbPrpCPplan.getInfo(dbpool, prpJpayRefRecSchema
									.getCertiNo(), serialNo);
							if (intReturn == 0) {
								
								if (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan
										.getDelinquentFee())) == 0) {
									
									
									
									dbPrpCPplan.setDelinquentFee("" + 0);
									dbPrpCPplan.update(dbpool);
								} else {
									
									double DelinquentFeeE = Double.parseDouble(ChgData
											.chgStrZero(dbPrpCPplan.getDelinquentFee()))
											- Double.parseDouble(ChgData
													.chgStrZero(prpJpayRefRecSchema.getPlanFee()));
									if (DelinquentFeeE == Double.parseDouble(ChgData
											.chgStrZero(dbPrpCPplan.getDelinquentFee()))) {
										throw new UserException(-96, -1167,
												"BLPrpJpaymantVoucher.paymentVoucher()",
												prpJpayRefRecSchema.getCertiNo()
														+ "回写PrpCPplan表失败！");
									}
									dbPrpCPplan.setDelinquentFee(""
											+ (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan
													.getDelinquentFee())) - Double
													.parseDouble(ChgData
															.chgStrZero(prpJpayRefRecSchema
																	.getPlanFee()))));
									dbPrpCPplan.update(dbpool);
								}
							}
						} else {
							BLPrpCPplan blPrpCPplan = new BLPrpCPplan();
							String strWhere = " PolicyNo='"+prpJpayRefRecSchema.getPolicyNo().trim()+"' and EndorseNo='" + prpJpayRefRecSchema.getCertiNo() + "' ";
							blPrpCPplan.query(dbpool, strWhere, 0);
							if (blPrpCPplan.getSize() > 0) {
								dbPrpCPplan.setSchema(blPrpCPplan.getArr(0));
								
								if (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan
										.getDelinquentFee())) == 0) {
									
									
									
									dbPrpCPplan.setDelinquentFee("" + 0);
									dbPrpCPplan.update(dbpool);
								} else {
									
									double DelinquentFeeE = Double.parseDouble(ChgData
											.chgStrZero(dbPrpCPplan.getDelinquentFee()))
											- Double.parseDouble(ChgData
													.chgStrZero(prpJpayRefRecSchema.getPlanFee()));
									if (DelinquentFeeE == Double.parseDouble(ChgData
											.chgStrZero(dbPrpCPplan.getDelinquentFee()))) {
										throw new UserException(-96, -1167,
												"BLPrpJpaymantVoucher.paymentVoucher()",
												prpJpayRefRecSchema.getCertiNo()
														+ "回写PrpCPplan表失败！");
									}
									
									dbPrpCPplan.setDelinquentFee(""
											+ (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan
													.getDelinquentFee())) - Double
													.parseDouble(ChgData
															.chgStrZero(prpJpayRefRecSchema
																	.getPlanFee()))));
									dbPrpCPplan.update(dbpool);
								}
							}
						}
					}
				}
				if (prpJpayRefRecSchema.getCoinsFlag().equals("1")
						&& prpJpayRefRecSchema.getCoinsType().equals("1")
						&& (prpJpayRefRecSchema.getCertiType().equals("P") || prpJpayRefRecSchema
								.getCertiType().equals("E"))) {
					
					String strWhereCoins1 = "CertiType='" + prpJpayRefRecSchema.getCertiType()
							+ "' AND CertiNo='" + prpJpayRefRecSchema.getCertiNo()
							+ "' AND SerialNo LIKE '" + prpJpayRefRecSchema.getSerialNo()
							+ "%' ORDER BY CoinsType ";
					
					BLPrpJplanFee blPrpJplanFeeCoins1 = new BLPrpJplanFee();
					blPrpJplanFeeCoins1.query(dbpool, strWhereCoins1);
					double dblFeeCoins1 = 0;
					for (int m = 0; m < blPrpJplanFeeCoins1.getSize(); m++) {
						if (blPrpJplanFeeCoins1.getArr(m).getCoinsType().equals("1")) {
							continue;
						} else {
							dbPrpJpayRefRec = new DBPrpJpayRefRec();
							dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
							dbPrpJpayRefRec.setPayRefReason(prpJpayRefRecSchema.getPayRefReason()
									.substring(0, 1)
									+ "81");
							dbPrpJpayRefRec
									.setSerialNo(blPrpJplanFeeCoins1.getArr(m).getSerialNo());
							dbPrpJpayRefRec.setPayRefDate(strPayRefDate);
							
							
							dbPrpJpayRefRec.setIdentifyType("1");
							dbPrpJpayRefRec.setIdentifyNumber("1");
							
							
							
							dbPrpJpayRefRec.setRealPayRefNo(strRealPayRefNo);
							
							dbPrpJpayRefRec
									.setPlanFee(""
											+ Double.parseDouble(blPrpJplanFeeCoins1.getArr(m)
													.getPlanFee()) * (-1));
							dbPrpJpayRefRec
									.setPayRefFee(""
											+ Double.parseDouble(blPrpJplanFeeCoins1.getArr(m)
													.getPlanFee()) * (-1));
							dblFeeCoins1 += Double.parseDouble(blPrpJplanFeeCoins1.getArr(m)
									.getPlanFee())
									* (-1);
							dbPrpJpayRefRec.insert(dbpool);
						}
					}
					dbPrpJpayRefRec = new DBPrpJpayRefRec();
					dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
					dbPrpJpayRefRec.setPayRefDate(strPayRefDate);
					
					
					dbPrpJpayRefRec.setIdentifyType("1");
					dbPrpJpayRefRec.setIdentifyNumber("1");
					
					
					dbPrpJpayRefRec.setRealPayRefNo(strRealPayRefNo);
					
					dbPrpJpayRefRec
							.setPlanFee(""
									+ (Double.parseDouble(prpJpayRefRecSchema.getPlanFee()) - dblFeeCoins1));
					dbPrpJpayRefRec
							.setPayRefFee(""
									+ (Double.parseDouble(prpJpayRefRecSchema.getPlanFee()) - dblFeeCoins1));
					dbPrpJpayRefRec.update(dbpool);
				}
				
				
				if (prpJpayRefRecSchema.getRiskCode().equals("YAB0")) {
					boolean flag = true;
					BLPrpJsettle blPrpJsettle = new BLPrpJsettle();
					strWherePart = " PolicyNo='" + prpJpayRefRecSchema.getPolicyNo()
							+ "' AND Currency2='" + prpJpayRefRecSchema.getCurrency2() + "'";
					blPrpJsettle.query(strWherePart);
					if (blPrpJsettle.getSize() == 0) {
						
						for (int j = 0; j < blPrpjPayRefRecSettle.getSize(); j++) {
							if (prpJpayRefRecSchema.getPolicyNo().equals(
									blPrpjPayRefRecSettle.getArr(j).getPolicyNo())
									&& prpJpayRefRecSchema.getCurrency2().equals(
											blPrpjPayRefRecSettle.getArr(j).getCurrency2())) {
								blPrpjPayRefRecSettle.getArr(j).setPlanFee(
										""
												+ (Double.parseDouble(blPrpjPayRefRecSettle.getArr(
														j).getPlanFee()) + Double
														.parseDouble(prpJpayRefRecSchema
																.getPlanFee())));
								blPrpjPayRefRecSettle.getArr(j).setPayRefFee(
										""
												+ (Double.parseDouble(blPrpjPayRefRecSettle.getArr(
														j).getPayRefFee()) + Double
														.parseDouble(prpJpayRefRecSchema
																.getPayRefFee())));
								flag = false;
								break;
							} else {
								flag = true;
							}
						}
						if (flag) {
							prpJpayRefRecSchema.setPayRefDate(strPayRefDate);
							blPrpjPayRefRecSettle.setArr(prpJpayRefRecSchema);
						}
					} else {
						for (int j = 0; j < blPrpJsettleTmp.getSize(); j++) {
							if (prpJpayRefRecSchema.getPolicyNo().equals(
									blPrpJsettleTmp.getArr(j).getPolicyNo())
									&& prpJpayRefRecSchema.getCurrency2().equals(
											blPrpJsettleTmp.getArr(j).getCurrency2())) {
								blPrpJsettleTmp.getArr(j).setPlanFee(
										""
												+ (Double.parseDouble(blPrpJsettleTmp.getArr(j)
														.getPlanFee()) + Double
														.parseDouble(prpJpayRefRecSchema
																.getPlanFee())));
								blPrpJsettleTmp.getArr(j).setLeftSettleFee(
										""
												+ (Double.parseDouble(blPrpJsettleTmp.getArr(j)
														.getLeftSettleFee()) + Double
														.parseDouble(prpJpayRefRecSchema
																.getPayRefFee())));
								flag = false;
								break;
							} else {
								flag = true;
							}
						}
						if (flag) {
							blPrpJsettle.getArr(0)
									.setPlanFee(
											""
													+ (Double.parseDouble(blPrpJsettle.getArr(0)
															.getPlanFee()) + Double
															.parseDouble(prpJpayRefRecSchema
																	.getPlanFee())));
							blPrpJsettle.getArr(0)
									.setLeftSettleFee(
											""
													+ (Double.parseDouble(blPrpJsettle.getArr(0)
															.getLeftSettleFee()) + Double
															.parseDouble(prpJpayRefRecSchema
																	.getPayRefFee())));
							blPrpJsettleTmp.setArr(blPrpJsettle.getArr(0));
						}
					}
				}
				
	  			  
	  			  if((prpJpayRefRecSchema.getCertiType().equals("P")||
	  					prpJpayRefRecSchema.getCertiType().equals("E")) &&
	  					!prpJpayRefRecSchema.getPayRefReason().equals("R72") &&
	  					!prpJpayRefRecSchema.getPayRefReason().equals("R73") &&
	  					!prpJpayRefRecSchema.getPayRefReason().equals("R74")){
	  				StringBuffer SQLBuffer =null;
	  				BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
	  				DBPrpJplanCommission dbPrpJplanCommission = null;
	  				double dbRealRefPremium =0;
	  				
	  				SQLBuffer =new StringBuffer();
	  				SQLBuffer.append(" CertiNo='"+prpJpayRefRecSchema.getCertiNo()+"'");
	  				SQLBuffer.append(" And Serialno='"+prpJpayRefRecSchema.getSerialNo()+"'");
	  				SQLBuffer.append(" And PayRefReason ='P90'");
	  				blPrpJplanCommission.query(dbpool, SQLBuffer.toString(), 0);
	  				
	  				

	  	  			for (int k = 0 ;k<blPrpJplanCommission.getSize();k++){
	  	  	  			dbPrpJplanCommission = new DBPrpJplanCommission();
	  	  				dbPrpJplanCommission.setSchema(blPrpJplanCommission.getArr(k));
	  	  	  			dbPrpJplanCommission.setPoliPayRefDate(strPayRefDate);
	  	  	  			if(dbPrpJplanCommission.getRealRefPremium()==null||
	  	  	  						dbPrpJplanCommission.getRealRefPremium().equals(""))
	  	  	  				dbRealRefPremium = Double.parseDouble(prpJpayRefRecSchema.getPlanFee());
	  	  	  			else
	  	  	  				dbRealRefPremium = Double.parseDouble(prpJpayRefRecSchema.getPlanFee())+
	  	  	  					 				   Double.parseDouble(dbPrpJplanCommission.getRealRefPremium());
	  	  	  			dbRealRefPremium = Str.round(dbRealRefPremium, 2);
	  	  	  			dbPrpJplanCommission.setRealRefPremium(dbRealRefPremium+"");
	  	  	  			dbPrpJplanCommission.setPayFlag("1");
	  	  	  			dbPrpJplanCommission.update(dbpool);
	  	  			}

	  				
	  				SQLBuffer =new StringBuffer();
	  				SQLBuffer.append(" PolicyNo='"+prpJpayRefRecSchema.getPolicyNo()+"'");
	  				SQLBuffer.append(" And Serialno='"+prpJpayRefRecSchema.getSerialNo()+"'");
	  				SQLBuffer.append(" And PayRefReason ='P93'");
	  				blPrpJplanCommission.query(dbpool, SQLBuffer.toString(), 0);
	  				
	  	  			for (int k = 0 ;k<blPrpJplanCommission.getSize();k++){
	  					dbPrpJplanCommission = new DBPrpJplanCommission();
	  	  				dbPrpJplanCommission.setSchema(blPrpJplanCommission.getArr(k));
	  	  				dbPrpJplanCommission.setPoliPayRefDate(prpJpayRefRecSchema.getPayRefDate());
	  	  				dbPrpJplanCommission.setRealRefPremium("0.00");
	  	  				dbPrpJplanCommission.setPayFlag("1");
	  	  				dbPrpJplanCommission.update(dbpool);
	  				}
	  			  }
	  			  
			}
			
			
			for (int i = 0; i < blPrpjPayRefRecSettle.getSize(); i++) {
				BLPrpJsettle blPrpJsettle = new BLPrpJsettle();
				blPrpjPayRefRecSettle.getArr(i).setCertiType("P");
				blPrpjPayRefRecSettle.getArr(i).setCertiNo(
						blPrpjPayRefRecSettle.getArr(i).getPolicyNo());
				blPrpjPayRefRecSettle.getArr(i).setPayRefReason("R10");
				blPrpJsettle.transData(dbpool, blPrpjPayRefRecSettle.getArr(i), "0"); 
			}
			
			for (int i = 0; i < blPrpJsettleTmp.getSize(); i++) {
				DBPrpJsettle dbPrpJsettle = new DBPrpJsettle();
				dbPrpJsettle.setSchema(blPrpJsettleTmp.getArr(i));
				dbPrpJsettle.update(dbpool);
			}
			

			strVoucherNo = callProcedure(dbpool, strRealPayRefNo);
			dbpool.commitTransaction();
			return strVoucherNo;
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
			if (cstmt != null)
				cstmt.close();
			if (conn != null)
				conn.close();
			dbpool.close();
		}
	}

	/**
	 * 未打包发票收付确认
	 */
	public String InvoiceVoucherVerify(String[] iArrVisaCode, String[] iArrVisaSerialNo,
			String iPayRefUnit, String ipayRefCoce, String ipayRefDate) throws UserException,
			SQLException, Exception {
		String strPayRefNo = "";
		String strPayRefNoCoins = "";
		
		this.blPrpJpayRefRec.queryInvioceCoins(iArrVisaCode, iArrVisaSerialNo);
		this.blPackageFlag = true;
		
		strPayRefNo = this.blPrpJpayRefRec.packageInvioce(iArrVisaCode, iArrVisaSerialNo,
				ipayRefCoce, iPayRefUnit, ipayRefDate);

		strPayRefNoCoins = this.blPrpJpayRefRec.packageInvioceCoins(ipayRefCoce, iPayRefUnit,
				ipayRefDate, this.blPrpJpayRefRecCoins, this.coinsSchemas);
		
		logger.info("生成打包号：" + strPayRefNo + "    " + strPayRefNoCoins);
		
		
		for (int i = 0; i < this.blPrpJpayRefDetail.getSize(); i++) {
			this.blPrpJpayRefDetail.getArr(i).setPayRefNo(strPayRefNo);
			this.blPrpJpayRefDetail.getArr(i).setSerialNo("" + (i + 1));
		}
		
		for (int i = 0; i < this.blPrpJpayRefRec.getSize(); i++) {
			this.blPrpJpayRefRec.getArr(i).setPayRefNo(strPayRefNo);
			
			this.blPrpJpayRefRec.getArr(i).setIdentifyNumber("1");
			
		}
		for (int i = 0; i < this.blPrpJpayRefRecCoins.getSize(); i++) {
			blPrpJpayRefRecCoins.getArr(i).setPayRefNo(strPayRefNoCoins);
			
			blPrpJpayRefRecCoins.getArr(i).setIdentifyNumber("1");
			
		}
		this.query(" PayRefNo='" + strPayRefNo + "'", 0);
		
		for (int i = 0; i < this.getSize(); i++) {
			this.getArr(i).setPayRefCode(ipayRefCoce);
			this.getArr(i).setPayRefUnit(iPayRefUnit);
			this.getArr(i).setPayRefDate(ipayRefDate);
		}
		
		String voucherNo = this.paymentVoucher(new PrpJpayRefVoucherSchema());
		return voucherNo;
	}
	
	/**
	 * 在XXXXX结算XXXXX确认
	 * @param strSettleNo 结算单号
	 * @param strPayRefCode 收付员
	 * @param strPayRefUnit 收付部门
	 * @param strPayRefDate 收付时间
	 * @param strCenterCode 核算单位
	 * @return String 凭证号
	 * @throws 异常类
	 */
	public String reinsSettlePaymentVoucher(String strSettleNo, String strPayRefCode,
			String strPayRefUnit, String strPayRefDate, String strCenterCode,String strAuxNumber) throws Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE); 
			dbpool.beginTransaction();
			
			ChgDate iDate = new ChgDate();
			String strOperateDate = iDate.getCurrentTime("yyyy-MM-dd");
			String strYearMonth = strOperateDate.substring(0, 4) + strOperateDate.substring(5, 7);
			DBPrpJpayRefVoucher dBPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
			String strRealPayRefNo = dBPrpJpayRefVoucher.genRealPayRefNo();
			dBPrpJpayRefVoucher.setRealPayRefNo(strRealPayRefNo);
			dBPrpJpayRefVoucher.setPayRefNoType("6");
			dBPrpJpayRefVoucher.setCenterCode(strCenterCode);
			dBPrpJpayRefVoucher.setVoucherDate(strPayRefDate);
			dBPrpJpayRefVoucher.setComCode(strPayRefUnit);
			dBPrpJpayRefVoucher.setPayRefCode(strPayRefCode);
			dBPrpJpayRefVoucher.setToCenterCode("0");
			dBPrpJpayRefVoucher.setYearMonth(strYearMonth);
			dBPrpJpayRefVoucher.setAttribute2(strAuxNumber);
			dBPrpJpayRefVoucher.insert(dbpool);
			
			
			if (this.blPrpJpayRefDetail.getSize() == 0) {
				throw new UserException(-96, -1167, "BLPrpJpaymantVoucher.paymentVoucher()",
						"PrpJpayRefDetail表生成的数据错误！");
			}
			for (int i = 0; i < this.blPrpJpayRefDetail.getSize(); i++) {
				
				this.blPrpJpayRefDetail.getArr(i).setRealPayRefNo(strRealPayRefNo);
				this.blPrpJpayRefDetail.getArr(i).setPayRefFee(
						""+ Math.abs(Double.parseDouble(this.blPrpJpayRefDetail.getArr(i)
										.getPayRefFee())));
			}
			this.blPrpJpayRefDetail.save(dbpool);
			
			String strSQL1 = "UPDATE PRPJREINSITEMPAY SET REALPAYREFNO ='" + strRealPayRefNo
					+ "', PAYREFDATE ='" + strPayRefDate + "' WHERE SETTLENO ='" + strSettleNo + "'";
			
			logger.info(strSQL1);
			
			dbpool.update(strSQL1);
			
			String strSQL2 = "UPDATE PRPJREINSPAYMENT SET REALPAYREFNO ='" + strRealPayRefNo
					+ "', PAYREFDATE ='" + strPayRefDate + "' WHERE SETTLENO ='" + strSettleNo + "'";
			dbpool.update(strSQL2);
			String strVoucherNo = callProcedure(dbpool, strRealPayRefNo);
			BLFjSettle blFjSettle = new BLFjSettle();
			blFjSettle.updateSettle(dbpool,strSettleNo,"1",strPayRefCode,strVoucherNo,"2");
			dbpool.commitTransaction();
			return strVoucherNo;
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
			if (cstmt != null)
				cstmt.close();
			if (conn != null)
				conn.close();
			dbpool.close();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}
