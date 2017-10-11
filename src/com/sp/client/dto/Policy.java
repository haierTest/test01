package com.sp.client.dto;
import java.util.*;

public class Policy {
	BaseInfoBean baseInfoBean = null;
	VhlInfoBean vhlInfoBean = null;
	ArrayList	rdrList = null;
	ArrayList	payList = null;
	ArrayList 	vchList = null;
	public void setbaseInfo (BaseInfoBean bib){
		this.baseInfoBean = bib;
	}
	public BaseInfoBean getbaseInfoBase(){
		return this.baseInfoBean;
	}
	public void setvhlInfoBean (VhlInfoBean vib){
		this.vhlInfoBean = vib;
	}
	public VhlInfoBean getvhlInfoBean(){
		return this.vhlInfoBean;
	}
	public void setrdrList (ArrayList rl){
		this.rdrList = rl;
	}
	public ArrayList getrdrList(){
		return this.rdrList;
	}
	public void setpayList (ArrayList pl){
		this.payList = pl;
	}
	public ArrayList getpayList(){
		return this.payList;
	}
	public void setvchList (ArrayList vl){
		this.vchList = vl;
	}
	public ArrayList getvchList(){
		return this.vchList;
	}
}
