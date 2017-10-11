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
     * ���캯��
     */       
    public BLCIInsureCompareDetail(){
    }

    /**
     *��ʼ����¼
     *@param ��
     *@return ��
     *@throws Exception
     */
    public void initArr() throws Exception
    {
       schemas = new Vector();
     }
    
    /**
     *����һ��CIInsureCompareDetailSchema��¼
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
     *�õ�һ��CIInsureCompareSchema��¼
     *@param index �±�
     *@return һ��CIInsureCompareSchema����
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
     *ɾ��һ��CIInsureCompareSchema��¼
     *@param index �±�
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
     *�õ�schemas��¼��
     *@return schemas��¼��
     *@throws Exception
     */
    public int getSize() throws Exception
    {
        return this.schemas.size();
    }
    
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
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
     *��dbpool��query����
     *@param ��
     *@return ��
     */
    public Vector query(DbPool dbPool, String strSQL) throws Exception
    {
    	Vector vCompareDetail = new Vector();
    	DBCIInsureCompareDetail dbCIInsureCompareDetail = new DBCIInsureCompareDetail();
    	vCompareDetail = dbCIInsureCompareDetail.findByConditions(dbPool, strSQL);
    	return vCompareDetail;
    }
}
