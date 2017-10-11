package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpPhoneSaleExpense;
import com.sp.prpall.schema.PrpPhoneSaleExpenseSchema;

/**
 * 定义PrpPhoneSaleExpense-核心电销费用表的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-10-11</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 */
public class BLPrpPhoneSaleExpense{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpPhoneSaleExpense(){
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
     *增加一条PrpPhoneSaleExpenseSchema记录
     *@param iPrpPhoneSaleExpenseSchema PrpPhoneSaleExpenseSchema
     *@throws Exception
     */
    public void setArr(PrpPhoneSaleExpenseSchema iPrpPhoneSaleExpenseSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpPhoneSaleExpenseSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpPhoneSaleExpenseSchema记录
     *@param index 下标
     *@return 一个PrpPhoneSaleExpenseSchema对象
     *@throws Exception
     */
    public PrpPhoneSaleExpenseSchema getArr(int index) throws Exception
    {
      PrpPhoneSaleExpenseSchema prpPhoneSaleExpenseSchema = null;
       try
       {
         prpPhoneSaleExpenseSchema = (PrpPhoneSaleExpenseSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpPhoneSaleExpenseSchema;
     }
    /**
     *删除一条PrpPhoneSaleExpenseSchema记录
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
      DBPrpPhoneSaleExpense dbPrpPhoneSaleExpense = new DBPrpPhoneSaleExpense();
      if (iLimitCount > 0 && dbPrpPhoneSaleExpense.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPhoneSaleExpense.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpPhoneSaleExpense WHERE " + iWherePart; 
        schemas = dbPrpPhoneSaleExpense.findByConditions(strSqlStatement);
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
      DBPrpPhoneSaleExpense dbPrpPhoneSaleExpense = new DBPrpPhoneSaleExpense();
      if (iLimitCount > 0 && dbPrpPhoneSaleExpense.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPhoneSaleExpense.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpPhoneSaleExpense WHERE " + iWherePart; 
        schemas = dbPrpPhoneSaleExpense.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpPhoneSaleExpense dbPrpPhoneSaleExpense = new DBPrpPhoneSaleExpense();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpPhoneSaleExpense.setSchema((PrpPhoneSaleExpenseSchema)schemas.get(i));
        dbPrpPhoneSaleExpense.insert(dbpool);
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
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        save(dbpool);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param iEndorseNo 批单号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpPhoneSaleExpense WHERE ProposalNo= '" + iProposalNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iEndorseNo 批单号
     *@return 无
     */
    public void cancel(String iProposalNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iProposalNo);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     * 带dbpool根据批单号获取数据
     *@param iEndorseNo 批单号
     *@return 无
     */
    public void getData(String iProposalNo) throws Exception
    {
        
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			getData(dbpool, iProposalNo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbpool.close();
		}
		
    }
      
    /**
     * 不带dbpool根据批单号获取数据
     *@param dbpool 连接池
     *@param iEndorseNo 批单号
     *@return 无
     */
    public void getData(DbPool dbpool,String iProposalNo) throws Exception
    {
      
      
      
      
      String strWherePart = " ProposalNo= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iProposalNo);
      query(dbpool, strWherePart, arrWhereValue, 0);  
      
    }
    
    /**
     *@author zhanghaoyu 20141020 
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param iWhereValue 查询条件各字段值
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,ArrayList iWhereValue,int iLimitCount) throws UserException,Exception
    {
      if(iWherePart.indexOf("'null'")>-1){
          throw new Exception("查询条件异常，请联系系统管理员！");
      }
      String strSqlStatement = "";
      DBPrpPhoneSaleExpense dbPrpPhoneSaleExpense = new DBPrpPhoneSaleExpense();
      if (iLimitCount > 0 && dbPrpPhoneSaleExpense.getCount(dbpool, iWherePart, iWhereValue) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPhoneSaleExpense.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpPhoneSaleExpense WHERE " + iWherePart; 
        schemas = dbPrpPhoneSaleExpense.findByConditions(dbpool,strSqlStatement, iWhereValue);
      }
    }    
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
