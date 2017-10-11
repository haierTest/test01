package com.sp.prpall.blsvr.jf;

import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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

/**
 * 定义PrpJsettle的BL类
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-04-16</p>
 * @author zhanglingjian
 * @version 1.0
 */
public class BLPrpJsettle{
    private Vector schemas = new Vector();
    private BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
    private BLPrpJpayRefDetail blPrpJpayRefDetail = new BLPrpJpayRefDetail();
    private BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 构造函数
     */
    public BLPrpJsettle(){
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

    /**
     *增加一条PrpJsettleSchema记录
     *@param iPrpJsettleSchema PrpJsettleSchema
     *@throws Exception
     */
    public void setArr(PrpJsettleSchema iPrpJsettleSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpJsettleSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }

    /**
     *得到一条PrpJsettleSchema记录
     *@param index 下标
     *@return 一个PrpJsettleSchema对象
     *@throws Exception
     */
    public PrpJsettleSchema getArr(int index) throws Exception
    {
    	PrpJsettleSchema prpJsettleSchema = null;
      try
      {
    	  prpJsettleSchema = (PrpJsettleSchema)this.schemas.get(index);
      }
      catch(Exception e)
      {
        throw e;
      }
      return prpJsettleSchema;
    }

    /**
     *删除一条PrpJsettleSchema记录
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
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJsettle dbPrpJsettle = new DBPrpJsettle();
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
    	  dbPrpJsettle.setSchema((PrpJsettleSchema)schemas.get(i));
    	  dbPrpJsettle.insert(dbpool);
      }
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
      DBPrpJsettle dbDBPrpJsettle = new DBPrpJsettle();
      if (iLimitCount > 0 && dbDBPrpJsettle.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJsettle.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJsettle WHERE " + iWherePart;
        schemas = dbDBPrpJsettle.findByConditions(strSqlStatement);
      }
    }
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool ,String iWherePart) throws UserException,Exception
    {
      this.query(dbpool ,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }

    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool ,String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJsettle dbDBPrpJsettle = new DBPrpJsettle();
      if (iLimitCount > 0 && dbDBPrpJsettle.getCount(dbpool,iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJsettle.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJsettle WHERE " + iWherePart;
        schemas = dbDBPrpJsettle.findByConditions(dbpool,strSqlStatement);
      }
    }    
    /**
     * 获取PayRefTimes zhanglingjian 20070417
     * @param dbpool  
     * @param PrpJsettleSchema 
     * @param iPayRefTimesBegin 10原数据 20对冲数据
     * @return
     * @throws Exception
     */
    public String getPayRefTimes(DbPool dbpool,PrpJsettleSchema iSchema,String iPayRefTimesBegin) throws Exception
    {
      String strPayRefTimes = "";
      String strSQL = "";
      strSQL = "SELECT MAX(PayRefTimes) FROM PrpJsettle"
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
     * 获取PayRefTimes zhanglingjian 20070417
     * @param dbpool  
     * @param PrpJpayRefRecSchema 
     * @param iPayRefTimesBegin 10原数据 20对冲数据
     * @return
     * @throws Exception
     */
    public String getPayRefTimes1(DbPool dbpool,PrpJpayRefRecSchema iSchema,String iPayRefTimesBegin) throws Exception
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
     * 查询同一policyno下所有批单记录 zhanglingjian 20070418
     * @param dbpool  
     * @param policyno 
     * @return
     * @throws Exception
     */
    public BLPrpJsettle getAllRecord(DbPool dbpool,String PolicyNo,String strWhereComCode) throws Exception
    {
    	BLPrpJsettle blPrpJsettle = new BLPrpJsettle();
    	String strWherePart = " CertiType='E' AND SettleStatus='0' AND LeftSettleFee!=0 AND " +
		" PolicyNo='"+PolicyNo+"'"+strWhereComCode;
    	blPrpJsettle.query(dbpool,strWherePart,0);
    	return blPrpJsettle;
    }

    /**
     *根据结算日期和核算单位代码进行结算
     *@param iSettleDate 结算日期
     *@param iCenterCode 结算的核算单位代码
     *@throws UserException
     *@throws Exception
     */
    public void settle(String iSettleDate,String iCenterCode) throws UserException,Exception
    {
    	
    	DbPool dbpool = new DbPool();
    	try {
    	    dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
            
            String strWherePart = " CenterFlag IN ('1','2') AND ValidStatus='1'";
            strWherePart += Str.convertString("ComCode",iCenterCode,"=");
            strWherePart += " ORDER BY ComCode";
            blPrpDcompany.query(dbpool,strWherePart,0);
            for(int i=0; i<blPrpDcompany.getSize(); i++){
            	try
            	{
            		dbpool.beginTransaction();
            		this.logPrintln("开始结算核算单位："+blPrpDcompany.getArr(i).getComCode());
            		this.settle(dbpool,iSettleDate,blPrpDcompany.getArr(i).getComCode());
                    dbpool.commitTransaction();
            	}catch(Exception e){
                    e.printStackTrace();
                    dbpool.rollbackTransaction();
                }
            }
    	}catch(Exception e){
            dbpool.rollbackTransaction();
            throw e;
        }
        finally {
            dbpool.close();
        }
    }
    
    /**
     *根据结算日期和核算单位代码进行结算
     *@param iSettleDate 结算日期
     *@param iCenterCode 结算的核算单位代码
     *@throws UserException
     *@throws Exception
     */
    public void settle(DbPool dbpool,String iSettleDate,String iCenterCode) throws UserException,Exception
    {
    	BLPrpJpayRefDetail blPrpJpayRefDetail = new BLPrpJpayRefDetail();
    	BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
    	BLPrpJsettle blPrpJsettle = new BLPrpJsettle();
    	DBPrpJsettle dbPrpJsettle = new DBPrpJsettle();
    	
    	ChgDate idate = new ChgDate();
    	String strPayRefDate  = idate.getCurrentTime("yyyy-MM-dd");
    	
        Bill bill = new Bill();
        String billNo = "";
        int intYear = Integer.parseInt(iSettleDate.substring(0,4));
        billNo = bill.getNo("prpjpayrec","0501","00000000",intYear,"00");
        
        
    	DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
    	String realPayRefNo = dbPrpJpayRefVoucher.genRealPayRefNo();
    	
    	
    	String strWhereComCode = " AND ComCode IN (" +
    			"SELECT ComCode FROM PrpDcompany START WITH ComCode='"+iCenterCode+
    			"' CONNECT BY PRIOR ComCode=UpperComcode " +
    			" AND (CenterFlag NOT IN ('1','2') OR CenterFlag IS NULL))";
    	
    	String strWherePart = " SettleStatus='0' AND LeftSettleFee>0 "+strWhereComCode;
    	blPrpJsettle.initArr();
    	blPrpJsettle.query(dbpool,strWherePart,0);
    	for(int i=0;i<blPrpJsettle.getSize();i++)
    	{
    		double realPayFee = 0;
    		boolean blnFlag = false; 
    		BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
    		strWherePart = " CertiType IN ('P','E') AND ContractNo='"+blPrpJsettle.getArr(i).getPolicyNo()+
					"' AND RealPayRefFee=0 AND PayRefFee=0 AND Currency1='"+blPrpJsettle.getArr(i).getCurrency2()+
					"' "+strWhereComCode;
			blPrpJplanFee.query(dbpool,strWherePart,0);
    		for(int j=0; j<blPrpJplanFee.getSize(); j++)
    		{
    			DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();

    			if(Double.parseDouble(blPrpJplanFee.getArr(j).getPlanFee())
						>Double.parseDouble(blPrpJsettle.getArr(i).getLeftSettleFee()))
    			{
    				
    				continue;
    			} else
    			{
    				blnFlag = true;
					
					PrpJpayRefRecSchema prpJpayRefRecSchema = new PrpJpayRefRecSchema();
					prpJpayRefRecSchema = transToPayRefRec(blPrpJplanFee.getArr(j),iSettleDate);
					blPrpJpayRefRec.setArr(prpJpayRefRecSchema);
					
					dbPrpJplanFee.setSchema(blPrpJplanFee.getArr(j));
					dbPrpJplanFee.setRealPayRefFee(prpJpayRefRecSchema.getPayRefFee());
        			
        			dbPrpJplanFee.setPayRefFee(prpJpayRefRecSchema.getPayRefFee());
        			
					dbPrpJplanFee.update(dbpool);
					
					
					blPrpJsettle.getArr(i).setSettleFee(""+(Double.parseDouble(blPrpJsettle.getArr(i).getSettleFee())
							+Double.parseDouble(prpJpayRefRecSchema.getPayRefFee())));
					blPrpJsettle.getArr(i).setLeftSettleFee(""+(Double.parseDouble(blPrpJsettle.getArr(i).getLeftSettleFee())
							-Double.parseDouble(prpJpayRefRecSchema.getPayRefFee())));
					realPayFee = realPayFee + Double.parseDouble(prpJpayRefRecSchema.getPayRefFee());
					
					
					transData(dbpool,prpJpayRefRecSchema,
							blPrpJsettle.getArr(i).getCertiNo(),
							blPrpJsettle.getArr(i).getSerialNo(),
							blPrpJsettle.getArr(i).getPayRefTimes());
					
					
					DBPrpCplan dbPrpCplan = new DBPrpCplan();
					DBPrpCPplan dbPrpCPplan = new DBPrpCPplan();
					if(dbPrpJplanFee.getCertiType().equals("P")){
            	        int intReturn = dbPrpCplan.getInfo(dbpool, dbPrpJplanFee.getCertiNo(),
            	        		  dbPrpJplanFee.getSerialNo());
                        if(intReturn == 0){
                        	dbPrpCplan.setDelinquentFee(""+0);
                        	dbPrpCplan.update(dbpool);
                        }
                        intReturn = dbPrpCPplan.getInfo(dbpool, dbPrpJplanFee.getCertiNo(),
          	        		  dbPrpJplanFee.getSerialNo());
                        if(intReturn == 0){
                        	dbPrpCPplan.setDelinquentFee(""+0);
                        	dbPrpCPplan.update(dbpool);
                        }
                    }else if(dbPrpJplanFee.getCertiType().equals("E")){
                    	DBPrpCplan dbPrpCplanE = new DBPrpCplan();
    					DBPrpCPplan dbPrpCPplanE = new DBPrpCPplan();
                        BLPrpCplan blPrpCplan = new BLPrpCplan();
                        BLPrpCPplan blPrpCPplan = new BLPrpCPplan();
                        String strWhere = " EndorseNo='" + dbPrpJplanFee.getCertiNo()+"' ";
                        blPrpCplan.query(dbpool,strWhere,0);
                        if(blPrpCplan.getSize()>0){
                        	dbPrpCplanE.setSchema(blPrpCplan.getArr(0));
                        	dbPrpCplanE.setDelinquentFee(""+0);
                        	dbPrpCplanE.update(dbpool);
                        }
                        strWhere = " EndorseNo='" + dbPrpJplanFee.getCertiNo()+"' ";
                        blPrpCPplan.query(dbpool,strWhere,0);
                        if(blPrpCPplan.getSize()>0){
                        	dbPrpCPplanE.setSchema(blPrpCPplan.getArr(0));
                        	dbPrpCPplanE.setDelinquentFee(""+0);
                        	dbPrpCPplanE.update(dbpool);
                        }
                    }
					
					if ((prpJpayRefRecSchema.getCertiType().equals("P") || prpJpayRefRecSchema
							.getCertiType().equals("E"))
							&& !prpJpayRefRecSchema.getPayRefReason().equals("R72")
							&& !prpJpayRefRecSchema.getPayRefReason().equals("R73")
							&& !prpJpayRefRecSchema.getPayRefReason().equals("R74")) {
						StringBuffer SQLBuffer = null;
						BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
						DBPrpJplanCommission dbPrpJplanCommission = null;
						double dbRealRefPremium = 0;
						
						SQLBuffer = new StringBuffer();
						SQLBuffer.append(" CertiNo='" + prpJpayRefRecSchema.getCertiNo() + "'");
						SQLBuffer.append(" And Serialno='" + prpJpayRefRecSchema.getSerialNo()
								+ "'");
						SQLBuffer.append(" And PayRefReason ='P90'");
						blPrpJplanCommission.query(dbpool, SQLBuffer.toString(), 0);

						for (int k = 0; k < blPrpJplanCommission.getSize(); k++) {
							dbPrpJplanCommission = new DBPrpJplanCommission();
							dbPrpJplanCommission.setSchema(blPrpJplanCommission.getArr(k));
							dbPrpJplanCommission.setPoliPayRefDate(strPayRefDate);
							if (dbPrpJplanCommission.getRealRefPremium() == null
									|| dbPrpJplanCommission.getRealRefPremium().equals(""))
								dbRealRefPremium = Double.parseDouble(prpJpayRefRecSchema
										.getPlanFee());
							else
								dbRealRefPremium = Double.parseDouble(prpJpayRefRecSchema
										.getPlanFee())
										+ Double.parseDouble(dbPrpJplanCommission
												.getRealRefPremium());
							dbRealRefPremium = Str.round(dbRealRefPremium, 2);
							dbPrpJplanCommission.setRealRefPremium(dbRealRefPremium + "");
							dbPrpJplanCommission.setPayFlag("1");
							dbPrpJplanCommission.update(dbpool);
						}

						
						SQLBuffer = new StringBuffer();
						SQLBuffer.append(" PolicyNo='" + prpJpayRefRecSchema.getPolicyNo() + "'");
						SQLBuffer.append(" And Serialno='" + prpJpayRefRecSchema.getSerialNo()
								+ "'");
						SQLBuffer.append(" And PayRefReason ='P93'");
						blPrpJplanCommission.query(dbpool, SQLBuffer.toString(), 0);

						for (int k = 0; k < blPrpJplanCommission.getSize(); k++) {
							dbPrpJplanCommission = new DBPrpJplanCommission();
							dbPrpJplanCommission.setSchema(blPrpJplanCommission.getArr(k));
							dbPrpJplanCommission.setPoliPayRefDate(prpJpayRefRecSchema
									.getPayRefDate());
							dbPrpJplanCommission.setRealRefPremium("0.00");
							dbPrpJplanCommission.setPayFlag("1");
							dbPrpJplanCommission.update(dbpool);
						}
					}
					
    			}
    		}
			
    		
			dbPrpJsettle.setSchema(blPrpJsettle.getArr(i));
	    	dbPrpJsettle.update(dbpool);
	    	
	    	
	    	PrpJpayRefDetailSchema prpJpayRefDetailSchema = new PrpJpayRefDetailSchema();
	    	prpJpayRefDetailSchema.setPayRefNo(billNo);
	    	prpJpayRefDetailSchema.setRealPayRefNo(realPayRefNo);
	    	prpJpayRefDetailSchema.setSerialNo(""+(i+1));
	    	prpJpayRefDetailSchema.setPayWay("410");
	    	prpJpayRefDetailSchema.setCurrency2(blPrpJsettle.getArr(i).getCurrency2());
	    	prpJpayRefDetailSchema.setPayRefFee(""+realPayFee);
	    	prpJpayRefDetailSchema.setPayRefDate(iSettleDate);
	    	prpJpayRefDetailSchema.setComCode(blPrpJsettle.getArr(i).getComCode());
	    	prpJpayRefDetailSchema.setHandler1Code(blPrpJsettle.getArr(i).getHandler1Code());
	    	if(blnFlag){
	    		blPrpJpayRefDetail.setArr(prpJpayRefDetailSchema);
	    	}
    	}
    	
    	
    	
    	if(blPrpJpayRefRec.getSize()!=0)
    	{
    		
    	}else
    	{
    		return;
    	}
    	
    	
    	
    	BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
    	PrpJpayRefMainSchema prpJpayRefMainSchema = new PrpJpayRefMainSchema();
    	
    	
        prpJpayRefMainSchema.setSerialNo("1");
        prpJpayRefMainSchema.setPayRefNoType("1");
        prpJpayRefMainSchema.setCurrency2(blPrpJpayRefRec.getArr(0).getCurrency2());
        prpJpayRefMainSchema.setPackageUnit("00000000");
        
        double allRealPayFee = 0;
    	for(int i=0;i<blPrpJpayRefRec.getSize();i++)
    	{
    		allRealPayFee = allRealPayFee+Double.parseDouble(blPrpJpayRefRec.getArr(i).getPayRefFee());
    	}
        prpJpayRefMainSchema.setPayRefFee(""+allRealPayFee);
        prpJpayRefMainSchema.setPayRefNo(billNo);
        prpJpayRefMainSchema.setPackageDate(iSettleDate);
        prpJpayRefMainSchema.setPayRefDate(iSettleDate);






        prpJpayRefMainSchema.setCenterCode(blPrpJpayRefRec.getArr(0).getCenterCode());
		
		prpJpayRefMainSchema.setRealPayRefNo(realPayRefNo);
		blPrpJpayRefMain.setArr(prpJpayRefMainSchema);
    	for(int i=0;i<blPrpJpayRefRec.getSize();i++)
    	{
    		blPrpJpayRefRec.getArr(i).setPayRefNo(billNo);
    		blPrpJpayRefRec.getArr(i).setIdentifyType("1");
    		blPrpJpayRefRec.getArr(i).setIdentifyNumber("1");
    		blPrpJpayRefRec.getArr(i).setOperateDate(iSettleDate);
    		blPrpJpayRefRec.getArr(i).setPayRefDate(iSettleDate);
    		
    		blPrpJpayRefRec.getArr(i).setRealPayRefNo(realPayRefNo);
    		
    	}
    	
    	
    	dbPrpJpayRefVoucher.setRealPayRefNo(realPayRefNo);
    	dbPrpJpayRefVoucher.setPayRefNoType("1");
    	dbPrpJpayRefVoucher.setCenterCode(blPrpJpayRefRec.getArr(0).getCenterCode());
    	dbPrpJpayRefVoucher.setVoucherDate(iSettleDate);
    	dbPrpJpayRefVoucher.setComCode("00000000");
    	dbPrpJpayRefVoucher.setPayRefCode("00000000");
    	dbPrpJpayRefVoucher.insert(dbpool);
    	
    	blPrpJpayRefRec.save(dbpool);
    	blPrpJpayRefMain.save(dbpool);
    	blPrpJpayRefDetail.save(dbpool);
    	BLPrpJpaymentVoucher blPrpJpaymentVoucher = new BLPrpJpaymentVoucher();
    	blPrpJpaymentVoucher.callProcedure(dbpool,realPayRefNo);
    }
    
    /**
     *根据结算日期和核算单位代码进行反结算
     *@param iSettleDate 反结算日期
     *@param iCenterCode 反结算的核算单位代码
     *@throws UserException
     *@throws Exception
     */
    public void reverseSettle(String iSettleDate,String iCenterCode) throws UserException,Exception
    {
    	
    	DbPool dbpool = new DbPool();
    	try {
    	    dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
            
            String strWherePart = " CenterFlag IN ('1','2') AND ValidStatus='1'";
            strWherePart += Str.convertString("ComCode",iCenterCode,"=");
            strWherePart += " ORDER BY ComCode";
            blPrpDcompany.query(dbpool,strWherePart,0);
            for(int i=0; i<blPrpDcompany.getSize(); i++){
            	try
            	{
            		dbpool.beginTransaction();
            		this.logPrintln("开始反结算核算单位："+blPrpDcompany.getArr(i).getComCode());
            		this.reverseSettle(dbpool,iSettleDate,blPrpDcompany.getArr(i).getComCode());
                    dbpool.commitTransaction();
            	}catch(Exception e){
                    e.printStackTrace();
                    dbpool.rollbackTransaction();
                }
            }
    	}catch(Exception e){
            dbpool.rollbackTransaction();
            throw e;
        }
        finally {
            dbpool.close();
        }
    }
    
    /**
     *根据结算日期和核算单位代码进行反结算
     *@param iSettleDate 结算日期
     *@param iCenterCode 结算的核算单位代码
     *@throws UserException
     *@throws Exception
     */
    public void reverseSettle(DbPool dbpool,String iSettleDate,String iCenterCode) throws UserException,Exception
    {
    	BLPrpJpayRefDetail blPrpJpayRefDetail = new BLPrpJpayRefDetail();
		BLPrpJpayRefRec blPrpJpayRefRecAll = new BLPrpJpayRefRec();
    	BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
    	BLPrpJplanFee blPrpJplanfee = new BLPrpJplanFee();
    	BLPrpJsettle blPrpJsettle = new BLPrpJsettle();
    	DBPrpJsettle dbPrpJsettle = new DBPrpJsettle();
    	
    	
        Bill bill = new Bill();
        String billNo = "";
        int intYear = Integer.parseInt(iSettleDate.substring(0,4));
        billNo = bill.getNo("prpjpayrec","0501","00000000",intYear,"00");
        
        
    	DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
    	String realPayRefNo = dbPrpJpayRefVoucher.genRealPayRefNo();
    	
    	
    	String strWhereComCode = " AND ComCode IN (" +
    			"SELECT ComCode FROM PrpDcompany START WITH ComCode='"+iCenterCode+
    			"' CONNECT BY PRIOR ComCode=UpperComcode " +
    			" AND (CenterFlag NOT IN ('1','2') OR CenterFlag IS NULL))";
    	
    	String strWherePart = " LeftSettleFee<0 "+strWhereComCode;
    	blPrpJsettle.initArr();
    	blPrpJsettle.query(dbpool,strWherePart,0);
    	for(int i=0;i<blPrpJsettle.getSize();i++)
    	{
    		double realPayFee = 0;
    		boolean blnFlag = false; 
    		BLPrpJsettle blPrpJsettleFor = new BLPrpJsettle();
    		strWherePart = " EndStatus='0' AND SettleCertiNo='"+blPrpJsettle.getArr(i).getCertiNo()+
    				"' AND SettleSerialNo='"+blPrpJsettle.getArr(i).getSerialNo()+
    				"' AND SettlePayRefTimes='"+blPrpJsettle.getArr(i).getPayRefTimes()+
    				"' AND Currency2='"+blPrpJsettle.getArr(i).getCurrency2()+"'"+strWhereComCode+
    				" order by SettleDate desc";
    		blPrpJsettleFor.query(dbpool,strWherePart,0);
    		for(int j=0;j<blPrpJsettleFor.getSize();j++)
    		{
    			if(Double.parseDouble(blPrpJsettle.getArr(i).getLeftSettleFee())>=0)
    			{
    				
    				break;
    			} else
    			{
    				blnFlag = true;
    				blPrpJsettle.getArr(i).setSettleFee(""+(Double.parseDouble(blPrpJsettle.getArr(i).getSettleFee())
    						-Double.parseDouble(blPrpJsettleFor.getArr(j).getSettleFee())));
    				blPrpJsettle.getArr(i).setLeftSettleFee(""+(Double.parseDouble(blPrpJsettle.getArr(i).getLeftSettleFee())
    						+Double.parseDouble(blPrpJsettleFor.getArr(j).getSettleFee())));
    				BLPrpJpayRefRec blPrpJpayRefRecTmp = new BLPrpJpayRefRec();
    				BLPrpJsettle blPrpJsettleTmp = new BLPrpJsettle();
        			DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
        			DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
        			PrpJsettleSchema prpJsettleForSchema = new PrpJsettleSchema();
        			String payRefTimesOld = blPrpJsettleFor.getArr(j).getPayRefTimes();
        			
        			blPrpJsettleTmp = new BLPrpJsettle();
        			prpJsettleForSchema = blPrpJsettleFor.getArr(j);
        			prpJsettleForSchema.setPayRefTimes(this.getPayRefTimes(dbpool,prpJsettleForSchema,"10"));
        			prpJsettleForSchema.setEndStatus("1");
        			blPrpJsettleTmp.setArr(prpJsettleForSchema);
        			blPrpJsettleTmp.save(dbpool);
                    
        			prpJsettleForSchema = new PrpJsettleSchema();
        			prpJsettleForSchema = blPrpJsettleFor.getArr(j);
        			prpJsettleForSchema.setEndStatus("1");
        			prpJsettleForSchema.setPayRefTimes(this.getPayRefTimes(dbpool,prpJsettleForSchema,"20"));
        			prpJsettleForSchema.setSettleFee(""+(-1)*Double.parseDouble(prpJsettleForSchema.getSettleFee()));
        			prpJsettleForSchema.setSettleDate(iSettleDate);
        			blPrpJsettleTmp = new BLPrpJsettle();
        			blPrpJsettleTmp.setArr(prpJsettleForSchema);
        			blPrpJsettleTmp.save(dbpool);

        			
        			PrpJpayRefRecSchema prpJpayRefRecSchema = new PrpJpayRefRecSchema();
        			strWherePart = " CertiType='"+blPrpJsettleFor.getArr(j).getCertiType()+
        						"' AND CertiNo='"+blPrpJsettleFor.getArr(j).getCertiNo()+
        						"' AND SerialNo='"+blPrpJsettleFor.getArr(j).getSerialNo()+
        						"' AND PayRefReason='"+blPrpJsettleFor.getArr(j).getPayRefReason()+
        						"' AND PayRefTimes='"+payRefTimesOld+"'";
        			blPrpJpayRefRec.query(dbpool,strWherePart,0);
        			
        			
        			String flagTmp = prpJpayRefRecSchema.getFlag();
        			prpJpayRefRecSchema.setFlag(flagTmp+"R");
        			
        			prpJpayRefRecSchema = new PrpJpayRefRecSchema();
        			prpJpayRefRecSchema = blPrpJpayRefRec.getArr(0);
        			blPrpJpayRefRecTmp = new BLPrpJpayRefRec();
        			prpJpayRefRecSchema.setPayRefTimes(this.getPayRefTimes1(dbpool,prpJpayRefRecSchema,"10"));
        			blPrpJpayRefRecTmp.setArr(prpJpayRefRecSchema);
        			blPrpJpayRefRecTmp.save(dbpool);
                    
        			prpJpayRefRecSchema = new PrpJpayRefRecSchema();
        			prpJpayRefRecSchema = blPrpJpayRefRec.getArr(0);
        			prpJpayRefRecSchema.setPayRefTimes(this.getPayRefTimes1(dbpool,prpJpayRefRecSchema,"20"));
        			prpJpayRefRecSchema.setPayRefFee(""+(-1)*Double.parseDouble(prpJpayRefRecSchema.getPayRefFee()));
        			realPayFee = realPayFee + Double.parseDouble(prpJpayRefRecSchema.getPayRefFee());
        			
        			blPrpJpayRefRecAll.setArr(prpJpayRefRecSchema);
        			
        			dbPrpJpayRefRec = new DBPrpJpayRefRec();
        			dbPrpJpayRefRec.delete(dbpool,blPrpJsettleFor.getArr(j).getCertiType(),
        					blPrpJsettleFor.getArr(j).getCertiNo(),
        					blPrpJsettleFor.getArr(j).getSerialNo(),
        					blPrpJsettleFor.getArr(j).getPayRefReason(),
        					payRefTimesOld);
        			
        			
        			
        			
        			
        			
        			
        			
        			blPrpJplanfee.getNewInfo(blPrpJsettleFor.getArr(j).getCertiType(),blPrpJsettleFor.getArr(j).getCertiNo(),
        									blPrpJsettleFor.getArr(j).getSerialNo(),blPrpJsettleFor.getArr(j).getPayRefReason());
        			
        			dbPrpJplanFee.setSchema(blPrpJplanfee.getArr(0));
        			dbPrpJplanFee.setRealPayRefFee("0");
        			
        			dbPrpJplanFee.setPayRefFee("0");
        			
        			dbPrpJplanFee.update(dbpool);
        			
        			
        			dbPrpJsettle = new DBPrpJsettle();
        			dbPrpJsettle.delete(dbpool,blPrpJsettleFor.getArr(j).getCertiType(),
        					blPrpJsettleFor.getArr(j).getCertiNo(),
        					blPrpJsettleFor.getArr(j).getSerialNo(),
        					blPrpJsettleFor.getArr(j).getPayRefReason(),
        					payRefTimesOld,
        					blPrpJsettleFor.getArr(j).getSettleStatus(),
        					blPrpJsettleFor.getArr(j).getCurrency2());
        			
        			
					DBPrpCplan dbPrpCplan = new DBPrpCplan();
					DBPrpCPplan dbPrpCPplan = new DBPrpCPplan();
					if(dbPrpJplanFee.getCertiType().equals("P")){
            	        int intReturn = dbPrpCplan.getInfo(dbpool, dbPrpJplanFee.getCertiNo(),
            	        		  dbPrpJplanFee.getSerialNo());
                        if(intReturn == 0){
                        	dbPrpCplan.setDelinquentFee("" +
                                    (Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee())) +
                                            Double.parseDouble(ChgData.chgStrZero(dbPrpJplanFee.getPlanFee()))));
                        	dbPrpCplan.update(dbpool);
                        }
                        intReturn = dbPrpCPplan.getInfo(dbpool, dbPrpJplanFee.getCertiNo(),
          	        		  dbPrpJplanFee.getSerialNo());
                        if(intReturn == 0){
                        	dbPrpCPplan.setDelinquentFee("" +
                                    (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee())) +
                                            Double.parseDouble(ChgData.chgStrZero(dbPrpJplanFee.getPlanFee()))));
                        	dbPrpCPplan.update(dbpool);
                        }
                    }else if(dbPrpJplanFee.getCertiType().equals("E")){
                    	DBPrpCplan dbPrpCplanE = new DBPrpCplan();
    					DBPrpCPplan dbPrpCPplanE = new DBPrpCPplan();
                        BLPrpCplan blPrpCplan = new BLPrpCplan();
                        BLPrpCPplan blPrpCPplan = new BLPrpCPplan();
                        String strWhere = " EndorseNo='" + dbPrpJplanFee.getCertiNo()+"' ";
                        blPrpCplan.query(dbpool,strWhere,0);
                        if(blPrpCplan.getSize()>0){
                        	dbPrpCplanE.setSchema(blPrpCplan.getArr(0));
                        	dbPrpCplanE.setDelinquentFee("" +
                                    (Double.parseDouble(ChgData.chgStrZero(dbPrpCplanE.getDelinquentFee())) +
                                            Double.parseDouble(ChgData.chgStrZero(dbPrpJplanFee.getPlanFee()))));
                        	dbPrpCplanE.update(dbpool);
                        }
                        strWhere = " EndorseNo='" + dbPrpJplanFee.getCertiNo()+"' ";
                        blPrpCPplan.query(dbpool,strWhere,0);
                        if(blPrpCPplan.getSize()>0){
                        	dbPrpCPplanE.setSchema(blPrpCPplan.getArr(0));
                        	dbPrpCPplanE.setDelinquentFee("" +
                                    (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplanE.getDelinquentFee())) +
                                            Double.parseDouble(ChgData.chgStrZero(dbPrpJplanFee.getPlanFee()))));
                        	dbPrpCPplanE.update(dbpool);
                        }
                    }
					
					if ((prpJpayRefRecSchema.getCertiType().equals("P") || prpJpayRefRecSchema
							.getCertiType().equals("E"))
							&& !prpJpayRefRecSchema.getPayRefReason().equals("R72")
							&& !prpJpayRefRecSchema.getPayRefReason().equals("R73")
							&& !prpJpayRefRecSchema.getPayRefReason().equals("R74")) {
						StringBuffer SQLBuffer = null;
						BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
						DBPrpJplanCommission dbPrpJplanCommission = null;
						
						SQLBuffer = new StringBuffer();
						SQLBuffer.append(" CertiNo='" + prpJpayRefRecSchema.getCertiNo() + "'");
						SQLBuffer.append(" And Serialno='" + prpJpayRefRecSchema.getSerialNo()
								+ "'");
						SQLBuffer.append(" And PayRefReason ='P90'");
						blPrpJplanCommission.query(dbpool, SQLBuffer.toString(), 0);

						for (int n = 0; n < blPrpJplanCommission.getSize(); n++) {
							dbPrpJplanCommission = new DBPrpJplanCommission();
							dbPrpJplanCommission.setSchema(blPrpJplanCommission.getArr(n));
							if (!dbPrpJplanCommission.getRealPayRefFee().equals(
									dbPrpJplanCommission.getPlanFee())) {
								dbPrpJplanCommission.setPoliPayRefDate("");
								dbPrpJplanCommission.setPayFlag("0");
							}
							dbPrpJplanCommission.setRealRefPremium("0.00");
							dbPrpJplanCommission.update(dbpool);
						}

						
						SQLBuffer = new StringBuffer();
						SQLBuffer.append(" PolicyNo='" + prpJpayRefRecSchema.getPolicyNo() + "'");
						SQLBuffer.append(" And Serialno='" + prpJpayRefRecSchema.getSerialNo()
								+ "'");
						SQLBuffer.append(" And PayRefReason ='P93'");
						blPrpJplanCommission.query(dbpool, SQLBuffer.toString(), 0);

						for (int n = 0; n < blPrpJplanCommission.getSize(); n++) {
							dbPrpJplanCommission = new DBPrpJplanCommission();
							dbPrpJplanCommission.setSchema(blPrpJplanCommission.getArr(n));
							if (!dbPrpJplanCommission.getRealPayRefFee().equals(
									dbPrpJplanCommission.getPlanFee())) {
								dbPrpJplanCommission.setPoliPayRefDate("");
								dbPrpJplanCommission.setPayFlag("0");
							}
							dbPrpJplanCommission.setRealRefPremium("0.00");
							dbPrpJplanCommission.update(dbpool);
						}
					}
					
    			}
    		}
    		
    		dbPrpJsettle = new DBPrpJsettle();
    		dbPrpJsettle.setSchema(blPrpJsettle.getArr(i));
    		dbPrpJsettle.update(dbpool);
    		
    		
	    	PrpJpayRefDetailSchema prpJpayRefDetailSchema = new PrpJpayRefDetailSchema();
	    	prpJpayRefDetailSchema.setPayRefNo(billNo);
	    	prpJpayRefDetailSchema.setRealPayRefNo(realPayRefNo);
	    	prpJpayRefDetailSchema.setSerialNo(""+(i+1));
	    	prpJpayRefDetailSchema.setPayWay("410");
	    	prpJpayRefDetailSchema.setCurrency2(blPrpJsettle.getArr(i).getCurrency2());
	    	prpJpayRefDetailSchema.setPayRefFee(""+realPayFee);
	    	prpJpayRefDetailSchema.setPayRefDate(iSettleDate);
	    	prpJpayRefDetailSchema.setComCode(blPrpJsettle.getArr(i).getComCode());
	    	prpJpayRefDetailSchema.setHandler1Code(blPrpJsettle.getArr(i).getHandler1Code());
	    	if(blnFlag){
	    		blPrpJpayRefDetail.setArr(prpJpayRefDetailSchema);
	    	}
    	}
    	
    	
    	
    	if(blPrpJpayRefRecAll.getSize()!=0)
    	{
    		
    	}else
    	{
    		return;
    	}
    	
    	
    	
    	BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
    	PrpJpayRefMainSchema prpJpayRefMainSchema = new PrpJpayRefMainSchema();
    	
    	
        prpJpayRefMainSchema.setSerialNo("1");
        prpJpayRefMainSchema.setPayRefNoType("1");
        prpJpayRefMainSchema.setCurrency2(blPrpJpayRefRecAll.getArr(0).getCurrency2());
        prpJpayRefMainSchema.setPackageUnit("00000000");
        
        double allRealPayFee = 0;
    	for(int i=0;i<blPrpJpayRefRecAll.getSize();i++)
    	{
    		allRealPayFee = allRealPayFee+Double.parseDouble(blPrpJpayRefRecAll.getArr(i).getPayRefFee());
    	}
        prpJpayRefMainSchema.setPayRefFee(""+allRealPayFee);
        prpJpayRefMainSchema.setPayRefNo(billNo);
        prpJpayRefMainSchema.setPackageDate(iSettleDate);
        prpJpayRefMainSchema.setPayRefDate(iSettleDate);






        prpJpayRefMainSchema.setCenterCode(blPrpJpayRefRecAll.getArr(0).getCenterCode());
		
		prpJpayRefMainSchema.setRealPayRefNo(realPayRefNo);
		blPrpJpayRefMain.setArr(prpJpayRefMainSchema);
    	for(int i=0;i<blPrpJpayRefRecAll.getSize();i++)
    	{
    		blPrpJpayRefRecAll.getArr(i).setPayRefNo(billNo);
    		blPrpJpayRefRecAll.getArr(i).setIdentifyType("1");
    		blPrpJpayRefRecAll.getArr(i).setIdentifyNumber("1");
    		blPrpJpayRefRecAll.getArr(i).setOperateDate(iSettleDate);
    		blPrpJpayRefRecAll.getArr(i).setPayRefDate(iSettleDate);
    		
    		blPrpJpayRefRecAll.getArr(i).setRealPayRefNo(realPayRefNo);
    		
    	}
    	
    	
    	dbPrpJpayRefVoucher.setRealPayRefNo(realPayRefNo);
    	dbPrpJpayRefVoucher.setPayRefNoType("1");
    	dbPrpJpayRefVoucher.setCenterCode(blPrpJpayRefRecAll.getArr(0).getCenterCode());
    	dbPrpJpayRefVoucher.setVoucherDate(iSettleDate);
    	dbPrpJpayRefVoucher.setComCode("00000000");
    	dbPrpJpayRefVoucher.setPayRefCode("00000000");
    	dbPrpJpayRefVoucher.insert(dbpool);
    	
    	blPrpJpayRefRecAll.save(dbpool);
    	blPrpJpayRefMain.save(dbpool);
    	blPrpJpayRefDetail.save(dbpool);
    	BLPrpJpaymentVoucher blPrpJpaymentVoucher = new BLPrpJpaymentVoucher();
    	blPrpJpaymentVoucher.callProcedure(dbpool,realPayRefNo);
    }
    
    /**
     *预约协议往结算表送数
     *@param dbpool 数据库连接池
     *@param prpJpayRefRecSchema 转数实体对象
     *@param iSettleStatus 结算状态：0收款 1取消收款 2结算
     *@throws UserException
     *@throws Exception
     */
    public void transData(DbPool dbpool,PrpJpayRefRecSchema prpJpayRefRecSchema,String iSettleStatus) throws UserException,Exception
    {
    	PrpJsettleSchema prpJsettleSchema = new PrpJsettleSchema();
    	prpJsettleSchema.setCertiType(prpJpayRefRecSchema.getCertiType());
    	prpJsettleSchema.setCertiNo(prpJpayRefRecSchema.getCertiNo());
    	prpJsettleSchema.setPolicyNo(prpJpayRefRecSchema.getPolicyNo());
    	prpJsettleSchema.setSerialNo(prpJpayRefRecSchema.getSerialNo());
    	prpJsettleSchema.setPayRefReason(prpJpayRefRecSchema.getPayRefReason());
    	prpJsettleSchema.setPayRefTimes(prpJpayRefRecSchema.getPayRefTimes());
    	prpJsettleSchema.setSettleStatus(iSettleStatus);
    	prpJsettleSchema.setClassCode(prpJpayRefRecSchema.getClassCode());
    	prpJsettleSchema.setRiskCode(prpJpayRefRecSchema.getRiskCode());
    	prpJsettleSchema.setContractNo(prpJpayRefRecSchema.getContractNo());
    	prpJsettleSchema.setStartDate(prpJpayRefRecSchema.getStartDate());
    	prpJsettleSchema.setEndDate(prpJpayRefRecSchema.getEndDate());
    	prpJsettleSchema.setValidDate(prpJpayRefRecSchema.getValidDate());
    	prpJsettleSchema.setPayNo(prpJpayRefRecSchema.getPayNo());
    	prpJsettleSchema.setCurrency1(prpJpayRefRecSchema.getCurrency1());
    	prpJsettleSchema.setPlanFee(prpJpayRefRecSchema.getPlanFee());
    	prpJsettleSchema.setComCode(prpJpayRefRecSchema.getComCode());
    	prpJsettleSchema.setMakeCom(prpJpayRefRecSchema.getMakeCom());
    	prpJsettleSchema.setHandler1Code(prpJpayRefRecSchema.getHandler1Code());
    	prpJsettleSchema.setUnderWriteDate(prpJpayRefRecSchema.getUnderWriteDate());
    	prpJsettleSchema.setSettleDate(prpJpayRefRecSchema.getPayRefDate());
    	prpJsettleSchema.setExchangeRate(prpJpayRefRecSchema.getExchangeRate());
    	prpJsettleSchema.setCurrency2(prpJpayRefRecSchema.getCurrency2());
    	prpJsettleSchema.setSettleFee(""+(Double.parseDouble(prpJpayRefRecSchema.getPlanFee())
							-Double.parseDouble(prpJpayRefRecSchema.getPayRefFee())));
    	prpJsettleSchema.setLeftSettleFee(prpJpayRefRecSchema.getPayRefFee());
	    prpJsettleSchema.setSettleCertiNo("");
	    prpJsettleSchema.setSettleSerialNo("");
	    prpJsettleSchema.setSettlePayRefTimes("");
    	prpJsettleSchema.setEndStatus("0");
    	prpJsettleSchema.setRemark("");
    	BLPrpJsettle blPrpJsettle = new BLPrpJsettle();
    	blPrpJsettle.setArr(prpJsettleSchema);
    	blPrpJsettle.save(dbpool);
    }
    
    /**
     *小XX结算后往结算表送数
     *@param dbpool 数据库连接池
     *@param prpJpayRefRecSchema 转数实体对象
     *@param iSettleCertiNo 被结算预约协议CertiNo
     *@param iSettleSerialNo 被结算预约协议SerialNo
     *@param iSettlePayRefTimes 被结算预约协议PayRefTimes
     *@throws UserException
     *@throws Exception
     */
    public void transData(DbPool dbpool,PrpJpayRefRecSchema prpJpayRefRecSchema,
    		String iSettleCertiNo,String iSettleSerialNo,String iSettlePayRefTimes) throws UserException,Exception
    {
    	PrpJsettleSchema prpJsettleSchema = new PrpJsettleSchema();
    	prpJsettleSchema.setCertiType(prpJpayRefRecSchema.getCertiType());
    	prpJsettleSchema.setCertiNo(prpJpayRefRecSchema.getCertiNo());
    	prpJsettleSchema.setPolicyNo(prpJpayRefRecSchema.getPolicyNo());
    	prpJsettleSchema.setSerialNo(prpJpayRefRecSchema.getSerialNo());
    	prpJsettleSchema.setPayRefReason(prpJpayRefRecSchema.getPayRefReason());
    	prpJsettleSchema.setPayRefTimes(prpJpayRefRecSchema.getPayRefTimes());
    	prpJsettleSchema.setSettleStatus("2");
    	prpJsettleSchema.setClassCode(prpJpayRefRecSchema.getClassCode());
    	prpJsettleSchema.setRiskCode(prpJpayRefRecSchema.getRiskCode());
    	prpJsettleSchema.setContractNo(prpJpayRefRecSchema.getContractNo());
    	prpJsettleSchema.setStartDate(prpJpayRefRecSchema.getStartDate());
    	prpJsettleSchema.setEndDate(prpJpayRefRecSchema.getEndDate());
    	prpJsettleSchema.setValidDate(prpJpayRefRecSchema.getValidDate());
    	prpJsettleSchema.setPayNo(prpJpayRefRecSchema.getPayNo());
    	prpJsettleSchema.setCurrency1(prpJpayRefRecSchema.getCurrency1());
    	prpJsettleSchema.setPlanFee(prpJpayRefRecSchema.getPlanFee());
    	prpJsettleSchema.setComCode(prpJpayRefRecSchema.getComCode());
    	prpJsettleSchema.setMakeCom(prpJpayRefRecSchema.getMakeCom());
    	prpJsettleSchema.setHandler1Code(prpJpayRefRecSchema.getHandler1Code());
    	prpJsettleSchema.setUnderWriteDate(prpJpayRefRecSchema.getUnderWriteDate());
    	prpJsettleSchema.setSettleDate(prpJpayRefRecSchema.getPayRefDate());
    	prpJsettleSchema.setExchangeRate(prpJpayRefRecSchema.getExchangeRate());
    	prpJsettleSchema.setCurrency2(prpJpayRefRecSchema.getCurrency2());
    	prpJsettleSchema.setSettleFee(prpJpayRefRecSchema.getPlanFee());
    	prpJsettleSchema.setLeftSettleFee("0");
	    prpJsettleSchema.setSettleCertiNo(iSettleCertiNo);
	    prpJsettleSchema.setSettleSerialNo(iSettleSerialNo);
	    prpJsettleSchema.setSettlePayRefTimes(iSettlePayRefTimes);
    	prpJsettleSchema.setEndStatus("0");
    	prpJsettleSchema.setRemark("");
    	BLPrpJsettle blPrpJsettle = new BLPrpJsettle();
    	blPrpJsettle.setArr(prpJsettleSchema);
    	blPrpJsettle.save(dbpool);
    }

    /**
     * @desc 生成XX实收表信息 
     * @param blPrpJplanFeeSchema
     * @param iSettleDate 日期
     * @throws UserException
     * @throws Exception
     */
    public PrpJpayRefRecSchema transToPayRefRec(PrpJplanFeeSchema blPrpJplanFeeSchema,String iSettleDate) throws UserException,Exception
    {
    	PrpJpayRefRecSchema blPrpJpayRefRecSchema = new PrpJpayRefRecSchema();
		blPrpJpayRefRecSchema.setPayRefTimes("1");
		blPrpJpayRefRecSchema.setOperateDate(iSettleDate);
		blPrpJpayRefRecSchema.setCertiType(blPrpJplanFeeSchema.getCertiType());
		blPrpJpayRefRecSchema.setCertiNo(blPrpJplanFeeSchema.getCertiNo());
		blPrpJpayRefRecSchema.setPolicyNo(blPrpJplanFeeSchema.getPolicyNo());
		blPrpJpayRefRecSchema.setSerialNo(blPrpJplanFeeSchema.getSerialNo());
		blPrpJpayRefRecSchema.setPayRefReason(blPrpJplanFeeSchema.getPayRefReason());
		blPrpJpayRefRecSchema.setClaimNo(blPrpJplanFeeSchema.getClaimNo());
		blPrpJpayRefRecSchema.setClassCode(blPrpJplanFeeSchema.getClassCode());
		blPrpJpayRefRecSchema.setRiskCode(blPrpJplanFeeSchema.getRiskCode());
		blPrpJpayRefRecSchema.setContractNo(blPrpJplanFeeSchema.getContractNo());
		blPrpJpayRefRecSchema.setAppliCode(blPrpJplanFeeSchema.getAppliCode());
		blPrpJpayRefRecSchema.setAppliName(blPrpJplanFeeSchema.getAppliName());
		blPrpJpayRefRecSchema.setInsuredCode(blPrpJplanFeeSchema.getInsuredCode());
		blPrpJpayRefRecSchema.setInsuredName(blPrpJplanFeeSchema.getInsuredName());
		blPrpJpayRefRecSchema.setStartDate(blPrpJplanFeeSchema.getStartDate());
		blPrpJpayRefRecSchema.setEndDate(blPrpJplanFeeSchema.getEndDate());
		blPrpJpayRefRecSchema.setValidDate(blPrpJplanFeeSchema.getValidDate());
		blPrpJpayRefRecSchema.setPayNo(blPrpJplanFeeSchema.getPayNo());
		blPrpJpayRefRecSchema.setCurrency1(blPrpJplanFeeSchema.getCurrency1());
		blPrpJpayRefRecSchema.setPlanFee(blPrpJplanFeeSchema.getPlanFee());
		blPrpJpayRefRecSchema.setPlanDate(blPrpJplanFeeSchema.getPlanDate());
		blPrpJpayRefRecSchema.setComCode(blPrpJplanFeeSchema.getComCode());
		blPrpJpayRefRecSchema.setMakeCom(blPrpJplanFeeSchema.getMakeCom());
		blPrpJpayRefRecSchema.setAgentCode(blPrpJplanFeeSchema.getAgentCode());
		blPrpJpayRefRecSchema.setHandler1Code(blPrpJplanFeeSchema.getHandler1Code());
		blPrpJpayRefRecSchema.setHandlerCode(blPrpJplanFeeSchema.getHandlerCode());
		blPrpJpayRefRecSchema.setUnderWriteDate(blPrpJplanFeeSchema.getUnderWriteDate());
		blPrpJpayRefRecSchema.setCoinsFlag(blPrpJplanFeeSchema.getCoinsFlag());
		blPrpJpayRefRecSchema.setCoinsCode(blPrpJplanFeeSchema.getCoinsCode());
		blPrpJpayRefRecSchema.setCoinsName(blPrpJplanFeeSchema.getCoinsName());
		blPrpJpayRefRecSchema.setCoinsType(blPrpJplanFeeSchema.getCoinsType());
		blPrpJpayRefRecSchema.setExchangeRate(blPrpJplanFeeSchema.getExchangeRate());
		blPrpJpayRefRecSchema.setCarNatureCode(blPrpJplanFeeSchema.getCarNatureCode());
		blPrpJpayRefRecSchema.setUseNatureCode(blPrpJplanFeeSchema.getUseNatureCode());
		blPrpJpayRefRecSchema.setCarProperty(blPrpJplanFeeSchema.getCarProperty());    
		blPrpJpayRefRecSchema.setCurrency2(blPrpJplanFeeSchema.getCurrency1());
		blPrpJpayRefRecSchema.setPayRefFee(blPrpJplanFeeSchema.getPlanFee());
		blPrpJpayRefRecSchema.setPayRefDate(iSettleDate);
		
		blPrpJpayRefRecSchema.setFlag("0");
		
		
		blPrpJpayRefRecSchema.setCenterCode(blPrpJplanFeeSchema.getCenterCode());
		blPrpJpayRefRecSchema.setBusinessNature(blPrpJplanFeeSchema.getBusinessNature());
        return blPrpJpayRefRecSchema;
    }
    
    /**
     * @desc 生成小XX实收凭证
     * @param dbpool
     * @param prpjpayrefrecschema 小XX实收信息对象
     * @param iCenterCode 核算单位
     * @param iSettleDate 结算、反结算日期
     * @param flag 0:结算 1：反结算
     * @throws Exception
     */
    public BLAccMainVoucher createVoucher(DbPool dbpool, BLPrpJpayRefRec blprpjpayrefrec,
    		String iCenterCode,String iSettleDate,String flag)
    	throws UserException,Exception{
    	BLAccMainVoucher blAccMainVoucher = new BLAccMainVoucher();
        
		String strAccBookType = "02";
		String strAccBookCode = "11";		
	    
	    String strYearMonth = iSettleDate.substring(0,4)+iSettleDate.substring(5,7);
	    
	    /****************************生成小XX实收凭证*******************************/
		BLAccVoucher blAccVoucher = new BLAccVoucher();
		
		AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
		accMainVoucherSchema.setAccBookType(strAccBookType);
		accMainVoucherSchema.setAccBookCode(strAccBookCode);
		accMainVoucherSchema.setCenterCode(iCenterCode);
		accMainVoucherSchema.setBranchCode(iCenterCode);
		accMainVoucherSchema.setAuxNumber(""+blprpjpayrefrec.getSize());
		accMainVoucherSchema.setOperatorCode("00000000");
		accMainVoucherSchema.setOperatorBranch(iCenterCode);
		accMainVoucherSchema.setVoucherDate(iSettleDate);
		accMainVoucherSchema.setVoucherType("1");
		accMainVoucherSchema.setGenerateWay("6");
		accMainVoucherSchema.setVoucherFlag("1");
		accMainVoucherSchema.setYearMonth(strYearMonth);			
		blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);
		
		AccSubVoucherSchema accSubVoucherSchema = null;
		BLAccSubVoucher blAccSubVoucher = new BLAccSubVoucher();
		double dblExchRate = 1;
		for(int i=0;i<blprpjpayrefrec.getSize();i++){
			
			for(int j = 0;j<2;j++){
				accSubVoucherSchema = new AccSubVoucherSchema();
				accSubVoucherSchema.setAccBookType(strAccBookType);
				accSubVoucherSchema.setAccBookCode(strAccBookCode);
				accSubVoucherSchema.setCenterCode(iCenterCode);
				accSubVoucherSchema.setYearMonth(strYearMonth);
				accSubVoucherSchema.setCurrency(blprpjpayrefrec.getArr(i).getCurrency2());
				accSubVoucherSchema.setF01(blprpjpayrefrec.getArr(i).getClassCode());
				accSubVoucherSchema.setF05(blprpjpayrefrec.getArr(i).getRiskCode());
				accSubVoucherSchema.setF28(blprpjpayrefrec.getArr(i).getComCode());
				accSubVoucherSchema.setF27(blprpjpayrefrec.getArr(i).getHandlerCode());
				accSubVoucherSchema.setF26(iCenterCode);
				
				dblExchRate = Double.parseDouble(blprpjpayrefrec.getArr(i).getExchangeRate());				
				accSubVoucherSchema.setExchangeRate(""+dblExchRate);
				if(j==0){
					accSubVoucherSchema.setF03(iSettleDate);
					if(flag.equals("0"))
					{
						accSubVoucherSchema.setRemark("转入"+iSettleDate + "预约协议下小XX到款确认");
					}else
					{
						accSubVoucherSchema.setRemark("对冲"+blprpjpayrefrec.getArr(i).getPayRefDate() + "预约协议下小XX到款确认");
					}
					accSubVoucherSchema.setItemCode("2203");
					accSubVoucherSchema.setDirectionIdx("01/");
					accSubVoucherSchema.setDebitSource(blprpjpayrefrec.getArr(i).getPayRefFee());
					double dblCreditDest = Double.parseDouble(blprpjpayrefrec.getArr(i).getPayRefFee())*dblExchRate;
					dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
					accSubVoucherSchema.setDebitDest(""+dblCreditDest);
					accSubVoucherSchema.setCreditSource("0");
					accSubVoucherSchema.setCreditDest("0");
				}
				if(j==1){
					String startDate = "";
					if(blprpjpayrefrec.getArr(i).getCertiType().equals("P"))
					{
						if(blprpjpayrefrec.getArr(i).getStartDate().compareTo(blprpjpayrefrec.getArr(i).getUnderWriteDate()) >= 0)
						{
							startDate = blprpjpayrefrec.getArr(i).getStartDate();
						}else
						{
							startDate = blprpjpayrefrec.getArr(i).getUnderWriteDate();
						}
					}
					else if(blprpjpayrefrec.getArr(i).getCertiType().equals("E"))
					{
						if(blprpjpayrefrec.getArr(i).getValidDate().compareTo(blprpjpayrefrec.getArr(i).getUnderWriteDate()) >= 0)
						{
							startDate = blprpjpayrefrec.getArr(i).getValidDate();
						}else
						{
							startDate = blprpjpayrefrec.getArr(i).getUnderWriteDate();
						}
					}
					if(blprpjpayrefrec.getArr(i).getPayRefDate().compareTo(startDate) >= 0)
					{
						
						accSubVoucherSchema.setF03(iSettleDate);
						if(flag.equals("0"))
						{
							accSubVoucherSchema.setRemark("转入"+iSettleDate + "预约协议下小XX到款确认");
						}else
						{
							accSubVoucherSchema.setRemark("对冲"+blprpjpayrefrec.getArr(i).getPayRefDate() + "预约协议下小XX到款确认");
						}
						accSubVoucherSchema.setItemCode("1122");
						accSubVoucherSchema.setDirectionIdx("00");			
						accSubVoucherSchema.setDebitSource("0");
						accSubVoucherSchema.setDebitDest("0");
						accSubVoucherSchema.setCreditSource(blprpjpayrefrec.getArr(i).getPayRefFee());
						double dblCreditDest = Double.parseDouble(blprpjpayrefrec.getArr(i).getPayRefFee())*dblExchRate;
						dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
						accSubVoucherSchema.setCreditDest(""+dblCreditDest);
					} else
					{
						
						accSubVoucherSchema.setF03(iSettleDate);
						if(flag.equals("0"))
						{
							accSubVoucherSchema.setRemark("转入"+iSettleDate + "预约协议下小XX到款确认");
						}else
						{
							accSubVoucherSchema.setRemark("对冲"+blprpjpayrefrec.getArr(i).getPayRefDate() + "预约协议下小XX到款确认");
						}
						accSubVoucherSchema.setItemCode("2203");
						accSubVoucherSchema.setDirectionIdx("02/");
						accSubVoucherSchema.setDebitSource("0");
						accSubVoucherSchema.setDebitDest("0");
						accSubVoucherSchema.setCreditSource(blprpjpayrefrec.getArr(i).getPayRefFee());
						double dblCreditDest = Double.parseDouble(blprpjpayrefrec.getArr(i).getPayRefFee())*dblExchRate;
						dblCreditDest = Str.round(Str.round(dblCreditDest,8),2);
						accSubVoucherSchema.setCreditDest(""+dblCreditDest);
					}
				}
				blAccSubVoucher.setArr(accSubVoucherSchema);
			}
		}
		
		for(int n=0; n<blAccSubVoucher.getSize(); n++){
			blAccSubVoucher.getArr(n).setSuffixNo("" + (n + 1));
			blAccSubVoucher.createRawArticle(dbpool,blAccSubVoucher.getArr(n),"");
		}
		blAccSubVoucher.voucherComp("111");
		blAccSubVoucher.voucherOrder();
		blAccVoucher.setBLAccSubVoucher(blAccSubVoucher);
		blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());
		blAccVoucher.save(dbpool);
		blAccMainVoucher = blAccVoucher.getBLAccMainVoucher();
    	return blAccMainVoucher;
    }

    /**
     * @desc 生成小XX实收凭证
     * @param dbpool
     * @param prpjpayrefrecschema 小XX实收信息对象
     * @param iCenterCode 核算单位
     * @param iSettleDate 结算、反结算日期
     * @param flag 0:结算 1：反结算
     * @throws Exception
     */
    public BLAccMainVoucher voucher(DbPool dbpool, BLPrpJpayRefRec blprpjpayrefrec,String iCenterCode,String iSettleDate,String flag)
	throws UserException,Exception{
    	
    	DBPrpJpayRefVoucher dbPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
    	String realPayRefNo = dbPrpJpayRefVoucher.genRealPayRefNo();
    	
    	
    	BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
    	PrpJpayRefMainSchema prpJpayRefMainSchema = new PrpJpayRefMainSchema();
    	BLPrpJpayRefDetail blPrpJpayRefDetail = new BLPrpJpayRefDetail();
    	PrpJpayRefDetailSchema prpJpayRefDetailSchema = new PrpJpayRefDetailSchema();
        
        Bill bill = new Bill();
        String billNo = "";
        int intYear = Integer.parseInt(iSettleDate.substring(0,4));
        billNo = bill.getNo("prpjpayrec","0501","00000000",intYear,"00");
    	
    	BLAccMainVoucher blAccMainVoucher = createVoucher(dbpool,blprpjpayrefrec,iCenterCode,iSettleDate,flag);
    	
        prpJpayRefMainSchema.setSerialNo("1");
        prpJpayRefMainSchema.setPayRefNoType("1");
        prpJpayRefMainSchema.setCurrency2(blprpjpayrefrec.getArr(0).getCurrency2());
        prpJpayRefMainSchema.setPackageUnit("00000000");
        
        double allRealPayFee = 0;
    	for(int i=0;i<blprpjpayrefrec.getSize();i++)
    	{
    		allRealPayFee = allRealPayFee+Double.parseDouble(blprpjpayrefrec.getArr(i).getPayRefFee());
    	}
        prpJpayRefMainSchema.setPayRefFee(""+allRealPayFee);
        prpJpayRefMainSchema.setPayRefNo(billNo);
        prpJpayRefMainSchema.setPackageDate(iSettleDate);
        prpJpayRefMainSchema.setPayRefDate(iSettleDate);
        prpJpayRefMainSchema.setAccBookType(blAccMainVoucher.getArr(0).getAccBookType());
        prpJpayRefMainSchema.setAccBookCode(blAccMainVoucher.getArr(0).getAccBookCode());
        prpJpayRefMainSchema.setCenterCode(blAccMainVoucher.getArr(0).getCenterCode());
        prpJpayRefMainSchema.setBranchCode(blAccMainVoucher.getArr(0).getBranchCode());
		prpJpayRefMainSchema.setYearMonth(blAccMainVoucher.getArr(0).getYearMonth());
		prpJpayRefMainSchema.setVoucherNo(blAccMainVoucher.getArr(0).getVoucherNo());
		
		prpJpayRefMainSchema.setRealPayRefNo(realPayRefNo);
		blPrpJpayRefMain.setArr(prpJpayRefMainSchema);
    	for(int i=0;i<blprpjpayrefrec.getSize();i++)
    	{
    		blprpjpayrefrec.getArr(i).setPayRefNo(billNo);
            
    		blprpjpayrefrec.getArr(i).setIdentifyType("1");
    		blprpjpayrefrec.getArr(i).setIdentifyNumber("1");
            
    		blprpjpayrefrec.getArr(i).setOperateDate(iSettleDate);
    		blprpjpayrefrec.getArr(i).setPayRefDate(iSettleDate);
    		
    		blprpjpayrefrec.getArr(i).setRealPayRefNo(realPayRefNo);
    	}
    	
    	
    	prpJpayRefDetailSchema.setPayRefNo(billNo);
    	prpJpayRefDetailSchema.setRealPayRefNo(realPayRefNo);
    	prpJpayRefDetailSchema.setSerialNo("1");
    	prpJpayRefDetailSchema.setPayWay("410");
    	prpJpayRefDetailSchema.setCurrency2(blprpjpayrefrec.getArr(0).getCurrency2());
    	prpJpayRefDetailSchema.setPayRefFee(""+allRealPayFee);
    	prpJpayRefDetailSchema.setPayRefDate(iSettleDate);
    	prpJpayRefDetailSchema.setComCode(blprpjpayrefrec.getArr(0).getComCode());
    	prpJpayRefDetailSchema.setHandler1Code(blprpjpayrefrec.getArr(0).getHandler1Code());
    	blPrpJpayRefDetail.setArr(prpJpayRefDetailSchema);
    	
    	
    	dbPrpJpayRefVoucher.setRealPayRefNo(realPayRefNo);
    	dbPrpJpayRefVoucher.setPayRefNoType("1");
    	dbPrpJpayRefVoucher.insert(dbpool);
    	
    	blprpjpayrefrec.save(dbpool);
    	blPrpJpayRefMain.save(dbpool);
    	blPrpJpayRefDetail.save(dbpool);
    	
    	if(flag.equals("0"))
    	{
    		this.logPrintln("结算生成凭证号："+blAccMainVoucher.getArr(0).getVoucherNo());
    	}
    	if(flag.equals("1"))
    	{
    		this.logPrintln("反结算生成凭证号："+blAccMainVoucher.getArr(0).getVoucherNo());
    	}
    	return blAccMainVoucher;
    }
    
    /**
     * @desc 历史数据处理
     * @throws Exception
     */
    public void dealDataHis(String dealDate)
	throws UserException,Exception{
    	
    	DbPool dbpool = new DbPool();
    	try {
    	    dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
    		this.logPrintln("开始处理历史数据：");
    		dbpool.beginTransaction();
    		
        	
    		BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
        	String strWherePart = "";
    		strWherePart = "select " +
    						"  a.policyno," +
    						"  b.currency2,"+
    						"  sum(a.realpayreffee) as realpayreffee, " +
    						"  sum(a.planfee) as planfee " +
    						"from prpjplanfee a,prpjpayrefrec b "+
    						"where a.CertiType IN ('P','E') " +
    						"  AND (a.ContractNo='' or a.ContractNo is null) " +
    						"  AND a.riskcode='YAB0' " +
    						"  and a.certitype=b.certitype "+
    						"  and a.policyno=b.policyno "+
    						"  and a.serialno=b.serialno "+
    						"  and a.payrefreason=b.payrefreason "+
    						"  and b.payreftimes='1' "+
    						"  and (b.payrefdate!='' or b.payrefdate is not null) "+
    						"  AND b.payrefdate <= '"+dealDate+
    						"' group by a.policyno,b.currency2 " +
    						"  having sum(a.realpayreffee)>0";
    		ResultSet resultSet = dbpool.query(strWherePart);
            while(resultSet.next())
            {
            	PrpJpayRefRecSchema prpJpayRefRecSchema = new PrpJpayRefRecSchema();
            	prpJpayRefRecSchema.setPolicyNo(resultSet.getString("policyno"));
            	prpJpayRefRecSchema.setCurrency2(resultSet.getString("currency2"));
            	prpJpayRefRecSchema.setPlanFee(resultSet.getString("planfee"));
            	prpJpayRefRecSchema.setPayRefFee(resultSet.getString("realpayreffee"));
            	blPrpJpayRefRec.setArr(prpJpayRefRecSchema);
            }
            resultSet.close();
            String policyno = "";
            this.logPrintln("预约协议个数："+blPrpJpayRefRec.getSize());
            for(int i=0;i<blPrpJpayRefRec.getSize();i++)
            {
            	this.logPrintln(""+i+"开始处理预约协议号："+blPrpJpayRefRec.getArr(i).getPolicyNo());
        		if(blPrpJpayRefRec.getArr(i).getPayRefFee() != null && blPrpJpayRefRec.getArr(i).getPayRefFee() != "")
        		{
        			policyno += this.dealDataHis(dbpool,dealDate,
                			       blPrpJpayRefRec.getArr(i).getPolicyNo(),
                			       blPrpJpayRefRec.getArr(i).getCurrency2(),
                			       blPrpJpayRefRec.getArr(i).getPayRefFee(),
                			       blPrpJpayRefRec.getArr(i).getPlanFee());
        		}
        		this.logPrintln("处理预约协议号："+blPrpJpayRefRec.getArr(i).getPolicyNo()+"结束。");
            }
            this.logPrintln("预约协议剩余金额小于0的XX号："+policyno);
            dbpool.commitTransaction();
    	}catch(Exception e){
            dbpool.rollbackTransaction();
            throw e;
        }
        finally {
            dbpool.close();
        }
    }
    
    /**
     * @desc 历史数据处理
     * @param dbpool 连接池
     * @param dealDate 处理日期
     * @param strPolicyno 预约协议XX号
     * @param strCurrency2 预约协议实收币种
     * @param strRealPayRefFee 预约协议实收金额
     * @throws Exception
     */
    public String dealDataHis(DbPool dbpool,
    		String dealDate,String strPolicyno,String strCurrency2,String strRealPayRefFee,String strPlanFee)
	throws UserException,Exception{

    	String strWherePart = "";
    	String errorPolicyno = "";
    	double leftFee = Double.parseDouble(strRealPayRefFee);
    	

    	strWherePart = "select " +
    					"  sum(realpayreffee) as realpayreffee " +
    					"from prpjplanfee " +
    					"where contractno='"+strPolicyno+
    					"' and currency1='"+strCurrency2+
    					"' and payrefreason='R00'";
    	ResultSet resultSetTmp =dbpool.query(strWherePart);
    	while(resultSetTmp.next())
    	{
    		if(resultSetTmp.getString("realpayreffee") != null && resultSetTmp.getString("realpayreffee") != "")
    		{
    			leftFee -= Double.parseDouble(resultSetTmp.getString("realpayreffee"));
    		}
    	}
    	if(leftFee < 0)
    	{
    		errorPolicyno = "'"+strPolicyno+"',";
    	}
		
		BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
		strWherePart = "";
		strWherePart = " policyno='"+strPolicyno+
						"' and currency2='"+strCurrency2+
						"' and payreftimes='1' and (payrefdate is not null or payrefdate!='') " +
						"order by serialno";
		blPrpJpayRefRec.query(dbpool,strWherePart,0);
		blPrpJpayRefRec.getArr(0).setCertiType("P");
		blPrpJpayRefRec.getArr(0).setCertiNo(blPrpJpayRefRec.getArr(0).getPolicyNo());
		blPrpJpayRefRec.getArr(0).setPayRefReason("R10");
		blPrpJpayRefRec.getArr(0).setPlanFee(strPlanFee);
		blPrpJpayRefRec.getArr(0).setPayRefFee(""+leftFee);
		this.transData(dbpool,blPrpJpayRefRec.getArr(0),"0");
		
		BLPrpJpayRefRec blPrpJpayRefRecYAB0 = new BLPrpJpayRefRec();
		strWherePart = "";
		strWherePart = " contractno='"+strPolicyno+
						"' and currency2='"+strCurrency2+
						"' and payreftimes='1' and (payrefdate is not null or payrefdate!='')" +
						"  and payrefreason='R00'";
		blPrpJpayRefRecYAB0.query(dbpool,strWherePart,0);
		for(int j=0;j<blPrpJpayRefRecYAB0.getSize();j++)
		{
			this.logPrintln("开始处理小XX号："+blPrpJpayRefRec.getArr(j).getPolicyNo());
			this.transData(dbpool,blPrpJpayRefRecYAB0.getArr(j),
					blPrpJpayRefRec.getArr(0).getPolicyNo(),
					blPrpJpayRefRec.getArr(0).getSerialNo(),
					blPrpJpayRefRec.getArr(0).getPayRefTimes());
		}
    	
    	resultSetTmp.close();
    	
    	return errorPolicyno;
    }
    
    /**
     * 不带dbpool
     *根据结算日期和核算单位代码进行结算和反结算
     *@param iSettleDate 结算日期
     *@param iCenterCode 结算的核算单位代码
     *@throws UserException
     *@throws Exception
     */
    public void settleAll(String iSettleDate,String iCenterCode) throws UserException,Exception
    {
    	
    	DbPool dbpool = new DbPool();
    	try {
    	    dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
    		
    		if(iSettleDate == null || iSettleDate.equals(""))
    		{
        		ChgDate chgDate = new ChgDate();

        		
        		com.sp.utility.string.Date sinosoftDate = new com.sp.utility.string.Date();
        		
        		String preDate = chgDate.toFormat(sinosoftDate.getString(sinosoftDate.YEAR+sinosoftDate.MONTH+sinosoftDate.DATE));
        		iSettleDate = preDate;
    		}

            BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
            
            String strWherePart = " CenterFlag IN ('1','2') AND ValidStatus='1'";
            strWherePart += Str.convertString("ComCode",iCenterCode,"=");
            strWherePart += " ORDER BY ComCode";
            blPrpDcompany.query(dbpool,strWherePart,0);
            for(int i=0; i<blPrpDcompany.getSize(); i++){
            	try
            	{
            		dbpool.beginTransaction();
            		this.logPrintln("开始反结算核算单位："+blPrpDcompany.getArr(i).getComCode());
            		
            		this.reverseSettle(dbpool,iSettleDate,blPrpDcompany.getArr(i).getComCode());
                    dbpool.commitTransaction();
            		
            		dbpool.beginTransaction();
            		this.logPrintln("开始结算核算单位："+blPrpDcompany.getArr(i).getComCode());
            		
            		this.settle(dbpool,iSettleDate,blPrpDcompany.getArr(i).getComCode());
                    dbpool.commitTransaction();

            	}catch(Exception e){
                    e.printStackTrace();
                    dbpool.rollbackTransaction();
                }
            }
    	}catch(Exception e){
            dbpool.rollbackTransaction();
            throw e;
        }
        finally{
            dbpool.close();
        }
    }
    
    /**
     * 带DbPool
     *根据结算日期和核算单位代码进行结算和反结算
     *@param iSettleDate 结算日期
     *@param iCenterCode 结算的核算单位代码
     *@throws UserException
     *@throws Exception
     */
    public void settleAll(DbPool dbpool,String iSettleDate,String iCenterCode) throws UserException,Exception
    {
    	try
    	{
    		
    		if(iSettleDate == null || iSettleDate.equals(""))
    		{
        		ChgDate chgDate = new ChgDate();

        		
        		com.sp.utility.string.Date sinosoftDate = new com.sp.utility.string.Date();
        		
        		String preDate = chgDate.toFormat(sinosoftDate.getString(sinosoftDate.YEAR+sinosoftDate.MONTH+sinosoftDate.DATE));
        		iSettleDate = preDate;
    		}

            BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
            
            String strWherePart = " CenterFlag IN ('1','2') AND ValidStatus='1'";
            strWherePart += Str.convertString("ComCode",iCenterCode,"=");
            strWherePart += " ORDER BY ComCode";
            blPrpDcompany.query(dbpool,strWherePart,0);
            for(int i=0; i<blPrpDcompany.getSize(); i++){
            	try
            	{
            		dbpool.beginTransaction();
            		this.logPrintln("开始反结算核算单位："+blPrpDcompany.getArr(i).getComCode());
            		
            		this.reverseSettle(dbpool,iSettleDate,blPrpDcompany.getArr(i).getComCode());
                    dbpool.commitTransaction();
            		
            		dbpool.beginTransaction();
            		this.logPrintln("开始结算核算单位："+blPrpDcompany.getArr(i).getComCode());
            		
            		this.settle(dbpool,iSettleDate,blPrpDcompany.getArr(i).getComCode());
                    dbpool.commitTransaction();

            	}catch(Exception e){
                    e.printStackTrace();
                    dbpool.rollbackTransaction();
                }
            }
    	}catch(Exception e){
            dbpool.rollbackTransaction();
            throw e;
        }
        finally{
            dbpool.close();
        }
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
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
