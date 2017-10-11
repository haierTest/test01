package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import java.util.regex.Pattern;

import com.sp.sysframework.common.datatype.DateTime;
import com.sp.utility.*;
import com.sp.utility.string.*;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFee;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefRec;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefMain;
import com.sp.prpall.dbsvr.jf.DBPrpJtaxSettle;
import com.sp.prpall.schema.PrpJplanFeeSchema;
import com.sp.prpall.schema.PrpJtaxSettleSchema;
import com.sp.prpall.schema.PrpCcarshipTaxSchema;
import com.sp.prpall.schema.PrpPcarshipTaxSchema;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.cb.DBPrpCinsured;
import com.sp.prpall.blsvr.cb.*;
import com.sp.prpall.blsvr.pg.*;
import com.sp.utiall.dbsvr.DBPrpDcode;
import com.sp.utiall.dbsvr.DBPrpDuser;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.prpall.dbsvr.cb.DBPrpCitemCar;
import com.sp.prpall.pubfun.*;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;
import com.sp.prpall.dbsvr.cb.DBPrpCcarshipTax;

/**
 * 定义BLPrpJtaxSettle的BL类
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-07-23</p>
 * @author zhanglingjian
 * @version 1.0
 */
public class BLPrpJtaxSettle{
    private Vector schemas = new Vector();
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 构造函数
     */
    public BLPrpJtaxSettle(){
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
     *增加一条PrpJtaxSettleSchema记录
     *@param PrpJtaxSettleSchema PrpJtaxSettleSchema
     *@throws Exception
     */
    public void setArr(PrpJtaxSettleSchema iPrpJtaxSettleSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpJtaxSettleSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }

    /**
     *得到一条PrpJtaxSettleSchema记录
     *@param index 下标
     *@return 一个PrpJtaxSettleSchema对象
     *@throws Exception
     */
    public PrpJtaxSettleSchema getArr(int index) throws Exception
    {
    	PrpJtaxSettleSchema prpJtaxSettleSchema = null;
      try
      {
    	  prpJtaxSettleSchema = (PrpJtaxSettleSchema)this.schemas.get(index);
      }
      catch(Exception e)
      {
        throw e;
      }
      return prpJtaxSettleSchema;
    }
    
    /**
     *删除一条PrpJplanFeeSchema记录
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
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJtaxSettle dbPrpJtaxSettle = new DBPrpJtaxSettle();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpJtaxSettle.setSchema((PrpJtaxSettleSchema)schemas.get(i));
        dbPrpJtaxSettle.insert(dbpool);
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
      DBPrpJtaxSettle dbPrpJtaxSettle = new DBPrpJtaxSettle();
      if (iLimitCount > 0 && dbPrpJtaxSettle.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJplanFee.query");
      }else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJtaxSettle WHERE " + iWherePart;
        schemas = dbPrpJtaxSettle.findByConditions(strSqlStatement);
      }
    }
    
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
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
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJtaxSettle dbPrpJtaxSettle = new DBPrpJtaxSettle();
      if (iLimitCount > 0 && dbPrpJtaxSettle.getCount(dbpool,iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJplanFee.query");
      }else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJtaxSettle WHERE " + iWherePart;
        schemas = dbPrpJtaxSettle.findByConditions(dbpool,strSqlStatement);
      }
    }
    
    /*
     *@Author     : zhanglingjian
     *@desc   根据年月获取该月最后一天
     *@param  iYearMonth
     *@return strMonthEndDay
     */
    public String getMonthEndDay(String iYearMonth) throws Exception
    {
      String strMonthEndDay = "";
      int intYear = 0;
      String strMonth = "";

      if (iYearMonth==null||iYearMonth.trim().length() != 6) {
        return iYearMonth;
      }

      intYear  = Integer.parseInt(iYearMonth.substring(0,4));
      strMonth = iYearMonth.substring(4,6);
      if(strMonth.equals("01")||strMonth.equals("03")||strMonth.equals("05")||strMonth.equals("07")||
         strMonth.equals("08")||strMonth.equals("10")||strMonth.equals("12"))
        strMonthEndDay = ""+intYear+"-"+strMonth+"-31";

      if (strMonth.equals("04")||strMonth.equals("06")||strMonth.equals("09")||strMonth.equals("11"))
        strMonthEndDay = ""+intYear + "-" + strMonth + "-30";

      if(strMonth.equals("02")&&intYear%4!=0)
        strMonthEndDay = ""+intYear + "-" + strMonth + "-28";
      if(strMonth.equals("02")&&intYear%4==0)
        strMonthEndDay = ""+intYear + "-" + strMonth + "-29";

      return strMonthEndDay;
    }

    /**
     * @author zhanglingjian 转入车船使用税结算表数据（XX）
     * @param dbpool 数据库连接池
     * @param PrpJplanFeeSchema 对应XXXXX批单的传数对象
     * @param DBPrpCcarshipTax
     * @param DBPrpCitemCar
     * @param DBPrpCinsured
     * @throws UserException
     * @throws Exception
     */
    public void transTotaxSettleP(DbPool dbpool,PrpJplanFeeSchema schema,DBPrpCcarshipTax dbPrpCcarshipTax,
    		DBPrpCitemCar dbPrpCitemCar,DBPrpCinsured dbPrpCinsured,DBPrpCmain dbPrpCmain) throws UserException,Exception
    {
    	DBPrpJtaxSettle dbPrpJtaxSettle = new DBPrpJtaxSettle();
    	dbPrpJtaxSettle.setCertiType(schema.getCertiType());
    	dbPrpJtaxSettle.setCertiNo(schema.getCertiNo());
    	dbPrpJtaxSettle.setPolicyNo(schema.getPolicyNo());
    	dbPrpJtaxSettle.setSerialNo(schema.getSerialNo());
    	dbPrpJtaxSettle.setPayRefReason(schema.getPayRefReason());
    	dbPrpJtaxSettle.setCenterCode(schema.getCenterCode());
    	dbPrpJtaxSettle.setComCode(schema.getComCode());
    	dbPrpJtaxSettle.setAreaCode(dbPrpCcarshipTax.getAreaCode());
    	dbPrpJtaxSettle.setTaxpayerIdentifier(dbPrpCcarshipTax.getTaxpayerIdentifier());
    	dbPrpJtaxSettle.setTaxpayerCode(dbPrpCcarshipTax.getTaxpayerCode());
    	dbPrpJtaxSettle.setTaxpayerName(dbPrpCcarshipTax.getTaxpayerName());
    	dbPrpJtaxSettle.setIdentifyNumber(dbPrpCcarshipTax.getIdentifyNumber());
    	dbPrpJtaxSettle.setInsuredAddress(dbPrpCinsured.getInsuredAddress());
    	dbPrpJtaxSettle.setPhoneNumber(dbPrpCinsured.getPhoneNumber());
    	dbPrpJtaxSettle.setMobile(dbPrpCinsured.getMobile());
    	dbPrpJtaxSettle.setCarOwner(dbPrpCitemCar.getCarOwner());
    	dbPrpJtaxSettle.setEnrollDate(dbPrpCitemCar.getEnrollDate());
    	dbPrpJtaxSettle.setLicenseNo(dbPrpCcarshipTax.getLicenseNo());
    	dbPrpJtaxSettle.setLicenseColorCode(dbPrpCitemCar.getLicenseColorCode());
    	dbPrpJtaxSettle.setFrameNo(dbPrpCitemCar.getFrameNo());
    	dbPrpJtaxSettle.setEngineNo(dbPrpCitemCar.getEngineNo());
    	dbPrpJtaxSettle.setBrandName(dbPrpCitemCar.getBrandName());
    	dbPrpJtaxSettle.setCarKindCode(dbPrpCcarshipTax.getCarKindCode());
    	dbPrpJtaxSettle.setCompleteKerbMass(dbPrpCcarshipTax.getCompleteKerbMass());
    	dbPrpJtaxSettle.setSeatCount(dbPrpCitemCar.getSeatCount());
    	dbPrpJtaxSettle.setTonCount(dbPrpCitemCar.getTonCount());
    	dbPrpJtaxSettle.setTaxItemCode(dbPrpCcarshipTax.getTaxItemCode());
    	dbPrpJtaxSettle.setTaxItemName(dbPrpCcarshipTax.getTaxItemName());
    	dbPrpJtaxSettle.setTaxItemDetailName(dbPrpCcarshipTax.getTaxItemDetailName());
    	dbPrpJtaxSettle.setPayStartDate(dbPrpCcarshipTax.getPayStartDate());
    	dbPrpJtaxSettle.setPayEndDate(dbPrpCcarshipTax.getPayEndDate());
    	dbPrpJtaxSettle.setPaidFreeCertificate(dbPrpCcarshipTax.getPaidFreeCertificate());
    	dbPrpJtaxSettle.setTaxRelifFlag(dbPrpCcarshipTax.getTaxRelifFlag());
    	if(dbPrpJtaxSettle.getTaxRelifFlag().equals("4"))
    	{
    		dbPrpJtaxSettle.setTaxVoucherKind("完税凭证");
    	}
    	dbPrpJtaxSettle.setRelifReason(dbPrpCcarshipTax.getRelifReason());
    	dbPrpJtaxSettle.setTaxUnitText(dbPrpCcarshipTax.getTaxUnitText());
    	dbPrpJtaxSettle.setFreeRate(dbPrpCcarshipTax.getFreeRate());
    	dbPrpJtaxSettle.setFreeRateText(dbPrpCcarshipTax.getFreeRateText());
    	dbPrpJtaxSettle.setTaxRelief(dbPrpCcarshipTax.getTaxRelief());
    	dbPrpJtaxSettle.setTaxUnit(dbPrpCcarshipTax.getTaxUnit());
    	dbPrpJtaxSettle.setTaxDue(dbPrpCcarshipTax.getTaxDue());
    	if(schema.getPayRefReason().equals("R72"))
    	{
        	dbPrpJtaxSettle.setTaxActual(schema.getPlanFee());
        	dbPrpJtaxSettle.setPreviousPay("0");
        	dbPrpJtaxSettle.setLateFee("0");
    	}
    	if(schema.getPayRefReason().equals("R73"))
    	{
        	dbPrpJtaxSettle.setTaxActual("0");
        	dbPrpJtaxSettle.setPreviousPay(schema.getPlanFee());
        	dbPrpJtaxSettle.setLateFee("0");
    	}
    	if(schema.getPayRefReason().equals("R74"))
    	{
        	dbPrpJtaxSettle.setTaxActual("0");
        	dbPrpJtaxSettle.setPreviousPay("0");
        	dbPrpJtaxSettle.setLateFee(schema.getPlanFee());
    	}
    	dbPrpJtaxSettle.setAllTax(schema.getPlanFee());
    	dbPrpJtaxSettle.setPayRefFee("0");
    	dbPrpJtaxSettle.setTaxSettleFlag("0");
    	dbPrpJtaxSettle.setPayRefDate("2100-01-01");
    	dbPrpJtaxSettle.setTaxSettleDate("2100-01-01");
    	dbPrpJtaxSettle.setPayRefNo("");
    	dbPrpJtaxSettle.setExtendChar1("");
    	dbPrpJtaxSettle.setExtendChar2("");
    	dbPrpJtaxSettle.setExtendNum1("0");
    	dbPrpJtaxSettle.setExtendNum2("0");
    	dbPrpJtaxSettle.setExtendDate1("");
    	dbPrpJtaxSettle.setExtendDate2("");
    	dbPrpJtaxSettle.setFlag("0");
    	dbPrpJtaxSettle.setOperateDate(dbPrpCmain.getOperateDate());
    	dbPrpJtaxSettle.setStartDate(schema.getStartDate());
    	dbPrpJtaxSettle.setEndDate(schema.getEndDate());
    	dbPrpJtaxSettle.setInsuredCode(schema.getInsuredCode());
    	dbPrpJtaxSettle.setInsuredName(schema.getInsuredName());
    	dbPrpJtaxSettle.setTaxComCode(dbPrpCcarshipTax.getTaxComCode());
    	dbPrpJtaxSettle.setTaxComName(dbPrpCcarshipTax.getTaxComName());
    	dbPrpJtaxSettle.setUnderWriteDate(schema.getUnderWriteDate());
    	dbPrpJtaxSettle.setIdentifyType(dbPrpCinsured.getIdentifyType());
    	
    	if(dbPrpJtaxSettle.getIdentifyType() != "" 
    		&& dbPrpJtaxSettle.getIdentifyType() != null
    		&& dbPrpJtaxSettle.getIdentifyType().equals("02"))
    	{
    		DBPrpDcode dbPrpDcode = new DBPrpDcode();
        	dbPrpDcode.getInfo(dbpool,"BusinessDetail",dbPrpCinsured.getBusinessSort());
        	dbPrpJtaxSettle.setBusinessSort(dbPrpDcode.getCodeCName());
    	}else
    	{
    		dbPrpJtaxSettle.setBusinessSort("");
    	}
    	dbPrpJtaxSettle.setExhaustScale(dbPrpCitemCar.getExhaustScale());
    	dbPrpJtaxSettle.insert(dbpool);
    }
    
    /**
     * @author zhanglingjian 转入车船使用税结算表数据（批单）,将数据更新到相对应的XX车船税上面
     * @param dbpool 数据库连接池
     * @param PrpJplanFeeSchema 对应XXXXX批单的传数对象
     * @param PrpCcarshipTaxSchema
     * @param DBPrpCitemCar
     * @param DBPrpCinsured
     * @throws UserException
     * @throws Exception
     */
    public void transTotaxSettleE(DbPool dbpool,PrpJplanFeeSchema schema,DBPrpCcarshipTax dbPrpCcarshipTax,
    		DBPrpCitemCar dbPrpCitemCar,DBPrpCinsured dbPrpCinsured,DBPrpCmain dbPrpCmain) throws UserException,Exception
    {
    	DBPrpJtaxSettle dbPrpJtaxSettle = new DBPrpJtaxSettle();
    	int result = dbPrpJtaxSettle.getInfo(dbpool,"P",schema.getPolicyNo(),
    			schema.getSerialNo(),schema.getPayRefReason());
    	if(result == 100)
    	{
        	dbPrpJtaxSettle.setCertiType("P");
        	dbPrpJtaxSettle.setCertiNo(schema.getPolicyNo());
        	dbPrpJtaxSettle.setPolicyNo(schema.getPolicyNo());
        	dbPrpJtaxSettle.setSerialNo(schema.getSerialNo());
        	dbPrpJtaxSettle.setPayRefReason(schema.getPayRefReason());
    	}
    	dbPrpJtaxSettle.setCenterCode(schema.getCenterCode());
    	dbPrpJtaxSettle.setComCode(schema.getComCode());
    	dbPrpJtaxSettle.setAreaCode(dbPrpCcarshipTax.getAreaCode());
    	dbPrpJtaxSettle.setTaxpayerIdentifier(dbPrpCcarshipTax.getTaxpayerIdentifier());
    	dbPrpJtaxSettle.setTaxpayerCode(dbPrpCcarshipTax.getTaxpayerCode());
    	dbPrpJtaxSettle.setTaxpayerName(dbPrpCcarshipTax.getTaxpayerName());
    	dbPrpJtaxSettle.setIdentifyNumber(dbPrpCcarshipTax.getIdentifyNumber());
    	dbPrpJtaxSettle.setInsuredAddress(dbPrpCinsured.getInsuredAddress());
    	dbPrpJtaxSettle.setPhoneNumber(dbPrpCinsured.getPhoneNumber());
    	dbPrpJtaxSettle.setMobile(dbPrpCinsured.getMobile());
    	dbPrpJtaxSettle.setCarOwner(dbPrpCitemCar.getCarOwner());
    	dbPrpJtaxSettle.setEnrollDate(dbPrpCitemCar.getEnrollDate());
    	dbPrpJtaxSettle.setLicenseNo(dbPrpCcarshipTax.getLicenseNo());
    	dbPrpJtaxSettle.setLicenseColorCode(dbPrpCitemCar.getLicenseColorCode());
    	dbPrpJtaxSettle.setFrameNo(dbPrpCitemCar.getFrameNo());
    	dbPrpJtaxSettle.setEngineNo(dbPrpCitemCar.getEngineNo());
    	dbPrpJtaxSettle.setBrandName(dbPrpCitemCar.getBrandName());
    	dbPrpJtaxSettle.setCarKindCode(dbPrpCcarshipTax.getCarKindCode());
    	dbPrpJtaxSettle.setCompleteKerbMass(dbPrpCcarshipTax.getCompleteKerbMass());
    	dbPrpJtaxSettle.setSeatCount(dbPrpCitemCar.getSeatCount());
    	dbPrpJtaxSettle.setTonCount(dbPrpCitemCar.getTonCount());
    	dbPrpJtaxSettle.setTaxItemCode(dbPrpCcarshipTax.getTaxItemCode());
    	dbPrpJtaxSettle.setTaxItemName(dbPrpCcarshipTax.getTaxItemName());
    	dbPrpJtaxSettle.setTaxItemDetailName(dbPrpCcarshipTax.getTaxItemDetailName());
    	dbPrpJtaxSettle.setPayStartDate(dbPrpCcarshipTax.getPayStartDate());
    	dbPrpJtaxSettle.setPayEndDate(dbPrpCcarshipTax.getPayEndDate());
    	dbPrpJtaxSettle.setPaidFreeCertificate(dbPrpCcarshipTax.getPaidFreeCertificate());
    	dbPrpJtaxSettle.setTaxRelifFlag(dbPrpCcarshipTax.getTaxRelifFlag());
    	if(dbPrpJtaxSettle.getTaxRelifFlag().equals("4"))
    	{
    		dbPrpJtaxSettle.setTaxVoucherKind("完税凭证");
    	}
    	dbPrpJtaxSettle.setRelifReason(dbPrpCcarshipTax.getRelifReason());
    	dbPrpJtaxSettle.setTaxUnitText(dbPrpCcarshipTax.getTaxUnitText());
    	dbPrpJtaxSettle.setFreeRate(dbPrpCcarshipTax.getFreeRate());
    	dbPrpJtaxSettle.setFreeRateText(dbPrpCcarshipTax.getFreeRateText());
    	dbPrpJtaxSettle.setTaxRelief(dbPrpCcarshipTax.getTaxRelief());
    	dbPrpJtaxSettle.setTaxUnit(dbPrpCcarshipTax.getTaxUnit());
    	dbPrpJtaxSettle.setTaxDue(dbPrpCcarshipTax.getTaxDue());
    	if(schema.getPayRefReason().equals("R72"))
    	{
        	if(result == 0)
        	{
	        	dbPrpJtaxSettle.setTaxActual(""+(Double.parseDouble(dbPrpJtaxSettle.getTaxActual())+
	        			Double.parseDouble(schema.getPlanFee())));
        	} else if(result == 100)
        	{
        		dbPrpJtaxSettle.setTaxActual(schema.getPlanFee());
        	}
        	dbPrpJtaxSettle.setPreviousPay("0");
        	dbPrpJtaxSettle.setLateFee("0");
        	
    	}
    	if(schema.getPayRefReason().equals("R73"))
    	{
        	dbPrpJtaxSettle.setTaxActual("0");
        	if(result == 0)
        	{
            	dbPrpJtaxSettle.setPreviousPay(""+(Double.parseDouble(dbPrpJtaxSettle.getPreviousPay())+
            			Double.parseDouble(schema.getPlanFee())));
        	} else if(result == 100)
        	{
        		dbPrpJtaxSettle.setPreviousPay(schema.getPlanFee());
        	}
        	dbPrpJtaxSettle.setLateFee("0");
    	}
    	if(schema.getPayRefReason().equals("R74"))
    	{
        	dbPrpJtaxSettle.setTaxActual("0");
        	dbPrpJtaxSettle.setPreviousPay("0");
        	if(result == 0)
        	{
            	dbPrpJtaxSettle.setLateFee(""+(Double.parseDouble(dbPrpJtaxSettle.getLateFee())+
            			Double.parseDouble(schema.getPlanFee())));
        	} else if(result == 100)
        	{
        		dbPrpJtaxSettle.setLateFee(schema.getPlanFee());
        	}
    	}
    	if(result == 0)
    	{
        	dbPrpJtaxSettle.setAllTax(""+(Double.parseDouble(dbPrpJtaxSettle.getAllTax())+
        			Double.parseDouble(schema.getPlanFee())));
        	dbPrpJtaxSettle.setPayRefFee(dbPrpJtaxSettle.getPayRefFee());
    	} else if(result == 100)
    	{
    		dbPrpJtaxSettle.setAllTax(schema.getPlanFee());
    		dbPrpJtaxSettle.setPayRefFee("0");
    	}
    	dbPrpJtaxSettle.setTaxSettleFlag("0");
    	dbPrpJtaxSettle.setTaxSettleDate("2100-01-01");
    	dbPrpJtaxSettle.setExtendChar1("");
    	dbPrpJtaxSettle.setExtendChar2("");
    	dbPrpJtaxSettle.setExtendNum1("0");
    	dbPrpJtaxSettle.setExtendNum2("0");
    	dbPrpJtaxSettle.setExtendDate1("");
    	dbPrpJtaxSettle.setExtendDate2("");
    	if(schema.getFlag().substring(0,1).equals("1"))
    	{
    		dbPrpJtaxSettle.setFlag("1");
    	}else
    	{
    		dbPrpJtaxSettle.setFlag("0");
    	}
    	dbPrpJtaxSettle.setOperateDate(dbPrpCmain.getOperateDate());
    	dbPrpJtaxSettle.setStartDate(schema.getStartDate());
    	dbPrpJtaxSettle.setEndDate(schema.getEndDate());
    	dbPrpJtaxSettle.setInsuredCode(schema.getInsuredCode());
    	dbPrpJtaxSettle.setInsuredName(schema.getInsuredName());
    	dbPrpJtaxSettle.setTaxComCode(dbPrpCcarshipTax.getTaxComCode());
    	dbPrpJtaxSettle.setTaxComName(dbPrpCcarshipTax.getTaxComName());
    	dbPrpJtaxSettle.setUnderWriteDate(schema.getUnderWriteDate());
    	dbPrpJtaxSettle.setIdentifyType(dbPrpCinsured.getIdentifyType());
    	
    	if(dbPrpJtaxSettle.getIdentifyType() != "" 
    		&& dbPrpJtaxSettle.getIdentifyType() != null
    		&& dbPrpJtaxSettle.getIdentifyType().equals("02"))
    	{
    		DBPrpDcode dbPrpDcode = new DBPrpDcode();
        	dbPrpDcode.getInfo(dbpool,"BusinessDetail",dbPrpCinsured.getBusinessSort());
        	dbPrpJtaxSettle.setBusinessSort(dbPrpDcode.getCodeCName());
    	}else
    	{
    		dbPrpJtaxSettle.setBusinessSort("");
    	}
    	dbPrpJtaxSettle.setExhaustScale(dbPrpCitemCar.getExhaustScale());
    	if(result == 100)
    	{
    		dbPrpJtaxSettle.insert(dbpool);
    	}else if (result == 0)
    	{
    		dbPrpJtaxSettle.update(dbpool);
    	}
    }
    
    /**
     * @author zhanglingjian 转入车船使用税结算表数据（批单）,将数据更新到相对应的XX车船税上面
     * @param dbpool 数据库连接池
     * @param PrpJplanFeeSchema 对应XXXXX批单的传数对象
     * @param PrpCcarshipTaxSchema
     * @param DBPrpCitemCar
     * @param DBPrpCinsured
     * @throws UserException
     * @throws Exception
     */
    public void updateTaxSettle(DbPool dbpool,PrpJplanFeeSchema schema,DBPrpCcarshipTax dbPrpCcarshipTax,
    		DBPrpCitemCar dbPrpCitemCar,DBPrpCinsured dbPrpCinsured,String payRefReason) throws UserException,Exception
    {
    	DBPrpJtaxSettle dbPrpJtaxSettle = new DBPrpJtaxSettle();
    	int result = dbPrpJtaxSettle.getInfo(dbpool,"P",schema.getPolicyNo(),schema.getSerialNo(),payRefReason);
    	if(result == 0)
    	{
    		dbPrpJtaxSettle.setCenterCode(schema.getCenterCode());
    		dbPrpJtaxSettle.setComCode(schema.getComCode());
	    	dbPrpJtaxSettle.setAreaCode(dbPrpCcarshipTax.getAreaCode());
	    	dbPrpJtaxSettle.setTaxpayerIdentifier(dbPrpCcarshipTax.getTaxpayerIdentifier());
	    	dbPrpJtaxSettle.setTaxpayerCode(dbPrpCcarshipTax.getTaxpayerCode());
	    	dbPrpJtaxSettle.setTaxpayerName(dbPrpCcarshipTax.getTaxpayerName());
	    	dbPrpJtaxSettle.setIdentifyNumber(dbPrpCcarshipTax.getIdentifyNumber());
	    	dbPrpJtaxSettle.setInsuredAddress(dbPrpCinsured.getInsuredAddress());
	    	dbPrpJtaxSettle.setPhoneNumber(dbPrpCinsured.getPhoneNumber());
	    	dbPrpJtaxSettle.setMobile(dbPrpCinsured.getMobile());
	    	dbPrpJtaxSettle.setCarOwner(dbPrpCitemCar.getCarOwner());
	    	dbPrpJtaxSettle.setEnrollDate(dbPrpCitemCar.getEnrollDate());
	    	dbPrpJtaxSettle.setLicenseNo(dbPrpCcarshipTax.getLicenseNo());
	    	dbPrpJtaxSettle.setLicenseColorCode(dbPrpCitemCar.getLicenseColorCode());
	    	dbPrpJtaxSettle.setFrameNo(dbPrpCitemCar.getFrameNo());
	    	dbPrpJtaxSettle.setEngineNo(dbPrpCitemCar.getEngineNo());
	    	dbPrpJtaxSettle.setBrandName(dbPrpCitemCar.getBrandName());
	    	dbPrpJtaxSettle.setCarKindCode(dbPrpCcarshipTax.getCarKindCode());
	    	dbPrpJtaxSettle.setCompleteKerbMass(dbPrpCcarshipTax.getCompleteKerbMass());
	    	dbPrpJtaxSettle.setSeatCount(dbPrpCitemCar.getSeatCount());
	    	dbPrpJtaxSettle.setTonCount(dbPrpCitemCar.getTonCount());
	    	dbPrpJtaxSettle.setTaxItemCode(dbPrpCcarshipTax.getTaxItemCode());
	    	dbPrpJtaxSettle.setTaxItemName(dbPrpCcarshipTax.getTaxItemName());
	    	dbPrpJtaxSettle.setTaxItemDetailName(dbPrpCcarshipTax.getTaxItemDetailName());
	    	dbPrpJtaxSettle.setPayStartDate(dbPrpCcarshipTax.getPayStartDate());
	    	dbPrpJtaxSettle.setPayEndDate(dbPrpCcarshipTax.getPayEndDate());
	    	dbPrpJtaxSettle.setPaidFreeCertificate(dbPrpCcarshipTax.getPaidFreeCertificate());
	    	dbPrpJtaxSettle.setTaxRelifFlag(dbPrpCcarshipTax.getTaxRelifFlag());
	    	if(dbPrpJtaxSettle.getTaxRelifFlag().equals("4"))
	    	{
	    		dbPrpJtaxSettle.setTaxVoucherKind("完税凭证");
	    	}
	    	dbPrpJtaxSettle.setRelifReason(dbPrpCcarshipTax.getRelifReason());
	    	dbPrpJtaxSettle.setTaxUnitText(dbPrpCcarshipTax.getTaxUnitText());
	    	dbPrpJtaxSettle.setFreeRate(dbPrpCcarshipTax.getFreeRate());
	    	dbPrpJtaxSettle.setFreeRateText(dbPrpCcarshipTax.getFreeRateText());
	    	dbPrpJtaxSettle.setTaxRelief(dbPrpCcarshipTax.getTaxRelief());
	    	dbPrpJtaxSettle.setTaxUnit(dbPrpCcarshipTax.getTaxUnit());
	    	dbPrpJtaxSettle.setTaxDue(dbPrpCcarshipTax.getTaxDue());
	    	dbPrpJtaxSettle.setExtendChar1("");
	    	dbPrpJtaxSettle.setExtendChar2("");
	    	dbPrpJtaxSettle.setExtendNum1("0");
	    	dbPrpJtaxSettle.setExtendNum2("0");
	    	dbPrpJtaxSettle.setExtendDate1("");
	    	dbPrpJtaxSettle.setExtendDate2("");
	    	if(schema.getFlag().substring(0,1).equals("1"))
	    	{
	    		dbPrpJtaxSettle.setFlag("1");
	    	}else
	    	{
	    		dbPrpJtaxSettle.setFlag("0");
	    	}
	    	dbPrpJtaxSettle.setStartDate(schema.getStartDate());
	    	dbPrpJtaxSettle.setEndDate(schema.getEndDate());
	    	dbPrpJtaxSettle.setInsuredCode(schema.getInsuredCode());
	    	dbPrpJtaxSettle.setInsuredName(schema.getInsuredName());
	    	dbPrpJtaxSettle.setTaxComCode(dbPrpCcarshipTax.getTaxComCode());
	    	dbPrpJtaxSettle.setTaxComName(dbPrpCcarshipTax.getTaxComName());
	    	
	    	
	    	dbPrpJtaxSettle.setIdentifyType(dbPrpCinsured.getIdentifyType());
	    	
	    	if(dbPrpJtaxSettle.getIdentifyType() != "" 
	    		&& dbPrpJtaxSettle.getIdentifyType() != null
	    		&& dbPrpJtaxSettle.getIdentifyType().equals("02"))
	    	{
	    		DBPrpDcode dbPrpDcode = new DBPrpDcode();
	        	dbPrpDcode.getInfo(dbpool,"BusinessDetail",dbPrpCinsured.getBusinessSort());
	        	dbPrpJtaxSettle.setBusinessSort(dbPrpDcode.getCodeCName());
	    	}else
	    	{
	    		dbPrpJtaxSettle.setBusinessSort("");
	    	}
	    	dbPrpJtaxSettle.setExhaustScale(dbPrpCitemCar.getExhaustScale());
	    	dbPrpJtaxSettle.update(dbpool);
    	}
    }
    
    /**
     *不带dbpool的查询方法,查询可结算的车船税XX
     *@param 无
     *@return 无
     */
    public void queryTaxSettle(String strWherePart) throws Exception
    {
    	DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	        queryTaxSettle(dbpool,strWherePart);
	    }
        catch (Exception e)
        {
        	dbpool.rollbackTransaction();
        	throw e;
        }
        finally {
        	dbpool.close();
        }
    }
    
    /**
     * @author liuyongxin 车船税转数
     * @param strWherePart
     * @throws UserException
     * @throws Exception
     */
    public void transTaxSettle(String strWherePart) throws UserException,Exception
    {
    	
        BLPrpJtaxSettle blPrpJtaxSettle = new BLPrpJtaxSettle();
        PrpJtaxSettleSchema prpJtaxSettleSchema = new PrpJtaxSettleSchema();
        DBPrpJtaxSettle dbPrpJtaxSettle = new DBPrpJtaxSettle();
        DBPrpCmain dbPrpCmain=new DBPrpCmain();
        DBPrpDuser dbPrpDuser=new DBPrpDuser();        
        DBPrpCcarshipTax dbPrpCcarshiptax=new DBPrpCcarshipTax();
        DbPool dbpool = new DbPool();
             
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            blPrpJtaxSettle.query(dbpool, strWherePart);
            for(int i=0;i<blPrpJtaxSettle.getSize();i++){
            	prpJtaxSettleSchema= blPrpJtaxSettle.getArr(i);
            	dbPrpJtaxSettle.setSchema(prpJtaxSettleSchema);
            	dbPrpCmain.getInfo(dbpool, dbPrpJtaxSettle.getPolicyNo());
            	dbPrpDuser.getInfo(dbpool, dbPrpCmain.getOperatorCode());
            	dbPrpCcarshiptax.getInfo(dbpool, dbPrpJtaxSettle.getPolicyNo(),dbPrpJtaxSettle.getSerialNo());
            	dbPrpJtaxSettle.setCol_06(dbPrpDuser.getUserName());
            	dbPrpJtaxSettle.setCol_07(dbPrpCmain.getInputDate());        	
            	dbPrpJtaxSettle.setCol_08(""+DateTime.intervalMonth(new DateTime(dbPrpCcarshiptax.getLateFeeStartDate()),0,new DateTime(dbPrpCcarshiptax.getLateFeeEndDate()),24));
            	dbPrpJtaxSettle.update(dbpool);
            }
            dbpool.commitTransaction();
	    }
        catch (Exception e)
        {
        	dbpool.rollbackTransaction();
        	throw e;
        }
        finally {
        	dbpool.close();
        }
    }
    
    /**
     * @author zhanglingjian 查询可结算的车船税XX
     * @param dbpool 数据库连接池
     * @param strWherePart
     * @throws UserException
     * @throws Exception
     */
    public void queryTaxSettle(DbPool dbpool,String strWherePart) throws UserException,Exception
    {
    	BLPrpJtaxSettle blPrpJtaxSettleTmp = new BLPrpJtaxSettle();
    	DBPrpJtaxSettle dbPrpJtaxSettle = new DBPrpJtaxSettle();
    	PrpJtaxSettleSchema prpJtaxSettleSchema = new PrpJtaxSettleSchema();
    	
    	String sql = "select policyno,serialno,centercode,payrefdate,sum(TaxActual) as TaxActual,sum(PreviousPay) as PreviousPay," +
    				 "sum(LateFee) as LateFee,sum(AllTax) as AllTax,sum(PayRefFee) as PayRefFee,TaxSettleFlag " +
    				 " from PrpJtaxSettle where AllTax=PayRefFee and " + strWherePart +
    			     " group by policyno,serialno,centercode,TaxSettleFlag,payrefdate ";
        ResultSet resultSet = dbpool.query(sql);
        while(resultSet.next())
        {
        	prpJtaxSettleSchema = new PrpJtaxSettleSchema();
        	prpJtaxSettleSchema.setPolicyNo(resultSet.getString("policyno"));
        	prpJtaxSettleSchema.setSerialNo(resultSet.getString("serialno"));
        	prpJtaxSettleSchema.setTaxActual(""+resultSet.getDouble("TaxActual"));
        	prpJtaxSettleSchema.setCenterCode(resultSet.getString("CenterCode"));
        	prpJtaxSettleSchema.setPreviousPay(""+resultSet.getDouble("PreviousPay"));
        	prpJtaxSettleSchema.setLateFee(""+resultSet.getDouble("LateFee"));
        	prpJtaxSettleSchema.setAllTax(""+resultSet.getDouble("AllTax"));
        	prpJtaxSettleSchema.setPayRefFee(""+resultSet.getDouble("PayRefFee"));
        	prpJtaxSettleSchema.setTaxSettleFlag(resultSet.getString("TaxSettleFlag"));
        	prpJtaxSettleSchema.setPayRefDate("" + resultSet.getDate("PayRefDate"));
        	blPrpJtaxSettleTmp.setArr(prpJtaxSettleSchema);
        }
        
        for(int i=0;i<blPrpJtaxSettleTmp.getSize();i++)
        {
        	String strWhere = " AllTax!=PayRefFee and policyno='"+blPrpJtaxSettleTmp.getArr(i).getPolicyNo()+"'";
        	int count = dbPrpJtaxSettle.getCount(dbpool,strWhere);
        	if(count == 0)
        	{
        		this.setArr(blPrpJtaxSettleTmp.getArr(i));
        	}
        }
    }
    
    /**
     *不带dbpool的查询方法,查询已结算的车船税XX用于打印
     *@param 无
     *@return 无
     */
    public void queryToPrint(String strWherePart) throws Exception
    {
    	DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            queryToPrint(dbpool,strWherePart);
	    }
        catch (Exception e)
        {
        	dbpool.rollbackTransaction();
        	throw e;
        }
        finally {
        	dbpool.close();
        }
    }
    
    /**
     * @author zhanglingjian 查询已结算的车船税XX用于打印
     * @param dbpool 数据库连接池
     * @param strWherePart
     * @throws UserException
     * @throws Exception
     */
    public void queryToPrint(DbPool dbpool,String strWherePart) throws UserException,Exception
    {
    	PrpJtaxSettleSchema prpJtaxSettleSchema = new PrpJtaxSettleSchema();
    	
    	String sql = "select policyno,serialno,centercode,payrefdate,sum(TaxActual) as TaxActual,sum(PreviousPay) as PreviousPay," +
    				 "sum(LateFee) as LateFee,sum(AllTax) as AllTax,sum(PayRefFee) as PayRefFee,TaxSettleFlag " +
    				 " from PrpJtaxSettle where " + strWherePart +
    			     " group by policyno,serialno,centercode,TaxSettleFlag,payrefdate ";
        ResultSet resultSet = dbpool.query(sql);
        while(resultSet.next())
        {
        	prpJtaxSettleSchema = new PrpJtaxSettleSchema();
        	prpJtaxSettleSchema.setPolicyNo(resultSet.getString("policyno"));
        	prpJtaxSettleSchema.setSerialNo(resultSet.getString("serialno"));
        	prpJtaxSettleSchema.setTaxActual(""+resultSet.getDouble("TaxActual"));
        	prpJtaxSettleSchema.setCenterCode(resultSet.getString("CenterCode"));
        	prpJtaxSettleSchema.setPreviousPay(""+resultSet.getDouble("PreviousPay"));
        	prpJtaxSettleSchema.setLateFee(""+resultSet.getDouble("LateFee"));
        	prpJtaxSettleSchema.setAllTax(""+resultSet.getDouble("AllTax"));
        	prpJtaxSettleSchema.setPayRefFee(""+resultSet.getDouble("PayRefFee"));
        	prpJtaxSettleSchema.setTaxSettleFlag(resultSet.getString("TaxSettleFlag"));
        	prpJtaxSettleSchema.setPayRefDate("" + resultSet.getDate("PayRefDate"));
        	this.setArr(prpJtaxSettleSchema);
        }
    }
    
    /**
     *不带dbpool的查询方法,查询结算车船税XX详细信息
     *@param arrPolicyNo 需要打印的XX号
     *@param arrSerialNo 需要打印的XX的缴费序号
     *@return 无
     */
    public void queryForPrint(String[] arrCheck,String[] arrPolicyNo,String[] arrSerialNo,String[] arrTaxActual,
    		String[] arrPreviousPay,String[] arrLateFee,String[] arrAllTax,String[] arrPayRefFee) throws Exception
    {
    	DbPool dbpool = new DbPool();

        try {
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	        queryForPrint(dbpool,arrCheck,arrPolicyNo,arrSerialNo,arrTaxActual,arrPreviousPay
	        		,arrLateFee,arrAllTax,arrPayRefFee);
	    }
        catch (Exception e)
        {
        	throw e;
        }
        finally {
        	dbpool.close();
        }
    }
    
    /**
     * @author zhanglingjian 查询结算车船税XX详细信息
     * @param dbpool 数据库连接池
     * @param arrPolicyNo 需要打印的XX号
     * @param arrSerialNo 需要打印的XX的缴费序号
     * @throws UserException
     * @throws Exception
     */
    public void queryForPrint(DbPool dbpool,String[] arrCheck,String[] arrPolicyNo,String[] arrSerialNo,
    		String[] arrTaxActual,String[] arrPreviousPay,String[] arrLateFee,String[] arrAllTax,String[] arrPayRefFee) throws UserException,Exception
    {
    	BLPrpJtaxSettle blPrpJtaxSettleTmp = new BLPrpJtaxSettle();
    	
    	this.initArr();
    	for(int i=0;i<arrCheck.length;i++)
    	{
    		String sql = " policyno='"+arrPolicyNo[Integer.parseInt(arrCheck[i])]+
    							"' and serialno='"+arrSerialNo[Integer.parseInt(arrCheck[i])]+"'";
    		blPrpJtaxSettleTmp.query(dbpool,sql,0);
    		blPrpJtaxSettleTmp.getArr(0).setTaxActual(arrTaxActual[Integer.parseInt(arrCheck[i])]);
    		blPrpJtaxSettleTmp.getArr(0).setPreviousPay(arrPreviousPay[Integer.parseInt(arrCheck[i])]);
    		blPrpJtaxSettleTmp.getArr(0).setLateFee(arrLateFee[Integer.parseInt(arrCheck[i])]);
    		blPrpJtaxSettleTmp.getArr(0).setAllTax(arrAllTax[Integer.parseInt(arrCheck[i])]);
    		blPrpJtaxSettleTmp.getArr(0).setPayRefFee(arrPayRefFee[Integer.parseInt(arrCheck[i])]);
    		this.setArr(blPrpJtaxSettleTmp.getArr(0));
    	}
    }
    
    /**
     *不带dbpool的查询方法,结算
     *@return 无
     */
    public String settleTheChecked() throws Exception
    {
    	DbPool dbpool = new DbPool();
    	String strPayRefNo = "";

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	        dbpool.beginTransaction();
	        strPayRefNo = settleTheChecked(dbpool);
	        dbpool.commitTransaction();
	    }
        catch (Exception e)
        {
        	dbpool.rollbackTransaction();
        	throw e;
        }
        finally {
        	dbpool.close();
        }
        return strPayRefNo;
    }
    
    /**
     * @author zhanglingjian 结算
     * @param dbpool 数据库连接池
     * @param arrPolicyNo 需要结算的XX号
     * @param arrSerialNo 需要打印的XX的缴费序号
     * @throws UserException
     * @throws Exception
     */
    public String settleTheChecked(DbPool dbpool) throws UserException,Exception
    {
    	BLPrpJtaxSettle blPrpJtaxSettle = new BLPrpJtaxSettle();
    	DBPrpCcarshipTax dbPrpCcarshipTax = new DBPrpCcarshipTax();
        ChgDate chgDate  = new ChgDate();
        String currentDate = chgDate.getCurrentTime("yyyy-MM-dd");
    	
        
        Bill bill = new Bill();
        String billNo = "";
        int intYear = Integer.parseInt(currentDate.substring(0,4));
        billNo = bill.getNo("prpjpayrec","0501","00000000",intYear,"00");
        
        HashMap useNatureCode = new HashMap();
        BLPrpDcode blPrpDcode = new BLPrpDcode();
        if(this.getArr(0).getCenterCode().substring(0,2).equals("04"))
		{
        	blPrpDcode.query(" codetype='UseNature' ");
        	for(int i=0;i<blPrpDcode.getSize();i++)
        	{
        		useNatureCode.put(blPrpDcode.getArr(i).getCodeCode(),blPrpDcode.getArr(i).getCodeCName());
        	}
		}
        
    	for(int i=0;i<this.getSize();i++)
    	{
    		String sql = " policyno='"+this.getArr(i).getPolicyNo()+
    							"' and serialno='"+this.getArr(i).getSerialNo()+"'";
    		blPrpJtaxSettle.query(dbpool,sql,0);
    		for(int j=0;j<blPrpJtaxSettle.getSize();j++)
    		{
    			DBPrpJtaxSettle dbPrpJtaxSettle= new DBPrpJtaxSettle();
    			dbPrpJtaxSettle.setSchema(blPrpJtaxSettle.getArr(j));
    			dbPrpJtaxSettle.setTaxSettleFlag("1");
    			dbPrpJtaxSettle.setTaxSettleDate(currentDate);
    			dbPrpJtaxSettle.setPayRefNo(billNo);
    			
    			if(dbPrpJtaxSettle.getCenterCode().substring(0,2).equals("12"))
    			{
    				if(dbPrpJtaxSettle.getTaxItemCode().equals("A"))
    				{
    					if(dbPrpJtaxSettle.getTaxItemDetailName().equals("大型客车"))
    					{
    						dbPrpJtaxSettle.setCol_01("301");
    					}else if(dbPrpJtaxSettle.getTaxItemDetailName().equals("中型客车"))
    					{
    						dbPrpJtaxSettle.setCol_01("302");
    					}else if(dbPrpJtaxSettle.getTaxItemDetailName().equals("小型客车"))
    					{
    						dbPrpJtaxSettle.setCol_01("303");
    					}else if(dbPrpJtaxSettle.getTaxItemDetailName().equals("微型客车"))
    					{
    						dbPrpJtaxSettle.setCol_01("304");
    					}
    				}else if(dbPrpJtaxSettle.getTaxItemCode().equals("H") || dbPrpJtaxSettle.getTaxItemCode().equals("K"))
    				{
    					dbPrpJtaxSettle.setCol_01("305");
    				}else if(dbPrpJtaxSettle.getTaxItemCode().equals("S") || dbPrpJtaxSettle.getTaxItemCode().equals("D"))
    				{
    					dbPrpJtaxSettle.setCol_01("306");
    				}else if(dbPrpJtaxSettle.getTaxItemCode().equals("Z") || dbPrpJtaxSettle.getTaxItemCode().equals("T"))
    				{
    					dbPrpJtaxSettle.setCol_01("307");
    				}else if(dbPrpJtaxSettle.getTaxItemCode().equals("M"))
    				{
    					dbPrpJtaxSettle.setCol_01("308");
    				}
    			}else if(dbPrpJtaxSettle.getCenterCode().substring(0,2).equals("09"))
    			{
    				
    				if(dbPrpJtaxSettle.getIdentifyType() != "" 
    		    		&& dbPrpJtaxSettle.getIdentifyType() != null
    		    		&& dbPrpJtaxSettle.getIdentifyType().equals("01"))
    				{
    					if(dbPrpJtaxSettle.getIdentifyNumber().length() == 8)
    					{
    						dbPrpJtaxSettle.setCol_05("12");
    					}else
    					{
    						dbPrpJtaxSettle.setCol_05("11");
    					}
    				}else if(dbPrpJtaxSettle.getIdentifyType() != "" 
    		    		&& dbPrpJtaxSettle.getIdentifyType() != null)
    				{
    					if(dbPrpJtaxSettle.getTaxpayerIdentifier().length() == 8)
    					{
    						dbPrpJtaxSettle.setCol_05("97");
    					}else if(dbPrpJtaxSettle.getTaxpayerIdentifier().length() == 9
    							|| dbPrpJtaxSettle.getTaxpayerIdentifier().length() == 10)
    					{
    						dbPrpJtaxSettle.setCol_05("00");
    					}else
    					{
    						dbPrpJtaxSettle.setCol_05("90");
    					}
    				}else
					{
						dbPrpJtaxSettle.setCol_05("90");
					}
    				
    				
    				if(dbPrpJtaxSettle.getTaxItemCode().equals("A"))
    				{
    					if(Integer.parseInt(dbPrpJtaxSettle.getSeatCount()) < 11)
    					{
    						dbPrpJtaxSettle.setCol_03("02");
    					}
    				}else if(dbPrpJtaxSettle.getTaxItemCode().equals("M"))
    				{
    					dbPrpJtaxSettle.setCol_03("07");
    				}else if(dbPrpJtaxSettle.getLicenseColorCode().equals("02"))
    				{
    					dbPrpJtaxSettle.setCol_03("06");
    				}else
    				{
    					dbPrpJtaxSettle.setCol_03("01");
    				}
    				
    				
    				if(dbPrpJtaxSettle.getTaxItemCode().equals("A"))
    				{
    					if(Integer.parseInt(dbPrpJtaxSettle.getSeatCount()) >= 20)
    					{
    						dbPrpJtaxSettle.setCol_02("K11");
    					}else if(Integer.parseInt(dbPrpJtaxSettle.getSeatCount()) >= 10
    							&& Integer.parseInt(dbPrpJtaxSettle.getSeatCount()) < 20)
    					{
    						dbPrpJtaxSettle.setCol_02("K21");
    					}else if(Integer.parseInt(dbPrpJtaxSettle.getSeatCount()) >= 6
    							&& Integer.parseInt(dbPrpJtaxSettle.getSeatCount()) < 10)
    					{
    						dbPrpJtaxSettle.setCol_02("K31");
    					}else if(Integer.parseInt(dbPrpJtaxSettle.getSeatCount()) < 6)
    					{
    						dbPrpJtaxSettle.setCol_02("K33");
    					}
    					if(Double.parseDouble(dbPrpJtaxSettle.getExhaustScale()) <= 1)
    					{
    						dbPrpJtaxSettle.setCol_02("K43");
    					}
    				}else if(dbPrpJtaxSettle.getTaxItemCode().equals("H"))
    				{
    					if(Double.parseDouble(dbPrpJtaxSettle.getTonCount()) > 10)
    					{
    						dbPrpJtaxSettle.setCol_02("H11");
    					}else if(Double.parseDouble(dbPrpJtaxSettle.getTonCount()) > 5
    							&& Double.parseDouble(dbPrpJtaxSettle.getTonCount()) <= 10)
    					{
    						dbPrpJtaxSettle.setCol_02("H21");
    					}else if(Double.parseDouble(dbPrpJtaxSettle.getTonCount()) > 2
    							&& Double.parseDouble(dbPrpJtaxSettle.getTonCount()) <= 5)
    					{
    						dbPrpJtaxSettle.setCol_02("H31");
    					}else if(Double.parseDouble(dbPrpJtaxSettle.getTonCount()) <= 2)
    					{
    						dbPrpJtaxSettle.setCol_02("H41");
    					}
    				}else if(dbPrpJtaxSettle.getTaxItemCode().equals("M"))
    				{
    					if(dbPrpJtaxSettle.getCarKindCode().equals("M2"))
    					{
    						dbPrpJtaxSettle.setCol_02("M11");
    					}else
    					{
    						dbPrpJtaxSettle.setCol_02("M21");
    					}
    				}else
    				{
    					dbPrpJtaxSettle.setCol_02("X99");
    				}
    			}else if(dbPrpJtaxSettle.getCenterCode().substring(0,2).equals("04"))
    			{
    				DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
    				dbPrpJplanFee.getInfo(dbpool,dbPrpJtaxSettle.getCertiType(),dbPrpJtaxSettle.getCertiNo(),
    						dbPrpJtaxSettle.getSerialNo(),dbPrpJtaxSettle.getPayRefReason());
    				if(!dbPrpJplanFee.getUseNatureCode().equals(""))
    				{
    					dbPrpJtaxSettle.setCol_01((String)useNatureCode.get(dbPrpJplanFee.getUseNatureCode()));
    				}
    			}
    			dbPrpJtaxSettle.update(dbpool);
    		}
    		
    		dbPrpCcarshipTax.getInfo(dbpool,this.getArr(i).getPolicyNo(),
    				this.getArr(i).getSerialNo());
    		dbPrpCcarshipTax.setFinalFlag("Y");
    		dbPrpCcarshipTax.update(dbpool);
    	}
    	return billNo;
    }
    
    /**
     *不带dbpool的处理历史数据
     *@return 无
     */
    public void dealHistoryData() throws Exception
    {
    	DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dealHistoryData(dbpool);
	    }
        catch (Exception e)
        {
        	dbpool.rollbackTransaction();
        	throw e;
        }
        finally {
        	dbpool.close();
        }
    }
    
    /**
     * @author zhanglingjian 处理历史数据
     * @param dbpool 数据库连接池
     * @throws UserException
     * @throws Exception
     */
    public void dealHistoryData(DbPool dbpool) throws UserException,Exception
    {
    	BLPrpCcarshipTax blPrpCcarshipTax = new BLPrpCcarshipTax();
    	BLPrpPcarshipTax blPrpPcarshipTax = new BLPrpPcarshipTax();
    	DBPrpCmain dbPrpCmain = new DBPrpCmain();
        DBPrpCitemCar dbPrpCitemCar = new DBPrpCitemCar();
        DBPrpCinsured dbPrpCinsured = new DBPrpCinsured();
        
    	
        
        logger.info("----开始处理XX------");
        
        blPrpCcarshipTax.query(dbpool," 1=1 ",0);
        for(int i=0;i<blPrpCcarshipTax.getSize();i++)
        {
        	dbpool.commitTransaction();
        	
        	logger.info("----XX号------"+blPrpCcarshipTax.getArr(i).getPolicyNo());
        	
            dbPrpCmain.getInfo(dbpool,blPrpCcarshipTax.getArr(i).getPolicyNo());
            dbPrpCitemCar.getInfo(dbpool,blPrpCcarshipTax.getArr(i).getPolicyNo(),"1");
            dbPrpCinsured.getInfo(dbpool,blPrpCcarshipTax.getArr(i).getPolicyNo(),
            		blPrpCcarshipTax.getArr(i).getSerialNo());
            
            if(blPrpCcarshipTax.getArr(i).getTaxRelifFlag().equals("4") || 
            		blPrpCcarshipTax.getArr(i).getTaxRelifFlag().equals("5"))
        	{
            	transPolicyDataHis(dbpool,blPrpCcarshipTax.getArr(i),dbPrpCmain,dbPrpCitemCar,dbPrpCinsured,"R72");
        	}else
        	{
        		transPolicyDataHis(dbpool,blPrpCcarshipTax.getArr(i),dbPrpCmain,dbPrpCitemCar,dbPrpCinsured,"R72");
                transPolicyDataHis(dbpool,blPrpCcarshipTax.getArr(i),dbPrpCmain,dbPrpCitemCar,dbPrpCinsured,"R73");
                transPolicyDataHis(dbpool,blPrpCcarshipTax.getArr(i),dbPrpCmain,dbPrpCitemCar,dbPrpCinsured,"R74");
        	}
            dbpool.beginTransaction();
        }
        
        logger.info("----处理XX结束------");
        
    }
    
    /**
     * @author zhanglingjian 处理历史数据
     * @param dbpool 数据库连接池
     * @throws UserException
     * @throws Exception
     */
    public void transPolicyDataHis(DbPool dbpool,PrpCcarshipTaxSchema prpCcarshipTaxSchema,DBPrpCmain dbPrpCmain,
    		DBPrpCitemCar dbPrpCitemCar,DBPrpCinsured dbPrpCinsured,String strPayRefReason) throws UserException,Exception
    {
    	DBPrpJtaxSettle dbPrpJtaxSettle = new DBPrpJtaxSettle();
    	int result = dbPrpJtaxSettle.getInfo(dbpool,"P",prpCcarshipTaxSchema.getPolicyNo(),
    			prpCcarshipTaxSchema.getSerialNo(),strPayRefReason);
    	if(result == 0)
    	{
    		
    		return;
    	}
    	
    	DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
    	DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
    	DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
    	if(!prpCcarshipTaxSchema.getTaxRelifFlag().equals("4") && 
    			!prpCcarshipTaxSchema.getTaxRelifFlag().equals("5"))
    	{
    		int count = dbPrpJplanFee.getInfo(dbpool,"P",prpCcarshipTaxSchema.getPolicyNo(),
    				prpCcarshipTaxSchema.getSerialNo(),strPayRefReason);
    		if(count == 100)
    		{
    			return;
    		}
    		if(dbPrpJplanFee.getFlag().substring(0,1).equals("1"))
    		{
    			dbPrpJtaxSettle.setFlag("1");
    		}else
    		{
    			dbPrpJtaxSettle.setFlag("0");
    		}
    		if(Double.parseDouble(dbPrpJplanFee.getPlanFee()) == Double.parseDouble(dbPrpJplanFee.getRealPayRefFee()))
        	{
        		
        		int count1 = dbPrpJpayRefRec.getInfo(dbpool,dbPrpJplanFee.getCertiType(),dbPrpJplanFee.getCertiNo(),
        				dbPrpJplanFee.getSerialNo(),dbPrpJplanFee.getPayRefReason(),"1");
        		if(count1 == 100)
        		{
        			return;
        		}
        		int count2 = dbPrpJpayRefMain.getInfo(dbpool,dbPrpJpayRefRec.getPayRefNo(),dbPrpJpayRefRec.getSerialNo());
        		if(count2 == 100)
        		{
        			return;
        		}
        		
        		BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
        		String sql = " policyno='"+prpCcarshipTaxSchema.getPolicyNo()+"' and payrefreason='"+strPayRefReason+"' ";
        		blPrpJplanFee.query(dbpool,sql);
        		dbPrpJtaxSettle.setPayRefFee("0");
        		for(int i=0;i<blPrpJplanFee.getSize();i++)
        		{
        			dbPrpJtaxSettle.setPayRefFee(""+(Double.parseDouble(dbPrpJtaxSettle.getPayRefFee()) +
        					Double.parseDouble(blPrpJplanFee.getArr(i).getRealPayRefFee())));
        		}
        		dbPrpJtaxSettle.setCenterCode(dbPrpJpayRefMain.getCenterCode());
        		dbPrpJtaxSettle.setPayRefDate(dbPrpJpayRefRec.getPayRefDate());
        	}else
        	{
        		dbPrpJtaxSettle.setPayRefFee("0");
        		dbPrpJtaxSettle.setCenterCode("");
        		dbPrpJtaxSettle.setPayRefDate("2100-01-01");
        	}
    	}else
    	{
    		int count = dbPrpJplanFee.getInfo(dbpool,"P",prpCcarshipTaxSchema.getPolicyNo(),
    				prpCcarshipTaxSchema.getSerialNo(),"R10");
    		if(count == 100)
    		{
    			return;
    		}else
    		{
    			if(dbPrpJplanFee.getFlag().substring(0,1).equals("1"))
        		{
        			dbPrpJtaxSettle.setFlag("1");
        		}else
        		{
        			dbPrpJtaxSettle.setFlag("0");
        		}
    		}
    		int count1 = dbPrpJpayRefRec.getInfo(dbpool,"P",prpCcarshipTaxSchema.getPolicyNo(),
    				prpCcarshipTaxSchema.getSerialNo(),"R10","1");
    		if(count1 == 0)
    		{
    			if(dbPrpJpayRefRec.getIdentifyType().equals("1"))
    			{
    				int count2 = dbPrpJpayRefMain.getInfo(dbpool,dbPrpJpayRefRec.getPayRefNo(),dbPrpJpayRefRec.getSerialNo());
            		if(count2 == 100)
            		{
            			return;
            		}
            		dbPrpJtaxSettle.setCenterCode(dbPrpJpayRefMain.getCenterCode());
            		dbPrpJtaxSettle.setPayRefDate(dbPrpJpayRefRec.getPayRefDate());
    			}else
    			{
    				dbPrpJtaxSettle.setPayRefDate("2100-01-01");
    	    		dbPrpJtaxSettle.setCenterCode("");
    			}
    		}else
    		{
    			dbPrpJtaxSettle.setPayRefDate("2100-01-01");
        		dbPrpJtaxSettle.setCenterCode("");
    		}
    	}
        
    	dbPrpJtaxSettle.setCertiType("P");
    	dbPrpJtaxSettle.setCertiNo(prpCcarshipTaxSchema.getPolicyNo());
    	dbPrpJtaxSettle.setPolicyNo(prpCcarshipTaxSchema.getPolicyNo());
    	dbPrpJtaxSettle.setSerialNo(prpCcarshipTaxSchema.getSerialNo());
    	dbPrpJtaxSettle.setPayRefReason(strPayRefReason);
    	dbPrpJtaxSettle.setComCode(dbPrpCmain.getComCode());
    	dbPrpJtaxSettle.setAreaCode(prpCcarshipTaxSchema.getAreaCode());
    	dbPrpJtaxSettle.setTaxpayerIdentifier(prpCcarshipTaxSchema.getTaxpayerIdentifier());
    	dbPrpJtaxSettle.setTaxpayerCode(prpCcarshipTaxSchema.getTaxpayerCode());
    	dbPrpJtaxSettle.setTaxpayerName(prpCcarshipTaxSchema.getTaxpayerName());
    	dbPrpJtaxSettle.setIdentifyNumber(prpCcarshipTaxSchema.getIdentifyNumber());
    	dbPrpJtaxSettle.setInsuredAddress(dbPrpCinsured.getInsuredAddress());
    	dbPrpJtaxSettle.setPhoneNumber(dbPrpCinsured.getPhoneNumber());
    	dbPrpJtaxSettle.setMobile(dbPrpCinsured.getMobile());
    	dbPrpJtaxSettle.setCarOwner(dbPrpCitemCar.getCarOwner());
    	dbPrpJtaxSettle.setEnrollDate(dbPrpCitemCar.getEnrollDate());
    	dbPrpJtaxSettle.setLicenseNo(prpCcarshipTaxSchema.getLicenseNo());
    	dbPrpJtaxSettle.setLicenseColorCode(dbPrpCitemCar.getLicenseColorCode());
    	dbPrpJtaxSettle.setFrameNo(dbPrpCitemCar.getFrameNo());
    	dbPrpJtaxSettle.setEngineNo(dbPrpCitemCar.getEngineNo());
    	dbPrpJtaxSettle.setBrandName(dbPrpCitemCar.getBrandName());
    	dbPrpJtaxSettle.setCarKindCode(prpCcarshipTaxSchema.getCarKindCode());
    	dbPrpJtaxSettle.setCompleteKerbMass(prpCcarshipTaxSchema.getCompleteKerbMass());
    	dbPrpJtaxSettle.setSeatCount(dbPrpCitemCar.getSeatCount());
    	dbPrpJtaxSettle.setTonCount(dbPrpCitemCar.getTonCount());
    	dbPrpJtaxSettle.setTaxItemCode(prpCcarshipTaxSchema.getTaxItemCode());
    	dbPrpJtaxSettle.setTaxItemName(prpCcarshipTaxSchema.getTaxItemName());
    	dbPrpJtaxSettle.setTaxItemDetailName(prpCcarshipTaxSchema.getTaxItemDetailName());
    	dbPrpJtaxSettle.setPayStartDate(prpCcarshipTaxSchema.getPayStartDate());
    	dbPrpJtaxSettle.setPayEndDate(prpCcarshipTaxSchema.getPayEndDate());
    	dbPrpJtaxSettle.setPaidFreeCertificate(prpCcarshipTaxSchema.getPaidFreeCertificate());
    	dbPrpJtaxSettle.setTaxRelifFlag(prpCcarshipTaxSchema.getTaxRelifFlag());
    	if(dbPrpJtaxSettle.getTaxRelifFlag().equals("4"))
    	{
    		dbPrpJtaxSettle.setTaxVoucherKind("完税凭证");
    	}
    	dbPrpJtaxSettle.setRelifReason(prpCcarshipTaxSchema.getRelifReason());
    	dbPrpJtaxSettle.setTaxUnitText(prpCcarshipTaxSchema.getTaxUnitText());
    	dbPrpJtaxSettle.setFreeRate(prpCcarshipTaxSchema.getFreeRate());
    	dbPrpJtaxSettle.setFreeRateText(prpCcarshipTaxSchema.getFreeRateText());
    	dbPrpJtaxSettle.setTaxRelief(prpCcarshipTaxSchema.getTaxRelief());
    	dbPrpJtaxSettle.setTaxUnit(prpCcarshipTaxSchema.getTaxUnit());
    	dbPrpJtaxSettle.setTaxDue(prpCcarshipTaxSchema.getTaxDue());
    	if(prpCcarshipTaxSchema.getTaxRelifFlag().equals("4") || 
    			prpCcarshipTaxSchema.getTaxRelifFlag().equals("5"))
    	{
    		dbPrpJtaxSettle.setTaxActual("0");
        	dbPrpJtaxSettle.setPreviousPay("0");
        	dbPrpJtaxSettle.setLateFee("0");
        	dbPrpJtaxSettle.setAllTax("0");
        	dbPrpJtaxSettle.setPayRefFee("0");
    	}else
    	{
    		if(dbPrpJtaxSettle.getPayRefReason().equals("R72"))
        	{
    			if(Double.parseDouble(prpCcarshipTaxSchema.getTaxActual()) == 0)
    			{
    				return;
    			}
            	dbPrpJtaxSettle.setTaxActual(prpCcarshipTaxSchema.getTaxActual());
            	dbPrpJtaxSettle.setPreviousPay("0");
            	dbPrpJtaxSettle.setLateFee("0");
            	dbPrpJtaxSettle.setAllTax(prpCcarshipTaxSchema.getTaxActual());
        	}
        	if(dbPrpJtaxSettle.getPayRefReason().equals("R73"))
        	{
        		if(Double.parseDouble(prpCcarshipTaxSchema.getPreviousPay()) == 0)
    			{
    				return;
    			}
        		dbPrpJtaxSettle.setTaxActual("0");
            	dbPrpJtaxSettle.setPreviousPay(prpCcarshipTaxSchema.getPreviousPay());
            	dbPrpJtaxSettle.setLateFee("0");
            	dbPrpJtaxSettle.setAllTax(prpCcarshipTaxSchema.getPreviousPay());
        	}
        	if(dbPrpJtaxSettle.getPayRefReason().equals("R74"))
        	{
        		if(Double.parseDouble(prpCcarshipTaxSchema.getLateFee()) == 0)
    			{
    				return;
    			}
        		dbPrpJtaxSettle.setTaxActual("0");
            	dbPrpJtaxSettle.setPreviousPay("0");
            	dbPrpJtaxSettle.setLateFee(prpCcarshipTaxSchema.getLateFee());
            	dbPrpJtaxSettle.setAllTax(prpCcarshipTaxSchema.getLateFee());
        	}
    	}
    	dbPrpJtaxSettle.setTaxSettleFlag("0");
    	dbPrpJtaxSettle.setTaxSettleDate("2100-01-01");
    	dbPrpJtaxSettle.setPayRefNo("");
    	dbPrpJtaxSettle.setExtendChar1("");
    	dbPrpJtaxSettle.setExtendChar2("");
    	dbPrpJtaxSettle.setExtendNum1("0");
    	dbPrpJtaxSettle.setExtendNum2("0");
    	dbPrpJtaxSettle.setExtendDate1("");
    	dbPrpJtaxSettle.setExtendDate2("");
    	dbPrpJtaxSettle.insert(dbpool);
    }
    
    /**
     * @author zhanglingjian 处理历史数据
     * @param dbpool 数据库连接池
     * @throws UserException
     * @throws Exception
     */
    public void transEndorDataHis(DbPool dbpool,PrpPcarshipTaxSchema prpPcarshipTaxSchema,DBPrpCmain dbPrpCmain,
    		DBPrpCitemCar dbPrpCitemCar,DBPrpCinsured dbPrpCinsured,String strPayRefReason) throws UserException,Exception
    {
    	DBPrpJtaxSettle dbPrpJtaxSettle = new DBPrpJtaxSettle();
    	int result = dbPrpJtaxSettle.getInfo(dbpool,"P",prpPcarshipTaxSchema.getPolicyNo(),
    			prpPcarshipTaxSchema.getSerialNo(),strPayRefReason);
    	if(result == 100)
    	{
    		
        	dbPrpJtaxSettle.setCertiType("P");
        	dbPrpJtaxSettle.setCertiNo(prpPcarshipTaxSchema.getPolicyNo());
        	dbPrpJtaxSettle.setPolicyNo(prpPcarshipTaxSchema.getPolicyNo());
        	dbPrpJtaxSettle.setSerialNo(prpPcarshipTaxSchema.getSerialNo());
        	dbPrpJtaxSettle.setPayRefReason(strPayRefReason);
    	}else
    	{
    		return;
    	}
    	
    	DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
    	DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
    	DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
    	if(!prpPcarshipTaxSchema.getTaxRelifFlag().equals("4") && 
    			!prpPcarshipTaxSchema.getTaxRelifFlag().equals("5"))
    	{
    		int count = dbPrpJplanFee.getInfo(dbpool,"E",prpPcarshipTaxSchema.getEndorseNo(),
    				prpPcarshipTaxSchema.getSerialNo(),strPayRefReason);
    		if(count == 100)
    		{
    			
    			return;
    		}
    		if(dbPrpJplanFee.getFlag().substring(0,1).equals("1"))
    		{
    			dbPrpJtaxSettle.setFlag("1");
    		}else
    		{
    			dbPrpJtaxSettle.setFlag("0");
    		}
    		if(Double.parseDouble(dbPrpJplanFee.getPlanFee()) == Double.parseDouble(dbPrpJplanFee.getRealPayRefFee()))
        	{
        		
        		int count1 = dbPrpJpayRefRec.getInfo(dbpool,dbPrpJplanFee.getCertiType(),dbPrpJplanFee.getCertiNo(),
        				dbPrpJplanFee.getSerialNo(),dbPrpJplanFee.getPayRefReason(),"1");
        		if(count1 == 100)
        		{
        			return;
        		}
        		int count2 = dbPrpJpayRefMain.getInfo(dbpool,dbPrpJpayRefRec.getPayRefNo(),dbPrpJpayRefRec.getSerialNo());
        		if(count2 == 100)
        		{
        			return;
        		}
        		dbPrpJtaxSettle.setCenterCode(dbPrpJpayRefMain.getCenterCode());
        		dbPrpJtaxSettle.setPayRefDate(dbPrpJpayRefRec.getPayRefDate());
        	}else
        	{
        		dbPrpJtaxSettle.setCenterCode("");
        		dbPrpJtaxSettle.setPayRefDate("2100-01-01");
        	}
    	}else
    	{
    		int count = dbPrpJplanFee.getInfo(dbpool,"E",prpPcarshipTaxSchema.getEndorseNo(),
    				prpPcarshipTaxSchema.getSerialNo(),"P10");
    		if(count == 100)
    		{
    			return;
    		}
    		if(dbPrpJplanFee.getFlag().substring(0,1).equals("1"))
    		{
    			dbPrpJtaxSettle.setFlag("1");
    		}else
    		{
    			dbPrpJtaxSettle.setFlag("0");
    		}
    		int count1 = dbPrpJpayRefRec.getInfo(dbpool,"E",dbPrpJplanFee.getCertiNo(),
    				dbPrpJplanFee.getSerialNo(),dbPrpJplanFee.getPayRefReason(),"1");
    		if(count1 == 0)
    		{
    			if(dbPrpJpayRefRec.getIdentifyType().equals("1"))
    			{
    				int count2 = dbPrpJpayRefMain.getInfo(dbpool,dbPrpJpayRefRec.getPayRefNo(),dbPrpJpayRefRec.getSerialNo());
            		if(count2 == 100)
            		{
            			return;
            		}
            		dbPrpJtaxSettle.setCenterCode(dbPrpJpayRefMain.getCenterCode());
            		dbPrpJtaxSettle.setPayRefDate(dbPrpJpayRefRec.getPayRefDate());
    			}else
    			{
    				dbPrpJtaxSettle.setPayRefDate("2100-01-01");
    	    		dbPrpJtaxSettle.setCenterCode("");
    			}
    		}else
    		{
    			dbPrpJtaxSettle.setPayRefDate("2100-01-01");
        		dbPrpJtaxSettle.setCenterCode("");
    		}
    	}
        
    	dbPrpJtaxSettle.setComCode(dbPrpCmain.getComCode());
    	dbPrpJtaxSettle.setAreaCode(prpPcarshipTaxSchema.getAreaCode());
    	dbPrpJtaxSettle.setTaxpayerIdentifier(prpPcarshipTaxSchema.getTaxpayerIdentifier());
    	dbPrpJtaxSettle.setTaxpayerCode(prpPcarshipTaxSchema.getTaxpayerCode());
    	dbPrpJtaxSettle.setTaxpayerName(prpPcarshipTaxSchema.getTaxpayerName());
    	dbPrpJtaxSettle.setIdentifyNumber(prpPcarshipTaxSchema.getIdentifyNumber());
    	dbPrpJtaxSettle.setInsuredAddress(dbPrpCinsured.getInsuredAddress());
    	dbPrpJtaxSettle.setPhoneNumber(dbPrpCinsured.getPhoneNumber());
    	dbPrpJtaxSettle.setMobile(dbPrpCinsured.getMobile());
    	dbPrpJtaxSettle.setCarOwner(dbPrpCitemCar.getCarOwner());
    	dbPrpJtaxSettle.setEnrollDate(dbPrpCitemCar.getEnrollDate());
    	dbPrpJtaxSettle.setLicenseNo(prpPcarshipTaxSchema.getLicenseNo());
    	dbPrpJtaxSettle.setLicenseColorCode(dbPrpCitemCar.getLicenseColorCode());
    	dbPrpJtaxSettle.setFrameNo(dbPrpCitemCar.getFrameNo());
    	dbPrpJtaxSettle.setEngineNo(dbPrpCitemCar.getEngineNo());
    	dbPrpJtaxSettle.setBrandName(dbPrpCitemCar.getBrandName());
    	dbPrpJtaxSettle.setCarKindCode(prpPcarshipTaxSchema.getCarKindCode());
    	dbPrpJtaxSettle.setCompleteKerbMass(prpPcarshipTaxSchema.getCompleteKerbMass());
    	dbPrpJtaxSettle.setSeatCount(dbPrpCitemCar.getSeatCount());
    	dbPrpJtaxSettle.setTonCount(dbPrpCitemCar.getTonCount());
    	dbPrpJtaxSettle.setTaxItemCode(prpPcarshipTaxSchema.getTaxItemCode());
    	dbPrpJtaxSettle.setTaxItemName(prpPcarshipTaxSchema.getTaxItemName());
    	dbPrpJtaxSettle.setTaxItemDetailName(prpPcarshipTaxSchema.getTaxItemDetailName());
    	dbPrpJtaxSettle.setPayStartDate(prpPcarshipTaxSchema.getPayStartDate());
    	dbPrpJtaxSettle.setPayEndDate(prpPcarshipTaxSchema.getPayEndDate());
    	dbPrpJtaxSettle.setPaidFreeCertificate(prpPcarshipTaxSchema.getPaidFreeCertificate());
    	dbPrpJtaxSettle.setTaxRelifFlag(prpPcarshipTaxSchema.getTaxRelifFlag());
    	if(dbPrpJtaxSettle.getTaxRelifFlag().equals("4"))
    	{
    		dbPrpJtaxSettle.setTaxVoucherKind("完税凭证");
    	}
    	dbPrpJtaxSettle.setRelifReason(prpPcarshipTaxSchema.getRelifReason());
    	dbPrpJtaxSettle.setTaxUnitText(prpPcarshipTaxSchema.getTaxUnitText());
    	dbPrpJtaxSettle.setFreeRate(prpPcarshipTaxSchema.getFreeRate());
    	dbPrpJtaxSettle.setFreeRateText(prpPcarshipTaxSchema.getFreeRateText());
    	dbPrpJtaxSettle.setTaxRelief(prpPcarshipTaxSchema.getTaxRelief());
    	dbPrpJtaxSettle.setTaxUnit(prpPcarshipTaxSchema.getTaxUnit());
    	dbPrpJtaxSettle.setTaxDue(prpPcarshipTaxSchema.getTaxDue());
    	if(prpPcarshipTaxSchema.getTaxRelifFlag().equals("4") || 
    			prpPcarshipTaxSchema.getTaxRelifFlag().equals("5"))
    	{
    		dbPrpJtaxSettle.setTaxActual("0");
        	dbPrpJtaxSettle.setPreviousPay("0");
        	dbPrpJtaxSettle.setLateFee("0");
        	dbPrpJtaxSettle.setAllTax("0");
        	dbPrpJtaxSettle.setPayRefFee("0");
    	}else
    	{
    		if(dbPrpJtaxSettle.getPayRefReason().equals("R72"))
        	{
    			if(result == 100)
    			{
    				dbPrpJtaxSettle.setTaxActual(dbPrpJplanFee.getPlanFee());
                	dbPrpJtaxSettle.setPreviousPay("0");
                	dbPrpJtaxSettle.setLateFee("0");
                	dbPrpJtaxSettle.setAllTax(dbPrpJplanFee.getPlanFee());
                	dbPrpJtaxSettle.setPayRefFee(dbPrpJplanFee.getRealPayRefFee());
    			}else
    			{
    				dbPrpJtaxSettle.setTaxActual(""+(Double.parseDouble(dbPrpJtaxSettle.getTaxActual()) +
    						Double.parseDouble(dbPrpJplanFee.getPlanFee())));
                	dbPrpJtaxSettle.setPreviousPay("0");
                	dbPrpJtaxSettle.setLateFee("0");
                	dbPrpJtaxSettle.setAllTax(""+(Double.parseDouble(dbPrpJtaxSettle.getAllTax()) +
    						Double.parseDouble(dbPrpJplanFee.getPlanFee())));
                	dbPrpJtaxSettle.setPayRefFee(""+(Double.parseDouble(dbPrpJtaxSettle.getPayRefFee()) +
    						Double.parseDouble(dbPrpJplanFee.getRealPayRefFee())));
    			}
        	}
        	if(dbPrpJtaxSettle.getPayRefReason().equals("R73"))
        	{
        		if(result == 100)
    			{
        			dbPrpJtaxSettle.setTaxActual("0");
                	dbPrpJtaxSettle.setPreviousPay(dbPrpJplanFee.getPlanFee());
                	dbPrpJtaxSettle.setLateFee("0");
                	dbPrpJtaxSettle.setAllTax(dbPrpJplanFee.getPlanFee());
                	dbPrpJtaxSettle.setPayRefFee(dbPrpJplanFee.getRealPayRefFee());
    			}else
    			{
    				dbPrpJtaxSettle.setTaxActual("0");
                	dbPrpJtaxSettle.setPreviousPay(""+(Double.parseDouble(dbPrpJtaxSettle.getPreviousPay()) +
    						Double.parseDouble(dbPrpJplanFee.getPlanFee())));
                	dbPrpJtaxSettle.setLateFee("0");
                	dbPrpJtaxSettle.setAllTax(""+(Double.parseDouble(dbPrpJtaxSettle.getAllTax()) +
    						Double.parseDouble(dbPrpJplanFee.getPlanFee())));
                	dbPrpJtaxSettle.setPayRefFee(""+(Double.parseDouble(dbPrpJtaxSettle.getPayRefFee()) +
    						Double.parseDouble(dbPrpJplanFee.getRealPayRefFee())));
    			}
        	}
        	if(dbPrpJtaxSettle.getPayRefReason().equals("R74"))
        	{
        		if(result == 100)
    			{
        			dbPrpJtaxSettle.setTaxActual("0");
                	dbPrpJtaxSettle.setPreviousPay("0");
                	dbPrpJtaxSettle.setLateFee(dbPrpJplanFee.getPlanFee());
                	dbPrpJtaxSettle.setAllTax(dbPrpJplanFee.getPlanFee());
                	dbPrpJtaxSettle.setPayRefFee(dbPrpJplanFee.getRealPayRefFee());
    			}else
    			{
    				dbPrpJtaxSettle.setTaxActual("0");
                	dbPrpJtaxSettle.setPreviousPay("0");
                	dbPrpJtaxSettle.setLateFee(""+(Double.parseDouble(dbPrpJtaxSettle.getLateFee()) +
    						Double.parseDouble(dbPrpJplanFee.getPlanFee())));
                	dbPrpJtaxSettle.setAllTax(""+(Double.parseDouble(dbPrpJtaxSettle.getAllTax()) +
    						Double.parseDouble(dbPrpJplanFee.getPlanFee())));
                	dbPrpJtaxSettle.setPayRefFee(""+(Double.parseDouble(dbPrpJtaxSettle.getPayRefFee()) +
    						Double.parseDouble(dbPrpJplanFee.getRealPayRefFee())));
    			}
        	}
    	}
    	dbPrpJtaxSettle.setTaxSettleFlag("0");
    	dbPrpJtaxSettle.setTaxSettleDate("2100-01-01");
    	dbPrpJtaxSettle.setPayRefNo("");
    	dbPrpJtaxSettle.setExtendChar1("");
    	dbPrpJtaxSettle.setExtendChar2("");
    	dbPrpJtaxSettle.setExtendNum1("");
    	dbPrpJtaxSettle.setExtendNum2("");
    	dbPrpJtaxSettle.setExtendDate1("");
    	dbPrpJtaxSettle.setExtendDate2("");
    	if(result == 100)
    	{
    		dbPrpJtaxSettle.insert(dbpool);
    	}else
    	{
    		dbPrpJtaxSettle.update(dbpool);
    	}
    }
    
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
