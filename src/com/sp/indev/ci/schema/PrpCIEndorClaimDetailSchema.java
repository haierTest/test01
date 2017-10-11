package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ����PrpCIEndorClaimDetail�����ݴ��������
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-08-23</p>
 * @author DtoGenerator
 * @version 1.0
 */
public class PrpCIEndorClaimDetailSchema  implements Serializable{
    
    private String DemandNo = "";
    
    private String FSerialNo = "";
    
    private String SerialNo = "";
    
    private String PolicyNo = "";
    
    private String LossFee = "";
    
    private String KindCode = "";
    
    private String Remarks = "";
    
    private String Flag = "";

    /**
     * ���캯��
     */       
    public PrpCIEndorClaimDetailSchema(){
    }

    /**
     * ��������DemandNo
     * @param DemandNo DemandNo
     */
    public void setDemandNo(String DemandNo){
        this.DemandNo = Str.rightTrim(DemandNo);
    }

    /**
     * ��ȡ����DemandNo
     * @return DemandNo
     */
    public String getDemandNo(){
        return Str.rightTrim(DemandNo);
    }

    /**
     * ��������FSerialNo
     * @param FSerialNo FSerialNo
     */
    public void setFSerialNo(String FSerialNo){
        this.FSerialNo = Str.rightTrim(FSerialNo);
    }

    /**
     * ��ȡ����FSerialNo
     * @return FSerialNo
     */
    public String getFSerialNo(){
        return Str.rightTrim(FSerialNo);
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
        return Str.rightTrim(SerialNo);
    }

    /**
     * ��������PolicyNo
     * @param PolicyNo PolicyNo
     */
    public void setPolicyNo(String PolicyNo){
        this.PolicyNo = Str.rightTrim(PolicyNo);
    }

    /**
     * ��ȡ����PolicyNo
     * @return PolicyNo
     */
    public String getPolicyNo(){
        return Str.rightTrim(PolicyNo);
    }

    /**
     * ��������LossFee
     * @param LossFee LossFee
     */
    public void setLossFee(String LossFee){
        this.LossFee = Str.rightTrim(LossFee);
    }

    /**
     * ��ȡ����LossFee
     * @return LossFee
     */
    public String getLossFee(){
        return Str.rightTrim(LossFee);
    }

    /**
     * ��������KindCode
     * @param KindCode KindCode
     */
    public void setKindCode(String KindCode){
        this.KindCode = Str.rightTrim(KindCode);
    }

    /**
     * ��ȡ����KindCode
     * @return KindCode
     */
    public String getKindCode(){
        return Str.rightTrim(KindCode);
    }

    /**
     * ��������Remarks
     * @param Remarks Remarks
     */
    public void setRemarks(String Remarks){
        this.Remarks = Str.rightTrim(Remarks);
    }

    /**
     * ��ȡ����Remarks
     * @return Remarks
     */
    public String getRemarks(){
        return Str.rightTrim(Remarks);
    }

    /**
     * ��������Flag
     * @param Flag Flag
     */
    public void setFlag(String Flag){
        this.Flag = Str.rightTrim(Flag);
    }

    /**
     * ��ȡ����Flag
     * @return Flag
     */
    public String getFlag(){
        return Str.rightTrim(Flag);
    }

    /**
     * @param iSchema ����PrpCIEndorClaimDetailSchema
     */       
    public void setSchema(PrpCIEndorClaimDetailSchema iSchema)
    {
        this.DemandNo = iSchema.getDemandNo();
        this.FSerialNo = iSchema.getFSerialNo();
        this.SerialNo = iSchema.getSerialNo();
        this.PolicyNo = iSchema.getPolicyNo();
        this.LossFee = iSchema.getLossFee();
        this.KindCode = iSchema.getKindCode();
        this.Remarks = iSchema.getRemarks();
        this.Flag = iSchema.getFlag();
    }
}
