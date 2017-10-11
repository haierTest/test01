package com.sp.indiv.ci.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.indiv.ci.schema.CIInsureCompanyCompareSchema;
import com.sp.utility.database.DbPool;

public class DBCIInsureCompanyCompare extends CIInsureCompanyCompareSchema
{
	/**
     * ¹¹Ôìº¯Êý
     */       
    public DBCIInsureCompanyCompare()
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
    
    public Vector findByConditions(DbPool dbpool, String strSQL) 
    	throws SQLException, NamingException
    {
        Vector vector = new Vector();
        CIInsureCompanyCompareSchema cIInsureCompanyCompareSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
        	cIInsureCompanyCompareSchema = new CIInsureCompanyCompareSchema();
        	cIInsureCompanyCompareSchema.setComCode(resultSet.getString("ComCode"));
        	cIInsureCompanyCompareSchema.setCompareDate(resultSet.getString("CompareDate"));
        	cIInsureCompanyCompareSchema.setOperateDate(resultSet.getString("OperateDate"));
        	cIInsureCompanyCompareSchema.setPolicyNo(resultSet.getString("PolicyNo"));
        	cIInsureCompanyCompareSchema.setSerialNo(resultSet.getString("SerialNo"));
        	cIInsureCompanyCompareSchema.setProposalNo(resultSet.getString("ProposalNo"));
        	cIInsureCompanyCompareSchema.setDemandNo(resultSet.getString("DemandNo"));
        	cIInsureCompanyCompareSchema.setValidNo(resultSet.getString("ValidNo"));
        	cIInsureCompanyCompareSchema.setFlag(resultSet.getString("Flag"));
        	cIInsureCompanyCompareSchema.setUnderWriteFlag(resultSet.getString("UnderWriteFlag"));
        	cIInsureCompanyCompareSchema.setOthFlag(resultSet.getString("OthFlag"));
        	cIInsureCompanyCompareSchema.setUnderWriteEndDate(resultSet.getString("UnderWriteEndDate"));
            vector.add(cIInsureCompanyCompareSchema);
        }
        resultSet.close();
        return vector;
    }
}
