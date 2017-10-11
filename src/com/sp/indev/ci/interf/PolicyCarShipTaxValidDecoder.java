package com.sp.indiv.ci.interf;

import java.sql.SQLException;

import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCICarShipTaxQGDemand;
import com.sp.indiv.ci.blsvr.BLCICarshipTaxDemand;
import com.sp.indiv.ci.schema.CICarShipTaxQGDemandSchema;
import com.sp.indiv.ci.schema.CICarshipTaxDemandSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.pubfun.PubTools;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;

public class PolicyCarShipTaxValidDecoder {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
	}

	public  void decode(BLPolicy blPolicy, Node node) throws Exception 
	{
        UtiPower utiPower = new UtiPower();
        if(utiPower.checkCICarshipTaxSH(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode()))
        {
			BLCICarshipTaxDemand blCICarshipTaxDemand = new BLCICarshipTaxDemand();
			blCICarshipTaxDemand.setArr(new CICarshipTaxDemandSchema());
			blCICarshipTaxDemand.getArr(0).setPolicyNo(blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
			blCICarshipTaxDemand.getArr(0).setTaxNo(XMLUtils.getChildNodeValue(node,"TAX_NO"));
			blPolicy.setBLCICarshipTaxDemand(blCICarshipTaxDemand);
        }else if(utiPower.checkCICarshipTaxBJ(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())){
        	BLCICarshipTaxDemand blCICarshipTaxDemand = new BLCICarshipTaxDemand();
			blCICarshipTaxDemand.setArr(new CICarshipTaxDemandSchema());
			blCICarshipTaxDemand.getArr(0).setPolicyNo(blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
			
			String licenseNo = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseNo().trim();
			PubTools pubTools = new PubTools();
			String strEcdemicVehicleFlag = pubTools.checkEcdemicVehicleFlag(licenseNo);
			String payNo = XMLUtils.getChildNodeValue(node,"PAY_NO");
			if("1".equals(strEcdemicVehicleFlag)){
				if(payNo!=null && !"".equals(payNo) && payNo.charAt(0) == 'M' && blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxRelifFlag().equals("3")){
					payNo = payNo.substring(1);
				}
			}
			
			blCICarshipTaxDemand.getArr(0).setPaidCertificate(payNo);
			blPolicy.setBLCICarshipTaxDemand(blCICarshipTaxDemand);
			
        }else if(utiPower.checkCICarShipTaxQG(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())
        		||utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
            BLCICarShipTaxQGDemand blCICarshipTaxQGDemand = new BLCICarShipTaxQGDemand();
            blCICarshipTaxQGDemand.setArr(new CICarShipTaxQGDemandSchema());
            blCICarshipTaxQGDemand.getArr(0).setPolicyNo(blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
            blCICarshipTaxQGDemand.getArr(0).setTaxPrintNo(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxPrintNo());
            blPolicy.setBLCICarShipTaxQGDemand(blCICarshipTaxQGDemand);
          
        }
	}
        
	
	public void updateCicarshiptaxdemand(DbPool dbPool, BLPolicy blPolicy) throws Exception
	{
        UtiPower utiPower = new UtiPower();
        if(utiPower.checkCICarshipTaxSH(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())) {
    		String updateSQL = " UPDATE CICarshipTaxDemand SET PolicyNo = '"
    			+ blPolicy.getBLCICarshipTaxDemand().getArr(0).getPolicyNo() + "'"
    			+ ",TaxNo = '" + blPolicy.getBLCICarshipTaxDemand().getArr(0).getTaxNo()
    			+ "' WHERE DemandNo = '"
    			+ blPolicy.getBLCIInsureValid().getArr(0).getDemandNo() + "'";        	
        	
			try
			{
				if(dbPool != null) 
				{
					
					
					if ("1".equals(SysConfig.getProperty("DB_SPLIT_SWITCH"))) {
						DbPool dbpoolNew = new DbPool();
						try{
							dbpoolNew.open("platformNewDataSource");
							dbpoolNew.beginTransaction();
							dbpoolNew.update(updateSQL);
							dbpoolNew.commitTransaction();
						}catch(Exception e){
							dbpoolNew.rollbackTransaction();
							e.printStackTrace();
							throw e;
						}finally{
							dbpoolNew.close();
						}					
					} else {
						dbPool.update(updateSQL);
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
	   }else if(utiPower.checkCICarshipTaxBJ(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())) {
   		String updateSQL = " UPDATE CICarshipTaxDemand SET PolicyNo = '"
			+ blPolicy.getBLCICarshipTaxDemand().getArr(0).getPolicyNo() + "'"
			+ ",PaidCertificate = '" + blPolicy.getBLCICarshipTaxDemand().getArr(0).getPaidCertificate()
			+ "' WHERE DemandNo = '"
			+ blPolicy.getBLCIInsureValid().getArr(0).getDemandNo() + "'";
		String updateSQL1 = "UPDATE prpCcarshipTax set paidfreecertificate = '" + blPolicy.getBLCICarshipTaxDemand().getArr(0).getPaidCertificate() + "'"	
							+ " where policyno = '" + blPolicy.getBLCICarshipTaxDemand().getArr(0).getPolicyNo() + "'";
		try
		{
			if(dbPool != null) 
			{
				
				
				if ("1".equals(SysConfig.getProperty("DB_SPLIT_SWITCH"))) {
					DbPool dbpoolNew = new DbPool();
					try{
						dbpoolNew.open("platformNewDataSource");
						dbpoolNew.beginTransaction();
						dbpoolNew.update(updateSQL);
						dbpoolNew.commitTransaction();
					}catch(Exception e){
						dbpoolNew.rollbackTransaction();
						e.printStackTrace();
						throw e;
					}finally{
						dbpoolNew.close();
					}					
				} else {
					dbPool.update(updateSQL);
				}
				
				
				dbPool.update(updateSQL1);
			}
			else
			{
				throw new Exception("回写CICarshipTaxDemand表或prpccarshiptax表时dbPool为空。请务必马上与管理员联系处理数据！");
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
	   else if(utiPower.checkCICarShipTaxQG(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())
			   ||utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(),blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
	     




		   String updateSQL = " UPDATE CICarShipTaxQGDemand SET PolicyNo = '"
			+ blPolicy.getBLCICarShipTaxQGDemand().getArr(0).getPolicyNo() + "'"
			+ ", TaxPrintNo = '"
			+ blPolicy.getBLCICarShipTaxQGDemand().getArr(0).getTaxPrintNo()+"'"
			+ " WHERE DemandNo = '"
			+ blPolicy.getBLCIInsureValid().getArr(0).getDemandNo() + "'";
	     
		   try
			{
				if(dbPool != null) 
				{
					
					
					if ("1".equals(SysConfig.getProperty("DB_SPLIT_SWITCH"))) {
						DbPool dbpoolNew = new DbPool();
						try{
							dbpoolNew.open("platformNewDataSource");
							dbpoolNew.beginTransaction();
							dbpoolNew.update(updateSQL);
							dbpoolNew.commitTransaction();
						}catch(Exception e){
							dbpoolNew.rollbackTransaction();
							e.printStackTrace();
							throw e;
						}finally{
							dbpoolNew.close();
						}					
					} else {
						dbPool.update(updateSQL);
					}
					
				}
				else
				{
					throw new Exception("回写CICarshipTaxDemand表时dbPool为空。请务必马上与管理员联系处理数据！");
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
        
	}
	
	public void updateCicarshiptaxdemandNew(DbPool dbPool, BLPolicy blPolicy) throws Exception
	{
        UtiPower utiPower = new UtiPower();
        if(utiPower.checkCICarshipTaxSH(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())) {
    		String updateSQL = " UPDATE CICarshipTaxDemand SET PolicyNo = '"
    			+ blPolicy.getBLCICarshipTaxDemand().getArr(0).getPolicyNo() + "'"
    			+ ",TaxNo = '" + blPolicy.getBLCICarshipTaxDemand().getArr(0).getTaxNo()
    			+ "' WHERE DemandNo = '"
    			+ blPolicy.getBLCIInsureValid().getArr(0).getDemandNo() + "'";        	
        	
			try
			{
				if(dbPool != null) 
				{
					dbPool.update(updateSQL);
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
	   }else if(utiPower.checkCICarshipTaxBJ(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())) {
   		String updateSQL = " UPDATE CICarshipTaxDemand SET PolicyNo = '"
			+ blPolicy.getBLCICarshipTaxDemand().getArr(0).getPolicyNo() + "'"
			+ ",PaidCertificate = '" + blPolicy.getBLCICarshipTaxDemand().getArr(0).getPaidCertificate()
			+ "' WHERE DemandNo = '"
			+ blPolicy.getBLCIInsureValid().getArr(0).getDemandNo() + "'";
		try
		{
			if(dbPool != null) 
			{
				dbPool.update(updateSQL);
			}
			else
			{
				throw new Exception("回写CICarshipTaxDemand表或prpccarshiptax表时dbPool为空。请务必马上与管理员联系处理数据！");
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
	   else if(utiPower.checkCICarShipTaxQG(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())
			   ||utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(),blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
	       
	       String updateSQL = " UPDATE CICarShipTaxQGDemand SET PolicyNo = '"
			+ blPolicy.getBLCICarShipTaxQGDemand().getArr(0).getPolicyNo() + "'"
			+ ", TaxPrintNo = '"
			+ blPolicy.getBLCICarShipTaxQGDemand().getArr(0).getTaxPrintNo()+"'"
			+ " WHERE DemandNo = '"
			+ blPolicy.getBLCIInsureValid().getArr(0).getDemandNo() + "'";




	       
		   try
			{
				if(dbPool != null) 
				{
					dbPool.update(updateSQL);
				}
				else
				{
					throw new Exception("回写CICarshipTaxDemand表时dbPool为空。请务必马上与管理员联系处理数据！");
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
	}
	public void updateCicarshiptaxdemandNewToSave(DbPool dbPool, BLPolicy blPolicy) throws Exception
	{
		UtiPower utiPower = new UtiPower();
		if(utiPower.checkCICarshipTaxBJ(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())) {
			String updateSQL1 = "UPDATE prpCcarshipTax set paidfreecertificate = '" + blPolicy.getBLCICarshipTaxDemand().getArr(0).getPaidCertificate() + "'"	
			+ " where policyno = '" + blPolicy.getBLCICarshipTaxDemand().getArr(0).getPolicyNo() + "'";
			try{
				if(dbPool != null) {
					dbPool.update(updateSQL1);
				}
			}
			catch(SQLException sqlex){
				throw new Exception("操作SQL错误。" + sqlex.getMessage());
			}
			catch(Exception ex){
				throw ex;
			}
		}
	}
	
}
