package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import java.text.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.*;
import com.sp.prpall.dbsvr.jf.DBPrpJreserveDetail;
import com.sp.prpall.schema.PrpJreserveDetailSchema;
import com.sp.prpall.blsvr.cb.BLPrpCmain;
import com.sp.prpall.blsvr.qry.BLPolicySumQuery;
import com.sp.prpall.blsvr.jf.*;
import com.sp.prpall.blsvr.pg.BLPrpPhead;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRec;

/**
 * 定义PrpJreserveDetail的BL类
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-12-12</p>
 * @Author     : X
 * @version 1.0
 */
public class BLPrpJreserveDetail{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */
    public BLPrpJreserveDetail(){
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
     *增加一条PrpJreserveDetailSchema记录
     *@param iPrpJreserveDetailSchema PrpJreserveDetailSchema
     *@throws Exception
     */
    public void setArr(PrpJreserveDetailSchema iPrpJreserveDetailSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJreserveDetailSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }

     /**
      *替换一条PrpJreserveDetailSchema记录
      *@param iPrpJreserveDetailSchema PrpJreserveDetailSchema
      *@param index 替换索引值
      *@throws Exception
      */
     public void setArr(PrpJreserveDetailSchema iPrpJreserveDetailSchema,int index) throws Exception
     {
       try {
         schemas.setElementAt(iPrpJreserveDetailSchema,index);
       }
       catch (Exception e) {
         throw e;
       }
     }

    /**
     *得到一条PrpJreserveDetailSchema记录
     *@param index 下标
     *@return 一个PrpJreserveDetailSchema对象
     *@throws Exception
     */
    public PrpJreserveDetailSchema getArr(int index) throws Exception
    {
     PrpJreserveDetailSchema prpJreserveDetailSchema = null;
       try
       {
        prpJreserveDetailSchema = (PrpJreserveDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJreserveDetailSchema;
     }
    /**
     *删除一条PrpJreserveDetailSchema记录
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
       this.query(iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBPrpJreserveDetail dbPrpJreserveDetail = new DBPrpJreserveDetail();
      if (iLimitCount > 0 && dbPrpJreserveDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJreserveDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJreserveDetail WHERE " + iWherePart;
        schemas = dbPrpJreserveDetail.findByConditions(strSqlStatement);
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
       this.query(dbpool,iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBPrpJreserveDetail dbPrpJreserveDetail = new DBPrpJreserveDetail();
      if (iLimitCount > 0 && dbPrpJreserveDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJreserveDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJreserveDetail WHERE " + iWherePart;
        schemas = dbPrpJreserveDetail.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJreserveDetail dbPrpJreserveDetail = new DBPrpJreserveDetail();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJreserveDetail.setSchema((PrpJreserveDetailSchema)schemas.get(i));
      dbPrpJreserveDetail.insert(dbpool);
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
     *带dbpool的save方法
     *@param iPolicyWhere  查询XX的Where语句
     *@param iReserveWhere XXXXX种和部门的Where部分
     *@param iBeginDate    本期起始日期
     *@param iEndDate      下期起始日期
     *@param iReserveTerm  本期期间 年/月
     *@param iTotalFee     总计费用
     *@return 无
     */
    public BLPrpJreserve getReserveData(String iPolicyWhere,String iReserveWhere,String iBeginDate,String iEndDate,String iReserveTerm,String iCurrency,double iTotalFee) throws Exception
    {
      
      BLPrpCmain blPrpCmain = new BLPrpCmain();
      BLPolicySumQuery blPolicySumQuery = new BLPolicySumQuery();
      PrpJreserveDetailSchema schema = null;
      ChgDate chgDate = new ChgDate();

      
      BLPrpJpayRec blPrpJpayRec = new BLPrpJpayRec();
      BLPrpJrefRec blPrpJrefRec = new BLPrpJrefRec();
      BLPrpPhead   blPrpPhead   = new BLPrpPhead();
      double sumRealPayFee = 0.0;   

      String maxReserveTerm = "";
      BLPrpJreserve blPrpJreserve = new BLPrpJreserve();

      
      DbPool dbpool = new DbPool();
      

      try {
    	  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        
        blPrpCmain.query(dbpool,iPolicyWhere,0);
        for(int i=0; i<blPrpCmain.getSize(); i++)
        {
          
          
          double dblShareRate = blPolicySumQuery.getShareRate(dbpool,blPrpCmain.getArr(i));
          
          schema = new PrpJreserveDetailSchema();
          schema.setPolicyNo(blPrpCmain.getArr(i).getPolicyNo());
          schema.setClassCode(blPrpCmain.getArr(i).getClassCode());
          schema.setRiskCode(blPrpCmain.getArr(i).getRiskCode());
          schema.setInsuredCode(blPrpCmain.getArr(i).getInsuredCode());
          schema.setInsuredName(blPrpCmain.getArr(i).getInsuredName());
          schema.setStartDate(blPrpCmain.getArr(i).getStartDate());
          schema.setEndDate(blPrpCmain.getArr(i).getEndDate());


          schema.setLimitYear(blPrpCmain.getArr(i).getEndDate().substring(0,4));
          schema.setHandleDate(chgDate.getCurrentTime("yyyy-MM-dd"));
          schema.setReserveTerm(iReserveTerm);
          schema.setCurrency(blPrpCmain.getArr(i).getCurrency());
          schema.setSumPremium(blPrpCmain.getArr(i).getSumPremium());
          schema.setThisRealPremium("0.0");
          schema.setTotalRealPremium("0.0");
          schema.setRetentionRate(""+dblShareRate);
          schema.setThisRealPayFee("0.0");
          schema.setTotalRealPayFee("0.0");
          schema.setThisFee("0.0");
          schema.setTotalFee("0.0");
          schema.setComCode(blPrpCmain.getArr(i).getComCode());
          schema.setFlag("");
          this.setArr(schema);
        }
        this.save();

        
        String strPayWhere = " RealPayDate<'"+iEndDate+"' AND RealPayDate>='"+iBeginDate+"' AND Currency2='"+iCurrency+"' AND "+iReserveWhere;
        blPrpJpayRec.getSumFeeByPolicyNo(strPayWhere);
        
        DBPrpJpayRec dbPrpJpayRec = new DBPrpJpayRec();
        sumRealPayFee = dbPrpJpayRec.getSumRealPayFee(strPayWhere);

        
        blPrpJrefRec.getSumFeeByPolicyNo(strPayWhere);
        
        String strEndorWhere = " (ValidDate>=UnderWriteEndDate AND ValidDate<'"+iEndDate+"' AND ValidDate>='"+iBeginDate+"'"
                             + " OR ValidDate<UnderWriteEndDate AND UnderWriteEndDate<'"+iEndDate+"' AND UnderWriteEndDate>='"+iBeginDate+"')"
                             + " AND UnderWriteFlag IN ('1','3') AND "+iReserveWhere;
        blPrpPhead.query(strEndorWhere,0);
        
        maxReserveTerm = blPrpJreserve.getMaxReserveTerm(iReserveWhere);
      }
      catch(Exception e)
      {
        throw e;
      }

      
      DBPrpJreserveDetail dbPrpJreserveDetail = null;
      DecimalFormat decimalFormat = new DecimalFormat("#0.00");
      iReserveWhere += " AND Currency='"+iCurrency+"'";

      try {
        dbpool.beginTransaction();
        
        String strSQL = "";
        if(maxReserveTerm.equals(iReserveTerm))
          strSQL = "UPDATE PrpJreserveDetail SET TotalRealPremium=TotalRealPremium-ThisRealPremium,ThisRealPremium=0,"
                 + "TotalRealPayFee=TotalRealPayFee-ThisRealPayFee,ThisRealPayFee=0,"
                 + "TotalFee=TotalFee-ThisFee,ThisFee=0,HandleDate='"+chgDate.getCurrentTime("yyyy-MM-dd")+"' "
                 + "WHERE ReserveTerm='"+iReserveTerm+"' AND "+iReserveWhere;
        else
          strSQL = "UPDATE PrpJreserveDetail SET ThisRealPremium=0,ThisRealPayFee=0,ThisFee=0,HandleDate='"+chgDate.getCurrentTime("yyyy-MM-dd")+"',"
                 + "ReserveTerm='"+iReserveTerm+"' WHERE EndDate>='"+iBeginDate+"' AND "+iReserveWhere;
        dbpool.update(strSQL);

        
        double propotionFee = 0.0;
        String strThisFee = "0.0";
        double dblSumThisFee = 0.0;
        double dblMaxRealPayFee = 0.0;
        String policyNo = "";
        if(sumRealPayFee!=0)
          propotionFee = iTotalFee/sumRealPayFee;
        for(int i=0; i<blPrpJpayRec.getSize(); i++)
        {
          
          strThisFee = decimalFormat.format(Double.parseDouble(blPrpJpayRec.getArr(i).getRealPayFee())*propotionFee);
          dblSumThisFee += Double.parseDouble(strThisFee);
          strSQL = "UPDATE PrpJreserveDetail SET ThisRealPremium="+blPrpJpayRec.getArr(i).getRealPayFee()+","
                 + "TotalRealPremium=TotalRealPremium+"+blPrpJpayRec.getArr(i).getRealPayFee()+","
                 + "ThisRealPayFee=0,HandleDate='"+chgDate.getCurrentTime("yyyy-MM-dd")+"',"
                 + "ThisFee="+strThisFee+",TotalFee=TotalFee+"+strThisFee+","
                 + "ReserveTerm='"+iReserveTerm+"' WHERE PolicyNo='"+blPrpJpayRec.getArr(i).getPolicyNo()+"'";
          dbpool.update(strSQL);

          
          if(dblMaxRealPayFee<Double.parseDouble(blPrpJpayRec.getArr(i).getRealPayFee()))
          {
            dblMaxRealPayFee = Double.parseDouble(blPrpJpayRec.getArr(i).getRealPayFee());
            policyNo = blPrpJpayRec.getArr(i).getPolicyNo();
          }
        }

        
        if(Math.abs(iTotalFee-dblSumThisFee)>0.005)
        {
          strThisFee = decimalFormat.format(iTotalFee-dblSumThisFee);
          strSQL = "UPDATE PrpJreserveDetail SET "
                 + "ThisFee=ThisFee+"+strThisFee+",TotalFee=TotalFee+"+strThisFee
                 + " WHERE PolicyNo='"+policyNo+"'";
          dbpool.update(strSQL);
        }

        
        for(int i=0; i<blPrpJrefRec.getSize(); i++)
        {
          dbPrpJreserveDetail = new DBPrpJreserveDetail();
          dbPrpJreserveDetail.getInfo(dbpool,blPrpJrefRec.getArr(i).getPolicyNo());
          if(dbPrpJreserveDetail.getReserveTerm().equals(iReserveTerm))
            strSQL = "UPDATE PrpJreserveDetail SET ThisRealPayFee="+blPrpJrefRec.getArr(i).getRealPayFee()+","
                   + "TotalRealPayFee=TotalRealPayFee+"+blPrpJrefRec.getArr(i).getRealPayFee()
                   + " WHERE PolicyNo='"+blPrpJrefRec.getArr(i).getPolicyNo()+"'";
          else
            strSQL = "UPDATE PrpJreserveDetail SET ThisRealPayFee="+blPrpJrefRec.getArr(i).getRealPayFee()+","
                   + "TotalRealPayFee=TotalRealPayFee+"+blPrpJrefRec.getArr(i).getRealPayFee()+","
                   + "ThisRealPremium=0,ThisFee=0,HandleDate='"+chgDate.getCurrentTime("yyyy-MM-dd")+"',"
                   + "ReserveTerm='"+iReserveTerm+"' WHERE PolicyNo='"+blPrpJrefRec.getArr(i).getPolicyNo()+"'";
          dbpool.update(strSQL);
        }

        
        for(int i=0; i<blPrpPhead.getSize(); i++)
        {
          DBPrpCmain dbPrpCmain = new DBPrpCmain();
          dbPrpCmain.getInfo(dbpool,blPrpPhead.getArr(i).getPolicyNo());
          strSQL = "UPDATE PrpJreserveDetail SET EndDate='"+dbPrpCmain.getEndDate()+"',"
                 + "SumPremium="+dbPrpCmain.getEndDate()+" WHERE PolicyNo='"+dbPrpCmain.getPolicyNo()+"'";
          dbpool.update(strSQL);
        }
        dbpool.commitTransaction();

        
        blPrpJreserve.getReserveMoney(iReserveTerm,iBeginDate,iEndDate,iReserveWhere);

      }
      catch(Exception e)
      {
        dbpool.rollbackTransaction();
        throw e;
      }
      finally {
        dbpool.close();
      }
      return blPrpJreserve;
    }

    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
