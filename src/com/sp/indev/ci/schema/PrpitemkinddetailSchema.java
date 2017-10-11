package com.sp.indiv.ci.schema;

import java.io.Serializable;

import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ����prpitemkinddetail�����ݴ��������
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
     * ���캯��
     */       
    public PrpitemkinddetailSchema(){
    }

    /**
     * ��������businessno
     * @param Businessno businessno
     */
    public void setBusinessno(String Businessno){
        this.Businessno = Businessno;
    }

    /**
     * ��ȡ����businessno
     * @return businessno
     */
    public String getBusinessno(){
        return Businessno;
    }

    /**
     * ��������businesstype
     * @param Businesstype businesstype
     */
    public void setBusinesstype(String Businesstype){
        this.Businesstype = Businesstype;
    }

    /**
     * ��ȡ����businesstype
     * @return businesstype
     */
    public String getBusinesstype(){
        return Businesstype;
    }

    /**
     * ��������itemkindno
     * @param Itemkindno itemkindno
     */
    public void setItemkindno(String Itemkindno){
        this.Itemkindno = Itemkindno;
    }

    /**
     * ��ȡ����itemkindno
     * @return itemkindno
     */
    public String getItemkindno(){
        return Itemkindno;
    }

    /**
     * ��������kindcode
     * @param Kindcode kindcode
     */
    public void setKindcode(String Kindcode){
        this.Kindcode = Kindcode;
    }

    /**
     * ��ȡ����kindcode
     * @return kindcode
     */
    public String getKindcode(){
        return Kindcode;
    }

    /**
     * ��������kindcodedetail
     * @param Kindcodedetail kindcodedetail
     */
    public void setKindcodedetail(String Kindcodedetail){
        this.Kindcodedetail = Kindcodedetail;
    }

    /**
     * ��ȡ����kindcodedetail
     * @return kindcodedetail
     */
    public String getKindcodedetail(){
        return Kindcodedetail;
    }
    
    /**
     * ��������BenchMarkPremium
     * @param BenchMarkPremium BenchMarkPremium
     */
    public void setBenchMarkPremium(String BenchMarkPremium){
        this.BenchMarkPremium = Str.rightTrim(BenchMarkPremium);
    }

    /**
     * ��ȡ����BenchMarkPremium
     * @return BenchMarkPremium
     */
    public String getBenchMarkPremium(){
        return Str.rightTrim(BenchMarkPremium);
    }

    /**
     * ��������premiun
     * @param Premiun premiun
     */
    public void setPremiun(String Premiun){
        this.Premiun = Premiun;
    }

    /**
     * ��ȡ����premiun
     * @return premiun
     */
    public String getPremiun(){
        return Premiun;
    }
    
    /**
     * ��������StartDate
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
     * ��ȡ����StartDate
     * @return StartDate
     */
    public String getStartDate(){
        return Str.rightTrim(StartDate);
    }

    /**
     * ��������StartHour
     * @param StartHour StartHour
     */
    public void setStartHour(String StartHour){
        this.StartHour = Str.rightTrim(StartHour);
    }

    /**
     * ��ȡ����StartHour
     * @return StartHour
     */
    public String getStartHour(){
        return Str.rightTrim(StartHour);
    }

    /**
     * ��������EndDate
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
     * ��ȡ����EndDate
     * @return EndDate
     */
    public String getEndDate(){
        return Str.rightTrim(EndDate);
    }

    /**
     * ��������EndHour
     * @param EndHour EndHour
     */
    public void setEndHour(String EndHour){
        this.EndHour = Str.rightTrim(EndHour);
    }

    /**
     * ��ȡ����EndHour
     * @return EndHour
     */
    public String getEndHour(){
        return Str.rightTrim(EndHour);
    }
    
    /**
     * ��������Amount
     * @param Amount Amount
     */
    public void setAmount(String Amount){
        this.Amount = Str.rightTrim(Amount);
    }

    /**
     * ��ȡ����Amount
     * @return Amount
     */
    public String getAmount(){
        return Str.rightTrim(Amount);
    }


    /**
     * @param iSchema ����PrpitemkinddetailSchema
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
