package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefDetail;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefMain;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFee;
import com.sp.prpall.schema.PrpJpayRefDetailSchema;
import com.sp.prpall.schema.PrpJpayRefMainSchema;
import com.sp.prpall.schema.PrpJplanFeeSchema;

/**
 * 定义PrpJpayRefDetail的BL类
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>@createdate 2005-05-27</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJpayRefDetail{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */
    public BLPrpJpayRefDetail(){
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
     *增加一条PrpJpayRefDetailSchema记录
     *@param iPrpJpayRefDetailSchema PrpJpayRefDetailSchema
     *@throws Exception
     */
    public void setArr(PrpJpayRefDetailSchema iPrpJpayRefDetailSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJpayRefDetailSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpJpayRefDetailSchema记录
     *@param index 下标
     *@return 一个PrpJpayRefDetailSchema对象
     *@throws Exception
     */
    public PrpJpayRefDetailSchema getArr(int index) throws Exception
    {
     PrpJpayRefDetailSchema prpJpayRefDetailSchema = null;
       try
       {
        prpJpayRefDetailSchema = (PrpJpayRefDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJpayRefDetailSchema;
     }
    /**
     *删除一条PrpJpayRefDetailSchema记录
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
      DBPrpJpayRefDetail dbPrpJpayRefDetail = new DBPrpJpayRefDetail();
      if (iLimitCount > 0 && dbPrpJpayRefDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRefDetail WHERE " + iWherePart;
        schemas = dbPrpJpayRefDetail.findByConditions(strSqlStatement);
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
      DBPrpJpayRefDetail dbPrpJpayRefDetail = new DBPrpJpayRefDetail();
      if (iLimitCount > 0 && dbPrpJpayRefDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRefDetail WHERE " + iWherePart;
        schemas = dbPrpJpayRefDetail.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJpayRefDetail dbPrpJpayRefDetail = new DBPrpJpayRefDetail();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJpayRefDetail.setSchema((PrpJpayRefDetailSchema)schemas.get(i));
      dbPrpJpayRefDetail.insert(dbpool);
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
     *收付款确认 SangMingqian 20050615(生成凭证信息有待补充)
     *@param strCondition 筛选条件,index 选中记录,
             strPayRefDate 收付确认日期,strPayRefUnit 收付确认单位,strPayRefCode 收付确认人
     *@throws Exception
    */
    public void payRefVerity(String strCondition,String[] index,String strPayRefDate,String strPayRefUnit,String strPayRefCode) throws UserException,SQLException,Exception
    {
      BLPrpJpayRefDetail blPrpJpayRefDetail = new  BLPrpJpayRefDetail();
      BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
      BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
      BLPrpJplanFee blPrpJplanfee = new BLPrpJplanFee();

      PrpJpayRefMainSchema prpJpayRefMainSchema = null;
      PrpJplanFeeSchema prpJplanFeeSchema = null;

      DBPrpJpayRefDetail dbPrpJpayRefDetail = new DBPrpJpayRefDetail();
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();

      blPrpJpayRefMain.queryWithRec(strCondition,0);
      
      String strConditionRec = "1=1";
      strConditionRec = strConditionRec + " AND ( 1=0 ";
      
      String strConditionFee = "1=1";
      strConditionFee = strConditionFee + " AND ( 1=0 ";

      DbPool dbpool = new DbPool();
      
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();

        
        this.save(dbpool);

        
        for(int i = 0;i<index.length;i++){
          prpJpayRefMainSchema = blPrpJpayRefMain.getArr(Integer.parseInt(index[i]));

          dbPrpJpayRefMain = new DBPrpJpayRefMain();
          dbPrpJpayRefMain.setSchema(prpJpayRefMainSchema);
          dbPrpJpayRefMain.setPayRefCode(strPayRefCode);
          dbPrpJpayRefMain.setPayRefUnit(strPayRefUnit);
          dbPrpJpayRefMain.setPayRefDate(strPayRefDate);
          dbPrpJpayRefMain.update(dbpool);

          strConditionRec += " OR PayRefNo='"+ blPrpJpayRefMain.getArr(Integer.parseInt(index[i])).getPayRefNo() +"' ";
        }
        strConditionRec += " ) ";

        
        blPrpJpayRefRec.query(strConditionRec,0);
        for (int j=0;j<blPrpJpayRefRec.getSize();j++){
          strConditionFee += " OR (CertiType='" + blPrpJpayRefRec.getArr(j).getCertiType() + "' AND CertiNo='"
                              + blPrpJpayRefRec.getArr(j).getCertiNo() + "' AND SerialNo='"
                              + blPrpJpayRefRec.getArr(j).getSerialNo() + "' AND PayRefReason='"
                              + blPrpJpayRefRec.getArr(j).getPayRefReason() + "')";
        }
        strConditionFee += " ) ";
        blPrpJplanfee.query(strConditionFee,0);
        for(int k=0;k<blPrpJplanfee.getSize();k++){
          prpJplanFeeSchema=blPrpJplanfee.getArr(k);
          dbPrpJplanFee = new DBPrpJplanFee();
          dbPrpJplanFee.setSchema(prpJplanFeeSchema);
          dbPrpJplanFee.setRealPayRefFee(prpJplanFeeSchema.getPayRefFee());
          dbPrpJplanFee.update(dbpool);
        }

        dbpool.commitTransaction();
        dbpool.close();
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
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
