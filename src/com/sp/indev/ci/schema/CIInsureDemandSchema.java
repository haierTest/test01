package com.sp.indiv.ci.schema;
  
import java.io.Serializable;

import weblogic.wtc.jatmi.islflds;

import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.UtiPower;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ����XX��ѯ����-CIInsureDemand�����ݴ�������� 
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author DtoGenerator
 * @version 1.0
 */
public class CIInsureDemandSchema implements Serializable{
    
    private String ProposalNo = "";
    
    private String DemandNo = "";
    
    private String PolicyNo = "";
    
    private String LicenseNo = "";
    
    private String LicenseType = "";
    
    private String UseNatureCode = "";
    
    private String FrameNo = "";
    
    private String EngineNo = "";
    
    private String LicenseColorCode = "";
    
    private String CarOwner = "";
    
    private String EnrollDate = "";
    
    private String MakeDate = "";
    
    private String SeatCount = "";
    
    private String TonCount = "";
    
    private String ValidCheckDate = "";
    
    private String ManufacturerName = "";
    
    private String ModelCode = "";
    
    private String BrandCName = "";
    
    private String BrandName = "";
    
    private String CarKindCode = "";
    
    private String CheckDate = "";
    
    private String EndValidDate = "";
    
    private String CarStatus = "";
    
    private String Haulage = "";
    
    private String StartDate = "";
    
    private String EndDate = "";
    
    private String Amount = "";
    
    private String Premium = "";
    
    private String BenchmarkPremium = "";
    
    private String PeccancyCoeff = "";
    
    private String ClaimCoeff = "";
    
    private String DriverCoeff = "";
    
    private String DistrictCoeff = "";
    
    private String CommissionRate = "";
    
    private String BasePremium = "";
    
    private String ComCode = "";
    
    private String OperatorCode = "";
    
    private String DemandTime = "";
    
    private String Remark = "";
    
    private String Flag = "";
    
    private String ProconfirmSequenceNo = "";   
    
    private String ProconfirmStartDate = "";    
    
    private String ProconfirmEndDate = "";      
    
    
    private String RestricFlag = "";
    
    private String PreferentialPremium = "";
    
    private String PreferentialFormula = "";
    
    private String PreferentialDay = "";
    
    
    
    private String LastYearStartDate = "";
    
    private String LastYearEndDate = "";
    
    private String ErrorMessage = ""; 
    
    
    
    private String BillDate 		= "";	
    private String ReinsureFlag 	= "";	
    private String LastBillDate 	= "";	
    private String RateFloatFlag 	= "";	
    private String PeccancyAdjustReason = "";	
    private String ClaimAdjustReason  	= "";	
    private String DriverRateReason  	= "";	
    private String DistrictRateReason  	= "";	
    
    
    
    private String SearchSequenceNo = "";
    private String SafeAdjust = "";
    private String NonclaimAdjust = "";
    private String LoyaltyAdjust = "";
    
    
    
    private String UseTypeSource = "";
    
    
    
    private String QueryPastDate = "" ;
    private String TransferDate = "";
    private String LoyaltyAdjustReason="";
    
    
    private String BusiLastYearEndDate = "";   
    private String BusiAdjustStart = "";        
    private String BusiAdjustEnd = "";          
    private String PoWeight = "";               
    
    
    private String ReCoverMsg = "";   
    
	
    private String PolicyCoeff = "";
    
    
    private String NoLoyaltyAdjustReason = "";	
    private String NoClaimAdjustReason = "";	
    

    private String sendLastPolicyNo = "";
    
    
    private String IP = "";
    private String UsbKey = "";
    
    
    
    private String PeccancyStartDate = "";
    private String PeccancyEndDate = "";
    private String KindAdjustValue = "";
    private String KindAdjustReason = "";
    private String NoKindAdjustReason = "";
    private String NoPeccancyReason = "";
    
    
    private String LastProducerCode = "";
    
    
    private String LastPolicyQueryDate = "";
    private String LastPolicyTotalPremium = "";
    private String InsureInDoorValue = "";
    private String InsureInDoorReason = "";
    private String ClaimAmountValue = "";
    private String ClaimAmountReason = "";
    private String NoClaimAmountReason = "";
    private String SpecificRiskValue = "";
    private String IndependentValue = "";
	
    
    private String RenewalFlag = "";
    private String CheckCode = "";
    private String Question = "";
    
    
    private String commissionrateupper = "";
    private String companycommissionrateupper  = "";
    private String claimeffectreason  = "";
    private String newvehicleeffectreason  = "";
    private String producereffectreason   = "";
    
    
    private String CarOwnerMessage = "";
	
	
    private String FuelType = "";
	
    
    private String ExhaustScale = "";
    
    
    private String FundRate = "";
    private String FundAmount = "";
    
    
    private String ReinsureFlagBom = "";
    
    
    private String ReinsureFrameNoBom = "";
    
    
    private String TransferFlag = "";
    private String HighRiskFlag = "";
    private String EffectReason = "";
    private String LastyearCompanyId = "";
    
    
    private String LastModelCode = "";  
    private String LastModel = "";  
    private String LastPurchasePrice = "";  
    
    
    private String RepeatLastYearStartDate = "";
    private String RepeatLastYearEndDate = "";
    private String RepeatLicensePlatNo = "";
    private String RepeatPolicyNo = "";
    private String RepeatVIN = "";
    private String RepeatEngineNo = "";
    private String RepeatBillDate = "";
    private String RepeatInsurerCode = "";
    
    
    private String ChannelType = "";
    
    
    private String UsageTypeMessage = "";
    private String Wholeweight = "";
    private String Displacement = "";
    private String ProducerType = "";
    private String SalePrice = "";
    private String PmFuelType = "";
    private String VehicleCategory = "";
    private String VehicleOwnerName = "";
    private String ChgOwnerDate = "";
    private String NoPeccancyAdjustReason = "";
    private String NoDriverAdjustReason = "";
    private String FleetMessage = "";
    private String MileageFactorMessage = "";
    
    
    private String RepeatInsurerArea = "";
    
    /**
     * ���캯��
     */       
    public CIInsureDemandSchema(){
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
     * �������Ժ��ƺ���
     * @param LicenseNo ���ƺ���
     */
    public void setLicenseNo(String LicenseNo){
        this.LicenseNo = Str.rightTrim(LicenseNo);
    }

    /**
     * ��ȡ���Ժ��ƺ���
     * @return ���ƺ���
     */
    public String getLicenseNo(){
        return Str.rightTrim(LicenseNo);
    }

    /**
     * �������Ժ�������
     * @param LicenseType ��������
     */
    public void setLicenseType(String LicenseType){
        this.LicenseType = Str.rightTrim(LicenseType);
    }

    /**
     * ��ȡ���Ժ�������
     * @return ��������
     */
    public String getLicenseType(){
        return Str.rightTrim(LicenseType);
    }

    /**
     * ��������ʹ������
     * @param UseNatureCode ʹ������
     */
    public void setUseNatureCode(String UseNatureCode){
        this.UseNatureCode = Str.rightTrim(UseNatureCode);
    }

    /**
     * ��ȡ����ʹ������
     * @return ʹ������
     */
    public String getUseNatureCode(){
        return Str.rightTrim(UseNatureCode);
    }

    /**
     * �������Գ��ܺ�
     * @param FrameNo ���ܺ�
     */
    public void setFrameNo(String FrameNo){
        this.FrameNo = Str.rightTrim(FrameNo);
    }

    /**
     * ��ȡ���Գ��ܺ�
     * @return ���ܺ�
     */
    public String getFrameNo(){
        return Str.rightTrim(FrameNo);
    }

    /**
     * �������Է�������
     * @param EngineNo ��������
     */
    public void setEngineNo(String EngineNo){
        this.EngineNo = Str.rightTrim(EngineNo);
    }

    /**
     * ��ȡ���Է�������
     * @return ��������
     */
    public String getEngineNo(){
        return Str.rightTrim(EngineNo);
    }

    /**
     * �������Գ�����ɫ����
     * @param LicenseColorCode ������ɫ����
     */
    public void setLicenseColorCode(String LicenseColorCode){
        this.LicenseColorCode = Str.rightTrim(LicenseColorCode);
    }

    /**
     * ��ȡ���Գ�����ɫ����
     * @return ������ɫ����
     */
    public String getLicenseColorCode(){
        return Str.rightTrim(LicenseColorCode);
    }

    /**
     * ����������ʻ֤����
     * @param CarOwner ��ʻ֤����
     */
    public void setCarOwner(String CarOwner){
        this.CarOwner = Str.rightTrim(CarOwner);
    }

    /**
     * ��ȡ������ʻ֤����
     * @return ��ʻ֤����
     */
    public String getCarOwner(){
        return Str.rightTrim(CarOwner);
    }

    /**
     * �������Գ�����ʼ�Ǽ�����
     * @param EnrollDate ������ʼ�Ǽ�����
     */
    public void setEnrollDate(String EnrollDate){
      ChgDate chgDate = new ChgDate(); 
      EnrollDate = chgDate.toFormat(EnrollDate);
      if  (EnrollDate == null || EnrollDate.length() == 0 ) 
      {
        this.EnrollDate = "";
      }else
      {
       this.EnrollDate = EnrollDate.trim().substring(0,10);
      }
    }

    /**
     * ��ȡ���Գ�����ʼ�Ǽ�����
     * @return ������ʼ�Ǽ�����
     */
    public String getEnrollDate(){
        return Str.rightTrim(EnrollDate);
    }

    /**
     * �������Գ�������
     * @param MakeDate ��������
     */
    public void setMakeDate(String MakeDate){
      ChgDate chgDate = new ChgDate(); 
      MakeDate = chgDate.toFormat(MakeDate);
      if  (MakeDate == null || MakeDate.length() == 0 ) 
      {
        this.MakeDate = "";
      }else
      {
       this.MakeDate = MakeDate.trim().substring(0,10);
      }
    }

    /**
     * ��ȡ���Գ�������
     * @return ��������
     */
    public String getMakeDate(){
        return Str.rightTrim(MakeDate);
    }

    /**
     * �������Ժ˶��ؿ�����
     * @param SeatCount �˶��ؿ�����
     */
    public void setSeatCount(String SeatCount){
        this.SeatCount = Str.rightTrim(SeatCount);
    }

    /**
     * ��ȡ���Ժ˶��ؿ�����
     * @return �˶��ؿ�����
     */
    public String getSeatCount(){
        return Str.rightTrim(SeatCount);
    }

    /**
     * �������Ժ˶�������
     * @param TonCount �˶�������
     */
    public void setTonCount(String TonCount){
        this.TonCount = Str.rightTrim(TonCount);
    }

    /**
     * ��ȡ���Ժ˶�������
     * @return �˶�������
     */
    public String getTonCount(){
        return Str.rightTrim(TonCount);
    }

    /**
     * ��������������Ч��ֹ
     * @param ValidCheckDate ������Ч��ֹ
     */
    public void setValidCheckDate(String ValidCheckDate){
      ChgDate chgDate = new ChgDate(); 
      ValidCheckDate = chgDate.toFormat(ValidCheckDate);
      if  (ValidCheckDate == null || ValidCheckDate.length() == 0 ) 
      {
        this.ValidCheckDate = "";
      }else
      {
       this.ValidCheckDate = ValidCheckDate.trim().substring(0,10);
      }
    }

    /**
     * ��ȡ����������Ч��ֹ
     * @return ������Ч��ֹ
     */
    public String getValidCheckDate(){
        return Str.rightTrim(ValidCheckDate);
    }

    /**
     * �����������쳧����
     * @param ManufacturerName ���쳧����
     */
    public void setManufacturerName(String ManufacturerName){
        this.ManufacturerName = Str.rightTrim(ManufacturerName);
    }

    /**
     * ��ȡ�������쳧����
     * @return ���쳧����
     */
    public String getManufacturerName(){
        return Str.rightTrim(ManufacturerName);
    }

    /**
     * �������Գ��ʹ���
     * @param ModelCode ���ʹ���
     */
    public void setModelCode(String ModelCode){
        this.ModelCode = Str.rightTrim(ModelCode);
    }

    /**
     * ��ȡ���Գ��ʹ���
     * @return ���ʹ���
     */
    public String getModelCode(){
        return Str.rightTrim(ModelCode);
    }

    /**
     * ������������Ʒ��
     * @param BrandCName ����Ʒ��
     */
    public void setBrandCName(String BrandCName){
        this.BrandCName = Str.rightTrim(BrandCName);
    }

    /**
     * ��ȡ��������Ʒ��
     * @return ����Ʒ��
     */
    public String getBrandCName(){
        return Str.rightTrim(BrandCName);
    }

    /**
     * �������Գ���Ʒ��2
     * @param BrandName ����Ʒ��2
     */
    public void setBrandName(String BrandName){
        this.BrandName = Str.rightTrim(BrandName);
    }

    /**
     * ��ȡ���Գ���Ʒ��2
     * @return ����Ʒ��2
     */
    public String getBrandName(){
        return Str.rightTrim(BrandName);
    }

    /**
     * �������Գ�������
     * @param CarKindCode ��������
     */
    public void setCarKindCode(String CarKindCode){
        this.CarKindCode = Str.rightTrim(CarKindCode);
    }

    /**
     * ��ȡ���Գ�������
     * @return ��������
     */
    public String getCarKindCode(){
        return Str.rightTrim(CarKindCode);
    }

    /**
     * �������������������
     * @param CheckDate �����������
     */
    public void setCheckDate(String CheckDate){
      ChgDate chgDate = new ChgDate(); 
      CheckDate = chgDate.toFormat(CheckDate);
      if  (CheckDate == null || CheckDate.length() == 0 ) 
      {
        this.CheckDate = "";
      }else
      {
       this.CheckDate = CheckDate.trim().substring(0,10);
      }
    }

    /**
     * ��ȡ���������������
     * @return �����������
     */
    public String getCheckDate(){
        return Str.rightTrim(CheckDate);
    }

    /**
     * ��������ǿ����Ч��ֹ
     * @param EndValidDate ǿ����Ч��ֹ
     */
    public void setEndValidDate(String EndValidDate){
      ChgDate chgDate = new ChgDate(); 
      EndValidDate = chgDate.toFormat(EndValidDate);
      if  (EndValidDate == null || EndValidDate.length() == 0 ) 
      {
        this.EndValidDate = "";
      }else
      {
       this.EndValidDate = EndValidDate.trim().substring(0,10);
      }
    }

    /**
     * ��ȡ����ǿ����Ч��ֹ
     * @return ǿ����Ч��ֹ
     */
    public String getEndValidDate(){
        return Str.rightTrim(EndValidDate);
    }

    /**
     * �������Ի�����״̬
     * @param CarStatus ������״̬
     */
    public void setCarStatus(String CarStatus){
        this.CarStatus = Str.rightTrim(CarStatus);
    }

    /**
     * ��ȡ���Ի�����״̬
     * @return ������״̬
     */
    public String getCarStatus(){
        return Str.rightTrim(CarStatus);
    }

    /**
     * ��������׼ǣ��������
     * @param Haulage ׼ǣ��������
     */
    public void setHaulage(String Haulage){
        this.Haulage = Str.rightTrim(Haulage);
    }

    /**
     * ��ȡ����׼ǣ��������
     * @return ׼ǣ��������
     */
    public String getHaulage(){
        return Str.rightTrim(Haulage);
    }

    /**
     * ����������XXXXX����
     * @param StartDate ��XXXXX����
     */
    public void setStartDate(String StartDate){
      ChgDate chgDate = new ChgDate(); 
      StartDate = chgDate.toFormat(StartDate);
      if  (StartDate == null || StartDate.length() == 0 ) 
      {
        this.StartDate = "";
      }else
      {
       this.StartDate = StartDate.trim().substring(0,10);
      }
    }

    /**
     * ��ȡ������XXXXX����
     * @return ��XXXXX����
     */
    public String getStartDate(){
        return Str.rightTrim(StartDate);
    }

    /**
     * ����������XXXXX����
     * @param EndDate ��XXXXX����
     */
    public void setEndDate(String EndDate){
      ChgDate chgDate = new ChgDate(); 
      EndDate = chgDate.toFormat(EndDate);
      if  (EndDate == null || EndDate.length() == 0 ) 
      {
        this.EndDate = "";
      }else
      {
       this.EndDate = EndDate.trim().substring(0,10);
      }
    }

    /**
     * ��ȡ������XXXXX����
     * @return ��XXXXX����
     */
    public String getEndDate(){
        return Str.rightTrim(EndDate);
    }

    /**
     * ������������XX
     * @param Amount ����XX
     */
    public void setAmount(String Amount){
        this.Amount = Str.rightTrim(Amount);
    }

    /**
     * ��ȡ��������XX
     * @return ����XX
     */
    public String getAmount(){
        return Str.rightTrim(Amount);
    }

    /**
     * ������������ʵ��XX
     * @param Premium ����ʵ��XX
     */
    public void setPremium(String Premium){
        this.Premium = Str.rightTrim(Premium);
    }

    /**
     * ��ȡ��������ʵ��XX
     * @return ����ʵ��XX
     */
    public String getPremium(){
        return Str.rightTrim(Premium);
    }

    /**
     * ��������ǿ����׼XX
     * @param BenchmarkPremium ǿ����׼XX
     */
    public void setBenchmarkPremium(String BenchmarkPremium){
        this.BenchmarkPremium = Str.rightTrim(BenchmarkPremium);
    }

    /**
     * ��ȡ����ǿ����׼XX
     * @return ǿ����׼XX
     */
    public String getBenchmarkPremium(){
        return Str.rightTrim(BenchmarkPremium);
    }

    /**
     * ��������ǿ��Υ������ϵ��
     * @param PeccancyCoeff ǿ��Υ������ϵ��
     */
    public void setPeccancyCoeff(String PeccancyCoeff){
        this.PeccancyCoeff = Str.rightTrim(PeccancyCoeff);
    }

    /**
     * ��ȡ����ǿ��Υ������ϵ��
     * @return ǿ��Υ������ϵ��
     */
    public String getPeccancyCoeff(){
        return Str.rightTrim(PeccancyCoeff);
    }

    /**
     * ��������ǿ��XXXXX����ϵ��
     * @param ClaimCoeff ǿ��XXXXX����ϵ��
     */
    public void setClaimCoeff(String ClaimCoeff){
        this.ClaimCoeff = Str.rightTrim(ClaimCoeff);
    }

    /**
     * ��ȡ����ǿ��XXXXX����ϵ��
     * @return ǿ��XXXXX����ϵ��
     */
    public String getClaimCoeff(){
        return Str.rightTrim(ClaimCoeff);
    }

    /**
     * ��������ǿ��ָ����ʻԱ����ϵ��
     * @param DriverCoeff ǿ��ָ����ʻԱ����ϵ��
     */
    public void setDriverCoeff(String DriverCoeff){
        this.DriverCoeff = Str.rightTrim(DriverCoeff);
    }

    /**
     * ��ȡ����ǿ��ָ����ʻԱ����ϵ��
     * @return ǿ��ָ����ʻԱ����ϵ��
     */
    public String getDriverCoeff(){
        return Str.rightTrim(DriverCoeff);
    }

    /**
     * ��������ǿ������ϵ��
     * @param DistrictCoeff ǿ������ϵ��
     */
    public void setDistrictCoeff(String DistrictCoeff){
        this.DistrictCoeff = Str.rightTrim(DistrictCoeff);
    }

    /**
     * ��ȡ����ǿ������ϵ��
     * @return ǿ������ϵ��
     */
    public String getDistrictCoeff(){
        return Str.rightTrim(DistrictCoeff);
    }

    /**
     * ��������ǿ�����������ѱ���(%)
     * @param CommissionRate ǿ�����������ѱ���(%)
     */
    public void setCommissionRate(String CommissionRate){
        this.CommissionRate = Str.rightTrim(CommissionRate);
    }

    /**
     * ��ȡ����ǿ�����������ѱ���(%)
     * @return ǿ�����������ѱ���(%)
     */
    public String getCommissionRate(){
        return Str.rightTrim(CommissionRate);
    }

    /**
     * ��������ǿ������XX
     * @param BasePremium ǿ������XX
     */
    public void setBasePremium(String BasePremium){
        this.BasePremium = Str.rightTrim(BasePremium);
    }

    /**
     * ��ȡ����ǿ������XX
     * @return ǿ������XX
     */
    public String getBasePremium(){
        return Str.rightTrim(BasePremium);
    }

    /**
     * �������Թ�������
     * @param ComCode ��������
     */
    public void setComCode(String ComCode){
        this.ComCode = Str.rightTrim(ComCode);
    }

    /**
     * ��ȡ���Թ�������
     * @return ��������
     */
    public String getComCode(){
        return Str.rightTrim(ComCode);
    }

    /**
     * �������Բ���Ա����
     * @param OperatorCode ����Ա����
     */
    public void setOperatorCode(String OperatorCode){
        this.OperatorCode = Str.rightTrim(OperatorCode);
    }

    /**
     * ��ȡ���Բ���Ա����
     * @return ����Ա����
     */
    public String getOperatorCode(){
        return Str.rightTrim(OperatorCode);
    }

    /**
     * �������Բ�ѯʱ��
     * @param DemandTime ��ѯʱ��
     */
    public void setDemandTime(String DemandTime){
      ChgDate chgDate = new ChgDate(); 
      DemandTime = chgDate.toFormat(DemandTime);
      if  (DemandTime == null || DemandTime.length() == 0 ) 
      {
        this.DemandTime = "";
      }else
      {
       this.DemandTime = DemandTime.trim().substring(0,10);
      }
    }

    /**
     * ��ȡ���Բ�ѯʱ��
     * @return ��ѯʱ��
     */
    public String getDemandTime(){
        return Str.rightTrim(DemandTime);
    }

    /**
     * �������Ա�ע
     * @param Remark ��ע
     */
    public void setRemark(String Remark){
        this.Remark = Str.rightTrim(Remark);
    }

    /**
     * ��ȡ���Ա�ע
     * @return ��ע
     */
    public String getRemark(){
        return Str.rightTrim(Remark);
    }

    /**
     * ��������״̬�ֶ�
     * @param Flag ״̬�ֶ�
     */
    public void setFlag(String Flag){
        this.Flag = Flag;
    }

    /**
     * ��ȡ����״̬�ֶ�
     * @return ״̬�ֶ�
     */
    public String getFlag(){
        return Flag;
    }
    
    
    /**
     * ������������XX����
     * @param Flag ����XX����
     */
    public void setLastYearStartDate(String lastYearStartDate){
		  ChgDate chgDate = new ChgDate(); 
		  lastYearStartDate = chgDate.toFormat(lastYearStartDate);
	      if(lastYearStartDate == null || lastYearStartDate.length() == 0 ) 
	      {
	        this.LastYearStartDate = "";
	      }else
	      {
	       this.LastYearStartDate = lastYearStartDate.trim().substring(0,10);
	      }
    }

    /**
     * ��ȡ��������XX����
     * @return ����XX����
     */
    public String getLastYearStartDate(){
        return Str.rightTrim(LastYearStartDate);
    }
    /**
     * ������������XXֹ��
     * @param Flag ����XXֹ��
     */
    public void setLastYearEndDate(String lastYearEndDate) {
		ChgDate chgDate = new ChgDate();
		lastYearEndDate = chgDate.toFormat(lastYearEndDate);
		if (lastYearEndDate == null || lastYearEndDate.length() == 0) {
			this.LastYearEndDate = "";
		} else {
			
			this.LastYearEndDate = lastYearEndDate;
			
		}
	}

    /**
	 * ��ȡ��������XXֹ��
	 * 
	 * @return ����XXֹ��
	 */
    public String getLastYearEndDate(){
        return Str.rightTrim(LastYearEndDate);
    }
    
    
    public String getBillDate() {
		return BillDate;
	}

	public void setBillDate(String billDate) {
		BillDate = Str.rightTrim(billDate);
	}

	public String getLastBillDate() {
		return LastBillDate;
	}

	public void setLastBillDate(String lastBillDate) {
		LastBillDate = Str.rightTrim(lastBillDate);
	}

	public String getRateFloatFlag() {
		return RateFloatFlag;
	}

	public void setRateFloatFlag(String rateFloatFlag) {
		RateFloatFlag = Str.rightTrim(rateFloatFlag);
	}

	public String getReinsureFlag() {
		return ReinsureFlag;
	}

	public void setReinsureFlag(String reinsureFlag) {
		ReinsureFlag = Str.rightTrim(reinsureFlag);
	}
	
	public String getClaimAdjustReason() {
		return ClaimAdjustReason;
	}

	public void setClaimAdjustReason(String claimAdjustReason) {
		ClaimAdjustReason = Str.rightTrim(claimAdjustReason);
	}

	public String getDistrictRateReason() {
		return DistrictRateReason;
	}

	public void setDistrictRateReason(String districtRateReason) {
		DistrictRateReason = Str.rightTrim(districtRateReason);
	}

	public String getDriverRateReason() {
		return DriverRateReason;
	}

	public void setDriverRateReason(String driverRateReason) {
		DriverRateReason = Str.rightTrim(driverRateReason);
	}

	public String getPeccancyAdjustReason() {
		return PeccancyAdjustReason;
	}

	public void setPeccancyAdjustReason(String peccancyAdjustReason) {
		PeccancyAdjustReason = Str.rightTrim(peccancyAdjustReason);
	}
  
	public String getProconfirmSequenceNo() {
		return Str.rightTrim(ProconfirmSequenceNo);
	}

	public void setProconfirmSequenceNo(String proconfirmSequenceNo) {
		this.ProconfirmSequenceNo = Str.rightTrim(proconfirmSequenceNo);
	}
	
	public String getProconfirmStartDate() {
		return Str.rightTrim(ProconfirmStartDate);
	}
	
	public void setProconfirmStartDate(String proconfirmStartDate) {
		ChgDate chgDate = new ChgDate(); 
		proconfirmStartDate = chgDate.toFormat(proconfirmStartDate);
	      if  (proconfirmStartDate == null || proconfirmStartDate.length() == 0 ) 
	      {
	        this.ProconfirmStartDate = "";
	      }else
	      {
	       this.ProconfirmStartDate = proconfirmStartDate.trim().substring(0,19);
	      }
	}
	
	public String getProconfirmEndDate() {
		return Str.rightTrim(ProconfirmEndDate);
	}
	
	public void setProconfirmEndDate(String proconfirmEndDate) {
		ChgDate chgDate = new ChgDate(); 
		proconfirmEndDate = chgDate.toFormat(proconfirmEndDate);
	      if  (proconfirmEndDate == null || proconfirmEndDate.length() == 0 ) 
	      {
	        this.ProconfirmEndDate = "";
	      }else
	      {
	       this.ProconfirmEndDate = proconfirmEndDate.trim().substring(0,19);
	      }
	}
  
	
	public String getRestricFlag(){
		return Str.rightTrim(RestricFlag);
	}
	public void setRestricFlag(String restricFlag){
		this.RestricFlag = Str.rightTrim(restricFlag);
	}
	public String getPreferentialPremium(){
		return Str.rightTrim(PreferentialPremium);
	}
	public void setPreferentialPremium(String preferentialPremium){
		this.PreferentialPremium = Str.rightTrim(preferentialPremium);
	}
	public String getPreferentialFormula(){
		return Str.rightTrim(PreferentialFormula);
	}
	public void setPreferentialFormula(String preferentialFormula){
		this.PreferentialFormula = Str.rightTrim(preferentialFormula);
	}
	public String getPreferentialDay(){
		return Str.rightTrim(PreferentialDay);
	}
	public void setPreferentialDay(String preferentialDay){
		this.PreferentialDay = Str.rightTrim(preferentialDay);
	}
	
	
	public String getErrorMessage(){
		return Str.rightTrim(ErrorMessage);
	}
	public void setErrorMessage(String errorMessage){
		this.ErrorMessage = Str.rightTrim(errorMessage);
	}
	
	
	public String getLoyaltyAdjust() {
		return Str.rightTrim(LoyaltyAdjust);
	}

	public void setLoyaltyAdjust(String loyaltyAdjust) {
		LoyaltyAdjust = Str.rightTrim(loyaltyAdjust);
	}

	public String getNonclaimAdjust() {
		return Str.rightTrim(NonclaimAdjust);
	}

	public void setNonclaimAdjust(String nonclaimAdjust) {
		NonclaimAdjust = Str.rightTrim(nonclaimAdjust);
	}

	public String getSafeAdjust() {
		return Str.rightTrim(SafeAdjust);
	}

	public void setSafeAdjust(String safeAdjust) {
		SafeAdjust = Str.rightTrim(safeAdjust);
	}

	public String getSearchSequenceNo() {
		return Str.rightTrim(SearchSequenceNo);
	}

	public void setSearchSequenceNo(String searchSequenceNo) {
		SearchSequenceNo = Str.rightTrim(searchSequenceNo);
	}
	
	
	public String getUseTypeSource() {
		return UseTypeSource;
	}

	public void setUseTypeSource(String useTypeSource) {
		UseTypeSource = Str.rightTrim(useTypeSource);
	}
	
    
    
    
    /**
     * ��������״̬�ֶ�
     * @param Flag ״̬�ֶ�
     */
    public void setQueryPastDate(String QueryPastDate){
        ChgDate chgDate = new ChgDate(); 
        QueryPastDate = chgDate.toFormat(QueryPastDate);
        if  (QueryPastDate == null || QueryPastDate.length() == 0 ) 
        {
          this.QueryPastDate = "";
        }else
        {
         this.QueryPastDate = QueryPastDate.trim().substring(0,10);
        }
    }

    /**
     * ��ȡ����״̬�ֶ�
     * @return ״̬�ֶ�
     */
    public String getQueryPastDate(){
        return Str.rightTrim(QueryPastDate);
    }
    /**
     * ��������״̬�ֶ�
     * @param Flag ״̬�ֶ�
     */
    public void setTransferDate(String TransferDate){
        ChgDate chgDate = new ChgDate(); 
        TransferDate = chgDate.toFormat(TransferDate);
        if  (TransferDate == null || TransferDate.length() == 0 ) 
        {
          this.TransferDate = "";
        }else
        {
         this.TransferDate = TransferDate.trim().substring(0,10);
        }
    }

    /**
     * ��ȡ����״̬�ֶ�
     * @return ״̬�ֶ�
     */
    public String getTransferDate(){
        return Str.rightTrim(TransferDate);
    }
    
    
    public void setLoyaltyAdjustReason(String LoyaltyAdjustReason){
        this.LoyaltyAdjustReason=LoyaltyAdjustReason;
    }
    
    public String getLoyaltyAdjustReason(){
        return Str.rightTrim(LoyaltyAdjustReason);
    }
    
    
	public String getBusiLastYearEndDate() {
		return BusiLastYearEndDate;
	}

	public void setBusiLastYearEndDate(String busiLastYearEndDate) {
		ChgDate chgDate = new ChgDate(); 
		busiLastYearEndDate = chgDate.toFormat(busiLastYearEndDate);
		if(busiLastYearEndDate == null || busiLastYearEndDate.length() == 0 ){
			this.BusiLastYearEndDate = "";
		}else{
			this.BusiLastYearEndDate = busiLastYearEndDate.trim().substring(0,10);
		}
	}

	public String getBusiAdjustStart() {
		return BusiAdjustStart;
	}

	public void setBusiAdjustStart(String busiAdjustStart) {
		ChgDate chgDate = new ChgDate(); 
		busiAdjustStart = chgDate.toFormat(busiAdjustStart);
		if(busiAdjustStart == null || busiAdjustStart.length() == 0 ){
			this.BusiAdjustStart = "";
		}else{
			this.BusiAdjustStart = busiAdjustStart.trim().substring(0,10);
		}
	}

	public String getBusiAdjustEnd() {
		return BusiAdjustEnd;
	}

	public void setBusiAdjustEnd(String busiAdjustEnd) {
		ChgDate chgDate = new ChgDate(); 
		busiAdjustEnd = chgDate.toFormat(busiAdjustEnd);
		if(busiAdjustEnd == null || busiAdjustEnd.length() == 0 ){
			this.BusiAdjustEnd = "";
		}else{
			this.BusiAdjustEnd = busiAdjustEnd.trim().substring(0,10);
		}
	}
	public String getPoWeight() {
		return PoWeight;
	}

	public void setPoWeight(String poWeight) {
		PoWeight = Str.rightTrim(poWeight);
	}
    
	
	public String getReCoverMsg() {
		return ReCoverMsg;
	}

	public void setReCoverMsg(String ReCoverMsg) {
		this.ReCoverMsg = Str.rightTrim(ReCoverMsg);
	}
	
		
	public String getPolicyCoeff() {
		return PolicyCoeff;
	}

	public void setPolicyCoeff(String PolicyCoeff) {
		this.PolicyCoeff = Str.rightTrim(PolicyCoeff);
	}
	
	
	public String getNoLoyaltyAdjustReason() {
		return NoLoyaltyAdjustReason;
	}

	public void setNoLoyaltyAdjustReason(String NoLoyaltyAdjustReason) {
		this.NoLoyaltyAdjustReason = Str.rightTrim(NoLoyaltyAdjustReason);
	}
	public String getNoClaimAdjustReason() {
		return NoClaimAdjustReason;
	}

	public void setNoClaimAdjustReason(String NoClaimAdjustReason) {
		this.NoClaimAdjustReason = Str.rightTrim(NoClaimAdjustReason);
	}
	
	
	
	public String getSendLastPolicyNo() {
		return sendLastPolicyNo;
	}

	public void setSendLastPolicyNo(String sendLastPolicyNo) {
		this.sendLastPolicyNo = Str.rightTrim(sendLastPolicyNo);
	}
	
	
	public String getIP() {
		return IP;
	}

	public void setIP(String ip) {
		IP = Str.rightTrim(ip);
	}

	public String getUsbKey() {
		return UsbKey;
	}

	public void setUsbKey(String usbKey) {
		UsbKey = Str.rightTrim(usbKey);
	}
	
    
	public String getKindAdjustReason() {
		return KindAdjustReason;
	}

	public void setKindAdjustReason(String kindAdjustReason) {
		KindAdjustReason = Str.rightTrim(kindAdjustReason);
	}

	public String getKindAdjustValue() {
		return KindAdjustValue;
	}

	public void setKindAdjustValue(String kindAdjustValue) {
		KindAdjustValue = Str.rightTrim(kindAdjustValue);
	}

	public String getNoKindAdjustReason() {
		return NoKindAdjustReason;
	}

	public void setNoKindAdjustReason(String noKindAdjustReason) {
		NoKindAdjustReason = Str.rightTrim(noKindAdjustReason);
	}

	public String getNoPeccancyReason() {
		return NoPeccancyReason;
	}

	public void setNoPeccancyReason(String noPeccancyReason) {
		NoPeccancyReason = Str.rightTrim(noPeccancyReason);
	}

	public String getPeccancyEndDate() {
		return PeccancyEndDate;
	}

	public void setPeccancyEndDate(String peccancyEndDate) {
		PeccancyEndDate = Str.rightTrim(peccancyEndDate);
	}

	public String getPeccancyStartDate() {
		return PeccancyStartDate;
	}

	public void setPeccancyStartDate(String peccancyStartDate) {
		PeccancyStartDate = Str.rightTrim(peccancyStartDate);
	}
	
	
	public String getLastProducerCode() {
		return LastProducerCode;
	}

	public void setLastProducerCode(String lastProducerCode) {
		LastProducerCode = Str.rightTrim(lastProducerCode);
	}
	
	
	public String getLastPolicyQueryDate() {
		return LastPolicyQueryDate;
	}

	public void setLastPolicyQueryDate(String lastPolicyQueryDate) {
		LastPolicyQueryDate = Str.rightTrim(lastPolicyQueryDate);
	}

	public String getLastPolicyTotalPremium() {
		return LastPolicyTotalPremium;
	}

	public void setLastPolicyTotalPremium(String lastPolicyTotalPremium) {
		LastPolicyTotalPremium = Str.rightTrim(lastPolicyTotalPremium);
	}

	public String getInsureInDoorValue() {
		return InsureInDoorValue;
	}

	public void setInsureInDoorValue(String insureInDoorValue) {
		InsureInDoorValue = Str.rightTrim(insureInDoorValue);
	}

	public String getInsureInDoorReason() {
		return InsureInDoorReason;
	}

	public void setInsureInDoorReason(String insureInDoorReason) {
		InsureInDoorReason = Str.rightTrim(insureInDoorReason);
	}

	public String getClaimAmountValue() {
		return ClaimAmountValue;
	}

	public void setClaimAmountValue(String claimAmountValue) {
		ClaimAmountValue = Str.rightTrim(claimAmountValue);
	}

	public String getClaimAmountReason() {
		return ClaimAmountReason;
	}

	public void setClaimAmountReason(String claimAmountReason) {
		ClaimAmountReason = Str.rightTrim(claimAmountReason);
	}

	public String getNoClaimAmountReason() {
		return NoClaimAmountReason;
	}

	public void setNoClaimAmountReason(String noClaimAmountReason) {
		NoClaimAmountReason = Str.rightTrim(noClaimAmountReason);
	}
	
	public String getSpecificRiskValue() {
		return SpecificRiskValue;
	}

	public void setSpecificRiskValue(String specificRiskValue) {
		SpecificRiskValue = Str.rightTrim(specificRiskValue);
	}

	public String getIndependentValue() {
		return IndependentValue;
	}

	public void setIndependentValue(String independentValue) {
		IndependentValue = Str.rightTrim(independentValue);
	}
	
    
	public String getCheckCode() {
		return CheckCode;
	}

	public void setCheckCode(String checkCode) {
		CheckCode = Str.rightTrim(checkCode);
	}

	public String getQuestion() {
		return Question;
	}

	public void setQuestion(String question) {
		Question = Str.rightTrim(question);
	}

	public String getRenewalFlag() {
		return RenewalFlag;
	}

	public void setRenewalFlag(String renewalFlag) {
		RenewalFlag = Str.rightTrim(renewalFlag);
	}
    
	
	public String getCommissionrateupper() {
		return commissionrateupper;
	}

	public void setCommissionrateupper(String commissionrateupper) {
		this.commissionrateupper = Str.rightTrim(commissionrateupper);
	}

	public String getCompanycommissionrateupper() {
		return companycommissionrateupper;
	}

	public void setCompanycommissionrateupper(String companycommissionrateupper) {
		this.companycommissionrateupper = Str.rightTrim(companycommissionrateupper);
	}

	public String getClaimeffectreason() {
		return claimeffectreason;
	}

	public void setClaimeffectreason(String claimeffectreason) {
		this.claimeffectreason = Str.rightTrim(claimeffectreason);
	}

	public String getNewvehicleeffectreason() {
		return newvehicleeffectreason;
	}

	public void setNewvehicleeffectreason(String newvehicleeffectreason) {
		this.newvehicleeffectreason = Str.rightTrim(newvehicleeffectreason);
	}

	public String getProducereffectreason() {
		return producereffectreason;
	}

	public void setProducereffectreason(String producereffectreason) {
		this.producereffectreason = Str.rightTrim(producereffectreason);
	}
	
	
    public String getCarOwnerMessage() {
		return CarOwnerMessage;
	}

	public void setCarOwnerMessage(String carOwnerMessage) {
		CarOwnerMessage = Str.rightTrim(carOwnerMessage);
	}
    
	
	public String getFuelType() {
		return FuelType;
	}

	public void setFuelType(String FuelType) {
		this.FuelType = Str.rightTrim(FuelType);
	}
	
    
	public String getExhaustScale() {
		return ExhaustScale;
	}

	public void setExhaustScale(String ExhaustScale) {
		this.ExhaustScale = Str.rightTrim(ExhaustScale);
	}
    
	
	public String getFundRate() {
		return FundRate;
	}

	public void setFundRate(String fundRate) {
		this.FundRate = Str.rightTrim(fundRate);
	}

	public String getFundAmount() {
		return FundAmount;
	}

	public void setFundAmount(String fundAmount) {
		this.FundAmount = Str.rightTrim(fundAmount);
	}
	
	
	public String getReinsureFlagBom() {
		return ReinsureFlagBom;
	}

	public void setReinsureFlagBom(String reinsureFlagBom) {
		ReinsureFlagBom = reinsureFlagBom;
	}
	
	
	public String getReinsureFrameNoBom() {
		return ReinsureFrameNoBom;
	}

	public void setReinsureFrameNoBom(String reinsureFrameNoBom) {
		ReinsureFrameNoBom = reinsureFrameNoBom;
	}
	

    
    /**
     * ��ȡ������־
     * @return ������־
     */
    public String getTransferFlag() {
        return TransferFlag;
    }

    /**
     * ���ù�����־
     * @param transferFlag ������־
     */
    public void setTransferFlag(String transferFlag) {
        TransferFlag = transferFlag;
    }

    /**
     * ��ȡ�߷�XXXXX��־
     * @return �߷�XXXXX��־
     */
    public String getHighRiskFlag() {
        return HighRiskFlag;
    }

    /**
     * ���ø߷�XXXXX��־
     * @param highRiskFlag �߷�XXXXX��־
     */
    public void setHighRiskFlag(String highRiskFlag) {
        HighRiskFlag = highRiskFlag;
    }

    /**
     * ��ȡ������Ч��������
     * @return ������Ч��������
     */
    public String getEffectReason() {
        return EffectReason;
    }

    /**
     * ���ý�����Ч��������
     * @param effectReason ������Ч��������
     */
    public void setEffectReason(String effectReason) {
        EffectReason = effectReason;
    }

    /**
     * ��ȡ���һ�Ž�ǿXXXXXXXXX��˾
     * @return ���һ�Ž�ǿXXXXXXXXX��˾
     */
    public String getLastyearCompanyId() {
        return LastyearCompanyId;
    }

    /**
     * �������һ�Ž�ǿXXXXXXXXX��˾
     * @param lastyearCompanyId ���һ�Ž�ǿXXXXXXXXX��˾
     */
    public void setLastyearCompanyId(String lastyearCompanyId) {
        LastyearCompanyId = lastyearCompanyId;
    }
    
    
    public String getLastModelCode() {
		return LastModelCode;
	}

	public void setLastModelCode(String lastModelCode) {
		this.LastModelCode = Str.rightTrim(lastModelCode);
	}

	public String getLastModel() {
		return LastModel;
	}

	public void setLastModel(String lastModel) {
		this.LastModel = Str.rightTrim(lastModel);
	}

	public String getLastPurchasePrice() {
		return LastPurchasePrice;
	}

	public void setLastPurchasePrice(String lastPurchasePrice) {
		this.LastPurchasePrice = Str.rightTrim(lastPurchasePrice);
	}
    
    
	public String getRepeatLastYearStartDate() {
		return RepeatLastYearStartDate;
	}

	public void setRepeatLastYearStartDate(String repeatLastYearStartDate) {
		RepeatLastYearStartDate = repeatLastYearStartDate;
	}

	public String getRepeatLastYearEndDate() {
		return RepeatLastYearEndDate;
	}

	public void setRepeatLastYearEndDate(String repeatLastYearEndDate) {
		RepeatLastYearEndDate = repeatLastYearEndDate;
	}
	
    public String getRepeatLicensePlatNo() {
		return RepeatLicensePlatNo;
	}

	public void setRepeatLicensePlatNo(String repeatLicensePlatNo) {
		RepeatLicensePlatNo = repeatLicensePlatNo;
	}

	public String getRepeatPolicyNo() {
		return RepeatPolicyNo;
	}

	public void setRepeatPolicyNo(String repeatPolicyNo) {
		RepeatPolicyNo = repeatPolicyNo;
	}

	public String getRepeatVIN() {
		return RepeatVIN;
	}

	public void setRepeatVIN(String repeatVIN) {
		RepeatVIN = repeatVIN;
	}

	public String getRepeatEngineNo() {
		return RepeatEngineNo;
	}

	public void setRepeatEngineNo(String repeatEngineNo) {
		RepeatEngineNo = repeatEngineNo;
	}

	public String getRepeatBillDate() {
		return RepeatBillDate;
	}

	public void setRepeatBillDate(String repeatBillDate) {
		RepeatBillDate = repeatBillDate;
	}

	public String getRepeatInsurerCode() {
		return RepeatInsurerCode;
	}

	public void setRepeatInsurerCode(String repeatInsurerCode) {
		RepeatInsurerCode = repeatInsurerCode;
	}
    
	
	public String getRepeatInsurerArea() {
		return Str.rightTrim(this.RepeatInsurerArea);
	}

	public void setRepeatInsurerArea(String repeatInsurerArea) {
		RepeatInsurerArea = Str.rightTrim(repeatInsurerArea);
	}
	
	
	public String getChannelType() {
		return Str.rightTrim(this.ChannelType);
	}

	public void setChannelType(String channelType) {
		this.ChannelType = Str.rightTrim(channelType);
	}
    
    
	public String getWholeweight() {
		return Wholeweight;
	}

	public void setWholeweight(String wholeweight) {
		Wholeweight = wholeweight;
	}

	public String getDisplacement() {
		return Displacement;
	}

	public void setDisplacement(String displacement) {
		Displacement = displacement;
	}

	public String getChgOwnerDate() {
		return ChgOwnerDate;
	}

	public void setChgOwnerDate(String chgOwnerDate) {
		ChgOwnerDate = chgOwnerDate;
	}

	public String getProducerType() {
		return ProducerType;
	}

	public void setProducerType(String producerType) {
		ProducerType = producerType;
	}

	public String getSalePrice() {
		return SalePrice;
	}

	public void setSalePrice(String salePrice) {
		SalePrice = salePrice;
	}

	public String getPmFuelType() {
		return PmFuelType;
	}

	public void setPmFuelType(String pmFuelType) {
		PmFuelType = pmFuelType;
	}

	public String getVehicleCategory() {
		return VehicleCategory;
	}

	public void setVehicleCategory(String vehicleCategory) {
		VehicleCategory = vehicleCategory;
	}

	public String getNoPeccancyAdjustReason() {
		return NoPeccancyAdjustReason;
	}

	public void setNoPeccancyAdjustReason(String noPeccancyAdjustReason) {
		NoPeccancyAdjustReason = noPeccancyAdjustReason;
	}

	public String getNoDriverAdjustReason() {
		return NoDriverAdjustReason;
	}

	public void setNoDriverAdjustReason(String noDriverAdjustReason) {
		NoDriverAdjustReason = noDriverAdjustReason;
	}

	public String getVehicleOwnerName() {
		return VehicleOwnerName;
	}

	public void setVehicleOwnerName(String vehicleOwnerName) {
		VehicleOwnerName = vehicleOwnerName;
	}

	public String getUsageTypeMessage() {
		return UsageTypeMessage;
	}

	public void setUsageTypeMessage(String usageTypeMessage) {
		UsageTypeMessage = usageTypeMessage;
	}

	public String getFleetMessage() {
		return FleetMessage;
	}

	public void setFleetMessage(String fleetMessage) {
		FleetMessage = fleetMessage;
	}

	public String getMileageFactorMessage() {
		return MileageFactorMessage;
	}

	public void setMileageFactorMessage(String mileageFactorMessage) {
		MileageFactorMessage = mileageFactorMessage;
	}
	

	/**
     * @param iSchema ����CIInsureDemandSchema
     */       
    public void setSchema(CIInsureDemandSchema iSchema)
    {
        this.ProposalNo = iSchema.getProposalNo();
        this.DemandNo = iSchema.getDemandNo();
        this.PolicyNo = iSchema.getPolicyNo();
        this.LicenseNo = iSchema.getLicenseNo();
        this.LicenseType = iSchema.getLicenseType();
        this.UseNatureCode = iSchema.getUseNatureCode();
        this.FrameNo = iSchema.getFrameNo();
        this.EngineNo = iSchema.getEngineNo();
        this.LicenseColorCode = iSchema.getLicenseColorCode();
        this.CarOwner = iSchema.getCarOwner();
        this.EnrollDate = iSchema.getEnrollDate();
        this.MakeDate = iSchema.getMakeDate();
        this.SeatCount = iSchema.getSeatCount();
        this.TonCount = iSchema.getTonCount();
        this.ValidCheckDate = iSchema.getValidCheckDate();
        this.ManufacturerName = iSchema.getManufacturerName();
        this.ModelCode = iSchema.getModelCode();
        this.BrandCName = iSchema.getBrandCName();
        this.BrandName = iSchema.getBrandName();
        this.CarKindCode = iSchema.getCarKindCode();
        this.CheckDate = iSchema.getCheckDate();
        this.EndValidDate = iSchema.getEndValidDate();
        this.CarStatus = iSchema.getCarStatus();
        this.Haulage = iSchema.getHaulage();
        this.StartDate = iSchema.getStartDate();
        this.EndDate = iSchema.getEndDate();
        this.Amount = iSchema.getAmount();
        this.Premium = iSchema.getPremium();
        this.BenchmarkPremium = iSchema.getBenchmarkPremium();
        this.PeccancyCoeff = iSchema.getPeccancyCoeff();
        this.ClaimCoeff = iSchema.getClaimCoeff();
        this.DriverCoeff = iSchema.getDriverCoeff();
        this.DistrictCoeff = iSchema.getDistrictCoeff();
        this.CommissionRate = iSchema.getCommissionRate();
        this.BasePremium = iSchema.getBasePremium();
        this.ComCode = iSchema.getComCode();
        this.OperatorCode = iSchema.getOperatorCode();
        this.DemandTime = iSchema.getDemandTime();
        this.Remark = iSchema.getRemark();
        this.Flag = iSchema.getFlag();
        
        this.LastYearStartDate = iSchema.getLastYearStartDate();
        this.LastYearEndDate = iSchema.getLastYearEndDate();
        
        this.BillDate = iSchema.getBillDate();
        this.ReinsureFlag = iSchema.getReinsureFlag();
        this.LastBillDate = iSchema.getLastBillDate();
        this.RateFloatFlag = iSchema.getRateFloatFlag();
        this.PeccancyAdjustReason = iSchema.getPeccancyAdjustReason();
        this.ClaimAdjustReason = iSchema.getClaimAdjustReason();
        this.DriverRateReason = iSchema.getDriverRateReason();
        this.DistrictRateReason = iSchema.getDistrictRateReason();
        this.ProconfirmSequenceNo = iSchema.getProconfirmSequenceNo();        
        this.ProconfirmStartDate = iSchema.getProconfirmStartDate();    
        this.ProconfirmEndDate = iSchema.getProconfirmEndDate();    
        
        this.RestricFlag = iSchema.getRestricFlag();
        this.PreferentialPremium = iSchema.getPreferentialPremium();
        this.PreferentialFormula = iSchema.getPreferentialFormula();
        this.PreferentialDay = iSchema.getPreferentialDay();
        
        
        this.SearchSequenceNo = iSchema.getSearchSequenceNo();
        this.SafeAdjust = iSchema.getSafeAdjust();
        this.NonclaimAdjust = iSchema.getNonclaimAdjust();
        this.LoyaltyAdjust = iSchema.getLoyaltyAdjust();
        
        
        this.UseTypeSource = iSchema.getUseTypeSource();
        
        
        this.LoyaltyAdjustReason=iSchema.getLoyaltyAdjustReason();
        this.QueryPastDate=iSchema.getQueryPastDate();
        this.TransferDate=iSchema.getTransferDate();
        
        
        this.BusiLastYearEndDate=iSchema.getBusiLastYearEndDate();
        this.BusiAdjustStart=iSchema.getBusiAdjustStart();
        this.BusiAdjustEnd=iSchema.getBusiAdjustEnd();
        this.PoWeight=iSchema.getPoWeight();
        
        
        this.ReCoverMsg=iSchema.getReCoverMsg();
        
		
        this.PolicyCoeff=iSchema.getPolicyCoeff();
        
        
        this.NoLoyaltyAdjustReason=iSchema.getNoLoyaltyAdjustReason();
        this.NoClaimAdjustReason=iSchema.getNoClaimAdjustReason();
        
        
        this.sendLastPolicyNo = iSchema.getSendLastPolicyNo();
        
        
        this.IP = iSchema.getIP();
        this.UsbKey = iSchema.getUsbKey();
        
        
        this.PeccancyStartDate = iSchema.getPeccancyStartDate();
        this.PeccancyEndDate = iSchema.getPeccancyEndDate();
        this.KindAdjustValue = iSchema.getKindAdjustValue();
        this.KindAdjustReason = iSchema.getKindAdjustReason();
        this.NoKindAdjustReason = iSchema.getNoKindAdjustReason();
        this.NoPeccancyReason = iSchema.getNoPeccancyReason();
        
      
        this.LastProducerCode = iSchema.getLastProducerCode();
        
        
        this.LastPolicyQueryDate = iSchema.getLastPolicyQueryDate();
        this.LastPolicyTotalPremium = iSchema.getLastPolicyTotalPremium();
        this.InsureInDoorValue = iSchema.getInsureInDoorValue();
        this.InsureInDoorReason = iSchema.getInsureInDoorReason();
        this.ClaimAmountValue = iSchema.getClaimAmountValue();
        this.ClaimAmountReason = iSchema.getClaimAmountReason();
        this.NoClaimAmountReason = iSchema.getNoClaimAmountReason();
        this.SpecificRiskValue = iSchema.getSpecificRiskValue();
        this.IndependentValue = iSchema.getIndependentValue();
        
        
        
        this.CheckCode = iSchema.getCheckCode();
        this.Question = iSchema.getQuestion();
        this.RenewalFlag = iSchema.getRenewalFlag();
        
        
        this.commissionrateupper  = iSchema.getCommissionrateupper();
        this.companycommissionrateupper   = iSchema.getCompanycommissionrateupper();
        this.claimeffectreason   = iSchema.getClaimeffectreason();
        this.newvehicleeffectreason   = iSchema.getNewvehicleeffectreason();
        this.producereffectreason  = iSchema.getProducereffectreason();
        
        this.CarOwnerMessage = iSchema.getCarOwnerMessage();
    	
        this.FuelType = iSchema.getFuelType();
    	
        
        this.ExhaustScale = iSchema.getExhaustScale();
        
        
        this.FundRate = iSchema.getFundRate();
        this.FundAmount = iSchema.getFundAmount();
        
        
        this.ReinsureFlagBom = iSchema.getReinsureFlagBom();
        
        
        this.TransferFlag = iSchema.getTransferFlag();
        this.HighRiskFlag = iSchema.getHighRiskFlag();
        this.EffectReason = iSchema.getEffectReason();
        this.LastyearCompanyId = iSchema.getLastyearCompanyId();
        
        
        this.LastModelCode = iSchema.getLastModelCode();
        this.LastModel = iSchema.getLastModel();
        this.LastPurchasePrice = iSchema.getLastPurchasePrice();
        
        
        this.RepeatLastYearStartDate = iSchema.getRepeatLastYearStartDate();
        this.RepeatLastYearEndDate = iSchema.getRepeatLastYearEndDate();
        this.RepeatBillDate = iSchema.getRepeatBillDate();
        this.RepeatEngineNo = iSchema.getRepeatEngineNo();
        this.RepeatInsurerCode = iSchema.getRepeatInsurerCode();
        this.RepeatLicensePlatNo = iSchema.getRepeatLicensePlatNo();
        this.RepeatPolicyNo = iSchema.getRepeatPolicyNo();
        this.RepeatVIN = iSchema.getRepeatVIN();
        
        
        this.RepeatInsurerArea = iSchema.getRepeatInsurerArea();
        
        
        this.ChannelType = iSchema.getChannelType();
        
        
        this.Wholeweight = iSchema.getWholeweight();
        this.Displacement = iSchema.getDisplacement();
        this.ChgOwnerDate = iSchema.getChgOwnerDate();
        this.ProducerType = iSchema.getProducerType();
        this.SalePrice = iSchema.getSalePrice();
        this.PmFuelType = iSchema.getPmFuelType();
        this.VehicleCategory = iSchema.getVehicleCategory();
        this.VehicleOwnerName = iSchema.getVehicleOwnerName();
        this.NoPeccancyAdjustReason = iSchema.getNoPeccancyAdjustReason();
        this.NoDriverAdjustReason = iSchema.getNoDriverAdjustReason();
        this.UsageTypeMessage = iSchema.getUsageTypeMessage();
        this.FleetMessage = iSchema.getFleetMessage();
        this.MileageFactorMessage = iSchema.getMileageFactorMessage();
        
    }
}
