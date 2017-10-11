package com.sp.prpall.blsvr.misc;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.prpall.dbsvr.misc.DBPrpMaterial;
import com.sp.prpall.schema.PrpMaterialSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义PrpMaterial的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-11-24</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 *              5.getData方法新增try、catch、finally异常处理;
 */
public class BLPrpMaterial{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpMaterial(){
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
	 * 增加一条PrpMaterialSchema记录
	 * 
	 * @param iPrpMaterialSchema
	 *            PrpMaterialSchema
	 * @throws Exception
	 */
    public void setArr(PrpMaterialSchema iPrpMaterialSchema) throws Exception {
		try {
			schemas.add(iPrpMaterialSchema);
		} catch (Exception e) {
			throw e;
		}
	}
    /**
	 * 得到一条PrpMaterialSchema记录
	 * 
	 * @param index
	 *            下标
	 * @return 一个PrpMaterialSchema对象
	 * @throws Exception
	 */
    public PrpMaterialSchema getArr(int index) throws Exception {
		PrpMaterialSchema prpMaterialSchema = null;
		try {
			prpMaterialSchema = (PrpMaterialSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpMaterialSchema;
	}
    /**
	 * 删除一条PrpMaterialSchema记录
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
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount) throws UserException,
			Exception {
		String strSqlStatement = "";
		DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();
		if (iLimitCount > 0 && dbPrpMaterial.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpMaterial.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpMaterial WHERE " + iWherePart;
			schemas = dbPrpMaterial.findByConditions(strSqlStatement);
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
		DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();
		if (iLimitCount > 0 && dbPrpMaterial.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpMaterial.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpMaterial WHERE " + iWherePart;
			schemas = dbPrpMaterial.findByConditions(dbpool, strSqlStatement);
		}
	}
    /**
     * 按照查询条件和记录限制数，查询条数得到一组记录数，并将这组记录赋给schemas对象
     * @param strSQL        sql语句
     * @param rowNumCount   查询条数
     * @param iLimitCount   记录限制数(iLimitCount=0: 无限制)
     * @throws UserException
     * @throws Exception
     * @author Administrator fanjiangtao 2013-12-19
     */
    public void query(String strSQL,int rowNumCount,int iLimitCount)throws UserException, Exception{
        String strSqlStatement = "";
        if(iLimitCount > 0 && rowNumCount > iLimitCount){
            throw new UserException(-98, -1003, "BLPrpMaterial.query");
        }else{
            initArr();
            strSqlStatement = strSQL +rowNumCount;
            DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();
            schemas = dbPrpMaterial.findByConditions(strSqlStatement);
        }
    }
    /**
     * 按照查询条件和记录限制数，查询条数得到一组记录数，并将这组记录赋给schemas对象
     * @param dbpool            全局池 
     * @param strSQL            sql语句
     * @param rowNumCount       查询条数
     * @param iLimitCount       记录限制数(iLimitCount=0: 无限制)
     * @throws UserException
     * @throws Exception
     * @author Administrator    fanjingtao 20131219
     */
    public void query(DbPool dbpool,String strSQL,int rowNumCount,int iLimitCount)throws UserException, Exception{
        String strSqlStatement = "";
        if(iLimitCount > 0 && rowNumCount > iLimitCount){
            throw new UserException(-98, -1003, "BLPrpMaterial.query");
        }else{
            initArr();
            strSqlStatement = strSQL +rowNumCount;
            DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();
            schemas = dbPrpMaterial.findByConditions(dbpool,strSqlStatement);
        }
    }
      
    /**
	 * 带dbpool的save方法
	 * 
	 * @param 无
	 * @return 无
	 */
    public void save(DbPool dbpool) throws Exception {
		DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();

		int i = 0;

		for (i = 0; i < schemas.size(); i++) {
			dbPrpMaterial.setSchema((PrpMaterialSchema) schemas.get(i));
			dbPrpMaterial.insert(dbpool);
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
	 * @param iBusinessNo
	 *            BusinessNo
	 * @return 无
	 */
    public void cancel(DbPool dbpool, String iBusinessNo) throws Exception {
		String strSqlStatement = "";

		strSqlStatement = " DELETE FROM PrpMaterial WHERE BusinessNo= '"
				+ iBusinessNo + "'";

		dbpool.delete(strSqlStatement);
	}
      
    /**
	 * 不带dbpool的删除方法
	 * 
	 * @param iBusinessNo
	 *            BusinessNo
	 * @return 无
	 */
    public void cancel(String iBusinessNo) throws Exception {
		DbPool dbpool = new DbPool();

		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			dbpool.beginTransaction();
			cancel(dbpool, iBusinessNo);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}
      
    /**
	 * 带dbpool根据BusinessNo获取数据
	 * 
	 * @param iBusinessNo
	 *            BusinessNo
	 * @return 无
	 */
    public void getData(String iBusinessNo) throws Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			getData(dbpool, iBusinessNo);
		} catch (Exception e) {
			
		} finally {
			dbpool.close();
		}
	}
      
    /**
	 * 不带dbpool根据BusinessNo获取数据
	 * 
	 * @param dbpool
	 *            连接池
	 * @param iBusinessNo
	 *            BusinessNo
	 * @return 无
	 */
    public void getData(DbPool dbpool, String iBusinessNo) throws Exception {
		String strWherePart = "";
		strWherePart = "BusinessNo= '" + iBusinessNo + "'";
		query(dbpool, strWherePart, 0);
	}
    /**
     * 无序查询 不带dbpool
     * @author wangchuanzhong 20101125
     * @param iWherePart 查询条件
     * @param intPageNum 页码
     * @param intLineNumPage 每页条数
     * @return 无
     * @throws Exception
     */
    public void queryWithoutOrder(String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        this.queryWithoutOrder(dbpool,iWherePart,intPageNum,intLineNumPage);
      }catch (Exception e)
      {
        throw e;
      }finally {
        dbpool.close();
      }
    }
    /**
     * 无序查询 带dbpool
     * @author wangchuanzhong 20101125
     * @param dbpool
     * @param iWherePart 查询条件
     * @param intPageNum 页码
     * @param intLineNumPage 每页条数
     * @return 无
     * @throws Exception
     */
     public void queryWithoutOrder(DbPool dbpool,String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
     {
       StringBuffer strSqlStatement = new StringBuffer();
       int intStartNum = 0;
       int intEndNum = 0;

       intStartNum = (intPageNum - 1) * intLineNumPage;
       intEndNum = intPageNum * intLineNumPage;

       DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();
       initArr();
       
       strSqlStatement.append(" SELECT * FROM ( ");
       strSqlStatement.append("Select RowNum As LineNum,M.* From ( ");
       strSqlStatement.append("Select * From PrpMaterial PM Where ");
       strSqlStatement.append(iWherePart);
       strSqlStatement.append(" And PM.SerialNo =(Select MAX(Serialno) From PrpMaterial inPM Where inPM.BusinessNo = PM.BusinessNo)");
       strSqlStatement.append(") M Where RowNum<=");
       strSqlStatement.append(intEndNum);
       strSqlStatement.append(") Where LineNum>");
       strSqlStatement.append(intStartNum);
       
       schemas = dbPrpMaterial.findByConditions(dbpool,strSqlStatement.toString());
     }
     
     
     /**
      * 有序查询 带dbpool
      * @author ronglijun 20140617
      * @param dbpool
      * @param iWherePart 查询条件
      * @param intPageNum 页码
      * @param intLineNumPage 每页条数
      * @return 无
      * @throws Exception
      */
      public void queryByOrderNew(DbPool dbpool,String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
      {
        StringBuffer strSqlStatement = new StringBuffer();
        int intStartNum = 0;
        int intEndNum = 0;
        intStartNum = (intPageNum - 1) * intLineNumPage;
        intEndNum = intPageNum * intLineNumPage;
        DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();
        initArr();
        strSqlStatement.append(" SELECT x.* from( ");
        strSqlStatement.append("SELECT z.*,rownum numbers from( ");
        strSqlStatement.append("select * from PrpMaterial where ");
        strSqlStatement.append(iWherePart);
        strSqlStatement.append(" order by Serialno ) z  ");
        strSqlStatement.append("  Where RowNum<=");
        strSqlStatement.append(intEndNum);
        strSqlStatement.append(") x Where x.numbers>");
        strSqlStatement.append(intStartNum);
        schemas = dbPrpMaterial.findByConditions(dbpool,strSqlStatement.toString());
      }

     
 
     /**
      * y序查询 带dbpool
      * @author ronglijun 20101125
      * @param dbpool
      * @param iWherePart 查询条件
      * @param intPageNum 页码
      * @param intLineNumPage 每页条数
      * @return 无
      * @throws Exception
      */
      public void queryByOrder(String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
      {  
          DbPool dbpool = new DbPool();
          try {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            this.queryByOrderNew(dbpool,iWherePart,intPageNum,intLineNumPage);
          }catch (Exception e)
          {
            throw e;
          }finally {
            dbpool.close();
          }
      }
      
     
     /**
      * 不带dbpool的更新方法
      *@return 无
      */
     public void update() throws Exception
     {
       DbPool dbpool = new DbPool();

         try {
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
         dbpool.beginTransaction();
         update(dbpool);
         dbpool.commitTransaction();
       }
       catch (Exception e)
       {
         dbpool.rollbackTransaction();
       }
       finally {
         dbpool.close();
       }
     }
       /**
       *带dbpool的更新方法
       *@param dbpool    连接池
       *@return 无
       */
      public void update(DbPool dbpool) throws Exception
      {
    	  DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();

        int i = 0;

        for(i = 0; i< schemas.size(); i++)
        {
        	dbPrpMaterial.setSchema((PrpMaterialSchema)schemas.get(i));
          dbPrpMaterial.update(dbpool);
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
