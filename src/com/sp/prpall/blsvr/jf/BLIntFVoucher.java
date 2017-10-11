package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.sp.prpall.dbsvr.jf.DBIntFVoucher;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefMain;
import com.sp.prpall.schema.IntFVoucherSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义BLIntFVoucher的BL类
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>@createdate 2007-12-27</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLIntFVoucher {
	private Vector schemas = new Vector();
    /**
     * 构造函数
     */
    public BLIntFVoucher(){
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
     *增加一条IntFVoucherSchema记录
     *@param iIntFVoucherSchema IntFVoucherSchema
     *@throws Exception
     */
    public void setArr(IntFVoucherSchema iIntFVoucherSchema) throws Exception
    {
      try
      {
        schemas.add(iIntFVoucherSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }

    /**
     *得到一条IntFVoucherSchema记录
     *@param index 下标
     *@return 一个IntFVoucherSchema对象
     *@throws Exception
     */
    public IntFVoucherSchema getArr(int index) throws Exception
    {
    	IntFVoucherSchema intFVoucherSchema = null;
      try
      {
    	  intFVoucherSchema = (IntFVoucherSchema)this.schemas.get(index);
      }
      catch(Exception e)
      {
        throw e;
      }
      return intFVoucherSchema;
    }

    /**
     *删除一条IntFVoucherSchema记录
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
      DBIntFVoucher dbIntFVoucher = new DBIntFVoucher();
      if (iLimitCount > 0 && dbIntFVoucher.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntFVoucher.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntFVoucher WHERE " + iWherePart;
        schemas = dbIntFVoucher.findByConditions(strSqlStatement);
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
      DBIntFVoucher dbIntFVoucher = new DBIntFVoucher();
      if (iLimitCount > 0 && dbIntFVoucher.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntFVoucher.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntFVoucher WHERE " + iWherePart;
        schemas = dbIntFVoucher.findByConditions(dbpool,strSqlStatement);
      }
    }
    /**
     *联合PrpJpayRefRec表查询先查出打包号，然后把打包号下的所有发票查出来 zhanglingjian
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryVoucherStatusList(String iWherePart,String iOrderPart) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBIntFVoucher dbIntFVoucher = new DBIntFVoucher();
      initArr();
      String sql ="select segment1,prpdcompany.comcname ," +
      		"intfvoucher.journalyear||decode(length(intfvoucher.journalmonth),1,'0','')||intfvoucher.journalmonth journalyear," +
      		" case when intfvoucher.journalstatus='0' then '未复核' " +
      		"when intfvoucher.journalstatus='1' then '已复核' " +
      		"else '打回' end status" +
      		",count(1) stanum " +
      		"from intfvoucher intfvoucher,prpdcompany prpdcompany " +
      		"where intfvoucher.segment1=prpdcompany.comcode " ;
      iOrderPart	=	"group by segment1,prpdcompany.comcname,intfvoucher.journalstatus,intfvoucher.journalyear,intfvoucher.journalmonth　" +
      		"order by  segment1,status　";
      strSqlStatement = sql + iWherePart + iOrderPart;
      schemas = dbIntFVoucher.findVoucherStatus(strSqlStatement);
    }
    /*
     *@Author     : 
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

    /*
     *@author lijibin 20060520
     *@desc   根据年月获取该月第一天
     *@param  iYearMonth
     *@return strMonthFirstDay
     */
    public String getMonthFirstDay(String iYearMonth) throws Exception
    {
      String strMonthFirstDay = "";
      int intYear = 0;
      String strMonth = "";

      if (iYearMonth==null||iYearMonth.trim().length() != 6) {
        return iYearMonth;
      }

      strMonthFirstDay = iYearMonth.substring(0,4)+"-"+iYearMonth.substring(4,6)+"-01";
      return strMonthFirstDay;
    }   
    /**
     * 
     * @param ve 传入显示金额的列表，用来装配
     * @throws Exception
     */
    public void assembleAmount(Vector ve) throws Exception{
    	Vector vc = new Vector();
    	for(int i=0;i<this.schemas.size();i++){
    		IntFVoucherSchema intFVoucherSchema = (IntFVoucherSchema)this.schemas.get(i);
    		for(int j=0;j<ve.size();j++){
    			IntFVoucherSchema intFVoucherSchemaForAmount = (IntFVoucherSchema)ve.get(j);
    			if(intFVoucherSchemaForAmount.getVoucherNo().equals(intFVoucherSchema.getVoucherNo())
    					&&intFVoucherSchemaForAmount.getSegment1().equals(intFVoucherSchema.getSegment1())){
    				intFVoucherSchema.setUploadDate(intFVoucherSchemaForAmount.getRealPayNo());
    				vc.add(intFVoucherSchema);
    			}
    		}
    	}
    	this.schemas = vc;
    }
}
