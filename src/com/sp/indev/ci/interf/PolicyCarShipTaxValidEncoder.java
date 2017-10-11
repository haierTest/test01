package com.sp.indiv.ci.interf;

import com.sp.indiv.ci.blsvr.BLCICarShipTaxQGDemand;
import com.sp.platform.bl.facade.BLPrpDcompanyFacade;
import com.sp.platform.dto.domain.PrpDcompanyDto;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.pubfun.PubTools;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.utility.StringConvertor;
import com.sp.utility.UtiPower;
import com.sp.utility.SysConfig;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;
import com.sp.sysframework.reference.AppConfig;

/**
 * 车船税XX确认编码器
 * @author 刘义楷
 *
 */
public class PolicyCarShipTaxValidEncoder {
	
	public void encode(BLPolicy blPolicy, StringBuffer buf, String part) throws Exception 
    {
        if("BASE_PART".equals(part)){
        	encode(blPolicy,buf);
        }else if("VehicleTaxation".equals(part)){
            UtiPower utiPower = new UtiPower();
			
            if(utiPower.checkCICarShipTaxQG(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())
			    ||new UtiPower().checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
            
        	  encodeVehicleTaxation(blPolicy,buf);
            }
        }
    }
	
	
    public void encode(BLPolicy blPolicy, StringBuffer buf) throws Exception 
    {
        
        UtiPower utiPower = new UtiPower();
        if(utiPower.checkCICarshipTaxBJ(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode()))
        {
        	addCarShipTaxBJ(buf,blPolicy);
        }else if(utiPower.checkCICarShipTaxQG(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())){
        	addCarShipTaxQG(buf,blPolicy);
        }
    }
	
    
    protected void addCarShipTaxBJ(StringBuffer buf, BLPolicy blPolicy) throws Exception{
		
		String payTaxWay = "";
		String licenseNo = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseNo().trim().substring(0,1);
		
		String iLicenseNo = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseNo().trim();
		PubTools pubTools = new PubTools();
		String strEcdemicVehicleFlag = pubTools.checkEcdemicVehicleFlag(iLicenseNo);
		
		
		String currentDate=new ChgDate().getCurrentTime("yyyy-MM-dd");
		
		if(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxRelifFlag().equals("0")){
			payTaxWay = "02";
			addNode(buf, "ACTUAL_TAX_FEE", blPolicy.getBLPrpCcarshipTax().getArr(0).getSumPayTax());
		}
		else if(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxRelifFlag().equals("1")){
			payTaxWay = "01";
			addNode(buf, "ACTUAL_TAX_FEE", "0");
		}else if(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxRelifFlag().equals("2")){
			payTaxWay = "03";
			addNode(buf, "ACTUAL_TAX_FEE", "0");
			
			if(new UtiPower().checkCarShipTaxBJ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), currentDate)){
				
				if("1".equals(strEcdemicVehicleFlag)){
				
	    			
	    			if((null!=blPolicy.getBLPrpCcarshipTax().getArr(0).getPaidFreeCertificate())&&
	    			   (!"".equals(blPolicy.getBLPrpCcarshipTax().getArr(0).getPaidFreeCertificate())))
	    			  {
	        			addNode(buf, "PAY_NO", "M" + blPolicy.getBLPrpCcarshipTax().getArr(0).getPaidFreeCertificate());  
	    			  }	else{
	    				addNode(buf, "PAY_NO", "");
	    			  }
					
					addNode(buf, "DEPARTMENT_NONLOCAL", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxComCode());
				}else{
					addNode(buf, "PAY_NO", "");
					addNode(buf, "DEPARTMENT_NONLOCAL", "");
				}
			}else{
				addNode(buf, "PAY_NO", blPolicy.getBLPrpCcarshipTax().getArr(0).getPaidFreeCertificate());
				addNode(buf, "DEPARTMENT", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxComName());
			}
			
		}else if(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxRelifFlag().equals("3")){
			payTaxWay = "03";
			addNode(buf, "ACTUAL_TAX_FEE", "0");
			
			if("1".equals(strEcdemicVehicleFlag)){
			
            
            if((null!=blPolicy.getBLPrpCcarshipTax().getArr(0).getPaidFreeCertificate())&&
                 (!"".equals(blPolicy.getBLPrpCcarshipTax().getArr(0).getPaidFreeCertificate())))
            {
	          addNode(buf, "PAY_NO", "M" + blPolicy.getBLPrpCcarshipTax().getArr(0).getPaidFreeCertificate());  
            }else{
	          addNode(buf, "PAY_NO", "");
            }
            
			}else{
				if(!new UtiPower().checkCarShipTaxBJ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), currentDate)){
					addNode(buf, "PAY_NO", blPolicy.getBLPrpCcarshipTax().getArr(0).getPaidFreeCertificate());
				}else{
					addNode(buf, "PAY_NO", "");
				}
			}
			
			if(new UtiPower().checkCarShipTaxBJ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), currentDate)){
				
				if("1".equals(strEcdemicVehicleFlag)){
				
					addNode(buf, "DEPARTMENT_NONLOCAL", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxComCode());
				}else{
					addNode(buf, "DEPARTMENT_NONLOCAL", "");
				}
			}else{
				addNode(buf, "DEPARTMENT", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxComName());
			}
			
		
		}else if(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxRelifFlag().equals("4")){
			payTaxWay = "01";  
			addNode(buf, "ACTUAL_TAX_FEE", "0");
		}else if(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxRelifFlag().equals("5")){
			payTaxWay = "03";  
			addNode(buf, "ACTUAL_TAX_FEE", "0");
		}
		
		addNode(buf, "PAY_TAX_WAY", payTaxWay);
		addNode(buf, "COMPUTER_CODE",AppConfig.get("sysconst.CI_INSURED_01_COMPUTER_CODE"));
	}

    protected void addCarShipTaxQG(StringBuffer buf, BLPolicy blPolicy) throws Exception{
    	
        
    	
    }
    
    protected void encodeVehicleTaxation(BLPolicy blPolicy, StringBuffer buf ) throws Exception{
    	String TaxRelifFlag = blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxRelifFlag();
        BLPrpDcompanyFacade dcompany  = new BLPrpDcompanyFacade();
        PrpDcompanyDto prpDcompanyDto = dcompany.findByPrimaryKey(blPolicy.getBLPrpCmain().getArr(0).getComCode());
        
        if(new UtiPower().isCarShipTaxNB(blPolicy.getBLPrpCmain().getArr(0).getComCode())){
        	buf.append("<VehicleTaxation_NB>");
        }else{
        	buf.append("<VehicleTaxation>");
        }
        
    	addNode(buf, "TaxTermTypeCode", "08");
    	addNode(buf, "TaxConditionCode", taxRelifFlagEncoder(TaxRelifFlag));
    	addNode(buf, "TaxRegistryNumber",prpDcompanyDto.getTaxRegisterNo());
    	addNode(buf, "TaxPayerName", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxpayerName());
    	addNode(buf, "TaxPayerIdentificationCode", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxpayerIdentifier());
    	
    	UtiPower utiPower = new UtiPower();
        BLCICarShipTaxQGDemand blCICarShipTaxQGDemand = new BLCICarShipTaxQGDemand();
        String strTaxLocationCode ="";
		String strTaxUnitTypeCode ="";
		
		String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
		if(!"1".equals(strSwitch)){
			blCICarShipTaxQGDemand.getDataByProposalNo(blPolicy.getBLPrpCmain().getArr(0).getProposalNo());
		}else{
			blCICarShipTaxQGDemand.getData(blPolicy.getBLPrpCitemCar().getArr(0).getDemandNo());
		}
		
		
		double TotalAmount = 0;
		
		if(blCICarShipTaxQGDemand.getSize()>0){
			if(blCICarShipTaxQGDemand.getArr(0).getTaxLocationCode()!=null){
			  strTaxLocationCode= blCICarShipTaxQGDemand.getArr(0).getTaxLocationCode();
			}
			if(blCICarShipTaxQGDemand.getArr(0).getTaxUnitTypeCode()!=null){
			  strTaxUnitTypeCode= blCICarShipTaxQGDemand.getArr(0).getTaxUnitTypeCode();
			}
			
			if(utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
				
				if("08".equals(blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2))&&"2".equals(TaxRelifFlag)){
					TotalAmount=Double.parseDouble(ChgData.chgStrZero(blCICarShipTaxQGDemand.getArr(0).getTotalAmount()));
				}else {
					TotalAmount = Double.parseDouble(ChgData.chgStrZero(blCICarShipTaxQGDemand.getArr(0).getTaxDue()))
		              +Double.parseDouble(ChgData.chgStrZero(blCICarShipTaxQGDemand.getArr(0).getOverDue()));
				}
				
			}
			
		}
    	if("1".equals(TaxRelifFlag) ){
        	if(utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
        		buf.append("<CurrentTaxDue>");
        		addNode(buf, "TaxLocationCode",strTaxLocationCode);
        		addNode(buf, "TaxUnitTypeCode",strTaxUnitTypeCode);
        		addNode(buf, "TaxStartDate",correctDate(blPolicy.getBLPrpCcarshipTax().getArr(0).getPayStartDate()));
        		addNode(buf, "TaxEndDate",correctDate(blPolicy.getBLPrpCcarshipTax().getArr(0).getPayEndDate()));
        		
        		addNode(buf, "UnitRate",blCICarShipTaxQGDemand.getArr(0).getUnitRate());
        		addNode(buf, "AnnualTaxAmount",blCICarShipTaxQGDemand.getArr(0).getAnnualTaxAmount());
        		addNode(buf, "TaxDue",blCICarShipTaxQGDemand.getArr(0).getTaxDue());
        		addNode(buf, "OverDue",blCICarShipTaxQGDemand.getArr(0).getOverDue());
        		addNode(buf, "TotalAmount",""+TotalAmount);
        		
        		buf.append("</CurrentTaxDue>");
        	}
        	
        }else if("2".equals(TaxRelifFlag)){
            buf.append("<CurrentTaxDue>");
            
        	if(utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
        		addNode(buf, "TaxLocationCode",strTaxLocationCode);
        		addNode(buf, "TaxUnitTypeCode",strTaxUnitTypeCode);
        		addNode(buf, "TaxStartDate",correctDate(blPolicy.getBLPrpCcarshipTax().getArr(0).getPayStartDate()));
        		addNode(buf, "TaxEndDate",correctDate(blPolicy.getBLPrpCcarshipTax().getArr(0).getPayEndDate()));
        		
        		addNode(buf, "UnitRate",blCICarShipTaxQGDemand.getArr(0).getUnitRate());
        		addNode(buf, "AnnualTaxAmount",blCICarShipTaxQGDemand.getArr(0).getAnnualTaxAmount());
        		addNode(buf, "TaxDue",blCICarShipTaxQGDemand.getArr(0).getTaxDue());
        		addNode(buf, "OverDue",blCICarShipTaxQGDemand.getArr(0).getOverDue());
        		addNode(buf, "TotalAmount",""+TotalAmount);
        		
        	}
        	
            buf.append("<Derate>");
            addNode(buf, "DeductionDueCode", relifReasonEncoder(blPolicy.getBLPrpCcarshipTax().getArr(0).getRelifReason()));
            addNode(buf, "DeductionDueType", baseTaxationEncoder(blPolicy.getBLPrpCcarshipTax().getArr(0).getBaseTaxation()));
            
            if(Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCcarshipTax().getArr(0).getFreeRate()))/100==0){
            	addNode(buf, "DeductionDueProportion", "");
            }else{
            	addNode(buf, "DeductionDueProportion", ""+(Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCcarshipTax().getArr(0).getFreeRate()))/100));
            }
            if(Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxRelief()))==0){
            	addNode(buf, "Deduction", "");
            }else{
            	addNode(buf, "Deduction", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxRelief());
            }
            
            addNode(buf, "DeductionDocumentNumber", blPolicy.getBLPrpCcarshipTax().getArr(0).getPaidFreeCertificate());
            addNode(buf, "TaxDepartmentCode", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxComCode());
            addNode(buf, "TaxDepartment", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxComName());
            buf.append("</Derate>");
            buf.append("</CurrentTaxDue>");
    	}else if("3".equals(TaxRelifFlag)){
            
            buf.append("<CurrentTaxDue>");
            
        	if(utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
        		addNode(buf, "TaxLocationCode",strTaxLocationCode);
        		addNode(buf, "TaxUnitTypeCode",strTaxUnitTypeCode);
        		addNode(buf, "TaxStartDate",correctDate(blPolicy.getBLPrpCcarshipTax().getArr(0).getPayStartDate()));
        		addNode(buf, "TaxEndDate",correctDate(blPolicy.getBLPrpCcarshipTax().getArr(0).getPayEndDate()));
        		
        		addNode(buf, "UnitRate",blCICarShipTaxQGDemand.getArr(0).getUnitRate());
        		addNode(buf, "AnnualTaxAmount",blCICarShipTaxQGDemand.getArr(0).getAnnualTaxAmount());
        		addNode(buf, "TaxDue",blCICarShipTaxQGDemand.getArr(0).getTaxDue());
        		addNode(buf, "OverDue",blCICarShipTaxQGDemand.getArr(0).getOverDue());
        		addNode(buf, "TotalAmount",""+TotalAmount);
        		
        	}
            if(!new UtiPower().checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
            	addNode(buf, "DeclareDate", correctDate(blPolicy.getBLPrpCmain().getArr(0).getOperateDate()));
            }
			
            buf.append("<Derate>");
            addNode(buf, "DeductionDueCode", relifReasonEncoder(blPolicy.getBLPrpCcarshipTax().getArr(0).getRelifReason()));
            addNode(buf, "DeductionDueType", baseTaxationEncoder(blPolicy.getBLPrpCcarshipTax().getArr(0).getBaseTaxation()));
            
            if("A".equals(baseTaxationEncoder(blPolicy.getBLPrpCcarshipTax().getArr(0).getBaseTaxation()))){
            	addNode(buf, "DeductionDueProportion", "");
            }else{
            	addNode(buf, "DeductionDueProportion", ""+(Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCcarshipTax().getArr(0).getFreeRate()))/100));
            }
            if("P".equals(baseTaxationEncoder(blPolicy.getBLPrpCcarshipTax().getArr(0).getBaseTaxation()))){
            	addNode(buf, "Deduction", "");
            }else{
            	addNode(buf, "Deduction", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxRelief());
            }
            
            addNode(buf, "DeductionDocumentNumber", blPolicy.getBLPrpCcarshipTax().getArr(0).getPaidFreeCertificate());
            addNode(buf, "TaxDepartmentCode", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxComCode());
            addNode(buf, "TaxDepartment", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxComName());
            buf.append("</Derate>");
            buf.append("</CurrentTaxDue>");
        }else if("4".equals(TaxRelifFlag)){
            
            buf.append("<CurrentTaxDue>");
			
        	if(utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
        		addNode(buf, "TaxLocationCode",strTaxLocationCode);
        		addNode(buf, "TaxUnitTypeCode",strTaxUnitTypeCode);
        		addNode(buf, "TaxStartDate",correctDate(blPolicy.getBLPrpCcarshipTax().getArr(0).getPayStartDate()));
        		addNode(buf, "TaxEndDate",correctDate(blPolicy.getBLPrpCcarshipTax().getArr(0).getPayEndDate()));
        		
        		addNode(buf, "UnitRate",blCICarShipTaxQGDemand.getArr(0).getUnitRate());
        		addNode(buf, "AnnualTaxAmount",blCICarShipTaxQGDemand.getArr(0).getAnnualTaxAmount());
				addNode(buf, "TaxDue",blCICarShipTaxQGDemand.getArr(0).getTaxDue());
        		addNode(buf, "OverDue",blCICarShipTaxQGDemand.getArr(0).getOverDue());
        		addNode(buf, "TotalAmount",""+TotalAmount);
        		
        	}
        	
			
            if(!new UtiPower().checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
            	addNode(buf, "DeclareDate", correctDate(blPolicy.getBLPrpCmain().getArr(0).getOperateDate()));
                addNode(buf, "TaxStartDate", correctDate(blPolicy.getBLPrpCcarshipTax().getArr(0).getPayStartDate()));
                addNode(buf, "TaxEndDate", correctDate(blPolicy.getBLPrpCcarshipTax().getArr(0).getPayEndDate()));
                addNode(buf, "TaxDepartment", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxComName());
                addNode(buf, "TaxDocumentNumber", blPolicy.getBLPrpCcarshipTax().getArr(0).getPaidFreeCertificate());
            }else{
            	buf.append("<Paid>");
        		addNode(buf, "TaxDepartmentCode", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxComCode());
        		addNode(buf, "TaxDepartment", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxComName());
        		addNode(buf, "TaxDocumentNumber", blPolicy.getBLPrpCcarshipTax().getArr(0).getPaidFreeCertificate());
        		buf.append("</Paid>");
            }
            
            buf.append("</CurrentTaxDue>");
        
    	}else if("6".equals(TaxRelifFlag) ){
        	if(utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
        		buf.append("<CurrentTaxDue>");
        		addNode(buf, "TaxLocationCode",strTaxLocationCode);
        		addNode(buf, "TaxUnitTypeCode",strTaxUnitTypeCode);
        		addNode(buf, "TaxStartDate",correctDate(blCICarShipTaxQGDemand.getArr(0).getTaxStartDate()));
        		addNode(buf, "TaxEndDate",correctDate(blCICarShipTaxQGDemand.getArr(0).getTaxEndDate()));
        		addNode(buf, "UnitRate",blCICarShipTaxQGDemand.getArr(0).getUnitRate());
        		addNode(buf, "AnnualTaxAmount",blCICarShipTaxQGDemand.getArr(0).getAnnualTaxAmount());
        		addNode(buf, "TaxDue",blCICarShipTaxQGDemand.getArr(0).getTaxDue());
        		addNode(buf, "OverDue",blCICarShipTaxQGDemand.getArr(0).getOverDue());
        		addNode(buf, "TotalAmount",""+TotalAmount);
        		buf.append("</CurrentTaxDue>");
        	}
        }
    	
		
        if(!new UtiPower().checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
        	addNode(buf, "AnnualTaxDue", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxActual());
        	addNode(buf, "SumTaxDefault", blPolicy.getBLPrpCcarshipTax().getArr(0).getPreviousPay());
        	addNode(buf, "SumOverdue", blPolicy.getBLPrpCcarshipTax().getArr(0).getLateFee());
        	addNode(buf, "SumTax", blPolicy.getBLPrpCcarshipTax().getArr(0).getSumPayTax());
        }else{
        	buf.append("<TaxAmount>");
        	addNode(buf, "TaxAmount_Flag", "2");
        	
        	addNode(buf, "AnnualTaxDue", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxActual());
        	addNode(buf, "SumTaxDefault", blPolicy.getBLPrpCcarshipTax().getArr(0).getPreviousPay());
        	addNode(buf, "SumOverdue", blPolicy.getBLPrpCcarshipTax().getArr(0).getLateFee());
        	addNode(buf, "SumTax", blPolicy.getBLPrpCcarshipTax().getArr(0).getSumPayTax());
        	buf.append("</TaxAmount>");
    		if("6".equals(TaxRelifFlag)){
    			addNode(buf, "Declare_Status_IA", "");
    		}else if("4".equals(TaxRelifFlag)){
				addNode(buf, "Declare_Status_IA", "1");
			}else{
    			addNode(buf, "Declare_Status_IA", "0");
    		}
			
    		addNode(buf, "Calc_Tax_Flag", blPolicy.getBLPrpCcarshipTax().getArr(0).getCalcTaxFlag());
        }
		
        
        if(utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())
                && utiPower.checkCarShipTaxJZDelinquent(blPolicy.getBLPrpCmain().getArr(0).getComCode())){
            int carshiptaxSize = blCICarShipTaxQGDemand.getSize();
            for(int i = 0; i < carshiptaxSize; i++){
                if("1".equals(blCICarShipTaxQGDemand.getArr(i).getTaxType())){
                    buf.append("<DelinquentTaxDue>");
                    String strTaxLocationCodeD="";
                    String strTaxUnitTypeCodeD="";
                    if(blCICarShipTaxQGDemand.getArr(i).getTaxLocationCode()!=null){
                        strTaxLocationCodeD=blCICarShipTaxQGDemand.getArr(i).getTaxLocationCode();
                    }
                    if(blCICarShipTaxQGDemand.getArr(i).getTaxUnitTypeCode()!=null){
                        strTaxUnitTypeCodeD=blCICarShipTaxQGDemand.getArr(i).getTaxUnitTypeCode();
                    }
                    addNode(buf, "TaxLocationCode", strTaxLocationCodeD);
                    addNode(buf, "TaxUnitTypeCode", strTaxUnitTypeCodeD);
                    addNode(buf, "TaxStartDate", correctDate(blCICarShipTaxQGDemand.getArr(i).getTaxStartDate()));
                    addNode(buf, "TaxEndDate", correctDate(blCICarShipTaxQGDemand.getArr(i).getTaxEndDate()));
                    addNode(buf, "UnitRate", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getUnitRate()));
                    addNode(buf, "AnnualTaxAmount", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getAnnualTaxAmount()));
                    addNode(buf, "TaxDue", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getTaxDue()));
                    addNode(buf, "ExceedDate", correctDate(blCICarShipTaxQGDemand.getArr(i).getExceedDate()));
                    addNode(buf, "ExceedDaysCount", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getExceedDaysCount()));
                    addNode(buf, "OverDue", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getOverDue()));
                    addNode(buf, "TotalAmount", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getTotalAmount()));
                    buf.append("<Derate>");
                    addNode(buf, "DeductionDueCode", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getDeductionDueCode()));
                    addNode(buf, "DeductionDueType", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getDeductionDueType()));
                    if(Double.parseDouble(ChgData.chgStrZero(blCICarShipTaxQGDemand.getArr(i).getDeductionDueProportion()))/100==0){
                        addNode(buf, "DeductionDueProportion", "");
                    }else{
                        addNode(buf, "DeductionDueProportion", ""+(Double.parseDouble(ChgData.chgStrZero(blCICarShipTaxQGDemand.getArr(i).getDeductionDueProportion()))/100));
                    }
                    if(Double.parseDouble(ChgData.chgStrZero(blCICarShipTaxQGDemand.getArr(i).getDeduction()))==0){
                        addNode(buf, "Deduction", "");
                    }else{
                        addNode(buf, "Deduction", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getDeduction()));
                    }
                    addNode(buf, "DeductionDocumentNumber", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getDeductionDocumentNumber()));
                    addNode(buf, "TaxDepartmentCode", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getDeductionDepartmentCode()));
                    addNode(buf, "TaxDepartment", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getDeductionDepartment()));
                    buf.append("</Derate>");
                    buf.append("<Paid>");
                    addNode(buf, "TaxDepartmentCode", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getTaxDepartmentCode()));
                    addNode(buf, "TaxDepartment", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getTaxDepartment()));
                    addNode(buf, "TaxDocumentNumber", StringConvertor.changeNullToEmpty(blCICarShipTaxQGDemand.getArr(i).getTaxDocumentNumber()));
                    buf.append("</Paid>");
                    buf.append("</DelinquentTaxDue>");
                }
            }
        }
        
        
        if(new UtiPower().isCarShipTaxNB(blPolicy.getBLPrpCmain().getArr(0).getComCode())){
        	buf.append("</VehicleTaxation_NB>");
        }else{
        	buf.append("</VehicleTaxation>");
        }
    	
    }
    

	/**
	 * 纠正日期格式
	 *
	 * @param dateString
	 *            8个字符的日期
	 * @return YYYYMMDD形式的日期
	 */
	private String correctDate(String dateString) {
		String result = StringUtils.replace(dateString, "-", "");
		return result;
	}
    
	public static void addNode(StringBuffer buffer, String name, String value) {
		value = StringUtils.replace(value, "<", "&lt;");
		value = StringUtils.replace(value, ">", "&gt;");
		value = StringUtils.replace(value, "&", "&amp;");
		value = StringUtils.replace(value, "'", "&apos;");
		value = StringUtils.replace(value, "\"", "&quot;");

		buffer.append("<");
		buffer.append(name);
		buffer.append(">");
		buffer.append(value);
		buffer.append("</");
		buffer.append(name);
		buffer.append(">");
		buffer.append("\r\n");
	}
    
    
    /**
     * 纳税代码转换
     *
     * @param 核心业务纳税类型
     *           
     * @return 行业平台纳税类型
     */
    public String taxRelifFlagEncoder(String taxRelifFlag){
        if("1".equals(taxRelifFlag)){
            return "T";
        }else if("2".equals(taxRelifFlag)){
            return "E";
        }else if("3".equals(taxRelifFlag)){
            return "C";
        }else if("4".equals(taxRelifFlag)){
            return "P";
        }else if("5".equals(taxRelifFlag)){
            return "D";
        }else if("6".equals(taxRelifFlag)){
            return "R";
        }else if("7".equals(taxRelifFlag)){
            return "R";
        }else{
            return "";
        }
    }
    
    /**
     * 减免原因代码转换
     *
     * @param 核心业务减免原因
     *           
     * @return 行业平台减免原因
     */
    public String relifReasonEncoder(String relifReason){
        if("01".equals(relifReason)){
            return "F";
        }else if("02".equals(relifReason)){
            return "A";
        }else if("03".equals(relifReason)){
            return "P";
        }else if("04".equals(relifReason)){
            return "C";
        }else if("05".equals(relifReason)){
            return "D";
        }else if("06".equals(relifReason)){
            return "O";
        
        }else if("07".equals(relifReason)){
        	return "E";
        
        }else{
            return "";
        }
        
    }
    
    public String baseTaxationEncoder(String baseTaxation){
        if("1".equals(baseTaxation)){
            return "A";
        }else if("2".equals(baseTaxation)){
            return "P";
        }else if("3".equals(baseTaxation)){
            return "E";
        }else{
            return "";
        }
    }
    
}
