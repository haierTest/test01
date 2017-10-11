package com.sp.indiv.ci.schema;

import java.io.Serializable;

import com.sp.utility.string.Str;
/**
 * 定义CIMotorcadeDeclare-团车申报接口表的数据传输对象类
 * 从pdm中取数据库信息,根据数据库表生成对应的Dto或Schema类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-03-19</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList 1.改造Schema层类生成工具继承关系方法;
 */
public class CIMotorcadeDeclareSchema implements Serializable{
    
    private String SerialNo = "";
    
    private String GroupCode = "";
    
    private String ContractNo = "";
    
    private String ProposalNo = "";
    
    private String CarMark = "";
    
    private String EngineNo = "";
    
    private String RackNo = "";
    
    private String Owner = "";
    
    private String PolicyHolder = "";
    
    private String InsuredName = "";
    
    private String PassVehicle = "";
    
    private String FailureVehicle = "";
    
    private String GroupStartDate = "";
    
    private String GroupEndDate = "";
    
    private String ComCode="";
    
    private String ErrorMessage="";
    
    private String ResponseCode = "";
    
    private String OperationFlag = "";
    




	/**
     * 构造函数
     */       
    public CIMotorcadeDeclareSchema(){
    }

    /**
     * 设置属性序列号
     * @param SerialNo 序列号
     */
    public void setSerialNo(String SerialNo){
        this.SerialNo = SerialNo;
    }

    /**
     * 获取属性序列号
     * @return 序列号
     */
    public String getSerialNo(){
        return SerialNo;
    }

    /**
     * 设置属性团车业务编号
     * @param GroupCode 团车业务编号
     */
    public void setGroupCode(String GroupCode){
        this.GroupCode = GroupCode;
    }

    /**
     * 获取属性团车业务编号
     * @return 团车业务编号
     */
    public String getGroupCode(){
        return GroupCode;
    }

    /**
     * 设置属性协议号
     * @param ContractNo 协议号
     */
    public void setContractNo(String ContractNo){
        this.ContractNo = ContractNo;
    }

    /**
     * 获取属性协议号
     * @return 协议号
     */
    public String getContractNo(){
        return ContractNo;
    }

    /**
     * 设置属性XX单号
     * @param ProposalNo XX单号
     */
    public void setProposalNo(String ProposalNo){
        this.ProposalNo = ProposalNo;
    }

    /**
     * 获取属性XX单号
     * @return XX单号
     */
    public String getProposalNo(){
        return ProposalNo;
    }

    /**
     * 设置属性车牌号
     * @param CarMark 车牌号
     */
    public void setCarMark(String CarMark){
        this.CarMark = CarMark;
    }

    /**
     * 获取属性车牌号
     * @return 车牌号
     */
    public String getCarMark(){
        return CarMark;
    }

    /**
     * 设置属性发动机号
     * @param EngineNo 发动机号
     */
    public void setEngineNo(String EngineNo){
        this.EngineNo = EngineNo;
    }

    /**
     * 获取属性发动机号
     * @return 发动机号
     */
    public String getEngineNo(){
        return EngineNo;
    }

    /**
     * 设置属性车架号
     * @param RackNo 车架号
     */
    public void setRackNo(String RackNo){
        this.RackNo = RackNo;
    }

    /**
     * 获取属性车架号
     * @return 车架号
     */
    public String getRackNo(){
        return RackNo;
    }

    /**
     * 设置属性行驶证车主
     * @param Owner 行驶证车主
     */
    public void setOwner(String Owner){
        this.Owner = Owner;
    }

    /**
     * 获取属性行驶证车主
     * @return 行驶证车主
     */
    public String getOwner(){
        return Owner;
    }

    /**
     * 设置属性XX人名称
     * @param PolicyHolder XX人名称
     */
    public void setPolicyHolder(String PolicyHolder){
        this.PolicyHolder = PolicyHolder;
    }

    /**
     * 获取属性XX人名称
     * @return XX人名称
     */
    public String getPolicyHolder(){
        return PolicyHolder;
    }

    /**
     * 设置属性被XX人名称
     * @param InsuredName 被XX人名称
     */
    public void setInsuredName(String InsuredName){
        this.InsuredName = InsuredName;
    }

    /**
     * 获取属性被XX人名称
     * @return 被XX人名称
     */
    public String getInsuredName(){
        return InsuredName;
    }

    /**
     * 设置属性上报成功车辆数
     * @param PassVehicle 上报成功车辆数
     */
    public void setPassVehicle(String PassVehicle){
        this.PassVehicle = PassVehicle;
    }

    /**
     * 获取属性上报成功车辆数
     * @return 上报成功车辆数
     */
    public String getPassVehicle(){
        return PassVehicle;
    }

    /**
     * 设置属性上报失败车辆数
     * @param FailureVehicle 上报失败车辆数
     */
    public void setFailureVehicle(String FailureVehicle){
        this.FailureVehicle = FailureVehicle;
    }

    /**
     * 获取属性上报失败车辆数
     * @return 上报失败车辆数
     */
    public String getFailureVehicle(){
        return FailureVehicle;
    }

    /**
     * 设置属性申报有效起期
     * @param GroupStartDate 申报有效起期
     */
    public void setGroupStartDate(String GroupStartDate){
     if  (GroupStartDate == null ) 
     {
        this.GroupStartDate = "";
     }
     else
     {	
        this.GroupStartDate = GroupStartDate.substring(0,10);
     }
    }

    /**
     * 获取属性申报有效起期
     * @return 申报有效起期
     */
    public String getGroupStartDate(){
        return GroupStartDate;
    }

    /**
     * 设置属性申报有效止期
     * @param GroupEndDate 申报有效止期
     */
    public void setGroupEndDate(String GroupEndDate){
     if  (GroupEndDate == null ) 
     {
        this.GroupEndDate = "";
     }
     else
     {
        this.GroupEndDate = GroupEndDate.substring(0,10);
     }
    }

    /**
     * 获取属性申报有效止期
     * @return 申报有效止期
     */
    public String getGroupEndDate(){
        return GroupEndDate;
    }

    public String getComCode() {
		return ComCode;
	}

	public void setComCode(String comCode) {
		ComCode = comCode;
	}

	
	public String getErrorMessage() {
		return ErrorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		ErrorMessage = errorMessage;
	}
	

	public String getResponseCode() {
		return ResponseCode;
	}

	public void setResponseCode(String responseCode) {
		ResponseCode = responseCode;
	}
	
	
	public String getOperationFlag() {
		return  Str.rightTrim(OperationFlag);
	}

	public void setOperationFlag(String operationFlag) {
		this.OperationFlag =  Str.rightTrim(operationFlag);
	}
	
	/**
     * @param iSchema 对象CIMotorcadeDeclareSchema
     */       
    public void setSchema(CIMotorcadeDeclareSchema iSchema)
    {
        this.SerialNo = iSchema.getSerialNo();
        this.GroupCode = iSchema.getGroupCode();
        this.ContractNo = iSchema.getContractNo();
        this.ProposalNo = iSchema.getProposalNo();
        this.CarMark = iSchema.getCarMark();
        this.EngineNo = iSchema.getEngineNo();
        this.RackNo = iSchema.getRackNo();
        this.Owner = iSchema.getOwner();
        this.PolicyHolder = iSchema.getPolicyHolder();
        this.InsuredName = iSchema.getInsuredName();
        this.PassVehicle = iSchema.getPassVehicle();
        this.FailureVehicle = iSchema.getFailureVehicle();
        this.GroupStartDate = iSchema.getGroupStartDate();
        this.GroupEndDate = iSchema.getGroupEndDate();
        this.ComCode=iSchema.getComCode();
        this.ErrorMessage=iSchema.getErrorMessage();
        
        this.OperationFlag = iSchema.getOperationFlag();
        
    }
}
