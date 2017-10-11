package com.sp.indiv.ci.schema;
  
import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * �������Ĳ�ѯȷ�ϱ�-CIEndorValid�����ݴ��������
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author DtoGenerator
 * @version 1.0
 */
public class CIEndorValidSchema implements Serializable{
    
    private String ValidNo = "";
    
    private String DemandNo = "";
    
    private String EndorseNo = "";
    
    private String PolicyNo = "";
    
    private String ChgPremium = "";
    
    private String Ptext = "";
    
    private String ValidTime = "";
    
    private String ValidStatus = "";
    
    private String Flag = "";
    
    private String ResponseCode = "";
    
    private String ErrorMessage = "";
    
    
    
    private String AmendBasedPremium = "";
    
    private String AmendStandArdPremium = "";
    
    
    /**<<<<<< added by harry on 22/08/07 �����ظ�XX��־**/
    private String CoverageType = "";
    
    private String CoverageCode = "";
    
    private String PeccancyAdjustValue = "";
    
    private String ClaimAdjustValue = "";
    
    private String DriverRate = "";
    
    private String DistrictRate = "";
    
    private String AdditionRate = "";
    
    private String PeccancyAdjustReason = "";
    
    private String ClaimAdjustReason = "";
    
    private String DriverRateReason = "";
    
    private String DistrictRateReason = "";
    
    private String RateFloatFlag = "";
    
    private String LastBillDate = "";
    
    private String EndorType = "";
    
    private String QReinsureFlag = "";
    
    private String AReinsureFlag = "";
    /**<<<<<< added by liujia end 22/08/07 �����ظ�XX��־**/

    
    
    private String CurrentTax = "";
    
    private String FormerTax = "";
    
    private String LateFee = "";
    
    private String CancelTax = "";
    
    
    
    private String RestricFlag = "";
    
    private String PreferentialPremium = "";
    
    private String PreferentialFormula = "";
    
    private String PreferentialDay = "";
    
    
    
    private String TaxAmendPremium = "";
    
    private String TaxAmendDeclare = "";
    
    private String IsAmendTax="";
    
    
    private String SearchSequenceNo = "";
    
    
    
    private String QueryPastDate = "" ;
    private String TransferDate = "";
    private String LoyaltyAdjustReason="";
    private String NonclaimAdjust = "";
    private String LoyaltyAdjust = "";
    
    
    private String ReCoverMsg = "";   
    
    
    private String NoLoyaltyAdjustReason = "";	
    private String NoClaimAdjustReason = "";	
    
    
    private String ClaimRecordStartDate = "";
    private String ClaimRecordEndDate = "";
    private String PeccancyStartDate = "";
    private String PeccancyEndDate = "";
    private String KindAdjustValue = "";
    private String KindAdjustReason = "";
    private String NoKindAdjustReason = "";
    private String NoPeccancyReason = "";
    
    
    private String LastProducerCode = "";
    
    
    private String LastPolicyQueryDate = "";
    private String LastYearStartDate = "";
    private String LastYearEndDate = "";
    private String LastPolicyTotalPremium = "";
    private String InsureInDoorValue = "";
    private String InsureInDoorReason = "";
    private String ClaimAmountValue = "";
    private String ClaimAmountReason = "";
    private String NoClaimAmountReason = "";
    private String SpecificRiskValue = "";
    private String IndependentValue = "";
   	
    
    private String StartDate = "";  
    private String EndDate = "";    
    
    
    private String CarOwnerMessage = "";
	
    
    private String FundAmount = "";
    
    
    private String LastModelCode = "";  
    private String LastModel = "";  
    private String LastPurchasePrice = "";  
    
    
    private String LicensePlateNo = "";
    private String LicensePlateType = "";
    private String VIN = "";
    private String EngineNo = "";
    private String VehicleType = "";
    private String PmMotorUsageTypeCode = "";
    private String IneffectualDate = "";
    private String RejectDate = "";
    private String FirstRegisterDate = "";
    private String LastCheckDate = "";
    private String WholeWeight = "";
    private String RatedPassengerCapacity = "";
    private String Tonnage = "";
    private String Displacement = "";
    private String MadeFactory = "";
    private String Model = "";
    private String ProducerType = "";
    private String BrandCN = "";
    private String BrandEN = "";
    private String Haulage = "";
    private String VehicleColour = "";
    private String SalePrice = "";
    private String PmFuelType = "";
    private String Status = "";
    private String MotorTypeCode = "";
    private String VehicleOwnerName = "";
    private String UsageTypeMessage = "";
    private String FleetMessage = "";
    private String MileageFactorMessage = "";

    private String NoDriverAdjustReason = "";
    
    
	/**
     * ���캯��
     */       
    public CIEndorValidSchema(){
    }
    
    /**
     * ��������ȷ����
     * @param ValidNo ȷ����
     */
    public void setValidNo(String ValidNo){
        this.ValidNo = Str.rightTrim(ValidNo);
    }

    /**
     * ��ȡ����ȷ����
     * @return ȷ����
     */
    public String getValidNo(){
        return Str.rightTrim(ValidNo);
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
     * ����������������
     * @param EndorseNo ��������
     */
    public void setEndorseNo(String EndorseNo){
        this.EndorseNo = Str.rightTrim(EndorseNo);
    }

    /**
     * ��ȡ������������
     * @return ��������
     */
    public String getEndorseNo(){
        return Str.rightTrim(EndorseNo);
    }

    /**
     * ��������XX����
     * @param PolicyNo XX����
     */
    public void setPolicyNo(String PolicyNo){
        this.PolicyNo = Str.rightTrim(PolicyNo);
    }

    /**
     * ��ȡ����XX����
     * @return XX����
     */
    public String getPolicyNo(){
        return Str.rightTrim(PolicyNo);
    }

    /**
     * ������������Ӧ�˷�
     * @param ChgPremium ����Ӧ�˷�
     */
    public void setChgPremium(String ChgPremium){
        this.ChgPremium = Str.rightTrim(ChgPremium);
    }

    /**
     * ��ȡ��������Ӧ�˷�
     * @return ����Ӧ�˷�
     */
    public String getChgPremium(){
        return Str.rightTrim(ChgPremium);
    }

    /**
     * ������������
     * @param Ptext ����
     */
    public void setPtext(String Ptext){
        this.Ptext = Str.rightTrim(Ptext);
    }

    /**
     * ��ȡ��������
     * @return ����
     */
    public String getPtext(){
        return Str.rightTrim(Ptext);
    }

    /**
     * ��������ValidTime
     * @param ValidTime ValidTime
     */
    public void setValidTime(String ValidTime){
      ChgDate chgDate = new ChgDate(); 
      ValidTime = chgDate.toFormat(ValidTime);
      if  (ValidTime == null || ValidTime.length() == 0 ) 
      {
        this.ValidTime = "";
      }else
      {
       this.ValidTime = ValidTime.trim().substring(0,10);
      }
    }

    /**
     * ��ȡ����ValidTime
     * @return ValidTime
     */
    public String getValidTime(){
        return Str.rightTrim(ValidTime);
    }

    /**
     * ��������Ч��״̬
     * @param ValidStatus Ч��״̬
     */
    public void setValidStatus(String ValidStatus){
        this.ValidStatus = Str.rightTrim(ValidStatus);
    }

    /**
     * ��ȡ����Ч��״̬
     * @return Ч��״̬
     */
    public String getValidStatus(){
        return Str.rightTrim(ValidStatus);
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
     * ��ȡ���Դ�������
     * @return ��������
     */
    public String getErrorMessage()
    {
    	return Str.rightTrim(ErrorMessage);
    }
    
    /**
     * ��ȡ���Դ�������
     * @return ��������
     */
    public void setErrorMessage(String errorMessage)
    {
    	this.ErrorMessage = errorMessage;
    }
    
    /**
     * ��ȡ���Է���������
     * @return ����������
     */
    public String getResponseCode()
    {
    	return Str.rightTrim(ResponseCode);
    }
    
    /**
     * ��ȡ���Է���������
     * @return ����������
     */
    public void setResponseCode(String responseCode)
    {
    	this.ResponseCode = responseCode;
    }
    
    public String getAmendBasedPremium() {
		return AmendBasedPremium;
	}

	public void setAmendBasedPremium(String amendBasedPremium) {
		AmendBasedPremium = amendBasedPremium;
	}

	public String getAmendStandArdPremium() {
		return AmendStandArdPremium;
	}

	public void setAmendStandArdPremium(String amendStandArdPremium) {
		AmendStandArdPremium = amendStandArdPremium;
	}

    /**<<<<<< added by liujia on 22/08/07 �����ظ�XX��־**/
    /**
     * ��������XXXXX�����ʹ���
     * @param CoverageType XXXXX�����ʹ���
     */
    public void setCoverageType(String CoverageType){
        this.CoverageType = Str.rightTrim(CoverageType);
    }

    /**
     * ��ȡ����XXXXX�����ʹ���
     * @return XXXXX�����ʹ���
     */
    public String getCoverageType(){
        return Str.rightTrim(CoverageType);
    }

    /**
     * ��������XXXXX�ִ���
     * @param CoverageCode XXXXX�ִ���
     */
    public void setCoverageCode(String CoverageCode){
        this.CoverageCode = Str.rightTrim(CoverageCode);
    }

    /**
     * ��ȡ����XXXXX�ִ���
     * @return XXXXX�ִ���
     */
    public String getCoverageCode(){
        return Str.rightTrim(CoverageCode);
    }

    /**
     * ��������Υ������ϵ��
     * @param PeccancyAdjustValue Υ������ϵ��
     */
    public void setPeccancyAdjustValue(String PeccancyAdjustValue){
        this.PeccancyAdjustValue = Str.rightTrim(PeccancyAdjustValue);
    }

    /**
     * ��ȡ����Υ������ϵ��
     * @return Υ������ϵ��
     */
    public String getPeccancyAdjustValue(){
        return Str.rightTrim(PeccancyAdjustValue);
    }

    /**
     * ��������XXXXX����ϵ��
     * @param ClaimAdjustValue XXXXX����ϵ��
     */
    public void setClaimAdjustValue(String ClaimAdjustValue){
        this.ClaimAdjustValue = Str.rightTrim(ClaimAdjustValue);
    }

    /**
     * ��ȡ����XXXXX����ϵ��
     * @return XXXXX����ϵ��
     */
    public String getClaimAdjustValue(){
        return Str.rightTrim(ClaimAdjustValue);
    }

    /**
     * ��������ָ����ʻԱ����ϵ��
     * @param DriverRate ָ����ʻԱ����ϵ��
     */
    public void setDriverRate(String DriverRate){
        this.DriverRate = Str.rightTrim(DriverRate);
    }

    /**
     * ��ȡ����ָ����ʻԱ����ϵ��
     * @return ָ����ʻԱ����ϵ��
     */
    public String getDriverRate(){
        return Str.rightTrim(DriverRate);
    }

    /**
     * �������Ե���ϵ��
     * @param DistrictRate ����ϵ��
     */
    public void setDistrictRate(String DistrictRate){
        this.DistrictRate = Str.rightTrim(DistrictRate);
    }

    /**
     * ��ȡ���Ե���ϵ��
     * @return ����ϵ��
     */
    public String getDistrictRate(){
        return Str.rightTrim(DistrictRate);
    }

    /**
     * �������Ը��������ѱ���
     * @param AdditionRate ���������ѱ���
     */
    public void setAdditionRate(String AdditionRate){
        this.AdditionRate = Str.rightTrim(AdditionRate);
    }

    /**
     * ��ȡ���Ը��������ѱ���
     * @return ���������ѱ���
     */
    public String getAdditionRate(){
        return Str.rightTrim(AdditionRate);
    }

    /**
     * ��������Υ������ԭ��
     * @param PeccancyAdjustReason Υ������ԭ��
     */
    public void setPeccancyAdjustReason(String PeccancyAdjustReason){
        this.PeccancyAdjustReason = Str.rightTrim(PeccancyAdjustReason);
    }

    /**
     * ��ȡ����Υ������ԭ��
     * @return Υ������ԭ��
     */
    public String getPeccancyAdjustReason(){
        return Str.rightTrim(PeccancyAdjustReason);
    }

    /**
     * ��������XXXXX����ԭ��
     * @param ClaimAdjustReason XXXXX����ԭ��
     */
    public void setClaimAdjustReason(String ClaimAdjustReason){
        this.ClaimAdjustReason = Str.rightTrim(ClaimAdjustReason);
    }

    /**
     * ��ȡ����XXXXX����ԭ��
     * @return XXXXX����ԭ��
     */
    public String getClaimAdjustReason(){
        return Str.rightTrim(ClaimAdjustReason);
    }

    /**
     * �������Լ�ʻԱ����ԭ��
     * @param DriverRateReason ��ʻԱ����ԭ��
     */
    public void setDriverRateReason(String DriverRateReason){
        this.DriverRateReason = Str.rightTrim(DriverRateReason);
    }

    /**
     * ��ȡ���Լ�ʻԱ����ԭ��
     * @return ��ʻԱ����ԭ��
     */
    public String getDriverRateReason(){
        return Str.rightTrim(DriverRateReason);
    }

    /**
     * �������Ե�������ԭ��
     * @param DistrictRateReason ��������ԭ��
     */
    public void setDistrictRateReason(String DistrictRateReason){
        this.DistrictRateReason = Str.rightTrim(DistrictRateReason);
    }

    /**
     * ��ȡ���Ե�������ԭ��
     * @return ��������ԭ��
     */
    public String getDistrictRateReason(){
        return Str.rightTrim(DistrictRateReason);
    }

    /**
     * �������Բ�����ԭ��
     * @param RateFloatFlag ������ԭ��
     */
    public void setRateFloatFlag(String RateFloatFlag){
        this.RateFloatFlag = Str.rightTrim(RateFloatFlag);
    }

    /**
     * ��ȡ���Բ�����ԭ��
     * @return ������ԭ��
     */
    public String getRateFloatFlag(){
        return Str.rightTrim(RateFloatFlag);
    }

    /**
     * ������������XXǩ������
     * @param LastBillDate ����XXǩ������
     */
    public void setLastBillDate(String LastBillDate){
      ChgDate chgDate = new ChgDate(); 
      LastBillDate = chgDate.toFormat(LastBillDate);
      if  (LastBillDate == null || LastBillDate.length() == 0 ) 
      {
        this.LastBillDate = "";
      }else
      {
       this.LastBillDate = LastBillDate.trim().substring(0,10);
      }
    }

    /**
     * ��ȡ��������XXǩ������
     * @return ����XXǩ������
     */
    public String getLastBillDate(){
        return Str.rightTrim(LastBillDate);
    }

    /**
     * ����������������
     * @param EndorType ��������
     */
    public void setEndorType(String EndorType){
        this.EndorType = Str.rightTrim(EndorType);
    }

    /**
     * ��ȡ������������
     * @return ��������
     */
    public String getEndorType(){
        return Str.rightTrim(EndorType);
    }

    /**
     * �����������Ĳ�ѯʱ���ظ�XX��־
     * @param QReinsureFlag ���Ĳ�ѯʱ���ظ�XX��־
     */
    public void setQReinsureFlag(String QReinsureFlag){
        this.QReinsureFlag = Str.rightTrim(QReinsureFlag);
    }

    /**
     * ��ȡ�������Ĳ�ѯʱ���ظ�XX��־
     * @return ���Ĳ�ѯʱ���ظ�XX��־
     */
    public String getQReinsureFlag(){
        return Str.rightTrim(QReinsureFlag);
    }

    /**
     * ������������ȷ��ʱ���ظ�XX��־
     * @param AReinsureFlag ����ȷ��ʱ���ظ�XX��־
     */
    public void setAReinsureFlag(String AReinsureFlag){
        this.AReinsureFlag = Str.rightTrim(AReinsureFlag);
    }

    /**
     * ��ȡ��������ȷ��ʱ���ظ�XX��־
     * @return ����ȷ��ʱ���ظ�XX��־
     */
    public String getAReinsureFlag(){
        return Str.rightTrim(AReinsureFlag);
    }

    /**added by liujia on 22/08/07 �����ظ�XX��־ >>>>>>**/
    
    
    /**
     * �������Ե���Ӧ�ɳ���˰
     * @param CurrentTax ����Ӧ�ɳ���˰
     */
    public void setCurrentTax(String CurrentTax){
        this.CurrentTax = Str.rightTrim(CurrentTax);
    }

    /**
     * ��ȡ���Ե���Ӧ�ɳ���˰
     * @return ����Ӧ�ɳ���˰
     */
    public String getCurrentTax(){
        return Str.rightTrim(CurrentTax);
    }
    
    /**
     * �����������겹�ɳ���˰
     * @param FormerTax ���겹�ɳ���˰
     */
    public void setFormerTax(String FormerTax){
        this.FormerTax = Str.rightTrim(FormerTax);
    }

    /**
     * ��ȡ�������겹�ɳ���˰
     * @return ���겹�ɳ���˰
     */
    public String getFormerTax(){
        return Str.rightTrim(FormerTax);
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
     * ����������XXXXXӦ��˰
     * @param CancelTax ��XXXXXӦ��˰
     */
    public void setCancelTax(String CancelTax){
        this.CancelTax = Str.rightTrim(CancelTax);
    }

    /**
     * ��ȡ������XXXXXӦ��˰
     * @return ��XXXXXӦ��˰
     */
    public String getCancelTax(){
        return Str.rightTrim(CancelTax);
    }
    
    
    
    /**
     * ��������XX��˫�ű�־
     * @param RestricFlag XX��˫�ű�־
     */
    public void setRestricFlag(String RestricFlag){
        this.RestricFlag = Str.rightTrim(RestricFlag);
    }

    /**
     * ��ȡ����XX��˫�ű�־
     * @return XX��˫�ű�־
     */
    public String getRestricFlag(){
        return Str.rightTrim(RestricFlag);
    }
    
    /**
     * �������Լ���XX
     * @param PreferentialPremium ����XX
     */
    public void setPreferentialPremium(String PreferentialPremium){
        this.PreferentialPremium = Str.rightTrim(PreferentialPremium);
    }

    /**
     * ��ȡ���Լ���XX
     * @return ����XX
     */
    public String getPreferentialPremium(){
        return Str.rightTrim(PreferentialPremium);
    }
    
    /**
     * �������Լ���XX���㹫ʽ
     * @param PreferentialFormula ����XX���㹫ʽ
     */
    public void setPreferentialFormula(String PreferentialFormula){
        this.PreferentialFormula = Str.rightTrim(PreferentialFormula);
    }

    /**
     * ��ȡ���Լ���XX���㹫ʽ
     * @return ����XX���㹫ʽ
     */
    public String getPreferentialFormula(){
        return Str.rightTrim(PreferentialFormula);
    }
    
    /**
     * �������Լ���������
     * @param PreferentialDay ����������
     */
    public void setPreferentialDay(String PreferentialDay){
        this.PreferentialDay = Str.rightTrim(PreferentialDay);
    }

    /**
     * ��ȡ���Լ���������
     * @return ����������
     */
    public String getPreferentialDay(){
        return Str.rightTrim(PreferentialDay);
    }
    
    
    /**
     * ��������ƽ̨���ĳ���˰�ϼ�
     * @param TaxAmendPremium ƽ̨���ĳ���˰�ϼ�
     */
    public void setTaxAmendPremium(String TaxAmendPremium){
        this.TaxAmendPremium = TaxAmendPremium;
    }

    /**
     * ��ȡ����ƽ̨���ĳ���˰�ϼ�
     * @return ƽ̨���ĳ���˰�ϼ�
     */
    public String getTaxAmendPremium(){
        return TaxAmendPremium;
    }

    /**
     * ��������XX��/��ʼ�걨����(0����1���� )
     * @param TaxAmendDeclare XX��/��ʼ�걨����(0����1���� )
     */
    public void setTaxAmendDeclare(String TaxAmendDeclare){
        this.TaxAmendDeclare = TaxAmendDeclare;
    }

    /**
     * ��ȡ����XX��/��ʼ�걨����(0����1���� )
     * @return XX��/��ʼ�걨����(0����1���� )
     */
    public String getTaxAmendDeclare(){
        return TaxAmendDeclare;
    }
    /**
     * ��ȡ����XX��/�����ĳ���˰
     * @return XX��/�����ĳ���˰
     */
    public String getIsAmendTax(){
        return IsAmendTax;
    }
    /**
     * ��������XX��/�����ĳ���˰
     * @param TaxAmendDeclare XX��/�����ĳ���˰
     */
    public void setIsAmendTax(String isAmendTax){
        IsAmendTax=isAmendTax;
    }
    
    
	public String getSearchSequenceNo() {
		return Str.rightTrim(SearchSequenceNo);
	}

	public void setSearchSequenceNo(String searchSequenceNo) {
		SearchSequenceNo = Str.rightTrim(searchSequenceNo);
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
    
    
	public String getReCoverMsg() {
		return ReCoverMsg;
	}

	public void setReCoverMsg(String ReCoverMsg) {
		this.ReCoverMsg = Str.rightTrim(ReCoverMsg);
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
	
	public String getClaimRecordStartDate() {
		return ClaimRecordStartDate;
	}

	public void setClaimRecordStartDate(String claimRecordStartDate) {
		ClaimRecordStartDate = claimRecordStartDate;
	}
	
	public String getClaimRecordEndDate() {
		return ClaimRecordEndDate;
	}

	public void setClaimRecordEndDate(String claimRecordEndDate) {
		ClaimRecordEndDate = claimRecordEndDate;
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

	public String getLastYearStartDate() {
		return LastYearStartDate;
	}

	public void setLastYearStartDate(String lastYearStartDate) {
		LastYearStartDate = Str.rightTrim(lastYearStartDate);
	}

	public String getLastYearEndDate() {
		return LastYearEndDate;
	}

	public void setLastYearEndDate(String lastYearEndDate) {
		LastYearEndDate = Str.rightTrim(lastYearEndDate);
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
	
	
	public String getStartDate() {
		return StartDate;
	}

	public void setStartDate(String startDate) {
		StartDate = Str.rightTrim(startDate);
	}

	public String getEndDate() {
		return EndDate;
	}

	public void setEndDate(String endDate) {
		EndDate = Str.rightTrim(endDate);
	}
	
	
    public String getCarOwnerMessage() {
		return CarOwnerMessage;
	}

	public void setCarOwnerMessage(String carOwnerMessage) {
		CarOwnerMessage = Str.rightTrim(carOwnerMessage);
	}
    
	
	public String getFundAmount() {
		return FundAmount;
	}

	public void setFundAmount(String fundAmount) {
		this.FundAmount = Str.rightTrim(fundAmount);
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
	
    
    public String getLicensePlateNo() {
		return LicensePlateNo;
	}

	public void setLicensePlateNo(String licensePlateNo) {
		LicensePlateNo = licensePlateNo;
	}

	public String getLicensePlateType() {
		return LicensePlateType;
	}

	public void setLicensePlateType(String licensePlateType) {
		LicensePlateType = licensePlateType;
	}

	public String getVIN() {
		return VIN;
	}

	public void setVIN(String vIN) {
		VIN = vIN;
	}

	public String getEngineNo() {
		return EngineNo;
	}

	public void setEngineNo(String engineNo) {
		EngineNo = engineNo;
	}

	public String getVehicleType() {
		return VehicleType;
	}

	public void setVehicleType(String vehicleType) {
		VehicleType = vehicleType;
	}

	public String getPmMotorUsageTypeCode() {
		return PmMotorUsageTypeCode;
	}

	public void setPmMotorUsageTypeCode(String pmMotorUsageTypeCode) {
		PmMotorUsageTypeCode = pmMotorUsageTypeCode;
	}

	public String getIneffectualDate() {
		return IneffectualDate;
	}

	public void setIneffectualDate(String ineffectualDate) {
		IneffectualDate = ineffectualDate;
	}

	public String getRejectDate() {
		return RejectDate;
	}

	public void setRejectDate(String rejectDate) {
		RejectDate = rejectDate;
	}

	public String getFirstRegisterDate() {
		return FirstRegisterDate;
	}

	public void setFirstRegisterDate(String firstRegisterDate) {
		FirstRegisterDate = firstRegisterDate;
	}

	public String getLastCheckDate() {
		return LastCheckDate;
	}

	public void setLastCheckDate(String lastCheckDate) {
		LastCheckDate = lastCheckDate;
	}

	public String getWholeWeight() {
		return WholeWeight;
	}

	public void setWholeWeight(String wholeWeight) {
		WholeWeight = wholeWeight;
	}

	public String getRatedPassengerCapacity() {
		return RatedPassengerCapacity;
	}

	public void setRatedPassengerCapacity(String ratedPassengerCapacity) {
		RatedPassengerCapacity = ratedPassengerCapacity;
	}

	public String getTonnage() {
		return Tonnage;
	}

	public void setTonnage(String tonnage) {
		Tonnage = tonnage;
	}

	public String getDisplacement() {
		return Displacement;
	}

	public void setDisplacement(String displacement) {
		Displacement = displacement;
	}

	public String getMadeFactory() {
		return MadeFactory;
	}

	public void setMadeFactory(String madeFactory) {
		MadeFactory = madeFactory;
	}

	public String getModel() {
		return Model;
	}

	public void setModel(String model) {
		Model = model;
	}

	public String getProducerType() {
		return ProducerType;
	}

	public void setProducerType(String producerType) {
		ProducerType = producerType;
	}

	public String getBrandCN() {
		return BrandCN;
	}

	public void setBrandCN(String brandCN) {
		BrandCN = brandCN;
	}

	public String getBrandEN() {
		return BrandEN;
	}

	public void setBrandEN(String brandEN) {
		BrandEN = brandEN;
	}

	public String getHaulage() {
		return Haulage;
	}

	public void setHaulage(String haulage) {
		Haulage = haulage;
	}

	public String getVehicleColour() {
		return VehicleColour;
	}

	public void setVehicleColour(String vehicleColour) {
		VehicleColour = vehicleColour;
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

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getMotorTypeCode() {
		return MotorTypeCode;
	}

	public void setMotorTypeCode(String motorTypeCode) {
		MotorTypeCode = motorTypeCode;
	}

	public String getVehicleOwnerName() {
		return VehicleOwnerName;
	}

	public void setVehicleOwnerName(String vehicleOwnerName) {
		VehicleOwnerName = vehicleOwnerName;
	}

	public String getNoDriverAdjustReason() {
		return NoDriverAdjustReason;
	}

	public void setNoDriverAdjustReason(String noDriverAdjustReason) {
		NoDriverAdjustReason = noDriverAdjustReason;
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
     * @param iSchema ����CIEndorValidSchema
     */       
    public void setSchema(CIEndorValidSchema iSchema)
    {
        this.ValidNo = iSchema.getValidNo();
        this.DemandNo = iSchema.getDemandNo();
        this.EndorseNo = iSchema.getEndorseNo();
        this.PolicyNo = iSchema.getPolicyNo();
        this.ChgPremium = iSchema.getChgPremium();
        this.Ptext = iSchema.getPtext();
        this.ValidTime = iSchema.getValidTime();
        this.ValidStatus = iSchema.getValidStatus();
        this.AmendBasedPremium = iSchema.getAmendBasedPremium();
        this.AmendStandArdPremium = iSchema.getAmendStandArdPremium();
        this.Flag = iSchema.getFlag();
        /*modi by liujia start*/
        this.CoverageType = iSchema.getCoverageType();
        this.CoverageCode = iSchema.getCoverageCode();
        this.PeccancyAdjustValue = iSchema.getPeccancyAdjustValue();
        this.ClaimAdjustValue = iSchema.getClaimAdjustValue();
        this.DriverRate = iSchema.getDriverRate();
        this.DistrictRate = iSchema.getDistrictRate();
        this.AdditionRate = iSchema.getAdditionRate();
        this.PeccancyAdjustReason = iSchema.getPeccancyAdjustReason();
        this.ClaimAdjustReason = iSchema.getClaimAdjustReason();
        this.DriverRateReason = iSchema.getDriverRateReason();
        this.DistrictRateReason = iSchema.getDistrictRateReason();
        this.RateFloatFlag = iSchema.getRateFloatFlag();
        this.LastBillDate = iSchema.getLastBillDate();
        this.EndorType = iSchema.getEndorType();
        this.QReinsureFlag = iSchema.getQReinsureFlag();
        this.AReinsureFlag = iSchema.getAReinsureFlag();
        /*modi by liujia start*/
        
        this.CurrentTax = iSchema.getCurrentTax();
        this.FormerTax  = iSchema.getFormerTax();
        this.LateFee    = iSchema.getLateFee();
        this.CancelTax  = iSchema.getCancelTax();
        
        
        this.RestricFlag         = iSchema.getRestricFlag();
        this.PreferentialPremium = iSchema.getPreferentialPremium();
        this.PreferentialFormula = iSchema.getPreferentialFormula();
        this.PreferentialDay     = iSchema.getPreferentialDay();
        
        
        this.TaxAmendPremium = iSchema.getTaxAmendPremium();
        this.TaxAmendDeclare = iSchema.getTaxAmendDeclare();
        this.IsAmendTax=iSchema.getIsAmendTax();
        
        
        this.SearchSequenceNo = iSchema.getSearchSequenceNo();
        
        
        this.LoyaltyAdjustReason=iSchema.getLoyaltyAdjustReason();
        this.QueryPastDate=iSchema.getQueryPastDate();
        this.TransferDate=iSchema.getTransferDate();
        this.NonclaimAdjust=iSchema.getNonclaimAdjust();
        this.LoyaltyAdjust=iSchema.getLoyaltyAdjust();
        
        
        this.ReCoverMsg=iSchema.getReCoverMsg();
        
        
        this.NoLoyaltyAdjustReason=iSchema.getNoLoyaltyAdjustReason();
        this.NoClaimAdjustReason=iSchema.getNoClaimAdjustReason();
        
        
        this.ClaimRecordStartDate = iSchema.getClaimRecordStartDate();
        this.ClaimRecordEndDate = iSchema.getClaimRecordEndDate();
        this.PeccancyStartDate = iSchema.getPeccancyStartDate();
        this.PeccancyEndDate = iSchema.getPeccancyEndDate();
        this.KindAdjustValue = iSchema.getKindAdjustValue();
        this.KindAdjustReason = iSchema.getKindAdjustReason();
        this.NoKindAdjustReason = iSchema.getNoKindAdjustReason();
        this.NoPeccancyReason = iSchema.getNoPeccancyReason();
        
        
        this.LastProducerCode = iSchema.getLastProducerCode();
        
        
        this.LastPolicyQueryDate = iSchema.getLastPolicyQueryDate();
        this.LastYearStartDate = iSchema.getLastYearStartDate();
        this.LastYearEndDate = iSchema.getLastYearEndDate();
        this.LastPolicyTotalPremium = iSchema.getLastPolicyTotalPremium();
        this.InsureInDoorValue = iSchema.getInsureInDoorValue();
        this.InsureInDoorReason = iSchema.getInsureInDoorReason();
        this.ClaimAmountValue = iSchema.getClaimAmountValue();
        this.ClaimAmountReason = iSchema.getClaimAmountReason();
        this.NoClaimAmountReason = iSchema.getNoClaimAmountReason();
        this.SpecificRiskValue = iSchema.getSpecificRiskValue();
        this.IndependentValue = iSchema.getIndependentValue();
        
        
        this.StartDate = iSchema.getStartDate();
        this.EndDate = iSchema.getEndDate();
        
        this.CarOwnerMessage = iSchema.getCarOwnerMessage();
        
        this.FundAmount = iSchema.getFundAmount();
        
        
        this.LastModelCode = iSchema.getLastModelCode();
        this.LastModel = iSchema.getLastModel();
        this.LastPurchasePrice = iSchema.getLastPurchasePrice();
        
        
        this.LicensePlateNo = iSchema.getLicensePlateNo();
        this.LicensePlateType = iSchema.getLicensePlateType();
        this.VIN = iSchema.getVIN();
        this.EngineNo = iSchema.getEngineNo();
        this.VehicleType = iSchema.getVehicleType();
        this.PmMotorUsageTypeCode = iSchema.getPmMotorUsageTypeCode();
        this.IneffectualDate = iSchema.getIneffectualDate();
        this.RejectDate = iSchema.getRejectDate();
        this.FirstRegisterDate = iSchema.getFirstRegisterDate();
        this.LastCheckDate = iSchema.getLastCheckDate();
        this.WholeWeight = iSchema.getWholeWeight();
        this.RatedPassengerCapacity = iSchema.getRatedPassengerCapacity();
        this.Tonnage = iSchema.getTonnage();
        this.Displacement = iSchema.getDisplacement();
        this.MadeFactory = iSchema.getMadeFactory();
        this.Model = iSchema.getModel();
        this.ProducerType = iSchema.getProducerType();
        this.BrandCN = iSchema.getBrandCN();
        this.BrandEN = iSchema.getBrandEN();
        this.Haulage = iSchema.getHaulage();
        this.VehicleColour = iSchema.getVehicleColour();
        this.SalePrice = iSchema.getSalePrice();
        this.PmFuelType = iSchema.getPmFuelType();
        this.Status = iSchema.getStatus();
        this.MotorTypeCode = iSchema.getMotorTypeCode();
        this.VehicleOwnerName = iSchema.getVehicleOwnerName();

        this.UsageTypeMessage = iSchema.getUsageTypeMessage();
        this.FleetMessage = iSchema.getFleetMessage();
        this.MileageFactorMessage = iSchema.getMileageFactorMessage();

        this.NoDriverAdjustReason = iSchema.getNoDriverAdjustReason();
        
    }
}
