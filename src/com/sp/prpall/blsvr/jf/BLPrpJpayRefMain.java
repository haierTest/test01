package com.sp.prpall.blsvr.jf;

import java.lang.reflect.Modifier;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;

import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.*;
import com.sp.prpall.schema.*;
import com.sp.account.blsvr.*;
import com.sp.account.dbsvr.DBAccMainVoucher;
import com.sp.account.schema.*;
import com.sp.utiall.blsvr.*;
import com.sp.utility.string.Str;
import com.sp.prpall.pubfun.*;
import com.sp.prpall.dbsvr.cb.DBPrpCplan;
import com.sp.prpall.blsvr.cb.BLPrpCplan;
import com.sp.prpall.dbsvr.cb.DBPrpCPplan;
import com.sp.prpall.blsvr.cb.BLPrpCPplan;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.account.dbsvr.DBAccBankAccount;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;
import com.sp.reins.dbsvr.DBPrpDreins;
import com.sp.reins.blsvr.BLFjSettle;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.DBManager;
import com.sp.prpall.dbsvr.jf.DBPrpJsettle;
import com.sp.prpall.blsvr.jf.BLPrpJsettle;
import com.sp.prpall.dbsvr.jf.DBPrpJtaxSettle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 定义PrpJpayRefMain的BL类
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>@createdate 2005-05-27</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJpayRefMain{
	protected final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    private Vector coinsSchemas = new Vector();
    private BLPrpJpayRefDetail blPrpJpayRefDetail = new BLPrpJpayRefDetail();
    private BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
    private BLPrpJpayRefRec blPrpJpayRefRecCoins = new BLPrpJpayRefRec();
    private BLPrpJpayCommission blPrpJpayCommission = new BLPrpJpayCommission();
    private BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
    private boolean blPackageFlag = false;
    private static Connection conn = null;
	private static CallableStatement cstmt = null;

    /**
     * 构造函数
     */
    public BLPrpJpayRefMain(){
    }

    /**
     *初始化记录
     *@param 无
     *@return 无
     *@throws Exception
     */
    public void initArr() throws Exception
    {
      schemas = new Vector();
    }

    public void initCoinsArr() throws Exception
    {
      coinsSchemas = new Vector();
    }

    /**
     *增加一条PrpJpayRefMainSchema记录
     *@param iPrpJpayRefMainSchema PrpJpayRefMainSchema
     *@throws Exception
     */
    public void setArr(PrpJpayRefMainSchema iPrpJpayRefMainSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpJpayRefMainSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }

    public void setCoinsArr(PrpJpayRefMainSchema iPrpJpayRefMainSchema) throws Exception
    {
      try
      {
        coinsSchemas.add(iPrpJpayRefMainSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }

    /**
     *得到一条PrpJpayRefMainSchema记录
     *@param index 下标
     *@return 一个PrpJpayRefMainSchema对象
     *@throws Exception
     */
    public PrpJpayRefMainSchema getArr(int index) throws Exception
    {
      PrpJpayRefMainSchema prpJpayRefMainSchema = null;
      try
      {
        prpJpayRefMainSchema = (PrpJpayRefMainSchema)this.schemas.get(index);
      }
      catch(Exception e)
      {
        throw e;
      }
      return prpJpayRefMainSchema;
    }

    public PrpJpayRefMainSchema getCoinsArr(int index) throws Exception
    {
      PrpJpayRefMainSchema prpJpayRefMainSchema = null;
      try
      {
        prpJpayRefMainSchema = (PrpJpayRefMainSchema)this.coinsSchemas.get(index);
      }
      catch(Exception e)
      {
        throw e;
      }
      return prpJpayRefMainSchema;
    }

    /**
     *删除一条PrpJpayRefMainSchema记录
     *@param index 下标
     *@throws Exception
     */
    public void remove(int index) throws Exception
    {
      try
      {
        this.schemas.remove(index);
      }
      catch(Exception e)
      {
        throw e;
      }
    }

    /**
     *得到schemas记录数
     *@return schemas记录数
     *@throws Exception
     */
    public int getSize() throws Exception
    {
      return this.schemas.size();
    }

    public int getCoinsSize() throws Exception
    {
      return this.coinsSchemas.size();
    }

    /**
     *@desc 给私有对象BLPrpJpayRefDetail赋值
     *@return
     *@throws Exception
     */
    public void setBLPrpJpayRefDetail(BLPrpJpayRefDetail iBLPrpJpayRefDetail) throws Exception
    {
      this.blPrpJpayRefDetail = iBLPrpJpayRefDetail;
    }


    /**
     *@desc 得到一条BLPrpJpayRefDetail对象
     *@return 一个BLPrpJpayRefDetail对象
     *@throws Exception
     */
    public BLPrpJpayRefDetail getBLPrpJpayRefDetail() throws Exception
    {
      return this.blPrpJpayRefDetail;
    }
    
    
    /**
     *@desc 给私有对象BLPrpJpayCommission赋值
     *@return
     *@throws Exception
     */
    public void setBLPrpJpayCommission(BLPrpJpayCommission iBLPrpJpayCommission) throws Exception
    {
      this.blPrpJpayCommission = iBLPrpJpayCommission;
    }

    /**
     *@desc 得到一条BLPrpJpayCommission对象
     *@return 一个BLPrpJpayCommission对象
     *@throws Exception
     */
    public BLPrpJpayCommission getBLPrpJpayCommission() throws Exception
    {
      return this.blPrpJpayCommission;
    }
    
    
    /**
     *@desc 给私有对象BLPrpJpayRefRec赋值
     *@return
     *@throws Exception
     */
    public void setBLPrpJpayRefRec(BLPrpJpayRefRec iBLPrpJpayRefRec) throws Exception
    {
      this.blPrpJpayRefRec = iBLPrpJpayRefRec;
    }

    /**
     *@desc 得到一条BLPrpJpayRefRec对象
     *@return 一个BLPrpJpayRefRec对象
     *@throws Exception
     */
    public BLPrpJpayRefRec getBLPrpJpayRefRec() throws Exception
    {
      return this.blPrpJpayRefRec;
    }

    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart) throws UserException,Exception
    {
      this.query(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }

    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      if (iLimitCount > 0 && dbPrpJpayRefMain.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefMain.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRefMain WHERE " + iWherePart;
        schemas = dbPrpJpayRefMain.findByConditions(strSqlStatement);
      }
    }

    /**
     *联合PrpJpayRefRec表查询 SangMingqian 20050613
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryWithRec(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      if (iLimitCount > 0 && dbPrpJpayRefMain.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefMain.queryWithRec");
      }
      else
      {
        initArr();
        strSqlStatement =" SELECT distinct PrpJpayRefMain.* FROM PrpJpayRefMain,PrpJpayRefRec "
            + " WHERE PrpJpayRefMain.PayRefNo=PrpJpayRefRec.PayRefNo"
            
            
            + " AND "            
            + iWherePart;
        schemas = dbPrpJpayRefMain.findByConditions(strSqlStatement);
      }
    }
    
    /**
     *联合PrpJpayRefRec表查询 zhanglingjian
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryWithRecByLimitCountOld(String iWherePart) throws UserException,Exception
    {
    	String strSqlStatement = "";
    	int count = Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim());
    	DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
    	initArr();
        String sql =" SELECT distinct PrpJpayRefMain.* FROM PrpJpayRefMain,PrpJpayRefRec "
            + " WHERE PrpJpayRefMain.PayRefNo=PrpJpayRefRec.PayRefNo"
            + " AND "
            + iWherePart;
        strSqlStatement = " SELECT * FROM ( " + sql + " ) WHERE ROWNUM<=" + count;
        schemas = dbPrpJpayRefMain.findByConditions(strSqlStatement);
    }
    
    /**
     *联合PrpJpayRefRec表查询先查出打包号，然后把打包号下的所有发票查出来 xushaojiang 20070813
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryWithRecNew(String iWherePart,String iOrderPart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      if (iLimitCount > 0 && dbPrpJpayRefMain.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefMain.queryWithRecNew");
      }
      else
      {
        initArr();
        strSqlStatement ="SELECT  PrpJpayRefMain.* FROM PrpJpayRefMain where payrefno in("
            + " SELECT  PrpJpayRefMain.payrefno FROM PrpJpayRefMain,PrpJpayRefRec "
            + " WHERE PrpJpayRefMain.PayRefNo=PrpJpayRefRec.PayRefNo"
            + " AND PrpJpayRefMain.VisaSerialNo=PrpJpayRefRec.VisaSerialNo AND "
            + iWherePart+" )"+iOrderPart;
        schemas = dbPrpJpayRefMain.findByConditions(strSqlStatement);
      }
    }
    
    /**
     *联合PrpJpayRefRec表查询先查出打包号，然后把打包号下的所有发票查出来 zhanglingjian
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryWithRecByLimitCount(String iWherePart,String iOrderPart) throws UserException,Exception
    {
      String strSqlStatement = "";
      int count = Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim());
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      initArr();
      String sql ="SELECT  PrpJpayRefMain.* FROM PrpJpayRefMain where payrefno in("
            + " SELECT  PrpJpayRefMain.payrefno FROM PrpJpayRefMain,PrpJpayRefRec "
            + " WHERE PrpJpayRefMain.PayRefNo=PrpJpayRefRec.PayRefNo"
            + " AND PrpJpayRefMain.VisaSerialNo=PrpJpayRefRec.VisaSerialNo AND "
            + iWherePart+" )"+iOrderPart;
      strSqlStatement = " SELECT * FROM ( " + sql + " ) WHERE ROWNUM<=" + count;
      schemas = dbPrpJpayRefMain.findByConditions(strSqlStatement);
    }
    
    /**
     *联合PrpJpayCommission表查询 
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryWithCommission(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      if (iLimitCount > 0 && dbPrpJpayRefMain.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefMain.queryWithCommission");
      }
      else
      {
        initArr();
        strSqlStatement =" SELECT distinct PrpJpayRefMain.* FROM PrpJpayRefMain,PrpJpayCommission "
            + " WHERE PrpJpayRefMain.PayRefNo=PrpJpayCommission.PayRefNo AND "
            + iWherePart;
        schemas = dbPrpJpayRefMain.findByConditions(strSqlStatement);
      }
    }
    
    /**
     *联合PrpJpayCommission表查询 zhanglingjian
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryWithCommissionByLimitCount(String iWherePart) throws UserException,Exception
    {
    	String strSqlStatement = "";
    	int count = Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim());
    	DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
    	initArr();
        String sql =" SELECT distinct PrpJpayRefMain.* FROM PrpJpayRefMain,PrpJpayCommission "
            + " WHERE PrpJpayRefMain.PayRefNo=PrpJpayCommission.PayRefNo AND "
            + iWherePart;
        strSqlStatement = " SELECT * FROM ( " + sql + " ) WHERE ROWNUM<=" + count;
        schemas = dbPrpJpayRefMain.findByConditions(strSqlStatement);
    }
    
    /**
     *add by huangen 收付款确认打包发票查询，增加XX号列
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryWithPayRefRec(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      if (iLimitCount > 0 && dbPrpJpayRefMain.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefMain.queryWithPayRefRec");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT distinct " 
        	+ " PrpJpayRefMain.PAYREFNO,PrpJpayRefMain.SERIALNO,PrpJpayRefMain.PAYREFNOTYPE,PrpJpayRefMain.VISACODE,PrpJpayRefMain.VISASERIALNO,"
        	+ " PrpJpayRefMain.PRINTDATE,PrpJpayRefMain.PRINTERCODE,PrpJpayRefMain.VISAHANDLER,PrpJpayRefMain.PAYREFNAME,PrpJpayRefMain.IDENTIFYTYPE,"
        	+ " PrpJpayRefMain.IDENTIFYNUMBER,decode(PrpJpayRefMain.PAYREFNO,null,null,PrpJpayRefRec.policyno) as REMARK ,"
        	+ " PrpJpayRefMain.PACKAGECODE,PrpJpayRefMain.PACKAGEUNIT,PrpJpayRefMain.PACKAGEDATE,"
        	+ " PrpJpayRefMain.PAYREFCODE,PrpJpayRefMain.PAYREFUNIT,PrpJpayRefMain.PAYREFDATE,PrpJpayRefMain.CURRENCY2,PrpJpayRefMain.PAYREFFEE,"
        	+ " PrpJpayRefMain.CENTERCODE,PrpJpayRefMain.BRANCHCODE,PrpJpayRefMain.ACCBOOKTYPE,PrpJpayRefMain.ACCBOOKCODE,PrpJpayRefMain.YEARMONTH,"
        	+ " PrpJpayRefMain.VOUCHERNO,PrpJpayRefMain.FLAG "      	        	
        	+ " FROM PrpJpayRefMain,PrpJpayRefRec "
            + " WHERE PrpJpayRefMain.PayRefNo=PrpJpayRefRec.PayRefNo"
            + " AND (PrpJpayRefMain.VisaSerialNo=PrpJpayRefRec.VisaSerialNo"
            + " OR PrpJpayRefMain.VisaSerialNo IS NULL) AND "
            + iWherePart;
        schemas = dbPrpJpayRefMain.findByConditions(strSqlStatement);
      }
    }      
    
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart) throws UserException,Exception
    {
      this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }

    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      if (iLimitCount > 0 && dbPrpJpayRefMain.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefMain.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRefMain WHERE " + iWherePart;
        schemas = dbPrpJpayRefMain.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpJpayRefMain.setSchema((PrpJpayRefMainSchema)schemas.get(i));
        dbPrpJpayRefMain.insert(dbpool);
      }
    }

    /**
     *不带dbpool的XXXXX存方法
     *@param 无
     *@return 无
     */
    public void save() throws Exception
    {
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        save(dbpool);
        dbpool.commitTransaction();
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally {
        dbpool.close();
      }
    }

    /**
     *发票收付款确认的生成方法
     *@param 无
     *@return 无
     */
    public String checkInvoice(String strCondition, String[] strVisaSerialNo,PrpJpayRefDetailSchema prpJpayRefDetailSchemaParam) throws
        UserException, SQLException, Exception {
      String serialno = "1234567"; 
      BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
      BLPrpJpayRefDetail blPrpJpayRefDetail = new BLPrpJpayRefDetail();
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      PrpJpayRefRecSchema prpJpayRefRecSchema = new PrpJpayRefRecSchema();
      PrpJpayRefMainSchema prpJpayRefMainSchema = new PrpJpayRefMainSchema();
      PrpJpayRefDetailSchema prpJpayRefDetailSchema = new PrpJpayRefDetailSchema();
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE); 
        dbpool.beginTransaction();
        for (int i = 0; i < strVisaSerialNo.length; i++) {
          blPrpJpayRefRec.query(strCondition + " AND VisaSerialNo = '" + strVisaSerialNo[i] + "'");
          Date date = new Date();
          long seed = date.getTime();
          Random random = new Random(seed);
          String strPayRefNo = Long.toString(random.nextLong());
          for(int j = 0; j<blPrpJpayRefRec.getSize();j++){
            prpJpayRefRecSchema = blPrpJpayRefRec.getArr(j);
            
            dbPrpJplanFee = new DBPrpJplanFee();
            dbPrpJplanFee.getInfo(dbpool, prpJpayRefRecSchema.getCertiType(),
                                  prpJpayRefRecSchema.getCertiNo(),
                                  prpJpayRefRecSchema.getSerialNo(),
                                  prpJpayRefRecSchema.getPayRefReason());
            if (!dbPrpJplanFee.getRealPayRefFee().equals("")) {
              throw new UserException( -96, -1167, "", "已经做过到款确认！");
            }
            dbPrpJplanFee.setRealPayRefFee(dbPrpJplanFee.getPayRefFee());
            dbPrpJplanFee.update(dbpool);
            
            dbPrpJpayRefRec = new DBPrpJpayRefRec();
            dbPrpJpayRefRec.getInfo(dbpool, prpJpayRefRecSchema.getCertiType(),
                                    prpJpayRefRecSchema.getCertiNo(),
                                    prpJpayRefRecSchema.getSerialNo(),
                                    prpJpayRefRecSchema.getPayRefReason(),
                                    prpJpayRefRecSchema.getPayRefTimes());
            if (!dbPrpJpayRefRec.getPayRefDate().equals("")) {
              throw new UserException( -96, -1167, "", "已经做过到款确认！");
            }
            dbPrpJpayRefRec.setPayRefDate(prpJpayRefDetailSchemaParam.getPayRefDate());
            
            dbPrpJpayRefRec.setIdentifyType("1");
            dbPrpJpayRefRec.setIdentifyNumber("1");
            
            dbPrpJpayRefRec.setPayRefNo(strPayRefNo);
            dbPrpJpayRefRec.update(dbpool);
          }
          
          String strSqlStatement = "SELECT VisaCode,visaserialno,PrintDate,PrinterCode,VisaHandler,"
              + " PayRefName,IdentifyType,IdentifyNumber, Remark,Sum(PayRefFee) as PayRefFee FROM PrpJpayRefRec WHERE "
              + " VisaSerialNo = '" + strVisaSerialNo[i] + "' "
              + " Group By VisaCode,visaserialno,PrintDate,PrinterCode,VisaHandler, PayRefName,IdentifyType,IdentifyNumber, Remark";
          ResultSet resultSet = dbpool.query(strSqlStatement);
          prpJpayRefMainSchema = new PrpJpayRefMainSchema();
          if(resultSet.next()){
            prpJpayRefMainSchema.setPayRefNo(strPayRefNo);
            prpJpayRefMainSchema.setSerialNo("1");
            prpJpayRefMainSchema.setPayRefNoType("1");
            prpJpayRefMainSchema.setVisaCode(resultSet.getString("VisaCode"));
            prpJpayRefMainSchema.setVisaSerialNo(resultSet.getString("visaserialno"));
            prpJpayRefMainSchema.setPrintDate("" + resultSet.getDate("PrintDate"));
            prpJpayRefMainSchema.setPrinterCode(resultSet.getString("PrinterCode"));
            prpJpayRefMainSchema.setVisaHandler(resultSet.getString("VisaHandler"));
            prpJpayRefMainSchema.setPayRefName(resultSet.getString("PayRefName"));
            prpJpayRefMainSchema.setIdentifyType(resultSet.getString("IdentifyType"));
            prpJpayRefMainSchema.setIdentifyNumber(resultSet.getString("IdentifyNumber"));
            prpJpayRefMainSchema.setRemark(resultSet.getString("Remark"));
            prpJpayRefMainSchema.setCurrency2(prpJpayRefDetailSchemaParam.getCurrency2());
            prpJpayRefMainSchema.setPayRefFee(resultSet.getString("PayRefFee"));
            this.setArr(prpJpayRefMainSchema);
            /*
             PayRefCode等凭证相关信息生成凭证时写入
            */
            
            prpJpayRefDetailSchema = new PrpJpayRefDetailSchema();
            prpJpayRefDetailSchema.setPayRefNo(strPayRefNo);
            prpJpayRefDetailSchema.setSerialNo("1");
            prpJpayRefDetailSchema.setPayWay(prpJpayRefDetailSchemaParam.
                                               getPayWay());
            prpJpayRefDetailSchema.setCheckType(prpJpayRefDetailSchemaParam.
                                                  getCheckType());
            prpJpayRefDetailSchema.setCheckNo(prpJpayRefDetailSchemaParam.
                                                getCheckNo());
            prpJpayRefDetailSchema.setBankCode(prpJpayRefDetailSchemaParam.
                                                 getBankCode());
            prpJpayRefDetailSchema.setBankName(prpJpayRefDetailSchemaParam.
                                                 getBankName());
            
            if(prpJpayRefDetailSchemaParam.getPayWay().substring(0,1).equals("2"))
            {
            	prpJpayRefDetailSchema.setAccountNo(prpJpayRefDetailSchemaParam.
                        getAccountNo());
            }

            prpJpayRefDetailSchema.setAccountName(prpJpayRefDetailSchemaParam.
                                                    getAccountName());
            prpJpayRefDetailSchema.setCurrency2(prpJpayRefDetailSchemaParam.
                                                  getCurrency2());
            prpJpayRefDetailSchema.setPayRefFee(prpJpayRefMainSchema.
                                                  getPayRefFee());
            prpJpayRefDetailSchema.setPayRefDate(prpJpayRefDetailSchemaParam.
                                                   getPayRefDate());
            prpJpayRefDetailSchema.setFlag(prpJpayRefDetailSchemaParam.getFlag());
            blPrpJpayRefDetail.setArr(prpJpayRefDetailSchema);

          }
          else{
            throw new UserException( -96, -1167, "", "到款确认失败！");
          }
          resultSet.close();
        }
        this.save();
        blPrpJpayRefDetail.save();
        dbpool.commitTransaction();
        dbpool.close();
      }
      catch (UserException userexception) {
        dbpool.rollbackTransaction();
        dbpool.close();
        serialno = "";
        throw userexception;
      }
      catch (SQLException sqlexception) {
        dbpool.rollbackTransaction();
        dbpool.close();
        serialno = "";
        throw sqlexception;
      }
      catch (Exception exception) {
        dbpool.rollbackTransaction();
        dbpool.close();
        serialno = "";
        throw exception;
      }
      finally {
        dbpool.close();
      }
      return serialno;
    }

    /**
     *收付款确认 SangMingqian 20050615
     *@param iAuxNumber 附件张数
     *@throws Exception
    */
    public BLAccMainVoucher payRefVerify(String iAuxNumber) throws UserException,SQLException,Exception
    {
      String strWherePart = "";
      int intNoCount = 1;  
      
      BLAccMainVoucher blAccMainVoucher = new BLAccMainVoucher();
      BLAccMainVoucher blAccMainVoucherHeadOffice = new BLAccMainVoucher();
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();

      
      if(blPackageFlag==false)
        this.blPrpJpayRefRec.queryInvioceCoins(this.schemas);
      

      
      strWherePart = " PayRefNo IN ('"+this.getArr(0).getPayRefNo()+"'";
      String strPayRefNoTmp = this.getArr(0).getPayRefNo();
      for(int i=1; i<this.getSize(); i++)
      {
        
        if(this.getArr(i).getPayRefNo().equals(strPayRefNoTmp))
        {
          continue;
        }else
        {
          intNoCount ++;
          strPayRefNoTmp = this.getArr(i).getPayRefNo();
          strWherePart += ",'" + this.getArr(i).getPayRefNo() + "'";
        }
      }

      strWherePart += ")";

      this.blPrpJpayRefRec.query(strWherePart,0);
      
      String strPayRefDate = this.getArr(0).getPayRefDate();
      String strPayRefCode = this.getArr(0).getPayRefCode();
      String strPayRefNoType = this.getArr(0).getPayRefNoType();
      
      BLAccVoucher blAccVoucher = this.genAccVoucher(strPayRefDate,strPayRefCode,strPayRefNoType,iAuxNumber);
      BLAccVoucher blAccVoucherHeadOffice = this.genAccVoucherHeadOffice(strPayRefDate,strPayRefCode,strPayRefNoType,iAuxNumber);
      String strAccBookType = blAccVoucher.getBLAccMainVoucher().getArr(0).getAccBookType();
      String strAccBookCode = blAccVoucher.getBLAccMainVoucher().getArr(0).getAccBookCode();
      String strCenterCode = blAccVoucher.getBLAccMainVoucher().getArr(0).getCenterCode();
      String strBranchCode = blAccVoucher.getBLAccMainVoucher().getArr(0).getBranchCode();
      String strYearMonth = blAccVoucher.getBLAccMainVoucher().getArr(0).getYearMonth();
      String strVoucherNo = "";
      String strVoucherNoHeadOffice = "";

      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE); 
        dbpool.beginTransaction();
        
        
        
        
        
        
        
        if(this.blPrpJplanFee.getSize()>0)
        {
          this.blPrpJplanFee.createBeforeVoucher(dbpool,
                                                 blAccVoucher.getBLAccMainVoucher().
                                                 getArr(0));
          if(this.blPrpJplanFee.getBLAccMainVoucher().getSize()>0)
            blAccMainVoucher.setArr(this.blPrpJplanFee.getBLAccMainVoucher().getArr(0));
        }


        
        blAccVoucher.save(dbpool);

        if(blAccVoucherHeadOffice.getBLAccSubVoucher().getSize()>0)
        {
          blAccVoucherHeadOffice.save(dbpool);
          strVoucherNoHeadOffice = blAccVoucherHeadOffice.getBLAccMainVoucher().getArr(0).getVoucherNo();
          blAccMainVoucherHeadOffice.setArr(blAccVoucherHeadOffice.getBLAccMainVoucher().getArr(0));
          for(int i=0; i<this.getCoinsSize(); i++)
          {
            dbPrpJpayRefMain.setSchema(this.getCoinsArr(i));
            dbPrpJpayRefMain.setAccBookType(strAccBookType);
            dbPrpJpayRefMain.setAccBookCode(strAccBookCode);
            dbPrpJpayRefMain.setCenterCode("00000000");
            dbPrpJpayRefMain.setBranchCode("00000000");
            dbPrpJpayRefMain.setYearMonth(strYearMonth);
            dbPrpJpayRefMain.setVoucherNo(strVoucherNoHeadOffice);
            dbPrpJpayRefMain.update(dbpool);
          }
          DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
          for(int i=0; i<this.blPrpJpayRefRecCoins.getSize(); i++)
          {
            dbPrpJpayRefRec.setSchema(this.blPrpJpayRefRecCoins.getArr(i));
            dbPrpJpayRefRec.setPayRefDate(strPayRefDate);
            
            dbPrpJpayRefRec.setIdentifyType("1");
            
            dbPrpJpayRefRec.update(dbpool);

            if(dbPrpJpayRefRec.getCoinsType().equals("2"))
            {
              DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
              dbPrpJplanFee.getInfo(dbpool, dbPrpJpayRefRec.getCertiType(),
                  dbPrpJpayRefRec.getCertiNo(),
                  dbPrpJpayRefRec.getSerialNo(),
                  dbPrpJpayRefRec.getPayRefReason());
              dbPrpJplanFee.setRealPayRefFee(dbPrpJpayRefRec.getPlanFee());
              dbPrpJplanFee.update(dbpool);
            }
          }
        }

        strVoucherNo = blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        
        if(this.getArr(0).getPayRefNoType().equals("6"))
        {
          BLFjSettle blFjSettle = new BLFjSettle();
          blFjSettle.updateSettle(dbpool,this.getArr(0).getPayRefNo(),"1",strPayRefCode,strVoucherNo,"2");
        }

        blAccMainVoucher.setArr(blAccVoucher.getBLAccMainVoucher().getArr(0));

        if(blAccVoucherHeadOffice.getBLAccSubVoucher().getSize()>0)
        {
          blAccMainVoucher.setArr(blAccVoucherHeadOffice.getBLAccMainVoucher().getArr(0));
        }


        
        for(int i=0; i<this.getSize(); i++)
        {
          dbPrpJpayRefMain.setSchema(this.getArr(i));
          dbPrpJpayRefMain.setAccBookType(strAccBookType);
          dbPrpJpayRefMain.setAccBookCode(strAccBookCode);
          dbPrpJpayRefMain.setCenterCode(strCenterCode);
          dbPrpJpayRefMain.setBranchCode(strBranchCode);
          dbPrpJpayRefMain.setYearMonth(strYearMonth);
          dbPrpJpayRefMain.setVoucherNo(strVoucherNo);
          dbPrpJpayRefMain.update(dbpool);
        }

        
        if(intNoCount>1&&this.blPrpJpayRefDetail.getSize()==1)
        {
          PrpJpayRefDetailSchema tempSchema = this.blPrpJpayRefDetail.getArr(0);
          this.blPrpJpayRefDetail.initArr();
          double dblPayRefFee = 0;
          strPayRefNoTmp = this.getArr(0).getPayRefNo();
          for(int i=0; i<this.getSize(); i++)
          {
            PrpJpayRefDetailSchema detailSchema = new PrpJpayRefDetailSchema();
            detailSchema.setSchema(tempSchema);
            detailSchema.setPayRefNo(this.getArr(i).getPayRefNo());
            detailSchema.setSerialNo("1");
            detailSchema.setFlag("");
            dblPayRefFee = Double.parseDouble(this.getArr(i).getPayRefFee());
            for(int j=i+1; j<this.getSize(); j++)
            {
              if(this.getArr(j).getPayRefNo().equals(strPayRefNoTmp))
              {
                i++;
                dblPayRefFee += Double.parseDouble(this.getArr(i).getPayRefFee());
              }
              else
              {
                strPayRefNoTmp = this.getArr(j).getPayRefNo();
                break;
              }
            }
            detailSchema.setPayRefFee(""+dblPayRefFee);
            this.blPrpJpayRefDetail.setArr(detailSchema);
          }
        }
        
        this.blPrpJpayRefDetail.save(dbpool);

        
        PrpJpayRefRecSchema prpJpayRefRecSchema = null;

        DBPrpJpayRefRec dbPrpJpayRefRec = null;
        DBPrpJplanFee dbPrpJplanFee = null;
        for(int i = 0;i<this.blPrpJpayRefRec.getSize();i++)
        {
          prpJpayRefRecSchema = blPrpJpayRefRec.getArr(i);

          
          if(prpJpayRefRecSchema.getCertiType().equals("E")&&Double.parseDouble(prpJpayRefRecSchema.getPlanFee())<0)
          {
            PrpJpayRefRecSchema prpJpayRefRecSchema1 = null;
            
            double cPlanFee =0;
            for(int k=0;k<this.blPrpJpayRefRec.getSize();k++)
            {
              prpJpayRefRecSchema1 = blPrpJpayRefRec.getArr(k);
              
              if((prpJpayRefRecSchema1.getCertiType().equals("P")&&
                   prpJpayRefRecSchema1.getCertiNo().equals(prpJpayRefRecSchema.getPolicyNo()))
				  ||(prpJpayRefRecSchema1.getCertiType().equals("E")&&
		             prpJpayRefRecSchema1.getPolicyNo().equals(prpJpayRefRecSchema.getPolicyNo())&&
					 !prpJpayRefRecSchema1.getCertiNo().equals(prpJpayRefRecSchema.getCertiNo())))
              {
                if(prpJpayRefRecSchema1.getPayRefReason().equals("R82")||
                    prpJpayRefRecSchema1.getPayRefReason().equals("R42")||
                    prpJpayRefRecSchema1.getPayRefReason().equals("P82")||
                    prpJpayRefRecSchema1.getPayRefReason().equals("P92"))
                {

                }
                else
                {
                  cPlanFee += Double.parseDouble(prpJpayRefRecSchema1.getPlanFee());
                }
              }
            }
            String strConditionFee = " (CertiType='P' AND CertiNo='" +
                                     prpJpayRefRecSchema.getPolicyNo() + "' AND RealPayRefFee!=0 )"
				                     + " OR (CertiType='E' AND PolicyNo='" +
                                     prpJpayRefRecSchema.getPolicyNo() + "' AND RealPayRefFee!=0 )";
            
            this.blPrpJplanFee.querySum(strConditionFee);
            if(this.blPrpJplanFee.getSize()>0)
            {
              cPlanFee += Double.parseDouble(blPrpJplanFee.getArr(0).getPlanFee());
            }
            
            if(cPlanFee==0)
            {
              throw new UserException( -96, -1167,
                                      "BLPrpJpayRefMain.payRefVerify()",
                                      prpJpayRefRecSchema.getCertiNo() +
                                      "对应的XX未做收付款确认！");
            }
            if(cPlanFee < Math.abs(Double.parseDouble(prpJpayRefRecSchema.getPlanFee())))
            {
              throw new UserException( -96, -1167,
                                      "BLPrpJpayRefMain.payRefVerify()",
                                      prpJpayRefRecSchema.getCertiNo() +
                                      "批单退费不能超过XX收费("+cPlanFee+")！");
            }
          }
          

          
          dbPrpJpayRefRec = new DBPrpJpayRefRec();
          dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
          dbPrpJpayRefRec.setPayRefDate(strPayRefDate);
          
          dbPrpJpayRefRec.setIdentifyType("1");
          
          dbPrpJpayRefRec.update(dbpool);

          
          if(prpJpayRefRecSchema.getCertiType().equals("R"))
            continue;

          
          dbPrpJplanFee = new DBPrpJplanFee();
          

          dbPrpJplanFee.getInfoForUpdate(dbpool, prpJpayRefRecSchema.getCertiType(),
                                prpJpayRefRecSchema.getCertiNo(),
                                prpJpayRefRecSchema.getSerialNo(),
                                prpJpayRefRecSchema.getPayRefReason());
          dbPrpJplanFee.setRealPayRefFee("" +
             (Double.parseDouble(Str.chgStrZero(dbPrpJplanFee.getRealPayRefFee()))
             +Double.parseDouble(prpJpayRefRecSchema.getPlanFee())));
          if(Double.parseDouble(Str.chgStrZero(dbPrpJplanFee.getPlanFeeCNY()))==0)
          {
            
            if(dbPrpJpayRefRec.getCurrency2().equals("CNY"))
              dbPrpJplanFee.setExchangeRate(dbPrpJpayRefRec.getExchangeRate());
            
            else
              dbPrpJplanFee.setExchangeRate("" +
                  PubTools.getExchangeRate(dbpool,
                  dbPrpJplanFee.getCurrency1(), "CNY", strPayRefDate));
            double dblPlanFeeCNY = Double.parseDouble(dbPrpJplanFee.getPlanFee()) *
                Double.parseDouble(dbPrpJplanFee.getExchangeRate());
            dblPlanFeeCNY = Str.round(Str.round(dblPlanFeeCNY,8),2);
            dbPrpJplanFee.setPlanFeeCNY(""+dblPlanFeeCNY);
            if(dbPrpJplanFee.getCertiType().equals("P")||dbPrpJplanFee.getCertiType().equals("E"))
              dbPrpJplanFee.setFlag("1");
          }
          dbPrpJplanFee.update(dbpool);

          
          if(prpJpayRefRecSchema.getPayRefReason().equals("R82")||
              prpJpayRefRecSchema.getPayRefReason().equals("R42")||
              prpJpayRefRecSchema.getPayRefReason().equals("P82")||
              prpJpayRefRecSchema.getPayRefReason().equals("P92"))
          {

          }
          else
          {
            
          	
            
            
            if (prpJpayRefRecSchema.getCertiType().equals("P") ||
                 prpJpayRefRecSchema.getCertiType().equals("E"))
            {
              
              DBPrpCplan dbPrpCplan = new DBPrpCplan();
              int intReturn = 0;
              if(prpJpayRefRecSchema.getCertiType().equals("P")){
              	intReturn = dbPrpCplan.getInfo(dbpool, prpJpayRefRecSchema.getCertiNo(),
                                               prpJpayRefRecSchema.getSerialNo());
                if(intReturn==0)
                {
                  dbPrpCplan.setDelinquentFee("" +
                                (Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee())) -
                                 Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
                  dbPrpCplan.update(dbpool);
                }
              }else{
                BLPrpCplan blPrpCplan = new BLPrpCplan();
                String strWhere = " EndorseNo='" + prpJpayRefRecSchema.getCertiNo()+"' ";
                blPrpCplan.query(dbpool,strWhere,0);
                if(blPrpCplan.getSize()>0){
                  intReturn = dbPrpCplan.getInfo(dbpool,blPrpCplan.getArr(0).getPolicyNo(),
                                                 blPrpCplan.getArr(0).getSerialNo());
                }
                if(intReturn==0)
                {
                  dbPrpCplan.setDelinquentFee("" +
                                (Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee())) -
                                 Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
                  dbPrpCplan.update(dbpool);
                }
              }
              
              
              DBPrpCPplan dbPrpCPplan = new DBPrpCPplan();
              if(prpJpayRefRecSchema.getCertiType().equals("P")){
                intReturn = dbPrpCPplan.getInfo(dbpool, prpJpayRefRecSchema.getCertiNo(),
                                               prpJpayRefRecSchema.getSerialNo());
                if(intReturn==0)
                {
                  dbPrpCPplan.setDelinquentFee("" +
                                (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee())) -
                                 Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
                  dbPrpCPplan.update(dbpool);
                }
              }else{
                BLPrpCPplan blPrpCPplan = new BLPrpCPplan();
                String strWhere = " EndorseNo='" + prpJpayRefRecSchema.getCertiNo()+"' ";
                blPrpCPplan.query(dbpool,strWhere,0);
                if(blPrpCPplan.getSize()>0){
                  intReturn = dbPrpCPplan.getInfo(dbpool,blPrpCPplan.getArr(0).getPolicyNo(),
                                                  blPrpCPplan.getArr(0).getSerialNo());
                }
                if(intReturn==0)
                {
                  dbPrpCPplan.setDelinquentFee("" +
                                (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee())) -
                                 Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
                  dbPrpCPplan.update(dbpool);
                }
              }
              
            }
          }
        }
        dbpool.commitTransaction();
      }
      catch(UserException userexception){
        dbpool.rollbackTransaction();
        throw userexception;
      }
      catch(SQLException sqlexception){
        dbpool.rollbackTransaction();
        throw sqlexception;
      }
      catch(Exception exception){
        dbpool.rollbackTransaction();
        throw exception;
      }
      finally {
        dbpool.close();
      }
      return blAccMainVoucher;
    }

    /**
     *取消收付款确认 SangMingqian 20050703
     *@param arrVoucherNo[] 选中凭证号
     *@throws Exception
    */
    public void payRefCancel(String iWherepart) throws UserException,SQLException,Exception
    {
      PrpJpayRefMainSchema prpJpayRefMainSchema = null;
      AccMainVoucherSchema accMainVoucherSchema = null;
      PrpJpayRefRecSchema prpJpayRefRecSchema = null;

      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      DBAccMainVoucher dbAccMainVoucher = null;
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      DBPrpCplan dbPrpCplan = new DBPrpCplan();
      DBPrpCPplan dbPrpCPplan = new DBPrpCPplan();
      DBPrpJpayRefRec dbPrpJpayRefRec = null;

      BLAccVoucher blAccVoucher = new BLAccVoucher();

      this.query(iWherepart);
      if(this.getSize()==0)
        throw new UserException( -96, -1167, "BLPrpJpayRefMain.payRefCancel", "没有要取消收付款确认得记录！");

      blAccVoucher.getBLAccMainVoucher().query(iWherepart,0);

      
      String strWherePart = "";
      strWherePart = " PayRefNo IN ('"+this.getArr(0).getPayRefNo()+"'";
      String strPayRefNoTmp = this.getArr(0).getPayRefNo();
      for(int i=1; i<this.getSize(); i++)
      {
        if(this.getArr(i).getPayRefNo().equals(strPayRefNoTmp))
        {
          continue;
        }
        else
        {
          strPayRefNoTmp = this.getArr(i).getPayRefNo();
          strWherePart += ",'" + this.getArr(i).getPayRefNo() + "'";
        }
      }
      strWherePart += ")";
      this.blPrpJpayRefRec.query(strWherePart,0);

      DbPool dbpool = new DbPool();
      
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();

        
        for(int j=0;j<blAccVoucher.getBLAccMainVoucher().getSize();j++)
        {
          accMainVoucherSchema = blAccVoucher.getBLAccMainVoucher().getArr(j);
          dbAccMainVoucher = new DBAccMainVoucher();

          dbAccMainVoucher.getInfo(dbpool,accMainVoucherSchema.getCenterCode(),
                                   accMainVoucherSchema.getAccBookType(),
                                   accMainVoucherSchema.getAccBookCode(),
                                   accMainVoucherSchema.getYearMonth(),
                                   accMainVoucherSchema.getVoucherNo());
          if(dbAccMainVoucher.getVoucherFlag().equals("3"))
          {
            throw new UserException( -96, -1167, "BLPrpJpayRefMain.payRefCancel", "凭证已经复核了！");
          }
          else if(dbAccMainVoucher.getVoucherFlag().equals("4"))
          {
            throw new UserException( -96, -1167, "BLPrpJpayRefMain.payRefCancel", "凭证已经记帐了！");
          }
          else if(dbAccMainVoucher.getVoucherFlag().equals("2"))
          {
            throw new UserException( -96, -1167, "BLPrpJpayRefMain.payRefCancel", "凭证需订正！");
          }
        }

        for(int i=0;i<this.getSize();i++)
        {
          prpJpayRefMainSchema = this.getArr(i);

          
          
          strWherePart = "DELETE FROM PrpJpayRefDetail WHERE PayRefNo='"+prpJpayRefMainSchema.getPayRefNo()+"'";
          dbpool.delete(strWherePart);

          
          dbPrpJpayRefMain.getInfo(prpJpayRefMainSchema.getPayRefNo(),prpJpayRefMainSchema.getSerialNo());
          dbPrpJpayRefMain.setPayRefCode("");
          dbPrpJpayRefMain.setPayRefUnit("");
          dbPrpJpayRefMain.setPayRefDate("");
          dbPrpJpayRefMain.setAccBookType("");
          dbPrpJpayRefMain.setAccBookCode("");
          dbPrpJpayRefMain.setCenterCode("");
          dbPrpJpayRefMain.setBranchCode("");
          dbPrpJpayRefMain.setYearMonth("");
          dbPrpJpayRefMain.setVoucherNo("");
          dbPrpJpayRefMain.update(dbpool);
        }
        
        blAccVoucher.cancel(dbpool);

        for(int k=0;k<this.blPrpJpayRefRec.getSize();k++)
        {
          prpJpayRefRecSchema = this.blPrpJpayRefRec.getArr(k);

          
          dbPrpJplanFee = new DBPrpJplanFee();
          dbPrpJplanFee.getInfo(dbpool, prpJpayRefRecSchema.getCertiType(),
                                prpJpayRefRecSchema.getCertiNo(),
                                prpJpayRefRecSchema.getSerialNo(),
                                prpJpayRefRecSchema.getPayRefReason());
          
          if(!dbPrpJplanFee.getFlag().equals("")){
            if(dbPrpJplanFee.getFlag().substring(0,1).equals("1") && dbPrpJplanFee.getVoucherNo().equals("")){
              dbPrpJplanFee.setFlag("");
              dbPrpJplanFee.setPlanFeeCNY("0");
            }
          }
          dbPrpJplanFee.setRealPayRefFee("0");
          dbPrpJplanFee.update(dbpool);

          
          if(prpJpayRefRecSchema.getCertiType().equals("P"))
          {
            if (dbPrpCplan.getInfo(dbpool, prpJpayRefRecSchema.getCertiNo(),
                                   prpJpayRefRecSchema.getSerialNo()) == 0)
            {
              dbPrpCplan.setDelinquentFee("" +
                                          (Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee()))
                                          +Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
              dbPrpCplan.update(dbpool);
            }
          
          }else if(prpJpayRefRecSchema.getCertiType().equals("E")){
            BLPrpCplan blPrpCplan = new BLPrpCplan();
            String strWhere = " EndorseNo='" + prpJpayRefRecSchema.getCertiNo()+"' ";
            blPrpCplan.query(dbpool,strWhere,0);
            int intReturn = 0;
            if(blPrpCplan.getSize()>0){
              intReturn = dbPrpCplan.getInfo(dbpool,blPrpCplan.getArr(0).getPolicyNo(),
                                             blPrpCplan.getArr(0).getSerialNo());
            }
            if (intReturn == 0)
            {
              dbPrpCplan.setDelinquentFee("" +
                                          (Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee()))
                                          +Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
              dbPrpCplan.update(dbpool);
            }
          }
          
          
          if(prpJpayRefRecSchema.getCertiType().equals("P"))
          {
            if (dbPrpCPplan.getInfo(dbpool, prpJpayRefRecSchema.getCertiNo(),
                                    prpJpayRefRecSchema.getSerialNo()) == 0)
            {
              dbPrpCPplan.setDelinquentFee("" +
                                          (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee()))
                                          +Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
              dbPrpCPplan.update(dbpool);
            }
          }else if(prpJpayRefRecSchema.getCertiType().equals("E")){
            BLPrpCPplan blPrpCPplan = new BLPrpCPplan();
            String strWhere = " EndorseNo='" + prpJpayRefRecSchema.getCertiNo()+"' ";
            blPrpCPplan.query(dbpool,strWhere,0);
            int intReturn = 0;
            if(blPrpCPplan.getSize()>0){
              
              intReturn = dbPrpCPplan.getInfo(dbpool,blPrpCPplan.getArr(0).getPolicyNo(),
                                             blPrpCPplan.getArr(0).getSerialNo());
            }
            if (intReturn == 0)
            {
              dbPrpCPplan.setDelinquentFee("" +
                                          (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee()))
                                          +Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
              dbPrpCPplan.update(dbpool);
            }
          }
          

          
          
          if((prpJpayRefRecSchema.getCertiType().equals("P")||prpJpayRefRecSchema.getCertiType().equals("E"))
                && prpJpayRefRecSchema.getCoinsFlag().equals("1")
                && prpJpayRefRecSchema.getCoinsType().equals("1")){
              if(prpJpayRefRecSchema.getPayRefReason().substring(1,3).equals("81")){
                dbPrpJpayRefRec = new DBPrpJpayRefRec();
                dbPrpJpayRefRec.delete(dbpool,prpJpayRefRecSchema.getCertiType(),
                                        prpJpayRefRecSchema.getCertiNo(),
                                        prpJpayRefRecSchema.getSerialNo(),
                                        prpJpayRefRecSchema.getPayRefReason(),
                                        prpJpayRefRecSchema.getPayRefTimes());                                            
              }else{
                  DBPrpJplanFee dbPrpJplanFeeCoins1 = new DBPrpJplanFee();
                  dbPrpJplanFeeCoins1.getInfo(dbpool,prpJpayRefRecSchema.getCertiType(),
                                               prpJpayRefRecSchema.getCertiNo(),
                                               prpJpayRefRecSchema.getSerialNo(),
                                               prpJpayRefRecSchema.getPayRefReason());
                  prpJpayRefRecSchema.setPlanFee(dbPrpJplanFeeCoins1.getPlanFee());
                  prpJpayRefRecSchema.setPayRefFee(dbPrpJplanFeeCoins1.getPlanFee());
                  prpJpayRefRecSchema.setPayRefDate("");
                  
                  prpJpayRefRecSchema.setIdentifyType("0");
                  
                  prpJpayRefRecSchema.setFlag("");
                  dbPrpJpayRefRec = new DBPrpJpayRefRec();
                  dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
                  dbPrpJpayRefRec.update(dbpool);                                              
              }
          }else{
              prpJpayRefRecSchema.setPayRefDate("");
              
              prpJpayRefRecSchema.setIdentifyType("0");
              
              prpJpayRefRecSchema.setFlag("");
              dbPrpJpayRefRec = new DBPrpJpayRefRec();
              dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
              dbPrpJpayRefRec.update(dbpool);                    
          }
        }
        dbpool.commitTransaction();
      }
      catch(UserException userexception){
        dbpool.rollbackTransaction();
        throw userexception;
      }
      catch(SQLException sqlexception){
        dbpool.rollbackTransaction();
        throw sqlexception;
      }
      catch(Exception exception){
        dbpool.rollbackTransaction();
        throw exception;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     * 取消收付款确认-生成对冲凭证 SangMingqian 20051118  该方法没有被调用
     * @param iWherepart
     * @param iUserCode
     * @param iComCode
     * @return
     * @throws SQLException
     * @throws Exception
     */
    public Vector payRefCancelNew(String iWherepart,String iUserCode,String iComCode) throws SQLException,Exception
    {
      BLAccVoucher blAccVoucher = new BLAccVoucher();
      BLAccVoucher blAccVoucherNew = null;
      AccMainVoucherSchema accMainVoucherSchema = null;
      BLPrpJpayRefMain blPrpJpayRefMain = null;
      BLPrpJpayRefMain blPrpJpayRefMainOld = null;
      BLPrpJpayRefMain blPrpJpayRefMainNew = null;
      PrpJpayRefMainSchema prpJpayRefMainSchema = null;
      Bill bill = new Bill();
      String billNo = "";
      ChgDate iDate = new ChgDate();
      String strOperateDate = iDate.getCurrentTime("yyyy-MM-dd");
      int intYear = Integer.parseInt(strOperateDate.substring(0,4));
      BLPrpJpayRefRec blPrpJpayRefRecNo = null;
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
      BLPrpJpayRefRec blPrpJpayRefRecOld = null;
      BLPrpJpayRefRec blPrpJpayRefRecNew = null;
      PrpJpayRefRecSchema prpJpayRefRecSchema = null;
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      DBPrpCplan dbPrpCplan = new DBPrpCplan();
      DBPrpCPplan dbPrpCPplan = new DBPrpCPplan();
      BLPrpJpayRefDetail blPrpJpayRefDetail = new BLPrpJpayRefDetail();
      BLPrpJpayRefDetail blPrpJpayRefDetailOld = null;
      BLPrpJpayRefDetail blPrpJpayRefDetailNew = null;
      PrpJpayRefDetailSchema prpJpayRefDetailSchema = null;
      Vector arrVoucherNoNew = new Vector();
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE); 
        dbpool.beginTransaction();
        
        this.query(dbpool,iWherepart);
        if(this.getSize()==0){
            throw new UserException( -96, -1167, "BLPrpJpayRefMain.payRefCancel", "没有要取消收付款确认的记录！");
        }
        
        blAccVoucher.getBLAccMainVoucher().query(dbpool,iWherepart,0);
        for(int i=0;i<blAccVoucher.getBLAccMainVoucher().getSize();i++){
            
            String strCenterCode = blAccVoucher.getBLAccMainVoucher().getArr(i).getCenterCode();
            String strAccBookType = blAccVoucher.getBLAccMainVoucher().getArr(i).getAccBookType();
            String strAccBookCode = blAccVoucher.getBLAccMainVoucher().getArr(i).getAccBookCode();
            String strYearMonthOld = blAccVoucher.getBLAccMainVoucher().getArr(i).getYearMonth();
            String strVoucherNoOld = blAccVoucher.getBLAccMainVoucher().getArr(i).getVoucherNo();
            accMainVoucherSchema = new AccMainVoucherSchema();
            blAccVoucherNew = new BLAccVoucher();
            
            
            String strWherePartCancel = " CenterCode='" + strCenterCode
                                        + "' AND AccBookType='" + strAccBookType
                                        + "' AND AccBookCode='" + strAccBookCode
                                        + "' AND YearMonth='" + strYearMonthOld
                                        + "' AND VoucherNo='" + strVoucherNoOld
                                        + "' ";
            blAccVoucherNew.queryAll(dbpool,strWherePartCancel);
            
            BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
            String strWherePartMonth = "";
            String strYearMonth = "";
            strWherePartMonth = " CenterCode='" + strCenterCode
                                + "' AND AccBookType='" + strAccBookType
                                + "' AND AccBookCode='" + strAccBookCode
                                + "' AND AccMonthStat='3'";
            blAccMonthTrace.query(strWherePartMonth);
            if(blAccMonthTrace.getSize()==0){
                throw new UserException( -98, -1167, "BLAccVoucher.VoucherCancel()",
                        "核算单位" + strCenterCode + "还没有初始化");
            }else{
                strYearMonth = blAccMonthTrace.getArr(0).getYearMonth();
            }
            
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setCenterCode(strCenterCode);
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setBranchCode(strCenterCode);
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setAccBookType(strAccBookType);
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setAccBookCode(strAccBookCode);
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setYearMonth(strYearMonth);
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setVoucherNo("");
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setVoucherDate(strOperateDate);
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setOperatorCode(iUserCode);
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setOperatorBranch(iComCode);
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setApproverCode("");
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setApproverBranch("");
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setApproveDate("");
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setGeneCode("");
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setGeneDate("");
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setVoucherFlag("1");
            String strVoucherNoNew = "";
            int intVoucherNoNew = 0;
            
            intVoucherNoNew = blAccVoucherNew.createVoucherNum2(dbpool, strCenterCode,
            		                                            strCenterCode, strAccBookType,
																strAccBookCode, strYearMonth, "1");
            strVoucherNoNew = blAccVoucherNew.formatVoucherNo(strYearMonth, intVoucherNoNew);
            blAccVoucherNew.getBLAccMainVoucher().getArr(0).setVoucherNo(strVoucherNoNew);
            arrVoucherNoNew.add(strVoucherNoNew);
            
            AccSubVoucherSchema accSubVoucherSchema = null;
            double dbDebitSource = 0;
            double dbCreditSource = 0;
            double dbDebitDest = 0;
            double dbCreditDest = 0;
            double dbExchangeRate = 0;
            String strItemCode = "";
            for(int m=0;m<blAccVoucherNew.getBLAccSubVoucher().getSize();m++){
                accSubVoucherSchema = blAccVoucherNew.getBLAccSubVoucher().getArr(m);
                dbDebitSource = Double.parseDouble(accSubVoucherSchema.getDebitSource());
                dbCreditSource = Double.parseDouble(accSubVoucherSchema.getCreditSource());
                dbDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitDest());
                dbCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditDest());
                dbExchangeRate = Double.parseDouble(accSubVoucherSchema.getExchangeRate());
                blAccVoucherNew.getBLAccSubVoucher().getArr(m).setCenterCode(strCenterCode);
                blAccVoucherNew.getBLAccSubVoucher().getArr(m).setAccBookType(strAccBookType);
                blAccVoucherNew.getBLAccSubVoucher().getArr(m).setAccBookCode(strAccBookCode);
                blAccVoucherNew.getBLAccSubVoucher().getArr(m).setYearMonth(strYearMonth);
                blAccVoucherNew.getBLAccSubVoucher().getArr(m).setVoucherNo(strVoucherNoNew);
                blAccVoucherNew.getBLAccSubVoucher().getArr(m).setRemark("对冲" + strVoucherNoOld + "号凭证");
                blAccVoucherNew.getBLAccSubVoucher().getArr(m).setDebitSource(""+(-1)*dbDebitSource);
                blAccVoucherNew.getBLAccSubVoucher().getArr(m).setCreditSource(""+(-1)*dbCreditSource);
                blAccVoucherNew.getBLAccSubVoucher().getArr(m).setDebitDest(""+(-1)*dbDebitDest);
                blAccVoucherNew.getBLAccSubVoucher().getArr(m).setCreditDest(""+(-1)*dbCreditDest);
                strItemCode = accSubVoucherSchema.getItemCode();
            }
            
            accMainVoucherSchema = blAccVoucherNew.getBLAccMainVoucher().getArr(0);
            strCenterCode = accMainVoucherSchema.getCenterCode();
            String strBranchCode = "";
            strBranchCode = accMainVoucherSchema.getBranchCode();
            strAccBookType = accMainVoucherSchema.getAccBookType();
            strAccBookCode = accMainVoucherSchema.getAccBookCode();
            String strYearMonthNew = "";
            strYearMonthNew = accMainVoucherSchema.getYearMonth();
            
            String strWherePart = " CenterCode='" + strCenterCode
                                  + "' AND YearMonth='" + strYearMonthOld
                                  + "' AND VoucherNo='" + strVoucherNoOld
								  + "' ORDER BY PayRefNo ";
            blPrpJpayRefMain = new BLPrpJpayRefMain();
            blPrpJpayRefMain.query(dbpool,strWherePart,0);
            
            String strPayRefNoPre = "";
            for(int j=0;j<blPrpJpayRefMain.getSize();j++){
                prpJpayRefMainSchema = new PrpJpayRefMainSchema();
                prpJpayRefMainSchema = blPrpJpayRefMain.getArr(j);
                String strPayRefNo = "";
                String strSerialNo = "";
                double dbPayRefFee = 0;
                strPayRefNo = prpJpayRefMainSchema.getPayRefNo();
                strSerialNo = prpJpayRefMainSchema.getSerialNo();
                dbPayRefFee = Double.parseDouble(prpJpayRefMainSchema.getPayRefFee());
                
                
                billNo = bill.getNo("prpjpayrec","0501",prpJpayRefMainSchema.getPackageUnit(),intYear,"00");
                String strPayRefNoOld = "";
                blPrpJpayRefRecNo = new BLPrpJpayRefRec();
                strPayRefNoOld = blPrpJpayRefRecNo.createPayRefNo(billNo,"0501",prpJpayRefMainSchema.getPackageUnit());
                prpJpayRefMainSchema.setPayRefNo(strPayRefNoOld);
                blPrpJpayRefMainOld = new BLPrpJpayRefMain();
                blPrpJpayRefMainOld.setArr(prpJpayRefMainSchema);
                blPrpJpayRefMainOld.save(dbpool);
                
                billNo = bill.getNo("prpjpayrec","0501",prpJpayRefMainSchema.getPackageUnit(),intYear,"00");
                String strPayRefNoNew = "";
                blPrpJpayRefRecNo = new BLPrpJpayRefRec();
                strPayRefNoNew = blPrpJpayRefRecNo.createPayRefNo(billNo,"0501",prpJpayRefMainSchema.getPackageUnit());
                prpJpayRefMainSchema = blPrpJpayRefMain.getArr(j);
                prpJpayRefMainSchema.setPayRefNo(strPayRefNoNew);
                prpJpayRefMainSchema.setPayRefFee(""+(-1)*dbPayRefFee);
                prpJpayRefMainSchema.setCenterCode(strCenterCode);
                prpJpayRefMainSchema.setBranchCode(strBranchCode);
                prpJpayRefMainSchema.setAccBookType(strAccBookType);
                prpJpayRefMainSchema.setAccBookCode(strAccBookCode);
                prpJpayRefMainSchema.setYearMonth(strYearMonthNew);
                prpJpayRefMainSchema.setVoucherNo(strVoucherNoNew);
                prpJpayRefMainSchema.setPayRefDate(strOperateDate);
                prpJpayRefMainSchema.setPayRefCode(iUserCode);
                prpJpayRefMainSchema.setPayRefUnit(iComCode);
                blPrpJpayRefMainNew = new BLPrpJpayRefMain();
                blPrpJpayRefMainNew.setArr(prpJpayRefMainSchema);
                blPrpJpayRefMainNew.save(dbpool);
                
                prpJpayRefMainSchema.setPayRefNo(strPayRefNo);
                prpJpayRefMainSchema.setSerialNo(strSerialNo);
                prpJpayRefMainSchema.setPayRefFee(""+dbPayRefFee);
                prpJpayRefMainSchema.setPayRefCode("");
                prpJpayRefMainSchema.setPayRefUnit("");
                prpJpayRefMainSchema.setPayRefDate("");
                prpJpayRefMainSchema.setAccBookType("");
                prpJpayRefMainSchema.setAccBookCode("");
                prpJpayRefMainSchema.setCenterCode("");
                prpJpayRefMainSchema.setBranchCode("");
                prpJpayRefMainSchema.setYearMonth("");
                prpJpayRefMainSchema.setVoucherNo("");
                dbPrpJpayRefMain.setSchema(prpJpayRefMainSchema);
                dbPrpJpayRefMain.update(dbpool);
                if(strPayRefNo.equals(strPayRefNoPre)){
                	continue;
                }
                
                String strWherePartNo = " PayRefNo='" + strPayRefNo + "' ";
                
                blPrpJpayRefRec.query(strWherePartNo,0);
                for(int k=0;k<blPrpJpayRefRec.getSize();k++){
                    prpJpayRefRecSchema = new PrpJpayRefRecSchema();
                    prpJpayRefRecSchema = blPrpJpayRefRec.getArr(k);
                    dbPayRefFee = Double.parseDouble(prpJpayRefRecSchema.getPayRefFee());
                    
                    String strPayRefTimes = "";
                    strPayRefTimes = this.getPayRefTimes(strPayRefNo,"10");
                    prpJpayRefRecSchema.setPayRefTimes(strPayRefTimes);
                    prpJpayRefRecSchema.setPayRefNo(strPayRefNoOld);
                    
                    prpJpayRefRecSchema.setIdentifyType("1");
                    prpJpayRefRecSchema.setIdentifyNumber("1");
                    
                    blPrpJpayRefRecOld = new BLPrpJpayRefRec();
                    blPrpJpayRefRecOld.setArr(prpJpayRefRecSchema);
                    blPrpJpayRefRecOld.save(dbpool);
                    
                    prpJpayRefRecSchema = blPrpJpayRefRec.getArr(k);
                    strPayRefTimes = this.getPayRefTimes(strPayRefNo,"20");
                    prpJpayRefRecSchema.setPayRefTimes(strPayRefTimes);
                    prpJpayRefRecSchema.setPayRefNo(strPayRefNoNew);
                    prpJpayRefRecSchema.setPayRefFee(""+(-1)*dbPayRefFee);
                    prpJpayRefRecSchema.setPayRefDate(strOperateDate);
                    
                    prpJpayRefRecSchema.setIdentifyType("1");
                    prpJpayRefRecSchema.setIdentifyNumber("1");
                    
                    blPrpJpayRefRecNew = new BLPrpJpayRefRec();
                    blPrpJpayRefRecNew.setArr(prpJpayRefRecSchema);
                    blPrpJpayRefRecNew.save(dbpool);
                    
                    prpJpayRefRecSchema.setPayRefNo(strPayRefNo);
                    prpJpayRefRecSchema.setPayRefTimes("1");
                    prpJpayRefRecSchema.setPayRefFee(""+dbPayRefFee);
                    prpJpayRefRecSchema.setPayRefDate("");
                    
                    prpJpayRefRecSchema.setIdentifyType("0");
                    prpJpayRefRecSchema.setIdentifyNumber("1");
                    
                    dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
                    dbPrpJpayRefRec.update(dbpool);
                    
                    dbPrpJplanFee.getInfo(dbpool,
                                          prpJpayRefRecSchema.getCertiType(),
                                          prpJpayRefRecSchema.getCertiNo(),
                                          prpJpayRefRecSchema.getSerialNo(),
                                          prpJpayRefRecSchema.getPayRefReason());
                    dbPrpJplanFee.setRealPayRefFee("0");
                    dbPrpJplanFee.update(dbpool);
                    
                    if(!dbPrpJplanFee.getFlag().equals("")){
                        if(dbPrpJplanFee.getFlag().substring(0,1).equals("1")
                            &&!dbPrpJplanFee.getVoucherNo().equals("")){
                            
                            accSubVoucherSchema = new AccSubVoucherSchema();
                            accSubVoucherSchema.setAccBookType(strAccBookType);
                            accSubVoucherSchema.setAccBookCode(strAccBookCode);
                            accSubVoucherSchema.setCenterCode(strCenterCode);
                            accSubVoucherSchema.setF26(strBranchCode);
                            accSubVoucherSchema.setYearMonth(strYearMonth);
                            accSubVoucherSchema.setCurrency(dbPrpJplanFee.getCurrency1());
                            accSubVoucherSchema.setVoucherNo(strVoucherNoNew);
                            accSubVoucherSchema.setF01(dbPrpJplanFee.getClassCode());
                            accSubVoucherSchema.setF05(dbPrpJplanFee.getRiskCode());
                            accSubVoucherSchema.setF28(dbPrpJplanFee.getComCode());
                            accSubVoucherSchema.setF27(dbPrpJplanFee.getHandler1Code());
                            accSubVoucherSchema.setRemark("对冲" + strVoucherNoOld + "号凭证");
                            accSubVoucherSchema.setItemCode("1122");
                            accSubVoucherSchema.setDirectionIdx("00");
                            accSubVoucherSchema.setExchangeRate(""+dbExchangeRate);
                            accSubVoucherSchema.setDebitSource(""+Double.parseDouble(dbPrpJplanFee.getPlanFee()));
                            accSubVoucherSchema.setDebitDest(""+Double.parseDouble(dbPrpJplanFee.getPlanFee())*dbExchangeRate);
                            accSubVoucherSchema.setCreditSource(""+0);
                            accSubVoucherSchema.setCreditDest(""+0);
                            blAccVoucherNew.getBLAccSubVoucher().setArr(accSubVoucherSchema);
                            
                            accSubVoucherSchema = new AccSubVoucherSchema();
                            accSubVoucherSchema.setAccBookType(strAccBookType);
                            accSubVoucherSchema.setAccBookCode(strAccBookCode);
                            accSubVoucherSchema.setCenterCode(strCenterCode);
                            accSubVoucherSchema.setF26(strBranchCode);
                            accSubVoucherSchema.setYearMonth(strYearMonth);
                            accSubVoucherSchema.setCurrency(dbPrpJplanFee.getCurrency1());
                            accSubVoucherSchema.setVoucherNo(strVoucherNoNew);
                            accSubVoucherSchema.setF01(dbPrpJplanFee.getClassCode());
                            accSubVoucherSchema.setF05(dbPrpJplanFee.getRiskCode());
                            accSubVoucherSchema.setF28(dbPrpJplanFee.getComCode());
                            accSubVoucherSchema.setF27(dbPrpJplanFee.getHandler1Code());
                            accSubVoucherSchema.setRemark("对冲" + strVoucherNoOld + "号凭证");
                            
                            accSubVoucherSchema.setItemCode("2203");
                            accSubVoucherSchema.setDirectionIdx("02/");
                            accSubVoucherSchema.setExchangeRate(""+dbExchangeRate);
                            accSubVoucherSchema.setDebitSource(""+0);
                            accSubVoucherSchema.setDebitDest(""+0);
                            accSubVoucherSchema.setCreditSource(""+Double.parseDouble(dbPrpJplanFee.getPlanFee()));
                            accSubVoucherSchema.setCreditDest(""+Double.parseDouble(dbPrpJplanFee.getPlanFee())*dbExchangeRate);
                            blAccVoucherNew.getBLAccSubVoucher().setArr(accSubVoucherSchema);
                            strItemCode = accSubVoucherSchema.getItemCode();
                        }
                    }
                    
                    
                    if(prpJpayRefRecSchema.getCertiType().equals("P")){
                        if (dbPrpCplan.getInfo(dbpool,
                    			prpJpayRefRecSchema.getCertiNo(),
								prpJpayRefRecSchema.getSerialNo()) == 0){
                    		dbPrpCplan.setDelinquentFee("" +
                    				(Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee()))
                    			     +Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
                            dbPrpCplan.update(dbpool);
                          }
                    }else if(prpJpayRefRecSchema.getCertiType().equals("E")){
                    	BLPrpCplan blPrpCplan = new BLPrpCplan();
                    	String strWhere = " EndorseNo='" + prpJpayRefRecSchema.getCertiNo()+"' ";
                    	blPrpCplan.query(dbpool,strWhere,0);
                    	int intReturn = 0;
                    	if(blPrpCplan.getSize()>0){
                            intReturn = dbPrpCplan.getInfo(dbpool,
                                                           blPrpCplan.getArr(0).getPolicyNo(),
                                                           blPrpCplan.getArr(0).getSerialNo());
                        }
                        if (intReturn == 0){
                            dbPrpCplan.setDelinquentFee("" +
                                                        (Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee()))
                                                        +Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
                            dbPrpCplan.update(dbpool);
                        }
                    }
                    
                    if(prpJpayRefRecSchema.getCertiType().equals("P")){
                        if (dbPrpCPplan.getInfo(dbpool,
                                                  prpJpayRefRecSchema.getCertiNo(),
                                                  prpJpayRefRecSchema.getSerialNo()) == 0){
                            dbPrpCPplan.setDelinquentFee("" +
                                                        (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee()))
                                                        +Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
                            dbPrpCPplan.update(dbpool);
                        }
                    }else if(prpJpayRefRecSchema.getCertiType().equals("E")){
                        BLPrpCPplan blPrpCPplan = new BLPrpCPplan();
                        String strWhere = " EndorseNo='" + prpJpayRefRecSchema.getCertiNo()+"' ";
                        blPrpCPplan.query(dbpool,strWhere,0);
                        int intReturn = 0;
                        if(blPrpCPplan.getSize()>0){
                            
                            intReturn = dbPrpCPplan.getInfo(dbpool,
                                                           blPrpCPplan.getArr(0).getPolicyNo(),
                                                           blPrpCPplan.getArr(0).getSerialNo());
                        }
                        if (intReturn == 0){
                            dbPrpCPplan.setDelinquentFee("" +
                                                        (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee()))
                                                        +Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
                            dbPrpCPplan.update(dbpool);
                        }
                    }
                }
                
                blPrpJpayRefDetail.query(dbpool,strWherePartNo,0);
                for(int k=0;k<blPrpJpayRefDetail.getSize();k++){
                    prpJpayRefDetailSchema = new PrpJpayRefDetailSchema();
                    prpJpayRefDetailSchema = blPrpJpayRefDetail.getArr(k);
                    dbPayRefFee = Double.parseDouble(prpJpayRefDetailSchema.getPayRefFee());
                    
                    prpJpayRefDetailSchema.setPayRefNo(strPayRefNoOld);
                    blPrpJpayRefDetailOld = new BLPrpJpayRefDetail();
                    blPrpJpayRefDetailOld.setArr(prpJpayRefDetailSchema);
                    blPrpJpayRefDetailOld.save(dbpool);
                    
                    prpJpayRefDetailSchema = blPrpJpayRefDetail.getArr(k);
                    prpJpayRefDetailSchema.setPayRefNo(strPayRefNoNew);
                    prpJpayRefDetailSchema.setPayRefFee(""+(-1)*dbPayRefFee);
                    blPrpJpayRefDetailNew = new BLPrpJpayRefDetail();
                    blPrpJpayRefDetailNew.setArr(prpJpayRefDetailSchema);
                    blPrpJpayRefDetailNew.save(dbpool);
                    
                    String strWherePartDel = "";
                    strWherePartDel = "DELETE FROM PrpJpayRefDetail WHERE PayRefNo='" + strPayRefNo + "'";
                    dbpool.delete(strWherePartDel);
                }
                strPayRefNoPre = strPayRefNo;
            }
            
            for(int n=0; n<blAccVoucherNew.getBLAccSubVoucher().getSize(); n++)
            {
            	blAccVoucherNew.getBLAccSubVoucher().getArr(n).setSuffixNo("" + (n + 1));
            	blAccVoucherNew.getBLAccSubVoucher().createRawArticle(blAccVoucherNew.getBLAccSubVoucher().getArr(n),strItemCode);
            }
            blAccVoucherNew.setBLAccVoucherArticle(blAccVoucherNew.getBLAccSubVoucher().genVoucherArticle());
            
            blAccVoucherNew.getBLAccSubVoucher().voucherComp("101");
            for(int n=0; n<blAccVoucherNew.getBLAccSubVoucher().getSize(); n++)
            {
            	blAccVoucherNew.getBLAccSubVoucher().getArr(n).setSuffixNo("" + (n + 1));
            	blAccVoucherNew.getBLAccSubVoucher().createRawArticle(blAccVoucherNew.getBLAccSubVoucher().getArr(n),"");
            }
            blAccVoucherNew.setBLAccVoucherArticle(blAccVoucherNew.getBLAccSubVoucher().genVoucherArticle());
            
            for(int n=0; n<blAccVoucherNew.getBLAccSubVoucher().getSize(); n++){
                if(Double.parseDouble(blAccVoucherNew.getBLAccSubVoucher().getArr(n).getCreditDest())==0
            	    && Double.parseDouble(blAccVoucherNew.getBLAccSubVoucher().getArr(n).getDebitDest())==0){
            	    blAccVoucherNew.getBLAccSubVoucher().remove(n);
            	    blAccVoucherNew.getBLAccSubVoucher().removeItemArticle(n);
            	}
            }
            for(int n=0; n<blAccVoucherNew.getBLAccSubVoucher().getSize(); n++)
            {
            	blAccVoucherNew.getBLAccSubVoucher().getArr(n).setSuffixNo("" + (n + 1));
            	blAccVoucherNew.getBLAccSubVoucher().createRawArticle(blAccVoucherNew.getBLAccSubVoucher().getArr(n),"");
            }
            blAccVoucherNew.setBLAccVoucherArticle(blAccVoucherNew.getBLAccSubVoucher().genVoucherArticle());
            
            blAccVoucherNew.getBLAccSubVoucher().voucherOrder();
            
            blAccVoucherNew.save(dbpool);
            
        }
        dbpool.commitTransaction();
        bill.deleteNo("prpjpayrec",billNo);
      }
      catch(SQLException sqlexception){
        dbpool.rollbackTransaction();
        bill.putNo("prpjpayrec",billNo);
        throw sqlexception;
      }
      catch(Exception exception){
        dbpool.rollbackTransaction();
        bill.putNo("prpjpayrec",billNo);
        throw exception;
      }
      finally {
        dbpool.close();
      }
      return arrVoucherNoNew;
    }


    /**
     *未打包发票的收付款确认 SangMingqian 20050627
     *@param arrVisaCode[] 选中发票类型
     *@param arrVisaSerialNo[] 选中发票号
     *@param strPayRefNo 打包号
     *@param strPayRefDate 收付日期
     *@param strPayRefUnit 收付单位
     *@param strPayRefCode 收付人
     *@param iAuxNumber 附件张数
     *@throws Exception
    */

   public BLAccMainVoucher invoicePayRefVerify(String[] iArrVisaCode,
                                               String[] iArrVisaSerialNo,
                                               String iPayRefDate,
                                               String iPayRefUnit,
                                               String iPayRefCode,
                                               String iAuxNumber) throws
       UserException, SQLException, Exception
    {
      
      BLAccMainVoucher blAccMainVoucher = new BLAccMainVoucher();
      String strPayRefNo = "";
      String strPayRefNoCoins = "";
      
      this.blPrpJpayRefRec.queryInvioceCoins(iArrVisaCode,iArrVisaSerialNo);
      this.blPackageFlag = true;
      
      
      strPayRefNo = this.blPrpJpayRefRec.packageInvioce(iArrVisaCode,iArrVisaSerialNo,
          iPayRefCode, iPayRefUnit, iPayRefDate);

      strPayRefNoCoins = this.blPrpJpayRefRec.packageInvioceCoins(iPayRefCode, iPayRefUnit,
          iPayRefDate,this.blPrpJpayRefRecCoins,this.coinsSchemas);

      
      logger.info("生成打包号：" + strPayRefNo + "    " + strPayRefNoCoins);
      

      
      for(int i=0; i<this.blPrpJpayRefDetail.getSize(); i++)
      {
        this.blPrpJpayRefDetail.getArr(i).setPayRefNo(strPayRefNo);
        this.blPrpJpayRefDetail.getArr(i).setSerialNo(""+(i+1));
      }
      
      for(int i=0; i<this.blPrpJpayRefRec.getSize(); i++)
      {
        this.blPrpJpayRefRec.getArr(i).setPayRefNo(strPayRefNo);
        
        this.blPrpJpayRefRec.getArr(i).setIdentifyNumber("1");
        
      }

      for(int i=0; i<this.blPrpJpayRefRecCoins.getSize(); i++)
      {
        blPrpJpayRefRecCoins.getArr(i).setPayRefNo(strPayRefNoCoins);
        
        blPrpJpayRefRecCoins.getArr(i).setIdentifyNumber("1");
        
      }

      this.query(" PayRefNo='"+strPayRefNo+"'",0);

      
      for(int i=0; i<this.getSize(); i++)
      {
        this.getArr(i).setPayRefCode(iPayRefCode);
        this.getArr(i).setPayRefUnit(iPayRefUnit);
        this.getArr(i).setPayRefDate(iPayRefDate);
      }

      
      blAccMainVoucher = this.payRefVerify(iAuxNumber);
      return blAccMainVoucher;
    }
    /*public BLAccMainVoucher invoicePayRefVerify(String[] arrVisaSerialNo,String strPayRefNo,String strPayRefDate,String strPayRefUnit,String strPayRefCode) throws UserException,SQLException,Exception
    {
      PrpJpayRefRecSchema prpJpayRefRecSchema = null;
      PrpJpayRefMainSchema prpJpayRefMainSchema = new PrpJpayRefMainSchema();
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      DBPrpJplanFee dbPrpJplanFee = null;

      String strWherePart = " VisaSerialNo IN ('" + arrVisaSerialNo[0] + "'";
      for(int i=1; i<arrVisaSerialNo.length; i++)
      {
        strWherePart += ",'" + arrVisaSerialNo[i ]+ "'";
      }
      strWherePart += ")";
      this.blPrpJpayRefRec.query(strWherePart,0);

      
      String strPayRefNoType="1";
      BLAccVoucher blAccVoucher = this.genAccVoucher(strPayRefDate,strPayRefCode,strPayRefNoType);
      String strAccBookType = blAccVoucher.getBLAccMainVoucher().getArr(0).getAccBookType();
      String strAccBookCode = blAccVoucher.getBLAccMainVoucher().getArr(0).getAccBookCode();
      String strCenterCode = blAccVoucher.getBLAccMainVoucher().getArr(0).getCenterCode();
      String strBranchCode = blAccVoucher.getBLAccMainVoucher().getArr(0).getBranchCode();
      String strYearMonth = blAccVoucher.getBLAccMainVoucher().getArr(0).getYearMonth();
      String strVoucherNo = "";

      DbPool dbpool = new DbPool();
      
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        
        blAccVoucher.save(dbpool);
        strVoucherNo = blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();

        double dblPayRefFee = 0;
        int j=1;
        for (int i = 0; i < this.blPrpJpayRefRec.getSize(); i++)
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema = this.blPrpJpayRefRec.getArr(i);

          
          if(prpJpayRefRecSchema.getCertiType().equals("E"))
          {
            String Flag="0";
            PrpJpayRefRecSchema prpJpayRefRecSchema1 = null;
            
            double PPlanFee =0;
            for(int k=0;k<this.blPrpJpayRefRec.getSize();k++)
            {
              prpJpayRefRecSchema1 = blPrpJpayRefRec.getArr(k);
              if(prpJpayRefRecSchema1.getCertiType().equals("P")&&
                 prpJpayRefRecSchema1.getCertiNo().equals(prpJpayRefRecSchema.getPolicyNo()))
              {
                PPlanFee += Double.parseDouble(prpJpayRefRecSchema1.getPlanFee());
                Flag="1"; 
              }
            }
            if(Flag.equals("0"))
            {
              String strConditionFee = " CertiType='P' AND CertiNo='" + prpJpayRefRecSchema.getPolicyNo()+ "' AND RealPayRefFee!=0 ";
              this.blPrpJplanFee.querySum(strConditionFee);
              if(this.blPrpJplanFee.getSize()>0)
              {
                PPlanFee = Double.parseDouble(blPrpJplanFee.getArr(0).getPlanFee());
                Flag="1"; 
              }
            }
            if(Flag.equals("1") && PPlanFee < Math.abs(Double.parseDouble(prpJpayRefRecSchema.getPlanFee())))
            {
              throw new UserException(-96,-1167,"","批单退费不能超过XX收费！");
            }
            
            if(Flag.equals("0"))
            {
              throw new UserException(-96,-1167,"","对应的XX未做收付款确认！");
            }
          }
          

          
          dbPrpJpayRefRec.getInfo(dbpool, prpJpayRefRecSchema.getCertiType(),
                                  prpJpayRefRecSchema.getCertiNo(),
                                  prpJpayRefRecSchema.getSerialNo(),
                                  prpJpayRefRecSchema.getPayRefReason(),
                                  prpJpayRefRecSchema.getPayRefTimes());
          dbPrpJpayRefRec.setPayRefNo(strPayRefNo);
          dbPrpJpayRefRec.setPayRefDate(strPayRefDate);
          dbPrpJpayRefRec.update(dbpool);

          
          dbPrpJplanFee = new DBPrpJplanFee();
          dbPrpJplanFee.getInfo(dbpool, prpJpayRefRecSchema.getCertiType(),
                                prpJpayRefRecSchema.getCertiNo(),
                                prpJpayRefRecSchema.getSerialNo(),
                                prpJpayRefRecSchema.getPayRefReason());
          dbPrpJplanFee.setRealPayRefFee("" +
             (Double.parseDouble(Str.chgStrZero(dbPrpJplanFee.getRealPayRefFee()))
             +Double.parseDouble(prpJpayRefRecSchema.getPlanFee())));

          if(Double.parseDouble(Str.chgStrZero(dbPrpJplanFee.getPlanFeeCNY()))==0)
          {
            
            if(dbPrpJpayRefRec.getCurrency2().equals("CNY"))
              dbPrpJplanFee.setExchangeRate(dbPrpJpayRefRec.getExchangeRate());
            
            else
              dbPrpJplanFee.setExchangeRate("" +
                  PubTools.getExchangeRate(dbpool,
                  dbPrpJplanFee.getCurrency1(), "CNY", strPayRefDate));
            double dblPlanFeeCNY = Double.parseDouble(dbPrpJplanFee.getPlanFee()) *
                Double.parseDouble(dbPrpJplanFee.getExchangeRate());
            dblPlanFeeCNY = Str.round(Str.round(dblPlanFeeCNY,8),2);
            dbPrpJplanFee.setPlanFeeCNY(""+dblPlanFeeCNY);
            if(dbPrpJplanFee.getCertiType().equals("P")||dbPrpJplanFee.getCertiType().equals("E"))
              dbPrpJplanFee.setFlag("1");
          }
          dbPrpJplanFee.update(dbpool);

          
          if ((prpJpayRefRecSchema.getCertiType().equals("P") ||
              prpJpayRefRecSchema.getCertiType().equals("E"))&&
              Double.parseDouble(prpJpayRefRecSchema.getPlanFee())>0)
          {
            DBPrpCplan dbPrpCplan = new DBPrpCplan();
            dbPrpCplan.getInfo(dbpool, prpJpayRefRecSchema.getCertiNo(),
                               prpJpayRefRecSchema.getSerialNo());
            
            if(dbPrpCplan.getInfo(dbpool, prpJpayRefRecSchema.getCertiNo(),prpJpayRefRecSchema.getSerialNo())==0)
            {
              dbPrpCplan.setDelinquentFee("" +
                            (Double.parseDouble(dbPrpCplan.getDelinquentFee()) -
                             Double.parseDouble(prpJpayRefRecSchema.getPlanFee())));
              dbPrpCplan.update(dbpool);
            }
          }

          
          dblPayRefFee += Double.parseDouble(dbPrpJpayRefRec.getPayRefFee());
          prpJpayRefMainSchema = new PrpJpayRefMainSchema();
          prpJpayRefMainSchema.setPayRefNo(strPayRefNo);
          prpJpayRefMainSchema.setSerialNo(Integer.toString(j));
          prpJpayRefMainSchema.setPayRefNoType("1");
          prpJpayRefMainSchema.setVisaCode(prpJpayRefRecSchema.getVisaCode());
          prpJpayRefMainSchema.setVisaSerialNo(prpJpayRefRecSchema.getVisaSerialNo());
          prpJpayRefMainSchema.setPrintDate(prpJpayRefRecSchema.getPrintDate());
          prpJpayRefMainSchema.setPrinterCode(prpJpayRefRecSchema.getPrinterCode());
          prpJpayRefMainSchema.setVisaHandler(prpJpayRefRecSchema.getVisaHandler());
          prpJpayRefMainSchema.setPayRefName(prpJpayRefRecSchema.getPayRefName());
          prpJpayRefMainSchema.setIdentifyType(prpJpayRefRecSchema.getIdentifyType());
          prpJpayRefMainSchema.setIdentifyNumber(prpJpayRefRecSchema.getIdentifyNumber());
          prpJpayRefMainSchema.setCurrency2(prpJpayRefRecSchema.getCurrency2());
          prpJpayRefMainSchema.setRemark(prpJpayRefRecSchema.getRemark());
          prpJpayRefMainSchema.setPackageCode(strPayRefCode);
          prpJpayRefMainSchema.setPackageUnit(strPayRefUnit);
          prpJpayRefMainSchema.setPackageDate(strPayRefDate);
          prpJpayRefMainSchema.setPayRefCode(strPayRefCode);
          prpJpayRefMainSchema.setPayRefUnit(strPayRefUnit);
          prpJpayRefMainSchema.setPayRefDate(strPayRefDate);
          prpJpayRefMainSchema.setCurrency2(prpJpayRefRecSchema.getCurrency2());
          prpJpayRefMainSchema.setCenterCode(strCenterCode);
          prpJpayRefMainSchema.setBranchCode(strBranchCode);
          prpJpayRefMainSchema.setAccBookType(strAccBookType);
          prpJpayRefMainSchema.setAccBookCode(strAccBookCode);
          prpJpayRefMainSchema.setYearMonth(strYearMonth);
          prpJpayRefMainSchema.setVoucherNo(strVoucherNo);
          for(int k=i+1; k<this.getSize(); k++)
          {
            if(prpJpayRefRecSchema.getVisaCode().equals(this.getArr(k).getVisaCode())&&
               prpJpayRefRecSchema.getVisaSerialNo().equals(this.getArr(k).getVisaSerialNo()))
            {
              dbPrpJpayRefRec.getInfo(dbpool, this.blPrpJpayRefRec.getArr(k).getCertiType(),
                                this.blPrpJpayRefRec.getArr(k).getCertiNo(),
                                this.blPrpJpayRefRec.getArr(k).getSerialNo(),
                                this.blPrpJpayRefRec.getArr(k).getPayRefReason(),
                                this.blPrpJpayRefRec.getArr(k).getPayRefTimes());
              dbPrpJpayRefRec.setPayRefNo(strPayRefNo);
              dbPrpJpayRefRec.update(dbpool);
              dblPayRefFee += Double.parseDouble(this.getArr(k).getPayRefFee());
              i++;
            }
            else
            {
              break;
            }
          }

          prpJpayRefMainSchema.setPayRefFee(""+dblPayRefFee);
          this.setArr(prpJpayRefMainSchema);
          dblPayRefFee = 0;
          j++;
        }
        this.save(dbpool);

        
        this.blPrpJpayRefDetail.save(dbpool);

        dbpool.commitTransaction();
      }
      catch(UserException userexception)
      {
        dbpool.rollbackTransaction();
        throw userexception;
      }
      catch(SQLException sqlexception)
      {
        dbpool.rollbackTransaction();
        throw sqlexception;
      }
      catch(Exception exception)
      {
        dbpool.rollbackTransaction();
        throw exception;
      }
      finally {
        dbpool.close();
      }
      return blAccVoucher.getBLAccMainVoucher();
    }*/

    /** add by huangen:该方法只对联共XXXXX的单子有用
     * @author Yangzhen 20051222
     * @return blAccVoucher 凭证信息
     * @throws UserException
     * @throws Exception
     */
   public BLAccVoucher genAccVoucherHeadOffice(String strPayRefDate1,String strPayRefCode1,String strPayRefNoType1,String iAuxNumber) 
   throws UserException,Exception{
     BLAccVoucher blAccVoucher = new BLAccVoucher();
     BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
     String strCenterCode = "00000000";
     String strBranchCode = "00000000";
     String strAccBookType = SysConst.getProperty("ACCBOOKTYPE");
     String strAccBookCode = "";
     if(strAccBookType.equals("02"))
       strAccBookCode = SysConst.getProperty("ACCBOOKCODE");

     
     BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
     String strSQL = "";
     String strYearMonth = "";
     strSQL = " CenterCode='"+strCenterCode
         + "' AND AccBookType='"+strAccBookType
         + "' AND AccBookCode='"+strAccBookCode
         + "' AND AccMonthStat='3'";
     blAccMonthTrace.query(strSQL);
     if(blAccMonthTrace.getSize()==0)
       throw new UserException( -98, -1167, "BLPrpJpayRefMain.genAccVoucherHeadOffice",
                               "核算单位" + strCenterCode + "还没有初始化");
     else
       
      
      strYearMonth = strPayRefDate1.substring(0,4) + strPayRefDate1.substring(5,7);

     
     AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
     accMainVoucherSchema.setAccBookType(strAccBookType);
     accMainVoucherSchema.setAccBookCode(strAccBookCode);
     accMainVoucherSchema.setCenterCode(strCenterCode);
     accMainVoucherSchema.setBranchCode(strBranchCode);
     accMainVoucherSchema.setAuxNumber("0");
     accMainVoucherSchema.setOperatorCode(strPayRefCode1);
     accMainVoucherSchema.setOperatorBranch(strBranchCode);
     accMainVoucherSchema.setVoucherDate(strPayRefDate1);
     accMainVoucherSchema.setVoucherType("1");
     accMainVoucherSchema.setGenerateWay("1");
     accMainVoucherSchema.setVoucherFlag("1");
     accMainVoucherSchema.setYearMonth(strYearMonth);
     blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

     
     String strRemark = "";
     if(strPayRefNoType1.equals("1")||
        strPayRefNoType1.equals("2"))
       strRemark = "联XXXXX业务XX";
     if(strPayRefNoType1.equals("3"))
       strRemark = "联XXXXX业务手续费";
     if(strPayRefNoType1.equals("4"))
       strRemark = "联XXXXX业务赔款";

     
     BLAccSubVoucher blAccSubVoucher = new BLAccSubVoucher();
     AccSubVoucherSchema accSubVoucherSchema = null;

     
     String strItemCode = "";
     for(int i=0; i<this.blPrpJpayRefRec.getSize(); i++)
     {
       if((blPrpJpayRefRec.getArr(i).getCoinsFlag().equals("1")||
           blPrpJpayRefRec.getArr(i).getCoinsFlag().equals("3"))&&
           blPrpJpayRefRec.getArr(i).getCoinsType().equals("1"))
       {
         String strWherePart = "";
         String strPayRefReasonTmp = "";
         BLPrpJpayRefRec blPrpJpayRefRecTmp = new BLPrpJpayRefRec();
         if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P")||
             blPrpJpayRefRec.getArr(i).getCertiType().equals("E"))
         {
           if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P40"))
             strPayRefReasonTmp = "('P42')";
           else
             strPayRefReasonTmp = "('R82','P82')";
         }
         else if(blPrpJpayRefRec.getArr(i).getCertiType().equals("C"))
         {
           strPayRefReasonTmp = "('F"+ blPrpJpayRefRec.getArr(i).getPayRefReason().substring(1) +"')";
         }
         else if(blPrpJpayRefRec.getArr(i).getCertiType().equals("S"))
           strPayRefReasonTmp = "('P92')";
         else
           strPayRefReasonTmp = "('')";

         strWherePart = " CertiNo='"+ blPrpJpayRefRec.getArr(i).getCertiNo() +"'"
                      + " And PayRefReason In " + strPayRefReasonTmp
                      + " And PayNo=" + blPrpJpayRefRec.getArr(i).getPayNo()
                      + " And CoinsType='2'";
         blPrpJpayRefRecTmp.query(strWherePart,0);
         for(int j=0;j<blPrpJpayRefRecTmp.getSize();j++)
         {
           
           accSubVoucherSchema = new AccSubVoucherSchema();
           accSubVoucherSchema.setAccBookType(strAccBookType);
           accSubVoucherSchema.setAccBookCode(strAccBookCode);
           accSubVoucherSchema.setCenterCode(strCenterCode);
           accSubVoucherSchema.setF26(strBranchCode);
           accSubVoucherSchema.setYearMonth(strYearMonth);
           accSubVoucherSchema.setCurrency(blPrpJpayRefRec.getArr(i).getCurrency1());
           accSubVoucherSchema.setF01(blPrpJpayRefRec.getArr(i).getClassCode());
           accSubVoucherSchema.setF05(blPrpJpayRefRec.getArr(i).getRiskCode());
           accSubVoucherSchema.setF28(blPrpJpayRefRec.getArr(i).getComCode());
           accSubVoucherSchema.setF27(blPrpJpayRefRec.getArr(i).getHandler1Code());
           if(blPrpJpayRefRecTmp.getArr(j).getCoinsType().equals("2"))
             accSubVoucherSchema.setF22(blPrpDcompany.getCenterCode(
                 blPrpJpayRefRecTmp.getArr(j).getComCode()));
           
           
           
           
           double dblExchRate = this.getExchRate(blPrpJpayRefRecTmp.getArr(i),
                                          accMainVoucherSchema.getVoucherDate(),false);

           accSubVoucherSchema.setExchangeRate(""+dblExchRate);

           String strPayRefReason = blPrpJpayRefRec.getArr(i).getPayRefReason();
           accSubVoucherSchema = this.getSubVoucher(strPayRefReason,accSubVoucherSchema);

           if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P")||
               blPrpJpayRefRec.getArr(i).getCertiType().equals("E"))
           {
             accSubVoucherSchema.setF02("03");
             
             
             
             accSubVoucherSchema.setItemCode("3001");
             if(accSubVoucherSchema.getCenterCode().substring(2).equals("000000"))
             {
             	accSubVoucherSchema.setDirectionIdx("01/03/");
             }
             else
             {
             	accSubVoucherSchema.setDirectionIdx("02/03/");     
             }             
           }

           
           if(blPrpJpayRefRec.getArr(i).getCertiType().equals("S"))
           {
             accSubVoucherSchema.setF02("04");
             
             
             
             accSubVoucherSchema.setItemCode("3001");
             if(accSubVoucherSchema.getCenterCode().substring(2).equals("000000"))
             {
             	accSubVoucherSchema.setDirectionIdx("01/04/");
             }
             else
             {
             	accSubVoucherSchema.setDirectionIdx("02/04/");     
             }             
           }

           
           if(blPrpJpayRefRec.getArr(i).getCertiType().equals("Y")||
              blPrpJpayRefRec.getArr(i).getCertiType().equals("C"))
           {
             accSubVoucherSchema.setF02("04");
             
             
             
             accSubVoucherSchema.setItemCode("3001");
             if(accSubVoucherSchema.getCenterCode().substring(2).equals("000000"))
             {
             	accSubVoucherSchema.setDirectionIdx("01/04/");
             }
             else
             {
             	accSubVoucherSchema.setDirectionIdx("02/04/");     
             }             
           }

           strItemCode = accSubVoucherSchema.getItemCode();

           
           if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P")||
              blPrpJpayRefRec.getArr(i).getCertiType().equals("E"))
           {
             double dblPlanFee = -1 * Double.parseDouble(Str.chgStrZero(blPrpJpayRefRecTmp.getArr(j).getPlanFee()));
             dblPlanFee = Str.round(dblPlanFee,2);
             accSubVoucherSchema.setDebitSource("" + dblPlanFee);
             double dblDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
             dblDebitDest = Str.round(Str.round(dblDebitDest,8),2);
             accSubVoucherSchema.setDebitDest(""+dblDebitDest);
           }
           else
           {
             double dblPlanFee = -1 * Double.parseDouble(Str.chgStrZero(blPrpJpayRefRecTmp.getArr(j).getPlanFee()));
             dblPlanFee = Str.round(dblPlanFee,2);
             accSubVoucherSchema.setCreditSource("" + dblPlanFee);
             double dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
             dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
             accSubVoucherSchema.setCreditDest(""+dblCreditDest);
           }
           accSubVoucherSchema.setRemark(strRemark);

           blAccSubVoucher.setArr(accSubVoucherSchema);


           
           accSubVoucherSchema = new AccSubVoucherSchema();
           accSubVoucherSchema.setAccBookType(strAccBookType);
           accSubVoucherSchema.setAccBookCode(strAccBookCode);
           accSubVoucherSchema.setCenterCode(strCenterCode);
           accSubVoucherSchema.setF26(strBranchCode);
           accSubVoucherSchema.setYearMonth(strYearMonth);
           accSubVoucherSchema.setCurrency(blPrpJpayRefRec.getArr(i).getCurrency1());
           accSubVoucherSchema.setF01(blPrpJpayRefRec.getArr(i).getClassCode());
           accSubVoucherSchema.setF05(blPrpJpayRefRec.getArr(i).getRiskCode());
           accSubVoucherSchema.setF28(blPrpJpayRefRec.getArr(i).getComCode());
           accSubVoucherSchema.setF27(blPrpJpayRefRec.getArr(i).getHandler1Code());
           if(blPrpJpayRefRecTmp.getArr(j).getCoinsType().equals("2"))
             accSubVoucherSchema.setF22(blPrpDcompany.getCenterCode(
                 blPrpJpayRefRecTmp.getArr(j).getCoinsCode()));

           
           
           

           accSubVoucherSchema.setExchangeRate(""+dblExchRate);

           strPayRefReason = blPrpJpayRefRec.getArr(i).getPayRefReason();
           accSubVoucherSchema = this.getSubVoucher(strPayRefReason,accSubVoucherSchema);

           if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P")||
               blPrpJpayRefRec.getArr(i).getCertiType().equals("E"))
           {
             accSubVoucherSchema.setF02("03");
             
             
             
             accSubVoucherSchema.setItemCode("3001");
             if(accSubVoucherSchema.getCenterCode().substring(2).equals("000000"))
             {
             	accSubVoucherSchema.setDirectionIdx("01/03/");
             }
             else
             {
             	accSubVoucherSchema.setDirectionIdx("02/03/");     
             }             
           }

           
           if(blPrpJpayRefRec.getArr(i).getCertiType().equals("S"))
           {
             accSubVoucherSchema.setF02("04");
             
             
             
             accSubVoucherSchema.setItemCode("3001");
             if(accSubVoucherSchema.getCenterCode().substring(2).equals("000000"))
             {
             	accSubVoucherSchema.setDirectionIdx("01/04/");
             }
             else
             {
             	accSubVoucherSchema.setDirectionIdx("02/04/");     
             }             
           }

           
           if(blPrpJpayRefRec.getArr(i).getCertiType().equals("Y")||
              blPrpJpayRefRec.getArr(i).getCertiType().equals("C"))
           {
             accSubVoucherSchema.setF02("04");
             
             
             
             accSubVoucherSchema.setItemCode("3001");
             if(accSubVoucherSchema.getCenterCode().substring(2).equals("000000"))
             {
             	accSubVoucherSchema.setDirectionIdx("01/04/");
             }
             else
             {
             	accSubVoucherSchema.setDirectionIdx("02/04/");     
             }             
           }

           strItemCode = accSubVoucherSchema.getItemCode();

           
           if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P")||
              blPrpJpayRefRec.getArr(i).getCertiType().equals("E"))
           {
             double dblPlanFee = -1 * Double.parseDouble(Str.chgStrZero(blPrpJpayRefRecTmp.getArr(j).getPlanFee()));
             dblPlanFee = Str.round(dblPlanFee,2);
             accSubVoucherSchema.setCreditSource("" + dblPlanFee);
             double dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
             dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
             accSubVoucherSchema.setCreditDest(""+dblCreditDest);
           }
           else
           {
             double dblPlanFee = -1 * Double.parseDouble(Str.chgStrZero(blPrpJpayRefRecTmp.getArr(j).getPlanFee()));
             dblPlanFee = Str.round(dblPlanFee,2);
             accSubVoucherSchema.setDebitSource("" + dblPlanFee);
             double dblDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
             dblDebitDest = Str.round(Str.round(dblDebitDest,8),2);
             accSubVoucherSchema.setDebitDest(""+dblDebitDest);
           }
           accSubVoucherSchema.setRemark(strRemark);

           blAccSubVoucher.setArr(accSubVoucherSchema);

           this.blPrpJpayRefRecCoins.setArr(blPrpJpayRefRecTmp.getArr(j));
         }
       }
     }

     
     double dblSumDebit = 0;
     double dblSumCredit = 0;
     for(int i=0; i<blAccSubVoucher.getSize(); i++)
     {
       dblSumDebit += Double.parseDouble(blAccSubVoucher.getArr(i).getDebitDest());
       dblSumCredit += Double.parseDouble(blAccSubVoucher.getArr(i).getCreditDest());
     }
     
     dblSumDebit = Str.round(dblSumDebit,2);
     dblSumCredit = Str.round(dblSumCredit,2);
     if(dblSumDebit!=dblSumCredit)
     {
       accSubVoucherSchema = new AccSubVoucherSchema();
       accSubVoucherSchema.setSchema(blAccSubVoucher.getArr(blAccSubVoucher.getSize()-1));
       accSubVoucherSchema.setCurrency("CNY");
       accSubVoucherSchema.setExchangeRate("1");
       
       accSubVoucherSchema.setItemCode("6061");
       accSubVoucherSchema.setDirectionIdx("00");
       accSubVoucherSchema.setRemark("调整汇兑损益差");
       if(dblSumDebit-dblSumCredit>0)
       {
         accSubVoucherSchema.setDebitSource("0");
         accSubVoucherSchema.setDebitDest("0");
         accSubVoucherSchema.setCreditSource("" + (dblSumDebit - dblSumCredit));
         accSubVoucherSchema.setCreditDest("" + (dblSumDebit - dblSumCredit));
       }
       else
       {
         accSubVoucherSchema.setDebitSource("" + (dblSumCredit - dblSumDebit));
         accSubVoucherSchema.setDebitDest("" + (dblSumCredit - dblSumDebit));
         accSubVoucherSchema.setCreditSource("0");
         accSubVoucherSchema.setCreditDest("0");
       }
       blAccSubVoucher.setArr(accSubVoucherSchema);
     }

     
     for(int i=0; i<blAccSubVoucher.getSize(); i++)
     {
       blAccSubVoucher.getArr(i).setSuffixNo("" + (i + 1));
       blAccSubVoucher.createRawArticle(blAccSubVoucher.getArr(i),strItemCode);
     }

     blAccSubVoucher.voucherComp("111");
     blAccSubVoucher.voucherOrder();

     blAccVoucher.setBLAccSubVoucher(blAccSubVoucher);
     blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());

     return blAccVoucher;
   }

    /**
     * @author lijibin 20050630 获取实收凭证中业务子表信息的兑换率
     * @param PrpJpayRefRecSchema
     * @return dblExchRate
     * @throws Exception
     */
    private double getExchRate(PrpJpayRefRecSchema iSchema,String iDate) throws Exception
    {
      double dblExchRate = 1;
      BLPrpJplanFee blPrpJplanFeeTmp = new BLPrpJplanFee();
      String strWherePart = "";

      
      if(iSchema.getCertiType().equals("R"))
      {
        
        if(iSchema.getCurrency2().equals("CNY"))
          dblExchRate = Double.parseDouble(iSchema.getExchangeRate());
        
        else
          dblExchRate = PubTools.getExchangeRate(iSchema.getCurrency1(), "CNY", iDate);
        return dblExchRate;
      }
      
      
      
      
      
      
      
      
      blPrpJplanFeeTmp.getNewInfo(iSchema.getCertiType(),iSchema.getCertiNo(),iSchema.getSerialNo(),iSchema.getPayRefReason());
      
      if(blPrpJplanFeeTmp.getSize()==0)
      {
        throw new UserException( -98, -1167, "BLPrpJpayRefMain.genAccVoucher",
                                "无此应收应付信息："+iSchema.getCertiNo());
      }
      
      if(Double.parseDouble(blPrpJplanFeeTmp.getArr(0).getPlanFeeCNY())!=0)
      {
        dblExchRate = Double.parseDouble(blPrpJplanFeeTmp.getArr(0).
                                         getExchangeRate());
      }
      else
      {
        
        if(!iSchema.getCurrency1().equals("CNY"))
        {
          
          if(iSchema.getCurrency2().equals("CNY"))
            dblExchRate = Double.parseDouble(iSchema.getExchangeRate());
          
          else
            dblExchRate = PubTools.getExchangeRate(iSchema.getCurrency1(), "CNY", iDate);
        }
        blPrpJplanFeeTmp.getArr(0).setExchangeRate(""+dblExchRate);

        
        if((blPrpJplanFeeTmp.getArr(0).getCertiType().equals("P")&&
           PubTools.compareDate(blPrpJplanFeeTmp.getArr(0).getStartDate(),iDate)<=0||
           blPrpJplanFeeTmp.getArr(0).getCertiType().equals("E")&&
           PubTools.compareDate(blPrpJplanFeeTmp.getArr(0).getValidDate(),iDate)<=0||
           blPrpJplanFeeTmp.getArr(0).getCertiType().equals("S"))&&
          !blPrpJplanFeeTmp.getArr(0).getRiskCode().equals("YAB0"))
          this.blPrpJplanFee.setArr(blPrpJplanFeeTmp.getArr(0));
      }
      return dblExchRate;
    }

    /**
     * @author lijibin 20050630 获取实收凭证中业务子表信息的兑换率
     * @param PrpJpayRefRecSchema
     * @return dblExchRate
     * @throws Exception
     */
    private double getExchRate(PrpJpayRefRecSchema iSchema,String iDate,
        boolean iFlag) throws Exception
    {
      double dblExchRate = 1;
      BLPrpJplanFee blPrpJplanFeeTmp = new BLPrpJplanFee();
      String strWherePart = "";
      
      
      
      
      
      
      
      
      blPrpJplanFeeTmp.getNewInfo(iSchema.getCertiType(),iSchema.getCertiNo(),iSchema.getSerialNo(),iSchema.getPayRefReason());
      
      if(blPrpJplanFeeTmp.getSize()==0)
      {
        throw new UserException( -98, -1167, "BLPrpJpayRefMain.genAccVoucher",
                                "无此应收应付信息："+iSchema.getCertiNo());
      }
      
      if(Double.parseDouble(blPrpJplanFeeTmp.getArr(0).getPlanFeeCNY())!=0)
      {
        dblExchRate = Double.parseDouble(blPrpJplanFeeTmp.getArr(0).
                                         getExchangeRate());
      }
      else
      {
        
        if(!iSchema.getCurrency1().equals("CNY"))
        {
          
          if(iSchema.getCurrency2().equals("CNY"))
            dblExchRate = Double.parseDouble(iSchema.getExchangeRate());
          
          else
            dblExchRate = PubTools.getExchangeRate(iSchema.getCurrency1(), "CNY", iDate);
        }
        blPrpJplanFeeTmp.getArr(0).setExchangeRate(""+dblExchRate);

        
        if(iFlag)
        {
          if((blPrpJplanFeeTmp.getArr(0).getCertiType().equals("P")&&
             PubTools.compareDate(blPrpJplanFeeTmp.getArr(0).getStartDate(),iDate)<=0||
             blPrpJplanFeeTmp.getArr(0).getCertiType().equals("E")&&
             PubTools.compareDate(blPrpJplanFeeTmp.getArr(0).getValidDate(),iDate)<=0||
             blPrpJplanFeeTmp.getArr(0).getCertiType().equals("S"))&&
            !blPrpJplanFeeTmp.getArr(0).getRiskCode().equals("YAB0"))
            this.blPrpJplanFee.setArr(blPrpJplanFeeTmp.getArr(0));
        }
      }
      return dblExchRate;
    }

    /**
     * @author lijibin 20050630 获取实收凭证中业务子表信息的兑换率
     * @param PrpJpayRefRecSchema
     * @return dblExchRate
     * @throws Exception
     */
    private double getExchRate(PrpJpayRefRecSchema iPrpJpayRefRecSchema,PrpJplanFeeSchema iPrpJplanFeeSchema,String iDate) throws Exception
    {
      double dblExchRate = 1;

      
      if(Double.parseDouble(iPrpJplanFeeSchema.getPlanFeeCNY())!=0)
      {
        dblExchRate = Double.parseDouble(iPrpJplanFeeSchema.
                                         getExchangeRate());
      }
      else
      {
        
        if(!iPrpJpayRefRecSchema.getCurrency1().equals("CNY"))
        {
          
          if(iPrpJpayRefRecSchema.getCurrency2().equals("CNY"))
            dblExchRate = Double.parseDouble(iPrpJpayRefRecSchema.getExchangeRate());
          
          else
            dblExchRate = PubTools.getExchangeRate(iPrpJpayRefRecSchema.getCurrency1(), "CNY", iDate);
        }
        iPrpJplanFeeSchema.setExchangeRate(""+dblExchRate);

        
        if((iPrpJplanFeeSchema.getCertiType().equals("P")&&
           PubTools.compareDate(iPrpJplanFeeSchema.getStartDate(),iDate)<=0||
           iPrpJplanFeeSchema.getCertiType().equals("E")&&
           PubTools.compareDate(iPrpJplanFeeSchema.getValidDate(),iDate)<=0||
           iPrpJplanFeeSchema.getCertiType().equals("S"))&&
          !iPrpJplanFeeSchema.getRiskCode().equals("YAB0"))
          this.blPrpJplanFee.setArr(iPrpJplanFeeSchema);
      }
      return dblExchRate;
    }

    /**
     * @author lijibin 20050617
     * @return blAccVoucher 凭证信息
     * @throws UserException
     * @throws Exception
     */
   public BLAccVoucher genAccVoucher(String strPayRefDate1,String strPayRefCode1,String strPayRefNoType1,String iAuxNumber) throws UserException,Exception
   {
     BLAccVoucher blAccVoucher = new BLAccVoucher();
     String strComCode = this.blPrpJpayRefRec.getArr(0).getComCode();
     BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
     String strCenterCode = blPrpDcompany.getCenterCode(strComCode);
     String strBranchCode = blPrpDcompany.getBranchCode(strComCode);
     if(strBranchCode.equals(""))
       strBranchCode = strCenterCode;
     String strAccBookType = SysConst.getProperty("ACCBOOKTYPE");
     String strAccBookCode = "";
     if(strAccBookType.equals("02"))
       strAccBookCode = SysConst.getProperty("ACCBOOKCODE");
     String strPayRefDate = strPayRefDate1;

     
     BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
     String strSQL = "";
     String strYearMonth = "";
     strSQL = " CenterCode='"+strCenterCode
         + "' AND AccBookType='"+strAccBookType
         + "' AND AccBookCode='"+strAccBookCode
         + "' AND AccMonthStat='3'";
     blAccMonthTrace.query(strSQL);
     if(blAccMonthTrace.getSize()==0)
       throw new UserException( -98, -1167, "BLPrpJpayRefMain.genAccVoucher",
                               "核算单位" + strCenterCode + "还没有初始化");
     else
       
      
      strYearMonth = strPayRefDate1.substring(0,4)+strPayRefDate1.substring(5,7);

     
     AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
     accMainVoucherSchema.setAccBookType(strAccBookType);
     accMainVoucherSchema.setAccBookCode(strAccBookCode);
     accMainVoucherSchema.setCenterCode(strCenterCode);
     accMainVoucherSchema.setBranchCode(strBranchCode);
     if(iAuxNumber.equals("")){
         accMainVoucherSchema.setAuxNumber(""+this.blPrpJpayRefDetail.getSize());
     }else{
         accMainVoucherSchema.setAuxNumber(iAuxNumber);
     }
     accMainVoucherSchema.setOperatorCode(strPayRefCode1);
     accMainVoucherSchema.setOperatorBranch(strBranchCode);
     accMainVoucherSchema.setVoucherDate(strPayRefDate1);
     accMainVoucherSchema.setVoucherType("1");
     
     if(this.getArr(0).getPayRefNoType().equals("6"))
       accMainVoucherSchema.setGenerateWay("0");
     else
       accMainVoucherSchema.setGenerateWay("1");
     accMainVoucherSchema.setVoucherFlag("1");
     accMainVoucherSchema.setYearMonth(strYearMonth);

     blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

     
     String strRemark = "";
     String strPreRemark = "";
     if(strPayRefNoType1.equals("1")||
        strPayRefNoType1.equals("2"))
       strRemark = "XX";
     if(strPayRefNoType1.equals("3"))
       strRemark = "手续费";
     if(strPayRefNoType1.equals("4"))
       strRemark = "赔款";
     if(strPayRefNoType1.equals("5"))
       strRemark = "出单费";
     if(strPayRefNoType1.equals("6"))
     {
       strRemark = this.getBLPrpJpayRefRec().getArr(0).getCoinsName()+"分XXXXX业务往来款";
     }

     DBAccBankAccount dbAccBankAccount = new DBAccBankAccount();
     
     BLAccSubVoucher blAccSubVoucher = new BLAccSubVoucher();
     AccSubVoucherSchema accSubVoucherSchema = null;
     BLPrpDcode blPrpDcode = new BLPrpDcode();
     for(int i=0; i<this.blPrpJpayRefDetail.getSize(); i++)
     {
       accSubVoucherSchema = new AccSubVoucherSchema();
       accSubVoucherSchema.setAccBookType(strAccBookType);
       accSubVoucherSchema.setAccBookCode(strAccBookCode);
       accSubVoucherSchema.setCenterCode(strCenterCode);
       accSubVoucherSchema.setYearMonth(strYearMonth);
       accSubVoucherSchema.setF22(blPrpJpayRefDetail.getArr(i).getFlag());
       accSubVoucherSchema.setF26(strBranchCode);
       accSubVoucherSchema.setCurrency(blPrpJpayRefDetail.getArr(i).getCurrency2());
       accSubVoucherSchema.setF25(blPrpJpayRefDetail.getArr(i).getBankCode());
       
       accSubVoucherSchema.setF27(blPrpJpayRefRec.getArr(0).getHandler1Code());
       
       accSubVoucherSchema.setF28(blPrpJpayRefRec.getArr(0).getComCode());
       if(blPrpJpayRefDetail.getArr(i).getPayWay().substring(0,1).equals("2"))
       {
         accSubVoucherSchema.setF29(blPrpJpayRefDetail.getArr(i).getAccountNo());
       }

       this.blPrpJpayRefDetail.getArr(i).setFlag("");
       if(!accSubVoucherSchema.getF29().equals(""))
       {
         dbAccBankAccount = new DBAccBankAccount();
         dbAccBankAccount.getInfo(accSubVoucherSchema.getF29(),strCenterCode);
         accSubVoucherSchema.setF09("0"+dbAccBankAccount.getSaveNature());
       }
       
       accSubVoucherSchema.setF03(blPrpJpayRefDetail.getArr(i).getPayRefDate());
       
       if(!blPrpJpayRefDetail.getArr(i).getCheckType().equals(""))
         accSubVoucherSchema.setCheckNo(blPrpJpayRefDetail.getArr(i).getCheckType() + "/" +
                                        blPrpJpayRefDetail.getArr(i).getCheckNo());
       
       double dblExchRate = 1;
       dblExchRate = PubTools.getExchangeRate(accSubVoucherSchema.getCurrency(),
                                              "CNY",
                                              accMainVoucherSchema.getVoucherDate());
       accSubVoucherSchema.setExchangeRate(""+dblExchRate);

       String strPayWay = blPrpJpayRefDetail.getArr(i).getPayWay();
       
       accSubVoucherSchema = this.getBankSubVoucherNew(strPayWay,accSubVoucherSchema);
       
       if(strPayWay.substring(1,2).equals("1")&&!strPayWay.equals("615"))
       {
         
       	 
         if(strPayWay.substring(0,1).equals("1")||strPayWay.substring(0,1).equals("2")
         	 ||strPayWay.substring(0,1).equals("5")){
             accSubVoucherSchema.setRemark(blPrpDcode.translateCode("PayWay",strPayWay,true)+strRemark);
         }else{
             accSubVoucherSchema.setRemark(blPrpDcode.translateCode("PayWay",strPayWay,true));
         }
         accSubVoucherSchema.setDebitSource(""+Math.abs(Double.parseDouble(blPrpJpayRefDetail.getArr(i).getPayRefFee())));
         double dblDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
         dblDebitDest = Str.round(Str.round(dblDebitDest,8),2);
         accSubVoucherSchema.setDebitDest(""+Math.abs(dblDebitDest));
       }
       else
       {
      	 
         if(strPayWay.substring(0,1).equals("1")||strPayWay.substring(0,1).equals("2")
         	  ||strPayWay.substring(0,1).equals("5")){
             accSubVoucherSchema.setRemark(blPrpDcode.translateCode("PayWay",strPayWay,true)+strRemark);
         }else{
             accSubVoucherSchema.setRemark(blPrpDcode.translateCode("PayWay",strPayWay,true));
         }
         
         if(strPayWay.equals("615")){
            accSubVoucherSchema.setCreditSource(""+Math.abs((-1)*Double.parseDouble(blPrpJpayRefDetail.getArr(i).getPayRefFee())));
         }else{
            accSubVoucherSchema.setCreditSource(""+Math.abs(Double.parseDouble(blPrpJpayRefDetail.getArr(i).getPayRefFee())));
         }
         
         double dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
         dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
         accSubVoucherSchema.setCreditDest(""+Math.abs(dblCreditDest));
       }
       blAccSubVoucher.setArr(accSubVoucherSchema);
       if(!strPayWay.equals("420")){
       	   strPreRemark = accSubVoucherSchema.getRemark();
       }
     }
     strRemark = accSubVoucherSchema.getRemark();

     
     String strItemCode = "";
     for(int i=0; i<this.blPrpJpayRefRec.getSize(); i++)
     {
       double dblSource = Double.parseDouble(Str.chgStrZero(blPrpJpayRefRec.getArr(i).getPlanFee()));

       
       if((blPrpJpayRefRec.getArr(i).getCoinsFlag().equals("1")||
           blPrpJpayRefRec.getArr(i).getCoinsFlag().equals("3"))&&
           blPrpJpayRefRec.getArr(i).getCoinsType().equals("1"))
       {
         String strWherePart = "";
         String strPayRefReasonTmp = "";
         BLPrpJplanFee blPrpJplanFeeCoins = new BLPrpJplanFee();
         if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P")||
             blPrpJpayRefRec.getArr(i).getCertiType().equals("E"))
         {
           if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P40"))
             strPayRefReasonTmp = "('P41','P42')";
           else
             strPayRefReasonTmp = "('R81','R82','P81','P82')";
         }
         else if(blPrpJpayRefRec.getArr(i).getCertiType().equals("S"))
           strPayRefReasonTmp = "('P91','P92')";
         else if(blPrpJpayRefRec.getArr(i).getCertiType().equals("C"))
         {
           strPayRefReasonTmp = "('F"+ blPrpJpayRefRec.getArr(i).getPayRefReason().substring(1) +"',"
                              + "'S"+ blPrpJpayRefRec.getArr(i).getPayRefReason().substring(1) +"')";
         }
         else
           strPayRefReasonTmp = "('')";

         strWherePart = " CertiNo='"+ blPrpJpayRefRec.getArr(i).getCertiNo() +"'"
                      + " And PayRefReason In " + strPayRefReasonTmp
                      + " And PayNo=" + blPrpJpayRefRec.getArr(i).getPayNo();
         blPrpJplanFeeCoins.query(strWherePart,0);
         for(int j=0;j<blPrpJplanFeeCoins.getSize();j++)
         {
           
           dblSource = dblSource + Double.parseDouble(Str.chgStrZero(blPrpJplanFeeCoins.getArr(j).getPlanFee()));
           dblSource = Str.round(dblSource,2);


           
           accSubVoucherSchema = new AccSubVoucherSchema();
           accSubVoucherSchema.setAccBookType(strAccBookType);
           accSubVoucherSchema.setAccBookCode(strAccBookCode);
           accSubVoucherSchema.setCenterCode(strCenterCode);
           accSubVoucherSchema.setF26(strBranchCode);
           accSubVoucherSchema.setYearMonth(strYearMonth);
           accSubVoucherSchema.setCurrency(blPrpJpayRefRec.getArr(i).getCurrency1());
           accSubVoucherSchema.setF01(blPrpJpayRefRec.getArr(i).getClassCode());
           accSubVoucherSchema.setF05(blPrpJpayRefRec.getArr(i).getRiskCode());
           accSubVoucherSchema.setF28(blPrpJpayRefRec.getArr(i).getComCode());
           accSubVoucherSchema.setF27(blPrpJpayRefRec.getArr(i).getHandler1Code());
           if(blPrpJplanFeeCoins.getArr(j).getCoinsType().equals("2"))
           {
             accSubVoucherSchema.setF22("00000000");
             accSubVoucherSchema.setRemark("从联方");
           }
           else
           {
             accSubVoucherSchema.setF22(blPrpJplanFeeCoins.getArr(j).getCoinsCode());
             
             DBPrpDreins dbPrpDreins = new DBPrpDreins();
             int intResult = dbPrpDreins.getInfo(blPrpJplanFeeCoins.getArr(j).getCoinsCode());
             if(intResult==0){
             	accSubVoucherSchema.setRemark("应付" + dbPrpDreins.getShortName() + "共XXXXXXX");
             }else{
                throw new UserException( -98, -1167, "BLPrpJpayRefMain.genAccVoucher",
                        "没有" + blPrpJplanFeeCoins.getArr(j).getCoinsCode() + "的信息");
             }
             
           }

           

           double dblExchRate = this.getExchRate(blPrpJpayRefRec.getArr(i),
                                          blPrpJplanFeeCoins.getArr(j),
                                          accMainVoucherSchema.getVoucherDate());

           accSubVoucherSchema.setExchangeRate(""+dblExchRate);

           String strPayRefReason = blPrpJpayRefRec.getArr(i).getPayRefReason();
           accSubVoucherSchema = this.getSubVoucher(strPayRefReason,accSubVoucherSchema);

           if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P")||
               blPrpJpayRefRec.getArr(i).getCertiType().equals("E"))
           {
             if(blPrpJplanFeeCoins.getArr(j).getCoinsType().equals("2"))
             {
               accSubVoucherSchema.setF02("03");
               
               
               
               accSubVoucherSchema.setItemCode("3001");
               if(accSubVoucherSchema.getCenterCode().substring(2).equals("000000"))
               {
               	accSubVoucherSchema.setDirectionIdx("01/03/");
               }
               else
               {
               	accSubVoucherSchema.setDirectionIdx("02/03/");     
               }               
               accSubVoucherSchema.setRemark("从联方");
             }
             else if(blPrpJplanFeeCoins.getArr(j).getCoinsType().equals("3"))
             {
               accSubVoucherSchema.setF21("05");
               
               
               accSubVoucherSchema.setItemCode("2241");
               accSubVoucherSchema.setDirectionIdx("01/");               
               
               DBPrpDreins dbPrpDreins = new DBPrpDreins();
               int intResult = dbPrpDreins.getInfo(accSubVoucherSchema.getF22());
               if(intResult==0){
               	accSubVoucherSchema.setRemark("应付" + dbPrpDreins.getShortName() + "共XXXXXXX");
               }else{
                  throw new UserException( -98, -1167, "BLPrpJpayRefMain.genAccVoucher",
                          "没有" + accSubVoucherSchema.getF22() + "的信息");
               }
               
             }
           }

           
           if(blPrpJpayRefRec.getArr(i).getCertiType().equals("S"))
           {
             if(blPrpJplanFeeCoins.getArr(j).getCoinsType().equals("2"))
             {
               accSubVoucherSchema.setF02("04");
               
               
               
               accSubVoucherSchema.setItemCode("3001");
               if(accSubVoucherSchema.getCenterCode().substring(2).equals("000000"))
               {
               	accSubVoucherSchema.setDirectionIdx("01/04/");
               }
               else
               {
               	accSubVoucherSchema.setDirectionIdx("02/04/");     
               }               
             }
             else if(blPrpJplanFeeCoins.getArr(j).getCoinsType().equals("3"))
             {
               accSubVoucherSchema.setF16("02");
               accSubVoucherSchema.setF21("04");
               accSubVoucherSchema.setItemCode("1191");
               accSubVoucherSchema.setDirectionIdx("04/02/");
             }
           }

           
           
           if(blPrpJpayRefRec.getArr(i).getCertiType().equals("Y")||
              blPrpJpayRefRec.getArr(i).getCertiType().equals("C"))
           {
             DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
             dbPrpJplanFee.getInfo(blPrpJpayRefRec.getArr(i).getCertiType(),
                                   blPrpJpayRefRec.getArr(i).getCertiNo(),
                                   blPrpJpayRefRec.getArr(i).getSerialNo(),
                                   blPrpJpayRefRec.getArr(i).getPayRefReason());
             if(blPrpJplanFeeCoins.getArr(j).getCoinsType().equals("2"))
             {
               accSubVoucherSchema.setF02("04");
               
               
               
               accSubVoucherSchema.setItemCode("3001");
               if(accSubVoucherSchema.getCenterCode().substring(2).equals("000000"))
               {
               	accSubVoucherSchema.setDirectionIdx("01/04/");
               }
               else
               {
               	accSubVoucherSchema.setDirectionIdx("02/04/");     
               }               
             }
             else if(blPrpJplanFeeCoins.getArr(j).getCoinsType().equals("3"))
             {
               accSubVoucherSchema.setF16("01");
               accSubVoucherSchema.setF21("04");
               accSubVoucherSchema.setItemCode("1191");
               accSubVoucherSchema.setDirectionIdx("04/01/");
             }
           }

           strItemCode = accSubVoucherSchema.getItemCode();

           
           if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P")||
              blPrpJpayRefRec.getArr(i).getCertiType().equals("E"))
           {
             double dblPlanFee = -1 * Double.parseDouble(Str.chgStrZero(blPrpJplanFeeCoins.getArr(j).getPlanFee()));
             dblPlanFee = Str.round(dblPlanFee,2);
             accSubVoucherSchema.setCreditSource("" + dblPlanFee);
             double dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
             dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
             accSubVoucherSchema.setCreditDest(""+dblCreditDest);
           }
           else
           {
             double dblPlanFee = -1 * Double.parseDouble(Str.chgStrZero(blPrpJplanFeeCoins.getArr(j).getPlanFee()));
             dblPlanFee = Str.round(dblPlanFee,2);
             accSubVoucherSchema.setDebitSource("" + dblPlanFee);
             double dblDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
             dblDebitDest = Str.round(Str.round(dblDebitDest,8),2);
             accSubVoucherSchema.setDebitDest(""+dblDebitDest);
           }
           blAccSubVoucher.setArr(accSubVoucherSchema);
         }
       }


       accSubVoucherSchema = new AccSubVoucherSchema();
       accSubVoucherSchema.setAccBookType(strAccBookType);
       accSubVoucherSchema.setAccBookCode(strAccBookCode);
       accSubVoucherSchema.setCenterCode(strCenterCode);
       accSubVoucherSchema.setF26(strBranchCode);
       accSubVoucherSchema.setYearMonth(strYearMonth);
       accSubVoucherSchema.setCurrency(blPrpJpayRefRec.getArr(i).getCurrency1());
       accSubVoucherSchema.setF01(blPrpJpayRefRec.getArr(i).getClassCode());
       accSubVoucherSchema.setF05(blPrpJpayRefRec.getArr(i).getRiskCode());
       accSubVoucherSchema.setF28(blPrpJpayRefRec.getArr(i).getComCode());
       accSubVoucherSchema.setF27(blPrpJpayRefRec.getArr(i).getHandler1Code());
       
       if(strRemark.equals("转未签单预收")){
           accSubVoucherSchema.setRemark(strPreRemark);
       }else{
           accSubVoucherSchema.setRemark(strRemark);
       }
       
       double dblExchRate = this.getExchRate(blPrpJpayRefRec.getArr(i),
                                             accMainVoucherSchema.getVoucherDate());

       accSubVoucherSchema.setExchangeRate(""+dblExchRate);

       String strPayRefReason = blPrpJpayRefRec.getArr(i).getPayRefReason();
       accSubVoucherSchema = this.getSubVoucher(strPayRefReason,accSubVoucherSchema);
       
       if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P"))
       {
         if(PubTools.compareDate(blPrpJpayRefRec.getArr(i).getStartDate(),strPayRefDate) > 0)
         {
           accSubVoucherSchema.setItemCode("2121");
           accSubVoucherSchema.setDirectionIdx("02/");
         }else if (blPrpJplanFee.checkAllowUnpayed(blPrpJpayRefRec.getArr(i).getComCode(),
                                                  blPrpJpayRefRec.getArr(i).getRiskCode())){
            accSubVoucherSchema.setItemCode("4101");
         }else{
            
         	DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
            dbPrpJplanFee.getInfo(blPrpJpayRefRec.getArr(i).getCertiType(),
                                  blPrpJpayRefRec.getArr(i).getCertiNo(),
                                  blPrpJpayRefRec.getArr(i).getSerialNo(),
                                  blPrpJpayRefRec.getArr(i).getPayRefReason());
            if(!dbPrpJplanFee.getFlag().equals("") && dbPrpJplanFee.getFlag().substring(0,1).equals("1")){
              accSubVoucherSchema.setItemCode("2121");
              accSubVoucherSchema.setDirectionIdx("02/");
            }
         }
         if(blPrpJpayRefRec.getArr(i).getRiskCode().equals("YAB0"))
         {
           accSubVoucherSchema.setItemCode("2121");
           accSubVoucherSchema.setDirectionIdx("01/");
         }
       }
       
       if(blPrpJpayRefRec.getArr(i).getCertiType().equals("E"))
       {
         if(PubTools.compareDate(blPrpJpayRefRec.getArr(i).getValidDate(),strPayRefDate) > 0)
         {
           accSubVoucherSchema.setItemCode("2121");
           accSubVoucherSchema.setDirectionIdx("02/");
         }else if (blPrpJplanFee.checkAllowUnpayed(blPrpJpayRefRec.getArr(i).getComCode(),
                                                  blPrpJpayRefRec.getArr(i).getRiskCode())){
            accSubVoucherSchema.setItemCode("4101");
         }else{
            
            DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
            dbPrpJplanFee.getInfo(blPrpJpayRefRec.getArr(i).getCertiType(),
                                  blPrpJpayRefRec.getArr(i).getCertiNo(),
                                  blPrpJpayRefRec.getArr(i).getSerialNo(),
                                  blPrpJpayRefRec.getArr(i).getPayRefReason());
            if(!dbPrpJplanFee.getFlag().equals("") && dbPrpJplanFee.getFlag().substring(0,1).equals("1")){
              accSubVoucherSchema.setItemCode("2121");
              accSubVoucherSchema.setDirectionIdx("02/");
            }
         }
         if(blPrpJpayRefRec.getArr(i).getRiskCode().equals("YAB0"))
         {
           accSubVoucherSchema.setItemCode("2121");
           accSubVoucherSchema.setDirectionIdx("01/");
         }
         if(blPrpJpayRefRec.getArr(i).getRiskCode().equals("YAB0"))
         {
           accSubVoucherSchema.setItemCode("2121");
           accSubVoucherSchema.setDirectionIdx("01/");
         }
       }
       
       if(blPrpJpayRefRec.getArr(i).getCertiType().equals("S"))
       {
         if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P91"))
         {
           accSubVoucherSchema.setF22(blPrpJpayRefRec.getArr(i).getCoinsCode());
           accSubVoucherSchema.setF16("02");
           accSubVoucherSchema.setF21("04");
           accSubVoucherSchema.setItemCode("1191");
           accSubVoucherSchema.setDirectionIdx("04/02/");
         }
         else
           accSubVoucherSchema.setItemCode("2111");  
       }

       
       if(blPrpJpayRefRec.getArr(i).getCertiType().equals("F"))
       {
         if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P95"))
         {
           accSubVoucherSchema.setF16("99");
           accSubVoucherSchema.setItemCode("6601");
           accSubVoucherSchema.setDirectionIdx("99/");
         }
         else if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P96"))
         {
           accSubVoucherSchema.setF08("99");
           accSubVoucherSchema.setItemCode("6601");
           accSubVoucherSchema.setDirectionIdx("99/");
         }
       }

       
       if(blPrpJpayRefRec.getArr(i).getCertiType().equals("Y")||
          blPrpJpayRefRec.getArr(i).getCertiType().equals("C"))
       {
         DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
         dbPrpJplanFee.getInfo(blPrpJpayRefRec.getArr(i).getCertiType(),
                               blPrpJpayRefRec.getArr(i).getCertiNo(),
                               blPrpJpayRefRec.getArr(i).getSerialNo(),
                               blPrpJpayRefRec.getArr(i).getPayRefReason());
         if(!dbPrpJplanFee.getVoucherNo().equals("")&&
            !dbPrpJplanFee.getVoucherNo().equals("0")){
           if(blPrpJpayRefRec.getArr(i).getPayRefReason().startsWith("S"))
           {
             accSubVoucherSchema.setF16("01");
             accSubVoucherSchema.setF21("04");
             accSubVoucherSchema.setItemCode("1191");
             accSubVoucherSchema.setDirectionIdx("04/01/");
           }
           else
           {
             
             if(dbPrpJplanFee.getRiskCode().substring(0,2).equals("05")&&
              dbPrpJplanFee.getPayRefReason().equals("P62")){
                 accSubVoucherSchema.setF21("34");
                 accSubVoucherSchema.setItemCode("6601");
                 accSubVoucherSchema.setDirectionIdx("34/");
             }else{
                 accSubVoucherSchema.setItemCode("2123");
             }
           }
         }
       }

       strItemCode = accSubVoucherSchema.getItemCode();
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       if(blPrpJpayRefRec.getArr(i).getCertiType().equals("R"))
       {
         accSubVoucherSchema.setRemark(blPrpJpayRefRec.getArr(i).getRemark());
         accSubVoucherSchema.setF22(blPrpJpayRefRec.getArr(i).getCoinsCode());
         if(strItemCode.length()>4)
           strItemCode = strItemCode.substring(0,4);
       }

       
       if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P")||
          blPrpJpayRefRec.getArr(i).getCertiType().equals("E"))
       {
         if((blPrpJpayRefRec.getArr(i).getCoinsFlag().equals("1")||
             blPrpJpayRefRec.getArr(i).getCoinsFlag().equals("3"))&&
             blPrpJpayRefRec.getArr(i).getCoinsType().equals("3"))
         {
           accSubVoucherSchema.setDebitSource("" + Str.round(-1*dblSource,2));
           double dblDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
           dblDebitDest = Str.round(Str.round(dblDebitDest,8),2);
           accSubVoucherSchema.setDebitDest(""+dblDebitDest);
         }
         else
         {
           
           accSubVoucherSchema.setCreditSource("" + dblSource);
           double dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
           dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
           accSubVoucherSchema.setCreditDest(""+dblCreditDest);
         }
       }
       else
       {
         if((blPrpJpayRefRec.getArr(i).getCoinsFlag().equals("1")||
             blPrpJpayRefRec.getArr(i).getCoinsFlag().equals("3"))&&
             blPrpJpayRefRec.getArr(i).getCoinsType().equals("3")&&
             blPrpJpayRefRec.getArr(i).getCertiType().equals("S"))
         {
           accSubVoucherSchema.setCreditSource("" + Str.round(-1*dblSource,2));
           double dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
           dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
           accSubVoucherSchema.setCreditDest(""+dblCreditDest);
         }
         else
         {
           
           accSubVoucherSchema.setDebitSource("" + dblSource);
           double dblDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
           dblDebitDest = Str.round(Str.round(dblDebitDest,8),2);
           accSubVoucherSchema.setDebitDest(""+dblDebitDest);
         }
       }
       blAccSubVoucher.setArr(accSubVoucherSchema);
     }

     
     double dblSumDebit = 0;
     double dblSumCredit = 0;
     String strCurrency = "CNY";
     for(int i=0; i<blAccSubVoucher.getSize(); i++)
     {
       dblSumDebit += Double.parseDouble(blAccSubVoucher.getArr(i).getDebitDest());
       dblSumCredit += Double.parseDouble(blAccSubVoucher.getArr(i).getCreditDest());
       if(!blAccSubVoucher.getArr(i).getCurrency().equals("CNY"))
         strCurrency = blAccSubVoucher.getArr(i).getCurrency();
     }
     
     dblSumDebit = Str.round(dblSumDebit,2);
     dblSumCredit = Str.round(dblSumCredit,2);
     if(dblSumDebit!=dblSumCredit)
     {
       accSubVoucherSchema = new AccSubVoucherSchema();
       accSubVoucherSchema.setSchema(blAccSubVoucher.getArr(blAccSubVoucher.getSize()-1));
       accSubVoucherSchema.setCurrency("CNY");
       accSubVoucherSchema.setExchangeRate("1");
       accSubVoucherSchema.setItemCode("4303");
       accSubVoucherSchema.setDirectionIdx("00");
       accSubVoucherSchema.setRemark("调整汇兑损益差");
       if(dblSumDebit-dblSumCredit>0)
       {
         accSubVoucherSchema.setDebitSource("0");
         accSubVoucherSchema.setDebitDest("0");
         accSubVoucherSchema.setCreditSource("" + (dblSumDebit - dblSumCredit));
         accSubVoucherSchema.setCreditDest("" + (dblSumDebit - dblSumCredit));
       }
       else
       {
         accSubVoucherSchema.setDebitSource("" + (dblSumCredit - dblSumDebit));
         accSubVoucherSchema.setDebitDest("" + (dblSumCredit - dblSumDebit));
         accSubVoucherSchema.setCreditSource("0");
         accSubVoucherSchema.setCreditDest("0");
       }
       blAccSubVoucher.setArr(accSubVoucherSchema);
     }

     
     for(int i=0; i<blAccSubVoucher.getSize(); i++)
     {
       blAccSubVoucher.getArr(i).setSuffixNo("" + (i + 1));
       blAccSubVoucher.createRawArticle(blAccSubVoucher.getArr(i),strItemCode);
     }

     
     if(accMainVoucherSchema.getGenerateWay().equals("1"))
       blAccSubVoucher.voucherComp("101");
     
     if(accMainVoucherSchema.getGenerateWay().equals("0"))
        blAccSubVoucher.voucherCompByRemark();
     blAccSubVoucher.voucherOrder();

     blAccVoucher.setBLAccSubVoucher(blAccSubVoucher);
     blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());

     return blAccVoucher;
   }


    /**
     * @author lijibin 20050617 根据收付方式获取科目代码
     * @param iPayWay
     * @return
     * @throws Exception
     */
   /*
    public AccSubVoucherSchema getBankSubVoucher(String iPayWay,AccSubVoucherSchema iAccSubVoucherSchema) throws Exception
    {
      char chPayWay = iPayWay.charAt(0);
      String itemCode = "";
      switch(chPayWay)
      {
        case '1':
          itemCode = "1001";
          if(iAccSubVoucherSchema.getCurrency().equals("CNY"))
            iAccSubVoucherSchema.setDirectionIdx("01/");
          if(iAccSubVoucherSchema.getCurrency().equals("USD"))
            iAccSubVoucherSchema.setDirectionIdx("02/");
          if(iAccSubVoucherSchema.getCurrency().equals("HKD"))
            iAccSubVoucherSchema.setDirectionIdx("03/");
          break;
        case '2':
          itemCode = "1002";
          iAccSubVoucherSchema.setDirectionIdx(iAccSubVoucherSchema.getF09() +
                                               "/" + iAccSubVoucherSchema.getF25() +
                                               "/" + iAccSubVoucherSchema.getF29() +
                                               "/");
          break;
        case '3':
          break;
        case '4':
          
          if(iPayWay.equals("421")){
            itemCode = "2149";
            iAccSubVoucherSchema.setDirectionIdx("12/"); 
          }else{
            itemCode = "2121";
            iAccSubVoucherSchema.setDirectionIdx("01/");
          }
          
          break;
        case '5':
          itemCode = "2155";
          if(iPayWay.equals("511"))
          {
            iAccSubVoucherSchema.setDirectionIdx("03/"); 
          }else if(iPayWay.equals("521"))
          {
            iAccSubVoucherSchema.setDirectionIdx("04/"); 
          }else
          {
            iAccSubVoucherSchema.setDirectionIdx("05/");
          }
          break;
        case '6':
          break;
        case '7':
          itemCode = "2123";
        default:
          break;
      }
      iAccSubVoucherSchema.setItemCode(itemCode);
      return iAccSubVoucherSchema;
    }
*/
    
    public AccSubVoucherSchema getBankSubVoucher(String iPayWay,AccSubVoucherSchema iAccSubVoucherSchema) throws Exception
    {
      char chPayWay = iPayWay.charAt(0);
      String itemCode = "";
      switch(chPayWay)
      {
        case '1':
          itemCode = "1001";
          if(iAccSubVoucherSchema.getCurrency().equals("CNY"))
            iAccSubVoucherSchema.setDirectionIdx("01/");
          if(iAccSubVoucherSchema.getCurrency().equals("USD"))
            iAccSubVoucherSchema.setDirectionIdx("02/");
          if(iAccSubVoucherSchema.getCurrency().equals("HKD"))
            iAccSubVoucherSchema.setDirectionIdx("03/");
          break;
        case '2':
          itemCode = "1002";
          iAccSubVoucherSchema.setDirectionIdx(iAccSubVoucherSchema.getF09() +
                                               "/" + iAccSubVoucherSchema.getF25() +
                                               "/" + iAccSubVoucherSchema.getF29() +
                                               "/");
          break;
        case '3':
          break;
        case '4':
          
          if(iPayWay.equals("421")){
            itemCode = "2205";
            iAccSubVoucherSchema.setDirectionIdx("01/"); 
          }else{
            itemCode = "2203";
            iAccSubVoucherSchema.setDirectionIdx("01/");
          }
          
          break;
        case '5':
          itemCode = "3001";
          if(iPayWay.equals("511"))
          {
            iAccSubVoucherSchema.setDirectionIdx("02/03/"); 
          }else if(iPayWay.equals("521"))
          {
            iAccSubVoucherSchema.setDirectionIdx("02/04/"); 
          }else 
          {
            iAccSubVoucherSchema.setDirectionIdx("05/");
          }
          break;
        case '6':
          break;
        case '7':
          itemCode = "2205";
        default:
          break;
      }
      iAccSubVoucherSchema.setItemCode(itemCode);
      return iAccSubVoucherSchema;
    }    
    
    
    /**
     * 根据收付方式获取科目（从AccItemDesc表中）SangMingqian 20051215
     * @param iPayWay 收付方式
     * @param iAccSubVoucherSchema
     * @return
     * @throws Exception
     */
    public AccSubVoucherSchema getBankSubVoucherNew(String iPayWay,AccSubVoucherSchema iAccSubVoucherSchema) throws Exception
    {
      String itemCode = "";
      
      
      BLAccItemDesc blAccItemDesc = new BLAccItemDesc();
      String strWherePart = " ColCode='PayWay' AND ColValue='" + iPayWay + "'";
      blAccItemDesc.query(strWherePart);
      if(blAccItemDesc.getSize()==0)
        throw new UserException( -98, -1167, "BLPrpJpayRefMain.getSubVoucher",
                                "没有找到收付方式" + iPayWay + "对应的科目代码");
      itemCode = blAccItemDesc.getArr(0).getItemCode();
      /*
      if(itemCode.equals("1001")){
          if(iAccSubVoucherSchema.getCurrency().equals("CNY"))
            iAccSubVoucherSchema.setDirectionIdx("01/");
          if(iAccSubVoucherSchema.getCurrency().equals("USD"))
            iAccSubVoucherSchema.setDirectionIdx("02/");
          if(iAccSubVoucherSchema.getCurrency().equals("HKD"))
            iAccSubVoucherSchema.setDirectionIdx("03/");
      }else if(itemCode.equals("1002")){
          iAccSubVoucherSchema.setDirectionIdx(iAccSubVoucherSchema.getF09() +
                                               "/" + iAccSubVoucherSchema.getF25() +
                                               "/" + iAccSubVoucherSchema.getF29() +
                                               "/");
      }
      */
      if(itemCode.equals("1001")){
          if(blPrpJpayRefRec.getArr(0).getCertiType().equals("P")||
             blPrpJpayRefRec.getArr(0).getCertiType().equals("E")||
             blPrpJpayRefRec.getArr(0).getCertiType().equals("R"))
            iAccSubVoucherSchema.setDirectionIdx("02/");
          
          if(blPrpJpayRefRec.getArr(0).getCertiType().equals("C")||
             blPrpJpayRefRec.getArr(0).getCertiType().equals("Y")||
             blPrpJpayRefRec.getArr(0).getCertiType().equals("Z")||
             blPrpJpayRefRec.getArr(0).getCertiType().equals("F"))
            iAccSubVoucherSchema.setDirectionIdx("03/");
          
          if(blPrpJpayRefRec.getArr(0).getCertiType().equals("S"))
            iAccSubVoucherSchema.setDirectionIdx("01/");
          
          if(blPrpJpayRefRec.getArr(0).getCertiType().equals("D"))
              iAccSubVoucherSchema.setDirectionIdx("01/");
      }else if(itemCode.equals("1002")){
          iAccSubVoucherSchema.setDirectionIdx(iAccSubVoucherSchema.getF09() +
                                               "/" + iAccSubVoucherSchema.getF25() +
                                               "/" + iAccSubVoucherSchema.getF29() +
                                               "/");
      }     
      iAccSubVoucherSchema.setItemCode(itemCode);
      return iAccSubVoucherSchema;
    }


    /**
     * @author lijibin 20050617 根据业务收付类型获取业务科目
     * @param iPayWay
     * @return
     * @throws Exception
     */
    public AccSubVoucherSchema getSubVoucher(String iPayRefReason,AccSubVoucherSchema iAccSubVoucherSchema) throws Exception
    {
      String itemCode = "";
      BLAccItemDesc blAccItemDesc = new BLAccItemDesc();
      String strWherePart = " ColCode='ReceiptType' AND ColValue='"+iPayRefReason+"'";
      blAccItemDesc.query(strWherePart);
      if(blAccItemDesc.getSize()==0)
        throw new UserException( -98, -1167, "BLPrpJpayRefMain.getSubVoucher",
                                "没有找到收付原因" + iPayRefReason + "对应的科目代码");
      
      if(iPayRefReason.equals("R82")||
         iPayRefReason.equals("P82")||
         iPayRefReason.equals("P42"))
      {   	  
    	  iAccSubVoucherSchema.setItemCode("3001");
          if(iAccSubVoucherSchema.getCenterCode().substring(2).equals("000000"))
          {
        	  iAccSubVoucherSchema.setDirectionIdx("01/03/");
          }
          else
          {
        	  iAccSubVoucherSchema.setDirectionIdx("02/03/");     
          }
    	  
      }
      else  if(iPayRefReason.equals("P92")||
    	       iPayRefReason.equals("F60")||
    	       iPayRefReason.equals("F61")||
    	       iPayRefReason.equals("F62")||
    	       iPayRefReason.equals("F63")||
    	       iPayRefReason.equals("F64")||
    	       iPayRefReason.equals("F65")||
    	       iPayRefReason.equals("F66")||
    	       iPayRefReason.equals("F67")||
    	       iPayRefReason.equals("F68")||
    	       iPayRefReason.equals("F69"))
      {
    	  iAccSubVoucherSchema.setItemCode("3001");
          if(iAccSubVoucherSchema.getCenterCode().substring(2).equals("000000"))
          {
        	  iAccSubVoucherSchema.setDirectionIdx("01/04/");
          }
          else
          {
        	  iAccSubVoucherSchema.setDirectionIdx("02/04/");     
          }    	     	  
      }
      else
      {   	       
      itemCode = blAccItemDesc.getArr(0).getItemCode();
      iAccSubVoucherSchema.setItemCode(itemCode);
      
      if(itemCode.indexOf("/")>0){
          iAccSubVoucherSchema.setItemCode(itemCode.substring(0,4));
          iAccSubVoucherSchema.setDirectionIdx(itemCode.substring(itemCode.indexOf("/")+1));
      }
      }
      return iAccSubVoucherSchema;
    }

    /**
     * @lijibin 20050617 根据收付号获取业务的归属机构
     * @param iPayRefNo
     * @return
     * @throws Exception
     */
    public String getComCodeByPayRefNo(String iPayRefNo) throws Exception
    {
      String strComCode = "";
      BLPrpJpayRefRec blPrpJpayRefRecTmp = new BLPrpJpayRefRec();
      
      blPrpJpayRefRecTmp.query(" PayRefNo='"+iPayRefNo+"'",0);
      if(blPrpJpayRefRecTmp.getSize()==0)
        throw new UserException( -98, -1167, "BLPrpJpayRefMain.getComCodeByPayRefNo",
                                "没有该收付单号的业务信息："+iPayRefNo);

      strComCode = blPrpJpayRefRecTmp.getArr(0).getComCode();
      blPrpJpayRefRecTmp.initArr();
      return strComCode;
    }

    
    /**
	 * @yanglei 20080117 根据实际收付号获取业务的归属机构
	 * @param String iRealPayRefNo
	 * @return String strComCode
	 * @throws Exception
	 */
	public String getComCodeByRealPayRefNo(String iRealPayRefNo) throws Exception {
		String strComCode = "";
		BLPrpJpayRefRec blPrpJpayRefRecTmp = new BLPrpJpayRefRec();
		blPrpJpayRefRecTmp.query(" RealPayRefNo='" + iRealPayRefNo + "'", 0);
		if (blPrpJpayRefRecTmp.getSize() == 0)
			throw new UserException(-98, -1167, "BLPrpJpayRefMain.getComCodeByPayRefNo",
					"没有该实际收付号的业务信息：" + iRealPayRefNo);

		strComCode = blPrpJpayRefRecTmp.getArr(0).getComCode();
		blPrpJpayRefRecTmp.initArr();
		return strComCode;
	}

	/**
	 * @yanglei 20080117 根据收付号获取业务的归属人员
	 * @param String iPayRefNo
	 * @return String strHandler1Code
	 * @throws Exception
	 */
	public String getHandler1CodeByPayRefNo(String iPayRefNo) throws Exception {
		String strHandler1Code = "";
		BLPrpJpayRefRec blPrpJpayRefRecTmp = new BLPrpJpayRefRec();
		blPrpJpayRefRecTmp.query(" PayRefNo='" + iPayRefNo + "'", 0);
		if (blPrpJpayRefRecTmp.getSize() == 0)
			throw new UserException(-98, -1167, "BLPrpJpayRefMain.getComCodeByPayRefNo",
					"没有该收付号的业务信息：" + iPayRefNo);

		strHandler1Code = blPrpJpayRefRecTmp.getArr(0).getHandler1Code();
		blPrpJpayRefRecTmp.initArr();
		return strHandler1Code;
	}

	/**
	 * @yanglei 20080129 根据收付号获取业务的归属人员
	 * @param String iRealPayRefNo
	 * @return String strHandler1Code
	 * @throws Exception
	 */
	public String getHandler1CodeByRealPayRefNo(String iRealPayRefNo) throws Exception {
		String strHandler1Code = "";
		BLPrpJpayRefRec blPrpJpayRefRecTmp = new BLPrpJpayRefRec();
		blPrpJpayRefRecTmp.query(" RealPayRefNo='" + iRealPayRefNo + "'", 0);
		if (blPrpJpayRefRecTmp.getSize() == 0)
			throw new UserException(-98, -1167, "BLPrpJpayRefMain.getComCodeByPayRefNo",
					"没有该实际收付号的业务信息：" + iRealPayRefNo);

		strHandler1Code = blPrpJpayRefRecTmp.getArr(0).getHandler1Code();
		blPrpJpayRefRecTmp.initArr();
		return strHandler1Code;
	}

    /**
	 * 按币别汇总查询实收付信息 SangMingqian 20050623
	 * @param String condition
	 * @throws Exception
	 */
    public void sumByCurrency2(String strCondition) throws Exception
    {
      
      String strSqlStatement1 = "SELECT PayRefDate,PayRefCode,Currency2,"
          + "sum(PayRefFee) as PayRefFee FROM PrpJpayRefMain WHERE "
          + " PayRefNoType IN ('1','2') AND "
          + strCondition;
      String strSqlStatement2 = "SELECT PayRefDate,PayRefCode,Currency2,"
          + "-sum(PayRefFee) as PayRefFee FROM PrpJpayRefMain WHERE "
          + " PayRefNoType IN ('3','4') AND "
          + strCondition;
      String strSqlStatement = "SELECT PayRefDate,PayRefCode,Currency2,SUM(PayRefFee) as PayRefFee "
          + " FROM ( SELECT * FROM ("
          + strSqlStatement1
          + " )UNION ("
          + strSqlStatement2
          + ") ) GROUP BY PayRefDate,PayRefCode,Currency2 "
          + " ORDER BY PayRefDate,PayRefCode,Currency2 ";
      

      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        PrpJpayRefMainSchema prpJpayRefMainSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJpayRefMainSchema = new PrpJpayRefMainSchema();
          prpJpayRefMainSchema.setPayRefDate(""+resultSet.getDate("PayRefDate"));
          prpJpayRefMainSchema.setPayRefCode(resultSet.getString("PayRefCode"));
          prpJpayRefMainSchema.setCurrency2(resultSet.getString("Currency2"));
          prpJpayRefMainSchema.setPayRefFee(""+resultSet.getDouble("payRefFee"));
          this.setArr(prpJpayRefMainSchema);
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(NamingException namingException)
      {
        dbpool.close();
        throw namingException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     *按币别汇总查询收付信息 LingHaiYang 20051223
     *@param String condition
     *@throws Exception
     */
    public void sumByCurrency2New(String strCondition) throws Exception
    {
      String strSqlStatement1 = "SELECT PrpJpayRefMain.PayRefDate,PrpJpayRefMain.PayRefCode,PrpJpayRefMain.Currency2,"
          + " sum(PrpJpayRefDetail.PayRefFee) as PayRefFee FROM PrpJpayRefMain,PrpJpayRefDetail WHERE "
          + " PrpJpayRefMain.PayRefNo=PrpJpayRefDetail.PayRefNo  "
          + " AND PayRefNoType IN ('1','2') AND "
          + " PrpJpayRefMain.SerialNo='1' AND "
          + strCondition
          + " GROUP BY PrpJpayRefMain.PayRefDate,PrpJpayRefMain.PayRefCode,PrpJpayRefMain.Currency2 ";
     String strSqlStatement2 = "SELECT PrpJpayRefMain.PayRefDate,PrpJpayRefMain.PayRefCode,PrpJpayRefMain.Currency2,"
          + "-sum(PrpJpayRefDetail.PayRefFee) as PayRefFee FROM PrpJpayRefMain,PrpJpayRefDetail WHERE "
          + " PrpJpayRefMain.PayRefNo=PrpJpayRefDetail.PayRefNo  "
          + " AND PayRefNoType IN ('3','4') AND "
          + " PrpJpayRefMain.SerialNo='1' AND "
          + strCondition
          + " GROUP BY PrpJpayRefMain.PayRefDate,PrpJpayRefMain.PayRefCode,PrpJpayRefMain.Currency2 ";
      String strSqlStatement = "SELECT PayRefDate,PayRefCode,Currency2,SUM(PayRefFee) as PayRefFee "
          + " FROM ( SELECT * FROM ("
          + strSqlStatement1
          + " )UNION ("
          + strSqlStatement2
          + ") ) GROUP BY PayRefDate,PayRefCode,Currency2 "
          + " ORDER BY PayRefDate,PayRefCode,Currency2 ";
      

      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        PrpJpayRefMainSchema prpJpayRefMainSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJpayRefMainSchema = new PrpJpayRefMainSchema();
          prpJpayRefMainSchema.setPayRefDate(""+resultSet.getDate("PayRefDate"));
          prpJpayRefMainSchema.setPayRefCode(resultSet.getString("PayRefCode"));
          prpJpayRefMainSchema.setCurrency2(resultSet.getString("Currency2"));
          prpJpayRefMainSchema.setPayRefFee(""+resultSet.getDouble("payRefFee"));
          this.setArr(prpJpayRefMainSchema);
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(NamingException namingException)
      {
        dbpool.close();
        throw namingException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     *收付费台帐查询 SangMingqian 20050625
     *@param String condition
     *@throws Exception
     */
    public void accountQuery(String strCondition) throws Exception
    {
      
      String strSQL1 = "SELECT PrpJpayRefMain.CenterCode,PrpJpayRefDetail.PayRefDate,PrpJpayRefMain.PayRefCode,PrpJpayRefDetail.Currency2,PrpJpayRefDetail.PayWay,PrpJpayRefDetail.BankCode,PrpJpayRefDetail.AccountNo,"
          + "SUM(PrpJpayRefDetail.PayRefFee) AS PayRefFee FROM PrpJpayRefMain,PrpJpayRefDetail "
          + "WHERE PrpJpayRefMain.PayRefNo=PrpJpayRefDetail.PayRefNo AND PrpJpayRefMain.SerialNo='1' AND "
          + " PayRefNoType IN ('1','2') AND "
          + strCondition
          + " GROUP BY PrpJpayRefMain.CenterCode,PrpJpayRefDetail.PayRefDate,PrpJpayRefMain.PayRefCode,PrpJpayRefDetail.Currency2,PrpJpayRefDetail.PayWay,PrpJpayRefDetail.BankCode,PrpJpayRefDetail.AccountNo ";
      String strSQL2 = "SELECT PrpJpayRefMain.CenterCode,PrpJpayRefDetail.PayRefDate,PrpJpayRefMain.PayRefCode,PrpJpayRefDetail.Currency2,PrpJpayRefDetail.PayWay,PrpJpayRefDetail.BankCode,PrpJpayRefDetail.AccountNo,"
          + "-SUM(PrpJpayRefDetail.PayRefFee) AS PayRefFee FROM PrpJpayRefMain,PrpJpayRefDetail "
          + "WHERE PrpJpayRefMain.PayRefNo=PrpJpayRefDetail.PayRefNo AND PrpJpayRefMain.SerialNo='1' AND "
          + " PayRefNoType IN ('3','4') AND "
          + strCondition
          + " GROUP BY PrpJpayRefMain.CenterCode,PrpJpayRefDetail.PayRefDate,PrpJpayRefMain.PayRefCode,PrpJpayRefDetail.Currency2,PrpJpayRefDetail.PayWay,PrpJpayRefDetail.BankCode,PrpJpayRefDetail.AccountNo ";
      String strSQL = "SELECT CenterCode,PayRefDate,PayRefCode,Currency2,PayWay,BankCode,AccountNo,SUM(PayRefFee) AS PayRefFee "
                      + " FROM (SELECT * FROM ("
                      + strSQL1
                      + " ) UNION ( "
                      + strSQL2
                      + " ) )"
                      + " GROUP BY CenterCode,PayRefDate,PayRefCode,Currency2,PayWay,BankCode,AccountNo "
                      + " ORDER BY PayRefDate,PayRefCode,Currency2,PayWay,BankCode,AccountNo ";
      

      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        PrpJpayRefMainSchema prpJpayRefMainSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
          prpJpayRefMainSchema = new PrpJpayRefMainSchema();
          prpJpayRefMainSchema.setPayRefDate(""+resultSet.getDate("PayRefDate"));
          prpJpayRefMainSchema.setPayRefCode(resultSet.getString("PayRefCode"));
          prpJpayRefMainSchema.setCurrency2(resultSet.getString("currency2"));
          prpJpayRefMainSchema.setAccBookType(resultSet.getString("PayWay"));
          prpJpayRefMainSchema.setAccBookCode(resultSet.getString("BankCode"));
          prpJpayRefMainSchema.setCenterCode(resultSet.getString("CenterCode"));
          prpJpayRefMainSchema.setBranchCode(resultSet.getString("AccountNo"));
          prpJpayRefMainSchema.setPayRefFee(""+resultSet.getDouble("payRefFee"));
          this.setArr(prpJpayRefMainSchema);
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(NamingException namingException)
      {
        dbpool.close();
        throw namingException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }
    
    /**
     *收付费台帐查询 zhanglingjian 20070822
     *@param String condition
     *@throws Exception
     */
    public void accountQueryNew(String strCondition) throws Exception
    {
      
      String strSQL1 = "SELECT PrpJpayRefMain.CenterCode,PrpJpayRefDetail.PayRefDate,PrpJpayRefMain.PayRefCode,PrpJpayRefDetail.Currency2,PrpJpayRefDetail.PayWay,PrpJpayRefDetail.BankCode,PrpJpayRefDetail.AccountNo,"
          
          + "sum(decode(PrpJpayRefMain.PayRefNoType,'1',1,(decode(PrpJpayRefMain.PayRefNoType,'2',1,-1))*PrpJpayRefDetail.PayRefFee)) as PayRefFee"
    	  + " FROM PrpJpayRefMain,PrpJpayRefDetail "
          + "WHERE PrpJpayRefMain.PayRefNo=PrpJpayRefDetail.PayRefNo AND PrpJpayRefMain.SerialNo='1' AND "
          + strCondition
          + " GROUP BY PrpJpayRefMain.CenterCode,PrpJpayRefDetail.PayRefDate,PrpJpayRefMain.PayRefCode,PrpJpayRefDetail.Currency2,PrpJpayRefDetail.PayWay,PrpJpayRefDetail.BankCode,PrpJpayRefDetail.AccountNo ";
      String strSQL = "SELECT * "
                      + " FROM ("
                      + strSQL1
                      + ")"
                      + " WHERE rownum<= "+Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim());
      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        PrpJpayRefMainSchema prpJpayRefMainSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
          prpJpayRefMainSchema = new PrpJpayRefMainSchema();
          prpJpayRefMainSchema.setPayRefDate(""+resultSet.getDate("PayRefDate"));
          prpJpayRefMainSchema.setPayRefCode(resultSet.getString("PayRefCode"));
          prpJpayRefMainSchema.setCurrency2(resultSet.getString("currency2"));
          prpJpayRefMainSchema.setAccBookType(resultSet.getString("PayWay"));
          prpJpayRefMainSchema.setAccBookCode(resultSet.getString("BankCode"));
          prpJpayRefMainSchema.setCenterCode(resultSet.getString("CenterCode"));
          prpJpayRefMainSchema.setBranchCode(resultSet.getString("AccountNo"));
          prpJpayRefMainSchema.setPayRefFee(""+resultSet.getDouble("payRefFee"));
          this.setArr(prpJpayRefMainSchema);
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(NamingException namingException)
      {
        dbpool.close();
        throw namingException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     * 获取PayRefTimes SangMingqian 20051118
     * @param iPayRefNo 打包号
     * @param iPayRefTimesBegin 10原凭证数据 20对冲凭证数据
     * @return
     * @throws Exception
     */
    public String getPayRefTimes(String iPayRefNo,String iPayRefTimesBegin) throws Exception
    {
      String strPayRefTimes = "";
      String strSQL = "";
      strSQL = "select max(a.payreftimes) "
               + " from prpjpayrefrec a,prpjpayrefrec b "
               + " where a.certitype=b.certitype and a.certino=b.certino "
               + " and a.serialno=b.serialno and a.payrefreason=b.payrefreason "
               + " and b.payrefno='" + iPayRefNo
               + "' and a.payreftimes like '" + iPayRefTimesBegin + "%'";
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        ResultSet resultSet = dbpool.query(strSQL);
        int intPayRefTimes = 0;
        if(resultSet.next())
        {
            if(resultSet.getString(1)==null){
                strPayRefTimes = iPayRefTimesBegin + "01";
            }else{
                intPayRefTimes = Integer.parseInt(resultSet.getString(1));
                intPayRefTimes = intPayRefTimes + 1;
                strPayRefTimes = String.valueOf(intPayRefTimes);
            }
        }
        resultSet.close();
        dbpool.close();
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
      return strPayRefTimes;
    }

    /**
     * 获取PayRefTimes lijibin 20060603
     * @param iPayRefNo 打包号
     * @param iPayRefTimesBegin 10原凭证数据 20对冲凭证数据
     * @return
     * @throws Exception
     */
    public String getPayRefTimes(DbPool dbpool,PrpJpayRefRecSchema iSchema,String iPayRefTimesBegin) throws Exception
    {
      String strPayRefTimes = "";
      String strSQL = "";
      strSQL = "SELECT MAX(PayRefTimes) FROM PrpJpayRefRec"
          + " WHERE CertiType='" + iSchema.getCertiType() + "' AND CertiNo='"
          + iSchema.getCertiNo() + "' AND SerialNo='" + iSchema.getSerialNo()
          + "' AND PayRefReason='" + iSchema.getPayRefReason() + "'"
          + " and PayRefTimes LIKE '" + iPayRefTimesBegin + "%'";
      ResultSet resultSet = dbpool.query(strSQL);
      int intPayRefTimes = 0;
      if(resultSet.next())
      {
        if(resultSet.getString(1)==null){
          strPayRefTimes = iPayRefTimesBegin + "01";
        }else{
          intPayRefTimes = Integer.parseInt(resultSet.getString(1));
          intPayRefTimes = intPayRefTimes + 1;
          strPayRefTimes = String.valueOf(intPayRefTimes);
        }
      }
      resultSet.close();
      return strPayRefTimes;
    }

    /**
     * 获取手续费的PayRefTimes 
     * @param iPayRefNo 打包号
     * @param iPayRefTimesBegin 10原凭证数据 20对冲凭证数据
     * @return
     * @throws Exception
     */
    public String getPayRefTimesNew(DbPool dbpool,PrpJpayCommissionSchema iSchema,String iPayRefTimesBegin) throws Exception
    {
      String strPayRefTimes = "";
      String strSQL = "";
      strSQL = "SELECT MAX(PayRefTimes) FROM PrpJpayCommission"
          + " WHERE CertiType='" + iSchema.getCertiType() + "' AND CertiNo='"
          + iSchema.getCertiNo() + "' AND SerialNo='" + iSchema.getSerialNo()
          + "' AND PayRefReason='" + iSchema.getPayRefReason() + "'"
          + " and PayRefTimes LIKE '" + iPayRefTimesBegin + "%'";
      ResultSet resultSet = dbpool.query(strSQL);
      int intPayRefTimes = 0;
      if(resultSet.next())
      {
        if(resultSet.getString(1)==null){
          strPayRefTimes = iPayRefTimesBegin + "01";
        }else{
          intPayRefTimes = Integer.parseInt(resultSet.getString(1));
          intPayRefTimes = intPayRefTimes + 1;
          strPayRefTimes = String.valueOf(intPayRefTimes);
        }
      }
      resultSet.close();
      return strPayRefTimes;
    }

    
    /**
     * 收付确认凭证预览 SangMingqian 20060426
     * @param strPayRefDate 收付日期
     * @param strPayRefCode 收付员
     * @param iAuxNumber 附件张数
     * @return
     * @throws UserException
     * @throws Exception
     */
    public BLAccVoucher payRefToVoucher(String strPayRefDate,String strPayRefCode,String iAuxNumber) throws UserException,Exception
	{
	    String strPayRefNoType = "";
    	if(this.blPrpJpayRefRec.getArr(0).getCertiType().equals("P")
	        ||this.blPrpJpayRefRec.getArr(0).getCertiType().equals("E")
			||this.blPrpJpayRefRec.getArr(0).getCertiType().equals("J")){
	        strPayRefNoType = "2"; 
	    }else if(this.blPrpJpayRefRec.getArr(0).getCertiType().equals("S")){
	    	strPayRefNoType = "3"; 
	    }else if(this.blPrpJpayRefRec.getArr(0).getCertiType().equals("C")){
	    	strPayRefNoType = "4"; 
	    }else if(this.blPrpJpayRefRec.getArr(0).getCertiType().equals("R")){
	    	strPayRefNoType = "6"; 
	    }else if(this.blPrpJpayRefRec.getArr(0).getCertiType().equals("Z")){
            strPayRefNoType = "7"; 
	    }else if(this.blPrpJpayRefRec.getArr(0).getCertiType().equals("F")){
            strPayRefNoType = "5"; 
            
	    } 
    	
	    else if(this.blPrpJpayRefRec.getArr(0).getCertiType().equals("D")&&this.blPrpJpayRefRec.getArr(0).getPayRefReason().equals("P6B")){
            strPayRefNoType = "8"; 
	    }
    	
	    else if(this.blPrpJpayRefRec.getArr(0).getCertiType().equals("D")&&this.blPrpJpayRefRec.getArr(0).getPayRefReason().equals("P6C")){
            strPayRefNoType = "9"; 
	    }
    	BLAccVoucher blAccVoucher = new BLAccVoucher();
	    
	    String strComCode = this.blPrpJpayRefRec.getArr(0).getComCode();
	    
	    if(this.blPrpJpayRefRec.getArr(0).getComCode().equals(""))
	    {
	    	throw new UserException( -98, -1167, "BLPrpJpayRefMain.payRefToVoucher","PrpJpayRefRec表归属机构为空！");
	    }	    
	    BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
	    String strCenterCode = blPrpDcompany.getCenterCode(strComCode);
	    String strBranchCode = blPrpDcompany.getBranchCode(strComCode);
	    if(strBranchCode.equals("")){
		    strBranchCode = strCenterCode;
	    }
	    String strAccBookType = SysConst.getProperty("ACCBOOKTYPE");
	    String strAccBookCode = "";
	    if(strAccBookType.equals("02")){
		    strAccBookCode = SysConst.getProperty("ACCBOOKCODE");
	    }
	    
	    BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
	    String strSQL = "";
	    String strYearMonth = "";
	    strSQL = " CenterCode='"+strCenterCode
	             + "' AND AccBookType='"+strAccBookType
	             + "' AND AccBookCode='"+strAccBookCode
	             + "' AND AccMonthStat='3'";
	    blAccMonthTrace.query(strSQL);
	    if(blAccMonthTrace.getSize()==0){
		    throw new UserException( -98, -1167, "BLPrpJpayRefMain.genAccVoucher","核算单位" + strCenterCode + "还没有初始化");
	    }else{
		    strYearMonth = strPayRefDate.substring(0,4)+strPayRefDate.substring(5,7);
	    }

	    
	    AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
	    accMainVoucherSchema.setAccBookType(strAccBookType);
	    accMainVoucherSchema.setAccBookCode(strAccBookCode);
	    accMainVoucherSchema.setCenterCode(strCenterCode);
	    accMainVoucherSchema.setBranchCode(strBranchCode);
	    if(iAuxNumber.equals("")){
	        accMainVoucherSchema.setAuxNumber(""+this.blPrpJpayRefDetail.getSize());
	    }else{
	        accMainVoucherSchema.setAuxNumber(iAuxNumber);
	    }
	    accMainVoucherSchema.setOperatorCode(strPayRefCode);
	    accMainVoucherSchema.setOperatorBranch(strBranchCode);
	    accMainVoucherSchema.setVoucherDate(strPayRefDate);
	    accMainVoucherSchema.setVoucherType("1");
	    if(strPayRefNoType.equals("6")){
		    accMainVoucherSchema.setGenerateWay("0");
	    }else{
		    accMainVoucherSchema.setGenerateWay("1");
	    }
	    accMainVoucherSchema.setVoucherFlag("1");
	    accMainVoucherSchema.setYearMonth(strYearMonth);
	    blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

	    
	    String strRemark = "";
	    String strPreRemark = "";
	    
	    String strShipReamrk ="";
	    
	    if(strPayRefNoType.equals("1")|| strPayRefNoType.equals("2")){

		    strRemark = "XX";
	    }
	    if(strPayRefNoType.equals("3")){
		    strRemark = "手续费";
	    }
	    if(strPayRefNoType.equals("4")){
		    strRemark = "赔款";
	    }
	    if(strPayRefNoType.equals("5")){
		    strRemark = "出单费";
	    }
	    if(strPayRefNoType.equals("6"))
	    {
	        strRemark = this.getBLPrpJpayRefRec().getArr(0).getCoinsName()+"分XXXXX业务往来款";
	    }
	     if(strPayRefNoType.equals("7")){
            strRemark = "追偿款";
        }
	     
	    if(strPayRefNoType.equals("8")){
			strRemark = "垫付款";
		}
	    if(strPayRefNoType.equals("9"))
	    {
	    	strRemark = "垫付冲销款";
	    }

	    DBAccBankAccount dbAccBankAccount = new DBAccBankAccount();
	    
	    BLAccSubVoucher blAccSubVoucherZJ = new BLAccSubVoucher();
	    AccSubVoucherSchema accSubVoucherSchema = null;
	    BLPrpDcode blPrpDcode = new BLPrpDcode();
	    for(int i=0; i<this.blPrpJpayRefDetail.getSize(); i++)
	    {
	        accSubVoucherSchema = new AccSubVoucherSchema();
	        accSubVoucherSchema.setAccBookType(strAccBookType);
	        accSubVoucherSchema.setAccBookCode(strAccBookCode);
	        accSubVoucherSchema.setCenterCode(strCenterCode);
	        accSubVoucherSchema.setYearMonth(strYearMonth);
	        
            if(blPrpJpayRefRec.getArr(0).getRiskCode().equals("0507")){
                accSubVoucherSchema.setF05(blPrpJpayRefRec.getArr(0).getRiskCode()+blPrpJpayRefRec.getArr(0).getCarNatureCode());
            }else{
                accSubVoucherSchema.setF05(blPrpJpayRefRec.getArr(0).getRiskCode());                
            }
	        accSubVoucherSchema.setF22(blPrpJpayRefDetail.getArr(i).getFlag());
	        accSubVoucherSchema.setF26(strBranchCode);
	        accSubVoucherSchema.setCurrency(blPrpJpayRefDetail.getArr(i).getCurrency2());
	        accSubVoucherSchema.setF25(blPrpJpayRefDetail.getArr(i).getBankCode());
	        accSubVoucherSchema.setF27(blPrpJpayRefRec.getArr(0).getHandler1Code());
	        accSubVoucherSchema.setF28(blPrpJpayRefRec.getArr(0).getComCode());
	        
	        if(blPrpJpayRefRec.getArr(0).getComCode().equals("")||
	           accSubVoucherSchema.getF28().equals("")||
	           blPrpJpayRefRec.getArr(0).getHandler1Code().equals("")||
	           accSubVoucherSchema.getF27().equals(""))	           
	        {
	        	throw new UserException( -98, -1167, "BLPrpJpayRefMain.genAccVoucher","归属机构和业务归属人员为空！");
	        }
	        
	        if(blPrpJpayRefDetail.getArr(i).getPayWay().substring(0,1).equals("2"))
	        {
	            accSubVoucherSchema.setF29(blPrpJpayRefDetail.getArr(i).getAccountNo());
	        }
	        this.blPrpJpayRefDetail.getArr(i).setFlag("");
	        if(!accSubVoucherSchema.getF29().equals(""))
	        {
	            dbAccBankAccount = new DBAccBankAccount();
	            dbAccBankAccount.getInfo(accSubVoucherSchema.getF29(),strCenterCode);
	            accSubVoucherSchema.setF09("0"+dbAccBankAccount.getSaveNature());
	        }
	        accSubVoucherSchema.setF03(blPrpJpayRefDetail.getArr(i).getPayRefDate());
	        if(!blPrpJpayRefDetail.getArr(i).getCheckType().equals("")){
	            accSubVoucherSchema.setCheckNo(blPrpJpayRefDetail.getArr(i).getCheckType() + "/" +
                                                 blPrpJpayRefDetail.getArr(i).getCheckNo());
	        }
	        
	        double dblExchRate = 1;
	        dblExchRate = PubTools.getExchangeRate(accSubVoucherSchema.getCurrency(),
	                                               "CNY",
	                                               accMainVoucherSchema.getVoucherDate());
	        accSubVoucherSchema.setExchangeRate(""+dblExchRate);
	        String strPayWay = blPrpJpayRefDetail.getArr(i).getPayWay();
	        accSubVoucherSchema = this.getBankSubVoucherNew(strPayWay,accSubVoucherSchema);
	        if(strPayWay.substring(1,2).equals("1")&&!strPayWay.equals("615"))
	        {
	            if(strPayWay.substring(0,1).equals("1")||strPayWay.substring(0,1).equals("2")
	         	      ||strPayWay.substring(0,1).equals("5")){
	            	
	                accSubVoucherSchema.setRemark(blPrpDcode.translateCode("PayWay",strPayWay,true)+strRemark);
	            }else{
	                accSubVoucherSchema.setRemark(blPrpDcode.translateCode("PayWay",strPayWay,true));
	            }
	    	    
	    	    strShipReamrk =blPrpDcode.translateCode("PayWay",strPayWay,true);
	    	    
	            accSubVoucherSchema.setDebitSource(""+Math.abs(Double.parseDouble(blPrpJpayRefDetail.getArr(i).getPayRefFee())));
	            double dblDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
	            dblDebitDest = Str.round(Str.round(dblDebitDest,8),2);
	            accSubVoucherSchema.setDebitDest(""+Math.abs(dblDebitDest));
	        }else{
	            if(strPayWay.substring(0,1).equals("1")||strPayWay.substring(0,1).equals("2")
	         	     ||strPayWay.substring(0,1).equals("5")){
	                accSubVoucherSchema.setRemark(blPrpDcode.translateCode("PayWay",strPayWay,true)+strRemark);
	            }else{
	                accSubVoucherSchema.setRemark(blPrpDcode.translateCode("PayWay",strPayWay,true));
	            }
	    	    
	    	    strShipReamrk =blPrpDcode.translateCode("PayWay",strPayWay,true);
	    	    
	            if(strPayWay.equals("615")){
	                accSubVoucherSchema.setCreditSource(""+Math.abs((-1)*Double.parseDouble(blPrpJpayRefDetail.getArr(i).getPayRefFee())));
	            }else{
	                accSubVoucherSchema.setCreditSource(""+Math.abs(Double.parseDouble(blPrpJpayRefDetail.getArr(i).getPayRefFee())));
	            }
	            double dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
	            dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
	            accSubVoucherSchema.setCreditDest(""+Math.abs(dblCreditDest));
	        }
	        blAccSubVoucherZJ.setArr(accSubVoucherSchema);
	        
	        /*
	        if(strPayWay.equals("616")){
		        accSubVoucherSchema = new AccSubVoucherSchema();
		        accSubVoucherSchema.setAccBookType(strAccBookType);
		        accSubVoucherSchema.setAccBookCode(strAccBookCode);
		        accSubVoucherSchema.setCenterCode(strCenterCode);
		        accSubVoucherSchema.setYearMonth(strYearMonth);
		        accSubVoucherSchema.setF05(blPrpJpayRefRec.getArr(0).getRiskCode());
		        accSubVoucherSchema.setF22(blPrpJpayRefDetail.getArr(i).getFlag());
		        accSubVoucherSchema.setF26(strBranchCode);
		        accSubVoucherSchema.setCurrency(blPrpJpayRefDetail.getArr(i).getCurrency2());
		        accSubVoucherSchema.setF25(blPrpJpayRefDetail.getArr(i).getBankCode());
		        accSubVoucherSchema.setF27(blPrpJpayRefRec.getArr(0).getHandler1Code());
		        accSubVoucherSchema.setF28(blPrpJpayRefRec.getArr(0).getComCode());
		        accSubVoucherSchema.setF29(blPrpJpayRefDetail.getArr(i).getAccountNo());
		        if(!accSubVoucherSchema.getF29().equals(""))
		        {
		            dbAccBankAccount = new DBAccBankAccount();
		            dbAccBankAccount.getInfo(accSubVoucherSchema.getF29(),strCenterCode);
		            accSubVoucherSchema.setF09("0"+dbAccBankAccount.getSaveNature());
		        }
		        accSubVoucherSchema.setF03(blPrpJpayRefDetail.getArr(i).getPayRefDate());
		        if(!blPrpJpayRefDetail.getArr(i).getCheckType().equals("")){
		            accSubVoucherSchema.setCheckNo(blPrpJpayRefDetail.getArr(i).getCheckType() + "/" +
	                                                 blPrpJpayRefDetail.getArr(i).getCheckNo());
		        }
		        
		        dblExchRate = PubTools.getExchangeRate(accSubVoucherSchema.getCurrency(),
		                                               "CNY",
		                                               accMainVoucherSchema.getVoucherDate());
		        accSubVoucherSchema.setExchangeRate(""+dblExchRate);
		        accSubVoucherSchema = this.getBankSubVoucherNew("212",accSubVoucherSchema);
		        accSubVoucherSchema.setRemark(blPrpDcode.translateCode("PayWay",strPayWay,true));
		        accSubVoucherSchema.setCreditSource(""+Math.abs(Double.parseDouble(blPrpJpayRefDetail.getArr(i).getPayRefFee())));
		        double dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
		        dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
		        accSubVoucherSchema.setCreditDest(""+Math.abs(dblCreditDest));
		        blAccSubVoucherZJ.setArr(accSubVoucherSchema);
	        }
	        */
	        if(!strPayWay.equals("420")&&!strPayWay.equals("616")){
	       	    strPreRemark = accSubVoucherSchema.getRemark();
	        }
	    }
	    strRemark = accSubVoucherSchema.getRemark();
	    
	    String strItemCode = "";
	    BLAccSubVoucher blAccSubVoucher = new BLAccSubVoucher();
	    
	    
	    HashSet YAB0HashSet = new HashSet();
        
	    for(int i=0; i<this.blPrpJpayRefRec.getSize(); i++)
	    {
	        double dblSource = Double.parseDouble(Str.chgStrZero(blPrpJpayRefRec.getArr(i).getPlanFee()));
	        
	        if((blPrpJpayRefRec.getArr(i).getCoinsFlag().equals("1")||
	              blPrpJpayRefRec.getArr(i).getCoinsFlag().equals("3"))&&
	              blPrpJpayRefRec.getArr(i).getCoinsType().equals("1"))
	        {
	            String strWherePart = "";
	            String strPayRefReasonTmp = "";
	            BLPrpJplanFee blPrpJplanFeeCoins = new BLPrpJplanFee();
	            if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P")||
	                 blPrpJpayRefRec.getArr(i).getCertiType().equals("E"))
	            {
	                if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P40")){
	   	                strPayRefReasonTmp = "('P41','P42')";
	                }else{
	   	                strPayRefReasonTmp = "('R81','R82','P81','P82')";
	                }
	            }else if(blPrpJpayRefRec.getArr(i).getCertiType().equals("S")){
	 	            strPayRefReasonTmp = "('P91','P92')";
	            }else if(blPrpJpayRefRec.getArr(i).getCertiType().equals("C"))
	            {
	                strPayRefReasonTmp = "('F"+ blPrpJpayRefRec.getArr(i).getPayRefReason().substring(1) +"',"
	                                       + "'S"+ blPrpJpayRefRec.getArr(i).getPayRefReason().substring(1) +"')";
	            }else{
	 	            strPayRefReasonTmp = "('')";
	            }

	            strWherePart = " CertiNo='"+ blPrpJpayRefRec.getArr(i).getCertiNo() +"'"
	                           + " And PayRefReason In " + strPayRefReasonTmp
	                           + " And PayNo=" + blPrpJpayRefRec.getArr(i).getPayNo();
	            blPrpJplanFeeCoins.query(strWherePart,0);
	            for(int j=0;j<blPrpJplanFeeCoins.getSize();j++)
	            {
	                
	                dblSource = dblSource + Double.parseDouble(Str.chgStrZero(blPrpJplanFeeCoins.getArr(j).getPlanFee()));
	                dblSource = Str.round(dblSource,2);

	                
	                accSubVoucherSchema = new AccSubVoucherSchema();
	                accSubVoucherSchema.setAccBookType(strAccBookType);
	                accSubVoucherSchema.setAccBookCode(strAccBookCode);
	                accSubVoucherSchema.setCenterCode(strCenterCode);
	                accSubVoucherSchema.setF26(strBranchCode);
	                accSubVoucherSchema.setYearMonth(strYearMonth);
	                accSubVoucherSchema.setCurrency(blPrpJpayRefRec.getArr(i).getCurrency1());
	                accSubVoucherSchema.setF01(blPrpJpayRefRec.getArr(i).getClassCode());
                    if(blPrpJpayRefRec.getArr(i).getRiskCode().equals("0507")){
                        accSubVoucherSchema.setF05(blPrpJpayRefRec.getArr(i).getRiskCode()+blPrpJpayRefRec.getArr(i).getCarNatureCode());                        
                    }else{
                        accSubVoucherSchema.setF05(blPrpJpayRefRec.getArr(i).getRiskCode());                        
                    }
	                accSubVoucherSchema.setF28(blPrpJpayRefRec.getArr(i).getComCode());	                
	                accSubVoucherSchema.setF27(blPrpJpayRefRec.getArr(i).getHandler1Code());
	    	        
	    	        if(blPrpJpayRefRec.getArr(i).getComCode().equals("")||
	    	           accSubVoucherSchema.getF28().equals("")||
	    	           blPrpJpayRefRec.getArr(i).getHandler1Code().equals("")||
	    	           accSubVoucherSchema.getF27().equals(""))	           
	    	        {
	    	        	throw new UserException( -98, -1167, "BLPrpJpayRefMain.genAccVoucher","归属机构和业务归属人员为空！");
	    	        }
	    	        
	                if(blPrpJplanFeeCoins.getArr(j).getCoinsType().equals("2"))
	                {
	                    accSubVoucherSchema.setF22("00000000");
	                    accSubVoucherSchema.setRemark("从联方");
	                }else{
	                    accSubVoucherSchema.setF22(blPrpJplanFeeCoins.getArr(j).getCoinsCode());
	                    DBPrpDreins dbPrpDreins = new DBPrpDreins();
	                    int intResult = dbPrpDreins.getInfo(blPrpJplanFeeCoins.getArr(j).getCoinsCode());
	                    if(intResult==0){
	             	        accSubVoucherSchema.setRemark("应付" + dbPrpDreins.getShortName() + "共XXXXXXX");
	                    }else{
	                        throw new UserException( -98, -1167, "BLPrpJpayRefMain.genAccVoucher",
	                                                   "没有" + blPrpJplanFeeCoins.getArr(j).getCoinsCode() + "的信息");
	                    }
	                }
	                
	                double dblExchRate = this.getExchRate(blPrpJpayRefRec.getArr(i),
	                                                         blPrpJplanFeeCoins.getArr(j),
	                                                         accMainVoucherSchema.getVoucherDate());
	                accSubVoucherSchema.setExchangeRate(""+dblExchRate);
	                String strPayRefReason = blPrpJpayRefRec.getArr(i).getPayRefReason();
	                accSubVoucherSchema = this.getSubVoucher(strPayRefReason,accSubVoucherSchema);

	                if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P")||
	                       blPrpJpayRefRec.getArr(i).getCertiType().equals("E")){
	                    if(blPrpJplanFeeCoins.getArr(j).getCoinsType().equals("2")){
	                        accSubVoucherSchema.setF02("03");
	                        
	                        
	                        
	                        
	                        accSubVoucherSchema.setItemCode("3001");
	                        if(accSubVoucherSchema.getCenterCode().substring(2).equals("000000"))
	                        {
	                        	accSubVoucherSchema.setDirectionIdx("01/03/");
	                        }
	                        else
	                        {
	                        	accSubVoucherSchema.setDirectionIdx("02/03/");     
	                        }
	                        accSubVoucherSchema.setRemark("从联方");
	                    }else if(blPrpJplanFeeCoins.getArr(j).getCoinsType().equals("3")){
	                        accSubVoucherSchema.setF21("05");
	                        
	                        
	                        accSubVoucherSchema.setItemCode("2241");
	                        accSubVoucherSchema.setDirectionIdx("01/");	                        
	                        DBPrpDreins dbPrpDreins = new DBPrpDreins();
	                        int intResult = dbPrpDreins.getInfo(accSubVoucherSchema.getF22());
	                        if(intResult==0){
	               	            accSubVoucherSchema.setRemark("应付" + dbPrpDreins.getShortName() + "共XXXXXXX");
	                        }else{
	                            throw new UserException( -98, -1167, "BLPrpJpayRefMain.genAccVoucher",
	                                                       "没有" + accSubVoucherSchema.getF22() + "的信息");
	                        }
	                    }
	                }
	                
	                if(blPrpJpayRefRec.getArr(i).getCertiType().equals("S")){
	                    if(blPrpJplanFeeCoins.getArr(j).getCoinsType().equals("2")){
	                        accSubVoucherSchema.setF02("04");
	                        
	                        
	                        
	                        accSubVoucherSchema.setItemCode("3001");
	                        if(accSubVoucherSchema.getCenterCode().substring(2).equals("000000"))
	                        {
	                        	accSubVoucherSchema.setDirectionIdx("01/04/");
	                        }
	                        else
	                        {
	                        	accSubVoucherSchema.setDirectionIdx("02/04/");     
	                        }	                        
	                    }else if(blPrpJplanFeeCoins.getArr(j).getCoinsType().equals("3")){
	                        
	                        
	                        
	                        
	                        accSubVoucherSchema.setF16("02");
	                        accSubVoucherSchema.setF21("07");
	                        accSubVoucherSchema.setItemCode("1221");
	                        accSubVoucherSchema.setDirectionIdx("07/02/");                        
	                    }
	                }
	                
	                
	                if(blPrpJpayRefRec.getArr(i).getCertiType().equals("Y")||
	                     blPrpJpayRefRec.getArr(i).getCertiType().equals("C")){
	                    DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
	                    dbPrpJplanFee.getInfo(blPrpJpayRefRec.getArr(i).getCertiType(),
	                                           blPrpJpayRefRec.getArr(i).getCertiNo(),
	                                           blPrpJpayRefRec.getArr(i).getSerialNo(),
	                                           blPrpJpayRefRec.getArr(i).getPayRefReason());
	                    if(blPrpJplanFeeCoins.getArr(j).getCoinsType().equals("2")){
	                        accSubVoucherSchema.setF02("04");
	                        
	                       
	                       
	                        accSubVoucherSchema.setItemCode("3001");
	                        if(accSubVoucherSchema.getCenterCode().substring(2).equals("000000"))
	                        {
	                        	accSubVoucherSchema.setDirectionIdx("01/04/");
	                        }
	                        else
	                        {
	                        	accSubVoucherSchema.setDirectionIdx("02/04/");     
	                        }	                        
	                    }else if(blPrpJplanFeeCoins.getArr(j).getCoinsType().equals("3")){
	                        
	                       
	                       
	                       
	                        accSubVoucherSchema.setF16("01");
	                        accSubVoucherSchema.setF21("07");
	                        accSubVoucherSchema.setItemCode("1221");
	                        accSubVoucherSchema.setDirectionIdx("07/01/");	                        
	                    }
	                }
	                strItemCode = accSubVoucherSchema.getItemCode();
	                
	                if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P")||
	                      blPrpJpayRefRec.getArr(i).getCertiType().equals("E")){
	                    double dblPlanFee = -1 * Double.parseDouble(Str.chgStrZero(blPrpJplanFeeCoins.getArr(j).getPlanFee()));
	                    dblPlanFee = Str.round(dblPlanFee,2);
	                    accSubVoucherSchema.setCreditSource("" + dblPlanFee);
	                    double dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
	                    dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
	                    accSubVoucherSchema.setCreditDest(""+dblCreditDest);
	                }else{
	                    double dblPlanFee = -1 * Double.parseDouble(Str.chgStrZero(blPrpJplanFeeCoins.getArr(j).getPlanFee()));
	                    dblPlanFee = Str.round(dblPlanFee,2);
	                    accSubVoucherSchema.setDebitSource("" + dblPlanFee);
	                    double dblDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
	                    dblDebitDest = Str.round(Str.round(dblDebitDest,8),2);
	                    accSubVoucherSchema.setDebitDest(""+dblDebitDest);
	                }
	                blAccSubVoucher.setArr(accSubVoucherSchema);
	            }
	        }

	        accSubVoucherSchema = new AccSubVoucherSchema();
	        accSubVoucherSchema.setAccBookType(strAccBookType);
	        accSubVoucherSchema.setAccBookCode(strAccBookCode);
	        accSubVoucherSchema.setCenterCode(strCenterCode);
	        accSubVoucherSchema.setF26(strBranchCode);
	        accSubVoucherSchema.setYearMonth(strYearMonth);
	        accSubVoucherSchema.setCurrency(blPrpJpayRefRec.getArr(i).getCurrency1());
	        accSubVoucherSchema.setF01(blPrpJpayRefRec.getArr(i).getClassCode());
            if(blPrpJpayRefRec.getArr(i).getRiskCode().equals("0507")){
                accSubVoucherSchema.setF05(blPrpJpayRefRec.getArr(i).getRiskCode()+blPrpJpayRefRec.getArr(i).getCarNatureCode());                
            }else{
                accSubVoucherSchema.setF05(blPrpJpayRefRec.getArr(i).getRiskCode());                
            }
	        accSubVoucherSchema.setF28(blPrpJpayRefRec.getArr(i).getComCode());
	        accSubVoucherSchema.setF27(blPrpJpayRefRec.getArr(i).getHandler1Code());
	        
	        if(blPrpJpayRefRec.getArr(i).getComCode().equals("")||
	           accSubVoucherSchema.getF28().equals("")||
	           blPrpJpayRefRec.getArr(i).getHandler1Code().equals("")||
	           accSubVoucherSchema.getF27().equals(""))	           
	        {
	        	throw new UserException( -98, -1167, "BLPrpJpayRefMain.genAccVoucher","归属机构和业务归属人员为空！");
	        }
	        
	        
	        
	        if(strRemark.equals("转未签单预收")||strRemark.equals("代扣银行结算费")){
	            accSubVoucherSchema.setRemark(strPreRemark);
	        }else if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("R72")){
	        	 accSubVoucherSchema.setRemark(strShipReamrk+"当年应缴车船税");
	        }else if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("R73")){
	        	 accSubVoucherSchema.setRemark(strShipReamrk+"往年补缴车船税");
	        }else if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("R74")){
	        	 accSubVoucherSchema.setRemark(strShipReamrk+"车船税滞纳金");
	        }else{
	            accSubVoucherSchema.setRemark(strRemark);
	        }
	        
	      
	        
	        double dblExchRate = this.getExchRate(blPrpJpayRefRec.getArr(i),
	                                                accMainVoucherSchema.getVoucherDate());
	        accSubVoucherSchema.setExchangeRate(""+dblExchRate);
	        String strPayRefReason = blPrpJpayRefRec.getArr(i).getPayRefReason();
	        accSubVoucherSchema = this.getSubVoucher(strPayRefReason,accSubVoucherSchema);
	        
	        if(blPrpJpayRefRec.getArr(i).getCertiType().equals("J")){
                    
                    
                    accSubVoucherSchema.setItemCode("1191");
                    accSubVoucherSchema.setDirectionIdx("08/");                    
	        }
	        
            String strYAB0 = "";
            double dblPlanFeeYAB0 = 0;
            if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P")){
                
            if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P81")){
                    accSubVoucherSchema.setF22(blPrpJpayRefRec.getArr(i).getCoinsCode());
                }
            
            if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("R72")){
	                accSubVoucherSchema.setItemCode("1221");
	                accSubVoucherSchema.setDirectionIdx("12/01/");	 
            }else if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("R73")){
                accSubVoucherSchema.setItemCode("1221");
                accSubVoucherSchema.setDirectionIdx("12/02/");	 
            }else if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("R74")){
	                accSubVoucherSchema.setItemCode("1221");
	                accSubVoucherSchema.setDirectionIdx("12/03/");	 
            }else{
            	 if(PubTools.compareDate(blPrpJpayRefRec.getArr(i).getStartDate(),strPayRefDate) > 0){
 	            	
 	                
 	                
 	                accSubVoucherSchema.setItemCode("2203");
 	                accSubVoucherSchema.setDirectionIdx("02/");	            	
                     }
 	            else if (blPrpJplanFee.checkAllowUnpayed(blPrpJpayRefRec.getArr(i).getComCode(),
                         blPrpJpayRefRec.getArr(i).getRiskCode())){
                     
 	                accSubVoucherSchema.setItemCode("6031");
 	                accSubVoucherSchema.setDirectionIdx("01/");	
                     }
 	            else{
 	            	












 	                
 	            }
 	            
 	            if(blPrpJpayRefRec.getArr(i).getRiskCode().equals("YAB0")){
 	            		

                     




















                     











                     
                     accSubVoucherSchema.setItemCode("2203");
 	                accSubVoucherSchema.setDirectionIdx("01/");
 	                
 	            }	
            }
            
        }
        
        if(blPrpJpayRefRec.getArr(i).getCertiType().equals("E")){
                
        	   
                if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P41")
                 ||blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P81")
                 ||blPrpJpayRefRec.getArr(i).getPayRefReason().equals("R81")){
                    accSubVoucherSchema.setF22(blPrpJpayRefRec.getArr(i).getCoinsCode());
                }
                
                if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("R72")){
	                accSubVoucherSchema.setItemCode("1221");
	                accSubVoucherSchema.setDirectionIdx("12/01/");	 
            }else if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("R73")){
                accSubVoucherSchema.setItemCode("1221");
                accSubVoucherSchema.setDirectionIdx("12/02/");	 
            }else if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("R74")){
	                accSubVoucherSchema.setItemCode("1221");
	                accSubVoucherSchema.setDirectionIdx("12/03/");	 
            }else{
            if(PubTools.compareDate(blPrpJpayRefRec.getArr(i).getValidDate(),strPayRefDate) > 0){
                
                
            	accSubVoucherSchema.setItemCode("2203");
                accSubVoucherSchema.setDirectionIdx("02/");
                }else if (blPrpJplanFee.checkAllowUnpayed(blPrpJpayRefRec.getArr(i).getComCode(),
                    blPrpJpayRefRec.getArr(i).getRiskCode())){
                
                
                accSubVoucherSchema.setItemCode("6031");
                accSubVoucherSchema.setDirectionIdx("01/");
            }else{
            	











            	
            }
            if(blPrpJpayRefRec.getArr(i).getRiskCode().equals("YAB0")){
            	
                
                accSubVoucherSchema.setItemCode("2203");
                accSubVoucherSchema.setDirectionIdx("01/");
            	}
             }
         }
	        
	        if(blPrpJpayRefRec.getArr(i).getCertiType().equals("S")){
	            if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P91")){
	                accSubVoucherSchema.setF22(blPrpJpayRefRec.getArr(i).getCoinsCode());
	                accSubVoucherSchema.setF16("02");
	                accSubVoucherSchema.setF21("07");	                
	                accSubVoucherSchema.setItemCode("1221");
	                accSubVoucherSchema.setDirectionIdx("07/02/");	                
	            }else{
	 	           accSubVoucherSchema.setItemCode("2202");  
	            }
	        }
	        
	        if(blPrpJpayRefRec.getArr(i).getCertiType().equals("F")){
	            if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P95")){
	                accSubVoucherSchema.setF08("99");
	                accSubVoucherSchema.setItemCode("6601");  
	                accSubVoucherSchema.setDirectionIdx("99/");
	            }else if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P96")){
	                accSubVoucherSchema.setF08("99");	                
	                accSubVoucherSchema.setItemCode("6601");
	                accSubVoucherSchema.setDirectionIdx("99/");	                
	            }
	        }
	        
	        if(blPrpJpayRefRec.getArr(i).getCertiType().equals("Y")||
                   blPrpJpayRefRec.getArr(i).getCertiType().equals("C")){
	            DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
	            dbPrpJplanFee.getInfo(blPrpJpayRefRec.getArr(i).getCertiType(),
	                                  blPrpJpayRefRec.getArr(i).getCertiNo(),
	                                  blPrpJpayRefRec.getArr(i).getSerialNo(),
	                                  blPrpJpayRefRec.getArr(i).getPayRefReason());
	            if(!dbPrpJplanFee.getVoucherNo().equals("")&&
                       !dbPrpJplanFee.getVoucherNo().equals("0")){
	                if(blPrpJpayRefRec.getArr(i).getPayRefReason().startsWith("S")){
	                    accSubVoucherSchema.setF16("01");
	                    accSubVoucherSchema.setF21("07");
	                    accSubVoucherSchema.setItemCode("1221");
	                    accSubVoucherSchema.setDirectionIdx("07/01/");	                    
	                }else{
	                    if(dbPrpJplanFee.getRiskCode().substring(0,2).equals("05")&&
                               dbPrpJplanFee.getPayRefReason().equals("P62")){
	                        accSubVoucherSchema.setF21("02");
	                        accSubVoucherSchema.setItemCode("6601");
	                        accSubVoucherSchema.setDirectionIdx("34/");
	                    }else{
	                        accSubVoucherSchema.setItemCode("2205");
                                
                                if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P6A"))
                                	
                                    accSubVoucherSchema.setDirectionIdx("02/");
                                else
                                    accSubVoucherSchema.setDirectionIdx("01/");
	                    }
	                }
		            
		            accSubVoucherSchema.setF22(blPrpJpayRefRec.getArr(i).getCoinsCode());	                
	            }
	        }
            
            
            if(!accSubVoucherSchema.getDirectionIdx().equals("")){
                strItemCode = accSubVoucherSchema.getItemCode()+"/"+accSubVoucherSchema.getDirectionIdx();
            }else{
                strItemCode = accSubVoucherSchema.getItemCode();
            }	        
            if(blPrpJpayRefRec.getArr(i).getCertiType().equals("R")){
	            accSubVoucherSchema.setRemark(blPrpJpayRefRec.getArr(i).getRemark());
	            accSubVoucherSchema.setF22(blPrpJpayRefRec.getArr(i).getCoinsCode());
	            if(strItemCode.length()>4){
                        strItemCode = strItemCode.substring(0,4);
	            }
	        }
            
            if(blPrpJpayRefRec.getArr(i).getCertiType().equals("D")){
            	 DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
  	              dbPrpJplanFee.getInfo(blPrpJpayRefRec.getArr(i).getCertiType(),
  	                                  blPrpJpayRefRec.getArr(i).getCertiNo(),
  	                                  blPrpJpayRefRec.getArr(i).getSerialNo(),
  	                                  blPrpJpayRefRec.getArr(i).getPayRefReason());
  	              
  	              DBPrpJunInsurer dbPrpJunInsurer = new DBPrpJunInsurer();
  	              dbPrpJunInsurer.getInfo(blPrpJpayRefRec.getArr(i).getCertiType(),
  	                                  blPrpJpayRefRec.getArr(i).getCertiNo(),
  	                                  blPrpJpayRefRec.getArr(i).getSerialNo(),
  	                                  blPrpJpayRefRec.getArr(i).getPayRefReason());
  	                    
  	                    	
  	              
  	               accSubVoucherSchema.setItemCode("1221");
                      if(blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P6B")||blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P6C")){
                                	  accSubVoucherSchema.setF10(dbPrpJunInsurer.getArticleCode());
                                      accSubVoucherSchema.setDirectionIdx("10/");
                      }
                      else
                      accSubVoucherSchema.setDirectionIdx("01/");
  		            
  		            accSubVoucherSchema.setF22(blPrpJpayRefRec.getArr(i).getCoinsCode());	                
  	            
	        }
	        
	        if(blPrpJpayRefRec.getArr(i).getCertiType().equals("P")||
                   blPrpJpayRefRec.getArr(i).getCertiType().equals("E")||
                   
                   (blPrpJpayRefRec.getArr(i).getCertiType().equals("Z")&&blPrpJpayRefRec.getArr(i).getPayRefReason().equalsIgnoreCase("R40"))||
                   
                   (blPrpJpayRefRec.getArr(i).getCertiType().equals("D")&& blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P6C") )||

                   blPrpJpayRefRec.getArr(i).getCertiType().equals("J")||(blPrpJpayRefRec.getArr(i).getCertiType().equals("R")&&
                   
                	(blPrpJpayRefRec.getArr(i).getPayRefReason().startsWith("I")||
                				  blPrpJpayRefRec.getArr(i).getPayRefReason().startsWith("M")))){
	        	  
	        	if((blPrpJpayRefRec.getArr(i).getCoinsFlag().equals("1")||
                        blPrpJpayRefRec.getArr(i).getCoinsFlag().equals("3"))&&
                       blPrpJpayRefRec.getArr(i).getCoinsType().equals("3")){
	                accSubVoucherSchema.setDebitSource("" + Str.round(-1*dblSource,2));
	                double dblDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
	                dblDebitDest = Str.round(Str.round(dblDebitDest,8),2);
	                accSubVoucherSchema.setDebitDest(""+dblDebitDest);
	            }else{
	            	
                    



	            	
	                accSubVoucherSchema.setCreditSource("" + dblSource);
	                double dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
	                dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
	                accSubVoucherSchema.setCreditDest(""+dblCreditDest);
	            }
	        }else{
	            if((blPrpJpayRefRec.getArr(i).getCoinsFlag().equals("1")||
                        blPrpJpayRefRec.getArr(i).getCoinsFlag().equals("3"))&&
                       blPrpJpayRefRec.getArr(i).getCoinsType().equals("3")&&
                       blPrpJpayRefRec.getArr(i).getCertiType().equals("S")){
	                accSubVoucherSchema.setCreditSource("" + Str.round(-1*dblSource,2));
	                double dblCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
	                dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
	                accSubVoucherSchema.setCreditDest(""+dblCreditDest);
	            }else{
	                accSubVoucherSchema.setDebitSource("" + dblSource);
	                double dblDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
	                dblDebitDest = Str.round(Str.round(dblDebitDest,8),2);
	                accSubVoucherSchema.setDebitDest(""+dblDebitDest);
	            }
	        }
	        
            
            if(blPrpJpayRefRec.getArr(i).getCertiType().equals("F")&&blPrpJpayRefRec.getArr(i).getPayRefReason().equals("P95"))
            {
                accSubVoucherSchema.setItemCode("6601");       
                accSubVoucherSchema.setDirectionIdx("99/"); 
                double dblDebitDest2 = Double.parseDouble(accSubVoucherSchema.getDebitSource())*Double.parseDouble(accSubVoucherSchema.getExchangeRate());
                dblDebitDest2 = Str.round(Str.round(dblDebitDest2,8),2);
                accSubVoucherSchema.setCreditDest("" + dblDebitDest2);
                accSubVoucherSchema.setCreditSource("" + dblSource);   
                accSubVoucherSchema.setDebitDest("0");
                accSubVoucherSchema.setDebitSource("0");                            
            } 










            	blAccSubVoucher.setArr(accSubVoucherSchema);

            





















            








































            
	    }
	    
	    double dblSumDebit = 0;
	    double dblSumCredit = 0;
	    String strCurrency = "CNY";
	    boolean checkRight =true;
	    BLAccSubVoucher checksAccSubVoucher =new BLAccSubVoucher();
	    for(int i=0; i<blAccSubVoucher.getSize(); i++)
	    {
	        dblSumDebit += Double.parseDouble(blAccSubVoucher.getArr(i).getDebitDest());
	        dblSumCredit += Double.parseDouble(blAccSubVoucher.getArr(i).getCreditDest());
	        if(!blAccSubVoucher.getArr(i).getCurrency().equals("CNY")){
                    strCurrency = blAccSubVoucher.getArr(i).getCurrency();
                }
	        checksAccSubVoucher.setArr(blAccSubVoucher.getArr(i));
	    }
	    for(int i=0; i<blAccSubVoucherZJ.getSize(); i++)
	    {
	        dblSumDebit += Double.parseDouble(blAccSubVoucherZJ.getArr(i).getDebitDest());
	        dblSumCredit += Double.parseDouble(blAccSubVoucherZJ.getArr(i).getCreditDest());
	        checksAccSubVoucher.setArr(blAccSubVoucherZJ.getArr(i));
	    }
	    for (int i=0;i<checksAccSubVoucher.getSize();i++)
	    {
	    	if(!checksAccSubVoucher.getArr(i).getCurrency().equals("CNY"))
	    	{
	    		checkRight=false;
	    		continue;
	    	}
	    }
	    dblSumDebit = Str.round(dblSumDebit,2);
	    dblSumCredit = Str.round(dblSumCredit,2);
	    if(checkRight&&(dblSumDebit!=dblSumCredit))
        {
        	throw new UserException( -98, -1167, "BLPrpJpayRefMain.genAccVoucher","没有把一个打包发票的数据选全，建议使用打包号重新查询！");
        }
	    if(dblSumDebit!=dblSumCredit){
	        accSubVoucherSchema = new AccSubVoucherSchema();
	        accSubVoucherSchema.setSchema(blAccSubVoucher.getArr(blAccSubVoucher.getSize()-1));
	        accSubVoucherSchema.setCurrency("CNY");
	        accSubVoucherSchema.setExchangeRate("1");
	        
	        accSubVoucherSchema.setItemCode("6061");
	        accSubVoucherSchema.setDirectionIdx("");
	        accSubVoucherSchema.setRemark("调整汇兑损益差");
	        if(dblSumDebit-dblSumCredit>0){
	            accSubVoucherSchema.setDebitSource("0");
	            accSubVoucherSchema.setDebitDest("0");
	            accSubVoucherSchema.setCreditSource("" + (dblSumDebit - dblSumCredit));
	            accSubVoucherSchema.setCreditDest("" + (dblSumDebit - dblSumCredit));
	        }else{
	            accSubVoucherSchema.setDebitSource("" + (dblSumCredit - dblSumDebit));
	            accSubVoucherSchema.setDebitDest("" + (dblSumCredit - dblSumDebit));
	            accSubVoucherSchema.setCreditSource("0");
	            accSubVoucherSchema.setCreditDest("0");
	        }
	        blAccSubVoucher.setArr(accSubVoucherSchema);
	    }
	    
            String[] arrItemArticle = null; 
	    for(int i=0; i<blAccSubVoucher.getSize(); i++){
	        blAccSubVoucher.getArr(i).setSuffixNo("" + (i + 1));
	        blAccSubVoucher.createRawArticle(blAccSubVoucher.getArr(i),strItemCode);
	    }
	    
	    if(accMainVoucherSchema.getGenerateWay().equals("1")){
                blAccSubVoucher.voucherComp("101");
	    }
	    
	    if(accMainVoucherSchema.getGenerateWay().equals("0")){
	        blAccSubVoucher.voucherCompByRemark();
	    }
	    
	    for(int i=0; i<blAccSubVoucherZJ.getSize(); i++){
	        blAccSubVoucherZJ.getArr(i).setSuffixNo("" + (blAccSubVoucher.getSize() + i));
            blAccSubVoucherZJ.createRawArticle(blAccSubVoucherZJ.getArr(i),strItemCode);
	    }
	    for(int i=0; i<blAccSubVoucherZJ.getSize(); i++){
	        blAccSubVoucher.setArr(blAccSubVoucherZJ.getArr(i));
	        blAccSubVoucher.setItemArticle(blAccSubVoucherZJ.getItemArticle(i));
	    }
	    for(int i=0; i<blAccSubVoucher.getSize(); i++){
            arrItemArticle = new String[5];
            if(blAccSubVoucher.getArr(i).getDirectionIdx().equals("")){
                arrItemArticle[0] = blAccSubVoucher.getArr(i).getItemCode();
            }else{
                arrItemArticle[0] = blAccSubVoucher.getArr(i).getItemCode()+"/"+blAccSubVoucher.getArr(i).getDirectionIdx();
            }
            arrItemArticle[1] = blAccSubVoucher.getArr(i).getDirectionOther();            
            blAccSubVoucher.setItemArticle(arrItemArticle);
	    }
	    blAccSubVoucher.voucherOrder();
	    blAccVoucher.setBLAccSubVoucher(blAccSubVoucher);
	    blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());
	    return blAccVoucher;
	 }

    /**
     * 收付确认凭证XXXXX存 SangMingqian 20060428
     * @param blAccVoucher 凭证信息
     * @return
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     */
    public BLAccMainVoucher VoucherVerify(BLAccVoucher blAccVoucher) throws UserException,SQLException,Exception
    {
    	String strWherePart = "";
        int intNoCount = 1;  
        
        BLAccMainVoucher blAccMainVoucher = new BLAccMainVoucher();
        BLAccMainVoucher blAccMainVoucherHeadOffice = new BLAccMainVoucher();
        DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();

        
        if(blPackageFlag==false){
            this.blPrpJpayRefRec.queryInvioceCoins(this.schemas);
        }

        
        strWherePart = " PayRefNo IN ('"+this.getArr(0).getPayRefNo()+"'";
        String strPayRefNoTmp = this.getArr(0).getPayRefNo();
        for(int i=1; i<this.getSize(); i++){
            
            if(this.getArr(i).getPayRefNo().equals(strPayRefNoTmp)){
                continue;
            }else{
                intNoCount ++;
                strPayRefNoTmp = this.getArr(i).getPayRefNo();
                strWherePart += ",'" + this.getArr(i).getPayRefNo() + "'";
            }
        }
        strWherePart += ") ";
        String strOrderWhere = " ORDER BY PayRefNo";
        String strRefRecWherePart = strWherePart +" and payreftimes <1000 " + strOrderWhere ;
        this.blPrpJpayRefRec.query(strRefRecWherePart,0);
        if(this.blPrpJpayRefRec.getSize()==0){
        	throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()","查询PrpJpayRefRec失败！");
        }
        
        String strPayRefDate = this.getArr(0).getPayRefDate();
        String strPayRefCode = this.getArr(0).getPayRefCode();
        String strPayRefNoType = this.getArr(0).getPayRefNoType();
        if((strPayRefDate.equals("")||strPayRefDate==null)
          &&(strPayRefCode.equals("")||strPayRefCode==null)
          &&(strPayRefNoType.equals("")||strPayRefNoType==null)){
        	throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()","strPayRefDate、strPayRefCode、strPayRefNoType字段为空！");
        }
        
        BLAccVoucher blAccVoucherHeadOffice = this.genAccVoucherHeadOffice(strPayRefDate,strPayRefCode,strPayRefNoType,blAccVoucher.getBLAccMainVoucher().getArr(0).getAuxNumber());
        String strAccBookType = blAccVoucher.getBLAccMainVoucher().getArr(0).getAccBookType();
        String strAccBookCode = blAccVoucher.getBLAccMainVoucher().getArr(0).getAccBookCode();
        String strCenterCode = blAccVoucher.getBLAccMainVoucher().getArr(0).getCenterCode();
        String strBranchCode = blAccVoucher.getBLAccMainVoucher().getArr(0).getBranchCode();
        String strYearMonth = blAccVoucher.getBLAccMainVoucher().getArr(0).getYearMonth();
        String strVoucherNo = "";
        String strVoucherNoHeadOffice = "";
        DbPool dbpool = new DbPool();
        try {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE); 
          dbpool.beginTransaction();
          
          String[] arrArticleCode = null;
          for(int n=0;n<blAccVoucher.getBLAccSubVoucher().getSize();n++){
          	  if(blAccVoucher.getBLAccSubVoucher().getArr(n).getItemCode().equals("2203")
          	        &&blAccVoucher.getBLAccSubVoucher().getArr(n).getDirectionIdx().equals("01/")          	        
				    &&Double.parseDouble(blAccVoucher.getBLAccSubVoucher().getArr(n).getDebitDest())!=0){
          	      arrArticleCode = blAccVoucher.getBLAccSubVoucher().getArr(n).getDirectionOther().split("/");         		
          	      for(int m=0;m<arrArticleCode.length;m++){
          	    	BLAccArticleBalance aBLAccArticleBalance =new BLAccArticleBalance();
          	    	if(arrArticleCode[m].substring(0,2).equals("YW"))
          	    	{         	    		
          	          double result=  aBLAccArticleBalance.queryBalanceSource(strCenterCode,strYearMonth,arrArticleCode[m],
          	        		  "2203","01/",blAccVoucher.getBLAccSubVoucher().getArr(n).getCurrency());
          	          if(result==0){
      	          	  	  throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
        		                    strCenterCode + "的专项" + arrArticleCode[m] + "未签单预收没有余额！");          	        	  
          	          }
          	          else if(Double.parseDouble(blAccVoucher.getBLAccSubVoucher().getArr(n).getDebitSource())>result*(-1)){
                          throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
                                  strCenterCode + "的专项" + arrArticleCode[m] + "未签单预收余额不足！");          	        	  
          	          }
          	    	}          	          
          	      }
          	  }else if(blAccVoucher.getBLAccSubVoucher().getArr(n).getItemCode().equals("2203")
            	        &&blAccVoucher.getBLAccSubVoucher().getArr(n).getDirectionIdx().equals("03/")          	        
    				    &&Double.parseDouble(blAccVoucher.getBLAccSubVoucher().getArr(n).getDebitDest())!=0){
              	      arrArticleCode = blAccVoucher.getBLAccSubVoucher().getArr(n).getDirectionOther().split("/");         		
              	      for(int m=0;m<arrArticleCode.length;m++){
              	    	BLAccArticleBalance aBLAccArticleBalance =new BLAccArticleBalance();
              	    	if(arrArticleCode[m].substring(0,2).equals("YW"))
              	    	{         	    		
              	          double result=  aBLAccArticleBalance.queryBalanceSource(strCenterCode,strYearMonth,arrArticleCode[m],
              	        		  "2203","03/",blAccVoucher.getBLAccSubVoucher().getArr(n).getCurrency());
              	          if(result==0){
          	          	  	  throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
            		                    strCenterCode + "的专项" + arrArticleCode[m] + "温暖1+家没有余额！");          	        	  
              	          }
              	          else if(Double.parseDouble(blAccVoucher.getBLAccSubVoucher().getArr(n).getDebitSource())>result*(-1)){
                              throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
                                      strCenterCode + "的专项" + arrArticleCode[m] + "温暖1+家余额不足！");          	        	  
              	          }
              	    	}          	          
              	      }
              	  }
          }
          
          
          













			
			BLPrpJplanFee blPrpJplanFeeCarShip = new BLPrpJplanFee();
			for (int m = 0; m < this.blPrpJpayRefRec.getSize(); m++) {
				BLPrpJplanFee blPrpJplanFeeShip = new BLPrpJplanFee();
				if (this.blPrpJpayRefRec.getArr(m).getPayRefReason().equals(
						"R72")
						|| this.blPrpJpayRefRec.getArr(m).getPayRefReason()
								.equals("R73")
						|| this.blPrpJpayRefRec.getArr(m).getPayRefReason()
								.equals("R74")) {
					String strWherePartShip = " CertiNo='"
							+ this.blPrpJpayRefRec.getArr(m).getCertiNo() + "'"
							+ " And PayRefReason ='"
							+ this.blPrpJpayRefRec.getArr(m).getPayRefReason()
							+ "' And SerialNo='"
							+ this.blPrpJpayRefRec.getArr(m).getSerialNo()
							+ "' And riskcode='0507' " + " And voucherno='0'";
					blPrpJplanFeeShip.query(dbpool, strWherePartShip, 0);
					for (int i = 0; i < blPrpJplanFeeShip.getSize(); i++) {
						double dblExchRate = PubTools.getExchangeRate(dbpool,
								blPrpJplanFeeShip.getArr(i).getCurrency1(),
								"CNY", strPayRefDate);
						blPrpJplanFeeShip.getArr(i).setExchangeRate(
								"" + dblExchRate);
						double dblPlanFeeCNY = Double
								.parseDouble(blPrpJplanFeeShip.getArr(i)
										.getPlanFee())
								* dblExchRate;
						dblPlanFeeCNY = Str.round(Str.round(dblPlanFeeCNY, 8),
								2);
						blPrpJplanFeeShip.getArr(i).setPlanFeeCNY(
								"" + dblPlanFeeCNY);
						if(blPrpJplanFeeShip.getSize()!=0)
						blPrpJplanFeeCarShip
								.setArr(blPrpJplanFeeShip.getArr(i));
					}
				}
			}
			if (blPrpJplanFeeCarShip.getSize() > 0) {
				blPrpJplanFeeCarShip.createBeforeVoucher(dbpool, blAccVoucher
						.getBLAccMainVoucher().getArr(0));
			}
			
          
          
          if(blAccVoucher.getBLAccSubVoucher().getSize()>0
           &&blAccVoucher.getBLAccVoucherArticle().getSize()>0
           &&blAccVoucher.getBLAccMainVoucher().getSize()>0){
        	  for (int i=0;i<blAccVoucher.getBLAccSubVoucher().getSize();i++)
        	  {
        		  if(blAccVoucher.getBLAccSubVoucher().getArr(i).getF28().equals("")
        		    ||blAccVoucher.getBLAccSubVoucher().getArr(i).getF28()==null
        		    ||blAccVoucher.getBLAccSubVoucher().getArr(i).getF27().equals("")
        		    ||blAccVoucher.getBLAccSubVoucher().getArr(i).getF27()==null){
        			  throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()","送XXXXX的数据错误！"); 
        		  }
        	  }        	  
            blAccVoucher.save(dbpool);
          }
          else{
              throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()","送XXXXX的数据错误！");        	  
          }
          
          
          DBPrpJpayRefVoucher dBPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
          String strRealPayRefNo = dBPrpJpayRefVoucher.genRealPayRefNo();
          dBPrpJpayRefVoucher.setRealPayRefNo(strRealPayRefNo);
          dBPrpJpayRefVoucher.setPayRefNoType(strPayRefNoType);
          dBPrpJpayRefVoucher.setCenterCode(strCenterCode);
          dBPrpJpayRefVoucher.setVoucherDate(this.getArr(0).getPayRefDate());
          dBPrpJpayRefVoucher.setComCode(this.getArr(0).getPayRefUnit());
          dBPrpJpayRefVoucher.setPayRefCode(this.getArr(0).getPayRefCode());
          dBPrpJpayRefVoucher.insert(dbpool);
          
          
          if(blAccVoucherHeadOffice.getBLAccSubVoucher().getSize()>0){
        	  
              if(blAccVoucherHeadOffice.getBLAccMainVoucher().getSize()>0
                      &&blAccVoucherHeadOffice.getBLAccVoucherArticle().getSize()>0){           	  
                blAccVoucherHeadOffice.save(dbpool);
              }
              else{
            	  throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()","送XXXXX的联共XXXXX数据错误！"); 
              }
              strVoucherNoHeadOffice = blAccVoucherHeadOffice.getBLAccMainVoucher().getArr(0).getVoucherNo();
              blAccMainVoucherHeadOffice.setArr(blAccVoucherHeadOffice.getBLAccMainVoucher().getArr(0));
              if(this.getCoinsSize()==0&&this.blPrpJpayRefRecCoins.getSize()==0){
            	  throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()","收付表生成的联共XXXXX数据错误！"); 
              }
              for(int i=0; i<this.getCoinsSize(); i++){
                  dbPrpJpayRefMain.setSchema(this.getCoinsArr(i));
                  dbPrpJpayRefMain.setAccBookType(strAccBookType);
                  dbPrpJpayRefMain.setAccBookCode(strAccBookCode);
                  dbPrpJpayRefMain.setCenterCode("00000000");
                  dbPrpJpayRefMain.setBranchCode("00000000");
                  dbPrpJpayRefMain.setYearMonth(strYearMonth);
                  dbPrpJpayRefMain.setVoucherNo(strVoucherNoHeadOffice);
                  
                  dbPrpJpayRefMain.setRealPayRefNo(strRealPayRefNo);
                  
                  dbPrpJpayRefMain.update(dbpool);
              }
              DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
              for(int i=0; i<this.blPrpJpayRefRecCoins.getSize(); i++){
                  dbPrpJpayRefRec.setSchema(this.blPrpJpayRefRecCoins.getArr(i));
                  dbPrpJpayRefRec.setPayRefDate(strPayRefDate);
                  
                  dbPrpJpayRefRec.setIdentifyType("1");
                  
                  
                  dbPrpJpayRefRec.setRealPayRefNo(strRealPayRefNo);
                  
                  dbPrpJpayRefRec.update(dbpool);
                  if(dbPrpJpayRefRec.getCoinsType().equals("2")){
                      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
                      dbPrpJplanFee.getInfo(dbpool, dbPrpJpayRefRec.getCertiType(),
                      dbPrpJpayRefRec.getCertiNo(),
                      dbPrpJpayRefRec.getSerialNo(),
                      dbPrpJpayRefRec.getPayRefReason());
                      if(dbPrpJplanFee.getPolicyNo()==null&Double.parseDouble(dbPrpJpayRefRec.getPlanFee())==0){
                    	  throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()","PrpJplanFee表生成的联共XXXXX数据错误！");
                      }
                      dbPrpJplanFee.setRealPayRefFee(dbPrpJpayRefRec.getPlanFee());
                      dbPrpJplanFee.update(dbpool);
                  }
              }
          }
          strVoucherNo = blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
          
          if(this.getArr(0).getPayRefNoType().equals("6")){
              BLFjSettle blFjSettle = new BLFjSettle();
              blFjSettle.updateSettle(dbpool,this.getArr(0).getPayRefNo(),"1",strPayRefCode,strVoucherNo,"2");
          }
          blAccMainVoucher.setArr(blAccVoucher.getBLAccMainVoucher().getArr(0));
          if(blAccVoucherHeadOffice.getBLAccSubVoucher().getSize()>0){
              blAccMainVoucher.setArr(blAccVoucherHeadOffice.getBLAccMainVoucher().getArr(0));
          }
          
          if(this.getSize()==0){
        	  throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()","PrpJpayRefMain表生成的数据错误！");
          }
          for(int i=0; i<this.getSize(); i++){
              dbPrpJpayRefMain.setSchema(this.getArr(i));
              dbPrpJpayRefMain.setAccBookType(strAccBookType);
              dbPrpJpayRefMain.setAccBookCode(strAccBookCode);
              dbPrpJpayRefMain.setCenterCode(strCenterCode);
              dbPrpJpayRefMain.setBranchCode(strBranchCode);
              dbPrpJpayRefMain.setYearMonth(strYearMonth);
              dbPrpJpayRefMain.setVoucherNo(strVoucherNo);
              
              dbPrpJpayRefMain.setRealPayRefNo(strRealPayRefNo);
              
              dbPrpJpayRefMain.update(dbpool);
          }
          for(int i=0;i<this.blPrpJpayRefDetail.getSize();i++){
        	  this.blPrpJpayRefDetail.getArr(i).setPayRefNo(this.getArr(0).getPayRefNo());
        	  this.blPrpJpayRefDetail.getArr(i).setSerialNo(""+(i+1));
    		  this.blPrpJpayRefDetail.getArr(i).setRealPayRefNo(strRealPayRefNo);
    	  }
          
          
          
          if(this.blPrpJpayRefDetail.getSize()==0){
        	  throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()","PrpJpayRefDetail表生成的数据错误！");
          }
          else{
        	  this.blPrpJpayRefDetail.save(dbpool);
          }         
          
          PrpJpayRefRecSchema prpJpayRefRecSchema = null;
          DBPrpJpayRefRec dbPrpJpayRefRec = null;
          DBPrpJplanFee dbPrpJplanFee = null;
          
          HashSet YAB0HashSet = new HashSet();
          
          for(int i = 0;i<this.blPrpJpayRefRec.getSize();i++){
        	  prpJpayRefRecSchema = blPrpJpayRefRec.getArr(i);
              
              if(prpJpayRefRecSchema.getCertiType().equals("E")&&Double.parseDouble(prpJpayRefRecSchema.getPlanFee())<0){
              	  PrpJpayRefRecSchema prpJpayRefRecSchema1 = null;
              	  
                  double cPlanFee =0;
                  
                  for(int k=0;k<this.blPrpJpayRefRec.getSize();k++){
                      prpJpayRefRecSchema1 = blPrpJpayRefRec.getArr(k);
                      if((prpJpayRefRecSchema1.getCertiType().equals("P")
                    	  && prpJpayRefRecSchema1.getCertiNo().equals(prpJpayRefRecSchema.getPolicyNo()))
				         ||(prpJpayRefRecSchema1.getCertiType().equals("E")
				            && prpJpayRefRecSchema1.getPolicyNo().equals(prpJpayRefRecSchema.getPolicyNo())
				            && !prpJpayRefRecSchema1.getCertiNo().equals(prpJpayRefRecSchema.getCertiNo()))){
                          if(prpJpayRefRecSchema1.getPayRefReason().equals("R82")||
                             prpJpayRefRecSchema1.getPayRefReason().equals("R42")||
                             prpJpayRefRecSchema1.getPayRefReason().equals("P82")||
                             prpJpayRefRecSchema1.getPayRefReason().equals("P92")){

                          }else{
                          	cPlanFee += Double.parseDouble(prpJpayRefRecSchema1.getPlanFee());
                          }
                      }
                  }
                  
                  String strConditionFee = " (CertiType='P' AND CertiNo='"
                  	                       + prpJpayRefRecSchema.getPolicyNo()
										   + "' AND RealPayRefFee<>0 )"
				                           + " OR (CertiType='E' AND PolicyNo='"
										   + prpJpayRefRecSchema.getPolicyNo()
										   + "' AND RealPayRefFee<>0 ) AND PayRefReason !='P40' ";
                  this.blPrpJplanFee.querySum(dbpool,strConditionFee);
                  if(this.blPrpJplanFee.getSize()>0){
                      cPlanFee += Double.parseDouble(blPrpJplanFee.getArr(0).getPlanFee());
                  }
                  if(cPlanFee==0){
                      throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
                                                prpJpayRefRecSchema.getCertiNo() + "对应的XX未做收付款确认！");
                  }
                  if(Str.round(cPlanFee,2) < Math.abs(Double.parseDouble(prpJpayRefRecSchema.getPlanFee()))){
                      throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
                                                prpJpayRefRecSchema.getCertiNo() + "批单退费不能超过XX收费("+cPlanFee+")！");
                  }
              }
              
              if(prpJpayRefRecSchema.getCertiType().equals("C")
    		      &&Double.parseDouble(prpJpayRefRecSchema.getPlanFee())>0){
                      double cPlanFee =0;
                      String strConditionFee = " (CertiType='P' AND CertiNo='"
                       	                       + prpJpayRefRecSchema.getPolicyNo()
     										   + "' AND RealPayRefFee<>0 )"
     				                           + " OR (CertiType='E' AND PolicyNo='"
     										   + prpJpayRefRecSchema.getPolicyNo()
     										   + "' AND RealPayRefFee<>0 )";
                      this.blPrpJplanFee.querySum(dbpool,strConditionFee);
                      if(this.blPrpJplanFee.getSize()>0){
                          cPlanFee += Double.parseDouble(blPrpJplanFee.getArr(0).getPlanFee());
                      }
                      if(cPlanFee==0){
                          throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
                                                     prpJpayRefRecSchema.getCertiNo() + "对应的XX未做收付款确认！");
                      }
                  }
                  if(prpJpayRefRecSchema.getCertiType().equals("S")
                		  &&!prpJpayRefRecSchema.getPayRefReason().equals("P93")
        				  &&Double.parseDouble(prpJpayRefRecSchema.getPlanFee())>0){
                      double cPlanFee =0;
                      String strConditionFee = " CertiType IN ('P','E') AND CertiNo='"                 
                       	                       + prpJpayRefRecSchema.getCertiNo()
         				             		   + "' AND RealPayRefFee<>0 AND PayRefReason not in('R72','R73','R74')";    			  
                      this.blPrpJplanFee.querySum(dbpool,strConditionFee);
                      if(this.blPrpJplanFee.getSize()>0){
                          cPlanFee += Double.parseDouble(blPrpJplanFee.getArr(0).getPlanFee());
                      }
                      if(cPlanFee==0){
                          throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
                                                     prpJpayRefRecSchema.getCertiNo() + "对应的XX未做收付款确认！");
                      }
                  }
                  
                  
                  if(prpJpayRefRecSchema.getCertiType().equals("S")
                		  &&prpJpayRefRecSchema.getPayRefReason().equals("P93")
        				  &&Double.parseDouble(prpJpayRefRecSchema.getPlanFee())>0){
                      double cPlanFee =0;
                      String strConditionFee = " CertiType ='P' AND CertiNo='"
    	                                       + prpJpayRefRecSchema.getPolicyNo()+ "' AND RealPayRefFee<>0 AND PayRefReason not in('R72','R73','R74')";
                      this.blPrpJplanFee.querySum(dbpool,strConditionFee);
                      if(this.blPrpJplanFee.getSize()>0){
                          cPlanFee += Double.parseDouble(blPrpJplanFee.getArr(0).getPlanFee());
                      }
                      if(cPlanFee==0){
                          throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
                                                     prpJpayRefRecSchema.getCertiNo() + "对应的XX未做收付款确认！");
                      }
                  } 
          }
          
          BLPrpJpayRefRec blPrpjPayRefRecSettle = new BLPrpJpayRefRec();
          BLPrpJsettle blPrpJsettleTmp = new BLPrpJsettle();
          
           
          for(int i = 0;i<this.blPrpJpayRefRec.getSize();i++){
              prpJpayRefRecSchema = blPrpJpayRefRec.getArr(i);
              prpJpayRefRecSchema.setVoucherNo(strVoucherNo);
              
              dbPrpJpayRefRec = new DBPrpJpayRefRec();
              dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
              dbPrpJpayRefRec.setPayRefDate(strPayRefDate);
              
              dbPrpJpayRefRec.setIdentifyType("1");
              
              
              dbPrpJpayRefRec.setRealPayRefNo(strRealPayRefNo);
              
              dbPrpJpayRefRec.update(dbpool);
              
              if(prpJpayRefRecSchema.getCertiType().equals("P")||prpJpayRefRecSchema.getCertiType().equals("E")){                 
                  DBPrpJmanageFee dbPrpJmanageFee = new DBPrpJmanageFee();
                
                  int intResult = dbPrpJmanageFee.getInfoNew(dbpool,"0", prpJpayRefRecSchema.getCertiNo(),prpJpayRefRecSchema.getSerialNo());
                  
                  if(intResult == 100){
                      
                      logger.info("未找到批单号"+prpJpayRefRecSchema.getCertiNo() + "对应的管理费用记录！");
                      
                      
                  }else{                    
                      dbPrpJmanageFee.setPayRefDate(strPayRefDate);
                      dbPrpJmanageFee.setPayRefFee(prpJpayRefRecSchema.getPayRefFee());
                      dbPrpJmanageFee.update(dbpool);
                  }
              }   
              
              if(prpJpayRefRecSchema.getPayRefReason().equals("P6B")){
                  BLPrpJUnInsurer blPrpJunInsurer = new BLPrpJUnInsurer();
                  
                  blPrpJunInsurer.insert4Tables(dbpool,prpJpayRefRecSchema );
                  
              }
              
              
              
              if(prpJpayRefRecSchema.getPayRefReason().equals("P6C")){
                 
                 BLPrpJUnInsurer blPrpJunInsurer = new BLPrpJUnInsurer();
                  
                  blPrpJunInsurer.writeBackPrpjuninsure(dbpool,prpJpayRefRecSchema);
                  
              }
              
            
              
              	
              if(prpJpayRefRecSchema.getCertiType().equals("D")&&prpJpayRefRecSchema.getPayRefReason().equals("P60")){







                  
                BLPrpJUnInsurer blPrpJunInsurer = new BLPrpJUnInsurer();
                   
                blPrpJunInsurer.writeBackPrpjuninsure1(dbpool,prpJpayRefRecSchema);
                }
                   


              
              
              if(prpJpayRefRecSchema.getCertiType().equals("C")
            		  && prpJpayRefRecSchema.getClassCode().equals("05")
            		  && prpJpayRefRecSchema.getPayRefReason().equals("P60")){
            	  this.callClaimProcedure(dbpool,prpJpayRefRecSchema.getClaimNo(),prpJpayRefRecSchema.getCertiNo(),
            			  Double.parseDouble(prpJpayRefRecSchema.getPayRefFee()),strPayRefDate,
            			  dBPrpJpayRefVoucher.getPayRefCode(),dBPrpJpayRefVoucher.getComCode(),
            			  prpJpayRefRecSchema.getClassCode(),prpJpayRefRecSchema.getRiskCode(),
            			  prpJpayRefRecSchema.getHandler1Code(),strCenterCode);
              }
                       
              
              if(prpJpayRefRecSchema.getCertiType().equals("R"))
                  continue;
              
              dbPrpJplanFee = new DBPrpJplanFee();
              dbPrpJplanFee.getInfoForUpdate(dbpool, prpJpayRefRecSchema.getCertiType(),
                                             prpJpayRefRecSchema.getCertiNo(),
                                             prpJpayRefRecSchema.getSerialNo(),
                                             prpJpayRefRecSchema.getPayRefReason());
              dbPrpJplanFee.setRealPayRefFee("" +
                                   (Double.parseDouble(Str.chgStrZero(dbPrpJplanFee.getRealPayRefFee()))
                                   +Double.parseDouble(prpJpayRefRecSchema.getPlanFee())));
              
              
              
              if((dbPrpJplanFee.getCertiType().equals("C")||dbPrpJplanFee.getCertiType().equals("Y"))
            		  && Double.parseDouble(Str.chgStrZero(dbPrpJplanFee.getPlanFeeCNY()))==0){
                  
                  if(dbPrpJpayRefRec.getCurrency2().equals("CNY")){
                      dbPrpJplanFee.setExchangeRate(dbPrpJpayRefRec.getExchangeRate());
                  }else {
                      
                      dbPrpJplanFee.setExchangeRate("" +
                                         PubTools.getExchangeRate(dbpool,
                                         dbPrpJplanFee.getCurrency1(), "CNY", strPayRefDate));
                  }
                  double dblPlanFeeCNY = Double.parseDouble(dbPrpJplanFee.getPlanFee()) *
                                               Double.parseDouble(dbPrpJplanFee.getExchangeRate());
                  dblPlanFeeCNY = Str.round(Str.round(dblPlanFeeCNY,8),2);
                  dbPrpJplanFee.setPlanFeeCNY(""+dblPlanFeeCNY);
              }
              
              dbPrpJplanFee.update(dbpool);
              
              
              BLPrpJtaxSettle blPrpJtaxSettle = new BLPrpJtaxSettle();
              DBPrpJtaxSettle dbPrpJtaxSettle = new DBPrpJtaxSettle();
              if(prpJpayRefRecSchema.getRiskCode().equals("0507")
            		  && (prpJpayRefRecSchema.getPayRefReason().equals("R72")
            		  || prpJpayRefRecSchema.getPayRefReason().equals("R73")
            		  || prpJpayRefRecSchema.getPayRefReason().equals("R74") 
            		  || prpJpayRefRecSchema.getCertiType().equals("P")))
              {
            	  String sql = " policyno='"+prpJpayRefRecSchema.getPolicyNo()+
            	  					"' and serialno='"+prpJpayRefRecSchema.getSerialNo()+"'";
            	  blPrpJtaxSettle.query(dbpool,sql);
            	  for(int j=0;j<blPrpJtaxSettle.getSize();j++)
            	  {
            		  dbPrpJtaxSettle.setSchema(blPrpJtaxSettle.getArr(j));
            		  if(prpJpayRefRecSchema.getCertiType().equals("P")  
            				  && !prpJpayRefRecSchema.getPayRefReason().equals("R72")
                    		  && !prpJpayRefRecSchema.getPayRefReason().equals("R73")
                    		  && !prpJpayRefRecSchema.getPayRefReason().equals("R74")
                    		  && !dbPrpJtaxSettle.getTaxRelifFlag().equals("2") 
                    		  && !dbPrpJtaxSettle.getTaxRelifFlag().equals("4")
                    		  && !dbPrpJtaxSettle.getTaxRelifFlag().equals("5"))
            		  {
            			  continue;
            		  }
            		  if(dbPrpJtaxSettle.getPayRefReason().equals(prpJpayRefRecSchema.getPayRefReason()))
            		  {
            			  dbPrpJtaxSettle.setPayRefFee(""+(Double.parseDouble(dbPrpJtaxSettle.getPayRefFee()) + 
                    			  Double.parseDouble(prpJpayRefRecSchema.getPlanFee())));
            		  }
                	  dbPrpJtaxSettle.setPayRefDate(strPayRefDate);
                	  dbPrpJtaxSettle.setCenterCode(strCenterCode);
                	  dbPrpJtaxSettle.update(dbpool);
            	  }
              }
              
              
              
               if(prpJpayRefRecSchema.getPayRefReason().equals("R82")||
                  prpJpayRefRecSchema.getPayRefReason().equals("R42")||
                  prpJpayRefRecSchema.getPayRefReason().equals("P82")||
                  prpJpayRefRecSchema.getPayRefReason().equals("P92")||
                  prpJpayRefRecSchema.getPayRefReason().equals("P81")||
                  prpJpayRefRecSchema.getPayRefReason().equals("R81")||
                  prpJpayRefRecSchema.getPayRefReason().equals("R72")||
                  prpJpayRefRecSchema.getPayRefReason().equals("R73")||
                  prpJpayRefRecSchema.getPayRefReason().equals("R74")
                  ){

              }else{
                  if (prpJpayRefRecSchema.getCertiType().equals("P") ||
                       prpJpayRefRecSchema.getCertiType().equals("E")){
                      
                      DBPrpCplan dbPrpCplan = new DBPrpCplan();
                      int intReturn = 0;
                      if(prpJpayRefRecSchema.getCertiType().equals("P")){
              	          intReturn = dbPrpCplan.getInfo(dbpool, prpJpayRefRecSchema.getCertiNo(),
                                                          prpJpayRefRecSchema.getSerialNo());
                          if(intReturn==0){
                              
                              if(Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee()))==0){
                            	  
                                 
                                 
                            	  dbPrpCplan.setDelinquentFee(""+0);
                            	  dbPrpCplan.update(dbpool);
                              }
                              else
                              {
                          
                          double DelinquentFeeP = Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee())) -
                          Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()));
                          if(DelinquentFeeP==Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee())))
                          {
                              throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
                                      prpJpayRefRecSchema.getCertiNo() + "回写PrpCplan表失败！");                       	  
                          }                     
                              dbPrpCplan.setDelinquentFee("" +
                                           (Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee())) -
                                            Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
                              dbPrpCplan.update(dbpool);
                              }
                          }
                      }else{
                          BLPrpCplan blPrpCplan = new BLPrpCplan();
                          String strWhere = " EndorseNo='" + prpJpayRefRecSchema.getCertiNo()+"' ";
                          blPrpCplan.query(dbpool,strWhere,0);
                          if(blPrpCplan.getSize()>0){
                              dbPrpCplan.setSchema(blPrpCplan.getArr(0));
                              
                              if(Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee()))==0){
                            	  
                                  
                                  
                            	  dbPrpCplan.setDelinquentFee(""+0);
                            	  dbPrpCplan.update(dbpool);
                              }
                              else{
                              double DelinquentFeeE = Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee())) -
                                          Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()));         
                              if(DelinquentFeeE==Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee()))){
                                  throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
                                          prpJpayRefRecSchema.getCertiNo() + "回写PrpCplan表失败！");                       	  
                              }                             
                              dbPrpCplan.setDelinquentFee("" +
                                         (Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee())) -
                                          Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
                              dbPrpCplan.update(dbpool);   
                              }
                          }
                      }
                      
                      DBPrpCPplan dbPrpCPplan = new DBPrpCPplan();
                      if(prpJpayRefRecSchema.getCertiType().equals("P")){
                          intReturn = dbPrpCPplan.getInfo(dbpool, prpJpayRefRecSchema.getCertiNo(),
                                                           prpJpayRefRecSchema.getSerialNo());
                          if(intReturn==0){
                              
                              if(Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee()))==0){
                                 
                                 
                            	  dbPrpCPplan.setDelinquentFee(""+0);
                            	  dbPrpCPplan.update(dbpool);
                              }
                              else{
                                  
                                  double DelinquentFeeE = Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee())) -
                                              Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()));
                                  if(DelinquentFeeE==Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee())))
                                  {
                                      throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
                                              prpJpayRefRecSchema.getCertiNo() + "回写PrpCPplan表失败！");                       	  
                                  }                              	  
                              dbPrpCPplan.setDelinquentFee("" +
                                              (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee())) -
                                               Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
                              dbPrpCPplan.update(dbpool);
                              }
                          }
                      }else{
                          BLPrpCPplan blPrpCPplan = new BLPrpCPplan();
                          String strWhere = " EndorseNo='" + prpJpayRefRecSchema.getCertiNo()+"' ";
                          blPrpCPplan.query(dbpool,strWhere,0);
                          if(blPrpCPplan.getSize()>0){
                              dbPrpCPplan.setSchema(blPrpCPplan.getArr(0));
                              
                              if(Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee()))==0){
                                 
                                 
                            	  dbPrpCPplan.setDelinquentFee(""+0);
                            	  dbPrpCPplan.update(dbpool);                           	  
                              }     
                              else
                              {
                                  
                                  double DelinquentFeeE = Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee())) -
                                              Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()));
                                  if(DelinquentFeeE==Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee())))
                                  {
                                      throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
                                              prpJpayRefRecSchema.getCertiNo() + "回写PrpCPplan表失败！");                       	  
                                  }
                                  
                              dbPrpCPplan.setDelinquentFee("" +
                                           (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee())) -
                                            Double.parseDouble(ChgData.chgStrZero(prpJpayRefRecSchema.getPlanFee()))));
                              dbPrpCPplan.update(dbpool);
                              }                           
                          }
                      }
                  }
              }

              
               
              






















































              
              if(prpJpayRefRecSchema.getCoinsFlag().equals("1")&&prpJpayRefRecSchema.getCoinsType().equals("1")
                  && (prpJpayRefRecSchema.getCertiType().equals("P")||prpJpayRefRecSchema.getCertiType().equals("E"))){
            	  
                  String strWhereCoins1 = "CertiType='" + prpJpayRefRecSchema.getCertiType()
                                          +"' AND CertiNo='" + prpJpayRefRecSchema.getCertiNo()
                                          + "' AND SerialNo LIKE '" + prpJpayRefRecSchema.getSerialNo()
                                          + "%' ORDER BY CoinsType ";
                  
                  BLPrpJplanFee blPrpJplanFeeCoins1 = new BLPrpJplanFee();
                  blPrpJplanFeeCoins1.query(dbpool,strWhereCoins1);
                  double dblFeeCoins1 = 0;
                  for(int m=0;m<blPrpJplanFeeCoins1.getSize();m++){
                      if(blPrpJplanFeeCoins1.getArr(m).getCoinsType().equals("1")){
                         continue; 
                      }else{
                        dbPrpJpayRefRec = new DBPrpJpayRefRec();
                        dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
                        dbPrpJpayRefRec.setPayRefReason(prpJpayRefRecSchema.getPayRefReason().substring(0,1) + "81");   
                        dbPrpJpayRefRec.setSerialNo(blPrpJplanFeeCoins1.getArr(m).getSerialNo());
                        dbPrpJpayRefRec.setPayRefDate(strPayRefDate);
                        
                        dbPrpJpayRefRec.setIdentifyType("1");
                        dbPrpJpayRefRec.setIdentifyNumber("1");
                        
                        
                        dbPrpJpayRefRec.setRealPayRefNo(strRealPayRefNo);
                        
                        dbPrpJpayRefRec.setPlanFee(""+Double.parseDouble(blPrpJplanFeeCoins1.getArr(m).getPlanFee())*(-1));
                        dbPrpJpayRefRec.setPayRefFee(""+Double.parseDouble(blPrpJplanFeeCoins1.getArr(m).getPlanFee())*(-1));                        
                        dblFeeCoins1 += Double.parseDouble(blPrpJplanFeeCoins1.getArr(m).getPlanFee())*(-1);
                        dbPrpJpayRefRec.insert(dbpool);
                      }
                  }
                  dbPrpJpayRefRec = new DBPrpJpayRefRec();
                  dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
                  dbPrpJpayRefRec.setPayRefDate(strPayRefDate);
                  
                  dbPrpJpayRefRec.setIdentifyType("1");
                  dbPrpJpayRefRec.setIdentifyNumber("1");
                  
                  
                  dbPrpJpayRefRec.setRealPayRefNo(strRealPayRefNo);
                  
                  dbPrpJpayRefRec.setPlanFee(""+(Double.parseDouble(prpJpayRefRecSchema.getPlanFee())-dblFeeCoins1));
                  dbPrpJpayRefRec.setPayRefFee(""+(Double.parseDouble(prpJpayRefRecSchema.getPlanFee())-dblFeeCoins1));
                  dbPrpJpayRefRec.update(dbpool);
              }
              
              
  			  if(prpJpayRefRecSchema.getRiskCode().equals("YAB0")){
  				  boolean flag = true;
				  BLPrpJsettle blPrpJsettle = new BLPrpJsettle();
				  strWherePart = " PolicyNo='"+prpJpayRefRecSchema.getPolicyNo()+
				  			"' AND Currency2='"+prpJpayRefRecSchema.getCurrency2()+"'";
				  blPrpJsettle.query(strWherePart);
				  if(blPrpJsettle.getSize()==0)
				  {
					  
					  for(int j=0;j<blPrpjPayRefRecSettle.getSize();j++)
					  {
						  if(prpJpayRefRecSchema.getPolicyNo().equals(blPrpjPayRefRecSettle.getArr(j).getPolicyNo())
								  &&prpJpayRefRecSchema.getCurrency2().equals(blPrpjPayRefRecSettle.getArr(j).getCurrency2()))
						  {
							  blPrpjPayRefRecSettle.getArr(j).setPlanFee(""+
									  (Double.parseDouble(blPrpjPayRefRecSettle.getArr(j).getPlanFee())+
											  Double.parseDouble(prpJpayRefRecSchema.getPlanFee())));
							  blPrpjPayRefRecSettle.getArr(j).setPayRefFee(""+
									  (Double.parseDouble(blPrpjPayRefRecSettle.getArr(j).getPayRefFee())+
											  Double.parseDouble(prpJpayRefRecSchema.getPayRefFee())));
							  flag = false;
							  break;
						  } else
						  {
							  flag = true;
						  }
					  }
					  if(flag)
					  {
						  prpJpayRefRecSchema.setPayRefDate(strPayRefDate);
						  blPrpjPayRefRecSettle.setArr(prpJpayRefRecSchema);
					  }
				  } else
  				  {
					  for(int j=0;j<blPrpJsettleTmp.getSize();j++)
					  {
						  if(prpJpayRefRecSchema.getPolicyNo().equals(blPrpJsettleTmp.getArr(j).getPolicyNo())
								  &&prpJpayRefRecSchema.getCurrency2().equals(blPrpJsettleTmp.getArr(j).getCurrency2()))
						  {
							  blPrpJsettleTmp.getArr(j).setPlanFee(""+
									  (Double.parseDouble(blPrpJsettleTmp.getArr(j).getPlanFee())+
											  Double.parseDouble(prpJpayRefRecSchema.getPlanFee())));
							  blPrpJsettleTmp.getArr(j).setLeftSettleFee(""+
									  (Double.parseDouble(blPrpJsettleTmp.getArr(j).getLeftSettleFee())+
											  Double.parseDouble(prpJpayRefRecSchema.getPayRefFee())));
							  flag = false;
							  break;
						  } else
						  {
							  flag = true;
						  }
					  }
					  if(flag)
					  {
						  blPrpJsettle.getArr(0).setPlanFee(""+
								  (Double.parseDouble(blPrpJsettle.getArr(0).getPlanFee())+
										  Double.parseDouble(prpJpayRefRecSchema.getPlanFee())));
						  blPrpJsettle.getArr(0).setLeftSettleFee(""+
								  (Double.parseDouble(blPrpJsettle.getArr(0).getLeftSettleFee())+
										  Double.parseDouble(prpJpayRefRecSchema.getPayRefFee())));
						  blPrpJsettleTmp.setArr(blPrpJsettle.getArr(0));
					  }
  				  }
			  }
  			  
  			  
  			  if(prpJpayRefRecSchema.getCertiType().equals("P")||
  					prpJpayRefRecSchema.getCertiType().equals("E")){
  				  
  				StringBuffer SQLBuffer =null;
  				BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
  				DBPrpJplanCommission dbPrpJplanCommission = null;
  				double dbRealRefPremium =0;
  				
  				SQLBuffer =new StringBuffer();
  				SQLBuffer.append(" CertiNo='"+prpJpayRefRecSchema.getCertiNo()+"'");
  				SQLBuffer.append(" And Serialno='"+prpJpayRefRecSchema.getSerialNo()+"'");
  				SQLBuffer.append(" And PayRefReason ='P90'");
  				blPrpJplanCommission.query(dbpool, SQLBuffer.toString(), 0);
  				
  				

  	  			for (int k = 0 ;k<blPrpJplanCommission.getSize();k++){
  	  	  			dbPrpJplanCommission = new DBPrpJplanCommission();
  	  				dbPrpJplanCommission.setSchema(blPrpJplanCommission.getArr(k));
  	  	  			dbPrpJplanCommission.setPoliPayRefDate(strPayRefDate);
  	  	  			if(dbPrpJplanCommission.getRealRefPremium()==null||
  	  	  						dbPrpJplanCommission.getRealRefPremium().equals(""))
  	  	  				dbRealRefPremium = Double.parseDouble(prpJpayRefRecSchema.getPlanFee());
  	  	  			else
  	  	  				dbRealRefPremium = Double.parseDouble(prpJpayRefRecSchema.getPlanFee())+
  	  	  					 				   Double.parseDouble(dbPrpJplanCommission.getRealRefPremium());
  	  	  			dbRealRefPremium = Str.round(dbRealRefPremium, 2);
  	  	  			dbPrpJplanCommission.setRealRefPremium(dbRealRefPremium+"");
  	  	  			dbPrpJplanCommission.setPayFlag("1");
  	  	  			dbPrpJplanCommission.update(dbpool);
  	  			}

  				
  				SQLBuffer =new StringBuffer();
  				SQLBuffer.append(" PolicyNo='"+prpJpayRefRecSchema.getPolicyNo()+"'");
  				SQLBuffer.append(" And Serialno='"+prpJpayRefRecSchema.getSerialNo()+"'");
  				SQLBuffer.append(" And PayRefReason ='P93'");
  				blPrpJplanCommission.query(dbpool, SQLBuffer.toString(), 0);
  				
  	  			for (int k = 0 ;k<blPrpJplanCommission.getSize();k++){
  					dbPrpJplanCommission = new DBPrpJplanCommission();
  	  				dbPrpJplanCommission.setSchema(blPrpJplanCommission.getArr(k));
  	  				dbPrpJplanCommission.setPoliPayRefDate(prpJpayRefRecSchema.getPayRefDate());
  	  				dbPrpJplanCommission.setRealRefPremium("0.00");
  	  				dbPrpJplanCommission.setPayFlag("1");
  	  				dbPrpJplanCommission.update(dbpool);
  				}
  			  }
  			  
          }

          
          
          logger.info("开始插入数据...");
          

		  for(int i=0;i<blPrpjPayRefRecSettle.getSize();i++)
		  {
			  BLPrpJsettle blPrpJsettle = new BLPrpJsettle();
			  blPrpjPayRefRecSettle.getArr(i).setCertiType("P");
			  blPrpjPayRefRecSettle.getArr(i).setCertiNo(blPrpjPayRefRecSettle.getArr(i).getPolicyNo());
			  blPrpjPayRefRecSettle.getArr(i).setPayRefReason("R10");
			  blPrpJsettle.transData(dbpool,blPrpjPayRefRecSettle.getArr(i),"0"); 
		  }
		  
		  
		  logger.info("开始更新数据...");
		  

		  for(int i=0;i<blPrpJsettleTmp.getSize();i++)
		  {
			  DBPrpJsettle dbPrpJsettle = new DBPrpJsettle();
			  dbPrpJsettle.setSchema(blPrpJsettleTmp.getArr(i));
			  dbPrpJsettle.update(dbpool);
		  }

          dbpool.commitTransaction();
      }
      catch(UserException userexception){
          dbpool.rollbackTransaction();
          throw userexception;
      }
      catch(SQLException sqlexception){
          dbpool.rollbackTransaction();
          throw sqlexception;
      }
      catch(Exception exception){
          dbpool.rollbackTransaction();
          throw exception;
      }
      finally {
          dbpool.close();
      }
      return blAccMainVoucher;
    }

    /**
	 * 根据DbPool获取Connection
	 * @param dbpool数据库连接池
	 * @return Connection 数据库连接
	 * @throws SQLException 数据库操作异常类
	 */
	private Connection getConnection(DbPool dbpool) throws Exception {
		Connection connection=null;
		try {
			DBManager dbManager = dbpool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);
			connection = dbManager.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return connection;
	}

	/**
	 * 调用总帐接口的存储过程
	 * @param dbpool数据库连接池
	 * @param realPayRefNo实际收付号
	 * @return String 凭证号
	 * @throws 异常类
	 */
	public String callProcedure(DbPool dbpool, String realPayRefNo)
			throws Exception {
		String messageCode = "";
		String messageDesc = "";
		conn = this.getConnection(dbpool);
		conn.setAutoCommit(false);
		cstmt = conn.prepareCall("{call sunshvoucrt_pkg.payment_voucher(?,?,?)}");
		cstmt.registerOutParameter(2, Types.VARCHAR);
		cstmt.registerOutParameter(3, Types.VARCHAR);
		cstmt.setString(1, realPayRefNo);
		cstmt.setString(2, messageCode);
		cstmt.setString(3, messageDesc);
		cstmt.execute();
		messageCode = cstmt.getString(2);
		messageDesc = cstmt.getString(3);
		return messageDesc;
	}
	
    /**
     * 手续费支付单收付确认 Zhanglina 20080125
     * @param strPayRefDate 收付日期
     * @param strPayRefCode 收付员
     * @param iAuxNumber 附件张数
     * @return 
     * @throws UserException
     * @throws Exception
     */
    public String CommissionVoucherVerify(String strPayRefDate, String strPayRefCode,
            PrpJpayRefVoucherSchema iprpJpayRefVoucherSchema) throws UserException, Exception {
    	
    	int intWriteOffCount=0;
    	String strWriteOffMsg ="";
    	for(int i = 0;i<this.blPrpJpayCommission.getSize();i++){
    		if(this.blPrpJpayCommission.getArr(i).getPayFlag().equals("0")){
    			intWriteOffCount++;
    			strWriteOffMsg +=this.blPrpJpayCommission.getArr(i).getCertiNo()+" ";
    		}
    	}
    	if(intWriteOffCount!=0)
    		throw new UserException( -98, -1167, "BLPrpJpayRefMain.CommissionVoucherVerify","注销业务XXXXX批单无法做手续费支付，对应业务单号为："+strWriteOffMsg);
    	String strVoucherNo = "";
    	
	    if(this.blPrpJpayCommission.getArr(0).getComCode().equals(""))
	    {
	    	throw new UserException( -98, -1167, "BLPrpJpayRefMain.CommissionVoucherVerify","PrpJpayCommission表归属机构为空！");
	    }	    
	    String strCenterCode = this.blPrpJpayCommission.getArr(0).getCenterCode();
	    String strBranchCode = this.blPrpJpayCommission.getArr(0).getCenterCode();
	    if(strBranchCode.equals("")){
		    strBranchCode = strCenterCode;
	    }
	    
        
        DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
        
        String strPayRefNoType = "3";
        if((strPayRefDate.equals("")||strPayRefDate==null)
          &&(strPayRefCode.equals("")||strPayRefCode==null)
          &&(strPayRefNoType.equals("")||strPayRefNoType==null)){
        	throw new UserException( -96, -1167,"BLPrpJpayRefMain.CommissionVoucherVerify()","strPayRefDate、strPayRefCode、strPayRefNoType字段为空！");
        }
        
        
        if(this.getSize()==0){
      	  throw new UserException( -96, -1167,"BLPrpJpayRefMain.CommissionVoucherVerify()","PrpJpayRefMain表生成的数据错误！");
        }
        
        if(this.blPrpJpayRefDetail.getSize()==0){
      	  throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()","PrpJpayRefDetail表生成的数据错误！");
        }
        
        
        DbPool dbpool = new DbPool();
        try {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE); 
          dbpool.beginTransaction();
          
          
          DBPrpJpayRefVoucher dBPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
          String strRealPayRefNo = dBPrpJpayRefVoucher.genRealPayRefNo();
          dBPrpJpayRefVoucher.setRealPayRefNo(strRealPayRefNo);
          dBPrpJpayRefVoucher.setPayRefNoType(strPayRefNoType);
          dBPrpJpayRefVoucher.setCenterCode(this.getArr(0).getCenterCode());
          dBPrpJpayRefVoucher.setVoucherNo("0");
          dBPrpJpayRefVoucher.setVoucherDate(this.getArr(0).getPayRefDate());
          dBPrpJpayRefVoucher.setVoucherFlag("0");
          dBPrpJpayRefVoucher.setVoucherStatus("0");
          dBPrpJpayRefVoucher.setToCenterCode(this.getArr(0).getBranchCode());
          dBPrpJpayRefVoucher.setComCode(this.getArr(0).getPayRefUnit());
          dBPrpJpayRefVoucher.setPayRefCode(this.getArr(0).getPayRefCode());
          
          dBPrpJpayRefVoucher.setAttribute2(iprpJpayRefVoucherSchema.getAttribute2());
          dBPrpJpayRefVoucher.setAttribute3(iprpJpayRefVoucherSchema.getAttribute3());
          dBPrpJpayRefVoucher.insert(dbpool);
          
          

          
          for(int i=0; i<this.getSize(); i++){
              dbPrpJpayRefMain.setSchema(this.getArr(i));
              dbPrpJpayRefMain.setRealPayRefNo(strRealPayRefNo);
              dbPrpJpayRefMain.update(dbpool);
          }
          for(int i=0;i<this.blPrpJpayRefDetail.getSize();i++){
        	  this.blPrpJpayRefDetail.getArr(i).setPayRefNo(this.getArr(0).getPayRefNo());
        	  this.blPrpJpayRefDetail.getArr(i).setSerialNo(""+(i+1));
    		  this.blPrpJpayRefDetail.getArr(i).setRealPayRefNo(strRealPayRefNo);
    		  this.blPrpJpayRefDetail.getArr(i).setPayRefFee(
						""+ Math.abs(Double.parseDouble(this.blPrpJpayRefDetail.getArr(i).getPayRefFee())));
    		  
			  if (this.blPrpJpayRefDetail.getArr(i).getPayWay().substring(0, 1).equals("4")
					  &&!blPrpJpayRefDetail.getArr(i).getPayWay().equals("421")) {
				  BLPrpJpoaMain blPrpJpoaMain = new BLPrpJpoaMain();
				  blPrpJpoaMain.voucherVerifyPoa(dbpool,this.blPrpJpayRefDetail.getArr(i));
			  }
			  if (blPrpJpayRefDetail.getArr(i).getPayWay().equals("421")) {
				  this.blPrpJpayRefDetail.getArr(i).setComCode(
						  this.blPrpJpayCommission.getArr(0).getComCode());
				  this.blPrpJpayRefDetail.getArr(i).setHandler1Code(
						  this.blPrpJpayCommission.getArr(0).getHandler1Code());
			  }
    	  }
          
        	  this.blPrpJpayRefDetail.save(dbpool);
        	  
          
          PrpJpayCommissionSchema prpJpayCommissionSchema = null;
          DBPrpJpayCommission dbPrpJpayCommission = null;
          DBPrpJplanCommission dbPrpJplanCommission = null;
          
          for(int i = 0;i<this.blPrpJpayCommission.getSize();i++){
        	  prpJpayCommissionSchema = this.blPrpJpayCommission.getArr(i);
              
              
              dbPrpJpayCommission = new DBPrpJpayCommission();
              dbPrpJpayCommission.setSchema(prpJpayCommissionSchema);
              dbPrpJpayCommission.setPayRefFlag("1");
              dbPrpJpayCommission.setPayRefDate(strPayRefDate);
              dbPrpJpayCommission.setRealPayRefNo(strRealPayRefNo);
              dbPrpJpayCommission.setPayItemType("0");
              dbPrpJpayCommission.update(dbpool);
              
              dbPrpJplanCommission = new DBPrpJplanCommission();
              dbPrpJplanCommission.getInfoForUpdate(dbpool, prpJpayCommissionSchema.getCertiNo(),
            		  prpJpayCommissionSchema.getSerialNo());
              dbPrpJplanCommission.setRealPayRefFee("" +
                                   (Double.parseDouble(Str.chgStrZero(dbPrpJplanCommission.getRealPayRefFee()))
                                   +Double.parseDouble(prpJpayCommissionSchema.getPlanFee())));

              dbPrpJplanCommission.update(dbpool);
          }
          BLPrpJpaymentVoucher blPrpJpaymentVoucher= new BLPrpJpaymentVoucher();
  		  strVoucherNo = blPrpJpaymentVoucher.callProcedure(dbpool, strRealPayRefNo);
          dbpool.commitTransaction();
          return strVoucherNo;
      }
      catch(UserException userexception){
          dbpool.rollbackTransaction();
          throw userexception;
      }
      catch(SQLException sqlexception){
          dbpool.rollbackTransaction();
          throw sqlexception;
      }
      catch(Exception exception){
          dbpool.rollbackTransaction();
          throw exception;
      }
      finally {
    	  if(cstmt!=null)
      		cstmt.close();
      	  if(conn!=null)
      		conn.close();
          dbpool.close();
      }
    }

    /**
     * 发票收付确认凭证XXXXX存 SangMingqian 20060428
     * @param iArrVisaCode 发票类型
     * @param iArrVisaSerialNo 发票号
     * @param iPayRefUnit 收付机构
     * @param blAccVoucher 凭证信息
     * @return
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     */
    public BLAccMainVoucher InvoiceVoucherVerify(String[] iArrVisaCode,
                                                  String[] iArrVisaSerialNo,
												  String iPayRefUnit,
												  BLAccVoucher blAccVoucher) throws UserException, SQLException, Exception
    {
        
        BLAccMainVoucher blAccMainVoucher = new BLAccMainVoucher();
        String strPayRefNo = "";
        String strPayRefNoCoins = "";
        
        this.blPrpJpayRefRec.queryInvioceCoins(iArrVisaCode,iArrVisaSerialNo);
        this.blPackageFlag = true;
        
        strPayRefNo = this.blPrpJpayRefRec.packageInvioce(iArrVisaCode,iArrVisaSerialNo,
                                          blAccVoucher.getBLAccMainVoucher().getArr(0).getOperatorCode(),
										  iPayRefUnit, blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherDate());

        strPayRefNoCoins = this.blPrpJpayRefRec.packageInvioceCoins(blAccVoucher.getBLAccMainVoucher().getArr(0).getOperatorCode(),
        		                          iPayRefUnit,blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherDate(),
										  this.blPrpJpayRefRecCoins,this.coinsSchemas);

        
        logger.info("生成打包号：" + strPayRefNo + "    " + strPayRefNoCoins);
        

        
        for(int i=0; i<this.blPrpJpayRefDetail.getSize(); i++){
            this.blPrpJpayRefDetail.getArr(i).setPayRefNo(strPayRefNo);
            this.blPrpJpayRefDetail.getArr(i).setSerialNo(""+(i+1));
        }
        
        for(int i=0; i<this.blPrpJpayRefRec.getSize(); i++){
            this.blPrpJpayRefRec.getArr(i).setPayRefNo(strPayRefNo);
            
            this.blPrpJpayRefRec.getArr(i).setIdentifyNumber("1");
            
        }
        for(int i=0; i<this.blPrpJpayRefRecCoins.getSize(); i++){
            blPrpJpayRefRecCoins.getArr(i).setPayRefNo(strPayRefNoCoins);
            
            blPrpJpayRefRecCoins.getArr(i).setIdentifyNumber("1");
            
        }
        this.query(" PayRefNo='"+strPayRefNo+"'",0);
        
        for(int i=0; i<this.getSize(); i++){
            this.getArr(i).setPayRefCode(blAccVoucher.getBLAccMainVoucher().getArr(0).getOperatorCode());
            this.getArr(i).setPayRefUnit(iPayRefUnit);
            this.getArr(i).setPayRefDate(blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherDate());
        }
        
        blAccMainVoucher = this.VoucherVerify(blAccVoucher);
        return blAccMainVoucher;
      }
    
      /**
       * 生成红冲凭证 lijibin 20060603
       * @param iWherepart
       * @param iUserCode
       * @param iComCode
       * @return
       * @throws SQLException
       * @throws Exception
       */
      public Vector cancelVoucherBak(String iWherepart,String iUserCode,String iComCode) throws SQLException,Exception
      {
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        BLAccVoucher blAccVoucherNew = null;
        BLPrpJpayRefMain blPrpJpayRefMain = null;
        BLPrpJpayRefMain blPrpJpayRefMainOld = null;
        BLPrpJpayRefMain blPrpJpayRefMainNew = null;
        PrpJpayRefMainSchema prpJpayRefMainSchema = null;
        Bill bill = new Bill();
        ChgDate iDate = new ChgDate();
        String strOperateDate = iDate.getCurrentTime("yyyy-MM-dd");
        int intYear = Integer.parseInt(strOperateDate.substring(0,4));
        
        DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
        BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
        BLPrpJpayRefRec blPrpJpayRefRecOld = null;
        BLPrpJpayRefRec blPrpJpayRefRecNew = null;
        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
        
        DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
        DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
        DBPrpCplan dbPrpCplan = new DBPrpCplan();
        DBPrpCPplan dbPrpCPplan = new DBPrpCPplan();
        BLPrpJpayRefDetail blPrpJpayRefDetail = new BLPrpJpayRefDetail();
        BLPrpJpayRefDetail blPrpJpayRefDetailOld = null;
        BLPrpJpayRefDetail blPrpJpayRefDetailNew = null;
        PrpJpayRefDetailSchema prpJpayRefDetailSchema = null;
        Vector arrVoucherNoNew = new Vector();
        ArrayList arrPayRefNo = new ArrayList();
        DbPool dbpool = new DbPool();
        try {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE); 
          dbpool.beginTransaction();
          
          this.query(dbpool,iWherepart);
          if(this.getSize()==0){
              throw new UserException( -96, -1167, "BLPrpJpayRefMain.payRefCancel", "没有要取消收付款确认的记录！");
          }
          
          blAccVoucher.getBLAccMainVoucher().queryWithHis(dbpool,iWherepart,0);
          for(int i=0;i<blAccVoucher.getBLAccMainVoucher().getSize();i++){
              
              String strCenterCode = blAccVoucher.getBLAccMainVoucher().getArr(i).getCenterCode();
              String strBranchCode = blAccVoucher.getBLAccMainVoucher().getArr(i).getBranchCode();
              String strAccBookType = blAccVoucher.getBLAccMainVoucher().getArr(i).getAccBookType();
              String strAccBookCode = blAccVoucher.getBLAccMainVoucher().getArr(i).getAccBookCode();
              String strYearMonthOld = blAccVoucher.getBLAccMainVoucher().getArr(i).getYearMonth();
              String strVoucherNoOld = blAccVoucher.getBLAccMainVoucher().getArr(i).getVoucherNo();
              
              blAccVoucherNew = new BLAccVoucher();
              String strWherePartCancel = " CenterCode='" + strCenterCode
                                          + "' AND AccBookType='" + strAccBookType
                                          + "' AND AccBookCode='" + strAccBookCode
                                          + "' AND YearMonth='" + strYearMonthOld
                                          + "' AND VoucherNo='" + strVoucherNoOld
                                          + "' ";
              
              blAccVoucherNew.queryAll(dbpool,strWherePartCancel);
              
              String strYearMonth = strOperateDate.substring(0,4)+strOperateDate.substring(5,7);
              
              blAccVoucherNew.getBLAccMainVoucher().getArr(0).setYearMonth(strYearMonth);
              blAccVoucherNew.getBLAccMainVoucher().getArr(0).setVoucherNo("");
              blAccVoucherNew.getBLAccMainVoucher().getArr(0).setVoucherDate(strOperateDate);
              blAccVoucherNew.getBLAccMainVoucher().getArr(0).setOperatorCode(iUserCode);
              blAccVoucherNew.getBLAccMainVoucher().getArr(0).setOperatorBranch(iComCode);
              blAccVoucherNew.getBLAccMainVoucher().getArr(0).setApproverCode("");
              blAccVoucherNew.getBLAccMainVoucher().getArr(0).setApproverBranch("");
              blAccVoucherNew.getBLAccMainVoucher().getArr(0).setApproveDate("");
              blAccVoucherNew.getBLAccMainVoucher().getArr(0).setGeneCode("");
              blAccVoucherNew.getBLAccMainVoucher().getArr(0).setGeneDate("");
              blAccVoucherNew.getBLAccMainVoucher().getArr(0).setVoucherFlag("1");
              
              AccSubVoucherSchema accSubVoucherSchema = null;
              double dbDebitSource = 0;
              double dbCreditSource = 0;
              double dbDebitDest = 0;
              double dbCreditDest = 0;
              String strItemCode = "";
              for(int m=0;m<blAccVoucherNew.getBLAccSubVoucher().getSize();m++){
                  accSubVoucherSchema = blAccVoucherNew.getBLAccSubVoucher().getArr(m);
                  dbDebitSource = Double.parseDouble(accSubVoucherSchema.getDebitSource());
                  dbCreditSource = Double.parseDouble(accSubVoucherSchema.getCreditSource());
                  dbDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitDest());
                  dbCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditDest());
                  blAccVoucherNew.getBLAccSubVoucher().getArr(m).setYearMonth(strYearMonth);
                  blAccVoucherNew.getBLAccSubVoucher().getArr(m).setVoucherNo("");
                  blAccVoucherNew.getBLAccSubVoucher().getArr(m).setRemark("对冲" + strVoucherNoOld + "号凭证");
                  blAccVoucherNew.getBLAccSubVoucher().getArr(m).setDebitSource(""+(-1)*dbDebitSource);
                  blAccVoucherNew.getBLAccSubVoucher().getArr(m).setCreditSource(""+(-1)*dbCreditSource);
                  blAccVoucherNew.getBLAccSubVoucher().getArr(m).setDebitDest(""+(-1)*dbDebitDest);
                  blAccVoucherNew.getBLAccSubVoucher().getArr(m).setCreditDest(""+(-1)*dbCreditDest);
                  
                  if(!accSubVoucherSchema.getItemCode().equals("1001")&&
                      !accSubVoucherSchema.getItemCode().equals("1002")&&
                      
                      !accSubVoucherSchema.getItemCode().equals("3001")){
                    if(!accSubVoucherSchema.getDirectionIdx().equals("00")){
                        strItemCode = accSubVoucherSchema.getItemCode()+"/"+accSubVoucherSchema.getDirectionIdx();
                    }else{
                        strItemCode = accSubVoucherSchema.getItemCode();
                    }           
                  }
              }
              
              
              String strWherePart = " CenterCode='" + strCenterCode
                                    + "' AND YearMonth='" + strYearMonthOld
                                    + "' AND VoucherNo='" + strVoucherNoOld
                                    + "' ORDER BY PayRefNo ";
              blPrpJpayRefMain = new BLPrpJpayRefMain();
              blPrpJpayRefMain.query(dbpool,strWherePart,0);

              
              String strPayRefNo = blPrpJpayRefMain.getArr(0).getPayRefNo();
              strWherePart = " PayRefNo IN ('"+strPayRefNo+"'";
              for(int j=1; j<blPrpJpayRefMain.getSize(); j++)
              {
                if(!blPrpJpayRefMain.getArr(j).getPayRefNo().equals(strPayRefNo))
                {
                  strPayRefNo = blPrpJpayRefMain.getArr(j).getPayRefNo();
                  strWherePart += ",'"+strPayRefNo+"'";
                }
              }
              strWherePart += ")";
              blPrpJpayRefRec.query(dbpool,strWherePart,0);
              for(int j=0; j<blPrpJpayRefRec.getSize(); j++)
              {
                prpJpayRefRecSchema = blPrpJpayRefRec.getArr(j);
                
                dbPrpJplanFee.getInfo(dbpool,
                                      prpJpayRefRecSchema.getCertiType(),
                                      prpJpayRefRecSchema.getCertiNo(),
                                      prpJpayRefRecSchema.getSerialNo(),
                                      prpJpayRefRecSchema.getPayRefReason());
                dbPrpJplanFee.setRealPayRefFee("0");
                
                if(!dbPrpJplanFee.getFlag().equals("")&&
                   dbPrpJplanFee.getFlag().substring(0,1).equals("1")&&
                   !dbPrpJplanFee.getVoucherNo().equals(""))
                {
                  
                  accSubVoucherSchema = new AccSubVoucherSchema();
                  accSubVoucherSchema.setSchema(blAccVoucherNew.getBLAccSubVoucher().getArr(0));
                  accSubVoucherSchema.setCurrency(dbPrpJplanFee.getCurrency1());
                  accSubVoucherSchema.setF01(dbPrpJplanFee.getClassCode());
                  
                  if(dbPrpJplanFee.getRiskCode().equals("0507")){
                      accSubVoucherSchema.setF05(dbPrpJplanFee.getRiskCode()+dbPrpJplanFee.getCarNatureCode());                    
                  }else{
                      accSubVoucherSchema.setF05(dbPrpJplanFee.getRiskCode());                    
                  }
                  accSubVoucherSchema.setF28(dbPrpJplanFee.getComCode());
                  accSubVoucherSchema.setF27(dbPrpJplanFee.getHandler1Code());
                  accSubVoucherSchema.setRemark("对冲" + strVoucherNoOld + "号凭证");
                  accSubVoucherSchema.setItemCode("1122");
                  accSubVoucherSchema.setDirectionIdx("00");
                  accSubVoucherSchema.setExchangeRate(prpJpayRefRecSchema.getExchangeRate());
                  accSubVoucherSchema.setDebitSource(dbPrpJplanFee.getPlanFee());
                  accSubVoucherSchema.setDebitDest(dbPrpJplanFee.getPlanFeeCNY());
                  accSubVoucherSchema.setCreditSource("" + 0);
                  accSubVoucherSchema.setCreditDest("" + 0);
                  blAccVoucherNew.getBLAccSubVoucher().setArr(accSubVoucherSchema);
                  
                  accSubVoucherSchema = new AccSubVoucherSchema();
                  accSubVoucherSchema.setSchema(blAccVoucherNew.getBLAccSubVoucher().getArr(0));
                  accSubVoucherSchema.setCurrency(dbPrpJplanFee.getCurrency1());
                  accSubVoucherSchema.setF01(dbPrpJplanFee.getClassCode());
                  
                  if(dbPrpJplanFee.getRiskCode().equals("0507")){
                      accSubVoucherSchema.setF05(dbPrpJplanFee.getRiskCode()+dbPrpJplanFee.getCarNatureCode());                    
                  }else{
                      accSubVoucherSchema.setF05(dbPrpJplanFee.getRiskCode());                    
                  }
                  accSubVoucherSchema.setF28(dbPrpJplanFee.getComCode());
                  accSubVoucherSchema.setF27(dbPrpJplanFee.getHandler1Code());
                  accSubVoucherSchema.setRemark("对冲" + strVoucherNoOld + "号凭证");
                  
                  accSubVoucherSchema.setItemCode("2203");
                  accSubVoucherSchema.setDirectionIdx("02/");
                  accSubVoucherSchema.setExchangeRate(prpJpayRefRecSchema.getExchangeRate());
                  accSubVoucherSchema.setDebitSource("" + 0);
                  accSubVoucherSchema.setDebitDest("" + 0);
                  accSubVoucherSchema.setCreditSource(dbPrpJplanFee.getPlanFee());
                  accSubVoucherSchema.setCreditDest(dbPrpJplanFee.getPlanFeeCNY());
                  blAccVoucherNew.getBLAccSubVoucher().setArr(accSubVoucherSchema);
                  
                  dbPrpJplanFee.setFlag("");
                }
                dbPrpJplanFee.update(dbpool);
                
                if (prpJpayRefRecSchema.getCertiType().equals("P")) {
                	if(prpJpayRefRecSchema.getPayRefReason().equals("R82")||
                       prpJpayRefRecSchema.getPayRefReason().equals("R42")||
                       prpJpayRefRecSchema.getPayRefReason().equals("P82")||
                       prpJpayRefRecSchema.getPayRefReason().equals("P92")||
                	   prpJpayRefRecSchema.getPayRefReason().equals("P81")||
                       prpJpayRefRecSchema.getPayRefReason().equals("R81"))
                	{                
                	}
                	else{
                      
                      if (dbPrpCplan.getInfo(dbpool,
                                         prpJpayRefRecSchema.getCertiNo(),
                                         prpJpayRefRecSchema.getSerialNo()) == 0) {
                      
                      
                      
                      
                    dbPrpCplan.setDelinquentFee(prpJpayRefRecSchema.getPlanFee());
                    dbPrpCplan.update(dbpool);
                  }
                  
                  if (dbPrpCPplan.getInfo(dbpool,
                                          prpJpayRefRecSchema.getCertiNo(),
                                          prpJpayRefRecSchema.getSerialNo()) == 0) {
                    
                    
                    
                   
                    dbPrpCPplan.setDelinquentFee(prpJpayRefRecSchema.getPlanFee());
                    dbPrpCPplan.update(dbpool);
                   }
                  }
                }
                else if (prpJpayRefRecSchema.getCertiType().equals("E")) {
                	if(prpJpayRefRecSchema.getPayRefReason().equals("R82")||
                            prpJpayRefRecSchema.getPayRefReason().equals("R42")||
                            prpJpayRefRecSchema.getPayRefReason().equals("P82")||
                            prpJpayRefRecSchema.getPayRefReason().equals("P92")||
                     	   prpJpayRefRecSchema.getPayRefReason().equals("P81")||
                            prpJpayRefRecSchema.getPayRefReason().equals("R81"))
                     	{                
                     	}
                     	else{                	
                  BLPrpCplan blPrpCplan = new BLPrpCplan();
                  String strWhere = " EndorseNo='" + prpJpayRefRecSchema.getCertiNo() + "' ";
                  blPrpCplan.query(dbpool, strWhere, 0);
                  for(int k=0; k<blPrpCplan.getSize(); k++)
                  {   
                    dbPrpCplan.setSchema(blPrpCplan.getArr(k));
                    
                   
                  
                   
                    dbPrpCplan.setDelinquentFee(prpJpayRefRecSchema.getPlanFee());
                    dbPrpCplan.update(dbpool);
                  }
                  BLPrpCPplan blPrpCPplan = new BLPrpCPplan();
                  blPrpCPplan.query(dbpool, strWhere, 0);
                  for(int k=0; k<blPrpCPplan.getSize(); k++)
                  {
                    dbPrpCPplan.setSchema(blPrpCPplan.getArr(k));
                    
                   
                   
                   
                   
                   
                   
                    dbPrpCPplan.setDelinquentFee(prpJpayRefRecSchema.getPlanFee());
                    dbPrpCPplan.update(dbpool);
                  }
                 }
                }
              }
              
              for(int n=0; n<blAccVoucherNew.getBLAccSubVoucher().getSize(); n++)
              {
                blAccVoucherNew.getBLAccSubVoucher().getArr(n).setSuffixNo("" + (n + 1));
                blAccVoucherNew.getBLAccSubVoucher().createRawArticle(blAccVoucherNew.getBLAccSubVoucher().getArr(n),strItemCode);
              }
              blAccVoucherNew.setBLAccVoucherArticle(blAccVoucherNew.getBLAccSubVoucher().genVoucherArticle());
              
              blAccVoucherNew.getBLAccSubVoucher().voucherComp("101");
              for(int n=0; n<blAccVoucherNew.getBLAccSubVoucher().getSize(); n++)
              {
                
                if(Double.parseDouble(blAccVoucherNew.getBLAccSubVoucher().getArr(n).getCreditDest())==0&&
                   Double.parseDouble(blAccVoucherNew.getBLAccSubVoucher().getArr(n).getDebitDest())==0)
                {
                  blAccVoucherNew.getBLAccSubVoucher().remove(n);
                  blAccVoucherNew.getBLAccSubVoucher().removeItemArticle(n);
                  n--;
                  continue;
                }
                blAccVoucherNew.getBLAccSubVoucher().getArr(n).setSuffixNo("" + (n + 1));
                blAccVoucherNew.getBLAccSubVoucher().createRawArticle(blAccVoucherNew.getBLAccSubVoucher().getArr(n),"");
              }
              blAccVoucherNew.setBLAccVoucherArticle(blAccVoucherNew.getBLAccSubVoucher().genVoucherArticle());
              
              blAccVoucherNew.getBLAccSubVoucher().voucherOrder();
              
              blAccVoucherNew.save(dbpool);

              String strVoucherNoNew = blAccVoucherNew.getBLAccMainVoucher().getArr(0).getVoucherNo();
              String strPayRefNoPre = "";
              String strPayRefNoOld = "";
              String strPayRefNoNew = "";
              double dbPayRefFee = 0;
              String billNo1 = "";
              String billNo2 = "";
              for(int j=0;j<blPrpJpayRefMain.getSize();j++)
              {
                  prpJpayRefMainSchema = new PrpJpayRefMainSchema();
                  prpJpayRefMainSchema = blPrpJpayRefMain.getArr(j);
                  strPayRefNo = prpJpayRefMainSchema.getPayRefNo();
                  dbPayRefFee = Double.parseDouble(prpJpayRefMainSchema.getPayRefFee());

                  
                  
                  if(!strPayRefNo.equals(strPayRefNoPre)){
                      billNo1 = bill.getNo("prpjpayrec","0501",prpJpayRefMainSchema.getPackageUnit(),intYear,"00");
                      billNo2 = bill.getNo("prpjpayrec","0501",prpJpayRefMainSchema.getPackageUnit(),intYear,"00");
                  }
                  strPayRefNoOld = blPrpJpayRefRec.createPayRefNo(billNo1,"0501",prpJpayRefMainSchema.getPackageUnit());
                  prpJpayRefMainSchema.setPayRefNo(strPayRefNoOld);
                  prpJpayRefMainSchema.setFlag("1"); 
                  blPrpJpayRefMainOld = new BLPrpJpayRefMain();
                  blPrpJpayRefMainOld.setArr(prpJpayRefMainSchema);
                  blPrpJpayRefMainOld.save(dbpool);
                  
                  strPayRefNoNew = blPrpJpayRefRec.createPayRefNo(billNo2,"0501",prpJpayRefMainSchema.getPackageUnit());
                  prpJpayRefMainSchema = blPrpJpayRefMain.getArr(j);
                  prpJpayRefMainSchema.setPayRefNo(strPayRefNoNew);
                  prpJpayRefMainSchema.setPayRefFee(""+(-1)*dbPayRefFee);
                  prpJpayRefMainSchema.setCenterCode(strCenterCode);
                  prpJpayRefMainSchema.setBranchCode(strBranchCode);
                  prpJpayRefMainSchema.setAccBookType(strAccBookType);
                  prpJpayRefMainSchema.setAccBookCode(strAccBookCode);
                  prpJpayRefMainSchema.setYearMonth(strYearMonth);
                  prpJpayRefMainSchema.setVoucherNo(strVoucherNoNew);
                  prpJpayRefMainSchema.setPayRefDate(strOperateDate);
                  prpJpayRefMainSchema.setPayRefCode(iUserCode);
                  prpJpayRefMainSchema.setPayRefUnit(iComCode);
                  blPrpJpayRefMainNew = new BLPrpJpayRefMain();
                  blPrpJpayRefMainNew.setArr(prpJpayRefMainSchema);
                  blPrpJpayRefMainNew.save(dbpool);
                  
                  prpJpayRefMainSchema.setPayRefNo(strPayRefNo);
                  prpJpayRefMainSchema.setPayRefFee(""+dbPayRefFee);
                  prpJpayRefMainSchema.setPayRefCode("");
                  prpJpayRefMainSchema.setPayRefUnit("");
                  prpJpayRefMainSchema.setPayRefDate("");
                  prpJpayRefMainSchema.setAccBookType("");
                  prpJpayRefMainSchema.setAccBookCode("");
                  prpJpayRefMainSchema.setCenterCode("");
                  prpJpayRefMainSchema.setBranchCode("");
                  prpJpayRefMainSchema.setYearMonth("");
                  prpJpayRefMainSchema.setVoucherNo("");
                  prpJpayRefMainSchema.setFlag("");
                  dbPrpJpayRefMain.setSchema(prpJpayRefMainSchema);
                  dbPrpJpayRefMain.update(dbpool);
                  if(strPayRefNo.equals(strPayRefNoPre)){
                    continue;
                  }
                  strWherePart = " PayRefNo='"+strPayRefNo+"'";
                  
                  blPrpJpayRefDetail.query(dbpool,strWherePart,0);
                  for(int k=0;k<blPrpJpayRefDetail.getSize();k++)
                  {
                      prpJpayRefDetailSchema = blPrpJpayRefDetail.getArr(k);
                      dbPayRefFee = Double.parseDouble(prpJpayRefDetailSchema.getPayRefFee());
                      
                      prpJpayRefDetailSchema.setPayRefNo(strPayRefNoOld);
                      blPrpJpayRefDetailOld = new BLPrpJpayRefDetail();
                      blPrpJpayRefDetailOld.setArr(prpJpayRefDetailSchema);
                      blPrpJpayRefDetailOld.save(dbpool);
                      
                      prpJpayRefDetailSchema = blPrpJpayRefDetail.getArr(k);
                      prpJpayRefDetailSchema.setPayRefNo(strPayRefNoNew);
                      prpJpayRefDetailSchema.setPayRefFee(""+(-1)*dbPayRefFee);
                      blPrpJpayRefDetailNew = new BLPrpJpayRefDetail();
                      blPrpJpayRefDetailNew.setArr(prpJpayRefDetailSchema);
                      blPrpJpayRefDetailNew.save(dbpool);
                      
                      String strWherePartDel = "";
                      strWherePartDel = "DELETE FROM PrpJpayRefDetail WHERE PayRefNo='" + strPayRefNo + "'";
                      dbpool.delete(strWherePartDel);
                  }
                  arrPayRefNo.add(new String[]{strPayRefNo,strPayRefNoOld,strPayRefNoNew,billNo1,billNo2});
                  strPayRefNoPre = strPayRefNo;
              }
              for(int k=0;k<blPrpJpayRefRec.getSize();k++)
              {
                  prpJpayRefRecSchema = blPrpJpayRefRec.getArr(k);
                  dbPayRefFee = Double.parseDouble(prpJpayRefRecSchema.getPayRefFee());
                  strPayRefNo = prpJpayRefRecSchema.getPayRefNo();
                  for(int j=0; j<arrPayRefNo.size(); j++)
                  {
                    if( (((String[])arrPayRefNo.get(i))[0]).equals(strPayRefNo))
                    {
                      strPayRefNoOld = ( (String[]) arrPayRefNo.get(i))[1];
                      strPayRefNoNew = ( (String[]) arrPayRefNo.get(i))[2];
                    }
                  }
                  
                  prpJpayRefRecSchema.setPayRefTimes(this.getPayRefTimes(dbpool,prpJpayRefRecSchema,"10"));
                  prpJpayRefRecSchema.setPayRefNo(strPayRefNoOld);
                  prpJpayRefRecSchema.setFlag("1");
                  blPrpJpayRefRecOld = new BLPrpJpayRefRec();
                  blPrpJpayRefRecOld.setArr(prpJpayRefRecSchema);
                  blPrpJpayRefRecOld.save(dbpool);
                  
                  prpJpayRefRecSchema = blPrpJpayRefRec.getArr(k);
                  prpJpayRefRecSchema.setPayRefTimes(this.getPayRefTimes(dbpool,prpJpayRefRecSchema,"20"));
                  prpJpayRefRecSchema.setPayRefNo(strPayRefNoNew);
                  prpJpayRefRecSchema.setPayRefFee(""+(-1)*dbPayRefFee);
                  prpJpayRefRecSchema.setPayRefDate(strOperateDate);
                  
                  blPrpJpayRefRecNew = new BLPrpJpayRefRec();
                  blPrpJpayRefRecNew.setArr(prpJpayRefRecSchema);
                  blPrpJpayRefRecNew.save(dbpool);
                  
                  
                  if((prpJpayRefRecSchema.getCertiType().equals("P")||prpJpayRefRecSchema.getCertiType().equals("E"))
                        && prpJpayRefRecSchema.getCoinsFlag().equals("1")
                        && prpJpayRefRecSchema.getCoinsType().equals("1")){
                      if(prpJpayRefRecSchema.getPayRefReason().substring(1,3).equals("81")){
                        dbPrpJpayRefRec = new DBPrpJpayRefRec();
                        dbPrpJpayRefRec.delete(dbpool,prpJpayRefRecSchema.getCertiType(),
                                                prpJpayRefRecSchema.getCertiNo(),
                                                prpJpayRefRecSchema.getSerialNo(),
                                                prpJpayRefRecSchema.getPayRefReason(),
                                                "1"); 
                        continue;
                      }else{
                          DBPrpJplanFee dbPrpJplanFeeCoins1 = new DBPrpJplanFee();
                          dbPrpJplanFeeCoins1.getInfo(dbpool,prpJpayRefRecSchema.getCertiType(),
                                                       prpJpayRefRecSchema.getCertiNo(),
                                                       prpJpayRefRecSchema.getSerialNo(),
                                                       prpJpayRefRecSchema.getPayRefReason());
                          prpJpayRefRecSchema.setPayRefNo(strPayRefNo);
                          prpJpayRefRecSchema.setPayRefTimes("1");
                          prpJpayRefRecSchema.setPlanFee(dbPrpJplanFeeCoins1.getPlanFee());
                          prpJpayRefRecSchema.setPayRefFee(dbPrpJplanFeeCoins1.getPlanFee());
                          prpJpayRefRecSchema.setPayRefDate("");
                          prpJpayRefRecSchema.setFlag("");
                          dbPrpJpayRefRec = new DBPrpJpayRefRec();
                          dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
                          dbPrpJpayRefRec.update(dbpool);                                              
                      }
                  }else{
                      prpJpayRefRecSchema.setPayRefNo(strPayRefNo);
                      prpJpayRefRecSchema.setPayRefTimes("1");
                      prpJpayRefRecSchema.setPayRefFee(""+dbPayRefFee);
                      prpJpayRefRecSchema.setPayRefDate("");
                      prpJpayRefRecSchema.setFlag("");
                      dbPrpJpayRefRec = new DBPrpJpayRefRec();
                      dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
                      dbPrpJpayRefRec.update(dbpool);                    
                  }
              }
              arrVoucherNoNew.add(strVoucherNoNew);
          }
          dbpool.commitTransaction();
          for(int i=0; i<arrPayRefNo.size(); i++)
          {
            bill.deleteNo("prpjpayrec", ((String[])arrPayRefNo.get(i))[3]);
            bill.deleteNo("prpjpayrec", ((String[])arrPayRefNo.get(i))[3]);
          }
        }
        catch(SQLException sqlexception){
          dbpool.rollbackTransaction();
          sqlexception.printStackTrace();
          for(int i=0; i<arrPayRefNo.size(); i++)
          {
            bill.putNo("prpjpayrec", ((String[])arrPayRefNo.get(i))[3]);
            bill.putNo("prpjpayrec", ((String[])arrPayRefNo.get(i))[3]);
          }
          throw sqlexception;
        }
        catch(Exception exception){
          dbpool.rollbackTransaction();
          exception.printStackTrace();
          for(int i=0; i<arrPayRefNo.size(); i++)
          {
            bill.putNo("prpjpayrec", ((String[])arrPayRefNo.get(i))[3]);
            bill.putNo("prpjpayrec", ((String[])arrPayRefNo.get(i))[3]);
          }
          throw exception;
        }
        finally {
          dbpool.close();
        }
        return arrVoucherNoNew;
    }
      /**
       * @desc  取消手续费支付单收付款确认，生成红冲凭证入口
       * @param iWherepart
       * @param iUserCode
       * @param iComCode
       * @return
       * @throws SQLException
       * @throws Exception
       */
      public Vector cancelCommissionVoucher(Vector arrCenterCode,Vector arrRealPayRefNo,String iUserCode,String iComCode) 
        throws SQLException,Exception{
    	  
    	  Bill bill              = new Bill();
          ChgDate iDate          = new ChgDate();
          Vector arrVoucherNoNew = new Vector();
          ArrayList arrPayRefNo  = new ArrayList();
          String strOperateDate  = iDate.getCurrentTime("yyyy-MM-dd");
          int intYear            = Integer.parseInt(strOperateDate.substring(0,4));
          
    	  PrpJpayRefMainSchema prpJpayRefMainSchema       = null;
    	  PrpJpayCommissionSchema prpJpayCommissionSchema = null;
    	  PrpJpayRefDetailSchema prpJpayRefDetailSchema   = new PrpJpayRefDetailSchema();
    	  
    	  DBPrpJpayCommission dbPrpJpayCommission         = null;
          DBPrpJplanCommission dbPrpJplanCommission       = null;
          
          DBPrpJpayRefMain dbPrpJpayRefMain               = new DBPrpJpayRefMain();
          
          BLPrpJpayRefMain blPrpJpayRefMain               = null;
          BLPrpJpayRefMain blPrpJpayRefMainOld            = null;
          BLPrpJpayRefMain blPrpJpayRefMainNew            = null;
          BLPrpJpayCommission blPrpJpayCommission         = new BLPrpJpayCommission();
          BLPrpJpayCommission blPrpJpayCommissionOld      = null;
          BLPrpJpayCommission blPrpJpayCommissionNew      = new BLPrpJpayCommission();
          BLPrpJpayRefDetail blPrpJpayRefDetail           = new BLPrpJpayRefDetail();
          BLPrpJpayRefDetail blPrpJpayRefDetailOld        = new BLPrpJpayRefDetail();
          BLPrpJpayRefDetail blPrpJpayRefDetailNew        = new BLPrpJpayRefDetail();
          
          DbPool dbpool = new DbPool();
          try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE); 
            dbpool.beginTransaction();
            for (int i=0;i<arrRealPayRefNo.size();i++){
              
              blPrpJpayRefMain = new BLPrpJpayRefMain();
              blPrpJpayCommission = new BLPrpJpayCommission();
            
              String strRealPayRefNo = (String)arrRealPayRefNo.get(i);
              String strWherePart = " RealPayRefNo ='"+strRealPayRefNo+"' ";
              blPrpJpayRefMain.query(dbpool,strWherePart,0);
              blPrpJpayCommission.query(dbpool,strWherePart,0);
              blPrpJpayRefDetail.query(dbpool,strWherePart,0);
            
              
              DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
              String cancelRealPayRefNo = dbPrpJpayRefVoucher.genRealPayRefNo();
              dbPrpJpayRefVoucher.setRealPayRefNo(cancelRealPayRefNo);
              dbPrpJpayRefVoucher.setPayRefNoType(blPrpJpayRefMain.getArr(0).getPayRefNoType());
              dbPrpJpayRefVoucher.setCenterCode(blPrpJpayRefMain.getArr(0).getCenterCode());
              dbPrpJpayRefVoucher.setVoucherNo("0");
              dbPrpJpayRefVoucher.setVoucherDate(strOperateDate);
              dbPrpJpayRefVoucher.setVoucherFlag("0");
              dbPrpJpayRefVoucher.setVoucherStatus("0");
              dbPrpJpayRefVoucher.setToCenterCode(blPrpJpayRefMain.getArr(0).getBranchCode());
              
              if(dbPrpJpayRefVoucher.getToCenterCode().equals(dbPrpJpayRefVoucher.getCenterCode())){
              	dbPrpJpayRefVoucher.setToCenterCode("0");
              }
              dbPrpJpayRefVoucher.setComCode(iComCode);
              dbPrpJpayRefVoucher.setPayRefCode(iUserCode);
              dbPrpJpayRefVoucher.insert(dbpool);
              
              String strPayRefNoPre = "";
              String strPayRefNoOld = "";
              String strPayRefNoNew = "";
              double dbPayRefFee = 0;
              String billNo1 = "";
              String billNo2 = "";
              String strPayRefNo = "";
            
              String strCenterCode = blPrpJpayRefMain.getArr(0).getCenterCode();
            
              
              for(int j=0;j<blPrpJpayRefMain.getSize();j++){
                prpJpayRefMainSchema = blPrpJpayRefMain.getArr(j);
                strPayRefNo = prpJpayRefMainSchema.getPayRefNo();
                dbPayRefFee = Double.parseDouble(prpJpayRefMainSchema.getPayRefFee());
                if(!strPayRefNo.equals(strPayRefNoPre)){
                    billNo1 = cancelRealPayRefNo + "1" + j;
                    billNo2 = cancelRealPayRefNo + "2" + j;
                    strPayRefNoOld = billNo1;
                    strPayRefNoNew = billNo2;
                    arrPayRefNo.add(new String[]{strPayRefNo,strPayRefNoOld,strPayRefNoNew,billNo1,billNo2});
                }
                
                
                
                prpJpayRefMainSchema.setPayRefNo(strPayRefNoOld);
                prpJpayRefMainSchema.setFlag("1"); 
                blPrpJpayRefMainOld = new BLPrpJpayRefMain();
                blPrpJpayRefMainOld.setArr(prpJpayRefMainSchema);
                blPrpJpayRefMainOld.save(dbpool);
                
                
                prpJpayRefMainSchema = blPrpJpayRefMain.getArr(j);
                prpJpayRefMainSchema.setPayRefNo(strPayRefNoNew);
                prpJpayRefMainSchema.setPayRefFee(""+(-1)*dbPayRefFee);
                prpJpayRefMainSchema.setCenterCode(strCenterCode);
                prpJpayRefMainSchema.setAccBookCode("");
                prpJpayRefMainSchema.setAccBookType("");
                prpJpayRefMainSchema.setYearMonth("");
                prpJpayRefMainSchema.setVoucherNo("");
                prpJpayRefMainSchema.setPayRefDate(strOperateDate);
                prpJpayRefMainSchema.setPayRefCode(iUserCode);
                prpJpayRefMainSchema.setPayRefUnit(iComCode);
                prpJpayRefMainSchema.setRealPayRefNo(cancelRealPayRefNo);
                blPrpJpayRefMainNew = new BLPrpJpayRefMain();
                blPrpJpayRefMainNew.setArr(prpJpayRefMainSchema);
                blPrpJpayRefMainNew.save(dbpool);
                
                





                
                prpJpayRefMainSchema.setPayRefNo(strPayRefNo);
                prpJpayRefMainSchema.setPayRefFee(""+dbPayRefFee);
                prpJpayRefMainSchema.setPayRefCode("");
                prpJpayRefMainSchema.setPayRefUnit("");
                prpJpayRefMainSchema.setPayRefDate("");
                prpJpayRefMainSchema.setFlag("");
                prpJpayRefMainSchema.setRealPayRefNo("");
                dbPrpJpayRefMain.setSchema(prpJpayRefMainSchema);
                dbPrpJpayRefMain.update(dbpool);
                
                strPayRefNoPre = strPayRefNo;
            }
              
      		  
      		  for (int j = 0; j < blPrpJpayRefDetail.getSize(); j++) {
				prpJpayRefDetailSchema = blPrpJpayRefDetail.getArr(j);
				String payRefNo = prpJpayRefDetailSchema.getPayRefNo();
				double dblPayRefFee = Double.parseDouble(prpJpayRefDetailSchema.getPayRefFee());
				for (int m = 0; m < arrPayRefNo.size(); m++) {
					if ((((String[]) arrPayRefNo.get(m))[0]).equals(payRefNo)) {
						
						prpJpayRefDetailSchema.setPayRefNo(((String[]) arrPayRefNo.get(m))[1]);
						blPrpJpayRefDetailOld = new BLPrpJpayRefDetail();
						blPrpJpayRefDetailOld.setArr(prpJpayRefDetailSchema);
						blPrpJpayRefDetailOld.save(dbpool);
						
						prpJpayRefDetailSchema = blPrpJpayRefDetail.getArr(j);
						prpJpayRefDetailSchema.setPayRefNo(((String[]) arrPayRefNo.get(m))[2]);
						prpJpayRefDetailSchema.setPayRefFee("" + (-1) * dblPayRefFee);
						
						prpJpayRefDetailSchema.setRealPayRefNo(cancelRealPayRefNo);
						prpJpayRefDetailSchema.setPayRefDate(strOperateDate);
						blPrpJpayRefDetailNew = new BLPrpJpayRefDetail();
						blPrpJpayRefDetailNew.setArr(prpJpayRefDetailSchema);
						blPrpJpayRefDetailNew.save(dbpool);
						
						String strWherePartDel = "";
						strWherePartDel = "DELETE FROM PrpJpayRefDetail WHERE PayRefNo='"
								+ payRefNo + "'";
						dbpool.delete(strWherePartDel);
						break;
					}
				}
    			
				if (blPrpJpayRefDetail.getArr(j).getPayWay().substring(0, 1).equals("4")
						&&!blPrpJpayRefDetail.getArr(j).getPayWay().equals("421")) {
					String strPoaCode = blPrpJpayRefDetail.getArr(j).getFlag1();
					DBPrpJpoaMain dbPrpJpoaMainTemp = new DBPrpJpoaMain();
					dbPrpJpoaMainTemp.getInfo(dbpool, strPoaCode);
					int intIoType = 1;
					if (blPrpJpayRefDetail.getArr(j).getPayWay().substring(1,2).equals("1")) {
						intIoType = -1;
					}
					double dblDeliqueeFee = dbPrpJpoaMainTemp.getDeliqueefee()
							- dblPayRefFee * intIoType;
					if (dblDeliqueeFee < 0) {
						throw new UserException(-96, -1167, "BLPrpJpayRefMain.cancelCommissionVoucher()",
							"预存款支付失败：原预存款帐号" + strPoaCode + " 余额不足！");
					}
					dbPrpJpoaMainTemp.setDeliqueefee(dblDeliqueeFee);
					dbPrpJpoaMainTemp.update(dbpool);
				}
			}
      		
			BLPrpJpoaTrace blPrpJpoaTrace = new BLPrpJpoaTrace();
			blPrpJpoaTrace.query(dbpool, strWherePart);
			for (int j = 0; j < blPrpJpoaTrace.getSize(); j++) {
				DBPrpJpoaTrace dbPrpJpoaTrace = new DBPrpJpoaTrace();
				PrpJpoaTraceSchema prpJpoaTraceSchema = new PrpJpoaTraceSchema();
				prpJpoaTraceSchema = blPrpJpoaTrace.getArr(j);
				String SerailnoOld = prpJpoaTraceSchema.getSerailno();
				int maxSerailNo = Integer.parseInt(dbPrpJpoaTrace.getMaxSerailNo(dbpool,
						prpJpoaTraceSchema.getPoacode()));
				prpJpoaTraceSchema.setSerailno(String.valueOf(maxSerailNo + 1));
				prpJpoaTraceSchema.setPayfee(-prpJpoaTraceSchema.getPayfee());
				prpJpoaTraceSchema.setRemark("取消第" + SerailnoOld + "条收付款确认");
				prpJpoaTraceSchema.setInputdate(String.valueOf(new DateTime(new Date(),
						DateTime.YEAR_TO_SECOND)));
				
				prpJpoaTraceSchema.setRealpayrefno(cancelRealPayRefNo);
				dbPrpJpoaTrace.setSchema(prpJpoaTraceSchema);
				dbPrpJpoaTrace.insert(dbpool);
			}
            
            for(int k=0; k<blPrpJpayCommission.getSize(); k++)
            {
              prpJpayCommissionSchema = blPrpJpayCommission.getArr(k);
              dbPrpJplanCommission = new DBPrpJplanCommission();
              dbPayRefFee = Double.parseDouble(prpJpayCommissionSchema.getPayRefFee());
              double dbPlanFee = Double.parseDouble(prpJpayCommissionSchema.getPlanFee());
              strPayRefNo = prpJpayCommissionSchema.getPayRefNo();
              
              int result= dbPrpJplanCommission.getInfo(dbpool,
                                        prpJpayCommissionSchema.getCertiNo(),
                                        prpJpayCommissionSchema.getSerialNo());
              if(result==0){
                 if(Double.parseDouble(dbPrpJplanCommission.getRealPayRefFee())==0){
                          throw new UserException( -96, -1167, "BLPrpJpayRefMain.cancelCommissionVoucher", 
                        		  dbPrpJplanCommission.getCertiNo()+"已经取消收付款确认，不能重复取消！");
                  }
                  dbPrpJplanCommission.setRealPayRefFee("0.00");
                  dbPrpJplanCommission.update(dbpool);
                }
              
              for(int j=0; j<arrPayRefNo.size(); j++)
              {
                if( (((String[])arrPayRefNo.get(j))[0]).equals(strPayRefNo))
                {
                  strPayRefNoOld = ( (String[]) arrPayRefNo.get(j))[1];
                  strPayRefNoNew = ( (String[]) arrPayRefNo.get(j))[2];
                }
              }
              
              
              
              prpJpayCommissionSchema.setPayRefTimes(this.getPayRefTimesNew(dbpool,prpJpayCommissionSchema,"10"));
              prpJpayCommissionSchema.setPayRefNo(strPayRefNoOld);
              
              prpJpayCommissionSchema.setPayItemType("1");
              blPrpJpayCommissionOld = new BLPrpJpayCommission();
              blPrpJpayCommissionOld.setArr(prpJpayCommissionSchema);
              blPrpJpayCommissionOld.save(dbpool);
              
              prpJpayCommissionSchema.setPayRefTimes(this.getPayRefTimesNew(dbpool,prpJpayCommissionSchema,"20"));
              prpJpayCommissionSchema.setPayRefNo(strPayRefNoNew);
              prpJpayCommissionSchema.setPlanFee(""+(-1)*dbPlanFee);
              prpJpayCommissionSchema.setPayRefFee(""+(-1)*dbPayRefFee);
              prpJpayCommissionSchema.setPayRefDate(strOperateDate);
              prpJpayCommissionSchema.setRealPayRefNo(cancelRealPayRefNo);
              prpJpayCommissionSchema.setPayItemType("2");
              blPrpJpayCommissionNew = new BLPrpJpayCommission();
              blPrpJpayCommissionNew.setArr(prpJpayCommissionSchema);
              blPrpJpayCommissionNew.save(dbpool);
              
              
              prpJpayCommissionSchema.setPayRefNo(strPayRefNo);
              prpJpayCommissionSchema.setPayRefTimes("1");
              prpJpayCommissionSchema.setPlanFee(""+dbPlanFee);
              prpJpayCommissionSchema.setPayRefFee(""+dbPayRefFee);
              prpJpayCommissionSchema.setPayRefDate("");
              prpJpayCommissionSchema.setPayRefFlag("0");
              prpJpayCommissionSchema.setPayItemType("0");
              prpJpayCommissionSchema.setPayRefNoFlag("1");
              prpJpayCommissionSchema.setRealPayRefNo("");
              prpJpayCommissionSchema.setVoucherNo("");
              
              dbPrpJpayCommission = new DBPrpJpayCommission();
              dbPrpJpayCommission.setSchema(prpJpayCommissionSchema);
              dbPrpJpayCommission.update(dbpool);                    
              
             }
      		
    		  
    		  BLPrpJpaymentVoucher blPrpJpaymentVoucher = new BLPrpJpaymentVoucher();
    		  String voucherNo = blPrpJpaymentVoucher.callProcedure(dbpool,cancelRealPayRefNo);

              arrVoucherNoNew.add(voucherNo);
            
            }
            dbpool.commitTransaction();
          }
          catch(SQLException sqlexception){
            dbpool.rollbackTransaction();
            sqlexception.printStackTrace();
            throw sqlexception;
          }
          catch(Exception exception){
            dbpool.rollbackTransaction();
            exception.printStackTrace();
            throw exception;
          }
          finally {
            if(cstmt!=null)
          		cstmt.close();
          	if(conn!=null)
          		conn.close();
            dbpool.close();
          }
          return arrVoucherNoNew;
    }
      
      /**
       * @desc 取消收付款确认，生成红冲凭证入口
       * @param iWherepart
       * @param iUserCode
       * @param iComCode
       * @return
       * @throws SQLException
       * @throws Exception
       */
      public Vector cancelVoucher(String iWherepart,String iUserCode,String iComCode) 
        throws SQLException,Exception{
          BLAccVoucher blAccVoucher = new BLAccVoucher();
          BLAccVoucher blAccVoucherNew = null;
          BLPrpJpayRefMain blPrpJpayRefMain = null;
          BLPrpJpayRefMain blPrpJpayRefMainOld = null;
          BLPrpJpayRefMain blPrpJpayRefMainNew = null;
          PrpJpayRefMainSchema prpJpayRefMainSchema = null;
          Bill bill = new Bill();
          ChgDate iDate = new ChgDate();
          String strOperateDate = iDate.getCurrentTime("yyyy-MM-dd");
          int intYear = Integer.parseInt(strOperateDate.substring(0,4));
          DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
          BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
          BLPrpJpayRefRec blPrpJpayRefRecOld = null;
          BLPrpJpayRefRec blPrpJpayRefRecNew = null;
          PrpJpayRefRecSchema prpJpayRefRecSchema = null;
          DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
          DBPrpJplanFee dbPrpJplanFee = null;
          DBPrpCplan dbPrpCplan = new DBPrpCplan();
          DBPrpCPplan dbPrpCPplan = new DBPrpCPplan();
          BLPrpJpayRefDetail blPrpJpayRefDetail = new BLPrpJpayRefDetail();
          BLPrpJpayRefDetail blPrpJpayRefDetailOld = null;
          BLPrpJpayRefDetail blPrpJpayRefDetailNew = null;
          PrpJpayRefDetailSchema prpJpayRefDetailSchema = null;
          Vector arrVoucherNoNew = new Vector();
          ArrayList arrPayRefNo = new ArrayList();
          DbPool dbpool = new DbPool();
          try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE); 
            dbpool.beginTransaction();
            
            this.query(dbpool,iWherepart);
            if(this.getSize()==0){
                throw new UserException( -96, -1167, "BLPrpJpayRefMain.payRefCancel", "没有要取消收付款确认的记录！");
            }
            
            blAccVoucher.getBLAccMainVoucher().queryWithHis(dbpool,iWherepart,0);
            
            HashSet hashSet = new HashSet();
        for(int i=0;i<blAccVoucher.getBLAccMainVoucher().getSize();i++){
        	String checkVoucher =blAccVoucher.getBLAccMainVoucher().getArr(i).getCenterCode()+blAccVoucher.getBLAccMainVoucher().getArr(i).getVoucherNo();
        	if(hashSet.contains(checkVoucher))
        	{
        		continue;
        	}
        	else{
        		hashSet.add(checkVoucher);
        	}       
            
                
                String strCenterCode = blAccVoucher.getBLAccMainVoucher().getArr(i).getCenterCode();
                String strBranchCode = blAccVoucher.getBLAccMainVoucher().getArr(i).getBranchCode();
                String strAccBookType = blAccVoucher.getBLAccMainVoucher().getArr(i).getAccBookType();
                String strAccBookCode = blAccVoucher.getBLAccMainVoucher().getArr(i).getAccBookCode();
                String strYearMonthOld = blAccVoucher.getBLAccMainVoucher().getArr(i).getYearMonth();
                String strVoucherNoOld = blAccVoucher.getBLAccMainVoucher().getArr(i).getVoucherNo();
                
                blAccVoucherNew = new BLAccVoucher();
                String strWherePartCancel = " CenterCode='" + strCenterCode
                                            + "' AND AccBookType='" + strAccBookType
                                            + "' AND AccBookCode='" + strAccBookCode
                                            + "' AND YearMonth='" + strYearMonthOld
                                            + "' AND VoucherNo='" + strVoucherNoOld
                                            + "' ";
                
                blAccVoucherNew.queryAll(dbpool,strWherePartCancel);
                
                String strYearMonth = strOperateDate.substring(0,4)+strOperateDate.substring(5,7);
                
                blAccVoucherNew.getBLAccMainVoucher().getArr(0).setYearMonth(strYearMonth);
                blAccVoucherNew.getBLAccMainVoucher().getArr(0).setVoucherNo("");
                blAccVoucherNew.getBLAccMainVoucher().getArr(0).setVoucherDate(strOperateDate);
                blAccVoucherNew.getBLAccMainVoucher().getArr(0).setOperatorCode(iUserCode);
                blAccVoucherNew.getBLAccMainVoucher().getArr(0).setOperatorBranch(iComCode);
                blAccVoucherNew.getBLAccMainVoucher().getArr(0).setApproverCode("");
                blAccVoucherNew.getBLAccMainVoucher().getArr(0).setApproverBranch("");
                blAccVoucherNew.getBLAccMainVoucher().getArr(0).setApproveDate("");
                blAccVoucherNew.getBLAccMainVoucher().getArr(0).setGeneCode("");
                blAccVoucherNew.getBLAccMainVoucher().getArr(0).setGeneDate("");
                blAccVoucherNew.getBLAccMainVoucher().getArr(0).setVoucherFlag("1");
                
                AccSubVoucherSchema accSubVoucherSchema = null;
                double dbDebitSource = 0;
                double dbCreditSource = 0;
                double dbDebitDest = 0;
                double dbCreditDest = 0;
                String strItemCode = "";
                for(int m=0;m<blAccVoucherNew.getBLAccSubVoucher().getSize();m++){
                    accSubVoucherSchema = blAccVoucherNew.getBLAccSubVoucher().getArr(m);
                    dbDebitSource = Double.parseDouble(accSubVoucherSchema.getDebitSource());
                    dbCreditSource = Double.parseDouble(accSubVoucherSchema.getCreditSource());
                    dbDebitDest = Double.parseDouble(accSubVoucherSchema.getDebitDest());
                    dbCreditDest = Double.parseDouble(accSubVoucherSchema.getCreditDest());
                    blAccVoucherNew.getBLAccSubVoucher().getArr(m).setYearMonth(strYearMonth);
                    blAccVoucherNew.getBLAccSubVoucher().getArr(m).setVoucherNo("");
                    blAccVoucherNew.getBLAccSubVoucher().getArr(m).setRemark("对冲" + strVoucherNoOld + "号凭证");
                    blAccVoucherNew.getBLAccSubVoucher().getArr(m).setDebitSource(""+(-1)*dbDebitSource);
                    blAccVoucherNew.getBLAccSubVoucher().getArr(m).setCreditSource(""+(-1)*dbCreditSource);
                    blAccVoucherNew.getBLAccSubVoucher().getArr(m).setDebitDest(""+(-1)*dbDebitDest);
                    blAccVoucherNew.getBLAccSubVoucher().getArr(m).setCreditDest(""+(-1)*dbCreditDest);
                    
                    if(!accSubVoucherSchema.getItemCode().equals("1001")
                    		&& !accSubVoucherSchema.getItemCode().equals("1002")
                    		&& !accSubVoucherSchema.getItemCode().equals("3001")){
                    	if(!accSubVoucherSchema.getDirectionIdx().equals("00")){
                    		strItemCode = accSubVoucherSchema.getItemCode()+"/"+accSubVoucherSchema.getDirectionIdx();
                    		}
                    	else{
                    		strItemCode = accSubVoucherSchema.getItemCode();
                    	}           
                    }
                }
                
                
                String strWherePart = " CenterCode='" + strCenterCode
                                      + "' AND YearMonth='" + strYearMonthOld
                                      + "' AND VoucherNo='" + strVoucherNoOld
                                      + "' ORDER BY PayRefNo ";
                blPrpJpayRefMain = new BLPrpJpayRefMain();
                blPrpJpayRefMain.query(dbpool,strWherePart,0);

                
                String strPayRefNo = blPrpJpayRefMain.getArr(0).getPayRefNo();
                strWherePart = " PayRefNo IN ('"+strPayRefNo+"'";
                for(int j=1; j<blPrpJpayRefMain.getSize(); j++)
                {
                  if(!blPrpJpayRefMain.getArr(j).getPayRefNo().equals(strPayRefNo))
                  {
                    strPayRefNo = blPrpJpayRefMain.getArr(j).getPayRefNo();
                    strWherePart += ",'"+strPayRefNo+"'";
                  }
                }
                strWherePart += ")";
                blPrpJpayRefRec.query(dbpool,strWherePart,0);
                for(int j=0; j<blPrpJpayRefRec.getSize(); j++)
                {
                  prpJpayRefRecSchema = blPrpJpayRefRec.getArr(j);
                  dbPrpJplanFee = new DBPrpJplanFee();
                  
                  
                  
                 int result= dbPrpJplanFee.getInfoForUpdate(dbpool,
                                        prpJpayRefRecSchema.getCertiType(),
                                        prpJpayRefRecSchema.getCertiNo(),
                                        prpJpayRefRecSchema.getSerialNo(),
                                        prpJpayRefRecSchema.getPayRefReason());
                 
                 
                 
                 if(result==0){
                	  if(Double.parseDouble(dbPrpJplanFee.getRealPayRefFee())==0){
                          throw new UserException( -96, -1167, "BLPrpJpayRefMain.cancelVoucher", 
                        		  dbPrpJplanFee.getCertiNo()+"已经取消收付款确认，不能重复取消！");
                      }
                      dbPrpJplanFee.setRealPayRefFee("0");
                      
                      if(PubTools.compareDate(prpJpayRefRecSchema.getStartDate(), prpJpayRefRecSchema.getPayRefDate()) > 0 
                    		  &&  PubTools.compareDate(prpJpayRefRecSchema.getStartDate(), strOperateDate) < 0
                    		  && !prpJpayRefRecSchema.getRiskCode().equals("YAB0")
                    		  
                    		  
                    		  && (prpJpayRefRecSchema.getCertiType().equals("P")||prpJpayRefRecSchema.getCertiType().equals("E"))
                    		  && !prpJpayRefRecSchema.getPayRefReason().equals("R72")
                    		  && !prpJpayRefRecSchema.getPayRefReason().equals("R73")
                    		  && !prpJpayRefRecSchema.getPayRefReason().equals("R74")){
                          
                          accSubVoucherSchema = new AccSubVoucherSchema();
                          accSubVoucherSchema.setSchema(blAccVoucherNew.getBLAccSubVoucher().getArr(0));
                          accSubVoucherSchema.setCurrency(prpJpayRefRecSchema.getCurrency1());
                          accSubVoucherSchema.setF01(prpJpayRefRecSchema.getClassCode());
                          
                          if(dbPrpJplanFee.getRiskCode().equals("0507")){
                              accSubVoucherSchema.setF05(prpJpayRefRecSchema.getRiskCode()+prpJpayRefRecSchema.getCarNatureCode());                    
                          }else{
                              accSubVoucherSchema.setF05(prpJpayRefRecSchema.getRiskCode());                    
                          }
                          accSubVoucherSchema.setF28(prpJpayRefRecSchema.getComCode());
                          accSubVoucherSchema.setF27(prpJpayRefRecSchema.getHandler1Code());
                          accSubVoucherSchema.setRemark("对冲应收转预收凭证");
                          accSubVoucherSchema.setItemCode("2203");
                          if(prpJpayRefRecSchema.getPayRefReason().equals("R00")){
                        	  accSubVoucherSchema.setDirectionIdx("01/");
                          }else{
                        	  accSubVoucherSchema.setDirectionIdx("02/");
                          }                   
                          accSubVoucherSchema.setExchangeRate(dbPrpJplanFee.getExchangeRate());                     
                          accSubVoucherSchema.setDebitSource(""+(-1)*Double.parseDouble(prpJpayRefRecSchema.getPlanFee()));
                          accSubVoucherSchema.setDebitDest(""+(-1)*Double.parseDouble(prpJpayRefRecSchema.getPlanFee())*Double.parseDouble(dbPrpJplanFee.getExchangeRate()));                     
                          accSubVoucherSchema.setCreditSource("" + 0);
                          accSubVoucherSchema.setCreditDest("" + 0);
                          blAccVoucherNew.getBLAccSubVoucher().setArr(accSubVoucherSchema);
                        
                        accSubVoucherSchema = new AccSubVoucherSchema();
                        accSubVoucherSchema.setSchema(blAccVoucherNew.getBLAccSubVoucher().getArr(0));
                        accSubVoucherSchema.setCurrency(prpJpayRefRecSchema.getCurrency1());
                        accSubVoucherSchema.setF01(prpJpayRefRecSchema.getClassCode());
                        
                        if(dbPrpJplanFee.getRiskCode().equals("0507")){
                            accSubVoucherSchema.setF05(prpJpayRefRecSchema.getRiskCode()+prpJpayRefRecSchema.getCarNatureCode());                    
                        }else{
                            accSubVoucherSchema.setF05(prpJpayRefRecSchema.getRiskCode());                    
                        }
                        accSubVoucherSchema.setF28(prpJpayRefRecSchema.getComCode());
                        accSubVoucherSchema.setF27(prpJpayRefRecSchema.getHandler1Code());
                        accSubVoucherSchema.setRemark("对冲应收转预收凭证");
                        accSubVoucherSchema.setItemCode("1122");
                        accSubVoucherSchema.setDirectionIdx("00");
                        accSubVoucherSchema.setExchangeRate(dbPrpJplanFee.getExchangeRate());                  
                        accSubVoucherSchema.setDebitSource(prpJpayRefRecSchema.getPlanFee());
                        accSubVoucherSchema.setDebitDest(""+Double.parseDouble(prpJpayRefRecSchema.getPlanFee())*Double.parseDouble(dbPrpJplanFee.getExchangeRate()));    
                        accSubVoucherSchema.setCreditSource(""+0);
                        accSubVoucherSchema.setCreditDest(""+0);
                        blAccVoucherNew.getBLAccSubVoucher().setArr(accSubVoucherSchema);                   
                      }
                      
                      dbPrpJplanFee.update(dbpool);
                      
                      
                      if(prpJpayRefRecSchema.getRiskCode().equals("0507")
                    		  && (prpJpayRefRecSchema.getPayRefReason().equals("R72")
                            		  || prpJpayRefRecSchema.getPayRefReason().equals("R73")
                            		  || prpJpayRefRecSchema.getPayRefReason().equals("R74")
                            		  || prpJpayRefRecSchema.getCertiType().equals("P")))
                      {
                    	  BLPrpJtaxSettle blPrpJtaxSettle = new BLPrpJtaxSettle();
	                      DBPrpJtaxSettle dbPrpJtaxSettle = new DBPrpJtaxSettle();
                    	  String sql = " policyno='"+prpJpayRefRecSchema.getPolicyNo()+
  	  										"' and serialno='"+prpJpayRefRecSchema.getSerialNo()+"'";
				      	  blPrpJtaxSettle.query(dbpool,sql);
				      	  for(int m=0;m<blPrpJtaxSettle.getSize();m++)
				      	  {
				      		  if(blPrpJtaxSettle.getArr(m).getTaxSettleFlag().equals("1"))
				      		  {
				      			throw new UserException( -96, -1167, "BLPrpJpayRefMain.VoucherVerify",
					          			  "XX："+prpJpayRefRecSchema.getPolicyNo()+"已经结算，不能再取消收付款确认！");
				      		  }
				      		  dbPrpJtaxSettle.setSchema(blPrpJtaxSettle.getArr(m));
				      		  if(prpJpayRefRecSchema.getCertiType().equals("P")  
		            				  && !prpJpayRefRecSchema.getPayRefReason().equals("R72")
		                    		  && !prpJpayRefRecSchema.getPayRefReason().equals("R73")
		                    		  && !prpJpayRefRecSchema.getPayRefReason().equals("R74")
		                    		  && !dbPrpJtaxSettle.getTaxRelifFlag().equals("2") 
		                    		  && !dbPrpJtaxSettle.getTaxRelifFlag().equals("4")
		                    		  && !dbPrpJtaxSettle.getTaxRelifFlag().equals("5"))
		            		  {
		            			  continue;
		            		  }
				      		  if(dbPrpJtaxSettle.getPayRefReason().equals(prpJpayRefRecSchema.getPayRefReason()))
				      		  {
				      			dbPrpJtaxSettle.setPayRefFee(""+(Double.parseDouble(dbPrpJtaxSettle.getPayRefFee()) - 
		                    			  Double.parseDouble(prpJpayRefRecSchema.getPlanFee())));
				      		  }
	                    	  dbPrpJtaxSettle.setPayRefDate("2100-01-01");
	                    	  
				          	  dbPrpJtaxSettle.update(dbpool);
				      	  }
                      }
                      
                      
                      
                      if (prpJpayRefRecSchema.getCertiType().equals("P")) {
                      	if(prpJpayRefRecSchema.getPayRefReason().equals("R82")||
                             prpJpayRefRecSchema.getPayRefReason().equals("R42")||
                             prpJpayRefRecSchema.getPayRefReason().equals("P82")||
                             prpJpayRefRecSchema.getPayRefReason().equals("P92")||
                      	     prpJpayRefRecSchema.getPayRefReason().equals("P81")||
                             prpJpayRefRecSchema.getPayRefReason().equals("R81")||
                             prpJpayRefRecSchema.getPayRefReason().equals("R72")||
                             prpJpayRefRecSchema.getPayRefReason().equals("R73")||
                             prpJpayRefRecSchema.getPayRefReason().equals("R74")
                         )
                      	{                
                      	}
                      	else{
                            
                            if (dbPrpCplan.getInfo(dbpool,
                                               prpJpayRefRecSchema.getCertiNo(),
                                               prpJpayRefRecSchema.getSerialNo()) == 0) {                	  
                          dbPrpCplan.setDelinquentFee(dbPrpJplanFee.getPlanFee());
                          dbPrpCplan.update(dbpool);
                        }
                        
                        if (dbPrpCPplan.getInfo(dbpool,
                                                prpJpayRefRecSchema.getCertiNo(),
                                                prpJpayRefRecSchema.getSerialNo()) == 0) {             	  
                          dbPrpCPplan.setDelinquentFee(dbPrpJplanFee.getPlanFee());
                          dbPrpCPplan.update(dbpool);
                         }
                        }
                      }
                      else if (prpJpayRefRecSchema.getCertiType().equals("E")) {
                      	if(prpJpayRefRecSchema.getPayRefReason().equals("R82")||
                           prpJpayRefRecSchema.getPayRefReason().equals("R42")||
                           prpJpayRefRecSchema.getPayRefReason().equals("P82")||
                           prpJpayRefRecSchema.getPayRefReason().equals("P92")||
                           prpJpayRefRecSchema.getPayRefReason().equals("P81")||
                           prpJpayRefRecSchema.getPayRefReason().equals("R81")||
                           prpJpayRefRecSchema.getPayRefReason().equals("R72")||
                           prpJpayRefRecSchema.getPayRefReason().equals("R73")||
                           prpJpayRefRecSchema.getPayRefReason().equals("R74")){                                 		
                      	}
                      	else{                	
                      		BLPrpCplan blPrpCplan = new BLPrpCplan();
                      		String strWhere = " EndorseNo='" + prpJpayRefRecSchema.getCertiNo() + "' ";
                      		blPrpCplan.query(dbpool, strWhere, 0);
                      		for(int k=0; k<blPrpCplan.getSize(); k++){   
                      			dbPrpCplan.setSchema(blPrpCplan.getArr(k));                     
                      			dbPrpCplan.setDelinquentFee(dbPrpJplanFee.getPlanFee());
                      			dbPrpCplan.update(dbpool);
                      		}
                      		BLPrpCPplan blPrpCPplan = new BLPrpCPplan();
                      		blPrpCPplan.query(dbpool, strWhere, 0);
                      		for(int k=0; k<blPrpCPplan.getSize(); k++){
                      			dbPrpCPplan.setSchema(blPrpCPplan.getArr(k));
                      			dbPrpCPplan.setDelinquentFee(dbPrpJplanFee.getPlanFee());
                      			dbPrpCPplan.update(dbpool);
                      	    }
                      		}
                      }
                  }
                
                }
                
                
                
                for(int n=0; n<blAccVoucherNew.getBLAccSubVoucher().getSize(); n++){
                	if(Double.parseDouble(blAccVoucherNew.getBLAccSubVoucher().getArr(n).getCreditDest())==0&&
                            Double.parseDouble(blAccVoucherNew.getBLAccSubVoucher().getArr(n).getDebitDest())==0){
                        blAccVoucherNew.getBLAccSubVoucher().remove(n);	
                        n--;
                	}
                }
                for(int n=0; n<blAccVoucherNew.getBLAccSubVoucher().getSize(); n++){
                	blAccVoucherNew.getBLAccSubVoucher().getArr(n).setSuffixNo("" + (n + 1));
                    blAccVoucherNew.getBLAccSubVoucher().createRawArticle(blAccVoucherNew.getBLAccSubVoucher().getArr(n),strItemCode);
                }   
                
                blAccVoucherNew.getBLAccSubVoucher().voucherComp("101");                          
                
                blAccVoucherNew.getBLAccSubVoucher().voucherOrder();
                
                blAccVoucherNew.setBLAccVoucherArticle(blAccVoucherNew.getBLAccSubVoucher().genVoucherArticle());
                
                blAccVoucherNew.save(dbpool);
                
                String strVoucherNoNew = blAccVoucherNew.getBLAccMainVoucher().getArr(0).getVoucherNo();
                String strPayRefNoPre = "";
                String strPayRefNoOld = "";
                String strPayRefNoNew = "";
                double dbPayRefFee = 0;
                String billNo1 = "";
                String billNo2 = "";
                
                
                DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
                String realPayRefNo = dbPrpJpayRefVoucher.genRealPayRefNo();
                dbPrpJpayRefVoucher.setRealPayRefNo(realPayRefNo);
                dbPrpJpayRefVoucher.setPayRefNoType(this.getArr(0).getPayRefNoType());
                dbPrpJpayRefVoucher.setCenterCode(strCenterCode);
                dbPrpJpayRefVoucher.setVoucherDate(blPrpJpayRefMain.getArr(0).getPayRefDate());
                dbPrpJpayRefVoucher.setComCode(blPrpJpayRefMain.getArr(0).getPayRefUnit());
                dbPrpJpayRefVoucher.setPayRefCode(blPrpJpayRefMain.getArr(0).getPayRefCode());
                dbPrpJpayRefVoucher.insert(dbpool);
                
                for(int j=0;j<blPrpJpayRefMain.getSize();j++){
                    prpJpayRefMainSchema = blPrpJpayRefMain.getArr(j);
                    strPayRefNo = prpJpayRefMainSchema.getPayRefNo();
                    dbPayRefFee = Double.parseDouble(prpJpayRefMainSchema.getPayRefFee());
                    
                    
                    if(!strPayRefNo.equals(strPayRefNoPre)){
                        billNo1 = bill.getNo("prpjpayrec","0501",prpJpayRefMainSchema.getPackageUnit(),intYear,"00");
                        billNo2 = bill.getNo("prpjpayrec","0501",prpJpayRefMainSchema.getPackageUnit(),intYear,"00");
                    }
                    strPayRefNoOld = blPrpJpayRefRec.createPayRefNo(billNo1,"0501",prpJpayRefMainSchema.getPackageUnit());
                    prpJpayRefMainSchema.setPayRefNo(strPayRefNoOld);
                    prpJpayRefMainSchema.setFlag("1"); 
                    blPrpJpayRefMainOld = new BLPrpJpayRefMain();
                    blPrpJpayRefMainOld.setArr(prpJpayRefMainSchema);
                    blPrpJpayRefMainOld.save(dbpool);
                    
                    strPayRefNoNew = blPrpJpayRefRec.createPayRefNo(billNo2,"0501",prpJpayRefMainSchema.getPackageUnit());
                    prpJpayRefMainSchema = blPrpJpayRefMain.getArr(j);
                    prpJpayRefMainSchema.setPayRefNo(strPayRefNoNew);
                    prpJpayRefMainSchema.setPayRefFee(""+(-1)*dbPayRefFee);
                    prpJpayRefMainSchema.setCenterCode(strCenterCode);
                    prpJpayRefMainSchema.setBranchCode(strBranchCode);
                    prpJpayRefMainSchema.setAccBookType(strAccBookType);
                    prpJpayRefMainSchema.setAccBookCode(strAccBookCode);
                    prpJpayRefMainSchema.setYearMonth(strYearMonth);
                    prpJpayRefMainSchema.setVoucherNo(strVoucherNoNew);
                    prpJpayRefMainSchema.setPayRefDate(strOperateDate);
                    prpJpayRefMainSchema.setPayRefCode(iUserCode);
                    prpJpayRefMainSchema.setPayRefUnit(iComCode);
                    
                    prpJpayRefMainSchema.setRealPayRefNo(realPayRefNo);
                    blPrpJpayRefMainNew = new BLPrpJpayRefMain();
                    blPrpJpayRefMainNew.setArr(prpJpayRefMainSchema);
                    blPrpJpayRefMainNew.save(dbpool);
                    
                    prpJpayRefMainSchema.setPayRefNo(strPayRefNo);
                    prpJpayRefMainSchema.setPayRefFee(""+dbPayRefFee);
                    prpJpayRefMainSchema.setPayRefCode("");
                    prpJpayRefMainSchema.setPayRefUnit("");
                    prpJpayRefMainSchema.setPayRefDate("");
                    prpJpayRefMainSchema.setAccBookType("");
                    prpJpayRefMainSchema.setAccBookCode("");
                    prpJpayRefMainSchema.setBranchCode("");
                    prpJpayRefMainSchema.setYearMonth("");
                    prpJpayRefMainSchema.setVoucherNo("");
                    prpJpayRefMainSchema.setFlag("");
                    
                    prpJpayRefMainSchema.setRealPayRefNo("");
                    dbPrpJpayRefMain.setSchema(prpJpayRefMainSchema);
                    dbPrpJpayRefMain.update(dbpool);
                    if(strPayRefNo.equals(strPayRefNoPre)){
                      continue;
                    }
                    strWherePart = " PayRefNo='"+strPayRefNo+"'";
                    
                    blPrpJpayRefDetail.query(dbpool,strWherePart,0);
                    for(int k=0;k<blPrpJpayRefDetail.getSize();k++)
                    {
                        prpJpayRefDetailSchema = blPrpJpayRefDetail.getArr(k);
                        dbPayRefFee = Double.parseDouble(prpJpayRefDetailSchema.getPayRefFee());
                        
                        prpJpayRefDetailSchema.setPayRefNo(strPayRefNoOld);
                        blPrpJpayRefDetailOld = new BLPrpJpayRefDetail();
                        blPrpJpayRefDetailOld.setArr(prpJpayRefDetailSchema);
                        blPrpJpayRefDetailOld.save(dbpool);
                        
                        prpJpayRefDetailSchema = blPrpJpayRefDetail.getArr(k);
                        prpJpayRefDetailSchema.setPayRefNo(strPayRefNoNew);
                        prpJpayRefDetailSchema.setPayRefFee(""+(-1)*dbPayRefFee);
                        
                        prpJpayRefDetailSchema.setRealPayRefNo(realPayRefNo);
                        blPrpJpayRefDetailNew = new BLPrpJpayRefDetail();
                        blPrpJpayRefDetailNew.setArr(prpJpayRefDetailSchema);
                        blPrpJpayRefDetailNew.save(dbpool);
                        
                        String strWherePartDel = "";
                        strWherePartDel = "DELETE FROM PrpJpayRefDetail WHERE PayRefNo='" + strPayRefNo + "'";
                        dbpool.delete(strWherePartDel);
                    }
                    arrPayRefNo.add(new String[]{strPayRefNo,strPayRefNoOld,strPayRefNoNew,billNo1,billNo2});
                    strPayRefNoPre = strPayRefNo;
                }
                
                BLPrpJsettle blPrpJSettleTmp = new BLPrpJsettle();
                
                for(int k=0;k<blPrpJpayRefRec.getSize();k++)
                {
                    prpJpayRefRecSchema = blPrpJpayRefRec.getArr(k);
                    dbPayRefFee = Double.parseDouble(prpJpayRefRecSchema.getPayRefFee());
                    strPayRefNo = prpJpayRefRecSchema.getPayRefNo();
                    for(int j=0; j<arrPayRefNo.size(); j++)
                    {
                      if( (((String[])arrPayRefNo.get(i))[0]).equals(strPayRefNo))
                      {
                        strPayRefNoOld = ( (String[]) arrPayRefNo.get(i))[1];
                        strPayRefNoNew = ( (String[]) arrPayRefNo.get(i))[2];
                      }
                    }
                    
                    
                    String flagTmp = prpJpayRefRecSchema.getFlag();
                    prpJpayRefRecSchema.setPayRefTimes(this.getPayRefTimes(dbpool,prpJpayRefRecSchema,"10"));
                    prpJpayRefRecSchema.setPayRefNo(strPayRefNoOld);
                    prpJpayRefRecSchema.setFlag(flagTmp+"R");
                    blPrpJpayRefRecOld = new BLPrpJpayRefRec();
                    blPrpJpayRefRecOld.setArr(prpJpayRefRecSchema);
                    blPrpJpayRefRecOld.save(dbpool);
                    
                    
                    
                    prpJpayRefRecSchema.setPayRefTimes(this.getPayRefTimes(dbpool,prpJpayRefRecSchema,"20"));
                    prpJpayRefRecSchema.setPayRefNo(strPayRefNoNew);
                    prpJpayRefRecSchema.setPayRefFee(""+(-1)*dbPayRefFee);
                    prpJpayRefRecSchema.setPayRefDate(strOperateDate);
                    
                    
                    if(prpJpayRefRecSchema.getCertiType().equals("C")
                  		  && prpJpayRefRecSchema.getClassCode().equals("05")
                  		  && prpJpayRefRecSchema.getPayRefReason().equals("P60")){
                  	  this.callClaimProcedure(dbpool,prpJpayRefRecSchema.getClaimNo(),prpJpayRefRecSchema.getCertiNo(),
                  			Double.parseDouble(prpJpayRefRecSchema.getPayRefFee()),strOperateDate,
                  			  dbPrpJpayRefVoucher.getPayRefCode(),dbPrpJpayRefVoucher.getComCode(),
                  			  prpJpayRefRecSchema.getClassCode(),prpJpayRefRecSchema.getRiskCode(),
                  			  prpJpayRefRecSchema.getHandler1Code(),strCenterCode);
                    }
                    
                    
                    prpJpayRefRecSchema.setRealPayRefNo(realPayRefNo);
                    
                    prpJpayRefRecSchema.setVoucherNo(strVoucherNoNew);
                    blPrpJpayRefRecNew = new BLPrpJpayRefRec();
                    blPrpJpayRefRecNew.setArr(prpJpayRefRecSchema);
                    blPrpJpayRefRecNew.save(dbpool);
                    
                    
                    if(prpJpayRefRecSchema.getCertiType().equals("P")||prpJpayRefRecSchema.getCertiType().equals("E")){
                    	if(!prpJpayRefRecSchema.getPayRefReason().equals("R72")
                        		&&!prpJpayRefRecSchema.getPayRefReason().equals("R73")
                        		&&!prpJpayRefRecSchema.getPayRefReason().equals("R74"))
                        {
                    		BLPrpJmanageFee blPrpJmanageFee = new BLPrpJmanageFee();
                    		blPrpJmanageFee.transCancelVoucher(dbpool,prpJpayRefRecSchema); 
                        }
                    }                                       
                    
                    
                    if((prpJpayRefRecSchema.getCertiType().equals("P")||prpJpayRefRecSchema.getCertiType().equals("E"))
                          && prpJpayRefRecSchema.getCoinsFlag().equals("1")
                          && prpJpayRefRecSchema.getCoinsType().equals("1")){
                        if(prpJpayRefRecSchema.getPayRefReason().substring(1,3).equals("81")){
                          dbPrpJpayRefRec = new DBPrpJpayRefRec();
                          dbPrpJpayRefRec.delete(dbpool,prpJpayRefRecSchema.getCertiType(),
                                                  prpJpayRefRecSchema.getCertiNo(),
                                                  prpJpayRefRecSchema.getSerialNo(),
                                                  prpJpayRefRecSchema.getPayRefReason(),
                                                  "1"); 
                          continue;
                        }else{
                            DBPrpJplanFee dbPrpJplanFeeCoins1 = new DBPrpJplanFee();
                            dbPrpJplanFeeCoins1.getInfo(dbpool,prpJpayRefRecSchema.getCertiType(),
                                                         prpJpayRefRecSchema.getCertiNo(),
                                                         prpJpayRefRecSchema.getSerialNo(),
                                                         prpJpayRefRecSchema.getPayRefReason());
                            prpJpayRefRecSchema.setPayRefNo(strPayRefNo);
                            prpJpayRefRecSchema.setPayRefTimes("1");
                            prpJpayRefRecSchema.setPlanFee(dbPrpJplanFeeCoins1.getPlanFee());
                            prpJpayRefRecSchema.setPayRefFee(dbPrpJplanFeeCoins1.getPlanFee());
                            prpJpayRefRecSchema.setPayRefDate("");
                            
                            prpJpayRefRecSchema.setIdentifyType("0");
                            prpJpayRefRecSchema.setIdentifyNumber("1");
                            
                            
                            
                            prpJpayRefRecSchema.setFlag(flagTmp);
                            dbPrpJpayRefRec = new DBPrpJpayRefRec();
                            
                            prpJpayRefRecSchema.setRealPayRefNo("");
                            
                            prpJpayRefRecSchema.setVoucherNo("");
                            dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
                            dbPrpJpayRefRec.update(dbpool);                                              
                        }
                    }else{
                        prpJpayRefRecSchema.setPayRefNo(strPayRefNo);
                        prpJpayRefRecSchema.setPayRefTimes("1");
                        prpJpayRefRecSchema.setPayRefFee(""+dbPayRefFee);
                        prpJpayRefRecSchema.setPayRefDate("");
                        
                        prpJpayRefRecSchema.setIdentifyType("0");
                        prpJpayRefRecSchema.setIdentifyNumber("1");
                        
                        
                        
                        prpJpayRefRecSchema.setFlag(flagTmp);
                        dbPrpJpayRefRec = new DBPrpJpayRefRec();
                        
                        prpJpayRefRecSchema.setRealPayRefNo("");
                        
                        prpJpayRefRecSchema.setVoucherNo("");
                        dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
                        dbPrpJpayRefRec.update(dbpool);                    
                    }
                    
                    
                    boolean flag = true;
                    if(prpJpayRefRecSchema.getRiskCode().equals("YAB0")){
                    	BLPrpJsettle blPrpJsettle = new BLPrpJsettle();
                    	strWherePart = " PolicyNo='"+blPrpJpayRefRec.getArr(k).getPolicyNo()+
                    	"' AND Currency2='"+blPrpJpayRefRec.getArr(k).getCurrency2()+"'";
                    	blPrpJsettle.query(strWherePart);
                    	if(blPrpJsettle.getSize() == 0)
                    	{
                    		continue;
                    	}else
                    	{
      					  for(int j=0;j<blPrpJSettleTmp.getSize();j++)
    					  {
    						  if(prpJpayRefRecSchema.getPolicyNo().equals(blPrpJSettleTmp.getArr(j).getPolicyNo())
    								  &&prpJpayRefRecSchema.getCurrency2().equals(blPrpJSettleTmp.getArr(j).getCurrency2()))
    						  {
    							  blPrpJSettleTmp.getArr(j).setPlanFee(""+(Double.parseDouble(blPrpJSettleTmp.getArr(j).getPlanFee())
    							  			-Double.parseDouble(prpJpayRefRecSchema.getPlanFee())));
    							  blPrpJSettleTmp.getArr(j).setLeftSettleFee(""+(Double.parseDouble(blPrpJSettleTmp.getArr(j).getLeftSettleFee())
    							  			-Double.parseDouble(prpJpayRefRecSchema.getPayRefFee())));
    							  flag = false;
    							  break;
    						  } else
    						  {
    							  flag = true;
    						  }
    					  }
    					  if(flag)
    					  {
    						  blPrpJsettle.getArr(0).setPlanFee(""+(Double.parseDouble(blPrpJsettle.getArr(0).getPlanFee())
						  			-Double.parseDouble(prpJpayRefRecSchema.getPayRefFee())));
    						  blPrpJsettle.getArr(0).setLeftSettleFee(""+(Double.parseDouble(blPrpJsettle.getArr(0).getLeftSettleFee())
  						  			-Double.parseDouble(prpJpayRefRecSchema.getPayRefFee())));
    						  blPrpJSettleTmp.setArr(blPrpJsettle.getArr(0));
    					  }
                    }
                    }
        			
        			  
        			  if(prpJpayRefRecSchema.getCertiType().equals("P")||
        					prpJpayRefRecSchema.getCertiType().equals("E")){
        				  
        				StringBuffer SQLBuffer =null;
        				BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
        				DBPrpJplanCommission dbPrpJplanCommission = null;
        				
        				SQLBuffer =new StringBuffer();
        				SQLBuffer.append(" CertiNo='"+prpJpayRefRecSchema.getCertiNo()+"'");
        				SQLBuffer.append(" And Serialno='"+prpJpayRefRecSchema.getSerialNo()+"'");
        				SQLBuffer.append(" And PayRefReason ='P90'");
        				blPrpJplanCommission.query(dbpool, SQLBuffer.toString(), 0);
        				
          	  			for (int n = 0 ;n<blPrpJplanCommission.getSize();n++){
        	  				dbPrpJplanCommission = new DBPrpJplanCommission();
        					dbPrpJplanCommission.setSchema(blPrpJplanCommission.getArr(n));
        					if(!dbPrpJplanCommission.getRealPayRefFee().equals(dbPrpJplanCommission.getPlanFee()))
        					{
            	  				dbPrpJplanCommission.setPoliPayRefDate("");  		
            	  				dbPrpJplanCommission.setPayFlag("0");
        					}
        	  				dbPrpJplanCommission.setRealRefPremium("0.00");
        	  				dbPrpJplanCommission.update(dbpool);
        				}
        				
        				
        				SQLBuffer =new StringBuffer();
        				SQLBuffer.append(" PolicyNo='"+prpJpayRefRecSchema.getPolicyNo()+"'");
        				SQLBuffer.append(" And Serialno='"+prpJpayRefRecSchema.getSerialNo()+"'");
        				SQLBuffer.append(" And PayRefReason ='P93'");
        				blPrpJplanCommission.query(dbpool, SQLBuffer.toString(), 0);
        				
          	  			for (int n = 0 ;n<blPrpJplanCommission.getSize();n++){
        					dbPrpJplanCommission = new DBPrpJplanCommission();
        	  				dbPrpJplanCommission.setSchema(blPrpJplanCommission.getArr(n));
        					if(!dbPrpJplanCommission.getRealPayRefFee().equals(dbPrpJplanCommission.getPlanFee()))
        					{
            	  				dbPrpJplanCommission.setPoliPayRefDate("");		
            	  				dbPrpJplanCommission.setPayFlag("0");
        					}
        	  				dbPrpJplanCommission.setRealRefPremium("0.00");
        	  				dbPrpJplanCommission.update(dbpool);
        				}
        			  }
        			  
                }

                
    			  for(int j=0;j<blPrpJSettleTmp.getSize();j++)
  			  {
    				DBPrpJsettle dbPrpJsettle = new DBPrpJsettle();
    				dbPrpJsettle.setSchema(blPrpJSettleTmp.getArr(j));
    				dbPrpJsettle.update();
  			  }

                arrVoucherNoNew.add(strVoucherNoNew);
            }
            dbpool.commitTransaction();
            for(int i=0; i<arrPayRefNo.size(); i++)
            {
              bill.deleteNo("prpjpayrec", ((String[])arrPayRefNo.get(i))[2]);
              bill.deleteNo("prpjpayrec", ((String[])arrPayRefNo.get(i))[3]);
            }
          }
          catch(SQLException sqlexception){
            dbpool.rollbackTransaction();
            sqlexception.printStackTrace();
            for(int i=0; i<arrPayRefNo.size(); i++)
            {
              bill.putNo("prpjpayrec", ((String[])arrPayRefNo.get(i))[2]);
              bill.putNo("prpjpayrec", ((String[])arrPayRefNo.get(i))[3]);
            }
            throw sqlexception;
          }
          catch(Exception exception){
            dbpool.rollbackTransaction();
            exception.printStackTrace();
            for(int i=0; i<arrPayRefNo.size(); i++)
            {
              bill.putNo("prpjpayrec", ((String[])arrPayRefNo.get(i))[2]);
              bill.putNo("prpjpayrec", ((String[])arrPayRefNo.get(i))[3]);
            }
            throw exception;
          }
          finally {
            dbpool.close();
          }
          return arrVoucherNoNew;
    }
      
      /**
       *按照查询条件得到一组记录数，并取得改支付单的应付币种   zhanglingjian 20071018
       *@param iWherePart 查询条件(包括排序字句)
       *@throws UserException
       *@throws Exception
       */
      public void queryPayRefNo(String iWherePart) throws UserException,Exception
      {
        this.queryPayRefNo(iWherePart,0);
      }

      /**
       *按照查询条件和记录数限制得到一组记录数，并取得改支付单的应付币种   zhanglingjian 20071018
       *@param iWherePart 查询条件(包括排序字句)
       *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
       *@throws UserException
       *@throws Exception
       */
      public void queryPayRefNo(String iWherePart,int iLimitCount) throws UserException,Exception
      {
        String strSqlStatement = "";
        DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
        if (iLimitCount > 0 && dbPrpJpayRefMain.getCount(iWherePart) > iLimitCount)
        {
          throw new UserException(-98,-1003,"BLPrpJpayRefMain.query");
        }
        else
        {
          initArr();
          strSqlStatement = " SELECT * FROM PrpJpayRefMain WHERE " + iWherePart;
          schemas = dbPrpJpayRefMain.findByConditions(strSqlStatement);
          this.findCurrency1();
        }
      }
      

      /**
       * 根据SQL语句获取结果集,查询应付币种和手续费个数
       * @param strSQL  SQL语句
       * @return Vector 查询结果记录集
       * @throws SQLException    数据库操作异常类
       * @throws NamingException 名字异常类
       */
      public void findCurrency1() throws
          Exception,SQLException,NamingException{
          DbPool dbpool = new DbPool();
          
          try {
              dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
              findCurrency1(dbpool);
          }
          catch(SQLException sqlException){
              throw sqlException;
          }
          catch(NamingException namingException){
              throw namingException;
          }
          finally {
              dbpool.close();
          }
      }

      /**
       * 根据SQL语句获取结果集
       * @param dbpool  数据库连接池
       * @param strSQL  SQL语句
       * @return Vector 查询结果记录集
       * @throws SQLException    数据库操作异常类
       * @throws NamingException 名字异常类
       */
      public void findCurrency1(DbPool dbpool) throws
          SQLException,NamingException,Exception{
          for(int i = 0;i < this.getSize();i++)
          {
        	  
        	  String strSQL1 = " SELECT currency1,exchangerate FROM prpjpayCommission WHERE payrefno='"+this.getArr(i).getPayRefNo()+"' and rownum=1 " ;
        	  String strSQL2 = " SELECT count(1) FROM prpjpayCommission WHERE payrefno='"+this.getArr(i).getPayRefNo()+"' " ;
        	  
              ResultSet resultSet = null;
              ResultSet resultSet1 = null;
              try{
                  resultSet = dbpool.query(strSQL1);
                  if(resultSet.next()){
                	  this.getArr(i).setCurrency1(resultSet.getString("Currency1"));
                	  this.getArr(i).setExchangeRate(resultSet.getString("ExchangeRate"));
                  }
                  resultSet.close();
                  resultSet1 = dbpool.query(strSQL2);
                  if(resultSet1.next()){
                  }
                  this.getArr(i).setFeeCount(""+resultSet1.getInt(1));
                  resultSet1.close();
              }catch(Exception e){
              	e.printStackTrace();
              	throw e;
              }finally {
            	  if(resultSet != null)
            	  {
            		  resultSet.close();
            	  }
            	  if(resultSet1 != null)
            	  {
            		  resultSet1.close();
            	  }
              }
          }
      }
      
      /**
       * 调用XXXXX接口
       * @param dbpool数据库连接池
       * @param 
       * @return 
       * @throws 异常类
       */
      public void callClaimProcedure(DbPool dbpool,String claimNo,String compensateNo,double amount,String payDate,
                      String payerCode,String paycomCode,String classCode,String riskCode,String handlerCode,String centerCode)
                      throws Exception {
              Connection conn = null;
              CallableStatement cstmt = null;
              conn = this.getConnection(dbpool);
              cstmt = conn.prepareCall("{call claimrealpay_proc(?,?,?,?,?,?,?,?,?,?)}");
              cstmt.setString(1, claimNo);
              cstmt.setString(2, compensateNo);
              cstmt.setDouble(3, amount);
              cstmt.setString(4, payDate);
              cstmt.setString(5, payerCode);
              cstmt.setString(6, paycomCode);
              cstmt.setString(7, classCode);
              cstmt.setString(8, riskCode);
              cstmt.setString(9, handlerCode);
              cstmt.setString(10, centerCode);
              cstmt.execute();
      }
        /**
         * @desc 取消收付款确认，生成红冲凭证入口   zhanglingjian 20071211
         * @param arrVoucherNo  需要取消的凭证号数组
         * @param arrCenterCode 核算单位
         * @param arrAccBookType 账套类型
         * @param arrAccBookCode 账套代码
         * @param iUserCode
         * @param iComCode
         * @return
         * @throws SQLException
         * @throws Exception
         */
        public Vector cancelVoucherNew(Vector arrRealPayRefNo,Vector arrCenterCode,
        		String iUserCode,String iComCode)
          throws SQLException,Exception{
        	BLPrpJpayRefMain blPrpJpayRefMain = null;
            BLPrpJpayRefMain blPrpJpayRefMainOld = null;
            BLPrpJpayRefMain blPrpJpayRefMainNew = null;
            PrpJpayRefMainSchema prpJpayRefMainSchema = null;
            Bill bill = new Bill();
            ChgDate iDate = new ChgDate();
            String strOperateDate = iDate.getCurrentTime("yyyy-MM-dd");
            int intYear = Integer.parseInt(strOperateDate.substring(0,4));
            DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
            BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
            BLPrpJpayRefRec blPrpJpayRefRecOld = null;
            BLPrpJpayRefRec blPrpJpayRefRecNew = null;
            PrpJpayRefRecSchema prpJpayRefRecSchema = null;
            DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
            DBPrpJplanFee dbPrpJplanFee = null;
            DBPrpCplan dbPrpCplan = new DBPrpCplan();
            DBPrpCPplan dbPrpCPplan = new DBPrpCPplan();
            BLPrpJpayRefDetail blPrpJpayRefDetail = new BLPrpJpayRefDetail();
            BLPrpJpayRefDetail blPrpJpayRefDetailOld = null;
            BLPrpJpayRefDetail blPrpJpayRefDetailNew = null;
            PrpJpayRefDetailSchema prpJpayRefDetailSchema = null;
            Vector arrVoucherNoNew = new Vector();
            ArrayList arrPayRefNo = new ArrayList();
            DbPool dbpool = new DbPool();
            try {
            	dbpool.open(SysConfig.CONST_DDCCDATASOURCE); 
            	dbpool.beginTransaction();
            	for(int i=0;i<arrRealPayRefNo.size();i++){
                    
                    
                    String strWherePart = " CenterCode='" + arrCenterCode.get(i)
                                        + "' AND RealPayRefNo='" + arrRealPayRefNo.get(i)
                                        + "' ORDER BY PayRefNo ";
          		    blPrpJpayRefMain = new BLPrpJpayRefMain();
          		    blPrpJpayRefMain.query(dbpool,strWherePart,0);
          		    
            		
                    DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
                    String realPayRefNo = dbPrpJpayRefVoucher.genRealPayRefNo();
                    dbPrpJpayRefVoucher.setRealPayRefNo(realPayRefNo);
          		    dbPrpJpayRefVoucher.setPayRefNoType(blPrpJpayRefMain.getArr(0).getPayRefNoType());
          		    dbPrpJpayRefVoucher.setCenterCode(blPrpJpayRefMain.getArr(0).getCenterCode());
                    dbPrpJpayRefVoucher.setPayRefNoType(blPrpJpayRefMain.getArr(0).getPayRefNoType());
                    dbPrpJpayRefVoucher.setVoucherNo("0");
                    dbPrpJpayRefVoucher.setVoucherDate(strOperateDate);
                    dbPrpJpayRefVoucher.setVoucherFlag("0");
                    dbPrpJpayRefVoucher.setVoucherStatus("0");
                    dbPrpJpayRefVoucher.setToCenterCode(blPrpJpayRefMain.getArr(0).getBranchCode());
                    
                    if(dbPrpJpayRefVoucher.getToCenterCode().equals(dbPrpJpayRefVoucher.getCenterCode())){
                    	dbPrpJpayRefVoucher.setToCenterCode("0");
                    }
                    dbPrpJpayRefVoucher.setComCode(iComCode);
                    dbPrpJpayRefVoucher.setPayRefCode(iUserCode);
                    dbPrpJpayRefVoucher.insert(dbpool);
          		  
          		  
          		  String realPayRefNoOld = blPrpJpayRefMain.getArr(0).getRealPayRefNo();
          		  
          		  
          		  strWherePart = " RealPayRefNo='"+realPayRefNoOld+"'";
          		  blPrpJpayRefRec.query(dbpool,strWherePart,0);
          		  
          		  
          		  blPrpJpayRefDetail.query(dbpool,strWherePart,0);
          		  
          		  HashSet payRefNoHashSet = new HashSet();
          		  
          		  for(int j=0; j<blPrpJpayRefMain.getSize(); j++)
          		  {
          			  prpJpayRefMainSchema = new PrpJpayRefMainSchema();
          			  prpJpayRefMainSchema = blPrpJpayRefMain.getArr(j);
          			  String payRefNo = prpJpayRefMainSchema.getPayRefNo();
          			  double dbPayRefFee = Double.parseDouble(prpJpayRefMainSchema.getPayRefFee());
          			  if(!payRefNoHashSet.contains(payRefNo))
          			  {
          				  payRefNoHashSet.add(payRefNo);
          				  
          				  
          				  
          				  
          				  String strPayRefNoOld = dbPrpJpayRefVoucher.genRealPayRefNo();
          				  String strPayRefNoNew = strPayRefNoOld + "1";
          				  arrPayRefNo.add(new String[]{payRefNo,strPayRefNoOld,strPayRefNoNew});
          			  }
          			  for(int m = 0;m<arrPayRefNo.size();m++)
          			  {
          				  if((((String[])arrPayRefNo.get(m))[0]).equals(payRefNo))
          				  {
          					  prpJpayRefMainSchema.setPayRefNo(((String[])arrPayRefNo.get(m))[1]);
          					  prpJpayRefMainSchema.setFlag("1"); 
          					  blPrpJpayRefMainOld = new BLPrpJpayRefMain();
          					  blPrpJpayRefMainOld.setArr(prpJpayRefMainSchema);
          					  blPrpJpayRefMainOld.save(dbpool);
          					  
          					  prpJpayRefMainSchema = blPrpJpayRefMain.getArr(j);
          					  prpJpayRefMainSchema.setPayRefNo(((String[])arrPayRefNo.get(m))[2]);
          					  prpJpayRefMainSchema.setPayRefFee(""+(-1)*dbPayRefFee);
          					  prpJpayRefMainSchema.setPayRefDate(strOperateDate);
          					  prpJpayRefMainSchema.setPayRefCode(iUserCode);
          					  prpJpayRefMainSchema.setPayRefUnit(iComCode);
          					  prpJpayRefMainSchema.setAccBookType("");
          					  prpJpayRefMainSchema.setAccBookCode("");
          					  prpJpayRefMainSchema.setYearMonth("");
          					  prpJpayRefMainSchema.setVoucherNo("");
          					  prpJpayRefMainSchema.setFlag("1");
          					  
          					  prpJpayRefMainSchema.setRealPayRefNo(realPayRefNo);
          					  blPrpJpayRefMainNew = new BLPrpJpayRefMain();
          					  blPrpJpayRefMainNew.setArr(prpJpayRefMainSchema);
          					  blPrpJpayRefMainNew.save(dbpool);
          					  






          					  prpJpayRefMainSchema.setPayRefNo(payRefNo);
          					  prpJpayRefMainSchema.setIdentifyType("0");
          					  prpJpayRefMainSchema.setPayRefFee(""+dbPayRefFee);
          					  prpJpayRefMainSchema.setPayRefCode("");
          					  prpJpayRefMainSchema.setPayRefUnit("");
          					  prpJpayRefMainSchema.setPayRefDate("");
          					  prpJpayRefMainSchema.setAccBookType("");
          					  prpJpayRefMainSchema.setAccBookCode("");
          					  
          					  prpJpayRefMainSchema.setYearMonth("");
          					  prpJpayRefMainSchema.setVoucherNo("");
          					  prpJpayRefMainSchema.setFlag("");
          					  
          					  prpJpayRefMainSchema.setRealPayRefNo("");
          					  dbPrpJpayRefMain.setSchema(prpJpayRefMainSchema);
          					  dbPrpJpayRefMain.update(dbpool);
          				  }
          			  }
          		  }
          		  
          		  
          		  for (int j = 0; j < blPrpJpayRefDetail.getSize(); j++) {
					prpJpayRefDetailSchema = blPrpJpayRefDetail.getArr(j);
					String payRefNo = prpJpayRefDetailSchema.getPayRefNo();
					double dbPayRefFee = Double.parseDouble(prpJpayRefDetailSchema.getPayRefFee());
					for (int m = 0; m < arrPayRefNo.size(); m++) {
						if ((((String[]) arrPayRefNo.get(m))[0]).equals(payRefNo)) {
							
							prpJpayRefDetailSchema.setPayRefNo(((String[]) arrPayRefNo.get(m))[1]);
							blPrpJpayRefDetailOld = new BLPrpJpayRefDetail();
							blPrpJpayRefDetailOld.setArr(prpJpayRefDetailSchema);
							blPrpJpayRefDetailOld.save(dbpool);
							
							prpJpayRefDetailSchema = blPrpJpayRefDetail.getArr(j);
							prpJpayRefDetailSchema.setPayRefNo(((String[]) arrPayRefNo.get(m))[2]);
							prpJpayRefDetailSchema.setPayRefFee("" + (-1) * dbPayRefFee);
							
							prpJpayRefDetailSchema.setRealPayRefNo(realPayRefNo);
							prpJpayRefDetailSchema.setPayRefDate(strOperateDate);
							blPrpJpayRefDetailNew = new BLPrpJpayRefDetail();
							blPrpJpayRefDetailNew.setArr(prpJpayRefDetailSchema);
							blPrpJpayRefDetailNew.save(dbpool);
							
							String strWherePartDel = "";
							strWherePartDel = "DELETE FROM PrpJpayRefDetail WHERE PayRefNo='"
									+ payRefNo + "'";
							dbpool.delete(strWherePartDel);
						}
					}
        			
					if (blPrpJpayRefDetail.getArr(j).getPayWay().substring(0, 1).equals("4")
							&&!blPrpJpayRefDetail.getArr(j).getPayWay().equals("421")) {
						String strPoaCode = blPrpJpayRefDetail.getArr(j).getFlag1();
						DBPrpJpoaMain dbPrpJpoaMainTemp = new DBPrpJpoaMain();
						dbPrpJpoaMainTemp.getInfo(dbpool, strPoaCode);
						int intIoType = 1;
						if (blPrpJpayRefDetail.getArr(j).getPayWay().substring(1,2).equals("1")) {
							intIoType = -1;
						}
						double dblDeliqueeFee = dbPrpJpoaMainTemp.getDeliqueefee() - dbPayRefFee*intIoType;
						if (dblDeliqueeFee < 0) {
							throw new UserException(-96, -1167, "BLPrpJpayRefMain.cancelVoucherNew()",
									"预存款支付失败：原预存款帐号" + strPoaCode + " 余额不足！");
						}
						dbPrpJpoaMainTemp.setDeliqueefee(dblDeliqueeFee);
						dbPrpJpoaMainTemp.update(dbpool);
					}
				}
				
				BLPrpJpoaTrace blPrpJpoaTrace = new BLPrpJpoaTrace();
				blPrpJpoaTrace.query(dbpool, strWherePart);
				for (int j = 0; j < blPrpJpoaTrace.getSize(); j++) {
					DBPrpJpoaTrace dbPrpJpoaTrace = new DBPrpJpoaTrace();
					PrpJpoaTraceSchema prpJpoaTraceSchema = new PrpJpoaTraceSchema();
					prpJpoaTraceSchema = blPrpJpoaTrace.getArr(j);
					String SerailnoOld = prpJpoaTraceSchema.getSerailno();
					int maxSerailNo = Integer.parseInt(dbPrpJpoaTrace.getMaxSerailNo(dbpool,
							prpJpoaTraceSchema.getPoacode()));
					prpJpoaTraceSchema.setSerailno(String.valueOf(maxSerailNo + 1));
					prpJpoaTraceSchema.setPayfee(-prpJpoaTraceSchema.getPayfee());
					prpJpoaTraceSchema.setRemark("取消第" + SerailnoOld + "条收付款确认");
					prpJpoaTraceSchema.setInputdate(String.valueOf(new DateTime(new Date(),
							DateTime.YEAR_TO_SECOND)));
					
					prpJpoaTraceSchema.setRealpayrefno(realPayRefNo);
					dbPrpJpoaTrace.setSchema(prpJpoaTraceSchema);
					dbPrpJpoaTrace.insert(dbpool);
				}
				
          		  BLPrpJsettle blPrpJSettleTmp = new BLPrpJsettle();
          		  
          		  for(int j=0; j<blPrpJpayRefRec.getSize(); j++)
          		  {
          			  prpJpayRefRecSchema = blPrpJpayRefRec.getArr(j);
          			  dbPrpJplanFee = new DBPrpJplanFee();
          			  int result= dbPrpJplanFee.getInfoForUpdate(dbpool,
                                          prpJpayRefRecSchema.getCertiType(),
                                          prpJpayRefRecSchema.getCertiNo(),
                                          prpJpayRefRecSchema.getSerialNo(),
                                          prpJpayRefRecSchema.getPayRefReason());
          			  if(result==0){
          				  if(Double.parseDouble(dbPrpJplanFee.getRealPayRefFee())==0){
          					  throw new UserException( -96, -1167, "BLPrpJpayRefMain.cancelVoucher", 
                          		  dbPrpJplanFee.getCertiNo()+"已经取消收付款确认，不能重复取消！");
          					  }
          				  dbPrpJplanFee.setRealPayRefFee("0");
          				  dbPrpJplanFee.update(dbpool);
          				  
          				  if(prpJpayRefRecSchema.getRiskCode().equals("0507")
                      		  && (prpJpayRefRecSchema.getPayRefReason().equals("R72")
                              		  || prpJpayRefRecSchema.getPayRefReason().equals("R73")
                              		  || prpJpayRefRecSchema.getPayRefReason().equals("R74")
                              		  || prpJpayRefRecSchema.getCertiType().equals("P")))
          				  {
          					  BLPrpJtaxSettle blPrpJtaxSettle = new BLPrpJtaxSettle();
          					  DBPrpJtaxSettle dbPrpJtaxSettle = new DBPrpJtaxSettle();
          					  String sql = " policyno='"+prpJpayRefRecSchema.getPolicyNo()+
    	  										"' and serialno='"+prpJpayRefRecSchema.getSerialNo()+"'";
          					  blPrpJtaxSettle.query(dbpool,sql);
          					  for(int m=0;m<blPrpJtaxSettle.getSize();m++)
          					  {
          						  if(blPrpJtaxSettle.getArr(m).getTaxSettleFlag().equals("1"))
          						  {
          							  throw new UserException( -96, -1167, "BLPrpJpayRefMain.VoucherVerify",
  					          			  "XX："+prpJpayRefRecSchema.getPolicyNo()+"已经结算，不能再取消收付款确认！");
          							  }
          						  dbPrpJtaxSettle.setSchema(blPrpJtaxSettle.getArr(m));
          						  if(prpJpayRefRecSchema.getCertiType().equals("P")  
  		            				  && !prpJpayRefRecSchema.getPayRefReason().equals("R72")
  		                    		  && !prpJpayRefRecSchema.getPayRefReason().equals("R73")
  		                    		  && !prpJpayRefRecSchema.getPayRefReason().equals("R74")
  		                    		  && !dbPrpJtaxSettle.getTaxRelifFlag().equals("2") 
  		                    		  && !dbPrpJtaxSettle.getTaxRelifFlag().equals("4")
  		                    		  && !dbPrpJtaxSettle.getTaxRelifFlag().equals("5"))
          						  {
          							  continue;
          						  }
          						  if(dbPrpJtaxSettle.getPayRefReason().equals(prpJpayRefRecSchema.getPayRefReason()))
          						  {
          							  dbPrpJtaxSettle.setPayRefFee(""+(Double.parseDouble(dbPrpJtaxSettle.getPayRefFee()) - 
  		                    			  Double.parseDouble(prpJpayRefRecSchema.getPlanFee())));
          							  }
          						  dbPrpJtaxSettle.setPayRefDate("2100-01-01");
          						  
          						  dbPrpJtaxSettle.update(dbpool);
          						  }
          					  }
          				  
          				  
          				  if (prpJpayRefRecSchema.getCertiType().equals("P")) {
          					  if(prpJpayRefRecSchema.getPayRefReason().equals("R82")||
          							  prpJpayRefRecSchema.getPayRefReason().equals("R42")||
          							  prpJpayRefRecSchema.getPayRefReason().equals("P82")||
          							  prpJpayRefRecSchema.getPayRefReason().equals("P92")||
          							  prpJpayRefRecSchema.getPayRefReason().equals("P81")||
          							  prpJpayRefRecSchema.getPayRefReason().equals("R81")||
          							  prpJpayRefRecSchema.getPayRefReason().equals("R72")||
          							  prpJpayRefRecSchema.getPayRefReason().equals("R73")||
          							  prpJpayRefRecSchema.getPayRefReason().equals("R74"))
          					  {
          					  }else{
          						  
          						  
          						String serialNo = prpJpayRefRecSchema.getPayNo();
    							if(prpJpayRefRecSchema.getPayRefReason().equals("R14")
    									|| prpJpayRefRecSchema.getPayRefReason().equals("R15")
    									|| prpJpayRefRecSchema.getPayRefReason().equals("R16")
    									|| prpJpayRefRecSchema.getPayRefReason().equals("R17"))
    							{
    								serialNo = prpJpayRefRecSchema.getSerialNo();
    							}
          						  if (dbPrpCplan.getInfo(dbpool,
                                                 prpJpayRefRecSchema.getCertiNo(),
                                                 serialNo) == 0) {
          							  dbPrpCplan.setDelinquentFee(""+(Double.parseDouble(dbPrpCplan.getDelinquentFee())
          									  +Double.parseDouble(dbPrpJplanFee.getPlanFee())));
          							  dbPrpCplan.update(dbpool);
          							  }
          						  
          						  if (dbPrpCPplan.getInfo(dbpool,
                                                  prpJpayRefRecSchema.getCertiNo(),
                                                  serialNo) == 0) {
          							  dbPrpCPplan.setDelinquentFee(""+(Double.parseDouble(dbPrpCPplan.getDelinquentFee())
          									  +Double.parseDouble(dbPrpJplanFee.getPlanFee())));
          							  dbPrpCPplan.update(dbpool);
          							  }
          						  }
          					  }else if (prpJpayRefRecSchema.getCertiType().equals("E")) {
          						  if(prpJpayRefRecSchema.getPayRefReason().equals("R82")||
          								  prpJpayRefRecSchema.getPayRefReason().equals("R42")||
          								  prpJpayRefRecSchema.getPayRefReason().equals("P82")||
          								  prpJpayRefRecSchema.getPayRefReason().equals("P92")||
          								  prpJpayRefRecSchema.getPayRefReason().equals("P81")||
          								  prpJpayRefRecSchema.getPayRefReason().equals("R81")||
          								  prpJpayRefRecSchema.getPayRefReason().equals("R72")||
          								  prpJpayRefRecSchema.getPayRefReason().equals("R73")||
          								  prpJpayRefRecSchema.getPayRefReason().equals("R74"))
          						  {
          						  }else{
          							  BLPrpCplan blPrpCplan = new BLPrpCplan();
          							  String strWhere = " EndorseNo='" + prpJpayRefRecSchema.getCertiNo() + "' ";;
          							  blPrpCplan.query(dbpool, strWhere, 0);
          							  for(int k=0; k<blPrpCplan.getSize(); k++){
          								  dbPrpCplan.setSchema(blPrpCplan.getArr(k));
          								  dbPrpCplan.setDelinquentFee(""+(Double.parseDouble(dbPrpCplan.getDelinquentFee())
              									  +Double.parseDouble(dbPrpJplanFee.getPlanFee())));
          								  dbPrpCplan.update(dbpool);
          								  }
          							  BLPrpCPplan blPrpCPplan = new BLPrpCPplan();
          							  blPrpCPplan.query(dbpool, strWhere, 0);
          							  for(int k=0; k<blPrpCPplan.getSize(); k++){
          								  dbPrpCPplan.setSchema(blPrpCPplan.getArr(k));
          								  dbPrpCPplan.setDelinquentFee(""+(Double.parseDouble(dbPrpCPplan.getDelinquentFee())
              									  +Double.parseDouble(dbPrpJplanFee.getPlanFee())));
          								  dbPrpCPplan.update(dbpool);
          								  }
          							  }
          						  }
          				  
          				  }
          			    prpJpayRefRecSchema = blPrpJpayRefRec.getArr(j);
                        double dbPayRefFee = Double.parseDouble(prpJpayRefRecSchema.getPayRefFee());
                        String payRefNo = prpJpayRefRecSchema.getPayRefNo();
                        String flagTmp = "";
                        for(int m=0; m<arrPayRefNo.size(); m++)
                        {
                      	  if( (((String[])arrPayRefNo.get(m))[0]).equals(payRefNo))
                      	  {
                      		  
                      		  flagTmp = prpJpayRefRecSchema.getFlag();
                      		  prpJpayRefRecSchema.setPayRefTimes(this.getPayRefTimes(dbpool,prpJpayRefRecSchema,"10"));
                      		  prpJpayRefRecSchema.setPayRefNo(((String[])arrPayRefNo.get(m))[1]);
                      		  prpJpayRefRecSchema.setFlag(flagTmp+"R");
                      		  blPrpJpayRefRecOld = new BLPrpJpayRefRec();
                      		  blPrpJpayRefRecOld.setArr(prpJpayRefRecSchema);
                      		  blPrpJpayRefRecOld.save(dbpool);
                      		  
                      		  prpJpayRefRecSchema.setPayRefTimes(this.getPayRefTimes(dbpool,prpJpayRefRecSchema,"20"));
                      		  prpJpayRefRecSchema.setPayRefNo(((String[])arrPayRefNo.get(m))[2]);
                      		  prpJpayRefRecSchema.setPayRefFee(""+(-1)*dbPayRefFee);
                      		  prpJpayRefRecSchema.setPayRefDate(strOperateDate);
                      		  
                              
                              if(prpJpayRefRecSchema.getCertiType().equals("C")
                            		  && prpJpayRefRecSchema.getClassCode().equals("05")
                            		  && prpJpayRefRecSchema.getPayRefReason().equals("P60")){
                            	  this.callClaimProcedure(dbpool,prpJpayRefRecSchema.getClaimNo(),prpJpayRefRecSchema.getCertiNo(),
                            			Double.parseDouble(prpJpayRefRecSchema.getPayRefFee()),strOperateDate,
                            			  dbPrpJpayRefVoucher.getPayRefCode(),dbPrpJpayRefVoucher.getComCode(),
                            			  prpJpayRefRecSchema.getClassCode(),prpJpayRefRecSchema.getRiskCode(),
                            			  prpJpayRefRecSchema.getHandler1Code(),blPrpJpayRefMain.getArr(0).getCenterCode());
                              }
                      		  
                      		  
                      		  prpJpayRefRecSchema.setRealPayRefNo(realPayRefNo);
                      		  prpJpayRefRecSchema.setVoucherNo("");
                      		  blPrpJpayRefRecNew = new BLPrpJpayRefRec();
                      		  blPrpJpayRefRecNew.setArr(prpJpayRefRecSchema);
                      		  blPrpJpayRefRecNew.save(dbpool);
                      		  }
                      	  }
                        
                        if(prpJpayRefRecSchema.getCertiType().equals("P")||prpJpayRefRecSchema.getCertiType().equals("E")){
                        	if(!prpJpayRefRecSchema.getPayRefReason().equals("R72")
                            		&&!prpJpayRefRecSchema.getPayRefReason().equals("R73")
                            		&&!prpJpayRefRecSchema.getPayRefReason().equals("R74"))
                            {
                        		BLPrpJmanageFee blPrpJmanageFee = new BLPrpJmanageFee();
                        		blPrpJmanageFee.transCancelVoucher(dbpool,prpJpayRefRecSchema); 
                            }
                        }
                        
                        
                        if((prpJpayRefRecSchema.getCertiType().equals("P")||prpJpayRefRecSchema.getCertiType().equals("E"))
                              && prpJpayRefRecSchema.getCoinsFlag().equals("1")
                              && prpJpayRefRecSchema.getCoinsType().equals("1")){
                      	  if(prpJpayRefRecSchema.getPayRefReason().substring(1,3).equals("81")){
                      		  dbPrpJpayRefRec = new DBPrpJpayRefRec();
                      		  dbPrpJpayRefRec.delete(dbpool,prpJpayRefRecSchema.getCertiType(),
                                                      prpJpayRefRecSchema.getCertiNo(),
                                                      prpJpayRefRecSchema.getSerialNo(),
                                                      prpJpayRefRecSchema.getPayRefReason(),
                                                      "1");
                      		  continue;
                            }else{
                                DBPrpJplanFee dbPrpJplanFeeCoins1 = new DBPrpJplanFee();
                                dbPrpJplanFeeCoins1.getInfo(dbpool,prpJpayRefRecSchema.getCertiType(),
                                                             prpJpayRefRecSchema.getCertiNo(),
                                                             prpJpayRefRecSchema.getSerialNo(),
                                                             prpJpayRefRecSchema.getPayRefReason());
                                prpJpayRefRecSchema.setPayRefNo(payRefNo);
                                prpJpayRefRecSchema.setPayRefTimes("1");
                                prpJpayRefRecSchema.setPlanFee(dbPrpJplanFeeCoins1.getPlanFee());
                                prpJpayRefRecSchema.setPayRefFee(dbPrpJplanFeeCoins1.getPlanFee());
                                prpJpayRefRecSchema.setPayRefDate("");
                                prpJpayRefRecSchema.setIdentifyType("0");
                                
                                prpJpayRefRecSchema.setFlag(flagTmp);
                                dbPrpJpayRefRec = new DBPrpJpayRefRec();
                                
                                prpJpayRefRecSchema.setRealPayRefNo("");
                                
                                prpJpayRefRecSchema.setVoucherNo("");
                                dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
                                dbPrpJpayRefRec.update(dbpool);
                            }
                        }else{
                            prpJpayRefRecSchema.setPayRefNo(payRefNo);
                            prpJpayRefRecSchema.setPayRefTimes("1");
                            prpJpayRefRecSchema.setPayRefFee(""+dbPayRefFee);
                            prpJpayRefRecSchema.setPayRefDate("");
                            prpJpayRefRecSchema.setIdentifyType("0");
                            
                            prpJpayRefRecSchema.setFlag(flagTmp);
                            dbPrpJpayRefRec = new DBPrpJpayRefRec();
                            
                            prpJpayRefRecSchema.setRealPayRefNo("");
                            
                            prpJpayRefRecSchema.setVoucherNo("");
                            dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
                            dbPrpJpayRefRec.update(dbpool);                    
                        }
                        
                        boolean flag = true;
                        if(prpJpayRefRecSchema.getRiskCode().equals("YAB0")){
                      	  BLPrpJsettle blPrpJsettle = new BLPrpJsettle();
                      	  strWherePart = " PolicyNo='"+blPrpJpayRefRec.getArr(j).getPolicyNo()+
                      	  		"' AND Currency2='"+blPrpJpayRefRec.getArr(j).getCurrency2()+"'";
                      	  blPrpJsettle.query(strWherePart);
                      	  if(blPrpJsettle.getSize() == 0)
                      	  {
                      		  continue;
                      	  }else
                      	  {
                      		  for(int n=0;n<blPrpJSettleTmp.getSize();n++)
                      		  {
                      			  if(prpJpayRefRecSchema.getPolicyNo().equals(blPrpJSettleTmp.getArr(n).getPolicyNo())
        								  &&prpJpayRefRecSchema.getCurrency2().equals(blPrpJSettleTmp.getArr(n).getCurrency2()))
                      			  {
                      				  blPrpJSettleTmp.getArr(n).setPlanFee(""+(Double.parseDouble(blPrpJSettleTmp.getArr(n).getPlanFee())
        							  			-Double.parseDouble(prpJpayRefRecSchema.getPlanFee())));
                      				  blPrpJSettleTmp.getArr(n).setLeftSettleFee(""+(Double.parseDouble(blPrpJSettleTmp.getArr(n).getLeftSettleFee())
        							  			-Double.parseDouble(prpJpayRefRecSchema.getPayRefFee())));
                      				  flag = false;
                      				  break;
                      			  } else
                      			  {
                      				  flag = true;
                      			  }
                      		  }
                      		  if(flag)
                      		  {
                      			  blPrpJsettle.getArr(0).setPlanFee(""+(Double.parseDouble(blPrpJsettle.getArr(0).getPlanFee())
    						  			-Double.parseDouble(prpJpayRefRecSchema.getPayRefFee())));
                      			  blPrpJsettle.getArr(0).setLeftSettleFee(""+(Double.parseDouble(blPrpJsettle.getArr(0).getLeftSettleFee())
      						  			-Double.parseDouble(prpJpayRefRecSchema.getPayRefFee())));
                      			  blPrpJSettleTmp.setArr(blPrpJsettle.getArr(0));
                      		  }
                      	  }
                        }
                        
          			  
          			  if((prpJpayRefRecSchema.getCertiType().equals("P")||
      	  					prpJpayRefRecSchema.getCertiType().equals("E")) &&
    	  					!prpJpayRefRecSchema.getPayRefReason().equals("R72") &&
    	  					!prpJpayRefRecSchema.getPayRefReason().equals("R73") &&
    	  					!prpJpayRefRecSchema.getPayRefReason().equals("R74")){
          				StringBuffer SQLBuffer =null;
          				BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
          				DBPrpJplanCommission dbPrpJplanCommission = null;
          				
          				SQLBuffer =new StringBuffer();
          				SQLBuffer.append(" CertiNo='"+prpJpayRefRecSchema.getCertiNo()+"'");
          				SQLBuffer.append(" And Serialno='"+prpJpayRefRecSchema.getSerialNo()+"'");
          				SQLBuffer.append(" And PayRefReason ='P90'");
          				blPrpJplanCommission.query(dbpool, SQLBuffer.toString(), 0);
          				
            	  			for (int n = 0 ;n<blPrpJplanCommission.getSize();n++){
          	  				dbPrpJplanCommission = new DBPrpJplanCommission();
          					dbPrpJplanCommission.setSchema(blPrpJplanCommission.getArr(n));
          					if(!dbPrpJplanCommission.getRealPayRefFee().equals(dbPrpJplanCommission.getPlanFee()))
          					{
              	  				dbPrpJplanCommission.setPoliPayRefDate("");  		
              	  				dbPrpJplanCommission.setPayFlag("0");
          					}
          	  				dbPrpJplanCommission.setRealRefPremium("0.00");
          	  				dbPrpJplanCommission.update(dbpool);
          				}
          				
          				
          				SQLBuffer =new StringBuffer();
          				SQLBuffer.append(" PolicyNo='"+prpJpayRefRecSchema.getPolicyNo()+"'");
          				SQLBuffer.append(" And Serialno='"+prpJpayRefRecSchema.getSerialNo()+"'");
          				SQLBuffer.append(" And PayRefReason ='P93'");
          				blPrpJplanCommission.query(dbpool, SQLBuffer.toString(), 0);
          				
            	  			for (int n = 0 ;n<blPrpJplanCommission.getSize();n++){
          					dbPrpJplanCommission = new DBPrpJplanCommission();
          	  				dbPrpJplanCommission.setSchema(blPrpJplanCommission.getArr(n));
          					if(!dbPrpJplanCommission.getRealPayRefFee().equals(dbPrpJplanCommission.getPlanFee()))
          					{
              	  				dbPrpJplanCommission.setPoliPayRefDate("");		
              	  				dbPrpJplanCommission.setPayFlag("0");
          					}
          	  				dbPrpJplanCommission.setRealRefPremium("0.00");
          	  				dbPrpJplanCommission.update(dbpool);
          				}
          			  }
          			  
                  }

                  
          		  for(int j=0;j<blPrpJSettleTmp.getSize();j++)
          		  {
          			  DBPrpJsettle dbPrpJsettle = new DBPrpJsettle();
      				  dbPrpJsettle.setSchema(blPrpJSettleTmp.getArr(j));
      				  dbPrpJsettle.update();
      			  }
          		
          		  
          		  BLPrpJpaymentVoucher blPrpJpaymentVoucher = new BLPrpJpaymentVoucher();
          		  String voucherNo = blPrpJpaymentVoucher.callProcedure(dbpool,realPayRefNo);
                  arrVoucherNoNew.add(voucherNo);
              }
              dbpool.commitTransaction();





            }
            catch(SQLException sqlexception){
            	dbpool.rollbackTransaction();
                sqlexception.printStackTrace();





                throw sqlexception;
            }
            catch(Exception exception){
                dbpool.rollbackTransaction();
                exception.printStackTrace();





                throw exception;
            }
            finally {
              dbpool.close();
            }
            return arrVoucherNoNew;
      }

    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
