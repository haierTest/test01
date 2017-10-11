package com.sp.prpall.blsvr.misc;

import java.util.HashMap;
import java.util.Vector;

import com.sp.payment.blsvr.jf.BLPrpJplanCommission;
import com.sp.payment.schema.PrpJplanCommissionSchema;
import com.sp.prpall.blsvr.jf.BLPrpQueryPaymentService;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.prpall.blsvr.pg.BLPrpPhead;
import com.sp.prpall.dbsvr.misc.DBPrpCPayablefee;
import com.sp.prpall.schema.PrpCPayablefeeSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义prpCPayablefee的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-11-13</p> 
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 *              5.getData方法新增try、catch、finally异常处理;
 */
public class BLPrpCPayablefee{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpCPayablefee(){
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
     *增加一条PrpCPayablefeeSchema记录
     *@param iPrpCPayablefeeSchema PrpCPayablefeeSchema
     *@throws Exception
     */
    public void setArr(PrpCPayablefeeSchema iPrpCPayablefeeSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpCPayablefeeSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpCPayablefeeSchema记录
     *@param index 下标
     *@return 一个PrpCPayablefeeSchema对象
     *@throws Exception
     */
    public PrpCPayablefeeSchema getArr(int index) throws Exception
    {
      PrpCPayablefeeSchema prpCPayablefeeSchema = null;
       try
       {
         prpCPayablefeeSchema = (PrpCPayablefeeSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpCPayablefeeSchema;
     }
    /**
     *删除一条PrpCPayablefeeSchema记录
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
      DBPrpCPayablefee dbPrpCPayablefee = new DBPrpCPayablefee();
      if (iLimitCount > 0 && dbPrpCPayablefee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCPayablefee.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCPayablefee WHERE " + iWherePart; 
        schemas = dbPrpCPayablefee.findByConditions(strSqlStatement);
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
      DBPrpCPayablefee dbPrpCPayablefee = new DBPrpCPayablefee();
      if (iLimitCount > 0 && dbPrpCPayablefee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCPayablefee.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCPayablefee WHERE " + iWherePart; 
        schemas = dbPrpCPayablefee.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpCPayablefee dbPrpCPayablefee = new DBPrpCPayablefee();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpCPayablefee.setSchema((PrpCPayablefeeSchema)schemas.get(i));
        dbPrpCPayablefee.insert(dbpool);
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
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param iPolicyNo XX号
     *@return 无
     */
    public void cancel(DbPool dbpool,String iPolicyNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpCPayablefee WHERE PolicyNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iPolicyNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iPolicyNo XX号
     *@return 无
     */
    public void cancel(String iPolicyNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iPolicyNo);
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
     * 带dbpool根据XX号获取数据
     *@param iPolicyNo XX号
     *@return 无
     */
    public void getData(String iPolicyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        getData(dbpool,iPolicyNo);
      }
      catch (Exception e)
      {
        
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     * 不带dbpool根据XX号获取数据
     *@param dbpool 连接池
     *@param iPolicyNo XX号
     *@return 无
     */
    public void getData(DbPool dbpool,String iPolicyNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "PolicyNo= '" + iPolicyNo + "'";
      query(dbpool,strWherePart,0);
    }
    
    /**
     *@author gaohaifeng 20101115
     *@description 不带dbpool的更新方法
     *@param dbpool 连接池
     *@return 无
     */
    public void update()throws Exception{
        DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            this.update(dbpool);
        } catch (Exception e) {
            dbpool.rollbackTransaction();
        } finally {
            dbpool.close();
        }
    }
    /**
     * @author gaohaifeng 20101115
     * @description 带dbpool的更新方法
     * @param dbpool连接池
     * @return 无
     */
    public void update(DbPool dbpool)throws Exception{
        DBPrpCPayablefee dbPrpCPayablefee = new DBPrpCPayablefee();
        for (int i = 0; i < schemas.size(); i++) {
            dbPrpCPayablefee.setSchema((PrpCPayablefeeSchema) schemas.get(i));
            dbPrpCPayablefee.update(dbpool);
        }
    }
    
    /**
     *@description 合规冲销时，更新手续费冲销表的处理。
     *             
     *@param dbpool 连接池
     *@param iPrpCPayablefeeSchema 应付手续费冲销对象
     *@return 无
     */
    public void updateNormal(BLEndorse blEndorse)throws Exception{
        DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            updateNormal(dbpool, blEndorse);
        } catch (Exception e) {
            throw e;
        } finally {
            dbpool.close();
        }
    }
    /**
     * @description 合规冲销时，更新手续费冲销表的处理。
     * @param dbpool连接池
     * @param iPrpCPayablefeeSchema应付手续费冲销对象
     * @return 无
     */
    public void updateNormal(DbPool dbpool,BLEndorse blEndorse)throws Exception{
    	
    	String strEndorseNo = blEndorse.getBLPrpPhead().getArr(0).getEndorseNo();
    	String strPolicyNo = blEndorse.getBLPrpPhead().getArr(0).getPolicyNo();
    	String strPayableFee = "";  
    	String strCanclePayFee = "";  
    	String strDisRate = blEndorse.getBLPrpCommission().getArr(0).getDisRate();  
    	String strOperateDate = blEndorse.getBLPrpPhead().getArr(0).getEndorDate();  
    	String strOperateCode = blEndorse.getBLPrpPhead().getArr(0).getOperatorCode();  
    	
    	BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
    	
    	HashMap map = new HashMap();
        String strSwitch = BLPrpQueryPaymentService.queryMirrorSwitch();
        if ("1".equals(strSwitch)){
            map=paySplitGetPayCommissionFee(strPolicyNo);
        }else{
            map=blPrpJplanCommission.getPayCommissionFee(strPolicyNo);
        }
        
    	strPayableFee = (String)map.get("PLAN_FEE");  
    	strCanclePayFee = (-1.0 * Double.parseDouble((String)map.get("PLAN_FEE")))+"";  
    	  
        DBPrpCPayablefee dbPrpCPayablefee = new DBPrpCPayablefee();
        int intCount = dbPrpCPayablefee.getInfo(dbpool, strPolicyNo);
        if(intCount ==100){
            throw new UserException(-98,-1003,"BLPrpCPayablefee.updateNormal");
        }
        this.schemas.clear();
        PrpCPayablefeeSchema prpCPayablefeeSchema = new PrpCPayablefeeSchema();
        prpCPayablefeeSchema.setSchema(dbPrpCPayablefee);
        prpCPayablefeeSchema.setEndorseno(strEndorseNo);
        prpCPayablefeeSchema.setPayableFee(strPayableFee);
        prpCPayablefeeSchema.setCanclePayFee(strCanclePayFee);
        prpCPayablefeeSchema.setDisrate(strDisRate);
        prpCPayablefeeSchema.setOperatedate(strOperateDate);
        prpCPayablefeeSchema.setOperatorcode(strOperateCode);
        prpCPayablefeeSchema.setFlag("1");
        
        this.setArr(prpCPayablefeeSchema);
        this.update(dbpool);

    }
    
    /**
     *@description 更新手续费冲销表,使其还原回默认状态
     *             
     *@param dbpool 连接池
     *@param iPrpCPayablefeeSchema 应付手续费冲销对象
     *@return 无
     */
    public void backToDefault(String strEndorseNo)throws Exception{
        DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            backToDefault(dbpool, strEndorseNo);
        } catch (Exception e) {
            throw e;
        } finally {
            dbpool.close();
        }
    }
    /**
     * @description 合规冲销时，更新手续费冲销表的处理。
     * @param dbpool连接池
     * @param iPrpCPayablefeeSchema应付手续费冲销对象
     * @return 无
     */
    public void backToDefault(DbPool dbpool,String strEndorseNo)throws Exception{
    	
    	BLPrpPhead blPrpPhead = new BLPrpPhead();
    	blPrpPhead.getData(dbpool, strEndorseNo);
    	
    	if(blPrpPhead.getSize()>0){
    		String strPolicyNo = blPrpPhead.getArr(0).getPolicyNo();

            DBPrpCPayablefee dbPrpCPayablefee = new DBPrpCPayablefee();
            int intCount = dbPrpCPayablefee.getInfo(dbpool, strPolicyNo);
            if(intCount == 0){
            	this.schemas.clear();
                PrpCPayablefeeSchema prpCPayablefeeSchema = new PrpCPayablefeeSchema();
                prpCPayablefeeSchema.setSchema(dbPrpCPayablefee);
                prpCPayablefeeSchema.setEndorseno("");
                prpCPayablefeeSchema.setPayableFee("");
                prpCPayablefeeSchema.setCanclePayFee("");
                prpCPayablefeeSchema.setDisrate("");
                prpCPayablefeeSchema.setOperatedate("");
                prpCPayablefeeSchema.setOperatorcode("");
                prpCPayablefeeSchema.setFlag("0");
                
                this.setArr(prpCPayablefeeSchema);
                this.update(dbpool);
            }
    	}
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
    /**
     * @description:收付拆分库方法操作数据库表方式调整
     * @param strPolicyNo
     * @throws Exception
     * @author XuJingyu
     */
    public HashMap paySplitGetPayCommissionFee( String strPolicyNo) throws Exception {
        String strRealPayRefFee = "0";
        String strPlanFee = "0";
        HashMap hsMap  =new HashMap();
        StringBuffer buffer = new StringBuffer(200);
        
        buffer.append(" SELECT SUM(RealPayRefFee) RealPayRefFee,");
        buffer.append(" SUM(PlanFee-RealPayRefFee) PlanFee ");
        buffer.append(" FROM PrpJplanCommission ");
        buffer.append(" WHERE CertiType = 'S' ");
        buffer.append(" AND PolicyNo = '"+strPolicyNo+"' ");
        buffer.append(" AND PayFlag = '1' ");      
        Vector<PrpJplanCommissionSchema> vectorPrpJplanCommissionSchemas=BLPrpQueryPaymentService.queryByServlet(BLPrpQueryPaymentService.PrpJplanCommission,buffer.toString());
        for(PrpJplanCommissionSchema schema :vectorPrpJplanCommissionSchemas ){
            try{
                strRealPayRefFee = schema.getRealPayRefFee();
                strPlanFee = schema.getPlanFee();
                hsMap.put("REAL_PAY_FEE", strRealPayRefFee);
                hsMap.put("PLAN_FEE", strPlanFee);
            } catch (Exception exception) {
                throw exception;
            }              
        } 
        return (HashMap)hsMap;
    }
}
