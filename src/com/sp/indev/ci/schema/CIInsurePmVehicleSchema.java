package com.sp.indiv.ci.schema;
  
import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义交管车辆信息查询-CIInsurePmVehicleSchema的数据传输对象类
 * add by fanjiangtao 
 * create date 2014-01-24
 */
public class CIInsurePmVehicleSchema implements Serializable{
    
    private String PmVehicleID  = "";
    
    private String CarMark = "";
    
    private String VehicleType = "";
    
    private String RackNo = "";
    
    private String EngineNo = "";
    
    private String VehicleStyle = "";
    
    private String PmUseType = "";
    
    private String IneffectualDate = "";
    
    private String RejectDate = "";
    
    private String VehicleRegisterDate= "";
    
    private String LastCheckDate = "";
    
    private String TransferDate = "";
    
    private String WholeWeight = "";
    
    private String LimitLoadPerson = "";
    
    private String LimitLoad = "";
    
    private String Displacement = "";
    
    private String MadeFactory = "";
    
    private String VehicleModel = "";
    
    private String ProducerType = "";
    
    private String VehicleBrand1 = "";
    
    private String VehicleBrand2 = "";
    
    private String Haulage = "";
    
    private String VehicleColour = "";
    
    private String SalePrice = "";
    
    private String PmFuelType = "";
    
    private String Status = "";
    
    private String VehicleCategory = "";
    
    private String InsertDate = "";
    
    private String UpdateDate = "";
    
    private String ValidFlag = "";
    
    private String OperateSite = "";
    
    private String Flag = "";
    
    private String DemandNo="";
    
    private String OwnerName="";
    
    
    /**
     * 构造函数
     */       
    public CIInsurePmVehicleSchema(){
    }

    /**
     * 设置属性号牌号码
     * @param CarMark 号牌号码
     */
    public void setCarMark(String CarMark){
        this.CarMark = Str.rightTrim(CarMark);
    }

    /**
     * 获取属性号牌号码
     * @return 号牌号码
     */
    public String getCarMark(){
        return Str.rightTrim(CarMark);
    }

    /**
     * 设置属性号牌种类
     * @param VehicleType 号牌种类
     */
    public void setVehicleType(String VehicleType){
        this.VehicleType = Str.rightTrim(VehicleType);
    }

    /**
     * 获取属性号牌种类
     * @return 号牌种类
     */
    public String getVehicleType(){
        return Str.rightTrim(VehicleType);
    }

    /**
     * 设置属性车辆识别代号（车架号/VIN码）
     * @param PolicyNo XX号
     */
    public void setRackNo(String RackNo){
        this.RackNo = Str.rightTrim(RackNo);
    }

    /**
     * 获取属性车辆识别代号（车架号/VIN码）
     * @return 车辆识别代号（车架号/VIN码）
     */
    public String getRackNo(){
        return Str.rightTrim(RackNo);
    }

    /**
     * 设置属性发动机号
     * @param EngineNo 发动机号
     */
    public void setEngineNo(String EngineNo){
        this.EngineNo = Str.rightTrim(EngineNo);
    }

    /**
     * 获取属性发动机号
     * @return 发动机号
     */
    public String getEngineNo(){
        return Str.rightTrim(EngineNo);
    }

    /**
     * 设置属性交管车辆类型
     * @param VehicleStyle 交管车辆类型
     */
    public void setVehicleStyle(String VehicleStyle){
        this.VehicleStyle = Str.rightTrim(VehicleStyle);
    }

    /**
     * 获取属性交管车辆类型
     * @return 交管车辆类型
     */
    public String getVehicleStyle(){
        return Str.rightTrim(VehicleStyle);
    }

    /**
     * 设置属性交管使用性质代码
     * @param PmUseType 交管使用性质代码
     */
    public void setPmUseType(String PmUseType){
        this.PmUseType = Str.rightTrim(PmUseType);
    }

    /**
     * 获取属性交管使用性质代码
     * @return 交管使用性质代码
     */
    public String getPmUseType(){
        return Str.rightTrim(PmUseType);
    }

    /**
     * 设置属性检验有效日期止
     * @param IneffectualDate 检验有效日期止
     */
    public void setIneffectualDate(String IneffectualDate){
        ChgDate chgDate = new ChgDate(); 
        IneffectualDate = chgDate.toFormat(IneffectualDate);
        if  (IneffectualDate == null || IneffectualDate.length() == 0 ) 
        {
          this.IneffectualDate = "";
        }else
        {
         this.IneffectualDate = IneffectualDate.trim().substring(0,10);
        }
    }

    /**
     * 获取属性检验有效日期止
     * @return 检验有效日期止
     */
    public String getIneffectualDate(){
        return Str.rightTrim(IneffectualDate);
    }

    /**
     * 设置属性强制报废期止
     * @param RejectDate 强制报废期止
     */
    public void setRejectDate(String RejectDate){
        ChgDate chgDate = new ChgDate(); 
        RejectDate = chgDate.toFormat(RejectDate);
        if  (RejectDate == null || RejectDate.length() == 0 ) 
        {
          this.RejectDate = "";
        }else
        {
         this.RejectDate = RejectDate.trim().substring(0,10);
        }
    }

    /**
     * 获取属性强制报废期止
     * @return 强制报废期止
     */
    public String getRejectDate(){
        return Str.rightTrim(RejectDate);
    }

    /**
     * 设置属性车辆初始登记日期
     * @param VehicleRegisterDate 车辆初始登记日期
     */
    public void setVehicleRegisterDate(String VehicleRegisterDate){
        ChgDate chgDate = new ChgDate(); 
        VehicleRegisterDate = chgDate.toFormat(VehicleRegisterDate);
        if  (VehicleRegisterDate == null || VehicleRegisterDate.length() == 0 ) 
        {
          this.VehicleRegisterDate = "";
        }else
        {
         this.VehicleRegisterDate = VehicleRegisterDate.trim().substring(0,10);
        }
    }

    /**
     * 获取属性车辆初始登记日期
     * @return 车辆初始登记日期
     */
    public String getVehicleRegisterDate(){
        return Str.rightTrim(VehicleRegisterDate);
    }

    /**
     * 设置属性最近定检日期
     * @param LastCheckDate 最近定检日期
     */
    public void setLastCheckDate(String LastCheckDate){
        ChgDate chgDate = new ChgDate(); 
        LastCheckDate = chgDate.toFormat(LastCheckDate);
        if  (LastCheckDate == null || LastCheckDate.length() == 0 ) 
        {
          this.LastCheckDate = "";
        }else
        {
         this.LastCheckDate = LastCheckDate.trim().substring(0,10);
        }
    }

    /**
     * 获取属性最近定检日期
     * @return 最近定检日期
     */
    public String getLastCheckDate(){
        return Str.rightTrim(LastCheckDate);
    }

    /**
     * 设置属性转移登记日期
     * @param TransferDate 转移登记日期
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
     * 获取属性转移登记日期
     * @return 转移登记日期
     */
    public String getTransferDate(){
        return Str.rightTrim(TransferDate);
    }

    /**
     * 设置属性整备质量
     * @param WholeWeight 整备质量
     */
    public void setWholeWeight(String WholeWeight){
        this.WholeWeight = Str.rightTrim(WholeWeight);
    }

    /**
     * 获取属性整备质量
     * @return 整备质量
     */
    public String getWholeWeight(){
        return Str.rightTrim(WholeWeight);
    }

    /**
     * 设置属性核定载客人数
     * @param LimitLoadPerson 核定载客人数
     */
    public void setLimitLoadPerson(String LimitLoadPerson){
        this.LimitLoadPerson = Str.rightTrim(LimitLoadPerson);
    }

    /**
     * 获取属性核定载客人数
     * @return 核定载客人数
     */
    public String getLimitLoadPerson(){
        return Str.rightTrim(LimitLoadPerson);
    }

    /**
     * 设置属性核定载质量（千克）
     * @param LimitLoad 核定载质量（千克）
     */
    public void setLimitLoad(String LimitLoad){
        this.LimitLoad = Str.rightTrim(LimitLoad);
    }

    /**
     * 获取属性核定载质量（千克）
     * @return 核定载质量（千克）
     */
    public String getLimitLoad(){
        return Str.rightTrim(LimitLoad);
    }

    /**
     * 设置属性排量（毫升）
     * @param Displacement 排量（毫升）
     */
    public void setDisplacement(String Displacement){
        this.Displacement = Str.rightTrim(Displacement);
    }

    /**
     * 获取属性属性排量（毫升）
     * @return 属性排量（毫升）
     */
    public String getDisplacement(){
        return Str.rightTrim(Displacement);
    }

    /**
     * 设置属性制造厂名称
     * @param MadeFactory 制造厂名称
     */
    public void setMadeFactory(String MadeFactory){
        this.MadeFactory = Str.rightTrim(MadeFactory);
    }

    /**
     * 获取属性制造厂名称
     * @return 制造厂名称
     */
    public String getMadeFactory(){
        return Str.rightTrim(MadeFactory);
    }

    /**
     * 设置属性车辆型号
     * @param VehicleModel 车辆型号
     */
    public void setVehicleModel(String VehicleModel){
        this.VehicleModel = Str.rightTrim(VehicleModel);
    }

    /**
     * 获取属性车辆型号
     * @return 车辆型号
     */
    public String getVehicleModel(){
        return Str.rightTrim(VehicleModel);
    }

    /**
     * 设置属性渠道种类
     * @param ProducerType 渠道种类
     */
    public void setProducerType(String ProducerType){
        this.ProducerType = Str.rightTrim(ProducerType);
    }

    /**
     * 获取属性渠道种类
     * @return 渠道种类
     */
    public String getProducerType(){
        return Str.rightTrim(ProducerType);
    }

    /**
     * 设置属性中文品牌
     * @param VehicleBrand1 中文品牌
     */
    public void setVehicleBrand1(String VehicleBrand1){
        this.VehicleBrand1 = Str.rightTrim(VehicleBrand1);
    }

    /**
     * 获取属性中文品牌
     * @return 中文品牌
     */
    public String getVehicleBrand1(){
        return Str.rightTrim(VehicleBrand1);
    }

    /**
     * 设置属性英文品牌
     * @param VehicleBrand2 英文品牌
     */
    public void setVehicleBrand2(String VehicleBrand2){
        this.VehicleBrand2 = Str.rightTrim(VehicleBrand2);
    }

    /**
     * 获取属性英文品牌
     * @return 英文品牌
     */
    public String getVehicleBrand2(){
        return Str.rightTrim(VehicleBrand2);
    }

    /**
     * 设置属性准牵引总质量（千克）
     * @param Haulage 准牵引总质量（千克）
     */
    public void setHaulage(String Haulage){
        this.Haulage = Str.rightTrim(Haulage);
    }

    /**
     * 获取属性准牵引总质量（千克）
     * @return 准牵引总质量（千克）
     */
    public String getHaulage(){
        return Str.rightTrim(Haulage);
    }

    /**
     * 设置属性车身颜色
     * @param VehicleColour 车身颜色
     */
    public void setVehicleColour(String VehicleColour){
        this.VehicleColour = Str.rightTrim(VehicleColour);
    }

    /**
     * 获取属性车身颜色
     * @return 车身颜色
     */
    public String getVehicleColour(){
        return Str.rightTrim(VehicleColour);
    }

    /**
     * 设置属性销售价格
     * @param SalePrice 销售价格
     */
    public void setSalePrice(String SalePrice){
        this.SalePrice = Str.rightTrim(SalePrice);
    }

    /**
     * 获取属性销售价格
     * @return 销售价格
     */
    public String getSalePrice(){
        return Str.rightTrim(SalePrice);
    }

    /**
     * 设置属性交管能源种类
     * @param PmFuelType 交管能源种类
     */
    public void setPmFuelType(String PmFuelType){
        this.PmFuelType = Str.rightTrim(PmFuelType);
    }

    /**
     * 获取属性交管能源种类
     * @return 交管能源种类
     */
    public String getPmFuelType(){
        return Str.rightTrim(PmFuelType);
    }

    /**
     * 设置属性机动车状态代码
     * @param Status 机动车状态代码
     */
    public void setStatus(String Status){
        this.Status = Str.rightTrim(Status);
    }

    /**
     * 获取属性机动车状态代码
     * @return 机动车状态代码
     */
    public String getStatus(){
        return Str.rightTrim(Status);
    }

    /**
     * 设置属性平台车辆种类
     * @param VehicleCategory 平台车辆种类
     */
    public void setVehicleCategory(String VehicleCategory){
        this.VehicleCategory = Str.rightTrim(VehicleCategory);
    }

    /**
     * 获取属性平台车辆种类
     * @return 平台车辆种类
     */
    public String getVehicleCategory(){
        return Str.rightTrim(VehicleCategory);
    }

    /**
     * 设置属性插入时间
     * @param InsertDate 插入时间
     */
    public void setInsertDate(String InsertDate){
        ChgDate chgDate = new ChgDate(); 
        System.out.println("InsertDate " + InsertDate);
        InsertDate = chgDate.toFormat(InsertDate);
        System.out.println("InsertDate " + InsertDate);
        if  (InsertDate == null || InsertDate.length() == 0 ) 
        {
          this.InsertDate = "";
        }else
        {
         this.InsertDate = InsertDate.trim().substring(0,18);
        }
    }

    /**
     * 获取属性插入时间
     * @return 插入时间
     */
    public String getInsertDate(){
        return Str.rightTrim(InsertDate);
    }

    /**
     * 设置属性标志位
     * @param DemandNo 标志位
     */
    public void setDemandNo(String DemandNo){
        this.DemandNo = Str.rightTrim(DemandNo);
    }

    /**
     * 获取属性标志位
     * @return 标志位
     */
    public String getDemandNo(){
        return Str.rightTrim(DemandNo);
    }
    /**
     * 设置属性标志位
     * @param Flag 标志位
     */
    public void setFlag(String Flag){
        this.Flag = Str.rightTrim(Flag);
    }

    /**
     * 获取属性标志位
     * @return 标志位
     */
    public String getFlag(){
        return Str.rightTrim(Flag);
    }
    
    /**
     * 设置属性车主名称
     * @param OwnerName 车主名称
     */
    public void setOwnerName(String OwnerName){
        this.OwnerName = Str.rightTrim(OwnerName);
    }

    /**
     * 获取属性车主名称
     * @return 车主名称
     */
    public String getOwnerName(){
        return Str.rightTrim(OwnerName);
    }

    public String getUpdateDate() {
		return Str.rightTrim(UpdateDate);
	}

	public void setUpdateDate(String UpdateDate) {
		ChgDate chgDate = new ChgDate();
		UpdateDate = chgDate.toFormat(UpdateDate);
		if  (UpdateDate == null || UpdateDate.length() == 0 )
		{
			this.UpdateDate = "";
		}else{
			this.UpdateDate = UpdateDate.trim().substring(0,10);
		}
	}

	public String getValidFlag() {
		return ValidFlag;
	}

	public void setValidFlag(String validFlag) {
		ValidFlag = validFlag;
	}

	public String getOperateSite() {
		return OperateSite;
	}

	public void setOperateSite(String operateSite) {
		OperateSite = operateSite;
	}
	
	public String getPmVehicleID() {
		return PmVehicleID;
	}

	public void setPmVehicleID(String PmVehicleID) {
		this.PmVehicleID = PmVehicleID;
	}

    
    /**
     * @param iSchema 对象CIInsurePmVehicleSchema
     */       
    public void setSchema(CIInsurePmVehicleSchema iSchema)
    {
    	this.PmVehicleID = iSchema.getPmVehicleID();
        this.CarMark = iSchema.getCarMark();
        this.VehicleType = iSchema.getVehicleType();
        this.RackNo = iSchema.getRackNo();
        this.EngineNo = iSchema.getEngineNo();
        this.VehicleStyle = iSchema.getVehicleStyle();
        this.PmUseType = iSchema.getPmUseType();
        this.IneffectualDate = iSchema.getIneffectualDate();
        this.RejectDate = iSchema.getRejectDate();
        this.VehicleRegisterDate = iSchema.getVehicleRegisterDate();
        this.LastCheckDate = iSchema.getLastCheckDate();
        this.TransferDate = iSchema.getTransferDate();
        this.WholeWeight = iSchema.getWholeWeight();
        this.LimitLoadPerson = iSchema.getLimitLoadPerson();
        this.LimitLoad = iSchema.getLimitLoad();
        this.Displacement = iSchema.getDisplacement();
        this.MadeFactory = iSchema.getMadeFactory();
        this.VehicleModel = iSchema.getVehicleModel();
        this.ProducerType = iSchema.getProducerType();
        this.VehicleBrand1 = iSchema.getVehicleBrand1();
        this.VehicleBrand2 = iSchema.getVehicleBrand2();
        this.Haulage = iSchema.getHaulage();
        this.VehicleColour = iSchema.getVehicleColour();
        this.SalePrice = iSchema.getSalePrice();
        this.PmFuelType = iSchema.getPmFuelType();
        this.Status = iSchema.getStatus();
        this.VehicleCategory = iSchema.getVehicleCategory();
        this.InsertDate = iSchema.getInsertDate();
        this.UpdateDate = iSchema.getUpdateDate();
        this.ValidFlag = iSchema.getValidFlag();
        this.OperateSite = iSchema.getOperateSite();
        this.Flag = iSchema.getFlag();
        this.DemandNo = iSchema.getDemandNo();
        this.OwnerName = iSchema.getOwnerName();
    }
}
