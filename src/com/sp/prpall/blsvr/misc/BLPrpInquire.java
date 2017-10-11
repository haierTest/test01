package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpInquire;
import com.sp.prpall.schema.PrpInquireSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义PrpInquire的BL类
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>@createdate 2005-05-18</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpInquire{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */
    public BLPrpInquire(){
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
     *增加一条PrpInquireSchema记录
     *@param iPrpInquireSchema PrpInquireSchema
     *@throws Exception
     */
    public void setArr(PrpInquireSchema iPrpInquireSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpInquireSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpInquireSchema记录
     *@param index 下标
     *@return 一个PrpInquireSchema对象
     *@throws Exception
     */
    public PrpInquireSchema getArr(int index) throws Exception
    {
     PrpInquireSchema prpInquireSchema = null;
       try
       {
        prpInquireSchema = (PrpInquireSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpInquireSchema;
     }
    /**
     *删除一条PrpInquireSchema记录
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
      DBPrpInquire dbPrpInquire = new DBPrpInquire();
      if (iLimitCount > 0 && dbPrpInquire.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpInquire.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpInquire WHERE " + iWherePart;
        schemas = dbPrpInquire.findByConditions(strSqlStatement);
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
      DBPrpInquire dbPrpInquire = new DBPrpInquire();
      if (iLimitCount > 0 && dbPrpInquire.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpInquire.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpInquire WHERE " + iWherePart;
        schemas = dbPrpInquire.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpInquire dbPrpInquire = new DBPrpInquire();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpInquire.setSchema((PrpInquireSchema)schemas.get(i));
      dbPrpInquire.insert(dbpool);
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
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param null null
     *@return 无
     */
    public void cancel(DbPool dbpool,String CertiNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpInquire WHERE CertiNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, CertiNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }

    /**
     * 不带dbpool的删除方法
     *@param null null
     *@return 无
     */
    public void cancel(String CertiNo ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,null);
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
     * 带dbpool根据null获取数据
     *@param null null
     *@return 无
     */
    public void getData(String CertiNo) throws Exception
    {
        DbPool dbpool = new DbPool();

        try {
  		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  		getData(dbpool, null);
  	} catch (Exception e) {
  		
  	}finally {      
  	dbpool.close();
      }

      }

    /**
     * 不带dbpool根据null获取数据
     *@param dbpool 连接池
     *@param null null
     *@return 无
     */
    public PrpInquireSchema Evaluate(PrpInquireSchema iPrpInquireSchema,String strPolicyNo) throws Exception
    {
    	PrpInquireSchema prpInquireSchema = new PrpInquireSchema();
    	 prpInquireSchema.setCertiNo(strPolicyNo);
    	 if(!iPrpInquireSchema.getAnswerContent().equals("")){
    	 prpInquireSchema.setAnswerContent(iPrpInquireSchema.getAnswerContent());
    	 }
    	 if(!iPrpInquireSchema.getColumnCode().equals("")){
    	 prpInquireSchema.setColumnCode(iPrpInquireSchema.getColumnCode());
    	 }
    	 if(!iPrpInquireSchema.getDetailno().equals("")){
    	 prpInquireSchema.setDetailno(iPrpInquireSchema.getDetailno());
    	 }
    	 if(!iPrpInquireSchema.getFlag().equals("")){
    	 prpInquireSchema.setFlag(iPrpInquireSchema.getFlag());
    	 }
    	 if(!iPrpInquireSchema.getNumeric().equals("")){
    	 prpInquireSchema.setNumeric(iPrpInquireSchema.getNumeric());
    	 }
    	 if(!iPrpInquireSchema.getQuestionContent().equals("")){
    	 prpInquireSchema.setQuestionContent(iPrpInquireSchema.getQuestionContent());
         }
    	 if(!iPrpInquireSchema.getRiskCode().equals("")){
    	 prpInquireSchema.setRiskCode(iPrpInquireSchema.getRiskCode());
         }
    	 if(!iPrpInquireSchema.getSerialno().equals("")){
    	 prpInquireSchema.setSerialno(iPrpInquireSchema.getSerialno());
         }

        return prpInquireSchema;
    }
    public void getData(DbPool dbpool,String CertiNo) throws Exception
    {
        
        
        
        
        String strWherePart = " CertiNo=? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(CertiNo);
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
      DBPrpInquire dbPrpInquire = new DBPrpInquire();
      if (iLimitCount > 0 && dbPrpInquire.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
      {
          throw new UserException(-98,-1003,"BLPrpInquire.query");
      }else
      {
          initArr();
          strSqlStatement = " SELECT * FROM PrpInquire WHERE " + iWherePart;
          schemas = dbPrpInquire.findByConditions(dbpool,strSqlStatement,iWhereValue);
      }
  }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
