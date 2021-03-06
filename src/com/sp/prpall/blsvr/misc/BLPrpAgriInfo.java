package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.Vector;
import java.util.ArrayList;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.misc.DBPrpAgriInfo;
import com.sp.prpall.schema.PrpAgriInfoSchema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 定义PrpAgriInfo的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-05-30</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 */
public class BLPrpAgriInfo{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpAgriInfo(){
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
     *增加一条PrpAgriInfoSchema记录
     *@param iPrpAgriInfoSchema PrpAgriInfoSchema
     *@throws Exception
     */
    public void setArr(PrpAgriInfoSchema iPrpAgriInfoSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpAgriInfoSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpAgriInfoSchema记录
     *@param index 下标
     *@return 一个PrpAgriInfoSchema对象
     *@throws Exception
     */
    public PrpAgriInfoSchema getArr(int index) throws Exception
    {
      PrpAgriInfoSchema prpAgriInfoSchema = null;
       try
       {
         prpAgriInfoSchema = (PrpAgriInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpAgriInfoSchema;
     }
    /**
     *删除一条PrpAgriInfoSchema记录
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
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart,ArrayList iWhereValue,int intPageNum,int intLineNumPage) throws UserException,Exception
    {
    	DbPool dbpool = new DbPool();
        try {
          dbpool.open(SysConfig.CONST_SUNQUERYDATASOURCE);
          this.query(dbpool, iWherePart, iWhereValue,intPageNum,intLineNumPage);      
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
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpAgriInfo dbPrpAgriInfo = new DBPrpAgriInfo();
      if (iLimitCount > 0 && dbPrpAgriInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpAgriInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpAgriInfo WHERE " + iWherePart; 
        schemas = dbPrpAgriInfo.findByConditions(strSqlStatement);
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
      DBPrpAgriInfo dbPrpAgriInfo = new DBPrpAgriInfo();
      if (iLimitCount > 0 && dbPrpAgriInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpAgriInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpAgriInfo WHERE " + iWherePart; 
        schemas = dbPrpAgriInfo.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpAgriInfo dbPrpAgriInfo = new DBPrpAgriInfo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpAgriInfo.setSchema((PrpAgriInfoSchema)schemas.get(i));
        dbPrpAgriInfo.insert(dbpool);
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
     *@param iPolicyNO PolicyNO
     *@return 无
     */
    public void cancel(DbPool dbpool,String iPolicyNO) throws Exception
    {
      String strSqlStatement = " DELETE FROM PrpAgriInfo WHERE PolicyNO= ?";
      dbpool.prepareInnerStatement(strSqlStatement);
      dbpool.setString(1, iPolicyNO);
	  dbpool.executePreparedUpdate();
	  dbpool.closePreparedStatement();
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iPolicyNO PolicyNO
     *@return 无
     */
    public void cancel(String iPolicyNO ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iPolicyNO);
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
     * 带dbpool根据PolicyNO获取数据
     *@param iPolicyNO PolicyNO
     *@return 无
     */
    public void getData(String iPolicyNO) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        getData(dbpool,iPolicyNO);
      }
      catch (Exception e)
      {
        
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     * 不带dbpool根据PolicyNO获取数据
     *@param dbpool 连接池
     *@param iPolicyNO PolicyNO
     *@return 无
     */
    public void getData(DbPool dbpool,String iPolicyNO) throws Exception
    {
	    String strWherePart = " PolicyNO=? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iPolicyNO);
        query(dbpool, strWherePart, arrWhereValue, 0);
	   
    }
	/**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@author wangchuanzhong 20100602
     *@param dbpool      全局池
     *@param iWherePart  查询条件,传入条件均已绑定变量形式，问号个数与属性值个数一致
     *@param iWhereValue 查询条件各字段值
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,ArrayList iWhereValue, int iLimitCount) throws UserException,Exception
    {   	 
        String strSqlStatement = "";
        DBPrpAgriInfo dbPrpAgriInfo = new DBPrpAgriInfo();
        if (iLimitCount > 0 && dbPrpAgriInfo.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
        	 
            throw new UserException(-98,-1003,"BLPrpAgriInfo.query");
        }else
        {       	 
            initArr();
            strSqlStatement = " SELECT * FROM PrpAgriInfo WHERE " + iWherePart;
            schemas = dbPrpAgriInfo.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@author liying 20110328
     *@param dbpool      全局池
     *@param iWherePart  查询条件,传入条件均已绑定变量形式，问号个数与属性值个数一致
     *@param iWhereValue 查询条件各字段值
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,ArrayList iWhereValue, int intPageNum,int intLineNumPage) throws UserException,Exception
    {   	 
    	int intStartNum = 0;
        int intEndNum = 0;

        intStartNum = (intPageNum - 1) * intLineNumPage;
        intEndNum = intPageNum * intLineNumPage;
        
        StringBuffer strSqlStatement = new StringBuffer();
        DBPrpAgriInfo dbPrpAgriInfo = new DBPrpAgriInfo();
        strSqlStatement.append(" SELECT * FROM ( ");
        strSqlStatement.append("Select RowNum As LineNum, T.*From (  SELECT * FROM PrpAgriInfo WHERE ");
        strSqlStatement.append(iWherePart);
        strSqlStatement.append(") T Where RowNum<=");
        strSqlStatement.append(intEndNum);
        strSqlStatement.append(") Where LineNum>");
        strSqlStatement.append(intStartNum);
        schemas = dbPrpAgriInfo.findByConditions(dbpool,strSqlStatement.toString(),iWhereValue);
    }
    /**
     * 根据查询条件，查询SysConfig.CONST_SUNQUERYDATASOURCE数据源对应的数据.并将这组记录赋给schemas对象 不带dbpool
     * @param iWherePart 查询条件
     * @param intPageNum 页码
     * @param intLineNumPage 每页条数
     * @return 无
     * @throws Exception
     */
    public void queryInsureAgri(String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConfig.CONST_SUNQUERYDATASOURCE);
        this.queryInsureAgriOrder(dbpool,iWherePart,intPageNum,intLineNumPage);
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
     * 根据查询条件，查询SysConfig.CONST_SUNQUERYDATASOURCE数据源对应的数据.并将这组记录赋给schemas对象 不带dbpool
     * @param iWherePart 查询条件
     * @param intPageNum 页码
     * @param intLineNumPage 每页条数
     * @return 无
     * @throws Exception
     */
    public void queryInsureAgri(String iWherePart) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConfig.CONST_SUNQUERYDATASOURCE);
        this.queryInsureAgriOrder(dbpool,iWherePart);
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
     * 根据查询条件，查询SysConfig.CONST_SUNQUERYDATASOURCE数据源对应的数据.并将这组记录赋给schemas对象 带dbpool
     * @param iWherePart 查询条件
     * @param intPageNum 页码
     * @param intLineNumPage 每页条数
     * @return 无
     * @throws Exception
     */
     public void queryInsureAgriOrder(DbPool dbpool,String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
     {
      
       StringBuffer strSqlStatement = new StringBuffer();
      
       int intStartNum = 0;
       int intEndNum = 0;

       intStartNum = (intPageNum - 1) * intLineNumPage;
       intEndNum = intPageNum * intLineNumPage;

       DBPrpCmain dbPrpCmain = new DBPrpCmain();
       DBPrpAgriInfo dbPrpAgriInfo = new DBPrpAgriInfo();
       initArr();
       
  
       strSqlStatement.append(" SELECT * FROM ( ");
       strSqlStatement.append("Select RowNum As LineNum, T.*From ( ");
       
       strSqlStatement.append("Select distinct prpAgriInfo.flag,prpAgriInfo.Policyno,prpAgriInfo.InsuredCode,prpAgriInfo.Insuredname," +
       
       
       "prpAgriInfo.AddressNo,prpAgriInfo.BankCode,prpAgriInfo.RiskName," +
       "prpAgriInfo.unitAmout,prpAgriInfo.proposalNo,prpAgriInfo.Rate, "+
       
       		"prpAgriInfo. LicenseNo,prpAgriInfo. Premium From" +
       		" prpAgriInfo, PrpCmain Where ");
       strSqlStatement.append(iWherePart);
       strSqlStatement.append(") T Where RowNum<=");
       strSqlStatement.append(intEndNum);
       strSqlStatement.append(") Where LineNum>");
       strSqlStatement.append(intStartNum);
       schemas = dbPrpAgriInfo.findByConditions(dbpool,strSqlStatement.toString(),"");
       
     }
     /**
      * 根据查询条件，查询SysConfig.CONST_SUNQUERYDATASOURCE数据源对应的数据.并将这组记录赋给schemas对象 带dbpool
      * @param iWherePart 查询条件
      * @return 无
      * @throws Exception
      */
      public void queryInsureAgriOrder(DbPool dbpool,String iWherePart ) throws UserException,Exception
      {
        StringBuffer strSqlStatement = new StringBuffer();
        DBPrpAgriInfo dbPrpAgriInfo = new DBPrpAgriInfo();
        initArr();
        strSqlStatement.append("Select * From prpAgriInfo Where ");
        strSqlStatement.append(iWherePart);
        schemas = dbPrpAgriInfo.findByConditions(dbpool,strSqlStatement.toString());
      }
     
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
