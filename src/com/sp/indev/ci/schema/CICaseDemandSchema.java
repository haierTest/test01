package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ����᰸���ݽ�����Ϣ��-CICaseDemand�����ݴ��������
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
     * ���캯��
     */       
    public CICaseDemandSchema(){
    }

    /**
     * ��������������
     * @param ClaimNo ������
     */
    public void setClaimNo(String ClaimNo){
        this.ClaimNo = Str.rightTrim(ClaimNo);
    }

    /**
     * ��ȡ����������
     * @return ������
     */
    public String getClaimNo(){
        return Str.rightTrim(ClaimNo);
    }

    /**
     * ��������XXXXX����
     * @param ClaimCode XXXXX����
     */
    public void setClaimCode(String ClaimCode){
        this.ClaimCode = Str.rightTrim(ClaimCode);
    }

    /**
     * ��ȡ����XXXXX����
     * @return XXXXX����
     */
    public String getClaimCode(){
        return Str.rightTrim(ClaimCode);
    }

    /**
     * ���������ⰸ�᰸У����
     * @param CaseCheckNo �ⰸ�᰸У����
     */
    public void setCaseCheckNo(String CaseCheckNo){
        this.CaseCheckNo = Str.rightTrim(CaseCheckNo);
    }

    /**
     * ��ȡ�����ⰸ�᰸У����
     * @return �ⰸ�᰸У����
     */
    public String getCaseCheckNo(){
        return Str.rightTrim(CaseCheckNo);
    }

    /**
     * @param iSchema ����CICaseDemandSchema
     */       
    public void setSchema(CICaseDemandSchema iSchema)
    {
        this.ClaimNo = iSchema.getClaimNo();
        this.ClaimCode = iSchema.getClaimCode();
        this.CaseCheckNo = iSchema.getCaseCheckNo();
    }
}
