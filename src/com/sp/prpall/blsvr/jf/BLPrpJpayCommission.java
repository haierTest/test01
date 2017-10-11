package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.prpall.dbsvr.jf.DBPrpJpayCommission;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefMain;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefRec;
import com.sp.prpall.dbsvr.jf.DBPrpJplanCommission;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFee;
import com.sp.prpall.dbsvr.misc.DBPrpCommission;
import com.sp.prpall.interf.Visa;
import com.sp.prpall.pubfun.Bill;
import com.sp.prpall.schema.PrpJpayCommissionSchema;
import com.sp.prpall.schema.PrpJpayRefMainSchema;
import com.sp.prpall.schema.PrpJpayRefRecSchema;
import com.sp.prpall.schema.PrpJplanCommissionSchema;
import com.sp.prpall.schema.PrpJplanFeeSchema;
import com.sp.utiall.dbsvr.DBPrpDagent;
import com.sp.utiall.dbsvr.DBPrpDcode;
import com.sp.utiall.dbsvr.DBPrpDrisk;
import com.sp.utiall.dbsvr.DBPrpDuser;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

public class BLPrpJpayCommission {

	private Vector schemas = new Vector();
	
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * ���캯��
	 */
	public BLPrpJpayCommission() {
		
	}

	/**
	 * ��ʼ����¼
	 * 
	 * @param ��
	 * @return ��
	 * @throws Exception
	 */
	public void initArr() throws Exception {
		schemas = new Vector();
	}

	/**
	 * ����һ��PrpJpayCommissionSchema��¼
	 * 
	 * @param iPrpJpayCommissionSchema
	 *            PrpJpayCommissionSchema
	 * @throws Exception
	 */
	public void setArr(PrpJpayCommissionSchema iPrpJpayCommissionSchema)
			throws Exception {
		try {
			schemas.add(iPrpJpayCommissionSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �õ�һ��PrpJpayCommissionSchema��¼
	 * 
	 * @param index
	 *            �±�
	 * @return һ��PrpJplanFeeSchema����
	 * @throws Exception
	 */
	public PrpJpayCommissionSchema getArr(int index) throws Exception {
		PrpJpayCommissionSchema prpJpayCommissionSchema = null;
		try {
			prpJpayCommissionSchema = (PrpJpayCommissionSchema) this.schemas
					.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJpayCommissionSchema;
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
	 * @param dbpool
	 *            ȫ�ֳ�
	 * @param iWherePart
	 *            ��ѯ����(���������־�)
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
	 * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
	 * 
	 * @param dbpool
	 *            ȫ�ֳ�
	 * @param iWherePart
	 *            ��ѯ����(���������־�)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart) throws UserException,
			Exception {
		this.query(dbpool, iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
	 * 
	 * @param dbpool
	 *            ȫ�ֳ�
	 * @param iWherePart
	 *            ��ѯ����(���������־�)
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
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * 
	 * @param dbpool
	 *            ȫ�ֳ�
	 * @param iWherePart
	 *            ��ѯ����(���������־�)
	 * @param iLimitCount
	 *            ��¼������(iLimitCount=0: ������)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, int iLimitCount)
			throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpJpayCommission dbPrpJpayCommission = new DBPrpJpayCommission();
		if (iLimitCount > 0
				&& dbPrpJpayCommission.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJpayCommission.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJpayCommission WHERE "
					+ iWherePart;
			schemas = dbPrpJpayCommission.findByConditions(dbpool,
					strSqlStatement);
		}
	}
	
	/**
     *ʵ��XX��ѯ LingHaiYang 20051223
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void queryRec(String iWherePart,int iLimitCount) throws Exception
    {
      DBPrpJpayCommission dbPrpJpayCommission = new DBPrpJpayCommission();
      if (iLimitCount > 0 && dbPrpJpayCommission.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefRec.queryRec");
      }else
      {
        initArr();
        String strSqlStatement = "select ComCode,Handler1Code,AgentCode,"
                               + " RiskCode,Currency2,"
                               + " sum(PayRefFee) as PayRefFee "
                               + " from PrpJpayCommission "
                               + " where "
                               + iWherePart
                               + " group by ComCode,Handler1Code,AgentCode,RiskCode,Currency2";
        strSqlStatement += " order by ComCode,RiskCode ";

      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        PrpJpayCommissionSchema prpJpayCommissionSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJpayCommissionSchema = new PrpJpayCommissionSchema();
          prpJpayCommissionSchema.setComCode(resultSet.getString("ComCode"));
          prpJpayCommissionSchema.setHandler1Code(resultSet.getString("Handler1Code"));
          prpJpayCommissionSchema.setAgentCode(resultSet.getString("AgentCode"));
          prpJpayCommissionSchema.setRiskCode(resultSet.getString("RiskCode"));
          prpJpayCommissionSchema.setCurrency2(""+resultSet.getString("Currency2"));
          prpJpayCommissionSchema.setPayRefFee(""+resultSet.getDouble("PayRefFee"));
          this.setArr(prpJpayCommissionSchema);
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
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
	public void queryTranslateCode(String iWherePart,int iLimitCount) throws UserException, Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
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
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void queryTranslateCode(DbPool dbpool,String iWherePart,int iLimitCount) throws UserException,Exception
    {
      this.query(dbpool,iWherePart, iLimitCount);
      this.translateCode(dbpool,true);
    }
    
	/**
	 * ��dbpool��save����
	 * 
	 * @param ��
	 * @return ��
	 */
	public void save(DbPool dbpool) throws Exception {
		DBPrpJpayCommission dbPrpJpayCommission = new DBPrpJpayCommission();
		int i = 0;
		for (i = 0; i < schemas.size(); i++) {
			dbPrpJpayCommission.setSchema((PrpJpayCommissionSchema) schemas
					.get(i));
			dbPrpJpayCommission.insert(dbpool);
		}
	}

	/**
     *@author xushaojiang 20080115 ����֧������
     *@param iPrpJpayRefMainSchema
     *@return strPayRefNo ֧������
     *@throws Exception
     */
    public String genFeeInvioce(PrpJpayRefMainSchema iPrpJpayRefMainSchema) throws UserException,SQLException,Exception
    {
      BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
      DBPrpJplanCommission dbPrpJplanCommission = null;
      PrpJplanCommissionSchema prpJplanCommissionSchema = new PrpJplanCommissionSchema();
      String strPayRefNo = "";
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();

      

      
      Bill bill = new Bill();
      String billNo = "";
      double dblRealRefPremium = 0;
      int intYear = Integer.parseInt(iPrpJpayRefMainSchema.getPackageDate().substring(0,4));
      billNo = bill.getNo("prpjrefrec","0501",iPrpJpayRefMainSchema.getPackageUnit(),intYear,"00");
      
      if(billNo.equals("")){
      	throw new UserException(-98,-1167,"BLPrpJpayCommission.genFeeInvioce",
      			                 iPrpJpayRefMainSchema.getPackageUnit().substring(0,4)+ "0501" + intYear + "����û�г�ʼ��������ϵϵͳ����Ա��");
      }
      strPayRefNo = this.createPayRefNo(billNo,"0501",iPrpJpayRefMainSchema.getPackageUnit());

      DbPool dbpool = new DbPool();
      
      try {
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        
        for(int i = 0;i<this.getSize(); i++)
        {
          dbPrpJplanCommission = new DBPrpJplanCommission();
          
          dbPrpJplanCommission.getInfoForUpdate(dbpool,this.getArr(i).getCertiNo(),this.getArr(i).getSerialNo());
          if(Double.parseDouble(Str.chgStrZero(dbPrpJplanCommission.getPayRefFee()))!=0)
          {
            throw new UserException(-96,-1167,"","�Ѿ�����֧�����ˣ�");
          }
          if (dbPrpJplanCommission.getRealRefPremium()!=null &&!dbPrpJplanCommission.getRealRefPremium().equals(""))
        	  dblRealRefPremium += Str.round(Double.parseDouble(dbPrpJplanCommission.getRealRefPremium()),2);
          dbPrpJplanCommission.setPayRefFee(this.getArr(i).getPayRefFee());
          dbPrpJplanCommission.update(dbpool);
        }
        
        for(int i=0; i<this.getSize(); i++)
        {
          this.getArr(i).setPayRefNo(strPayRefNo);
          this.getArr(i).setPayRefNoFlag("1");
          this.getArr(i).setPayRefFlag("0");
          this.getArr(i).setPayItemType("0");
          this.getArr(i).setPayRefNoType("3");
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
        dbPrpJpayRefMain.setAgentCode(this.getArr(0).getAgentCode());
        
        dbPrpJpayRefMain.setRemark(dblRealRefPremium+"");
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
     * @author xushaojiang 20080115 ����֧������
     * @return strPayRefNo ֧������
     * @throws Exception
     */
    public String createPayRefNo(String iBillNo,String iRiskCode,String iComCode) throws Exception
    {
      
      String strPayRefNo = iBillNo.replaceAll(iComCode+iRiskCode,iComCode);
      return strPayRefNo;
    }
    
    /**
     *�ۺϲ�ѯ��ҳ��ѯ
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
             "SELECT distinct PrpJpayCommission.CertiNo,PrpJpayCommission.PolicyNo,PrpJpayCommission.InsuredName, " +
             "PrpJpayCommission.PlanDate,PrpJpayCommission.PayNo,PrpJpayCommission.CertiType, " +
			 "PrpJpayCommission.SerialNo,PrpJpayCommission.PayRefReason,PrpJpayCommission.PayRefTimes," +           
			 "PrpJpayCommission.PayRefNo,PrpJpayCommission.PayRefDate, " +
			 "PrpJpayCommission.Currency2,PrpJpayCommission.PayRefFee, " +
			 "PrpJpayCommission.CenterCode,PrpJpayCommission.VoucherNo " +
			 " FROM PrpJpayCommission WHERE " + strCondition +
             " ORDER BY CertiNo,PolicyNo,SerialNo) T " +
             "Where RowNum<=" + intEndNum + ") Where LineNum>" + intStartNum;
      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        PrpJpayCommissionSchema prpJpayCommissionSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJpayCommissionSchema = new PrpJpayCommissionSchema();
          prpJpayCommissionSchema.setCertiNo(resultSet.getString("CertiNo"));
          prpJpayCommissionSchema.setPolicyNo(resultSet.getString("PolicyNo"));
          prpJpayCommissionSchema.setInsuredName(resultSet.getString("InsuredName"));
          prpJpayCommissionSchema.setPlanDate("" + resultSet.getDate("PlanDate"));
          prpJpayCommissionSchema.setPayNo(resultSet.getString("PayNo"));
          prpJpayCommissionSchema.setPayRefNo(resultSet.getString("PayRefNo"));
          prpJpayCommissionSchema.setPayRefDate("" + resultSet.getDate("PayRefDate"));
          prpJpayCommissionSchema.setCurrency2(resultSet.getString("Currency2"));
          prpJpayCommissionSchema.setPayRefFee(resultSet.getString("PayRefFee"));
          prpJpayCommissionSchema.setCertiType(resultSet.getString("CertiType"));
          prpJpayCommissionSchema.setSerialNo(resultSet.getString("SerialNo"));
          prpJpayCommissionSchema.setPayRefReason(resultSet.getString("PayRefReason"));
          prpJpayCommissionSchema.setPayRefTimes(resultSet.getString("PayRefTimes"));
          prpJpayCommissionSchema.setCenterCode(resultSet.getString("CenterCode")); 
          prpJpayCommissionSchema.setVoucherNo(resultSet.getString("VoucherNo")); 
          this.setArr(prpJpayCommissionSchema);
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
    
	/**   ����PrpJplanCommission��������PrpJpayCommission
	 * @desc 
	 * @param PrpJplanCommissionSchema
	 *            
	 * @param prpJpayCommissionSchema
	 *            
	 * @throws Exception
	 */
    public PrpJpayCommissionSchema evaluate(PrpJplanCommissionSchema iSchema) throws Exception
    {
    	PrpJpayCommissionSchema prpJpayCommissionSchema = new PrpJpayCommissionSchema();
    	prpJpayCommissionSchema.setCertiType(iSchema.getCertiType());
    	prpJpayCommissionSchema.setCertiNo(iSchema.getCertiNo());
    	prpJpayCommissionSchema.setPolicyNo(iSchema.getPolicyNo());
    	prpJpayCommissionSchema.setSerialNo(iSchema.getSerialNo());
    	prpJpayCommissionSchema.setPayRefReason(iSchema.getPayRefReason());
    	prpJpayCommissionSchema.setPayRefTimes("1");
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
    	prpJpayCommissionSchema.setAgentName(iSchema.getAgentName());
    	prpJpayCommissionSchema.setHandler1Code(iSchema.getHandler1Code());
    	prpJpayCommissionSchema.setHandler1Name(iSchema.getHandler1Name());
    	prpJpayCommissionSchema.setHandlerCode(iSchema.getHandlerCode());
    	prpJpayCommissionSchema.setUnderWriteDate(iSchema.getUnderWriteDate());
    	prpJpayCommissionSchema.setCoinsFlag(iSchema.getCoinsFlag());
    	prpJpayCommissionSchema.setCoinsCode(iSchema.getCoinsCode());
    	prpJpayCommissionSchema.setCoinsName(iSchema.getCoinsName());
    	prpJpayCommissionSchema.setCoinsType(iSchema.getCoinsType());
    	prpJpayCommissionSchema.setOperateDate("");
    	prpJpayCommissionSchema.setOperatorCode("");
    	prpJpayCommissionSchema.setOperateUnit("");
    	prpJpayCommissionSchema.setCurrency2("");
		prpJpayCommissionSchema.setExchangeRate("1.00000000");
		prpJpayCommissionSchema.setPayRefFee("0.00");
		prpJpayCommissionSchema.setDisRate(iSchema.getDisRate());
		prpJpayCommissionSchema.setRealRefPremium(iSchema.getRealRefPremium());
		prpJpayCommissionSchema.setPayRefName("");
		prpJpayCommissionSchema.setPayRefFlag("0");
		prpJpayCommissionSchema.setPayRefNoFlag("1");
		prpJpayCommissionSchema.setRemark("");
		prpJpayCommissionSchema.setPayRefNo("");
		prpJpayCommissionSchema.setPayRefDate("");
		prpJpayCommissionSchema.setCarNatureCode(iSchema.getCarNatureCode());
		prpJpayCommissionSchema.setUseNatureCode(iSchema.getUseNatureCode());
		prpJpayCommissionSchema.setCarProperty(iSchema.getCarProperty());
		prpJpayCommissionSchema.setBusinessNature(iSchema.getBusinessNature());		
    	prpJpayCommissionSchema.setCenterCode(iSchema.getCenterCode());
		prpJpayCommissionSchema.setVoucherNo("");
		prpJpayCommissionSchema.setPayRefNoType("");
		prpJpayCommissionSchema.setRealPayRefNo("");
		prpJpayCommissionSchema.setMainRiskCode(iSchema.getMainRiskCode());
		prpJpayCommissionSchema.setPayFlag(iSchema.getPayFlag());
		prpJpayCommissionSchema.setWriteOffFlag(iSchema.getWriteOffFlag());
		prpJpayCommissionSchema.setMainPolicyFlag(iSchema.getMainPolicyFlag());
		prpJpayCommissionSchema.setAllinsFlag(iSchema.getAllinsFlag());
		prpJpayCommissionSchema.setPayItemType("");
		prpJpayCommissionSchema.setFlag("");
		prpJpayCommissionSchema.setExtendChar1("");
		prpJpayCommissionSchema.setExtendChar2("");
		prpJpayCommissionSchema.setExtendNum1("");
		prpJpayCommissionSchema.setExtendNum2("");
        
        prpJpayCommissionSchema.setToCenterCode(iSchema.getToCenterCode());
        prpJpayCommissionSchema.setToComCode(iSchema.getToComCode());
        prpJpayCommissionSchema.setToDesc(iSchema.getToDesc());
        prpJpayCommissionSchema.setToStatus(iSchema.getToStatus());
        prpJpayCommissionSchema.setToUserCode(iSchema.getToUserCode());
		return prpJpayCommissionSchema;
    }
  
    /**
     *������֧��������  
     *@param String[] PayRefNo ������֧������
     *@throws Exception
     */
    public boolean dropPayCommission(String[] PayRefNo) throws Exception
    {
      boolean result = true;
      String payRefNoCollection = "";
      DBPrpJpayCommission dbPrpJpayCommission = new DBPrpJpayCommission();
      PrpJpayCommissionSchema prpJpayCommissionSchema = null;

      BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      PrpJpayRefMainSchema prpJpayRefMainSchema = null;

      DBPrpJplanCommission dbPrpJplanCommission = new DBPrpJplanCommission();

      for (int i = 0; i < PayRefNo.length; i++)
      {
        payRefNoCollection = payRefNoCollection + ",'" + PayRefNo[i] + "'";
      }
      payRefNoCollection = "(" + payRefNoCollection.substring(1) + ")";
      String strSqlStatement =
          "SELECT * FROM PrpJpayCommission WHERE PayRefNo in " + payRefNoCollection;
      initArr();
      schemas = dbPrpJpayCommission.findByConditions(strSqlStatement);

      String strWherePart = "PayRefNo in" + payRefNoCollection;
      blPrpJpayRefMain.query(strWherePart,0);

      DbPool dbpool = new DbPool();
      
      try {
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        for (int i = 0; i < this.getSize(); i++)
        {
          prpJpayCommissionSchema = new PrpJpayCommissionSchema();
          prpJpayCommissionSchema = this.getArr(i);

          
          dbPrpJplanCommission.getInfo(dbpool,prpJpayCommissionSchema.getCertiNo(),prpJpayCommissionSchema.getSerialNo());
          dbPrpJplanCommission.setPayRefFee("0");
          dbPrpJplanCommission.update(dbpool);

          
          dbPrpJpayCommission.delete(dbpool,prpJpayCommissionSchema.getCertiNo(),prpJpayCommissionSchema.getSerialNo(),prpJpayCommissionSchema.getPayRefTimes());
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
     * @param code
     * @param isChinese
     * @param type
     *    1: ������ 2��ҵ��Ա 3���ո�ԭ��
     * @return String
     * @throws Exception
     */
    public String translateCode(String code,boolean isChinese,int type)
    throws UserException,Exception{
		DbPool dbpool = new DbPool();
		String result = "";
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			result = this.translateCode(dbpool, code, isChinese, type);
		} catch (UserException ue) {
			throw ue;
		} catch (Exception e) {
			throw e;
		} finally {
			dbpool.close();
		}
		return result;
	}
	
    /**
     * @param dbpool
     * @param code
     * @param isChinese
     * @param type
     *    1: ������ 2��ҵ��Ա 3���ո�ԭ��
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
        String strDataSource =SysConfig.CONST_DDCCDATASOURCE;

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
     * @param BLPrpJplanFee
     * @param isChinese
     * @return BLPrpJplanFee
     * @throws Exception
     */
    public void translateCode(DbPool dbpool,boolean isChinese) throws Exception{
        for(int i = 0;i < this.getSize();i++)
        {
        	
        	this.getArr(i).setPayRefReasonName(this.translateCode(dbpool,
        			this.getArr(i).getPayRefReason(),isChinese,3));
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
        }
    }
    
    /**
     * @desc ����һ��PrpJpayCommissionSchema����Ӧ��Ӧ����Ϣ�ļ�¼
     * @param PrpJplanCommissionSchema prpJplanCommissionSchema
     * @throws Exception
     */
    public PrpJpayCommissionSchema genSchema(PrpJplanCommissionSchema prpJplanCommissionSchema)
    throws Exception {
        PrpJpayCommissionSchema prpJpayCommissionSchema = null;
        prpJpayCommissionSchema = new PrpJpayCommissionSchema();
        prpJpayCommissionSchema.setCertiType(prpJplanCommissionSchema.getCertiType());
        prpJpayCommissionSchema.setCertiNo(prpJplanCommissionSchema.getCertiNo());
        prpJpayCommissionSchema.setPolicyNo(prpJplanCommissionSchema.getPolicyNo());
        prpJpayCommissionSchema.setSerialNo(prpJplanCommissionSchema.getSerialNo());
        prpJpayCommissionSchema.setPayRefReason(prpJplanCommissionSchema.getPayRefReason());
        prpJpayCommissionSchema.setPayRefTimes("1");
        prpJpayCommissionSchema.setClassCode(prpJplanCommissionSchema.getClassCode());
        prpJpayCommissionSchema.setRiskCode(prpJplanCommissionSchema.getRiskCode());
        prpJpayCommissionSchema.setAppliCode(prpJplanCommissionSchema.getAppliCode());
        prpJpayCommissionSchema.setAppliName(prpJplanCommissionSchema.getAppliName());
        prpJpayCommissionSchema.setInsuredCode(prpJplanCommissionSchema.getInsuredCode());
        prpJpayCommissionSchema.setInsuredName(prpJplanCommissionSchema.getInsuredName());
        prpJpayCommissionSchema.setStartDate(prpJplanCommissionSchema.getStartDate());
        prpJpayCommissionSchema.setEndDate(prpJplanCommissionSchema.getEndDate());
        prpJpayCommissionSchema.setValidDate(prpJplanCommissionSchema.getValidDate());
        prpJpayCommissionSchema.setPayNo(prpJplanCommissionSchema.getPayNo());
        prpJpayCommissionSchema.setCurrency1(prpJplanCommissionSchema.getCurrency1());
        prpJpayCommissionSchema.setPlanFee(prpJplanCommissionSchema.getPlanFee());
        prpJpayCommissionSchema.setPlanDate(prpJplanCommissionSchema.getPlanDate());
        prpJpayCommissionSchema.setComCode(prpJplanCommissionSchema.getComCode());
        prpJpayCommissionSchema.setMakeCom(prpJplanCommissionSchema.getMakeCom());
        prpJpayCommissionSchema.setAgentCode(prpJplanCommissionSchema.getAgentCode());
        prpJpayCommissionSchema.setAgentName(prpJplanCommissionSchema.getAgentName());
        prpJpayCommissionSchema.setHandler1Code(prpJplanCommissionSchema.getHandler1Code());
        prpJpayCommissionSchema.setHandler1Name(prpJplanCommissionSchema.getHandler1Name());
        prpJpayCommissionSchema.setHandlerCode(prpJplanCommissionSchema.getHandlerCode());
        prpJpayCommissionSchema.setUnderWriteDate(prpJplanCommissionSchema.getUnderWriteDate());
        prpJpayCommissionSchema.setCoinsFlag(prpJplanCommissionSchema.getCoinsFlag());
        prpJpayCommissionSchema.setCoinsCode(prpJplanCommissionSchema.getCoinsCode());
        prpJpayCommissionSchema.setCoinsName(prpJplanCommissionSchema.getCoinsName());
        prpJpayCommissionSchema.setCoinsType(prpJplanCommissionSchema.getCoinsType());
        prpJpayCommissionSchema.setDisRate(prpJplanCommissionSchema.getDisRate());
        prpJpayCommissionSchema.setRealRefPremium(prpJplanCommissionSchema.getRealRefPremium());
        prpJpayCommissionSchema.setCarNatureCode(prpJplanCommissionSchema.getCarNatureCode());
        prpJpayCommissionSchema.setUseNatureCode(prpJplanCommissionSchema.getUseNatureCode());
        prpJpayCommissionSchema.setCarProperty(prpJplanCommissionSchema.getCarProperty());
        
        prpJpayCommissionSchema.setBusinessNature(prpJplanCommissionSchema.getBusinessNature());
        prpJpayCommissionSchema.setCenterCode(prpJplanCommissionSchema.getCenterCode());
        prpJpayCommissionSchema.setMainRiskCode(prpJplanCommissionSchema.getMainRiskCode());
        prpJpayCommissionSchema.setPayFlag(prpJplanCommissionSchema.getPayFlag());
        prpJpayCommissionSchema.setWriteOffFlag(prpJplanCommissionSchema.getWriteOffFlag());
        prpJpayCommissionSchema.setMainPolicyFlag(prpJplanCommissionSchema.getMainPolicyFlag());
        prpJpayCommissionSchema.setAllinsFlag(prpJplanCommissionSchema.getAllinsFlag());
        
        prpJpayCommissionSchema.setToCenterCode(prpJplanCommissionSchema.getToCenterCode());
        prpJpayCommissionSchema.setToComCode(prpJplanCommissionSchema.getToComCode());
        prpJpayCommissionSchema.setToDesc(prpJplanCommissionSchema.getToDesc());
        prpJpayCommissionSchema.setToStatus(prpJplanCommissionSchema.getToStatus());
        prpJpayCommissionSchema.setToUserCode(prpJplanCommissionSchema.getToUserCode());
        
        prpJpayCommissionSchema.setCarNatureCode(prpJplanCommissionSchema.getCarNatureCode());
        return prpJpayCommissionSchema;
    }
    
    /**
     *ʵ��ʵ����ҳ��ѯ
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
             "select ComCode,Handler1Code,Handler1Name,AgentCode,AgentName,"
                               + " RiskCode,Currency2,"
                               + " sum(PayRefFee) as PayRefFee "
                               + " from PrpJpayCommission "
                               + " where "
                               + strCondition
                               + " group by ComCode,Handler1Code,Handler1Name,AgentCode,AgentName,RiskCode,Currency2) T " +
             "Where RowNum<=" + intEndNum + ") Where LineNum>" + intStartNum;
      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        PrpJpayCommissionSchema prpJpayCommissionSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJpayCommissionSchema = new PrpJpayCommissionSchema();
          prpJpayCommissionSchema.setComCode(resultSet.getString("ComCode"));
          prpJpayCommissionSchema.setHandler1Code(resultSet.getString("Handler1Code"));
          prpJpayCommissionSchema.setHandler1Name(resultSet.getString("Handler1Name"));
          prpJpayCommissionSchema.setAgentCode(resultSet.getString("AgentCode"));
          prpJpayCommissionSchema.setAgentName(resultSet.getString("AgentName"));
          prpJpayCommissionSchema.setRiskCode(resultSet.getString("RiskCode"));
          prpJpayCommissionSchema.setCurrency2(resultSet.getString("Currency2"));
          prpJpayCommissionSchema.setPayRefFee(resultSet.getString("PayRefFee"));
          this.setArr(prpJpayCommissionSchema);
          
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
     * ʵ��ʵ����ѯ���嵥�б� SangMingqian 20060301
     * @param iWherePart ��ѯ����
     * @throws Exception
     */
    public void RealInvoiceList(String iWherePart) throws Exception
    {
        String strSqlStatement = "";
        strSqlStatement = "SELECT distinct PrpJpayCommission.CertiType,PrpJpayCommission.CertiNo,"
                          + " PrpJpayCommission.SerialNo,PrpJpayCommission.PayRefReason,"
                          + " PrpJpayCommission.PolicyNo,PrpJpayCommission.RiskCode,"
                          + " PrpJpayCommission.StartDate,PrpJpayCommission.PayRefDate,"
                          + " PrpJpayCommission.InsuredName,PrpJpayCommission.Currency2,"
                          + " PrpJpayCommission.PayRefFee,PrpJpayCommission.CenterCode, "
                          + " PrpJpayRefMain.VoucherNo,PrpJpayCommission.PayNo "
						  + " FROM PrpJpayCommission,PrpJpayRefMain "
                          + " WHERE PrpJpayCommission.PayRefNo=PrpJpayRefMain.PayRefNo(+) AND "
                          + iWherePart;

      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        PrpJpayCommissionSchema prpJpayCommissionSchema = null;
        ResultSet resultSet = dbpool.query(strSqlStatement);
        while(resultSet.next())
        {
          prpJpayCommissionSchema = new PrpJpayCommissionSchema();
          prpJpayCommissionSchema.setCertiType(resultSet.getString("CertiType"));
          prpJpayCommissionSchema.setCertiNo(resultSet.getString("CertiNo"));
          prpJpayCommissionSchema.setSerialNo(resultSet.getString("SerialNo"));
          prpJpayCommissionSchema.setPayRefReason(resultSet.getString("PayRefReason"));
          prpJpayCommissionSchema.setPolicyNo(resultSet.getString("PolicyNo"));
          prpJpayCommissionSchema.setPayNo(resultSet.getString("PayNo"));
          prpJpayCommissionSchema.setRiskCode(resultSet.getString("RiskCode"));
          prpJpayCommissionSchema.setStartDate(""+resultSet.getDate("StartDate"));
          prpJpayCommissionSchema.setPayRefDate(""+resultSet.getDate("PayRefDate"));
          prpJpayCommissionSchema.setInsuredName(resultSet.getString("InsuredName"));
          prpJpayCommissionSchema.setCurrency2(resultSet.getString("Currency2"));
          prpJpayCommissionSchema.setPayRefFee(""+resultSet.getDouble("PayRefFee"));
          prpJpayCommissionSchema.setCenterCode(resultSet.getString("CenterCode")); 
          prpJpayCommissionSchema.setVoucherNo(resultSet.getString("VoucherNo")); 
          this.setArr(prpJpayCommissionSchema);
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
	 * @desc ��־���
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
