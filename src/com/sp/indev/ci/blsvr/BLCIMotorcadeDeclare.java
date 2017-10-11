package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.indiv.ci.dbsvr.DBCIMotorcadeDeclare;
import com.sp.indiv.ci.schema.CIMotorcadeDeclareSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义CIMotorcadeDeclare-团车申报接口表的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-03-19</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 *              5.getData方法新增try、catch、finally异常处理;
 */
public class BLCIMotorcadeDeclare{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCIMotorcadeDeclare(){
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
     *增加一条CIMotorcadeDeclareSchema记录
     *@param iCIMotorcadeDeclareSchema CIMotorcadeDeclareSchema
     *@throws Exception
     */
    public void setArr(CIMotorcadeDeclareSchema iCIMotorcadeDeclareSchema) throws Exception
    {
      try
      {
        schemas.add(iCIMotorcadeDeclareSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条CIMotorcadeDeclareSchema记录
     *@param index 下标
     *@return 一个CIMotorcadeDeclareSchema对象
     *@throws Exception
     */
    public CIMotorcadeDeclareSchema getArr(int index) throws Exception
    {
      CIMotorcadeDeclareSchema cIMotorcadeDeclareSchema = null;
       try
       {
         cIMotorcadeDeclareSchema = (CIMotorcadeDeclareSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIMotorcadeDeclareSchema;
     }
    /**
     *删除一条CIMotorcadeDeclareSchema记录
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
      DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
      if (iLimitCount > 0 && dbCIMotorcadeDeclare.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIMotorcadeDeclare.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIMotorcadeDeclare WHERE " + iWherePart; 
        schemas = dbCIMotorcadeDeclare.findByConditions(strSqlStatement);
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
      DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
      if (iLimitCount > 0 && dbCIMotorcadeDeclare.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIMotorcadeDeclare.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIMotorcadeDeclare WHERE " + iWherePart; 
        schemas = dbCIMotorcadeDeclare.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbCIMotorcadeDeclare.setSchema((CIMotorcadeDeclareSchema)schemas.get(i));
        dbCIMotorcadeDeclare.insert(dbpool);
      }
    }
    
    public void saveBySeq(DbPool dbpool) throws Exception
    {
      DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbCIMotorcadeDeclare.setSchema((CIMotorcadeDeclareSchema)schemas.get(i));
        dbCIMotorcadeDeclare.insertBySeq(dbpool);
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
        
        String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
          dbpool.open("platformNewDataSource");
        } else {
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        }
        
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
     *@param iSerialNo 序列号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iSerialNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM CIMotorcadeDeclare WHERE SerialNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iSerialNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iSerialNo 序列号
     *@return 无
     */
    public void cancel(String iSerialNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        
        String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
          dbpool.open("platformNewDataSource");
        } else {
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        }
        
        dbpool.beginTransaction();
        cancel(dbpool,iSerialNo);
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
     * 带dbpool根据序列号获取数据
     *@param iSerialNo 序列号
     *@return 无
     */
    public void getData(String iSerialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        
        String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
          dbpool.open("platformNewDataSource");
        } else {
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        }
        
        getData(dbpool,iSerialNo);
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
     * 不带dbpool根据序列号获取数据
     *@param dbpool 连接池
     *@param iSerialNo 序列号
     *@return 无
     */
    public void getData(DbPool dbpool,String iSerialNo) throws Exception
    {
        
        
        
        
        String strWherePart = " SerialNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iSerialNo);
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
        DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
        if (iLimitCount > 0 && dbCIMotorcadeDeclare.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIMotorcadeDeclare.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIMotorcadeDeclare WHERE " + iWherePart;
            schemas = dbCIMotorcadeDeclare.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
    
    /**
     * 根据下面的参数更新XX单号
     *@param iProposalNo XX单号
     *@param iContractNo 协议号
     *@param iRackno 车架号
     *@param iCarmark 车牌号
     *@param iEngineNo 发动机号
     *@return 无
     */
    public void updateProposalNo(String iProposalNo,String iContractNo,String iRackno,String iCarmark,String iEngineNo) throws Exception{
    	 DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
    	 dbCIMotorcadeDeclare.updateProposalNo(iProposalNo,iContractNo,iRackno,iCarmark,iEngineNo);
    }
    
    /**
     * 清空CIMotorcadeDeclare表的XX单号
     *@param iProposalNo XX单号
     *@return 无
     */
    public void cancelProposalNo(String iProposalNo) throws Exception{
    	DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
    	dbCIMotorcadeDeclare.cancelProposalNo(iProposalNo);
    }
}
