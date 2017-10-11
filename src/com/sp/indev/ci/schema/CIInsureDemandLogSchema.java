package com.sp.indiv.ci.schema;

/**
 * 定义表-CIInsureDemandRisk的数据传输对象类
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>CreateDate 2013-05-07</p>
 * @author 
 * @version 1.0
 */
public class CIInsureDemandLogSchema {

    
    private String ItemNo = "";
    
    private String LicenseNo = "";
    
    private String FrameNo = "";
    
    private String EngineNo = "";
    
    private String Flag = "";
    
    private String ErrorMessage = "";
    
    private String DemandNo = "";
    
    private String ComCode = "";
    
    private String RiskCode = "";
    
    private String ChannelType = "";
    
    private String OperateSite = "";
    
    private String OperaterCode = "";
    
    private String CreateDate = "";
    
    private String Remark = "";
    
    private String IniTime = "";

    public String getItemNo() {
        return ItemNo;
    }

    public void setItemNo(String itemNo) {
        ItemNo = itemNo;
    }

    public String getLicenseNo() {
        return LicenseNo;
    }

    public void setLicenseNo(String licenseNo) {
        LicenseNo = licenseNo;
    }

    public String getFrameNo() {
        return FrameNo;
    }

    public void setFrameNo(String frameNo) {
        FrameNo = frameNo;
    }

    public String getEngineNo() {
        return EngineNo;
    }

    public void setEngineNo(String engineNo) {
        EngineNo = engineNo;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getDemandNo() {
        return DemandNo;
    }

    public void setDemandNo(String demandNo) {
        DemandNo = demandNo;
    }

    public String getComCode() {
        return ComCode;
    }

    public void setComCode(String comCode) {
        ComCode = comCode;
    }

    public String getRiskCode() {
        return RiskCode;
    }

    public void setRiskCode(String riskCode) {
        RiskCode = riskCode;
    }

    public String getChannelType() {
        return ChannelType;
    }

    public void setChannelType(String channelType) {
        ChannelType = channelType;
    }

    public String getOperateSite() {
        return OperateSite;
    }

    public void setOperateSite(String operateSite) {
        OperateSite = operateSite;
    }
    
    public String getOperaterCode() {
        return OperaterCode;
    }

    public void setOperaterCode(String operaterCode) {
        OperaterCode = operaterCode;
    }
    
    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }
    
    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
    
    public String getIniTime() {
        return IniTime;
    }

    public void setIniTime(String iniTime) {
    	this.IniTime = iniTime;
    }
    


    public void setSchema(CIInsureDemandLogSchema iSchema) {
        this.ItemNo       = iSchema.getItemNo();
        this.LicenseNo    = iSchema.getLicenseNo();
        this.FrameNo      = iSchema.getFrameNo();   
        this.EngineNo     = iSchema.getEngineNo();
        this.Flag         = iSchema.getFlag();
        this.ErrorMessage = iSchema.getErrorMessage();
        this.DemandNo     = iSchema.getDemandNo();
        this.ComCode      = iSchema.getComCode();
        this.RiskCode     = iSchema.getRiskCode();
        this.ChannelType  = iSchema.getChannelType();
        this.OperateSite  = iSchema.getOperateSite();
        this.OperaterCode = iSchema.getOperaterCode();
        this.CreateDate   = iSchema.getCreateDate();
        this.Remark       = iSchema.getRemark();
        this.IniTime      = iSchema.getIniTime();
    }
}
