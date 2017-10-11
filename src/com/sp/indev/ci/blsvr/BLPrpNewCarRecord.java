package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.prpall.dbsvr.tb.DBPrpTmain;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBPrpNewCarRecord;
import com.sp.indiv.ci.schema.PrpNewCarRecordSchema;

/**
 * 定义prpNewCarRecord的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-12-22</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 */
public class BLPrpNewCarRecord{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpNewCarRecord(){
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
     *增加一条PrpNewCarRecordSchema记录
     *@param iPrpNewCarRecordSchema PrpNewCarRecordSchema
     *@throws Exception
     */
    public void setArr(PrpNewCarRecordSchema iPrpNewCarRecordSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpNewCarRecordSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpNewCarRecordSchema记录
     *@param index 下标
     *@return 一个PrpNewCarRecordSchema对象
     *@throws Exception
     */
    public PrpNewCarRecordSchema getArr(int index) throws Exception
    {
      PrpNewCarRecordSchema prpNewCarRecordSchema = null;
       try
       {
         prpNewCarRecordSchema = (PrpNewCarRecordSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpNewCarRecordSchema;
     }
    /**
     *删除一条PrpNewCarRecordSchema记录
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
      DBPrpNewCarRecord dbPrpNewCarRecord = new DBPrpNewCarRecord();
      if (iLimitCount > 0 && dbPrpNewCarRecord.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpNewCarRecord.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpNewCarRecord WHERE " + iWherePart; 
        schemas = dbPrpNewCarRecord.findByConditions(strSqlStatement);
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
      DBPrpNewCarRecord dbPrpNewCarRecord = new DBPrpNewCarRecord();
      if (iLimitCount > 0 && dbPrpNewCarRecord.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpNewCarRecord.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpNewCarRecord WHERE " + iWherePart; 
        schemas = dbPrpNewCarRecord.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpNewCarRecord dbPrpNewCarRecord = new DBPrpNewCarRecord();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpNewCarRecord.setSchema((PrpNewCarRecordSchema)schemas.get(i));
        dbPrpNewCarRecord.insert(dbpool);
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
     *@param iEngineNo EngineNo
     *@return 无
     */
    public void cancel(DbPool dbpool,String iEngineNo) throws Exception
    {




  	  String strSqlStatement = "DELETE FROM PrpNewCarRecord WHERE EngineNo= ? ";
  	  dbpool.prepareInnerStatement(strSqlStatement);
  	  dbpool.setString(1, iEngineNo);
  	  dbpool.executePreparedUpdate();
  	  dbpool.closePreparedStatement();
      
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iEngineNo EngineNo
     *@return 无
     */
    public void cancel(String iEngineNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iEngineNo);
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
     * 带dbpool根据EngineNo获取数据
     *@param iEngineNo EngineNo
     *@param iRackNo RackNo
     *@return 无
     */
    public void getData(String iEngineNo,String iRackNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iEngineNo,iRackNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据EngineNo获取数据
     *@param dbpool 连接池
     *@param iEngineNo EngineNo
     *@return 无
     */
    public void getData(DbPool dbpool,String iEngineNo ,String iRackNo) throws Exception
    {
      
      
      
      
      String strWherePart = " EngineNo= ? and RackNo= ?";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iEngineNo);
      arrWhereValue.add(iRackNo);
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
        DBPrpNewCarRecord dbPrpNewCarRecord = new DBPrpNewCarRecord();
        if (iLimitCount > 0 && dbPrpNewCarRecord.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpNewCarRecord.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpNewCarRecord WHERE " + iWherePart;
            schemas = dbPrpNewCarRecord.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * 不带dbpool根据XX号获取数据
     *@param iEndorseNo XX号
     *@return 无
     */
    public void getDataByValidNo(String iEngineNo,String iRackNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      getDataByEngineNo(dbpool,iEngineNo,iRackNo);
      dbpool.close();
    }
      
    /**
     * 带dbpool根据XX号获取数据
     *@param dbpool 连接池
     *@param ValidNo XX确认码
     *@return 无
     */
    public void getDataByEngineNo(DbPool dbpool,String iEngineNo,String iRackNo) throws Exception
    {
    String strWherePart = "";
    strWherePart = "EngineNo= '" + iEngineNo + "'"+" and RackNo = '"+iRackNo+"'";
    query(dbpool,strWherePart,0);
    }
    /**
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param null null
     *@return 无
     */
    public void cancelByEngineNo(DbPool dbpool,String iEngineNo,String iRackNo) throws Exception
    {



      String strSqlStatement = "DELETE FROM PrpNewCarRecord WHERE EngineNo=  ? and RackNo = ?";
	  dbpool.prepareInnerStatement(strSqlStatement);
	  dbpool.setString(1, iEngineNo);
	  dbpool.setString(2, iRackNo);
	  dbpool.executePreparedUpdate();
	  dbpool.closePreparedStatement();
    }
    
    /**
     * 根据查询条件，查询SysConfig.CONST_SUNQUERYDATASOURCE数据源对应的数据.并将这组记录赋给schemas对象 不带dbpool
     * @author wangchuanzhong 20100531
     * @param iWherePart 查询条件
     * @param intPageNum 页码
     * @param intLineNumPage 每页条数
     * @return 无
     * @throws Exception
     */
    public void queryCarRecordInfo(String iWherePart, int intPageNum,int intLineNumPage) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConfig.CONST_SUNQUERYDATASOURCE);
        this.queryCarRecordInfo(dbpool,iWherePart,intPageNum,intLineNumPage);
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
     * @author wangchuanzhong 201005231
     * @param iWherePart 查询条件
     * @param intPageNum 页码
     * @param intLineNumPage 每页条数
     * @return 无
     * @throws Exception
     */
     public void queryCarRecordInfo(DbPool dbpool,String iWherePart, int intPageNum,int intLineNumPage
     		) throws UserException,Exception
     {
         
         if(iWherePart.indexOf("'null'")>-1){
             throw new Exception("查询条件异常，请联系系统管理员！");
         }
         
       StringBuffer strSqlStatement = new StringBuffer();
       int intStartNum = 0;
       int intEndNum = 0;

       intStartNum = (intPageNum - 1) * intLineNumPage;
       intEndNum = intPageNum * intLineNumPage;

       DBPrpNewCarRecord dbPrpNewCarRecord = new DBPrpNewCarRecord();
       initArr();
       strSqlStatement.append(" SELECT * FROM ( ");
       strSqlStatement.append("Select RowNum As LineNum,T.* From ( ");
       strSqlStatement.append("Select PrpNewCarRecord.* From PrpNewCarRecord Where ");
       strSqlStatement.append(iWherePart);
       strSqlStatement.append(") T Where RowNum<=");
       strSqlStatement.append(intEndNum);
       strSqlStatement.append(") Where LineNum>");
       strSqlStatement.append(intStartNum);
       schemas = dbPrpNewCarRecord.findByConditions(dbpool,strSqlStatement.toString());
     }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
