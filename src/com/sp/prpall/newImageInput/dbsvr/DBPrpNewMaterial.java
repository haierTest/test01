package com.sp.prpall.newImageInput.dbsvr;

import java.sql.ResultSet;

import com.sp.prpall.newImageInput.schema.PrpNewMaterialSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;

public class DBPrpNewMaterial extends PrpNewMaterialSchema {

	private static final long serialVersionUID = -6321618280955769329L;


	public DBPrpNewMaterial() {
		super();
	}

	public DBPrpNewMaterial(String followUpFlag, String barCode,
			String fileStatus) {
		super(followUpFlag, barCode, fileStatus);
	}

	/**
	 * 插入一条记录
	 * 
	 * @param dbpool
	 *            dbpool
	 * @throws Exception
	 */
	public void insert(DbPool dbpool) throws Exception {
		String strSQL = " Insert Into PrpMaterial(" + " BusinessNo,"
				+ " SerialNo," + " PolicyNo," + " TypeCode," + " PicName,"
				+ " SysFileName," + " FileName," + " FilePath," + " FileType,"
				+ " Description," + " OperatorCode," + " OperatorName,"
				+ " OperateTime," + " Flag,"+"FollowUpFlag,"+"BarCode)"
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		dbpool.prepareInnerStatement(strSQL);
		int index = 1;
		dbpool.setString(index++, getBusinessNo());
		dbpool.setInt(index++, getSerialNo());
		dbpool.setString(index++, getPolicyNo());
		dbpool.setString(index++, getTypeCode());
		dbpool.setString(index++, getPicName());
		dbpool.setString(index++, getSysFileName());
		dbpool.setString(index++, getFileName());
		dbpool.setString(index++, getFilePath());
		dbpool.setString(index++, getFileType());
		dbpool.setString(index++, getDescription());
		dbpool.setString(index++, getOperatorCode());
		dbpool.setString(index++, getOperatorName());
		dbpool.setString(index++, getOperateTime());
		dbpool.setString(index++, getFlag());
		dbpool.setString(index++, getFollowUpFlag());
		dbpool.setString(index++, getBarCode());
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
	}

	/**
	 * 插入一条记录
	 * 
	 * @throws Exception
	 */
	public void insert() throws Exception {
		DbPool dbpool = new DbPool();
		
		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			dbpool.beginTransaction();
			insert(dbpool);
			dbpool.commitTransaction();
			dbpool.close();
		} catch (Exception exception) {
			dbpool.rollbackTransaction();
			dbpool.close();
			throw exception;
		} finally {
			dbpool.close();
		}
	}
	
    public int getMaxNo(String businessNo) throws Exception {
        DbPool dbpool = new DbPool();
        int maxNo = 0;
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            String strSQL = " Select max(serialno) From PrpMaterial Where businessNo = "
                    + "'" + businessNo + "'";
            ResultSet resultSet = dbpool.executeQuery(strSQL);
            if (resultSet.next()) {
                String value = resultSet.getString(1);
                if (value != null && value.trim().length() > 0) {
                    maxNo = Integer.parseInt(value.trim());
                }
            }

        } catch (Exception exception) {
            throw exception;
        } finally {
            dbpool.close();
        }
        return maxNo;
    }
    
    public void delete(String businessNo, String sysFileName) throws Exception {
        DbPool dbpool = new DbPool();
        
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            dbpool.beginTransaction();
            String strSQL = " Delete From PrpMaterial Where businessNo = "
                    + "'" + businessNo + "' AND sysFileName ='" + sysFileName+"' And followUpFlag='1'";
            dbpool.delete(strSQL);
            dbpool.commitTransaction();
        } catch (Exception exception) {
            dbpool.rollbackTransaction();
            throw exception;
        } finally {
            dbpool.close();
        }
    }
 
    public void delete(DbPool dbpool,String businessNo, String sysFileName) throws Exception {

        String strSQL = " Delete From PrpMaterial Where businessNo = ? And sysFileName = ? And followUpFlag='1'";
    	dbpool.prepareInnerStatement(strSQL);
        dbpool.setString(1,businessNo);
        dbpool.setString(2,sysFileName);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement(); 
    }

}
