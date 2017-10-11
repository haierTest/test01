package com.sp.prpall.blsvr.jf;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.sysframework.exceptionlog.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;
import com.sp.prpall.blsvr.cb.BLPrpCplan;
import com.sp.prpall.blsvr.pg.BLPrpPfee;
import com.sp.prpall.dbsvr.cb.DBPrpCexpense;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.jf.DBPrpJmanageFee;
import com.sp.prpall.dbsvr.jf.DBPrpJmanageFeePackage;
import com.sp.prpall.dbsvr.pg.DBPrpPexpense;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;
import com.sp.prpall.schema.PrpJmanageFeeSchema;
import com.sp.prpall.schema.PrpJpayRefRecSchema;

/**
 * 定义管理费用表的BL类
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>@createdate 2007-01-31</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJmanageFee {
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpJmanageFee(){
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
     *增加一条PrpJmanageFeeSchema记录
     *@param iPrpJmanageFeeSchema PrpJmanageFeeSchema
     *@throws Exception
     */
    public void setArr(PrpJmanageFeeSchema iPrpJmanageFeeSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJmanageFeeSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpJmanageFeeSchema记录
     *@param index 下标
     *@return 一个PrpJmanageFeeSchema对象
     *@throws Exception
     */
    public PrpJmanageFeeSchema getArr(int index) throws Exception
    {
     PrpJmanageFeeSchema prpJmanageFeeSchema = null;
       try
       {
        prpJmanageFeeSchema = (PrpJmanageFeeSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJmanageFeeSchema;
     }
    /**
     *删除一条PrpJmanageFeeSchema记录
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
      DBPrpJmanageFee dbPrpJmanageFee = new DBPrpJmanageFee();
      if (iLimitCount > 0 && dbPrpJmanageFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJmanageFee.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJmanageFee WHERE " + iWherePart; 
        schemas = dbPrpJmanageFee.findByConditions(strSqlStatement);
      }
    }
    
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@throws UserException
     *@throws Exception
     */
    public void queryByLimitCount(String iWherePart) throws UserException,Exception
    {
    	int limitCount = Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim());
    	String strSqlStatement = "";
    	DBPrpJmanageFee dbPrpJmanageFee = new DBPrpJmanageFee();initArr();
    	String sql = " SELECT * FROM PrpJmanageFee WHERE " + iWherePart;
    	strSqlStatement = " SELECT * FROM (" + sql + ") WHERE ROWNUM<=" + limitCount;
        schemas = dbPrpJmanageFee.findByConditions(strSqlStatement);
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
      DBPrpJmanageFee dbPrpJmanageFee = new DBPrpJmanageFee();
      if (iLimitCount > 0 && dbPrpJmanageFee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJmanageFee.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJmanageFee WHERE " + iWherePart; 
        schemas = dbPrpJmanageFee.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJmanageFee dbPrpJmanageFee = new DBPrpJmanageFee();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJmanageFee.setSchema((PrpJmanageFeeSchema)schemas.get(i));
      dbPrpJmanageFee.insert(dbpool);
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
     * @author guwensi 20070212 取消收付款确认，将对冲费用信息存入管理费用表
     * @modify xushaojiang 20070329
     * @param dbpool
     * @param prpJpayRefRecSchema
     * @throws Exception
     */
    public void transCancelVoucher(DbPool dbpool,PrpJpayRefRecSchema prpJpayRefRecSchema) throws Exception
    {
        
        DBPrpCexpense dbPrpCexpense = new DBPrpCexpense();

        String strPolicyNo = prpJpayRefRecSchema.getPolicyNo();
        int intReturn = dbPrpCexpense.getInfo(dbpool,strPolicyNo);
        ChgDate iDate = new ChgDate();
        String strOperateDate = iDate.getCurrentTime("yyyy-MM-dd");
        
        if(!(intReturn == 100))
        {          
                DBPrpJmanageFee dbPrpJmanageFee = new DBPrpJmanageFee();
                BLPrpJmanageFee blPrpJmanageFee=new BLPrpJmanageFee();
                PrpJmanageFeeSchema prpJmanageFeeSchemaRed = new PrpJmanageFeeSchema();
                PrpJmanageFeeSchema prpJmanageFeeSchema = new PrpJmanageFeeSchema();
                
                 intReturn =dbPrpJmanageFee.getInfoNew(dbpool,"0", prpJpayRefRecSchema.getCertiNo(),prpJpayRefRecSchema.getSerialNo());                
                 if(!(intReturn == 100))
                 { 
                	 /**
                      * 负的冲销记录
                      */
                     prpJmanageFeeSchemaRed.setSchema(dbPrpJmanageFee);
                     prpJmanageFeeSchemaRed.setSerialNo(""+(-1*Double.parseDouble(dbPrpJmanageFee.getSerialNo())));
                     prpJmanageFeeSchemaRed.setPayRefFee(""+(-1*Double.parseDouble(dbPrpJmanageFee.getPayRefFee())));
                     prpJmanageFeeSchemaRed.setPayRefDate(strOperateDate);
                     prpJmanageFeeSchemaRed.setManageFee(""+(-1*Double.parseDouble(dbPrpJmanageFee.getManageFee())));
                     prpJmanageFeeSchemaRed.setValidStatus("0");
                     blPrpJmanageFee.setArr(prpJmanageFeeSchemaRed);                
                     /**
                      * 正的未实收记录
                      */
                     prpJmanageFeeSchema.setSchema(dbPrpJmanageFee);
                     prpJmanageFeeSchema.setSerialNo(""+(Double.parseDouble(dbPrpJmanageFee.getSerialNo())+1));
                     prpJmanageFeeSchema.setPayRefDate("5000-01-01");
                     prpJmanageFeeSchema.setPayRefFee("0");
                     prpJmanageFeeSchema.setValidStatus("1");
                     blPrpJmanageFee.setArr(prpJmanageFeeSchema);   
                     blPrpJmanageFee.save(dbpool);
                     /**
                      * 修改原来那条记录状态为"已取消"
                      */
                     dbPrpJmanageFee.setValidStatus("0");
                     dbPrpJmanageFee.update(dbpool);
                 }

            }

        }
 
    
    /**
     * @author guwensi 20070207 结算拆包方法
     * @param arrCheckFlag
     * @param arrFeeType
     * @param arrPayRefNo
     * @throws Exception 
     * @throws UserException 
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
    public void dealPackageReverse(String[] arrCheckFlag,String[] arrFeeType,String[] arrPayRefNo) throws SQLException, ClassNotFoundException, UserException, Exception
    {
        DbPool dbpool = new DbPool();
        BLPrpJmanageFee blPrpJmanageFee = new BLPrpJmanageFee();
        DBPrpJmanageFee dbPrpJmanageFee = new DBPrpJmanageFee();
        DBPrpJmanageFeePackage dbPrpJmanageFeePackage = new DBPrpJmanageFeePackage();
        String strWherePart = "";
        try {
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          dbpool.beginTransaction();
          
          for(int i=0; i<arrCheckFlag.length; i++)
          {
            if(arrCheckFlag[i].equals("Y"))
            {
                
                strWherePart = " FeeType = '" + arrFeeType[i] + "' AND PayRefNo = '" + arrPayRefNo[i] + "' ";
                blPrpJmanageFee.query(strWherePart);
                for(int j=0;j<blPrpJmanageFee.getSize();j++)
                {
                    blPrpJmanageFee.getArr(j).setPayRefNo("0000000000000000000");
                    dbPrpJmanageFee.setSchema(blPrpJmanageFee.getArr(j));
                    dbPrpJmanageFee.update(dbpool);
                }
                
                dbPrpJmanageFeePackage.delete(dbpool, arrFeeType[i], arrPayRefNo[i]);
            }
          }
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
     * @author guwensi 20070206 结算确认方法
     * @param arrCheckFlag
     * @param arrFeeType
     * @param arrPayRefNo
     * @throws Exception 
     * @throws UserException 
     * @throws ClassNotFoundException 
     * @throws SQLException 
     */
    public void dealPackageConfirm(String[] arrCheckFlag,String[] arrFeeType,String[] arrPayRefNo) throws SQLException, ClassNotFoundException, UserException, Exception
    {
        DbPool dbpool = new DbPool();
        BLPrpJmanageFee blPrpJmanageFee = new BLPrpJmanageFee();
        DBPrpJmanageFee dbPrpJmanageFee = new DBPrpJmanageFee();
        DBPrpJmanageFeePackage dbPrpJmanageFeePackage = new DBPrpJmanageFeePackage();
        String strWherePart = " 1=1 ";
        
        String strDate = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        
        try {
        	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          dbpool.beginTransaction();
          
          for(int i=0; i<arrCheckFlag.length; i++)
          {
            if(arrCheckFlag[i].equals("Y"))
            {
                
                strWherePart += " AND FeeType = '" + arrFeeType[i] + "' AND PayRefNo = '" + arrPayRefNo[i] + "' ";
                blPrpJmanageFee.query(strWherePart);
                for(int j=0;j<blPrpJmanageFee.getSize();j++)
                {
                    blPrpJmanageFee.getArr(j).setDealStatus("1");
                    blPrpJmanageFee.getArr(j).setDealDate(strDate);
                    dbPrpJmanageFee.setSchema(blPrpJmanageFee.getArr(j));
                    dbPrpJmanageFee.update(dbpool);
                }
                
                dbPrpJmanageFeePackage.getInfo(arrFeeType[i], arrPayRefNo[i]);
                dbPrpJmanageFeePackage.setDealStatus("1");
                dbPrpJmanageFeePackage.update(dbpool);
            }
          }
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
     * @author guwensi 20070205 结算打包方法
     * @param arrCheckFlag
     * @param arrFeeType
     * @param arrCertiNo
     * @param arrPolicyNo
     * @param arrSerialNo
     * @param iPayRefNo
     * @param iTotalFee
     * @param iFlag 备注 存储规则：机构|业务员|代理人|实收起期|实收止期
     * @return
     * @throws UserException
     * @throws Exception
     */
    public boolean dealPackage(String[] arrCheckFlag,String[] arrFeeType,String[] arrCertiNo,String[] arrPlanSerialNo,String[] arrSerialNo,String iPayRefNo,String iTotalFee,String iFlag) throws UserException,Exception
    {
        DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          dbpool.beginTransaction();
          
          for(int i=0; i<arrCheckFlag.length; i++)
          {
            if(arrCheckFlag[i].equals("Y"))
            {
                this.dealPackageManageFee(dbpool, arrFeeType[i], arrCertiNo[i], arrPlanSerialNo[i], arrSerialNo[i], iPayRefNo);
            }
          }
         
          
          DBPrpJmanageFeePackage dbPrpJmanageFeePackage = new DBPrpJmanageFeePackage();
          dbPrpJmanageFeePackage.setFeeType("0");
          dbPrpJmanageFeePackage.setPayRefNo(iPayRefNo);
          dbPrpJmanageFeePackage.setTotalFee(iTotalFee);
          dbPrpJmanageFeePackage.setDealStatus("0");
          dbPrpJmanageFeePackage.setValidStatus("1");
          dbPrpJmanageFeePackage.setFlag(iFlag);
          dbPrpJmanageFeePackage.insert(dbpool);
          
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
        return true;
    }    
    /**
     * @author guwensi 20070205 更新PrpJmanageFee表的打包号
     * @param dbpool 数据库连接池
     * @param iFeeType 费用类型
     * @param iCertiNo XX/批单号
     * @param iPolicyNo XX号
     * @param iSerialNo 序列号
     * @param iPayRefNo 打包号
     * @throws UserException
     * @throws Exception
     */
    public void dealPackageManageFee(DbPool dbpool,String iFeeType,String iCertiNo,String iPlanSerialNo,String iSerialNo,String iPayRefNo) throws UserException,Exception
    {
      DBPrpJmanageFee dbPrpJmanageFee = new DBPrpJmanageFee();
      dbPrpJmanageFee.getInfo(dbpool, iFeeType, iCertiNo, iPlanSerialNo, iSerialNo);
      dbPrpJmanageFee.setPayRefNo(iPayRefNo);
      
      dbPrpJmanageFee.update(dbpool);
      }  
    
    /**
     * @author guwensi 20070201 从业务转入管理费用信息到PrpJmanageFee表
     * @param iCertiType 业务类型
     * @param iCertiNo 业务号
     * @throws UserException
     * @throws Exception
     */
    public void transManageFeeData(String iCertiType,String iCertiNo) throws UserException,Exception
    {
        DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          dbpool.beginTransaction();
          this.transManageFeeData(dbpool,iCertiType,iCertiNo);
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
     * @author guwensi 20070202 从业务转入管理费用信息到PrpJmanageFee表
     * @param dbpool 数据库连接池
     * @param iCertiType 业务类型
     * @param iCertiNo 业务号
     * @throws UserException
     * @throws Exception
     */
    public void transManageFeeData(DbPool dbpool,String iCertiType,String iCertiNo) throws UserException,Exception
    {
      
      if(iCertiType.equals("P"))
      {
          this.transManageFeePolicy(dbpool,iCertiNo);
          
          this.save(dbpool);
      }
      
      else if(iCertiType.equals("E"))
      {
          this.transManageFeeEndor(dbpool,iCertiNo);
          
          this.save(dbpool);
      }
    }  
    
    /**
     * @author guwensi 20070202 转入XX管理费用信息到PrpJmanageFee表
     * @param dbpool 数据库连接池
     * @param iPolicyNo XX号
     * @throws UserException
     * @throws Exception
     */
    public void transManageFeePolicy(DbPool dbpool,String iPolicyNo) throws UserException,Exception
    {
      
      DBPrpCexpense dbPrpCexpense = new DBPrpCexpense();
      int intReturnPrpCexpense = 0;
      String strWherePart = "";
      
      
      intReturnPrpCexpense = dbPrpCexpense.getInfo(dbpool,iPolicyNo);
      
      
      if(!(intReturnPrpCexpense == 100))
      {
          
          BLPrpCplan blPrpCplan = new BLPrpCplan();
          
          
          strWherePart = "PolicyNo='"+iPolicyNo+"' AND (EndorseNo IS NULL OR EndorseNo='')";
          blPrpCplan.query(dbpool,strWherePart,0);
          
          for(int i=0; i<blPrpCplan.getSize(); i++)
          {
              
              DBPrpCmain dbPrpCmain = new DBPrpCmain();
              
              
              PrpJmanageFeeSchema prpJmanageFeeSchema = null;
              
              int intReturnPrpCmain = 0;

              
              intReturnPrpCmain = dbPrpCmain.getInfo(dbpool,iPolicyNo);
              if(intReturnPrpCmain == 100)
              {
                  throw new UserException( -98, -1167, "BLPrpJmanageFee.transManageFeePolicy", "PrpCmain表中无此XX信息："+iPolicyNo);
              }

              prpJmanageFeeSchema = new PrpJmanageFeeSchema();
              prpJmanageFeeSchema.setFeeType("0");
              prpJmanageFeeSchema.setCertiNo(dbPrpCmain.getPolicyNo());
              prpJmanageFeeSchema.setPlanSerialNo(blPrpCplan.getArr(i).getSerialNo());
              prpJmanageFeeSchema.setSerialNo("1");
              prpJmanageFeeSchema.setPolicyNo(dbPrpCmain.getPolicyNo());
              prpJmanageFeeSchema.setPayNo(blPrpCplan.getArr(i).getPayNo());
              prpJmanageFeeSchema.setPayRefNo("0000000000000000000");
              prpJmanageFeeSchema.setInsuredCode(dbPrpCmain.getInsuredCode());
              prpJmanageFeeSchema.setInsuredName(dbPrpCmain.getInsuredName());
              prpJmanageFeeSchema.setSignDate(dbPrpCmain.getOperateDate());
              prpJmanageFeeSchema.setStartDate(dbPrpCmain.getStartDate());
              prpJmanageFeeSchema.setValidDate(dbPrpCmain.getStartDate());
              prpJmanageFeeSchema.setClassCode(dbPrpCmain.getClassCode());
              prpJmanageFeeSchema.setRiskCode(dbPrpCmain.getRiskCode());
              prpJmanageFeeSchema.setComCode(dbPrpCmain.getComCode());
              prpJmanageFeeSchema.setHandlerCode(dbPrpCmain.getHandlerCode());
              prpJmanageFeeSchema.setAgentCode(dbPrpCmain.getAgentCode());
              prpJmanageFeeSchema.setOperatorCode(dbPrpCmain.getOperatorCode());
              prpJmanageFeeSchema.setDealStatus("0");
              
              
              double dblManageFee = 0;
              
              double dblManageFeeRate =0;
              if(!dbPrpCexpense.getManageFeeRate().equals(""))
              {
                  dblManageFeeRate = Double.parseDouble(dbPrpCexpense.getManageFeeRate());
              }
              
              double dblPlanFee = 0;
              dblPlanFee = Double.parseDouble(blPrpCplan.getArr(i).getPlanFee());
              
              dblManageFee = 0;
              dblManageFee = Str.round(dblPlanFee * (dblManageFeeRate/100), 2);
              
              prpJmanageFeeSchema.setManageFee(String.valueOf(dblManageFee));
              prpJmanageFeeSchema.setManageFeeRate(""+dblManageFeeRate);
              prpJmanageFeeSchema.setSalesSalaryRate(dbPrpCexpense.getSalesSalaryRate());
              prpJmanageFeeSchema.setTeamFeeRate(dbPrpCexpense.getTeamFeeRate());
              prpJmanageFeeSchema.setOfficeFeeRate(dbPrpCexpense.getOfficeFeeRate());
              prpJmanageFeeSchema.setValidStatus("1");
              
              prpJmanageFeeSchema.setPayRefFee("0");
              prpJmanageFeeSchema.setPayRefDate("5000-01-01");
              this.setArr(prpJmanageFeeSchema);
          }
      }
      


    }   

    /**
     * @author guwensi 20070202 转入批单管理费用信息到PrpJmanageFee表
     * @param dbpool
     * @param iEndorseNo
     * @throws UserException
     * @throws Exception
     */
    public void transManageFeeEndor(DbPool dbpool,String iEndorseNo) throws UserException,Exception
    {
       
        
        DBPrpCmain dbPrpCmain = new DBPrpCmain();
        
        
        
        
        
        
        BLPrpPfee blPrpPfee = new BLPrpPfee();
        
        
        DBPrpCexpense dbPrpCexpense = new DBPrpCexpense();
        DBPrpPexpense dbPrpPexpense = new DBPrpPexpense();       
        
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        
        
        PrpJmanageFeeSchema prpJmanageFeeSchema = null;
        
        int intReturnPrpCmain = 0;
        int intReturnPrpCexpense = 0;
        int intReturnPrpPexpense = 0;
        int intReturnPrpPhead = 0;

        
        intReturnPrpPhead = dbPrpPhead.getInfo(dbpool,iEndorseNo);
        if(intReturnPrpPhead == 100)
        {
            throw new UserException( -98, -1167, "BLPrpJmanageFee.transManageFeeEndor", "PrpPhead表中无此批单信息："+iEndorseNo);
        }
        String iPolicyNo = dbPrpPhead.getPolicyNo();
        String strEndorType = dbPrpPhead.getEndorType();
        String[] arrEndorType = strEndorType.split(",");
        
        intReturnPrpCmain = dbPrpCmain.getInfo(dbpool,iPolicyNo);
        if(intReturnPrpCmain == 100)
        {
            throw new UserException( -98, -1167, "BLPrpJmanageFee.transManageFeeEndor", "PrpCmain表中无此XX信息："+iPolicyNo);
        }
        
        blPrpPfee.getData(iEndorseNo);
        
        intReturnPrpCexpense = dbPrpCexpense.getInfo(dbpool,iPolicyNo);
        
        if(!(intReturnPrpCexpense == 100))
        {
            
            if(blPrpPfee.getSize()!=0)
            {
                for(int i=0; i<blPrpPfee.getSize(); i++)
                {
                    prpJmanageFeeSchema = new PrpJmanageFeeSchema();
                    prpJmanageFeeSchema.setFeeType("0");
                    prpJmanageFeeSchema.setCertiNo(blPrpPfee.getArr(i).getEndorseNo());
                    prpJmanageFeeSchema.setPlanSerialNo(String.valueOf(i+1));
                    prpJmanageFeeSchema.setSerialNo(String.valueOf(1));
                    prpJmanageFeeSchema.setPolicyNo(blPrpPfee.getArr(i).getPolicyNo());
                    prpJmanageFeeSchema.setPayNo(String.valueOf(i+1));
                    prpJmanageFeeSchema.setPayRefNo("0000000000000000000");
                    prpJmanageFeeSchema.setInsuredCode(dbPrpCmain.getInsuredCode());
                    prpJmanageFeeSchema.setInsuredName(dbPrpCmain.getInsuredName());
                    prpJmanageFeeSchema.setSignDate(dbPrpCmain.getOperateDate());
                    prpJmanageFeeSchema.setStartDate(dbPrpCmain.getStartDate());
                    prpJmanageFeeSchema.setValidDate(dbPrpPhead.getValidDate());
                    prpJmanageFeeSchema.setClassCode(dbPrpCmain.getClassCode());
                    prpJmanageFeeSchema.setRiskCode(dbPrpCmain.getRiskCode());
                    prpJmanageFeeSchema.setComCode(dbPrpCmain.getComCode());
                    prpJmanageFeeSchema.setHandlerCode(dbPrpCmain.getHandlerCode());
                    prpJmanageFeeSchema.setAgentCode(dbPrpCmain.getAgentCode());
                    prpJmanageFeeSchema.setOperatorCode(dbPrpCmain.getOperatorCode());
                    prpJmanageFeeSchema.setDealStatus("0");
                    
                    
                    double dblManageFee = 0;
                    
                    double dblManageFeeRate =0;
                    if(!dbPrpCexpense.getManageFeeRate().equals("")&&!(dbPrpCexpense.getManageFeeRate()==null))
                    {
                        dblManageFeeRate = Double.parseDouble(dbPrpCexpense.getManageFeeRate());
                    }
                    
                    double dblChgPremium = 0;
                    
                     if((blPrpPfee.getArr(i).getChgPremium()!=null)&&(!blPrpPfee.getArr(i).getChgPremium().equals(""))){
                        
                    	dblChgPremium = Double.parseDouble(blPrpPfee.getArr(i).getChgPremium());
                    	
                    }
                     
                     
                    dblManageFee = 0;
                    dblManageFee = Str.round(dblChgPremium * (dblManageFeeRate/100), 2);
                    
                    prpJmanageFeeSchema.setManageFee(String.valueOf(dblManageFee));
                    prpJmanageFeeSchema.setManageFeeRate(""+dblManageFeeRate);
                    prpJmanageFeeSchema.setSalesSalaryRate(dbPrpCexpense.getSalesSalaryRate());
                    prpJmanageFeeSchema.setTeamFeeRate(dbPrpCexpense.getTeamFeeRate());
                    prpJmanageFeeSchema.setOfficeFeeRate(dbPrpCexpense.getOfficeFeeRate());
                    prpJmanageFeeSchema.setValidStatus("1");
                    prpJmanageFeeSchema.setPayRefFee("0");
                    prpJmanageFeeSchema.setPayRefDate("5000-01-01");
                    this.setArr(prpJmanageFeeSchema);
                }            
            }
            
            else if(arrEndorType.length!=0&&arrEndorType[arrEndorType.length-1].equals("59")&&blPrpPfee.getSize()==0)
            {
                
                intReturnPrpPexpense = dbPrpPexpense.getInfo(iEndorseNo);
                
                prpJmanageFeeSchema = new PrpJmanageFeeSchema();
                prpJmanageFeeSchema.setFeeType("0");
                prpJmanageFeeSchema.setCertiNo(dbPrpPhead.getEndorseNo());
                prpJmanageFeeSchema.setPlanSerialNo("1");
                prpJmanageFeeSchema.setSerialNo("1");
                prpJmanageFeeSchema.setPolicyNo(dbPrpPhead.getPolicyNo());
                prpJmanageFeeSchema.setPayNo("1");
                prpJmanageFeeSchema.setPayRefNo("0000000000000000000");
                prpJmanageFeeSchema.setInsuredCode(dbPrpCmain.getInsuredCode());
                prpJmanageFeeSchema.setInsuredName(dbPrpCmain.getInsuredName());
                prpJmanageFeeSchema.setSignDate(dbPrpCmain.getOperateDate());
                prpJmanageFeeSchema.setStartDate(dbPrpCmain.getStartDate());
                prpJmanageFeeSchema.setValidDate(dbPrpPhead.getValidDate());
                prpJmanageFeeSchema.setClassCode(dbPrpCmain.getClassCode());
                prpJmanageFeeSchema.setRiskCode(dbPrpCmain.getRiskCode());
                prpJmanageFeeSchema.setComCode(dbPrpCmain.getComCode());
                prpJmanageFeeSchema.setHandlerCode(dbPrpCmain.getHandlerCode());
                prpJmanageFeeSchema.setAgentCode(dbPrpCmain.getAgentCode());
                prpJmanageFeeSchema.setOperatorCode(dbPrpCmain.getOperatorCode());
                prpJmanageFeeSchema.setDealStatus("0");
                
                
                double dblManageFee = 0;
                double dblSumPremium = 0;
                
                double dblManageFeeRateBefore = 0;
                double dblManageFeeRateAfter  = 0;
                
                if(intReturnPrpPexpense!=100){
                    
                	if((dbPrpPexpense.getManageFeeRate()!=null)&&(!dbPrpPexpense.getManageFeeRate().equals(""))){
                		
                        dblManageFeeRateBefore = Double.parseDouble(dbPrpPexpense.getManageFeeRate());        
                        
                	}
                    
                }
                
            	if((dbPrpCexpense.getManageFeeRate()!=null)&&(!dbPrpCexpense.getManageFeeRate().equals(""))){
            		
                    dblManageFeeRateAfter = Double.parseDouble(dbPrpCexpense.getManageFeeRate());        
                    
            	}                  
                
            	if((dbPrpCmain.getSumPremium()!=null)&&(!dbPrpCmain.getSumPremium().equals(""))){
            		
                    dblSumPremium = Double.parseDouble(dbPrpCmain.getSumPremium());	
                    
            	}
                
                
                dblManageFee = 0;
                dblManageFee = Str.round(dblSumPremium * ((dblManageFeeRateAfter - dblManageFeeRateBefore)/100), 2);
                
                GregorianCalendar gc = new GregorianCalendar();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String strNowaday = df.format(gc.getTime());
                prpJmanageFeeSchema.setPayRefDate(strNowaday);
                prpJmanageFeeSchema.setPayRefFee(String.valueOf(dblSumPremium));
                prpJmanageFeeSchema.setManageFee(String.valueOf(dblManageFee));
                prpJmanageFeeSchema.setManageFeeRate(String.valueOf(dblManageFeeRateAfter - dblManageFeeRateBefore));
                prpJmanageFeeSchema.setSalesSalaryRate(dbPrpCexpense.getSalesSalaryRate());
                prpJmanageFeeSchema.setTeamFeeRate(dbPrpCexpense.getTeamFeeRate());
                prpJmanageFeeSchema.setOfficeFeeRate(dbPrpCexpense.getOfficeFeeRate());
                prpJmanageFeeSchema.setValidStatus("1");
                this.setArr(prpJmanageFeeSchema);
                
            }
        }


        
    }    
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
