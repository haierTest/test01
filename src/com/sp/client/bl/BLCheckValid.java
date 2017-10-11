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
					"����XX�ţ�" + baseInfoBean.getPlyNo()
							+ "����"+plyNoE.getMessage());
		}
		if ( iCount > 0 ){
			throw new UserException(-98, -1007, this.getClass().getName(),
					"XX�ţ�" + baseInfoBean.getPlyNo()
							+ "�Ѿ����ڣ�");
		}
		DBPrpTmain dBPrpTmain = new DBPrpTmain(dbManager);
		try{
			iCount = dBPrpTmain.getCount("proposalno='"+baseInfoBean.getPlyAppNo()+"'");
		}catch(Exception plyNoE){
			throw new UserException(-98, -1007, this.getClass().getName(),
					"����XX���ţ�" + baseInfoBean.getPlyAppNo()
							+ "����");
		}
		
		logger.info(iCount);
		
		if ( iCount > 0 ){
			throw new UserException(-98, -1007, this.getClass().getName(),
					"XX���ţ�" + baseInfoBean.getPlyAppNo()
							+ "�Ѿ����ڣ�");
		}
		DBPrpCitemCar dBPrpCitemCar = new DBPrpCitemCar(dbManager);
		try{
			iCount = dBPrpCitemCar.getCount("licenseno='"+vhlInfoBean.getC_LCN_NO()
					+"' and engineno='"+vhlInfoBean.getC_ENG_NO()
					+"' and frameno='"+vhlInfoBean.getC_VHL_FRM()
					+"' and vinno='"+vhlInfoBean.getC_VHL_VIN()+"'");
		}catch(Exception plyNoE){
			throw new UserException(-98, -1007, this.getClass().getName(),
					"����XX������Ϣ�������ƺţ�" + vhlInfoBean.getC_LCN_NO()
							+ "��");
		}
		if ( iCount > 0 ){
			throw new UserException(-98, -1007, this.getClass().getName(),
					"XX�����Ѿ�XX�����ƺţ�" + vhlInfoBean.getC_LCN_NO()
							+ "���������ţ�" + vhlInfoBean.getC_ENG_NO()
							+ "�����ܺţ�" + vhlInfoBean.getC_VHL_FRM()
							+ "��VIN�ţ�" + vhlInfoBean.getC_VHL_VIN()
							+ "��");
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
					"����/���ͷѱ������ܳ��������ñ�����" + Float.toString(dbMaxManageFeeRate)
							+ "����");
		}
		if (dbManageFeeRate > dbMaxManageFeeRate) {
			throw new UserException(-98, -1007, this.getClass().getName(),
					"����ѱ������ܳ��������ñ�����" + Float.toString(dbMaxManageFeeRate)
							+ "����");
		}
		if (Math.round((dbDisRate + dbManageFeeRate) * 10000) > dbMaxManageFeeRate * 10000) {
			throw new UserException(-98, -1007, this.getClass().getName(),
					"����/���ͷѱ����͹���ѱ���֮�Ͳ��ܳ��������ñ�����"
							+ Float.toString(dbMaxManageFeeRate) + "����");
		}
	}

	public void checkExpensesForMiddleCost(float dbDisRate, float dbDisRate1,
			float PremiumDisRate, float PremiumDisRate1, float SumPremium)
			throws UserException {
		if ((dbDisRate + dbDisRate1) > 100) {
			throw new UserException(-98, -1007, this.getClass().getName(),
					"����/���ͷѱ������������ӱ����ĺͲ��ܴ���100�����������Ϣ��");
		}
		if (PremiumDisRate1 > SumPremium) {
			throw new UserException(-98, -1007, this.getClass().getName(),
					"�������ӽ�������XX����������д�������ӱ�����");
		}
		if (PremiumDisRate < 0) {
			throw new UserException(-98, -1007, this.getClass().getName(),
					"����/���ͷѽ���С��0��������XX���������ӱ����Ƿ���ȷ��");
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
