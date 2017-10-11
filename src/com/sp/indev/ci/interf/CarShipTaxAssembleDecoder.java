package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCIAssemblePayNo;
import com.sp.indiv.ci.schema.CIAssemblePayNoSchema;
/*modify by huangfp 2009-02-03-001 begain 引用路径修改 */
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
 * 车船税汇总完税凭证请求数据的解码器
 * 
 */
public class CarShipTaxAssembleDecoder {

	public static void main(String[] args) throws Exception {}

	/**
	 * 解码
	 * 
	 * @return 车船税汇总完税凭证XML格式串
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
	 * 处理HEAD节
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
	 * 处理BODY节
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
	 * 处理BASE_PART节
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
	 * XXXXX存汇总信息
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
	 * 纠正日期格式
	 *
	 * @param dateString
	 *            8个字符的日期
	 * @return YYYYMMDD形式的日期
	 */
    private static String correctDate(String dateString) {
        String result = StringUtils.replace(dateString, "-", "");
        return result;
    }
    
}
