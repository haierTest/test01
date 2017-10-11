package com.sp.prpall.blsvr.jf;


import org.apache.axiom.om.OMElement;
import org.apache.axis2.extensions.spring.receivers.ApplicationContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DXPaymentSHService extends ApplicationContextHolder 
{
	protected final Log logger = LogFactory.getLog(getClass());
	public DXPaymentSHService() {
	};
	
	/**
	 * ���������ո���ѯ������
	 * @param list List<Map> key:CardType ֧�����͡�RcptNo �����š�PayId ��ˮ�š�SumPremium ��ProList XX���б�
	 * @throws �쳣��
	 */
	public OMElement queryPoaCodeByDX(OMElement omElement) throws Exception
	{		
        logger.info("�Ϻ������ո���ѯ��������Σ�" + omElement);
		BLPrpJDXPaymentSH blPrpJDXPaymentSH = new BLPrpJDXPaymentSH();
	    OMElement resultOME = blPrpJDXPaymentSH.queryPoaCodeByDX(omElement);
        logger.info("�Ϻ������ո���ѯ����������" + resultOME);
        return resultOME;		
	}
	
	/**
	 * ���������ո���ѯƽ̨״̬
	 * @param list List<Map> key:V_RCPTNO ������ˮ�š�V_PAYID ƽ̨�����롢V_CMSSTATUS �ۿ���
	 * @throws �쳣��
	 */
	public OMElement queryAccStatusByDX(OMElement omElement) throws Exception {  
		logger.info("�Ϻ������ո���ѯƽ̨״̬��Σ�"+omElement);
		BLPrpJDXPaymentSH blPrpJDXPaymentSH = new BLPrpJDXPaymentSH();
	    OMElement resultOME = blPrpJDXPaymentSH.queryAccStatusByDX(omElement);
	    logger.info("�Ϻ������ո���ѯƽ̨״̬�����"+resultOME);
		return resultOME;
	}	
	
	
	/**
	 * �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨������
	 * @param list 	 XX���б�
	 * @param String �ܽ��
	 * @param String ֧����ʽ���������Ǯ��01-������09-��Ǯ
	 * @throws �쳣��
	 */
	public OMElement queryPoaCodeByEX(OMElement omElement) throws Exception
	{		
        logger.info("�гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨��������Σ�" + omElement);
		BLPrpJDXPaymentSH blPrpJDXPaymentSH = new BLPrpJDXPaymentSH();
	    OMElement resultOME = blPrpJDXPaymentSH.queryPoaCodeByEX(omElement);
        logger.info("�гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯƽ̨����������" + resultOME);
        return resultOME;		
	}
	/**
	 * �гϰ���XXXXXƽ̨ͨ�����ߵ����ո���ѯXX״̬
	 * @param String �ܽ��
	 * @param String ƽ̨������
	 * @throws �쳣��
	 */
	public OMElement queryAccStatusByEX(OMElement omElement) throws Exception {  
		logger.info("�гϰ���XXXXXƽ̨ͨ�����ߵ����ո�XX״̬��Σ�"+omElement);
		BLPrpJDXPaymentSH blPrpJDXPaymentSH = new BLPrpJDXPaymentSH();
	    OMElement resultOME = blPrpJDXPaymentSH.preConfirmEx(omElement);
	    logger.info("�гϰ���XXXXXƽ̨ͨ�����ߵ����ո�XX״̬�����"+resultOME);
		return resultOME;
	}
	
	
}
