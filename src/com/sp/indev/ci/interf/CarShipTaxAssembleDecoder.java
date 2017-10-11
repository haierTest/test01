package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCIAssemblePayNo;
import com.sp.indiv.ci.schema.CIAssemblePayNoSchema;
/*modify by huangfp 2009-02-03-001 begain ����·���޸� */
/*
import com.sp.prpall.blsvr.jf.BLPrpJPayBook;
import com.sp.prpall.dbsvr.jf.DBPrpJPayBook;
import com.sp.prpall.schema.PrpJPayBookSchema;
*/
import com.sp.payment.blsvr.jf.BLPrpJPayBook;
import com.sp.payment.dbsvr.jf.DBPrpJPayBook;
import com.sp.payment.schema.PrpJPayBookSchema;
import com.sp.prpall.blsvr.jf.BLPrpQueryPaymentService;
/*modify by huangfp 2009-02-03-001 end */
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

/**
 * ����˰������˰ƾ֤�������ݵĽ�����
 * 
 */
public class CarShipTaxAssembleDecoder {

	public static void main(String[] args) throws Exception {}

	/**
	 * ����
	 * 
	 * @return ����˰������˰ƾ֤XML��ʽ��
	 * @throws Exception
	 */
	public void decode(BLCIAssemblePayNo blCIAssemblePayNo ,String content) throws Exception {
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);

		processHead(blCIAssemblePayNo, XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
		processBody(blCIAssemblePayNo, XMLUtils.getChildNodeByPath(document,
				"/PACKET/BODY"));
		saveMsg(blCIAssemblePayNo);
	}

	/**
	 * ����HEAD��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processHead(BLCIAssemblePayNo blCIAssemblePayNo, Node node) throws Exception {
		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
		if (!responseCode.equals("1")) {
			String errorMessage = XMLUtils.getChildNodeValue(node,
					"ERROR_MESSAGE");
			throw new Exception(errorMessage);
		}
		blCIAssemblePayNo.getArr(0).setResponseCode(responseCode);
	}

	/**
	 * ����BODY��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBody(BLCIAssemblePayNo blCIAssemblePayNo , Node node)
			throws Exception {
		processBasePart(blCIAssemblePayNo, XMLUtils.getChildNodeByTagName(node,
				"BASE_PART"));
	}

	/**
	 * ����BASE_PART��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBasePart(BLCIAssemblePayNo blCIAssemblePayNo , Node node)
			throws Exception {
		
		String declareNo = XMLUtils.getChildNodeValue(node, "DECLARE_NO");
		
		CIAssemblePayNoSchema schema = blCIAssemblePayNo.getArr(0);
        schema.setDeclareNo(declareNo);
	}
	
	/**
	 * XXXXX�������Ϣ
	 * @param blCIAssemblePayNo
	 * @throws Exception
	 */
	protected void saveMsg(BLCIAssemblePayNo blCIAssemblePayNo) throws Exception{
    	DbPool dbPool = new DbPool();
    	CIAssemblePayNoSchema ciAssemblePayNoSchema = blCIAssemblePayNo.getArr(0);
        
    	DbPool paymentDbPool = new DbPool();
        String strSwitch = BLPrpQueryPaymentService.queryMirrorSwitch();
        try {
            if ("1".equals(strSwitch)) {
                  BLPrpQueryPaymentService.querySwitch(paymentDbPool, 2);
            }else{
                paymentDbPool.open(SysConfig.CONST_DDCCDATASOURCE);
           }
            dbPool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbPool.beginTransaction();
			paymentDbPool.beginTransaction();
		
	        
			String sql = "INSERT INTO CIASSEMBLEPAYNO(CHECKNO,STARTDATE,ENDDATE,DECLARENO,RESPONSECODE,REMARK,EXTENDCHAR1,FLAG,PAYBOOKNUM) VALUES" +
			             "('"  + ciAssemblePayNoSchema.getCheckNo() +
			             "', '" + ciAssemblePayNoSchema.getStartDate() +
			             "', '" + ciAssemblePayNoSchema.getEndDate() +
			             "', '" + ciAssemblePayNoSchema.getDeclareNo() +
			             "', '" + ciAssemblePayNoSchema.getResponseCode() +
			             "', '" + ciAssemblePayNoSchema.getRemark() +
			             "', '" + ciAssemblePayNoSchema.getExtendChar1() +
			             "', '" + ciAssemblePayNoSchema.getFlag() +
			             "', '" + ciAssemblePayNoSchema.getPayBookNum() +
			             "')";
			dbPool.insert(sql);
	        DBPrpJPayBook dbPrpJPayBook = new DBPrpJPayBook();
	        dbPrpJPayBook.delete(ciAssemblePayNoSchema.getDeclareNo());
	        dbPrpJPayBook.setDeclareNo(ciAssemblePayNoSchema.getDeclareNo());
	        dbPrpJPayBook.setCenterCode("01000000");
	        dbPrpJPayBook.setStartDate(ciAssemblePayNoSchema.getStartDate());
	        dbPrpJPayBook.setEndDate(ciAssemblePayNoSchema.getEndDate());
	        dbPrpJPayBook.setBookStatus("0");
	        dbPrpJPayBook.setPrintTime("0");
            
            dbPrpJPayBook.insert(paymentDbPool);
	        dbPool.commitTransaction();
	        paymentDbPool.commitTransaction();
		}catch(Exception e){
			dbPool.rollbackTransaction();
			paymentDbPool.rollbackTransaction();
        	throw e;
		}finally{
			dbPool.close();
			paymentDbPool.close();
		}
	}
	
    
    /**
	 * �������ڸ�ʽ
	 *
	 * @param dateString
	 *            8���ַ�������
	 * @return YYYYMMDD��ʽ������
	 */
    private static String correctDate(String dateString) {
        String result = StringUtils.replace(dateString, "-", "");
        return result;
    }
    
}
