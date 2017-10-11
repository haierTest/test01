package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.blsvr.cb.BLPrpCcoins;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.misc.DBPrpCommission;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;
import com.sp.prpall.dbsvr.pg.DBPrpPmain;
import com.sp.prpall.dbsvr.tb.DBPrpTmain;
import com.sp.prpall.schema.PrpCommissionSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 定义PrpCommission的BL类
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>@createdate 2005-06-14</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpCommission{
    private Vector schemas = new Vector();
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    /**
     * 构造函数
     */
    public BLPrpCommission(){
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
     *增加一条PrpCommissionSchema记录
     *@param iPrpCommissionSchema PrpCommissionSchema
     *@throws Exception
     */
    public void setArr(PrpCommissionSchema iPrpCommissionSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpCommissionSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpCommissionSchema记录
     *@param index 下标
     *@return 一个PrpCommissionSchema对象
     *@throws Exception
     */
    public PrpCommissionSchema getArr(int index) throws Exception
    {
     PrpCommissionSchema prpCommissionSchema = null;
       try
       {
        prpCommissionSchema = (PrpCommissionSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpCommissionSchema;
     }
    /**
     *删除一条PrpCommissionSchema记录
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
      DBPrpCommission dbPrpCommission = new DBPrpCommission();
      if (iLimitCount > 0 && dbPrpCommission.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCommission.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCommission WHERE " + iWherePart;
        schemas = dbPrpCommission.findByConditions(strSqlStatement);
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
      DBPrpCommission dbPrpCommission = new DBPrpCommission();
      if (iLimitCount > 0 && dbPrpCommission.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCommission.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCommission WHERE " + iWherePart;
        schemas = dbPrpCommission.findByConditions(dbpool,strSqlStatement);
      }
    }
    /**
     *
     * @param iWherePart
     * @throws UserException
     * @throws Exception
     */
    public void sumByCurrency(String iWherePart) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpCommission dbPrpCommission = new DBPrpCommission();
      initArr();
      strSqlStatement = " SELECT SUM(DISFEE) AS DISFEE,CURRENCY  FROM PrpCommission WHERE " + iWherePart+" GROUP BY CURRENCY";
      schemas = dbPrpCommission.sumByCurrency(strSqlStatement);
    }

    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpCommission dbPrpCommission = new DBPrpCommission();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpCommission.setSchema((PrpCommissionSchema)schemas.get(i));
      dbPrpCommission.insert(dbpool);
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
            
        	
            
        	
            
        	
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        	
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
     * @author lijibin 2005-06-19
     * @desc 生成手续/经纪费信息
     * @param iCertiType
     * @param iCertiNo
     * @throws UserException
     * @throws Exception
     */
    public void createCommission(String iCertiType, String iCertiNo) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
        try {
            
        	
            
        	
            
        	
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        	
        dbpool.beginTransaction();
        this.createCommission(dbpool,iCertiType,iCertiNo);
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
     * @author lijibin 2005-06-19
     * @desc 生成手续/经纪费信息
     * @param iPolicyNo
     * @throws UserException
     * @throws Exception
     */
    public void createCommission(DbPool dbpool,String iCertiType,String iCertiNo) throws UserException,Exception
    {
      if(iCertiType.equals("P"))
        this.createCommissionC(dbpool,iCertiNo);
      if(iCertiType.equals("E"))
        this.createCommissionP(dbpool,iCertiNo);
    }

    /**
     * @author lijibin 2005-06-19
     * @desc 生成XX手续/经纪费信息
     * @param dbpool
     * @param iPolicyNo
     * @throws UserException
     * @throws Exception
     */
    public void createCommissionC(DbPool dbpool,String iPolicyNo) throws UserException,Exception
    {
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      dbPrpCmain.getInfo(dbpool,iPolicyNo);
      
      BLPrpCcoins blPrpCcoins = new BLPrpCcoins();
      BLPrpCommissionDetail blPrpCommissionDetail = new BLPrpCommissionDetail();
      double dblSelfRate = 100; 
      dblSelfRate = Str.round(Double.parseDouble(Str.chgStrZero(blPrpCcoins.getSelfRate(dbpool,iPolicyNo,"1"))),4);
      double dblSelfRate1 = 100; 
      dblSelfRate1 = Str.round(Double.parseDouble(Str.chgStrZero(blPrpCcoins.getSelfRate(dbpool,iPolicyNo,"2"))),4);
      
      double dblCommissionRate = 0;
      double dblCommission = 0;
      dblCommissionRate = Double.parseDouble(Str.chgStrZero(dbPrpCmain.getDisRate()));
      if(dblCommissionRate == 0 && Double.parseDouble(Str.chgStrZero(dbPrpCmain.getPureRate()))==0)
        return;

      
      String strSQL =
          "SELECT Currency,SUM(PlanFee) AS PlanFee FROM PrpCplan WHERE PolicyNo='" +
          iPolicyNo + "' AND EndorseNo IS NULL GROUP BY Currency";
      String strCurrency = "";
      double dblSumPremium = 0;
      ResultSet rs = dbpool.query(strSQL);
      while(rs.next())
      {
        strCurrency = rs.getString("Currency");
        dblSumPremium += rs.getDouble("PlanFee");
      }
      rs.close();
      if(dblSumPremium == 0)
      {
        strCurrency = dbPrpCmain.getCurrency();
        dblSumPremium = Double.parseDouble(dbPrpCmain.getSumPremium());
      }
      dblSumPremium = Str.round(dblSumPremium,2);

      
      double dblDisRate = Double.parseDouble(Str.chgStrZero(dbPrpCmain.getDisRate1()));
      double dblDisPremium = dblSumPremium*dblDisRate/100;
      
      dblDisPremium = Str.round(Str.round(dblDisPremium,8),2);
      
      dblDisPremium = Str.round(Str.round(dblDisPremium * dblSelfRate1/100,4),2);
      

      
      dblCommission = (dblSumPremium - dblDisPremium) * dblCommissionRate/100;
      dblCommission = Str.round(Str.round(dblCommission,8),2);
      
      dblCommission = Str.round(Str.round(dblCommission * dblSelfRate/100,4),2);
      
      
      
      
      
      
      
      
      
      
      
      
      

      ChgDate chgDate = new ChgDate();
      PrpCommissionSchema prpCommissionSchema = new PrpCommissionSchema();
      prpCommissionSchema.setClassCode(dbPrpCmain.getClassCode());
      prpCommissionSchema.setRiskCode(dbPrpCmain.getRiskCode());
      prpCommissionSchema.setCertiNo(iPolicyNo);
      prpCommissionSchema.setCertiType("P");
      prpCommissionSchema.setPolicyNo(iPolicyNo);
      prpCommissionSchema.setDisRate(""+dblCommissionRate);
      prpCommissionSchema.setCurrency(strCurrency);
      prpCommissionSchema.setDisFee(""+dblCommission);
      prpCommissionSchema.setGenerateDate(chgDate.getCurrentTime("yyyy-MM-dd"));
      prpCommissionSchema.setGenerateTime(chgDate.getCurrentTime("HH:mm"));
      
      prpCommissionSchema.setSelfRate(String.valueOf(dblSelfRate));
      
      this.setArr(prpCommissionSchema);
      blPrpCommissionDetail.createCommissionCDetail(dbpool,this,dbPrpCmain);
      this.save(dbpool);
      blPrpCommissionDetail.save(dbpool);
    }

    /**
     * @author lijibin 2005-06-19
     * @desc 生成XX手续/经纪费信息
     * @param dbpool
     * @param iPolicyNo
     * @throws UserException
     * @throws Exception
     */
    public void createCommissionP(DbPool dbpool,String iEndorseNo) throws UserException,Exception
    {
      DBPrpPmain dbPrpPmain = new DBPrpPmain();
      dbPrpPmain.getInfo(dbpool,iEndorseNo);
      BLPrpCommissionDetail blPrpCommissionDetail = new BLPrpCommissionDetail();

      double dblCommissionRate = 0;
      double dblCommission = 0;
      dblCommissionRate = Double.parseDouble(Str.chgStrZero(dbPrpPmain.getDisRate()));
      if(dblCommissionRate == 0 && Double.parseDouble(Str.chgStrZero(dbPrpPmain.getPureRate()))==0)
        return;

      
      DBPrpPhead dbPrpPhead = new DBPrpPhead();
      dbPrpPhead.getInfo(dbpool,iEndorseNo);
      if(dbPrpPhead.getEndorType().trim().equals(SysConfig.getProperty("EDITTYPE_MIDDLECOST"))
         ||dbPrpPhead.getEndorType().trim().equals(SysConfig.getProperty("EDITTYPE_COMMISSION")))
        return;

      String strSQL =
          "SELECT Currency1,SUM(ChgPremium1) AS ChgPremium1 FROM PrpPfee WHERE EndorseNo='" +
          iEndorseNo + "' GROUP BY Currency1";
      String strCurrency = "";
      double dblSumPremium = 0;
      ResultSet rs = dbpool.query(strSQL);
      while(rs.next())
      {
        strCurrency = rs.getString("Currency1");
        dblSumPremium += rs.getDouble("ChgPremium1");
      }
      rs.close();
      if(dblSumPremium == 0)
        return;

      
      double dblDisRate = Double.parseDouble(Str.chgStrZero(dbPrpPmain.getDisRate1()));
      double dblDisPremium = dblSumPremium*dblDisRate/100;
      
      dblDisPremium = Str.round(Str.round(dblDisPremium,8),2);

      
      dblCommission = (dblSumPremium - dblDisPremium) * dblCommissionRate/100;
      dblCommission = Str.round(Str.round(dblCommission,8),2);

      ChgDate chgDate = new ChgDate();
      PrpCommissionSchema prpCommissionSchema = new PrpCommissionSchema();
      prpCommissionSchema.setClassCode(dbPrpPmain.getClassCode());
      prpCommissionSchema.setRiskCode(dbPrpPmain.getRiskCode());
      prpCommissionSchema.setCertiNo(iEndorseNo);
      prpCommissionSchema.setCertiType("E");
      prpCommissionSchema.setPolicyNo(dbPrpPmain.getPolicyNo());
      prpCommissionSchema.setDisRate(""+dblCommissionRate);
      prpCommissionSchema.setCurrency(strCurrency);
      prpCommissionSchema.setDisFee(""+dblCommission);
      prpCommissionSchema.setGenerateDate(chgDate.getCurrentTime("yyyy-MM-dd"));
      prpCommissionSchema.setGenerateTime(chgDate.getCurrentTime("HH:mm"));
      this.setArr(prpCommissionSchema);
      blPrpCommissionDetail.createCommissionPDetail(dbpool,this,dbPrpPmain);
      this.save(dbpool);
      blPrpCommissionDetail.save(dbpool);
    }

    /**
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param iEndorseNo 批单号
     *@return 无
     */
    public void cancel(DbPool dbpool,String certiNo) throws Exception
    {
      DBPrpCommission dbPrpCommission = new DBPrpCommission();
      dbPrpCommission.delete(dbpool,certiNo);
    }

    /**
     *
     * @param certiNo
     * @throws Exception
     */
    public void getData(String certiNo) throws Exception
    {
        DbPool dbpool = new DbPool();

        try {
            
        	
            
        	
            
        	
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        	
  		getData(dbpool, certiNo);
  	} catch (Exception e) {
  		
  	}finally {      
  	dbpool.close();
      }

      }

    /**
     *
     * @param dbpool
     * @param certiNo
     * @throws Exception
     */
  public void getData(DbPool dbpool, String certiNo) throws Exception {
        
        
        
        
        String strWherePart = " CertiNo=? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(certiNo);
        query(dbpool, strWherePart, arrWhereValue, 0);
        
  }
  
  /**
   *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
   *@author wangchuanzhong 20100602
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
      DBPrpCommission dbPrpCommission = new DBPrpCommission();
      if (iLimitCount > 0 && dbPrpCommission.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
      {
          throw new UserException(-98,-1003,"BLPrpCommission.query");
      }else
      {
          initArr();
          strSqlStatement = " SELECT * FROM PrpCommission WHERE " + iWherePart;
          schemas = dbPrpCommission.findByConditions(dbpool,strSqlStatement,iWhereValue);
      }
  }

    /* add by xiaojian 20050828 begin reason：为了XX单、XX、批单查询用的方法 */
    /**
     * @desc 按XX单号查询手续/经纪费信息
     * @desc SELECT * FROM PrpCommission
     *       WHERE CertiNo=strProposalNo
     *       AND CertiType='T'
     *       ORDER BY GnerateDate,GenerateTime DESC
     * @param String strProposalNo XX单号
     * @return PrpCommissionSchema 主要获取DisRate、DisFee
     */
    public PrpCommissionSchema getDataT(String strProposalNo) throws Exception {
        DBPrpTmain dbPrpTmain = new DBPrpTmain();
        PrpCommissionSchema prpCommissionSchema = new PrpCommissionSchema();

        String strWhere = "";

        int i = 0;
        boolean blExist = false;

        if (dbPrpTmain.getInfo(strProposalNo) == 100) {
            
        	
            logger.info("BLPrpCommission.getDataT：没有"+strProposalNo+"这张XX单！");
        	
            return null;
        }

        strWhere = " CertiNo='" + strProposalNo + "'"
                 + " AND CertiType='T'"
                 + " ORDER BY GenerateDate,GenerateTime DESC";
        this.query(strWhere);
        if (this.getSize() == 0) {
            
        } else {
            prpCommissionSchema = this.getArr(0);
        }

        return prpCommissionSchema;
    }

    /**
     * @desc 按XX号查询手续/经纪费信息
     * @desc SELECT * FROM PrpCommission
     *       WHERE PolicyNo=strPolicyNo AND (如果是批单，核批通过)
     *       ORDER BY GnerateDate,GenerateTime DESC
     * @param String strPolicyNo XX号
     * @return PrpCommissionSchema 主要获取DisRate、DisFee
     */
    public PrpCommissionSchema getDataP(String strPolicyNo) throws Exception {
        DBPrpCmain dbPrpCmain = new DBPrpCmain();
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        PrpCommissionSchema prpCommissionSchema = new PrpCommissionSchema();
        PrpCommissionSchema prpCommissionSchemaTmp = null;

        String strCertiNo = "";
        String strCertiType = "";
        String strUnderWriteFlag = "";
        String strWhere = "";

        double dbSumDisFee = 0;

        int i = 0;
        boolean blExist = false;

        if (dbPrpCmain.getInfo(strPolicyNo) == 100) {
            
        	
            logger.info("BLPrpCommission.getDataP：没有"+strPolicyNo+"这张XX！");
            
            return null;
        }

        strWhere = " PolicyNo='" + strPolicyNo + "'"
                 + " ORDER BY GenerateDate,GenerateTime DESC";
        this.query(strWhere);
        if (this.getSize() == 0) {
            
        } else {
            for (i = 0; i < this.getSize(); i++) {
                prpCommissionSchemaTmp = this.getArr(i);
                strCertiNo = prpCommissionSchemaTmp.getCertiNo();
                strCertiType = prpCommissionSchemaTmp.getCertiType();

                if (strCertiType.equals("E")) {
                    if (dbPrpPhead.getInfo(strCertiNo) == 100) {
                        
                    	
                        logger.info("BLPrpCommission.getDataP：没有"+strCertiNo+"这张批单！");
                        
                        return null;
                    }
                    strUnderWriteFlag = dbPrpPhead.getUnderWriteFlag();
                    
                    if (!strUnderWriteFlag.equals("1")||!strUnderWriteFlag.equals("3")) {
                        continue;
                    }
                }

                if (!blExist) {
                    prpCommissionSchema = this.getArr(i);
                    blExist = true;
                }

                dbSumDisFee = Str.round(Str.round(dbSumDisFee + Double.parseDouble(Str.chgStrZero(prpCommissionSchemaTmp.getDisFee())),8),2);
            }

            if (blExist) {
                prpCommissionSchema.setDisFee(String.valueOf(dbSumDisFee));
            }
        }

        return prpCommissionSchema;
    }

    /**
     * @desc 按批单号查询手续/经纪费信息（含这张批单，不再判断是否核批通过）
     * @desc SELECT * FROM PrpCommission
     *       WHERE (CERTINo=strPolicyNo
     *       OR CERTINO<=strEndorseNo)
     *       ORDER BY GenerateDate,GenerateTime DESC
     * @param String strEndorseNo 批单号
     * @return PrpCommissionSchema 主要获取DisRate、DisFee
     */
    public PrpCommissionSchema getDataE(String strEndorseNo) throws Exception {
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        PrpCommissionSchema prpCommissionSchema = new PrpCommissionSchema();
        PrpCommissionSchema prpCommissionSchemaTmp = null;

        String strPolicyNo = "";
        String strWhere = "";

        double dbSumDisFee = 0;

        int i = 0;
        boolean blExist = false;

        if (dbPrpPhead.getInfo(strEndorseNo)==100) {
            
        	
            logger.info("BLPrpCommission.getDataE：没有"+strEndorseNo+"这张批单！");
            
            return null;
        }

        strPolicyNo = dbPrpPhead.getPolicyNo();
        strWhere = " PolicyNo='"+strPolicyNo+"'"
                 + " AND (CertiNo='"+strPolicyNo+"'"
                 + " OR CertiNo<='"+strEndorseNo+"')"
                 + " ORDER BY GenerateDate,GenerateTime DESC";
        this.query(strWhere);
        for (i = 0; i < this.getSize(); i++) {
            prpCommissionSchemaTmp = this.getArr(i);

            if (!blExist) {
                prpCommissionSchema = this.getArr(0);
                blExist = true;
            }

            dbSumDisFee = Str.round(Str.round(dbSumDisFee + Double.parseDouble(Str.chgStrZero(prpCommissionSchemaTmp.getDisFee())),8),2);
        }

        if (blExist) {
            prpCommissionSchema.setDisFee(String.valueOf(dbSumDisFee));
        }

        return prpCommissionSchema;
    }

    /**
     * @desc 按批单号查询手续/经纪费信息（不含这张批单，不再判断是否核批通过）
     * @desc SELECT * FROM PrpCommission
     *       WHERE (CERTINo=strPolicyNo
     *       OR CERTINO<strEndorseNo)
     *       ORDER BY GenerateDate,GenerateTime DESC
     * @param String strEndorseNo 批单号
     * @return PrpCommissionSchema 主要获取DisRate、DisFee
     */
    public PrpCommissionSchema getDataEPre(String strEndorseNo) throws Exception {
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        PrpCommissionSchema prpCommissionSchema = new PrpCommissionSchema();
        PrpCommissionSchema prpCommissionSchemaTmp = null;

        String strPolicyNo = "";
        String strWhere = "";

        double dbSumDisFee = 0;

        int i = 0;
        boolean blExist = false;

        if (dbPrpPhead.getInfo(strEndorseNo)==100) {
            
        	
            logger.info("BLPrpCommission.getDataE：没有"+strEndorseNo+"这张批单！");
        	
            return null;
        }

        strPolicyNo = dbPrpPhead.getPolicyNo();
        strWhere = " PolicyNo='"+strPolicyNo+"'"
                 + " AND (CertiNo='"+strPolicyNo+"'"
                 + " OR CertiNo<'"+strEndorseNo+"')"
                 + " ORDER BY GenerateDate,GenerateTime DESC";
        this.query(strWhere);
        for (i = 0; i < this.getSize(); i++) {
            prpCommissionSchemaTmp = this.getArr(i);

            if (!blExist) {
                prpCommissionSchema = this.getArr(0);
                blExist = true;
            }

            dbSumDisFee = Str.round(Str.round(dbSumDisFee + Double.parseDouble(Str.chgStrZero(prpCommissionSchemaTmp.getDisFee())),8),2);
        }

        if (blExist) {
            prpCommissionSchema.setDisFee(String.valueOf(dbSumDisFee));
        }

        return prpCommissionSchema;
    }

    /**
     * @desc 按批单号查询手续/经纪费信息（批改类型为56的手续/经纪费最新数据）
     * @desc 核批通过产手的手续/经纪费信息，但注意中间成本的批单号大于手续/经纪费信息的CertiNo
     * @param String strEndorseNo 批单号
     * @return PrpCommissionSchema 主要获取DisRate、DisFee
     */
    public PrpCommissionSchema getDataE56(String strEndorseNo) throws Exception {
        PrpCommissionSchema prpCommissionSchema = null;
        prpCommissionSchema = this.getDataE(strEndorseNo);
        return prpCommissionSchema;
    }

    /**
     * @desc 按批单号查询手续/经纪费信息（批改类型为56的手续/经纪费第二新数据）
     * @desc 核批通过产手的手续/经纪费信息，但注意中间成本的批单号大于手续/经纪费信息的CertiNo
     * @param String strEndorseNo 批单号
     * @return PrpCommissionSchema 主要获取DisRate、DisFee
     */
    public PrpCommissionSchema getDataEPre56(String strEndorseNo) throws Exception {
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        PrpCommissionSchema prpCommissionSchema = new PrpCommissionSchema();
        PrpCommissionSchema prpCommissionSchemaTmp = null;

        String strPolicyNo = "";
        String strWhere = "";

        double dbSumDisFee = 0;

        int i = 0;
        boolean blExist = false;

        if (dbPrpPhead.getInfo(strEndorseNo)==100) {
            
        	
            logger.info("BLPrpCommission.getDataE：没有"+strEndorseNo+"这张批单！");
        	
            return null;
        }

        strPolicyNo = dbPrpPhead.getPolicyNo();
        strWhere = " PolicyNo='"+strPolicyNo+"'"
                 + " AND (CertiNo='"+strPolicyNo+"'"
                 + " OR CertiNo<'"+strEndorseNo+"')"
                 + " ORDER BY GenerateDate,GenerateTime DESC";
        this.query(strWhere);
        for (i = 0; i < this.getSize(); i++) {
            prpCommissionSchemaTmp = this.getArr(i);

            if (!blExist) {
                prpCommissionSchema = this.getArr(0);
                blExist = true;
            }

            dbSumDisFee = Str.round(Str.round(dbSumDisFee + Double.parseDouble(Str.chgStrZero(prpCommissionSchemaTmp.getDisFee())),8),2);
        }

        if (blExist) {
            prpCommissionSchema.setDisFee(String.valueOf(dbSumDisFee));
        }

        return prpCommissionSchema;
    }
    /* add by xiaojian 20050828 end */


    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
  }
}
