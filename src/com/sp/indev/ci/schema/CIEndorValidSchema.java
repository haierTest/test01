package com.sp.indiv.ci.schema;
  
import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义批改查询确认表-CIEndorValid的数据传输对象类
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
    
    
    /**<<<<<< added by harry on 22/08/07 新增重复XX标志**/
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
    /**<<<<<< added by liujia end 22/08/07 新增重复XX标志**/

    
    
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
     * 构造函数
     */       
    public CIEndorValidSchema(){
    }
    
    /**
     * 设置属性确认码
     * @param ValidNo 确认码
     */
    public void setValidNo(String ValidNo){
        this.ValidNo = Str.rightTrim(ValidNo);
    }

    /**
     * 获取属性确认码
     * @return 确认码
     */
    public String getValidNo(){
        return Str.rightTrim(ValidNo);
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
     * 设置属性批单号码
     * @param EndorseNo 批单号码
     */
    public void setEndorseNo(String EndorseNo){
        this.EndorseNo = Str.rightTrim(EndorseNo);
    }

    /**
     * 获取属性批单号码
     * @return 批单号码
     */
    public String getEndorseNo(){
        return Str.rightTrim(EndorseNo);
    }

    /**
     * 设置属性XX号码
     * @param PolicyNo XX号码
     */
    public void setPolicyNo(String PolicyNo){
        this.PolicyNo = Str.rightTrim(PolicyNo);
    }

    /**
     * 获取属性XX号码
     * @return XX号码
     */
    public String getPolicyNo(){
        return Str.rightTrim(PolicyNo);
    }

    /**
     * 设置属性批改应退费
     * @param ChgPremium 批改应退费
     */
    public void setChgPremium(String ChgPremium){
        this.ChgPremium = Str.rightTrim(ChgPremium);
    }

    /**
     * 获取属性批改应退费
     * @return 批改应退费
     */
    public String getChgPremium(){
        return Str.rightTrim(ChgPremium);
    }

    /**
     * 设置属性批文
     * @param Ptext 批文
     */
    public void setPtext(String Ptext){
        this.Ptext = Str.rightTrim(Ptext);
    }

    /**
     * 获取属性批文
     * @return 批文
     */
    public String getPtext(){
        return Str.rightTrim(Ptext);
    }

    /**
     * 设置属性ValidTime
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
     * 获取属性ValidTime
     * @return ValidTime
     */
    public String getValidTime(){
        return Str.rightTrim(ValidTime);
    }

    /**
     * 设置属性效力状态
     * @param ValidStatus 效力状态
     */
    public void setValidStatus(String ValidStatus){
        this.ValidStatus = Str.rightTrim(ValidStatus);
    }

    /**
     * 获取属性效力状态
     * @return 效力状态
     */
    public String getValidStatus(){
        return Str.rightTrim(ValidStatus);
    }

    /**
     * 设置属性状态字段
     * @param Flag 状态字段
     */
    public void setFlag(String Flag){
        this.Flag = Flag;
    }

    /**
     * 获取属性状态字段
     * @return 状态字段
     */
    public String getFlag(){
        return Flag;
    }
    
    /**
     * 获取属性错误描述
     * @return 错误描述
     */
    public String getErrorMessage()
    {
    	return Str.rightTrim(ErrorMessage);
    }
    
    /**
     * 获取属性错误描述
     * @return 错误描述
     */
    public void setErrorMessage(String errorMessage)
    {
    	this.ErrorMessage = errorMessage;
    }
    
    /**
     * 获取属性返回码类型
     * @return 返回码类型
     */
    public String getResponseCode()
    {
    	return Str.rightTrim(ResponseCode);
    }
    
    /**
     * 获取属性返回码类型
     * @return 返回码类型
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

    /**<<<<<< added by liujia on 22/08/07 新增重复XX标志**/
    /**
     * 设置属性XXXXX种类型代码
     * @param CoverageType XXXXX种类型代码
     */
    public void setCoverageType(String CoverageType){
        this.CoverageType = Str.rightTrim(CoverageType);
    }

    /**
     * 获取属性XXXXX种类型代码
     * @return XXXXX种类型代码
     */
    public String getCoverageType(){
        return Str.rightTrim(CoverageType);
    }

    /**
     * 设置属性XXXXX种代码
     * @param CoverageCode XXXXX种代码
     */
    public void setCoverageCode(String CoverageCode){
        this.CoverageCode = Str.rightTrim(CoverageCode);
    }

    /**
     * 获取属性XXXXX种代码
     * @return XXXXX种代码
     */
    public String getCoverageCode(){
        return Str.rightTrim(CoverageCode);
    }

    /**
     * 设置属性违法调整系数
     * @param PeccancyAdjustValue 违法调整系数
     */
    public void setPeccancyAdjustValue(String PeccancyAdjustValue){
        this.PeccancyAdjustValue = Str.rightTrim(PeccancyAdjustValue);
    }

    /**
     * 获取属性违法调整系数
     * @return 违法调整系数
     */
    public String getPeccancyAdjustValue(){
        return Str.rightTrim(PeccancyAdjustValue);
    }

    /**
     * 设置属性XXXXX调整系数
     * @param ClaimAdjustValue XXXXX调整系数
     */
    public void setClaimAdjustValue(String ClaimAdjustValue){
        this.ClaimAdjustValue = Str.rightTrim(ClaimAdjustValue);
    }

    /**
     * 获取属性XXXXX调整系数
     * @return XXXXX调整系数
     */
    public String getClaimAdjustValue(){
        return Str.rightTrim(ClaimAdjustValue);
    }

    /**
     * 设置属性指定驾驶员调整系数
     * @param DriverRate 指定驾驶员调整系数
     */
    public void setDriverRate(String DriverRate){
        this.DriverRate = Str.rightTrim(DriverRate);
    }

    /**
     * 获取属性指定驾驶员调整系数
     * @return 指定驾驶员调整系数
     */
    public String getDriverRate(){
        return Str.rightTrim(DriverRate);
    }

    /**
     * 设置属性地区系数
     * @param DistrictRate 地区系数
     */
    public void setDistrictRate(String DistrictRate){
        this.DistrictRate = Str.rightTrim(DistrictRate);
    }

    /**
     * 获取属性地区系数
     * @return 地区系数
     */
    public String getDistrictRate(){
        return Str.rightTrim(DistrictRate);
    }

    /**
     * 设置属性附加手续费比例
     * @param AdditionRate 附加手续费比例
     */
    public void setAdditionRate(String AdditionRate){
        this.AdditionRate = Str.rightTrim(AdditionRate);
    }

    /**
     * 获取属性附加手续费比例
     * @return 附加手续费比例
     */
    public String getAdditionRate(){
        return Str.rightTrim(AdditionRate);
    }

    /**
     * 设置属性违法浮动原因
     * @param PeccancyAdjustReason 违法浮动原因
     */
    public void setPeccancyAdjustReason(String PeccancyAdjustReason){
        this.PeccancyAdjustReason = Str.rightTrim(PeccancyAdjustReason);
    }

    /**
     * 获取属性违法浮动原因
     * @return 违法浮动原因
     */
    public String getPeccancyAdjustReason(){
        return Str.rightTrim(PeccancyAdjustReason);
    }

    /**
     * 设置属性XXXXX浮动原因
     * @param ClaimAdjustReason XXXXX浮动原因
     */
    public void setClaimAdjustReason(String ClaimAdjustReason){
        this.ClaimAdjustReason = Str.rightTrim(ClaimAdjustReason);
    }

    /**
     * 获取属性XXXXX浮动原因
     * @return XXXXX浮动原因
     */
    public String getClaimAdjustReason(){
        return Str.rightTrim(ClaimAdjustReason);
    }

    /**
     * 设置属性驾驶员浮动原因
     * @param DriverRateReason 驾驶员浮动原因
     */
    public void setDriverRateReason(String DriverRateReason){
        this.DriverRateReason = Str.rightTrim(DriverRateReason);
    }

    /**
     * 获取属性驾驶员浮动原因
     * @return 驾驶员浮动原因
     */
    public String getDriverRateReason(){
        return Str.rightTrim(DriverRateReason);
    }

    /**
     * 设置属性地区浮动原因
     * @param DistrictRateReason 地区浮动原因
     */
    public void setDistrictRateReason(String DistrictRateReason){
        this.DistrictRateReason = Str.rightTrim(DistrictRateReason);
    }

    /**
     * 获取属性地区浮动原因
     * @return 地区浮动原因
     */
    public String getDistrictRateReason(){
        return Str.rightTrim(DistrictRateReason);
    }

    /**
     * 设置属性不浮动原因
     * @param RateFloatFlag 不浮动原因
     */
    public void setRateFloatFlag(String RateFloatFlag){
        this.RateFloatFlag = Str.rightTrim(RateFloatFlag);
    }

    /**
     * 获取属性不浮动原因
     * @return 不浮动原因
     */
    public String getRateFloatFlag(){
        return Str.rightTrim(RateFloatFlag);
    }

    /**
     * 设置属性上张XX签单日期
     * @param LastBillDate 上张XX签单日期
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
     * 获取属性上张XX签单日期
     * @return 上张XX签单日期
     */
    public String getLastBillDate(){
        return Str.rightTrim(LastBillDate);
    }

    /**
     * 设置属性批改类型
     * @param EndorType 批改类型
     */
    public void setEndorType(String EndorType){
        this.EndorType = Str.rightTrim(EndorType);
    }

    /**
     * 获取属性批改类型
     * @return 批改类型
     */
    public String getEndorType(){
        return Str.rightTrim(EndorType);
    }

    /**
     * 设置属性批改查询时的重复XX标志
     * @param QReinsureFlag 批改查询时的重复XX标志
     */
    public void setQReinsureFlag(String QReinsureFlag){
        this.QReinsureFlag = Str.rightTrim(QReinsureFlag);
    }

    /**
     * 获取属性批改查询时的重复XX标志
     * @return 批改查询时的重复XX标志
     */
    public String getQReinsureFlag(){
        return Str.rightTrim(QReinsureFlag);
    }

    /**
     * 设置属性批改确认时的重复XX标志
     * @param AReinsureFlag 批改确认时的重复XX标志
     */
    public void setAReinsureFlag(String AReinsureFlag){
        this.AReinsureFlag = Str.rightTrim(AReinsureFlag);
    }

    /**
     * 获取属性批改确认时的重复XX标志
     * @return 批改确认时的重复XX标志
     */
    public String getAReinsureFlag(){
        return Str.rightTrim(AReinsureFlag);
    }

    /**added by liujia on 22/08/07 新增重复XX标志 >>>>>>**/
    
    
    /**
     * 设置属性当年应缴车船税
     * @param CurrentTax 当年应缴车船税
     */
    public void setCurrentTax(String CurrentTax){
        this.CurrentTax = Str.rightTrim(CurrentTax);
    }

    /**
     * 获取属性当年应缴车船税
     * @return 当年应缴车船税
     */
    public String getCurrentTax(){
        return Str.rightTrim(CurrentTax);
    }
    
    /**
     * 设置属性往年补缴车船税
     * @param FormerTax 往年补缴车船税
     */
    public void setFormerTax(String FormerTax){
        this.FormerTax = Str.rightTrim(FormerTax);
    }

    /**
     * 获取属性往年补缴车船税
     * @return 往年补缴车船税
     */
    public String getFormerTax(){
        return Str.rightTrim(FormerTax);
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
     * 设置属性退XXXXX应退税
     * @param CancelTax 退XXXXX应退税
     */
    public void setCancelTax(String CancelTax){
        this.CancelTax = Str.rightTrim(CancelTax);
    }

    /**
     * 获取属性退XXXXX应退税
     * @return 退XXXXX应退税
     */
    public String getCancelTax(){
        return Str.rightTrim(CancelTax);
    }
    
    
    
    /**
     * 设置属性XX单双号标志
     * @param RestricFlag XX单双号标志
     */
    public void setRestricFlag(String RestricFlag){
        this.RestricFlag = Str.rightTrim(RestricFlag);
    }

    /**
     * 获取属性XX单双号标志
     * @return XX单双号标志
     */
    public String getRestricFlag(){
        return Str.rightTrim(RestricFlag);
    }
    
    /**
     * 设置属性减免XX
     * @param PreferentialPremium 减免XX
     */
    public void setPreferentialPremium(String PreferentialPremium){
        this.PreferentialPremium = Str.rightTrim(PreferentialPremium);
    }

    /**
     * 获取属性减免XX
     * @return 减免XX
     */
    public String getPreferentialPremium(){
        return Str.rightTrim(PreferentialPremium);
    }
    
    /**
     * 设置属性减免XX计算公式
     * @param PreferentialFormula 减免XX计算公式
     */
    public void setPreferentialFormula(String PreferentialFormula){
        this.PreferentialFormula = Str.rightTrim(PreferentialFormula);
    }

    /**
     * 获取属性减免XX计算公式
     * @return 减免XX计算公式
     */
    public String getPreferentialFormula(){
        return Str.rightTrim(PreferentialFormula);
    }
    
    /**
     * 设置属性减免总天数
     * @param PreferentialDay 减免总天数
     */
    public void setPreferentialDay(String PreferentialDay){
        this.PreferentialDay = Str.rightTrim(PreferentialDay);
    }

    /**
     * 获取属性减免总天数
     * @return 减免总天数
     */
    public String getPreferentialDay(){
        return Str.rightTrim(PreferentialDay);
    }
    
    
    /**
     * 设置属性平台批改车船税合计
     * @param TaxAmendPremium 平台批改车船税合计
     */
    public void setTaxAmendPremium(String TaxAmendPremium){
        this.TaxAmendPremium = TaxAmendPremium;
    }

    /**
     * 获取属性平台批改车船税合计
     * @return 平台批改车船税合计
     */
    public String getTaxAmendPremium(){
        return TaxAmendPremium;
    }

    /**
     * 设置属性XX是/否开始申报流程(0：否1：是 )
     * @param TaxAmendDeclare XX是/否开始申报流程(0：否1：是 )
     */
    public void setTaxAmendDeclare(String TaxAmendDeclare){
        this.TaxAmendDeclare = TaxAmendDeclare;
    }

    /**
     * 获取属性XX是/否开始申报流程(0：否1：是 )
     * @return XX是/否开始申报流程(0：否1：是 )
     */
    public String getTaxAmendDeclare(){
        return TaxAmendDeclare;
    }
    /**
     * 获取属性XX是/否批改车船税
     * @return XX是/否批改车船税
     */
    public String getIsAmendTax(){
        return IsAmendTax;
    }
    /**
     * 设置属性XX是/否批改车船税
     * @param TaxAmendDeclare XX是/否批改车船税
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
     * 设置属性状态字段
     * @param Flag 状态字段
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
     * 获取属性状态字段
     * @return 状态字段
     */
    public String getQueryPastDate(){
        return Str.rightTrim(QueryPastDate);
    }
    /**
     * 设置属性状态字段
     * @param Flag 状态字段
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
     * 获取属性状态字段
     * @return 状态字段
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
     * @param iSchema 对象CIEndorValidSchema
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
