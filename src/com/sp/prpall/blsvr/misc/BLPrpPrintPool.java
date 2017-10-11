package com.sp.prpall.blsvr.misc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.misc.DBPrpPrintPool;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;
import com.sp.prpall.schema.PrpPrintPoolSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;


/**
 * 定义PrpPrintPool的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-01-21</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4) 
 * @LastVersion: v1.4.1
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 */
public class BLPrpPrintPool{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpPrintPool(){
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
     *增加一条PrpPrintPoolSchema记录
     *@param iPrpPrintPoolSchema PrpPrintPoolSchema
     *@throws Exception
     */
    public void setArr(PrpPrintPoolSchema iPrpPrintPoolSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpPrintPoolSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpPrintPoolSchema记录
     *@param index 下标
     *@return 一个PrpPrintPoolSchema对象
     *@throws Exception
     */
    public PrpPrintPoolSchema getArr(int index) throws Exception
    {
      PrpPrintPoolSchema prpPrintPoolSchema = null;
       try
       {
         prpPrintPoolSchema = (PrpPrintPoolSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpPrintPoolSchema;
     }
    /**
     *删除一条PrpPrintPoolSchema记录
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
      DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
      if (iLimitCount > 0 && dbPrpPrintPool.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPrintPool.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpPrintPool WHERE " + iWherePart; 
        schemas = dbPrpPrintPool.findByConditions(strSqlStatement);
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
      DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
      if (iLimitCount > 0 && dbPrpPrintPool.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPrintPool.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpPrintPool WHERE " + iWherePart; 
        schemas = dbPrpPrintPool.findByConditions(dbpool,strSqlStatement);
      }
    }
    
    /**
     * 根据查询条件，查询SysConfig.CONST_SUNQUERYDATASOURCE数据源对应的数据.并将这组记录赋给schemas对象
     * @param iWherePart 查询条件
     * @param intPageNum 页码
     * @param intLineNumPage 每页条数
     * @return 无
     * @throws Exception
     */
    public void query(String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
    {
      try {
        this.query(iWherePart,intPageNum,intLineNumPage,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
      }
      catch (Exception e)
      {
        throw e;
      }
    }
    
    /**
     *按照查询条件得到一组记录数，用于翻页查询，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(不包括排序字句)
     *@param intPageNum 页码
     *@param intLineNumPage 每页条数
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@return 无
     *@throws Exception
     */
    public void query(String iWherePart,int intPageNum,int intLineNumPage,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      int intStartNum = 0;
      int intEndNum = 0;
      
      intStartNum = (intPageNum - 1) * intLineNumPage;
      
      intEndNum = intPageNum * intLineNumPage;

      DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
      if (iLimitCount > 0 && dbPrpPrintPool.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPrintPool.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM ( " +
         "Select RowNum As LineNum,T.* From ( " +
         "Select * From PrpPrintPool Where " + iWherePart +
         ") T Where RowNum<=" + intEndNum + ") Where LineNum>" +
         intStartNum ;
        schemas = dbPrpPrintPool.findByConditions(strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
      
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpPrintPool.setSchema((PrpPrintPoolSchema)schemas.get(i));
        dbPrpPrintPool.insert(dbpool);
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
     *@param iBusinessNo 业务号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iBusinessNo) throws Exception
    {
      



    	String strSqlStatement = " DELETE FROM PrpPrintPool WHERE BusinessNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iBusinessNo);
	    dbpool.executePreparedUpdate();
	    dbpool.closePreparedStatement();
        
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iBusinessNo 业务号
     *@return 无
     */
    public void cancel(String iBusinessNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iBusinessNo);
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
     * 带dbpool根据业务号获取数据
     *@param iBusinessNo 业务号
     *@return 无
     */
    public void getData(String iBusinessNo) throws Exception
    { 
      DbPool dbpool = new DbPool();
      try
      {
			
			String PrintDBPool = "0";
			try{
				PrintDBPool = SysConfig.getProperty("SunPrintQueryDBPool");
			}catch(Exception e){
				PrintDBPool="0";
			}
			if("0".equals(PrintDBPool)){
				dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			}else{
				dbpool.open(PrintDBPool);
			}
			
        getData(dbpool,iBusinessNo);
      }catch( Exception e ){  
      }finally
      {
        dbpool.close();
      }
      
    }
      
    /**
     * 不带dbpool根据业务号获取数据
     *@param dbpool 连接池
     *@param iBusinessNo 业务号
     *@return 无
     */
    public void getData(DbPool dbpool,String iBusinessNo) throws Exception
    {
        
        
        
        
        String strWherePart = " BusinessNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iBusinessNo);
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
        DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
        if (iLimitCount > 0 && dbPrpPrintPool.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpPrintPool.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpPrintPool WHERE " + iWherePart;
            schemas = dbPrpPrintPool.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * 批量授权
     * @param strBusinessNos  授权业务号数组
     * @return 
     * @throws Exception
     * @throws SQLException
     * @throws NamingException
     */
    public String batchAuthorize(String[] strBusinessNos) throws
        Exception,SQLException,NamingException{
        String strMessage = "";
        DbPool dbpool = new DbPool();
        
        
        try{
        	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        	dbpool.beginTransaction();
        	strMessage  = batchAuthorize(dbpool, strBusinessNos);
        	dbpool.commitTransaction();
        }catch(SQLException sqlException){
            dbpool.rollbackTransaction();
        	throw sqlException;
        }catch(NamingException namingException){
            dbpool.rollbackTransaction();
        	throw namingException;
        }finally{
        	dbpool.close();
        }
        
        return strMessage;
    }
    /**
     * 批量授权
     * @param dbpool
     * @param strBusinessNos  授权业务号数组
     * @return
     * @throws UserException
     * @throws Exception
     */
    public String batchAuthorize(DbPool dbpool, String[] strBusinessNos) throws UserException,Exception
    {
    	StringBuffer bufMessage = new StringBuffer();
    	DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
    	DBPrpCmain dbPrpCmain = new DBPrpCmain();
    	DBPrpPhead dbPrpPhead = new DBPrpPhead();
    	
    	String strBusinessNo = null;
    	String strBizType = null;
    	String strGroupFlag = null;
    	int intReturnPool = 0;
    	int intReturn = 0;
    	
    	for(int i=0;i<strBusinessNos.length;i++)
        {
    		strBusinessNo = strBusinessNos[i];
    		if(!"none".equals(strBusinessNo))
    		{
    			intReturnPool = dbPrpPrintPool.getInfo(dbpool, strBusinessNo);
        		
                if(intReturnPool == 100)
                {
                	bufMessage.append("无此业务号:");
                	bufMessage.append(strBusinessNo);
                	bufMessage.append("<br>");
                	continue;
                }
                strBizType = dbPrpPrintPool.getPrintType();
                if("P".equals(strBizType))
                {
                  intReturn = dbPrpCmain.getInfo(dbpool, strBusinessNo);
                  if(intReturn == 100)
                  {
                	  bufMessage.append("XX号:");
                	  bufMessage.append(strBusinessNo);
                	  bufMessage.append("，无具体业务数据！");
                	  bufMessage.append("<br>");
                	  continue;
                	  
                  }
                }else
                {
                  intReturn = dbPrpPhead.getInfo(dbpool, strBusinessNo);
                  if(intReturn == 100)
                  {
                	  bufMessage.append("批单号:");
                	  bufMessage.append(strBusinessNo);
                	  bufMessage.append("，无具体业务数据！");
                	  bufMessage.append("<br>");
                	  continue;
                  }
                }
                strGroupFlag = dbPrpPrintPool.getGroupFlag();
                if(!"1".equals(strGroupFlag))
                {
                	bufMessage.append("业务号:");
              	    bufMessage.append(strBusinessNo);
              	    bufMessage.append("为非集中打印业务，不能进行授权操作！");
              	    bufMessage.append("<br>");
              	    continue;
                }
                
                
                dbPrpPrintPool.setGroupFlag("0");
                dbPrpPrintPool.update(dbpool);
            }
        }
    	return bufMessage.toString();
    }
    
    /**
     * @description 批量授权方法
     * @author gaohaifeng 20110402
     * @param strBusinessNos  授权业务号数组
     * @param strAuthorizeCode  授权人代码
     * @return
     * @throws UserException
     * @throws Exception
     */
    public String batchAuthorize(String[] strBusinessNos,String strAuthorizeCode) throws
        Exception,SQLException,NamingException{
        String strMessage = "";
        DbPool dbpool = new DbPool();
        
        try{
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            dbpool.beginTransaction();
            strMessage  = batchAuthorize(dbpool, strBusinessNos, strAuthorizeCode);
            dbpool.commitTransaction();
        }catch(SQLException sqlException){
            dbpool.rollbackTransaction();
            throw sqlException;
        }catch(NamingException namingException){
            dbpool.rollbackTransaction();
            throw namingException;
        }finally{
            dbpool.close();
        }
        return strMessage;
    }
    
    /**
     * @description 批量授权方法
     * @author gaohaifeng 20110402
     * @param dbpool
     * @param strBusinessNos  授权业务号数组
     * @param strAuthorizeCode  授权人代码
     * @return
     * @throws UserException
     * @throws Exception
     */
    public String batchAuthorize(DbPool dbpool, String[] strBusinessNos,String strAuthorizeCode) throws UserException,Exception
    {
        StringBuffer bufMessage = new StringBuffer();
        DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
        DBPrpCmain dbPrpCmain = new DBPrpCmain();
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        ChgDate chgDate = new ChgDate();
        String strBusinessNo = null;
        String strBizType = null;
        String strGroupFlag = null;
        int intReturnPool = 0;
        int intReturn = 0;
        
        for(int i=0;i<strBusinessNos.length;i++)
        {
            strBusinessNo = strBusinessNos[i];
            if(!"none".equals(strBusinessNo))
            {
                intReturnPool = dbPrpPrintPool.getInfo(dbpool, strBusinessNo);
                
                if(intReturnPool == 100)
                {
                    bufMessage.append("无此业务号:");
                    bufMessage.append(strBusinessNo);
                    bufMessage.append("<br>");
                    continue;
                }
                strBizType = dbPrpPrintPool.getPrintType();
                if("P".equals(strBizType))
                {
                  intReturn = dbPrpCmain.getInfo(dbpool, strBusinessNo);
                  if(intReturn == 100)
                  {
                      bufMessage.append("XX号:");
                      bufMessage.append(strBusinessNo);
                      bufMessage.append("，无具体业务数据！");
                      bufMessage.append("<br>");
                      continue;
                      
                  }
                }else
                {
                  intReturn = dbPrpPhead.getInfo(dbpool, strBusinessNo);
                  if(intReturn == 100)
                  {
                      bufMessage.append("批单号:");
                      bufMessage.append(strBusinessNo);
                      bufMessage.append("，无具体业务数据！");
                      bufMessage.append("<br>");
                      continue;
                  }
                }
                strGroupFlag = dbPrpPrintPool.getGroupFlag();
                if(!"1".equals(strGroupFlag))
                {
                    bufMessage.append("业务号:");
                    bufMessage.append(strBusinessNo);
                    bufMessage.append("为非集中打印业务，不能进行授权操作！");
                    bufMessage.append("<br>");
                    continue;
                }
                
                
                dbPrpPrintPool.setGroupFlag("0");
                dbPrpPrintPool.setAuthorizeCode(strAuthorizeCode);
                dbPrpPrintPool.setAuthorizeDate(chgDate.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
                dbPrpPrintPool.update(dbpool);
            }
        }
        return bufMessage.toString();
    }
    
    
    /**
     * 根据查询条件，查询DDCCDATASOURCE数据源对应的数据.并将这组记录赋给schemas对象
     * @param iWherePart      查询条件
     * @param arrWhereValue   查询条件各属性值
     * @param intPageNum      页码
     * @param intLineNumPage  每页条数
     * @return 无
     * @throws Exception
     */
    public void queryWithBindVariable(String iWherePart,ArrayList arrWhereValue, int intPageNum,int intLineNumPage)
            throws UserException,Exception
    {
    	DbPool dbpool = new DbPool();
    	
        try{
            
            
                
            
      		
      		String PrintDBPool = "0";
      		try{
      			PrintDBPool = SysConfig.getProperty("SunPrintQueryDBPool");
      		}catch(Exception e){
      			PrintDBPool="0";
      		}
      		if("0".equals(PrintDBPool)){
      			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      		}else{
      			dbpool.open(PrintDBPool);
      		}
      		
            
            
        	queryWithBindVariable(dbpool, iWherePart,arrWhereValue, intPageNum,intLineNumPage,
        			Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
        }catch(SQLException sqlException){
        	throw sqlException;
        }catch(NamingException namingException){
        	throw namingException;
        }finally{
        	dbpool.close();
        }
    }
    
    /**
     * 按照查询条件得到一组记录数，用于翻页查询，并将这组记录赋给schemas对象
     * @param strWherePart    查询条件(不包括排序字句)
     * @param arrWhereValue   查询条件各属性值
     * @param intPageNum      页码
     * @param intLineNumPage  每页条数
     * @param iLimitCount     记录数限制(iLimitCount=0: 无限制)
     * @throws UserException
     * @throws Exception
     */
    public void queryWithBindVariable(DbPool dbpool,String strWherePart,ArrayList arrWhereValue, int intPageNum,int intLineNumPage,
    		int iLimitCount) throws UserException,Exception
    {
    	StringBuffer bufSqlStatement = new StringBuffer();
        int intStartNum = 0;
        int intEndNum = 0;
        
        intStartNum = (intPageNum - 1) * intLineNumPage;
        
        intEndNum = intPageNum * intLineNumPage;

        DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
        if (iLimitCount > 0 && dbPrpPrintPool.getCount(dbpool,strWherePart,arrWhereValue) > iLimitCount)
        {
      	  throw new UserException(-98,-1003,"BLPrpPrintPool.query");
        }else
        {
        	initArr();
            bufSqlStatement.append(" SELECT * FROM ( ");
            bufSqlStatement.append("Select RowNum As LineNum,T.* From ( ");
            bufSqlStatement.append("Select * From PrpPrintPool Where ");
            bufSqlStatement.append(strWherePart);
            bufSqlStatement.append(") T Where RowNum<=");
            bufSqlStatement.append(intEndNum);
            bufSqlStatement.append(") Where LineNum>");
            bufSqlStatement.append(intStartNum);
            schemas = dbPrpPrintPool.findByConditions(dbpool, bufSqlStatement.toString(), arrWhereValue);
        }
    }
    
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
