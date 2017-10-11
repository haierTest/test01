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
 * ����PrpCommissionDetailSub��BL�� ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
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
public class BLPrpCommissionDetailSub {
	private Vector schemas = new Vector();

	/**
	 * ���캯��
	 */
	public BLPrpCommissionDetailSub() {
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
	 * ����һ��PrpCommissionDetailSubSchema��¼
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
	 * �õ�һ��PrpCommissionDetailSubSchema��¼
	 * @param index �±�
	 * @return һ��PrpCommissionDetailSubSchema����
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
	 * ɾ��һ��PrpCommissionDetailSubSchema��¼
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
	 * ��dbpool��save����
	 * @param ��
	 * @return ��
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
	 * ����dbpool��XXXXX�淽��
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
		strSqlStatement = " DELETE FROM PrpCommissionDetailSub WHERE PolicyNo= ?";
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
	 * @desc ��XXXXXͨ�����Զ�����һ����������ϸ
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
	 * @desc ����ͨ�����Զ�����һ����������ϸ
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
	 * ��������ϸ���������ۻ���ͣ����ڼ�������ʱ��ǰXX�������ѽ��
	 * @param iWherePart
	 * @return ��
	 */
	public void sumByItemKindNo(String iWherePart) throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpCommissionDetailSub dbPrpCommissionDetailSub = new DBPrpCommissionDetailSub();
		initArr();
		strSqlStatement = " SELECT SUM(DISFEE) AS DISFEE,CURRENCY,ITEMKINDNO  FROM PrpCommissionDetailSub WHERE " + iWherePart + " GROUP BY CURRENCY,ITEMKINDNO ORDER BY ITEMKINDNO";
		schemas = dbPrpCommissionDetailSub.sumByCurrency(strSqlStatement);
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
