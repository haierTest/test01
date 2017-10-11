package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import java.util.*;

import com.sp.payment.utility.Arith;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.jf.DBPrpJcredit;
import com.sp.prpall.schema.PrpJcreditSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.Str;

/**
 * 定义PRPJCREDIT的BL类
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>@createdate 2012-08-01</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJcredit{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */       
    public BLPrpJcredit(){
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
     *增加一条PRPJCREDITSchema记录
     *@param iPRPJCREDITSchema PRPJCREDITSchema
     *@throws Exception
     */
    public void setArr(PrpJcreditSchema iprpJcreditSchema) throws Exception
    {
       try
       {
         schemas.add(iprpJcreditSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PRPJCREDITSchema记录
     *@param index 下标
     *@return 一个PRPJCREDITSchema对象
     *@throws Exception
     */
    public PrpJcreditSchema getArr(int index) throws Exception
    {
        PrpJcreditSchema prpJcreditSchema = null;
       try
       {
           prpJcreditSchema = (PrpJcreditSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJcreditSchema;
     }
    /**
     *删除一条PRPJCREDITSchema记录
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
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()), bindValues);
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJcredit dbPrpJcredit = new DBPrpJcredit();
      if (iLimitCount > 0 && dbPrpJcredit.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPRPJCREDIT.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PRPJCREDIT WHERE " + iWherePart; 
        schemas = dbPrpJcredit.findByConditions(strSqlStatement, bindValues);
      }
    }
    /**
     *按照查询条件得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()),bindValues);
    }
    /**
     *按照查询条件和记录数限制得到一组记录数，并将这组记录赋给schemas对象
     *@param dbpool 全局池
     *@param iWherePart 查询条件(包括排序字句)
     *@param iLimitCount 记录数限制(iLimitCount=0: 无限制)
     *@param bindValues 绑定参数
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJcredit dbPrpJcredit = new DBPrpJcredit();
      if (iLimitCount > 0 && dbPrpJcredit.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPRPJCREDIT.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PRPJCREDIT WHERE " + iWherePart; 
        schemas = dbPrpJcredit.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
        DBPrpJcredit dbPrpJcredit = new DBPrpJcredit();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
          dbPrpJcredit.setSchema((PrpJcreditSchema)schemas.get(i));
          dbPrpJcredit.insert(dbpool);
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
      
      dbpool.open(SysConst.getProperty("PAYMENTDATASOURCE"));
      
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
     * 带dbpool的删除方法
     *@param dbpool    连接池
     *@param certiTye CERTITYE
     *@param policyNo POLICYNO
     *@param payNo PAYNO
     *@return 无
     */
    public void cancel(DbPool dbpool, String certiTye, String policyNo, String payNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PRPJCREDIT WHERE certiTye='" + certiTye + "', AND policyNo='" + policyNo + "', AND payNo='" + payNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param certiTye CERTITYE
     *@param policyNo POLICYNO
     *@param payNo PAYNO
     *@return 无
     */
    public void cancel(String certiTye, String policyNo, String payNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConst.getProperty("PAYMENTDATASOURCE"));
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool, certiTye, policyNo, payNo);
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
     * 不带dbpool根据主键获取数据
     *@param certiTye CERTITYE
     *@param policyNo POLICYNO
     *@param payNo PAYNO
     *@return 无
     */
    public void getData(String certiTye, String policyNo, String payNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("PAYMENTDATASOURCE"));
      getData(dbpool, certiTye, policyNo, payNo);
      dbpool.close();
    }
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param certiTye CERTITYE
     *@param policyNo POLICYNO
     *@param payNo PAYNO
     *@return 无
     */
    public void getData(DbPool dbpool, String certiTye, String policyNo, String payNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "certiTye='" + certiTye + "', AND policyNo='" + policyNo + "', AND payNo='" + payNo + "'";
       query(dbpool, strWherePart, 0, null);
    }
    
    /**
     * @description:小额信贷业务数据插入中间表
     * @param map
     * @return Map
     * @throws Exception
     * @author Administrator
     */
    public Map creditSave(DbPool dbpool, Map map) throws Exception ,UserException{
        DBPrpJcredit dbPrpJcredit = new DBPrpJcredit();
        DBPrpCmain dbPrpCmain = new DBPrpCmain();
        
        
        int intReturn = -1;
        String strSwitch = BLPrpQueryPaymentService.queryMirrorSwitch();
        if ("1".equals(strSwitch)) {
        	intReturn = dbPrpCmain.getInfo(map.get("POLICYNO").toString());
		}else{
			intReturn = dbPrpCmain.getInfo(dbpool,map.get("POLICYNO").toString());
		}
        
        if (intReturn == 100 ) {
            throw new UserException(-98, -1167, "BLPrpJcredit.creditSave", "无此XX信息：" + map.get("POLICYNO").toString());
        }
        Date nowDate = new Date();
        String strNowDate = new DateTime(new Date(),DateTime.YEAR_TO_DAY).toString();
        try {
            dbPrpJcredit.setCertiTye(map.get("CERTITYPE").toString());
            dbPrpJcredit.setPolicyNo(map.get("POLICYNO").toString());
            dbPrpJcredit.setPlanFeePayNO(map.get("PAYNO").toString());
            dbPrpJcredit.setPayNo(map.get("ID").toString());
            dbPrpJcredit.setSplitFlag("0");    
            dbPrpJcredit.setDelinquentFee(map.get("DELINQUENTFEE").toString());
            dbPrpJcredit.setPlanFeeSerialNo(map.get("PAYNO").toString());   
            dbPrpJcredit.setBankCode(map.get("BANKCODE").toString());
            dbPrpJcredit.setSerialCode(map.get("SERIALCODE").toString());
            
            dbPrpJcredit.setPayDate(this.dateFormat(map.get("PAYDATE").toString()));
            dbPrpJcredit.setCreateDateTime(this.dateFormat(map.get("CREATEDATETIME").toString()));
            dbPrpJcredit.setPlanFee(map.get("PLANFEE").toString());
            dbPrpJcredit.setPayRefFee(map.get("PAYREFFEE").toString());
            dbPrpJcredit.setIsAutoPay(map.get("ISAUTOPAY").toString());
            dbPrpJcredit.setCheckDate(this.dateFormat(map.get("CHECKDATE").toString()));
            dbPrpJcredit.setCheckFlag(map.get("CHECKFLAG").toString());
            dbPrpJcredit.setReMark(map.get("REMARK").toString());
            dbPrpJcredit.setVoucherNo("");
            dbPrpJcredit.setVoucherFlag("");
            dbPrpJcredit.setVoucherDate("");
            dbPrpJcredit.setRealPayRefNo("");
            dbPrpJcredit.setInputDate(strNowDate);
            dbPrpJcredit.setFlag1("");
            dbPrpJcredit.setFlag2("");
            
            
            if (map.get("PLANFEE").toString() != null && map.get("PAYREFFEE").toString() != null
            		
            		&&!"".equals(map.get("PLANFEE").toString())&&!"".equals(map.get("PAYREFFEE").toString())){
				checkFee(map,dbPrpJcredit,dbpool);
			}else{
				throw new UserException(-98, -1167, "BLPrpJcredit.creditSave", "实收金额与应收金额不能为空："+ map.get("POLICYNO").toString());
			}
			
			if (new DateTime(dbPrpCmain.getStartDate()).after(nowDate)) {
				throw new UserException(-98, -1167, "BLPrpJcredit.creditSave", "起XXXXX日期大于当前日期的不能自动实收："+ map.get("POLICYNO").toString());
			}
			
			map.put("ISSUCC", "true");
			map.put("FAILCAUSE", "");
		}catch (UserException us) {
			us.printStackTrace();
			throw us;
        } catch (Exception e) {
        e.printStackTrace();
        throw e;
        }
        return map;
    }
    /**
     * 
     * @description:         XX校验
     * @param map            插入数据
     * @param dbPrpJcredit   DBPrpJcredit对象
     * @param dbpool         DbPool对象
     * @throws Exception
     * @author lizhen
     */
    public void checkFee(Map map, DBPrpJcredit dbPrpJcredit, DbPool dbpool) throws Exception,UserException {

		try {
			if (Double.parseDouble(map.get("PLANFEE").toString()) != Double
			        .parseDouble(map.get("PAYREFFEE").toString())) {
				if(map.get("DELINQUENTFEE").toString()==null ||"".equals(map.get("DELINQUENTFEE").toString())){
					throw new UserException(-98, -1167, "BLPrpJcredit.checkFee", "拖欠金额不能为空："+ map.get("POLICYNO").toString());
				}
				
				String sqlCondition = "policyno='" + map.get("POLICYNO").toString() 
				                       + "' and PlanFeePayNO='" + map.get("PAYNO").toString() 
				                       + "' order by paydate";
				this.query(dbpool, sqlCondition,0, null);

				int count = 0;
				double sumpayrefFee = 0;
				count = this.getSize();
				for (int i = 0; i < this.getSize(); i++) {
					sumpayrefFee += Double.parseDouble(this.getArr(i).getPayRefFee());
				}

				double payrefFee = Double.parseDouble(map.get("PAYREFFEE").toString());
				double delinQuentFee = Double.parseDouble(map.get("DELINQUENTFEE").toString());
				double planFee = Double.parseDouble(map.get("PLANFEE").toString());
				if (count == 0) {
					if (Str.round(Arith.add(payrefFee, delinQuentFee), 2) != Str.round(planFee, 2))
						throw new UserException(-98, -1167, "BLPrpJcredit.checkFee", "实收金额+拖欠金额不等于应收金额："
						        + map.get("POLICYNO").toString());
					else if (delinQuentFee > 0) {
						dbPrpJcredit.setSplitFlag("1");
					}
					dbPrpJcredit.setPlanFeeSerialNo(String.valueOf(++count));
				} else {
					
					if (Str.round(Arith.add(Arith.add(sumpayrefFee, payrefFee), delinQuentFee), 2) != Str.round(
					        planFee, 2)) {
						throw new UserException(-98, -1167, "BLPrpJcredit.checkFee", "本期实收金额+拖欠金额不等于应收XX："
						        + map.get("POLICYNO").toString());
					}
					if (delinQuentFee > 0) {
						dbPrpJcredit.setSplitFlag("1");
						dbPrpJcredit.setPlanFeeSerialNo(String.valueOf(++count));
					} else if (delinQuentFee == 0.0) {
						
						PrpJcreditSchema schema = new PrpJcreditSchema();
						int intLast = count - 1;
						schema = this.getArr(intLast);
						if ("2".equals(schema.getSplitFlag())) {
							dbPrpJcredit.setPlanFeeSerialNo("" + (Integer.parseInt(schema.getPlanFeeSerialNo()) + 1));
						} else {
							dbPrpJcredit.setPlanFeeSerialNo(String.valueOf(++count));
						}
					}
				}
			}else {
				
				String sqlCondition = "policyno='" + map.get("POLICYNO").toString() 
				                       + "' and PlanFeePayNO='" + map.get("PAYNO").toString() 
				                       + "' order by paydate";

            	if(!"0".equals(map.get("PAYNO").toString())){
            		this.query(dbpool, sqlCondition,0, null);
    				if(this.getSize()>0){
    					throw new UserException(-98, -1167, "BLPrpJcredit.checkFee", "应收金额不等于实收金额："+ map.get("POLICYNO").toString());
    				}
            	}
			}
			dbPrpJcredit.insert(dbpool);
		} catch (UserException us) {
			throw us;
		}catch (Exception e) {
			throw e;
		}
	}
    /**
	 * @description:小额信贷业务数据插入中间表
	 * @param map
	 * @return Map
	 * @throws Exception
	 * @author Administrator
	 */
    public Map creditSave(Map map) throws Exception,UserException {
        DbPool dbpool = new DbPool();
        Map returnMap = new HashMap();
        String strDataSource = SysConfig.CONST_PAYMENTDATASOURCE;
        
        dbpool.open(strDataSource);
        try {
            dbpool.beginTransaction();
            returnMap = creditSave(dbpool,map);
            dbpool.commitTransaction();
        }catch(UserException us){
        	map.put("ISSUCC", "false");
            map.put("FAILCAUSE", us.getErrorMessage());
            us.printStackTrace();
            dbpool.rollbackTransaction();
        }
        catch (Exception e) {
            map.put("ISSUCC", "false");
            map.put("FAILCAUSE", e.getMessage());
            e.printStackTrace();
            dbpool.rollbackTransaction();


        } finally {
            dbpool.close();
        }
        return returnMap;
    }
    /**
     * @description:查询实收数据
     * @param dbpool
     * @param istrBankCode
     * @param iPayDte_S
     * @param iPayDte_E
     * @return Map
     * @throws Exception
     * @author Administrator
     */
    public Map query(DbPool dbpool,String istrBankCode,String iPayDte_S,String iPayDte_E)throws Exception{
        Map map = new HashMap();
        BLPrpJcredit blPrpJcredit = new BLPrpJcredit();
        PrpJcreditSchema prpJcreditSchema = null;
        ArrayList bindValues = new ArrayList();
        ArrayList prpJcreditList = new ArrayList();

        String strWhereCredit = "  NVL(VoucherNo,0)<> 0 AND BankCode =? AND PayDate BETWEEN to_date(?"
            + ", 'YYYY-MM-DD hh24:mi:ss') AND to_date(?" + ", 'YYYY-MM-DD hh24:mi:ss')";
        bindValues.add(istrBankCode);
        bindValues.add(iPayDte_S);
        bindValues.add(iPayDte_E);
        
        blPrpJcredit.query(strWhereCredit, 0, bindValues);
        for (int i = 0; i < blPrpJcredit.getSize(); i++) {
            prpJcreditSchema = new PrpJcreditSchema();
            prpJcreditSchema.setPolicyNo(blPrpJcredit.getArr(i).getPolicyNo());
            prpJcreditSchema.setPayNo(blPrpJcredit.getArr(i).getPayNo());
            prpJcreditSchema.setPayDate(blPrpJcredit.getArr(i).getPayDate());
            prpJcreditSchema.setBankCode(istrBankCode);
            prpJcreditSchema.setSerialCode(blPrpJcredit.getArr(i).getSerialCode());
            prpJcreditSchema.setPayRefFee(blPrpJcredit.getArr(i).getPayRefFee());
            prpJcreditSchema.setVoucherNo(blPrpJcredit.getArr(i).getVoucherNo());
            prpJcreditList.add(prpJcreditSchema);
        }
        map.put(prpJcreditList, "");
        return map;
    }
    /**
     * @description:查询实收数据
     * @param istrBankCode
     * @param iPayDte_S
     * @param iPayDte_E
     * @return Map
     * @throws Exception
     * @author Administrator
     */
    public Map query(String istrBankCode,String iPayDte_S,String iPayDte_E) throws Exception {
        DbPool dbpool = new DbPool();
        Map map = new HashMap();
        String strDataSource = SysConfig.CONST_PAYMENTDATASOURCE;
        
        dbpool.open(strDataSource);
        try {
            map = query(dbpool, istrBankCode, iPayDte_S, iPayDte_E);
        } catch (Exception e) {
            
            throw e;
        } finally {
            dbpool.close();
        }
        return map;
    }
    
    /**
     * @description:日期格式化
     * @param idate
     * @return String
     * @throws Exception
     * @author genghaijun-wb
     */
    public String dateFormat(String idate)throws Exception{
        SimpleDateFormat format_T = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format_D = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String strDate = "";
        try {
            if(idate.length()>6&&idate.length()<11){
                date = format_D.parse(idate);
                strDate = format_D.format(date);
                
            }else if(idate.length()>11){
                date = format_T.parse(idate);
                strDate = format_T.format(date);                
            }
        } catch (Exception e) {
            throw e;
        }
        return strDate;
    }
    
    /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
        String time= "2012-8-8";
        BLPrpJcredit blPrpJcredit = new  BLPrpJcredit();
        try {
            time = blPrpJcredit.dateFormat(time);
        } catch (Exception e) {
            
        }
    }        
}
