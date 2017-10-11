package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ����CICarShipTaxDemand�����ݴ��������
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
     * ���캯��
     */       
    public CICarshipTaxDemandSchema(){
    }

    /**
     * ��������XX����
     * @param ProposalNo XX����
     */
    public void setProposalNo(String ProposalNo){
        this.ProposalNo = Str.rightTrim(ProposalNo);
    }

    /**
     * ��ȡ����XX����
     * @return XX����
     */
    public String getProposalNo(){
        return Str.rightTrim(ProposalNo);
    }

    /**
     * �������Բ�ѯ��
     * @param DemandNo ��ѯ��
     */
    public void setDemandNo(String DemandNo){
        this.DemandNo = Str.rightTrim(DemandNo);
    }

    /**
     * ��ȡ���Բ�ѯ��
     * @return ��ѯ��
     */
    public String getDemandNo(){
        return Str.rightTrim(DemandNo);
    }

    /**
     * ��������XX��
     * @param PolicyNo XX��
     */
    public void setPolicyNo(String PolicyNo){
        this.PolicyNo = Str.rightTrim(PolicyNo);
    }

    /**
     * ��ȡ����XX��
     * @return XX��
     */
    public String getPolicyNo(){
        return Str.rightTrim(PolicyNo);
    }

    /**
     * �������Գ���˰����
     * @param TaxNo ����˰����
     */
    public void setTaxNo(String TaxNo){
        this.TaxNo = Str.rightTrim(TaxNo);
    }

    /**
     * ��ȡ���Գ���˰����
     * @return ����˰����
     */
    public String getTaxNo(){
        return Str.rightTrim(TaxNo);
    }

    /**
     * �������Խ�˰��������˰��־
     * @param TaxFlag ��˰��������˰��־
     */
    public void setTaxFlag(String TaxFlag){
        this.TaxFlag = Str.rightTrim(TaxFlag);
    }

    /**
     * ��ȡ���Խ�˰��������˰��־
     * @return ��˰��������˰��־
     */
    public String getTaxFlag(){
        return Str.rightTrim(TaxFlag);
    }

    /**
     * ����������ʻ֤����
     * @param LicenseCategory ��ʻ֤����
     */
    public void setLicenseCategory(String LicenseCategory){
        this.LicenseCategory = Str.rightTrim(LicenseCategory);
    }

    /**
     * ��ȡ������ʻ֤����
     * @return ��ʻ֤����
     */
    public String getLicenseCategory(){
        return Str.rightTrim(LicenseCategory);
    }

    /**
     * ����������ʻ֤��������
     * @param CompleteKerbMass ��ʻ֤��������
     */
    public void setCompleteKerbMass(String CompleteKerbMass){
        this.CompleteKerbMass = Str.rightTrim(CompleteKerbMass);
    }

    /**
     * ��ȡ������ʻ֤��������
     * @return ��ʻ֤��������
     */
    public String getCompleteKerbMass(){
        return Str.rightTrim(CompleteKerbMass);
    }

    /**
     * ����������˰��֤������
     * @param TaxpayerCertiType ��˰��֤������
     */
    public void setTaxpayerCertiType(String TaxpayerCertiType){
        this.TaxpayerCertiType = Str.rightTrim(TaxpayerCertiType);
    }

    /**
     * ��ȡ������˰��֤������
     * @return ��˰��֤������
     */
    public String getTaxpayerCertiType(){
        return Str.rightTrim(TaxpayerCertiType);
    }

    /**
     * ����������˰��֤������
     * @param IdentifyNumber ��˰��֤������
     */
    public void setIdentifyNumber(String IdentifyNumber){
        this.IdentifyNumber = Str.rightTrim(IdentifyNumber);
    }

    /**
     * ��ȡ������˰��֤������
     * @return ��˰��֤������
     */
    public String getIdentifyNumber(){
        return Str.rightTrim(IdentifyNumber);
    }

    /**
     * ����������˰ƾ֤��
     * @param PaidCertificate ��˰ƾ֤��
     */
    public void setPaidCertificate(String PaidCertificate){
        this.PaidCertificate = Str.rightTrim(PaidCertificate);
    }

    /**
     * ��ȡ������˰ƾ֤��
     * @return ��˰ƾ֤��
     */
    public String getPaidCertificate(){
        return Str.rightTrim(PaidCertificate);
    }

    /**
     * ����������˰ƾ֤��
     * @param FreeCertificate ��˰ƾ֤��
     */
    public void setFreeCertificate(String FreeCertificate){
        this.FreeCertificate = Str.rightTrim(FreeCertificate);
    }

    /**
     * ��ȡ������˰ƾ֤��
     * @return ��˰ƾ֤��
     */
    public String getFreeCertificate(){
        return Str.rightTrim(FreeCertificate);
    }

    /**
     * �������Կ���˰����ش���
     * @param TaxComCode ����˰����ش���
     */
    public void setTaxComCode(String TaxComCode){
        this.TaxComCode = Str.rightTrim(TaxComCode);
    }

    /**
     * ��ȡ���Կ���˰����ش���
     * @return ����˰����ش���
     */
    public String getTaxComCode(){
        return Str.rightTrim(TaxComCode);
    }

    /**
     * �������Կ���˰���������
     * @param TaxComName ����˰���������
     */
    public void setTaxComName(String TaxComName){
        this.TaxComName = Str.rightTrim(TaxComName);
    }

    /**
     * ��ȡ���Կ���˰���������
     * @return ����˰���������
     */
    public String getTaxComName(){
        return Str.rightTrim(TaxComName);
    }

    /**
     * ����������˰��ʶ�����
     * @param PayerCode ��˰��ʶ�����
     */
    public void setPayerCode(String PayerCode){
        this.PayerCode = Str.rightTrim(PayerCode);
    }

    /**
     * ��ȡ������˰��ʶ�����
     * @return ��˰��ʶ�����
     */
    public String getPayerCode(){
        return Str.rightTrim(PayerCode);
    }

    /**
     * �������Լ�˰ԭ��
     * @param TaxAbateReason ��˰ԭ��
     */
    public void setTaxAbateReason(String TaxAbateReason){
        this.TaxAbateReason = Str.rightTrim(TaxAbateReason);
    }

    /**
     * ��ȡ���Լ�˰ԭ��
     * @return ��˰ԭ��
     */
    public String getTaxAbateReason(){
        return Str.rightTrim(TaxAbateReason);
    }

    /**
     * �������Լ�˰���
     * @param TaxAbateAmount ��˰���
     */
    public void setTaxAbateAmount(String TaxAbateAmount){
        this.TaxAbateAmount = Str.rightTrim(TaxAbateAmount);
    }

    /**
     * ��ȡ���Լ�˰���
     * @return ��˰���
     */
    public String getTaxAbateAmount(){
        return Str.rightTrim(TaxAbateAmount);
    }

    /**
     * �������Ե���Ӧ��
     * @param TaxActual ����Ӧ��
     */
    public void setTaxActual(String TaxActual){
        this.TaxActual = Str.rightTrim(TaxActual);
    }

    /**
     * ��ȡ���Ե���Ӧ��
     * @return ����Ӧ��
     */
    public String getTaxActual(){
        return Str.rightTrim(TaxActual);
    }

    /**
     * �����������겹��
     * @param PreviousPay ���겹��
     */
    public void setPreviousPay(String PreviousPay){
        this.PreviousPay = Str.rightTrim(PreviousPay);
    }

    /**
     * ��ȡ�������겹��
     * @return ���겹��
     */
    public String getPreviousPay(){
        return Str.rightTrim(PreviousPay);
    }

    /**
     * �����������ɽ�
     * @param LateFee ���ɽ�
     */
    public void setLateFee(String LateFee){
        this.LateFee = Str.rightTrim(LateFee);
    }

    /**
     * ��ȡ�������ɽ�
     * @return ���ɽ�
     */
    public String getLateFee(){
        return Str.rightTrim(LateFee);
    }

    /**
     * �������Խ�˰�ϼ�
     * @param SumTax ��˰�ϼ�
     */
    public void setSumTax(String SumTax){
        this.SumTax = Str.rightTrim(SumTax);
    }

    /**
     * ��ȡ���Խ�˰�ϼ�
     * @return ��˰�ϼ�
     */
    public String getSumTax(){
        return Str.rightTrim(SumTax);
    }

    /**
     * �������Ա�־�ֶ� 1����XXXXX¼�룻2��XX��˰
     * @param TaxType ��־�ֶ� 1����XXXXX¼�룻2��XX��˰
     */
    public void setTaxType(String TaxType){
        this.TaxType = Str.rightTrim(TaxType);
    }

    /**
     * ��ȡ���Ա�־�ֶ� 1����XXXXX¼�룻2��XX��˰
     * @return ��־�ֶ� 1����XXXXX¼�룻2��XX��˰
     */
    public String getTaxType(){
        return Str.rightTrim(TaxType);
    }

    /**
     * �������Ա�־�ֶ�
     * @param Flag ��־�ֶ�
     */
    public void setFlag(String Flag){
        this.Flag = Str.rightTrim(Flag);
    }

    /**
     * ��ȡ���Ա�־�ֶ�
     * @return ��־�ֶ�
     */
    public String getFlag(){
        return Str.rightTrim(Flag);
    }

    /**
     * �������Գ������
     * @param CarNumber �������
     */
    public void setCarNumber(String CarNumber){
        this.CarNumber = Str.rightTrim(CarNumber);
    }

    /**
     * ��ȡ���Գ������
     * @return �������
     */
    public String getCarNumber(){
        return Str.rightTrim(CarNumber);
    }

    /**
     * �������Խɷ���ʼ����
     * @param PayStartDate �ɷ���ʼ����
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
     * ��ȡ���Խɷ���ʼ����
     * @return �ɷ���ʼ����
     */
    public String getPayStartDate(){
        return Str.rightTrim(PayStartDate);
    }

    /**
     * �������Խɷ���ֹ����
     * @param PayEndDate �ɷ���ֹ����
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
     * ��ȡ���Խɷ���ֹ����
     * @return �ɷ���ֹ����
     */
    public String getPayEndDate(){
        return Str.rightTrim(PayEndDate);
    }
    /**
     * �������Է�֤����
     * @param PayEndDate ��֤����
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
     * ��ȡ���Է�֤����
     * @return ��֤����
     */
    public String getCertificateDate(){
        return Str.rightTrim(CertificateDate);
    }
    
    
    /**��ȡ�����Ƿ�����������˰����
	 * @return commissionTax
	 */
	public String getCommissionTax() {
		return Str.rightTrim(CommissionTax);
	}

	/**
	 * ���������Ƿ�����������˰����
	 * @param commissionTax Ҫ���õ� commissionTax
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
     * @param iSchema ����CICarShipTaxDemandSchema
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
