package com.sp.prpall.blsvr.misc;

import java.util.HashMap;
import java.util.Map;


public class BLLifetableMotorcade {
	
    private BLPrpMotorcade blPrpMotorcade = new BLPrpMotorcade();
    private BLPrpdMotorcadeExpense blPrpdMotorcadeExpense = new BLPrpdMotorcadeExpense();
    private BLPrpmotorcadeCoef blPrpmotorcadeCoef = new BLPrpmotorcadeCoef();
    private BLPrpmotorcadeDeclare blPrpmotorcadeDeclare = new BLPrpmotorcadeDeclare();
    private Map discountCI = new HashMap();
       
	public BLPrpMotorcade getBlPrpMotorcade() {
		return blPrpMotorcade;
	}
	public void setBlPrpMotorcade(BLPrpMotorcade blPrpMotorcade) {
		this.blPrpMotorcade = blPrpMotorcade;
	}
	public BLPrpdMotorcadeExpense getBlPrpdMotorcadeExpense() {
		return blPrpdMotorcadeExpense;
	}
	public void setBlPrpdMotorcadeExpense(
			BLPrpdMotorcadeExpense blPrpdMotorcadeExpense) {
		this.blPrpdMotorcadeExpense = blPrpdMotorcadeExpense;
	}
	public BLPrpmotorcadeCoef getBlPrpmotorcadeCoef() {
		return blPrpmotorcadeCoef;
	}
	public void setBlPrpmotorcadeCoef(BLPrpmotorcadeCoef blPrpmotorcadeCoef) {
		this.blPrpmotorcadeCoef = blPrpmotorcadeCoef;
	}
	public BLPrpmotorcadeDeclare getBlPrpmotorcadeDeclare() {
		return blPrpmotorcadeDeclare;
	}
	public void setBlPrpmotorcadeDeclare(BLPrpmotorcadeDeclare blPrpmotorcadeDeclare) {
		this.blPrpmotorcadeDeclare = blPrpmotorcadeDeclare;
	}
	public Map getDiscountCI() {
		return discountCI;
	}
	public void setDiscountCI(Map discountCI) {
		this.discountCI = discountCI;
	}
	

}
