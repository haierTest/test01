package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import javax.naming.NamingException;
import com.sp.indiv.ci.schema.CIInsureCompareDetailSchema;
import com.sp.utility.database.DbPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBCIInsureCompareDetail extends CIInsureCompareDetailSchema
{
	Log logger = LogFactory.getLog(getClass());
	/**
     * 构造函数
     */       
    public DBCIInsureCompareDetail()
    {
    }
    
    public void update(DbPool dbpool, String strSQL) 
    	throws Exception
    {
        dbpool.update(strSQL);
    }
    
    public int getMaxSerialNo(DbPool dbpool, String strSQL)
    	throws Exception
    {
    	int iMaxSerialNo = 0;
    	ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next())
        {
        	iMaxSerialNo = resultSet.getInt("SerialNo");
        }
        resultSet.close();
    	return iMaxSerialNo;
    }
    
    /**
     * 插入一条记录
     * @param dbpool dbpool
     * @throws Exception
     */       
    public void insert(DbPool dbpool) throws Exception
    {
    	String strSQL = " INSERT INTO CIINSURECOMPAREDETAIL(" + 
                           " ComCode, " + 
                           " CompareDate, " + 
                           " OperateDate, " + 
                           " BusinessNo, " + 
                           " SerialNo, " + 
                           " SerialNoCom, " + 
                           " RequestType, " + 
                           " ResponseCode, " + 
                           " ResponseMessage, " + 
                           " ReturnTotalNum, " + 
                           " DemandNo, " + 
                           " ProposalNo, " + 
                           " PolicyNo, " + 
                           " Flag) values( " + 
                           "'" + getComCode() + "'" + "," +
                           "TO_DATE('" + getCompareDate() + "', 'YYYY-MM-DD')" + "," +
                           "TO_DATE('" + getOperateDate() + "', 'YYYY:MM:DD:HH24:MI:SS')" + "," +
                           "'" + getBusinessNo() + "'" + "," +
                           "'" + getSerialNo() + "'" + "," +
                           "'" + getSerialNoCom() + "'" + "," +
                           "'" + getRequestType() + "'" + "," +
                           "'" + getResponseCode() + "'" + "," +
                           "'" + getResponseMessage() + "'" + "," +
                           "'" + getReturnTotalNum() + "'" + "," +
                           "'" + getDemandNo() + "'" + "," +
                           "'" + getProposalNo() + "'" + "," +
                           "'" + getPolicyNo() + "'" + "," +
                           "'" + getFlag() + "'" + 
                           ")";
        
        logger.info("=====XXXXX交强XXXXX平台数据比对明细-回写CIInsureCompareDetail：" + strSQL);
        
        dbpool.insert(strSQL);
    }
    
    public Vector findByConditions(DbPool dbpool, String strSQL) 
    	throws SQLException, NamingException
    {
        Vector vector = new Vector();
        CIInsureCompareDetailSchema cIInsureCompareDetailSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
        	cIInsureCompareDetailSchema = new CIInsureCompareDetailSchema();
        	cIInsureCompareDetailSchema.setComCode(resultSet.getString("ComCode"));
        	cIInsureCompareDetailSchema.setCompareDate(resultSet.getString("CompareDate"));
        	cIInsureCompareDetailSchema.setOperateDate(resultSet.getString("OperateDate"));
        	cIInsureCompareDetailSchema.setBusinessNo(resultSet.getString("BusinessNo"));
        	cIInsureCompareDetailSchema.setSerialNo(resultSet.getString("SerialNo"));
        	cIInsureCompareDetailSchema.setSerialNoCom(resultSet.getString("SerialNoCom"));
        	cIInsureCompareDetailSchema.setRequestType(resultSet.getString("RequestType"));
        	cIInsureCompareDetailSchema.setResponseCode(resultSet.getString("ResponseCode"));
        	cIInsureCompareDetailSchema.setResponseMessage(resultSet.getString("ResponseMessage"));
        	cIInsureCompareDetailSchema.setReturnTotalNum(resultSet.getString("ReturnTotalNum"));
        	cIInsureCompareDetailSchema.setDemandNo(resultSet.getString("DemandNo"));
        	cIInsureCompareDetailSchema.setProposalNo(resultSet.getString("ProposalNo"));
        	cIInsureCompareDetailSchema.setPolicyNo(resultSet.getString("PolicyNo"));
        	cIInsureCompareDetailSchema.setFlag(resultSet.getString("Flag"));
            vector.add(cIInsureCompareDetailSchema);
        }
        resultSet.close();
        return vector;
    }
}

