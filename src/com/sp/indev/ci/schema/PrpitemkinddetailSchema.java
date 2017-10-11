package com.sp.indiv.ci.schema;

import java.io.Serializable;

import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义prpitemkinddetail的数据传输对象类
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-03-26</p>
 * @author DtoGenerator
 * @version 1.1
 */
public class PrpitemkinddetailSchema implements Serializable{
    
    private String Businessno = "";
    
    private String Businesstype = "";
    
    private String Itemkindno = "";
    
    private String Kindcode = "";
    
    private String Kindcodedetail = "";
    
    private String BenchMarkPremium = "";
    
    private String Premiun = "";
    
    private String StartDate = "";
    
    private String StartHour = "";
    
    private String EndDate = "";
    
    private String EndHour = "";
    
    private String Amount = "";
    

    /**
     * 构造函数
     */       
    public PrpitemkinddetailSchema(){
    }

    /**
     * 设置属性businessno
     * @param Businessno businessno
     */
    public void setBusinessno(String Businessno){
        this.Businessno = Businessno;
    }

    /**
     * 获取属性businessno
     * @return businessno
     */
    public String getBusinessno(){
        return Businessno;
    }

    /**
     * 设置属性businesstype
     * @param Businesstype businesstype
     */
    public void setBusinesstype(String Businesstype){
        this.Businesstype = Businesstype;
    }

    /**
     * 获取属性businesstype
     * @return businesstype
     */
    public String getBusinesstype(){
        return Businesstype;
    }

    /**
     * 设置属性itemkindno
     * @param Itemkindno itemkindno
     */
    public void setItemkindno(String Itemkindno){
        this.Itemkindno = Itemkindno;
    }

    /**
     * 获取属性itemkindno
     * @return itemkindno
     */
    public String getItemkindno(){
        return Itemkindno;
    }

    /**
     * 设置属性kindcode
     * @param Kindcode kindcode
     */
    public void setKindcode(String Kindcode){
        this.Kindcode = Kindcode;
    }

    /**
     * 获取属性kindcode
     * @return kindcode
     */
    public String getKindcode(){
        return Kindcode;
    }

    /**
     * 设置属性kindcodedetail
     * @param Kindcodedetail kindcodedetail
     */
    public void setKindcodedetail(String Kindcodedetail){
        this.Kindcodedetail = Kindcodedetail;
    }

    /**
     * 获取属性kindcodedetail
     * @return kindcodedetail
     */
    public String getKindcodedetail(){
        return Kindcodedetail;
    }
    
    /**
     * 设置属性BenchMarkPremium
     * @param BenchMarkPremium BenchMarkPremium
     */
    public void setBenchMarkPremium(String BenchMarkPremium){
        this.BenchMarkPremium = Str.rightTrim(BenchMarkPremium);
    }

    /**
     * 获取属性BenchMarkPremium
     * @return BenchMarkPremium
     */
    public String getBenchMarkPremium(){
        return Str.rightTrim(BenchMarkPremium);
    }

    /**
     * 设置属性premiun
     * @param Premiun premiun
     */
    public void setPremiun(String Premiun){
        this.Premiun = Premiun;
    }

    /**
     * 获取属性premiun
     * @return premiun
     */
    public String getPremiun(){
        return Premiun;
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
     * 设置属性StartHour
     * @param StartHour StartHour
     */
    public void setStartHour(String StartHour){
        this.StartHour = Str.rightTrim(StartHour);
    }

    /**
     * 获取属性StartHour
     * @return StartHour
     */
    public String getStartHour(){
        return Str.rightTrim(StartHour);
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
     * 设置属性EndHour
     * @param EndHour EndHour
     */
    public void setEndHour(String EndHour){
        this.EndHour = Str.rightTrim(EndHour);
    }

    /**
     * 获取属性EndHour
     * @return EndHour
     */
    public String getEndHour(){
        return Str.rightTrim(EndHour);
    }
    
    /**
     * 设置属性Amount
     * @param Amount Amount
     */
    public void setAmount(String Amount){
        this.Amount = Str.rightTrim(Amount);
    }

    /**
     * 获取属性Amount
     * @return Amount
     */
    public String getAmount(){
        return Str.rightTrim(Amount);
    }


    /**
     * @param iSchema 对象PrpitemkinddetailSchema
     */       
    public void setSchema(PrpitemkinddetailSchema iSchema)
    {
        this.Businessno = iSchema.getBusinessno();
        this.Businesstype = iSchema.getBusinesstype();
        this.Itemkindno = iSchema.getItemkindno();
        this.Kindcode = iSchema.getKindcode();
        this.Kindcodedetail = iSchema.getKindcodedetail();
        this.BenchMarkPremium = iSchema.getBenchMarkPremium();
        this.Premiun = iSchema.getPremiun();
        this.StartDate = iSchema.getStartDate();
        this.StartHour = iSchema.getStartHour();
        this.EndDate = iSchema.getEndDate();
        this.EndHour = iSchema.getEndHour();
        this.Amount = iSchema.getAmount();
    }
}
