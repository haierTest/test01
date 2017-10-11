package com.sp.prpall.blsvr.jf;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.payment.dbsvr.jf.DBPrpJpayWVisaTrace;
import com.sp.payment.schema.PrpJpayWVisaTraceSchema;
import com.sp.prpall.schema.PrpJpayRefRecSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;


/**
 * 定义PrpJpayWVisaTrace的BL类
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>@createdate 2011-08-03</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJpayWVisaTrace {
    private Vector schemas = new Vector();
	protected final Log logger = LogFactory.getLog(getClass());
    /**
     * 构造函数
     */
    public BLPrpJpayWVisaTrace(){
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
     *增加一条PrpJpayRefRecSchema记录
     *@param iPrpJpayRefRecSchema PrpJpayRefRecSchema
     *@throws Exception
     */
    public void setArr(PrpJpayWVisaTraceSchema iPrpJpayWVisaTraceSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpJpayWVisaTraceSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }

    /**
     *得到一条PrpJpayWVisaTraceSchema记录
     *@param index 下标
     *@return 一个PrpJpayWVisaTraceSchema对象
     *@throws Exception
     */
    public PrpJpayWVisaTraceSchema getArr(int index) throws Exception
    {
      PrpJpayWVisaTraceSchema prpJpayWVisaTraceSchema = null;
      try
      {
        prpJpayWVisaTraceSchema = (PrpJpayWVisaTraceSchema)this.schemas.get(index);
      }
      catch(Exception e)
      {
        throw e;
      }
      return prpJpayWVisaTraceSchema;
    }

    /**
     *删除一条PrpJpayWVisaTraceSchema记录
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
      DBPrpJpayWVisaTrace dbPrpJpayWVisaTrace = new DBPrpJpayWVisaTrace();
      if (iLimitCount > 0 && dbPrpJpayWVisaTrace.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"PrpJpayWVisaTrace.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayWVisaTrace WHERE " + iWherePart;
        
        schemas = dbPrpJpayWVisaTrace.findByConditions(strSqlStatement);
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
      DBPrpJpayWVisaTrace dbPrpJpayWVisaTrace = new DBPrpJpayWVisaTrace();
      if (iLimitCount > 0 && dbPrpJpayWVisaTrace.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayWVisaTrace.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayWVisaTrace WHERE " + iWherePart;
        schemas = dbPrpJpayWVisaTrace.findByConditions(dbpool,strSqlStatement);
      }
    }
    
    
    public void query(String strWhere,String[] bindValues) throws Exception{
	    DbPool dbpool = new DbPool();
	    
	    try {
	        
	        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
	        
	        query(dbpool,strWhere,0,bindValues);
	    }
	    catch(Exception exception){
	        throw exception;
	    }
	    finally {
	        dbpool.close();
	    }
    }
    
    public void query(DbPool dbpool,String iWherePart,int iLimitCount,String[] bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJpayWVisaTrace dbPrpJpayWVisaTrace = new DBPrpJpayWVisaTrace();
      if (iLimitCount > 0 && dbPrpJpayWVisaTrace.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayWVisaTrace.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayWVisaTrace WHERE " + iWherePart;
        schemas = dbPrpJpayWVisaTrace.findByConditions(dbpool,strSqlStatement,bindValues);
      }
    }
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJpayWVisaTrace dbPrpJpayWVisaTrace = new DBPrpJpayWVisaTrace();
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpJpayWVisaTrace.setSchema((PrpJpayWVisaTraceSchema)schemas.get(i));
        dbPrpJpayWVisaTrace.insert(dbpool);
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
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
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
     * @desc 生成一条PrpJpayWVisaTraceSchema包含应收应付信息的记录
     * @param PrpJpayRefRecSchema prpJpayRefRecSchema
     * @throws Exception
     */
    public PrpJpayWVisaTraceSchema genSchema(PrpJpayRefRecSchema prpJpayRefRecSchema,String strWVisaStatus)
    throws Exception {
    	PrpJpayWVisaTraceSchema prpJpayWVisaTraceSchema = new PrpJpayWVisaTraceSchema();
    	prpJpayWVisaTraceSchema.setCertiType(prpJpayRefRecSchema.getCertiType());
    	prpJpayWVisaTraceSchema.setCertiNo(prpJpayRefRecSchema.getCertiNo());
    	prpJpayWVisaTraceSchema.setPolicyNo(prpJpayRefRecSchema.getPolicyNo());
    	prpJpayWVisaTraceSchema.setSerialNo(prpJpayRefRecSchema.getSerialNo());
    	prpJpayWVisaTraceSchema.setPayRefReason(prpJpayRefRecSchema.getPayRefReason());
    	prpJpayWVisaTraceSchema.setPayRefTimes(prpJpayRefRecSchema.getPayRefTimes());
    	prpJpayWVisaTraceSchema.setClaimNo(prpJpayRefRecSchema.getClaimNo());
    	prpJpayWVisaTraceSchema.setClassCode(prpJpayRefRecSchema.getClassCode());
    	prpJpayWVisaTraceSchema.setRiskCode(prpJpayRefRecSchema.getRiskCode());
    	prpJpayWVisaTraceSchema.setContractNo(prpJpayRefRecSchema.getContractNo());
    	prpJpayWVisaTraceSchema.setAppliCode(prpJpayRefRecSchema.getAppliCode());
    	prpJpayWVisaTraceSchema.setAppliName(prpJpayRefRecSchema.getAppliName());
    	prpJpayWVisaTraceSchema.setInsuredCode(prpJpayRefRecSchema.getInsuredCode());
    	prpJpayWVisaTraceSchema.setInsuredName(prpJpayRefRecSchema.getInsuredName());
    	prpJpayWVisaTraceSchema.setStartDate(prpJpayRefRecSchema.getStartDate());
    	prpJpayWVisaTraceSchema.setEndDate(prpJpayRefRecSchema.getEndDate());
    	prpJpayWVisaTraceSchema.setValidDate(prpJpayRefRecSchema.getValidDate());
    	prpJpayWVisaTraceSchema.setPayNo(prpJpayRefRecSchema.getPayNo());
    	prpJpayWVisaTraceSchema.setCurrency1(prpJpayRefRecSchema.getCurrency1());
    	prpJpayWVisaTraceSchema.setPlanFee(prpJpayRefRecSchema.getPlanFee());
    	prpJpayWVisaTraceSchema.setPlanDate(prpJpayRefRecSchema.getPlanDate());
    	prpJpayWVisaTraceSchema.setComCode(prpJpayRefRecSchema.getComCode());
    	prpJpayWVisaTraceSchema.setMakeCom(prpJpayRefRecSchema.getMakeCom());
    	prpJpayWVisaTraceSchema.setAgentCode(prpJpayRefRecSchema.getAgentCode());
    	prpJpayWVisaTraceSchema.setHandler1Code(prpJpayRefRecSchema.getHandler1Code());
    	prpJpayWVisaTraceSchema.setHandlerCode(prpJpayRefRecSchema.getHandlerCode());
    	prpJpayWVisaTraceSchema.setUnderWriteDate(prpJpayRefRecSchema.getUnderWriteDate());
    	prpJpayWVisaTraceSchema.setCoinsFlag(prpJpayRefRecSchema.getCoinsFlag());
    	prpJpayWVisaTraceSchema.setCoinsCode(prpJpayRefRecSchema.getCoinsCode());
    	prpJpayWVisaTraceSchema.setCoinsName(prpJpayRefRecSchema.getCoinsName());
    	prpJpayWVisaTraceSchema.setCoinsType(prpJpayRefRecSchema.getCoinsType());
    	prpJpayWVisaTraceSchema.setOperateDate(prpJpayRefRecSchema.getOperateDate());
    	prpJpayWVisaTraceSchema.setOperatorCode(prpJpayRefRecSchema.getOperatorCode());
    	prpJpayWVisaTraceSchema.setOperateUnit(prpJpayRefRecSchema.getOperateUnit());
    	prpJpayWVisaTraceSchema.setCurrency2(prpJpayRefRecSchema.getCurrency2());
    	prpJpayWVisaTraceSchema.setExchangeRate(prpJpayRefRecSchema.getExchangeRate());
    	prpJpayWVisaTraceSchema.setPayRefFee(prpJpayRefRecSchema.getPayRefFee());
    	prpJpayWVisaTraceSchema.setVisaCode(prpJpayRefRecSchema.getVisaCode());
    	prpJpayWVisaTraceSchema.setVisaName(prpJpayRefRecSchema.getVisaName());
    	prpJpayWVisaTraceSchema.setVisaSerialNo(prpJpayRefRecSchema.getVisaSerialNo());
    	prpJpayWVisaTraceSchema.setPrintDate(prpJpayRefRecSchema.getPrintDate());
    	prpJpayWVisaTraceSchema.setPrinterCode(prpJpayRefRecSchema.getPrinterCode());
    	prpJpayWVisaTraceSchema.setVisaHandler(prpJpayRefRecSchema.getVisaHandler());
    	prpJpayWVisaTraceSchema.setPayRefName(prpJpayRefRecSchema.getPayRefName());
    	prpJpayWVisaTraceSchema.setIdentifyType(prpJpayRefRecSchema.getIdentifyType());
    	prpJpayWVisaTraceSchema.setIdentifyNumber(prpJpayRefRecSchema.getIdentifyNumber());
    	prpJpayWVisaTraceSchema.setRemark(prpJpayRefRecSchema.getRemark());
    	prpJpayWVisaTraceSchema.setPayRefNo(prpJpayRefRecSchema.getPayRefNo());
    	prpJpayWVisaTraceSchema.setPayRefDate(prpJpayRefRecSchema.getPayRefDate());
    	prpJpayWVisaTraceSchema.setFlag(prpJpayRefRecSchema.getFlag());
    	prpJpayWVisaTraceSchema.setCarNatureCode(prpJpayRefRecSchema.getCarNatureCode());
    	prpJpayWVisaTraceSchema.setUseNatureCode(prpJpayRefRecSchema.getUseNatureCode());
    	prpJpayWVisaTraceSchema.setCarProperty(prpJpayRefRecSchema.getCarProperty());
    	prpJpayWVisaTraceSchema.setCenterCode(prpJpayRefRecSchema.getCenterCode());
    	prpJpayWVisaTraceSchema.setVoucherNo(prpJpayRefRecSchema.getVoucherNo());
    	prpJpayWVisaTraceSchema.setPayRefNoType(prpJpayRefRecSchema.getPayRefNoType());
    	prpJpayWVisaTraceSchema.setFlag1(prpJpayRefRecSchema.getFlag1());
    	prpJpayWVisaTraceSchema.setFlag2(prpJpayRefRecSchema.getFlag2());
    	prpJpayWVisaTraceSchema.setFlag3(prpJpayRefRecSchema.getFlag3());
    	prpJpayWVisaTraceSchema.setBusinessNature(prpJpayRefRecSchema.getBusinessNature());
    	prpJpayWVisaTraceSchema.setRealPayRefNo(prpJpayRefRecSchema.getRealPayRefNo());
    	prpJpayWVisaTraceSchema.setToStatus(prpJpayRefRecSchema.getToStatus());
    	prpJpayWVisaTraceSchema.setToCenterCode(prpJpayRefRecSchema.getToCenterCode());
    	prpJpayWVisaTraceSchema.setToComCode(prpJpayRefRecSchema.getToComCode());
    	prpJpayWVisaTraceSchema.setToUserCode(prpJpayRefRecSchema.getToUserCode());
    	prpJpayWVisaTraceSchema.setToDesc(prpJpayRefRecSchema.getToDesc());
    	prpJpayWVisaTraceSchema.setPayBankId(prpJpayRefRecSchema.getPayBankId());
    	prpJpayWVisaTraceSchema.setPoaType(prpJpayRefRecSchema.getPoaType());
    	prpJpayWVisaTraceSchema.setPrpCplanSerialno(prpJpayRefRecSchema.getPrpCplanSerialno());
    	prpJpayWVisaTraceSchema.setChannelType(prpJpayRefRecSchema.getChannelType());
    	
    	prpJpayWVisaTraceSchema.setAppliType(prpJpayRefRecSchema.getAppliType());
        
    	prpJpayWVisaTraceSchema.setCurrency3(prpJpayRefRecSchema.getCurrency3());
    	prpJpayWVisaTraceSchema.setCurrency3Fee(prpJpayRefRecSchema.getCurrency3Fee());
    	prpJpayWVisaTraceSchema.setExchangeRate3(prpJpayRefRecSchema.getExchangeRate3());
    	prpJpayWVisaTraceSchema.setWVisaCode(prpJpayRefRecSchema.getWVisaCode());
    	prpJpayWVisaTraceSchema.setWVisaSerialNo(prpJpayRefRecSchema.getWVisaSerialNo());
    	prpJpayWVisaTraceSchema.setWVisaStatus(strWVisaStatus);
    	return prpJpayWVisaTraceSchema;
    }
    
    /**
     * @author sunyouqiang 20110805  广东地税发票作废回置状态
     * @param strVisaCode   地税发票代码
     * @param strVisaSerialNo  地税发票号 
     * @param strVisaStatus  01-已打印  02-已作废  03-已红冲
     * @return 
     * @throws Exception
     */
    public String dropVisa(String strVisaCode,String strVisaSerialNo,String strVisaStatus) throws Exception{
    	DBPrpJpayWVisaTrace dbPrpJpayWVisaTrace = new DBPrpJpayWVisaTrace();
    	DbPool dbpool = new DbPool();
    	String strWhere = "";
    	String[] bindValues = new String[2];
    	
    	/*ADD BY PENGJINLING 2012-3-9 payment-435 产XXXXX广东省分公司营业税及附加的纳税基数规则调整 BEGIN*/
    	DateTime currentDate = new DateTime(new Date(),DateTime.YEAR_TO_DAY);
    	/*ADD BY PENGJINLING 2012-3-9 payment-435 产XXXXX广东省分公司营业税及附加的纳税基数规则调整 END*/
    	
	    
	    try {
	        
	        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
	        
	    	dbpool.beginTransaction();
	    	bindValues[0] = strVisaCode;
	    	bindValues[1] = strVisaSerialNo;
	    	strWhere = " WVisaCode=? and WVisaSerialNo=? ";
	        this.query(dbpool,strWhere,0,bindValues);
	        if(this.getSize()>0){
	        	for(int i=0;i<this.getSize();i++){
	        	    dbPrpJpayWVisaTrace.setSchema((PrpJpayWVisaTraceSchema)this.schemas.get(i));
	        	    dbPrpJpayWVisaTrace.setWVisaStatus(strVisaStatus);
	        	    dbPrpJpayWVisaTrace.update(dbpool);
	        	    
	            	/*ADD BY PENGJINLING 2012-3-9 payment-435 产XXXXX广东省分公司营业税及附加的纳税基数规则调整 BEGIN*/
	        	    
	        	    
	            	
	        	    dbPrpJpayWVisaTrace.setPayRefFee(String.valueOf(Double.parseDouble(dbPrpJpayWVisaTrace.getPayRefFee())*(-1)));
	        	    
	            	dbPrpJpayWVisaTrace.setSerialNo("-" + dbPrpJpayWVisaTrace.getSerialNo());
	            	dbPrpJpayWVisaTrace.setWVisaStatus("99");
	            	dbPrpJpayWVisaTrace.setPrintDate(currentDate.toString());
	            	dbPrpJpayWVisaTrace.setOperateDate(currentDate.toString());
	            	dbPrpJpayWVisaTrace.insert(dbpool);
	            	/*ADD BY PENGJINLING 2012-3-9 payment-435 产XXXXX广东省分公司营业税及附加的纳税基数规则调整 END*/	        	    
	        	}	        	
	        }
	        dbpool.commitTransaction();
	    }
	    catch(Exception e){
	    	dbpool.rollbackTransaction();
	    	e.printStackTrace();
	    }
	    finally {
	        dbpool.close();
	    }
    	return "";
    }
    
    /**
     * @author sunyouqiang 20110805  广东地税发票开票记录轨迹
     * @param iPrpJpayRefRecSchemaList   发票数据
     * @param strVisaStatus  01-已打印  02-已作废  03-以红冲
     * @return 
     * @throws Exception
     */
    public String traceVisa(List schemaList,String flag,String strVisaStatus) throws Exception{
    	PrpJpayWVisaTraceSchema iPrpJpayWVisaTraceSchema = null;
    	DbPool dbpool = new DbPool();
	    try {
	        
	        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
	        
	        dbpool.beginTransaction();
	        for(int i=0;i<schemaList.size();i++){
	        	if("refrec".equals(flag)){
	                iPrpJpayWVisaTraceSchema = this.genSchema((PrpJpayRefRecSchema)schemaList.get(i), strVisaStatus);
	                if("98".equals(strVisaStatus)){
		                iPrpJpayWVisaTraceSchema.setWVisaCode(iPrpJpayWVisaTraceSchema.getVisaCode());
		                iPrpJpayWVisaTraceSchema.setWVisaSerialNo(iPrpJpayWVisaTraceSchema.getVisaSerialNo());
	                }
	        	}else{
	        		throw new Exception("无效的转换类型"+flag);
	        	}
	            this.setArr(iPrpJpayWVisaTraceSchema);
	        }
	        this.save(dbpool);
	        dbpool.commitTransaction();
	    }
	    catch(Exception e){
	    	dbpool.rollbackTransaction();
	    	e.printStackTrace();
	    }
	    finally {
	        dbpool.close();
	    }
    	return "";
    }

    public boolean getCheckIpAddressFlag() {
		String result = "";
		boolean flag = false;
		try {
			String ipAddress =SysConfig.getProperty("IP_OF_SERVICE"); 
			
			Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = InetAddress.getLocalHost();
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces
						.nextElement();
				ip = (InetAddress) ni.getInetAddresses().nextElement();
				logger.info("根据网卡取本机配置的 IP"+ip.getHostAddress());
				if (!ip.isLoopbackAddress()
						&& ip.getHostAddress().indexOf(":") == -1) {
					result = ip.getHostAddress();
					if(ipAddress.indexOf(result)!=-1){
						flag = true;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
    }
    
    /*ADD BY PENGJINLING 2012-3-9 payment-435 产XXXXX广东省分公司营业税及附加的纳税基数规则调整 BEGIN*/    
    /**
     * @author PENGJINLING
     * @param iPrpJpayRefRecSchemaList   发票数据
     * @param strVisaStatus  01-已打印  02-已作废  03-以红冲
     * @return 
     * @throws Exception
     */
    public void traceVisa99(DbPool dbpool,List schemaList) throws Exception{
        DateTime currentDate = new DateTime(new Date(),DateTime.YEAR_TO_DAY); 
    	PrpJpayWVisaTraceSchema iPrpJpayWVisaTraceSchema = null;
        for(int i=0;i<schemaList.size();i++)
        {
        	iPrpJpayWVisaTraceSchema = (PrpJpayWVisaTraceSchema)schemaList.get(i);
			iPrpJpayWVisaTraceSchema.setWVisaStatus("99");
			
			iPrpJpayWVisaTraceSchema.setPayRefFee(String.valueOf(Double.parseDouble(iPrpJpayWVisaTraceSchema.getPayRefFee())*(-1)));
			
			iPrpJpayWVisaTraceSchema.setSerialNo("-" + iPrpJpayWVisaTraceSchema.getSerialNo());
			iPrpJpayWVisaTraceSchema.setPrintDate(currentDate.toString());
			iPrpJpayWVisaTraceSchema.setOperateDate(currentDate.toString());	        	
        	this.setArr(iPrpJpayWVisaTraceSchema);
        }
        this.save(dbpool);
    }
    /*ADD BY PENGJINLING 2012-3-9 payment-435 产XXXXX广东省分公司营业税及附加的纳税基数规则调整 END*/

    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
