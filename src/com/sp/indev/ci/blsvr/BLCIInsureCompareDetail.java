package com.sp.indiv.ci.blsvr;

import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCICaseDemand;
import com.sp.indiv.ci.dbsvr.DBCIInsureCompanyCompare;
import com.sp.indiv.ci.dbsvr.DBCIInsureCompareDetail;
import com.sp.indiv.ci.schema.CIInsureCompareDetailSchema;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

public class BLCIInsureCompareDetail 
{
	private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCIInsureCompareDetail(){
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
     *增加一条CIInsureCompareDetailSchema记录
     *@param iCIInsureCompareDetailSchema CIInsureCompareDetailSchema
     *@throws Exception
     */
    public void setArr(CIInsureCompareDetailSchema iCIInsureCompareDetailSchema) 
    	throws Exception
    {
       try
       {
         schemas.add(iCIInsureCompareDetailSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CIInsureCompareSchema记录
     *@param index 下标
     *@return 一个CIInsureCompareSchema对象
     *@throws Exception
     */
    public CIInsureCompareDetailSchema getArr(int index) throws Exception
    {
    	CIInsureCompareDetailSchema cIInsureCompareDetailSchema = null;
       try
       {
    	   cIInsureCompareDetailSchema = (CIInsureCompareDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsureCompareDetailSchema;
     }
    /**
     *删除一条CIInsureCompareSchema记录
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
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception    
    {
    	DBCIInsureCompareDetail dbCIInsureCompareDetail = new DBCIInsureCompareDetail();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
    	  dbCIInsureCompareDetail.setSchema((CIInsureCompareDetailSchema)schemas.get(i));
    	  dbCIInsureCompareDetail.insert(dbpool);
      }
    }
    
    /**
     *带dbpool的query方法
     *@param 无
     *@return 无
     */
    public Vector query(DbPool dbPool, String strSQL) throws Exception
    {
    	Vector vCompareDetail = new Vector();
    	DBCIInsureCompareDetail dbCIInsureCompareDetail = new DBCIInsureCompareDetail();
    	vCompareDetail = dbCIInsureCompareDetail.findByConditions(dbPool, strSQL);
    	return vCompareDetail;
    }
}
