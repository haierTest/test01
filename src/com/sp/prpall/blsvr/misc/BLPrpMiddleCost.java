package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.blsvr.cb.BLPrpCcoins;
import com.sp.prpall.blsvr.cb.BLPrpCfee;
import com.sp.prpall.blsvr.cb.BLPrpCitemKind;
import com.sp.prpall.blsvr.pg.BLPrpPcoins;
import com.sp.prpall.blsvr.pg.BLPrpPfee;
import com.sp.prpall.blsvr.pg.BLPrpPitemKind;
import com.sp.prpall.blsvr.pg.BLPrpPmain;
import com.sp.prpall.blsvr.pg.BLPrpPtext;
import com.sp.prpall.blsvr.tb.BLPrpTcoins;
import com.sp.prpall.blsvr.tb.BLPrpTfee;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.misc.DBPrpMiddleCost;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;
import com.sp.prpall.dbsvr.pg.DBPrpPmain;
import com.sp.prpall.dbsvr.tb.DBPrpTmain;
import com.sp.prpall.pubfun.Bill;
import com.sp.prpall.schema.PrpCitemKindSchema;
import com.sp.prpall.schema.PrpMiddleCostSchema;
import com.sp.prpall.schema.PrpPitemKindSchema;
import com.sp.prpall.schema.PrpPtextSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Money;
import com.sp.utility.string.Str;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * ����prpMiddleCost-�м�ɱ���Ϣ���BL��
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>@createdate 2005-08-09</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpMiddleCost{
    private Vector schemas = new Vector();
    
    protected final Log logger = LogFactory.getLog(getClass());
    
    /**
     * ���캯��
     */
    public BLPrpMiddleCost(){
    }

    /**
     *��ʼ����¼
     *@param ��
     *@return ��
     *@throws Exception
     */
    public void initArr() throws Exception
    {
       schemas = new Vector();
     }
    /**
     *����һ��PrpMiddleCostSchema��¼
     *@param iPrpMiddleCostSchema PrpMiddleCostSchema
     *@throws Exception
     */
    public void setArr(PrpMiddleCostSchema iPrpMiddleCostSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpMiddleCostSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpMiddleCostSchema��¼
     *@param index �±�
     *@return һ��PrpMiddleCostSchema����
     *@throws Exception
     */
    public PrpMiddleCostSchema getArr(int index) throws Exception
    {
     PrpMiddleCostSchema prpMiddleCostSchema = null;
       try
       {
        prpMiddleCostSchema = (PrpMiddleCostSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpMiddleCostSchema;
     }
    /**
     *ɾ��һ��PrpMiddleCostSchema��¼
     *@param index �±�
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
     *�õ�schemas��¼��
     *@return schemas��¼��
     *@throws Exception
     */
    public int getSize() throws Exception
    {
        return this.schemas.size();
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart) throws UserException,Exception
    {
       this.query(iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpMiddleCost dbPrpMiddleCost = new DBPrpMiddleCost();
      if (iLimitCount > 0 && dbPrpMiddleCost.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpMiddleCost.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpMiddleCost WHERE " + iWherePart;
        schemas = dbPrpMiddleCost.findByConditions(strSqlStatement);
      }
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpMiddleCost dbPrpMiddleCost = new DBPrpMiddleCost();
      if (iLimitCount > 0 && dbPrpMiddleCost.getCount(dbpool,iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpMiddleCost.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpMiddleCost WHERE " + iWherePart;
        schemas = dbPrpMiddleCost.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpMiddleCost dbPrpMiddleCost = new DBPrpMiddleCost();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpMiddleCost.setSchema((PrpMiddleCostSchema)schemas.get(i));
        dbPrpMiddleCost.insert(dbpool);
      }
    }

    /**
     *����dbpool��XXXXX�淽��
     *@param ��
     *@return ��
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
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param CertiNo iCertiNo
     *@return ��
     */
    public void cancel(DbPool dbpool,String iCertiNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpMiddleCost WHERE CertiNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iCertiNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }

    /**
     * ����dbpool��ɾ������
     *@param CertiNo iCertiNo
     *@return ��
     */
    public void cancel(String iCertiNo ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,iCertiNo);
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
     * ��dbpool����CertiNo��ȡ����
     *@param CertiNo iCertiNo
     *@return ��
     */
    public void getData(String iCertiNo) throws Exception
    {
        DbPool dbpool = new DbPool();

        try {
  		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  		getData(dbpool, iCertiNo);
  	} catch (Exception e) {
  		
  	}finally {      
  	dbpool.close();
      }

     }

    /**
     * ����dbpool����CertiNo��ȡ����
     *@param dbpool ���ӳ�
     *@param CertiNo iCertiNo
     *@return ��
     */
    public void getData(DbPool dbpool,String iCertiNo) throws Exception
    {
        
        
        
        
        String strWherePart = " CertiNo=? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iCertiNo);
        query(dbpool, strWherePart, arrWhereValue, 0);
        
  }
  
  /**
   *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
   *@author wangchuanzhong 20100602
   *@param dbpool      ȫ�ֳ�
   *@param iWherePart  ��ѯ����,�����������Ѱ󶨱�����ʽ���ʺŸ���������ֵ����һ��
   *@param iWhereValue ��ѯ�������ֶ�ֵ
   *@param iLimitCount ��¼������(iLimitCount=0: ������)
   *@throws UserException
   *@throws Exception
   */
  public void query(DbPool dbpool,String iWherePart,ArrayList iWhereValue, int iLimitCount) throws UserException,Exception
  {
      String strSqlStatement = "";
      DBPrpMiddleCost dbPrpMiddleCost = new DBPrpMiddleCost();
      if (iLimitCount > 0 && dbPrpMiddleCost.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
      {
          throw new UserException(-98,-1003,"BLPrpMiddleCost.query");
      }else
      {
          initArr();
          strSqlStatement = " SELECT * FROM PrpMiddleCost WHERE " + iWherePart;
          schemas = dbPrpMiddleCost.findByConditions(dbpool,strSqlStatement,iWhereValue);
      }
  }

    public void sumByCurrency(String iWherePart) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpMiddleCost dbPrpMiddleCost = new DBPrpMiddleCost();
      initArr();
      strSqlStatement = " SELECT SUM(DISFEE) AS DISFEE,CURRENCY  FROM PrpMiddleCost WHERE " + iWherePart+" GROUP BY CURRENCY";
      schemas = dbPrpMiddleCost.sumByCurrency(strSqlStatement);
    }

    /**
     * @author lijibin 2005-06-19
     * @desc �����м�ɱ���Ϣ
     * @param iCertiType
     * @param iCertiNo
     * @throws UserException
     * @throws Exception
     */
    public void createDisPremium(String iCertiType, String iCertiNo) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        this.createDisPremium(dbpool,iCertiType,iCertiNo);
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
     * @desc �����м�ɱ���Ϣ
     * @param iEndorseNo
     * @throws UserException
     * @throws Exception
     */
    public void createDisPremium(DbPool dbpool,String iCertiType,String iCertiNo) throws UserException,Exception
    {
      if(iCertiType.equals("P"))
        this.createPdisPremium(dbpool,iCertiNo);
      if(iCertiType.equals("E"))
        this.createEdisPremium(dbpool,iCertiNo);
    }

    /**
     * @author lijibin 2005-06-19
     * @desc ��XXXXXͨ�����Զ�����һ���м�ɱ�������
     * @desc �м�ɱ�������Ϊ�㣬�Զ�����һ������
     * @param DBPool dbpool
     * @param String strPolicyNo
     * @throws UserException
     * @throws Exception
     */
    
    public void createPdisPremium(DbPool dbpool, String strPolicyNo)
    throws UserException,Exception {
        BLPrpPtext blPrpPtext = new BLPrpPtext();
        DBPrpCmain dbPrpCmain = new DBPrpCmain();
        DBPrpPmain dbPrpPmain = new DBPrpPmain();
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        PrpMiddleCostSchema prpMiddleCostSchema = new PrpMiddleCostSchema();
        PrpPtextSchema prpPtextSchema = null;
        
        BLPrpCcoins blPrpCcoins = new BLPrpCcoins();
        
        BLPrpMiddleCostDetail blPrpMiddleCostDetail = new BLPrpMiddleCostDetail();
        Bill bill = new Bill();
        ChgDate chgDate = new ChgDate();
        Money money = new Money();

        String strWhere = "";
        String strEndorseNo = "";
        String strCurrency1 = "";
        String strPtext = "";
        String[] arrPtext = {};

        double dblDisRate1 = 0;
        double dblDisFee1 = 0;
        double dblPremium1Sum = 0;
        double dblDisFee1Left = 0;
        
        double dblSelfRate1 = 100;
        

        int intMaxLength = 70;
        int intLineNo = 0;

        
        dbPrpCmain.getInfo(dbpool,strPolicyNo);

        
        strEndorseNo = bill.getNo("prpphead", strPolicyNo);
        dbPrpPmain = dbPrpCmain.evaluateFromCmainToPmain(dbPrpCmain, strEndorseNo);

        
        
        dblDisRate1 = Double.parseDouble(Str.chgStrZero(dbPrpCmain.getDisRate1()));
        if(dblDisRate1 == 0) {
            return;
        }
        
        
        dblSelfRate1 = Str.round(Double.parseDouble(Str.chgStrZero(blPrpCcoins.getSelfRate(dbpool,strPolicyNo,"2"))),4);
        

        
        
        
        
        
        
        strWhere = "SELECT Currency1,SUM(Premium1) AS Premium1 FROM PrpCfee"
                 + " WHERE PolicyNo='" + strPolicyNo + "'"
                 + " GROUP BY Currency1";
        ResultSet rs = dbpool.query(strWhere);
        while (rs.next()) {
            strCurrency1 = rs.getString("Currency1");
            dblPremium1Sum += rs.getDouble("Premium1");
        }
        rs.close();
        if(dblPremium1Sum == 0) {
            return;
        }

        
        
        
        dblPremium1Sum = Str.round(dblPremium1Sum,2);
        dblDisRate1 = Str.round(dblDisRate1,4);
        dblDisFee1 = dblPremium1Sum * dblDisRate1 / 100;
        
        
        dblDisFee1 = Str.round(Str.round(dblDisFee1, 8), 2);
        
        dblDisFee1 = Str.round(Str.round(dblDisFee1 * dblSelfRate1/100,4),2);
        
        
        dblDisFee1Left = dblPremium1Sum - dblDisFee1;
        dblDisFee1Left = Str.round(dblDisFee1Left, 2);
        
        prpMiddleCostSchema.setClassCode(dbPrpCmain.getClassCode());
        prpMiddleCostSchema.setRiskCode(dbPrpCmain.getRiskCode());
        prpMiddleCostSchema.setEndorseNo(strEndorseNo);
        prpMiddleCostSchema.setPolicyNo(strPolicyNo);
        prpMiddleCostSchema.setCertiNo(strPolicyNo);
        prpMiddleCostSchema.setCertiType("P");
        prpMiddleCostSchema.setDisRate(String.valueOf(dblDisRate1));
        prpMiddleCostSchema.setCurrency(strCurrency1);
        prpMiddleCostSchema.setDisFee(String.valueOf(dblDisFee1));
        prpMiddleCostSchema.setGenerateDate(chgDate.getCurrentTime("yyyy-MM-dd"));
        prpMiddleCostSchema.setGenerateTime(chgDate.getCurrentTime("HH:mm"));
        
        prpMiddleCostSchema.setSelfRate(String.valueOf(dblSelfRate1));
        
        this.setArr(prpMiddleCostSchema);

        
        dbPrpPhead.setEndorseNo(strEndorseNo);
        dbPrpPhead.setPolicyNo(strPolicyNo);
        dbPrpPhead.setPrintNo("");
        dbPrpPhead.setClassCode(dbPrpCmain.getClassCode());
        dbPrpPhead.setRiskCode(dbPrpCmain.getRiskCode());
        
        dbPrpPhead.setEndorseTimes("0");
        dbPrpPhead.setMakeCom(dbPrpCmain.getMakeCom());
        dbPrpPhead.setCompensateNo("");
        dbPrpPhead.setInsuredCode(dbPrpCmain.getInsuredCode());
        dbPrpPhead.setInsuredName(dbPrpCmain.getInsuredName());
        dbPrpPhead.setLanguage(dbPrpCmain.getLanguage());
        dbPrpPhead.setPolicyType(dbPrpCmain.getPolicyType());
        dbPrpPhead.setEndorType("56");
        dbPrpPhead.setEndorDate(prpMiddleCostSchema.getGenerateDate());
        dbPrpPhead.setValidDate(dbPrpCmain.getStartDate());
        dbPrpPhead.setValidHour(dbPrpCmain.getStartHour());
        dbPrpPhead.setHandlerCode(dbPrpCmain.getHandlerCode());
        dbPrpPhead.setHandler1Code(dbPrpCmain.getHandler1Code());
        dbPrpPhead.setApproverCode(dbPrpCmain.getApproverCode());
        dbPrpPhead.setUnderWriteCode(dbPrpCmain.getUnderWriteCode());
        dbPrpPhead.setUnderWriteName(dbPrpCmain.getUnderWriteName());
        dbPrpPhead.setOperatorCode(dbPrpCmain.getOperatorCode());
        dbPrpPhead.setInputDate(prpMiddleCostSchema.getGenerateDate());
        dbPrpPhead.setInputHour(chgDate.getCurrentTime("HH"));
        dbPrpPhead.setComCode(dbPrpCmain.getComCode());
        dbPrpPhead.setAgentCode(dbPrpCmain.getAgentCode());
        dbPrpPhead.setStatisticsYM(dbPrpCmain.getStatisticsYM());
        dbPrpPhead.setUnderWriteEndDate(prpMiddleCostSchema.getGenerateDate());
        
        dbPrpPhead.setUnderWriteFlag("1");
        dbPrpPhead.setFlag("");
        dbPrpPhead.setUpdaterCode("");
        dbPrpPhead.setUpdateDate("");
        dbPrpPhead.setUpdateHour("");

        
        
        dbPrpCmain.setEndorseTimes(String.valueOf(Integer.parseInt(dbPrpCmain.getEndorseTimes()) + 1));
        blPrpMiddleCostDetail.createPdisPremiumDetail(dbpool,this,dbPrpCmain);

        if(this.getSize()>0)
        {
          dblDisFee1 = Double.parseDouble(Str.chgStrZero(this.getArr(0).getDisFee()));
          dblDisFee1Left = Str.round(dblPremium1Sum - dblDisFee1,2);
        }
        
        
        /* modify by zhulei 20050901 begin reason������س����� */
        strPtext = "    ����XX�˼�ǿ�˷�XXXXX�ܿأ���ʹ��XXXXX�̶ȵ��Ը��ƣ���˫��Э��ͬ�⣬��XX��������XX�Żݣ�\r\n ��"
                 + strCurrency1 + money.toAccount(String.valueOf(dblPremium1Sum)) + "Ԫ����Ϊ"
                 + strCurrency1 + money.toAccount(String.valueOf(dblDisFee1Left)) + "Ԫ������XX"
                 + strCurrency1 + money.toAccount(String.valueOf(dblDisFee1)) + "Ԫ����\r\n";
        /* modify by zhulei 20050901 end */
        arrPtext = (String[])Str.split(strPtext, intMaxLength);
        for (int i = 0; i < arrPtext.length; i++) {
            intLineNo++;
            prpPtextSchema = new PrpPtextSchema();
            prpPtextSchema.setEndorseNo(dbPrpPhead.getEndorseNo());
            prpPtextSchema.setPolicyNo(dbPrpPhead.getPolicyNo());
            prpPtextSchema.setLineNo(String.valueOf(intLineNo));
            prpPtextSchema.setEndorseText(arrPtext[i]);
            blPrpPtext.setArr(prpPtextSchema);
        }
        
        strPtext = "    ��XX�������������������䣬�ش���ע��";
        intLineNo++;
        prpPtextSchema = new PrpPtextSchema();
        prpPtextSchema.setEndorseNo(dbPrpPhead.getEndorseNo());
        prpPtextSchema.setPolicyNo(dbPrpPhead.getPolicyNo());
        prpPtextSchema.setLineNo(String.valueOf(intLineNo));
        prpPtextSchema.setEndorseText(strPtext);
        blPrpPtext.setArr(prpPtextSchema);


        dbPrpCmain.update(dbpool);
        dbPrpPhead.insert(dbpool);
        dbPrpPmain.insert(dbpool);
        blPrpPtext.save(dbpool);
        this.save(dbpool);
        blPrpMiddleCostDetail.save(dbpool);
    }

    /**
     * @author lijibin 2005-06-19
     * @desc ����ͨ�����Զ�������һ���м�ɱ�������
     * @desc strEndorseNoOld������������ͨ������Ϊ�м�ɱ�������Ϊ�㡢����������XX�ı仯�������Զ�������һ������
     * @param DBPool dbpool
     * @param String strEndorseNoOld
     * @throws UserException
     * @throws Exception
     */
    
    public void createEdisPremium(DbPool dbpool, String strEndorseNoOld)
    throws UserException,Exception {
        /* modify by zhulei 20050829 begin reason���м�ɱ���mainӦ��ȡC������P */
        
        
        BLPrpPmain blPrpPmain = new BLPrpPmain();
        BLPrpPtext blPrpPtext = new BLPrpPtext();
        DBPrpCmain dbPrpCmain = new DBPrpCmain();
        DBPrpPmain dbPrpPmain = new DBPrpPmain();
        DBPrpPmain dbPrpPmainOld = new DBPrpPmain();
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        PrpMiddleCostSchema prpMiddleCostSchema = new PrpMiddleCostSchema();
        PrpPtextSchema prpPtextSchema = null;

        Bill bill = new Bill();
        ChgDate chgDate = new ChgDate();
        Money money = new Money();

        String strWhere = "";
        String strPolicyNo = "";
        String strEndorseNo = "";
        String strCurrency1 = "";
        String strPtext = "";
        String[] arrPtext = {};

        double dblDisRate1 = 0;
        double dblDisFee1 = 0;
        double dblChgPremium1Sum = 0;
        double dblDisFee1Left = 0;

        int intMaxLength = 70;
        int intLineNo = 0;

        
        strWhere = "EndorseNo = '" + strEndorseNoOld + "'";
        blPrpPmain.query(strWhere);
        strPolicyNo = blPrpPmain.getArr(0).getPolicyNo();
        dbPrpCmain.getInfo(dbpool, strPolicyNo);

        
        strEndorseNo = bill.getNo("prpphead", dbPrpCmain.getPolicyNo());
        dbPrpPmain = dbPrpCmain.evaluateFromCmainToPmain(dbPrpCmain, strEndorseNo);

        
        
        dblDisRate1 = Double.parseDouble(Str.chgStrZero(dbPrpCmain.getDisRate1()));
        if (dblDisRate1 == 0) {
            return;
        }
        
        dbPrpPhead.getInfo(dbpool, strEndorseNoOld);
        if (dbPrpPhead.getEndorType().trim().equals(SysConfig.getProperty("EDITTYPE_MIDDLECOST").trim()) ||
            dbPrpPhead.getEndorType().trim().equals(SysConfig.getProperty("EDITTYPE_COMMISSION").trim())) {
            return;
        }

        
        strWhere = "SELECT Currency1,SUM(ChgPremium1) AS ChgPremium1 FROM PrpPfee"
                 + " WHERE EndorseNo='" + strEndorseNoOld + "'"
                 + " GROUP BY Currency1";
        ResultSet rs = dbpool.query(strWhere);
        while (rs.next()) {
            strCurrency1 = rs.getString("Currency1");
            dblChgPremium1Sum += rs.getDouble("ChgPremium1");
        }
        rs.close();
        if (dblChgPremium1Sum == 0) {
            return;
        }
        
        dblDisFee1 = dblChgPremium1Sum * dblDisRate1 / 100;
        
        dblDisFee1 = Str.round(Str.round(dblDisFee1, 8), 2);
        
        dblDisFee1Left = dblChgPremium1Sum - dblDisFee1;
        dblDisFee1Left = Str.round(dblDisFee1Left, 2);
        
        prpMiddleCostSchema.setClassCode(dbPrpCmain.getClassCode());
        prpMiddleCostSchema.setRiskCode(dbPrpCmain.getRiskCode());
        prpMiddleCostSchema.setEndorseNo(strEndorseNo);
        prpMiddleCostSchema.setPolicyNo(strPolicyNo);
        prpMiddleCostSchema.setCertiNo(strEndorseNoOld);
        prpMiddleCostSchema.setCertiType("E");
        prpMiddleCostSchema.setDisRate(String.valueOf(dblDisRate1));
        prpMiddleCostSchema.setCurrency(strCurrency1);
        prpMiddleCostSchema.setDisFee(String.valueOf(dblDisFee1));
        prpMiddleCostSchema.setGenerateDate(chgDate.getCurrentTime("yyyy-MM-dd"));
        prpMiddleCostSchema.setGenerateTime(chgDate.getCurrentTime("HH:mm"));
        this.setArr(prpMiddleCostSchema);

        
        dbPrpPhead.setEndorseNo(strEndorseNo);
        dbPrpPhead.setPrintNo("");
        dbPrpPhead.setEndorseTimes(String.valueOf(Integer.parseInt(dbPrpCmain.getEndorseTimes())));
        dbPrpPhead.setEndorType("56");
        
        dbPrpPhead.setEndorDate(prpMiddleCostSchema.getGenerateDate());
        dbPrpPhead.setInputDate(prpMiddleCostSchema.getGenerateDate());
        dbPrpPhead.setInputHour(chgDate.getCurrentTime("HH"));
        dbPrpPhead.setUnderWriteEndDate(prpMiddleCostSchema.getGenerateDate());
        dbPrpPhead.setUnderWriteFlag("1");
        dbPrpPhead.setFlag("");
        dbPrpPhead.setUpdaterCode("");
        dbPrpPhead.setUpdateDate("");
        dbPrpPhead.setUpdateHour("");


        
        
        dbPrpCmain.setEndorseTimes(String.valueOf(Integer.parseInt(dbPrpCmain.getEndorseTimes()) + 1));

        BLPrpMiddleCostDetail blPrpMiddleCostDetail = new BLPrpMiddleCostDetail();
        dbPrpPmainOld.getInfo(dbpool,strEndorseNoOld);
        blPrpMiddleCostDetail.createEdisPremiumDetail(dbpool,this,dbPrpPmainOld);

        if(this.getSize()>0)
        {
          dblDisFee1=Math.abs(Double.parseDouble(Str.chgStrZero(this.getArr(0).getDisFee())));
        }
         
        
        /* modify by zhulei 20050901 begin reason������س����� */
        if (dblChgPremium1Sum > 0) {
            strPtext = "    ����XX��ĵķ�XXXXX�����˱仯�����ݱ�XX�����룬��˫��Э�̣�"
                     + "ͬ�ⱾXX�����µ�XX�����Ա����\r\n �����٣�"
                     + prpMiddleCostSchema.getCurrency() + money.toAccount(String.valueOf(Math.abs(dblDisFee1))) + "Ԫ��\r\n";
        } else {
            strPtext = "    ����XX��ĵķ�XXXXX�����˱仯�����ݱ�XX�����룬��˫��Э�̣�"
                     + "ͬ�ⱾXX�����µ�XX�����Ա����\r\n ������ӣ�"
                     + prpMiddleCostSchema.getCurrency() + money.toAccount(String.valueOf(Math.abs(dblDisFee1))) + "Ԫ��\r\n";
        }
        /* modify by zhulei 20050901 end */
        arrPtext = (String[])Str.split(strPtext, intMaxLength);
        for (int i = 0; i < arrPtext.length; i++) {
            intLineNo++;
            prpPtextSchema = new PrpPtextSchema();
            prpPtextSchema.setEndorseNo(dbPrpPhead.getEndorseNo());
            prpPtextSchema.setPolicyNo(dbPrpPhead.getPolicyNo());
            prpPtextSchema.setLineNo(String.valueOf(intLineNo));
            prpPtextSchema.setEndorseText(arrPtext[i]);
            blPrpPtext.setArr(prpPtextSchema);
        }
        
        strPtext = "    ��XX�����������������䡣";
        intLineNo++;
        prpPtextSchema = new PrpPtextSchema();
        prpPtextSchema.setEndorseNo(dbPrpPhead.getEndorseNo());
        prpPtextSchema.setPolicyNo(dbPrpPhead.getPolicyNo());
        prpPtextSchema.setLineNo(String.valueOf(intLineNo));
        prpPtextSchema.setEndorseText(strPtext);
        blPrpPtext.setArr(prpPtextSchema);

        dbPrpCmain.update(dbpool);
        dbPrpPhead.insert(dbpool);
        dbPrpPmain.insert(dbpool);
        blPrpPtext.save(dbpool);
        this.save(dbpool);
        blPrpMiddleCostDetail.save(dbpool);
        /* modify by zhulei 20050829 end */
    }

    /* add by xiaojian 20050828 begin reason��Ϊ��XX����XX��������ѯ�õķ��� */
    /**
     * @desc ��XX���Ų�ѯ�м�ɱ���Ϣ
     * @desc SELECT * FROM PrpMiddleCost
     *       WHERE CertiNo=strProposalNo
     *       AND CertiType='T'
     *       ORDER BY GnerateDate,GenerateTime DESC
     * @param String strProposalNo XX����
     * @return PrpMiddleCostSchema ��Ҫ��ȡDisRate��DisFee
     */
    public PrpMiddleCostSchema getDataT(String strProposalNo) throws Exception {
        DBPrpTmain dbPrpTmain = new DBPrpTmain();
        PrpMiddleCostSchema prpMiddleCostSchema = new PrpMiddleCostSchema();

        String strWhere = "";

        int i = 0;
        boolean blExist = false;

        if (dbPrpTmain.getInfo(strProposalNo) == 100) {
            
            
            logger.info("BLPrpMiddleCost.getDataT��û��"+strProposalNo+"����XX����");
            
            return null;
        }

        strWhere = " CertiNo='" + strProposalNo + "'"
                 + " AND CertiType='T'"
                 + " ORDER BY GenerateDate,GenerateTime DESC";
        this.query(strWhere);
        if (this.getSize() == 0) {
            
        } else {
            prpMiddleCostSchema = this.getArr(0);
        }

        return prpMiddleCostSchema;
    }

    /**
     * @desc ��XX�Ų�ѯ�м�ɱ���Ϣ
     * @desc SELECT * FROM PrpMiddleCost
     *       WHERE PolicyNo=strPolicyNo AND (���������������ͨ��)
     *       ORDER BY GnerateDate,GenerateTime DESC
     * @param String strPolicyNo XX��
     * @return PrpMiddleCostSchema ��Ҫ��ȡDisRate��DisFee
     */
    public PrpMiddleCostSchema getDataP(String strPolicyNo) throws Exception {
        DBPrpCmain dbPrpCmain = new DBPrpCmain();
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        PrpMiddleCostSchema prpMiddleCostSchema = new PrpMiddleCostSchema();
        PrpMiddleCostSchema prpMiddleCostSchemaTmp = null;

        String strCertiNo = "";
        String strCertiType = "";
        String strUnderWriteFlag = "";
        String strWhere = "";

        double dbSumDisFee = 0;

        int i = 0;
        boolean blExist = false;

        if (dbPrpCmain.getInfo(strPolicyNo) == 100) {
            
            
            logger.info("BLPrpMiddleCost.getDataP��û��"+strPolicyNo+"����XX��");
            
            return null;
        }

        strWhere = " PolicyNo='" + strPolicyNo + "'"
                 + " ORDER BY GenerateDate,GenerateTime DESC";
        this.query(strWhere);
        if (this.getSize() == 0) {
            
        } else {
            for (i = 0; i < this.getSize(); i++) {
                prpMiddleCostSchemaTmp = this.getArr(i);
                strCertiNo = prpMiddleCostSchemaTmp.getCertiNo();
                strCertiType = prpMiddleCostSchemaTmp.getCertiType();

                if (strCertiType.equals("E")) {
                    if (dbPrpPhead.getInfo(strCertiNo) == 100) {
                        
                        
                        logger.info("BLPrpMiddleCost.getDataP��û��"+strCertiNo+"����������");
                        
                        return null;
                    }
                    strUnderWriteFlag = dbPrpPhead.getUnderWriteFlag();
                    
                    if (!strUnderWriteFlag.equals("1")||!strUnderWriteFlag.equals("3")) {
                        continue;
                    }
                }

                if (!blExist) {
                    prpMiddleCostSchema = this.getArr(i);
                    blExist = true;
                }

                dbSumDisFee = Str.round(Str.round(dbSumDisFee + Double.parseDouble(Str.chgStrZero(prpMiddleCostSchemaTmp.getDisFee())),8),2);
            }

            if (blExist) {
                prpMiddleCostSchema.setDisFee(String.valueOf(dbSumDisFee));
            }
        }

        return prpMiddleCostSchema;
    }

    /**
     * @desc �������Ų�ѯ�м�ɱ���Ϣ�������������������ж��Ƿ����ͨ����
     * @desc SELECT * FROM PrpMiddleCost
     *       WHERE (CERTINo=strPolicyNo
     *       OR CERTINO<=strEndorseNo)
     *       ORDER BY GenerateDate,GenerateTime DESC
     * @param String strEndorseNo ������
     * @return PrpMiddleCostSchema ��Ҫ��ȡDisRate��DisFee
     */
    public PrpMiddleCostSchema getDataE(String strEndorseNo) throws Exception {
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        PrpMiddleCostSchema prpMiddleCostSchema = new PrpMiddleCostSchema();
        PrpMiddleCostSchema prpMiddleCostSchemaTmp = null;

        String strPolicyNo = "";
        String strWhere = "";

        double dbSumDisFee = 0;

        int i = 0;
        boolean blExist = false;

        if (dbPrpPhead.getInfo(strEndorseNo)==100) {
            
            
            logger.info("BLPrpMiddleCost.getDataE��û��"+strEndorseNo+"����������");
            
            return null;
        }

        strPolicyNo = dbPrpPhead.getPolicyNo();
        strWhere = " PolicyNo='"+strPolicyNo+"'"
                 + " AND (CertiNo='"+strPolicyNo+"'"
                 + " OR CertiNo<='"+strEndorseNo+"')"
                 + " ORDER BY GenerateDate,GenerateTime DESC";
        this.query(strWhere);
        for (i = 0; i < this.getSize(); i++) {
            prpMiddleCostSchemaTmp = this.getArr(i);

            if (!blExist) {
                prpMiddleCostSchema = this.getArr(0);
                blExist = true;
            }

            dbSumDisFee = Str.round(Str.round(dbSumDisFee + Double.parseDouble(Str.chgStrZero(prpMiddleCostSchemaTmp.getDisFee())),8),2);
        }

        if (blExist) {
            prpMiddleCostSchema.setDisFee(String.valueOf(dbSumDisFee));
        }

        return prpMiddleCostSchema;
    }

    
    /**
     * @desc �������Ų�ѯ�м�ɱ���Ϣ�������������������ж��Ƿ����ͨ����
     * @desc SELECT * FROM PrpMiddleCost
     *       WHERE (CERTINo=strPolicyNo
     *       OR CERTINO<=strEndorseNo)
     *       ORDER BY GenerateDate,GenerateTime DESC
     * @param String strEndorseNo ������
     * @return PrpMiddleCostSchema ��Ҫ��ȡDisRate��DisFee
     */
    public PrpMiddleCostSchema getDataEForStat(DbPool dbpool,String strEndorseNo) throws Exception {
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        PrpMiddleCostSchema prpMiddleCostSchema = new PrpMiddleCostSchema();
        PrpMiddleCostSchema prpMiddleCostSchemaTmp = null;

        String strPolicyNo = "";
        String strWhere = "";

        double dbSumDisFee = 0;

        int i = 0;
        boolean blExist = false;

        if (dbPrpPhead.getInfo(dbpool,strEndorseNo)==100) {
            
            
            logger.info("BLPrpMiddleCost.getDataE��û��"+strEndorseNo+"����������");
            
            return null;
        }

        
        strPolicyNo = dbPrpPhead.getPolicyNo();
        strWhere = " PolicyNo='"+strPolicyNo+"'"
                 + " AND (EndorseNo='"+strPolicyNo+"'"
                 + " OR EndorseNo<='"+strEndorseNo+"')"
                 + " ORDER BY GenerateDate,GenerateTime DESC";
        this.query(dbpool,strWhere);
        for (i = 0; i < this.getSize(); i++) {
            prpMiddleCostSchemaTmp = this.getArr(i);

            if (!blExist) {
                prpMiddleCostSchema = this.getArr(0);
                blExist = true;
            }

            dbSumDisFee = Str.round(Str.round(dbSumDisFee + Double.parseDouble(Str.chgStrZero(prpMiddleCostSchemaTmp.getDisFee())),8),2);
        }

        if (blExist) {
            prpMiddleCostSchema.setDisFee(String.valueOf(dbSumDisFee));
        }

        return prpMiddleCostSchema;
    }
    

    /**
     * @desc �������Ų�ѯ�м�ɱ���Ϣ���������������������ж��Ƿ����ͨ����
     * @desc SELECT * FROM PrpMiddleCost
     *       WHERE (CERTINo=strPolicyNo
     *       OR CERTINO<strEndorseNo)
     *       ORDER BY GenerateDate,GenerateTime DESC
     * @param String strEndorseNo ������
     * @return PrpMiddleCostSchema ��Ҫ��ȡDisRate��DisFee
     */
    public PrpMiddleCostSchema getDataEPre(String strEndorseNo) throws Exception {
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        PrpMiddleCostSchema prpMiddleCostSchema = new PrpMiddleCostSchema();
        PrpMiddleCostSchema prpMiddleCostSchemaTmp = null;

        String strPolicyNo = "";
        String strWhere = "";

        double dbSumDisFee = 0;

        int i = 0;
        boolean blExist = false;

        if (dbPrpPhead.getInfo(strEndorseNo)==100) {
            
            
            logger.info("BLPrpMiddleCost.getDataE��û��"+strEndorseNo+"����������");
            
            return null;
        }

        strPolicyNo = dbPrpPhead.getPolicyNo();
        strWhere = " PolicyNo='"+strPolicyNo+"'"
                 + " AND (CertiNo='"+strPolicyNo+"'"
                 + " OR CertiNo<'"+strEndorseNo+"')"
                 + " ORDER BY GenerateDate,GenerateTime DESC";
        this.query(strWhere);
        for (i = 0; i < this.getSize(); i++) {
            prpMiddleCostSchemaTmp = this.getArr(i);

            if (!blExist) {
                prpMiddleCostSchema = this.getArr(0);
                blExist = true;
            }

            dbSumDisFee = Str.round(Str.round(dbSumDisFee + Double.parseDouble(Str.chgStrZero(prpMiddleCostSchemaTmp.getDisFee())),8),2);
        }

        if (blExist) {
            prpMiddleCostSchema.setDisFee(String.valueOf(dbSumDisFee));
        }

        return prpMiddleCostSchema;
    }

    /**
     * @desc �������Ų�ѯ�м�ɱ���Ϣ����������������������Ϊ56�ģ�
     * @desc ��������Ϊ56��������ѯ�Ͱ�����SQL����в�ѯ������CertiNo��ѯ�Ľ��һ��
     * @desc SELECT CertiNo FROM PrpMiddleCost
     *       WHERE EndorseNo=strEndorseNo
     * @param String strEndorseNo ������
     * @return PrpMiddleCostSchema ��Ҫ��ȡDisRate��DisFee
     */
    public PrpMiddleCostSchema getDataE56(String strEndorseNo) throws Exception {
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        PrpMiddleCostSchema prpMiddleCostSchema = new PrpMiddleCostSchema();
        PrpMiddleCostSchema prpMiddleCostSchemaTmp = new PrpMiddleCostSchema();

        String strCertiNo = "";
        String strCertiType = "";
        String strWhere = "";

        if (dbPrpPhead.getInfo(strEndorseNo) == 100) {
            
            
            logger.info("BLPrpMiddleCost.getDataE56��û��"+strEndorseNo+"����������");
            
            return null;
        }

        strWhere = " EndorseNo='" + strEndorseNo + "'";
        this.query(strWhere);
        if (this.getSize() > 0) {
            prpMiddleCostSchemaTmp = this.getArr(0);
            strCertiNo = prpMiddleCostSchemaTmp.getCertiNo();
            strCertiType = prpMiddleCostSchemaTmp.getCertiType();

            if (strCertiType.equals("P")) {
                prpMiddleCostSchema = this.getDataP(strCertiNo);
            } else if (strCertiType.equals("E")) {
                prpMiddleCostSchema = this.getDataE(strCertiNo);
            }
        }

        return prpMiddleCostSchema;
    }

    /**
     * @desc �������Ų�ѯ�м�ɱ���Ϣ������������������������Ϊ56�ģ�
     * @desc ��������Ϊ56��������ѯ�Ͱ�����SQL����в�ѯ������CertiNo��ѯ�Ľ��һ��
     * @desc SELECT CertiNo FROM PrpMiddleCost
     *       WHERE EndorseNo=strEndorseNo
     * @param String strEndorseNo ������
     * @return PrpMiddleCostSchema ��Ҫ��ȡDisRate��DisFee
     */
    public PrpMiddleCostSchema getDataEPre56(String strEndorseNo) throws Exception {
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        PrpMiddleCostSchema prpMiddleCostSchema = new PrpMiddleCostSchema();
        PrpMiddleCostSchema prpMiddleCostSchemaTmp = new PrpMiddleCostSchema();

        String strCertiNo = "";
        String strCertiType = "";
        String strWhere = "";

        if (dbPrpPhead.getInfo(strEndorseNo) == 100) {
            
            
            logger.info("BLPrpMiddleCost.getDataE56��û��"+strEndorseNo+"����������");
            
            return null;
        }

        strWhere = " EndorseNo='" + strEndorseNo + "'";
        this.query(strWhere);
        if (this.getSize() > 0) {
            prpMiddleCostSchemaTmp = this.getArr(0);
            strCertiNo = prpMiddleCostSchemaTmp.getCertiNo();
            strCertiType = prpMiddleCostSchemaTmp.getCertiType();

            if (strCertiType.equals("P")) {
                prpMiddleCostSchema = this.getDataP(strCertiNo);
            } else if (strCertiType.equals("E")) {
                prpMiddleCostSchema = this.getDataEPre(strCertiNo);
            }
        }

        return prpMiddleCostSchema;
    }
    /* add by xiaojian 20050828 end */
    /**
     * @desc �õ������м����
     * @param iProposalNo
     * @throws UserException
     * @throws Exception
     */
    public double getDisPremiumT( String iProposalNo) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        return this.getDisPremiumT(dbpool,iProposalNo);
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
     * @author
     * @desc �õ������м����
     * @param iProposalNo
     * @throws UserException
     * @throws Exception
     */
    public double getDisPremiumT(DbPool dbpool,String iProposalNo) throws UserException,Exception
    {

      String strWhere = "";
      String strCurrency1 = "";
      double dblDisRate1 = 0;
      double dblDisFee1Left = 0;
      double dblSelfRate1 = 100;
      double dbDisPremium = 0;
      double dblPremium1Sum = 0;

      DBPrpTmain  dbPrpTmain = new DBPrpTmain();
      BLPrpTcoins blPrpTcoins = new BLPrpTcoins();
      BLPrpTitemKind blPrpTitemKind = new BLPrpTitemKind();
      BLPrpTfee      blPrpTfee      = new BLPrpTfee();

      PrpTitemKindSchema        prpTitemKindSchema        = new PrpTitemKindSchema();

      dbPrpTmain.getInfo(dbpool,iProposalNo);

      dblDisRate1 = Double.parseDouble(Str.chgStrZero(dbPrpTmain.getDisRate1()));
      if(dblDisRate1 == 0) {
          return dbDisPremium;
      }

      dblSelfRate1 = Str.round(Double.parseDouble(Str.chgStrZero(blPrpTcoins.getSelfRate(dbpool,iProposalNo,"2"))),4);

      if(dblSelfRate1==0)
        dblSelfRate1 = 100;

      if(dbPrpTmain.getClassCode().trim().equals("05"))
      {
        blPrpTitemKind.query(dbpool,"ProposalNo = '"+ iProposalNo +"'",0);
        blPrpTfee.query(dbpool,"ProposalNo = '"+ iProposalNo +"'",0);

        if(blPrpTitemKind.getSize()<1 || blPrpTfee.getSize()<1 )
          return dbDisPremium;

        for(int i=0;i<blPrpTitemKind.getSize();i++)
        {
          prpTitemKindSchema = blPrpTitemKind.getArr(i);
          if(prpTitemKindSchema.getFlag().length()>=6&&
             prpTitemKindSchema.getFlag().substring(5,6).equals("1"))
          {}
          else
          {
            dbDisPremium += Str.round(Str.round(Double.parseDouble(Str.chgStrZero(prpTitemKindSchema.getPremium()))* dblDisRate1 / 100 * dblSelfRate1/100,8),2);
          }
        }
      }
      else
      {
        strWhere = "SELECT Currency1,SUM(Premium1) AS Premium1 FROM PrpTfee"
                 + " WHERE ProposalNo='" + iProposalNo + "'"
                 + " GROUP BY Currency1";
        ResultSet rs = dbpool.query(strWhere);
        while (rs.next()) {
            strCurrency1 = rs.getString("Currency1");
            dblPremium1Sum += rs.getDouble("Premium1");
        }
        rs.close();
        if(dblPremium1Sum == 0) {
            return dbDisPremium;
        }

        dblPremium1Sum = Str.round(dblPremium1Sum,2);
        dbDisPremium = dblPremium1Sum * dblDisRate1 / 100 * dblSelfRate1/100;
      }
      dbDisPremium = Str.round(Str.round(dbDisPremium ,4),2);

      return dbDisPremium;
    }


    /**
     * @desc �õ������м����
     * @param iPolicyNo
     * @throws UserException
     * @throws Exception
     */
    public double getDisPremiumC( String iPolicyNo) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        return this.getDisPremiumC(dbpool,iPolicyNo);
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
     * @author
     * @desc �õ������м����
     * @param iPolicyNo
     * @throws UserException
     * @throws Exception
     */
    public double getDisPremiumC(DbPool dbpool,String iPolicyNo) throws UserException,Exception
    {

      String strWhere = "";
      String strCurrency1 = "";
      double dblDisRate1 = 0;
      double dblDisFee1Left = 0;
      double dblSelfRate1 = 100;
      double dbDisPremium = 0;
      double dblPremium1Sum = 0;

      DBPrpCmain  dbPrpCmain = new DBPrpCmain();
      BLPrpCcoins blPrpCcoins = new BLPrpCcoins();
      BLPrpCitemKind blPrpCitemKind = new BLPrpCitemKind();
      BLPrpCfee      blPrpCfee      = new BLPrpCfee();

      PrpCitemKindSchema        prpCitemKindSchema        = new PrpCitemKindSchema();

      dbPrpCmain.getInfo(dbpool,iPolicyNo);

      dblDisRate1 = Double.parseDouble(Str.chgStrZero(dbPrpCmain.getDisRate1()));
      if(dblDisRate1 == 0) {
          return dbDisPremium;
      }

      dblSelfRate1 = Str.round(Double.parseDouble(Str.chgStrZero(blPrpCcoins.getSelfRate(dbpool,iPolicyNo,"2"))),4);

      if(dblSelfRate1==0)
        dblSelfRate1 = 100;

      if(dbPrpCmain.getClassCode().trim().equals("05"))
      {
        blPrpCitemKind.query(dbpool,"PolicyNo = '"+ iPolicyNo +"'",0);
        blPrpCfee.query(dbpool,"PolicyNo = '"+ iPolicyNo +"'",0);

        if(blPrpCitemKind.getSize()<1 || blPrpCfee.getSize()<1 )
          return dbDisPremium;

        for(int i=0;i<blPrpCitemKind.getSize();i++)
        {
          prpCitemKindSchema = blPrpCitemKind.getArr(i);
          if(prpCitemKindSchema.getFlag().length()>=6&&
             prpCitemKindSchema.getFlag().substring(5,6).equals("1"))
          {}
          else
          {
            dbDisPremium += Str.round(Str.round(Double.parseDouble(Str.chgStrZero(prpCitemKindSchema.getPremium()))* dblDisRate1 / 100 * dblSelfRate1/100,8),2);
          }
        }
      }
      else
      {
        strWhere = "SELECT Currency1,SUM(Premium1) AS Premium1 FROM PrpCfee"
                 + " WHERE PolicyNo='" + iPolicyNo + "'"
                 + " GROUP BY Currency1";
        ResultSet rs = dbpool.query(strWhere);
        while (rs.next()) {
            strCurrency1 = rs.getString("Currency1");
            dblPremium1Sum += rs.getDouble("Premium1");
        }
        rs.close();
        if(dblPremium1Sum == 0) {
            return dbDisPremium;
        }

        dblPremium1Sum = Str.round(dblPremium1Sum,2);
        dbDisPremium = dblPremium1Sum * dblDisRate1 / 100 * dblSelfRate1/100;
      }
      dbDisPremium = Str.round(Str.round(dbDisPremium ,4),2);

      return dbDisPremium;
    }

    /**
     * @desc �õ������м����
     * @param iPolicyNo
     * @throws UserException
     * @throws Exception
     */
    public double getDisPremiumE( String iEndorseNo) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        return this.getDisPremiumE(dbpool,iEndorseNo);
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
     * @author
     * @desc �õ������м����
     * @param iEndorseNo
     * @throws UserException
     * @throws Exception
     */
    public double getDisPremiumE(DbPool dbpool,String iEndorseNo) throws UserException,Exception
    {

      String strWhere = "";
      String strCurrency1 = "";
      double dblDisRate1 = 0;
      double dblDisFee1Left = 0;
      double dblSelfRate1 = 100;
      double dbDisPremium = 0;
      double dblPremium1Sum = 0;

      DBPrpPmain  dbPrpPmain = new DBPrpPmain();
      BLPrpPcoins blPrpPcoins = new BLPrpPcoins();
      BLPrpPitemKind blPrpPitemKind = new BLPrpPitemKind();
      BLPrpPfee      blPrpPfee      = new BLPrpPfee();
      
      DBPrpPhead       dbPrpPhead      = new DBPrpPhead();
      DBPrpMiddleCost  dbPrpMiddleCost = new DBPrpMiddleCost();
      
      PrpPitemKindSchema        prpPitemKindSchema        = new PrpPitemKindSchema();

      
      dbPrpPhead.getInfo(dbpool,iEndorseNo);
      if (dbPrpPhead.getEndorType().equals("58")){
          dbPrpMiddleCost.getInfo(dbpool,iEndorseNo);
          dbDisPremium = Double.parseDouble(dbPrpMiddleCost.getDisFee());
          return dbDisPremium;
      }
      
      dbPrpPmain.getInfo(dbpool,iEndorseNo);

      dblDisRate1 = Double.parseDouble(Str.chgStrZero(dbPrpPmain.getDisRate1()));
      if(dblDisRate1 == 0) {
          return dbDisPremium;
      }

      dblSelfRate1 = Str.round(Double.parseDouble(Str.chgStrZero(blPrpPcoins.getSelfRate(dbpool,iEndorseNo,"2"))),4);

      if(dblSelfRate1==0)
        dblSelfRate1 = 100;

      if(dbPrpPmain.getClassCode().trim().equals("05"))
      {
        blPrpPitemKind.query(dbpool,"EndorseNo = '"+ iEndorseNo +"'",0);
        blPrpPfee.query(dbpool,"EndorseNo = '"+ iEndorseNo +"'",0);

        if(blPrpPitemKind.getSize()<1 || blPrpPfee.getSize()<1 )
          return dbDisPremium;

        for(int i=0;i<blPrpPitemKind.getSize();i++)
        {
          prpPitemKindSchema = blPrpPitemKind.getArr(i);
          if(prpPitemKindSchema.getFlag().length()>=6&&
             prpPitemKindSchema.getFlag().substring(5,6).equals("1"))
          {}
          else
          {
            dbDisPremium += Str.round(Str.round(Double.parseDouble(Str.chgStrZero(prpPitemKindSchema.getChgPremium()))* dblDisRate1 / 100 * dblSelfRate1/100,8),2);
          }
        }
      }
      else
      {
        strWhere = "SELECT Currency1,SUM(ChgPremium1) AS Premium1 FROM PrpPfee"
                 + " WHERE EndorseNo='" + iEndorseNo + "'"
                 + " GROUP BY Currency1";
        ResultSet rs = dbpool.query(strWhere);
        while (rs.next()) {
            strCurrency1 = rs.getString("Currency1");
            dblPremium1Sum += rs.getDouble("Premium1");
        }
        rs.close();
        if(dblPremium1Sum == 0) {
            return dbDisPremium;
        }

        dblPremium1Sum = Str.round(dblPremium1Sum,2);
        dbDisPremium = dblPremium1Sum * dblDisRate1 / 100 * dblSelfRate1/100;
      }
      dbDisPremium = Str.round(Str.round(dbDisPremium ,4),2);

      return dbDisPremium;
    }

    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
