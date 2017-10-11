package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCIEndorValid;
import com.sp.indiv.ci.schema.CIEndorValidSchema;
import com.sp.prpall.blsvr.cb.BLPrpCmain;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;

/**
 * 发送退XXXXX查询请求数据的解码器
 * 
 */
public class PolicyWithDrawQueryDecoder {

	public static void main(String[] args) 
		throws Exception 
	{
	}

	
	private static Logger logger = Logger.getLogger(PolicyWithDrawQueryDecoder.class);
	
	/**
	 * 解码
	 * 
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, BLEndorse blEndorse, String content)
			throws Exception {
		
		
		logger.info("[XX退XXXXX查询返回报文]:"+content);
		
        
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		int type = -1;
		try
		{
			type = processHead(blEndorse, XMLUtils .getChildNodeByPath(document, "/PACKET/HEAD"));
			if (type == 1) {
				processBody(blEndorse, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
			}
		}
		catch(Exception ex1)
		{
			throw new Exception("解析平台全单退XXXXX接口返回串错误。XX号：" + blEndorse.getBLPrpPhead().getArr(0).getPolicyNo() 
		             + "，请与管理员联系！" + ex1.getMessage());
		}
		
		if(type != 1)
		{
			throw new Exception(blEndorse.getBLCIEndorValid().getArr(0).getErrorMessage());
		}
		
		try
		{
			
			saveCIEndorValid(dbPool, blEndorse, type);
		}
		catch(Exception ex2)
		{
			throw new Exception("与平台交互成功。但" + ex2.getMessage() + " XX号：" + blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
		}
	}

	/**
	 * 处理HEAD节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected int processHead(BLEndorse blEndorse, Node node) throws Exception {
		
		int type = 1;

		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
		if (!responseCode.equals("1")) {
			String errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE"); 	
			CIEndorValidSchema schema = new CIEndorValidSchema();
			schema.setErrorMessage(errorMessage);
			blEndorse.getBLCIEndorValid().setArr(schema);
			type = 0;
		}
		return type;
	}

	/**
	 * 处理BODY节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBody(BLEndorse blEndorse, Node node) 
		throws Exception 
	{
		processBasePart(blEndorse, XMLUtils.getChildNodeByTagName(node, "BASE_PART"));
		
		BLPrpCmain blPrpCmain = new BLPrpCmain();
		blPrpCmain.getData(blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
		String strOperateDate = blPrpCmain.getArr(0).getOperateDate();
		 if(new UtiPower().checkCarShipTaxJZ(blEndorse.getBLPrpPhead().getArr(0).getComCode(), strOperateDate)){
			 processVehicleTaxation(blEndorse,XMLUtils.getChildNodeByTagName(node, "VehicleTaxation"));
		 }
		
	}

	/**
	 * 处理BASE_PART节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBasePart(BLEndorse blEndorse, Node node)
			throws Exception {
		
		
		
		

		String cancelPremium = XMLUtils.getChildNodeValue(node, "CANCEL_PREMIUM");
		String formula = XMLUtils.getChildNodeValue(node, "FORMULA");
		String strDemanNo = XMLUtils.getChildNodeValue(node, "CANCEL_QUERY_NO");
		
		String strCancelTax = "";
		String strRiskCode = blEndorse.getBLPrpPhead().getArr(0).getRiskCode();
		String strComCode  = blEndorse.getBLPrpPhead().getArr(0).getComCode();
		if(new UtiPower().checkCIInsureSH(strRiskCode, strComCode)){
			strCancelTax = XMLUtils.getChildNodeValue(node, "CANCEL_TAX");
		}
		
		
		String strRestricFlag = "";
		String strPreferentialPremium = "";
		String strPreferentialFormula = "";
		String strPreferentialDay = "";
		if(strComCode.substring(0, 2).equals("01")) {
			strRestricFlag         = XMLUtils.getChildNodeValue(node, "RESTRIC_FLAG");
			strPreferentialPremium = XMLUtils.getChildNodeValue(node, "PREFERENTIAL_PREMIUM");
			strPreferentialFormula = XMLUtils.getChildNodeValue(node, "PREFERENTIAL_FORMULA");
			strPreferentialDay     = XMLUtils.getChildNodeValue(node, "PREFERENTIAL_DAY");
		}
		
        
        UtiPower utiPower = new UtiPower();
        String strTaxAmendPremium="";
        String strTaxAmendDeclare="";
        if(utiPower.checkCICarShipTaxQG(strRiskCode, strComCode.substring(0,2))){
            strTaxAmendPremium= XMLUtils.getChildNodeValue(node, "TAX_CANCEL_PREMIUM");
            strTaxAmendDeclare= XMLUtils.getChildNodeValue(node, "TAX_CANCEL_DECLARE");
        }
        
        String strFundAmount = "";
        if(utiPower.addFundRescueInfo(strComCode)){
        	strFundAmount = XMLUtils.getChildNodeValue(node, "FUND_AMOUNT");
        }
        
        
		
		
		DateTime validTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND);
		
		CIEndorValidSchema schema = new CIEndorValidSchema();
		schema.setDemandNo(strDemanNo);                                
		
		
		if(cancelPremium==null||"".equals(cancelPremium))
			cancelPremium = "0";
		
		
		schema.setChgPremium("" + (Double.parseDouble(cancelPremium)));
		schema.setValidTime("" + validTime.toString());	               
		schema.setPtext(formula);		                               
		schema.setPolicyNo(blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());		
		schema.setValidStatus("3");						               
		
		if(new UtiPower().checkCIInsureSH(strRiskCode, strComCode)){
			schema.setCancelTax(strCancelTax);
			
			if("0507".equals(strRiskCode) && blEndorse.getBLPrpPcarshipTax().getSize()>0){
				double dbCancelTax = Double.parseDouble(ChgData.chgStrZero(strCancelTax));
				double sumPayTax = Double.parseDouble(ChgData.chgStrZero(blEndorse.getBLPrpPcarshipTax().getArr(0).getSumPayTax()));
				
				if(dbCancelTax<0&&(sumPayTax!=-dbCancelTax)){
					throw new Exception("平台退税金额"+(-dbCancelTax)+"不等于系统收款金额"+sumPayTax+",请联系平台!");
				}
			}
			
		}
		
		
		if(strComCode.substring(0, 2).equals("01")) {
			schema.setRestricFlag(strRestricFlag);
			schema.setPreferentialPremium(strPreferentialPremium);
			schema.setPreferentialFormula(strPreferentialFormula);
			schema.setPreferentialDay(strPreferentialDay);
		}
		
        
        if(utiPower.checkCICarShipTaxQG(strRiskCode, strComCode.substring(0,2))){
           schema.setTaxAmendPremium(strTaxAmendPremium);
           schema.setTaxAmendDeclare(strTaxAmendDeclare);
           
           schema.setIsAmendTax("1");
           
        }
        
        
    	if(utiPower.addFundRescueInfo(strComCode)){
    		schema.setFundAmount(strFundAmount);
    	}
    	
		blEndorse.getBLCIEndorValid().setArr(schema);
	}
	
	
	protected void processVehicleTaxation(BLEndorse blEndorse, Node node)throws Exception{
        EndorseCarShipTaxQueryDecoder endorseCarShipTaxQueryDecoder =  new EndorseCarShipTaxQueryDecoder();
        endorseCarShipTaxQueryDecoder.addVehicleTaxation(blEndorse,node);
    }
	
	/**
	 * XXXXX存XX确认主信息
	 * 
	 * @param dbPool
	 * @param blEndorse
	 * @throws Exception
	 */
	private void saveCIEndorValid(DbPool dbPool, BLEndorse blEndorse, int type)
			throws Exception {
		if (type == 1){
			
			type = 0;
		}else{
			type = 1;
		}
		
		BLCIEndorValid blCIEndorValid = blEndorse.getBLCIEndorValid();
		
		



		
		blCIEndorValid.save(dbPool);
		

		
		
		BLPrpCmain blPrpCmain = new BLPrpCmain();
		blPrpCmain.getData(blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
		String strOperateDate = blPrpCmain.getArr(0).getOperateDate();
		 if(new UtiPower().checkCarShipTaxJZ(blEndorse.getBLPrpPhead().getArr(0).getComCode(), strOperateDate)){
			 blEndorse.getBLCICarShipTaxQGEndorse().save(dbPool);
		 }
		
	}
}
