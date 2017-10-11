package com.sp.indiv.ci.schema;

import java.io.Serializable;

/**
 * 定义prpNewCarRecord的数据传输对象类
 * 从pdm中取数据库信息,根据数据库表生成对应的Dto或Schema类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-12-22</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList 1.改造Schema层类生成工具继承关系方法;
 */
public class PrpNewCarRecordSchema implements Serializable{
    
    private String EngineNo = "";
    
    private String RackNo = "";
    
    private String Owner = "";
    
    private String CertiType = "";
    
    private String CertiCode = "";
    
    private String PCVehicleCategory = "";
    
    private String LimitLodaPerson = "";
    
    private String LimitLoda = "";
    
    private String POWeight = "";
    
    private String ExhaustCapacity = "";
    
    private String FuelType = "";
    
    private String CertificateType = "";
    
    private String CertificateNO = "";
    
    private String CertifycateDate = "";
    
    private String CarRecordDate = "";
    
    private String UserCode = "";

    /**
     * 构造函数
     */       
    public PrpNewCarRecordSchema(){
    }

    /**
     * 设置属性EngineNo
     * @param EngineNo EngineNo
     */
    public void setEngineNo(String EngineNo){
        this.EngineNo = EngineNo;
    }

    /**
     * 获取属性EngineNo
     * @return EngineNo
     */
    public String getEngineNo(){
        return EngineNo;
    }

    /**
     * 设置属性RackNo
     * @param RackNo RackNo
     */
    public void setRackNo(String RackNo){
        this.RackNo = RackNo;
    }

    /**
     * 获取属性RackNo
     * @return RackNo
     */
    public String getRackNo(){
        return RackNo;
    }

    /**
     * 设置属性Owner
     * @param Owner Owner
     */
    public void setOwner(String Owner){
        this.Owner = Owner;
    }

    /**
     * 获取属性Owner
     * @return Owner
     */
    public String getOwner(){
        return Owner;
    }

    /**
     * 设置属性CertiType
     * @param CertiType CertiType
     */
    public void setCertiType(String CertiType){
        this.CertiType = CertiType;
    }

    /**
     * 获取属性CertiType
     * @return CertiType
     */
    public String getCertiType(){
        return CertiType;
    }

    /**
     * 设置属性CertiCode
     * @param CertiCode CertiCode
     */
    public void setCertiCode(String CertiCode){
        this.CertiCode = CertiCode;
    }

    /**
     * 获取属性CertiCode
     * @return CertiCode
     */
    public String getCertiCode(){
        return CertiCode;
    }

    /**
     * 设置属性PCVehicleCategory
     * @param PCVehicleCategory PCVehicleCategory
     */
    public void setPCVehicleCategory(String PCVehicleCategory){
        this.PCVehicleCategory = PCVehicleCategory;
    }

    /**
     * 获取属性PCVehicleCategory
     * @return PCVehicleCategory
     */
    public String getPCVehicleCategory(){
        return PCVehicleCategory;
    }

    /**
     * 设置属性LimitLodaPerson
     * @param LimitLodaPerson LimitLodaPerson
     */
    public void setLimitLodaPerson(String LimitLodaPerson){
        this.LimitLodaPerson = LimitLodaPerson;
    }

    /**
     * 获取属性LimitLodaPerson
     * @return LimitLodaPerson
     */
    public String getLimitLodaPerson(){
        return LimitLodaPerson;
    }

    /**
     * 设置属性LimitLoda
     * @param LimitLoda LimitLoda
     */
    public void setLimitLoda(String LimitLoda){
        this.LimitLoda = LimitLoda;
    }

    /**
     * 获取属性LimitLoda
     * @return LimitLoda
     */
    public String getLimitLoda(){
        return LimitLoda;
    }

    /**
     * 设置属性POWeight
     * @param POWeight POWeight
     */
    public void setPOWeight(String POWeight){
        this.POWeight = POWeight;
    }

    /**
     * 获取属性POWeight
     * @return POWeight
     */
    public String getPOWeight(){
        return POWeight;
    }

    /**
     * 设置属性ExhaustCapacity
     * @param ExhaustCapacity ExhaustCapacity
     */
    public void setExhaustCapacity(String ExhaustCapacity){
        this.ExhaustCapacity = ExhaustCapacity;
    }

    /**
     * 获取属性ExhaustCapacity
     * @return ExhaustCapacity
     */
    public String getExhaustCapacity(){
        return ExhaustCapacity;
    }

    /**
     * 设置属性FuelType
     * @param FuelType FuelType
     */
    public void setFuelType(String FuelType){
        this.FuelType = FuelType;
    }

    /**
     * 获取属性FuelType
     * @return FuelType
     */
    public String getFuelType(){
        return FuelType;
    }

    /**
     * 设置属性CertificateType
     * @param CertificateType CertificateType
     */
    public void setCertificateType(String CertificateType){
        this.CertificateType = CertificateType;
    }

    /**
     * 获取属性CertificateType
     * @return CertificateType
     */
    public String getCertificateType(){
        return CertificateType;
    }

    /**
     * 设置属性CertificateNO
     * @param CertificateNO CertificateNO
     */
    public void setCertificateNO(String CertificateNO){
        this.CertificateNO = CertificateNO;
    }

    /**
     * 获取属性CertificateNO
     * @return CertificateNO
     */
    public String getCertificateNO(){
        return CertificateNO;
    }

    /**
     * 设置属性CertifycateDate
     * @param CertifycateDate CertifycateDate
     */
    public void setCertifycateDate(String CertifycateDate){
     if  (CertifycateDate == null ) 
     {
        this.CertifycateDate = "";
     }
     else
     {
        this.CertifycateDate = CertifycateDate.substring(0,8);
     }
    }

    /**
     * 获取属性CertifycateDate
     * @return CertifycateDate
     */
    public String getCertifycateDate(){
        return CertifycateDate;
    }

    /**
     * 设置属性CarRecordDate
     * @param CarRecordDate CarRecordDate
     */
    public void setCarRecordDate(String CarRecordDate){
     if  (CarRecordDate == null ) 
     {
        this.CarRecordDate = "";
     }
     else
     {
        this.CarRecordDate = CarRecordDate.substring(0,10);
     }
    }

    /**
     * 获取属性CarRecordDate
     * @return CarRecordDate
     */
    public String getCarRecordDate(){
        return CarRecordDate;
    }
    /**
     * 设置属性UserCode
     * @param UserCode UserCode
     */
    public void setUserCode(String userCode) {
    	UserCode = userCode;
    }
    
    /**
     * 获取属性UserCode
     * @return UserCode
     */
    public String getUserCode() {
    	return UserCode;
    }

    /**
     * @param iSchema 对象PrpNewCarRecordSchema
     */       
    public void setSchema(PrpNewCarRecordSchema iSchema)
    {
        this.EngineNo = iSchema.getEngineNo();
        this.RackNo = iSchema.getRackNo();
        this.Owner = iSchema.getOwner();
        this.CertiType = iSchema.getCertiType();
        this.CertiCode = iSchema.getCertiCode();
        this.PCVehicleCategory = iSchema.getPCVehicleCategory();
        this.LimitLodaPerson = iSchema.getLimitLodaPerson();
        this.LimitLoda = iSchema.getLimitLoda();
        this.POWeight = iSchema.getPOWeight();
        this.ExhaustCapacity = iSchema.getExhaustCapacity();
        this.FuelType = iSchema.getFuelType();
        this.CertificateType = iSchema.getCertificateType();
        this.CertificateNO = iSchema.getCertificateNO();
        this.CertifycateDate = iSchema.getCertifycateDate();
        this.CarRecordDate = iSchema.getCarRecordDate();
        this.UserCode = iSchema.getUserCode();
    }

}
