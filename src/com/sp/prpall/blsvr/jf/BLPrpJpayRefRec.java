 package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.payment.blsvr.jf.BLPrpJInvoicePrintTem;
import com.sp.payment.blsvr.jf.BLPrpJPayBank;
import com.sp.payment.blsvr.jf.BLPrpJinvPrintDetail;
import com.sp.payment.blsvr.jf.BLPrpJinvPrintMain;
import com.sp.prpall.blsvr.jf.BLPrpJpayRefMain;

import com.sp.payment.blsvr.jf.BLPrpJpayRefRecTrace;
import com.sp.payment.schema.PrpJInvoicePrintTemSchema;
import com.sp.payment.schema.PrpJinvPrintDetailSchema;
import com.sp.payment.schema.PrpJinvPrintMainSchema;
import com.sp.payment.schema.PrpJpayRefRecTraceSchema;
import com.sp.payment.dbsvr.jf.DBPrpJinvPrintMain;
import com.sp.payment.dbsvr.jf.DBPrpJplanAirFly;
import com.sp.payment.dbsvr.jf.DBPrpJpayWVisaTrace;
import com.sp.payment.dbsvr.jf.DBPrpJplanFeePreTrace;
import com.sp.payment.schema.PrpJpayWVisaTraceSchema;
import com.sp.platform.dto.domain.PrpDriskConfigDto;
import com.sp.platform.ui.model.PrpDriskConfigFindByConditionsCommand;
import com.sp.prpall.dbsvr.jf.DBPrpJQuotaInvoice;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefMain;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefRec;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefVoucher;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFee;
import com.sp.prpall.dbsvr.jf.DBPrpJplanPrint;
import com.sp.prpall.dbsvr.misc.DBPrpCommission;
import com.sp.prpall.interf.Visa;
import com.sp.prpall.pubfun.Bill;
import com.sp.prpall.pubfun.PaymentTransCode;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpJQuotaInvoiceSchema;
import com.sp.prpall.schema.PrpJpayRefMainSchema;
import com.sp.prpall.schema.PrpJpayRefRecSchema;
import com.sp.prpall.schema.PrpJplanFeeSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDagent;
import com.sp.utiall.blsvr.BLPrpDuser;
import com.sp.utiall.dbsvr.DBPrpDagent;
import com.sp.utiall.dbsvr.DBPrpDcode;
import com.sp.utiall.dbsvr.DBPrpDcompany;
import com.sp.utiall.dbsvr.DBPrpDrisk;
import com.sp.utiall.dbsvr.DBPrpDuser;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.Str;
import com.sp.visa.dto.domain.VsCodeDto;
import com.sp.visa.dto.domain.VsMarkDto;
import com.sp.visa.resource.dtofactory.domain.DBVsCode;
import com.sp.visa.resource.dtofactory.domain.DBVsMark;
import com.sp.visa.utility.pub.SysConstConfig;
import com.sp.visa.utility.ui.control.action.UIFormatAction;


import com.sp.prpall.schema.PrpJInvoicePreSchema;
import com.sp.prpall.dbsvr.jf.DBPrpJInvoicePre;
/**
 * 定义PrpJpayRefRec的BL类
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>@createdate 2005-05-27</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJpayRefRec{
    private Vector schemas = new Vector();
    protected final Log logger = LogFactory.getLog(getClass());
    boolean checkFlag = false;

    /**
     * 构造函数
     */
    public BLPrpJpayRefRec(){
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
    public void setArr(PrpJpayRefRecSchema iPrpJpayRefRecSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpJpayRefRecSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }

    /**
     *得到一条PrpJpayRefRecSchema记录
     *@param index 下标
     *@return 一个PrpJpayRefRecSchema对象
     *@throws Exception
     */
    public PrpJpayRefRecSchema getArr(int index) throws Exception
    {
      PrpJpayRefRecSchema prpJpayRefRecSchema = null;
      try
      {
        prpJpayRefRecSchema = (PrpJpayRefRecSchema)this.schemas.get(index);
      }
      catch(Exception e)
      {
        throw e;
      }
      return prpJpayRefRecSchema;
    }

    /**
     *删除一条PrpJpayRefRecSchema记录
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
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      if (iLimitCount > 0 && dbPrpJpayRefRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRefRec WHERE " + iWherePart;
        schemas = dbPrpJpayRefRec.findByConditions(strSqlStatement);
      }
    }

    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
	public void queryTranslateCode(String iWherePart,int iLimitCount) throws UserException, Exception {
		DbPool dbpool = new DbPool();
		try {
		    
		    dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		    
			this.queryTranslateCode(dbpool, iWherePart,iLimitCount);
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
	}
    
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryTranslateCode(DbPool dbpool,String iWherePart,int iLimitCount) throws UserException,Exception
    {
      this.query(dbpool,iWherePart, iLimitCount);
      this.translateCode(dbpool,true);
    }
    /**
     *实收XX查询 LingHaiYang 20051223
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryRec(String iWherePart,int iLimitCount) throws Exception
    {
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      if (iLimitCount > 0 && dbPrpJpayRefRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefRec.queryRec");
      }else
      {
        initArr();
        String strSqlStatement = "select ComCode,Handler1Code,AgentCode,"
                               + " RiskCode,Currency2,"
                               + " sum(PayRefFee) as PayRefFee "
                               + " from PrpJpayRefRec "
                               + " where "
                               + iWherePart
                               + " group by ComCode,Handler1Code,AgentCode,RiskCode,Currency2";
        strSqlStatement += " order by ComCode,RiskCode ";

      DbPool dbpool = new DbPool();
        try {
        	
      	    dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
      	    
        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema.setComCode(resultSet.getString("ComCode"));
          prpJpayRefRecSchema.setHandler1Code(resultSet.getString("Handler1Code"));
          prpJpayRefRecSchema.setAgentCode(resultSet.getString("AgentCode"));
          prpJpayRefRecSchema.setRiskCode(resultSet.getString("RiskCode"));
          prpJpayRefRecSchema.setCurrency2(""+resultSet.getString("Currency2"));
          prpJpayRefRecSchema.setPayRefFee(""+resultSet.getDouble("PayRefFee"));
          this.setArr(prpJpayRefRecSchema);
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(NamingException namingException)
      {
        dbpool.close();
        throw namingException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
     }
    }

    /**
     *综合查询分页查询
     *@param String condition
     *@throws Exception
     *@author LingHaiYang
     */
    public void checkInvioceQuery(String strCondition,int intPageNum,int intPageCount) throws Exception
    {
      int intStartNum = 0;
      int intEndNum = 0;

      intStartNum = (intPageNum - 1) * intPageCount;
      intEndNum = intPageNum * intPageCount;

      
      String strSqlStatement = " SELECT * FROM ( " +
             "Select RowNum As LineNum,T.* From ( " +
             "SELECT distinct PrpJpayRefRec.CertiNo,PrpJpayRefRec.PolicyNo,PrpJpayRefRec.InsuredName, " +
             "PrpJpayRefRec.PlanDate,PrpJpayRefRec.PayNo,PrpJpayRefRec.CertiType, " +
			 "PrpJpayRefRec.SerialNo,PrpJpayRefRec.PayRefReason,PrpJpayRefRec.PayRefTimes," +           
			 "PrpJpayRefRec.VisaSerialNo,"+
			 
			 "PrpJpayRefRec.VisaCode,"+
			 
			 "PrpJpayRefRec.PayRefNo,PrpJpayRefRec.PayRefDate, " +
			 "PrpJpayRefRec.Currency2,PrpJpayRefRec.PayRefFee, " +
			 "PrpJpayRefRec.CenterCode,PrpJpayRefRec.VoucherNo " +
			 " FROM PrpJpayRefRec WHERE " + strCondition +
             " ORDER BY CertiNo,PolicyNo,SerialNo) T " +
             "Where RowNum<=" + intEndNum + ") Where LineNum>" + intStartNum;
      DbPool dbpool = new DbPool();
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema.setCertiNo(resultSet.getString("CertiNo"));
          prpJpayRefRecSchema.setPolicyNo(resultSet.getString("PolicyNo"));
          prpJpayRefRecSchema.setInsuredName(resultSet.getString("InsuredName"));
          prpJpayRefRecSchema.setPlanDate("" + resultSet.getDate("PlanDate"));
          prpJpayRefRecSchema.setPayNo(resultSet.getString("PayNo"));
          prpJpayRefRecSchema.setVisaSerialNo(resultSet.getString("VisaSerialNo"));
          prpJpayRefRecSchema.setPayRefNo(resultSet.getString("PayRefNo"));
          prpJpayRefRecSchema.setPayRefDate("" + resultSet.getDate("PayRefDate"));
          prpJpayRefRecSchema.setCurrency2(resultSet.getString("Currency2"));
          prpJpayRefRecSchema.setPayRefFee(resultSet.getString("PayRefFee"));
          prpJpayRefRecSchema.setCertiType(resultSet.getString("CertiType"));
          
          prpJpayRefRecSchema.setVisaCode(resultSet.getString("VisaCode"));
          
          prpJpayRefRecSchema.setSerialNo(resultSet.getString("SerialNo"));
          prpJpayRefRecSchema.setPayRefReason(resultSet.getString("PayRefReason"));
          prpJpayRefRecSchema.setPayRefTimes(resultSet.getString("PayRefTimes"));
          prpJpayRefRecSchema.setCenterCode(resultSet.getString("CenterCode")); 
          prpJpayRefRecSchema.setVoucherNo(resultSet.getString("VoucherNo")); 
          this.setArr(prpJpayRefRecSchema);
          
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(NamingException namingException)
      {
        dbpool.close();
        throw namingException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     *实收实付分页查询
     *@param String condition
     *@throws Exception
     *@author LingHaiYang
     */
    public void RealInvoiceQuery(String strCondition,int intPageNum,int intPageCount) throws Exception
    {
      int intStartNum = 0;
      int intEndNum = 0;

      intStartNum = (intPageNum - 1) * intPageCount;
      intEndNum = intPageNum * intPageCount;

      String strSqlStatement = " SELECT * FROM ( " +
             "Select RowNum As LineNum,T.* From ( " +
             "select ComCode,Handler1Code,AgentCode,"
                               + " RiskCode,Currency2,"
                               + " sum(PayRefFee) as PayRefFee "
                               + " from PrpJpayRefRec "
                               + " where "
                               + strCondition
                               + " group by ComCode,Handler1Code,AgentCode,RiskCode,Currency2) T " +
             "Where RowNum<=" + intEndNum + ") Where LineNum>" + intStartNum;
      DbPool dbpool = new DbPool();
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema.setComCode(resultSet.getString("ComCode"));
          prpJpayRefRecSchema.setHandler1Code(resultSet.getString("Handler1Code"));
          prpJpayRefRecSchema.setAgentCode(resultSet.getString("AgentCode"));
          
          prpJpayRefRecSchema.setRiskCode(resultSet.getString("RiskCode"));
          prpJpayRefRecSchema.setCurrency2(resultSet.getString("Currency2"));
          prpJpayRefRecSchema.setPayRefFee(resultSet.getString("PayRefFee"));
          this.setArr(prpJpayRefRecSchema);
          
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(NamingException namingException)
      {
        dbpool.close();
        throw namingException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     * 实收实付查询的清单列表 SangMingqian 20060301
     * @param iWherePart 查询条件
     * @throws Exception
     */
    public void RealInvoiceList(String iWherePart) throws Exception
    {
        String strSqlStatement = "";
        strSqlStatement = "SELECT distinct PrpJpayRefRec.CertiType,PrpJpayRefRec.CertiNo,"
                          + " PrpJpayRefRec.SerialNo,PrpJpayRefRec.PayRefReason,"
                          + " PrpJpayRefRec.PolicyNo,PrpJpayRefRec.RiskCode,"
                          + " PrpJpayRefRec.StartDate,PrpJpayRefRec.PayRefDate,"
                          + " PrpJpayRefRec.InsuredName,PrpJpayRefRec.Currency2,"
                          + " PrpJpayRefRec.PayRefFee,PrpJpayRefMain.CenterCode, "
                          + " PrpJpayRefMain.VoucherNo,PrpJpayRefRec.PayNo "
						  + " FROM PrpJpayRefRec,PrpJpayRefMain "
                          + " WHERE PrpJpayRefRec.PayRefNo=PrpJpayRefMain.PayRefNo(+) AND "
                          + iWherePart;

      DbPool dbpool = new DbPool();
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema.setCertiType(resultSet.getString("CertiType"));
          prpJpayRefRecSchema.setCertiNo(resultSet.getString("CertiNo"));
          prpJpayRefRecSchema.setSerialNo(resultSet.getString("SerialNo"));
          prpJpayRefRecSchema.setPayRefReason(resultSet.getString("PayRefReason"));
          prpJpayRefRecSchema.setPolicyNo(resultSet.getString("PolicyNo"));
          prpJpayRefRecSchema.setPayNo(resultSet.getString("PayNo"));
          prpJpayRefRecSchema.setRiskCode(resultSet.getString("RiskCode"));
          prpJpayRefRecSchema.setStartDate(""+resultSet.getDate("StartDate"));
          prpJpayRefRecSchema.setPayRefDate(""+resultSet.getDate("PayRefDate"));
          prpJpayRefRecSchema.setInsuredName(resultSet.getString("InsuredName"));
          prpJpayRefRecSchema.setCurrency2(resultSet.getString("Currency2"));
          prpJpayRefRecSchema.setPayRefFee(""+resultSet.getDouble("PayRefFee"));
          prpJpayRefRecSchema.setIdentifyNumber(resultSet.getString("CenterCode")); 
          prpJpayRefRecSchema.setRemark(resultSet.getString("VoucherNo")); 
          this.setArr(prpJpayRefRecSchema);
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(NamingException namingException)
      {
        dbpool.close();
        throw namingException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }


    /**
     *收付台帐业务清单查询 LingHaiYang 20051223
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryWithMainNew(String iWherePart,int iLimitCount) throws Exception
    {
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      if (iLimitCount > 0 && dbPrpJpayRefMain.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"dbPrpJpayRefMain.queryWithRec2");
      }else
      {
        initArr();
        String strSQL1 = "select PrpJpayRefRec.CertiNo,PrpJpayRefRec.PolicyNo,PrpJpayRefRec.VisaSerialNo,"
                               + " PrpJpayRefRec.InsuredName,PrpJpayRefRec.Currency2,PrpJpayRefRec.PayRefDate,"
                               + " PrpJpayRefRec.PayRefFee,PrpJpayRefRec.Handler1Code,PrpJpayRefRec.AgentCode,PrpJpayRefRec.CoinsName,PrpJpayRefRec.CoinsFlag "
                               + " from PrpJpayRefMain,PrpJpayRefRec,PrpJpayRefDetail "
                               + " WHERE PrpJpayRefRec.PayRefNo=PrpJpayRefMain.PayRefNo AND PrpJpayRefMain.PayRefNo=PrpJpayRefDetail.PayRefNo(+) "
                               + " AND PrpJpayRefMain.SerialNo='1' AND "
                               + " PayRefNoType IN ('1','2') AND "
                               + iWherePart;
        String strSQL2 = "select PrpJpayRefRec.CertiNo,PrpJpayRefRec.PolicyNo,PrpJpayRefRec.VisaSerialNo,"
                               + " PrpJpayRefRec.InsuredName,PrpJpayRefRec.Currency2,PrpJpayRefRec.PayRefDate,"
                               + " -1*PrpJpayRefRec.PayRefFee,PrpJpayRefRec.Handler1Code,PrpJpayRefRec.AgentCode,PrpJpayRefRec.CoinsName,PrpJpayRefRec.CoinsFlag "
                               + " from PrpJpayRefMain,PrpJpayRefRec,PrpJpayRefDetail "
                               + " WHERE PrpJpayRefRec.PayRefNo=PrpJpayRefMain.PayRefNo AND PrpJpayRefMain.PayRefNo=PrpJpayRefDetail.PayRefNo(+) "
                               + " AND PrpJpayRefMain.SerialNo='1' AND "
                               + " PayRefNoType IN ('3','4') AND "
                               + iWherePart;
        String strSQL =  "select CertiNo,PolicyNo,VisaSerialNo,InsuredName,Currency2,PayRefDate,PayRefFee,Handler1Code,AgentCode,CoinsName,CoinsFlag"
                               + " FROM (SELECT * FROM ("
                               + strSQL1
                               
							   + " ) UNION ( "
                               + strSQL2
                               + " ) )"
                               + " ORDER BY CertiNo,PolicyNo,VisaSerialNo DESC ";

      DbPool dbpool = new DbPool();
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema.setCertiNo(resultSet.getString("CertiNo"));
          prpJpayRefRecSchema.setPolicyNo(resultSet.getString("PolicyNo"));
          prpJpayRefRecSchema.setVisaSerialNo(resultSet.getString("VisaSerialNo"));
          prpJpayRefRecSchema.setInsuredName(resultSet.getString("InsuredName"));
          prpJpayRefRecSchema.setCurrency2(""+resultSet.getString("Currency2"));
          prpJpayRefRecSchema.setPayRefDate(""+resultSet.getDate("PayRefDate"));
          prpJpayRefRecSchema.setPayRefFee(""+resultSet.getDouble("PayRefFee"));
          prpJpayRefRecSchema.setHandler1Code(""+resultSet.getString("Handler1Code"));
          prpJpayRefRecSchema.setAgentCode(""+resultSet.getString("AgentCode"));
          prpJpayRefRecSchema.setCoinsName(""+resultSet.getString("CoinsName"));
          prpJpayRefRecSchema.setCoinsFlag(""+resultSet.getString("CoinsFlag"));
          this.setArr(prpJpayRefRecSchema);
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(NamingException namingException)
      {
        dbpool.close();
        throw namingException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
     }
    }

    /**
     *联合PrpJpayRefMain表查询 SangMingqian 20050703
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryWithMain(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      if (iLimitCount > 0 && dbPrpJpayRefRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefRec.queryWithMain");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT distinct PrpJpayRefRec.* FROM PrpJpayRefMain,PrpJpayRefRec "
            + "WHERE PrpJpayRefMain.PayRefNo=PrpJpayRefRec.PayRefNo AND " + iWherePart;
        schemas = dbPrpJpayRefRec.findByConditions(strSqlStatement);
      }
    }

    /**
     * 联合MAIN表及DETAIL表查询 SangMingqian 20051102
     * @param iWherePart
     * @throws Exception
     */
    public void queryWithMainDetail(String iWherePart) throws Exception
    {
        String strSqlStatement = "";
        strSqlStatement = "SELECT PrpJpayRefRec.CertiType,PrpJpayRefRec.CertiNo,"
                          + " PrpJpayRefRec.SerialNo,PrpJpayRefRec.PayRefReason,"
                          + " PrpJpayRefRec.PolicyNo,PrpJpayRefRec.VisaSerialNo,"
                          + " PrpJpayRefRec.InsuredName,PrpJpayRefMain.PayRefCode,"
                          + " PrpJpayRefRec.AppliName,PrpJpayRefRec.Currency2,"
                          + " PrpJpayRefRec.PayRefDate,PrpJpayRefRec.PayRefFee,"
                          + " PrpJpayRefRec.Handler1Code,PrpJpayRefRec.AgentCode,"
                          + " PrpJpayRefRec.CoinsFlag,PrpJpayRefDetail.PayWay,"
                          + " PrpJpayRefMain.CenterCode,PrpJpayRefDetail.BankCode,"
                          + " PrpJpayRefDetail.AccountNo,PrpJpayRefDetail.CheckNo "
                          + " FROM PrpJpayRefRec,PrpJpayRefMain,PrpJpayRefDetail "
                          + " WHERE PrpJpayRefRec.PayRefNo=PrpJpayRefMain.PayRefNo "
                          + " AND PrpJpayRefMain.PayRefNo=PrpJpayRefDetail.PayRefNo "
                          + " AND PrpJpayRefMain.SerialNo='1' AND "
                          + iWherePart
                          + " Order By PrpJpayRefRec.CertiNo,PrpJpayRefRec.PolicyNo,"
                          + " PrpJpayRefRec.SerialNo,PrpJpayRefDetail.PayWay,"
                          + " PrpJpayRefDetail.AccountNo,PrpJpayRefDetail.CheckNo DESC";

      DbPool dbpool = new DbPool();
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema.setCertiType(resultSet.getString("CertiType"));
          prpJpayRefRecSchema.setCertiNo(resultSet.getString("CertiNo"));
          prpJpayRefRecSchema.setSerialNo(resultSet.getString("SerialNo"));
          prpJpayRefRecSchema.setPayRefReason(resultSet.getString("PayRefReason"));
          prpJpayRefRecSchema.setPolicyNo(resultSet.getString("PolicyNo"));
          prpJpayRefRecSchema.setVisaSerialNo(resultSet.getString("VisaSerialNo"));
          prpJpayRefRecSchema.setInsuredName(resultSet.getString("InsuredName"));
          prpJpayRefRecSchema.setInsuredCode(resultSet.getString("PayRefCode"));
          prpJpayRefRecSchema.setAppliName(resultSet.getString("AppliName"));
          prpJpayRefRecSchema.setCurrency2(resultSet.getString("Currency2"));
          prpJpayRefRecSchema.setPayRefDate(""+resultSet.getDate("PayRefDate"));
          prpJpayRefRecSchema.setPayRefFee(""+resultSet.getDouble("PayRefFee"));
          prpJpayRefRecSchema.setHandler1Code(resultSet.getString("Handler1Code"));
          prpJpayRefRecSchema.setAgentCode(resultSet.getString("AgentCode"));
          prpJpayRefRecSchema.setCoinsFlag(resultSet.getString("CoinsFlag"));
          prpJpayRefRecSchema.setIdentifyType(resultSet.getString("PayWay")); 
          
          prpJpayRefRecSchema.setPrinterCode(resultSet.getString("CenterCode"));
          prpJpayRefRecSchema.setVisaHandler(resultSet.getString("BankCode"));
          prpJpayRefRecSchema.setIdentifyNumber(resultSet.getString("AccountNo")); 
          prpJpayRefRecSchema.setRemark(resultSet.getString("CheckNo")); 
          this.setArr(prpJpayRefRecSchema);
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(NamingException namingException)
      {
        dbpool.close();
        throw namingException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
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
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      if (iLimitCount > 0 && dbPrpJpayRefRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRefRec WHERE " + iWherePart;
        schemas = dbPrpJpayRefRec.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpJpayRefRec.setSchema((PrpJpayRefRecSchema)schemas.get(i));
        dbPrpJpayRefRec.insert(dbpool);
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
     * 发票预借申请方法，不带dbpool
     * @param String[] arrCertiType
     * @param String[] arrCertiNo
     * @param String[] arrSerialNo
     * @param String[] arrPayRefReason
     * @param PrpJpayRefRecSchema prpJpayRefRecSchema
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     */
    public void genInvoicePre(String[] arrCertiType, String[] arrCertiNo, String[] arrSerialNo, String[] arrPayRefReason,String ApplyNo,String strPlanRevertDate,String strkoaurl, ArrayList iSchemas)
    throws UserException,SQLException, Exception {
        DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            dbpool.beginTransaction();
            genInvoicePre(dbpool, arrCertiType, arrCertiNo, arrSerialNo, arrPayRefReason,ApplyNo,strPlanRevertDate,strkoaurl, iSchemas);
            dbpool.commitTransaction();
        } catch(Exception exception) {
            dbpool.rollbackTransaction();
            throw exception;
        }
        finally {
            dbpool.close();
        }
    }
    /**
     * @author 发票预借申请方法，带dbpool
     * @param DBPool dbpool
     * @param String[] arrCertiType
     * @param String[] arrCertiNo
     * @param String[] arrSerialNo
     * @param String[] arrPayRefReason
     * @param PrpJpayRefRecSchema prpJpayRefRecSchema
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     */
    public void genInvoicePre(DbPool dbpool,String[] arrCertiType, String[] arrCertiNo, String[] arrSerialNo, String[] arrPayRefReason,String ApplyNo,String strPlanRevertDate,String strkoaurl, ArrayList iSchemas)
    throws UserException,SQLException, Exception {
        BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
        DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
        String iSerialNo = ""; 
        String strCondition = "";        
        DBPrpJplanPrint dbPrpJplanPrint = null;
        DBPrpJplanAirFly dbPrpJplanAirFly = null;
        double dblPayRefFee = 0;
        double payRefFee = 0;
        double dbCurrency3Fee =0;
        String wherePartRed = null;
        checkFlag = false;
        String strPoatype = "";
        String strCertiType = "";
        String strCertiNo = "";
        String strSerialNo = "";
        String strPayRefReason = "";
        String strPayRefFee_Invoice_Switch = "";
        String strCenterCode = "00000000";
        
     	DateTime dateTime = new DateTime(new Date(),DateTime.YEAR_TO_DAY);
     	String currentDate = dateTime.toString();
        BLPrpJPayBank blPrpJPayBank = new BLPrpJPayBank();
        BLPrpJplanFee blPrpJplanFeeNew = new BLPrpJplanFee();
        DBPrpJplanFee dbPrpJplanFeeNew = new DBPrpJplanFee();
        ArrayList newSchema = new ArrayList();

        DBPrpJInvoicePre dbPrpJInvoicePre= new DBPrpJInvoicePre();
        PrpJInvoicePreSchema prpJInvoicePreSchema= new PrpJInvoicePreSchema();
        BLPrpJInvoicePre blprpJInvoicePre= new BLPrpJInvoicePre();
        
        BLPrpJInvoicePre blPrpJInvoicePreNum = new BLPrpJInvoicePre();
        
        try {
            for (int i = 0; i < arrCertiNo.length; i++) {
                strCondition = "CertiType='"+arrCertiType[i]+"'"
                             + " AND CertiNo='"+arrCertiNo[i]+"'"
                             + " AND SerialNo='"+arrSerialNo[i]+"'"
                             + " AND PayRefReason='"+arrPayRefReason[i]+"'";
                dbPrpJplanFeeNew.getInfo(dbpool, arrCertiType[i], arrCertiNo[i], arrSerialNo[i], arrPayRefReason[i]);
                
                if(!"0".equals(dbPrpJplanFeeNew.getRealPayRefFee())){
                	throw new UserException( -96, -1167, "UIInvoicePrintIniPre.jsp", "XX已实收，无需预借发票！");
                }
                if(!"".equals(dbPrpJplanFeeNew.getApproveStatus())&&!("2".equals(dbPrpJplanFeeNew.getApproveStatus())||"4".equals(dbPrpJplanFeeNew.getApproveStatus()))){
                	throw new UserException( -96, -1167, "BLPrpJpayRefRec.genInvoicePre", "非驳回或已收回，不允许再次申请预借发票！");
                	
                }
                if(!"0".equals(dbPrpJplanFeeNew.getPoaType())&&dbPrpJplanFeeNew.getPoaType()!=null){
                	throw new UserException( -96, -1167, "BLPrpJpayRefRec.genInvoicePre", dbPrpJplanFeeNew.getPolicyNo()+"此XX是见费出单业务，不能进行预借发票！");
                }
                
            	prpJInvoicePreSchema.setCertiType(dbPrpJplanFeeNew.getCertiType());
            	prpJInvoicePreSchema.setCertiNo(dbPrpJplanFeeNew.getCertiNo());
            	prpJInvoicePreSchema.setSerialNo(dbPrpJplanFeeNew.getSerialNo());
            	prpJInvoicePreSchema.setPayRefReason(dbPrpJplanFeeNew.getPayRefReason());
            	prpJInvoicePreSchema.setRiskCode(dbPrpJplanFeeNew.getRiskCode());
            	prpJInvoicePreSchema.setCurrency1(dbPrpJplanFeeNew.getCurrency1());
            	prpJInvoicePreSchema.setPlanFee(dbPrpJplanFeeNew.getPlanFee());
            	prpJInvoicePreSchema.setComCode(dbPrpJplanFeeNew.getComCode());
            	prpJInvoicePreSchema.setCenterCode(dbPrpJplanFeeNew.getCenterCode());
            	prpJInvoicePreSchema.setHandler1Code(dbPrpJplanFeeNew.getHandler1Code());
            	prpJInvoicePreSchema.setHandlerCode(dbPrpJplanFeeNew.getHandlerCode());
            	prpJInvoicePreSchema.setCoinsFlag(dbPrpJplanFeeNew.getCoinsFlag());         	
            	prpJInvoicePreSchema.setPolicyNo(dbPrpJplanFeeNew.getPolicyNo());
            	prpJInvoicePreSchema.setAppliCode(dbPrpJplanFeeNew.getAppliCode());
            	prpJInvoicePreSchema.setAppliName(dbPrpJplanFeeNew.getAppliName());
            	prpJInvoicePreSchema.setInsuredCode(dbPrpJplanFeeNew.getInsuredCode());
            	prpJInvoicePreSchema.setInsuredName(dbPrpJplanFeeNew.getInsuredName());
            	prpJInvoicePreSchema.setPayNo(dbPrpJplanFeeNew.getPayNo());
            	prpJInvoicePreSchema.setPlanDate(dbPrpJplanFeeNew.getPlanDate());
            	prpJInvoicePreSchema.setAgentCode(dbPrpJplanFeeNew.getAgentCode());
            	prpJInvoicePreSchema.setPoaType(dbPrpJplanFeeNew.getPoaType());
            	prpJInvoicePreSchema.setPoaCode(dbPrpJplanFeeNew.getPoaCode());
            	prpJInvoicePreSchema.setContractNo(dbPrpJplanFeeNew.getContractNo());
            	prpJInvoicePreSchema.setLicenseNo(dbPrpJplanFeeNew.getLicenseNo());
            	prpJInvoicePreSchema.setCoinsType(dbPrpJplanFeeNew.getCoinsType());
            	prpJInvoicePreSchema.setCoinsCode(dbPrpJplanFeeNew.getCoinsCode());
            	prpJInvoicePreSchema.setKOAURL(strkoaurl);
            	
            	blPrpJInvoicePreNum.query(dbpool, strCondition,0, null);
            	
            	if("".equals(dbPrpJplanFeeNew.getApproveStatus())||dbPrpJplanFeeNew.getApproveStatus()==null||
            			dbPrpJplanFeeNew.getApproveStatus().equals("2")||dbPrpJplanFeeNew.getApproveStatus().equals("4")){
            		
            		prpJInvoicePreSchema.setPetitionDate(currentDate);
            		prpJInvoicePreSchema.setApplyNo(ApplyNo);
            		prpJInvoicePreSchema.setPetitioner(dbPrpJplanFeeNew.getHandler1Code());
            		prpJInvoicePreSchema.setPlanRevertDate(""+strPlanRevertDate);
            		prpJInvoicePreSchema.setApproveStatus("0");
            	}
            	dbPrpJInvoicePre.setSchema(prpJInvoicePreSchema);
            	
            	if(("".equals(dbPrpJplanFeeNew.getApproveStatus())||dbPrpJplanFeeNew.getApproveStatus()==null)&&blPrpJInvoicePreNum.getSize()==0){
            		dbPrpJInvoicePre.insert(dbpool);
            	}else if(dbPrpJplanFeeNew.getApproveStatus().equals("2")||dbPrpJplanFeeNew.getApproveStatus().equals("4")||blPrpJInvoicePreNum.getSize()>0){
            		dbPrpJInvoicePre.update(dbpool);
            	}
            	
            	blprpJInvoicePre.setArr(prpJInvoicePreSchema);
            	dbPrpJplanFee = new DBPrpJplanFee();
                dbPrpJplanFee.getInfoForUpdate(dbpool,
                		arrCertiType[i],
                		arrCertiNo[i],
                		arrSerialNo[i],
                		arrPayRefReason[i]);
                dbPrpJplanFee.setApproveStatus("0"); 
                dbPrpJplanFee.update(dbpool);
                
                dbPrpJplanPrint = new DBPrpJplanPrint();
                dbPrpJplanPrint.getInfo(dbpool,
                		arrCertiType[i],
                		arrCertiNo[i],
                		arrSerialNo[i],
                		arrPayRefReason[i]);
                dbPrpJplanPrint.setApproveStatus("0"); 
                dbPrpJplanPrint.update(dbpool);
                
                dbPrpJplanAirFly = new DBPrpJplanAirFly();
                dbPrpJplanAirFly.getInfo(dbpool, arrCertiType[i],
                		arrCertiNo[i],
                		arrSerialNo[i],
                		arrPayRefReason[i]);
                dbPrpJplanAirFly.setApproveStatus("0");
                dbPrpJplanAirFly.update(dbpool);
                
            }










        } catch(UserException userexception) {
            throw userexception;
        } catch(SQLException sqlexception) {
            throw sqlexception;
        } catch(Exception exception) {
            throw exception;
        }
    }
    
    
    /**
     * 合打发票的生成方法，不带dbpool
     * @param String[] arrCertiType
     * @param String[] arrCertiNo
     * @param String[] arrSerialNo
     * @param String[] arrPayRefReason
     * @param PrpJpayRefRecSchema prpJpayRefRecSchema
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     */
    public void genInvoice(String[] arrCertiType, String[] arrCertiNo, String[] arrSerialNo, String[] arrPayRefReason, ArrayList iSchemas)
    throws UserException,SQLException, Exception {
        DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
            dbpool.beginTransaction();
            genInvoice(dbpool, arrCertiType, arrCertiNo, arrSerialNo, arrPayRefReason, iSchemas);
            dbpool.commitTransaction();
        
        } catch(Exception exception) {
            String strSwitch = SysConfig.getProperty("SWITCH_OF_INVOICEPRINTLOG");
            if ("1".equals(strSwitch)) {
                Map invoiceMap = new HashMap();
                PrpJpayRefRecSchema prpJpayRefRecSchema = (PrpJpayRefRecSchema)iSchemas.get(0);
                invoiceMap.put("certino", arrCertiNo[0]);
                invoiceMap.put("visacode", prpJpayRefRecSchema.getVisaCode());
                invoiceMap.put("visaserialno", prpJpayRefRecSchema.getVisaSerialNo());
                invoiceMap.put("printercode", prpJpayRefRecSchema.getOperatorCode());
                invoiceMap.put("flag", "print");
                this.logInvoicePrint(invoiceMap);
            }
        
            dbpool.rollbackTransaction();
            throw exception;
        }
        finally {
            dbpool.close();
        }
    }


    /**
     * @author lijibin 20050611 合打发票的生成方法，带dbpool
     * @param DBPool dbpool
     * @param String[] arrCertiType
     * @param String[] arrCertiNo
     * @param String[] arrSerialNo
     * @param String[] arrPayRefReason
     * @param PrpJpayRefRecSchema prpJpayRefRecSchema
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     */
    public void genInvoice(DbPool dbpool,String[] arrCertiType, String[] arrCertiNo, String[] arrSerialNo, String[] arrPayRefReason, ArrayList iSchemas)
    throws UserException,SQLException, Exception {
        BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
        DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
        
        BLPrpJpayWVisaTrace blPrpJpayWVisaTrace=null;
        
        Visa visa = new Visa();
        PrpJpayRefRecSchema prpJpayRefRecSchema = (PrpJpayRefRecSchema)iSchemas.get(0);
        
        String iSerialNo = ""; 
        if(prpJpayRefRecSchema.getVisaSerialNo().length() < 10){
        	iSerialNo = visa.getSerailNo(prpJpayRefRecSchema.getVisaSerialNo());
        	
        	iSerialNo = UIFormatAction.formatSerialNo(Long.parseLong(iSerialNo));
            
        	prpJpayRefRecSchema.setVisaSerialNo(iSerialNo);
        }
        
        else{ 
          iSerialNo = UIFormatAction.formatSerialNo(Long.parseLong(prpJpayRefRecSchema.getVisaSerialNo()));
          prpJpayRefRecSchema.setVisaSerialNo(iSerialNo);
        }  
        
        
        String strCondition = "";
        
        
        DBPrpJplanPrint dbPrpJplanPrint = null;
        
        
        DBPrpJplanAirFly dbPrpJplanAirFly = null;
        
        
        
        String TPFlag = prpJpayRefRecSchema.getIdentifyNumber();
        
        prpJpayRefRecSchema.setIdentifyNumber("0");
        
        
        double dblPayRefFee = 0;
        double payRefFee = 0;
        double dbCurrency3Fee =0;
        
        
        
        String wherePartRed = null;
        checkFlag = false;
        String strJSRED_SWITCH ="";
        String strPoatype = "";
        String strCertiType = "";
        String strCertiNo = "";
        String strSerialNo = "";
        String strPayRefReason = "";
        
        String strPayRefFee_Invoice_Switch = "";
        String strCenterCode = "00000000";
        try {
            strJSRED_SWITCH = SysConfig.getProperty("JSRED_SWITCH");
            com.sp.payment.utility.PaymentTransCode paymentTransCode = new com.sp.payment.utility.PaymentTransCode();
            if(!(prpJpayRefRecSchema.getCenterCode() == null || "".equals(prpJpayRefRecSchema.getCenterCode()))){
            	strCenterCode = prpJpayRefRecSchema.getCenterCode();
            }
            strPayRefFee_Invoice_Switch = paymentTransCode.getConfigValueByComCode(strCenterCode, "0000", "PayRefFee_Invoice_Switch"); 
        } catch (Exception e) {
            strJSRED_SWITCH = "0";
            e.printStackTrace();
        }
        
        BLPrpJPayBank blPrpJPayBank = new BLPrpJPayBank();
        
        if ("1".equals(blPrpJPayBank.getConfigValueByComCode(prpJpayRefRecSchema.getOperateUnit(), "0000", "JSRED_SWITCHNEW"))) {
        
        
            
            wherePartRed = this.checkRedPayrefNo(dbpool, arrCertiType, arrCertiNo, arrSerialNo, arrPayRefReason);
            if(checkFlag){
                BLPrpJpayRefRec blPrpJpayRefRecTem = new BLPrpJpayRefRec();
                for (int i = 0; i < arrCertiNo.length; i++) {
                    blPrpJpayRefRecTem.checkPoaTypePrint(dbpool,arrCertiNo[i],arrCertiType[i],  arrSerialNo[i], arrPayRefReason[i]);
                    if(blPrpJpayRefRecTem.getSize() == 0){
                        strCertiType = arrCertiType[i];
                        strCertiNo = arrCertiNo[i];
                        strSerialNo = arrSerialNo[i];
                        strPayRefReason = arrPayRefReason[i];
                        break;
                    }
                }
            }
        }
        

        
        try {
                
                DBPrpJpayRefVoucher dBPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
            String strPayRefNo = dBPrpJpayRefVoucher.genRealPayRefNo();
            
            for (int i = 0; i < arrCertiNo.length; i++) {
            	
                
                
                
                
                
                blPrpJplanFee.getNewInfo(arrCertiType[i],arrCertiNo[i],arrSerialNo[i],arrPayRefReason[i]);
                
                this.setArr(this.genSchema(blPrpJplanFee.getArr(0)));
                
                
                if(checkFlag){
                    if(strCertiType.equals(blPrpJplanFee.getArr(0).getCertiType())&& strCertiNo.equals(blPrpJplanFee.getArr(0).getCertiNo())&& 
                            strSerialNo.equals(blPrpJplanFee.getArr(0).getSerialNo())&& strPayRefReason.equals(blPrpJplanFee.getArr(0).getPayRefReason()) ){
                        strPoatype = blPrpJplanFee.getArr(0).getPoaType();
                    }                    
                }
                
            }
            













            
            
            for (int i = 0; i < arrCertiNo.length; i++) {
            	
                
                
                
                
                
                blPrpJplanFee.getNewInfo(arrCertiType[i],arrCertiNo[i],arrSerialNo[i],arrPayRefReason[i]);
                
              
                String switch_PRINT_INVOICES =SysConfig.getProperty("SWITCH_PRINT_INVOICES");
                if ("0".equals(switch_PRINT_INVOICES)){
                	if(Double.parseDouble(blPrpJplanFee.getArr(0).getRealPayRefFee())==0&&("".equals(blPrpJplanFee.getArr(0).getPoaType())||blPrpJplanFee.getArr(0).getPoaType()==null||"0".equals(blPrpJplanFee.getArr(0).getPoaType()))){
                		if(!"1".equals(blPrpJplanFee.getArr(0).getApproveStatus())){
                			throw new UserException( -96, -1167, "BLPrpJplanFee.genInvoice", blPrpJplanFee.getArr(0).getCertiNo()+"XX未实收数据,当" +
                				"为预借发票且审核状态为审核通过时，才允许XX发票打印！");
                		}
                	}
                }else if("1".equals(switch_PRINT_INVOICES)){
                	BLPrpJpayRefRec blPrpJpayRefRecNew03 = new BLPrpJpayRefRec();	
                	if(blPrpJplanFee.getArr(0).getPoaType().equals("0")){
                		String iWherePart = " certitype = '"+blPrpJplanFee.getArr(0).getCertiType()+"' and certino = '"+blPrpJplanFee.getArr(0).getCertiNo()+"' " +
                	    	"and payrefreason = '"+blPrpJplanFee.getArr(0).getPayRefReason()+"' and serialno = '"+blPrpJplanFee.getArr(0).getSerialNo()+"' and payreftimes = 1";
                		blPrpJpayRefRecNew03.query(iWherePart,0);
                	    if(blPrpJpayRefRecNew03.getArr(0).getVoucherNo() != null && blPrpJpayRefRecNew03.getArr(0).getVoucherNo().length() > 0){
                	    	continue;
                	    }else{
                    	    if("1".equals(blPrpJplanFee.getArr(0).getApproveStatus())){
                    	    	continue;
                    	    }else{
                    	        throw new UserException( -96, -1167, "BLPrpJplanFee.genInvoice", blPrpJplanFee.getArr(0).getCertiNo()+"XX未实收数据,当" +
                    	        	"为预借发票且审核状态为审核通过时，才允许XX发票打印！");
                    	    }
                	    }
                	}
                }
              
            }
            if (strPayRefFee_Invoice_Switch.length() >= 8) {
            	
            genInvoiceNew(dbpool, this,prpJpayRefRecSchema, iSchemas, strPoatype,wherePartRed);
            	return;
            }
            
            
            for (int i = 0; i < this.getSize(); i++) {
                this.getArr(i).setOperateDate(prpJpayRefRecSchema.getOperateDate());
                this.getArr(i).setOperatorCode(prpJpayRefRecSchema.getOperatorCode());
                this.getArr(i).setOperateUnit(prpJpayRefRecSchema.getOperateUnit());
                this.getArr(i).setCurrency2(prpJpayRefRecSchema.getCurrency2());
                double dblSumPayRefFee = 0;
                for (int j = 0; j < iSchemas.size(); j++) {
                                        PrpJpayRefRecSchema prpJpayRefRecSchema1 = (PrpJpayRefRecSchema) iSchemas
                                                        .get(j);
                                        dblSumPayRefFee += Double.parseDouble(prpJpayRefRecSchema1.getPayRefFee());
                                        if (this.getArr(i).getCurrency1().equals(prpJpayRefRecSchema1.getCurrency1())) {
                                                this.getArr(i).setExchangeRate(prpJpayRefRecSchema1.getExchangeRate());
                                                dblPayRefFee = Double.parseDouble(this.getArr(i).getPlanFee())
                                                                * Double.parseDouble(prpJpayRefRecSchema1.getExchangeRate());
                                        }
                    					
                    					if ("EUR".equals(prpJpayRefRecSchema1.getCurrency3())) {
                    						this.getArr(i).setExchangeRate3(prpJpayRefRecSchema1.getExchangeRate3());
                    						this.getArr(i).setExchangeRate(prpJpayRefRecSchema1.getExchangeRate());
                    						dbCurrency3Fee = Double.parseDouble(this.getArr(i).getPlanFee())
                    								* Double.parseDouble(prpJpayRefRecSchema1.getExchangeRate3());
                    						dbCurrency3Fee = Str.round(Str.round(dbCurrency3Fee, 8), 2);
                    						dblPayRefFee = dbCurrency3Fee * Double.parseDouble(prpJpayRefRecSchema1.getExchangeRate());
                    					}
                    					
                                }
                if(i==this.getSize()-1){
                        dblPayRefFee = dblSumPayRefFee - payRefFee;
                        
    				if(!"EUR".equals(prpJpayRefRecSchema.getCurrency3())){
                        if(Math.abs(dblPayRefFee - Double.parseDouble(this.getArr(i).getPlanFee())* Double.parseDouble(this.getArr(i).getExchangeRate()))>0.1)
                        {
                            logger.info(this.getArr(i).getCertiNo()+"--发票金额错误--planfee="+this.getArr(i).getPlanFee()+"--dblPayRefFee="+dblPayRefFee);
                            throw new UserException( -96, -1167, "BLPrpJplanFee.genInvoice", this.getArr(i).getCertiNo()+"发票金额不正确，不能进行发票打印！");
                        }
    				}
                }
                dblPayRefFee = Str.round(Str.round(dblPayRefFee, 8), 2);
                
                
                this.getArr(i).setPayRefFee("" + dblPayRefFee);
                payRefFee = payRefFee + dblPayRefFee;
                this.getArr(i).setVisaCode(prpJpayRefRecSchema.getVisaCode());
                this.getArr(i).setVisaName(prpJpayRefRecSchema.getVisaName());
                this.getArr(i).setVisaSerialNo(prpJpayRefRecSchema.getVisaSerialNo());
                
                this.getArr(i).setWVisaCode(prpJpayRefRecSchema.getWVisaCode());
                this.getArr(i).setWVisaSerialNo(prpJpayRefRecSchema.getWVisaSerialNo());
                
                this.getArr(i).setPrintDate(prpJpayRefRecSchema.getPrintDate());
                this.getArr(i).setPrinterCode(prpJpayRefRecSchema.getPrinterCode());
                this.getArr(i).setVisaHandler(prpJpayRefRecSchema.getVisaHandler());
                this.getArr(i).setPayRefName(prpJpayRefRecSchema.getPayRefName());
                this.getArr(i).setIdentifyType(prpJpayRefRecSchema.getIdentifyType());
                this.getArr(i).setIdentifyNumber(prpJpayRefRecSchema.getIdentifyNumber());
                this.getArr(i).setRemark(prpJpayRefRecSchema.getRemark());
                
                this.getArr(i).setCurrency3(prpJpayRefRecSchema.getCurrency3());
                this.getArr(i).setCurrency3Fee("" + dbCurrency3Fee);
                
                
                this.getArr(i).setFlag("0");
                
                
                this.getArr(i).setIdentifyType("0");
                
                
                this.getArr(i).setPayRefNo(strPayRefNo);
                
                
                dbPrpJplanPrint = new DBPrpJplanPrint();
                dbPrpJplanPrint.delete(dbpool, this.getArr(i).getCertiType(), this.getArr(i).getCertiNo(), 
                		this.getArr(i).getSerialNo(), this.getArr(i).getPayRefReason());
                
                
                dbPrpJplanAirFly = new DBPrpJplanAirFly();
                dbPrpJplanAirFly.delete(dbpool, this.getArr(i).getCertiType(), this.getArr(i).getCertiNo(), 
                        this.getArr(i).getSerialNo(), this.getArr(i).getPayRefReason());
                
                
                if(0==dblPayRefFee){
                    
                    
                    this.remove(i);
                    i--;
                    
                }
                
                
                if(checkFlag){
                    this.getArr(i).setPoaType(strPoatype);
                }
                
            }
            if(this.getSize()==0){
                throw new UserException( -96, -1167, "BLPrpJplanFee.genInvoice", "发票金额为0，不能进行发票打印！");
            }
            
            if(prpJpayRefRecSchema.getComCode().length()>2 &&"08".equals(prpJpayRefRecSchema.getComCode().substring(0, 2))){
            	blPrpJpayWVisaTrace = new BLPrpJpayWVisaTrace();
            	blPrpJpayWVisaTrace.traceVisa(this.schemas,"refrec","01" );
            }
            
            
            /*ADD BY PENGJINLING 2012-3-9 payment-435 产XXXXX广东省分公司营业税及附加的纳税基数规则调整 BEGIN*/
            PrpJpayWVisaTraceSchema iPrpJpayWVisaTraceSchema = null;
            if(prpJpayRefRecSchema.getComCode().length()>0 &&"98".equals(prpJpayRefRecSchema.getComCode())){
            	blPrpJpayWVisaTrace = new BLPrpJpayWVisaTrace();
            	PrpJpayRefRecSchema prpJpayRefRecSchema98 = null;
            	for(int i = 0; i < this.getSize(); i++){
            		prpJpayRefRecSchema98 = this.getArr(i);
	                iPrpJpayWVisaTraceSchema = blPrpJpayWVisaTrace.genSchema(prpJpayRefRecSchema98, "98");
	                iPrpJpayWVisaTraceSchema.setWVisaCode(iPrpJpayWVisaTraceSchema.getVisaCode());
	                iPrpJpayWVisaTraceSchema.setWVisaSerialNo(iPrpJpayWVisaTraceSchema.getVisaSerialNo());	                
	                blPrpJpayWVisaTrace.setArr(iPrpJpayWVisaTraceSchema);
            	}
            	blPrpJpayWVisaTrace.save(dbpool);
            }            
            /*ADD BY PENGJINLING 2012-3-9 payment-435 产XXXXX广东省分公司营业税及附加的纳税基数规则调整 END*/
            
            
            















            
            int RefMainFlag=0;
            for (int i = 0; i < this.getSize(); i++) {
                dbPrpJplanFee = new DBPrpJplanFee();
                dbPrpJplanFee.getInfoForUpdate(dbpool,
                                               this.getArr(i).getCertiType(),
                                               this.getArr(i).getCertiNo(),
                                               this.getArr(i).getSerialNo(),
                                               this.getArr(i).getPayRefReason());
                
                
                
                
                
                
                if (Double.parseDouble(Str.chgStrZero(dbPrpJplanFee.getPayRefFee())) != 0) {
                    throw new UserException( -96, -1167, "BLPrpJplanFee.genInvoice", "已经打过发票，不能再打！");
                }
                
                if("".equals(dbPrpJplanFee.getApproveStatus())||dbPrpJplanFee.getApproveStatus()==null){
                	System.out.println(dbPrpJplanFee.getPolicyNo()+"是非预借发票，不生成对应的PrpJpayRefMain表数据!!");
                	RefMainFlag++;
                }
                
                dbPrpJplanFee.setPayRefFee(this.getArr(i).getPlanFee());
                dbPrpJplanFee.setInvPrintFlag("1"); 
                dbPrpJplanFee.update(dbpool);
            }
            
            if(RefMainFlag==0){
	            BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
	            if(this.getSize() != 0)
	            {
	            	PrpJpayRefMainSchema prpJpayRefMainSchema = new PrpJpayRefMainSchema();
	                prpJpayRefMainSchema.setPayRefNo(strPayRefNo);
	                prpJpayRefMainSchema.setSerialNo("1");
	                prpJpayRefMainSchema.setPayRefNoType("2");
	                prpJpayRefMainSchema.setVisaCode(prpJpayRefRecSchema.getVisaCode());
	                prpJpayRefMainSchema.setVisaSerialNo(prpJpayRefRecSchema.getVisaSerialNo());
	                prpJpayRefMainSchema.setPrintDate(prpJpayRefRecSchema.getPrintDate());
	                prpJpayRefMainSchema.setPrinterCode(prpJpayRefRecSchema.getPrinterCode());
	                prpJpayRefMainSchema.setVisaHandler(prpJpayRefRecSchema.getVisaHandler());
	                prpJpayRefMainSchema.setPayRefName(prpJpayRefRecSchema.getPayRefName());
	                prpJpayRefMainSchema.setIdentifyType("0");
	                prpJpayRefMainSchema.setIdentifyNumber("0");
	                prpJpayRefMainSchema.setCurrency2(prpJpayRefRecSchema.getCurrency2());
	                prpJpayRefMainSchema.setCurrency3(prpJpayRefRecSchema.getCurrency3());
	                prpJpayRefMainSchema.setRemark(prpJpayRefRecSchema.getRemark());
	                
	                
	                if (this.getArr(0).getToStatus().equals("0")||this.getArr(0).getToStatus().equals("")) {
						prpJpayRefMainSchema.setCenterCode(this.getArr(0).getCenterCode());
						prpJpayRefMainSchema.setBranchCode("0");
					} else {
						prpJpayRefMainSchema.setCenterCode(this.getArr(0).getToCenterCode());
						prpJpayRefMainSchema.setBranchCode(this.getArr(0).getCenterCode());
					}
	                
	                
	                
	                String strComcode = prpJpayRefRecSchema.getComCode();
	                if(strComcode.length()>2 &&(("03".equals(strComcode.substring(0, 2))) || ("35".equals(strComcode.substring(0, 2))))){
	                	prpJpayRefMainSchema.setCheckCode(prpJpayRefRecSchema.getFlag3());
	                }
	                
	               
	                prpJpayRefMainSchema.setPayRefFee(""+payRefFee);
	                blPrpJpayRefMain.setArr(prpJpayRefMainSchema);
	            }
	            
	            
	            
	            if(checkFlag){
	                this.updateRedDate(dbpool,wherePartRed,strPayRefNo,prpJpayRefRecSchema.getPrintDate(),strPoatype);
	            }
	            
	            
	            blPrpJpayRefMain.save(dbpool);
        	}
            this.save(dbpool);
            
            if (TPFlag == null || TPFlag.equals("") || !TPFlag.equals("1")) {
            	visa.useTrans(dbpool, prpJpayRefRecSchema.getOperatorCode(), prpJpayRefRecSchema.getVisaCode(),
            			prpJpayRefRecSchema.getVisaSerialNo(), this.getArr(0).getCertiNo());
            }
            
            
            String strSwitch = SysConfig.getProperty("SWITCH_OF_INVOICEPRINTLOG");
            if ("1".equals(strSwitch)) {
                Map invoiceMap = new HashMap();
                invoiceMap.put("certino", this.getArr(0).getCertiNo());
                invoiceMap.put("visacode", prpJpayRefRecSchema.getVisaCode());
                invoiceMap.put("visaserialno", prpJpayRefRecSchema.getVisaSerialNo());
                invoiceMap.put("printercode", prpJpayRefRecSchema.getOperatorCode());
                invoiceMap.put("flag", "print");
                this.logInvoicePrint(invoiceMap);
            }
            
        } catch(UserException userexception) {
            throw userexception;
        } catch(SQLException sqlexception) {
            throw sqlexception;
        } catch(Exception exception) {
            throw exception;
        }
    }
    

    /* modify by xiaojian 20051116 begin reason：收付处理传参数方式修改 */
    /**
     * @desc 批量打发票的生成方法
     * @param String[] arrCertiType
     * @param String[] arrCertiNo
     * @param String[] arrSerialNo
     * @param String[] arrPayRefReason
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     */
    public void genBatchInvoice(String[] arrCertiType, String[] arrCertiNo, String[] arrSerialNo, String[] arrPayRefReason)
    throws UserException,SQLException,Exception {
        BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
        BLPrpJplanFee blPrpJplanFeeTmp = new BLPrpJplanFee();
        DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
        PrpJplanFeeSchema prpJplanFeeSchema = new  PrpJplanFeeSchema();

        Visa visa = new Visa();

        String strCondition = "";
        double dblPayRefFee = 0;

        int i = 0;

        for (i = 0; i < arrCertiNo.length; i++) {
            
        	
            
            
            
            
            
        	blPrpJplanFeeTmp.getNewInfo(arrCertiType[i],arrCertiNo[i],arrSerialNo[i],arrPayRefReason[i]);
            
            prpJplanFeeSchema = blPrpJplanFeeTmp.getArr(0);
            blPrpJplanFee.setArr(prpJplanFeeSchema);
        }
        this.genBatchSchema(blPrpJplanFee);
        for (i = 0; i < this.getSize(); i++) {
            dblPayRefFee = Double.parseDouble(this.getArr(i).getPlanFee())
                         * Double.parseDouble(this.getArr(i).getExchangeRate());
            dblPayRefFee = Str.round(Str.round(dblPayRefFee,8),2);
            
            this.getArr(i).setFlag("0");
            this.getArr(i).setIdentifyType("0");
            this.getArr(i).setIdentifyNumber("0");
            
            this.getArr(i).setPayRefFee("" + dblPayRefFee);
        }

        DbPool dbpool = new DbPool();
        
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
            dbpool.beginTransaction();
            for (i = 0; i < arrCertiNo.length; i++) {
                dbPrpJplanFee = new DBPrpJplanFee();
                dbPrpJplanFee.getInfoForUpdate(dbpool,
                                               this.getArr(i).getCertiType(),
                                               this.getArr(i).getCertiNo(),
                                               this.getArr(i).getSerialNo(),
                                               this.getArr(i).getPayRefReason());
            
            if (dbPrpJplanFee.getPayRefReason().equals("P40")) {
                this.isRealPayRef(dbpool,this.getArr(i));
            }
            
            if (Double.parseDouble(Str.chgStrZero(dbPrpJplanFee.getPayRefFee())) != 0) {
                throw new UserException( -96, -1167, "BLPrpJplanFee.genInvoice", "已经打过发票,不能再打！");
            }
            dbPrpJplanFee.setPayRefFee("" + (Double.parseDouble(this.getArr(i).getPlanFee()) + Double.parseDouble(dbPrpJplanFee.getPayRefFee())));
            dbPrpJplanFee.update(dbpool);
            
             
            visa.useTrans(dbpool,
                      this.getArr(i).getPrinterCode(),
                      this.getArr(i).getVisaCode(),
                      this.getArr(i).getVisaSerialNo(),
                      this.getArr(i).getCertiNo());
             
            }
            this.save(dbpool);
            dbpool.commitTransaction();
            dbpool.close();
        } catch(UserException userexception) {
            dbpool.rollbackTransaction();
            dbpool.close();
            throw userexception;
        } catch(SQLException sqlexception) {
            dbpool.rollbackTransaction();
            dbpool.close();
            throw sqlexception;
        } catch(Exception exception) {
            dbpool.rollbackTransaction();
            dbpool.close();
            throw exception;
        }
        finally {
            dbpool.close();
        }
    }
    /* modify by xiaojian 20051116 end */

    /**
     * @desc 生成一条PrpJpayRefRecSchema包含应收应付信息的记录
     * @param PrpJplanFeeSchema prpJplanFeeSchema
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     */
    public PrpJpayRefRecSchema genSchema(PrpJplanFeeSchema prpJplanFeeSchema)
    throws Exception {
        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
        prpJpayRefRecSchema = new PrpJpayRefRecSchema();
        prpJpayRefRecSchema.setCertiType(prpJplanFeeSchema.getCertiType());
        prpJpayRefRecSchema.setCertiNo(prpJplanFeeSchema.getCertiNo());
        prpJpayRefRecSchema.setPolicyNo(prpJplanFeeSchema.getPolicyNo());
        prpJpayRefRecSchema.setSerialNo(prpJplanFeeSchema.getSerialNo());
        prpJpayRefRecSchema.setPayRefReason(prpJplanFeeSchema.getPayRefReason());
        prpJpayRefRecSchema.setPayRefTimes("1");
        prpJpayRefRecSchema.setClaimNo(prpJplanFeeSchema.getClaimNo());
        prpJpayRefRecSchema.setClassCode(prpJplanFeeSchema.getClassCode());
        prpJpayRefRecSchema.setRiskCode(prpJplanFeeSchema.getRiskCode());
        prpJpayRefRecSchema.setAppliCode(prpJplanFeeSchema.getAppliCode());
        prpJpayRefRecSchema.setAppliName(prpJplanFeeSchema.getAppliName());
        prpJpayRefRecSchema.setInsuredCode(prpJplanFeeSchema.getInsuredCode());
        prpJpayRefRecSchema.setInsuredName(prpJplanFeeSchema.getInsuredName());
        prpJpayRefRecSchema.setStartDate(prpJplanFeeSchema.getStartDate());
        prpJpayRefRecSchema.setEndDate(prpJplanFeeSchema.getEndDate());
        prpJpayRefRecSchema.setValidDate(prpJplanFeeSchema.getValidDate());
        prpJpayRefRecSchema.setPayNo(prpJplanFeeSchema.getPayNo());
        prpJpayRefRecSchema.setCurrency1(prpJplanFeeSchema.getCurrency1());
        prpJpayRefRecSchema.setPlanFee(prpJplanFeeSchema.getPlanFee());
        prpJpayRefRecSchema.setPlanDate(prpJplanFeeSchema.getPlanDate());
        prpJpayRefRecSchema.setComCode(prpJplanFeeSchema.getComCode());
        prpJpayRefRecSchema.setMakeCom(prpJplanFeeSchema.getMakeCom());
        prpJpayRefRecSchema.setAgentCode(prpJplanFeeSchema.getAgentCode());
        prpJpayRefRecSchema.setHandler1Code(prpJplanFeeSchema.getHandler1Code());
        prpJpayRefRecSchema.setHandlerCode(prpJplanFeeSchema.getHandlerCode());
        prpJpayRefRecSchema.setUnderWriteDate(prpJplanFeeSchema.getUnderWriteDate());
        prpJpayRefRecSchema.setCoinsFlag(prpJplanFeeSchema.getCoinsFlag());
        prpJpayRefRecSchema.setCoinsCode(prpJplanFeeSchema.getCoinsCode());
        prpJpayRefRecSchema.setCoinsName(prpJplanFeeSchema.getCoinsName());
        prpJpayRefRecSchema.setCoinsType(prpJplanFeeSchema.getCoinsType());
        prpJpayRefRecSchema.setCarNatureCode(prpJplanFeeSchema.getCarNatureCode());
        prpJpayRefRecSchema.setUseNatureCode(prpJplanFeeSchema.getUseNatureCode());
        prpJpayRefRecSchema.setCarProperty(prpJplanFeeSchema.getCarProperty());
        
        prpJpayRefRecSchema.setCenterCode(prpJplanFeeSchema.getCenterCode());
        prpJpayRefRecSchema.setBusinessNature(prpJplanFeeSchema.getBusinessNature());
        
        prpJpayRefRecSchema.setToCenterCode(prpJplanFeeSchema.getToCenterCode());
        prpJpayRefRecSchema.setToComCode(prpJplanFeeSchema.getToComCode());
        prpJpayRefRecSchema.setToDesc(prpJplanFeeSchema.getToDesc());
        prpJpayRefRecSchema.setToStatus(prpJplanFeeSchema.getToStatus());
        prpJpayRefRecSchema.setToUserCode(prpJplanFeeSchema.getToUserCode());
        
        prpJpayRefRecSchema.setCarNatureCode(prpJplanFeeSchema.getCarNatureCode());
        
        prpJpayRefRecSchema.setPoaType(prpJplanFeeSchema.getPoaType());
        
        
        prpJpayRefRecSchema.setChannelType(prpJplanFeeSchema.getChannelType());
        
        
        prpJpayRefRecSchema.setAppliType(prpJplanFeeSchema.getAppliType());
        
        
        
        prpJpayRefRecSchema.setPayBankId(prpJplanFeeSchema.getPayBankId());
        prpJpayRefRecSchema.setPrpCplanSerialno(prpJplanFeeSchema.getPrpCplanSerialno());
        
        
        
        String switch_PRINT_INVOICES =SysConfig.getProperty("SWITCH_PRINT_INVOICES");
        if ("0".equals(switch_PRINT_INVOICES)){
        	if(Double.parseDouble(prpJplanFeeSchema.getRealPayRefFee())!=0){
        		prpJpayRefRecSchema.setIdentifyType("1");
        	}else{
        		prpJpayRefRecSchema.setIdentifyType("0");        	
        	}
        }else if("1".equals(switch_PRINT_INVOICES)){
        	BLPrpJpayRefRec blPrpJpayRefRecNew02 = new BLPrpJpayRefRec();
            String iWherePart = " certitype = '"+prpJplanFeeSchema.getCertiType()+"' and certino = '"+prpJplanFeeSchema.getCertiNo()+"' " +
    		"and payrefreason = '"+prpJplanFeeSchema.getPayRefReason()+"' and serialno = '"+prpJplanFeeSchema.getSerialNo()+"' and payreftimes = 1";
            blPrpJpayRefRecNew02.query(iWherePart,0);
    		if(blPrpJpayRefRecNew02.getArr(0).getVoucherNo() != null && blPrpJpayRefRecNew02.getArr(0).getVoucherNo().length() > 0){
    			prpJpayRefRecSchema.setIdentifyType("1");
    		}else{
    			prpJpayRefRecSchema.setIdentifyType("0");
    		}
        }
      
        prpJpayRefRecSchema.setInPutJPlanFeeDate(prpJplanFeeSchema.getInPutDate());
        prpJpayRefRecSchema.setAutoRefFlag(prpJplanFeeSchema.getAutoRefFlag());
        prpJpayRefRecSchema.setInvPrintConFlag(prpJplanFeeSchema.getInvPrintConFlag());
        prpJpayRefRecSchema.setPayBankId(prpJplanFeeSchema.getPayBankId());
        
        
        return prpJpayRefRecSchema;
    }

    /* modify by xiaojian 20051116 begin reason：收付处理传参数方式修改 */
    /**
     * @desc 批量大印数据写入应收应付信息
     * @param BLPrpJplanFee blPrpJplanFee
     */
    public void genBatchSchema(BLPrpJplanFee blPrpJplanFee)
    throws Exception {
        for(int i = 0;i<blPrpJplanFee.getSize();i++) {
            this.getArr(i).setCertiType(blPrpJplanFee.getArr(i).getCertiType());
            this.getArr(i).setCertiNo(blPrpJplanFee.getArr(i).getCertiNo());
            this.getArr(i).setPolicyNo(blPrpJplanFee.getArr(i).getPolicyNo());
            this.getArr(i).setSerialNo(blPrpJplanFee.getArr(i).getSerialNo());
            this.getArr(i).setPayRefReason(blPrpJplanFee.getArr(i).getPayRefReason());
            this.getArr(i).setPayRefTimes("1");
            this.getArr(i).setClaimNo(blPrpJplanFee.getArr(i).getClaimNo());
            this.getArr(i).setClassCode(blPrpJplanFee.getArr(i).getClassCode());
            this.getArr(i).setRiskCode(blPrpJplanFee.getArr(i).getRiskCode());
            this.getArr(i).setAppliCode(blPrpJplanFee.getArr(i).getAppliCode());
            this.getArr(i).setAppliName(blPrpJplanFee.getArr(i).getAppliName());
            this.getArr(i).setInsuredCode(blPrpJplanFee.getArr(i).getInsuredCode());
            this.getArr(i).setInsuredName(blPrpJplanFee.getArr(i).getInsuredName());
            this.getArr(i).setStartDate(blPrpJplanFee.getArr(i).getStartDate());
            this.getArr(i).setEndDate(blPrpJplanFee.getArr(i).getEndDate());
            this.getArr(i).setValidDate(blPrpJplanFee.getArr(i).getValidDate());
            this.getArr(i).setPayNo(blPrpJplanFee.getArr(i).getPayNo());
            this.getArr(i).setCurrency1(blPrpJplanFee.getArr(i).getCurrency1());
            this.getArr(i).setPlanFee(blPrpJplanFee.getArr(i).getPlanFee());
            this.getArr(i).setPlanDate(blPrpJplanFee.getArr(i).getPlanDate());
            this.getArr(i).setComCode(blPrpJplanFee.getArr(i).getComCode());
            this.getArr(i).setMakeCom(blPrpJplanFee.getArr(i).getMakeCom());
            this.getArr(i).setAgentCode(blPrpJplanFee.getArr(i).getAgentCode());
            this.getArr(i).setHandler1Code(blPrpJplanFee.getArr(i).getHandler1Code());
            this.getArr(i).setHandlerCode(blPrpJplanFee.getArr(i).getHandlerCode());
            this.getArr(i).setUnderWriteDate(blPrpJplanFee.getArr(i).getUnderWriteDate());
            this.getArr(i).setCoinsFlag(blPrpJplanFee.getArr(i).getCoinsFlag());
            this.getArr(i).setCoinsCode(blPrpJplanFee.getArr(i).getCoinsCode());
            this.getArr(i).setCoinsName(blPrpJplanFee.getArr(i).getCoinsName());
            this.getArr(i).setCoinsType(blPrpJplanFee.getArr(i).getCoinsType());
            this.getArr(i).setCarNatureCode(blPrpJplanFee.getArr(i).getCarNatureCode());
            this.getArr(i).setUseNatureCode(blPrpJplanFee.getArr(i).getUseNatureCode());
            this.getArr(i).setCarProperty(blPrpJplanFee.getArr(i).getCarProperty());
            
            this.getArr(i).setPoaType(blPrpJplanFee.getArr(i).getPoaType());
            
        }
    }
    /* modify by xiaojian 20051116 end */

    /**
     *发票作废查询
     *@param String condition
     *@throws Exception
     */
    public void dropInvioceQuery(String strCondition) throws Exception
    {
      String strSQL = "";
      
      strSQL = "SELECT VisaCode,VisaName,visaserialno,Currency2,PrintDate,PrinterCode,VisaHandler,PayRefName,"
          + "sum(PayRefFee) as PayRefFee,PoaType,IdentifyType,PayRefNo FROM PrpJpayRefRec WHERE " + strCondition;
      

      DbPool dbpool = new DbPool();
      this.initArr();
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema.setVisaCode(resultSet.getString("VisaCode"));
          prpJpayRefRecSchema.setVisaName(resultSet.getString("VisaName"));
          prpJpayRefRecSchema.setCurrency2(resultSet.getString("currency2"));
          prpJpayRefRecSchema.setVisaSerialNo(resultSet.getString("visaSerialNo"));
          prpJpayRefRecSchema.setPrintDate(""+resultSet.getDate("PrintDate"));
          prpJpayRefRecSchema.setPrinterCode(resultSet.getString("PrinterCode"));
          prpJpayRefRecSchema.setVisaHandler(resultSet.getString("VisaHandler"));
          prpJpayRefRecSchema.setPayRefName(resultSet.getString("PayRefName"));
          prpJpayRefRecSchema.setPayRefFee(""+resultSet.getDouble("payRefFee"));
          
          prpJpayRefRecSchema.setPoaType(""+resultSet.getString("PoaType"));
          prpJpayRefRecSchema.setIdentifyType(""+resultSet.getString("IdentifyType"));
          
        
          prpJpayRefRecSchema.setPayRefNo(resultSet.getString("PayRefNo"));
          
          this.setArr(prpJpayRefRecSchema);
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(NamingException namingException)
      {
        dbpool.close();
        throw namingException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     *发票作废
     *@param String[] visaserialno 发票号
     *@throws Exception
     */
    public boolean dropInvioce(String[] visaSerialNo) throws Exception
    {
      boolean result = true;
      String visaCollection = "";
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      PrpJpayRefRecSchema prpJpayRefRecSchema = null;
      for (int i = 0; i < visaSerialNo.length; i++)
      {
        visaCollection = visaCollection + ",'" + visaSerialNo[i] + "'";
      }
      visaCollection = "(" + visaCollection.substring(1) + ")";
      String strSqlStatement =
          "SELECT * FROM PrpJpayRefRec WHERE VisaSerialNo in " + visaCollection
          + " AND PayRefDate is NULL AND PayRefNo is NULL";
      initArr();
      schemas = dbPrpJpayRefRec.findByConditions(strSqlStatement);
      DbPool dbpool = new DbPool();
      
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        dbpool.beginTransaction();
        for (int i = 0; i < this.getSize(); i++)
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema = this.getArr(i);
          dbPrpJplanFee.getInfo(dbpool, prpJpayRefRecSchema.getCertiType(),
                                prpJpayRefRecSchema.getCertiNo(),
                                prpJpayRefRecSchema.getSerialNo(),
                                prpJpayRefRecSchema.getPayRefReason());
          dbPrpJplanFee.setPayRefFee("0");
          dbPrpJplanFee.update();
          dbPrpJpayRefRec.delete(dbpool, prpJpayRefRecSchema.getCertiType(),
                                 prpJpayRefRecSchema.getCertiNo(),
                                 prpJpayRefRecSchema.getSerialNo(),
                                 prpJpayRefRecSchema.getPayRefReason(),
                                 prpJpayRefRecSchema.getPayRefTimes());
        }
        dbpool.commitTransaction();
        dbpool.close();
      }
      catch (UserException userexception)
      {
        result = false;
        dbpool.rollbackTransaction();
        dbpool.close();
        throw userexception;
      }
      catch (SQLException sqlexception)
      {
        result = false;
        dbpool.rollbackTransaction();
        dbpool.close();
        throw sqlexception;
      }
      catch (Exception exception)
      {
        result = false;
        dbpool.rollbackTransaction();
        dbpool.close();
        throw exception;
      }
      finally {
        dbpool.close();
      }
      return result;
    }

    /**
     * @author lijibin 20050612 发票作废
     * @param iVisaCode 发票类型
     * @param iVisaSerialNo 发票号
     * @throws Exception
     */
    public void dropInvioce(String iVisaCode,String iVisaSerialNo)
       throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        dbpool.beginTransaction();
        this.dropInvioce(dbpool,iVisaCode,iVisaSerialNo);
        dbpool.commitTransaction();
      }
      catch(UserException ue)
      {
        dbpool.rollbackTransaction();
        throw ue;
      }
      catch(Exception e)
      {
        dbpool.rollbackTransaction();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     * @author lijibin 20050612 发票作废
     * @param dbpool
     * @param iVisaCode 发票类型
     * @param iVisaSerialNo 发票号
     * @throws Exception
     */
    public void dropInvioce(DbPool dbpool,String iVisaCode,String iVisaSerialNo) throws Exception
    {
      String strWherePart = "";
      
      DBPrpJInvoicePre dbPrpJInvoicePre = new DBPrpJInvoicePre();
      String YJFPFlag ="";
      String[] aiVisaSerialNo = iVisaSerialNo.split("_");
      if(aiVisaSerialNo.length>1){
    	  iVisaSerialNo = aiVisaSerialNo[0];
    	  YJFPFlag = aiVisaSerialNo[1];
      }else{
    	  iVisaSerialNo = aiVisaSerialNo[0];
      }
      
      if(iVisaCode==null||iVisaCode.equals(""))
      {
        strWherePart = " VisaCode IS NULL";
      }
      else
      {
        strWherePart = " VisaCode='"+iVisaCode+"'";
      }
      
      strWherePart += " AND VisaSerialNo='" + iVisaSerialNo 
	                  + "' AND PayRefTimes<1000 ";
      this.query(dbpool,strWherePart,0);
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      
      
      DBPrpJplanPrint dbPrpJplanPrint = null;
      

      
      PrpJpayRefRecSchema prpJpayRefRecSchema = null;
      
      /*ADD BY PENGJINLING 2012-3-5 payment-435 产XXXXX广东省分公司营业税及附加的纳税基数规则调整 BEGIN*/
      PrpJpayRefRecSchema prpJpayRefRecSchemaOne = (PrpJpayRefRecSchema)this.schemas.get(0);     
	  
      if(prpJpayRefRecSchemaOne.getComCode().length()>2 
    		  && "08".equals(prpJpayRefRecSchemaOne.getComCode().substring(0, 2))){
	      this.dealVisa99(dbpool,this.schemas);
	  }
      /*ADD BY PENGJINLING 2012-3-5 payment-435 产XXXXX广东省分公司营业税及附加的纳税基数规则调整 END*/      
      
      BLPrpJpayRefRecTrace blPrpJpayRefRecTrace = new BLPrpJpayRefRecTrace();
      
      String strPayRefFee_Invoice_Switch = "";
      try {
    	  com.sp.payment.utility.PaymentTransCode paymentTransCode = new com.sp.payment.utility.PaymentTransCode();
    	  strPayRefFee_Invoice_Switch = paymentTransCode.getConfigValueByComCode(prpJpayRefRecSchemaOne.getCenterCode(), "0000","PayRefFee_Invoice_Switch"); 
      } catch (Exception e) {
    	  e.printStackTrace();
      }
      int inttemp = 0;
      String strPoaype = "";
      for (int i = 0; i < this.getSize(); i++)
      {
        prpJpayRefRecSchema = new PrpJpayRefRecSchema();
        prpJpayRefRecSchema = this.getArr(i);
        if(strPayRefFee_Invoice_Switch.length() >= 8){
        	if(inttemp == 0){
        		if("".equals(prpJpayRefRecSchema.getRedFlag())){
        			strPoaype = prpJpayRefRecSchema.getPoaType();
        			++ inttemp;
        		}
        	}
        	
        	if ("1".equals(prpJpayRefRecSchema.getIsDrop())) {
        		throw new UserException(-98, -1003, "BLPrpJpayRefRec.dropInvioce", "通过<<发票重新打印>>功能打印出的发票不允许作废。");
        	}
        }
		
        
        dbPrpJplanFee = new DBPrpJplanFee();
        int intResult = dbPrpJplanFee.getInfo(dbpool, prpJpayRefRecSchema.getCertiType(),
                              prpJpayRefRecSchema.getCertiNo(),
                              prpJpayRefRecSchema.getSerialNo(),
                              prpJpayRefRecSchema.getPayRefReason());
        if(intResult==0){
        dbPrpJplanFee.setPayRefFee("0");
        dbPrpJplanFee.setInvPrintFlag("0");
        dbPrpJInvoicePre = new DBPrpJInvoicePre();
        dbPrpJInvoicePre.getInfo(dbpool, prpJpayRefRecSchema.getCertiType(),
                prpJpayRefRecSchema.getCertiNo(),
                prpJpayRefRecSchema.getSerialNo(),
                prpJpayRefRecSchema.getPayRefReason());
        





        
        
        
        
        dbPrpJplanPrint = new DBPrpJplanPrint();
        dbPrpJplanPrint.setSchema(dbPrpJplanFee);
        if(!"".equals(dbPrpJplanFee.getRealPayRefFeeNew())&&!"0".equals(dbPrpJplanFee.getRealPayRefFeeNew())){
        	dbPrpJplanPrint.setRealPayRefFee(dbPrpJplanFee.getRealPayRefFeeNew());
        }
        
        if(YJFPFlag.equals("1")){
        	if(dbPrpJplanFee.getApproveStatus().equals("3")){
        		dbPrpJplanFee.setApproveStatus("4");
        		dbPrpJplanPrint.setApproveStatus("4");
        		dbPrpJInvoicePre.setApproveStatus("4");
        	}      	
        }else{
        	if(dbPrpJplanFee.getApproveStatus().equals("3")){
        		dbPrpJplanFee.setApproveStatus("1");
        		dbPrpJplanPrint.setApproveStatus("1");
        		dbPrpJInvoicePre.setApproveStatus("1");
        	} 
        }
        dbPrpJInvoicePre.update(dbpool);
        dbPrpJplanFee.update(dbpool);
    	
        dbPrpJplanPrint.insert(dbpool);
        
      }
        






        
      
        
        
        PrpJpayRefRecTraceSchema prpJpayRefRecTraceSchema = this.transSchema(this.getArr(i));
        prpJpayRefRecTraceSchema.setFlag3("Drop");
        prpJpayRefRecTraceSchema.setInvoiceStatus("0");
        /* ADD BY PENGJINLING 2013-1-9 payment-520 福建发票 作废发票需上传校验码 BEGIN*/
        if(this.getArr(i).getComCode().length()>2 
      		  && "35".equals(this.getArr(i).getComCode().substring(0, 2))){
	        DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
	        dbPrpJpayRefMain.getInfo(dbpool,this.getArr(i).getPayRefNo(), "1");
	        String flag = dbPrpJpayRefMain.getCheckCode();
	        if(flag != null && !"".equals(flag) && flag.length() <= 10){
	        	prpJpayRefRecTraceSchema.setFlag(flag);
	        }
        }
        /* ADD BY PENGJINLING 2013-1-9 payment-520 福建发票 作废发票需上传校验码 END*/
        blPrpJpayRefRecTrace.setArr(prpJpayRefRecTraceSchema);
      }
      blPrpJpayRefRecTrace.save(dbpool);
      
      










      
      if (strPayRefFee_Invoice_Switch.length() >= 8) {
    	  dropInvioceNew(dbpool, this, strWherePart);
    		return;
    	}
      
		for (int i = 0; i < this.getSize(); i++) {
			if ("1".equals(this.getArr(i).getIdentifyType())) {
				throw new UserException(-98, -1003, "BLPrpJpayRefRec.dropInvioce","已实收发票不允许作废！");
			}
        }
		
      dbPrpJpayRefRec.deleteByVisaCode(dbpool, iVisaCode, iVisaSerialNo);
      
      
      
     
      
     
      
      if(this.getArr(0).getCertiType().equals("D")){
    	  BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
          DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
    	  PrpJpayRefMainSchema prpJpayRefMainSchema = new PrpJpayRefMainSchema();
      	  String payRefNoCollection = "";
      	  for(int i = 0;i<this.getSize();i++){
      		
            if(this.getArr(i).getIdentifyNumber()==null ||this.getArr(i).getIdentifyNumber().equals("0")){
           
            	throw new UserException(-98,-1003,"BLPrpJpayRefRec.query");
                
            }
      		payRefNoCollection = payRefNoCollection + ",'" + this.getArr(i).getPayRefNo() + "'";
      	  }
      	  payRefNoCollection = "(" + payRefNoCollection.substring(1) + ")";
    	  
      	  strWherePart = " NVL(RedFlag,'0') <> 'Reded' ANDPayRefNo in" + payRefNoCollection;
          
      	  blPrpJpayRefMain.query(strWherePart,0);
    	  for(int i=0;i<blPrpJpayRefMain.getSize();i++)
          {
            prpJpayRefMainSchema = new PrpJpayRefMainSchema();
            prpJpayRefMainSchema = blPrpJpayRefMain.getArr(i);
            dbPrpJpayRefMain.delete(dbpool,prpJpayRefMainSchema.getPayRefNo(),prpJpayRefMainSchema.getSerialNo());
          }
      }
      
      
      String arrPayRefNo = "";
      for(int i = 0;i<this.getSize();i++){
          if(this.getArr(i).getIdentifyNumber().equals("1")){
          	  throw new UserException(-98,-1003,"BLPrpJpayRefRec.dropInvioce","发票已经打包，请拆包后再作废！");
          }
          arrPayRefNo = arrPayRefNo + ",'" + this.getArr(i).getPayRefNo() + "'";
      }
      arrPayRefNo = "(" + arrPayRefNo.substring(1) + ")";
      
      String sql = " delete from prpjpayrefmain where payrefno in " + arrPayRefNo+" AND NVL(RedFlag,'0') <> 'Reded'";
      
      dbpool.prepareInnerStatement(sql);
      dbpool.executePreparedUpdate();
      dbpool.closePreparedStatement();
      
      
      
      
      
      
      
      
      
      
      
      BLPrpJPayBank blPrpJPayBank = new BLPrpJPayBank();
      
      if ("1".equals(blPrpJPayBank.getConfigValueByComCode(prpJpayRefRecSchema.getOperateUnit(), "0000", "JSRED_SWITCHNEW"))) {
      
      
      
          this.updateRedInvoice(dbpool, prpJpayRefRecSchema.getPayRefNo());
      }
      
      
      Visa visa = new Visa();
      visa.cancelR(dbpool, iVisaCode, iVisaSerialNo, prpJpayRefRecSchema.getPrinterCode());
      
    }
 
    /**
     * @author Neil.Fu 20070125 垫付单作废
     * @param dbpool
     * @param iVisaCode
     * @param iVisaSerialNo
     * @throws Exception
     */
    public boolean dropCollisionInvioce(String[] visaSerialNo) throws Exception
    {
    	 boolean result = true;
         String visaCollection = "";
         DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
         BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
         DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
         PrpJpayRefRecSchema prpJpayRefRecSchema = null;
         for (int i = 0; i < visaSerialNo.length; i++)
         {
           visaCollection = visaCollection + ",'" + visaSerialNo[i] + "'";
         }
         visaCollection = "(" + visaCollection.substring(1) + ")";
         String strSqlStatement =
             "SELECT * FROM PrpJpayRefRec WHERE VisaSerialNo in " + visaCollection
             + " AND PayRefDate is NULL AND PayRefNo is NULL";
         initArr();
         schemas = dbPrpJpayRefRec.findByConditions(strSqlStatement);
         DbPool dbpool = new DbPool();
         
         try {
             
             dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
             
           dbpool.beginTransaction();
           for (int i = 0; i < this.getSize(); i++)
           {
             prpJpayRefRecSchema = new PrpJpayRefRecSchema();
             prpJpayRefRecSchema = this.getArr(i);
             dbPrpJplanFee.getInfo(dbpool, prpJpayRefRecSchema.getCertiType(),
                                   prpJpayRefRecSchema.getCertiNo(),
                                   prpJpayRefRecSchema.getSerialNo(),
                                   prpJpayRefRecSchema.getPayRefReason());
             dbPrpJplanFee.setPayRefFee("0");
             dbPrpJplanFee.update();
             dbPrpJpayRefRec.delete(dbpool, prpJpayRefRecSchema.getCertiType(),
                                    prpJpayRefRecSchema.getCertiNo(),
                                    prpJpayRefRecSchema.getSerialNo(),
                                    prpJpayRefRecSchema.getPayRefReason(),
                                    prpJpayRefRecSchema.getPayRefTimes());
           }
           dbpool.commitTransaction();
           dbpool.close();
         }
         catch (UserException userexception)
         {
           result = false;
           dbpool.rollbackTransaction();
           dbpool.close();
           throw userexception;
         }
         catch (SQLException sqlexception)
         {
           result = false;
           dbpool.rollbackTransaction();
           dbpool.close();
           throw sqlexception;
         }
         catch (Exception exception)
         {
           result = false;
           dbpool.rollbackTransaction();
           dbpool.close();
           throw exception;
         }
         finally {
           dbpool.close();
         }
         return result;
    }
    /**
     * @author Neil.Fu 20070125 垫付单作废
     * @param dbpool
     * @param iVisaCode
     * @param iVisaSerialNo
     * @throws Exception
     */
    public void dropCollisionInvioce(String iVisaCode,String iVisaSerialNo) throws Exception
    {
    	DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
          dbpool.beginTransaction();
          this.dropInvioce(dbpool,iVisaCode,iVisaSerialNo);
          dbpool.commitTransaction();
        }
        catch(UserException ue)
        {
          dbpool.rollbackTransaction();
          throw ue;
        }
        catch(Exception e)
        {
          dbpool.rollbackTransaction();
          throw e;
        }
        finally {
          dbpool.close();
        }
    } 
    /**
     * @author Neil.Fu 20070125 垫付单作废
     * @param dbpool
     * @param iVisaCode
     * @param iVisaSerialNo
     * @throws Exception
     */
    public void dropCollisionInvioce(DbPool dbpool,String iVisaCode,String iVisaSerialNo) throws Exception
    {
    	String strWherePart = "";
        if(iVisaCode==null||iVisaCode.equals(""))
        {
          strWherePart = " VisaCode IS NULL";
        }
        else
        {
          strWherePart = " VisaCode='"+iVisaCode+"'";
        }
        
        strWherePart += " AND VisaSerialNo='" + iVisaSerialNo 
  	                  + "' AND PayRefTimes<1000 ";
        this.query(dbpool,strWherePart,0);
        DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
        DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
        for (int i = 0; i < this.getSize(); i++)
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema = this.getArr(i);
          
          dbPrpJplanFee = new DBPrpJplanFee();
          dbPrpJplanFee.getInfo(dbpool, prpJpayRefRecSchema.getCertiType(),
                                prpJpayRefRecSchema.getCertiNo(),
                                prpJpayRefRecSchema.getSerialNo(),
                                prpJpayRefRecSchema.getPayRefReason());
          dbPrpJplanFee.setPayRefFee("0");
          dbPrpJplanFee.update(dbpool);

          
          dbPrpJpayRefRec.delete(dbpool,prpJpayRefRecSchema.getCertiType(),
                                 prpJpayRefRecSchema.getCertiNo(),
                                 prpJpayRefRecSchema.getSerialNo(),
                                 prpJpayRefRecSchema.getPayRefReason(),
                                 prpJpayRefRecSchema.getPayRefTimes());
        }
        
        Visa visa = new Visa();
        
        visa.cancelR(dbpool,iVisaCode,iVisaSerialNo,prpJpayRefRecSchema.getPrinterCode());
      
    }
    
    
    /**
     *发票打包查询
     *@param String condition
     *@throws Exception
     */
    public void packageQuery(String strCondition) throws Exception
    {
      
      String strSqlStatement = "SELECT VisaCode,VisaName,visaserialno,Currency2,PrintDate,PrinterCode,"
          + "VisaHandler,PayRefName,CoinsFlag,CoinsType, "
          + "sum(PayRefFee) as PayRefFee FROM PrpJpayRefRec WHERE " + strCondition
          + " Group By VisaCode,VisaName,VisaSerialNo,Currency2,PrintDate,PrinterCode,"
          + "VisaHandler,PayRefName,CoinsFlag,CoinsType ORDER BY VisaSerialNo";
      DbPool dbpool = new DbPool();
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema.setVisaCode(resultSet.getString("VisaCode"));
          prpJpayRefRecSchema.setVisaName(resultSet.getString("VisaName"));
          prpJpayRefRecSchema.setCurrency2(resultSet.getString("currency2"));
          prpJpayRefRecSchema.setVisaSerialNo(resultSet.getString("visaSerialNo"));
          prpJpayRefRecSchema.setPrintDate(""+resultSet.getDate("PrintDate"));
          prpJpayRefRecSchema.setPrinterCode(resultSet.getString("PrinterCode"));
          prpJpayRefRecSchema.setVisaHandler(resultSet.getString("VisaHandler"));
          prpJpayRefRecSchema.setPayRefName(resultSet.getString("PayRefName"));
          prpJpayRefRecSchema.setCoinsFlag(resultSet.getString("CoinsFlag"));
          prpJpayRefRecSchema.setCoinsCode(resultSet.getString("CoinsType"));
          
          prpJpayRefRecSchema.setPayRefFee(""+resultSet.getDouble("payRefFee"));
          this.setArr(prpJpayRefRecSchema);
          
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(NamingException namingException)
      {
        dbpool.close();
        throw namingException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }
    
    /**
     *发票打包查询(新)
     *@param String condition
     *@throws Exception
     */
    public void packageQueryNew(String strCondition) throws Exception
    {
      
      String strSqlStatement = "SELECT VisaCode,VisaName,visaserialno,certitype,certino,serialno,payrefreason,Currency2,PrintDate,PrinterCode,"
          + "VisaHandler,PayRefName,CoinsFlag,CoinsType, "
          + "PayRefFee FROM PrpJpayRefRec WHERE " + strCondition
          + "ORDER BY VisaSerialNo,certino";
      DbPool dbpool = new DbPool();
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema.setVisaCode(resultSet.getString("VisaCode"));
          prpJpayRefRecSchema.setVisaName(resultSet.getString("VisaName"));
          prpJpayRefRecSchema.setCurrency2(resultSet.getString("currency2"));
          prpJpayRefRecSchema.setVisaSerialNo(resultSet.getString("visaSerialNo"));
          prpJpayRefRecSchema.setCertiType(resultSet.getString("Certitype"));
          prpJpayRefRecSchema.setCertiNo(resultSet.getString("CertiNo"));
          prpJpayRefRecSchema.setSerialNo(resultSet.getString("SerialNo"));
          prpJpayRefRecSchema.setPayRefReason(resultSet.getString("PayRefReason"));
          prpJpayRefRecSchema.setPrintDate(""+resultSet.getDate("PrintDate"));
          prpJpayRefRecSchema.setPrinterCode(resultSet.getString("PrinterCode"));
          prpJpayRefRecSchema.setVisaHandler(resultSet.getString("VisaHandler"));
          prpJpayRefRecSchema.setPayRefName(resultSet.getString("PayRefName"));
          prpJpayRefRecSchema.setCoinsFlag(resultSet.getString("CoinsFlag"));
          prpJpayRefRecSchema.setCoinsCode(resultSet.getString("CoinsType"));
          
          prpJpayRefRecSchema.setPayRefFee(""+resultSet.getDouble("payRefFee"));
          this.setArr(prpJpayRefRecSchema);
          
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(NamingException namingException)
      {
        dbpool.close();
        throw namingException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     *发票打包
     *@author YangZhen 判断主联以及主共单是对应的从联方是否打过发票
     *@param String[] visaCode 发票单证类型
     *@param String[] visaSerialNo 发票号
     *@throws Exception
     */
    public void queryInvioceCoins(String[] visaCode,String[] visaSerialNo) throws Exception
    {
      String strVisaWhere = "";
      String strWherePart = "";
      String strErrorMessage = "";

      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
      PrpJpayRefRecSchema prpJpayRefRecSchema = null;
      try
      {
        strVisaWhere = "( (VisaCode = '" + visaCode[0] + "' AND VisaSerialNo = '" + visaSerialNo[0] + "') ";
        for (int i = 1; i < visaSerialNo.length; i++)
        {
          strVisaWhere = strVisaWhere + " OR (VisaCode = '" + visaCode[i] + "' AND VisaSerialNo = '" + visaSerialNo[i] + "')";

        }
        strVisaWhere = strVisaWhere + ")";
        
        String strSqlStatement =
            "SELECT * FROM PrpJpayRefRec WHERE " + strVisaWhere
            + " AND identifytype='0' AND identifynumber='0' Order By VisaCode,VisaSerialNo,SerialNo";
        
        initArr();
        schemas = dbPrpJpayRefRec.findByConditions(strSqlStatement);

        for (int i = 0; i < this.getSize(); i++)
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema = this.getArr(i);
          if((prpJpayRefRecSchema.getCoinsFlag().equals("1")||
              prpJpayRefRecSchema.getCoinsFlag().equals("3"))&&
              prpJpayRefRecSchema.getCoinsType().equals("1"))
          {
            String strPayRefReason = "";
            if(prpJpayRefRecSchema.getCertiType().equals("P")||
                prpJpayRefRecSchema.getCertiType().equals("E"))
            {
              if(prpJpayRefRecSchema.getPayRefReason().equals("P40"))
                strPayRefReason = "('P42')";
              else
                strPayRefReason = "('R82','P82')";
            }
            else if(prpJpayRefRecSchema.getCertiType().equals("S"))
              strPayRefReason = "('P92')";
            else
              strPayRefReason = "('')";

            strWherePart = " CertiNo='" + prpJpayRefRecSchema.getCertiNo() + "'"
                         + " And CertiType='" + prpJpayRefRecSchema.getCertiType() + "'"
                         + " And PayNo='" + prpJpayRefRecSchema.getPayNo() + "'"
                         + " And PayRefReason In " + strPayRefReason
                         + " And PayRefFee = 0";
            blPrpJplanFee.query(strWherePart,0);
            if(blPrpJplanFee.getSize()>0)
              strErrorMessage = prpJpayRefRecSchema.getCertiNo() + "\n";
          }
        }
        if(strErrorMessage.length()>0)
        {
          strErrorMessage = "以下单号的从联方还未打印发票或支付单，不允许打包处理或做收付处理：\n" + strErrorMessage;
          throw new UserException( -96, -1167, "BLPrpJpayRefRec.queryInvioceCoins", strErrorMessage);
        }
      }
      catch (UserException userexception)
      {
        throw userexception;
      }
      catch (SQLException sqlexception)
      {
        throw sqlexception;
      }
      catch (Exception exception)
      {
        throw exception;
      }
    }

    public void queryInvioceCoins(Vector iSchema) throws Exception
    {
      String strWherePart = "";
      String strPayRefNo = "";
      String strErrorMessage = "";

      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
      PrpJpayRefRecSchema prpJpayRefRecSchema = null;
      PrpJpayRefMainSchema prpJpayRefMainSchema = null;
      try
      {
        prpJpayRefMainSchema = (PrpJpayRefMainSchema)iSchema.get(0);
        strPayRefNo = prpJpayRefMainSchema.getPayRefNo();
        strWherePart = " PayRefNo In ('" + strPayRefNo  + "'";
        for (int i = 1; i < iSchema.size(); i++)
        {
          prpJpayRefMainSchema = (PrpJpayRefMainSchema)iSchema.get(i);
          if(strPayRefNo.equals(prpJpayRefMainSchema.getPayRefNo()))
            continue;
          else
          {
            strPayRefNo = prpJpayRefMainSchema.getPayRefNo();
            strWherePart = strWherePart + ",'" + strPayRefNo + "'";
          }
        }
        strWherePart = strWherePart + ")";
        String strSqlStatement =
            "SELECT * FROM PrpJpayRefRec WHERE " + strWherePart
            + " AND identifytype='0' Order By VisaCode,VisaSerialNo,SerialNo";
        
        initArr();
        schemas = dbPrpJpayRefRec.findByConditions(strSqlStatement);
        for (int i = 0; i < this.getSize(); i++)
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema = this.getArr(i);
          if((prpJpayRefRecSchema.getCoinsFlag().equals("1")||
              prpJpayRefRecSchema.getCoinsFlag().equals("3"))&&
              prpJpayRefRecSchema.getCoinsType().equals("1"))
          {
            String strPayRefReason = "";
            if(prpJpayRefRecSchema.getCertiType().equals("P")||
                prpJpayRefRecSchema.getCertiType().equals("E"))
            {
              if(prpJpayRefRecSchema.getPayRefReason().equals("P40"))
                strPayRefReason = "('P42')";
              else
                strPayRefReason = "('R82','P82')";
            }
            else if(prpJpayRefRecSchema.getCertiType().equals("S"))
              strPayRefReason = "('P92')";
            else if(prpJpayRefRecSchema.getCertiType().equals("C"))
            {
              strPayRefReason = "('F"+ prpJpayRefRecSchema.getPayRefReason().substring(1) +"')";
            }
            else
              strPayRefReason = "('')";

            strWherePart = " CertiNo='" + prpJpayRefRecSchema.getCertiNo() + "'"
                         + " And CertiType='" + prpJpayRefRecSchema.getCertiType() + "'"
                         + " And PayNo='" + prpJpayRefRecSchema.getPayNo() + "'"
                         + " And PayRefReason In " + strPayRefReason
                         + " And PayRefFee = 0";

            blPrpJplanFee.query(strWherePart,0);
            if(blPrpJplanFee.getSize()>0)
              strErrorMessage = prpJpayRefRecSchema.getCertiNo() + "\n";
          }
        }
        if(strErrorMessage.length()>0)
        {
          strErrorMessage = "以下单号的从联方还未打印发票或支付单，不允许打包处理或做收付处理：\n" + strErrorMessage;
          throw new UserException( -96, -1167, "BLPrpJpayRefRec.queryInvioceCoins", strErrorMessage);
        }
      }
      catch (UserException userexception)
      {
        throw userexception;
      }
      catch (SQLException sqlexception)
      {
        throw sqlexception;
      }
      catch (Exception exception)
      {
        throw exception;
      }
    }


    /**
     *发票打包
     *@param String[] visaserialno 发票号
     *@throws Exception
     */
    public String packageInvioce(String[] visaCode,String[] visaSerialNo, String packageCode,
                                 String packageUnit, String packageDate) throws Exception
    {
      String strPayRefNo = "";
      String strVisaWhere = "";
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      PrpJpayRefRecSchema prpJpayRefRecSchema = null;
      
      strVisaWhere = "( (VisaCode = '" + visaCode[0] + "' AND VisaSerialNo = '" + visaSerialNo[0] + "') ";
      for (int i = 1; i < visaSerialNo.length; i++)


      {
        strVisaWhere = strVisaWhere + " OR (VisaCode = '" + visaCode[i] + "' AND VisaSerialNo = '" + visaSerialNo[i] + "')";

      }
      strVisaWhere = strVisaWhere + ")";
      String strSqlStatement =
          "SELECT * FROM PrpJpayRefRec WHERE " + strVisaWhere
          + " AND identifytype='0' AND identifynumber='0' Order By VisaCode,VisaSerialNo,SerialNo";
      

      initArr();
      this.schemas = dbPrpJpayRefRec.findByConditions(strSqlStatement);
      
      if(this.getSize()==0){
        throw new UserException( -98, -1167, "BLPrpJpayRefRec.packageInvioce",
                                  "该发票已经打过包！");
      }
      
      for(int i = 0;i < this.getSize(); i++){
          if("E".equals(this.getArr(i).getCertiType())||"P".equals(this.getArr(i).getCertiType())){
              if("2".equals(this.getArr(i).getPoaType())||"5".equals(this.getArr(i).getPoaType()) ||
              		"3".equals(this.getArr(i).getPoaType())||"4".equals(this.getArr(i).getPoaType()) ){
                  throw new UserException(-96, -1167, "BLPrpJpayRefRec.packageInvioce","业务号："
                  +this.getArr(i).getCertiNo()+" 是见费出单业务（发票号"+this.getArr(i).getVisaSerialNo()+"），不能打包！");
              }
          }
        }
      
      
      Bill bill = new Bill();
      String billNo = "";
      int intYear = Integer.parseInt(packageDate.substring(0,4));
      billNo = bill.getNo("prpjpayrec","0501",packageUnit,intYear,"00");
      
      if(billNo.equals("")){
      	throw new UserException(-98,-1167,"BLPrpJpayRefRec.packageInvioce",
      			                 packageUnit.substring(0,4)+ "0501" + intYear + "单号没有初始化，请联系系统管理员！");
      }
      strPayRefNo = this.createPayRefNo(billNo,"0501",packageUnit);

      DbPool dbpool = new DbPool();
      
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        dbpool.beginTransaction();
        String payRefNoTemp = "";
        int j = 1;
        for (int i = 0; i < this.getSize(); i++)
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema = this.getArr(i);
          
          dbPrpJpayRefRec.getInfoForUpdate(dbpool, prpJpayRefRecSchema.getCertiType(),
                                  prpJpayRefRecSchema.getCertiNo(),
                                  prpJpayRefRecSchema.getSerialNo(),
                                  prpJpayRefRecSchema.getPayRefReason(),
                                  prpJpayRefRecSchema.getPayRefTimes());
          
          if(dbPrpJpayRefRec.getIdentifyNumber()!=null&&dbPrpJpayRefRec.getIdentifyNumber().equals("1")){
         
            throw new UserException( -98, -1167, "BLPrpJpayRefRec.packageInvioce",
                                      "该发票已经打过包！");
          }
          
          String strPayRefNoOld = dbPrpJpayRefRec.getPayRefNo();
          dbPrpJpayRefRec.setFlag1(strPayRefNoOld);
          
          dbPrpJpayRefRec.setPayRefNo(strPayRefNo);
          dbPrpJpayRefRec.setIdentifyNumber("1");
          dbPrpJpayRefRec.update(dbpool);
          
          
          if(!payRefNoTemp.equals(strPayRefNoOld)){
        	  if(dbPrpJpayRefMain.getInfo(dbpool,strPayRefNoOld,"1") == 100)
        	  {
        		  throw new UserException( -98, -1167, "BLPrpJpayRefRec.packageInvioce",
                  "发票" + dbPrpJpayRefRec.getVisaSerialNo() + "没有prpjpayrefmain数据！");
        	  }else
        	  {
        		  dbPrpJpayRefMain.setPayRefNo(strPayRefNo);
        		  dbPrpJpayRefMain.setSerialNo(""+j);
        		  dbPrpJpayRefMain.setPayRefNoBak(strPayRefNoOld);
        		  dbPrpJpayRefMain.updateByPayRefNo(dbpool);
        		  payRefNoTemp = strPayRefNoOld;
        		  j++;
        	  }
          }
          
























        }
        dbpool.commitTransaction();
        dbpool.close();
        bill.deleteNo("prpjpayrec",billNo);
      }
      catch (UserException userexception)
      {
        dbpool.rollbackTransaction();
        dbpool.close();
        bill.putNo("prpjpayrec",billNo);
        throw userexception;
      }
      catch (SQLException sqlexception)
      {
        dbpool.rollbackTransaction();
        dbpool.close();
        bill.putNo("prpjpayrec",billNo);
        throw sqlexception;
      }
      catch (Exception exception)
      {
        dbpool.rollbackTransaction();
        dbpool.close();
        bill.putNo("prpjpayrec",billNo);
        throw exception;
      }
      finally {
        dbpool.close();
      }

      return strPayRefNo;
    }

    /**
     *联XXXXX方发票打包
     *@param
     *@throws Exception
     */
    public String packageInvioceCoins(String packageCode,String packageUnit,
        String packageDate,BLPrpJpayRefRec iBLPrpJpayRefRec,
        Vector iSchema) throws Exception
    {
      String strPayRefNo = "";
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      PrpJpayRefRecSchema prpJpayRefRecSchema = null;
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      
      Bill bill = new Bill();
      String billNo = "";
      int intYear = Integer.parseInt(packageDate.substring(0,4));
      billNo = bill.getNo("prpjpayrec","0501",packageUnit,intYear,"00");
      strPayRefNo = this.createPayRefNo(billNo,"0501",packageUnit);

      DbPool dbpool = new DbPool();
      
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        dbpool.beginTransaction();
        int j = 1;
        for (int i = 0; i < this.getSize(); i++)
        {
          if(!this.getArr(i).getCoinsFlag().equals("1")&&
              !this.getArr(i).getCoinsFlag().equals("3"))
            continue;
          if(!this.getArr(i).getCoinsType().equals("1"))
            continue;

          String strPayRefReasonTmp = "";
          String strWherePart = "";
          BLPrpJpayRefRec blPrpJpayRefRecTmp = new BLPrpJpayRefRec();
          if(this.getArr(i).getCertiType().equals("P")||
              this.getArr(i).getCertiType().equals("E"))
          {
            if(this.getArr(i).getPayRefReason().equals("P40"))
              strPayRefReasonTmp = "('P42')";
            else
              strPayRefReasonTmp = "('R82','P82')";
          }
          else if(this.getArr(i).getCertiType().equals("S"))
            strPayRefReasonTmp = "('P92')";
          else
            strPayRefReasonTmp = "('')";

          strWherePart = " CertiNo='"+ this.getArr(i).getCertiNo() +"'"
                       + " And PayRefReason In " + strPayRefReasonTmp
                       + " And PayNo=" + this.getArr(i).getPayNo()
                       + " And CoinsType='2'";
          blPrpJpayRefRecTmp.query(strWherePart,0);

          for(int m=0;m<blPrpJpayRefRecTmp.getSize();m++)
          {
            prpJpayRefRecSchema = new PrpJpayRefRecSchema();
            prpJpayRefRecSchema.setSchema(blPrpJpayRefRecTmp.getArr(m));
            iBLPrpJpayRefRec.setArr(prpJpayRefRecSchema);
            dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
            dbPrpJpayRefRec.setFlag1(dbPrpJpayRefRec.getPayRefNo());
            dbPrpJpayRefRec.setPayRefNo(strPayRefNo);
            dbPrpJpayRefRec.setIdentifyNumber("1");
            dbPrpJpayRefRec.update(dbpool);
            
            String sql = " update prpjpayrefmain set payrefno='"+strPayRefNo+"',serialno='"+j+"',payrefnobak=payrefno,identifynumber='1' where payrefno='"+dbPrpJpayRefRec.getFlag1()+"'";
            dbpool.prepareInnerStatement(sql);
            dbpool.executePreparedUpdate();
            dbpool.closePreparedStatement();
            j++;
          }
        }

        dbpool.commitTransaction();
        dbpool.close();
        if(j == 1)
          bill.putNo("prpjpayrec",billNo);
        else
          bill.deleteNo("prpjpayrec",billNo);
      }
      catch (UserException userexception)
      {
        dbpool.rollbackTransaction();
        dbpool.close();
        bill.putNo("prpjpayrec",billNo);
        throw userexception;
      }
      catch (SQLException sqlexception)
      {
        dbpool.rollbackTransaction();
        dbpool.close();
        bill.putNo("prpjpayrec",billNo);
        throw sqlexception;
      }
      catch (Exception exception)
      {
        dbpool.rollbackTransaction();
        dbpool.close();
        bill.putNo("prpjpayrec",billNo);
        throw exception;
      }
      finally {
        dbpool.close();
      }
      return strPayRefNo;
    }


    /**
     *发票拆包查询
     *@param String condition
     *@throws Exception
     */
    public void dePackageQuery(String strCondition) throws Exception
    {
      
      String strSqlStatement = "SELECT PayRefNo,VisaCode,VisaName,visaserialno,Currency2,sum(PayRefFee) as PayRefFee,PrintDate,PrinterCode,VisaHandler,PayRefName FROM PrpJpayRefRec WHERE " + strCondition;
      strSqlStatement = strSqlStatement + " Group By PayRefNo,VisaCode,VisaName,visaserialno,Currency2,PrintDate,PrinterCode,VisaHandler,PayRefName Order By PayRefNo,VisaSerialNo";
      DbPool dbpool = new DbPool();
      ResultSet resultSet = null;
      PrpJpayRefRecSchema prpJpayRefRecSchema = null;
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        resultSet = dbpool.query(strSqlStatement);
        while (resultSet.next())
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema.setPayRefNo(resultSet.getString("PayRefNo"));
          prpJpayRefRecSchema.setVisaCode(resultSet.getString("VisaCode"));
          prpJpayRefRecSchema.setVisaName(resultSet.getString("VisaName"));
          prpJpayRefRecSchema.setVisaSerialNo(resultSet.getString("visaSerialNo"));
          prpJpayRefRecSchema.setCurrency2(resultSet.getString("currency2"));
          prpJpayRefRecSchema.setPayRefFee(resultSet.getString("payRefFee"));
          prpJpayRefRecSchema.setPrintDate("" + resultSet.getDate("PrintDate"));
          prpJpayRefRecSchema.setPrinterCode(resultSet.getString("PrinterCode"));
          prpJpayRefRecSchema.setVisaHandler(resultSet.getString("VisaHandler"));
          prpJpayRefRecSchema.setPayRefName(resultSet.getString("PayRefName"));
          this.setArr(prpJpayRefRecSchema);
          
        }
        resultSet.close();
        dbpool.close();
        int num = 0;
        String payRefNoTmp = "";
        for(int i = 0; i < this.getSize();i++)
        {
          if(payRefNoTmp.equals(""))
          {
            num++;
            payRefNoTmp = this.getArr(i).getPayRefNo();
          }
          else if(!payRefNoTmp.equals(this.getArr(i).getPayRefNo()))
          {
            for(int j = 0;j<this.getSize();j++)
            {
              if(payRefNoTmp.equals(this.getArr(j).getPayRefNo()))
              {
                this.getArr(j).setSerialNo(Integer.toString(num));
              }
            }
            num = 1;
            payRefNoTmp = this.getArr(i).getPayRefNo();
          }
          else
          {
            num++;
          }
        }
        if(this.getSize()>0)
        {
          for(int j = 0;j<this.getSize();j++)
          {
            if(payRefNoTmp.equals(this.getArr(j).getPayRefNo()))
            {
              this.getArr(j).setSerialNo(Integer.toString(num));
            }
          }
        }
      }
      catch (SQLException sqlException)
      {
        resultSet.close();
        dbpool.close();
        throw sqlException;
      }
      catch (NamingException namingException)
      {
        resultSet.close();
        dbpool.close();
        throw namingException;
      }
      catch (Exception e)
      {
        resultSet.close();
        dbpool.close();
        throw e;
      }
      finally {
        resultSet.close();
        dbpool.close();
      }
    }

    /**
     *发票打包
     *@param String[] payRefRecNo 打包号
     *@throws Exception
     */
    public boolean dePackage(String[] payRefRecNo) throws Exception
    {
      boolean result = true;
      String payRefRecNoCol = "";
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      PrpJpayRefRecSchema prpJpayRefRecSchema = null;
      for (int i = 0; i < payRefRecNo.length; i++)
      {
        payRefRecNoCol = payRefRecNoCol + ",'" + payRefRecNo[i] + "'";
      }
      payRefRecNoCol = "(" + payRefRecNoCol.substring(1) + ")";
      String strSqlStatement =
          "SELECT * FROM PrpJpayRefRec WHERE payRefNo in " + payRefRecNoCol
          + " AND identifytype='0' AND identifynumber='1' ";
      initArr();
      schemas = dbPrpJpayRefRec.findByConditions(strSqlStatement);
      DbPool dbpool = new DbPool();
      
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        dbpool.beginTransaction();
        for (int i = 0; i < this.getSize(); i++)
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema = this.getArr(i);
          dbPrpJpayRefRec.getInfo(dbpool,prpJpayRefRecSchema.getCertiType(),prpJpayRefRecSchema.getCertiNo(),prpJpayRefRecSchema.getSerialNo(),prpJpayRefRecSchema.getPayRefReason(),prpJpayRefRecSchema.getPayRefTimes());
          dbPrpJpayRefRec.setPayRefNo(dbPrpJpayRefRec.getFlag1());
          dbPrpJpayRefRec.setFlag1("");
          dbPrpJpayRefRec.setIdentifyNumber("0");
          dbPrpJpayRefRec.update(dbpool);
          
        }
        String sql = " update prpjpayrefmain set payrefno=payrefnobak,serialno='1',payrefnobak='',IdentifyNumber='0' where payrefno in " + payRefRecNoCol;
        dbpool.prepareInnerStatement(sql);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
        dbpool.commitTransaction();
        dbpool.close();
      }
      catch (UserException userexception)
      {
        dbpool.rollbackTransaction();
        dbpool.close();
        result = false;
        throw userexception;
      }
      catch (SQLException sqlexception)
      {
        dbpool.rollbackTransaction();
        dbpool.close();
        result = false;
        throw sqlexception;
      }
      catch (Exception exception)
      {
        dbpool.rollbackTransaction();
        dbpool.close();
        result = false;
        throw exception;
      }
      finally {
        dbpool.close();
      }
      return result;
    }

    /**
     *发票到款确认查询
     *@param String condition
     *@throws Exception
     */
    public void checkInvioceQuery(String strCondition) throws Exception
    {
      
      
      String strSqlStatement = "SELECT VisaCode,VisaName,visaserialno,Currency2,PrintDate,PrinterCode,"
          + "VisaHandler,PayRefName,CoinsFlag,CoinsType,sum(PayRefFee) as PayRefFee "
          + " FROM PrpJpayRefRec WHERE " + strCondition
          + " Group By VisaCode,VisaName,visaserialno,Currency2,PrintDate,PrinterCode,"
          + "VisaHandler,PayRefName,CoinsFlag,CoinsType";
      DbPool dbpool = new DbPool();
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema.setVisaCode(resultSet.getString("VisaCode"));
          prpJpayRefRecSchema.setVisaName(resultSet.getString("VisaName"));
          prpJpayRefRecSchema.setVisaSerialNo(resultSet.getString("visaserialno"));
          prpJpayRefRecSchema.setCurrency2(resultSet.getString("currency2"));
          prpJpayRefRecSchema.setPrintDate("" + resultSet.getDate("PrintDate"));
          prpJpayRefRecSchema.setPrinterCode(resultSet.getString("PrinterCode"));
          prpJpayRefRecSchema.setVisaHandler(resultSet.getString("VisaHandler"));
          prpJpayRefRecSchema.setPayRefName(resultSet.getString("PayRefName"));
          prpJpayRefRecSchema.setCoinsFlag(resultSet.getString("CoinsFlag"));
          prpJpayRefRecSchema.setCoinsType(resultSet.getString("CoinsType"));
          prpJpayRefRecSchema.setPayRefFee(resultSet.getString("payRefFee"));
          
          this.setArr(prpJpayRefRecSchema);
          
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(NamingException namingException)
      {
        dbpool.close();
        throw namingException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     *支付单合并生成 SangMingqian 20050609
     *modify SangMingqian 20051114 收付处理传参数方式修改
     *@param iPrpJpayRefMainSchema
     *@return strPayRefNo 支付单号
     *@throws Exception
     */
    public String genFeeInvioce(PrpJpayRefMainSchema iPrpJpayRefMainSchema) throws UserException,SQLException,Exception
    {
      BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
      DBPrpJplanFee dbPrpJplanFee = null;
      PrpJplanFeeSchema prpJplanFeeSchema = new PrpJplanFeeSchema();
      String strPayRefNo = "";
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();

      

      
      Bill bill = new Bill();
      String billNo = "";
      int intYear = Integer.parseInt(iPrpJpayRefMainSchema.getPackageDate().substring(0,4));
      billNo = bill.getNo("prpjrefrec","0501",iPrpJpayRefMainSchema.getPackageUnit(),intYear,"00");
      
      if(billNo.equals("")){
      	throw new UserException(-98,-1167,"BLPrpJpayRefRec.genFeeInvioce",
      			                 iPrpJpayRefMainSchema.getPackageUnit().substring(0,4)+ "0501" + intYear + "单号没有初始化，请联系系统管理员！");
      }
      strPayRefNo = this.createPayRefNo(billNo,"0501",iPrpJpayRefMainSchema.getPackageUnit());

      DbPool dbpool = new DbPool();
      
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        dbpool.beginTransaction();
        
        for(int i = 0;i<this.getSize(); i++)
        {
          dbPrpJplanFee = new DBPrpJplanFee();
          
          dbPrpJplanFee.getInfoForUpdate(dbpool, this.getArr(i).getCertiType(),
                                         this.getArr(i).getCertiNo(),
                                         this.getArr(i).getSerialNo(),
                                         this.getArr(i).getPayRefReason());
          
          this.isRealPayRef(dbpool,this.getArr(i));
          
          if(Double.parseDouble(Str.chgStrZero(dbPrpJplanFee.getPayRefFee()))!=0)
          {
            throw new UserException(-96,-1167,"","已经生成支付单了！");
          }
          if(dbPrpJplanFee.getCertiType().equals("F"))
          {
            dbPrpJplanFee.setPlanFeeCNY(this.getArr(i).getPayRefFee());
            dbPrpJplanFee.setExchangeRate(this.getArr(i).getExchangeRate());
          }
          dbPrpJplanFee.setPayRefFee(this.getArr(i).getPayRefFee());
          dbPrpJplanFee.update(dbpool);
          
          this.getArr(i).setToCenterCode(dbPrpJplanFee.getToCenterCode());
          this.getArr(i).setToComCode(dbPrpJplanFee.getToComCode());
          this.getArr(i).setToDesc(dbPrpJplanFee.getToDesc());
          this.getArr(i).setToStatus(dbPrpJplanFee.getToStatus());
          this.getArr(i).setToUserCode(dbPrpJplanFee.getToUserCode());
        }
        
        for(int i=0; i<this.getSize(); i++)
        {
          this.getArr(i).setPayRefNo(strPayRefNo);
          
          this.getArr(i).setIdentifyNumber("1");
          this.getArr(i).setIdentifyType("0");
          this.getArr(i).setFlag("0");
          
          
        }
        this.save(dbpool);
        
        dbPrpJpayRefMain.setSchema(iPrpJpayRefMainSchema);
        
        
        
        if (this.getArr(0).getToStatus().equals("0")||this.getArr(0).getToStatus().equals("")) {
        	dbPrpJpayRefMain.setCenterCode(this.getArr(0).getCenterCode());
        	dbPrpJpayRefMain.setBranchCode("0");
		} else {
			dbPrpJpayRefMain.setCenterCode(this.getArr(0).getToCenterCode());
			dbPrpJpayRefMain.setBranchCode(this.getArr(0).getCenterCode());
		}
        
        dbPrpJpayRefMain.setPayRefNo(strPayRefNo);
        dbPrpJpayRefMain.insert(dbpool);

        
        if(this.getArr(0).getCertiType().endsWith("D")){
        	Visa visa = new Visa();
        	





        }
        dbpool.commitTransaction();
        dbpool.close();
        bill.deleteNo("prpjrefrec",billNo);
        
        

      }
      catch(UserException userexception)
      {
        dbpool.rollbackTransaction();
        bill.putNo("prpjrefrec",billNo);
        throw userexception;
      }
      catch(SQLException sqlexception)
      {
        dbpool.rollbackTransaction();
        bill.putNo("prpjrefrec",billNo);
        throw sqlexception;
      }
      catch(Exception exception)
      {
        dbpool.rollbackTransaction();
        bill.putNo("prpjrefrec",billNo);
        throw exception;
      }
      finally {
        dbpool.close();
      }
      return strPayRefNo;
    }
    
    /**
     *支付单合并生成 zhanglingjian 20071120
     *@param iPrpJpayRefMainSchema
     *@return strPayRefNo 支付单号
     *@throws Exception
     */
    public String genFeeInvioceNew(PrpJpayRefMainSchema iPrpJpayRefMainSchema) throws UserException,SQLException,Exception
    {
      DBPrpJplanFee dbPrpJplanFee = null;
      String strPayRefNo = "";
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();

      
      Bill bill = new Bill();
      String billNo = "";
      int intYear = Integer.parseInt(iPrpJpayRefMainSchema.getPackageDate().substring(0,4));
      billNo = bill.getNo("prpjrefrec","0501",iPrpJpayRefMainSchema.getPackageUnit(),intYear,"00");
      
      if(billNo.equals("")){
      	throw new UserException(-98,-1167,"BLPrpJpayRefRec.genFeeInvioce",
      			                 iPrpJpayRefMainSchema.getPackageUnit().substring(0,4)+ "0501" + intYear + "单号没有初始化，请联系系统管理员！");
      }
      strPayRefNo = this.createPayRefNo(billNo,"0501",iPrpJpayRefMainSchema.getPackageUnit());

      DbPool dbpool = new DbPool();
      
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        dbpool.beginTransaction();
        
        for(int i = 0;i<this.getSize(); i++)
        {
          dbPrpJplanFee = new DBPrpJplanFee();
          
          dbPrpJplanFee.getInfoForUpdate(dbpool, this.getArr(i).getCertiType(),
                                         this.getArr(i).getCertiNo(),
                                         this.getArr(i).getSerialNo(),
                                         this.getArr(i).getPayRefReason());
          
          this.isRealPayRef(dbpool,this.getArr(i));
          
          if(Double.parseDouble(Str.chgStrZero(dbPrpJplanFee.getPayRefFee()))!=0)
          {
            throw new UserException(-96,-1167,"","已经生成支付单了！");
          }
          if(dbPrpJplanFee.getCertiType().equals("F"))
          {
            dbPrpJplanFee.setPlanFeeCNY(this.getArr(i).getPayRefFee());
            dbPrpJplanFee.setExchangeRate(this.getArr(i).getExchangeRate());
          }
          dbPrpJplanFee.setPayRefFee(this.getArr(i).getPayRefFee());
          dbPrpJplanFee.update(dbpool);
        }
        
        for(int i=0; i<this.getSize(); i++)
        {
          this.getArr(i).setPayRefNo(strPayRefNo);
          
          this.getArr(i).setIdentifyNumber("1");
          this.getArr(i).setIdentifyType("0");
          this.getArr(i).setFlag("0");
          
        }
        this.save(dbpool);
        
        dbPrpJpayRefMain.setSchema(iPrpJpayRefMainSchema);
        dbPrpJpayRefMain.setPayRefNo(strPayRefNo);
        dbPrpJpayRefMain.setAgentCode(this.getArr(0).getAgentCode());
        
        dbPrpJpayRefMain.setCenterCode(this.getArr(0).getCenterCode());
        dbPrpJpayRefMain.insert(dbpool);

        dbpool.commitTransaction();
        dbpool.close();
        bill.deleteNo("prpjrefrec",billNo);
      }
      catch(UserException userexception)
      {
        dbpool.rollbackTransaction();
        bill.putNo("prpjrefrec",billNo);
        throw userexception;
      }
      catch(SQLException sqlexception)
      {
        dbpool.rollbackTransaction();
        bill.putNo("prpjrefrec",billNo);
        throw sqlexception;
      }
      catch(Exception exception)
      {
        dbpool.rollbackTransaction();
        bill.putNo("prpjrefrec",billNo);
        throw exception;
      }
      finally {
        dbpool.close();
      }
      return strPayRefNo;
    }

    /**
     *支付单批量生成 SangMingqian 200506016
     *modify SangMingqian 20051114 收付处理传参数方式修改
     *@param arrCertiType
     *@param arrCertiNo
     *@param arrSerialNo
     *@param arrPayRefReason
     *@param iPrpJpayRefRecSchema
     *@throws Exception
     */
    public void genBatchFeeInvioce(String[] arrCertiType,
                                   String[] arrCertiNo,
                                   String[] arrSerialNo,
                                   String[] arrPayRefReason,
                                   PrpJpayRefRecSchema iPrpJpayRefRecSchema) throws UserException,SQLException,Exception
    {
      BLPrpJplanFee blPrpJplanFee = null;
      DBPrpJplanFee dbPrpJplanFee = null;
      PrpJplanFeeSchema prpJplanFeeSchema = new PrpJplanFeeSchema();
      BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
      DBPrpJpayRefMain dbPrpJpayRefMain = null;
      PrpJpayRefMainSchema prpJpayRefMainSchema = null;

      
      String strCondition = "";
      
      Bill bill = new Bill();
      String billNo = "";
      int intYear = Integer.parseInt(iPrpJpayRefRecSchema.getOperateDate().substring(0,4));
      String strPayRefNo = "";

      DbPool dbpool = new DbPool();
      
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        dbpool.beginTransaction();
        for(int i=0; i< arrCertiType.length; i++)
        {
          billNo = bill.getNo("prpjrefrec","0501",iPrpJpayRefRecSchema.getOperateUnit(),intYear,"00");
          strPayRefNo = this.createPayRefNo(billNo,"0501",iPrpJpayRefRecSchema.getOperateUnit());
          String strPayRefName = "";
          String strPayRefNoType = "";
          double dbPayRefFee = 0;

          blPrpJplanFee = new BLPrpJplanFee();
          
          
          
          
          
          
          blPrpJplanFee.getNewInfo(arrCertiType[i],arrCertiNo[i],arrSerialNo[i],arrPayRefReason[i]);
          
          this.setArr(this.genSchema(blPrpJplanFee.getArr(0)));
          this.getArr(i).setOperateDate(iPrpJpayRefRecSchema.getOperateDate());
          this.getArr(i).setOperateUnit(iPrpJpayRefRecSchema.getOperateUnit());
          this.getArr(i).setOperatorCode(iPrpJpayRefRecSchema.getOperatorCode());
          this.getArr(i).setPayRefNo(strPayRefNo);
          
          this.getArr(i).setIdentifyNumber("1");
          this.getArr(i).setIdentifyType("0");
          this.getArr(i).setFlag("0");
          
          this.getArr(i).setCurrency2(iPrpJpayRefRecSchema.getCurrency2());
          this.getArr(i).setExchangeRate(iPrpJpayRefRecSchema.getExchangeRate());
          dbPayRefFee = Double.parseDouble(this.getArr(i).getPlanFee())/Double.parseDouble(iPrpJpayRefRecSchema.getExchangeRate());
          this.getArr(i).setPayRefFee(""+dbPayRefFee);

          if(iPrpJpayRefRecSchema.getCertiType().equals("S"))
          {
            BLPrpDagent blPrpDagent = new BLPrpDagent();
            strPayRefName = blPrpDagent.translateCode(this.getArr(i).getAgentCode(),true);
            
            
            
            strPayRefNoType = "3";
          }
          else
          {
            strPayRefName = this.getArr(i).getInsuredName();
            strPayRefNoType = "4";
          }
          this.getArr(i).setPayRefName(strPayRefName);

          prpJpayRefMainSchema = new PrpJpayRefMainSchema();
          prpJpayRefMainSchema.setPayRefNo(strPayRefNo);
          prpJpayRefMainSchema.setSerialNo("1");
          prpJpayRefMainSchema.setPayRefNoType(strPayRefNoType);
          prpJpayRefMainSchema.setPayRefName(strPayRefName);
          prpJpayRefMainSchema.setPackageCode(iPrpJpayRefRecSchema.getOperatorCode());
          prpJpayRefMainSchema.setPackageUnit(iPrpJpayRefRecSchema.getOperateUnit());
          prpJpayRefMainSchema.setPackageDate(iPrpJpayRefRecSchema.getOperateDate());
          prpJpayRefMainSchema.setCurrency2(iPrpJpayRefRecSchema.getCurrency2());
          prpJpayRefMainSchema.setPayRefFee(""+dbPayRefFee);
          
          prpJpayRefMainSchema.setPrintDate(iPrpJpayRefRecSchema.getOperateDate());
          prpJpayRefMainSchema.setPrinterCode(iPrpJpayRefRecSchema.getOperatorCode());
          prpJpayRefMainSchema.setAgentCode(blPrpJplanFee.getArr(0).getAgentCode());
          
          
          
          if (this.getArr(i).getToStatus().equals("0")||this.getArr(i).getToStatus().equals("")) {
				prpJpayRefMainSchema.setCenterCode(this.getArr(i).getCenterCode());
				prpJpayRefMainSchema.setBranchCode("0");
			} else {
				prpJpayRefMainSchema.setCenterCode(this.getArr(i).getToCenterCode());
				prpJpayRefMainSchema.setBranchCode(this.getArr(i).getCenterCode());
			}
          
          blPrpJpayRefMain.setArr(prpJpayRefMainSchema);

          prpJplanFeeSchema = blPrpJplanFee.getArr(0);
          dbPrpJplanFee = new DBPrpJplanFee();
          dbPrpJplanFee.setSchema(prpJplanFeeSchema);
          
          this.isRealPayRef(dbpool,this.getArr(i));
          
          dbPrpJplanFee.setPayRefFee(this.getArr(i).getPlanFee());
          dbPrpJplanFee.update(dbpool);
          bill.deleteNo("prpjrefrec",billNo);
        }

        blPrpJpayRefMain.save(dbpool);
        this.save(dbpool);

        dbpool.commitTransaction();
        dbpool.close();
      }
      catch(UserException userexception)
      {
        dbpool.rollbackTransaction();
        bill.putNo("prpjrefrec",billNo);
        throw userexception;
      }
      catch(SQLException sqlexception)
      {
        dbpool.rollbackTransaction();
        bill.putNo("prpjrefrec",billNo);
        throw sqlexception;
      }
      catch(Exception exception)
      {
        dbpool.rollbackTransaction();
        bill.putNo("prpjrefrec",billNo);
        throw exception;
      }
      finally {
        dbpool.close();
      }
    }
    /**
     *根据操作序列支付单批量生成 xushaojiang  20070806
     *@param iStrWhere
     *@param iPrpJpayRefRecSchema
     *@throws Exception
     */
    public void genBatchFeeInvioce(String iStrWhere,PrpJpayRefRecSchema iPrpJpayRefRecSchema) throws UserException,SQLException,Exception
    {
      DBPrpJplanFee dbPrpJplanFee = null;
      BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
      PrpJplanFeeSchema prpJplanFeeSchema = new PrpJplanFeeSchema();
      BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
      PrpJpayRefMainSchema prpJpayRefMainSchema = null;
      
      Bill bill = new Bill();
      String billNo = "";
      int intYear = Integer.parseInt(iPrpJpayRefRecSchema.getOperateDate().substring(0,4));
      String strPayRefNo = "";
      DbPool dbpool = new DbPool();
      
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        blPrpJplanFee.query(dbpool, iStrWhere,0);
        dbpool.beginTransaction();
        for(int i=0; i< blPrpJplanFee.getSize(); i++)
        {
          billNo = bill.getNo("prpjrefrec","0501",iPrpJpayRefRecSchema.getOperateUnit(),intYear,"00");
          strPayRefNo = this.createPayRefNo(billNo,"0501",iPrpJpayRefRecSchema.getOperateUnit());
          String strPayRefName = "";
          String strPayRefNoType = "";
          double dbPayRefFee = 0;
          this.setArr(this.genSchema(blPrpJplanFee.getArr(i)));
          this.getArr(i).setOperateDate(iPrpJpayRefRecSchema.getOperateDate());
          this.getArr(i).setOperateUnit(iPrpJpayRefRecSchema.getOperateUnit());
          this.getArr(i).setOperatorCode(iPrpJpayRefRecSchema.getOperatorCode());
          this.getArr(i).setPayRefNo(strPayRefNo);
          this.getArr(i).setIdentifyNumber("1");
          this.getArr(i).setIdentifyType("0");
          this.getArr(i).setFlag("0");  
          this.getArr(i).setCurrency2(iPrpJpayRefRecSchema.getCurrency2());
          this.getArr(i).setExchangeRate(iPrpJpayRefRecSchema.getExchangeRate());
          dbPayRefFee = Double.parseDouble(this.getArr(i).getPlanFee())/Double.parseDouble(iPrpJpayRefRecSchema.getExchangeRate());
          this.getArr(i).setPayRefFee(""+dbPayRefFee);

          if(iPrpJpayRefRecSchema.getCertiType().equals("S"))
          {
            BLPrpDagent blPrpDagent = new BLPrpDagent();
            strPayRefName = blPrpDagent.translateCode(this.getArr(i).getAgentCode(),true);
            strPayRefNoType = "3";
          }
          else
          {
            strPayRefName = this.getArr(i).getInsuredName();
            strPayRefNoType = "4";
          }
          this.getArr(i).setPayRefName(strPayRefName);

          prpJpayRefMainSchema = new PrpJpayRefMainSchema();
          prpJpayRefMainSchema.setPayRefNo(strPayRefNo);
          prpJpayRefMainSchema.setSerialNo("1");
          prpJpayRefMainSchema.setPayRefNoType(strPayRefNoType);
          prpJpayRefMainSchema.setPayRefName(strPayRefName);
          prpJpayRefMainSchema.setPackageCode(iPrpJpayRefRecSchema.getOperatorCode());
          prpJpayRefMainSchema.setPackageUnit(iPrpJpayRefRecSchema.getOperateUnit());
          prpJpayRefMainSchema.setPackageDate(iPrpJpayRefRecSchema.getOperateDate());
          prpJpayRefMainSchema.setCurrency2(iPrpJpayRefRecSchema.getCurrency2());
          prpJpayRefMainSchema.setPayRefFee(""+dbPayRefFee);
          
          prpJpayRefMainSchema.setPrintDate(iPrpJpayRefRecSchema.getOperateDate());
          prpJpayRefMainSchema.setPrinterCode(iPrpJpayRefRecSchema.getOperatorCode());
          
          
          
          if (this.getArr(i).getToStatus().equals("0")||this.getArr(i).getToStatus().equals("")) {
				prpJpayRefMainSchema.setCenterCode(this.getArr(i).getCenterCode());
				prpJpayRefMainSchema.setBranchCode("0");
			} else {
				prpJpayRefMainSchema.setCenterCode(this.getArr(i).getToCenterCode());
				prpJpayRefMainSchema.setBranchCode(this.getArr(i).getCenterCode());
			}
          
          blPrpJpayRefMain.setArr(prpJpayRefMainSchema);

          prpJplanFeeSchema = blPrpJplanFee.getArr(i);
          dbPrpJplanFee = new DBPrpJplanFee();
          dbPrpJplanFee.setSchema(prpJplanFeeSchema);
          this.isRealPayRef(dbpool,this.getArr(i));
          dbPrpJplanFee.setPayRefFee(this.getArr(i).getPlanFee());
          dbPrpJplanFee.update(dbpool);
          bill.deleteNo("prpjrefrec",billNo);
        }

        blPrpJpayRefMain.save(dbpool);
        this.save(dbpool);

        dbpool.commitTransaction();
        dbpool.close();
      }
      catch(UserException userexception)
      {
        dbpool.rollbackTransaction();
        bill.putNo("prpjrefrec",billNo);
        throw userexception;
      }
      catch(SQLException sqlexception)
      {
        dbpool.rollbackTransaction();
        bill.putNo("prpjrefrec",billNo);
        throw sqlexception;
      }
      catch(Exception exception)
      {
        dbpool.rollbackTransaction();
        bill.putNo("prpjrefrec",billNo);
        throw exception;
      }
      finally {
        dbpool.close();
      }
    }
    /**
     * @author lijibin 20050609 生成支付单号
     * @return strPayRefNo 支付单号
     * @throws Exception
     */
    public String createPayRefNo(String iBillNo,String iRiskCode,String iComCode) throws Exception
    {
      
      String strPayRefNo = iBillNo.replaceAll(iComCode+iRiskCode,iComCode);
      return strPayRefNo;
    }

    /**
     *支付单作废 SangMingqian 20050610
     *@param String[] visaserialno 发票号
     *@throws Exception
     */
    public boolean dropPayvisa(String[] PayRefNo) throws Exception
    {
      boolean result = true;
      String payRefNoCollection = "";
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      PrpJpayRefRecSchema prpJpayRefRecSchema = null;

      BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      PrpJpayRefMainSchema prpJpayRefMainSchema = null;

      BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();

      for (int i = 0; i < PayRefNo.length; i++)
      {
        payRefNoCollection = payRefNoCollection + ",'" + PayRefNo[i] + "'";
      }
      payRefNoCollection = "(" + payRefNoCollection.substring(1) + ")";
      String strSqlStatement =
          "SELECT * FROM PrpJpayRefRec WHERE PayRefNo in " + payRefNoCollection;
      initArr();
      schemas = dbPrpJpayRefRec.findByConditions(strSqlStatement);

      String strWherePart = "PayRefNo in" + payRefNoCollection;
      blPrpJpayRefMain.query(strWherePart,0);

      DbPool dbpool = new DbPool();
      
      try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
        dbpool.beginTransaction();
        for (int i = 0; i < this.getSize(); i++)
        {
          prpJpayRefRecSchema = new PrpJpayRefRecSchema();
          prpJpayRefRecSchema = this.getArr(i);

          dbPrpJplanFee.getInfo(dbpool,prpJpayRefRecSchema.getCertiType(),prpJpayRefRecSchema.getCertiNo(),prpJpayRefRecSchema.getSerialNo(),prpJpayRefRecSchema.getPayRefReason());
          dbPrpJplanFee.setPayRefFee("0");
          dbPrpJplanFee.update();

          dbPrpJpayRefRec.delete(dbpool,prpJpayRefRecSchema.getCertiType(),prpJpayRefRecSchema.getCertiNo(),prpJpayRefRecSchema.getSerialNo(),prpJpayRefRecSchema.getPayRefReason(),prpJpayRefRecSchema.getPayRefTimes());
        }
        for(int i=0;i<blPrpJpayRefMain.getSize();i++)
        {
          prpJpayRefMainSchema = new PrpJpayRefMainSchema();
          prpJpayRefMainSchema = blPrpJpayRefMain.getArr(i);
          dbPrpJpayRefMain.delete(dbpool,prpJpayRefMainSchema.getPayRefNo(),prpJpayRefMainSchema.getSerialNo());
        }
        
        dbpool.commitTransaction();
        dbpool.close();
      }
      catch (UserException userexception)
      {
        result = false;
        dbpool.rollbackTransaction();
        dbpool.close();
        throw userexception;
      }
      catch (SQLException sqlexception)
      {
        result = false;
        dbpool.rollbackTransaction();
        dbpool.close();
        throw sqlexception;
      }
      catch (Exception exception)
      {
        result = false;
        dbpool.rollbackTransaction();
        dbpool.close();
        throw exception;
      }
      finally {
        dbpool.close();
      }
      return result;
    }

    /**
     * @author lijibin 20050630 判断XX是否实际收款
     * @param dbpool
     * @param iSchema
     * @throws UserException
     * @throws Exception
     */
    public void isRealPayRef(DbPool dbpool,PrpJpayRefRecSchema iSchema) throws UserException,Exception
    {
      double dblRealPayRefFee = 0;
      String strSQL = "";
      
      if(!iSchema.getCertiType().equals("E")&&Double.parseDouble(iSchema.getPlanFee())<0)
          return;
      if(iSchema.getCertiType().equals("S"))
      
      
    	  
        strSQL =
            "SELECT SUM(RealPayRefFee) FROM PrpJplanFee WHERE CertiType in ('P','E') AND PolicyNo='" +
            iSchema.getPolicyNo() + "' AND PayNo='" + iSchema.getPayNo() + 
           
            "' AND PayRefReason not in('R72','R73','R74')";
      else if (iSchema.getCertiType().equals("E"))
        strSQL = "SELECT SUM(RealPayRefFee) FROM PrpJplanFee WHERE CertiType in ('P','E') AND PolicyNo='" +
            iSchema.getPolicyNo() +"' AND PayNo='" + iSchema.getPayNo()+ "' ";
      else 
        strSQL = "SELECT SUM(RealPayRefFee) FROM PrpJplanFee WHERE CertiType in ('P','E') AND PolicyNo='" +
            iSchema.getPolicyNo() + "' AND PolicyNo='" +
            iSchema.getCertiNo()+ "'";

      ResultSet rs = dbpool.query(strSQL);
      if(rs.next())
        dblRealPayRefFee = rs.getDouble(1);
      rs.close();
      if(dblRealPayRefFee==0)
      {
        
        
        
        
        logger.info("XX" + iSchema.getPolicyNo() + "未做收付款确认！允许赔付");
        
      }
      
      if(dblRealPayRefFee <Math.abs(Double.parseDouble(iSchema.getPlanFee())))
      {
        String strMessage = "";
        if(iSchema.getCertiType().equals("E"))
        {
          strMessage = "实际收款("+dblRealPayRefFee+")小于本次退费!";
          throw new UserException( -96, -1167,
                                  "BLPrpJpayRefRec.genBatchInvoice()",
                                  "XX" + iSchema.getPolicyNo() + strMessage );
        }
        
        
        else if(iSchema.getCertiType().equals("S")&&!iSchema.getComCode().substring(0,2).equals("10"))
        {
        	
        	
        	strSQL = "select 1 from prpjpayrefrec where policyno='"+iSchema.getPolicyNo()+"' and "
        	+" certitype ='E' and payrefreason ='P30'";	
        	ResultSet temprs = dbpool.query(strSQL);
        	
          if (!temprs.next())
          {  
        	  temprs.close(); 
        	strMessage = "实际收款("+dblRealPayRefFee+")小于本次支付手续费！";
          throw new UserException( -96, -1167,
                                  "BLPrpJpayRefRec.genInvoice()",
                                 "XX" + iSchema.getPolicyNo() + strMessage );
          }
          else
          {
        	 temprs.close();
          }
        }
        
        
        
        
        
        
        
      }

    }

    /**
     *得到一条PrpJpayRefRecSchema记录
     *@param index 下标
     *@return 一个PrpJpayRefRecSchema对象
     *@throws Exception
     */
    public PrpJpayRefRecSchema evaluate(PrpJplanFeeSchema iSchema) throws Exception
    {
      PrpJpayRefRecSchema prpJpayRefRecSchema = new PrpJpayRefRecSchema();
      prpJpayRefRecSchema.setCertiType(iSchema.getCertiType());
      prpJpayRefRecSchema.setCertiNo(iSchema.getCertiNo());
      prpJpayRefRecSchema.setPolicyNo(iSchema.getPolicyNo());
      prpJpayRefRecSchema.setSerialNo(iSchema.getSerialNo());
      prpJpayRefRecSchema.setPayRefReason(iSchema.getPayRefReason());
      prpJpayRefRecSchema.setPayRefTimes("1"); 
      prpJpayRefRecSchema.setClaimNo(iSchema.getClaimNo());
      prpJpayRefRecSchema.setClassCode(iSchema.getClassCode());
      prpJpayRefRecSchema.setRiskCode(iSchema.getRiskCode());
      prpJpayRefRecSchema.setContractNo(iSchema.getContractNo());
      prpJpayRefRecSchema.setAppliCode(iSchema.getAppliCode());
      prpJpayRefRecSchema.setAppliName(iSchema.getAppliName());
      prpJpayRefRecSchema.setInsuredCode(iSchema.getInsuredCode());
      prpJpayRefRecSchema.setInsuredName(iSchema.getInsuredName());
      prpJpayRefRecSchema.setStartDate(iSchema.getStartDate());
      prpJpayRefRecSchema.setEndDate(iSchema.getEndDate());
      prpJpayRefRecSchema.setValidDate(iSchema.getValidDate());
      prpJpayRefRecSchema.setPayNo(iSchema.getPayNo());
      prpJpayRefRecSchema.setCurrency1(iSchema.getCurrency1());
      prpJpayRefRecSchema.setPlanFee(iSchema.getPlanFee());
      prpJpayRefRecSchema.setPlanDate(iSchema.getPlanDate());
      prpJpayRefRecSchema.setComCode(iSchema.getComCode());
      prpJpayRefRecSchema.setMakeCom(iSchema.getMakeCom());
      prpJpayRefRecSchema.setAgentCode(iSchema.getAgentCode());
      prpJpayRefRecSchema.setHandler1Code(iSchema.getHandler1Code());
      prpJpayRefRecSchema.setHandlerCode(iSchema.getHandlerCode());
      prpJpayRefRecSchema.setUnderWriteDate(iSchema.getUnderWriteDate());
      prpJpayRefRecSchema.setCoinsFlag(iSchema.getCoinsFlag());
      prpJpayRefRecSchema.setCoinsCode(iSchema.getCoinsCode());
      prpJpayRefRecSchema.setCoinsName(iSchema.getCoinsName());
      prpJpayRefRecSchema.setCoinsType(iSchema.getCoinsType());
      prpJpayRefRecSchema.setPayRefDate(iSchema.getUnderWriteDate());
      prpJpayRefRecSchema.setCarNatureCode(iSchema.getCarNatureCode());
      prpJpayRefRecSchema.setUseNatureCode(iSchema.getUseNatureCode());
      prpJpayRefRecSchema.setCarProperty(iSchema.getCarProperty());
      return prpJpayRefRecSchema;
    }
    
    
    /**
     * @author zhanglingjian 20070718
     * @param BLPrpJplanFee
     * @param isChinese
     * @return BLPrpJplanFee
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
     * @param BLPrpJplanFee
     * @param isChinese
     * @return BLPrpJplanFee
     * @throws Exception
     */
    public void translateCode(DbPool dbpool,boolean isChinese) throws Exception{
        for(int i = 0;i < this.getSize();i++)
        {
        	
        	this.getArr(i).setAgentName(this.translateCode(dbpool,
        			this.getArr(i).getAgentCode(),isChinese,1));
        	
        	this.getArr(i).setHandler1Name(this.translateCode(dbpool,
        			this.getArr(i).getHandler1Code(),isChinese,2));
        	
        	this.getArr(i).setPayRefReasonName(this.translateCode(dbpool,
        			this.getArr(i).getPayRefReason(),isChinese,3));
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
     * @author zhanglingjian 20070718
     * @param 
     * @param payRefNo
     * @return BLPrpJplanFee
     * @throws Exception
     */
    public void translateCode(String payRefNo) throws Exception{
        DbPool dbpool = new DbPool();
        
        String strDataSource =SysConfig.CONST_PAYMENTDATASOURCE;
        
        try{
            
            dbpool.open(strDataSource);
            translateCode(dbpool,payRefNo);
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
     * @param 
     * @param payRefNo
     * @return BLPrpJplanFee
     * @throws Exception
     */
    public void translateCode(DbPool dbpool,String payRefNo) throws Exception{
    	String sql = " payrefno='" + payRefNo + "' ";
    	this.query(dbpool,sql,0);
        for(int i = 0;i < this.getSize();i++)
        {
        	
        	DBPrpDrisk dbPrpDrisk = new DBPrpDrisk();
        	dbPrpDrisk.getInfo(dbpool,this.getArr(i).getRiskCode());
        	this.getArr(i).setRiskCodeName(dbPrpDrisk.getRiskCName());
        	
        	DBPrpCommission dbPrpCommission = new DBPrpCommission();
            dbPrpCommission.getInfo(dbpool,this.getArr(i).getCertiNo());
        	this.getArr(i).setDisRate(dbPrpCommission.getDisRate());
        }
    }


    public void transCode(){
    	PaymentTransCode paymentTransCode = new PaymentTransCode();
    	try {
    		paymentTransCode.transCode("prpduser","prpjpayrefrec",this.schemas);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
  
    public String validVisaSerialNo(String iVisaSerialNo)
	throws Exception{
		String visaSerialNo = "";
		if(iVisaSerialNo.length()>Integer.parseInt(SysConstConfig.VISALENGTH)){
			throw new UserException(-98,-1200,this.getClass().getName(),"您录入的流水号长度大于流水号规定长度:"+SysConstConfig.VISALENGTH+"位，请重新录入！");
		}
		visaSerialNo = UIFormatAction.formatSerialNo(Long.parseLong(iVisaSerialNo));
		return visaSerialNo;
	}
    
    
    public String checkVisaDoUsed(String visaCode,String visaSerialNo,String operatorCode)	throws Exception {
    	HashMap VisaKindCodeNameMap = new HashMap();
    	VisaKindCodeNameMap.put("P", "XX");
    	VisaKindCodeNameMap.put("E", "批单");
    	VisaKindCodeNameMap.put("R", "发票");
    	DBManager dbManager = new DBManager();
    	String flag = "1";
    	try {
  			dbManager.open(SysConstConfig.CONST_VISANEWDATASOURCE);
  			VsCodeDto vsCodeDto = new VsCodeDto();
  			DBVsCode dbVsCode = new DBVsCode(dbManager);
  			DBVsMark dbVsMark = new DBVsMark(dbManager);
  			
  			vsCodeDto = dbVsCode.findByPrimaryKey(visaCode);
  			if (vsCodeDto==null || vsCodeDto.getValidStatus()==null || !vsCodeDto.getValidStatus().equals("1")) {
  			    flag = "单证类型"+visaCode+"在单证代码表中不存在或已经注销，请核对录入的数据！";
  			    return flag;
  			}
  			String visaPreName = (String)VisaKindCodeNameMap.get(vsCodeDto.getVisaPre());
  			if(visaPreName==null||visaPreName.equals("")){
  				visaPreName = "单证";
  			}
  			
  			visaSerialNo = validVisaSerialNo(visaSerialNo);
  			DBPrpDuser dbPrpDuser = new DBPrpDuser();
  			dbPrpDuser.getInfo(operatorCode);
  			String comCode = dbPrpDuser.getComCode();
  			VsMarkDto vsMarkDto = dbVsMark.findByPrimaryKey(visaSerialNo, visaCode);
  			if (vsMarkDto == null){
  				flag = "类型："+visaCode+"，流水号："+visaSerialNo+"的"+visaPreName+"在单证使用明细表（VsMark）中不存在，请核对录入的数据！";
  				return flag;
  			}
  			
  			if(vsMarkDto.getVisaStatus() == null || "".equals(vsMarkDto.getVisaStatus().trim()))
  			{
  			    flag = "类型："+visaCode+"，流水号："+visaSerialNo+"的"+visaPreName+"状态为空，不能使用!";
  			    return flag;
  			}
  			if (!"03".equals(vsMarkDto.getVisaStatus()))
  			{
  			    flag = "类型："+visaCode+"，流水号："+visaSerialNo+"的"+visaPreName+"状态为不可使用，请核对录入的数据！";
  			    return flag;
  			}
  			if (!vsMarkDto.getUserCode().equals(operatorCode)
  	            && !vsMarkDto.getUserCode().equals(comCode)
  	            && !vsMarkDto.getOperateCode().equals(operatorCode)){
  				flag = "类型：" + visaCode + "，流水号：" + visaSerialNo + "的"	+ visaPreName + "归属" + vsMarkDto.getUserName()	+ "，您无权进行核销";
  				return flag;
  			}
  		}catch (Exception ex) {
  			flag = "0";
  		}finally{
  			dbManager.close();
  		}
		
		return flag;
	}
    
    /**
     * @description:定额发票销号
     * @param arrCertiType
     * @param arrCertiNo
     * @param arrSerialNo
     * @param arrPayRefReason
     * @param iSchemas
     * @param listInvoiceNo
     * @param QuotaInvoiceCount
     * @param strInvoiceFee
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     * @author genghaijun-wb
     */
    public String genQuotaInvoice(String[] arrCertiType, String[] arrCertiNo, String[] arrSerialNo,
            String[] arrPayRefReason, ArrayList iSchemas, ArrayList listInvoiceNo, int quotaInvoiceCount,
            String strInvoiceFee) throws UserException, SQLException, Exception {
        DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
            dbpool.beginTransaction();
            String strpayrefNo = genQuotaInvoice(dbpool, arrCertiType, arrCertiNo, arrSerialNo, arrPayRefReason, iSchemas, listInvoiceNo,
                    quotaInvoiceCount, strInvoiceFee);
            dbpool.commitTransaction();
            return strpayrefNo;
        } catch (UserException userexception) {
            dbpool.rollbackTransaction();
            throw userexception;
        } catch (SQLException sqlexception) {
            dbpool.rollbackTransaction();
            throw sqlexception;
        } catch (Exception exception) {
            dbpool.rollbackTransaction();
            throw exception;
        } finally {
            dbpool.close();
        }
    }
    /**
     * @description:定额发票销号
     * @param dbpool
     * @param arrCertiType
     * @param arrCertiNo
     * @param arrSerialNo
     * @param arrPayRefReason
     * @param iSchemas
     * @param listInvoiceNo
     * @param QuotaInvoiceCount
     * @param strInvoiceFee
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     * @author genghaijun-wb
     */
    public String genQuotaInvoice(DbPool dbpool, String[] arrCertiType, String[] arrCertiNo, String[] arrSerialNo,
            String[] arrPayRefReason, ArrayList iSchemas, ArrayList listInvoiceNo, int quotaInvoiceCount,
            String strInvoiceFee) throws UserException, SQLException, Exception {
        BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
        DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();

        
        DBPrpJplanPrint dbPrpJplanPrint = null;
        

        Visa visa = new Visa();
        PrpJpayRefRecSchema prpJpayRefRecSchema = (PrpJpayRefRecSchema) iSchemas.get(0);
        
        String iSerialNo = "";
        if (prpJpayRefRecSchema.getVisaSerialNo().length() < 10) {
            iSerialNo = visa.getSerailNo(prpJpayRefRecSchema.getVisaSerialNo());
            prpJpayRefRecSchema.setVisaSerialNo(iSerialNo);
        }
        
        String strCondition = "";
        
        
        String TPFlag = prpJpayRefRecSchema.getIdentifyNumber();
        
        prpJpayRefRecSchema.setIdentifyNumber("0");
        
        
        double dblPayRefFee = 0;
        double payRefFee = 0;
        double dbCurrency3Fee = 0;
        BLPrpJpayWVisaTrace blPrpJpayWVisaTrace = null;
        BLPrpJQuotaInvoice blPrpJQuotaInvoice = new BLPrpJQuotaInvoice();

        
        String strPayRefFee_Invoice_Switch = "";
        String strCenterCode = "00000000";
        try {
        	com.sp.payment.utility.PaymentTransCode paymentTransCode = new com.sp.payment.utility.PaymentTransCode();
            if(!(prpJpayRefRecSchema.getCenterCode() == null || "".equals(prpJpayRefRecSchema.getCenterCode()))){
            	strCenterCode = prpJpayRefRecSchema.getCenterCode();
            }
            strPayRefFee_Invoice_Switch = paymentTransCode.getConfigValueByComCode(strCenterCode, "0000", "PayRefFee_Invoice_Switch"); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        try {
            
            DBPrpJpayRefVoucher dBPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
            String strPayRefNo = dBPrpJpayRefVoucher.genRealPayRefNo();

            for (int i = 0; i < arrCertiNo.length; i++) {
            	
                
                
               
                blPrpJplanFee.getNewInfo(arrCertiType[i],arrCertiNo[i],arrSerialNo[i],arrPayRefReason[i]);
                
                this.setArr(this.genSchema(blPrpJplanFee.getArr(0)));
            }
            












            
            
            
            for (int i = 0; i < arrCertiNo.length; i++) {
            	
                
                
                
                
                
                blPrpJplanFee.getNewInfo(arrCertiType[i],arrCertiNo[i],arrSerialNo[i],arrPayRefReason[i]);
                
                
                String switch_PRINT_INVOICES =SysConfig.getProperty("SWITCH_PRINT_INVOICES");
                if ("0".equals(switch_PRINT_INVOICES)){
                	if("0".equals(blPrpJplanFee.getArr(0).getRealPayRefFee())&&"0".equals(blPrpJplanFee.getArr(0).getPoaType())){
                		if("1".equals(blPrpJplanFee.getArr(0).getApproveStatus())){
                		
                		}else{
                			throw new UserException( -96, -1167, "BLPrpJplanFee.genInvoice", blPrpJplanFee.getArr(0).getCertiNo()+"XX未实收数据,当" +
                				"为预借发票且审核状态为审核通过时，才允许XX发票打印！");
                		}
                	}
                }else if("1".equals(switch_PRINT_INVOICES)){
                	BLPrpJpayRefRec blPrpJpayRefRecNew01 = new BLPrpJpayRefRec();
                    if("0".equals(blPrpJplanFee.getArr(0).getPoaType())){
                    	String iWherePart = " certitype = '"+blPrpJplanFee.getArr(0).getCertiType()+"' and certino = '"+blPrpJplanFee.getArr(0).getCertiNo()+"' " +
        				"and payrefreason = '"+blPrpJplanFee.getArr(0).getPayRefReason()+"' and serialno = '"+blPrpJplanFee.getArr(0).getSerialNo()+"' and payreftimes = 1";
                    	blPrpJpayRefRecNew01.query(iWherePart,0);
    		    		if(blPrpJpayRefRecNew01.getArr(0).getVoucherNo() != null && blPrpJpayRefRecNew01.getArr(0).getVoucherNo().length() > 0){
    		    			continue;
    		    		}else{
    		            	if("1".equals(blPrpJplanFee.getArr(0).getApproveStatus())){
    		            		
    		            	}else{
    		            		throw new UserException( -96, -1167, "BLPrpJplanFee.genInvoice", blPrpJplanFee.getArr(0).getCertiNo()+"XX未实收数据,当" +
    		            				"为预借发票且审核状态为审核通过时，才允许XX发票打印！");
    		            	}
    		    		}
                    }
                }
              
            }
            if (strPayRefFee_Invoice_Switch.length() >= 8) {
            	
            	String strpayrefNo = genQuotaInvoiceNew(dbpool,iSchemas, listInvoiceNo, quotaInvoiceCount,
                      strInvoiceFee,prpJpayRefRecSchema);
                return strpayrefNo;
            }
            
            
            for (int i = 0; i < this.getSize(); i++) {
                this.getArr(i).setOperateDate(prpJpayRefRecSchema.getOperateDate());
                this.getArr(i).setOperatorCode(prpJpayRefRecSchema.getOperatorCode());
                this.getArr(i).setOperateUnit(prpJpayRefRecSchema.getOperateUnit());
                this.getArr(i).setCurrency2(prpJpayRefRecSchema.getCurrency2());
                double dblSumPayRefFee = 0;
                for (int j = 0; j < iSchemas.size(); j++) {
                    PrpJpayRefRecSchema prpJpayRefRecSchema1 = (PrpJpayRefRecSchema) iSchemas.get(j);
                    dblSumPayRefFee += Double.parseDouble(prpJpayRefRecSchema1.getPayRefFee());
                    if (this.getArr(i).getCurrency1().equals(prpJpayRefRecSchema1.getCurrency1())) {
                        this.getArr(i).setExchangeRate(prpJpayRefRecSchema1.getExchangeRate());
                        dblPayRefFee = Double.parseDouble(this.getArr(i).getPlanFee())
                                * Double.parseDouble(prpJpayRefRecSchema1.getExchangeRate());
                    }
                    
                    if ("EUR".equals(prpJpayRefRecSchema1.getCurrency3())) {
                        this.getArr(i).setExchangeRate3(prpJpayRefRecSchema1.getExchangeRate3());
                        this.getArr(i).setExchangeRate(prpJpayRefRecSchema1.getExchangeRate());
                        dbCurrency3Fee = Double.parseDouble(this.getArr(i).getPlanFee())
                                * Double.parseDouble(prpJpayRefRecSchema1.getExchangeRate3());
                        dbCurrency3Fee = Str.round(Str.round(dbCurrency3Fee, 8), 2);
                        dblPayRefFee = dbCurrency3Fee * Double.parseDouble(prpJpayRefRecSchema1.getExchangeRate());
                    }
                    
                }
                if (i == this.getSize() - 1) {
                    dblPayRefFee = dblSumPayRefFee - payRefFee;
                    
                    if (!"EUR".equals(prpJpayRefRecSchema.getCurrency3())) {
                        if (Math.abs(dblPayRefFee - Double.parseDouble(this.getArr(i).getPlanFee())
                                * Double.parseDouble(this.getArr(i).getExchangeRate())) > 0.1) {
                            logger.info(this.getArr(i).getCertiNo() + "--发票金额错误1--planfee="
                                    + this.getArr(i).getPlanFee() + "--dblPayRefFee=" + dblPayRefFee);
                            throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", this.getArr(i).getCertiNo()
                                    + "发票金额不正确，不能进行发票打印！");
                        }
                    }
                }

                dblPayRefFee = Str.round(Str.round(dblPayRefFee, 8), 2);

                
                if (0 == dblPayRefFee) {
                    throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", this.getArr(i).getCertiNo()
                            + "发票金额为0，不能进行发票打印！");
                }
                this.getArr(i).setPayRefFee("" + dblPayRefFee);
                payRefFee = payRefFee + dblPayRefFee;
                this.getArr(i).setVisaCode(prpJpayRefRecSchema.getVisaCode());
                this.getArr(i).setVisaName(prpJpayRefRecSchema.getVisaName());
                this.getArr(i).setVisaSerialNo(strPayRefNo);
                this.getArr(i).setWVisaCode(prpJpayRefRecSchema.getWVisaCode());
                this.getArr(i).setWVisaSerialNo(prpJpayRefRecSchema.getWVisaSerialNo());
                this.getArr(i).setPrintDate(prpJpayRefRecSchema.getPrintDate());
                this.getArr(i).setPrinterCode(prpJpayRefRecSchema.getPrinterCode());
                this.getArr(i).setVisaHandler(prpJpayRefRecSchema.getVisaHandler());
                this.getArr(i).setPayRefName(prpJpayRefRecSchema.getPayRefName());
                this.getArr(i).setIdentifyType(prpJpayRefRecSchema.getIdentifyType());
                this.getArr(i).setIdentifyNumber(prpJpayRefRecSchema.getIdentifyNumber());
                this.getArr(i).setRemark(prpJpayRefRecSchema.getRemark());
                this.getArr(i).setCurrency3(prpJpayRefRecSchema.getCurrency3());
                this.getArr(i).setCurrency3Fee("" + dbCurrency3Fee);
                this.getArr(i).setFlag("0");
                
                this.getArr(i).setIdentifyType("0");
                this.getArr(i).setPayRefNo(strPayRefNo);
                
                dbPrpJplanPrint = new DBPrpJplanPrint();
                dbPrpJplanPrint.delete(dbpool, this.getArr(i).getCertiType(), this.getArr(i).getCertiNo(), this.getArr(
                        i).getSerialNo(), this.getArr(i).getPayRefReason());
                
                
                if (0 == dblPayRefFee) {
                    
                    
                    
                    this.remove(i);
                    i--;
                    
                }
                

                
                
                double size = PubTools.div(Double.parseDouble(this.getArr(i).getPlanFee()), Double
                        .parseDouble(strInvoiceFee), 1);
               
                 
                for (int k = 0; k < size; k++) 
                {
                    PrpJQuotaInvoiceSchema prpJQuotaInvoiceSchema = new PrpJQuotaInvoiceSchema();
                    prpJQuotaInvoiceSchema.setPayrefNo(strPayRefNo);
                    prpJQuotaInvoiceSchema.setVisaSerialNo(listInvoiceNo.get(--quotaInvoiceCount).toString());
                    prpJQuotaInvoiceSchema.setVisaCode(prpJpayRefRecSchema.getVisaCode());
                    prpJQuotaInvoiceSchema.setOperateCode(prpJpayRefRecSchema.getOperatorCode());
                    prpJQuotaInvoiceSchema.setOperateDate(prpJpayRefRecSchema.getOperateDate());
                    prpJQuotaInvoiceSchema.setCurrency("CNY");
                    prpJQuotaInvoiceSchema.setVISAName(prpJpayRefRecSchema.getVisaName());
                    prpJQuotaInvoiceSchema.setInvoiceFee(strInvoiceFee);
                    prpJQuotaInvoiceSchema.setFlag("");
                    prpJQuotaInvoiceSchema.setRemark(prpJpayRefRecSchema.getRemark());
                    prpJQuotaInvoiceSchema.setPayrefName(prpJpayRefRecSchema.getPayRefName());
                    prpJQuotaInvoiceSchema.setPolicyNo(this.getArr(i).getPolicyNo());
                    prpJQuotaInvoiceSchema.setCertiType(this.getArr(i).getCertiType());
                    prpJQuotaInvoiceSchema.setRiskCode(this.getArr(i).getRiskCode());
                    prpJQuotaInvoiceSchema.setCenterCode(this.getArr(i).getCenterCode());
                    prpJQuotaInvoiceSchema.setPayRefdate("");
                    prpJQuotaInvoiceSchema.setComCode(this.getArr(i).getComCode());
                    prpJQuotaInvoiceSchema.setCertiNo(this.getArr(i).getCertiNo());

                    blPrpJQuotaInvoice.setArr(prpJQuotaInvoiceSchema);
                    
                }
            }
            blPrpJQuotaInvoice.save(dbpool);
            if (this.getSize() == 0) {
                throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", "发票金额为0，不能进行发票打印！");
            }
            
            if (prpJpayRefRecSchema.getComCode().length() > 2
                    && "08".equals(prpJpayRefRecSchema.getComCode().substring(0, 2))) {
                blPrpJpayWVisaTrace = new BLPrpJpayWVisaTrace();
                blPrpJpayWVisaTrace.traceVisa(this.schemas, "refrec", "01");
            }
            











            for (int i = 0; i < this.getSize(); i++) {
                dbPrpJplanFee = new DBPrpJplanFee();
                dbPrpJplanFee.getInfoForUpdate(dbpool, this.getArr(i).getCertiType(), this.getArr(i).getCertiNo(), this
                        .getArr(i).getSerialNo(), this.getArr(i).getPayRefReason());
                
                if (Double.parseDouble(Str.chgStrZero(dbPrpJplanFee.getPayRefFee())) != 0) {
                    throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", "已经打过发票，不能再打！");
                }
                dbPrpJplanFee.setPayRefFee(this.getArr(i).getPlanFee());
                dbPrpJplanFee.setInvPrintFlag("1");
                dbPrpJplanFee.update(dbpool);
            }
            
            BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
            if (this.getSize() != 0) {
                PrpJpayRefMainSchema prpJpayRefMainSchema = new PrpJpayRefMainSchema();
                prpJpayRefMainSchema.setPayRefNo(strPayRefNo);
                prpJpayRefMainSchema.setSerialNo("1");
                prpJpayRefMainSchema.setPayRefNoType("2");
                prpJpayRefMainSchema.setVisaCode(prpJpayRefRecSchema.getVisaCode());
                prpJpayRefMainSchema.setVisaSerialNo(strPayRefNo);
                prpJpayRefMainSchema.setPrintDate(prpJpayRefRecSchema.getPrintDate());
                prpJpayRefMainSchema.setPrinterCode(prpJpayRefRecSchema.getPrinterCode());
                prpJpayRefMainSchema.setVisaHandler(prpJpayRefRecSchema.getVisaHandler());
                prpJpayRefMainSchema.setPayRefName(prpJpayRefRecSchema.getPayRefName());
                prpJpayRefMainSchema.setIdentifyType("0");
                prpJpayRefMainSchema.setIdentifyNumber("0");
                prpJpayRefMainSchema.setCurrency2(prpJpayRefRecSchema.getCurrency2());
                prpJpayRefMainSchema.setCurrency3(prpJpayRefRecSchema.getCurrency3());
                prpJpayRefMainSchema.setRemark(prpJpayRefRecSchema.getRemark());

                
                if (this.getArr(0).getToStatus().equals("0") || this.getArr(0).getToStatus().equals("")) {
                    prpJpayRefMainSchema.setCenterCode(this.getArr(0).getCenterCode());
                    prpJpayRefMainSchema.setBranchCode("0");
                } else {
                    prpJpayRefMainSchema.setCenterCode(this.getArr(0).getToCenterCode());
                    prpJpayRefMainSchema.setBranchCode(this.getArr(0).getCenterCode());
                }
                
                
                if (prpJpayRefRecSchema.getComCode().length() > 2
                        && "03".equals(prpJpayRefRecSchema.getComCode().substring(0, 2))) {
                    prpJpayRefMainSchema.setCheckCode(prpJpayRefRecSchema.getFlag3());
                }
                prpJpayRefMainSchema.setPayRefFee("" + payRefFee);
                blPrpJpayRefMain.setArr(prpJpayRefMainSchema);
            }
            blPrpJpayRefMain.save(dbpool);
            this.save(dbpool);
            
            if (TPFlag == null || TPFlag.equals("") || !TPFlag.equals("1")) {
            	for (int j = 0; j < blPrpJQuotaInvoice.getSize(); j++) {
            		visa.useTrans(dbpool, blPrpJQuotaInvoice.getArr(j).getOperateCode(), blPrpJQuotaInvoice.getArr(j).getVisaCode(), listInvoiceNo.get(j).toString(), blPrpJQuotaInvoice.getArr(j).getCertiNo());
            	}
            }
            
            return strPayRefNo;
        } catch (UserException userexception) {
            throw userexception;
        } catch (SQLException sqlexception) {
            throw sqlexception;
        } catch (Exception exception) {
            throw exception;
        }
    }
    
    /**
     * @description:定额发票作废
     * @param arrPayRefNo
     * @throws UserException
     * @throws Exception
     * @author genghaijun-wb
     */
    public void dropQuotaInvioce(String[] arrPayRefNo) throws UserException, Exception {
        DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
            dbpool.beginTransaction();
            this.dropQuotaInvioce(dbpool, arrPayRefNo);
            dbpool.commitTransaction();
        } catch (UserException ue) {
            dbpool.rollbackTransaction();
            throw ue;
        } catch (Exception e) {
            dbpool.rollbackTransaction();
            throw e;
        } finally {
            dbpool.close();
        }
    }
    /**
     * @description:定额发票作废
     * @param dbpool
     * @param arrPayRefNo
     * @throws Exception
     * @author genghaijun-wb
     */
    public void dropQuotaInvioce(DbPool dbpool, String[] arrPayRefNo) throws Exception {
        BLPrpJQuotaInvoice blPrpJQuotaInvoice = new BLPrpJQuotaInvoice();
        DBPrpJQuotaInvoice dbPrpJQuotaInvoice = new DBPrpJQuotaInvoice();
        
        DBPrpJInvoicePre dbPrpJInvoicePre = new DBPrpJInvoicePre();
        String YJFPFlag ="";
        String[] strarrPayRefNo = arrPayRefNo[0].split("_");
        if(strarrPayRefNo.length>1){
        	arrPayRefNo[0]=strarrPayRefNo[0];
        	YJFPFlag = strarrPayRefNo[1];
        }else{
        	arrPayRefNo[0]=strarrPayRefNo[0];
        }
        
        String strWherePart = "";
        StringBuffer buf = new StringBuffer();
        buf.append("PayrefNo in (");
        for (int i = 0; i < arrPayRefNo.length; i++) {
            buf.append("'" + arrPayRefNo[i]);
            if (i == arrPayRefNo.length - 1) {
                buf.append("')");
            } else {
                buf.append("',");
            }
        }
        this.query(dbpool, buf.toString(), 0);
        blPrpJQuotaInvoice.query(buf.toString(), 0);
        
        DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
        
        DBPrpJplanPrint dbPrpJplanPrint = null;
        

        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
        
        int inttemp = 0;
        String strPoaype = "";
        String strPayRefFee_Invoice_Switch = "";
        try {
        	com.sp.payment.utility.PaymentTransCode paymentTransCode = new com.sp.payment.utility.PaymentTransCode();
        	strPayRefFee_Invoice_Switch = paymentTransCode.getConfigValueByComCode(this.getArr(0).getCenterCode(), "0000","PayRefFee_Invoice_Switch"); 
        } catch (Exception e) {
        	e.printStackTrace();
        }
        for (int i = 0; i < this.getSize(); i++) {
            prpJpayRefRecSchema = new PrpJpayRefRecSchema();
            prpJpayRefRecSchema = this.getArr(i);
            if(strPayRefFee_Invoice_Switch.length() >= 8){
            	if(inttemp == 0){
            		if("".equals(prpJpayRefRecSchema.getRedFlag())){
            			strPoaype = prpJpayRefRecSchema.getPoaType();
            			++ inttemp;
            		}
            	}
            	
            	if ("1".equals(prpJpayRefRecSchema.getIsDrop())) {
            		throw new UserException(-98, -1003, "BLPrpJpayRefRec.dropInvioce", "通过<<发票重新打印>>功能打印出的发票不允许作废。");
            	}
            }
            
            dbPrpJplanFee = new DBPrpJplanFee();
            dbPrpJplanFee.getInfo(dbpool, prpJpayRefRecSchema.getCertiType(), prpJpayRefRecSchema.getCertiNo(),
                    prpJpayRefRecSchema.getSerialNo(), prpJpayRefRecSchema.getPayRefReason());
            dbPrpJplanFee.setPayRefFee("0");
            dbPrpJplanFee.setInvPrintFlag("0");
            dbPrpJInvoicePre.getInfo(dbpool, prpJpayRefRecSchema.getCertiType(), prpJpayRefRecSchema.getCertiNo(),
                    prpJpayRefRecSchema.getSerialNo(), prpJpayRefRecSchema.getPayRefReason());
            





            

            
            dbPrpJplanPrint = new DBPrpJplanPrint();
            dbPrpJplanPrint.setSchema(dbPrpJplanFee);
            if(!"".equals(dbPrpJplanFee.getRealPayRefFeeNew())&&!"0".equals(dbPrpJplanFee.getRealPayRefFeeNew())){            	
            	dbPrpJplanPrint.setRealPayRefFee(dbPrpJplanFee.getRealPayRefFeeNew());
            }
            
        	if(YJFPFlag.equals("1")){
        		if("3".equals(dbPrpJplanFee.getApproveStatus())){
                	dbPrpJplanFee.setApproveStatus("4");
                	dbPrpJplanPrint.setApproveStatus("4");
                	dbPrpJInvoicePre.setApproveStatus("4");
            	}
        	}else{
        		if("3".equals(dbPrpJplanFee.getApproveStatus())){
                	dbPrpJplanFee.setApproveStatus("1");
                	dbPrpJplanPrint.setApproveStatus("1");
                	dbPrpJInvoicePre.setApproveStatus("1");
            	}
        	}
        	dbPrpJplanFee.update(dbpool);
        	dbPrpJInvoicePre.update(dbpool);
        	
            dbPrpJplanPrint.insert(dbpool);
            
        }
        












        
        if (strPayRefFee_Invoice_Switch.length() >= 8) {
        	this.dropQuotaInvioceNew(dbpool, this, arrPayRefNo, buf.toString());
      		return;
      	}
        
        
		for (int i = 0; i < this.getSize(); i++) {
			if ("1".equals(this.getArr(i).getIdentifyType())) {
				throw new UserException(-98, -1003, "BLPrpJpayRefRec.dropQuotaInvioce","已实收发票不允许作废！");
			}
        }
		
        
        






        
        if (this.getArr(0).getCertiType().equals("D")) {
            BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
            DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
            PrpJpayRefMainSchema prpJpayRefMainSchema = new PrpJpayRefMainSchema();
            String payRefNoCollection = "";
            for (int i = 0; i < this.getSize(); i++) {
                
                if (this.getArr(i).getIdentifyNumber() == null || this.getArr(i).getIdentifyNumber().equals("0")) {
                    throw new UserException(-98, -1003, "BLPrpJpayRefRec.query");
                }
                payRefNoCollection = payRefNoCollection + ",'" + this.getArr(i).getPayRefNo() + "'";
            }
            payRefNoCollection = "(" + payRefNoCollection.substring(1) + ")";
            strWherePart = "PayRefNo in" + payRefNoCollection;
            blPrpJpayRefMain.query(strWherePart, 0);
            for (int i = 0; i < blPrpJpayRefMain.getSize(); i++) {
                prpJpayRefMainSchema = new PrpJpayRefMainSchema();
                prpJpayRefMainSchema = blPrpJpayRefMain.getArr(i);
                dbPrpJpayRefMain.delete(dbpool, prpJpayRefMainSchema.getPayRefNo(), prpJpayRefMainSchema.getSerialNo());
            }
        }
        
        for (int i = 0; i < this.getSize(); i++) {
            if (this.getArr(i).getIdentifyNumber().equals("1")) {
                throw new UserException(-98, -1003, "BLPrpJpayRefRec.dropInvioce", "发票已经打包，请拆包后再作废！");
            }
        }
        String delrefmainsql = " delete from prpjpayrefmain where  " + buf.toString();
        dbpool.prepareInnerStatement(delrefmainsql);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
       
        
        
        String delRefRecSql = " delete from PrpJpayRefRec where " + buf.toString()+ "and PayRefTimes<1000";
        dbpool.prepareInnerStatement(delRefRecSql);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
        
        
        String delQuotaInvoiceSql = " delete from PrpJQuotaInvoice where " + buf.toString();
        dbpool.prepareInnerStatement(delQuotaInvoiceSql);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
        
        Visa visa = new Visa();
        for (int i = 0; i < blPrpJQuotaInvoice.getSize(); i++) {
            visa.cancelR(dbpool, blPrpJQuotaInvoice.getArr(i).getVisaCode(), blPrpJQuotaInvoice.getArr(i)
                    .getVisaSerialNo(), blPrpJQuotaInvoice.getArr(i).getOperateCode());
        }
        
    }
    
    
    public void query(DbPool dbpool,String iWherePart,int iLimitCount,String[] bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
      if (iLimitCount > 0 && dbPrpJpayRefRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRefRec WHERE " + iWherePart;
        schemas = dbPrpJpayRefRec.findByConditions(dbpool,strSqlStatement,bindValues);
      }
    }
    
    
    /*ADD BY PENGJINLING 2012-3-13 payment-435 产XXXXX广东省分公司营业税及附加的纳税基数规则调整 BEGIN*/    
    /**
	 * @desc 根据机构获取PrpDriskConfig表的ConfigValue值
	 * @author zhangliang
	 * @param iComCode 机构代码
	 * @param iRiskCode XXXXX种代码
	 * @param iConfigCode 配置代码
	 * @return configValue 配置变量
	 * @throws Exception
	 */
    public String getConfigValueByComCode(String iComCode, String iRiskCode, String iConfigCode) throws Exception {
        String configValue = "";
        while (true) {
            StringBuffer strBuffer = new StringBuffer();
            strBuffer.append(" ComCode ='").append(iComCode).append("' ");
            strBuffer.append(" AND RiskCode = '").append(iRiskCode).append("' ");
            strBuffer.append(" AND ConfigCode = '").append(iConfigCode).append("' ");
            PrpDriskConfigFindByConditionsCommand command = new PrpDriskConfigFindByConditionsCommand(strBuffer.toString());
            Collection col = (Collection) command.execute();
            Iterator iter = col.iterator();
            PrpDriskConfigDto prpDriskConfigDto = null;
            if (iter.hasNext()) {
                prpDriskConfigDto = (PrpDriskConfigDto) iter.next();
                configValue = prpDriskConfigDto.getConfigValue();
                break;
            }else {
                if(iComCode.equals("00000000")){
                    
                    throw new UserException(-98,-1200,"PaymentTransCode.getConfigValueByComCode()","获取总公司配置代码("+iConfigCode+")失败！");
                    
                }
                DBPrpDcompany dbPrpDcompany = new DBPrpDcompany();
                int intResult = dbPrpDcompany.getInfo(iComCode);
                if(intResult ==100){
                    
                    throw new UserException(-98,-1200,"PaymentTransCode.getConfigValueByComCode()","获取上级机构代码失败！");
                    
                }else{
                    iComCode = dbPrpDcompany.getUpperComCode();
                }
            }
        }
        return configValue;
    }
    
    /**
     * @author PENGJINLING
     * @param iPrpJpayRefRecSchemaList   发票数据
     * @return 
     * @throws Exception
     */
    public void dealVisa99(DbPool dbpool,List schemaList) throws Exception{
        BLPrpJpayWVisaTrace blPrpJpayWVisaTrace = null;
        PrpJpayRefRecSchema iPrpJpayRefRecSchema = null;
        blPrpJpayWVisaTrace = new BLPrpJpayWVisaTrace();
        DBPrpJpayWVisaTrace dbPrpJpayWVisaTrace = null;
        ArrayList list99 = new ArrayList();
        
	    for(int i=0;i<schemaList.size();i++){
	  	    iPrpJpayRefRecSchema = (PrpJpayRefRecSchema)schemaList.get(i);
       
	        String wVisaCode = iPrpJpayRefRecSchema.getWVisaCode();
	        String wVisaSerialNo = iPrpJpayRefRecSchema.getWVisaSerialNo();
	        
	        if("".equals(wVisaCode) || wVisaCode == null){
	      	    wVisaCode = iPrpJpayRefRecSchema.getVisaCode();
	      	    wVisaSerialNo = iPrpJpayRefRecSchema.getVisaSerialNo();
	        }
	        
	        dbPrpJpayWVisaTrace = new DBPrpJpayWVisaTrace();	
	        
	        dbPrpJpayWVisaTrace.getInfo(iPrpJpayRefRecSchema.getCertiType(), iPrpJpayRefRecSchema.getCertiNo(),
	      		  iPrpJpayRefRecSchema.getSerialNo(),iPrpJpayRefRecSchema.getPayRefReason(),
	      		  iPrpJpayRefRecSchema.getPayRefTimes(), wVisaCode, wVisaSerialNo);
	        
		    if(dbPrpJpayWVisaTrace != null){
			    String centerCode = iPrpJpayRefRecSchema.getCenterCode();
		        
		        String yearMonthPrint = new DateTime(new DateTime(iPrpJpayRefRecSchema.getPrintDate()),DateTime.YEAR_TO_MONTH).toString();
		        
		        String yearMonthCur = new DateTime(new Date(),DateTime.YEAR_TO_MONTH).toString();   		    	
		    	
		    	
		    	if(yearMonthCur.equals(yearMonthPrint)){
    				
	    			if("01".equals(dbPrpJpayWVisaTrace.getWVisaStatus())){
			    		String taxDropCofig = this.getConfigValueByComCode(centerCode, "0000", "SWITCH_OF_TAXDROP");
			    		
			    		if(!"".equals(taxDropCofig) && !(taxDropCofig == null) && "1".equals(taxDropCofig)){
		        			throw new UserException(-98,-1003,"BLPrpJpayRefRec.dropInvioce","发票类型：" + dbPrpJpayWVisaTrace.getVisaCode() + ",发票流水号：" + dbPrpJpayWVisaTrace.getVisaSerialNo() + ",请先做网上发票作废，再做发票作废！");
		        		}
		    		}
	    			
	    			if("98".equals(dbPrpJpayWVisaTrace.getWVisaStatus())){
	    				list99.add(dbPrpJpayWVisaTrace);
	    			}	    			
		    	}
		        else{
		        	 
		    		if("01".equals(dbPrpJpayWVisaTrace.getWVisaStatus()) || "98".equals(dbPrpJpayWVisaTrace.getWVisaStatus())){
		        	    
		    			list99.add(dbPrpJpayWVisaTrace);
		    		}           	
		        }
		    }
	    }
	    if(list99.size() > 0){
			blPrpJpayWVisaTrace.traceVisa99(dbpool,list99);	    	
	    }
    }
    /*ADD BY PENGJINLING 2012-3-13 payment-435 产XXXXX广东省分公司营业税及附加的纳税基数规则调整 END*/
    
    
    /**
     * @description:校验已实收且被红冲的数据是否同时选中
     * @param arrCertiType
     * @param arrCertiNo
     * @param arrSerialNo
     * @param arrPayRefReason
     * @throws Exception
     * @author genghaijun
     */
    public String checkRedPayrefNo(String[] arrCertiType, String[] arrCertiNo, String[] arrSerialNo,
            String[] arrPayRefReason) throws Exception {
        String wherePartRed = "";
        DbPool dbpool = new DbPool();
        String strDataSource = SysConfig.CONST_PAYMENTDATASOURCE;
        
        dbpool.open(strDataSource);
        try {
            dbpool.beginTransaction();
            wherePartRed = checkRedPayrefNo(dbpool, arrCertiType, arrCertiNo, arrSerialNo, arrPayRefReason);
            dbpool.commitTransaction();
        } catch (Exception exception) {
            dbpool.rollbackTransaction();
            throw exception;
        } finally {
            dbpool.close();
        }
        return wherePartRed;
    }
    /**
     * @description:校验已实收且被红冲的数据是否同时选中
     * @param dbpool
     * @param arrCertiType
     * @param arrCertiNo
     * @param arrSerialNo
     * @param arrPayRefReason
     * @return
     * @throws Exception
     * @author genghaijun
     */
    public String checkRedPayrefNo(DbPool dbpool, String[] arrCertiType, String[] arrCertiNo, String[] arrSerialNo,
            String[] arrPayRefReason) throws Exception {
        StringBuffer wherePartRed = new StringBuffer();
        String[] bindValuesRed = null;
        Map payRefNoredMap = new HashMap();
        Map payRefNoredMapTemp = new HashMap();
        BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
        for (int i = 0; i < arrCertiNo.length; i++) {
            bindValuesRed = new String[5];
            wherePartRed
                    .append(" CertiType = ? AND CertiNo = ? AND SerialNo = ?  AND PayRefReason = ? AND NVL(identifytype,0)<>1 AND RedFlag = ? ");
            bindValuesRed[0] = (arrCertiType[i]);
            bindValuesRed[1] = (arrCertiNo[i]);
            bindValuesRed[2] = (arrSerialNo[i]);
            bindValuesRed[3] = (arrPayRefReason[i]);
            bindValuesRed[4] = ("Reded");
            blPrpJpayRefRec.query(dbpool, wherePartRed.toString(), 0, bindValuesRed);
            if (blPrpJpayRefRec.getSize() > 0) {
                payRefNoredMap.put(blPrpJpayRefRec.getArr(0).getPayRefNo(), "");
            }
            wherePartRed.delete(0, wherePartRed.length());
            String strcheckKey = arrCertiType[i]+arrCertiNo[i]+arrSerialNo[i]+arrPayRefReason[i];
            payRefNoredMapTemp.put(strcheckKey, "");
        }
        wherePartRed.delete(0, wherePartRed.length());
        Iterator it = payRefNoredMap.entrySet().iterator();
        if (payRefNoredMap.size() > 0) {
            checkFlag = true;
            wherePartRed.append(" NVL(identifytype,0)<>1 AND RedFlag = 'Reded' AND PayRefNo IN (");
            int i = 0;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String strpayRefNored = (String) entry.getKey();
                if (i == (payRefNoredMap.size() - 1)) {
                    wherePartRed.append("'" + strpayRefNored + "')");
                } else if ((i % 900) == 0 && i > 0) {
                    wherePartRed.append("'" + strpayRefNored + "') OR PayRefNo IN ( ");
                } else {
                    wherePartRed.append("'" + strpayRefNored + "', ");
                }
                i++;
            }
        } else {
            wherePartRed.append(" PayRefNo = '0'");
        }
        payRefNoredMap.clear();
        blPrpJpayRefRec.initArr();
        blPrpJpayRefRec.query(dbpool,wherePartRed.toString());
        
        for (int i = 0; i < blPrpJpayRefRec.getSize(); i++) {
            String strcheckKeyRed = blPrpJpayRefRec.getArr(i).getCertiType()+blPrpJpayRefRec.getArr(i).getCertiNo()+blPrpJpayRefRec.getArr(i).getSerialNo()+blPrpJpayRefRec.getArr(i).getPayRefReason();
            if (!payRefNoredMapTemp.containsKey(strcheckKeyRed)) {
                throw new UserException(-96, -1167, "BLPrpJpayRefRec.genRedInvioce", "已实收且被红冲的数据不允许单独打印！");
            }
        }
        return wherePartRed.toString();
    }
    
    /**
     * @description:模拟打包,更新红冲数据
     * @param dbpool
     * @param blPrpJpayRefMain
     * @param wherePartRed
     * @param strPayRefNo
     * @param strDate
     * @throws Exception
     * @author genghaijun-wb
     */
    public void updateRedDate(String wherePartRed, String strPayRefNo, String strDate,String Poatype) throws Exception {
        DbPool dbpool = new DbPool();
        String strDataSource = SysConfig.CONST_PAYMENTDATASOURCE;
        
        dbpool.open(strDataSource);
        try {
            dbpool.beginTransaction();
            updateRedDate(dbpool, wherePartRed, strPayRefNo, strDate,Poatype);
            dbpool.commitTransaction();
        } catch (Exception exception) {
            dbpool.rollbackTransaction();
            throw exception;
        } finally {
            dbpool.close();
        }
    }
    /**
     * @description:模拟打包,更新红冲数据
     * @param dbpool
     * @param wherePartRed
     * @param strPayRefNo
     * @param strDate
     * @throws Exception
     * @author genghaijun
     */
    public void updateRedDate(DbPool dbpool, String wherePartRed, String strPayRefNo, String strDate,String Poatype) throws Exception {
        
        BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
        blPrpJpayRefRec.query(dbpool, wherePartRed);
        DBPrpJpayRefRec dbPrpJpayRefRec = null;
        DBPrpJpayRefMain dbPrpJpayRefMainRed = null;
        BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
        for (int j = 0; j < blPrpJpayRefRec.getSize(); j++) {
            dbPrpJpayRefRec = new DBPrpJpayRefRec();
            dbPrpJpayRefRec.getInfo(dbpool, blPrpJpayRefRec.getArr(j).getCertiType(), blPrpJpayRefRec.getArr(j)
                    .getCertiNo(), blPrpJpayRefRec.getArr(j).getSerialNo(),
                    blPrpJpayRefRec.getArr(j).getPayRefReason(), blPrpJpayRefRec.getArr(j).getPayRefTimes());
            String strOldPayrefNo = dbPrpJpayRefRec.getPayRefNo();
            dbPrpJpayRefRec.setFlag1(strOldPayrefNo);
            dbPrpJpayRefRec.setPayRefNo(strPayRefNo);
            dbPrpJpayRefRec.setPrintDate(strDate);
            dbPrpJpayRefRec.setIdentifyNumber("0");
            dbPrpJpayRefRec.setPoaType(Poatype);
            dbPrpJpayRefRec.update(dbpool);
        }
        
        blPrpJpayRefMain.query(dbpool, wherePartRed);
        for (int i = 0; i < blPrpJpayRefMain.getSize(); i++) {
            dbPrpJpayRefMainRed = new DBPrpJpayRefMain();
            dbPrpJpayRefMainRed.setSchema(blPrpJpayRefMain.getArr(i));
            dbPrpJpayRefMainRed.updateRefMain(dbpool, strPayRefNo, String.valueOf(2+i), strDate);
        }
    }
    
    /**
     * @description:发票作废，红冲数据恢复红冲后状态
     * @param PayRefNo
     * @throws Exception
     * @author genghaijun
     */
    public void updateRedInvoice(String PayRefNo) throws Exception {
        DbPool dbpool = new DbPool();
        String strDataSource = SysConfig.CONST_PAYMENTDATASOURCE;
        
        dbpool.open(strDataSource);
        try {
            dbpool.beginTransaction();
            updateRedInvoice(dbpool, PayRefNo);
            dbpool.commitTransaction();
        } catch (Exception exception) {
            dbpool.rollbackTransaction();
            throw exception;
        } finally {
            dbpool.close();
        }
    }
    /**
     * @description:发票作废，红冲数据恢复红冲后状态
     * @param dbpool
     * @param PayRefNo
     * @throws Exception
     * @author genghaijun
     */
    public void updateRedInvoice(DbPool dbpool, String PayRefNo) throws Exception {
        DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
        String strSqlStatement = " payRefNo ='" + PayRefNo
                + "' AND NVL(RedFlag,'0') = 'Reded' ";
        BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
        blPrpJpayRefRec.query(dbpool, strSqlStatement);
        DBPrpJplanFee dbPrpJplanFee = null;
        for (int i = 0; i < blPrpJpayRefRec.getSize(); i++) {
            
            dbPrpJpayRefRec.getInfo(dbpool, blPrpJpayRefRec.getArr(i).getCertiType(), blPrpJpayRefRec.getArr(i)
                    .getCertiNo(), blPrpJpayRefRec.getArr(i).getSerialNo(),
                    blPrpJpayRefRec.getArr(i).getPayRefReason(), blPrpJpayRefRec.getArr(i).getPayRefTimes());
            dbPrpJpayRefRec.setPayRefNo(dbPrpJpayRefRec.getFlag1());
            dbPrpJpayRefRec.setFlag1("");
            dbPrpJpayRefRec.setPrintDate("9999-12-30");
            dbPrpJpayRefRec.update(dbpool);

            
            dbPrpJplanFee = new DBPrpJplanFee();
            dbPrpJplanFee.getInfo(dbpool, this.getArr(i).getCertiType(), this.getArr(i).getCertiNo(), this.getArr(i)
                    .getSerialNo(), this.getArr(i).getPayRefReason());
            dbPrpJplanFee.setPayRefFee("0");
            dbPrpJplanFee.update(dbpool);

        }
        String sql = " UPDATE prpjpayrefmain SET payrefno = payrefnobak,payrefnobak = '',PrintDate = '9999-12-30' WHERE RedFlag = 'Reded' AND payrefno = '"
                + PayRefNo + "' ";
        dbpool.prepareInnerStatement(sql);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();

    }
    
    
    /**
     * 根据PrpJpayRefRec转换数据到PrpJpayRefRecTrace
     *@param PrpJpayRefRecSchema XX实收数据
     *@return PrpJpayRefRecTraceSchema 发票打印轨迹
     */
    public PrpJpayRefRecTraceSchema transSchema(PrpJpayRefRecSchema iSchema) {
        PrpJpayRefRecTraceSchema prpJpayRefRecTraceSchema = new PrpJpayRefRecTraceSchema();
        prpJpayRefRecTraceSchema.setAgentCode(iSchema.getAgentCode());
        prpJpayRefRecTraceSchema.setAgentName(iSchema.getAgentName());
        prpJpayRefRecTraceSchema.setAppliCode(iSchema.getAppliCode());
        prpJpayRefRecTraceSchema.setAppliName(iSchema.getAppliName());
        prpJpayRefRecTraceSchema.setAutoRefFlag(iSchema.getAutoRefFlag());
        prpJpayRefRecTraceSchema.setBusinessNature(iSchema.getBusinessNature());
        prpJpayRefRecTraceSchema.setChannelType(iSchema.getChannelType());
        prpJpayRefRecTraceSchema.setAppliType(iSchema.getAppliType());
        prpJpayRefRecTraceSchema.setCarNatureCode(iSchema.getCarNatureCode());
        prpJpayRefRecTraceSchema.setCarProperty(iSchema.getCarProperty());
        prpJpayRefRecTraceSchema.setCenterCode(iSchema.getCenterCode());
        prpJpayRefRecTraceSchema.setCertiNo(iSchema.getCertiNo());
        prpJpayRefRecTraceSchema.setCertiType(iSchema.getCertiType());
        prpJpayRefRecTraceSchema.setClaimNo(iSchema.getClaimNo());
        prpJpayRefRecTraceSchema.setClassCode(iSchema.getClassCode());
        prpJpayRefRecTraceSchema.setCoinsCode(iSchema.getCoinsCode());
        prpJpayRefRecTraceSchema.setCoinsFlag(iSchema.getCoinsFlag());
        prpJpayRefRecTraceSchema.setCoinsName(iSchema.getCoinsName());
        prpJpayRefRecTraceSchema.setCoinsType(iSchema.getCoinsType());
        prpJpayRefRecTraceSchema.setComCode(iSchema.getComCode());
        prpJpayRefRecTraceSchema.setContractNo(iSchema.getContractNo());
        prpJpayRefRecTraceSchema.setCurrency1(iSchema.getCurrency1());
        prpJpayRefRecTraceSchema.setCurrency2(iSchema.getCurrency2());
        prpJpayRefRecTraceSchema.setDisRate(iSchema.getDisRate());
        prpJpayRefRecTraceSchema.setEndDate(iSchema.getEndDate());
        prpJpayRefRecTraceSchema.setExchangeRate(iSchema.getExchangeRate());
        prpJpayRefRecTraceSchema.setFlag(iSchema.getFlag());
        prpJpayRefRecTraceSchema.setFlag1(iSchema.getFlag1());
        prpJpayRefRecTraceSchema.setFlag2(iSchema.getFlag2());
        prpJpayRefRecTraceSchema.setFlag3(iSchema.getFlag3());
        prpJpayRefRecTraceSchema.setHandler1Code(iSchema.getHandler1Code());
        prpJpayRefRecTraceSchema.setHandler1Name(iSchema.getHandler1Name());
        prpJpayRefRecTraceSchema.setHandlerCode(iSchema.getHandlerCode());
        prpJpayRefRecTraceSchema.setIdentifyNumber(iSchema.getIdentifyNumber());
        prpJpayRefRecTraceSchema.setIdentifyType(iSchema.getIdentifyType());
        prpJpayRefRecTraceSchema.setInsuredCode(iSchema.getInsuredCode());
        prpJpayRefRecTraceSchema.setInsuredName(iSchema.getInsuredName());
        prpJpayRefRecTraceSchema.setInvPrintConFlag(iSchema.getInvPrintConFlag());
        prpJpayRefRecTraceSchema.setMakeCom(iSchema.getMakeCom());
        prpJpayRefRecTraceSchema.setOperateDate(iSchema.getOperateDate());
        prpJpayRefRecTraceSchema.setOperateUnit(iSchema.getOperateUnit());
        prpJpayRefRecTraceSchema.setOperatorCode(iSchema.getOperatorCode());
        prpJpayRefRecTraceSchema.setPayBankId(iSchema.getPayBankId());
        prpJpayRefRecTraceSchema.setPayNo(iSchema.getPayNo());
        prpJpayRefRecTraceSchema.setPayRefDate(iSchema.getPayRefDate());
        prpJpayRefRecTraceSchema.setPayRefFee(iSchema.getPayRefFee());
        prpJpayRefRecTraceSchema.setPayRefName(iSchema.getPayRefName());
        prpJpayRefRecTraceSchema.setPayRefNo(iSchema.getPayRefNo());
        prpJpayRefRecTraceSchema.setPayRefNoType(iSchema.getPayRefNoType());
        prpJpayRefRecTraceSchema.setPayRefReason(iSchema.getPayRefReason());
        prpJpayRefRecTraceSchema.setPayRefReasonName(iSchema.getPayRefReasonName());
        prpJpayRefRecTraceSchema.setPayRefTimes(iSchema.getPayRefTimes());
        prpJpayRefRecTraceSchema.setPlanDate(iSchema.getPlanDate());
        prpJpayRefRecTraceSchema.setPlanFee(iSchema.getPlanFee());
        prpJpayRefRecTraceSchema.setPoaType(iSchema.getPoaType());
        prpJpayRefRecTraceSchema.setPolicyNo(iSchema.getPolicyNo());
        prpJpayRefRecTraceSchema.setPrintDate(iSchema.getPrintDate());
        prpJpayRefRecTraceSchema.setPrinterCode(iSchema.getPrinterCode());
        prpJpayRefRecTraceSchema.setRealPayRefNo(iSchema.getRealPayRefNo());
        prpJpayRefRecTraceSchema.setRemark(iSchema.getRemark());
        prpJpayRefRecTraceSchema.setRiskCode(iSchema.getRiskCode());
        prpJpayRefRecTraceSchema.setRiskCodeName(iSchema.getRiskCodeName());
        prpJpayRefRecTraceSchema.setSerialNo(iSchema.getSerialNo());
        prpJpayRefRecTraceSchema.setStartDate(iSchema.getStartDate());
        prpJpayRefRecTraceSchema.setToCenterCode(iSchema.getToCenterCode());
        prpJpayRefRecTraceSchema.setToComCode(iSchema.getToComCode());
        prpJpayRefRecTraceSchema.setToDesc(iSchema.getToDesc());
        prpJpayRefRecTraceSchema.setToStatus(iSchema.getToStatus());
        prpJpayRefRecTraceSchema.setToUserCode(iSchema.getToUserCode());
        prpJpayRefRecTraceSchema.setUnderWriteDate(iSchema.getUnderWriteDate());
        prpJpayRefRecTraceSchema.setUseNatureCode(iSchema.getUseNatureCode());
        prpJpayRefRecTraceSchema.setValidDate(iSchema.getValidDate());
        prpJpayRefRecTraceSchema.setVisaCode(iSchema.getVisaCode());
        prpJpayRefRecTraceSchema.setVisaHandler(iSchema.getVisaHandler());
        prpJpayRefRecTraceSchema.setVisaName(iSchema.getVisaName());
        prpJpayRefRecTraceSchema.setVisaSerialNo(iSchema.getVisaSerialNo());
        prpJpayRefRecTraceSchema.setVoucherNo(iSchema.getVoucherNo());
        return prpJpayRefRecTraceSchema;
    }
    
    /**
     * @description:查询红冲数据
     * @param CertiNo
     * @param CertiType
     * @param SerialNo
     * @param PayRefReason
     * @throws Exception
     * @author Administrator
     */
    public void checkPoaTypePrint(String CertiNo, String CertiType, String SerialNo, String PayRefReason) throws Exception {
        DbPool dbpool = new DbPool();
        String strDataSource = SysConfig.CONST_PAYMENTDATASOURCE;
        
        dbpool.open(strDataSource);
        try {
            dbpool.beginTransaction();
            checkPoaTypePrint(dbpool, CertiNo, CertiType, SerialNo, PayRefReason);
            dbpool.commitTransaction();
        } catch (Exception exception) {
            dbpool.rollbackTransaction();
            throw exception;
        } finally {
            dbpool.close();
        }
    }
    /**
     * @description:查询红冲数据
     * @param dbpool
     * @param CertiNo
     * @param CertiType
     * @param SerialNo
     * @param PayRefReason
     * @throws Exception
     * @author genghaijun-wb
     */
    public void checkPoaTypePrint(DbPool dbpool, String CertiNo, String CertiType, String SerialNo, String PayRefReason)
            throws Exception {
        String wherePartRed = " CertiType = '"+ CertiType+ "' AND CertiNo = '"+ CertiNo+ "' AND SerialNo = '"+ SerialNo+ "' AND PayRefReason = '"+ PayRefReason+ "' " +
        		              " AND RedFlag = 'Reded' AND NVL(identifytype,0)<>1 ";
        this.query(dbpool, wherePartRed, 0);
    }
    
    /**
     * @description:发票打印数据XXXXX存到临时表（不带DbPool）
     * @param arrCertiType
     * @param arrCertiNo
     * @param arrSerialNo
     * @param arrPayRefReason
     * @param iSchemas
     * @return strInvoiceId
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     */
    public String  SavePrpJInvoicePrintTem(String[] arrCertiType, String[] arrCertiNo, String[] arrSerialNo, String[] arrPayRefReason, ArrayList iSchemas)
    throws UserException,SQLException, Exception {
        DbPool dbpool = new DbPool();
        String strInvoiceId = "";
        try {
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE); 
            dbpool.beginTransaction();
            strInvoiceId = SavePrpJInvoicePrintTem(dbpool, arrCertiType, arrCertiNo, arrSerialNo, arrPayRefReason, iSchemas);
            dbpool.commitTransaction();
        } catch(UserException userexception) {
            dbpool.rollbackTransaction();
            throw userexception;
        } catch(SQLException sqlexception) {
            dbpool.rollbackTransaction();
            throw sqlexception;
        } catch(Exception exception) {
            dbpool.rollbackTransaction();
            throw exception;
        }
        finally {
            dbpool.close();
        }
        return strInvoiceId;
    }
    /**
     * @description:发票打印数据XXXXX存到临时表（带DbPool）
     * @param dbpool
     * @param arrCertiType
     * @param arrCertiNo
     * @param arrSerialNo
     * @param arrPayRefReason
     * @param iSchemas
     * @return
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     */
    public String SavePrpJInvoicePrintTem(DbPool dbpool,String[] arrCertiType, String[] arrCertiNo, String[] arrSerialNo, String[] arrPayRefReason, ArrayList iSchemas)
    throws UserException,SQLException, Exception {
    	DBPrpJQuotaInvoice dbPrpJQuotaInvoice = new DBPrpJQuotaInvoice();
    	int intInvoiceId = dbPrpJQuotaInvoice.getInvoiceIDSequence(dbpool);
    	BLPrpJInvoicePrintTem blPrpJInvoicePrintTem = new BLPrpJInvoicePrintTem();
    	PrpJpayRefRecSchema prpJpayRefRecSchema = (PrpJpayRefRecSchema)iSchemas.get(0);
    	for (int i = 0; i < arrCertiType.length; i++) {
    		PrpJInvoicePrintTemSchema prpJInvoicePrintTemSchema = new PrpJInvoicePrintTemSchema();
    		prpJInvoicePrintTemSchema.setCERTITYPE(arrCertiType[i]);
    		prpJInvoicePrintTemSchema.setCERTINO(arrCertiNo[i]);
    		prpJInvoicePrintTemSchema.setSERIALNO(arrSerialNo[i]);
    		prpJInvoicePrintTemSchema.setPAYREFREASON(arrPayRefReason[i]);
    		prpJInvoicePrintTemSchema.setOPERATEDATE(prpJpayRefRecSchema.getOperateDate());
    		prpJInvoicePrintTemSchema.setOPERATORCODE(prpJpayRefRecSchema.getOperatorCode());
    		prpJInvoicePrintTemSchema.setOPERATEUNIT(prpJpayRefRecSchema.getOperateUnit());
    		prpJInvoicePrintTemSchema.setCURRENCY1(prpJpayRefRecSchema.getCurrency1());
    		prpJInvoicePrintTemSchema.setVISACODE(prpJpayRefRecSchema.getVisaCode());
    		prpJInvoicePrintTemSchema.setVISANAME(prpJpayRefRecSchema.getVisaName());
    		prpJInvoicePrintTemSchema.setVISASERIALNO(prpJpayRefRecSchema.getVisaSerialNo());
    		prpJInvoicePrintTemSchema.setPRINTDATE(prpJpayRefRecSchema.getPrintDate());
    		prpJInvoicePrintTemSchema.setPRINTERCODE(prpJpayRefRecSchema.getPrinterCode());
    		prpJInvoicePrintTemSchema.setVISAHANDLER(prpJpayRefRecSchema.getVisaHandler());
    		prpJInvoicePrintTemSchema.setWVISACODE(prpJpayRefRecSchema.getWVisaCode());
    		prpJInvoicePrintTemSchema.setWVISASERIALNO(prpJpayRefRecSchema.getWVisaSerialNo());
    		prpJInvoicePrintTemSchema.setPAYREFNAME(prpJpayRefRecSchema.getPayRefName());
    		prpJInvoicePrintTemSchema.setFLAG3(prpJpayRefRecSchema.getFlag3());
    		prpJInvoicePrintTemSchema.setREMARK(prpJpayRefRecSchema.getRemark());
    		prpJInvoicePrintTemSchema.setIDENTIFYNUMBER(prpJpayRefRecSchema.getIdentifyNumber());
    		prpJInvoicePrintTemSchema.setCURRENCY2(prpJpayRefRecSchema.getCurrency2());
    		prpJInvoicePrintTemSchema.setPAYREFFEE(prpJpayRefRecSchema.getPayRefFee());
    		prpJInvoicePrintTemSchema.setCURRENCY3(prpJpayRefRecSchema.getCurrency3());
    		prpJInvoicePrintTemSchema.setCURRENCY3FEE(prpJpayRefRecSchema.getCurrency3Fee());
    		prpJInvoicePrintTemSchema.setEXCHANGERATE(prpJpayRefRecSchema.getExchangeRate());
    		prpJInvoicePrintTemSchema.setEXCHANGERATE3(prpJpayRefRecSchema.getExchangeRate3());
    		prpJInvoicePrintTemSchema.setCURRENCY2(prpJpayRefRecSchema.getCurrency2());
    		prpJInvoicePrintTemSchema.setPAYREFFEE(prpJpayRefRecSchema.getPayRefFee());
    		prpJInvoicePrintTemSchema.setCOMCODE(prpJpayRefRecSchema.getComCode());
    		prpJInvoicePrintTemSchema.setINVOICEID(intInvoiceId+"");
    		blPrpJInvoicePrintTem.setArr(prpJInvoicePrintTemSchema);
		}
    	blPrpJInvoicePrintTem.save(dbpool);
    	return intInvoiceId+"";
    }
    /**
     * @description:获取临时表中的打印数据（不带DbPool）
     * @param istrInvoiceId
     * @return
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     */
    public void  genInvoicePrintPre(String istrInvoiceId)throws UserException,Exception {
        DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE); 
            dbpool.beginTransaction();
            genInvoicePrintPre(dbpool, istrInvoiceId);
            dbpool.commitTransaction();
        } catch(UserException userexception) {
            dbpool.rollbackTransaction();
            throw userexception;
        } catch(SQLException sqlexception) {
            dbpool.rollbackTransaction();
            throw sqlexception;
        } catch(Exception exception) {
            dbpool.rollbackTransaction();
            throw exception;
        }
        finally {
            dbpool.close();
        }
    }
    /**
     * @description:获取临时表中的打印数据（带DbPool）
     * @param dbpool
     * @param istrInvoiceId
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     */
    public void genInvoicePrintPre(DbPool dbpool,String strInvoiceId)
    throws UserException,SQLException, Exception {    	
    	ArrayList schemas = new ArrayList();
    	BLPrpJInvoicePrintTem blPrpJInvoicePrintTem = new BLPrpJInvoicePrintTem();
    	if(strInvoiceId == null ||"".equals(strInvoiceId)){
    		throw new UserException(-98, -1003, "BLPrpJpayRefRec.genInvoicePrintPre", "发票打印InvoiceId不允许为空，请重新打印！");
    	}
    	blPrpJInvoicePrintTem.query(dbpool,"invoiceId='" + strInvoiceId + "'", 0, null);
    	if(blPrpJInvoicePrintTem.getSize() == 0){
    		throw new UserException(-98, -1003, "BLPrpJpayRefRec.genInvoicePrintPre", "发票已打印，请重新选择打印！");
    	}
    	String strdelInvoicePrintTem = " DELETE FROM PrpJInvoicePrintTem WHERE invoiceId='" + strInvoiceId + "'";
        
        dbpool.delete(strdelInvoicePrintTem);
    	int intSize = blPrpJInvoicePrintTem.getSize();
    	String[] arrCertiType = new String [intSize];
    	String[] arrCertiNo = new String [intSize];
    	String[] arrSerialNo = new String [intSize];
    	String[] arrPayRefReason = new String [intSize];
    	
    	for (int i = 0; i < blPrpJInvoicePrintTem.getSize(); i++) {
    		arrCertiType[i]    = blPrpJInvoicePrintTem.getArr(i).getCERTITYPE();
    		
    		arrCertiNo[i]      = blPrpJInvoicePrintTem.getArr(i).getCERTINO();
    		arrSerialNo[i]     = blPrpJInvoicePrintTem.getArr(i).getSERIALNO();
    		arrPayRefReason[i] = blPrpJInvoicePrintTem.getArr(i).getPAYREFREASON();
    		if(i==0){
    			PrpJpayRefRecSchema prpJpayRefRecSchema = new PrpJpayRefRecSchema();
    			prpJpayRefRecSchema.setOperateDate(blPrpJInvoicePrintTem.getArr(i).getOPERATEDATE());
    			prpJpayRefRecSchema.setOperatorCode(blPrpJInvoicePrintTem.getArr(i).getOPERATORCODE());
    			prpJpayRefRecSchema.setOperateUnit(blPrpJInvoicePrintTem.getArr(i).getOPERATEUNIT());
    			prpJpayRefRecSchema.setCurrency1(blPrpJInvoicePrintTem.getArr(i).getCURRENCY1());
    			prpJpayRefRecSchema.setVisaCode(blPrpJInvoicePrintTem.getArr(i).getVISACODE());
    			prpJpayRefRecSchema.setVisaName(blPrpJInvoicePrintTem.getArr(i).getVISANAME());
    			prpJpayRefRecSchema.setVisaSerialNo(blPrpJInvoicePrintTem.getArr(i).getVISASERIALNO());
    			prpJpayRefRecSchema.setPrintDate(blPrpJInvoicePrintTem.getArr(i).getPRINTDATE());
    			prpJpayRefRecSchema.setPrinterCode(blPrpJInvoicePrintTem.getArr(i).getOPERATORCODE());
    			prpJpayRefRecSchema.setVisaHandler(blPrpJInvoicePrintTem.getArr(i).getVISAHANDLER());
    			prpJpayRefRecSchema.setWVisaCode(blPrpJInvoicePrintTem.getArr(i).getWVISACODE());
    			prpJpayRefRecSchema.setWVisaSerialNo(blPrpJInvoicePrintTem.getArr(i).getWVISASERIALNO());
    			prpJpayRefRecSchema.setPayRefName(blPrpJInvoicePrintTem.getArr(i).getPAYREFNAME());
    			prpJpayRefRecSchema.setFlag3(blPrpJInvoicePrintTem.getArr(i).getFLAG3());
    			prpJpayRefRecSchema.setRemark(blPrpJInvoicePrintTem.getArr(i).getREMARK());
    			prpJpayRefRecSchema.setIdentifyNumber(blPrpJInvoicePrintTem.getArr(i).getIDENTIFYNUMBER());
    			prpJpayRefRecSchema.setCurrency2(blPrpJInvoicePrintTem.getArr(i).getCURRENCY2());
    			prpJpayRefRecSchema.setPayRefFee(blPrpJInvoicePrintTem.getArr(i).getPAYREFFEE());
    			prpJpayRefRecSchema.setCurrency3(blPrpJInvoicePrintTem.getArr(i).getCURRENCY3());
    			prpJpayRefRecSchema.setCurrency3Fee(blPrpJInvoicePrintTem.getArr(i).getCURRENCY3FEE());
    			prpJpayRefRecSchema.setExchangeRate(blPrpJInvoicePrintTem.getArr(i).getEXCHANGERATE());
    			prpJpayRefRecSchema.setExchangeRate3(blPrpJInvoicePrintTem.getArr(i).getEXCHANGERATE3());
    			prpJpayRefRecSchema.setCurrency2(blPrpJInvoicePrintTem.getArr(i).getCURRENCY2());
    			prpJpayRefRecSchema.setPayRefFee(blPrpJInvoicePrintTem.getArr(i).getPAYREFFEE());
    			prpJpayRefRecSchema.setExchangeRate(blPrpJInvoicePrintTem.getArr(i).getEXCHANGERATE());
    			prpJpayRefRecSchema.setComCode(blPrpJInvoicePrintTem.getArr(i).getCOMCODE());
    			schemas.add(prpJpayRefRecSchema);
    		}
    	}
    	this.genInvoice(dbpool,arrCertiType, arrCertiNo, arrSerialNo, arrPayRefReason, schemas); 
    }
    
    /**
     * @description:记录发票打印日志
     * @author ZhaoPengfei
     * @param  Map map
     */
    public void logInvoicePrint(Map map) {
        DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            dbpool.beginTransaction();
            DBPrpJplanFeePreTrace dbPrpJplanFeePreTrace = new DBPrpJplanFeePreTrace();
            
            String preTraceId = dbPrpJplanFeePreTrace.getSequence(dbpool);
            dbPrpJplanFeePreTrace.setPreTraceId(preTraceId);
            dbPrpJplanFeePreTrace.setCertiNo(map.get("certino").toString());
            dbPrpJplanFeePreTrace.setPolicyNo(map.get("visacode").toString());
            dbPrpJplanFeePreTrace.setContractNo(map.get("visaserialno").toString());
            dbPrpJplanFeePreTrace.setAppliCode(map.get("printercode").toString());
            dbPrpJplanFeePreTrace.setAppliName(map.get("flag").toString());
            dbPrpJplanFeePreTrace.setInsertDate("" + new DateTime(new Date(), DateTime.YEAR_TO_MILLISECOND));
            
            dbPrpJplanFeePreTrace.setCertiType("0");
            dbPrpJplanFeePreTrace.setSerialNo("0");
            dbPrpJplanFeePreTrace.setPlanSerialNo("0");
            dbPrpJplanFeePreTrace.setPayRefReason("0");
            dbPrpJplanFeePreTrace.setClassCode("0");
            dbPrpJplanFeePreTrace.setRiskCode("0");
            dbPrpJplanFeePreTrace.setStartDate("2099-01-01");
            dbPrpJplanFeePreTrace.setEndDate("2099-01-01");
            dbPrpJplanFeePreTrace.setCurrency1("0");
            dbPrpJplanFeePreTrace.setPremiumPlanFee("0");
            dbPrpJplanFeePreTrace.setCSTaxPlanFee("0");
            dbPrpJplanFeePreTrace.setPlanFee("0");
            dbPrpJplanFeePreTrace.setComCode("0");
            dbPrpJplanFeePreTrace.setMakeCom("0");
            dbPrpJplanFeePreTrace.setHandler1Code("0");
            dbPrpJplanFeePreTrace.setHandlerCode("0");
            dbPrpJplanFeePreTrace.setUnderWriteDate("2099-01-01");
            dbPrpJplanFeePreTrace.setCertiStatus("0");
            dbPrpJplanFeePreTrace.setAfterOperateStatus("0");

            dbPrpJplanFeePreTrace.insert(dbpool);
            dbpool.commitTransaction();
        } catch (Exception e) {
            try {
                dbpool.rollbackTransaction();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                dbpool.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    
    
    /**
     * @description:判断发票打印是否走新流程
     * @param iComCode
     * @param iInPutJPlanFeeDate
     * @throws Exception
     * @author genghaijun-wb
     */
	public boolean checkPrintNew(String iComCode, String iInPutJPlanFeeDate) throws Exception {
		if ("03".equals(iComCode)) {
			return true;
		} else {
			
			if (iInPutJPlanFeeDate != null && !"".equals(iInPutJPlanFeeDate)) {
				return true;
			}
			return false;
		}
	}
	/**
	 * @description:XX发票打印走新流程
	 * @param dbpool
	 * @param blPrpJpayRefRec
	 * @param prpJpayRefRecSchema
	 * @param iSchemas
	 * @param strPoatype
	 * @param wherePartRed
	 * @throws UserException
	 * @throws SQLException
	 * @throws Exception
	 * @author genghaijun-wb
	 */
	public void genInvoiceNew(DbPool dbpool, BLPrpJpayRefRec blPrpJpayRefRec,PrpJpayRefRecSchema prpJpayRefRecSchema, ArrayList iSchemas, String strPoatype,String wherePartRed)throws UserException, SQLException, Exception {
		Visa visa = new Visa();
		DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
		DBPrpJplanPrint dbPrpJplanPrint = null;
		BLPrpJpayWVisaTrace blPrpJpayWVisaTrace = null;
		BLPrpJpayRefRec blPrpJpayRefRecInPut = new BLPrpJpayRefRec();
		BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();

		int PayRefMain=0;
		
		double dblPayRefFee = 0;
		double payRefFee = 0;
		double dbCurrency3Fee = 0;
		String TPFlag = prpJpayRefRecSchema.getIdentifyNumber();
		boolean newDataRealPayFlag = false;
		BLPrpJPayBank blPrpJPayBank = new BLPrpJPayBank();
		String JSRED_SWITCHNEW = blPrpJPayBank.getConfigValueByComCode(prpJpayRefRecSchema.getOperateUnit(), "0000", "JSRED_SWITCHNEW");

		try {
			DBPrpJpayRefVoucher dBPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
			String strPayRefNo = dBPrpJpayRefVoucher.genRealPayRefNo();

			for (int i = 0; i < this.getSize(); i++) {
				double dblSumPayRefFee = 0;
				
				DBPrpJplanFee dbPrpJplanFeeRefFee = new DBPrpJplanFee();
				dbPrpJplanFeeRefFee.getInfo(this.getArr(i).getCertiType(), this.getArr(i).getCertiNo(),this.getArr(i).getSerialNo(),this.getArr(i).getPayRefReason());
				
				if(dbPrpJplanFeeRefFee.getPayFlag().equals("1")){
					
					if(!"".equals(dbPrpJplanFeeRefFee.getRealPayRefFeeNew())&&!"0".equals(dbPrpJplanFeeRefFee.getRealPayRefFeeNew())){
						dbPrpJplanFeeRefFee.setRealPayRefFee(dbPrpJplanFeeRefFee.getRealPayRefFeeNew());
					}
					
					for (int j = 0; j < iSchemas.size(); j++) {
						PrpJpayRefRecSchema prpJpayRefRecSchema1 = (PrpJpayRefRecSchema) iSchemas.get(j);
						dblSumPayRefFee += Double.parseDouble(prpJpayRefRecSchema1.getPayRefFee());
					}
					if (i == this.getSize() - 1) {
						dblPayRefFee = dblSumPayRefFee - payRefFee;
						
						if (!"EUR".equals(prpJpayRefRecSchema.getCurrency3())) {
							if (Math.abs(dblPayRefFee - Double.parseDouble(dbPrpJplanFeeRefFee.getRealPayRefFee())) > 0.1) {
								logger.info(this.getArr(i).getCertiNo() + "--发票金额错误1--planfee="
								        + this.getArr(i).getPlanFee() + "--dblPayRefFee=" + dblPayRefFee);
								throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", this.getArr(i).getCertiNo()
								        + "发票金额不正确，不能进行发票打印！");
							}
						}
					}else{
						
						dblPayRefFee = Double.parseDouble(dbPrpJplanFeeRefFee.getRealPayRefFee());
					}
					dblPayRefFee = Str.round(Str.round(dblPayRefFee, 8), 2);
					
					String switch_PRINT_INVOICES =SysConfig.getProperty("SWITCH_PRINT_INVOICES");
					if ("0".equals(switch_PRINT_INVOICES)){
						if (0 == dblPayRefFee) {
							throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", this.getArr(i).getCertiNo()
							        + "发票金额为0，不能进行发票打印！");
						}
					}
					

				}else{
					for (int j = 0; j < iSchemas.size(); j++) {
						PrpJpayRefRecSchema prpJpayRefRecSchema1 = (PrpJpayRefRecSchema) iSchemas.get(j);
						dblSumPayRefFee += Double.parseDouble(prpJpayRefRecSchema1.getPayRefFee());
						if (this.getArr(i).getCurrency1().equals(prpJpayRefRecSchema1.getCurrency1())) {
							this.getArr(i).setExchangeRate(prpJpayRefRecSchema1.getExchangeRate());
							dblPayRefFee = Double.parseDouble(this.getArr(i).getPlanFee())
							        * Double.parseDouble(prpJpayRefRecSchema1.getExchangeRate());
						}
						
						if ("EUR".equals(prpJpayRefRecSchema1.getCurrency3())) {
							this.getArr(i).setExchangeRate3(prpJpayRefRecSchema1.getExchangeRate3());
							this.getArr(i).setExchangeRate(prpJpayRefRecSchema1.getExchangeRate());
							dbCurrency3Fee = Double.parseDouble(this.getArr(i).getPlanFee())
							        * Double.parseDouble(prpJpayRefRecSchema1.getExchangeRate3());
							dbCurrency3Fee = Str.round(Str.round(dbCurrency3Fee, 8), 2);
							dblPayRefFee = dbCurrency3Fee * Double.parseDouble(prpJpayRefRecSchema1.getExchangeRate());
						}
					}
					if (i == this.getSize() - 1) {
						dblPayRefFee = dblSumPayRefFee - payRefFee;
						
						if (!"EUR".equals(prpJpayRefRecSchema.getCurrency3())) {
							if (Math.abs(dblPayRefFee - Double.parseDouble(this.getArr(i).getPlanFee())
							        * Double.parseDouble(this.getArr(i).getExchangeRate())) > 0.1) {
								logger.info(this.getArr(i).getCertiNo() + "--发票金额错误1--planfee="
								        + this.getArr(i).getPlanFee() + "--dblPayRefFee=" + dblPayRefFee);
								throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", this.getArr(i).getCertiNo()
								        + "发票金额不正确，不能进行发票打印！");
							}
						}
					}
					dblPayRefFee = Str.round(Str.round(dblPayRefFee, 8), 2);
					if (0 == dblPayRefFee) {
						throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", this.getArr(i).getCertiNo()
						        + "发票金额为0，不能进行发票打印！");
					}
				}
				
				payRefFee = payRefFee + dblPayRefFee;

				String strCertiType = this.getArr(i).getCertiType();
				String strCertiNo = this.getArr(i).getCertiNo();
				String strSerialNo = this.getArr(i).getSerialNo();
				String strPayRefReason = this.getArr(i).getPayRefReason();
				String strPayRefTimes = this.getArr(i).getPayRefTimes();
				String strExchangeRate = this.getArr(i).getExchangeRate();
				String strExchangeRate3 = this.getArr(i).getExchangeRate3();
				boolean checkNewdataFlag = false;
				if("1".equals(this.getArr(i).getIdentifyType())){
					DBPrpJpayRefRec dbPrpJpayRefRecNewData = new DBPrpJpayRefRec();
					int intResult = dbPrpJpayRefRecNewData.getInfo(dbpool, strCertiType, strCertiNo, strSerialNo, strPayRefReason,strPayRefTimes);
					if(intResult == 0){
						checkNewdataFlag = true;
						newDataRealPayFlag = true;
					}
				}
				
				if ("1".equals(this.getArr(i).getIdentifyType()) && checkNewdataFlag) {
					DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
					dbPrpJpayRefRec.getInfo(dbpool, strCertiType, strCertiNo, strSerialNo, strPayRefReason,
					        strPayRefTimes);
					dbPrpJpayRefRec.setPrintDate(prpJpayRefRecSchema.getOperateDate());
					dbPrpJpayRefRec.setOperatorCode(prpJpayRefRecSchema.getOperatorCode());
					dbPrpJpayRefRec.setPrinterCode(prpJpayRefRecSchema.getPrinterCode());
					dbPrpJpayRefRec.setOperateUnit(prpJpayRefRecSchema.getOperateUnit());
					if(!"1".equals(dbPrpJplanFeeRefFee.getPayFlag())){
						dbPrpJpayRefRec.setExchangeRate(strExchangeRate);
						dbPrpJpayRefRec.setCurrency2(prpJpayRefRecSchema.getCurrency2());
						dbPrpJpayRefRec.setPayRefFee(""+dblPayRefFee);
					}
					this.getArr(i).setRealPayRefNo(dbPrpJpayRefRec.getRealPayRefNo());
					this.getArr(i).setPayRefFee(""+dblPayRefFee);
					dbPrpJpayRefRec.setExchangeRate3(strExchangeRate3);
					dbPrpJpayRefRec.setCurrency3(prpJpayRefRecSchema.getCurrency3());
					dbPrpJpayRefRec.setVisaCode(prpJpayRefRecSchema.getVisaCode());
					dbPrpJpayRefRec.setVisaName(prpJpayRefRecSchema.getVisaName());
					dbPrpJpayRefRec.setVisaSerialNo(prpJpayRefRecSchema.getVisaSerialNo());
					dbPrpJpayRefRec.setWVisaCode(prpJpayRefRecSchema.getWVisaCode());
					dbPrpJpayRefRec.setWVisaSerialNo(prpJpayRefRecSchema.getWVisaSerialNo());
					dbPrpJpayRefRec.setVisaHandler(prpJpayRefRecSchema.getVisaHandler());
					dbPrpJpayRefRec.setPayRefName(prpJpayRefRecSchema.getPayRefName());
					dbPrpJpayRefRec.setRemark(prpJpayRefRecSchema.getRemark());
					dbPrpJpayRefRec.setPayRefNo(strPayRefNo);
					dbPrpJpayRefRec.setOperateDate(prpJpayRefRecSchema.getOperateDate());
					dbPrpJpayRefRec.setCurrency3Fee("" + dbCurrency3Fee);
					
					dbPrpJpayRefRec.setFlag(!"0J".equals(dbPrpJpayRefRec.getFlag())? "0":dbPrpJpayRefRec.getFlag());
					
					
					prpJpayRefRecSchema.setPlanFee(dbPrpJpayRefRec.getPlanFee());
					if((dbPrpJpayRefRec.getCoinsFlag().equals("5")||dbPrpJpayRefRec.getCoinsFlag().equals("1")) && dbPrpJpayRefRec.getCoinsType().equals("1")){
						BLPrpJpayRefRec blPrpJpayRefRecCoins = new BLPrpJpayRefRec();
						blPrpJpayRefRecCoins.query(dbpool, " RealPayRefNo = '"+dbPrpJpayRefRec.getRealPayRefNo()+"' And CertiNo = '"+dbPrpJpayRefRec.getCertiNo()+"' ");
						prpJpayRefRecSchema.setRealPayRefNo(dbPrpJpayRefRec.getRealPayRefNo());
						if(blPrpJpayRefRecCoins.getSize()> 1){
							for (int j = 0; j < blPrpJpayRefRecCoins.getSize(); j++) {
								if (blPrpJpayRefRecCoins.getArr(j).getCoinsType().equals("1")
								        && !dbPrpJpayRefRec.getSerialNo().equals(blPrpJpayRefRecCoins.getArr(j).getSerialNo())) {
									DBPrpJpayRefRec dbPrpJpayRefRecCoinsOther = new DBPrpJpayRefRec();
									dbPrpJpayRefRecCoinsOther.setSchema(blPrpJpayRefRecCoins.getArr(j));
									dbPrpJpayRefRecCoinsOther.setPrintDate(prpJpayRefRecSchema.getOperateDate());
									dbPrpJpayRefRecCoinsOther.setOperatorCode(prpJpayRefRecSchema.getOperatorCode());
									dbPrpJpayRefRecCoinsOther.setPrinterCode(prpJpayRefRecSchema.getPrinterCode());
									dbPrpJpayRefRecCoinsOther.setOperateUnit(prpJpayRefRecSchema.getOperateUnit());
									dbPrpJpayRefRecCoinsOther.setExchangeRate3(strExchangeRate3);
									dbPrpJpayRefRecCoinsOther.setCurrency2(prpJpayRefRecSchema.getCurrency2());
									dbPrpJpayRefRecCoinsOther.setCurrency3(prpJpayRefRecSchema.getCurrency3());
									dbPrpJpayRefRecCoinsOther.setVisaCode(prpJpayRefRecSchema.getVisaCode());
									dbPrpJpayRefRecCoinsOther.setVisaName(prpJpayRefRecSchema.getVisaName());
									dbPrpJpayRefRecCoinsOther.setVisaSerialNo(prpJpayRefRecSchema.getVisaSerialNo());
									dbPrpJpayRefRecCoinsOther.setWVisaCode(prpJpayRefRecSchema.getWVisaCode());
									dbPrpJpayRefRecCoinsOther.setWVisaSerialNo(prpJpayRefRecSchema.getWVisaSerialNo());
									dbPrpJpayRefRecCoinsOther.setVisaHandler(prpJpayRefRecSchema.getVisaHandler());
									dbPrpJpayRefRecCoinsOther.setPayRefName(prpJpayRefRecSchema.getPayRefName());
									dbPrpJpayRefRecCoinsOther.setRemark(prpJpayRefRecSchema.getRemark());
									
									if(!"1".equals(dbPrpJplanFeeRefFee.getPayFlag())){
										dbPrpJpayRefRecCoinsOther.setExchangeRate(strExchangeRate);
										dbPrpJpayRefRecCoinsOther.setPayRefFee(dbPrpJpayRefRecCoinsOther.getPlanFee());
									}
									
									dbPrpJpayRefRecCoinsOther.setPayRefNo(strPayRefNo);
									dbPrpJpayRefRecCoinsOther.setOperateDate(prpJpayRefRecSchema.getOperateDate());
									dbPrpJpayRefRecCoinsOther.setCurrency3Fee("" + dbCurrency3Fee);
									
									dbPrpJpayRefRecCoinsOther.setFlag(!"0J".equals(dbPrpJpayRefRecCoinsOther.getFlag())? "0":dbPrpJpayRefRecCoinsOther.getFlag());
									
									if (checkFlag) {
										dbPrpJpayRefRec.setPoaType(strPoatype);
									}
									dbPrpJpayRefRecCoinsOther.update(dbpool);
								}
                            }
						}
					}
					
					if (checkFlag) {
						dbPrpJpayRefRec.setPoaType(strPoatype);
					}
					dbPrpJpayRefRec.update(dbpool);

				} else {
					PayRefMain=1;
					this.getArr(i).setPayRefFee(""+dblPayRefFee);
					
					PrpJpayRefRecSchema prpJpayRefRecSchemaTemp = new PrpJpayRefRecSchema();
					prpJpayRefRecSchemaTemp.setSchema(this.getArr(i));
					prpJpayRefRecSchemaTemp.setPayRefFee("" + dblPayRefFee);
					prpJpayRefRecSchemaTemp.setOperateDate(prpJpayRefRecSchema.getOperateDate());
					prpJpayRefRecSchemaTemp.setOperatorCode(prpJpayRefRecSchema.getOperatorCode());
					prpJpayRefRecSchemaTemp.setOperateUnit(prpJpayRefRecSchema.getOperateUnit());
					prpJpayRefRecSchemaTemp.setCurrency2(prpJpayRefRecSchema.getCurrency2());
					prpJpayRefRecSchemaTemp.setVisaCode(prpJpayRefRecSchema.getVisaCode());
					prpJpayRefRecSchemaTemp.setVisaName(prpJpayRefRecSchema.getVisaName());
					prpJpayRefRecSchemaTemp.setVisaSerialNo(prpJpayRefRecSchema.getVisaSerialNo());
					prpJpayRefRecSchemaTemp.setWVisaCode(prpJpayRefRecSchema.getWVisaCode());
					prpJpayRefRecSchemaTemp.setWVisaSerialNo(prpJpayRefRecSchema.getWVisaSerialNo());
					prpJpayRefRecSchemaTemp.setPrintDate(prpJpayRefRecSchema.getPrintDate());
					prpJpayRefRecSchemaTemp.setPrinterCode(prpJpayRefRecSchema.getPrinterCode());
					prpJpayRefRecSchemaTemp.setVisaHandler(prpJpayRefRecSchema.getVisaHandler());
					prpJpayRefRecSchemaTemp.setPayRefName(prpJpayRefRecSchema.getPayRefName());
					prpJpayRefRecSchemaTemp.setIdentifyNumber(prpJpayRefRecSchema.getIdentifyNumber());
					prpJpayRefRecSchemaTemp.setRemark(prpJpayRefRecSchema.getRemark());
					prpJpayRefRecSchemaTemp.setCurrency3(prpJpayRefRecSchema.getCurrency3());
					prpJpayRefRecSchemaTemp.setCurrency3Fee("" + dbCurrency3Fee);
					prpJpayRefRecSchemaTemp.setFlag("0");
					
					prpJpayRefRecSchemaTemp.setPayTimes(prpJpayRefRecSchema.getPayTimes());
					prpJpayRefRecSchemaTemp.setPayMode(prpJpayRefRecSchema.getPayMode());
					prpJpayRefRecSchemaTemp.setProductType(prpJpayRefRecSchema.getProductType());
					
					prpJpayRefRecSchemaTemp.setPayRefNo(strPayRefNo);
					if (checkFlag) {
						prpJpayRefRecSchemaTemp.setPoaType(strPoatype);
					}
					blPrpJpayRefRecInPut.setArr(prpJpayRefRecSchemaTemp);
				}

				
				dbPrpJplanPrint = new DBPrpJplanPrint();
				dbPrpJplanPrint.delete(dbpool, strCertiType, strCertiNo, strSerialNo, strPayRefReason);
			}

			
			if (this.getSize() != 0) {
				PrpJpayRefMainSchema prpJpayRefMainSchema = new PrpJpayRefMainSchema();
				prpJpayRefMainSchema.setPayRefNo(strPayRefNo);
				prpJpayRefMainSchema.setSerialNo("1");
				prpJpayRefMainSchema.setPayRefNoType("2");
				prpJpayRefMainSchema.setVisaCode(prpJpayRefRecSchema.getVisaCode());
				prpJpayRefMainSchema.setVisaSerialNo(prpJpayRefRecSchema.getVisaSerialNo());
				prpJpayRefMainSchema.setPrintDate(prpJpayRefRecSchema.getPrintDate());
				prpJpayRefMainSchema.setPrinterCode(prpJpayRefRecSchema.getPrinterCode());
				prpJpayRefMainSchema.setVisaHandler(prpJpayRefRecSchema.getVisaHandler());
				prpJpayRefMainSchema.setPayRefName(prpJpayRefRecSchema.getPayRefName());
				prpJpayRefMainSchema.setIdentifyType("0");
				prpJpayRefMainSchema.setIdentifyNumber("0");
				prpJpayRefMainSchema.setCurrency2(prpJpayRefRecSchema.getCurrency2());
				prpJpayRefMainSchema.setCurrency3(prpJpayRefRecSchema.getCurrency3());
				prpJpayRefMainSchema.setRemark(prpJpayRefRecSchema.getRemark());

				if (this.getArr(0).getToStatus().equals("0") || this.getArr(0).getToStatus().equals("")) {
					prpJpayRefMainSchema.setCenterCode(this.getArr(0).getCenterCode());
					prpJpayRefMainSchema.setBranchCode("0");
				} else {
					prpJpayRefMainSchema.setCenterCode(this.getArr(0).getToCenterCode());
					prpJpayRefMainSchema.setBranchCode(this.getArr(0).getCenterCode());
				}
				
				String strComcode = prpJpayRefRecSchema.getComCode();
				if (strComcode.length() > 2
				        && (("03".equals(strComcode.substring(0, 2))) || ("35".equals(strComcode.substring(0, 2))))) {
					prpJpayRefMainSchema.setCheckCode(prpJpayRefRecSchema.getFlag3());
				}

				prpJpayRefMainSchema.setPayRefFee("" + payRefFee);
				blPrpJpayRefMain.setArr(prpJpayRefMainSchema);
			}

			
			this.genInvoiceForInvPrintDetail(dbpool, prpJpayRefRecSchema, payRefFee + "");

			
			if (checkFlag) {
				this.updateRedDate(dbpool, wherePartRed, strPayRefNo, prpJpayRefRecSchema.getPrintDate(), strPoatype);
				if(newDataRealPayFlag){
					
					BLPrpJpayRefRec blPrpJpayRefRecred = new BLPrpJpayRefRec();
					blPrpJpayRefRecred.query(dbpool, "RedFlag = 'Reded' AND PayRefNo ='" + strPayRefNo+"' ");
					DBPrpJpayRefRec dbPrpJpayRefRec = null;
					for (int j = 0; j < blPrpJpayRefRecred.getSize(); j++) {
						dbPrpJpayRefRec = new DBPrpJpayRefRec();
						dbPrpJpayRefRec.getInfo(dbpool, blPrpJpayRefRecred.getArr(j).getCertiType(), blPrpJpayRefRecred.getArr(j).getCertiNo(), blPrpJpayRefRecred.getArr(j).getSerialNo(),
								blPrpJpayRefRecred.getArr(j).getPayRefReason(), blPrpJpayRefRecred.getArr(j).getPayRefTimes());
						dbPrpJpayRefRec.setIdentifyType("1");
						dbPrpJpayRefRec.update(dbpool);
					}
				}
			}
			
			for (int i = 0; i < this.getSize(); i++) {
				dbPrpJplanFee = new DBPrpJplanFee();
				dbPrpJplanFee.getInfoForUpdate(dbpool, this.getArr(i).getCertiType(), this.getArr(i).getCertiNo(), this
				        .getArr(i).getSerialNo(), this.getArr(i).getPayRefReason());
				if (Double.parseDouble(Str.chgStrZero(dbPrpJplanFee.getPayRefFee())) != 0) {
					throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", "已经打过发票，不能再打！");
				}
				
				DBPrpJInvoicePre dbPrpJInvoicePre = new DBPrpJInvoicePre();
				dbPrpJInvoicePre.getInfo(dbpool, this.getArr(i).getCertiType(), this.getArr(i).getCertiNo(), this
				        .getArr(i).getSerialNo(), this.getArr(i).getPayRefReason());
				
				String switch_PRINT_INVOICES =SysConfig.getProperty("SWITCH_PRINT_INVOICES");
				if ("0".equals(switch_PRINT_INVOICES)){
					if(!"1".equals(dbPrpJplanFee.getApproveStatus())&&"0".equals(dbPrpJplanFee.getRealPayRefFee())&&"0".equals(dbPrpJplanFee.getPoaType())){
						throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoiceNew", this.getArr(i).getCertiNo()
						+ "非预借发票或预借发票审核状态非审核通过状态,不允许打印发票！");
					}
				}else if("1".equals(switch_PRINT_INVOICES)){
					BLPrpJpayRefRec blPrpJpayRefRecNew = new BLPrpJpayRefRec();
					if(!"1".equals(dbPrpJplanFee.getApproveStatus())&&"0".equals(dbPrpJplanFee.getPoaType())){
						String iWherePart = " certitype = '"+dbPrpJplanFee.getCertiType()+"' and certino = '"+dbPrpJplanFee.getCertiNo()+"' " +
	    				"and payrefreason = '"+dbPrpJplanFee.getPayRefReason()+"' and serialno = '"+dbPrpJplanFee.getSerialNo()+"' and payreftimes = 1";
						blPrpJpayRefRecNew.query(iWherePart,0);
			    		if(blPrpJpayRefRecNew.getArr(0).getVoucherNo() == null || blPrpJpayRefRecNew.getArr(0).getVoucherNo().length() == 0){
			    			throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoiceNew", this.getArr(i).getCertiNo()
			    					+ "非预借发票或预借发票审核状态非审核通过状态,不允许打印发票！");
			    		}
				 	}
				}
				
				if("1".equals(dbPrpJplanFee.getApproveStatus())){
					dbPrpJplanFee.setApproveStatus("3");
					dbPrpJInvoicePre.setApproveStatus("3");
				}
				double PayRefFee = 0.00;
				if(dbPrpJplanFee.getPayFlag().equals("1")){
					if(!"".equals(dbPrpJplanFee.getRealPayRefFeeNew())&&!"0".equals(dbPrpJplanFee.getRealPayRefFeeNew())){						
						PayRefFee = Double.parseDouble(dbPrpJplanFee.getRealPayRefFeeNew());						
					}else{
						PayRefFee = Double.parseDouble(dbPrpJplanFee.getRealPayRefFee());						
					}
				}else{
					PayRefFee = Double.parseDouble(prpJpayRefRecSchema.getExchangeRate())*Double.parseDouble(dbPrpJplanFee.getPlanFee());
				}
				dbPrpJplanFee.setPayRefFee(""+PayRefFee);
				
				
				dbPrpJplanFee.setInvPrintFlag("1");
				dbPrpJplanFee.update(dbpool);
				dbPrpJInvoicePre.update(dbpool);
			}
			
			blPrpJpayRefRecInPut.save(dbpool);
			blPrpJpayRefMain.save(dbpool);

			
			if (prpJpayRefRecSchema.getComCode().length() > 2
			        && "08".equals(prpJpayRefRecSchema.getComCode().substring(0, 2))) {
				blPrpJpayWVisaTrace = new BLPrpJpayWVisaTrace();
				blPrpJpayWVisaTrace.traceVisa(this.schemas, "refrec", "01");
			}

			
			if (TPFlag == null || TPFlag.equals("") || !TPFlag.equals("1")) {
				visa.useTrans(dbpool, prpJpayRefRecSchema.getOperatorCode(), prpJpayRefRecSchema.getVisaCode(),
				        prpJpayRefRecSchema.getVisaSerialNo(), this.getArr(0).getCertiNo());
			}
			
			String strSwitch = SysConfig.getProperty("SWITCH_OF_INVOICEPRINTLOG");
			if ("1".equals(strSwitch)) {
				Map invoiceMap = new HashMap();
				invoiceMap.put("certino", this.getArr(0).getCertiNo());
				invoiceMap.put("visacode", prpJpayRefRecSchema.getVisaCode());
				invoiceMap.put("visaserialno", prpJpayRefRecSchema.getVisaSerialNo());
				invoiceMap.put("printercode", prpJpayRefRecSchema.getOperatorCode());
				invoiceMap.put("flag", "print");
				this.logInvoicePrint(invoiceMap);
			}

		} catch (UserException userexception) {
			throw userexception;
		} catch (SQLException sqlexception) {
			throw sqlexception;
		} catch (Exception exception) {
			throw exception;
		}
	}
	/**
     * @description:打印数据插入表PrpJinvPrintMain、PrpJinvPrintDetail中
     * @param dbpool
     * @param iprpJpayRefRecSchema
     * @param payRefFee
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     * @author genghaijun-wb
     */
	public void genInvoiceForInvPrintDetail(DbPool dbpool, PrpJpayRefRecSchema iprpJpayRefRecSchema, String payRefFee)
	        throws UserException, SQLException, Exception {
		BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
		DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();

		String strInvoiceId = "";
		String strConditionPrintMain = "";
		String strConditionPrintDetail = "";

		try {
			
			BLPrpJinvPrintMain blPrpJinvPrintMain = new BLPrpJinvPrintMain();
			if (this.getSize() != 0) {
				PrpJinvPrintMainSchema prpJinvPrintMainSchema = new PrpJinvPrintMainSchema();
				DBPrpJinvPrintMain dbPrpJinvPrintMain = new DBPrpJinvPrintMain();
				BLPrpDuser blPrpDuser = new BLPrpDuser();
				strInvoiceId = dbPrpJinvPrintMain.getSequence(dbpool);
				prpJinvPrintMainSchema.setInvoiceId(strInvoiceId);
				prpJinvPrintMainSchema.setInvCode(iprpJpayRefRecSchema.getVisaCode());
				prpJinvPrintMainSchema.setInvName(iprpJpayRefRecSchema.getVisaName()); 
				prpJinvPrintMainSchema.setInvSerialNo(iprpJpayRefRecSchema.getVisaSerialNo());
				prpJinvPrintMainSchema.setInvPrintDate(iprpJpayRefRecSchema.getPrintDate());
				prpJinvPrintMainSchema.setInvPrinter(iprpJpayRefRecSchema.getPrinterCode());
				prpJinvPrintMainSchema.setInvPrinterName(blPrpDuser.translateCode(
				        iprpJpayRefRecSchema.getPrinterCode(), true));
				prpJinvPrintMainSchema.setInvHandler(iprpJpayRefRecSchema.getVisaHandler());
				prpJinvPrintMainSchema.setInvRemark(iprpJpayRefRecSchema.getRemark());
				prpJinvPrintMainSchema.setInvCustomName(iprpJpayRefRecSchema.getPayRefName());
				prpJinvPrintMainSchema.setCurrency2(iprpJpayRefRecSchema.getCurrency2());
				prpJinvPrintMainSchema.setInvoiceFee(payRefFee + "");
				prpJinvPrintMainSchema.setInvPrintModel("1");
				prpJinvPrintMainSchema.setInvStatus("P");
				prpJinvPrintMainSchema.setFlag("自动实收");
				blPrpJinvPrintMain.setArr(prpJinvPrintMainSchema);
			}
			blPrpJinvPrintMain.save(dbpool);

			
			BLPrpJinvPrintDetail blPrpJinvPrintDetail = new BLPrpJinvPrintDetail();
			
			Map map = new HashMap();
	        String NewKey2700 = "";
			
			for (int i = 0; i < this.getSize(); i++) {
				
				
				
            	if((this.getArr(i).getCoinsFlag().equals("5")||this.getArr(i).getCoinsFlag().equals("1"))&& this.getArr(i).getCoinsType().equals("1")){
            		BLPrpJpayRefRec blPrpJpayRefRecCoins = new BLPrpJpayRefRec();
            		blPrpJpayRefRecCoins.query(dbpool, " RealPayRefNo = '" + this.getArr(i).getRealPayRefNo()+ "' And CertiNo = '" + this.getArr(i).getCertiNo() + "' ");
            		if(blPrpJpayRefRecCoins.getSize()==0){
            			iprpJpayRefRecSchema.setPayRefFee(""+this.getArr(i).getPayRefFee());
            			blPrpJinvPrintDetail.setArr(this.genSchemaRefRec(this.getArr(i), iprpJpayRefRecSchema, strInvoiceId));
            		}
            		if (blPrpJpayRefRecCoins.getSize() > 1) {
            			for (int j = 0; j < blPrpJpayRefRecCoins.getSize(); j++) {
            				if (blPrpJpayRefRecCoins.getArr(j).getCoinsType().equals("1")&& this.getArr(i).getSerialNo().equals(blPrpJpayRefRecCoins.getArr(j).getSerialNo())) {
            					iprpJpayRefRecSchema.setPayRefFee(blPrpJpayRefRecCoins.getArr(j).getPayRefFee());
            					NewKey2700 = blPrpJpayRefRecCoins.getArr(j).getCertiType()+","
  					                   +blPrpJpayRefRecCoins.getArr(j).getCertiNo()+","
  							           +blPrpJpayRefRecCoins.getArr(j).getPolicyNo()+","
  							           +blPrpJpayRefRecCoins.getArr(j).getPayRefReason()+","
  							           +blPrpJpayRefRecCoins.getArr(j).getPayRefTimes()+","
  							           +blPrpJpayRefRecCoins.getArr(j).getSerialNo();
            					if(!map.containsKey(NewKey2700)){
            						map.put(NewKey2700, NewKey2700);
            						blPrpJinvPrintDetail.setArr(this.genSchemaRefRec(blPrpJpayRefRecCoins.getArr(j),iprpJpayRefRecSchema, strInvoiceId));
            					}
            					
            				}
            			}
            		}
            	}else{
            		blPrpJinvPrintDetail.setArr(this.genSchemaRefRec(this.getArr(i), iprpJpayRefRecSchema, strInvoiceId));
            	}
				if ((this.getArr(i).getCoinsFlag().equals("5")||this.getArr(i).getCoinsFlag().equals("1")) && this.getArr(i).getCoinsType().equals("1")) {
					BLPrpJpayRefRec blPrpJpayRefRecCoins = new BLPrpJpayRefRec();
					blPrpJpayRefRecCoins.query(dbpool, " RealPayRefNo = '" + this.getArr(i).getRealPayRefNo()+ "' And CertiNo = '" + this.getArr(i).getCertiNo() + "' ");
					if (blPrpJpayRefRecCoins.getSize() > 1) {
						for (int j = 0; j < blPrpJpayRefRecCoins.getSize(); j++) {
							if (blPrpJpayRefRecCoins.getArr(j).getCoinsType().equals("1")
							        && !this.getArr(i).getSerialNo().equals(blPrpJpayRefRecCoins.getArr(j).getSerialNo())) {
								iprpJpayRefRecSchema.setPlanFee(blPrpJpayRefRecCoins.getArr(j).getPlanFee());
								
            					iprpJpayRefRecSchema.setPayRefFee(blPrpJpayRefRecCoins.getArr(j).getPayRefFee());
            					NewKey2700 = blPrpJpayRefRecCoins.getArr(j).getCertiType()+","
 					                   +blPrpJpayRefRecCoins.getArr(j).getCertiNo()+","
 							           +blPrpJpayRefRecCoins.getArr(j).getPolicyNo()+","
 							           +blPrpJpayRefRecCoins.getArr(j).getPayRefReason()+","
 							           +blPrpJpayRefRecCoins.getArr(j).getPayRefTimes()+","
 							           +blPrpJpayRefRecCoins.getArr(j).getSerialNo();
            					if(!map.containsKey(NewKey2700)){
            						map.put(NewKey2700, NewKey2700);
            						blPrpJinvPrintDetail.setArr(this.genSchemaRefRec(blPrpJpayRefRecCoins.getArr(j),
    								        iprpJpayRefRecSchema, strInvoiceId));
            					}
            					
							}
						}
					}
				}
			}
			blPrpJinvPrintDetail.save(dbpool);
		} catch (UserException userexception) {
			throw userexception;
		} catch (SQLException sqlexception) {
			throw sqlexception;
		} catch (Exception exception) {
			throw exception;
		}
	}

	
	/**
	 * @description:发票作废新流程
	 * @param dbpool
	 * @param blPrpJpayRefRec
	 * @param strWherePart
	 * @throws Exception
	 * @author genghaijun-wb
	 */
    public void dropInvioceNew(DbPool dbpool,BLPrpJpayRefRec blPrpJpayRefRec,String strWherePart) throws Exception{
		DBPrpJpayRefRec dbPrpJpayRefRec = null;
		DBPrpJInvoicePre dbPrpJInvoicePre = null;
		DBPrpJplanFee dbPrpJPlanFee = null;
		for (int i = 0; i < blPrpJpayRefRec.getSize(); i++) {
			
			if ("1".equals(blPrpJpayRefRec.getArr(i).getIsDrop())) {
				throw new UserException(-98, -1003, "BLPrpJpayRefRec.dropInvioceNew", "通过<<发票重新打印>>功能打印出的发票不允许作废。");
			}

			String strIdentifyType = blPrpJpayRefRec.getArr(i).getIdentifyType();
			dbPrpJpayRefRec = new DBPrpJpayRefRec();
			dbPrpJPlanFee = new DBPrpJplanFee();
			dbPrpJInvoicePre = new DBPrpJInvoicePre();
			DBPrpJplanPrint dbPrpJplanPrint = new DBPrpJplanPrint();
			dbPrpJpayRefRec.setSchema(blPrpJpayRefRec.getArr(i));
			DBPrpJplanFee dbPrpJplanFeeExc = new DBPrpJplanFee();
			if ("1".equals(strIdentifyType)) {
				dbPrpJpayRefRec.setPrintDate("");
				dbPrpJpayRefRec.setOperatorCode("");
				dbPrpJpayRefRec.setPrinterCode("");
				dbPrpJpayRefRec.setOperateUnit("");
				dbPrpJplanFeeExc.getInfo(dbpool,dbPrpJpayRefRec.getCertiType(), dbPrpJpayRefRec.getCertiNo(),dbPrpJpayRefRec.getSerialNo(),dbPrpJpayRefRec.getPayRefReason());
				if(!"1".equals(dbPrpJplanFeeExc.getPayFlag())){
					if("0".equals(dbPrpJplanFeeExc.getPoaType())||dbPrpJplanFeeExc.getPoaType()==null||"".equals(dbPrpJplanFeeExc.getPoaType())){
						dbPrpJplanFeeExc.setPayFlag("1");
						dbPrpJplanFeeExc.setPayRefFee("0");
						dbPrpJplanFeeExc.setInvPrintFlag("0");
						
						dbPrpJplanFeeExc.setPayRefCNY(dbPrpJpayRefRec.getCurrency2());
						dbPrpJplanFeeExc.update(dbpool);
						
						dbPrpJplanPrint.setSchema(dbPrpJplanFeeExc);
						if(!"".equals(dbPrpJplanFeeExc.getRealPayRefFeeNew())&&!"0".equals(dbPrpJplanFeeExc.getRealPayRefFeeNew())){
							dbPrpJplanPrint.setRealPayRefFee(dbPrpJplanFeeExc.getRealPayRefFeeNew());							
						}
						dbPrpJplanPrint.update(dbpool);
					}
					
					
				}
				dbPrpJpayRefRec.setExchangeRate3("");
				dbPrpJpayRefRec.setCurrency3("");
				dbPrpJpayRefRec.setVisaCode("");
				dbPrpJpayRefRec.setVisaName("");
				dbPrpJpayRefRec.setVisaSerialNo("");
				dbPrpJpayRefRec.setWVisaCode("");
				dbPrpJpayRefRec.setWVisaSerialNo("");
				dbPrpJpayRefRec.setVisaHandler("");
				
				dbPrpJpayRefRec.setRemark("");
				
				
				dbPrpJpayRefRec.setOperateDate("");
				dbPrpJpayRefRec.update(dbpool);
			} else {
				dbPrpJpayRefRec.delete(dbpool, dbPrpJpayRefRec.getCertiType(), dbPrpJpayRefRec.getCertiNo(),
				        dbPrpJpayRefRec.getSerialNo(), dbPrpJpayRefRec.getPayRefReason(), dbPrpJpayRefRec
				                .getPayRefTimes());				
			}
		}

		
		String strinvPrintDetail = " delete from PrpJinvPrintDetail where VisaCode = '"
		        + blPrpJpayRefRec.getArr(0).getVisaCode() + "' AND VisaSerialNo = '"
		        + blPrpJpayRefRec.getArr(0).getVisaSerialNo() + "' ";
		dbpool.prepareInnerStatement(strinvPrintDetail);
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();

		
		String strJinvPrintMain = " delete from PrpJinvPrintMain where InvCode = '"
		        + blPrpJpayRefRec.getArr(0).getVisaCode() + "' AND InvSerialNo = '"
		        + blPrpJpayRefRec.getArr(0).getVisaSerialNo() + "' ";
		dbpool.prepareInnerStatement(strJinvPrintMain);
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();

		
		if (this.getArr(0).getCertiType().equals("D")) {
			BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
			DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
			PrpJpayRefMainSchema prpJpayRefMainSchema = new PrpJpayRefMainSchema();
			String payRefNoCollection = "";
			for (int i = 0; i < this.getSize(); i++) {
				if (this.getArr(i).getIdentifyNumber() == null || this.getArr(i).getIdentifyNumber().equals("0")) {
					throw new UserException(-98, -1003, "BLPrpJpayRefRec.query");

				}
				payRefNoCollection = payRefNoCollection + ",'" + this.getArr(i).getPayRefNo() + "'";
			}
			payRefNoCollection = "(" + payRefNoCollection.substring(1) + ")";
			
			strWherePart = " NVL(RedFlag,'0') <> 'Reded' AND PayRefNo in" + payRefNoCollection;
			blPrpJpayRefMain.query(strWherePart, 0);
			for (int i = 0; i < blPrpJpayRefMain.getSize(); i++) {
				prpJpayRefMainSchema = new PrpJpayRefMainSchema();
				prpJpayRefMainSchema = blPrpJpayRefMain.getArr(i);
				dbPrpJpayRefMain.delete(dbpool, prpJpayRefMainSchema.getPayRefNo(), prpJpayRefMainSchema.getSerialNo());
			}
		}

		
		String arrPayRefNo = "";
		boolean YujFP = true;
		for (int i = 0; i < this.getSize(); i++) {
			if (this.getArr(i).getIdentifyNumber().equals("1")) {
				throw new UserException(-98, -1003, "BLPrpJpayRefRec.dropInvioce", "发票已经打包，请拆包后再作废！");
			}
			arrPayRefNo = arrPayRefNo + ",'" + this.getArr(i).getPayRefNo() + "'";
			if(this.getArr(i).getIdentifyType().equals("1")){
	        	  YujFP=false;
	        }
		}
		
		arrPayRefNo = "(" + arrPayRefNo.substring(1) + ")";
	      if(YujFP){
	          String sql = " delete from prpjpayrefmain where payrefno in " + arrPayRefNo; 
	          dbpool.prepareInnerStatement(sql);
	          dbpool.executePreparedUpdate();
	          dbpool.closePreparedStatement();
	      }else{
	      	  
	      	  
	      	  checkDelRefMain(dbpool,"payrefno in "+arrPayRefNo);
	        }
	    
        
		
		
		
		
		
		
		
		
        BLPrpJPayBank blPrpJPayBank = new BLPrpJPayBank();
        
	    if ("1".equals(blPrpJPayBank.getConfigValueByComCode(blPrpJpayRefRec.getArr(0).getOperateUnit(), "0000", "JSRED_SWITCHNEW"))) {
        
        
			this.updateRedInvoice(dbpool, blPrpJpayRefRec.getArr(0).getPayRefNo());
		}
		Visa visa = new Visa();
		visa.cancelR(dbpool, blPrpJpayRefRec.getArr(0).getVisaCode(), blPrpJpayRefRec.getArr(0).getVisaSerialNo(),
		        blPrpJpayRefRec.getArr(0).getPrinterCode());
	}
    
    /**
     * @description:定额发票打印新流程
     * @param dbpool
     * @param iSchemas
     * @param listInvoiceNo
     * @param quotaInvoiceCount
     * @param strInvoiceFee
     * @param prpJpayRefRecSchema
     * @return
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     * @author genghaijun-wb
     */
    public String genQuotaInvoiceNew(DbPool dbpool,ArrayList iSchemas, ArrayList listInvoiceNo, int quotaInvoiceCount,
            String strInvoiceFee,PrpJpayRefRecSchema prpJpayRefRecSchema) throws UserException, SQLException, Exception {
        BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
        DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
        BLPrpJpayRefRec blPrpJpayRefRecInPut = new BLPrpJpayRefRec();
        DBPrpJplanPrint dbPrpJplanPrint = null;
        DBPrpJplanAirFly dbPrpJplanAirFly = null;
        Visa visa = new Visa();
        String iSerialNo = "";
        String strCondition = "";
        String TPFlag = prpJpayRefRecSchema.getIdentifyNumber();
        prpJpayRefRecSchema.setIdentifyNumber("0");
        double dblPayRefFee = 0;
        double payRefFee = 0;
        double dbCurrency3Fee = 0;
        
        int PayRefMain=0;
        
        BLPrpJpayWVisaTrace blPrpJpayWVisaTrace = null;
        BLPrpJQuotaInvoice blPrpJQuotaInvoice = new BLPrpJQuotaInvoice();
        try {
            DBPrpJpayRefVoucher dBPrpJpayRefVoucher = new DBPrpJpayRefVoucher();
            String strPayRefNo = dBPrpJpayRefVoucher.genRealPayRefNo();
            
            for (int i = 0; i < this.getSize(); i++) {
                double dblSumPayRefFee = 0;
                DBPrpJplanFee dbPrpJplanFeeRefFee = new DBPrpJplanFee();
				dbPrpJplanFeeRefFee.getInfo(this.getArr(i).getCertiType(), this.getArr(i).getCertiNo(),this.getArr(i).getSerialNo(),this.getArr(i).getPayRefReason());
                if(dbPrpJplanFeeRefFee.getPayFlag().equals("1")){
                	
                	if(!"".equals(dbPrpJplanFeeRefFee.getRealPayRefFeeNew())&&!"0".equals(dbPrpJplanFeeRefFee.getRealPayRefFeeNew())){
                		dbPrpJplanFeeRefFee.setRealPayRefFee(dbPrpJplanFeeRefFee.getRealPayRefFeeNew());
                	}
                	
					for (int j = 0; j < iSchemas.size(); j++) {
						PrpJpayRefRecSchema prpJpayRefRecSchema1 = (PrpJpayRefRecSchema) iSchemas.get(j);
						dblSumPayRefFee += Double.parseDouble(prpJpayRefRecSchema1.getPayRefFee());
					}
					if (i == this.getSize() - 1) {
						dblPayRefFee = dblSumPayRefFee - payRefFee;
						
						if (!"EUR".equals(prpJpayRefRecSchema.getCurrency3())) {
							if (Math.abs(dblPayRefFee - Double.parseDouble(dbPrpJplanFeeRefFee.getRealPayRefFee())) > 0.1) {
								logger.info(this.getArr(i).getCertiNo() + "--发票金额错误1--planfee="
								        + this.getArr(i).getPlanFee() + "--dblPayRefFee=" + dblPayRefFee);
								throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", this.getArr(i).getCertiNo()
								        + "发票金额不正确，不能进行发票打印！");
							}
						}
					}else{
						
						dblPayRefFee = Double.parseDouble(dbPrpJplanFeeRefFee.getRealPayRefFee());
					}
					dblPayRefFee = Str.round(Str.round(dblPayRefFee, 8), 2);
					
					String switch_PRINT_INVOICES =SysConfig.getProperty("SWITCH_PRINT_INVOICES");
					if ("0".equals(switch_PRINT_INVOICES)){
						if (0 == dblPayRefFee) {
							throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", this.getArr(i).getCertiNo()
							        + "发票金额为0，不能进行发票打印！");
						}
					}
					

				}else{
	                for (int j = 0; j < iSchemas.size(); j++) {
	                    PrpJpayRefRecSchema prpJpayRefRecSchema1 = (PrpJpayRefRecSchema) iSchemas.get(j);
	                    dblSumPayRefFee += Double.parseDouble(prpJpayRefRecSchema1.getPayRefFee());
	                    if (this.getArr(i).getCurrency1().equals(prpJpayRefRecSchema1.getCurrency1())) {
	                        this.getArr(i).setExchangeRate(prpJpayRefRecSchema1.getExchangeRate());
	                        dblPayRefFee = Double.parseDouble(this.getArr(i).getPlanFee())
	                                * Double.parseDouble(prpJpayRefRecSchema1.getExchangeRate());
	                    }
	                    if ("EUR".equals(prpJpayRefRecSchema1.getCurrency3())) {
	                        this.getArr(i).setExchangeRate3(prpJpayRefRecSchema1.getExchangeRate3());
	                        this.getArr(i).setExchangeRate(prpJpayRefRecSchema1.getExchangeRate());
	                        dbCurrency3Fee = Double.parseDouble(this.getArr(i).getPlanFee())
	                                * Double.parseDouble(prpJpayRefRecSchema1.getExchangeRate3());
	                        dbCurrency3Fee = Str.round(Str.round(dbCurrency3Fee, 8), 2);
	                        dblPayRefFee = dbCurrency3Fee * Double.parseDouble(prpJpayRefRecSchema1.getExchangeRate());
	                    }
	                }
	                if (i == this.getSize() - 1) {
	                    dblPayRefFee = dblSumPayRefFee - payRefFee;
	                    if (!"EUR".equals(prpJpayRefRecSchema.getCurrency3())) {
	                        if (Math.abs(dblPayRefFee - Double.parseDouble(this.getArr(i).getPlanFee())
	                                * Double.parseDouble(this.getArr(i).getExchangeRate())) > 0.1) {
	                            logger.info(this.getArr(i).getCertiNo() + "--发票金额错误1--planfee="
	                                    + this.getArr(i).getPlanFee() + "--dblPayRefFee=" + dblPayRefFee);
	                            throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", this.getArr(i).getCertiNo()
	                                    + "发票金额不正确，不能进行发票打印！");
	                        }
	                    }
	                }
	
	                dblPayRefFee = Str.round(Str.round(dblPayRefFee, 8), 2);
	
	                if (0 == dblPayRefFee) {
	                    throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", this.getArr(i).getCertiNo()
	                            + "发票金额为0，不能进行发票打印！");
	                }
				}
                payRefFee = payRefFee + dblPayRefFee;
                
                String strCertiType = this.getArr(i).getCertiType();
            	String strCertiNo = this.getArr(i).getCertiNo();
            	String strSerialNo = this.getArr(i).getSerialNo();
            	String strPayRefReason = this.getArr(i).getPayRefReason();
            	String strPayRefTimes = this.getArr(i).getPayRefTimes();
            	String strExchangeRate = this.getArr(i).getExchangeRate();
            	String strExchangeRate3 = this.getArr(i).getExchangeRate3();
            	
            	if("1".equals(this.getArr(i).getIdentifyType())){
            		DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
            		dbPrpJpayRefRec.getInfo(dbpool, strCertiType, strCertiNo, strSerialNo, strPayRefReason, strPayRefTimes);
            		dbPrpJpayRefRec.setPrintDate(prpJpayRefRecSchema.getOperateDate());    
            		dbPrpJpayRefRec.setOperatorCode(prpJpayRefRecSchema.getOperatorCode()); 
            		dbPrpJpayRefRec.setPrinterCode(prpJpayRefRecSchema.getPrinterCode());  
            		dbPrpJpayRefRec.setOperateUnit(prpJpayRefRecSchema.getOperateUnit());
            		if(!"1".equals(dbPrpJplanFeeRefFee.getPayFlag())){
            			dbPrpJpayRefRec.setExchangeRate(strExchangeRate);
            			dbPrpJpayRefRec.setCurrency2(prpJpayRefRecSchema.getCurrency2());
            			dbPrpJpayRefRec.setPayRefFee(""+dblPayRefFee);
            		}
            		dbPrpJpayRefRec.setExchangeRate3(strExchangeRate3);
            		dbPrpJpayRefRec.setCurrency3(prpJpayRefRecSchema.getCurrency3());
            		dbPrpJpayRefRec.setVisaCode(prpJpayRefRecSchema.getVisaCode());     
            		dbPrpJpayRefRec.setVisaName(prpJpayRefRecSchema.getVisaName());     
            		dbPrpJpayRefRec.setVisaSerialNo(strPayRefNo); 
            		dbPrpJpayRefRec.setWVisaCode(prpJpayRefRecSchema.getWVisaCode());    
            		dbPrpJpayRefRec.setWVisaSerialNo(prpJpayRefRecSchema.getWVisaSerialNo());
            		dbPrpJpayRefRec.setVisaHandler(prpJpayRefRecSchema.getVisaHandler());  
            		dbPrpJpayRefRec.setPayRefName(prpJpayRefRecSchema.getPayRefName());   
            		dbPrpJpayRefRec.setRemark(prpJpayRefRecSchema.getRemark());       
            		
            		dbPrpJpayRefRec.setPayRefNo(strPayRefNo);
            		dbPrpJpayRefRec.setOperateDate(prpJpayRefRecSchema.getOperateDate());
            		dbPrpJpayRefRec.setCurrency3Fee("" + dbCurrency3Fee);
            		
            		dbPrpJpayRefRec.setFlag(!"0J".equals(dbPrpJpayRefRec.getFlag())? "0":dbPrpJpayRefRec.getFlag());
            		
            		dbPrpJpayRefRec.update(dbpool);
            		
            	}else{
            		 PayRefMain=1;
            		 PrpJpayRefRecSchema prpJpayRefRecSchemaTemp = new PrpJpayRefRecSchema();
            		 prpJpayRefRecSchemaTemp.setSchema(this.getArr(i));
            		 prpJpayRefRecSchemaTemp.setPayRefFee("" + dblPayRefFee);
            		 prpJpayRefRecSchemaTemp.setOperateDate(prpJpayRefRecSchema.getOperateDate());
            		 prpJpayRefRecSchemaTemp.setOperatorCode(prpJpayRefRecSchema.getOperatorCode());
            		 prpJpayRefRecSchemaTemp.setOperateUnit(prpJpayRefRecSchema.getOperateUnit());
            		 prpJpayRefRecSchemaTemp.setCurrency2(prpJpayRefRecSchema.getCurrency2());
            		 prpJpayRefRecSchemaTemp.setVisaCode(prpJpayRefRecSchema.getVisaCode());
            		 prpJpayRefRecSchemaTemp.setVisaName(prpJpayRefRecSchema.getVisaName());
            		 prpJpayRefRecSchemaTemp.setVisaSerialNo(strPayRefNo);
            		 prpJpayRefRecSchemaTemp.setWVisaCode(prpJpayRefRecSchema.getWVisaCode());
            		 prpJpayRefRecSchemaTemp.setWVisaSerialNo(prpJpayRefRecSchema.getWVisaSerialNo());
            		 prpJpayRefRecSchemaTemp.setPrintDate(prpJpayRefRecSchema.getPrintDate());
            		 prpJpayRefRecSchemaTemp.setPrinterCode(prpJpayRefRecSchema.getPrinterCode());
            		 prpJpayRefRecSchemaTemp.setVisaHandler(prpJpayRefRecSchema.getVisaHandler());
            		 prpJpayRefRecSchemaTemp.setPayRefName(prpJpayRefRecSchema.getPayRefName());
            		 prpJpayRefRecSchemaTemp.setIdentifyType(prpJpayRefRecSchema.getIdentifyType());
            		 prpJpayRefRecSchemaTemp.setIdentifyNumber(prpJpayRefRecSchema.getIdentifyNumber());
            		 prpJpayRefRecSchemaTemp.setRemark(prpJpayRefRecSchema.getRemark());
            		 prpJpayRefRecSchemaTemp.setCurrency3(prpJpayRefRecSchema.getCurrency3());
            		 prpJpayRefRecSchemaTemp.setCurrency3Fee("" + dbCurrency3Fee);
            		 prpJpayRefRecSchemaTemp.setFlag("0");
            		 prpJpayRefRecSchemaTemp.setIdentifyType("0");
            		 prpJpayRefRecSchemaTemp.setPayRefNo(strPayRefNo);
            		 blPrpJpayRefRecInPut.setArr(prpJpayRefRecSchemaTemp);
            	}
            	
            	
            	dbPrpJplanPrint = new DBPrpJplanPrint();
            	dbPrpJplanPrint.delete(dbpool, strCertiType, strCertiNo, strSerialNo, strPayRefReason);
                
            	
            	
				dbPrpJplanAirFly = new DBPrpJplanAirFly();
				dbPrpJplanPrint.delete(dbpool, strCertiType, strCertiNo, strSerialNo, strPayRefReason);
            	
            	
            	
                
                double size = PubTools.div(Double.parseDouble(this.getArr(i).getPlanFee()), Double
                        .parseDouble(strInvoiceFee), 1);
                for (int k = 0; k < size; k++) 
                {
                    PrpJQuotaInvoiceSchema prpJQuotaInvoiceSchema = new PrpJQuotaInvoiceSchema();
                    prpJQuotaInvoiceSchema.setPayrefNo(strPayRefNo);
                    prpJQuotaInvoiceSchema.setVisaSerialNo(listInvoiceNo.get(--quotaInvoiceCount).toString());
                    prpJQuotaInvoiceSchema.setVisaCode(prpJpayRefRecSchema.getVisaCode());
                    prpJQuotaInvoiceSchema.setOperateCode(prpJpayRefRecSchema.getOperatorCode());
                    prpJQuotaInvoiceSchema.setOperateDate(prpJpayRefRecSchema.getOperateDate());
                    prpJQuotaInvoiceSchema.setCurrency("CNY");
                    prpJQuotaInvoiceSchema.setVISAName(prpJpayRefRecSchema.getVisaName());
                    prpJQuotaInvoiceSchema.setInvoiceFee(strInvoiceFee);
                    prpJQuotaInvoiceSchema.setFlag("");
                    prpJQuotaInvoiceSchema.setRemark(prpJpayRefRecSchema.getRemark());
                    prpJQuotaInvoiceSchema.setPayrefName(prpJpayRefRecSchema.getPayRefName());
                    prpJQuotaInvoiceSchema.setPolicyNo(this.getArr(i).getPolicyNo());
                    prpJQuotaInvoiceSchema.setCertiType(this.getArr(i).getCertiType());
                    prpJQuotaInvoiceSchema.setRiskCode(this.getArr(i).getRiskCode());
                    prpJQuotaInvoiceSchema.setCenterCode(this.getArr(i).getCenterCode());
                    prpJQuotaInvoiceSchema.setPayRefdate("");
                    prpJQuotaInvoiceSchema.setComCode(this.getArr(i).getComCode());
                    prpJQuotaInvoiceSchema.setCertiNo(this.getArr(i).getCertiNo());
                    blPrpJQuotaInvoice.setArr(prpJQuotaInvoiceSchema);
                }
        }
            blPrpJQuotaInvoice.save(dbpool);
            if (this.getSize() == 0) {
                throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", "发票金额为0，不能进行发票打印！");
            }
            if (prpJpayRefRecSchema.getComCode().length() > 2
                    && "08".equals(prpJpayRefRecSchema.getComCode().substring(0, 2))) {
                blPrpJpayWVisaTrace = new BLPrpJpayWVisaTrace();
                blPrpJpayWVisaTrace.traceVisa(this.schemas, "refrec", "01");
            }
            int RefMainFlag=0;
            for (int i = 0; i < this.getSize(); i++) {
                dbPrpJplanFee = new DBPrpJplanFee();
                dbPrpJplanFee.getInfoForUpdate(dbpool, this.getArr(i).getCertiType(), this.getArr(i).getCertiNo(), this
                        .getArr(i).getSerialNo(), this.getArr(i).getPayRefReason());
                
                DBPrpJInvoicePre dbPrpJInvoicePre = new DBPrpJInvoicePre();
				dbPrpJInvoicePre.getInfo(dbpool, this.getArr(i).getCertiType(), this.getArr(i).getCertiNo(), this
				        .getArr(i).getSerialNo(), this.getArr(i).getPayRefReason());
                if (Double.parseDouble(Str.chgStrZero(dbPrpJplanFee.getPayRefFee())) != 0) {
                    throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoice", "已经打过发票，不能再打！");
                }
                
				
                if("0".equals(this.getArr(i).getIdentifyType())&&("0".equals(this.getArr(i).getPoaType())||"".equals(this.getArr(i).getPoaType()))){
                	if(!"1".equals(dbPrpJplanFee.getApproveStatus())){
        				throw new UserException(-96, -1167, "BLPrpJplanFee.genInvoiceNew", this.getArr(i).getCertiNo()
        					+ "XXXX未实收，预借发票审核非审核通过状态,不允许打印发票！");
                	}
				}




				
				if("1".equals(dbPrpJplanFee.getApproveStatus())){
					dbPrpJplanFee.setApproveStatus("3");
					dbPrpJInvoicePre.setApproveStatus("3");
				}
				double PayRefFee = 0.00;
				if(dbPrpJplanFee.getPayFlag().equals("1")){
					if("".equals(dbPrpJplanFee.getRealPayRefFeeNew())||"0".equals(dbPrpJplanFee.getRealPayRefFeeNew())){
						PayRefFee = Double.parseDouble(dbPrpJplanFee.getRealPayRefFee());
					}else{
						PayRefFee = Double.parseDouble(dbPrpJplanFee.getRealPayRefFeeNew());
					}
				}else{
					PayRefFee = Double.parseDouble(prpJpayRefRecSchema.getExchangeRate())*Double.parseDouble(dbPrpJplanFee.getPlanFee());
				}
				dbPrpJplanFee.setPayRefFee(""+PayRefFee);
				
                
                dbPrpJplanFee.update(dbpool);
                
                dbPrpJInvoicePre.update(dbpool);
            }
            blPrpJpayRefRecInPut.save(dbpool);
            
            if(RefMainFlag==0){
	            BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
	            if (this.getSize() != 0) {
	                PrpJpayRefMainSchema prpJpayRefMainSchema = new PrpJpayRefMainSchema();
	                prpJpayRefMainSchema.setPayRefNo(strPayRefNo);
	                prpJpayRefMainSchema.setSerialNo("1");
	                prpJpayRefMainSchema.setPayRefNoType("2");
	                prpJpayRefMainSchema.setVisaCode(prpJpayRefRecSchema.getVisaCode());
	                prpJpayRefMainSchema.setVisaSerialNo(strPayRefNo);
	                prpJpayRefMainSchema.setPrintDate(prpJpayRefRecSchema.getPrintDate());
	                prpJpayRefMainSchema.setPrinterCode(prpJpayRefRecSchema.getPrinterCode());
	                prpJpayRefMainSchema.setVisaHandler(prpJpayRefRecSchema.getVisaHandler());
	                prpJpayRefMainSchema.setPayRefName(prpJpayRefRecSchema.getPayRefName());
	                prpJpayRefMainSchema.setIdentifyType("0");
	                prpJpayRefMainSchema.setIdentifyNumber("0");
	                prpJpayRefMainSchema.setCurrency2(prpJpayRefRecSchema.getCurrency2());
	                prpJpayRefMainSchema.setCurrency3(prpJpayRefRecSchema.getCurrency3());
	                prpJpayRefMainSchema.setRemark(prpJpayRefRecSchema.getRemark());
	                if (this.getArr(0).getToStatus().equals("0") || this.getArr(0).getToStatus().equals("")) {
	                    prpJpayRefMainSchema.setCenterCode(this.getArr(0).getCenterCode());
	                    prpJpayRefMainSchema.setBranchCode("0");
	                } else {
	                    prpJpayRefMainSchema.setCenterCode(this.getArr(0).getToCenterCode());
	                    prpJpayRefMainSchema.setBranchCode(this.getArr(0).getCenterCode());
	                }
	                if (prpJpayRefRecSchema.getComCode().length() > 2
	                        && "03".equals(prpJpayRefRecSchema.getComCode().substring(0, 2))) {
	                    prpJpayRefMainSchema.setCheckCode(prpJpayRefRecSchema.getFlag3());
	                }
	                prpJpayRefMainSchema.setPayRefFee("" + payRefFee);
	                blPrpJpayRefMain.setArr(prpJpayRefMainSchema);
	            }
	            blPrpJpayRefMain.save(dbpool);
            }
            
            prpJpayRefRecSchema.setVisaSerialNo(strPayRefNo);
            this.genInvoiceForInvPrintDetail(dbpool, prpJpayRefRecSchema, payRefFee+"");
            if (TPFlag == null || TPFlag.equals("") || !TPFlag.equals("1")) {
                for (int j = 0; j < blPrpJQuotaInvoice.getSize(); j++) {
                    visa.useTrans(dbpool, blPrpJQuotaInvoice.getArr(j).getOperateCode(), blPrpJQuotaInvoice.getArr(j)
                            .getVisaCode(), listInvoiceNo.get(j).toString(), blPrpJQuotaInvoice.getArr(j).getCertiNo());
                }
            }
            return strPayRefNo;
        } catch (UserException userexception) {
            throw userexception;
        } catch (SQLException sqlexception) {
            throw sqlexception;
        } catch (Exception exception) {
            throw exception;
        }
    }
    /**
     * @description:定额发票作废新流程
     * @param dbpool
     * @param blprpjpayrefrec
     * @param arrPayRefNo
     * @param PayrefNosql
     * @throws Exception
     * @author genghaijun-wb
     */
    public void dropQuotaInvioceNew(DbPool dbpool,BLPrpJpayRefRec blprpjpayrefrec ,String[] arrPayRefNo,String PayrefNosql) throws Exception {
        BLPrpJQuotaInvoice blPrpJQuotaInvoice = new BLPrpJQuotaInvoice();
        DBPrpJpayRefRec dbPrpJpayRefRec = null;
        DBPrpJInvoicePre dbPrpJInvoicePre = null;
		DBPrpJplanFee dbPrpJPlanFee = null;
        String strWherePart = "";
        blPrpJQuotaInvoice.query(PayrefNosql, 0);

        PrpJpayRefRecSchema prpJpayRefRecSchema = null;
		for (int i = 0; i < blprpjpayrefrec.getSize(); i++) {
			
			if ("1".equals(blprpjpayrefrec.getArr(i).getIsDrop())) {
				throw new UserException(-98, -1003, "BLPrpJpayRefRec.dropQuotaInvioceNew", "通过<<发票重新打印>>功能打印出的发票不允许作废。");
			}
			prpJpayRefRecSchema = new PrpJpayRefRecSchema();
			prpJpayRefRecSchema = blprpjpayrefrec.getArr(i);
			String strIdentifyType = prpJpayRefRecSchema.getIdentifyType();
			dbPrpJpayRefRec = new DBPrpJpayRefRec();
			dbPrpJPlanFee = new DBPrpJplanFee();
			dbPrpJInvoicePre = new DBPrpJInvoicePre();
			dbPrpJpayRefRec.setSchema(prpJpayRefRecSchema);
			DBPrpJplanFee dbPrpJplanFeeExc = new DBPrpJplanFee();
			if ("1".equals(strIdentifyType)) {
				dbPrpJpayRefRec.setPrintDate("");
				dbPrpJpayRefRec.setOperatorCode("");
				dbPrpJpayRefRec.setPrinterCode("");
				dbPrpJpayRefRec.setOperateUnit("");
				
				
					
					
				
				dbPrpJpayRefRec.setExchangeRate3("");
				
				dbPrpJpayRefRec.setCurrency3("");
				dbPrpJpayRefRec.setVisaCode("");
				dbPrpJpayRefRec.setVisaName("");
				dbPrpJpayRefRec.setVisaSerialNo("");
				dbPrpJpayRefRec.setWVisaCode("");
				dbPrpJpayRefRec.setWVisaSerialNo("");
				dbPrpJpayRefRec.setVisaHandler("");
				
				dbPrpJpayRefRec.setRemark("");
				
				
				dbPrpJpayRefRec.setOperateDate("");
				dbPrpJpayRefRec.update(dbpool);
			} else {
				dbPrpJpayRefRec.delete(dbpool, dbPrpJpayRefRec.getCertiType(), dbPrpJpayRefRec.getCertiNo(),
				        dbPrpJpayRefRec.getSerialNo(), dbPrpJpayRefRec.getPayRefReason(), dbPrpJpayRefRec
				                .getPayRefTimes());
			}
		}
        

        
        if (blprpjpayrefrec.getArr(0).getCertiType().equals("D")) {
            BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
            DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
            PrpJpayRefMainSchema prpJpayRefMainSchema = new PrpJpayRefMainSchema();
            String payRefNoCollection = "";
            for (int i = 0; i < blprpjpayrefrec.getSize(); i++) {
                
                if (this.getArr(i).getIdentifyNumber() == null || this.getArr(i).getIdentifyNumber().equals("0")) {
                    throw new UserException(-98, -1003, "BLPrpJpayRefRec.query");
                }
                payRefNoCollection = payRefNoCollection + ",'" + this.getArr(i).getPayRefNo() + "'";
            }
            payRefNoCollection = "(" + payRefNoCollection.substring(1) + ")";
            strWherePart = "PayRefNo in" + payRefNoCollection;
            blPrpJpayRefMain.query(strWherePart, 0);
            for (int i = 0; i < blPrpJpayRefMain.getSize(); i++) {
                prpJpayRefMainSchema = new PrpJpayRefMainSchema();
                prpJpayRefMainSchema = blPrpJpayRefMain.getArr(i);
                dbPrpJpayRefMain.delete(dbpool, prpJpayRefMainSchema.getPayRefNo(), prpJpayRefMainSchema.getSerialNo());
            }
        }
        
        boolean YujFP = true;
        for (int i = 0; i < blprpjpayrefrec.getSize(); i++) {
            if (this.getArr(i).getIdentifyNumber().equals("1")) {
                throw new UserException(-98, -1003, "BLPrpJpayRefRec.dropInvioce", "发票已经打包，请拆包后再作废！");
            }
            if(this.getArr(i).getIdentifyType().equals("1")){
          	  YujFP=false;
            }
        }
        
        if(YujFP){
        	String delrefmainsql = " delete from prpjpayrefmain where  " + PayrefNosql;
            dbpool.prepareInnerStatement(delrefmainsql);
            dbpool.executePreparedUpdate();
            dbpool.closePreparedStatement();
        }else{
        	  
        	  
        	  checkDelRefMain(dbpool,PayrefNosql);
        }
        
        
       
        
        String delQuotaInvoiceSql = " delete from PrpJQuotaInvoice where " + PayrefNosql;
        dbpool.prepareInnerStatement(delQuotaInvoiceSql);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
        
      
        String strinvPrintDetail = " delete from PrpJinvPrintDetail where VisaCode = '"+ prpJpayRefRecSchema.getVisaCode()+"' AND VisaSerialNo = '" + prpJpayRefRecSchema.getVisaSerialNo()+"' " ;
        
        logger.info(strinvPrintDetail);
        
        dbpool.prepareInnerStatement(strinvPrintDetail);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
        
        
        String strJinvPrintMain = " delete from PrpJinvPrintMain where InvCode = '"+ prpJpayRefRecSchema.getVisaCode()+"' AND InvSerialNo = '" + prpJpayRefRecSchema.getVisaSerialNo()+"' " ;
        
        
        logger.info(strJinvPrintMain);
        
        
        dbpool.prepareInnerStatement(strJinvPrintMain);
        dbpool.executePreparedUpdate();
        dbpool.closePreparedStatement();
        
        Visa visa = new Visa();
        for (int i = 0; i < blPrpJQuotaInvoice.getSize(); i++) {
            visa.cancelR(dbpool, blPrpJQuotaInvoice.getArr(i).getVisaCode(), blPrpJQuotaInvoice.getArr(i)
                    .getVisaSerialNo(), blPrpJQuotaInvoice.getArr(i).getOperateCode());
        }
    }
    
    /**
     * @description:PrpJpayRefRec数据转换成PrpJinvPrintDetail
     * @param PrpJpayRefRecSchema
     * @param iPrpJpayRefRecSchema
     * @param strInvoiceId
     * @return
     * @throws Exception
     * @author genghaijun-wb
     */
    public PrpJinvPrintDetailSchema genSchemaRefRec(PrpJpayRefRecSchema PrpJpayRefRecSchema, PrpJpayRefRecSchema iPrpJpayRefRecSchema,String strInvoiceId)
    throws Exception {
    	PrpJinvPrintDetailSchema prpJinvPrintDetailSchema = new PrpJinvPrintDetailSchema();
        prpJinvPrintDetailSchema.setInvoiceId(strInvoiceId);
        prpJinvPrintDetailSchema.setCertiType(PrpJpayRefRecSchema.getCertiType());
        prpJinvPrintDetailSchema.setCertiNo(PrpJpayRefRecSchema.getCertiNo());
        prpJinvPrintDetailSchema.setPolicyNo(PrpJpayRefRecSchema.getPolicyNo());
        prpJinvPrintDetailSchema.setSerialNo(PrpJpayRefRecSchema.getSerialNo());
        prpJinvPrintDetailSchema.setPayRefReason(PrpJpayRefRecSchema.getPayRefReason());
        prpJinvPrintDetailSchema.setPayRefTimes("0");
        prpJinvPrintDetailSchema.setClassCode(PrpJpayRefRecSchema.getClassCode());
        prpJinvPrintDetailSchema.setRiskCode(PrpJpayRefRecSchema.getRiskCode());
        prpJinvPrintDetailSchema.setContractNo(PrpJpayRefRecSchema.getContractNo());
        prpJinvPrintDetailSchema.setAppliCode(PrpJpayRefRecSchema.getAppliCode());
        prpJinvPrintDetailSchema.setAppliName(PrpJpayRefRecSchema.getAppliName());
        prpJinvPrintDetailSchema.setInsuredCode(PrpJpayRefRecSchema.getInsuredCode());
        prpJinvPrintDetailSchema.setInsuredName(PrpJpayRefRecSchema.getInsuredName());
        prpJinvPrintDetailSchema.setStartDate(PrpJpayRefRecSchema.getStartDate());
        prpJinvPrintDetailSchema.setEndDate(PrpJpayRefRecSchema.getEndDate());
        prpJinvPrintDetailSchema.setValidDate(PrpJpayRefRecSchema.getValidDate());    
        prpJinvPrintDetailSchema.setPayNo(PrpJpayRefRecSchema.getPayNo());
        prpJinvPrintDetailSchema.setPlanFee(PrpJpayRefRecSchema.getPlanFee());
        prpJinvPrintDetailSchema.setPlanDate(PrpJpayRefRecSchema.getPlanDate());
        prpJinvPrintDetailSchema.setComCode(PrpJpayRefRecSchema.getComCode());
        prpJinvPrintDetailSchema.setMakeCom(PrpJpayRefRecSchema.getMakeCom());
        prpJinvPrintDetailSchema.setAgentCode(PrpJpayRefRecSchema.getAgentCode());
        prpJinvPrintDetailSchema.setHandler1Code(PrpJpayRefRecSchema.getHandler1Code());
        prpJinvPrintDetailSchema.setHandlerCode(PrpJpayRefRecSchema.getHandlerCode());
        prpJinvPrintDetailSchema.setUnderWriteDate(PrpJpayRefRecSchema.getUnderWriteDate());
        prpJinvPrintDetailSchema.setCoinsFlag(PrpJpayRefRecSchema.getCoinsFlag());
        prpJinvPrintDetailSchema.setCoinsCode(PrpJpayRefRecSchema.getCoinsCode());
        prpJinvPrintDetailSchema.setCoinsName(PrpJpayRefRecSchema.getCoinsName());
        prpJinvPrintDetailSchema.setCoinsType(PrpJpayRefRecSchema.getCoinsType());
        prpJinvPrintDetailSchema.setCenterCode(PrpJpayRefRecSchema.getCenterCode());
        prpJinvPrintDetailSchema.setOperateDate(iPrpJpayRefRecSchema.getOperateDate());
        prpJinvPrintDetailSchema.setOperatorCode(iPrpJpayRefRecSchema.getOperatorCode());
        prpJinvPrintDetailSchema.setOperateUnit(iPrpJpayRefRecSchema.getOperateUnit());
        prpJinvPrintDetailSchema.setCurrency2(iPrpJpayRefRecSchema.getCurrency2());
        prpJinvPrintDetailSchema.setExchangeRate(iPrpJpayRefRecSchema.getExchangeRate());
        
        
        prpJinvPrintDetailSchema.setInvPrintFee(PrpJpayRefRecSchema.getPayRefFee());
        prpJinvPrintDetailSchema.setCurrency1(iPrpJpayRefRecSchema.getCurrency1());
        
        prpJinvPrintDetailSchema.setAutoRefFlag(PrpJpayRefRecSchema.getAutoRefFlag());
        prpJinvPrintDetailSchema.setInvPrintTimes("1");
        prpJinvPrintDetailSchema.setInvStatus("0");
        prpJinvPrintDetailSchema.setVisaCode(iPrpJpayRefRecSchema.getVisaCode());
        prpJinvPrintDetailSchema.setVisaName(iPrpJpayRefRecSchema.getVisaName());
        prpJinvPrintDetailSchema.setVisaSerialNo(iPrpJpayRefRecSchema.getVisaSerialNo());
        prpJinvPrintDetailSchema.setPrintDate(iPrpJpayRefRecSchema.getPrintDate());
        prpJinvPrintDetailSchema.setPrinterCode(iPrpJpayRefRecSchema.getPrinterCode());
        prpJinvPrintDetailSchema.setVisaHandler(iPrpJpayRefRecSchema.getVisaHandler());
        prpJinvPrintDetailSchema.setPayRefName(iPrpJpayRefRecSchema.getPayRefName());
        prpJinvPrintDetailSchema.setRemark(iPrpJpayRefRecSchema.getRemark());
        prpJinvPrintDetailSchema.setFlag(iPrpJpayRefRecSchema.getFlag());
        prpJinvPrintDetailSchema.setWVisaCode(iPrpJpayRefRecSchema.getWVisaCode());
        prpJinvPrintDetailSchema.setWVisaSerialNo(iPrpJpayRefRecSchema.getWVisaSerialNo());
        return prpJinvPrintDetailSchema;
    }
    /**
     * 判断是否删除RefMain中的数据
     * @param dbpool
     * @param refNoSQL
     * @throws UserException
     * @throws SQLException
     * @throws Exception
     */
    public void checkDelRefMain(DbPool dbpool,String refNoSQL)throws UserException,SQLException, Exception {
    	BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
        DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
        try {
        	blPrpJpayRefMain.query(refNoSQL, 0);
        	int sizeMain=blPrpJpayRefMain.getSize();
        	for (int i = 0; i < sizeMain; i++) {
        		
        		if("".equals(blPrpJpayRefMain.getArr(i).getVoucherNo())){
        			dbPrpJpayRefMain.setSchema(blPrpJpayRefMain.getArr(i));
        			dbPrpJpayRefMain.delete(dbpool, blPrpJpayRefMain.getArr(i).getPayRefNo(), blPrpJpayRefMain.getArr(i).getSerialNo());
        		}
        	}
			
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
