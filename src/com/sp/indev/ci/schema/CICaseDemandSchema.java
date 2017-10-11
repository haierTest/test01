package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义结案数据交互信息表-CICaseDemand的数据传输对象类
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author DtoGenerator
 * @version 1.0
 */
public class CICaseDemandSchema implements Serializable{
    
    private String ClaimNo = "";
    
    private String ClaimCode = "";
    
    private String CaseCheckNo = "";

    /**
     * 构造函数
     */       
    public CICaseDemandSchema(){
    }

    /**
     * 设置属性立案号
     * @param ClaimNo 立案号
     */
    public void setClaimNo(String ClaimNo){
        this.ClaimNo = Str.rightTrim(ClaimNo);
    }

    /**
     * 获取属性立案号
     * @return 立案号
     */
    public String getClaimNo(){
        return Str.rightTrim(ClaimNo);
    }

    /**
     * 设置属性XXXXX编码
     * @param ClaimCode XXXXX编码
     */
    public void setClaimCode(String ClaimCode){
        this.ClaimCode = Str.rightTrim(ClaimCode);
    }

    /**
     * 获取属性XXXXX编码
     * @return XXXXX编码
     */
    public String getClaimCode(){
        return Str.rightTrim(ClaimCode);
    }

    /**
     * 设置属性赔案结案校验码
     * @param CaseCheckNo 赔案结案校验码
     */
    public void setCaseCheckNo(String CaseCheckNo){
        this.CaseCheckNo = Str.rightTrim(CaseCheckNo);
    }

    /**
     * 获取属性赔案结案校验码
     * @return 赔案结案校验码
     */
    public String getCaseCheckNo(){
        return Str.rightTrim(CaseCheckNo);
    }

    /**
     * @param iSchema 对象CICaseDemandSchema
     */       
    public void setSchema(CICaseDemandSchema iSchema)
    {
        this.ClaimNo = iSchema.getClaimNo();
        this.ClaimCode = iSchema.getClaimCode();
        this.CaseCheckNo = iSchema.getCaseCheckNo();
    }
}
