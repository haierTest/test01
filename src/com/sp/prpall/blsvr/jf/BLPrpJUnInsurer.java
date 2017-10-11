package com.sp.prpall.blsvr.jf;

import java.util.Vector;

import com.sp.prpall.dbsvr.jf.DBPrpJunInsurer;
import com.sp.prpall.schema.PrpJpayRefRecSchema;
import com.sp.prpall.schema.PrpJplanFeeSchema;
import com.sp.prpall.schema.PrpJunInsurerSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.schema.*;
import com.sp.utility.string.ChgDate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BLPrpJUnInsurer {
	private Vector schemas = new Vector();
	protected final Log logger = LogFactory.getLog(getClass());

	public void initArr() throws Exception {
		schemas = new Vector();
	}

	public void setArr(PrpJunInsurerSchema iPrpJunInsurerSchema)
			throws Exception {
		try {
			schemas.add(iPrpJunInsurerSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	public PrpJunInsurerSchema getArr(int index) throws Exception {
		PrpJunInsurerSchema prpJunInsurerSchema = null;
		try {
			prpJunInsurerSchema = (PrpJunInsurerSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJunInsurerSchema;
	}

	public void remove(int index) throws Exception {
		try {
			this.schemas.remove(index);
		} catch (Exception e) {
			throw e;
		}
	}

	public int getSize() throws Exception {
		return this.schemas.size();
	}

	public void query(String iWherePart) throws UserException, Exception {
		this.query(iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}

	public void query(String iWherePart, int iLimitCount) throws UserException,
			Exception {
		String strSqlStatement = "";
		DBPrpJunInsurer dbPrpJunInsurer = new DBPrpJunInsurer();
		if (iLimitCount > 0
				&& dbPrpJunInsurer.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJUnInsurer.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJUnInsurer WHERE "
					+ iWherePart;
			schemas = dbPrpJunInsurer.findByConditions(strSqlStatement);
		}
	}
	
	    /**
     * 在垫付赔案支付确认款完成时，向4个表中插入数据，在后面的垫付赔款冲销中使用
     * @param prpJpayRefRecSchema
     * @param dbpool
     * @throws UserException
     * @throws Exception
     */
    public void insert4Tables(DbPool dbpool,PrpJpayRefRecSchema prpJpayRefRecSchema
            ) throws UserException, Exception {
        
        BLPrpJpayRefMain blPrpJpayRefMain1 = new BLPrpJpayRefMain();
        BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
        PrpJpayRefMainSchema prpJpayRefMainSchema = new PrpJpayRefMainSchema();
        PrpJpayRefRecSchema prpJpayRefRecSchema1=new PrpJpayRefRecSchema();
        prpJpayRefRecSchema1.setSchema(prpJpayRefRecSchema);
      
        String certino = prpJpayRefRecSchema1.getCertiNo();
       
        

        prpJpayRefRecSchema1.setPayRefReason("P6C");
        prpJpayRefRecSchema1.setPayRefDate("");
        
        prpJpayRefRecSchema1.setIdentifyType("0");
        
        if(prpJpayRefRecSchema.getCertiNo().length()>19)
        {
        prpJpayRefRecSchema1.setPayRefNo(prpJpayRefRecSchema.getCertiNo().substring(0,19));
        }else
        {
        	prpJpayRefRecSchema1.setPayRefNo(prpJpayRefRecSchema.getCertiNo());
        	}

        
        prpJpayRefRecSchema1.setIdentifyNumber("1");
        
        prpJpayRefRecSchema1.setVoucherNo("");
        blPrpJpayRefRec.setArr(prpJpayRefRecSchema1);
        blPrpJpayRefRec.save(dbpool);
        

        
        BLPrpJUnInsurer blPrpJunInsurer = new BLPrpJUnInsurer();
        String iWherePart1 = "certino='" + certino + "'";
       
        blPrpJunInsurer.query(dbpool, iWherePart1);
        PrpJunInsurerSchema prpJunInsurerSchema = blPrpJunInsurer.getArr(0);
        String UnInsurerName = prpJunInsurerSchema.getUnInsurerName();
        String UnRegistNo = prpJunInsurerSchema.getUnRegistNo();
        String ReplaceFee = prpJunInsurerSchema.getReplaceFee();
        prpJpayRefMainSchema.setVisaCode(prpJpayRefRecSchema.getPolicyNo()); 
        prpJpayRefMainSchema.setSerialNo("1"); 
        prpJpayRefMainSchema.setVisaSerialNo(prpJpayRefRecSchema.getVisaSerialNo());
        if(prpJpayRefRecSchema.getCertiNo().length()>19){
        prpJpayRefMainSchema.setPayRefNo(prpJpayRefRecSchema.getCertiNo().substring(0,19));
        }else prpJpayRefMainSchema.setPayRefNo(prpJpayRefRecSchema.getCertiNo());
        prpJpayRefMainSchema.setPayRefNoType("9"); 
       
       
        prpJpayRefMainSchema.setPayRefName(UnInsurerName); 
        prpJpayRefMainSchema.setRemark(prpJpayRefRecSchema
                .getInsuredName()+"$"+UnRegistNo); 
        prpJpayRefMainSchema.setCurrency2(prpJpayRefRecSchema.getCurrency1()); 
        prpJpayRefMainSchema.setPayRefFee(ReplaceFee); 
        prpJpayRefMainSchema.setPackageUnit(prpJpayRefRecSchema.getComCode());
        prpJpayRefMainSchema.setCenterCode(prpJpayRefRecSchema.getCenterCode());
        blPrpJpayRefMain1.setArr(prpJpayRefMainSchema);

        blPrpJpayRefMain1.save(dbpool);
        

        

        PrpJplanFeeSchema prpJplanFeeSchema1 = new PrpJplanFeeSchema();
        BLPrpJplanFee blPrpJplanfee = new BLPrpJplanFee();
        prpJplanFeeSchema1.setRealPayRefFee("0");
        prpJplanFeeSchema1.setAccBookCode("");
        prpJplanFeeSchema1.setAccBookType("");
        prpJplanFeeSchema1.setCenterCode(prpJpayRefRecSchema.getCenterCode());
        prpJplanFeeSchema1.setBusinessNature(prpJpayRefRecSchema.getBusinessNature());
        prpJplanFeeSchema1.setBranchCode("");
        
        prpJplanFeeSchema1.setVoucherNo("0");
        prpJplanFeeSchema1.setFlag("00");
        
        prpJplanFeeSchema1.setYearMonth("");
        prpJplanFeeSchema1.setPayRefReason("P6C");
        prpJplanFeeSchema1.setAgentCode(prpJpayRefRecSchema.getAgentCode());
        prpJplanFeeSchema1.setAppliCode(prpJpayRefRecSchema.getAppliCode());
        prpJplanFeeSchema1.setAppliName(prpJpayRefRecSchema.getAppliName());
        prpJplanFeeSchema1.setCarNatureCode(prpJpayRefRecSchema
                .getCarNatureCode());
        prpJplanFeeSchema1.setCarProperty(prpJpayRefRecSchema.getCarProperty());
        prpJplanFeeSchema1.setCertiNo(prpJpayRefRecSchema.getCertiNo());
        prpJplanFeeSchema1.setCertiType(prpJpayRefRecSchema.getCertiType());
        prpJplanFeeSchema1.setClaimNo(prpJpayRefRecSchema.getClaimNo());
        prpJplanFeeSchema1.setClassCode(prpJpayRefRecSchema.getClassCode());
        prpJplanFeeSchema1.setCoinsCode(prpJpayRefRecSchema.getCoinsCode());
        prpJplanFeeSchema1.setCoinsFlag(prpJpayRefRecSchema.getCoinsFlag());
        prpJplanFeeSchema1.setCoinsName(prpJpayRefRecSchema.getCoinsName());
        prpJplanFeeSchema1.setCoinsType(prpJpayRefRecSchema.getCoinsType());
        prpJplanFeeSchema1.setComCode(prpJpayRefRecSchema.getComCode());
        prpJplanFeeSchema1.setContractNo(prpJpayRefRecSchema.getContractNo());
        prpJplanFeeSchema1.setCurrency1(prpJpayRefRecSchema.getCurrency1());
        prpJplanFeeSchema1.setEndDate(prpJpayRefRecSchema.getEndDate());
        prpJplanFeeSchema1
                .setExchangeRate(prpJpayRefRecSchema.getExchangeRate());
        prpJplanFeeSchema1
                .setHandler1Code(prpJpayRefRecSchema.getHandler1Code());
        prpJplanFeeSchema1.setHandlerCode(prpJpayRefRecSchema.getHandlerCode());
        prpJplanFeeSchema1.setInsuredCode(prpJpayRefRecSchema.getInsuredCode());
        prpJplanFeeSchema1.setInsuredName(prpJpayRefRecSchema.getInsuredName());
        prpJplanFeeSchema1.setMakeCom(prpJpayRefRecSchema.getMakeCom());
        prpJplanFeeSchema1.setPayNo(prpJpayRefRecSchema.getPayNo());
        prpJplanFeeSchema1.setPayRefFee(prpJpayRefRecSchema.getPayRefFee());
        prpJplanFeeSchema1.setPlanDate(prpJpayRefRecSchema.getPlanDate());
        prpJplanFeeSchema1.setPlanFeeCNY(prpJpayRefRecSchema.getPlanFee());
        prpJplanFeeSchema1.setPolicyNo(prpJpayRefRecSchema.getPolicyNo());
        prpJplanFeeSchema1.setRiskCode(prpJpayRefRecSchema.getRiskCode());
        prpJplanFeeSchema1.setSerialNo(prpJpayRefRecSchema.getSerialNo());
        prpJplanFeeSchema1.setStartDate(prpJpayRefRecSchema.getStartDate());
        prpJplanFeeSchema1.setUnderWriteDate(prpJpayRefRecSchema
                .getUnderWriteDate());
        prpJplanFeeSchema1.setUseNatureCode(prpJpayRefRecSchema
                .getUseNatureCode());
        prpJplanFeeSchema1.setValidDate(prpJpayRefRecSchema.getValidDate());
        prpJplanFeeSchema1.setPlanFee(prpJpayRefRecSchema.getPlanFee());
        blPrpJplanfee.setArr(prpJplanFeeSchema1);

        blPrpJplanfee.save(dbpool);
        

        
        prpJunInsurerSchema.setRemark("");
        BLPrpJUnInsurer blPrpJunInsurer1 = new BLPrpJUnInsurer();
        

        
        String strSQL = "update prpjuninsurer set payrefdate = sysdate "+       
	       " Where CertiType = " + "'" +prpJunInsurerSchema.getCertiType() + "'" +
	                            " And CertiNo = " + "'" +prpJunInsurerSchema.getCertiNo() + "'" +
	                            " And SerialNo = " + "'" +prpJunInsurerSchema.getSerialNo() + "'" +
	                            " And PayRefReason = " + "'" +prpJunInsurerSchema.getPayRefReason() + "'" +
	                            " And responsibility = " + "'" +prpJunInsurerSchema.getResponsibility() + "'";
       
        dbpool.update(strSQL);
        
        
        
        logger.info("prpJunInsurerSchema.certino="+prpJunInsurerSchema.getCertiNo());
        
        PrpJunInsurerSchema prpJunInsurerSchema1 = new PrpJunInsurerSchema();
        prpJunInsurerSchema1.setSchema(prpJunInsurerSchema); 
        prpJunInsurerSchema1.setPayRefDate("");
        prpJunInsurerSchema1.setPayRefReason("P6C");
        prpJunInsurerSchema1.setResponsibility("2");
        prpJunInsurerSchema1.setStandbyFlag2("");
        prpJunInsurerSchema1.setCertiNo(certino);
        blPrpJunInsurer1.setArr(prpJunInsurerSchema1);
        
        logger.info("prpJunInsurerSchema1.certino="+prpJunInsurerSchema1.getCertiNo());
        

        blPrpJunInsurer1.save(dbpool);

       

        

    }

    public void writeBackPrpjuninsure(DbPool dbpool,PrpJpayRefRecSchema prpJpayRefRecSchema
            ) throws UserException, Exception {
        BLPrpJUnInsurer blPrpJunInsurer = new BLPrpJUnInsurer();
        String iWherePart = "certino='" + prpJpayRefRecSchema.getCertiNo() + "'"
                + "and payrefreason='P6C'" + "and responsibility='2'";
        blPrpJunInsurer.query(dbpool, iWherePart);
        
            PrpJunInsurerSchema prpJunInsurerSchema = blPrpJunInsurer.getArr(0);
            
            
            String strSQL = "update prpjuninsurer set payrefdate = sysdate "+       
 	       " Where CertiType = " + "'" +prpJunInsurerSchema.getCertiType() + "'" +
 	                            " And CertiNo = " + "'" +prpJunInsurerSchema.getCertiNo() + "'" +
 	                            " And SerialNo = " + "'" +prpJunInsurerSchema.getSerialNo() + "'" +
 	                            " And PayRefReason = " + "'" +prpJunInsurerSchema.getPayRefReason() + "'" +
 	                            " And responsibility = " + "'" +prpJunInsurerSchema.getResponsibility() + "'";        
           dbpool.update(strSQL);
    }
	
    public void writeBackPrpjuninsure1(DbPool dbpool,PrpJpayRefRecSchema prpJpayRefRecSchema
    ) throws UserException, Exception {
     BLPrpJUnInsurer blPrpJunInsurer = new BLPrpJUnInsurer();
     String iWherePart = "certino='" + prpJpayRefRecSchema.getCertiNo() + "'"
        + "and payrefreason='P60'" + "and responsibility='0'";
   blPrpJunInsurer.query(dbpool, iWherePart);
    PrpJunInsurerSchema prpJunInsurerSchema = blPrpJunInsurer.getArr(0);
    String strSQL = "update prpjuninsurer set payrefdate = sysdate "+       
    " Where CertiType = " + "'" +prpJunInsurerSchema.getCertiType() + "'" +
                         " And CertiNo = " + "'" +prpJunInsurerSchema.getCertiNo() + "'" +
                         " And SerialNo = " + "'" +prpJunInsurerSchema.getSerialNo() + "'" +
                         " And PayRefReason = " + "'" +prpJunInsurerSchema.getPayRefReason() + "'" +
                         " And responsibility = " + "'" +prpJunInsurerSchema.getResponsibility() + "'";
  
   dbpool.update(strSQL);
}

	
	public void printQuery(String iWherePart, String strInsuredName,
			String strInsuredNameSign) throws UserException, Exception {
		this.printQuery(iWherePart, strInsuredName, strInsuredNameSign, Integer
				.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
	}

	public void printQuery(String iWherePart, String strInsuredName,
			String strInsuredNameSign, int iLimitCount) throws UserException,
			Exception {
		String strSqlStatement = "";
		DBPrpJunInsurer dbPrpJunInsurer = new DBPrpJunInsurer();
		if (iLimitCount > 0
				&& dbPrpJunInsurer.getCount2(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJUnInsurer.query");
		} else {
			initArr();
			strSqlStatement = " SELECT A.CERTITYPE,A.CERTINO,A.POLICYNO,A.SERIALNO,A.PAYREFREASON,A.CLAIMNO,"
					+ " A.UNPOLICYNO,A.UNREGISTNO,A.UNINSURERCODE,A.UNINSURERNAME, A.ARTICLECODE,"
					+ " A.UNACCOUNTCODE,A.Uninsuredcode,B.INSUREDNAME AS UNINSUREDNAME,A.RPCURRENCY,A.REPLACEFEE,"
					+ " A.OPERATEDATE,A.STANDBYFLAG1,A.STANDBYFLAG2 FROM PRPJUNINSURER A,PRPJPAYREFREC B "
					+ " WHERE  A.POLICYNO=B.POLICYNO AND A.SERIALNO=B.SERIALNO AND A.CERTINO=B.CERTINO "
					+ " AND A.PAYREFREASON=B.PAYREFREASON AND A.CLAIMNO=B.CLAIMNO  AND payrefdate  IS NOT  NULL"
					+ iWherePart;
			schemas = dbPrpJunInsurer.findByConditions2(strSqlStatement,
					strInsuredName, strInsuredNameSign);
		}

	}

	public void save(DbPool dbpool) throws Exception {
		DBPrpJunInsurer dbPrpJunInsurer = new DBPrpJunInsurer();

		int i = 0;

		for (i = 0; i < schemas.size(); i++) {
			dbPrpJunInsurer.setSchema((PrpJunInsurerSchema) schemas.get(i));
			dbPrpJunInsurer.insert(dbpool);
		}
	}

	public void save() throws Exception {
		DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.beginTransaction();
			save(dbpool);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}
    public void query(DbPool dbpool, String iWherePart) throws UserException,
            Exception {
        this.query(dbpool, iWherePart, Integer.parseInt(SysConfig.getProperty(
                "QUERY_LIMIT_COUNT").trim()));
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
        DBPrpJunInsurer dbPrpJunInsurer = new DBPrpJunInsurer();
        if (iLimitCount > 0
                && dbPrpJunInsurer.getCount(iWherePart) > iLimitCount) {
            throw new UserException(-98, -1003, "BLPrpJUnInsurer.query");
        } else {
            initArr();
            strSqlStatement = " SELECT * FROM PrpJuninsurer WHERE " + iWherePart;
            schemas = dbPrpJunInsurer.findByConditions(dbpool, strSqlStatement);
        }
    }
	/**
	 * 主函数
	 * 
	 * @param args
	 *            参数列表
	 */
	public static void main(String[] args) {
		
	}
}
