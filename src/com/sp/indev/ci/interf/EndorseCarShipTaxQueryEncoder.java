package com.sp.indiv.ci.interf;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sp.indiv.ci.blsvr.BLCICheckCarShipTaxDetailQG;
import com.sp.indiv.ci.dbsvr.DBCICheckCarShipTaxDetailQG;
import com.sp.platform.bl.facade.BLPrpDcompanyFacade;
import com.sp.platform.dto.domain.PrpDcompanyDto;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPrpCitemCar;
import com.sp.prpall.blsvr.cb.BLPrpCcarshipTax;
import com.sp.prpall.schema.PrpCitemCarSchema;
import com.sp.prpall.schema.PrpCcarshipTaxSchema;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.string.ChgData;

public class EndorseCarShipTaxQueryEncoder {

    /**
     * @param args
     */
    public static void main(String[] args) {
        

    }
    
	private static Logger logger = Logger.getLogger(EndorseCarShipTaxQueryEncoder.class);
	
    public void encode(BLPolicy blPolicy,StringBuffer buf, String part) throws Exception 
    {
        if("BASE_PART".equals(part)){
            encodeBasePart(blPolicy, buf);
        }else if("VehicleTaxation".equals(part)){
            encodeVehicleTaxation(blPolicy, buf);
        }
        
        logger.info("[批改查询车船税信息查询发送报文]:"+buf.toString());
        
    }
      /**
     * 编码
     * @return 车船税XX查询请求XML格式串
     * @throws Exception
     */
    public void encodeBasePart(BLPolicy blPolicy,StringBuffer buf) throws Exception 
    {
        
        UtiPower utiPower = new UtiPower();
        if(SysConfig.getProperty("CIBASEPARTQGDPT").trim().indexOf(blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0,2)) >= 0){
            addCarShipTaxQG(blPolicy,buf);
        }
    }  
    
    public void addCarShipTaxQG(BLPolicy blPolicy,StringBuffer buf) throws Exception{
    	
    	if(new UtiPower().savePlatformAdjustSwitchJS(blPolicy.getBLPrpCmain().getArr(0).getComCode())
    			&& "0507".equals(blPolicy.getBLPrpCmain().getArr(0).getRiskCode())
    			&&!"M".equals(blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode().substring(0,1))){
    		addNode(buf, "VEHICLE_STYLE", blPolicy.getBLPrpCitemCar().getArr(0).getLicenseCategory());
    	}else
    	
        
        
        
        if(blPolicy.getBLPrpCcarshipTax().getSize()>0){
        	
        	boolean carShipTaxQGFlag = new UtiPower().checkCICarShipTaxQG(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode());
        	
        	if("M".equals(blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode().substring(0,1)) && !carShipTaxQGFlag){
        		addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(generateTaxItem(blPolicy.getBLPrpCitemCar().getArr(0)),Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCitemCar().getArr(0).getTonCount()))));
        	}else{
        		
        		if(new UtiPower().CarCategorySwitch(blPolicy.getBLPrpCmain().getArr(0).getComCode(),blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
        			addNode(buf, "VEHICLE_STYLE", blPolicy.getBLPrpCitemCar().getArr(0).getLicenseCategory());
        		} else {
	        		if(blPolicy.getBLPrpCcarshipTax().getArr(0).getExtendChar2()!=null&&(!"".equals(blPolicy.getBLPrpCcarshipTax().getArr(0).getExtendChar2().trim())))
	                    addNode(buf, "VEHICLE_STYLE", blPolicy.getBLPrpCcarshipTax().getArr(0).getExtendChar2());
	                else{
	                    if("A".equals(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxItemCode()))
	                       addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxItemDetailCode(),Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCitemCar().getArr(0).getTonCount()))));
	                    else
	                       addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxItemCode(),Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCitemCar().getArr(0).getTonCount()))));
	                }
        		}
        		
        	}
            
        	
        }else{
        	if(new UtiPower().CarCategorySwitch(blPolicy.getBLPrpCmain().getArr(0).getComCode(),blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
    			addNode(buf, "VEHICLE_STYLE", blPolicy.getBLPrpCitemCar().getArr(0).getLicenseCategory());
    		} else {
	            if("A".equals(blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode().substring(0,1)))
	                addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(generateTaxItem(blPolicy.getBLPrpCitemCar().getArr(0)),Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCitemCar().getArr(0).getTonCount()))));
	            else
	                addNode(buf, "VEHICLE_STYLE", vehicleStyleEncoder(generateTaxItem(blPolicy.getBLPrpCitemCar().getArr(0)),Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCitemCar().getArr(0).getTonCount()))));
    		}
        }
        
        addNode(buf, "LIMIT_LOAD_PERSON", blPolicy.getBLPrpCitemCar().getArr(0).getSeatCount());
        addNode(buf, "LIMIT_LOAD", ""+(new Double(1000*Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCitemCar().getArr(0).getTonCount()))).intValue()));
        String strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
        
        if(blPolicy.getBLPrpCcarshipTax().getSize()>0){
            if(!"".equals(blPolicy.getBLPrpCcarshipTax().getArr(0).getCompleteKerbMass())){
            	
                addNode(buf, "WHOLE_WEIGHT", new DecimalFormat("0").format(Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCcarshipTax().getArr(0).getCompleteKerbMass()))));
                
            }else{
                if(!"".equals(blPolicy.getBLPrpCitemCar().getArr(0).getCompleteKerbMass()))
                   addNode(buf, "WHOLE_WEIGHT", ChgData.chgStrZero(blPolicy.getBLPrpCitemCar().getArr(0).getCompleteKerbMass()));
                else
                   addNode(buf, "WHOLE_WEIGHT", "0");
            }
        }else{
                addNode(buf, "WHOLE_WEIGHT", ChgData.chgStrZero(blPolicy.getBLPrpCitemCar().getArr(0).getCompleteKerbMass()));
        }
        addNode(buf, "DISPLACEMENT",""+(new Double(1000*Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCitemCar().getArr(0).getExhaustScale()))).intValue()));
        addNode(buf, "POWER", ""+(new Double(1000*Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCitemCar().getArr(0).getExhaustScale()))).intValue()));
        addNode(buf, "OWNER_NAME", blPolicy.getBLPrpCitemCar().getArr(0).getCarOwner());
        addNode(buf, "OWNER_TYPE", "");
        
        
        
        if(!new UtiPower().checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
        if(blPolicy.getBLCIInsureQuery().getSize() <=0){
        
        	addNode(buf, "IS_AMEND_TAX", "");
        }else{
            addNode(buf, "IS_AMEND_TAX", blPolicy.getBLCIInsureQuery().getArr(0).getIsAmendTax());
            }
        }else{
        	
        	if(blPolicy.getBLCIInsureQuery().getSize() <=0){
        		addNode(buf, "IS_AMEND_TAX", "");
        	}else{
        		addNode(buf, "IS_AMEND_TAX", blPolicy.getBLCIInsureQuery().getArr(0).getIsAmendTax());
        	}
        	
        	
        	BLPrpCitemCar blPrpCitemCar = new BLPrpCitemCar();
        	blPrpCitemCar.query("policyNo = '"+blPolicy.getBLPrpCitemCar().getArr(0).getPolicyNo()+"'");
        	if(!blPrpCitemCar.getArr(0).getEnrollDate().equals(blPolicy.getBLPrpCitemCar().getArr(0).getEnrollDate())){
        		addNode(buf, "VEHICLE_REGISTER_DATE", correctDate(blPolicy.getBLPrpCitemCar().getArr(0).getEnrollDate()));
        	}else {
        		addNode(buf, "VEHICLE_REGISTER_DATE", "");
			}
        	
        	addNode(buf, "FUEL_TYPE", blPolicy.getBLPrpCitemCarExt().getArr(0).getFuelType());
        }
		
        
    }
    
    public void encodeVehicleTaxation(BLPolicy blPolicy,StringBuffer buf) throws Exception{
        UtiPower utiPower = new UtiPower();
        
        if(utiPower.checkCICarShipTaxQG(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode())){
        	if("1".equals(blPolicy.getBLCIInsureQuery().getArr(0).getIsAmendTax())){
        		addVehicleTaxationQG(blPolicy,buf);
        	}
        }
        if(utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
        	
        	BLPrpCcarshipTax blPrpCcarshipTax = new BLPrpCcarshipTax();
        	blPrpCcarshipTax.getData(blPolicy.getBLPrpCmain().getArr(0).getPolicyNo()); 
        	if(blPrpCcarshipTax.getSize()>0){
        		PrpCcarshipTaxSchema prpCcarshipTaxSchema = blPrpCcarshipTax.getArr(0);
        		if(!("4".equals(prpCcarshipTaxSchema.getTaxRelifFlag())&&"".equals(prpCcarshipTaxSchema.getPaidFreeCertificate())
        				&&"".equals(prpCcarshipTaxSchema.getTaxComName()))){
        			addVehicleTaxationQG(blPolicy,buf);
        		}
        	}
        	
        }
        
    }
    
    public void addVehicleTaxationQG(BLPolicy blPolicy,StringBuffer buf) throws Exception{
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
        addNode(buf, "TaxRegistryNumber", prpDcompanyDto.getTaxRegisterNo());
        addNode(buf, "TaxPayerName", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxpayerName());
        addNode(buf, "TaxPayerIdentificationCode", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxpayerIdentifier());
        
        UtiPower utiPower = new UtiPower();
        
        if("1".equals(TaxRelifFlag) ){
        	if(!utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
        		 buf.append("<CurrentTaxDue>");
                 addNode(buf, "DeclareDate", correctDate(blPolicy.getBLPrpCmain().getArr(0).getOperateDate()));
                 buf.append("</CurrentTaxDue>");
        	}
        	if(utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
        		buf.append("<CurrentTaxDue>");
        		addNode(buf, "TaxLocationCode", "");
        		addNode(buf, "TaxStartDate", correctDate(blPolicy.getBLPrpCcarshipTax().getArr(0).getPayStartDate()));
        		addNode(buf, "TaxEndDate", correctDate(blPolicy.getBLPrpCcarshipTax().getArr(0).getPayEndDate()));
        		addNode(buf, "TaxUnitTypeCode", "");
        		addNode(buf, "UnitRate", "");
        		addNode(buf, "AnnualTaxAmount", "");
        		addNode(buf, "TaxDue","");
        		addNode(buf, "OverDue","");
        		addNode(buf, "TotalAmount","");
        		buf.append("</CurrentTaxDue>");
        	}
        }else if("3".equals(TaxRelifFlag)){
            
            buf.append("<CurrentTaxDue>");
            if(!utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
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
        }else if("2".equals(TaxRelifFlag)){
            buf.append("<CurrentTaxDue>");
            buf.append("<Derate>");
            addNode(buf, "DeductionDueCode", relifReasonEncoder(blPolicy.getBLPrpCcarshipTax().getArr(0).getRelifReason()));
            addNode(buf, "DeductionDueType", baseTaxationEncoder(blPolicy.getBLPrpCcarshipTax().getArr(0).getBaseTaxation()));
            if(utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
            	addNode(buf, "DeductionDueProportion",  "");
            }else{
            	addNode(buf, "DeductionDueProportion",  ""+(Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCcarshipTax().getArr(0).getFreeRate()))/100));
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
        }else if("4".equals(TaxRelifFlag)){
            buf.append("<CurrentTaxDue>");
            if(!utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
            	addNode(buf, "DeclareDate", correctDate(blPolicy.getBLPrpCmain().getArr(0).getOperateDate()));
            	addNode(buf, "TaxDepartment", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxComName());
                addNode(buf, "TaxDocumentNumber", blPolicy.getBLPrpCcarshipTax().getArr(0).getPaidFreeCertificate());
            }else{
            	buf.append("<Paid>");
            	addNode(buf, "TaxDepartmentCode", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxComCode());
            	addNode(buf, "TaxDepartment", blPolicy.getBLPrpCcarshipTax().getArr(0).getTaxComName());
                addNode(buf, "TaxDocumentNumber", blPolicy.getBLPrpCcarshipTax().getArr(0).getPaidFreeCertificate());
                buf.append("</Paid>");
            }
            addNode(buf, "TaxStartDate", correctDate(blPolicy.getBLPrpCcarshipTax().getArr(0).getPayStartDate()));
            addNode(buf, "TaxEndDate", correctDate(blPolicy.getBLPrpCcarshipTax().getArr(0).getPayEndDate()));
         
            buf.append("</CurrentTaxDue>");
        }
        
        if(utiPower.checkCarShipTaxJZ(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
        	String strValidNo = blPolicy.getBLCIInsureValid().getArr(0).getValidNo();
        	BLCICheckCarShipTaxDetailQG blciCheckCarShipTaxDetailQG = new BLCICheckCarShipTaxDetailQG();
        	blciCheckCarShipTaxDetailQG.getData(strValidNo);
        	addNode(buf,"Declare_Status_IA",blciCheckCarShipTaxDetailQG.getArr(0).getStatus());
        	addNode(buf, "Calc_Tax_Flag",blPolicy.getBLPrpCcarshipTax().getArr(0).getCalcTaxFlag());
        }
        
        
        
        if(new UtiPower().isCarShipTaxNB(blPolicy.getBLPrpCmain().getArr(0).getComCode())){
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
    
    
    
    public String generateTaxItem(PrpCitemCarSchema prpCitemCarSchema)
    {
        String carKindCode  = prpCitemCarSchema.getCarKindCode();
        String seatCount    = prpCitemCarSchema.getSeatCount();
        String exhaustScale = prpCitemCarSchema.getExhaustScale();
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
        else if("G0,G1,G2,H0,TM,TP,TQ,TR,TS,TT,H3,H5,H6,H7,H8".indexOf(carKindCode) > -1 || carKindCode == "T11" || carKindCode == "T12"){
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
