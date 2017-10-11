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
     * ���캯��
     */       
    public BLCIInsureCompanyCompare(){
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
     *����һ��CIInsureCompanyCompare��¼
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
     *�õ�һ��CIInsureCompanyCompare��¼
     *@param index �±�
     *@return һ��CIInsureCompanyCompare����
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
     *ɾ��һ��CIInsureCompanyCompare��¼
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
     * ��ȡCIInsureCompanyCompare��ǰ�������ڵ����SerialNo
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
    	
    	logger.info("XXXXX��ǿXXXXXƽ̨���ݱȶ�-��ȡCIInsureCompanyCompare��ǰ�������ڵ����SerialNo-SQL: " + sqlSQL);
    	
    	iMaxSerialNo = dbCIInsureCompanyCompare.getMaxSerialNo(dbPool, sqlSQL);
    	return iMaxSerialNo;
    }
    
    /**
     * ��ȡb_claim_ebao_compare��ǰ�������ڵ����SerialNo
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
    	
    	logger.info("XXXXX��ǿXXXXXƽ̨���ݱȶ�-��ȡb_claim_ebao_compare��ǰ�������ڵ����SerialNo-SQL: " + sqlSQL);
    	
    	iMaxSerialNoClaim = dbCIInsureCompanyCompare.getMaxSerialNo(dbPool, sqlSQL);
    	return iMaxSerialNoClaim;
    }
    
    /**
     * ���ݱȶ����ڽ�ҵ���XX���ݵ���CIInsureCompanyCompare����
     * @param dbPool
     * @param strCompareDate	�ȶ�����
     * @param strCurrentDate	��ǰ����
     * @param iMaxSerialNo		��ǰ������к�
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
    	
    	
    	logger.info("XXXXX��ǿXXXXXƽ̨���ݱȶ���CIInsureCompanyCOmpare�����XX���ֵ�����SQL�� " + sqlSQL);
    	
    	dbCIInsureCompanyCompare.update(dbPool, sqlSQL);
    }
    
    /**
     * ���ݱȶ����ڽ�ҵ����������ݵ���CIInsureCompanyCompare����
     * @param dbPool
     * @param strCompareDate	�ȶ�����
     * @param strCurrentDate	��ǰ����
     * @param iMaxSerialNo		��ǰ������к�
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
    	
    	
    	logger.info("XXXXX��ǿXXXXXƽ̨���ݱȶ���CIInsureCompanyCOmpare��������Ĵ��������SQL�� " + sqlSQL);
    	
    	dbCIInsureCompanyCompare.update(dbPool, sqlSQL);
    }
    
    /**
     *��dbpool��query����
     *@param ��
     *@return ��
     */
    public Vector query(DbPool dbPool, String strSQL) throws Exception
    {
    	Vector vCompanyCompare = new Vector();
    	DBCIInsureCompanyCompare dbCIInsureCompanyCompare = new DBCIInsureCompanyCompare();
    	vCompanyCompare = dbCIInsureCompanyCompare.findByConditions(dbPool, strSQL);
    	return vCompanyCompare;
    }
}
