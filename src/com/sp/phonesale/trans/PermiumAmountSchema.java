package com.sp.phonesale.trans;

public class PermiumAmountSchema {

    private String  SumAmount="";
    private String  SumPremium="";
    private String  Sum27Amount="";
    private String  Sum03Amount="";
    private String  Sum27Permium="";
    private String  Sum03Permium="";
    /**
     * @param args
     */
    public void setSumPremium(String SumPremium){
        this.SumPremium=SumPremium;
    }
    
    public void setSumAmount(String SumAmount){
        this.SumAmount=SumAmount;
    }
    
    public void setSum27Amount(String Sum27Amount){
        this.Sum27Amount=Sum27Amount;
    }
    
    public void setSum03Amount(String Sum03Amount){
        this.Sum03Amount=Sum03Amount;
    }
    
    public void setSum27Permium(String Sum27Permium){
        this.Sum27Permium=Sum27Permium;
    }
    
    public void setSum03Permium(String Sum03Permium){
        this.Sum03Permium=Sum03Permium;
    }
    
    public String getSumAmount(){
        return SumAmount;
    }

    public String getSumPremium(){
        return SumPremium;
    }
    
    public String getSum27Amount(){
        return Sum27Amount;
    }
    
    public String getSum03Amount(){
        return Sum03Amount;
    }
    
    public String getSum27Permium(){
        return Sum27Permium;
    }
    
    public String getSum03Permium(){
        return Sum03Permium;
    }
}
