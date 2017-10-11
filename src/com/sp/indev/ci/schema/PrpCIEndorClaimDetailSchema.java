package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义PrpCIEndorClaimDetail的数据传输对象类
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
     * 构造函数
     */       
    public PrpCIEndorClaimDetailSchema(){
    }

    /**
     * 设置属性DemandNo
     * @param DemandNo DemandNo
     */
    public void setDemandNo(String DemandNo){
        this.DemandNo = Str.rightTrim(DemandNo);
    }

    /**
     * 获取属性DemandNo
     * @return DemandNo
     */
    public String getDemandNo(){
        return Str.rightTrim(DemandNo);
    }

    /**
     * 设置属性FSerialNo
     * @param FSerialNo FSerialNo
     */
    public void setFSerialNo(String FSerialNo){
        this.FSerialNo = Str.rightTrim(FSerialNo);
    }

    /**
     * 获取属性FSerialNo
     * @return FSerialNo
     */
    public String getFSerialNo(){
        return Str.rightTrim(FSerialNo);
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
        return Str.rightTrim(SerialNo);
    }

    /**
     * 设置属性PolicyNo
     * @param PolicyNo PolicyNo
     */
    public void setPolicyNo(String PolicyNo){
        this.PolicyNo = Str.rightTrim(PolicyNo);
    }

    /**
     * 获取属性PolicyNo
     * @return PolicyNo
     */
    public String getPolicyNo(){
        return Str.rightTrim(PolicyNo);
    }

    /**
     * 设置属性LossFee
     * @param LossFee LossFee
     */
    public void setLossFee(String LossFee){
        this.LossFee = Str.rightTrim(LossFee);
    }

    /**
     * 获取属性LossFee
     * @return LossFee
     */
    public String getLossFee(){
        return Str.rightTrim(LossFee);
    }

    /**
     * 设置属性KindCode
     * @param KindCode KindCode
     */
    public void setKindCode(String KindCode){
        this.KindCode = Str.rightTrim(KindCode);
    }

    /**
     * 获取属性KindCode
     * @return KindCode
     */
    public String getKindCode(){
        return Str.rightTrim(KindCode);
    }

    /**
     * 设置属性Remarks
     * @param Remarks Remarks
     */
    public void setRemarks(String Remarks){
        this.Remarks = Str.rightTrim(Remarks);
    }

    /**
     * 获取属性Remarks
     * @return Remarks
     */
    public String getRemarks(){
        return Str.rightTrim(Remarks);
    }

    /**
     * 设置属性Flag
     * @param Flag Flag
     */
    public void setFlag(String Flag){
        this.Flag = Str.rightTrim(Flag);
    }

    /**
     * 获取属性Flag
     * @return Flag
     */
    public String getFlag(){
        return Str.rightTrim(Flag);
    }

    /**
     * @param iSchema 对象PrpCIEndorClaimDetailSchema
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
