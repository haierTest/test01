package com.sp.prpall.blsvr.misc;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpRuleUndwrtInfo;
import com.sp.prpall.dbsvr.misc.DBTbItemDevice;
import com.sp.prpall.schema.PrpRuleUndwrtInfoSchema;

/**
 * （规则引擎返回核XXXXX前置信息,涉及调用点XXXXX客观信息库、XXXXX主观信息库、XXXXX自动特约）
 * 定义PrpRuleUndwrtInfo的BL类
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-01-06</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpRuleUndwrtInfo{
	
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpRuleUndwrtInfo(){
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
     *增加一条PrpRuleUndwrtInfoSchema记录
     *@param iPrpRuleUndwrtInfoSchema PrpRuleUndwrtInfoSchema
     *@throws Exception
     */
    public void setArr(PrpRuleUndwrtInfoSchema iPrpRuleUndwrtInfoSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpRuleUndwrtInfoSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    
    /**
     *得到一条PrpRuleUndwrtInfoSchema记录
     *@param index 下标
     *@return 一个PrpRuleUndwrtInfoSchema对象
     *@throws Exception
     */
    public PrpRuleUndwrtInfoSchema getArr(int index) throws Exception
    {
       PrpRuleUndwrtInfoSchema prpRuleUndwrtInfoSchema = null;
       try
       {
        prpRuleUndwrtInfoSchema = (PrpRuleUndwrtInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpRuleUndwrtInfoSchema;
     }
    
    /**
     *删除一条PrpRuleUndwrtInfoSchema记录
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
      DBPrpRuleUndwrtInfo dbPrpRuleUndwrtInfo = new DBPrpRuleUndwrtInfo();
      if (iLimitCount > 0 && dbPrpRuleUndwrtInfo.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpRuleUndwrtInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpRuleUndwrtInfo WHERE " + iWherePart; 
        schemas = dbPrpRuleUndwrtInfo.findByConditions(strSqlStatement, bindValues);
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
      DBPrpRuleUndwrtInfo dbPrpRuleUndwrtInfo = new DBPrpRuleUndwrtInfo();
      if (iLimitCount > 0 && dbPrpRuleUndwrtInfo.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpRuleUndwrtInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpRuleUndwrtInfo WHERE " + iWherePart; 
        schemas = dbPrpRuleUndwrtInfo.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpRuleUndwrtInfo dbPrpRuleUndwrtInfo = new DBPrpRuleUndwrtInfo();
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpRuleUndwrtInfo.setSchema((PrpRuleUndwrtInfoSchema)schemas.get(i));
      dbPrpRuleUndwrtInfo.insert(dbpool);
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
    	e.printStackTrace();
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
     *@param proposalNo ProposalNo
     *@param riskCode RiskCode
     *@param serialNo SerialNo
     *@return 无
     */
    public void cancel(DbPool dbpool, String proposalNo, String serialNo) throws Exception
    {
        String strSqlStatement = " DELETE FROM PrpRuleUndwrtInfo WHERE proposalNo=? AND serialNo=?";
      	dbpool.prepareInnerStatement(strSqlStatement);
      	dbpool.setString(1, proposalNo);
      	dbpool.setString(2, serialNo);
  	    dbpool.executePreparedUpdate();
  	    dbpool.closePreparedStatement();
    }
    
    /**
     * 带dbpool的删除方法
     *@param dbpool    连接池
     *@param proposalNo ProposalNo
     *@param riskCode RiskCode
     *@param serialNo SerialNo
     *@return 无
     */
    public void cancel(DbPool dbpool, String proposalNo) throws Exception
    {
        String strSqlStatement = " DELETE FROM PrpRuleUndwrtInfo WHERE proposalNo=?";
      	dbpool.prepareInnerStatement(strSqlStatement);
      	dbpool.setString(1, proposalNo);
  	    dbpool.executePreparedUpdate();
  	    dbpool.closePreparedStatement();
    }
      
    /**
     * 不带dbpool的删除方法
     *@param proposalNo ProposalNo
     *@param riskCode RiskCode
     *@param serialNo SerialNo
     *@return 无
     */
    public void cancel(String proposalNo,String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool, proposalNo,serialNo);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
    	e.printStackTrace();
        dbpool.rollbackTransaction();
      }
      finally
      {
        dbpool.close();
      }
    }
    
    
    /**
     * 不带dbpool的删除方法
     *@param proposalNo ProposalNo
     *@param riskCode RiskCode
     *@param serialNo SerialNo
     *@return 无
     */
    public void cancelPrpRule(String proposalNo,String riskCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancelPrpRule(dbpool, proposalNo,riskCode);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
    	e.printStackTrace();
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
     *@param proposalNo ProposalNo
     *@param riskCode RiskCode
     *@param serialNo SerialNo
     *@return 无
     */
    public void cancelPrpRule(DbPool dbpool, String proposalNo, String riskCode) throws Exception
    {
        String strSqlStatement = " DELETE FROM PrpRuleUndwrtInfo WHERE proposalNo=? AND riskCode=?";
      	dbpool.prepareInnerStatement(strSqlStatement);
      	dbpool.setString(1, proposalNo);
      	dbpool.setString(2, riskCode);
  	    dbpool.executePreparedUpdate();
  	    dbpool.closePreparedStatement();
    }
    
      
    /**
     * 不带dbpool根据主键获取数据
     *@param proposalNo ProposalNo
     *@param riskCode RiskCode
     *@param serialNo SerialNo
     *@return 无
     */
    public void getData(String proposalNo,String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        getData(dbpool,proposalNo,serialNo);
      }
      catch (Exception e)
      {
        e.printStackTrace();
        throw e;
      }
      finally
      {
        dbpool.close();
      }
    }
    
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param proposalNo ProposalNo
     *@param riskCode RiskCode
     *@param serialNo SerialNo
     *@return 无
     */
    public void getData(DbPool dbpool, String proposalNo,String serialNo) throws Exception
    {
      String strWherePart = "proposalNo=? AND serialNo=?";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(proposalNo);
      arrWhereValue.add(serialNo);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@author liuweichang 20150106
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
        DBPrpRuleUndwrtInfo dbPrpRuleUndwrtInfo = new DBPrpRuleUndwrtInfo();
        if (iLimitCount > 0 && dbPrpRuleUndwrtInfo.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpRuleUndwrtInfo.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpRuleUndwrtInfo WHERE " + iWherePart;
            schemas = dbPrpRuleUndwrtInfo.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param proposalNo ProposalNo
     *@return 无
     */
    public void getData(DbPool dbpool, String proposalNo) throws Exception
    {
      String strWherePart = " proposalNo=?";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(proposalNo);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    /**
     * @author liuweichang-ghq 20150112
     * 根据参数获取SerialNo最大值
     * @param proposalNo
     * @param riskCode
     * @return
     * @throws Exception
     */
    public int getMaxSerialNo(String proposalNo) throws Exception{
    	int intResult = 0;
        DBPrpRuleUndwrtInfo dbPrpRuleUndwrtInfo = new DBPrpRuleUndwrtInfo();
        DbPool dbpool = new DbPool();
        try
        {
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
          String strWherePart = "ProposalNo=? ";
          ArrayList arrWhereValue = new ArrayList();
          arrWhereValue.add(proposalNo);
          intResult = dbPrpRuleUndwrtInfo.queryMaxSerialNo(dbpool,strWherePart,arrWhereValue);
        }catch (Exception e){
        	e.printStackTrace();
        	throw e;
        }finally{
          dbpool.close();
        }
    	return intResult;
    }
    
    /**
     * @author liuweichang-ghq 20150112
     * XXXXX存规则返回的转人工提示
     * @param iListRuleInfo 转人工提示信息
     * @param iProposalNo XX单号
     * @param iRiskCode XXXXX种
     * @param iRuleFlag 接口标识(1客观信息库、2主观信息库、3自动特约)
     * @param iRemark  (1合规提示.2转人工提示3质检提示)
     * @throws Exception
     */
    public void saveRuleUndwrtInfo(List<String> iListRuleInfo,String iProposalNo,String iRiskCode,String iRuleFlag,String iRemark) throws Exception{
    	int maxSerialNo = getMaxSerialNo(iProposalNo)+1;
    	for(int i=0;i<iListRuleInfo.size();i++){
    		PrpRuleUndwrtInfoSchema prpRuleUndwrtInfoSchema = new PrpRuleUndwrtInfoSchema();
    		prpRuleUndwrtInfoSchema.setProposalNo(iProposalNo);
    		prpRuleUndwrtInfoSchema.setRiskCode(iRiskCode);
    		prpRuleUndwrtInfoSchema.setSerialNo(""+(maxSerialNo++));
    		prpRuleUndwrtInfoSchema.setUndwrtInfo(iListRuleInfo.get(i));
    		prpRuleUndwrtInfoSchema.setFlag(iRuleFlag);
    		prpRuleUndwrtInfoSchema.setRemark(iRemark);
    		this.setArr(prpRuleUndwrtInfoSchema);
    	}
    	this.save();
    }
    
    /**
     * @author zhangpengfei  20150603
     * 用于删除错误信息
     * @param iProposalNo XX单号/流水号
     * @param iRiskCode XXXXX种
     * @param iRuleFlag 接口标识(1客观信息库、2主观信息库、3自动特约)
     * @throws Exception
     */
    public void delete(String iProposalNo,String iRiskCode,String iRuleFlag) throws Exception
    {
    	DbPool dbpool = new DbPool();
        try
        {
      	    dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            dbpool.beginTransaction();
            String strSqlStatement = " DELETE FROM PrpRuleUndwrtInfo WHERE proposalNo=? AND serialNo=? AND flag=?";
        	dbpool.prepareInnerStatement(strSqlStatement);
        	dbpool.setString(1, iProposalNo);
        	dbpool.setString(2, iRiskCode);
        	dbpool.setString(3, iRuleFlag);
    	    dbpool.executePreparedUpdate();
    	    dbpool.closePreparedStatement();
            dbpool.commitTransaction(); 
        }
        catch (Exception e)
        {
      	e.printStackTrace();
          dbpool.rollbackTransaction();
        }
        finally
        {
          dbpool.close();
        }
    }
    public void deleteRuleUndwrtInfo(String iProposalNo,String iRiskCode,String iRuleFlag) throws Exception{
    	    this.delete(iProposalNo,iRiskCode,iRuleFlag);
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
