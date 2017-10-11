package com.sp.client.bl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.math.*;
import com.sp.sysframework.exceptionlog.UserException;
import com.sp.sysframework.reference.DBManager;
import com.sp.client.dto.*;
import java.util.*;
import com.sp.prpall.resource.dtofactory.domain.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BLCheckValid {
	Log logger = LogFactory.getLog(getClass());
	public void checkDbData ( Policy policy , DBManager dbManager) throws UserException{
		BaseInfoBean baseInfoBean = policy.getbaseInfoBase();
		VhlInfoBean vhlInfoBean = policy.getvhlInfoBean();
		int iCount = 0;
		DBPrpCmain dBPrpCmain = new DBPrpCmain(dbManager);
		try{
			iCount = dBPrpCmain.getCount("policyno='"+baseInfoBean.getPlyNo()+"'");
		}catch(Exception plyNoE){
			plyNoE.printStackTrace(System.out);
				throw new UserException(-98, -1007, this.getClass().getName(),
					"检索XX号：" + baseInfoBean.getPlyNo()
							+ "出错！"+plyNoE.getMessage());
		}
		if ( iCount > 0 ){
			throw new UserException(-98, -1007, this.getClass().getName(),
					"XX号：" + baseInfoBean.getPlyNo()
							+ "已经存在！");
		}
		DBPrpTmain dBPrpTmain = new DBPrpTmain(dbManager);
		try{
			iCount = dBPrpTmain.getCount("proposalno='"+baseInfoBean.getPlyAppNo()+"'");
		}catch(Exception plyNoE){
			throw new UserException(-98, -1007, this.getClass().getName(),
					"检索XX单号：" + baseInfoBean.getPlyAppNo()
							+ "出错！");
		}
		
		logger.info(iCount);
		
		if ( iCount > 0 ){
			throw new UserException(-98, -1007, this.getClass().getName(),
					"XX单号：" + baseInfoBean.getPlyAppNo()
							+ "已经存在！");
		}
		DBPrpCitemCar dBPrpCitemCar = new DBPrpCitemCar(dbManager);
		try{
			iCount = dBPrpCitemCar.getCount("licenseno='"+vhlInfoBean.getC_LCN_NO()
					+"' and engineno='"+vhlInfoBean.getC_ENG_NO()
					+"' and frameno='"+vhlInfoBean.getC_VHL_FRM()
					+"' and vinno='"+vhlInfoBean.getC_VHL_VIN()+"'");
		}catch(Exception plyNoE){
			throw new UserException(-98, -1007, this.getClass().getName(),
					"检索XX车辆信息出错，车牌号：" + vhlInfoBean.getC_LCN_NO()
							+ "！");
		}
		if ( iCount > 0 ){
			throw new UserException(-98, -1007, this.getClass().getName(),
					"XX车辆已经XX，车牌号：" + vhlInfoBean.getC_LCN_NO()
							+ "，发动机号：" + vhlInfoBean.getC_ENG_NO()
							+ "，车架号：" + vhlInfoBean.getC_VHL_FRM()
							+ "，VIN号：" + vhlInfoBean.getC_VHL_VIN()
							+ "！");
		}
	}
	public void checkForm(Policy policy) throws Exception {
		checkPlan();
		checkEngage();
		BaseInfoBean baseInfoBean = policy.getbaseInfoBase();
		checkExpenses(baseInfoBean);
		checkOther(baseInfoBean.getAppDate(),baseInfoBean.getInsbgnDate());
	}

	
	
	public void checkPlan() throws Exception {
		
	}

	
	public void checkEngage() throws Exception {
		
	}

	
	public void checkExpenses(BaseInfoBean bib) throws Exception {
		float dbManageFeeRate = 0;
		float dbDisRate1 = 0;
		float dbPureRate = 0;

		

		dbManageFeeRate = 0;
		dbDisRate1 = 0;
		dbPureRate = 0;

		if (dbManageFeeRate > 0) 
		{
			
			
		} else if (dbDisRate1 > 0) 
		{
			
			
		}
		if (dbDisRate1 > 0) 
		{
			
		}

	}

	public void checkExpensesForManageFeeRate(float DisRate,
			float MaxManageFeeRate, float ManageFeeRate) throws UserException {
		float dbDisRate = 0;
		float dbMaxManageFeeRate = 0;
		float dbManageFeeRate = 0;
		
		dbDisRate = DisRate;
		dbMaxManageFeeRate = MaxManageFeeRate;
		dbManageFeeRate = ManageFeeRate;

		if (dbDisRate > dbMaxManageFeeRate) {
			throw new UserException(-98, -1007, this.getClass().getName(),
					"手续/经纪费比例不能超过最大费用比例（" + Float.toString(dbMaxManageFeeRate)
							+ "）！");
		}
		if (dbManageFeeRate > dbMaxManageFeeRate) {
			throw new UserException(-98, -1007, this.getClass().getName(),
					"管理费比例不能超过最大费用比例（" + Float.toString(dbMaxManageFeeRate)
							+ "）！");
		}
		if (Math.round((dbDisRate + dbManageFeeRate) * 10000) > dbMaxManageFeeRate * 10000) {
			throw new UserException(-98, -1007, this.getClass().getName(),
					"手续/经纪费比例和管理费比例之和不能超过最大费用比例（"
							+ Float.toString(dbMaxManageFeeRate) + "）！");
		}
	}

	public void checkExpensesForMiddleCost(float dbDisRate, float dbDisRate1,
			float PremiumDisRate, float PremiumDisRate1, float SumPremium)
			throws UserException {
		if ((dbDisRate + dbDisRate1) > 100) {
			throw new UserException(-98, -1007, this.getClass().getName(),
					"手续/经纪费比例和特殊因子比例的和不能大于100，请检查费用信息！");
		}
		if (PremiumDisRate1 > SumPremium) {
			throw new UserException(-98, -1007, this.getClass().getName(),
					"特殊因子金额大于总XX，请重新填写特殊因子比例！");
		}
		if (PremiumDisRate < 0) {
			throw new UserException(-98, -1007, this.getClass().getName(),
					"手续/经纪费金额不能小于0，请检查总XX、特殊因子比例是否正确！");
		}
	}

	public void checkExpensesPureRate(float PureRate, float DisRate)
			throws UserException {
		boolean bThirdB = false;
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

	public void checkOther(String StartDate, String OperateDate)
			throws Exception {
		
		logger.info(StartDate);
		logger.info(OperateDate);
		logger.info("***************************");
		logger.info(compareFullDate(StartDate, OperateDate));
		




	}

	public static int compareFullDate(String date1, String date2)  {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-mm-dd");
		try {
			Date firstdate = bartDateFormat.parse(date1);
			Date seconddate = bartDateFormat.parse(date2);






			return firstdate.compareTo(seconddate);
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}
}
