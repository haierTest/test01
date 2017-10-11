
/****************************************************************************************
 * Description：宁波车船税对账（走平台和地税局交互的）
 * Author     : 刘佳
 * CreateDate：2009-07-01
 * UpdateLog：   Name       Date            Reason/Contents
 *****************************************************************************************/


package com.sp.indiv.ci.interf;

import java.sql.ResultSet;
import com.sp.indiv.ci.interf.EbaoProxy;
import com.sp.indiv.ci.schema.CICheckCarShipTaxDetailQGSchema;
import com.sp.indiv.ci.blsvr.BLCIAssemblePayNoQG;
import com.sp.indiv.ci.blsvr.BLCICheckCarShipTaxDetailQG;
import com.sp.indiv.ci.blsvr.BLCICheckCarShipTaxQG;
import com.sp.indiv.ci.blsvr.BLCITaxDataCompareQG;
import com.sp.indiv.ci.dbsvr.DBCICheckCarShipTaxDetailQG;
import com.sp.platform.bl.facade.BLPrpDcompanyFacade;
import com.sp.platform.dto.domain.PrpDcompanyDto;
import com.sp.prpall.blsvr.cb.BLPrpCcarshipTax;
import com.sp.prpall.blsvr.cb.BLPrpCcarshipTaxOrigin;
import com.sp.prpall.blsvr.pg.BLPrpPcarshipTax;
import com.sp.prpall.schema.PrpCcarshipTaxOriginSchema;
import com.sp.prpall.schema.PrpCcarshipTaxSchema;
import com.sp.prpall.schema.PrpPcarshipTaxSchema;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;



public class CarShipTaxCheckQG {
	Log logger = LogFactory.getLog(getClass());
    private BLCICheckCarShipTaxQG       blCICheckCarShipTaxQG       = new BLCICheckCarShipTaxQG();
    private BLCICheckCarShipTaxDetailQG blCICheckCarShipTaxDetailQG = new BLCICheckCarShipTaxDetailQG();
    private BLCITaxDataCompareQG        blCITaxDataCompareQG        = new BLCITaxDataCompareQG();
    private BLCIAssemblePayNoQG         blCIAssemblePayNoQG         = new BLCIAssemblePayNoQG();
    
    public BLCICheckCarShipTaxQG getBlCICheckCarShipTaxQG() {
        return blCICheckCarShipTaxQG;
    }

    public void setBlCICheckCarShipTax(BLCICheckCarShipTaxQG blCICheckCarShipTaxQG) {
        this.blCICheckCarShipTaxQG = blCICheckCarShipTaxQG;
    }

    public BLCICheckCarShipTaxDetailQG getBlCICheckCarShipTaxDetailQG() {
        return blCICheckCarShipTaxDetailQG;
    }

    public void setBlCICheckCarShipTaxDetailQG(BLCICheckCarShipTaxDetailQG blCICheckCarShipTaxDetailQG) {
        this.blCICheckCarShipTaxDetailQG = blCICheckCarShipTaxDetailQG;
    }
    
    public void setBlCITaxDataCompareQG(BLCITaxDataCompareQG blCITaxDataCompareQG){
        this.blCITaxDataCompareQG=blCITaxDataCompareQG;
    }
    
    public BLCITaxDataCompareQG getBlCITaxDataCompareQG(){
        return blCITaxDataCompareQG;
    }
    
    public void setBlCIAssemblePayNoQG(BLCIAssemblePayNoQG blCIAssemblePayNoQG){
        this.blCIAssemblePayNoQG=blCIAssemblePayNoQG;
    }
    
    public BLCIAssemblePayNoQG getBlCIAssemblePayNoQG(){
        return blCIAssemblePayNoQG;
    }
    
    public String check(String comcode, String startDate, String endDate, String operateCode, String checkType)
            throws UserException, Exception {
        String strReturn = "0";
        String checkNo = "";
        String TaxDeclareQueryNo="";
        String iWherePart = "";
        String strSQL = "";
        DbPool dbpool = new DbPool();
        try 
        {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            BLCICheckCarShipTaxQG blCICheckCarShipTaxQG = new BLCICheckCarShipTaxQG();
            BLPrpDcompanyFacade dcompany  = new BLPrpDcompanyFacade();
            PrpDcompanyDto prpDcompanyDto = dcompany.findByPrimaryKey(comcode);
            
            
            iWherePart = " ComCode = '" + comcode + "' AND STARTDATE = TO_DATE('" + startDate + "','yyyy-mm-dd') AND EndDate = TO_DATE('" + endDate +"','yyyy-mm-dd') ";
            
            blCICheckCarShipTaxQG.query(dbpool,iWherePart);
            String taxDeclearConfirmNo="";
            
            for(int i = 0; i<blCICheckCarShipTaxQG.getSize() ; i++)
            {
                TaxDeclareQueryNo   = blCICheckCarShipTaxQG.getArr(i).getTaxDeclareQueryNo();
                taxDeclearConfirmNo = blCICheckCarShipTaxQG.getArr(i).getTaxDeclearConfirmNo();
                if(taxDeclearConfirmNo!=null&&!"".equals(taxDeclearConfirmNo.trim()))
                {
                    
                    strReturn = "2";
                    return strReturn;
                }
                
                strSQL = " DELETE FROM CICHECKCARSHIPTAXQG WHERE  TaxDeclareQueryNo= " + "'" + TaxDeclareQueryNo + "'";
                dbpool.delete(strSQL);
                strSQL = " DELETE FROM CICHECKCARSHIPTAXDETAILQG WHERE  TaxDeclareQueryNo = " + "'" + TaxDeclareQueryNo + "'";
                dbpool.delete(strSQL);
            }
            
            if (blCICheckCarShipTaxQG.getSize() > 0) {
                checkNo = blCICheckCarShipTaxQG.getArr(0).getCheckNo();  
            }
            
           
            checkNo = getCheckInf(dbpool, comcode, startDate, endDate, operateCode, checkType ,prpDcompanyDto.getTaxRegisterNo());
            if(!checkNo.equals("")){
                strReturn = "1";
            }else{
                strReturn = "0";
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
    
    public  String getCheckInf(DbPool dbpool, String comcode,String startDate,String endDate,String operateCode,String checkType,String taxregisterno) throws UserException,
    Exception{  
        String checkNo = "";  
        ResultSet resultSet = null;
        try 
        {
            String strPolicySQL  = "";
            String strEndorseSQL = "";
            int mPolicyCount    = 0; 
            int tPolicyCount    = 0; 
            int mEndorseCount   = 0; 
            int tEndorseCount   = 0; 
            double mSumPolicyMoney    = 0; 
            double tSumPolicyMoney    = 0; 
            double mSumEndorseMoney   = 0; 
            double tSumEndorseMoney   = 0; 
            ChgDate chgDate    = new ChgDate();
            String nowDate     = chgDate.getCurrentTime("yyyy-MM-dd");  
            String requestXML  = "";
            String responseXML = "";
            CarShipTaxCheckQG ZCarShipTaxCheck          = new CarShipTaxCheckQG();
            CarShipTaxCheckQGEncoder encoder            = new CarShipTaxCheckQGEncoder();
            CarShipTaxCheckQGDecoder decoder            = new CarShipTaxCheckQGDecoder();
            
            resultSet = dbpool.query("select TaxCheckNoQG_SEQ.nextval from dual"); 
            if(resultSet.next()){
                checkNo = ""+resultSet.getInt(1);
                resultSet.close();
            }  
            
            
            strPolicySQL = "SELECT COUNT(*), SUM(T.SUMPAYTAX) FROM PRPCCARSHIPTAXORIGIN T,PRPCMAIN M,ciinsurevalid D WHERE "
            			 
            			 + "T.TAXITEMCODE NOT IN('M','W') AND "
            			 
                         + "(T.TAXAMENDDECLARE is null or T.TAXAMENDDECLARE='0')   AND M.POLICYNO = T.POLICYNO AND D.POLICYNO = M.POLICYNO "
                         + "AND M.STARTDATE Between TO_DATE('"+startDate+"','YYYY-MM-DD') AND TO_DATE('"+endDate+"','YYYY-MM-DD') "
                         + "AND M.COMCODE in ( select comcode from prpdcompany where taxregisterno='"+taxregisterno+"') AND M.RISKCODE='0507'";
            
            
            logger.info("strPolicySQL:"+strPolicySQL);
            
            resultSet = dbpool.query(strPolicySQL);
            if(resultSet.next()){
                mPolicyCount = resultSet.getInt(1);
                mSumPolicyMoney = resultSet.getDouble(2);
            }  
            
            logger.info("XX公司XX总数： " + mPolicyCount);
            logger.info("XX公司XX车船税总金额： " + mSumPolicyMoney);
            
            
            
            
            strEndorseSQL = "SELECT COUNT(*), SUM(T.CHGSUMPAYTAX) FROM PRPPCARSHIPTAX T, PRPPHEAD M , ciendorvalid D WHERE "
			              
			   			  + "T.TAXITEMCODE NOT IN('M','W') AND "
			   			  
                          + " D.TAXAMENDPREMIUM<>0 AND M.underwriteflag in ('1','3') AND M.ENDORSENO = T.ENDORSENO AND D.ENDORSENO =M.ENDORSENO AND D.IsAmendTax='1' "
                          + "AND M.VALIDDATE Between TO_DATE('"+startDate+"','YYYY-MM-DD') and TO_DATE('"+endDate+"','YYYY-MM-DD') "
                          + "AND M.COMCODE in ( select comcode from prpdcompany where taxregisterno='"+taxregisterno+"')";
            
            
            logger.info("strEndorseSQL:"+strEndorseSQL);
            
            resultSet = dbpool.query(strEndorseSQL);
            if(resultSet.next()){
                mEndorseCount = resultSet.getInt(1);
                mSumEndorseMoney = resultSet.getDouble(2);
            }  
            
            logger.info("XX公司批单总数： " + mEndorseCount);
            logger.info("XX公司批单车船税总金额： " + mSumEndorseMoney);
            
            
            
            String strStartDate = correctDate(startDate);
            String strEndDate = correctDate(endDate);
            requestXML  = encoder.encode(comcode, strStartDate,strEndDate,taxregisterno);
            responseXML = EbaoProxy.getInstance().request(requestXML, comcode);
            responseXML = StringUtils.replace(responseXML, "GBK", "GB2312");
            
            logger.info("====对账发给ebao平台的XML串：" + requestXML);
            logger.info("====对账ebao平台返回的XML串：" + responseXML);
            
            decoder.decode(ZCarShipTaxCheck, responseXML);
            String strTPolicyCount  = ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).getTaxPolicyCount();
            String strTPolicyMoney  = ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).getTaxPolicyMoney();
            String strTEndorseCount = ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).getTaxAmendCount();
            String strTEndorseMoney = ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).getTaxAmendMoney();  
            if(strTPolicyCount==null || "".equals(strTPolicyCount))
                strTPolicyCount = "0";
            if(strTPolicyMoney==null || "".equals(strTPolicyMoney))
                strTPolicyMoney = "0";
            if(strTEndorseCount==null || "".equals(strTEndorseCount))
                strTEndorseCount ="0";
            if(strTEndorseMoney==null || "".equals(strTEndorseMoney))
                strTEndorseMoney="0";
            tPolicyCount    = Integer.parseInt(strTPolicyCount);
            tSumPolicyMoney = Double.parseDouble(strTPolicyMoney); 
            tEndorseCount   = Integer.parseInt(strTEndorseCount);
            tSumEndorseMoney   = Double.parseDouble(strTEndorseMoney);
            ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).setCheckNo(checkNo);
            ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).setComCode(comcode);
            ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).setStartDate(startDate);
            ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).setEndDate(endDate);
            ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).setMTaxPolicyCount(""+mPolicyCount);
            ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).setMTaxPolicyMoney(""+mSumPolicyMoney);
            ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).setMTaxAmendCount(""+mEndorseCount);
            ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).setMTaxAmendMoney(""+mSumEndorseMoney);
            ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).setOperateDate(nowDate);
            ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).setOperateCode(operateCode);
            ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).setTaxDeclearConfirmNo("");
            ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).setExtendChar1(taxregisterno);
            
            if((tPolicyCount==mPolicyCount)
                &&(tSumPolicyMoney==mSumPolicyMoney)
                &&(tEndorseCount==mEndorseCount)
                &&(tSumEndorseMoney==mSumEndorseMoney)){
                ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).setFlag("1");
            }
            else{
                ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).setFlag("0");
            }
            ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().save(dbpool);
            return checkNo;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    public String checkDetail(String taxDeclareQueryNo,String comCode)
    throws UserException, Exception {
        String iWherePart = "";
        DbPool dbpool     = new DbPool();
        String strReturn  = "0";
        String checkNo    = "";
        try{
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            BLPrpDcompanyFacade dcompany  = new BLPrpDcompanyFacade();
            PrpDcompanyDto prpDcompanyDto = dcompany.findByPrimaryKey(comCode);
            BLCICheckCarShipTaxDetailQG blCICheckCarShipTaxDetail = new BLCICheckCarShipTaxDetailQG();
            BLCICheckCarShipTaxQG     blCICheckCarShipTaxQG     = new BLCICheckCarShipTaxQG();
            
            iWherePart = " TaxDeclareQueryNo = '" + taxDeclareQueryNo + "'";
            blCICheckCarShipTaxDetail.query(dbpool,iWherePart);
            blCICheckCarShipTaxQG.query(dbpool,iWherePart);
            if(blCICheckCarShipTaxQG.getSize()>0)
                checkNo=blCICheckCarShipTaxQG.getArr(0).getCheckNo();
            if(checkNo.equals(""))
                return strReturn;
            if (blCICheckCarShipTaxDetail.getSize() > 0) {
                return "1";
            }
            
            strReturn = getCheckInfDedail(dbpool, taxDeclareQueryNo,checkNo,blCICheckCarShipTaxQG,comCode,prpDcompanyDto.getTaxRegisterNo());
            dbpool.commitTransaction();
        }catch (Exception e) {
            e.printStackTrace();
            dbpool.rollbackTransaction();
            throw e;
        }finally {
            dbpool.close();
        }
        return strReturn;
    }
    
    public  String getCheckInfDedail(DbPool dbpool, String taxDeclareQueryNo,String checkNo,BLCICheckCarShipTaxQG blCICheckCarShipTaxQG,String comCode,String taxregisterno) throws UserException,
    Exception{  
        try 
        {
            String sqlWhere      = "";
            String strSQL        = "";
            int    policyCount      = 0; 
            int    endorseCount     = 0; 
            int    policyPageCount  = 0; 
            int    endorsePageCount = 0; 
            double mTaxSumTax      = 0; 
            double TaxSumTax       = 0; 
            String  strMTaxSumTax    = ""; 
            String  strTaxSumTax     = ""; 
            
            String requestXML  = "";
            String responseXML = "";
            CarShipTaxCheckQGDedailEncoder  encoder              = new CarShipTaxCheckQGDedailEncoder();
            CarShipTaxCheckQGDedailDecoder  decoder              = new CarShipTaxCheckQGDedailDecoder();
            CarShipTaxCheckQG               DCarShipTaxCheckP    = new CarShipTaxCheckQG();
            CarShipTaxCheckQG               DCarShipTaxCheckE    = new CarShipTaxCheckQG();
            BLPrpCcarshipTaxOrigin          blPrpCcarshipTaxOrigin     = new BLPrpCcarshipTaxOrigin();
            PrpCcarshipTaxOriginSchema      prpCcarshipTaxTaxOriginSchema = null;
            BLPrpPcarshipTax                blPrpPcarshipTax     = new BLPrpPcarshipTax();
            PrpPcarshipTaxSchema            prpPcarshipTaxSchema = null;
            policyCount  = Integer.parseInt(blCICheckCarShipTaxQG.getArr(0).getMTaxPolicyCount());
            endorseCount = Integer.parseInt(blCICheckCarShipTaxQG.getArr(0).getMTaxAmendCount());
            String pageLength = AppConfig.get("sysconst.CI_INSURED_29_PAGE_LENGTH");
            if(policyCount>0){
                policyPageCount=policyCount/Integer.parseInt(pageLength);
                if(policyCount%Integer.parseInt(pageLength)>0)
                    policyPageCount=policyPageCount+1;
            }else{
                policyPageCount=0;
            }
            if(endorseCount>0){
                endorsePageCount=endorseCount/Integer.parseInt(pageLength);
                if(endorseCount%Integer.parseInt(pageLength)>0)
                    endorsePageCount=endorsePageCount+1;
            }else{
                endorsePageCount=0;
            }
            
            
            for(int i=1; i<=policyPageCount; i++)
            {
                requestXML  = encoder.encode(comCode,taxDeclareQueryNo,pageLength, ""+i, pageLength, "0");
                responseXML = EbaoProxy.getInstance().request(requestXML, comCode);
                responseXML = StringUtils.replace(responseXML, "GBK", "GB2312");
                decoder.decode(DCarShipTaxCheckP, responseXML);
                
            }
            for(int i=0; i<DCarShipTaxCheckP.getBlCICheckCarShipTaxDetailQG().getSize(); i++)
            {
                DCarShipTaxCheckP.getBlCICheckCarShipTaxDetailQG().getArr(i).setCheckNo(checkNo);
                sqlWhere = "policyno='" + DCarShipTaxCheckP.getBlCICheckCarShipTaxDetailQG().getArr(i).getCretiNo() + "' and (TAXAMENDDECLARE is null or TAXAMENDDECLARE='0')";
                blPrpCcarshipTaxOrigin.query(dbpool, sqlWhere);
                DCarShipTaxCheckP.getBlCICheckCarShipTaxDetailQG().getArr(i).setTaxDeclareQueryNo(taxDeclareQueryNo);
                
                if(blPrpCcarshipTaxOrigin.getSize()>0){
                    prpCcarshipTaxTaxOriginSchema = blPrpCcarshipTaxOrigin.getArr(0);
                    DCarShipTaxCheckP.getBlCICheckCarShipTaxDetailQG().getArr(i).setMTaxAnnualTaxDue(ChgData.chgStrZero(prpCcarshipTaxTaxOriginSchema.getTaxActual()));
                    DCarShipTaxCheckP.getBlCICheckCarShipTaxDetailQG().getArr(i).setMTaxSumOverDue(ChgData.chgStrZero(prpCcarshipTaxTaxOriginSchema.getLateFee()));                    
                    DCarShipTaxCheckP.getBlCICheckCarShipTaxDetailQG().getArr(i).setMTaxSumTax(prpCcarshipTaxTaxOriginSchema.getSumPayTax());
                    DCarShipTaxCheckP.getBlCICheckCarShipTaxDetailQG().getArr(i).setMTaxSumTaxDeFault(ChgData.chgStrZero(prpCcarshipTaxTaxOriginSchema.getPreviousPay()));
                    strTaxSumTax = DCarShipTaxCheckP.getBlCICheckCarShipTaxDetailQG().getArr(i).getTaxSumTax();
                    strMTaxSumTax = prpCcarshipTaxTaxOriginSchema.getSumPayTax();
                    if(strTaxSumTax==null||"".equals(strTaxSumTax))
                        strTaxSumTax="0";
                    if(strMTaxSumTax==null||"".equals(strMTaxSumTax))
                        strMTaxSumTax="0";
                    TaxSumTax  = Double.parseDouble(strTaxSumTax);
                    mTaxSumTax = Double.parseDouble(strMTaxSumTax);
                    if(TaxSumTax==mTaxSumTax)
                        DCarShipTaxCheckP.getBlCICheckCarShipTaxDetailQG().getArr(i).setExtendChar1("1");
                    else
                        DCarShipTaxCheckP.getBlCICheckCarShipTaxDetailQG().getArr(i).setExtendChar1("0");
                    
                    
                }
            }
            
            
            for(int i=1; i<=endorsePageCount; i++)
            {
                requestXML  = encoder.encode(comCode,taxDeclareQueryNo,pageLength, "0", pageLength, ""+i);
                responseXML = EbaoProxy.getInstance().request(requestXML, comCode);
                responseXML = StringUtils.replace(responseXML, "GBK", "GB2312");
                decoder.decode(DCarShipTaxCheckE, responseXML);
            }
            for(int i=0; i<DCarShipTaxCheckE.getBlCICheckCarShipTaxDetailQG().getSize(); i++)
            {
                DCarShipTaxCheckE.getBlCICheckCarShipTaxDetailQG().getArr(i).setCheckNo(checkNo);
                sqlWhere = "endorseno='" + DCarShipTaxCheckE.getBlCICheckCarShipTaxDetailQG().getArr(i).getCretiNo()+"'" ;
                blPrpPcarshipTax.query(dbpool, sqlWhere);
                DCarShipTaxCheckE.getBlCICheckCarShipTaxDetailQG().getArr(i).setTaxDeclareQueryNo(taxDeclareQueryNo);
                if(blPrpPcarshipTax.getSize()>0){
                    prpPcarshipTaxSchema = blPrpPcarshipTax.getArr(0);
                    DCarShipTaxCheckE.getBlCICheckCarShipTaxDetailQG().getArr(i).setMTaxAnnualTaxDue(ChgData.chgStrZero(prpPcarshipTaxSchema.getChgTaxActual()));
                    DCarShipTaxCheckE.getBlCICheckCarShipTaxDetailQG().getArr(i).setMTaxSumOverDue(ChgData.chgStrZero(prpPcarshipTaxSchema.getChgLateFee()));                    
                    DCarShipTaxCheckE.getBlCICheckCarShipTaxDetailQG().getArr(i).setMTaxSumTax(prpPcarshipTaxSchema.getChgSumPayTax());
                    DCarShipTaxCheckE.getBlCICheckCarShipTaxDetailQG().getArr(i).setMTaxSumTaxDeFault(ChgData.chgStrZero(prpPcarshipTaxSchema.getChgPreviousPay()));
                    strTaxSumTax = DCarShipTaxCheckE.getBlCICheckCarShipTaxDetailQG().getArr(i).getTaxSumTax();
                    strMTaxSumTax = prpPcarshipTaxSchema.getChgSumPayTax();
                    if(strTaxSumTax==null||"".equals(strTaxSumTax))
                        strTaxSumTax="0";
                    if(strMTaxSumTax==null||"".equals(strMTaxSumTax))
                        strMTaxSumTax="0";
                    TaxSumTax  = Double.parseDouble(strTaxSumTax);
                    mTaxSumTax = Double.parseDouble(strMTaxSumTax);
                    if(TaxSumTax==mTaxSumTax){
                        DCarShipTaxCheckE.getBlCICheckCarShipTaxDetailQG().getArr(i).setExtendChar1("1");
                    }
                    else{
                        DCarShipTaxCheckE.getBlCICheckCarShipTaxDetailQG().getArr(i).setExtendChar1("0");
                    }
                }else{
                    DCarShipTaxCheckE.getBlCICheckCarShipTaxDetailQG().getArr(i).setExtendChar1("0");
                }
            }

            DCarShipTaxCheckP.getBlCICheckCarShipTaxDetailQG().save(dbpool);
            DCarShipTaxCheckE.getBlCICheckCarShipTaxDetailQG().save(dbpool);
            
            
            strSQL = "INSERT INTO CICheckCarShipTaxDetailQG ( SELECT '"
                + checkNo+"'"
                + ",D.validno,'P',M.PROPOSALNO,M.POLICYNO,'2','','0','0','0','0',T.taxactual,T.previouspay,T.latefee,T.sumpaytax,'0','','"+taxDeclareQueryNo+"','','','','','','','','','' FROM PRPCCARSHIPTAXORIGIN T,PRPCMAIN M,  ciinsurevalid D WHERE "
                
                + "T.TAXITEMCODE NOT IN('M','W') AND "
                
                + " (T.TAXAMENDDECLARE is null or T.TAXAMENDDECLARE='0') AND M.RISKCODE='0507' AND M.POLICYNO = T.POLICYNO  AND D.POLICYNO = M.POLICYNO AND M.StartDate Between TO_DATE('"
                + blCICheckCarShipTaxQG.getArr(0).getStartDate()
                + "','YYYY-MM-DD') AND TO_DATE('"
                + blCICheckCarShipTaxQG.getArr(0).getEndDate()
                + "','YYYY-MM-DD') AND M.COMCODE in (select comcode from prpdcompany where taxregisterno='"+taxregisterno+"')"
                + " AND M.POLICYNO NOT IN (SELECT CretiNo FROM CICheckCarShipTaxDetailQG WHERE  taxDeclareQueryNo ='"
                + taxDeclareQueryNo + "'))";
            
            dbpool.insert(strSQL);
            
            
            
            strSQL = "INSERT INTO CICheckCarShipTaxDetailQG (SELECT '"
                + checkNo+"'"
                + ",D.validno,'E','',M.ENDORSENO,'2','','0','0','0','0',T.chgtaxactual,T.chgpreviouspay,T.chglatefee,T.chgsumpaytax,'0','','"+taxDeclareQueryNo+"','','','','','','','','','' FROM PRPPCARSHIPTAX T,PRPPHEAD M,  ciendorvalid D WHERE "
                
                + "T.TAXITEMCODE NOT IN('M','W') AND "
                
                + "  D.TAXAMENDPREMIUM<>0 AND M.RISKCODE='0507' AND M.ENDORSENO = T.ENDORSENO  AND M.underwriteflag in ('1','3') AND D.ENDORSENO = M.ENDORSENO AND M.VALIDDATE Between TO_DATE('"
                + blCICheckCarShipTaxQG.getArr(0).getStartDate()
                + "','YYYY-MM-DD') AND TO_DATE('"
                + blCICheckCarShipTaxQG.getArr(0).getEndDate()
                + "','YYYY-MM-DD') AND M.COMCODE in (select comcode from prpdcompany where taxregisterno='"+taxregisterno+"')" 
                + " AND M.Endorseno NOT IN (SELECT CretiNo FROM CICheckCarShipTaxDetailQG WHERE  taxDeclareQueryNo ='"
                + taxDeclareQueryNo + "'))";
            
            dbpool.insert(strSQL);
            return checkNo;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public String carShipTaxDataCompare(String comcode, String startDate, String endDate)
    throws UserException, Exception {
        String strReturn = "0";
        String compareNo = "";
        String iWherePart = "";
        String strSQL = "";
        DbPool dbpool = new DbPool();
        try 
        {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            BLCITaxDataCompareQG blCarShipTaxDataCompare = new BLCITaxDataCompareQG();
            BLPrpDcompanyFacade dcompany  = new BLPrpDcompanyFacade();
            PrpDcompanyDto prpDcompanyDto = dcompany.findByPrimaryKey(comcode);
            
            iWherePart =" EndDate >=to_date('"+startDate+"','yyyy-mm-dd')  AND StartDate <=to_date('"+endDate+"','yyyy-mm-dd') order by enddate desc";
            blCarShipTaxDataCompare.query(dbpool,iWherePart);
            if (blCarShipTaxDataCompare.getSize() > 0) {
                compareNo = blCarShipTaxDataCompare.getArr(0).getCompareNo();
                strSQL = " DELETE FROM CITaxDataCompareQG WHERE   EndDate >=to_date('"+startDate+"','yyyy-mm-dd')  AND StartDate <=to_date('"+endDate+"','yyyy-mm-dd') "; 
                dbpool.delete(strSQL);
            }
            
            compareNo = getDataCompareInf(dbpool, comcode, startDate, endDate, prpDcompanyDto.getTaxRegisterNo());
            if(!compareNo.equals("")){
                strReturn = "1";
            }else{
                strReturn = "0";
            }
            dbpool.commitTransaction();
        }catch (Exception e) {
            e.printStackTrace();
            dbpool.rollbackTransaction();
            throw e;
        }finally {
            dbpool.close();
        }
        return strReturn;
    }
    

    public  String getDataCompareInf(DbPool dbpool, String comcode,String startDate,String endDate,String taxregisterno) throws UserException,
    Exception{  
        String compareNo = "";  
        ResultSet resultSet = null;
        try 
        {
            String strPolicySQL    = "";
            String strEndorseSQL   = "";
            String strSQL          = "";
            ArrayList pConfirmNoList  = new ArrayList();
            ArrayList eConfirmNoList  = new ArrayList();
            String requestXML  = "";
            String responseXML = "";
            CarShipTaxCheckQG ZCarShipTaxCheck          = new CarShipTaxCheckQG();
            CarShipTaxDataCompareQGEncoder encoder      = new CarShipTaxDataCompareQGEncoder();
            CarShipTaxDataCompareQGDecoder decoder      = new CarShipTaxDataCompareQGDecoder();
            
            resultSet = dbpool.query("select TaxCompareNoQG_SEQ.nextval from dual"); 
            if(resultSet.next()){
                compareNo = ""+resultSet.getInt(1);
                resultSet.close();
            }  
            
            
            strPolicySQL = "SELECT d.validno FROM PRPCCARSHIPTAXORIGIN T,PRPCMAIN M,ciinsurevalid D WHERE "
		                 
		                 + "T.TAXITEMCODE NOT IN('M','W') AND "
		                 
                         + "(T.TAXAMENDDECLARE is null or T.TAXAMENDDECLARE = '0') AND M.POLICYNO = T.POLICYNO AND D.POLICYNO = M.POLICYNO "
                         + "AND M.STARTDATE Between TO_DATE('"+startDate+"','YYYY-MM-DD') AND TO_DATE('"+endDate+"','YYYY-MM-DD') "
                         + "AND M.COMCODE in (select comcode from prpdcompany where taxregisterno='"+taxregisterno+"') AND M.RISKCODE='0507' and d.validno is not null ";
            
            resultSet = dbpool.query(strPolicySQL);
            
            logger.info("strPolicySQL:"+strPolicySQL);
            
            while(resultSet.next()){
                pConfirmNoList.add(resultSet.getString(1));
            }  
            
            
            
            strEndorseSQL = "SELECT d.validno FROM PRPPCARSHIPTAX T, PRPPHEAD M , ciendorvalid D WHERE "
		            	  
		                  + "T.TAXITEMCODE NOT IN('M','W') AND "
		                  
                          + " D.TAXAMENDPREMIUM<>0 AND M.ENDORSENO = T.ENDORSENO AND M.underwriteflag in ('1','3') AND D.ENDORSENO =M.ENDORSENO AND D.IsAmendTax='1' "
                          + "AND M.VALIDDATE Between TO_DATE('"+startDate+"','YYYY-MM-DD') and TO_DATE('"+endDate+"','YYYY-MM-DD') "
                          + "AND M.COMCODE in (select comcode from prpdcompany where taxregisterno='"+taxregisterno+"') and d.validno is not null  and m.riskcode='0507'";
            
            
            logger.info("strEndorseSQL:"+strEndorseSQL);
            
            
            resultSet = dbpool.query(strEndorseSQL);
            while(resultSet.next()){
                eConfirmNoList.add(resultSet.getString(1));
            }  

            
            String strTaxToTalPayIC   = "";
            String strDeclareStatusIC = "";
            String certino    ="";
            String conFirmNo ="";
            requestXML  = encoder.encode(comcode,pConfirmNoList,eConfirmNoList);
            responseXML = EbaoProxy.getInstance().request(requestXML, comcode);
            responseXML = StringUtils.replace(responseXML, "GBK", "GB2312");
            decoder.decode(ZCarShipTaxCheck, responseXML);
            for(int i=0;i<ZCarShipTaxCheck.getBlCITaxDataCompareQG().getSize();i++){
                if("P".equals(ZCarShipTaxCheck.getBlCITaxDataCompareQG().getArr(i).getType())){
                    strSQL = " select D.policyno,D.validno,T.SUMPAYTAX,T.TAXAMENDDECLARE from ciinsurevalid D,PRPCCARSHIPTAXORIGIN T "
                           + " where D.validno='"+ZCarShipTaxCheck.getBlCITaxDataCompareQG().getArr(i).getConFirmNo()+"'"
                           + " and D.policyno=t.policyno";
                }else{
                    strSQL = " select D.endorseno,D.validno,T.CHGSUMPAYTAX,T.TAXAMENDDECLARE from ciendorvalid D,PRPPCARSHIPTAX T "
                           + " where D.validno='"+ZCarShipTaxCheck.getBlCITaxDataCompareQG().getArr(i).getConFirmNo()+"'"
                           + " and D.endorseno=t.endorseno";
                }
                
                resultSet = dbpool.query(strSQL);
                if(resultSet.next()){
                    certino            = resultSet.getString(1);
                    conFirmNo          = resultSet.getString(2);
                    strTaxToTalPayIC   = resultSet.getString(3);
                    if(strTaxToTalPayIC==null||"".equals(strTaxToTalPayIC))
                        strTaxToTalPayIC="0";
                    strDeclareStatusIC = resultSet.getString(4);
                    if(strDeclareStatusIC==null||"".equals(""))
                        strDeclareStatusIC="0";
       
                    
                }
                ZCarShipTaxCheck.getBlCITaxDataCompareQG().getArr(i).setCompareNo(compareNo);
                ZCarShipTaxCheck.getBlCITaxDataCompareQG().getArr(i).setConFirmNo(conFirmNo);
                ZCarShipTaxCheck.getBlCITaxDataCompareQG().getArr(i).setStartDate(startDate);
                ZCarShipTaxCheck.getBlCITaxDataCompareQG().getArr(i).setExtendChar1(certino);
                ZCarShipTaxCheck.getBlCITaxDataCompareQG().getArr(i).setExtendChar2(taxregisterno);
                ZCarShipTaxCheck.getBlCITaxDataCompareQG().getArr(i).setEndDate(endDate);
                ZCarShipTaxCheck.getBlCITaxDataCompareQG().getArr(i).setTaxToTalPayIC(strTaxToTalPayIC);
                ZCarShipTaxCheck.getBlCITaxDataCompareQG().getArr(i).setDeclareStatusIC(strDeclareStatusIC);
            }
            ZCarShipTaxCheck.getBlCITaxDataCompareQG().save(dbpool);
            return compareNo;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public BLCIAssemblePayNoQG carShipTaxPay(String comcode,String taxDeclareConfirmNo)
    throws UserException, Exception {
        CarShipTaxCheckQG ZCarShipTaxCheck          = new CarShipTaxCheckQG();
        CarShipTaxAssembleQGEncoder encoder      = new CarShipTaxAssembleQGEncoder();
        CarShipTaxAssembleQGDecoder decoder      = new CarShipTaxAssembleQGDecoder();
        try 
        {
            String requestXML  = "";
            String responseXML = "";
            requestXML  = encoder.encode(comcode,taxDeclareConfirmNo);
            responseXML = EbaoProxy.getInstance().request(requestXML, comcode);
            responseXML = StringUtils.replace(responseXML, "GBK", "GB2312");
            
            logger.info("====对账发给ebao平台的XML串：" + requestXML);
            logger.info("====对账ebao平台返回的XML串：" + responseXML);
            
            decoder.decode(ZCarShipTaxCheck, responseXML);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return ZCarShipTaxCheck.getBlCIAssemblePayNoQG();
       
    }
 
    public String carShipTaxCheckQGConfirm(String userCode,String comcode,String taxDeclareQueryNo)
    throws UserException, Exception {
        CarShipTaxCheckQG ZCarShipTaxCheck          = new CarShipTaxCheckQG();
        CarShipTaxCheckQGConfirmEncoder encoder     = new CarShipTaxCheckQGConfirmEncoder();
        CarShipTaxCheckQGConfirmDecoder decoder     = new CarShipTaxCheckQGConfirmDecoder();
        DbPool dbpool = new DbPool();
        String strSQL = "";
        try 
        {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            String requestXML  = "";
            String responseXML = "";
            requestXML  = encoder.encode(comcode,taxDeclareQueryNo);
            responseXML = EbaoProxy.getInstance().request(requestXML, comcode);
            responseXML = StringUtils.replace(responseXML, "GBK", "GB2312");
            
            logger.info("====对账发给ebao平台的XML串：" + requestXML);
            logger.info("====对账ebao平台返回的XML串：" + responseXML);
            
            decoder.decode(ZCarShipTaxCheck, responseXML);
            
            strSQL = "update CICheckCarShipTaxQG set TaxDeclearConfirmNo='"
                   + ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).getTaxDeclearConfirmNo()+"'"
                   + ",CorpDelayPay='"+ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).getCorpDelayPay()+"'"
                   + ",CorpDelayPayReason='"+ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).getCorpDelayPayReason()+"'"
                   + ",updateCode='"+userCode+"',ConfirmoperateDate=to_date(to_char(sysdate,'yyyy-mm-dd'),'yyyy-mm-dd')"
                   + " where TaxDeclareQueryNo ='"+taxDeclareQueryNo+"'";
            
            
            logger.info(strSQL);
            
            dbpool.update(strSQL);
            dbpool.commitTransaction();
        }catch (Exception e) {
            e.printStackTrace();
            dbpool.rollbackTransaction();
            throw e;
        }finally {
            dbpool.close();
        }
        return ZCarShipTaxCheck.getBlCICheckCarShipTaxQG().getArr(0).getTaxDeclearConfirmNo();
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
    
    
    public int[] carShipTaxDataCompareJZ(String startDate, String endDate, ArrayList arrayList)
    throws UserException, Exception {
        int[] number = new int[3];
        DbPool dbpool = new DbPool();
        try 
        {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            
            number = getDataCompareInfJZ(dbpool,startDate, endDate, arrayList);
            dbpool.commitTransaction();
        }catch (Exception e) {
            e.printStackTrace();
            dbpool.rollbackTransaction();
            throw e;
        }finally {
            dbpool.close();
        }
        return number;
    }
    
    public  int[] getDataCompareInfJZ(DbPool dbpool,String startDate,String endDate,
    		ArrayList arrayList) throws UserException,Exception{  
        ResultSet resultSet = null;
        String comcode = "";
        int[] number = new int[3];
        int sum=0,success=0,fail=0;
        CICheckCarShipTaxDetailQGSchema ciCheckCarShipTaxDetailQGSchema = (CICheckCarShipTaxDetailQGSchema)arrayList.get(0);
        comcode = ciCheckCarShipTaxDetailQGSchema.getComCode();
        double strAnnualTaxDue   = 0;
        double strSumTaxDefault  = 0;
        double strSumOverdue     = 0;
        double strSumTax         = 0;
        double newAnnualTaxDue   = 0;
        double newSumTaxDefault  = 0;
        double newSumOverdue     = 0;
        double newSumTax         = 0;
        String checkSuccessFlag  = "";
        String extendFlag1       = "";
        String extendFlag2       = "";
        try 
        {
            StringBuffer strWhere_buffer = new StringBuffer(200);
            String strSQL          = "";
            ArrayList pConfirmNoList  = new ArrayList();
            ArrayList eConfirmNoList  = new ArrayList();
            String requestXML  = "";
            String responseXML = "";
            CarShipTaxCheckQG ZCarShipTaxCheck          = new CarShipTaxCheckQG();
            CarShipTaxDataCompareQGEncoder encoder      = new CarShipTaxDataCompareQGEncoder();
            CarShipTaxDataCompareQGDecoder decoder      = new CarShipTaxDataCompareQGDecoder();
            
            
            strWhere_buffer.append("SELECT C.ConFirmSequenceNo,C.CretiNo,C.type FROM CICheckCarShipTaxDetailQG C WHERE "
            				+ "substr(C.SIGNDATE,1,10) Between TO_DATE('"+startDate+"','YYYY-MM-DD') AND TO_DATE('"+endDate+"','YYYY-MM-DD') ");
            
            if(!"".equals(ciCheckCarShipTaxDetailQGSchema.getConFirmSequenceNo())){
            	strWhere_buffer.append("and C.ConFirmSequenceNo = '"+ciCheckCarShipTaxDetailQGSchema.getConFirmSequenceNo()+"' ");
            }
            if(!"".equals(ciCheckCarShipTaxDetailQGSchema.getCretiNo())){
            	strWhere_buffer.append("and C.CretiNo = '"+ciCheckCarShipTaxDetailQGSchema.getCretiNo()+"' ");
            }
            if(!"".equals(ciCheckCarShipTaxDetailQGSchema.getLicenseNo())){
            	strWhere_buffer.append("and C.LicenseNo = '"+ciCheckCarShipTaxDetailQGSchema.getLicenseNo()+"' ");
            }
            if(!"".equals(ciCheckCarShipTaxDetailQGSchema.getFrameNo())){
            	strWhere_buffer.append("and C.FrameNo = '"+ciCheckCarShipTaxDetailQGSchema.getFrameNo()+"' ");
            }
            if(!"".equals(ciCheckCarShipTaxDetailQGSchema.getEngineNo())){
            	strWhere_buffer.append("and C.EngineNo = '"+ciCheckCarShipTaxDetailQGSchema.getEngineNo()+"' ");
            }
            if(!"".equals(ciCheckCarShipTaxDetailQGSchema.getComCode())){
            	strWhere_buffer.append("and C.ComCode = '"+ciCheckCarShipTaxDetailQGSchema.getComCode()+"' ");
            }
            if(!"".equals(ciCheckCarShipTaxDetailQGSchema.getCheckSuccessFlag())){
            	strWhere_buffer.append("and C.CheckSuccessFlag = '"+ciCheckCarShipTaxDetailQGSchema.getCheckSuccessFlag()+"' ");
            }
            resultSet = dbpool.query(strWhere_buffer.toString());
            while(resultSet.next()){
            	sum++;
            	if("E".equals(resultSet.getString(3))){
            		eConfirmNoList.add(resultSet.getString(1));
            	}else{
            		pConfirmNoList.add(resultSet.getString(1));
            	}
            }
            if(sum!=0){
            
            requestXML  = encoder.encode(comcode,pConfirmNoList,eConfirmNoList);
            
            logger.info("业务号对账发送 requestXML===="+requestXML);
            
            responseXML = EbaoProxy.getInstance().request(requestXML, comcode);
            responseXML = StringUtils.replace(responseXML, "GBK", "GB2312");
            
            logger.info("业务号对账返回 responseXML===="+responseXML);
            
            
            decoder.decodeJZ(dbpool,ZCarShipTaxCheck, responseXML,comcode);
            
            DBCICheckCarShipTaxDetailQG dbciCheckCarShipTaxDetailQG = null;
            for(int i=0;i<ZCarShipTaxCheck.getBlCICheckCarShipTaxDetailQG().getSize();i++){
            	dbciCheckCarShipTaxDetailQG = new DBCICheckCarShipTaxDetailQG();
            	dbciCheckCarShipTaxDetailQG.getInfo(dbpool, ZCarShipTaxCheck.getBlCICheckCarShipTaxDetailQG().getArr(i).getConFirmSequenceNo());
            	if(ZCarShipTaxCheck.getBlCICheckCarShipTaxDetailQG().getArr(i).getErrorMsg()!=""&&
            			ZCarShipTaxCheck.getBlCICheckCarShipTaxDetailQG().getArr(i).getErrorMsg()!=null){
            		dbciCheckCarShipTaxDetailQG.setErrorMsg(ZCarShipTaxCheck.getBlCICheckCarShipTaxDetailQG().getArr(i).getErrorMsg());
            		dbciCheckCarShipTaxDetailQG.setCheckSuccessFlag("1");
            		break;
            	}else {
            		strAnnualTaxDue    = Double.parseDouble(dbciCheckCarShipTaxDetailQG.getMTaxAnnualTaxDue());
            		strSumTaxDefault   = Double.parseDouble(dbciCheckCarShipTaxDetailQG.getMTaxSumTaxDeFault());
            		strSumOverdue      = Double.parseDouble(dbciCheckCarShipTaxDetailQG.getMTaxSumOverDue());
            		strSumTax          = Double.parseDouble(dbciCheckCarShipTaxDetailQG.getMTaxSumTax());
            		newAnnualTaxDue = Double.parseDouble(ZCarShipTaxCheck.getBlCICheckCarShipTaxDetailQG().getArr(i).getTaxAnnualTaxDue());
            		newSumTaxDefault = Double.parseDouble(ZCarShipTaxCheck.getBlCICheckCarShipTaxDetailQG().getArr(i).getTaxSumTaxDeFault());
            		newSumOverdue = Double.parseDouble(ZCarShipTaxCheck.getBlCICheckCarShipTaxDetailQG().getArr(i).getTaxSumOverDue());
            		newSumTax = Double.parseDouble(ZCarShipTaxCheck.getBlCICheckCarShipTaxDetailQG().getArr(i).getTaxSumTax());
            		extendFlag1 = ZCarShipTaxCheck.getBlCICheckCarShipTaxDetailQG().getArr(i).getExtendChar1();
            		extendFlag2 = ZCarShipTaxCheck.getBlCICheckCarShipTaxDetailQG().getArr(i).getExtendChar2();
            		boolean flag = true;
            		if(strAnnualTaxDue!=0 && newAnnualTaxDue!=0 && strAnnualTaxDue != newAnnualTaxDue){
            			flag = false;
            		}
            		if(strSumTaxDefault!=0 && newSumTaxDefault!=0 && strSumTaxDefault != newSumTaxDefault){
            			flag = false;
            		}
	                if(strSumOverdue!=0 && newSumOverdue!=0 && strSumOverdue != newSumOverdue){
	                	flag = false;
	                }
	                if(strSumTax!=0 && newSumTax!=0 && strSumTax != newSumTax){
	                	flag = false;
	                }
	                if(flag){
	                	success++;
	                	checkSuccessFlag = "2";
	                }else{
	                	fail++;
	                	checkSuccessFlag = "1";
	                }
	                dbciCheckCarShipTaxDetailQG.setTaxAnnualTaxDue(newAnnualTaxDue+"");
	                dbciCheckCarShipTaxDetailQG.setTaxSumTaxDeFault(newSumTaxDefault+"");
	                dbciCheckCarShipTaxDetailQG.setTaxSumOverDue(strSumOverdue+"");
	                dbciCheckCarShipTaxDetailQG.setTaxSumTax(newSumTax+"");
	                dbciCheckCarShipTaxDetailQG.setCheckSuccessFlag(checkSuccessFlag);
	                dbciCheckCarShipTaxDetailQG.setExtendChar1(extendFlag1);
	                dbciCheckCarShipTaxDetailQG.setExtendChar2(extendFlag2);
            	}
                dbciCheckCarShipTaxDetailQG.update(dbpool);
            }
            }
            number[0] = sum;
            number[1] = success;
            number[2] = fail;
            return number;
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
}
