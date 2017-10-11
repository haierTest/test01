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
	 * XXԤȷ��
	 * @param businessNo  ҵ���
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
			throw new Exception("Ԥȷ����ƽ̨����ʧ��,���͵���ϸ����\n" + context + "\n"
					+ "���յ�����Ϊ" + "\n" + response + "\n" + e.getMessage() + "\n"
					+ e.toString() + "\n" + e.getLocalizedMessage());
		}finally{
			dbPool.close();
		}
	}*/
	/**
	 * XX�������ɷ�
	 * @param paymentDto
	 * @return ���׺�
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
			throw new Exception("�ɷ���ƽ̨����ʧ��,���͵���ϸ����\n" + context + "\n"
					+ "���յ�����Ϊ" + "\n" + response + "\n" + e.getMessage() + "\n"
					+ e.toString() + "\n" + e.getLocalizedMessage());
		}finally{
			dbPool.close();
		}
		return paymentNo;
	}*/
	/**
	 * ���׸���
	 * @param businessNo ҵ���
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
			throw new Exception("���׸�����ƽ̨����ʧ��,���͵���ϸ����\n" + context + "\n"
					+ "���յ�����Ϊ" + "\n" + response + "\n" + e.getMessage() + "\n"
					+ e.toString() + "\n" + e.getLocalizedMessage());
		}finally{
			dbPool.close();
		}
	}
	/**
	 * ���׻���
	 * @param businessNo ҵ���
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
			throw new Exception("���׻�����ƽ̨����ʧ��,���͵���ϸ����\n" + context + "\n"
					+ "���յ�����Ϊ" + "\n" + response + "\n" + e.getMessage() + "\n"
					+ e.toString() + "\n" + e.getLocalizedMessage());
		}finally{
			dbPool.close();
		}
	}*/
	/**
	 * ��ҵXXXXX��ʷXX�ϴ�
	 * @param businessNo ҵ���
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
				
				logger.info("==��ҵXXXXXXX�ϴ�ƽ̨request=="+context);
				
				response = EbaoProxy.getInstance().request(context,iComCode);
				response = StringUtils.replace(response, "GBK", "GB2312");
				
				logger.info("==��ҵXXXXXXX�ϴ�ƽ̨response=="+response);
				
				policyUploadDecoder.decode(dbPool, blPolicy, response);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("XX�ϴ���ƽ̨����ʧ��,���͵���ϸ����\n" + context + "\n"
					+ "���յ�����Ϊ" + "\n" + response + "\n" + e.getMessage() + "\n"
					+ e.toString() + "\n" + e.getLocalizedMessage());
		}/*finally{
			dbPool.close();
		}*/
	}
}
