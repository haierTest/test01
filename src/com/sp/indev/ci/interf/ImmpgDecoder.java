package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCIEndorValid;
import com.sp.indiv.ci.schema.CIEndorValidSchema;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImmpgDecoder {

	Log logger = LogFactory.getLog(getClass());
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

	/**
	 * ����
	 * 
	 * @return XX��ѯ����XML��ʽ��
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, BLEndorse blEndorse, String content)
			throws SQLException, UserException, Exception {
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);

		
		int type = -1;
		try {
			type = processHead(blEndorse, XMLUtils.getChildNodeByPath(document,
					"/PACKET/HEAD"));
			if (type == 1) {
				processBody(blEndorse, XMLUtils.getChildNodeByPath(document,
						"/PACKET/BODY"));
			}
			
			logger.info("****************type=" + type);
			
			if (type == 0) {
				throw new Exception("ֱ������ʧ��");
			}

		} catch (Exception ex1) {
			throw new Exception("����ƽ̨���ش����������ţ�"
					+ blEndorse.getBLPrpPmain().getArr(0).getEndorseNo()
					+ "���������Ա��ϵ��" + ex1.getMessage());
		}
		
		try {
			saveCIEndorValid(dbPool, blEndorse);
		} catch (Exception ex) {
			throw new Exception("��ƽ̨�����ɹ�����" + ex.getMessage() + " �����ţ�"
					+ blEndorse.getBLPrpPmain().getArr(0).getEndorseNo());
		}

	}

	/**
	 * ����HEAD��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected int processHead(BLEndorse blEndorse, Node node) throws Exception {
		
		int type = 1;
		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE"); 
		if (!responseCode.equals("1")) {
			String errorMessage = XMLUtils.getChildNodeValue(node,
					"ERROR_MESSAGE"); 
			blEndorse.getBLCIEndorValid().getArr(0).setErrorMessage(
					errorMessage);
			type = 0;
		}
		return type;
	}

	/**
	 * ����BODY��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBody(BLEndorse blEndorse, Node node) throws Exception {
		processBasePart(blEndorse, XMLUtils.getChildNodeByTagName(node,
				"BASE_PART"));
	}

	/**
	 * ����BASE_PART��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBasePart(BLEndorse blEndorse, Node node)
			throws Exception {

		
		String amendConfirmNo = XMLUtils.getChildNodeValue(node,
				"AMEND_CONFIRM_NO");

		DateTime validTime = new DateTime(DateTime.current(),
				DateTime.YEAR_TO_SECOND);

		
		logger.info("��������="
				+ blEndorse.getBLPrpPmain().getArr(0).getEndorseNo());
		logger.info("XX="
				+ blEndorse.getBLPrpPmain().getArr(0).getPolicyNo());
		logger.info("�仯XX="
				+ blEndorse.getBLPrpPmain().getArr(0).getChgPremium());
		logger.info("ȷ��ʱ��=" + validTime.toString());
		
		
		CIEndorValidSchema schema = new CIEndorValidSchema();
		schema.setValidNo(amendConfirmNo); 
		schema.setDemandNo(amendConfirmNo); 
		schema.setEndorseNo(blEndorse.getBLPrpPmain().getArr(0).getEndorseNo()); 
		schema.setPolicyNo(blEndorse.getBLPrpPmain().getArr(0).getPolicyNo()); 
		schema.setChgPremium(blEndorse.getBLPrpPmain().getArr(0)
				.getChgPremium()); 
		schema.setValidTime("" + validTime.toString()); 
		schema.setValidStatus("0");
		
		blEndorse.getBLCIEndorValid().setArr(schema);
	}

	/**
	 * XXXXX��XXȷ������Ϣ
	 * 
	 * @param dbPool
	 * @param blpolicy
	 * @throws Exception
	 */
	private void saveCIEndorValid(DbPool dbPool, BLEndorse blEndorse)
			throws Exception {
		BLCIEndorValid blCIEndorValid = blEndorse.getBLCIEndorValid();
		blCIEndorValid.save(dbPool);
	}
}
