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
 * ����BLIntFVoucher��BL��
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>@createdate 2007-12-27</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLIntFVoucher {
	private Vector schemas = new Vector();
    /**
     * ���캯��
     */
    public BLIntFVoucher(){
    }

    /**
     *��ʼ����¼
     *@param ��
     *@return ��
     *@throws Exception
     */
    public void initArr() throws Exception
    {
      schemas = new Vector();
    }

    /**
     *����һ��IntFVoucherSchema��¼
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
     *�õ�һ��IntFVoucherSchema��¼
     *@param index �±�
     *@return һ��IntFVoucherSchema����
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
     *ɾ��һ��IntFVoucherSchema��¼
     *@param index �±�
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
     *�õ�schemas��¼��
     *@return schemas��¼��
     *@throws Exception
     */
    public int getSize() throws Exception
    {
      return this.schemas.size();
    }

    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart) throws UserException,Exception
    {
      this.query(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }

    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
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
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
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
     *����PrpJpayRefRec���ѯ�Ȳ������ţ�Ȼ��Ѵ�����µ����з�Ʊ����� zhanglingjian
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
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
      		" case when intfvoucher.journalstatus='0' then 'δ����' " +
      		"when intfvoucher.journalstatus='1' then '�Ѹ���' " +
      		"else '���' end status" +
      		",count(1) stanum " +
      		"from intfvoucher intfvoucher,prpdcompany prpdcompany " +
      		"where intfvoucher.segment1=prpdcompany.comcode " ;
      iOrderPart	=	"group by segment1,prpdcompany.comcname,intfvoucher.journalstatus,intfvoucher.journalyear,intfvoucher.journalmonth��" +
      		"order by  segment1,status��";
      strSqlStatement = sql + iWherePart + iOrderPart;
      schemas = dbIntFVoucher.findVoucherStatus(strSqlStatement);
    }
    /*
     *@Author     : 
     *@desc   �������»�ȡ�������һ��
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
     *@desc   �������»�ȡ���µ�һ��
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
     * @param ve ������ʾ�����б�����װ��
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
