package com.sp.indiv.ci.blsvr;

import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBCIInsureCompare;
import com.sp.indiv.ci.dbsvr.DBCIInsureDemand;
import com.sp.indiv.ci.schema.CIInsureCompareSchema;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.utility.database.DbPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BLCIInsureCompare 
{
	private Vector schemas = new Vector();
	Log logger = LogFactory.getLog(getClass());
    /**
     * 构造函数
     */       
    public BLCIInsureCompare(){
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
     *增加一条CIInsureCompareSchema记录
     *@param iCIInsureCompareSchema CIInsureCompareSchema
     *@throws Exception
     */
    public void setArr(CIInsureCompareSchema iCIInsureCompareSchema) 
    	throws Exception
    {
       try
       {
         schemas.add(iCIInsureCompareSchema);
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
    public CIInsureCompareSchema getArr(int index) throws Exception
    {
    	CIInsureCompareSchema cIInsureCompareSchema = null;
       try
       {
        cIInsureCompareSchema = (CIInsureCompareSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIInsureCompareSchema;
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
      DBCIInsureCompare dbCIInsureCompare = new DBCIInsureCompare();
      
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
    	  dbCIInsureCompare.setSchema((CIInsureCompareSchema)schemas.get(i));
    	  dbCIInsureCompare.insert(dbpool);
      }
    }
    
    /**
     * 获取XX确认件数
     * @param dbPool
     * @param compareDate	对比日期
     * @param maxSerialNo	序列号
     * @throws Exception
     */
    public int getConfirmTotal(DbPool dbPool, 
    						   String  compareDate, 
    						   int maxSerialNo, 
    						   String iComCode)
    	throws Exception
    {
    	DBCIInsureCompare dbCIInsureCompare = new DBCIInsureCompare();
    	int intConfirmTotal = 0;
    	String sqlWhere = " SELECT COUNT(distinct(policyno)) TOTALDATA FROM CIINSURECOMPANYCOMPARE "
						+ "  WHERE UNDERWRITEENDDATE = DATE'" + compareDate + "' " 
						+ "    AND SERIALNO = '" + maxSerialNo + "' "
						+ "    AND UNDERWRITEFLAG IN ('1', '3') "
						+ "    AND SUBSTR(COMCODE, 0, 2) = '" + iComCode.substring(0, 2) + "'";
    	
    	logger.info("获取XX确认件数: " + sqlWhere);
    	
    	intConfirmTotal = dbCIInsureCompare.getCompareTotalDate(dbPool, sqlWhere);
    	return intConfirmTotal;
    }
    
    /**
     * 获取注销XX件数
     * @param dbPool
     * @param compareDate	对比日期
     * @param maxSerialNo	序列号
     * @throws Exception
     */
    public int getCancleTotal(DbPool dbPool, 
    						  String  compareDate, 
    						  int maxSerialNo, 
   						   	  String iComCode)
    	throws Exception
    {
    	DBCIInsureCompare dbCIInsureCompare = new DBCIInsureCompare();
    	int intCancleTotal = 0;
    	String sqlWhere = " SELECT COUNT(*) TOTALDATA FROM CIINSURECOMPANYCOMPARE "
			 			+ "  WHERE UNDERWRITEFLAG IN ('1', '3') "
			 			+ "    AND SUBSTR(OTHFLAG, 4, 1) = '1' "
			 			+ "    AND UNDERWRITEENDDATE = DATE'" + compareDate + "' "
			 			+ "    AND SERIALNO = '" + maxSerialNo + "' "
			 			+ "    AND SUBSTR(COMCODE, 0, 2) = '" + iComCode.substring(0, 2) + "'";
    	
    	logger.info("获取注销XX件数: " + sqlWhere);
    	
    	intCancleTotal = dbCIInsureCompare.getCompareTotalDate(dbPool, sqlWhere);
    	return intCancleTotal;
    }
    
    /**
     * 获取退XXXXXXX件数
     * @param dbPool
     * @param compareDate	对比日期
     * @param maxSerialNo	序列号
     * @throws Exception
     */
    public int getWithDrawTotal(DbPool dbPool, 
    							String compareDate, 
    							int maxSerialNo, 
     						   	String iComCode)
    	throws Exception
    {
    	DBCIInsureCompare dbCIInsureCompare = new DBCIInsureCompare();
    	int intWithDrawTotal = 0;
    	String sqlWhere = " SELECT COUNT(*) TOTALDATA FROM CIINSURECOMPANYCOMPARE "
			 			+ "  WHERE UNDERWRITEFLAG IN ('1', '3') "
			 			+ "    AND SUBSTR(OTHFLAG, 3, 1) = '1' "
			 			+ "    AND UNDERWRITEENDDATE = DATE'" + compareDate + "' "
			 			+ "    AND SERIALNO = '" + maxSerialNo + "' "
			 			+ "    AND SUBSTR(COMCODE, 0, 2) = '" + iComCode.substring(0, 2) + "'";
    	
    	logger.info("获取退XXXXXXX件数: " + sqlWhere);
    	
    	intWithDrawTotal = dbCIInsureCompare.getCompareTotalDate(dbPool, sqlWhere);
    	return intWithDrawTotal;
    }
    
    /**
     * 获取报案数
     * @param dbPool
     * @param compareDate	对比日期
     * @param maxSerialNo	序列号
     * @throws Exception
     */
    public int getReportTotal(DbPool dbPool, 
    						  String  compareDate, 
    						  int maxSerialNoClaim, 
   						   	  String iComCode)
    	throws Exception
    {
    	DBCIInsureCompare dbCIInsureCompare = new DBCIInsureCompare();
    	int intReportTotal = 0;
    	String sqlWhere = " SELECT COUNT(*) TOTALDATA FROM "
			 			+ "  (SELECT DISTINCT CLAIMCODE "
			 			+ "     FROM B_CLAIM_EBAO_COMPARE "
			 			+ "    WHERE SENDTIME = TO_DATE('" + compareDate + "','YYYY-MM-DD') "
			 			+ "      AND WORKTYPE = '50') A ";
    	
    	logger.info("获取报案数: " + sqlWhere);
    	
    	intReportTotal = dbCIInsureCompare.getCompareTotalDate(dbPool, sqlWhere);
    	return intReportTotal;
    }
    
    /**
     * 获取立案数
     * @param dbPool
     * @param compareDate	对比日期
     * @param maxSerialNo	序列号
     * @throws Exception
     */
    public int getRegistrationTotal(DbPool dbPool, 
    							    String  compareDate, 
    							    int maxSerialNoClaim, 
    	   						   	String iComCode)
    	throws Exception
    {
    	DBCIInsureCompare dbCIInsureCompare = new DBCIInsureCompare();
    	int intRegistrationTotal = 0;
    	String sqlWhere = " SELECT COUNT(*) TOTALDATA FROM "
 						+ "  (SELECT DISTINCT CLAIMCODE "
 						+ "     FROM B_CLAIM_EBAO_COMPARE "
 						+ "    WHERE SENDTIME = TO_DATE('" + compareDate + "','YYYY-MM-DD') "
 						+ "      AND WORKTYPE = '51') A ";
    	
    	logger.info("获取立案数: " + sqlWhere);
    	
    	intRegistrationTotal = dbCIInsureCompare.getCompareTotalDate(dbPool, sqlWhere);
    	return intRegistrationTotal;
    }
    
    /**
     * 获取结案数
     * @param dbPool
     * @param compareDate	对比日期
     * @param maxSerialNo	序列号
     * @throws Exception
     */
    public int getEndClaimTotal(DbPool dbPool, 
    							String  compareDate, 
    							int maxSerialNoClaim, 
     						   	String iComCode)
    	throws Exception
    {
    	DBCIInsureCompare dbCIInsureCompare = new DBCIInsureCompare();
    	int intEndClaimTotal = 0;
    	String sqlWhere = " SELECT COUNT(*) TOTALDATA FROM "
						+ "  (SELECT DISTINCT CLAIMCODE "
						+ "     FROM B_CLAIM_EBAO_COMPARE "
						+ "    WHERE SENDTIME = TO_DATE('" + compareDate + "','YYYY-MM-DD') "
						+ "      AND WORKTYPE = '52') A ";
    	
    	logger.info("获取结案数: " + sqlWhere);
    	
    	intEndClaimTotal = dbCIInsureCompare.getCompareTotalDate(dbPool, sqlWhere);
    	return intEndClaimTotal;
    }
    
    /**
     * 获取注销案件数
     * @param dbPool
     * @param compareDate	对比日期
     * @param maxSerialNo	序列号
     * @throws Exception
     */
    public int getCancelClaimTotal(DbPool dbPool, 
    							   String  compareDate, 
    							   int maxSerialNoClaim, 
    	   						   String iComCode)
    	throws Exception
    {
    	DBCIInsureCompare dbCIInsureCompare = new DBCIInsureCompare();
    	int intCancelClaimTotal = 0;
    	String sqlWhere = " SELECT COUNT(*) TOTALDATA FROM "
						+ "  (SELECT DISTINCT CLAIMCODE "
						+ "     FROM B_CLAIM_EBAO_COMPARE "
						+ "    WHERE SENDTIME = TO_DATE('" + compareDate + "','YYYY-MM-DD') "
						+ "      AND WORKTYPE = '54') A ";
    	
    	logger.info("获取注销案件数: " + sqlWhere);
    	
    	intCancelClaimTotal = dbCIInsureCompare.getCompareTotalDate(dbPool, sqlWhere);
    	return intCancelClaimTotal;
    }
}
