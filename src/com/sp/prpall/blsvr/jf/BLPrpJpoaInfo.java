package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sp.payment.blsvr.jf.BLPrpJpoaType;
import com.sp.prpall.dbsvr.jf.DBPrpJpoaInfo;
import com.sp.prpall.interf.Visa;
import com.sp.prpall.schema.PrpJpoaInfoSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����PrpJpoaInfo-POSˢ����֧Ʊ��Ϣ���BL��
 * <p>Copyright: Copyright (c) 2007</p>
 * <p> @createdate 2007-11-30</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJpoaInfo {
	private List schemas = new ArrayList();
	
	private BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
	
    /* BLPrpJplanFeePre��get���� */
    public BLPrpJplanFeePre getBLPrpJplanFeePre() {
        return this.blPrpJplanFeePre;
    }
    /* BLPrpJplanFeePre��set���� */
    public void setBLPrpJplanFeePre(BLPrpJplanFeePre iblPrpJplanFeePre) {
        this.blPrpJplanFeePre = iblPrpJplanFeePre;
    }
	/**
	 * ���캯��
	 */
	public BLPrpJpoaInfo() {
	}

	/**
	 * ��ʼ����¼
	 * 
	 * @param ��
	 * @return ��
	 * @throws Exception
	 */
	public void initArr() throws Exception {
		schemas = new ArrayList();
	}

	/**
	 * ����һ��PrpJpoaInfoSchema��¼
	 * 
	 * @param iPrpJpoaInfoSchema PrpJpoaInfoSchema
	 * @throws Exception
	 */
	public void setArr(PrpJpoaInfoSchema iPrpJpoaInfoSchema) throws Exception {
		try {
			schemas.add(iPrpJpoaInfoSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �õ�һ��PrpJpoaInfoSchema��¼
	 * 
	 * @param index �±�
	 * @return һ��PrpJpoaInfoSchema����
	 * @throws Exception
	 */
	public PrpJpoaInfoSchema getArr(int index) throws Exception {
		PrpJpoaInfoSchema prpJpoaInfoSchema = null;
		try {
			prpJpoaInfoSchema = (PrpJpoaInfoSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJpoaInfoSchema;
	}

	/**
	 * ɾ��һ��PrpJpoaInfoSchema��¼
	 * 
	 * @param index �±�
	 * @throws Exception
	 */
	public void remove(int index) throws Exception {
		try {
			this.schemas.remove(index);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �õ�schemas��¼��
	 * 
	 * @return schemas��¼��
	 * @throws Exception
	 */
	public int getSize() throws Exception {
		return this.schemas.size();
	}

	/**
	 * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
	 * 
	 * @param iWherePart ��ѯ����(���������־�)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart) throws UserException, Exception {
		this.query(iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * 
	 * @param iWherePart ��ѯ����(���������־�)
	 * @param iLimitCount ��¼������(iLimitCount=0: ������)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart, int iLimitCount) throws UserException,
			Exception {
		String strSqlStatement = "";
		DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
		if (iLimitCount > 0 && dbPrpJpoaInfo.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJpoaInfo.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJpoaInfo WHERE " + iWherePart;
			schemas = dbPrpJpoaInfo.findByConditions(strSqlStatement);
		}
	}

	/**
	 * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
	 * 
	 * @param dbpool ȫ�ֳ�
	 * @param iWherePart ��ѯ����(���������־�)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart) throws UserException,
			Exception {
		this.query(dbpool, iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * 
	 * @param dbpool ȫ�ֳ�
	 * @param iWherePart ��ѯ����(���������־�)
	 * @param iLimitCount ��¼������(iLimitCount=0: ������)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, int iLimitCount)
			throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
		if (iLimitCount > 0 && dbPrpJpoaInfo.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJpoaInfo.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJpoaInfo WHERE " + iWherePart;
			schemas = dbPrpJpoaInfo.findByConditions(dbpool, strSqlStatement);
		}
	}

	/**
	 * ��dbpool��save����
	 * 
	 * @param ��
	 * @return ��
	 */
	public void save(DbPool dbpool) throws Exception {
		DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
		int i = 0;
		for (i = 0; i < schemas.size(); i++) {
			dbPrpJpoaInfo.setSchema((PrpJpoaInfoSchema) schemas.get(i));
			dbPrpJpoaInfo.insert(dbpool);
		}
	}

	/**
	 * ����dbpool��XXXXX�淽��
	 * 
	 * @param ��
	 * @return ��
	 */
	public void save() throws Exception {
		DbPool dbpool = new DbPool();
        
        
        
		
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
		try {
			dbpool.beginTransaction();
			save(dbpool);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}

	/**
	 * ��dbpool��ɾ������
	 * 
	 * @param dbpool ���ӳ�
	 * @param poaCode ˢ���վݺ�/֧Ʊ�վݺ�
	 * @return ��
	 */
	public void cancel(DbPool dbpool, String poaCode) throws Exception {
		DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
		dbPrpJpoaInfo.delete(dbpool, poaCode);
	}

	/**
	 * ����dbpool��ɾ������
	 * 
	 * @param poaCode ˢ���վݺ�/֧Ʊ�վݺ�
	 * @return ��
	 */
	public void cancel(String poaCode) throws Exception {
		DbPool dbpool = new DbPool();
        
        
        
		
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
		try {
			dbpool.beginTransaction();
			cancel(poaCode);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}

	/**
	 * ��dbpool����poaCode��ȡ����
	 * 
	 * @param poaCode ˢ���վݺ�/֧Ʊ�վݺ�
	 * @return ��
	 */
	public PrpJpoaInfoSchema getData(String poaCode) throws Exception {
		DbPool dbpool = new DbPool();
		try {
            
            
            
			
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
            
			return getData(dbpool, poaCode);
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
	}

	/**
	 * ����dbpool����poaCode��ȡ����
	 * 
	 * @param dbpool ���ӳ�
	 * @param poaCode ˢ���վݺ�/֧Ʊ�վݺ�
	 * @return ��
	 */
	public PrpJpoaInfoSchema getData(DbPool dbpool, String poaCode) throws Exception {
		DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
		int result = dbPrpJpoaInfo.getInfo(poaCode);
		if( result == 0){
			return dbPrpJpoaInfo;
		}else{
			return null;
		}
	}
	
	/**
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * 
	 * @param iWherePart ��ѯ����(���������־�)
	 * @param iLimitCount ��¼������(iLimitCount=0: ������)
	 * @throws UserException
	 * @throws Exception
	 */
	public void queryWithPlanFeePre(String iWherePart, int iLimitCount) throws UserException,
			Exception {
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT distinct(PrpJpoaInfo.poaCode) PoaCode, ");
        /*ADD BY PENGJINLING 2013-4-15 payment-600 �Ϻ���������֧�� BEGIN*/
        buffer.append(" PrpJpoaInfo.PoaType PoaType, ");
        buffer.append(" PrpJpoaInfo.OpenDate OpenDate, ");
        /*ADD BY PENGJINLING 2013-4-15 payment-600 �Ϻ���������֧�� END*/
        buffer.append(" PrpJpoaInfo.AccBillNo AccBillNo, ");
        buffer.append(" PrpJpoaInfo.ValidDate ValidDate, ");
        buffer.append(" PrpJpoaInfo.AccDate AccDate, ");
        buffer.append(" PrpJpoaInfo.AccFlag AccFlag, ");
        buffer.append(" PrpJpoaInfo.Attribute2 Attribute2, ");
        buffer.append(" PrpJpoaInfo.TotalFee TotalFee, ");
        buffer.append(" PrpJpoaInfo.PayMethod PayMethod, ");
        buffer.append(" PrpJpoaInfo.PaymentStartDate PaymentStartDate, ");
        buffer.append(" PrpJpoaInfo.PaymentEndDate PaymentEndDate, ");
        buffer.append(" PrpJpoaInfo.AccBillAmount AccBillAmount ");
        buffer.append(" FROM PrpJpoaInfo,PrpJplanFeePre WHERE " );
        buffer.append(" PrpJpoaInfo.PoaCode = PrpJplanFeePre.PoaCode And ");
        buffer.append(iWherePart);
        buffer.append(" And ROWNUM < ");
        buffer.append(iLimitCount);
        buffer.append(" ORDER BY accdate ");
        DbPool dbpool = new DbPool();
        
        
        
        
        
		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
		if("0".equals(payment_switch)){
			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
		}else{
			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
		}
		
        
        ResultSet resultSet = null;
        try {
            PrpJpoaInfoSchema prpJpoaInfoSchema = null;
            resultSet = dbpool.query(buffer.toString());
            while (resultSet.next()) {
                prpJpoaInfoSchema = new PrpJpoaInfoSchema();
                prpJpoaInfoSchema.setPoaCode(resultSet.getString("PoaCode"));
                /*ADD BY PENGJINLING 2013-4-15 payment-600 �Ϻ���������֧�� BEGIN*/
                prpJpoaInfoSchema.setPoaType(resultSet.getString("PoaType"));
                prpJpoaInfoSchema.setOpenDate("" + resultSet.getTimestamp("OpenDate"));
                /*ADD BY PENGJINLING 2013-4-15 payment-600 �Ϻ���������֧�� END*/                
                prpJpoaInfoSchema.setAccBillNo(resultSet.getString("AccBillNo"));
                prpJpoaInfoSchema.setValidDate("" + resultSet.getTimestamp("validDate"));
                prpJpoaInfoSchema.setAccDate("" + resultSet.getTimestamp("accDate"));
                prpJpoaInfoSchema.setAccFlag(resultSet.getString("AccFlag"));
                prpJpoaInfoSchema.setAttribute2(resultSet.getString("Attribute2"));
                prpJpoaInfoSchema.setTotalFee("" + resultSet.getDouble("TotalFee"));
                prpJpoaInfoSchema.setPayMethod(resultSet.getString("payMethod"));
                prpJpoaInfoSchema.setPaymentStartDate("" + resultSet.getTimestamp("paymentStartDate"));
                prpJpoaInfoSchema.setPaymentEndDate("" + resultSet.getTimestamp("paymentEndDate"));
                prpJpoaInfoSchema.setAccBillAmount("" + resultSet.getDouble("accBillAmount"));
                this.setArr(prpJpoaInfoSchema);
            }
            
        } catch (Exception exception) {
            throw exception;
        } finally {
            if (resultSet != null){
                resultSet.close();
            }
            dbpool.close();
        }

    }
	
	/**
	 * XX�����վݴ�ӡ
	 * @param prpJpoaInfoSchema Ԥ�տ���Ϣ
	 * @throws UserException
	 * @throws SQLException
	 * @throws Exception
	 */
	public void printCheck(PrpJpoaInfoSchema prpJpoaInfoSchema)throws UserException,SQLException, Exception {
        DbPool dbpool = new DbPool();
        try {
            
            
            
            
        	
    		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
    		if("0".equals(payment_switch)){
    			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
    		}else{
    			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
    		}
    		
            
            dbpool.beginTransaction();
            printCheck(dbpool,prpJpoaInfoSchema);
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
	 * XX�����վݴ�ӡ
	 * @param prpJpoaInfoSchema Ԥ�տ���Ϣ
	 * @param dbpool ���ݿ����ӳ�
	 * @throws UserException
	 * @throws SQLException
	 * @throws Exception
	 */
	public void printCheck(DbPool dbpool,PrpJpoaInfoSchema prpJpoaInfoSchema)
			throws UserException, SQLException, Exception {
		Visa visa = new Visa();
		String strOperatorCode = prpJpoaInfoSchema.getOperatorCode();
		String strVsCode = prpJpoaInfoSchema.getRemark();
		String strVisaSerialNo = prpJpoaInfoSchema.getPosVoucherNo();
		visa.useTrans(strOperatorCode, strVsCode, strVisaSerialNo,
				prpJpoaInfoSchema.getBusinessNo());
		DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
		dbPrpJpoaInfo.getInfo(dbpool,prpJpoaInfoSchema.getPoaCode());
		dbPrpJpoaInfo.setPoaCode(prpJpoaInfoSchema.getPoaCode());
		dbPrpJpoaInfo.setRemark(strVsCode);
		dbPrpJpoaInfo.setPosVoucherNo(strVisaSerialNo);
		dbPrpJpoaInfo.update(dbpool);
		
	}
	
	/**
	 * �жϸ�֧Ʊ�����е�֧Ʊ��3�������Ƿ�ʹ��
	 * @param loginComCode ��½����
	 * @param accBillNo ֧Ʊ��
	 * @param accBillBank ֧Ʊ������
	 * @throws UserException
	 * @throws SQLException
	 * @throws Exception
	 */
	public void isAccBillNoRepeat( String accBillNo,
			String accBillBank) throws UserException, SQLException, Exception {
		DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
		DateTime now = new DateTime(DateTime.current(), DateTime.YEAR_TO_DAY);
		DateTime treeMonthAgo = now.addMonth(-3);
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT * FROM PrpJpoaInfo WHERE ");
		buffer.append(" AccBillNo = '" + accBillNo + "'");
		buffer.append(" AND AccBillBank = '" + accBillBank + "'");
		buffer.append(" AND OpenDate > to_date('" + treeMonthAgo.toString()
				+ "','YYYY-MM-DD')");
		int size = dbPrpJpoaInfo.findByConditions(buffer.toString()).size();
		if (size != 0) {
			throw new UserException(-98, -1003,
					"��ƱԤ���ύ BLPrpJpoaInfo.isAccBillNoRepeat()", "�÷�Ʊ��"
							+ accBillNo + "���������Ѿ�ʹ�ã������ٴ�ʹ�ã�");
		}
	}
	
	
	/**
	 * �Ϻ�POSˢ���Զ�����ȷ��
	 * @param strDate ƽ̨�����뷵������
	 * @throws UserException
	 * @throws SQLException
	 * @throws Exception
	 */
	public void autoSHPOSConfirm( String strDate) throws UserException, SQLException, Exception {
		
		  String strPoaCode = "";
	  	  String strOnLineTime = "2011-03-01 23:59:59";
	  	  String strSettleDateBegin = DateTime.getOffsetDate(new DateTime(strDate), -10).toString();
	  	  strSettleDateBegin = strSettleDateBegin + " 23:59:59";
		  StringBuffer sqlBuffer = new StringBuffer(100);
		  
	  	  BLPrpJpoaInfo blPrpJpoaInfo = null;
    	  BLPrpJpoaInfo blPrpJpoaInfoNew = null;
    	  BLPrpJplanFeePre blPrpJplanFeePre = null;
    	  DBPrpJpoaInfo dbPrpJpoaInfo = null;		  
		  BLPrpJpreFeeCharge blPrpJpareFeeCharge = null;
		  
		  StringBuffer poaType =new StringBuffer();		  
		  String poaTypeAll = "";
		  BLPrpJpoaType blPrpJpoaType = new BLPrpJpoaType();
		  List poaTypelist = blPrpJpoaType.getPoaTypeByOtherFlag();		  
		
		  for(int i=0;poaTypelist != null&&i<poaTypelist.size();i++){
			  if(i < poaTypelist.size()-1){
			      poaType = poaType.append("'"+(String)poaTypelist.get(i)+"',");
			  }else{
				  poaType = poaType.append("'"+(String)poaTypelist.get(i)+"'");
			  }
		  }				  		  		  
		  poaTypeAll = poaType.toString();
		  
		  
	      sqlBuffer.append(" PrpJpoaInfo.AccFlag = '0' ");	
	      
	      sqlBuffer.append(" and (PrpJpoaInfo.PayMethod in ('01','09') or (PrpJpoaInfo.PayMethod = '07' and PrpJpoaInfo.customno != '007')) ");
		  sqlBuffer.append(" and PrpJpoaInfo.paymentstartdate > to_date('" + strOnLineTime + "','YYYY-MM-DD HH24:MI:SS')");
		  sqlBuffer.append(" and PrpJpoaInfo.paymentstartdate > to_date('" + strSettleDateBegin + "','YYYY-MM-DD HH24:MI:SS')");	  
		  sqlBuffer.append(" and PrpJplanFeePre.CertiStatus !='3'");
		  
		  if(poaTypelist != null){
			  sqlBuffer.append(" and PrpJpoaInfo.poaType not in ("+poaTypeAll+")");
		  }
		  
		  String conditions = sqlBuffer.toString();
		 	  		  
	  	  blPrpJpoaInfo = new BLPrpJpoaInfo();		  
		  blPrpJpoaInfo.queryWithPlanFeePre(conditions,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
	      
		  for (int i = 0; i < blPrpJpoaInfo.getSize(); i++) {
			
		    strPoaCode = blPrpJpoaInfo.getArr(i).getPoaCode();
		    try{
		    	
		    	dbPrpJpoaInfo = new DBPrpJpoaInfo();
				dbPrpJpoaInfo.getInfo(strPoaCode);
		    	
		    	blPrpJpoaInfoNew = new BLPrpJpoaInfo();
				blPrpJpoaInfoNew.setArr(dbPrpJpoaInfo);
				
		    	blPrpJplanFeePre = new BLPrpJplanFeePre();
				blPrpJplanFeePre.query("PoaCode = '" + strPoaCode + "'");
				blPrpJpoaInfoNew.setBLPrpJplanFeePre(blPrpJplanFeePre);
				
				blPrpJpareFeeCharge = new BLPrpJpreFeeCharge();				
		        blPrpJpareFeeCharge.sendPolicyAccQueryRequest(blPrpJpoaInfoNew);
		    }
		    catch(Exception e){
		        DbPool dbpool = new DbPool();
		    	String logSql = 
		    		"insert into errorlog(errorid,logtime,errordetail,attribute2) " +
		    		"values('SHPOSAUTOCONFIRM',to_date(sysdate,'YYYY-MM-DD'),'poacode:"+ 
		    		strPoaCode +"����ȷ��ʧ��',to_char(SYSDATE,'YYYY-MM-DD HH24:MI:SS'))";
		        try {
                    
                    
                    
                    
		        	
            		String payment_switch = SysConfig.getProperty("PAYMENT_SWITCH_prpall");
            		if("0".equals(payment_switch)){
            			dbpool.open(SysConfig.CONST_PAYMENTDATASOURCE);
            		}else{
            			dbpool.open(SysConfig.CONST_PAYMENTNEWDATASOURCE);
            		}
            		
                    
		            dbpool.beginTransaction();
		            dbpool.executeUpdate(logSql);
		            dbpool.commitTransaction();
		        } catch(Exception exception) {
		        	e.printStackTrace();
		        	dbpool.rollbackTransaction();
		        }
		        finally {
		            dbpool.close();
		        }
		    }
	      }
	}
	
	
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@author ZhaoPengfei-GHQ 20110712
     *@param iWherePart ��ѯ����(���������־�)
     *@param bindValues ����İ󶨲���
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, ArrayList bindValues) throws UserException, Exception {
        String strSqlStatement = "";
        DBPrpJpoaInfo dbPrpJpoaInfo = new DBPrpJpoaInfo();
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpoaInfo WHERE " + iWherePart;
        schemas = dbPrpJpoaInfo.findByConditions(strSqlStatement, bindValues);
    }
	
    /**
     * ������
     * 
     * @param args
     *            �����б�
     */
	public static void main(String[] args) {
		
	}
}
