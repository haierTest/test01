package com.sp.prpall.blsvr.jf;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sp.platform.dto.domain.PrpDuserDto;
import com.sp.prpall.dbsvr.jf.DBPrpJBankCheckDetail;
import com.sp.prpall.dbsvr.jf.DBPrpJBankCheckMain;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaInfo;
import com.sp.prpall.pubfun.SFFExcelUtils;
import com.sp.prpall.schema.PrpJBankCheckDetailSchema;
import com.sp.prpall.schema.PrpJBankCheckMainSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.Money;
import com.sp.utility.string.Str;

/**
 * 定义PrpJBankCheckMain-银行对账信息主表的BL类
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-12-21</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJBankCheckMain{
    private List schemas = new ArrayList();
    public List errorBillNoList = new ArrayList();
	public static Map bankBillItemPosMap = new HashMap();
	public static Map errorCodeMap = new HashMap();
	public static Map errorCodeNameMap = new HashMap();
	public static Map billTypeNameMap  = new HashMap();
	public static Map bankCodeNameMap  = new HashMap();
	static {
		initBillItemPosMap();
		initErrorCodeMap();
		initErrorCodeNameMap();
		initBillTypeNameMap();
		initBankCodeNameMap();
	}
    /**
     * 构造函数
     */       
    public BLPrpJBankCheckMain(){
    }
	/**
	 * @desc 初始化对账单类型代码与名称Map
	 *
	 */
	private static synchronized void initBillTypeNameMap(){
		billTypeNameMap.put("01", "POS对账单");
		billTypeNameMap.put("99", "其他类型");
	}
	/**
	 * @desc 初始化对账单帐单银行代码与名称Map
	 *
	 */
	private static synchronized void initBankCodeNameMap(){
		bankCodeNameMap.put("01", "工商银行");
		bankCodeNameMap.put("99", "其他类型");
	}
	/**
	 * @desc 初始化对账单中元素与位置Map
	 *
	 */
	private static synchronized void initBillItemPosMap(){
		bankBillItemPosMap.put("PosNo", "5");
		bankBillItemPosMap.put("PosVoucherNo", "6");
		bankBillItemPosMap.put("CustomNo", "7");
		bankBillItemPosMap.put("TransCardNo", "1");
		bankBillItemPosMap.put("TransFee", "2");
		bankBillItemPosMap.put("CommissionRate", "8");
		bankBillItemPosMap.put("CommissionFee", "3");
		bankBillItemPosMap.put("NetFee", "4");
		bankBillItemPosMap.put("TransDate", "0");
	}
	/**
	 * @desc 初始化错误代码对应Map
	 *
	 */
	private static synchronized void initErrorCodeMap(){
		errorCodeMap.put("NoTransError", "01");
		errorCodeMap.put("TransMapMoreError", "02");
		errorCodeMap.put("TransDateError", "03");
		errorCodeMap.put("TransFeeError", "04");
		errorCodeMap.put("OtherError", "99");
	}
	/**
	 * @desc 初始化错误代码错误名称对应Map
	 *
	 */
	private static synchronized void initErrorCodeNameMap(){
		errorCodeNameMap.put("01", "系统无该交易");
		errorCodeNameMap.put("02", "系统对应记录>1条");
		errorCodeNameMap.put("03", "交易日期不一致");
		errorCodeNameMap.put("04", "交易金额不一致");
		errorCodeNameMap.put("99", "其他错误");
	}
	/**
	 * @desc 获取代码对应名称
	 * @param errorCode
	 * @return
	 */
	public static String translateCodeToName(String codeType,String codeValue){
		String nameValue = "";
		if(codeValue==null||codeValue.equals(""))
			return nameValue;
		if(codeType!=null&&codeType.equals("ErrorCode")){
			nameValue = (String)errorCodeNameMap.get(codeValue);
		}else if(codeType!=null&&codeType.equals("BillType")){
			nameValue = (String)billTypeNameMap.get(codeValue);
		}else if(codeType!=null&&codeType.equals("BankCode")){
			nameValue = (String)bankCodeNameMap.get(codeValue);
		}else{
			nameValue = "未知";
		}
		if(nameValue==null||nameValue.equals(""))
			nameValue="未知";
		return nameValue;
	}
    /**
     *初始化记录
     *@param 无
     *@return 无
     *@throws Exception
     */
    public void initArr() throws Exception
    {
       schemas = new ArrayList();
       errorBillNoList = new ArrayList();
     }
    /**
     *增加一条PrpJBankCheckMainSchema记录
     *@param iPrpJBankCheckMainSchema PrpJBankCheckMainSchema
     *@throws Exception
     */
    public void setArr(PrpJBankCheckMainSchema iPrpJBankCheckMainSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJBankCheckMainSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpJBankCheckMainSchema记录
     *@param index 下标
     *@return 一个PrpJBankCheckMainSchema对象
     *@throws Exception
     */
    public PrpJBankCheckMainSchema getArr(int index) throws Exception
    {
     PrpJBankCheckMainSchema prpJBankCheckMainSchema = null;
       try
       {
        prpJBankCheckMainSchema = (PrpJBankCheckMainSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJBankCheckMainSchema;
     }
    /**
     *删除一条PrpJBankCheckMainSchema记录
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
      DBPrpJBankCheckMain dbPrpJBankCheckMain = new DBPrpJBankCheckMain();
      if (iLimitCount > 0 && dbPrpJBankCheckMain.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJBankCheckMain.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJBankCheckMain WHERE " + iWherePart; 
        schemas = dbPrpJBankCheckMain.findByConditions(strSqlStatement);
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
      DBPrpJBankCheckMain dbPrpJBankCheckMain = new DBPrpJBankCheckMain();
      if (iLimitCount > 0 && dbPrpJBankCheckMain.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJBankCheckMain.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJBankCheckMain WHERE " + iWherePart; 
        schemas = dbPrpJBankCheckMain.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJBankCheckMain dbPrpJBankCheckMain = new DBPrpJBankCheckMain();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJBankCheckMain.setSchema((PrpJBankCheckMainSchema)schemas.get(i));
      dbPrpJBankCheckMain.insert(dbpool);
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
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param billNo billNo
     *@return 无
     */
    public void cancel(DbPool dbpool,String billNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpJBankCheckMain WHERE BillNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, billNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * 不带dbpool的删除方法
     *@param billNo billNo
     *@return 无
     */
    public void cancel(String billNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
      
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,billNo);
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
     * 带dbpool根据billNo获取数据
     *@param billNo billNo
     *@return 无
     */
    public void getData(String billNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
      
      getData(dbpool,billNo);
      dbpool.close();
    }
      
    /**
     * 不带dbpool根据billNo获取数据
     *@param dbpool 连接池
     *@param billNo billNo
     *@return 无
     */
    public void getData(DbPool dbpool,String billNo) throws Exception
    {
    String strWherePart = "";
    strWherePart = "BillNo= '" + billNo + "'";
    query(dbpool,strWherePart,0);
    }
    /**
     * @desc 银行对账单导入-不带dbpool
     * @author liufengyao
     * @param iBillType
     * @param iBankCode
     * @param iBillFile
     * @param prpDuserDto
     * @return strBillNo
     * @throws Exception
     */
    public String billImport(String iBillType,String iBankCode,String iBillFile,PrpDuserDto prpDuserDto)
    throws Exception{
    	String strBillNo = "";
		DbPool dbpool = new DbPool();
		
		dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		
		try {
			dbpool.beginTransaction();
			strBillNo = billImport(dbpool, iBillType, iBankCode, iBillFile,prpDuserDto);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
			e.printStackTrace();
			throw e;
		} finally {
			dbpool.close();
		}
		return strBillNo;
	}
    /**
	 * @desc 银行对账单导入-带dbpool
	 * @author liufengyao
	 * @param iBillType
	 * @param iBankCode
	 * @param iBillFile
	 * @param prpDuserDto
	 * @return strBillNo
	 * @throws Exception
	 */
    public String billImport(DbPool dbPool,String iBillType,String iBankCode,String iBillFile,PrpDuserDto prpDuserDto)
    throws Exception{
    	String strBillNo = "";
    	int billItemNum = 0;   	
    	int ignoreRow = 2;
    	
    	BLPrpJBankCheckDetail blPrpJBankCheckDetail = new BLPrpJBankCheckDetail();
    	PrpJBankCheckMainSchema prpJBankCheckMainSchema = new PrpJBankCheckMainSchema();
    	DBPrpJBankCheckMain dbPrpJBankCheckMain = new DBPrpJBankCheckMain();
    	PrpJBankCheckDetailSchema prpJBankCheckDetailSchema = null;
    	DateTime currentDate = new DateTime(new Date(),DateTime.YEAR_TO_DAY);
    	DateTime currentDateDetail = new DateTime(new Date(),DateTime.YEAR_TO_SECOND);
    	strBillNo = geneValidBillNo(dbPool);
    	if(strBillNo==null||strBillNo.equals(""))
    		throw new UserException(-98,-1200,"BLPrpJBankCheckMain.billImport","帐单编码获取失败！");
		File billFile = new File(iBillFile);
		final String[][] billDetailData = SFFExcelUtils.getData(billFile,ignoreRow);
		billItemNum = billDetailData.length;
		
		prpJBankCheckMainSchema.setBillNo(strBillNo);
		prpJBankCheckMainSchema.setBillType(iBillType);
		prpJBankCheckMainSchema.setBankCode(iBankCode);
		
		prpJBankCheckMainSchema.setAttribute2(currentDateDetail.toString());
		prpJBankCheckMainSchema.setOperatorCode(prpDuserDto.getUserCode());
		prpJBankCheckMainSchema.setComCode(prpDuserDto.getLoginComCode());
		prpJBankCheckMainSchema.setBillNum(""+billItemNum);
		prpJBankCheckMainSchema.setCheckFlag("0");
		prpJBankCheckMainSchema.setFalseNum("0");	
		int iSerialNo = 0;
		for (int i = 0; i < billItemNum; i++) {
			
			prpJBankCheckDetailSchema = new PrpJBankCheckDetailSchema();
			prpJBankCheckDetailSchema.setBillNo(strBillNo);
			prpJBankCheckDetailSchema.setSerialNo(""+iSerialNo++);
			setBankBillDetailInfo(prpJBankCheckDetailSchema,billDetailData[i]);
			prpJBankCheckDetailSchema.setCheckFlag("0");
			prpJBankCheckDetailSchema.setOperatorCode(prpDuserDto.getUserCode());
			prpJBankCheckDetailSchema.setOperatorDate(currentDateDetail.toString());
			blPrpJBankCheckDetail.setArr(prpJBankCheckDetailSchema);
		}
		
		blPrpJBankCheckDetail.save(dbPool); 
		if(blPrpJBankCheckDetail.getSize()==0)
			throw new UserException(-98,-1200,"BLPrpJBankCheckMain.billImport","没有交易信息，无需导入！");
		prpJBankCheckMainSchema.setBillDate(blPrpJBankCheckDetail.getArr(0).getTransDate());
		dbPrpJBankCheckMain.setSchema(prpJBankCheckMainSchema);
		
		dbPrpJBankCheckMain.insert(dbPool);
		
    	return strBillNo;
    }
    /**
     * @desc 循环核对银行对帐单,每一个对账单一个事务
     * @author liufengyao
     * @param arrBillNo
     * @param user
     * @throws Exception
     */
    public void billCheck(String[] arrBillNo,PrpDuserDto user)
    throws Exception{
    	String strBillNo = "";
		DbPool dbpool = new DbPool();
		try {
		    
		    dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		    
			for(int i=0;i<arrBillNo.length;i++){
				strBillNo = arrBillNo[i];
				try{
					dbpool.beginTransaction();
					billCheck(dbpool,strBillNo,user);
					dbpool.commitTransaction();					
				}catch (Exception ex){
					dbpool.rollbackTransaction();
					ex.printStackTrace();
					errorBillNoList.add(strBillNo);
				}				
			}			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dbpool.close();
		}
	}
    /**
     * @desc 对单个银行对帐单进行核对
     * @author liufengyao
     * @param arrBillNo 对帐单编码
     * @param user
     * @throws Exception
     */
    public void billCheck(DbPool dbpool,String iBillNo,PrpDuserDto user)
    throws Exception{
    	int falseNum = 0;
    	String strPosNo = "";
    	String strPosVoucherNo = "";
    	String strCustomNo = "";
    	String strTransFee = "";
    	String strTransDate = "";
    	String strSQL = "";
    	int result = 0;
    	String strCheckFlag = "1";
    	String errorCode = "";
    	String systemValue = "";
    	double poaInfoTransFee = 0.00;
    	DateTime poaInfoDateTime = null;
    	DateTime bankCheckDateTime = null;
    	BLPrpJBankCheckDetail blPrpJBankCheckDetail = new BLPrpJBankCheckDetail();
    	PrpJBankCheckDetailSchema detailSchema = new PrpJBankCheckDetailSchema();
    	DBPrpJBankCheckMain dbPrpJBankCheckMain = new DBPrpJBankCheckMain();
    	DateTime currentDateDetail = new DateTime(new Date(),DateTime.YEAR_TO_SECOND);
    	DBPrpJpoaInfo dbPrpJpoaInfo = null;
    	DBPrpJBankCheckDetail dbPrpJBankCheckDetail = new DBPrpJBankCheckDetail();
    	
    	int mainResult = dbPrpJBankCheckMain.getInfo(iBillNo);
    	if(mainResult==100)
    		throw new UserException(-98,-1200,"BLPrpJBankCheckMain.billCheck","银行对账信息主表中不存在帐单编号"+iBillNo+"对应信息!");
    	
    	strSQL = " BillNo = '"+iBillNo+"' Order By SerialNo";
    	blPrpJBankCheckDetail.query(dbpool, strSQL, 0);
    	if(blPrpJBankCheckDetail.getSize()==0)
    		throw new UserException(-98,-1200,"BLPrpJBankCheckMain.billCheck","银行对账信息明细表中不存在帐单编号"+iBillNo+"对应信息!");
    	
    	for(int i=0;i<blPrpJBankCheckDetail.getSize();i++){
    		detailSchema = blPrpJBankCheckDetail.getArr(i);
    		
    		strPosNo = detailSchema.getPosNo();
    		strPosVoucherNo = detailSchema.getPosVoucherNo();
    		strCustomNo = detailSchema.getCustomNo();
    		strTransFee = detailSchema.getTransFee();
    		strTransDate = detailSchema.getTransDate();
    		bankCheckDateTime = new DateTime(strTransDate,DateTime.YEAR_TO_DAY);
    		
    		dbPrpJpoaInfo = new DBPrpJpoaInfo();
    		result = dbPrpJpoaInfo.getInfoByMainElem(dbpool, strPosNo, strPosVoucherNo, strCustomNo);
    		
    		strCheckFlag = "1";
    		errorCode = "";
    		systemValue = "";
    		if(result==0){
    			
    			strCheckFlag = "2";
    			errorCode = (String)errorCodeMap.get("NoTransError");
    		}
    		else if(result>1){
    			
    			strCheckFlag = "2";
    			errorCode = (String)errorCodeMap.get("TransMapMoreError");
    		}else{
    			
    			poaInfoTransFee = Double.parseDouble(dbPrpJpoaInfo.getTotalFee());
    			poaInfoDateTime = new DateTime(dbPrpJpoaInfo.getAccDate(),DateTime.YEAR_TO_DAY);
    			if(poaInfoTransFee!=Double.parseDouble(strTransFee)){
    				
    				strCheckFlag = "2";
    				errorCode = (String)errorCodeMap.get("TransFeeError");
    				systemValue = new Money().toAccount(dbPrpJpoaInfo.getTotalFee());
    				
    			}else if(bankCheckDateTime.compareTo(poaInfoDateTime)!=0){
    				
    				strCheckFlag = "2";
    				errorCode = (String)errorCodeMap.get("TransDateError");
    				systemValue = poaInfoDateTime.toString();
    			}
    			
    			dbPrpJpoaInfo.setAttribute1(strCheckFlag);
    			dbPrpJpoaInfo.update(dbpool);
    		}
    		
    		if(strCheckFlag.equals("2"))
    			falseNum++;
    		detailSchema.setCheckFlag(strCheckFlag);
    		detailSchema.setErrorCode(errorCode);
    		detailSchema.setErrorName(systemValue);
    		detailSchema.setOperatorCode(user.getUserCode());
    		detailSchema.setOperatorDate(currentDateDetail.toString());
    		dbPrpJBankCheckDetail.setSchema(detailSchema);
    		dbPrpJBankCheckDetail.update(dbpool);
    	}
    	dbPrpJBankCheckMain.setCheckFlag("1");
    	dbPrpJBankCheckMain.setFalseNum(""+falseNum);
    	dbPrpJBankCheckMain.setCheckerCode(user.getUserCode());
    	dbPrpJBankCheckMain.setCheckDate(currentDateDetail.toString());
    	dbPrpJBankCheckMain.update(dbpool);   	
    	this.setArr(dbPrpJBankCheckMain);
    }
    /**
     * @desc 删除已导入、未核对帐单
     * @author liufengyao
     * @param arrBillNo
     */
    public void billDelete(String[] arrBillNo)throws Exception{
		DbPool dbpool = new DbPool();
		try {
		    
		    dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		    
			dbpool.beginTransaction();
			billDelete(dbpool,arrBillNo);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
			e.printStackTrace();
			throw e;
		} finally {
			dbpool.close();
		}
    }
    /**
     * @desc 删除已导入、未核对对账单
     * @author liufengyao
     * @param dbpool
     * @param arrBillNo
     * @throws Exception
     */
    public void billDelete(DbPool dbpool,String[] arrBillNo)
    throws Exception{
    	DBPrpJBankCheckMain dbPrpJBankCheckMain = new DBPrpJBankCheckMain();
    	DBPrpJBankCheckDetail dbPrpJBankCheckDetail = new DBPrpJBankCheckDetail();
    	for(int i=0;i<arrBillNo.length;i++){
    		dbPrpJBankCheckMain.delete(dbpool, arrBillNo[i]);
    		dbPrpJBankCheckDetail.deleteByBillNo(dbpool,arrBillNo[i]);
    	}
    }
    /**
     * @desc 设置帐单明细项信息
     * @author liufengyao
     * @param iSchema
     * @param billDetailData
     * @throws Exception
     */
    public void setBankBillDetailInfo(PrpJBankCheckDetailSchema iSchema,String[] billDetailData)
    throws Exception{
		int rowNum = billDetailData.length;
		int posNoIndex = Integer.parseInt((String)bankBillItemPosMap.get("PosNo"));
		int PosVoucherNoIndex = Integer.parseInt((String)bankBillItemPosMap.get("PosVoucherNo"));
		int CustomNoIndex = Integer.parseInt((String)bankBillItemPosMap.get("CustomNo"));
		int TransCardNoIndex = Integer.parseInt((String)bankBillItemPosMap.get("TransCardNo"));
		int TransFeeIndex = Integer.parseInt((String)bankBillItemPosMap.get("TransFee"));
		int CommissionRateIndex = Integer.parseInt((String)bankBillItemPosMap.get("CommissionRate"));
		int CommissionFeeIndex = Integer.parseInt((String)bankBillItemPosMap.get("CommissionFee"));
		int NetFeeIndex = Integer.parseInt((String)bankBillItemPosMap.get("NetFee"));
		int TransDateIndex = Integer.parseInt((String)bankBillItemPosMap.get("TransDate"));
		if(posNoIndex>=rowNum||PosVoucherNoIndex>=rowNum||CustomNoIndex>=rowNum
				||TransCardNoIndex>=rowNum||TransFeeIndex>=rowNum||CommissionRateIndex>=rowNum
				||CommissionFeeIndex>=rowNum||NetFeeIndex>=rowNum||TransDateIndex>=rowNum){
			throw new UserException(-98,-1200,"BLPrpJBankCheckMain.setBankBillDetailInfo","数组取值越界异常,请检查导入对账单格式!");
		}
    	String strPosNo = transDataFormat("POSNO",billDetailData[posNoIndex]);         
    	String strPosVoucherNo = transDataFormat("POSVOUCHERNO",billDetailData[PosVoucherNoIndex]);  
    	String strCustomNo = transDataFormat("CUSTOMNO",billDetailData[CustomNoIndex]);      
    	String strTransCardNo = transDataFormat("",billDetailData[TransCardNoIndex]);   
    	String strTransFee = billDetailData[TransFeeIndex];      
    	String strCommissionRate = billDetailData[CommissionRateIndex];
    	String strCommissionFee = billDetailData[CommissionFeeIndex]; 
    	String strNetFee = billDetailData[NetFeeIndex];        
    	String strTransDate = transDataFormat("",billDetailData[TransDateIndex]);     
    	iSchema.setPosNo(strPosNo);
    	iSchema.setPosVoucherNo(strPosVoucherNo);
    	iSchema.setCustomNo(strCustomNo);
    	iSchema.setTransCardNo(strTransCardNo);
    	iSchema.setTransFee(strTransFee);
    	iSchema.setCommissionRate(strCommissionRate);
    	iSchema.setCommissionFee(strCommissionFee);
    	iSchema.setNetFee(strNetFee);
    	iSchema.setTransDate(strTransDate);		
    }
    /**
     * @desc 转换数据格式
     * @author liufengyao
     * @param DateType 数据类型
     * @param iData    待转换数据
     * @return 转换数据
     */
    private String transDataFormat(String DataType,String iData)
    throws Exception{
    	String convDate = "";
    	if(iData.indexOf(".")>0)
    		iData = iData.substring(0,iData.indexOf("."));
    	if(DataType.equals("POSNO")){
    		
    		if(iData.length()>3){
    			convDate = iData.substring(iData.length()-3);
    		}else if(iData.length()<3){
    			throw new UserException(-98,-1200,"BLPrpJBankCheckMain.transDataFormat","POS终端号"+iData+"不满足格式:至少3位,请检查导入对账单文件!");
    		}else{
        		convDate = iData;
    		}
    	}else if(DataType.equals("POSVOUCHERNO")){
    		
    		if(iData.length()>8){
    			throw new UserException(-98,-1200,"BLPrpJBankCheckMain.transDataFormat","检索参考号"+iData+"不满足格式:不能大于8位,请检查导入对账单文件!");
    		}else if(iData.length()<=0){
    			throw new UserException(-98,-1200,"BLPrpJBankCheckMain.transDataFormat","检索参考号"+iData+"不满足格式:不能为空,请检查导入对账单文件!");
    		}else if(iData.length()< 8){
    			int fillLength = 8 - iData.length();
    			convDate = Str.newString("0", fillLength) + iData;
    		}else{
    			convDate = iData;
    		}
    	}else if(DataType.equals("CUSTOMNO")){
    		
    		if(iData.length()>20){
    			throw new UserException(-98,-1200,"BLPrpJBankCheckMain.transDataFormat","商户号"+iData+"不满足格式:不能大于20位,请检查导入对账单文件!");
    		}else if(iData.length()<=0){
    			throw new UserException(-98,-1200,"BLPrpJBankCheckMain.transDataFormat","商户号"+iData+"不满足格式:不能为空,请检查导入对账单文件!");
    		}else if(iData.length()< 12){
    			int fillLength = 12 - iData.length();
    			convDate = Str.newString("0", fillLength) + iData;
    		}else{
    			convDate = iData;
    		}    		
    	}else{
    		convDate = iData;
    	} 	
    	return convDate;
    }
    /**
     * @desc 获取帐单编号,如果数据库中该编号已经存在，则继续取下一个
     * @author liufengyao
     * @param dbPool
     * @return
     * @throws Exception
     */
    public String geneValidBillNo(DbPool dbPool)
    throws Exception{
    	String strBillNo = "";
        boolean returnFlag = false;
		while(!returnFlag){
			strBillNo = geneBillNo(dbPool);
			BLPrpJBankCheckMain blPrpJBankCheckMainCheckBillNo = new BLPrpJBankCheckMain();
			blPrpJBankCheckMainCheckBillNo.getData(dbPool,strBillNo);
			if(blPrpJBankCheckMainCheckBillNo.getSize()==0)
				returnFlag = true;
		}
		return strBillNo;
    }
    /**
     * @desc 获取帐单编号
     * @author liufengyao
     * @param dbPool
     * @return
     * @throws Exception
     */
    public String geneBillNo(DbPool dbPool)
    throws Exception{
    	String strBillNo = "";
    	int billNoLength = 10;
        String strSQL = " SELECT PRPJBANKCHECKMAIN_SEQUENCE.nextval FROM DUAL";
        ResultSet resultSet = dbPool.query(strSQL);
        if (resultSet.next()) {
			strBillNo = resultSet.getString("nextval");
			resultSet.close();
		}
		int fillLength = billNoLength - strBillNo.length();
		strBillNo = Str.newString("0", fillLength) + strBillNo;
		return strBillNo;
    }
    /**
	 * 主函数
	 * 
	 * @param args 参数列表
	 */
    public static void main(String[] args){
        
    }
}
