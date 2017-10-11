package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCICarShipTaxPayMsg;
import com.sp.indiv.ci.schema.CICarShipTaxPayMsgSchema;
import com.sp.prpall.blsvr.cb.BLPrpCitemCar;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;


/**
 * 车船税上传外地车辆完税信息的解码器
 * 
 */
public class CarShipTaxUpdatePayNoDecoder {

	public static void main(String[] args) throws Exception {}

	/**
	 * 解码
	 * 
	 * @return 上传外地车辆完税信息XML格式串
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, BLCICarShipTaxPayMsg blCICarShipTaxPayMsg, String content) throws Exception {
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
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
	
	protected void saveMsg(DbPool dbPool, BLCICarShipTaxPayMsg blCICarShipTaxPayMsg) throws Exception{
		CICarShipTaxPayMsgSchema ciCarShipTaxPayMsgSchema = new CICarShipTaxPayMsgSchema();
	    
	    ciCarShipTaxPayMsgSchema = blCICarShipTaxPayMsg.getArr(0);
	    String strTaxRelifFlag = "";
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
	    					+ "', TAXFLAG='" + ciCarShipTaxPayMsgSchema.getPayID()
	    					+ "', TAXRELIFFLAG='" +  strTaxRelifFlag
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
