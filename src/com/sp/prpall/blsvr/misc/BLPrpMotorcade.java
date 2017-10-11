package com.sp.prpall.blsvr.misc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.blsvr.tb.BLProposalBatch;
import com.sp.prpall.blsvr.tb.BLPrpTfee;
import com.sp.prpall.blsvr.tb.BLPrpTinsured;
import com.sp.prpall.blsvr.tb.BLPrpTmain;
import com.sp.prpall.blsvr.tb.BLPrpTplan;
import com.sp.prpall.blsvr.tb.BLPrpTprofitDetail;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.misc.DBPrpMotorcade;
import com.sp.prpall.dbsvr.tb.DBPrpTinsured;
import com.sp.prpall.dbsvr.tb.DBPrpTitemCar;
import com.sp.prpall.dbsvr.tb.DBPrpTmain;
import com.sp.prpall.dbsvr.tb.DBPrpTplan;
import com.sp.prpall.schema.PrpMotorcadeSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTplanSchema;
import com.sp.prpall.schema.TMotorcadeSchema;
import com.sp.utility.StringConvertor;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 定义PrpMotorcade的BL类
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-07-17</p>
 * @Author     : X
 * @version 1.0
 */
public class BLPrpMotorcade
{
	private Vector schemas = new Vector();

	/**
	 * 构造函数
	 */
	public BLPrpMotorcade ()
	{
	}

	/**
	 *初始化记录
	 *@param 无
	 *@return 无
	 *@throws Exception
	 */
	public void initArr () throws Exception
	{
		schemas = new Vector();
	}

	/**
	 *增加一条PrpMotorcadeSchema记录
	 *@param iPrpMotorcadeSchema PrpMotorcadeSchema
	 *@throws Exception
	 */
	public void setArr ( PrpMotorcadeSchema iPrpMotorcadeSchema ) throws Exception
	{
		try
		{
			schemas.add( iPrpMotorcadeSchema );
		}
		catch ( Exception e )
		{
			throw e;
		}
	}

	/**
	 *得到一条PrpMotorcadeSchema记录
	 *@param index 下标
	 *@return 一个PrpMotorcadeSchema对象
	 *@throws Exception
	 */
	public PrpMotorcadeSchema getArr ( int index ) throws Exception
	{
		PrpMotorcadeSchema prpMotorcadeSchema = null;
		try
		{
			prpMotorcadeSchema = ( PrpMotorcadeSchema )this.schemas.get( index );
		}
		catch ( Exception e )
		{
			throw e;
		}
		return prpMotorcadeSchema;
	}

	/**
	 *删除一条PrpMotorcadeSchema记录
	 *@param index 下标
	 *@throws Exception
	 */
	public void remove ( int index ) throws Exception
	{
		try
		{
			this.schemas.remove( index );
		}
		catch ( Exception e )
		{
			throw e;
		}
	}

	/**
	 *得到schemas记录数
	 *@return schemas记录数
	 *@throws Exception
	 */
	public int getSize () throws Exception
	{
		return this.schemas.size();
	}

	/**
	 *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 *@param iWherePart 查询条件(包括排序字句)
	 *@throws UserException
	 *@throws Exception
	 */
	public void query ( String iWherePart ) throws UserException, Exception
	{
		this.query( iWherePart, Integer.parseInt( SysConfig.getProperty( "QUERY_LIMIT_COUNT" ).trim() ) );
	}

	/**
	 *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 *@param iWherePart 查询条件(包括排序字句)
	 *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
	 *@throws UserException
	 *@throws Exception
	 */
	public void query ( String iWherePart, int iLimitCount ) throws UserException, Exception
	{
		String strSqlStatement = "";
		DBPrpMotorcade dbPrpMotorcade = new DBPrpMotorcade();
		if ( iLimitCount > 0 && dbPrpMotorcade.getCount( iWherePart ) > iLimitCount )
		{
			throw new UserException( -98, -1003, "BLPrpMotorcade.query" );
		}
		else
		{
			initArr();
			strSqlStatement = " SELECT * FROM PrpMotorcade WHERE " + iWherePart;
			schemas = dbPrpMotorcade.findByConditions( strSqlStatement );
		}
	}

	
	/**
	 *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 *@param iWherePart 查询条件(包括排序字句)
	 *@throws UserException
	 *@throws Exception
	 */
	public void queryHistory ( String iWherePart ) throws UserException, Exception
	{
		this.queryHistory( iWherePart, Integer.parseInt( SysConfig.getProperty( "QUERY_LIMIT_COUNT" ).trim() ) );
	}

	/**
	 *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 *@param iWherePart 查询条件(包括排序字句)
	 *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
	 *@throws UserException
	 *@throws Exception
	 */
	public void queryHistory ( String iWherePart, int iLimitCount ) throws UserException, Exception
	{
		String strSqlStatement = "";
		DBPrpMotorcade dbPrpMotorcade = new DBPrpMotorcade();
		if ( iLimitCount > 0 && dbPrpMotorcade.getCountHistory( iWherePart ) > iLimitCount )
		{
			throw new UserException( -98, -1003, "BLPrpMotorcade.query" );
		}
		else
		{
			initArr();
			strSqlStatement = " SELECT * FROM PrpMotorcade WHERE " + iWherePart;
			schemas = dbPrpMotorcade.findByConditionsHistory( strSqlStatement );
		}
	}	
	
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象。先从生产库获取相关信息，如果未取到，再从历史信息库获取。
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart) throws UserException,Exception
    {
       
       query(iWherePart);
       
       if(this.getSize() == 0){
    	   queryHistory(iWherePart);
       }
    }  
    
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象。先从生产库获取相关信息，如果未取到，再从历史信息库获取。
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart, int iLimitCount) throws UserException,Exception
    {
       
       query(iWherePart,iLimitCount);
       
       if(this.getSize() == 0){
    	   queryHistory(iWherePart,iLimitCount);
       }
    }  
	
	/**
	 *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 *@param dbpool 全局池
	 *@param iWherePart 查询条件(包括排序字句)
	 *@throws UserException
	 *@throws Exception
	 */
	public void query ( DbPool dbpool, String iWherePart ) throws UserException, Exception
	{
		this.query( dbpool, iWherePart, Integer.parseInt( SysConfig.getProperty( "QUERY_LIMIT_COUNT" ).trim() ) );
	}

	/**
	 *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 *@param dbpool 全局池
	 *@param iWherePart 查询条件(包括排序字句)
	 *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
	 *@throws UserException
	 *@throws Exception
	 */
	public void query ( DbPool dbpool, String iWherePart, int iLimitCount ) throws UserException, Exception
	{
		String strSqlStatement = "";
		DBPrpMotorcade dbPrpMotorcade = new DBPrpMotorcade();
		if ( iLimitCount > 0 && dbPrpMotorcade.getCount( iWherePart ) > iLimitCount )
		{
			throw new UserException( -98, -1003, "BLPrpMotorcade.query" );
		}
		else
		{
			initArr();
			strSqlStatement = " SELECT * FROM PrpMotorcade WHERE " + iWherePart;
			schemas = dbPrpMotorcade.findByConditions( dbpool, strSqlStatement );
		}
	}
	
	/**
	 * 车队协议分页查询 
	 * @author luogang 20090403
	 * @param dbpool
	 * @param iWherePart
	 * @param intPageNum
	 * @param intPageCount
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, int intPageNum, int intPageCount) throws UserException, Exception {
		String strSqlStatement = "";
		int intStartNum = 0;
		int intEndNum = 0;

		intStartNum = (intPageNum - 1) * intPageCount;
		intEndNum = intPageNum * intPageCount;

		DBPrpMotorcade dbPrpMotorcade = new DBPrpMotorcade();
		initArr();

		strSqlStatement = " SELECT * FROM ( " + "Select RowNum As LineNum,T.* From ( " + "Select * From PrpMotorcade Where "
				+ iWherePart + ") T Where RowNum<=" + intEndNum + ") Where LineNum>" + intStartNum + " Order By ContractNo DESC";

		schemas = dbPrpMotorcade.findByConditions(dbpool, strSqlStatement);
	}

	/**
	 * 车队协议分页查询
	 * @author luogang 20090403
	 * @param iWherePart
	 * @param intPageNum
	 * @param intPageCount
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart, int intPageNum, int intPageCount) throws UserException, Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			this.query(dbpool, iWherePart, intPageNum, intPageCount);
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
	}
	
	/**
	 *带dbpool的save方法
	 *@param 无
	 *@return 无
	 */
	public void save ( DbPool dbpool ) throws Exception
	{
		DBPrpMotorcade dbPrpMotorcade = new DBPrpMotorcade();

		int i = 0;

		for ( i = 0; i < schemas.size(); i++ )
		{
			dbPrpMotorcade.setSchema( ( PrpMotorcadeSchema ) schemas.get( i ) );
			dbPrpMotorcade.insert( dbpool );
		}
	}

	/**
	 *不带dbpool的XXXXX存方法
	 *@param 无
	 *@return 无
	 */
	public void save () throws Exception
	{
		DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			save( dbpool );
			dbpool.commitTransaction();
		}
		catch ( Exception e )
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
	 *@param null null
	 *@return 无
	 */
	public void cancel ( DbPool dbpool, String certino ) throws Exception
	{




		String strSqlStatement = " DELETE FROM PrpMotorcade WHERE certino= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, certino);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
	}

	/**
	 * 不带dbpool的删除方法
	 *@param null null
	 *@return 无
	 */
	public void cancel ( String certino ) throws Exception
	{
		DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			cancel( dbpool, null );
			dbpool.commitTransaction();
		}
		catch ( Exception e )
		{
			dbpool.rollbackTransaction();
		}
    finally {
      dbpool.close();
		}
	}

	/**
	 * 带dbpool根据null获取数据
	 *@param null null
	 *@return 无
	 */
	public void getData ( String certino ) throws Exception
	{
		DbPool dbpool = new DbPool();

		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			getData(dbpool, null);
		} catch (Exception e) {
			
		}finally {		
		dbpool.close();
		}

	}

	/**
	 * 不带dbpool根据null获取数据
	 *@param dbpool 连接池
	 *@param null null
	 *@return 无
	 */
	public void getData ( DbPool dbpool, String certino ) throws Exception
	{
	    
		
		
		
	    String strWherePart = " certino= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(certino);
        query(dbpool, strWherePart, arrWhereValue, 0);
		
	}
	
	  /**
	   *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	   *@author yangxiaodong 20100602
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
	      DBPrpMotorcade dbPrpMotorcade = new DBPrpMotorcade();
	      if (iLimitCount > 0 && dbPrpMotorcade.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
	      {
	          throw new UserException(-98,-1003,"BLPrpMotorcade.query");
	      }else
	      {
	          initArr();
	          strSqlStatement = " SELECT * FROM PrpMotorcade WHERE " + iWherePart;
	          schemas = dbPrpMotorcade.findByConditions(dbpool,strSqlStatement,iWhereValue);
	      }
	  }
	  

	
	/*根据XX单信息生成车队公共信息
	 *@autor liuyang
	 *@param blPrpTmain    所有单车信息
	 *@param strContractNo 合同号
	 *@throws UserException
	 *@throws Exception
	 */
	public BLTMotorcade generateBLTMotorcade ( BLPrpTmain blPrpTmain, String strContractNo ) throws UserException, Exception
	{
		int i = 0;
		double dblContractPremium = 0;
		BLTMotorcade blTMotorcade = new BLTMotorcade();
		DBPrpMotorcade dbPrpMotorcade = new DBPrpMotorcade();
		PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
		DBPrpTinsured dbPrpTinsured = new DBPrpTinsured();
		TMotorcadeSchema tMotorcadeSchema = new TMotorcadeSchema();
		prpTmainSchema = blPrpTmain.getArr( 0 );
		dbPrpMotorcade.getInfo( strContractNo );
		
		for ( i = 0; i < blPrpTmain.getSize(); i++ )
		{
			dblContractPremium = dblContractPremium +
				Double.parseDouble( blPrpTmain.getArr( i ).getSumPremium() );
		}
		tMotorcadeSchema.setContractNo( strContractNo );
		tMotorcadeSchema.setRiskCode( prpTmainSchema.getRiskCode() );
		tMotorcadeSchema.setBusinessNature( prpTmainSchema.getBusinessNature() );
		tMotorcadeSchema.setArgueSolution( prpTmainSchema.getArgueSolution() );
		tMotorcadeSchema.setArbitBoardName( prpTmainSchema.getArbitBoardName() );
		tMotorcadeSchema.setPayTimes( Integer.parseInt( prpTmainSchema.getPayTimes() ) );
		tMotorcadeSchema.setHandlerCode( prpTmainSchema.getHandlerCode() );
		tMotorcadeSchema.setHandler1Code( prpTmainSchema.getHandler1Code() );
		tMotorcadeSchema.setComCode( prpTmainSchema.getComCode() );
		tMotorcadeSchema.setAgentCode( prpTmainSchema.getAgentCode() );
		tMotorcadeSchema.setOperatorCode( prpTmainSchema.getOperatorCode() );
		tMotorcadeSchema.setApproverCode( prpTmainSchema.getAppliCode() );
		tMotorcadeSchema.setSumPremium( dblContractPremium );
		tMotorcadeSchema.setRealCarCount( blPrpTmain.getSize() );
		tMotorcadeSchema.setCarCount( Integer.parseInt( dbPrpMotorcade.getCarCount() ) );
		tMotorcadeSchema.setMinusFlag( dbPrpMotorcade.getMinusFlag() );
		tMotorcadeSchema.setSumPremium( dblContractPremium );
		
		tMotorcadeSchema.setChannelType(prpTmainSchema.getChannelType());
		
		
		dbPrpTinsured.getInfo( prpTmainSchema.getProposalNo(), "1" );
		tMotorcadeSchema.setInsuredCode( dbPrpTinsured.getInsuredCode() );
		tMotorcadeSchema.setInsuredName( dbPrpTinsured.getInsuredName() );
		tMotorcadeSchema.setInsuredAddress( dbPrpTinsured.getInsuredAddress() );
		tMotorcadeSchema.setInsuredNature( dbPrpTinsured.getInsuredNature() );
		tMotorcadeSchema.setBusinessSort( dbPrpTinsured.getBusinessSort() );
		tMotorcadeSchema.setIdentifyNumber( dbPrpTinsured.getIdentifyNumber() );
		tMotorcadeSchema.setInsuredPostCode( dbPrpTinsured.getPostCode() );
		tMotorcadeSchema.setInsuredLinkerName( dbPrpTinsured.getLinkerName() );
		tMotorcadeSchema.setInsuredPhoneNumber( dbPrpTinsured.getPhoneNumber() );
		tMotorcadeSchema.setInsuredMobile( dbPrpTinsured.getMobile() );
		
		dbPrpTinsured = new DBPrpTinsured();
		dbPrpTinsured.getInfo( prpTmainSchema.getProposalNo(), "2" );
		tMotorcadeSchema.setAppliCode( dbPrpTinsured.getInsuredCode() );
		tMotorcadeSchema.setAppliName( dbPrpTinsured.getInsuredName() );
		tMotorcadeSchema.setAppliAddress( dbPrpTinsured.getInsuredAddress() );
		tMotorcadeSchema.setAppliPostCode( dbPrpTinsured.getPostCode() );
		tMotorcadeSchema.setAppliLinkerName( dbPrpTinsured.getLinkerName() );
		tMotorcadeSchema.setAppliPhoneNumber( dbPrpTinsured.getPhoneNumber() );
		tMotorcadeSchema.setAppliPhoneNumber( dbPrpTinsured.getPhoneNumber() );
		tMotorcadeSchema.setAppliMobile( dbPrpTinsured.getMobile() );
		
		tMotorcadeSchema.setAppliEmail(dbPrpTinsured.getEmail());
		tMotorcadeSchema.setAppliCustomerType(dbPrpTinsured.getInsuredType());
		tMotorcadeSchema.setBusinessSort(dbPrpTinsured.getBusinessSort());
		tMotorcadeSchema.setAppliIdentifyType(dbPrpTinsured.getIdentifyType());  
		tMotorcadeSchema.setIdentifyNumber(dbPrpTinsured.getIdentifyNumber());   
		

		
		DBPrpTitemCar dbPrpTitemCar = new DBPrpTitemCar();
		BLPrpTprofitDetail blPrpTprofitDetail = new BLPrpTprofitDetail();
		dbPrpTitemCar.getInfo( prpTmainSchema.getProposalNo(), "1" );
		String strSQL = "";
		double dblMotorCadeCoeff = 1;
		String strMotorcadeNature = "";
		if ( dbPrpTitemCar.getUseNatureCode().equals( "84" )
				 || dbPrpTitemCar.getUseNatureCode().equals( "85" ) )
		{
			strMotorcadeNature = "2";
			strSQL = " ProposalNo = '" + prpTmainSchema.getProposalNo() + "'"
				+ " AND ProfitType = '1' AND KindCode = 'A'"
				+ " AND ProfitCode = 'A92'";
			blPrpTprofitDetail.query( strSQL );
			if ( blPrpTprofitDetail.getSize() > 0 )
			{
				dblMotorCadeCoeff = 1 - Double.parseDouble( blPrpTprofitDetail.getArr( 0 ).getProfitRate() ) / 100;
			}
		}
		else
		{
			strMotorcadeNature = "1";
		}
		tMotorcadeSchema.setMotorCadeNature( strMotorcadeNature );
		tMotorcadeSchema.setMotorCadeCoeff( dblMotorCadeCoeff );
		

		blTMotorcade.setArr( tMotorcadeSchema );
		return blTMotorcade;
	}

	/*批量更新车队公共信息事物函数
	 *@Author     : X
	 *@param blTmotorcade  车队公共信息
	 *@throws UserException
	 *@throws Exception
	 */
	public void updatePubInfoTrans ( BLTMotorcade blTMotorcade ) throws UserException, Exception
	{
		DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			this.updatePubInfo( dbpool, blTMotorcade );
			dbpool.commitTransaction();
			dbpool.close();
		}
		catch ( Exception exception )
		{
			dbpool.rollbackTransaction();
			dbpool.close();
			throw exception;
		}
    finally {
      dbpool.close();
		}
	}

	/*批量更新车队公共信息
	 *@Author     : X
	 *@param blTMotorcade  车队公共信息
	 *@throws UserException
	 *@throws Exception
	 */
	public void updatePubInfo ( DbPool dbpool, BLTMotorcade blTMotorcade ) throws UserException, Exception
	{
		int i = 0;
		int j = 0;
		BLPrpTmain blPrpTmain = new BLPrpTmain();
		blPrpTmain.query( dbpool, "ContractNo = '" + blTMotorcade.getArr( 0 ).getContractNo() + "'", 0 );
		for ( i = 0; i < blPrpTmain.getSize(); i++ )
		{
			PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
			prpTmainSchema = blPrpTmain.getArr( i );
			if ( !prpTmainSchema.getUnderWriteFlag().equals( "1" ) &&
					 !prpTmainSchema.getUnderWriteFlag().equals( "3" ) &&
					 !prpTmainSchema.getUnderWriteFlag().equals( "9" ) )
			{
				prpTmainSchema.setBusinessNature( blTMotorcade.getArr( 0 ).getBusinessNature() );
				prpTmainSchema.setAppliCode( blTMotorcade.getArr( 0 ).getAppliCode() );
				prpTmainSchema.setAppliName( blTMotorcade.getArr( 0 ).getAppliName() );
				prpTmainSchema.setAppliAddress( blTMotorcade.getArr( 0 ).getAppliAddress() );
				prpTmainSchema.setArgueSolution( blTMotorcade.getArr( 0 ).getArgueSolution() );
				prpTmainSchema.setArbitBoardName( blTMotorcade.getArr( 0 ).getArbitBoardName() );
				prpTmainSchema.setHandlerCode( blTMotorcade.getArr( 0 ).getHandlerCode() );
				prpTmainSchema.setHandler1Code( blTMotorcade.getArr( 0 ).getHandler1Code() );
				prpTmainSchema.setComCode( blTMotorcade.getArr( 0 ).getComCode() );
				prpTmainSchema.setAgentCode( blTMotorcade.getArr( 0 ).getAgentCode() );
				
				prpTmainSchema.setChannelType(blTMotorcade.getArr( 0 ).getChannelType());
				
				DBPrpTmain dbPrpTmain = new DBPrpTmain();
				dbPrpTmain.setSchema( prpTmainSchema );
				dbPrpTmain.update( dbpool );
			}
			
			BLPrpTinsured blPrpTinsured = new BLPrpTinsured();
			blPrpTinsured.getData( dbpool, prpTmainSchema.getProposalNo() );
			for ( j = 0; j < blPrpTinsured.getSize(); j++ )
			{
				PrpTinsuredSchema prpTinsuredSchema = new PrpTinsuredSchema();
				prpTinsuredSchema = blPrpTinsured.getArr( j );
				if ( prpTinsuredSchema.getInsuredFlag().equals( "2" ) )
				{
					prpTinsuredSchema.setInsuredCode( blTMotorcade.getArr( 0 ).getAppliCode() );
					prpTinsuredSchema.setInsuredName( blTMotorcade.getArr( 0 ).getAppliName() );
					prpTinsuredSchema.setInsuredAddress( blTMotorcade.getArr( 0 ).getAppliAddress() );
					prpTinsuredSchema.setPostCode( blTMotorcade.getArr( 0 ).getAppliPostCode() );
					prpTinsuredSchema.setPhoneNumber( blTMotorcade.getArr( 0 ).getAppliPhoneNumber() );
					prpTinsuredSchema.setMobile( blTMotorcade.getArr( 0 ).getAppliMobile() );
					prpTinsuredSchema.setLinkerName( blTMotorcade.getArr( 0 ).getAppliLinkerName() );
					DBPrpTinsured dbPrpTinsured = new DBPrpTinsured();
					dbPrpTinsured.setSchema( prpTinsuredSchema );
					dbPrpTinsured.update( dbpool );
				}
				
				if ( prpTinsuredSchema.getInsuredFlag().equals( "1" ) &&
						 prpTinsuredSchema.getInsuredCode().equals( blTMotorcade.getArr( 0 ).getAppliCode() ) &&
						 prpTinsuredSchema.getInsuredName().equals( blTMotorcade.getArr( 0 ).getAppliName() ) )
				{
					if ( prpTinsuredSchema.getInsuredAddress().length() == 0 )
					{
						prpTinsuredSchema.setInsuredAddress( blTMotorcade.getArr( 0 ).getAppliAddress() );
					}
					if ( prpTinsuredSchema.getPostCode().length() == 0 )
					{
						prpTinsuredSchema.setPostCode( blTMotorcade.getArr( 0 ).getAppliPostCode() );
					}
					if ( prpTinsuredSchema.getPhoneNumber().length() == 0 )
					{
						prpTinsuredSchema.setPhoneNumber( blTMotorcade.getArr( 0 ).getAppliPhoneNumber() );
					}
					if ( prpTinsuredSchema.getMobile().length() == 0 )
					{
						prpTinsuredSchema.setMobile( blTMotorcade.getArr( 0 ).getAppliMobile() );
					}
					if ( prpTinsuredSchema.getLinkerName().length() == 0 )
					{
						prpTinsuredSchema.setLinkerName( blTMotorcade.getArr( 0 ).getAppliLinkerName() );
					}
					DBPrpTinsured dbPrpTinsured = new DBPrpTinsured();
					dbPrpTinsured.setSchema( prpTinsuredSchema );
					dbPrpTinsured.update( dbpool );
				}
			}
		}

	}

	public void updateCombineCommon( BLTMotorcade blTMotorcade ) throws UserException, Exception{
		DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			this.updateCombineCommon( dbpool, blTMotorcade );
			dbpool.commitTransaction();
		}catch ( Exception exception ){
			dbpool.rollbackTransaction();
			throw exception;
		}finally {
			dbpool.close();
		}
	}
	
	public void updateCombineCommon( DbPool dbpool, BLTMotorcade blTMotorcade ) throws UserException, Exception{
		int i = 0;
		int j = 0;
		BLPrpTmain blPrpTmain = new BLPrpTmain();
		BLPrpMotorcade  blPrpMotorcade=new BLPrpMotorcade();
		PrpMotorcadeSchema prpMotorcadeSchema=null; 
		TMotorcadeSchema tMotorcadeSchema=blTMotorcade.getArr(0);
		blPrpTmain.query( dbpool, " ContractNo = '" + tMotorcadeSchema.getContractNo() + "'", 0 );
		blPrpMotorcade.query(dbpool, " ContractNo = '" + tMotorcadeSchema.getContractNo() + "'"); 
		if(blPrpMotorcade.getSize()>0){
			prpMotorcadeSchema=blPrpMotorcade.getArr(0);
			prpMotorcadeSchema.setAppliCode(tMotorcadeSchema.getAppliCode());
			prpMotorcadeSchema.setAppliName(tMotorcadeSchema.getAppliName());
			prpMotorcadeSchema.setHandlerCode(tMotorcadeSchema.getHandlerCode());     
			prpMotorcadeSchema.setHandler1Code(tMotorcadeSchema.getHandler1Code());   
			prpMotorcadeSchema.setComCodeM(tMotorcadeSchema.getComCode());           
			DBPrpMotorcade dbPrpMotorcade=new DBPrpMotorcade();
			dbPrpMotorcade.setSchema(prpMotorcadeSchema);
			dbPrpMotorcade.update(dbpool);
		}
		for ( i = 0; i < blPrpTmain.getSize(); i++ ){
			PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
			prpTmainSchema = blPrpTmain.getArr( i );
			if ("0".equals(prpTmainSchema.getUnderWriteFlag())||"2".equals(prpTmainSchema.getUnderWriteFlag())){
				prpTmainSchema.setAppliCode(tMotorcadeSchema.getAppliCode());
				prpTmainSchema.setAppliName(tMotorcadeSchema.getAppliName());
				prpTmainSchema.setAppliAddress(tMotorcadeSchema.getAppliAddress());
				prpTmainSchema.setArgueSolution(tMotorcadeSchema.getArgueSolution());
				
				if("2".equals(StringConvertor.changeNullToEmpty(prpTmainSchema.getArgueSolution()))){
					prpTmainSchema.setArbitBoardName(tMotorcadeSchema.getArbitBoardName());
				}else{
					prpTmainSchema.setArbitBoardName(""); 
				}
				prpTmainSchema.setHandlerCode(tMotorcadeSchema.getHandlerCode());
				prpTmainSchema.setHandler1Code(tMotorcadeSchema.getHandler1Code());
				prpTmainSchema.setComCode(tMotorcadeSchema.getComCode());
				
				prpTmainSchema.setChannelType(tMotorcadeSchema.getChannelType());
				
				DBPrpTmain dbPrpTmain = new DBPrpTmain();
				dbPrpTmain.setSchema( prpTmainSchema );
				dbPrpTmain.update(dbpool);
			}
			
			BLPrpTinsured blPrpTinsured = new BLPrpTinsured();
			blPrpTinsured.getData( dbpool, prpTmainSchema.getProposalNo() );
			for ( j = 0; j < blPrpTinsured.getSize(); j++ ){
				PrpTinsuredSchema prpTinsuredSchema = new PrpTinsuredSchema();
				prpTinsuredSchema = blPrpTinsured.getArr( j );
				if("0".equals(prpTmainSchema.getUnderWriteFlag())||"2".equals(prpTmainSchema.getUnderWriteFlag())){
					if ("2".equals(prpTinsuredSchema.getInsuredFlag())){  
						prpTinsuredSchema.setInsuredCode(tMotorcadeSchema.getAppliCode());
						prpTinsuredSchema.setInsuredName(tMotorcadeSchema.getAppliName());
						prpTinsuredSchema.setInsuredAddress(tMotorcadeSchema.getAppliAddress());
						prpTinsuredSchema.setLinkerName(tMotorcadeSchema.getAppliLinkerName());
						prpTinsuredSchema.setPhoneNumber(tMotorcadeSchema.getAppliPhoneNumber());
						prpTinsuredSchema.setMobile(tMotorcadeSchema.getAppliMobile());
						prpTinsuredSchema.setPostCode(tMotorcadeSchema.getAppliPostCode());
						prpTinsuredSchema.setEmail(tMotorcadeSchema.getAppliEmail());
						prpTinsuredSchema.setInsuredNature(tMotorcadeSchema.getAppliNature());
						if("3".equals(tMotorcadeSchema.getAppliNature())){  
							prpTinsuredSchema.setBusinessSort("");  
							prpTinsuredSchema.setInsuredType("1");  
							prpTinsuredSchema.setIdentifyType("");
							prpTinsuredSchema.setIdentifyNumber("");
						}else{
							prpTinsuredSchema.setBusinessSort(tMotorcadeSchema.getBusinessSort());
							prpTinsuredSchema.setInsuredType("2");   
							prpTinsuredSchema.setIdentifyType("99");
							prpTinsuredSchema.setIdentifyNumber(tMotorcadeSchema.getIdentifyNumber());
						}
						prpTinsuredSchema.setRelateSerialNo("");
						prpTinsuredSchema.setBenefitRate("");
						prpTinsuredSchema.setRelateItemKindNo("");
						DBPrpTinsured dbPrpTinsured = new DBPrpTinsured();
						dbPrpTinsured.setSchema(prpTinsuredSchema);
						dbPrpTinsured.update(dbpool);
					}
				}
			}
		}
	}
	
	
	/*更新交费次数事物函数
	 *@Author     : X
	 *@param blTmotorcade  车队公共信息
	 *@throws UserException
	 *@throws Exception
	 */
	public void updatePayTimesTrans ( String strContractNo, int iPayTimes ) throws UserException, Exception
	{
		DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			this.updatePayTimes( dbpool, strContractNo, iPayTimes );
			dbpool.commitTransaction();
			dbpool.close();
		}
		catch ( Exception exception )
		{
			dbpool.rollbackTransaction();
			dbpool.close();
			throw exception;
		}
    finally {
      dbpool.close();
		}
	}

	/*更新交费次数信息
	 *@Author     : X
	 *@param blTMotorcade  车队公共信息
	 *@throws UserException
	 *@throws Exception
	 */
	public void updatePayTimes ( DbPool dbpool, String strContractNo, int iPayTimes ) throws UserException, Exception
	{
		int i = 0;
		int j = 0;
		BLPrpTmain blPrpTmain = new BLPrpTmain();
		BLProposalBatch blProposalBatch = new BLProposalBatch();
		blPrpTmain.query( dbpool, "ContractNo = '" + strContractNo + "'", 0 );
		for ( i = 0; i < blPrpTmain.getSize(); i++ )
		{
			PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
			prpTmainSchema = blPrpTmain.getArr( i );
			if ( !prpTmainSchema.getUnderWriteFlag().equals( "1" ) &&
					 !prpTmainSchema.getUnderWriteFlag().equals( "3" ) &&
					 !prpTmainSchema.getUnderWriteFlag().equals( "9" ) )
			{
				prpTmainSchema.setPayTimes( String.valueOf( iPayTimes ) );
				DBPrpTmain dbPrpTmain = new DBPrpTmain();
				dbPrpTmain.setSchema( prpTmainSchema );
				dbPrpTmain.update( dbpool );
				
				BLPrpTfee blPrpTfee = new BLPrpTfee();
				blPrpTfee.getData( dbpool, prpTmainSchema.getProposalNo() );
				BLPrpTplan blPrpTplan = new BLPrpTplan();
				blPrpTplan = blProposalBatch.generateTplan( prpTmainSchema, blPrpTfee, blPrpTplan );
				blPrpTplan.cancel( dbpool, prpTmainSchema.getProposalNo() );
				blPrpTplan.save( dbpool );
			}
		}
	}
	
	/*
	 */
	public void updatePayTimesTrans(String strContractNo, String strRiskCode, int iPayTimes) throws UserException, Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			this.updatePayTimes(dbpool, strContractNo, strRiskCode, iPayTimes);
			dbpool.commitTransaction();
			dbpool.close();
		} catch (Exception exception) {
			dbpool.rollbackTransaction();
			dbpool.close();
			throw exception;
		} finally {
			dbpool.close();
		}
	}

	/*
	 */
	public void updatePayTimes(DbPool dbpool, String strContractNo, String strRiskCode, int iPayTimes) throws UserException,
			Exception {
		int i = 0;
		BLPrpTmain blPrpTmain = new BLPrpTmain();
		BLProposalBatch blProposalBatch = new BLProposalBatch();
		blPrpTmain.query(dbpool, " RiskCode='"+strRiskCode+"' and ContractNo = '" + strContractNo + "'", 0);
		for (i = 0; i < blPrpTmain.getSize(); i++) {
			PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
			prpTmainSchema = blPrpTmain.getArr(i);
			if (!prpTmainSchema.getUnderWriteFlag().equals("1") && !prpTmainSchema.getUnderWriteFlag().equals("3")
					&& !prpTmainSchema.getUnderWriteFlag().equals("9")) {
				prpTmainSchema.setPayTimes(String.valueOf(iPayTimes));
				DBPrpTmain dbPrpTmain = new DBPrpTmain();
				dbPrpTmain.setSchema(prpTmainSchema);
				dbPrpTmain.update(dbpool);
				
				BLPrpTfee blPrpTfee = new BLPrpTfee();
				blPrpTfee.getData(dbpool, prpTmainSchema.getProposalNo());
				BLPrpTplan blPrpTplan = new BLPrpTplan();
				blPrpTplan = blProposalBatch.generateTplan(prpTmainSchema, blPrpTfee, blPrpTplan);
				blPrpTplan.cancel(dbpool, prpTmainSchema.getProposalNo());
				blPrpTplan.save(dbpool);
			}
		}
	}

	/*
	 * 根据调整比例更新交费计划次数事物函数 @Author : X @param iArrDate 交费截止日期 @parm
	 * iArrPercent 交费比例 @param blTmotorcade 车队公共信息 @throws UserException @throws
	 * Exception
	 */
	public void updatePlanTrans ( String[] iArrDate, int[] iArrPercent, BLTMotorcade iBLTMotorcade ) throws UserException, Exception
	{
		DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			this.updatePlan( dbpool, iArrDate, iArrPercent, iBLTMotorcade );
			dbpool.commitTransaction();
			dbpool.close();
		}
		catch ( Exception exception )
		{
			dbpool.rollbackTransaction();
			dbpool.close();
			throw exception;
		}
    finally {
      dbpool.close();
		}
	}

	/*根据调整比例更新交费计划次数函数
	 *@Author     : X
	 *@param  iArrDate      交费截止日期
	 *@parm   iArrPercent   交费比例
	 *@param  blTmotorcade  车队公共信息
	 *@throws UserException
	 *@throws Exception
	 */
	public void updatePlan ( DbPool dbpool, String[] iArrDate, int[] iArrPercent, BLTMotorcade iBLTMotorcade ) throws UserException, Exception
	{
		int i = 0;
		int j = 0;
		double dblPlanFee = 0;
		double dblSumPlanFee = 0;
		BLPrpTmain blPrpTmain = new BLPrpTmain();
		DecimalFormat idecimalFormat = new DecimalFormat( "0.00" );
		blPrpTmain.query( dbpool, "ContractNo='" + iBLTMotorcade.getArr( 0 ).getContractNo() + "'", 0 );
		for ( i = 0; i < blPrpTmain.getSize(); i++ )
		{
			dblSumPlanFee = 0;
			PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
			prpTmainSchema = blPrpTmain.getArr( i );
			if ( !prpTmainSchema.getUnderWriteFlag().equals( "1" ) &&
					 !prpTmainSchema.getUnderWriteFlag().equals( "3" ) &&
					 !prpTmainSchema.getUnderWriteFlag().equals( "9" ) )
			{
				BLPrpTplan blPrpTplan = new BLPrpTplan();
				blPrpTplan.getData( dbpool, prpTmainSchema.getProposalNo() );
				for ( j = 0; j < blPrpTplan.getSize(); j++ )
				{
					PrpTplanSchema prpTplanSchema = new PrpTplanSchema();
					prpTplanSchema = blPrpTplan.getArr( j );
					if ( j != blPrpTplan.getSize() - 1 )
					{
						dblPlanFee = Double.parseDouble( idecimalFormat.format( Double.parseDouble( prpTmainSchema.getSumPremium() ) * iArrPercent[j] / 100 ) );
						prpTplanSchema.setPlanFee( String.valueOf( dblPlanFee ) );
						prpTplanSchema.setDelinquentFee( String.valueOf( dblPlanFee ) );
						dblSumPlanFee = dblSumPlanFee + dblPlanFee;
					}
					else
					{
						
						prpTplanSchema.setPlanFee( String.valueOf( Double.parseDouble( prpTmainSchema.getSumPremium() ) - dblSumPlanFee ) );
						prpTplanSchema.setDelinquentFee( prpTplanSchema.getPlanFee() );
					}
					prpTplanSchema.setPlanDate( iArrDate[j] );
					DBPrpTplan dbPrpTplan = new DBPrpTplan();
					dbPrpTplan.setSchema( prpTplanSchema );
					dbPrpTplan.update( dbpool );
				}
			}
		}
	}
	
	/*
	 */
	public void updatePlanTrans(String[] iArrDate, int[] iArrPercent, BLTMotorcade iBLTMotorcade, String strRiskCode)
			throws UserException, Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			this.updatePlan(dbpool, iArrDate, iArrPercent, iBLTMotorcade);
			dbpool.commitTransaction();
			dbpool.close();
		} catch (Exception exception) {
			dbpool.rollbackTransaction();
			dbpool.close();
			throw exception;
		} finally {
			dbpool.close();
		}
	}

	/*
	 */
	public void updatePlan(DbPool dbpool, String[] iArrDate, int[] iArrPercent, BLTMotorcade iBLTMotorcade, String strRiskCode)
			throws UserException, Exception {
		int i = 0;
		int j = 0;
		double dblPlanFee = 0;
		double dblSumPlanFee = 0;
		BLPrpTmain blPrpTmain = new BLPrpTmain();
		DecimalFormat idecimalFormat = new DecimalFormat("0.00");
		blPrpTmain.query(dbpool, " RiskCode='" + strRiskCode + "' and ContractNo='" + iBLTMotorcade.getArr(0).getContractNo()
				+ "'", 0);
		for (i = 0; i < blPrpTmain.getSize(); i++) {
			dblSumPlanFee = 0;
			PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
			prpTmainSchema = blPrpTmain.getArr(i);
			if (!prpTmainSchema.getUnderWriteFlag().equals("1") && !prpTmainSchema.getUnderWriteFlag().equals("3")
					&& !prpTmainSchema.getUnderWriteFlag().equals("9")) {
				BLPrpTplan blPrpTplan = new BLPrpTplan();
				blPrpTplan.getData(dbpool, prpTmainSchema.getProposalNo());
				for (j = 0; j < blPrpTplan.getSize(); j++) {
					PrpTplanSchema prpTplanSchema = new PrpTplanSchema();
					prpTplanSchema = blPrpTplan.getArr(j);
					if (j != blPrpTplan.getSize() - 1) {
						dblPlanFee = Double.parseDouble(idecimalFormat.format(Double.parseDouble(prpTmainSchema.getSumPremium())
								* iArrPercent[j] / 100));
						prpTplanSchema.setPlanFee(String.valueOf(dblPlanFee));
						prpTplanSchema.setDelinquentFee(String.valueOf(dblPlanFee));
						dblSumPlanFee = dblSumPlanFee + dblPlanFee;
					} else {
						
						prpTplanSchema.setPlanFee(String.valueOf(Double.parseDouble(prpTmainSchema.getSumPremium())
								- dblSumPlanFee));
						prpTplanSchema.setDelinquentFee(prpTplanSchema.getPlanFee());
					}
					prpTplanSchema.setPlanDate(iArrDate[j]);
					DBPrpTplan dbPrpTplan = new DBPrpTplan();
					dbPrpTplan.setSchema(prpTplanSchema);
					dbPrpTplan.update(dbpool);
				}
			}
		}
	}

	/*
	 * 车队续XXXXX之前的检测函数 @Author : X @param 合同号 @throws UserException @throws
	 * Exception
	 */
	public int batchUnderChk ( String strContractNo ) throws UserException, Exception
	{
		int ierrmsg = 0;
		int i = 0;
		String strWherePart = "";
		strWherePart = "ContractNo='" + strContractNo + "' AND SUBSTR(othflag,2,1)!='1'" +
			" AND SUBSTR(othflag,3,1)!='1'" +
			" AND SUBSTR(othflag,4,1)!='1'";
		BLPrpTmain blPrpTmain = new BLPrpTmain();
		blPrpTmain.query( strWherePart, 0 );
		for ( i = 0; i < blPrpTmain.getSize(); i++ )
		{
			PrpTmainSchema prpTmainSchema = new PrpTmainSchema();
			prpTmainSchema = blPrpTmain.getArr( i );
			if ( prpTmainSchema.getUnderWriteEndDate().length() == 0 ||
					 prpTmainSchema.getUnderWriteCode().length() == 0 ||
					 prpTmainSchema.getUnderWriteName().length() == 0 )
			{
				ierrmsg = 1;
				break;
			}
		}
		if ( ierrmsg == 1 )
		{
			return ierrmsg;
		}
		
		for ( i = 0; i < blPrpTmain.getSize(); i++ )
		{
			strWherePart = "ProposalNo = '" + blPrpTmain.getArr( i ).getProposalNo() + "'";
			DBPrpCmain dbPrpCmain = new DBPrpCmain();
			dbPrpCmain.getCount( strWherePart );
			if ( dbPrpCmain.getPolicyNo().length() == 0 )
			{
				ierrmsg = 2;
				break;
			}
		}
		return ierrmsg;
	}

	
	
	
    /**
     *带dbpool的update方法
     *@param 无
     *@return 无
     */
	public void update(DbPool dbpool) throws Exception{
		DBPrpMotorcade dbPrpMotorcade = new DBPrpMotorcade();
		int i = 0;
		for(i = 0; i< schemas.size(); i++){
	    	dbPrpMotorcade.setSchema((PrpMotorcadeSchema)schemas.get(i));
	    	dbPrpMotorcade.update(dbpool);
	    }
	}
	
   /**
    *不带dbpool的更新方法
    *@param 无
    *@return 无
    */
	public void update() throws Exception{
		DbPool dbpool = new DbPool();
		try{
		  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	      dbpool.beginTransaction();
	      update(dbpool);
	      dbpool.commitTransaction();
	    }catch (Exception e){
	      dbpool.rollbackTransaction();
	      throw e;
	    }finally{
	      dbpool.close();
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
	 public void queryWithoutOrder(String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
	 {
	   DbPool dbpool = new DbPool();
	   try {
	     dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	     this.queryWithoutOrder(dbpool,iWherePart,intPageNum,intLineNumPage);
	   }
	   catch (Exception e)
	   {
	     throw e;
	   }
	   finally {
	     dbpool.close();
	   }
	 }
	
	/**
	  *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	  *@param dbpool 连接池
	  *@param iWherePart 查询条件(不包括排序字句)
	  *@param intPageNum 页码
	  *@param intPageCount 每页条数
	  *@throws Exception
	  */
	 public void queryWithoutOrder(DbPool dbpool,String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
	 {
	     if(iWherePart.indexOf("'null'")>-1){
	         throw new Exception("查询条件异常，请联系系统管理员！");
	     }
	   StringBuffer strSqlStatement = new StringBuffer();
	   int intStartNum = 0;
	   int intEndNum = 0;

	   intStartNum = (intPageNum - 1) * intLineNumPage;
	   intEndNum = intPageNum * intLineNumPage;

	   DBPrpMotorcade dbPrpMotorcade = new DBPrpMotorcade();
	   initArr();

	   strSqlStatement.append(" SELECT * FROM ( ");
	   strSqlStatement.append("Select RowNum As LineNum,T.* From ( ");
	   strSqlStatement.append("Select * From PrpMotorcade Where ");
	   strSqlStatement.append(iWherePart);
	   strSqlStatement.append(") T Where RowNum<=");
	   strSqlStatement.append(intEndNum);
	   strSqlStatement.append(") Where LineNum>");
	   strSqlStatement.append(intStartNum);
	   schemas = dbPrpMotorcade.findByConditions(dbpool,strSqlStatement.toString());
	 }
	
	/**
	 * 主函数
	 * @param args 参数列表
	 */
	public static void main ( String[] args )
	{
		
	}
}
