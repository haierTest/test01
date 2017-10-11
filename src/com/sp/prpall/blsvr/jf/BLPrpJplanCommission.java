package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.account.blsvr.BLAccBookBranch;
import com.sp.account.blsvr.BLAccMainVoucher;
import com.sp.account.blsvr.BLAccSubVoucher;
import com.sp.account.blsvr.BLAccVoucher;
import com.sp.account.schema.AccMainVoucherSchema;
import com.sp.account.schema.AccSubVoucherSchema;
import com.sp.prpall.blsvr.cb.BLPrpCitemKind;
import com.sp.prpall.blsvr.misc.BLPrpCommission;
import com.sp.prpall.dbsvr.cb.DBPrpCitemCar;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.jf.DBPrpJpayCommission;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefMain;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefRec;
import com.sp.prpall.dbsvr.jf.DBPrpJplanCommission;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFee;
import com.sp.prpall.dbsvr.misc.DBPrpCommission;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpJpayCommissionSchema;
import com.sp.prpall.schema.PrpJpayRefRecSchema;
import com.sp.prpall.schema.PrpJplanCommissionSchema;
import com.sp.prpall.schema.PrpJplanFeeSchema;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDagent;
import com.sp.utiall.blsvr.BLPrpDcompany;
import com.sp.utiall.blsvr.BLPrpDuser;
import com.sp.utiall.dbsvr.DBPrpDagent;
import com.sp.utiall.dbsvr.DBPrpDcode;
import com.sp.utiall.dbsvr.DBPrpDuser;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

public class BLPrpJplanCommission {

	private Vector schemas = new Vector();
	private BLAccMainVoucher blAccMainVoucher = new BLAccMainVoucher();
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * 构造函数
	 */
	public BLPrpJplanCommission() {
		
	}

	/**
	 * 初始化记录
	 * 
	 * @param 无
	 * @return 无
	 * @throws Exception
	 */
	public void initArr() throws Exception {
		schemas = new Vector();
	}

	/**
	 * 增加一条PrpJplanCommissionSchema记录
	 * 
	 * @param iPrpJplanCommissionSchema
	 *            PrpJplanCommissionSchema
	 * @throws Exception
	 */
	public void setArr(PrpJplanCommissionSchema iPrpJplanCommissionSchema)
			throws Exception {
		try {
			schemas.add(iPrpJplanCommissionSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到一条PrpJplanCommissionSchema记录
	 * 
	 * @param index
	 *            下标
	 * @return 一个PrpJplanFeeSchema对象
	 * @throws Exception
	 */
	public PrpJplanCommissionSchema getArr(int index) throws Exception {
		PrpJplanCommissionSchema prpJplanCommissionSchema = null;
		try {
			prpJplanCommissionSchema = (PrpJplanCommissionSchema) this.schemas
					.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJplanCommissionSchema;
	}

	/**
	 * 得到schemas记录数
	 * 
	 * @return schemas记录数
	 * @throws Exception
	 */
	public int getSize() throws Exception {
		return this.schemas.size();
	}

	/**
	 * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool
	 *            全局池
	 * @param iWherePart
	 *            查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart) throws UserException, Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			this.query(dbpool, iWherePart);
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
	}

	/**
	 * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool
	 *            全局池
	 * @param iWherePart
	 *            查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart) throws UserException,
			Exception {
		this.query(dbpool, iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * 按照查询条件得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool
	 *            全局池
	 * @param iWherePart
	 *            查询条件(包括排序字句)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart, int iLimitCount) throws UserException,
			Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			this.query(dbpool, iWherePart, iLimitCount);
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
	}

	/**
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool
	 *            全局池
	 * @param iWherePart
	 *            查询条件(包括排序字句)
	 * @param iLimitCount
	 *            记录数限制(iLimitCount=0: 无限制)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, int iLimitCount)
			throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpJplanCommission dbPrpJplanCommission = new DBPrpJplanCommission();
		if (iLimitCount > 0
				&& dbPrpJplanCommission.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJplanCommission.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJplanCommission WHERE "
					+ iWherePart;
			schemas = dbPrpJplanCommission.findByConditions(dbpool,
					strSqlStatement);
		}
	}
	
	/**
	 * 按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
	 * 
	 * @param dbpool
	 *            全局池
	 * @param iWherePart
	 *            查询条件(包括排序字句)
	 * @param iLimitCount
	 *            记录数限制(iLimitCount=0: 无限制)
	 * @throws UserException
	 * @throws Exception
	 */
	public void queryByLimitCount(String iWherePart)
			throws UserException, Exception {
		String strSqlStatement = "";
		int count = Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim());
		DBPrpJplanCommission dbPrpJplanCommission = new DBPrpJplanCommission();
		initArr();
		String sql = " SELECT * FROM PrpJplanCommission WHERE "
					+ iWherePart;
		strSqlStatement = " SELECT * FROM ( " + sql + " ) WHERE ROWNUM<=" + count;
        
		schemas = dbPrpJplanCommission.findByConditions(strSqlStatement);
	}

	/**
	 * 带dbpool的save方法
	 * 
	 * @param 无
	 * @return 无
	 */
	public void save(DbPool dbpool) throws Exception {
		DBPrpJplanCommission dbPrpJplanCommission = new DBPrpJplanCommission();
		int i = 0;
		for (i = 0; i < schemas.size(); i++) {
			dbPrpJplanCommission.setSchema((PrpJplanCommissionSchema) schemas
					.get(i));
			dbPrpJplanCommission.insert(dbpool);
		}
	}
    /**
     * @param blPrpJplanCommission
     * @param isChinese
     * @return blPrpJplanCommission
     * @throws Exception
     */
    public void evaluate(PrpJplanFeeSchema iSchema,PrpJplanCommissionSchema prpJplanCommissionSchema,String strType) throws Exception{
        DbPool dbpool = new DbPool();
        String strDataSource =SysConfig.CONST_DDCCDATASOURCE;

        try{
            
            dbpool.open(strDataSource);
            this.evaluate(dbpool,iSchema,prpJplanCommissionSchema,strType);
        }
        catch(Exception exception){
            throw exception;
        }
        finally{
            dbpool.close();
        }
    }	
	/**   根据blPrpJplanFee生成blPrpJplanCommission
	 * @desc 
	 * @param blPrpJplanFee
	 *            
	 * @param blPrpJplanCommission
	 *            
	 * @param strType
	 *            代码转换类型
	 *            0：根据代码直接查询数据库  1：先查询代码和名称放到HashMap中，然后用代码去查询
	 * @throws Exception
	 */
    public void evaluate(DbPool dbpool,PrpJplanFeeSchema iSchema,PrpJplanCommissionSchema prpJplanCommissionSchema,String strType) throws Exception
    {
		if (strType == null || strType.equals(""))
			strType = "0";
		prpJplanCommissionSchema.setCertiType(iSchema.getCertiType());
		prpJplanCommissionSchema.setCertiNo(iSchema.getCertiNo());
		prpJplanCommissionSchema.setPolicyNo(iSchema.getPolicyNo());
		prpJplanCommissionSchema.setSerialNo(iSchema.getSerialNo());
		prpJplanCommissionSchema.setPayRefReason(iSchema.getPayRefReason());
		prpJplanCommissionSchema.setClassCode(iSchema.getClassCode());
		prpJplanCommissionSchema.setRiskCode(iSchema.getRiskCode());
		prpJplanCommissionSchema.setAppliCode(iSchema.getAppliCode());
		prpJplanCommissionSchema.setAppliName(iSchema.getAppliName());
		prpJplanCommissionSchema.setInsuredCode(iSchema.getInsuredCode());
		prpJplanCommissionSchema.setInsuredName(iSchema.getInsuredName());
		prpJplanCommissionSchema.setStartDate(iSchema.getStartDate());
		prpJplanCommissionSchema.setEndDate(iSchema.getEndDate());
		prpJplanCommissionSchema.setValidDate(iSchema.getValidDate());
		prpJplanCommissionSchema.setPayNo(iSchema.getPayNo());
		prpJplanCommissionSchema.setCurrency1(iSchema.getCurrency1());
		prpJplanCommissionSchema.setPlanFee(iSchema.getPlanFee());
		prpJplanCommissionSchema.setPlanDate(iSchema.getPlanDate());
		prpJplanCommissionSchema.setComCode(iSchema.getComCode());
		prpJplanCommissionSchema.setMakeCom(iSchema.getMakeCom());
		prpJplanCommissionSchema.setAgentCode(iSchema.getAgentCode());
		prpJplanCommissionSchema.setAgentName("");
		if (strType.equals("0")) {
			String strAgentName = translateCode(dbpool,
					prpJplanCommissionSchema.getAgentCode(), true, 1);
			if (strAgentName.equals(""))
				throw new UserException(-98, -1167,
						"blPrpJplanCommission.evaluate", "prpdagent表中没有配置"
								+ prpJplanCommissionSchema.getAgentCode()
								+ "对应代理人数据");
			prpJplanCommissionSchema.setAgentName(strAgentName);
		}
		prpJplanCommissionSchema.setHandler1Code(iSchema.getHandler1Code());
		prpJplanCommissionSchema.setHandler1Name("");
		if (strType.equals("0")) {
			String strHandler1Name = translateCode(dbpool,
					prpJplanCommissionSchema.getHandler1Code(), true, 2);
			if (strHandler1Name.equals(""))
				throw new UserException(-98, -1167,
						"blPrpJplanCommission.evaluate", "prpduser表中没有配置"
								+ prpJplanCommissionSchema.getHandler1Code()
								+ "对应业务员数据");
			prpJplanCommissionSchema.setHandler1Name(strHandler1Name);
		}
		prpJplanCommissionSchema.setHandlerCode(iSchema.getHandlerCode());
		prpJplanCommissionSchema.setUnderWriteDate(iSchema.getUnderWriteDate());
		prpJplanCommissionSchema.setCoinsFlag(iSchema.getCoinsFlag());
		prpJplanCommissionSchema.setCoinsCode(iSchema.getCoinsCode());
		prpJplanCommissionSchema.setCoinsName(iSchema.getCoinsName());
		prpJplanCommissionSchema.setCoinsType(iSchema.getCoinsType());
		prpJplanCommissionSchema.setCenterCode(iSchema.getCenterCode());
		prpJplanCommissionSchema.setBranchCode(iSchema.getBranchCode());
		prpJplanCommissionSchema.setAccBookType(iSchema.getAccBookType());
		prpJplanCommissionSchema.setAccBookCode(iSchema.getAccBookCode());
		prpJplanCommissionSchema.setYearMonth(iSchema.getYearMonth());
		prpJplanCommissionSchema.setVoucherNo(iSchema.getVoucherNo());
		prpJplanCommissionSchema.setExchangeRate(iSchema.getExchangeRate());
		prpJplanCommissionSchema.setPlanFeeCNY(iSchema.getPlanFeeCNY());
		prpJplanCommissionSchema.setPayRefFee(iSchema.getPayRefFee());
		prpJplanCommissionSchema.setRealPayRefFee(iSchema.getRealPayRefFee());
		prpJplanCommissionSchema.setCarNatureCode(iSchema.getCarNatureCode());
		prpJplanCommissionSchema.setUseNatureCode(iSchema.getUseNatureCode());
		prpJplanCommissionSchema.setCarProperty(iSchema.getCarProperty());
		prpJplanCommissionSchema.setBusinessNature(iSchema.getBusinessNature());
		prpJplanCommissionSchema.setOperateSequence(iSchema	.getOperateSequence());
		
		if (iSchema.getFlag().substring(2, 3).equals("1")) {
			prpJplanCommissionSchema.setMainRiskCode(iSchema.getFlag1());
		}
		
		prpJplanCommissionSchema.setWriteOffFlag(iSchema.getFlag().substring(0,	1));
		
		prpJplanCommissionSchema.setAllinsFlag(iSchema.getFlag().substring(1, 2));
		
		prpJplanCommissionSchema.setMainPolicyFlag(iSchema.getFlag().substring(2, 3));
		
		prpJplanCommissionSchema.setCarNatureCode(iSchema.getCarNatureCode());
		
    }
    /**
	 * @param blPrpJplanCommission
	 * @param isChinese
	 * @return blPrpJplanCommission
	 * @throws Exception
	 */
    public void translateCode(boolean isChinese) throws Exception{
        DbPool dbpool = new DbPool();
        String strDataSource =SysConfig.CONST_DDCCDATASOURCE;

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
     * @param blPrpJplanCommission
     * @param isChinese
     * @return blPrpJplanCommission
     * @throws Exception
     */
    public void translateCode(DbPool dbpool,boolean isChinese) throws Exception{
        for(int i = 0;i < this.getSize();i++)
        {
        	
        	this.getArr(i).setAgentName(this.translateCode(dbpool,
        			this.getArr(i).getAgentCode(),isChinese,1));
        	
        	this.getArr(i).setHandler1Name(this.translateCode(dbpool,
        			this.getArr(i).getHandler1Code(),isChinese,2));
        }
    }    
    /**
     * @param blPrpJplanCommission
     * @param isChinese
     * @return blPrpJplanCommission
     * @throws Exception
     */
    public String translateCode(String code,boolean isChinese,int type) throws Exception{
        DbPool dbpool = new DbPool();
        String strName="";
        String strDataSource =SysConfig.CONST_DDCCDATASOURCE;

        try{
            
            dbpool.open(strDataSource);
            strName=this.translateCode(dbpool,code,isChinese,type);
        }
        catch(Exception exception){
            throw exception;
        }
        finally{
            dbpool.close();
        }
        return strName;
    }    
    /**
     * @param dbpool
     * @param code
     * @param isChinese
     * @param type
     *    1: 代理人 2：业务员 3：收付原因
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
     * @author 张丽娜 20080110 转手续费
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
      if(dblDisFee==0)
    	  return;
      double dblLeftFee = dblDisFee;
      double dblPlanFee = 0;
      
      double dblSumPremium = 0;
      for(int i=0; i<blPrpJplanFee.getSize(); i++)
      {
        dblSumPremium += Double.parseDouble(blPrpJplanFee.getArr(i).getPlanFee());
      }
      BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
      PrpJplanCommissionSchema prpJplanCommissionSchema =null;
      for(int i=0; i<blPrpJplanFee.getSize(); i++)
      {
    	prpJplanCommissionSchema = new PrpJplanCommissionSchema();
        this.evaluate(dbpool,blPrpJplanFee.getArr(i),prpJplanCommissionSchema,"0");
        prpJplanCommissionSchema.setCertiType("S");
        prpJplanCommissionSchema.setPayRefReason("P90");
        prpJplanCommissionSchema.setCurrency1(blPrpCommission.getArr(0).getCurrency());
        if(i==blPrpJplanFee.getSize()-1)
          dblPlanFee = dblLeftFee;
        else
          dblPlanFee = dblDisFee * (Double.parseDouble(blPrpJplanFee.getArr(i).getPlanFee())/dblSumPremium);
        dblPlanFee = Str.round(Str.round(dblPlanFee,8),2);
        dblLeftFee -= dblPlanFee;
        prpJplanCommissionSchema.setPlanFee(""+dblPlanFee);
        prpJplanCommissionSchema.setPayFlag("0");
        prpJplanCommissionSchema.setPoliPayRefDate("");
        prpJplanCommissionSchema.setDisRate(blPrpCommission.getArr(0).getDisRate());
        prpJplanCommissionSchema.setRealRefPremium("0");
        blPrpJplanCommission.setArr(prpJplanCommissionSchema);
      }
      blPrpJplanCommission.save(dbpool);
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
      if(dblDisFee==0)
    	  return;
      double dblLeftFee = dblDisFee;
      double dblPlanFee = 0;
      
      double dblSumPremium = 0;
      for(int i=0; i<blPrpJplanFee.getSize(); i++)
      {
        dblSumPremium += Double.parseDouble(blPrpJplanFee.getArr(i).getPlanFee());
      }
      PrpJplanCommissionSchema schema = null;
      for(int i=0; i<blPrpJplanFee.getSize(); i++)
      {
        schema = new PrpJplanCommissionSchema();
        this.evaluate(dbpool,blPrpJplanFee.getArr(i),schema,"0");

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
        if(dblPlanFee < 0 && !"19".equals(dbPrpPhead.getEndorType()))
        {
        	schema.setPayFlag("1");
        }else
        {
        	schema.setPayFlag("0");
        }
        schema.setPoliPayRefDate("");
        schema.setDisRate(blPrpCommission.getArr(0).getDisRate());
        schema.setRealRefPremium("0");
        this.setArr(schema);
      }
      if(blPrpJplanFee.getSize()==0)
      {
        schema = new PrpJplanCommissionSchema();
        schema.setCertiType("S");
        
        schema.setCertiNo(dbPrpPhead.getEndorseNo());
        schema.setSerialNo("1");
        schema.setPolicyNo(dbPrpPhead.getPolicyNo());
        
        if(dbPrpPhead.getEndorType().indexOf("57")>=0){
            schema.setPayRefReason("P93"); 
        }else{
            schema.setPayRefReason("P90");
            schema.setPoliPayRefDate("");
            schema.setPayFlag("0");
        }
        schema.setClassCode(dbPrpCmain.getClassCode());
        schema.setRiskCode(dbPrpCmain.getRiskCode());
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
        schema.setAgentName("");
        
        String strAgentName = translateCode(dbpool,schema.getAgentCode(), true, 1);
	    if (strAgentName.equals(""))
				throw new UserException(-98, -1167, "blPrpJplanCommission.evaluate",
						"prpdagent表中没有配置"+schema.getAgentCode()+"对应代理人数据" );  
  	    schema.setAgentName(strAgentName);
        schema.setHandler1Code(dbPrpCmain.getHandler1Code());
        schema.setHandler1Name("");
        
        String strHandler1Name = translateCode(dbpool,schema.getHandler1Code(), true, 2);
	    if (strHandler1Name.equals(""))
				throw new UserException(-98, -1167, "blPrpJplanCommission.evaluate",
						"prpduser表中没有配置"+schema.getHandler1Code()+"对应业务员数据" );  
  	    schema.setHandler1Name(strHandler1Name);
        schema.setHandlerCode(dbPrpCmain.getHandlerCode());
        schema.setUnderWriteDate(dbPrpPhead.getUnderWriteEndDate());
        schema.setCoinsFlag(dbPrpCmain.getCoinsFlag());
        schema.setCoinsCode("");
        schema.setCoinsName("");
        schema.setCoinsType("");
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
        schema.setRealPayRefFee("0.00");
        schema.setBusinessNature(dbPrpCmain.getBusinessNature());
        
  		if("19".equals(dbPrpPhead.getEndorType())){
  			schema.setWriteOffFlag("1");
  		}else{
  			schema.setWriteOffFlag("0");
  		}
  		
        if("1".equals(dbPrpCmain.getAllinsFlag())){
          	schema.setAllinsFlag("1");
        }else{
          	schema.setAllinsFlag("0");
        }
        
        if(iRiskCode != null && iRiskCode != "" && iRiskCode.substring(0,2).equals("30")){
        	schema.setMainPolicyFlag("1");
        	schema.setMainRiskCode(iRiskCode);
        }else{
        	schema.setMainPolicyFlag("0");
        	schema.setMainRiskCode("");
        }
        
        if(dbPrpCmain.getRiskCode().equals("0507")){
            this.getCarNature(dbpool,schema);
        }
        
        schema.setDisRate(blPrpCommission.getArr(0).getDisRate());
        schema.setRealRefPremium("0.00");
        if (schema.getPayRefReason().equals("P93")){
            this.transCommissionControl(dbpool,schema);
        }
        
        
        int count = 1;
        double dbSumPremium = 0;
        double riskKindCodeFee = 0;
        BLPrpCitemKind blPrpCitemKind = new BLPrpCitemKind();
        BLPrpJplanFee blPrpJplanFeeTmp = new BLPrpJplanFee();
        DBPrpDcode dbPrpDcode = new DBPrpDcode();
        String splitFlag = "0";
        if(SysConfig.getProperty("SplitRisk").indexOf(dbPrpCmain.getRiskCode()) >=0 )
        {
      	  splitFlag = "1";
        }
        if(splitFlag.equals("1"))
        {
        	blPrpCitemKind = blPrpJplanFeeTmp.findRiskKindCodeByPolicyNo(dbpool,dbPrpCmain.getPolicyNo());
        	for(int j=0; j<blPrpCitemKind.getSize(); j++)
            {
        		dbSumPremium += Str.round(Double.parseDouble(blPrpCitemKind.getArr(j).getPremium()),2);
            }
        	for(int j=0; j<blPrpCitemKind.getSize(); j++)
            {
        		PrpJplanCommissionSchema schemaRiskKindCode = new PrpJplanCommissionSchema();
				schemaRiskKindCode.setSchema(schema);
				schemaRiskKindCode.setSerialNo(""+count);
				count++;
				int result = dbPrpDcode.getInfo(dbpool,"RiskArticle",dbPrpCmain.getRiskCode()+blPrpCitemKind.getArr(j).getKindCode());
				if(result == 100)
				{
					throw new UserException( -98, -1167, "BLPrpJplanFee.transPolicy", "XXXXX别没有对应XXXXX核算专项");
				}
				String flag1 = dbPrpDcode.getNewCodeCode();
				schemaRiskKindCode.setCarNatureCode(flag1);
				schemaRiskKindCode.setPayNo("1");
				double planFee = 0;
				planFee = Str.round(dblDisFee*Double.parseDouble(blPrpCitemKind.getArr(j).getPremium())
					/dbSumPremium,2);
				if(j == blPrpCitemKind.getSize()-1)
				{
					schemaRiskKindCode.setPlanFee(""+(dblDisFee - riskKindCodeFee));
				}else
				{
					schemaRiskKindCode.setPlanFee(""+planFee);
					riskKindCodeFee = riskKindCodeFee + planFee;
				}
				this.setArr(schemaRiskKindCode);
            }
        }else
        {
        	this.setArr(schema);
        }
        
      }
      this.save(dbpool);
    }
    
    /**
     * 为车辆种类等字段赋值 SangMingqian 20060726
     * @param iSchema planfeeSchema
     * @throws Exception
     */
    public void getCarNature(DbPool dbpool,PrpJplanCommissionSchema iSchema) throws Exception,UserException{
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
     * @desc 自动转入XXXXX的应付手续费凭证
     * @param dbpool
     * @param iAccBookType 帐套类型
     * @param iAccBookCode 帐套代码
     * @param iCenterCode 核算单位
     * @param iBranchCode 基层单位
     * @param iDate 截至日期
     * @param iOperatorCode 操作员代码
     * @throws Exception
     */
    public void transAccountS(DbPool dbpool, String iAccBookType,
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
        StringBuffer SQLBuffer = new StringBuffer();
        DBPrpJplanCommission dbPrpJplanCommission = new DBPrpJplanCommission();
        AccMainVoucherSchema accMainVoucherSchema = null;
        
        String strYearMonth = iDate.substring(0,4)+iDate.substring(5,7);   
        for(int i=0; i<blAccBookBranch.getSize(); i++){
        	
        	accMainVoucherSchema = new AccMainVoucherSchema();
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
            accMainVoucherSchema.setYearMonth(strYearMonth);
            
            BLAccVoucher blAccVoucher = new BLAccVoucher();
            blAccVoucher.getBLAccMainVoucher().setArr(accMainVoucherSchema);
            
            
            SQLBuffer.append(" CertiType ='S' And CenterCode = '"+iCenterCode+"'  ");
            SQLBuffer.append(" And VoucherNo='0' And PayFlag= '1'                 ");

        	this.logPrintln("kai shi cha xun ying fu shou xu fei ping zheng！");
            this.initArr();
            this.query(dbpool, SQLBuffer.toString(), 0);
        	this.logPrintln("ying fu shou xu fei shu ju tiao shu=="+this.getSize());
            
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
            
            
            if(this.getSize()==0){
            	this.logPrintln("mei you yao zhuan ru ying fu shou xu fei de shu ju！");
            	return;
            }
        	this.logPrintln("ying fu shou xu fei shu ju huo qu dui huan lv jie shu");
            
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
              this.logPrintln("kai shi bao cun ping zheng ");
              blAccVoucher.save(dbpool);
              
              this.blAccMainVoucher.setArr(blAccVoucher.getBLAccMainVoucher().getArr(0));
              strVoucherNo = blAccVoucher.getBLAccMainVoucher().getArr(0).getVoucherNo();
              this.logPrintln("sheng cheng ying fu shou xu fei ping zheng  "+ accMainVoucherSchema.getCenterCode()+" "+strVoucherNo);
            }
            else{
              this.logPrintln("mei you yao zhuan ru ying fu shou xu fei de shu ju！");
            }
            
            for(int j=0; j<this.getSize(); j++){
              dbPrpJplanCommission.setSchema(this.getArr(j));
              dbPrpJplanCommission.setAccBookCode(accMainVoucherSchema.getAccBookCode());
              dbPrpJplanCommission.setAccBookType(accMainVoucherSchema.getAccBookType());
              dbPrpJplanCommission.setCenterCode(accMainVoucherSchema.getCenterCode());
              dbPrpJplanCommission.setBranchCode(accMainVoucherSchema.getBranchCode());
              dbPrpJplanCommission.setYearMonth(accMainVoucherSchema.getYearMonth());
              dbPrpJplanCommission.setVoucherNo(strVoucherNo);
              dbPrpJplanCommission.update(dbpool);
            }
        }       
        this.logPrintln("hui xie ying shou ying fu biao zhong de ping zheng xin xi wan cheng");
    }
	
    /**
     * @desc 生成手续费凭证子表信息 
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
          if (j == 0) { 
            if(this.getArr(i).getCertiType().equals("S")){
              accSubVoucherSchema.setItemCode("6421"); 
            }
            accSubVoucherSchema.setDebitSource(this.getArr(i).getPlanFee());
            accSubVoucherSchema.setExchangeRate(this.getArr(i).getExchangeRate());
            accSubVoucherSchema.setDebitDest(this.getArr(i).getPlanFeeCNY());
          }
          else { 
              if(this.getArr(i).getCertiType().equals("S")){
                  accSubVoucherSchema.setItemCode("2202"); 
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
    }
	
    /**
     * 投联XXXXX手续费处理  
     * @param dbpool 
     * @param blPrpJplanFee BLPrpJplanFee 对象
     * @param strPayRefDate XXXXX、批单实收日期
     */
    public void transInvestCommission(DbPool dbpool, BLPrpJplanFee blPrpJplanFee, String strPayRefDate) throws Exception {
		BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
		BLPrpJinvest blPrpJinvest = new BLPrpJinvest();
		DBPrpCommission dbPrpCommission = null;
		PrpJplanFeeSchema prpJplanFeeSchema = null;
		
		PrpJplanCommissionSchema prpJplanCommissionSchema = null;
		
		StringBuffer  stringBuffer = new StringBuffer();

		for (int i = 0; i < blPrpJplanFee.getSize(); i++) {
			dbPrpCommission = new DBPrpCommission();
			prpJplanFeeSchema = blPrpJplanFee.getArr(i);
			prpJplanCommissionSchema = new PrpJplanCommissionSchema();	
			
			int intReturn = dbPrpCommission.getInfo(dbpool, prpJplanFeeSchema.getCertiNo());
			if (intReturn == 100)
				continue;
			double dblPlanFee = 0;
			if (dbPrpCommission.getDisFee() != null
					&& !dbPrpCommission.getDisFee().equals("")) {
				dblPlanFee = Double.parseDouble(dbPrpCommission.getDisFee());
			} else {
				continue;
			}
			dblPlanFee = Str.round(Str.round(dblPlanFee, 8), 2);
			if(dblPlanFee==0)
				continue;

			prpJplanCommissionSchema.setPlanFee("" + dblPlanFee);

			prpJplanCommissionSchema.setCertiType("S");
			prpJplanCommissionSchema.setCertiNo(prpJplanFeeSchema.getCertiNo());
			prpJplanCommissionSchema.setPolicyNo(prpJplanFeeSchema.getPolicyNo());
			prpJplanCommissionSchema.setSerialNo(prpJplanFeeSchema.getSerialNo());
			prpJplanCommissionSchema.setPayRefReason("P90");
			prpJplanCommissionSchema.setClassCode(prpJplanFeeSchema.getClassCode());
			prpJplanCommissionSchema.setRiskCode(prpJplanFeeSchema.getRiskCode());
			prpJplanCommissionSchema.setAppliCode(prpJplanFeeSchema.getAppliCode());
			prpJplanCommissionSchema.setAppliName(prpJplanFeeSchema.getAppliName());
			prpJplanCommissionSchema.setInsuredCode(prpJplanFeeSchema.getInsuredCode());
			prpJplanCommissionSchema.setInsuredName(prpJplanFeeSchema.getInsuredName());
			prpJplanCommissionSchema.setStartDate(prpJplanFeeSchema.getStartDate());
			prpJplanCommissionSchema.setEndDate(prpJplanFeeSchema.getEndDate());
			prpJplanCommissionSchema.setValidDate(prpJplanFeeSchema.getValidDate());
			prpJplanCommissionSchema.setPayNo(prpJplanFeeSchema.getPayNo());
			prpJplanCommissionSchema.setCurrency1(dbPrpCommission.getCurrency());
			prpJplanCommissionSchema.setPlanDate(prpJplanFeeSchema.getPlanDate());
			prpJplanCommissionSchema.setComCode(prpJplanFeeSchema.getComCode());
			prpJplanCommissionSchema.setMakeCom(prpJplanFeeSchema.getMakeCom());
			prpJplanCommissionSchema.setAgentCode(prpJplanFeeSchema.getAgentCode());
			prpJplanCommissionSchema.setAgentName("");
			String strAgentName = this.translateCode(dbpool,prpJplanCommissionSchema.getAgentCode(), true, 1);
			if (strAgentName.equals(""))
				throw new UserException(-98, -1167,
						"blPrpJplanCommission.evaluate", "prpdagent表中没有配置"
								+ prpJplanCommissionSchema.getAgentCode()
								+ "对应代理人数据");
			prpJplanCommissionSchema.setAgentName(strAgentName);
			prpJplanCommissionSchema.setHandler1Code(prpJplanFeeSchema.getHandler1Code());
			prpJplanCommissionSchema.setHandler1Name("");
			String strHandler1Name = this.translateCode(dbpool,prpJplanCommissionSchema.getHandler1Code(), true, 2);
			if (strHandler1Name.equals(""))
				throw new UserException(-98, -1167,
						"blPrpJplanCommission.evaluate", "prpduser表中没有配置"
								+ prpJplanCommissionSchema.getHandler1Code()
								+ "对应业务员数据");
			prpJplanCommissionSchema.setHandler1Name(strHandler1Name);
			prpJplanCommissionSchema.setHandlerCode(prpJplanFeeSchema.getHandlerCode());
			prpJplanCommissionSchema.setUnderWriteDate(prpJplanFeeSchema.getUnderWriteDate());
			prpJplanCommissionSchema.setCoinsFlag(prpJplanFeeSchema.getCoinsFlag());
			prpJplanCommissionSchema.setCoinsCode("");
			prpJplanCommissionSchema.setCoinsName("");
			prpJplanCommissionSchema.setCoinsType("");
			prpJplanCommissionSchema.setCenterCode(prpJplanFeeSchema.getCenterCode());
			prpJplanCommissionSchema.setBranchCode("");
			prpJplanCommissionSchema.setAccBookType("");
			prpJplanCommissionSchema.setAccBookCode("");
			prpJplanCommissionSchema.setYearMonth("");
			prpJplanCommissionSchema.setVoucherNo("0");
			prpJplanCommissionSchema.setExchangeRate(prpJplanFeeSchema.getExchangeRate());
			prpJplanCommissionSchema.setPlanFeeCNY("0.00");
			prpJplanCommissionSchema.setPayRefFee("0.00");
			prpJplanCommissionSchema.setRealPayRefFee("0.00");
			prpJplanCommissionSchema.setCarNatureCode("");
			prpJplanCommissionSchema.setUseNatureCode("");
			prpJplanCommissionSchema.setCarProperty("");
			prpJplanCommissionSchema.setBusinessNature(prpJplanFeeSchema.getBusinessNature());
			prpJplanCommissionSchema.setOperateSequence("");
			prpJplanCommissionSchema.setFlag("");
			
			prpJplanCommissionSchema.setMainRiskCode("");
			
			prpJplanCommissionSchema.setWriteOffFlag("0");
			
			prpJplanCommissionSchema.setAllinsFlag(prpJplanFeeSchema.getFlag().substring(1, 2));
			
			prpJplanCommissionSchema.setMainPolicyFlag("0");
			prpJplanCommissionSchema.setPayFlag("1");
			prpJplanCommissionSchema.setPoliPayRefDate(strPayRefDate);
			prpJplanCommissionSchema.setDisRate(dbPrpCommission.getDisRate());
			blPrpJplanCommission.setArr(prpJplanCommissionSchema);
			
			stringBuffer = new StringBuffer();
			stringBuffer.append(" PayRefReason = 'R01'");
			stringBuffer.append(" AND SerialNo = '0'");
			stringBuffer.append(" AND CertiNO = '"+ prpJplanFeeSchema.getCertiNo() +"'");
			blPrpJinvest.query(dbpool,stringBuffer.toString(),0);
			
			
			if(blPrpJinvest.getSize() == 0)
			{
				throw new UserException(-98, -1167,
						"blPrpJplanCommission.transInvestCommission", "XX"
								+ prpJplanCommissionSchema.getPolicyNo()
								+ "没有对应的投资本金数据");
			}














			prpJplanCommissionSchema.setToCenterCode(blPrpJinvest.getArr(0).getToCenterCode());
			prpJplanCommissionSchema.setToComCode(blPrpJinvest.getArr(0).getToComCode());
			
			prpJplanCommissionSchema.setRealRefPremium(blPrpJinvest.getArr(0).getPlanFee());
		}
		blPrpJplanCommission.save(dbpool);
	}
    /**
     * @author xushaojiang 20070806
     * @return 
     * @throws Exception
     */
    public String updateOperateSequence(String[] check ,String[] arrCertiNo,String[] arrSerialNo) throws Exception{
        DbPool dbpool = new DbPool();
        DBPrpJplanCommission dbPrpJplanCommission = new DBPrpJplanCommission();
        String strDataSource =SysConfig.CONST_DDCCDATASOURCE;
        String OperateSequence = "";
        try{
            
        	dbpool.open(strDataSource);
        	dbpool.beginTransaction();
            OperateSequence=dbPrpJplanCommission.updateOperateSequence(dbpool,check,arrCertiNo,arrSerialNo);
            dbpool.commitTransaction();
        }
        catch(Exception exception){
        	dbpool.rollbackTransaction();
            throw exception;
        }
        finally{
        	dbpool.close();
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
    public String  updateOperateSequence(String iWherePart,String payRefNo,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJplanCommission dbPrpJplanCommission = new DBPrpJplanCommission();
      if (iLimitCount > 0 && dbPrpJplanCommission.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJplanFee.query");
      }else
      {
        initArr();
        strSqlStatement = " SELECT PrpJplanCommission.* FROM PrpJplanCommission,prpjpayCommission WHERE " +
			"     PrpJplanCommission.certino=prpjpayCommission.certino " +
			" and PrpJplanCommission.serialno=prpjpayCommission.serialno " +
			" and prpJpayCommission.payreftimes='1' and prpjpayCommission.payrefno='"+payRefNo+"' and "+ iWherePart;
        schemas = dbPrpJplanCommission.findByConditions(strSqlStatement);
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
    public String  updateOperateSequence(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJplanCommission dbPrpJplanCommission = new DBPrpJplanCommission();
      if (iLimitCount > 0 && dbPrpJplanCommission.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJplanCommission.query");
      }else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJplanCommission WHERE " + iWherePart;
        schemas = dbPrpJplanCommission.findByConditions(strSqlStatement);
        return updateOperateSequence(schemas);
      }
    }
    
    /**
     * @author zhanglingjian 20071016
     * @return 
     * @throws Exception
     */
    public String updateOperateSequence(Vector schemas) throws Exception{
        DBManager    dbManager     = new DBManager();
        DBPrpJplanCommission dbPrpJplanCommissionSeg = new DBPrpJplanCommission();
        String strDataSource = SysConfig.CONST_DDCCDATASOURCE;
        String OperateSequence = "";
        try{
            
            dbManager.open(strDataSource);
            dbManager.beginTransaction();
            OperateSequence = dbPrpJplanCommissionSeg.updateOperateSequence(dbManager,schemas);
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
     * 手续费支付单打印查询 zhanglingjian 
     * @param operateSequence 操作序列
     * @param iPageNo 
     * @param iPageCount 页数
     * @param iLimitCount
     * @throws UserException
     * @throws Exception
     */
    public void queryBySequence(String operateSequence,int iPageNo,int iPageCount,int iLimitCount) throws UserException,Exception
    {
    	String strSqlStatement = "";
    	initArr();
    	DBPrpJplanCommission dbPrpJplanCommission = new DBPrpJplanCommission();
    	strSqlStatement = "select * from (select rownum as rn,prpjplancommission.* from prpjplancommission where operateSequence='"+operateSequence+
    		"') where rn>"+(iPageNo*iPageCount-iPageCount)+" and rn<="+iPageNo*iPageCount;
        schemas = dbPrpJplanCommission.findByConditions(strSqlStatement);
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
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
    		
    		DBPrpJplanCommission dbPrpJplanCommission = new DBPrpJplanCommission();
    		dbPrpJplanCommission.getInfo(dbpool,arrCertiNo[Integer.parseInt(arrCheck[i])],
    				arrSerialNo[Integer.parseInt(arrCheck[i])]);
    		dbPrpJplanCommission.setPayRefFee(dbPrpJplanCommission.getPlanFee());
    		dbPrpJplanCommission.update(dbpool);
    		
    		
    		BLPrpJpayCommission blPrpJpayCommission = new BLPrpJpayCommission();
    		DBPrpJpayCommission dbPrpJpayCommission = new DBPrpJpayCommission();
    		dbPrpJpayCommission.setSchema(blPrpJpayCommission.genSchema(dbPrpJplanCommission));
    		
    		ChgDate chgDate  = new ChgDate();
        	String currentDate = chgDate.getCurrentTime("yyyy-MM-dd");
    		
    	    double dblPayReFfee = 0;
    	    dblPayReFfee = Double.parseDouble(dbPrpJpayCommission.getPlanFee())*Double.parseDouble(exchangeRate);
    	    dblPayReFfee = Str.round(Str.round(dblPayReFfee,8),2);
    	    dbPrpJpayCommission.setCurrency2(currency2);
    	    dbPrpJpayCommission.setExchangeRate(""+exchangeRate);
    	    dbPrpJpayCommission.setPayRefFee(""+dblPayReFfee);
    	    dbPrpJpayCommission.setOperateDate(currentDate);
    	    dbPrpJpayCommission.setPayRefNo(payRefNo);
    	    dbPrpJpayCommission.setPayRefNoFlag("1");
    	    dbPrpJpayCommission.setPayRefFlag("0");
    	    dbPrpJpayCommission.setPayRefNoType("3");
    	    dbPrpJpayCommission.setPayItemType("0");

    	    
    	    
    	    DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
    	    dbPrpJpayRefMain.getInfo(dbpool,payRefNo,"1");
    	    dbPrpJpayRefMain.setPayRefFee(""+(Double.parseDouble(dbPrpJpayRefMain.getPayRefFee())+
    	    		Double.parseDouble(dbPrpJpayCommission.getPayRefFee())));
    	    double  dblRealRefPremium = 0;
    	    double  dblRealRefPremiumPre = 0;   
            if (dbPrpJpayRefMain.getRemark()!=null &&!dbPrpJpayRefMain.getRemark().equals(""))
            	dblRealRefPremiumPre = Str.round(Double.parseDouble(dbPrpJpayRefMain.getRemark()),2);
            if (dbPrpJplanCommission.getRealRefPremium()!=null &&!dbPrpJplanCommission.getRealRefPremium().equals(""))
          	  dblRealRefPremium = Str.round(dblRealRefPremiumPre+Double.parseDouble(dbPrpJplanCommission.getRealRefPremium()),2);
            dbPrpJpayRefMain.setRemark(dblRealRefPremium+"");
      	    String strSQL = " SELECT count(1) FROM prpjpaycommission WHERE payrefno='"+payRefNo+"' " ;
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
                                      "BLPrpJplanCommission.addPolicy()",
                                      "支付单" + payRefNo + "所含手续费个数大于1000，请重新选择！" );
            }
    	    dbPrpJpayCommission.setOperatorCode(dbPrpJpayRefMain.getPackageCode());
    	    dbPrpJpayCommission.setOperateUnit(dbPrpJpayRefMain.getPackageUnit());
    	    dbPrpJpayCommission.setPayRefName(dbPrpJpayRefMain.getPayRefName());
    	    dbPrpJpayCommission.insert(dbpool);
    	    
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
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
    		
    		DBPrpJplanCommission dbPrpJplanCommission = new DBPrpJplanCommission();
    		dbPrpJplanCommission.getInfo(dbpool,arrCertiNo[Integer.parseInt(arrCheck[i])],
    				arrSerialNo[Integer.parseInt(arrCheck[i])]);
    		dbPrpJplanCommission.setPayRefFee("0");
    		dbPrpJplanCommission.update(dbpool);
    		
    		
    		DBPrpJpayCommission dbPrpJpayCommission = new DBPrpJpayCommission();
    		dbPrpJpayCommission.getInfo(dbpool,arrCertiNo[Integer.parseInt(arrCheck[i])],
    				arrSerialNo[Integer.parseInt(arrCheck[i])],"1");
    	    double dblPayCommission = Double.parseDouble(dbPrpJpayCommission.getPayRefFee());
    	    dbPrpJpayCommission.delete(dbpool,arrCertiNo[Integer.parseInt(arrCheck[i])],
    				arrSerialNo[Integer.parseInt(arrCheck[i])],"1");
    	    dblPayCommission = Str.round(Str.round(dblPayCommission,8),2);
    	    
    	    
    	    DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
    	    dbPrpJpayRefMain.getInfo(dbpool,payRefNo,"1");
    	    dbPrpJpayRefMain.setPayRefFee(""+(Double.parseDouble(dbPrpJpayRefMain.getPayRefFee())
    	    		- dblPayCommission));
    	    double  dblRealRefPremium = 0;
    	    double  dblRealRefPremiumPre = 0;   
            if (dbPrpJpayRefMain.getRemark()!=null &&!dbPrpJpayRefMain.getRemark().equals(""))
            	dblRealRefPremiumPre = Str.round(Double.parseDouble(dbPrpJpayRefMain.getRemark()),2);
            if (dbPrpJplanCommission.getRealRefPremium()!=null &&!dbPrpJplanCommission.getRealRefPremium().equals(""))
          	  dblRealRefPremium = Str.round(dblRealRefPremiumPre-Double.parseDouble(dbPrpJplanCommission.getRealRefPremium()),2);
            dbPrpJpayRefMain.setRemark(dblRealRefPremium+"");
    	    String strSQL = " SELECT count(1) FROM prpjpaycommission WHERE payrefno='"+payRefNo+"' " ;
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
                                      "BLPrpJplanCommission.delPolicy()",
                                      "支付单" + payRefNo + "所含手续费个数小于1个，请重新选择！" );
            }
    	    dbPrpJpayRefMain.update(dbpool);
    	    count++;
    	}
    	return count;
    }
    
    /**
     * @author  xushaojiang 20080110
     * @ 从prpjplanfee表把历史手续费数据转入prpjplancommission表（不带dbpool）
     * @param iCenterCode 核算单位
     * @throws Exception
     */
    public void transHisCommission(String iCenterCode) throws Exception{
        DbPool dbpool = new DbPool();
        String strDataSource =SysConfig.CONST_DDCCDATASOURCE;

        try{
            
            dbpool.open(strDataSource);
        	this.logPrintln("开始进行手续费历史数据处理");
            this.transHisCommission(dbpool,iCenterCode);
        	this.logPrintln("结束手续费历史数据处理");
        }
        catch(Exception exception){
            throw exception;
        }
        finally{
            dbpool.close();
        }
    }        
    /**
     * @author  xushaojiang 20080110
     * 从prpjplanfee表把历史手续费数据转入prpjplancommission表（带dbpool）
     * @param dbpool 数据库连接池
     * @param iCenterCode 核算单位
     * @throws Exception
     */
    public void transHisCommission(DbPool dbpool, String iCenterCode)
       throws UserException,Exception
    {
    	this.logPrintln("开始进行手续费历史数据处理");
        BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
        
        String strWherePart = " CenterFlag IN ('1','2') AND ValidStatus='1'";
        if (iCenterCode.length()==8){
        	strWherePart += Str.convertString("ComCode",iCenterCode,"=");	
        }else if (iCenterCode.length()==2){
        	strWherePart += " And comcode like '"+iCenterCode+"%' ";
        }
        strWherePart += " ORDER BY ComCode";
        blPrpDcompany.query(dbpool,strWherePart,0);
        for(int i=0; i<blPrpDcompany.getSize(); i++){
            try {
            	String strCenterCode =blPrpDcompany.getArr(i).getComCode();
            	this.logPrintln("开始进行核算单位"+strCenterCode+"手续费历史数据处理");   
            	StringBuffer sqlWhereBuffer =null;
            	
            	
            	sqlWhereBuffer= new StringBuffer();
            	sqlWhereBuffer.append(" CertiType ='S' And CenterCode = '"+strCenterCode+"'");
            	BLPrpJplanFee blPrpJplanFee = null ;
            	
                String strSQL = " SELECT COUNT(1) FROM prpjplanfee Where" + sqlWhereBuffer.toString() ; 
                ResultSet resultSet = null;
				int intCount = 0;
				try {
					resultSet = dbpool.query(strSQL);
					if (resultSet.next()) {
						intCount = resultSet.getInt(1);
					}
					resultSet.close();
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				} finally {
					if (resultSet != null)
						resultSet.close();
				}
				for (int a =0 ; a<=intCount/1000;a++){
					blPrpJplanFee = new BLPrpJplanFee();
	            	sqlWhereBuffer= new StringBuffer();
	            	sqlWhereBuffer.append(" CertiType ='S' And CenterCode = '"+strCenterCode+"'");
					sqlWhereBuffer.append(" And rownum <1000 ");
					blPrpJplanFee.query(dbpool, sqlWhereBuffer.toString(), 0);
	            	this.logPrintln("手续费总条数为："+blPrpJplanFee.getSize());  
	            	
	            	
	            	sqlWhereBuffer= new StringBuffer();
	            	sqlWhereBuffer.append(" comcode like '"+strCenterCode.substring(0,2)+"%'                ");
	            	BLPrpDagent blPrpDagent = new BLPrpDagent();
	            	blPrpDagent.query(dbpool,sqlWhereBuffer.toString(), 0);
	            	HashMap hashMapAgent = new HashMap();
	            	for(int j=0;j<blPrpDagent.getSize();j++){
	            		hashMapAgent.put(blPrpDagent.getArr(j).getAgentCode(), blPrpDagent.getArr(j).getAgentName());
	            	}
	            	
	            	
	            	BLPrpDuser blPrpDuser = new BLPrpDuser();
	            	blPrpDuser.query(dbpool,sqlWhereBuffer.toString(), 0);
	            	HashMap hashMapUser = new HashMap();
	            	for(int j=0;j<blPrpDuser.getSize();j++){
	            		hashMapUser.put(blPrpDuser.getArr(j).getUserCode(), blPrpDuser.getArr(j).getUserName());
	            	}            	
	            	
	            	
	         	    this.transHisCommissionData(dbpool,blPrpJplanFee,hashMapAgent,hashMapUser); 
	            	this.logPrintln("结束核算单位"+strCenterCode+"手续费历史数据处理");    
				}
            	          	        	    
            } catch(UserException ue) {
                ue.printStackTrace();
                throw ue;
            } catch(Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
    	this.logPrintln("结束手续费历史数据处理");
    }  
    /**	 
     * @author  xushaojiang 20080110
     * @deprecated 从prpjplanfee表把某个核算单位历史手续费数据转入prpjplancommission表（带dbpool）
     * @param dbpool 数据库连接池
     * @param blPrpJplanFee 应收应付手续费结果集
     * @param hashMapAgent  代理人代码HashMap
     * @param hashMapUser   业务员代码HashMap
     * @throws Exception
     */
    public void transHisCommissionData(DbPool dbpool, BLPrpJplanFee blPrpJplanFee,HashMap hashMapAgent,HashMap hashMapUser)
       throws UserException,Exception
    {
    	for(int i=0;i<blPrpJplanFee.getSize();i++){




    		
    		try{
    			this.logPrintln("手续费总条数为："+blPrpJplanFee.getSize()+" 开始处理第"+(i+1)+"条数据，业务单号为"+blPrpJplanFee.getArr(i).getCertiNo());               	
                dbpool.beginTransaction();
                
                DBPrpJplanCommission dbPrpJplanCommission = new DBPrpJplanCommission();
                PrpJplanCommissionSchema prpJplanCommissionSchema = new PrpJplanCommissionSchema();
                PrpJplanFeeSchema prpJplanFeeSchema = blPrpJplanFee.getArr(i);
                DBPrpJplanFee DBPrpJplanFee = new DBPrpJplanFee();
                BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();    
                DBPrpJpayRefRec dbPrpJpayRefRec = null;  
                PrpJpayCommissionSchema prpJpayCommissionSchema = null;
                DBPrpJpayCommission dbPrpJpayCommission = null;
                PrpJpayRefRecSchema prpJpayRefRecSchema = null;
                StringBuffer sqlWhereBuffer =null;
                
                DBPrpCommission dbPrpCommission = new DBPrpCommission();
            	int intReturn = dbPrpCommission.getInfo(dbpool, prpJplanFeeSchema.getCertiNo());

    			
                
                sqlWhereBuffer = new StringBuffer();
                sqlWhereBuffer.append(" CertiType = 'S' And CertiNo='"+prpJplanFeeSchema.getCertiNo()+"'");
                sqlWhereBuffer.append(" And SerialNo = '"+prpJplanFeeSchema.getSerialNo()+"'            ");
                sqlWhereBuffer.append(" And PayRefReason = '"+prpJplanFeeSchema.getPayRefReason()+"'    ");
                blPrpJpayRefRec.query(dbpool, sqlWhereBuffer.toString(), 0);
                
                
                this.evaluate(dbpool,prpJplanFeeSchema, prpJplanCommissionSchema,"1");
                
    			this.logPrintln("开始处理prpjplanfee中数据");
                
    			String strAgentName = prpJplanCommissionSchema.getAgentCode();
    			if(hashMapAgent.get(prpJplanCommissionSchema.getAgentCode())!=null){
    				strAgentName = hashMapAgent.get(prpJplanCommissionSchema.getAgentCode()).toString();
    			}
    			String strHandler1Name = prpJplanCommissionSchema.getHandler1Code();
    			if(hashMapUser.get(prpJplanCommissionSchema.getHandler1Code())!=null){
    				strHandler1Name = hashMapUser.get(prpJplanCommissionSchema.getHandler1Code()).toString();
    			}
                prpJplanCommissionSchema.setAgentName(strAgentName);
                prpJplanCommissionSchema.setHandler1Name(strHandler1Name);
            	if (intReturn==100){
            		prpJplanCommissionSchema.setExtendChar1("1");
            	}else{
            		prpJplanCommissionSchema.setDisRate(dbPrpCommission.getDisRate());
            	}

                
                
                this.transHisCommissionControl(dbpool,prpJplanCommissionSchema);
                dbPrpJplanCommission.setSchema(prpJplanCommissionSchema);
                
                dbPrpJplanCommission.insert(dbpool);
                DBPrpJplanFee.delete(dbpool, "S", prpJplanFeeSchema.getCertiNo(), prpJplanFeeSchema.getSerialNo(), prpJplanFeeSchema.getPayRefReason());
                
    			this.logPrintln("开始处理prpjpayrefrec中数据");  	
                for(int j = 0;j<blPrpJpayRefRec.getSize();j++){ 
                	
                	dbPrpJpayRefRec = new DBPrpJpayRefRec();
                    prpJpayRefRecSchema = blPrpJpayRefRec.getArr(j);                	
                	prpJpayCommissionSchema = new PrpJpayCommissionSchema();
                	dbPrpJpayCommission = new DBPrpJpayCommission();
                	this.evaluate(prpJpayRefRecSchema, prpJpayCommissionSchema) ;
                	
                	prpJpayCommissionSchema.setAgentName(prpJplanCommissionSchema.getAgentName());
                	prpJpayCommissionSchema.setHandler1Name(prpJplanCommissionSchema.getHandler1Name());
                	prpJpayCommissionSchema.setDisRate(prpJplanCommissionSchema.getDisRate());
                	prpJpayCommissionSchema.setPayFlag(prpJplanCommissionSchema.getPayFlag());
                	prpJpayCommissionSchema.setRealRefPremium(prpJplanCommissionSchema.getRealRefPremium());
                	prpJpayCommissionSchema.setMainRiskCode(prpJplanCommissionSchema.getMainRiskCode());
                	prpJpayCommissionSchema.setWriteOffFlag(prpJplanCommissionSchema.getWriteOffFlag());
                	prpJpayCommissionSchema.setMainPolicyFlag(prpJplanCommissionSchema.getMainPolicyFlag());
                	prpJpayCommissionSchema.setAllinsFlag(prpJplanCommissionSchema.getAllinsFlag());
                	dbPrpJpayCommission.setSchema(prpJpayCommissionSchema);
                	dbPrpJpayCommission.insert(dbpool);
                	dbPrpJpayRefRec.delete(dbpool, "S", blPrpJpayRefRec.getArr(j).getCertiNo(), 
                			blPrpJpayRefRec.getArr(j).getSerialNo(), blPrpJpayRefRec.getArr(j).getPayRefReason(),
                			blPrpJpayRefRec.getArr(j).getPayRefTimes());
                }
    			this.logPrintln("结束处理第"+(i+1)+"条数据，业务单号为"+prpJplanFeeSchema.getCertiNo());     
                dbpool.commitTransaction();               			
    		}catch(UserException ue) {
                dbpool.rollbackTransaction();
                this.logPrintln(ue.toString());
                ue.printStackTrace();
            } catch(Exception e) {
                dbpool.rollbackTransaction();
                this.logPrintln(e.toString());
                e.printStackTrace();
            }
    	}
    }      
    /**
     * @author xushaojiang 20080110
     * 处理PrpJplanCommission表的控制字段PoliPayRefDate、RealRefPremium、PayFlag
     * @param dbpool 数据库连接池
     * @param prpJplanCommissionSchema 
     * @throws Exception
     */
    public void transHisCommissionControl(DbPool dbpool,
			PrpJplanCommissionSchema prpJplanCommissionSchema)
			throws UserException, Exception {
		try {
			BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
			String strCertitype ="";
			
			if(prpJplanCommissionSchema.getCertiNo().equals(prpJplanCommissionSchema.getPolicyNo())
					||prpJplanCommissionSchema.getPayRefReason().equals("P93")){
				strCertitype="P";
			}else{
				strCertitype="E";
			}
			StringBuffer sqlWhereBuffer = new StringBuffer();
			sqlWhereBuffer.append(" CertiType='"+strCertitype+"'                               ");
			sqlWhereBuffer.append(" And PayRefReason not in('R72','R73','R74')                 ");	
			sqlWhereBuffer.append(" And PayRefTimes ='1' And  IdentifyType='1'                 ");	
			sqlWhereBuffer.append(" And SerialNo ='"+prpJplanCommissionSchema.getSerialNo()+"' ");		
			
			if(prpJplanCommissionSchema.getPayRefReason().equals("P93")){
				sqlWhereBuffer.append(" And PolicyNo='"+prpJplanCommissionSchema.getPolicyNo()+"'");				
			}else {
				sqlWhereBuffer.append(" And CertiNo='"+prpJplanCommissionSchema.getCertiNo()+"'");				
			}
			
			blPrpJpayRefRec.query(dbpool, sqlWhereBuffer.toString(), 0);
			int intReturn = blPrpJpayRefRec.getSize();
			double dbRealRefPremium =0;
			for (int i=0;i<intReturn ;i++){
				dbRealRefPremium+=Double.parseDouble(blPrpJpayRefRec.getArr(i).getPlanFee());
			}
			
			if (intReturn==0){
				prpJplanCommissionSchema.setPoliPayRefDate("");
				prpJplanCommissionSchema.setRealRefPremium("0.00");
				prpJplanCommissionSchema.setPayFlag("0");
			}else{
				
					prpJplanCommissionSchema.setPoliPayRefDate(blPrpJpayRefRec.getArr(0).getPayRefDate());
					if(prpJplanCommissionSchema.getPayRefReason().equals("P90"))
						prpJplanCommissionSchema.setRealRefPremium(dbRealRefPremium+"");
					else
						prpJplanCommissionSchema.setRealRefPremium("0.00");
					prpJplanCommissionSchema.setPayFlag("1");						
			}
			
		} catch (UserException ue) {
			ue.printStackTrace();
			throw ue;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}  
    
    /**
     * 处理P93的PrpJplanCommission表的控制字段PoliPayRefDate、RealRefPremium、PayFlag
     * @param dbpool 数据库连接池
     * @param prpJplanCommissionSchema 
     * @throws Exception
     */
    public void transCommissionControl(DbPool dbpool,
			PrpJplanCommissionSchema prpJplanCommissionSchema)
			throws UserException, Exception {
		try {
			BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
			String strCertitype = "P";
			
			StringBuffer sqlWhereBuffer = new StringBuffer();
			sqlWhereBuffer.append(" CertiType='"+strCertitype+"'                               ");
			sqlWhereBuffer.append(" And PayRefReason not in('R72','R73','R74')                 ");	
			sqlWhereBuffer.append(" And PayRefTimes ='1' And  IdentifyType='1'                 ");	
			sqlWhereBuffer.append(" And SerialNo ='"+prpJplanCommissionSchema.getSerialNo()+"' ");		
			
			 if(prpJplanCommissionSchema.getPayRefReason().equals("P93")){
				sqlWhereBuffer.append(" And PolicyNo='"+prpJplanCommissionSchema.getPolicyNo()+"'");				
			}else{
				throw new UserException(-98, -1167, "BLPrpJplanCommission.transHisCommissionControl",
    					prpJplanCommissionSchema.getCertiNo()+"收付原因"+prpJplanCommissionSchema.getPayRefReason()+"错误！" ); 
			}
			
			blPrpJpayRefRec.query(dbpool, sqlWhereBuffer.toString(), 0);
			int intReturn = blPrpJpayRefRec.getSize();
			
			
			if (intReturn==0){
				prpJplanCommissionSchema.setPoliPayRefDate("");
				prpJplanCommissionSchema.setPayFlag("0");
			}else{
				
				prpJplanCommissionSchema.setPoliPayRefDate(blPrpJpayRefRec.getArr(0).getPayRefDate());
				prpJplanCommissionSchema.setPayFlag("1");						
			}
			
		} catch (UserException ue) {
			ue.printStackTrace();
			throw ue;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	} 
    

    
	/**   
	 * @author  xushaojiang 20080110
	 * @desc 根据blPrpJplanFee生成blPrpJplanCommission
	 * @param blPrpJplanFee
	 *            
	 * @param blPrpJplanCommission
	 *            
	 * @throws Exception
	 */
    public void evaluate(PrpJpayRefRecSchema iSchema,PrpJpayCommissionSchema prpJpayCommissionSchema) throws Exception
    {
    	prpJpayCommissionSchema.setCertiType(iSchema.getCertiType());
    	prpJpayCommissionSchema.setCertiNo(iSchema.getCertiNo());
    	prpJpayCommissionSchema.setPolicyNo(iSchema.getPolicyNo());
    	prpJpayCommissionSchema.setSerialNo(iSchema.getSerialNo());
    	prpJpayCommissionSchema.setPayRefReason(iSchema.getPayRefReason());
    	prpJpayCommissionSchema.setPayRefTimes(iSchema.getPayRefTimes());
    	prpJpayCommissionSchema.setClassCode(iSchema.getClassCode());
    	prpJpayCommissionSchema.setRiskCode(iSchema.getRiskCode());
    	prpJpayCommissionSchema.setAppliCode(iSchema.getAppliCode());
		prpJpayCommissionSchema.setAppliName(iSchema.getAppliName());
		prpJpayCommissionSchema.setInsuredCode(iSchema.getInsuredCode());
		prpJpayCommissionSchema.setInsuredName(iSchema.getInsuredName());
		prpJpayCommissionSchema.setStartDate(iSchema.getStartDate());
		prpJpayCommissionSchema.setEndDate(iSchema.getEndDate());
		prpJpayCommissionSchema.setValidDate(iSchema.getValidDate());
		prpJpayCommissionSchema.setPayNo(iSchema.getPayNo());
		prpJpayCommissionSchema.setCurrency1(iSchema.getCurrency1());
		prpJpayCommissionSchema.setPlanFee(iSchema.getPlanFee());
		prpJpayCommissionSchema.setPlanDate(iSchema.getPlanDate());
		prpJpayCommissionSchema.setComCode(iSchema.getComCode());
		prpJpayCommissionSchema.setMakeCom(iSchema.getMakeCom());
		prpJpayCommissionSchema.setAgentCode(iSchema.getAgentCode());
		prpJpayCommissionSchema.setAgentName("");
		prpJpayCommissionSchema.setHandler1Code(iSchema.getHandler1Code());
		prpJpayCommissionSchema.setHandler1Name("");
		prpJpayCommissionSchema.setHandlerCode(iSchema.getHandlerCode());
		prpJpayCommissionSchema.setUnderWriteDate(iSchema.getUnderWriteDate());
		prpJpayCommissionSchema.setCoinsFlag(iSchema.getCoinsFlag());
		prpJpayCommissionSchema.setCoinsCode(iSchema.getCoinsCode());
		prpJpayCommissionSchema.setCoinsName(iSchema.getCoinsName());
		prpJpayCommissionSchema.setCoinsType(iSchema.getCoinsType());
		prpJpayCommissionSchema.setOperateDate(iSchema.getOperateDate());
		prpJpayCommissionSchema.setOperatorCode(iSchema.getOperatorCode());
		prpJpayCommissionSchema.setOperateUnit(iSchema.getOperateUnit());
		prpJpayCommissionSchema.setCurrency2(iSchema.getCurrency2());
		prpJpayCommissionSchema.setExchangeRate(iSchema.getExchangeRate());
		prpJpayCommissionSchema.setPayRefFee(iSchema.getPayRefFee());
		prpJpayCommissionSchema.setDisRate("");
		prpJpayCommissionSchema.setRealRefPremium("");
		prpJpayCommissionSchema.setPayRefName(iSchema.getPayRefName());
		prpJpayCommissionSchema.setPayRefFlag(iSchema.getIdentifyType());
		prpJpayCommissionSchema.setPayRefNoFlag(iSchema.getIdentifyNumber());	
		prpJpayCommissionSchema.setRemark(iSchema.getRemark());
		prpJpayCommissionSchema.setPayRefNo(iSchema.getPayRefNo());
		prpJpayCommissionSchema.setPayRefDate(iSchema.getPayRefDate());
		prpJpayCommissionSchema.setCarNatureCode(iSchema.getCarNatureCode());
		prpJpayCommissionSchema.setUseNatureCode(iSchema.getUseNatureCode());
		prpJpayCommissionSchema.setCarProperty(iSchema.getCarProperty());		
		prpJpayCommissionSchema.setBusinessNature(iSchema.getBusinessNature());
		prpJpayCommissionSchema.setCenterCode(iSchema.getCenterCode());
		prpJpayCommissionSchema.setVoucherNo(iSchema.getVoucherNo());
		prpJpayCommissionSchema.setPayRefNoType("3");
		prpJpayCommissionSchema.setRealPayRefNo(iSchema.getRealPayRefNo());
		prpJpayCommissionSchema.setMainRiskCode("");
		prpJpayCommissionSchema.setPayFlag("");
		prpJpayCommissionSchema.setWriteOffFlag("");
		prpJpayCommissionSchema.setMainPolicyFlag("");
		prpJpayCommissionSchema.setAllinsFlag("");
		if (iSchema.getPayRefTimes().equals("1")){
			prpJpayCommissionSchema.setPayItemType("0");	
		}else if(iSchema.getPayRefTimes().startsWith("1")){
			prpJpayCommissionSchema.setPayItemType("1");
		}else if (iSchema.getPayRefTimes().startsWith("2")){
			prpJpayCommissionSchema.setPayItemType("2");
		}else {
			throw new UserException(-98, -1167, "BLPrpJplanCommission.evaluate",
					"prpjpayrefrec表中没有业务单号"+iSchema.getCertiNo()+"手续费的支付次数PayRefTimes错误"+ iSchema.getPayRefTimes()); 
		}
	
		prpJpayCommissionSchema.setFlag("");
		prpJpayCommissionSchema.setExtendChar1("");
		prpJpayCommissionSchema.setExtendChar2("");
		prpJpayCommissionSchema.setExtendNum1("");
		prpJpayCommissionSchema.setExtendNum2("");
	}
    
    /**
     *应付手续费核销查询 (包含已注销XX) 
     *@param iPlanWhere PrpJplanCommission表查询条件
     *@param iOrderBy 排序项 SangMingqian 20051021 现场修改
     *@param iPayWhere  PrpJpayCommission表查询条件
     *@throws UserException
     *@throws Exception
     */
    public void queryRef(String iPlanWhere, String iOrderBy, String iPayWhere) throws Exception {
    	
		String strSqlStatement = "select currency1,comcode,agentcode,agentname,handler1code,handler1name, "
				+ " sum(planfee) as planfee,sum(realpayreffee) as RealPayRefFee, "
				+ " sum(planfee-realpayreffee) from ( "
				+ " select Currency1,comcode,agentcode,agentname,handler1code,handler1name, "
				+ " sum(planfee) as PlanFee,0 as RealPayRefFee "
				+ " from PrpJplanCommission where "
				+ iPlanWhere
				+ " group by Currency1,comcode,agentcode,agentname,handler1code,handler1name "
				+ " union all "
				+ " select Currency1,comcode,agentcode,agentname,handler1code,handler1name, "
				+ " 0 as PlanFee,sum(decode(payreffee,null,0,payreffee/exchangerate)) as RealPayRefFee "
				+ " from PrpJpayCommission where "
				+ iPayWhere
				+ " group by Currency1,comcode,agentcode,agentname,handler1code,handler1name ) "
				+ " group by Currency1,comcode,agentcode,agentname,handler1code,handler1name "
				+ " having sum(planfee-realpayreffee)!=0 " + " order by " + iOrderBy;
      this.initArr();
      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        PrpJplanCommissionSchema prpJplanCommissionSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJplanCommissionSchema = new PrpJplanCommissionSchema();
          prpJplanCommissionSchema.setCurrency1(resultSet.getString("Currency1"));
          prpJplanCommissionSchema.setComCode(resultSet.getString("ComCode"));
          prpJplanCommissionSchema.setAgentCode(resultSet.getString("AgentCode"));
          prpJplanCommissionSchema.setAgentName(resultSet.getString("AgentName"));
          prpJplanCommissionSchema.setHandler1Code(resultSet.getString("Handler1Code"));
          prpJplanCommissionSchema.setHandler1Name(resultSet.getString("Handler1Name"));
          prpJplanCommissionSchema.setPlanFee(""+resultSet.getDouble("PlanFee"));
          prpJplanCommissionSchema.setRealPayRefFee(""+resultSet.getDouble("RealPayRefFee"));
          this.setArr(prpJplanCommissionSchema);
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
     * 不带dbpool的委托处理方法
     * @param CancleFlag 1-取消委托
     * @return 无
     */
    public int consignCommission(String CancleFlag) throws Exception
    {
    	DbPool dbpool = new DbPool();
    	int count = 0;

        try {
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        	dbpool.beginTransaction();
        	count = consignCommission(dbpool,CancleFlag);
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
	public int consignCommission(DbPool dbpool, String CancleFlag) throws UserException, Exception {
		int size = 0;
		for (int i = 0; i < this.getSize(); i++) {
			DBPrpJplanCommission dbPrpJplanCommission = new DBPrpJplanCommission();
			dbPrpJplanCommission.getInfo(dbpool, this.getArr(i).getCertiNo(), this.getArr(i)
					.getSerialNo());
			if (CancleFlag.equals("1")) {
				dbPrpJplanCommission.setToCenterCode("0");
				dbPrpJplanCommission.setToComCode("0");
				dbPrpJplanCommission.setToUserCode("0");
				dbPrpJplanCommission.setToDesc("");
				dbPrpJplanCommission.setToStatus("0");
			} else {
				dbPrpJplanCommission.setToCenterCode(this.getArr(i).getToCenterCode());
				dbPrpJplanCommission.setToComCode(this.getArr(i).getToComCode());
				dbPrpJplanCommission.setToUserCode(this.getArr(i).getToUserCode());
				dbPrpJplanCommission.setToDesc(this.getArr(i).getToDesc());
				dbPrpJplanCommission.setToStatus("1");
			}
			dbPrpJplanCommission.update(dbpool);
			size++;
		}
		return size;
	}
	
    /**
     * 不带dbpool的委托确认处理方法
     * @param Flag 0-委托确认，1-委托打回
     * @return 无
     */
    public int consignCommissionVerify(String Flag) throws Exception
    {
    	DbPool dbpool = new DbPool();
    	int count = 0;
        try {
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        	dbpool.beginTransaction();
        	count = consignCommissionVerify(dbpool,Flag);
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
	public int consignCommissionVerify(DbPool dbpool, String Flag) throws UserException, Exception {
		int size = 0;
		String strToStatus = "";
		if (Flag.equals("0")) {
			strToStatus = "2";
		} else {
			strToStatus = "3";
		}
		for (int i = 0; i < this.getSize(); i++) {
			DBPrpJplanCommission dbPrpJplanCommission = new DBPrpJplanCommission();
			dbPrpJplanCommission.getInfo(dbpool, this.getArr(i).getCertiNo(), this.getArr(i)
					.getSerialNo());
			dbPrpJplanCommission.setToStatus(strToStatus);
			dbPrpJplanCommission.update(dbpool);
			size++;
		}
		return size;
	}
	/**
	 * @desc 日志输出
	 * @param iLog
	 * @throws Exception
	 */
	public void logPrintln(String iLog) throws Exception {
		ChgDate chgDate = new ChgDate();
		logger
				.info(chgDate.getCurrentTime("yyyy-MM-dd hh:mm:ss") + "><"
						+ iLog);
	}
}
