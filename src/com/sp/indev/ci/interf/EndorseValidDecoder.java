package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCICheckCarShipTaxDetailQG;
import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.dbsvr.DBCIEndorValid;
import com.sp.indiv.ci.dbsvr.DBCIInsureValid;
import com.sp.indiv.ci.schema.CICheckCarShipTaxDetailQGSchema;
import com.sp.indiv.ci.schema.CIEndorValidSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPrpCPcarshipTax;
import com.sp.prpall.blsvr.cb.BLPrpCPitemCar;
import com.sp.prpall.blsvr.cb.BLPrpCcarshipTax;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 发送批改确认返回数据的解码器
 * 
 */
public class EndorseValidDecoder {

    public static void main(String[] args) throws Exception {
    }
    
    
    private static Logger logger = Logger.getLogger(EndorseValidDecoder.class);
    
	
    /**
     * 解码
     * 
     * @return XX查询请求XML格式串
     * @throws Exception
     */
    public void decode(DbPool dbPool, 
                       BLEndorse blEndorse, 
                       String content)
        throws SQLException, UserException, Exception 
    {
    	
        logger.info("[批改确认返回报文]:"+content);
        
        
        InputStream in    = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);

        
        int type = -1;
        try
        {
            type = processHead(blEndorse, XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
            if(type == 1)
            {
                processBody(blEndorse, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
            } 
            
            /*else if(type == 0)
            {
                
                blEndorse.getBLCIEndorValid().getArr(0).setValidNo(blEndorse.getBLCIEndorValid().getArr(0).getEndorseNo());
                
                blEndorse.getBLCIEndorValid().getArr(0).setValidStatus("1");
            }*/
            
        }
        catch(Exception ex1)
        {
            throw new Exception("解析平台返回串错误。批单号：" + blEndorse.getBLPrpPmain().getArr(0).getEndorseNo() 
                                + "，请与管理员联系！" + ex1.getMessage());
        }
        
        if(type != 1)
        {
            throw new Exception(blEndorse.getBLCIEndorValid().getArr(0).getErrorMessage());
        }
        

        
        try
        {
            updateCIEndorValid(dbPool, blEndorse);
            
            
            if(new UtiPower().checkCarShipTaxJZ(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
            updateTaxPrintNo(dbPool, blEndorse);
            updateCICheckCarshipTaxDetailqg(dbPool, blEndorse);
            }
            
            
        }
        catch(Exception ex2)
        {
            throw new Exception("与平台交互成功。但" + ex2.getMessage() + " 批单号：" + blEndorse.getBLPrpPmain().getArr(0).getEndorseNo());
        }
    }

    /**
     * 处理HEAD节
     * 
     * @param node
     *            Node
     * @throws Exception
     */
    
    /*protected int processHead(Node node) throws Exception {
        
        
        
        
        

        
        
        int type = 1;
        

        String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
        if (!responseCode.equals("1")) 
        {
            type = 0;
        }
        return type;
    }*/
    
    
    protected int processHead(BLEndorse blEndorse, Node node) throws Exception 
    {
        
        int type = 1;
        String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");    
        /*modi by liujia start
          reason:将原来两句代码移出来，因为平台重复XX开关为开时发生重复XX交易返回正常
        */
        String errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");    
        blEndorse.getBLCIEndorValid().getArr(0).setErrorMessage(errorMessage);
        /*modi by liujia end*/
        if (!responseCode.equals("1")) {
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
    protected void processBody(BLEndorse blEndorse, Node node) throws Exception {
        processBasePart(blEndorse, XMLUtils.getChildNodeByTagName(node, "BASE_PART"));
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
        
        
        
        
        /**<<<<<< added by harry on 22/08/07 新增重复XX标志**/
        String strReInsureFlag    = "";   
        /**added by harry on 22/08/07 新增重复XX标志 >>>>>>**/
        
        
        String amendConfirmNo = "";
        
        amendConfirmNo = XMLUtils.getChildNodeValue(node, "AMEND_CONFIRM_NO");
        
        String strTaxPrintNo = "";
        
        
        /**<<<<<< added by liujia on 22/08/07 新增重复XX标志**/
        if(blEndorse.getBLPrpPmain().getArr(0).getComCode().substring(0, 2).trim().equals("01") ||
        		blEndorse.getBLPrpPmain().getArr(0).getComCode().substring(0, 2).trim().equals("07"))
		{
					
		}else{
			String tempComCode=blEndorse.getBLPrpPmain().getArr(0).getComCode().substring(0, 2).trim();
			if(SysConfig.getProperty("EXPERIMENTALCOM").trim().indexOf(tempComCode.substring(0,2))>-1){
				    strReInsureFlag= XMLUtils.getChildNodeValue(node, "REINSURE_FLAG");
		        blEndorse.getBLCIEndorValid().getArr(0).setAReinsureFlag(strReInsureFlag);
		        
		        
		        /*if (strReInsureFlag!=null&&!strReInsureFlag.equals("0")) {
		        	 throw new Exception(blEndorse.getBLCIEndorValid().getArr(0).getErrorMessage());
		        }*/
		        
		      
				if(new UtiPower().checkCarShipTaxJZ(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
					strTaxPrintNo = XMLUtils.getChildNodeValue(node,"TAX_PRINT_NO");
					blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).setTaxPrintNo(strTaxPrintNo);
				}
				
	        }
		}
        /**added by liujia on 22/08/07 新增重复XX标志 >>>>>>**/
        
        blEndorse.getBLCIEndorValid().getArr(0).setValidNo(amendConfirmNo);
        
        blEndorse.getBLCIEndorValid().getArr(0).setValidStatus("0");
    }

    /**
     * 回写批改确认表的确认码
     * 
     * @param dbPool
     * @param blpolicy
     * @throws Exception
     */
    private void updateCIEndorValid(DbPool dbPool, BLEndorse blEndorse)
            throws SQLException, UserException, Exception 
    {
        String validNo = blEndorse.getBLCIEndorValid().getArr(0).getValidNo();
        String validStatus = blEndorse.getBLCIEndorValid().getArr(0).getValidStatus();
        String demandNo = blEndorse.getBLCIEndorValid().getArr(0).getDemandNo();
        String areinsureFlag = blEndorse.getBLCIEndorValid().getArr(0).getAReinsureFlag();
        
        
        String updateSQL = " UPDATE CIEndorValid SET ValidNo = '" + validNo + "' , areinsureFlag='" + areinsureFlag + "' , " 
                           + " ValidStatus = '" + validStatus 
                           + "' WHERE DemandNo = '" + demandNo + "'";
        
        String updateSQL1 = "";
        if(new UtiPower().checkCarShipTaxJZ(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
        updateSQL1 = " UPDATE CICarShipTaxQGEndorse SET TaxPrintNo = '"
			+ blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxPrintNo() + "'"
			+ " WHERE EndorseNo = '"
			+ blEndorse.getBLPrpPmain().getArr(0).getEndorseNo() + "'";
        }
        
        
        try
        {
            if(dbPool != null) 
            {
                
                dbPool.update(updateSQL);
                
                if(new UtiPower().checkCarShipTaxJZ(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
                	dbPool.update(updateSQL1);
                }
                
            }
            else
            {
                throw new Exception("回写CIInsureValid表时dbPool为空。请务必马上与管理员联系处理数据！");
            }
        }
        catch(SQLException sqlex)
        {
            throw new Exception("操作SQL错误。" + sqlex.getMessage());
        }
        catch(Exception ex)
        {
            throw ex;
        }
        
    }
	
	
	private void updateTaxPrintNo(DbPool dbPool, BLEndorse blEndorse)
			throws SQLException, UserException, Exception {
		String strTaxPrintNo = "";
		if("".equals(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxPrintNo())){
			BLPrpCcarshipTax blPrpCcarshipTax = new BLPrpCcarshipTax();
			blPrpCcarshipTax.getData(blEndorse.getBLPrpPmain().getArr(0).getPolicyNo());
			strTaxPrintNo = blPrpCcarshipTax.getArr(0).getTaxPrintNo();
        }else{
        	strTaxPrintNo = blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxPrintNo();
        }
		
		String updateSQL = " UPDATE PrpCpCarShiptax SET TaxPrintNo = '"
				+ strTaxPrintNo + "'"
				+ " WHERE PolicyNo = '"
				+ blEndorse.getBLPrpPmain().getArr(0).getPolicyNo() + "'";
		
		String updateSQL1 = " UPDATE PrpCCarShiptax SET TaxPrintNo = '"
			+ strTaxPrintNo + "'"
			+ " WHERE PolicyNo = '"
			+ blEndorse.getBLPrpPmain().getArr(0).getPolicyNo() + "'";
		
		try {
			if (dbPool != null) {
				dbPool.update(updateSQL);
				
				dbPool.update(updateSQL1);
				
			} else {
				throw new Exception(
						"回写CIInsureValid表时dbPool为空。请务必马上与管理员联系处理数据！");
			}
		} catch (SQLException sqlex) {
			throw new Exception("操作SQL错误。" + sqlex.getMessage());
		} catch (Exception ex) {
			throw ex;
		}
	}
	
	
	
	private void updateCICheckCarshipTaxDetailqg(DbPool dbPool, BLEndorse blEndorse)
			throws SQLException, UserException, Exception {
		BLPrpCPcarshipTax blPrpCPcarshipTax = new BLPrpCPcarshipTax();
		blPrpCPcarshipTax.getData(blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
		CICheckCarShipTaxDetailQGSchema ciCheckCarShipTaxDetailQGSchema = new CICheckCarShipTaxDetailQGSchema();
		ciCheckCarShipTaxDetailQGSchema.setCheckNo("1");
		ciCheckCarShipTaxDetailQGSchema.setConFirmSequenceNo(blEndorse.getBLCIEndorValid().getArr(0).getValidNo());
		ciCheckCarShipTaxDetailQGSchema.setType("E");
		ciCheckCarShipTaxDetailQGSchema.setProposalNo(blEndorse.getBLPrpPmain().getArr(0).getProposalNo());
		ciCheckCarShipTaxDetailQGSchema.setCretiNo(blEndorse.getBLPrpPhead().getArr(0).getEndorseNo());
		ciCheckCarShipTaxDetailQGSchema.setSerialNo(blPrpCPcarshipTax.getArr(0).getSerialNo());
		ciCheckCarShipTaxDetailQGSchema.setTaxDeclareQueryNo("1");
		ciCheckCarShipTaxDetailQGSchema.setTaxConditionCode(blPrpCPcarshipTax.getArr(0).getTaxRelifFlag());
		ciCheckCarShipTaxDetailQGSchema.setMTaxAnnualTaxDue(blPrpCPcarshipTax.getArr(0).getTaxActual());
		ciCheckCarShipTaxDetailQGSchema.setMTaxSumTaxDeFault(blPrpCPcarshipTax.getArr(0).getPreviousPay());
		ciCheckCarShipTaxDetailQGSchema.setMTaxSumOverDue(blPrpCPcarshipTax.getArr(0).getLateFee());
		ciCheckCarShipTaxDetailQGSchema.setMTaxSumTax(blPrpCPcarshipTax.getArr(0).getSumPayTax());
		ciCheckCarShipTaxDetailQGSchema.setSignDate(blEndorse.getBLPrpPmain().getArr(0).getOperateDate());
		if(blEndorse.getBLPrpPitemCar().getSize()>0){
			ciCheckCarShipTaxDetailQGSchema.setLicenseNo(blEndorse.getBLPrpPitemCar().getArr(0).getLicenseNo());
			ciCheckCarShipTaxDetailQGSchema.setFrameNo(blEndorse.getBLPrpPitemCar().getArr(0).getFrameNo());
			ciCheckCarShipTaxDetailQGSchema.setEngineNo(blEndorse.getBLPrpPitemCar().getArr(0).getEngineNo());
		}else{
			BLPrpCPitemCar blPrpCPitemCar = new BLPrpCPitemCar();
			blPrpCPitemCar.getData(blEndorse.getBLPrpPhead().getArr(0).getPolicyNo());
			ciCheckCarShipTaxDetailQGSchema.setLicenseNo(blPrpCPitemCar.getArr(0).getLicenseNo());
			ciCheckCarShipTaxDetailQGSchema.setFrameNo(blPrpCPitemCar.getArr(0).getFrameNo());
			ciCheckCarShipTaxDetailQGSchema.setEngineNo(blPrpCPitemCar.getArr(0).getEngineNo());
		}
		ciCheckCarShipTaxDetailQGSchema.setStatus("0");
		ciCheckCarShipTaxDetailQGSchema.setCheckSuccessFlag("0");
		ciCheckCarShipTaxDetailQGSchema.setComCode(blEndorse.getBLPrpPmain().getArr(0).getComCode());
		BLCICheckCarShipTaxDetailQG blciCheckCarShipTaxDetailQG = new BLCICheckCarShipTaxDetailQG();
		try {
			blciCheckCarShipTaxDetailQG.setArr(ciCheckCarShipTaxDetailQGSchema);
			blciCheckCarShipTaxDetailQG.save(dbPool);
		} catch (SQLException sqlex) {
			throw new Exception("操作SQL错误。" + sqlex.getMessage());
		} catch (Exception ex) {
			throw ex;
		}
	}
	
}