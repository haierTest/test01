package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ����XXȷ������-CIInsureValid�����ݴ��������
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
     * ���캯��
     */       
    public CIInsureValidSchema(){
    }

    /**
     * ��������ȷ����
     * @param ValidNo ȷ����
     */
    public void setValidNo(String ValidNo){
        this.ValidNo = Str.rightTrim(ValidNo);
    }

    /**
     * ��ȡ����ȷ����
     * @return ȷ����
     */
    public String getValidNo(){
        return Str.rightTrim(ValidNo);
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
     * ��������XX������
     * @param ProposalNo XX������
     */
    public void setProposalNo(String ProposalNo){
        this.ProposalNo = Str.rightTrim(ProposalNo);
    }

    /**
     * ��ȡ����XX������
     * @return XX������
     */
    public String getProposalNo(){
        return Str.rightTrim(ProposalNo);
    }

    /**
     * ��������XX����
     * @param PolicyNo XX����
     */
    public void setPolicyNo(String PolicyNo){
        this.PolicyNo = Str.rightTrim(PolicyNo);
    }

    /**
     * ��ȡ����XX����
     * @return XX����
     */
    public String getPolicyNo(){
        return Str.rightTrim(PolicyNo);
    }

    /**
     * ��������XX��־����
     * @param InsureMarkNo XX��־����
     */
    public void setInsureMarkNo(String InsureMarkNo){
        this.InsureMarkNo = Str.rightTrim(InsureMarkNo);
    }

    /**
     * ��ȡ����XX��־����
     * @return XX��־����
     */
    public String getInsureMarkNo(){
        return Str.rightTrim(InsureMarkNo);
    }

    /**
     * ����������������
     * @param BussinessNature ��������
     */
    public void setBussinessNature(String BussinessNature){
        this.BussinessNature = Str.rightTrim(BussinessNature);
    }

    /**
     * ��ȡ������������
     * @return ��������
     */
    public String getBussinessNature(){
        return Str.rightTrim(BussinessNature);
    }

    /**
     * ��������ǩ������
     * @param OperateDate ǩ������
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
     * ��ȡ����ǩ������
     * @return ǩ������
     */
    public String getOperateDate(){
        return Str.rightTrim(OperateDate);
    }

    /**
     * ���������ر�Լ��
     * @param Clauses �ر�Լ��
     */
    public void setClauses(String Clauses){
        this.Clauses = Str.rightTrim(Clauses);
    }

    /**
     * ��ȡ�����ر�Լ��
     * @return �ر�Լ��
     */
    public String getClauses(){
        return Str.rightTrim(Clauses);
    }

    /**
     * �������Ծ�����
     * @param HandlerName ������
     */
    public void setHandlerName(String HandlerName){
        this.HandlerName = Str.rightTrim(HandlerName);
    }

    /**
     * ��ȡ���Ծ�����
     * @return ������
     */
    public String getHandlerName(){
        return Str.rightTrim(HandlerName);
    }

    /**
     * �������Բ���Ա
     * @param OperatorName ����Ա
     */
    public void setOperatorName(String OperatorName){
        this.OperatorName = Str.rightTrim(OperatorName);
    }

    /**
     * ��ȡ���Բ���Ա
     * @return ����Ա
     */
    public String getOperatorName(){
        return Str.rightTrim(OperatorName);
    }

    /**
     * �������Աұ�
     * @param Currency �ұ�
     */
    public void setCurrency(String Currency){
        this.Currency = Str.rightTrim(Currency);
    }

    /**
     * ��ȡ���Աұ�
     * @return �ұ�
     */
    public String getCurrency(){
        return Str.rightTrim(Currency);
    }

    /**
     * ��������ʵ��XX
     * @param Premium ʵ��XX
     */
    public void setPremium(String Premium){
        this.Premium = Str.rightTrim(Premium);
    }

    /**
     * ��ȡ����ʵ��XX
     * @return ʵ��XX
     */
    public String getPremium(){
        return Str.rightTrim(Premium);
    }

    /**
     * ��������ʵ��XX�仯ԭ��
     * @param ChangeReason ʵ��XX�仯ԭ��
     */
    public void setChangeReason(String ChangeReason){
        this.ChangeReason = Str.rightTrim(ChangeReason);
    }

    /**
     * ��ȡ����ʵ��XX�仯ԭ��
     * @return ʵ��XX�仯ԭ��
     */
    public String getChangeReason(){
        return Str.rightTrim(ChangeReason);
    }

    /**
     * ��������ʵ��XX�仯ԭ������
     * @param ChangeDetail ʵ��XX�仯ԭ������
     */
    public void setChangeDetail(String ChangeDetail){
        this.ChangeDetail = Str.rightTrim(ChangeDetail);
    }

    /**
     * ��ȡ����ʵ��XX�仯ԭ������
     * @return ʵ��XX�仯ԭ������
     */
    public String getChangeDetail(){
        return Str.rightTrim(ChangeDetail);
    }

    /**
     * �������Թ�������
     * @param ComCode ��������
     */
    public void setComCode(String ComCode){
        this.ComCode = Str.rightTrim(ComCode);
    }

    /**
     * ��ȡ���Թ�������
     * @return ��������
     */
    public String getComCode(){
        return Str.rightTrim(ComCode);
    }

    /**
     * ��������ȷ��ʱ��
     * @param ValidTime ȷ��ʱ��
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
     * ��ȡ����ȷ��ʱ��
     * @return ȷ��ʱ��
     */
    public String getValidTime(){
        return Str.rightTrim(ValidTime);
    }

    /**
     * �������Ա�ע
     * @param Remark ��ע
     */
    public void setRemark(String Remark){
        this.Remark = Str.rightTrim(Remark);
    }

    /**
     * ��ȡ���Ա�ע
     * @return ��ע
     */
    public String getRemark(){
        return Str.rightTrim(Remark);
    }

    /**
     * ��������״̬�ֶ�
     * @param Flag ״̬�ֶ�
     */
    public void setFlag(String Flag){
        this.Flag = Flag;
    }

    /**
     * ��ȡ����״̬�ֶ�
     * @return ״̬�ֶ�
     */
    public String getFlag(){
        return Flag;
    }
    
    /**
     * ��ȡ���Դ�������
     * @return ��������
     */
    public String getErrorMessage()
    {
    	return Str.rightTrim(ErrorMessage);
    }
    
    /**
     * ��ȡ���Դ�������
     * @return ��������
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
     * @param iSchema ����CIInsureValidSchema
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
