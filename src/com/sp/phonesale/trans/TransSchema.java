package com.sp.phonesale.trans;

import com.sp.utility.string.Str;

/*
 * modify by zhengxiaoluo 20091119 增加字符串null处理
 */
public class TransSchema {

    /**
     * @param args
     */
    public static void main(String[] args) {
        

    }
    
    private String contNo           = "";  
    private String payTimes         = "";  
    private String comCode          = "";  
    private String operCode         = "";  
    private String grade            = "";  
    private String startDate        = "";  
    private String endDate          = "";  
    private String newpaymode       = "";  
    private String extpaymode       = "";  
    private String myheadPayMode    = "";  
    private String policyType       = "";  
    private String riskFlag         = "";  
    private String mainProposalNo   = "";  
    private String subOneProposalNo = "";  
    private String subTowProposalNo = "";  
    private String childrenCount    = "";  
    private String sumPremium       = "";  
    private String childrenNames    = "";  
    
    public void setContNo(String contNo){
        this.contNo=Str.rightTrim(contNo);
    }
    
    public String getContNo(){
        return Str.rightTrim(contNo);
    }
    
    public void setPayTimes(String payTimes){
        this.payTimes=Str.rightTrim(payTimes);
    }
    
    public String getPayTimes(){
        return Str.rightTrim(payTimes);
    }
    
    public void setComCode(String comCode){
        this.comCode=Str.rightTrim(comCode);
    }
    
    public String getComCode(){
        return Str.rightTrim(comCode);
    }
    
    public void setOperCode(String operCode){
        this.operCode=Str.rightTrim(operCode);
    }
      
    public String getOperCode(){
        return Str.rightTrim(operCode);
    }
    
    public void setGrade(String grade){
        this.grade=Str.rightTrim(grade);
    }
    
    public String getGrade(){
        return Str.rightTrim(grade);
    }
    
    public void setStartDate(String startDate){
        this.startDate=Str.rightTrim(startDate);
    }
    
    public String getStartDate(){ 
        return Str.rightTrim(startDate);
    }
    
    public void setEndDate(String endDate){
        this.endDate=Str.rightTrim(endDate);
    }
    
    public String getEndDate(){
        return Str.rightTrim(endDate);
    }
    
    public void setNewpaymode(String newpaymode){
        this.newpaymode=Str.rightTrim(newpaymode);
    }
    
    public String getNewpaymode(){
        return Str.rightTrim(newpaymode);
    }
    
    public void setExtpaymode(String extpaymode){
        this.extpaymode=Str.rightTrim(extpaymode);
    }
    
    public String getExtpaymode(){
        return Str.rightTrim(extpaymode);
    }
    
    public void setMyheadPayMode(String myheadPayMode){
        this.myheadPayMode=Str.rightTrim(myheadPayMode);
    }
    
    public String getMyheadPayMode(){
        return Str.rightTrim(myheadPayMode);
    }
    
    public void setPolicyType(String policyType){
        this.policyType=Str.rightTrim(policyType);
    }
    
    public String getPolicyType(){
        return Str.rightTrim(policyType);
    }
    
    public void setRiskFlag(String riskFlag){
        this.riskFlag=Str.rightTrim(riskFlag);
    }
    
    public String getRiskFlag(){
        return Str.rightTrim(riskFlag);
    }
    
    public void setMainProposalNo(String mainProposalNo){
        this.mainProposalNo=Str.rightTrim(mainProposalNo);
    }
    
    public String getMainProposalNo(){
        return Str.rightTrim(mainProposalNo);
    }
    
    public void setSubOneProposalNo(String subOneProposalNo){
        this.subOneProposalNo=Str.rightTrim(subOneProposalNo);
    }
    
    public String getSubOneProposalNo(){
        return Str.rightTrim(subOneProposalNo);
    }
    
    public void setSubTowProposalNo(String subTowProposalNo){
        this.subTowProposalNo=Str.rightTrim(subTowProposalNo);
    }
    
    public String getSubTowProposalNo(){
        return Str.rightTrim(subTowProposalNo);
    }
    
    public void setChildrenCount(String childrenCount){
        this.childrenCount=Str.rightTrim(childrenCount);
    }
    
    public String getChildrenCount(){
        return Str.rightTrim(childrenCount);
    }
    
    public void setChildrenNames(String childrenNames){
        this.childrenNames=Str.rightTrim(childrenNames);
    }
    
    public String getChildrenNames(){
        return Str.rightTrim(childrenNames);
    }
    
    public void setSumPremium(String sumPremium){
        this.sumPremium=Str.rightTrim(sumPremium);
    }
    
    public String getSumPremium(){
        return Str.rightTrim(sumPremium);
    }
}
