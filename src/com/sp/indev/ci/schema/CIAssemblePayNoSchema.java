package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 定义车船税汇总完税凭证表的数据传输对象类
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>@createdate 2008-01-21</p>
 * @author DtoGenerator
 * @version 1.0
 */
public class CIAssemblePayNoSchema implements Serializable{
    
    private String CheckNo = "";
    
    private String StartDate = "";
    
    private String EndDate = "";
    
    private String DeclareNo = "";
    
    private String ResponseCode = "";
    
    private String Remark = "";
    
    private String ExtendChar1 = "";
    
    private String Flag = "";
    
    private String PayBookNum = "";

    /**
     * 构造函数
     */       
    public CIAssemblePayNoSchema(){
    }

    /**
     * 设置属性对账号
     * @param CheckNo 对账号
     */
    public void setCheckNo(String CheckNo){
        this.CheckNo = Str.rightTrim(CheckNo);
    }

    /**
     * 获取属性对账号
     * @return 对账号
     */
    public String getCheckNo(){
        return Str.rightTrim(CheckNo);
    }

    /**
     * 设置属性对账起期
     * @param StartDate 对账起期
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
     * 获取属性对账起期
     * @return 对账起期
     */
    public String getStartDate(){
        return Str.rightTrim(StartDate);
    }

    /**
     * 设置属性对账止期
     * @param EndDate 对账止期
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
     * 获取属性对账止期
     * @return 对账止期
     */
    public String getEndDate(){
        return Str.rightTrim(EndDate);
    }

    /**
     * 设置属性申报编号
     * @param DeclareNo 申报编号
     */
    public void setDeclareNo(String DeclareNo){
        this.DeclareNo = Str.rightTrim(DeclareNo);
    }

    /**
     * 获取属性申报编号
     * @return 申报编号
     */
    public String getDeclareNo(){
        return Str.rightTrim(DeclareNo);
    }

    /**
     * 设置属性返回类型代码
     * @param ResponseCode 返回类型代码
     */
    public void setResponseCode(String ResponseCode){
        this.ResponseCode = Str.rightTrim(ResponseCode);
    }

    /**
     * 获取属性返回类型代码
     * @return 返回类型代码
     */
    public String getResponseCode(){
        return Str.rightTrim(ResponseCode);
    }

    /**
     * 设置属性错误描述
     * @param Remark 错误描述
     */
    public void setRemark(String Remark){
        this.Remark = Str.rightTrim(Remark);
    }

    /**
     * 获取属性错误描述
     * @return 错误描述
     */
    public String getRemark(){
        return Str.rightTrim(Remark);
    }

    /**
     * 设置属性扩展字段
     * @param ExtendChar1 扩展字段
     */
    public void setExtendChar1(String ExtendChar1){
        this.ExtendChar1 = Str.rightTrim(ExtendChar1);
    }

    /**
     * 获取属性扩展字段
     * @return 扩展字段
     */
    public String getExtendChar1(){
        return Str.rightTrim(ExtendChar1);
    }

    /**
     * 设置属性标志位
     * @param Flag 标志位
     */
    public void setFlag(String Flag){
        this.Flag = Str.rightTrim(Flag);
    }

    /**
     * 获取属性标志位
     * @return 标志位
     */
    public String getFlag(){
        return Str.rightTrim(Flag);
    }

    /**
     * 设置属性缴款书总张数
     * @param PayBookNum 缴款书总张数
     */
    public void setPayBookNum(String PayBookNum){
        this.PayBookNum = Str.rightTrim(PayBookNum);
    }

    /**
     * 获取属性缴款书总张数
     * @return 缴款书总张数
     */
    public String getPayBookNum(){
        return Str.rightTrim(PayBookNum);
    }

    /**
     * @param iSchema 对象CIAssemblePayNoSchema
     */       
    public void setSchema(CIAssemblePayNoSchema iSchema)
    {
        this.CheckNo = iSchema.getCheckNo();
        this.StartDate = iSchema.getStartDate();
        this.EndDate = iSchema.getEndDate();
        this.DeclareNo = iSchema.getDeclareNo();
        this.ResponseCode = iSchema.getResponseCode();
        this.Remark = iSchema.getRemark();
        this.ExtendChar1 = iSchema.getExtendChar1();
        this.Flag = iSchema.getFlag();
        this.PayBookNum = iSchema.getPayBookNum();
    }
}
