package com.sp.prpall.blsvr.misc;

import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.visa.dto.custom.VisaInfoDto;
import com.sp.prpall.dbsvr.misc.DBPrpActivedCardHis;
import com.sp.prpall.schema.PrpActivedCardHisSchema;

/**
 * 定义PrpActivedCardHis的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-08-04</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 */
public class BLPrpActivedCardHis{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpActivedCardHis(){
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
     *增加一条PrpActivedCardHisSchema记录
     *@param iPrpActivedCardHisSchema PrpActivedCardHisSchema
     *@throws Exception
     */
    public void setArr(PrpActivedCardHisSchema iPrpActivedCardHisSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpActivedCardHisSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpActivedCardHisSchema记录
     *@param index 下标
     *@return 一个PrpActivedCardHisSchema对象
     *@throws Exception
     */
    public PrpActivedCardHisSchema getArr(int index) throws Exception
    {
     PrpActivedCardHisSchema prpActivedCardHisSchema = null;
       try
       {
        prpActivedCardHisSchema = (PrpActivedCardHisSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpActivedCardHisSchema;
     }
    /**
     *删除一条PrpActivedCardHisSchema记录
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
      DBPrpActivedCardHis dbPrpActivedCardHis = new DBPrpActivedCardHis();
      if (iLimitCount > 0 && dbPrpActivedCardHis.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpActivedCardHis.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpActivedCardHis WHERE " + iWherePart; 
        schemas = dbPrpActivedCardHis.findByConditions(strSqlStatement);
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
      DBPrpActivedCardHis dbPrpActivedCardHis = new DBPrpActivedCardHis();
      if (iLimitCount > 0 && dbPrpActivedCardHis.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpActivedCardHis.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpActivedCardHis WHERE " + iWherePart; 
        schemas = dbPrpActivedCardHis.findByConditions(dbpool,strSqlStatement);
      }
    }
    
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@author echaonan 20100804
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
        DBPrpActivedCardHis dbPrpActivedCardHis = new DBPrpActivedCardHis();
        if (iLimitCount > 0 && dbPrpActivedCardHis.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpActivedCardHis.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpActivedCardHis WHERE " + iWherePart;
            schemas = dbPrpActivedCardHis.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    } 
    
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpActivedCardHis dbPrpActivedCardHis = new DBPrpActivedCardHis();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpActivedCardHis.setSchema((PrpActivedCardHisSchema)schemas.get(i));
      dbPrpActivedCardHis.insert(dbpool);
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
     *@param iVisaCode VisaCode
     *@return 无
     */
    public void cancel(DbPool dbpool,String iVisaCode) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpActivedCardHis WHERE VisaCode= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iVisaCode);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iVisaCode VisaCode
     *@return 无
     */
    public void cancel(String iVisaCode ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iVisaCode);
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
     * 带dbpool根据VisaCode获取数据
     *@param iVisaCode VisaCode
     *@return 无
     */
    public void getData(String iVisaCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iVisaCode);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据VisaCode获取数据
     *@param dbpool 连接池
     *@param iVisaCode VisaCode
     *@return 无
     */
    public void getData(DbPool dbpool,String iVisaCode) throws Exception
    {
      String strWherePart = "";
      strWherePart = "VisaCode= '" + iVisaCode + "'";
      query(dbpool,strWherePart,0);
    }
    
    /**
     * 重新构建需校验的流水号集合，历史已激活数据不进行校验
     * @param listVisaInfo
     * @return 去掉历史已激活数据后集合
     * @throws Exception
     */
    public List dealHisVisa(List listVisaInfo) throws Exception
    {
    	List listReturn = new ArrayList();
    	VisaInfoDto visaInfoDto = new VisaInfoDto();
    	
    	String iWherePart = "";
    	ArrayList listWhereValue = null;
    	
    	iWherePart = " VISACODE = ? AND BILLSTARTNO = ? AND BILLENDNO = ?";
    	DbPool dbpool = new DbPool();
        try{
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        	for(int i=0;i<listVisaInfo.size();i++)
    	    {   
    	    	listWhereValue = new ArrayList();
    		    visaInfoDto = (VisaInfoDto)listVisaInfo.get(i);
    		    listWhereValue.add(visaInfoDto.getVisaCode());
    		    listWhereValue.add(visaInfoDto.getStartSerialNo());
    		    listWhereValue.add(visaInfoDto.getEndSerialNo());
    	        this.query(dbpool, iWherePart, listWhereValue, 0);
    		    if(this.getSize() > 0)
    		    {
    			    continue;
    		    }
    		    listReturn.add(visaInfoDto);
    	    }
        }catch(Exception e)
        {
        	e.printStackTrace();
        	throw e;
        }finally
        {
          dbpool.close();
        }
		return listReturn;
    }
    
    public void delete(DbPool dbpool,String strSQL ,ArrayList iWhereValue) throws Exception{
    if(iWhereValue != null)
    {
      dbpool.prepareInnerStatement(strSQL);
      for(int i=0;i<iWhereValue.size();i++)
        {
          dbpool.setString(i+1,(iWhereValue.get(i)).toString());
        }
      	dbpool.executePreparedQuery();
    }else
    {
         dbpool.delete(strSQL);
    }      
   }  
      
    /**
     * 不带dbpool的删除方法
     *@param iVisaCode VisaCode
     *@return 无
     */
    public void detele(String strSQL ,ArrayList iWhereValue) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        strSQL = "DELETE FROM PrpActivedCardHis WHERE " + strSQL ;
        delete(dbpool,strSQL , iWhereValue);
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
    public void query(String strSQL ,ArrayList iWhereValue) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        query(dbpool,strSQL,iWhereValue, 0);
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
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
