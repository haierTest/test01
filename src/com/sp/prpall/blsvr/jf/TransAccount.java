/******************************************************************************
* DESC       : 收付数据自动挂帐
* AUTHOR     : 李继斌
* CREATEDATE : 2007-04-06
* MODIFYLIST : Name       Date            Reason/Contents
*              lijibin    2004-08-31      日志改用英文，由于中文显示乱码
******************************************************************************/
package com.sp.prpall.blsvr.jf;







import com.sp.utiall.blsvr.BLPrpDcompany;
import com.sp.utility.SysConst;
import com.sp.utility.string.*;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;

public final class TransAccount
{
  private String ACCBOOKTYPE = "02";
  private String ACCBOOKCODE = "11";

  protected final Log logger = LogFactory.getLog(getClass());

  /**
   * 构造函数
   */
  public TransAccount(){
      
  }
  
  
  public void trans(String iCenterCode, String iDate, String iOperatorCode, String iType ,String iServerNo) throws UserException,Exception
  {
      
      BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();

      String iBranchCode = "";
      DbPool dbpool = this.getPool();

      try {
    	  
    	  
    	  if(iServerNo.equals("1")||iServerNo.equals("0")){
              
              if(iType.equals("1")||iType.equals("0")){
                  logger.info("***kai shi zhuan ru "+iDate+"ying shou bao fei ...");
                  blPrpJplanFee.transAccountPE(dbpool,ACCBOOKTYPE,ACCBOOKCODE,iCenterCode,iBranchCode,iDate,iOperatorCode);
                  logger.info("---jie shu zhuan ru "+iDate+"ying shou bao fei ...");
              }
    	  }
    	  
    	  
    	  if(iServerNo.equals("2")||iServerNo.equals("0")){
              
              if(iType.equals("2")||iType.equals("0")){
                  logger.info("***kai shi zhuan ru "+iDate+"ying fu shou xu fei ...");
                  blPrpJplanFee.transAccounts(dbpool,ACCBOOKTYPE,ACCBOOKCODE,iCenterCode,iBranchCode,iDate,iOperatorCode);
                  logger.info("---jie shu zhuan ru "+iDate+"ying fu shou xu fei ...");
              }
    	  }
    	  
    	  
    	  if(iServerNo.equals("3")||iServerNo.equals("0")){
              
              if(iType.equals("3")||iType.equals("0")){
                  logger.info("***kai shi zhuan ru "+iDate+"ying shou che chuan shui ...");
            	  blPrpJplanFee.transAccountCarShipTax(dbpool,ACCBOOKTYPE,ACCBOOKCODE,iCenterCode,iBranchCode,iDate,iOperatorCode);
                  logger.info("***kai shi zhuan ru "+iDate+"ying shou che chuan shui ...");
              }            
              
              
              if(iType.equals("4")||iType.equals("0")){
                  logger.info("***kai shi zhuan ru "+iDate+"ying fu pei kuan  ...");
                  blPrpJplanFee.transAccountCAll(dbpool,ACCBOOKTYPE,ACCBOOKCODE,iCenterCode,iBranchCode,iDate,iOperatorCode);
                  logger.info("---jie shu zhuan ru "+iDate+"ying fu pei kuan ...");
              }
              
              
              if(iType.equals("5")||iType.equals("0")){
                  logger.info("***kai shi zhuan ru "+iDate+"zhui chang kuan shou ru ...");
                  blPrpJplanFee.transAccountZAll(dbpool,ACCBOOKTYPE,ACCBOOKCODE,iCenterCode,iBranchCode,iDate,iOperatorCode);
                  logger.info("---jie shu zhuan ru "+iDate+"zhui chang kuan shou ru ...");
              }
              
              
              if(iType.equals("6")||iType.equals("0")){
                  logger.info("***kai shi zhuan ru "+iDate+"yu yue xie yi jie suan  ...");
                  BLPrpJsettle blPrpJsettle = new BLPrpJsettle();
                  blPrpJsettle.settleAll(dbpool,iDate,iCenterCode);
                  logger.info("***kai shi zhuan ru "+iDate+"yu yue xie yi jie suan  ...");
              }    
    	  }
    	  
    	  BLPrpJplanCommission blPrpJplanCommission = null;
    	  
    	  if(iServerNo.equals("90")){
    		  blPrpJplanCommission  = new BLPrpJplanCommission();
    		  String[] strCenterCode = new String[] { "00","01", "02", "03", "04" };
    		  for(int i=0;i<strCenterCode.length;i++){
                  blPrpJplanCommission.transHisCommission(dbpool,strCenterCode[i]);  
    		  }
    	  }
    	  
    	  
    	  if(iServerNo.equals("91")){
    		  blPrpJplanCommission  = new BLPrpJplanCommission();
    		  String[] strCenterCode = new String[] { "05", "06", "07", "08",
    				  								  "09", "10" };
    		  for(int i=0;i<strCenterCode.length;i++){
                  blPrpJplanCommission.transHisCommission(dbpool,strCenterCode[i]);  
    		  }
    	  }
    	  
    	  
    	  if(iServerNo.equals("93")){
    		  blPrpJplanCommission  = new BLPrpJplanCommission();
    		  String[] strCenterCode = new String[] { "11", "12" , "15" , "16", 
    				  								  "17", "18" , "19" , "20"};
    		  for(int i=0;i<strCenterCode.length;i++){
                  blPrpJplanCommission.transHisCommission(dbpool,strCenterCode[i]);  
    		  }
    	  }
    	  
    	  
    	  if(iServerNo.equals("94")){
    		  blPrpJplanCommission  = new BLPrpJplanCommission();
    		  String[] strCenterCode = new String[] { "21",  "23", "24", 
    				  								  "25",  "26", "27", "28",
    				  								  "29",  "30", "31", "32"};
    		  for(int i=0;i<strCenterCode.length;i++){
                  blPrpJplanCommission.transHisCommission(dbpool,strCenterCode[i]);  
    		  }
    	  }
    	  
    	  
    	  if(iServerNo.equals("99")&&!iType.equals("0")){
    		  blPrpJplanCommission  = new BLPrpJplanCommission();
              blPrpJplanCommission.transHisCommission(dbpool,iType);  
    	  }
    	  
    	  
    	  
    	  if(iServerNo.equals("29_1")){
    		  BLPrpJinvestIncome blPrpJinvestIncome  = new BLPrpJinvestIncome();
    		  BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
  			  String strWherePart = " CenterFlag ='1' AND ValidStatus='1' AND (ComCode like '02%' OR ComCode like '03%' OR ComCode like '04%')";
			  strWherePart += " ORDER BY ComCode";
			  blPrpDcompany.query(dbpool, strWherePart, 0);
			  for (int i =0;i<blPrpDcompany.getSize() ;i++){
				  String strCenterCode = blPrpDcompany.getArr(i).getComCode();
	    		  blPrpJinvestIncome.transIncomeAll(dbpool,"0","0", ACCBOOKTYPE,ACCBOOKCODE,strCenterCode,strCenterCode,
	    				  iDate, iOperatorCode);
			  }
    	  }
    	  
    	  if(iServerNo.equals("29_2")){
    		  BLPrpJinvestIncome blPrpJinvestIncome  = new BLPrpJinvestIncome();
    		  BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
  			  String strWherePart = " CenterFlag ='1' AND ValidStatus='1' AND ComCode like '15%' ";
			  strWherePart += " ORDER BY ComCode";
			  blPrpDcompany.query(dbpool, strWherePart, 0);
			  for (int i =0;i<blPrpDcompany.getSize() ;i++){
				  String strCenterCode = blPrpDcompany.getArr(i).getComCode();
	    		  blPrpJinvestIncome.transIncomeAll(dbpool,"0","0", ACCBOOKTYPE,ACCBOOKCODE,strCenterCode,strCenterCode,
	    				  iDate, iOperatorCode);
			  }
    	  }
    	  
    	  if(iServerNo.equals("29_3")){
    		  BLPrpJinvestIncome blPrpJinvestIncome  = new BLPrpJinvestIncome();
    		  BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
  			  String strWherePart = " CenterFlag ='1' AND ValidStatus='1' AND ComCode not like '02%' AND ComCode not like '03%' AND ComCode not like '04%' AND ComCode not like '15%' ";
			  strWherePart += " ORDER BY ComCode";
			  blPrpDcompany.query(dbpool, strWherePart, 0);
			  for (int i =0;i<blPrpDcompany.getSize() ;i++){
				  String strCenterCode = blPrpDcompany.getArr(i).getComCode();
	    		  blPrpJinvestIncome.transIncomeAll(dbpool,"0","0", ACCBOOKTYPE,ACCBOOKCODE,strCenterCode,strCenterCode,
	    				  iDate, iOperatorCode);
			  }
    	  }    	  
    	  
    	  
    	  
    	  if(iServerNo.equals("2900")){
    		  BLPrpJinvestIncome blPrpJinvestIncome  = new BLPrpJinvestIncome();
    		  blPrpJinvestIncome.transIncomeLastTime(dbpool,"0","0", ACCBOOKTYPE,ACCBOOKCODE,iCenterCode,iBranchCode,
    				  iDate, iOperatorCode);
    	  }
    	  
    	  
    	  
    	  if(iServerNo.equals("100")){
    		  BLPrpJinvest blPrpJinvest  = new BLPrpJinvest();
    		  blPrpJinvest.autoCreateInvest(dbpool, iCenterCode, iDate, iOperatorCode);
    	  }
    	  
    	  
      } catch(UserException ue) {
          throw ue;
      } catch(Exception e) {
          throw e;
      }finally{
          dbpool.close();
      }      
  }

  /**
   * 创建一个新的数据库连接
   * @return 数据库连接对象
   * @throw UserException,Exception
   */
  private DbPool getPool() throws UserException,Exception
  {
    DbPool dbpool = new DbPool();

    
    String strUrl  = SysConst.getProperty("DB_URL");
    String strUser = SysConst.getProperty("DB_USER");
    String strPwd  = SysConst.getProperty("DB_PASSWORD");



    dbpool.openOra(strUrl,strUser,strPwd);

    return dbpool;
  }

  
  /**
   * 显示用法
   */
  private static void showUsage()
  {
	
	Log logger = LogFactory.getLog(TransAccount.class.getName());
	StringBuffer bufferTemp = new StringBuffer(200);
	bufferTemp.append("Usage: java TransAccount {conf} [serverNo] [date] [centercode] [type] [operatorcode] ");
	bufferTemp.append("     serverNo: 分三台服务器挂帐,对应为1、2、3，0则表示对所有类型挂帐");
	bufferTemp.append("         conf: SysConstConfig_sff.xml file");
	bufferTemp.append("         date: trans-account date(yyyy-MM-dd),the default gen-date is yesterday");
	bufferTemp.append("   centercode: the default centercode is all centercode");
	bufferTemp.append("         type: 0 all types,the default type");
	bufferTemp.append("               1 policy&endor");
	bufferTemp.append("               2 compensate");
	bufferTemp.append(" operatorcode: the default operatorcode is admin(00000000)");
	logger.info(bufferTemp.toString());
	
  }

  /**
   * 主函数
   */
  public static void main(String args[])
    throws UserException,Exception
  {
    
    String strCenterCode = "";
    String strDate = "";
    String strType = "0";
    String strOperatorCode = "00000000";
    String serverNo ="0";

    
    if(args.length<1)
    {
      showUsage();
      System.exit(0);
    }

    
    if(args.length>=2){
      serverNo = args[1];	
    }
    
    if(args.length>=3){
      strDate = args[2];
    }
    else{
      com.sp.utility.string.Date sinosoftDate = new com.sp.utility.string.Date();
      sinosoftDate.set(sinosoftDate.DATE,sinosoftDate.get(sinosoftDate.DATE)-1);
      strDate = new ChgDate().toFormat(sinosoftDate.getString(sinosoftDate.YEAR+sinosoftDate.MONTH+sinosoftDate.DATE));
    }
    
    
    if(args.length>=4){
      strCenterCode = args[3];
    }
    
    
    if(args.length>=5){
      strType = args[4];
    }
    
    
    if(args.length>=6){
      strOperatorCode = args[5];
    }

    

    SysConst.init(args[0],false);
	  
    TransAccount transAccount = new TransAccount();
    transAccount.trans(strCenterCode, strDate, strOperatorCode, strType,serverNo);
  }
}
