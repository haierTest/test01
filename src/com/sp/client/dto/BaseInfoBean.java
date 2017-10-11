package com.sp.client.dto;

import com.sp.sysframework.exceptionlog.UserException;

public class BaseInfoBean {
	
	String C_PLY_APP_NO = null;
	
	String C_PLY_NO= null;
	
	String C_BSNS_TYP = null;
	
	String C_DPT_CDE = null;
	
	String C_CRT_CDE = null;
	
	String C_SLS_CDE = null;
	
	String C_AGT_CDE = null;
	
	String C_AGT_NO = null;
	
	String C_APP_CNM = null;
	
	String C_INSRNT_CNM = null;
	
	String C_INSRNT_ADDR = null;
	
	String C_INSRNT_ZIP = null;
	
	String C_INSRNT_TEL = null;
	
	String C_INSRNT_ID = null;
	
	String C_OWN_CNM = null;
	
	String D_APP_DATE = null;
	
	String D_SIGN_DATE = null;
	
	String D_INSBGN_DATE = null;
	
	String D_INSEND_DATE = null;
	
	double N_AMT = 0.0;
	
	double N_PRM = 0.0;
	
	double N_CMM_PROP = 0.0;
	
	double N_RATIO = 0.0;
	
	String C_SHORT_RSN = null;
	
	String C_CUR_CDE = null;
	
	String C_STTL_CDE = null;
	
	String C_APPNT_CDE = null;
	
	String C_APPNT = null;
	
	String C_NOTICE = null;
	
	String C_PROPERTY="3";
	
	String C_SEX = "1";
	
	String N_AGE = "0";
	
	String C_MARRIAGE = "1";
	
	String C_VHL_REL = "1";
	
	String C_TAT_CLNT = "1";
	
	public void setPlyAppNo ( String stPlyAppNo ){
		this.C_PLY_APP_NO = stPlyAppNo;
	}
	public String getPlyAppNo ( ) {
		return this.C_PLY_APP_NO;
	}
	
	public void setPlyNo ( String stPlyNo ){
		this.C_PLY_NO = stPlyNo;
	}
	public String getPlyNo ( ) {
		return this.C_PLY_NO;
	}
	
	public void setBsnsType ( String stBsnsType ){
		this.C_BSNS_TYP = stBsnsType;
	}
	public String getBsnsType ( ) {
		return this.C_BSNS_TYP;
	}
	
	public void setDptCde ( String stDptCde ){
		this.C_DPT_CDE = stDptCde;
	}
	public String getDptCde ( ) {
		return this.C_DPT_CDE;
	}
	
	public void setSlsCde ( String stSlsCde ){
		this.C_SLS_CDE = stSlsCde;
	}
	public String getSlsCde ( ) {
		return this.C_SLS_CDE;
	}
	
	public void setAgtCde ( String stAgtCde ){
		this.C_AGT_CDE = stAgtCde;
	}
	public String getAgtCde ( ) {
		return this.C_AGT_CDE;
	}
	
	public void setAgtNo ( String stAgtNo ){
		this.C_AGT_NO = stAgtNo;
	}
	public String getAgtNo ( ) {
		return this.C_AGT_NO;
	}
	
	public void setAppCnm ( String stAppCnm ){
		this.C_APP_CNM = stAppCnm;
	}
	public String getAppCnm ( ) {
		return this.C_APP_CNM;
	}
	
	public void setInsrntCnm ( String stInsrntCnm ){
		this.C_INSRNT_CNM = stInsrntCnm;
	}
	public String getInsrntCnm ( ) {
		return this.C_INSRNT_CNM;
	}
	
	public void setInsrntAdd ( String stInsrntAdd ){
		this.C_INSRNT_ADDR = stInsrntAdd;
	}
	public String getInsrntAdd ( ) {
		return this.C_INSRNT_ADDR;
	}
	
	public void setInsrntZip ( String stInsrntZip ){
		this.C_INSRNT_ZIP = stInsrntZip;
	}
	public String getInsrntZip ( ) {
		return this.C_INSRNT_ZIP;
	}
	
	public void setInsrntTel ( String stInsrntTel ){
		this.C_INSRNT_TEL = stInsrntTel;
	}
	public String getInsrntTel ( ) {
		return this.C_INSRNT_TEL;
	}
	
	public void setInsrntId ( String stInsrntId ){
		this.C_INSRNT_ID = stInsrntId;
	}
	public String getInsrntId ( ) {
		return this.C_INSRNT_ID;
	}
	
	public void setOwnCnm ( String stOwnCnm ){
		this.C_OWN_CNM = stOwnCnm;
	}
	public String getOwnCnm ( ) {
		return this.C_OWN_CNM;
	}
	
	public void setAppDate ( String stAppDate ){
		this.D_APP_DATE = stAppDate;
	}
	public String getAppDate ( ) {
		return this.D_APP_DATE;
	}
	
	public void setSignDate ( String stSignDate ){
		this.D_SIGN_DATE = stSignDate;
	}
	public String getSignDate ( ) {
		return this.D_SIGN_DATE;
	}
	
	public void setInsbgnDate ( String stInsbgnDate ){
		this.D_INSBGN_DATE = stInsbgnDate;
	}
	public String getInsbgnDate ( ) {
		return this.D_INSBGN_DATE;
	}
	
	public void setInsrntDate ( String stInsrntDate ){
		this.D_INSEND_DATE = stInsrntDate;
	}
	public String getInsrntDate ( ) {
		return this.D_INSEND_DATE;
	}
	
	public void setAmt ( double dAmt ){
		this.N_AMT = dAmt;
	}
	public double getAmt ( ) {
		return this.N_AMT;
	}
	
	public void setPrm ( double dPrm ) throws UserException{
		this.N_PRM = dPrm;
	}
	public double getPrm ( ) {
		return this.N_PRM;
	}
	
	public void setCmmProp ( double dCmmProp ){
		this.N_CMM_PROP = dCmmProp;
	}
	public double getCmmProp ( ) {
		return this.N_CMM_PROP;
	}
	
	public void setRatio ( double dRatio ){
		this.N_RATIO = dRatio;
	}
	public double getRatio ( ) {
		return this.N_RATIO;
	}
	
	public void setShortRsn ( String stShortRsn ){
		this.C_SHORT_RSN = stShortRsn;
	}
	public String getShortRsn ( ) {
		return this.C_SHORT_RSN;
	}
	
	public void setCurCde ( String stCurCde ){
		this.C_CUR_CDE = stCurCde;
	}
	public String getCurCde ( ) {
		return this.C_CUR_CDE;
	}
	
	public void setSttlCde ( String stSttlCde ){
		this.C_STTL_CDE = stSttlCde;
	}
	public String getSttlCde ( ) {
		return this.C_STTL_CDE;
	}
	
	public void setAppnt ( String stAppnt ){
		this.C_APPNT = stAppnt;
	}
	public String getAppnt ( ) {
		return this.C_APPNT;
	}
	
	public void setNotice ( String stNotice ){
		this.C_NOTICE = stNotice;
	}
	public String getNotice ( ) {
		return this.C_NOTICE;
	}
	public String getC_CRT_CDE() {
		return this.C_CRT_CDE;
	}
	public void setC_CRT_CDE(String c_crt_cde) {
		this.C_CRT_CDE = c_crt_cde;
	}
	public String getC_MARRIAGE() {
		return C_MARRIAGE;
	}
	public void setC_MARRIAGE(String c_marriage) {
		C_MARRIAGE = c_marriage;
	}
	public String getC_PROPERTY() {
		return C_PROPERTY;
	}
	public void setC_PROPERTY(String c_property) {
		C_PROPERTY = c_property;
	}
	public String getC_SEX() {
		return C_SEX;
	}
	public void setC_SEX(String c_sex) {
		C_SEX = c_sex;
	}
	public String getC_TAT_CLNT() {
		return C_TAT_CLNT;
	}
	public void setC_TAT_CLNT(String c_tat_clnt) {
		C_TAT_CLNT = c_tat_clnt;
	}
	public String getC_VHL_REL() {
		return C_VHL_REL;
	}
	public void setC_VHL_REL(String c_vhl_rel) {
		C_VHL_REL = c_vhl_rel;
	}
	public String getN_AGE() {
		return N_AGE;
	}
	public void setN_AGE(String n_age) {
		N_AGE = n_age;
	}
	public String getC_APPNT_CDE() {
		return C_APPNT_CDE;
	}
	public void setC_APPNT_CDE(String c_appnt_cde) {
		C_APPNT_CDE = c_appnt_cde;
	}
}
