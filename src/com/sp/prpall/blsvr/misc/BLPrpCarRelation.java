package com.sp.prpall.blsvr.misc;

import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.misc.DBPrpCarRelation;
import com.sp.prpall.schema.PrpCarRelationSchema;

/**
 * 定义PrpCarRelation-XXXXX关联出单表的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-11-07</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 */
public class BLPrpCarRelation{

    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpCarRelation(){
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
     *增加一条PrpCarRelationSchema记录
     *@param iPrpCarRelationSchema PrpCarRelationSchema
     *@throws Exception
     */
    public void setArr(PrpCarRelationSchema iPrpCarRelationSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpCarRelationSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpCarRelationSchema记录
     *@param index 下标
     *@return 一个PrpCarRelationSchema对象
     *@throws Exception
     */
    public PrpCarRelationSchema getArr(int index) throws Exception
    {
      PrpCarRelationSchema prpCarRelationSchema = null;
       try
       {
         prpCarRelationSchema = (PrpCarRelationSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpCarRelationSchema;
     }
    /**
     *删除一条PrpCarRelationSchema记录
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
      DBPrpCarRelation dbPrpCarRelation = new DBPrpCarRelation();
      if (iLimitCount > 0 && dbPrpCarRelation.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCarRelation.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCarRelation WHERE " + iWherePart; 
        schemas = dbPrpCarRelation.findByConditions(strSqlStatement);
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
      DBPrpCarRelation dbPrpCarRelation = new DBPrpCarRelation();
      if (iLimitCount > 0 && dbPrpCarRelation.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCarRelation.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCarRelation WHERE " + iWherePart; 
        schemas = dbPrpCarRelation.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpCarRelation dbPrpCarRelation = new DBPrpCarRelation();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpCarRelation.setSchema((PrpCarRelationSchema)schemas.get(i));
        dbPrpCarRelation.insert(dbpool);
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
     *@param iPolicyNo XX号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iOrderNo ,String iSerialNo ) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpCarRelation WHERE OrderNo= '" + iOrderNo + "' and SerialNo = '"+iSerialNo+"'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iPolicyNo XX号
     *@return 无
     */
    public void cancel( String iOrderNo ,String iSerialNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iOrderNo , iSerialNo);
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
     * 带dbpool根据XX号获取数据
     *@param iPolicyNo XX号
     *@return 无
     */
    public void getData( String iOrderNo ,String iSerialNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iOrderNo,iSerialNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据XX号获取数据
     *@param dbpool 连接池
     *@param iPolicyNo XX号
     *@return 无
     */
    public void getData(DbPool dbpool,String iOrderNo ,String iSerialNo ) throws Exception
    {
      String strWherePart = "";
      strWherePart = "OrderNo= '" + iOrderNo + "' and SerialNo = '"+iSerialNo+"'";
      query(dbpool,strWherePart,0);
    }
    
    /**
     * 不带dbpool根据XX号获取数据
     *@param dbpool 连接池
     *@param iPolicyNo XX号
     *@return 无
     */
    public void getDataByProposalNoBI(DbPool dbpool,String iProposalNo ) throws Exception
    {
      String strWherePart = "";
      strWherePart = " ProposalNo= '" + iProposalNo + "'";
      query(dbpool,strWherePart,0);
      if(schemas.size()>0){
	      PrpCarRelationSchema prpCarRelationSchema = (PrpCarRelationSchema)schemas.get(0);
	      getData(dbpool,prpCarRelationSchema.getOrderNo(),"3");
      }
    }
    /**
     * 不带dbpool根据XX号获取数据
     *@param dbpool 连接池
     *@param iPolicyNo XX号
     *@return 无
     */
    public void getDataByProposalNoBI(String iProposalNo ) throws Exception
    {
        DbPool dbpool = new DbPool();
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        getDataByProposalNoBI(dbpool,iProposalNo);
        dbpool.close();
    }
    /**
     * 检查是否属于关联意健XXXXX出单业务
     *@param iProductCode 产品编码
     *@return boolean
     */
    public boolean checkCarRelation(String iProductCode ) throws Exception
    {
    	boolean isCarRelation = false;
    	if(SysConfig.getProperty("CheckCarRelation").indexOf(","+iProductCode+",")>-1){
    		isCarRelation = true;
    	}
    	return isCarRelation;
    }
    
    /**
     * 获取PrpCarRelationSchema对象列表
     * 根据查询条件，查询SysConfig.CONST_SUNQUERYDATASOURCE数据源对应的数据.并将这组记录赋给schemas对象 不带dbpool
     * @author sizhijin 20120905
     * @param iWherePart 查询条件
     * @param intPageNum 页码
     * @param intLineNumPage 每页条数
     * @return 无
     * @throws Exception
     */
    public void queryHasItemCarInfo(String iWherePart, int intPageNum,int intLineNumPage) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConfig.CONST_SUNQUERYDATASOURCE);
        this.queryHasItemCarInfo(dbpool,iWherePart,intPageNum,intLineNumPage);
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
     * 获取PrpCarRelationSchema对象列表
     * 查询条件包括设备信息，需关联查询prpCarRelation及PrpCitemCar表 带dbpool
     * @author sizhijin 20120905
     * @param dbpool     连接池
     * @param iWherePart 查询条件
     * @param intPageNum 页码
     * @param intLineNumPage 每页条数
     * @return 无
     * @throws Exception
     */
     public void queryHasItemCarInfo(DbPool dbpool,String iWherePart, int intPageNum,int intLineNumPage
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

       DBPrpCarRelation dbPrpCarRelation = new DBPrpCarRelation();
       initArr();
       
       strSqlStatement.append(" SELECT * FROM ( ");
       strSqlStatement.append("Select RowNum As LineNum,C.* From ( ");
       strSqlStatement.append("Select PrpCarRelation.* From PrpCarRelation,PrpCitemCar Where ");
       strSqlStatement.append(iWherePart);
       strSqlStatement.append(") C Where RowNum<=");
       strSqlStatement.append(intEndNum);
       strSqlStatement.append(") Where LineNum>");
       strSqlStatement.append(intStartNum);
       schemas = dbPrpCarRelation.findByConditions(dbpool,strSqlStatement.toString());
     }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
