package com.sp.indiv.ci.interf;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sp.platform.bl.facade.BLPrpDcompanyFacade;
import com.sp.platform.dto.domain.PrpDcompanyDto;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpTitemCarSchema;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower; 
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;
public class ProposalCarShipTaxQueryEncoder {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
        
	}
	
	private static Logger logger = Logger.getLogger(ProposalCarShipTaxQueryEncoder.class);
	
	
	
	public void encode(BLProposal blProposal,StringBuffer buf, String part) throws Exception 
    {
    	if("BASE_PART".equals(part)){
    		encodeBasePart(blProposal, buf);
    	}else if("VehicleTaxation".equals(part)){
    		encodeVehicleTaxation(blProposal, buf);
    	}
    	
        logger.info("[XX查询车船税信息查询发送报文]:"+buf.toString());
        
    	
    }
	
	
	  /**
     * 编码
     * @return 车船税XX查询请求XML格式串
     * @throws Exception
     */
    public void encodeBasePart(BLProposal blProposal,StringBuffer buf) throws Exception 
    {
    	
        UtiPower utiPower = new UtiPower();
        if(utiPower.checkCICarshipTaxSH(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode()))
        {
        	addCarShipTaxSH(blProposal,buf);
        }
        else if(utiPower.checkCICarshipTaxBJ(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode()))
        {
        	addCarShipTaxBJ(blProposal,buf);
        }else if(SysConfig.getProperty("CIBASEPARTQGDPT").trim().indexOf(blProposal.getBLPrpTmain().getArr(0).getComCode().substring(0,2)) >= 0){
            addCarShipTaxQG(blProposal,buf);
        }
    }
    

    /**
     * 添加车船税信息
     * @param buf StringBuffer
     * @throws Exception 
     */
    protected void addCarShipTaxSH(BLProposal blProposal,StringBuffer buf) throws Exception 
    {
		
		
		
		
		
		
		
		
    	
    	String paidCertificate = "";
    	String freeCertificate = "";
    	if(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelifFlag().equals("W")){
    		paidCertificate = blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate();
    	}
    	else if(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelifFlag().equals("M")){
    		freeCertificate = blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate();
    	}
      	addNode(buf, "TAX_FLAG", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelifFlag());
      	
      	
      	if(new UtiPower().checkCarShipCategorySH(blProposal.getBLPrpTmain().getArr(0).getComCode())){
      		if("X01".equals(blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2())){
      			addNode(buf, "PO_CATEGORY",blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2());
      		}else{
      		addNode(buf, "PO_CATEGORY", encoderCarShipCategory(blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2(),
      			blProposal.getBLPrpTitemCar().getArr(0).getExhaustScale(),
      			blProposal.getBLPrpTitemCar().getArr(0).getSeatCount()));
      		}
      	}else{
      		addNode(buf, "PO_CATEGORY", encoderCategory(blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2()));
      	}
      	
      	
      	addNode(buf, "PO_WEIGHT", blProposal.getBLPrpTcarshipTax().getArr(0).getCompleteKerbMass());
      	
      	
        
        
        String taxpayerIdentifier=blProposal.getBLPrpTcarshipTax().getArr(0).getTaxpayerIdentifier();
        String identifyNumber=blProposal.getBLPrpTcarshipTax().getArr(0).getIdentifyNumber();
        String strComCode = blProposal.getBLPrpTmain().getArr(0).getComCode();
        if(strComCode!=null&&strComCode.substring(0,2).equals("07")){
            if(taxpayerIdentifier!=null&&taxpayerIdentifier.equals("10")){
                
                if(identifyNumber!=null&&identifyNumber.indexOf("-")>-1&&identifyNumber.length()>9){
                   identifyNumber=identifyNumber.replaceFirst("-","");
                }else if(identifyNumber!=null&&identifyNumber.indexOf("_")>-1&&identifyNumber.length()>9){
                   identifyNumber=identifyNumber.replaceFirst("_","");
                }
            }
        }
        addNode(buf, "TAXPAYER_CERTI_TYPE", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxpayerIdentifier());
        addNode(buf, "TAXPAYER_CERTI_CODE", identifyNumber);
        
      	addNode(buf, "PAY_NO", paidCertificate);
      	addNode(buf, "FREE_NO", freeCertificate);
      	addNode(buf, "DEPARTMENT", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComName());
      	
      	addNode(buf,"TAX_FLAG_2008",blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar1());
      	
      	
      	if(new UtiPower().checkCarShipCategorySH(blProposal.getBLPrpTmain().getArr(0).getComCode())){
      		
      		addNode(buf,"CERTIFICATE_DATE",correctDate(blProposal.getBLPrpTitemCarExt().getArr(0).getBuyCarDate()));
      	    
      	}
      	
    }
    
    /**
     * 添加XXXXX车船税信息
     * @param blProposal
     * @param buf
     * @throws Exception
     */
    protected void addCarShipTaxBJ(BLProposal blProposal,StringBuffer buf) throws Exception{
    	String TaxRelifFlag = blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelifFlag();

    	addNode(buf, "PC_VEHICLE_CATEGORY", blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2());
    	addNode(buf, "OWNER", blProposal.getBLPrpTitemCar().getArr(0).getCarOwner());
    	addNode(buf, "LIMIT_LOAD_PERSON", blProposal.getBLPrpTitemCar().getArr(0).getSeatCount());
    	addNode(buf, "PO_WEIGHT", blProposal.getBLPrpTcarshipTax().getArr(0).getCompleteKerbMass());
    	
    	String intExhaustScale = new DecimalFormat("##0").format(Double.parseDouble(blProposal.getBLPrpTitemCar().getArr(0).getExhaustScale())*1000);
    	
    	addNode(buf, "EXHAUST_CAPACITY", intExhaustScale);
    	addNode(buf, "VEHICLE_REGISTER_DATE", correctDate(blProposal.getBLPrpTitemCar().getArr(0).getEnrollDate()));
    	addNode(buf, "CERTIFICATE_DATE", correctDate(blProposal.getBLPrpTcarshipTax().getArr(0).getCertificateDate()));
    	if("3".equals(TaxRelifFlag) || "2".equals(TaxRelifFlag)){
    	
    		addNode(buf, "PAY_TAX_FLAG", "1");
    	}else{
	    	addNode(buf, "PAY_TAX_FLAG", "0");
    	}
    	
    	String currentDate=new ChgDate().getCurrentTime("yyyy-MM-dd");
    	if(new UtiPower().checkCarShipTaxBJ(blProposal.getBLPrpTmain().getArr(0).getComCode(), currentDate)){
    		String licenseNo = blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo().trim().substring(0,1);
    		
    		String iLicenseNo = blProposal.getBLPrpTitemCar().getArr(0).getLicenseNo().trim();
    		PubTools pubTools = new PubTools();
    		String strEcdemicVehicleFlag = pubTools.checkEcdemicVehicleFlag(iLicenseNo);
    		if("1".equals(strEcdemicVehicleFlag)){
    		
    			if("3".equals(TaxRelifFlag) || "2".equals(TaxRelifFlag)){
        			
        			if((null!=blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate())&&
        			   (!"".equals(blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate())))
        			  {
            			addNode(buf, "PAY_NO", "M" + blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate());  
        			  }	else{
    	    			addNode(buf, "PAY_NO", "");
        			  }
    				
        		}else{
    			addNode(buf, "PAY_NO", blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate());  
    			}
    			addNode(buf, "DEPARTMENT_NONLOCAL", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComCode());  
    			
    		}else{
    			addNode(buf, "PAY_NO", "");  
    			addNode(buf, "DEPARTMENT_NONLOCAL", "");  
    		}
    	}
    	
    }
    
    
    public void addCarShipTaxQG(BLProposal blProposal,StringBuffer buf) throws Exception{
    	
        
    	
    	
    	if(new UtiPower().savePlatformAdjustSwitchJS(blProposal.getBLPrpTmain().getArr(0).getComCode())
    			&& "0507".equals(blProposal.getBLPrpTmain().getArr(0).getRiskCode())
    			&& !"M".equals(blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode().substring(0,1))){
    		addNode(buf, "VEHICLE_STYLE", blProposal.getBLPrpTitemCar().getArr(0).getLicenseCategory());
    	}else
    	
    	if(blProposal.getBLPrpTcarshipTax().getSize()>0){
        	
        	boolean carShipTaxQGFlag = new UtiPower().checkCICarShipTaxQG(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode());
        	
        	if("M".equals(blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode().substring(0,1)) && !carShipTaxQGFlag){
        		 addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(generateTaxItem(blProposal.getBLPrpTitemCar().getArr(0)),Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount()))));
        	}else{
        		if(new UtiPower().CarCategorySwitch(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
        			addNode(buf, "VEHICLE_STYLE",blProposal.getBLPrpTitemCar().getArr(0).getLicenseCategory());
        		}else{
	        		if(blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2()!=null&&(!"".equals(blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2().trim()))){
	                    addNode(buf, "VEHICLE_STYLE", blProposal.getBLPrpTcarshipTax().getArr(0).getExtendChar2());
	        		}else{
	                    if("A".equals(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxItemCode()))
	                        addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxItemDetailCode(),Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount()))));
	                    else
	                        addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(blProposal.getBLPrpTcarshipTax().getArr(0).getTaxItemCode(),Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount()))));
	                }
        		}
        	}
        	
        	
        }else{
        	if(new UtiPower().CarCategorySwitch(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
        		addNode(buf, "VEHICLE_STYLE",blProposal.getBLPrpTitemCar().getArr(0).getLicenseCategory());
        	}else{
	            if("A".equals(blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode().substring(0,1)))
	                addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(generateTaxItem(blProposal.getBLPrpTitemCar().getArr(0)),Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount()))));
	            else
	                addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(generateTaxItem(blProposal.getBLPrpTitemCar().getArr(0)),Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount()))));
        	}
        }   
        
        
        addNode(buf, "LIMIT_LOAD_PERSON", blProposal.getBLPrpTitemCar().getArr(0).getSeatCount());
         
    	
        DecimalFormat ideciamlFormat = new DecimalFormat("0");
	addNode(buf, "LIMIT_LOAD",ideciamlFormat.format(Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getTonCount()))*1000));
	
    	String strCarKindCode = blProposal.getBLPrpTitemCar().getArr(0).getCarKindCode();
        
        
        if(blProposal.getBLPrpTcarshipTax().getSize()>0){    
            if(!"".equals(blProposal.getBLPrpTcarshipTax().getArr(0).getCompleteKerbMass())){
               addNode(buf, "WHOLE_WEIGHT", blProposal.getBLPrpTcarshipTax().getArr(0).getCompleteKerbMass());
            }else{
               if(!"".equals(blProposal.getBLPrpTitemCar().getArr(0).getCompleteKerbMass()))
                   addNode(buf, "WHOLE_WEIGHT", ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getCompleteKerbMass()));
               else
                  addNode(buf, "WHOLE_WEIGHT", "0");
            }
        }else{
            addNode(buf, "WHOLE_WEIGHT", ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getCompleteKerbMass()));
        }
        
    	addNode(buf, "DISPLACEMENT",""+(new Double(1000*Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getExhaustScale()))).intValue()));
    	addNode(buf, "POWER", ""+(new Double(1000*Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTitemCar().getArr(0).getExhaustScale()))).intValue()));
    	
    	if(!new UtiPower().checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(),blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    		addNode(buf, "OWNER_NAME", blProposal.getBLPrpTitemCar().getArr(0).getCarOwner());
        	addNode(buf, "OWNER_TYPE", "");
    	}else{
    		
    		if(new UtiPower().checkSinoCommission(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    			addNode(buf, "OWNER_NAME", blProposal.getBLPrpTitemCar().getArr(0).getCarOwner());
            	addNode(buf, "OWNER_TYPE", "");
    		}
    		
    		addNode(buf, "FUEL_TYPE", blProposal.getBLPrpTitemCarExt().getArr(0).getFuelType());
    	}
    	
    }
    
    public void encodeVehicleTaxation(BLProposal blProposal,StringBuffer buf) throws Exception{
    	UtiPower utiPower = new UtiPower();
    	
    	if(utiPower.checkCICarShipTaxQG(blProposal.getBLPrpTmain().getArr(0).getRiskCode(), blProposal.getBLPrpTmain().getArr(0).getComCode())
    			||utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    	
    		addVehicleTaxationQG(blProposal,buf);
        }
    }
    
    public void addVehicleTaxationQG(BLProposal blProposal,StringBuffer buf) throws Exception{
    	String TaxRelifFlag = blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelifFlag();
    	
    	if(new UtiPower().checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    		if("6".equals(TaxRelifFlag)){
    			TaxRelifFlag="1";
    		}
    	}
    	
        BLPrpDcompanyFacade dcompany  = new BLPrpDcompanyFacade();
        PrpDcompanyDto prpDcompanyDto = dcompany.findByPrimaryKey(blProposal.getBLPrpTmain().getArr(0).getComCode());
        
        UtiPower utiPower = new UtiPower();
        
        if(utiPower.isCarShipTaxNB(blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	buf.append("<VehicleTaxation_NB>");
        }else{
        	buf.append("<VehicleTaxation>");
        }
        
    	addNode(buf, "TaxTermTypeCode", "08");
    	addNode(buf, "TaxConditionCode", taxRelifFlagEncoder(TaxRelifFlag));
    	addNode(buf, "TaxRegistryNumber", prpDcompanyDto.getTaxRegisterNo());
    	addNode(buf, "TaxPayerName", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxpayerName());
    	addNode(buf, "TaxPayerIdentificationCode", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxpayerIdentifier());
    	if("1".equals(TaxRelifFlag) ){
    		if(!utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    		     buf.append("<CurrentTaxDue>");
    		     addNode(buf, "DeclareDate", correctDate(blProposal.getBLPrpTmain().getArr(0).getOperateDate()));
    		     buf.append("</CurrentTaxDue>");
    		}
    	}else if("3".equals(TaxRelifFlag)){
    		
    		buf.append("<CurrentTaxDue>");
    		if(!utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    			addNode(buf, "DeclareDate", correctDate(blProposal.getBLPrpTmain().getArr(0).getOperateDate()));
    		}
    		buf.append("<Derate>");
    		addNode(buf, "DeductionDueCode", relifReasonEncoder(blProposal.getBLPrpTcarshipTax().getArr(0).getRelifReason()));
    		addNode(buf, "DeductionDueType", baseTaxationEncoder(blProposal.getBLPrpTcarshipTax().getArr(0).getBaseTaxation()));
    		
    		if("A".equals(baseTaxationEncoder(blProposal.getBLPrpTcarshipTax().getArr(0).getBaseTaxation()))){
    			addNode(buf, "DeductionDueProportion", "");
    		}else{
    			addNode(buf, "DeductionDueProportion", ""+(Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTcarshipTax().getArr(0).getFreeRate()))/100));
    		}
    		if("P".equals(baseTaxationEncoder(blProposal.getBLPrpTcarshipTax().getArr(0).getBaseTaxation()))){
    			addNode(buf, "Deduction", "");
    		}else{
    			addNode(buf, "Deduction", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxRelief());
    		}
    		
    		addNode(buf, "DeductionDocumentNumber", blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate());
    		addNode(buf, "TaxDepartmentCode", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComCode());
    		addNode(buf, "TaxDepartment", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComName());
    		buf.append("</Derate>");
    		buf.append("</CurrentTaxDue>");
    	}else if("2".equals(TaxRelifFlag)){
    		buf.append("<CurrentTaxDue>");
    		buf.append("<Derate>");
    		addNode(buf, "DeductionDueCode", relifReasonEncoder(blProposal.getBLPrpTcarshipTax().getArr(0).getRelifReason()));
    		addNode(buf, "DeductionDueType", baseTaxationEncoder(blProposal.getBLPrpTcarshipTax().getArr(0).getBaseTaxation()));
    		
    		if((Double.parseDouble(ChgData.chgStrZero(blProposal.getBLPrpTcarshipTax().getArr(0).getFreeRate()))/100)==0){
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
    		buf.append("</CurrentTaxDue>");
    	}else if("4".equals(TaxRelifFlag)){
            
    		buf.append("<CurrentTaxDue>");
    		if(!utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    			addNode(buf, "DeclareDate", correctDate(blProposal.getBLPrpTmain().getArr(0).getOperateDate()));
    			addNode(buf, "TaxDepartment", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComName());
        		addNode(buf, "TaxDocumentNumber", blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate());
    		}
    		addNode(buf, "TaxStartDate", correctDate(blProposal.getBLPrpTcarshipTax().getArr(0).getPayStartDate()));
    		addNode(buf, "TaxEndDate", correctDate(blProposal.getBLPrpTcarshipTax().getArr(0).getPayEndDate()));
    		if(utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    			buf.append("<Paid>");
    			addNode(buf, "TaxDepartmentCode", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComCode());
    			addNode(buf, "TaxDepartment", blProposal.getBLPrpTcarshipTax().getArr(0).getTaxComName());
        		addNode(buf, "TaxDocumentNumber", blProposal.getBLPrpTcarshipTax().getArr(0).getPaidFreeCertificate());
    			buf.append("</Paid>");
    		}
    		buf.append("</CurrentTaxDue>");
    	}
    	
    	
    	if(utiPower.checkCarShipTaxJZ(blProposal.getBLPrpTmain().getArr(0).getComCode(), blProposal.getBLPrpTmain().getArr(0).getOperateDate())){
    		buf.append("<TaxAmount>");
    		addNode(buf, "TaxAmount_Flag", "1");
    		addNode(buf, "SumTax", "0");
    		buf.append("</TaxAmount>");
    	}
    	
    	
    	if(utiPower.isCarShipTaxNB(blProposal.getBLPrpTmain().getArr(0).getComCode())){
    		buf.append("</VehicleTaxation_NB>");
    	}else{
    		buf.append("</VehicleTaxation>");
    	}
    	
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
    
    public static String encoderCategory(String licenseCategory)   
	{
		String Category ="";
		String Category100 ="D11、D12、K11、K12、K13、K14、K15";
		String Category200 ="K21、K22、K23、K24、K25";
		String Category300 ="K31、K32、K33";
		String Category400 ="K41、K42、K43";
		String Category500 ="M11、M12、M13、M14、M15、M21";
		String Category600 ="M22";
		String Category700 ="B11、B12、B13、B14、B15、B16、B17、B21、B22、B23、B24、B25、B26、B27、B31、B32、B33、B34、B35、G11、G12、G13、G14、G15、G16、G21、G22、G23、G24、G25、G26、G31、G32、G33、G34、G35、H11、H12、H13、H14、H15、H16、H17、H18、H21、H22、H23、H24、H25、H26、H27、H28、H31、H32、H33、H34、H35、H37、H38、H41、H42、H43、H44、H45、H46、Q11、Q21、Q31";
        
        
        
        
        
        
		String Category800 ="J11、J12、J13、N11、N21、N22、N23、N24、T11、T21、T31、T32、T33、Z11、Z21、Z31、Z41、Z51、Z71、H51、H52、H53、H54";
		
        if(licenseCategory==null||licenseCategory.length()==0)
		{
			Category = ""; 
		}
		else
		{
			if(Category100.indexOf(licenseCategory)>=0)
			{
				Category = "100";
			}
			else if(Category200.indexOf(licenseCategory)>=0)
			{
				Category = "200";
			}
			else if(Category300.indexOf(licenseCategory)>=0)
			{
				Category = "300";
			}
			else if(Category400.indexOf(licenseCategory)>=0)
			{
				Category = "400";
			}
			else if(Category500.indexOf(licenseCategory)>=0)
			{
				Category = "500";
			}
			else if(Category600.indexOf(licenseCategory)>=0)
			{
				Category = "600";
			}
			else if(Category700.indexOf(licenseCategory)>=0)
			{
				Category = "700";
			}
			else if(Category800.indexOf(licenseCategory)>=0)
			{
				Category = "800";
			}
		}
		
		return Category;
	}
    
    public static String encoderCarShipCategory(String licenseCategory,String exhaustScale,String seatCount){
    	
    	String Category = "";
    	String Categoryke = "K11、K12、K13、K14、K15、K21、K22、K23、K24、K25、K31、K32、K33";
    	String CategoryHuo = "N11、N21、N22、N23、N24、H11、H12、H13、H14、H15、H16、H17、H18、H21、H22、H23、H24、H25、H26、H27、H28、H31、H32、H33、H34、H35、H37、H38、H41、H42、H43、H44、H45、H46、Q11、Q21、Q31、H51、H52、H53、H54";
    	String CategoryGua = "B11、B12、B13、B14、B15、B16、B17、B21、B22、B23、B24、B25、B26、B27、B31、B32、B33、B34、B35、G11、G12、G13、G14、G15、G16、G21、G22、G23、G24、G25、G26、G31、G32、G33、G34、G35";
    	String categoryZuo = "J11、J12、J13、Z11、Z21、Z31、Z41、Z51、Z71、J11、J12、J13、T11、T21、T31、T32、T33";
    	String CategoryMo1 = "M11、M12、M13、M14、M15、M21";
    	String CategoryMo2 = "M22";
    	int intSeatCount = Integer.parseInt(seatCount);
    	float flExhaustScale = Float.parseFloat(exhaustScale);
    	if(licenseCategory==null||licenseCategory.length()==0)
		{
			Category = ""; 
		}
		else
		{
			if(Categoryke.indexOf(licenseCategory)>=0&&intSeatCount>0 && intSeatCount<=9 && flExhaustScale<=1.0 ){
				Category = "K01";
			}else if (Categoryke.indexOf(licenseCategory)>=0&&intSeatCount>0 && intSeatCount<=9 && flExhaustScale>1.0 && flExhaustScale<=1.6) {
				Category = "K02";
			}else if (Categoryke.indexOf(licenseCategory)>=0&&intSeatCount>0 && intSeatCount<=9 && flExhaustScale>1.6 && flExhaustScale<=2.0) {
				Category = "K03";
			}else if (Categoryke.indexOf(licenseCategory)>=0&&intSeatCount>0 && intSeatCount<=9 && flExhaustScale>2.0 && flExhaustScale<=2.5) {
				Category = "K04";
			}else if (Categoryke.indexOf(licenseCategory)>=0&&intSeatCount>0 && intSeatCount<=9 && flExhaustScale>2.5 && flExhaustScale<=3.0) {
				Category = "K05";
			}else if (Categoryke.indexOf(licenseCategory)>=0&&intSeatCount>0 && intSeatCount<=9 && flExhaustScale>3.0 && flExhaustScale<=4.0) {
				Category = "K06";
			}else if (Categoryke.indexOf(licenseCategory)>=0&&intSeatCount>0 && intSeatCount<=9 && flExhaustScale>4.0) {
				Category = "K07";
			}else if (Categoryke.indexOf(licenseCategory)>=0&&intSeatCount>9 && intSeatCount<20) {
				Category = "K08";
			}else if ((Categoryke.indexOf(licenseCategory)>=0&&intSeatCount>=20)||"D11".equals(licenseCategory)||"D12".equals(licenseCategory)) {
				Category = "K09";
			}else if (CategoryHuo.indexOf(licenseCategory)>=0) {
				Category = "H01";
			}else if (CategoryGua.indexOf(licenseCategory)>=0) {
				Category = "G01";
			}else if (categoryZuo.indexOf(licenseCategory)>=0) {
				Category = "Z01";
			}else if (CategoryMo1.indexOf(licenseCategory)>=0) {
				Category = "M01";
			}else if (CategoryMo2.indexOf(licenseCategory)>=0) {
				Category = "M02";
			}
		}
    	return Category;
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
    
    public String vehicleStyleEncoder(String vehicleStyleEncoder,double TonCount){
         if("A0".equals(vehicleStyleEncoder)){
            return "K33";
        }else if("D".equals(vehicleStyleEncoder)){
            return "H51";
        }else if("H".equals(vehicleStyleEncoder)){
            if(TonCount<6)
               return "H31";
            else if(TonCount>=6&&TonCount<14)
               return "H21";
            else
               return "H11";
        }else if("K".equals(vehicleStyleEncoder)){
            return "H31";
        
        }else if("M1".equals(vehicleStyleEncoder)){
            return "M21";
        }else if("M5".equals(vehicleStyleEncoder)){
            return "M11";
        }else if("M6".equals(vehicleStyleEncoder)){
            return "M15";
        
        }else if("S".equals(vehicleStyleEncoder)){
            return "N11";
        }else if("T".equals(vehicleStyleEncoder)){
            return "J11";
        }else if("Z".equals(vehicleStyleEncoder)){
            return "Z11";
        }else if("A1".equals(vehicleStyleEncoder)){
            return "K31";
        }else if("A2".equals(vehicleStyleEncoder)){
            return "K21";
        }else if("A3".equals(vehicleStyleEncoder)){
            return "K11";
        
        }else if("A4,A5,A6,A7,A8,A9,A10".indexOf((vehicleStyleEncoder))>-1){
            return "K31";
        }else if("G".equals(vehicleStyleEncoder)){
            return "H11";
        }
        
        
        else if ("W".equals(vehicleStyleEncoder)){
        	return "X99";
        }
        
        return vehicleStyleEncoder;
    }
    
    public String generateTaxItem(PrpTitemCarSchema prpTitemCarSchema)
    {
        String carKindCode  = prpTitemCarSchema.getCarKindCode();
        String seatCount    = prpTitemCarSchema.getSeatCount();
        String exhaustScale = prpTitemCarSchema.getExhaustScale();
        String taxItemCode = "";
        String taxItemDetailCode = "";
        
        if(carKindCode == null || "".equals(carKindCode.trim())){
            return "";
        }
        
        else if("H4".equals(carKindCode)){
            taxItemCode = "K";
        }
        else if("A".equals(carKindCode.substring(0,1))) {
            if((seatCount == null || "".equals(seatCount.trim()) || exhaustScale == null || "".equals(exhaustScale.trim()))){
                return "";
            }
            else if(Double.parseDouble(exhaustScale) <=1 ){
                taxItemDetailCode = "A0";
            }
            else if(Integer.parseInt(seatCount) <= 9){
                taxItemDetailCode = "A1";
            }
            else if(Integer.parseInt(seatCount) > 9 && Integer.parseInt(seatCount) < 20){
                taxItemDetailCode = "A2";
            }
            else if(Integer.parseInt(seatCount) >= 20){
                taxItemDetailCode = "A3";
            }
            taxItemCode = "A";
        }
        else if("G0,G1,G2,H0,TM,TP,TQ,TR,TS,TT,H3,H5,H6,H7,H8".indexOf(carKindCode) > -1 || "T11".equals(carKindCode) || "T12".equals(carKindCode)){
            taxItemCode = "H";
        }
        
        
        else if("M".equals(carKindCode.substring(0,1))){
            taxItemCode = carKindCode;
        }
        
        else if("T2,TF,TG,TH,TI,TJ".indexOf(carKindCode) > -1){
            taxItemCode = "T";
        }
        else if("G3,T1,T3,T4,T5,T6,T7,T8,T9,TC,TD,TE,TK,TL,TN,TO,TU,TV,TW,TX,TY,TZ,T10".indexOf(carKindCode) > -1){
            taxItemCode = "Z";
        }
        
        else if("H1,H11,H12".indexOf(carKindCode) > -1){
            taxItemCode = "D";
        }
        else if("H2,H21,H22".indexOf(carKindCode) > -1){
            taxItemCode = "S";
        }
        
        
        else if("J".equals(carKindCode.substring(0,1))){
        
            taxItemCode = "W";
        }
        
        if("A".equals(carKindCode.substring(0,1)))
            return taxItemDetailCode;
        else
            return taxItemCode;
       
    }
    
    
}
