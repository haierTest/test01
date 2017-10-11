package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * �����ѯ���ػ��ϴ������˰��Ϣ������ݴ��������
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-10-06</p>
 * @author DtoGenerator
 * @version 1.0
 */
public class CICarShipTaxPayMsgSchema implements Serializable{
	
	private String PayID = "";
    
    private String PolicyNo = "";
    
    private String ValidNo = "";
    
    private String PayNo = "";
    
    private String TaxComName = "";
    
    private String OperaterCode = "";
    
    private String OperateDate = "";
    
    private String Flag = "";

    /**
     * ���캯��
     */       
    public CICarShipTaxPayMsgSchema(){
    }

    
	/**
	 * @return payID
	 */
	public String getPayID() {
		return Str.rightTrim(PayID);
	}

	/**
	 * @param payID Ҫ���õ� payID
	 */
	public void setPayID(String payID) {
		this.PayID = Str.rightTrim(payID);
	}
	
    
	/**
	 * @return policyNo
	 */
	public String getPolicyNo() {
		return Str.rightTrim(PolicyNo);
	}

	/**
	 * @param policyNo Ҫ���õ� policyNo
	 */
	public void setPolicyNo(String policyNo) {
		this.PolicyNo = Str.rightTrim(policyNo);
	}
	
	/**
	 * @return validNo
	 */
	public String getValidNo() {
		return Str.rightTrim(ValidNo);
	}

	/**
	 * @param validNo Ҫ���õ� validNo
	 */
	public void setValidNo(String validNo) {
		this.ValidNo = Str.rightTrim(validNo);
	}
	
	/**
	 * @return payNo
	 */
	public String getPayNo() {
		return Str.rightTrim(PayNo);
	}

	/**
	 * @param payNo Ҫ���õ� payNo
	 */
	public void setPayNo(String payNo) {
		this.PayNo = Str.rightTrim(payNo);
	}
	
	/**
	 * @return taxComName
	 */
	public String getTaxComName() {
		return Str.rightTrim(TaxComName);
	}

	/**
	 * @param taxComName Ҫ���õ� taxComName
	 */
	public void setTaxComName(String taxComName) {
		this.TaxComName = Str.rightTrim(taxComName);
	}
	
	/**
     * ��ȡ���Բ���Ա
     * @return ����Ա
     */
	public String getOperaterCode() {
		return Str.rightTrim(OperaterCode);
	}

	/**
     * �������Բ���Ա
     * @param operaterCode ����Ա
     */
	public void setOperaterCode(String operaterCode) {
		this.OperaterCode = Str.rightTrim(operaterCode);
	}

	
    /**
     * ��������OperateDate
     * @param OperateDate OperateDate
     */
    public void setOperateDate(String OperateDate){
      ChgDate chgDate = new ChgDate();
      OperateDate = chgDate.toFormat(OperateDate);
      if  (OperateDate == null || OperateDate.length() == 0 )
      {
        this.OperateDate = "";
      }else
      {
       this.OperateDate = OperateDate.trim().substring(0,10);
      }
    }

    /**
     * ��ȡ����OperateDate
     * @return OperateDate
     */
    public String getOperateDate(){
        return Str.rightTrim(OperateDate);
    }
	
    /**
     * ��ȡ���Ա�־λ
     * @return ��־λ
     */
	public String getFlag() {
		return Str.rightTrim(Flag);
	}

	/**
     * �������Ա�־λ
     * @param Flag ��־λ
     */
	public void setFlag(String flag) {
		this.Flag = Str.rightTrim(flag);
	}

	/**
     * @param iSchema ����CIAssemblePayNoSchema
     */       
    public void setSchema(CICarShipTaxPayMsgSchema iSchema)
    {
        this.PolicyNo = iSchema.getPolicyNo();
        this.ValidNo = iSchema.getValidNo();
        this.PayNo = iSchema.getPayNo();
        this.TaxComName = iSchema.getTaxComName();
        this.OperaterCode = iSchema.getOperaterCode();
        this.OperateDate = iSchema.getOperateDate();
        this.Flag = iSchema.getFlag();
    }
}

