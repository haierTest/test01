package com.sp.prpall.blsvr.jf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nutz.ssdb4j.SSDB2m;
import org.nutz.ssdb4j.spi.Response;

import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPrpCmain;
import com.sp.prpall.dbsvr.jf.DBPrpCBackUpDataUP;
import com.sp.prpall.jfcd.cb.CFeedBack;
import com.sp.prpall.schema.PrpCBackUpDataUPSchema;
import com.sp.sysframework.reference.AppConfig;
import com.sp.thirdparty.claim.util.DateTime;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;

/**
 * 定义数据库异地灾备中间表的BL类
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>@createdate 2014-08-25</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpCBackUpDataUP{
    private Vector schemas = new Vector();
    final Log logger = LogFactory.getLog(getClass());
	CFeedBack cFeedBack = new CFeedBack();
    /**
     * 构造函数
     */       
    public BLPrpCBackUpDataUP(){
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
     *增加一条PrpCBackUpDataUPSchema记录
     *@param iPrpCBackUpDataUPSchema PrpCBackUpDataUPSchema
     *@throws Exception
     */
    public void setArr(PrpCBackUpDataUPSchema iPrpCBackUpDataUPSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpCBackUpDataUPSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *得到一条PrpCBackUpDataUPSchema记录
     *@param index 下标
     *@return 一个PrpCBackUpDataUPSchema对象
     *@throws Exception
     */
    public PrpCBackUpDataUPSchema getArr(int index) throws Exception
    {
     PrpCBackUpDataUPSchema prpCBackUpDataUPSchema = null;
       try
       {
        prpCBackUpDataUPSchema = (PrpCBackUpDataUPSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpCBackUpDataUPSchema;
     }
    /**
     *删除一条PrpCBackUpDataUPSchema记录
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
      DBPrpCBackUpDataUP dbPrpCBackUpDataUP = new DBPrpCBackUpDataUP();
      if (iLimitCount > 0 && dbPrpCBackUpDataUP.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCBackUpDataUP.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCBackUpDataUP WHERE " + iWherePart;
        schemas = dbPrpCBackUpDataUP.findByConditions(strSqlStatement, bindValues);
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
      DBPrpCBackUpDataUP dbPrpCBackUpDataUP = new DBPrpCBackUpDataUP();
      if (iLimitCount > 0 && dbPrpCBackUpDataUP.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCBackUpDataUP.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCBackUpDataUP WHERE " + iWherePart; 
        schemas = dbPrpCBackUpDataUP.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *带dbpool的save方法
     *@param 无
     *@return 无
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpCBackUpDataUP dbPrpCBackUpDataUP = new DBPrpCBackUpDataUP();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpCBackUpDataUP.setSchema((PrpCBackUpDataUPSchema)schemas.get(i));
      dbPrpCBackUpDataUP.insert(dbpool);
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
      try
      {
	    dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
     *带dbpool的upDate方法
     *@param 无
     *@return 无
     */
    public void upDate(DbPool dbpool) throws Exception
    {
      DBPrpCBackUpDataUP dbPrpCBackUpDataUP = new DBPrpCBackUpDataUP();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpCBackUpDataUP.setSchema((PrpCBackUpDataUPSchema)schemas.get(i));
      dbPrpCBackUpDataUP.update(dbpool);
      }
    }
    
    /**
     *不带dbpool的修改方法
     *@param 无
     *@return 无
     */
    public void upDate() throws Exception
    {
      DbPool dbpool = new DbPool();
    
      try
      {
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        upDate(dbpool);
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
     *@param policyNo XX号
     *@return 无
     */
    public void cancel(DbPool dbpool, String policyNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpCBackUpDataUP WHERE policyNo='" + policyNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * 不带dbpool的删除方法
     *@param policyNo XX号
     *@return 无
     */
    public void cancel(String policyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool, policyNo);
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
     *@param policyNo XX号
     *@return 无
     */
    public void getData(String policyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      getData(dbpool, policyNo);
      dbpool.close();
    }
    
      
    /**
     * 带dbpool根据主键获取数据
     *@param dbpool 连接池
     *@param policyNo XX号
     *@return 无
     */
    public void getData(DbPool dbpool, String policyNo) throws Exception
    {
       String strWherePart = "";
       strWherePart = "policyNo='" + policyNo + "'";
       query(dbpool, strWherePart, 0, null);
    }
    /**
     * 数据异地灾备
     * @throws Exception
     */
    public void DataBackUp()
    {
    	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date date=new Date();
    	Response resp;
		boolean backUpFlag = true;
		SSDB2m ssdb2m = null;
		SSDB2m ssdb2mSH = null;
    	String newDate=simpleDateFormat.format(date);
    	List listForPolicyNo=new ArrayList();
		BLPrpCBackUpDataUP blbackUpDataUP = new BLPrpCBackUpDataUP();
		DbPool dbpool = new DbPool();
		ArrayList list = new ArrayList();
		DBPrpCBackUpDataUP dbPrpCBackUpDataUP = null;
		String sql = "  Status in ('0','2') and DataType in ('N','U','H') and rownum <= 1000";
		
		String strPolicyNo = "";
		String strFlag1="";
		try {
			
			try {
				ssdb2m = getSSDB();
            } catch (Exception e) {
            	e.printStackTrace();
            	logger.info("同城SSDB所在服务器连接失败。失败原因：" + e.getMessage());
            }
            try {
            	ssdb2mSH = getSSDBForSH();
            } catch (Exception e) {
            	e.printStackTrace();
            	logger.info("异地SSDB所在服务器连接失败。失败原因：" + e.getMessage());
            }
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			for (int count = 0; count < 100; count++) {
				blbackUpDataUP.query(dbpool, sql, 0, list);
				int intSize = 0;
				intSize = blbackUpDataUP.getSize();
				if (intSize > 0) {
					for (int i = 0; i < intSize; i++) {
						try {
							strPolicyNo = blbackUpDataUP.getArr(i).getPolicyNo();
							strFlag1 = blbackUpDataUP.getArr(i).getFlag1();
							if (strFlag1 == null || "".equals(strFlag1)) {
								listForPolicyNo.add(strPolicyNo);
							}
							
							
							Map map = this.dataBackUpInfo(dbpool, strPolicyNo);
							
							byte[] arrByte = serialize(map);
							
							try {
								resp = ssdb2m.hset("ZB", strPolicyNo, arrByte);
								if (!"ok".equals(resp.stat)) {
									backUpFlag = false;
									logger.info("XX" + strPolicyNo + "通过SSDB方式同城备份XX信息失败。");
								}
                            } catch (Exception e) {
                            	backUpFlag = false;
                            	e.printStackTrace();
                            	logger.info("XX" + strPolicyNo + "通过SSDB方式同城备份XX信息失败。");
                            }
							
							try {
								resp = ssdb2mSH.hset("ZB", strPolicyNo, arrByte);
								if (!"ok".equals(resp.stat)) {
									backUpFlag = false;
									logger.info("XX" + strPolicyNo + "通过SSDB方式异地备份XX信息失败。"); 
								}
							} catch (Exception e) {
								backUpFlag = false;
								e.printStackTrace();
								logger.info("XX" + strPolicyNo + "通过SSDB方式异地备份XX信息失败。"); 
							}
							
							if(backUpFlag){
								dbpool.beginTransaction();
								dbPrpCBackUpDataUP = new DBPrpCBackUpDataUP();
								dbPrpCBackUpDataUP.setSchema(blbackUpDataUP.getArr(i));
								dbPrpCBackUpDataUP.setStatus("1");
								dbPrpCBackUpDataUP.setFlag1(new ChgDate().getCurrentTime("yyyy-MM-dd HH:mm:ss"));
								dbPrpCBackUpDataUP.update(dbpool);
								dbpool.commitTransaction();
							}
						} catch (Exception e) {
							try {
								dbpool.rollbackTransaction();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
						
					}
				}
			}
			if (listForPolicyNo.size() > 0) {
				byte[] arrByteForList = serialize(listForPolicyNo);
				try {
					resp = ssdb2m.hset("ZB", newDate, arrByteForList);	                
                } catch (Exception e) {
                	e.printStackTrace();
                }
                try {
                	resp = ssdb2mSH.hset("ZB", newDate, arrByteForList);                	
                } catch (Exception e) {
                	e.printStackTrace();
                }
			}
		} catch (UserException ue) {
			ue.printStackTrace();
			logger.info("XX" + strPolicyNo + "通过SSDB方式备份XX信息失败。失败原因：" + ue.getErrorMessage());
		} catch (Exception e) {
			e.printStackTrace();
			
			
			
			
			
			String strErrorMesg = cFeedBack.getErrorMessageDetails(e,false);
			logger.info("XX"+strPolicyNo+"通过SSDB方式备份XX信息失败。失败原因："+strErrorMesg);
		}finally{
			try {
				dbpool.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				ssdb2m.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				ssdb2mSH.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
    /**
     * 历史数据按天插入数据备份中间表
     * @throws Exception
     */
    public void HistoryDataBackUp()
    {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		ArrayList list=new ArrayList();
		String NowDate = simpleDateFormat.format(date);
		String createstring = null;
		BLPrpCmain blPrpCmain = new BLPrpCmain();
		String sql="enddate>Date'"+NowDate+"' order by inputdate asc";
		String inputdate=null;
		try {
			blPrpCmain.query(sql,0);
			inputdate = blPrpCmain.getArr(0).getInputDate();
		} catch (UserException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		DateTime earlyTime=new DateTime(inputdate);
		DateTime lateTime=new DateTime(date);
		int days=DateTime.intervalDay(earlyTime, 0, lateTime,0);
		for (int a = 1; a <= days; a++) {
			if (createstring == null) {
				createstring =inputdate;
			}
			try {
				this.BackupH(NowDate, createstring);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			createstring =earlyTime.addDay(1).toString();
			earlyTime=new DateTime(createstring);
		}
	}
    
    /**
     * 将Prpcmain表中的数据插入到中间表中
     * @throws Exception
     */
    public void BackupH(String nowDate, String inputDate) throws SQLException {
		
		final Log logger = LogFactory.getLog(getClass());
		int intSize = 0;

		DbPool dbpool = new DbPool();
		DBPrpCBackUpDataUP dbPrpCBackUpDataUP = new DBPrpCBackUpDataUP();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			String sql = "enddate>Date'" +nowDate+ "' and inputdate = Date'" + inputDate + "' ";
			BLPrpCmain blPrpCmain = new BLPrpCmain();
			blPrpCmain.query(sql,0);
			BLPrpCBackUpDataUP blPrpCBackUpDataUP = new BLPrpCBackUpDataUP();
			intSize = blPrpCmain.getSize();
			for (int n = 0; n < intSize; n++) {
				PrpCBackUpDataUPSchema schema = new PrpCBackUpDataUPSchema();
				String policyNo = blPrpCmain.getArr(n).getPolicyNo();
				schema.setPolicyNo(policyNo);
				schema.setCreateDate(nowDate);
				schema.setStatus("0");
				schema.setDataType("H");
				schema.setStartDate(blPrpCmain.getArr(n).getStartDate());
				schema.setEndDate(blPrpCmain.getArr(n).getEndDate());
				int size = dbPrpCBackUpDataUP.getInfo(dbpool, policyNo);
				if(size == 100){
					blPrpCBackUpDataUP.setArr(schema);
				}
			}
			blPrpCBackUpDataUP.save(dbpool);
			dbpool.commitTransaction(); 
			logger.info("插入" + intSize + "条数据成功！");
			dbPrpCBackUpDataUP.callErrorLogProcedureNew("DataBackupH", nowDate + "数据转入中间表成功" + blPrpCmain.getSize()
			        + "条！", "S");
		} catch (Exception e) {
			
			e.printStackTrace();
			dbpool.rollbackTransaction();
			CFeedBack cFeedBack = new CFeedBack();
			String strErrorMesg = cFeedBack.getErrorMessageDetails(e, false);
			logger.info("插入" + intSize + "条数据失败！" + "失败原因" + strErrorMesg);
			dbPrpCBackUpDataUP.callErrorLogProcedureNew("DataBackupH", nowDate + "数据转入中间表失败" + intSize + "条！", "F");
		} finally{
			dbpool.close();
		}
	}
    /**
     * 通过SSDB方式实现数据备份
     * @param dbpool
     * @param policyNo
     */
    public void dataBackUpForSSDB(DbPool dbpool, String policyNo) {
		BLPolicy blPolicy = new BLPolicy();
		Map map = null;
		Response resp;
		try {
			SSDB2m ssdb2m=getSSDB();
			map = blPolicy.getDataForXuLieHua(dbpool, policyNo);
			byte[] arrByte=serialize(map);
			resp = ssdb2m.hset("ZB",policyNo, arrByte);
			if (!"ok".equals(resp.stat)) {
				logger.info("XX"+policyNo+"通过SSDB方式备份XX信息失败。");
			}
		} catch (Exception e) {
			e.printStackTrace();
			String strErrorMesg = cFeedBack.getErrorMessageDetails(e,false);
			logger.info("XX"+policyNo+"通过SSDB方式备份XX信息失败.失败原因："+strErrorMesg);
		}
	}
	public Map dataBackUpInfo(DbPool dbpool, String policyNo) {
		BLPolicy blPolicy = new BLPolicy();
		Map map = null;
		Response resp;
		try {
			map = blPolicy.getDataForXuLieHua(dbpool, policyNo);
		} catch (Exception e) {
			e.printStackTrace();
			String strErrorMesg = cFeedBack.getErrorMessageDetails(e, false);
			logger.info("XX" + policyNo + "获取备份XX信息失败.失败原因：" + strErrorMesg);
		}
		return map;
	}
    
    
    public static SSDB2m getSSDB(){
    	String ip =AppConfig.get("sysconst.DataBackUp_IP").toString();
		int port =Integer.parseInt(AppConfig.get("sysconst.DataBackUp_PORT"));
		String ip_Bak =AppConfig.get("sysconst.DataBackUp_IPBAK").toString();
		int port_Bak =Integer.parseInt(AppConfig.get("sysconst.DataBackUp_PORTBAK"));
		SSDB2m ssdb2m = new SSDB2m(ip, port, ip_Bak, port_Bak,2000);
    	return ssdb2m;
    }
    
	
    /**
     * 将任意对象转化为字节数组
     * @param Object
     */
    public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				baos.close();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
  
  
    /**
     * 将字节数组反序列化生成对象
     * @param byte[]
     */
    public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois =null;
		try {
			
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				bais.close();
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	

	public static SSDB2m getSSDBForSH() {
		String ip = AppConfig.get("sysconst.DataBackUpSH_IP").toString();
		int port = Integer.parseInt(AppConfig.get("sysconst.DataBackUpSH_PORT"));
		String ip_Bak = AppConfig.get("sysconst.DataBackUpSH_IPBAK").toString();
		int port_Bak = Integer.parseInt(AppConfig.get("sysconst.DataBackUpSH_PORTBAK"));
		
		SSDB2m ssdb2m = new SSDB2m(ip, port, ip_Bak, port_Bak, 2000);
		return ssdb2m;
	}

	public String getSSDBValueTest(String PolicyNo, String Ip_Port) throws IOException {
		
		Response resp;
		Map map = null;
		SSDB2m ssdb2m = null;
		String strRISKCODE = "";
		DbPool dbpool = new DbPool();
		BLPolicy blPolicy = new BLPolicy();
		DBPrpCBackUpDataUP dbPrpCBackUpDataUP = new DBPrpCBackUpDataUP();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			String arrIp_Port[] = Ip_Port.split(":");
			String strIp = arrIp_Port[0];
			int intPort = Integer.parseInt(arrIp_Port[1]);
			ssdb2m = new SSDB2m(strIp, intPort, strIp, intPort, 2000);
			map = blPolicy.getDataForXuLieHua(dbpool, PolicyNo);
			byte[] arrByte = serialize(map);
			
			resp = ssdb2m.hset("ZB", PolicyNo, arrByte);
			if (!"ok".equals(resp.stat)) {
				System.out.println("XX" + PolicyNo + "通过SSDB方式备份XX信息失败。");
				logger.info("XX" + PolicyNo + "通过SSDB方式备份XX信息失败。");
			}
			int intReturn = dbPrpCBackUpDataUP.getInfo(dbpool, PolicyNo);
			if (intReturn == 0) {
				dbPrpCBackUpDataUP.setStatus("1");
				dbPrpCBackUpDataUP.setFlag1(new ChgDate().getCurrentTime("yyyy-MM-dd HH:mm:ss"));
				dbPrpCBackUpDataUP.update();
			}
			
			resp = ssdb2m.hget("ZB", PolicyNo);
			ArrayList<byte[]> asd = resp.datas;
			Map returnMap = (Map) this.unserialize(asd.get(0));
			List list = (ArrayList) returnMap.get("PrpCmain");
			Map PrpCmainMap = (Map) list.get(0);
			String strReturnPolicyNo1 = PrpCmainMap.get("POLICYNO").toString();
			strRISKCODE = PrpCmainMap.get("RISKCODE").toString();
			System.out.println(strRISKCODE + "**************" + strReturnPolicyNo1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ssdb2m.close();
			try {
	            dbpool.close();
            } catch (SQLException e) {
	            e.printStackTrace();
            }
		}
		return strRISKCODE;
	}
	
	/**
	 * @description:根据主键获取SSDB的内容
	 * @param PolicyNo
	 * @param Ip_Port
	 * @return
	 * @throws IOException
	 * @author genghaijun-wb
	 */
	public String getSSDBValue(String PolicyNo, String Ip_Port) throws IOException {
		
		Response resp;
		Map map = null;
		SSDB2m ssdb2m = null;
		String strRISKCODE = "";
		DbPool dbpool = new DbPool();
		BLPolicy blPolicy = new BLPolicy();
		DBPrpCBackUpDataUP dbPrpCBackUpDataUP = new DBPrpCBackUpDataUP();
		try {
			
			String arrIp_Port[] = Ip_Port.split(":");
			String strIp = arrIp_Port[0];
			int intPort = Integer.parseInt(arrIp_Port[1]);
			ssdb2m = new SSDB2m(strIp, intPort, strIp, intPort, 2000);			
			
			resp = ssdb2m.hget("ZB", PolicyNo);
			ArrayList<byte[]> asd = resp.datas;
			Map returnMap = (Map) this.unserialize(asd.get(0));
			List list = (ArrayList) returnMap.get("PrpCmain");
			Map PrpCmainMap = (Map) list.get(0);
			String strReturnPolicyNo1 = PrpCmainMap.get("POLICYNO").toString();
			strRISKCODE = PrpCmainMap.get("RISKCODE").toString();
			System.out.println(strRISKCODE + "**************" + strReturnPolicyNo1);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ssdb2m.close();
		}
		return strRISKCODE;
	}
}
