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
 * ����PrpJBankCheckMain-���ж�����Ϣ�����BL��
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
     * ���캯��
     */       
    public BLPrpJBankCheckMain(){
    }
	/**
	 * @desc ��ʼ�����˵����ʹ���������Map
	 *
	 */
	private static synchronized void initBillTypeNameMap(){
		billTypeNameMap.put("01", "POS���˵�");
		billTypeNameMap.put("99", "��������");
	}
	/**
	 * @desc ��ʼ�����˵��ʵ����д���������Map
	 *
	 */
	private static synchronized void initBankCodeNameMap(){
		bankCodeNameMap.put("01", "��������");
		bankCodeNameMap.put("99", "��������");
	}
	/**
	 * @desc ��ʼ�����˵���Ԫ����λ��Map
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
	 * @desc ��ʼ����������ӦMap
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
	 * @desc ��ʼ���������������ƶ�ӦMap
	 *
	 */
	private static synchronized void initErrorCodeNameMap(){
		errorCodeNameMap.put("01", "ϵͳ�޸ý���");
		errorCodeNameMap.put("02", "ϵͳ��Ӧ��¼>1��");
		errorCodeNameMap.put("03", "�������ڲ�һ��");
		errorCodeNameMap.put("04", "���׽�һ��");
		errorCodeNameMap.put("99", "��������");
	}
	/**
	 * @desc ��ȡ�����Ӧ����
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
			nameValue = "δ֪";
		}
		if(nameValue==null||nameValue.equals(""))
			nameValue="δ֪";
		return nameValue;
	}
    /**
     *��ʼ����¼
     *@param ��
     *@return ��
     *@throws Exception
     */
    public void initArr() throws Exception
    {
       schemas = new ArrayList();
       errorBillNoList = new ArrayList();
     }
    /**
     *����һ��PrpJBankCheckMainSchema��¼
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
     *�õ�һ��PrpJBankCheckMainSchema��¼
     *@param index �±�
     *@return һ��PrpJBankCheckMainSchema����
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
     *ɾ��һ��PrpJBankCheckMainSchema��¼
     *@param index �±�
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
     *�õ�schemas��¼��
     *@return schemas��¼��
     *@throws Exception
     */
    public int getSize() throws Exception
    {
        return this.schemas.size();
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart) throws UserException,Exception
    {
       this.query(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
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
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
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
     *��dbpool��save����
     *@param ��
     *@return ��
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
     *����dbpool��XXXXX�淽��
     *@param ��
     *@return ��
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
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param billNo billNo
     *@return ��
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
     * ����dbpool��ɾ������
     *@param billNo billNo
     *@return ��
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
     * ��dbpool����billNo��ȡ����
     *@param billNo billNo
     *@return ��
     */
    public void getData(String billNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
      
      getData(dbpool,billNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����billNo��ȡ����
     *@param dbpool ���ӳ�
     *@param billNo billNo
     *@return ��
     */
    public void getData(DbPool dbpool,String billNo) throws Exception
    {
    String strWherePart = "";
    strWherePart = "BillNo= '" + billNo + "'";
    query(dbpool,strWherePart,0);
    }
    /**
     * @desc ���ж��˵�����-����dbpool
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
	 * @desc ���ж��˵�����-��dbpool
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
    		throw new UserException(-98,-1200,"BLPrpJBankCheckMain.billImport","�ʵ������ȡʧ�ܣ�");
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
			throw new UserException(-98,-1200,"BLPrpJBankCheckMain.billImport","û�н�����Ϣ�����赼�룡");
		prpJBankCheckMainSchema.setBillDate(blPrpJBankCheckDetail.getArr(0).getTransDate());
		dbPrpJBankCheckMain.setSchema(prpJBankCheckMainSchema);
		
		dbPrpJBankCheckMain.insert(dbPool);
		
    	return strBillNo;
    }
    /**
     * @desc ѭ���˶����ж��ʵ�,ÿһ�����˵�һ������
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
     * @desc �Ե������ж��ʵ����к˶�
     * @author liufengyao
     * @param arrBillNo ���ʵ�����
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
    		throw new UserException(-98,-1200,"BLPrpJBankCheckMain.billCheck","���ж�����Ϣ�����в������ʵ����"+iBillNo+"��Ӧ��Ϣ!");
    	
    	strSQL = " BillNo = '"+iBillNo+"' Order By SerialNo";
    	blPrpJBankCheckDetail.query(dbpool, strSQL, 0);
    	if(blPrpJBankCheckDetail.getSize()==0)
    		throw new UserException(-98,-1200,"BLPrpJBankCheckMain.billCheck","���ж�����Ϣ��ϸ���в������ʵ����"+iBillNo+"��Ӧ��Ϣ!");
    	
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
     * @desc ɾ���ѵ��롢δ�˶��ʵ�
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
     * @desc ɾ���ѵ��롢δ�˶Զ��˵�
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
     * @desc �����ʵ���ϸ����Ϣ
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
			throw new UserException(-98,-1200,"BLPrpJBankCheckMain.setBankBillDetailInfo","����ȡֵԽ���쳣,���鵼����˵���ʽ!");
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
     * @desc ת�����ݸ�ʽ
     * @author liufengyao
     * @param DateType ��������
     * @param iData    ��ת������
     * @return ת������
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
    			throw new UserException(-98,-1200,"BLPrpJBankCheckMain.transDataFormat","POS�ն˺�"+iData+"�������ʽ:����3λ,���鵼����˵��ļ�!");
    		}else{
        		convDate = iData;
    		}
    	}else if(DataType.equals("POSVOUCHERNO")){
    		
    		if(iData.length()>8){
    			throw new UserException(-98,-1200,"BLPrpJBankCheckMain.transDataFormat","�����ο���"+iData+"�������ʽ:���ܴ���8λ,���鵼����˵��ļ�!");
    		}else if(iData.length()<=0){
    			throw new UserException(-98,-1200,"BLPrpJBankCheckMain.transDataFormat","�����ο���"+iData+"�������ʽ:����Ϊ��,���鵼����˵��ļ�!");
    		}else if(iData.length()< 8){
    			int fillLength = 8 - iData.length();
    			convDate = Str.newString("0", fillLength) + iData;
    		}else{
    			convDate = iData;
    		}
    	}else if(DataType.equals("CUSTOMNO")){
    		
    		if(iData.length()>20){
    			throw new UserException(-98,-1200,"BLPrpJBankCheckMain.transDataFormat","�̻���"+iData+"�������ʽ:���ܴ���20λ,���鵼����˵��ļ�!");
    		}else if(iData.length()<=0){
    			throw new UserException(-98,-1200,"BLPrpJBankCheckMain.transDataFormat","�̻���"+iData+"�������ʽ:����Ϊ��,���鵼����˵��ļ�!");
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
     * @desc ��ȡ�ʵ����,������ݿ��иñ���Ѿ����ڣ������ȡ��һ��
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
     * @desc ��ȡ�ʵ����
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
	 * ������
	 * 
	 * @param args �����б�
	 */
    public static void main(String[] args){
        
    }
}
