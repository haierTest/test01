package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCITaxDataCompareQG;
import com.sp.indiv.ci.schema.CITaxDataCompareQGSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 定义CITaxDataCompareQG的BL类
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-06-16</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLCITaxDataCompareQG{
    private Vector schemas = new Vector();
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    /**
     * 构造函数
     */       
    public BLCITaxDataCompareQG(){
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
     *增加一条CITaxDataCompareQGSchema记录
     *@param iCITaxDataCompareQGSchema CITaxDataCompareQGSchema
     *@throws Exception
     */
    public void setArr(CITaxDataCompareQGSchema iCITaxDataCompareQGSchema) throws Exception
    {
       try
       {
         schemas.add(iCITaxDataCompareQGSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CITaxDataCompareQGSchema记录
     *@param index 下标
     *@return 一个CITaxDataCompareQGSchema对象
     *@throws Exception
     */
    public CITaxDataCompareQGSchema getArr(int index) throws Exception
    {
     CITaxDataCompareQGSchema cITaxDataCompareQGSchema = null;
       try
       {
        cITaxDataCompareQGSchema = (CITaxDataCompareQGSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cITaxDataCompareQGSchema;
     }
    /**
     *删除一条CITaxDataCompareQGSchema记录
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
      DBCITaxDataCompareQG dbCITaxDataCompareQG = new DBCITaxDataCompareQG();
      if (iLimitCount > 0 && dbCITaxDataCompareQG.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCITaxDataCompareQG.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT TYPE,CONFIRMNO,TAXTOTALPAYIA,TAXTOTALPAYLT,DECLARESTATUSIA,DECLARESTATUSLT,TAXTOTALPAYIC,DECLARESTATUSIC,"
            +" ISAMENDMENT,EXTENDCHAR1,EXTENDCHAR2,to_char(STARTDATE,'YYYY-MM-DD') STARTDATE,to_char(ENDDATE,'YYYY-MM-DD') ENDDATE,COMPARENO"
            +" FROM CITaxDataCompareQG WHERE " + iWherePart; 
        schemas = dbCITaxDataCompareQG.findByConditions(strSqlStatement);
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
      DBCITaxDataCompareQG dbCITaxDataCompareQG = new DBCITaxDataCompareQG();
      if (iLimitCount > 0 && dbCITaxDataCompareQG.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCITaxDataCompareQG.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT TYPE,CONFIRMNO,TAXTOTALPAYIA,TAXTOTALPAYLT,DECLARESTATUSIA,DECLARESTATUSLT,TAXTOTALPAYIC,DECLARESTATUSIC,"
            +" ISAMENDMENT,EXTENDCHAR1,EXTENDCHAR2,to_char(STARTDATE,'YYYY-MM-DD') STARTDATE,to_char(ENDDATE,'YYYY-MM-DD') ENDDATE,COMPARENO"
            +" FROM CITaxDataCompareQG WHERE " + iWherePart; 
        schemas = dbCITaxDataCompareQG.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCITaxDataCompareQG dbCITaxDataCompareQG = new DBCITaxDataCompareQG();
      
      int i = 0;
      
      logger.info(schemas.size());
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbCITaxDataCompareQG.setSchema((CITaxDataCompareQGSchema)schemas.get(i));
      dbCITaxDataCompareQG.insert(dbpool);
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
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      
      try
      {
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
     *@param iCompareNo 对比号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iCompareNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM CITaxDataCompareQG WHERE CompareNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iCompareNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iCompareNo 对比号
     *@return 无
     */
    public void cancel(String iCompareNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iCompareNo);
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
     * 带dbpool根据iCompareNo获取数据
     *@param iCompareNo 对比号
     *@return 无
     */
    public void getData(String iCompareNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iCompareNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据iCompareNo获取数据
     *@param dbpool 连接池
     *@param iCompareNo 对比号
     *@return 无
     */
    public void getData(DbPool dbpool,String iCompareNo) throws Exception
    {
        
        
        
        
        String strWherePart = " CompareNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iCompareNo);
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
        DBCITaxDataCompareQG dbCITaxDataCompareQG = new DBCITaxDataCompareQG();
        if (iLimitCount > 0 && dbCITaxDataCompareQG.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCITaxDataCompareQG.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CITaxDataCompareQG WHERE " + iWherePart;
            schemas = dbCITaxDataCompareQG.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
