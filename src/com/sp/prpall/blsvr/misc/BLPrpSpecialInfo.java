package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpSpecialInfo;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpSpecialInfoSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;

/**
 * 定义prpSpecialInfo的BL类
 * 从pdm中取数据库信息,根据数据库表生成对应的BL类
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: 中科软Java源码生成工具</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-01-06</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.新增对CIXXXXX平台类的生成处理;
 *              2.修正特殊表生成类cancel()、getdata()方法时获取pdm表主键入参为null的问题;
 *              3.修正特殊表生成类部分import类为null的问题;
 *              4.新增log4j日志bl层类自动初始化;
 */
public class BLPrpSpecialInfo{

    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpSpecialInfo(){
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
     *增加一条PrpSpecialInfoSchema记录
     *@param iPrpSpecialInfoSchema PrpSpecialInfoSchema
     *@throws Exception
     */
    public void setArr(PrpSpecialInfoSchema iPrpSpecialInfoSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpSpecialInfoSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *得到一条PrpSpecialInfoSchema记录
     *@param index 下标
     *@return 一个PrpSpecialInfoSchema对象
     *@throws Exception
     */
    public PrpSpecialInfoSchema getArr(int index) throws Exception
    {
      PrpSpecialInfoSchema prpSpecialInfoSchema = null;
       try
       {
         prpSpecialInfoSchema = (PrpSpecialInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpSpecialInfoSchema;
     }
    /**
     *删除一条PrpSpecialInfoSchema记录
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
      DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();
      if (iLimitCount > 0 && dbPrpSpecialInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpSpecialInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpSpecialInfo WHERE " + iWherePart; 
        schemas = dbPrpSpecialInfo.findByConditions(strSqlStatement);
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
      DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();
      if (iLimitCount > 0 && dbPrpSpecialInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpSpecialInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpSpecialInfo WHERE " + iWherePart; 
        schemas = dbPrpSpecialInfo.findByConditions(dbpool,strSqlStatement);
      }
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
        DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();
        if (iLimitCount > 0 && dbPrpSpecialInfo.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpSpecialInfo.query");
        }else
        {
        	initArr();
            strSqlStatement = " SELECT * FROM PrpSpecialInfo WHERE " + iWherePart;
            schemas = dbPrpSpecialInfo.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    public void query(String iWherePart,ArrayList iWhereValue, int iLimitCount) throws UserException,Exception
    {
        String strSqlStatement = "";
        DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();
        if (iLimitCount > 0 && dbPrpSpecialInfo.getCount(iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpSpecialInfo.query");
        }else
        {
        	initArr();
            strSqlStatement = " SELECT * FROM PrpSpecialInfo WHERE " + iWherePart; 
            schemas = dbPrpSpecialInfo.findByConditions(strSqlStatement,iWhereValue);
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

      DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();
      if (iLimitCount > 0 && dbPrpSpecialInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPrintPool.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM ( " +
         "Select RowNum As LineNum,T.* From ( " +
         "Select * From prpSpecialInfo Where " + iWherePart +
         ") T Where RowNum<=" + intEndNum + ") Where LineNum>" +
         intStartNum ;
        schemas = dbPrpSpecialInfo.findByConditions(strSqlStatement);
      }
    }
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpSpecialInfo.setSchema((PrpSpecialInfoSchema)schemas.get(i));
        dbPrpSpecialInfo.insert(dbpool);
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
      DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
    	  dbPrpSpecialInfo.setSchema((PrpSpecialInfoSchema)schemas.get(i));
    	  dbPrpSpecialInfo.update(dbpool);
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
     *@param iProposalNo ProposalNo
     *@return 无
     */
    public void cancel(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpSpecialInfo WHERE ProposalNo= '" + iProposalNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param iProposalNo ProposalNo
     *@return 无
     */
    public void cancel(String iProposalNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iProposalNo);
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
      
    public void getData(String iProposalNo) throws Exception
    {
    	
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			getData(dbpool, iProposalNo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbpool.close();
		}
		
    }
      
    /**
     * 不带dbpool根据ProposalNo获取数据
     *@param dbpool 连接池
     *@param iProposalNo ProposalNo
     *@return 无
     */
    public void getData(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strWherePart = " ProposalNo= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iProposalNo);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    /**
     *根据是否查到特殊业务合同号，如果未查到则为首张XX
     *@param dbpool      全局池
     *@param prpSpecialInfoSchema  特殊业务
     *@return 特殊业务合同号
     *@throws Exception
     */
    public String getSpecialContractNo(PrpSpecialInfoSchema prpSpecialInfoSchema)throws Exception{
    	
		String[] columns1 = new String[4];
		String[] columns2 = new String[4];
		String[] operators = new String[4];
		ArrayList arrWhereValueAppli = new ArrayList(4);
		ArrayList arrWhereValueInsured= new ArrayList(4);
		columns1[0] = "AppliName";
		columns1[1] = "StartDate";
		columns1[2] = "SpecialBusiness";
		columns1[3] = "UnderWriteStatus";
		
		columns2[0] = "InsuredName";
		columns2[1] = "StartDate";
		columns2[2] = "SpecialBusiness";
		columns2[3] = "UnderWriteStatus";
		
		operators[0] = "=";
		operators[1] = ">";
		operators[2] = "=";
		operators[3] = "=";
		String wherePartAppli = PubTools.getSwhere(columns1, operators);
		String wherePartInsured = PubTools.getSwhere(columns2, operators);
		
		BLPrpSpecialInfo blPrpSpecialInfoAppli = new BLPrpSpecialInfo();
		BLPrpSpecialInfo blPrpSpecialInfoInsured = new BLPrpSpecialInfo();
		
		arrWhereValueAppli.add(prpSpecialInfoSchema.getAppliName());
		arrWhereValueAppli.add("");
		arrWhereValueAppli.add(prpSpecialInfoSchema.getSpecialBusiness());
		arrWhereValueAppli.add("1");
		arrWhereValueInsured.add(prpSpecialInfoSchema.getInsuredName());
		arrWhereValueInsured.add("");
		arrWhereValueInsured.add(prpSpecialInfoSchema.getSpecialBusiness());
		arrWhereValueInsured.add("1");
		
		java.util.Date d = new java.util.Date(); 
		
		java.text.SimpleDateFormat dformat = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
		java.util.Date stratdate = dformat.parse(prpSpecialInfoSchema.getStartDate()); 
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(stratdate);
		if("a".equals(prpSpecialInfoSchema.getSpecialBusiness())){
			
			calendar.add(Calendar.DAY_OF_MONTH,-30); 
			d = calendar.getTime(); 
			String datetime = dformat.format(d); 
			arrWhereValueAppli.set(1,datetime);
			arrWhereValueInsured.set(1,datetime);
		}else if("8".equals(prpSpecialInfoSchema.getSpecialBusiness())){
			
    		calendar.add(Calendar.MONTH,-12); 
    		d = calendar.getTime();
    		String datetime = dformat.format(d);
    		arrWhereValueAppli.set(1,datetime);
    		arrWhereValueInsured.set(1,datetime);
		}
    	
    	
  	    blPrpSpecialInfoAppli.query(wherePartAppli, arrWhereValueAppli, 0);
  	    
  	    
  	    if(blPrpSpecialInfoAppli.getSize()>0){
  	      return  blPrpSpecialInfoAppli.getArr(0).getSpecialContractNo();
  	    }else{
	      blPrpSpecialInfoInsured.query(wherePartInsured, arrWhereValueInsured, 0);
	      
	  	  
	  	  if(blPrpSpecialInfoInsured.getSize()>0){
	  	   	return  blPrpSpecialInfoInsured.getArr(0).getSpecialContractNo(); 
	  	  }else{
	  	   	return "";
	  	  }
  	    }
    }
    /**
     *统括业务，查询已录入的特殊业务合同号是否正确
     *@param strSpecialContractNo  特殊业务合同号
     *@return 系统已存在录入特殊业务合同号的个数
     *@throws Exception
     */
    public int getSpecialContractNoCount(String strSpecialBusiness,String strSpecialContractNo )throws Exception{
    	String[] columns1 = new String[3];
		String[] operators = new String[3];
		ArrayList arrWhereValue = new ArrayList();
		columns1[0] = "SpecialBusiness";
		columns1[1] = "SpecialContractNo";
		columns1[2] = "UnderWriteStatus";
		operators[0] = "=";
		operators[1] = "=";
		operators[2] = "=";
		String wherePart = PubTools.getSwhere(columns1, operators);
		arrWhereValue.add(strSpecialBusiness);
		arrWhereValue.add(strSpecialContractNo);
		arrWhereValue.add("1");
		DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();
		try{
		return dbPrpSpecialInfo.getCount(wherePart,arrWhereValue);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
    }
    /**
     *双核审核通过后如果是首张XX，生成特殊业务编号并回写BLprpSpecialInfo，审核通过UnderWriteStatus置为1
     *@param iProposalNo  XX单号
     *@return 无
     *@throws Exception
     */
    public void echoBLprpSpecialInfo(DbPool dbpool,String iProposalNo) throws Exception{
    	BLPrpSpecialInfo blPrpSpecialInfo = new BLPrpSpecialInfo();
    	PrpSpecialInfoSchema prpSpecialInfoSchema = new  PrpSpecialInfoSchema();
    	blPrpSpecialInfo.getData(dbpool,iProposalNo);
    	
    	if(blPrpSpecialInfo.getSize()>0){
    		prpSpecialInfoSchema = blPrpSpecialInfo.getArr(0);
    		
    		if("1".equals(prpSpecialInfoSchema.getFirstFlag())){
    			String strSpecialContractNo = new ChgDate().getCurrentTime("yyyyMMddHHmmss")
    			                                 +prpSpecialInfoSchema.getComCode().substring(0,4)
    			                                 +prpSpecialInfoSchema.getSpecialBusiness();
    			prpSpecialInfoSchema.setSpecialContractNo(strSpecialContractNo);
    		}
    		prpSpecialInfoSchema.setUnderWriteStatus("1");
    		blPrpSpecialInfo.setArr(prpSpecialInfoSchema);
    		blPrpSpecialInfo.update(dbpool);
    	}
    }

    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
