package com.sp.indiv.ci.blsvr;

import java.util.Vector;

import com.sp.indiv.ci.schema.CIInsureQuerySchema;
import com.sp.utility.database.DbPool;

public class BLCIInsureQuery 
{
	private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLCIInsureQuery(){
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
     *增加一条CIInsureQuerySchema记录
     *@param iCIInsureQuerySchema CIInsureQuerySchema
     *@throws Exception
     */
    public void setArr(CIInsureQuerySchema iCIInsureQuerySchema) 
    	throws Exception
    {
       try
       {
         schemas.add(iCIInsureQuerySchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CIInsureQuerySchema记录
     *@param index 下标
     *@return 一个CIInsureQuerySchema对象
     *@throws Exception
     */
    public CIInsureQuerySchema getArr(int index) throws Exception
    {
    	CIInsureQuerySchema  cIInsureQuerySchema = null;
       try
       {
    	   cIInsureQuerySchema = (CIInsureQuerySchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsureQuerySchema;
     }
    /**
     *删除一条CIInsureQuerySchema记录
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
}
