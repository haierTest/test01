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
	 * 电销调用收付查询交易码
	 * @param list List<Map> key:CardType 支付类型、RcptNo 订单号、PayId 流水号、SumPremium 金额、ProList XX单列表
	 * @throws 异常类
	 */
	public OMElement queryPoaCodeByDX(OMElement omElement) throws Exception
	{		
        logger.info("上海调用收付查询交易码入参：" + omElement);
		BLPrpJDXPaymentSH blPrpJDXPaymentSH = new BLPrpJDXPaymentSH();
	    OMElement resultOME = blPrpJDXPaymentSH.queryPoaCodeByDX(omElement);
        logger.info("上海调用收付查询交易码结果：" + resultOME);
        return resultOME;		
	}
	
	/**
	 * 电销调用收付查询平台状态
	 * @param list List<Map> key:V_RCPTNO 交易流水号、V_PAYID 平台交易码、V_CMSSTATUS 扣款结果
	 * @throws 异常类
	 */
	public OMElement queryAccStatusByDX(OMElement omElement) throws Exception {  
		logger.info("上海调用收付查询平台状态入参："+omElement);
		BLPrpJDXPaymentSH blPrpJDXPaymentSH = new BLPrpJDXPaymentSH();
	    OMElement resultOME = blPrpJDXPaymentSH.queryAccStatusByDX(omElement);
	    logger.info("上海调用收付查询平台状态结果："+resultOME);
		return resultOME;
	}	
	
	
	/**
	 * 中诚安信XXXXX平台通过总线调用收付查询平台交易码
	 * @param list 	 XX单列表
	 * @param String 总金额
	 * @param String 支付方式：银联或快钱，01-银联，09-快钱
	 * @throws 异常类
	 */
	public OMElement queryPoaCodeByEX(OMElement omElement) throws Exception
	{		
        logger.info("中诚安信XXXXX平台通过总线调用收付查询平台交易码入参：" + omElement);
		BLPrpJDXPaymentSH blPrpJDXPaymentSH = new BLPrpJDXPaymentSH();
	    OMElement resultOME = blPrpJDXPaymentSH.queryPoaCodeByEX(omElement);
        logger.info("中诚安信XXXXX平台通过总线调用收付查询平台交易码结果：" + resultOME);
        return resultOME;		
	}
	/**
	 * 中诚安信XXXXX平台通过总线调用收付查询XX状态
	 * @param String 总金额
	 * @param String 平台交易码
	 * @throws 异常类
	 */
	public OMElement queryAccStatusByEX(OMElement omElement) throws Exception {  
		logger.info("中诚安信XXXXX平台通过总线调用收付XX状态入参："+omElement);
		BLPrpJDXPaymentSH blPrpJDXPaymentSH = new BLPrpJDXPaymentSH();
	    OMElement resultOME = blPrpJDXPaymentSH.preConfirmEx(omElement);
	    logger.info("中诚安信XXXXX平台通过总线调用收付XX状态结果："+resultOME);
		return resultOME;
	}
	
	
}
