package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBIntfPosMain;
import com.sp.prpall.schema.IntfPosMainSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义IntfPosMain的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2009-11-23</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 */
public class BLIntfPosMain{
    private Vector schemas = new Vector();  
    /**
     * 构造函数
     */       
    public BLIntfPosMain(){
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
     *增加一条IntfPosMainSchema记录
     *@param iIntfPosMainSchema IntfPosMainSchema
     *@throws Exception
     */
    public void setArr(IntfPosMainSchema iIntfPosMainSchema) throws Exception
    {
       try
       {
         schemas.add(iIntfPosMainSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条IntfPosMainSchema记录
     *@param index 下标
     *@return 一个IntfPosMainSchema对象
     *@throws Exception
     */
    public IntfPosMainSchema getArr(int index) throws Exception
    {
     IntfPosMainSchema intfPosMainSchema = null;
       try
       {
        intfPosMainSchema = (IntfPosMainSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return intfPosMainSchema;
     }
    /**
     *删除一条IntfPosMainSchema记录
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
      DBIntfPosMain dbIntfPosMain = new DBIntfPosMain();
      if (iLimitCount > 0 && dbIntfPosMain.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntfPosMain.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntfPosMain WHERE " + iWherePart; 
        schemas = dbIntfPosMain.findByConditions(strSqlStatement);
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
      DBIntfPosMain dbIntfPosMain = new DBIntfPosMain();
      if (iLimitCount > 0 && dbIntfPosMain.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntfPosMain.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntfPosMain WHERE " + iWherePart; 
        schemas = dbIntfPosMain.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBIntfPosMain dbIntfPosMain = new DBIntfPosMain();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbIntfPosMain.setSchema((IntfPosMainSchema)schemas.get(i));
      dbIntfPosMain.insert(dbpool);
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
        dbpool.open(SysConst.getProperty("POSDATASOURCE"));
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
     *@param ipolicyNo policyNo
     *@return 无
     */
    public void cancel(DbPool dbpool,String ipolicyNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM IntfPosMain WHERE policyNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, ipolicyNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param ipolicyNo policyNo
     *@return 无
     */
    public void cancel(String ipolicyNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("POSDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,ipolicyNo);
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
     * 带dbpool根据policyNo获取数据
     *@param ipolicyNo policyNo
     *@return 无
     */
    public void getData(String ipolicyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("POSDATASOURCE"));
      getData(dbpool,ipolicyNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据policyNo获取数据
     *@param dbpool 连接池
     *@param ipolicyNo policyNo
     *@return 无
     */
    public void getData(DbPool dbpool,String ipolicyNo) throws Exception
    {
        
        
        
        
        String strWherePart = " policyNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(ipolicyNo);
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
        DBIntfPosMain dbIntfPosMain = new DBIntfPosMain();
        if (iLimitCount > 0 && dbIntfPosMain.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLIntfPosMain.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM IntfPosMain WHERE " + iWherePart;
            schemas = dbIntfPosMain.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
