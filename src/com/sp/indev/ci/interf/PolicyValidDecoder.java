package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCICarShipTaxQGDemand;
import com.sp.indiv.ci.blsvr.BLCICarshipTaxDemand;
import com.sp.indiv.ci.blsvr.BLCICheckCarShipTaxDetailQG;
import com.sp.indiv.ci.blsvr.BLCIInsureDemand;
import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.schema.CICarshipTaxDemandSchema;
import com.sp.indiv.ci.schema.CICheckCarShipTaxDetailQGSchema;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPrpCmain;
import com.sp.prpall.schema.PrpCmainSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.blsvr.cb.BLPrpCjfcdLogMsg;
import com.sp.indiv.ci.blsvr.BLCICarShipTaxPayMsg;
import com.sp.indiv.ci.blsvr.BLCICarshipTaxDemand;
import com.sp.utility.string.ChgDate;

/**
 * ����Ͷȷ��ѯ�������ݵĽ�����
 * 
 */
public class PolicyValidDecoder {

	public static void main(String[] args) throws Exception {
	}
	
	private static Logger logger = Logger.getLogger(PolicyValidDecoder.class);
	
	/**
	 * ����
	 * 
	 * @return XX��ѯ����XML��ʽ��
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, 
					   BLPolicy blPolicy, 
					   String content)
		throws SQLException, UserException, Exception 
	{   
		
		logger.info("[XXȷ�Ϸ��ر���]:"+content);
		
		
		InputStream in    = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		
		PolicyCarShipTaxValidDecoder policyCarShipTaxValidDecoder = new PolicyCarShipTaxValidDecoder();
		
		
		int type = -1;
		
		try
		{
			type = processHead(blPolicy, XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
			if (type == 1)
			{
				processBody(blPolicy, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
			} 
			
			/*else 
			{
				
				CIInsureValidSchema schema = new CIInsureValidSchema();
				schema.setDemandNo(blPolicy.getBLCIInsureDemand().getArr(0).getDemandNo());	
				schema.setProposalNo(blPolicy.getBLPrpCmain().getArr(0).getProposalNo());	
				schema.setPolicyNo(blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());		
				schema.setComCode(blPolicy.getBLPrpCmain().getArr(0).getComCode());			
				schema.setValidNo(blPolicy.getBLPrpCmain().getArr(0) .getProposalNo());     
				schema.setValidTime("");													
				
				
				schema.setFlag("1     ");													
				
				
				blPolicy.getBLCIInsureValid().setArr(schema);
			}*/
			
		}
		catch(Exception ex1)
		{
			throw new Exception("����ƽ̨���ش�����XX�ţ�" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo() 
		             + "���������Ա��ϵ��" + ex1.getMessage());
		}
		
		if(type != 1)
		{
			throw new Exception(blPolicy.getBLCIInsureValid().getArr(0).getErrorMessage());
		}
		
		
		
		try
		{
			saveCIInsureValid(dbPool, blPolicy);
			
			policyCarShipTaxValidDecoder.updateCicarshiptaxdemand(dbPool,blPolicy);
			
			
			
			if(new UtiPower().checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(),blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
			   updateTaxPrintNo(dbPool, blPolicy);
			   updateCICheckCarshipTaxDetailqg(dbPool, blPolicy);
			}
			
			
			
			if("01".equals(blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2)) 
					&& "0507".equals(blPolicy.getBLPrpCmain().getArr(0).getRiskCode())){
				
				ChgDate chgDate = new ChgDate();
				String nowDateStr = chgDate.getCurrentTime("yyyy-MM-dd");
				if(new UtiPower().carShipTaxCheckBJSwitch(nowDateStr)){
					dealCarshiptaxBJ(dbPool, blPolicy);
				}
				
			}
			
		}
		catch(Exception ex2)
		{
			throw new Exception("��ƽ̨�����ɹ�����" + ex2.getMessage() + " XX�ţ�" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
		}
		
	}
	
	public void decodeNew(DbPool dbPool, BLPolicy blPolicy, String content) throws SQLException, UserException, Exception 
	{   
		logger.info("[XXȷ�Ϸ��ر���]:"+content);
		InputStream in    = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		PolicyCarShipTaxValidDecoder policyCarShipTaxValidDecoder = new PolicyCarShipTaxValidDecoder();
		
		int type = -1;
		try
		{
			type = processHead(blPolicy, XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
			if (type == 1)
			{
				processBody(blPolicy, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
			} 
		}
		catch(Exception ex1)
		{
			ex1.printStackTrace();
			throw new Exception("����ƽ̨���ش�����XX�ţ�" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo() 
		          + "���������Ա��ϵ��" + ex1.getMessage());
		}
		if(type != 1)
		{
			throw new Exception(blPolicy.getBLCIInsureValid().getArr(0).getErrorMessage());
		}
		
		try
		{
			saveCIInsureValidNew(dbPool, blPolicy);
			policyCarShipTaxValidDecoder.updateCicarshiptaxdemandNew(dbPool,blPolicy);
		}
		catch(Exception ex2)
		{	
			ex2.printStackTrace();
			throw new Exception("��ƽ̨�����ɹ�����" + ex2.getMessage() + " XX�ţ�" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
		}
	}
	
	/**
	 * תXX����
	 * @author liuweichang-ghq 20140617
	 * @param blPolicy
	 * @param content
	 * @throws SQLException
	 * @throws UserException
	 * @throws Exception
	 */
	public void decodeNewProcess(BLPolicy blPolicy, String content) throws SQLException, UserException, Exception 
	{   
		logger.info("[XXȷ�Ϸ��ر���]:"+content);
		InputStream in    = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		PolicyCarShipTaxValidDecoder policyCarShipTaxValidDecoder = new PolicyCarShipTaxValidDecoder();
		
		int type = -1;
		try
		{
			type = processHead(blPolicy, XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
			if (type == 1)
			{
				processBody(blPolicy, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
			} 
		}
		catch(Exception ex1)
		{
			throw new Exception("����ƽ̨���ش�����XX�ţ�" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo() 
					+ "���������Ա��ϵ��" + ex1.getMessage());
		}
		if(type != 1)
		{
			throw new Exception(blPolicy.getBLCIInsureValid().getArr(0).getErrorMessage());
		}
	}
	
	/**
	 * 
	 * @param dbPool
	 * @param blPolicy
	 * @throws SQLException
	 * @throws UserException
	 * @throws Exception
	 */
	public void decodeNewSave(DbPool dbPool, BLPolicy blPolicy) throws SQLException, UserException, Exception 
	{   
		PolicyCarShipTaxValidDecoder policyCarShipTaxValidDecoder = new PolicyCarShipTaxValidDecoder();
		
		try
		{
			saveCIInsureValidNew(dbPool, blPolicy);
			policyCarShipTaxValidDecoder.updateCicarshiptaxdemandNew(dbPool,blPolicy);
		}
		catch(Exception ex2)
		{
			throw new Exception("��ƽ̨�����ɹ�����" + ex2.getMessage() + " XX�ţ�" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
		}
	}
	
	
	public void decodeNewToSave(DbPool dbPool, BLPolicy blPolicy) throws SQLException, UserException, Exception 
	{
		PolicyCarShipTaxValidDecoder policyCarShipTaxValidDecoder = new PolicyCarShipTaxValidDecoder();
		try
		{
			saveCIInsureValidNewToSave(dbPool, blPolicy);
			policyCarShipTaxValidDecoder.updateCicarshiptaxdemandNewToSave(dbPool,blPolicy);
			if(new UtiPower().checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(),blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
				updateTaxPrintNo(dbPool, blPolicy);
				updateCICheckCarshipTaxDetailqg(dbPool, blPolicy);
			}
			if("01".equals(blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2)) 
					&& "0507".equals(blPolicy.getBLPrpCmain().getArr(0).getRiskCode())){
				ChgDate chgDate = new ChgDate();
				String nowDateStr = chgDate.getCurrentTime("yyyy-MM-dd");
				if(new UtiPower().carShipTaxCheckBJSwitch(nowDateStr)){
					dealCarshiptaxBJ(dbPool, blPolicy);
				}
			}
		}
		catch(Exception ex2)
		{
			ex2.printStackTrace();
			throw new Exception("��ƽ̨�����ɹ�����" + ex2.getMessage() + " XX�ţ�" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
		}
	}
	
	/**
	 * add by fanjiangtao 20141223 reason:תXX�߼�����
	 */
	public void decodeNewToSave1(DbPool dbPool, BLPolicy blPolicy) throws SQLException, UserException, Exception 
	{
		PolicyCarShipTaxValidDecoder policyCarShipTaxValidDecoder = new PolicyCarShipTaxValidDecoder();
		try
		{
			saveCIInsureValidNewToSave(dbPool, blPolicy);
			policyCarShipTaxValidDecoder.updateCicarshiptaxdemandNewToSave(dbPool,blPolicy);
			if(new UtiPower().checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(),blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
				updateCICheckCarshipTaxDetailqg(dbPool, blPolicy);
			}
			if("01".equals(blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2)) 
					&& "0507".equals(blPolicy.getBLPrpCmain().getArr(0).getRiskCode())){
				ChgDate chgDate = new ChgDate();
				String nowDateStr = chgDate.getCurrentTime("yyyy-MM-dd");
				if(new UtiPower().carShipTaxCheckBJSwitch(nowDateStr)){
					dealCarshiptaxBJ(dbPool, blPolicy);
				}
			}
		}
		catch(Exception ex2)
		{
			throw new Exception("��ƽ̨�����ɹ�����" + ex2.getMessage() + " XX�ţ�" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
		}
	}
	/**
	 * add by fanjiangtao 20141223 reason:תXX�߼�����
	 * @param dbPool
	 * @param blPolicy
	 * @throws SQLException
	 * @throws UserException
	 * @throws Exception
	 */
	public void decodeNewToSave2(DbPool dbPool, BLPolicy blPolicy) throws SQLException, UserException, Exception 
	{
		PolicyCarShipTaxValidDecoder policyCarShipTaxValidDecoder = new PolicyCarShipTaxValidDecoder();
		try
		{
			if(new UtiPower().checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(),blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
				updateTaxPrintNo(dbPool, blPolicy);
			}
			
		}
		catch(Exception ex2)
		{
			throw new Exception("��ƽ̨�����ɹ�����" + ex2.getMessage() + " XX�ţ�" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
		}
	}

	/**
	 * ����HEAD��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	
	/*protected int processHead(Node node) throws Exception 
	{
		int type = -1;
		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
		if (!responseCode.equals("1")) {
			type = 0;
		}
		else
		{
			type = 1;
		}
		return type;
	}*/
	
	
	protected int processHead(BLPolicy blPolicy, Node node) throws Exception 
	{
		
		int type = 1;
		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");	
		if (!responseCode.equals("1")) {
			String errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE"); 	
			CIInsureValidSchema schema = new CIInsureValidSchema();
			schema.setErrorMessage(errorMessage);
			blPolicy.getBLCIInsureValid().setArr(schema);
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
	protected void processBody(BLPolicy blPolicy, Node node) throws Exception {
		processBasePart(blPolicy, XMLUtils.getChildNodeByTagName(node,
				"BASE_PART"));
	}

	/**
	 * ����BASE_PART��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBasePart(BLPolicy blPolicy, 
								   Node node)
			throws Exception 
	{
		
		String confirmSequenceNo = XMLUtils.getChildNodeValue(node, "CONFIRM_SEQUENCE_NO");
		DateTime validTime = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND);
		
		String strImmeValidFlag = "";
		String strImmeValidDate = "";
		
		
		String strWarrantNo = "";
		String strBankPaymentTime = "";
		
		
		String strDeficitFlag= "";   
		
		
		
		String strTaxPrintNo = "";
		
		String strReinsureFlag = "";
		if(blPolicy.getBLPrpCmain().getArr(0).getComCode().trim().substring(0, 2).equals("01") ||
				blPolicy.getBLPrpCmain().getArr(0).getComCode().trim().substring(0, 2).equals("07"))
		{
			
			if(blPolicy.getBLPrpCmain().getArr(0).getComCode().trim().substring(0, 2).equals("01")){
				strImmeValidFlag = XMLUtils.getChildNodeValue(node, "POLICY_EFFECT_FLAG");
				strImmeValidDate = XMLUtils.getChildNodeValue(node, "POLICY_EFFECT_TIME");
				
				strDeficitFlag = XMLUtils.getChildNodeValue(node,"DEFICIT_FLAG");
				
			}
			

			
			if(blPolicy.getBLPrpCmain().getArr(0).getComCode().trim().substring(0, 2).equals("07")){
				strWarrantNo = XMLUtils.getChildNodeValue(node, "WARRANT_NO");
				strBankPaymentTime = XMLUtils.getChildNodeValue(node, "BANK_PAYMENT_TIME");
			}
			
		}
		else
		{
			strReinsureFlag = XMLUtils.getChildNodeValue(node, "REINSURE_FLAG");	
			
			if(new UtiPower().checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
				strTaxPrintNo = XMLUtils.getChildNodeValue(node,"TAX_PRINT_NO");
				blPolicy.getBLPrpCcarshipTax().getArr(0).setTaxPrintNo(strTaxPrintNo);
			}
			
		}
		
		
		
		PolicyCarShipTaxValidDecoder policyCarShipTaxValidDecoder = new PolicyCarShipTaxValidDecoder();
		policyCarShipTaxValidDecoder.decode(blPolicy,node);
		

		
		CIInsureValidSchema schema = new CIInsureValidSchema();
		schema.setValidNo(confirmSequenceNo);	
		
		
		schema.setReinsureFlag(strReinsureFlag);	
		
		
		schema.setWarrantNo(strWarrantNo);
		if(blPolicy.getBLPrpCmain().getArr(0).getComCode().trim().substring(0, 2).equals("07")
				&&!"".equals(strBankPaymentTime)){
			schema.setBankPaymentTime(strBankPaymentTime.substring(0,4)+"-"+strBankPaymentTime.substring(4,6)+
				"-"+strBankPaymentTime.substring(6,8)+" "+strBankPaymentTime.substring(8,10)+
				":"+strBankPaymentTime.substring(10,12)+":"+strBankPaymentTime.substring(12,14));
		}
		
		schema.setDemandNo(blPolicy.getBLCIInsureDemand().getArr(0).getDemandNo());	
		schema.setProposalNo(blPolicy.getBLPrpCmain().getArr(0).getProposalNo());	
		schema.setPolicyNo(blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());		
		schema.setComCode(blPolicy.getBLPrpCmain().getArr(0).getComCode());			
		schema.setValidTime("" + validTime.toString());								
		
		schema.setImmeValidFlag(strImmeValidFlag);
		schema.setImmeValidDate(strImmeValidDate);
		
		
		
		schema.setFlag("0    ");
		
		
		
		schema.setDeficitFlag(strDeficitFlag);
		
		
		blPolicy.getBLCIInsureValid().setArr(schema);
	}

	/**
	 * XXXXX��XXȷ������Ϣ
	 * 
	 * @param dbPool
	 * @param blpolicy
	 * @throws Exception
	 */
	private void saveCIInsureValid(DbPool dbPool, 
			                       BLPolicy blPolicy)
			throws SQLException, UserException, Exception 
    {
		BLCIInsureValid blCIInsureValid = blPolicy.getBLCIInsureValid();
		
		String updateSQL = " UPDATE CIInsureDemand SET PolicyNo = '"
				+ blPolicy.getBLPrpCmain().getArr(0).getPolicyNo() + "'"
				+ " WHERE DemandNo = '"
				+ blCIInsureValid.getArr(0).getDemandNo() + "'";	
		
		
		
		try
		{
			if(dbPool != null) 
			{
				
				blCIInsureValid.save(dbPool);
	            
	            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
	            if("1".equals(strSwitch)){
	                DbPool dbpool1=new DbPool();
	                try {
	                    dbpool1.open("platformNewDataSource");
	                    dbpool1.beginTransaction();
	                    dbpool1.update(updateSQL);
	                    dbpool1.commitTransaction();
	                } catch (Exception e) {
	                    dbpool1.rollbackTransaction();
	                    e.printStackTrace();
	                }finally{
	                    dbpool1.close();
	                }
	            }else{
	                dbPool.update(updateSQL);
	            }
	            
			}
			else
			{
				throw new Exception("��дCIInsureValid��ʱdbPoolΪ�ա���������������Ա��ϵ�������ݣ�");
			}
		}
		catch(SQLException sqlex)
		{
			throw new Exception("����SQL����" + sqlex.getMessage());
		}
		catch(Exception ex)
		{
			throw ex;
		}
		
	}
	
	private void saveCIInsureValidNew(DbPool dbPool, BLPolicy blPolicy) throws SQLException, UserException, Exception 
	{
		BLCIInsureValid blCIInsureValid = blPolicy.getBLCIInsureValid();
		
		String updateSQL = " UPDATE CIInsureDemand SET PolicyNo = '"
				+ blPolicy.getBLPrpCmain().getArr(0).getPolicyNo() + "'"
				+ " WHERE DemandNo = '"
				+ blCIInsureValid.getArr(0).getDemandNo() + "'";	
		try
		{
			if(dbPool != null) 
			{
				
				dbPool.update(updateSQL);
			}
			else
			{
			throw new Exception("��дCIInsureValid��ʱdbPoolΪ�ա���������������Ա��ϵ�������ݣ�");
			}
		}
		catch(SQLException sqlex){
			throw new Exception("����SQL����" + sqlex.getMessage());
		}
		catch(Exception ex){
			throw ex;
		}
	}
	private void saveCIInsureValidNewToSave(DbPool dbPool, BLPolicy blPolicy) throws SQLException, UserException, Exception 
	{
		BLCIInsureValid blCIInsureValid = blPolicy.getBLCIInsureValid();
		try
		{
			if(dbPool != null) 
			{
				
				blCIInsureValid.save(dbPool);
			}
			else
			{
			throw new Exception("��дCIInsureValid��ʱdbPoolΪ�ա���������������Ա��ϵ�������ݣ�");
			}
		}
		catch(SQLException sqlex){
			throw new Exception("����SQL����" + sqlex.getMessage());
		}
		catch(Exception ex){
			throw ex;
		}
	}
	
	
	
	
	private void updateTaxPrintNo(DbPool dbPool, BLPolicy blPolicy)
			throws SQLException, UserException, Exception {
		
	    	String strTaxPrintNo = blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxPrintNo();
	    	String updateSQL = "";
	    	if(strTaxPrintNo != null && !"".equals(strTaxPrintNo)){
	    	
			updateSQL = " UPDATE PrpCCarShiptax SET TaxPrintNo = '"
					+ strTaxPrintNo + "'"
					+ " WHERE PolicyNo = '"
					+ blPolicy.getBLPrpCmain().getArr(0).getPolicyNo() + "'";
	    	}else{
	    		
	    	    
	    		String demandno = blPolicy.getBLPrpCitemCar().getArr(0).getDemandNo();
	    		BLCICarShipTaxQGDemand blCICarShipTaxQGDemand = new BLCICarShipTaxQGDemand();
	    		ArrayList valueList = new ArrayList();
	    		valueList.add(demandno);
	    		blCICarShipTaxQGDemand.query("demandno = ?", valueList);
	    		if(blCICarShipTaxQGDemand.getSize()>0){
	    			strTaxPrintNo = blCICarShipTaxQGDemand.getArr(0).getTaxPrintNo();
	    		}
	    		
	    	    updateSQL = " UPDATE PrpCCarShiptax SET TaxPrintNo = '"
			+ strTaxPrintNo + "'"
			+ " WHERE PolicyNo = '"
			+ blPolicy.getBLPrpCmain().getArr(0).getPolicyNo() + "'";
	    	}
	        

		try {
			if (dbPool != null) {
				dbPool.update(updateSQL);
			} else {
				throw new Exception(
						"��дCIInsureValid��ʱdbPoolΪ�ա���������������Ա��ϵ�������ݣ�");
			}
		} catch (SQLException sqlex) {
			throw new Exception("����SQL����" + sqlex.getMessage());
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	
	
	private void updateCICheckCarshipTaxDetailqg(DbPool dbPool, BLPolicy blPolicy)
			throws SQLException, UserException, Exception {
		CICheckCarShipTaxDetailQGSchema ciCheckCarShipTaxDetailQGSchema = new CICheckCarShipTaxDetailQGSchema();
		ciCheckCarShipTaxDetailQGSchema.setCheckNo("1");
		ciCheckCarShipTaxDetailQGSchema.setConFirmSequenceNo(blPolicy.getBLCIInsureValid().getArr(0).getValidNo());
		ciCheckCarShipTaxDetailQGSchema.setType("P");
		ciCheckCarShipTaxDetailQGSchema.setProposalNo(blPolicy.getBLPrpCmain().getArr(0).getProposalNo());
		ciCheckCarShipTaxDetailQGSchema.setCretiNo(blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
		ciCheckCarShipTaxDetailQGSchema.setSerialNo(blPolicy.getBLPrpCcarshipTax().getArr(0).getSerialNo());
		ciCheckCarShipTaxDetailQGSchema.setTaxDeclareQueryNo("1");
		ciCheckCarShipTaxDetailQGSchema.setTaxConditionCode(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxRelifFlag());
		ciCheckCarShipTaxDetailQGSchema.setMTaxAnnualTaxDue(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxActual());
		ciCheckCarShipTaxDetailQGSchema.setMTaxSumTaxDeFault(blPolicy.getBLPrpCcarshipTax().getArr(0).getPreviousPay());
		ciCheckCarShipTaxDetailQGSchema.setMTaxSumOverDue(blPolicy.getBLPrpCcarshipTax().getArr(0).getLateFee());
		ciCheckCarShipTaxDetailQGSchema.setMTaxSumTax(blPolicy.getBLPrpCcarshipTax().getArr(0).getSumPayTax());
		ciCheckCarShipTaxDetailQGSchema.setSignDate(blPolicy.getBLPrpCmain().getArr(0).getOperateDate());
		ciCheckCarShipTaxDetailQGSchema.setLicenseNo(blPolicy.getBLPrpCitemCar().getArr(0).getLicenseNo());
		ciCheckCarShipTaxDetailQGSchema.setFrameNo(blPolicy.getBLPrpCitemCar().getArr(0).getFrameNo());
		ciCheckCarShipTaxDetailQGSchema.setEngineNo(blPolicy.getBLPrpCitemCar().getArr(0).getEngineNo());
		ciCheckCarShipTaxDetailQGSchema.setStatus("0");
		ciCheckCarShipTaxDetailQGSchema.setCheckSuccessFlag("0");
		ciCheckCarShipTaxDetailQGSchema.setComCode(blPolicy.getBLPrpCmain().getArr(0).getComCode());
		BLCICheckCarShipTaxDetailQG blciCheckCarShipTaxDetailQG = new BLCICheckCarShipTaxDetailQG();
		try {
			blciCheckCarShipTaxDetailQG.setArr(ciCheckCarShipTaxDetailQGSchema);
			blciCheckCarShipTaxDetailQG.save(dbPool);
		} catch (SQLException sqlex) {
			throw new Exception("����SQL����" + sqlex.getMessage());
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	
	private void dealCarshiptaxBJ(DbPool dbPool, BLPolicy blPolicy)
			throws SQLException, UserException, Exception {
		CICheckCarShipTaxDetailQGSchema ciCheckCarShipTaxDetailQGSchema = new CICheckCarShipTaxDetailQGSchema();
		ciCheckCarShipTaxDetailQGSchema.setCheckNo("1");
		ciCheckCarShipTaxDetailQGSchema.setConFirmSequenceNo(blPolicy.getBLCIInsureValid().getArr(0).getValidNo());
		ciCheckCarShipTaxDetailQGSchema.setType("P");
		ciCheckCarShipTaxDetailQGSchema.setProposalNo(blPolicy.getBLPrpCmain().getArr(0).getProposalNo());
		ciCheckCarShipTaxDetailQGSchema.setCretiNo(blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
		ciCheckCarShipTaxDetailQGSchema.setSerialNo(blPolicy.getBLPrpCcarshipTax().getArr(0).getSerialNo());
		ciCheckCarShipTaxDetailQGSchema.setTaxDeclareQueryNo("1");
		ciCheckCarShipTaxDetailQGSchema.setMTaxAnnualTaxDue(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxActual());
		ciCheckCarShipTaxDetailQGSchema.setMTaxSumTaxDeFault(blPolicy.getBLPrpCcarshipTax().getArr(0).getPreviousPay());
		ciCheckCarShipTaxDetailQGSchema.setMTaxSumOverDue(blPolicy.getBLPrpCcarshipTax().getArr(0).getLateFee());
		ciCheckCarShipTaxDetailQGSchema.setMTaxSumTax(blPolicy.getBLPrpCcarshipTax().getArr(0).getSumPayTax());
		ciCheckCarShipTaxDetailQGSchema.setLicenseNo(blPolicy.getBLPrpCitemCar().getArr(0).getLicenseNo());
		ciCheckCarShipTaxDetailQGSchema.setFrameNo(blPolicy.getBLPrpCitemCar().getArr(0).getFrameNo());
		ciCheckCarShipTaxDetailQGSchema.setEngineNo(blPolicy.getBLPrpCitemCar().getArr(0).getEngineNo());
		ciCheckCarShipTaxDetailQGSchema.setStatus("0");
		ciCheckCarShipTaxDetailQGSchema.setCheckSuccessFlag("0");
		ciCheckCarShipTaxDetailQGSchema.setComCode(blPolicy.getBLPrpCmain().getArr(0).getComCode());
		ciCheckCarShipTaxDetailQGSchema.setTaxConditionCode(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxRelifFlag());
		ciCheckCarShipTaxDetailQGSchema.setExtendChar1(blPolicy.getBLCICarshipTaxDemand().getArr(0).getPaidCertificate());
		
		BLCICarshipTaxDemand blCICarShiptaxDemand = new BLCICarshipTaxDemand();
		
		String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
		
		ArrayList<String> iWhereValue=new ArrayList<String>();
		if(!"1".equals(strSwitch)){
			iWhereValue.add(blPolicy.getBLPrpCmain().getArr(0).getProposalNo());
			blCICarShiptaxDemand.query("proposalno = ?",iWhereValue);
		}else{
			iWhereValue.add(blPolicy.getBLPrpCitemCar().getArr(0).getDemandNo());
			blCICarShiptaxDemand.query("DemandNo = ?",iWhereValue);
		}
		
		
		ciCheckCarShipTaxDetailQGSchema.setExtendChar2(blCICarShiptaxDemand.getArr(0).getCommissionTax());
		
		BLPrpCjfcdLogMsg blPrpCjfcdLogMsg = new BLPrpCjfcdLogMsg();
		blPrpCjfcdLogMsg.query(dbPool,"policyno = '"+blPolicy.getBLPrpCmain().getArr(0).getPolicyNo()+"'");
		String SignDate = new ChgDate().toFormat(blPrpCjfcdLogMsg.getArr(0).getValidDate()+"");
		ciCheckCarShipTaxDetailQGSchema.setSignDate(SignDate.trim().substring(0,19));
		
		BLCICheckCarShipTaxDetailQG blciCheckCarShipTaxDetailQG = new BLCICheckCarShipTaxDetailQG();
		try {
			blciCheckCarShipTaxDetailQG.setArr(ciCheckCarShipTaxDetailQGSchema);
			blciCheckCarShipTaxDetailQG.save(dbPool);
		} catch (SQLException sqlex) {
			throw new Exception("����SQL����" + sqlex.getMessage());
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	/**
	 * �������ڸ�ʽ
	 * 
	 * @param dateString
	 *            8���ַ�������
	 * @return YYYY-MM-DD��ʽ������
	 */
	private String correctDate(String dateString) {
		if (dateString.length() < 8) {
			throw new IllegalArgumentException(dateString
					+ "�����ڸ�ʽ���ԣ�����Ϊ����8λ�Ĵ����ֵ��ַ���");
		}
		String result = dateString.substring(0, 4) + "-"
				+ dateString.substring(4, 6) + "-" + dateString.substring(6, 8);
		return result;
	}

	/**
	 * ��������ʱ���ʽ
	 * 
	 * @param dateString
	 *            ����ǰ������ʱ��
	 * @return �����������ʱ��
	 */
	private String correctDateTime(String dateString) {
		String result = correctDate(dateString);
		if (dateString.length() >= 10) {
			result += " " + dateString.substring(8, 10);
		}
		if (dateString.length() >= 12) {
			result += ":" + dateString.substring(10, 12);
		}
		if (dateString.length() >= 14) {
			result += ":" + dateString.substring(12, 14);
		}
		return result;
	}
}
