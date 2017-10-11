package com.sp.indiv.ci.interf;

/*import com.sp.indiv.ci.schema.PaymentDto;*/
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPrpCmain;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PlatFormServiceImpl extends PlatFormServiceAbstract{

	Log logger = LogFactory.getLog(getClass());
	/**
	 * XX预确认
	 * @param businessNo  业务号
	 * @throws Exception
	 */
	/*public void proConfirmOperation(String businessNo)throws Exception{		
		String context = "";
		String response = "";
		String iComCode = "";
		BLPrpCmain blPrpCmain = new BLPrpCmain();
		BLPolicy blPolicy = new BLPolicy();
		DbPool dbPool = new DbPool();
		dbPool.open(SysConfig.getProperty("DDCCDATASOURCE"));
		try {
			blPrpCmain.query(dbPool, "policyNo='" + businessNo + "'");
			if (blPrpCmain.getSize() != 0) {
				blPolicy.getData(dbPool, blPrpCmain.getArr(0).getPolicyNo());
				this.getCIInsureDemand(dbPool, blPolicy);
				iComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();
				ProposalConfirmEncoder proposalConfirmEncoder = new ProposalConfirmEncoder();
				ProposalConfirmDecoder proposalConfirmDecoder = new ProposalConfirmDecoder();
				context = proposalConfirmEncoder.encode(dbPool, blPolicy);
				response = EbaoProxy.getInstance().request(context,iComCode);
				proposalConfirmDecoder.decode(dbPool, blPolicy, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("预确认与平台交互失败,发送的详细数据\n" + context + "\n"
					+ "接收的数据为" + "\n" + response + "\n" + e.getMessage() + "\n"
					+ e.toString() + "\n" + e.getLocalizedMessage());
		}finally{
			dbPool.close();
		}
	}*/
	/**
	 * XX、批单缴费
	 * @param paymentDto
	 * @return 交易号
	 * @throws Exception
	 *
	public String paymentOperation(PaymentDto paymentDto)throws Exception{
		String context = "";
		String response = "";
		String paymentNo = "";
		String comCode = paymentDto.getComCode();
		String businessType = paymentDto.getCertiType();
		DbPool dbPool = new DbPool();
		dbPool.open(SysConfig.getProperty("DDCCDATASOURCE"));
		try {
			if (businessType.equals("P")) {
				PolicyPaymentEncoder policyPaymentEncoder = new PolicyPaymentEncoder();
				PolicyPaymentDecoder policyPaymentDecoder = new PolicyPaymentDecoder();
				context = policyPaymentEncoder.encode(paymentDto);
				response = EbaoProxy.getInstance().request(context,comCode);
				policyPaymentDecoder.decode(dbPool, response);
				paymentNo = policyPaymentDecoder.getPaymentNo();
			}else if(businessType.equals("E"))
			{
				EndorsePaymentEncoder endorsePaymentEncoder = new EndorsePaymentEncoder();
				EndorsePaymentDecoder endorsePaymentDecoder = new EndorsePaymentDecoder();
				context = endorsePaymentEncoder.encode(paymentDto);
				response = EbaoProxy.getInstance().request(context,comCode);
				endorsePaymentDecoder.decode(dbPool, response);
				paymentNo = endorsePaymentDecoder.getPaymentNo();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("缴费与平台交互失败,发送的详细数据\n" + context + "\n"
					+ "接收的数据为" + "\n" + response + "\n" + e.getMessage() + "\n"
					+ e.toString() + "\n" + e.getLocalizedMessage());
		}finally{
			dbPool.close();
		}
		return paymentNo;
	}*/
	/**
	 * 交易更改
	 * @param businessNo 业务号
	 * @throws Exception
	 */
	public void cancelOperation(String businessNo)throws Exception{
		String context = "";
		String response = "";
		String iComCode = "";
		BLEndorse blEndorse = new BLEndorse();
		DbPool dbPool = new DbPool();
		dbPool.open(SysConfig.getProperty("DDCCDATASOURCE"));
		try {
			blEndorse.getData(dbPool, businessNo);
			iComCode = blEndorse.getBLPrpPmain().getArr(0).getComCode();
			PolicyCancelEncoder policyCancelEncoder = new PolicyCancelEncoder();
			PolicyCancelDecoder policyCancelDecoder = new PolicyCancelDecoder();
			context = policyCancelEncoder.encode(dbPool, blEndorse);
			response = EbaoProxy.getInstance().request(context,iComCode);
			policyCancelDecoder.decode(dbPool, blEndorse, response);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("交易更改与平台交互失败,发送的详细数据\n" + context + "\n"
					+ "接收的数据为" + "\n" + response + "\n" + e.getMessage() + "\n"
					+ e.toString() + "\n" + e.getLocalizedMessage());
		}finally{
			dbPool.close();
		}
	}
	/**
	 * 交易回退
	 * @param businessNo 业务号
	 * @throws Exception
	 */
	/*public void backspaceOperation(String businessNo)throws Exception{
		String context = "";
		String response = "";
		String iComCode = "";
		BLPrpCmain blPrpCmain = new BLPrpCmain();
		BLPolicy blPolicy = new BLPolicy();
		DbPool dbPool = new DbPool();
		dbPool.open(SysConfig.getProperty("DDCCDATASOURCE"));
		try {
			blPrpCmain.query(dbPool, "policyNo='" + businessNo + "'");
			if (blPrpCmain.getSize() != 0) {
				blPolicy.getData(dbPool, blPrpCmain.getArr(0).getPolicyNo());
				iComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();
				PolicyBackspaceEncoder policyBackspaceEncoder = new PolicyBackspaceEncoder();
				PolicyBackspaceDecoder policyBackspaceDecoder = new PolicyBackspaceDecoder();
				context = policyBackspaceEncoder.encode(dbPool, blPolicy);
				response = EbaoProxy.getInstance().request(context,iComCode);
				policyBackspaceDecoder.decode(dbPool, blPolicy, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("交易回退与平台交互失败,发送的详细数据\n" + context + "\n"
					+ "接收的数据为" + "\n" + response + "\n" + e.getMessage() + "\n"
					+ e.toString() + "\n" + e.getLocalizedMessage());
		}finally{
			dbPool.close();
		}
	}*/
	/**
	 * 商业XXXXX历史XX上传
	 * @param businessNo 业务号
	 * @throws Exception
	 */
	public void uploadOperation(DbPool dbPool,String businessNo)throws Exception{
		String context = "";
		String response = "";
		String iComCode = "";
		BLPrpCmain blPrpCmain = new BLPrpCmain();
		BLPolicy blPolicy = new BLPolicy();
		/*DbPool dbPool = new DbPool();
		dbPool.open(SysConfig.getProperty("DDCCDATASOURCE"));*/
		try {
			blPrpCmain.query(dbPool, "policyNo='" + businessNo + "'");
			if (blPrpCmain.getSize() != 0) {
				blPolicy.getData(dbPool, blPrpCmain.getArr(0).getPolicyNo());
				iComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();
				PolicyUploadEncoder policyUploadEncoder = new PolicyUploadEncoder();
				PolicyUploadDecoder policyUploadDecoder = new PolicyUploadDecoder();
				context = policyUploadEncoder.encode(dbPool, blPolicy);
				
				logger.info("==商业XXXXXXX上传平台request=="+context);
				
				response = EbaoProxy.getInstance().request(context,iComCode);
				response = StringUtils.replace(response, "GBK", "GB2312");
				
				logger.info("==商业XXXXXXX上传平台response=="+response);
				
				policyUploadDecoder.decode(dbPool, blPolicy, response);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("XX上传与平台交互失败,发送的详细数据\n" + context + "\n"
					+ "接收的数据为" + "\n" + response + "\n" + e.getMessage() + "\n"
					+ e.toString() + "\n" + e.getLocalizedMessage());
		}/*finally{
			dbPool.close();
		}*/
	}
}
