package com.sp.prpall.blsvr.jf;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import com.sp.payment.utility.DBHelper;
import com.sp.prpall.dbsvr.jf.*;
import com.sp.prpall.schema.*;
import com.sp.sysframework.reference.DBManager;
import com.sp.utility.*;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;

public class BLPrpJPreFeeVoucher {

	/**
	 * 生成支票、划账预收凭证
	 * @throws 异常类
	 */
	public String genPreFeeVoucher(DbPool dbpool, PrpJpayRefVoucherSchema prpJpayRefVoucherSchema, BLPrpJpayRefDetail blPrpJpayRefDetail) 
	throws Exception {

		if ((prpJpayRefVoucherSchema.getPayRefCode().equals("") || prpJpayRefVoucherSchema.getPayRefCode() == null)
				&& (prpJpayRefVoucherSchema.getVoucherDate().equals("") || prpJpayRefVoucherSchema.getVoucherDate() == null)) {
			throw new UserException(-96, -1167, "BLPrpJPreFeeVoucher.genPreFeeVoucher()",
					"PayRefDate、PayRefCode字段为空！");
		}
		if (blPrpJpayRefDetail.getSize() == 0) {
			throw new UserException(-96, -1167, "BLPrpJPreFeeVoucher.genPreFeeVoucher()",
					"PrpJpayRefDetail表生成的数据错误！");
		}

		
		
		
		DBPrpJpayRefVoucher dBPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
		dBPrpJpayRefVoucher.setSchema(prpJpayRefVoucherSchema);
		
		
		dBPrpJpayRefVoucher.insert(dbpool);

		
		for (int i = 0; i < blPrpJpayRefDetail.getSize(); i++) {

			blPrpJpayRefDetail.getArr(i).setPayRefFee(
					""+ Math.abs(Double.parseDouble(blPrpJpayRefDetail.getArr(i).getPayRefFee())));
			
			if (blPrpJpayRefDetail.getArr(i).getPayWay().substring(0, 1).equals("4")
					&&!blPrpJpayRefDetail.getArr(i).getPayWay().equals("421")) {
				BLPrpJpoaMain blPrpJpoaMain = new BLPrpJpoaMain();
				blPrpJpoaMain.voucherVerifyPoa(dbpool,blPrpJpayRefDetail.getArr(i));
			}
		}
		
		blPrpJpayRefDetail.save(dbpool);

		
		String strRealPayRefNo = prpJpayRefVoucherSchema.getRealPayRefNo();
		String strVoucherNo="";
		String strVoucherNo2="";
		String prePayment_switch=SysConfig.getProperty("PAYMENT_XYSKPZ_SWITCH");
		if("1".equals(prePayment_switch)){
			strVoucherNo = callProcedureNew(dbpool,dBPrpJpayRefVoucher.getCenterCode(),"1a",dBPrpJpayRefVoucher.getVoucherDate());
			
			if(!"0".equals(dBPrpJpayRefVoucher.getToCenterCode())
					&&!dBPrpJpayRefVoucher.getCenterCode().equals(dBPrpJpayRefVoucher.getToCenterCode())
					&&dBPrpJpayRefVoucher.getToCenterCode()!=null){
			
				strVoucherNo2 = callProcedureNew(dbpool,dBPrpJpayRefVoucher.getToCenterCode(),"1a",dBPrpJpayRefVoucher.getVoucherDate());
				strVoucherNo = strVoucherNo+","+strVoucherNo2;
			}
		}else{
			strVoucherNo = callProcedure(dbpool, strRealPayRefNo );
		}
		return strVoucherNo;

	}
	/**
	 * 调用总帐接口的存储过程
	 * @param dbpool数据库连接池
	 * @param realPayRefNo实际收付号
	 * @return String 凭证号
	 * @throws 异常类
	 */
	public String callProcedure(DbPool dbpool, String realPayRefNo) throws Exception {
		String messageCode = "";
		String messageDesc = "";
		DBManager dbManager = dbpool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);
		Connection conn = dbManager.getConnection();
		CallableStatement cstmt = conn.prepareCall("{call sunshvoucrt_pkg.payment_voucher(?,?,?)}");
		cstmt.registerOutParameter(2, Types.VARCHAR);
		cstmt.registerOutParameter(3, Types.VARCHAR);
		cstmt.setString(1, realPayRefNo);
		cstmt.setString(2, messageCode);
		cstmt.setString(3, messageDesc);
		cstmt.execute();
		messageCode = cstmt.getString(2);
		messageDesc = cstmt.getString(3);
		if (cstmt != null)
			cstmt.close();

		if (!messageCode.equals("0")) {
			throw new UserException(-96, -1167, "BLPrpJpaymantVoucher.callProcedure()", "收付款确认失败："
					+ messageDesc);
		}
		return messageDesc;
	}
	
	/**
	 * 调用总帐接口的存储过程
	 * @param dbpool数据库连接池
	 * @param realPayRefNo实际收付号
	 * @return String 凭证号
	 * @throws 异常类
	 */
	/*public String splitCallProcedure(DbPool dbpool, String realPayRefNo) throws Exception {
		String messageCode = "";
		String messageDesc = "";
		DBManager dbManager = dbpool.getDBManager(SysConfig.CONST_PAYMENTDATASOURCE);
		Connection conn = dbManager.getConnection();
		CallableStatement cstmt = conn.prepareCall("{call sunshvoucrt_pkg.payment_voucher(?,?,?)}");
		cstmt.registerOutParameter(2, Types.VARCHAR);
		cstmt.registerOutParameter(3, Types.VARCHAR);
		cstmt.setString(1, realPayRefNo);
		cstmt.setString(2, messageCode);
		cstmt.setString(3, messageDesc);
		cstmt.execute();
		messageCode = cstmt.getString(2);
		messageDesc = cstmt.getString(3);
		if (cstmt != null)
			cstmt.close();

		if (!messageCode.equals("0")) {
			throw new UserException(-96, -1167, "BLPrpJpaymantVoucher.callProcedure()", "收付款确认失败："
					+ messageDesc);
		}
		return messageDesc;
	}*/
	public String callProcedureNew(DbPool dbpool, String CenterCode,String v_voutype,String PayRefDate) throws Exception {
		String messageCode = "";
		String messageDesc = "";
		Connection conn = DBHelper.getConnection(dbpool);
		CallableStatement cstmt = conn.prepareCall("{call sunshvoucrt_pkg.Only_get_VoucherNo(?,?,?,?,?)}");
		cstmt.registerOutParameter(4, Types.VARCHAR);
		cstmt.registerOutParameter(5, Types.VARCHAR);
		cstmt.setString(1, CenterCode);
		cstmt.setString(2, v_voutype);
		cstmt.setString(3, PayRefDate);
		cstmt.setString(4, messageCode);
		cstmt.setString(5, messageDesc);
		cstmt.execute();
		messageCode = cstmt.getString(4);
		messageDesc = cstmt.getString(5);
		if (cstmt != null)
			cstmt.close();

		if (!messageCode.equals("0")) {
			throw new UserException(-96, -1167, "BLPrpJpaymantVoucher.callProcedureNew()", "收付款确认失败："
					+ messageDesc);
		}
		return messageDesc;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
}
