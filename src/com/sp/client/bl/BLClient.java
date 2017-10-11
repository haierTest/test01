package com.sp.client.bl;
import com.sp.client.dto.*;
import com.sp.reins.common.bl.action.custom.BLReinsAction;
import com.sp.undwrt.bl.action.custom.BLPrpFeedBackAction;
import com.sp.utility.error.UserException;
import com.sp.utility.SysConfig;
import com.sp.utility.database.*;
import com.sp.prpall.schema.*;
import com.sp.prpall.blsvr.jf.BLPrpJplanFee;
import com.sp.prpall.blsvr.jf.BLPrpJpayRefRec;
import com.sp.prpall.blsvr.misc.BLPrpCommission;
import com.sp.prpall.blsvr.misc.BLPrpMiddleCost;
import com.sp.prpall.blsvr.tb.*;
import com.sp.prpall.blsvr.cb.*;
import com.sp.prpall.interf.Visa;
import com.sp.prpall.jfcd.cb.CFeedBack;
import com.sp.reins.out.bl.action.custom.BLTDangerGetAction;
import com.sp.reins.out.bl.action.custom.BLCDangerGetAction;
import com.sp.sysframework.reference.DBManager;

import java.sql.ResultSet;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * XX类
 * 支持clone方法
 */
public class BLClient implements Cloneable
{
	Log logger = LogFactory.getLog(getClass());
	/**
	 * add by zhulei
	 * 导入XXXXX存方法
	 * 20060717
	 * 
	 */
	public boolean savePolicyAndOther(DbPool dbPool,DBManager dbManager,BLProposal iBLProposal,BLPolicy iBLPolicy,ArrayList vchInfoList,
			PrpJpayRefRecSchema iPrpJpayRefRecSchema) throws UserException, Exception{
		boolean bltest = false;
		String strProposalNo=iBLProposal.getBLPrpTmain().getArr(0).getProposalNo();
		String strPolicyNo=iBLPolicy.getBLPrpCmain().getArr(0).getPolicyNo();
		String strClassCode=iBLPolicy.getBLPrpCmain().getArr(0).getClassCode();
		BLPrpFeedBackAction blPrpFeedBackAction = new BLPrpFeedBackAction();


        try {
			
			iBLProposal.save(dbPool, "I", false);
			
			iBLPolicy.save(dbPool);
			
			BLPolicyOrigin blPolicyOrigin = new BLPolicyOrigin();
			blPolicyOrigin.policyToOriginPolicy(dbPool, iBLPolicy);
			
			BLPrpMiddleCost blPrpMiddleCost = new BLPrpMiddleCost();
			blPrpMiddleCost.createDisPremium(dbPool, "P", strPolicyNo);
			BLPrpCommission blPrpCommission = new BLPrpCommission();
			blPrpCommission.createCommission(dbPool, "P", strPolicyNo);
			
			
			BLTDangerGetAction blTDangerGetAction = new BLTDangerGetAction();
			blTDangerGetAction.getTDangerInfo(strProposalNo,dbManager);
			BLCDangerGetAction blCDangerGetAction = new BLCDangerGetAction();
			blCDangerGetAction.getCDangerInfo(strPolicyNo,dbManager);
			blPrpFeedBackAction.cRepolicyInfo(dbManager, strPolicyNo, "P");
			
			BLReinsAction blReinsAction = new BLReinsAction();
			blReinsAction.simulateRepolicyByDangerNo(strPolicyNo, strClassCode, "P",dbManager);
			
			
			CFeedBack cFeedBack = new CFeedBack();
			boolean jmsFlag = cFeedBack.checkJMSSend(dbPool, "P", strPolicyNo);
			if (jmsFlag) {
	            cFeedBack.updateDataForJMS(dbPool, "P", strPolicyNo);
            }else{
            	BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
            	blPrpJplanFee.transData(dbPool, "P", strPolicyNo);
            }
			
			
			Visa visa = new Visa();
			String strUserCode = iBLProposal.getBLPrpTmain().getArr(0).getOperatorCode();
			String strComCode = null;
			String stQueryCondition = null;
			stQueryCondition = " select comcode "
				+ "from prpduser where usercode = '" + strUserCode+"'";
			
			logger.info(stQueryCondition);
			
			ResultSet rs = dbManager.executeQuery(stQueryCondition);
			
			if (rs.next()) {
				strComCode = rs.getString(1);
			}else{
				throw new UserException(-98, -1007, "BLClient",
						"查找出单员机构失败！");
			}
			String strLocTypeCode = "";
			String strSerialNo = "";
			Iterator it = vchInfoList.iterator();
			VchInfoBean vib = null;
			vib = (VchInfoBean)it.next();
			
			strLocTypeCode = vib.getC_VCH_TYP();
			strSerialNo = vib.getC_VCH_NO();
			
			int ret = 0 ;
           
			visa.useTrans(dbPool,strUserCode,strLocTypeCode,strSerialNo,strPolicyNo);
           

			
			logger.info("单证"+strSerialNo+"已经销号！");
			
			
			vib = (VchInfoBean)it.next();
			strLocTypeCode = vib.getC_VCH_TYP();
			strSerialNo = vib.getC_VCH_NO();

             
		     visa.useTrans(dbPool,strUserCode,strLocTypeCode,strSerialNo,strPolicyNo);
             

			
			logger.info("单证"+strSerialNo+"已经销号！");
			
			
			String[] arrCertiType = new String[1];
		    String[] arrCertiNo = new String[1];
		    String[] arrSerialNo = new String[1];
		    String[] arrPayRefReason = new String[1];
		    arrCertiType[0] = "P";
		    arrCertiNo[0] = strPolicyNo;
		    vib = (VchInfoBean)it.next();
		    arrSerialNo[0] = "1";  
		    arrPayRefReason[0] = "R10";
		    BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
		    ArrayList iSchemas = new ArrayList();
		    iSchemas.add(iPrpJpayRefRecSchema);
		    try{
		    	blPrpJpayRefRec.genInvoice(dbPool, arrCertiType, arrCertiNo, arrSerialNo, arrPayRefReason, iSchemas);
	        }catch(NullPointerException e){


			}
	        
	        logger.info("单证"+iPrpJpayRefRecSchema.getVisaSerialNo()+"已经销号！");
	        
			
		    vib = (VchInfoBean)it.next();
			strLocTypeCode = vib.getC_VCH_TYP();
			strSerialNo = vib.getC_VCH_NO();
			try{
               
				visa.useTrans(dbPool,strUserCode,strLocTypeCode,strSerialNo,strPolicyNo);
               
	        }catch(NullPointerException e){


			}
		} catch (UserException ee) {
			ee.printStackTrace();
			throw ee;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
		return bltest;
	}
}
