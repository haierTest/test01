package com.sp.indiv.ci.blsvr;

import java.util.Vector;
import com.sp.indiv.ci.dbsvr.*;
import com.sp.indiv.ci.schema.*;
import com.sp.utility.database.*;
import com.sp.prpall.blsvr.cb.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BLCIInsureCompanyCompare 
{
	private Vector schemas = new Vector();
	Log logger = LogFactory.getLog(getClass());
    /**
     * 构造函数
     */       
    public BLCIInsureCompanyCompare(){
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
     *增加一条CIInsureCompanyCompare记录
     *@param iCIInsureCompanyCompare CIInsureCompanyCompare
     *@throws Exception
     */
    public void setArr(CIInsureCompanyCompareSchema iCIInsureCompanyCompareSchema) 
    	throws Exception
    {
       try
       {
         schemas.add(iCIInsureCompanyCompareSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条CIInsureCompanyCompare记录
     *@param index 下标
     *@return 一个CIInsureCompanyCompare对象
     *@throws Exception
     */
    public CIInsureCompanyCompareSchema getArr(int index) throws Exception
    {
    	CIInsureCompanyCompareSchema cIInsureCompanyCompareSchema = null;
       try
       {
        cIInsureCompanyCompareSchema = (CIInsureCompanyCompareSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsureCompanyCompareSchema;
     }
    /**
     *删除一条CIInsureCompanyCompare记录
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
     * 获取CIInsureCompanyCompare表当前操作日期的最大SerialNo
     * @param dbPool
     * @param strCompareDate
     * @return
     * @throws Exception
     */
    public int getMaxSerialNo(DbPool dbPool, String strOperateDate)
    	throws Exception
    {
    	DBCIInsureCompanyCompare dbCIInsureCompanyCompare = new DBCIInsureCompanyCompare();
    	int iMaxSerialNo = 0;
    	String sqlSQL = " SELECT MAX(SERIALNO) SERIALNO FROM CIINSURECOMPANYCOMPARE " 
    				  + " WHERE OPERATEDATE >= TO_DATE('" + (strOperateDate + " 00:00:00") + "', 'YYYY-MM-DD HH24:MI:SS')"
    				  + "   AND OPERATEDATE <= TO_DATE('" + (strOperateDate + " 23:59:59") + "', 'YYYY-MM-DD HH24:MI:SS')";
    	
    	logger.info("XXXXX交强XXXXX平台数据比对-获取CIInsureCompanyCompare表当前操作日期的最大SerialNo-SQL: " + sqlSQL);
    	
    	iMaxSerialNo = dbCIInsureCompanyCompare.getMaxSerialNo(dbPool, sqlSQL);
    	return iMaxSerialNo;
    }
    
    /**
     * 获取b_claim_ebao_compare表当前操作日期的最大SerialNo
     * @param dbPool
     * @param strCompareDate
     * @return
     * @throws Exception
     */
    public int getMaxSerialNoClaim(DbPool dbPool, String strOperateDate)
    	throws Exception
    {
    	DBCIInsureCompanyCompare dbCIInsureCompanyCompare = new DBCIInsureCompanyCompare();
    	int iMaxSerialNoClaim = 0;
    	String sqlSQL = " SELECT MAX(SERIALNO) SERIALNO FROM b_claim_ebao_compare " 
    					+ " WHERE INSERTTIME >= TO_DATE('" + (strOperateDate + " 00:00:00") + "', 'YYYY-MM-DD HH24:MI:SS')"
    					+ "   AND INSERTTIME <= TO_DATE('" + (strOperateDate + " 23:59:59") + "', 'YYYY-MM-DD HH24:MI:SS')";
    	
    	logger.info("XXXXX交强XXXXX平台数据比对-获取b_claim_ebao_compare表当前操作日期的最大SerialNo-SQL: " + sqlSQL);
    	
    	iMaxSerialNoClaim = dbCIInsureCompanyCompare.getMaxSerialNo(dbPool, sqlSQL);
    	return iMaxSerialNoClaim;
    }
    
    /**
     * 根据比对日期将业务表XX数据导到CIInsureCompanyCompare表中
     * @param dbPool
     * @param strCompareDate	比对日期
     * @param strCurrentDate	当前日期
     * @param iMaxSerialNo		当前最大序列号
     * @throws Exception
     */
    public void setCompanyComparePolicy(DbPool dbPool, 
    							  		String iCompareDate, 
    							  		String iCurrentDate, 
    							  		int iCurrentMaxSerialNo,
    							  		String iComCode)
    	throws Exception
    {
    	DBCIInsureCompanyCompare dbCIInsureCompanyCompare = new DBCIInsureCompanyCompare();
    	String sqlSQL = "INSERT INTO CIINSURECOMPANYCOMPARE(COMCODE, COMPAREDATE, POLICYNO, SERIALNO, PROPOSALNO, " 
    					+ "DEMANDNO, VALIDNO, FLAG, UNDERWRITEFLAG, OTHFLAG, UNDERWRITEENDDATE, OPERATEDATE) " 
    					+ " SELECT A.COMCODE, DATE'" + iCompareDate + "' COMPAREDATE, A.POLICYNO, " 
    					+          (iCurrentMaxSerialNo) + " SERIALNO, A.PROPOSALNO, B.DEMANDNO, " 
    					+ "        B.VALIDNO, B.FLAG, A.UNDERWRITEFLAG, A.OTHFLAG, A.UNDERWRITEENDDATE, TO_DATE('" + iCurrentDate + "', 'YYYY:MM:DD:HH24:MI:SS') OPERATEDATE "
    					+ "  FROM PRPCMAIN A, CIINSUREVALID B "
    					+ " WHERE A.RISKCODE = '0507' "
    				    + "   AND A.POLICYNO = B.POLICYNO(+) "
    					+ "   AND A.UNDERWRITEENDDATE = DATE'" + iCompareDate + "' " 
    					+ "   AND SUBSTR(A.COMCODE, 0, 2) = '" + iComCode.substring(0, 2) + "'";
    	
    	
    	logger.info("XXXXX交强XXXXX平台数据比对向CIInsureCompanyCOmpare表插入XX部分的数据SQL： " + sqlSQL);
    	
    	dbCIInsureCompanyCompare.update(dbPool, sqlSQL);
    }
    
    /**
     * 根据比对日期将业务表批改数据导到CIInsureCompanyCompare表中
     * @param dbPool
     * @param strCompareDate	比对日期
     * @param strCurrentDate	当前日期
     * @param iMaxSerialNo		当前最大序列号
     * @throws Exception
     */
    public void setCompanyCompareEndorse(DbPool dbPool, 
    							  		String iCompareDate, 
    							  		String iCurrentDate, 
    							  		int iCurrentMaxSerialNo,
    							  		String iComCode)
    	throws Exception
    {
    	DBCIInsureCompanyCompare dbCIInsureCompanyCompare = new DBCIInsureCompanyCompare();
    	String sqlSQL = "INSERT INTO CIINSURECOMPANYCOMPARE(COMCODE, COMPAREDATE, POLICYNO, SERIALNO, PROPOSALNO, " 
    					+ "DEMANDNO, VALIDNO, FLAG, UNDERWRITEFLAG, OTHFLAG, UNDERWRITEENDDATE, OPERATEDATE) " 
    					+ " SELECT A.COMCODE, DATE'" + iCompareDate + "' COMPAREDATE, A.POLICYNO, " 
    					+          (iCurrentMaxSerialNo) + " SERIALNO, A.PROPOSALNO, B.DEMANDNO, " 
    					+ "        B.VALIDNO, B.FLAG, A.UNDERWRITEFLAG, A.OTHFLAG, C.UNDERWRITEENDDATE, TO_DATE('" + iCurrentDate + "', 'YYYY:MM:DD:HH24:MI:SS') OPERATEDATE "
    					+ "  FROM PRPCMAIN A, CIINSUREVALID B, PRPPHEAD C  "
    					+ " WHERE A.RISKCODE = '0507' "
    				    + "   AND A.POLICYNO = B.POLICYNO(+) "
    				    + "   AND A.POLICYNO = C.POLICYNO "
    					+ "   AND C.UNDERWRITEENDDATE = DATE'" + iCompareDate + "' "
    					+ "   AND C.UNDERWRITEFLAG IN('1', '3') "	
    					+ "   AND C.ENDORTYPE IN('19', '21') " 
    					+ "   AND SUBSTR(A.COMCODE, 0, 2) = '" + iComCode.substring(0, 2) + "'";
    	
    	
    	logger.info("XXXXX交强XXXXX平台数据比对向CIInsureCompanyCOmpare表插入批改处理的数据SQL： " + sqlSQL);
    	
    	dbCIInsureCompanyCompare.update(dbPool, sqlSQL);
    }
    
    /**
     *带dbpool的query方法
     *@param 无
     *@return 无
     */
    public Vector query(DbPool dbPool, String strSQL) throws Exception
    {
    	Vector vCompanyCompare = new Vector();
    	DBCIInsureCompanyCompare dbCIInsureCompanyCompare = new DBCIInsureCompanyCompare();
    	vCompanyCompare = dbCIInsureCompanyCompare.findByConditions(dbPool, strSQL);
    	return vCompanyCompare;
    }
}
