package com.sp.prpall.blsvr.jf;

import java.util.*;

import com.sp.utiall.dbsvr.DBPrpDagent;
import com.sp.utiall.dbsvr.DBPrpDcode;
import com.sp.utiall.dbsvr.DBPrpDuser;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.payment.dbsvr.jf.DBPrpJplanFee;
import com.sp.prpall.dbsvr.jf.DBPrpJplanPrint;
import com.sp.prpall.schema.PrpJplanPrintSchema;
import com.sp.prpall.pubfun.PaymentTransCode;

/**
 * 定义BLPrpJplanPrint的BL类
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>@createdate 2010-05-06</p>
 * @author BLGenerator
 * @version 1.0
 */ 
public class BLPrpJplanPrint{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpJplanPrint(){
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
     *增加一条PrpJplanPrintSchema记录
     *@param iPrpJplanPrintSchema PrpJplanPrintSchema
     *@throws Exception
     */
    public void setArr(PrpJplanPrintSchema iPrpJplanPrintSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJplanPrintSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpJplanPrintSchema记录
     *@param index 下标
     *@return 一个PrpJplanPrintSchema对象
     *@throws Exception
     */
    public PrpJplanPrintSchema getArr(int index) throws Exception
    {
     PrpJplanPrintSchema PrpJplanPrintSchema = null;
       try
       {
        PrpJplanPrintSchema = (PrpJplanPrintSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return PrpJplanPrintSchema;
     }
    /**
     *删除一条PrpJplanPrintSchema记录
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
       this.query(iWherePart, 0, bindValues);
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
      DBPrpJplanPrint dbPrpJplanPrint = new DBPrpJplanPrint();
      if (iLimitCount > 0 && dbPrpJplanPrint.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJplanPrint.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJplanPrint WHERE " + iWherePart; 
        schemas = dbPrpJplanPrint.findByConditions(strSqlStatement, bindValues);
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
       this.query(dbpool,iWherePart,0,bindValues);
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
      DBPrpJplanPrint dbPrpJplanPrint = new DBPrpJplanPrint();
      if (iLimitCount > 0 && dbPrpJplanPrint.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJplanPrint.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJplanPrint WHERE " + iWherePart; 
        schemas = dbPrpJplanPrint.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJplanPrint dbPrpJplanPrint = new DBPrpJplanPrint();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
			dbPrpJplanPrint.setSchema((PrpJplanPrintSchema) schemas.get(i));
			dbPrpJplanPrint.insert(dbpool);
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
      
      dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
      
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
     * XX发票、手续费支付单、赔款支付单打印查询 SangMingqian 20051121
     * @param iWherePart
     * @param iLimitCount
     * @throws UserException
     * @throws Exception
     */
    public void queryPrint(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJplanPrint dbPrpJplanPrint = new DBPrpJplanPrint();
      if (iLimitCount > 0 && dbPrpJplanPrint.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJplanPrint.queryPrint");
      }else{
        initArr();
        strSqlStatement = " SELECT PrpJplanPrint.* FROM PrpJplanPrint where"
            + iWherePart;
        schemas = dbPrpJplanPrint.findByConditions(strSqlStatement);
      }
    }

    /**
     * XX发票、手续费支付单、赔款支付单打印查询. 
     * <br><b>绑定参数版</b>
     * 
     * @param iWherePart
     * @param iLimitCount
     * @param bindValues 待绑定的参数值
     * @throws UserException
     * @throws Exception
     */
    public void queryPrint(String iWherePart,int iLimitCount,ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJplanPrint dbPrpJplanPrint = new DBPrpJplanPrint();
      if (iLimitCount > 0 && dbPrpJplanPrint.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJplanPrint.queryPrint");
      }else{
        initArr();
        strSqlStatement = " SELECT PrpJplanPrint.* FROM PrpJplanPrint where" + iWherePart;
        
    
        schemas = dbPrpJplanPrint.findByConditions(strSqlStatement,bindValues);
        
      }
    }
    
    /**
     * @author zhanglingjian 20070718
     * @param BLPrpJplanPrint
     * @param isChinese
     * @return BLPrpJplanPrint
     * @throws Exception
     */
    public void translateCode(boolean isChinese) throws Exception{
        DbPool dbpool = new DbPool();
        
        String strDataSource =SysConfig.CONST_PAYMENTDATASOURCE;
        

        try{
            
            dbpool.open(strDataSource);
            this.translateCode(dbpool,isChinese);
        }
        catch(Exception exception){
            throw exception;
        }
        finally{
            dbpool.close();
        }
    }
    
    /**
     * @author zhanglingjian 20070718
     * @param BLPrpJplanPrint
     * @param isChinese
     * @return BLPrpJplanPrint
     * @throws Exception
     */
    public void translateCode(DbPool dbpool,boolean isChinese) throws Exception{
        HashMap hm = new HashMap();
        for(int i = 0;i < this.getSize();i++)
        {
            
            if(hm.containsKey(this.getArr(i).getAgentCode()))
            {
                String tempAgentName =(String)hm.get(this.getArr(i).getAgentCode());
                this.getArr(i).setAgentName(tempAgentName);
            }
            else
            {
                String tempAgentName =  this.translateCode(dbpool,this.getArr(i).getAgentCode(),isChinese,1);
                this.getArr(i).setAgentName(tempAgentName);
                hm.put(this.getArr(i).getAgentCode(),tempAgentName);
            }
            
            if(hm.containsKey(this.getArr(i).getHandler1Code()))
            {
                String tempHandler1Name =(String)hm.get(this.getArr(i).getHandler1Code());
                this.getArr(i).setHandler1Name(tempHandler1Name);
            }
            else
            {
                String tempHandler1Name = this.translateCode(dbpool,this.getArr(i).getHandler1Code(),isChinese,2);
                this.getArr(i).setHandler1Name(tempHandler1Name);
                hm.put(this.getArr(i).getHandler1Code(),tempHandler1Name);
            }
            
            if(hm.containsKey(this.getArr(i).getPayRefReason()))
            {
                String tempPayRefReason =(String)hm.get(this.getArr(i).getPayRefReason());
                this.getArr(i).setPayRefReasonName(tempPayRefReason);
            }
            else
            {   
                String tempPayRefReason = this.translateCode(dbpool,this.getArr(i).getPayRefReason(),isChinese,3);
                this.getArr(i).setPayRefReasonName(tempPayRefReason);
                hm.put(this.getArr(i).getPayRefReason(),tempPayRefReason);
            }
        }
    }
    /**
     * @author zhanglingjian 20070718
     * @param dbpool
     * @param code
     * @param isChinese
     * @param type
     * @return String
     * @throws Exception
     */
    public String translateCode(DbPool dbpool,String code,boolean isChinese,int type)
    throws UserException,Exception
    {
        if(code.equals("")) return ""; 
        DBPrpDagent dbPrpDagent = new DBPrpDagent();
        DBPrpDcode dbPrpDcode = new DBPrpDcode();
        DBPrpDuser dbPrpDuser = new DBPrpDuser();
        
        if(type == 1)
        {
            dbPrpDagent.getInfo(dbpool,code);
            if(isChinese)
                  return dbPrpDagent.getAgentName();
                else
                  return dbPrpDagent.getAgentName();
        }else if(type == 2)
        {
            
            dbPrpDuser.getInfo(dbpool,code);
            if (isChinese) {
                return dbPrpDuser.getUserName();
            } else {
                if (dbPrpDuser.getUserEName() == null || dbPrpDuser.getUserEName().equals("")) {
                    return dbPrpDuser.getUserName();
                } else {
                    return dbPrpDuser.getUserEName();
                }
            }
        }else if(type == 3)
        {
            
            dbPrpDcode.getInfo(dbpool,"PayRefReason", code);
            if (isChinese) {
                return dbPrpDcode.getCodeCName();
            } else {
                if (dbPrpDcode.getCodeEName()==null || dbPrpDcode.getCodeEName().equals("")) {
                    return dbPrpDcode.getCodeCName();
                } else {
                    return dbPrpDcode.getCodeEName();
                }
            }
        }
        return "";
    }
    
    
    /**
     * 相关组合产品查询(根据已知条件来查询与该记录相关的组合记录)
     * <br><b>绑定参数版</b>
     * @param iWherePart 查询条件
     * @param strcomSQL2 查询符合以上条件的相关的组合记录的条件
     * @param iLimitCount 记录数的限制
     * @param bindValues 待绑定的参数值
     * @throws UserException
     * @throws Exception
     */
    public void queryCombinRef(String iWherePart,String strcomSQL2,int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJplanPrint dbPrpJplanPrint = new DBPrpJplanPrint();
      initArr();
      strSqlStatement = "SELECT * from PrpJplanPrint " +strcomSQL2
                         + " and ContractNo in (SELECT ContractNo from PrpJplanPrint WHERE "
                         + iWherePart
                         + " ) and ROWNUM<=1000 order by contractno,PayNo,certino,policyNo ";
      schemas = dbPrpJplanPrint.findByConditions(strSqlStatement, bindValues);
    }
    
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *效率优化条件改变，修改query方法
     *<br><b>绑定参数版</b>
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@param bindValues 传入的绑定参数，如果为null，使用非绑定参数的方式
     *@throws UserException
     *@throws Exception
     */
    public void queryByLimit(String iWherePart,int iLimitCount,ArrayList bindValues) throws UserException,Exception
    {
        String strSqlStatement = "";
        String strOthWherePart =" ORDER BY CertiNo,PolicyNo,PayNo,SerialNo DESC";
        DBPrpJplanPrint dbPrpJplanPrint = new DBPrpJplanPrint();

        initArr();
        strSqlStatement = " SELECT * FROM PrpJplanPrint WHERE " + iWherePart+" and ROWNUM<=1000"+strOthWherePart;
        schemas = dbPrpJplanPrint.findByConditions(strSqlStatement,bindValues);
    }
    
    /**
     *代码翻译
     */
    public void transCode(){
    	
    	PaymentTransCode paymentTransCode = new PaymentTransCode();
    	
    	try {
    		paymentTransCode.transCode("prpduser","prpjplanprint",this.schemas);
    		paymentTransCode.transCode("prpdcode","prpjplanprint",this.schemas);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    }
    
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
