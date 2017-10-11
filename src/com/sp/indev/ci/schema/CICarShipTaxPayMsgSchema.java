package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义查询本地或上传外地完税信息表的数据传输对象类
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
     * 构造函数
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
	 * @param payID 要设置的 payID
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
	 * @param policyNo 要设置的 policyNo
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
	 * @param validNo 要设置的 validNo
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
	 * @param payNo 要设置的 payNo
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
	 * @param taxComName 要设置的 taxComName
	 */
	public void setTaxComName(String taxComName) {
		this.TaxComName = Str.rightTrim(taxComName);
	}
	
	/**
     * 获取属性操作员
     * @return 操作员
     */
	public String getOperaterCode() {
		return Str.rightTrim(OperaterCode);
	}

	/**
     * 设置属性操作员
     * @param operaterCode 操作员
     */
	public void setOperaterCode(String operaterCode) {
		this.OperaterCode = Str.rightTrim(operaterCode);
	}

	
    /**
     * 设置属性OperateDate
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
     * 获取属性OperateDate
     * @return OperateDate
     */
    public String getOperateDate(){
        return Str.rightTrim(OperateDate);
    }
	
    /**
     * 获取属性标志位
     * @return 标志位
     */
	public String getFlag() {
		return Str.rightTrim(Flag);
	}

	/**
     * 设置属性标志位
     * @param Flag 标志位
     */
	public void setFlag(String flag) {
		this.Flag = Str.rightTrim(flag);
	}

	/**
     * @param iSchema 对象CIAssemblePayNoSchema
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

