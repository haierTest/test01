package com.sp.indiv.ci.blsvr;

import java.util.Vector;

import com.sp.indiv.ci.schema.CIInsureQuerySchema;
import com.sp.utility.database.DbPool;

public class BLCIInsureQuery 
{
	private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCIInsureQuery(){
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
     *����һ��CIInsureQuerySchema��¼
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
     *�õ�һ��CIInsureQuerySchema��¼
     *@param index �±�
     *@return һ��CIInsureQuerySchema����
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
     *ɾ��һ��CIInsureQuerySchema��¼
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
}
