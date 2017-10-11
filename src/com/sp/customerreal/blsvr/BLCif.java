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
		String regEx = "[\"`~!@#$%^&*()+=|{}':;',\\[\\]<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.find(0);
	}
	
	/**
	 * 获取新XXXXX电销合作数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDXmobile(List list) {
		logger.info("BLCif->getTmmobile()->获取新XXXXX电销合作数据->开始");
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
						logger.error("根据车牌号查电销合作数据:"+prpCustomerMessageSchema.getLicenseNo(),e);
						 String message = "BLCif->getDXdate()->根据车牌号查电销合作数据->"+ e.getMessage();
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
						logger.error("根据发送机号查电销合作数据:"+prpCustomerMessageSchema.getEngineNo(),e);
						 String message = "BLCif->getDXdate()->根据车牌号查电销合作数据->"+ e.getMessage();
						 TaskMngUtil.insertMidDataLog("BLCif.getDXmobile", message,
								e, TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取新XXXXX电销合作数据：", e);
			String message = "BLCif->getTmmobile()->获取新XXXXX电销合作数据->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLCif.getItmVisitData", message,
					e, TaskMngUtil.DXVISIT_JOBNAME);
		} finally {
			try {
				dbpool.close();
			} catch (SQLException e) {
				logger.error("电销合作数据错误信息：", e);
			}
		}
		logger.info("BLCif->getTmmobile()->获取新XXXXX电销合作数据->结束");
		return glList;
	}

	
	/**
	 * @Title: insertDXmobile
	 * @Description: 插入电销合作接口表PRPCUSTOMERMESSAGE_TMMOBLIE
	 * @param
	 * @param list
	 *            设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void insertDXmobile(List list) {
		logger.info("BLCif->insertDXmobile()->电销合作数据写入接口表->开始");
		if (list == null || list.size() < 1) {
			logger.info("BLCif->insertDXmobile()->电销合作无符合条件的数据");
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
						logger.error(e+" 电销合作数据写入95510接口表出错XX号是："+cifSchema.getId());
						String message = "BLCif->insertDXmobile()->电销合作数据写入接口表出错的XX号是："+cifSchema.getId()+e.getMessage();
						TaskMngUtil.insertMidDataLog("BLCif.insertDXmobile", message,e,
								TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
					}
				}
			} catch (Exception e) {
				logger.error("电销回访错误信息：", e);
				String message = "BLCif->insertDXmobile()->电销合作数据写入接口表："
						+ e.getMessage();
				TaskMngUtil.insertMidDataLog("BLCif.insertDXmobile",
						message, e, TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			} finally {
				try {
					if (dbpool != null) {
						dbpool.close();
					}
				} catch (SQLException e) {
					logger.error("电销回访错误信息：", e);
				}
			}
		}
		logger.info("BLCif->insertDXmobile()->电销合作数据写入接口表->结束");
	}
	
	/**
	 * 未完成
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDxVisitData(List list) {
		logger.info("BLCif->getDxVisitData()->电销合作数据写入接口表->开始");
		if (list == null || list.size() < 1) {
			logger.info("BLCif->getDxVisitData()->需查询电销合作无符合条件的数据");
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
				logger.error("电销回访错误信息：", e);
				String message = "BLCif->getDxVisitData()->获取XX电销数据数据->"
						+ e.getMessage();
				TaskMngUtil.insertMidDataLog("BLCif.getItmVisitData",
						message, e, TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			} finally {
				try {
					dbpool.close();
				} catch (SQLException e) {
					logger.error("电销回访错误信息：", e);
				}
			}
		}
		logger.info("BLCif->getDxVisitData()->按时间段获取XX电销数据准备写入95510接口表->结束");
		return cifList;
	}
	

	/**
	 * @Title: insertDXmobile
	 * @Description: 插入关联CIF接口表PRPCUSTOMERMESSAGE_CIFMOBILE
	 * @param
	 * @param list
	 *            设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void insetCifMobile(List list) {
		logger.info("BLCif->insertDXmobile()->插入关联CIF接口表->开始");
		if (list == null || list.size() < 1) {
			logger.info("BLCif->insertDXmobile()->插入关联CIF接口表无数据");
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
						logger.error(e+" 插入关联CIF接口表出错XX号是："+cifSchema.getId());
						String message = "BLCif->insetCifMobile()->插入关联CIF接口表出错的XX号是："+cifSchema.getPolicyno()+e.getMessage();
						TaskMngUtil.insertMidDataLog("BLCif.insetCifMobile", message,e,
								TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
					}
				}
			} catch (Exception e) {
				logger.error("插入关联CIF接口表错误信息：", e);
				String message = "BLCif->insertDXmobile()->插入关联CIF接口表："
						+ e.getMessage();
				TaskMngUtil.insertMidDataLog("BLItmVisit.insertVisitDataData",
						message, e, TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			} finally {
				try {
					if (dbpool != null) {
						dbpool.close();
					}
				} catch (SQLException e) {
					logger.error("插入关联CIF接口表错误信息：", e);
				}
			}
		}
		logger.info("BLCif->insertDXmobile()->插入关联CIF接口表->结束");
	}
}
