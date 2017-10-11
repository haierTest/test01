package com.sp.indiv.ci.schema;
  
import java.io.Serializable;



import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ���彻�ܳ�����Ϣ��ѯ-CIInsurePmDriverSchema�����ݴ��������
 * add by fanjiangtao 
 * create date 2014-01-24
 */
public class CIInsurePmDriverSchema implements Serializable{
    
    private String Name = "";
    
    private String DriverTypeCode = "";
    
    private String LicenseNo = "";
    
    private String LicenseStatusCode = "";
    
    private String LicensedDate = "";
    
    private String InsertDate = "";
    
    private String UpdateDate = "";
    
    private String ValidFlag = "";
    
    private String OperateSite = "";
    
    private String Flag = "";
    
    private String DemandNo = "";
    /**
     * ���캯��
     */       
    public CIInsurePmDriverSchema(){
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
     * �������Բ���ʱ��
     * @param InsertDate ����ʱ��
     */
    public void setInsertDate(String InsertDate){
        ChgDate chgDate = new ChgDate(); 
        InsertDate = chgDate.toFormat(InsertDate);
        if  (InsertDate == null || InsertDate.length() == 0 ) 
        {
          this.InsertDate = "";
        }else
        {
         this.InsertDate = InsertDate.trim().substring(0,18);
        }
    }

    /**
     * ��ȡ���Բ���ʱ��
     * @return ����ʱ��
     */
    public String getInsertDate(){
        return Str.rightTrim(InsertDate);
    }

    /**
     * �������Ա�־λ
     * @param Flag ��־λ
     */
    public void setFlag(String Flag){
        this.Flag = Str.rightTrim(Flag);
    }

    /**
     * ��ȡ���Ա�־λ
     * @return ��־λ
     */
    public String getFlag(){
        return Str.rightTrim(Flag);
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

    public String getUpdateDate() {
		return UpdateDate;
	}

	public void setUpdateDate(String updateDate) {
		UpdateDate = updateDate;
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

	/**
     * @param iSchema ����CIInsurePmDriverSchema
     */       
    public void setSchema(CIInsurePmDriverSchema iSchema)
    {
        this.Name = iSchema.getName();
        this.DriverTypeCode = iSchema.getDriverTypeCode();
        this.LicenseNo = iSchema.getLicenseNo();
        this.LicenseStatusCode = iSchema.getLicenseStatusCode();
        this.LicensedDate = iSchema.getLicensedDate();
        this.InsertDate = iSchema.getInsertDate();
        this.UpdateDate = iSchema.getUpdateDate();
        this.ValidFlag = iSchema.getValidFlag();
        this.OperateSite = iSchema.getOperateSite();
        this.Flag = iSchema.getFlag();
        this.DemandNo = iSchema.getDemandNo();
    }
}
