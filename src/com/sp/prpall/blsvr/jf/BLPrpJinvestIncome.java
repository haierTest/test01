package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.account.blsvr.BLAccMainVoucher;
import com.sp.account.blsvr.BLAccSubVoucher;
import com.sp.account.blsvr.BLAccVoucher;
import com.sp.account.schema.AccMainVoucherSchema;
import com.sp.account.schema.AccSubVoucherSchema;
import com.sp.platform.dto.domain.PrpDbankRateDto;
import com.sp.prpall.dbsvr.jf.DBPrpJinvest;
import com.sp.prpall.dbsvr.jf.DBPrpJinvestIncome;
import com.sp.prpall.dbsvr.jf.DBPrpJpayInvest;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFee;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpJinvestIncomeSchema;
import com.sp.prpall.schema.PrpJinvestSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.utiall.blsvr.BLPrpDcompany;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Date;
import com.sp.utility.string.Str;

public class BLPrpJinvestIncome {  

	private Vector schemas = new Vector();

	private BLAccMainVoucher blAccMainVoucher = new BLAccMainVoucher();

	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * 构造函数
	 */
	public BLPrpJinvestIncome() {

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
	 * 增加一条PrpJplanFeeSchema记录
	 * 
	 * @param iPrpJplanFeeSchema
	 *            PrpJplanFeeSchema
	 * @throws Exception
	 */
	public void setArr(PrpJinvestIncomeSchema iPrpJinvestIncomeSchema)
			throws Exception {
		try {
			schemas.add(iPrpJinvestIncomeSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到一条PrpJplanFeeSchema记录
	 * 
	 * @param index
	 *            下标
	 * @return 一个PrpJplanFeeSchema对象
	 * @throws Exception
	 */
	public PrpJinvestIncomeSchema getArr(int index) throws Exception {
		PrpJinvestIncomeSchema prpJinvestIncomeSchema = null;
		try {
			prpJinvestIncomeSchema = (PrpJinvestIncomeSchema) this.schemas
					.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJinvestIncomeSchema;
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
	 * @param dbpool
	 *            全局池
	 * @param iWherePart
	 *            查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart) throws UserException, Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			this.query(dbpool, iWherePart);
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
	}

	/**
	 * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool
	 *            全局池
	 * @param iWherePart
	 *            查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart) throws UserException,
			Exception {
		this.query(dbpool, iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool
	 *            全局池
	 * @param iWherePart
	 *            查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart, int iLimitCount) throws UserException,
			Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			this.query(dbpool, iWherePart, iLimitCount);
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
	}

	/**
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool
	 *            全局池
	 * @param iWherePart
	 *            查询条件(包括排序字句)
	 * @param iLimitCount
	 *            记录数限制(iLimitCount=0: 无限制)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, int iLimitCount)
			throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpJinvestIncome dbPrpJinvestIncome = new DBPrpJinvestIncome();
		if (iLimitCount > 0
				&& dbPrpJinvestIncome.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJinvestIncome.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJinvestIncome WHERE "
					+ iWherePart;
			schemas = dbPrpJinvestIncome.findByConditions(dbpool,
					strSqlStatement);
		}
	}

	/**
	 * @desc 自动计提投资收益，并且生成XXXXX凭证
	 * @author xushaojiang
	 * @param iAccBookType
	 *            帐套类型
	 * @param iAccBookCode
	 *            帐套编码
	 * @param iCenterCode
	 *            核算单位
	 * @param iBranchCode
	 *            基层单位
	 * @param iIncomeDate
	 *            计提时间
	 * @param iOperatorCode
	 *            操作员
	 * @throws UserException
	 * @throws Exception
	 */
	public void transIncomeAll(String iDrawType, String DataType,
			String iAccBookType, String iAccBookCode, String iCenterCode,
			String iBranchCode, String iIncomeDate, String iOperatorCode)
			throws UserException, Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			this.transIncomeAll(dbpool, iDrawType, DataType, iAccBookType,
					iAccBookCode, iCenterCode, iBranchCode, iIncomeDate,
					iOperatorCode);
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
	}

	/**
	 * @desc 自动计提投资收益，并且生成XXXXX凭证
	 * @author xushaojiang
	 * @param iAccBookType
	 *            帐套类型
	 * @param iAccBookCode
	 *            帐套编码
	 * @param iCenterCode
	 *            核算单位
	 * @param iBranchCode
	 *            基层单位
	 * @param iIncomeDate
	 *            计提时间
	 * @param iOperatorCode
	 *            操作员
	 * @throws UserException
	 * @throws Exception
	 */
	public void transIncomeAll(DbPool dbpool, String iDrawType,
			String DataType, String iAccBookType, String iAccBookCode,
			String iCenterCode, String iBranchCode, String iIncomeDate,
			String iOperatorCode) throws UserException, Exception {
		try {
			this.logPrintln("开始计提富安居投资收益，计提日期：" + iIncomeDate);
			BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
			
			String strWherePart = " CenterFlag IN ('1','2') AND ValidStatus='1'";
			strWherePart += Str.convertString("ComCode", iCenterCode, "=");
			strWherePart += " ORDER BY ComCode";
			blPrpDcompany.query(dbpool, strWherePart, 0);
			for (int i = 0; i < blPrpDcompany.getSize(); i++) {
				iCenterCode = blPrpDcompany.getArr(i).getComCode();
				try {
					
					if (DataType.equals("0") || DataType.equals("1")) {
						this.logPrintln("开始计提核算单位:" + iCenterCode
								+ "的2901XXXXX种富安居投资收益");
						BLPrpJinvest blPrpJinvest = new BLPrpJinvest();
						blPrpJinvest.transIncome(dbpool, iAccBookType,
								iAccBookCode, iCenterCode, iBranchCode,
								iIncomeDate, iOperatorCode);
						this.logPrintln("结束计提核算单位:" + iCenterCode
								+ "的2901XXXXX种富安居投资收益");
					}
					
					if (DataType.equals("0") || DataType.equals("2")) {
						this.logPrintln("开始计提核算单位:" + iCenterCode
								+ "的非2901XXXXX种富安居投资收益");
						this.transIncome(dbpool, iDrawType, iAccBookType,
								iAccBookCode, iCenterCode, iBranchCode,
								iIncomeDate, iOperatorCode);
						this.logPrintln("结束计提核算单位:" + iCenterCode
								+ "的非2901XXXXX种富安居投资收益");
					}
				} catch (UserException ue) {
					ue.printStackTrace();
					throw ue;
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
			this.logPrintln("结束计提富安居投资收益，计提日期：" + iIncomeDate);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	/**
	 * @desc 自动计提投资收益,已抽单而计提未结束的情况 add by zhanglingjian 20080616
	 * @author zhanglingjian
	 * @param iAccBookType
	 *            帐套类型
	 * @param iAccBookCode
	 *            帐套编码
	 * @param iCenterCode
	 *            核算单位
	 * @param iBranchCode
	 *            基层单位
	 * @param iIncomeDate
	 *            计提时间
	 * @param iOperatorCode
	 *            操作员
	 * @throws UserException
	 * @throws Exception
	 */
	public void transIncomeLastTime(String iDrawType, String DataType,
			String iAccBookType, String iAccBookCode, String iCenterCode,
			String iBranchCode, String iIncomeDate, String iOperatorCode)
			throws UserException, Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			this.transIncomeLastTime(dbpool, iDrawType, DataType, iAccBookType,
					iAccBookCode, iCenterCode, iBranchCode, iIncomeDate,
					iOperatorCode);
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
	}

	/**
	 * @desc 自动计提投资收益,自动计提投资收益,已抽单而计提未结束的情况 add by zhanglingjian 20080616
	 * @author zhanglingjian
	 * @param iAccBookType
	 *            帐套类型
	 * @param iAccBookCode
	 *            帐套编码
	 * @param iCenterCode
	 *            核算单位
	 * @param iBranchCode
	 *            基层单位
	 * @param iIncomeDate
	 *            计提时间
	 * @param iOperatorCode
	 *            操作员
	 * @throws UserException
	 * @throws Exception
	 */
	public void transIncomeLastTime(DbPool dbpool, String iDrawType,
			String DataType, String iAccBookType, String iAccBookCode,
			String iCenterCode, String iBranchCode, String iIncomeDate,
			String iOperatorCode) throws UserException, Exception {
		try {
			this.logPrintln("开始计提富安居投资收益(已抽单而计提未结束的情况)，计提日期：" + iIncomeDate);
			BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
			
			String strWherePart = " CenterFlag IN ('1','2') AND ValidStatus='1'";
			strWherePart += Str.convertString("ComCode", iCenterCode, "=");
			strWherePart += " ORDER BY ComCode";
			blPrpDcompany.query(dbpool, strWherePart, 0);
			for (int i = 0; i < blPrpDcompany.getSize(); i++) {
				iCenterCode = blPrpDcompany.getArr(i).getComCode();
				try {
					
					if (DataType.equals("0") || DataType.equals("2")) {
						this.logPrintln("开始计提核算单位:" + iCenterCode
								+ "的非2901XXXXX种富安居投资收益(已抽单而计提未结束的情况)");
						this.drawIncomeDataLastTime(dbpool, iCenterCode,
								iIncomeDate, iOperatorCode);
						this.logPrintln("结束计提核算单位:" + iCenterCode
								+ "的非2901XXXXX种富安居投资收益(已抽单而计提未结束的情况)");
					}
				} catch (UserException ue) {
					ue.printStackTrace();
					throw ue;
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
			this.logPrintln("结束计提富安居投资收益，计提日期：" + iIncomeDate);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	/**
	 * @desc 自动计提投资收益(浮动收益率),(已抽单而计提未结束的情况) add by zhanglingjian 20080616
	 * @author zhanglingjian
	 * @param dbpool
	 * @param iCenterCode
	 *            核算单位
	 * @param iIncomeDate
	 *            计提时间
	 * @throws UserException
	 * @throws Exception
	 */
	public void drawIncomeDataLastTime(DbPool dbpool, String iCenterCode,
			String iIncomeDate, String iOperatorCode) throws UserException,
			Exception {
		
		StringBuffer comBuffer = new StringBuffer();
		String strComcodeWhere = "";
		String incomeStartDate = "";
		String incomeEndDate = "";
		int succeeCount = 0;
		BLPrpDcompany iBLPrpDcompany = new BLPrpDcompany();
		String[] arrComCode = iBLPrpDcompany.getLowerComCodeNew(dbpool,
				iCenterCode);
		comBuffer.append("('" + iCenterCode + "'");
	    
        for (int j = 0; j < arrComCode.length; j++) {
            if (j == 0)
                comBuffer.append(","); 
            if (j == (arrComCode.length - 1))
                comBuffer.append("'" + arrComCode[j] + "'"); 
            else if ((j % 900) == 0 && j > 0)
                comBuffer.append("'" + arrComCode[j] + "' ) OR comcode IN ( "); 
            else
                comBuffer.append("'" + arrComCode[j] + "', ");
        }
        comBuffer.append(")");
        
		strComcodeWhere = comBuffer.toString();
		BLPrpJinvest iBLPrpJinvest = new BLPrpJinvest();
		BLPrpJpayInvest blPrpJpayInvest = new BLPrpJpayInvest();
		DBPrpJpayInvest dbPrpJpayInvest = new DBPrpJpayInvest();
		DBPrpJinvestIncome dbPrpJinvestIncome = new DBPrpJinvestIncome();
		PrpJinvestIncomeSchema prpJinvestIncomeSchema = null;
		DBPrpJinvest dbPrpJinvest = null;
		
		StringBuffer conditionBuffer = new StringBuffer();
		String strCondition = "";
		conditionBuffer.append(" certitype='P' AND riskcode !='2901'");
		conditionBuffer.append(" AND incomeflag='0' ");
		conditionBuffer.append(" AND payflag='00' ");
		
		conditionBuffer.append(" AND enddate<=date'" + iIncomeDate + "'+1 ");
		conditionBuffer.append(" AND comcode in " + strComcodeWhere);
		strCondition = conditionBuffer.toString();
		blPrpJpayInvest.query(dbpool, strCondition, 0);
		this
				.logPrintln("满足条件的PrpJpayInvest表数据条数是："
						+ blPrpJpayInvest.getSize());
		for (int i = 0; i < blPrpJpayInvest.getSize(); i++) {
			try {
				dbpool.beginTransaction();
				succeeCount++;
				prpJinvestIncomeSchema = new PrpJinvestIncomeSchema();
				dbPrpJinvest = new DBPrpJinvest();
				dbPrpJinvest.getInfo(dbpool, blPrpJpayInvest.getArr(i)
						.getCertiNo(), "0");
				prpJinvestIncomeSchema = this.evaluate(dbPrpJinvest);
				double sumIncomeFee = 0;
				
				String yearCount = "";
				String Income_Year_Count_RiskCode = "INCOME_YEAR_COUNT_"
						+ dbPrpJinvest.getRiskCode();
				yearCount = SysConst.getProperty(Income_Year_Count_RiskCode);
				incomeStartDate = dbPrpJinvest.getStartDate();
				incomeEndDate = dbPrpJinvest.getEndDate();
				sumIncomeFee = this.drawPolicyIncome(dbpool, yearCount,
						dbPrpJinvest.getCertiType(), dbPrpJinvest.getCertiNo(),
						incomeStartDate, incomeEndDate, Double
								.parseDouble(dbPrpJinvest.getLeftInvestMent()));
				if (dbPrpJinvest.getIncometimes() != null
						&& !dbPrpJinvest.getIncometimes().equals("0")
						&& dbPrpJinvest.getIncomeDate() != null) {
					
					dbPrpJinvest.setIncometimes((Integer.parseInt(dbPrpJinvest
							.getIncometimes()) + 2)
							+ "");
				} else {
					dbPrpJinvest.setIncometimes("2");
				}
				
				String hasIncomeDate = dbPrpJinvest.getIncomeDate();
				
				
				if (hasIncomeDate == null || hasIncomeDate.equals("")) {
					DateTime dateTime = new DateTime(dbPrpJinvest
							.getStartDate());
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					Calendar c = Calendar.getInstance();
					c.setTimeInMillis(dateTime.getTime() - 1 * 24 * 60 * 60
							* 1000);
					hasIncomeDate = df.format(c.getTime());
				}
				
				
				dbPrpJinvest.setIncomeDate(incomeEndDate);
				
				dbPrpJinvest.setIncomeFlag("1");
				
				String hasIncomeFee = "0";
				if (dbPrpJinvest.getSumIncomeFee() != null
						&& !dbPrpJinvest.getSumIncomeFee().equals("")) {
					hasIncomeFee = dbPrpJinvest.getSumIncomeFee();
				}
				dbPrpJinvest.setSumIncomeFee("" + sumIncomeFee);
				dbPrpJinvest.update(dbpool);

				
				dbPrpJinvestIncome.setSchema(prpJinvestIncomeSchema);
				dbPrpJinvestIncome.setIncomeFee("" + sumIncomeFee);
				dbPrpJinvestIncome.setExchangeRate("1");
				dbPrpJinvestIncome.setPayRefReason("R02");
				dbPrpJinvestIncome.setIncomeFeeCny("0");
				dbPrpJinvestIncome.setIncomeStartDate(incomeStartDate);
				dbPrpJinvestIncome.setIncomeEndDate(incomeEndDate);
				dbPrpJinvestIncome
						.setIncometimes(dbPrpJinvest.getIncometimes());
				dbPrpJinvestIncome.setCenterCode(dbPrpJinvest.getCenterCode());
				dbPrpJinvestIncome.setVoucherNo("0");
				dbPrpJinvestIncome.setCurrency2(prpJinvestIncomeSchema
						.getCurrency1());
				dbPrpJinvestIncome.setOperatorCode(iOperatorCode);
				dbPrpJinvestIncome.setOperatorDate(iIncomeDate);
				dbPrpJinvestIncome.insert(dbpool);
				
				dbPrpJinvestIncome = new DBPrpJinvestIncome();
				dbPrpJinvestIncome.setSchema(prpJinvestIncomeSchema);
				dbPrpJinvestIncome.setIncomeFee(""
						+ (-Double.parseDouble(hasIncomeFee)));
				dbPrpJinvestIncome.setExchangeRate("1");
				dbPrpJinvestIncome.setPayRefReason("R02");
				dbPrpJinvestIncome.setIncomeFeeCny("0");
				dbPrpJinvestIncome.setIncomeStartDate(incomeStartDate);
				dbPrpJinvestIncome.setIncomeEndDate(hasIncomeDate);
				dbPrpJinvestIncome
						.setIncometimes(""
								+ (Integer.parseInt(dbPrpJinvest
										.getIncometimes()) - 1));
				dbPrpJinvestIncome.setCenterCode(dbPrpJinvest.getCenterCode());
				dbPrpJinvestIncome.setVoucherNo("0");
				dbPrpJinvestIncome.setCurrency2(prpJinvestIncomeSchema
						.getCurrency1());
				dbPrpJinvestIncome.setOperatorCode(iOperatorCode);
				dbPrpJinvestIncome.setOperatorDate(iIncomeDate);
				dbPrpJinvestIncome.insert(dbpool);

				
				dbPrpJpayInvest.setSchema(blPrpJpayInvest.getArr(i));
				dbPrpJpayInvest.setSumIncomeFee("" + sumIncomeFee);
				dbPrpJpayInvest.setIncometimes(dbPrpJinvest.getIncometimes());
				dbPrpJpayInvest.setIncomeFlag("1");
				dbPrpJpayInvest.setPayFlag("01");
				dbPrpJpayInvest
						.setPayRefFee(""
								+ (Double.parseDouble(dbPrpJpayInvest
										.getPlanFee()) + sumIncomeFee));
				dbPrpJpayInvest.update(dbpool);

				dbpool.commitTransaction();
			} catch (Exception e) {
				dbpool.rollbackTransaction();
				this.logPrintln("计提XX:"
						+ blPrpJpayInvest.getArr(i).getPolicyNo() + " 截至"
						+ iIncomeDate + "的投资收益出错 " + e.toString());
				succeeCount--;
				e.printStackTrace();
			}
		}
		this.logPrintln("满足条件的PrpJpayInvest表数据条数是：" + blPrpJpayInvest.getSize()
				+ " 计提成功的条数是： " + succeeCount);
	}

	/**
	 * @desc 自动计提投资收益，并且生成XXXXX凭证
	 * @author xushaojiang
	 * @param iAccBookType
	 *            帐套类型
	 * @param iAccBookCode
	 *            帐套编码
	 * @param iCenterCode
	 *            核算单位
	 * @param iBranchCode
	 *            基层单位
	 * @param iIncomeDate
	 *            计提时间
	 * @param iOperatorCode
	 *            操作员
	 * @throws UserException
	 * @throws Exception
	 */
	public void transIncome(String iDrawType, String iAccBookType,
			String iAccBookCode, String iCenterCode, String iBranchCode,
			String iIncomeDate, String iOperatorCode) throws UserException,
			Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			this.transIncome(dbpool, iDrawType, iAccBookType, iAccBookCode,
					iCenterCode, iBranchCode, iIncomeDate, iOperatorCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dbpool.close();
		}
	}

	/**
	 * @desc 自动计提投资收益，并且生成XXXXX凭证
	 * @author xushaojiang
	 * @param iAccBookType
	 *            帐套类型
	 * @param iAccBookCode
	 *            帐套编码
	 * @param iCenterCode
	 *            核算单位
	 * @param iBranchCode
	 *            基层单位
	 * @param iIncomeDate
	 *            计提时间
	 * @param iOperatorCode
	 *            操作员
	 * @throws UserException
	 * @throws Exception
	 */
	public void transIncome(DbPool dbpool, String iDrawType,
			String iAccBookType, String iAccBookCode, String iCenterCode,
			String iBranchCode, String iIncomeDate, String iOperatorCode)
			throws UserException, Exception {
		try {
			
			if (iDrawType.equals("0") || iDrawType.equals("1")) {
				this.logPrintln("开始计提非2901XXXXX种投资收益，送投资收益表数据");
				try {
					this.drawIncomeData(dbpool, iCenterCode, iIncomeDate,
							iOperatorCode);
					this.logPrintln("结束计提非2901XXXXX种投资收益，送投资收益表数据");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
		}
	}

	/**
	 * @desc 自动计提投资收益(浮动收益率)
	 * @author xushaojiang
	 * @param dbpool
	 * @param iCenterCode
	 *            核算单位
	 * @param iIncomeDate
	 *            计提时间
	 * @throws UserException
	 * @throws Exception
	 */
	public void drawIncomeData(DbPool dbpool, String iCenterCode,
			String iIncomeDate,String iOperatorCode) throws UserException, Exception {
		
		String incomeStartDate = "";
		String strincomeStartDate = "";
		int succeeCount =0;
		
		/**
		 * StringBuffer comBuffer = new StringBuffer(); String strComcodeWhere =
		 * ""; BLPrpDcompany iBLPrpDcompany = new BLPrpDcompany(); String[]
		 * arrComCode = iBLPrpDcompany.getLowerComCodeNew(dbpool,iCenterCode);
		 * comBuffer.append("('" + iCenterCode + "'"); for (int j = 0; j <
		 * arrComCode.length; j++) { comBuffer.append(",'" + arrComCode[j] +
		 * "'"); } comBuffer.append(")"); strComcodeWhere =
		 * comBuffer.toString();
		 */
		
		BLPrpJinvest iBLPrpJinvest = new BLPrpJinvest();
		DBPrpJinvestIncome dbPrpJinvestIncome = new DBPrpJinvestIncome();
		PrpJinvestSchema prpJinvestSchema = null;
		PrpJinvestIncomeSchema prpJinvestIncomeSchema = null;
		DBPrpJinvest dbPrpJinvest = null;
		
		StringBuffer conditionBuffer = new StringBuffer();
		String strCondition = "";
		conditionBuffer.append(" certitype IN ('P','E')                                                   ");
		conditionBuffer.append(" AND riskcode !='2901'                                                    ");		
		conditionBuffer.append(" AND (incomedate IS NULL OR incomedate < '"	+ iIncomeDate + "')           ");
		conditionBuffer.append(" AND decode(certitype,'E',validdate,startdate) <= '"+ iIncomeDate + "'    ");
		conditionBuffer.append(" AND payrefreason = 'R01'                                                 ");
		conditionBuffer.append(" AND flag='E'                                                             ");
		conditionBuffer.append(" AND incomeflag = 0                                                       ");
		conditionBuffer.append(" AND realpayreffee > 0                                                    ");
		
		conditionBuffer.append(" AND payrefdate <= '" + iIncomeDate+ "'                                   ");
		
		conditionBuffer.append(" AND PayMentFlag='00' ");
		
		
		conditionBuffer.append(" AND CenterCode = " + iCenterCode	+ "                                   ");
		strCondition = conditionBuffer.toString();
		DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
    	String OperateSequence = dbPrpJplanFee.getSequence(dbpool);
		String strUpdate = "update prpjinvest set OPERATESEQUENCE ='"+OperateSequence+"'  where "+strCondition;
		int intCount = dbpool.executeUpdate(strUpdate);

		this.logPrintln("满足条件的prpjinvest表数据条数是："+intCount);
		for (int i = 0; i <= intCount/100; i++) {

            	String strWhere= "OPERATESEQUENCE ='"+OperateSequence+"' AND PayMentFlag='00' And (incomedate <'"+ iIncomeDate + "' or incomedate is null)  And rownum <=100 ";
            	iBLPrpJinvest.query(dbpool, strWhere, 0);
            	for (int j=0;j<iBLPrpJinvest.getSize();j++){
        			try{
        				dbpool.beginTransaction();
        				succeeCount++;
        				prpJinvestSchema = iBLPrpJinvest.getArr(j);
        				prpJinvestIncomeSchema = new PrpJinvestIncomeSchema();
        				dbPrpJinvest = new DBPrpJinvest();
        				dbPrpJinvest.setSchema(prpJinvestSchema);			
        				prpJinvestIncomeSchema=this.evaluate(prpJinvestSchema);
        				dbPrpJinvestIncome.setSchema(prpJinvestIncomeSchema);
        				double incomeFee=0;
        				double sumIncomeFee = 0;
        		        String strIncomeFlag = "0";   
        				
        				String yearCount ="";
        				String  Income_Year_Count_RiskCode ="INCOME_YEAR_COUNT_"+prpJinvestSchema.getRiskCode();
        			    yearCount=SysConst.getProperty(Income_Year_Count_RiskCode);
        				if (prpJinvestSchema.getIncometimes() != null && !prpJinvestSchema.getIncometimes().equals("0")
        			            && prpJinvestSchema.getIncomeDate() != null){
        					  
        			        if(PubTools.compareDate(iIncomeDate, dbPrpJinvest.getEndDate()) >= 0){
        			        	incomeStartDate = prpJinvestSchema.getStartDate();
        			        	strincomeStartDate = DateTime.getOffsetDate(new DateTime(prpJinvestSchema.getIncomeDate()), 1).toString();
        			        	iIncomeDate = dbPrpJinvest.getEndDate();
        			        	strIncomeFlag = "1";
        						incomeFee = this.drawPolicyIncome(dbpool,yearCount,prpJinvestSchema.getCertiType(),prpJinvestSchema.getCertiNo(),incomeStartDate,iIncomeDate,
        								Double.parseDouble(prpJinvestSchema.getLeftInvestMent()));
        						incomeFee= incomeFee-Double.parseDouble(dbPrpJinvest.getSumIncomeFee());
        						incomeStartDate=strincomeStartDate;
        			        }else{
        						  
        						incomeStartDate = DateTime.getOffsetDate(new DateTime(prpJinvestSchema.getIncomeDate()), 1).toString();
        						incomeFee = this.drawPolicyIncome(dbpool,yearCount,prpJinvestSchema.getCertiType(),prpJinvestSchema.getCertiNo(),incomeStartDate,iIncomeDate,
        								Double.parseDouble(prpJinvestSchema.getLeftInvestMent()));
        			        }
        				}else{
        			          
        			        	incomeStartDate=prpJinvestSchema.getStartDate();
        						incomeFee = this.drawPolicyIncome(dbpool,yearCount,prpJinvestSchema.getCertiType(),prpJinvestSchema.getCertiNo(),incomeStartDate,iIncomeDate,
        								Double.parseDouble(prpJinvestSchema.getLeftInvestMent()));
        			    }
        				 
        			    dbPrpJinvest.setIncometimes((Integer.parseInt(dbPrpJinvest.getIncometimes())+1)+"");
        				 
        			    dbPrpJinvest.setIncomeDate(iIncomeDate);
        				 
        			    dbPrpJinvest.setIncomeFlag(strIncomeFlag);
        			    
        			    
        			    






        			    
        				dbPrpJinvestIncome.setIncomeFee(incomeFee+"");
        				dbPrpJinvestIncome.setExchangeRate("1");
        				dbPrpJinvestIncome.setPayRefReason("R02");
        				dbPrpJinvestIncome.setIncomeFeeCny("0");	
        				dbPrpJinvestIncome.setIncomeStartDate(incomeStartDate);
        				dbPrpJinvestIncome.setIncomeEndDate(iIncomeDate);
        				dbPrpJinvestIncome.setIncometimes((Integer.parseInt(prpJinvestIncomeSchema.getIncometimes())+1)+"");
        				dbPrpJinvestIncome.setCenterCode(prpJinvestSchema.getCenterCode());
        				dbPrpJinvestIncome.setBranchCode(prpJinvestSchema.getBranchCode());
        				dbPrpJinvestIncome.setVoucherNo("0");
        				dbPrpJinvestIncome.setCurrency2(prpJinvestIncomeSchema.getCurrency1());
        				dbPrpJinvestIncome.setOperatorCode(iOperatorCode);
        				dbPrpJinvestIncome.setOperatorDate(iIncomeDate);
        				dbPrpJinvestIncome.insert(dbpool);
        		        if(dbPrpJinvest.getSumIncomeFee()==null||dbPrpJinvest.getSumIncomeFee().equals("")){
        		        	sumIncomeFee=incomeFee;	        	
        		        }else{
        		        	sumIncomeFee=Str.round(Double.parseDouble(dbPrpJinvest.getSumIncomeFee())+incomeFee,2);
        		        }
        		        dbPrpJinvest.setSumIncomeFee(""+sumIncomeFee);
        			    dbPrpJinvest.update(dbpool);
        			    dbpool.commitTransaction();
        			}catch (Exception e) {
        				dbpool.rollbackTransaction();
        				this.logPrintln("计提XX:"+iBLPrpJinvest.getArr(j).getPolicyNo()+" 截至"+iIncomeDate+"的投资收益出错 "+e.toString());
        				succeeCount--;
        				e.printStackTrace();
        			} 
            	}
        		
		}
		this.logPrintln("满足条件的prpjinvest表数据条数是："+intCount+" 计提成功的条数是： "+succeeCount);
	}

	/**
	 * @desc 退XXXXX的投资收益处理
	 * @author xushaojiang
	 * @param dbpool
	 * @param iCenterCode
	 *            核算单位
	 * @param iIncomeDate
	 *            计提时间
	 * @throws UserException
	 * @throws Exception
	 */
	public BLPrpJinvestIncome drawIncomeData(DbPool dbpool,
			DBPrpJinvest iDBPrpJinvest) throws UserException, Exception {
		BLPrpJinvestIncome iBLPrpJinvestIncome = new BLPrpJinvestIncome();
		PrpJinvestIncomeSchema iPrpJinvestIncomeSchemaJT = null;
		PrpJinvestIncomeSchema iPrpJinvestIncomeSchemaFC = null;
		String strOperatorDate = "";
		String strCenterCode = "";
		BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
		strCenterCode = blPrpDcompany.getCenterCodeNew(dbpool, iDBPrpJinvest
				.getComCode());
		if (strCenterCode.equals("") || strCenterCode.trim().length() == 0) {
			throw new UserException(-98, -1167,
					"BLPrpJinvestIncome.drawIncomeData()", "核算单位取值失败！");
		}
		com.sp.utility.string.Date sinosoftDate = new com.sp.utility.string.Date();
		strOperatorDate = new ChgDate().toFormat(sinosoftDate
				.getString(sinosoftDate.YEAR + sinosoftDate.MONTH
						+ sinosoftDate.DATE));
		
		iPrpJinvestIncomeSchemaJT = this.evaluate(iDBPrpJinvest);
		iPrpJinvestIncomeSchemaJT.setPayRefReason("R02");
		iPrpJinvestIncomeSchemaJT.setCenterCode(strCenterCode);
		iPrpJinvestIncomeSchemaJT.setBranchCode(strCenterCode);
		iPrpJinvestIncomeSchemaJT.setIncomeStartDate(iDBPrpJinvest
				.getStartDate());
		iPrpJinvestIncomeSchemaJT.setIncomeEndDate(iDBPrpJinvest.getEndDate());
		iPrpJinvestIncomeSchemaJT.setIncomeFee(iDBPrpJinvest.getSumIncomeFee());
		iPrpJinvestIncomeSchemaJT.setCurrency2(iDBPrpJinvest.getCurrency1());
		iPrpJinvestIncomeSchemaJT.setExchangeRate("1");
		iPrpJinvestIncomeSchemaJT.setIncomeFeeCny("0");
		iPrpJinvestIncomeSchemaJT.setOperatorCode(iDBPrpJinvest
				.getHandlerCode());
		iPrpJinvestIncomeSchemaJT.setOperatorDate(strOperatorDate);
		iBLPrpJinvestIncome.setArr(iPrpJinvestIncomeSchemaJT);
		
		if (Integer.parseInt(iDBPrpJinvest.getIncometimes()) > 1) {
			if (iDBPrpJinvest.getIncomeFee() == null
					|| iDBPrpJinvest.getIncomeFee().equals(""))
				iDBPrpJinvest.setIncomeFee("0");
			double dbhasincomeFee = 0;
			dbhasincomeFee = -1
					* Double.parseDouble(iDBPrpJinvest.getIncomeFee());
			iPrpJinvestIncomeSchemaFC = this.evaluate(iDBPrpJinvest);
			iPrpJinvestIncomeSchemaFC.setPayRefReason("R03");
			iPrpJinvestIncomeSchemaFC.setCenterCode(strCenterCode);
			iPrpJinvestIncomeSchemaFC.setBranchCode(strCenterCode);
			iPrpJinvestIncomeSchemaFC.setIncomeStartDate(iDBPrpJinvest
					.getStartDate());
			iPrpJinvestIncomeSchemaFC.setIncomeEndDate(iDBPrpJinvest
					.getEndDate());
			iPrpJinvestIncomeSchemaFC.setIncomeFee(dbhasincomeFee + "");
			iPrpJinvestIncomeSchemaFC.setIncometimes((Integer
					.parseInt(iPrpJinvestIncomeSchemaFC.getIncometimes()) - 1)
					+ "");
			iPrpJinvestIncomeSchemaFC
					.setCurrency2(iDBPrpJinvest.getCurrency1());
			iPrpJinvestIncomeSchemaFC.setExchangeRate("1");
			iPrpJinvestIncomeSchemaFC.setIncomeFeeCny("0");
			iPrpJinvestIncomeSchemaFC.setOperatorCode(iDBPrpJinvest
					.getHandlerCode());
			iPrpJinvestIncomeSchemaFC.setOperatorDate(strOperatorDate);
			iBLPrpJinvestIncome.setArr(iPrpJinvestIncomeSchemaFC);
		}
		return iBLPrpJinvestIncome;
	}

	/**
	 * @desc 自动计提投资收益(浮动收益率) 增加剩余金额参数 mofify by zhanglingjian 20080613
	 * @author xushaojiang
	 * @param iYearCount
	 *            产品的期限 1：一年期 2：两年期
	 * @param iCertiType
	 *            业务类型
	 * @param iCertiNo
	 *            业务号
	 * @param iIncomeStartDate
	 *            计提收益开始日期
	 * @param iIncomeEndDate
	 *            计提收益结束日期
	 * @throws UserException
	 * @throws Exception
	 */
	public double drawPolicyIncome(String iYearCount, String iCertiType,
			String iCertiNo, String iIncomeStartDate, String iIncomeEndDate,
			double leftInvestMent) throws UserException, Exception {
		DbPool dbpool = new DbPool();
		double incomeFee = 0;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			incomeFee = this.drawPolicyIncome(dbpool, iYearCount, iCertiType,
					iCertiNo, iIncomeStartDate, iIncomeEndDate, leftInvestMent);
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
		return incomeFee;
	}

	/**
	 * @desc 自动计提投资收益(浮动收益率)
	 * @author xushaojiang
	 * @param dbpool
	 * @param iYearCount
	 *            产品的期限 1：一年期 2：两年期
	 * @param iCertiType
	 *            业务类型
	 * @param iCertiNo
	 *            业务号
	 * @param iIncomeStartDate
	 *            计提收益开始日期
	 * @param iIncomeEndDate
	 *            计提收益结束日期
	 * @throws UserException
	 * @throws Exception
	 */
	public double drawPolicyIncome(DbPool dbpool, String iYearCount,
			String iCertiType, String iCertiNo, String iIncomeStartDate,
			String iIncomeEndDate, double leftInvestMent) throws UserException,
			Exception {
		BLPrpJinvest iBLPrpJinvest = new BLPrpJinvest();
		PrpJinvestSchema iPrpJinvestSchema = null;
		double incomeFee = 0;
		
		StringBuffer conditionBuffer = new StringBuffer();
		StringBuffer incomeRateBuffer = new StringBuffer();
		String strCondition = "";
		String strIncomeRateSql = "";
		conditionBuffer
				.append(" certitype ='P'                                             ");
		conditionBuffer.append(" AND CertiNo ='" + iCertiNo
				+ "'                                             ");
		conditionBuffer
				.append(" AND payrefreason = 'R01'                                                ");
		conditionBuffer
				.append(" AND flag='E'                                                            ");

		
		strCondition = conditionBuffer.toString();
		iBLPrpJinvest.query(dbpool, strCondition, 0);
		if (iBLPrpJinvest.getSize() == 0) {
			return incomeFee;
		}

		
		if (iCertiType != null && iCertiType.equals("E")) {
			
			iBLPrpJinvest.getArr(0).setLeftInvestMent("" + leftInvestMent);
		}
		
		

		for (int i = 0; i < iBLPrpJinvest.getSize(); i++) {
			iPrpJinvestSchema = iBLPrpJinvest.getArr(i);
			if (PubTools.compareDate(iPrpJinvestSchema.getStartDate(),
					iIncomeStartDate) > 0) {
				throw new UserException(-98, -1167,
						"BLPrpJinvestIncome.drawPolicyIncome", "XX：" + iCertiNo
								+ " 计提开始日期：" + iIncomeStartDate + "不能小于XX起XXXXX日期："
								+ iPrpJinvestSchema.getStartDate());
			}
			if (PubTools.compareDate(iPrpJinvestSchema.getEndDate(),
					iIncomeEndDate) < 0) {
				throw new UserException(-98, -1167,
						"BLPrpJinvestIncome.drawPolicyIncome", "XX：" + iCertiNo
								+ " 计提截至日期：" + iIncomeEndDate + "不能大于XX截至日期："
								+ iPrpJinvestSchema.getEndDate());
			}
			
			incomeRateBuffer
					.append(" (select a.validdate,a.bankrate from                  ");
			incomeRateBuffer
					.append(" (select bankrate,validdate from PrpdBankRate         ");
			incomeRateBuffer.append(" where validdate <='" + iIncomeStartDate
					+ "'             ");
			incomeRateBuffer.append(" And SavePeriod ='" + iYearCount
					+ "'                     ");
			incomeRateBuffer
					.append(" order by validdate desc ) a                          ");
			incomeRateBuffer
					.append(" where rownum='1')                                    ");
			incomeRateBuffer
					.append(" union                                                ");
			incomeRateBuffer
					.append(" (select validdate,bankrate from  PrpdBankRate        ");
			incomeRateBuffer.append(" where validdate>'" + iIncomeStartDate
					+ "'               ");
			incomeRateBuffer.append(" and validdate <='" + iIncomeEndDate
					+ "'                  ");
			incomeRateBuffer.append(" And SavePeriod ='" + iYearCount
					+ "')                    ");
			strIncomeRateSql = incomeRateBuffer.toString();
			ResultSet rs = null;
			rs = dbpool.query(strIncomeRateSql);
			PrpDbankRateDto prpDbankRateDto = null;
			ArrayList list = new ArrayList();
			while (rs.next()) {
				prpDbankRateDto = new PrpDbankRateDto();
				prpDbankRateDto.setValidDate(new DateTime(rs
						.getDate("validdate")));
				prpDbankRateDto.setBankRate(rs.getDouble("bankrate"));
				list.add(prpDbankRateDto);
			}
			
			for (int j = 0; j < list.size(); j++) {
				PrpDbankRateDto PrpDbankRateDto = null;
				String incomeStartDate = "";
				String incomeEndDate = "";
				double incomeRate = 0;
				
				
				if (j == 0) {
					if (list.size() == 1) {
						incomeStartDate = iIncomeStartDate;
						incomeEndDate = iIncomeEndDate;
						PrpDbankRateDto = (PrpDbankRateDto) list.get(j);
						incomeRate = PrpDbankRateDto.getBankRate() / 100;
					} else {
						PrpDbankRateDto = (PrpDbankRateDto) list.get(j);
						incomeRate = PrpDbankRateDto.getBankRate() / 100;
						incomeStartDate = iIncomeStartDate;
						PrpDbankRateDto = (PrpDbankRateDto) list.get(j + 1);
						incomeEndDate = DateTime.getOffsetDate(
								PrpDbankRateDto.getValidDate(), -1).toString();
					}
					
					
				} else if (j == list.size() - 1) {
					PrpDbankRateDto = (PrpDbankRateDto) list.get(j);
					incomeStartDate = PrpDbankRateDto.getValidDate().toString();
					incomeRate = PrpDbankRateDto.getBankRate() / 100;
					incomeEndDate = iIncomeEndDate;
					
					
				} else {
					PrpDbankRateDto = (PrpDbankRateDto) list.get(j);
					incomeStartDate = PrpDbankRateDto.getValidDate().toString();
					incomeRate = PrpDbankRateDto.getBankRate() / 100;
					PrpDbankRateDto = (PrpDbankRateDto) list.get(j + 1);
					incomeEndDate = DateTime.getOffsetDate(
							PrpDbankRateDto.getValidDate(), -1).toString();
				}
				Date startDate = new Date(incomeStartDate);
				Date endDate = new Date(incomeEndDate);
				String incomeStartYear = incomeStartDate.substring(0, 4);
				String incomeEndYear = incomeEndDate.substring(0, 4);
				int cludeYearCount = Integer.parseInt(incomeEndYear)
						- Integer.parseInt(incomeStartYear) + 1;
				String incomeStartEndDate = incomeStartYear + "-12-31";
				int iPeriodSumIncomeDate = 0; 
				double yearDateCount = 0;
				if (PubTools.compareDate(incomeStartDate, incomeStartEndDate) <= 0
						&& PubTools.compareDate(incomeEndDate,
								incomeStartEndDate) > 0) {
					for (int k = 0; k < cludeYearCount; k++) {
						if (cludeYearCount > 2) {
							if (k == 0) {
								endDate = new Date(incomeStartEndDate);
							} else if (k == cludeYearCount - 1) {
								startDate = new Date((Integer
										.parseInt(incomeStartYear) + k)
										+ "-01-01");
								endDate = new Date(incomeEndDate);
							} else {
								startDate = new Date((Integer
										.parseInt(incomeStartYear) + k)
										+ "-01-01");
								endDate = new Date((Integer
										.parseInt(incomeStartYear) + k)
										+ "-12-31");
							}
						} else {
							if (k == 0) {
								endDate = new Date(incomeStartEndDate);
							} else {
								startDate = new Date((Integer
										.parseInt(incomeStartYear) + k)
										+ "-01-01");
								endDate = new Date(incomeEndDate);
							}
						}
						yearDateCount = this.getYearDate(startDate.toString());
						iPeriodSumIncomeDate = PubTools.getDayMinus(startDate,
								0, endDate, 24);
						
						incomeFee += Str
								.round(
										(iPeriodSumIncomeDate
												* Double
														.parseDouble(ChgData
																.chgStrZero(iPrpJinvestSchema
																		.getLeftInvestMent())) * incomeRate)
												/ yearDateCount, 2);
						
					}
				} else {
					yearDateCount = this.getYearDate(incomeStartDate);
					iPeriodSumIncomeDate = PubTools.getDayMinus(startDate, 0,
							endDate, 24);
					
					incomeFee += Str
							.round(
									(iPeriodSumIncomeDate
											* Double
													.parseDouble(ChgData
															.chgStrZero(iPrpJinvestSchema
																	.getLeftInvestMent())) * incomeRate)
											/ yearDateCount, 2);
					
				}
			}
		}
		return incomeFee;
	}

	/**
	 * @desc 投资收益制证
	 * @author 徐少将
	 * @param dbpool
	 * @param iAccBookType
	 *            帐套类型
	 * @param iAccBookCode
	 *            帐套编码
	 * @param iCenterCode
	 *            核算单位
	 * @param iBranchCode
	 *            基层单位
	 * @param iIncomeDate
	 *            计提时间
	 * @param iOperatorCode
	 *            操作员
	 * @throws UserException
	 * @throws Exception
	 */
	public void voucherIncomeData(DbPool dbpool, String iAccBookType,
			String iAccBookCode, String iCenterCode, String iBranchCode,
			String iIncomeDate, String iOperatorCode) throws UserException,
			Exception {
		
		StringBuffer conditionBuffer = new StringBuffer();
		BLPrpJinvestIncome blPrpJinvestIncome = new BLPrpJinvestIncome();
		DBPrpJinvestIncome dbPrpJinvestIncome = new DBPrpJinvestIncome();
		conditionBuffer.append(" CenterCode = '" + iCenterCode
				+ "'            ");
		conditionBuffer.append(" And RiskCode != '2901'                    ");
		conditionBuffer.append(" And VoucherNo = '0'                       ");
		conditionBuffer.append(" And IncomeEndDate <= '" + iIncomeDate
				+ "'    ");
		blPrpJinvestIncome.query(dbpool, conditionBuffer.toString(), 0);
		
		BLAccVoucher blAccVoucher = new BLAccVoucher();
		
		AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
		accMainVoucherSchema.setAccBookType(iAccBookType);
		accMainVoucherSchema.setAccBookCode(iAccBookCode);
		accMainVoucherSchema.setCenterCode(iCenterCode);
		accMainVoucherSchema.setBranchCode(iBranchCode);
		accMainVoucherSchema.setVoucherType("1");
		accMainVoucherSchema.setGenerateWay("6");
		accMainVoucherSchema.setVoucherDate(iIncomeDate);
		accMainVoucherSchema.setOperatorBranch(iBranchCode);
		accMainVoucherSchema.setOperatorCode(iOperatorCode);
		accMainVoucherSchema.setVoucherFlag("1");
		
		String strYearMonth = iIncomeDate.substring(0, 4)
				+ iIncomeDate.substring(5, 7);
		accMainVoucherSchema.setAuxNumber("0");
		accMainVoucherSchema.setYearMonth(strYearMonth);
		accMainVoucherSchema.setVoucherNo("");
		
		blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);
		
		String strRemark = "自动计提" + iIncomeDate + "投联XXXXX投资收益";
		AccSubVoucherSchema accSubVoucherSchema = null;
		for (int j = 0; j < blPrpJinvestIncome.getSize(); j++) {
			double dblExchRate = 1;
			double dbIncomeFeeCny = 0;
			dblExchRate = PubTools.getExchangeRate(dbpool, blPrpJinvestIncome
					.getArr(j).getCurrency1(), "CNY", accMainVoucherSchema
					.getVoucherDate());
			blPrpJinvestIncome.getArr(j).setExchangeRate("" + dblExchRate);
			dbIncomeFeeCny = Double.parseDouble(blPrpJinvestIncome.getArr(j)
					.getIncomeFee())
					* dblExchRate;
			dbIncomeFeeCny = Str.round(Str.round(dbIncomeFeeCny, 8), 2);
			blPrpJinvestIncome.getArr(j).setIncomeFeeCny("" + dbIncomeFeeCny);
			for (int k = 0; k < 2; k++) {
				accSubVoucherSchema = new AccSubVoucherSchema();
				accSubVoucherSchema.setAccBookType(accMainVoucherSchema
						.getAccBookType());
				accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
						.getAccBookCode());
				accSubVoucherSchema.setCenterCode(accMainVoucherSchema
						.getCenterCode());
				accSubVoucherSchema.setYearMonth(accMainVoucherSchema
						.getYearMonth());
				accSubVoucherSchema
						.setF26(accMainVoucherSchema.getBranchCode());
				accSubVoucherSchema.setVoucherNo(accMainVoucherSchema
						.getVoucherNo());
				accSubVoucherSchema.setSuffixNo("" + (2 * j + k + 1));
				accSubVoucherSchema.setCurrency(blPrpJinvestIncome.getArr(j)
						.getCurrency1());
				accSubVoucherSchema.setF01(blPrpJinvestIncome.getArr(j)
						.getClassCode());
				accSubVoucherSchema.setF05(blPrpJinvestIncome.getArr(j)
						.getRiskCode());
				accSubVoucherSchema.setF27(blPrpJinvestIncome.getArr(j)
						.getHandler1Code());
				accSubVoucherSchema.setF28(blPrpJinvestIncome.getArr(j)
						.getComCode());
				accSubVoucherSchema.setRemark(strRemark);

				if (k == 0) {
					
					accSubVoucherSchema.setItemCode("6521");
					accSubVoucherSchema.setDirectionIdx("01/");
					accSubVoucherSchema.setDebitSource(blPrpJinvestIncome
							.getArr(j).getIncomeFee());
					accSubVoucherSchema.setExchangeRate(blPrpJinvestIncome
							.getArr(j).getExchangeRate());
					accSubVoucherSchema.setDebitDest(blPrpJinvestIncome.getArr(
							j).getIncomeFeeCny());

				} else {
					
					accSubVoucherSchema.setItemCode("2251");
					accSubVoucherSchema.setDirectionIdx("00");
					accSubVoucherSchema.setCreditSource(blPrpJinvestIncome
							.getArr(j).getIncomeFee());
					accSubVoucherSchema.setExchangeRate(blPrpJinvestIncome
							.getArr(j).getExchangeRate());
					accSubVoucherSchema.setCreditDest(blPrpJinvestIncome
							.getArr(j).getIncomeFeeCny());
				}
				if (Double.parseDouble(accSubVoucherSchema.getDebitDest()) != 0
						|| Double.parseDouble(accSubVoucherSchema
								.getCreditDest()) != 0)
					blAccVoucher.getBLAccSubVoucher().setArr(
							accSubVoucherSchema);
			}
		}
		BLAccSubVoucher blAccSubVoucher = blAccVoucher.getBLAccSubVoucher();
		if (blAccSubVoucher.getSize() == 0) {
			if (blPrpJinvestIncome.getSize() != 0)
				this.logPrintln("没有凭证信息！");
			return;
		}
		
		for (int j = 0; j < blAccSubVoucher.getSize(); j++) {
			blAccSubVoucher.getArr(j).setSuffixNo("" + (j + 1));
			blAccSubVoucher.createRawArticle(dbpool, blAccSubVoucher.getArr(j),
					"");
		}
		
		blAccSubVoucher.voucherComp("111");
		
		blAccSubVoucher.voucherOrder();
		blAccVoucher
				.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());
		
		blAccVoucher.save(dbpool);
		
		for (int j = 0; j < blPrpJinvestIncome.getSize(); j++) {
			dbPrpJinvestIncome.setSchema(blPrpJinvestIncome.getArr(j));
			dbPrpJinvestIncome.setAccBookCode(blAccVoucher
					.getBLAccMainVoucher().getArr(0).getAccBookCode());
			dbPrpJinvestIncome.setAccBookType(blAccVoucher
					.getBLAccMainVoucher().getArr(0).getAccBookType());
			dbPrpJinvestIncome.setCenterCode(blAccVoucher.getBLAccMainVoucher()
					.getArr(0).getCenterCode());
			dbPrpJinvestIncome.setBranchCode(blAccVoucher.getBLAccMainVoucher()
					.getArr(0).getBranchCode());
			dbPrpJinvestIncome.setYearMonth(blAccVoucher.getBLAccMainVoucher()
					.getArr(0).getYearMonth());
			dbPrpJinvestIncome.setVoucherNo(blAccVoucher.getBLAccMainVoucher()
					.getArr(0).getVoucherNo());
			dbPrpJinvestIncome.update(dbpool);
		}
		this.blAccMainVoucher.setArr(blAccVoucher.getBLAccMainVoucher().getArr(
				0));
		String strVoucherNo = blAccVoucher.getBLAccMainVoucher().getArr(0)
				.getVoucherNo();
		this.logPrintln("生成核算单位" + iCenterCode + "计提收益凭证 " + strVoucherNo);
	}

	/**
	 * 
	 * 
	 * @param 无
	 * @return 无
	 */
	public double getYearDate(String date) throws Exception {
		double YearDate = 0;
		int intYear = Integer.parseInt(date.substring(0, 4));
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		if (gregorianCalendar.isLeapYear(intYear))
			YearDate = 366;
		else
			YearDate = 365;
		return YearDate;
	}

	/**
	 * 带dbpool的save方法
	 * 
	 * @param 无
	 * @return 无
	 */
	public void save(DbPool dbpool) throws Exception {
		DBPrpJinvestIncome dbPrpJinvestIncome = new DBPrpJinvestIncome();
		int i = 0;
		for (i = 0; i < schemas.size(); i++) {
			dbPrpJinvestIncome.setSchema((PrpJinvestIncomeSchema) schemas
					.get(i));
			dbPrpJinvestIncome.insert(dbpool);
		}
	}

	/**
	 * 得到一条PrpJpayRefRecSchema记录
	 * 
	 * @param index
	 *            下标
	 * @return 一个PrpJpayRefRecSchema对象
	 * @throws Exception
	 */
	public PrpJinvestIncomeSchema evaluate(PrpJinvestSchema iSchema)
			throws Exception {
		PrpJinvestIncomeSchema prpJinvestIncomeSchema = new PrpJinvestIncomeSchema();
		prpJinvestIncomeSchema.setProcSeq(iSchema.getProcSeq());
		prpJinvestIncomeSchema.setCertiType(iSchema.getCertiType());
		prpJinvestIncomeSchema.setCertiNo(iSchema.getCertiNo());
		prpJinvestIncomeSchema.setPolicyNo(iSchema.getPolicyNo());
		prpJinvestIncomeSchema.setPayRefReason(iSchema.getPayRefReason());
		prpJinvestIncomeSchema.setClassCode(iSchema.getClassCode());
		prpJinvestIncomeSchema.setRiskCode(iSchema.getRiskCode());
		prpJinvestIncomeSchema.setInsuredCode(iSchema.getInsuredCode());
		prpJinvestIncomeSchema.setInsuredName(iSchema.getInsuredName());
		prpJinvestIncomeSchema.setInvestCount(iSchema.getInvestCount());
		prpJinvestIncomeSchema.setComCode(iSchema.getComCode());
		prpJinvestIncomeSchema.setMakeCom(iSchema.getMakeCom());
		prpJinvestIncomeSchema.setAgentCode(iSchema.getAgentCode());
		prpJinvestIncomeSchema.setHandler1Code(iSchema.getHandler1Code());
		prpJinvestIncomeSchema.setHandlerCode(iSchema.getHandlerCode());
		prpJinvestIncomeSchema.setStartDate(iSchema.getStartDate());
		prpJinvestIncomeSchema.setEndDate(iSchema.getEndDate());
		prpJinvestIncomeSchema.setValidDate(iSchema.getValidDate());
		prpJinvestIncomeSchema.setUnderWriteDate(iSchema.getUnderWriteDate());
		prpJinvestIncomeSchema.setCurrency1(iSchema.getCurrency1());
		prpJinvestIncomeSchema.setPlanFee(iSchema.getPlanFee());
		prpJinvestIncomeSchema.setIncometimes(iSchema.getIncometimes());
		prpJinvestIncomeSchema.setExchangeRate(iSchema.getExchangeRate());
		
		prpJinvestIncomeSchema.setCenterCode(iSchema.getCenterCode());
		
		prpJinvestIncomeSchema.setBranchCode("");
		prpJinvestIncomeSchema.setAccBookType("");
		prpJinvestIncomeSchema.setAccBookCode("");
		prpJinvestIncomeSchema.setVoucherNo("0");
		prpJinvestIncomeSchema.setYearMonth("");
		
		prpJinvestIncomeSchema.setBusinessNature(iSchema.getBusinessNature());
		
        
		prpJinvestIncomeSchema.setChannelType(iSchema.getChannelType());
        
		return prpJinvestIncomeSchema;
	}

	/**
	 * @desc 日志输出
	 * @param iLog
	 * @throws Exception
	 */
	public void logPrintln(String iLog) throws Exception {
		ChgDate chgDate = new ChgDate();
		logger
				.info(chgDate.getCurrentTime("yyyy-MM-dd hh:mm:ss") + "><"
						+ iLog);
	}
}
