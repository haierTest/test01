package com.sp.indiv.ci.interf;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sp.indiv.ci.interf.CarShipTaxCheckDecoder;
import com.sp.indiv.ci.interf.CarShipTaxCheckEncoder;
import com.sp.indiv.ci.interf.EbaoProxy;
import com.sp.indiv.ci.blsvr.BLCIAssemblePayNo;
import com.sp.indiv.ci.blsvr.BLCICheckCarShipTax;
import com.sp.indiv.ci.blsvr.BLCICheckCarShipTaxDetail;
import com.sp.prpall.blsvr.cb.BLPrpCcarshipTax;
import com.sp.prpall.schema.PrpCcarshipTaxSchema;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import java.util.logging.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class CarShipTaxCheck {
	Log logger = LogFactory.getLog(getClass());

    private BLCICheckCarShipTax blCICheckCarShipTax = new BLCICheckCarShipTax();
    private BLCICheckCarShipTaxDetail blCICheckCarShipTaxDetail = new BLCICheckCarShipTaxDetail();

    
    public BLCICheckCarShipTax getBlCICheckCarShipTax() {
        return blCICheckCarShipTax;
    }

    public void setBlCICheckCarShipTax(BLCICheckCarShipTax blCICheckCarShipTax) {
        this.blCICheckCarShipTax = blCICheckCarShipTax;
    }

    public BLCICheckCarShipTaxDetail getBlCICheckCarShipTaxDetail() {
        return blCICheckCarShipTaxDetail;
    }

    public void setBlCICheckCarShipTaxDetail(BLCICheckCarShipTaxDetail blCICheckCarShipTaxDetail) {
        this.blCICheckCarShipTaxDetail = blCICheckCarShipTaxDetail;
    }
    
    public String check(String comcode, String startDate, String endDate, String operateCode, String checkType)
            throws UserException, Exception {
    	Logger logger = Logger.getLogger(this.getClass().getName());
    	logger.info("comcode: " + comcode);
    	logger.info("startDate: " + startDate);
    	logger.info("endDate: " + endDate);
    	logger.info("operateCode: " + operateCode);
    	logger.info("checkType: " + checkType);
        String strReturn = "0";
        String checkNo = "";
        String iWherePart = "";
        String strSQL = "";
        DbPool dbpool = new DbPool();
        ResultSet resultSet = null;
        
        try 
        {
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            BLCICheckCarShipTax blCICheckCarShipTax = new BLCICheckCarShipTax();
            BLCICheckCarShipTaxDetail blCICheckCarShipTaxDetail = new BLCICheckCarShipTaxDetail();
            BLCIAssemblePayNo bLCIAssemblePayNo = new BLCIAssemblePayNo();
            
            iWherePart = " COMCODE = '" + comcode + "' AND STARTDATE ='" + startDate + "' AND EndDate ='" + endDate + "' AND CHECKTYPE ='" + checkType
                    + "'";
            blCICheckCarShipTax.query(dbpool,iWherePart);
            
            for(int i = 0; i<blCICheckCarShipTax.getSize() ; i++)
            {
            	checkNo = blCICheckCarShipTax.getArr(i).getCheckNo();
            	bLCIAssemblePayNo.initArr();
            	iWherePart = " CHECKNO='"+ checkNo + "' AND ResponseCode='1'";
            	bLCIAssemblePayNo.query(dbpool, iWherePart);
            	
            	if(bLCIAssemblePayNo.getSize()>0)
            	{
            		
            		strReturn = "2";
            		logger.info(startDate + " - " + endDate + " 已经有汇总过完税凭证，不能再次对帐");
            		return strReturn;
            	}
            }
            
            if (blCICheckCarShipTax.getSize() > 0) {
                checkNo = blCICheckCarShipTax.getArr(0).getCheckNo();
                strSQL = " DELETE FROM CICHECKCARSHIPTAX WHERE  CHECKNO= " + "'" + checkNo + "'";
                dbpool.delete(strSQL);
                strSQL = " DELETE FROM CICHECKCARSHIPTAXDETAIL WHERE  CHECKNO = " + "'" + checkNo + "'";
                dbpool.delete(strSQL);
            }

            
            checkNo = CarShipTaxCheck.getCheckInf(dbpool, comcode, startDate, endDate, operateCode, checkType);
            
            
            if("1".equals(checkType)){
	            
	            if(!checkNo.equals(""))
	            {
	              CarShipTaxCheck.updateCheckInf(dbpool, checkNo);
	            }
	            iWherePart = "CHECKNO = '" + checkNo + "' AND FLAG !='1'";
	            
	            blCICheckCarShipTaxDetail.query(dbpool,iWherePart);
	            if (blCICheckCarShipTaxDetail.getSize() > 0)
	            {
	                strSQL = "UPDATE CICHECKCARSHIPTAX SET FLAG = '0' WHERE CHECKNO = '" + checkNo +"' AND FLAG ='1'";
	                dbpool.update(strSQL);
	                strReturn = "0";
	            }
	            else
	            {
	            	strReturn = "1";
	            }
            }else{
            	
            	if(!checkNo.equals("")){
            		strReturn = "1";
            	}else{
            		strReturn = "0";
            	}
            }
            dbpool.commitTransaction();
        }
        catch (Exception e) {
            e.printStackTrace();
            dbpool.rollbackTransaction();
            throw e;
        }
        finally {
            dbpool.close();
        }
        return strReturn;
    }
    
    public static String getCheckInf(DbPool dbpool, String comcode,String startDate,String endDate,String operateCode,String checkType) throws UserException,
    Exception{  
    	Logger logger = Logger.getLogger(CarShipTaxCheck.class.getName());
        String checkNo = "";  
        ResultSet resultSet = null;
        try 
        {
            String strSQL = "";
            
            int totalPage = 0; 
            
            int mTotalPayNo = 0; 
            int tTotalPayNo = 0; 
            
            double mSumTax = 0; 
            double tSumTax = 0; 
            
            ChgDate chgDate       = new ChgDate();
            String nowDate        = chgDate.getCurrentTime("yyyy-MM-dd");  
            String requestXML = "";
            String responseXML = "";
            

            CarShipTaxCheck ZCarShipTaxCheck = new CarShipTaxCheck();
            CarShipTaxCheck DCarShipTaxCheck = new CarShipTaxCheck();
            
            CarShipTaxCheckEncoder encoder = new CarShipTaxCheckEncoder();
            CarShipTaxCheckDecoder decoder = new CarShipTaxCheckDecoder();
            BLPrpCcarshipTax blPrpCcarshipTax = new BLPrpCcarshipTax();
            PrpCcarshipTaxSchema prpCcarshipTaxSchema = null;
            String sqlWhere = "";

            
            resultSet = dbpool.query("select TaxCheckNo_SEQ.nextval from dual"); 
            if(resultSet.next()){
                checkNo = ""+resultSet.getInt(1);
                resultSet.close();
            }  
            logger.info("对帐号为： " + resultSet);
            
            
            
            
            
            if(!new UtiPower().carShipTaxCheckBJSwitch(startDate)){
            strSQL = " SELECT COUNT(1), SUM(T.SUMPAYTAX) FROM PRPCCARSHIPTAX T, PRPCMAIN M, prpcjfcdlogmsg P, cicarshiptaxdemand D WHERE "
                    + " T.TAXRELIFFlAG = '0' AND M.POLICYNO = T.POLICYNO AND P.POLICYNO = M.POLICYNO AND D.POLICYNO = M.POLICYNO AND P.VALIDDATE Between "
                    + "to_date('"+startDate+" 00:00:00','YYYY-MM-DD hh24:mi:ss')"
                    + " AND "
                    + "to_date('"+endDate+" 23:59:59','YYYY-MM-DD hh24:mi:ss')"
                    + " AND M.COMCODE LIKE '"
                    + comcode.substring(0, 2)
                    + "%' AND D.COMMISSIONTAX='1'" ;
            }else{
            strSQL = " SELECT COUNT(1), SUM(T.MTAXSUMTAX) FROM CICheckCarShipTaxDetailQG T WHERE T.TAXCONDITIONCODE = '0'"
            		+ " AND T.SIGNDATE Between to_date('"+startDate+" 00:00:00','YYYY-MM-DD hh24:mi:ss')"
            		+ " AND to_date('"+endDate+" 23:59:59','YYYY-MM-DD hh24:mi:ss') AND T.COMCODE LIKE '"+comcode.substring(0, 2)
            		+ "%' AND T.EXTENDCHAR2='1'" ;
            }
            
            
            
            resultSet = dbpool.query(strSQL);
            if(resultSet.next()){
                mTotalPayNo = resultSet.getInt(1);
                mSumTax = resultSet.getDouble(2);
            }  
            logger.info("本地总完税凭证数： " + mTotalPayNo);
            logger.info("本地总完税总金额： " + mSumTax);
            
            String pageLength = AppConfig.get("sysconst.CI_INSURED_01_PAGE_LENGTH");
            String computerCode = AppConfig.get("sysconst.CI_INSURED_01_COMPUTER_CODE");
            String strStartDate = correctDate(startDate);
            String strEndDate = correctDate(endDate);
            String pageNum = "";
            requestXML = encoder.encode(comcode, strStartDate,strEndDate,"","",computerCode);
            responseXML = EbaoProxy.getInstance().request(requestXML, comcode);
            responseXML = StringUtils.replace(responseXML, "GBK", "GB2312");

            logger.info("====对账发给ebao平台的XML串：" + requestXML);
            logger.info("====对账ebao平台返回的XML串：" + responseXML);
            decoder.decode(ZCarShipTaxCheck, responseXML);
            String strTTotalPayNo = ZCarShipTaxCheck.blCICheckCarShipTax.getArr(0).getTTotalPayNo();
            String strTTSumTax = ZCarShipTaxCheck.blCICheckCarShipTax.getArr(0).getTTotalTax();
            if(strTTotalPayNo.equals("")){
            	strTTotalPayNo = "0";
            }
            if(strTTSumTax.equals("")){
            	strTTSumTax = "0";
            }
            tTotalPayNo = Integer.parseInt(strTTotalPayNo);
            tSumTax = Double.parseDouble(strTTSumTax);
            logger.info("平台返回总完税凭证数： " + tTotalPayNo);
            logger.info("平台返回总完税总金额： " + tSumTax);
            

            ZCarShipTaxCheck.blCICheckCarShipTax.getArr(0).setCheckNo(checkNo);
            ZCarShipTaxCheck.blCICheckCarShipTax.getArr(0).setComCode(comcode);
            ZCarShipTaxCheck.blCICheckCarShipTax.getArr(0).setStartDate(startDate);
            ZCarShipTaxCheck.blCICheckCarShipTax.getArr(0).setEndDate(endDate);
            ZCarShipTaxCheck.blCICheckCarShipTax.getArr(0).setMTotalPayNo(""+mTotalPayNo);
            ZCarShipTaxCheck.blCICheckCarShipTax.getArr(0).setMTotalTax(""+mSumTax);
            ZCarShipTaxCheck.blCICheckCarShipTax.getArr(0).setOperateDate(nowDate);
            ZCarShipTaxCheck.blCICheckCarShipTax.getArr(0).setOperateCode(operateCode);
            ZCarShipTaxCheck.blCICheckCarShipTax.getArr(0).setCheckType(checkType);
            

            if((tTotalPayNo==mTotalPayNo)&&(tSumTax==mSumTax))
            {
            	ZCarShipTaxCheck.blCICheckCarShipTax.getArr(0).setFlag("1");
            }
            else
            {
            	ZCarShipTaxCheck.blCICheckCarShipTax.getArr(0).setFlag("0");
            }
            
            ZCarShipTaxCheck.blCICheckCarShipTax.save(dbpool);
            
            
            if("1".equals(checkType)){
                
            	CarShipTaxCheck TempCarShipTaxCheck = new CarShipTaxCheck();
            	requestXML = encoder.encode(comcode, strStartDate,strEndDate,pageLength,"1",computerCode);
                responseXML = EbaoProxy.getInstance().request(requestXML, comcode);
                responseXML = StringUtils.replace(responseXML, "GBK", "GB2312");
                decoder.decode(TempCarShipTaxCheck, responseXML);
            	String strTotalPage = TempCarShipTaxCheck.blCICheckCarShipTax.getArr(0).getTotalPage();
            	
            	if(strTotalPage.equals("")){
            		strTotalPage = "0";
            	}
            	
            	totalPage = Integer.parseInt(strTotalPage);
                logger.info("====对账发给ebao平台的XML串：" + requestXML);
                logger.info("====对账ebao平台返回的XML串：" + responseXML);
                decoder.decode(ZCarShipTaxCheck, responseXML);
	            
            	logger.info("日对帐对详细信息时总页数为：" + totalPage);
	        	for(int i=1; i<=totalPage; i++)
	        	{
	        		requestXML = encoder.encode(comcode, strStartDate,strEndDate, pageLength, ""+i,computerCode);
	        		responseXML = EbaoProxy.getInstance().request(requestXML, comcode);
	                responseXML = StringUtils.replace(responseXML, "GBK", "GB2312");
	                
	            	logger.info("====对账发给ebao平台的XML串：" + requestXML);
	            	logger.info("====对账ebao平台返回的XML串：" + responseXML);
	        		decoder.decode(DCarShipTaxCheck, responseXML);
	        	}
	
	        	for(int i=0; i<DCarShipTaxCheck.blCICheckCarShipTaxDetail.getSize(); i++)
	        	{
	        		DCarShipTaxCheck.blCICheckCarShipTaxDetail.getArr(i).setCheckNo(checkNo);
	        		sqlWhere = "policyno='" + DCarShipTaxCheck.blCICheckCarShipTaxDetail.getArr(i).getTPolicyNo() + "' and TAXRELIFFlAG = '0'";
	        		blPrpCcarshipTax.query(dbpool, sqlWhere);
	        		if(blPrpCcarshipTax.getSize()>0){
		        		prpCcarshipTaxSchema = blPrpCcarshipTax.getArr(0);
		        		
		        		DCarShipTaxCheck.blCICheckCarShipTaxDetail.getArr(i).setMPolicyNo(prpCcarshipTaxSchema.getPolicyNo());
		        		
		        		DCarShipTaxCheck.blCICheckCarShipTaxDetail.getArr(i).setMPayNo(prpCcarshipTaxSchema.getPaidFreeCertificate());
		        		
		        		double taxActual = Double.parseDouble(prpCcarshipTaxSchema.getTaxActual());
		        		double previousPay  = Double.parseDouble(prpCcarshipTaxSchema.getPreviousPay());
		        		DCarShipTaxCheck.blCICheckCarShipTaxDetail.getArr(i).setMPayTax(taxActual+previousPay + "");
		        		
		        		DCarShipTaxCheck.blCICheckCarShipTaxDetail.getArr(i).setMLateFee(prpCcarshipTaxSchema.getLateFee());
	        		}
	        	}
	        	
	        	
	        	DCarShipTaxCheck.blCICheckCarShipTaxDetail.save(dbpool);
	        	
	        	
	        	
	        	
	        	
	            if(!new UtiPower().carShipTaxCheckBJSwitch(startDate)){
	        	strSQL = "INSERT INTO CICHECKCARSHIPTAXDETAIL ( SELECT '"
	        		+ checkNo
	        		+ "',T.POLICYNO,'无',T.PAIDFREECERTIFICATE,'无',T.TAXACTUAL,'',T.LATEFEE,'','','','5' FROM PRPCCARSHIPTAX T,PRPCMAIN M, prpcjfcdlogmsg P, cicarshiptaxdemand D WHERE "
	        		+ " T.TAXRELIFFlAG = '0' AND M.RISKCODE='0507' AND M.POLICYNO = T.POLICYNO AND P.POLICYNO = M.POLICYNO AND D.POLICYNO = M.POLICYNO AND P.VALIDDATE Between "
	        		+ " to_date('"+startDate+" 00:00:00','YYYY-MM-DD hh24:mi:ss')"
	        		+ " AND "
	        		+ " to_date('"+endDate+" 23:59:59','YYYY-MM-DD hh24:mi:ss')"
	        		+ " AND M.COMCODE LIKE '"
	        		+ comcode.substring(0, 2)
	        		+ "%' AND D.COMMISSIONTAX='1'" 
	        		+ " AND M.POLICYNO NOT IN (SELECT MPOLICYNO FROM CICHECKCARSHIPTAXDETAIL WHERE  CHECKNO ='"
	        		+ checkNo + "'))";
	            }else{
	            strSQL = "INSERT INTO CICHECKCARSHIPTAXDETAIL ( SELECT '"
	            	+ checkNo
	            	+ "',T.CRETINO,'无',T.ExtendChar1,'无',T.MTaxAnnualTaxDue,'',T.MTaxSumOverDue,'','','','5' FROM CICheckCarShipTaxDetailQG T,PRPCMAIN M WHERE "
	            	+ " T.TAXCONDITIONCODE = '0' AND M.RISKCODE='0507' AND M.POLICYNO = T.CRETINO AND T.SIGNDATE Between "
	        		+ " to_date('"+startDate+" 00:00:00','YYYY-MM-DD hh24:mi:ss')"
	        		+ " AND "
	        		+ " to_date('"+endDate+" 23:59:59','YYYY-MM-DD hh24:mi:ss')"
	        		+ " AND T.COMCODE LIKE '"
	        		+ comcode.substring(0, 2)
	        		+ "%' AND T.EXTENDCHAR2='1'" 
	        		+ " AND T.CRETINO NOT IN (SELECT MPOLICYNO FROM CICHECKCARSHIPTAXDETAIL WHERE  CHECKNO ='"
	        		+ checkNo + "'))";
	            }
	            
	        	
	        	dbpool.insert(strSQL);
            }
            return checkNo;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public static void updateCheckInf(DbPool dbpool, String checkNo) throws SQLException, ClassNotFoundException, UserException, Exception {    
        try 
        {
            String strSQL = "";
            
            
            strSQL =  "UPDATE CICHECKCARSHIPTAXDETAIL  SET FLAG = '1' WHERE 1=1 AND CHECKNO = '" + checkNo + "' AND " +
                    "( TPAYNO = MPAYNO OR ( TPAYNO IS NULL AND MPAYNO IS NULL ) ) AND " +
                    "( TPAYTAX = MPAYTAX OR ( TPAYTAX IS NULL AND MPAYTAX IS NULL )) AND " +
                    "( TLATEFEE = MLATEFEE OR ( TLATEFEE IS NULL AND MLATEFEE IS NULL ))" ;
            dbpool.update(strSQL);
            
            strSQL = "UPDATE CICHECKCARSHIPTAXDETAIL  SET FLAG = '2' WHERE 1=1 AND CHECKNO = '" + checkNo + "' AND "
                    + "( TPAYNO IS NOT NULL AND MPAYNO IS NULL )";
            dbpool.update(strSQL);
            
            strSQL = "UPDATE CICHECKCARSHIPTAXDETAIL  SET FLAG = '3' WHERE 1=1 AND CHECKNO = '" + checkNo + "' AND "
                    + "( TPAYNO IS  NULL AND MPAYNO IS NOT NULL )";
            dbpool.update(strSQL);
            
            strSQL = "UPDATE CICHECKCARSHIPTAXDETAIL  SET FLAG = '4' WHERE 1=1 AND CHECKNO = '" + checkNo + "' AND "
                    + "FLAG IS NULL"; 
            dbpool.update(strSQL);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("更新对帐明细表标志位时出现异常！");
        }
    }
    
    /**
	 * 纠正日期格式
	 *
	 * @param dateString
	 *            8个字符的日期
	 * @return YYYYMMDD形式的日期
	 */
    private static String correctDate(String dateString) {
        String result = StringUtils.replace(dateString, "-", "");
        return result;
    }

    public static void main(String[] args) throws Exception {
        ChgDate chgDate       = new ChgDate();
        String nowDate        = chgDate.getCurrentTime("yyyy-MM-dd");  
        CarShipTaxCheck check = new CarShipTaxCheck();
        check.check("11000000", nowDate, nowDate, "zidong", "1");
    }
}
