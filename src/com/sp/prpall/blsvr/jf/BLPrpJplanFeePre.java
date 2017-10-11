package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.indiv.ci.blsvr.BLCIEndorValid;
import com.sp.indiv.ci.blsvr.BLCIInsureDemand;
import com.sp.payment.dbsvr.jf.DBPrpJpoaInfo;
import com.sp.prpall.blsvr.misc.BLPrpCarRelation;
import com.sp.prpall.blsvr.pg.BLPrpPcarshipTax;
import com.sp.prpall.blsvr.pg.BLPrpPfee;
import com.sp.prpall.blsvr.pg.BLPrpPmainSub;
import com.sp.prpall.blsvr.tb.BLPrpTbatch;
import com.sp.prpall.blsvr.tb.BLPrpTmainSub;
import com.sp.prpall.blsvr.tb.BLPrpTplan;
import com.sp.prpall.blsvr.tb.BLPrpTitemCar;
import com.sp.prpall.dbsvr.cb.DBPrpCcarshipTax;
import com.sp.prpall.dbsvr.cb.DBPrpCinsured;
import com.sp.prpall.dbsvr.cb.DBPrpCitemCar;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFeePre;
import com.sp.prpall.dbsvr.pg.DBPrpPcarshipTax;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;
import com.sp.prpall.dbsvr.pg.DBPrpPjfcdLogMsg;
import com.sp.prpall.dbsvr.pg.DBPrpPmain;
import com.sp.prpall.dbsvr.cb.DBPrpCjfcdLogMsg;
import com.sp.prpall.dbsvr.tb.DBPrpTcarshipTax;
import com.sp.prpall.dbsvr.tb.DBPrpTitemCar;
import com.sp.prpall.dbsvr.tb.DBPrpTmain;
import com.sp.prpall.schema.PrpJpayRefRecSchema;
import com.sp.prpall.schema.PrpJplanFeePreSchema;
import com.sp.prpall.schema.PrpJplanFeeSchema;
import com.sp.prpall.schema.PrpPmainSubSchema;
import com.sp.prpall.schema.PrpTmainSubSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.utiall.blsvr.BLPrpDagent;
import com.sp.utiall.blsvr.BLPrpDcompany;
import com.sp.utiall.blsvr.BLPrpDuser;
import com.sp.utiall.dbsvr.DBPrpDagent;
import com.sp.utiall.dbsvr.DBPrpDcompany;
import com.sp.utiall.dbsvr.DBPrpDuser;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;
import com.sp.visa.interf.VisaInterfaceForPrpAction;


/**
 * 定义PrpJplanFeePre-预收款信息表的BL类
 * <p> Copyright: Copyright (c) 2007 </p>
 * <p> @createdate 2007-11-30 </p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJplanFeePre {
	private List schemas = new ArrayList();
    protected final Log logger = LogFactory.getLog(getClass());
	public static Map certiTypeNameMap  = new HashMap();
	public static Map certiStatusNameMap  = new HashMap();
	public static Map posTypeNameMap  = new HashMap();
	static {
		initCertiTypeNameMap();
		initCertiStatusNameMap();
		initPoaTypeNameMap();
	}
	/**
	 * 构造函数
	 */
	public BLPrpJplanFeePre() {
	}
	/**
	 * @desc 初始化业务代码与名称Map
	 *
	 */
	private static synchronized void initCertiTypeNameMap(){
		certiTypeNameMap.put("T", "XX单");
		certiTypeNameMap.put("E", "批单");
	}
	/**
	 * @desc 初始化业务状态代码与名称Map
	 *
	 */
	private static synchronized void initCertiStatusNameMap(){
		certiStatusNameMap.put("0", "初始状态");
		certiStatusNameMap.put("1", "未到帐");
		certiStatusNameMap.put("2", "已到账");
		certiStatusNameMap.put("3", "已生成XX");
		certiStatusNameMap.put("4", "收款成功未数据校验");
		certiStatusNameMap.put("5", "已到账");
		certiStatusNameMap.put("6", "校验失败");
		certiStatusNameMap.put("7", "已到账");
		certiStatusNameMap.put("8", "已退款");
		certiStatusNameMap.put("9", "已撤单");
	}
	/**
	 * @desc 初始化交易方式代码与名称Map
	 *
	 */
	private static synchronized void initPoaTypeNameMap(){
		posTypeNameMap.put("1", "代理冲减预存款");
		posTypeNameMap.put("2", "POS");
		posTypeNameMap.put("3", "支票");
		posTypeNameMap.put("4", "划账");
		posTypeNameMap.put("5", "现金");
		
		posTypeNameMap.put("7", "快钱垫付");
		
		
	}
	/**
	 * @desc 获取代码对应名称
	 * @param errorCode
	 * @return
	 */
	public static String translateCodeToName(String codeType,String codeValue){
		String nameValue = "";
		if(codeValue==null||codeValue.equals(""))
			return nameValue;
		if(codeType!=null&&codeType.equals("CertiType")){
			nameValue = (String)certiTypeNameMap.get(codeValue);
		}else if(codeType!=null&&codeType.equals("CertiStatus")){
			nameValue = (String)certiStatusNameMap.get(codeValue);
		}else if(codeType!=null&&codeType.equals("PoaType")){
			nameValue = (String)posTypeNameMap.get(codeValue);
		}else{
			nameValue = "未知";
		}
		if(nameValue==null||nameValue.equals(""))
			nameValue="未知";
		return nameValue;
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
	 * 增加一条PrpJplanFeePreSchema记录
	 * 
	 * @param iPrpJplanFeePreSchema PrpJplanFeePreSchema
	 * @throws Exception
	 */
	public void setArr(PrpJplanFeePreSchema iPrpJplanFeePreSchema)
			throws Exception {
		try {
			schemas.add(iPrpJplanFeePreSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到一条PrpJplanFeePreSchema记录
	 * 
	 * @param index 下标
	 * @return 一个PrpJplanFeePreSchema对象
	 * @throws Exception
	 */
	public PrpJplanFeePreSchema getArr(int index) throws Exception {
		PrpJplanFeePreSchema prpJplanFeePreSchema = null;
		try {
			prpJplanFeePreSchema = (PrpJplanFeePreSchema) this.schemas
					.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJplanFeePreSchema;
	}

	/**
	 * 删除一条PrpJplanFeePreSchema记录
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
		DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
		if (iLimitCount > 0
				&& dbPrpJplanFeePre.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJplanFeePre.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJplanFeePre WHERE "
					+ iWherePart;
			schemas = dbPrpJplanFeePre.findByConditions(strSqlStatement);
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
		DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
		if (iLimitCount > 0
				&& dbPrpJplanFeePre.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJplanFeePre.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJplanFeePre WHERE "
					+ iWherePart;
			schemas = dbPrpJplanFeePre
					.findByConditions(dbpool, strSqlStatement);
		}
	}
	/**
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象(使用绑定变量)
	 * @param dbpool
	 * @param iWherePart
	 * @param iLimitCount
	 * @param bindValues
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, int iLimitCount,ArrayList bindValues)
	throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
		if (iLimitCount > 0
				&& dbPrpJplanFeePre.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJplanFeePre.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJplanFeePre WHERE "+ iWherePart;
			schemas = dbPrpJplanFeePre.findByConditions(dbpool,strSqlStatement,bindValues);
		}
	}

	/**
	 * 带dbpool的save方法
	 * @param 无
	 * @return 无
	 */
	public void save(DbPool dbpool) throws Exception {
		DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
		int i = 0;
		for (i = 0; i < schemas.size(); i++) {
			dbPrpJplanFeePre.setSchema((PrpJplanFeePreSchema) schemas.get(i));
			dbPrpJplanFeePre.insert(dbpool);
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

        
        
        
        String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
        if("0".equals(payment_switch)){
        	dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        }else{
        	dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
        }
        
        

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
	 * @param certiNo certiNo
	 * @param serialNo serialNo
	 * @return 无
	 */
	public void cancel(DbPool dbpool, String certiNo, String serialNo)
			throws Exception {
		DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
		dbPrpJplanFeePre.delete(certiNo, serialNo);
	}

	/**
	 * 不带dbpool的删除方法
	 * 
	 * @param certiNo certiNo
	 * @param serialNo serialNo
	 * @return 无
	 */
	public void cancel(String certiNo, String serialNo) throws Exception {
		DbPool dbpool = new DbPool();

        
        
        
        
        String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
        if("0".equals(payment_switch)){
        	dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        }else{
        	dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
        }
        
        
		try {
			dbpool.beginTransaction();
			cancel(dbpool, certiNo, serialNo);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}

	/**
	 * 带dbpool根据certiNo,serialNo获取数据
	 * 
	 * @param certiNo certiNo
	 * @param serialNo serialNo
	 * @return 无
	 */
	public void getData(String certiNo, String serialNo) throws Exception {
		DbPool dbpool = new DbPool();
        
        
        
		
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
		getData(dbpool, certiNo, serialNo);
		dbpool.close();
	}

	/**
	 * 不带dbpool根据certiNo,serialNo获取数据
	 * 
	 * @param dbpool 连接池
	 * @param certiNo certiNo
	 * @param serialNo serialNo
	 * @return 无
	 */
	public void getData(DbPool dbpool, String certiNo, String serialNo)
			throws Exception {
		
		String strWherePart = "";
		
		ArrayList bindValues = new ArrayList();
		
		
		strWherePart = " CertiNo= ? AND SerialNo= ? ";
		bindValues.add(certiNo);
		bindValues.add(serialNo);
		this.query(dbpool,strWherePart,0,bindValues);
		
		
	}

	/**
	 * @desc 双核通过送收付预收款信息表数据接口-不带DbPool
	 * @author liufengyao
	 * @param iCertiType 业务类型
	 * @param iCertiNo 业务号
	 * @throws UserException
	 * @throws Exception
	 */
	public void transPreData(String iCertiType, String iCertiNo)
			throws UserException, Exception {
		DbPool dbpool = new DbPool();
		try {
            
            
            
			
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
            
			dbpool.beginTransaction();
			this.transPreData(dbpool, iCertiType, iCertiNo);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
			throw e;
		} finally {
			dbpool.close();
		}
	}

	/**
	 * @desc 双核通过送收付预收款信息表数据接口-带DbPool
	 * @author liufengyao
	 * @param dbpool 数据库连接池
	 * @param iCertiType 业务类型
	 * @param iCertiNo 业务号
	 * @throws UserException
	 * @throws Exception
	 */

	public void transPreData(DbPool dbpool, String iCertiType, String iCertiNo)
			throws UserException, Exception {
	    
	    if(true)
	      throw new UserException(-98,-1167,"BLPrpJplanFeePre.transPreData","该方法已被删除！");
	    
		
		if (iCertiType.equals("T"))
			this.transPreProposal(dbpool, iCertiNo);
		
		else if (iCertiType.equals("E"))
			this.transPreEndor(dbpool, iCertiNo);
		else
			throw new UserException(-98, -1167, this.getClass().getName(),
					"业务类型：" + iCertiType + "不满足预收款转入条件");
		
		this.save(dbpool);
	}

	/**
	 * @desc 双核通过，转入已核XX单数据
	 * @author liufengyao
	 * @param dbpool
	 * @param iCertiNo
	 * @throws UserException
	 * @throws Exception
	 */
	public void transPreProposal(DbPool dbpool, String iProposalNo)
			throws UserException, Exception {
		
		DBPrpTmain dbPrpTmain = new DBPrpTmain();
		BLPrpTplan blPrpTplan = new BLPrpTplan();
		BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
		DBPrpTcarshipTax dbPrpTcarshipTax = new DBPrpTcarshipTax();
		BLCIInsureDemand blCIInsureDemand = new BLCIInsureDemand();
		
		PrpJplanFeePreSchema schema = null;
		String strWherePart = "";
		int intReturn = 0;
		int serialNo = 0;
		double carShipTax = 0.00;
		double totalFee = 0.00;
		DateTime currentDetailTime = new DateTime(new Date(),DateTime.YEAR_TO_SECOND);
		
		
		String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
		if(!"1".equals(strSwitch)){
		    blCIInsureDemand.getData(dbpool, iProposalNo);
		}else{
		    BLPrpTitemCar blprpTitemCar = new BLPrpTitemCar();
		    blprpTitemCar.getData(iProposalNo);
		  
			ArrayList iWhereValue=new ArrayList();
			iWhereValue.add(blprpTitemCar.getArr(0).getDemandNo());
			blCIInsureDemand.query(dbpool," DemandNo = ?",iWhereValue,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
			
		}
		
		String strProConfirmSequenceNo = "";
		if(blCIInsureDemand.getSize()!=0){
			strProConfirmSequenceNo = blCIInsureDemand.getArr(0).getProconfirmSequenceNo();
		}
		
		intReturn = dbPrpTmain.getInfo(dbpool, iProposalNo);
		if (intReturn == 100)
			throw new UserException(-98, -1167,
					"BLPrpJplanFeePre.transPreProposal()", "无此已核XX单信息："+ iProposalNo);
		
		String centercode = blPrpDcompany.getCenterCodeNew(dbpool, dbPrpTmain.getComCode());
		
		DBPrpTitemCar dbPrpTitemCar = new DBPrpTitemCar();
		String licenseNo = "";
		String engineNo = "";
		String brandName = "";
		String frameNo  = "";
		if (dbPrpTitemCar.getInfo(dbpool, iProposalNo, "1") == 0) {
			licenseNo = dbPrpTitemCar.getLicenseNo();
			engineNo = dbPrpTitemCar.getEngineNo();
			brandName = dbPrpTitemCar.getBrandName();
			frameNo = dbPrpTitemCar.getFrameNo();
		}
		
		strWherePart = "ProposalNo = '" + iProposalNo + "' ";
		blPrpTplan.query(dbpool, strWherePart, 0);
		this.initArr();
		for (int i = 0; i < blPrpTplan.getSize(); i++) {
			schema = new PrpJplanFeePreSchema();
			schema.setCertiType("T");
			schema.setCertiNo(iProposalNo);
			schema.setSerialNo(Integer.toString(serialNo++));
			schema.setPlanSerialNo(blPrpTplan.getArr(i).getSerialNo());
			schema.setPolicyNo("");
			schema.setPayRefReason(blPrpTplan.getArr(i).getPayReason());
			schema.setClassCode(dbPrpTmain.getClassCode());
			schema.setRiskCode(dbPrpTmain.getRiskCode());
			schema.setContractNo(dbPrpTmain.getContractNo());
			schema.setAppliCode(dbPrpTmain.getAppliCode());
			schema.setAppliName(dbPrpTmain.getAppliName());
			schema.setInsuredCode(dbPrpTmain.getInsuredCode());
			schema.setInsuredName(dbPrpTmain.getInsuredName());
			schema.setStartDate(dbPrpTmain.getStartDate());
			schema.setEndDate(dbPrpTmain.getEndDate());
			
			schema.setValidDate(dbPrpTmain.getStartDate());
			schema.setComCode(dbPrpTmain.getComCode());
			schema.setMakeCom(dbPrpTmain.getMakeCom());
			schema.setAgentCode(dbPrpTmain.getAgentCode());
			schema.setHandler1Code(dbPrpTmain.getHandler1Code());
			schema.setHandlerCode(dbPrpTmain.getHandlerCode());
			schema.setUnderWriteDate(dbPrpTmain.getUnderWriteEndDate());
			schema.setPayNo(blPrpTplan.getArr(i).getPayNo());
			schema.setCenterCode(centercode);
			schema.setCurrency1(blPrpTplan.getArr(i).getCurrency());
			schema.setPlanDate(blPrpTplan.getArr(i).getPlanDate());
			schema.setExchangeRate("1.0");
			schema.setPrePayRefFee("0.00");
			carShipTax = 0.00;
			if (dbPrpTmain.getRiskCode().equals("0507")) {
				int taxReturn = 0;
				taxReturn = dbPrpTcarshipTax.getInfo(dbpool, iProposalNo,blPrpTplan.getArr(i).getSerialNo());
				if (taxReturn == 0) {
					
					carShipTax = Double.parseDouble(ChgData.chgStrZero(dbPrpTcarshipTax.getTaxActual()))
						       + Double.parseDouble(ChgData.chgStrZero(dbPrpTcarshipTax.getPreviousPay()))
							   + Double.parseDouble(ChgData.chgStrZero(dbPrpTcarshipTax.getLateFee()));
				}
			}
			
			schema.setPremiumPlanFee(blPrpTplan.getArr(i).getPlanFee());
			
			schema.setCSTaxPlanFee(Double.toString(carShipTax));
			totalFee = Double.parseDouble(ChgData.chgStrZero(blPrpTplan.getArr(i).getPlanFee()))
					+ carShipTax;
			
			schema.setPlanFee(Double.toString(totalFee));
			
			schema.setCertiStatus("0");
			schema.setOperateDate(currentDetailTime.toString());
			schema.setPoaType("");
			schema.setPoaCode("");
			schema.setLicenseNo(licenseNo);
			schema.setEngineNo(engineNo);
			schema.setBrandName(brandName);
			schema.setFrameNo(frameNo);
			
			schema.setBusinessNature(dbPrpTmain.getBusinessNature());
			
			
			schema.setProConfirmSequenceNo(strProConfirmSequenceNo);
			schema.setPayMethod(dbPrpTmain.getPayMethod());
			
			this.setArr(schema);
		}
	}

	/**
	 * @desc 双核通过，转入已核批单数据(批增)
	 * @author liufengyao
	 * @param dbpool
	 * @param iCertiNo
	 * @throws UserException
	 * @throws Exception
	 */
	public void transPreEndor(DbPool dbpool, String iEndorseNo)
			throws UserException, Exception {
		
		DBPrpPhead dbPrpPhead = new DBPrpPhead();
		DBPrpCmain dbPrpCmain = new DBPrpCmain();
        DBPrpPcarshipTax dbPrpPcarshipTax = new DBPrpPcarshipTax();
		BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
		BLPrpPfee blPrpPfee = new BLPrpPfee();
		PrpJplanFeePreSchema schema = null;
		BLCIEndorValid blCIEndorValid = new BLCIEndorValid();
		
		String strWherePart = "";
		int intReturn = 0;
		int serialNo = 0;
        double carShipTax = 0.00;
        double totalFee = 0.00;
		DateTime currentDetailTime = new DateTime(new Date(),DateTime.YEAR_TO_SECOND);
		intReturn = dbPrpPhead.getInfo(dbpool, iEndorseNo);
		if (intReturn == 100)
			throw new UserException(-98, -1167, this.getClass().getName(),
					"无此批单信息：" + iEndorseNo);
		intReturn = dbPrpCmain.getInfo(dbpool, dbPrpPhead.getPolicyNo());
		if (intReturn == 100)
			throw new UserException(-98, -1167, this.getClass().getName(),
					"该批单无对应XX信息：" + dbPrpPhead.getPolicyNo());
		String centercode = blPrpDcompany.getCenterCodeNew(dbpool, dbPrpCmain
				.getComCode());
		
		blCIEndorValid.query(dbpool, " EndorseNo='" + iEndorseNo + "'");
		String strProConfirmSequenceNo = "";
		if(blCIEndorValid.getSize()!=0){
			strProConfirmSequenceNo = blCIEndorValid.getArr(0).getDemandNo();
		}
		
		
		DBPrpCitemCar dbPrpCitemCar = new DBPrpCitemCar();
		String licenseNo = "";
		String engineNo = "";
		String brandName = "";
		String frameNo  = "";
		if (dbPrpCitemCar.getInfo(dbpool, dbPrpPhead.getPolicyNo(), "1") == 0) {
			licenseNo = dbPrpCitemCar.getLicenseNo();
			engineNo = dbPrpCitemCar.getEngineNo();
			brandName = dbPrpCitemCar.getBrandName();
			frameNo = dbPrpCitemCar.getFrameNo();
		}
		strWherePart = "EndorseNo='" + iEndorseNo + "' AND ChgPremium1 > 0";
		blPrpPfee.querySumByCurrency1(dbpool, strWherePart);
		if (blPrpPfee.getSize() > 1)
			throw new Exception("批单" + iEndorseNo + "存在多个支付币别,无法生成预收款数据！");
		
		this.initArr();
		
		if (blPrpPfee.getSize() == 0 && dbPrpPhead.getRiskCode().equals("0507")) {
            DBPrpCcarshipTax dbPrpCcarshipTax = new DBPrpCcarshipTax();
            BLPrpPcarshipTax blPrpPcarshipTax = new BLPrpPcarshipTax();
            DBPrpCinsured dbPrpCinsured = new DBPrpCinsured();
            dbPrpCitemCar.getInfo(dbpool, dbPrpPhead.getPolicyNo(), "1");
            String strSQl = " EndorseNo='" + iEndorseNo + "'";
            blPrpPcarshipTax.query(dbpool, strSQl);
            if (blPrpPcarshipTax.getSize() != 0) {
                dbPrpCcarshipTax.getInfo(dbpool, blPrpPcarshipTax.getArr(0).getPolicyNo(),
                    blPrpPcarshipTax.getArr(0).getSerialNo());
                dbPrpCinsured.getInfo(dbpool, blPrpPcarshipTax.getArr(0).getPolicyNo(),
                    blPrpPcarshipTax.getArr(0).getSerialNo());
                schema = new PrpJplanFeePreSchema();
                schema.setCertiType("E");
                schema.setCertiNo(iEndorseNo);
                schema.setSerialNo(Integer.toString(serialNo++));
                schema.setPlanSerialNo("1");
                schema.setPolicyNo(dbPrpPhead.getPolicyNo());
                schema.setPayRefReason("R30");
                schema.setClassCode(dbPrpCmain.getClassCode());
                schema.setRiskCode(dbPrpCmain.getRiskCode());
                schema.setContractNo(dbPrpCmain.getContractNo());
                schema.setAppliCode(dbPrpCmain.getAppliCode());
                schema.setAppliName(dbPrpCmain.getAppliName());
                schema.setInsuredCode(dbPrpCmain.getInsuredCode());
                schema.setInsuredName(dbPrpCmain.getInsuredName());
                schema.setStartDate(dbPrpPhead.getValidDate());
                schema.setEndDate(dbPrpCmain.getEndDate());
                schema.setValidDate(dbPrpPhead.getValidDate());
                schema.setPayNo("1");
                schema.setComCode(dbPrpCmain.getComCode());
                schema.setMakeCom(dbPrpCmain.getMakeCom());
                schema.setAgentCode(dbPrpCmain.getAgentCode());
                schema.setHandler1Code(dbPrpCmain.getHandler1Code());
                schema.setHandlerCode(dbPrpCmain.getHandlerCode());
                schema.setUnderWriteDate(dbPrpPhead.getUnderWriteEndDate());
                schema.setCenterCode(centercode);
                schema.setCurrency1("CNY");
                schema.setPlanDate(dbPrpPhead.getValidDate());
                schema.setExchangeRate("1.0");
                schema.setPrePayRefFee("0.00");
                carShipTax = 0.00;
                
                carShipTax = Double.parseDouble(ChgData.chgStrZero(blPrpPcarshipTax.getArr(0).getChgSumPayTax()));
                
                schema.setPremiumPlanFee("0");
                
                schema.setCSTaxPlanFee(Double.toString(carShipTax));
                totalFee = carShipTax;
                schema.setPlanFee(Double.toString(totalFee));
                
                schema.setCertiStatus("0");
                schema.setOperateDate(currentDetailTime.toString());
                schema.setPoaType("");
                schema.setPoaCode("");
                schema.setLicenseNo(licenseNo);
                schema.setEngineNo(engineNo);
                schema.setBrandName(brandName);
                schema.setFrameNo(frameNo);
                
    			schema.setBusinessNature(dbPrpCmain.getBusinessNature());
    			
    			
    			schema.setProConfirmSequenceNo(strProConfirmSequenceNo);
    			schema.setPayMethod(dbPrpCmain.getPayMethod());
    			
                this.setArr(schema);
            }
            
	 }else {
		for (int i = 0; i < blPrpPfee.getSize(); i++) {
			schema = new PrpJplanFeePreSchema();
			schema.setCertiType("E");
			schema.setCertiNo(iEndorseNo);
			schema.setSerialNo(Integer.toString(serialNo++));
			schema.setPlanSerialNo(""+(i+1));
			schema.setPolicyNo(dbPrpPhead.getPolicyNo());
			schema.setPayRefReason("R30");
			schema.setClassCode(dbPrpCmain.getClassCode());
			schema.setRiskCode(dbPrpCmain.getRiskCode());
			schema.setContractNo(dbPrpCmain.getContractNo());
			schema.setAppliCode(dbPrpCmain.getAppliCode());
			schema.setAppliName(dbPrpCmain.getAppliName());
			schema.setInsuredCode(dbPrpCmain.getInsuredCode());
			schema.setInsuredName(dbPrpCmain.getInsuredName());
			schema.setStartDate(dbPrpPhead.getValidDate());
			schema.setEndDate(dbPrpCmain.getEndDate());
			schema.setValidDate(dbPrpPhead.getValidDate());
			schema.setPayNo("1");
			schema.setComCode(dbPrpCmain.getComCode());
			schema.setMakeCom(dbPrpCmain.getMakeCom());
			schema.setAgentCode(dbPrpCmain.getAgentCode());
			schema.setHandler1Code(dbPrpCmain.getHandler1Code());
			schema.setHandlerCode(dbPrpCmain.getHandlerCode());
			schema.setUnderWriteDate(dbPrpPhead.getUnderWriteEndDate());
			schema.setCenterCode(centercode);
			schema.setCurrency1(blPrpPfee.getArr(i).getCurrency1());
			schema.setPlanDate(dbPrpPhead.getValidDate());
			schema.setExchangeRate("1.0");
			schema.setPrePayRefFee("0.00");			
			carShipTax = 0.00;
			if (dbPrpCmain.getRiskCode().equals("0507")) {
				int taxReturn = 0;
		        intReturn = dbPrpPcarshipTax.getInfo(dbpool,iEndorseNo,Integer.toString(i+1));
				if (taxReturn == 0) {
					
				    carShipTax = Double.parseDouble(ChgData.chgStrZero(dbPrpPcarshipTax.getChgSumPayTax()));
				    
				}
			}
			
			schema.setPremiumPlanFee(blPrpPfee.getArr(i).getChgPremium1());
			
			schema.setCSTaxPlanFee(Double.toString(carShipTax));
			totalFee = Double.parseDouble(ChgData.chgStrZero(blPrpPfee.getArr(i).getChgPremium1()))+ carShipTax;
			
			schema.setPlanFee(Double.toString(totalFee));
			
			schema.setCertiStatus("0");
			schema.setOperateDate(currentDetailTime.toString());
			schema.setPoaType("");
			schema.setPoaCode("");
			schema.setLicenseNo(licenseNo);
			schema.setEngineNo(engineNo);
			schema.setBrandName(brandName);
			schema.setFrameNo(frameNo);
			
			schema.setBusinessNature(dbPrpCmain.getBusinessNature());
			
			
			schema.setProConfirmSequenceNo(strProConfirmSequenceNo);
			schema.setPayMethod(dbPrpCmain.getPayMethod());
			
			this.setArr(schema);
		}
	      }
	}
    /**
     * @desc 根据本次预收款的商业XXXXXXX单号列表（除交强XXXXX外的XXXXX）和交强XXXXXXX号列表，校验商业XXXXX关联的交强XXXXX是否已经全部缴费。
     * @param list0507
     * @param listOther05
     * @return 关联交强XXXXX未缴费的商业XXXXXXX单号集合
     * @throws Exception
     */
	public ArrayList getNoPayBusinessBy0507(ArrayList list0507,ArrayList listOther05)
	throws Exception{
		ArrayList noPayListOther05 = new ArrayList();
		DbPool dbpool = new DbPool();
		try {
            
            
            
            
            
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
			noPayListOther05 = this.getNoPayBusinessBy0507(dbpool, list0507, listOther05);
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
		return noPayListOther05;
	}
    /**
     * @desc 根据本次预收款的商业XXXXXXX单号列表（除交强XXXXX外的XXXXX）和交强XXXXXXX号列表，校验商业XXXXX关联的交强XXXXX是否已经全部缴费。
     * @param list0507
     * @param listOther05
     * @return 关联交强XXXXX未缴费的商业XXXXXXX单号集合
     * @throws Exception
     */
	public ArrayList getNoPayBusinessBy0507(DbPool dbPool,ArrayList list0507,ArrayList listOther05)
	throws Exception{
		ArrayList noPayListOther05 = new ArrayList();
		String proposalNo05 = "";
		String proposalNo0507 = "";
		String strCond05Other = "('";
		String strCond0507 = "";
		if(list0507==null||list0507.size()==0){
			strCond0507 = "('1')";
		}else{
			strCond0507 = "('";
		}
		for(int i=0;i<list0507.size();i++){
			if(i==list0507.size()-1)
				strCond0507 += (String)list0507.get(i)+"')";
			else
				strCond0507 += (String)list0507.get(i)+"','";
		}
		if(listOther05==null||listOther05.size()==0){
			strCond05Other += "')";
		}
		for(int j=0;j<listOther05.size();j++){
			if(j==listOther05.size()-1)
				strCond05Other += (String)listOther05.get(j)+"')";
			else
				strCond05Other += (String)listOther05.get(j)+"','";
		}
		
		String condition = " ProposalNo in "+strCond05Other+" AND MainPolicyNo NOT IN  "+strCond0507
		                 + " AND MainPolicyNo like 'T%' AND substr(Flag,1,1) = '1' AND (substr(Flag,4,1) IS NULL OR substr(Flag,4,1)!='1')";
		BLPrpTmainSub blPrpTmainSub  = new BLPrpTmainSub();
		PrpTmainSubSchema prpTmainSubSchema = null;
		blPrpTmainSub.query(dbPool,condition);
		for(int k=0;k<blPrpTmainSub.getSize();k++){
			prpTmainSubSchema = blPrpTmainSub.getArr(k);
			proposalNo05 = prpTmainSubSchema.getProposalNo();
			proposalNo0507 = prpTmainSubSchema.getMainPolicyNo();
			noPayListOther05.add(proposalNo05+"-"+proposalNo0507);
		}
		return noPayListOther05;
	} 
	/**
	 * 查询PrpJpalnFeePre表信息，schemas内包括拓展的信息，如归属机构名称，代理人名称等
	 * @param conditions
	 * @author 徐光远 
	 * @version 1.0 Dec 4, 2007 2:48:45 PM
	 * @throws Exception 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public void findExpendSchemas(String conditions, int limitCount) throws Exception {
        DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
        DBPrpDagent dbPrpDagent = new DBPrpDagent();
        DBPrpDuser dbPrpDuser = new DBPrpDuser();
		DbPool dbpool = new DbPool();
        
        
        
		
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
		try {
			query(dbpool, conditions, limitCount);
			if (!schemas.isEmpty()) {
				for (Iterator iterator = schemas.iterator(); iterator.hasNext();) {
					PrpJplanFeePreSchema schema = (PrpJplanFeePreSchema) iterator.next();
					if (StringUtils.isNotEmpty(schema.getComCode())) {
						
						dbPrpDcompany.getInfo(dbpool, schema.getComCode());
						schema.setComName(dbPrpDcompany.getComCName());
					}
					if (StringUtils.isNotEmpty(schema.getAgentCode())) {
						
						dbPrpDagent.getInfo(dbpool,schema.getAgentCode());
						schema.setAgentName(dbPrpDagent.getAgentName());
					}
					if (StringUtils.isNotEmpty(schema.getHandler1Code())) {
						
						dbPrpDuser.getInfo(dbpool, schema.getHandler1Code());
						schema.setHandler1Name(dbPrpDuser.getUserName());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dbpool.close();
		}
	}
	
	/**
	 * 查询预收付信息
	 * 包括完整的prpjpoaInfo数据
	 * 
	 * @author 徐光远
	 * @param conditions
	 * @return
	 */
	public void queryExtendInfo(String conditions, int limitCount) throws Exception{
		DbPool dbpool = new DbPool();
        
        
        
		
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
		try {
			DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
			initArr();
			schemas = dbPrpJplanFeePre.queryExtendInfo(conditions, limitCount);
			if (!schemas.isEmpty()) {
		        DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
		        DBPrpDagent dbPrpDagent = new DBPrpDagent();
				for (Iterator iterator = schemas.iterator(); iterator.hasNext();) {
					PrpJplanFeePreSchema schema = (PrpJplanFeePreSchema) iterator.next();
					if (StringUtils.isNotEmpty(schema.getComCode())) {
						
						dbPrpDcompany.getInfo(dbpool, schema.getComCode());
						schema.setComName(dbPrpDcompany.getComCName());
					}
					if (StringUtils.isNotEmpty(schema.getAgentCode())) {
						
						dbPrpDagent.getInfo(dbpool,schema.getAgentCode());
						schema.setAgentName(dbPrpDagent.getAgentName());
					}
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
		
	}
	
	/**
	 * 查询预收付信息
	 * 包括完整的prpjpoaInfo数据
	 * 
	 * @author 徐光远
	 * @param conditions
	 * @return
	 * @throws UserException 
	 */
	public void queryExtendInfo(String conditions) throws UserException, Exception {
		queryExtendInfo(conditions, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT")
				.trim()));
	}
	
	
	/**
	 * 预收款统计查询
	 * @author yanglei
	 * @param conditions
	 * @return
	 * @throws Exception 
	 */
	public void queryByGroup(String conditions) throws Exception {
		StringBuffer sqlBuffer = new StringBuffer(100);
		sqlBuffer.append(" SELECT PoaType,CertiStatus,decode(riskcode, '0507', '交强XXXXX', '商业XXXXX') RiskName,");
        sqlBuffer.append(" BusinessNature,COUNT(1) BillCount,ChannelType,");
        sqlBuffer.append(" SUM(PreMiumPlanFee) PreMiumPlanFee,SUM(CSTaxPlanFee) CSTaxPlanFee,SUM(PlanFee) PlanFee ");
        sqlBuffer.append(" FROM PrpJplanFeePre Where " + conditions);
        sqlBuffer.append(" GROUP BY PoaType,CertiStatus,decode(riskcode, '0507', '交强XXXXX', '商业XXXXX'),decode(agentcode,'','直接业务','代理业务'),BusinessNature,ChannelType ");
        sqlBuffer.append(" ORDER BY PoaType ");
        DbPool dbpool = new DbPool();
	    
        
        
        
        
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
        try{
        	PrpJplanFeePreSchema prpJplanFeePreSchema = null;
            ResultSet resultSet = dbpool.query(sqlBuffer.toString());
            while(resultSet.next())
            {
            	prpJplanFeePreSchema = new PrpJplanFeePreSchema();
            	prpJplanFeePreSchema.setPoaType(resultSet.getString("PoaType"));
            	prpJplanFeePreSchema.setCertiStatus(resultSet.getString("CertiStatus"));
            	prpJplanFeePreSchema.setAttribute2(resultSet.getString("RiskName"));
            	prpJplanFeePreSchema.setAttribute3(resultSet.getString("BillCount"));
            	prpJplanFeePreSchema.setBusinessNature(resultSet.getString("BusinessNature"));
            	prpJplanFeePreSchema.setPlanFee(resultSet.getString("PlanFee"));
            	prpJplanFeePreSchema.setPremiumPlanFee(resultSet.getString("PreMiumPlanFee"));
            	prpJplanFeePreSchema.setCSTaxPlanFee(resultSet.getString("CSTaxPlanFee"));
            	prpJplanFeePreSchema.setChannelType(resultSet.getString("ChannelType"));
            	this.setArr(prpJplanFeePreSchema);
            }
            resultSet.close();
        }
        catch(Exception exception){
            throw exception;
        }
        finally{
            dbpool.close();
        }
	}
 
	public void QueryPrpJplanFeePreTZ(String iWhere) throws Exception
	{
		String strSQL =   "SELECT prpjplanfeepre.CertiType,prpjpoainfo.comcode,prpjplanfeepre.CertiNo,prpjplanfeepre.PolicyNO,"
						+ " prpjplanfeepre.AppliName,prpjplanfeepre.InsuredName,prpjplanfeepre.StartDate,"
						+ " prpjplanfeepre.EndDate,prpjplanfeepre.PremiumPlanFee,prpjpoainfo.PoaType,PrpJplanFeePre.Handler1Code,"
						+ " prpjpoainfo.AccDate,prpjpoainfo.posvoucherno,prpjpoainfo.AgentCode,prpjpoainfo.CustomNo,"
						+ " prpjpoainfo.posno"
						+ " FROM prpjplanfeepre,prpjpoainfo "
						+ " WHERE 1=1 "+iWhere
						+ " AND prpjpoainfo.poacode=prpjplanfeepre.poacode "
                        + " AND ROWNUM<1000 ";
		
		
		
		DbPool dbpool = new DbPool();
        
        
        
		
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
		try
		{
			
			PrpJplanFeePreSchema prpJplanFeePreSchema = null;
            ResultSet resultSet = dbpool.query(strSQL);
            while(resultSet.next())
            {
            	
            	prpJplanFeePreSchema = new PrpJplanFeePreSchema();
            	String strCertiType = "";
            	strCertiType = resultSet.getString("CertiType");
            	
            	prpJplanFeePreSchema.setCertiType(strCertiType);
            	prpJplanFeePreSchema.setCertiNo(resultSet.getString("CertiNo"));
            	prpJplanFeePreSchema.setAppliName(resultSet.getString("AppliName"));
            	prpJplanFeePreSchema.setInsuredName(resultSet.getString("InsuredName"));
            	prpJplanFeePreSchema.setStartDate(resultSet.getDate("StartDate").toString());
            	prpJplanFeePreSchema.setEndDate(resultSet.getDate("EndDate").toString());
            	prpJplanFeePreSchema.setPremiumPlanFee(resultSet.getString("PremiumPlanFee"));
            	
            	
            	String strHandler1Code = "";
            	strHandler1Code = resultSet.getString("Handler1Code");
            	
            	DBPrpDuser prpduser = new DBPrpDuser();
            	prpduser.getInfo(strHandler1Code);
            	prpJplanFeePreSchema.setHandler1Code(prpduser.getUserName());
            	
            	
            	int PoaTypeSgin = 0;
            	String strPoaType  = "";
            	
            	PoaTypeSgin = Integer.parseInt(resultSet.getString("PoaType"));
            	
            	if(PoaTypeSgin == 2)
            	{
            		strPoaType = "POS";
            	}else if(PoaTypeSgin == 3)
            	{
            		strPoaType = "支票";
            	}
            	else if (PoaTypeSgin == 4)
            	{
            		strPoaType = "划账";
            	}
            	else if(PoaTypeSgin==5)
            	{
            		strPoaType = "现金";
            	}
            	
            	
            	if(strCertiType.equals("T") )
            	{
            		String strPolicyNO ="";
            		strPolicyNO = resultSet.getString("policyNo");
            		DBPrpCjfcdLogMsg dbPrpCjfcdLogMsg = new DBPrpCjfcdLogMsg();
            		dbPrpCjfcdLogMsg.getInfo(dbpool,strPolicyNO);
            		prpJplanFeePreSchema.setAppliCode(dbPrpCjfcdLogMsg.getConfirmDate());
            		prpJplanFeePreSchema.setInsuredCode(dbPrpCjfcdLogMsg.getPrintDate());
            	}
            	else
            	{
            		String strCertiNO = "" ;
            		strCertiNO = resultSet.getString("CertiNO");
            		DBPrpPjfcdLogMsg dbPrpPjfcdLogMsg = new DBPrpPjfcdLogMsg();
            		dbPrpPjfcdLogMsg.getInfo(dbpool,strCertiNO);
            		prpJplanFeePreSchema.setAppliCode(dbPrpPjfcdLogMsg.getConfirmDate());
            		prpJplanFeePreSchema.setInsuredCode(dbPrpPjfcdLogMsg.getPrintDate());
            	}
            	
            	
            	String strAgentCode = "";
            	strAgentCode = resultSet.getString("AgentCode");
            	DBPrpDagent dbprpdagent = new DBPrpDagent();
                dbprpdagent.getInfo(strAgentCode);
            	
            	
            	prpJplanFeePreSchema.setAttribute1(resultSet.getString("comcode"));
            	prpJplanFeePreSchema.setAttribute2(strPoaType);



            	
            	if (!(resultSet.getTimestamp("AccDate") == null || ""
						.equals(resultSet.getTimestamp("AccDate").toString()))) {
					prpJplanFeePreSchema.setAttribute3(resultSet.getTimestamp(
							"AccDate").toString().substring(0, 19));
				}
                
            	prpJplanFeePreSchema.setProConfirmSequenceNo(resultSet.getString("posvoucherno"));
            	prpJplanFeePreSchema.setErrorMessage(dbprpdagent.getAgentName());
            	prpJplanFeePreSchema.setBrandName(resultSet.getString("CustomNo"));
            	prpJplanFeePreSchema.setFrameNo(resultSet.getString("posno"));
            	
            	this.setArr(prpJplanFeePreSchema);
            }
            resultSet.close();
			
		}
		catch(Exception exception)
		{
			throw exception;
		}
		finally
		{
			 dbpool.close();
		}
	}
	
	public void QueryPrpJplanFeePreTZHis(String iWhere) throws Exception
	{
		String strSQL =   "SELECT prpjplanfeepre.CertiType,prpjpoainfo.comcode,prpjplanfeepre.CertiNo,prpjplanfeepre.PolicyNO,"
						+ " prpjplanfeepre.AppliName,prpjplanfeepre.InsuredName,prpjplanfeepre.StartDate,"
						+ " prpjplanfeepre.EndDate,prpjplanfeepre.PremiumPlanFee,prpjpoainfo.PoaType,PrpJplanFeePre.Handler1Code,"
						+ " prpjpoainfo.AccDate,prpjpoainfo.posvoucherno,prpjpoainfo.AgentCode,prpjpoainfo.CustomNo,"
						+ " prpjpoainfo.posno"
						+ " FROM prpjplanfeepre,prpjpoainfo "
						+ " WHERE 1=1 "+iWhere
						+ " AND prpjpoainfo.poacode=prpjplanfeepre.poacode "
                        + " AND ROWNUM<1000 ";
		
		
		
		DbPool dbpool = new DbPool();
		try
		{
            
            
            
			
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
            
			PrpJplanFeePreSchema prpJplanFeePreSchema = null;
            ResultSet resultSet = dbpool.query(strSQL);
            while(resultSet.next())
            {
            	
            	prpJplanFeePreSchema = new PrpJplanFeePreSchema();
            	String strCertiType = "";
            	strCertiType = resultSet.getString("CertiType");
            	
            	prpJplanFeePreSchema.setCertiType(strCertiType);
            	prpJplanFeePreSchema.setCertiNo(resultSet.getString("CertiNo"));
            	prpJplanFeePreSchema.setAppliName(resultSet.getString("AppliName"));
            	prpJplanFeePreSchema.setInsuredName(resultSet.getString("InsuredName"));
            	prpJplanFeePreSchema.setStartDate(resultSet.getDate("StartDate").toString());
            	prpJplanFeePreSchema.setEndDate(resultSet.getDate("EndDate").toString());
            	prpJplanFeePreSchema.setPremiumPlanFee(resultSet.getString("PremiumPlanFee"));
            	
            	
            	String strHandler1Code = "";
            	strHandler1Code = resultSet.getString("Handler1Code");
            	
            	DBPrpDuser prpduser = new DBPrpDuser();
            	prpduser.getInfo(strHandler1Code);
            	prpJplanFeePreSchema.setHandler1Code(prpduser.getUserName());
            	
            	
            	int PoaTypeSgin = 0;
            	String strPoaType  = "";
            	
            	PoaTypeSgin = Integer.parseInt(resultSet.getString("PoaType"));
            	
            	if(PoaTypeSgin == 2)
            	{
            		strPoaType = "POS";
            	}else if(PoaTypeSgin == 3)
            	{
            		strPoaType = "支票";
            	}
            	else if (PoaTypeSgin == 4)
            	{
            		strPoaType = "划账";
            	}
            	else if(PoaTypeSgin==5)
            	{
            		strPoaType = "现金";
            	}
            	
            	
            	if(strCertiType.equals("T") )
            	{
            		String strPolicyNO ="";
            		strPolicyNO = resultSet.getString("policyNo");
            		DBPrpCjfcdLogMsg dbPrpCjfcdLogMsg = new DBPrpCjfcdLogMsg();
            		dbPrpCjfcdLogMsg.getInfo(dbpool,strPolicyNO);
            		prpJplanFeePreSchema.setAppliCode(dbPrpCjfcdLogMsg.getConfirmDate());
            		prpJplanFeePreSchema.setInsuredCode(dbPrpCjfcdLogMsg.getPrintDate());
            	}
            	else
            	{
            		String strCertiNO = "" ;
            		strCertiNO = resultSet.getString("CertiNO");
            		DBPrpPjfcdLogMsg dbPrpPjfcdLogMsg = new DBPrpPjfcdLogMsg();
            		dbPrpPjfcdLogMsg.getInfo(dbpool,strCertiNO);
            		prpJplanFeePreSchema.setAppliCode(dbPrpPjfcdLogMsg.getConfirmDate());
            		prpJplanFeePreSchema.setInsuredCode(dbPrpPjfcdLogMsg.getPrintDate());
            	}
            	
            	
            	String strAgentCode = "";
            	strAgentCode = resultSet.getString("AgentCode");
            	DBPrpDagent dbprpdagent = new DBPrpDagent();
                dbprpdagent.getInfo(strAgentCode);
            	
            	
            	prpJplanFeePreSchema.setAttribute1(resultSet.getString("comcode"));
            	prpJplanFeePreSchema.setAttribute2(strPoaType);



            	
            	if (!(resultSet.getTimestamp("AccDate") == null || ""
						.equals(resultSet.getTimestamp("AccDate").toString()))) {
					prpJplanFeePreSchema.setAttribute3(resultSet.getTimestamp(
							"AccDate").toString().substring(0, 19));
				}
                
            	prpJplanFeePreSchema.setProConfirmSequenceNo(resultSet.getString("posvoucherno"));
            	prpJplanFeePreSchema.setErrorMessage(dbprpdagent.getAgentName());
            	prpJplanFeePreSchema.setBrandName(resultSet.getString("CustomNo"));
            	prpJplanFeePreSchema.setFrameNo(resultSet.getString("posno"));
            	
            	this.setArr(prpJplanFeePreSchema);
            }
            resultSet.close();
			
		}
		catch(Exception exception)
		{
			throw exception;
		}
		finally
		{
			 dbpool.close();
		}
	}
   
    /**
     * @desc 同XXXXX交强XXXXX是否与商业XXXXX关联选中(不带dbpool）
     * @param list0507TB
     * @param listOther05
     * @return 关联交强XXXXX未同时选中商业XXXXXXX单号集合
     * @throws Exception
     */
	public ArrayList getNoTBBy0507(ArrayList list0507TB,ArrayList listOther05)
	throws Exception{
		ArrayList noTBList = new ArrayList();
		DbPool dbpool = new DbPool();
		try {
            
            
            
			
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
            
			noTBList = this.getNoTBBy0507(dbpool, list0507TB, listOther05);
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
		return noTBList;
	}
	
	
    /**
     * @desc 判断是否符合倒签单
     * @param iComCode 登录机构
     * @param iPoaCode 收据号
     * @throws Exception
     */
    public void checkReverseEndorse(String iComCode, String iPoaCode, Date iValidDate) throws Exception{
        
        ResultSet resultSet = null;
        String strRiskCode = null;
        int i = 0;
        DbPool dbpool = new DbPool();
        Date now = new Date();
        String strSQL = " SELECT distinct riskcode as RISKCODE FROM PRPJPLANFEEPRE WHERE POACODE = '" + iPoaCode + "' ";
        
        try {
            
            
            
        	
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
            
            resultSet = dbpool.query(strSQL);
            
            while(resultSet.next()){
            	strRiskCode = resultSet.getString("RISKCODE");
            	i ++;
            }
            resultSet.close();
            
            
            if(i == 1){
            	BLPrpJpreFeeCharge blPrpJpreFeeCharge = new BLPrpJpreFeeCharge();
            	String strReverseDays = blPrpJpreFeeCharge.getConfigValueByRiskCodeComCode(iComCode, strRiskCode, "REVERSE_WRITTEN_PERMISSION");
            	
            	if("0".equals(strReverseDays)){ 
            		throw new UserException(-98,-1200,"BLPrpJplanFeePre","该登录机构XXXXX种" + strRiskCode + "不允许倒签单");
            	}else{ 
            		long interval = iValidDate.getTime() - now.getTime();
                    if ((interval/(1000L * 60 * 60 * 24)) > 0 || (interval/(1000L * 60 * 60 * 24)) <= Integer.parseInt(strReverseDays)) {
                        throw new UserException(-98, -1003, "BLPrpJplanFeePre", "该机构配置的倒签时间为 " + strReverseDays + "天，" +
                        		"该笔业务的生效日期不在倒签时间范围内，不能进行此操作！");
                    }
            	}
            }else{
            	throw new UserException(-98,-1200,"BLPrpJplanFeePre","该笔交易存在多XXXXX种，不允许倒签单");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(resultSet != null){
            	resultSet.close();
            }
            dbpool.close();   
        }
    } 
	

    /**
	 * @desc 同XXXXX交强XXXXX是否与商业XXXXX关联选中(带dbpool)
	 * @param list0507TB
	 * @param listOther05
	 * @return 关联交强XXXXX未同时选中商业XXXXXXX单号集合
	 * @throws Exception
	 */
	public ArrayList getNoTBBy0507(DbPool dbPool,ArrayList list0507TB,ArrayList listOther05)
	throws Exception{
		ArrayList noTBList = new ArrayList();
		String proposalNo05 = "";
		String proposalNo0507 = "";
		String strCond05Other = "";
		String strCond0507 = "('";
		if(list0507TB==null||list0507TB.size()==0){
			strCond0507 += "')";
		}
		for(int i=0;i<list0507TB.size();i++){
			if(i==list0507TB.size()-1)
				strCond0507 += (String)list0507TB.get(i)+"')";
			else
				strCond0507 += (String)list0507TB.get(i)+"','";
		}
		
		if(listOther05==null||listOther05.size()==0){
			strCond05Other = "('1')";
		}else{
			strCond05Other = "('";
		}
		for(int j=0;j<listOther05.size();j++){
			if(j==listOther05.size()-1)
				strCond05Other += (String)listOther05.get(j)+"')";
			else
				strCond05Other += (String)listOther05.get(j)+"','";
		}
		
		String condition = " ProposalNo NOT IN "+strCond05Other+" AND MainPolicyNo IN  "+strCond0507 ;
		                 
		BLPrpTmainSub blPrpTmainSub  = new BLPrpTmainSub();
		PrpTmainSubSchema prpTmainSubSchema = null;
		blPrpTmainSub.query(dbPool,condition);
		for(int k=0;k<blPrpTmainSub.getSize();k++){
			prpTmainSubSchema = blPrpTmainSub.getArr(k);
			proposalNo05 = prpTmainSubSchema.getProposalNo();
			proposalNo0507 = prpTmainSubSchema.getMainPolicyNo();
			noTBList.add(proposalNo05+"-"+proposalNo0507);
		}
		return noTBList;
	} 
	
    /**
     * @desc 车+意业务的XX单是否全部选中(不带dbpool）
     * @param certiNoList052700
     * @param orderNoList052700
     * @return 车+意业务未同时选中XX单号集合
     * @throws Exception
     */
	public ArrayList getNoFor052700(ArrayList certiNoList052700,ArrayList orderNoList052700)
	throws Exception{
		ArrayList noTBList = new ArrayList();
		DbPool dbpool = new DbPool();
		try {
            
            
            
			
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
            
			noTBList = this.getNoFor052700(dbpool, certiNoList052700, orderNoList052700);
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
		return noTBList;
	}
    /**
	 * @desc 意业务的XX单是否全部选中(带dbpool)
	 * @param certiNoList052700
	 * @param orderNoList052700
	 * @return 车+意业务未同时选中XX单号集合
	 * @throws Exception
	 */
	public ArrayList getNoFor052700(DbPool dbPool,ArrayList certiNoList052700,ArrayList orderNoList052700)
	throws Exception{
		ArrayList noTBList = new ArrayList();
		String strCond0507 = "('";
		for(int i=0;i<orderNoList052700.size();i++){
			if(i==orderNoList052700.size()-1)
				strCond0507 += (String)orderNoList052700.get(i)+"')";
			else
				strCond0507 += (String)orderNoList052700.get(i)+"','";
		}
		
		String condition = " OrderNo  IN "+strCond0507 ;
		BLPrpCarRelation blPrpCarRelation = new BLPrpCarRelation();
		blPrpCarRelation.query(dbPool,condition);
		boolean flag = false;
		for(int k=0;k<blPrpCarRelation.getSize();k++){
           for(int j=0;j<certiNoList052700.size();j++){
        	  if(blPrpCarRelation.getArr(k).getProposalNo().equals((String)certiNoList052700.get(j))){
        		  flag = true;
        		  break;
        	  }
           }
           if(!flag){
			noTBList.add(blPrpCarRelation.getArr(k).getProposalNo());
           }
		   flag = false;
		}
		return noTBList;
	} 
    /**
     * @desc 同XXXXX业务关联的XX单是否删除(不带dbpool）
     * @param dbpool 数据源
     * @param strProposalNo XX单号
     * @return XX单是否删除 true-已删除 false-未删除
     * @throws Exception
     */
    public boolean isCancelOfTB(String strProposalNo)
    throws Exception{
        boolean flag = false;
        DbPool dbpool = new DbPool();
        try {
            
            
            
        	
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
            
            flag = this.isCancelOfTB(dbpool, strProposalNo);
        } catch (Exception e) {
            throw e;
        } finally {
            dbpool.close();
        }
        return flag;
    }
    /**
     * @desc 同XXXXX业务关联的XX单是否删除(带dbpool）
     * @param dbpool 数据源
     * @param strProposalNo XX单号
     * @return XX单是否删除 true-已删除 false-未删除
     * @throws Exception
     */
    public boolean isCancelOfTB(DbPool dbpool,String strProposalNo)
    throws Exception{
        String strOthFlag4 = "";
        DBPrpTmain dbPrpTmain = new DBPrpTmain();
        
        if(strProposalNo==null||"".equals(strProposalNo)){
            return true;
        }
        int result = dbPrpTmain.getInfo(dbpool, strProposalNo);
        
        if(result == 100){
            return true;
        }
        
        if(dbPrpTmain.getOthFlag().length()>=4){
            strOthFlag4 = dbPrpTmain.getOthFlag().substring(3,4);
        }else{
            return true;
        }
        
        if("1".equals(strOthFlag4)||"2".equals(strOthFlag4)){
            return true;
        }
        return false;
    }
    
    /* ADD BY PENGJINLING 2013-3-18 payment-551XXXXX合并批单处理-配合核心修改 BEGIN */    
    /**
     * @desc 同XXXXX业务关联的批单是否删除(不带dbpool）
     * @param dbpool 数据源
     * @param strEndorseNo 批单号
     * @return 批单是否删除 true-已删除 false-未删除
     * @throws Exception
     */
    public boolean isCancelOfTBE(String strEndorseNo)
    throws Exception{
        boolean flag = false;
        DbPool dbpool = new DbPool();
        try {
            
        	
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
            flag = this.isCancelOfTBE(dbpool, strEndorseNo);
        } catch (Exception e) {
            throw e;
        } finally {
            dbpool.close();
        }
        return flag;
    }
    /**
     * @desc 同XXXXX业务关联的批单是否删除(带dbpool）
     * @param dbpool 数据源
     * @param strEndorseNo 批单号
     * @return 批单是否删除 true-已删除 false-未删除
     * @throws Exception
     */
    public boolean isCancelOfTBE(DbPool dbpool,String strEndorseNo)
    throws Exception{
        DBPrpPmain dbPrpPmain = new DBPrpPmain();
        
        if(strEndorseNo==null||"".equals(strEndorseNo)){
            return true;
        }
        int result = dbPrpPmain.getInfo(dbpool, strEndorseNo);
        
        if(result == 100){
            return true;
        }
        return false;
    }
    
    /**
     * @desc 同XXXXX交强XXXXX是否与商业XXXXX关联选中(不带dbpool）
     * @param list0507TB
     * @param listOther05
     * @return 关联交强XXXXX未同时选中商业XXXXX批单号集合
     * @throws Exception
     */
	public ArrayList getNoTBBy0507E(ArrayList list0507TB,ArrayList listOther05)
	throws Exception{
		ArrayList noTBList = new ArrayList();
		DbPool dbpool = new DbPool();
		try {
            
			
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
			noTBList = this.getNoTBBy0507E(dbpool, list0507TB, listOther05);
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
		return noTBList;
	}
	
    /**
	 * @desc 同XXXXX交强XXXXX是否与商业XXXXX关联选中(带dbpool)
	 * @param list0507TB
	 * @param listOther05
	 * @return 关联交强XXXXX未同时选中商业XXXXX批单号集合
	 * @throws Exception
	 */
	public ArrayList getNoTBBy0507E(DbPool dbPool,ArrayList list0507TB,ArrayList listOther05)
	throws Exception{
		ArrayList noTBList = new ArrayList();
		String endorseNo05 = "";
		String endorseNo0507 = "";
		String strCond05Other = "";
		String strCond0507 = "('";
		if(list0507TB==null||list0507TB.size()==0){
			strCond0507 += "')";
		}
		for(int i=0;i<list0507TB.size();i++){
			if(i==list0507TB.size()-1)
				strCond0507 += (String)list0507TB.get(i)+"')";
			else
				strCond0507 += (String)list0507TB.get(i)+"','";
		}
		
		if(listOther05==null||listOther05.size()==0){
			strCond05Other = "('1')";
		}else{
			strCond05Other = "('";
		}
		for(int j=0;j<listOther05.size();j++){
			if(j==listOther05.size()-1)
				strCond05Other += (String)listOther05.get(j)+"')";
			else
				strCond05Other += (String)listOther05.get(j)+"','";
		}

		String condition = " ENDORSENO NOT IN "+strCond05Other+" AND MAINENDORSENO IN  "+strCond0507 ;
		BLPrpPmainSub blPrpPmainSub  = new BLPrpPmainSub();
		PrpPmainSubSchema prpPmainSubSchema = null;
		blPrpPmainSub.query(dbPool,condition);
		for(int k=0;k<blPrpPmainSub.getSize();k++){
			prpPmainSubSchema = blPrpPmainSub.getArr(k);
			endorseNo05 = prpPmainSubSchema.getEndorseNo();
			endorseNo0507 = prpPmainSubSchema.getMainEndorseNo();
			noTBList.add(endorseNo05+"-"+endorseNo0507);
		}
		return noTBList;
	}
	
    /**
     * @desc 同XXXXX商业XXXXX是否与交强XXXXX关联选中(不带dbpool）
     * @param list0507TB
     * @param listOther05
     * @return 关联商业XXXXX未同时选中交强XXXXX批单号集合
     * @throws Exception
     */
	public ArrayList getNoTBByOther05E(ArrayList list0507TB,ArrayList listOther05)
	throws Exception{
		ArrayList noTBList = new ArrayList();
		DbPool dbpool = new DbPool();
		try {
            
			
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
			noTBList = this.getNoTBByOther05E(dbpool, list0507TB, listOther05);
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
		return noTBList;
	}
	
    /**
	 * @desc 同XXXXX商业XXXXX是否与交强XXXXX关联选中(带dbpool)
	 * @param list0507TB
	 * @param listOther05
	 * @return 关联商业XXXXX未同时选中交强XXXXX批单号集合
	 * @throws Exception
	 */
	public ArrayList getNoTBByOther05E(DbPool dbPool,ArrayList list0507TB,ArrayList listOther05)
	throws Exception{
		ArrayList noTBList = new ArrayList();
		String endorseNo05 = "";
		String endorseNo0507 = "";
		String strCond05Other = "('";
		String strCond0507 = "";
		if(listOther05==null||listOther05.size()==0){
			strCond05Other += "')";
		}
		for(int i=0;i<listOther05.size();i++){
			if(i==listOther05.size()-1)
				strCond05Other += (String)listOther05.get(i)+"')";
			else
				strCond05Other += (String)listOther05.get(i)+"','";
		}
		
		if(list0507TB==null||list0507TB.size()==0){
			strCond0507 = "('1')";
		}else{
			strCond0507 = "('";
		}
		for(int j=0;j<list0507TB.size();j++){
			if(j==list0507TB.size()-1)
				strCond0507 += (String)list0507TB.get(j)+"')";
			else
				strCond0507 += (String)list0507TB.get(j)+"','";
		}

		String condition = " MAINENDORSENO NOT IN "+strCond0507+" AND ENDORSENO IN  "+strCond05Other ;
		BLPrpPmainSub blPrpPmainSub  = new BLPrpPmainSub();
		PrpPmainSubSchema prpPmainSubSchema = null;
		blPrpPmainSub.query(dbPool,condition);
		for(int k=0;k<blPrpPmainSub.getSize();k++){
			prpPmainSubSchema = blPrpPmainSub.getArr(k);
			endorseNo05 = prpPmainSubSchema.getEndorseNo();
			endorseNo0507 = prpPmainSubSchema.getMainEndorseNo();
			noTBList.add(endorseNo05+"-"+endorseNo0507);
		}
		return noTBList;
	}
    /* ADD BY PENGJINLING 2013-3-18 payment-551XXXXX合并批单处理-配合核心修改 END */    
    
    /**
     * @desc 车+意业务关联的XX单是否删除(不带dbpool）
     * @param dbpool 数据源
     * @param strProposalNo XX单号
     * @return XX单是否删除 true-已删除 false-未删除
     * @throws Exception
     */
    public boolean isCancelOfCaY(String strProposalNo)
    throws Exception{
        boolean flag = false;
        DbPool dbpool = new DbPool();
        try {
            
            
            
        	
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
            
            flag = this.isCancelOfCaY(dbpool, strProposalNo);
        } catch (Exception e) {
            throw e;
        } finally {
            dbpool.close();
        }
        return flag;
    }
    /**
     * @desc 同XXXXX业务关联的XX单是否删除(带dbpool）
     * @param dbpool 数据源
     * @param strProposalNo XX单号
     * @return XX单是否删除 true-已删除 false-未删除
     * @throws Exception
     */
    public boolean isCancelOfCaY(DbPool dbpool,String strProposalNo)
    throws Exception{
        String strOthFlag4 = "";
        DBPrpTmain dbPrpTmain = new DBPrpTmain();
        BLPrpCarRelation blPrpCarRelation = new BLPrpCarRelation();
        
        if(strProposalNo==null||"".equals(strProposalNo)){
            return true;
        }
        String strWherepart = " orderNo = '" + strProposalNo +"'";
        blPrpCarRelation.query(dbpool,strWherepart);
        if(blPrpCarRelation.getSize()==0){
        	return true;
        }
        for(int i = 0;i < blPrpCarRelation.getSize(); i++){
        	
        	int result = dbPrpTmain.getInfo(dbpool, blPrpCarRelation.getArr(i).getProposalNo());
            
            if(result == 100){
                return true;
            }
            
            if(dbPrpTmain.getOthFlag().length()>=4){
                strOthFlag4 = dbPrpTmain.getOthFlag().substring(3,4);
            }else{
                return true;
            }
            
            if("1".equals(strOthFlag4)||"2".equals(strOthFlag4)){
                return true;
            }	
        }
        
        return false;
    }
    
    /**
     * 激活卡业务预收款单证预约校验
     * @param iCertiNo
     * @param iComCode
     * @throws Exception
     */
	public void checkVSCode(String[] iCertiNo, String[] iComCode)throws Exception {
		Map map = null;
		boolean flag = false;
		ArrayList list = new ArrayList();
		ArrayList listR = new ArrayList();
		BLPrpTbatch blPrpTbatch = new BLPrpTbatch();
		StringBuffer buf = new StringBuffer();
		Map checkProposalNo = new HashMap();
		BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre(); 
		VisaInterfaceForPrpAction visaInterfaceForPrpAction = new VisaInterfaceForPrpAction();
		String strErrorMessage = "";
		int intSize = iCertiNo.length;
		buf.append(" Certino IN (");
		for (int i = 0; i < intSize; i++) {
			if (i == (intSize - 1)) {
				buf.append("'" + iCertiNo[i] + "')");
			} else {
				buf.append("'" + iCertiNo[i] + "', ");
			}
		}
		blPrpJplanFeePre.query(buf.toString());
		int count = blPrpJplanFeePre.getSize();
		for (int i = 0; i < count; i++) {
			if("4".equals(blPrpJplanFeePre.getArr(i).getAttribute3())){
				checkProposalNo.put(blPrpJplanFeePre.getArr(i).getContractNo(),blPrpJplanFeePre.getArr(i).getComCode() );
			}else{
				checkProposalNo.put(blPrpJplanFeePre.getArr(i).getCertiNo(),blPrpJplanFeePre.getArr(i).getComCode() );
			}
		}
		
		buf.delete(0, buf.length());
		buf.append(" ProposalNo IN (' '");
		Iterator it = checkProposalNo.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			String strProposalNo = (String)entry.getKey();
			buf.append(",'").append(strProposalNo).append("'");
		}
		buf.append(")");
		blPrpTbatch.query(buf.toString());
		int size = blPrpTbatch.getSize();
		for (int i = 0; i < size; i++) {
			map = new HashMap();
			map.put("visaCode", blPrpTbatch.getArr(i).getVisaCode());
			map.put("acount", blPrpTbatch.getArr(i).getBillCount());
			map.put("comCode", checkProposalNo.get(blPrpTbatch.getArr(i).getProposalNo()));
			map.put("systemFlag", "P");
			map.put("proposalNo", blPrpTbatch.getArr(i).getProposalNo());
			
			list.add(map);
		}
		listR = (ArrayList) visaInterfaceForPrpAction.docheckVsUnusedMark(list);
		int listSize = listR.size();
		strErrorMessage += "新激活卡业务,预收款单证预约校验失败：<br/>";
		for (int i = 0; i < listSize; i++) {
			Map mapTem = (Map) listR.get(i);
			if ("false".equals(mapTem.get("status"))) {
				flag = true;
				strErrorMessage += "XX单号：" + mapTem.get("proposalNo") + ",单证类型："+ mapTem.get("visaCode") + ",预约数量：" + mapTem.get("acount")+ "<br/>";
			}
		}
		if (flag) {
			throw new UserException(-96, -1167, "BLPrpJplanFeePre.checkVSCode",strErrorMessage);
		}
	}

	
    /**
     * 易宝垫付集中对账数据提取
     * @param StrAccStartDate
     * @param StrAccEndDate
     * @param StrCenterCode
     * @throws Exception
     */
	

	
	
	
	
	public Collection JEPayPosMode93Query(String StrAccStartDate,String StrAccEndDate,String StrCenterCode,Map map) throws Exception
	
	{	
		
		String strCondition = (String) map.get("Condition");
		String strCondition1 = (String) map.get("Condition1");
		ArrayList bindValues = (ArrayList) map.get("BindValues");
		String strNoPayCount = (String) map.get("NoPayCount");
		String strpayforStartDate = (String)map.get("payforStartDate");
		String strpayforEndDate = (String)map.get("payforEndDate");
		String strWherePart = " and 1 = 1 ";
		String strSql1 = " and 1 = 1 ";
		String strSql2 = " and 1 = 1 ";
		String strAdd ="";
		if (strNoPayCount != null && !"".equals(strNoPayCount)) {
			strWherePart += " and ceil(TO_DATE(TO_CHAR(SYSDATE, 'YYYY-MM-DD'), 'YYYY-MM-DD') - a.accdate) >= '"+ strNoPayCount + "'";
		}
		if ((bindValues != null && bindValues.size()>0) ||(strpayforStartDate != null && !"".equals(strpayforStartDate)) ||  (strpayforEndDate != null && !"".equals(strpayforEndDate))){
			strSql1 += strCondition;
		}
		if (strCondition1 != null && !"".equals(strCondition1)) {
			strSql2 += strCondition1;
		}
		
		String strpoaCode = (String)map.get("poaCode");
		if (strpoaCode != null && !"".equals(strpoaCode)) {
			strWherePart += " and a.poaCode = '"+ strpoaCode + "'";
		}
		String posmodelno = (String)map.get("posmodelno");
		if (posmodelno != null && !"".equals(posmodelno)) {
			strWherePart += " and a.posmodelno = '"+ posmodelno + "'";
		}
		
		
		if("93".equals(posmodelno)){
			strAdd = "vi_yeepay_detail";
		}else if("94".equals(posmodelno)){
			strAdd = "vi_bill99_detail";
		}
		
		
		
	
		String strSQL =   " select bv.comcode," +
		" bv.centercode," +
		" bv.certino," +
		" bv.policyno," +
		" av.accdate," +
		" nvl(av.accdate+14,'1111-11-11') as zqhkr, " +
		" nvl(av.accdate+29,'1111-11-11') as zhhkr, " +
		" bv.cms_return_date," +
		" bv.poacode,  " +
		" av.totalfee," +
		" decode(bv.status,'','已垫付',bv.status) as status," +
		" bv.sunpayfee," +
		" bv.suninterest," +
		" bv.attribute12," +
		" bv.account," +
		" bv.ChannelType  from (" +
			" select t1.comcode," +
		
				          " t1.centercode," +
				          " t1.certino," +
				          " t1.policyno," +
				          " nvl(t2.dishonor_date,'1111-11-11') as dishonor_date," +
				          " nvl(t2.dishonor_date+14,'1111-11-11') as zqhkr," + 
				          " nvl(t2.dishonor_date+29,'1111-11-11') as zhhkr," + 
		                  " nvl(t2.cms_return_date,'1111-11-11') as cms_return_date," +
				          " t1.poacode," +  
				          " nvl(t2.amount,'') as amount,"+ 
				          " nvl(t2.status,'') as status,"+ 
				          " nvl(t2.sunpayfee,'') as sunpayfee,"+ 
				          " nvl(t2.suninterest,'') as suninterest,"+
				          " nvl(t2.attribute12,'') as attribute12,"+
				          " nvl(t2.account,'') as account,"+
				          " t1.ChannelType" +

		                  " from prpjplanfeepre t1  LEFT JOIN  " + strAdd + " t2" +
		                  " on t1.poacode = t2.PAY_ID  where" +
		                  " t1.poacode in " +
		                  " (select a.poacode" +
		                  " from PrpJpoaInfo a" +
		                  " where a.centercode = '" +StrCenterCode+"' "+
		                  " and a.accdate between date '"+StrAccStartDate+"' and date'" +StrAccEndDate+"'+1"+

		                  " and a.poatype in ('2','O')" +
		                  " and a.accflag = '1'" +
		                  
		                  strWherePart+
		                  ") " + strSql1 +
		                  ") bv , PrpJpoaInfo av " +
		                  " where bv.poacode = av.poacode " + strSql2 +
		                  " order by bv.poacode "; 
						  
        Collection yeePayschema = new ArrayList();
        Collection yeePayschemapre = new ArrayList();
        
        PrpJplanFeePreSchema prpJplanFeePreSchemapre = null;
        PrpJplanFeePreSchema prpJplanFeePreSchema1 = null;
        PrpJplanFeePreSchema prpJplanFeePreSchema2 = null;
        DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
        BLPrpJpoaInfo blPrpJpoaInfo = new BLPrpJpoaInfo();
        String certino ="";
        String certino1 ="";
        ChgDate idate = new ChgDate();
        String strDate  = idate.getCurrentTime("yyyy-MM-dd");
        com.sp.utility.string.Date sinosoftDate = new com.sp.utility.string.Date();
        
        
        sinosoftDate.set(sinosoftDate.DATE,sinosoftDate.get(sinosoftDate.DATE)+10);
        
       String strPreWeekDate = "";
       
		DbPool dbpool = new DbPool();
        
        
        String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
        if("0".equals(payment_switch)){
        	dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        }else{
        	dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
        }
        
		try
		{
			
			PrpJplanFeePreSchema prpJplanFeePreSchema = null;
			
			ResultSet resultSet = null;
	        if (bindValues != null && bindValues.size()>0) {
	        	dbpool.prepareInnerStatement(strSQL);
	        	for (int i = 0; i < bindValues.size(); i++) {
	        		dbpool.setString(i + 1, (String) bindValues.get(i));
	        	}
	        	resultSet = dbpool.executePreparedQuery();
	        } else {
	        	resultSet = dbpool.query(strSQL);
	        }
            
	        
            while(resultSet.next())
            {
 
            	prpJplanFeePreSchema = new PrpJplanFeePreSchema();
            	prpJplanFeePreSchema.setComCode(resultSet.getString("comcode"));
            	prpJplanFeePreSchema.setCenterCode(resultSet.getString("centercode"));
            	prpJplanFeePreSchema.setCertiNo(resultSet.getString("certino"));
            	prpJplanFeePreSchema.setPolicyNo(resultSet.getString("policyno"));
				
            	prpJplanFeePreSchema.setOperateDate((resultSet.getTimestamp("accdate")).toString());
            	
				prpJplanFeePreSchema.setAttribute2((resultSet.getTimestamp("cms_return_date")).toString().substring(0,19));
            	
            	prpJplanFeePreSchema.setInsuredCode((resultSet.getTimestamp("zqhkr")).toString().substring(0,10)+" 23:59:59");
            	prpJplanFeePreSchema.setInsuredName((resultSet.getTimestamp("zhhkr")).toString().substring(0,10)+" 23:59:59");
            	prpJplanFeePreSchema.setPoaCode(resultSet.getString("poacode"));
            	
            	prpJplanFeePreSchema.setPremiumPlanFee(resultSet.getString("totalfee"));
            	
				prpJplanFeePreSchema.setCertiStatus("已垫付");
            	prpJplanFeePreSchema.setPlanFee(resultSet.getString("sunpayfee"));
            	
            	prpJplanFeePreSchema.setPrePayRefFee(resultSet.getString("suninterest"));
            	
            	prpJplanFeePreSchema.setVSCardFlag(resultSet.getString("attribute12"));
            	prpJplanFeePreSchema.setAttribute1(resultSet.getString("account"));
            	DBPrpDcompany dbPrpDCompany = new DBPrpDcompany();
               	dbPrpDCompany.getInfo(prpJplanFeePreSchema.getComCode());
            	prpJplanFeePreSchema.setComName(dbPrpDCompany.getComCName());
            	prpJplanFeePreSchema.setChannelType(resultSet.getString("ChannelType"));
            	prpJplanFeePreSchema.setAgentName(resultSet.getString("status"));

            	if("0".equals(prpJplanFeePreSchema.getPlanFee()))
            		prpJplanFeePreSchema.setPlanFee("");
            	if("0".equals(prpJplanFeePreSchema.getPrePayRefFee()))
            		prpJplanFeePreSchema.setPrePayRefFee("");
            	
            	if("1111-11-11 00:00:00".equals(prpJplanFeePreSchema.getAttribute2()))
            	    prpJplanFeePreSchema.setAttribute2("");
    
            	if("已还款".equals(prpJplanFeePreSchema.getAgentName())){
					
            		prpJplanFeePreSchema.setAgentCode(resultSet.getString("totalfee"));
            		
				
            	}else{
            		prpJplanFeePreSchema.setAgentCode("");
            	   prpJplanFeePreSchema.setAgentName("");
            	}
            	
            	prpJplanFeePreSchema.setFrameNo("否");
            	/*if("".equals(prpJplanFeePreSchema.getPrePayRefFee()) || null == prpJplanFeePreSchema.getPrePayRefFee())
            		{
            		prpJplanFeePreSchema.setFrameNo("");
            		}
            	else if(Double.parseDouble(prpJplanFeePreSchema.getPrePayRefFee())>0 )
            		prpJplanFeePreSchema.setFrameNo("是");
            	else
            		prpJplanFeePreSchema.setFrameNo("否");*/
            	
            	if(prpJplanFeePreSchema.getChannelType().equals("N073") || prpJplanFeePreSchema.getChannelType().equals("N074") )
            		prpJplanFeePreSchema.setClassCode("电销-DM");
            		else if(prpJplanFeePreSchema.getChannelType().equals("N071") || prpJplanFeePreSchema.getChannelType().equals("N072")
            				|| prpJplanFeePreSchema.getChannelType().equals("N075")|| prpJplanFeePreSchema.getChannelType().equals("N07"))
            			prpJplanFeePreSchema.setClassCode("电销-非DM");
            		else if(prpJplanFeePreSchema.getChannelType().equals("N10"))
            			prpJplanFeePreSchema.setClassCode("网销");
            		else prpJplanFeePreSchema.setClassCode("其它");
            		
            	
            	if("93".equals(posmodelno)){
            		prpJplanFeePreSchema.setHandler1Name("易宝垫付");
            	}else if("94".equals(posmodelno)){
            		prpJplanFeePreSchema.setHandler1Name("快钱垫付");
            	}
            	
            	this.setArr(prpJplanFeePreSchema);
            }
            resultSet.close();
            
            if(this.schemas.size()>0)
            {
            	for(int i= 0 ; i<this.schemas.size();)
            	{
            		prpJplanFeePreSchema1 = new PrpJplanFeePreSchema();
            		prpJplanFeePreSchema1 = this.getArr(i);
            		
            		
            		System.out.println(prpJplanFeePreSchema1.getPoaCode());
            		if(i<this.schemas.size()-1)
            		{
                		for(int j=i+1 ; j<this.schemas.size();j++)
                		{
                			prpJplanFeePreSchema2 = new PrpJplanFeePreSchema();
                			prpJplanFeePreSchema2 = this.getArr(j);
                			if(prpJplanFeePreSchema1.getPoaCode().equals(prpJplanFeePreSchema2.getPoaCode())){
                				prpJplanFeePreSchema1.setCertiNo(prpJplanFeePreSchema1.getCertiNo()+";"+prpJplanFeePreSchema2.getCertiNo());
                				prpJplanFeePreSchema1.setPolicyNo(prpJplanFeePreSchema1.getPolicyNo()+";"+prpJplanFeePreSchema2.getPolicyNo());
                			}else{
                				yeePayschema.add(prpJplanFeePreSchema1);
                				i=j;
                				j=this.schemas.size();
                				continue;
                				
                			}
                			
                			i=j+1;
                			if(j==this.schemas.size()-1)
                			{
                				yeePayschema.add(prpJplanFeePreSchema1);
                			}
                			
                		}	
            		}else{
            			yeePayschema.add(prpJplanFeePreSchema1);
            			i++;
            		}
            		
            		
            	}
            }
		}
		catch(Exception exception)
		{
			throw exception;
		}
		finally
		{
			 dbpool.close();
		}
		
		return yeePayschema;
	}	
		
	/**
	 * @desc 根据XX号查询是否为随车业务，如果是则获取所有关联的信息
	 * @author xujianchao
	 * @param policyNo XX号
	 * @throws UserException
	 * @throws Exception
	 */
	public String  getDataByPolicyNoFor0917(String[] policyNoList)
			throws UserException, Exception {
		DbPool dbpool = new DbPool();
		String StrReturn ="";
		String iWherePart ="";
		String iWherePartReturn =" and policyno in ('policyno'";
		List list = null;
		try {
            
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
			dbpool.beginTransaction();
			
			for(int i=0;i<policyNoList.length;i++){
				iWherePart = " relievingareacode='D' and relievingcertino in (select nvl(relievingcertino,'relievingcertino') from prpjplanfeepre where policyno ='" + policyNoList[i].trim() + "') ";
				list = this.getDataByPolicyNoFor0917(dbpool, iWherePart);
				String riskCodes = "";
				





					for(int z=0;z<list.size();z++){
						PrpJplanFeePreSchema prpJplanFeePreSchemaInner = (PrpJplanFeePreSchema)list.get(z);
						if(!"".equals(prpJplanFeePreSchemaInner.getPolicyNo())){
							iWherePartReturn += ",'"+ prpJplanFeePreSchemaInner.getPolicyNo()+"'";
						}
					}

				this.getDataByPolicyNoFor0917(dbpool, iWherePart);
			}
			
			dbpool.commitTransaction();
			
			iWherePartReturn +=")";
			
		} catch (Exception e) {
			dbpool.rollbackTransaction();
			iWherePartReturn =" and policyno in ('policyno')";
			throw e;
		} finally {
			dbpool.close();
		}
		
		return iWherePartReturn;
	}
	
	/**
	 * 据XX号查询是否为随车业务，如果是则获取所有关联的信息
	 * 
	 * @param dbpool 全局池
	 * @param iWherePart 查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public List getDataByPolicyNoFor0917(DbPool dbpool, String iWherePart)
			throws UserException, Exception {
		String strSqlStatement = "";
		List list =null;
		DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();

		initArr();
		strSqlStatement = " SELECT * FROM PrpJplanFeePre WHERE "
				+ iWherePart;
		list = dbPrpJplanFeePre
				.findByConditions(dbpool, strSqlStatement);

		
		return list;
		
	}
	
    /**
	 * 递归查询二级机构以下所有的机构（核算单位递归向下查询）
	 * @param iWherePart
	 * @param iOrderBy 排序项
	 * @param bindValues 页面变量
	 * @throws Exception
	 */
    public String CenterCodeList (String strCenterCode) throws Exception{
    	String CenterCodeList = new String();
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
   	    
   	    String strSqlPrpDcompany = " centerflag='1' " +
   	  							 "start with comcode = '" + strCenterCode +"' " +
   	  							 "connect by nocycle prior comcode = uppercomcode " +
   	  							 "and prior comcode != comcode " +
   	  							 "and validstatus='1' ";
   	    blPrpDcompany.query(strSqlPrpDcompany,0);
   	    CenterCodeList = "(";
   	    if(blPrpDcompany.getSize()>0){
   	    	for(int i=0;i<blPrpDcompany.getSize();i++){
   	    		if(i<blPrpDcompany.getSize()-1){
   	    			CenterCodeList = CenterCodeList+"'"+blPrpDcompany.getArr(i).getComCode()+"',";
   	    		}else{
   	    			CenterCodeList = CenterCodeList+"'"+blPrpDcompany.getArr(i).getComCode()+"')";
   	    		}
   	    	}
   	    }
   	    return CenterCodeList;
    }
    /**
     * 易宝垫付查询优化
     * @param StrAccStartDate
     * @param StrAccEndDate
     * @param StrCenterCode
     * @throws Exception
     */
	public Collection JEPayPosMode93QueryForCenterCode(String StrAccStartDate,String StrAccEndDate,String StrCenterCode,Map map) throws Exception
	{	
		String strCondition = (String) map.get("Condition");
		String strCondition1 = (String) map.get("Condition1");
		ArrayList bindValues = (ArrayList) map.get("BindValues");
		String strNoPayCount = (String) map.get("NoPayCount");
		String strpayforStartDate = (String)map.get("payforStartDate");
		String strpayforEndDate = (String)map.get("payforEndDate");
		String strQueryScope = (String)map.get("QueryScope");
		BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
		if("1".equals(strQueryScope)){
	   	    
			
	   	    String strSqlPrpDcompany = " centerflag='1' " +
	   	  							 "start with comcode = '" + StrCenterCode +"' " +
	   	  							 "connect by nocycle prior comcode = uppercomcode " +
	   	  							 "and prior comcode != comcode " +
	   	  							 "and validstatus='1' and comcode not in  ("+
	   	  							 "SELECT comcode FROM prpdcompany a where  "+
	   	  							 "((a.comcodeflag = '1' and substr(comcode, 5, 1) = 'A') or comcode like '40%') "+
	   	  							 "and centerflag = '1')";
	   	    
	   	    blPrpDcompany.query(strSqlPrpDcompany,0);
		}else{
			String strSqlPrpDcompany = " comcode='"+StrCenterCode+"' ";
			blPrpDcompany.query(strSqlPrpDcompany,0);
		}
		String strWherePart = " and 1 = 1 ";
		String strWherePartKQ = " and 1 = 1 ";
		String strWherePartYB = " and 1 = 1 ";
		String strSql1 = " and 1 = 1 ";
		String strSql2 = " and 1 = 1 ";
		String strAdd ="";
		String strAddKQ ="";
		if (strNoPayCount != null && !"".equals(strNoPayCount)) {
			strWherePart += " and ceil(TO_DATE(TO_CHAR(SYSDATE, 'YYYY-MM-DD'), 'YYYY-MM-DD') - a.accdate) >= '"+ strNoPayCount + "'";
		}
		if ((bindValues != null && bindValues.size()>0) ||(strpayforStartDate != null && !"".equals(strpayforStartDate)) ||  (strpayforEndDate != null && !"".equals(strpayforEndDate))){
			strSql1 += strCondition;
		}
		if (strCondition1 != null && !"".equals(strCondition1)) {
			strSql2 += strCondition1;
		}
		
		String strpoaCode = (String)map.get("poaCode");
		if (strpoaCode != null && !"".equals(strpoaCode)) {
			strWherePart += " and a.poaCode = '"+ strpoaCode + "'";
		}
		String posmodelno = (String)map.get("posmodelno");
		if (posmodelno != null && !"".equals(posmodelno)) {
			if("all".equals(posmodelno)){
				strWherePartYB =strWherePart + " and a.posmodelno  = '93'";
				strWherePartKQ = strWherePart + " and a.posmodelno  = '94'";
			}else{
				strWherePart += " and a.posmodelno = '"+ posmodelno + "'";
			}
		}
		
		
		if("93".equals(posmodelno)){
			strAdd = "vi_yeepay_detail";
		}else if("94".equals(posmodelno)){
			strAdd = "vi_bill99_detail";
		}else if("all".equals(posmodelno)){
			strAdd = "vi_yeepay_detail";
			strAddKQ = "vi_bill99_detail";
		}
		String strSQL ="";
		String strSQLKQ ="";
        Collection yeePayschema = new ArrayList();
        Collection yeePayschemapre = new ArrayList(); 
        PrpJplanFeePreSchema prpJplanFeePreSchemapre = null;
        PrpJplanFeePreSchema prpJplanFeePreSchema1 = null;
        PrpJplanFeePreSchema prpJplanFeePreSchema2 = null;
        DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
        BLPrpJpoaInfo blPrpJpoaInfo = new BLPrpJpoaInfo();
        String certino ="";
        String certino1 ="";
        ChgDate idate = new ChgDate();
        String strDate  = idate.getCurrentTime("yyyy-MM-dd");
        com.sp.utility.string.Date sinosoftDate = new com.sp.utility.string.Date();
        sinosoftDate.set(sinosoftDate.DATE,sinosoftDate.get(sinosoftDate.DATE)+10);
        
        String strPreWeekDate = "";
		DbPool dbpool = new DbPool();
        
        
        String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
        if("0".equals(payment_switch)){
        	dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        }else{
        	dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
        }
        
		try
		{
			
			ResultSet resultSet = null;
			for(int k=0;k<blPrpDcompany.getSize();k++){
				if("93".equals(posmodelno)||"94".equals(posmodelno)){
					strSQL =   YBKQ(strAdd) +
					                  " on t1.poacode = t2.PAY_ID  where" +
					                  " t1.poacode in " +
					                  " (select a.poacode" +
					                  " from PrpJpoaInfo a" +
					                  " where a.centercode = '" +blPrpDcompany.getArr(k).getComCode()+"' "+
					                  " and a.accdate between date '"+StrAccStartDate+"' and date'" +StrAccEndDate+"'+1"+
					                  " and a.poatype in ('2','O')" +
					                  " and a.accflag = '1'" +
					                  strWherePart+
					                  ") " + strSql1 +
					                  ") bv , PrpJpoaInfo av " +
					                  " where bv.poacode = av.poacode " + strSql2 +
					                  " order by bv.poacode ";
				}else if ("all".equals(posmodelno)){
					strSQL =   YBKQ(strAdd) +
				            " on t1.poacode = t2.PAY_ID  where" +
				            " t1.poacode in " +
				            " (select a.poacode" +
				            " from PrpJpoaInfo a" +
				            " where a.centercode = '" +blPrpDcompany.getArr(k).getComCode()+"' "+
				            " and a.accdate between date '"+StrAccStartDate+"' and date'" +StrAccEndDate+"'+1"+
				            " and a.poatype in ('2','O')" +
				            " and a.accflag = '1'" +
				            strWherePartYB+
				            ") " + strSql1 +
				            ") bv , PrpJpoaInfo av " +
				            " where bv.poacode = av.poacode " + strSql2 +
				            " order by bv.poacode ";
					strSQLKQ =   YBKQ(strAddKQ) +
				            " on t1.poacode = t2.PAY_ID  where" +
				            " t1.poacode in " +
				            " (select a.poacode" +
				            " from PrpJpoaInfo a" +
				            " where a.centercode = '" +blPrpDcompany.getArr(k).getComCode()+"' "+
				            " and a.accdate between date '"+StrAccStartDate+"' and date'" +StrAccEndDate+"'+1"+
				            " and a.poatype in ('2','O')" +
				            " and a.accflag = '1'" +
				            strWherePartKQ+
				            ") " + strSql1 +
				            ") bv , PrpJpoaInfo av " +
				            " where bv.poacode = av.poacode " + strSql2 +
				            " order by bv.poacode ";
				}
				PrpJplanFeePreSchema prpJplanFeePreSchema = null;
		        if (bindValues != null && bindValues.size()>0) {
		        	dbpool.prepareInnerStatement(strSQL);
		        	for (int i = 0; i < bindValues.size(); i++) {
		        		dbpool.setString(i + 1, (String) bindValues.get(i));
		        	}
		        	resultSet = dbpool.executePreparedQuery();
		        } else {
		        	resultSet = dbpool.query(strSQL);
		        }
	            while(resultSet.next())
	            {
	            	prpJplanFeePreSchema = new PrpJplanFeePreSchema();
	            	prpJplanFeePreSchema.setComCode(resultSet.getString("comcode"));
	            	prpJplanFeePreSchema.setCenterCode(resultSet.getString("centercode"));
	            	prpJplanFeePreSchema.setCertiNo(resultSet.getString("certino"));
	            	prpJplanFeePreSchema.setPolicyNo(resultSet.getString("policyno"));
	            	prpJplanFeePreSchema.setOperateDate((resultSet.getTimestamp("accdate")).toString());
					prpJplanFeePreSchema.setAttribute2((resultSet.getTimestamp("cms_return_date")).toString().substring(0,19));
	            	prpJplanFeePreSchema.setInsuredCode((resultSet.getTimestamp("zqhkr")).toString().substring(0,10)+" 23:59:59");
	            	prpJplanFeePreSchema.setInsuredName((resultSet.getTimestamp("zhhkr")).toString().substring(0,10)+" 23:59:59");
	            	prpJplanFeePreSchema.setPoaCode(resultSet.getString("poacode"));
	            	prpJplanFeePreSchema.setPremiumPlanFee(resultSet.getString("totalfee"));
					prpJplanFeePreSchema.setCertiStatus("已垫付");
	            	prpJplanFeePreSchema.setPlanFee(resultSet.getString("sunpayfee"));
	            	prpJplanFeePreSchema.setPrePayRefFee(resultSet.getString("suninterest"));
	            	prpJplanFeePreSchema.setVSCardFlag(resultSet.getString("attribute12"));
	            	prpJplanFeePreSchema.setAttribute1(resultSet.getString("account"));
	            	DBPrpDcompany dbPrpDCompany = new DBPrpDcompany();
	               	dbPrpDCompany.getInfo(prpJplanFeePreSchema.getComCode());
	            	prpJplanFeePreSchema.setComName(dbPrpDCompany.getComCName());
	            	prpJplanFeePreSchema.setChannelType(resultSet.getString("ChannelType"));
	            	prpJplanFeePreSchema.setAgentName(resultSet.getString("status"));
	            	if("0".equals(prpJplanFeePreSchema.getPlanFee()))
	            		prpJplanFeePreSchema.setPlanFee("");
	            	if("0".equals(prpJplanFeePreSchema.getPrePayRefFee()))
	            		prpJplanFeePreSchema.setPrePayRefFee("");
	            	
	            	if("1111-11-11 00:00:00".equals(prpJplanFeePreSchema.getAttribute2()))
	            	    prpJplanFeePreSchema.setAttribute2("");
	    
	            	if("已还款".equals(prpJplanFeePreSchema.getAgentName())){
	            		prpJplanFeePreSchema.setAgentCode(resultSet.getString("totalfee"));
	            	}else{
	            		prpJplanFeePreSchema.setAgentCode("");
	            	   prpJplanFeePreSchema.setAgentName("");
	            	}
	            	prpJplanFeePreSchema.setFrameNo("否");
	            	if(prpJplanFeePreSchema.getChannelType().equals("N073") || prpJplanFeePreSchema.getChannelType().equals("N074") )
	            		prpJplanFeePreSchema.setClassCode("电销-DM");
	            		else if(prpJplanFeePreSchema.getChannelType().equals("N071") || prpJplanFeePreSchema.getChannelType().equals("N072")
	            				|| prpJplanFeePreSchema.getChannelType().equals("N075")|| prpJplanFeePreSchema.getChannelType().equals("N07"))
	            			prpJplanFeePreSchema.setClassCode("电销-非DM");
	            		else if(prpJplanFeePreSchema.getChannelType().equals("N10"))
	            			prpJplanFeePreSchema.setClassCode("网销");
	            		else prpJplanFeePreSchema.setClassCode("其它");
	            	if("93".equals(posmodelno)||"all".equals(posmodelno)){
	            		prpJplanFeePreSchema.setHandler1Name("易宝垫付");
	        		}else if("94".equals(posmodelno)){
	        			prpJplanFeePreSchema.setHandler1Name("快钱垫付");
	        		}
	            	this.setArr(prpJplanFeePreSchema);
	            }
				if("all".equals(posmodelno)){
					if (bindValues != null && bindValues.size()>0) {
			        	dbpool.prepareInnerStatement(strSQLKQ);
			        	for (int i = 0; i < bindValues.size(); i++) {
			        		dbpool.setString(i + 1, (String) bindValues.get(i));
			        	}
			        	resultSet = dbpool.executePreparedQuery();
			        } else {
			        	resultSet = dbpool.query(strSQLKQ);
			        }
		            while(resultSet.next())
		            {
		            	prpJplanFeePreSchema = new PrpJplanFeePreSchema();
		            	prpJplanFeePreSchema.setComCode(resultSet.getString("comcode"));
		            	prpJplanFeePreSchema.setCenterCode(resultSet.getString("centercode"));
		            	prpJplanFeePreSchema.setCertiNo(resultSet.getString("certino"));
		            	prpJplanFeePreSchema.setPolicyNo(resultSet.getString("policyno"));
		            	prpJplanFeePreSchema.setOperateDate((resultSet.getTimestamp("accdate")).toString());
						prpJplanFeePreSchema.setAttribute2((resultSet.getTimestamp("cms_return_date")).toString().substring(0,19));
		            	prpJplanFeePreSchema.setInsuredCode((resultSet.getTimestamp("zqhkr")).toString().substring(0,10)+" 23:59:59");
		            	prpJplanFeePreSchema.setInsuredName((resultSet.getTimestamp("zhhkr")).toString().substring(0,10)+" 23:59:59");
		            	prpJplanFeePreSchema.setPoaCode(resultSet.getString("poacode"));
		            	prpJplanFeePreSchema.setPremiumPlanFee(resultSet.getString("totalfee"));
						prpJplanFeePreSchema.setCertiStatus("已垫付");
		            	prpJplanFeePreSchema.setPlanFee(resultSet.getString("sunpayfee"));
		            	prpJplanFeePreSchema.setPrePayRefFee(resultSet.getString("suninterest"));
		            	prpJplanFeePreSchema.setVSCardFlag(resultSet.getString("attribute12"));
		            	prpJplanFeePreSchema.setAttribute1(resultSet.getString("account"));
		            	DBPrpDcompany dbPrpDCompany = new DBPrpDcompany();
		               	dbPrpDCompany.getInfo(prpJplanFeePreSchema.getComCode());
		            	prpJplanFeePreSchema.setComName(dbPrpDCompany.getComCName());
		            	prpJplanFeePreSchema.setChannelType(resultSet.getString("ChannelType"));
		            	prpJplanFeePreSchema.setAgentName(resultSet.getString("status"));
		            	if("0".equals(prpJplanFeePreSchema.getPlanFee()))
		            		prpJplanFeePreSchema.setPlanFee("");
		            	if("0".equals(prpJplanFeePreSchema.getPrePayRefFee()))
		            		prpJplanFeePreSchema.setPrePayRefFee("");
		            	
		            	if("1111-11-11 00:00:00".equals(prpJplanFeePreSchema.getAttribute2()))
		            	    prpJplanFeePreSchema.setAttribute2("");
		    
		            	if("已还款".equals(prpJplanFeePreSchema.getAgentName())){
		            		prpJplanFeePreSchema.setAgentCode(resultSet.getString("totalfee"));
		            	}else{
		            		prpJplanFeePreSchema.setAgentCode("");
		            	   prpJplanFeePreSchema.setAgentName("");
		            	}
		            	prpJplanFeePreSchema.setFrameNo("否");
		            	if(prpJplanFeePreSchema.getChannelType().equals("N073") || prpJplanFeePreSchema.getChannelType().equals("N074") )
		            		prpJplanFeePreSchema.setClassCode("电销-DM");
		            		else if(prpJplanFeePreSchema.getChannelType().equals("N071") || prpJplanFeePreSchema.getChannelType().equals("N072")
		            				|| prpJplanFeePreSchema.getChannelType().equals("N075")|| prpJplanFeePreSchema.getChannelType().equals("N07"))
		            			prpJplanFeePreSchema.setClassCode("电销-非DM");
		            		else if(prpJplanFeePreSchema.getChannelType().equals("N10"))
		            			prpJplanFeePreSchema.setClassCode("网销");
		            		else prpJplanFeePreSchema.setClassCode("其它");
		            	prpJplanFeePreSchema.setHandler1Name("快钱垫付");	
		            	this.setArr(prpJplanFeePreSchema);
		            }
				}
			}
			
            resultSet.close();
            
            if(this.schemas.size()>0)
            {
            	for(int i= 0 ; i<this.schemas.size();)
            	{
            		prpJplanFeePreSchema1 = new PrpJplanFeePreSchema();
            		prpJplanFeePreSchema1 = this.getArr(i);
            		if(i<this.schemas.size()-1)
            		{
                		for(int j=i+1 ; j<this.schemas.size();j++)
                		{
                			prpJplanFeePreSchema2 = new PrpJplanFeePreSchema();
                			prpJplanFeePreSchema2 = this.getArr(j);
                			if(prpJplanFeePreSchema1.getPoaCode().equals(prpJplanFeePreSchema2.getPoaCode())){
                				prpJplanFeePreSchema1.setCertiNo(prpJplanFeePreSchema1.getCertiNo()+";"+prpJplanFeePreSchema2.getCertiNo());
                				prpJplanFeePreSchema1.setPolicyNo(prpJplanFeePreSchema1.getPolicyNo()+";"+prpJplanFeePreSchema2.getPolicyNo());
                			}else{
                				yeePayschema.add(prpJplanFeePreSchema1);
                				i=j;
                				j=this.schemas.size();
                				continue;
                			}
                			
                			i=j+1;
                			if(j==this.schemas.size()-1)
                			{
                				yeePayschema.add(prpJplanFeePreSchema1);
                			}
                		}	
            		}else{
            			yeePayschema.add(prpJplanFeePreSchema1);
            			i++;
            		}
            		
            		
            	}
            }
		}
		catch(Exception exception)
		{
			throw exception;
		}
		finally
		{
			 dbpool.close();
		}
		return yeePayschema;
	}
	public String YBKQ (String strAdd) throws Exception{
		String strSQL =   " select bv.comcode," +
		" bv.centercode," +
		" bv.certino," +
		" bv.policyno," +
		" av.accdate," +
		" nvl(av.accdate+14,'1111-11-11') as zqhkr, " +
		" nvl(av.accdate+29,'1111-11-11') as zhhkr, " +
		" bv.cms_return_date," +
		" bv.poacode,  " +
		" av.totalfee," +
		" decode(bv.status,'','已垫付',bv.status) as status," +
		" bv.sunpayfee," +
		" bv.suninterest," +
		" bv.attribute12," +
		" bv.account," +
		" bv.ChannelType  from (" +
			" select t1.comcode," +
				          " t1.centercode," +
				          " t1.certino," +
				          " t1.policyno," +
				          " nvl(t2.dishonor_date,'1111-11-11') as dishonor_date," +
				          " nvl(t2.dishonor_date+14,'1111-11-11') as zqhkr," + 
				          " nvl(t2.dishonor_date+29,'1111-11-11') as zhhkr," + 
		                  " nvl(t2.cms_return_date,'1111-11-11') as cms_return_date," +
				          " t1.poacode," +  
				          " nvl(t2.amount,'') as amount,"+ 
				          " nvl(t2.status,'') as status,"+ 
				          " nvl(t2.sunpayfee,'') as sunpayfee,"+ 
				          " nvl(t2.suninterest,'') as suninterest,"+
				          " nvl(t2.attribute12,'') as attribute12,"+
				          " nvl(t2.account,'') as account,"+
				          " t1.ChannelType" +
		                  " from prpjplanfeepre t1  LEFT JOIN  " + strAdd + " t2" ;
		return strSQL;
	}
	/**
     * 易宝垫付查询分页显示方法
     * @param intPageNum  第几页
     * @param intPageCount 每页数据最多100 
     * @param yeePayschema1  集合中一共有多少数据 
     * @param intPageCountMax  集合中数据最多分几页
     * @throws Exception
     */
	public Collection checkPageSplit(int intPageCountMax,int intPageNum,int intPageCount,Collection yeePayschema1) throws Exception
    {
		Collection yeePayschema = new  ArrayList();
		ArrayList yeePayschemaList = new  ArrayList();
		int intStartNum = 0;
	    int intEndNum = 0;
	    intStartNum = (intPageNum - 1) * intPageCount;
	    if(intPageNum<intPageCountMax){
	    	intEndNum = intPageNum * intPageCount;
	    }else if (intPageNum==intPageCountMax){
	    	intEndNum = yeePayschema1.size();
	    }
	    for (Iterator j = yeePayschema1.iterator(); j.hasNext();) {
	    	yeePayschemaList.add((PrpJplanFeePreSchema)j.next());
	    }
	    for(int i =intStartNum;i<intEndNum;i++){
	    	yeePayschema.add(yeePayschemaList.get(i));
	    }
	    return yeePayschema;
    }
    
	
	/**
	 * 查询PrpJpalnFeePre表信息，schemas内包括拓展的信息，如归属机构名称，代理人名称等
	 * @param conditions
	 * @author 徐光远 
	 * @version 1.0 Dec 4, 2007 2:48:45 PM
	 * @throws Exception 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public void findExpendSchemas(String conditions, int limitCount,ArrayList bindValues) throws Exception {
        DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
        DBPrpDagent dbPrpDagent = new DBPrpDagent();
        DBPrpDuser dbPrpDuser = new DBPrpDuser();
		DbPool dbpool = new DbPool();
        
        
        
		
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
		try {
			queryNew(dbpool, conditions, limitCount,bindValues);
			if (!schemas.isEmpty()) {
				for (Iterator iterator = schemas.iterator(); iterator.hasNext();) {
					PrpJplanFeePreSchema schema = (PrpJplanFeePreSchema) iterator.next();
					if (StringUtils.isNotEmpty(schema.getComCode())) {
						
						dbPrpDcompany.getInfo(dbpool, schema.getComCode());
						schema.setComName(dbPrpDcompany.getComCName());
					}
					if (StringUtils.isNotEmpty(schema.getAgentCode())) {
						
						dbPrpDagent.getInfo(dbpool,schema.getAgentCode());
						schema.setAgentName(dbPrpDagent.getAgentName());
					}
					if (StringUtils.isNotEmpty(schema.getHandler1Code())) {
						
						dbPrpDuser.getInfo(dbpool, schema.getHandler1Code());
						schema.setHandler1Name(dbPrpDuser.getUserName());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dbpool.close();
		}
	}
	/**
	 * 
	 * @param dbpool 全局池
	 * @param iWherePart 查询条件(包括排序字句)
	 * @param iLimitCount 记录数限制(iLimitCount=0: 无限制)
	 * @throws UserException
	 * @throws Exception
	 */
	public void queryNew(DbPool dbpool, String iWherePart, int iLimitCount,ArrayList bindValues)
			throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
		if (iLimitCount > 0
				&& dbPrpJplanFeePre.getCount(iWherePart,bindValues) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJplanFeePre.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJplanFeePre WHERE "
					+ iWherePart;
			schemas = dbPrpJplanFeePre
					.findByConditions(dbpool, strSqlStatement,bindValues);
		}
	}
	
	/**
	 * @desc 日志输出
	 * @param iLog
	 * @throws Exception
	 */
	
	
	public void logPrintln(String iLog) throws Exception {
		ChgDate chgDate = new ChgDate();
		logger.info(chgDate.getCurrentTime("yyyy-MM-dd hh:mm:ss") + "><"+ iLog);
	}
	/**
	 * 主函数
	 * 
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		
	}
}
