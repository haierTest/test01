package com.sp.indiv.ci.schema;

import java.io.Serializable;

/**
 * 定义CITaxDataCompareQG的数据传输对象类
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-06-16</p>
 * @author DtoGenerator
 * @version 1.1
 */
public class CITaxDataCompareQGSchema implements Serializable{
    
    private String Type = "";
    
    private String ConFirmNo = "";
    
    private String TaxToTalPayIA = "";
    
    private String TaxToTalPayLT = "";
    
    private String DeclareStatusIA = "";
    
    private String DeclareStatusLT = "";
    
    private String TaxToTalPayIC = "";
    
    private String DeclareStatusIC = "";
    
    private String ISAmendment = "";
    
    private String ExtendChar1 = "";
    
    private String ExtendChar2 = "";
    
    private String StartDate = "";
    
    private String EndDate = "";
    
    private String CompareNo = "";

    /**
     * 构造函数
     */       
    public CITaxDataCompareQGSchema(){
    }

    /**
     * 设置属性Type
     * @param Type Type
     */
    public void setType(String Type){
        this.Type = Type;
    }

    /**
     * 获取属性Type
     * @return Type
     */
    public String getType(){
        return Type;
    }

    /**
     * 设置属性ConFirmNo
     * @param ConFirmNo ConFirmNo
     */
    public void setConFirmNo(String ConFirmNo){
        this.ConFirmNo = ConFirmNo;
    }

    /**
     * 获取属性ConFirmNo
     * @return ConFirmNo
     */
    public String getConFirmNo(){
        return ConFirmNo;
    }

    /**
     * 设置属性TaxToTalPayIA
     * @param TaxToTalPayIA TaxToTalPayIA
     */
    public void setTaxToTalPayIA(String TaxToTalPayIA){
        this.TaxToTalPayIA = TaxToTalPayIA;
    }

    /**
     * 获取属性TaxToTalPayIA
     * @return TaxToTalPayIA
     */
    public String getTaxToTalPayIA(){
        return TaxToTalPayIA;
    }

    /**
     * 设置属性TaxToTalPayLT
     * @param TaxToTalPayLT TaxToTalPayLT
     */
    public void setTaxToTalPayLT(String TaxToTalPayLT){
        this.TaxToTalPayLT = TaxToTalPayLT;
    }

    /**
     * 获取属性TaxToTalPayLT
     * @return TaxToTalPayLT
     */
    public String getTaxToTalPayLT(){
        return TaxToTalPayLT;
    }

    /**
     * 设置属性DeclareStatusIA
     * @param DeclareStatusIA DeclareStatusIA
     */
    public void setDeclareStatusIA(String DeclareStatusIA){
        this.DeclareStatusIA = DeclareStatusIA;
    }

    /**
     * 获取属性DeclareStatusIA
     * @return DeclareStatusIA
     */
    public String getDeclareStatusIA(){
        return DeclareStatusIA;
    }

    /**
     * 设置属性DeclareStatusLT
     * @param DeclareStatusLT DeclareStatusLT
     */
    public void setDeclareStatusLT(String DeclareStatusLT){
        this.DeclareStatusLT = DeclareStatusLT;
    }

    /**
     * 获取属性DeclareStatusLT
     * @return DeclareStatusLT
     */
    public String getDeclareStatusLT(){
        return DeclareStatusLT;
    }

    /**
     * 设置属性TaxToTalPayIC
     * @param TaxToTalPayIC TaxToTalPayIC
     */
    public void setTaxToTalPayIC(String TaxToTalPayIC){
        this.TaxToTalPayIC = TaxToTalPayIC;
    }

    /**
     * 获取属性TaxToTalPayIC
     * @return TaxToTalPayIC
     */
    public String getTaxToTalPayIC(){
        return TaxToTalPayIC;
    }

    /**
     * 设置属性DeclareStatusIC
     * @param DeclareStatusIC DeclareStatusIC
     */
    public void setDeclareStatusIC(String DeclareStatusIC){
        this.DeclareStatusIC = DeclareStatusIC;
    }

    /**
     * 获取属性DeclareStatusIC
     * @return DeclareStatusIC
     */
    public String getDeclareStatusIC(){
        return DeclareStatusIC;
    }

    /**
     * 设置属性ISAmendment
     * @param ISAmendment ISAmendment
     */
    public void setISAmendment(String ISAmendment){
        this.ISAmendment = ISAmendment;
    }

    /**
     * 获取属性ISAmendment
     * @return ISAmendment
     */
    public String getISAmendment(){
        return ISAmendment;
    }

    /**
     * 设置属性ExtendChar1
     * @param ExtendChar1 ExtendChar1
     */
    public void setExtendChar1(String ExtendChar1){
        this.ExtendChar1 = ExtendChar1;
    }

    /**
     * 获取属性ExtendChar1
     * @return ExtendChar1
     */
    public String getExtendChar1(){
        return ExtendChar1;
    }

    /**
     * 设置属性ExtendChar2
     * @param ExtendChar2 ExtendChar2
     */
    public void setExtendChar2(String ExtendChar2){
        this.ExtendChar2 = ExtendChar2;
    }

    /**
     * 获取属性ExtendChar2
     * @return ExtendChar2
     */
    public String getExtendChar2(){
        return ExtendChar2;
    }

    /**
     * 设置属性StartDate
     * @param StartDate StartDate
     */
    public void setStartDate(String StartDate){
     if  (StartDate == null || StartDate.length() == 0 ) 
     {
        this.StartDate = "";
     }
     else
     {
        this.StartDate = StartDate.substring(0,10);
     }
    }

    /**
     * 获取属性StartDate
     * @return StartDate
     */
    public String getStartDate(){
        return StartDate;
    }

    /**
     * 设置属性EndDate
     * @param EndDate EndDate
     */
    public void setEndDate(String EndDate){
     if  (EndDate == null || EndDate.length() == 0) 
     {
        this.EndDate = "";
     }
     else
     {
        this.EndDate = EndDate.substring(0,10);
     }
    }

    /**
     * 获取属性EndDate
     * @return EndDate
     */
    public String getEndDate(){
        return EndDate;
    }

    /**
     * 设置属性CompareNo
     * @param CompareNo CompareNo
     */
    public void setCompareNo(String CompareNo){
        this.CompareNo = CompareNo;
    }

    /**
     * 获取属性CompareNo
     * @return CompareNo
     */
    public String getCompareNo(){
        return CompareNo;
    }

    /**
     * @param iSchema 对象CITaxDataCompareQGSchema
     */       
    public void setSchema(CITaxDataCompareQGSchema iSchema)
    {
        this.Type = iSchema.getType();
        this.ConFirmNo = iSchema.getConFirmNo();
        this.TaxToTalPayIA = iSchema.getTaxToTalPayIA();
        this.TaxToTalPayLT = iSchema.getTaxToTalPayLT();
        this.DeclareStatusIA = iSchema.getDeclareStatusIA();
        this.DeclareStatusLT = iSchema.getDeclareStatusLT();
        this.TaxToTalPayIC = iSchema.getTaxToTalPayIC();
        this.DeclareStatusIC = iSchema.getDeclareStatusIC();
        this.ISAmendment = iSchema.getISAmendment();
        this.ExtendChar1 = iSchema.getExtendChar1();
        this.ExtendChar2 = iSchema.getExtendChar2();
        this.StartDate = iSchema.getStartDate();
        this.EndDate = iSchema.getEndDate();
        this.CompareNo = iSchema.getCompareNo();
    }
}
