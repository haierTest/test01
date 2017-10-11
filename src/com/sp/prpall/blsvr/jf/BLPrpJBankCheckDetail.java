package com.sp.prpall.blsvr.jf;

import java.util.ArrayList;
import java.util.List;

import com.sp.prpall.dbsvr.jf.DBPrpJBankCheckDetail;
import com.sp.prpall.schema.PrpJBankCheckDetailSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义PrpJBankCheckDetail-银行对账信息明细表的BL类
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-12-21</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJBankCheckDetail{
    private List schemas = new ArrayList();
    /**
     * 构造函数
     */       
    public BLPrpJBankCheckDetail(){
    }

    /**
     *初始化记录
     *@param 无
     *@return 无
     *@throws Exception
     */
    public void initArr() throws Exception
    {
       schemas = new ArrayList();
     }
    /**
     *增加一条PrpJBankCheckDetailSchema记录
     *@param iPrpJBankCheckDetailSchema PrpJBankCheckDetailSchema
     *@throws Exception
     */
    public void setArr(PrpJBankCheckDetailSchema iPrpJBankCheckDetailSchema)
			throws Exception {
		try {
			schemas.add(iPrpJBankCheckDetailSchema);
		} catch (Exception e) {
			throw e;
		}
	}
    /**
	 * 得到一条PrpJBankCheckDetailSchema记录
	 * 
	 * @param index 下标
	 * @return 一个PrpJBankCheckDetailSchema对象
	 * @throws Exception
	 */
    public PrpJBankCheckDetailSchema getArr(int index) throws Exception {
		PrpJBankCheckDetailSchema prpJBankCheckDetailSchema = null;
		try {
			prpJBankCheckDetailSchema = (PrpJBankCheckDetailSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJBankCheckDetailSchema;
	}
    /**
	 * 删除一条PrpJBankCheckDetailSchema记录
	 * 
	 * @param index 下标
	 * @throws Exception
	 */
    public void remove(int index) throws Exception {
		try {
			this.schemas.remove(index);
		} catch (Exception e) {
			throw e;
		}
	}
    /**
	 * 得到schemas记录数
	 * 
	 * @return schemas记录数
	 * @throws Exception
	 */
    public int getSize() throws Exception {
		return this.schemas.size();
	}
    /**
	 * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param iWherePart 查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
    public void query(String iWherePart) throws UserException, Exception {
		this.query(iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount) throws UserException,
			Exception {
		String strSqlStatement = "";
		DBPrpJBankCheckDetail dbPrpJBankCheckDetail = new DBPrpJBankCheckDetail();
		if (iLimitCount > 0 && dbPrpJBankCheckDetail.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJBankCheckDetail.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJBankCheckDetail WHERE "+ iWherePart;
			schemas = dbPrpJBankCheckDetail.findByConditions(strSqlStatement);
		}
	}
    /**
	 * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool 全局池
	 * @param iWherePart 查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
    public void query(DbPool dbpool, String iWherePart) throws UserException,
			Exception {
		this.query(dbpool, iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount)
			throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpJBankCheckDetail dbPrpJBankCheckDetail = new DBPrpJBankCheckDetail();
		if (iLimitCount > 0 && dbPrpJBankCheckDetail.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJBankCheckDetail.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJBankCheckDetail WHERE " + iWherePart;
			schemas = dbPrpJBankCheckDetail.findByConditions(dbpool,strSqlStatement);
		}
	}
      
    /**
	 * 带dbpool的save方法
	 * 
	 * @param 无
	 * @return 无
	 */
	public void save(DbPool dbpool) throws Exception {
		DBPrpJBankCheckDetail dbPrpJBankCheckDetail = new DBPrpJBankCheckDetail();
		int i = 0;
		for (i = 0; i < schemas.size(); i++) {
			dbPrpJBankCheckDetail.setSchema((PrpJBankCheckDetailSchema) schemas.get(i));
			dbPrpJBankCheckDetail.insert(dbpool);
		}
	}
      
    /**
	 * 不带dbpool的XXXXX存方法
	 * 
	 * @param 无
	 * @return 无
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
	 * 
	 * @param dbpool 连接池
	 * @param billNo billNo
	 * @return 无
	 */
    public void cancel(DbPool dbpool, String billNo) throws Exception {





    	String strSqlStatement = " DELETE FROM PrpJBankCheckDetail WHERE BillNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, billNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
	}
      
    /**
	 * 不带dbpool的删除方法
	 * 
	 * @param billNo billNo
	 * @return 无
	 */
    public void cancel(String billNo) throws Exception {
		DbPool dbpool = new DbPool();
		
		dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		
		try {
			dbpool.beginTransaction();
			cancel(dbpool, billNo);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}
      
    /**
	 * 带dbpool根据billNo和serialNo获取数据
	 * 
	 * @param billNo billNo
	 * @param serialNo serialNo
	 * @return 无
	 */
    public void getData(String billNo, String serialNo) throws Exception {
		DbPool dbpool = new DbPool();
		
		dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		
		try{
			getData(dbpool, billNo, serialNo);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			dbpool.close();
		}
	}
      
    /**
	 * 不带dbpool根据billNo和serialNo获取数据
	 * 
	 * @param dbpool 连接池
	 * @param billNo billNo
	 * @param serialNo serialNo
	 * @return 无
	 */
	public void getData(DbPool dbpool, String billNo, String serialNo)
			throws Exception {
		String strWherePart = "";
		strWherePart = "BillNo= '" + billNo + "' AND SerialNo = '" + serialNo+ "'";
		query(dbpool, strWherePart, 0);
	}
    /**
     * @desc  查询对账单明细信息
     * @param iBillNo
     * @param iShowType 是否包含未核对或正确数据 0 不包含 1 包含
     * @throws Exception
     */  
    public void billDetailShow(String iBillNo,String iShowType)
    throws Exception{
		DbPool dbpool = new DbPool();
        
        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        
		try{
			billDetailShow(dbpool, iBillNo, iShowType);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			dbpool.close();
		}
    }
    /**
     * @desc  查询对账单明细信息
     * @param iBillNo
     * @param iShowType 是否包含未核对或正确数据 0 不包含 1 包含
     * @throws Exception
     */  
    public void billDetailShow(DbPool dbpool,String iBillNo,String iShowType)
    throws Exception{
    	StringBuffer strBuffer = new StringBuffer();
    	strBuffer.append(" BillNo = '");
    	strBuffer.append(iBillNo);
    	strBuffer.append("' ");
    	if(iShowType.equals("0")){
    		
    		strBuffer.append(" AND CheckFlag = '2'");
    	}
    	strBuffer.append(" Order by SerialNo");
    	this.query(dbpool, strBuffer.toString(), 0);
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
