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
 * ����PrpCommissionSub��BL�� ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-03-01</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 */
public class BLPrpCommissionSub {
	private Vector schemas = new Vector();

	/**
	 * ���캯��
	 */
	public BLPrpCommissionSub() {
	}

	/**
	 * ��ʼ����¼
	 * 
	 * @param ��
	 * @return ��
	 * @throws Exception
	 */
	public void initArr() throws Exception {
		schemas = new Vector();
	}

	/**
	 * ����һ��PrpCommissionSubSchema��¼
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
	 * �õ�һ��PrpCommissionSubSchema��¼
	 * @param index �±�
	 * @return һ��PrpCommissionSubSchema����
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
	 * ɾ��һ��PrpCommissionSubSchema��¼
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
	 * 
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
	 * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
	 * @param dbpool ȫ�ֳ�
	 * @param iWherePart ��ѯ����(���������־�)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart) throws UserException, Exception {
		this.query(dbpool, iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * @param dbpool ȫ�ֳ�
	 * @param iWherePart ��ѯ����(���������־�)
	 * @param iLimitCount ��¼������(iLimitCount=0: ������)
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
	 * ��dbpool��save����
	 * @param ��
	 * @return ��
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
	 * ����dbpool��XXXXX�淽��
	 * 
	 * @param ��
	 * @return ��
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
	 * ��dbpool��ɾ������
	 * @param dbpool ���ӳ�
	 * @param iPolicyNo XX��
	 * @return ��
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
	 * ����dbpool��ɾ������
	 * @param iPolicyNo XX��
	 * @return ��
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
	 * ��dbpool����XX�Ż�ȡ����
	 * @param iPolicyNo XX��
	 * @return ��
	 */
	public void getData(String iPolicyNo) throws Exception {
		DbPool dbpool = new DbPool();
		dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
		getData(dbpool, iPolicyNo);
		dbpool.close();
	}

	/**
	 * ����dbpool����XX�Ż�ȡ����
	 * @param dbpool ���ӳ�
	 * @param iPolicyNo XX��
	 * @return ��
	 */
	public void getData(DbPool dbpool, String iPolicyNo) throws Exception {
		String strWherePart = " PolicyNo= ? ";
		ArrayList arrWhereValue = new ArrayList();
		arrWhereValue.add(iPolicyNo);
		query(dbpool, strWherePart, arrWhereValue, 0);
	}

	/**
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * @param iWherePart ��ѯ����,�����������Ѱ󶨱�����ʽ���ʺŸ���������ֵ����һ��
	 * @param iWhereValue ��ѯ�������ֶ�ֵ
	 * @param iLimitCount ��¼������(iLimitCount=0: ������)
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
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * @param dbpool ȫ�ֳ�
	 * @param iWherePart ��ѯ����,�����������Ѱ󶨱�����ʽ���ʺŸ���������ֵ����һ��
	 * @param iWhereValue ��ѯ�������ֶ�ֵ
	 * @param iLimitCount ��¼������(iLimitCount=0: ������)
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
	 * @desc ��������/���ͷ���Ϣ
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
	 * @desc ��������/���ͷ���Ϣ
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
	 * @desc ����XX����/���ͷ���Ϣ
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
	 * @desc ����XX����/���ͷ���Ϣ
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
	 * ������
	 * 
	 * @param args
	 *            �����б�
	 */
	public static void main(String[] args) {
		
	}
}
