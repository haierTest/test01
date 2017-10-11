package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpBankToCompany;
import com.sp.prpall.schema.PrpBankToCompanySchema;

/**
 * 定义PrpBankToCompany的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-07-07</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 */
public class BLPrpBankToCompany{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpBankToCompany(){
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
     *增加一条PrpBankToCompanySchema记录
     *@param iPrpBankToCompanySchema PrpBankToCompanySchema
     *@throws Exception
     */
    public void setArr(PrpBankToCompanySchema iPrpBankToCompanySchema) throws Exception
    {
      try
      {
        schemas.add(iPrpBankToCompanySchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpBankToCompanySchema记录
     *@param index 下标
     *@return 一个PrpBankToCompanySchema对象
     *@throws Exception
     */
    public PrpBankToCompanySchema getArr(int index) throws Exception
    {
      PrpBankToCompanySchema prpBankToCompanySchema = null;
       try
       {
         prpBankToCompanySchema = (PrpBankToCompanySchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpBankToCompanySchema;
     }
    /**
     *删除一条PrpBankToCompanySchema记录
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
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart) throws UserException,Exception
    {
      this.query(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpBankToCompany dbPrpBankToCompany = new DBPrpBankToCompany();
      if (iLimitCount > 0 && dbPrpBankToCompany.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpBankToCompany.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpBankToCompany WHERE " + iWherePart; 
        schemas = dbPrpBankToCompany.findByConditions(strSqlStatement);
      }
    }
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart) throws UserException,Exception
    {
      this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpBankToCompany dbPrpBankToCompany = new DBPrpBankToCompany();
      if (iLimitCount > 0 && dbPrpBankToCompany.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpBankToCompany.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpBankToCompany WHERE " + iWherePart; 
        schemas = dbPrpBankToCompany.findByConditions(dbpool,strSqlStatement);
      }
    }
    /**
     * 根据查询条件，查询SysConfig.CONST_SUNQUERYDATASOURCE数据源对应的数据.并将这组记录赋给schemas对象
     * @param iWherePart 查询条件
     * @param intPageNum 页码
     * @param intLineNumPage 每页条数
     * @return 无
     * @throws Exception
     */
    public void query(String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
    {
      try {
        this.query(iWherePart,intPageNum,intLineNumPage,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
      }
      catch (Exception e)
      {
        throw e;
      }
    }
    
    /**
     *按照查询条件得到一组记录数，用于翻页查询，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(不包括排序字句)
     *@param intPageNum 页码
     *@param intLineNumPage 每页条数
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@return 无
     *@throws Exception
     */
    public void query(String iWherePart,int intPageNum,int intLineNumPage,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      int intStartNum = 0;
      int intEndNum = 0;
      
      intStartNum = (intPageNum - 1) * intLineNumPage;
      
      intEndNum = intPageNum * intLineNumPage;

      DBPrpBankToCompany dbPrpBankToCompany = new DBPrpBankToCompany();
      if (iLimitCount > 0 && dbPrpBankToCompany.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpBankToCompany.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM ( " +
         "Select RowNum As LineNum,T.* From ( " +
         "Select * From PrpBankToCompany Where " + iWherePart +
         ") T Where RowNum<=" + intEndNum + ") Where LineNum>" +
         intStartNum ;
        schemas = dbPrpBankToCompany.findByConditions(strSqlStatement);
      }
    }
    /**
     *带dbpool的save方法 
     *@param 无 
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpBankToCompany dbPrpBankToCompany = new DBPrpBankToCompany();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpBankToCompany.setSchema((PrpBankToCompanySchema)schemas.get(i));
        dbPrpBankToCompany.insert(dbpool);
      }
    }
      
    /**
     *不带dbpool的XXXXX存方法
     *@param 无
     *@return 无
     */
    public void save() throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        save(dbpool);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally
      {
        dbpool.close();
      }
    }
    /**
     *带dbpool的update方法
     *@param 无
     *@return 无
     */
    public void update(DbPool dbpool) throws Exception
    {
    	DBPrpBankToCompany dbPrpBankToCompany = new DBPrpBankToCompany();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
    	 dbPrpBankToCompany.setSchema((PrpBankToCompanySchema)schemas.get(i));
    	 dbPrpBankToCompany.update(dbpool);
      }
    }

    /**
     *不带dbpool的更新方法
     *@param 无
     *@return 无
     */
    public void update() throws Exception
    {
      DbPool dbpool = new DbPool();

      dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));

      try
      {
        dbpool.beginTransaction();
        update(dbpool);
        dbpool.commitTransaction();
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally
      {
        dbpool.close();
      }
    } 
    /**
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param iBankSeqNo BankSeqNo
     *@return 无
     */
    public void cancel(DbPool dbpool,String iBankSeqNo ,String iSerialNo,String riskCode) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpBankToCompany WHERE BankSeqNo= '" + iBankSeqNo + "' and SerialNo='"+iSerialNo +  "' and riskCode='"+riskCode+"'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iBankSeqNo BankSeqNo
     *@return 无
     */
    public void cancel(String iBankSeqNo ,String iSerialNo,String riskCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iBankSeqNo,iSerialNo,riskCode);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     * 带dbpool根据BankSeqNo获取数据
     *@param iBankSeqNo BankSeqNo
     *@return 无
     */
    public void getData(String iBankSeqNo,String iSerialNo,String riskCode) throws Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			getData(dbpool, iBankSeqNo, iSerialNo,riskCode);
		} catch (Exception e) {
			
		} finally {
			dbpool.close();
		}
	}
      
    /**
	 * 不带dbpool根据BankSeqNo获取数据
	 * 
	 * @param dbpool
	 *            连接池
	 * @param iBankSeqNo
	 *            BankSeqNo
	 * @return 无
	 */
    public void getData(DbPool dbpool,String iBankSeqNo,String iSerialNo,String riskCode) throws Exception
    {
      String strWherePart = "";
      strWherePart = " BankSeqNo = ? and SerialNo = ? and riskCode = ?";
      ArrayList arrWhereValue = new ArrayList(2);
      arrWhereValue.add(iBankSeqNo);
      arrWhereValue.add(iSerialNo);
      arrWhereValue.add(riskCode);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    public void getDataByProposalNo(String iProposalNo) throws Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			getDataByProposalNo(dbpool, iProposalNo);
		} catch (Exception e) {
			
		} finally {
			dbpool.close();
		}
	}
    public void getDataByProposalNo(DbPool dbpool, String iProposalNo) throws Exception {
		String strWherePart = " ProposalNo= ? ";
		ArrayList arrWhereValue = new ArrayList(1);
		arrWhereValue.add(iProposalNo);
		query(dbpool, strWherePart, arrWhereValue, 0);
	}
    public void getDataByPolicyNo(String iPolicyNo) throws Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			getDataByPolicyNo(dbpool, iPolicyNo);
		} catch (Exception e) {
			
		} finally {
			dbpool.close();
		}
	}

	public void getDataByPolicyNo(DbPool dbpool, String iPolicyNo) throws Exception {
		String strWherePart = " PolicyNo= ? ";
		ArrayList arrWhereValue = new ArrayList();
		arrWhereValue.add(iPolicyNo);
		query(dbpool, strWherePart, arrWhereValue, 0);
	}

    /**
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool
	 *            全局池
	 * @param iWherePart
	 *            查询条件,传入条件均已绑定变量形式，问号个数与属性值个数一致
	 * @param iWhereValue
	 *            查询条件各字段值
	 * @param iLimitCount
	 *            记录数限制(iLimitCount=0: 无限制)
	 * @throws UserException
	 * @throws Exception
	 */
    public void query(DbPool dbpool, String iWherePart, ArrayList iWhereValue, int iLimitCount) throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpBankToCompany dbPrpBankToCompany = new DBPrpBankToCompany();
		if (iLimitCount > 0 && dbPrpBankToCompany.getCount(dbpool, iWherePart, iWhereValue) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpBankToCompany.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM prpBankToCompany WHERE " + iWherePart;
			schemas = dbPrpBankToCompany.findByConditions(dbpool, strSqlStatement, iWhereValue);
		}
	}
    /**
	 * 主函数
	 * 
	 * @param args
	 *            参数列表
	 */
    public static void main(String[] args){
        
    }
}