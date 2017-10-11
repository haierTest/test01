package com.sp.prpall.blsvr.misc;

import java.util.Vector;

import org.apache.commons.logging.LogFactory;

import com.sp.prpall.dbsvr.misc.DBPrpmotorcadeCoef;
import com.sp.prpall.dbsvr.misc.DBPrpmotorcadeUnderitDetail;
import com.sp.prpall.schema.PrpmotorcadeCoefSchema;
import com.sp.prpall.schema.PrpmotorcadeUnderitDetailSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义PRPMOTORCADEUNDEWRITDETAIL的BL类
 * PRPMOTORCADEUNDEWRITDETAIL：团车核XXXXX因子详细信息表
 */
public class BLPrpmotorcadeUnderitDetail{
    private final org.apache.commons.logging.Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpmotorcadeUnderitDetail(){
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
     *增加一条PrpmotorcadeCoefSchema记录
     *@param iPrpmotorcadeCoefSchema PrpmotorcadeCoefSchema
     *@throws Exception
     */
    public void setArr(PrpmotorcadeUnderitDetailSchema iPrpmotorcadeUnderitDetailSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpmotorcadeUnderitDetailSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpmotorcadeCoefSchema记录
     *@param index 下标
     *@return 一个PrpmotorcadeCoefSchema对象
     *@throws Exception
     */
    public PrpmotorcadeUnderitDetailSchema getArr(int index) throws Exception
    {
	PrpmotorcadeUnderitDetailSchema prpmotorcadeUnderitDetailSchema = null;
       try
       {
	   prpmotorcadeUnderitDetailSchema = (PrpmotorcadeUnderitDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpmotorcadeUnderitDetailSchema;
     }
    /**
     *删除一条PrpmotorcadeCoefSchema记录
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
      DBPrpmotorcadeUnderitDetail dbPrpmotorcadeUnderitDetail = new DBPrpmotorcadeUnderitDetail();
      if (iLimitCount > 0 && dbPrpmotorcadeUnderitDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpmotorcadeUnderitDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PRPMOTORCADEUNDEWRITDETAIL WHERE " + iWherePart; 
        schemas = dbPrpmotorcadeUnderitDetail.findByConditions(strSqlStatement);
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
      DBPrpmotorcadeUnderitDetail dbPrpmotorcadeUnderitDetail = new DBPrpmotorcadeUnderitDetail();
      if (iLimitCount > 0 && dbPrpmotorcadeUnderitDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpmotorcadeUnderitDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PRPMOTORCADEUNDEWRITDETAIL WHERE " + iWherePart; 
        schemas = dbPrpmotorcadeUnderitDetail.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
	DBPrpmotorcadeUnderitDetail dbPrpmotorcadeUnderitDetail = new DBPrpmotorcadeUnderitDetail();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
	  dbPrpmotorcadeUnderitDetail.setSchema((PrpmotorcadeUnderitDetailSchema)schemas.get(i));
	  dbPrpmotorcadeUnderitDetail.insert(dbpool);
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
     *@param iSerialNo SERIALNO
     *@return 无
     */
    public void cancel(DbPool dbpool,String contractno) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PRPMOTORCADEUNDEWRITDETAIL WHERE contractno= '" + contractno + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iSerialNo SERIALNO
     *@return 无
     */
    public void cancel(String contractno) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,contractno);
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
     * 带dbpool根据SERIALNO获取数据
     *@param iSerialNo SERIALNO
     *@return 无
     */
    public void getData(String contractno,String motorcadetype,String riskfactorcode) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,contractno,motorcadetype,riskfactorcode);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据SERIALNO获取数据
     *@param dbpool 连接池
     *@param iSerialNo SERIALNO
     *@return 无
     */
    public void getData(DbPool dbpool,String contractno,String motorcadetype,String riskfactorcode) throws Exception
    {
      String strWherePart = "";
      strWherePart = "contractno= '" + contractno + "' and motorcadetype= '"+motorcadetype+"' and riskfactorcode= '"+riskfactorcode+"'";
      query(dbpool,strWherePart,0);
    }
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
    
    /**
     *带dbpool的update方法
     *@param 无
     *@return 无
     */
	public void update(DbPool dbpool) throws Exception{
	    DBPrpmotorcadeUnderitDetail dbPrpmotorcadeUnderitDetail = new DBPrpmotorcadeUnderitDetail();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
		    dbPrpmotorcadeUnderitDetail.setSchema((PrpmotorcadeUnderitDetailSchema)schemas.get(i));
		    dbPrpmotorcadeUnderitDetail.update(dbpool);
	    }
	}
	
   /**
    *不带dbpool的更新方法
    *@param 无
    *@return 无
    */
	public void update() throws Exception{
		DbPool dbpool = new DbPool();
		try{
		  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	      dbpool.beginTransaction();
	      update(dbpool);
	      dbpool.commitTransaction();
	    }catch (Exception e){
	      dbpool.rollbackTransaction();
	      throw e;
	    }finally{
	      dbpool.close();
	    }
	}
	/**
	     *带dbpool的update方法
	     *@param 无
	     *@return 无
	     */
		public void updateByContractNo(DbPool dbpool) throws Exception{
		    DBPrpmotorcadeUnderitDetail dbPrpmotorcadeUnderitDetail = new DBPrpmotorcadeUnderitDetail();
			int i = 0;
			for(i = 0; i< schemas.size(); i++){
			    dbPrpmotorcadeUnderitDetail.setSchema((PrpmotorcadeUnderitDetailSchema)schemas.get(i));
			    dbPrpmotorcadeUnderitDetail.updateByContractNo(dbpool);
		    }
		}
	
}
