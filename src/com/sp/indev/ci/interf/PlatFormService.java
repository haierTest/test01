package com.sp.indiv.ci.interf;

import com.sp.utility.database.DbPool;

public interface PlatFormService {
	/**
	 * XX预确认
	 * @param businessNo  业务号
	 * @param businessType 业务类型
	 * @throws Exception
	 */
	public void proConfirmOperation(String businessNo)throws Exception;
	/**
	 * 交易更改
	 * @param businessNo 业务号
	 * @throws Exception
	 */
	public void cancelOperation(String businessNo)throws Exception;
	/**
	 * 交易回退
	 * @param businessNo 业务号
	 * @throws Exception
	 */
	public void backspaceOperation(String businessNo)throws Exception;
	/**
	 * 商业XXXXX历史XX上传
	 * @param businessNo 业务号
	 * @throws Exception
	 */
	public void uploadOperation(DbPool dbPool,String businessNo)throws Exception;
	/**
	 * 到帐确认
	 * @throws Exception
	 */
	public void accountConfirmOperation()throws Exception;
	/**
	 * 到帐查询
	 * @throws Exception
	 */
	public void accountQueryOperation()throws Exception;
	/**
	 * 缴费更改
	 * @throws Exception
	 */
	public void paymentAlterOperation()throws Exception;
	/**
	 * 凭证补传
	 * @throws Exception
	 */
	public void warrantUploadOperation()throws Exception;
}
