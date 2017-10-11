package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义CICarShipTaxDemand的数据传输对象类
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-11-21</p>
 * @author DtoGenerator
 * @version 1.0
 */
public class CICarshipTaxDemandSchema implements Serializable{
    
    private String ProposalNo = "";
    
    private String DemandNo = "";
    
    private String PolicyNo = "";
    
    private String TaxNo = "";
    
    private String TaxFlag = "";
    
    private String LicenseCategory = "";
    
    private String CompleteKerbMass = "";
    
    private String TaxpayerCertiType = "";
    
    private String IdentifyNumber = "";
    
    private String PaidCertificate = "";
    
    private String FreeCertificate = "";
    
    private String TaxComCode = "";
    
    private String TaxComName = "";
    
    private String PayerCode = "";
    
    private String TaxAbateReason = "";
    
    private String TaxAbateAmount = "";
    
    private String TaxActual = "";
    
    private String PreviousPay = "";
    
    private String LateFee = "";
    
    private String SumTax = "";
    
    private String TaxType = "";
    
    private String Flag = "";
    
    private String CarNumber = "";
    
    private String PayStartDate = "";
    
    private String PayEndDate = "";
    
    
    private String CommissionTax = "";
    
    private String CertificateDate = "";
    
    
    private String OwnerName = "";
    
	/**
     * 构造函数
     */       
    public CICarshipTaxDemandSchema(){
    }

    /**
     * 设置属性XX单号
     * @param ProposalNo XX单号
     */
    public void setProposalNo(String ProposalNo){
        this.ProposalNo = Str.rightTrim(ProposalNo);
    }

    /**
     * 获取属性XX单号
     * @return XX单号
     */
    public String getProposalNo(){
        return Str.rightTrim(ProposalNo);
    }

    /**
     * 设置属性查询码
     * @param DemandNo 查询码
     */
    public void setDemandNo(String DemandNo){
        this.DemandNo = Str.rightTrim(DemandNo);
    }

    /**
     * 获取属性查询码
     * @return 查询码
     */
    public String getDemandNo(){
        return Str.rightTrim(DemandNo);
    }

    /**
     * 设置属性XX号
     * @param PolicyNo XX号
     */
    public void setPolicyNo(String PolicyNo){
        this.PolicyNo = Str.rightTrim(PolicyNo);
    }

    /**
     * 获取属性XX号
     * @return XX号
     */
    public String getPolicyNo(){
        return Str.rightTrim(PolicyNo);
    }

    /**
     * 设置属性车船税编码
     * @param TaxNo 车船税编码
     */
    public void setTaxNo(String TaxNo){
        this.TaxNo = Str.rightTrim(TaxNo);
    }

    /**
     * 获取属性车船税编码
     * @return 车船税编码
     */
    public String getTaxNo(){
        return Str.rightTrim(TaxNo);
    }

    /**
     * 设置属性缴税、减、免税标志
     * @param TaxFlag 缴税、减、免税标志
     */
    public void setTaxFlag(String TaxFlag){
        this.TaxFlag = Str.rightTrim(TaxFlag);
    }

    /**
     * 获取属性缴税、减、免税标志
     * @return 缴税、减、免税标志
     */
    public String getTaxFlag(){
        return Str.rightTrim(TaxFlag);
    }

    /**
     * 设置属性行驶证类型
     * @param LicenseCategory 行驶证类型
     */
    public void setLicenseCategory(String LicenseCategory){
        this.LicenseCategory = Str.rightTrim(LicenseCategory);
    }

    /**
     * 获取属性行驶证类型
     * @return 行驶证类型
     */
    public String getLicenseCategory(){
        return Str.rightTrim(LicenseCategory);
    }

    /**
     * 设置属性行驶证整备质量
     * @param CompleteKerbMass 行驶证整备质量
     */
    public void setCompleteKerbMass(String CompleteKerbMass){
        this.CompleteKerbMass = Str.rightTrim(CompleteKerbMass);
    }

    /**
     * 获取属性行驶证整备质量
     * @return 行驶证整备质量
     */
    public String getCompleteKerbMass(){
        return Str.rightTrim(CompleteKerbMass);
    }

    /**
     * 设置属性纳税人证件类型
     * @param TaxpayerCertiType 纳税人证件类型
     */
    public void setTaxpayerCertiType(String TaxpayerCertiType){
        this.TaxpayerCertiType = Str.rightTrim(TaxpayerCertiType);
    }

    /**
     * 获取属性纳税人证件类型
     * @return 纳税人证件类型
     */
    public String getTaxpayerCertiType(){
        return Str.rightTrim(TaxpayerCertiType);
    }

    /**
     * 设置属性纳税人证件号码
     * @param IdentifyNumber 纳税人证件号码
     */
    public void setIdentifyNumber(String IdentifyNumber){
        this.IdentifyNumber = Str.rightTrim(IdentifyNumber);
    }

    /**
     * 获取属性纳税人证件号码
     * @return 纳税人证件号码
     */
    public String getIdentifyNumber(){
        return Str.rightTrim(IdentifyNumber);
    }

    /**
     * 设置属性完税凭证号
     * @param PaidCertificate 完税凭证号
     */
    public void setPaidCertificate(String PaidCertificate){
        this.PaidCertificate = Str.rightTrim(PaidCertificate);
    }

    /**
     * 获取属性完税凭证号
     * @return 完税凭证号
     */
    public String getPaidCertificate(){
        return Str.rightTrim(PaidCertificate);
    }

    /**
     * 设置属性免税凭证号
     * @param FreeCertificate 免税凭证号
     */
    public void setFreeCertificate(String FreeCertificate){
        this.FreeCertificate = Str.rightTrim(FreeCertificate);
    }

    /**
     * 获取属性免税凭证号
     * @return 免税凭证号
     */
    public String getFreeCertificate(){
        return Str.rightTrim(FreeCertificate);
    }

    /**
     * 设置属性开具税务机关代码
     * @param TaxComCode 开具税务机关代码
     */
    public void setTaxComCode(String TaxComCode){
        this.TaxComCode = Str.rightTrim(TaxComCode);
    }

    /**
     * 获取属性开具税务机关代码
     * @return 开具税务机关代码
     */
    public String getTaxComCode(){
        return Str.rightTrim(TaxComCode);
    }

    /**
     * 设置属性开具税务机关名称
     * @param TaxComName 开具税务机关名称
     */
    public void setTaxComName(String TaxComName){
        this.TaxComName = Str.rightTrim(TaxComName);
    }

    /**
     * 获取属性开具税务机关名称
     * @return 开具税务机关名称
     */
    public String getTaxComName(){
        return Str.rightTrim(TaxComName);
    }

    /**
     * 设置属性纳税人识别代码
     * @param PayerCode 纳税人识别代码
     */
    public void setPayerCode(String PayerCode){
        this.PayerCode = Str.rightTrim(PayerCode);
    }

    /**
     * 获取属性纳税人识别代码
     * @return 纳税人识别代码
     */
    public String getPayerCode(){
        return Str.rightTrim(PayerCode);
    }

    /**
     * 设置属性减税原因
     * @param TaxAbateReason 减税原因
     */
    public void setTaxAbateReason(String TaxAbateReason){
        this.TaxAbateReason = Str.rightTrim(TaxAbateReason);
    }

    /**
     * 获取属性减税原因
     * @return 减税原因
     */
    public String getTaxAbateReason(){
        return Str.rightTrim(TaxAbateReason);
    }

    /**
     * 设置属性减税金额
     * @param TaxAbateAmount 减税金额
     */
    public void setTaxAbateAmount(String TaxAbateAmount){
        this.TaxAbateAmount = Str.rightTrim(TaxAbateAmount);
    }

    /**
     * 获取属性减税金额
     * @return 减税金额
     */
    public String getTaxAbateAmount(){
        return Str.rightTrim(TaxAbateAmount);
    }

    /**
     * 设置属性当年应缴
     * @param TaxActual 当年应缴
     */
    public void setTaxActual(String TaxActual){
        this.TaxActual = Str.rightTrim(TaxActual);
    }

    /**
     * 获取属性当年应缴
     * @return 当年应缴
     */
    public String getTaxActual(){
        return Str.rightTrim(TaxActual);
    }

    /**
     * 设置属性往年补缴
     * @param PreviousPay 往年补缴
     */
    public void setPreviousPay(String PreviousPay){
        this.PreviousPay = Str.rightTrim(PreviousPay);
    }

    /**
     * 获取属性往年补缴
     * @return 往年补缴
     */
    public String getPreviousPay(){
        return Str.rightTrim(PreviousPay);
    }

    /**
     * 设置属性滞纳金
     * @param LateFee 滞纳金
     */
    public void setLateFee(String LateFee){
        this.LateFee = Str.rightTrim(LateFee);
    }

    /**
     * 获取属性滞纳金
     * @return 滞纳金
     */
    public String getLateFee(){
        return Str.rightTrim(LateFee);
    }

    /**
     * 设置属性缴税合计
     * @param SumTax 缴税合计
     */
    public void setSumTax(String SumTax){
        this.SumTax = Str.rightTrim(SumTax);
    }

    /**
     * 获取属性缴税合计
     * @return 缴税合计
     */
    public String getSumTax(){
        return Str.rightTrim(SumTax);
    }

    /**
     * 设置属性标志字段 1：新XXXXX录入；2：XX补税
     * @param TaxType 标志字段 1：新XXXXX录入；2：XX补税
     */
    public void setTaxType(String TaxType){
        this.TaxType = Str.rightTrim(TaxType);
    }

    /**
     * 获取属性标志字段 1：新XXXXX录入；2：XX补税
     * @return 标志字段 1：新XXXXX录入；2：XX补税
     */
    public String getTaxType(){
        return Str.rightTrim(TaxType);
    }

    /**
     * 设置属性标志字段
     * @param Flag 标志字段
     */
    public void setFlag(String Flag){
        this.Flag = Str.rightTrim(Flag);
    }

    /**
     * 获取属性标志字段
     * @return 标志字段
     */
    public String getFlag(){
        return Str.rightTrim(Flag);
    }

    /**
     * 设置属性车辆编号
     * @param CarNumber 车辆编号
     */
    public void setCarNumber(String CarNumber){
        this.CarNumber = Str.rightTrim(CarNumber);
    }

    /**
     * 获取属性车辆编号
     * @return 车辆编号
     */
    public String getCarNumber(){
        return Str.rightTrim(CarNumber);
    }

    /**
     * 设置属性缴费起始日期
     * @param PayStartDate 缴费起始日期
     */
    public void setPayStartDate(String PayStartDate){
      ChgDate chgDate = new ChgDate(); 
      PayStartDate = chgDate.toFormat(PayStartDate);
      if  (PayStartDate == null || PayStartDate.length() == 0 ) 
      {
        this.PayStartDate = "";
      }else
      {
       this.PayStartDate = PayStartDate.trim().substring(0,10);
      }
    }

    /**
     * 获取属性缴费起始日期
     * @return 缴费起始日期
     */
    public String getPayStartDate(){
        return Str.rightTrim(PayStartDate);
    }

    /**
     * 设置属性缴费终止日期
     * @param PayEndDate 缴费终止日期
     */
    public void setPayEndDate(String PayEndDate){
      ChgDate chgDate = new ChgDate(); 
      PayEndDate = chgDate.toFormat(PayEndDate);
      if  (PayEndDate == null || PayEndDate.length() == 0 ) 
      {
        this.PayEndDate = "";
      }else
      {
       this.PayEndDate = PayEndDate.trim().substring(0,10);
      }
    }

    /**
     * 获取属性缴费终止日期
     * @return 缴费终止日期
     */
    public String getPayEndDate(){
        return Str.rightTrim(PayEndDate);
    }
    /**
     * 设置属性发证日期
     * @param PayEndDate 发证日期
     */
    public void setCertificateDate(String CertificateDate){
      ChgDate chgDate = new ChgDate(); 
      CertificateDate = chgDate.toFormat(CertificateDate);
      if  (CertificateDate == null || CertificateDate.length() == 0 ) 
      {
        this.CertificateDate = "";
      }else
      {
       this.CertificateDate = CertificateDate.trim().substring(0,10);
      }
    }

    /**
     * 获取属性发证日期
     * @return 发证日期
     */
    public String getCertificateDate(){
        return Str.rightTrim(CertificateDate);
    }
    
    
    /**获取属性是否属代征车船税车辆
	 * @return commissionTax
	 */
	public String getCommissionTax() {
		return Str.rightTrim(CommissionTax);
	}

	/**
	 * 设置属性是否属代征车船税车辆
	 * @param commissionTax 要设置的 commissionTax
	 */
	public void setCommissionTax(String commissionTax) {
		this.CommissionTax = Str.rightTrim(commissionTax);
	}
	

	
    public String getOwnerName() {
		return Str.rightTrim(OwnerName);
	}
    
    public void setOwnerName(String OwnerName) {
		this.OwnerName = Str.rightTrim(OwnerName);
	}
    
    /**
     * @param iSchema 对象CICarShipTaxDemandSchema
     */       
    public void setSchema(CICarshipTaxDemandSchema iSchema)
    {
        this.ProposalNo = iSchema.getProposalNo();
        this.DemandNo = iSchema.getDemandNo();
        this.PolicyNo = iSchema.getPolicyNo();
        this.TaxNo = iSchema.getTaxNo();
        this.TaxFlag = iSchema.getTaxFlag();
        this.LicenseCategory = iSchema.getLicenseCategory();
        this.CompleteKerbMass = iSchema.getCompleteKerbMass();
        this.TaxpayerCertiType = iSchema.getTaxpayerCertiType();
        this.IdentifyNumber = iSchema.getIdentifyNumber();
        this.PaidCertificate = iSchema.getPaidCertificate();
        this.FreeCertificate = iSchema.getFreeCertificate();
        this.TaxComCode = iSchema.getTaxComCode();
        this.TaxComName = iSchema.getTaxComName();
        this.PayerCode = iSchema.getPayerCode();
        this.TaxAbateReason = iSchema.getTaxAbateReason();
        this.TaxAbateAmount = iSchema.getTaxAbateAmount();
        this.TaxActual = iSchema.getTaxActual();
        this.PreviousPay = iSchema.getPreviousPay();
        this.LateFee = iSchema.getLateFee();
        this.SumTax = iSchema.getSumTax();
        this.TaxType = iSchema.getTaxType();
        this.Flag = iSchema.getFlag();
        this.CarNumber = iSchema.getCarNumber();
        this.PayStartDate = iSchema.getPayStartDate();
        this.PayEndDate = iSchema.getPayEndDate();
        
        this.CommissionTax = iSchema.getCommissionTax();
        this.CertificateDate = iSchema.getCertificateDate();
        
        
        this.OwnerName = iSchema.getOwnerName();
        
    }
}
