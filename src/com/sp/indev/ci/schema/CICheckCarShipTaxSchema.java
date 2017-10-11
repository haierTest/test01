package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义CICheckCarShipTax的数据传输对象类
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-22</p>
 * @author DtoGenerator
 * @version 1.0
 */
public class CICheckCarShipTaxSchema implements Serializable{
    
    private String CheckNo = "";
    
    private String ComCode = "";
    
    private String StartDate = "";
    
    private String EndDate = "";
    
    private String MTotalPayNo = "";
    
    private String TTotalPayNo = "";
    
    private String MTotalTax = "";
    
    private String TTotalTax = "";
    
    private String OperateDate = "";
    
    private String OperateCode = "";
    
    private String ExtendChar1 = "";
    
    private String ExtendChar2 = "";
    
    private String CheckType = "";
    
    private String Flag = "";
    
    private String TotalPage = "";
    private String PageNum = "";
    private String SheetPayNo = "";

    /**
     * 构造函数
     */       
    public CICheckCarShipTaxSchema(){
    }

    /**
     * 设置属性CheckNo
     * @param CheckNo CheckNo
     */
    public void setCheckNo(String CheckNo){
        this.CheckNo = Str.rightTrim(CheckNo);
    }

    /**
     * 获取属性CheckNo
     * @return CheckNo
     */
    public String getCheckNo(){
        return Str.rightTrim(CheckNo);
    }

    /**
     * 设置属性ComCode
     * @param ComCode ComCode
     */
    public void setComCode(String ComCode){
        this.ComCode = Str.rightTrim(ComCode);
    }

    /**
     * 获取属性ComCode
     * @return ComCode
     */
    public String getComCode(){
        return Str.rightTrim(ComCode);
    }

    /**
     * 设置属性StartDate
     * @param StartDate StartDate
     */
    public void setStartDate(String StartDate){
      ChgDate chgDate = new ChgDate(); 
      StartDate = chgDate.toFormat(StartDate);
      if  (StartDate == null || StartDate.length() == 0 ) 
      {
        this.StartDate = "";
      }else
      {
       this.StartDate = StartDate.trim().substring(0,10);
      }
    }

    /**
     * 获取属性StartDate
     * @return StartDate
     */
    public String getStartDate(){
        return Str.rightTrim(StartDate);
    }

    /**
     * 设置属性EndDate
     * @param EndDate EndDate
     */
    public void setEndDate(String EndDate){
      ChgDate chgDate = new ChgDate(); 
      EndDate = chgDate.toFormat(EndDate);
      if  (EndDate == null || EndDate.length() == 0 ) 
      {
        this.EndDate = "";
      }else
      {
       this.EndDate = EndDate.trim().substring(0,10);
      }
    }

    /**
     * 获取属性EndDate
     * @return EndDate
     */
    public String getEndDate(){
        return Str.rightTrim(EndDate);
    }

    /**
     * 设置属性MTotalPayNo
     * @param MTotalPayNo MTotalPayNo
     */
    public void setMTotalPayNo(String MTotalPayNo){
        this.MTotalPayNo = Str.rightTrim(MTotalPayNo);
    }

    /**
     * 获取属性MTotalPayNo
     * @return MTotalPayNo
     */
    public String getMTotalPayNo(){
        return Str.rightTrim(MTotalPayNo);
    }

    /**
     * 设置属性TTotalPayNo
     * @param TTotalPayNo TTotalPayNo
     */
    public void setTTotalPayNo(String TTotalPayNo){
        this.TTotalPayNo = Str.rightTrim(TTotalPayNo);
    }

    /**
     * 获取属性TTotalPayNo
     * @return TTotalPayNo
     */
    public String getTTotalPayNo(){
        return Str.rightTrim(TTotalPayNo);
    }

    /**
     * 设置属性MTotalTax
     * @param MTotalTax MTotalTax
     */
    public void setMTotalTax(String MTotalTax){
        this.MTotalTax = Str.rightTrim(MTotalTax);
    }

    /**
     * 获取属性MTotalTax
     * @return MTotalTax
     */
    public String getMTotalTax(){
        return Str.rightTrim(MTotalTax);
    }

    /**
     * 设置属性TTotalTax
     * @param TTotalTax TTotalTax
     */
    public void setTTotalTax(String TTotalTax){
        this.TTotalTax = Str.rightTrim(TTotalTax);
    }

    /**
     * 获取属性TTotalTax
     * @return TTotalTax
     */
    public String getTTotalTax(){
        return Str.rightTrim(TTotalTax);
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
     * 设置属性OperateCode
     * @param OperateCode OperateCode
     */
    public void setOperateCode(String OperateCode){
        this.OperateCode = Str.rightTrim(OperateCode);
    }

    /**
     * 获取属性OperateCode
     * @return OperateCode
     */
    public String getOperateCode(){
        return Str.rightTrim(OperateCode);
    }

    /**
     * 设置属性ExtendChar1
     * @param ExtendChar1 ExtendChar1
     */
    public void setExtendChar1(String ExtendChar1){
        this.ExtendChar1 = Str.rightTrim(ExtendChar1);
    }

    /**
     * 获取属性ExtendChar1
     * @return ExtendChar1
     */
    public String getExtendChar1(){
        return Str.rightTrim(ExtendChar1);
    }

    /**
     * 设置属性ExtendChar2
     * @param ExtendChar2 ExtendChar2
     */
    public void setExtendChar2(String ExtendChar2){
        this.ExtendChar2 = Str.rightTrim(ExtendChar2);
    }

    /**
     * 获取属性ExtendChar2
     * @return ExtendChar2
     */
    public String getExtendChar2(){
        return Str.rightTrim(ExtendChar2);
    }

    /**
     * 设置属性CheckType
     * @param CheckType CheckType
     */
    public void setCheckType(String CheckType){
        this.CheckType = Str.rightTrim(CheckType);
    }

    /**
     * 获取属性CheckType
     * @return CheckType
     */
    public String getCheckType(){
        return Str.rightTrim(CheckType);
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
     * 设置属性TotalPage
     * @param TotalPage TotalPage
     */
    public void setTotalPage(String TotalPage){
        this.TotalPage = Str.rightTrim(TotalPage);
    }

    /**
     * 获取属性TotalPage
     * @return TotalPage
     */
    public String getTotalPage(){
        return Str.rightTrim(TotalPage);
    }
    
    /**
     * 设置属性PageNum
     * @param PageNum PageNum
     */
    public void setPageNum(String PageNum){
        this.PageNum = Str.rightTrim(PageNum);
    }

    /**
     * 获取属性PageNum
     * @return PageNum
     */
    public String getPageNum(){
        return Str.rightTrim(PageNum);
    }
    
    /**
     * 设置属性SheetPayNo
     * @param SheetPayNo SheetPayNo
     */
    public void setSheetPayNo(String SheetPayNo){
        this.SheetPayNo = Str.rightTrim(SheetPayNo);
    }

    /**
     * 获取属性SheetPayNo
     * @return SheetPayNo
     */
    public String getSheetPayNo(){
        return Str.rightTrim(SheetPayNo);
    }
    
    /**
     * @param iSchema 对象CICheckCarShipTaxSchema
     */       
    public void setSchema(CICheckCarShipTaxSchema iSchema)
    {
        this.CheckNo = iSchema.getCheckNo();
        this.ComCode = iSchema.getComCode();
        this.StartDate = iSchema.getStartDate();
        this.EndDate = iSchema.getEndDate();
        this.MTotalPayNo = iSchema.getMTotalPayNo();
        this.TTotalPayNo = iSchema.getTTotalPayNo();
        this.MTotalTax = iSchema.getMTotalTax();
        this.TTotalTax = iSchema.getTTotalTax();
        this.OperateDate = iSchema.getOperateDate();
        this.OperateCode = iSchema.getOperateCode();
        this.ExtendChar1 = iSchema.getExtendChar1();
        this.ExtendChar2 = iSchema.getExtendChar2();
        this.CheckType = iSchema.getCheckType();
        this.Flag = iSchema.getFlag();
    }
}
