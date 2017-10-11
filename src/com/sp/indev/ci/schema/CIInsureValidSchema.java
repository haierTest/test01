package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义XX确认主表-CIInsureValid的数据传输对象类
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>@createdate 2006-06-14</p>
 * @author DtoGenerator
 * @version 1.0
 */
public class CIInsureValidSchema implements Serializable{
    
    private String ValidNo = "";
    
    private String DemandNo = "";
    
    private String ProposalNo = "";
    
    private String PolicyNo = "";
    
    private String InsureMarkNo = "";
    
    private String BussinessNature = "";
    
    private String OperateDate = "";
    
    private String Clauses = "";
    
    private String HandlerName = "";
    
    private String OperatorName = "";
    
    private String Currency = "";
    
    private String Premium = "";
    
    private String ChangeReason = "";
    
    private String ChangeDetail = "";
    
    private String ComCode = "";
    
    private String ValidTime = "";
    
    private String Remark = "";
    
    private String Flag = "";
    
    private String ErrorMessage = "";
    
    
    private String ReinsureFlag = "";
    
    
    private String ImmeValidFlag = "";
    private String ImmeValidDate = "";
    
    
    
    private String WarrantNo = "";
    
    private String BankPaymentTime = "";
    
    
    
    private String DeficitFlag="";
	
    
    private String IP = "";
    private String UsbKey = "";
    

	/**
     * 构造函数
     */       
    public CIInsureValidSchema(){
    }

    /**
     * 设置属性确认码
     * @param ValidNo 确认码
     */
    public void setValidNo(String ValidNo){
        this.ValidNo = Str.rightTrim(ValidNo);
    }

    /**
     * 获取属性确认码
     * @return 确认码
     */
    public String getValidNo(){
        return Str.rightTrim(ValidNo);
    }

    /**
     * 设置属性查询码
     * @param DemandNo 查询码
     */
    public void setDemandNo(String DemandNo){
        this.DemandNo = Str.rightTrim(DemandNo);
    }

    /**
     * 获取属性查询码
     * @return 查询码
     */
    public String getDemandNo(){
        return Str.rightTrim(DemandNo);
    }

    /**
     * 设置属性XX单号码
     * @param ProposalNo XX单号码
     */
    public void setProposalNo(String ProposalNo){
        this.ProposalNo = Str.rightTrim(ProposalNo);
    }

    /**
     * 获取属性XX单号码
     * @return XX单号码
     */
    public String getProposalNo(){
        return Str.rightTrim(ProposalNo);
    }

    /**
     * 设置属性XX号码
     * @param PolicyNo XX号码
     */
    public void setPolicyNo(String PolicyNo){
        this.PolicyNo = Str.rightTrim(PolicyNo);
    }

    /**
     * 获取属性XX号码
     * @return XX号码
     */
    public String getPolicyNo(){
        return Str.rightTrim(PolicyNo);
    }

    /**
     * 设置属性XX标志号码
     * @param InsureMarkNo XX标志号码
     */
    public void setInsureMarkNo(String InsureMarkNo){
        this.InsureMarkNo = Str.rightTrim(InsureMarkNo);
    }

    /**
     * 获取属性XX标志号码
     * @return XX标志号码
     */
    public String getInsureMarkNo(){
        return Str.rightTrim(InsureMarkNo);
    }

    /**
     * 设置属性销售渠道
     * @param BussinessNature 销售渠道
     */
    public void setBussinessNature(String BussinessNature){
        this.BussinessNature = Str.rightTrim(BussinessNature);
    }

    /**
     * 获取属性销售渠道
     * @return 销售渠道
     */
    public String getBussinessNature(){
        return Str.rightTrim(BussinessNature);
    }

    /**
     * 设置属性签单日期
     * @param OperateDate 签单日期
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
     * 获取属性签单日期
     * @return 签单日期
     */
    public String getOperateDate(){
        return Str.rightTrim(OperateDate);
    }

    /**
     * 设置属性特别约定
     * @param Clauses 特别约定
     */
    public void setClauses(String Clauses){
        this.Clauses = Str.rightTrim(Clauses);
    }

    /**
     * 获取属性特别约定
     * @return 特别约定
     */
    public String getClauses(){
        return Str.rightTrim(Clauses);
    }

    /**
     * 设置属性经办人
     * @param HandlerName 经办人
     */
    public void setHandlerName(String HandlerName){
        this.HandlerName = Str.rightTrim(HandlerName);
    }

    /**
     * 获取属性经办人
     * @return 经办人
     */
    public String getHandlerName(){
        return Str.rightTrim(HandlerName);
    }

    /**
     * 设置属性操作员
     * @param OperatorName 操作员
     */
    public void setOperatorName(String OperatorName){
        this.OperatorName = Str.rightTrim(OperatorName);
    }

    /**
     * 获取属性操作员
     * @return 操作员
     */
    public String getOperatorName(){
        return Str.rightTrim(OperatorName);
    }

    /**
     * 设置属性币别
     * @param Currency 币别
     */
    public void setCurrency(String Currency){
        this.Currency = Str.rightTrim(Currency);
    }

    /**
     * 获取属性币别
     * @return 币别
     */
    public String getCurrency(){
        return Str.rightTrim(Currency);
    }

    /**
     * 设置属性实收XX
     * @param Premium 实收XX
     */
    public void setPremium(String Premium){
        this.Premium = Str.rightTrim(Premium);
    }

    /**
     * 获取属性实收XX
     * @return 实收XX
     */
    public String getPremium(){
        return Str.rightTrim(Premium);
    }

    /**
     * 设置属性实收XX变化原因
     * @param ChangeReason 实收XX变化原因
     */
    public void setChangeReason(String ChangeReason){
        this.ChangeReason = Str.rightTrim(ChangeReason);
    }

    /**
     * 获取属性实收XX变化原因
     * @return 实收XX变化原因
     */
    public String getChangeReason(){
        return Str.rightTrim(ChangeReason);
    }

    /**
     * 设置属性实收XX变化原因描述
     * @param ChangeDetail 实收XX变化原因描述
     */
    public void setChangeDetail(String ChangeDetail){
        this.ChangeDetail = Str.rightTrim(ChangeDetail);
    }

    /**
     * 获取属性实收XX变化原因描述
     * @return 实收XX变化原因描述
     */
    public String getChangeDetail(){
        return Str.rightTrim(ChangeDetail);
    }

    /**
     * 设置属性归属部门
     * @param ComCode 归属部门
     */
    public void setComCode(String ComCode){
        this.ComCode = Str.rightTrim(ComCode);
    }

    /**
     * 获取属性归属部门
     * @return 归属部门
     */
    public String getComCode(){
        return Str.rightTrim(ComCode);
    }

    /**
     * 设置属性确认时间
     * @param ValidTime 确认时间
     */
    public void setValidTime(String ValidTime){
      ChgDate chgDate = new ChgDate(); 
      ValidTime = chgDate.toFormat(ValidTime);
      if  (ValidTime == null || ValidTime.length() == 0 ) 
      {
        this.ValidTime = "";
      }else
      {
       this.ValidTime = ValidTime.trim().substring(0,10);
      }
    }

    /**
     * 获取属性确认时间
     * @return 确认时间
     */
    public String getValidTime(){
        return Str.rightTrim(ValidTime);
    }

    /**
     * 设置属性备注
     * @param Remark 备注
     */
    public void setRemark(String Remark){
        this.Remark = Str.rightTrim(Remark);
    }

    /**
     * 获取属性备注
     * @return 备注
     */
    public String getRemark(){
        return Str.rightTrim(Remark);
    }

    /**
     * 设置属性状态字段
     * @param Flag 状态字段
     */
    public void setFlag(String Flag){
        this.Flag = Flag;
    }

    /**
     * 获取属性状态字段
     * @return 状态字段
     */
    public String getFlag(){
        return Flag;
    }
    
    /**
     * 获取属性错误描述
     * @return 错误描述
     */
    public String getErrorMessage()
    {
    	return Str.rightTrim(ErrorMessage);
    }
    
    /**
     * 获取属性错误描述
     * @return 错误描述
     */
    public void setErrorMessage(String errorMessage)
    {
    	this.ErrorMessage = errorMessage;
    }
    
    public String getReinsureFlag() {
		return ReinsureFlag;
	}

	public void setReinsureFlag(String reinsureFlag) {
		
		ReinsureFlag = Str.rightTrim(reinsureFlag);
		
	}
    
    public String getImmeValidFlag() {
		return Str.rightTrim(ImmeValidFlag);
	}

	public void setImmeValidFlag(String immeValidFlag) {
		this.ImmeValidFlag = Str.rightTrim(immeValidFlag);
	}
    public String getImmeValidDate() {
		return Str.rightTrim(ImmeValidDate);
	}

	public void setImmeValidDate(String immeValidDate) {
		this.ImmeValidDate = Str.rightTrim(immeValidDate);
	}
    

	
    public String getWarrantNo() {
		return Str.rightTrim(WarrantNo);
	}

	public void setWarrantNo(String warrantNo) {
		this.WarrantNo = Str.rightTrim(warrantNo);
	}

    public void setBankPaymentTime(String bankPaymentTime){
      ChgDate chgDate = new ChgDate(); 
      bankPaymentTime = chgDate.toFormat(bankPaymentTime);
      if  (bankPaymentTime == null || bankPaymentTime.length() == 0 ) 
      {
        this.BankPaymentTime = "";
      }else
      {
       this.BankPaymentTime = bankPaymentTime.trim().substring(0,19);
      }
    }

    public String getBankPaymentTime(){
        return Str.rightTrim(BankPaymentTime);
    }
	
    
    
	public String getDeficitFlag() {
		return DeficitFlag;
	}

	public void setDeficitFlag(String deficitFlag) {
		DeficitFlag = Str.rightTrim(deficitFlag);
	}
    
	
	public String getIP() {
		return IP;
	}

	public void setIP(String ip) {
		IP = Str.rightTrim(ip);
	}

	public String getUsbKey() {
		return UsbKey;
	}

	public void setUsbKey(String usbKey) {
		UsbKey = Str.rightTrim(usbKey);
	}
	
    /**
     * @param iSchema 对象CIInsureValidSchema
     */       
    public void setSchema(CIInsureValidSchema iSchema)
    {
        this.ValidNo = iSchema.getValidNo();
        this.DemandNo = iSchema.getDemandNo();
        this.ProposalNo = iSchema.getProposalNo();
        this.PolicyNo = iSchema.getPolicyNo();
        this.InsureMarkNo = iSchema.getInsureMarkNo();
        this.BussinessNature = iSchema.getBussinessNature();
        this.OperateDate = iSchema.getOperateDate();
        this.Clauses = iSchema.getClauses();
        this.HandlerName = iSchema.getHandlerName();
        this.OperatorName = iSchema.getOperatorName();
        this.Currency = iSchema.getCurrency();
        this.Premium = iSchema.getPremium();
        this.ChangeReason = iSchema.getChangeReason();
        this.ChangeDetail = iSchema.getChangeDetail();
        this.ComCode = iSchema.getComCode();
        this.ValidTime = iSchema.getValidTime();
        this.Remark = iSchema.getRemark();
        this.ReinsureFlag = iSchema.getReinsureFlag();
        this.Flag = iSchema.getFlag();
        
        this.ImmeValidFlag = iSchema.getImmeValidFlag();
        this.ImmeValidDate = iSchema.getImmeValidDate();
        
        
        this.WarrantNo = iSchema.getWarrantNo();
        this.BankPaymentTime = iSchema.getBankPaymentTime();
        
        
        this.DeficitFlag=iSchema.getDeficitFlag();
        
        
        this.IP = iSchema.getIP();
        this.UsbKey = iSchema.getUsbKey();
        
    }
}
