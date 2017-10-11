package com.sp.prpall.blsvr.jf;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.indiv.ci.interf.EbaoProxy;
import com.sp.indiv.ci.interf.PolicyAccConfirmDecoder;
import com.sp.indiv.ci.interf.PolicyAccConfirmEncoder;
import com.sp.indiv.ci.interf.PolicyAccQueryDecoder;
import com.sp.indiv.ci.interf.PolicyAccQueryEncoder;
import com.sp.indiv.ci.interf.PolicyFeeChangeDecoder;
import com.sp.indiv.ci.interf.PolicyFeeChangeEncoder;
import com.sp.indiv.ci.interf.PolicyPayRecDecoder;
import com.sp.indiv.ci.interf.PolicyPayRecEncoder;
import com.sp.indiv.ci.interf.RenewWarrantNoDecoder;
import com.sp.indiv.ci.interf.RenewWarrantNoEncoder;
import com.sp.payment.blsvr.jf.BLPrpJPayBank;
import com.sp.payment.blsvr.jf.BLPrpJPayBankPre;
import com.sp.payment.dbsvr.jf.DBCmsInterface001;
import com.sp.payment.dbsvr.jf.DBCmsInterface003;
import com.sp.payment.dbsvr.jf.DBPrpJPayBank;
import com.sp.payment.dbsvr.jf.DBPrpJPayBankPre;
import com.sp.payment.dbsvr.jf.DBPrpJplanFeePreTrace;
import com.sp.payment.schema.PrpJPayBankPreSchema;
import com.sp.payment.schema.PrpJPayBankSchema;
import com.sp.payment.utility.ErrorLoger;
import com.sp.payment.utility.PaymentConfig;
import com.sp.platform.dto.domain.PrpDriskConfigDto;
import com.sp.platform.ui.model.PrpDriskConfigFindByConditionsCommand;
import com.sp.prpall.blsvr.tb.BLPrpTbatch;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFeePre;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaInfo;
import com.sp.prpall.dbsvr.jf.DBPrpJtransPolicy;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;
import com.sp.prpall.dbsvr.tb.DBPrpTmain;
import com.sp.prpall.dbsvr.tb.DBPrpTmainSub;
import com.sp.prpall.schema.PrpJpayRefVoucherSchema;
import com.sp.prpall.schema.PrpJplanFeePreSchema;
import com.sp.prpall.schema.PrpJpoaInfoSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.dbsvr.DBPrpDcompany;
import com.sp.utiall.dbsvr.DBPrpDrisk;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;
import com.sp.visa.ui.control.action.UIVsAccountInfoAction;

/**
 * 见费出单预收款确认的BL类
 * <p>Copyright: Copyright (c) 2007</p>
 * <p> @createdate 2007-11-30</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJpreFeeCharge {
    protected final Log logger = LogFactory.getLog(getClass());
	/**
	 * 构造函数
	 */
	public BLPrpJpreFeeCharge() {
	}


    /**
     * @desc 见费出单预收款确认完成
     * @author liufengyao
     * @param iCertiType
     * @param iCertiNo
     * @param iSerialNo
     * @param poaType 预收款方式
     * @param iPprpjpoaInfoSchema
     * @return
     * @throws Exception
     */
    public ArrayList preFeeChargeConfirm(String[] iCertiType,String[] iCertiNo,String[] iSerialNo,
    		                            String poaType,PrpJpoaInfoSchema iPprpjpoaInfoSchema)
    throws Exception{
    	ArrayList policyNoList = null;
	    
    	preFeeChargeConfirmData(iCertiType,iCertiNo,iSerialNo,poaType,iPprpjpoaInfoSchema);
    	




    	return policyNoList;
    }
    /**
     * @desc 支票到账确认调用函数
     * @param iPoaCode
     * @return
     * @throws Exception
     */
    public ArrayList preFeeChargeBillConfirm(String[] iPoaCode) 
    throws Exception {
		ArrayList policyNoList = null;
		BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
		
		blPrpJplanFeePre = preFeeChargeBillConfirmData(iPoaCode);		
		String[] certiType = new String[blPrpJplanFeePre.getSize()];
		String[] certiNo = new String[blPrpJplanFeePre.getSize()];
		String[] serialNo = new String[blPrpJplanFeePre.getSize()];
		for (int j = 0; j < blPrpJplanFeePre.getSize(); j++) {
			certiType[j] = blPrpJplanFeePre.getArr(j).getCertiType();
			certiNo[j] = blPrpJplanFeePre.getArr(j).getCertiNo();
			serialNo[j] = blPrpJplanFeePre.getArr(j).getSerialNo();
		}
			
		
		if (!"HX-JHK".equals(blPrpJplanFeePre.getArr(0).getVSCardFlag())) {
			
			policyNoList = createPolicyByServelt(certiType, certiNo, serialNo);
		}
		
		return policyNoList;
	}

    /**
     * @desc 支票到账确认，生成预收款凭证，调用业务提供servlet，生成XX或批单数据
	 * @param iPoaCode
	 * @param prpJpayRefVoucherSchema 
	 * @param blPrpJpayRefDetail
	 * @param voucherNo[out] 生成的凭证号
	 * @return
	 * @throws Exception
	 */
    public ArrayList preFeeChargeBillConfirm(String[] iPoaCode,PrpJpayRefVoucherSchema prpJpayRefVoucherSchema, 
    		BLPrpJpayRefDetail blPrpJpayRefDetail, StringBuffer voucherNo) 
    throws Exception {
		ArrayList policyNoList = null;
		BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
		
		blPrpJplanFeePre = preFeeChargeBillConfirmData(iPoaCode, prpJpayRefVoucherSchema, blPrpJpayRefDetail, voucherNo);		
		String[] certiType = new String[blPrpJplanFeePre.getSize()];
		String[] certiNo = new String[blPrpJplanFeePre.getSize()];
		String[] serialNo = new String[blPrpJplanFeePre.getSize()];
		for(int j=0;j<blPrpJplanFeePre.getSize();j++){
			certiType[j] = blPrpJplanFeePre.getArr(j).getCertiType();
			certiNo[j] = blPrpJplanFeePre.getArr(j).getCertiNo();
			serialNo[j] = blPrpJplanFeePre.getArr(j).getSerialNo();
		}
		
		if (!"HX-JHK".equals(blPrpJplanFeePre.getArr(0).getVSCardFlag())) {
			
			policyNoList = createPolicyByServelt(certiType, certiNo, serialNo);
		}
		
		
		
		return policyNoList;
	}
    
    
    /**
     * @desc  现金到账确认，生成预收款凭证，调用业务提供servlet，生成XX或批单数据
	 * @param iPoaCode
	 * @param prpJpayRefVoucherSchema 
	 * @param blPrpJpayRefDetail
	 * @param voucherNo[out] 生成的凭证号
	 * @return
	 * @throws Exception
	 */
    public ArrayList preFeeChargeCashConfirm(String[] iPoaCode,PrpJpayRefVoucherSchema prpJpayRefVoucherSchema, 
    		BLPrpJpayRefDetail blPrpJpayRefDetail, StringBuffer voucherNo) 
    throws Exception {
		ArrayList policyNoList = null;
		BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
		
		blPrpJplanFeePre = preFeeChargeCashConfirmData(iPoaCode, prpJpayRefVoucherSchema, blPrpJpayRefDetail, voucherNo);		
		String[] certiType = new String[blPrpJplanFeePre.getSize()];
		String[] certiNo = new String[blPrpJplanFeePre.getSize()];
		String[] serialNo = new String[blPrpJplanFeePre.getSize()];
		for(int j=0;j<blPrpJplanFeePre.getSize();j++){
			certiType[j] = blPrpJplanFeePre.getArr(j).getCertiType();
			certiNo[j] = blPrpJplanFeePre.getArr(j).getCertiNo();
			serialNo[j] = blPrpJplanFeePre.getArr(j).getSerialNo();
		}
		if (!"HX-JHK".equals(blPrpJplanFeePre.getArr(0).getVSCardFlag())) {
			
			policyNoList = createPolicyByServelt(certiType, certiNo, serialNo);
		}
		return policyNoList;
	}
    /**
     * @desc 支票到账确认,回写预收款PrpJplanFeePre、PrpJpoaInfo表信息及业务表PrpTmain表信息，生成预收款凭证
     * -无dbpool
     * 
     * @param iPoaCode
     * @param prpJpayRefVoucherSchema
     * @param blPrpJpayRefDetail
     * @param voucherNo[out] 生成的凭证号
     * @return
     * @throws Exception
     */
    public BLPrpJplanFeePre preFeeChargeCashConfirmData(String[] iPoaCode, PrpJpayRefVoucherSchema prpJpayRefVoucherSchema, 
    		BLPrpJpayRefDetail blPrpJpayRefDetail, StringBuffer voucherNo)
    throws Exception{
    	DbPool dbpool = new DbPool();
    	DbPool paymentDbpool = null;
    	BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
        
		
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
		
		String prePayment_switch=SysConfig.getProperty("PAYMENT_XYSKPZ_SWITCH");
		if("1".equals(prePayment_switch)){
			paymentDbpool = new DbPool();
			paymentDbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
			paymentDbpool.beginTransaction();
		}
		
    	try {
    		dbpool.beginTransaction();

            
    		blPrpJplanFeePre = preFeeChargeBillConfirmData(dbpool,iPoaCode);
    		
    		
    		String updateSql = "UPDATE PrpJpoaInfo SET RealPayRefNo = ? WHERE PoaCode = ?";
    		dbpool.prepareInnerStatement(updateSql);
    		for (int i = 0; i < iPoaCode.length; i++) {
    			int index = 1;
    			dbpool.setString(index++,prpJpayRefVoucherSchema.getRealPayRefNo());
    			dbpool.setString(index++,iPoaCode[i]);
    			dbpool.executePreparedUpdate();     
    		}
    		dbpool.closePreparedStatement();
    		
    	    BLPrpJPreFeeVoucher blPrpJPreFeeVoucher = new BLPrpJPreFeeVoucher();
    	    
    	    String no="";
    	    if("1".equals(prePayment_switch)){
    	    	 no = blPrpJPreFeeVoucher.genPreFeeVoucher(paymentDbpool,prpJpayRefVoucherSchema,blPrpJpayRefDetail);
    	    	 this.splitVoucherNo(no,iPoaCode,dbpool);
    	    	 
    	    }else{
    	    	no = blPrpJPreFeeVoucher.genPreFeeVoucher(dbpool,prpJpayRefVoucherSchema,blPrpJpayRefDetail);
    	    }
    	    voucherNo.append(no);
    	    if("1".equals(prePayment_switch)){
    	    	paymentDbpool.commitTransaction();
    	    }
    		dbpool.commitTransaction();
    	} catch (Exception e) {
    		if("1".equals(prePayment_switch)){
    	    	paymentDbpool.rollbackTransaction();
    	    }
    		dbpool.rollbackTransaction();
    		throw e;
    	} finally {
    		if(paymentDbpool!=null){
    			paymentDbpool.close();
    		}
    		dbpool.close();
    	} 
		return blPrpJplanFeePre;
    }
    
    /**
     * @desc 支票撤销,修改PrpJplanFeePre,PrpJpoaInfo表信息-无dbpool
     * @author liufengyao
     * @param iPoaCode
     * @throws Exception
     */
    public void preFeeChargeBillCancel(String[] iPoaCode,String userCode)
    throws Exception{
    	DbPool dbpool = new DbPool();
        
        
        
    	
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
    	try {
    		dbpool.beginTransaction();
    		preFeeChargeBillCancel(dbpool,iPoaCode,userCode);
    		dbpool.commitTransaction();
    	} catch (Exception e) {
    		dbpool.rollbackTransaction();
    		throw e;
    	} finally {
    		dbpool.close();
    	} 
    }
    /**
     * @desc 支票撤销,修改PrpJplanFeePre,PrpJpoaInfo表信息-带dbpool
     * @author liufengyao
     * @param dbpool
     * @param iPoaCode
     * @throws Exception
     */
    public void preFeeChargeBillCancel(DbPool dbpool,String[] iPoaCode,String userCode)
    throws Exception{
    	DateTime dateTimeCurrent = new DateTime(new Date(),DateTime.YEAR_TO_SECOND);
		BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
    	DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
		PrpJplanFeePreSchema prpJplanFeePreSchema = null;
		DBPrpTmain dbPrpTmain = null;
		DBPrpTmainSub dbPrpTmainSub = new DBPrpTmainSub();
		DBPrpPhead dbPrpPhead = null;
		String othFlag = "";
		String flag = "";
		char othFlag18 = '1';
		char flag3 = '1';
		
		
    	PrpJpoaInfoSchema prpJpoaInfoSchema = new PrpJpoaInfoSchema();
    	DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
    	prpJpoaInfoSchema.setAccFlag("3");
    	prpJpoaInfoSchema.setWithDrawDate(dateTimeCurrent.toString());
    	prpJpoaInfoSchema.setWithDrawCode(userCode);
    	int[] result = dbPrpJpoaInfo.updateBatchForBillCancel(dbpool, prpJpoaInfoSchema, iPoaCode);
    	if(!analyzeBatchUpdateResults(result, iPoaCode.length)){
    		throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","预取消的支票状态已经改变，请刷新页面后再次提交！");
    	}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(" PoaCode in (''");
		for(int i=0;i<iPoaCode.length;i++){
				buffer.append(",'").append(iPoaCode[i]).append("'");
		}
		buffer.append(")");
		blPrpJplanFeePre.query(buffer.toString(), 0);
		for(int i=0;i<blPrpJplanFeePre.getSize();i++){
			prpJplanFeePreSchema = blPrpJplanFeePre.getArr(i);
	    	
			dbPrpJplanFeePre.setSchema(prpJplanFeePreSchema);
			dbPrpJplanFeePre.setCertiStatus("0");
			dbPrpJplanFeePre.setOperateDate(dateTimeCurrent.toString());
			dbPrpJplanFeePre.update(dbpool);
			if(prpJplanFeePreSchema.getCertiType().equals("T")){
				
	    		dbPrpTmain = new DBPrpTmain();
	    		dbPrpTmain.getInfo(dbpool, prpJplanFeePreSchema.getCertiNo());
	    		othFlag = dbPrpTmain.getOthFlag();
	    		dbPrpTmain.setOthFlag(convertOthFlag(othFlag,othFlag18));
                
                dbPrpJplanFeePre.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
                
                
	    		
        		if("4".equals(dbPrpTmain.getPolicySort())){
        			int infoFlag = dbPrpTmainSub.getInfo(dbPrpTmain.getProposalNo());
        			if(infoFlag!=100){
        				dbPrpTmain = new DBPrpTmain();
        				dbPrpTmain.getInfo(dbpool, dbPrpTmainSub.getMainPolicyNo());
                		othFlag = dbPrpTmain.getOthFlag();
                		dbPrpTmain.setOthFlag(convertOthFlag(othFlag,othFlag18));
                        
                        dbPrpJplanFeePre.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
                        
                        
        			}else{
        				throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","30XXXXX组合产品大XX单不存在！");
        			}
        		}
        		
			}else if(prpJplanFeePreSchema.getCertiType().equals("E")){
				
    			dbPrpPhead = new DBPrpPhead();
    			dbPrpPhead.getInfo(dbpool,prpJplanFeePreSchema.getCertiNo());
    			flag = dbPrpPhead.getFlag();
    			dbPrpPhead.setFlag(convertFlag(flag,flag3));
    			
    			dbPrpJplanFeePre.updatePrpPheadForConfirm(dbpool ,dbPrpPhead);
    			
    			
			}else{
    			throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","错误的业务类型！");
			}
		}
    	
    }
    /**
     * @desc 支票到账确认,修改PrpJplanFeePre,PrpJpoaInfo表信息-无dbpool
     * @author liufengyao
     * @param iPoaCode
     * @throws Exception
     */
    public BLPrpJplanFeePre preFeeChargeBillConfirmData(String[] iPoaCode)
    throws Exception{
    	DbPool dbpool = new DbPool();
    	BLPrpJplanFeePre blPrpJplanFeePre = null;
        
        
        
    	
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
    	try {
    		dbpool.beginTransaction();
    		blPrpJplanFeePre = preFeeChargeBillConfirmData(dbpool,iPoaCode);
    		dbpool.commitTransaction();
    	} catch (Exception e) {
    		e.printStackTrace();
    		dbpool.rollbackTransaction();
    		throw e;
    	} finally {
    		dbpool.close();
    	} 
		return blPrpJplanFeePre;
    }
    /**
     * @desc 支票到账确认,回写预收款PrpJplanFeePre、PrpJpoaInfo表信息及业务表PrpTmain表信息，生成预收款凭证
     * -无dbpool
     * 
     * @param iPoaCode
     * @param prpJpayRefVoucherSchema
     * @param blPrpJpayRefDetail
     * @param voucherNo[out] 生成的凭证号
     * @return
     * @throws Exception
     */
    public BLPrpJplanFeePre preFeeChargeBillConfirmData(String[] iPoaCode, PrpJpayRefVoucherSchema prpJpayRefVoucherSchema, 
    		BLPrpJpayRefDetail blPrpJpayRefDetail, StringBuffer voucherNo)
    throws Exception{
    	DbPool dbpool = new DbPool();
    	
    	DbPool paymentDbpool=null;
    	
    	BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
        
        
        
    	
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
		
		String prePayment_switch=SysConfig.getProperty("PAYMENT_XYSKPZ_SWITCH");
		if("1".equals(prePayment_switch)){
			paymentDbpool=new DbPool();
			paymentDbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
			paymentDbpool.beginTransaction();
		}
		
    	try {
    		dbpool.beginTransaction();
    		
    		
    		DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
        	String sql = "select * from PrpJpoaInfo where poacode=? for update nowait";
    		if ( iPoaCode.length != 1 ){
    			throw new UserException(-96, -1167, "BLPrpJplanFeePre.preFeeChargeBillConfirmData()", 
    					"不能同时对2个或2个以上的支票、划账到账确认处理");
    		}
        	List prpJpoaInfos = dbPrpJpoaInfo.findByConditions(dbpool, sql, iPoaCode );
        	for (Iterator it = prpJpoaInfos.iterator(); it.hasNext();) {
        		PrpJpoaInfoSchema prpJpoaInfoSchema = (PrpJpoaInfoSchema)it.next();
        		if (!prpJpoaInfoSchema.getAccFlag().equals("0")){
        			throw new UserException(-96, -1167, "BLPrpJplanFeePre.preFeeChargeBillConfirmData()", 
        					iPoaCode[0] + "到账确认失败");
        		}
        	}

            
    		blPrpJplanFeePre = preFeeChargeBillConfirmData(dbpool,iPoaCode);
    		
    		
    		String updateSql = "UPDATE PrpJpoaInfo SET RealPayRefNo = ? WHERE PoaCode = ?";
    		dbpool.prepareInnerStatement(updateSql);
    		for (int i = 0; i < iPoaCode.length; i++) {
    			int index = 1;
    			dbpool.setString(index++,prpJpayRefVoucherSchema.getRealPayRefNo());
    			dbpool.setString(index++,iPoaCode[i]);
    			dbpool.executePreparedUpdate();     
    		}
    		dbpool.closePreparedStatement();
    		
    		
    	    BLPrpJPreFeeVoucher blPrpJPreFeeVoucher = new BLPrpJPreFeeVoucher();
    	    String no="";
    	    if("1".equals(prePayment_switch)){
   	    	 	no = blPrpJPreFeeVoucher.genPreFeeVoucher(paymentDbpool,prpJpayRefVoucherSchema,blPrpJpayRefDetail);
   	    	 	this.splitVoucherNo(no,iPoaCode,dbpool);
    	    }else{
    	    	no = blPrpJPreFeeVoucher.genPreFeeVoucher(dbpool,prpJpayRefVoucherSchema,blPrpJpayRefDetail);
    	    }
    	    voucherNo.append(no);
    	    if("1".equals(prePayment_switch)){
    	    	paymentDbpool.commitTransaction();
    	    }
    		dbpool.commitTransaction();
    	} catch (Exception e) {
    		if("1".equals(prePayment_switch)){
    			paymentDbpool.rollbackTransaction();
    		}
    		dbpool.rollbackTransaction();
    		throw e;
    	} finally {
    		if(paymentDbpool!=null){
    			paymentDbpool.close();
    		}
    		dbpool.close();
    	} 
		return blPrpJplanFeePre;
    }
    /**
     * @desc 支票到账确认,修改PrpJplanFeePre,PrpJpoaInfo表信息-带dbpool
     * @author liufengyao
     * @param dbpool
     * @param iPoaCode
     * @throws Exception
     */
    public BLPrpJplanFeePre preFeeChargeBillConfirmData(DbPool dbpool,String[] iPoaCode)
    throws Exception{
    	DateTime dateTimeCurrent = new DateTime(new Date(),DateTime.YEAR_TO_SECOND);
		BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
    	DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
		PrpJplanFeePreSchema prpJplanFeePreSchema = null;
		DBPrpTmain dbPrpTmain = null;
		DBPrpTmainSub dbPrpTmainSub = new DBPrpTmainSub();
		DBPrpPhead dbPrpPhead = null;
		String othFlag = "";
		String flag = "";
		char othFlag18 = '3';
		char flag3 = '3';
		
		boolean flagF = false;
		boolean flagJHK = false;
		
		
    	PrpJpoaInfoSchema prpJpoaInfoSchema = new PrpJpoaInfoSchema();
    	DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
    	prpJpoaInfoSchema.setAccFlag("1");
    	prpJpoaInfoSchema.setAccDate(dateTimeCurrent.toString());
    	int[] result = dbPrpJpoaInfo.updateBatchForBillConfirm(dbpool, prpJpoaInfoSchema, iPoaCode);
    	if(!analyzeBatchUpdateResults(result, iPoaCode.length)){
    		throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","预确认到账的支票状态已经改变，请刷新页面后再次提交！");
    	}
		
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(" PoaCode in (''");
		for(int i=0;i<iPoaCode.length;i++){
				buffer.append(",'").append(iPoaCode[i]).append("'");
		}
		buffer.append(")");
		blPrpJplanFeePre.query(buffer.toString(), 0);
		for(int i=0;i<blPrpJplanFeePre.getSize();i++){
			prpJplanFeePreSchema = blPrpJplanFeePre.getArr(i);
	    	
			dbPrpJplanFeePre.setSchema(prpJplanFeePreSchema);
			dbPrpJplanFeePre.setCertiStatus("2");
			dbPrpJplanFeePre.setOperateDate(dateTimeCurrent.toString());
			dbPrpJplanFeePre.update(dbpool);
			
			if(prpJplanFeePreSchema.getCertiType().equals("T")){
	    		dbPrpTmain = new DBPrpTmain();
	    		dbPrpTmain.getInfo(dbpool, prpJplanFeePreSchema.getCertiNo());
	    		othFlag = dbPrpTmain.getOthFlag();
	    		dbPrpTmain.setOthFlag(convertOthFlag(othFlag,othFlag18));
                
                dbPrpJplanFeePre.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
                
                
	    		
        		if("4".equals(dbPrpTmain.getPolicySort())){
        			int infoFlag = dbPrpTmainSub.getInfo(dbPrpTmain.getProposalNo());
        			if(infoFlag!=100){
        				dbPrpTmain = new DBPrpTmain();
        				dbPrpTmain.getInfo(dbpool, dbPrpTmainSub.getMainPolicyNo());
                		othFlag = dbPrpTmain.getOthFlag();
                		dbPrpTmain.setOthFlag(convertOthFlag(othFlag,othFlag18));
                        
                        dbPrpJplanFeePre.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
                        
                        
        			}else{
        				throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","30XXXXX组合产品大XX单不存在！");
        			}
        		}
        		
        		
        		if("HX-JHK".equals(prpJplanFeePreSchema.getVSCardFlag())){
        			flagJHK = true;
        			blPrpJplanFeePre.getArr(i).setOperateDate(dateTimeCurrent.toString());
        		}else{
        			flagF = true;
        		}
        		if(flagJHK && flagF){
        			throw new UserException(-98, -1003,"BLPrpJpreFeeCharge.java","激活卡业务与非激活卡业务不允许合并预收款到账确认 ");
        		}
        		
			}else if(prpJplanFeePreSchema.getCertiType().equals("E")){
				
    			dbPrpPhead = new DBPrpPhead();
    			dbPrpPhead.getInfo(dbpool,prpJplanFeePreSchema.getCertiNo());
    			flag = dbPrpPhead.getFlag();
    			dbPrpPhead.setFlag(convertFlag(flag,flag3));
    			
    			dbPrpJplanFeePre.updatePrpPheadForConfirm(dbpool ,dbPrpPhead);
    			
    			
			}else{
    			throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","错误的业务类型！");
			}
		}
		
		
		if(flagJHK){
			this.recevieeAccountInfoForVS(blPrpJplanFeePre);			
		}
    	
    	return blPrpJplanFeePre;
    }
    
    /**
     * 验证参数中poaCodes数组指定的收据到账状态是否均为0.
     * 
     * @param dbPool
     * @param poaCodes
     * @return 不是返回
     * @throws Exception
     */
    public boolean validateForPoaInfo(DbPool dbPool, String[] poaCodes) throws Exception{
    	StringBuffer sb = new StringBuffer(100);
    	sb.append(" AccFlag = '0' ");
    	sb.append(" PoaCode in (''");
		for (int i = 0; i < poaCodes.length; i++) {
			sb.append(",'").append(poaCodes[i]).append("'");
		}
		sb.append(") ");
    	DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
    	return poaCodes.length == dbPrpJpoaInfo.getCount(dbPool, sb.toString());
    }
    
	/**
	 * @desc 预收款确认,回写预收款PrpJplanFeePre、PrpJpoaInfo表信息及业务表PrpTmain表信息-无dbpool
	 * @author liufengyao
	 * @param iCertiType
	 * @param iCertiNo
	 * @param iSerialNo
	 * @param poaType
	 * @param iPprpjpoaInfoSchema
	 * @throws Exception
	 */
    public void preFeeChargeConfirmData(String[] iCertiType,String[] iCertiNo,String[] iSerialNo,
            String poaType,PrpJpoaInfoSchema iPprpjpoaInfoSchema)
    throws Exception{
    	DbPool dbpool = new DbPool();
        
        
        
    	
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
    	try {
    		dbpool.beginTransaction();
    		preFeeChargeConfirmData(dbpool,iCertiType,iCertiNo,iSerialNo,poaType,iPprpjpoaInfoSchema);
    		dbpool.commitTransaction();
    	} catch (Exception e) {
    		dbpool.rollbackTransaction();
    		throw e;
    	} finally {
    		dbpool.close();
    	} 
    }
	/**
	 * @desc 预收款确认,回写预收款PrpJplanFeePre、PrpJpoaInfo表信息及业务表PrpTmain表信息-带dbpool
	 * @author liufengyao
	 * @param iCertiType
	 * @param iCertiNo
	 * @param iSerialNo
	 * @param poaType
	 * @param iPprpjpoaInfoSchema
	 * @throws Exception
	 */
    public void preFeeChargeConfirmData(DbPool dbpool,String[] iCertiType,String[] iCertiNo,String[] iSerialNo,
            String poaType,PrpJpoaInfoSchema iPprpjpoaInfoSchema)
    throws Exception{
    	try{
    		DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
        	DBPrpTmain dbPrpTmain = null;
        	DBPrpPhead dbPrpPhead = null;
        	DBPrpTmainSub dbPrpTmainSub = new DBPrpTmainSub();
        	DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
        	PrpJplanFeePreSchema prpJplanFeePreSchema = new PrpJplanFeePreSchema();
        	String othFlag = "";
        	String flag = "";
        	char othFlag18 ;
        	char flag3;
        	String poaCode = iPprpjpoaInfoSchema.getPoaCode();
        	String operateDate = iPprpjpoaInfoSchema.getOpenDate();
        	String accFlag = iPprpjpoaInfoSchema.getAccFlag();
        	
        	String stampTax = iPprpjpoaInfoSchema.getRemark();
        	String certiStatus="0";
        	boolean vsCardFlag = false;
        	boolean posFlag = false;
        	boolean vsAccFlag = false;
        	if(accFlag.equals("0")){
        		
        		certiStatus = "1";
        		othFlag18 = '2';
        		flag3 = '2';
        	}
        	else if(accFlag.equals("1")){
        		
        		certiStatus = "2";
        		othFlag18 = '3';
        		flag3 = '3';
        		vsAccFlag = true;
        	}
        	else
        		throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","支票/pos信息中AccFlag设置错误！");
        	
        	
        	prpJplanFeePreSchema.setCertiStatus(certiStatus);
        	prpJplanFeePreSchema.setOperateDate(operateDate);
        	prpJplanFeePreSchema.setPoaType(poaType);
        	prpJplanFeePreSchema.setPoaCode(poaCode);
        	prpJplanFeePreSchema.setPrePayRefFee(stampTax);
            
            
            if(poaType.equals("2") ||poaType.equals("O"))
            {
            	posFlag = true;
                for (int i=0;i<iCertiNo.length;i++)
                {
                    dbPrpJplanFeePre.getInfo(dbpool,iCertiNo[i],iSerialNo[i]);
                    if("HX-JHK".equals(dbPrpJplanFeePre.getVSCardFlag())){
                    	vsCardFlag = true;
                    }
                    if(!dbPrpJplanFeePre.getCertiStatus().equals("1"))
                        throw new Exception("准备操作的业务状态不正确，请重新查询后再处理");
                }
            }
            else
            {
                for (int i=0;i<iCertiNo.length;i++)
                {
                    dbPrpJplanFeePre.getInfo(dbpool,iCertiNo[i],iSerialNo[i]);
                    if(!dbPrpJplanFeePre.getCertiStatus().equals("0"))
                        throw new Exception("准备操作的业务状态不正确，请重新查询后再处理");
                }
            }
           
        	int[] result = dbPrpJplanFeePre.updateBatchForConfirm(dbpool,prpJplanFeePreSchema,iCertiNo,iSerialNo);
        	if(!analyzeBatchUpdateResults(result, iCertiNo.length)){
        		throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","准备预收的业务状态已改变，请刷新页面后再次提交！");
        	}
        	
        	dbPrpJpoaInfo.setSchema(iPprpjpoaInfoSchema);
        	if(iPprpjpoaInfoSchema.getPoaType().equals("2") ||iPprpjpoaInfoSchema.getPoaType().equals("O") )
        	/*MODIFY BY PENGJINLING 2013-5-3 PAYMENT-608 关于使用易宝支付垫款实现网电业务一次配送的申请 BEGIN*/
        	{
        		dbPrpJpoaInfo.update(dbpool);
        		
        		if(dbPrpJpoaInfo.getPOSModelNo() != null && "93".equals(dbPrpJpoaInfo.getPOSModelNo())){
        			if("1".equals(dbPrpJpoaInfo.getAccFlag())){
        				this.insertCMSDFData(dbpool,dbPrpJpoaInfo,iCertiNo,iSerialNo);
        			}
        		}
        	}
        	/*MODIFY BY PENGJINLING 2013-5-3 PAYMENT-608 关于使用易宝支付垫款实现网电业务一次配送的申请 END*/
        	else
        		dbPrpJpoaInfo.insert(dbpool);
        	
        	
        	
        	
        	for(int i=0;i<iCertiNo.length;i++){
        		if(iCertiType[i].equals("T")){
            		dbPrpTmain = new DBPrpTmain();
            		dbPrpTmain.getInfo(dbpool, iCertiNo[i]);
            		othFlag = dbPrpTmain.getOthFlag();
            		dbPrpTmain.setOthFlag(convertOthFlag(othFlag,othFlag18));
                    
                    dbPrpJplanFeePre.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
                    
                    
            		
            		if("4".equals(dbPrpTmain.getPolicySort())){
            			int infoFlag = dbPrpTmainSub.getInfo(dbPrpTmain.getProposalNo());
            			if(infoFlag!=100){
            				dbPrpTmain = new DBPrpTmain();
            				dbPrpTmain.getInfo(dbpool, dbPrpTmainSub.getMainPolicyNo());
                    		othFlag = dbPrpTmain.getOthFlag();
                    		dbPrpTmain.setOthFlag(convertOthFlag(othFlag,othFlag18));
                            
                            dbPrpJplanFeePre.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
                            
                            
            			}else{
            				throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","30XXXXX组合产品大XX单不存在！");
            			}
            		}
            		
        		}else if(iCertiType[i].equals("E")){
        			dbPrpPhead = new DBPrpPhead();
        			dbPrpPhead.getInfo(dbpool,iCertiNo[i]);
        			
        			flag = dbPrpPhead.getFlag();
        			dbPrpPhead.setFlag(convertFlag(flag,flag3));
        			
        			
        			dbPrpJplanFeePre.updatePrpPheadForConfirm(dbpool ,dbPrpPhead);
        			
        		}else
        			throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","错误的业务类型！");
        	}  	
        	
        	if(posFlag&&vsCardFlag&&vsAccFlag){
        		BLPrpJplanFeePre  blPrpJplanFeePre = new BLPrpJplanFeePre();
        		blPrpJplanFeePre.query(" PoaCode = '" + poaCode+ "' ", 0);
        		this.recevieeAccountInfoForVS(blPrpJplanFeePre);
        		
        	}
        	
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    	}
    	
    }

    /*ADD BY PENGJINLING 2013-5-3 PAYMENT-608 关于使用易宝支付垫款实现网电业务一次配送的申请 BEGIN*/
	/**
	 * @desc 插入资金接口表垫付数据-带dbpool
	 * @author pengjinling
	 * @param dbpool
	 * @param dbPrpJpoaInfo
	 * @throws Exception
	 */
    public void insertCMSDFData(DbPool dbpool,DBPrpJpoaInfo dbPrpJpoaInfo,String[] iCertiNo,String[] iSerialNo) throws Exception{
		DateTime sysDate = new DateTime(new Date(),DateTime.YEAR_TO_SECOND);
		DBCmsInterface003 dbCmsInterface003 = new DBCmsInterface003();
		dbCmsInterface003.setPAY_ID(dbPrpJpoaInfo.getPoaCode());
		dbCmsInterface003.setSOURCE("SFF");
		dbCmsInterface003.setBUSINESS_COM("P");
		dbCmsInterface003.setBUSINESS_TYPE("PL");
		dbCmsInterface003.setEX_TYPE("D");
		dbCmsInterface003.setEX_MODE_TYPE("PUR");
		dbCmsInterface003.setEX_MODE("14");
		dbCmsInterface003.setEX_STATUS("I");
		dbCmsInterface003.setCREATION_DATE(sysDate.toString());
		dbCmsInterface003.setDISHONOR_DATE(dbPrpJpoaInfo.getAccDate());
		dbCmsInterface003.setAREA_CODE("未知");
		dbCmsInterface003.setBANK_CODE("未知");
		dbCmsInterface003.setCUSTOMER_TYPE("未知");
		dbCmsInterface003.setBANK_NAME("未知");
		dbCmsInterface003.setACCOUNT_TYPE("99");
		dbCmsInterface003.setACCOUNT("未知");
		dbCmsInterface003.setACCOUNT_NAME("未知");
		dbCmsInterface003.setACCOUNT_ATTRIBUTE3("未知");
		dbCmsInterface003.setID_TYPE("un");
		dbCmsInterface003.setID_NO("未知");
		dbCmsInterface003.setCURRENCY("CNY");
		dbCmsInterface003.setAMOUNT(dbPrpJpoaInfo.getTotalFee());
		dbCmsInterface003.setBUSINESS_NO(iCertiNo[0]);
		dbCmsInterface003.setRCPTNO(iCertiNo[0]);		
		
        
        String certinoList = ""; 
		for(int i=0;i<iCertiNo.length;i++){ 
		   if(i == iCertiNo.length-1){ 
		      certinoList += iCertiNo[i]; 
		   }else{ 
		      certinoList += iCertiNo[i] + ","; 
		   } 
		} 
		dbCmsInterface003.setAPPLY_NO(certinoList); 
		

		
		DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
		dbPrpDcompany.getInfo(dbPrpJpoaInfo.getCenterCode());
		String ACNTUNIT = dbPrpDcompany.getAcntUnit();
		dbCmsInterface003.setBUSINESS_ORG(ACNTUNIT);
		dbCmsInterface003.setDRAW_BATCH_NO("0");
		
		DBPrpJplanFeePre dbPrpJplanFeePreRisk= new DBPrpJplanFeePre();
		dbPrpJplanFeePreRisk.getInfo(dbpool,iCertiNo[0],iSerialNo[0]);
		DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
		dbPrpDrisk.getInfo(dbPrpJplanFeePreRisk.getRiskCode());
		String remark = dbPrpDrisk.getRiskCName();
		dbCmsInterface003.setREMARK(remark);
		dbCmsInterface003.setATTRIBUTE7(dbPrpJpoaInfo.getComCode());
		dbCmsInterface003.setATTRIBUTE19("0");
		dbCmsInterface003.setCASH_FLOW("0");
		
		String strSQL = " Insert Into CMS_INTERFACE_003( PAY_ID, SOURCE, BUSINESS_COM, BUSINESS_TYPE, EX_TYPE, " +
				"EX_MODE_TYPE, EX_MODE, EX_STATUS, CREATION_DATE, DISHONOR_DATE," +
				"AREA_CODE, BANK_CODE, CUSTOMER_TYPE, BANK_NAME, ACCOUNT_TYPE, " +
				"ACCOUNT, ACCOUNT_NAME, ACCOUNT_ATTRIBUTE3, ID_TYPE, ID_NO, " +
				"CURRENCY, AMOUNT, BUSINESS_NO, RCPTNO, APPLY_NO, " +
				"BUSINESS_ORG, DRAW_BATCH_NO, REMARK, ATTRIBUTE19, ATTRIBUTE7,CASH_FLOW ) " +
				"values(?,?,?,?,?,?,?,?,to_date(?,'yyyy-MM-dd hh24:mi:ss'),to_date(?,'yyyy-MM-dd hh24:mi:ss')," +
				"?,?,?,?,?,?,?,?,?,?," +
				"?,?,?,?,?,?,?,?,?,?,?)";

		dbpool.prepareInnerStatement(strSQL);
		int index = 1;
		dbpool.setString(index++, dbCmsInterface003.getPAY_ID());
		dbpool.setString(index++, dbCmsInterface003.getSOURCE());
		dbpool.setString(index++, dbCmsInterface003.getBUSINESS_COM());
		dbpool.setString(index++, dbCmsInterface003.getBUSINESS_TYPE());
		dbpool.setString(index++, dbCmsInterface003.getEX_TYPE());
		dbpool.setString(index++, dbCmsInterface003.getEX_MODE_TYPE());
		dbpool.setString(index++, dbCmsInterface003.getEX_MODE());
		dbpool.setString(index++, dbCmsInterface003.getEX_STATUS());
		dbpool.setString(index++, dbCmsInterface003.getCREATION_DATE());
		dbpool.setString(index++, dbCmsInterface003.getDISHONOR_DATE());
		dbpool.setString(index++, dbCmsInterface003.getAREA_CODE());
		dbpool.setString(index++, dbCmsInterface003.getBANK_CODE());
		dbpool.setString(index++, dbCmsInterface003.getCUSTOMER_TYPE());
		dbpool.setString(index++, dbCmsInterface003.getBANK_NAME());
		dbpool.setString(index++, dbCmsInterface003.getACCOUNT_TYPE());
		dbpool.setString(index++, dbCmsInterface003.getACCOUNT());
		dbpool.setString(index++, dbCmsInterface003.getACCOUNT_NAME());
		dbpool.setString(index++, dbCmsInterface003.getACCOUNT_ATTRIBUTE3());
		dbpool.setString(index++, dbCmsInterface003.getID_TYPE());
		dbpool.setString(index++, dbCmsInterface003.getID_NO());
		dbpool.setString(index++, dbCmsInterface003.getCURRENCY());
		dbpool.setString(index++, dbCmsInterface003.getAMOUNT());
		dbpool.setString(index++, dbCmsInterface003.getBUSINESS_NO());
		dbpool.setString(index++, dbCmsInterface003.getRCPTNO());
		dbpool.setString(index++, dbCmsInterface003.getAPPLY_NO());
		dbpool.setString(index++, dbCmsInterface003.getBUSINESS_ORG());
		dbpool.setString(index++, dbCmsInterface003.getDRAW_BATCH_NO());
		dbpool.setString(index++, dbCmsInterface003.getREMARK());
		dbpool.setString(index++, dbCmsInterface003.getATTRIBUTE19());
		dbpool.setString(index++, dbCmsInterface003.getATTRIBUTE7());
		dbpool.setString(index++, dbCmsInterface003.getCASH_FLOW());
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
    }
    /*ADD BY PENGJINLING 2013-5-3 PAYMENT-608 关于使用易宝支付垫款实现网电业务一次配送的申请 END*/
    
    /**
    * @desc 将PrpTmain的Othflag的第18位进行赋值
    * @author liufengyao
    * @param othFlag
    * @param othFlag18
    * @return
    */
    public String convertOthFlag(String othFlag,char othFlag18) {
		String newOthFlag = "";
		StringBuffer strBuffer = new StringBuffer(20);
		strBuffer.append(Str.newString(" ", 20));
		strBuffer.replace(0, othFlag.length(), othFlag);
		strBuffer.setCharAt(17, othFlag18);
		newOthFlag = strBuffer.toString();
		return newOthFlag;
	}
    /**
     * @desc 将PrpPhead的flag的第3位进行赋值
     * @author liufengyao
     * @param flag
     * @param flag3
     * @return
     */
     public String convertFlag(String flag,char flag3) {
 		String newFlag = "";
 		StringBuffer strBuffer = new StringBuffer(10);
 		strBuffer.append(Str.newString(" ", 10));
 		strBuffer.replace(0, flag.length(), flag);
 		strBuffer.setCharAt(2, flag3);
 		newFlag = strBuffer.toString();
 		return newFlag;
 	}
    /**
     * @desc 调用业务提供servlet，生成XX或批单数据
     * @author liufengyao
     * @param iCertiType
     * @param iCertiNo
     * @param iSerialNo
     * @return 生成的XX号集合和未生成XX的XX单号的集合
     * @throws Exception
     */
    public ArrayList createPolicyByServelt(String[] iCertiType,String[] iCertiNo,String[] iSerialNo)
    throws Exception{
    	ArrayList policyNoList = new ArrayList();
    	ArrayList errorProposalList = new ArrayList();
    	ArrayList allList = new ArrayList();
    	
    	
    	
    	
    	
    	String BASE_URL = "";
    	String switchFlag =  SysConfig.getProperty("GENERATEPOLICY_URL_SWITCH");
    	String switchRiskCode = SysConfig.getProperty("GENERATEPOLICY_URL_RISKCODE");
    	String strRiskCodeFlag = "0";
    	if("0".equals(switchFlag)){
    		BASE_URL = SysConfig.getProperty("GENERATEPOLICY_URL");
    	}
    	
    	
        InputStream inputStream = null;
        BufferedReader reader = null;
        OutputStream outputStream = null;
        OutputStreamWriter writer = null;
        String businessMsg = "";
        String strMessage = "";
        StringBuffer strBuffer = null;
        
        List iiCertiType = new ArrayList();
        List iiCertiNo = new ArrayList();
        List iiiCertiNo = new ArrayList();
        List iiiCertiType = new ArrayList();
        List iiSerialNo = new ArrayList();
        String strWhere ="proposalno in ('";
        BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
        DBPrpJtransPolicy dbPrpJtransPolicy = new DBPrpJtransPolicy();
        for(int i=0;i<iCertiNo.length;i++){
        	if(iCertiType[i].equals("T")){
        		blPrpJplanFeePre.getData(iCertiNo[i], iSerialNo[i]);
        		if("4".equals(blPrpJplanFeePre.getArr(0).getAttribute3())){
        			if(!iiCertiNo.contains(blPrpJplanFeePre.getArr(0).getContractNo())){
        				iiCertiType.add(iCertiType[i]);
        				iiCertiNo.add(blPrpJplanFeePre.getArr(0).getContractNo());
        			}else{
        				continue;
        			}
        		}else{
        			iiCertiType.add(iCertiType[i]);
            		iiCertiNo.add(iCertiNo[i]);
        		}
        	}else{
        		iiCertiType.add(iCertiType[i]);
        		iiCertiNo.add(iCertiNo[i]);
        	}
        }
        
        boolean isflag = false;
        for(int j=0;j<iiCertiNo.size();j++){
        	String flag = (String)iiCertiNo.get(j);
        	if("T".equals(flag.substring(0,1))){
        	  isflag = true;       	  
        	  if(j>0&&j%900 == 0)
        		 strWhere += (String)iiCertiNo.get(j) +"') or proposalno in ('";
			  else if (j!=iiCertiNo.size()-1)
				 strWhere += (String)iiCertiNo.get(j)+"','";
        	}else{
        		 iiiCertiNo.add(iiCertiNo.get(j));
        		 iiiCertiType.add(iiCertiType.get(j));
        	}
        	if(j==iiCertiNo.size()-1)
       		  strWhere += (String)iiCertiNo.get(j)+"')";
        }
        if(isflag){
          strWhere += " order by riskcode ";
          BLPrpTmain blPrpTmain = new BLPrpTmain();
          blPrpTmain.query(strWhere,0);
          for(int k=0;k<blPrpTmain.getSize();k++){
        	  iiiCertiNo.add(blPrpTmain.getArr(k).getProposalNo());
        	  iiiCertiType.add("T");
          }
        }
        
        
        String iWherePart = "";
        BLPrpJplanFeePre blPre = new BLPrpJplanFeePre();
        for(int j=0;j<iiiCertiNo.size();j++){
        	
        	iWherePart = "certino = '" + iiiCertiNo.get(j)+"'";
        	blPre.query(iWherePart,0);
        	if(blPre.getSize()>0 && (switchRiskCode.indexOf(blPre.getArr(0).getRiskCode())>0)){
        		
        		strRiskCodeFlag = "1";
        		break;
        	}
        }
        if("1".equals(switchFlag)){
        	if("1".equals(strRiskCodeFlag)){
        		BASE_URL = SysConfig.getProperty("GENERATEPOLICY_URL_BAK");
        	}else{
        		BASE_URL = SysConfig.getProperty("GENERATEPOLICY_URL");
        	}
        }
        
        
        DbPool dbpool = new DbPool();
        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    	for(int i=0;i<iiiCertiNo.size();i++){
    	    dbpool.beginTransaction();
    	    int intResult = dbPrpJtransPolicy.getInfo(iiiCertiType.get(i).toString() ,iiiCertiNo.get(i).toString());
    	    DateTime dateTime = new DateTime(new Date(),DateTime.YEAR_TO_SECOND);
    	    String currentDate = dateTime.toString();
    		try{
    			
    			URL uploadServlet = new URL(BASE_URL);
    			HttpURLConnection servletConnection = (HttpURLConnection)uploadServlet.openConnection();
    			
    			servletConnection.setUseCaches(false);
    			servletConnection.setDoOutput(true);
    			servletConnection.setDoInput(true);
    			servletConnection.setRequestMethod("POST");
    			servletConnection.setAllowUserInteraction(true);
    			servletConnection.connect();
    			
    			businessMsg = iiiCertiType.get(i) + "," +iiiCertiNo.get(i);
                outputStream = servletConnection.getOutputStream();
                writer = new OutputStreamWriter(outputStream);
                writer.write(businessMsg);
                writer.flush();
                writer.close();
    			
    			inputStream = servletConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                strBuffer = new StringBuffer();
                while ((strMessage = reader.readLine()) != null) {
                	strBuffer.append(strMessage);
                }
                try {
                    if (strBuffer.length() < 1){
                        errorProposalList.add(iiiCertiNo.get(i));
                        if(intResult == 100){
                            if(iiiCertiType.get(i).toString().equals("E")){
                                dbPrpJtransPolicy.setPolicyNo(iiiCertiNo.get(i).toString().substring(0, iiiCertiNo.get(i).toString().length()-4));  
                            }
                            dbPrpJtransPolicy.setTransPolicyStatus("0");
                            dbPrpJtransPolicy.setCertiNo(iiiCertiNo.get(i).toString());
                            dbPrpJtransPolicy.setCertiType(iiiCertiType.get(i).toString());
                            dbPrpJtransPolicy.setCreateDateTime(currentDate);
                            dbPrpJtransPolicy.setUpdateErrorReason("失败");
                            dbPrpJtransPolicy.insert(dbpool);
                        }else{
                            dbPrpJtransPolicy.setExecuteDateTime(currentDate);
                            dbPrpJtransPolicy.setUpdateErrorReason("失败");
                            dbPrpJtransPolicy.update(dbpool);
                        }
                    }else{
                        if(intResult == 0){
                            if(iiiCertiType.get(i).toString().equals("T")){
                                dbPrpJtransPolicy.setPolicyNo(strBuffer.toString());
                            }else if(iiiCertiType.get(i).toString().equals("E")){
                                dbPrpJtransPolicy.setPolicyNo(strBuffer.toString().substring(0, strBuffer.toString().length()-4));
                            }
                            dbPrpJtransPolicy.setTransPolicyStatus("1");
                            dbPrpJtransPolicy.setExecuteDateTime(currentDate);
                            dbPrpJtransPolicy.setUpdateErrorReason("成功");
                        }
                        policyNoList.add(strBuffer.toString());
                        this.logPrintln("生成XXXXX/批单成功，业务号："+strBuffer.toString());
                    } 
                    dbpool.commitTransaction(); 
                } catch (Exception e) {
                    dbpool.rollbackTransaction();
                    e.printStackTrace();
                }
        	}catch (MalformedURLException me) {
        		this.logPrintln("创建非法URL！请与系统管理员联系！");
        		errorProposalList.add(iiiCertiNo.get(i));
    			me.printStackTrace();
    		} catch (Exception e) {
                errorProposalList.add(iiiCertiNo.get(i));
                
                if(intResult == 100){
                    if(iiiCertiType.get(i).toString().equals("E")){
                        dbPrpJtransPolicy.setPolicyNo(iiiCertiNo.get(i).toString().substring(0, iiiCertiNo.get(i).toString().length()-4));  
                    }
                    dbPrpJtransPolicy.setCertiNo(iiiCertiNo.get(i).toString());
                    dbPrpJtransPolicy.setCertiType(iiiCertiType.get(i).toString());
                    dbPrpJtransPolicy.setCreateDateTime(currentDate);
                    dbPrpJtransPolicy.setUpdateErrorReason("servlet调用失败");
                    dbPrpJtransPolicy.setTransPolicyStatus("0");
                    dbPrpJtransPolicy.insert(dbpool);
                }else{
                    dbPrpJtransPolicy.setExecuteDateTime(currentDate);
                    dbPrpJtransPolicy.setUpdateErrorReason("servlet调用失败");
                    dbPrpJtransPolicy.update(dbpool);
                }
                dbpool.commitTransaction(); 
                
        		this.logPrintln("XX/批单"+iiiCertiNo.get(i)+"数据发送，没有返回生成的XX信息！");
    			e.printStackTrace();
    		}
    	}
    	dbpool.close();
    	
    	
    	allList.add(policyNoList);
    	allList.add(errorProposalList);
    	return allList;
    }  
    /**
     * @desc 产生时间序列ID
     * @author liufengyao
     * @param iBillType
     * @param ComCode
     * @return
     */
	public String createPoaCode() {
		Calendar utiCalendar = new GregorianCalendar();
		String calendarTime = Long.toString(utiCalendar.getTime().getTime());
		return calendarTime;
	}
	/**
	 * desc 判断改机构是否使用测试环境进行POS机交易，如果是测试环境，则所有交易金额置为1
	 * @param iComCode
	 * @param iRiskCode
	 * @param iConfigCode
	 * @return
	 * @throws Exception
	 */
	public boolean isTestEnvironment(String iComCode,String iRiskCode,String iConfigCode)
	throws Exception{
		boolean environFlag = false;
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(" ComCode ='").append(iComCode).append("' ");
		strBuffer.append(" AND RiskCode = '").append(iRiskCode).append("' ");
		strBuffer.append(" AND ConfigCode = '").append(iConfigCode).append("' ");
		PrpDriskConfigFindByConditionsCommand command = new PrpDriskConfigFindByConditionsCommand(strBuffer.toString());
		Collection col = (Collection) command.execute();
		Iterator iter = col.iterator();
		PrpDriskConfigDto prpDriskConfigDto = null;
		if (iter.hasNext()) {
			prpDriskConfigDto = (PrpDriskConfigDto) iter.next();
			String configValue = prpDriskConfigDto.getConfigValue();
			if (configValue != null && configValue.equals("1"))
				environFlag = true;
		}
		return environFlag;
	}
    /**
	 * @desc 日志输出
	 * @author liufengyao
	 * @param iLog
	 * @throws Exception
	 */
	public void logPrintln(String iLog) throws Exception {
		ChgDate chgDate = new ChgDate();
		logger.info(chgDate.getCurrentTime("yyyy-MM-dd hh:mm:ss") + "><"+ iLog);
	}
	/**
	 * 主函数
	 * 
	 * @param args 参数列表
	 */
	public static void main(String[] args) {
		
	}
	
	/**
	 * 分析批量更新的结果
	 * 
	 * @param resultArray 批量更新的结果
	 * @param expectCount 期望更新结果数
	 * @return 结果符合期望 ture
	 * 		   不符合期望   false
	 */
	private boolean analyzeBatchUpdateResults(int[] resultArray, int expectCount){
		int count = 0;
		for (int i = 0; i < resultArray.length; i++) {
			count += resultArray[i];
		}
		return expectCount == count;
	}
    /**
     * @desc POS交易撤销,修改PrpJplanFeePre,PrpJpoaInfo表信息-无dbpool
     * @author liufengyao
     * @param iPoaCode
     * @throws Exception
     */
    public void preFeeChargePosCancel(String[] iPoaCode,String userCode,String accFlag)
    throws Exception{
    	DbPool dbpool = new DbPool();
        
        
        
    	
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
    	try {
    		dbpool.beginTransaction();
    		preFeeChargePosCancel(dbpool,iPoaCode,userCode,accFlag);
    		dbpool.commitTransaction();
    	} catch (Exception e) {
    		dbpool.rollbackTransaction();
    		throw e;
    	} finally {
    		dbpool.close();
    	} 
    }
    
    /**
     * @description:POS交易强制到账，修改PrpJplanFeePre,PrpJpoaInfo,PrpTmain,PrpPhead表信息-无dbpool
     * @param iPoaCode
     * @param userCode
     * @param accFlag
     * @throws Exception
     * @author genghaijun-wb
     */
    public void preFeeChargePosForcedAccount(String[] iPoaCode, String userCode, String accFlag) throws Exception {
        DbPool dbpool = new DbPool();
        
        
        
        
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
        try {
            dbpool.beginTransaction();
            preFeeChargePosForcedAccount(dbpool, iPoaCode, userCode, accFlag);
            dbpool.commitTransaction();
        } catch (Exception e) {
            dbpool.rollbackTransaction();
            throw e;
        } finally {
            dbpool.close();
        }
    }
    
    /* ADD BY PENGJINLING 2012-4-25 payment-480 关于快钱无线POS交易撤销功能申请需求 BEGIN */
    /**
     * @desc 快钱无线POS交易撤销,修改PrpJplanFeePre,PrpJpoaInfo,PrpJpaybank,Cms_interface003表信息-无dbpool
     * @author PengJinling
     * @param iPoaCode
     * @throws Exception
     */
    public void preFeeChargeWXPosCancel(String[] iPoaCode,String userCode)
    throws Exception{
    	DbPool dbpool = new DbPool();
    	DbPool paymentDbpool=null;
    	String PAYMENT_XYSKPZ_SWITCH="";
    	try {
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		PAYMENT_XYSKPZ_SWITCH=SysConfig.getProperty("PAYMENT_XYSKPZ_SWITCH");
			if("0".equals(payment_switch)){
				dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
				dbpool.beginTransaction();
			}else{
				dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
				dbpool.beginTransaction();
			}
			if("1".equals(PAYMENT_XYSKPZ_SWITCH)){
				paymentDbpool=new DbPool();
				paymentDbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
				paymentDbpool.beginTransaction();
				splitPreFeeChargeWXPosCancel(paymentDbpool,dbpool,iPoaCode,userCode);
			}else{
				preFeeChargeWXPosCancel(dbpool,iPoaCode,userCode);
			}
    		dbpool.commitTransaction();
    		if("1".equals(PAYMENT_XYSKPZ_SWITCH)){
    			paymentDbpool.commitTransaction();
    		}
    	} catch (Exception e) {
    		if("1".equals(PAYMENT_XYSKPZ_SWITCH)){
    			paymentDbpool.rollbackTransaction();
    		}
    		dbpool.rollbackTransaction();
    		throw e;
    	} finally {
    		dbpool.close();
    		if(paymentDbpool!=null){
    			paymentDbpool.close();
    		}
    	} 
    }    
    
    /**
     * @desc 快钱无线POS交易撤销,修改PrpJplanFeePre,PrpJpoaInfo,PrpJpaybank,Cms_interface003表信息-带dbpool
     * @author PengJinling
     * @param dbpool
     * @param iPoaCode
     * @throws Exception
     */
    public void preFeeChargeWXPosCancel(DbPool dbpool,String[] iPoaCode,String userCode)
    throws Exception{
    	
    	boolean isInit = false;
    	
    	com.sp.payment.schema.PrpJplanFeePreSchema preSchema = null;
    	DBPrpTmain dbPrpTmain = null;
    	DBPrpPhead dbPrpPhead = null;
		DBPrpJPayBank dbPrpJPayBank = null;
		DBPrpJpoaInfo dbPrpJpoaInfo = null;
		com.sp.payment.dbsvr.jf.DBPrpJplanFeePre dbPrpJplanFeePre = null;
		DBPrpJplanFeePre dbPrpJplanFeePreForUpdate = new DBPrpJplanFeePre();
		DBCmsInterface003 dbCmsInterface003 = null;
		DBPrpJplanFeePreTrace dbPrpJplanFeePreTrace = null;
		BLPrpJPayBank blPrpJPayBank = null;
		com.sp.payment.blsvr.jf.BLPrpJplanFeePre blPrpJplanFeePre = new com.sp.payment.blsvr.jf.BLPrpJplanFeePre();
    	
    	for(int i = 0; i < iPoaCode.length; i++){
    		String strPoaCode = iPoaCode[i];
    		
			
			String poaCodeSql = " POACODE = '" + strPoaCode + "'";
			blPrpJplanFeePre.query(poaCodeSql);
			
			
			dbCmsInterface003 = new DBCmsInterface003();
			dbCmsInterface003.getInfo(dbpool,strPoaCode);
			
			
			
			if("I".equals(dbCmsInterface003.getEX_STATUS()) || "A".equals(dbCmsInterface003.getEX_STATUS())){
				for(int k = 0; k < blPrpJplanFeePre.getSize(); k++){
					preSchema = new com.sp.payment.schema.PrpJplanFeePreSchema();
					preSchema = blPrpJplanFeePre.getArr(k);
					if("1".equals(preSchema.getCertiStatus())){
						isInit = true;
					}
					else{
						isInit = false;
						break;
					}
				}
			}			
			
			if(isInit == true){
				
				dbCmsInterface003.setEX_STATUS("D");
				dbCmsInterface003.update(dbpool);
				
	    		
	    		
				for(int k = 0; k < blPrpJplanFeePre.getSize(); k++){
					preSchema = new com.sp.payment.schema.PrpJplanFeePreSchema();
					preSchema = blPrpJplanFeePre.getArr(k);
		    		dbPrpJplanFeePre = new com.sp.payment.dbsvr.jf.DBPrpJplanFeePre();
		    		dbPrpJplanFeePre.setSchema(preSchema);
		    		dbPrpJplanFeePre.setCertiStatus("0");
		    		dbPrpJplanFeePre.setPoaType("");
		    		dbPrpJplanFeePre.setPoaCode("");
		    		dbPrpJplanFeePre.setOperateDate("" + new DateTime(new Date(),DateTime.YEAR_TO_MILLISECOND));
		    		dbPrpJplanFeePre.setOrderId("");
		    		dbPrpJplanFeePre.setPrePayRefFee("0.00");
		    		dbPrpJplanFeePre.update(dbpool);
		    		
		    		
		    		if("T".equals(preSchema.getCertiType())){
		    			String strOthFlag = "";
		    			dbPrpTmain = new DBPrpTmain();
		    			dbPrpTmain.getInfo(dbpool,preSchema.getCertiNo());
		    			strOthFlag = dbPrpTmain.getOthFlag();
		    			dbPrpTmain.setOthFlag(strOthFlag.substring(0, 17) + "1" + strOthFlag.substring(18));
	                    
		    			dbPrpJplanFeePreForUpdate.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
	                    
	                    
		    			
		    			if("4".equals(preSchema.getAttribute3())){
		    				dbPrpTmain.getInfo(dbpool,preSchema.getContractNo());
		    				strOthFlag = dbPrpTmain.getOthFlag();
		        			dbPrpTmain.setOthFlag(strOthFlag.substring(0, 17) + "1" + strOthFlag.substring(18));
		                    
		        			dbPrpJplanFeePreForUpdate.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
		                    
		                    
		    			}
		    		}
		    		else if("E".equals(preSchema.getCertiType())){
		    			String flag = "";
		    			dbPrpPhead = new DBPrpPhead();
		    			dbPrpPhead.getInfo(dbpool,preSchema.getCertiNo());
		    			flag = dbPrpPhead.getFlag();
		    			dbPrpPhead.setFlag(flag.substring(0,2) + "1" + flag.substring(3));
		    			
		    			
		    			dbPrpJplanFeePreForUpdate.updatePrpPheadForConfirm(dbpool ,dbPrpPhead);
		    			
		    		}
		    		
		    		
		    		dbPrpJplanFeePreTrace = new DBPrpJplanFeePreTrace();
		    		dbPrpJplanFeePreTrace.setSchemaWithPre(preSchema);
		    		
		    		String preTraceId = dbPrpJplanFeePreTrace.getSequence(dbpool);
		    		dbPrpJplanFeePreTrace.setPreTraceId(preTraceId);
		    		dbPrpJplanFeePreTrace.setAfterOperateStatus("0");
		    		dbPrpJplanFeePreTrace.setInsertDate("" + new DateTime(new Date(),DateTime.YEAR_TO_MILLISECOND));
		    		dbPrpJplanFeePreTrace.insert(dbpool);
				}
	    		
	    		
	    		blPrpJPayBank = new BLPrpJPayBank();
	    		dbPrpJPayBank = new DBPrpJPayBank();
	    		PrpJPayBankSchema prpJPayBankSchema = null;
	    		String payBankSql = " PAYTOBANKNO = '" + strPoaCode + "'";
	    		blPrpJPayBank.query(payBankSql);
	    		for(int j = 0; j < blPrpJPayBank.getSize(); j++){
	    			prpJPayBankSchema = new PrpJPayBankSchema();
	    			prpJPayBankSchema = blPrpJPayBank.getArr(j);
	    			prpJPayBankSchema.setPayMentFlag("CX"); 
	    			dbPrpJPayBank.setSchema(prpJPayBankSchema);
	        		dbPrpJPayBank.update(dbpool);
	    		}
	    		
	    		
	            dbPrpJpoaInfo = new DBPrpJpoaInfo();
	            dbPrpJpoaInfo.getInfo(dbpool, strPoaCode);
	            dbPrpJpoaInfo.setAccFlag("3");
	            dbPrpJpoaInfo.setWithDrawDate("" + new DateTime(new Date(),DateTime.YEAR_TO_SECOND));
	            dbPrpJpoaInfo.setWithDrawCode(userCode);
	            dbPrpJpoaInfo.update(dbpool);
			} 
			else{
				throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","收付与资金状态不一致，不可撤销！");
			}
    	}
    }    
    /* ADD BY PENGJINLING 2012-4-25 payment-480 关于快钱无线POS交易撤销功能申请需求 END */
    
    /**
     * @desc POS交易撤销,修改PrpJplanFeePre,PrpJpoaInfo表信息-带dbpool
     * @author liufengyao
     * @param dbpool
     * @param iPoaCode
     * @throws Exception
     */
    public void preFeeChargePosCancel(DbPool dbpool,String[] iPoaCode,String userCode,String accFlag)
    throws Exception{
    	DateTime dateTimeCurrent = new DateTime(new Date(),DateTime.YEAR_TO_SECOND);
		BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
    	DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
		PrpJplanFeePreSchema prpJplanFeePreSchema = null;
		DBPrpTmain dbPrpTmain = null;
		DBPrpTmainSub dbPrpTmainSub = new DBPrpTmainSub();
		DBPrpPhead dbPrpPhead = null;
		String othFlag = "";
		String flag = "";
		char othFlag18 = '1';
		char flag3 = '1';
		
		
    	PrpJpoaInfoSchema prpJpoaInfoSchema = new PrpJpoaInfoSchema();
    	DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
    	prpJpoaInfoSchema.setAccFlag(accFlag);
    	prpJpoaInfoSchema.setWithDrawDate(dateTimeCurrent.toString());
    	prpJpoaInfoSchema.setWithDrawCode(userCode);
        
    	int[] result = dbPrpJpoaInfo.updateBatchForPosCancel(dbpool, prpJpoaInfoSchema, iPoaCode);
    	if(!analyzeBatchUpdateResults(result, iPoaCode.length)){
    		throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","预取消的支票状态已经改变，请刷新页面后再次提交！");
    	}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(" PoaCode in (''");
		for(int i=0;i<iPoaCode.length;i++){
				buffer.append(",'").append(iPoaCode[i]).append("'");
		}
		buffer.append(")");
		blPrpJplanFeePre.query(buffer.toString(), 0);
		for(int i=0;i<blPrpJplanFeePre.getSize();i++){
			prpJplanFeePreSchema = blPrpJplanFeePre.getArr(i);
            
             
            if(!prpJplanFeePreSchema.getCertiStatus().equals("1") && !prpJplanFeePreSchema.getCertiStatus().equals("2") )
                throw new Exception("准备操作的业务状态不正确，请重新查询后再操作！");
                
			
			if (accFlag.equals("2") && prpJplanFeePreSchema.getCertiStatus().equals("3")) {
                throw new UserException(-98, -1200, "BLPrpJpreFeeCharge.java", "该业务号"
                        + prpJplanFeePreSchema.getCertiNo() + "已经生成XX，不能撤销交易！");
            }
			
			if (accFlag.equals("3") && !prpJplanFeePreSchema.getCertiStatus().equals("1")){
	                throw new UserException(-98, -1200, "BLPrpJpreFeeCharge.java", "该业务号"
	                    + prpJplanFeePreSchema.getCertiNo() + "不是未到账状态，不能删除交易！");
			}
	    	
			dbPrpJplanFeePre.setSchema(prpJplanFeePreSchema);
			dbPrpJplanFeePre.setCertiStatus("0");
			dbPrpJplanFeePre.setOperateDate(dateTimeCurrent.toString());
			dbPrpJplanFeePre.update(dbpool);
			if(prpJplanFeePreSchema.getCertiType().equals("T")){
				
	    		dbPrpTmain = new DBPrpTmain();
	    		dbPrpTmain.getInfo(dbpool, prpJplanFeePreSchema.getCertiNo());
	    		othFlag = dbPrpTmain.getOthFlag();
	    		dbPrpTmain.setOthFlag(convertOthFlag(othFlag,othFlag18));
	    		
                dbPrpJplanFeePre.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
                
                
	    		
        		if("4".equals(dbPrpTmain.getPolicySort())){
        			int infoFlag = dbPrpTmainSub.getInfo(dbPrpTmain.getProposalNo());
        			if(infoFlag!=100){
        				dbPrpTmain = new DBPrpTmain();
        				dbPrpTmain.getInfo(dbpool, dbPrpTmainSub.getMainPolicyNo());
                		othFlag = dbPrpTmain.getOthFlag();
                		dbPrpTmain.setOthFlag(convertOthFlag(othFlag,othFlag18));
                		
                        dbPrpJplanFeePre.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
                        
                        
        			}else{
        				throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","30XXXXX组合产品大XX单不存在！");
        			}
        		}
        		
			}else if(prpJplanFeePreSchema.getCertiType().equals("E")){
				
    			dbPrpPhead = new DBPrpPhead();
    			dbPrpPhead.getInfo(dbpool,prpJplanFeePreSchema.getCertiNo());
    			flag = dbPrpPhead.getFlag();
    			dbPrpPhead.setFlag(convertFlag(flag,flag3));
    			
    			
    			dbPrpJplanFeePre.updatePrpPheadForConfirm(dbpool ,dbPrpPhead);
    			
			}else{
    			throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","错误的业务类型！");
			}
		}
    	
    }

	public void preFeeChargeCreatePoaInfo(HttpServletRequest request)throws Exception {
		DateTime currentDateTimeDetail = new DateTime(new Date(),DateTime.YEAR_TO_SECOND);
		String poaType = request.getParameter("poaType");
		String[] arrCertiType = request.getParameterValues("certiType"); 
		String[] arrCertiNo = request.getParameterValues("certiNo"); 
		String[] arrSerialNo = request.getParameterValues("serialNo"); 
		String comCode = request.getParameter("comCode");
		String makeCode = request.getParameter("makeCode");
		String agentCode = request.getParameter("agentCode");
		String centerCode = request.getParameter("centerCode");
		String customNo = request.getParameter("customNo");
		String posNo = request.getParameter("posNo");
	    String businessNo = request.getParameter("businessNo");
		String planFee = request.getParameter("planFee");
		String validDate = request.getParameter("validDate");
		String validStatus = request.getParameter("validStatus");
		String posVoucherNo = request.getParameter("posVoucherNo");
		String transType = request.getParameter("transType");
		String userCode = request.getParameter("userCode");	
		String cardNo = request.getParameter("cardNo");	
		String posBillNum = request.getParameter("posBillNum");	
		String accFlag = request.getParameter("accFlag");
		String poaCode = request.getParameter("poaCode");
		String stampTax = request.getParameter("stampTax");
		String strPOSCompanyNo = request.getParameter("posCompanyNo");
		String strPOSModelNo = request.getParameter("posModelNo");
		
		PrpJpoaInfoSchema prpjpoaInfoSchema = null;
		prpjpoaInfoSchema = new PrpJpoaInfoSchema();
		prpjpoaInfoSchema.setPoaType(poaType);
		prpjpoaInfoSchema.setTransType(transType);
		prpjpoaInfoSchema.setPoaCode(poaCode);
		prpjpoaInfoSchema.setPosNo(posNo);
		prpjpoaInfoSchema.setCustomNo(customNo);
		
		prpjpoaInfoSchema.setAccBillNo(cardNo);
		prpjpoaInfoSchema.setPoaUser(userCode);
		prpjpoaInfoSchema.setOperatorCode(userCode);
		prpjpoaInfoSchema.setComCode(comCode);
		prpjpoaInfoSchema.setMakeCom(makeCode);
		prpjpoaInfoSchema.setAgentCode(agentCode);
		prpjpoaInfoSchema.setCenterCode(centerCode);
		prpjpoaInfoSchema.setTotalFee(planFee);
		prpjpoaInfoSchema.setValidDate(validDate);
		prpjpoaInfoSchema.setBusinessNo(businessNo);
		
		prpjpoaInfoSchema.setOpenDate(currentDateTimeDetail.toString());
		prpjpoaInfoSchema.setAccDate(currentDateTimeDetail.toString());
		prpjpoaInfoSchema.setAccFlag(accFlag);
		prpjpoaInfoSchema.setPosStatus(validStatus);
		prpjpoaInfoSchema.setPosVoucherNo(posVoucherNo);
		
		prpjpoaInfoSchema.setAttribute2(posBillNum);
		
		prpjpoaInfoSchema.setRemark(stampTax);
		prpjpoaInfoSchema.setPOSCompanyNo(strPOSCompanyNo);
		prpjpoaInfoSchema.setPOSModelNo(strPOSModelNo);
		try {
			preFeeChargeCreatePoaInfo(arrCertiType,arrCertiNo,arrSerialNo,prpjpoaInfoSchema);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * @desc POS刷卡生成PrpJpoaInfo表信息,回写预收款PrpJplanFeePre及业务表PrpTmain表信息-无dbpool
	 * @author liufengyao
	 * @param iCertiType
	 * @param iCertiNo
	 * @param iSerialNo
	 * @param poaType
	 * @param iPprpjpoaInfoSchema
	 * @throws Exception
	 */
    public void preFeeChargeCreatePoaInfo(String[] iCertiType,String[] iCertiNo,String[] iSerialNo,
            PrpJpoaInfoSchema iPprpjpoaInfoSchema)
    throws Exception{
    	DbPool dbpool = new DbPool();
        
        
        
    	
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
    	try {
    		dbpool.beginTransaction();
    		preFeeChargeCreatePoaInfo(dbpool,iCertiType,iCertiNo,iSerialNo,iPprpjpoaInfoSchema);
    		dbpool.commitTransaction();
    	} catch (Exception e) {
    		dbpool.rollbackTransaction();
    		throw e;
    	} finally {
    		dbpool.close();
    	} 
    }
	/**
	 * @desc POS刷卡生成PrpJpoaInfo表信息,回写预收款PrpJplanFeePre及业务表PrpTmain表信息-带dbpool
	 * @author liufengyao
	 * @param iCertiType
	 * @param iCertiNo
	 * @param iSerialNo
	 * @param poaType
	 * @param iPprpjpoaInfoSchema
	 * @throws Exception
	 */
    public void preFeeChargeCreatePoaInfo(DbPool dbpool,String[] iCertiType,String[] iCertiNo,String[] iSerialNo,
            PrpJpoaInfoSchema iPprpjpoaInfoSchema)
    throws Exception{
    	try{
    		DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
        	DBPrpTmain dbPrpTmain = null;
        	DBPrpTmainSub dbPrpTmainSub = new DBPrpTmainSub();
        	DBPrpPhead dbPrpPhead = null;
        	DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
        	PrpJplanFeePreSchema prpJplanFeePreSchema = new PrpJplanFeePreSchema();
        	String othFlag = "";
        	String flag = "";
        	char othFlag18 ;
        	char flag3;
        	boolean flagF = false;
        	boolean flagJHK = false;
        	PrpJplanFeePreSchema schemaJHK = null;
        	BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
        	String poaCode = iPprpjpoaInfoSchema.getPoaCode();
        	String operateDate = iPprpjpoaInfoSchema.getOpenDate();
        	String accFlag = iPprpjpoaInfoSchema.getAccFlag();
        	String poaType = iPprpjpoaInfoSchema.getPoaType();
        	
        	String stampTax = iPprpjpoaInfoSchema.getRemark();
        	String certiStatus="0";
        	if(accFlag.equals("0")){
        		
        		certiStatus = "1";
        		othFlag18 = '2';
        		flag3 = '2';
        	}
        	else if(accFlag.equals("1")){
        		
        		certiStatus = "2";
        		othFlag18 = '3';
        		flag3 = '3';
        	}
        	else
        		throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","支票/pos信息中AccFlag设置错误！");
        	

            
                    prpJplanFeePreSchema.setCertiStatus(certiStatus);
                    prpJplanFeePreSchema.setOperateDate(operateDate);
                    prpJplanFeePreSchema.setPoaType(poaType);
                    prpJplanFeePreSchema.setPoaCode(poaCode);
                    
                    prpJplanFeePreSchema.setPrePayRefFee(stampTax);
                    DBPrpJpoaInfo dbPrpJpoaInfo1 = new DBPrpJpoaInfo();
                    int intResult = dbPrpJpoaInfo1.getInfo(poaCode);
                    
                    
                    if(intResult==0){
                        for (int i=0;i<iCertiNo.length;i++)
                        {
                            dbPrpJplanFeePre.getInfo(dbpool,iCertiNo[i],iSerialNo[i]);
                            if(!dbPrpJplanFeePre.getCertiStatus().equals("1"))
                                throw new Exception("准备操作的业务状态不正确，请重新查询后再处理");
                        }
                    }else{
                        for (int i=0;i<iCertiNo.length;i++)
                        {
                            dbPrpJplanFeePre.getInfo(dbpool,iCertiNo[i],iSerialNo[i]);
                            if(!dbPrpJplanFeePre.getCertiStatus().equals("0"))
                                throw new Exception("XX单："+dbPrpJplanFeePre.getCertiNo()+"已经生成业务票据号");
                        }
                    }
                    int[] result = dbPrpJplanFeePre.updateBatchForConfirm(dbpool,prpJplanFeePreSchema,iCertiNo,iSerialNo);
                    if(!analyzeBatchUpdateResults(result, iCertiNo.length)){
                        throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","准备预收的业务状态已改变，请刷新页面后再次提交！");
                    }
                    
                    dbPrpJpoaInfo.setSchema(iPprpjpoaInfoSchema);
                    
                    if(intResult==0){
                        dbPrpJpoaInfo.update(dbpool);
                    }else{
                        dbPrpJpoaInfo.insert(dbpool);
                    }
        	
        	
        	
        	
        	for(int i=0;i<iCertiNo.length;i++){
        		if(iCertiType[i].equals("T")){
            		dbPrpTmain = new DBPrpTmain();
            		dbPrpTmain.getInfo(dbpool, iCertiNo[i]);
            		othFlag = dbPrpTmain.getOthFlag();
            		dbPrpTmain.setOthFlag(convertOthFlag(othFlag,othFlag18));
                    
                    dbPrpJplanFeePre.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
                    
                    
            		
            		







            		if("4".equals(dbPrpTmain.getPolicySort())){
            			int infoFlag = dbPrpTmainSub.getInfo(dbPrpTmain.getProposalNo());
            			if(infoFlag!=100){
            				dbPrpTmain = new DBPrpTmain();
            				dbPrpTmain.getInfo(dbpool, dbPrpTmainSub.getMainPolicyNo());
                    		othFlag = dbPrpTmain.getOthFlag();
                    		dbPrpTmain.setOthFlag(convertOthFlag(othFlag,othFlag18));
                    		
                            dbPrpJplanFeePre.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
                            
                            


            			}else{
            				throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","30XXXXX组合产品大XX单不存在！");
            			}
            		}
            		









                    
        		}else if(iCertiType[i].equals("E")){
        			dbPrpPhead = new DBPrpPhead();
        			dbPrpPhead.getInfo(dbpool,iCertiNo[i]);
        			
        			flag = dbPrpPhead.getFlag();
        			dbPrpPhead.setFlag(convertFlag(flag,flag3));
        			
        			
        			dbPrpJplanFeePre.updatePrpPheadForConfirm(dbpool ,dbPrpPhead);
        			
        		}else
        			throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","错误的业务类型！");
        	}
        	
        	



        	
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    	}
    	
    }
	/**
	 * @desc 根据机构获取PrpDriskConfig表的ConfigValue值
	 * @author yanglei
	 * @param iComCode 登陆机构
	 * @param iRiskCode XXXXX种代码
	 * @param iConfigCode 配置代码
	 * @return String 配置变量
	 * @throws Exception
	 */
    public String getConfigValueByComCode(String iComCode, String iRiskCode,
			String iConfigCode) throws Exception {
		String configValue = "";
		while (true) {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" ComCode ='").append(iComCode).append("' ");
			strBuffer.append(" AND RiskCode = '").append(iRiskCode).append("' ");
			strBuffer.append(" AND ConfigCode = '").append(iConfigCode).append("' ");
			PrpDriskConfigFindByConditionsCommand command = new PrpDriskConfigFindByConditionsCommand(strBuffer.toString());
			Collection col = (Collection) command.execute();
			Iterator iter = col.iterator();
			PrpDriskConfigDto prpDriskConfigDto = null;
			if (iter.hasNext()) {
				prpDriskConfigDto = (PrpDriskConfigDto) iter.next();
				configValue = prpDriskConfigDto.getConfigValue();
				break;
			}else {
				if(iComCode.equals("00000000")){
					throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.getLeastDaysByCheck()","获取总公司配置代码("+iConfigCode+")失败！");
				}
				DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
				int intResult = dbPrpDcompany.getInfo(iComCode);
				if(intResult ==100){
					throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.getLeastDaysByCheck()","获取上级机构代码失败！");
				}else{
					iComCode = dbPrpDcompany.getUpperComCode();
				}
			}
		}
		return configValue;
    }
    
    
    /**
	 * @desc 根据机构获取PrpDriskConfig表的ConfigValue值
	 * @author yanglei
	 * @param iComCode 登陆机构
	 * @param iRiskCode XXXXX种代码
	 * @param iConfigCode 配置代码
	 * @return String 配置变量
	 * @throws Exception
	 */
    public String getConfigValueByRiskCodeComCode(String iComCode, String iRiskCode,
			String iConfigCode) throws Exception {
		String configValue = "";
		while (true) {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" ComCode ='").append(iComCode).append("' ");
			strBuffer.append(" AND RiskCode = '").append(iRiskCode).append("' ");
			strBuffer.append(" AND ConfigCode = '").append(iConfigCode).append("' ");
			PrpDriskConfigFindByConditionsCommand command = new PrpDriskConfigFindByConditionsCommand(strBuffer.toString());
			Collection col = (Collection) command.execute();
			Iterator iter = col.iterator();
			PrpDriskConfigDto prpDriskConfigDto = null;
			if (iter.hasNext()) {
				prpDriskConfigDto = (PrpDriskConfigDto) iter.next();
				configValue = prpDriskConfigDto.getConfigValue();
				break;
			}else {
				if(iComCode.equals("00000000")){
					return "0";
				}
				DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
				int intResult = dbPrpDcompany.getInfo(iComCode);
				if(intResult ==100){
					throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.getLeastDaysByCheck()","获取上级机构代码失败！");
				}else{
					iComCode = dbPrpDcompany.getUpperComCode();
				}
			}
		}
		return configValue;
    }
    
    
	/**
	 * @desc XX缴费请求发送到平台
	 * @author yanglei
	 * @throws Exception
	 */
    public void sendPolicyPayRecRequest(BLPrpJpoaInfo blPrpJpoaInfo,String bankAccounts,String warrantBank)throws Exception{
 
    	PolicyPayRecEncoder policyPayRecEncoder = new PolicyPayRecEncoder();
    	PolicyPayRecDecoder policyPayRecDecoder = new PolicyPayRecDecoder();
    	DbPool dbpool = new DbPool();
        
        
        
    	
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
    	String context="";
    	String response = "";
    	 try
		{
			context = policyPayRecEncoder.encode(blPrpJpoaInfo,bankAccounts,warrantBank);
		}
		catch (Exception ex)
		{
			throw new Exception(ex.getMessage());
		}
		
		try
		{
			
			logger.info("context"+context);
			
			response = EbaoProxy.getInstance().request(context, blPrpJpoaInfo.getArr(0).getComCode());
			response = StringUtils.replace(response, "GBK", "GB2312");
			
			logger.info("response"+response);
			
			policyPayRecDecoder.decode(dbpool, blPrpJpoaInfo, response);
			convertFlagForSH(dbpool, blPrpJpoaInfo);
		}catch (UserException ue){
			throw new Exception(ue.getErrorMessage());
		}catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("解析交强XXXXX平台返回XX缴费请求接口串失败：" + ex.getMessage());
		}finally {
    		dbpool.close();
    	} 
    }
    
	/**
	 * @desc XX确认调用业务服务生成XX
	 * @author yanglei
	 * @throws Exception
	 */
    public ArrayList preConfirm(String poaCode)throws Exception{
    	ArrayList policyNoList = null;
    	StringBuffer buffer = new StringBuffer();
    	buffer.append(" PoaCode ='" + poaCode + "'");
    	BLPrpJpoaInfo blPrpJpoaInfo = new BLPrpJpoaInfo();
    	blPrpJpoaInfo.query(buffer.toString(), 0);
    	BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
    	blPrpJplanFeePre.query(buffer.toString(), 0);
    	if(blPrpJplanFeePre.getSize()==0){
    		throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.preConfirm()","获取XX单信息失败！");
    	}
    	blPrpJpoaInfo.setBLPrpJplanFeePre(blPrpJplanFeePre);
    	
    	if(!blPrpJpoaInfo.getArr(0).getAccFlag().equals("1")){
    		
    		sendPolicyAccQueryRequest(blPrpJpoaInfo);
    		if(!blPrpJpoaInfo.getArr(0).getAccFlag().equals("1")){
    			throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.preConfirm()","该笔交易为非到账状态，不能进行XX/批改确认！");
    		}
    	}
		String[] certiType = new String[blPrpJplanFeePre.getSize()];
		String[] certiNo = new String[blPrpJplanFeePre.getSize()];
		String[] serialNo = new String[blPrpJplanFeePre.getSize()];
		for(int j=0;j<blPrpJplanFeePre.getSize();j++){
			certiType[j] = blPrpJplanFeePre.getArr(j).getCertiType();
			certiNo[j] = blPrpJplanFeePre.getArr(j).getCertiNo();
			serialNo[j] = blPrpJplanFeePre.getArr(j).getSerialNo();
		}
		
		policyNoList = createPolicyByServelt(certiType,certiNo,serialNo);
		return policyNoList;
    }

    /*ADD BY PENGJINLING 2013-4-2 payment-600 上海电销在线支付 BEGIN*/
	/**
	 * @desc 上海电销XX确认调用业务服务生成XX
	 * @author pengjinling
	 * @throws Exception
	 */
    public ArrayList preConfirmDX(String poaCode)throws Exception{
    	String messageDesc = "";
    	ArrayList policyNoList = null;
    	StringBuffer buffer = new StringBuffer();
    	buffer.append(" PoaCode ='" + poaCode + "'");
    	BLPrpJpoaInfo blPrpJpoaInfo = new BLPrpJpoaInfo();
    	blPrpJpoaInfo.query(buffer.toString(), 0);
    	BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
    	blPrpJplanFeePre.query(buffer.toString(), 0);
    	if(blPrpJplanFeePre.getSize()==0){
    		throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.preConfirmDX()","获取XX单信息失败！");
    	}    	
    	blPrpJpoaInfo.setBLPrpJplanFeePre(blPrpJplanFeePre);
    	
    	
    	String poaType = blPrpJpoaInfo.getArr(0).getPoaType();
    	
    	if(!blPrpJpoaInfo.getArr(0).getAccFlag().equals("1")){
    		
        	DBCmsInterface001 dbCmsInterface001 = new DBCmsInterface001();
    		int count = dbCmsInterface001.getInfo(poaCode);
    		
    		if(count == 100){
    			messageDesc = "没有找到CmsInterface001交易信息!V_PAYID:'" + poaCode;                    		
    			throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.preConfirmDX()",messageDesc);			
    		}
    		else{
    			
    			if(!"M".equals(poaType) && !"N".equals(poaType)){
    				
        			if(!"S".equals(dbCmsInterface001.getCMS_STATUS())){
            			messageDesc = "交易CmsInterface001不为成功，请核查CMS_STATUS!V_PAYID:'" + poaCode;                   			
            			throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.preConfirmDX()",messageDesc);
        			}
    			}
    			
    		}
    		
    		
    		sendPolicyAccQueryRequestDX(blPrpJpoaInfo);
    		if(!blPrpJpoaInfo.getArr(0).getAccFlag().equals("1")){
    			throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.preConfirmDX()","该笔交易为非到账状态，不能进行XX/批改确认！");
    		}
    	}
		String[] certiType = new String[blPrpJplanFeePre.getSize()];
		String[] certiNo = new String[blPrpJplanFeePre.getSize()];
		String[] serialNo = new String[blPrpJplanFeePre.getSize()];
		for(int j=0;j<blPrpJplanFeePre.getSize();j++){
			certiType[j] = blPrpJplanFeePre.getArr(j).getCertiType();
			certiNo[j] = blPrpJplanFeePre.getArr(j).getCertiNo();
			serialNo[j] = blPrpJplanFeePre.getArr(j).getSerialNo();
		}
		
		
		
		Map checkMap = new HashMap();
		if("M".equals(poaType) || "N".equals(poaType)){
			checkMap = this.callProcedureMN(certiNo[0]);
    	}else{
    		checkMap = this.callProcedure(certiNo[0]);
    	}
		
		if("0".equals(checkMap.get("MessageCode").toString())){
			
			policyNoList = createPolicyByServelt(certiType,certiNo,serialNo);
		}
		else{
			messageDesc = "生成XX前数据校验失败：poaCode:" + poaCode + "," + checkMap.get("MessageDesc");
			throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.preConfirmDX()",messageDesc);
		}
		return policyNoList;
    }
    
	/**
	 * @desc 到账查询请求发送到平台
	 * @author pengjinling
	 * @throws Exception
	 */
    public void sendPolicyAccQueryRequestDX(BLPrpJpoaInfo blPrpJpoaInfo)throws Exception{
    	String messageDesc = "";
    	DBCmsInterface001 dbCmsInterface001 = new DBCmsInterface001();
    	BLPrpJDXPaymentSH blPrpJDXPaymentSH = new BLPrpJDXPaymentSH();
    	DbPool dbpool = new DbPool();
		try {
            
            
			
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
            
            dbpool.beginTransaction();           
      
            
            Map requestMap = blPrpJDXPaymentSH.sendPolicyAccQueryRequestDX(dbpool,blPrpJpoaInfo);
            
            if("000000".equals(requestMap.get("MessageCode"))){
        		if(blPrpJpoaInfo.getArr(0).getAccFlag().equals("1")){
        			for(int i =0;i < blPrpJpoaInfo.getBLPrpJplanFeePre().getSize();i++){
        				
        				blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).setCertiStatus("4");
        	    		DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
        	    		dbPrpJplanFeePre.setSchema(blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i));
        	    		dbPrpJplanFeePre.update(dbpool);
        			}
        			
        			dbCmsInterface001.getInfo(dbpool, blPrpJpoaInfo.getArr(0).getPoaCode());
        			dbCmsInterface001.setDISHONOR_FLAG("1");
        			dbCmsInterface001.update(dbpool);	
        		}
            }
            else{
            	messageDesc = requestMap.get("MessageDesc").toString();
            	throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.sendPolicyAccQueryRequestDX()",messageDesc);
            }
            
            
            dbpool.commitTransaction();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		} finally {
    		dbpool.close();
    	} 
	}
    
	/**
	 * 调用数据校验存储过程
	 * @param strCertiNo 业务号
	 * @return Map 返回信息
	 * @throws 异常类
	 */
	public Map callProcedure(String strCertiNo) throws Exception {
		String messageCode = "";
		String messageDesc = "";
		Map checkMap = new HashMap();
		DbPool dbpool = new DbPool();
    	try {
			
			
    		
			String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
			if("0".equals(payment_switch)){
				dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
			}else{
				dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
			}
			
			
			dbpool.beginTransaction();
			
			
			Connection conn = this.getConnection(dbpool);
			CallableStatement cstmt = conn.prepareCall("{call PayCMS_pkg.PayPLProposalChkSH_Main(?,?,?)}");
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.setString(1, strCertiNo);
			cstmt.setString(2, messageCode);
			cstmt.setString(3, messageDesc);
			cstmt.execute();
			
			messageCode = cstmt.getString(2);
			messageDesc = cstmt.getString(3);
			logPrintln("存储过程返回信息代码："+messageCode);
			logPrintln("存储过程返回详细信息："+messageDesc);
			checkMap.put("MessageCode", messageCode);
			checkMap.put("MessageDesc", messageDesc);
			
			dbpool.commitTransaction();

		} catch (Exception exception) {
			dbpool.rollbackTransaction();
			exception.printStackTrace();
			ErrorLoger.callErrorLogProcedure("sendPolicyAccQueryRequestDX", "数据库异常:"
					+ exception.getMessage());
		} finally {
			dbpool.close();
		}
		return checkMap;
	}
	
	
	/**
	 * 调用数据校验存储过程
	 * @param strCertiNo 业务号
	 * @return Map 返回信息
	 * @throws 异常类
	 */
	public Map callProcedureMN(String strCertiNo) throws Exception {
		String messageCode = "";
		String messageDesc = "";
		Map checkMap = new HashMap();
		DbPool dbpool = new DbPool();
    	try {
			
			
    		
			String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
			if("0".equals(payment_switch)){
				dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
			}else{
				dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
			}
			
			
			dbpool.beginTransaction();
			
			
			Connection conn = this.getConnection(dbpool);
			CallableStatement cstmt = conn.prepareCall("{call PayCMS_pkg.PayPLProposalChkSHForMNSD_Main(?,?,?)}");
			cstmt.registerOutParameter(2, Types.VARCHAR);
			cstmt.registerOutParameter(3, Types.VARCHAR);
			cstmt.setString(1, strCertiNo);
			cstmt.setString(2, messageCode);
			cstmt.setString(3, messageDesc);
			cstmt.execute();
			
			messageCode = cstmt.getString(2);
			messageDesc = cstmt.getString(3);
			logPrintln("存储过程返回信息代码："+messageCode);
			logPrintln("存储过程返回详细信息："+messageDesc);
			checkMap.put("MessageCode", messageCode);
			checkMap.put("MessageDesc", messageDesc);
			
			dbpool.commitTransaction();

		} catch (Exception exception) {
			dbpool.rollbackTransaction();
			exception.printStackTrace();
			ErrorLoger.callErrorLogProcedure("sendPolicyAccQueryRequestDX", "数据库异常:"
					+ exception.getMessage());
		} finally {
			dbpool.close();
		}
		return checkMap;
	}
	
	
	
	/**
	 * 根据DbPool获取Connection
	 * @param dbpool数据库连接池
	 * @return Connection 数据库连接
	 * @throws SQLException 数据库操作异常类
	 */
	private Connection getConnection(DbPool dbpool) {
		Connection connection = null;
		try {
			
			
			
			DBManager dbManager = null;
			String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
			if("0".equals(payment_switch)){
				dbManager = dbpool.getDBManager(SysConfig.CONST_PAYMENTDATASOURCE);
			}else{
				dbManager = dbpool.getDBManager(SysConfig.CONST_PAYMENTNEWDATASOURCE);
			}
			
			
			connection = dbManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}	
    /* ADD BY PENGJINLING 2013-4-2 payment-600 上海电销在线支付 END*/
    
	/**
	 * @desc 到账确认请求发送到平台
	 * @author yanglei
	 * @throws Exception
	 */
    public void sendPolicyAccConfirmRequest(BLPrpJpoaInfo blPrpJpoaInfo)throws Exception{
    	
    	
    	PolicyAccConfirmEncoder policyAccConfirmEncoder = new PolicyAccConfirmEncoder();
    	PolicyAccConfirmDecoder policyAccConfirmDecoder = new PolicyAccConfirmDecoder();
    	DbPool dbpool = new DbPool();
    	
        
        
    	
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
    	String context="";
    	String response = "";
    	 try
		{
			context = policyAccConfirmEncoder.encode(blPrpJpoaInfo);
		}
		catch (Exception ex)
		{
			throw new Exception(ex.getMessage());
		}
		
		try
		{
			
			logger.info("context"+context);
			
			response = EbaoProxy.getInstance().request(context, blPrpJpoaInfo.getArr(0).getComCode());
			response = StringUtils.replace(response, "GBK", "GB2312");
			
			logger.info("response"+response);
			
			policyAccConfirmDecoder.decode(dbpool, blPrpJpoaInfo, response);
			convertFlagForSH(dbpool, blPrpJpoaInfo);
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("解析交强XXXXX平台返回XX缴费请求接口串失败：" + ex.getMessage());
		} finally {
    		dbpool.close();
    	} 
    }
    
	/**
	 * @desc 缴费更改请求发送到平台
	 * @author yanglei
	 * @throws Exception
	 */
    public void sendPolicyFeeChangeRequest(BLPrpJpoaInfo blPrpJpoaInfo)throws Exception{
    	
    	DbPool dbpool = new DbPool();
        
        
        
    	
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
		PolicyFeeChangeEncoder policyFeeChangeEncoder = new PolicyFeeChangeEncoder();
		PolicyFeeChangeDecoder policyFeeChangeDecoder = new PolicyFeeChangeDecoder();
		String context = "";
		String response = "";
		try {
			context = policyFeeChangeEncoder.encode(blPrpJpoaInfo);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
		try {
			
			logger.info("context" + context);
			
			response = EbaoProxy.getInstance().request(context, blPrpJpoaInfo.getArr(0).getComCode());
			response = StringUtils.replace(response, "GBK", "GB2312");
			
			logger.info("response" + response);
			
			policyFeeChangeDecoder.decode(dbpool, blPrpJpoaInfo, response);
			convertFlagForSH(dbpool, blPrpJpoaInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("解析交强XXXXX平台返回XX缴费请求接口串失败：" + ex.getMessage());
		} finally {
    		dbpool.close();
    	} 
	}
	/**
	 * @desc 到账查询请求发送到平台
	 * @author yanglei
	 * @throws Exception
	 */
    public void sendPolicyAccQueryRequest(BLPrpJpoaInfo blPrpJpoaInfo)throws Exception{
    	
    	DbPool dbpool = new DbPool();
        
        
        
		PolicyAccQueryEncoder policyAccQueryEncoder = new PolicyAccQueryEncoder();
		PolicyAccQueryDecoder policyAccQueryDecoder = new PolicyAccQueryDecoder();
		String context = "";
		String response = "";
		try {
			context = policyAccQueryEncoder.encode(blPrpJpoaInfo);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
    	
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
		
		try {
			
			logger.info("context" + context);
			
			response = EbaoProxy.getInstance().request(context, blPrpJpoaInfo.getArr(0).getComCode());
			response = StringUtils.replace(response, "GBK", "GB2312");
			
			logger.info("response" + response);
			
			policyAccQueryDecoder.decode(dbpool, blPrpJpoaInfo, response);
			convertFlagForSH(dbpool, blPrpJpoaInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("解析交强XXXXX平台返回XX缴费请求接口串失败：" + ex.getMessage());
		} finally {
    		dbpool.close();
    	} 
	}
    
	/**
	 * @desc凭证号补传请求发送到平台
	 * @author yanglei
	 * @throws Exception
	 */
    public void sendRenewWarrantNoRequest(BLPrpJpoaInfo blPrpJpoaInfo)throws Exception{
    	
    	DbPool dbpool = new DbPool();
        
        
        
    	
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
		RenewWarrantNoEncoder renewWarrantNoEncoder = new RenewWarrantNoEncoder();
		RenewWarrantNoDecoder RenewWarrantNoDecoder = new RenewWarrantNoDecoder();
		String context = "";
		String response = "";
		try {
			context = renewWarrantNoEncoder.encode(blPrpJpoaInfo);
		} catch (Exception ex) {
			throw new Exception(ex.getMessage());
		}
		
		try {
			
			logger.info("context" + context);
			
			response = EbaoProxy.getInstance().request(context, blPrpJpoaInfo.getArr(0).getComCode());
			response = StringUtils.replace(response, "GBK", "GB2312");
			
			logger.info("response" + response);
			
			RenewWarrantNoDecoder.decode(dbpool, blPrpJpoaInfo, response);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("解析交强XXXXX平台返回XX缴费请求接口串失败：" + ex.getMessage());
		} finally {
    		dbpool.close();
    	} 
	}
    
    /**
	 * @desc上海见费出单更新业务到账状态
	 * @author yanglei
	 * @throws Exception
	 */
	public void convertFlagForSH(DbPool dbpool, BLPrpJpoaInfo blPrpJpoaInfo)
			throws Exception {
		char othFlag18 = 0;
		char flag3 = 0;
		String accFlag = blPrpJpoaInfo.getArr(0).getAccFlag();
		if ("0".equals(accFlag)) {
			
			othFlag18 = '2';
			flag3 = '2';
		} else if ("1".equals(accFlag)) {
			
			othFlag18 = '3';
			flag3 = '3';
		} else if ("3".equals(accFlag)) {
			
			othFlag18 = '1';
			flag3 = '1';
		} else
			throw new UserException(-98, -1200, "BLPrpJpreFeeCharge.java",
			"错误的到账状态！");
		DBPrpTmainSub dbPrpTmainSub = new DBPrpTmainSub();
		DBPrpTmain dbPrpTmain = new DBPrpTmain();
		DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
		for (int i = 0; i < blPrpJpoaInfo.getBLPrpJplanFeePre().getSize(); i++) {
			if (blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i).getCertiType()
					.equals("T")) {
				dbPrpTmain.getInfo(dbpool, blPrpJpoaInfo.getBLPrpJplanFeePre()
						.getArr(i).getCertiNo());
				String othFlag = dbPrpTmain.getOthFlag();
				dbPrpTmain.setOthFlag(convertOthFlag(othFlag, othFlag18));
				
                dbPrpJplanFeePre.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
                
                
			} else if (blPrpJpoaInfo.getBLPrpJplanFeePre().getArr(i)
					.getCertiType().equals("E")) {
				DBPrpPhead dbPrpPhead = new DBPrpPhead();
				dbPrpPhead.getInfo(dbpool, blPrpJpoaInfo.getBLPrpJplanFeePre()
						.getArr(i).getCertiNo());
				String flag = dbPrpPhead.getFlag();
				dbPrpPhead.setFlag(convertFlag(flag, flag3));
				
				
				dbPrpJplanFeePre.updatePrpPheadForConfirm(dbpool ,dbPrpPhead);
				
			} else
				throw new UserException(-98, -1200, "BLPrpJpreFeeCharge.java",
						"错误的业务类型！");
		}
	}
	
	/**
	 * @description:POS交易强制到账，修改PrpJplanFeePre,PrpJpoaInfo,PrpTmain,PrpPhead表信息
	 * @param dbpool
	 * @param iPoaCode
	 * @param userCode
	 * @param accFlag
	 * @throws Exception
	 * @author genghaijun-wb
	 */
    public void preFeeChargePosForcedAccount(DbPool dbpool, String[] iPoaCode, String userCode, String accFlag)
            throws Exception {
        DateTime dateTimeCurrent = new DateTime(new Date(), DateTime.YEAR_TO_SECOND);
        BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
        DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
        PrpJplanFeePreSchema prpJplanFeePreSchema = null;
        DBPrpTmain dbPrpTmain = null;
        DBPrpTmainSub dbPrpTmainSub = new DBPrpTmainSub();
        DBPrpPhead dbPrpPhead = null;
        String othFlag = "";
        String flag = "";
        char othFlag18 = '3';
        char flag3 = '3';
        
        boolean flagF = false;
        boolean flagJHK = false;

        
        PrpJpoaInfoSchema prpJpoaInfoSchema = new PrpJpoaInfoSchema();
        DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
        prpJpoaInfoSchema.setAccFlag(accFlag);
        
        prpJpoaInfoSchema.setAccDate(dateTimeCurrent.toString());
        
        prpJpoaInfoSchema.setWithDrawDate(dateTimeCurrent.toString());
        prpJpoaInfoSchema.setWithDrawCode(userCode);

        int[] result = dbPrpJpoaInfo.updateBatchForPosCancel(dbpool, prpJpoaInfoSchema, iPoaCode);
        if (!analyzeBatchUpdateResults(result, iPoaCode.length)) {
            throw new UserException(-98, -1200, "BLPrpJpreFeeCharge.java", "预取消的支票状态已经改变，请刷新页面后再次提交！");
        }
        
        StringBuffer buffer = new StringBuffer();
        buffer.append(" PoaCode in (''");
        for (int i = 0; i < iPoaCode.length; i++) {
            buffer.append(",'").append(iPoaCode[i]).append("'");
        }
        buffer.append(")");
        blPrpJplanFeePre.query(buffer.toString(), 0);
        
    	/*ADD BY PENGJINLING 2013-5-3 PAYMENT-608 关于使用易宝支付垫款实现网电业务一次配送的申请 BEGIN*/
		
        String[] iCertiNo = new String[blPrpJplanFeePre.getSize()];
        String[] iSerialNo = new String[blPrpJplanFeePre.getSize()];
        for (int i = 0; i < blPrpJplanFeePre.getSize(); i++) {
        	iCertiNo[i] = blPrpJplanFeePre.getArr(i).getCertiNo();
        	iSerialNo[i] = blPrpJplanFeePre.getArr(i).getSerialNo();
        }
        for (int i = 0; i < iPoaCode.length; i++) {
	        dbPrpJpoaInfo.getInfo(iPoaCode[i]);
			if(dbPrpJpoaInfo.getPOSModelNo() != null && "93".equals(dbPrpJpoaInfo.getPOSModelNo())){
				if("1".equals(prpJpoaInfoSchema.getAccFlag())){
					dbPrpJpoaInfo.setAccDate(prpJpoaInfoSchema.getAccDate());
					this.insertCMSDFData(dbpool,dbPrpJpoaInfo,iCertiNo,iSerialNo);
				}
			}
        }
    	/*ADD BY PENGJINLING 2013-5-3 PAYMENT-608 关于使用易宝支付垫款实现网电业务一次配送的申请 END*/        
        
        for (int i = 0; i < blPrpJplanFeePre.getSize(); i++) {
            prpJplanFeePreSchema = blPrpJplanFeePre.getArr(i);

            
            if (!prpJplanFeePreSchema.getCertiStatus().equals("1"))
                throw new Exception("准备操作的业务非正在扣款状态，请重新查询后再操作！");

            
            dbPrpJplanFeePre.setSchema(prpJplanFeePreSchema);
            dbPrpJplanFeePre.setCertiStatus("2");
            dbPrpJplanFeePre.update(dbpool);
            if (prpJplanFeePreSchema.getCertiType().equals("T")) {
                
                dbPrpTmain = new DBPrpTmain();
                dbPrpTmain.getInfo(dbpool, prpJplanFeePreSchema.getCertiNo());
                othFlag = dbPrpTmain.getOthFlag();
                dbPrpTmain.setOthFlag(convertOthFlag(othFlag, othFlag18));
                
                dbPrpJplanFeePre.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
                
                
                
                if ("4".equals(dbPrpTmain.getPolicySort())) {
                    int infoFlag = dbPrpTmainSub.getInfo(dbPrpTmain.getProposalNo());
                    if (infoFlag != 100) {
                        dbPrpTmain = new DBPrpTmain();
                        dbPrpTmain.getInfo(dbpool, dbPrpTmainSub.getMainPolicyNo());
                        othFlag = dbPrpTmain.getOthFlag();
                        dbPrpTmain.setOthFlag(convertOthFlag(othFlag, othFlag18));
                        
                        dbPrpJplanFeePre.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
                        
                        
                    } else {
                        throw new UserException(-98, -1200, "BLPrpJpreFeeCharge.java", "30XXXXX组合产品大XX单不存在！");
                    }
                }
                
                
                if("HX-JHK".equals(prpJplanFeePreSchema.getVSCardFlag())){
                	flagJHK = true;
                	blPrpJplanFeePre.getArr(i).setOperateDate(dateTimeCurrent.toString());
                }else{
                	flagF = true;
                }
                if(flagJHK && flagF){
                	throw new UserException(-98, -1003,"BLPrpJpreFeeCharge.java","激活卡业务与非激活卡业务不允许合并强制到账！ ");
                }
                
            } else if (prpJplanFeePreSchema.getCertiType().equals("E")) {
                
                dbPrpPhead = new DBPrpPhead();
                dbPrpPhead.getInfo(dbpool, prpJplanFeePreSchema.getCertiNo());
                flag = dbPrpPhead.getFlag();
                dbPrpPhead.setFlag(convertFlag(flag, flag3));
                
                
                dbPrpJplanFeePre.updatePrpPheadForConfirm(dbpool ,dbPrpPhead);
                
            } else {
                throw new UserException(-98, -1200, "BLPrpJpreFeeCharge.java", "错误的业务类型！");
            }
        }
        
        
        
        if(flagJHK){
        	this.recevieeAccountInfoForVS(blPrpJplanFeePre);
        }
        
    }
    
    /**
     * 
     * @param blPrpJplanFeePre
     */
	public void recevieeAccountInfoForVS(BLPrpJplanFeePre blPrpJplanFeePre) throws Exception{
		ArrayList list = new ArrayList();
		StringBuffer buf = new StringBuffer();
		BLPrpTbatch blPrpTbatch = new BLPrpTbatch();
		int count = blPrpJplanFeePre.getSize();
		Map checkProposalNo = new HashMap();
		String strOperateDate = blPrpJplanFeePre.getArr(0).getOperateDate();
		String strPoaCode = blPrpJplanFeePre.getArr(0).getPoaCode();
		String strOperatorCode = blPrpJplanFeePre.getArr(0).getOperatorCode();
		for (int i = 0; i < count; i++) {
			if("4".equals(blPrpJplanFeePre.getArr(i).getAttribute3())){
				checkProposalNo.put(blPrpJplanFeePre.getArr(i).getContractNo(),blPrpJplanFeePre.getArr(i).getComCode() );
			}else{
				checkProposalNo.put(blPrpJplanFeePre.getArr(i).getCertiNo(),blPrpJplanFeePre.getArr(i).getComCode() );
			}
		}
		
		buf.append(" ProposalNo IN (' '");
		Iterator it = checkProposalNo.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry entry = (Map.Entry) it.next();
			String strProposalNo = (String)entry.getKey();
			buf.append(",'").append(strProposalNo).append("'");
		}
		buf.append(")");
		blPrpTbatch.query(buf.toString());
		int size = blPrpTbatch.getSize();
		for (int i = 0; i < size; i++) {
			Map map = new HashMap();
			map.put("visaCode", blPrpTbatch.getArr(i).getVisaCode());
			map.put("account", blPrpTbatch.getArr(i).getBillCount());
			map.put("comCode", checkProposalNo.get(blPrpTbatch.getArr(i).getProposalNo()));
			map.put("proposalNo", blPrpTbatch.getArr(i).getProposalNo());
			map.put("payDate", strOperateDate);
			map.put("poaCode", strPoaCode);
			map.put("payOperatorId", strOperatorCode);
			list.add(map);
		}
		
		String JHK_switch = SysConfig.getProperty("JHK_SWITCH");
		if("1".equals(JHK_switch)){
			BLPrpJVsaccountInfo blPrpJVsaccountInfo=new BLPrpJVsaccountInfo();
			blPrpJVsaccountInfo.dorecevieeAccount(list);
		}else{
			UIVsAccountInfoAction uiVsAccountInfoAction = new UIVsAccountInfoAction(); 
			uiVsAccountInfoAction.dorecevieeAccount(list);
		}
		
		
	}
	
	/**
	 * 有线POS缴费成功的数据插入单证 系统新激活卡下发中间表
	 * @param iCertiNo
	 * @param iSerialNo
	 * @throws Exception
	 */
	public boolean sendAccountInfoForVS(String[] iCertiNo,String[] iSerialNo)throws Exception {
		StringBuffer buf = new StringBuffer();
		BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre(); 
		BLPrpJpreFeeCharge blPrpJpreFeeCharge = new BLPrpJpreFeeCharge();
		int intSize = iCertiNo.length;
		buf.append(" Certino IN (");
		try {
			for (int i = 0; i < intSize; i++) {
				if (i == (intSize - 1)) {
					buf.append("'" + iCertiNo[i] + "')");
				} else {
					buf.append("'" + iCertiNo[i] + "', ");
				}
			}
			blPrpJplanFeePre.query(buf.toString());
			int count = blPrpJplanFeePre.getSize();
			if(count>0){
				blPrpJpreFeeCharge.recevieeAccountInfoForVS(blPrpJplanFeePre);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public void splitVoucherNo(String voucherNo,String[] iPoaCode,DbPool dbpool) throws Exception{
		String voucherNo1 = "";
		String voucherNo2 = "";
		String[]  voucherNos=voucherNo.split(",");
		if(voucherNos.length>1){
			voucherNo1=voucherNos[0];
			voucherNo2=voucherNos[1];
			String infoUpdateSql = "UPDATE PrpJpoaInfo SET PreVoucherNo = ?,PreVoucherNo2=? WHERE PoaCode = ?";
			dbpool.prepareInnerStatement(infoUpdateSql);
			for (int i = 0; i < iPoaCode.length; i++) {
				int index = 1;
				dbpool.setString(index++,voucherNo1);
				dbpool.setString(index++,voucherNo2);
				dbpool.setString(index++,iPoaCode[i]);
				dbpool.executePreparedUpdate();  
			}
			dbpool.closePreparedStatement();
		}else{
			voucherNo1=voucherNos[0];
			String infoUpdateSql = "UPDATE PrpJpoaInfo SET PreVoucherNo = ? WHERE PoaCode = ?";
			dbpool.prepareInnerStatement(infoUpdateSql);
			for (int j = 0; j < iPoaCode.length; j++) {
				int index = 1;
				dbpool.setString(index++,voucherNo1);
				dbpool.setString(index++,iPoaCode[j]);
				dbpool.executePreparedUpdate();  
			}
			dbpool.closePreparedStatement();

		}
	}

	/**
     * @desc 快钱无线POS交易撤销,修改PrpJplanFeePre,PrpJpoaInfo,PrpJpaybank,Cms_interface003表信息-带dbpool
     * @author PengJinling
     * @param dbpool
     * @param iPoaCode
     * @throws Exception
     */
    public void splitPreFeeChargeWXPosCancel(DbPool paymentDbpool,DbPool dbpool,String[] iPoaCode,String userCode)
    throws Exception{
    	
    	boolean isInit = false;
    	
    	com.sp.payment.schema.PrpJplanFeePreSchema preSchema = null;
    	DBPrpTmain dbPrpTmain = null;
    	DBPrpPhead dbPrpPhead = null;
		DBPrpJPayBankPre dbPrpJPayBank = null;
		DBPrpJPayBank bank=null;
		DBPrpJpoaInfo dbPrpJpoaInfo = null;
		com.sp.payment.dbsvr.jf.DBPrpJplanFeePre dbPrpJplanFeePre = null;
		DBPrpJplanFeePre dbPrpJplanFeePreForUpdate = new DBPrpJplanFeePre();
		DBCmsInterface003 dbCmsInterface003 = null;
		DBPrpJplanFeePreTrace dbPrpJplanFeePreTrace = null;
		BLPrpJPayBankPre blPrpJPayBank = null;
		BLPrpJPayBank blbank=null;
		com.sp.payment.blsvr.jf.BLPrpJplanFeePre blPrpJplanFeePre = new com.sp.payment.blsvr.jf.BLPrpJplanFeePre();
		String payment_bank_switch=SysConfig.getProperty("PAYMENT_BANK_SWITCH");
    	for(int i = 0; i < iPoaCode.length; i++){
    		String strPoaCode = iPoaCode[i];
    		
			
			String poaCodeSql = " POACODE = '" + strPoaCode + "'";
			blPrpJplanFeePre.query(poaCodeSql);
			
			
			dbCmsInterface003 = new DBCmsInterface003();
			dbCmsInterface003.getInfo(dbpool,strPoaCode);
			
			
			
			if("I".equals(dbCmsInterface003.getEX_STATUS()) || "A".equals(dbCmsInterface003.getEX_STATUS())){
				for(int k = 0; k < blPrpJplanFeePre.getSize(); k++){
					preSchema = new com.sp.payment.schema.PrpJplanFeePreSchema();
					preSchema = blPrpJplanFeePre.getArr(k);
					if("1".equals(preSchema.getCertiStatus())){
						isInit = true;
					}
					else{
						isInit = false;
						break;
					}
				}
			}			
			
			if(/*isInit ==*/ true){
				
				dbCmsInterface003.setEX_STATUS("D");
				dbCmsInterface003.update(dbpool);
				
	    		
	    		
				for(int k = 0; k < blPrpJplanFeePre.getSize(); k++){
					preSchema = new com.sp.payment.schema.PrpJplanFeePreSchema();
					preSchema = blPrpJplanFeePre.getArr(k);
		    		dbPrpJplanFeePre = new com.sp.payment.dbsvr.jf.DBPrpJplanFeePre();
		    		dbPrpJplanFeePre.setSchema(preSchema);
		    		dbPrpJplanFeePre.setCertiStatus("0");
		    		dbPrpJplanFeePre.setPoaType("");
		    		dbPrpJplanFeePre.setPoaCode("");
		    		dbPrpJplanFeePre.setOperateDate("" + new DateTime(new Date(),DateTime.YEAR_TO_MILLISECOND));
		    		dbPrpJplanFeePre.setOrderId("");
		    		dbPrpJplanFeePre.setPrePayRefFee("0.00");
		    		dbPrpJplanFeePre.update(dbpool);
		    		
		    		
		    		if("T".equals(preSchema.getCertiType())){
		    			String strOthFlag = "";
		    			dbPrpTmain = new DBPrpTmain();
		    			dbPrpTmain.getInfo(dbpool,preSchema.getCertiNo());
		    			strOthFlag = dbPrpTmain.getOthFlag();
		    			dbPrpTmain.setOthFlag(strOthFlag.substring(0, 17) + "1" + strOthFlag.substring(18));
	                    
		    			dbPrpJplanFeePreForUpdate.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
	                    
	                    
		    			
		    			if("4".equals(preSchema.getAttribute3())){
		    				dbPrpTmain.getInfo(dbpool,preSchema.getContractNo());
		    				strOthFlag = dbPrpTmain.getOthFlag();
		        			dbPrpTmain.setOthFlag(strOthFlag.substring(0, 17) + "1" + strOthFlag.substring(18));
		                    
		        			dbPrpJplanFeePreForUpdate.updatePrpTmainForConfirm(dbpool, dbPrpTmain);
		                    
		                    
		    			}
		    		}
		    		else if("E".equals(preSchema.getCertiType())){
		    			String flag = "";
		    			dbPrpPhead = new DBPrpPhead();
		    			dbPrpPhead.getInfo(dbpool,preSchema.getCertiNo());
		    			flag = dbPrpPhead.getFlag();
		    			dbPrpPhead.setFlag(flag.substring(0,2) + "1" + flag.substring(3));
		    			
		    			
		    			dbPrpJplanFeePreForUpdate.updatePrpPheadForConfirm(dbpool ,dbPrpPhead);
		    			
		    		}
		    		
		    		
		    		dbPrpJplanFeePreTrace = new DBPrpJplanFeePreTrace();
		    		dbPrpJplanFeePreTrace.setSchemaWithPre(preSchema);
		    		
		    		String preTraceId = dbPrpJplanFeePreTrace.getSequence(dbpool);
		    		dbPrpJplanFeePreTrace.setPreTraceId(preTraceId);
		    		dbPrpJplanFeePreTrace.setAfterOperateStatus("0");
		    		dbPrpJplanFeePreTrace.setInsertDate("" + new DateTime(new Date(),DateTime.YEAR_TO_MILLISECOND));
		    		dbPrpJplanFeePreTrace.insert(paymentDbpool);
				}
	    		if("1".equals(payment_bank_switch)){
		    		
		    		blPrpJPayBank = new BLPrpJPayBankPre();
		    		dbPrpJPayBank = new DBPrpJPayBankPre();
		    		PrpJPayBankPreSchema prpJPayBankSchema = null;
		    		String payBankSql = " PAYTOBANKNO = '" + strPoaCode + "'";
		    		blPrpJPayBank.query(payBankSql);
		    		for(int j = 0; j < blPrpJPayBank.getSize(); j++){
		    			prpJPayBankSchema = new PrpJPayBankPreSchema();
		    			prpJPayBankSchema = blPrpJPayBank.getArr(j);
		    			prpJPayBankSchema.setPayMentFlag("CX"); 
		    			dbPrpJPayBank.setSchema(prpJPayBankSchema);
		        		dbPrpJPayBank.update(dbpool);
	    			}
	    		}else{
	    			blbank = new BLPrpJPayBank();
		    		bank = new DBPrpJPayBank();
		    		PrpJPayBankSchema prpJPayBankSchema = null;
		    		String payBankSql = " PAYTOBANKNO = '" + strPoaCode + "'";
		    		blbank.query(payBankSql);
		    		for(int j = 0; j < blbank.getSize(); j++){
		    			prpJPayBankSchema = new PrpJPayBankSchema();
		    			prpJPayBankSchema = blbank.getArr(j);
		    			prpJPayBankSchema.setPayMentFlag("CX"); 
		    			bank.setSchema(prpJPayBankSchema);
		    			bank.update(dbpool);
		    		}
	    		}
	    		
	    		
	            dbPrpJpoaInfo = new DBPrpJpoaInfo();
	            dbPrpJpoaInfo.getInfo(dbpool, strPoaCode);
	            dbPrpJpoaInfo.setAccFlag("3");
	            dbPrpJpoaInfo.setWithDrawDate("" + new DateTime(new Date(),DateTime.YEAR_TO_SECOND));
	            dbPrpJpoaInfo.setWithDrawCode(userCode);
	            dbPrpJpoaInfo.update(dbpool);
			} 
			else{
				throw new UserException(-98,-1200,"BLPrpJpreFeeCharge.java","收付与资金状态不一致，不可撤销！");
			}
    	}
    }    
  
	
}
