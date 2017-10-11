package com.sp.indiv.ci.schema;
  
import java.io.Serializable;

import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ���彻�ܳ�����Ϣ��ѯ-PrpCIEndorClaimDriverSchema�����ݴ��������
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
     * ���캯��
     */       
    public PrpCIEndorClaimDriverSchema(){
    }

    /**
     * �������Լ�ʻԱ����
     * @param Name ��ʻԱ����
     */
    public void setName(String Name){
        this.Name = Str.rightTrim(Name);
    }

    /**
     * ��ȡ���Լ�ʻԱ����
     * @return ��ʻԱ����
     */
    public String getName(){
        return Str.rightTrim(Name);
    }

    /**
     * �������Լ�ʻ֤����
     * @param DriverTypeCode ��ʻ֤����
     */
    public void setDriverTypeCode(String DriverTypeCode){
        this.DriverTypeCode = Str.rightTrim(DriverTypeCode);
    }

    /**
     * ��ȡ���Լ�ʻ֤����
     * @return ��ʻ֤��������
     */
    public String getDriverTypeCode(){
        return Str.rightTrim(DriverTypeCode);
    }

    /**
     * �������Լ�ʻ֤��
     * @param LicenseNo ��ʻ֤��
     */
    public void setLicenseNo(String LicenseNo){
        this.LicenseNo = Str.rightTrim(LicenseNo);
    }

    /**
     * ��ȡ���Լ�ʻ֤��
     * @return ��ʻ֤��
     */
    public String getLicenseNo(){
        return Str.rightTrim(LicenseNo);
    }

    /**
     * �������Լ�ʻ֤״̬����
     * @param EngineNo ��ʻ֤״̬����
     */
    public void setLicenseStatusCode(String LicenseStatusCode){
        this.LicenseStatusCode = Str.rightTrim(LicenseStatusCode);
    }

    /**
     * ��ȡ���Լ�ʻ֤״̬����
     * @return ��ʻ֤״̬����
     */
    public String getLicenseStatusCode(){
        return Str.rightTrim(LicenseStatusCode);
    }

    /**
     * �������Լ�ʻ֤�״η�֤���ڣ���ȷ����
     * @param VehicleStyle ��ʻ֤�״η�֤���ڣ���ȷ����
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
     * ��ȡ���Լ�ʻ֤�״η�֤���ڣ���ȷ����
     * @return ��ʻ֤�״η�֤���ڣ���ȷ����
     */
    public String getLicensedDate(){
        return Str.rightTrim(LicensedDate);
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
     * ��������SerialNo
     * @param SerialNo SerialNo
     */
    public void setSerialNo(String SerialNo){
        this.SerialNo = Str.rightTrim(SerialNo);
    }

    /**
     * ��ȡ����SerialNo
     * @return SerialNo
     */
    public String getSerialNo(){
        return SerialNo;
    }

	/**
     * @param iSchema ����PrpCIEndorClaimDriverSchema
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
