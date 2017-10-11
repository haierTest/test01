package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCICarShipTaxPayMsg;
import com.sp.indiv.ci.schema.CICarShipTaxPayMsgSchema;
import com.sp.prpall.blsvr.cb.BLPrpCitemCar;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.database.DbPool;
import com.sp.utility.SysConfig;

/**
 * 获取本地车辆完税标识请求数据的解码器
 * 
 */
public class CarShipTaxObtainPayNoDecoder {

	public static void main(String[] args) throws Exception {}

	/**
	 * 解码
	 * 
	 * @return 获取本地车辆完税标识XML格式串
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, BLCICarShipTaxPayMsg blCICarShipTaxPayMsg, String content) throws Exception {
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
		processBody(blCICarShipTaxPayMsg, XMLUtils.getChildNodeByPath(document,"/PACKET/BODY"));
		saveMsg(dbPool, blCICarShipTaxPayMsg);
	}

	/**
	 * 处理HEAD节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processHead(Node node) throws Exception {
		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
		if (!responseCode.equals("1")) {
			String errorMessage = XMLUtils.getChildNodeValue(node,
					"ERROR_MESSAGE");
			throw new Exception(errorMessage);
		}
	}

	/**
	 * 处理BODY节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBody(BLCICarShipTaxPayMsg blCICarShipTaxPayMsg, Node node)
			throws Exception {
		processBasePart(blCICarShipTaxPayMsg, XMLUtils.getChildNodeByTagName(node,"BASE_PART"));
	}

	/**
	 * 处理BASE_PART节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBasePart(BLCICarShipTaxPayMsg blCICarShipTaxPayMsg, Node node)
			throws Exception {
		
		String payID = XMLUtils.getChildNodeValue(node, "PAY_ID");
		
		String payNo = XMLUtils.getChildNodeValue(node, "PAY_NO");
		
		String taxComName = XMLUtils.getChildNodeValue(node, "DEPARTMENT");
		
		CICarShipTaxPayMsgSchema schema = blCICarShipTaxPayMsg.getArr(0);
        schema.setPayID(payID);
        schema.setPayNo(payNo);
        schema.setTaxComName(taxComName);
        schema.setFlag("L");
	}
	
	protected void saveMsg(DbPool dbPool, BLCICarShipTaxPayMsg blCICarShipTaxPayMsg) throws Exception{
		CICarShipTaxPayMsgSchema ciCarShipTaxPayMsgSchema = blCICarShipTaxPayMsg.getArr(0);
		String strTaxRelifFlag = "";
		if(!ciCarShipTaxPayMsgSchema.getPayID().equals("") && 
				(ciCarShipTaxPayMsgSchema.getPayID().equals("01") ||
						ciCarShipTaxPayMsgSchema.getPayID().equals("02"))){
			if("01".equals(ciCarShipTaxPayMsgSchema.getPayID())){
				strTaxRelifFlag = "2";
			}else{
				strTaxRelifFlag = "3";
			}
			
			String strsql1 = " Insert Into CICarShipTaxPayMsg( PolicyNo, ValidNo, PayNo, TaxComName, OperaterCode, OperateDate, Flag) " +
			"values('" + 
			ciCarShipTaxPayMsgSchema.getPolicyNo()+ "','" + 
			ciCarShipTaxPayMsgSchema.getValidNo() + "','" + 
			ciCarShipTaxPayMsgSchema.getPayNo()   + "','" + 
			ciCarShipTaxPayMsgSchema.getTaxComName() + "','" +
			ciCarShipTaxPayMsgSchema.getOperaterCode() +"','" +
			ciCarShipTaxPayMsgSchema.getOperateDate() + "','" +
			ciCarShipTaxPayMsgSchema.getFlag()+"')";
			dbPool.insert(strsql1);
			
			String strsql2 = "update prpccarshiptax set PAIDFREECERTIFICATE='" + ciCarShipTaxPayMsgSchema.getPayNo()
			+ "', TAXCOMNAME='" + ciCarShipTaxPayMsgSchema.getTaxComName() 
			+ "', TAXRELIFFLAG='" +  strTaxRelifFlag
			+ "', TAXFLAG='" + ciCarShipTaxPayMsgSchema.getPayID() 
			+ "' where policyno='" + ciCarShipTaxPayMsgSchema.getPolicyNo() + "'" ;
			dbPool.update(strsql2);
			
			
			String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
			String strsql3 = "";
			if(!"1".equals(strSwitch)){
				strsql3 = "update cicarshiptaxdemand set PAIDCERTIFICATE='" + ciCarShipTaxPayMsgSchema.getPayNo() 
				+ "', TAXCOMNAME='" + ciCarShipTaxPayMsgSchema.getTaxComName() 
				+ "', TAXFLAG='" + ciCarShipTaxPayMsgSchema.getPayID() 
				+ "' where policyno='" + ciCarShipTaxPayMsgSchema.getPolicyNo() + "'";
				dbPool.update(strsql3);
			}else{
			    DbPool dbpool1=new DbPool();
			    try {
			        BLPrpCitemCar blPrpCitemCar = new BLPrpCitemCar();
				    blPrpCitemCar.getData(ciCarShipTaxPayMsgSchema.getPolicyNo());
				    strsql3 = "update cicarshiptaxdemand set PAIDCERTIFICATE='" + ciCarShipTaxPayMsgSchema.getPayNo() 
				        + "', TAXCOMNAME='" + ciCarShipTaxPayMsgSchema.getTaxComName() 
				        + "', TAXFLAG='" + ciCarShipTaxPayMsgSchema.getPayID() 
				        + "' where DemandNo='" + blPrpCitemCar.getArr(0).getDemandNo() + "'";
					dbpool1.open("platformNewDataSource");
			        dbpool1.beginTransaction();
			        dbpool1.update(strsql3);
			        dbpool1.commitTransaction();
			    } catch (Exception e) {
			        dbpool1.rollbackTransaction();
			        e.printStackTrace();
			    }finally{
			        dbpool1.close();
			    }
			}
			
		}
	}
	

}

