package com.sp.indiv.ci.interf;

import com.sp.platform.bl.facade.BLPrpDcompanyFacade;
import com.sp.platform.dto.domain.PrpDcompanyDto;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.pubfun.PubTools;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.StringConvertor;
import com.sp.utility.UtiPower;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;

public class ProposalCarShipTaxValidEncoder {
	
	public void encode(BLProposal blProposal, StringBuffer buf, String part) throws Exception 
    {
        if("BASE_PART".equals(part)){
        	encode(blProposal,buf);
        }else if("VehicleTaxation".equals(part)){
            UtiPower utiPower = new UtiPower();
            
            if(utiPower.checkCICarShipTaxQG(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())
            		||utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
        	encodeVehicleTaxation(buf,blProposal);
            }
            
        }
    }
	
	
	public void encode(BLProposal blProposal, StringBuffer buf) throws Exception 
    {
        
        UtiPower utiPower = new UtiPower();
        if(utiPower.checkCICarshipTaxBJ(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode()))
        {
        	addCarShipTaxBJ(buf,blProposal);
        }else if(utiPower.checkCICarShipTaxQG(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	addCarShipTaxQG(buf,blProposal);
        }
    }
	
    
    protected void addCarShipTaxBJ(StringBuffer buf, BLProposal blProposal) throws Exception{
		
		String payTaxWay = "";
		
		String iLicenseNo = blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo().trim();
		PubTools pubTools = new PubTools();
		String strEcdemicVehicleFlag = pubTools.checkEcdemicVehicleFlag(iLicenseNo);
		
		if(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelifFlag().equals("0")){
			payTaxWay = "02";
			addNode(buf, "ACTUAL_TAX_FEE", blProposal.getBLPrpTcarshipTax().getArr(0).getSumPayTax());
		}
		else if(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelifFlag().equals("1")){
			payTaxWay = "01";
			addNode(buf, "ACTUAL_TAX_FEE", "0");
		}else if(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelifFlag().equals("2")){
			payTaxWay = "03";
			
			addNode(buf, "ACTUAL_TAX_FEE", "0");
	    	String currentDate=new ChgDate().getCurrentTime("yyyy-MM-dd");
	    	if(new UtiPower().checkCarShipTaxBJ(blProposal.getBLPrpTmain().getArr(0).getComCode(), currentDate)){
	    		String licenseNo = blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo().trim().substring(0,1);
	    		
	    		if("1".equals(strEcdemicVehicleFlag)){
	    		
	    			
	    			if((null!=blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate())&&
	    			   (!"".equals(blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate())))
	    			  {
	        			addNode(buf, "PAY_NO", "M" + blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate());  
	    			  }	else{
	    				addNode(buf, "PAY_NO", "");
	    			  }
					
	    			addNode(buf, "DEPARTMENT_NONLOCAL", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComCode());
			 	}else{
			 		addNode(buf, "PAY_NO", "");
			 		addNode(buf, "DEPARTMENT_NONLOCAL", "");
	    		}
	    	}else{
	    		addNode(buf, "PAY_NO", blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate());
    			addNode(buf, "DEPARTMENT", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComName());
			}
			
		}else if(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelifFlag().equals("3")){
			payTaxWay = "03";
			addNode(buf, "ACTUAL_TAX_FEE", "0");
			String currentDate=new ChgDate().getCurrentTime("yyyy-MM-dd");
			String licenseNo = blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo().trim().substring(0,1);
			
			if("1".equals(strEcdemicVehicleFlag)){
			
              
              if((null!=blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate())&&
                    (!"".equals(blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate())))
              {
                addNode(buf, "PAY_NO", "M" + blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate());  
              }else{
	            addNode(buf, "PAY_NO", "");
              }
              
			}else{
				if(new UtiPower().checkCarShipTaxBJ(blProposal.getBLPrpTmain().getArr(0).getComCode(), currentDate)){
					addNode(buf, "PAY_NO", "");
				}else{
					addNode(buf, "PAY_NO", blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate());
				}
			}
			
	    	if(new UtiPower().checkCarShipTaxBJ(blProposal.getBLPrpTmain().getArr(0).getComCode(), currentDate)){
	    		
	    		if("1".equals(strEcdemicVehicleFlag)){
	    		
	    			addNode(buf, "DEPARTMENT_NONLOCAL", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComCode());
	    		}else{
	    			addNode(buf, "DEPARTMENT_NONLOCAL", "");
	    		}
	    	}else{
	    		addNode(buf, "DEPARTMENT", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComName());
	    	}
			
		}
		addNode(buf, "PAY_TAX_WAY", payTaxWay);
		addNode(buf, "COMPUTER_CODE",AppConfig.get("sysconst.CI_INSURED_01_COMPUTER_CODE"));
	}
	
    protected void addCarShipTaxQG(StringBuffer buf, BLProposal blProposal) throws Exception{
    	
    	
    }
    
    protected void encodeVehicleTaxation(StringBuffer buf, BLProposal blProposal) throws Exception{
    	String TaxRelifFlag = blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelifFlag();
        BLPrpDcompanyFacade dcompany  = new BLPrpDcompanyFacade();
        PrpDcompanyDto prpDcompanyDto = dcompany.findByPrimaryKey(blProposal.getBLPrpTmain().getArr(0).getComCode());
        
        if(new UtiPower().isCarShipTaxNB(blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	buf.append("<VehicleTaxation_NB>");
        }else{
        	buf.append("<VehicleTaxation>");
        }
        
    	addNode(buf, "TaxTermTypeCode", "08");
    	addNode(buf, "TaxConditionCode", taxRelifFlagEncoder(TaxRelifFlag));
    	addNode(buf, "TaxRegistryNumber", prpDcompanyDto.getTaxRegisterNo());
    	addNode(buf, "TaxPayerName", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxpayerName());
    	addNode(buf, "TaxPayerIdentificationCode", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxpayerIdentifier());
    	buf.append("<CurrentTaxDue>");
    	
    	UtiPower utiPower = new UtiPower();
    	if(utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    		String strTaxLocationCode="";
    		String strTaxUnitTypeCode="";
    		if(blProposal.getBLCICarShipTaxQGDemand().getArr(0).getTaxLocationCode()!=null){
    			strTaxLocationCode=blProposal.getBLCICarShipTaxQGDemand().getArr(0).getTaxLocationCode();
    		}
    		if(blProposal.getBLCICarShipTaxQGDemand().getArr(0).getTaxUnitTypeCode()!=null){
    			strTaxUnitTypeCode=blProposal.getBLCICarShipTaxQGDemand().getArr(0).getTaxUnitTypeCode();
    		}
    		addNode(buf, "TaxLocationCode",strTaxLocationCode);
    		addNode(buf, "TaxUnitTypeCode",strTaxUnitTypeCode);
    		
    		if("6".equals(TaxRelifFlag)){
    			addNode(buf, "TaxStartDate",correctDate(blProposal.getBLCICarShipTaxQGDemand().getArr(0).getTaxStartDate()));
    			addNode(buf, "TaxEndDate",correctDate(blProposal.getBLCICarShipTaxQGDemand().getArr(0).getTaxEndDate()));
    		}else{
    			addNode(buf, "TaxStartDate",correctDate(blProposal.getBLPrpTcarshipTax().getArr(0).getPayStartDate()));
        		addNode(buf, "TaxEndDate",correctDate(blProposal.getBLPrpTcarshipTax().getArr(0).getPayEndDate()));
    		}
    		addNode(buf, "UnitRate",blProposal.getBLCICarShipTaxQGDemand().getArr(0).getUnitRate());
    		addNode(buf, "AnnualTaxAmount",blProposal.getBLCICarShipTaxQGDemand().getArr(0).getAnnualTaxAmount());
    		addNode(buf, "TaxDue",blProposal.getBLCICarShipTaxQGDemand().getArr(0).getTaxDue());
    		addNode(buf, "OverDue",blProposal.getBLCICarShipTaxQGDemand().getArr(0).getOverDue());
    		
    		if("08".equals(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0, 2))&&"2".equals(TaxRelifFlag)){


    			String TotalAmount = StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(0).getTotalAmount());
    			addNode(buf, "TotalAmount",TotalAmount);
			}else {
				double TotalAmount = Double.parseDouble(ChgData.chgStrZero(blProposal.getBLCICarShipTaxQGDemand().getArr(0).getTaxDue()))
	             +Double.parseDouble(ChgData.chgStrZero(blProposal.getBLCICarShipTaxQGDemand().getArr(0).getOverDue()));
				addNode(buf, "TotalAmount",""+TotalAmount);
			}
    		
    		
    	}
    	if("2".equals(TaxRelifFlag)||"3".equals(TaxRelifFlag)){
    		if(!utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    			addNode(buf, "DeclareDate", correctDate(blProposal.getBLPrpTmain().getArr(0).getOperateDate()));
    		}
    		buf.append("<Derate>");
    		addNode(buf, "DeductionDueCode", relifReasonEncoder(blProposal.getBLPrpTcarshipTax().getArr(0).getRelifReason()));
    		addNode(buf, "DeductionDueType", baseTaxationEncoder(blProposal.getBLPrpTcarshipTax().getArr(0).getBaseTaxation()));
    		
    		if(Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTcarshipTax().getArr(0).getFreeRate()))/100==0){
    			addNode(buf, "DeductionDueProportion", "");
    		}else{
    			addNode(buf, "DeductionDueProportion", ""+(Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTcarshipTax().getArr(0).getFreeRate()))/100));
    		}
    		if(Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelief()))==0){
    			addNode(buf, "Deduction", "");
    		}else{
    			addNode(buf, "Deduction", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelief());
    		}
    		
    		addNode(buf, "DeductionDocumentNumber", blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate());
    		addNode(buf, "TaxDepartmentCode", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComCode());
    		addNode(buf, "TaxDepartment", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComName());
    		buf.append("</Derate>");
    	}
		
		else if("4".equals(TaxRelifFlag)){
    		if(!utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    		addNode(buf, "TaxStartDate", correctDate(blProposal.getBLPrpTcarshipTax().getArr(0).getPayStartDate()));
    		addNode(buf, "TaxEndDate", correctDate(blProposal.getBLPrpTcarshipTax().getArr(0).getPayEndDate()));
            addNode(buf, "DeclareDate", correctDate(blProposal.getBLPrpTmain().getArr(0).getOperateDate()));
            addNode(buf, "TaxDepartmentCode", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComCode());
    		addNode(buf, "TaxDepartment", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComName());
    		addNode(buf, "TaxDocumentNumber", blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate());
    		}else{
    		buf.append("<Paid>");
    		addNode(buf, "TaxDepartmentCode", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComCode());
    		addNode(buf, "TaxDepartment", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComName());
    		addNode(buf, "TaxDocumentNumber", blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate());
    		buf.append("</Paid>");
    		}
    	}
		
    	buf.append("</CurrentTaxDue>");
    	
    	if(utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())
    	        && utiPower.checkCarShipTaxJZDelinquent(blProposal.getBLPrpTmain().getArr(0).getComCode())){
    	    int carshiptaxSize = blProposal.getBLCICarShipTaxQGDemand().getSize();
    	    for(int i = 0; i < carshiptaxSize; i++){
    	        if("1".equals(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getTaxType())){
    	            buf.append("<DelinquentTaxDue>");
    	            String strTaxLocationCodeD="";
    	            String strTaxUnitTypeCodeD="";
    	            if(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getTaxLocationCode()!=null){
    	                strTaxLocationCodeD=blProposal.getBLCICarShipTaxQGDemand().getArr(i).getTaxLocationCode();
    	            }
    	            if(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getTaxUnitTypeCode()!=null){
    	                strTaxUnitTypeCodeD=blProposal.getBLCICarShipTaxQGDemand().getArr(i).getTaxUnitTypeCode();
    	            }
    	            addNode(buf, "TaxLocationCode", strTaxLocationCodeD);
    	            addNode(buf, "TaxUnitTypeCode", strTaxUnitTypeCodeD);
    	            addNode(buf, "TaxStartDate", correctDate(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getTaxStartDate()));
    	            addNode(buf, "TaxEndDate", correctDate(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getTaxEndDate()));
    	            addNode(buf, "UnitRate", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getUnitRate()));
    	            addNode(buf, "AnnualTaxAmount", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getAnnualTaxAmount()));
    	            addNode(buf, "TaxDue", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getTaxDue()));
    	            addNode(buf, "ExceedDate", correctDate(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getExceedDate()));
    	            addNode(buf, "ExceedDaysCount", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getExceedDaysCount()));
    	            addNode(buf, "OverDue", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getOverDue()));
    	            addNode(buf, "TotalAmount", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getTotalAmount()));
    	            buf.append("<Derate>");
    	            addNode(buf, "DeductionDueCode", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getDeductionDueCode()));
    	            addNode(buf, "DeductionDueType", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getDeductionDueType()));
    	            if(Double.parseDouble(ChgData.chgStrZero(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getDeductionDueProportion()))/100==0){
    	                addNode(buf, "DeductionDueProportion", "");
    	            }else{
    	                addNode(buf, "DeductionDueProportion", ""+(Double.parseDouble(ChgData.chgStrZero(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getDeductionDueProportion()))/100));
    	            }
    	            if(Double.parseDouble(ChgData.chgStrZero(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getDeduction()))==0){
    	                addNode(buf, "Deduction", "");
    	            }else{
    	                addNode(buf, "Deduction", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getDeduction()));
    	            }
    	            addNode(buf, "DeductionDocumentNumber", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getDeductionDocumentNumber()));
    	            addNode(buf, "TaxDepartmentCode", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getDeductionDepartmentCode()));
    	            addNode(buf, "TaxDepartment", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getDeductionDepartment()));
    	            buf.append("</Derate>");
    	            buf.append("<Paid>");
    	            addNode(buf, "TaxDepartmentCode", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getTaxDepartmentCode()));
    	            addNode(buf, "TaxDepartment", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getTaxDepartment()));
    	            addNode(buf, "TaxDocumentNumber", StringConvertor.changeNullToEmpty(blProposal.getBLCICarShipTaxQGDemand().getArr(i).getTaxDocumentNumber()));
    	            buf.append("</Paid>");
    	            buf.append("</DelinquentTaxDue>");
    	        }
    	    }
    	}
    	
    	if(!utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    		addNode(buf, "AnnualTaxDue", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxActual());
        	addNode(buf, "SumTaxDefault", blProposal.getBLPrpTcarshipTax().getArr(0).getPreviousPay());
        	addNode(buf, "SumOverdue", blProposal.getBLPrpTcarshipTax().getArr(0).getLateFee());
        	addNode(buf, "SumTax", blProposal.getBLPrpTcarshipTax().getArr(0).getSumPayTax());
    	}else{
    		buf.append("<TaxAmount>");
    		addNode(buf, "TaxAmount_Flag", "2");
    		addNode(buf, "AnnualTaxDue", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxActual());
            addNode(buf, "SumTaxDefault", blProposal.getBLPrpTcarshipTax().getArr(0).getPreviousPay());
            addNode(buf, "SumOverdue", blProposal.getBLPrpTcarshipTax().getArr(0).getLateFee());
            addNode(buf, "SumTax", blProposal.getBLPrpTcarshipTax().getArr(0).getSumPayTax());
    		buf.append("</TaxAmount>");
    		if("6".equals(TaxRelifFlag)){
    			addNode(buf, "Declare_Status_IA", "");
			}else if("4".equals(TaxRelifFlag)){
				addNode(buf, "Declare_Status_IA", "1");
			}else{
				addNode(buf, "Declare_Status_IA", "0");
			}
    		addNode(buf, "Calc_Tax_Flag", blProposal.getBLPrpTcarshipTax().getArr(0).getCalcTaxFlag());
    	}
    	
    	
    	if(new UtiPower().isCarShipTaxNB(blProposal.getBLPrpTmain().getArr(0).getComCode())){
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
