package com.sp.indiv.ci.schema;
  
import java.io.Serializable;

import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义交管车辆信息查询-PrpCIEndorClaimDriverSchema的数据传输对象类
 * add by fanjiangtao 
 * create date 2014-01-24
 */
public class PrpCIEndorClaimDriverSchema implements Serializable{
    
    private String DemandNo = "";
    
    private String SerialNo = "";
    
    private String Name = "";
    
    private String DriverTypeCode = "";
    
    private String LicenseNo = "";
    
    private String LicenseStatusCode = "";
    
    private String LicensedDate = "";
    /**
     * 构造函数
     */       
    public PrpCIEndorClaimDriverSchema(){
    }

    /**
     * 设置属性驾驶员名称
     * @param Name 驾驶员名称
     */
    public void setName(String Name){
        this.Name = Str.rightTrim(Name);
    }

    /**
     * 获取属性驾驶员名称
     * @return 驾驶员名称
     */
    public String getName(){
        return Str.rightTrim(Name);
    }

    /**
     * 设置属性驾驶证类型
     * @param DriverTypeCode 驾驶证类型
     */
    public void setDriverTypeCode(String DriverTypeCode){
        this.DriverTypeCode = Str.rightTrim(DriverTypeCode);
    }

    /**
     * 获取属性驾驶证类型
     * @return 驾驶证类型种类
     */
    public String getDriverTypeCode(){
        return Str.rightTrim(DriverTypeCode);
    }

    /**
     * 设置属性驾驶证号
     * @param LicenseNo 驾驶证号
     */
    public void setLicenseNo(String LicenseNo){
        this.LicenseNo = Str.rightTrim(LicenseNo);
    }

    /**
     * 获取属性驾驶证号
     * @return 驾驶证号
     */
    public String getLicenseNo(){
        return Str.rightTrim(LicenseNo);
    }

    /**
     * 设置属性驾驶证状态代码
     * @param EngineNo 驾驶证状态代码
     */
    public void setLicenseStatusCode(String LicenseStatusCode){
        this.LicenseStatusCode = Str.rightTrim(LicenseStatusCode);
    }

    /**
     * 获取属性驾驶证状态代码
     * @return 驾驶证状态代码
     */
    public String getLicenseStatusCode(){
        return Str.rightTrim(LicenseStatusCode);
    }

    /**
     * 设置属性驾驶证首次发证日期，精确到天
     * @param VehicleStyle 驾驶证首次发证日期，精确到天
     */
    public void setLicensedDate(String LicensedDate){
        ChgDate chgDate = new ChgDate(); 
        LicensedDate = chgDate.toFormat(LicensedDate);
        if  (LicensedDate == null || LicensedDate.length() == 0 ) 
        {
          this.LicensedDate = "";
        }else
        {
         this.LicensedDate = LicensedDate.trim().substring(0,10);
        }
    }

    /**
     * 获取属性驾驶证首次发证日期，精确到天
     * @return 驾驶证首次发证日期，精确到天
     */
    public String getLicensedDate(){
        return Str.rightTrim(LicensedDate);
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
     * 设置属性SerialNo
     * @param SerialNo SerialNo
     */
    public void setSerialNo(String SerialNo){
        this.SerialNo = Str.rightTrim(SerialNo);
    }

    /**
     * 获取属性SerialNo
     * @return SerialNo
     */
    public String getSerialNo(){
        return SerialNo;
    }

	/**
     * @param iSchema 对象PrpCIEndorClaimDriverSchema
     */       
    public void setSchema(PrpCIEndorClaimDriverSchema iSchema)
    {
        this.DemandNo = iSchema.getDemandNo();
        this.Name = iSchema.getName();
        this.DriverTypeCode = iSchema.getDriverTypeCode();
        this.LicenseNo = iSchema.getLicenseNo();
        this.LicenseStatusCode = iSchema.getLicenseStatusCode();
        this.LicensedDate = iSchema.getLicensedDate();
        this.SerialNo = iSchema.getSerialNo();
    }
}
