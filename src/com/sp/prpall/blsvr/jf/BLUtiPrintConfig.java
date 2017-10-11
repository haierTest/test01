package com.sp.prpall.blsvr.jf;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.jf.DBUtiPrintConfig;
import com.sp.prpall.schema.UtiPrintConfigSchema;
import com.sp.utiall.dbsvr.DBPrpDcompany;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义UtiPrintConfig的BL类
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>@createdate 2013-04-18</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLUtiPrintConfig {
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLUtiPrintConfig() {
    }

    /**
     *初始化记录
     *@param 无
     *@return 无
     *@throws Exception
     */
    public void initArr() throws Exception {
        schemas = new Vector();
    }
    /**
     *增加一条UtiPrintConfigSchema记录
     *@param iUtiPrintConfigSchema UtiPrintConfigSchema
     *@throws Exception
     */
    public void setArr(UtiPrintConfigSchema iUtiPrintConfigSchema) throws Exception {
        try {
            schemas.add(iUtiPrintConfigSchema);
        } catch(Exception e) {
            throw e;
        }
    }
    /**
     *得到一条UtiPrintConfigSchema记录
     *@param index 下标
     *@return 一个UtiPrintConfigSchema对象
     *@throws Exception
     */
    public UtiPrintConfigSchema getArr(int index) throws Exception {
        UtiPrintConfigSchema utiPrintConfigSchema = null;
        try {
            utiPrintConfigSchema = (UtiPrintConfigSchema)this.schemas.get(index);
        } catch(Exception e) {
            throw e;
        }
        return utiPrintConfigSchema;
    }
    /**
     *删除一条UtiPrintConfigSchema记录
     *@param index 下标
     *@throws Exception
     */
    public void remove(int index) throws Exception {
        try {
            this.schemas.remove(index);
        } catch(Exception e) {
            throw e;
        }
    }
    /**
     *得到schemas记录数
     *@return schemas记录数
     *@throws Exception
     */
    public int getSize() throws Exception {
        return this.schemas.size();
    }
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, ArrayList bindValues) throws UserException,Exception {
        this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()), bindValues);
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception {
        String strSqlStatement = "";
        DBUtiPrintConfig dbUtiPrintConfig = new DBUtiPrintConfig();
        if (iLimitCount > 0 && dbUtiPrintConfig.getCount(iWherePart) > iLimitCount) {
            throw new UserException(-98,-1003,"BLUtiPrintConfig.query");
        } else {
            initArr();
            strSqlStatement = " SELECT * FROM UtiPrintConfig WHERE " + iWherePart; 
            schemas = dbUtiPrintConfig.findByConditions(strSqlStatement, bindValues);
        }
    }
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, ArrayList bindValues) throws UserException,Exception {
        this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()),bindValues);
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception {
        String strSqlStatement = "";
        DBUtiPrintConfig dbUtiPrintConfig = new DBUtiPrintConfig();
        if (iLimitCount > 0 && dbUtiPrintConfig.getCount(iWherePart) > iLimitCount) {
            throw new UserException(-98,-1003,"BLUtiPrintConfig.query");
        } else {
            initArr();
            strSqlStatement = " SELECT * FROM UtiPrintConfig WHERE " + iWherePart; 
            schemas = dbUtiPrintConfig.findByConditions(dbpool, strSqlStatement, bindValues);
        }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception {
        DBUtiPrintConfig dbUtiPrintConfig = new DBUtiPrintConfig();
        int i = 0;
        for(i = 0; i< schemas.size(); i++) {
            dbUtiPrintConfig.setSchema((UtiPrintConfigSchema)schemas.get(i));
            dbUtiPrintConfig.insert(dbpool);
        }
    }
      
    /**
     *不带dbpool的XXXXX存方法
     *@param 无
     *@return 无
     */
    public void save() throws Exception {
        DbPool dbpool = new DbPool();
        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        try {
            dbpool.beginTransaction();
            save(dbpool);
            dbpool.commitTransaction(); 
        } catch (Exception e) {
            dbpool.rollbackTransaction();
        } finally {
            dbpool.close();
        }
    }
      
    /**
     * 带dbpool的删除方法
     *@param dbpool    连接池
     *@param systemCode SystemCode
     *@return 无
     */
    public void cancel(DbPool dbpool, String systemCode) throws Exception {
        String strSqlStatement = "";
        strSqlStatement = " DELETE FROM UtiPrintConfig WHERE systemCode='" + systemCode + "'";
        dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param systemCode SystemCode
     *@return 无
     */
    public void cancel(String systemCode) throws Exception {
        DbPool dbpool = new DbPool();
        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        try {
            dbpool.beginTransaction();
            cancel(dbpool, systemCode);
            dbpool.commitTransaction(); 
        } catch (Exception e) {
            dbpool.rollbackTransaction();
        } finally {
            dbpool.close();
        }
    }
      
    /**
     * 不带dbpool根据主键获取数据
     *@param systemCode SystemCode
     *@return 无
     */
    public void getData(String systemCode) throws Exception {
        DbPool dbpool = new DbPool();
        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        getData(dbpool, systemCode);
        dbpool.close();
    }
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param systemCode SystemCode
     *@return 无
     */
    public void getData(DbPool dbpool, String systemCode) throws Exception {
        String strWherePart = "";
        strWherePart = "systemCode='" + systemCode + "'";
        query(dbpool, strWherePart, 0, null);
    }
    
    public String getConfigValueByComCode(String systemCode, String comCode,
            String configCode) throws Exception {
        String configValue = "";
        DBUtiPrintConfig dbUtiPrintConfig = new DBUtiPrintConfig();
        while (true) {
        	int intResult = dbUtiPrintConfig.getInfo(systemCode, comCode, configCode);
            if (intResult == 0) {
                configValue = dbUtiPrintConfig.getConfigvalue();
                break;
            } else {
                if("00000000".equals(comCode)){
                    throw new UserException(-98,-1200,"BLUtiPrintConfig.getConfigValueByComCode()","获取总公司配置代码("+configCode+")失败！");
                }
                DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
                int intResult1 = dbPrpDcompany.getInfo(comCode);
                if(intResult1 ==100) {
                    throw new UserException(-98,-1200,"BLUtiPrintConfig.getConfigValueByComCode()","获取上级机构代码失败！");
                } else {
                	comCode = dbPrpDcompany.getUpperComCode();
                }
            }
        }
        return configValue;
    }
    
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args) {
        
    }
}
