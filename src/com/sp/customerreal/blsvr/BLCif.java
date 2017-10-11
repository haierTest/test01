package com.sp.customerreal.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.schema.CifSchema;
import com.sp.customerreal.schema.PrpCustomerMessageSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.taskmng.util.TaskMngUtil;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

public class BLCif {

	private final Log logger = LogFactory.getLog(getClass());

	
	public  boolean isStandard(String str) {
		String regEx = "[\"`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~��@#��%����&*��������+|{}������������������������]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find(0);
	}
	
	/**
	 * ��ȡ��XXXXX������������
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDXmobile(List list) {
		logger.info("BLCif->getTmmobile()->��ȡ��XXXXX������������->��ʼ");
		if(list==null || list.size()<1)
			return null;
		List glList = new ArrayList();
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DXHZDATASOURCE);
			for (int i = 0; i < list.size(); i++) {
				prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
				
				if(prpCustomerMessageSchema.getLicenseNo() != null && prpCustomerMessageSchema.getLicenseNo().length() == 7 && 
						!isStandard(prpCustomerMessageSchema.getLicenseNo())){
					try {
						String sql = "SELECT * FROM (SELECT t.chepaihao,t.fadongjihao,t.mobile,t.importtime,t.subfadongjihao FROM T_DX_CAR_P t WHERE t.chepaihao = upper(?) ORDER BY t.importtime DESC) WHERE ROWNUM = '1' ";
						dbpool.prepareInnerStatement(sql);
						dbpool.setString(1, prpCustomerMessageSchema.getLicenseNo());
						ResultSet resultSet = dbpool.executePreparedQuery();
						while (resultSet.next()) {
							CifSchema cifSchema = new CifSchema();
							cifSchema.setMobile(dbpool.getString(resultSet,"mobile"));
							cifSchema.setId(prpCustomerMessageSchema.getPolicyNo());
							cifSchema.setChannel("03");
							cifSchema.setCustomercode(prpCustomerMessageSchema.getCustomerCode());
							glList.add(cifSchema);
						}
						resultSet.close();
						dbpool.closePreparedStatement();
					} catch (Exception e) {
						logger.error("���ݳ��ƺŲ������������:"+prpCustomerMessageSchema.getLicenseNo(),e);
						 String message = "BLCif->getDXdate()->���ݳ��ƺŲ������������->"+ e.getMessage();
						 TaskMngUtil.insertMidDataLog("BLCif.getDXmobile", message,
								e, TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
					}
				}
				
				else if(prpCustomerMessageSchema.getEngineNo() != null && prpCustomerMessageSchema.getEngineNo().length() != 0 
						  && !isStandard(prpCustomerMessageSchema.getEngineNo())){
					try {
						String sql = "SELECT * FROM (" +
								     "SELECT T.CHEPAIHAO, T.FADONGJIHAO, T.MOBILE, T.IMPORTTIME, T.SUBFADONGJIHAO FROM T_DX_CAR_P T " +
								     "WHERE T.SUBFADONGJIHAO  = " +
								     "UPPER(SUBSTR(?,LENGTH(?) - 5,LENGTH(?))) " +
								     "ORDER BY T.IMPORTTIME DESC) " +
								     "WHERE ROWNUM = '1' ";
						dbpool.prepareInnerStatement(sql);
						dbpool.setString(1, prpCustomerMessageSchema.getEngineNo());
						dbpool.setString(2, prpCustomerMessageSchema.getEngineNo());
						dbpool.setString(3, prpCustomerMessageSchema.getEngineNo());
						ResultSet resultSet = dbpool.executePreparedQuery();
						while (resultSet.next()) {
							CifSchema cifSchema = new CifSchema();
							cifSchema.setMobile(dbpool.getString(resultSet,"mobile"));
							cifSchema.setId(prpCustomerMessageSchema.getPolicyNo());
							cifSchema.setChannel("03");
							cifSchema.setCustomercode(prpCustomerMessageSchema.getCustomerCode());
							glList.add(cifSchema);
						}
						resultSet.close();
						dbpool.closePreparedStatement();
					} catch (Exception e) {
						logger.error("���ݷ��ͻ��Ų������������:"+prpCustomerMessageSchema.getEngineNo(),e);
						 String message = "BLCif->getDXdate()->���ݳ��ƺŲ������������->"+ e.getMessage();
						 TaskMngUtil.insertMidDataLog("BLCif.getDXmobile", message,
								e, TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
					}
				}
			}
		} catch (Exception e) {
			logger.error("��ȡ��XXXXX�����������ݣ�", e);
			String message = "BLCif->getTmmobile()->��ȡ��XXXXX������������->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLCif.getItmVisitData", message,
					e, TaskMngUtil.DXVISIT_JOBNAME);
		} finally {
			try {
				dbpool.close();
			} catch (SQLException e) {
				logger.error("�����������ݴ�����Ϣ��", e);
			}
		}
		logger.info("BLCif->getTmmobile()->��ȡ��XXXXX������������->����");
		return glList;
	}

	
	/**
	 * @Title: insertDXmobile
	 * @Description: ������������ӿڱ�PRPCUSTOMERMESSAGE_TMMOBLIE
	 * @param
	 * @param list
	 *            �趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void insertDXmobile(List list) {
		logger.info("BLCif->insertDXmobile()->������������д��ӿڱ�->��ʼ");
		if (list == null || list.size() < 1) {
			logger.info("BLCif->insertDXmobile()->���������޷�������������");
		} else {
			DbPool dbpool = new DbPool();
			try {
				dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
				CifSchema cifSchema = null;
				for (int i = 0; i < list.size(); i++) {
					cifSchema = (CifSchema) list.get(i);
					
					String sql =  "insert into PRPCUSTOMERMESSAGE_tmmoblie(" +
					"mobile," +
					"customercode," +
					"channel," +
					"ID," +
					"createtime " +
					") values(?,?,?,?,sysdate) ";
					try{
						dbpool.prepareInnerStatement(sql);
						int j = 0;
						dbpool.setString(++j,cifSchema.getMobile());
						dbpool.setString(++j,cifSchema.getCustomercode());
						dbpool.setString(++j,cifSchema.getChannel());
						dbpool.setString(++j,cifSchema.getId());
						dbpool.executePreparedUpdate();
						dbpool.closePreparedStatement();
					}catch(Exception e)
					{
						logger.error(e+" ������������д��95510�ӿڱ����XX���ǣ�"+cifSchema.getId());
						String message = "BLCif->insertDXmobile()->������������д��ӿڱ�����XX���ǣ�"+cifSchema.getId()+e.getMessage();
						TaskMngUtil.insertMidDataLog("BLCif.insertDXmobile", message,e,
								TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
					}
				}
			} catch (Exception e) {
				logger.error("�����طô�����Ϣ��", e);
				String message = "BLCif->insertDXmobile()->������������д��ӿڱ�"
						+ e.getMessage();
				TaskMngUtil.insertMidDataLog("BLCif.insertDXmobile",
						message, e, TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			} finally {
				try {
					if (dbpool != null) {
						dbpool.close();
					}
				} catch (SQLException e) {
					logger.error("�����طô�����Ϣ��", e);
				}
			}
		}
		logger.info("BLCif->insertDXmobile()->������������д��ӿڱ�->����");
	}
	
	/**
	 * δ���
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDxVisitData(List list) {
		logger.info("BLCif->getDxVisitData()->������������д��ӿڱ�->��ʼ");
		if (list == null || list.size() < 1) {
			logger.info("BLCif->getDxVisitData()->���ѯ���������޷�������������");
		}
		List cifList = new ArrayList();
		String sql = 	"SELECT *                                                            "+
						"  FROM (SELECT T.PARTYNO, T.ORIGINNO, P.PHONENO, P.CREDIT, P.CHNLID ,p.modifydate "+
						"          FROM CIFSTAGE.T_S_PARTYMAPPING T, CIFSTAGE.T_S_CONTACT_CREDIT P             "+
						"         WHERE T.DATASOURCE = '01'                                  "+
						"           AND T.ORIGINNO = ?                                       "+
						"           AND T.PARTYNO = P.PARTYNO(+)                             "+
						"         ORDER BY P.CREDIT DESC,P.Modifydate DESC)                               "+
						" WHERE ROWNUM <= 3                                                  ";

		DbPool dbpool = new DbPool();
		
			for (int i = 0; i < list.size(); i++) {
			try {
				int j = 1;
				PrpCustomerMessageSchema prpCustomerMessageSchema = (PrpCustomerMessageSchema) list.get(i);
				dbpool.open(SysConfig.CONST_CIFDATASOURCE);
				dbpool.prepareInnerStatement(sql);
				dbpool.setString(1, prpCustomerMessageSchema.getCustomerCode());
				ResultSet resultSet = dbpool.executePreparedQuery();
				CifSchema cifSchema = new CifSchema();
				cifSchema.setPolicyno(prpCustomerMessageSchema.getPolicyNo());
				cifSchema.setId(prpCustomerMessageSchema.getId());
				cifSchema.setCan_custid(prpCustomerMessageSchema.getCustomerCode());
				cifSchema.setMobile4(prpCustomerMessageSchema.getMobile());
				
				
				while (resultSet.next()) {
					if (j == 1) {
						cifSchema.setCif_custid(dbpool.getString(resultSet,"PARTYNO"));
						cifSchema.setMobile1(dbpool.getString(resultSet,"PHONENO"));
						cifSchema.setKxd1(dbpool.getString(resultSet, "CREDIT"));
						cifSchema.setResource1(dbpool.getString(resultSet,"CHNLID"));
						cifSchema.setModifydate1(""+dbpool.getDateTime(resultSet,"modifydate"));
					} else if (j == 2) {
						cifSchema.setCif_custid(dbpool.getString(resultSet,"PARTYNO"));
						cifSchema.setMobile2(dbpool.getString(resultSet,"PHONENO"));
						cifSchema.setKxd2(dbpool.getString(resultSet, "CREDIT"));
						cifSchema.setResource2(dbpool.getString(resultSet,"CHNLID"));
						cifSchema.setModifydate2(""+dbpool.getDateTime(resultSet,"modifydate"));
					} else if (j == 3) {
						cifSchema.setCif_custid(dbpool.getString(resultSet,"PARTYNO"));
						cifSchema.setMobile3(dbpool.getString(resultSet,"PHONENO"));
						cifSchema.setKxd3(dbpool.getString(resultSet, "CREDIT"));
						cifSchema.setResource3(dbpool.getString(resultSet,"CHNLID"));
						cifSchema.setModifydate3(""+dbpool.getDateTime(resultSet,"modifydate"));
					}
					j++;
				}
			   cifList.add(cifSchema);
			} catch (Exception e) {
				logger.error("�����طô�����Ϣ��", e);
				String message = "BLCif->getDxVisitData()->��ȡXX������������->"
						+ e.getMessage();
				TaskMngUtil.insertMidDataLog("BLCif.getItmVisitData",
						message, e, TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			} finally {
				try {
					dbpool.close();
				} catch (SQLException e) {
					logger.error("�����طô�����Ϣ��", e);
				}
			}
		}
		logger.info("BLCif->getDxVisitData()->��ʱ��λ�ȡXX��������׼��д��95510�ӿڱ�->����");
		return cifList;
	}
	

	/**
	 * @Title: insertDXmobile
	 * @Description: �������CIF�ӿڱ�PRPCUSTOMERMESSAGE_CIFMOBILE
	 * @param
	 * @param list
	 *            �趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void insetCifMobile(List list) {
		logger.info("BLCif->insertDXmobile()->�������CIF�ӿڱ�->��ʼ");
		if (list == null || list.size() < 1) {
			logger.info("BLCif->insertDXmobile()->�������CIF�ӿڱ�������");
		} else {
			DbPool dbpool = new DbPool();
			try {
				dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
				CifSchema cifSchema = null;
				for (int i = 0; i < list.size(); i++) {
					cifSchema = (CifSchema) list.get(i);
					String sql =  "insert into PRPCUSTOMERMESSAGE_CIFMOBILE(" +
					"ID," +	
					"POLICYNO," +	
					"CIF_CUSTID," +	
					"CAN_CUSTID," +	
					"MOBILE1," +	
					"MOBILE2," +	
					"MOBILE3," +	
					"MOBILE4," +	
					"KXD1," +	
					"KXD2," +	
					"KXD3," +	
					"KXD4," +	
					"RESOURCE1," +	
					"RESOURCE2," +	
					"RESOURCE3," +	
					"RESOURCE4," +
					"MODIFYDATE1," +	
					"MODIFYDATE2," +	
					"MODIFYDATE3," +	
					"MODIFYDATE4," +
					"CREATETIME" +	
					") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate) ";
					try{
						dbpool.prepareInnerStatement(sql);
						int j = 0;
						dbpool.setString(++j,cifSchema.getId());
						dbpool.setString(++j,cifSchema.getPolicyno());
						dbpool.setString(++j,cifSchema.getCif_custid());
						dbpool.setString(++j,cifSchema.getCan_custid());
						dbpool.setString(++j,cifSchema.getMobile1());
						dbpool.setString(++j,cifSchema.getMobile2());
						dbpool.setString(++j,cifSchema.getMobile3());
						dbpool.setString(++j,cifSchema.getMobile4());
						dbpool.setString(++j,cifSchema.getKxd1());
						dbpool.setString(++j,cifSchema.getKxd2());
						dbpool.setString(++j,cifSchema.getKxd3());
						dbpool.setString(++j,cifSchema.getKxd4());
						dbpool.setString(++j,cifSchema.getResource1());
						dbpool.setString(++j,cifSchema.getResource2());
						dbpool.setString(++j,cifSchema.getResource3());
						dbpool.setString(++j,cifSchema.getResource4());
						dbpool.setDateTime(++j, new DateTime(cifSchema.getModifydate1()));
						dbpool.setDateTime(++j, new DateTime(cifSchema.getModifydate2()));
						dbpool.setDateTime(++j, new DateTime(cifSchema.getModifydate3()));
						dbpool.setDateTime(++j, new DateTime(cifSchema.getModifydate4()));
						dbpool.executePreparedUpdate();
						dbpool.closePreparedStatement();
					}catch(Exception e)
					{
						logger.error(e+" �������CIF�ӿڱ����XX���ǣ�"+cifSchema.getId());
						String message = "BLCif->insetCifMobile()->�������CIF�ӿڱ�����XX���ǣ�"+cifSchema.getPolicyno()+e.getMessage();
						TaskMngUtil.insertMidDataLog("BLCif.insetCifMobile", message,e,
								TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
					}
				}
			} catch (Exception e) {
				logger.error("�������CIF�ӿڱ������Ϣ��", e);
				String message = "BLCif->insertDXmobile()->�������CIF�ӿڱ�"
						+ e.getMessage();
				TaskMngUtil.insertMidDataLog("BLItmVisit.insertVisitDataData",
						message, e, TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			} finally {
				try {
					if (dbpool != null) {
						dbpool.close();
					}
				} catch (SQLException e) {
					logger.error("�������CIF�ӿڱ������Ϣ��", e);
				}
			}
		}
		logger.info("BLCif->insertDXmobile()->�������CIF�ӿڱ�->����");
	}
}
