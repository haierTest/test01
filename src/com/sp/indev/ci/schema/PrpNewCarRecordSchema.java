package com.sp.indiv.ci.schema;

import java.io.Serializable;

/**
 * ����prpNewCarRecord�����ݴ��������
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��Dto��Schema��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-12-22</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList 1.����Schema�������ɹ��߼̳й�ϵ����;
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
     * ���캯��
     */       
    public PrpNewCarRecordSchema(){
    }

    /**
     * ��������EngineNo
     * @param EngineNo EngineNo
     */
    public void setEngineNo(String EngineNo){
        this.EngineNo = EngineNo;
    }

    /**
     * ��ȡ����EngineNo
     * @return EngineNo
     */
    public String getEngineNo(){
        return EngineNo;
    }

    /**
     * ��������RackNo
     * @param RackNo RackNo
     */
    public void setRackNo(String RackNo){
        this.RackNo = RackNo;
    }

    /**
     * ��ȡ����RackNo
     * @return RackNo
     */
    public String getRackNo(){
        return RackNo;
    }

    /**
     * ��������Owner
     * @param Owner Owner
     */
    public void setOwner(String Owner){
        this.Owner = Owner;
    }

    /**
     * ��ȡ����Owner
     * @return Owner
     */
    public String getOwner(){
        return Owner;
    }

    /**
     * ��������CertiType
     * @param CertiType CertiType
     */
    public void setCertiType(String CertiType){
        this.CertiType = CertiType;
    }

    /**
     * ��ȡ����CertiType
     * @return CertiType
     */
    public String getCertiType(){
        return CertiType;
    }

    /**
     * ��������CertiCode
     * @param CertiCode CertiCode
     */
    public void setCertiCode(String CertiCode){
        this.CertiCode = CertiCode;
    }

    /**
     * ��ȡ����CertiCode
     * @return CertiCode
     */
    public String getCertiCode(){
        return CertiCode;
    }

    /**
     * ��������PCVehicleCategory
     * @param PCVehicleCategory PCVehicleCategory
     */
    public void setPCVehicleCategory(String PCVehicleCategory){
        this.PCVehicleCategory = PCVehicleCategory;
    }

    /**
     * ��ȡ����PCVehicleCategory
     * @return PCVehicleCategory
     */
    public String getPCVehicleCategory(){
        return PCVehicleCategory;
    }

    /**
     * ��������LimitLodaPerson
     * @param LimitLodaPerson LimitLodaPerson
     */
    public void setLimitLodaPerson(String LimitLodaPerson){
        this.LimitLodaPerson = LimitLodaPerson;
    }

    /**
     * ��ȡ����LimitLodaPerson
     * @return LimitLodaPerson
     */
    public String getLimitLodaPerson(){
        return LimitLodaPerson;
    }

    /**
     * ��������LimitLoda
     * @param LimitLoda LimitLoda
     */
    public void setLimitLoda(String LimitLoda){
        this.LimitLoda = LimitLoda;
    }

    /**
     * ��ȡ����LimitLoda
     * @return LimitLoda
     */
    public String getLimitLoda(){
        return LimitLoda;
    }

    /**
     * ��������POWeight
     * @param POWeight POWeight
     */
    public void setPOWeight(String POWeight){
        this.POWeight = POWeight;
    }

    /**
     * ��ȡ����POWeight
     * @return POWeight
     */
    public String getPOWeight(){
        return POWeight;
    }

    /**
     * ��������ExhaustCapacity
     * @param ExhaustCapacity ExhaustCapacity
     */
    public void setExhaustCapacity(String ExhaustCapacity){
        this.ExhaustCapacity = ExhaustCapacity;
    }

    /**
     * ��ȡ����ExhaustCapacity
     * @return ExhaustCapacity
     */
    public String getExhaustCapacity(){
        return ExhaustCapacity;
    }

    /**
     * ��������FuelType
     * @param FuelType FuelType
     */
    public void setFuelType(String FuelType){
        this.FuelType = FuelType;
    }

    /**
     * ��ȡ����FuelType
     * @return FuelType
     */
    public String getFuelType(){
        return FuelType;
    }

    /**
     * ��������CertificateType
     * @param CertificateType CertificateType
     */
    public void setCertificateType(String CertificateType){
        this.CertificateType = CertificateType;
    }

    /**
     * ��ȡ����CertificateType
     * @return CertificateType
     */
    public String getCertificateType(){
        return CertificateType;
    }

    /**
     * ��������CertificateNO
     * @param CertificateNO CertificateNO
     */
    public void setCertificateNO(String CertificateNO){
        this.CertificateNO = CertificateNO;
    }

    /**
     * ��ȡ����CertificateNO
     * @return CertificateNO
     */
    public String getCertificateNO(){
        return CertificateNO;
    }

    /**
     * ��������CertifycateDate
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
     * ��ȡ����CertifycateDate
     * @return CertifycateDate
     */
    public String getCertifycateDate(){
        return CertifycateDate;
    }

    /**
     * ��������CarRecordDate
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
     * ��ȡ����CarRecordDate
     * @return CarRecordDate
     */
    public String getCarRecordDate(){
        return CarRecordDate;
    }
    /**
     * ��������UserCode
     * @param UserCode UserCode
     */
    public void setUserCode(String userCode) {
    	UserCode = userCode;
    }
    
    /**
     * ��ȡ����UserCode
     * @return UserCode
     */
    public String getUserCode() {
    	return UserCode;
    }

    /**
     * @param iSchema ����PrpNewCarRecordSchema
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
