package com.sp.indiv.ci.interf;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.blsvr.BLCIInsureDemand;
import com.sp.indiv.ci.dbsvr.DBCIInsureDemand;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
/*import com.sp.indiv.ci.schema.PaymentDto;*/
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.utility.database.DbPool;
import com.sp.utility.SysConfig;

public abstract class PlatFormServiceAbstract implements PlatFormService {

	/**
	 * XX预确认
	 * @param businessNo  业务号
	 * @param businessType 业务类型
	 * @throws Exception
	 */
	public void proConfirmOperation(String businessNo)throws Exception
	{
		
	}
	/**
	 * XX、批单缴费
	 * @param paymentDto
	 * @return 交易号
	 * @throws Exception
	 */
	/*public String paymentOperation(PaymentDto paymentDto)throws Exception
	{
		return "";
	}*/
	/**
	 * 交易更改
	 * @param businessNo 业务号
	 * @throws Exception
	 */
	public void cancelOperation(String businessNo)throws Exception
	{
		
	}
	/**
	 * 交易回退
	 * @param businessNo 业务号
	 * @throws Exception
	 */
	public void backspaceOperation(String businessNo)throws Exception
	{
		
	}
	/**
	 * 商业XXXXX历史XX上传
	 * @param businessNo 业务号
	 * @throws Exception
	 */
	public void uploadOperation(DbPool dbPool,String businessNo)throws Exception
	{
		
	}
	/**
	 * 到帐确认
	 * @throws Exception
	 */
	public void accountConfirmOperation()throws Exception
	{
		
	}
	/**
	 * 到帐查询
	 * @throws Exception
	 */
	public void accountQueryOperation()throws Exception
	{
		
	}
	/**
	 * 缴费更改
	 * @throws Exception
	 */
	public void paymentAlterOperation()throws Exception
	{
		
	}
	/**
	 * 凭证补传
	 * @throws Exception
	 */
	public void warrantUploadOperation()throws Exception
	{
		
	}
	protected void getCIInsureDemand(DbPool dbPool,BLPolicy blPolicy) throws Exception {
		try {
			
			
					
			
			
			BLCIInsureDemand blciInsureDemand = new BLCIInsureDemand();
			
			String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
			if(!"1".equals(strSwitch)){
			    blciInsureDemand.getData(dbPool, blPolicy.getBLPrpCmain().getArr(0).getProposalNo());
			}else{
				if (blPolicy.getBLPrpCitemCar().getSize() > 0) {
				  
					ArrayList iWhereValue=new ArrayList();
					iWhereValue.add(blPolicy.getBLPrpCitemCar().getArr(0).getDemandNo());
					blciInsureDemand.query(" DemandNo = ?",iWhereValue);
					
				}
			}
			
			CIInsureDemandSchema cIInsureDemandSchema = null;
			if(blciInsureDemand.getSize()>0){
			    cIInsureDemandSchema = blciInsureDemand.getArr(0);
			    BLCIInsureDemand blCIInsureDemand = new BLCIInsureDemand();
			    blCIInsureDemand.setArr(cIInsureDemandSchema);
			    blPolicy.setBLCIInsureDemand(blCIInsureDemand);
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		} 
	}
}
