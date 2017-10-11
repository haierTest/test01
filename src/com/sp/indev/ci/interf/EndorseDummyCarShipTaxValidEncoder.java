package com.sp.indiv.ci.interf;



import com.sp.platform.bl.facade.BLPrpDcompanyFacade;
import com.sp.platform.dto.domain.PrpDcompanyDto;
import com.sp.prpall.blsvr.cb.BLPrpCPcarshipTax;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.utility.StringConvertor;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;

public class EndorseDummyCarShipTaxValidEncoder {

    /**
     * @param args
     */
    public static void main(String[] args) {
        

    }

    public void encode(DbPool dbPool,BLEndorse blEndorse, StringBuffer buf, String part) throws Exception 
    {
        if("BASE_PART".equals(part)){
            addCarShipTaxQG(buf,blEndorse);
        }else if("VehicleTaxation".equals(part)){
            encodeVehicleTaxation(dbPool,blEndorse, buf);
        }
    }

    protected void addCarShipTaxQG(StringBuffer buf, BLEndorse blEndorse) throws Exception{
        UtiPower utiPower = new UtiPower();
        if(utiPower.checkCICarShipTaxQG(blEndorse.getBLPrpPmain().getArr(0).getRiskCode(), blEndorse.getBLPrpPmain().getArr(0).getComCode())){
          addNode(buf, "IS_AMEND_TAX",blEndorse.getBLCIEndorValid().getArr(0).getIsAmendTax());
          addNode(buf, "TAX_ACTUAL_AMEND_PREMIUM",blEndorse.getBLCIEndorValid().getArr(0).getTaxAmendPremium());
        }
    }

    public void encodeVehicleTaxation(DbPool dbPool,BLEndorse blEndorse,StringBuffer buf) throws Exception{
        UtiPower utiPower = new UtiPower();
        
        if(utiPower.checkCICarShipTaxQG(blEndorse.getBLPrpPmain().getArr(0).getRiskCode(), blEndorse.getBLPrpPmain().getArr(0).getComCode())){
        	if("1".equals(blEndorse.getBLCIEndorValid().getArr(0).getIsAmendTax())){
        		addVehicleTaxationQG(dbPool,blEndorse,buf);
        	}
        }
        if(utiPower.checkCarShipTaxJZ(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
        		addVehicleTaxationQG(dbPool,blEndorse,buf);
        }
        
    }
    
    public void addVehicleTaxationQG(DbPool dbPool,BLEndorse blEndorse,StringBuffer buf) throws Exception{
        BLPrpDcompanyFacade dcompany  = new BLPrpDcompanyFacade();
        PrpDcompanyDto prpDcompanyDto = dcompany.findByPrimaryKey(blEndorse.getBLPrpPmain().getArr(0).getComCode());
        BLPrpCPcarshipTax  bLPrpCPcarshipTax = new  BLPrpCPcarshipTax();
        bLPrpCPcarshipTax.query(dbPool," policyno='"+blEndorse.getBLPrpPhead().getArr(0).getPolicyNo()+"'");
        String TaxRelifFlag = bLPrpCPcarshipTax.getArr(0).getTaxRelifFlag();
        
        if(new UtiPower().isCarShipTaxNB(blEndorse.getBLPrpPmain().getArr(0).getComCode())){
        	buf.append("<VehicleTaxation_NB>");
        }else{
        	buf.append("<VehicleTaxation>");
        }
        
        addNode(buf, "TaxTermTypeCode", "08");
        addNode(buf, "TaxConditionCode", taxRelifFlagEncoder(TaxRelifFlag));
        addNode(buf, "TaxRegistryNumber", prpDcompanyDto.getTaxRegisterNo());
        addNode(buf, "TaxPayerName", bLPrpCPcarshipTax.getArr(0).getTaxpayerName());
        addNode(buf, "TaxPayerIdentificationCode", bLPrpCPcarshipTax.getArr(0).getTaxpayerIdentifier());
        UtiPower utiPower = new UtiPower();
        
        double TotalAmount = 0;
        if(blEndorse.getBLCICarShipTaxQGEndorse().getSize()>0
        	&&utiPower.checkCarShipTaxJZ(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
        	TotalAmount = Double.parseDouble(ChgData.chgStrZero(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxDue()))
        	    +Double.parseDouble(ChgData.chgStrZero(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getOverDue()));
        }
        
        
        if("1".equals(TaxRelifFlag) ){
        	if(!utiPower.checkCarShipTaxJZ(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
        		 buf.append("<CurrentTaxDue>");
                 addNode(buf, "DeclareDate", correctDate(blEndorse.getBLPrpPmain().getArr(0).getOperateDate()));
                 buf.append("</CurrentTaxDue>");
        	}else{
        		buf.append("<CurrentTaxDue>");
        		String strTaxLocationCode = "";
        		String strTaxUnitTypeCode = "";
        		if(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxLocationCode()!=null){
        			strTaxLocationCode=blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxLocationCode();
        		}
        		if(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxUnitTypeCode()!=null){
        			strTaxUnitTypeCode=blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxUnitTypeCode();
        		}
        		addNode(buf, "TaxLocationCode",strTaxLocationCode);
        		addNode(buf, "TaxUnitTypeCode",strTaxUnitTypeCode);
        		addNode(buf, "TaxStartDate",correctDate(bLPrpCPcarshipTax.getArr(0).getPayStartDate()));
        		addNode(buf, "TaxEndDate",correctDate(bLPrpCPcarshipTax.getArr(0).getPayEndDate()));
        		addNode(buf, "UnitRate",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getUnitRate());
         		addNode(buf, "AnnualTaxAmount",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getAnnualTaxAmount());
         		addNode(buf, "TaxDue",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxDue());
         		addNode(buf, "OverDue",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getOverDue());
         		
         		addNode(buf, "TotalAmount",""+TotalAmount);
         		
        		buf.append("</CurrentTaxDue>");
        	}
        }else if("3".equals(TaxRelifFlag)){
            
            buf.append("<CurrentTaxDue>");
            if(!utiPower.checkCarShipTaxJZ(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
            	addNode(buf, "DeclareDate", correctDate(blEndorse.getBLPrpPmain().getArr(0).getOperateDate()));
            }else{
            	String strTaxLocationCode = "";
        		String strTaxUnitTypeCode = "";
        		if(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxLocationCode()!=null){
        			strTaxLocationCode=blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxLocationCode();
        		}
        		if(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxUnitTypeCode()!=null){
        			strTaxUnitTypeCode=blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxUnitTypeCode();
        		}
            	addNode(buf, "TaxLocationCode",strTaxLocationCode);
        		addNode(buf, "TaxUnitTypeCode",strTaxUnitTypeCode);
        		addNode(buf, "TaxStartDate",correctDate(bLPrpCPcarshipTax.getArr(0).getPayStartDate()));
        		addNode(buf, "TaxEndDate",correctDate(bLPrpCPcarshipTax.getArr(0).getPayEndDate()));
        		addNode(buf, "UnitRate",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getUnitRate());
        		addNode(buf, "AnnualTaxAmount",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getAnnualTaxAmount());
        		addNode(buf, "TaxDue",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxDue());
         		addNode(buf, "OverDue",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getOverDue());
         		
         		addNode(buf, "TotalAmount",""+TotalAmount);
         		
            }
            buf.append("<Derate>");
            addNode(buf, "DeductionDueCode", relifReasonEncoder(bLPrpCPcarshipTax.getArr(0).getRelifReason()));
            addNode(buf, "DeductionDueType", baseTaxationEncoder(bLPrpCPcarshipTax.getArr(0).getBaseTaxation()));
            
            if("A".equals(baseTaxationEncoder(bLPrpCPcarshipTax.getArr(0).getBaseTaxation()))){
            	addNode(buf, "DeductionDueProportion", "");
            }else{
            	addNode(buf, "DeductionDueProportion", ""+(Double.parseDouble(ChgData.chgStrZero(bLPrpCPcarshipTax.getArr(0).getFreeRate()))/100));
            }
            if("P".equals(baseTaxationEncoder(bLPrpCPcarshipTax.getArr(0).getBaseTaxation()))){
            	addNode(buf, "Deduction", "");
            }else{
            	addNode(buf, "Deduction", bLPrpCPcarshipTax.getArr(0).getTaxRelief());
            }
            
            addNode(buf, "DeductionDocumentNumber", bLPrpCPcarshipTax.getArr(0).getPaidFreeCertificate());
            addNode(buf, "TaxDepartmentCode", bLPrpCPcarshipTax.getArr(0).getTaxComCode());
            addNode(buf, "TaxDepartment", bLPrpCPcarshipTax.getArr(0).getTaxComName());
            buf.append("</Derate>");
            buf.append("</CurrentTaxDue>");
        }else if("2".equals(TaxRelifFlag)){
            buf.append("<CurrentTaxDue>");
            if(utiPower.checkCarShipTaxJZ(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
            	String strTaxLocationCode = "";
        		String strTaxUnitTypeCode = "";
        		if(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxLocationCode()!=null){
        			strTaxLocationCode=blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxLocationCode();
        		}
        		if(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxUnitTypeCode()!=null){
        			strTaxUnitTypeCode=blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxUnitTypeCode();
        		}
            	addNode(buf, "TaxLocationCode",strTaxLocationCode);
        		addNode(buf, "TaxUnitTypeCode",strTaxUnitTypeCode);
        		addNode(buf, "TaxStartDate",correctDate(bLPrpCPcarshipTax.getArr(0).getPayStartDate()));
        		addNode(buf, "TaxEndDate",correctDate(bLPrpCPcarshipTax.getArr(0).getPayEndDate()));
        		addNode(buf, "UnitRate",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getUnitRate());
        		addNode(buf, "AnnualTaxAmount",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getAnnualTaxAmount());
        		addNode(buf, "TaxDue",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxDue());
         		addNode(buf, "OverDue",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getOverDue());
         		
         		addNode(buf, "TotalAmount",""+TotalAmount);
         		
            }
            buf.append("<Derate>");
            addNode(buf, "DeductionDueCode", relifReasonEncoder(bLPrpCPcarshipTax.getArr(0).getRelifReason()));
            addNode(buf, "DeductionDueType", baseTaxationEncoder(bLPrpCPcarshipTax.getArr(0).getBaseTaxation()));
            
            if(Double.parseDouble(ChgData.chgStrZero(bLPrpCPcarshipTax.getArr(0).getFreeRate()))/100==0){
            	addNode(buf, "DeductionDueProportion", "");
            }else{
            	addNode(buf, "DeductionDueProportion", ""+(Double.parseDouble(ChgData.chgStrZero(bLPrpCPcarshipTax.getArr(0).getFreeRate()))/100));
            }
            if(Double.parseDouble(ChgData.chgStrZero(bLPrpCPcarshipTax.getArr(0).getTaxRelief()))==0){
            	addNode(buf, "Deduction", "");
            }else{
            	addNode(buf, "Deduction", bLPrpCPcarshipTax.getArr(0).getTaxRelief());
            }
            
            addNode(buf, "DeductionDocumentNumber", bLPrpCPcarshipTax.getArr(0).getPaidFreeCertificate());
            addNode(buf, "TaxDepartmentCode", bLPrpCPcarshipTax.getArr(0).getTaxComCode());
            addNode(buf, "TaxDepartment", bLPrpCPcarshipTax.getArr(0).getTaxComName());
            buf.append("</Derate>");
            buf.append("</CurrentTaxDue>");
        }else if("4".equals(TaxRelifFlag)){
            
            buf.append("<CurrentTaxDue>");
            if(!utiPower.checkCarShipTaxJZ(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
            	addNode(buf, "DeclareDate", correctDate(blEndorse.getBLPrpPmain().getArr(0).getOperateDate()));
            	addNode(buf, "TaxDepartment", bLPrpCPcarshipTax.getArr(0).getTaxComName());
                addNode(buf, "TaxDocumentNumber", bLPrpCPcarshipTax.getArr(0).getPaidFreeCertificate());
            }else{
            	String strTaxLocationCode = "";
        		String strTaxUnitTypeCode = "";
        		if(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxLocationCode()!=null){
        			strTaxLocationCode=blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxLocationCode();
        		}
        		if(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxUnitTypeCode()!=null){
        			strTaxUnitTypeCode=blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxUnitTypeCode();
        		}
            	addNode(buf, "TaxLocationCode",strTaxLocationCode);
        		addNode(buf, "TaxUnitTypeCode",strTaxUnitTypeCode);
        		addNode(buf, "TaxStartDate",correctDate(bLPrpCPcarshipTax.getArr(0).getPayStartDate()));
        		addNode(buf, "TaxEndDate",correctDate(bLPrpCPcarshipTax.getArr(0).getPayEndDate()));
        		addNode(buf, "UnitRate",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getUnitRate());
        		addNode(buf, "AnnualTaxAmount",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getAnnualTaxAmount());
        		addNode(buf, "TaxDue",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxDue());
         		addNode(buf, "OverDue",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getOverDue());
         		
         		addNode(buf, "TotalAmount",""+TotalAmount);
         		
            	buf.append("<Paid>");
            	addNode(buf, "TaxDepartmentCode", bLPrpCPcarshipTax.getArr(0).getTaxComCode());
            	addNode(buf, "TaxDepartment", bLPrpCPcarshipTax.getArr(0).getTaxComName());
                addNode(buf, "TaxDocumentNumber", bLPrpCPcarshipTax.getArr(0).getPaidFreeCertificate());
            	buf.append("</Paid>");
            }
            addNode(buf, "TaxStartDate", correctDate(bLPrpCPcarshipTax.getArr(0).getPayStartDate()));
            addNode(buf, "TaxEndDate", correctDate(bLPrpCPcarshipTax.getArr(0).getPayEndDate()));
            buf.append("</CurrentTaxDue>");
        }
        
        else if("6".equals(TaxRelifFlag)){
		  if(utiPower.checkCarShipTaxJZ(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
       		buf.append("<CurrentTaxDue>");
       		String strTaxLocationCode = "";
       		String strTaxUnitTypeCode = "";
       		if(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxLocationCode()!=null){
       			strTaxLocationCode=blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxLocationCode();
       		}
       		if(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxUnitTypeCode()!=null){
       			strTaxUnitTypeCode=blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxUnitTypeCode();
       		}
       		addNode(buf, "TaxLocationCode",strTaxLocationCode);
       		addNode(buf, "TaxUnitTypeCode",strTaxUnitTypeCode);
       		addNode(buf, "TaxStartDate",correctDate(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxStartDate()));
       		addNode(buf, "TaxEndDate",correctDate(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxEndDate()));
       		addNode(buf, "UnitRate",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getUnitRate());
        	addNode(buf, "AnnualTaxAmount",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getAnnualTaxAmount());
        	addNode(buf, "TaxDue",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getTaxDue());
        	addNode(buf, "OverDue",blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getOverDue());
        	addNode(buf, "TotalAmount",""+TotalAmount);
       		buf.append("</CurrentTaxDue>");
		  }
       	}
        
        if(!utiPower.checkCarShipTaxJZ(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
        	addNode(buf, "AnnualTaxDue", bLPrpCPcarshipTax.getArr(0).getTaxActual());
            addNode(buf, "SumTaxDefault", bLPrpCPcarshipTax.getArr(0).getPreviousPay());
            addNode(buf, "SumOverdue", bLPrpCPcarshipTax.getArr(0).getLateFee());
            addNode(buf, "SumTax", bLPrpCPcarshipTax.getArr(0).getSumPayTax());
        }else{
        	buf.append("<TaxAmount>");
    		addNode(buf, "TaxAmount_Flag", "4");
    		addNode(buf, "AnnualTaxDue", blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getAnnualTaxDue());
            addNode(buf, "SumTaxDefault", blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getSumTaxDefault());
            addNode(buf, "SumOverdue", blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getSumOverdue());
            addNode(buf, "SumTax", blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getSumTax());
            buf.append("</TaxAmount>");
    		
        }
        if(utiPower.checkCarShipTaxJZ(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate())){
		    
        	if(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getDeclareStatusIA()==null
        			||"".equals(blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getDeclareStatusIA())){
        		if(blEndorse.getBLPrpPcarshipTax().getSize()>0){
        			if("6".equals(blEndorse.getBLPrpPcarshipTax().getArr(0).getTaxRelifFlag())
            				&& "4".equals(bLPrpCPcarshipTax.getArr(0).getTaxRelifFlag())){
            		addNode(buf, "Declare_Status_IA", "1");
            		}else if("6".equals(blEndorse.getBLPrpPcarshipTax().getArr(0).getTaxRelifFlag())
                			&& !"6".equals(bLPrpCPcarshipTax.getArr(0).getTaxRelifFlag())){
            		addNode(buf, "Declare_Status_IA", "0");
            		}
        		}else{
        			addNode(buf, "Declare_Status_IA", "");
        		}
        	}else{
        		addNode(buf, "Declare_Status_IA", blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getDeclareStatusIA());
        	}
			
    		addNode(buf, "Calc_Tax_Flag", blEndorse.getBLCICarShipTaxQGEndorse().getArr(0).getCalcTaxFlag());
        }
        
        
        if(utiPower.checkCarShipTaxJZ(blEndorse.getBLPrpPmain().getArr(0).getComCode(), blEndorse.getBLPrpPmain().getArr(0).getOperateDate())
                && utiPower.checkCarShipTaxJZDelinquent(blEndorse.getBLPrpPmain().getArr(0).getComCode())){
            int carshiptaxSize = blEndorse.getBLCICarShipTaxQGEndorse().getSize();
            for(int i = 0; i < carshiptaxSize; i++){
                if("1".equals(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getTaxType())){
                    buf.append("<DelinquentTaxDue>");
                    String strTaxLocationCodeD="";
                    String strTaxUnitTypeCodeD="";
                    if(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getTaxLocationCode()!=null){
                        strTaxLocationCodeD=blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getTaxLocationCode();
                    }
                    if(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getTaxUnitTypeCode()!=null){
                        strTaxUnitTypeCodeD=blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getTaxUnitTypeCode();
                    }
                    addNode(buf, "TaxLocationCode", strTaxLocationCodeD);
                    addNode(buf, "TaxUnitTypeCode", strTaxUnitTypeCodeD);
                    addNode(buf, "TaxStartDate", correctDate(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getTaxStartDate()));
                    addNode(buf, "TaxEndDate", correctDate(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getTaxEndDate()));
                    addNode(buf, "UnitRate", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getUnitRate()));
                    addNode(buf, "AnnualTaxAmount", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getAnnualTaxAmount()));
                    addNode(buf, "TaxDue", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getTaxDue()));
                    addNode(buf, "ExceedDate", correctDate(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getExceedDate()));
                    addNode(buf, "ExceedDaysCount", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getExceedDaysCount()));
                    addNode(buf, "OverDue", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getOverDue()));
                    addNode(buf, "TotalAmount", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getTotalAmount()));
                    buf.append("<Derate>");
                    addNode(buf, "DeductionDueCode", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getDeductionDueCode()));
                    addNode(buf, "DeductionDueType", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getDeductionDueType()));
                    if(Double.parseDouble(ChgData.chgStrZero(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getDeductionDueProportion()))/100==0){
                        addNode(buf, "DeductionDueProportion", "");
                    }else{
                        addNode(buf, "DeductionDueProportion", ""+(Double.parseDouble(ChgData.chgStrZero(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getDeductionDueProportion()))/100));
                    }
                    if(Double.parseDouble(ChgData.chgStrZero(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getDeduction()))==0){
                        addNode(buf, "Deduction", "");
                    }else{
                        addNode(buf, "Deduction", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getDeduction()));
                    }
                    addNode(buf, "DeductionDocumentNumber", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getDeductionDocumentNumber()));
                    addNode(buf, "TaxDepartmentCode", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getDeductionDepartmentCode()));
                    addNode(buf, "TaxDepartment", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getDeductionDepartment()));
                    buf.append("</Derate>");
                    buf.append("<Paid>");
                    addNode(buf, "TaxDepartmentCode", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getTaxDepartmentCode()));
                    addNode(buf, "TaxDepartment", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getTaxDepartment()));
                    addNode(buf, "TaxDocumentNumber", StringConvertor.changeNullToEmpty(blEndorse.getBLCICarShipTaxQGEndorse().getArr(i).getTaxDocumentNumber()));
                    buf.append("</Paid>");
                    buf.append("</DelinquentTaxDue>");
                }
            }
        }
        
        
        if(new UtiPower().isCarShipTaxNB(blEndorse.getBLPrpPmain().getArr(0).getComCode())){
        	buf.append("</VehicleTaxation_NB>");
        }else{
        	buf.append("</VehicleTaxation>");
        }
        
    }

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
