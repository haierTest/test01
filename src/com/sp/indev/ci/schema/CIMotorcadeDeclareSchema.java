package com.sp.indiv.ci.schema;

import java.io.Serializable;

import com.sp.utility.string.Str;
/**
 * ����CIMotorcadeDeclare-�ų��걨�ӿڱ�����ݴ��������
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��Dto��Schema��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-03-19</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList 1.����Schema�������ɹ��߼̳й�ϵ����;
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
     * ���캯��
     */       
    public CIMotorcadeDeclareSchema(){
    }

    /**
     * �����������к�
     * @param SerialNo ���к�
     */
    public void setSerialNo(String SerialNo){
        this.SerialNo = SerialNo;
    }

    /**
     * ��ȡ�������к�
     * @return ���к�
     */
    public String getSerialNo(){
        return SerialNo;
    }

    /**
     * ���������ų�ҵ����
     * @param GroupCode �ų�ҵ����
     */
    public void setGroupCode(String GroupCode){
        this.GroupCode = GroupCode;
    }

    /**
     * ��ȡ�����ų�ҵ����
     * @return �ų�ҵ����
     */
    public String getGroupCode(){
        return GroupCode;
    }

    /**
     * ��������Э���
     * @param ContractNo Э���
     */
    public void setContractNo(String ContractNo){
        this.ContractNo = ContractNo;
    }

    /**
     * ��ȡ����Э���
     * @return Э���
     */
    public String getContractNo(){
        return ContractNo;
    }

    /**
     * ��������XX����
     * @param ProposalNo XX����
     */
    public void setProposalNo(String ProposalNo){
        this.ProposalNo = ProposalNo;
    }

    /**
     * ��ȡ����XX����
     * @return XX����
     */
    public String getProposalNo(){
        return ProposalNo;
    }

    /**
     * �������Գ��ƺ�
     * @param CarMark ���ƺ�
     */
    public void setCarMark(String CarMark){
        this.CarMark = CarMark;
    }

    /**
     * ��ȡ���Գ��ƺ�
     * @return ���ƺ�
     */
    public String getCarMark(){
        return CarMark;
    }

    /**
     * �������Է�������
     * @param EngineNo ��������
     */
    public void setEngineNo(String EngineNo){
        this.EngineNo = EngineNo;
    }

    /**
     * ��ȡ���Է�������
     * @return ��������
     */
    public String getEngineNo(){
        return EngineNo;
    }

    /**
     * �������Գ��ܺ�
     * @param RackNo ���ܺ�
     */
    public void setRackNo(String RackNo){
        this.RackNo = RackNo;
    }

    /**
     * ��ȡ���Գ��ܺ�
     * @return ���ܺ�
     */
    public String getRackNo(){
        return RackNo;
    }

    /**
     * ����������ʻ֤����
     * @param Owner ��ʻ֤����
     */
    public void setOwner(String Owner){
        this.Owner = Owner;
    }

    /**
     * ��ȡ������ʻ֤����
     * @return ��ʻ֤����
     */
    public String getOwner(){
        return Owner;
    }

    /**
     * ��������XX������
     * @param PolicyHolder XX������
     */
    public void setPolicyHolder(String PolicyHolder){
        this.PolicyHolder = PolicyHolder;
    }

    /**
     * ��ȡ����XX������
     * @return XX������
     */
    public String getPolicyHolder(){
        return PolicyHolder;
    }

    /**
     * �������Ա�XX������
     * @param InsuredName ��XX������
     */
    public void setInsuredName(String InsuredName){
        this.InsuredName = InsuredName;
    }

    /**
     * ��ȡ���Ա�XX������
     * @return ��XX������
     */
    public String getInsuredName(){
        return InsuredName;
    }

    /**
     * ���������ϱ��ɹ�������
     * @param PassVehicle �ϱ��ɹ�������
     */
    public void setPassVehicle(String PassVehicle){
        this.PassVehicle = PassVehicle;
    }

    /**
     * ��ȡ�����ϱ��ɹ�������
     * @return �ϱ��ɹ�������
     */
    public String getPassVehicle(){
        return PassVehicle;
    }

    /**
     * ���������ϱ�ʧ�ܳ�����
     * @param FailureVehicle �ϱ�ʧ�ܳ�����
     */
    public void setFailureVehicle(String FailureVehicle){
        this.FailureVehicle = FailureVehicle;
    }

    /**
     * ��ȡ�����ϱ�ʧ�ܳ�����
     * @return �ϱ�ʧ�ܳ�����
     */
    public String getFailureVehicle(){
        return FailureVehicle;
    }

    /**
     * ���������걨��Ч����
     * @param GroupStartDate �걨��Ч����
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
     * ��ȡ�����걨��Ч����
     * @return �걨��Ч����
     */
    public String getGroupStartDate(){
        return GroupStartDate;
    }

    /**
     * ���������걨��Чֹ��
     * @param GroupEndDate �걨��Чֹ��
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
     * ��ȡ�����걨��Чֹ��
     * @return �걨��Чֹ��
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
     * @param iSchema ����CIMotorcadeDeclareSchema
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
