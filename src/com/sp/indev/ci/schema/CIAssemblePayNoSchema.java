package com.sp.indiv.ci.schema;

import java.io.Serializable;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * ���峵��˰������˰ƾ֤������ݴ��������
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
     * ���캯��
     */       
    public CIAssemblePayNoSchema(){
    }

    /**
     * �������Զ��˺�
     * @param CheckNo ���˺�
     */
    public void setCheckNo(String CheckNo){
        this.CheckNo = Str.rightTrim(CheckNo);
    }

    /**
     * ��ȡ���Զ��˺�
     * @return ���˺�
     */
    public String getCheckNo(){
        return Str.rightTrim(CheckNo);
    }

    /**
     * �������Զ�������
     * @param StartDate ��������
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
     * ��ȡ���Զ�������
     * @return ��������
     */
    public String getStartDate(){
        return Str.rightTrim(StartDate);
    }

    /**
     * �������Զ���ֹ��
     * @param EndDate ����ֹ��
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
     * ��ȡ���Զ���ֹ��
     * @return ����ֹ��
     */
    public String getEndDate(){
        return Str.rightTrim(EndDate);
    }

    /**
     * ���������걨���
     * @param DeclareNo �걨���
     */
    public void setDeclareNo(String DeclareNo){
        this.DeclareNo = Str.rightTrim(DeclareNo);
    }

    /**
     * ��ȡ�����걨���
     * @return �걨���
     */
    public String getDeclareNo(){
        return Str.rightTrim(DeclareNo);
    }

    /**
     * �������Է������ʹ���
     * @param ResponseCode �������ʹ���
     */
    public void setResponseCode(String ResponseCode){
        this.ResponseCode = Str.rightTrim(ResponseCode);
    }

    /**
     * ��ȡ���Է������ʹ���
     * @return �������ʹ���
     */
    public String getResponseCode(){
        return Str.rightTrim(ResponseCode);
    }

    /**
     * �������Դ�������
     * @param Remark ��������
     */
    public void setRemark(String Remark){
        this.Remark = Str.rightTrim(Remark);
    }

    /**
     * ��ȡ���Դ�������
     * @return ��������
     */
    public String getRemark(){
        return Str.rightTrim(Remark);
    }

    /**
     * ����������չ�ֶ�
     * @param ExtendChar1 ��չ�ֶ�
     */
    public void setExtendChar1(String ExtendChar1){
        this.ExtendChar1 = Str.rightTrim(ExtendChar1);
    }

    /**
     * ��ȡ������չ�ֶ�
     * @return ��չ�ֶ�
     */
    public String getExtendChar1(){
        return Str.rightTrim(ExtendChar1);
    }

    /**
     * �������Ա�־λ
     * @param Flag ��־λ
     */
    public void setFlag(String Flag){
        this.Flag = Str.rightTrim(Flag);
    }

    /**
     * ��ȡ���Ա�־λ
     * @return ��־λ
     */
    public String getFlag(){
        return Str.rightTrim(Flag);
    }

    /**
     * �������Խɿ���������
     * @param PayBookNum �ɿ���������
     */
    public void setPayBookNum(String PayBookNum){
        this.PayBookNum = Str.rightTrim(PayBookNum);
    }

    /**
     * ��ȡ���Խɿ���������
     * @return �ɿ���������
     */
    public String getPayBookNum(){
        return Str.rightTrim(PayBookNum);
    }

    /**
     * @param iSchema ����CIAssemblePayNoSchema
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
