package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJreserve;
import com.sp.prpall.schema.PrpJreserveSchema;
import com.sp.prpall.schema.PrpJreserveDetailSchema;
import com.sp.account.blsvr.*;
import com.sp.account.schema.*;
import com.sp.utiall.blsvr.BLPrpDcompany;
import com.sp.utility.string.*;
import com.sp.prpall.pubfun.PubTools;

/**
 * 定义PrpJreserve的BL类
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * 
 * @createdate 2003-12-12
 *             </p>
 * @Author : X
 * @version 1.0
 */
public class BLPrpJreserve {
    private Vector schemas = new Vector();

    private String BACKDATAFLAG = "1"; 

    private String LNRETURN = "<BR>";

    private String CurrencyDest = "CNY"; 

    
    private String[] riskCode = new String[] { "0898", "0198", "1599", "0199",
            "0899", "1099", "1198", "1199", "1798" ,"1999" };

    private String[] arrInRiskCode = new String[] { "XZ0898", "XZ0198", "XZ1599",
            "XZ0199", "XZ0899", "XZ1099", "XZ0198", "XZ0199", "XZ1798","XZ1999" };
    protected final Log logger = LogFactory.getLog(getClass());
    /**
     * 构造函数
     */
    public BLPrpJreserve() {
    }

    /**
     * 初始化记录
     * 
     * @param 无
     * @return 无
     * @throws Exception
     */
    public void initArr() throws Exception {
        schemas = new Vector();
    }

    /**
     * 增加一条PrpJreserveSchema记录
     * 
     * @param iPrpJreserveSchema
     *            PrpJreserveSchema
     * @throws Exception
     */
    public void setArr(PrpJreserveSchema iPrpJreserveSchema) throws Exception {
        try {
            schemas.add(iPrpJreserveSchema);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @desc 替换一条PrpJreserveSchema记录
     * @Author : X
     * @param iPrpJreserveSchema
     *            PrpJreserveSchema
     * @param index
     *            索引值
     * @throws Exception
     */
    public void setArr(PrpJreserveSchema iPrpJreserveSchema, int index)
            throws Exception {
        try {
            schemas.setElementAt(iPrpJreserveSchema, index);
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * 得到一条PrpJreserveSchema记录
     * 
     * @param index
     *            下标
     * @return 一个PrpJreserveSchema对象
     * @throws Exception
     */
    public PrpJreserveSchema getArr(int index) throws Exception {
        PrpJreserveSchema prpJreserveSchema = null;
        try {
            prpJreserveSchema = (PrpJreserveSchema) this.schemas.get(index);
        } catch (Exception e) {
            throw e;
        }
        return prpJreserveSchema;
    }

    /**
     * 删除一条PrpJreserveSchema记录
     * 
     * @param index
     *            下标
     * @throws Exception
     */
    public void remove(int index) throws Exception {
        try {
            this.schemas.remove(index);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 得到schemas记录数
     * 
     * @return schemas记录数
     * @throws Exception
     */
    public int getSize() throws Exception {
        return this.schemas.size();
    }

    /**
     * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     * 
     * @param iWherePart
     *            查询条件(包括排序字句)
     * @throws UserException
     * @throws Exception
     */
    public void query(String iWherePart) throws UserException, Exception {
        this.query(iWherePart, Integer.parseInt(SysConfig.getProperty(
                "QUERY_LIMIT_COUNT").trim()));
    }

    /**
     * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     * 
     * @param iWherePart
     *            查询条件(包括排序字句)
     * @param iLimitCount
     *            记录数限制(iLimitCount=0: 无限制)
     * @throws UserException
     * @throws Exception
     */
    public void query(String iWherePart, int iLimitCount) throws UserException,
            Exception {
        String strSqlStatement = "";
        DBPrpJreserve dbPrpJreserve = new DBPrpJreserve();
        if (iLimitCount > 0 && dbPrpJreserve.getCount(iWherePart) > iLimitCount) {
            throw new UserException(-98, -1003, "BLPrpJreserve.query");
        } else {
            initArr();
            strSqlStatement = " SELECT * FROM PrpJreserve WHERE " + iWherePart;
            schemas = dbPrpJreserve.findByConditions(strSqlStatement);
        }
    }

    /**
     * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     * 
     * @param dbpool
     *            全局池
     * @param iWherePart
     *            查询条件(包括排序字句)
     * @throws UserException
     * @throws Exception
     */
    public void query(DbPool dbpool, String iWherePart) throws UserException,
            Exception {
        this.query(dbpool, iWherePart, Integer.parseInt(SysConfig.getProperty(
                "QUERY_LIMIT_COUNT").trim()));
    }

    /**
     * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     * 
     * @param dbpool
     *            全局池
     * @param iWherePart
     *            查询条件(包括排序字句)
     * @param iLimitCount
     *            记录数限制(iLimitCount=0: 无限制)
     * @throws UserException
     * @throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount)
            throws UserException, Exception {
        String strSqlStatement = "";
        DBPrpJreserve dbPrpJreserve = new DBPrpJreserve();
        if (iLimitCount > 0 && dbPrpJreserve.getCount(iWherePart) > iLimitCount) {
            throw new UserException(-98, -1003, "BLPrpJreserve.query");
        } else {
            initArr();
            strSqlStatement = " SELECT * FROM PrpJreserve WHERE " + iWherePart;
            schemas = dbPrpJreserve.findByConditions(dbpool, strSqlStatement);
        }
    }

    /**
     * 带dbpool的save方法
     * 
     * @param 无
     * @return 无
     */
    public void save(DbPool dbpool) throws Exception {
        DBPrpJreserve dbPrpJreserve = new DBPrpJreserve();

        int i = 0;

        for (i = 0; i < schemas.size(); i++) {
            dbPrpJreserve.setSchema((PrpJreserveSchema) schemas.get(i));
            dbPrpJreserve.insert(dbpool);
        }
    }

    /**
     * 不带dbpool的XXXXX存方法
     * 
     * @param 无
     * @return 无
     */
    public void save() throws Exception {
        DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            save(dbpool);
            dbpool.commitTransaction();
        } catch (Exception e) {
            dbpool.rollbackTransaction();
        } finally {
            dbpool.close();
        }
    }

    /**
     * @desc 计算长期责任准备金和本期到期XX的利润
     * @Author : X
     * @param iReserveTerm
     *            本期期间
     * @param iBeginDate
     *            本期起始日期
     * @param iEndDate
     *            下期起始日期
     * @return 无
     */
    public void getReserveMoney(String iReserveTerm, String iBeginDate,
            String iEndDate, String iReserveWhere) throws Exception {
        BLPrpJreserveDetail blPrpJreserveDetail = new BLPrpJreserveDetail();
        DBPrpJreserve dbPrpJreserve = new DBPrpJreserve();
        PrpJreserveDetailSchema detailSchema = null;
        PrpJreserveSchema schema = null;
        String strWherePart = "";
        double dblReserveMoney = 0.0;

        try {
            
            
            strWherePart = "EndDate>='" + iEndDate + "' AND StartDate<'"
                    + iEndDate + "' AND ReserveTerm='" + iReserveTerm
                    + "' AND " + iReserveWhere;
            blPrpJreserveDetail.query(strWherePart);
            for (int i = 0; i < blPrpJreserveDetail.getSize(); i++) {
                detailSchema = new PrpJreserveDetailSchema();
                detailSchema = blPrpJreserveDetail.getArr(i);
                dblReserveMoney = 0.0;
                dblReserveMoney = (Double.parseDouble(detailSchema
                        .getSumPremium()) - Double.parseDouble(detailSchema
                        .getTotalRealPayFee()))
                        * Double.parseDouble(detailSchema.getRetentionRate())
                        - Double.parseDouble(detailSchema.getTotalFee());
                detailSchema.setThisFee("" + dblReserveMoney); 
                blPrpJreserveDetail.setArr(detailSchema, i);
            }

            
            for (int i = 0; i < blPrpJreserveDetail.getSize(); i++) {
                detailSchema = new PrpJreserveDetailSchema();
                detailSchema = blPrpJreserveDetail.getArr(i);
                dblReserveMoney = Double.parseDouble(detailSchema.getThisFee());
                for (int j = i + 1; j < blPrpJreserveDetail.getSize(); j++) {
                    PrpJreserveDetailSchema schemaTmp = new PrpJreserveDetailSchema();
                    schemaTmp = blPrpJreserveDetail.getArr(j);
                    if (detailSchema.getRiskCode().equals(
                            schemaTmp.getRiskCode())
                            && detailSchema.getCurrency().equals(
                                    schemaTmp.getCurrency())
                            && detailSchema.getLimitYear().equals(
                                    schemaTmp.getLimitYear())
                            && detailSchema.getReserveTerm().equals(
                                    schemaTmp.getReserveTerm())
                            && detailSchema.getComCode().substring(0, 4)
                                    .equals(
                                            schemaTmp.getComCode().substring(0,
                                                    4))) {
                        dblReserveMoney += Double.parseDouble(schemaTmp
                                .getThisFee());
                        blPrpJreserveDetail.remove(j);
                        j--;
                    }
                }

                schema = new PrpJreserveSchema();
                schema.setClassCode(detailSchema.getClassCode());
                schema.setRiskCode(detailSchema.getRiskCode());
                schema.setLimitYear(detailSchema.getLimitYear());
                schema.setReserveTerm(detailSchema.getReserveTerm());
                schema.setCurrency(detailSchema.getCurrency());
                schema.setReserveMoney("" + dblReserveMoney);
                schema.setComCode(detailSchema.getComCode().substring(0, 4)
                        + "0000");
                schema.setProfit("0.0");
                this.setArr(schema);
            }

            
            strWherePart = "EndDate<'" + iEndDate + "' AND EndDate>='"
                    + iBeginDate + "' AND ReserveTerm='" + iReserveTerm
                    + "' AND " + iReserveWhere;
            blPrpJreserveDetail.query(strWherePart);
            for (int i = 0; i < blPrpJreserveDetail.getSize(); i++) {
                detailSchema = new PrpJreserveDetailSchema();
                detailSchema = blPrpJreserveDetail.getArr(i);
                dblReserveMoney = 0.0;
                dblReserveMoney = (Double.parseDouble(detailSchema
                        .getTotalRealPremium()) - Double
                        .parseDouble(detailSchema.getTotalRealPayFee()))
                        * Double.parseDouble(detailSchema.getRetentionRate())
                        - Double.parseDouble(detailSchema.getTotalFee());
                detailSchema.setThisFee("" + dblReserveMoney); 
                blPrpJreserveDetail.setArr(detailSchema, i);
            }

            
            for (int i = 0; i < blPrpJreserveDetail.getSize(); i++) {
                detailSchema = new PrpJreserveDetailSchema();
                detailSchema = blPrpJreserveDetail.getArr(i);
                dblReserveMoney = Double.parseDouble(detailSchema.getThisFee());
                for (int j = i + 1; j < blPrpJreserveDetail.getSize(); j++) {
                    PrpJreserveDetailSchema schemaTmp = new PrpJreserveDetailSchema();
                    schemaTmp = blPrpJreserveDetail.getArr(j);
                    if (detailSchema.getRiskCode().equals(
                            schemaTmp.getRiskCode())
                            && detailSchema.getCurrency().equals(
                                    schemaTmp.getCurrency())
                            && detailSchema.getLimitYear().equals(
                                    schemaTmp.getLimitYear())
                            && detailSchema.getReserveTerm().equals(
                                    schemaTmp.getReserveTerm())
                            && detailSchema.getComCode().substring(0, 4)
                                    .equals(
                                            schemaTmp.getComCode().substring(0,
                                                    4))) {
                        dblReserveMoney += Double.parseDouble(schemaTmp
                                .getThisFee());
                        blPrpJreserveDetail.remove(j);
                        j--;
                    }
                }

                schema = new PrpJreserveSchema();
                schema.setClassCode(detailSchema.getClassCode());
                schema.setRiskCode(detailSchema.getRiskCode());
                schema.setLimitYear(detailSchema.getLimitYear());
                schema.setReserveTerm(detailSchema.getReserveTerm());
                schema.setCurrency(detailSchema.getCurrency());
                schema.setReserveMoney("0.0");
                schema.setProfit("" + dblReserveMoney);
                schema.setComCode(detailSchema.getComCode().substring(0, 4)
                        + "0000");
                this.setArr(schema);
            }

            
            strWherePart = "EndDate<'" + iBeginDate + "' AND ReserveTerm='"
                    + iReserveTerm + "' AND " + iReserveWhere;
            blPrpJreserveDetail.query(strWherePart);
            for (int i = 0; i < blPrpJreserveDetail.getSize(); i++) {
                detailSchema = new PrpJreserveDetailSchema();
                detailSchema = blPrpJreserveDetail.getArr(i);
                dblReserveMoney = 0.0;
                dblReserveMoney = (Double.parseDouble(detailSchema
                        .getThisRealPremium()) - Double
                        .parseDouble(detailSchema.getThisRealPayFee()))
                        * Double.parseDouble(detailSchema.getRetentionRate())
                        - Double.parseDouble(detailSchema.getThisFee());
                detailSchema.setThisFee("" + dblReserveMoney); 
                blPrpJreserveDetail.setArr(detailSchema, i);
            }

            
            for (int i = 0; i < blPrpJreserveDetail.getSize(); i++) {
                detailSchema = new PrpJreserveDetailSchema();
                detailSchema = blPrpJreserveDetail.getArr(i);
                dblReserveMoney = Double.parseDouble(detailSchema.getThisFee());
                for (int j = i + 1; j < blPrpJreserveDetail.getSize(); j++) {
                    PrpJreserveDetailSchema schemaTmp = new PrpJreserveDetailSchema();
                    schemaTmp = blPrpJreserveDetail.getArr(j);
                    if (detailSchema.getRiskCode().equals(
                            schemaTmp.getRiskCode())
                            && detailSchema.getCurrency().equals(
                                    schemaTmp.getCurrency())
                            && detailSchema.getLimitYear().equals(
                                    schemaTmp.getLimitYear())
                            && detailSchema.getReserveTerm().equals(
                                    schemaTmp.getReserveTerm())
                            && detailSchema.getComCode().substring(0, 4)
                                    .equals(
                                            schemaTmp.getComCode().substring(0,
                                                    4))) {
                        dblReserveMoney += Double.parseDouble(schemaTmp
                                .getThisFee());
                        blPrpJreserveDetail.remove(j);
                        j--;
                    }
                }

                schema = new PrpJreserveSchema();
                schema.setClassCode(detailSchema.getClassCode());
                schema.setRiskCode(detailSchema.getRiskCode());
                schema.setLimitYear(detailSchema.getLimitYear());
                schema.setReserveTerm(detailSchema.getReserveTerm());
                schema.setCurrency(detailSchema.getCurrency());
                schema.setReserveMoney("0.0");
                schema.setProfit("" + dblReserveMoney);
                schema.setComCode(detailSchema.getComCode().substring(0, 4)
                        + "0000");
                this.setArr(schema);
            }

            
            double dblProfit = 0.0;
            for (int i = 0; i < this.getSize(); i++) {
                schema = new PrpJreserveSchema();
                schema = this.getArr(i);
                dblReserveMoney = Double.parseDouble(schema.getReserveMoney());
                dblProfit = Double.parseDouble(schema.getProfit());

                for (int j = i + 1; j < this.getSize(); j++) {
                    PrpJreserveSchema schemaTmp = new PrpJreserveSchema();
                    schemaTmp = this.getArr(j);
                    if (schema.getRiskCode().equals(schemaTmp.getRiskCode())
                            && schema.getCurrency().equals(
                                    schemaTmp.getCurrency())
                            && schema.getLimitYear().equals(
                                    schemaTmp.getLimitYear())
                            && schema.getReserveTerm().equals(
                                    schemaTmp.getReserveTerm())
                            && schema.getComCode().equals(
                                    schemaTmp.getComCode())) {
                        dblReserveMoney += Double.parseDouble(schemaTmp
                                .getReserveMoney());
                        dblProfit += Double.parseDouble(schemaTmp.getProfit());
                        this.remove(j);
                        j--;
                    }
                }
                schema.setReserveMoney("" + dblReserveMoney);
                schema.setProfit("" + dblProfit);
                this.setArr(schema, i);
            }

            
            strWherePart = " ReserveTerm='" + iReserveTerm + "' AND "
                    + iReserveWhere;
            this.saveByCancel(strWherePart);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 带dbpool的saveByCancel方法
     * 
     * @Author : X
     * @param dbpool
     *            数据库连接池
     * @param iWherePart
     *            删除条件
     * @return 无
     */
    public void saveByCancel(DbPool dbpool, String iWherePart) throws Exception {
        DBPrpJreserve dbPrpJreserve = new DBPrpJreserve();
        try {
            String strSQL = "DELETE FROM PrpJreserve WHERE " + iWherePart;
            dbpool.delete(strSQL);
            for (int i = 0; i < schemas.size(); i++) {
                dbPrpJreserve.setSchema((PrpJreserveSchema) schemas.get(i));
                dbPrpJreserve.insert(dbpool);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 不带dbpool的saveByCancel方法
     * 
     * @Author : X
     * @param iWherePart
     *            删除条件
     * @return 无
     */
    public void saveByCancel(String iWherePart) throws Exception {
        DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            saveByCancel(dbpool, iWherePart);
            dbpool.commitTransaction();
        } catch (Exception e) {
            dbpool.rollbackTransaction();
            throw e;
        } finally {
            dbpool.close();
        }
    }

    /**
     * 不带dbpool的saveByCancel方法
     * 
     * @Author : X
     * @param iWherePart
     *            删除条件
     * @return 无
     */
    public String getMaxReserveTerm(String iWherePart) throws Exception {
        DbPool dbpool = new DbPool();
        String strSQL = "SELECT MAX(ReserveTerm) FROM PrpJreserve WHERE "
                + iWherePart;
        String maxReserveTerm = "";

        try {
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            ResultSet rs = dbpool.query(strSQL);
            while (rs.next()) {
                maxReserveTerm = rs.getString(1);
            }
            rs.close();
            if (maxReserveTerm == null || maxReserveTerm.equals("null"))
                maxReserveTerm = "";
        } catch (Exception e) {
            throw e;
        } finally {
            dbpool.close();
        }
        return maxReserveTerm;
    }

    /**
     * @desc 未到期责任准备金送XXXXX凭证
     * @author lijibin 20060403
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transAccount(String iAccBookType, String iAccBookCode,
            String iCenterCode, String iDrawDate, String iOperatorCode,String iDrawType)
            throws Exception {
        String strReturn = "";
        String strSingReturn = "";
        ArrayList arrCenterCode = new ArrayList();
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        if (iCenterCode == null || iCenterCode.equals("")) {
            blPrpDcompany.query(" CenterFlag='1'", 0);
            for (int i = 0; i < blPrpDcompany.getSize(); i++)
                arrCenterCode.add(blPrpDcompany.getArr(i).getComCode());
        } else {
            arrCenterCode.add(iCenterCode);
        }
        DbPool dbpool = new DbPool();
        DbPool statDBpool = new DbPool();        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            statDBpool.open(SysConst.getProperty("STATDATASOURCE"));
            for (int i = 0; i < arrCenterCode.size(); i++) {
                iCenterCode = (String) arrCenterCode.get(i);
                
                if(iDrawType.equals("1")||iDrawType.equals("0")){
                    try{
                        dbpool.beginTransaction();
                        this.logPrintln("开始转入"+iCenterCode+"未到期责任准备金");
                        
                        strSingReturn = this.transAccount1(dbpool,statDBpool,iAccBookType,
                                iAccBookCode, iCenterCode, iDrawDate, iOperatorCode);
                        strReturn += strSingReturn+ this.LNRETURN;
                        this.logPrintln(strSingReturn);
                        this.logPrintln("开始转入"+iCenterCode+" 应收分XXXXX未到期责任准备金");
                        
                        strSingReturn = this.transAccount2(dbpool,statDBpool, iAccBookType,
                                iAccBookCode, iCenterCode, iDrawDate, iOperatorCode);
                        strReturn += strSingReturn+ this.LNRETURN;
                        this.logPrintln(strSingReturn);
                        dbpool.commitTransaction();                   
                        } catch(Exception e){
                        	dbpool.rollbackTransaction();
                        	e.printStackTrace();
                        	} 
                }
                
                if(iDrawType.equals("2")||iDrawType.equals("0")){
                    try{
                        dbpool.beginTransaction();
                        this.logPrintln("开始转入"+iCenterCode+" 未决赔款准备金-直接业务-已发生已报告");
                        
                        strSingReturn = this.transLossAccount1(dbpool,statDBpool, iAccBookType,
                                iAccBookCode, iCenterCode, iDrawDate, iOperatorCode);
                        strReturn += strSingReturn+ this.LNRETURN;
                        this.logPrintln(strSingReturn);
                        this.logPrintln("开始转入"+iCenterCode+" 未决赔款准备金-分入业务-已发生已报告");
                        
                        strSingReturn = this.transLossAccount2(dbpool,statDBpool, iAccBookType,
                                iAccBookCode, iCenterCode, iDrawDate, iOperatorCode);
                        strReturn += strSingReturn+ this.LNRETURN;
                        this.logPrintln(strSingReturn);
                        this.logPrintln("开始转入"+iCenterCode+" 应收分XXXXX未决赔款准备金-已发生已报告");
                        
                        strSingReturn = this.transLossAccount3(dbpool,statDBpool, iAccBookType,
                                iAccBookCode, iCenterCode, iDrawDate, iOperatorCode);
                        strReturn += strSingReturn+ this.LNRETURN;
                        this.logPrintln(strSingReturn);
                        dbpool.commitTransaction();
                    }catch(Exception e){
                    	dbpool.rollbackTransaction();
                    	e.printStackTrace();
                    }
                }
                
                if(iDrawType.equals("3")||iDrawType.equals("0")){
                    try{
                        dbpool.beginTransaction();
                        this.logPrintln("开始转入"+iCenterCode+" 未决赔款准备金-已发生未报告");
                        
                        strSingReturn = this.transIBNRAccount1(dbpool,statDBpool, iAccBookType, iAccBookCode,
    									iCenterCode, iDrawDate, iOperatorCode);
                        strReturn += strSingReturn+ this.LNRETURN;
                        this.logPrintln(strSingReturn);
    					this.logPrintln("开始转入"+iCenterCode+" 应收分XXXXX未决赔款准备金-已发生未报告");
    					
    					strSingReturn = this.transIBNRAccount2(dbpool,statDBpool, iAccBookType, iAccBookCode,
    									iCenterCode, iDrawDate, iOperatorCode);
                        strReturn += strSingReturn+ this.LNRETURN;
                        this.logPrintln(strSingReturn);
    					this.logPrintln("开始转入"+iCenterCode+" 未决赔款准备金-XXXXX费用");
    					
    					strSingReturn = this.transClaimsExpenses1(dbpool,statDBpool, iAccBookType, iAccBookCode,
    									iCenterCode, iDrawDate, iOperatorCode);
                        strReturn += strSingReturn+ this.LNRETURN;
                        this.logPrintln(strSingReturn);
    					this.logPrintln("开始转入"+iCenterCode+" 应收分XXXXX未决赔款准备金-XXXXX费用");
    					
    					strSingReturn = this.transClaimsExpenses2(dbpool,statDBpool, iAccBookType, iAccBookCode,
    									iCenterCode, iDrawDate, iOperatorCode);
                        strReturn += strSingReturn+ this.LNRETURN;
                        this.logPrintln(strSingReturn);
    					this.logPrintln("开始转入"+iCenterCode+" 未决赔款准备金-已发生已报告（已报案未立案）");
    					
    					strSingReturn = this.transIBNRLossAccount1(dbpool,statDBpool, iAccBookType, iAccBookCode,
    									iCenterCode, iDrawDate, iOperatorCode);
                        strReturn += strSingReturn+ this.LNRETURN;
                        this.logPrintln(strSingReturn);
    					this.logPrintln("开始转入"+iCenterCode+" 应收分XXXXX未决赔款准备金-已发生已报告（已报案未立案）");
    					
    					strSingReturn = this.transIBNRLossAccount2(dbpool,statDBpool, iAccBookType, iAccBookCode,
    									iCenterCode, iDrawDate, iOperatorCode);
                        strReturn += strSingReturn+ this.LNRETURN;
                        this.logPrintln(strSingReturn);
    					dbpool.commitTransaction();
                    }catch(Exception e){
                    	dbpool.rollbackTransaction();
                    	e.printStackTrace();
                    } 
                }                                  
            }
        } catch (Exception e) {
            dbpool.rollbackTransaction();
            throw e;
        } finally {
            dbpool.close();
            statDBpool.close();
        }
        return strReturn;

    }

    /**
     * @desc 生成准备金对冲凭证
     * @author 徐少将 20061220
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transAccountEnd(String iAccBookType, String iAccBookCode,
            String iCenterCode, String iDrawDate, String iOperatorCode)
            throws Exception {
        String strReturn = "";
        ArrayList arrCenterCode = new ArrayList();
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        if (iCenterCode == null || iCenterCode.equals("")) {
            blPrpDcompany.query(" CenterFlag='1'", 0);
            for (int i = 0; i < blPrpDcompany.getSize(); i++)
                arrCenterCode.add(blPrpDcompany.getArr(i).getComCode());
        } else {
            arrCenterCode.add(iCenterCode);
        }
        DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            for (int i = 0; i < arrCenterCode.size(); i++) {
                iCenterCode = (String) arrCenterCode.get(i);
                dbpool.beginTransaction();
                
                strReturn += this.transAccountPre(dbpool, iAccBookType,
                        iAccBookCode, iCenterCode, iDrawDate, iOperatorCode)
                        + this.LNRETURN;
                dbpool.commitTransaction();
                dbpool.beginTransaction();
                
                strReturn += this.transLossAccountPre1(dbpool, iAccBookType,
                        iAccBookCode, iCenterCode, iDrawDate, iOperatorCode)
                        + this.LNRETURN;
                
                strReturn += this.transLossAccountPre2(dbpool, iAccBookType,
                        iAccBookCode, iCenterCode, iDrawDate, iOperatorCode)
                        + this.LNRETURN;
                
                strReturn += this.transLossAccountPre3(dbpool, iAccBookType,
                        iAccBookCode, iCenterCode, iDrawDate, iOperatorCode)
                        + this.LNRETURN;
                dbpool.commitTransaction();
            }
        } catch (Exception e) {
            dbpool.rollbackTransaction();
            throw e;
        } finally {
            dbpool.close();
        }
        return strReturn;

    }

    /**
     * @desc 未到期责任准备金送XXXXX凭证
     * @author lijibin 20060403
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transAccount1(DbPool dbpool, DbPool statDBpool, String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) 
    throws Exception {
        String strReturn = "";
        double dblExchangeRate = 0;
        double dblDraw = 0;
        double dblDrawBack = 0;
        PubTools pubTools = new PubTools();
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        String strSQL = "";
        String strSQL1 = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        String strPreYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 1);

        
        
        
        
        
        
        
        
        
        
        blAccVoucher.getBLAccSubVoucher().initArr();
        
        BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
        blAccMonthTrace.initArr();
        String strWhere="  centercode='"+iCenterCode+"'"+
        			  " and yearmonth='"+strYearMonth+"'";
        blAccMonthTrace.query(strWhere, 0);
        if(blAccMonthTrace.getArr(0).getAccMonthStat().equals("2"))
        {   
        	strWhere=" comcode='"+iCenterCode+"'";
        	blPrpDcompany.initArr();
        	blPrpDcompany.query(dbpool,strWhere, 0);
        	strBranchCode=blPrpDcompany.getArr(0).getUpperComCode();
        	
        }
        
        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(strBranchCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(strBranchCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
        blPrpDcompany.initArr();
        String[] arrComCode = blPrpDcompany
                .getLowerComCodeNew(dbpool, iCenterCode);
        String strWhereCom = "('" + iCenterCode + "'";
        for (int j = 0; j < arrComCode.length; j++) {
            strWhereCom += ",'" + arrComCode[j] + "'";
        }
        strWhereCom += ")";

        
        
        strSQL = "SELECT ComCode,RiskCode,Currency,CarNatureCode,"
                + "SUM(DECODE(CertiType,'0',ROUND(DrawPremium,2),0)) AS DrawPremium "
                + " FROM prpnodutysum " + " WHERE Drawmonth='"+ strYearMonth
                + "' AND DrawWay='0' AND certitype ='0' AND ComCode IN " + strWhereCom;
        strSQL += " AND riskCode NOT IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL += ",'" + riskCode[i] + "'";
        }
        strSQL += ")";
        strSQL += " GROUP BY ComCode,RiskCode,Currency,CarNatureCode ";
        
        
        strSQL1 = "SELECT ComCode,RiskCode,Currency,CarNatureCode,"
                + "SUM(DECODE(CertiType,'0',ROUND(DrawPremium,2),0)) AS DrawPremium "
                + " FROM prpnodutysum " + " WHERE Drawmonth='"+ strYearMonth
                + "' AND DrawWay='0' AND certitype ='0' AND ComCode IN " + strWhereCom;
        strSQL1 += " AND riskCode  IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL1 += ",'" + riskCode[i] + "'";
        }
        strSQL1 += ")";
        strSQL1 += " GROUP BY ComCode,RiskCode,Currency,CarNatureCode ";
        ResultSet resultSet = statDBpool.query(strSQL);
        
        AccSubVoucherSchema accSubVoucherSchema = null;
        
        while (resultSet.next()) {
            dblDraw = Str.round(resultSet.getDouble("DrawPremium"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6501");
                accSubVoucherSchema.setF23("01");
                if (resultSet.getString("RiskCode").equals("0507")) {
                	if(resultSet.getString("CarNatureCode")==null||resultSet.getString("CarNatureCode").equals(""))
                	{  
                		throw new Exception();                		
                	}else{
                		accSubVoucherSchema.setF05(resultSet.getString("RiskCode")+resultSet.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                }






                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("01/");
                
                
                accSubVoucherSchema
                        .setRemark("提转" + iDrawDate + "直接业务未到期责任准备金");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("2601");

                accSubVoucherSchema.setF23("01");
                if (resultSet.getString("RiskCode").equals("0507"))  {
                	if(resultSet.getString("CarNatureCode")==null||resultSet.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception(); 
                	}else{
                		accSubVoucherSchema.setF05(resultSet.getString("RiskCode")+resultSet.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                }







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("01/");
               
                accSubVoucherSchema
                        .setRemark("提转" + iDrawDate + "直接业务未到期责任准备金");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        resultSet.close();
        
        ResultSet resultSet1 = statDBpool.query(strSQL1);
        while (resultSet1.next()) {
            dblDraw = Str.round(resultSet1.getDouble("DrawPremium"), 2);
            

            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6501");

                accSubVoucherSchema.setF23("02");
                if (resultSet1.getString("RiskCode").equals("0507"))  {
                	if(resultSet1.getString("CarNatureCode")==null||resultSet1.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception(); 
                	}else{
                		accSubVoucherSchema.setF05(resultSet1.getString("RiskCode")+resultSet1.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                }







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("02/");
                
                
                accSubVoucherSchema
                        .setRemark("提转" + iDrawDate + "分入业务未到期责任准备金");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("2601");
                accSubVoucherSchema.setF23("02");
                if (resultSet1.getString("RiskCode").equals("0507"))  {
                	if(resultSet1.getString("CarNatureCode")==null||resultSet1.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception(); 
                	}else{
                		accSubVoucherSchema.setF05(resultSet1.getString("RiskCode")+resultSet1.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                }







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("02/");
                
                accSubVoucherSchema
                        .setRemark("提转" + iDrawDate + "分入业务未到期责任准备金");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

        }

        resultSet1.close();
        String strCurrency = "";
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            accSubVoucherSchema = blAccVoucher.getBLAccSubVoucher().getArr(i);
            strCurrency = accSubVoucherSchema.getCurrency();
            if (!strCurrency.equals("CNY")) {
                dblExchangeRate = PubTools.getExchangeRate(strCurrency, "CNY",
                        iDrawDate);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setExchangeRate(
                        "" + dblExchangeRate);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getDebitSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setDebitDest(
                        "" + dblDrawBack);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getCreditSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setCreditDest(
                        "" + dblDrawBack);
            }





        }
        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return strBranchCode + " 没有生成未到期责任准备金凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());
        blAccVoucher.save(dbpool);
        strReturn = strBranchCode + " 生成未到期责任准备金凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }

    /**
     * @desc 应收分XXXXX未到期责任准备金送XXXXX凭证
     * @author lijibin 20060403
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transAccount2(DbPool dbpool,DbPool statDBpool, String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) throws Exception {
        String strReturn = "";
        double dblExchangeRate = 0;
        double dblDraw = 0;
        double dblDrawBack = 0;
        PubTools pubTools = new PubTools();
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        String strSQL1 = "";
        String strSQL2 = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        String strPreYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 1);

        
        
        
        
        
        
        
        
        
        
        blAccVoucher.getBLAccSubVoucher().initArr();
        
        BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
        blAccMonthTrace.initArr();
        String strWhere="  centercode='"+iCenterCode+"'"+
        			  " and yearmonth='"+strYearMonth+"'";
        blAccMonthTrace.query(strWhere, 0);
        if(blAccMonthTrace.getArr(0).getAccMonthStat().equals("2"))
        {   
        	strWhere=" comcode='"+iCenterCode+"'";
        	blPrpDcompany.initArr();
        	blPrpDcompany.query(dbpool,strWhere, 0);
        	strBranchCode=blPrpDcompany.getArr(0).getUpperComCode();
        }
        
        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(strBranchCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(strBranchCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
        blPrpDcompany.initArr();
        String[] arrComCode = blPrpDcompany
                .getLowerComCodeNew(dbpool, iCenterCode);
        String strWhereCom = "('" + iCenterCode + "'";
        for (int j = 0; j < arrComCode.length; j++) {
            strWhereCom += ",'" + arrComCode[j] + "'";
        }
        strWhereCom += ")";
        
        
        strSQL1 = "SELECT ComCode,RiskCode,Currency,CarNatureCode,"
                + "SUM(DECODE(CertiType,'1',ROUND(DrawPremium,2),0)) AS DrawPremium "
                + " FROM prpnodutysum " + " WHERE Drawmonth='"+ strYearMonth
                + "' AND DrawWay='0' AND certitype ='1' AND ComCode IN " + strWhereCom;
        strSQL1 += " AND riskCode NOT IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL1 += ",'" + riskCode[i] + "'";
        }
        strSQL1 += ")";
        strSQL1 += " GROUP BY ComCode,RiskCode,Currency,CarNatureCode ";
        
        
        strSQL2 = "SELECT ComCode,RiskCode,Currency,CarNatureCode,"
                + "SUM(DECODE(CertiType,'1',ROUND(DrawPremium,2),0)) AS DrawPremium "
                + " FROM prpnodutysum " + " WHERE Drawmonth='"+ strYearMonth
                + "' AND DrawWay='0' AND certitype ='1' AND ComCode IN " + strWhereCom;
        strSQL2 += " AND riskCode  IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL2 += ",'" + riskCode[i] + "'";
        }
        strSQL2 += ")";
        strSQL2 += " GROUP BY ComCode,RiskCode,Currency,CarNatureCode ";
        ResultSet resultSet1 = statDBpool.query(strSQL1);
        
        AccSubVoucherSchema accSubVoucherSchema = null;
        
        while (resultSet1.next()) {
            dblDraw = Str.round(resultSet1.getDouble("DrawPremium"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("1212");
                accSubVoucherSchema.setF23("01");
                if (resultSet1.getString("RiskCode").equals("0507"))  {
                	if(resultSet1.getString("CarNatureCode")==null||resultSet1.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception(); 
                	}else{
                		accSubVoucherSchema.setF05(resultSet1.getString("RiskCode")+resultSet1.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                } 







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("01/");
                
                
                accSubVoucherSchema.setRemark("转入" + iDrawDate
                        + "应收分XXXXX直接业务未到期责任准备金");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6501");
                accSubVoucherSchema.setF23("01");
                if (resultSet1.getString("RiskCode").equals("0507")) {
                	if(resultSet1.getString("CarNatureCode")==null||resultSet1.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception(); 
                	}else{
                		accSubVoucherSchema.setF05(resultSet1.getString("RiskCode")+resultSet1.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                } 







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("01/");
                
                accSubVoucherSchema.setRemark("转入" + iDrawDate
                        + "应收分XXXXX直接业务未到期责任准备金");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

        }
        resultSet1.close();
        
        ResultSet resultSet2 = statDBpool.query(strSQL2);
        while (resultSet2.next()) {
            dblDraw = Str.round(resultSet2.getDouble("DrawPremium"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("1212");
                accSubVoucherSchema.setF23("02");
                if (resultSet2.getString("RiskCode").equals("0507")) {
                	if(resultSet2.getString("CarNatureCode")==null||resultSet2.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception(); 
                	}else{
                		accSubVoucherSchema.setF05(resultSet2.getString("RiskCode")+resultSet2.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet2.getString("RiskCode"));
                } 







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet2.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("02/");
                
                
                accSubVoucherSchema.setRemark("转入" + iDrawDate
                        + "应收分XXXXX分入业务未到期责任准备金");
                accSubVoucherSchema.setCurrency(resultSet2
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6501");
                accSubVoucherSchema.setF23("02");
                if (resultSet2.getString("RiskCode").equals("0507")) {
                	if(resultSet2.getString("CarNatureCode")==null||resultSet2.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception(); 
                	}else{
                		accSubVoucherSchema.setF05(resultSet2.getString("RiskCode")+resultSet2.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet2.getString("RiskCode"));
                } 







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet2.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("02/");
                
                accSubVoucherSchema.setRemark("转入" + iDrawDate
                        + "应收分XXXXX分入业务未到期责任准备金");
                accSubVoucherSchema.setCurrency(resultSet2
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        resultSet2.close();

        String strCurrency = "";
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            accSubVoucherSchema = blAccVoucher.getBLAccSubVoucher().getArr(i);
            strCurrency = accSubVoucherSchema.getCurrency();
            if (!strCurrency.equals("CNY")) {
                dblExchangeRate = PubTools.getExchangeRate(strCurrency, "CNY",
                        iDrawDate);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setExchangeRate(
                        "" + dblExchangeRate);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getDebitSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setDebitDest(
                        "" + dblDrawBack);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getCreditSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setCreditDest(
                        "" + dblDrawBack);
            }





        }

        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return strBranchCode + " 没有生成应收分XXXXX未到期责任准备金凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());

        blAccVoucher.save(dbpool);
        strReturn = strBranchCode + " 生成应收分XXXXX未到期责任准备金凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }

    /**
     * @desc 未决赔款准备金-直接业务-已发生已报告送XXXXX凭证
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transLossAccount1(DbPool dbpool,DbPool statDBpool,  String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) throws Exception {
        String strReturn = "";
        double dblExchangeRate = 0;
        double dblDraw = 0;
        double dblBack = 0;
        double dblDrawBack = 0;
        PubTools pubTools = new PubTools();
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        String strSQL = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        String strPreYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 1);

        
        
        
        
        
        
        
        
        
        
        

        
        BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
        blAccMonthTrace.initArr();
        String strWhere="  centercode='"+iCenterCode+"'"+
        			  " and yearmonth='"+strYearMonth+"'";
        blAccMonthTrace.query(strWhere, 0);
        if(blAccMonthTrace.getArr(0).getAccMonthStat().equals("2"))
        {   
        	strWhere=" comcode='"+iCenterCode+"'";
        	blPrpDcompany.initArr();
        	blPrpDcompany.query(dbpool,strWhere, 0);
        	strBranchCode=blPrpDcompany.getArr(0).getUpperComCode();
        	
        }
        
        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(strBranchCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(strBranchCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
        
        blPrpDcompany.initArr();
        String[] arrComCode = blPrpDcompany
                .getLowerComCodeNew(dbpool, iCenterCode);
        String strWhereCom = "('" + iCenterCode + "'";
        for (int j = 0; j < arrComCode.length; j++) {
            strWhereCom += ",'" + arrComCode[j] + "'";
        }
        strWhereCom += ")";
        strSQL = "SELECT ComCode,RiskCode,Currency,CarNatureCode,"
                + "SUM(DECODE(PaymentMonth," + strYearMonth
                + ",DECODE(PayFlag,'01',SumPaid,0),0)"
                + "+DECODE(PaymentMonth," + strYearMonth
                + ",DECODE(PayFlag,'03',SumPaid,0),0)" + ") AS DrawPremium "
                + " FROM PrpLRPrepare " + " WHERE PaymentMonth= '"
                + strYearMonth + "'AND PayFlag IN ('01','03') AND ComCode IN "
                + strWhereCom;
        strSQL += " AND riskCode NOT IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL += ",'" + riskCode[i] + "'";
        }
        strSQL += ")";
        strSQL += " GROUP BY ComCode,RiskCode,Currency,CarNatureCode ";
        ResultSet resultSet = statDBpool.query(strSQL);

        
        AccVoucherArticleSchema accVoucherArticleSchema = null;
        AccSubVoucherSchema accSubVoucherSchema = null;
        while (resultSet.next()) {
            dblDraw = Str.round(resultSet.getDouble("DrawPremium"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6502");
                accSubVoucherSchema.setDirectionIdx("01/01/");
                accSubVoucherSchema.setF23("01");
                accSubVoucherSchema.setF03("01");
                if (resultSet.getString("RiskCode").equals("0507")) {
                	if(resultSet.getString("CarNatureCode")==null||resultSet.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception(); 
                	}else{
                		accSubVoucherSchema.setF05(resultSet.getString("RiskCode")+resultSet.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                } 







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                
                
                accSubVoucherSchema.setRemark("提转" + iDrawDate
                        + "直接业务已发生已报案未决赔款准备金");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
            
            dblDrawBack = dblDraw - dblBack;
            if (dblDrawBack != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("2602");
                accSubVoucherSchema.setDirectionIdx("01/01/");
                accSubVoucherSchema.setF23("01");
                accSubVoucherSchema.setF03("01");
                if (resultSet.getString("RiskCode").equals("0507")) {
                	if(resultSet.getString("CarNatureCode")==null||resultSet.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception(); 
                	}else{
                		accSubVoucherSchema.setF05(resultSet.getString("RiskCode")+resultSet.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                } 







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                
                accSubVoucherSchema.setRemark("提转" + iDrawDate
                        + "直接业务已发生已报案未决赔款准备金");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDrawBack);
                accSubVoucherSchema.setCreditDest("" + dblDrawBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        resultSet.close();
        String strCurrency = "";
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            accSubVoucherSchema = blAccVoucher.getBLAccSubVoucher().getArr(i);
            strCurrency = accSubVoucherSchema.getCurrency();
            if (!strCurrency.equals("CNY")) {
                dblExchangeRate = PubTools.getExchangeRate(strCurrency, "CNY",
                        iDrawDate);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setExchangeRate(
                        "" + dblExchangeRate);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getDebitSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setDebitDest(
                        "" + dblDrawBack);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getCreditSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setCreditDest(
                        "" + dblDrawBack);
            }





        }

        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return strBranchCode + " 没有生成未决赔款准备金-直接业务-已发生已报告凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());

        blAccVoucher.save(dbpool);
        strReturn = strBranchCode + " 生成未决赔款准备金-直接业务-已发生已报告凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }

    /**
     * @desc 未决赔款准备金-分入业务-已发生已报告送XXXXX凭证
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transLossAccount2(DbPool dbpool,DbPool statDBpool,  String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) throws Exception {
        String strReturn = "";
        double dblExchangeRate = 0;
        double dblDraw = 0;
        double dblBack = 0;
        double dblDrawBack = 0;
        PubTools pubTools = new PubTools();
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();

        String strSQL = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        String strPreYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 1);

        
        
        
        
        
        
        
        
        
        
        
        
        BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
        blAccMonthTrace.initArr();
        String strWhere="  centercode='"+iCenterCode+"'"+
        			  " and yearmonth='"+strYearMonth+"'";
        blAccMonthTrace.query(strWhere, 0);
        if(blAccMonthTrace.getArr(0).getAccMonthStat().equals("2"))
        {   
        	strWhere=" comcode='"+iCenterCode+"'";
        	blPrpDcompany.initArr();
        	blPrpDcompany.query(dbpool,strWhere, 0);
        	strBranchCode=blPrpDcompany.getArr(0).getUpperComCode();
        	
        }
        
        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(strBranchCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(strBranchCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
        
        blPrpDcompany.initArr();
        String[] arrComCode = blPrpDcompany
                .getLowerComCodeNew(dbpool, iCenterCode);
        String strWhereCom = "('" + iCenterCode + "'";
        for (int j = 0; j < arrComCode.length; j++) {
            strWhereCom += ",'" + arrComCode[j] + "'";
        }
        strWhereCom += ")";
        strSQL = "SELECT ComCode,RiskCode,Currency,CarNatureCode,"
                + "SUM(DECODE(PaymentMonth," + strYearMonth
                + ",DECODE(PayFlag,'01',SumPaid,0),0)" 
                + "+DECODE(PaymentMonth," + strYearMonth
                + ",DECODE(PayFlag,'03',SumPaid,0),0)" 
                + ") AS DrawPremium " + " FROM PrpLRPrepare "
                + " WHERE PaymentMonth= '" + strYearMonth
                + "'AND PayFlag IN ('01','03') AND ComCode IN " + strWhereCom;
        strSQL += " AND riskCode  IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL += ",'" + riskCode[i] + "'";
        }
        strSQL += ")";
        strSQL += " GROUP BY ComCode,RiskCode,Currency,CarNatureCode ";
        ResultSet resultSet = statDBpool.query(strSQL);

        
        AccVoucherArticleSchema accVoucherArticleSchema = null;
        AccSubVoucherSchema accSubVoucherSchema = null;
        while (resultSet.next()) {
            dblDraw = Str.round(resultSet.getDouble("DrawPremium"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6502");
                accSubVoucherSchema.setDirectionIdx("02/01/");
                accSubVoucherSchema.setF23("02");
                accSubVoucherSchema.setF03("01");
                if (resultSet.getString("RiskCode").equals("0507")) {
                	if(resultSet.getString("CarNatureCode")==null||resultSet.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception(); 
                	}else{
                		accSubVoucherSchema.setF05(resultSet.getString("RiskCode")+resultSet.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                } 







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                
                
                accSubVoucherSchema.setRemark("提转" + iDrawDate
                        + "分入业务已发生已报案未决赔款准备金");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            dblDrawBack = dblDraw - dblBack;
            if (dblDrawBack != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("2602");
                accSubVoucherSchema.setDirectionIdx("02/01/");
                accSubVoucherSchema.setF23("02");
                accSubVoucherSchema.setF03("01");
                if (resultSet.getString("RiskCode").equals("0507")) {
                	if(resultSet.getString("CarNatureCode")==null||resultSet.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception(); 
                	}else{
                		accSubVoucherSchema.setF05(resultSet.getString("RiskCode")+resultSet.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                } 







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                
                accSubVoucherSchema.setRemark("提转" + iDrawDate
                        + "分入业务已发生已报案未决赔款准备金");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDrawBack);
                accSubVoucherSchema.setCreditDest("" + dblDrawBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        resultSet.close();

        String strCurrency = "";
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            accSubVoucherSchema = blAccVoucher.getBLAccSubVoucher().getArr(i);
            strCurrency = accSubVoucherSchema.getCurrency();
            if (!strCurrency.equals("CNY")) {
                dblExchangeRate = PubTools.getExchangeRate(strCurrency, "CNY",
                        iDrawDate);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setExchangeRate(
                        "" + dblExchangeRate);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getDebitSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setDebitDest(
                        "" + dblDrawBack);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getCreditSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setCreditDest(
                        "" + dblDrawBack);
            }





        }

        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return strBranchCode + " 没有生成未决赔款准备金-分入业务-已发生已报告凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());

        blAccVoucher.save(dbpool);
        strReturn = strBranchCode + " 生成未决赔款准备金-分入业务-已发生已报告凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }

    /**
     * @desc 应收分XXXXX未决赔款准备金-已发生已报告1213/01/送XXXXX凭证
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transLossAccount3(DbPool dbpool,DbPool statDBpool,  String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) throws Exception {
        String strReturn = "";
        double dblExchangeRate = 0;
        double dblDraw = 0;
        PubTools pubTools = new PubTools();
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        String strSQL1 = "";
        String strSQL2 = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        String strPreYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 1);

        
        
        
        
        
        
        
        
        
        
        
        
        BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
        blAccMonthTrace.initArr();
        String strWhere="  centercode='"+iCenterCode+"'"+
        			  " and yearmonth='"+strYearMonth+"'";
        blAccMonthTrace.query(strWhere, 0);
        if(blAccMonthTrace.getArr(0).getAccMonthStat().equals("2"))
        {   
        	strWhere=" comcode='"+iCenterCode+"'";
        	blPrpDcompany.initArr();
        	blPrpDcompany.query(dbpool,strWhere, 0);
        	strBranchCode=blPrpDcompany.getArr(0).getUpperComCode();
        	
        }
        
        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(strBranchCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(strBranchCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
        blPrpDcompany.initArr();
        String[] arrComCode = blPrpDcompany
                .getLowerComCodeNew(dbpool, iCenterCode);
        String strWhereCom = "('" + iCenterCode + "'";
        for (int j = 0; j < arrComCode.length; j++) {
            strWhereCom += ",'" + arrComCode[j] + "'";
        }
        strWhereCom += ")";
        
        
        strSQL1 = "SELECT ComCode,RiskCode,Currency,CarNatureCode,"
                + "SUM(DECODE(PaymentMonth," + strYearMonth
                + ",DECODE(PayFlag,'07',SumPaid,0),0)"
                + "+DECODE(PaymentMonth," + strYearMonth
                + ",DECODE(PayFlag,'11',SumPaid,0),0)" + ") AS DrawPremium "
                + " FROM PrpLRPrepare " + " WHERE PaymentMonth= '"
                + strYearMonth + "'AND PayFlag IN ('07','11') AND ComCode IN "
                + strWhereCom;
        strSQL1 += " AND riskCode NOT IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL1 += ",'" + riskCode[i] + "'";
        }
        
        
        strSQL1 += ")";
        strSQL1 += " GROUP BY ComCode,RiskCode,Currency,CarNatureCode ";
        strSQL2 = "SELECT ComCode,RiskCode,Currency,CarNatureCode,"
                + "SUM(DECODE(PaymentMonth," + strYearMonth
                + ",DECODE(PayFlag,'07',SumPaid,0),0)"
                + "+DECODE(PaymentMonth," + strYearMonth
                + ",DECODE(PayFlag,'11',SumPaid,0),0)" + ") AS DrawPremium "
                + " FROM PrpLRPrepare " + " WHERE PaymentMonth= '"
                + strYearMonth + "'AND PayFlag IN ('07','11') AND ComCode IN "
                + strWhereCom;
        strSQL2 += " AND riskCode  IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL2 += ",'" + riskCode[i] + "'";
        }
        strSQL2 += ")";
        strSQL2 += " GROUP BY ComCode,RiskCode,Currency,CarNatureCode ";
        ResultSet resultSet1 = statDBpool.query(strSQL1);
         
        AccSubVoucherSchema accSubVoucherSchema = null;
        
        while (resultSet1.next()) {
            dblDraw = Str.round(resultSet1.getDouble("DrawPremium"), 2);

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("1213");
                accSubVoucherSchema.setDirectionIdx("01/01/");
                accSubVoucherSchema.setF23("01");
                accSubVoucherSchema.setF03("01");
                if (resultSet1.getString("RiskCode").equals("0507")) {
                	if(resultSet1.getString("CarNatureCode")==null||resultSet1.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception(); 
                	}else{
                		accSubVoucherSchema.setF05(resultSet1.getString("RiskCode")+resultSet1.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                } 







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                
                
                accSubVoucherSchema.setRemark(iDrawDate + "应收分XXXXX直接业务未决赔款准备金");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6201");
                accSubVoucherSchema.setDirectionIdx("01/01/");
                accSubVoucherSchema.setF23("01");
                accSubVoucherSchema.setF03("01");
                if (resultSet1.getString("RiskCode").equals("0507")) {
                	if(resultSet1.getString("CarNatureCode")==null||resultSet1.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception();
                	}else{
                		accSubVoucherSchema.setF05(resultSet1.getString("RiskCode")+resultSet1.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                } 







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                
                accSubVoucherSchema.setRemark(iDrawDate + "应收分XXXXX直接业务未决赔款准备金");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        resultSet1.close();
        ResultSet resultSet2 = statDBpool.query(strSQL2);
        
        while (resultSet2.next()) {

            dblDraw = Str.round(resultSet2.getDouble("DrawPremium"), 2);

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("1213");
                accSubVoucherSchema.setDirectionIdx("02/01/");
               
                if (resultSet2.getString("RiskCode").equals("0507")) {
                	if(resultSet2.getString("CarNatureCode")==null||resultSet2.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception();
                	}else{
                		accSubVoucherSchema.setF05(resultSet2.getString("RiskCode")+resultSet2.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet2.getString("RiskCode"));
                } 







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet2.getString("ComCode"));
                
                
                accSubVoucherSchema.setRemark(iDrawDate + "应收分XXXXX分入业务未决赔款准备金");
                accSubVoucherSchema.setCurrency(resultSet2
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6201");
                accSubVoucherSchema.setDirectionIdx("02/01/");
                accSubVoucherSchema.setF23("02");
                accSubVoucherSchema.setF03("01");
               
                if (resultSet2.getString("RiskCode").equals("0507")) {
                	if(resultSet2.getString("CarNatureCode")==null||resultSet2.getString("CarNatureCode").equals(""))
                	{
                		throw new Exception();
                	}else{
                		accSubVoucherSchema.setF05(resultSet2.getString("RiskCode")+resultSet2.getString("CarNatureCode"));
                	}
                }else{
                	accSubVoucherSchema.setF05(resultSet2.getString("RiskCode"));
                } 







                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet2.getString("ComCode"));
                
                accSubVoucherSchema.setRemark(iDrawDate + "应收分XXXXX分入业务未决赔款准备金");
                accSubVoucherSchema.setCurrency(resultSet2
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        resultSet2.close();
        
        String strCurrency = "";
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            accSubVoucherSchema = blAccVoucher.getBLAccSubVoucher().getArr(i);
            strCurrency = accSubVoucherSchema.getCurrency();
            if (!strCurrency.equals("CNY")) {
                dblExchangeRate = PubTools.getExchangeRate(strCurrency, "CNY",
                        iDrawDate);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setExchangeRate(
                        "" + dblExchangeRate);
                dblDraw = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getDebitSource());
                dblDraw = Str.round(dblDraw * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setDebitDest(
                        "" + dblDraw);
                dblDraw = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getCreditSource());
                dblDraw = Str.round(dblDraw * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setCreditDest(
                        "" + dblDraw);
            }





        }
        
        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return strBranchCode + " 没有生成应收分XXXXX未决赔款准备金-已发生已报告凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());

        blAccVoucher.save(dbpool);
        strReturn = strBranchCode + " 生成应收分XXXXX未决赔款准备金-已发生已报告凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }

    /**
     * @desc 未到期责任准备金对冲凭证
     * @author xushaojiang 20061220
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transAccountPre(DbPool dbpool, String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) throws Exception {
        String strReturn = "";


















        
        double dblExchangeRate = 0;
        double dblDraw = 0;
        double dblBack = 0;
        
        double dblDrawBack = 0;
        
        
        
        PubTools pubTools = new PubTools();

        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        

        String strSQL = "";
        String strSQL1 = "";
        String strSQL2 = "";
        String strSQL3 = "";
        String strSQL4 = "";
        String strSQL5 = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        String strPreYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 1);
        
        
        
        
        
        
        
        
        
        
        
        blAccVoucher.getBLAccSubVoucher().initArr();

        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(iCenterCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(iCenterCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
        blPrpDcompany.initArr();
        String[] arrComCode = blPrpDcompany
                .getLowerComCodeNew(dbpool, iCenterCode);
        String strWhereCom = "('" + iCenterCode + "'";
        for (int j = 0; j < arrComCode.length; j++) {
            strWhereCom += ",'" + arrComCode[j] + "'";
        }
        strWhereCom += ")";
        AccSubVoucherSchema accSubVoucherSchema = null;
        
        BLAccArticleBalance blAccArticleBalance = new BLAccArticleBalance();
        strSQL = " CenterCode='" + iCenterCode + "' AND AccBookType='"
                + iAccBookType + "' AND AccBookCode='" + iAccBookCode
                + "' AND YearMonth='" + strYearMonth
                + "' AND ItemCode='1212' AND DirectionIdx like '1212/01/%'"
                + " AND BalanceSource!=0";
        if (iCenterCode.equals("00000000")) {
            strSQL += " AND ArticleCode='1' AND DirectionOther LIKE 'BM%XZ%'";
            for (int i = 0; i < arrInRiskCode.length; i++)
                strSQL += " AND DirectionOther NOT LIKE '%" + arrInRiskCode[i]
                        + "%'";
        } else {
            strSQL += " AND ArticleCode LIKE 'XZ%' AND DirectionOther='1'";
            strSQL += " AND ArticleCode NOT IN ('" + arrInRiskCode[0] + "'";
            for (int i = 1; i < arrInRiskCode.length; i++)
                strSQL += ",'" + arrInRiskCode[i] + "'";
            strSQL += ")";
        }
        
        BLAccArticleBalance blAccArticleBalance1 = new BLAccArticleBalance();
        strSQL1 = " CenterCode='" + iCenterCode + "' AND AccBookType='"
                + iAccBookType + "' AND AccBookCode='" + iAccBookCode
                + "' AND YearMonth='" + strYearMonth
                + "' AND ItemCode='1212' AND DirectionIdx like '1212/02/%'"
                + " AND BalanceSource!=0";
        if (iCenterCode.equals("00000000")) {
            strSQL1 += " AND ArticleCode='1' AND DirectionOther LIKE 'BM%XZ%'";
            for (int i = 0; i < arrInRiskCode.length; i++)
                strSQL1 += " AND DirectionOther NOT LIKE '%" + arrInRiskCode[i]
                        + "%'";
        } else {
            strSQL1 += " AND ArticleCode LIKE 'XZ%' AND DirectionOther='1'";
            strSQL1 += " AND ArticleCode  IN ('" + arrInRiskCode[0] + "'";
            for (int i = 1; i < arrInRiskCode.length; i++)
                strSQL1 += ",'" + arrInRiskCode[i] + "'";
            strSQL1 += ")";
        }
        
        BLAccArticleBalance blAccArticleBalance2 = new BLAccArticleBalance();
        strSQL2 = " CenterCode='" + iCenterCode + "' AND AccBookType='"
                + iAccBookType + "' AND AccBookCode='" + iAccBookCode
                + "' AND YearMonth='" + strYearMonth
                + "' AND ItemCode='6501' AND DirectionIdx like '6501/01/%'"
                + " AND BalanceSource!=0";
        if (iCenterCode.equals("00000000")) {
            strSQL2 += " AND ArticleCode='1' AND DirectionOther LIKE 'BM%XZ%'";
            for (int i = 0; i < arrInRiskCode.length; i++)
                strSQL2 += " AND DirectionOther NOT LIKE '%" + arrInRiskCode[i]
                        + "%'";
        } else {
            strSQL2 += " AND ArticleCode LIKE 'XZ%' AND DirectionOther='1'";
            strSQL2 += " AND ArticleCode not IN ('" + arrInRiskCode[0] + "'";
            for (int i = 1; i < arrInRiskCode.length; i++)
                strSQL2 += ",'" + arrInRiskCode[i] + "'";
            strSQL2 += ")";
        }
        
        BLAccArticleBalance blAccArticleBalance3 = new BLAccArticleBalance();
        strSQL3 = " CenterCode='" + iCenterCode + "' AND AccBookType='"
                + iAccBookType + "' AND AccBookCode='" + iAccBookCode
                + "' AND YearMonth='" + strYearMonth
                + "' AND ItemCode='6501' AND DirectionIdx like '6501/02/%'"
                + " AND BalanceSource!=0";
        if (iCenterCode.equals("00000000")) {
            strSQL3 += " AND ArticleCode='1' AND DirectionOther LIKE 'BM%XZ%'";
            for (int i = 0; i < arrInRiskCode.length; i++)
                strSQL3 += " AND DirectionOther NOT LIKE '%" + arrInRiskCode[i]
                        + "%'";
        } else {
            strSQL3 += " AND ArticleCode LIKE 'XZ%' AND DirectionOther='1'";
            strSQL3 += " AND ArticleCode  IN ('" + arrInRiskCode[0] + "'";
            for (int i = 1; i < arrInRiskCode.length; i++)
                strSQL3 += ",'" + arrInRiskCode[i] + "'";
            strSQL3 += ")";
        }
        
        BLAccArticleBalance blAccArticleBalance4 = new BLAccArticleBalance();
        strSQL4 = " CenterCode='" + iCenterCode + "' AND AccBookType='"
                + iAccBookType + "' AND AccBookCode='" + iAccBookCode
                + "' AND YearMonth='" + strYearMonth
                + "' AND ItemCode='2601' AND DirectionIdx like '2601/01/%'"
                + " AND BalanceSource!=0";
        if (iCenterCode.equals("00000000")) {
            strSQL4 += " AND ArticleCode='1' AND DirectionOther LIKE 'BM%XZ%'";
            for (int i = 0; i < arrInRiskCode.length; i++)
                strSQL4 += " AND DirectionOther NOT LIKE '%" + arrInRiskCode[i]
                        + "%'";
        } else {
            strSQL4 += " AND ArticleCode LIKE 'XZ%' AND DirectionOther='1'";
            strSQL4 += " AND ArticleCode not IN ('" + arrInRiskCode[0] + "'";
            for (int i = 1; i < arrInRiskCode.length; i++)
                strSQL4 += ",'" + arrInRiskCode[i] + "'";
            strSQL4 += ")";
        }
        
        BLAccArticleBalance blAccArticleBalance5 = new BLAccArticleBalance();
        strSQL5 = " CenterCode='" + iCenterCode + "' AND AccBookType='"
                + iAccBookType + "' AND AccBookCode='" + iAccBookCode
                + "' AND YearMonth='" + strYearMonth
                + "' AND ItemCode='2601' AND DirectionIdx like '2601/02/%'"
                + " AND BalanceSource!=0";
        if (iCenterCode.equals("00000000")) {
            strSQL5 += " AND ArticleCode='1' AND DirectionOther LIKE 'BM%XZ%'";
            for (int i = 0; i < arrInRiskCode.length; i++)
                strSQL5 += " AND DirectionOther NOT LIKE '%" + arrInRiskCode[i]
                        + "%'";
        } else {
            strSQL5 += " AND ArticleCode LIKE 'XZ%' AND DirectionOther='1'";
            strSQL5 += " AND ArticleCode  IN ('" + arrInRiskCode[0] + "'";
            for (int i = 1; i < arrInRiskCode.length; i++)
                strSQL5 += ",'" + arrInRiskCode[i] + "'";
            strSQL5 += ")";
        }
        String strComCode = "";
        String strRiskCode = "";
        
        blAccArticleBalance.query(dbpool, strSQL);
        for (int i = 0; i < blAccArticleBalance.getSize(); i++) {
            double dblBackSource = 0;
            AccArticleBalanceSchema balanceSchema = blAccArticleBalance
                    .getArr(i);
            if (iCenterCode.equals("00000000")) {
                strComCode = "00"
                        + balanceSchema.getDirectionOther().substring(2, 8);
                strRiskCode = balanceSchema.getDirectionOther().substring(11);
                strRiskCode = strRiskCode
                        .substring(0, strRiskCode.indexOf("/"));
            } else {
                strComCode = "";
                strRiskCode = balanceSchema.getArticleCode().substring(2);
            }
            dblBackSource = Double
                    .parseDouble(balanceSchema.getBalanceSource());
            if (dblBackSource != 0.00) {

                dblBack = Double.parseDouble(balanceSchema.getBalanceDest());
                dblExchangeRate = dblBack / dblBackSource;
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setF05(strRiskCode);
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(strComCode);
                accSubVoucherSchema.setDirectionIdx("01/");
                accSubVoucherSchema.setF23("01");
                
                
                
                accSubVoucherSchema.setCurrency(balanceSchema.getCurrency());
                accSubVoucherSchema.setExchangeRate("" + dblExchangeRate);
                accSubVoucherSchema.setItemCode("1212");
                accSubVoucherSchema.setRemark("对冲" + iDrawDate
                        + "应收分XXXXX未到期责任准备金-直接业务");
                accSubVoucherSchema.setDebitSource("" + -1 * dblBackSource);
                accSubVoucherSchema.setDebitDest("" + -1 * dblBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        
        blAccArticleBalance1.query(dbpool, strSQL1);
        for (int i = 0; i < blAccArticleBalance1.getSize(); i++) {
            double dblBackSource = 0;
            AccArticleBalanceSchema balanceSchema1 = blAccArticleBalance1
                    .getArr(i);
            if (iCenterCode.equals("00000000")) {
                strComCode = "00"
                        + balanceSchema1.getDirectionOther().substring(2, 8);
                strRiskCode = balanceSchema1.getDirectionOther().substring(11);
                strRiskCode = strRiskCode
                        .substring(0, strRiskCode.indexOf("/"));
            } else {
                strComCode = "";
                strRiskCode = balanceSchema1.getArticleCode().substring(2);
            }
            dblBackSource = Double.parseDouble(balanceSchema1
                    .getBalanceSource());
            if (dblBackSource != 0.00) {
                dblBack = Double.parseDouble(balanceSchema1.getBalanceDest());
                dblExchangeRate = dblBack / dblBackSource;
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setF05(strRiskCode);
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF23("02");
                accSubVoucherSchema.setF28(strComCode);
                accSubVoucherSchema.setDirectionIdx("02/");
                
                
                
                accSubVoucherSchema.setCurrency(balanceSchema1.getCurrency());
                accSubVoucherSchema.setExchangeRate("" + dblExchangeRate);
                accSubVoucherSchema.setItemCode("1212");
                accSubVoucherSchema.setRemark("对冲" + iDrawDate
                        + "应收分XXXXX未到期责任准备金-分入业务");
                accSubVoucherSchema.setDebitSource("" + -1 * dblBackSource);
                accSubVoucherSchema.setDebitDest("" + -1 * dblBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        
        blAccArticleBalance2.query(dbpool, strSQL2);
        for (int i = 0; i < blAccArticleBalance2.getSize(); i++) {
            double dblBackSource = 0;
            AccArticleBalanceSchema balanceSchema2 = blAccArticleBalance2
                    .getArr(i);
            if (iCenterCode.equals("00000000")) {
                strComCode = "00"
                        + balanceSchema2.getDirectionOther().substring(2, 8);
                strRiskCode = balanceSchema2.getDirectionOther().substring(11);
                strRiskCode = strRiskCode
                        .substring(0, strRiskCode.indexOf("/"));
            } else {
                strComCode = "";
                strRiskCode = balanceSchema2.getArticleCode().substring(2);
            }
            dblBackSource = Double.parseDouble(balanceSchema2
                    .getBalanceSource());
            if (dblBackSource != 0.00) {
                dblBack = Double.parseDouble(balanceSchema2.getBalanceDest());
                dblExchangeRate = dblBack / dblBackSource;
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setF05(strRiskCode);
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(strComCode);
                accSubVoucherSchema.setDirectionIdx("01/");
                accSubVoucherSchema.setF23("01");
                
                
                
                accSubVoucherSchema.setCurrency(balanceSchema2.getCurrency());
                accSubVoucherSchema.setExchangeRate("" + dblExchangeRate);
                accSubVoucherSchema.setItemCode("6501");
                accSubVoucherSchema.setRemark("对冲" + iDrawDate
                        + "提取未到期责任准备金-直接业务");
                accSubVoucherSchema.setDebitSource("" + -1 * dblBackSource);
                accSubVoucherSchema.setDebitDest("" + -1 * dblBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        
        blAccArticleBalance3.query(dbpool, strSQL3);
        for (int i = 0; i < blAccArticleBalance3.getSize(); i++) {
            double dblBackSource = 0;
            AccArticleBalanceSchema balanceSchema3 = blAccArticleBalance3
                    .getArr(i);
            if (iCenterCode.equals("00000000")) {
                strComCode = "00"
                        + balanceSchema3.getDirectionOther().substring(2, 8);
                strRiskCode = balanceSchema3.getDirectionOther().substring(11);
                strRiskCode = strRiskCode
                        .substring(0, strRiskCode.indexOf("/"));
            } else {
                strComCode = "";
                strRiskCode = balanceSchema3.getArticleCode().substring(2);
            }
            dblBackSource = Double.parseDouble(balanceSchema3
                    .getBalanceSource());
            if (dblBackSource != 0.00) {
                dblBack = Double.parseDouble(balanceSchema3.getBalanceDest());
                dblExchangeRate = dblBack / dblBackSource;
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setF05(strRiskCode);
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(strComCode);
                accSubVoucherSchema.setF23("02");
                accSubVoucherSchema.setDirectionIdx("02/");
                
                
                
                accSubVoucherSchema.setCurrency(balanceSchema3.getCurrency());
                accSubVoucherSchema.setExchangeRate("" + dblExchangeRate);
                accSubVoucherSchema.setItemCode("6501");
                accSubVoucherSchema.setRemark("对冲" + iDrawDate
                        + "提取未到期责任准备金-分入业务");
                accSubVoucherSchema.setDebitSource("" + -1 * dblBackSource);
                accSubVoucherSchema.setDebitDest("" + -1 * dblBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        
        blAccArticleBalance4.query(dbpool, strSQL4);
        for (int i = 0; i < blAccArticleBalance4.getSize(); i++) {
            double dblBackSource = 0;
            AccArticleBalanceSchema balanceSchema4 = blAccArticleBalance4
                    .getArr(i);
            if (iCenterCode.equals("00000000")) {
                strComCode = "00"
                        + balanceSchema4.getDirectionOther().substring(2, 8);
                strRiskCode = balanceSchema4.getDirectionOther().substring(11);
                strRiskCode = strRiskCode
                        .substring(0, strRiskCode.indexOf("/"));
            } else {
                strComCode = "";
                strRiskCode = balanceSchema4.getArticleCode().substring(2);
            }
            dblBackSource = Double.parseDouble(balanceSchema4
                    .getBalanceSource());
            if (dblBackSource != 0.00) {
                dblBack = Double.parseDouble(balanceSchema4.getBalanceDest());
                dblExchangeRate = dblBack / dblBackSource;
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setF05(strRiskCode);
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(strComCode);
                accSubVoucherSchema.setDirectionIdx("01/");
                
                
                
                accSubVoucherSchema.setCurrency(balanceSchema4.getCurrency());
                accSubVoucherSchema.setExchangeRate("" + dblExchangeRate);
                accSubVoucherSchema.setItemCode("2601");
                accSubVoucherSchema.setF23("01");
                accSubVoucherSchema.setRemark("对冲" + iDrawDate
                        + "未到期责任准备金-直接业务");
                accSubVoucherSchema.setCreditSource("" + -1 * dblBackSource);
                accSubVoucherSchema.setCreditDest("" + -1 * dblBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        
        blAccArticleBalance5.query(dbpool, strSQL5);
        for (int i = 0; i < blAccArticleBalance5.getSize(); i++) {
            double dblBackSource = 0;
            AccArticleBalanceSchema balanceSchema5 = blAccArticleBalance5
                    .getArr(i);
            if (iCenterCode.equals("00000000")) {
                strComCode = "00"
                        + balanceSchema5.getDirectionOther().substring(2, 8);
                strRiskCode = balanceSchema5.getDirectionOther().substring(11);
                strRiskCode = strRiskCode
                        .substring(0, strRiskCode.indexOf("/"));
            } else {
                strComCode = "";
                strRiskCode = balanceSchema5.getArticleCode().substring(2);
            }
            dblBackSource = Double.parseDouble(balanceSchema5
                    .getBalanceSource());
            if (dblBackSource != 0.00) {
                dblBack = Double.parseDouble(balanceSchema5.getBalanceDest());
                dblExchangeRate = dblBack / dblBackSource;
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setF05(strRiskCode);
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(strComCode);
                accSubVoucherSchema.setDirectionIdx("02/");
                accSubVoucherSchema.setF23("02");
                
                
                
                accSubVoucherSchema.setCurrency(balanceSchema5.getCurrency());
                accSubVoucherSchema.setExchangeRate("" + dblExchangeRate);
                accSubVoucherSchema.setItemCode("2601");
                accSubVoucherSchema.setRemark("对冲" + iDrawDate
                        + "未到期责任准备金-分入业务");
                accSubVoucherSchema.setCreditSource("" + -1 * dblBackSource);
                accSubVoucherSchema.setCreditDest("" + -1 * dblBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return iCenterCode + " 没有生成未到期责任准备金对冲凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());

        blAccVoucher.save(dbpool);
        strReturn = iCenterCode + " 生成未到期责任准备金对冲凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }

    /**
     * @desc 未决赔款准备金-直接业务对冲凭证
     * @author xushaojiang 20061220
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transLossAccountPre1(DbPool dbpool, String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) throws Exception {
        String strReturn = "";


















        
        double dblExchangeRate = 0;
        double dblDraw = 0;
        double dblBack = 0;
        
        double dblDrawBack = 0;
        
        
        
        PubTools pubTools = new PubTools();

        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        

        String strSQL = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        String strPreYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 1);
        
        
        
        
        
        
        
        
        
        
        
        blAccVoucher.getBLAccSubVoucher().initArr();

        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(iCenterCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(iCenterCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
        blPrpDcompany.initArr();
        String[] arrComCode = blPrpDcompany
                .getLowerComCodeNew(dbpool, iCenterCode);
        String strWhereCom = "('" + iCenterCode + "'";
        for (int j = 0; j < arrComCode.length; j++) {
            strWhereCom += ",'" + arrComCode[j] + "'";
        }
        strWhereCom += ")";
        AccSubVoucherSchema accSubVoucherSchema = null;
        
        BLAccArticleBalance blAccArticleBalance = new BLAccArticleBalance();
        strSQL = " CenterCode='" + iCenterCode + "' AND AccBookType='"
                + iAccBookType + "' AND AccBookCode='" + iAccBookCode
                + "' AND YearMonth='" + strYearMonth
                + "' AND ItemCode='2602' AND DirectionIdx like '2602/01/01/%'"
                + " AND BalanceSource!=0";
        if (iCenterCode.equals("00000000")) {
            strSQL += " AND ArticleCode='1' AND DirectionOther LIKE 'BM%XZ%'";
            for (int i = 0; i < arrInRiskCode.length; i++)
                strSQL += " AND DirectionOther NOT LIKE '%" + arrInRiskCode[i]
                        + "%'";
        } else {
            strSQL += " AND ArticleCode LIKE 'XZ%' AND DirectionOther='1'";
            strSQL += " AND ArticleCode not IN ('" + arrInRiskCode[0] + "'";
            for (int i = 1; i < arrInRiskCode.length; i++)
                strSQL += ",'" + arrInRiskCode[i] + "'";
            strSQL += ")";
        }
        String strComCode = "";
        String strRiskCode = "";
        
        blAccArticleBalance.query(dbpool, strSQL);
        for (int i = 0; i < blAccArticleBalance.getSize(); i++) {
            double dblBackSource = 0;
            AccArticleBalanceSchema balanceSchema = blAccArticleBalance
                    .getArr(i);
            if (iCenterCode.equals("00000000")) {
                strComCode = "00"
                        + balanceSchema.getDirectionOther().substring(2, 8);
                strRiskCode = balanceSchema.getDirectionOther().substring(11);
                strRiskCode = strRiskCode
                        .substring(0, strRiskCode.indexOf("/"));
            } else {
                strComCode = "";
                strRiskCode = balanceSchema.getArticleCode().substring(2);
            }
            dblBackSource = Double
                    .parseDouble(balanceSchema.getBalanceSource());
            
            if (dblBackSource != 0.00) {

                dblBack = Double.parseDouble(balanceSchema.getBalanceDest());
                dblExchangeRate = dblBack / dblBackSource;
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setF05(strRiskCode);
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(strComCode);
                accSubVoucherSchema.setDirectionIdx("01/01/");
                accSubVoucherSchema.setF23("01");
                accSubVoucherSchema.setF03("01");
                
                
                
                accSubVoucherSchema.setCurrency(balanceSchema.getCurrency());
                accSubVoucherSchema.setExchangeRate("" + dblExchangeRate);
                accSubVoucherSchema.setItemCode("6502");
                accSubVoucherSchema.setRemark("对冲" + iDrawDate
                        + "提取未决赔款准备金-直接业务-已发生已报告");
                accSubVoucherSchema.setDebitSource("" + -1 * dblBackSource);
                accSubVoucherSchema.setDebitDest("" + -1 * dblBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
            
            if (dblBackSource != 0.00) {
                dblBack = Double.parseDouble(balanceSchema.getBalanceDest());
                dblExchangeRate = dblBack / dblBackSource;
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setF05(strRiskCode);
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(strComCode);
                accSubVoucherSchema.setDirectionIdx("01/01/");
                accSubVoucherSchema.setF23("01");
                accSubVoucherSchema.setF03("01");
                
                
                
                accSubVoucherSchema.setCurrency(balanceSchema.getCurrency());
                accSubVoucherSchema.setExchangeRate("" + dblExchangeRate);
                accSubVoucherSchema.setItemCode("2602");
                accSubVoucherSchema.setRemark("对冲" + iDrawDate
                        + "未决赔款准备金-直接业务-已发生已报告");
                accSubVoucherSchema.setCreditSource("" + -1 * dblBackSource);
                accSubVoucherSchema.setCreditDest("" + -1 * dblBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }

        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return iCenterCode + " 没有生成未决赔款准备金-直接业务-已发生已报告准备金对冲凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());

        blAccVoucher.save(dbpool);
        strReturn = iCenterCode + " 生成未决赔款准备金-直接业务-已发生已报告准备金对冲凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }

    /**
     * @desc 未决赔款准备金-分入业务对冲凭证
     * @author xushaojiang 20061220
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transLossAccountPre2(DbPool dbpool, String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) throws Exception {
        String strReturn = "";


















        
        double dblExchangeRate = 0;
        double dblDraw = 0;
        double dblBack = 0;
        
        double dblDrawBack = 0;
        
        
        
        PubTools pubTools = new PubTools();

        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        

        String strSQL = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        String strPreYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 1);
        
        
        
        
        
        
        
        
        
        
        
        blAccVoucher.getBLAccSubVoucher().initArr();

        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(iCenterCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(iCenterCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
        blPrpDcompany.initArr();
        String[] arrComCode = blPrpDcompany
                .getLowerComCodeNew(dbpool, iCenterCode);
        String strWhereCom = "('" + iCenterCode + "'";
        for (int j = 0; j < arrComCode.length; j++) {
            strWhereCom += ",'" + arrComCode[j] + "'";
        }
        strWhereCom += ")";
        AccSubVoucherSchema accSubVoucherSchema = null;
        
        BLAccArticleBalance blAccArticleBalance = new BLAccArticleBalance();
        strSQL = " CenterCode='" + iCenterCode + "' AND AccBookType='"
                + iAccBookType + "' AND AccBookCode='" + iAccBookCode
                + "' AND YearMonth='" + strYearMonth
                + "' AND ItemCode='2602' AND DirectionIdx like '2602/02/01/%'"
                + " AND BalanceSource!=0";
        if (iCenterCode.equals("00000000")) {
            strSQL += " AND ArticleCode='1' AND DirectionOther LIKE 'BM%XZ%'";
            for (int i = 0; i < arrInRiskCode.length; i++)
                strSQL += " AND DirectionOther NOT LIKE '%" + arrInRiskCode[i]
                        + "%'";
        } else {
            strSQL += " AND ArticleCode LIKE 'XZ%' AND DirectionOther='1'";
            strSQL += " AND ArticleCode not IN ('" + arrInRiskCode[0] + "'";
            for (int i = 1; i < arrInRiskCode.length; i++)
                strSQL += ",'" + arrInRiskCode[i] + "'";
            strSQL += ")";
        }
        String strComCode = "";
        String strRiskCode = "";
        
        blAccArticleBalance.query(dbpool, strSQL);
        for (int i = 0; i < blAccArticleBalance.getSize(); i++) {
            double dblBackSource = 0;
            AccArticleBalanceSchema balanceSchema = blAccArticleBalance
                    .getArr(i);
            if (iCenterCode.equals("00000000")) {
                strComCode = "00"
                        + balanceSchema.getDirectionOther().substring(2, 8);
                strRiskCode = balanceSchema.getDirectionOther().substring(11);
                strRiskCode = strRiskCode
                        .substring(0, strRiskCode.indexOf("/"));
            } else {
                strComCode = "";
                strRiskCode = balanceSchema.getArticleCode().substring(2);
            }
            dblBackSource = Double
                    .parseDouble(balanceSchema.getBalanceSource());
            
            if (dblBackSource != 0.00) {

                dblBack = Double.parseDouble(balanceSchema.getBalanceDest());
                dblExchangeRate = dblBack / dblBackSource;
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setF05(strRiskCode);
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(strComCode);
                accSubVoucherSchema.setDirectionIdx("02/01/");
                accSubVoucherSchema.setF23("02");
                accSubVoucherSchema.setF03("01");
                
                
                
                accSubVoucherSchema.setCurrency(balanceSchema.getCurrency());
                accSubVoucherSchema.setExchangeRate("" + dblExchangeRate);
                accSubVoucherSchema.setItemCode("6502");
                accSubVoucherSchema.setRemark("对冲" + iDrawDate
                        + "提取未决赔款准备金-分入业务-已发生已报告");
                accSubVoucherSchema.setDebitSource("" + -1 * dblBackSource);
                accSubVoucherSchema.setDebitDest("" + -1 * dblBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
            
            if (dblBackSource != 0.00) {
                dblBack = Double.parseDouble(balanceSchema.getBalanceDest());
                dblExchangeRate = dblBack / dblBackSource;
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setF05(strRiskCode);
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(strComCode);
                accSubVoucherSchema.setDirectionIdx("02/01/");
                accSubVoucherSchema.setF23("02");
                accSubVoucherSchema.setF03("01");
                
                
                
                accSubVoucherSchema.setCurrency(balanceSchema.getCurrency());
                accSubVoucherSchema.setExchangeRate("" + dblExchangeRate);
                accSubVoucherSchema.setItemCode("2602");
                accSubVoucherSchema.setRemark("对冲" + iDrawDate
                        + "未决赔款准备金-分入业务-已发生已报告");
                accSubVoucherSchema.setCreditSource("" + -1 * dblBackSource);
                accSubVoucherSchema.setCreditDest("" + -1 * dblBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }

        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return iCenterCode + " 没有生成未决赔款准备金-分入业务-已发生已报告准备金对冲凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());

        blAccVoucher.save(dbpool);
        strReturn = iCenterCode + " 生成未决赔款准备金-分入业务-已发生已报告准备金对冲凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }
    /**
     * @desc 应收分XXXXX未决赔款准备金对冲凭证
     * @author xushaojiang 20061220
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transLossAccountPre3(DbPool dbpool, String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) throws Exception {
        String strReturn = "";


















        
        double dblExchangeRate = 0;
        double dblDraw = 0;
        double dblBack = 0;
        
        double dblDrawBack = 0;
        
        
        
        PubTools pubTools = new PubTools();

        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        

        String strSQL = "";
        String strSQL1 = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        String strPreYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 1);
        
        
        
        
        
        
        
        
        
        
        
        blAccVoucher.getBLAccSubVoucher().initArr();

        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(iCenterCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(iCenterCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
        blPrpDcompany.initArr();
        String[] arrComCode = blPrpDcompany
                .getLowerComCodeNew(dbpool, iCenterCode);
        String strWhereCom = "('" + iCenterCode + "'";
        for (int j = 0; j < arrComCode.length; j++) {
            strWhereCom += ",'" + arrComCode[j] + "'";
        }
        strWhereCom += ")";
        AccSubVoucherSchema accSubVoucherSchema = null;
        
        BLAccArticleBalance blAccArticleBalance = new BLAccArticleBalance();
        strSQL = " CenterCode='" + iCenterCode + "' AND AccBookType='"
                + iAccBookType + "' AND AccBookCode='" + iAccBookCode
                + "' AND YearMonth='" + strYearMonth
                + "' AND ItemCode='1213' AND DirectionIdx like '1213/01/01/%'"
                + " AND BalanceSource!=0";
        if (iCenterCode.equals("00000000")) {
            strSQL += " AND ArticleCode='1' AND DirectionOther LIKE 'BM%XZ%'";
            for (int i = 0; i < arrInRiskCode.length; i++)
                strSQL += " AND DirectionOther NOT LIKE '%" + arrInRiskCode[i]
                        + "%'";
        } else {
            strSQL += " AND ArticleCode LIKE 'XZ%' AND DirectionOther='1'";
            strSQL += " AND ArticleCode NOT IN ('" + arrInRiskCode[0] + "'";
            for (int i = 1; i < arrInRiskCode.length; i++)
                strSQL += ",'" + arrInRiskCode[i] + "'";
            strSQL += ")";
        }
        
        BLAccArticleBalance blAccArticleBalance1 = new BLAccArticleBalance();
        strSQL1 = " CenterCode='" + iCenterCode + "' AND AccBookType='"
                + iAccBookType + "' AND AccBookCode='" + iAccBookCode
                + "' AND YearMonth='" + strYearMonth
                + "' AND ItemCode='1213' AND DirectionIdx like '1213/02/01/%'"
                + " AND BalanceSource!=0";
        if (iCenterCode.equals("00000000")) {
            strSQL1 += " AND ArticleCode='1' AND DirectionOther LIKE 'BM%XZ%'";
            for (int i = 0; i < arrInRiskCode.length; i++)
                strSQL1 += " AND DirectionOther NOT LIKE '%" + arrInRiskCode[i]
                        + "%'";
        } else {
            strSQL1 += " AND ArticleCode LIKE 'XZ%' AND DirectionOther='1'";
            strSQL1 += " AND ArticleCode  IN ('" + arrInRiskCode[0] + "'";
            for (int i = 1; i < arrInRiskCode.length; i++)
                strSQL1 += ",'" + arrInRiskCode[i] + "'";
            strSQL1 += ")";
        }
        String strComCode = "";
        String strRiskCode = "";
        
        blAccArticleBalance.query(dbpool, strSQL);
        for (int i = 0; i < blAccArticleBalance.getSize(); i++) {
            double dblBackSource = 0;
            AccArticleBalanceSchema balanceSchema = blAccArticleBalance
                    .getArr(i);
            if (iCenterCode.equals("00000000")) {
                strComCode = "00"
                        + balanceSchema.getDirectionOther().substring(2, 8);
                strRiskCode = balanceSchema.getDirectionOther().substring(11);
                strRiskCode = strRiskCode
                        .substring(0, strRiskCode.indexOf("/"));
            } else {
                strComCode = "";
                strRiskCode = balanceSchema.getArticleCode().substring(2);
            }
            dblBackSource = Double
                    .parseDouble(balanceSchema.getBalanceSource());
            
            if (dblBackSource != 0.00) {
                dblBack = Double.parseDouble(balanceSchema.getBalanceDest());
                dblExchangeRate = dblBack / dblBackSource;
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setF05(strRiskCode);
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(strComCode);
                accSubVoucherSchema.setDirectionIdx("01/01/");
                accSubVoucherSchema.setF23("01");
                accSubVoucherSchema.setF03("01");
                
                
                
                accSubVoucherSchema.setCurrency(balanceSchema.getCurrency());
                accSubVoucherSchema.setExchangeRate("" + dblExchangeRate);
                accSubVoucherSchema.setItemCode("6502");
                accSubVoucherSchema.setRemark("对冲" + iDrawDate
                        + "应收分XXXXX未决赔款准备金-直接业务-已发生已报告");
                accSubVoucherSchema.setDebitSource("" + -1 * dblBackSource);
                accSubVoucherSchema.setDebitDest("" + -1 * dblBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
            
            if (dblBackSource != 0.00) {
                dblBack = Double.parseDouble(balanceSchema.getBalanceDest());
                dblExchangeRate = dblBack / dblBackSource;
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setF05(strRiskCode);
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(strComCode);
                accSubVoucherSchema.setDirectionIdx("01/01/");
                accSubVoucherSchema.setF23("01");
                accSubVoucherSchema.setF03("01");
                
                
                
                accSubVoucherSchema.setCurrency(balanceSchema.getCurrency());
                accSubVoucherSchema.setExchangeRate("" + dblExchangeRate);
                accSubVoucherSchema.setItemCode("2602");
                accSubVoucherSchema.setRemark("对冲" + iDrawDate
                        + "应收分XXXXX未决赔款准备金-直接业务-已发生已报告");
                accSubVoucherSchema.setCreditSource("" + -1 * dblBackSource);
                accSubVoucherSchema.setCreditDest("" + -1 * dblBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        blAccArticleBalance1.query(dbpool, strSQL1);
        for (int i = 0; i < blAccArticleBalance1.getSize(); i++) {
            double dblBackSource = 0;
            AccArticleBalanceSchema balanceSchema1 = blAccArticleBalance1
                    .getArr(i);
            if (iCenterCode.equals("00000000")) {
                strComCode = "00"
                        + balanceSchema1.getDirectionOther().substring(2, 8);
                strRiskCode = balanceSchema1.getDirectionOther().substring(11);
                strRiskCode = strRiskCode
                        .substring(0, strRiskCode.indexOf("/"));
            } else {
                strComCode = "";
                strRiskCode = balanceSchema1.getArticleCode().substring(2);
            }
            dblBackSource = Double
                    .parseDouble(balanceSchema1.getBalanceSource());
            
            if (dblBackSource != 0.00) {
                dblBack = Double.parseDouble(balanceSchema1.getBalanceDest());
                dblExchangeRate = dblBack / dblBackSource;
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setF05(strRiskCode);
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(strComCode);
                accSubVoucherSchema.setDirectionIdx("02/01/");
                accSubVoucherSchema.setF23("02");
                accSubVoucherSchema.setF03("01");
                
                
                
                accSubVoucherSchema.setCurrency(balanceSchema1.getCurrency());
                accSubVoucherSchema.setExchangeRate("" + dblExchangeRate);
                accSubVoucherSchema.setItemCode("6502");
                accSubVoucherSchema.setRemark("对冲" + iDrawDate
                        + "应收分XXXXX未决赔款准备金-分入业务-已发生已报告");
                accSubVoucherSchema.setDebitSource("" + -1 * dblBackSource);
                accSubVoucherSchema.setDebitDest("" + -1 * dblBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
            
            if (dblBackSource != 0.00) {
                dblBack = Double.parseDouble(balanceSchema1.getBalanceDest());
                dblExchangeRate = dblBack / dblBackSource;
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setF05(strRiskCode);
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(strComCode);
                accSubVoucherSchema.setDirectionIdx("02/01/");
                accSubVoucherSchema.setF23("02");
                accSubVoucherSchema.setF03("01");
                
                
                
                accSubVoucherSchema.setCurrency(balanceSchema1.getCurrency());
                accSubVoucherSchema.setExchangeRate("" + dblExchangeRate);
                accSubVoucherSchema.setItemCode("2602");
                accSubVoucherSchema.setRemark("对冲" + iDrawDate
                        + "应收分XXXXX未决赔款准备金-分入业务-已发生已报告");
                accSubVoucherSchema.setCreditSource("" + -1 * dblBackSource);
                accSubVoucherSchema.setCreditDest("" + -1 * dblBack);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return iCenterCode + " 没有生成应收分XXXXX未决赔款准备金对冲凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());

        blAccVoucher.save(dbpool);
        strReturn = iCenterCode + " 生成应收分XXXXX未决赔款准备金对冲凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }

    /**
     * @desc 已发生未报告赔款准备金
     * @author lijibin 20070416
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transIBNRAccount1(DbPool dbpool,DbPool statDBpool,  String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) 
    throws Exception {
        String strReturn = "";
        double dblExchangeRate = 0;
        double dblDraw = 0;
        double dblDrawBack = 0;
        PubTools pubTools = new PubTools();
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        String strSQL = "";
        String strSQL1 = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        String strPreYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 1);

        blAccVoucher.getBLAccSubVoucher().initArr();

        
        BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
        blAccMonthTrace.initArr();
        String strWhere="  centercode='"+iCenterCode+"'"+
        			  " and yearmonth='"+strYearMonth+"'";
        blAccMonthTrace.query(strWhere, 0);
        if(blAccMonthTrace.getArr(0).getAccMonthStat().equals("2"))
        {   
        	strWhere=" comcode='"+iCenterCode+"'";
        	blPrpDcompany.initArr();
        	blPrpDcompany.query(dbpool,strWhere, 0);
        	strBranchCode=blPrpDcompany.getArr(0).getUpperComCode();
        	
        }
        
        
        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(strBranchCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(strBranchCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
		blPrpDcompany.initArr();
		String strWhereCom = "";
		if ("00000000".equals(iCenterCode)) {
			String[] arrComCode = blPrpDcompany.getLowerComCodeNew(dbpool,
					iCenterCode);
			strWhereCom = "('" + iCenterCode + "'";
			for (int j = 0; j < arrComCode.length; j++) {
				strWhereCom += ",'" + arrComCode[j] + "'";
			}
			strWhereCom += ")";
		} else {
        	strWhereCom = "('" + iCenterCode + "')";
        }

        
        strSQL = "SELECT ComCode,RiskCode,Currency,SUM(LOSSSUM) AS LOSSSUM "
                + " FROM PRPNODUTYACCOUNTSUM  WHERE DrawMonth='"
                + strYearMonth + "'"
                + " AND certitype ='01'"
                + " AND ComCode IN " + strWhereCom;
        strSQL += " AND riskCode NOT IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL += ",'" + riskCode[i] + "'";
        }
        strSQL += ")";
        strSQL += " GROUP BY ComCode,RiskCode,Currency ";
        
        strSQL1 = "SELECT ComCode,RiskCode,Currency,SUM(LOSSSUM) AS LOSSSUM "
                + " FROM PRPNODUTYACCOUNTSUM WHERE DrawMonth='"
                + strYearMonth + "'"
                + " AND certitype ='01'"
                + " AND ComCode IN " + strWhereCom;
        strSQL1 += " AND riskCode  IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL1 += ",'" + riskCode[i] + "'";
        }
        strSQL1 += ")";
        strSQL1 += " GROUP BY ComCode,RiskCode,Currency ";
        ResultSet resultSet = statDBpool.query(strSQL);
        
        AccSubVoucherSchema accSubVoucherSchema = null;
        
        while (resultSet.next()) {
            dblDraw = Str.round(resultSet.getDouble("LOSSSUM"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6502");
                accSubVoucherSchema.setF23("01/02");
                accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("01/02/");
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "直接业务提取未决赔款准备金已发生未报告");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("2602");
                accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                accSubVoucherSchema.setF23("01/02");
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("01/02/");
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "直接业务未决赔款准备金已发生未报告");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        resultSet.close();
        
        ResultSet resultSet1 = statDBpool.query(strSQL1);
        while (resultSet1.next()) {
            dblDraw = Str.round(resultSet1.getDouble("LOSSSUM"), 2);
            

            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6502");
                accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                accSubVoucherSchema.setF23("02/02");
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("02/02/");
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "分入业务提取未决赔款准备金已发生未报告");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("2602");
                accSubVoucherSchema.setF23("02/02");
                accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("02/02/");
                
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "分入业务未决赔款准备金已发生未报告");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

        }

        resultSet1.close();
        String strCurrency = "";
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            accSubVoucherSchema = blAccVoucher.getBLAccSubVoucher().getArr(i);
            strCurrency = accSubVoucherSchema.getCurrency();
            if (!strCurrency.equals("CNY")) {
                dblExchangeRate = PubTools.getExchangeRate(strCurrency, "CNY",
                        iDrawDate);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setExchangeRate(
                        "" + dblExchangeRate);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getDebitSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setDebitDest(
                        "" + dblDrawBack);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getCreditSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setCreditDest(
                        "" + dblDrawBack);
            }
        }
        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return strBranchCode + " 没有生成已发生未报告赔款准备金凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());

        blAccVoucher.save(dbpool);
        strReturn = strBranchCode + " 生成已发生未报告赔款准备金凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }

    /**
     * @desc 摊回已发生未报告未决赔款准备金送XXXXX凭证
     * @author lijibin 20070416
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transIBNRAccount2(DbPool dbpool,DbPool statDBpool,  String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) 
    throws Exception {
        String strReturn = "";
        double dblExchangeRate = 0;
        double dblDraw = 0;
        double dblDrawBack = 0;
        PubTools pubTools = new PubTools();
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        String strSQL = "";
        String strSQL1 = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        String strPreYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 1);

        blAccVoucher.getBLAccSubVoucher().initArr();
        
        
        BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
        blAccMonthTrace.initArr();
        String strWhere="  centercode='"+iCenterCode+"'"+
        			  " and yearmonth='"+strYearMonth+"'";
        blAccMonthTrace.query(strWhere, 0);
        if(blAccMonthTrace.getArr(0).getAccMonthStat().equals("2"))
        {   
        	strWhere=" comcode='"+iCenterCode+"'";
        	blPrpDcompany.initArr();
        	blPrpDcompany.query(dbpool,strWhere, 0);
        	strBranchCode=blPrpDcompany.getArr(0).getUpperComCode();
        	
        }
        

        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(strBranchCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(strBranchCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
		blPrpDcompany.initArr();
		
		String strWhereCom = "";
		if ("00000000".equals(iCenterCode)) {
			String[] arrComCode = blPrpDcompany.getLowerComCodeNew(dbpool,
					iCenterCode);
			strWhereCom = "('" + iCenterCode + "'";
			for (int j = 0; j < arrComCode.length; j++) {
				strWhereCom += ",'" + arrComCode[j] + "'";
			}
			strWhereCom += ")";
		} else {
        	strWhereCom = "('" + iCenterCode + "')";
        }


        
        strSQL = "SELECT ComCode,RiskCode,Currency,SUM(LOSSSUM) AS LOSSSUM "
                + " FROM PRPNODUTYACCOUNTSUM  WHERE DrawMonth='"
                + strYearMonth + "'"
                + " AND certitype ='02'"
                + " AND ComCode IN " + strWhereCom;
        strSQL += " AND riskCode NOT IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL += ",'" + riskCode[i] + "'";
        }
        strSQL += ")";
        strSQL += " GROUP BY ComCode,RiskCode,Currency ";
        
        strSQL1 = "SELECT ComCode,RiskCode,Currency,SUM(LOSSSUM) AS LOSSSUM "
                + " FROM PRPNODUTYACCOUNTSUM WHERE DrawMonth='"
                + strYearMonth + "'"
                + " AND certitype ='02'"
                + " AND ComCode IN " + strWhereCom;
        strSQL1 += " AND riskCode  IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL1 += ",'" + riskCode[i] + "'";
        }
        strSQL1 += ")";
        strSQL1 += " GROUP BY ComCode,RiskCode,Currency ";
        ResultSet resultSet = statDBpool.query(strSQL);
        
        AccSubVoucherSchema accSubVoucherSchema = null;
        
        while (resultSet.next()) {
            dblDraw = Str.round(resultSet.getDouble("LOSSSUM"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("1213");
                accSubVoucherSchema.setF23("01/02");
                accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("01/02/");
                
                
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "直接业务应收分XXXXX未决赔款准备金已发生未报告");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6201");
                accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                accSubVoucherSchema.setF23("01/02");
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("01/02/");
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "直接业务摊回未决赔款准备金已发生未报告");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        resultSet.close();
        
        ResultSet resultSet1 = statDBpool.query(strSQL1);
        while (resultSet1.next()) {
            dblDraw = Str.round(resultSet1.getDouble("LOSSSUM"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("1213");
                accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                accSubVoucherSchema.setF23("02/02");
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("02/02/");
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "分入业务应收分XXXXX未决赔款准备金已发生未报告");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6201");
                accSubVoucherSchema.setF23("02/02");
                accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("02/02/");
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "分入业务摊回未决赔款准备金已发生未报告");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

        }

        resultSet1.close();
        String strCurrency = "";
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            accSubVoucherSchema = blAccVoucher.getBLAccSubVoucher().getArr(i);
            strCurrency = accSubVoucherSchema.getCurrency();
            if (!strCurrency.equals("CNY")) {
                dblExchangeRate = PubTools.getExchangeRate(strCurrency, "CNY",
                        iDrawDate);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setExchangeRate(
                        "" + dblExchangeRate);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getDebitSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setDebitDest(
                        "" + dblDrawBack);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getCreditSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setCreditDest(
                        "" + dblDrawBack);
            }
        }
        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return strBranchCode + " 没有生成摊回已发生未报告未决赔款准备金凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());

        blAccVoucher.save(dbpool);
        strReturn = strBranchCode + " 生成摊回已发生未报告未决赔款准备金凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }   
    
    /**
     * @desc 未决XXXXX费用准备金送XXXXX凭证
     * @author lijibin 20070416
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transClaimsExpenses1(DbPool dbpool,DbPool statDBpool,  String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) 
    throws Exception {
        String strReturn = "";
        double dblExchangeRate = 0;
        double dblDraw = 0;
        double dblDrawBack = 0;
        PubTools pubTools = new PubTools();
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        String strSQL = "";
        String strSQL1 = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        String strPreYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 1);

        blAccVoucher.getBLAccSubVoucher().initArr();
        
        
        BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
        blAccMonthTrace.initArr();
        String strWhere="  centercode='"+iCenterCode+"'"+
        			  " and yearmonth='"+strYearMonth+"'";
        blAccMonthTrace.query(strWhere, 0);
        if(blAccMonthTrace.getArr(0).getAccMonthStat().equals("2"))
        {   
        	strWhere=" comcode='"+iCenterCode+"'";
        	blPrpDcompany.initArr();
        	blPrpDcompany.query(dbpool,strWhere, 0);
        	strBranchCode=blPrpDcompany.getArr(0).getUpperComCode();
        	
        }
        
        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(strBranchCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(strBranchCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
		blPrpDcompany.initArr();
		
		String strWhereCom = "";
		if ("00000000".equals(iCenterCode)) {
			String[] arrComCode = blPrpDcompany.getLowerComCodeNew(dbpool,
					iCenterCode);
			strWhereCom = "('" + iCenterCode + "'";
			for (int j = 0; j < arrComCode.length; j++) {
				strWhereCom += ",'" + arrComCode[j] + "'";
			}
			strWhereCom += ")";
		} else {
        	strWhereCom = "('" + iCenterCode + "')";
        }


        
        strSQL = "SELECT ComCode,RiskCode,Currency,SUM(LOSSSUM) AS LOSSSUM "
                + " FROM PRPNODUTYACCOUNTSUM  WHERE DrawMonth='"
                + strYearMonth + "'"
                + " AND certitype ='03'"
                + " AND ComCode IN " + strWhereCom;
        strSQL += " AND riskCode NOT IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL += ",'" + riskCode[i] + "'";
        }
        strSQL += ")";
        strSQL += " GROUP BY ComCode,RiskCode,Currency ";
        
        strSQL1 = "SELECT ComCode,RiskCode,Currency,SUM(LOSSSUM) AS LOSSSUM "
                + " FROM PRPNODUTYACCOUNTSUM WHERE DrawMonth='"
                + strYearMonth + "'"
                + " AND certitype ='03'"
                + " AND ComCode IN " + strWhereCom;
        strSQL1 += " AND riskCode  IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL1 += ",'" + riskCode[i] + "'";
        }
        strSQL1 += ")";
        strSQL1 += " GROUP BY ComCode,RiskCode,Currency ";
        ResultSet resultSet = statDBpool.query(strSQL);
        
        AccSubVoucherSchema accSubVoucherSchema = null;
        
        while (resultSet.next()) {
            dblDraw = Str.round(resultSet.getDouble("LOSSSUM"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6502");
                accSubVoucherSchema.setF23("01/03");
                accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("01/03/");
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "直接业务提取未决赔款准备金XXXXX费用准备金");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("2602");
                accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                accSubVoucherSchema.setF23("01/03");
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("01/03/");
               
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "直接业务未决赔款准备金XXXXX费用准备金");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        resultSet.close();
        
        ResultSet resultSet1 = statDBpool.query(strSQL1);
        while (resultSet1.next()) {
            dblDraw = Str.round(resultSet1.getDouble("LOSSSUM"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6502");
                accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                accSubVoucherSchema.setF23("02/03");
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("02/03/");
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "分入业务提取未决赔款准备金已发生未报告");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("2602");
                accSubVoucherSchema.setF23("02/03");
                accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("02/03/");
                
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "分入业务未决赔款准备金已发生未报告");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

        }

        resultSet1.close();
        String strCurrency = "";
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            accSubVoucherSchema = blAccVoucher.getBLAccSubVoucher().getArr(i);
            strCurrency = accSubVoucherSchema.getCurrency();
            if (!strCurrency.equals("CNY")) {
                dblExchangeRate = PubTools.getExchangeRate(strCurrency, "CNY",
                        iDrawDate);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setExchangeRate(
                        "" + dblExchangeRate);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getDebitSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setDebitDest(
                        "" + dblDrawBack);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getCreditSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setCreditDest(
                        "" + dblDrawBack);
            }
        }
        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return strBranchCode + " 没有生成未决XXXXX费用准备金凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());

        blAccVoucher.save(dbpool);
        strReturn = strBranchCode + " 生成未决XXXXX费用准备金凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }

    /**
     * @desc 摊回已XXXXX费用准备金送XXXXX凭证
     * @author lijibin 20070416
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transClaimsExpenses2(DbPool dbpool,DbPool statDBpool,  String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) 
    throws Exception {
        String strReturn = "";
        double dblExchangeRate = 0;
        double dblDraw = 0;
        double dblDrawBack = 0;
        PubTools pubTools = new PubTools();
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        String strSQL = "";
        String strSQL1 = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        String strPreYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 1);

        blAccVoucher.getBLAccSubVoucher().initArr();
        
        BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
        blAccMonthTrace.initArr();
        String strWhere="  centercode='"+iCenterCode+"'"+
        			  " and yearmonth='"+strYearMonth+"'";
        blAccMonthTrace.query(strWhere, 0);
        if(blAccMonthTrace.getArr(0).getAccMonthStat().equals("2"))
        {   
        	strWhere=" comcode='"+iCenterCode+"'";
        	blPrpDcompany.initArr();
        	blPrpDcompany.query(dbpool,strWhere, 0);
        	strBranchCode=blPrpDcompany.getArr(0).getUpperComCode();
        	
        }
        
        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(strBranchCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(strBranchCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
		blPrpDcompany.initArr();
		
		String strWhereCom = "";
		if ("00000000".equals(iCenterCode)) {
			String[] arrComCode = blPrpDcompany.getLowerComCodeNew(dbpool,
					iCenterCode);
			strWhereCom = "('" + iCenterCode + "'";
			for (int j = 0; j < arrComCode.length; j++) {
				strWhereCom += ",'" + arrComCode[j] + "'";
			}
			strWhereCom += ")";
		} else {
        	strWhereCom = "('" + iCenterCode + "')";
        }

        
        strSQL = "SELECT ComCode,RiskCode,Currency,SUM(LOSSSUM) AS LOSSSUM "
                + " FROM PRPNODUTYACCOUNTSUM  WHERE DrawMonth='"
                + strYearMonth + "'"
                + " AND certitype ='04'"
                + " AND ComCode IN " + strWhereCom;
        strSQL += " AND riskCode NOT IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL += ",'" + riskCode[i] + "'";
        }
        strSQL += ")";
        strSQL += " GROUP BY ComCode,RiskCode,Currency ";
        
        strSQL1 = "SELECT ComCode,RiskCode,Currency,SUM(LOSSSUM) AS LOSSSUM "
                + " FROM PRPNODUTYACCOUNTSUM WHERE DrawMonth='"
                + strYearMonth + "'"
                + " AND certitype ='04'"
                + " AND ComCode IN " + strWhereCom;
        strSQL1 += " AND riskCode  IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL1 += ",'" + riskCode[i] + "'";
        }
        strSQL1 += ")";
        strSQL1 += " GROUP BY ComCode,RiskCode,Currency ";
        ResultSet resultSet = statDBpool.query(strSQL);
        
        AccSubVoucherSchema accSubVoucherSchema = null;
        
        while (resultSet.next()) {
            dblDraw = Str.round(resultSet.getDouble("LOSSSUM"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("1213");
                accSubVoucherSchema.setF23("01/03");
                accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("01/03/");
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "直接业务应收分XXXXX未决赔款准备金XXXXX费用准备金");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6201");
                accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                accSubVoucherSchema.setF23("01/03");
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("01/03/");
               
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "直接业务摊回未决赔款准备金XXXXX费用准备金");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        resultSet.close();
        
        ResultSet resultSet1 = statDBpool.query(strSQL1);
        while (resultSet1.next()) {
            dblDraw = Str.round(resultSet1.getDouble("LOSSSUM"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("1213");
                accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                accSubVoucherSchema.setF23("02/03");
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("02/03/");
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "分入业务应收分XXXXX未决赔款准备金XXXXX费用准备金");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6201");
                accSubVoucherSchema.setF23("02/03");
                accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema.setDirectionIdx("02/03/");
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "分入业务摊回未决赔款准备金XXXXX费用准备金");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

        }

        resultSet1.close();
        String strCurrency = "";
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            accSubVoucherSchema = blAccVoucher.getBLAccSubVoucher().getArr(i);
            strCurrency = accSubVoucherSchema.getCurrency();
            if (!strCurrency.equals("CNY")) {
                dblExchangeRate = PubTools.getExchangeRate(strCurrency, "CNY",
                        iDrawDate);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setExchangeRate(
                        "" + dblExchangeRate);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getDebitSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setDebitDest(
                        "" + dblDrawBack);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getCreditSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setCreditDest(
                        "" + dblDrawBack);
            }
        }
        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return strBranchCode + " 没有生成摊回已XXXXX费用准备金凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());

        blAccVoucher.save(dbpool);
        strReturn = strBranchCode + " 生成摊回已XXXXX费用准备金凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }   
    
    /**
     * @desc 未决赔款已发生已报告（已报案未立案）送XXXXX
     * @author 徐少将 20070702
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transIBNRLossAccount1(DbPool dbpool,DbPool statDBpool,  String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) 
    throws Exception {
        String strReturn = "";
        double dblExchangeRate = 0;
        double dblDraw = 0;
        double dblDrawBack = 0;
        PubTools pubTools = new PubTools();
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        String strSQL = "";
        String strSQL1 = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        
        blAccVoucher.getBLAccSubVoucher().initArr();
        
        BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
        blAccMonthTrace.initArr();
        String strWhere="  centercode='"+iCenterCode+"'"+
        			  " and yearmonth='"+strYearMonth+"'";
        blAccMonthTrace.query(strWhere, 0);
        if(blAccMonthTrace.getArr(0).getAccMonthStat().equals("2"))
        {   
        	strWhere=" comcode='"+iCenterCode+"'";
        	blPrpDcompany.initArr();
        	blPrpDcompany.query(dbpool,strWhere, 0);
        	strBranchCode=blPrpDcompany.getArr(0).getUpperComCode();
        }
        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(strBranchCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(strBranchCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
		blPrpDcompany.initArr();
		
		String strWhereCom = "";
		if ("00000000".equals(iCenterCode)) {
			String[] arrComCode = blPrpDcompany.getLowerComCodeNew(dbpool,
					iCenterCode);
			strWhereCom = "('" + iCenterCode + "'";
			for (int j = 0; j < arrComCode.length; j++) {
				strWhereCom += ",'" + arrComCode[j] + "'";
			}
			strWhereCom += ")";
		} else {
        	strWhereCom = "('" + iCenterCode + "')";
        }

        
        strSQL = "SELECT ComCode,RiskCode,Currency,SUM(LOSSSUM) AS LOSSSUM "
                + " FROM PRPNODUTYACCOUNTSUM  WHERE DrawMonth='"
                + strYearMonth + "'"
                + " AND certitype ='05'"
                + " AND ComCode IN " + strWhereCom;
        strSQL += " AND riskCode NOT IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL += ",'" + riskCode[i] + "'";
        }
        strSQL += ")";
        strSQL += " GROUP BY ComCode,RiskCode,Currency ";
        
        strSQL1 = "SELECT ComCode,RiskCode,Currency,SUM(LOSSSUM) AS LOSSSUM "
                + " FROM PRPNODUTYACCOUNTSUM WHERE DrawMonth='"
                + strYearMonth + "'"
                + " AND certitype ='05'"
                + " AND ComCode IN " + strWhereCom;
        strSQL1 += " AND riskCode  IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL1 += ",'" + riskCode[i] + "'";
        }
        strSQL1 += ")";
        strSQL1 += " GROUP BY ComCode,RiskCode,Currency ";
        ResultSet resultSet = statDBpool.query(strSQL);
        
        AccSubVoucherSchema accSubVoucherSchema = null;
        
        while (resultSet.next()) {
            dblDraw = Str.round(resultSet.getDouble("LOSSSUM"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6502");
                accSubVoucherSchema.setDirectionIdx("01/01/");
                accSubVoucherSchema.setF23("01/01");
                accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "直接业务提取未决赔款准备金已发生已报告（已报案未立案）");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("2602");
                accSubVoucherSchema.setDirectionIdx("01/01/");
                accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                accSubVoucherSchema.setF23("01/01");
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "直接业务未决赔款准备金已发生已报告（已报案未立案）");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        resultSet.close();
        
        ResultSet resultSet1 = statDBpool.query(strSQL1);
        while (resultSet1.next()) {
            dblDraw = Str.round(resultSet1.getDouble("LOSSSUM"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6502");
                accSubVoucherSchema.setDirectionIdx("02/01/");
                accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                accSubVoucherSchema.setF23("02/01/");
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "分入业务提取未决赔款准备金已发生已报告（已报案未立案）");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("2602");
                accSubVoucherSchema.setDirectionIdx("02/01/");
                accSubVoucherSchema.setF23("02/01");
                accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "分入业务未决赔款准备金已发生已报告（已报案未立案）");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        resultSet1.close();
        String strCurrency = "";
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            accSubVoucherSchema = blAccVoucher.getBLAccSubVoucher().getArr(i);
            strCurrency = accSubVoucherSchema.getCurrency();
            if (!strCurrency.equals("CNY")) {
                dblExchangeRate = PubTools.getExchangeRate(strCurrency, "CNY",
                        iDrawDate);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setExchangeRate(
                        "" + dblExchangeRate);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getDebitSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setDebitDest(
                        "" + dblDrawBack);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getCreditSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setCreditDest(
                        "" + dblDrawBack);
            }
        }
        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return strBranchCode + " 没有生成未决已发生已报告（已发生未立案）准备金凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());

        blAccVoucher.save(dbpool);
        strReturn = strBranchCode + " 生成未决已发生已报告（已发生未立案）准备金凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }

    /**
     * @desc 应收分XXXXX已发生已报告（已报案未立案）送XXXXX
     * @author 徐少将 20070702
     * @param dbpool
     * @param iAccBookType
     * @param iAccBookCode
     * @param iCenterCode
     * @param iDrawDate
     * @param iOperatorCode
     * @return
     * @throws Exception
     */
    public String transIBNRLossAccount2(DbPool dbpool,DbPool statDBpool,  String iAccBookType,
            String iAccBookCode, String iCenterCode, String iDrawDate,
            String iOperatorCode) 
    throws Exception {
        String strReturn = "";
        double dblExchangeRate = 0;
        double dblDraw = 0;
        double dblDrawBack = 0;
        PubTools pubTools = new PubTools();
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        String strBranchCode = iCenterCode;
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        String strSQL = "";
        String strSQL1 = "";
        String strYearMonth = pubTools.getPreYearMonth(iDrawDate, "1", 0);
        
        blAccVoucher.getBLAccSubVoucher().initArr();
        
        BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
        blAccMonthTrace.initArr();
        String strWhere="  centercode='"+iCenterCode+"'"+
        			  " and yearmonth='"+strYearMonth+"'";
        blAccMonthTrace.query(strWhere, 0);
        if(blAccMonthTrace.getArr(0).getAccMonthStat().equals("2"))
        {   
        	strWhere=" comcode='"+iCenterCode+"'";
        	blPrpDcompany.initArr();
        	blPrpDcompany.query(dbpool,strWhere, 0);
        	strBranchCode=blPrpDcompany.getArr(0).getUpperComCode();
        }
        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(iAccBookType);
        accMainVoucherSchema.setAccBookCode(iAccBookCode);
        accMainVoucherSchema.setYearMonth(strYearMonth);
        accMainVoucherSchema.setCenterCode(strBranchCode);
        accMainVoucherSchema.setBranchCode(strBranchCode);
        accMainVoucherSchema.setVoucherDate(iDrawDate);
        accMainVoucherSchema.setAuxNumber("1");
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setGenerateWay("9"); 
        accMainVoucherSchema.setOperatorBranch(strBranchCode);
        accMainVoucherSchema.setVoucherFlag("1");
        if (strYearMonth.trim().substring(4, 6).equals("JS")) {
            accMainVoucherSchema.setVoucherType("6");
        } else {
            accMainVoucherSchema.setVoucherType("1");
        }
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

        
		blPrpDcompany.initArr();
		
		String strWhereCom = "";
		if ("00000000".equals(iCenterCode)) {
			String[] arrComCode = blPrpDcompany.getLowerComCodeNew(dbpool,
					iCenterCode);
			strWhereCom = "('" + iCenterCode + "'";
			for (int j = 0; j < arrComCode.length; j++) {
				strWhereCom += ",'" + arrComCode[j] + "'";
			}
			strWhereCom += ")";
		} else {
        	strWhereCom = "('" + iCenterCode + "')";
        }

        
        strSQL = "SELECT ComCode,RiskCode,Currency,SUM(LOSSSUM) AS LOSSSUM "
                + " FROM PRPNODUTYACCOUNTSUM  WHERE DrawMonth='"
                + strYearMonth + "'"
                + " AND certitype ='06'"
                + " AND ComCode IN " + strWhereCom;
        strSQL += " AND riskCode NOT IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL += ",'" + riskCode[i] + "'";
        }
        strSQL += ")";
        strSQL += " GROUP BY ComCode,RiskCode,Currency ";
        
        strSQL1 = "SELECT ComCode,RiskCode,Currency,SUM(LOSSSUM) AS LOSSSUM "
                + " FROM PRPNODUTYACCOUNTSUM WHERE DrawMonth='"
                + strYearMonth + "'"
                + " AND certitype ='06'"
                + " AND ComCode IN " + strWhereCom;
        strSQL1 += " AND riskCode  IN ('" + riskCode[0] + "'";
        for (int i = 1; i < riskCode.length; i++) {
            strSQL1 += ",'" + riskCode[i] + "'";
        }
        strSQL1 += ")";
        strSQL1 += " GROUP BY ComCode,RiskCode,Currency ";
        ResultSet resultSet = statDBpool.query(strSQL);
        
        AccSubVoucherSchema accSubVoucherSchema = null;
        
        while (resultSet.next()) {
            dblDraw = Str.round(resultSet.getDouble("LOSSSUM"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("1213");
                accSubVoucherSchema.setDirectionIdx("01/01/");
                accSubVoucherSchema.setF23("01/01");
                accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "直接业务已发生已报告应收分XXXXX未决赔款准备金（已报案未立案）");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6201");
                accSubVoucherSchema.setDirectionIdx("01/01/");
                accSubVoucherSchema.setF05(resultSet.getString("RiskCode"));
                accSubVoucherSchema.setF23("01/01");
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet.getString("ComCode"));
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "直接业务已发生已报告应收分XXXXX未决赔款准备金（已报案未立案）");
                accSubVoucherSchema
                        .setCurrency(resultSet.getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }
        }
        resultSet.close();
        
        ResultSet resultSet1 = statDBpool.query(strSQL1);
        while (resultSet1.next()) {
            dblDraw = Str.round(resultSet1.getDouble("LOSSSUM"), 2);
            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("1213");
                accSubVoucherSchema.setDirectionIdx("02/01/");
                accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                accSubVoucherSchema.setF23("02/01");
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "分入业务已发生已报告应收分XXXXX未决赔款准备金（已报案未立案）");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1"); 
                accSubVoucherSchema.setDebitSource("" + dblDraw);
                accSubVoucherSchema.setDebitDest("" + dblDraw); 
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

            
            if (dblDraw != 0) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setCenterCode(accMainVoucherSchema
                        .getCenterCode());
                accSubVoucherSchema.setAccBookType(accMainVoucherSchema
                        .getAccBookType());
                accSubVoucherSchema.setAccBookCode(accMainVoucherSchema
                        .getAccBookCode());
                accSubVoucherSchema.setYearMonth(accMainVoucherSchema
                        .getYearMonth());
                accSubVoucherSchema.setItemCode("6201");
                accSubVoucherSchema.setDirectionIdx("02/01/");
                accSubVoucherSchema.setF23("02/01");
                accSubVoucherSchema.setF05(resultSet1.getString("RiskCode"));
                accSubVoucherSchema.setF26(strBranchCode);
                accSubVoucherSchema.setF28(resultSet1.getString("ComCode"));
                accSubVoucherSchema
                        .setRemark("提取" + iDrawDate + "分入业务已发生已报告应收分XXXXX未决赔款准备金（已报案未立案）");
                accSubVoucherSchema.setCurrency(resultSet1
                        .getString("Currency"));
                accSubVoucherSchema.setExchangeRate("1");
                accSubVoucherSchema.setCreditSource("" + dblDraw);
                accSubVoucherSchema.setCreditDest("" + dblDraw);
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            }

        }

        resultSet1.close();
        String strCurrency = "";
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            accSubVoucherSchema = blAccVoucher.getBLAccSubVoucher().getArr(i);
            strCurrency = accSubVoucherSchema.getCurrency();
            if (!strCurrency.equals("CNY")) {
                dblExchangeRate = PubTools.getExchangeRate(strCurrency, "CNY",
                        iDrawDate);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setExchangeRate(
                        "" + dblExchangeRate);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getDebitSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setDebitDest(
                        "" + dblDrawBack);
                dblDrawBack = Double.parseDouble(blAccVoucher
                        .getBLAccSubVoucher().getArr(i).getCreditSource());
                dblDrawBack = Str.round(dblDrawBack * dblExchangeRate, 2);
                blAccVoucher.getBLAccSubVoucher().getArr(i).setCreditDest(
                        "" + dblDrawBack);
            }
        }
        if (blAccVoucher.getBLAccSubVoucher().getSize() == 0)
            return strBranchCode + " 没有生成已发生已报告应收分XXXXX未决赔款准备金（已报案未立案）凭证";

        
        for (int i = 0; i < blAccVoucher.getBLAccSubVoucher().getSize(); i++) {
            blAccVoucher.getBLAccSubVoucher().getArr(i).setSuffixNo(
                    "" + (i + 1));
            blAccVoucher.getBLAccSubVoucher().createRawArticle(
                    blAccVoucher.getBLAccSubVoucher().getArr(i), "");
        }

        blAccVoucher.getBLAccSubVoucher().voucherComp("101");
        blAccVoucher.getBLAccSubVoucher().voucherOrder();

        blAccVoucher.setBLAccVoucherArticle(blAccVoucher.getBLAccSubVoucher()
                .genVoucherArticle());

        blAccVoucher.save(dbpool);
        strReturn = strBranchCode + " 生成已发生已报告应收分XXXXX未决赔款准备金（已报案未立案）凭证: "
                + blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        return strReturn;
    }
    /**
     * @author lijibin 20060804 根据车辆种类和使用性质获取XXXXX监会车辆种类
     * @param dbpool
     * @param iCarKindCode
     * @param iUseNatureCode
     * @return
     * @throws Exception
     */
    public String queryCarNatureCode(DbPool dbpool, String iCarKindCode,
            String iUseNatureCode) throws Exception {
        String strCarNatureCode = "";
        String strSQL = "";
        strSQL = "SELECT CarNatureCode FROM PrpDcarNature WHERE CarKindCode='"
                + iCarKindCode + "' AND UseNatureCode='" + iUseNatureCode + "'";

        ResultSet rs = dbpool.query(strSQL);
        if (rs.next()) {
            strCarNatureCode = rs.getString(1);
        }
        return strCarNatureCode;
    }
    /**
     * @author lijibin 20061025
     * @desc 根据车辆种类和使用性质获取交强XXXXX九大类代码
     * @param CarKindCode
     * @param UseNatureCode        
     * @return strCarNatureCode   
     * @throws Exception
     */
    public String getCarNatureCode(String iCarKindCode, String iUseNatureCode)
            throws Exception, UserException {
        String strCarNatureCode = "";
        	if(iCarKindCode.equals("A0")
                    && iUseNatureCode.equals("8A")){      	
            strCarNatureCode = "01";  
        }else if(iCarKindCode.equals("A0") 
                && (iUseNatureCode.equals("8B")
                   || iUseNatureCode.equals("8C")
                   || iUseNatureCode.equals("8D"))){
            strCarNatureCode = "02";  
        }else if(iCarKindCode.equals("A0")
                && (iUseNatureCode.equals("9A")
                   || iUseNatureCode.equals("9B")
                   || iUseNatureCode.equals("9C")
                   || iUseNatureCode.equals("9D")
                   || iUseNatureCode.equals("9E"))){
            strCarNatureCode = "03";  
        }else if(iCarKindCode.equals("H0")
                && (iUseNatureCode.equals("8B")
                   || iUseNatureCode.equals("8C")
                   || iUseNatureCode.equals("8D")
                   || iUseNatureCode.equals("8A"))){
            strCarNatureCode = "04";  
        }else if(iCarKindCode.equals("H0")
                && (iUseNatureCode.equals("9A")
                   || iUseNatureCode.equals("9B")
                   || iUseNatureCode.equals("9C")
                   || iUseNatureCode.equals("9D")
                   || iUseNatureCode.equals("9E"))){
            strCarNatureCode = "05";  
        }else if(iCarKindCode.substring(0,1).equals("T")){
            strCarNatureCode = "06";  
        }else if(iCarKindCode.substring(0,1).equals("M")){
            strCarNatureCode = "07";  
        }else if(iCarKindCode.substring(0,1).equals("J")){
            strCarNatureCode = "08";  
       
        }else if(iCarKindCode.substring(0,1).equals("G")){   
            strCarNatureCode = "09";  
        }else{
            strCarNatureCode = "99";  
        }
        return strCarNatureCode;
    }
    /**
     * @desc 日志输出
     * @param iLog
     * @throws Exception
     */
    public void logPrintln(String iLog) throws Exception
    {
      ChgDate chgDate = new ChgDate();
      logger.info(chgDate.getCurrentTime("yyyy-MM-dd hh:mm:ss")+"><"+iLog);
    }
    /**
     * 主函数
     * 
     * @param args
     *            参数列表
     */
    public static void main(String[] args) {
        
    }
}
