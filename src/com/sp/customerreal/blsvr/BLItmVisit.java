package com.sp.customerreal.blsvr;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.dbsvr.DBItmVisit;
import com.sp.customerreal.schema.ItmVisitSchema;
import com.sp.taskmng.util.TaskMngUtil;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

public class BLItmVisit {

	private final Log logger = LogFactory.getLog(getClass());

	/**
	 * ��ȡ��Ҫ�طõ�XX�������ݵ�������
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getItmVisitData() {
		logger.info("BLItmVisit->getItmVisitData()->��ȡXX��������׼��д��95510�ӿڱ�->��ʼ");
		DBItmVisit dbItmVisit = new DBItmVisit();
		DbPool dbpool = new DbPool();
		List list = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbItmVisit.getItmVisitData(dbpool);
		} catch (Exception e) {
			logger.error("�����طô�����Ϣ��", e);
			 String message = "BLItmVisit->getItmVisitData()->��ȡXX������������->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLItmVisit.getItmVisitData", message,
					e, TaskMngUtil.DXVISIT_JOBNAME);
		} finally {
			try {
				dbpool.close();
			} catch (SQLException e) {
				logger.error("�����طô�����Ϣ��", e);
			}
		}
		logger.info("BLItmVisit->getItmVisitData()->��ȡXX��������׼��д��95510�ӿڱ�->����");
		return list;
	}

	/**
	 * ��ʱ��λ�ȡ��Ҫ�طõ�XX�������ݵ�������
	 * 
	 * @return
	 * @throws Exception
	 */
	public List getItmVisitData(String strBDate,String strNDate,String policyNo) {
		logger.info("��ʱ��λ�ȡXX��������׼��д��95510�ӿڱ�->��ʼ");
		DBItmVisit dbItmVisit = new DBItmVisit();
		DbPool dbpool = new DbPool();
		List list = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			list = dbItmVisit.getItmVisitData(dbpool,strBDate,strNDate,policyNo);
		} catch (Exception e) {
			logger.error("�����طô�����Ϣ��", e);
			 String message = "BLItmVisit->getItmVisitData()->��ȡXX������������->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog("BLItmVisit.getItmVisitData", message,
					e, TaskMngUtil.DXVISIT_JOBNAME);
		} finally {
			try {
				dbpool.close();
			} catch (SQLException e) {
				logger.error("�����طô�����Ϣ��", e);
			}
		}
		logger.info("BLItmVisit->getItmVisitData()->��ʱ��λ�ȡXX��������׼��д��95510�ӿڱ�->����");
		return list;
	}
	
	/**
	 * @Title: insertVisitDataData
	 * @Description: ����95510�����طýӿڱ�
	 * @param
	 * @param list
	 *            �趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public void insertVisitDataData(List list) {
		logger.info("BLItmVisit->insertVisitDataData()->XX��������д��95510�ӿڱ�->��ʼ");
		if (list == null || list.size() < 1) {
			logger.info("BLItmVisit->getItmVisitData()->�޷�������������");
		} else {
			DBItmVisit dbItmVisit = new DBItmVisit();
			DbPool dbpool = new DbPool();
			try {
				dbpool.open(SysConfig.CONST_CALLDATASOURCE);
				
				ItmVisitSchema itmVisitSchema = null;
				for (int i = 0; i < list.size(); i++) {
					itmVisitSchema = (ItmVisitSchema) list.get(i);
					
					int count = 0;
					count = dbItmVisit.getCountPolicy(dbpool,
							itmVisitSchema.getPolicyNo());
					if (count == 0) {
						dbItmVisit.insertItmVisitData(dbpool, itmVisitSchema);
					}
				}
			} catch (Exception e) {
				logger.error("�����طô�����Ϣ��", e);
				String message = "BLItmVisit->insertVisitDataData()->XX��������д��95510�ӿڱ�"
						+ e.getMessage();
				TaskMngUtil.insertMidDataLog("BLItmVisit.insertVisitDataData",
						message, e, TaskMngUtil.DXVISIT_JOBNAME);
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
		logger
				.info("BLPolicyClaim->insertSignPolicyData()->XX��������д��95510�ӿڱ�->����");
	}
}
