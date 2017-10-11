package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBTBCust;
import com.sp.prpall.schema.PCustSchema;
import com.sp.prpall.schema.TBCustSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义TB_CUST的BL类 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>
 * Title: SinosoftJavaTools v1.4
 * </p>
 * <p>
 * Description: 中科软Java源码生成工具
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * 
 * @createdate 2009-11-06
 *             </p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 */
public class BLTBCust {
	private Vector schemas = new Vector();

	/**
	 * 构造函数
	 */
	public BLTBCust() {
	}

	/**
	 * 初始化记录
	 * 
	 * @param 无
	 * @return 无
	 * @throws Exception
	 */
	public void initArr() throws Exception {
		schemas = new Vector();
	}

	/**
	 * 增加一条TBCustSchema记录
	 * 
	 * @param TBCustSchema
	 *            TBCustSchema
	 * @throws Exception
	 */
	public void setArr(TBCustSchema iTBCustSchema) throws Exception {
		try {
			schemas.add(iTBCustSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到一条TBCustSchema记录
	 * 
	 * @param index
	 *            下标
	 * @return 一个TBCustSchema对象
	 * @throws Exception
	 */
	public TBCustSchema getArr(int index) throws Exception {
		TBCustSchema tnCustSchema = null;
		try {
			tnCustSchema = (TBCustSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return tnCustSchema;
	}

	/**
	 * 删除一条TB_CUSTSchema记录
	 * 
	 * @param index
	 *            下标
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
	 * @param iWherePart
	 *            查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart) throws UserException, Exception {
		this.query(iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param iWherePart
	 *            查询条件(包括排序字句)
	 * @param iLimitCount
	 *            记录数限制(iLimitCount=0: 无限制)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart, int iLimitCount) throws UserException,
			Exception {
		String strSqlStatement = "";
		DBTBCust dbTBCust = new DBTBCust();
		if (iLimitCount > 0 && dbTBCust.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLTBCust.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM TB_CUST WHERE " + iWherePart;
			schemas = dbTBCust.findByConditions(strSqlStatement);
		}
	}

	/**
	 * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool
	 *            全局池
	 * @param iWherePart
	 *            查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart) throws UserException,
			Exception {
		this.query(dbpool, iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool
	 *            全局池
	 * @param iWherePart
	 *            查询条件(包括排序字句)
	 * @param iLimitCount
	 *            记录数限制(iLimitCount=0: 无限制)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, int iLimitCount)
			throws UserException, Exception {
		String strSqlStatement = "";
		DBTBCust dbTBCust = new DBTBCust();
		if (iLimitCount > 0 && dbTBCust.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLTBCust.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM TB_CUST WHERE " + iWherePart;
			schemas = dbTBCust.findByConditions(dbpool, strSqlStatement);
		}
	}

	/**
	 * 带dbpool的save方法
	 * 
	 * @param 无
	 * @return 无
	 */
	public void save(DbPool dbpool) throws Exception {
		DBTBCust dbTBCust = new DBTBCust();

		int i = 0;

		for (i = 0; i < schemas.size(); i++) {
			dbTBCust.setSchema((TBCustSchema) schemas.get(i));
			dbTBCust.insert(dbpool);
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

		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
	 * @param dbpool
	 *            连接池
	 * @param iCUSTID
	 *            CUSTID
	 * @return 无
	 */
	public void cancel(DbPool dbpool, String iCUSTID) throws Exception {





		String strSqlStatement = " DELETE FROM TB_CUST WHERE CUSTID= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iCUSTID);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
	}

	/**
	 * 不带dbpool的删除方法
	 * 
	 * @param iCUSTID
	 *            CUSTID
	 * @return 无
	 */
	public void cancel(String iCUSTID) throws Exception {
		DbPool dbpool = new DbPool();

		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			dbpool.beginTransaction();
			cancel(dbpool, iCUSTID);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}

	/**
	 * 带dbpool根据CUSTID获取数据
	 * 
	 * @param iCUSTID
	 *            CUSTID
	 * @return 无
	 */
	public void getData(String iCUSTID) throws Exception {
		DbPool dbpool = new DbPool();
		dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
		getData(dbpool, iCUSTID);
		dbpool.close();
	}

	/**
	 * 不带dbpool根据CUSTID获取数据
	 * 
	 * @param dbpool
	 *            连接池
	 * @param iCUSTID
	 *            CUSTID
	 * @return 无
	 */
	public void getData(DbPool dbpool, String iCUSTID) throws Exception {
        
        
        
        
        String strWherePart = " CUSTID= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iCUSTID);
        query(dbpool, strWherePart, arrWhereValue, 0);
        
	}

    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@author wangchuanzhong 20100602
     *@param dbpool      全局池
     *@param iWherePart  查询条件,传入条件均已绑定变量形式，问号个数与属性值个数一致
     *@param iWhereValue 查询条件各字段值
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,ArrayList iWhereValue, int iLimitCount) throws UserException,Exception
    {
        String strSqlStatement = "";
        DBTBCust dbTBCust = new DBTBCust();
        if (iLimitCount > 0 && dbTBCust.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLTBCust.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM TB_CUST WHERE " + iWherePart;
            schemas = dbTBCust.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
	
	/**
	 * 主函数
	 * 
	 * @param args
	 *            参数列表
	 */
	public static void main(String[] args) {
		
	}
	/**
	 * 带dbpool的update方法（贵州工行业务）用于往tb_cust回写字段调用
	 * 
	 * @param 无
	 * @return 无
	 * @author duguangrun 2015330
	 */
	public void writeBacknsuredcodeTB_Cust(String insuredCode,String custId,DbPool dbpool) throws Exception {

			String updateSQL = "update tb_cust set insuredcode = ? where custId = ? ";
	    	dbpool.prepareInnerStatement(updateSQL);
	    	dbpool.setString(1, insuredCode);
	    	dbpool.setString(2, custId);
	    	dbpool.executePreparedUpdate();
	    	dbpool.closePreparedStatement();
	}
	/**
	 * 
	 * XXXXX存所有list（贵州工行业务）批改添加或替换人时调用。
	 * @param p_CUSTSchemaList
	 * @author duguangrun  20150409
	 * @throws Exception 
	 */
	public void saveAll(ArrayList<PCustSchema> p_CUSTSchemaList) throws Exception {
		DbPool dbpool = new DbPool();

		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			dbpool.beginTransaction();
		for (PCustSchema p_CUSTSchema : p_CUSTSchemaList) {
			DBTBCust dbTBCust = new DBTBCust();
			dbTBCust.setCustID(p_CUSTSchema.getCUSTID());						
			dbTBCust.setCustName(p_CUSTSchema.getCUSTNAME());					
			dbTBCust.setBirthDay(p_CUSTSchema.getBIRTHDAY());					
			dbTBCust.setSex(p_CUSTSchema.getSEX());								
			dbTBCust.setAge(p_CUSTSchema.getAGE());								
			dbTBCust.setIDNo(p_CUSTSchema.getIDNO());							
			dbTBCust.setIDType(p_CUSTSchema.getIDTYPE());						
			dbTBCust.setPhone(p_CUSTSchema.getPHONE());							
			dbTBCust.setBankAccNo(p_CUSTSchema.getBANKACCNO());					
			dbTBCust.setAccName(p_CUSTSchema.getACCNAME());						
			dbTBCust.setOccupationType(p_CUSTSchema.getOCCUPATIONTYPE());		
			dbTBCust.setWorkType(p_CUSTSchema.getWORKTYPE());					
			dbTBCust.setBenefScale(p_CUSTSchema.getBENEFSCALE());				
			dbTBCust.setCustType(p_CUSTSchema.getCUSTTYPE());					
			dbTBCust.setPolicyNo(p_CUSTSchema.getPOLICYNO());					
			dbTBCust.setInsuredID(p_CUSTSchema.getINSUREDID());					
			dbTBCust.setModifyDate(p_CUSTSchema.getUPDATEDATE());				
			dbTBCust.setRelateItemKindNo(p_CUSTSchema.getFAMILYNO());			
				dbTBCust.setInsuredCode(p_CUSTSchema.getINSUREDCODE());				
				dbTBCust.setFlag("1");												
				dbTBCust.insert(dbpool);
			}
			dbpool.commitTransaction();
		}catch (Exception e) {
			e.printStackTrace();
			dbpool.rollbackTransaction();
		}finally{
			dbpool.close();
		}
	}
	/**
	 * 修改flag状态值，用于判断是删除状态
	 * @param dbpoolJT
	 * @param p_custList
	 * @throws Exception 
	 */
	public void updateFalg(DbPool dbpoolJT, ArrayList<PCustSchema> p_CUSTSchemaList) throws Exception {
		String flag = "0";
		String updateSQL = "UPDATE tb_cust SET flag = ? WHERE custId = ? ";
		String custId = "";
		if (p_CUSTSchemaList!=null) {
			for (PCustSchema p_CUSTSchema : p_CUSTSchemaList) {
				if("105".equals(p_CUSTSchema.getENDORTYPE())){
					custId = p_CUSTSchema.getINSUREDID();
				}else{
					custId = p_CUSTSchema.getCUSTID();
				}
					dbpoolJT.prepareInnerStatement(updateSQL);
					dbpoolJT.setString(1, flag);
					dbpoolJT.setString(2, custId);
			    	dbpoolJT.executePreparedUpdate();
			}
		}
	}	
}
