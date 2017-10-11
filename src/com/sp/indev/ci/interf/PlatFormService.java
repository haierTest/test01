package com.sp.indiv.ci.interf;

import com.sp.utility.database.DbPool;

public interface PlatFormService {
	/**
	 * XXԤȷ��
	 * @param businessNo  ҵ���
	 * @param businessType ҵ������
	 * @throws Exception
	 */
	public void proConfirmOperation(String businessNo)throws Exception;
	/**
	 * ���׸���
	 * @param businessNo ҵ���
	 * @throws Exception
	 */
	public void cancelOperation(String businessNo)throws Exception;
	/**
	 * ���׻���
	 * @param businessNo ҵ���
	 * @throws Exception
	 */
	public void backspaceOperation(String businessNo)throws Exception;
	/**
	 * ��ҵXXXXX��ʷXX�ϴ�
	 * @param businessNo ҵ���
	 * @throws Exception
	 */
	public void uploadOperation(DbPool dbPool,String businessNo)throws Exception;
	/**
	 * ����ȷ��
	 * @throws Exception
	 */
	public void accountConfirmOperation()throws Exception;
	/**
	 * ���ʲ�ѯ
	 * @throws Exception
	 */
	public void accountQueryOperation()throws Exception;
	/**
	 * �ɷѸ���
	 * @throws Exception
	 */
	public void paymentAlterOperation()throws Exception;
	/**
	 * ƾ֤����
	 * @throws Exception
	 */
	public void warrantUploadOperation()throws Exception;
}
