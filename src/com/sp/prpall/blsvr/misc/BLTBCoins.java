package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBTBCoins;
import com.sp.prpall.schema.TBCoinsSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

public class BLTBCoins {
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */
    public BLTBCoins(){
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
     *增加一条PrpTcoinsSchema记录
     *@param iPrpTcoinsSchema PrpTcoinsSchema
     *@throws Exception
     */
    public void setArr(TBCoinsSchema iPrpTcoinsSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpTcoinsSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpTcoinsSchema记录
     *@param index 下标
     *@return 一个PrpTcoinsSchema对象
     *@throws Exception
     */
    public TBCoinsSchema getArr(int index) throws Exception
    {
    	TBCoinsSchema prpTcoinsSchema = null;
       try
       {
        prpTcoinsSchema = (TBCoinsSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpTcoinsSchema;
     }
    /**
     *删除一条PrpTcoinsSchema记录
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
       this.query(iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBTBCoins dbPrpTcoins = new DBTBCoins();
      if (iLimitCount > 0 && dbPrpTcoins.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpTcoins.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM tb_coins WHERE " + iWherePart;
        schemas = dbPrpTcoins.findByConditions(strSqlStatement);
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
       this.query(dbpool,iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBTBCoins dbPrpTcoins = new DBTBCoins();
      if (iLimitCount > 0 && dbPrpTcoins.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpTcoins.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM tb_coins WHERE " + iWherePart;
        schemas = dbPrpTcoins.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
    	DBTBCoins dbPrpTcoins = new DBTBCoins();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpTcoins.setSchema((TBCoinsSchema)schemas.get(i));
      dbPrpTcoins.insert(dbpool);
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

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        save(dbpool);
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
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param iProposalNo XX单号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iProposalNo) throws Exception
    {

    	String strSqlStatement = " DELETE FROM tb_coins WHERE PolicyNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iProposalNo);
	    dbpool.executePreparedUpdate();
	    dbpool.closePreparedStatement();

    }

    /**
     * 不带dbpool的删除方法
     *@param iProposalNo XX单号
     *@return 无
     */
    public void cancel(String iProposalNo ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,iProposalNo);
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
     * 带dbpool根据XX单号获取数据
     *@param iProposalNo XX单号
     *@return 无
     */
    public void getData(String iProposalNo) throws Exception
    {
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            getData(dbpool, iProposalNo);
        } catch (Exception e) {
        } finally {
            dbpool.close();
        }
    }

    /**
     * 不带dbpool根据XX单号获取数据
     *@param dbpool 连接池
     *@param iProposalNo XX单号
     *@return 无
     */
    public void getData(DbPool dbpool,String iProposalNo) throws Exception
    {
        String strWherePart = " PolicyNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iProposalNo);
        query(dbpool, strWherePart, arrWhereValue, 0);
    }

    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
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
        DBTBCoins dbPrpTcoins = new DBTBCoins();
        if (iLimitCount > 0 && dbPrpTcoins.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLTBCoins.java");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM tb_coins WHERE " + iWherePart;
            schemas = dbPrpTcoins.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
  
    /**
     *@desc 显示手续费/特殊因子费我方承担比例
     *@param iCoinsFlag 联共XXXXX类型：0/1/2/3/4 非/主共/从共/主联/从联
     *@param iFlag 标志：1/2 手续费/特殊因子费
     *@throws UserException
     *@throws Exception
     */
    public String showSelfRate(String iCoinsFlag, String iFlag)
    throws UserException,Exception {
        String strProportionFlag = "";
        String strSelfRate = "100";

        int i = 0;

        if(iCoinsFlag.equals("1")||iCoinsFlag.equals("3")) {
            if (this.getSize() > 0 && iFlag.equals("1") && this.getArr(0).getProportionFlag().length() > 0) {
                strProportionFlag = this.getArr(0).getProportionFlag().substring(0, 1);
            } else if (this.getSize() > 0 && iFlag.equals("2") && this.getArr(0).getProportionFlag().length() > 1) {
                strProportionFlag = this.getArr(0).getProportionFlag().substring(1, 2);
            }
            for (i = 0; i < this.getSize(); i++) {
                if(this.getArr(i).getCoinsType().equals("1")) {
                    strSelfRate = this.getArr(i).getCoinsRate();
                    break;
                }
            }
            
            if (strProportionFlag.equals("1") || strProportionFlag.equals("2")) {
                strSelfRate = "100";
            }
        } else if(iCoinsFlag.equals("0")) {
            strSelfRate = "";
        }
        return strSelfRate;
    }

    /**
     *@desc 显示手续费、特殊因子费的计入方式
     *@param iCoinsFlag 联共XXXXX类型：0/1/2/3/4 非/主共/从共/主联/从联
     *@param iFlag 标志：1/2 手续费/特殊因子费
     *@throws UserException
     *@throws Exception
     */
    public String showProportionFlag(String iCoinsFlag, String iFlag)
    throws UserException, Exception {
        String strProportionFlag = "";

        if (this.getSize() > 0 && iFlag.equals("1") && this.getArr(0).getProportionFlag().length() > 0) {
            strProportionFlag = this.getArr(0).getProportionFlag().substring(0, 1);
        } else if (this.getSize() > 0 && iFlag.equals("2") && this.getArr(0).getProportionFlag().length() > 1) {
            strProportionFlag = this.getArr(0).getProportionFlag().substring(1, 2);
        }
        if (iCoinsFlag.equals("1") || iCoinsFlag.equals("3")) {
            if (strProportionFlag.equals("0")) {
                strProportionFlag = "份额计入";
            } else if (strProportionFlag.equals("1")) {
                strProportionFlag = "全额计入";
            } else if (strProportionFlag.equals("2")) {
                strProportionFlag = "全额承担";
            }
        } else if(!iCoinsFlag.equals("0")) {
            strProportionFlag = "份额计入";
        }
        return strProportionFlag;
    }

    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
