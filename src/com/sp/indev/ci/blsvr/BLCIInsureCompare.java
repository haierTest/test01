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
     * ���캯��
     */       
    public BLCIInsureCompare(){
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
     *����һ��CIInsureCompareSchema��¼
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
     *�õ�һ��CIInsureCompareSchema��¼
     *@param index �±�
     *@return һ��CIInsureCompareSchema����
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
      DBCIInsureCompare dbCIInsureCompare = new DBCIInsureCompare();
      
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
    	  dbCIInsureCompare.setSchema((CIInsureCompareSchema)schemas.get(i));
    	  dbCIInsureCompare.insert(dbpool);
      }
    }
    
    /**
     * ��ȡXXȷ�ϼ���
     * @param dbPool
     * @param compareDate	�Ա�����
     * @param maxSerialNo	���к�
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
    	
    	logger.info("��ȡXXȷ�ϼ���: " + sqlWhere);
    	
    	intConfirmTotal = dbCIInsureCompare.getCompareTotalDate(dbPool, sqlWhere);
    	return intConfirmTotal;
    }
    
    /**
     * ��ȡע��XX����
     * @param dbPool
     * @param compareDate	�Ա�����
     * @param maxSerialNo	���к�
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
    	
    	logger.info("��ȡע��XX����: " + sqlWhere);
    	
    	intCancleTotal = dbCIInsureCompare.getCompareTotalDate(dbPool, sqlWhere);
    	return intCancleTotal;
    }
    
    /**
     * ��ȡ��XXXXXXX����
     * @param dbPool
     * @param compareDate	�Ա�����
     * @param maxSerialNo	���к�
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
    	
    	logger.info("��ȡ��XXXXXXX����: " + sqlWhere);
    	
    	intWithDrawTotal = dbCIInsureCompare.getCompareTotalDate(dbPool, sqlWhere);
    	return intWithDrawTotal;
    }
    
    /**
     * ��ȡ������
     * @param dbPool
     * @param compareDate	�Ա�����
     * @param maxSerialNo	���к�
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
    	
    	logger.info("��ȡ������: " + sqlWhere);
    	
    	intReportTotal = dbCIInsureCompare.getCompareTotalDate(dbPool, sqlWhere);
    	return intReportTotal;
    }
    
    /**
     * ��ȡ������
     * @param dbPool
     * @param compareDate	�Ա�����
     * @param maxSerialNo	���к�
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
    	
    	logger.info("��ȡ������: " + sqlWhere);
    	
    	intRegistrationTotal = dbCIInsureCompare.getCompareTotalDate(dbPool, sqlWhere);
    	return intRegistrationTotal;
    }
    
    /**
     * ��ȡ�᰸��
     * @param dbPool
     * @param compareDate	�Ա�����
     * @param maxSerialNo	���к�
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
    	
    	logger.info("��ȡ�᰸��: " + sqlWhere);
    	
    	intEndClaimTotal = dbCIInsureCompare.getCompareTotalDate(dbPool, sqlWhere);
    	return intEndClaimTotal;
    }
    
    /**
     * ��ȡע��������
     * @param dbPool
     * @param compareDate	�Ա�����
     * @param maxSerialNo	���к�
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
    	
    	logger.info("��ȡע��������: " + sqlWhere);
    	
    	intCancelClaimTotal = dbCIInsureCompare.getCompareTotalDate(dbPool, sqlWhere);
    	return intCancelClaimTotal;
    }
}
