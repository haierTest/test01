package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.CIInsureCompareSchema;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.utility.database.DbPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBCIInsureCompare extends CIInsureCompareSchema
{
	Log logger = LogFactory.getLog(getClass());
	/**
     * 构造函数
     */       
    public DBCIInsureCompare()
    {
    }
    
    /**
     * 根据SQL获取XX确认、退XXXXX数据、注销数据的总数
     * @param dbpool
     * @param strSQL
     * @return 统计口径总数
     * @throws SQLException
     * @throws Exception
     */
    public int getCompareTotalDate(DbPool dbpool, String strSQL)
	throws SQLException, Exception
	{
		int iTotalDate = 0;
		ResultSet resultSet = dbpool.query(strSQL);
	    if(resultSet.next())
	    {
	    	iTotalDate = resultSet.getInt("TOTALDATA");
	    }
	    resultSet.close();
		return iTotalDate;
	}
    
    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception
    {
    	String strSQL = " Insert Into CIInsureCompare(" + 
                           " ComCode, " + 
                           " SerialNo, " + 
                           " CompareDate, " + 
                           " OperateDate, " + 
                           
                           
                           " PutConfirm, " + 
                           " GetConfirm, " + 
                           " PutCancel, " + 
                           " GetCancel, " + 
                           " PutWithDraw, " + 
                           " GetWithDraw, " + 
                           " PutReport, " + 
                           " GetReport, " + 
                           " PutRegistration, " + 
                           " GetRegistration, " + 
                           " PutClaim, " + 
                           " GetClaim, " + 
                           " PutCancelClaim, " + 
                           " GetCancelClaim, " + 
                           " ResponseCode, " + 
                           " ConfirmResult, " + 
                           " ResponseMessage, " + 
                           " Remark, " + 
                           " Flag) values( " + 
                           "'" + getComCode() +"'" + "," +
                           "'" + getSerialNo() +"'" + "," +
                           "TO_DATE('" + getCompareDate() +"', 'YYYY-MM-DD')" + "," +
                           "TO_DATE('" + getOperateDate() +"', 'YYYY:MM:DD:HH24:MI:SS')" + "," +
                           "'" + getPutConfirm() +"'" + "," +
                           "'" + getGetConfirm() +"'" + "," +
                           "'" + getPutCancel() +"'" + "," +
                           "'" + getGetCancel() +"'" + "," +
                           "'" + getPutWithDraw() +"'" + "," +
                           "'" + getGetWithDraw() +"'" + "," +
                           "'" + getPutReport() +"'" + "," +
                           "'" + getGetReport() +"'" + "," +
                           "'" + getPutRegistration() +"'" + "," +
                           "'" + getGetRegistration() +"'" + "," +
                           "'" + getPutEndClaim() +"'" + "," +
                           "'" + getGetEndClaim() +"'" + "," +
                           "'" + getPutCancelClaim() +"'" + "," +
                           "'" + getGetCancelClaim() +"'" + "," +
                           "'" + getResponseCode() +"'" + "," +
                           "'" + getConfirmResult() +"'" + "," +
                           "'" + getResponseMessage() +"'" + "," +
                           "'" + getRemark() +"'" + "," +
                           "'" + getFlag() +"'" + 
                           ")";
        
        logger.info("=====XXXXX交强XXXXX平台数据比对-回写CIInsureCompare：" + strSQL);
        
        dbpool.insert(strSQL);
    }
}
