package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.*;
import com.sp.indiv.ci.schema.CIEndorValidSchema;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPrpCcarshipTax;
import com.sp.prpall.blsvr.jf.BLPrpJplanFee;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.prpall.blsvr.pg.BLPrpPcarshipTax;
import com.sp.prpall.blsvr.pg.BLPrpPhead;
import com.sp.prpall.schema.PrpCcarshipTaxSchema;
import com.sp.prpall.schema.PrpPcarshipTaxSchema;
import com.sp.prpall.schema.PrpPheadSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;

/**
 * ������XXXXXȷ���������ݵĽ�����
 * 
 */
public class PolicyWithDrawDecoder {

	
	private String cancelTax = "";
	private UtiPower utiPower = new UtiPower();
	
	public static void main(String[] args) 
		throws Exception 
	{
	}
	
	
	private static Logger logger = Logger.getLogger(PolicyWithDrawDecoder.class);
	
	/**
	 * ����
	 * 
	 * @return XX��ѯ����XML��ʽ��
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, 
			           BLEndorse blEndorse, 
			           String content)
		throws SQLException, UserException, Exception 
	{
		
		logger.info("[XX��XXXXX���ر���]:"+content);
		
        
		InputStream in    = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		
		int type = -1;
		try
		{
			type = processHead(blEndorse, XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
			if(type == 1 || type == 3)
			{
				processBody(blEndorse, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
			} 
		}
		catch(Exception ex1)
		{
			throw new Exception("����ƽ̨���ش����������ţ�" + blEndorse.getBLPrpPmain().getArr(0).getEndorseNo() 
		             + "���������Ա��ϵ��" + ex1.getMessage());
		}
		
		
		if(type != 1 && type != 3)
		{
			throw new Exception(blEndorse.getBLCIEndorValid().getArr(0).getErrorMessage());
		}
		
		
		
		try
		{
			saveCIEndorValid(dbPool, blEndorse, type);
			
			saveCarship(dbPool, blEndorse, type);
			
		}
		catch(Exception ex2)
		{
			throw new Exception("��ƽ̨�����ɹ�����" + ex2.getMessage() + " �����ţ�" + blEndorse.getBLPrpPmain().getArr(0).getEndorseNo());
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
		if(!responseCode.equals("1")) 
		{
			type = 0;
		}
		else
		{
			type = 1;
		}
		return type;
	}*/
	
	
	protected int processHead(BLEndorse blEndorse, Node node) throws Exception 
	{
		
		int type = 1;
		String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");	
		if (responseCode.equals("0")) {
			String errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE"); 	
			blEndorse.getBLCIEndorValid().getArr(0).setErrorMessage(errorMessage);
			type = 0;
		}else if(responseCode.equals("3")){
			String errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE"); 	
			blEndorse.getBLCIEndorValid().getArr(0).setErrorMessage(errorMessage);
			type = 3;
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
		processBasePart(blEndorse, XMLUtils.getChildNodeByTagName(node, "BASE_PART"));
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
		String strDemanNo = XMLUtils.getChildNodeValue(node,
				"CANCEL_CONFIRM_NO");
		
		if(utiPower.checkCICarshipTaxBJ(blEndorse.getBLPrpPhead().getArr(0).getRiskCode(), blEndorse.getBLPrpPhead().getArr(0).getComCode())){
			cancelTax = XMLUtils.getChildNodeValue(node, "CANCEL_TAX");
		}
		
		blEndorse.getBLCIEndorValid().getArr(0).setValidNo(strDemanNo);
	}

	/**
	 * XXXXX��XXȷ������Ϣ
	 * 
	 * @param dbPool
	 * @param blEndorse
	 * @throws Exception
	 */
	private void saveCIEndorValid(DbPool dbPool, BLEndorse blEndorse, int type)
			throws SQLException, UserException, Exception 
	{
		if (type == 1)
		{
			
			type = 0;
		}else{
			type = 1;
		}

		
		
		
		String updateSQL = " UPDATE CIInsureValid SET Flag = SUBSTR(Flag, 1, 2) || '"
							+ type + "' || SUBSTR(Flag, 4, LENGTH(Flag))" + " WHERE PolicyNo = '"
							+ blEndorse.getBLPrpPmain().getArr(0).getPolicyNo() + "'";

		String updateSQL2 = " UPDATE CIEndorValid SET ValidNo = '" + blEndorse.getBLCIEndorValid().getArr(0).getValidNo().trim()
        					+ "'" + " WHERE EndorseNo = '"
        					+ blEndorse.getBLPrpPmain().getArr(0).getEndorseNo().trim() + "'";

		
		try
		{
			if(dbPool != null) 
			{
				
				dbPool.update(updateSQL);
				
				dbPool.update(updateSQL2);
			}
			else
			{
				throw new Exception("��дCIInsureValid��CIEndorValid��ʱdbPoolΪ�ա���������������Ա��ϵ�������ݣ�");
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
	
	
	/**
	 * ��XXXXXʱ����˰��prpccarshiptax,prpjplanfee�������ݵĸ���
	 * @param dbPool
	 * @param blEndorse
	 */
	private void saveCarship(DbPool dbPool, BLEndorse blEndorse, int type)
		throws SQLException, UserException, Exception{
		if(type == 1){
			return;
		}
		if(utiPower.checkCICarshipTaxBJ(blEndorse.getBLPrpPhead().getArr(0).getRiskCode(), blEndorse.getBLPrpPhead().getArr(0).getComCode())){
			double sumTaxLocal = 0.0d;
			double sumTaxCI = 0.0d;
			String policyno = blEndorse.getBLPrpPhead().getArr(0).getPolicyNo();
			BLPrpCcarshipTax blPrpCcarshipTax = new BLPrpCcarshipTax();
			BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
			blPrpCcarshipTax.getData(dbPool, policyno);
			if(blPrpCcarshipTax.getSize() > 0){
				sumTaxLocal = Double.parseDouble(blPrpCcarshipTax.getArr(0).getSumPayTax());
			}
			if(!cancelTax.equals(""))
				sumTaxCI = Double.parseDouble(cancelTax);
			if(sumTaxCI!=sumTaxLocal){
				throw new Exception("���ص�Ӧ�˳���˰���ƽ̨���ص�Ӧ�˳���˰���ȣ���������������Ա��ϵ�������ݣ�");
			}
			
	        
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if("1".equals(strSwitch)){
                DbPool dbpool1=new DbPool();
                try {
                    dbpool1.open("platformNewDataSource");
                    dbpool1.beginTransaction();
                    updateCicarshipTaxDemand(dbpool1, blEndorse);
                    dbpool1.commitTransaction();
                } catch (Exception e) {
                    dbpool1.rollbackTransaction();
                    e.printStackTrace();
                }finally{
                    dbpool1.close();
                }
            }else{
                updateCicarshipTaxDemand(dbPool, blEndorse);
            }
            
			
			insertPrpPcarshiptax(dbPool, blEndorse, blPrpCcarshipTax);
			
			updatePrpCpcarshiptax(dbPool, blEndorse, blPrpCcarshipTax);
			
			updatePrpCcarshiptax(dbPool, blEndorse);
			
			blPrpJplanFee.transData(dbPool, "T", blEndorse.getBLPrpPhead().getArr(0).getEndorseNo());
			
		}
		
	}
	
	/**
	 * ��дcicarshiptaxdemand��
	 * @param dbPool
	 * @param blEndorse
	 * @throws SQLException
	 * @throws UserException
	 * @throws Exception
	 */
	private void updateCicarshipTaxDemand(DbPool dbPool, BLEndorse blEndorse)
		throws SQLException, UserException, Exception{
		String updateSQL = "update cicarshiptaxdemand set TAXACTUAL = '0', PREVIOUSPAY = '0', LATEFEE = '0', SUMTAX = '0', FLAG='B' where policyno = '"
			+ blEndorse.getBLPrpPmain().getArr(0).getPolicyNo() + "'";
		try{
			if(dbPool != null) {
				
				dbPool.update(updateSQL);
				
			}
			else{
				throw new Exception("��дcicarshiptaxdemand��ʱdbPoolΪ�ա���������������Ա��ϵ�������ݣ�");
			}
		}catch(SQLException sqlex){
			throw new Exception("����SQL����" + sqlex.getMessage());
		}catch(Exception ex){
			throw ex;
		}
	}
	/**
	 * ����prppcarshiptax������
	 * @param dbPool
	 * @param blEndorse
	 */
	private void insertPrpPcarshiptax(DbPool dbPool, BLEndorse blEndorse, BLPrpCcarshipTax blPrpCcarshipTax)
		throws Exception{
		
		BLPrpPcarshipTax blPrpPcarshipTax = new BLPrpPcarshipTax();
		PrpCcarshipTaxSchema prpCcarshipTaxSchema = null;
		PrpPcarshipTaxSchema prpPcarshipTaxSchema = new PrpPcarshipTaxSchema();
		prpCcarshipTaxSchema = blPrpCcarshipTax.getArr(0);
		if(prpCcarshipTaxSchema==null){
			throw new Exception("û�г���˰������ݣ���������������Ա��ϵ��");
		}
		prpPcarshipTaxSchema.setEndorseNo(blEndorse.getBLPrpPhead().getArr(0).getEndorseNo());
		prpPcarshipTaxSchema.setPolicyNo(prpCcarshipTaxSchema.getPolicyNo());
		prpPcarshipTaxSchema.setSerialNo(prpCcarshipTaxSchema.getSerialNo());
		prpPcarshipTaxSchema.setRiskCode(prpCcarshipTaxSchema.getRiskCode());
		prpPcarshipTaxSchema.setAreaCode(prpCcarshipTaxSchema.getAreaCode());
		prpPcarshipTaxSchema.setLicenseNo(prpCcarshipTaxSchema.getLicenseNo());
		prpPcarshipTaxSchema.setLicenseType(prpCcarshipTaxSchema.getLicenseType());
		prpPcarshipTaxSchema.setCarKindCode(prpCcarshipTaxSchema.getCarKindCode());
		prpPcarshipTaxSchema.setTaxItemCode(prpCcarshipTaxSchema.getTaxItemCode());
		prpPcarshipTaxSchema.setTaxItemName(prpCcarshipTaxSchema.getTaxItemName());
		prpPcarshipTaxSchema.setTaxItemDetailCode(prpCcarshipTaxSchema.getTaxItemDetailCode());
		prpPcarshipTaxSchema.setTaxItemDetailName(prpCcarshipTaxSchema.getTaxItemDetailName());
		prpPcarshipTaxSchema.setBaseTaxation(prpCcarshipTaxSchema.getBaseTaxation());
		prpPcarshipTaxSchema.setTaxpayerCode(prpCcarshipTaxSchema.getTaxpayerCode());
		prpPcarshipTaxSchema.setTaxpayerName(prpCcarshipTaxSchema.getTaxpayerName());
		prpPcarshipTaxSchema.setIdentifyNumber(prpCcarshipTaxSchema.getIdentifyNumber());
		prpPcarshipTaxSchema.setTaxpayerIdentifier(prpCcarshipTaxSchema.getTaxpayerIdentifier());
		prpPcarshipTaxSchema.setTaxRelifFlag(prpCcarshipTaxSchema.getTaxRelifFlag());
		prpPcarshipTaxSchema.setRelifReason(prpCcarshipTaxSchema.getRelifReason());
		prpPcarshipTaxSchema.setFreeRate(prpCcarshipTaxSchema.getFreeRate());
		prpPcarshipTaxSchema.setFreeRateText(prpCcarshipTaxSchema.getFreeRateText());
		prpPcarshipTaxSchema.setTaxComCode(prpCcarshipTaxSchema.getTaxComCode());
		prpPcarshipTaxSchema.setTaxComName(prpCcarshipTaxSchema.getTaxComName());
		prpPcarshipTaxSchema.setPaidFreeCertificate(prpCcarshipTaxSchema.getPaidFreeCertificate());
		prpPcarshipTaxSchema.setTaxUnit(prpCcarshipTaxSchema.getTaxUnit());
		prpPcarshipTaxSchema.setTaxUnitText(prpCcarshipTaxSchema.getTaxUnitText());
		prpPcarshipTaxSchema.setCompleteKerbMass(prpCcarshipTaxSchema.getCompleteKerbMass());
		prpPcarshipTaxSchema.setCalculateMode(prpCcarshipTaxSchema.getCalculateMode());
		prpPcarshipTaxSchema.setCalculateFlag(prpCcarshipTaxSchema.getCalculateFlag());
		prpPcarshipTaxSchema.setPayLastYear(prpCcarshipTaxSchema.getPayLastYear());
        prpPcarshipTaxSchema.setHisPolicyEndDate(prpCcarshipTaxSchema.getHisPolicyEndDate());
        prpPcarshipTaxSchema.setTaxDue(prpCcarshipTaxSchema.getTaxDue());
        prpPcarshipTaxSchema.setTaxActual(prpCcarshipTaxSchema.getTaxActual());
        prpPcarshipTaxSchema.setTaxRelief(prpCcarshipTaxSchema.getTaxRelief());
        prpPcarshipTaxSchema.setPayStartDate(prpCcarshipTaxSchema.getPayStartDate());
        prpPcarshipTaxSchema.setPayEndDate(prpCcarshipTaxSchema.getPayEndDate());
        prpPcarshipTaxSchema.setPreviousPay(prpCcarshipTaxSchema.getPreviousPay());
        prpPcarshipTaxSchema.setLateFee(prpCcarshipTaxSchema.getLateFee());
        prpPcarshipTaxSchema.setLateFeeStartDate(prpCcarshipTaxSchema.getLateFeeStartDate());
        prpPcarshipTaxSchema.setLateFeeEndDate(prpCcarshipTaxSchema.getLateFeeEndDate());
        prpPcarshipTaxSchema.setSumPayTax(prpCcarshipTaxSchema.getSumPayTax());
        prpPcarshipTaxSchema.setLeviedDate(prpCcarshipTaxSchema.getLeviedDate());
        prpPcarshipTaxSchema.setPayTaxTimes(prpCcarshipTaxSchema.getPayTaxTimes());
        prpPcarshipTaxSchema.setFinalFlag(prpCcarshipTaxSchema.getFinalFlag());
        prpPcarshipTaxSchema.setExtendChar1(prpCcarshipTaxSchema.getExtendChar1());
        prpPcarshipTaxSchema.setExtendChar2(prpCcarshipTaxSchema.getExtendChar2());
        prpPcarshipTaxSchema.setExtendNum1(prpCcarshipTaxSchema.getExtendNum1());
        prpPcarshipTaxSchema.setExtendNum2(prpCcarshipTaxSchema.getExtendNum2());
        prpPcarshipTaxSchema.setExtendDate1(prpCcarshipTaxSchema.getExtendDate1());
        prpPcarshipTaxSchema.setExtendDate2(prpCcarshipTaxSchema.getExtendDate2());
        prpPcarshipTaxSchema.setFlag("B");
        prpPcarshipTaxSchema.setChgTaxActual("-" + prpCcarshipTaxSchema.getTaxActual());
        prpPcarshipTaxSchema.setChgPreviousPay("-" + prpCcarshipTaxSchema.getPreviousPay());
        prpPcarshipTaxSchema.setChgLateFee("-" + prpCcarshipTaxSchema.getLateFee());
        prpPcarshipTaxSchema.setChgSumPayTax("-" + prpCcarshipTaxSchema.getSumPayTax());
        prpPcarshipTaxSchema.setPayBalanceFee(prpCcarshipTaxSchema.getPayBalanceFee());
        prpPcarshipTaxSchema.setCalFeeFlag(prpCcarshipTaxSchema.getCalFeeFlag());
        prpPcarshipTaxSchema.setChgPayBalanceFee("0");
        prpPcarshipTaxSchema.setCertificateDate(prpCcarshipTaxSchema.getCertificateDate());
        prpPcarshipTaxSchema.setTaxFlag(prpCcarshipTaxSchema.getTaxFlag());
        
        blPrpPcarshipTax.setArr(prpPcarshipTaxSchema);
        blPrpPcarshipTax.save(dbPool);
	}
	
	/**
	 * ����prpcpcarshiptax������
	 * @param dbPool
	 * @param blEndorse
	 */
	private void updatePrpCpcarshiptax(DbPool dbPool, BLEndorse blEndorse, BLPrpCcarshipTax blPrpCcarshipTax)
		throws SQLException, UserException, Exception{
		String updateSQL = "update prpCpcarshipTax set taxactual = '0', previouspay = '0', latefee = '0', sumpaytax = '0', flag='B' where policyno = '"
						+ blEndorse.getBLPrpPmain().getArr(0).getPolicyNo() + "'";
		try
		{
			if(dbPool != null) 
			{
			
			dbPool.update(updateSQL);
			
			}
			else
			{
				throw new Exception("��дprpcpcarshiptax��ʱdbPoolΪ�ա���������������Ա��ϵ�������ݣ�");
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
	
	/**
	 * ����prpccarshiptax���taxactual,previouspay,latefee,sumpaytax�ֶ�
	 * @param dbPool
	 * @param blEndorse
	 */
	private void updatePrpCcarshiptax(DbPool dbPool, BLEndorse blEndorse)
		throws SQLException, UserException, Exception{
		String updateSQL = "update prpCcarshipTax set taxactual = '0', previouspay = '0', latefee = '0', sumpaytax = '0', flag='B' where policyno = '"
							+ blEndorse.getBLPrpPmain().getArr(0).getPolicyNo() + "'";
		try
		{
			if(dbPool != null) 
			{
				
				dbPool.update(updateSQL);
				
			}
			else
			{
				throw new Exception("��дprpccarshiptax��ʱdbPoolΪ�ա���������������Ա��ϵ�������ݣ�");
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
	
}
