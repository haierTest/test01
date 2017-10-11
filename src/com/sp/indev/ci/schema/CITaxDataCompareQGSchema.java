package com.sp.indiv.ci.schema;

import java.io.Serializable;

/**
 * ����CITaxDataCompareQG�����ݴ��������
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
     * ���캯��
     */       
    public CITaxDataCompareQGSchema(){
    }

    /**
     * ��������Type
     * @param Type Type
     */
    public void setType(String Type){
        this.Type = Type;
    }

    /**
     * ��ȡ����Type
     * @return Type
     */
    public String getType(){
        return Type;
    }

    /**
     * ��������ConFirmNo
     * @param ConFirmNo ConFirmNo
     */
    public void setConFirmNo(String ConFirmNo){
        this.ConFirmNo = ConFirmNo;
    }

    /**
     * ��ȡ����ConFirmNo
     * @return ConFirmNo
     */
    public String getConFirmNo(){
        return ConFirmNo;
    }

    /**
     * ��������TaxToTalPayIA
     * @param TaxToTalPayIA TaxToTalPayIA
     */
    public void setTaxToTalPayIA(String TaxToTalPayIA){
        this.TaxToTalPayIA = TaxToTalPayIA;
    }

    /**
     * ��ȡ����TaxToTalPayIA
     * @return TaxToTalPayIA
     */
    public String getTaxToTalPayIA(){
        return TaxToTalPayIA;
    }

    /**
     * ��������TaxToTalPayLT
     * @param TaxToTalPayLT TaxToTalPayLT
     */
    public void setTaxToTalPayLT(String TaxToTalPayLT){
        this.TaxToTalPayLT = TaxToTalPayLT;
    }

    /**
     * ��ȡ����TaxToTalPayLT
     * @return TaxToTalPayLT
     */
    public String getTaxToTalPayLT(){
        return TaxToTalPayLT;
    }

    /**
     * ��������DeclareStatusIA
     * @param DeclareStatusIA DeclareStatusIA
     */
    public void setDeclareStatusIA(String DeclareStatusIA){
        this.DeclareStatusIA = DeclareStatusIA;
    }

    /**
     * ��ȡ����DeclareStatusIA
     * @return DeclareStatusIA
     */
    public String getDeclareStatusIA(){
        return DeclareStatusIA;
    }

    /**
     * ��������DeclareStatusLT
     * @param DeclareStatusLT DeclareStatusLT
     */
    public void setDeclareStatusLT(String DeclareStatusLT){
        this.DeclareStatusLT = DeclareStatusLT;
    }

    /**
     * ��ȡ����DeclareStatusLT
     * @return DeclareStatusLT
     */
    public String getDeclareStatusLT(){
        return DeclareStatusLT;
    }

    /**
     * ��������TaxToTalPayIC
     * @param TaxToTalPayIC TaxToTalPayIC
     */
    public void setTaxToTalPayIC(String TaxToTalPayIC){
        this.TaxToTalPayIC = TaxToTalPayIC;
    }

    /**
     * ��ȡ����TaxToTalPayIC
     * @return TaxToTalPayIC
     */
    public String getTaxToTalPayIC(){
        return TaxToTalPayIC;
    }

    /**
     * ��������DeclareStatusIC
     * @param DeclareStatusIC DeclareStatusIC
     */
    public void setDeclareStatusIC(String DeclareStatusIC){
        this.DeclareStatusIC = DeclareStatusIC;
    }

    /**
     * ��ȡ����DeclareStatusIC
     * @return DeclareStatusIC
     */
    public String getDeclareStatusIC(){
        return DeclareStatusIC;
    }

    /**
     * ��������ISAmendment
     * @param ISAmendment ISAmendment
     */
    public void setISAmendment(String ISAmendment){
        this.ISAmendment = ISAmendment;
    }

    /**
     * ��ȡ����ISAmendment
     * @return ISAmendment
     */
    public String getISAmendment(){
        return ISAmendment;
    }

    /**
     * ��������ExtendChar1
     * @param ExtendChar1 ExtendChar1
     */
    public void setExtendChar1(String ExtendChar1){
        this.ExtendChar1 = ExtendChar1;
    }

    /**
     * ��ȡ����ExtendChar1
     * @return ExtendChar1
     */
    public String getExtendChar1(){
        return ExtendChar1;
    }

    /**
     * ��������ExtendChar2
     * @param ExtendChar2 ExtendChar2
     */
    public void setExtendChar2(String ExtendChar2){
        this.ExtendChar2 = ExtendChar2;
    }

    /**
     * ��ȡ����ExtendChar2
     * @return ExtendChar2
     */
    public String getExtendChar2(){
        return ExtendChar2;
    }

    /**
     * ��������StartDate
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
     * ��ȡ����StartDate
     * @return StartDate
     */
    public String getStartDate(){
        return StartDate;
    }

    /**
     * ��������EndDate
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
     * ��ȡ����EndDate
     * @return EndDate
     */
    public String getEndDate(){
        return EndDate;
    }

    /**
     * ��������CompareNo
     * @param CompareNo CompareNo
     */
    public void setCompareNo(String CompareNo){
        this.CompareNo = CompareNo;
    }

    /**
     * ��ȡ����CompareNo
     * @return CompareNo
     */
    public String getCompareNo(){
        return CompareNo;
    }

    /**
     * @param iSchema ����CITaxDataCompareQGSchema
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
