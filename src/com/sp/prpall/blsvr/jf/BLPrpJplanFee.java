package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.account.blsvr.BLAccBookBranch;
import com.sp.account.blsvr.BLAccBookCurrency;
import com.sp.account.blsvr.BLAccMainVoucher;
import com.sp.account.blsvr.BLAccMonthTrace;
import com.sp.account.blsvr.BLAccSubVoucher;
import com.sp.account.blsvr.BLAccVoucher;
import com.sp.account.schema.AccMainVoucherSchema;
import com.sp.account.schema.AccSubVoucherSchema;
import com.sp.payment.blsvr.jf.BLPrpJvisaExportMid;
import com.sp.payment.utility.Arith;
import com.sp.platform.dto.domain.PrpDriskConfigDto;
import com.sp.platform.ui.control.action.UIPrpDriskConfigAction;
import com.sp.prpall.blsvr.cb.BLPrpCPplan;
import com.sp.prpall.blsvr.cb.BLPrpCcoins;
import com.sp.prpall.blsvr.cb.BLPrpCcoinsDetail;
import com.sp.prpall.blsvr.cb.BLPrpCexpense;
import com.sp.prpall.blsvr.cb.BLPrpCitemKind;
import com.sp.prpall.blsvr.cb.BLPrpCplan;
import com.sp.prpall.blsvr.lp.BLPrpLcfeeCoins;
import com.sp.prpall.blsvr.lp.BLPrpLcharge;
import com.sp.prpall.blsvr.lp.BLPrpLloss;
import com.sp.prpall.blsvr.misc.BLPrpCommission;
import com.sp.prpall.blsvr.misc.BLPrpMiddleCost;
import com.sp.prpall.blsvr.pg.BLPrpPcarshipTax;
import com.sp.prpall.blsvr.pg.BLPrpPcoinsDetail;
import com.sp.prpall.blsvr.pg.BLPrpPexpense;
import com.sp.prpall.blsvr.pg.BLPrpPfee;
import com.sp.prpall.blsvr.pg.BLPrpPitemKind;
import com.sp.prpall.dbsvr.cb.DBPrpCPplan;
import com.sp.prpall.dbsvr.cb.DBPrpCcarshipTax;
import com.sp.prpall.dbsvr.cb.DBPrpCinsured;
import com.sp.prpall.dbsvr.cb.DBPrpCitemCar;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.cb.DBPrpCplan;
import com.sp.prpall.dbsvr.cb.DBPrpCsubsidy;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefMain;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefRec;
import com.sp.prpall.dbsvr.jf.DBPrpJplanCommission;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFee;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFeePre;
import com.sp.prpall.dbsvr.lp.DBPrpLclaim;
import com.sp.prpall.dbsvr.lp.DBPrpLcompensate;
import com.sp.prpall.dbsvr.lp.DBPrpLprepay;
import com.sp.prpall.dbsvr.misc.DBPrpCommission;
import com.sp.prpall.dbsvr.pg.DBPrpPcarshipTax;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;
import com.sp.prpall.dbsvr.pg.DBPrpPitemCar;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpCcoinsDetailSchema;
import com.sp.prpall.schema.PrpCitemKindSchema;
import com.sp.prpall.schema.PrpJpayRefRecSchema;
import com.sp.prpall.schema.PrpJplanCommissionSchema;
import com.sp.prpall.schema.PrpJplanFeeSchema;
import com.sp.prpall.schema.PrpLcfeeCoinsSchema;
import com.sp.prpall.schema.PrpLlossSchema;
import com.sp.prpall.schema.PrpPcoinsDetailSchema;
import com.sp.prpall.schema.PrpPitemKindSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDcompany;
import com.sp.utiall.dbsvr.DBPrpDagent;
import com.sp.utiall.dbsvr.DBPrpDcode;
import com.sp.utiall.dbsvr.DBPrpDuser;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;
import com.sp.visa.ui.control.action.UIVsStorageAction;
import com.sp.prpall.pubfun.PaymentTransCode;


/**
 * 定义PrpJplanFee的BL类
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>@createdate 2005-05-27</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJplanFee{
    private Vector schemas = new Vector();
    private BLAccMainVoucher blAccMainVoucher = new BLAccMainVoucher();
    
    private BLPrpJpayRefRec blPrpJpayRefRec  = new BLPrpJpayRefRec();
    protected final Log logger = LogFactory.getLog(getClass());

    /**
     * 构造函数
     */
    public BLPrpJplanFee(){
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
      
      blPrpJpayRefRec = new BLPrpJpayRefRec();
    }

    /**
     *增加一条PrpJplanFeeSchema记录
     *@param iPrpJplanFeeSchema PrpJplanFeeSchema
     *@throws Exception
     */
    public void setArr(PrpJplanFeeSchema iPrpJplanFeeSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpJplanFeeSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }

    /**
     *得到一条PrpJplanFeeSchema记录
     *@param index 下标
     *@return 一个PrpJplanFeeSchema对象
     *@throws Exception
     */
    public PrpJplanFeeSchema getArr(int index) throws Exception
    {
      PrpJplanFeeSchema prpJplanFeeSchema = null;
      try
      {
        prpJplanFeeSchema = (PrpJplanFeeSchema)this.schemas.get(index);
      }
      catch(Exception e)
      {
        throw e;
      }
      return prpJplanFeeSchema;
    }
    
    /**
     *删除一条PrpJplanFeeSchema记录
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
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      if (iLimitCount > 0 && dbPrpJplanFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJplanFee.query");
      }else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJplanFee WHERE " + iWherePart;
        schemas = dbPrpJplanFee.findByConditions(strSqlStatement);
      }
    }
    
    
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *效率优化条件改变，修改query方法
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryByLimit(String iWherePart,int iLimitCount) throws UserException,Exception
    {
        String strSqlStatement = "";
        String strOthWherePart =" ORDER BY CertiNo,PolicyNo,PayNo,SerialNo DESC";
        DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
        if (iLimitCount > 0 && dbPrpJplanFee.getCount(iWherePart) > iLimitCount)
        {
          throw new UserException(-98,-1003,"BLPrpJplanFee.queryByLimit");
        }else
        {
          initArr();
          strSqlStatement = " SELECT * FROM PrpJplanFee WHERE " + iWherePart+" and ROWNUM<=1000"+strOthWherePart;
          schemas = dbPrpJplanFee.findByConditions(strSqlStatement);
        }

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
        DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();

        initArr();
        strSqlStatement = " SELECT * FROM PrpJplanFee WHERE " + iWherePart+" and ROWNUM<=1000"+strOthWherePart;
        schemas = dbPrpJplanFee.findByConditions(strSqlStatement,bindValues);

    }
     /**
     * 相关组合产品查询(根据已知条件来查询与该记录相关的组合记录) zhanglina 20070908
     * @param iWherePart 查询条件
     * @param strcomSQL2 查询符合以上条件的相关的组合记录的条件
     * @param iLimitCount 记录数的限制
     * @throws UserException
     * @throws Exception
     */
    public void queryCombinRef(String iWherePart,String strcomSQL2,int iLimitCount) throws UserException,Exception
    {
    	String strSqlStatement = "";
    	DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
    	if(iLimitCount > 0 && dbPrpJplanFee.getCount(iWherePart) > iLimitCount)
    	{
    		throw new UserException(-98,-1003,"BLPrpJplanFee.queryCombinRef");
    	}else{
    		initArr();
    		strSqlStatement = "SELECT * from PrpJplanFee " +strcomSQL2
    		                   + " and ContractNo in (SELECT ContractNo from PrpJplanFee WHERE "
    		                   + iWherePart
    		                   + " ) order by contractno,certino,policyNo ";
    		schemas = dbPrpJplanFee.findByConditions(strSqlStatement);
    	}
    }
    
    /**
     * 相关组合产品查询(根据已知条件来查询与该记录相关的组合记录) zhanglina 20070908
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
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      initArr();
      strSqlStatement = "SELECT * from PrpJplanFee " +strcomSQL2
                         + " and ContractNo in (SELECT ContractNo from PrpJplanFee WHERE "
                         + iWherePart
                         + " ) and ROWNUM<=1000 order by contractno,PayNo,certino,policyNo ";
      schemas = dbPrpJplanFee.findByConditions(strSqlStatement, bindValues);
    }
    /**
     *应收XX核销查询 SangMingqian 20060118
     *@param iWherePart 查询条件
     *@param iOrderBy 排序项
     * @param iDateWhere 限制实收日期的条件
     *@throws UserException
     *@throws Exception
     */
    public void queryPremiumRef(String iWherePart,String iOrderBy,String iDateWhere) throws Exception
    {
      String strSqlStatement = "select prpjplanfee.Currency1,prpjplanfee.comcode,prpjplanfee.agentcode,"
      	                       + " prpjplanfee.handler1code,"
                               + " sum(prpjplanfee.planfee) as PlanFee,"
                               
							   + " sum(decode(b.payreffee,null,0,b.payreffee/b.exchangerate)) as RealPayRefFee "
                               + " from prpjplanfee ,(select * from prpjpayrefrec where " + iDateWhere + ") b "
                               + " where prpjplanfee.certitype=b.certitype(+) "
                               + " and prpjplanfee.certino=b.certino(+) "
                               + " and prpjplanfee.serialno=b.serialno(+) "
                               + " and prpjplanfee.payrefreason=b.payrefreason(+) "
                               + " and " + iWherePart
                               + " group by prpjplanfee.Currency1,prpjplanfee.comcode,prpjplanfee.agentcode,"
							   + "prpjplanfee.handler1code ";
      strSqlStatement += " order by " + iOrderBy;

      this.initArr();
      DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
        PrpJplanFeeSchema prpJplanFeeSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJplanFeeSchema = new PrpJplanFeeSchema();
          prpJplanFeeSchema.setCurrency1(resultSet.getString("Currency1"));
          prpJplanFeeSchema.setComCode(resultSet.getString("ComCode"));
          prpJplanFeeSchema.setAgentCode(resultSet.getString("AgentCode"));
          prpJplanFeeSchema.setHandler1Code(resultSet.getString("Handler1Code"));
          prpJplanFeeSchema.setPlanFee(""+resultSet.getDouble("PlanFee"));
          prpJplanFeeSchema.setRealPayRefFee(""+resultSet.getDouble("RealPayRefFee"));
          this.setArr(prpJplanFeeSchema);
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
     * 应收XX核销查询（先汇总再核销）SangMingqian 20060607
     * @param iWherePart1 公共查询条件
     * @param iWherePart2 prpjpayrefrec的查询条件
     * @param iPowerWhere1 prpjplanfee的权限控制
     * @param iPowerWhere2 prpjpayrefrec的权限控制
     * @param iOrderBy 排序项
     * @throws Exception
     */
    public void queryPremiumRefNew(String iWherePart1,String iWherePart2,String iPowerWhere1,
    		                         String iPowerWhere2,String iOrderBy ) throws Exception
    {
      String strSqlStatement = "";
      strSqlStatement = " SELECT Currency1,ComCode,AgentCode,Handler1Code, "
                + " SUM(PlanFee) as PlanFee,SUM(RealPayRefFee) as RealPayRefFee "
                + " FROM "
                + " (SELECT Currency1,ComCode,DECODE(AgentCode,'0',NULL,AgentCode) as AgentCode, "
                + " Handler1Code,SUM(PlanFee) as PlanFee,0 as RealPayRefFee "
                + " FROM PrpJplanFee "
                + " WHERE " + iWherePart1 + iPowerWhere1
                + " GROUP BY Currency1,ComCode,AgentCode,Handler1Code "
                + " UNION ALL "
                + " SELECT Currency1,ComCode,DECODE(AgentCode,'0',NULL,AgentCode) AS AgentCode, "
                + " Handler1Code,0 as PlanFee,SUM(PayRefFee/ExchangeRate) AS RealPayRefFee "
                + " FROM PrpJpayRefRec " 
                + " WHERE " + iWherePart1 + " AND " + iWherePart2 + iPowerWhere2 
                + " GROUP BY Currency1,ComCode,AgentCode,Handler1Code) "
                + " GROUP BY Currency1,ComCode,AgentCode,Handler1Code "
                + " ORDER BY " + iOrderBy;
      this.initArr();
      DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
        PrpJplanFeeSchema prpJplanFeeSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJplanFeeSchema = new PrpJplanFeeSchema();
          prpJplanFeeSchema.setCurrency1(resultSet.getString("Currency1"));
          prpJplanFeeSchema.setComCode(resultSet.getString("ComCode"));
          prpJplanFeeSchema.setAgentCode(resultSet.getString("AgentCode"));
          prpJplanFeeSchema.setHandler1Code(resultSet.getString("Handler1Code"));
          prpJplanFeeSchema.setPlanFee(""+resultSet.getDouble("PlanFee"));
          prpJplanFeeSchema.setRealPayRefFee(""+resultSet.getDouble("RealPayRefFee"));
          this.setArr(prpJplanFeeSchema);
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
     * 应收XX核销查询的清单 SangMingqian 20060213
     * @param iWherePart 查询条件
     * @param iDateWhere 限制实收日期的条件
     * @throws Exception
     */
    public void queryPremiumRefList(String iWherePart,String iDateWhere) throws Exception
    {
      String strSqlStatement = "select prpjplanfee.certitype,prpjplanfee.certino, prpjplanfee.policyno,prpjplanfee.payno,prpjplanfee.startdate,"
                               + " prpjplanfee.serialno,prpjplanfee.payrefreason,"
      	                       + " prpjplanfee.riskcode,prpjplanfee.insuredname,prpjplanfee.currency1,"
                               + " prpjplanfee.planfee as PlanFee,"
							   + " decode(b.payreffee,null,0,b.payreffee/b.exchangerate) as RealPayRefFee "
                               + " from prpjplanfee ,(select * from prpjpayrefrec where " + iDateWhere + ") b "
                               + " where prpjplanfee.certitype=b.certitype(+) "
                               + " and prpjplanfee.certino=b.certino(+) "
                               + " and prpjplanfee.serialno=b.serialno(+) "
                               + " and prpjplanfee.payrefreason=b.payrefreason(+) "
                               + " and " + iWherePart;
      strSqlStatement += " order by prpjplanfee.policyno,prpjplanfee.payno,prpjplanfee.certitype,prpjplanfee.certino,prpjplanfee.startdate ";

      this.initArr();
      DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
        PrpJplanFeeSchema prpJplanFeeSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJplanFeeSchema = new PrpJplanFeeSchema();
          prpJplanFeeSchema.setCertiType(resultSet.getString("CertiType"));
          prpJplanFeeSchema.setCertiNo(resultSet.getString("CertiNo"));
          prpJplanFeeSchema.setPolicyNo(resultSet.getString("PolicyNo"));
          prpJplanFeeSchema.setPayNo(resultSet.getString("PayNo"));
          prpJplanFeeSchema.setSerialNo(resultSet.getString("SerialNo"));
          prpJplanFeeSchema.setPayRefReason(resultSet.getString("PayRefReason"));
          prpJplanFeeSchema.setRiskCode(resultSet.getString("RiskCode"));
          prpJplanFeeSchema.setInsuredName(resultSet.getString("InsuredName"));
          prpJplanFeeSchema.setCurrency1(resultSet.getString("Currency1"));
          prpJplanFeeSchema.setPlanFee(""+resultSet.getDouble("PlanFee"));
          prpJplanFeeSchema.setRealPayRefFee(""+resultSet.getDouble("RealPayRefFee"));
          prpJplanFeeSchema.setStartDate(""+resultSet.getDate("StartDate"));
          this.setArr(prpJplanFeeSchema);
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
     * 应收XX核销查询清单（先汇总再核销）SangMingqian 20060612
     * @param iWherePart1 公共查询条件
     * @param iWherePart2 prpjpayrefrec的查询条件
     * @param iPowerWhere1 prpjplanfee的权限控制
     * @param iPowerWhere2 prpjpayrefrec的权限控制
     * @throws Exception
     */
    public void queryPremiumRefListNew(String iWherePart1,String iWherePart2,
    		                            String iPowerWhere1,String iPowerWhere2) throws Exception
    {
        String strSqlStatement = "";
    	strSqlStatement = " SELECT A.CertiType,A.CertiNo,A.PayRefReason,A.PolicyNo,A.SerialNo,A.PayNo,A.RiskCode,"
    		             + " A.InsuredName,A.Currency1,A.ComCode,A.Handler1Code,A.StartDate,A.UnderWriteDate,"
                         + " B.PayRefDate, DECODE(A.PlanFee,NULL,0,A.PlanFee) AS PlanFee,"
                         + " DECODE(B.RealPayRefFee,NULL,0,B.RealPayRefFee) AS RealPayRefFee "
                         + " FROM "
                         + "(SELECT CertiType,CertiNo,PayRefReason,SerialNo,PolicyNo,PayNo,RiskCode,InsuredName,"
						 + " Currency1,ComCode,Handler1Code,StartDate,UnderWriteDate,PlanFee "
                         + " FROM PrpjplanFee "
                         + " WHERE " + iWherePart1 + iPowerWhere1 + ") A, "
                         + "(SELECT CertiType,CertiNo,PayRefReason,SerialNo,PolicyNo,PayNo,RiskCode,InsuredName,"
						 + " Currency1,ComCode, Handler1Code,StartDate,UnderWriteDate,PayRefDate, "
						 + " DECODE(PayRefFee,NULL,0,PayRefFee/ExchangeRate) AS RealPayRefFee "
                         + " FROM PrpJpayRefRec "
                         + " WHERE " + iWherePart1 + " AND " + iWherePart2 + iPowerWhere2 
						 
						 + " AND PayRefTimes<1000 ) B "
                         + " WHERE A.CertiType=B.CertiType(+) AND A.CertiNo=B.CertiNo(+) "
                         + " AND A.PayRefReason=B.PayRefReason(+) AND A.SerialNo=B.SerialNo(+) ";
      strSqlStatement += " order by A.PolicyNo,A.PayNo,A.CertiType,A.CertiNo,A.StartDate ";

      this.initArr();
      DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
        PrpJplanFeeSchema prpJplanFeeSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJplanFeeSchema = new PrpJplanFeeSchema();
          prpJplanFeeSchema.setCertiType(resultSet.getString("CertiType"));
          prpJplanFeeSchema.setCertiNo(resultSet.getString("CertiNo"));
          prpJplanFeeSchema.setPolicyNo(resultSet.getString("PolicyNo"));
          prpJplanFeeSchema.setPayNo(resultSet.getString("PayNo"));
          prpJplanFeeSchema.setSerialNo(resultSet.getString("SerialNo"));
          prpJplanFeeSchema.setPayRefReason(resultSet.getString("PayRefReason"));
          prpJplanFeeSchema.setRiskCode(resultSet.getString("RiskCode"));
          prpJplanFeeSchema.setInsuredName(resultSet.getString("InsuredName"));
          prpJplanFeeSchema.setCurrency1(resultSet.getString("Currency1"));
          prpJplanFeeSchema.setPlanFee(""+resultSet.getDouble("PlanFee"));
          prpJplanFeeSchema.setRealPayRefFee(""+resultSet.getDouble("RealPayRefFee"));
          prpJplanFeeSchema.setStartDate(""+resultSet.getDate("StartDate"));
          this.setArr(prpJplanFeeSchema);
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
     * 应收XX核销查询（不包含已注销XX）SangMingqian 20060118
     * @param iWherePart
     * @param iOrderBy 排序项
     * @throws Exception
     */
    public void queryPremiumRefOff(String iWherePart,String iOrderBy,String iDateWhere) throws Exception
    {
      String strSqlStatement = "select prpjplanfee.Currency1,prpjplanfee.comcode, "
      	                       + " prpjplanfee.agentcode, "
							   + " prpjplanfee.handler1code,sum(prpjplanfee.planfee) as planfee, "
                               
							   + " sum(decode(b.payreffee,null,0,b.payreffee/b.exchangerate)) as RealPayRefFee "
                               + " from prpjplanfee ,(select * from prpjpayrefrec where " + iDateWhere +") b,prpcmain "
                               + " where prpjplanfee.policyno=prpcmain.policyno "
                               + " and substr(prpcmain.othflag,4,1)!='1' "
                               + " and prpjplanfee.certitype=b.certitype(+) "
                               + " and prpjplanfee.certino=b.certino(+) "
                               + " and prpjplanfee.serialno=b.serialno(+) "
                               + " and prpjplanfee.payrefreason=b.payrefreason(+) "
                               + " and " + iWherePart
                               + " group by prpjplanfee.Currency1,prpjplanfee.comcode,prpjplanfee.agentcode,"
							   + " prpjplanfee.handler1code ";

      this.initArr();
      DbPool dbpool = new DbPool();
      try {
        
        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        
        PrpJplanFeeSchema prpJplanFeeSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJplanFeeSchema = new PrpJplanFeeSchema();
          prpJplanFeeSchema.setCurrency1(resultSet.getString("Currency1"));
          prpJplanFeeSchema.setComCode(resultSet.getString("ComCode"));
          prpJplanFeeSchema.setAgentCode(resultSet.getString("AgentCode"));
          prpJplanFeeSchema.setHandler1Code(resultSet.getString("Handler1Code"));
          prpJplanFeeSchema.setPlanFee(""+resultSet.getDouble("PlanFee"));
          prpJplanFeeSchema.setRealPayRefFee(""+resultSet.getDouble("RealPayRefFee"));
          this.setArr(prpJplanFeeSchema);
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
     *对XX进行汇总金额 SangMingqian 20050630
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void querySum(String iWherePart) throws Exception
    {
      
      DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
        this.querySum(dbpool,iWherePart);
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
     *对XX进行汇总金额 lijibin 20061110 增加带dbpool的同名方法
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void querySum(DbPool dbpool,String iWherePart) throws Exception
    {
        
        
        String strSqlStatement = " SELECT sum(PlanFee) as PlanFee FROM PrpJplanFee WHERE " + iWherePart;
        
        this.initArr();
        PrpJplanFeeSchema prpJplanFeeSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
            prpJplanFeeSchema = new PrpJplanFeeSchema();
            
            
            prpJplanFeeSchema.setPlanFee(""+resultSet.getDouble("PlanFee"));
            this.setArr(prpJplanFeeSchema);
        }
        resultSet.close();
    }
    
    
    /**
     *对XX进行汇总金额 ZhangHuixin 20120413
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void querySumByPolicy(String iWherePart,String[] bindValues) throws Exception
    {
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        this.querySumByPolicy(dbpool,iWherePart,bindValues);
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
     *相同XX进行汇总金额 ZhangHuixin 20120413 增加带dbpool的同名方法
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void querySumByPolicy(DbPool dbpool,String iWherePart,String[] bindValues) throws Exception
    {
        String strSqlStatement = " SELECT sum(PlanFee) as PlanFee,policyno  FROM PrpJplanFee WHERE " + iWherePart;
        this.initArr();
        PrpJplanFeeSchema prpJplanFeeSchema = null;
		dbpool.prepareInnerStatement(strSqlStatement);
		if (bindValues != null && bindValues.length > 0) {
		    for (int i = 0; i < bindValues.length; i++) {
		        dbpool.setString(i + 1, bindValues[i]);
		    }
		}
		ResultSet resultSet = dbpool.executePreparedQuery();
        while(resultSet.next())
        {
            prpJplanFeeSchema = new PrpJplanFeeSchema();
            prpJplanFeeSchema.setPlanFee(""+resultSet.getDouble("PlanFee"));
            prpJplanFeeSchema.setPolicyNo(""+resultSet.getString("PolicyNo"));
            this.setArr(prpJplanFeeSchema);
        }
        resultSet.close();
        dbpool.closePreparedStatement();
    }
    /**
     *应付手续费核销查询 SangMingqian 20050715
     *@param iWherePart 查询条件(包括排序字句)
     *@param iOrderBy 排序项 SangMingqian 20051021 现场修改
     *@throws UserException
     *@throws Exception
     */
    public void queryRef(String iWherePart,String iOrderBy,String iDateWhere) throws Exception
    {
      
      
      
      
      
      
      
      
      
      String strSqlStatement = "select prpjplanfee.Currency1,prpjplanfee.comcode,prpjplanfee.agentcode,"
      	                       + " prpjplanfee.handler1code,"
                               + " sum(prpjplanfee.planfee) as PlanFee,"
                               
							   + " sum(decode(b.payreffee,null,0,b.payreffee/b.exchangerate)) as RealPayRefFee "
                               + " from prpjplanfee ,(select * from prpjpayrefrec where " + iDateWhere + ") b "
                               + " where prpjplanfee.certitype=b.certitype(+) "
                               + " and prpjplanfee.certino=b.certino(+) "
                               + " and prpjplanfee.serialno=b.serialno(+) "
                               + " and prpjplanfee.payrefreason=b.payrefreason(+) "
                               + " and " + iWherePart
                               
                               + " group by prpjplanfee.Currency1,prpjplanfee.comcode,prpjplanfee.agentcode,"
							   + "prpjplanfee.handler1code ";
      strSqlStatement += " order by " + iOrderBy;

      this.initArr();
      DbPool dbpool = new DbPool();
      try {
        
        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        
        PrpJplanFeeSchema prpJplanFeeSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJplanFeeSchema = new PrpJplanFeeSchema();
          prpJplanFeeSchema.setCurrency1(resultSet.getString("Currency1"));
          prpJplanFeeSchema.setComCode(resultSet.getString("ComCode"));
          prpJplanFeeSchema.setAgentCode(resultSet.getString("AgentCode"));
          prpJplanFeeSchema.setHandler1Code(resultSet.getString("Handler1Code"));
          prpJplanFeeSchema.setPlanFee(""+resultSet.getDouble("PlanFee"));
          prpJplanFeeSchema.setRealPayRefFee(""+resultSet.getDouble("RealPayRefFee"));
          this.setArr(prpJplanFeeSchema);
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
     * 应付赔款核销查询 SangMingqian 20051213
     * @param iWherePart
     * @param iOrderBy
     * @throws Exception
     */
    public void queryPay(String iWherePart,String iOrderBy,String iDateWhere) throws Exception
    {
      
      
      
      
      
      
      
      
      String strSqlStatement = "select prpjplanfee.Currency1,prpjplanfee.comcode,prpjplanfee.agentcode,"
                                 + " prpjplanfee.handler1code,"
                                 + " sum(prpjplanfee.planfee) as PlanFee,"
                                 
			                     + " sum(decode(b.payreffee,null,0,b.payreffee/b.exchangerate)) as RealPayRefFee "
                                 + " from prpjplanfee ,(select * from prpjpayrefrec where " + iDateWhere + ") b "
                                 + " where prpjplanfee.certitype=b.certitype(+) "
                                 + " and prpjplanfee.certino=b.certino(+) "
                                 + " and prpjplanfee.serialno=b.serialno(+) "
                                 + " and prpjplanfee.payrefreason=b.payrefreason(+) "
                                 + " and " + iWherePart
                                 
								 + " group by prpjplanfee.Currency1,prpjplanfee.comcode,prpjplanfee.agentcode,"
			                     + "prpjplanfee.handler1code ";
      strSqlStatement += " order by " + iOrderBy;

      this.initArr();
      DbPool dbpool = new DbPool();
      try {
        
        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        
        PrpJplanFeeSchema prpJplanFeeSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJplanFeeSchema = new PrpJplanFeeSchema();
          prpJplanFeeSchema.setCurrency1(resultSet.getString("Currency1"));
          prpJplanFeeSchema.setComCode(resultSet.getString("ComCode"));
          prpJplanFeeSchema.setAgentCode(resultSet.getString("AgentCode"));
          prpJplanFeeSchema.setHandler1Code(resultSet.getString("Handler1Code"));
          prpJplanFeeSchema.setPlanFee(""+resultSet.getDouble("PlanFee"));
          prpJplanFeeSchema.setRealPayRefFee(""+resultSet.getDouble("RealPayRefFee"));
          this.setArr(prpJplanFeeSchema);
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
     * 应付手续费核销查询（不包含已注销XX）SangMingqian 20051210
     * @param iWherePart
     * @param iOrderBy 排序项
     * @throws Exception
     */
    public void queryRefOff(String iWherePart,String iOrderBy,String iDateWhere) throws Exception
    {
      
      
      
      
      
      
	  
      
      
      String strSqlStatement = "select prpjplanfee.Currency1,prpjplanfee.comcode, "
      	                       + " prpjplanfee.agentcode,prpjplanfee.handler1code, "
							   + " sum(prpjplanfee.planfee) as planfee, "
                               
							   + " sum(decode(b.payreffee,null,0,b.payreffee/b.exchangerate)) as RealPayRefFee "
                               + " from prpjplanfee ,(select * from prpjpayrefrec where " + iDateWhere +") b,prpcmain "
                               + " where prpjplanfee.policyno=prpcmain.policyno "
                               + " and substr(prpcmain.othflag,4,1)!='1' "
                               + " and prpjplanfee.certitype=b.certitype(+) "
                               + " and prpjplanfee.certino=b.certino(+) "
                               + " and prpjplanfee.serialno=b.serialno(+) "
                               + " and prpjplanfee.payrefreason=b.payrefreason(+) "
                               + " and " + iWherePart
                               
                               + " group by prpjplanfee.Currency1,prpjplanfee.comcode,prpjplanfee.agentcode,"
							   + " prpjplanfee.handler1code ";

      this.initArr();
      DbPool dbpool = new DbPool();
      try {
        
        dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
        
        PrpJplanFeeSchema prpJplanFeeSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJplanFeeSchema = new PrpJplanFeeSchema();
          prpJplanFeeSchema.setCurrency1(resultSet.getString("Currency1"));
          prpJplanFeeSchema.setComCode(resultSet.getString("ComCode"));
          prpJplanFeeSchema.setAgentCode(resultSet.getString("AgentCode"));
          prpJplanFeeSchema.setHandler1Code(resultSet.getString("Handler1Code"));
          prpJplanFeeSchema.setPlanFee(""+resultSet.getDouble("PlanFee"));
          prpJplanFeeSchema.setRealPayRefFee(""+resultSet.getDouble("RealPayRefFee"));
          this.setArr(prpJplanFeeSchema);
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
     *自连接查询 SangMingqian 20050627
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryWithSelf(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      if (iLimitCount > 0 && dbPrpJplanFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefMain.queryWithSelf");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT distinct A.* FROM PrpJplanFee A,PrpJplanFee B "
            + "WHERE A.CertiNo=B.CertiNo and A.PolicyNo=B.PolicyNo and A.SerialNo=B.SerialNo AND " + iWherePart;
        schemas = dbPrpJplanFee.findByConditions(strSqlStatement);
      }
    }

    /**
     * 联合REC表查询 SangMingqian 20051111
     * @param iWherePart
     * @param iLimitCount
     * @throws UserException
     * @throws Exception
     */
    public void queryWithRec(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      if (iLimitCount > 0 && dbPrpJplanFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefMain.queryWithRec");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT distinct PrpJplanFee.* FROM PrpJplanFee ,PrpJpayRefRec "
            + "WHERE PrpJplanFee.CertiType=PrpJpayRefRec.CertiType "
            + " and PrpJplanFee.CertiNo=PrpJpayRefRec.CertiNo "
            + " and PrpJplanFee.SerialNo=PrpJpayRefRec.SerialNo "
            + " and PrpJplanFee.PayRefReason=PrpJpayRefRec.PayRefReason and " + iWherePart;
        schemas = dbPrpJplanFee.findByConditions(strSqlStatement);
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
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      if (iLimitCount > 0 && dbPrpJplanFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefMain.queryPrint");
      }else{
        initArr();
        
        
            
        	
            
        strSqlStatement = " SELECT PrpJplanFee.* FROM PrpJplanFee where"
        	+ iWherePart;
        
        schemas = dbPrpJplanFee.findByConditions(strSqlStatement);
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
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      if (iLimitCount > 0 && dbPrpJplanFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefMain.queryPrint");
      }else{
        initArr();
        strSqlStatement = " SELECT PrpJplanFee.* FROM PrpJplanFee where" + iWherePart;
        schemas = dbPrpJplanFee.findByConditions(strSqlStatement,bindValues);
      }
    }
    
    /**
     * 手续费支付单打印查询 SangMingqian 20060411
     * @param iWherePart PrpJplanFee的查询条件
     * @param iRecWherePart PrpJpayRefRec的查询条件
     * @param iOthWherePart 排序
     * @param iLimitCount
     * @throws UserException
     * @throws Exception
     */
    public void queryFeePrint(String iWherePart,String iRecWherePart,String iOthWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      if (iLimitCount > 0 && dbPrpJplanFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefMain.queryPrint");
      }else
      {
        initArr();
        
        
        
        
        
        
      
      strSqlStatement = " SELECT DISTINCT PrpJplanFee.* FROM PrpJplanFee ,PrpJpayRefRec "
      	+ "WHERE "
          + iWherePart
			+ " AND PrpJplanFee.CertiNo=PrpJPayRefRec.Certino(+) ";
      
        
        /*
        strSqlStatement = " SELECT DISTINCT PrpJplanFee.CERTITYPE,PrpJplanFee.CERTINO,PrpJplanFee.POLICYNO,PrpJplanFee.SERIALNO," 
        	+"PrpJplanFee.PAYREFREASON,PrpJplanFee.CLAIMNO,PrpJplanFee.CLASSCODE,PrpJplanFee.RISKCODE,PrpJplanFee.CONTRACTNO,"
            +"PrpJplanFee.APPLICODE,PrpJplanFee.APPLINAME,PrpJplanFee.INSUREDCODE,PrpJplanFee.INSUREDNAME," 
            +"PrpJplanFee.STARTDATE,PrpJplanFee.ENDDATE,PrpJplanFee.VALIDDATE,PrpJplanFee.PAYNO,"
            +"PrpJplanFee.CURRENCY1,PrpJplanFee.PLANFEE,PrpJplanFee.PLANDATE,PrpJplanFee.COMCODE," 
            +"PrpJplanFee.MAKECOM,PrpJplanFee.AGENTCODE,PrpJplanFee.HANDLER1CODE,PrpJplanFee.HANDLERCODE,"
            +"PrpJplanFee.UNDERWRITEDATE,PrpJplanFee.COINSFLAG,PrpJplanFee.COINSCODE,PrpJplanFee.COINSNAME," 
            +"PrpJplanFee.COINSTYPE,PrpJplanFee.CENTERCODE,PrpJplanFee.BRANCHCODE,"
            +"PrpJplanFee.ACCBOOKTYPE,PrpJplanFee.ACCBOOKCODE,PrpJplanFee.YEARMONTH,PrpJplanFee.VOUCHERNO," 
            +"PrpJplanFee.EXCHANGERATE,PrpJplanFee.PLANFEECNY,"
            +"PrpJplanFee.payreffee,"
            
            +"PrpJplanFee.REALPAYREFFEE,PrpJplanFee.FLAG,PrpJplanFee.CARNATURECODE,PrpJplanFee.USENATURECODE,PrpJplanFee.CARPROPERTY "      		
            +" FROM PrpJplanFee ,PrpCmain ,PrpJpayRefRec, PrpJplanFee a "
        	+ "WHERE PrpJplanFee.PolicyNo=PrpCmain.PolicyNo AND SUBSTR(PrpCmain.OthFlag,4,1)!='1' AND "
            + iWherePart
			+ " AND PrpJplanFee.CertiNo=PrpJPayRefRec.Certino(+) "
			+ " and a.CertiNo=PrpJplanFee.Certino and a.certitype in ('P','E') and a.PolicyNo=PrpJplanFee.PolicyNo ";   */     
        
        if(!iRecWherePart.equals("")){
        	
        	strSqlStatement += "AND PrpJpayRefRec.CertiType IN ('P','E') " + iRecWherePart;
        }
        strSqlStatement += iOthWherePart;
        schemas = dbPrpJplanFee.findByConditions(strSqlStatement);
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
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      if (iLimitCount > 0 && dbPrpJplanFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJplanFee.query");
      }else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJplanFee WHERE " + iWherePart;
        schemas = dbPrpJplanFee.findByConditions(dbpool,strSqlStatement);
      }
    }

    public void query(String iWherePart,int intPageNum,int intPageCount) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
        this.query(dbpool,iWherePart,intPageNum,intPageCount);
      }
      catch (Exception e)
      {
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    public void query(DbPool dbpool,String iWherePart,int intPageNum,int intPageCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      int intStartNum = 0;
      int intEndNum = 0;

      intStartNum = (intPageNum - 1) * intPageCount;
      intEndNum = intPageNum * intPageCount;

      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      initArr();

      strSqlStatement = " SELECT * FROM ( " +
         "Select RowNum As LineNum,T.* From ( " +
         "Select * From PrpJplanFee Where " + iWherePart +
         ") T Where RowNum<=" + intEndNum + ") Where LineNum>" +
         intStartNum + " Order By PolicyNo DESC";

      schemas = dbPrpJplanFee.findByConditions(dbpool,strSqlStatement);
    }
    
    /**
     * 按照查询条件得到记录，并将这组记录赋给HashMap
     * @param claimNo 查询参数
     * @return
     * @throws Exception 
     * @throws UserException 
     * @throws ClassNotFoundException 
     */
    public Vector queryClaim(String claimNo) throws ClassNotFoundException, UserException, Exception{
    	Vector result = null;
    	DbPool dbpool = new DbPool();
    	try{
        	
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
        	result = this.queryClaim(dbpool,claimNo);
    	}catch(Exception e){
    		e.printStackTrace();
    		throw e;
    	}finally {
    		dbpool.close();
    	}
    	return result;
    }
    
    /**
     * 按照查询条件得到记录，并将这组记录赋给HashMap
     * @param claimNo 查询参数
     * @param dbpool
     * @return
     * @throws SQLException
     */
    public Vector queryClaim(DbPool dbpool,String claimNo) throws SQLException{
    	ResultSet rs = null;
    	HashMap record = null;
    	Vector result = new Vector();
    	
    	String sql = "select a.claimno,a.losstype,(select itemname from d_codeitem b where"
    		+ " codeno='209' and b.itemcode=trim(a.losstype)) as damagereason,"
    		+ "a.damageplace,a.damagedate,caseno from c_claim a"
    		+ " where a.openno='" + claimNo +"'";
    	rs = dbpool.query(sql);
    	while(rs.next()){
    		record = new HashMap();
    		record.put("CLAIMNO", rs.getString("CLAIMNO"));
    		record.put("LOSSTYPE", rs.getString("LOSSTYPE"));
    		record.put("DAMAGEREASON", rs.getString("DAMAGEREASON"));
    		record.put("DAMAGEPLACE", rs.getString("DAMAGEPLACE"));
    		record.put("DAMAGEDATE", rs.getDate("DAMAGEDATE"));
    		record.put("CASENO", rs.getString("CASENO"));
    		result.add(record);
    	}
    	rs.close();
    	return result;
    }
    
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpJplanFee.setSchema((PrpJplanFeeSchema)schemas.get(i));
        dbPrpJplanFee.insert(dbpool);
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
     * @author lijibin 20050608 从业务转入应收应付信息到PrpJplanFee表
     * @param iCertiType 业务类型
     * @param iCertiNo 业务号
     * @throws UserException
     * @throws Exception
     */
    public void transData(String iCertiType,String iCertiNo) throws UserException,Exception
    {

      DbPool dbpool = new DbPool();
      try 
      {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
          dbpool.beginTransaction();
          this.transData(dbpool, iCertiType, iCertiNo);
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
     * @author lijibin 20050608 从业务转入应收应付信息到PrpJplanFee表
     * @param dbpool 数据库连接池
     * @param iCertiType 业务类型
     * @param iCertiNo 业务号
     * @throws UserException
     * @throws Exception
     */
    public void transData(DbPool dbpool,String iCertiType,String iCertiNo) throws UserException,Exception
    {
      
      if(true)
        throw new UserException(-98,-1167,"BLPrpJplanFee.transData","该方法已被删除！");
      
      
      if(iCertiType.equals("P"))
        this.transPolicy(dbpool,iCertiNo);
      
      else if(iCertiType.equals("E"))
      	this.transEndor(dbpool,iCertiNo);
      
      else if(iCertiType.equals("Y"))
        this.transPrepay(dbpool,iCertiNo);
      
      else if(iCertiType.equals("C"))
        this.transCompensate(dbpool,iCertiNo);
      


      else
        throw new UserException(-98,-1167,"BLPrpJplanFee.transData","没有此业务类型："+iCertiType);

      
      this.save(dbpool);
    }
    
    
   
    /**
     * @author lijibin 20050608 转入XX应收费信息到应收应付表
     * @param dbpool
     * @param iCertiNo
     * @throws UserException
     * @throws Exception
     */
    
    public void transPolicy(DbPool dbpool,String iPolicyNo) throws UserException,Exception
    {
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      DBPrpDcode dbPrpDcode = new DBPrpDcode();
      BLPrpCplan blPrpCplan = new BLPrpCplan();
      BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
      BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
      String strWherePart = "";
      int intReturn = 0;
      double dbSumPremium = 0;
      boolean blFlag = false;
      intReturn = dbPrpCmain.getInfo(dbpool,iPolicyNo);
      if(intReturn == 100)
        throw new UserException( -98, -1167, "BLPrpJplanFee.transPolicy", "无此XX信息："+iPolicyNo);
      
      
      String allowanceRiskFlag = SysConfig.getProperty("AllowanceRisk");
      if(allowanceRiskFlag.indexOf(dbPrpCmain.getRiskCode()) >= 0)
      {
    	  if(dbPrpCmain.getAgriNature().equals("1"))
    	  {
    		  DBPrpCsubsidy dbPrpCsubsidy = new DBPrpCsubsidy();
    		  int count = dbPrpCsubsidy.getInfo(dbpool,dbPrpCmain.getPolicyNo(),"1");
    		  if(count == 100)
    		  {
    			  throw new UserException( -98, -1167, "BLPrpJplanFee.transPolicy", iPolicyNo+"无此补贴信息：");
    		  }else
    		  {
    			  dbPrpCmain.setAgriNature(dbPrpCsubsidy.getAgriPolicySort());
    		  }
    	  }else if(dbPrpCmain.getAgriNature().equals("2")){
    		  dbPrpCmain.setAgriNature("3");
    	  }
      }
      

      
      
      String iRiskCode = this.getMainRiskNo(dbpool,dbPrpCmain);
      
      
      int count = 1;
      String splitFlag = "0";
      BLPrpCitemKind blPrpCitemKind = new BLPrpCitemKind();
      if(SysConfig.getProperty("SplitRisk").indexOf(dbPrpCmain.getRiskCode()) >=0 )
      {
    	  splitFlag = "1";
      }
      if(splitFlag.equals("1"))
      {
    	  blPrpCitemKind = this.findRiskKindCodeByPolicyNo(dbpool,dbPrpCmain.getPolicyNo());
      }
      
      
      
      String centercode = blPrpDcompany.getCenterCodeNew(dbpool,dbPrpCmain.getComCode());

      
      
      String poaType = "0";
      String poaCode = "";
      String poaDate = "";
      String proposalNo = dbPrpCmain.getProposalNo();
      DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
      
      int preReturn = dbPrpJplanFeePre.getInfo(proposalNo, "0");
      if (preReturn == 0) {
			poaType = dbPrpJplanFeePre.getPoaType();
			poaCode = dbPrpJplanFeePre.getPoaCode();
			poaDate = dbPrpJplanFeePre.getOperateDate();
			
			dbPrpJplanFeePre.setPolicyNo(iPolicyNo);
			dbPrpJplanFeePre.setCertiStatus("3");
			dbPrpJplanFeePre.update(dbpool);
      }else{
    	  
    	  proposalNo = "";
      }
      
      strWherePart = "PolicyNo='"+iPolicyNo+"' AND (EndorseNo IS NULL OR EndorseNo='')";
      blPrpCplan.query(dbpool,strWherePart,0);
      PrpJplanFeeSchema schema = null;
      for(int i=0; i<blPrpCplan.getSize(); i++)
      {
        dbSumPremium += Str.round(Double.parseDouble(blPrpCplan.getArr(i).getPlanFee()),2);
      }
      int intFCount = 0; 
      for(int i=0; i<blPrpCplan.getSize(); i++)
      {
        schema = new PrpJplanFeeSchema();
        schema.setCertiType("P");
        schema.setCertiNo(iPolicyNo);
        schema.setSerialNo(blPrpCplan.getArr(i).getSerialNo());
        schema.setPolicyNo(iPolicyNo);
        schema.setPayRefReason(blPrpCplan.getArr(i).getPayReason());
        
        
        


        
        
        schema.setClassCode(dbPrpCmain.getClassCode());
        schema.setRiskCode(dbPrpCmain.getRiskCode());
        schema.setContractNo(dbPrpCmain.getContractNo());
        schema.setAppliCode(dbPrpCmain.getAppliCode());
        schema.setAppliName(dbPrpCmain.getAppliName());
        schema.setInsuredCode(dbPrpCmain.getInsuredCode());
        schema.setInsuredName(dbPrpCmain.getInsuredName());
        schema.setStartDate(dbPrpCmain.getStartDate());
        schema.setEndDate(dbPrpCmain.getEndDate());
        
        schema.setPayNo(blPrpCplan.getArr(i).getPayNo());
        schema.setCurrency1(blPrpCplan.getArr(i).getCurrency());
        schema.setPlanFee(blPrpCplan.getArr(i).getPlanFee());
        schema.setPlanDate(blPrpCplan.getArr(i).getPlanDate());
        schema.setComCode(dbPrpCmain.getComCode());
        schema.setMakeCom(dbPrpCmain.getMakeCom());
        schema.setAgentCode(dbPrpCmain.getAgentCode());
        schema.setHandler1Code(dbPrpCmain.getHandler1Code());
        schema.setHandlerCode(dbPrpCmain.getHandlerCode());
        
        schema.setUnderWriteDate(DateTime.current().toString().substring(0,10)); 
        schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
        
        
        
        schema.setCenterCode(centercode);
        schema.setBranchCode("");
        schema.setAccBookType("");
        schema.setAccBookCode("");
        schema.setYearMonth("");
        
        schema.setVoucherNo("0");
        
        schema.setExchangeRate("1.0");
        schema.setPlanFeeCNY("0");
        schema.setPayRefFee("0");
        schema.setRealPayRefFee("0");
        
        schema.setBusinessNature(dbPrpCmain.getBusinessNature());
        
        
        
        String carNatureCode = "";
        if(allowanceRiskFlag.indexOf(dbPrpCmain.getRiskCode()) >= 0)
        {
        	
        	carNatureCode = dbPrpCmain.getAgriNature();
        	if (blPrpCplan.getArr(i).getPayReason().equals("R10")) {
                if (carNatureCode.equals("3"))
                    carNatureCode += "1";
                 else
                    carNatureCode += "4";
        	}else if(blPrpCplan.getArr(i).getPayReason().equals("R14"))
        	{
        		carNatureCode += "1";
        	}else if(blPrpCplan.getArr(i).getPayReason().equals("R15"))
        	{
        		carNatureCode += "2";
        	}else if(blPrpCplan.getArr(i).getPayReason().equals("R16"))
        	{
        		carNatureCode += "3";
        	}else if(blPrpCplan.getArr(i).getPayReason().equals("R17"))
        	{
        		carNatureCode += "9";
        	}
        }
        schema.setCarNatureCode(carNatureCode);
        
        
        
        
        schema.setPoaType(poaType);
        schema.setPoaCode(poaCode);
        schema.setPoaDate(poaDate);
        schema.setProposalNo(proposalNo);
        
        if("1".equals(dbPrpCmain.getAllinsFlag())){
        	schema.setFlag("01");
        } else {
        	schema.setFlag("00");
        }
       
       if(iRiskCode != null && iRiskCode != "" && (iRiskCode.substring(0,2).equals("30") || iRiskCode.equals("2700"))){
        	schema.setFlag(schema.getFlag()+"1");
        	schema.setFlag1(iRiskCode);
        }else{ 
        	schema.setFlag(schema.getFlag()+"0");
        }
        
        
        
        
        
        if(dbPrpCmain.getRiskCode().equals("0507")){
            this.getCarNature(dbpool,schema);
            
            this.logPrintln("kai shi zhuan ru bao dan hao "+schema.getCertiNo()+" de che chuan shui");
            String[] strTaxPayer = transCarShipTax(dbpool,schema,dbPrpCmain);
            schema.setTaxSettleFlag("0");
            schema.setTaxpayerCode(strTaxPayer[0]);
            schema.setTaxpayerName(strTaxPayer[1]);
            schema.setIdentifyNumber(strTaxPayer[2]);
            
        }
        
        if(dbPrpCmain.getCoinsFlag().equals("1")||dbPrpCmain.getCoinsFlag().equals("3")){
          if(i==blPrpCplan.getSize()-1)
            blFlag = true;
          else
            blFlag = false;
          strWherePart = " PolicyNo='"+iPolicyNo+"'";
          this.transCoinsDetail(dbpool,strWherePart,schema,blPrpJplanCommission,dbSumPremium,"POLICY",blFlag);
        }else{
            
        	if(splitFlag.equals("1"))
        	{
        		double riskKindCodeFee = 0;
        		if(i == blPrpCplan.getSize()-1)
        		{
        			for(int j = 0;j < blPrpCitemKind.getSize();j++)
        			{
        				PrpJplanFeeSchema schemaRiskKindCode = new PrpJplanFeeSchema();
        				schemaRiskKindCode.setSchema(schema);
        				riskKindCodeFee = 0;
        				int result = dbPrpDcode.getInfo(dbpool,"RiskArticle",dbPrpCmain.getRiskCode()+blPrpCitemKind.getArr(j).getKindCode());
        				if(result == 100)
        				{
        					throw new UserException( -98, -1167, "BLPrpJplanFee.transPolicy", "XXXXX别没有对应XXXXX核算专项");
        				}
        				String carNatureCodeTmp = dbPrpDcode.getNewCodeCode();
        				for(int n = 0;n < this.getSize();n++)
        				{
        					if(carNatureCodeTmp.equals(this.getArr(n).getCarNatureCode()))
        					{
        						riskKindCodeFee += Double.parseDouble(this.getArr(n).getPlanFee());
        					}
        				}
        				riskKindCodeFee = Str.round(Str.round(riskKindCodeFee,8),2);
        				schemaRiskKindCode.setSerialNo(""+count);
        				count++;
        				schemaRiskKindCode.setCarNatureCode(carNatureCodeTmp);
        				schemaRiskKindCode.setPlanFee(""+(Double.parseDouble(blPrpCitemKind.getArr(j).getPremium())
        						- riskKindCodeFee));
        				schemaRiskKindCode.setPayNo(blPrpCplan.getArr(i).getSerialNo());
        				
        				if(Double.parseDouble(schemaRiskKindCode.getPlanFee()) != 0)
        				{
        					this.setArr(schemaRiskKindCode);
        				}else
        				{
        					count--;
        				}
        				
        			}
        		}else
        		{
        			riskKindCodeFee = 0;
        			for(int j = 0;j < blPrpCitemKind.getSize();j++)
        			{
        				PrpJplanFeeSchema schemaRiskKindCode = new PrpJplanFeeSchema();
        				int result = dbPrpDcode.getInfo(dbpool,"RiskArticle",dbPrpCmain.getRiskCode()+blPrpCitemKind.getArr(j).getKindCode());
        				if(result == 100)
        				{
        					throw new UserException( -98, -1167, "BLPrpJplanFee.transPolicy", "XXXXX别没有对应XXXXX核算专项");
        				}
        				String carNatureCodeTmp = dbPrpDcode.getNewCodeCode();
        				schemaRiskKindCode.setSchema(schema);
        				schemaRiskKindCode.setSerialNo(""+count);
        				count++;
        				schemaRiskKindCode.setCarNatureCode(carNatureCodeTmp);
        				schemaRiskKindCode.setPayNo(blPrpCplan.getArr(i).getSerialNo());
        				double planFee = 0;
        				planFee = Str.round(Double.parseDouble(blPrpCplan.getArr(i).getPlanFee())
        					*Double.parseDouble(blPrpCitemKind.getArr(j).getPremium())
        					/dbSumPremium,2);
        				if(j == blPrpCitemKind.getSize()-1)
        				{
        					schemaRiskKindCode.setPlanFee(""+(Double.parseDouble(blPrpCplan.getArr(i).getPlanFee())
            						- riskKindCodeFee));
        				}else
        				{
        					schemaRiskKindCode.setPlanFee(""+planFee);
        					riskKindCodeFee = riskKindCodeFee + planFee;
        				}
        				
        				if(Double.parseDouble(schemaRiskKindCode.getPlanFee()) != 0)
        				{
        					this.setArr(schemaRiskKindCode);
        				}else
        				{
        					count--;
        				}
        				
        			}
        		}
        	}else
        	{
        		this.setArr(schema);
        	}
            
        }

        if(dbPrpCmain.getCoinsFlag().equals("2")&&
            i==blPrpCplan.getSize()-1)
        {
          BLPrpCcoins blPrpCcoins = new BLPrpCcoins();
          BLPrpCcoinsDetail blPrpCcoinsDetail = new BLPrpCcoinsDetail();
          strWherePart = " PolicyNo='"+iPolicyNo+"' And CoinsType='1'";
          blPrpCcoins.query(dbpool,strWherePart);
          if(blPrpCcoins.getSize()>0)
          {
            strWherePart = " PolicyNo='"+iPolicyNo+"' And SerialNo="
                         + blPrpCcoins.getArr(0).getSerialNo()
                         + " And OperateFee!=0";
            blPrpCcoinsDetail.query(dbpool,strWherePart);
            if(blPrpCcoinsDetail.getSize()>0)
            {
              schema = new PrpJplanFeeSchema();
              schema.setCertiType("F");
              schema.setCertiNo(iPolicyNo);
              schema.setSerialNo(blPrpCplan.getArr(i).getSerialNo());
              schema.setPolicyNo(iPolicyNo);
              schema.setPayRefReason("P96");
              schema.setClassCode(dbPrpCmain.getClassCode());
              schema.setRiskCode(dbPrpCmain.getRiskCode());
              schema.setContractNo(dbPrpCmain.getContractNo());
              schema.setAppliCode(dbPrpCmain.getAppliCode());
              schema.setAppliName(dbPrpCmain.getAppliName());
              schema.setInsuredCode(dbPrpCmain.getInsuredCode());
              schema.setInsuredName(dbPrpCmain.getInsuredName());
              schema.setStartDate(dbPrpCmain.getStartDate());
              schema.setEndDate(dbPrpCmain.getEndDate());
              
              schema.setPayNo(blPrpCplan.getArr(i).getPayNo());
              schema.setCurrency1(blPrpCplan.getArr(i).getCurrency());
              schema.setPlanFee(blPrpCcoinsDetail.getArr(0).getOperateFee());
              schema.setPlanDate(blPrpCplan.getArr(i).getPlanDate());
              schema.setComCode(dbPrpCmain.getComCode());
              schema.setMakeCom(dbPrpCmain.getMakeCom());
              schema.setAgentCode(dbPrpCmain.getAgentCode());
              schema.setHandler1Code(dbPrpCmain.getHandler1Code());
              schema.setHandlerCode(dbPrpCmain.getHandlerCode());
              schema.setUnderWriteDate(DateTime.current().toString().substring(0,10)); 
              schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
              schema.setCoinsCode(blPrpCcoins.getArr(0).getCoinsCode());
              schema.setCoinsName(blPrpCcoins.getArr(0).getCoinsName());
              schema.setCoinsType(blPrpCcoins.getArr(0).getCoinsType());
              schema.setCenterCode(centercode);
              schema.setBranchCode("");
              schema.setAccBookType("");
              schema.setAccBookCode("");
              schema.setYearMonth("");
              
              schema.setVoucherNo("0");
              
              schema.setExchangeRate("1.0");
              schema.setPlanFeeCNY("0");
              schema.setPayRefFee("0");
              schema.setRealPayRefFee("0");
              
              schema.setBusinessNature(dbPrpCmain.getBusinessNature());
              
              
              
              schema.setPoaType(poaType);
              schema.setPoaCode(poaCode);
              schema.setPoaDate(poaDate);
              schema.setProposalNo(proposalNo);
              
              if("1".equals(dbPrpCmain.getAllinsFlag())){
              	schema.setFlag("01");
              }else{
              	schema.setFlag("00");
              }
              
              if(iRiskCode != null && iRiskCode != "" && (iRiskCode.substring(0,2).equals("30") || iRiskCode.equals("2700"))){
            	  schema.setFlag(schema.getFlag()+"1");
            	  schema.setFlag1(iRiskCode);
              }else{
            	  schema.setFlag(schema.getFlag()+"0");
              }
              
              
              if(dbPrpCmain.getRiskCode().equals("0507")){
                this.getCarNature(dbpool,schema);
              }
              this.setArr(schema);
              intFCount++;
            }
          }
        }
      }

      if(!dbPrpCmain.getCoinsFlag().equals("1")&&!dbPrpCmain.getCoinsFlag().equals("3"))
      {
    	  
        
          int intCount=0;
          BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
    	for (int k=0;k<this.getSize();k++)
    	{
    		if((!this.getArr(k).getPayRefReason().equals("R72"))&&
    		   (!this.getArr(k).getPayRefReason().equals("R73"))&&
    		   (!this.getArr(k).getPayRefReason().equals("R74"))&&
    		   (!this.getArr(k).getPayRefReason().equals("P96")))
    		{
    			blPrpJplanFee.setArr(this.getArr(k));
    			intCount++;
    		}
    	}
        



        
        strWherePart = " CertiNo='"+iPolicyNo+"'";
        
        BLPrpJplanCommission  bPrpJplanCommission = new  BLPrpJplanCommission();
        bPrpJplanCommission.transCommission(dbpool,strWherePart,blPrpJplanFee);
        
        

        
        strWherePart = " CertiNo='"+iPolicyNo+"'";
        this.transDisPremium(dbpool,strWherePart,intCount);
      }

      





      
      if(dbPrpCmain.getRiskCode().equals("0507"))
      {
        BLPrpCexpense blPrpCexpense = new BLPrpCexpense();
        blPrpCexpense.query(dbpool," PolicyNo='"+iPolicyNo+"'");
        
        if(blPrpCexpense.getSize()>0 ){
          if(Double.parseDouble(Str.chgStrZero(blPrpCexpense.getArr(0).getSalvationFee()))!=0){
            schema = new PrpJplanFeeSchema();
            schema.setSchema(this.getArr(0));
            schema.setCertiType("J");
            schema.setPayRefReason("R12"); 
            schema.setCurrency1(blPrpCexpense.getArr(0).getCurrency());
            schema.setPlanFee(blPrpCexpense.getArr(0).getSalvationFee());
            this.setArr(schema);
          }
        }
      }
      blPrpJplanCommission.save(dbpool);
    }   
    
    /**
     * @author lijibin add 20051216 判断是否预约协议收费
     * @param dbpool
     * @param DBPrpCmain
     * @throws UserException
     * @throws Exception
     */
    public boolean isMainPolicySff(DbPool dbpool,DBPrpCmain iDBPrpCmain) throws UserException,Exception
    {
      boolean blnReturn = false;
      String strSQL = "";
      if(iDBPrpCmain.getClassCode().equals("09")||iDBPrpCmain.getClassCode().equals("10"))
      {
        strSQL = "SELECT * FROM PrpCmainSub WHERE PolicyNo='"+iDBPrpCmain.getPolicyNo()+"'";
        ResultSet rs=dbpool.query(strSQL);
        if(rs.next())
          iDBPrpCmain.setContractNo(rs.getString("MainPolicyNo"));
        rs.close();

        
        if(!iDBPrpCmain.getContractNo().equals("")){
          double dblFeeMain = 0;
         double dblFeeSub = 0;
          
          strSQL = "SELECT SUM(PayRefFee) FROM PrpJpayRefRec WHERE PolicyNo='"
             + iDBPrpCmain.getContractNo() + "' AND CertiType IN ('P','E')";
          rs = dbpool.query(strSQL);
          if(rs.next())
            dblFeeMain = rs.getDouble(1);
          rs.close();
          if(dblFeeMain>0){
            strSQL = "SELECT SUM(PlanFee) FROM PrpJpayRefRec WHERE ContractNo='" +
                iDBPrpCmain.getContractNo() + "' AND CertiType IN ('P','E') AND PayRefReason='R00'";
            rs = dbpool.query(strSQL);
            if(rs.next())
              dblFeeSub = rs.getDouble(1);
            rs.close();
            if(dblFeeMain-dblFeeSub>0)
             blnReturn = true;
          }
        }
      }
      return blnReturn;
    }

    /**
     * @author zhanglingjian add 20080908 根据XX号得到其大XX的XXXXX种号
     * @param dbpool
     * @param DBPrpCmain
     * @throws UserException
     * @throws Exception
     */
    public String getMainRiskNo(DbPool dbpool,DBPrpCmain iDBPrpCmain) throws UserException,Exception
    {
      String strRiskCode = "";
      String strSQL = "";
      if(iDBPrpCmain.getClassCode().equals("09")
    		  ||iDBPrpCmain.getClassCode().equals("10")
    		  ||iDBPrpCmain.getClassCode().equals("27")
    		  ||iDBPrpCmain.getClassCode().equals("03")
    		  ||iDBPrpCmain.getClassCode().equals("01")
    		  ||iDBPrpCmain.getClassCode().equals("09"))
      {
        strSQL = "SELECT * FROM PrpCmainSub WHERE PolicyNo='"+iDBPrpCmain.getPolicyNo()+"'";
        ResultSet rs=dbpool.query(strSQL);
        if(rs.next())
        	iDBPrpCmain.setContractNo(rs.getString("MainPolicyNo"));
        rs.close();
      }
      if((iDBPrpCmain.getContractNo() != null && iDBPrpCmain.getContractNo() != "") 
     		 && (iDBPrpCmain.getClassCode().equals("27") || iDBPrpCmain.getClassCode().equals("03")
     		   ||iDBPrpCmain.getClassCode().equals("01") || iDBPrpCmain.getClassCode().equals("09")))
      {
         strSQL = "SELECT * FROM PrpCMain WHERE PolicyNo='" +iDBPrpCmain.getContractNo()+"'";
         ResultSet  rs = dbpool.query(strSQL);
         if(rs.next())
        	 strRiskCode = (String)rs.getString("RiskCode");
         rs.close();
            
      }
      return strRiskCode;
    }
    
    /**
     * @author lijibin 20050609 转入批单应收应付费信息到应收应付表
     * @param dbpool
     * @param iCertiNo
     * @throws UserException
     * @throws Exception
     */
    
    
    public void transEndor(DbPool dbpool,String iEndorseNo) throws UserException,Exception
    {
      DBPrpPhead dbPrpPhead = new DBPrpPhead();
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      DBPrpDcode dbPrpDcode = new DBPrpDcode();
      BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
      BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
      BLPrpPfee blPrpPfee = new BLPrpPfee();
      String strWherePart = "";
      int intReturn = 0;
      intReturn = dbPrpPhead.getInfo(dbpool,iEndorseNo);
      
      if(dbPrpPhead.getRiskCode().equals("0507")&&dbPrpPhead.getEndorType().indexOf("40")>=0){
      	this.transEndorCar(dbpool,dbPrpPhead); 
      }
      
      if(intReturn == 100)
        throw new UserException( -98, -1167, "BLPrpJplanFee.transEndor", "无此批单信息："+iEndorseNo);
      intReturn = dbPrpCmain.getInfo(dbpool,dbPrpPhead.getPolicyNo());
      
      
      String centercode = blPrpDcompany.getCenterCodeNew(dbpool,dbPrpCmain.getComCode());
      
      
      String poaType = "0";
      String poaCode = "";
      String poaDate = "";
      DBPrpJplanFeePre dbPrpJplanFeePre = new DBPrpJplanFeePre();
      
      int preReturn = dbPrpJplanFeePre.getInfo(iEndorseNo, "0");
      if (preReturn == 0) {
			poaType = dbPrpJplanFeePre.getPoaType();
			poaCode = dbPrpJplanFeePre.getPoaCode();
			poaDate = dbPrpJplanFeePre.getOperateDate();
			dbPrpJplanFeePre.setCertiStatus("3");
			dbPrpJplanFeePre.update(dbpool);
      }
      
      
      
      
      String iRiskCode = this.getMainRiskNo(dbpool, dbPrpCmain);
      
      
      
      BLPrpPitemKind blPrpPitemKind = new BLPrpPitemKind();
      int count = 1;
      String splitFlag = "0";
      if(SysConfig.getProperty("SplitRisk").indexOf(dbPrpCmain.getRiskCode()) >=0 )
      {
    	  splitFlag = "1";
      }
      if(splitFlag.equals("1"))
      {
    	  blPrpPitemKind = this.findRiskKindCodeByEndorseNo(dbpool,iEndorseNo);
      }
      
      
      
      strWherePart = "EndorseNo='"+iEndorseNo+"' AND ChgPremium!=0";
      blPrpPfee.querySumByCurrency1(dbpool,strWherePart);
      if(blPrpPfee.getSize()>1)
        throw new Exception("存在多个支付币别！");
      PrpJplanFeeSchema schema = null;
      int intFCount = 0; 
      
      this.initArr();
      if(blPrpPfee.getSize() == 0 && dbPrpPhead.getRiskCode().equals("0507"))
      {
    	  BLPrpJtaxSettle blPrpJtaxSettle = new BLPrpJtaxSettle();
    	  DBPrpCcarshipTax dbPrpCcarshipTax = new DBPrpCcarshipTax();
    	  BLPrpPcarshipTax blPrpPcarshipTax = new BLPrpPcarshipTax();
    	  DBPrpCitemCar dbPrpCitemCar = new DBPrpCitemCar();
    	  DBPrpCinsured dbPrpCinsured = new DBPrpCinsured();
    	  
    	  dbPrpCitemCar.getInfo(dbpool,dbPrpPhead.getPolicyNo(),"1");
    	  
    	  String strSQl = " EndorseNo='"+iEndorseNo+"'";
    	  blPrpPcarshipTax.query(dbpool,strSQl);
    	  if(blPrpPcarshipTax.getSize() != 0)
    	  {
    		  dbPrpCcarshipTax.getInfo(dbpool,blPrpPcarshipTax.getArr(0).getPolicyNo(),blPrpPcarshipTax.getArr(0).getSerialNo());
        	  dbPrpCinsured.getInfo(dbpool,blPrpPcarshipTax.getArr(0).getPolicyNo(),
        			  blPrpPcarshipTax.getArr(0).getSerialNo());
    		  schema = new PrpJplanFeeSchema();
    		  schema.setCertiType("E");
              schema.setCertiNo(iEndorseNo);
              schema.setSerialNo(blPrpPcarshipTax.getArr(0).getSerialNo());
              schema.setPolicyNo(dbPrpPhead.getPolicyNo());
              schema.setClassCode(dbPrpCmain.getClassCode());
              schema.setRiskCode(dbPrpCmain.getRiskCode());
              schema.setContractNo(dbPrpCmain.getContractNo());
              schema.setAppliCode(dbPrpCmain.getAppliCode());
              schema.setAppliName(dbPrpCmain.getAppliName());
              schema.setInsuredCode(dbPrpCmain.getInsuredCode());
              schema.setInsuredName(dbPrpCmain.getInsuredName());
              schema.setStartDate(dbPrpPhead.getValidDate());
              schema.setEndDate(dbPrpCmain.getEndDate());
              schema.setValidDate(dbPrpPhead.getValidDate());
              schema.setPayNo("1");
              schema.setCurrency1("CNY");
              schema.setPlanDate(dbPrpPhead.getValidDate());
              schema.setComCode(dbPrpCmain.getComCode());
              schema.setMakeCom(dbPrpCmain.getMakeCom());
              schema.setAgentCode(dbPrpCmain.getAgentCode());
              schema.setHandler1Code(dbPrpCmain.getHandler1Code());
              schema.setHandlerCode(dbPrpCmain.getHandlerCode());
              schema.setUnderWriteDate(dbPrpPhead.getUnderWriteEndDate());
              schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
              schema.setCenterCode(centercode);
              schema.setBranchCode("");
              schema.setAccBookType("");
              schema.setAccBookCode("");
              schema.setYearMonth("");
              schema.setVoucherNo("0");
              schema.setExchangeRate("1.0");
              schema.setPlanFeeCNY("0");
              schema.setPayRefFee("0");
              schema.setRealPayRefFee("0");
              
              schema.setBusinessNature(dbPrpCmain.getBusinessNature());
              
              schema.setPoaType(poaType);
              schema.setPoaCode(poaCode);
              schema.setPoaDate(poaDate);
              
      		  if("19".equals(dbPrpPhead.getEndorType())){
      			  schema.setFlag("1");
      		  }else{
      			  schema.setFlag("0");
      		  }
              if("1".equals(dbPrpCmain.getAllinsFlag())){
              	  schema.setFlag(schema.getFlag()+"1");
              }
              else{
              	  schema.setFlag(schema.getFlag()+"0");
              }
              
              if(iRiskCode != null && iRiskCode != "" && (iRiskCode.substring(0,2).equals("30") || iRiskCode.equals("2700"))){
            	  schema.setFlag(schema.getFlag()+"1");
            	  schema.setFlag1(iRiskCode);
              }else{
            	  schema.setFlag(schema.getFlag()+"0");
              }
              
              schema.setTaxSettleFlag("0");
              schema.setTaxpayerCode(dbPrpCcarshipTax.getTaxpayerCode());
              schema.setTaxpayerName(dbPrpCcarshipTax.getTaxpayerName());
              schema.setIdentifyNumber(dbPrpCcarshipTax.getIdentifyNumber());
              
              this.getCarNature(dbpool,schema);
              
              PrpJplanFeeSchema prpJplanFeeSchema = new PrpJplanFeeSchema();
              if((blPrpPcarshipTax.getArr(0).getChgTaxActual()!=null)&&
            		  Double.parseDouble(blPrpPcarshipTax.getArr(0).getChgTaxActual())!=0)
              {
              	prpJplanFeeSchema = new PrpJplanFeeSchema();
            	prpJplanFeeSchema.setSchema(schema);
            	prpJplanFeeSchema.setPayRefReason("R72");
            	prpJplanFeeSchema.setPlanFee(blPrpPcarshipTax.getArr(0).getChgTaxActual());
            	this.setArr(prpJplanFeeSchema);
            	blPrpJtaxSettle.transTotaxSettleE(dbpool,prpJplanFeeSchema,dbPrpCcarshipTax,
            			dbPrpCitemCar,dbPrpCinsured,dbPrpCmain);
              } else
              {
            	  blPrpJtaxSettle.updateTaxSettle(dbpool,schema,dbPrpCcarshipTax,
            	    		dbPrpCitemCar,dbPrpCinsured,"R72");
              }
              
              if((blPrpPcarshipTax.getArr(0).getChgPreviousPay()!=null)&&
            		  Double.parseDouble(blPrpPcarshipTax.getArr(0).getChgPreviousPay())!=0)
              {
              	prpJplanFeeSchema = new PrpJplanFeeSchema();
            	prpJplanFeeSchema.setSchema(schema);
            	prpJplanFeeSchema.setPayRefReason("R73");
            	prpJplanFeeSchema.setPlanFee(blPrpPcarshipTax.getArr(0).getChgPreviousPay());
            	this.setArr(prpJplanFeeSchema);
            	blPrpJtaxSettle.transTotaxSettleE(dbpool,prpJplanFeeSchema,dbPrpCcarshipTax,
            			dbPrpCitemCar,dbPrpCinsured,dbPrpCmain);
              } else
              {
            	  blPrpJtaxSettle.updateTaxSettle(dbpool,schema,dbPrpCcarshipTax,
            	    		dbPrpCitemCar,dbPrpCinsured,"R73");
              }
              
              if((blPrpPcarshipTax.getArr(0).getChgLateFee()!=null)&&
            		  Double.parseDouble(blPrpPcarshipTax.getArr(0).getChgLateFee())!=0)
              {
              	prpJplanFeeSchema = new PrpJplanFeeSchema();
            	prpJplanFeeSchema.setSchema(schema);
            	prpJplanFeeSchema.setPayRefReason("R74");
            	prpJplanFeeSchema.setPlanFee(blPrpPcarshipTax.getArr(0).getChgLateFee());
            	this.setArr(prpJplanFeeSchema);
            	blPrpJtaxSettle.transTotaxSettleE(dbpool,prpJplanFeeSchema,dbPrpCcarshipTax,
            			dbPrpCitemCar,dbPrpCinsured,dbPrpCmain);
              } else
              {
            	  blPrpJtaxSettle.updateTaxSettle(dbpool,schema,dbPrpCcarshipTax,
            	    		dbPrpCitemCar,dbPrpCinsured,"R74");
              }
    	  }
      }else 
      {
          for(int i=0; i<blPrpPfee.getSize(); i++)
          {
            schema = new PrpJplanFeeSchema();
            schema.setCertiType("E");
            schema.setCertiNo(iEndorseNo);
            schema.setSerialNo(""+(i+1));
            schema.setPolicyNo(dbPrpPhead.getPolicyNo());
        	if(dbPrpPhead.getEndorType().equals("21"))
                schema.setPayRefReason("P30");
              else if(Double.parseDouble(blPrpPfee.getArr(i).getChgPremium1())>0)
                schema.setPayRefReason("R30");
              else
                schema.setPayRefReason("P10");  
            
            




            
            schema.setClassCode(dbPrpCmain.getClassCode());
            schema.setRiskCode(dbPrpCmain.getRiskCode());
            schema.setContractNo(dbPrpCmain.getContractNo());
            schema.setAppliCode(dbPrpCmain.getAppliCode());
            schema.setAppliName(dbPrpCmain.getAppliName());
            schema.setInsuredCode(dbPrpCmain.getInsuredCode());
            schema.setInsuredName(dbPrpCmain.getInsuredName());
            schema.setStartDate(dbPrpPhead.getValidDate());
            schema.setEndDate(dbPrpCmain.getEndDate());
            schema.setValidDate(dbPrpPhead.getValidDate());
            schema.setPayNo("1");
            schema.setCurrency1(blPrpPfee.getArr(i).getCurrency1());
            schema.setPlanFee(blPrpPfee.getArr(i).getChgPremium1());
            schema.setPlanDate(dbPrpPhead.getValidDate());
            schema.setComCode(dbPrpCmain.getComCode());
            schema.setMakeCom(dbPrpCmain.getMakeCom());
            schema.setAgentCode(dbPrpCmain.getAgentCode());
            schema.setHandler1Code(dbPrpCmain.getHandler1Code());
            schema.setHandlerCode(dbPrpCmain.getHandlerCode());
            schema.setUnderWriteDate(dbPrpPhead.getUnderWriteEndDate());
            schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
            
            
            
            schema.setCenterCode(centercode);
            schema.setBranchCode("");
            schema.setAccBookType("");
            schema.setAccBookCode("");
            schema.setYearMonth("");
            
            schema.setVoucherNo("0");
            
            schema.setExchangeRate("1.0");
            schema.setPlanFeeCNY("0");
            schema.setPayRefFee("0");
            schema.setRealPayRefFee("0");
            
            schema.setBusinessNature(dbPrpCmain.getBusinessNature());
            
            
    		
            
            schema.setPoaType(poaType);
            schema.setPoaCode(poaCode);
            schema.setPoaDate(poaDate);
            
    		if("19".equals(dbPrpPhead.getEndorType())){
    			schema.setFlag("1");
    		}else{
    			schema.setFlag("0");
    		}
            
            
    		
             
            if("1".equals(dbPrpCmain.getAllinsFlag())){
            	schema.setFlag(schema.getFlag()+"1");
            }
            else{
            	schema.setFlag(schema.getFlag()+"0");
            }
            
            
            if(iRiskCode != null && iRiskCode != "" && (iRiskCode.substring(0,2).equals("30") || iRiskCode.equals("2700"))){
            	schema.setFlag(schema.getFlag()+"1");
            	schema.setFlag1(iRiskCode);
            }else{
            	schema.setFlag(schema.getFlag()+"0");
            }
            
            
    		
            if(dbPrpCmain.getRiskCode().equals("0507")){
                this.getCarNature(dbpool,schema);
                
                this.logPrintln("kai shi zhuan ru pi dan hao "+schema.getCertiNo()+" de che chuan shui");
                
                String[] strTaxPayer = transEndorCarShipTax(dbpool,schema,dbPrpCmain);
                
                schema.setTaxSettleFlag("0");
                schema.setTaxpayerCode(strTaxPayer[0]);
                schema.setTaxpayerName(strTaxPayer[1]);
                schema.setIdentifyNumber(strTaxPayer[2]);
                
            }
            
            if(dbPrpCmain.getCoinsFlag().equals("1")||dbPrpCmain.getCoinsFlag().equals("3")){
              strWherePart = " EndorseNo='"+dbPrpPhead.getEndorseNo()+"'";
              this.transCoinsDetail(dbpool,strWherePart, schema,blPrpJplanCommission,0,"ENDORSE",true);
            }else{
                
            	double riskKindCodeFee = 0;
            	if(splitFlag.equals("1"))
            	{
            		riskKindCodeFee = 0;
            		if(i == blPrpPfee.getSize()-1)
            		{
            			for(int j = 0;j < blPrpPitemKind.getSize();j++)
            			{
            				PrpJplanFeeSchema schemaRiskKindCode = new PrpJplanFeeSchema();
            				schemaRiskKindCode.setSchema(schema);
            				riskKindCodeFee = 0;
            				int result = dbPrpDcode.getInfo(dbpool,"RiskArticle",dbPrpCmain.getRiskCode()+blPrpPitemKind.getArr(j).getKindCode());
            				if(result == 100)
            				{
            					throw new UserException( -98, -1167, "BLPrpJplanFee.transPolicy", "XXXXX别没有对应XXXXX核算专项");
            				}
            				String carNatureCodeTmp = dbPrpDcode.getNewCodeCode();
            				for(int n = 0;n < this.getSize();n++)
            				{
            					if(carNatureCodeTmp.equals(this.getArr(n).getCarNatureCode()))
            					{
            						riskKindCodeFee += Double.parseDouble(this.getArr(n).getPlanFee());
            					}
            				}
            				riskKindCodeFee = Str.round(Str.round(riskKindCodeFee,8),2);
            				schemaRiskKindCode.setSerialNo(""+count);
            				count++;
            				schemaRiskKindCode.setCarNatureCode(carNatureCodeTmp);
            				schemaRiskKindCode.setPlanFee(""+(Double.parseDouble(blPrpPitemKind.getArr(j).getChgPremium())
            						- riskKindCodeFee));
            				schemaRiskKindCode.setPayNo("1");
            				
            				if(Double.parseDouble(schemaRiskKindCode.getPlanFee()) != 0)
            				{
            					this.setArr(schemaRiskKindCode);
            				}else
            				{
            					count--;
            				}
            				
            			}
            		}




























            	}else
            	{
            		this.setArr(schema);
            	}
                
            }

            
            if(dbPrpCmain.getCoinsFlag().equals("2"))
            {
              
              
              BLPrpCcoins blPrpPcoins = new BLPrpCcoins();
              BLPrpPcoinsDetail blPrpPcoinsDetail = new BLPrpPcoinsDetail();
              
              strWherePart = " PolicyNo='"+dbPrpCmain.getPolicyNo()+"' And CoinsType='1'";
              blPrpPcoins.query(dbpool,strWherePart);
              if(blPrpPcoins.getSize()>0)
              {
                strWherePart = " EndorseNo='"+iEndorseNo+"' And SerialNo="
                             + blPrpPcoins.getArr(0).getSerialNo()
                             + " And ChgOperateFee!=0";
                blPrpPcoinsDetail.query(dbpool,strWherePart);
                if(blPrpPcoinsDetail.getSize()>0)
                {
                  schema = new PrpJplanFeeSchema();
                  schema.setCertiType("F");
                  schema.setCertiNo(iEndorseNo);
                  schema.setSerialNo("1");
                  schema.setPolicyNo(dbPrpCmain.getPolicyNo());
                  schema.setPayRefReason("P96");
                  schema.setClassCode(dbPrpCmain.getClassCode());
                  schema.setRiskCode(dbPrpCmain.getRiskCode());
                  schema.setContractNo(dbPrpCmain.getContractNo());
                  schema.setAppliCode(dbPrpCmain.getAppliCode());
                  schema.setAppliName(dbPrpCmain.getAppliName());
                  schema.setInsuredCode(dbPrpCmain.getInsuredCode());
                  schema.setInsuredName(dbPrpCmain.getInsuredName());
                  schema.setStartDate(dbPrpCmain.getStartDate());
                  schema.setEndDate(dbPrpCmain.getEndDate());
                  
                  schema.setPayNo("1");
                  schema.setCurrency1(blPrpPfee.getArr(i).getCurrency1());
                  schema.setPlanFee(blPrpPcoinsDetail.getArr(0).getChgOperateFee());
                  schema.setPlanDate(dbPrpPhead.getValidDate());
                  schema.setComCode(dbPrpCmain.getComCode());
                  schema.setMakeCom(dbPrpCmain.getMakeCom());
                  schema.setAgentCode(dbPrpCmain.getAgentCode());
                  schema.setHandler1Code(dbPrpCmain.getHandler1Code());
                  schema.setHandlerCode(dbPrpCmain.getHandlerCode());
                  schema.setUnderWriteDate(dbPrpPhead.getUnderWriteEndDate());
                  schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
                  schema.setCoinsCode(blPrpPcoins.getArr(0).getCoinsCode());
                  schema.setCoinsName(blPrpPcoins.getArr(0).getCoinsName());
                  schema.setCoinsType(blPrpPcoins.getArr(0).getCoinsType());
                  schema.setCenterCode(centercode);
                  schema.setBranchCode("");
                  schema.setAccBookType("");
                  schema.setAccBookCode("");
                  schema.setYearMonth("");
                  
                  schema.setVoucherNo("0");
                  
                  schema.setExchangeRate("1.0");
                  schema.setPlanFeeCNY("0");
                  schema.setPayRefFee("0");
                  schema.setRealPayRefFee("0");
                  
                  schema.setBusinessNature(dbPrpCmain.getBusinessNature());
                  
                  
                  
                  schema.setPoaType(poaType);
                  schema.setPoaCode(poaCode);
                  schema.setPoaDate(poaDate);
                  
          		
                  
          		if("19".equals(dbPrpPhead.getEndorType())){
          			schema.setFlag("1");
          		}else{
          			schema.setFlag("0");
          		}
                  
                  
          		
                   
                  if("1".equals(dbPrpCmain.getAllinsFlag())){
                  	schema.setFlag(schema.getFlag()+"1");
                  }
                  else{
                  	schema.setFlag(schema.getFlag()+"0");
                  }
                  
                  if(iRiskCode != null && iRiskCode != "" && (iRiskCode.substring(0,2).equals("30") || iRiskCode.equals("2700"))){
                	  schema.setFlag(schema.getFlag()+"1");
                	  schema.setFlag1(iRiskCode);
                  }else{
                	  schema.setFlag(schema.getFlag()+"0");
                  }
                  
                  
          		
                  if(dbPrpCmain.getRiskCode().equals("0507")){
                    this.getCarNature(dbpool,schema);
                    
                    this.logPrintln("kai shi zhuan ru pi dan hao "+schema.getCertiNo()+" de che chuan shui");
                    
                    String[] strTaxPayer = transEndorCarShipTax(dbpool,schema,dbPrpCmain);
                    
                    schema.setTaxSettleFlag("0");
                    schema.setTaxpayerCode(strTaxPayer[0]);
                    schema.setTaxpayerName(strTaxPayer[1]);
                    schema.setIdentifyNumber(strTaxPayer[2]);
                    
                  }
                  this.setArr(schema);
                  intFCount ++;
                }
              }
            }
          }
      }
      


      
      if(!dbPrpCmain.getCoinsFlag().equals("1")&&!dbPrpCmain.getCoinsFlag().equals("3"))
      {
        
        int intCount=0;
        BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
      	for (int k=0;k<this.getSize();k++){
      		if((!this.getArr(k).getPayRefReason().equals("R72"))&&
      		   (!this.getArr(k).getPayRefReason().equals("R73"))&&
      		   (!this.getArr(k).getPayRefReason().equals("R74"))&&
      		   (!this.getArr(k).getPayRefReason().equals("P96")))
      		{
      			blPrpJplanFee.setArr(this.getArr(k));
      			intCount++;
      		}
      	}





        
        strWherePart = " CertiNo='"+iEndorseNo+"'";
        
        BLPrpJplanCommission blPrJplanCommission = new BLPrpJplanCommission();
        blPrJplanCommission.transCommission(dbpool,strWherePart,blPrpJplanFee,dbPrpPhead,dbPrpCmain,iRiskCode);

        
        strWherePart = " CertiNo='"+iEndorseNo+"'";
        this.transDisPremium(dbpool,strWherePart,intCount,dbPrpPhead,dbPrpCmain,iRiskCode);
        
        
        this.updatePeople(dbpool,dbPrpPhead,dbPrpCmain);
        
      }
      
      








      
      if(dbPrpPhead.getEndorType().equals("60"))
      {
        strWherePart = "UPDATE PrpJplanFee SET AppliCode='" +
            dbPrpCmain.getAppliCode() + "',AppliName='" + dbPrpCmain.getAppliName() +
            "' WHERE PolicyNo='" + dbPrpCmain.getPolicyNo() + "'";
        dbpool.update(strWherePart);
        strWherePart = "UPDATE PrpJpayRefRec SET AppliCode='" +
            dbPrpCmain.getAppliCode() + "',AppliName='" + dbPrpCmain.getAppliName() +
            "' WHERE PolicyNo='" + dbPrpCmain.getPolicyNo() + "'";
        dbpool.update(strWherePart);
      }

      
      if(dbPrpPhead.getRiskCode().equals("0507"))
      {
        BLPrpPexpense blPrpPexpense = new BLPrpPexpense();
        blPrpPexpense.query(dbpool," EndorseNo='"+iEndorseNo+"'");
        
        if(blPrpPexpense.getSize()>0){
          if(Double.parseDouble(Str.chgStrZero(blPrpPexpense.getArr(0).getSalvationFee()))!=0){
            schema = new PrpJplanFeeSchema();
            schema.setSchema(this.getArr(0));
            schema.setCertiType("J");
            schema.setPayRefReason("R12"); 
            schema.setCurrency1(blPrpPexpense.getArr(0).getCurrency());
            schema.setPlanFee(blPrpPexpense.getArr(0).getSalvationFee());
            this.setArr(schema);
          }
        }
      }
	  
	  if("19".equals(dbPrpPhead.getEndorType())){

		  dbpool.update("UPDATE PrpJplanFee SET Flag='1'||substr(flag,2) WHERE policyno = '" + dbPrpPhead.getPolicyNo() + "'");
		  
		  dbpool.update("UPDATE PrpJpayRefRec SET Flag=Flag||'Z' WHERE policyno = '" + dbPrpPhead.getPolicyNo() + "'");
		  
		  dbpool.update("UPDATE PrpJtaxSettle SET Flag='1' WHERE policyno = '" + dbPrpPhead.getPolicyNo() + "'");
		  
		  
		  dbpool.update("UPDATE PrpJplanCommission SET payflag='0' WHERE policyno = '" + dbPrpPhead.getPolicyNo() + "'");
		  dbpool.update("UPDATE PrpJpayCommission SET payflag='0' WHERE policyno = '" + dbPrpPhead.getPolicyNo() + "'");
		  
	  }
	  blPrpJplanCommission.save(dbpool);
	
    }
    
    /**
     * @author zhanglingjian 20080407 转入车船税退税数据到应收应付表（原因：北分与平台交互）
     * @param dbpool
     * @param iCertiNo
     * @throws UserException
     * @throws Exception
     */











































































































































    /**
     * @author lijibin
     * @desc 如果交强XXXXX九大类发生改变,冲销原收付数据,生成新的九大类数据
     */
    public void transEndorCar(DbPool dbpool,DBPrpPhead dbPrpPhead) throws UserException, Exception {
        DBPrpCitemCar dbPrpCitemCar = new DBPrpCitemCar();
        DBPrpPitemCar dbPrpPitemCar = new DBPrpPitemCar();
        dbPrpPitemCar.getInfo(dbpool,dbPrpPhead.getEndorseNo(),"1"); 
        dbPrpCitemCar.getInfo(dbpool,dbPrpPhead.getPolicyNo(),"1");  
        
        String strCarNatureCodeP = this.getCarNatureCode(dbpool,dbPrpPitemCar.getCarKindCode(), dbPrpPitemCar.getUseNatureCode());
        
        String strCarNatureCodeC = this.getCarNatureCode(dbpool,dbPrpCitemCar.getCarKindCode(), dbPrpCitemCar.getUseNatureCode());
        
        if (strCarNatureCodeP.equals(strCarNatureCodeC)){
            return;
        } else {
            String strWherePart = " PolicyNo='" + dbPrpPhead.getPolicyNo() + "'";
            this.query(dbpool,strWherePart);
            
            for(int i=0; i<this.getSize(); i++){
                if(this.getArr(i).getCertiType().equals("P")||
                   this.getArr(i).getCertiType().equals("E")){
                    this.getArr(i).setPayRefReason("R00");
                }                
                this.getArr(i).setPlanFee(""+(-1*Double.parseDouble(this.getArr(i).getPlanFee())));      
                
                
                this.getArr(i).setBranchCode("");
                this.getArr(i).setAccBookType("");
                this.getArr(i).setAccBookCode("");
                this.getArr(i).setYearMonth("");
                
                this.getArr(i).setVoucherNo("0");
                
                this.getArr(i).setExchangeRate("1.0");
                this.getArr(i).setPlanFeeCNY("0");
                this.getArr(i).setPayRefFee("0");
                this.getArr(i).setRealPayRefFee("0");
                
                this.getArr(i).setSerialNo(""+(this.getMaxSerialNo(dbpool,this.getArr(i))+i));
                
                if(this.getArr(i).getCertiType().equals("P")){
                    this.getArr(i).setStartDate(dbPrpPhead.getValidDate());
                }
                else if(this.getArr(i).getCertiType().equals("E")){
                    this.getArr(i).setStartDate(dbPrpPhead.getValidDate());
                    this.getArr(i).setValidDate(dbPrpPhead.getValidDate());
                }
                else if(this.getArr(i).getCertiType().equals("S")){
                    this.getArr(i).setStartDate(dbPrpPhead.getValidDate());
                    this.getArr(i).setValidDate(dbPrpPhead.getValidDate());
                }
                else{
                    this.getArr(i).setUnderWriteDate(dbPrpPhead.getValidDate());
                }
            }
            
            this.save(dbpool);
            
            for (int i=0; i<this.getSize(); i++){
                this.getArr(i).setCarNatureCode(strCarNatureCodeC);
                this.getArr(i).setPlanFee(""+(-1*Double.parseDouble(this.getArr(i).getPlanFee())));      
                this.getArr(i).setSerialNo(""+(this.getMaxSerialNo(dbpool,this.getArr(i))+i));
            }
            this.save(dbpool);
            
            this.initArr();
        }
    }

    /**
     * 对最大序列号进行查询 lijibin 20061025 
     * @param iWherePart
     *            查询条件(包括排序字句)
     * @return intSerialNo
     * @throws Exception
     */
    public int getMaxSerialNo(DbPool dbpool,PrpJplanFeeSchema iSchema) throws Exception {      
       
        String strSQL = " SELECT Max(serialno) FROM PrpJplanFee "
            + " WHERE CertiType='" + iSchema.getCertiType() + "' AND CertiNo='"
            + iSchema.getCertiNo() + "' AND PayRefReason='" + iSchema.getPayRefReason() + "'";
        int intSerialNo = 0;
        ResultSet resultSet = dbpool.query(strSQL);
        if (resultSet.next()){
            intSerialNo = resultSet.getInt(1);
        }
        resultSet.close();
        intSerialNo ++;
        return intSerialNo;
    }

    /**
     * @author lijibin 20050609 转入预赔信息到应收应付表
     * @param dbpool
     * @param iCertiNo
     * @throws UserException
     * @throws Exception
     */
    
    public void transPrepay(DbPool dbpool,String iPreCompensateNo) throws UserException,Exception
    
    {
      DBPrpLprepay dbPrpLprepay = new DBPrpLprepay();
      BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      String strWherePart = "";
      int intReturn = 0;
      intReturn = dbPrpLprepay.getInfo(dbpool,iPreCompensateNo);
      if(intReturn == 100)
        throw new UserException( -98, -1167, "BLPrpJplanFee.transPrepay", "无此预赔信息："+iPreCompensateNo);
      intReturn = dbPrpCmain.getInfo(dbpool,dbPrpLprepay.getPolicyNo());
      
      String centercode = blPrpDcompany.getCenterCodeNew(dbpool,dbPrpCmain.getComCode());
      
      String iRiskCode = this.getMainRiskNo(dbpool, dbPrpCmain);
      

      PrpJplanFeeSchema schema = new PrpJplanFeeSchema();
      schema.setCertiType("Y");
      schema.setCertiNo(iPreCompensateNo);
      schema.setSerialNo("1");
      schema.setPolicyNo(dbPrpLprepay.getPolicyNo());
      schema.setPayRefReason("P50");
      schema.setClaimNo(dbPrpLprepay.getClaimNo());
      schema.setClassCode(dbPrpCmain.getClassCode());
      schema.setRiskCode(dbPrpCmain.getRiskCode());
      schema.setContractNo(dbPrpCmain.getContractNo());
      schema.setAppliCode(dbPrpCmain.getAppliCode());
      schema.setAppliName(dbPrpCmain.getAppliName());
      schema.setInsuredCode(dbPrpCmain.getInsuredCode());
      schema.setInsuredName(dbPrpCmain.getInsuredName());
      schema.setStartDate(dbPrpCmain.getStartDate());
      schema.setEndDate(dbPrpCmain.getEndDate());
      
      schema.setPayNo("1");
      schema.setCurrency1(dbPrpLprepay.getCurrency());
      schema.setPlanFee(dbPrpLprepay.getSumPrePaid());
      
      schema.setComCode(dbPrpCmain.getComCode());
      schema.setMakeCom(dbPrpCmain.getMakeCom());
      schema.setAgentCode(dbPrpCmain.getAgentCode());
      schema.setHandler1Code(dbPrpCmain.getHandler1Code());
      schema.setHandlerCode(dbPrpCmain.getHandlerCode());
      schema.setUnderWriteDate(dbPrpLprepay.getUnderWriteEndDate());
      schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
      
      
      
      schema.setCenterCode(centercode);
      schema.setBranchCode("");
      schema.setAccBookType("");
      schema.setAccBookCode("");
      schema.setYearMonth("");
      
      schema.setVoucherNo("0");
      
      schema.setExchangeRate("1.0");
      schema.setPlanFeeCNY("0");
      schema.setPayRefFee("0");
      schema.setRealPayRefFee("0");
      
      schema.setBusinessNature(dbPrpCmain.getBusinessNature());
      
      
      if("1".equals(dbPrpCmain.getAllinsFlag())){
    	  schema.setFlag("01");
      }
      else{
    	  schema.setFlag("00");
      }
      
      if(iRiskCode != null && iRiskCode != "" && (iRiskCode.substring(0,2).equals("30") || iRiskCode.equals("2700"))){
    	  schema.setFlag(schema.getFlag()+"1");
    	  schema.setFlag1(iRiskCode);
      }else{
    	  schema.setFlag(schema.getFlag()+"0");
      }
      
      
      if(dbPrpCmain.getRiskCode().equals("0507")){
    	  this.getCarNature(dbpool,schema);
      }
      
      if (!dbPrpCmain.getCoinsFlag().equals("0"))
      {
        strWherePart = " PolicyNo='" + dbPrpLprepay.getPolicyNo() + "'";
        this.transCoins(dbpool, strWherePart, schema);
      }else
      {
        this.setArr(schema);
      }
    }

    /**
     * @author lijibin 20050609 转入实赔信息到应收应付表
     * @param dbpool
     * @param iCertiNo
     * @throws UserException
     * @throws Exception
     */
    
    public void transCompensate(DbPool dbpool,String iCompensateNo) throws UserException,Exception
    {
      DBPrpLcompensate dbPrpLcompensate = new DBPrpLcompensate();
      BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      DBPrpDcode dbPrpDcode = new DBPrpDcode();
      
      
      PrpJplanFeeSchema schema = null;
      BLPrpLcharge blPrpLcharge = new BLPrpLcharge();

      String strWherePart = "";
      int intReturn = 0;
      intReturn = dbPrpLcompensate.getInfo(dbpool,iCompensateNo);
      if(intReturn == 100)
        throw new UserException( -98, -1167, "BLPrpJplanFee.transCompensate", "无此计算书信息："+iCompensateNo);
      intReturn = dbPrpCmain.getInfo(dbpool,dbPrpLcompensate.getPolicyNo());
      
      String centercode = blPrpDcompany.getCenterCodeNew(dbpool,dbPrpCmain.getComCode());

      
      
      
      String iRiskCode = this.getMainRiskNo(dbpool, dbPrpCmain);
      
      
      
      String splitFlag = "0";
      if(SysConfig.getProperty("SplitRisk").indexOf(dbPrpCmain.getRiskCode()) >=0 )
      {
    	  splitFlag = "1";
      }
      
      
      
      DBPrpLclaim dbPrpLclaim = new DBPrpLclaim();
      dbPrpLclaim.getInfo(dbpool,dbPrpLcompensate.getClaimNo());

      
      int intSerialNo = 0;
      if(!dbPrpCmain.getCoinsFlag().equals("1")&&!dbPrpCmain.getCoinsFlag().equals("3"))
      {
        
        
        strWherePart = " CompensateNo='" + iCompensateNo + "' AND SumRealPay!=0 AND SUBSTR(ChangeFlag,1,1)='0'";
        blPrpLcharge.query(strWherePart,0);
        String strPayRefReason = "";
        double dblCharge = 0;
        for(int i=0;i<blPrpLcharge.getSize();i++){
          schema = new PrpJplanFeeSchema();
          schema.setCertiType("C");
          schema.setCertiNo(iCompensateNo);
          intSerialNo++;
          schema.setSerialNo(""+intSerialNo);
          schema.setPolicyNo(dbPrpLcompensate.getPolicyNo());
          strPayRefReason = this.getPayRefReason(blPrpLcharge.getArr(i).getChargeCode());
          schema.setPayRefReason(strPayRefReason);
          schema.setClaimNo(dbPrpLcompensate.getClaimNo());
          schema.setClassCode(dbPrpCmain.getClassCode());
          schema.setRiskCode(dbPrpCmain.getRiskCode());
          schema.setContractNo(dbPrpCmain.getContractNo());
          schema.setAppliCode(dbPrpCmain.getAppliCode());
          schema.setAppliName(dbPrpCmain.getAppliName());
          
          
          










          
    	  schema.setInsuredCode(dbPrpLclaim.getInsuredCode());
    	  schema.setInsuredName(dbPrpLclaim.getInsuredName());   
          
          schema.setStartDate(dbPrpCmain.getStartDate());
          schema.setEndDate(dbPrpCmain.getEndDate());
          schema.setPayNo("1");
          schema.setCurrency1(dbPrpLcompensate.getCurrency());
          schema.setPlanFee(blPrpLcharge.getArr(i).getSumRealPay());
          schema.setComCode(dbPrpCmain.getComCode());
          schema.setMakeCom(dbPrpCmain.getMakeCom());
          schema.setAgentCode(dbPrpCmain.getAgentCode());
          schema.setHandler1Code(dbPrpCmain.getHandler1Code());
          schema.setHandlerCode(dbPrpCmain.getHandlerCode());
          schema.setUnderWriteDate(dbPrpLcompensate.getUnderWriteEndDate());
          schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
          schema.setCenterCode(centercode);
          schema.setBranchCode("");
          schema.setAccBookType("");
          schema.setAccBookCode("");
          schema.setYearMonth("");
          
          schema.setVoucherNo("0");
          
          schema.setExchangeRate("1.0");
          schema.setPlanFeeCNY("0");
          schema.setPayRefFee("0");
          schema.setRealPayRefFee("0");
          
          schema.setBusinessNature(dbPrpCmain.getBusinessNature());
          
          
            if("1".equals(dbPrpCmain.getAllinsFlag())){
            	schema.setFlag("01");
            }
            else{
            	schema.setFlag("00");
            }
            
            
            if(iRiskCode != null && iRiskCode != "" && (iRiskCode.substring(0,2).equals("30") || iRiskCode.equals("2700"))){
            	schema.setFlag(schema.getFlag()+"1");
            	schema.setFlag1(iRiskCode);
            }else{
            	schema.setFlag(schema.getFlag()+"0");
            }
            	
            
            
            
            
          if(dbPrpCmain.getRiskCode().equals("0507")){
            this.getCarNature(dbpool,schema);
          }
          
          
          if(splitFlag.equals("1"))
          {
        	  int result = dbPrpDcode.getInfo(dbpool,"RiskArticle",dbPrpCmain.getRiskCode()+blPrpLcharge.getArr(i).getKindCode());
			  if(result == 100)
			  {
				  throw new UserException( -98, -1167, "BLPrpJplanFee.transPolicy", "XXXXX别没有对应XXXXX核算专项");
			  }
			  String flag1 = dbPrpDcode.getNewCodeCode();
			  schema.setCarNatureCode(flag1);
          }
          
          
          this.setArr(schema);
          dblCharge += Double.parseDouble(blPrpLcharge.getArr(i).getSumRealPay());
        }
        double dblPaid = 0;
        schema = new PrpJplanFeeSchema();
        schema.setCertiType("C");
        schema.setCertiNo(iCompensateNo);
        intSerialNo++;
        schema.setSerialNo(""+intSerialNo);
        schema.setPolicyNo(dbPrpLcompensate.getPolicyNo());
        
        if (dbPrpLclaim.getEscapeFlag() != null &&
            dbPrpLclaim.getEscapeFlag().length() > 0 &&
            dbPrpLclaim.getEscapeFlag().substring(0, 1).equals("D"))
          schema.setPayRefFee("P6A"); 
        else
          schema.setPayRefReason("P60");
        schema.setClaimNo(dbPrpLcompensate.getClaimNo());
        schema.setClassCode(dbPrpCmain.getClassCode());
        schema.setRiskCode(dbPrpCmain.getRiskCode());
        schema.setContractNo(dbPrpCmain.getContractNo());
        schema.setAppliCode(dbPrpCmain.getAppliCode());
        schema.setAppliName(dbPrpCmain.getAppliName());


        
        
	  	  schema.setInsuredCode(dbPrpLclaim.getInsuredCode());
	  	  schema.setInsuredName(dbPrpLclaim.getInsuredName());  
  	  
        schema.setStartDate(dbPrpCmain.getStartDate());
        schema.setEndDate(dbPrpCmain.getEndDate());
        
        schema.setPayNo("1");
        schema.setCurrency1(dbPrpLcompensate.getCurrency());
        dblPaid = Double.parseDouble(dbPrpLcompensate.getSumThisPaid())-dblCharge;
        schema.setPlanFee(""+dblPaid);
        
        
        schema.setComCode(dbPrpCmain.getComCode());
        schema.setMakeCom(dbPrpCmain.getMakeCom());
        schema.setAgentCode(dbPrpCmain.getAgentCode());
        schema.setHandler1Code(dbPrpCmain.getHandler1Code());
        schema.setHandlerCode(dbPrpCmain.getHandlerCode());
        schema.setUnderWriteDate(dbPrpLcompensate.getUnderWriteEndDate());
        schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
        
        
        
        schema.setCenterCode(centercode);
        schema.setBranchCode("");
        schema.setAccBookType("");
        schema.setAccBookCode("");
        schema.setYearMonth("");
        
        schema.setVoucherNo("0");
        
        schema.setExchangeRate("1.0");
        schema.setPlanFeeCNY("0");
        schema.setPayRefFee("0");
        schema.setRealPayRefFee("0");
        
        schema.setBusinessNature(dbPrpCmain.getBusinessNature());
        
        
        if("1".equals(dbPrpCmain.getAllinsFlag())){
        	schema.setFlag("01");
        }
        else{
        	schema.setFlag("00");
        }
        
        
        if(iRiskCode != null && iRiskCode != "" && (iRiskCode.substring(0,2).equals("30") || iRiskCode.equals("2700"))){
        	schema.setFlag(schema.getFlag()+"1");
        	schema.setFlag1(iRiskCode);
        }
        else{
        	schema.setFlag(schema.getFlag()+"0");
        }
        
        if(dbPrpCmain.getRiskCode().equals("0507")){
            this.getCarNature(dbpool,schema);
        }
        
        
        if(splitFlag.equals("1"))
        {
        	
        	BLPrpLloss blPrpLloss = new BLPrpLloss();
        	PrpJplanFeeSchema schemaRiskKindCode = null;
        	blPrpLloss = this.findForPrpLcompensate(dbpool,iCompensateNo);
        	for(int j = 0;j < blPrpLloss.getSize();j++)
        	{
        		schemaRiskKindCode = new PrpJplanFeeSchema();
        		schemaRiskKindCode.setSchema(schema);
        		schemaRiskKindCode.setSerialNo(""+intSerialNo);
        		intSerialNo++;
        		int result = dbPrpDcode.getInfo(dbpool,"RiskArticle",dbPrpCmain.getRiskCode()+blPrpLloss.getArr(j).getKindCode());
  			    if(result == 100)
  			    {
  				    throw new UserException( -98, -1167, "BLPrpJplanFee.transPolicy", "XXXXX别没有对应XXXXX核算专项");
  			    }
  			    String flag1 = dbPrpDcode.getNewCodeCode();
        		schemaRiskKindCode.setCarNatureCode(flag1);
        		schemaRiskKindCode.setPlanFee(blPrpLloss.getArr(j).getSumRealPay());
        		schemaRiskKindCode.setPayNo("1");
        		this.setArr(schemaRiskKindCode);
        	}
        }else
        {
        	this.setArr(schema);
        }
        
      }
      else  
      {
        schema = new PrpJplanFeeSchema();
        schema.setCertiType("C");
        schema.setCertiNo(iCompensateNo);
        intSerialNo++;
        schema.setSerialNo(""+intSerialNo);
        schema.setPolicyNo(dbPrpLcompensate.getPolicyNo());
        schema.setPayRefReason("P60");
        schema.setClaimNo(dbPrpLcompensate.getClaimNo());
        schema.setClassCode(dbPrpCmain.getClassCode());
        schema.setRiskCode(dbPrpCmain.getRiskCode());
        schema.setContractNo(dbPrpCmain.getContractNo());
        schema.setAppliCode(dbPrpCmain.getAppliCode());
        schema.setAppliName(dbPrpCmain.getAppliName());


        
        
  	  schema.setInsuredCode(dbPrpLclaim.getInsuredCode());
  	  schema.setInsuredName(dbPrpLclaim.getInsuredName());  
        
        schema.setStartDate(dbPrpCmain.getStartDate());
        schema.setEndDate(dbPrpCmain.getEndDate());
        schema.setPayNo("1");
        schema.setCurrency1(dbPrpLcompensate.getCurrency());
        schema.setPlanFee("0");
        schema.setComCode(dbPrpCmain.getComCode());
        schema.setMakeCom(dbPrpCmain.getMakeCom());
        schema.setAgentCode(dbPrpCmain.getAgentCode());
        schema.setHandler1Code(dbPrpCmain.getHandler1Code());
        schema.setHandlerCode(dbPrpCmain.getHandlerCode());
        schema.setUnderWriteDate(dbPrpLcompensate.getUnderWriteEndDate());
        schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
        schema.setCenterCode(centercode);
        schema.setBranchCode("");
        schema.setAccBookType("");
        schema.setAccBookCode("");
        schema.setYearMonth("");
        
        schema.setVoucherNo("0");
        
        schema.setExchangeRate("1.0");
        schema.setPlanFeeCNY("0");
        schema.setPayRefFee("0");
        schema.setRealPayRefFee("0");
        
        schema.setBusinessNature(dbPrpCmain.getBusinessNature());
        
        
        if("1".equals(dbPrpCmain.getAllinsFlag())){
        	schema.setFlag("01");
        }
        else{
        	schema.setFlag("00");
        }
        
        
        if(iRiskCode != null && iRiskCode != "" && (iRiskCode.substring(0,2).equals("30") || iRiskCode.equals("2700"))){
        	schema.setFlag(schema.getFlag()+"1");
        	schema.setFlag1(iRiskCode);
        }else{
        	schema.setFlag(schema.getFlag()+"0");
        }
        
        if(dbPrpCmain.getRiskCode().equals("0507")){
            this.getCarNature(dbpool,schema);
        }
        
        
        strWherePart = " BusinessNo='"+iCompensateNo+"'";
        this.transCoinsDetail(dbpool,strWherePart, schema,null,0,"COMPENSATE",true);
      }
    }

    /**
     * @author lijibin 20050609 按联共XXXXX比例进行分摊
     * @param dbpool 数据库连接池
     * @param iWherePart 转数的条件
     * @param iSchema
     * @throws Exception
     */
    public void transCoins(DbPool dbpool,String iWherePart,PrpJplanFeeSchema iSchema) throws Exception
    {
      BLPrpCcoins blPrpCcoins = new BLPrpCcoins();
      blPrpCcoins.query(dbpool,iWherePart);
      
      /*for(int i=0; i<blPrpCcoins.getSize(); i++)
      {
        schema = new PrpJplanFeeSchema();
        schema.setSchema(iSchema);
        
        schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
        schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
        schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
        double dblCoinsFee = Double.parseDouble(schema.getPlanFee())
                           * Double.parseDouble(blPrpCcoins.getArr(i).getCoinsRate())
                           / 100;
        dblCoinsFee = Str.round(Str.round(dblCoinsFee,8),2);
        schema.setPlanFee(""+dblCoinsFee);
        this.setArr(schema);
      }*/
      
      this.setArr(iSchema);
    }

    /**
     * @author lijibin 20050609 按联共XXXXX比例进行分摊
     * @param dbpool 数据库连接池
     * @param iWherePart 转数的条件
     * @param iSchema
     * @throws Exception
     */
    public void transCoinsDetail(DbPool dbpool,String iWherePart,
        PrpJplanFeeSchema iSchema,BLPrpJplanCommission blPrpJplanCommission,double dbSumPremium,
        String iBizType,boolean iFlag) throws Exception
    {

      if("POLICY".equals(iBizType))
      { 

        DBPrpCommission dbPrpCommission = new DBPrpCommission();
        int intReturn = dbPrpCommission.getInfo(dbpool, iSchema.getPolicyNo());
    	
        BLPrpCcoins blPrpCcoins = new BLPrpCcoins();
        BLPrpCcoinsDetail blPrpCcoinsDetail = new BLPrpCcoinsDetail();
        blPrpCcoins.query(dbpool,iWherePart);
        blPrpCcoinsDetail.query(dbpool,iWherePart);
        PrpJplanFeeSchema schema = null;
        PrpJplanCommissionSchema prpJplanCommissionSchema = null;
        String strProportionFlag = "  ";
        String strCoinsType = "";
        String strSerialNo = "";
        double dblCoinsFee = 0;
        double dblAgentFee = 0;
        double dblMiddleCostFee = 0;
        int intSerialNo = 1;
        for(int i=0; i<blPrpCcoinsDetail.getSize(); i++)
        {
          for(int j=0; j<blPrpCcoins.getSize(); j++)
          {
            if(blPrpCcoins.getArr(j).getSerialNo().
                equals(blPrpCcoinsDetail.getArr(i).getSerialNo()))
            {
              strProportionFlag = blPrpCcoins.getArr(j).getProportionFlag() + "  ";
              strCoinsType = blPrpCcoins.getArr(j).getCoinsType();
              break;
            }
          }

          if(strProportionFlag.substring(0,1).equals("1")||
              strCoinsType.equals("1"))
          {
            dblAgentFee += Str.round(Double.parseDouble(Str.chgStrZero(blPrpCcoinsDetail.getArr(i).getAgentFee())),2);
          }
          if(strProportionFlag.substring(1,2).equals("1")||
              strCoinsType.equals("1"))
          {
            dblMiddleCostFee += Str.round(-1 * Double.parseDouble(Str.chgStrZero(blPrpCcoinsDetail.getArr(i).getMiddleCostFee())),2);
          }
        }

        PrpCcoinsDetailSchema prpCcoinsDetailSchema = new PrpCcoinsDetailSchema();

        for(int i=0; i<blPrpCcoins.getSize(); i++)
        {
          if("1".equals(blPrpCcoins.getArr(i).getCoinsType()))
          {
            schema = new PrpJplanFeeSchema();
            schema.setSchema(iSchema);
            schema.setPayRefReason("R10");
            schema.setCertiType("P");
            schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
            schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
            schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
            this.setArr(schema);

            for(int j=0; j<blPrpCcoinsDetail.getSize(); j++)
            {
              if(blPrpCcoins.getArr(i).getSerialNo().
                  equals(blPrpCcoinsDetail.getArr(j).getSerialNo()))
              {
                prpCcoinsDetailSchema = blPrpCcoinsDetail.getArr(j);
                break;
              }
            }

            if(iFlag)
            {
              for(int m=0;m<this.getSize();m++)
              {
                if(this.getArr(m).getPayRefReason().equals("P90")&&
                    this.getArr(m).getCoinsCode().equals(blPrpCcoins.getArr(i).getCoinsCode()))
                {
                  dblAgentFee = dblAgentFee - Str.round(Double.parseDouble(this.getArr(m).getPlanFee()),2);
                }
                else if(this.getArr(m).getPayRefReason().equals("P40")&&
                    this.getArr(m).getCoinsCode().equals(blPrpCcoins.getArr(i).getCoinsCode()))
                {
                  dblMiddleCostFee = dblMiddleCostFee - Str.round(Double.parseDouble(this.getArr(m).getPlanFee()),2);
                }
              }
              for(int n=0;n<blPrpJplanCommission.getSize();n++)
              {
                if(blPrpJplanCommission.getArr(n).getPayRefReason().equals("P90")&&
                		blPrpJplanCommission.getArr(n).getCoinsCode().equals(blPrpCcoins.getArr(i).getCoinsCode()))
                {
                  dblAgentFee = dblAgentFee - Str.round(Double.parseDouble(blPrpJplanCommission.getArr(n).getPlanFee()),2);
                }
              }
            }
            else
            {
              dblAgentFee = dblAgentFee * Double.parseDouble(iSchema.getPlanFee()) / dbSumPremium;;
              dblMiddleCostFee = dblMiddleCostFee * Double.parseDouble(iSchema.getPlanFee()) / dbSumPremium;;
            }

            dblAgentFee = Str.round(dblAgentFee,2);
            dblMiddleCostFee = Str.round(dblMiddleCostFee,2);

            
            if(Double.parseDouble(Str.chgStrZero(prpCcoinsDetailSchema.getAgentFee()))!=0)
            {
              
              
              
            	if (intReturn==100){
        			throw new UserException(-98, -1167, "BLPrpJplanFee.transCoinsDetail",
        					"PrpCommission表中没有业务单号"+iSchema.getPolicyNo()+"对应手续费数据" ); 
            	}
            	
              prpJplanCommissionSchema = new PrpJplanCommissionSchema();           
              blPrpJplanCommission.evaluate(dbpool, iSchema, prpJplanCommissionSchema, "0");
              prpJplanCommissionSchema.setPayRefReason("P90");
              prpJplanCommissionSchema.setCertiType("S");
              prpJplanCommissionSchema.setDisRate(dbPrpCommission.getDisRate());
              prpJplanCommissionSchema.setPayFlag("0");
              prpJplanCommissionSchema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
              prpJplanCommissionSchema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
              prpJplanCommissionSchema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
              prpJplanCommissionSchema.setPlanFee("" + dblAgentFee);
              blPrpJplanCommission.setArr(prpJplanCommissionSchema);
              
            }
            
            if(Double.parseDouble(Str.chgStrZero(prpCcoinsDetailSchema.getMiddleCostFee()))!=0)
            {
              schema = new PrpJplanFeeSchema();
              schema.setSchema(iSchema);
              schema.setPayRefReason("P40");
              schema.setCertiType("E");
              schema.setValidDate(iSchema.getStartDate());
              schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
              schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
              schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
              schema.setPlanFee("" + dblMiddleCostFee);
              this.setArr(schema);
            }
            continue;
          }

          String strPayRefReasonPremium = "";
          String strPayRefReasonAgent = "";
          String strPayRefReasonMiddle = "";
          if("2".equals(blPrpCcoins.getArr(i).getCoinsType()))
          {
            strPayRefReasonPremium = "P82";
            strPayRefReasonAgent = "P92";
            strPayRefReasonMiddle = "P42";
          }
          else if("3".equals(blPrpCcoins.getArr(i).getCoinsType()))
          {
            strPayRefReasonPremium = "P81";
            strPayRefReasonAgent = "P91";
            strPayRefReasonMiddle = "P41";
          }
          else
          {
            strPayRefReasonPremium = "";
            strPayRefReasonAgent = "";
            strPayRefReasonMiddle = "";
          }

          for(int j=0; j<blPrpCcoinsDetail.getSize(); j++)
          {
            if(blPrpCcoins.getArr(i).getSerialNo().
                equals(blPrpCcoinsDetail.getArr(j).getSerialNo()))
            {
              prpCcoinsDetailSchema = blPrpCcoinsDetail.getArr(j);
              break;
            }
          }

          dblCoinsFee = -1 * Double.parseDouble(Str.chgStrZero(prpCcoinsDetailSchema.getCoinsPremium()));
          dblAgentFee = -1 * Double.parseDouble(Str.chgStrZero(prpCcoinsDetailSchema.getAgentFee()));
          dblMiddleCostFee = Double.parseDouble(Str.chgStrZero(prpCcoinsDetailSchema.getMiddleCostFee()));
          if(iFlag)
          {
            for(int m=0;m<this.getSize();m++)
            {
              if(this.getArr(m).getPayRefReason().equals(strPayRefReasonPremium)&&
                  this.getArr(m).getCoinsCode().equals(blPrpCcoins.getArr(i).getCoinsCode()))
              {
                dblCoinsFee = dblCoinsFee - Str.round(Double.parseDouble(this.getArr(m).getPlanFee()),2);
              }
              else if(this.getArr(m).getPayRefReason().equals(strPayRefReasonMiddle)&&
                  this.getArr(m).getCoinsCode().equals(blPrpCcoins.getArr(i).getCoinsCode()))
              {
                dblMiddleCostFee = dblMiddleCostFee - Str.round(Double.parseDouble(this.getArr(m).getPlanFee()),2);
              }
            }
            for(int n=0;n<blPrpJplanCommission.getSize();n++)
            {
            	if(blPrpJplanCommission.getArr(n).getPayRefReason().equals(strPayRefReasonAgent)&&
            			blPrpJplanCommission.getArr(n).getCoinsCode().equals(blPrpCcoins.getArr(i).getCoinsCode()))
                {
                  dblAgentFee = dblAgentFee - Str.round(Double.parseDouble(blPrpJplanCommission.getArr(n).getPlanFee()),2);
                }
            }
          }
          else
          {
            dblCoinsFee = dblCoinsFee * Double.parseDouble(iSchema.getPlanFee()) / dbSumPremium;;
            dblAgentFee = dblAgentFee * Double.parseDouble(iSchema.getPlanFee()) / dbSumPremium;;
            dblMiddleCostFee = dblMiddleCostFee * Double.parseDouble(iSchema.getPlanFee()) / dbSumPremium;;
          }
          dblCoinsFee = Str.round(dblCoinsFee,2);
          dblAgentFee = Str.round(dblAgentFee,2);
          dblMiddleCostFee = Str.round(dblMiddleCostFee,2);
          

          schema = new PrpJplanFeeSchema();
          schema.setSchema(iSchema);         
          strSerialNo = "0" + intSerialNo;
          strSerialNo = strSerialNo.substring(strSerialNo.length()-2,strSerialNo.length());
          intSerialNo ++;
          schema.setSerialNo(schema.getSerialNo() + strSerialNo);
          schema.setPayRefReason(strPayRefReasonPremium);
          schema.setCertiType("P");
          schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
          schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
          schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
          schema.setPlanFee(""+dblCoinsFee);
          this.setArr(schema);

          strProportionFlag = blPrpCcoins.getArr(i).getProportionFlag() + "  ";
          
          if(strProportionFlag.substring(0,1).equals("1")&&
              Double.parseDouble(Str.chgStrZero(prpCcoinsDetailSchema.getAgentFee()))!=0)
          {
        	
            
            
        	  if (intReturn==100){
      			throw new UserException(-98, -1167, "BLPrpJplanFee.transCoinsDetail",
      					"PrpCommission表中没有业务单号"+iSchema.getPolicyNo()+"对应手续费数据" ); 
          	  }
        	  
        	prpJplanCommissionSchema = new PrpJplanCommissionSchema();
            blPrpJplanCommission.evaluate(dbpool, iSchema, prpJplanCommissionSchema, "0");
            strSerialNo = "0" + intSerialNo;
            strSerialNo = strSerialNo.substring(strSerialNo.length()-2,strSerialNo.length());
            intSerialNo ++;
            prpJplanCommissionSchema.setSerialNo(iSchema.getSerialNo() + strSerialNo);
            prpJplanCommissionSchema.setPayRefReason(strPayRefReasonAgent);
            prpJplanCommissionSchema.setCertiType("S");
            prpJplanCommissionSchema.setDisRate(dbPrpCommission.getDisRate());
            prpJplanCommissionSchema.setPayFlag("0");
            prpJplanCommissionSchema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
            prpJplanCommissionSchema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
            prpJplanCommissionSchema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
            prpJplanCommissionSchema.setPlanFee("" + dblAgentFee);
            blPrpJplanCommission.setArr(prpJplanCommissionSchema);
            
            
          }
          
          if(strProportionFlag.substring(1,2).equals("1")&&Double.parseDouble(Str.chgStrZero(prpCcoinsDetailSchema.getMiddleCostFee()))!=0)
          {
            schema = new PrpJplanFeeSchema();
            schema.setSchema(iSchema);
            strSerialNo = "0" + intSerialNo;
            strSerialNo = strSerialNo.substring(strSerialNo.length()-2,strSerialNo.length());
            intSerialNo ++;
            schema.setSerialNo(schema.getSerialNo() + strSerialNo);
            schema.setPayRefReason(strPayRefReasonMiddle);
            schema.setCertiType("E");
            schema.setValidDate(iSchema.getStartDate());
            schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
            schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
            schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
            schema.setPlanFee("" + dblMiddleCostFee);
            this.setArr(schema);
          }

          
          if(iFlag)
          {
            if(blPrpCcoins.getArr(i).getCoinsType().equals("3") &&
                Double.parseDouble(Str.chgStrZero(prpCcoinsDetailSchema.getOperateFee()))!=0)
            {
              schema = new PrpJplanFeeSchema();
              schema.setSchema(iSchema);
              strSerialNo = "0" + intSerialNo;
              strSerialNo = strSerialNo.substring(strSerialNo.length()-2,strSerialNo.length());
              intSerialNo ++;
              schema.setSerialNo(schema.getSerialNo() + strSerialNo);
              schema.setPayRefReason("P95");
              schema.setCertiType("F");
              schema.setCoinsCode(blPrpCcoins.getArr(i).getCoinsCode());
              schema.setCoinsName(blPrpCcoins.getArr(i).getCoinsName());
              schema.setCoinsType(blPrpCcoins.getArr(i).getCoinsType());
              schema.setPlanFee(prpCcoinsDetailSchema.getOperateFee());
              this.setArr(schema);
            }
          }
        }
      }
      else if("ENDORSE".equals(iBizType))
      {
        
        
        BLPrpCcoins blPrpPcoins = new BLPrpCcoins();
        BLPrpPcoinsDetail blPrpPcoinsDetail = new BLPrpPcoinsDetail();
        
        DBPrpCommission dbPrpCommission = new DBPrpCommission();
        int intReturn = dbPrpCommission.getInfo(dbpool, iSchema.getCertiNo());
    	
        blPrpPcoinsDetail.query(dbpool,iWherePart);
        if(blPrpPcoinsDetail.getSize()>0)
          blPrpPcoins.query(dbpool,"PolicyNo='"+blPrpPcoinsDetail.getArr(0).getPolicyNo()+"'");
        PrpJplanFeeSchema schema = null;
        PrpJplanCommissionSchema prpJplanCommissionSchema = null;
        String strProportionFlag = "  ";
        String strCoinsType = "";
        String strSerialNo = "";
        double dblCoinsFee = 0;
        double dblAgentFee = 0;
        double dblMiddleCostFee = 0;
        int intSerialNo = 1;
        for(int i=0; i<blPrpPcoinsDetail.getSize(); i++)
        {
          for(int j=0; j<blPrpPcoins.getSize(); j++)
          {
            if(blPrpPcoins.getArr(j).getSerialNo().
                equals(blPrpPcoinsDetail.getArr(i).getSerialNo()))
            {
              strProportionFlag = blPrpPcoins.getArr(j).getProportionFlag() + "  ";
              strCoinsType = blPrpPcoins.getArr(j).getCoinsType();
              break;
            }
          }

          if(strProportionFlag.substring(0,1).equals("1")||
              strCoinsType.equals("1"))
          {
            dblAgentFee += Str.round(Double.parseDouble(Str.chgStrZero(blPrpPcoinsDetail.getArr(i).getChgAgentFee())),2);
          }
          if(strProportionFlag.substring(1,2).equals("1")||
              strCoinsType.equals("1"))
          {
            dblMiddleCostFee += Str.round(-1 * Double.parseDouble(Str.chgStrZero(blPrpPcoinsDetail.getArr(i).getChgMiddleCostFee())),2);
          }
        }

        PrpPcoinsDetailSchema prpPcoinsDetailSchema = new PrpPcoinsDetailSchema();

        for(int i=0; i<blPrpPcoins.getSize(); i++)
        {

          if("1".equals(blPrpPcoins.getArr(i).getCoinsType()))
          {
            schema = new PrpJplanFeeSchema();
            schema.setSchema(iSchema);
            
            schema.setCertiType("E");
            schema.setValidDate(iSchema.getStartDate());
            schema.setCoinsCode(blPrpPcoins.getArr(i).getCoinsCode());
            schema.setCoinsName(blPrpPcoins.getArr(i).getCoinsName());
            schema.setCoinsType(blPrpPcoins.getArr(i).getCoinsType());
            this.setArr(schema);

            for(int j=0; j<blPrpPcoinsDetail.getSize(); j++)
            {
              if(blPrpPcoins.getArr(i).getSerialNo().
                  equals(blPrpPcoinsDetail.getArr(j).getSerialNo()))
              {
                prpPcoinsDetailSchema = blPrpPcoinsDetail.getArr(j);
                break;
              }
            }

            dblAgentFee = Str.round(dblAgentFee,2);
            dblMiddleCostFee = Str.round(dblMiddleCostFee,2);

            
            if(dblAgentFee!=0)
            {
              
              
              
            	if (intReturn==100){
        			throw new UserException(-98, -1167, "BLPrpJplanFee.transCoinsDetail",
        					"PrpCommission表中没有业务单号"+iSchema.getCertiNo()+"对应手续费数据" ); 
            	}
            	
              prpJplanCommissionSchema = new PrpJplanCommissionSchema();
              blPrpJplanCommission.evaluate(dbpool, iSchema, prpJplanCommissionSchema, "0");
              prpJplanCommissionSchema.setPayRefReason("P90");
              prpJplanCommissionSchema.setCertiType("S");
              prpJplanCommissionSchema.setDisRate(dbPrpCommission.getDisRate());
              prpJplanCommissionSchema.setPayFlag("0");
              prpJplanCommissionSchema.setCoinsCode(blPrpPcoins.getArr(i).getCoinsCode());
              prpJplanCommissionSchema.setCoinsName(blPrpPcoins.getArr(i).getCoinsName());
              prpJplanCommissionSchema.setCoinsType(blPrpPcoins.getArr(i).getCoinsType());
              prpJplanCommissionSchema.setPlanFee("" + dblAgentFee);
              blPrpJplanCommission.setArr(prpJplanCommissionSchema);
              
            }
            
            if(dblMiddleCostFee!=0)
            {
              schema = new PrpJplanFeeSchema();
              schema.setSchema(iSchema);
              schema.setPayRefReason("P40");
              schema.setCertiType("E");
              schema.setValidDate(iSchema.getStartDate());
              schema.setCoinsCode(blPrpPcoins.getArr(i).getCoinsCode());
              schema.setCoinsName(blPrpPcoins.getArr(i).getCoinsName());
              schema.setCoinsType(blPrpPcoins.getArr(i).getCoinsType());
              schema.setPlanFee("" + dblMiddleCostFee);
              this.setArr(schema);
            }
            continue;
          }

          String strPayRefReasonPremium = "";
          String strPayRefReasonAgent = "";
          String strPayRefReasonMiddle = "";
          if("2".equals(blPrpPcoins.getArr(i).getCoinsType()))
          {
            if(iSchema.getPayRefReason().startsWith("R"))
              strPayRefReasonPremium = "P82";
            else
              strPayRefReasonPremium = "R82";
            strPayRefReasonAgent = "P92";
            strPayRefReasonMiddle = "P42";
          }
          else if("3".equals(blPrpPcoins.getArr(i).getCoinsType()))
          {
            if(iSchema.getPayRefReason().startsWith("R"))
              strPayRefReasonPremium = "P81";
            else
              strPayRefReasonPremium = "R81";
            strPayRefReasonAgent = "P91";
            strPayRefReasonMiddle = "P41";
          }
          else
          {
            strPayRefReasonPremium = "";
            strPayRefReasonAgent = "";
            strPayRefReasonMiddle = "";
          }

          for(int j=0; j<blPrpPcoinsDetail.getSize(); j++)
          {
            if(blPrpPcoins.getArr(i).getSerialNo().
                equals(blPrpPcoinsDetail.getArr(j).getSerialNo()))
            {
              prpPcoinsDetailSchema = blPrpPcoinsDetail.getArr(j);
              break;
            }
          }

          dblCoinsFee = -1 * Double.parseDouble(Str.chgStrZero(prpPcoinsDetailSchema.getChgCoinsPremium()));
          dblAgentFee = -1 * Double.parseDouble(Str.chgStrZero(prpPcoinsDetailSchema.getChgAgentFee()));
          dblMiddleCostFee = Double.parseDouble(Str.chgStrZero(prpPcoinsDetailSchema.getChgMiddleCostFee()));

          dblCoinsFee = Str.round(dblCoinsFee,2);
          dblAgentFee = Str.round(dblAgentFee,2);
          dblMiddleCostFee = Str.round(dblMiddleCostFee,2);
          

          if(dblCoinsFee!=0)
          {
            schema = new PrpJplanFeeSchema();
            schema.setSchema(iSchema);
            strSerialNo = "0" + intSerialNo;
            strSerialNo = strSerialNo.substring(strSerialNo.length()-2,strSerialNo.length());
            intSerialNo ++;
            schema.setSerialNo(schema.getSerialNo() + strSerialNo);
            schema.setPayRefReason(strPayRefReasonPremium);
            schema.setCertiType("E");
            schema.setCoinsCode(blPrpPcoins.getArr(i).getCoinsCode());
            schema.setCoinsName(blPrpPcoins.getArr(i).getCoinsName());
            schema.setCoinsType(blPrpPcoins.getArr(i).getCoinsType());
            schema.setPlanFee(""+dblCoinsFee);
            this.setArr(schema);
          }

          strProportionFlag = blPrpPcoins.getArr(i).getProportionFlag() + "  ";
          
          if(strProportionFlag.substring(0,1).equals("1")&&dblAgentFee!=0)
          {
        	
            
            
        	  if (intReturn==100){
      			throw new UserException(-98, -1167, "BLPrpJplanFee.transCoinsDetail",
      					"PrpCommission表中没有业务单号"+iSchema.getCertiNo()+"对应手续费数据" ); 
          	  }
        	  
        	prpJplanCommissionSchema = new PrpJplanCommissionSchema();
            blPrpJplanCommission.evaluate(dbpool, iSchema, prpJplanCommissionSchema, "0");
            strSerialNo = "0" + intSerialNo;
            strSerialNo = strSerialNo.substring(strSerialNo.length()-2,strSerialNo.length());
            intSerialNo ++;
            prpJplanCommissionSchema.setSerialNo(iSchema.getSerialNo() + strSerialNo);
            prpJplanCommissionSchema.setPayRefReason(strPayRefReasonAgent);
            prpJplanCommissionSchema.setCertiType("S");
            prpJplanCommissionSchema.setDisRate(dbPrpCommission.getDisRate());
            prpJplanCommissionSchema.setPayFlag("0");
            prpJplanCommissionSchema.setCoinsCode(blPrpPcoins.getArr(i).getCoinsCode());
            prpJplanCommissionSchema.setCoinsName(blPrpPcoins.getArr(i).getCoinsName());
            prpJplanCommissionSchema.setCoinsType(blPrpPcoins.getArr(i).getCoinsType());
            prpJplanCommissionSchema.setPlanFee("" + dblAgentFee);
            blPrpJplanCommission.setArr(prpJplanCommissionSchema);
            
          }
          
          if(strProportionFlag.substring(1,2).equals("1")&&dblMiddleCostFee!=0)
          {
            schema = new PrpJplanFeeSchema();
            schema.setSchema(iSchema);
            strSerialNo = "0" + intSerialNo;
            strSerialNo = strSerialNo.substring(strSerialNo.length()-2,strSerialNo.length());
            intSerialNo ++;
            schema.setSerialNo(schema.getSerialNo() + strSerialNo);
            schema.setPayRefReason(strPayRefReasonMiddle);
            schema.setCertiType("E");
            schema.setValidDate(iSchema.getStartDate());
            schema.setCoinsCode(blPrpPcoins.getArr(i).getCoinsCode());
            schema.setCoinsName(blPrpPcoins.getArr(i).getCoinsName());
            schema.setCoinsType(blPrpPcoins.getArr(i).getCoinsType());
            schema.setPlanFee("" + dblMiddleCostFee);
            this.setArr(schema);
          }

          
          /*
          if(iSchema.getCoinsType().equals("3") &&
              Double.parseDouble(Str.chgStrZero(prpPcoinsDetailSchema.getChgOperateFee()))!=0)
          {
            schema = new PrpJplanFeeSchema();
            schema.setSchema(iSchema);
            strSerialNo = "0" + intSerialNo;
            strSerialNo = strSerialNo.substring(strSerialNo.length()-2,strSerialNo.length());
            intSerialNo ++;
            schema.setSerialNo(schema.getSerialNo() + strSerialNo);
            schema.setPayRefReason("P95");
            schema.setCertiType("F");
            schema.setCoinsCode(blPrpPcoins.getArr(i).getCoinsCode());
            schema.setCoinsName(blPrpPcoins.getArr(i).getCoinsName());
            schema.setCoinsType(blPrpPcoins.getArr(i).getCoinsType());
            schema.setPlanFee(prpPcoinsDetailSchema.getChgOperateFee());
            this.setArr(schema);
          }
          */
        }
      }
      else if("COMPENSATE".equals(iBizType))
      {
        String strWherePart = "";
        String strPayRefReason = "";
        String strSerialNo = "";
        BLPrpLcfeeCoins blPrpLcfeeCoins = new BLPrpLcfeeCoins();
        PrpLcfeeCoinsSchema prpLcfeeCoinsSchema = null;
        PrpLcfeeCoinsSchema prpLcfeeCoinsSchemaTmp = null;
        PrpJplanFeeSchema schema = null;
        double dblPaidFee = 0;
        double dblCoinsPaidFee = 0;
        int intSerialNo = 1;

        strWherePart = iWherePart + " AND CoinsType='1'";
        blPrpLcfeeCoins.query(dbpool,strWherePart);

        for(int i=0;i<blPrpLcfeeCoins.getSize();i++)
        {
          strSerialNo = "" + intSerialNo;
          intSerialNo ++;
          prpLcfeeCoinsSchema = blPrpLcfeeCoins.getArr(i);
          dblPaidFee = Str.round(Double.parseDouble(prpLcfeeCoinsSchema.getCoinsSumpaid()),2);

          if(prpLcfeeCoinsSchema.getLossFeeType().equals("0"))
          {
            strWherePart = iWherePart + " AND LossFeeType='0' And CoinsType<>'1'";
          }
          else
          {
            strWherePart = iWherePart + " AND LossFeeType='1' And CoinsType<>'1'"
                         + " And ChargeCode='" + prpLcfeeCoinsSchema.getChargeCode() + "'";
          }
          BLPrpLcfeeCoins blPrpLcfeeCoinsTmp = new BLPrpLcfeeCoins();
          blPrpLcfeeCoinsTmp.query(dbpool,strWherePart);
          for(int j=0;j<blPrpLcfeeCoinsTmp.getSize();j++)
          {
            prpLcfeeCoinsSchemaTmp = blPrpLcfeeCoinsTmp.getArr(j);
            dblCoinsPaidFee = Str.round(Double.parseDouble(prpLcfeeCoinsSchemaTmp.getCoinsSumpaid()),2);
            dblPaidFee = Str.round(dblPaidFee + dblCoinsPaidFee,2);
            dblCoinsPaidFee = Str.round(-1*dblCoinsPaidFee,2);
            if(prpLcfeeCoinsSchemaTmp.getLossFeeType().equals("0"))
            {
              if(prpLcfeeCoinsSchemaTmp.getCoinsType().equals("2"))
                strPayRefReason = "R60";
              else
                strPayRefReason = "S60";
            }
            else
              strPayRefReason = this.getPayRefReason(prpLcfeeCoinsSchemaTmp.getChargeCode(),
                  prpLcfeeCoinsSchemaTmp.getCoinsType());
            schema = new PrpJplanFeeSchema();
            schema.setSchema(iSchema);
            schema.setCurrency1(prpLcfeeCoinsSchemaTmp.getCurrency());
            schema.setPayRefReason(strPayRefReason);
            schema.setSerialNo(""+intSerialNo);
            intSerialNo++;
            schema.setCoinsCode(prpLcfeeCoinsSchemaTmp.getCoinsCode());
            schema.setCoinsName(prpLcfeeCoinsSchemaTmp.getCoinsName());
            schema.setCoinsType(prpLcfeeCoinsSchemaTmp.getCoinsType());
            schema.setPlanFee("" + dblCoinsPaidFee);

            this.setArr(schema);
          }

          if(prpLcfeeCoinsSchema.getLossFeeType().equals("0"))
          {
            strPayRefReason = "P60";
          }
          else
            strPayRefReason = this.getPayRefReason(prpLcfeeCoinsSchema.getChargeCode(),
                prpLcfeeCoinsSchema.getCoinsType());
          schema = new PrpJplanFeeSchema();
          schema.setSchema(iSchema);
          schema.setCurrency1(prpLcfeeCoinsSchema.getCurrency());
          schema.setPayRefReason(strPayRefReason);
          schema.setSerialNo(strSerialNo);
          schema.setCoinsCode(prpLcfeeCoinsSchema.getCoinsCode());
          schema.setCoinsName(prpLcfeeCoinsSchema.getCoinsName());
          schema.setCoinsType(prpLcfeeCoinsSchema.getCoinsType());
          schema.setPlanFee("" + dblPaidFee);

          this.setArr(schema);
        }
      }
    }


    /**
     * @author lijibin 20050609 转手续费
     * @param dbpool 数据库连接池
     * @param iWherePart 转数的条件
     * @param iSchema
     * @throws Exception
     */
    public void transCommission(DbPool dbpool,String iWherePart,BLPrpJplanFee blPrpJplanFee) throws Exception
    {
      BLPrpCommission blPrpCommission = new BLPrpCommission();
      blPrpCommission.query(dbpool,iWherePart);
      if(blPrpCommission.getSize()==0)
        return;
      double dblDisFee = Double.parseDouble(blPrpCommission.getArr(0).getDisFee());
      double dblLeftFee = dblDisFee;
      double dblPlanFee = 0;
      
      double dblSumPremium = 0;
      for(int i=0; i<blPrpJplanFee.getSize(); i++)
      {
        dblSumPremium += Double.parseDouble(blPrpJplanFee.getArr(i).getPlanFee());
      }
      PrpJplanFeeSchema schema = null;
      for(int i=0; i<blPrpJplanFee.getSize(); i++)
      {
        schema = new PrpJplanFeeSchema();
        schema.setSchema(blPrpJplanFee.getArr(i));
        schema.setCertiType("S");
        schema.setPayRefReason("P90");
        schema.setCurrency1(blPrpCommission.getArr(0).getCurrency());
        if(i==blPrpJplanFee.getSize()-1)
          dblPlanFee = dblLeftFee;
        else
          dblPlanFee = dblDisFee * (Double.parseDouble(blPrpJplanFee.getArr(i).getPlanFee())/dblSumPremium);
        dblPlanFee = Str.round(Str.round(dblPlanFee,8),2);
        dblLeftFee -= dblPlanFee;
        schema.setPlanFee(""+dblPlanFee);
        this.setArr(schema);
      }
    }

    /**
     * @author lijibin 20050609 转入中间成本
     * @param dbpool 数据库连接池
     * @param iWherePart 转数的条件
     * @param iSchema
     * @throws Exception
     */
    public void transDisPremium(DbPool dbpool,String iWherePart,int iCount) throws Exception
    {
      BLPrpMiddleCost blPrpMiddleCost = new BLPrpMiddleCost();
      blPrpMiddleCost.query(dbpool,iWherePart);
      if(blPrpMiddleCost.getSize()==0)
        return;
      double dblDisFee = -1*Double.parseDouble(blPrpMiddleCost.getArr(0).getDisFee());
      double dblLeftFee = dblDisFee;
      double dblPlanFee = 0;
      
      double dblSumPremium = 0;
      for(int i=0; i<iCount; i++)
      {
        
      	if(!this.getArr(i).getCertiType().equals("F")){
            dblSumPremium += Double.parseDouble(this.getArr(i).getPlanFee());
        }
      }
      PrpJplanFeeSchema schema = null;
      for(int i=0; i<iCount; i++)
      {
        
        if(!this.getArr(i).getCertiType().equals("F")){
          	schema = new PrpJplanFeeSchema();
            schema.setSchema(this.getArr(i));
            schema.setCertiType("E");
            schema.setCertiNo(blPrpMiddleCost.getArr(0).getEndorseNo());
            schema.setPayRefReason("P40");
            schema.setValidDate(this.getArr(i).getStartDate());
            schema.setCurrency1(blPrpMiddleCost.getArr(0).getCurrency());
            if(i==iCount-1)
              dblPlanFee = dblLeftFee;
            else
              dblPlanFee = dblDisFee * (Double.parseDouble(this.getArr(i).getPlanFee())/dblSumPremium);
            dblPlanFee = Str.round(Str.round(dblPlanFee,8),2);
            dblLeftFee -= dblPlanFee;
            schema.setPlanFee(""+dblPlanFee);
            this.setArr(schema);
        }
      }
    }

    /**
     * @author mozhanfeng 20050820 批改传收付费调用
     * @param dbpool
     * @param iWherePart
     * @param iCount
     * @param dbPrpPhead
     * @param dbPrpCmain
     * @throws Exception
     */
    public void transCommission(DbPool dbpool,String iWherePart,BLPrpJplanFee blPrpJplanFee,DBPrpPhead dbPrpPhead,DBPrpCmain dbPrpCmain,String iRiskCode) throws Exception
    {
      BLPrpCommission blPrpCommission = new BLPrpCommission();
      blPrpCommission.query(dbpool,iWherePart);
      if(blPrpCommission.getSize()==0)
        return;
      double dblDisFee = Double.parseDouble(blPrpCommission.getArr(0).getDisFee());
      double dblLeftFee = dblDisFee;
      double dblPlanFee = 0;
      
      double dblSumPremium = 0;
      for(int i=0; i<blPrpJplanFee.getSize(); i++)
      {
        dblSumPremium += Double.parseDouble(blPrpJplanFee.getArr(i).getPlanFee());
      }
      PrpJplanFeeSchema schema = null;
      for(int i=0; i<blPrpJplanFee.getSize(); i++)
      {
        schema = new PrpJplanFeeSchema();
        schema.setSchema(blPrpJplanFee.getArr(i));
        schema.setCertiType("S");
        schema.setPayRefReason("P90");
        schema.setCurrency1(blPrpCommission.getArr(0).getCurrency());
        if(i==blPrpJplanFee.getSize()-1)
          dblPlanFee = dblLeftFee;
        else
          dblPlanFee = dblDisFee * (Double.parseDouble(blPrpJplanFee.getArr(i).getPlanFee())/dblSumPremium);
        dblPlanFee = Str.round(Str.round(dblPlanFee,8),2);
        dblLeftFee -= dblPlanFee;
        schema.setPlanFee(""+dblPlanFee);
        this.setArr(schema);
      }
      if(blPrpJplanFee.getSize()==0)
      {
        schema = new PrpJplanFeeSchema();
        schema.setCertiType("S");
        schema.setCertiNo(dbPrpPhead.getEndorseNo());
        schema.setSerialNo("1");
        schema.setPolicyNo(dbPrpPhead.getPolicyNo());
        
        if(dbPrpPhead.getEndorType().indexOf("57")>=0){
            schema.setPayRefReason("P93");            
        }else{
            schema.setPayRefReason("P90");            
        }
        schema.setClassCode(dbPrpCmain.getClassCode());
        schema.setRiskCode(dbPrpCmain.getRiskCode());
        schema.setContractNo(dbPrpCmain.getContractNo());
        schema.setAppliCode(dbPrpCmain.getAppliCode());
        schema.setAppliName(dbPrpCmain.getAppliName());
        schema.setInsuredCode(dbPrpCmain.getInsuredCode());
        schema.setInsuredName(dbPrpCmain.getInsuredName());
        schema.setStartDate(dbPrpPhead.getValidDate());
        schema.setEndDate(dbPrpCmain.getEndDate());
        schema.setValidDate(dbPrpPhead.getValidDate());
        schema.setPayNo("1");
        schema.setCurrency1(blPrpCommission.getArr(0).getCurrency());
        schema.setPlanFee(dblDisFee+"");
        schema.setPlanDate(dbPrpPhead.getValidDate());
        schema.setComCode(dbPrpCmain.getComCode());
        schema.setMakeCom(dbPrpCmain.getMakeCom());
        schema.setAgentCode(dbPrpCmain.getAgentCode());
        schema.setHandler1Code(dbPrpCmain.getHandler1Code());
        schema.setHandlerCode(dbPrpCmain.getHandlerCode());
        schema.setUnderWriteDate(dbPrpPhead.getUnderWriteEndDate());
        schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        schema.setCenterCode(blPrpDcompany.getCenterCodeNew(dbpool,dbPrpCmain.getComCode()));
        schema.setBranchCode("");
        schema.setAccBookType("");
        schema.setAccBookCode("");
        schema.setYearMonth("");
        
        schema.setVoucherNo("0");
        
        schema.setExchangeRate("1.0");
        schema.setPlanFeeCNY("0");
        schema.setPayRefFee("0");
        schema.setRealPayRefFee("0");
        
        schema.setBusinessNature(dbPrpCmain.getBusinessNature());
        
        
  		if("19".equals(dbPrpPhead.getEndorType())){
  			schema.setFlag("1");
  		}else{
  			schema.setFlag("0");
  		}
          if("1".equals(dbPrpCmain.getAllinsFlag())){
          	schema.setFlag(schema.getFlag()+"1");
          }
          else{
          	schema.setFlag(schema.getFlag()+"0");
          }
          
        
        if(iRiskCode != null && iRiskCode != "" && iRiskCode.substring(0,2).equals("30")){
        	schema.setFlag(schema.getFlag()+"1");
        	schema.setFlag1(iRiskCode);
        }else{
        	schema.setFlag(schema.getFlag()+"0");
        }
        
        
        if(dbPrpCmain.getRiskCode().equals("0507")){
            this.getCarNature(dbpool,schema);








        }
        this.setArr(schema);
      }
    }

    /**
     * @author mozhanfeng 20050820 批改传收付费调用
     * @param dbpool
     * @param iWherePart
     * @param iCount
     * @param dbPrpPhead
     * @param dbPrpCmain
     * @throws Exception
     */
    public void transDisPremium(DbPool dbpool,String iWherePart,int iCount,DBPrpPhead dbPrpPhead,DBPrpCmain dbPrpCmain,String iRiskCode) throws Exception
    {
      BLPrpMiddleCost blPrpMiddleCost = new BLPrpMiddleCost();
      blPrpMiddleCost.query(dbpool,iWherePart);
      if(blPrpMiddleCost.getSize()==0)
        return;
      double dblDisFee = -1*Double.parseDouble(blPrpMiddleCost.getArr(0).getDisFee());
      double dblLeftFee = dblDisFee;
      double dblPlanFee = 0;
      
      double dblSumPremium = 0;
      for(int i=0; i<iCount; i++)
      {
        dblSumPremium += Double.parseDouble(this.getArr(i).getPlanFee());
      }
      PrpJplanFeeSchema schema = null;
      for(int i=0; i<iCount; i++)
      {
        schema = new PrpJplanFeeSchema();
        schema.setSchema(this.getArr(i));
        schema.setCertiType("E");
        schema.setCertiNo(blPrpMiddleCost.getArr(0).getEndorseNo());
        schema.setPayRefReason("P40");
        schema.setValidDate(this.getArr(i).getStartDate());
        schema.setCurrency1(blPrpMiddleCost.getArr(0).getCurrency());
        if(i==iCount-1)
          dblPlanFee = dblLeftFee;
        else
          dblPlanFee = dblDisFee * (Double.parseDouble(this.getArr(i).getPlanFee())/dblSumPremium);
        dblPlanFee = Str.round(Str.round(dblPlanFee,8),2);
        dblLeftFee -= dblPlanFee;
        schema.setPlanFee(""+dblPlanFee);
        this.setArr(schema);
      }
      if(iCount==0)
      {
        schema = new PrpJplanFeeSchema();
        schema.setCertiType("E");
        schema.setCertiNo(dbPrpPhead.getEndorseNo());
        schema.setSerialNo("1");
        schema.setPolicyNo(dbPrpPhead.getPolicyNo());
        schema.setPayRefReason("P40");
        schema.setClassCode(dbPrpCmain.getClassCode());
        schema.setRiskCode(dbPrpCmain.getRiskCode());
        schema.setContractNo(dbPrpCmain.getContractNo());
        schema.setAppliCode(dbPrpCmain.getAppliCode());
        schema.setAppliName(dbPrpCmain.getAppliName());
        schema.setInsuredCode(dbPrpCmain.getInsuredCode());
        schema.setInsuredName(dbPrpCmain.getInsuredName());
        schema.setStartDate(dbPrpPhead.getValidDate());
        schema.setEndDate(dbPrpCmain.getEndDate());
        schema.setValidDate(dbPrpPhead.getValidDate());
        schema.setPayNo("1");
        schema.setCurrency1(blPrpMiddleCost.getArr(0).getCurrency());
        schema.setPlanFee(dblDisFee+"");
        schema.setPlanDate(dbPrpPhead.getValidDate());
        schema.setComCode(dbPrpCmain.getComCode());
        schema.setMakeCom(dbPrpCmain.getMakeCom());
        schema.setAgentCode(dbPrpCmain.getAgentCode());
        schema.setHandler1Code(dbPrpCmain.getHandler1Code());
        schema.setHandlerCode(dbPrpCmain.getHandlerCode());
        schema.setUnderWriteDate(dbPrpPhead.getUnderWriteEndDate());
        schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        schema.setCenterCode(blPrpDcompany.getCenterCodeNew(dbpool,dbPrpCmain.getComCode()));
        schema.setBranchCode("");
        schema.setAccBookType("");
        schema.setAccBookCode("");
        schema.setYearMonth("");
        
        schema.setVoucherNo("0");
        
        schema.setExchangeRate("1.0");
        schema.setPlanFeeCNY("0");
        schema.setPayRefFee("0");
        schema.setRealPayRefFee("0");
        
        schema.setBusinessNature(dbPrpCmain.getBusinessNature());
        
        
  		if("19".equals(dbPrpPhead.getEndorType())){
  			schema.setFlag("1");
  		}else{
  			schema.setFlag("0");
  		}
          if("1".equals(dbPrpCmain.getAllinsFlag())){
          	schema.setFlag(schema.getFlag()+"1");
          }
          else{
          	schema.setFlag(schema.getFlag()+"0");
          }
          
        
        if(iRiskCode != null && iRiskCode != "" && iRiskCode.substring(0,2).equals("30")){
        	schema.setFlag(schema.getFlag()+"1");
        	schema.setFlag1(iRiskCode);
        }else{
        	schema.setFlag(schema.getFlag()+"0");
        }
        
        this.setArr(schema);
      }
    }

    /**
     * @author lijibin 20050615 自动转入XXXXX的应收应付凭证
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iDate 截至日期
     * @param iOperatorCode 操作员代码
     * @throws Exception
     */
    public void transAccount(String iAccBookType,
                             String iAccBookCode, String iCenterCode,
                             String iBranchCode, String iDate, String iOperatorCode)
       throws UserException,Exception
    {
        DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
            this.transAccountAll(dbpool,iAccBookType,iAccBookCode,iCenterCode,iBranchCode,iDate,iOperatorCode);
        }catch(UserException ue){
            throw ue;
        }catch(Exception e){
            throw e;
        }finally {        
            dbpool.close();
        }
    }

    /**
     * @author lijibin 20070409 自动转入XXXXX的应收应付凭证（带dbpool）
     * @param dbpool 数据库连接池
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iDate 截至日期
     * @param iOperatorCode 操作员代码
     * @throws Exception
     */
    public void transAccountAll(DbPool dbpool, String iAccBookType,
                             String iAccBookCode, String iCenterCode,
                             String iBranchCode, String iDate, String iOperatorCode)
       throws UserException,Exception
    {
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        
        String strWherePart = " CenterFlag IN ('1','2') AND ValidStatus='1'";
        strWherePart += Str.convertString("ComCode",iCenterCode,"=");
        strWherePart += " ORDER BY ComCode";
        blPrpDcompany.query(dbpool,strWherePart,0);
        for(int i=0; i<blPrpDcompany.getSize(); i++){
            iBranchCode = blPrpDcompany.getArr(i).getComCode();
            try{
                dbpool.beginTransaction();
                this.logPrintln("kai shi zhuan ru "+iBranchCode+" ying shou bao fei");
                
                this.transAccount(dbpool,iAccBookType,iAccBookCode,iCenterCode,iBranchCode,iDate,iOperatorCode);
                
                dbpool.commitTransaction();
                dbpool.beginTransaction();
                
                this.logPrintln("kai shi zhuan ru "+iBranchCode+" dai shou che chuan shui ");
                this.transCarshipTax(dbpool,iAccBookType,iAccBookCode,iCenterCode,iBranchCode,iDate,iOperatorCode); 
                
                
                
                dbpool.commitTransaction();
                
                this.logPrintln("kai shi zhuan ru "+iBranchCode+" ying fu shou xu fei");
                dbpool.beginTransaction();
                
                
                
                BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
                blPrpJplanCommission.transAccountS(dbpool,iAccBookType,iAccBookCode,iCenterCode,iBranchCode,iDate,iOperatorCode);
                
                dbpool.commitTransaction();
            }
            catch(UserException ue) {
                dbpool.rollbackTransaction();
                this.logPrintln(ue.getErrorMessage());
            }
            catch(Exception e) {
                dbpool.rollbackTransaction();
                this.logPrintln(e.getMessage());
            }
        }
    }
    /**
     * @author xushaojiang 20070920 自动转入XXXXX的应收XX凭证（带dbpool）
     * @param dbpool 数据库连接池
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iDate 截至日期
     * @param iOperatorCode 操作员代码
     * @throws Exception
     */
    public void transAccountPE(DbPool dbpool, String iAccBookType,
                             String iAccBookCode, String iCenterCode,
                             String iBranchCode, String iDate, String iOperatorCode)
       throws UserException,Exception
    {
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        
        String strWherePart = " CenterFlag IN ('1','2') AND ValidStatus='1'";
        strWherePart += Str.convertString("ComCode",iCenterCode,"=");
        strWherePart += " ORDER BY ComCode";
        blPrpDcompany.query(dbpool,strWherePart,0);
        for(int i=0; i<blPrpDcompany.getSize(); i++){
            iBranchCode = blPrpDcompany.getArr(i).getComCode();
            try{
                dbpool.beginTransaction();
                this.logPrintln("kai shi zhuan ru "+iBranchCode+" ying shou bao fei");
                
                this.transAccount(dbpool,iAccBookType,iAccBookCode,iCenterCode,iBranchCode,iDate,iOperatorCode);
                dbpool.commitTransaction();            
                }
            catch(UserException ue) {
                dbpool.rollbackTransaction();
                this.logPrintln(ue.getErrorMessage());
            }
            catch(Exception e) {
                dbpool.rollbackTransaction();
                this.logPrintln(e.getMessage());
            }
        }
    }
    /**
     * @author xushaojiang 20070920 自动转入XXXXX的应付手续费凭证（带dbpool）
     * @param dbpool 数据库连接池
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iDate 截至日期
     * @param iOperatorCode 操作员代码
     * @throws Exception
     */
    public void transAccounts(DbPool dbpool, String iAccBookType,
                             String iAccBookCode, String iCenterCode,
                             String iBranchCode, String iDate, String iOperatorCode)
       throws UserException,Exception
    {
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        BLPrpJplanCommission  blPrpJplanCommission = null;
        
        String strWherePart = " CenterFlag IN ('1','2') AND ValidStatus='1'";
        strWherePart += Str.convertString("ComCode",iCenterCode,"=");
        strWherePart += " ORDER BY ComCode";
        blPrpDcompany.query(dbpool,strWherePart,0);
        for(int i=0; i<blPrpDcompany.getSize(); i++){
            iBranchCode = blPrpDcompany.getArr(i).getComCode();
            try{
                dbpool.beginTransaction();
                this.logPrintln("kai shi zhuan ru "+iBranchCode+" ying fu shou xu fei");
                blPrpJplanCommission = new BLPrpJplanCommission();  
                
                blPrpJplanCommission.transAccountS(dbpool,iAccBookType,iAccBookCode,iCenterCode,iBranchCode,iDate,iOperatorCode);
                dbpool.commitTransaction();           
                }
            catch(UserException ue) {
                dbpool.rollbackTransaction();
                this.logPrintln(ue.getErrorMessage());
            }
            catch(Exception e) {
                dbpool.rollbackTransaction();
                this.logPrintln(e.getMessage());
            }
        }
    }
    /**
     * @author xushaojiang 20070920 自动转入XXXXX的应收车船税凭证（带dbpool）
     * @param dbpool 数据库连接池
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iDate 截至日期
     * @param iOperatorCode 操作员代码
     * @throws Exception
     */
    public void transAccountCarShipTax(DbPool dbpool, String iAccBookType,
                             String iAccBookCode, String iCenterCode,
                             String iBranchCode, String iDate, String iOperatorCode)
       throws UserException,Exception
    {
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        
        String strWherePart = " CenterFlag IN ('1','2') AND ValidStatus='1'";
        strWherePart += Str.convertString("ComCode",iCenterCode,"=");
        strWherePart += " ORDER BY ComCode";
        blPrpDcompany.query(dbpool,strWherePart,0);
        for(int i=0; i<blPrpDcompany.getSize(); i++){
            iBranchCode = blPrpDcompany.getArr(i).getComCode();
            try{
                dbpool.beginTransaction();
                this.logPrintln("kai shi zhuan ru "+iBranchCode+" ying shou che chuan shui");
                
                this.transCarshipTax(dbpool,iAccBookType,iAccBookCode,iCenterCode,iBranchCode,iDate,iOperatorCode);
                dbpool.commitTransaction();            }
            catch(UserException ue) {
                dbpool.rollbackTransaction();
                this.logPrintln(ue.getErrorMessage());
            }
            catch(Exception e) {
                dbpool.rollbackTransaction();
                this.logPrintln(e.getMessage());
            }
        }
    }    
    /**
     * @author lijibin 20050615 自动转入XXXXX的应收应付凭证（不带dbpool）
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iDate 截至日期
     * @param iOperatorCode 操作员代码
     * @throws Exception
     */
    public void transAccountC(String iAccBookType,
                             String iAccBookCode, String iCenterCode,
                             String iBranchCode, String iDate, String iOperatorCode)
       throws UserException,Exception
    {
        DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
            this.transAccountCAll(dbpool,iAccBookType,iAccBookCode,iCenterCode,iBranchCode,iDate,iOperatorCode);
			this.transAccountZAll(dbpool, iAccBookType, iAccBookCode,
					iCenterCode, iBranchCode, iDate, iOperatorCode);
        } catch(UserException ue) {
            throw ue;
        } catch(Exception e) {
            throw e;
        }finally {
            dbpool.close();
        }
    }

    /**
     * @author lijibin 20070409 自动转入XXXXX的应付赔款类凭证（带dbpool）
     * @param dbpool 数据库连接池
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iDate 截至日期
     * @param iOperatorCode 操作员代码
     * @throws Exception
     */
    public void transAccountCAll(DbPool dbpool, String iAccBookType,
                             String iAccBookCode, String iCenterCode,
                             String iBranchCode, String iDate, String iOperatorCode)
       throws UserException,Exception
    {
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        
        String strWherePart = " CenterFlag IN ('1','2') AND ValidStatus='1'";
        strWherePart += Str.convertString("ComCode",iCenterCode,"=");
        strWherePart += " ORDER BY ComCode";
        blPrpDcompany.query(dbpool,strWherePart,0);
        for(int i=0; i<blPrpDcompany.getSize(); i++){
            iBranchCode = blPrpDcompany.getArr(i).getComCode();
            try {
                dbpool.beginTransaction();
                
                this.transAccountC(dbpool,iAccBookType,iAccBookCode,iCenterCode,iBranchCode,iDate,iOperatorCode);             
                dbpool.commitTransaction();
            } catch(UserException ue) {
                dbpool.rollbackTransaction();
                ue.printStackTrace();
            } catch(Exception e) {
                dbpool.rollbackTransaction();
                e.printStackTrace();
            }
        }
    }
    
	/**
	 * @author xushaojiang 20070709 自动转入XXXXX的追偿款类凭证（带dbpool）
	 * @param dbpool
	 *            数据库连接池
	 * @param iAccBookType
	 *            帐套类型
	 * @param iAccBookCode
	 *            帐套代码
	 * @param iCenterCode
	 *            核算单位
	 * @param iBranchCode
	 *            基层单位
	 * @param iDate
	 *            截至日期
	 * @param iOperatorCode
	 *            操作员代码
	 * @throws Exception
	 */
	public void transAccountZAll(DbPool dbpool, String iAccBookType,
			String iAccBookCode, String iCenterCode, String iBranchCode,
			String iDate, String iOperatorCode) throws UserException, Exception {
		BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
		Vector vcCenterCode = new Vector();
		Vector vcComcode = new Vector();
		String centerCode = "";
		this.logPrintln("kai shi zhuan ru zhui chang kuan ");
		
		if (iCenterCode.equals("")) {
			String strSQL = "select distinct comcode as comcode from prpjplanfee where certitype='Z' and voucherno='0' and planfee!=0 "
					+ "and underwritedate <='" + iDate + "'";
			ResultSet resultSet = dbpool.query(strSQL);
			while (resultSet.next()) {
				vcComcode.add(resultSet.getString("comcode"));
			}
			resultSet.close();
			for (int i = 0; i < vcComcode.size(); i++) {
				centerCode = blPrpDcompany.getCenterCode(dbpool, vcComcode.get(
						i).toString());
				if (!vcCenterCode.contains(centerCode)) {
					vcCenterCode.add(centerCode);
				}
			}
			for (int j = 0; j < vcCenterCode.size(); j++) {
				iCenterCode = vcCenterCode.get(j).toString();
				try {
					dbpool.beginTransaction();
					this.logPrintln("kai shi zhuan ru "+iCenterCode+" de zhui chang kuan");
					this.transAccountZ(dbpool, iAccBookType, iAccBookCode,
							iCenterCode, iCenterCode, iDate, iOperatorCode);
					dbpool.commitTransaction();
				} catch (UserException ue) {
					dbpool.rollbackTransaction();
					ue.printStackTrace();
				} catch (Exception e) {
					dbpool.rollbackTransaction();
					e.printStackTrace();
				}
			}
		} else {
			try {
				dbpool.beginTransaction();
				this.transAccountZ(dbpool, iAccBookType, iAccBookCode,
						iCenterCode, iCenterCode, iDate, iOperatorCode);
				dbpool.commitTransaction();
			} catch (UserException ue) {
				dbpool.rollbackTransaction();
				ue.printStackTrace();
			} catch (Exception e) {
				dbpool.rollbackTransaction();
				e.printStackTrace();
			}
		}
	}

    /**
     * @author lijibin 20050614 自动转入XXXXX的应收XX凭证
     * @param dbpool
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iDate 截至日期
     * @param iOperatorCode 操作员代码
     * @throws Exception
     */
    public void transAccountBak(DbPool dbpool, String iAccBookType,
                             String iAccBookCode, String iCenterCode,
                             String iBranchCode, String iDate, String iOperatorCode)
       throws UserException,Exception
    {
      String strWherePart = " 1=1";
      if(iAccBookType.equals(""))
        iAccBookType = SysConst.getProperty("ACCBOOKTYPE");
      strWherePart += Str.convertString("AccBookType",iAccBookType,"=");
      strWherePart += Str.convertString("AccBookCode",iAccBookCode,"=");
      strWherePart += Str.convertString("CenterCode",iCenterCode,"=");
      strWherePart += Str.convertString("BranchCode",iBranchCode,"=");
      BLAccBookBranch blAccBookBranch = new BLAccBookBranch();
      blAccBookBranch.query(dbpool,strWherePart,0);

      BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
      for(int i=0; i<blAccBookBranch.getSize(); i++)
      {
        String strBranchCode = blAccBookBranch.getArr(i).getBranchCode();
        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(blAccBookBranch.getArr(i).getAccBookType());
        accMainVoucherSchema.setAccBookCode(blAccBookBranch.getArr(i).getAccBookCode());
        accMainVoucherSchema.setCenterCode(blAccBookBranch.getArr(i).getCenterCode());
        accMainVoucherSchema.setBranchCode(blAccBookBranch.getArr(i).getBranchCode());
        accMainVoucherSchema.setVoucherType("1");
        accMainVoucherSchema.setGenerateWay("6");
        accMainVoucherSchema.setVoucherDate(iDate);
        accMainVoucherSchema.setOperatorBranch(blAccBookBranch.getArr(i).getBranchCode());
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setVoucherFlag("1");

        
        String[] arrComCode = blPrpDcompany.getLowerComCodeNew(dbpool,strBranchCode);
        
        if(arrComCode.length<1000){
            strWherePart = "'"+strBranchCode+"'";
            for(int j=0; j<arrComCode.length; j++)
            {
              strWherePart += ",'"+arrComCode[j]+"'";
            }
            strWherePart = " ComCode IN ("+strWherePart+")"
            


                +" AND (( startdate >= underwritedate AND startdate <= '"+iDate+"' AND certitype = 'P'"
                +"  OR startdate < underwritedate AND underwritedate <= '"+iDate+"' AND certitype = 'P')"
                +"  OR "
                +" (validdate >= underwritedate AND validdate <= '"+iDate+"' AND certitype = 'E'"
                +"  OR validdate < underwritedate AND underwritedate <= '"+iDate+"' AND certitype = 'E'))"
                
                
                + " AND VoucherNo='0'"
                
                
                
                + " AND RiskCode!='YAB0'"; 
        }else{
        	String strComCodeWhere = " ( ComCode IN ('" + strBranchCode + "'"; 
        	for(int j=0;j<999;j++){
        		strComCodeWhere += ",'" + arrComCode[j] + "'";
        	}
        	strComCodeWhere += ") OR ComCode IN ('" + arrComCode[999] + "'";
        	for(int j=1000;j<arrComCode.length;j++){
        		strComCodeWhere += ",'" + arrComCode[j] + "'";
        	}
        	strComCodeWhere += " ) )";
        	strWherePart = strComCodeWhere 	
        	


                         +" AND (( startdate >= underwritedate AND startdate <= '"+iDate+"' AND certitype = 'P'"
                         +"  OR startdate < underwritedate AND underwritedate <= '"+iDate+"' AND certitype = 'P')"
                         +"  OR "
                         +" (validdate >= underwritedate AND validdate <= '"+iDate+"' AND certitype = 'E'"
                         +"  OR validdate < underwritedate AND underwritedate <= '"+iDate+"' AND certitype = 'E'))"  
                         
                         
                         + " AND VoucherNo='0'"
                         
                         + " AND RiskCode!='YAB0'";  
        }

        
        DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
        if(dbPrpJplanFee.getCount(dbpool,strWherePart+" AND ClassCode='29' ")>0){
            this.createRealRefVoucher(dbpool,strWherePart,accMainVoucherSchema);
        }
        
        this.createVoucher(dbpool,strWherePart,accMainVoucherSchema);
      }
    }
    /**
     * @desc 自动转入XXXXX的应收XX凭证    liufengyao20070208 修改
     * @param dbpool
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iDate 截至日期
     * @param iOperatorCode 操作员代码
     * @throws Exception
     */
    public void transAccount(DbPool dbpool, String iAccBookType,
                             String iAccBookCode, String iCenterCode,
                             String iBranchCode, String iDate, String iOperatorCode)
       throws UserException,Exception{
      String strWherePart = " 1=1";
      if(iAccBookType.equals(""))
        iAccBookType = SysConst.getProperty("ACCBOOKTYPE");
      strWherePart += Str.convertString("AccBookType",iAccBookType,"=");
      strWherePart += Str.convertString("AccBookCode",iAccBookCode,"=");
      strWherePart += Str.convertString("CenterCode",iCenterCode,"=");
      strWherePart += Str.convertString("BranchCode",iBranchCode,"=");
      BLAccBookBranch blAccBookBranch = new BLAccBookBranch();
      blAccBookBranch.query(dbpool,strWherePart,0);
      BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
      for(int i=0; i<blAccBookBranch.getSize(); i++)
      {
        String strBranchCode = blAccBookBranch.getArr(i).getBranchCode();
        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(blAccBookBranch.getArr(i).getAccBookType());
        accMainVoucherSchema.setAccBookCode(blAccBookBranch.getArr(i).getAccBookCode());
        accMainVoucherSchema.setCenterCode(blAccBookBranch.getArr(i).getCenterCode());
        accMainVoucherSchema.setBranchCode(blAccBookBranch.getArr(i).getBranchCode());
        accMainVoucherSchema.setVoucherType("1");
        accMainVoucherSchema.setGenerateWay("6");
        accMainVoucherSchema.setVoucherDate(iDate);
        accMainVoucherSchema.setOperatorBranch(blAccBookBranch.getArr(i).getBranchCode());
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setVoucherFlag("1");
        this.logPrintln("kai shi cha xun xia ji ji gou dai ma");
        
        String[] arrComCode = blPrpDcompany.getLowerComCodeNew(dbpool,strBranchCode);
        this.logPrintln("jie shu cha xun xia ji ji gou dai ma");
        
        if(arrComCode.length<1000){
            strWherePart = "'"+strBranchCode+"'";
            for(int j=0; j<arrComCode.length; j++)
              strWherePart += ",'"+arrComCode[j]+"'";
            
            strWherePart = " ComCode IN ("+strWherePart+")"
                + " AND (( startdate <= '"+iDate+"' "
                + " AND underwritedate <= '"+iDate+"' AND certitype = 'P')"
                + "  OR "
                + " (validdate <= '"+iDate+"' "
                + " AND underwritedate <= '"+iDate+"' AND certitype = 'E'))"  
                + " AND PayRefReason not in ('R72','R73','R74')" 
                + " AND RiskCode!='YAB0'"; 
            
            
        }else{
        	String strComCodeWhere = " ( ComCode IN ('" + strBranchCode + "'"; 
        	for(int j=0;j<999;j++){
        		strComCodeWhere += ",'" + arrComCode[j] + "'";
        	}
        	strComCodeWhere += ") OR ComCode IN ('" + arrComCode[999] + "'";
        	for(int j=1000;j<arrComCode.length;j++){
        		strComCodeWhere += ",'" + arrComCode[j] + "'";
        	}
        	strComCodeWhere += " ) )";
        	
        	strWherePart = strComCodeWhere 	     	
        	              + " AND (( startdate <= '"+iDate+"' "
                          + " AND underwritedate <= '"+iDate+"' AND certitype = 'P')"
                          + "  OR "
                          + " (validdate <= '"+iDate+"' "
                          + " AND underwritedate <= '"+iDate+"' AND certitype = 'E'))" 
                          + " AND PayRefReason not in ('R72','R73','R74')" 
                          + " AND RiskCode!='YAB0'";
        	
        }
        this.logPrintln("ping jie where tiao jian jie shu");
        
        
        String strWherePart1 = strWherePart+ " AND VoucherNo='0'";
        




        
        this.createVoucher(dbpool,strWherePart,accMainVoucherSchema);
      }
    }

    /**
     * @author lijibin 20050614 自动转入XXXXX的应收应付凭证
     * @param dbpool
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @throws Exception
     */
    public void createVoucherBak(DbPool dbpool, String iWherePart,
                              AccMainVoucherSchema iAccMainVoucherSchema
                              ) throws UserException, Exception
    {
      String strSQL = "";

      
      String strYearMonth = "";
      BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
      strSQL = " CenterCode='"+iAccMainVoucherSchema.getCenterCode()
          + "' AND AccBookType='"+iAccMainVoucherSchema.getAccBookType()
          + "' AND AccBookCode='"+iAccMainVoucherSchema.getAccBookCode()
          + "' AND AccMonthStat='3'";
      blAccMonthTrace.query(dbpool,strSQL,0);
      if(blAccMonthTrace.getSize()==0)
      {
        /*throw new UserException( -98, -1167, "BLPrpJplanFee.createVoucher",
                                "核算单位" + iAccMainVoucherSchema.getCenterCode() +
                                "还没有初始化");*/
        
        logger.info("核算单位" + iAccMainVoucherSchema.getCenterCode() + "还没有完成初始化");
        
        return;
      }else
      strYearMonth = blAccMonthTrace.getArr(0).getYearMonth();
     
      iAccMainVoucherSchema.setAuxNumber("0");
      iAccMainVoucherSchema.setYearMonth(strYearMonth);
      iAccMainVoucherSchema.setVoucherNo("");

      
      
      
      
      
      
      
      
      
      
      
      
      

      
      BLAccVoucher blAccVoucher = new BLAccVoucher();
      blAccVoucher.getBLAccMainVoucher().setArr(iAccMainVoucherSchema);

      
      strSQL = "SELECT ClassCode,RiskCode,CarNatureCode,ComCode,Handler1Code,Currency1,SUM(PlanFee) AS PlanFee"
               + " FROM PrpJplanFee WHERE "
               + iWherePart + " AND RealPayRefFee=0 GROUP BY ClassCode,RiskCode,CarNatureCode,ComCode,Handler1Code,Currency1";
      PrpJplanFeeSchema schema = null;
      
      this.initArr();
      ResultSet rs = dbpool.query(strSQL);
      while(rs.next())
      {
        schema = new PrpJplanFeeSchema();
        schema.setClassCode(rs.getString("ClassCode"));
        schema.setRiskCode(rs.getString("RiskCode"));
        schema.setCarNatureCode(rs.getString("CarNatureCode"));
        schema.setComCode(rs.getString("ComCode"));
        schema.setHandler1Code(rs.getString("Handler1Code"));
        schema.setCurrency1(rs.getString("Currency1"));
        schema.setPlanFee(""+rs.getDouble("PlanFee"));
        schema.setFlag("0");
        this.setArr(schema);
      }
      rs.close();
      
      double dblExchRate = 1;
      double dblPlanFeeCNY = 0;
      for(int i=0; i<this.getSize(); i++)
      {
        dblExchRate = PubTools.getExchangeRate(dbpool,
                                               this.getArr(i).getCurrency1(),
                                               "CNY",
                                               iAccMainVoucherSchema.getVoucherDate());
        this.getArr(i).setExchangeRate(""+dblExchRate);
        dblPlanFeeCNY = Double.parseDouble(this.getArr(i).getPlanFee())*dblExchRate;
        dblPlanFeeCNY = Str.round(Str.round(dblPlanFeeCNY,8),2);
        this.getArr(i).setPlanFeeCNY(""+dblPlanFeeCNY);
      }

      
      strSQL = "SELECT ClassCode,RiskCode,CarNatureCode,ComCode,Handler1Code,Currency1,ExchangeRate,"
          + "SUBSTR(Flag,1,1) AS Flag,SUM(PlanFee) AS PlanFee,SUM(PlanFeeCNY) AS PlanFeeCNY"
          + " FROM PrpJplanFee WHERE "
          + iWherePart
          + " AND RealPayRefFee!=0 AND SUBSTR(FLag,1,1) IN ('1','2')" 
          + " GROUP BY ClassCode,RiskCode,CarNatureCode,ComCode,Handler1Code,Currency1,ExchangeRate,SUBSTR(Flag,1,1)";
      
      rs = dbpool.query(strSQL);
      while(rs.next())
      {
        schema = new PrpJplanFeeSchema();
        schema.setClassCode(rs.getString("ClassCode"));
        schema.setRiskCode(rs.getString("RiskCode"));
        schema.setCarNatureCode(rs.getString("CarNatureCode"));
        schema.setComCode(rs.getString("ComCode"));
        schema.setHandler1Code(rs.getString("Handler1Code"));
        schema.setCurrency1(rs.getString("Currency1"));
        schema.setExchangeRate(""+rs.getDouble("ExchangeRate"));
        schema.setPlanFee(""+rs.getDouble("PlanFee"));
        schema.setPlanFeeCNY(""+rs.getDouble("PlanFeeCNY"));
        schema.setFlag(rs.getString("Flag"));
        this.setArr(schema);
      }
      rs.close();
      if(this.getSize()==0)
      {
        
        
        
        logger.info("没有要转入应收XX的数据");
        
        return;
      }

      
      this.createSubVoucher(dbpool,blAccVoucher,iAccMainVoucherSchema);

      
      BLAccSubVoucher blAccSubVoucher = blAccVoucher.getBLAccSubVoucher();
      if(blAccSubVoucher.getSize()==0)
        return;
      for (int i = 0; i < blAccSubVoucher.getSize(); i++)
      {
        blAccSubVoucher.createRawArticle(dbpool, blAccSubVoucher.getArr(i), "");
      }
      
      blAccSubVoucher.voucherComp("111");
      
      blAccSubVoucher.voucherOrder();
      blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());

      
      blAccVoucher.save(dbpool);
      this.blAccMainVoucher.setArr(blAccVoucher.getBLAccMainVoucher().getArr(0));

      
      String strVoucherNo = blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
      for(int i=0; i<this.getSize(); i++)
      {
        if(this.getArr(i).getFlag().equals("1")||this.getArr(i).getFlag().equals("2"))
          strSQL = "UPDATE PrpJplanFee SET AccBookType='" + iAccMainVoucherSchema.getAccBookType()
              + "',AccBookCode='" + iAccMainVoucherSchema.getAccBookCode()
              + "',CenterCode='" + iAccMainVoucherSchema.getCenterCode()
              + "',BranchCode='" + iAccMainVoucherSchema.getBranchCode()
              + "',YearMonth='" + iAccMainVoucherSchema.getYearMonth()
              + "',VoucherNo='" + strVoucherNo
              + "' WHERE ClassCode='" + this.getArr(i).getClassCode()
              + "' AND RiskCode='" + this.getArr(i).getRiskCode()
              + "' AND ComCode='" + this.getArr(i).getComCode()
              + "' AND Currency1='" + this.getArr(i).getCurrency1()
              + "' AND ExchangeRate='" + this.getArr(i).getExchangeRate()
              + "' AND " + iWherePart;
        else
          strSQL = "UPDATE PrpJplanFee SET AccBookType='" + iAccMainVoucherSchema.getAccBookType()
              + "',AccBookCode='" + iAccMainVoucherSchema.getAccBookCode()
              + "',CenterCode='" + iAccMainVoucherSchema.getCenterCode()
              + "',BranchCode='" + iAccMainVoucherSchema.getBranchCode()
              + "',YearMonth='" + iAccMainVoucherSchema.getYearMonth()
              + "',VoucherNo='" + strVoucherNo
              + "',ExchangeRate='" + this.getArr(i).getExchangeRate()
              + "',PlanFeeCNY=PlanFee*" + this.getArr(i).getExchangeRate()
              + " WHERE ClassCode='" + this.getArr(i).getClassCode()
              + "' AND RiskCode='" + this.getArr(i).getRiskCode()
              + "' AND ComCode='" + this.getArr(i).getComCode()
              + "' AND Currency1='" + this.getArr(i).getCurrency1()
              + "' AND " + iWherePart;
        dbpool.update(strSQL);
      }
      
      logger.info("生成应收XX凭证 "+strVoucherNo);
      
    }

    /**
     * @desc 自动应收XX挂账凭证生成主函数  
     * @author liufengyao
     * @param dbpool
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @throws Exception
     */
    public void createVoucher(DbPool dbpool, String iWherePart,AccMainVoucherSchema iAccMainVoucherSchema) 
      throws UserException, Exception{   	
      String strSQL = "";
      
      String strYearMonth = iAccMainVoucherSchema.getVoucherDate().substring(0,4)
                          + iAccMainVoucherSchema.getVoucherDate().substring(5,7);
      
      BLAccVoucher blAccVoucher = new BLAccVoucher();
      PrpJplanFeeSchema iPlanFeeschema = null;
      PrpJpayRefRecSchema iPayRefSchema = null;
      this.logPrintln("kai shi cha xun shou fu shu ju");
      this.initArr();
      
      iAccMainVoucherSchema.setAuxNumber("0");
      iAccMainVoucherSchema.setYearMonth(strYearMonth);
      iAccMainVoucherSchema.setVoucherNo("");
      blAccVoucher.getBLAccMainVoucher().setArr(iAccMainVoucherSchema);
      
      
      
      StringBuffer planBuffer = new StringBuffer();    
      planBuffer.append(" SELECT ClassCode,RiskCode,CarNatureCode,ComCode,Handler1Code,Currency1,SUM(PlanFee) AS PlanFee ");
      planBuffer.append(" FROM PrpJplanFee WHERE ");
      planBuffer.append(iWherePart);
      
      
      planBuffer.append(" AND VoucherNo='0' ");
      
      planBuffer.append(" GROUP BY ClassCode,RiskCode,CarNatureCode,ComCode,Handler1Code,Currency1 ");
      strSQL = planBuffer.toString();
      ResultSet rs = dbpool.query(strSQL);
      while(rs.next()){
    	  iPlanFeeschema = new PrpJplanFeeSchema();
    	  iPlanFeeschema.setClassCode(rs.getString("ClassCode"));
    	  iPlanFeeschema.setRiskCode(rs.getString("RiskCode"));
    	  iPlanFeeschema.setCarNatureCode(rs.getString("CarNatureCode"));
    	  iPlanFeeschema.setComCode(rs.getString("ComCode"));
    	  iPlanFeeschema.setHandler1Code(rs.getString("Handler1Code"));
    	  iPlanFeeschema.setCurrency1(rs.getString("Currency1"));
    	  iPlanFeeschema.setPlanFee(""+rs.getDouble("PlanFee"));
    	  
    	  iPlanFeeschema.setPayRefReason("");
    	  
    	  iPlanFeeschema.setFlag("0");
        this.setArr(iPlanFeeschema);
      }
      rs.close();
      this.logPrintln("prpjplanfee biao shu ju ge shu："+this.getSize());
      
      double dblExchRate = 1;
      double dblPlanFeeCNY = 0;
      for(int i=0; i<this.getSize(); i++){
        dblExchRate = PubTools.getExchangeRate(dbpool,this.getArr(i).getCurrency1(),"CNY",
                                               iAccMainVoucherSchema.getVoucherDate());
        this.getArr(i).setExchangeRate(""+dblExchRate);
        dblPlanFeeCNY = Double.parseDouble(this.getArr(i).getPlanFee())*dblExchRate;
        dblPlanFeeCNY = Str.round(Str.round(dblPlanFeeCNY,8),2);
        this.getArr(i).setPlanFeeCNY(""+dblPlanFeeCNY);
      }
      this.logPrintln("prpjplanfee biao shu ju huo qu hui lv jie shu ");
      
      
      
      this.blPrpJpayRefRec.initArr();
      StringBuffer strBuffer = new StringBuffer();
      strBuffer.append(" SELECT classcode,riskcode,payrefreason,carnaturecode,comcode,handler1code,currency1,                       ");
      strBuffer.append(" sum(planfee) as PlanFee                             ");
      strBuffer.append(" FROM prpjpayrefrec                                                                                          ");    
      strBuffer.append(" WHERE                                                                                                       ");
      strBuffer.append( iWherePart                                                                                                    );
      
      strBuffer.append(" AND payrefreason NOT IN ('P81','R81','P82','R82')                                                           ");
      strBuffer.append(" AND payrefdate < startdate                                                                                  ");    
      
      
      strBuffer.append(" AND  instr(flag,'J') <= 0 and PayReftimes<1000                                                                               "); 
      
      strBuffer.append(" GROUP BY classcode,riskcode,payrefreason,carnaturecode,comcode,handler1code,currency1                       ");
      strSQL = strBuffer.toString();
      rs = dbpool.query(strSQL);
      while(rs.next()){
    	  iPayRefSchema = new PrpJpayRefRecSchema();
    	  iPayRefSchema.setClassCode(rs.getString("ClassCode"));
    	  iPayRefSchema.setRiskCode(rs.getString("RiskCode"));
    	  iPayRefSchema.setPayRefReason("PayRefReason");
    	  iPayRefSchema.setCarNatureCode(rs.getString("CarNatureCode"));
    	  iPayRefSchema.setComCode(rs.getString("ComCode"));
    	  iPayRefSchema.setHandler1Code(rs.getString("Handler1Code"));
    	  iPayRefSchema.setCurrency1(rs.getString("Currency1"));
    	  iPayRefSchema.setPlanFee(""+rs.getDouble("PlanFee"));
    	  this.blPrpJpayRefRec.setArr(iPayRefSchema);
      }
      rs.close();
      this.logPrintln("prpjpayrefrec biao yu shou bao fei zhuan ying shou shu ju ge shu："+this.blPrpJpayRefRec.getSize());
      
      dblExchRate = 1;
      dblPlanFeeCNY = 0;
      for(int i=0; i<this.blPrpJpayRefRec.getSize(); i++){
        dblExchRate = PubTools.getExchangeRate(dbpool,
                                               this.blPrpJpayRefRec.getArr(i).getCurrency1(),
                                               "CNY",
                                               iAccMainVoucherSchema.getVoucherDate());
        this.blPrpJpayRefRec.getArr(i).setExchangeRate(""+dblExchRate);
        dblPlanFeeCNY = Double.parseDouble(this.blPrpJpayRefRec.getArr(i).getPlanFee())*dblExchRate;
        dblPlanFeeCNY = Str.round(Str.round(dblPlanFeeCNY,8),2);
        this.blPrpJpayRefRec.getArr(i).setPayRefFee(""+dblPlanFeeCNY);
      }    
      
      this.logPrintln("prpjpayrefrec biao yu shou bai fei shu ju huo qu hui lv jie shu");
      if(this.getSize()==0 && this.blPrpJpayRefRec.getSize()==0){
    	  this.logPrintln("mei you yao zhuan ru ying shou bao fei huo yu shou bao fei de shu ju");
        return;
      }
      
      this.createSubVoucher(dbpool,blAccVoucher,iAccMainVoucherSchema);
      
      BLAccSubVoucher blAccSubVoucher = blAccVoucher.getBLAccSubVoucher();
      if(blAccSubVoucher.getSize()==0){
    	  this.logPrintln("mei you sheng cheng ping zheng zi biao xin xi！");
          return;
      }

      for (int i = 0; i < blAccSubVoucher.getSize(); i++)
        blAccSubVoucher.createRawArticle(dbpool, blAccSubVoucher.getArr(i), "");
      
      blAccSubVoucher.voucherComp("111");
      
      blAccSubVoucher.voucherOrder();
      blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());
      this.logPrintln("kai shi bao cun ping zheng ");
      
      blAccVoucher.save(dbpool);
      this.blAccMainVoucher.setArr(blAccVoucher.getBLAccMainVoucher().getArr(0));
      this.logPrintln("kai shi hui xie ying shou ying fu ping zheng biao de xin xi");
      
      String strVoucherNo = blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
      for(int i=0; i<this.getSize(); i++){
          strSQL = "UPDATE PrpJplanFee SET AccBookType='" + iAccMainVoucherSchema.getAccBookType()
              + "',AccBookCode='" + iAccMainVoucherSchema.getAccBookCode()
              + "',CenterCode='" + iAccMainVoucherSchema.getCenterCode()
              + "',BranchCode='" + iAccMainVoucherSchema.getBranchCode()
              + "',YearMonth='" + iAccMainVoucherSchema.getYearMonth()
              + "',VoucherNo='" + strVoucherNo
              + "',ExchangeRate='" + this.getArr(i).getExchangeRate()
              + "',PlanFeeCNY=PlanFee*" + this.getArr(i).getExchangeRate()
              + " WHERE ClassCode='" + this.getArr(i).getClassCode()
              + "' AND RiskCode='" + this.getArr(i).getRiskCode()
              + "' AND ComCode='" + this.getArr(i).getComCode()
              + "' AND Currency1='" + this.getArr(i).getCurrency1()
              + "' AND " + iWherePart
              
              
              + "  AND VoucherNo='0'";
              
        dbpool.update(strSQL);
      }
      this.logPrintln("kai shi hui xie shi shou biao de jie zhuan xin xi ");
       
       
      StringBuffer sqlBuffer = new StringBuffer();
      sqlBuffer.append(" UPDATE PrpJpayRefRec SET flag = flag||'J'   ");
      sqlBuffer.append(" WHERE                                       ");
      sqlBuffer.append( iWherePart );
      sqlBuffer.append(" AND payrefreason NOT IN ('P81','R81','P82','R82')  ");
      sqlBuffer.append(" AND payrefdate < startdate                  ");
      
      sqlBuffer.append(" AND instr(flag,'J') <= 0  ");
      
      strSQL = sqlBuffer.toString();
      dbpool.update(strSQL);
      
      this.logPrintln("sheng cheng ying shou bao fei ping zheng "+iAccMainVoucherSchema.getCenterCode()+" "+strVoucherNo);
    }

    /**
     * 生成应收凭证的同时生成实收凭证 SangMingqian 20060804
     * @param dbpool
     * @param iWherePart 查询条件
     * @param iAccMainVoucherSchema 借用应收凭证的主信息
     * @throws UserException
     * @throws Exception
     */
    public void createRealRefVoucher(DbPool dbpool, String iWherePart,
                              AccMainVoucherSchema iAccMainVoucherSchema) throws UserException, Exception{
        String strSQL = "";
        
        
        String strYearMonth = "";
        BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
        strSQL = " CenterCode='"+iAccMainVoucherSchema.getCenterCode()
                 + "' AND AccBookType='"+iAccMainVoucherSchema.getAccBookType()
                 + "' AND AccBookCode='"+iAccMainVoucherSchema.getAccBookCode()
                 + "' AND AccMonthStat='3'";
        blAccMonthTrace.query(dbpool,strSQL,0);
        if(blAccMonthTrace.getSize()==0){
            
            logger.info("核算单位" + iAccMainVoucherSchema.getCenterCode() + "还没有完成初始化");
            
            return;
        }else
            strYearMonth = blAccMonthTrace.getArr(0).getYearMonth();
        iAccMainVoucherSchema.setAuxNumber("0");
        iAccMainVoucherSchema.setYearMonth(strYearMonth);
        iAccMainVoucherSchema.setVoucherNo("");
        
        
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        blAccVoucher.getBLAccMainVoucher().setArr(iAccMainVoucherSchema);
        
        
        strSQL = "SELECT ClassCode,RiskCode,CarNatureCode,ComCode,Handler1Code,Currency1,SUM(PlanFee) AS PlanFee"
                 + " FROM PrpJplanFee WHERE "
                 + iWherePart 
                 + " AND ClassCode='29' AND RealPayRefFee=0 GROUP BY ClassCode,RiskCode,CarNatureCode,ComCode,Handler1Code,Currency1";
        PrpJplanFeeSchema schema = null;
        
        this.initArr();
        ResultSet rs = dbpool.query(strSQL);
        while(rs.next()){
            schema = new PrpJplanFeeSchema();
            schema.setClassCode(rs.getString("ClassCode"));
            schema.setRiskCode(rs.getString("RiskCode"));
            schema.setCarNatureCode(rs.getString("CarNatureCode"));
            schema.setComCode(rs.getString("ComCode"));
            schema.setHandler1Code(rs.getString("Handler1Code"));
            schema.setCurrency1(rs.getString("Currency1"));
            schema.setPlanFee(""+rs.getDouble("PlanFee"));
            schema.setFlag("0");
            this.setArr(schema);
        }
        rs.close();
        
        double dblExchRate = 1;
        double dblPlanFeeCNY = 0;
        for(int i=0; i<this.getSize(); i++){
            dblExchRate = PubTools.getExchangeRate(dbpool,
                                                   this.getArr(i).getCurrency1(),
                                                   "CNY",
                                                   iAccMainVoucherSchema.getVoucherDate());
            this.getArr(i).setExchangeRate(""+dblExchRate);
            dblPlanFeeCNY = Double.parseDouble(this.getArr(i).getPlanFee())*dblExchRate;
            dblPlanFeeCNY = Str.round(Str.round(dblPlanFeeCNY,8),2);
            this.getArr(i).setRealPayRefFee(""+dblPlanFeeCNY);
            DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
            if(dbPrpJpayRefRec.getInfo(dbpool,this.getArr(i).getCertiType(),
                                       this.getArr(i).getCertiNo(),
                                       this.getArr(i).getSerialNo(),
                                       this.getArr(i).getPayRefReason(),
                                       "1")==0){
                dbPrpJpayRefRec.setPayRefDate(this.getArr(i).getUnderWriteDate());
                
                dbPrpJpayRefRec.setIdentifyType("1");
                dbPrpJpayRefRec.setIdentifyNumber("1");
                
                dbPrpJpayRefRec.update(dbpool);
            }
            DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
            if(dbPrpJplanFee.getInfo(dbpool,this.getArr(i).getCertiType(),
                                       this.getArr(i).getCertiNo(),
                                       this.getArr(i).getSerialNo(),
                                       this.getArr(i).getPayRefReason())==0){
                dbPrpJplanFee.setRealPayRefFee(this.getArr(i).getPlanFee());
                dbPrpJplanFee.update(dbpool);
            }
        }
        
        
        String strRemark = iAccMainVoucherSchema.getVoucherDate()+"应收XX转入投资收益";
        AccSubVoucherSchema accSubVoucherSchema = null;
        for (int i = 0; i < this.getSize(); i++)
        {
            for (int j = 0; j < 2; j++) {
                accSubVoucherSchema = new AccSubVoucherSchema();
                accSubVoucherSchema.setAccBookType(iAccMainVoucherSchema.getAccBookType());
                accSubVoucherSchema.setAccBookCode(iAccMainVoucherSchema.getAccBookCode());
                accSubVoucherSchema.setCenterCode(iAccMainVoucherSchema.getCenterCode());
                accSubVoucherSchema.setYearMonth(iAccMainVoucherSchema.getYearMonth());
                accSubVoucherSchema.setF26(iAccMainVoucherSchema.getBranchCode());
                accSubVoucherSchema.setVoucherNo(iAccMainVoucherSchema.getVoucherNo());
                accSubVoucherSchema.setSuffixNo("" + (2 * i + j + 1));
                accSubVoucherSchema.setCurrency(this.getArr(i).getCurrency1());
                accSubVoucherSchema.setF01(this.getArr(i).getClassCode());
                accSubVoucherSchema.setF05(this.getArr(i).getRiskCode());            
                accSubVoucherSchema.setF27(this.getArr(i).getHandler1Code());
                accSubVoucherSchema.setF28(this.getArr(i).getComCode());
                accSubVoucherSchema.setRemark(strRemark);
                if (j == 0) { 
                    
                	accSubVoucherSchema.setItemCode("6521");
                    accSubVoucherSchema.setDirectionIdx("02/"); 
                    accSubVoucherSchema.setDebitSource(this.getArr(i).getPlanFee());
                    accSubVoucherSchema.setExchangeRate(this.getArr(i).getExchangeRate());
                    accSubVoucherSchema.setDebitDest(this.getArr(i).getPlanFee());
                }else { 
                    accSubVoucherSchema.setItemCode("1122"); 
                    accSubVoucherSchema.setCreditSource(this.getArr(i).getPlanFee());
                    accSubVoucherSchema.setExchangeRate(this.getArr(i).getExchangeRate());
                    accSubVoucherSchema.setCreditDest(this.getArr(i).getPlanFee());
                }
                blAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
          }
        }
        
        
        BLAccSubVoucher blAccSubVoucher = blAccVoucher.getBLAccSubVoucher();
        if(blAccSubVoucher.getSize()==0)
            return;
        for (int i = 0; i < blAccSubVoucher.getSize(); i++){
            blAccSubVoucher.createRawArticle(dbpool, blAccSubVoucher.getArr(i), "");
        }
        
        blAccSubVoucher.voucherComp("111");
        
        blAccSubVoucher.voucherOrder();
        blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());
        
        
        blAccVoucher.save(dbpool);
        this.blAccMainVoucher.setArr(blAccVoucher.getBLAccMainVoucher().getArr(0));
        String strVoucherNo = blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        
        logger.info("生成应收XX转投资收益凭证 "+strVoucherNo);
        
    }
    
    /**
     * @lijibin 20050622
     * @param dbpool
     * @param iBLAccVoucher
     * @param iAccMainVoucherSchema
     * @throws Exception
     */
    public void createSubVoucherBak(DbPool dbpool, BLAccVoucher iBLAccVoucher,
                                 AccMainVoucherSchema iAccMainVoucherSchema) throws Exception
    {
      
      String strRemark = "转入" + iAccMainVoucherSchema.getVoucherDate();
      AccSubVoucherSchema accSubVoucherSchema = null;
      for (int i = 0; i < this.getSize(); i++)
      {
        
        if (this.checkAllowUnpayed(dbpool,this.getArr(i).getComCode(),
                                   this.getArr(i).getRiskCode())&&
            !this.getArr(i).getCertiType().equals("S")&&
            !this.getArr(i).getCertiType().equals("Y")&&
            !this.getArr(i).getCertiType().equals("C")&&
            !this.getArr(i).getCertiType().equals("Z"))
        {
          this.remove(i);
          i--;
          continue;
        }

        for (int j = 0; j < 2; j++) {
          accSubVoucherSchema = new AccSubVoucherSchema();
          accSubVoucherSchema.setAccBookType(iAccMainVoucherSchema.getAccBookType());
          accSubVoucherSchema.setAccBookCode(iAccMainVoucherSchema.getAccBookCode());
          accSubVoucherSchema.setCenterCode(iAccMainVoucherSchema.getCenterCode());
          accSubVoucherSchema.setYearMonth(iAccMainVoucherSchema.getYearMonth());
          accSubVoucherSchema.setF26(iAccMainVoucherSchema.getBranchCode());
          accSubVoucherSchema.setVoucherNo(iAccMainVoucherSchema.getVoucherNo());
          accSubVoucherSchema.setSuffixNo("" + (2 * i + j + 1));
          accSubVoucherSchema.setCurrency(this.getArr(i).getCurrency1());
          accSubVoucherSchema.setF01(this.getArr(i).getClassCode());
          if(this.getArr(i).getRiskCode().equals("0507")){
            accSubVoucherSchema.setF05(this.getArr(i).getRiskCode()+this.getArr(i).getCarNatureCode());            
          }else{
            accSubVoucherSchema.setF05(this.getArr(i).getRiskCode());            
          }
          accSubVoucherSchema.setF27(this.getArr(i).getHandler1Code());
          accSubVoucherSchema.setF28(this.getArr(i).getComCode());
          
          if(this.getArr(i).getCertiType().equals("S")){
            accSubVoucherSchema.setRemark(strRemark+"应付手续费");
          }else if(this.getArr(i).getCertiType().equals("Y")||
                   this.getArr(i).getCertiType().equals("C")){
            
            if(this.getArr(i).getPayRefReason().equals("P6A")){
              accSubVoucherSchema.setRemark(strRemark+"交强XXXXX垫付款");
            }
            
            else if(this.getArr(i).getPayRefReason().equals("P62")){
              accSubVoucherSchema.setRemark(strRemark+"查勘费");
            }else{
              accSubVoucherSchema.setRemark(strRemark+"应付赔款");
            }
          }else if(this.getArr(i).getCertiType().equals("J")){
            accSubVoucherSchema.setRemark(strRemark+"救助基金");
          }else if(this.getArr(i).getCertiType().equals("Z")){
              accSubVoucherSchema.setRemark(strRemark+"交强XXXXX应收追偿款");
          }else{
            accSubVoucherSchema.setRemark(strRemark+"应收XX");
          }

          if (j == 0) { 
        	
        	/*
            if (this.getArr(i).getFlag().equals("1"))
            {
              accSubVoucherSchema.setItemCode("2121"); 
              accSubVoucherSchema.setDirectionIdx("02/");
            }else if (this.getArr(i).getFlag().equals("2")){
              accSubVoucherSchema.setItemCode("2121"); 
              accSubVoucherSchema.setDirectionIdx("01/");
            }else{
              accSubVoucherSchema.setItemCode("1122"); 
            }
            if(this.getArr(i).getCertiType().equals("S")){
              accSubVoucherSchema.setItemCode("4431"); 
            }
            if(this.getArr(i).getCertiType().equals("C")||
               this.getArr(i).getCertiType().equals("Y")){
              accSubVoucherSchema.setItemCode("4401"); 
              if(this.getArr(i).getPayRefReason().equals("P6A"))
                accSubVoucherSchema.setDirectionIdx("02/");
              else
                accSubVoucherSchema.setDirectionIdx("01/");
            }
            if(this.getArr(i).getCertiType().equals("J")){
            	accSubVoucherSchema.setItemCode("1191");
            	accSubVoucherSchema.setDirectionIdx("08/"); 
            }
            
            if (this.getArr(i).getCertiType().equals("Z")) {
                accSubVoucherSchema.setItemCode("1191");
                accSubVoucherSchema.setDirectionIdx("09/01/");
            }
            */
            if (this.getArr(i).getFlag().equals("1"))
            {
              accSubVoucherSchema.setItemCode("2203"); 
              accSubVoucherSchema.setDirectionIdx("02/");
            }else if (this.getArr(i).getFlag().equals("2")){
              accSubVoucherSchema.setItemCode("2203"); 
              accSubVoucherSchema.setDirectionIdx("01/");
            }else{
              accSubVoucherSchema.setItemCode("1122"); 
            }
            if(this.getArr(i).getCertiType().equals("S")){
              accSubVoucherSchema.setItemCode("6421"); 
            }
            if(this.getArr(i).getCertiType().equals("C")||
               this.getArr(i).getCertiType().equals("Y")){
              accSubVoucherSchema.setItemCode("6511"); 
              if(this.getArr(i).getPayRefReason().equals("P6A"))
                accSubVoucherSchema.setDirectionIdx("02/");
              else
                accSubVoucherSchema.setDirectionIdx("01/"); 
            }
            if(this.getArr(i).getCertiType().equals("J")){
            	accSubVoucherSchema.setItemCode("1191");
            	accSubVoucherSchema.setDirectionIdx("08/"); 
            }
            
            if (this.getArr(i).getCertiType().equals("Z")) {
                accSubVoucherSchema.setItemCode("1201");
               
            }
            
            accSubVoucherSchema.setDebitSource(this.getArr(i).getPlanFee());
            accSubVoucherSchema.setExchangeRate(this.getArr(i).getExchangeRate());
            accSubVoucherSchema.setDebitDest(this.getArr(i).getPlanFeeCNY());
          }
          else { 
        	/*
            accSubVoucherSchema.setItemCode("4101"); 
            if(this.getArr(i).getCertiType().equals("S")){
                accSubVoucherSchema.setItemCode("2111"); 
            }
            if(this.getArr(i).getCertiType().equals("C")||
               this.getArr(i).getCertiType().equals("Y")){
                
                
            	
                if(this.getArr(i).getPayRefReason().equals("P62")&&
                   this.getArr(i).getRiskCode().substring(0,2).equals("05")){
                  accSubVoucherSchema.setItemCode("2149");
                  accSubVoucherSchema.setDirectionIdx("12/");
                }else{
                  accSubVoucherSchema.setItemCode("2123"); 
                  if(this.getArr(i).getPayRefReason().equals("P6A"))
                    accSubVoucherSchema.setDirectionIdx("02/");
                  else
                    accSubVoucherSchema.setDirectionIdx("01/");
                }
            }
            if(this.getArr(i).getCertiType().equals("J")){
            	accSubVoucherSchema.setItemCode("2163"); 
            }
            
            if (this.getArr(i).getCertiType().equals("Z")) {
                accSubVoucherSchema.setItemCode("1191");
                accSubVoucherSchema.setDirectionIdx("09/02/");
            }
            */
              accSubVoucherSchema.setItemCode("6031"); 
              if(this.getArr(i).getCertiType().equals("S")){
                  accSubVoucherSchema.setItemCode("2202"); 
              }
              if(this.getArr(i).getCertiType().equals("C")||
                 this.getArr(i).getCertiType().equals("Y")){
                  if(this.getArr(i).getPayRefReason().equals("P62")&&
                     this.getArr(i).getRiskCode().substring(0,2).equals("05")){
                    accSubVoucherSchema.setItemCode("6601");
                    accSubVoucherSchema.setDirectionIdx("34/");
                  }else{
                    accSubVoucherSchema.setItemCode("2205"); 
                    if(this.getArr(i).getPayRefReason().equals("P6A"))
                      accSubVoucherSchema.setDirectionIdx("02/");
                    else
                      accSubVoucherSchema.setDirectionIdx("01/");
                  }
              }     
              if(this.getArr(i).getCertiType().equals("J")){
              	accSubVoucherSchema.setItemCode("2163"); 
              }
              if (this.getArr(i).getCertiType().equals("Z")) {
                  accSubVoucherSchema.setItemCode("6511");
                  accSubVoucherSchema.setDirectionIdx("03/");
              }        
              if(accSubVoucherSchema.getItemCode().equals("6031"))
              {
            	  accSubVoucherSchema.setDirectionIdx("01/");  
              }             
            accSubVoucherSchema.setCreditSource(this.getArr(i).getPlanFee());
            accSubVoucherSchema.setExchangeRate(this.getArr(i).getExchangeRate());
            accSubVoucherSchema.setCreditDest(this.getArr(i).getPlanFeeCNY());
          }
          iBLAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
        }
      }
    }
    /**
     * @desc 生成凭证子表信息 liufengyao20070208
     * @param dbpool
     * @param iBLAccVoucher
     * @param iAccMainVoucherSchema
     * @throws Exception
     */
    public void createSubVoucher(DbPool dbpool, BLAccVoucher iBLAccVoucher,
                                 AccMainVoucherSchema iAccMainVoucherSchema) throws Exception
    {
      
      String strRemark = "转入" + iAccMainVoucherSchema.getVoucherDate();
      AccSubVoucherSchema accSubVoucherSchema = null;
      
      for (int i = 0; i < this.getSize(); i++){
        
        if (this.checkAllowUnpayed(dbpool,this.getArr(i).getComCode(),
                                   this.getArr(i).getRiskCode())&&
            !this.getArr(i).getCertiType().equals("S")&&
            !this.getArr(i).getCertiType().equals("Y")&&
            !this.getArr(i).getCertiType().equals("C")&&
            !this.getArr(i).getCertiType().equals("Z"))
        {
          this.remove(i);
          i--;
          continue;
        }
        
        for (int j = 0; j < 2; j++) {
          accSubVoucherSchema = new AccSubVoucherSchema();
          accSubVoucherSchema.setAccBookType(iAccMainVoucherSchema.getAccBookType());
          accSubVoucherSchema.setAccBookCode(iAccMainVoucherSchema.getAccBookCode());
          accSubVoucherSchema.setCenterCode(iAccMainVoucherSchema.getCenterCode());
          accSubVoucherSchema.setYearMonth(iAccMainVoucherSchema.getYearMonth());
          accSubVoucherSchema.setF26(iAccMainVoucherSchema.getBranchCode());
          accSubVoucherSchema.setVoucherNo(iAccMainVoucherSchema.getVoucherNo());
          accSubVoucherSchema.setSuffixNo("" + (2 * i + j + 1));
          accSubVoucherSchema.setCurrency(this.getArr(i).getCurrency1());
          accSubVoucherSchema.setF01(this.getArr(i).getClassCode());
          if(this.getArr(i).getRiskCode().equals("0507")){
            accSubVoucherSchema.setF05(this.getArr(i).getRiskCode()+this.getArr(i).getCarNatureCode());            
          }else{
            accSubVoucherSchema.setF05(this.getArr(i).getRiskCode());            
          }
          accSubVoucherSchema.setF27(this.getArr(i).getHandler1Code());
          accSubVoucherSchema.setF28(this.getArr(i).getComCode());
          if(this.getArr(i).getCertiType().equals("S")){
            accSubVoucherSchema.setRemark(strRemark+"应付手续费");
          }
          else if(this.getArr(i).getCertiType().equals("Y")||
                   this.getArr(i).getCertiType().equals("C")){
        	  if(this.getArr(i).getPayRefReason().equals("P6A")){
        		  accSubVoucherSchema.setRemark(strRemark+"交强XXXXX垫付款");
        		  }
        	  else if(this.getArr(i).getPayRefReason().equals("P62")){
        		  accSubVoucherSchema.setRemark(strRemark+"查勘费");
        		  }
        	  else{
        		  accSubVoucherSchema.setRemark(strRemark+"应付赔款");
        		  }
          }
          else if(this.getArr(i).getCertiType().equals("J")){
        	  accSubVoucherSchema.setRemark(strRemark+"救助基金");
          }
          else if(this.getArr(i).getCertiType().equals("Z")){
        	  accSubVoucherSchema.setRemark(strRemark+"应收追偿款");
          }
          
          else if(this.getArr(i).getPayRefReason().equals("R72")
        		  || this.getArr(i).getPayRefReason().equals("R73")
        		  || this.getArr(i).getPayRefReason().equals("R74"))
          {
        	  accSubVoucherSchema.setRemark(strRemark+"代收车船税");
          }
          
          else{
        	  accSubVoucherSchema.setRemark(strRemark+"应收XX");
          }

          if (j == 0) { 
        	  
        	  
              accSubVoucherSchema.setItemCode("1122"); 
            if(this.getArr(i).getCertiType().equals("S")){
              accSubVoucherSchema.setItemCode("6421"); 
            }
            if(this.getArr(i).getCertiType().equals("C")||this.getArr(i).getCertiType().equals("Y")){
            	accSubVoucherSchema.setItemCode("6511"); 
            	if(this.getArr(i).getPayRefReason().equals("P6A"))
            		accSubVoucherSchema.setDirectionIdx("02/");
            	else
            		accSubVoucherSchema.setDirectionIdx("01/"); 
            }
            if(this.getArr(i).getCertiType().equals("J")){
            	accSubVoucherSchema.setItemCode("1191");
            	accSubVoucherSchema.setDirectionIdx("08/"); 
            }
            
            if (this.getArr(i).getCertiType().equals("Z")) {
            	if(this.getArr(i).getPayRefReason().equals("R40")){
                    accSubVoucherSchema.setItemCode("1201");
                    accSubVoucherSchema.setDirectionIdx("01/");
            	}else if(this.getArr(i).getPayRefReason().equals("R42")){
                    accSubVoucherSchema.setItemCode("6511");
                    accSubVoucherSchema.setDirectionIdx("03/02/");
            	}else
            		accSubVoucherSchema.setItemCode("1201");
            }
            
            
            if(this.getArr(i).getRiskCode().equals("0507"))
            {
            	if(this.getArr(i).getPayRefReason().equals("R72"))
            	{
            		accSubVoucherSchema.setItemCode("1221");
                    accSubVoucherSchema.setDirectionIdx("12/01/");
            	} else if(this.getArr(i).getPayRefReason().equals("R73"))
            	{
            		accSubVoucherSchema.setItemCode("1221");
                    accSubVoucherSchema.setDirectionIdx("12/02/");
            	}else if(this.getArr(i).getPayRefReason().equals("R74"))
            	{
            		accSubVoucherSchema.setItemCode("1221");
                    accSubVoucherSchema.setDirectionIdx("12/03/");
            	}
            }
            
            accSubVoucherSchema.setDebitSource(this.getArr(i).getPlanFee());
            accSubVoucherSchema.setExchangeRate(this.getArr(i).getExchangeRate());
            accSubVoucherSchema.setDebitDest(this.getArr(i).getPlanFeeCNY());
          }
          else { 
              accSubVoucherSchema.setItemCode("6031"); 
              if(this.getArr(i).getCertiType().equals("S")){
                  accSubVoucherSchema.setItemCode("2202"); 
              }
              if(this.getArr(i).getCertiType().equals("C")||this.getArr(i).getCertiType().equals("Y")){
                  if(this.getArr(i).getPayRefReason().equals("P62")&&
                     this.getArr(i).getRiskCode().substring(0,2).equals("05")){
                    accSubVoucherSchema.setItemCode("6601");
                    accSubVoucherSchema.setDirectionIdx("34/");
                  }else{
                    accSubVoucherSchema.setItemCode("2205"); 
                    if(this.getArr(i).getPayRefReason().equals("P6A"))
                      accSubVoucherSchema.setDirectionIdx("02/");
                    else
                      accSubVoucherSchema.setDirectionIdx("01/");
                  }
              }     
              if(this.getArr(i).getCertiType().equals("J")){
              	accSubVoucherSchema.setItemCode("2163"); 
              }
              
              if (this.getArr(i).getCertiType().equals("Z")) {
              	if(this.getArr(i).getPayRefReason().equals("R40")){
                    accSubVoucherSchema.setItemCode("6511");
                    accSubVoucherSchema.setDirectionIdx("03/01/");
            	}else if(this.getArr(i).getPayRefReason().equals("R42")){
                    accSubVoucherSchema.setItemCode("1201");
                    accSubVoucherSchema.setDirectionIdx("02/");
            	}else
            		accSubVoucherSchema.setItemCode("6511");
              }        
              
              
              if(this.getArr(i).getRiskCode().equals("0507"))
              {
              	if(this.getArr(i).getPayRefReason().equals("R72"))
              	{
              		accSubVoucherSchema.setItemCode("2221");
                    accSubVoucherSchema.setDirectionIdx("10/01/");
              	} else if(this.getArr(i).getPayRefReason().equals("R73"))
              	{
              		accSubVoucherSchema.setItemCode("2221");
                    accSubVoucherSchema.setDirectionIdx("10/02/");
              	}else if(this.getArr(i).getPayRefReason().equals("R74"))
              	{
              		accSubVoucherSchema.setItemCode("2221");
                    accSubVoucherSchema.setDirectionIdx("10/03/");
              	}
              }
              
              if(accSubVoucherSchema.getItemCode().equals("6031")){
            	  accSubVoucherSchema.setDirectionIdx("01/");  
              }             
              accSubVoucherSchema.setCreditSource(this.getArr(i).getPlanFee());
              accSubVoucherSchema.setExchangeRate(this.getArr(i).getExchangeRate());
              accSubVoucherSchema.setCreditDest(this.getArr(i).getPlanFeeCNY());
          }
          
          
          if(Double.parseDouble(accSubVoucherSchema.getDebitDest())!=0 ||
        		  Double.parseDouble(accSubVoucherSchema.getCreditDest())!=0){
              iBLAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
          }
        }
      }
      
      
      for(int i = 0;i<this.blPrpJpayRefRec.getSize();i++){
    	  for (int j = 0; j < 2; j++) {
              accSubVoucherSchema = new AccSubVoucherSchema();
              accSubVoucherSchema.setAccBookType(iAccMainVoucherSchema.getAccBookType());
              accSubVoucherSchema.setAccBookCode(iAccMainVoucherSchema.getAccBookCode());
              accSubVoucherSchema.setCenterCode(iAccMainVoucherSchema.getCenterCode());
              accSubVoucherSchema.setYearMonth(iAccMainVoucherSchema.getYearMonth());
              accSubVoucherSchema.setF26(iAccMainVoucherSchema.getBranchCode());
              accSubVoucherSchema.setVoucherNo(iAccMainVoucherSchema.getVoucherNo());
              
              accSubVoucherSchema.setSuffixNo("" + ((this.getSize()+i)*2 + j + 1));            
              accSubVoucherSchema.setCurrency(this.blPrpJpayRefRec.getArr(i).getCurrency1());
              accSubVoucherSchema.setF01(this.blPrpJpayRefRec.getArr(i).getClassCode());
              if(this.blPrpJpayRefRec.getArr(i).getRiskCode().equals("0507")){
                accSubVoucherSchema.setF05(this.blPrpJpayRefRec.getArr(i).getRiskCode()+this.blPrpJpayRefRec.getArr(i).getCarNatureCode());            
              }else{
                accSubVoucherSchema.setF05(this.blPrpJpayRefRec.getArr(i).getRiskCode());            
              }
              accSubVoucherSchema.setF27(this.blPrpJpayRefRec.getArr(i).getHandler1Code());
              accSubVoucherSchema.setF28(this.blPrpJpayRefRec.getArr(i).getComCode());                                  
              if (j == 0) { 
            	  accSubVoucherSchema.setItemCode("2203"); 
            	  if(this.blPrpJpayRefRec.getArr(i).getPayRefReason().equals("R00")){
            		  accSubVoucherSchema.setDirectionIdx("01/");  
            		  accSubVoucherSchema.setRemark(strRemark+"未签单预收XX"); 
            	  }else{
            		  accSubVoucherSchema.setDirectionIdx("02/");  
            		  accSubVoucherSchema.setRemark(strRemark+"签单预收XX"); 
            	  }                
                  accSubVoucherSchema.setDebitSource(this.blPrpJpayRefRec.getArr(i).getPlanFee());
                  accSubVoucherSchema.setExchangeRate(this.blPrpJpayRefRec.getArr(i).getExchangeRate());
                  accSubVoucherSchema.setDebitDest(this.blPrpJpayRefRec.getArr(i).getPayRefFee());
              }
              else { 
                  accSubVoucherSchema.setItemCode("1122"); 
                  accSubVoucherSchema.setRemark(strRemark+"应收XX");   
                  accSubVoucherSchema.setDebitSource(""+(-1)*Double.parseDouble(this.blPrpJpayRefRec.getArr(i).getPlanFee()));
                  accSubVoucherSchema.setExchangeRate(this.blPrpJpayRefRec.getArr(i).getExchangeRate());
                  accSubVoucherSchema.setDebitDest(""+(-1)*Double.parseDouble(this.blPrpJpayRefRec.getArr(i).getPayRefFee()));
              }
              iBLAccVoucher.getBLAccSubVoucher().setArr(accSubVoucherSchema);
            } 
      }
      
      
    }



    /**
     * 自动转入XXXXX的救助基金凭证 SangMingqian 20060619
     * @param dbpool
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套编码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iDate 日期
     * @param iOperatorCode 操作员代码
     * @throws UserException
     * @throws Exception
     */
    public void transAccountJ(DbPool dbpool, String iAccBookType,
                                   String iAccBookCode, String iCenterCode,
                                   String iBranchCode, String iDate, String iOperatorCode)
                                                                   throws UserException,Exception
    {
        String strWherePart = " 1=1";
        if(iAccBookType.equals(""))
            iAccBookType = SysConst.getProperty("ACCBOOKTYPE");
        strWherePart += Str.convertString("AccBookType",iAccBookType,"=");
        strWherePart += Str.convertString("AccBookCode",iAccBookCode,"=");
        strWherePart += Str.convertString("CenterCode",iCenterCode,"=");
        strWherePart += Str.convertString("BranchCode",iBranchCode,"=");

        BLAccBookBranch blAccBookBranch = new BLAccBookBranch();
        blAccBookBranch.query(dbpool,strWherePart,0);

        String strSQL = "";
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
        for(int i=0; i<blAccBookBranch.getSize(); i++){
            String strBranchCode = blAccBookBranch.getArr(i).getBranchCode();
            
            AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
            accMainVoucherSchema.setAccBookType(blAccBookBranch.getArr(i).getAccBookType());
            accMainVoucherSchema.setAccBookCode(blAccBookBranch.getArr(i).getAccBookCode());
            accMainVoucherSchema.setCenterCode(blAccBookBranch.getArr(i).getCenterCode());
            accMainVoucherSchema.setBranchCode(blAccBookBranch.getArr(i).getBranchCode());
            accMainVoucherSchema.setVoucherType("1");
            accMainVoucherSchema.setGenerateWay("6");
            accMainVoucherSchema.setVoucherDate(iDate);
            accMainVoucherSchema.setOperatorBranch(blAccBookBranch.getArr(i).getBranchCode());
            accMainVoucherSchema.setOperatorCode(iOperatorCode);
            accMainVoucherSchema.setVoucherFlag("1");
            accMainVoucherSchema.setAuxNumber("0");

            
            String strYearMonth = "";
            BLAccMonthTrace blAccMonthTrace = new BLAccMonthTrace();
            strSQL = " CenterCode='"+accMainVoucherSchema.getCenterCode()
                     + "' AND AccBookType='"+accMainVoucherSchema.getAccBookType()
                     + "' AND AccBookCode='"+accMainVoucherSchema.getAccBookCode()
                     + "' AND AccMonthStat='3'";
            blAccMonthTrace.query(dbpool,strSQL,0);
            if(blAccMonthTrace.getSize()==0){
                
                logger.info("核算单位" + accMainVoucherSchema.getCenterCode() + "还没有完成初始化");
                
                continue;
            }else{
                strYearMonth = blAccMonthTrace.getArr(0).getYearMonth();
            }
            accMainVoucherSchema.setAuxNumber("0");
            accMainVoucherSchema.setYearMonth(strYearMonth);

            
            BLAccVoucher blAccVoucher = new BLAccVoucher();
            blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

            
            String[] arrComCode = blPrpDcompany.getLowerComCodeNew(dbpool,strBranchCode);
            
            if(arrComCode.length<1000){
                strWherePart = "'"+strBranchCode+"'";
                for(int j=0; j<arrComCode.length; j++)
                {
                  strWherePart += ",'"+arrComCode[j]+"'";
                }
                strWherePart = " ComCode IN ("+strWherePart+")"
                               + " AND StartDate<='"+iDate+"' AND CertiType='J' "
                               
                               
                               + " AND VoucherNo='0'"
                               
                               
							   + " AND RiskCode='0507' AND PlanFee!=0 "; 
            }else{
            	String strComCodeWhere = " ( ComCode IN ('" + strBranchCode + "'"; 
            	for(int j=0;j<999;j++){
            		strComCodeWhere += ",'" + arrComCode[j] + "'";
            	}
            	strComCodeWhere += ") OR ComCode IN ('" + arrComCode[999] + "'";
            	for(int j=1000;j<arrComCode.length;j++){
            		strComCodeWhere += ",'" + arrComCode[j] + "'";
            	}
            	strComCodeWhere += " ) )";
            	strWherePart = strComCodeWhere 	
                               + " AND StartDate<='"+iDate+"' AND CertiType='J' "
                               
                               
                               + " AND  VoucherNo='0'"
                               
                               
                               + " AND RiskCode='0507' AND PlanFee!=0 "; 
            }
            strSQL = " SELECT * FROM PrpJplanfee WHERE " + strWherePart;
            this.initArr();
            this.schemas = dbPrpJplanFee.findByConditions(dbpool,strSQL);
            
            double dblExchRate = 1;
            double dblPlanFeeCNY = 0;
            for(int j=0; j<this.getSize(); j++){
            	dblExchRate = PubTools.getExchangeRate(dbpool,
                                                 this.getArr(j).getCurrency1(),
                                                 "CNY",
                                                 accMainVoucherSchema.getVoucherDate());
                this.getArr(j).setExchangeRate(""+dblExchRate);
                dblPlanFeeCNY = Double.parseDouble(this.getArr(j).getPlanFee())*dblExchRate;
                dblPlanFeeCNY = Str.round(Str.round(dblPlanFeeCNY,8),2);
                this.getArr(j).setPlanFeeCNY(""+dblPlanFeeCNY);
            }

            
            this.createSubVoucher(dbpool,blAccVoucher,accMainVoucherSchema);

            
            BLAccSubVoucher blAccSubVoucher = blAccVoucher.getBLAccSubVoucher();
            for (int j = 0; j < blAccSubVoucher.getSize(); j++){
                blAccSubVoucher.createRawArticle(dbpool, blAccSubVoucher.getArr(j), "");
            }
            
            blAccSubVoucher.voucherComp("111");
            
            blAccSubVoucher.voucherOrder();
            blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());

            
            String strVoucherNo = "";
            if(blAccVoucher.getBLAccSubVoucher().getSize()>0){
                blAccVoucher.save(dbpool);
                
                this.blAccMainVoucher.setArr(blAccVoucher.getBLAccMainVoucher().getArr(0));
                strVoucherNo = blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
                
                logger.info("生成救助基金凭证 "+strVoucherNo);
                
            }else{
                
                logger.info("没有要转入救助基金的数据！");
                
            }

            
            for(int j=0; j<this.getSize(); j++){
                dbPrpJplanFee.setSchema(this.getArr(j));
                dbPrpJplanFee.setAccBookCode(accMainVoucherSchema.getAccBookCode());
                dbPrpJplanFee.setAccBookType(accMainVoucherSchema.getAccBookType());
                dbPrpJplanFee.setCenterCode(accMainVoucherSchema.getCenterCode());
                dbPrpJplanFee.setBranchCode(accMainVoucherSchema.getBranchCode());
                dbPrpJplanFee.setYearMonth(accMainVoucherSchema.getYearMonth());
                dbPrpJplanFee.setVoucherNo(strVoucherNo);
                dbPrpJplanFee.update(dbpool);
            }
        }
    }


    /**
     * @author lijibin 20050625 自动转入XXXXX的应付赔款凭证
     * @param dbpool
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iDate 截至日期
     * @param iOperatorCode 操作员代码
     * @throws Exception
     */
    public void transAccountC(DbPool dbpool, String iAccBookType,
                             String iAccBookCode, String iCenterCode,
                             String iBranchCode, String iDate, String iOperatorCode)
       throws UserException,Exception
    {
      String strWherePart = " 1=1";
      if(iAccBookType.equals(""))
        iAccBookType = SysConst.getProperty("ACCBOOKTYPE");
      strWherePart += Str.convertString("AccBookType",iAccBookType,"=");
      strWherePart += Str.convertString("AccBookCode",iAccBookCode,"=");
      strWherePart += Str.convertString("CenterCode",iCenterCode,"=");
      strWherePart += Str.convertString("BranchCode",iBranchCode,"=");

      BLAccBookBranch blAccBookBranch = new BLAccBookBranch();
      blAccBookBranch.query(dbpool,strWherePart,0);

      String strSQL = "";
      BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      
      for(int i=0; i<blAccBookBranch.getSize(); i++)
      {
        String strBranchCode = blAccBookBranch.getArr(i).getBranchCode();
        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(blAccBookBranch.getArr(i).getAccBookType());
        accMainVoucherSchema.setAccBookCode(blAccBookBranch.getArr(i).getAccBookCode());
        accMainVoucherSchema.setCenterCode(blAccBookBranch.getArr(i).getCenterCode());
        accMainVoucherSchema.setBranchCode(blAccBookBranch.getArr(i).getBranchCode());
        accMainVoucherSchema.setVoucherType("1");
        accMainVoucherSchema.setGenerateWay("6");
        accMainVoucherSchema.setVoucherDate(iDate);
        accMainVoucherSchema.setOperatorBranch(blAccBookBranch.getArr(i).getBranchCode());
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setVoucherFlag("1");
        accMainVoucherSchema.setAuxNumber("0");

        

















        String strYearMonth = iDate.substring(0,4)+iDate.substring(5,7);
        accMainVoucherSchema.setAuxNumber("0");
        accMainVoucherSchema.setYearMonth(strYearMonth);

        
        BLAccVoucher blAccVoucher = new BLAccVoucher();
        blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);

    	this.logPrintln("kai shi cha xun ying fu pei kuan de shu ju！");
        
        String[] arrComCode = blPrpDcompany.getLowerComCodeNew(dbpool,strBranchCode);
        
        if(arrComCode.length<1000){
            strWherePart = "'"+strBranchCode+"'";
            for(int j=0; j<arrComCode.length; j++)
            {
              strWherePart += ",'"+arrComCode[j]+"'";
            }
            strWherePart = " ComCode IN ("+strWherePart+")"
                           + " AND UnderWriteDate<='"+iDate+"' AND CertiType IN ('Y','C')"
                           
                           
                           + " AND RealPayRefFee=0 AND VoucherNo ='0' "; 
            			   
        }else{
        	String strComCodeWhere = " ( ComCode IN ('" + strBranchCode + "'"; 
        	for(int j=0;j<999;j++){
        		strComCodeWhere += ",'" + arrComCode[j] + "'";
        	}
        	strComCodeWhere += ") OR ComCode IN ('" + arrComCode[999] + "'";
        	for(int j=1000;j<arrComCode.length;j++){
        		strComCodeWhere += ",'" + arrComCode[j] + "'";
        	}
        	strComCodeWhere += " ) )";
        	strWherePart = strComCodeWhere 	
                            + " AND UnderWriteDate<='"+iDate+"' AND CertiType IN ('Y','C')"
                            
                            
                            + " AND RealPayRefFee=0 AND VoucherNo ='0' "; 
                            
        }
        strSQL = "SELECT * FROM PrpJplanFee"
            + " WHERE " + strWherePart;
            

        this.initArr();
        this.schemas = dbPrpJplanFee.findByConditions(dbpool,strSQL);
        /*ResultSet rs = dbpool.query(strSQL);
        while(rs.next())
        {
          schema = new PrpJplanFeeSchema();
          schema.setClassCode(rs.getString("ClassCode"));
          schema.setRiskCode(rs.getString("RiskCode"));
          schema.setComCode(rs.getString("ComCode"));
          schema.setCurrency1(rs.getString("Currency1"));
          schema.setPlanFee(""+rs.getDouble("PlanFee"));
          schema.setFlag("0");
          this.setArr(schema);
        }
        rs.close();*/

        
        double dblExchRate = 1;
        double dblPlanFeeCNY = 0;
        for(int j=0; j<this.getSize(); j++)
        {
          dblExchRate = PubTools.getExchangeRate(dbpool,
                                                 this.getArr(j).getCurrency1(),
                                                 "CNY",
                                                 accMainVoucherSchema.getVoucherDate());
          this.getArr(j).setExchangeRate(""+dblExchRate);
          dblPlanFeeCNY = Double.parseDouble(this.getArr(j).getPlanFee())*dblExchRate;
          dblPlanFeeCNY = Str.round(Str.round(dblPlanFeeCNY,8),2);
          this.getArr(j).setPlanFeeCNY(""+dblPlanFeeCNY);
        }
        
    	this.logPrintln("ying fu pei kuan shu ju tiao shu=="+this.getSize());

        
        this.createSubVoucher(dbpool,blAccVoucher,accMainVoucherSchema);

        
        BLAccSubVoucher blAccSubVoucher = blAccVoucher.getBLAccSubVoucher();
        for (int j = 0; j < blAccSubVoucher.getSize(); j++)
        {
          blAccSubVoucher.createRawArticle(dbpool, blAccSubVoucher.getArr(j), "");
        }
        
        blAccSubVoucher.voucherComp("111");
        
        blAccSubVoucher.voucherOrder();
        blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());

        
        String strVoucherNo = "";
        if(blAccVoucher.getBLAccSubVoucher().getSize()>0)
        {
          this.logPrintln("kai shi bao cun ping zheng ");
          blAccVoucher.save(dbpool);
          
          this.blAccMainVoucher.setArr(blAccVoucher.getBLAccMainVoucher().getArr(0));
          strVoucherNo = blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
          this.logPrintln("sheng cheng ying fu pei kuan ping zheng "+accMainVoucherSchema.getCenterCode()+" "+strVoucherNo);
        }
        else
        {
          this.logPrintln("mei you yao zhuan ru ying fu pei kuan de shu ju！");
        }

        
        for(int j=0; j<this.getSize(); j++)
        {
          dbPrpJplanFee.setSchema(this.getArr(j));
          dbPrpJplanFee.setAccBookCode(accMainVoucherSchema.getAccBookCode());
          dbPrpJplanFee.setAccBookType(accMainVoucherSchema.getAccBookType());
          dbPrpJplanFee.setCenterCode(accMainVoucherSchema.getCenterCode());
          dbPrpJplanFee.setBranchCode(accMainVoucherSchema.getBranchCode());
          dbPrpJplanFee.setYearMonth(accMainVoucherSchema.getYearMonth());
          dbPrpJplanFee.setVoucherNo(strVoucherNo);
          dbPrpJplanFee.update(dbpool);
        }
        this.logPrintln("hui xie ying fu pei kuan biao zhong de ping zheng xin xi wan cheng");
      }
    }


    /**
     * 自动转入XXXXX的追偿款凭证 lijibin 20061025 
     * @param dbpool
     * @param iAccBookType
     *            帐套类型
     * @param iAccBookCode
     *            帐套编码
     * @param iCenterCode
     *            核算单位
     * @param iBranchCode
     *            基层单位
     * @param iDate
     *            日期
     * @param iOperatorCode
     *            操作员代码
     * @throws UserException
     * @throws Exception
     */
    public void transAccountZ(DbPool dbpool, String iAccBookType,
            String iAccBookCode, String iCenterCode, String iBranchCode,
            String iDate, String iOperatorCode) throws UserException, Exception {
        String strWherePart = " 1=1";
        if (iAccBookType.equals(""))
            iAccBookType = SysConst.getProperty("ACCBOOKTYPE");
        strWherePart += Str.convertString("AccBookType", iAccBookType, "=");
        strWherePart += Str.convertString("AccBookCode", iAccBookCode, "=");
        strWherePart += Str.convertString("CenterCode", iCenterCode, "=");
        strWherePart += Str.convertString("BranchCode", iBranchCode, "=");

        BLAccBookBranch blAccBookBranch = new BLAccBookBranch();
        PrpJplanFeeSchema iPlanFeeschema = null;
        blAccBookBranch.query(dbpool, strWherePart, 0);

        String strSQL = "";
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();  
        for (int i = 0; i < blAccBookBranch.getSize(); i++) {
            String strBranchCode = blAccBookBranch.getArr(i).getBranchCode();
            
            AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
            accMainVoucherSchema.setAccBookType(blAccBookBranch.getArr(i).getAccBookType());
            accMainVoucherSchema.setAccBookCode(blAccBookBranch.getArr(i).getAccBookCode());
            accMainVoucherSchema.setCenterCode(blAccBookBranch.getArr(i).getCenterCode());
            accMainVoucherSchema.setBranchCode(blAccBookBranch.getArr(i).getBranchCode());
            accMainVoucherSchema.setVoucherType("1");
            accMainVoucherSchema.setGenerateWay("6");
            accMainVoucherSchema.setVoucherDate(iDate);
            accMainVoucherSchema.setOperatorBranch(blAccBookBranch.getArr(i).getBranchCode());
            accMainVoucherSchema.setOperatorCode(iOperatorCode);
            accMainVoucherSchema.setVoucherFlag("1");
            accMainVoucherSchema.setAuxNumber("0");
















            String strYearMonth = iDate.substring(0,4)+iDate.substring(5,7);
            accMainVoucherSchema.setYearMonth(strYearMonth);

            
            BLAccVoucher blAccVoucher = new BLAccVoucher();
            blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);
            this.logPrintln("kai shi cha xun zhui chang kuan shou ru shu ju！");
            
            String[] arrComCode = blPrpDcompany.getLowerComCodeNew(dbpool,strBranchCode);
            
            if (arrComCode.length < 1000) {
                strWherePart = "'" + strBranchCode + "'";
                for (int j = 0; j < arrComCode.length; j++) {
                    strWherePart += ",'" + arrComCode[j] + "'";
                }
                strWherePart = " ComCode IN (" + strWherePart + ")"
                        + " AND UnderWriteDate<='" + iDate + "' AND CertiType='Z' "
                        
                        
                        + " AND  VoucherNo='0'"
                        
                        
                        + "  AND PlanFee!=0 ";
            } else {
                String strComCodeWhere = " ( ComCode IN ('" + strBranchCode
                        + "'";
                for (int j = 0; j < 999; j++) {
                    strComCodeWhere += ",'" + arrComCode[j] + "'";
                }
                strComCodeWhere += ") OR ComCode IN ('" + arrComCode[999] + "'";
                for (int j = 1000; j < arrComCode.length; j++) {
                    strComCodeWhere += ",'" + arrComCode[j] + "'";
                }
                strComCodeWhere += " ) )";
                strWherePart = strComCodeWhere + " AND UnderWriteDate<='" + iDate
                        + "' AND CertiType='Z' "
                        
                        
                        + " AND  VoucherNo='0'"
                        
                        
                        + "  AND PlanFee!=0 ";
            }
            strSQL = " SELECT CertiType,ClassCode,RiskCode,CarNatureCode,PayRefReason,ComCode,Currency1,SUM(PlanFee) AS PlanFee from prpjplanfee where " + strWherePart
            		+" Group by CertiType,ClassCode,RiskCode,CarNatureCode,PayRefReason,ComCode,Currency1 ";
            ResultSet rs = dbpool.query(strSQL);
            this.initArr();
            while(rs.next()){
          	  iPlanFeeschema = new PrpJplanFeeSchema();
              iPlanFeeschema.setCertiType(rs.getString("CertiType"));
          	  iPlanFeeschema.setClassCode(rs.getString("ClassCode"));
          	  iPlanFeeschema.setRiskCode(rs.getString("RiskCode"));
          	  iPlanFeeschema.setCarNatureCode(rs.getString("CarNatureCode"));
          	  iPlanFeeschema.setComCode(rs.getString("ComCode"));
          	  iPlanFeeschema.setPayRefReason(rs.getString("PayRefReason"));
          	  iPlanFeeschema.setCurrency1(rs.getString("Currency1"));
          	  iPlanFeeschema.setPlanFee(""+rs.getDouble("PlanFee"));
          	  
              this.setArr(iPlanFeeschema);
            }
            rs.close();
            
            double dblExchRate = 1;
            double dblPlanFeeCNY = 0;
            for (int j = 0; j < this.getSize(); j++) {
                dblExchRate = PubTools.getExchangeRate(dbpool, this.getArr(j)
                        .getCurrency1(), "CNY", accMainVoucherSchema
                        .getVoucherDate());
                this.getArr(j).setExchangeRate("" + dblExchRate);
                dblPlanFeeCNY = Double.parseDouble(this.getArr(j).getPlanFee())
                        * dblExchRate;
                dblPlanFeeCNY = Str.round(Str.round(dblPlanFeeCNY, 8), 2);
                this.getArr(j).setPlanFeeCNY("" + dblPlanFeeCNY);
            }
            this.logPrintln("zhui chang kuan shou ru shu ju ge shu wei："+this.getSize());
            
            this.createSubVoucher(dbpool, blAccVoucher, accMainVoucherSchema);

            
            BLAccSubVoucher blAccSubVoucher = blAccVoucher.getBLAccSubVoucher();
            for (int j = 0; j < blAccSubVoucher.getSize(); j++) {
                blAccSubVoucher.createRawArticle(dbpool, blAccSubVoucher.getArr(j), "");
            }
            
            blAccSubVoucher.voucherComp("111");
            
            blAccSubVoucher.voucherOrder();
            blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());
            this.logPrintln("kai shi bao cun ping zheng");
            
            String strVoucherNo = "";
            if (blAccVoucher.getBLAccSubVoucher().getSize() > 0) {
                blAccVoucher.save(dbpool);
                
                this.blAccMainVoucher.setArr(blAccVoucher.getBLAccMainVoucher()
                        .getArr(0));
                strVoucherNo = blAccVoucher.getBLAccMainVoucher().getArr(0)
                        .getVoucherNo();
                this.logPrintln("sheng cheng zhui chang kuan shou ru ping zheng  " + strVoucherNo);
            } else {
            	this.logPrintln("mei you yao zhuan ru zhui chang kuan shou ru de shu ju！");
            }
            for(int j=0; j<this.getSize(); j++){
                strSQL = "UPDATE PrpJplanFee SET AccBookType='" + accMainVoucherSchema.getAccBookType()
                    + "',AccBookCode='" + accMainVoucherSchema.getAccBookCode()
                    + "',CenterCode='" + accMainVoucherSchema.getCenterCode()
                    + "',BranchCode='" + accMainVoucherSchema.getBranchCode()
                    + "',YearMonth='" + accMainVoucherSchema.getYearMonth()
                    + "',VoucherNo='" + strVoucherNo
                    + "',ExchangeRate='" + this.getArr(j).getExchangeRate()
                    + "',PlanFeeCNY=PlanFee*" + this.getArr(j).getExchangeRate()
                    + " WHERE ClassCode='" + this.getArr(j).getClassCode()
                    + "' AND RiskCode='" + this.getArr(j).getRiskCode()
                    + "' AND ComCode='" + this.getArr(j).getComCode()
                    + "' AND Currency1='" + this.getArr(j).getCurrency1()
                    + "' AND " + strWherePart;        
              dbpool.update(strSQL);
            }
            this.logPrintln("hui xie ying shou ying fu biao zhong de ping zheng xin xi wan cheng");
        }
    }

    /**
     * @author lijibin 20050622
     * @desc 判断XXXXX种是否允许转入应收XX
     * @param iComCode
     * @param iRiskCode
     * @return boolean
     * @throws Exception
     */
    public boolean checkAllowUnpayed(String iComCode,String iRiskCode) throws Exception
    {
      boolean blnReturn = false;
      DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
        blnReturn = this.checkAllowUnpayed(dbpool,iComCode,iRiskCode);
      }
      catch(Exception e)
      {
        throw e;
      }
      finally {
        dbpool.close();
      }
      return blnReturn;
    }


    /**
     * @author lijibin 20050622
     * @desc 判断XXXXX种是否允许转入应收XX
     * @param iComCode
     * @param iRiskCode
     * @return boolean
     * @throws Exception
     */
    public boolean checkAllowUnpayed(DbPool dbpool,String iComCode,String iRiskCode) throws Exception
    {
      boolean blnReturn = false;
      /*
      UIPrpDriskConfigAction uiPrpDriskConfigAction  = new UIPrpDriskConfigAction();
      PrpDriskConfigDto prpDriskConfigDto = new PrpDriskConfigDto();
      prpDriskConfigDto = uiPrpDriskConfigAction.queryRiskConfig(iComCode,
          iRiskCode, "ALLOW_UNPAYED_POLICY");
      if(prpDriskConfigDto!=null &&
         !prpDriskConfigDto.getConfigValue().equals("") &&
         prpDriskConfigDto.getConfigValue().equals("2")  
        )
        blnReturn =true;
      */
      
      
      
      
      
      

      
      
        

      return blnReturn;
    }

    /**
     * @author lijibin 20050625 返回凭证主表信息，为了获取生成的凭证信息
     * @return BLAccMainVoucher
     * @throws Exception
     */
    public BLAccMainVoucher getBLAccMainVoucher() throws Exception
    {
      return this.blAccMainVoucher;
    }

    /**
     * @author lijibin 20050630 收付款确认时如果没有转入应收应付则先生成应收应付凭证
     * @param dbpool
     * @param iSchema
     * @throws UserException
     * @throws Exception
     */
    public void createBeforeVoucher(DbPool dbpool,AccMainVoucherSchema iSchema) throws UserException,Exception
    {
      BLAccVoucher blAccVoucher = new BLAccVoucher();
      AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
      
      accMainVoucherSchema.setSchema(iSchema);
      accMainVoucherSchema.setGenerateWay("6"); 
      blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);
      
      this.createSubVoucher(dbpool,blAccVoucher,accMainVoucherSchema);
      
      BLAccSubVoucher blAccSubVoucher = blAccVoucher.getBLAccSubVoucher();
      for (int j = 0; j < blAccSubVoucher.getSize(); j++)
      {
        blAccSubVoucher.createRawArticle(dbpool, blAccSubVoucher.getArr(j), "");
      }
      
      blAccSubVoucher.voucherComp("111");
      
      blAccSubVoucher.voucherOrder();
      
      blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());

      
      String strVoucherNo = "";
      if(blAccVoucher.getBLAccSubVoucher().getSize()>0)
      {
        blAccVoucher.save(dbpool);
        
        this.blAccMainVoucher.setArr(blAccVoucher.getBLAccMainVoucher().getArr(0));
        strVoucherNo = blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
        
        logger.info("生成应收应付凭证 "+strVoucherNo);
        
      }

      
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      for(int j=0; j<this.getSize(); j++)
      {
        dbPrpJplanFee.setSchema(this.getArr(j));
        dbPrpJplanFee.setAccBookCode(accMainVoucherSchema.getAccBookCode());
        dbPrpJplanFee.setAccBookType(accMainVoucherSchema.getAccBookType());
        dbPrpJplanFee.setCenterCode(accMainVoucherSchema.getCenterCode());
        dbPrpJplanFee.setBranchCode(accMainVoucherSchema.getBranchCode());
        dbPrpJplanFee.setYearMonth(accMainVoucherSchema.getYearMonth());
        dbPrpJplanFee.setVoucherNo(strVoucherNo);
        
        dbPrpJplanFee.setPlanFeeCNY(dbPrpJplanFee.getPlanFee());
        dbPrpJplanFee.update(dbpool);
      }
    }

    /**
     * 根据XX号获取实收实付金额
     * @param iPolicyNo XX号
     * @return  实收实付金额
     * @throws SQLException
     * @throws Exception
     */
    public double getRealPayRefFee(String iPolicyNo) throws SQLException,Exception{
      String strSQL = " SELECT SUM(RealPayRefFee) AS RealPayRefFee FROM PrpJplanFee "
                      + " WHERE CertiType='P' AND PolicyNo='" + iPolicyNo + "' ";

      double dblRealPayRefFee = 0;
      this.initArr();
      DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
        
        ResultSet resultSet = dbpool.query(strSQL);
        while(resultSet.next())
        {
          dblRealPayRefFee = resultSet.getDouble("RealPayRefFee");
        }
        resultSet.close();
        dbpool.close();
      }
      catch(SQLException sqlException)
      {
        dbpool.close();
        throw sqlException;
      }
      catch(Exception e)
      {
        dbpool.close();
        throw e;
      }
      finally {
        dbpool.close();
      }
      return dblRealPayRefFee;
    }

    /**
     * 获取手续费相应的实收XX SangMingqian 20051230
     * @param iCertiNo
     * @return
     * @throws SQLException
     * @throws Exception
     */
    
    public double getRealPayRefFeePE(String iCertiNo,String iPayNo,String iSerialNo) throws SQLException,Exception{
        String strSQL = " SELECT SUM(RealPayRefFee) AS RealPayRefFee FROM PrpJplanFee "
                        + " WHERE CertiType IN ('P','E') AND CertiNo='" + iCertiNo + "' AND PayNo='"+iPayNo+"' AND SerialNo ='"+iSerialNo+"'"
                        + " AND PayRefReason not in('R72','R73','R74')";

        double dblRealPayRefFee = 0;
        this.initArr();
        DbPool dbpool = new DbPool();
        try {
          
          dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
          
          ResultSet resultSet = dbpool.query(strSQL);
          while(resultSet.next())
          {
            dblRealPayRefFee = resultSet.getDouble("RealPayRefFee");
          }
          resultSet.close();
          dbpool.close();
        }
        catch(SQLException sqlException)
        {
          dbpool.close();
          throw sqlException;
        }
        catch(Exception e)
        {
          dbpool.close();
          throw e;
        }
        finally {
          dbpool.close();
        }
        return dblRealPayRefFee;
      }


    /**
     * 根据ChargeCode获取PayRefReason SangMingqian 20051203
     * @param iChargeCode
     * @return
     * @throws Exception
     */
    public String getPayRefReason(String iChargeCode) throws Exception{
    	String strPayRefReason = "";
    	try{
    		if(iChargeCode.equals("01")||
    		   iChargeCode.equals("07")){
    			strPayRefReason = "P61";
    		}else if(iChargeCode.equals("04")||
    				  iChargeCode.equals("06")||
					  iChargeCode.equals("12")){
    			strPayRefReason = "P62";
    		}else if(iChargeCode.equals("03")){
    			strPayRefReason = "P63";
    		}else if(iChargeCode.equals("05")){
    			strPayRefReason = "P64";
    		}else if(iChargeCode.equals("09")){
    			strPayRefReason = "P65";
    		}else if(iChargeCode.equals("11")){
    			strPayRefReason = "P66";
    		}else if(iChargeCode.equals("13")){
    			strPayRefReason = "P67";
    		}else if(iChargeCode.equals("15")){
    			strPayRefReason = "P68";
    		}else if(iChargeCode.equals("02")||
    				  iChargeCode.equals("08")||
					  iChargeCode.equals("14")||
					  iChargeCode.equals("27")||
					  iChargeCode.equals("99")){
    			strPayRefReason = "P69";
    		}else{
    			strPayRefReason = "P69";
    		}
    	}
    	catch(Exception e){
    		throw e;
    	}
    	return strPayRefReason;
    }

    /**
     * 根据ChargeCode获取PayRefReason SangMingqian 20051203
     * @param iChargeCode
     * @param iFlag
     * @return String
     * @throws Exception
     */
    public String getPayRefReason(String iChargeCode,String iFlag) throws Exception{
      String strPayRefReason = "";
      String strPrefix = "";
      try{
        if(iFlag.equals("1"))
          strPrefix = "P";
        else if(iFlag.equals("2"))
          strPrefix = "R";
        else if(iFlag.equals("3"))
          strPrefix = "S";
        else
          throw new Exception("没有该标志!");

        if(iChargeCode.equals("01")||
           iChargeCode.equals("07")){
          strPayRefReason = strPrefix + "61";
        }else if(iChargeCode.equals("04")||
              iChargeCode.equals("06")||
            iChargeCode.equals("12")){
          strPayRefReason = strPrefix + "62";
        }else if(iChargeCode.equals("03")){
          strPayRefReason = strPrefix + "63";
        }else if(iChargeCode.equals("05")){
          strPayRefReason = strPrefix + "64";
        }else if(iChargeCode.equals("09")){
          strPayRefReason = strPrefix + "65";
        }else if(iChargeCode.equals("11")){
          strPayRefReason = strPrefix + "66";
        }else if(iChargeCode.equals("13")){
          strPayRefReason = strPrefix + "67";
        }else if(iChargeCode.equals("15")){
          strPayRefReason = strPrefix + "68";
        }else if(iChargeCode.equals("02")||
              iChargeCode.equals("08")||
            iChargeCode.equals("14")||
            iChargeCode.equals("27")||
            iChargeCode.equals("99")){
          strPayRefReason = strPrefix + "69";
        }else{
          strPayRefReason = strPrefix + "69";
        }
      }
      catch(Exception e){
        throw e;
      }
      return strPayRefReason;
    }

    /**
     * @author lijibin 20060429 如果预约协议已经收费，则同时生成实收记录
     * @param dbpool 数据库连接池
     * @param iWherePart 转数的条件
     * @param iSchema
     * @throws Exception
     */
    public void transPayRefRec(DbPool dbpool) throws Exception
    {
      double dblRealFeeMain = 0;
      double dblRealFeeSub = 0;
      double dblPlanFee = 0;
      double dblExchRate = 1; 
      String strCurrency2 = "";
      String strSQL = "";
      
      strSQL = " PolicyNo='" + this.getArr(0).getContractNo()
          + "' AND CertiType IN ('P','E') AND PayRefDate IS NOT NULL";
      BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
      blPrpJpayRefRec.query(dbpool,strSQL);
      if(blPrpJpayRefRec.getSize()==0)
        return;

      for(int i=0; i<blPrpJpayRefRec.getSize(); i++){
        dblRealFeeMain += Double.parseDouble(blPrpJpayRefRec.getArr(i).getPayRefFee());
        dblExchRate = Double.parseDouble(blPrpJpayRefRec.getArr(i).getExchangeRate());
        strCurrency2 = blPrpJpayRefRec.getArr(i).getCurrency2();
      }

      for(int i=0; i<this.getSize(); i++){
        if(this.getArr(i).getCertiType().equals("P")||this.getArr(i).getCertiType().equals("E"))
          dblPlanFee += Str.round(Double.parseDouble(this.getArr(i).getPlanFee())*dblExchRate,2);
      }

      if(dblRealFeeMain>0){
        strSQL = "SELECT SUM(RealPayRefFee) FROM PrpJplanFee WHERE ContractNo='" +
            this.getArr(0).getContractNo() + "' AND CertiType IN ('P','E') AND PayRefReason='R00'";
        ResultSet rs = dbpool.query(strSQL);
        if(rs.next())
          dblRealFeeSub = rs.getDouble(1);
        rs.close();
      }

      
      
      if(Str.round(dblRealFeeMain-dblRealFeeSub-dblPlanFee,2)<0)
        return;

      blPrpJpayRefRec.initArr();
      for(int i=0; i<this.getSize(); i++){
        if(this.getArr(i).getCertiType().equals("S"))
          continue;
        if(strCurrency2.equals("CNY")){
          dblPlanFee = Str.round(Double.parseDouble(this.getArr(i).getPlanFee())*dblExchRate,2);
        }else{
          dblPlanFee = Str.round(Double.parseDouble(this.getArr(i).getPlanFee()) *
                                 PubTools.getExchangeRate(dbpool,this.getArr(i).getCurrency1(), "CNY",
                                 this.getArr(i).getUnderWriteDate()),2);
        }

        this.getArr(i).setPlanFeeCNY(""+dblPlanFee);
        this.getArr(i).setPayRefFee(this.getArr(i).getPlanFee());
        this.getArr(i).setRealPayRefFee(this.getArr(i).getPlanFee());
        this.getArr(i).setFlag("2");
        
        blPrpJpayRefRec.setArr(blPrpJpayRefRec.evaluate(this.getArr(i)));
        int index = blPrpJpayRefRec.getSize() - 1;
        blPrpJpayRefRec.getArr(index).setCurrency2(strCurrency2);
        blPrpJpayRefRec.getArr(index).setExchangeRate(""+dblExchRate);
        blPrpJpayRefRec.getArr(index).setPayRefFee(""+Str.round(Double.parseDouble(this.
            getArr(i).getPlanFee()) * dblExchRate, 2));
        
        if (this.getArr(i).getCertiType().equals("P") ||
                this.getArr(i).getCertiType().equals("E")){
               
               DBPrpCplan dbPrpCplan = new DBPrpCplan();
               int intReturn = 0;
               if(this.getArr(i).getCertiType().equals("P")){
                  intReturn = dbPrpCplan.getInfo(dbpool, this.getArr(i).getCertiNo(),
                                                    this.getArr(i).getSerialNo());
                   if(intReturn==0){
                       
                       double DelinquentFeeP = Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee())) -
                       Double.parseDouble(ChgData.chgStrZero(this.getArr(i).getPlanFee()));
                       if(DelinquentFeeP==Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee())))
                       {
                           throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
                        		   this.getArr(i).getCertiNo() + "回写PrpCplan表失败！");                       	  
                       }
                       
                       dbPrpCplan.setDelinquentFee("" +
                                    (Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee())) -
                                     Double.parseDouble(ChgData.chgStrZero(this.getArr(i).getPlanFee()))));
                       dbPrpCplan.update(dbpool);
                   }

               }else{
                   BLPrpCplan blPrpCplan = new BLPrpCplan();
                   String strWhere = " EndorseNo='" + this.getArr(i).getCertiNo()+"' ";
                   blPrpCplan.query(dbpool,strWhere,0);
                   if(blPrpCplan.getSize()>0){
                       intReturn = dbPrpCplan.getInfo(dbpool,blPrpCplan.getArr(0).getPolicyNo(),
                                                      blPrpCplan.getArr(0).getSerialNo());
                   }
                   if(intReturn==0){
                       
                       double DelinquentFeeE = Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee())) -
                                   Double.parseDouble(ChgData.chgStrZero(this.getArr(i).getPlanFee()));         
                       if(DelinquentFeeE==Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee())))
                       {
                           throw new UserException( -96, -1167,"BLPrpJpayRefMain.VoucherVerify()",
                        		   this.getArr(i).getCertiNo() + "回写PrpCplan表失败！");                       	  
                       }
                       
                       dbPrpCplan.setDelinquentFee("" +
                                  (Double.parseDouble(ChgData.chgStrZero(dbPrpCplan.getDelinquentFee())) -
                                   Double.parseDouble(ChgData.chgStrZero(this.getArr(i).getPlanFee()))));
                       dbPrpCplan.update(dbpool);
                   }
               }
               
               DBPrpCPplan dbPrpCPplan = new DBPrpCPplan();
               if(this.getArr(i).getCertiType().equals("P")){
                   intReturn = dbPrpCPplan.getInfo(dbpool, this.getArr(i).getCertiNo(),
                                                          this.getArr(i).getSerialNo());
                   if(intReturn==0){
                       dbPrpCPplan.setDelinquentFee("" +
                                       (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee())) -
                                        Double.parseDouble(ChgData.chgStrZero(this.getArr(i).getPlanFee()))));
                       dbPrpCPplan.update(dbpool);
                   }
               }else{
                   BLPrpCPplan blPrpCPplan = new BLPrpCPplan();
                   String strWhere = " EndorseNo='" + this.getArr(i).getCertiNo()+"' ";
                   blPrpCPplan.query(dbpool,strWhere,0);
                   if(blPrpCPplan.getSize()>0){
                       intReturn = dbPrpCPplan.getInfo(dbpool,blPrpCPplan.getArr(0).getPolicyNo(),
                                                        blPrpCPplan.getArr(0).getSerialNo());
                   }
                   if(intReturn==0){
                       dbPrpCPplan.setDelinquentFee("" +
                                    (Double.parseDouble(ChgData.chgStrZero(dbPrpCPplan.getDelinquentFee())) -
                                     Double.parseDouble(ChgData.chgStrZero(this.getArr(i).getPlanFee()))));
                       dbPrpCPplan.update(dbpool);
                   }
               }
           }        
      }
      blPrpJpayRefRec.save(dbpool);

    }    
    
    /**
     * 为车辆种类等字段赋值 SangMingqian 20060726
     * @param iSchema planfeeSchema
     * @throws Exception
     */
    public void getCarNature(DbPool dbpool,PrpJplanFeeSchema iSchema) throws Exception,UserException{
        DBPrpCitemCar dbPrpCitemCar = new DBPrpCitemCar();
        if(dbPrpCitemCar.getInfo(dbpool,iSchema.getPolicyNo(),"1")==100){
            throw new UserException( -96, -1167,"BLPrpJplanFee.getCarNature()",
                                iSchema.getPolicyNo() + "不存在标的信息！");
        }else{

        	String carNatureCode="";
        	carNatureCode=this.queryCarNatureCode(dbpool, dbPrpCitemCar.getCarKindCode(), dbPrpCitemCar.getUseNatureCode());
        	if(carNatureCode.equals("")){
              throw new UserException( -96, -1167, "BLPrpJplanFee.getCarNature",
              "车辆种类"+dbPrpCitemCar.getCarKindCode()
               +" 使用性质" + dbPrpCitemCar.getUseNatureCode()
               +"，在prpdcarnature表中没有配置，请查证！" );
        	}else if(!Pattern.matches("^0[1-9]$", carNatureCode)){
              throw new UserException( -96, -1167, "BLPrpJplanFee.getCarNature",
                        "车辆种类"+dbPrpCitemCar.getCarKindCode()
                         +" 使用性质" + dbPrpCitemCar.getUseNatureCode()
                         +"，在prpdcarnature表中配置错误，请查证！" );	
        	}
        	iSchema.setCarNatureCode(carNatureCode);
        	iSchema.setUseNatureCode(dbPrpCitemCar.getUseNatureCode());

        	
























































































            
            if(dbPrpCitemCar.getCarKindCode().equals("A0")){
                iSchema.setCarProperty(dbPrpCitemCar.getSeatCount());  
            }else if(dbPrpCitemCar.getCarKindCode().equals("H0")){
                iSchema.setCarProperty(dbPrpCitemCar.getTonCount());  
            }else if(dbPrpCitemCar.getCarKindCode().substring(0,1).equals("M")){
                iSchema.setCarProperty(dbPrpCitemCar.getExhaustScale());  
            }else if(dbPrpCitemCar.getCarKindCode().substring(0,1).equals("J")){
                iSchema.setCarProperty(dbPrpCitemCar.getExhaustScale());  
            }else{
                iSchema.setCarProperty("");            
            }            
          	
        }
    }

    
    /**
     * @author lijibin 20061025
     * @desc 根据车辆种类和使用性质获取交强XXXXX九大类代码
     * @param CarKindCode
     * @param UseNatureCode        
     * @return strCarNatureCode   
     * @throws Exception
     */
    public String getCarNatureCode(DbPool dbpool,String iCarKindCode, String iUseNatureCode)
            throws Exception, UserException {
        String strCarNatureCode = "";

        strCarNatureCode=this.queryCarNatureCode(dbpool, iCarKindCode, iUseNatureCode);
    	if(strCarNatureCode.equals("")){
          throw new UserException( -96, -1167, "BLPrpJplanFee.getCarNatureCode",
          "车辆种类"+iCarKindCode
           +" 使用性质" + iUseNatureCode
           +"，在prpdcarnature表中没有配置，请查证！" );
    	}else if(!Pattern.matches("^0[1-9]$", strCarNatureCode)){
          throw new UserException( -96, -1167, "BLPrpJplanFee.getCarNatureCode",
                    "车辆种类"+iCarKindCode
                     +" 使用性质" + iUseNatureCode
                     +"，在prpdcarnature表中配置错误，请查证！" );	
    	}
        return strCarNatureCode;


/*        
        

        if((!iCarKindCode.substring(0,1).equals("M")
             &&!iCarKindCode.substring(0,1).equals("J")
             &&!iCarKindCode.substring(0,1).equals("T")
             &&!iCarKindCode.equals("G0"))
             && iUseNatureCode.equals("8A")){
        	
        	if(iCarKindCode.equals("A0")
                    && iUseNatureCode.equals("8A")){      	
            strCarNatureCode = "01";  
        }else if(iCarKindCode.equals("A0") 
                && (iUseNatureCode.equals("8B")
                   || iUseNatureCode.equals("8C")
                   || iUseNatureCode.equals("8D"))){
            strCarNatureCode = "02";  
        }else if(iCarKindCode.equals("A0")
                && (iUseNatureCode.equals("9A")
                   || iUseNatureCode.equals("9B")
                   || iUseNatureCode.equals("9C")
                   || iUseNatureCode.equals("9D")
                   || iUseNatureCode.equals("9E"))){
            strCarNatureCode = "03";  
        }else if(iCarKindCode.equals("H0")
                && (iUseNatureCode.equals("8B")
                   || iUseNatureCode.equals("8C")
                   || iUseNatureCode.equals("8D")
                   || iUseNatureCode.equals("8A"))){
            strCarNatureCode = "04";  
        }else if(iCarKindCode.equals("H0")
                && (iUseNatureCode.equals("9A")
                   || iUseNatureCode.equals("9B")
                   || iUseNatureCode.equals("9C")
                   || iUseNatureCode.equals("9D")
                   || iUseNatureCode.equals("9E"))){
            strCarNatureCode = "05";  
        }else if(iCarKindCode.substring(0,1).equals("T")){
            strCarNatureCode = "06";  
        }else if(iCarKindCode.substring(0,1).equals("M")){
            strCarNatureCode = "07";  
        }else if(iCarKindCode.substring(0,1).equals("J")){
            strCarNatureCode = "08";  
       
        }else if(iCarKindCode.substring(0,1).equals("G")){   
            strCarNatureCode = "09";  
        }else{
            
            
            throw new UserException( -96, -1167, "BLPrpJplanFee.getCarNatureCode",
                                      "车辆种类"+iCarKindCode
                                       +" 使用性质" + iUseNatureCode
                                       +"，没有对应的收付车辆种类信息，请查证！" );
        }
*/

    }
    /**
     * @desc 日志输出
     * @param iLog
     * @throws Exception
     */
    public void logPrintln(String iLog) throws Exception
    {
      ChgDate chgDate = new ChgDate();
      logger.info(chgDate.getCurrentTime("yyyy-MM-dd hh:mm:ss")+"><"+iLog);
    }
    
    
    /**
     * @author zhanglingjian 转入车船使用税
     * @param dbpool 数据库连接池
     * @param schema 对应XXXXX批单的传数对象
     * @throws UserException
     * @throws Exception
     */
    public String[] transCarShipTax(DbPool dbpool,PrpJplanFeeSchema schema,DBPrpCmain dbPrpCmain) throws UserException,Exception
    {
    	
    	BLPrpJtaxSettle blPrpJtaxSettle = new BLPrpJtaxSettle();
        DBPrpCcarshipTax dbPrpCcarshipTax = new DBPrpCcarshipTax();
        DBPrpCitemCar dbPrpCitemCar = new DBPrpCitemCar();
        DBPrpCinsured dbPrpCinsured = new DBPrpCinsured();
        PrpJplanFeeSchema prpJplanFeeSchema = null;
        String[] strTaxPayer = new String[3];
        int intReturn = 0;
        intReturn = dbPrpCcarshipTax.getInfo(dbpool,schema.getCertiNo(),schema.getSerialNo());
        dbPrpCitemCar.getInfo(dbpool,schema.getPolicyNo(),"1");
        dbPrpCinsured.getInfo(dbpool,schema.getPolicyNo(),schema.getSerialNo());
        
        
        if(intReturn == 100)
          return strTaxPayer;
        
        
        if((dbPrpCcarshipTax.getTaxActual() != null)&&Double.parseDouble(dbPrpCcarshipTax.getTaxActual()) != 0)
        {
        	prpJplanFeeSchema = new PrpJplanFeeSchema();
        	prpJplanFeeSchema.setSchema(schema);
        	prpJplanFeeSchema.setPayRefReason("R72");
        	prpJplanFeeSchema.setTaxSettleFlag("0");
        	prpJplanFeeSchema.setTaxpayerCode(dbPrpCcarshipTax.getTaxpayerCode());
        	prpJplanFeeSchema.setTaxpayerName(dbPrpCcarshipTax.getTaxpayerName());
        	prpJplanFeeSchema.setIdentifyNumber(dbPrpCcarshipTax.getIdentifyNumber());
        	prpJplanFeeSchema.setPlanFee(dbPrpCcarshipTax.getTaxActual());
        	this.setArr(prpJplanFeeSchema);
        	blPrpJtaxSettle.transTotaxSettleP(dbpool,prpJplanFeeSchema,dbPrpCcarshipTax,dbPrpCitemCar,dbPrpCinsured,dbPrpCmain);
        }
        
        
        if((dbPrpCcarshipTax.getPreviousPay() != null)&&Double.parseDouble(dbPrpCcarshipTax.getPreviousPay()) != 0)
        {
        	prpJplanFeeSchema = new PrpJplanFeeSchema();
        	prpJplanFeeSchema.setSchema(schema);
        	prpJplanFeeSchema.setPayRefReason("R73");
        	prpJplanFeeSchema.setTaxSettleFlag("0");
        	prpJplanFeeSchema.setTaxpayerCode(dbPrpCcarshipTax.getTaxpayerCode());
        	prpJplanFeeSchema.setTaxpayerName(dbPrpCcarshipTax.getTaxpayerName());
        	prpJplanFeeSchema.setIdentifyNumber(dbPrpCcarshipTax.getIdentifyNumber());
        	prpJplanFeeSchema.setPlanFee(dbPrpCcarshipTax.getPreviousPay());
        	this.setArr(prpJplanFeeSchema);
        	blPrpJtaxSettle.transTotaxSettleP(dbpool,prpJplanFeeSchema,dbPrpCcarshipTax,dbPrpCitemCar,dbPrpCinsured,dbPrpCmain);
        }
        
        
        if((dbPrpCcarshipTax.getLateFee() != null)&&Double.parseDouble(dbPrpCcarshipTax.getLateFee()) != 0)
        {
        	prpJplanFeeSchema = new PrpJplanFeeSchema();
        	prpJplanFeeSchema.setSchema(schema);
        	prpJplanFeeSchema.setPayRefReason("R74");
        	prpJplanFeeSchema.setTaxSettleFlag("0");
        	prpJplanFeeSchema.setTaxpayerCode(dbPrpCcarshipTax.getTaxpayerCode());
        	prpJplanFeeSchema.setTaxpayerName(dbPrpCcarshipTax.getTaxpayerName());
        	prpJplanFeeSchema.setIdentifyNumber(dbPrpCcarshipTax.getIdentifyNumber());
        	prpJplanFeeSchema.setPlanFee(dbPrpCcarshipTax.getLateFee());
        	this.setArr(prpJplanFeeSchema);
        	blPrpJtaxSettle.transTotaxSettleP(dbpool,prpJplanFeeSchema,dbPrpCcarshipTax,dbPrpCitemCar,dbPrpCinsured,dbPrpCmain);
        }
        
        if(dbPrpCcarshipTax.getTaxRelifFlag().equals("2") || 
        		dbPrpCcarshipTax.getTaxRelifFlag().equals("4") ||
        		dbPrpCcarshipTax.getTaxRelifFlag().equals("5"))
        {
        	prpJplanFeeSchema = new PrpJplanFeeSchema();
        	prpJplanFeeSchema.setSchema(schema);
        	prpJplanFeeSchema.setPayRefReason("R72");
        	prpJplanFeeSchema.setTaxSettleFlag("0");
        	prpJplanFeeSchema.setTaxpayerCode(dbPrpCcarshipTax.getTaxpayerCode());
        	prpJplanFeeSchema.setTaxpayerName(dbPrpCcarshipTax.getTaxpayerName());
        	prpJplanFeeSchema.setIdentifyNumber(dbPrpCcarshipTax.getIdentifyNumber());
        	prpJplanFeeSchema.setPlanFee("0");
        	blPrpJtaxSettle.transTotaxSettleP(dbpool,prpJplanFeeSchema,dbPrpCcarshipTax,dbPrpCitemCar,dbPrpCinsured,dbPrpCmain);
        }
        
        strTaxPayer[0] = dbPrpCcarshipTax.getTaxpayerCode();
        strTaxPayer[1] = dbPrpCcarshipTax.getTaxpayerName();
        strTaxPayer[2] = dbPrpCcarshipTax.getIdentifyNumber();
    	return strTaxPayer;
    	
    }
    
    
    /**
     * @author zhanglingjian 转入车船使用税批单
     * @param dbpool 数据库连接池
     * @param schema 对应XXXXX批单的传数对象
     * @throws UserException
     * @throws Exception
     */
    public String[] transEndorCarShipTax(DbPool dbpool,PrpJplanFeeSchema schema,DBPrpCmain dbPrpCmain) throws UserException,Exception
    {
    	
    	BLPrpJtaxSettle blPrpJtaxSettle = new BLPrpJtaxSettle();
        DBPrpCitemCar dbPrpCitemCar = new DBPrpCitemCar();
        DBPrpCinsured dbPrpCinsured = new DBPrpCinsured();
        DBPrpCcarshipTax dbPrpCcarshipTax = new DBPrpCcarshipTax();
        DBPrpPcarshipTax dbPrpPcarshipTax = new DBPrpPcarshipTax();
        PrpJplanFeeSchema prpJplanFeeSchema = null;
        String[] strTaxPayer = new String[3];
        int intReturn = 0;
        intReturn = dbPrpPcarshipTax.getInfo(dbpool,schema.getCertiNo(),schema.getSerialNo());
        dbPrpCcarshipTax.getInfo(dbpool,schema.getPolicyNo(),schema.getSerialNo());
        dbPrpCitemCar.getInfo(dbpool,schema.getPolicyNo(),"1");
        dbPrpCinsured.getInfo(dbpool,schema.getPolicyNo(),schema.getSerialNo());
        
        if(intReturn == 100)
          return strTaxPayer;
        
        
        if((dbPrpPcarshipTax.getChgTaxActual()!=null)&&Double.parseDouble(dbPrpPcarshipTax.getChgTaxActual()) != 0)
        {
        	prpJplanFeeSchema = new PrpJplanFeeSchema();
        	prpJplanFeeSchema.setSchema(schema);
        	prpJplanFeeSchema.setPayRefReason("R72");
        	prpJplanFeeSchema.setTaxSettleFlag("0");
        	prpJplanFeeSchema.setTaxpayerCode(dbPrpCcarshipTax.getTaxpayerCode());
        	prpJplanFeeSchema.setTaxpayerName(dbPrpCcarshipTax.getTaxpayerName());
        	prpJplanFeeSchema.setIdentifyNumber(dbPrpCcarshipTax.getIdentifyNumber());
        	prpJplanFeeSchema.setPlanFee(dbPrpPcarshipTax.getChgTaxActual());
        	this.setArr(prpJplanFeeSchema);
        	blPrpJtaxSettle.transTotaxSettleE(dbpool,prpJplanFeeSchema,dbPrpCcarshipTax,dbPrpCitemCar,dbPrpCinsured,dbPrpCmain);
        }else
        {
      	    blPrpJtaxSettle.updateTaxSettle(dbpool,schema,dbPrpCcarshipTax,
  	    		dbPrpCitemCar,dbPrpCinsured,"R72");
        }
        
        
        if((dbPrpPcarshipTax.getChgPreviousPay()!=null)&&Double.parseDouble(dbPrpPcarshipTax.getChgPreviousPay()) != 0)
        {
        	prpJplanFeeSchema = new PrpJplanFeeSchema();
        	prpJplanFeeSchema.setSchema(schema);
        	prpJplanFeeSchema.setPayRefReason("R73");
        	prpJplanFeeSchema.setTaxSettleFlag("0");
        	prpJplanFeeSchema.setTaxpayerCode(dbPrpCcarshipTax.getTaxpayerCode());
        	prpJplanFeeSchema.setTaxpayerName(dbPrpCcarshipTax.getTaxpayerName());
        	prpJplanFeeSchema.setIdentifyNumber(dbPrpCcarshipTax.getIdentifyNumber());
        	prpJplanFeeSchema.setPlanFee(dbPrpPcarshipTax.getChgPreviousPay());
        	this.setArr(prpJplanFeeSchema);
        	blPrpJtaxSettle.transTotaxSettleE(dbpool,prpJplanFeeSchema,dbPrpCcarshipTax,dbPrpCitemCar,dbPrpCinsured,dbPrpCmain);
        }else
        {
        	blPrpJtaxSettle.updateTaxSettle(dbpool,schema,dbPrpCcarshipTax,
    	    		dbPrpCitemCar,dbPrpCinsured,"R73");
        }
        
        
        if((dbPrpPcarshipTax.getChgLateFee()!=null)&&Double.parseDouble(dbPrpPcarshipTax.getChgLateFee()) != 0)
        {
        	prpJplanFeeSchema = new PrpJplanFeeSchema();
        	prpJplanFeeSchema.setSchema(schema);
        	prpJplanFeeSchema.setPayRefReason("R74");
        	prpJplanFeeSchema.setTaxSettleFlag("0");
        	prpJplanFeeSchema.setTaxpayerCode(dbPrpCcarshipTax.getTaxpayerCode());
        	prpJplanFeeSchema.setTaxpayerName(dbPrpCcarshipTax.getTaxpayerName());
        	prpJplanFeeSchema.setIdentifyNumber(dbPrpCcarshipTax.getIdentifyNumber());
        	prpJplanFeeSchema.setPlanFee(dbPrpPcarshipTax.getChgLateFee());
        	this.setArr(prpJplanFeeSchema);
        	blPrpJtaxSettle.transTotaxSettleE(dbpool,prpJplanFeeSchema,dbPrpCcarshipTax,dbPrpCitemCar,dbPrpCinsured,dbPrpCmain);
        }else
        {
        	blPrpJtaxSettle.updateTaxSettle(dbpool,schema,dbPrpCcarshipTax,
    	    		dbPrpCitemCar,dbPrpCinsured,"R74");
        }
        
        strTaxPayer[0] = dbPrpCcarshipTax.getTaxpayerCode();
        strTaxPayer[1] = dbPrpCcarshipTax.getTaxpayerName();
        strTaxPayer[2] = dbPrpCcarshipTax.getIdentifyNumber();
    	return strTaxPayer;
    	
    }
    
    
    /**
     * @author zhanglingjian 结算和反结算车船使用税
     * @param certiNo XX号/批单号
     * @param serialNo 缴费序号
     * @param settleType 0:反结算，1：结算
     * @throws UserException
     * @throws Exception
     */
    public void settleCarShipTax(String certiNo,String serialNo,String settleType) throws UserException,Exception
    {
        DbPool dbpool = new DbPool();
        try
        {
            
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
        	ChgDate chgDate  = new ChgDate();
        	String currentDate = chgDate.getCurrentTime("yyyy-MM-dd");
        	String strSQL = " CertiNo='"+certiNo+"' AND SerialNo='"+serialNo+"'";
        	BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
        	blPrpJplanFee.query(strSQL);
        	for(int i=0;i<blPrpJplanFee.getSize();i++)
        	{
        		DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
        		dbPrpJplanFee.setSchema(blPrpJplanFee.getArr(i));
        		if(settleType.equals("1"))
        		{
        			dbPrpJplanFee.setTaxSettleFlag("1");
        		}else if(settleType.equals("0"))
        		{
        			dbPrpJplanFee.setTaxSettleFlag("0");
        		}
        		dbPrpJplanFee.setTaxSettleDate(currentDate);
        		dbPrpJplanFee.update(dbpool);
        	}
        	dbpool.close();
        }
        catch(SQLException sqlException)
        {
          dbpool.close();
          throw sqlException;
        }
        catch(Exception e)
        {
          dbpool.close();
          throw e;
        }
        finally
        {
          dbpool.close();
        }
    }
    
    /**
     * @author zhanglingjian 20070615 更新被XX人，XX人信息等
     * @param dbpool
     * @param iWherePart
     * @param dbPrpPhead
     * @param dbPrpCmain
     * @throws Exception
     */
    public void updatePeople(DbPool dbpool,DBPrpPhead dbPrpPhead,DBPrpCmain dbPrpCmain) throws Exception
    {
    	
    	
    	if(dbPrpPhead.getEndorType().indexOf("04") > -1)
    	{
    		String prpJplanFeeSQL = "update PrpJplanFee set InsuredCode=?,InsuredName=? where policyno=?";
    		String prpJpayRefRecSQL = "update PrpJpayRefRec set InsuredCode=?,InsuredName=? where policyno=?";
            dbpool.prepareInnerStatement(prpJplanFeeSQL);
            int index = 1;
            dbpool.setString(index++,dbPrpCmain.getInsuredCode());
            dbpool.setString(index++,dbPrpCmain.getInsuredName());
            dbpool.setString(index++,dbPrpPhead.getPolicyNo());
            dbpool.executePreparedUpdate();
            dbpool.closePreparedStatement(); 
            
            dbpool.prepareInnerStatement(prpJpayRefRecSQL);
            index = 1;
            dbpool.setString(index++,dbPrpCmain.getInsuredCode());
            dbpool.setString(index++,dbPrpCmain.getInsuredName());
            dbpool.setString(index++,dbPrpPhead.getPolicyNo());
            dbpool.executePreparedUpdate();
            dbpool.closePreparedStatement();
            
    		String prpJplanFeeSQL1 = "update PrpJplanFee set AppliCode=?,AppliName=? where policyno=?";
    		String prpJpayRefRecSQL1 = "update PrpJpayRefRec set AppliCode=?,AppliName=? where policyno=?";
            dbpool.prepareInnerStatement(prpJplanFeeSQL1);
            index = 1;
            dbpool.setString(index++,dbPrpCmain.getAppliCode());
            dbpool.setString(index++,dbPrpCmain.getAppliName());
            dbpool.setString(index++,dbPrpPhead.getPolicyNo());
            dbpool.executePreparedUpdate();
            dbpool.closePreparedStatement();
            	
            dbpool.prepareInnerStatement(prpJpayRefRecSQL1);
            index = 1;
            dbpool.setString(index++,dbPrpCmain.getAppliCode());
            dbpool.setString(index++,dbPrpCmain.getAppliName());
            dbpool.setString(index++,dbPrpPhead.getPolicyNo());
            dbpool.executePreparedUpdate();
            dbpool.closePreparedStatement(); 
		}
    }
    
    /**
     * @desc 自动转入XXXXX的车船税应收凭证
     * @param dbpool
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iDate 截至日期
     * @param iOperatorCode 操作员代码
     * @throws Exception
     */
    public void transCarshipTax(DbPool dbpool, String iAccBookType,
                             String iAccBookCode, String iCenterCode,
                             String iBranchCode, String iDate, String iOperatorCode)
       throws UserException,Exception{
      String strWherePart = " 1=1";
      if(iAccBookType.equals(""))
        iAccBookType = SysConst.getProperty("ACCBOOKTYPE");
      strWherePart += Str.convertString("AccBookType",iAccBookType,"=");
      strWherePart += Str.convertString("AccBookCode",iAccBookCode,"=");
      strWherePart += Str.convertString("CenterCode",iCenterCode,"=");
      strWherePart += Str.convertString("BranchCode",iBranchCode,"=");
      BLAccBookBranch blAccBookBranch = new BLAccBookBranch();
      blAccBookBranch.query(dbpool,strWherePart,0);
      BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
      for(int i=0; i<blAccBookBranch.getSize(); i++)
      {
        String strBranchCode = blAccBookBranch.getArr(i).getBranchCode();
        
        AccMainVoucherSchema accMainVoucherSchema = new AccMainVoucherSchema();
        accMainVoucherSchema.setAccBookType(blAccBookBranch.getArr(i).getAccBookType());
        accMainVoucherSchema.setAccBookCode(blAccBookBranch.getArr(i).getAccBookCode());
        accMainVoucherSchema.setCenterCode(blAccBookBranch.getArr(i).getCenterCode());
        accMainVoucherSchema.setBranchCode(blAccBookBranch.getArr(i).getBranchCode());
        accMainVoucherSchema.setVoucherType("1");
        accMainVoucherSchema.setGenerateWay("6");
        accMainVoucherSchema.setVoucherDate(iDate);
        accMainVoucherSchema.setOperatorBranch(blAccBookBranch.getArr(i).getBranchCode());
        accMainVoucherSchema.setOperatorCode(iOperatorCode);
        accMainVoucherSchema.setVoucherFlag("1");

        
        String[] arrComCode = blPrpDcompany.getLowerComCodeNew(dbpool,strBranchCode);
        
        if(arrComCode.length<1000){
            strWherePart = "'"+strBranchCode+"'";
            for(int j=0; j<arrComCode.length; j++)
              strWherePart += ",'"+arrComCode[j]+"'";
            
            strWherePart = " ComCode IN ("+strWherePart+")"
                + " AND (( startdate <= '"+iDate+"' "
                + " AND underwritedate <= '"+iDate+"' AND certitype = 'P')"
                + "  OR "
                + " (validdate <= '"+iDate+"' "
                + " AND underwritedate <= '"+iDate+"' AND certitype = 'E'))"  
                + " AND PayRefReason in ('R72','R73','R74')" 
                + " AND riskcode='0507'" 
                + " AND RiskCode!='YAB0'"; 
            
            
        }else{
        	String strComCodeWhere = " ( ComCode IN ('" + strBranchCode + "'"; 
        	for(int j=0;j<999;j++){
        		strComCodeWhere += ",'" + arrComCode[j] + "'";
        	}
        	strComCodeWhere += ") OR ComCode IN ('" + arrComCode[999] + "'";
        	for(int j=1000;j<arrComCode.length;j++){
        		strComCodeWhere += ",'" + arrComCode[j] + "'";
        	}
        	strComCodeWhere += " ) )";
        	
        	strWherePart = strComCodeWhere 	     	
        	              + " AND (( startdate <= '"+iDate+"' "
                          + " AND underwritedate <= '"+iDate+"' AND certitype = 'P')"
                          + "  OR "
                          + " (validdate <= '"+iDate+"' "
                          + " AND underwritedate <= '"+iDate+"' AND certitype = 'E'))" 
                          + " AND PayRefReason in ('R72','R73','R74')" 
                          + " AND riskcode='0507'" 
                          + " AND RiskCode!='YAB0'";
        	
        }
        
        this.createVoucherShip(dbpool,strWherePart,accMainVoucherSchema);
      }
    }
    /**
     * @desc 自动应收XX挂账凭证生成主函数  
     * @author liufengyao
     * @param dbpool
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @throws Exception
     */
    public void createVoucherShip(DbPool dbpool, String iWherePart,AccMainVoucherSchema iAccMainVoucherSchema) 
      throws UserException, Exception{   	
      String strSQL = "";
      
      String strYearMonth = iAccMainVoucherSchema.getVoucherDate().substring(0,4)
                          + iAccMainVoucherSchema.getVoucherDate().substring(5,7);
      
      BLAccVoucher blAccVoucher = new BLAccVoucher();
      PrpJplanFeeSchema iPlanFeeschema = null;
      PrpJpayRefRecSchema iPayRefSchema = null;
      this.logPrintln("kai shi cha xun shou fu shu ju");
      this.initArr();
      
      iAccMainVoucherSchema.setAuxNumber("0");
      iAccMainVoucherSchema.setYearMonth(strYearMonth);
      iAccMainVoucherSchema.setVoucherNo("");
      blAccVoucher.getBLAccMainVoucher().setArr(iAccMainVoucherSchema);
      
      
      
      StringBuffer planBuffer = new StringBuffer();    
      
      planBuffer.append(" SELECT ClassCode,RiskCode,CarNatureCode,ComCode,Handler1Code,Currency1,SUM(PlanFee) AS PlanFee,PayRefReason ");
      planBuffer.append(" FROM PrpJplanFee WHERE ");
      planBuffer.append(iWherePart);
      
      
      planBuffer.append(" AND VoucherNo='0' ");
      
      planBuffer.append(" GROUP BY ClassCode,RiskCode,CarNatureCode,ComCode,Handler1Code,Currency1,PayRefReason ");
      
      strSQL = planBuffer.toString();
      ResultSet rs = dbpool.query(strSQL);
      while(rs.next()){
    	  iPlanFeeschema = new PrpJplanFeeSchema();
    	  iPlanFeeschema.setClassCode(rs.getString("ClassCode"));
    	  iPlanFeeschema.setRiskCode(rs.getString("RiskCode"));
    	  iPlanFeeschema.setCarNatureCode(rs.getString("CarNatureCode"));
    	  iPlanFeeschema.setComCode(rs.getString("ComCode"));
    	  iPlanFeeschema.setHandler1Code(rs.getString("Handler1Code"));
    	  iPlanFeeschema.setCurrency1(rs.getString("Currency1"));
    	  iPlanFeeschema.setPlanFee(""+rs.getDouble("PlanFee"));
    	  
    	  iPlanFeeschema.setPayRefReason(""+rs.getString("PayRefReason"));
    	  
    	  iPlanFeeschema.setFlag("0");
        this.setArr(iPlanFeeschema);
      }
      rs.close();
      this.logPrintln("prpjplanfee biao shu ju ge shu wei："+this.getSize());
      
      double dblExchRate = 1;
      double dblPlanFeeCNY = 0;
      for(int i=0; i<this.getSize(); i++){
        dblExchRate = PubTools.getExchangeRate(dbpool,this.getArr(i).getCurrency1(),"CNY",
                                               iAccMainVoucherSchema.getVoucherDate());
        this.getArr(i).setExchangeRate(""+dblExchRate);
        dblPlanFeeCNY = Double.parseDouble(this.getArr(i).getPlanFee())*dblExchRate;
        dblPlanFeeCNY = Str.round(Str.round(dblPlanFeeCNY,8),2);
        this.getArr(i).setPlanFeeCNY(""+dblPlanFeeCNY);
      }
      this.logPrintln("prpjplanfee biao shu ju huo qu hui lv jie shu");
      
      this.createSubVoucher(dbpool,blAccVoucher,iAccMainVoucherSchema);
      
      BLAccSubVoucher blAccSubVoucher = blAccVoucher.getBLAccSubVoucher();
      if(blAccSubVoucher.getSize()==0){
    	  this.logPrintln("mei you sheng cheng ping zheng zi biao xin xi！");
          return;
      }

      for (int i = 0; i < blAccSubVoucher.getSize(); i++)
        blAccSubVoucher.createRawArticle(dbpool, blAccSubVoucher.getArr(i), "");
      
      blAccSubVoucher.voucherComp("111");
      
      blAccSubVoucher.voucherOrder();
      blAccVoucher.setBLAccVoucherArticle(blAccSubVoucher.genVoucherArticle());
      this.logPrintln("kai shi bao cun ping zheng ");
      
      blAccVoucher.save(dbpool);
      this.blAccMainVoucher.setArr(blAccVoucher.getBLAccMainVoucher().getArr(0));
      this.logPrintln("kai shi hui xie ying shou ying fu biao de ping zheng xin xi");
      
      String strVoucherNo = blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
      for(int i=0; i<this.getSize(); i++){
          strSQL = "UPDATE PrpJplanFee SET AccBookType='" + iAccMainVoucherSchema.getAccBookType()
              + "',AccBookCode='" + iAccMainVoucherSchema.getAccBookCode()
              + "',CenterCode='" + iAccMainVoucherSchema.getCenterCode()
              + "',BranchCode='" + iAccMainVoucherSchema.getBranchCode()
              + "',YearMonth='" + iAccMainVoucherSchema.getYearMonth()
              + "',VoucherNo='" + strVoucherNo
              + "',ExchangeRate='" + this.getArr(i).getExchangeRate()
              + "',PlanFeeCNY=PlanFee*" + this.getArr(i).getExchangeRate()
              + " WHERE ClassCode='" + this.getArr(i).getClassCode()
              + "' AND RiskCode='" + this.getArr(i).getRiskCode()
              + "' AND ComCode='" + this.getArr(i).getComCode()
              + "' AND Currency1='" + this.getArr(i).getCurrency1()
              + "' AND " + iWherePart
              
              
              + "  AND VoucherNo='0'";
              
        dbpool.update(strSQL);
      }

      this.logPrintln("sheng cheng dai shou che chuan shui ping zheng "+iAccMainVoucherSchema.getCenterCode()+" "+strVoucherNo);
    }
    
    /**
     * @author lijibin 20060804 根据车辆种类和使用性质获取XXXXX监会车辆种类
     * @param dbpool
     * @param iCarKindCode
     * @param iUseNatureCode
     * @return
     * @throws Exception
     */
    public String queryCarNatureCode(DbPool dbpool, String iCarKindCode,
            String iUseNatureCode) throws Exception {
        String strCarNatureCode = "";
        String strSQL = "";
        strSQL = "SELECT CarNatureCode FROM PrpDcarNature WHERE CarKindCode='"
                + iCarKindCode + "' AND UseNatureCode='" + iUseNatureCode + "'";

        ResultSet rs = dbpool.query(strSQL);
        if (rs.next()) {
            strCarNatureCode = rs.getString(1);
        }
        return strCarNatureCode;
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
     * @return 
     * @throws Exception
     */
    public Vector getAllPolicy(String[] arrCertiType,String[] arrCertiNo,String[] arrSerialNo,
    		String[] arrPayRefReason,boolean isChinese) throws Exception{
        DbPool dbpool = new DbPool();
        
        String strDataSource =SysConfig.CONST_PAYMENTDATASOURCE;
        
        Vector schema = new Vector();

        try{
            
            dbpool.open(strDataSource);
            this.getAllPolicy(dbpool,arrCertiType,arrCertiNo,arrSerialNo,arrPayRefReason,isChinese);
            dbpool.close();
        }
        catch(Exception exception){
            dbpool.close();
            throw exception;
        }
        finally{
            dbpool.close();
        }
        return schema;
    }
    
    /**
     * @author zhanglingjian 20070718
     * @return 
     * @throws Exception
     */
    public void getAllPolicy(DbPool dbpool,String[] arrCertiType,String[] arrCertiNo,
    		String[] arrSerialNo,String[] arrPayRefReason,boolean isChinese) throws Exception{
        for(int i = 0;i < arrCertiType.length;i++)
        {
        	DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
            dbPrpJplanFee.getInfo(dbpool,arrCertiType[i],arrCertiNo[i],arrSerialNo[i],arrPayRefReason[i]);
            this.setArr(dbPrpJplanFee);
        }
        this.translateCode(dbpool,isChinese);
    }
    
    
    /**
     * @author xushaojiang 20070806
     * @return 
     * @throws Exception
     */
    public Vector queryWithSequence(String  strWhere,boolean isChinese) throws Exception{
        DbPool dbpool = new DbPool();
        
        String strDataSource =SysConfig.CONST_PAYMENTDATASOURCE;
        
        Vector schema = new Vector();
        try{
            
            dbpool.open(strDataSource);
            this.queryWithSequence(dbpool,strWhere,isChinese);
            dbpool.close();
        }
        catch(Exception exception){
            dbpool.close();
            throw exception;
        }
        finally{
            dbpool.close();
        }
        return schema;
    }
    
    /**
     * @author xushaojiang 20070806
     * @return 
     * @throws Exception
     */
    public void queryWithSequence(DbPool dbpool,String strWhere,boolean isChinese) throws Exception{
    	this.query(dbpool, strWhere, 0);
        this.translateCode(dbpool,isChinese);
    }
    /**
     * @author xushaojiang 20070806
     * @return 
     * @throws Exception
     */
    public String updateOperateSequence(String[] check, String[] arrCertiType,String[] arrCertiNo,String[] arrSerialNo,
    		String[] arrPayRefReason) throws Exception{
        DBManager    dbManager     = new DBManager();
        DBPrpJplanFee dbPrpJplanFeeSeg = new DBPrpJplanFee();
        
        String strDataSource =SysConfig.CONST_PAYMENTDATASOURCE;
        
        String OperateSequence = "";
        try{
            
            dbManager.open(strDataSource);
            dbManager.beginTransaction();
            OperateSequence=dbPrpJplanFeeSeg.updateOperateSequence(dbManager,check,arrCertiType,arrCertiNo,arrSerialNo,arrPayRefReason);
            dbManager.commitTransaction();
        }
        catch(Exception exception){
        	dbManager.rollbackTransaction();
            throw exception;
        }
        finally{
        	dbManager.close();
        }
        return OperateSequence;
    }












    
    
    /**
     *手续费支付单生成分页查询
     *@author zhanglingjian 20071016
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@param iPageNo 页码
     *@param iPageCount 每页的记录数
     *@throws UserException
     *@throws Exception
     */
    public String  updateOperateSequence(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      if (iLimitCount > 0 && dbPrpJplanFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJplanFee.query");
      }else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJplanFee WHERE " + iWherePart;
        schemas = dbPrpJplanFee.findByConditions(strSqlStatement);
        return updateOperateSequence(schemas);
      }
    }
    
    /**
     *手续费支付单生成分页查询
     *@author zhanglingjian 20071016
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@param iPageNo 页码
     *@param iPageCount 每页的记录数
     *@throws UserException
     *@throws Exception
     */
    public String  updateOperateSequence(String iWherePart,String payRefNo,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      if (iLimitCount > 0 && dbPrpJplanFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJplanFee.query");
      }else
      {
        initArr();
        strSqlStatement = " SELECT PrpJplanFee.* FROM PrpJplanFee,prpjpayrefrec WHERE " +
			" PrpJplanFee.certitype=prpjpayrefrec.certitype and PrpJplanFee.certino=prpjpayrefrec.certino " +
			" and PrpJplanFee.serialno=prpjpayrefrec.serialno and PrpJplanFee.payrefreason=prpjpayrefrec.payrefreason" +
			" and prpjpayrefrec.payreftimes='1' and prpjpayrefrec.payrefno='"+payRefNo+"' and "+ iWherePart;
        schemas = dbPrpJplanFee.findByConditions(strSqlStatement);
        return updateOperateSequence(schemas);
      }
    }
    
    /**
     * 手续费支付单打印查询 zhanglingjian 20071016
     * @param iWherePart PrpJplanFee的查询条件
     * @param iRecWherePart PrpJpayRefRec的查询条件
     * @param iOthWherePart 排序
     * @param iLimitCount
     * @throws UserException
     * @throws Exception
     */
    public String updateOperateSequence(String iWherePart,String iRecWherePart,String iOthWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      if (iLimitCount > 0 && dbPrpJplanFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefMain.queryPrint");
      }else
      {
    	  initArr();
	      strSqlStatement = " SELECT DISTINCT PrpJplanFee.* FROM PrpJplanFee ,PrpJpayRefRec "
	      	+ " WHERE "
	          + iWherePart
				+ " AND PrpJplanFee.CertiNo=PrpJPayRefRec.Certino(+) ";
	        if(!iRecWherePart.equals("")){
	        	strSqlStatement += "AND PrpJpayRefRec.CertiType IN ('P','E') " + iRecWherePart;
          }
          strSqlStatement += iOthWherePart;
          schemas = dbPrpJplanFee.findByConditions(strSqlStatement);
          return updateOperateSequence(schemas);
      }
    }
    
    /**
     * 手续费支付单打印查询 zhanglingjian 20071016
     * @param iWherePart PrpJplanFee的查询条件
     * @param iRecWherePart PrpJpayRefRec的查询条件
     * @param iOthWherePart 排序
     * @param iLimitCount
     * @throws UserException
     * @throws Exception
     */
    public String updateOperateSequence(String iWherePart,String iRecWherePart,String iOthWherePart,String payRefNo,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      if (iLimitCount > 0 && dbPrpJplanFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJplanFee.updateOperateSequence");
      }else
      {
    	  initArr();
	      strSqlStatement = " SELECT DISTINCT PrpJplanFee.* FROM PrpJplanFee ,PrpJpayRefRec,PrpJpayRefRec a "
	      	+ "WHERE "
	          + iWherePart
				+ " AND PrpJplanFee.CertiNo=PrpJPayRefRec.Certino(+) ";
	        if(!iRecWherePart.equals("")){
	        	strSqlStatement += "AND PrpJpayRefRec.CertiType IN ('P','E') " + iRecWherePart;
        }
	    strSqlStatement += " and PrpJplanFee.certitype=a.certitype and PrpJplanFee.certino=a.certino " +
    		" and PrpJplanFee.serialno=a.serialno and PrpJplanFee.payrefreason=a.payrefreason " +
    		" and a.payreftimes='1' and a.payrefno='"+payRefNo+"'";
        strSqlStatement += iOthWherePart;
        schemas = dbPrpJplanFee.findByConditions(strSqlStatement);
        return updateOperateSequence(schemas);
      }
    }
    
    /**
     * 手续费支付单打印查询 zhanglingjian 20071017
     * @param iWherePart PrpJplanFee的查询条件
     * @param iRecWherePart PrpJpayRefRec的查询条件
     * @param iOthWherePart 排序
     * @param iLimitCount
     * @throws UserException
     * @throws Exception
     */
    public void queryBySequence(String operateSequence,int iPageNo,int iPageCount,int iLimitCount) throws UserException,Exception
    {
    	String strSqlStatement = "";
    	initArr();
    	DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
    	strSqlStatement = "select * from (select rownum as rn,prpjplanfee.* from prpjplanfee where operateSequence='"+operateSequence+
    		"') where rn>"+(iPageNo*iPageCount-iPageCount)+" and rn<="+iPageNo*iPageCount;
        schemas = dbPrpJplanFee.findByConditions(strSqlStatement);
    }
    
    /**
     * @author zhanglingjian 20071016
     * @return 
     * @throws Exception
     */
    public String updateOperateSequence(Vector schemas) throws Exception{
        DBManager    dbManager     = new DBManager();
        DBPrpJplanFee dbPrpJplanFeeSeg = new DBPrpJplanFee();
        
        String strDataSource = SysConfig.CONST_PAYMENTDATASOURCE;
        
        String OperateSequence = "";
        try{
            
            dbManager.open(strDataSource);
            dbManager.beginTransaction();
            OperateSequence = dbPrpJplanFeeSeg.updateOperateSequence(dbManager,schemas);
            dbManager.commitTransaction();
        }
        catch(Exception exception){
        	dbManager.rollbackTransaction();
            throw exception;
        }
        finally{
        	dbManager.close();
        }
        return OperateSequence;
    }
    
    /**
     *不带dbpool的查询方法
     *@param arrPolicyNo
     *@param arrSerialNo
     *@return 无
     */
    public int addPolicy(String[] arrCheck,String[] arrCertiType,String[] arrCertiNo,String[] arrSerialNo,
    		String[] arrPayRefReason,String payRefNo,String currency2,String exchangeRate) throws Exception
    {
    	DbPool dbpool = new DbPool();
    	int count = 0;

        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
        	dbpool.beginTransaction();
        	count = addPolicy(dbpool,arrCheck,arrCertiType,arrCertiNo,arrSerialNo,
        				arrPayRefReason,payRefNo,currency2,exchangeRate);
        	dbpool.commitTransaction();
	    }
        catch (Exception e)
        {
        	dbpool.rollbackTransaction();
        	throw e;
        }
        finally {
        	dbpool.close();
        }
        return count;
    }
    
    /**
     * @author zhanglingjian 
     * @param dbpool 数据库连接池
     * @param arrPolicyNo
     * @param arrSerialNo
     * @throws UserException
     * @throws Exception
     */
    public int addPolicy(DbPool dbpool,String[] arrCheck,String[] arrCertiType,String[] arrCertiNo,
    		String[] arrSerialNo,String[] arrPayRefReason,String payRefNo,String currency2,String exchangeRate)
    throws UserException,Exception
    {
    	int count = 0;
    	this.initArr();
    	for(int i=0;i<arrCheck.length;i++)
    	{
    		
    		DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
    		dbPrpJplanFee.getInfo(dbpool,arrCertiType[Integer.parseInt(arrCheck[i])],
    				arrCertiNo[Integer.parseInt(arrCheck[i])],
    				arrSerialNo[Integer.parseInt(arrCheck[i])],
    				arrPayRefReason[Integer.parseInt(arrCheck[i])]);
    		dbPrpJplanFee.setPayRefFee(dbPrpJplanFee.getPlanFee());
    		dbPrpJplanFee.update(dbpool);
    		
    		
    		BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
    		DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
    		dbPrpJpayRefRec.setSchema(blPrpJpayRefRec.genSchema(dbPrpJplanFee));
    		
    		ChgDate chgDate  = new ChgDate();
        	String currentDate = chgDate.getCurrentTime("yyyy-MM-dd");
    		
    	    double dblPayReFfee = 0;
    	    dblPayReFfee = Double.parseDouble(dbPrpJpayRefRec.getPlanFee())*Double.parseDouble(exchangeRate);
    	    dblPayReFfee = Str.round(Str.round(dblPayReFfee,8),2);
    	    dbPrpJpayRefRec.setCurrency2(currency2);
    	    dbPrpJpayRefRec.setExchangeRate(""+exchangeRate);
    	    dbPrpJpayRefRec.setPayRefFee(""+dblPayReFfee);
    	    dbPrpJpayRefRec.setOperateDate(currentDate);
    	    dbPrpJpayRefRec.setPayRefNo(payRefNo);
    	    dbPrpJpayRefRec.setIdentifyNumber("1");
    	    dbPrpJpayRefRec.setIdentifyType("0");
    	    dbPrpJpayRefRec.setFlag("0");
    	    
    	    blPrpJpayRefRec.isRealPayRef(dbpool,dbPrpJpayRefRec);
    	    
    	    
    	    DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
    	    dbPrpJpayRefMain.getInfo(dbpool,payRefNo,dbPrpJplanFee.getSerialNo());
    	    dbPrpJpayRefMain.setPayRefFee(""+(Double.parseDouble(dbPrpJpayRefMain.getPayRefFee())+
    	    		Double.parseDouble(dbPrpJpayRefRec.getPayRefFee())));
      	    String strSQL = " SELECT count(1) FROM prpjpayrefrec WHERE payrefno='"+payRefNo+"' " ;
            ResultSet resultSet = null;
            int count1 = 0;
            try{
                resultSet = dbpool.query(strSQL);
                if(resultSet.next()){
                }
                count1 = resultSet.getInt(1);
                resultSet.close();
            }catch(Exception e){
            	e.printStackTrace();
            	throw e;
            }finally {
            	if(resultSet != null)
            	{
            		resultSet.close();
            	}
            }
    	    if(count1 > 1000)
            {
              throw new UserException( -96, -1167,
                                      "BLPrpJplanFee.addPolicy()",
                                      "支付单" + payRefNo + "所含手续费个数大于1000，请重新选择！" );
            }
    	    dbPrpJpayRefRec.setOperatorCode(dbPrpJpayRefMain.getPackageCode());
    	    dbPrpJpayRefRec.setOperateUnit(dbPrpJpayRefMain.getPackageUnit());
    	    dbPrpJpayRefRec.setPayRefName(dbPrpJpayRefMain.getPayRefName());
    	    dbPrpJpayRefRec.insert(dbpool);
    	    
    	    dbPrpJpayRefMain.update(dbpool);
    	    count++;
    	}
    	return count;
    }
    
    /**
     *不带dbpool的查询方法
     *@param arrPolicyNo
     *@param arrSerialNo
     *@return 无
     */
    public int delPolicy(String[] arrCheck,String[] arrCertiType,String[] arrCertiNo,String[] arrSerialNo,
    		String[] arrPayRefReason,String payRefNo) throws Exception
    {
    	DbPool dbpool = new DbPool();
    	int count = 0;

        try {
            
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
            dbpool.beginTransaction();
            count = delPolicy(dbpool,arrCheck,arrCertiType,arrCertiNo,arrSerialNo,arrPayRefReason,payRefNo);
            dbpool.commitTransaction();
        }
        catch (Exception e)
        {
        	dbpool.rollbackTransaction();
        	throw e;
        }
        finally {
        	dbpool.close();
        }
        return count;
    }
    
    /**
     * @author zhanglingjian 
     * @param dbpool 数据库连接池
     * @param arrPolicyNo
     * @param arrSerialNo
     * @throws UserException
     * @throws Exception
     */
    public int delPolicy(DbPool dbpool,String[] arrCheck,String[] arrCertiType,String[] arrCertiNo,
    		String[] arrSerialNo,String[] arrPayRefReason,String payRefNo)
    throws UserException,Exception
    {
    	int count = 0;
    	this.initArr();
    	for(int i=0;i<arrCheck.length;i++)
    	{
    		
    		DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
    		dbPrpJplanFee.getInfo(dbpool,arrCertiType[Integer.parseInt(arrCheck[i])],
    				arrCertiNo[Integer.parseInt(arrCheck[i])],
    				arrSerialNo[Integer.parseInt(arrCheck[i])],
    				arrPayRefReason[Integer.parseInt(arrCheck[i])]);
    		dbPrpJplanFee.setPayRefFee("0");
    		dbPrpJplanFee.update(dbpool);
    		
    		
    		DBPrpJpayRefRec dbPrpJpayRefRec = new DBPrpJpayRefRec();
    		dbPrpJpayRefRec.getInfo(dbpool,arrCertiType[Integer.parseInt(arrCheck[i])],
    				arrCertiNo[Integer.parseInt(arrCheck[i])],
    				arrSerialNo[Integer.parseInt(arrCheck[i])],
    				arrPayRefReason[Integer.parseInt(arrCheck[i])],"1");
    	    double dblPayReFfee = Double.parseDouble(dbPrpJpayRefRec.getPayRefFee());
    	    dbPrpJpayRefRec.delete(dbpool,arrCertiType[Integer.parseInt(arrCheck[i])],
    				arrCertiNo[Integer.parseInt(arrCheck[i])],
    				arrSerialNo[Integer.parseInt(arrCheck[i])],
    				arrPayRefReason[Integer.parseInt(arrCheck[i])],"1");
    	    dblPayReFfee = Str.round(Str.round(dblPayReFfee,8),2);
    	    
    	    
    	    DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
    	    dbPrpJpayRefMain.getInfo(dbpool,payRefNo,dbPrpJplanFee.getSerialNo());
    	    dbPrpJpayRefMain.setPayRefFee(""+(Double.parseDouble(dbPrpJpayRefMain.getPayRefFee())
    	    		- dblPayReFfee));
    	    String strSQL = " SELECT count(1) FROM prpjpayrefrec WHERE payrefno='"+payRefNo+"' " ;
            ResultSet resultSet = null;
            int count1 = 0;
            try{
                resultSet = dbpool.query(strSQL);
                if(resultSet.next()){
                }
                count1 = resultSet.getInt(1);
                resultSet.close();
            }catch(Exception e){
            	e.printStackTrace();
            	throw e;
            }finally {
            	if(resultSet != null)
            	{
            		resultSet.close();
            	}
            }
    	    if(count1 < 1)
            {
              throw new UserException( -96, -1167,
                                      "BLPrpJplanFee.delPolicy()",
                                      "支付单" + payRefNo + "所含手续费个数小于1个，请重新选择！" );
            }
    	    dbPrpJpayRefMain.update(dbpool);
    	    count++;
    	}
    	return count;
    }
    
    /**
     * 不带dbpool的委托处理方法
     * @param CancleFlag 1-取消委托
     * @return 无
     */
    public int consignPayRec(String CancleFlag) throws Exception
    {
    	DbPool dbpool = new DbPool();
    	int count = 0;

        try {
        	
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
        	dbpool.beginTransaction();
        	count = consignPayRec(dbpool,CancleFlag);
        	dbpool.commitTransaction();
	    }
        catch (Exception e)
        {
        	dbpool.rollbackTransaction();
        	throw e;
        }
        finally {
        	dbpool.close();
        }
        return count;
    }
    
    /**
	 * 带dbpool的委托处理方法
	 * @param dbpool 数据库连接池
     * @param CancleFlag 1-取消委托
	 * @throws UserException
	 * @throws Exception
	 */
	public int consignPayRec(DbPool dbpool,String CancleFlag) throws UserException, Exception {
		int size = 0;
		for (int i = 0; i < this.getSize(); i++) {
			DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
			dbPrpJplanFee.getInfo(dbpool, this.getArr(i).getCertiType(), this.getArr(i)
					.getCertiNo(), this.getArr(i).getSerialNo(), this.getArr(i).getPayRefReason());
			if(CancleFlag.equals("1")){
				dbPrpJplanFee.setToCenterCode("0");
				dbPrpJplanFee.setToComCode("0");
				dbPrpJplanFee.setToUserCode("0");
				dbPrpJplanFee.setToDesc("");
				dbPrpJplanFee.setToStatus("0");
			}else{
				dbPrpJplanFee.setToCenterCode(this.getArr(i).getToCenterCode());
				dbPrpJplanFee.setToComCode(this.getArr(i).getToComCode());
				dbPrpJplanFee.setToUserCode(this.getArr(i).getToUserCode());
				dbPrpJplanFee.setToDesc(this.getArr(i).getToDesc());
				dbPrpJplanFee.setToStatus("1");
			}
			dbPrpJplanFee.update(dbpool);
			size++;
		}
		return size;
	}
	
    /**
     * 不带dbpool的委托确认处理方法
     * @param Flag 0-委托确认，1-委托打回
     * @return 无
     */
    public int consignVerify(String Flag) throws Exception
    {
    	DbPool dbpool = new DbPool();
    	int count = 0;
        try {
        	
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            
        	dbpool.beginTransaction();
        	count = consignVerify(dbpool,Flag);
        	dbpool.commitTransaction();
	    }
        catch (Exception e)
        {
        	dbpool.rollbackTransaction();
        	throw e;
        }
        finally {
        	dbpool.close();
        }
        return count;
    }
    
    /**
	 * 带dbpool的委托确认处理方法
	 * @param dbpool 数据库连接池
     * @param Flag 0-委托确认，1-委托打回
	 * @throws UserException
	 * @throws Exception
	 */
	public int consignVerify(DbPool dbpool, String Flag) throws UserException, Exception {
		int size = 0;
		String strToStatus = "";
		if (Flag.equals("0")) {
			strToStatus = "2";
		} else {
			strToStatus = "3";
		}
		for (int i = 0; i < this.getSize(); i++) {
			DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
			dbPrpJplanFee.getInfo(dbpool, this.getArr(i).getCertiType(), this.getArr(i)
					.getCertiNo(), this.getArr(i).getSerialNo(), this.getArr(i).getPayRefReason());
			dbPrpJplanFee.setToStatus(strToStatus);
			dbPrpJplanFee.update(dbpool);
			size++;
		}
		return size;
	}

    /**
	 * 查询XXXXX别数据 张灵建 20080512
	 * @param dbpool 数据库连接池
     * @param 
	 * @throws UserException
	 * @throws Exception
	 */
	public BLPrpCitemKind findRiskKindCodeByPolicyNo(DbPool dbpool,String policyno) throws UserException, Exception {
		BLPrpCitemKind blPrpCitemKind = new BLPrpCitemKind();
		PrpCitemKindSchema prpCitemKindSchema = null;
		
		String sql = " SELECT KINDCODE,SUM(PREMIUM) AS PREMIUM FROM PRPCITEMKIND WHERE POLICYNO='"
			+ policyno + "' GROUP BY KINDCODE ";
		ResultSet resultSet = null;
		try
		{
			resultSet = dbpool.query(sql);
			while(resultSet.next())
            {
				prpCitemKindSchema = new PrpCitemKindSchema();
				prpCitemKindSchema.setKindCode(resultSet.getString("KINDCODE"));
				prpCitemKindSchema.setPremium(resultSet.getString("PREMIUM"));
				blPrpCitemKind.setArr(prpCitemKindSchema);
            }
		}catch(Exception e){
        	e.printStackTrace();
        	throw e;
        }finally {
        	if(resultSet != null)
        		resultSet.close();
        }
        return blPrpCitemKind;
	}
	
    /**
	 * 查询XXXXX别数据 张灵建 20080512
	 * @param dbpool 数据库连接池
     * @param 
	 * @throws UserException
	 * @throws Exception
	 */
	public BLPrpPitemKind findRiskKindCodeByEndorseNo(DbPool dbpool,String endorseno) throws UserException, Exception {
		BLPrpPitemKind blPrpPitemKind = new BLPrpPitemKind();
		PrpPitemKindSchema prpPitemKindSchema = null;
		
		String sql = " SELECT KINDCODE,SUM(CHGPREMIUM) AS CHGPREMIUM FROM PRPPITEMKIND WHERE ENDORSENO='"
			+ endorseno + "' GROUP BY KINDCODE ";
		ResultSet resultSet = null;
		try
		{
			resultSet = dbpool.query(sql);
			while(resultSet.next())
            {
				prpPitemKindSchema = new PrpPitemKindSchema();
				prpPitemKindSchema.setKindCode(resultSet.getString("KINDCODE"));
				prpPitemKindSchema.setChgPremium(resultSet.getString("CHGPREMIUM"));
				blPrpPitemKind.setArr(prpPitemKindSchema);
            }
		}catch(Exception e){
        	e.printStackTrace();
        	throw e;
        }finally {
        	if(resultSet != null)
        		resultSet.close();
        }
        return blPrpPitemKind;
	}
	
    /**
	 * 查询XXXXX别数据 张灵建 20080512
	 * @param dbpool 数据库连接池
     * @param 
	 * @throws UserException
	 * @throws Exception
	 */
	public BLPrpLloss findForPrpLcompensate(DbPool dbpool,String compensateno) throws UserException, Exception {
		BLPrpLloss blPrpLloss = new BLPrpLloss();
		PrpLlossSchema prpLlossSchema = null;
		
		String sql1 = " select prplloss.kindcode as kindcode,sum(prplloss.sumrealpay) as sumrealpay " +
			" from prplloss where compensateno = '" + compensateno + "' group by kindcode ";
		String sql2 = " select prplpersonloss.kindcode as kindcode,sum(prplpersonloss.sumrealpay) as sumrealpay " +
			" from prplpersonloss where compensateno = '" + compensateno + "' group by kindcode ";
		String sql = " select kindcode as kindcode,sum(sumrealpay) as sumrealpay from ((" + sql1 
			+ ") union all (" + sql2 + ")) group by kindcode ";
		ResultSet resultSet = null;
		try
		{
			resultSet = dbpool.query(sql);
			while(resultSet.next())
            {
				prpLlossSchema = new PrpLlossSchema();
				prpLlossSchema.setKindCode(resultSet.getString("KINDCODE"));
				prpLlossSchema.setSumRealPay(resultSet.getString("sumrealpay"));
				blPrpLloss.setArr(prpLlossSchema);
            }
		}catch(Exception e){
        	e.printStackTrace();
        	throw e;
        }finally {
        	if(resultSet != null)
        		resultSet.close();
        }
        return blPrpLloss;
	}
	
    /**
	 * 根据币种换算汇率
	 * @param comCode 登录机构
	 * @param currency1 基本币种
	 * @param currency2 转换币种
	 * @return String 汇率
	 * @throws UserException
	 * @throws Exception
	 */
	public String getExchRate(String comCode, String currency1, String currency2)
			throws UserException, Exception {
		String strRate = "";
		ChgDate idate = new ChgDate();
		String strEndDate = idate.getCurrentTime("yyyy-MM-dd");
		BLPrpJcommon blPrpJcommon = new BLPrpJcommon();
		blPrpJcommon.getAccCurrExch(comCode, currency1, strEndDate);
		BLAccBookCurrency blAccBookCurrency = blPrpJcommon.getBLAccBookCurrency();
		for (int j = 0; j < blAccBookCurrency.getSize(); j++) {
			if (currency1.equals("CNY")) {
				double flag = Double.parseDouble(blAccBookCurrency.getArr(j).getFlag());
				strRate = String.valueOf(new java.text.DecimalFormat("0.0000").format(flag));
			} else {
				strRate = blAccBookCurrency.getArr(j).getFlag();
			}
			if (blAccBookCurrency.getArr(j).getCurrencyCode().equals(currency2)) {
				break;
			}
		}
		return strRate;
	}
	
    public void transCode(){
    	PaymentTransCode paymentTransCode = new PaymentTransCode();
    	try {
    		paymentTransCode.transCode("prpduser","prpjplanfee",this.schemas);
    		paymentTransCode.transCode("prpdcode","prpjplanfee",this.schemas);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}

    }
    
    /**
     * 应收XX核销查询（不包含已注销XX） 
     * 
     * @param iWherePart
     * @param iOrderBy
     *            排序项
     * @param bindValues
     *            页面变量
     * @throws Exception
     * genghaijun 20110803
     */
    public void queryPremiumRefOffOptm(String iWherePart, String iOrderBy, ArrayList bindValues) throws Exception {
        String strSqlStatement = "select prpjplanfee.Currency1,prpjplanfee.comcode, "
                + " prpjplanfee.agentcode, "
                + " prpjplanfee.handler1code,sum(prpjplanfee.planfee) as planfee, "
                
                + " Sum(prpjplanfee. realpayreffee) as realpayreffee, "
                + " sum(prpjplanfee.planfee) - Sum(prpjplanfee.realpayreffee) as theRest " + " from prpjplanfee "
                + " where  " + iWherePart
                + " group by prpjplanfee.Currency1,prpjplanfee.comcode,prpjplanfee.agentcode,"
                + " prpjplanfee.handler1code " + " order by " + iOrderBy;
        this.initArr();
        DbPool dbpool = new DbPool();
        try {
            
            dbpool.open(SysConfig.getProperty("SWITCH_DATASOURCE"));
            
            PrpJplanFeeSchema prpJplanFeeSchema = null;
            ResultSet resultSet = null;
            if (bindValues != null) {
                dbpool.prepareInnerStatement(strSqlStatement);
                for (int i = 0; i < bindValues.size(); i++) {
                    dbpool.setString(i + 1, (String) bindValues.get(i));
                }
                resultSet = dbpool.executePreparedQuery();
            } else {
                resultSet = dbpool.query(strSqlStatement);
            }

            while (resultSet.next()) {
                prpJplanFeeSchema = new PrpJplanFeeSchema();
                prpJplanFeeSchema.setCurrency1(resultSet.getString("Currency1"));
                prpJplanFeeSchema.setComCode(resultSet.getString("ComCode"));
                prpJplanFeeSchema.setAgentCode(resultSet.getString("AgentCode"));
                prpJplanFeeSchema.setHandler1Code(resultSet.getString("Handler1Code"));
                prpJplanFeeSchema.setPlanFee("" + resultSet.getDouble("PlanFee"));
                prpJplanFeeSchema.setRealPayRefFee("" + resultSet.getDouble("RealPayRefFee"));
                prpJplanFeeSchema.setToDesc("" + resultSet.getDouble("theRest"));
                this.setArr(prpJplanFeeSchema);
            }
            resultSet.close();
            dbpool.close();
        } catch (SQLException sqlException) {
            dbpool.close();
            throw sqlException;
        } catch (NamingException namingException) {
            dbpool.close();
            throw namingException;
        } catch (Exception e) {
            dbpool.close();
            throw e;
        } finally {
            dbpool.close();
        }
    }
  
	/**
	 * 应收XX核销查询（核算单位递归向下查询）
	 * @param iWherePart
	 * @param iOrderBy 排序项
	 * @param bindValues 页面变量
	 * @throws Exception
	 */
    public void queryPremiumRefOffOptmForCenter(String iWherePart,String iOrderBy,ArrayList bindValues,String strCenterCode) throws Exception
	{
	  BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
	  
	  String strSqlPrpDcompany = " centerflag='1' " +
	  							 "start with comcode = '" + strCenterCode +"' " +
	  							 "connect by nocycle prior comcode = uppercomcode " +
	  							 "and prior comcode != comcode " +
	  							 "and validstatus='1' ";
	  blPrpDcompany.query(strSqlPrpDcompany,0);
	  
	  String strSqlStatement = "select prpjplanfee.Currency1,prpjplanfee.comcode, "
	  	                       + " prpjplanfee.agentcode, "
							   + " prpjplanfee.handler1code,sum(prpjplanfee.planfee) as planfee, "
							   + " Sum(prpjplanfee. realpayreffee) as realpayreffee, "
							   + " sum(prpjplanfee.planfee) - Sum(prpjplanfee.realpayreffee) as theRest "
	                           + " from prpjplanfee "
	                           + " where  " 
	                           + iWherePart;
	                           
	  this.initArr();
	  DbPool dbpool = new DbPool();
	  try {
	    dbpool.open(SysConfig.getProperty("PaymentPremiumDS"));
	    PrpJplanFeeSchema prpJplanFeeSchema = null;
	    ResultSet resultSet = null;
	    String strComCode = "";
	    String strSqlForCenterCode = "";
	    for(int j=0; j<blPrpDcompany.getSize(); j++){
	    	strComCode = blPrpDcompany.getArr(j).getComCode();
	    	strSqlForCenterCode = strSqlStatement + " and centercode='" + strComCode + "' "
	    							+ " group by prpjplanfee.Currency1,prpjplanfee.comcode,prpjplanfee.agentcode,"
	    							+ " prpjplanfee.handler1code "
	    							+ " order by " +iOrderBy;
	    	System.out.println(strSqlForCenterCode);
	    	if(bindValues != null){
		        dbpool.prepareInnerStatement(strSqlForCenterCode);
		        for (int i = 0; i < bindValues.size(); i++) {
		            dbpool.setString(i+1, (String)bindValues.get(i));
		        }
		        resultSet = dbpool.executePreparedQuery();
		    }else{
		        resultSet = dbpool.query(strSqlForCenterCode);
		    }
		
		    while(resultSet.next())
		    {
		      prpJplanFeeSchema = new PrpJplanFeeSchema();
		      prpJplanFeeSchema.setCurrency1(resultSet.getString("Currency1"));
		      prpJplanFeeSchema.setComCode(resultSet.getString("ComCode"));
		      prpJplanFeeSchema.setAgentCode(resultSet.getString("AgentCode"));
		      prpJplanFeeSchema.setHandler1Code(resultSet.getString("Handler1Code"));
		      prpJplanFeeSchema.setPlanFee(""+resultSet.getDouble("PlanFee"));
		      prpJplanFeeSchema.setRealPayRefFee(""+resultSet.getDouble("RealPayRefFee"));
		      prpJplanFeeSchema.setToDesc(""+resultSet.getDouble("theRest"));
		      this.setArr(prpJplanFeeSchema);
		    }
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
     * 发票打印校验负数发票并返回备注信息
     * @author yanglei
     * @param dblSumPlanFee 发票总金额
     * @param arrCertiNo 业务号
     * @param arrCertiType 业务类型
     * @param arrSerialNo 序号
     * @param arrPayRefReason 收付原因
     * @param strCenterCode 核算单位
     * @throws Exception
     */
    /*MODIFY BY PENGJINLING 2013-1-7 payment-520 福建发票金额校验调整 */ 
    public String checkInvoiceAndGetRemark(double dblSumPlanFee, String[] arrCertiNo, String[] arrPolicyNo, String[] arrCertiType,
            String[] arrSerialNo, String[] arrPayRefReason, String[] arrPlanFee, String strCenterCode)
    throws Exception {
        DbPool dbpool = new DbPool();
        String remark = "";
        
        try {
            dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            dbpool.beginTransaction();
            /*MODIFY BY PENGJINLING 2013-1-7 payment-520 福建发票金额校验调整 */
            remark = checkInvoiceAndGetRemark(dbpool,dblSumPlanFee,arrCertiNo, arrPolicyNo, arrCertiType,
                     arrSerialNo, arrPayRefReason, arrPlanFee, strCenterCode);
            dbpool.commitTransaction();
        }
        catch(Exception exception){
            dbpool.rollbackTransaction();
            throw exception;
        }
        finally {
            dbpool.close();
        }
        return remark;
    }
    
    /*MODIFY BY PENGJINLING 2013-1-7 payment-520 福建发票金额校验调整 BEGIN*/
    /**
     * 发票打印校验负数发票并返回备注信息
     * @author yanglei
     * @param dbpool 数据源
     * @param dblSumPlanFee 发票总金额
     * @param arrCertiNo 业务号
     * @param arrCertiType 业务类型
     * @param arrSerialNo 序号
     * @param arrPayRefReason 收付原因
     * @param strCenterCode 核算单位
     * @throws Exception
     */
    public String checkInvoiceAndGetRemark(DbPool dbpool, double dblSumPlanFee, String[] arrCertiNo, String[] arrPolicyNo, 
    		String[] arrCertiType, String[] arrSerialNo, String[] arrPayRefReason, String[] arrPlanFee, String strCenterCode)
            throws Exception {
        StringBuffer remarkBuffer = new StringBuffer();
        BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
        
        List arrInvoiceNo = new ArrayList();
        String[] bindValues = null;
        List bindValuesList =new ArrayList();
        BLPrpJvisaExportMid blPrpJvisaExportMid = new BLPrpJvisaExportMid();
        String strSql = " V6=? and fphm=?";

        
        PrpDriskConfigDto prpDriskConfigDto = UIPrpDriskConfigAction.
	        queryRiskConfig(strCenterCode, "0000", "COMCODE_INVOICE_REMARK");
	    if(prpDriskConfigDto==null){
	        throw new Exception("找不到配置项：COMCODE_INVOICE_REMARK");
	    }else if (!"1".equals(prpDriskConfigDto.getConfigValue())) {
            return "";
        }
        
        if (dblSumPlanFee > 0) {
            return "";
        } else if (dblSumPlanFee == 0) {
            throw new Exception("发票金额为0，不允许打印");
        } else {
        	
        	HashSet set = new HashSet();
        	for(int i = 0; i < arrPolicyNo.length; i++){
        		set.add(arrPolicyNo[i]);
        	}
        	
        	Map policyNoGroupMap = new HashMap();
    		Iterator it = set.iterator();
    		while(it.hasNext()){
    			String policyNo = it.next().toString();
    			double sumFee = 0D;
            	for(int i = 0; i < arrPolicyNo.length; i++){
            		if(policyNo.equals(arrPolicyNo[i])){
            			sumFee += Double.parseDouble(arrPlanFee[i]);
            			policyNoGroupMap.put(policyNo,Double.toString(sumFee));
            		}
            	}
            	
            	/*ADD BY PENGJINLING 2012-6-1 PAYMENT-471 关于产XXXXX福建机打发票系统配合地税验收后期更改的申请 BEGIN*/
            	String strOperateDate = "";
            	String strOperateDateNew = "";        	
            	/*ADD BY PENGJINLING 2012-6-1 PAYMENT-471 关于产XXXXX福建机打发票系统配合地税验收后期更改的申请 END*/
            	
            	
        		String strSumFee = (String) policyNoGroupMap.get(policyNo);
        		double sumFeePolicyNoGroup = Double.parseDouble(strSumFee);
        		if(sumFeePolicyNoGroup < 0){
        			double sumUpoadFee = 0D;
        			String printSql = " POLICYNO = ? AND PAYREFTIMES < 1000  AND VISACODE IS　NOT NULL";
                    bindValues = new String[1];
                    bindValues[0] = policyNo;
                    blPrpJpayRefRec.query(dbpool, printSql.toString(), 0, bindValues);
                    if(blPrpJpayRefRec.getSize()>0){
                    	for(int j = 0; j < blPrpJpayRefRec.getSize(); j++){
                        	/*ADD BY PENGJINLING 2012-6-1 PAYMENT-471 关于产XXXXX福建机打发票系统配合地税验收后期更改的申请 BEGIN*/
                        	if(j == 0){
                        		strOperateDate = blPrpJpayRefRec.getArr(j).getOperateDate();
                        	}
                        	else{
                            	strOperateDateNew = blPrpJpayRefRec.getArr(j).getOperateDate();
                            	int offset = DateTime.getDateInterval(new DateTime(strOperateDateNew), new DateTime(strOperateDate));
                            	if(offset > 0){
                            		strOperateDate = strOperateDateNew;
                            	}           		
                        	}
                        	/*ADD BY PENGJINLING 2012-6-1 PAYMENT-471 关于产XXXXX福建机打发票系统配合地税验收后期更改的申请 END*/                    		
                    		
                    		PrpJpayRefRecSchema prpJpayRefRecSchema = blPrpJpayRefRec.getArr(j);
                    		
                    		String visaCode = prpJpayRefRecSchema.getVisaCode();
                    		String visaSerialNo = prpJpayRefRecSchema.getVisaSerialNo();
                        	bindValuesList.clear();
                        	bindValuesList.add(visaCode);
                        	bindValuesList.add(visaSerialNo.substring(2,10));
                        	
                    		blPrpJvisaExportMid.query(dbpool, strSql, 0, bindValuesList);
                    		if(blPrpJvisaExportMid.getSize() > 0){
                    			if("01".equals(blPrpJvisaExportMid.getArr(0).getIsSynchronous())){
                    				String strPayRefFee = prpJpayRefRecSchema.getPayRefFee();
                    				sumUpoadFee += Double.parseDouble(strPayRefFee); 
                    			}
                    		}
                    		
                            
                            String invoiceNo = getInvoiceNo(prpJpayRefRecSchema.getVisaCode(), prpJpayRefRecSchema
                                    .getVisaSerialNo(),prpJpayRefRecSchema.getOperatorCode());
                            String invoiceNoAndSerialno = invoiceNo + "," + prpJpayRefRecSchema.getVisaSerialNo();
                            
                            if (!isExsist(invoiceNoAndSerialno, arrInvoiceNo)) {
                                arrInvoiceNo.add(invoiceNoAndSerialno);
                            }
                    	}
                    	
                    	/*ADD BY PENGJINLING 2012-6-1 PAYMENT-471 关于产XXXXX福建机打发票系统配合地税验收后期更改的申请 BEGIN*/
                    	String strFjFPPrintDate = SysConfig.getProperty("FJFPPRINT_SWITCH");
                        int interval = DateTime.getDateInterval(new DateTime(strOperateDate), new DateTime(strFjFPPrintDate));
                    	if(interval >= 0){
                    		return "D_" + strOperateDate;
                    	}
                    	/*ADD BY PENGJINLING 2012-6-1 PAYMENT-471 关于产XXXXX福建机打发票系统配合地税验收后期更改的申请 END*/                    	                   	
                    	
                    	double sum = sumFeePolicyNoGroup + sumUpoadFee;
                    	if(sum < 0){
                    		throw new Exception("XX号为" + policyNo + "的对应的部分发票未上传地税，不允许打印负数发票，请重新进行选择!");                    		
                    	}
                    }else{
                        throw new Exception("XX号为" + policyNo + "的负数发票没有已打印的正数发票，不允许打印负数发票，请重新进行选择!");
                    }       			        			
        		}
    		}   		   		
        }

        
        remarkBuffer.append("减（退）XXXXX或联（共）XXXXX");
        for (int i = 0; i < arrInvoiceNo.size(); i++) {
            if (i < 3) {
                String s = (String) arrInvoiceNo.get(i);
                String[] ss = s.split(",");
                if (ss.length == 2) {
                    remarkBuffer.append("发票代码");
                    remarkBuffer.append(ss[0]);
                    remarkBuffer.append("、号码");
                    if(ss[1].length()>=8){
                        ss[1] = ss[1].substring(ss[1].length()-8, ss[1].length());
                        remarkBuffer.append(ss[1]);
                    }
                }
                if (i != 2 && i != arrInvoiceNo.size()-1) {
                    remarkBuffer.append("，");
                }
            } else {
                remarkBuffer.append("等。");
                break;
            }
        }
        
        return remarkBuffer.toString();
    }
    /*MODIFY BY PENGJINLING 2013-1-7 payment-520 福建发票金额校验调整 END*/
    
    /**
     * 根据发票类型和发票号码获取发票代码
     * @author yanglei
     * @param strVisaCode 发票类型
     * @param strVisaSerialNo 发票号码
     * @return String 发票代码
     * @throws Exception
     */
    public String getInvoiceNo(String strVisaCode,String strVisaSerialNo,String strOperatorCode)throws Exception{
        UIVsStorageAction uiVsStorageAction = new UIVsStorageAction();
        List list = new ArrayList();
        Map map = new HashMap();
        map.put("visacode", strVisaCode);
        map.put("serialno", strVisaSerialNo);
        map.put("operatecode", strOperatorCode);
        list.add(map);
        
        List rtnList = uiVsStorageAction.findTaxInfoBatch(list);
        if( rtnList.size()>0) {
            map = (Map) rtnList.get(0);
        }
        return (String)map.get("invoiceno");
    }
    
    /**
     * 指定字符串是否在list中存在
     * @author yanglei
     * @param str 字符串
     * @param list list
     * @return boolean 是否存在
     * @throws Exception
     */
    public boolean isExsist(String str,List list){
        boolean flag = false;
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).equals(str)){
                flag = true;
                break;
            }
        }
        return flag;
    }
    
	
	public Collection getKinditemPremium(String sql, String strPayRefFee)
			throws Exception {
		DbPool dbpool = new DbPool();
		ArrayList List = new ArrayList();
		Collection collection = new ArrayList();
		Collection collectionIni = new ArrayList();
		PrpJplanFeeSchema prpJplanFeeSchemaIni = null;
		PrpJplanFeeSchema prpJplanFeeSchema = null;

		try {
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
			dbpool.beginTransaction();
			ResultSet resultSet = dbpool.query(sql);
			
			while (resultSet.next()) {
				prpJplanFeeSchema = new PrpJplanFeeSchema();
				prpJplanFeeSchema.setAppliName(resultSet.getString("kindname"));
				prpJplanFeeSchema.setPlanFee(resultSet.getString("premium"));
				
				prpJplanFeeSchema.setFlag3(resultSet.getString("riskcode"));
				
				collection.add(prpJplanFeeSchema);
			}
			resultSet.close();
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
		HashMap hm = new HashMap();
		int size = collection.size();
		double PlanFeeIni = 0.00;

		if (collection.size() <= 8)
		{
			int index = 1;
			for (Iterator it = collection.iterator(); it.hasNext();) {

				prpJplanFeeSchema = (PrpJplanFeeSchema) it.next();
				prpJplanFeeSchemaIni = new PrpJplanFeeSchema();
				if (index + 1 <= size)
				{
					prpJplanFeeSchemaIni.setAppliName(prpJplanFeeSchema
							.getAppliName());
					prpJplanFeeSchemaIni.setPlanFee(prpJplanFeeSchema
							.getPlanFee());
					collectionIni.add(prpJplanFeeSchemaIni);
					PlanFeeIni = PlanFeeIni
							+ Double
									.parseDouble(prpJplanFeeSchema.getPlanFee());
					
				} else {
					prpJplanFeeSchemaIni.setAppliName(prpJplanFeeSchema
							.getAppliName());
					prpJplanFeeSchemaIni.setPlanFee(String.valueOf(((Double
							.parseDouble(strPayRefFee)) - PlanFeeIni)));
					collectionIni.add(prpJplanFeeSchemaIni);
				}
				index++;
			}

		} else
		{
			int index = 1;
			for (Iterator it = collection.iterator(); it.hasNext();) {

				prpJplanFeeSchema = (PrpJplanFeeSchema) it.next();
				prpJplanFeeSchemaIni = new PrpJplanFeeSchema();
				if (index <= 7)
				{
					prpJplanFeeSchemaIni.setAppliName(prpJplanFeeSchema
							.getAppliName());
					prpJplanFeeSchemaIni.setPlanFee(prpJplanFeeSchema
							.getPlanFee());
					collectionIni.add(prpJplanFeeSchemaIni);
					PlanFeeIni = PlanFeeIni
							+ Double
									.parseDouble(prpJplanFeeSchema.getPlanFee());
					index++;
				}
			}

			prpJplanFeeSchemaIni.setAppliName("其他");
			prpJplanFeeSchemaIni.setPlanFee(String.valueOf(((Double
					.parseDouble(strPayRefFee)) - PlanFeeIni)));
			collectionIni.add(prpJplanFeeSchemaIni);

		}
		return collectionIni;
	}

	public String[] toDiffArray(String[] arrPolicyNo) {

		Set set = new HashSet();
		for (int index = 0; index < arrPolicyNo.length; index++) {
			set.add(arrPolicyNo[index]);
		}
		return (String[]) set.toArray(new String[set.size()]);
	}

	
	
	/**
     *航意XXXXX为XX查询SQL
     *@param SQLSearch 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@throws UserException
     *@throws Exception
     */
    public void queryForPolicy(String SQLSearch,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
      initArr();
      strSqlStatement = SQLSearch;
      schemas = dbPrpJplanFee.findByConditions(strSqlStatement);
    }
	
    /**
	 * 主函数
	 * @param args 参数列表
	 */
    public static void main(String[] args){
        
    }
    /**
     * 根据XX号查询收付状态（不带dbpool)
     * @param policyNo XX号
     * @return ArrayList 返回结果集
     * @author XuJingyu
     */
    public ArrayList payRefStatus(String policyNo) throws Exception{
        ArrayList arrayList = new ArrayList();
        DbPool dbpool = new DbPool();
        
        String strSwitch = BLPrpQueryPaymentService.queryMirrorSwitch();
        try {
              if ("1".equals(strSwitch)) {
                      BLPrpQueryPaymentService.querySwitch(dbpool, 2);
            }else{
                  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
           }
        
            dbpool.beginTransaction();
            arrayList = payRefStatus(dbpool,policyNo);
            dbpool.commitTransaction();
        }
        catch(Exception exception){
            dbpool.rollbackTransaction();
            throw exception;
        }
        finally {
            dbpool.close();
        }
        return arrayList;
    }

    /**
     * 根据XX号查询收付状态（带dbpool)
     * @param policyNo XX号
     * @return ArrayList 返回结果集
     * @author XuJingyu
     */
    public ArrayList payRefStatus(DbPool dbpool, String policyNo)
            throws Exception {
        ArrayList arrayList = new ArrayList();
        StringBuffer buffer = new StringBuffer(1000);
        buffer.append(" SELECT POLICYNO, PAYNO, POATYPE, SUM(PLANFEE) PLANFEE, ");
        buffer.append(" SUM(PAYREFFEE) PAYREFFEE, SUM(REALPAYREFFEE) REALPAYREFFEE ");
        buffer.append(" FROM PRPJPLANFEE WHERE POLICYNO = '");
        buffer.append(policyNo);
        buffer.append("' AND CERTITYPE = 'P' ");
        buffer.append(" AND PAYREFREASON NOT IN ( 'R72', 'R73', 'R74' ) ");
        buffer.append(" GROUP BY POLICYNO, PAYNO, POATYPE");
        buffer.append(" ORDER BY PAYNO ");
        ResultSet resultSet = null;
        String strPayNo = "";
        String strPoaType = "";
        String strPlanFee = "";
        String strPayRefFee = "";
        String strRealPayRefFee = "";
        String strPayRefStatus = "";
        try {
            resultSet = dbpool.query(buffer.toString());
            while (resultSet.next()) {
                HashMap hm = new HashMap();
                strPayNo = resultSet.getString("PAYNO");
                strPoaType = resultSet.getString("POATYPE");
                strPlanFee = resultSet.getString("PLANFEE");
                strPayRefFee = resultSet.getString("PAYREFFEE");
                strRealPayRefFee = resultSet.getString("REALPAYREFFEE");
                if(strRealPayRefFee == null || strPayRefFee == null){
                    throw new UserException(-98, -1167,
                            "BLPrpJplanFee.payRefStatus", "数据异常！");
                }
                if (0 != Double.parseDouble(strRealPayRefFee)) {
                    strPayRefStatus = "3";
                } else if (0 != Double.parseDouble(strPayRefFee)) {
                    strPayRefStatus = "2";
                } else if (strPoaType!=null&&!"0".equals(strPoaType)) {
                    strPayRefStatus = "1";
                } else {
                    strPayRefStatus = "0";
                }
                hm.put("PolicyNo", policyNo);
                hm.put("Amount", strPlanFee);
                hm.put("PayNo", strPayNo);
                hm.put("PayRefStatus", strPayRefStatus);
                arrayList.add(hm);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (resultSet != null)
                resultSet.close();
        }
        return arrayList;
    }
    
    
    public void getNewInfo(DbPool dbpool,String certiType,String certiNo,String serialNo,String payRefReason) throws Exception{
        
        try {
        	ArrayList bindValues = new ArrayList();
        	bindValues.add(certiType);
        	bindValues.add(certiNo);
        	bindValues.add(serialNo);
        	bindValues.add(payRefReason);
        	this.query(dbpool," CertiType = ? And CertiNo = ? And SerialNo = ? And PayRefReason = ?  ", bindValues);
        }
        catch(Exception exception){
            throw exception;
        }
    }
    public void getNewInfo(String certiType,String certiNo,String serialNo,String payRefReason) throws Exception{
        
        try {
        	ArrayList bindValues = new ArrayList();
        	bindValues.add(certiType);
        	bindValues.add(certiNo);
        	bindValues.add(serialNo);
        	bindValues.add(payRefReason);
        	this.query(" CertiType = ? And CertiNo = ? And SerialNo = ? And PayRefReason = ?  ", bindValues);
        }
        catch(Exception exception){
            throw exception;
        }
    }
    
    public void query(String iWherePart,ArrayList bindValues) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
        try {
        	dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
	        this.query(dbpool,iWherePart,bindValues);
	      }
	      catch (Exception e)
	      {
	        throw e;
	      }
	      finally {
	        dbpool.close();
	      }
    }
    public void query(DbPool dbpool,String iWherePart,ArrayList bindValues) throws UserException,Exception
    {
		String strSqlStatement = "";
		DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();
		initArr();
		strSqlStatement = " SELECT * FROM PrpJplanFee WHERE " + iWherePart;
		schemas = dbPrpJplanFee.findByConditions(dbpool, strSqlStatement, bindValues);
    }
    
    
    
    
}


