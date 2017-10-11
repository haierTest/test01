package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.sp.prpall.dbsvr.jf.DBPrpJVsaccountInfo;
import com.sp.prpall.schema.PrpJVsaccountInfoSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.visa.bl.action.domain.BLVsaccountinfoAction;

/**
 * 定义收付激活卡单证表的BL类
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-12-26</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJVsaccountInfo{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpJVsaccountInfo(){
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
     *增加一条PrpJVsaccountInfoSchema记录
     *@param iPrpJVsaccountInfoSchema PrpJVsaccountInfoSchema
     *@throws Exception
     */
    public void setArr(PrpJVsaccountInfoSchema iPrpJVsaccountInfoSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJVsaccountInfoSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpJVsaccountInfoSchema记录
     *@param index 下标
     *@return 一个PrpJVsaccountInfoSchema对象
     *@throws Exception
     */
    public PrpJVsaccountInfoSchema getArr(int index) throws Exception
    {
     PrpJVsaccountInfoSchema prpJVsaccountInfoSchema = null;
       try
       {
        prpJVsaccountInfoSchema = (PrpJVsaccountInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJVsaccountInfoSchema;
     }
    /**
     *删除一条PrpJVsaccountInfoSchema记录
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
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
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
    public void query(String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJVsaccountInfo dbPrpJVsaccountInfo = new DBPrpJVsaccountInfo();
      if (iLimitCount > 0 && dbPrpJVsaccountInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJVsaccountInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJVsaccountInfo WHERE " + iWherePart; 
        schemas = dbPrpJVsaccountInfo.findByConditions(strSqlStatement, bindValues);
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
    public void query(DbPool dbpool, String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
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
    public void query(DbPool dbpool, String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJVsaccountInfo dbPrpJVsaccountInfo = new DBPrpJVsaccountInfo();
      if (iLimitCount > 0 && dbPrpJVsaccountInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJVsaccountInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJVsaccountInfo WHERE " + iWherePart; 
        schemas = dbPrpJVsaccountInfo.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJVsaccountInfo dbPrpJVsaccountInfo = new DBPrpJVsaccountInfo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJVsaccountInfo.setSchema((PrpJVsaccountInfoSchema)schemas.get(i));
      dbPrpJVsaccountInfo.insert(dbpool);
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
      
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      
      try
      {
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
     * 带dbpool的删除方法
     *@param dbpool    连接池
     *@param serialNo 序号
     *@return 无
     */
    public void cancel(DbPool dbpool, String serialNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpJVsaccountInfo WHERE serialNo='" + serialNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param serialNo 序号
     *@return 无
     */
    public void cancel(String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, serialNo);
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
     * 不带dbpool根据主键获取数据
     *@param serialNo 序号
     *@return 无
     */
    public void getData(String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      getData(dbpool, serialNo);
      dbpool.close();
    }
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param serialNo 序号
     *@return 无
     */
    public void getData(DbPool dbpool, String serialNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "serialNo='" + serialNo + "'";
       query(dbpool, strWherePart, 0, null);
    }
    
    /**
     * 将预收款信息存储到备用表
     *@param listAccount 存储的预收款信息
     *@return 无
     */
    public void dorecevieeAccount(List<Map> listAccount) throws Exception {
		String visaCode = "";
		String comCode = "";
		String payDate = "";
		String payOperatorId = "";
		String account = "";
		String proposalNo = "";
		BLVsaccountinfoAction blVsaccountinfoAction = new BLVsaccountinfoAction();
		DbPool dbpool=new DbPool();
		DBPrpJVsaccountInfo dbPrpJVsaccountInfo = null;
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			for (Map vsaccount : listAccount) {
				visaCode = (String) vsaccount.get("visaCode");
				if ((visaCode == null) || (visaCode.equals(""))) {
					throw new UserException(-98, -1200,
							"UIVsAccountInfoAction", "单证类型不能为空！");
				}
				account=(String) vsaccount.get("account");
				if (Integer.parseInt(account) == 0) {
					throw new UserException(-98, -1200,
							"UIVsAccountInfoAction", "预约库存量不能为空！");
				}

				proposalNo = (String) vsaccount.get("proposalNo");
				if ((proposalNo == "") || (proposalNo.equals(""))) {
					throw new UserException(-98, -1200,
							"UIVsAccountInfoAction", "XX单号不能为空！");
				}
				
				String strSequence = getIDSequence(dbpool);
				comCode = (String) vsaccount.get("comCode");
				payDate = (String) vsaccount.get("payDate");
				payOperatorId = (String) vsaccount.get("payOperatorId");
				dbPrpJVsaccountInfo = new DBPrpJVsaccountInfo();
				dbPrpJVsaccountInfo.setSerialNo(strSequence);
				dbPrpJVsaccountInfo.setPayDate(payDate);
				dbPrpJVsaccountInfo.setComCode(comCode);
				dbPrpJVsaccountInfo.setProposalNo(proposalNo);
				dbPrpJVsaccountInfo.setAcount(account);
				dbPrpJVsaccountInfo.setVsaCode(visaCode);
				dbPrpJVsaccountInfo.setFlag("0");
				dbPrpJVsaccountInfo.setPaypersonnel(payOperatorId);
				dbPrpJVsaccountInfo.insert(dbpool);
			}
			dbpool.commitTransaction();
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
	}
    
    /**
     * 获取主键序列
     * @param dbpool
     * @return
     * @throws Exception
     */
    public String getIDSequence(DbPool dbpool) 
    throws Exception{
       int strCount = 0;
       String strSQL = "";
       ResultSet resultSet = null;
       strSQL=" SELECT SEQ_PRPJVSACCOUNTINFO_SERIALNO.NEXTVAL FROM dual";
       try{
           resultSet = dbpool.query(strSQL);
           if(resultSet.next()){
               strCount = resultSet.getInt(1);
           }
           resultSet.close();
       }catch(Exception e){
           e.printStackTrace();
           throw e;
       } finally {
           if(resultSet != null)
               resultSet.close();          
       }
       return "V"+strCount;
   }
    
      
     
     
    public static void main(String[] args){	
    }
}
