package com.sp.prpall.blsvr.jf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.sp.account.blsvr.BLAccVoucher;
import com.sp.account.dbsvr.DBAccBankAccount;
import com.sp.account.schema.AccMainVoucherSchema;
import com.sp.account.schema.AccSubVoucherSchema;
import com.sp.platform.dto.domain.PrpDagentDto;
import com.sp.platform.resource.dtofactory.domain.DBPrpDagent;
import com.sp.prpall.blsvr.cb.BLPrpCitemKind;
import com.sp.prpall.dbsvr.cb.DBPrpCinsured;
import com.sp.prpall.dbsvr.jf.DBPrpJpayInvest;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefDetail;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefVoucher;
import com.sp.prpall.pubfun.Bill;
import com.sp.prpall.schema.PrpCitemKindSchema;
import com.sp.prpall.schema.PrpJinvestSchema;
import com.sp.prpall.schema.PrpJpayInvestSchema;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utiall.blsvr.BLPrpDcompany;
import com.sp.utiall.dbsvr.DBPrpDuser;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;  
import com.sp.utility.string.Str;  

public class BLPrpJpayInvest {

	private Vector schemas = new Vector();

	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * 构造函数
	 */
	public BLPrpJpayInvest() {
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
	public void setArr(PrpJpayInvestSchema iPrpJpayInvestSchema)
			throws Exception {
		try {
			schemas.add(iPrpJpayInvestSchema);
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
	public PrpJpayInvestSchema getArr(int index) throws Exception {
		PrpJpayInvestSchema prpJpayInvestSchema = null;
		try {
			prpJpayInvestSchema = (PrpJpayInvestSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJpayInvestSchema;
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
		DBPrpJpayInvest dbPrpJpayInvest = new DBPrpJpayInvest();
		if (iLimitCount > 0
				&& dbPrpJpayInvest.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJpayInvest.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJpayInvest WHERE "
					+ iWherePart;
			schemas = dbPrpJpayInvest.findByConditions(dbpool, strSqlStatement);
		}
	}
	/**
	 * 富安居邮储二次反馈按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool
	 *            全局池
	 * @param iWherePart
	 *            查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public void queryAgainPayToBank(String iWherePart, int iLimitCount) throws UserException,
			Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			this.queryAgainPayToBank(dbpool, iWherePart, iLimitCount);
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
	}

	/**
	 * 富安居邮储二次反馈按照查询条件得到一组记录数，并将这组记录赋给schemas对象
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
	public void queryAgainPayToBank(DbPool dbpool, String iWherePart, int iLimitCount)
			throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpJpayInvest dbPrpJpayInvest = new DBPrpJpayInvest();
		PrpJpayInvestSchema schema = null;
		if (iLimitCount > 0
				&& dbPrpJpayInvest.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJpayInvest.query");
		} else {
			initArr();
			strSqlStatement = " select prpcmain.printno,prpcmain.operatedate ,"
				+" prpcinsured.phonenumber,prpcmaininvest.sbranchbankcode,prpcmaininvest.sbranchbankname,"
				+" prpjpayinvest.appliname,prpjpayinvest.identifynumber,prpjpayinvest.planfee,prpjpayinvest.payreffee,"
				+" prpjpayinvest.policyno,prpjpayinvest.payaccountno"
				+" from prpcmain ,prpcinsured ,prpcmaininvest ,prpjpayinvest  where prpcmain.PolicyNo=prpcinsured.PolicyNo "
				+" And prpcmain.PolicyNo=prpcmaininvest.PolicyNo And prpcmain.PolicyNo=prpjpayinvest.PolicyNo And prpcinsured.InsuredFlag='2' And "
					+ iWherePart;
			ResultSet rs = dbpool.query(strSqlStatement);
			while (rs.next()) {
				schema = new PrpJpayInvestSchema();
				schema.setPrintNo(rs.getString("printno"));
				schema.setOperateDate(""+rs.getDate("operatedate"));
				schema.setPhoneNumber(rs.getString("phonenumber"));
				schema.setSbranchBankCode(rs.getString("sbranchbankcode"));
				schema.setSbrancBbankName(rs.getString("sbranchbankname"));
				schema.setAppliName(rs.getString("AppliName"));
				schema.setIdentifyNumber(rs.getString("IdentifyNumber"));
				schema.setPlanFee(rs.getString("PlanFee"));
				schema.setPayRefFee(rs.getString("PayRefFee"));
				schema.setPolicyNo(rs.getString("policyno"));
				schema.setPayAccountNo(rs.getString("payaccountno"));
				this.setArr(schema);
			}
			rs.close();
		
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
	public void queryByPayRefNo(String iWherePart, int iLimitCount)
			throws UserException, Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			this.queryByPayRefNo(dbpool, iWherePart, iLimitCount);
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
	public void queryByPayRefNo(DbPool dbpool, String iWherePart,
			int iLimitCount) throws UserException, Exception {
		String strSQL = "";
		DBPrpJpayInvest dbPrpJpayInvest = new DBPrpJpayInvest();
		PrpJpayInvestSchema schema = null;
		if (iLimitCount > 0
				&& dbPrpJpayInvest.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJpayInvest.query");
		} else {
			this.initArr();
			strSQL = " SELECT PAYREFNO,PAYREFDATE,PAYREFTIMES,CURRENCY2,OfferType,SUM(PAYREFFEE)AS SUMPAYREFFEE FROM PrpJpayInvest WHERE PAYREFNO in( "
					+ " SELECT PAYREFNO FROM PrpJpayInvest WHERE "
					+ iWherePart
					+ ")"
					+ " GROUP BY PAYREFNO,PAYREFDATE,PAYREFTIMES,CURRENCY2,OfferType";
			ResultSet rs = dbpool.query(strSQL);
			while (rs.next()) {
				schema = new PrpJpayInvestSchema();
				schema.setPayRefNo(rs.getString("PAYREFNO"));
				schema.setPayRefDate("" + rs.getDate("PAYREFDATE"));
				schema.setPayRefTimes(rs.getString("PAYREFTIMES"));
				schema.setPayRefFee(rs.getString("SUMPAYREFFEE"));
				schema.setCurrency2(rs.getString("CURRENCY2"));
				schema.setOfferType(rs.getString("OfferType"));
				this.setArr(schema);
			}
			rs.close();
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
	public String downLoad(String Payrefno) throws UserException, Exception {
		DbPool dbpool = new DbPool();
		String strPayRefTimes = "0";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			strPayRefTimes = this.downLoad(dbpool, Payrefno);
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
		return strPayRefTimes;
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
	public String downLoad(DbPool dbpool, String Payrefno)
			throws UserException, Exception {
		String strPayRefTimes = "0";
		String strWhere = "Payrefno='" + Payrefno + "'";
		this.query(dbpool, strWhere, 0);
		for (int i = 0; i < this.getSize(); i++) {
			DBPrpJpayInvest dbPrpJpayInvest = new DBPrpJpayInvest();
			dbPrpJpayInvest.setSchema(this.getArr(i));
			dbPrpJpayInvest.setPayRefTimes(""
					+ (Integer.parseInt(dbPrpJpayInvest.getPayRefTimes()) + 1));
			strPayRefTimes = dbPrpJpayInvest.getPayRefTimes();
			dbPrpJpayInvest.update(dbpool);
		}
		return strPayRefTimes;
	}

	/**
	 * @author xushaojiang 20070815 从富安居业务转入满期给付信息到PrpJPayInvest表
	 * @param iCertiType
	 *            业务类型
	 * @param iCertiNo
	 *            业务号
	 * @throws UserException
	 * @throws Exception
	 */
	public void transData(String iCertiType, String iCertiNo)
			throws UserException, Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			this.transData(dbpool, iCertiType, iCertiNo);
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
	 * @author xushaojiang 20070815 从富安居业务转入满期给付信息到PrpJPayInvest表
	 * @param dbpool
	 *            数据库连接池
	 * @param iCertiType
	 *            业务类型
	 * @param iCertiNo
	 *            业务号
	 * @throws UserException
	 * @throws Exception
	 */
	public void transData(DbPool dbpool, String iCertiType, String iCertiNo)
			throws UserException, Exception {
		BLPrpJinvest blPrpJinvest = new BLPrpJinvest();
		PrpJpayInvestSchema schema = new PrpJpayInvestSchema();
		PrpJinvestSchema prpJinvestSchema = null;
		DBPrpCinsured dbPrpCinsured =null;
		String strWhere = " CERTITYPE ='P' AND PAYREFREASON ='R01' AND flag ='E' AND POLICYNO ='"
				+ iCertiNo + "'";
		blPrpJinvest.query(dbpool, strWhere, 0);
		double strSumIncomeFee = 0;
		if (blPrpJinvest.getSize() == 0)
			throw new UserException(-98, -1167, "BLPrpJpayInvest.transData",
					"无此XX信息：" + iCertiNo);
		this.initArr();
		for (int i = 0; i < blPrpJinvest.getSize(); i++) {
			if (blPrpJinvest.getArr(i).getAppliName() == null
					|| blPrpJinvest.getArr(i).getAppliName().equals(""))
				throw new UserException(-98, -1167,
						"BLPrpJpayInvest.transData", "XX：" + iCertiNo
								+ "XX人不能为空");
			if (blPrpJinvest.getArr(i).getPayAccountNo() == null
					|| blPrpJinvest.getArr(i).getPayAccountNo().equals(""))
				throw new UserException(-98, -1167,
						"BLPrpJpayInvest.transData", "XX：" + iCertiNo
								+ "给付银行帐号不能为空");
			if (blPrpJinvest.getArr(i).getFlag() == null
					|| blPrpJinvest.getArr(i).getFlag().equals("TB"))
				throw new UserException(-98, -1167,
						"BLPrpJpayInvest.transData", "XX：" + iCertiNo
								+ "已经退XXXXX，不能做满期给付");
			prpJinvestSchema = blPrpJinvest.getArr(i);
	    	
			dbPrpCinsured = new DBPrpCinsured();
    		int intReturn = dbPrpCinsured.getInfo(dbpool, prpJinvestSchema.getPolicyNo(),"1");
        	if (intReturn==100)
    			throw new UserException(-98, -1167, "BLPrpJpayjnvest.transData",
    					"PrpCinsured表中没有批单"+iCertiNo+"对应XXXX人数据" );  
        	if (!dbPrpCinsured.getInsuredFlag().equals("2"))
    			throw new UserException(-98, -1167, "BLPrpJpayjnvest.transData",
    					"PrpCinsured表中XX"+dbPrpCinsured.getPolicyNo()+"的Serialno和InsuredFlag对应关系不对" ); 
        	
			schema.setProcSeq(prpJinvestSchema.getRaidNo());
			schema.setCertiNo(prpJinvestSchema.getCertiNo());
			schema.setCertiType(prpJinvestSchema.getCertiType());
			schema.setPolicyNo(prpJinvestSchema.getPolicyNo());
			schema.setSerialNo(prpJinvestSchema.getSerialNo());
			schema.setPayRefReason(prpJinvestSchema.getPayRefReason());
			schema.setClassCode(prpJinvestSchema.getClassCode());
			schema.setRiskCode(prpJinvestSchema.getRiskCode());
			schema.setContractNo(prpJinvestSchema.getContractNo());
			schema.setAppliCode(prpJinvestSchema.getAppliCode());
			schema.setAppliName(prpJinvestSchema.getAppliName());
			schema.setInsuredCode(prpJinvestSchema.getInsuredCode());
			schema.setInsuredName(prpJinvestSchema.getInsuredName());
			schema.setStartDate(prpJinvestSchema.getStartDate());
			schema.setEndDate(prpJinvestSchema.getEndDate());
			schema.setValidDate(prpJinvestSchema.getValidDate());
			schema.setCurrency1(prpJinvestSchema.getCurrency1());
			
			double reNewalPayment = 0;
			if(prpJinvestSchema.getReNewalPayment() != null && !prpJinvestSchema.getReNewalPayment().equals("")){
				reNewalPayment = Double.parseDouble(prpJinvestSchema.getReNewalPayment());
			}
			schema.setPlanFee(""+(Double.parseDouble(prpJinvestSchema.getLeftInvestMent()) - reNewalPayment));
			
			schema.setInvestCount(prpJinvestSchema.getInvestCount());
			schema.setComCode(prpJinvestSchema.getComCode());
			schema.setMakeCom(prpJinvestSchema.getMakeCom());
			schema.setAgentCode(prpJinvestSchema.getAgentCode());
			schema.setHandler1Code(prpJinvestSchema.getHandler1Code());
			schema.setHandlerCode(prpJinvestSchema.getHandlerCode());
			schema.setUnderWriteDate(prpJinvestSchema.getUnderWriteDate());
			schema.setBaseIncomeRate(prpJinvestSchema.getBaseIncomeRate());
			schema.setIncomeRate(prpJinvestSchema.getIncomeRate());
			
			if(prpJinvestSchema.getRiskCode().equals("2901"))
			{
				strSumIncomeFee = Str.round(((Double.parseDouble(prpJinvestSchema
						.getBaseIncomeRate())/100) * Double.parseDouble(prpJinvestSchema
						.getPlanFee())), 2);
				schema.setPayFlag("01");
			}else
			{
				if(prpJinvestSchema.getSumIncomeFee() != null && !prpJinvestSchema.getSumIncomeFee().equals(""))
	        	{
					strSumIncomeFee = Double.parseDouble(prpJinvestSchema.getSumIncomeFee());
	        	}else
	        	{
	        		strSumIncomeFee = 0;
	        	}
				if(prpJinvestSchema.getIncomeFlag().equals("1"))
				{
					schema.setPayFlag("01");
				}else
				{
					schema.setPayFlag("00");
				}
			}
			
			schema.setSumIncomeFee("" + strSumIncomeFee);
			schema.setIncometimes(prpJinvestSchema.getIncometimes());
			schema.setIncomeFlag(prpJinvestSchema.getIncomeFlag());
			schema.setIncomeFee("" + strSumIncomeFee);
			
			if(prpJinvestSchema.getRiskCode().equals("2901")){
				schema.setPayBankCode(prpJinvestSchema.getPayBankCode());
				
			}else{
				DBManager dbManager= new DBManager();
				dbManager=dbpool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);
				DBPrpDagent dbPrpDagent = new DBPrpDagent(dbManager);
				PrpDagentDto prpDagentDto = dbPrpDagent.findByPrimaryKey(prpJinvestSchema.getAgentCode());
				if (prpDagentDto==null){
					throw new UserException(-98, -1167,
							"BLPrpJpayInvest.transData", "XX：" + iCertiNo
									+ "的代理银行没有在平台系统做配置！");
				}else if(prpDagentDto.getAgentBankCode()==null||prpDagentDto.getAgentBankCode().equals("")){
					throw new UserException(-98, -1167,
							"BLPrpJpayInvest.transData", "XX：" + iCertiNo
									+ "的代理银行没有在平台系统做“银行名称”的配置！");
				}
				schema.setPayBankCode(prpDagentDto.getAgentBankCode());
			}
			
			schema.setInvestAccountNo(prpJinvestSchema.getInvestAccountNo());
			schema.setInvestBankCode(prpJinvestSchema.getInvestBankCode());
			schema.setPayAccountNo(prpJinvestSchema.getPayAccountNo());
			
			schema.setCenterCode(prpJinvestSchema.getCenterCode());
			
			schema.setBranchCode("");
			schema.setAccBookType("");
			schema.setAccBookCode("");
			schema.setYearMonth("");
			schema.setVoucherNo("0");
			schema.setExchangeRate("1.0");
			schema.setPlanFeeCNY("0");
			schema.setPayRefDate("");
			schema.setOperatorCode("");
			schema.setPayRefTimes("0");
			schema.setPayRefNo("0");
			schema.setPayRefFee(""
					+ (Double.parseDouble(schema.getPlanFee()) + Double
							.parseDouble(schema.getSumIncomeFee())));
			schema.setCurrency2(prpJinvestSchema.getCurrency1());
			schema.setRealPayRefFee("0");
			schema.setRealPayBankDate("");
			schema.setPayRefUserEason("");
			schema.setFlag("");
	        
			schema.setChannelType(prpJinvestSchema.getChannelType());
	        
			
			schema.setBusinessNature(prpJinvestSchema.getBusinessNature());
			
			
			schema.setReNewalFlag(prpJinvestSchema.getReNewalFlag());
			schema.setReNewalPolicyNo(prpJinvestSchema.getReNewalPolicyNo());
			schema.setReNewalPayment(prpJinvestSchema.getReNewalPayment());
			
	    	
			schema.setIdentifyType(dbPrpCinsured.getIdentifyType());
			schema.setIdentifyNumber(dbPrpCinsured.getIdentifyNumber());
        	
			this.setArr(schema);
		}
		
		this.save(dbpool);
	}

	/**
	 * @author xushaojiang 20070815 生成银行转帐单
	 * @param intType
	 *            业务类型
	 * @param iCurrentDate
	 *            日期
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iOperatorCode
	 *            操作员代码
	 * @throws UserException
	 * @throws Exception
	 */
	public String payToBank(String strOfferType, String iCurrentDate,
			String iCenterCode, String iOperatorCode) throws UserException,
			Exception {
		DbPool dbpool = new DbPool();
		String strPayRefNo = "";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			strPayRefNo = this.payToBank(dbpool, strOfferType, iCurrentDate,
					iCenterCode, iOperatorCode);
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
		return strPayRefNo;
	}

	/**
	 * @author xushaojiang 20070815 从富安居业务转入满期给付信息到PrpJPayInvest表
	 * @param dbpool
	 *            数据库连接池
	 * @param intType
	 *            业务类型
	 * @param iCurrentDate
	 *            日期
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iOperatorCode
	 *            操作员代码
	 * @throws UserException
	 * @throws Exception
	 */
	public String payToBank(DbPool dbpool, String strOfferType, String iCurrentDate,
			String iCenterCode, String iOperatorCode) throws UserException,
			Exception {
		String strPayRefNo = "";
		Bill bill = new Bill();
		int intYear = Integer.parseInt(iCurrentDate.substring(0, 4));
		strPayRefNo = bill.getNo("prpjpayrec", "0501", "00000000", intYear,
				"00");
		BLPrpJpayInvest blPrpJpayInvest = new BLPrpJpayInvest();
		for (int i = 0; i < this.getSize(); i++) {
			DBPrpJpayInvest dbPrpJpayInvest = new DBPrpJpayInvest();
			dbPrpJpayInvest.setSchema(this.getArr(i));
			if (dbPrpJpayInvest.getPayFlag() == null
					|| (!dbPrpJpayInvest.getPayFlag().equals("01")))
				throw new UserException(-98, -1167,
						"BLPrpJpayInvest.transData", "XX："
								+ dbPrpJpayInvest.getPolicyNo()
								+ "不是待给付状态，不能做满期给付");
			dbPrpJpayInvest.setPayFlag("02");
			dbPrpJpayInvest.setPayRefDate(iCurrentDate);
			dbPrpJpayInvest.setOperatorCode(iOperatorCode);
			dbPrpJpayInvest.setPayRefNo(strPayRefNo);
			dbPrpJpayInvest.setOfferType(strOfferType);
			dbPrpJpayInvest.update(dbpool);
		}
    	
		if(strOfferType.equals("801")){
			blPrpJpayInvest=this;
		}else{
			
			blPrpJpayInvest = this.findPayRefNo(dbpool, strPayRefNo);
		}
    	
		
		if(blPrpJpayInvest.getSize() >= 65536){
			throw new UserException(-98, -1167,
					"BLPrpJpayInvest.transData", "报盘总数超过65536行，请重新选择！");
		}
		
		
		if (strOfferType.equals("0")){
			WriteMethod(strOfferType,blPrpJpayInvest, iCenterCode, "" + intYear,
					strPayRefNo);
		}else if(strOfferType.equals("102")){
			WriteMethodICBC(strOfferType,blPrpJpayInvest, iCenterCode, "" + intYear,
					strPayRefNo);
		}else if(strOfferType.equals("105")){
			WriteMethodCBC(strOfferType,blPrpJpayInvest, iCenterCode, "" + intYear,
					strPayRefNo);
		}else if(strOfferType.equals("103")){
			WriteMethodABC(strOfferType,blPrpJpayInvest, iCenterCode, "" + intYear,
					strPayRefNo);
		}else if(strOfferType.equals("801")){
			WriteMethodPostalSavings(strOfferType,blPrpJpayInvest, iCenterCode, "" + intYear,
					strPayRefNo);
		}else{
			throw new UserException(-98, -1167,
					"BLPrpJpayInvest.payToBank", "报盘类型：“"
							+ strOfferType
							+ "” 是不合法的类型，不能生成报盘");
		}
		
		return strPayRefNo;
	}

	/**
	 * @author xushaojiang 20070815 2901生成转帐单execl文件
	 * @param blPrpJpayInvest
	 *            blPrpJpayInvest对象
	 * @param iYear
	 *            年
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iPayRefNo
	 *            打包号
	 * @throws UserException
	 * @throws Exception
	 */
	public void WriteMethod(String strOfferType,BLPrpJpayInvest blPrpJpayInvest,
			String iCenterCode, String iYear, String iPayRefNo)
			throws UserException, Exception {
		String path = SysConst.getProperty("PAYTOBANK_EXCEL_PATH");
		String separator = java.io.File.separator;
		
		String fileName = "";

		fileName = iCenterCode + "_" + iPayRefNo + ".xls";

		
		java.io.FileOutputStream fileOut = null;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("bank");
			wb.setSheetName(0, "bank", HSSFWorkbook.ENCODING_UTF_16);
			if (blPrpJpayInvest.getSize() == 0)
				throw new UserException(-98, -1167,
						"BLPrpJpayInvest.WriteMethod", "没有可用于生成报盘的数据");
			path += separator + iCenterCode;
			HSSFRow row0 = sheet.createRow((short) 0);
			row0.createCell((short) 0).setCellValue("XM");
			row0.createCell((short) 1).setCellValue("ZH");
			row0.createCell((short) 2).setCellValue("JE");
			HSSFCell cellXM; 
			HSSFCell cellZH; 
			HSSFCell cellJE; 
			for (int i = 0; i < blPrpJpayInvest.getSize(); i++) {
				
				HSSFRow row = sheet.createRow((short) (i + 1));
				cellXM = row.createCell((short) 0);
				cellZH = row.createCell((short) 1);
				cellJE = row.createCell((short) 2);
				cellXM.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellZH.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellJE.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellXM.setCellValue(blPrpJpayInvest.getArr(i).getAppliName());
				cellZH
						.setCellValue(blPrpJpayInvest.getArr(i)
								.getPayAccountNo());
				cellJE.setCellValue(Double.parseDouble(blPrpJpayInvest
						.getArr(i).getPayRefFee()));
			}
			
			File dirFile1 = new File(path);
			if (!dirFile1.exists()) {
				try {
					dirFile1.mkdir();
				} catch (Exception e) {
					throw new Exception("核算单位级目录不存在，创建失败！");
				}
			}
			
			path += separator + iYear;
			File dirFile2 = new File(path);
			if (!dirFile2.exists()) {
				try {
					dirFile2.mkdir();
				} catch (Exception e) {
					throw new Exception("月份级目录不存在，创建失败！");
				}
			}
			File file = new File(path + separator + fileName);
			
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			
			fileOut = new java.io.FileOutputStream(path + separator + fileName);
			wb.write(fileOut);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			fileOut.close();
		}
	}
	/**
	 * @author xushaojiang 20080623 非2901工行生成转帐单execl文件(Industry and Commercial Bank of China)——“爱存不存”)
	 * @param blPrpJpayInvest
	 *            blPrpJpayInvest对象
	 * @param iYear
	 *            年
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iPayRefNo
	 *            打包号
	 * @throws UserException
	 * @throws Exception
	 */
	public void WriteMethodICBC(String strOfferType,BLPrpJpayInvest blPrpJpayInvest,
			String iCenterCode, String iYear, String iPayRefNo)
			throws UserException, Exception {
		String path = SysConst.getProperty("PAYTOBANK_EXCEL_PATH");
		String separator = java.io.File.separator;
		String fileName = "";
		fileName = iCenterCode + "_" +strOfferType+"_"+ iPayRefNo + ".xls";
		java.io.FileOutputStream fileOut = null;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("bank");
			wb.setSheetName(0, "bank", HSSFWorkbook.ENCODING_UTF_16);
			if (blPrpJpayInvest.getSize() == 0)
				throw new UserException(-98, -1167,
						"BLPrpJpayInvest.WriteMethodICBC", "没有可用于生成报盘的数据");
			path += separator + iCenterCode;
			HSSFRow row0 = sheet.createRow((short) 0);
			row0.createCell((short) 0).setCellValue("XM");
			row0.createCell((short) 1).setCellValue("ZH");
			row0.createCell((short) 2).setCellValue("JE");
			HSSFCell cellXM; 
			HSSFCell cellZH; 
			HSSFCell cellJE; 
			for (int i = 0; i < blPrpJpayInvest.getSize(); i++) {
				
				HSSFRow row = sheet.createRow((short) (i + 1));
				cellXM = row.createCell((short) 0);
				cellZH = row.createCell((short) 1);
				cellJE = row.createCell((short) 2);
				cellXM.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellZH.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellJE.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellXM.setCellValue(blPrpJpayInvest.getArr(i).getAppliName());
				cellZH
						.setCellValue(blPrpJpayInvest.getArr(i)
								.getPayAccountNo());
				cellJE.setCellValue(Double.parseDouble(blPrpJpayInvest
						.getArr(i).getPayRefFee()));
			}
			
			File dirFile1 = new File(path);
			if (!dirFile1.exists()) {
				try {
					dirFile1.mkdir();
				} catch (Exception e) {
					throw new Exception("核算单位级目录不存在，创建失败！");
				}
			}
			
			path += separator + iYear;
			File dirFile2 = new File(path);
			if (!dirFile2.exists()) {
				try {
					dirFile2.mkdir();
				} catch (Exception e) {
					throw new Exception("月份级目录不存在，创建失败！");
				}
			}
			File file = new File(path + separator + fileName);
			
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			
			fileOut = new java.io.FileOutputStream(path + separator + fileName);
			wb.write(fileOut);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			fileOut.close();
		}
	}
	/**
	 * @author xushaojiang 20080623 建行生成转帐单execl文件 (Construction Bank of China)——“存不存？” 
	 * @param blPrpJpayInvest
	 *            blPrpJpayInvest对象
	 * @param iYear
	 *            年
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iPayRefNo
	 *            打包号
	 * @throws UserException
	 * @throws Exception
	 */
	public void WriteMethodCBC(String strOfferType,BLPrpJpayInvest blPrpJpayInvest,
			String iCenterCode, String iYear, String iPayRefNo)
			throws UserException, Exception {
		String path = SysConst.getProperty("PAYTOBANK_EXCEL_PATH");
		String separator = java.io.File.separator;
		String fileName = "";
		fileName = iCenterCode + "_" +strOfferType+"_"+ iPayRefNo + ".xls";
		java.io.FileOutputStream fileOut = null;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("bank");
			wb.setSheetName(0, "bank", HSSFWorkbook.ENCODING_UTF_16);
			if (blPrpJpayInvest.getSize() == 0)
				throw new UserException(-98, -1167,
						"BLPrpJpayInvest.WriteMethodICBC", "没有可用于生成报盘的数据");
			path += separator + iCenterCode;

			
			
			
			HSSFCell cellRowNo; 			
			HSSFCell cellPayAccountNo; 		
			HSSFCell cellAppliName; 		
			HSSFCell cellPayRefFee; 		
			HSSFCell cellRemark; 			
			HSSFCell cellNote; 				
			
			for (int i = 0; i < blPrpJpayInvest.getSize(); i++) {
				
				HSSFRow row = sheet.createRow((short) (i));
				cellRowNo = row.createCell((short) 0);
				cellPayAccountNo = row.createCell((short) 1);
				cellAppliName = row.createCell((short) 2);
				cellPayRefFee = row.createCell((short) 3);
				cellRemark = row.createCell((short) 4);
				cellNote = row.createCell((short) 5);
				
				cellRowNo.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellPayAccountNo.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellAppliName.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellPayRefFee.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellRemark.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellNote.setEncoding(HSSFCell.ENCODING_UTF_16);
				
				String rowNo =""+(i+1);
				
				if(rowNo.length()<7)
				{
					rowNo="0000000"+rowNo;
				}
				rowNo=rowNo.substring(rowNo.length()-7);
				cellRowNo.setCellValue(rowNo);
				cellPayAccountNo.setCellValue(blPrpJpayInvest.getArr(i)
						.getPayAccountNo());
				cellAppliName.setCellValue(blPrpJpayInvest.getArr(i).getAppliName());
				cellPayRefFee.setCellValue(Double.parseDouble(blPrpJpayInvest
						.getArr(i).getPayRefFee()));
				cellRemark.setCellValue("满期给付");
				cellNote.setCellValue("用户"+blPrpJpayInvest.getArr(i).getOperatorCode()+"在"+blPrpJpayInvest.getArr(i).getPayRefDate()+"生成报盘");		
			}
			
			
			
			File dirFile1 = new File(path);
			if (!dirFile1.exists()) {
				try {
					dirFile1.mkdir();
				} catch (Exception e) {
					throw new Exception("核算单位级目录不存在，创建失败！");
				}
			}
			
			path += separator + iYear;
			File dirFile2 = new File(path);
			if (!dirFile2.exists()) {
				try {
					dirFile2.mkdir();
				} catch (Exception e) {
					throw new Exception("月份级目录不存在，创建失败！");
				}
			}
			File file = new File(path + separator + fileName);
			
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			
			fileOut = new java.io.FileOutputStream(path + separator + fileName);
			wb.write(fileOut);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			fileOut.close();
		}
	}
	/**
	 * @author xushaojiang 20080623 农行生成转帐单execl文件 ABC（Agriculture Bank of China）——“啊，不存！”

	 * @param blPrpJpayInvest
	 *            blPrpJpayInvest对象
	 * @param iYear
	 *            年
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iPayRefNo
	 *            打包号
	 * @throws UserException
	 * @throws Exception
	 */
	public void WriteMethodABC(String strOfferType,BLPrpJpayInvest blPrpJpayInvest,
			String iCenterCode, String iYear, String iPayRefNo)
			throws UserException, Exception {
		String path = SysConst.getProperty("PAYTOBANK_EXCEL_PATH");
		String separator = java.io.File.separator;
		String fileName = "";
		fileName = iCenterCode + "_" +strOfferType+"_"+ iPayRefNo + ".xls";
		java.io.FileOutputStream fileOut = null;
		try {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("bank");
			wb.setSheetName(0, "bank", HSSFWorkbook.ENCODING_UTF_16);
			if (blPrpJpayInvest.getSize() == 0)
				throw new UserException(-98, -1167,
						"BLPrpJpayInvest.WriteMethodICBC", "没有可用于生成报盘的数据");
			path += separator + iCenterCode;
			HSSFRow row0 = sheet.createRow((short) 0);
			row0.createCell((short) 0).setCellValue("XM");
			row0.createCell((short) 1).setCellValue("ZH");
			row0.createCell((short) 2).setCellValue("JE");
			HSSFCell cellXM; 
			HSSFCell cellZH; 
			HSSFCell cellJE; 
			for (int i = 0; i < blPrpJpayInvest.getSize(); i++) {
				
				HSSFRow row = sheet.createRow((short) (i + 1));
				cellXM = row.createCell((short) 0);
				cellZH = row.createCell((short) 1);
				cellJE = row.createCell((short) 2);
				cellXM.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellZH.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellJE.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellXM.setCellValue(blPrpJpayInvest.getArr(i).getAppliName());
				cellZH
						.setCellValue(blPrpJpayInvest.getArr(i)
								.getPayAccountNo());
				cellJE.setCellValue(Double.parseDouble(blPrpJpayInvest
						.getArr(i).getPayRefFee()));
			}
			
			File dirFile1 = new File(path);
			if (!dirFile1.exists()) {
				try {
					dirFile1.mkdir();
				} catch (Exception e) {
					throw new Exception("核算单位级目录不存在，创建失败！");
				}
			}
			
			path += separator + iYear;
			File dirFile2 = new File(path);
			if (!dirFile2.exists()) {
				try {
					dirFile2.mkdir();
				} catch (Exception e) {
					throw new Exception("月份级目录不存在，创建失败！");
				}
			}
			File file = new File(path + separator + fileName);
			
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			
			fileOut = new java.io.FileOutputStream(path + separator + fileName);
			wb.write(fileOut);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			fileOut.close();
		}
	}
	/**
	 * @author xushaojiang 20081020 非2901邮储生成转帐单execl文件
	 * @param blPrpJpayInvest
	 *            blPrpJpayInvest对象
	 * @param iYear
	 *            年
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iPayRefNo
	 *            打包号
	 * @throws UserException
	 * @throws Exception
	 */
	public void WriteMethodPostalSavings(String strOfferType,BLPrpJpayInvest blPrpJpayInvest,
			String iCenterCode, String iYear, String iPayRefNo)
			throws UserException, Exception {
		String path = SysConst.getProperty("PAYTOBANK_EXCEL_PATH");
		String separator = java.io.File.separator;
		String fileName = "";
		fileName = iCenterCode + "_" +strOfferType+"_"+ iPayRefNo + ".xls";
		java.io.FileOutputStream fileOut = null;
		try {
			double sumPayRefFee = 0;
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("bank");
			wb.setSheetName(0, "bank", HSSFWorkbook.ENCODING_UTF_16);
			if (blPrpJpayInvest.getSize() == 0)
				throw new UserException(-98, -1167,
						"BLPrpJpayInvest.WriteMethodICBC", "没有可用于生成报盘的数据");
			path += separator + iCenterCode;
			HSSFRow row0 = sheet.createRow((short) 0);
			
			/*MODIFY BEGIN ZHUDANSHAN 2009-09-09 BY payment-87/邮政格式更改*/
			row0.createCell((short) 0).setCellValue("");
			row0.createCell((short) 1).setCellValue("");
			row0.createCell((short) 2).setCellValue("");
			row0.createCell((short) 3).setCellValue("");
			row0.createCell((short) 4).setCellValue("");
			row0.createCell((short) 5).setCellValue("");
			row0.createCell((short) 6).setCellValue("");
			row0.createCell((short) 7).setCellValue("");
			row0.createCell((short) 8).setCellValue("");
			row0.createCell((short) 9).setCellValue("");

			HSSFCell cellPolicyNo; 
			HSSFCell cellAppliName; 
			
			HSSFCell cellPayAccountNo; 
			HSSFCell cellPayRefFee; 
			HSSFCell cellflage; 
			HSSFCell celldealtype   ; 
			HSSFCell cellflag2  ; 
			
			
			
			
			for (int i = 0; i < blPrpJpayInvest.getSize(); i++) {
				
				HSSFRow row = sheet.createRow((short) (i + 1));
								
				
				cellflage = row.createCell((short) 0);
				cellPayAccountNo = row.createCell((short) 1);
				cellPayRefFee = row.createCell((short) 2);
				cellPolicyNo = row.createCell((short) 3);
				cellAppliName = row.createCell((short) 4);
				celldealtype = row.createCell((short) 5);
				cellflag2 = row.createCell((short) 6);
				
				
				
				
				cellPolicyNo.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellAppliName.setEncoding(HSSFCell.ENCODING_UTF_16);
				
				cellPayAccountNo.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellPolicyNo.setEncoding(HSSFCell.ENCODING_UTF_16);

				cellflage.setEncoding(HSSFCell.ENCODING_UTF_16);
				celldealtype.setEncoding(HSSFCell.ENCODING_UTF_16);
				cellflag2.setEncoding(HSSFCell.ENCODING_UTF_16);
				
				cellPolicyNo.setCellValue(blPrpJpayInvest.getArr(i).getPolicyNo());
				cellAppliName.setCellValue(blPrpJpayInvest.getArr(i).getAppliName());
				
				cellPayAccountNo.setCellValue(blPrpJpayInvest.getArr(i).getPayAccountNo());
				cellPayRefFee.setCellValue(Double.parseDouble(blPrpJpayInvest.getArr(i).getPayRefFee()));
				
				cellflage.setCellValue("1");
				celldealtype .setCellValue("06");
				cellflag2 .setCellValue("B");
				
				sumPayRefFee += Double.parseDouble(blPrpJpayInvest.getArr(i).getPayRefFee());
			}
			
			
			
			sheet.getRow(0).getCell((short)4).setEncoding(HSSFCell.ENCODING_UTF_16);
			sheet.getRow(0).getCell((short)5).setEncoding(HSSFCell.ENCODING_UTF_16);
				
			sheet.getRow(0).getCell((short)4).setCellValue(blPrpJpayInvest.getSize());
			sheet.getRow(0).getCell((short)5).setCellValue(sumPayRefFee);
			
			
			sheet.getRow(0).getCell((short)0).setEncoding(HSSFCell.ENCODING_UTF_16);
			sheet.getRow(0).getCell((short)0).setCellValue("F");
			sheet.getRow(0).getCell((short)1).setEncoding(HSSFCell.ENCODING_UTF_16);
			sheet.getRow(0).getCell((short)1).setCellValue("0085");
			sheet.getRow(0).getCell((short)2).setEncoding(HSSFCell.ENCODING_UTF_16);
			ChgDate chgDate = new ChgDate();
			String currentDate = chgDate.getCurrentTime("yyyyMMdd");
			sheet.getRow(0).getCell((short)2).setCellValue(currentDate);
			sheet.getRow(0).getCell((short)3).setEncoding(HSSFCell.ENCODING_UTF_16);
			sheet.getRow(0).getCell((short)3).setCellValue("1");



			/*MODIFY END ZHUDANSHAN 2009-09-09 BY payment-87/邮政格式更改*/

			
			File dirFile1 = new File(path);
			if (!dirFile1.exists()) {
				try {
					dirFile1.mkdir();
				} catch (Exception e) {
					throw new Exception("核算单位级目录不存在，创建失败！");
				}
			}
			
			path += separator + iYear;
			File dirFile2 = new File(path);
			if (!dirFile2.exists()) {
				try {
					dirFile2.mkdir();
				} catch (Exception e) {
					throw new Exception("月份级目录不存在，创建失败！");
				}
			}
			File file = new File(path + separator + fileName);
			
			if (file.exists() && file.isFile()) {
				file.delete();
			}
			
			fileOut = new java.io.FileOutputStream(path + separator + fileName);
			wb.write(fileOut);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			fileOut.close();
		}
	}	
	/**
	 * @author xushaojiang 20070820 根据银行回执回写满期给付表数据，并生成XXXXX凭证
	 * @param intType
	 *            业务类型
	 * @param iYear
	 *            年
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iPayRefNo
	 *            打包号
	 * @param iOperatorCode
	 *            操作员代码
	 * @throws UserException
	 * @throws Exception
	 */
	public String payRefToVoucher(String strOfferType, String[] check,
			String iCenterCode, String[] iYear, String iDate,
			String[] iPayRefNo, String iOperatorCode) throws UserException,
			Exception {
		DbPool dbpool = new DbPool();
		String strVoucherNo = "";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			strVoucherNo = this.payRefToVoucher(dbpool, strOfferType, check,
					iCenterCode, iYear, iDate, iPayRefNo, iOperatorCode);
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
		return strVoucherNo;
	}

	/**
	 * @author xushaojiang 20070820 根据银行回执回写满期给付表数据，并生成XXXXX凭证
	 * @param dbpool
	 *            数据库连接池
	 * @param intType
	 *            业务类型
	 * @param iYear
	 *            年
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iPayRefNo
	 *            打包号
	 * @param iOperatorCode
	 *            操作员代码
	 * @throws UserException
	 * @throws Exception
	 */
	public String payRefToVoucher(DbPool dbpool, String strOfferType, String[] check,
			String iCenterCode, String[] iYear, String iDate,
			String[] iPayRefNo, String iOperatorCode) throws UserException,
			Exception {
		
		if (iDate == null || iDate.equals("")) {
			com.sp.utility.string.Date sinosoftDate = new com.sp.utility.string.Date();
			iDate = new ChgDate().toFormat(sinosoftDate
					.getString(sinosoftDate.YEAR + sinosoftDate.MONTH
							+ sinosoftDate.DATE));
		}
	    
	    DBPrpJpayRefVoucher dBPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
	    String strRealPayRefNo = dBPrpJpayRefVoucher.genRealPayRefNo();
	    dBPrpJpayRefVoucher.setRealPayRefNo(strRealPayRefNo);
	    dBPrpJpayRefVoucher.setPayRefNoType("B");
	    dBPrpJpayRefVoucher.setCenterCode(iCenterCode);
	    dBPrpJpayRefVoucher.setVoucherDate(iDate);
	    dBPrpJpayRefVoucher.setComCode(iCenterCode);
	    dBPrpJpayRefVoucher.setPayRefCode(iOperatorCode);
	    dBPrpJpayRefVoucher.setToCenterCode("0");
	    dBPrpJpayRefVoucher.setAttribute2("0");
	    dBPrpJpayRefVoucher.insert(dbpool);
	    
		String strYearMonth = iDate.substring(0, 4) + iDate.substring(5, 7);
		String strVoucherNo = "";
		String strWhere = "";
		String paymentflag ="";
		String payRefUserEason ="";
		BLPrpJpayInvest blPrpJpayInvest = new BLPrpJpayInvest();
		BLPrpJpayInvest blPrpJpayInvestNew = new BLPrpJpayInvest();
		DBPrpJpayInvest dbPrpJpayInvest = new DBPrpJpayInvest();
		PrpJpayInvestSchema prpJpayInvestSchema = null;

















		for (int m = 0; m < check.length; m++) {
			
			this.initArr();
			int resultLength =0;
			ReadMethod00(strOfferType, iCenterCode, iYear[Integer.parseInt(check[m])],
						iPayRefNo[Integer.parseInt(check[m])]);
			resultLength=this.getSize()-1;
			for (int i = 0; i < resultLength; i++) {	
				
				strWhere = "PayRefNo ='" + this.getArr(i).getPayRefNo()
						+ "' AND PayAccountNo ='"
						+ this.getArr(i).getPayAccountNo() + "' ";
				blPrpJpayInvest.query(dbpool, strWhere, 0);		
				for (int j = 0; j < blPrpJpayInvest.getSize(); j++) {
					if (blPrpJpayInvest.getArr(j).getPayFlag() == null
							|| (!blPrpJpayInvest.getArr(j).getPayFlag().equals(
									"02")))
						throw new UserException(-98, -1167,
								"BLPrpJpayInvest.payRefToVoucher", "XX："
										+ blPrpJpayInvest.getArr(j)
												.getCertiNo()
										+ "不是”已提交银行“状态，不能做满期给付确认");
					prpJpayInvestSchema = blPrpJpayInvest.getArr(j);
					prpJpayInvestSchema.setRealPayRefFee(prpJpayInvestSchema
							.getPayRefFee());
					prpJpayInvestSchema.setPayRefUserEason(this.getArr(i)
							.getPayRefUserEason());
					prpJpayInvestSchema.setRealPayBankDate(iDate);
					

					if (this.getArr(i).getPayRefUserEason().equals("001")
							|| this.getArr(i).getPayRefUserEason().equals("002")) {

						prpJpayInvestSchema.setPayFlag("04");
					} else {
						prpJpayInvestSchema.setPayFlag("03");
					}
					blPrpJpayInvestNew.setArr(prpJpayInvestSchema);
				}
			}
		}
		























		double payRefFeeSum = 0;
		
		for (int i = 0; i < blPrpJpayInvestNew.getSize(); i++) {
			dbPrpJpayInvest.setSchema(blPrpJpayInvestNew.getArr(i));
			if (dbPrpJpayInvest.getPayFlag().equals("04")) {
				dbPrpJpayInvest.setCenterCode(iCenterCode);
				dbPrpJpayInvest.setBranchCode(iCenterCode);








				dbPrpJpayInvest.setPlanFeeCNY(dbPrpJpayInvest.getPayRefFee());
				
				payRefFeeSum += Double.parseDouble(dbPrpJpayInvest.getPayRefFee());
				
			}
			dbPrpJpayInvest.setRealPayBankDate(iDate);
			
			dbPrpJpayInvest.setRealPayRefNo(strRealPayRefNo);
			
			dbPrpJpayInvest.update(dbpool);
		}
		
		for (int i = 0; i < blPrpJpayInvestNew.getSize(); i++) {
			prpJpayInvestSchema = blPrpJpayInvestNew.getArr(i);
			if (prpJpayInvestSchema.getPayFlag().equals("03")) {
				paymentflag="03";
				payRefUserEason=prpJpayInvestSchema.getPayRefUserEason();
			}else{
				
				payRefUserEason="";
				paymentflag="06";
				
			}			
				String strSql = "update prpjinvest set paymentFlag ='"+paymentflag+"',PayRefUserEason='"
						+ payRefUserEason
						+ "' Where RaidNo = '"
						+ prpJpayInvestSchema.getProcSeq()
						+ "'  AND CERTINO = '"
						+ prpJpayInvestSchema.getCertiNo()
						+ "'  AND CERTITYPE = '"
						+ prpJpayInvestSchema.getCertiType()
						+ "'  AND SERIALNO = '"
						+ prpJpayInvestSchema.getSerialNo()
						+ "'  AND PAYREFREASON = '"
						+ prpJpayInvestSchema.getPayRefReason() + "'";
				dbpool.update(strSql);
		}
		
	    
		payRefFeeSum = Str.round(Str.round(payRefFeeSum, 8), 2);
	    DBPrpJpayRefDetail dbPrpJpayRefDetail = new DBPrpJpayRefDetail();
	    dbPrpJpayRefDetail.setPayWay("221");
	    dbPrpJpayRefDetail.setPayRefNo(strRealPayRefNo);
	    dbPrpJpayRefDetail.setPayRefDate(iDate);
	    dbPrpJpayRefDetail.setPayRefFee(payRefFeeSum+"");
	    dbPrpJpayRefDetail.setCurrency2(blPrpJpayInvestNew.getArr(0).getCurrency2());
	    dbPrpJpayRefDetail.setSerialNo("1");
	    dbPrpJpayRefDetail.setRealPayRefNo(strRealPayRefNo);
	    
	    dbPrpJpayRefDetail.setBankCode("02");
	    dbPrpJpayRefDetail.setAccountNo("004");
	    dbPrpJpayRefDetail.insert(dbpool);
	    
	    
	    BLPrpJpaymentVoucher blPrpJpaymentVoucher = new BLPrpJpaymentVoucher();
	    strVoucherNo = blPrpJpaymentVoucher.callProcedure(dbpool,strRealPayRefNo);
		return strVoucherNo;
	}
	/**
	 * @author xushaojiang 20080701 非2901根据银行回执回写满期给付表数据，并生成XXXXX凭证
	 * @param intType
	 *            业务类型
	 * @param iYear
	 *            年
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iPayRefNo
	 *            打包号
	 * @param iOperatorCode
	 *            操作员代码
	 * @throws UserException
	 * @throws Exception
	 */
	public String payRefToVoucher2900(String strOfferType,String iPayWay,
			String strBankItemCode, String iCenterCode, String[] iYear, String iDate,
			String[] iPayRefNo, String iOperatorCode,String  iCurrency) throws UserException,
			Exception {
		DbPool dbpool = new DbPool();
		String strVoucherNo = "";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			strVoucherNo = this.payRefToVoucher2900(dbpool, strOfferType,iPayWay,strBankItemCode,
					iCenterCode, iYear, iDate, iPayRefNo, iOperatorCode,iCurrency);
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
		return strVoucherNo;
	}

	/**
	 * @author xushaojiang 20080701 非2901根据银行回执回写满期给付表数据，并生成XXXXX凭证
	 * @param dbpool
	 *            数据库连接池
	 * @param intType
	 *            业务类型
	 * @param iYear
	 *            年
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iPayRefNo
	 *            打包号
	 * @param iOperatorCode
	 *            操作员代码
	 * @throws UserException
	 * @throws Exception
	 */
	public String payRefToVoucher2900(DbPool dbpool, String strOfferType,
			String iPayWay,String strBankItemCode,
			String iCenterCode, String[] iYear, String iDate,
			String[] iPayRefNo, String iOperatorCode,String  iCurrency) throws UserException,
			Exception {
		
		if (iDate == null || iDate.equals("")) {
			com.sp.utility.string.Date sinosoftDate = new com.sp.utility.string.Date();
			iDate = new ChgDate().toFormat(sinosoftDate
					.getString(sinosoftDate.YEAR + sinosoftDate.MONTH
							+ sinosoftDate.DATE));
		}
	    
	    DBPrpJpayRefVoucher dBPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
	    String strRealPayRefNo = dBPrpJpayRefVoucher.genRealPayRefNo();

		String strVoucherNo = "";
		String strReturn = "";
		String strWhere = "";
		String strWherepart = "";
		String paymentflag ="";
		String payRefUserEason ="";
		BLPrpJpayInvest blPrpJpayInvest = new BLPrpJpayInvest();
		BLPrpJpayInvest blPrpJpayInvestNew = null;
		DBPrpJpayInvest dbPrpJpayInvest = null;
		PrpJpayInvestSchema prpJpayInvestSchema =null;
		
		String strWhereNew ="Comcode like '"+iCenterCode.substring(0, 2)+"%' And CenterFlag='1' order by comcode ";
		if(iCenterCode.equals("00000000")){
			strWhereNew =" CenterFlag='1' order by comcode ";
		}
		BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
		blPrpDcompany.query(dbpool, strWhereNew, 0);
		
		
		for (int m = 0; m < iPayRefNo.length; m++) {
			this.initArr();
			int resultLength =0;
			
			if (strOfferType.equals("0")){
				ReadMethod00(strOfferType, iCenterCode, iYear[m],
						iPayRefNo[m]);
				resultLength=this.getSize()-1;
			}else if(strOfferType.equals("102")){
				ReadMethodICBC(strOfferType, iCenterCode, iYear[m],
						iPayRefNo[m]);
				resultLength=this.getSize();
			}else if(strOfferType.equals("105")){
				ReadMethodCBC(strOfferType, iCenterCode, iYear[m],
						iPayRefNo[m]);
				resultLength=this.getSize();
			}else if(strOfferType.equals("103")){
				ReadMethodABC(strOfferType, iCenterCode, iYear[m],
						iPayRefNo[m]);
				resultLength=this.getSize();
			}else if(strOfferType.equals("801")){
				


			}else{
				throw new UserException(-98, -1167,
						"BLPrpJpayInvest.payToBank", "报盘类型："
								+ strOfferType
								+ "系统未实现，无法解析该类型回执文件");
			}
			
			if(strOfferType.equals("801")){
				strWhere = "PayRefNo ='" + iPayRefNo[m]+"'";			
				blPrpJpayInvest.query(dbpool, strWhere, 0);		
				for (int j = 0; j < blPrpJpayInvest.getSize(); j++) {
					dbPrpJpayInvest = new DBPrpJpayInvest();
					if (blPrpJpayInvest.getArr(j).getPayFlag() == null
							|| (!blPrpJpayInvest.getArr(j).getPayFlag().equals(
									"02")))
						throw new UserException(-98, -1167,
								"BLPrpJpayInvest.payRefToVoucher", "XX："
										+ blPrpJpayInvest.getArr(j)
												.getCertiNo()
										+ "不是”已提交银行“状态，不能做满期给付确认");
					dbPrpJpayInvest.setSchema(blPrpJpayInvest.getArr(j));
					dbPrpJpayInvest.setPayRefUserEason("");
					dbPrpJpayInvest.setRealPayBankDate(iDate);				
					dbPrpJpayInvest.setPayFlag("04");
					dbPrpJpayInvest.setRealPayRefFee(dbPrpJpayInvest.getPayRefFee());
					dbPrpJpayInvest.setRealPayRefNo(strRealPayRefNo);
					dbPrpJpayInvest.update(dbpool);
				}
			
			}else{
				for (int i = 0; i < resultLength; i++) {
					strWhere = "PayRefNo ='" + this.getArr(i).getPayRefNo()
							+ "' AND PayAccountNo ='"
							+ this.getArr(i).getPayAccountNo()
							
							+ "' AND AppliName='"+this.getArr(i).getAppliName()+"'";
							
					blPrpJpayInvest.query(dbpool, strWhere, 0);		
					for (int j = 0; j < blPrpJpayInvest.getSize(); j++) {
						dbPrpJpayInvest = new DBPrpJpayInvest();
						if (blPrpJpayInvest.getArr(j).getPayFlag() == null
								|| (!blPrpJpayInvest.getArr(j).getPayFlag().equals(
										"02")))
							throw new UserException(-98, -1167,
									"BLPrpJpayInvest.payRefToVoucher", "XX："
											+ blPrpJpayInvest.getArr(j)
													.getCertiNo()
											+ "不是”已提交银行“状态，不能做满期给付确认");
						dbPrpJpayInvest.setSchema(blPrpJpayInvest.getArr(j));
						dbPrpJpayInvest.setPayRefUserEason(this.getArr(i)
								.getPayRefUserEason());
						dbPrpJpayInvest.setRealPayBankDate(iDate);		
						if (strOfferType.equals("0")&&(this.getArr(i).getPayRefUserEason().equals("001")
								|| this.getArr(i).getPayRefUserEason().equals("002"))
							||strOfferType.equals("102")&&(this.getArr(i).getPayRefUserEason().equals("102"))
						    ||strOfferType.equals("105")&&(this.getArr(i).getPayRefUserEason().equals("102"))
						    ||strOfferType.equals("103")&&(this.getArr(i).getPayRefUserEason().equals("102"))
						    ||strOfferType.equals("801")&&(this.getArr(i).getPayRefUserEason().equals("102"))) {				
							dbPrpJpayInvest.setPayFlag("04");
							dbPrpJpayInvest.setRealPayRefFee(dbPrpJpayInvest
									.getPayRefFee());
						} else {
							dbPrpJpayInvest.setPayFlag("03");
						}
						dbPrpJpayInvest.setRealPayRefNo(strRealPayRefNo);
						dbPrpJpayInvest.update(dbpool);
					}
				}				
			}
			
		}
		
		DBAccBankAccount dbAccBankAccount = new DBAccBankAccount();
		String strBankCode = "";
		String strAccountNo = "";
		if (!strBankItemCode.equals("") && strBankItemCode.trim().length() != 0) {
			String[] arrBankAccount = Str.split(strBankItemCode, "/");
			
			strBankCode = arrBankAccount[0];
			
			strAccountNo = arrBankAccount[1];
			
			dbAccBankAccount.getInfo(dbpool, strAccountNo, iCenterCode);
			String strSaveNature = dbAccBankAccount.getSaveNature();
			
			strBankItemCode = "0" + strSaveNature + "/" + strBankItemCode + "/";
		}
		
		
        for (int m=0;m<blPrpDcompany.getSize();m++ ){
        	String strCenterCode = blPrpDcompany.getArr(m).getComCode();
        	strWherepart="CenterCode='"+strCenterCode
			+"' And RealPayRefNo='"+strRealPayRefNo+"'";
    	    blPrpJpayInvestNew = new BLPrpJpayInvest();
    	    blPrpJpayInvestNew.query(dbpool, strWherepart, 0);
    	    if (blPrpJpayInvestNew.getSize()>0){
        	    
        	    DBPrpJpayRefVoucher dBPrpJpayRefVoucherNew = new DBPrpJpayRefVoucher();
        	    String strRealPayRefNoNew = dBPrpJpayRefVoucherNew.genRealPayRefNo();
        	    dBPrpJpayRefVoucherNew.setRealPayRefNo(strRealPayRefNoNew);
        	    dBPrpJpayRefVoucherNew.setPayRefNoType("B");
        	    dBPrpJpayRefVoucherNew.setCenterCode(iCenterCode);
        	    dBPrpJpayRefVoucherNew.setVoucherDate(iDate);
        		DBPrpDuser dbPrpDuser=new DBPrpDuser();
        		dbPrpDuser.getInfo(dbpool, iOperatorCode);
        		dBPrpJpayRefVoucher.setComCode(dbPrpDuser.getComCode());
        	    dBPrpJpayRefVoucherNew.setPayRefCode(iOperatorCode);
        	    if(iCenterCode.equals(strCenterCode))
        	    	dBPrpJpayRefVoucherNew.setToCenterCode("0");
        	    else
        	    	dBPrpJpayRefVoucherNew.setToCenterCode(strCenterCode);
        	    dBPrpJpayRefVoucherNew.setAttribute2("0");
        	    dBPrpJpayRefVoucherNew.insert(dbpool);
        	    
        		double payRefFeeSum = 0;
        		
        		for (int i = 0; i < blPrpJpayInvestNew.getSize(); i++) {
        			dbPrpJpayInvest = new DBPrpJpayInvest();
        			dbPrpJpayInvest.setSchema(blPrpJpayInvestNew.getArr(i));
        			if (dbPrpJpayInvest.getPayFlag().equals("04")) {
        				dbPrpJpayInvest.setCenterCode(strCenterCode);
        				dbPrpJpayInvest.setBranchCode(strCenterCode);
        				dbPrpJpayInvest.setPlanFeeCNY(dbPrpJpayInvest.getPayRefFee());
        				payRefFeeSum += Double.parseDouble(dbPrpJpayInvest.getPayRefFee());
        			}
        			dbPrpJpayInvest.setRealPayBankDate(iDate);
        			dbPrpJpayInvest.setRealPayRefNo(strRealPayRefNoNew);
        			dbPrpJpayInvest.update(dbpool);
        		}
        		
        		for (int i = 0; i < blPrpJpayInvestNew.getSize(); i++) {
        			prpJpayInvestSchema = new PrpJpayInvestSchema(); 
        			prpJpayInvestSchema = blPrpJpayInvestNew.getArr(i);
        			if (prpJpayInvestSchema.getPayFlag().equals("03")) {
        				paymentflag="03";
        				payRefUserEason=prpJpayInvestSchema.getPayRefUserEason();
        			}else{
        				paymentflag="06";
        				
        				payRefUserEason="";
        				
        			}			
        				String strSql = "update prpjinvest set paymentFlag ='"+paymentflag+"',PayRefUserEason='"
        						+ payRefUserEason
        						+ "' Where RaidNo = '"
        						+ prpJpayInvestSchema.getProcSeq()
        						+ "'  AND CERTINO = '"
        						+ prpJpayInvestSchema.getCertiNo()
        						+ "'  AND CERTITYPE = '"
        						+ prpJpayInvestSchema.getCertiType()
        						+ "'  AND SERIALNO = '"
        						+ prpJpayInvestSchema.getSerialNo()
        						+ "'  AND PAYREFREASON = '"
        						+ prpJpayInvestSchema.getPayRefReason() + "'";
        				dbpool.update(strSql);
        		}
        	    
        		payRefFeeSum = Str.round(Str.round(payRefFeeSum, 8), 2);
        	    DBPrpJpayRefDetail dbPrpJpayRefDetail = new DBPrpJpayRefDetail();
        	    dbPrpJpayRefDetail.setPayWay(iPayWay);
        	    dbPrpJpayRefDetail.setPayRefNo(strRealPayRefNoNew);
        	    dbPrpJpayRefDetail.setPayRefDate(iDate);
        	    dbPrpJpayRefDetail.setPayRefFee(payRefFeeSum+"");
        	    dbPrpJpayRefDetail.setCurrency2(iCurrency);
        	    dbPrpJpayRefDetail.setSerialNo("1");
        	    dbPrpJpayRefDetail.setRealPayRefNo(strRealPayRefNoNew);
        	    dbPrpJpayRefDetail.setBankCode(strBankCode);
        	    dbPrpJpayRefDetail.setAccountNo(strAccountNo);
        	    dbPrpJpayRefDetail.insert(dbpool);
        	    
        	    BLPrpJpaymentVoucher blPrpJpaymentVoucher = new BLPrpJpaymentVoucher();
        	    strVoucherNo = blPrpJpaymentVoucher.callProcedure(dbpool,strRealPayRefNoNew);
        	    strReturn+="  "+strVoucherNo;
            }
    	    }
		return strReturn;
	}	
	/**
	 * @author xushaojiang 20070820 根据银行回执回写满期给付表数据，并生成XXXXX凭证
	 * @param arrProcSeq
	 *            抽单批次号
	 * @param arrCertiNo
	 *            业务号
	 * @param arrCertiType
	 *            业务类型
	 * @param arrSerialNo
	 *            序列号
	 * @param arrPayRefReason
	 *            收付原因	          
	 * @param iOperatorCode
	 *            操作员代码
	 * @throws UserException
	 * @throws Exception
	 */
	public String payRefSingleToVoucher(String iCenterCode, String[] arrProcSeq,
			String[] arrCertiNo, String[] arrCertiType, String[] arrSerialNo,
			String[] arrPayRefReason, String iOperatorCode,
			String iPayWay,String iBankItemCode,String iCurrency,String iPayRefDate ) throws UserException,
			Exception {
		DbPool dbpool = new DbPool();
		String strVoucherNo = "";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			strVoucherNo = this.payRefSingleToVoucher(dbpool, iCenterCode,arrProcSeq,
					arrCertiNo, arrCertiType, arrSerialNo, arrPayRefReason, iOperatorCode,
					iPayWay,iBankItemCode,iCurrency,iPayRefDate);
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
		return strVoucherNo;
	}

	/**
	 * @author xushaojiang 20070820 根据银行回执回写满期给付表数据，并生成XXXXX凭证
	 * @param dbpool
	 *            数据库连接池
	 * @param arrProcSeq
	 *            抽单批次号
	 * @param arrCertiNo
	 *            业务号
	 * @param arrCertiType
	 *            业务类型
	 * @param arrSerialNo
	 *            序列号
	 * @param arrPayRefReason
	 *            收付原因	          
	 * @param iOperatorCode
	 *            操作员代码
	 * @throws UserException
	 * @throws Exception
	 */
	public String payRefSingleToVoucher(DbPool dbpool,String iCenterCode,  String[] arrProcSeq,
			String[] arrCertiNo, String[] arrCertiType, String[] arrSerialNo,
			String[] arrPayRefReason, String iOperatorCode,
			String iPayWay,String iBankItemCode,String iCurrency,String iPayRefDate) throws UserException,
			Exception {
		DBPrpJpayInvest dbPrpJpayInvest = null;
		this.initArr();
		for (int i = 0; i < arrProcSeq.length; i++) {
			dbPrpJpayInvest = new DBPrpJpayInvest();
			dbPrpJpayInvest.getInfo(dbpool, arrProcSeq[i], arrCertiNo[i],
					arrCertiType[i], arrSerialNo[i], arrPayRefReason[i]);
			this.setArr(dbPrpJpayInvest);
		}
		
		DBPrpJpayRefVoucher dBPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
		String strRealPayRefNo = dBPrpJpayRefVoucher.genRealPayRefNo();
		dBPrpJpayRefVoucher.setRealPayRefNo(strRealPayRefNo);
		dBPrpJpayRefVoucher.setPayRefNoType("B");
		dBPrpJpayRefVoucher.setCenterCode(iCenterCode);
		dBPrpJpayRefVoucher.setVoucherDate(iPayRefDate);
		DBPrpDuser dbPrpDuser=new DBPrpDuser();
		dbPrpDuser.getInfo(dbpool, iOperatorCode);
		dBPrpJpayRefVoucher.setComCode(dbPrpDuser.getComCode());
		dBPrpJpayRefVoucher.setPayRefCode(iOperatorCode);
		dBPrpJpayRefVoucher.setToCenterCode("0");
		dBPrpJpayRefVoucher.setAttribute2("0");
		dBPrpJpayRefVoucher.insert(dbpool);

		String strVoucherNo = "";
		PrpJpayInvestSchema prpJpayInvestSchema = null;

		
		DBAccBankAccount dbAccBankAccount = new DBAccBankAccount();
		String strBankCode = "";
		String strAccountNo = "";
		if (!iBankItemCode.equals("") && iBankItemCode.trim().length() != 0) {
			String[] arrBankAccount = Str.split(iBankItemCode, "/");
			
			strBankCode = arrBankAccount[0];
			
			strAccountNo = arrBankAccount[1];
			
			dbAccBankAccount.getInfo(dbpool, strAccountNo, iCenterCode);
		}

		double payRefFeeSum = 0;
		
		for (int i = 0; i < this.getSize(); i++) {
			
			dbPrpJpayInvest = new DBPrpJpayInvest();
			
			dbPrpJpayInvest.setSchema(this.getArr(i));
			dbPrpJpayInvest.setCenterCode(iCenterCode);
			dbPrpJpayInvest.setBranchCode(iCenterCode);
			dbPrpJpayInvest.setPlanFeeCNY(dbPrpJpayInvest.getPayRefFee());
			payRefFeeSum += Double.parseDouble(dbPrpJpayInvest.getPayRefFee());
			dbPrpJpayInvest.setPayFlag("04");
			dbPrpJpayInvest.setRealPayRefFee(dbPrpJpayInvest.getPayRefFee());
			dbPrpJpayInvest.setRealPayBankDate(iPayRefDate);
			dbPrpJpayInvest.setRealPayRefNo(strRealPayRefNo);
			dbPrpJpayInvest.setPayRefDate(iPayRefDate);
			dbPrpJpayInvest.setOperatorCode(iOperatorCode);
			dbPrpJpayInvest.setPayRefUserEason("111");
			dbPrpJpayInvest.setOfferType("111");
			dbPrpJpayInvest.update(dbpool);
		}
		
		for (int i = 0; i < this.getSize(); i++) {
			prpJpayInvestSchema = this.getArr(i);
			String strSql = "update prpjinvest set paymentFlag ='06'"
					+ " Where RaidNo = '" + prpJpayInvestSchema.getProcSeq()
					+ "'  AND CERTINO = '" + prpJpayInvestSchema.getCertiNo()
					+ "'  AND CERTITYPE = '"
					+ prpJpayInvestSchema.getCertiType()
					+ "'  AND SERIALNO = '" + prpJpayInvestSchema.getSerialNo()
					+ "'  AND PAYREFREASON = '"
					+ prpJpayInvestSchema.getPayRefReason() + "'";
			dbpool.update(strSql);
		}
		
		payRefFeeSum = Str.round(Str.round(payRefFeeSum, 8), 2);
		DBPrpJpayRefDetail dbPrpJpayRefDetail = new DBPrpJpayRefDetail();
		dbPrpJpayRefDetail.setPayWay(iPayWay);
		dbPrpJpayRefDetail.setPayRefNo(strRealPayRefNo);
		dbPrpJpayRefDetail.setPayRefDate(iPayRefDate);
		dbPrpJpayRefDetail.setPayRefFee(payRefFeeSum + "");
		dbPrpJpayRefDetail.setCurrency2(iCurrency);
		dbPrpJpayRefDetail.setSerialNo("1");
		dbPrpJpayRefDetail.setRealPayRefNo(strRealPayRefNo);
		dbPrpJpayRefDetail.setBankCode(strBankCode);
		dbPrpJpayRefDetail.setAccountNo(strAccountNo);
		dbPrpJpayRefDetail.insert(dbpool);
		
		BLPrpJpaymentVoucher blPrpJpaymentVoucher = new BLPrpJpaymentVoucher();
		strVoucherNo = blPrpJpaymentVoucher.callProcedure(dbpool,
				strRealPayRefNo);
		return strVoucherNo;
	}
	/**
	 * @desc 生成满期给付实付凭证
	 * @author 徐少将
	 * @param dbpool
	 * @param blPrpJpayInvest
	 *            对象blPrpJpayInvest
	 * @param accMainVoucherSchema
	 *            对象accMainVoucherSchema
	 * @param iOperatorCode
	 *            操作员代码
	 * @throws Exception
	 */
	public void createSubVoucher(DbPool dbpool, BLAccVoucher iBLAccVoucher,
			AccMainVoucherSchema iAccMainVoucherSchema,
			BLPrpJpayInvest blPrpJpayInvest) throws UserException, Exception {
		
		String strRemark = iAccMainVoucherSchema.getVoucherDate() + "满期给付";
		AccSubVoucherSchema accSubVoucherSchema = null;
		for (int i = 0; i < blPrpJpayInvest.getSize(); i++) {
			if (blPrpJpayInvest.getArr(i).getPayFlag().equals("03")) {
				continue;
			} else if (!blPrpJpayInvest.getArr(i).getPayFlag().equals("04")) {
				throw new UserException(-98, -1167,
						"BLPrpJpayInvest.createSubVoucher", "XX："
								+ blPrpJpayInvest.getArr(i).getCertiNo()
								+ "给付状态:"
								+ blPrpJpayInvest.getArr(i).getCurrency2()
								+ " 有误");
			}
			
			for (int j = 0; j < 3; j++) {
				accSubVoucherSchema = new AccSubVoucherSchema();
				accSubVoucherSchema.setAccBookType(iAccMainVoucherSchema
						.getAccBookType());
				accSubVoucherSchema.setAccBookCode(iAccMainVoucherSchema
						.getAccBookCode());
				accSubVoucherSchema.setCenterCode(iAccMainVoucherSchema
						.getCenterCode());
				accSubVoucherSchema.setYearMonth(iAccMainVoucherSchema
						.getYearMonth());
				accSubVoucherSchema.setF26(iAccMainVoucherSchema
						.getBranchCode());
				accSubVoucherSchema.setVoucherNo(iAccMainVoucherSchema
						.getVoucherNo());
				accSubVoucherSchema.setSuffixNo("" + (3 * i + j + 1));
				accSubVoucherSchema.setCurrency(blPrpJpayInvest.getArr(i)
						.getCurrency2());
				accSubVoucherSchema.setF01(blPrpJpayInvest.getArr(i)
						.getClassCode());
				accSubVoucherSchema.setF05(blPrpJpayInvest.getArr(i)
						.getRiskCode());
				accSubVoucherSchema.setRemark(strRemark);
				accSubVoucherSchema.setF27(blPrpJpayInvest.getArr(i)
						.getHandler1Code());
				accSubVoucherSchema.setF28(blPrpJpayInvest.getArr(i)
						.getComCode());
				if (j == 0) { 
					if (!blPrpJpayInvest.getArr(i).getCurrency2().equals("CNY")) {
						throw new UserException(-98, -1167,
								"BLPrpJpayInvest.createSubVoucher", "XX："
										+ blPrpJpayInvest.getArr(i)
												.getCertiNo()
										+ "币种为:"
										+ blPrpJpayInvest.getArr(i)
												.getCurrency2() + " 请查明!");
					}
					accSubVoucherSchema.setItemCode("2611");
					accSubVoucherSchema.setDirectionIdx("00");
					accSubVoucherSchema.setDebitSource(blPrpJpayInvest
							.getArr(i).getPlanFee());
					accSubVoucherSchema.setExchangeRate(blPrpJpayInvest.getArr(
							i).getExchangeRate());
					accSubVoucherSchema.setDebitDest(blPrpJpayInvest.getArr(i)
							.getPlanFee());
				} else if (j == 1) { 
					accSubVoucherSchema.setItemCode("2251");
					accSubVoucherSchema.setDirectionIdx("00");
					accSubVoucherSchema.setDebitSource(blPrpJpayInvest
							.getArr(i).getSumIncomeFee());
					accSubVoucherSchema.setExchangeRate(blPrpJpayInvest.getArr(
							i).getExchangeRate());
					accSubVoucherSchema.setDebitDest(blPrpJpayInvest.getArr(i)
							.getSumIncomeFee());
				} else { 
					accSubVoucherSchema.setItemCode("1002");
					accSubVoucherSchema.setDirectionIdx("01/02/004/");
					accSubVoucherSchema.setCreditSource(blPrpJpayInvest.getArr(
							i).getPayRefFee());
					accSubVoucherSchema.setExchangeRate(blPrpJpayInvest.getArr(
							i).getExchangeRate());
					accSubVoucherSchema.setCreditDest(blPrpJpayInvest.getArr(i)
							.getPayRefFee());
				}
				
				if (Double.parseDouble(accSubVoucherSchema.getDebitDest()) != 0
						|| Double.parseDouble(accSubVoucherSchema
								.getCreditDest()) != 0) {
					iBLAccVoucher.getBLAccSubVoucher().setArr(
							accSubVoucherSchema);
				}
			}
		}
	}
	/**
	 * @author xushaojiang 20080626 解析回执
	 * @param intType
	 *            业务类型
	 * @param iYear
	 *            年
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iPayRefNo
	 *            打包号
	 * @throws UserException
	 * @throws Exception
	 */
	public void ReadMethod(String strOfferType, String iCenterCode, String iYear,
			String iPayRefNo) throws UserException, Exception {
		
		if (strOfferType.equals("0")){
			ReadMethod00(strOfferType, iCenterCode, iYear,
					iPayRefNo);
		}else if(strOfferType.equals("102")){
			ReadMethodICBC(strOfferType, iCenterCode, iYear,
					iPayRefNo);
		}else if(strOfferType.equals("105")){
			ReadMethodCBC(strOfferType, iCenterCode, iYear,
					iPayRefNo);
		}else if(strOfferType.equals("103")){
			ReadMethodABC(strOfferType, iCenterCode, iYear,
					iPayRefNo);
		}else if(strOfferType.equals("801")){
			ReadMethodICBC(strOfferType, iCenterCode, iYear,
					iPayRefNo);
		}else{
			throw new UserException(-98, -1167,
					"BLPrpJpayInvest.payToBank", "报盘类型："
							+ strOfferType
							+ "是不合法的类型，无法解析该类型回执文件");
		}
	}
	/**
	 * @author xushaojiang 20080626 解析非2901工行回执 (Industry and Commercial Bank of China)——“爱存不存”)
	 * @param intType
	 *            业务类型
	 * @param iYear
	 *            年
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iPayRefNo
	 *            打包号
	 * @throws UserException
	 * @throws Exception
	 */
	public void ReadMethodICBC(String strOfferType, String iCenterCode, String iYear,
			String iPayRefNo) throws UserException, Exception {
		String path = SysConst.getProperty("PAYTOBANK_EXCEL_PATH");
		String fileName = iCenterCode + "_"+strOfferType+ "_"  + iPayRefNo + ".txt";
		PrpJpayInvestSchema prpJpayInvestSchema = null;
		try {
			BufferedReader bufread;
			String filepath, read;
			String separator = java.io.File.separator;
			
			filepath = path + separator + iCenterCode + separator+ iYear + separator
					+ fileName;
			FileReader fileread = null;
			try {
				File file = new File(filepath);
				fileread = new FileReader(file);
			} catch (Exception e) {
				throw new Exception("银行对帐单文件不存在！请导入: "+fileName);
			}
			bufread = new BufferedReader(fileread);
		    HashMap hashMap = new HashMap();
	        BLPrpDcode blPrpDcode = new BLPrpDcode();
	  	    String strWherePart1 = " CodeType='PayRefUserEason' And validstatus='1' ";
	        blPrpDcode.query(strWherePart1,0);
	        for (int i = 0; i < blPrpDcode.getSize(); i++)
	  	    {
	  	    	hashMap.put(blPrpDcode.getArr(i).getCodeCName(),blPrpDcode.getArr(i).getCodeCode());  
	        }
			try {
				
					int count=0;
					int dataStart =-1;
					int payAccountNoCol=-1;
					int appliNameCol=-1;
					int realPayRefFeeCol=-1;
					int resultFlagCol=-1;
				while ((read = bufread.readLine()) != null) {
					count++;
					String strUnKnow = read.toString();
					
					if (strUnKnow!=null&&strUnKnow!=""&&strUnKnow.startsWith("序号")){
						dataStart=count+1;
						String strTitle = read.toString();
					    String resultTitle[];
					    resultTitle=strTitle.split("\\|");	
					    for(int i=0;i<resultTitle.length;i++){
					    	if(resultTitle[i].equals("收款账号")){
					    		payAccountNoCol=i;
					    	}else if(resultTitle[i].equals("收款单位名称")){
					    		appliNameCol=i;
					    	}else if(resultTitle[i].equals("金额")){
					    		realPayRefFeeCol=i;
					    	}else if(resultTitle[i].equals("网上银行状态")){
					    		resultFlagCol=i;
					    	}
					    }
					}
					
					if (dataStart!=(-1)&&count>=dataStart){
						String str = read.toString();
					    String result[];
					    result=str.split("\\|");						
					    String resultFlag="";
					    try{
						    resultFlag=hashMap.get(result[resultFlagCol]).toString();
					    }catch(Exception e){
							throw new UserException(-98, -1167,
									"BLPrpJpayInvest.payToBank", "网上银行状态：“"
											+ result[8]
											+ "” 是不合法的状态，无法解析该类型回执文件");
					    }
						prpJpayInvestSchema = new PrpJpayInvestSchema();
						
						prpJpayInvestSchema.setPayAccountNo(result[payAccountNoCol]);
						
						prpJpayInvestSchema.setPayRefNo(iPayRefNo);
						
						prpJpayInvestSchema.setAppliName(result[appliNameCol]);
						
						prpJpayInvestSchema.setRealPayRefFee(result[realPayRefFeeCol]);
						
						prpJpayInvestSchema.setPayRefUserEason(resultFlag);		
						this.setArr(prpJpayInvestSchema);
						
					}					
				}
			}catch (UserException ue) {
				throw ue;
			}catch (Exception e) {
				throw new Exception("银行对帐单内容不符合标准格式！"+e.toString());
			}
		} catch (Exception d) {
			throw d;
		}
	}
	/**
	 * @author xushaojiang 20080626 解析非2901农行回执 ABC（Agriculture Bank of China）——“啊，不存！”

	 * @param intType
	 *            业务类型
	 * @param iYear
	 *            年
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iPayRefNo
	 *            打包号
	 * @throws UserException
	 * @throws Exception
	 */
	public void ReadMethodABC(String strOfferType, String iCenterCode, String iYear,
			String iPayRefNo) throws UserException, Exception {
		String path = SysConst.getProperty("PAYTOBANK_EXCEL_PATH");
		String fileName = iCenterCode + "_"+strOfferType+ "_"  + iPayRefNo + ".txt";
		PrpJpayInvestSchema prpJpayInvestSchema = null;
		try {
			BufferedReader bufread;
			String filepath, read;
			String separator = java.io.File.separator;
			
			filepath = path + separator + iCenterCode + separator+ iYear + separator
					+ fileName;
			FileReader fileread = null;
			try {
				File file = new File(filepath);
				fileread = new FileReader(file);
			} catch (Exception e) {
				throw new Exception("银行对帐单文件不存在！请导入: "+fileName);
			}
			bufread = new BufferedReader(fileread);
		    HashMap hashMap = new HashMap();
	        BLPrpDcode blPrpDcode = new BLPrpDcode();
	  	    String strWherePart1 = " CodeType='PayRefUserEason' And validstatus='1' ";
	        blPrpDcode.query(strWherePart1,0);
	        for (int i = 0; i < blPrpDcode.getSize(); i++)
	  	    {
	  	    	hashMap.put(blPrpDcode.getArr(i).getCodeCName(),blPrpDcode.getArr(i).getCodeCode());  
	        }
			try {
				
				int count=0;
				int dataStart =-1;
				int payAccountNoCol=-1;
				int appliNameCol=-1;
				int realPayRefFeeCol=-1;
				int resultFlagCol=-1;
			while ((read = bufread.readLine()) != null) {
				count++;
				String strUnKnow = read.toString();
				
				if (strUnKnow!=null&&strUnKnow!=""&&strUnKnow.startsWith("序号")){
					dataStart=count+1;
					String strTitle = read.toString();
				    String resultTitle[];
				    resultTitle=strTitle.split("\\|");	
				    for(int i=0;i<resultTitle.length;i++){
				    	if(resultTitle[i].equals("收款账号")){
				    		payAccountNoCol=i;
				    	}else if(resultTitle[i].equals("收款单位名称")){
				    		appliNameCol=i;
				    	}else if(resultTitle[i].equals("金额")){
				    		realPayRefFeeCol=i;
				    	}else if(resultTitle[i].equals("网上银行状态")){
				    		resultFlagCol=i;
				    	}
				    }
				}
				
				if (dataStart!=(-1)&&count>=dataStart){
					String str = read.toString();
				    String result[];
				    result=str.split("\\|");						
				    String resultFlag="";
				    try{
					    resultFlag=hashMap.get(result[resultFlagCol]).toString();
				    }catch(Exception e){
						throw new UserException(-98, -1167,
								"BLPrpJpayInvest.payToBank", "网上银行状态：“"
										+ result[8]
										+ "” 是不合法的状态，无法解析该类型回执文件");
				    }
					prpJpayInvestSchema = new PrpJpayInvestSchema();
					
					prpJpayInvestSchema.setPayAccountNo(result[payAccountNoCol]);
					
					prpJpayInvestSchema.setPayRefNo(iPayRefNo);
					
					prpJpayInvestSchema.setAppliName(result[appliNameCol]);
					
					prpJpayInvestSchema.setRealPayRefFee(result[realPayRefFeeCol]);
					
					prpJpayInvestSchema.setPayRefUserEason(resultFlag);		
					this.setArr(prpJpayInvestSchema);
					
				}					
			}
		}catch (UserException ue) {
				throw ue;
			}catch (Exception e) {
				throw new Exception("银行对帐单内容不符合标准格式！"+e.toString());
			}
		} catch (Exception d) {
			throw d;
		}
	}	
	/**
	 * @author xushaojiang 20080626 解析建行回执 (Construction Bank of China)——“存不存？” 
	 * @param intType
	 *            业务类型
	 * @param iYear
	 *            年
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iPayRefNo
	 *            打包号
	 * @throws UserException
	 * @throws Exception
	 */
	public void ReadMethodCBC(String strOfferType, String iCenterCode, String iYear,
			String iPayRefNo) throws UserException, Exception {
		String path = SysConst.getProperty("PAYTOBANK_EXCEL_PATH");
		String fileName = iCenterCode + "_"+strOfferType+ "_"  + iPayRefNo + ".txt";
		PrpJpayInvestSchema prpJpayInvestSchema = null;
		try {
			BufferedReader bufread;
			String filepath, read;
			String separator = java.io.File.separator;
			
			filepath = path + separator + iCenterCode + separator+ iYear + separator
					+ fileName;
			FileReader fileread = null;
			try {
				File file = new File(filepath);
				fileread = new FileReader(file);
			} catch (Exception e) {
				throw new Exception("银行对帐单文件不存在！请导入: "+fileName);
			}
			bufread = new BufferedReader(fileread);
		    HashMap hashMap = new HashMap();
	        BLPrpDcode blPrpDcode = new BLPrpDcode();
	  	    String strWherePart1 = " CodeType='PayRefUserEason' And validstatus='1' ";
	        blPrpDcode.query(strWherePart1,0);
	        for (int i = 0; i < blPrpDcode.getSize(); i++)
	  	    {
	  	    	hashMap.put(blPrpDcode.getArr(i).getCodeCName(),blPrpDcode.getArr(i).getCodeCode());  
	        }
			try {
				
				int count=0;
				int dataStart =-1;
				int payAccountNoCol=-1;
				int appliNameCol=-1;
				int realPayRefFeeCol=-1;
				int resultFlagCol=-1;
				int ErrorReasonCol=-1;
			while ((read = bufread.readLine()) != null) {
				count++;
				String strUnKnow = read.toString();
				
				if (strUnKnow!=null&&strUnKnow!=""&&strUnKnow.startsWith("序号")){
					dataStart=count+1;
					String strTitle = read.toString();
				    String resultTitle[];
				    resultTitle=strTitle.split("\\|");	
				    for(int i=0;i<resultTitle.length;i++){
				    	if(resultTitle[i].equals("收款账号")){
				    		payAccountNoCol=i;
				    	}else if(resultTitle[i].equals("收款人姓名")){
				    		appliNameCol=i;
				    	}else if(resultTitle[i].equals("金额")){
				    		realPayRefFeeCol=i;
				    	}else if(resultTitle[i].equals("交易结果")){
				    		resultFlagCol=i;
				    	}else if(resultTitle[i].equals("失败原因")){
				    		ErrorReasonCol=i;
				    	}
				    }
				}
				
				if (dataStart!=(-1)&&count>=dataStart){
					String str = read.toString();
					
					if(str!=null&&!str.equals("")){
						String result[];
					    result=str.split("\\|");	
					    String resultLog=result[resultFlagCol];
					    if(resultLog!=null&&!resultLog.equals("")&&resultLog.equals("成功")){
					    	resultLog="处理成功";
					    }else if(resultLog!=null&&!resultLog.equals("")&&resultLog.equals("失败")){
					    	resultLog=result[ErrorReasonCol];
					    }			    	
					    String resultFlag="";
					    try{
						    resultFlag=hashMap.get(resultLog).toString();
					    }catch(Exception e){
							throw new UserException(-98, -1167,
									"BLPrpJpayInvest.payToBank", "网上银行状态：“"
											+ result[8]
											+ "” 是不合法的状态，无法解析该类型回执文件");
					    }
						prpJpayInvestSchema = new PrpJpayInvestSchema();
						
						prpJpayInvestSchema.setPayAccountNo(result[payAccountNoCol]);
						
						prpJpayInvestSchema.setPayRefNo(iPayRefNo);
						
						prpJpayInvestSchema.setAppliName(result[appliNameCol]);
						
						prpJpayInvestSchema.setRealPayRefFee(result[realPayRefFeeCol]);
						
						prpJpayInvestSchema.setPayRefUserEason(resultFlag);		
						this.setArr(prpJpayInvestSchema);
						
					}
				}				    					
			}
		}catch (UserException ue) {
				throw ue;
			}catch (Exception e) {
				throw new Exception("银行对帐单内容不符合标准格式！"+e.toString());
			}
		} catch (Exception d) {
			throw d;
		}
	}	
	/**
	 * @author xushaojiang 20080626 解析2901工行回执 
	 * @param intType
	 *            业务类型
	 * @param iYear
	 *            年
	 * @param iCenterCode
	 *            核算单位代码
	 * @param iPayRefNo
	 *            打包号
	 * @throws UserException
	 * @throws Exception
	 */
	public void ReadMethod00(String strOfferType, String iCenterCode, String iYear,
			String iPayRefNo) throws UserException, Exception {
		String path = SysConst.getProperty("PAYTOBANK_EXCEL_PATH");
		String fileName = iCenterCode + "_" + iPayRefNo + ".txt";
		PrpJpayInvestSchema prpJpayInvestSchema = null;
		try {
			BufferedReader bufread;
			String filepath, read;
			String separator = java.io.File.separator;
			
			filepath = path + separator + iCenterCode + separator+ iYear + separator
					+ fileName;
			FileReader fileread = null;
			try {
				File file = new File(filepath);
				fileread = new FileReader(file);
			} catch (Exception e) {
				throw new Exception("银行对帐单文件不存在！请导入: "+fileName);
			}
			bufread = new BufferedReader(fileread);
			try {
				while ((read = bufread.readLine()) != null) {
					String str = read.toString();
					prpJpayInvestSchema = new PrpJpayInvestSchema();
					
					prpJpayInvestSchema.setPayAccountNo(str.trim().substring(
							16, 35));
					
					prpJpayInvestSchema.setPayRefNo(iPayRefNo);
					
					prpJpayInvestSchema.setAppliName(str.trim().substring(189)
							.trim());
					

					prpJpayInvestSchema.setPayRefUserEason(str.trim()
							.substring(0, 3));

					this.setArr(prpJpayInvestSchema);
				}
			} catch (Exception e) {
				throw new Exception("银行对帐单内容不符合标准格式！");
			}
		} catch (Exception d) {
			throw d;
		}
	}
	/**
	 * 带dbpool的save方法
	 * 
	 * @param 无
	 * @return 无
	 */
	public void save(DbPool dbpool) throws Exception {
		DBPrpJpayInvest dbPrpJpayInvest = new DBPrpJpayInvest();
		int i = 0;
		for (i = 0; i < schemas.size(); i++) {
			dbPrpJpayInvest.setSchema((PrpJpayInvestSchema) schemas.get(i));
			dbPrpJpayInvest.insert(dbpool);
		}
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
	
    /**
	 * 根据payrefno查询 张灵建 20080820
	 * @param dbpool 数据库连接池
     * @param payRefNo 打包号
	 * @throws UserException
	 * @throws Exception
	 */
	public BLPrpJpayInvest findPayRefNo(DbPool dbpool,String payRefNo) throws UserException, Exception {
		BLPrpJpayInvest blPrpJpayInvest = new BLPrpJpayInvest();
		PrpJpayInvestSchema prpJpayInvestSchema = null;
		
		String sql = " SELECT AppliName,PayAccountNo,OperatorCode,PayRefDate,SUM(payreffee) AS payreffee FROM prpjpayinvest WHERE payrefno='"
			+ payRefNo + "' group by AppliName,PayAccountNo,OperatorCode,PayRefDate ";
		ResultSet resultSet = null;
		try
		{
			resultSet = dbpool.query(sql);
			while(resultSet.next())
            {
				prpJpayInvestSchema = new PrpJpayInvestSchema();
				prpJpayInvestSchema.setAppliName(resultSet.getString("AppliName"));
				prpJpayInvestSchema.setPayAccountNo(resultSet.getString("PayAccountNo"));
				prpJpayInvestSchema.setPayRefFee(resultSet.getString("payreffee"));
				prpJpayInvestSchema.setOperatorCode(resultSet.getString("OperatorCode"));
				prpJpayInvestSchema.setPayRefDate(""+resultSet.getDate("PayRefDate"));
				blPrpJpayInvest.setArr(prpJpayInvestSchema);
            }
		}catch(Exception e){
        	e.printStackTrace();
        	throw e;
        }finally {
        	if(resultSet != null)
        		resultSet.close();
        }
        return blPrpJpayInvest;
	}
}
