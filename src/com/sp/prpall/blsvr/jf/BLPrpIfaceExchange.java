package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.utiall.dbsvr.*;
import com.sp.prpall.blsvr.cb.*;
import com.sp.prpall.blsvr.pg.*;
import com.sp.prpall.blsvr.lp.*;
import com.sp.prpall.blsvr.pg.BLPrpPfee;
import com.sp.prpall.dbsvr.jf.DBPrpIfaceExchange;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.cb.DBPrpCPitemKind;
import com.sp.prpall.dbsvr.pg.DBPrpPmain;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;
import com.sp.prpall.dbsvr.lp.DBPrpLprepay;
import com.sp.prpall.dbsvr.lp.DBPrpLcompensate;
import com.sp.prpall.schema.*;

import com.sp.prpall.dbsvr.lp.DBPrpLreplevy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ����PrpIfaceExchange��BL��
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>@createdate 2004-10-8</p>
 * @Author     : ³��
 * @version 1.0
 */
public class BLPrpIfaceExchange{
    private Vector schemas = new Vector();
    
    private BLPrpAccCompare blPrpAccCompare = new BLPrpAccCompare();
    
    protected final Log logger = LogFactory.getLog(getClass());
    /**
     * ���캯��
     */
    public BLPrpIfaceExchange(){
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
     *����һ��PrpIfaceExchangeSchema��¼
     *@param iPrpIfaceExchangeSchema PrpIfaceExchangeSchema
     *@throws Exception
     */
    public void setArr(PrpIfaceExchangeSchema iPrpIfaceExchangeSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpIfaceExchangeSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
    }

    /**
     *�õ�һ��PrpIfaceExchangeSchema��¼
     *@param index �±�
     *@return һ��PrpIfaceExchangeSchema����
     *@throws Exception
     */
    public PrpIfaceExchangeSchema getArr(int index) throws Exception
    {
     PrpIfaceExchangeSchema PrpIfaceExchangeSchema = null;
       try
       {
        PrpIfaceExchangeSchema = (PrpIfaceExchangeSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return PrpIfaceExchangeSchema;
    }

    /**
     *ɾ��һ��PrpIfaceExchangeSchema��¼
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
       this.query(iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBPrpIfaceExchange dbPrpIfaceExchange = new DBPrpIfaceExchange();
      if (iLimitCount > 0 && dbPrpIfaceExchange.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpIfaceExchange.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Iface_Exchange WHERE " + iWherePart;
        schemas = dbPrpIfaceExchange.findByConditions(strSqlStatement);
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
       this.query(dbpool,iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
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
      DBPrpIfaceExchange dbPrpIfaceExchange = new DBPrpIfaceExchange();
      if (iLimitCount > 0 && dbPrpIfaceExchange.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpIfaceExchange.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Iface_Exchange WHERE " + iWherePart;
        schemas = dbPrpIfaceExchange.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpIfaceExchange dbPrpIfaceExchange = new DBPrpIfaceExchange();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpIfaceExchange.setSchema((PrpIfaceExchangeSchema)schemas.get(i));
        dbPrpIfaceExchange.insert(dbpool);
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
     *@param null null
     *@return ��
     */
    public void cancel(DbPool dbpool,String strId) throws Exception
    {




    	String strSqlStatement = " DELETE FROM Iface_Exchange WHERE Id= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, strId);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }

    /**
     * ����dbpool��ɾ������
     *@param null null
     *@return ��
     */
    public void cancel(String strId ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,null);
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
     * ��dbpool����null��ȡ����
     *@param null null
     *@return ��
     */
    public void getData(String strId) throws Exception
    {
		DbPool dbpool = new DbPool();
		
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			getData(dbpool, null);
		} catch (Exception e) {
			
		} finally {
			dbpool.close();
		}
		
	}

    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param null null
     *@return ��
     */
    public void getData(DbPool dbpool,String strId) throws Exception
    {
    String strWherePart = "";
    strWherePart = "null= ' " + null + "'";
    query(dbpool,strWherePart,0);
    }




    /**
     * ����XX�ӿ����ݵ��ӿ����ݽ�����PrpIfaceExchange��ں���
     *@Author	³��
     *@param 	dbpool,dbPrpCmain,strBusinessNo,strTypeCode
     *@return
     *@date    20041018
    */
    public void	saveIfaceExchange(DbPool dbpool,
                                  PrpCmainSchema prpCmainSchema,
                                  String strBusinessNo,
                                  String strTypeCode) throws Exception
    {
      BLPrpCfee blPrpCfee = new BLPrpCfee();
      blPrpCfee.getData(strBusinessNo);
      PrpCfeeSchema prpCfeeSchema = new PrpCfeeSchema();
      String strContents = "";
      for (int i=0; i<blPrpCfee.getSize();i++)
      {
        prpCfeeSchema = blPrpCfee.getArr(i);
        strContents = this.getPolicy(prpCmainSchema, prpCfeeSchema,strBusinessNo); 
        this.saveIface(dbpool, strBusinessNo, strTypeCode, strContents,i);
      }
      this.saveIPlan(dbpool, strBusinessNo, "1005",blPrpCfee.getSize()); 
    }

    /**
     * �����������ӿ����ݵ��ӿ����ݽ�����PrpIfaceExchange��ں���
     *@Author	³��
     *@param 	dbpool,dbPrpPhead,PolicyNo,strBusinessNo,strTypeCode
     *@return
     *@date    20041018
    */
    public void	saveIfaceExchange(DbPool dbpool,
                                  DBPrpPhead dbPrpPhead,
                                  String strBusinessNo,
                                  String strTypeCode) throws Exception
    {
      BLPrpPfee blPrpPfee = new BLPrpPfee();
      blPrpPfee.getData(strBusinessNo);
      PrpPfeeSchema prpPfeeSchema = new PrpPfeeSchema();
      String strContents = "";
      for ( int i =0;i<blPrpPfee.getSize();i++ )
      {
        prpPfeeSchema = blPrpPfee.getArr(i);
        
        if(!prpPfeeSchema.getChgPremium().equals("0"))
        {
          strContents = this.getEndorse(dbPrpPhead, prpPfeeSchema, strBusinessNo); 
          this.saveIface(dbpool, strBusinessNo, strTypeCode, strContents,i);
        }
        
      }
      this.saveIPlan(dbpool,strBusinessNo,"1005",blPrpPfee.getSize());       
    }

    /**
     * ����Ԥ��ӿ����ݵ��ӿ����ݽ�����PrpIfaceExchange��ں���
     *@Author	³��
     *@param 	dbpool,dbPrpPhead,PolicyNo,strBusinessNo,strTypeCode
     *@return
     *@date    20041020
    */
    public void	saveIfaceExchange(DbPool dbpool,
                                  DBPrpLprepay dbPrpLprepay,
                                  String strBusinessNo,
                                  String strTypeCode) throws Exception
    {
      String strContents = "";
      strContents = this.getLprepay(dbPrpLprepay, strBusinessNo); 
      this.saveIface(dbpool, strBusinessNo, strTypeCode, strContents);
    }

    /**
     * ����ʵ��ӿ����ݵ��ӿ����ݽ�����PrpIfaceExchange��ں���
     *@Author	³��
     *@param 	dbpool,dbPrpPhead,PolicyNo,strBusinessNo,strTypeCode
     *@return
     *@date    20041020
    */
    public void	saveIfaceExchange(DbPool dbpool,
                                  DBPrpLcompensate dbPrpLcompensate,
                                  String strBusinessNo,
                                  String strTypeCode) throws Exception
    {
      BLPrpLcfee blPrpLCfee = new BLPrpLcfee();
      PrpLcfeeSchema prpLcfeeSchema = new PrpLcfeeSchema();
      blPrpLCfee.getData(strBusinessNo);
      String strContents = "";
      for ( int i =0;i<blPrpLCfee.getSize();i++ )
      {
        prpLcfeeSchema = blPrpLCfee.getArr(i);
        strContents = this.getLcompensate(dbPrpLcompensate,strBusinessNo,prpLcfeeSchema); 
        this.saveIface(dbpool, strBusinessNo, strTypeCode, strContents,i);
      }
    }

    /**
     * XXXXX��ӿ����ݵ��ӿ����ݽ�����PrpIfaceExchange
     *@Author	³��
     *@param 	dbpool,strBusinessNo,strTypeCode,strContents
     *@return
    */
    public void	saveIface(DbPool dbpool,
                                  String strBusinessNo,
                                  String strTypeCode,
                                  String strContents) throws Exception,SQLException,NamingException
    {
      
      PrpIfaceExchangeSchema prpIfaceExchangeSchema = new PrpIfaceExchangeSchema();

      String strId = "";
      String strFreeitem1 = "0";   
      String strTs = "";
      String strDr = "0";          
      int intDealNum  = 0;
      
      strId = this.getMaxID();
      
      if (strTypeCode.equals("1005"))
        strId = strId.substring(0,10)
                  + String.valueOf(Integer.parseInt(strId.substring(10)) + 1);
      strTs  = new ChgDate().getCurrentTime("yyyy-MM-dd HH:mm:ss");
      
      prpIfaceExchangeSchema.setId(strId);
      prpIfaceExchangeSchema.setTypeCode(strTypeCode);
      prpIfaceExchangeSchema.setSynCode(strBusinessNo);    
      prpIfaceExchangeSchema.setContents(strContents);
      prpIfaceExchangeSchema.setDealNum(String.valueOf(intDealNum));
      prpIfaceExchangeSchema.setFreeitem1(strFreeitem1);
      prpIfaceExchangeSchema.setFreeitem2("null");
      prpIfaceExchangeSchema.setFreeitem3("null");
      prpIfaceExchangeSchema.setFreeitem4("null");
      prpIfaceExchangeSchema.setFreeitem5("null");
      prpIfaceExchangeSchema.setTs(strTs);
      prpIfaceExchangeSchema.setDr(strDr);
      this.setArr(prpIfaceExchangeSchema);
      
      
      logger.info("Iface_exchange=====ID:"+strId);
      logger.info("Iface_exchange=====TypeCode:"+strTypeCode);
      logger.info("Iface_exchange=====SynCode:"+strBusinessNo);
      
      this.save(dbpool);
      this.initArr();
    }

    /**
     * ���ͽӿ����ݵ��ӿ����ݽ�����PrpIfaceExchange,����ID�ۼƱ���
     *@Author	³��
     *@param 	dbpool,strBusinessNo,strTypeCode,strContents,i
     *@return
     *@date    20041018
    */
    public void	saveIface(DbPool dbpool,
                          String strBusinessNo,
                          String strTypeCode,
                          String strContents,
                          int i) throws Exception,SQLException,NamingException
    {
      
      PrpIfaceExchangeSchema prpIfaceExchangeSchema = new PrpIfaceExchangeSchema();

      String strId = "";
      String strFreeitem1 = "0";   
      String strTs = "";
      String strDr = "0";          
      int intDealNum  = 0;
      
      strId = this.getMaxID();
      strId = strId.substring(0,10)
            + String.valueOf(Integer.parseInt(strId.substring(10)) + i);
      strTs = new ChgDate().getCurrentTime("yyyy-MM-dd HH:mm:ss");
      
      prpIfaceExchangeSchema.setId(strId);
      prpIfaceExchangeSchema.setTypeCode(strTypeCode);
      prpIfaceExchangeSchema.setSynCode(strBusinessNo);    
      prpIfaceExchangeSchema.setContents(strContents);
      prpIfaceExchangeSchema.setDealNum(String.valueOf(intDealNum));
      prpIfaceExchangeSchema.setFreeitem1(strFreeitem1);
      prpIfaceExchangeSchema.setFreeitem2("null");
      prpIfaceExchangeSchema.setFreeitem3("null");
      prpIfaceExchangeSchema.setFreeitem4("null");
      prpIfaceExchangeSchema.setFreeitem5("null");
      prpIfaceExchangeSchema.setTs(strTs);
      prpIfaceExchangeSchema.setDr(strDr);
      this.setArr(prpIfaceExchangeSchema);
      
      
      logger.info("Iface_exchange=====ID:"+strId);
      logger.info("Iface_exchange=====TypeCode:"+strTypeCode);
      logger.info("Iface_exchange=====SynCode:"+strBusinessNo);
      
      this.save(dbpool);
      this.initArr();
    }

    /**
    * XXXXX��ҵ��Ա�ӿ����ݵ��ӿ����ݽ�����,
    *@Author	³��
    *@param 	dbpool,dbPrpDuser
    *@return
    *@data      20041020
    */
    public void saveIfaceExchange(DbPool dbpool,
                                  DBPrpDuser dbPrpDuser,
                                  String strBusinessNo,
                                  String strUserCode) throws Exception
    {
      String strContents = "";
      strContents = getDuser(dbPrpDuser,strUserCode);
      this.saveIface(dbpool,strBusinessNo,"0001",strContents);
    }

    /**
    * XXXXX��������ͣ��˽ӿ�������ں���
    *@Author	³��
    *@param 	dbpool,dbPrpDagent
    *@return
    *@data      20041020
    */
    public void saveIfaceExchange(DbPool dbpool,
                                  DBPrpDagent dbPrpDagent,
                                  String strBusinessNo,
                                  String strUserCode)throws Exception
    {
      String strContents = "";
      strContents = getDagent(dbPrpDagent,strUserCode);
      this.saveIface(dbpool,strBusinessNo,"0005",strContents);
    }

    /**
     * ���ýɷѼƻ��洢����
     *@Author	Īչ��
     *@param 	dbpool,strBusinessNo,strTypeCode
     *@return
    */
    public void saveIPlan(DbPool dbpool,
                          String strBusinessNo,
                          String strTypeCode,
                          int intSize)throws Exception
    {
      String strContents  = "";
      Vector vecPlanContents =  new Vector();
      vecPlanContents = this.getPlan(strBusinessNo);
      for(int i=0;i<vecPlanContents.size();i++)
      {
        strContents = (String)vecPlanContents.get(i);  
        this.saveIface(dbpool,strBusinessNo,strTypeCode,strContents,intSize+i+1);
      }
    }

    /**
     * ��ȡ�ӿ����ݽ�����PrpIfaceExchange���ID��
     *@Author	³��
     *@param 	��
     *@return   strId
    */
    public String getMaxID() throws Exception
    {
      DbPool dbpool = new DbPool();
      String strSQL = "";
      String strId  = "";
      strSQL = "SELECT  MAX(Id) AS Id FROM Iface_Exchange";
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        ResultSet resultSet = dbpool.query(strSQL);
        while (resultSet.next())
        {
          strId = resultSet.getString("Id");
          if(strId==null || strId.equals(""))  
            strId = "DACCFFFFFF1000000000";
          else
            strId = strId.substring(0,10)
                  + String.valueOf(Integer.parseInt(strId.substring(10)) + 1);
        }
      }
      catch(Exception exception)
      {
        dbpool.close();
        throw exception;
      }
      finally {
        dbpool.close();
      }
      return strId;
    }

    /**
     * ����XX�ӿ�����
     *@Author  ³��
     *@param   DBPrpCmain,PolicyNo
     *@return  strContents
     *@date    20041018
    */
    public String getPolicy(PrpCmainSchema prpCmainSchema,
                            PrpCfeeSchema prpCfeeSchema,
                            String strPolicyNo ) throws Exception
    {
      String	strSeparator    = "\"^\"";
      String	strPolicyType	= "I";  
      String	strPolicyPeriod	= "S";  
      String    strKindContents ="";
      if(prpCmainSchema.getClassCode().equals("05"))
        strKindContents =this.getItemkind(prpCfeeSchema);

      String	strContents = "\"" + strPolicyNo        + strSeparator
                            +  prpCmainSchema.getComCode()	+ strSeparator
                            +  strPolicyNo	+ strSeparator
                            +  strPolicyType	+ strSeparator
                            +  strPolicyPeriod	+ strSeparator
                            +  prpCmainSchema.getInsuredName()    + strSeparator 
                            +  prpCmainSchema.getHandler1Code()   + strSeparator
                            
                            +  blPrpAccCompare.translateCode( "BusinessCode",prpCmainSchema.getBusinessNature().trim())+ strSeparator
                            +  prpCmainSchema.getAgentCode()      + strSeparator
                            +  blPrpAccCompare.translateCode( "RiskCode",prpCmainSchema.getRiskCode().trim())       + strSeparator
                            
                            +  prpCfeeSchema.getCurrency()    + strSeparator
                            +  prpCfeeSchema.getPremium()     + strSeparator
                            +  prpCmainSchema.getUnderWriteEndDate() + strSeparator
                            +  prpCmainSchema.getStartDate() + strSeparator
                            +  prpCmainSchema.getEndDate()   + strSeparator
                            +  ""  + strSeparator
                            +  ""  + strSeparator
                            +  ""  + strSeparator
                            +  ""  + strSeparator
                            +  ""  + strSeparator
                            +  prpCmainSchema.getDisRate()   + strSeparator 
                            +  prpCmainSchema.getAppliName() + strSeparator  
                            +  ""  + strSeparator
                            +  ""  + strSeparator
                            +  strKindContents + "\"";
      return strContents;
    }

    /**
     * ����Ԥ��ӿ�����
     *@Author  ³��
     *@param   dbPrpLprepay,strBusinessNo
     *@return  strContents
    */
    public String getLprepay(DBPrpLprepay  dbPrpLprepay,
                             String iBusinessNo) throws Exception
    {
      String strSeparator   = "\"^\"";
      String strContents    = "";
      if( iBusinessNo!=null && iBusinessNo.charAt(0)=='Y' )
      {
        strContents = "\"" + iBusinessNo + strSeparator
                    + dbPrpLprepay.getPolicyNo() + strSeparator
                    + iBusinessNo + strSeparator
                    + dbPrpLprepay.getClaimNo() + strSeparator
                    + "N" + strSeparator
                    + dbPrpLprepay.getCurrency() + strSeparator
                    + dbPrpLprepay.getSumPrePaid() + strSeparator
                    + "0" + strSeparator  
                    + dbPrpLprepay.getUnderWriteEndDate() + strSeparator
                    + "" + strSeparator
                    + "" + strSeparator
                    + "" + strSeparator
                    + "" + strSeparator
                    + "" + "\"";
      }
      return strContents;
    }

    /**
     * ����ʵ��ӿ�����
     *@Author  ³��
     *@param   dbPrpLcompensate,strBusinessNo
     *@return  strContents
    */
    public String getLcompensate(DBPrpLcompensate  dbPrpLcompensate,
                                 String iBusinessNo,
                                 PrpLcfeeSchema prpLcfeeSchema) throws Exception
    {
      String strSeparator   = "\"^\"";
      String strContents    = "";
      String strCheckPaid   = "";
      String strKindContents= "";
      
      
      String strChargeCode  = "";
      String strTranChargeCode  = "";
      double douCheck =0.0d;             
      double douJudge =0.0d;             
      double douOther =0.0d;             
      double douSum   =0.0d;             
      double douSumPaid   =0.0d;         
      int i = 0;
      BLPrpLcharge blPrpLcharge = new BLPrpLcharge();
      PrpLchargeSchema prpLchargeSchema = new PrpLchargeSchema();
      BLPrpAccCompare  chargeAccCompare = new BLPrpAccCompare();

      String strCondition  =" compensateno='"+prpLcfeeSchema.getCompensateNo()+"'"
                           +" and Currency='"+prpLcfeeSchema.getCurrency()+"'";
                       blPrpLcharge.query(strCondition);
      chargeAccCompare.query(" codetype = 'ChargeCode' ");

      for ( i=0;i<blPrpLcharge.getSize();i++ )
      {
        strChargeCode = chargeAccCompare.translateCode("ChargeCode",blPrpLcharge.getArr(i).getChargeCode().trim());
        if(strChargeCode.equals("01"))
        {
          douCheck = douCheck +
              Double.parseDouble(blPrpLcharge.getArr(i).getChargeAmount());
        }
        else if(strChargeCode.equals("02"))
        {
          douJudge = douJudge +
              Double.parseDouble(blPrpLcharge.getArr(i).getChargeAmount());
        }
        else
        {
          douOther = douOther +
              Double.parseDouble(blPrpLcharge.getArr(i).getChargeAmount());
        }
      }

      douSum = douCheck+douJudge+douOther;
      douSumPaid = douSum + Double.parseDouble(prpLcfeeSchema.getSumPaid());
      if(dbPrpLcompensate.getClassCode().equals("05"))
        strKindContents =this.getItemkind(prpLcfeeSchema);

      strContents = "\"" + iBusinessNo + strSeparator
                  + dbPrpLcompensate.getPolicyNo() + strSeparator
                  + iBusinessNo + strSeparator
                  + dbPrpLcompensate.getClaimNo() + strSeparator
                  + "Y" + strSeparator
                  + prpLcfeeSchema.getCurrency() + strSeparator
                  + douSumPaid + strSeparator
                  + douSum + strSeparator
                  + dbPrpLcompensate.getUnderWriteEndDate() + strSeparator
                  + strKindContents + strSeparator
                  + douCheck + strSeparator
                  + douJudge + strSeparator
                  + douOther + strSeparator
                  + "" + "\"";
      return strContents;
      
    }

    /**
     * ���������ӿ�����
     *@Author	³��
     *@param 	dbPrpPhead,EndorseNo
     *@return
     */
    public String getEndorse(DBPrpPhead dbPrpPhead,
                              PrpPfeeSchema prpPfeeSchema,
                              String strEndorseNo) throws Exception
    {
      String    strKindContents ="";
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      DBPrpPmain dbPrpPmain = new DBPrpPmain();
      dbPrpCmain.getInfo(dbPrpPhead.getPolicyNo());
      dbPrpPmain.getInfo(dbPrpPhead.getEndorseNo());
      String  strSeparator     =  "\"^\"";
      String  strEndorType     =  dbPrpCmain.getOthFlag();
      if ( strEndorType!=null && strEndorType.length()>=4 )
      {
        if (strEndorType.charAt(2) != '0')
          strEndorType = "3"; 
        else if (strEndorType.charAt(3) == '1')
          strEndorType = "2"; 
        else
          strEndorType = "1"; 
      }
      if(dbPrpCmain.getClassCode().equals("05"))
        strKindContents =this.getItemkind(prpPfeeSchema);
      String  strContents = "\"" + strEndorseNo  +  strSeparator
                        +	dbPrpPhead.getPolicyNo()		+	strSeparator
                        +	strEndorseNo		+	strSeparator
                        +	strEndorType		+	strSeparator
                        +	prpPfeeSchema.getCurrency() +	strSeparator
                        +	prpPfeeSchema.getChgPremium()+	strSeparator
                        +	dbPrpPhead.getUnderWriteEndDate()+strSeparator
                        +	"0"			+	strSeparator
                        +	"0"			+	strSeparator
                        +	"0"			+	strSeparator
                        +	"0"			+	strSeparator
                        +	""			+	strSeparator
                        +	strKindContents		+	strSeparator
                        +	""			+	strSeparator
                        +	""         + "\"";
      return strContents;
    }

    /**
    * ����ɷѼƻ��ӿ�����
    *@Author	Īչ��
    *@param 	EndorseNo
    *@return
    */
    public Vector getPlan(String strBusinessNo) throws Exception
    {
      Vector vecPlanContents = new Vector();
      BLPrpCplan blPrpCplan  = new BLPrpCplan();
      BLPrpCPplan blPrpCPplan = new BLPrpCPplan();
      PrpCplanSchema prpCplanSchema = null;
      PrpCPplanSchema  prpCPplanSchema = null;
      String	strSeparator = "\"^\"";
      String	strContents  = "";
      String 	strSerialNo  = "";
      String 	strPlanDate  = "";
      String 	strCurrency  = "";
      String 	strPlanFee   = "";
      int intPlanSize = 0;
      int i = 0;
      if (strBusinessNo.charAt(0)=='E')
      {
        blPrpCPplan.query(" EndorseNo ='" + strBusinessNo + "'");
        intPlanSize = blPrpCPplan.getSize();
      }
      else
      {
        blPrpCplan.getData(strBusinessNo);
        intPlanSize = blPrpCplan.getSize();
      }
      for(i=0;i<intPlanSize;i++)
      {
        if (strBusinessNo.charAt(0) == 'E')
        {
          prpCPplanSchema = blPrpCPplan.getArr(i);
          strSerialNo = prpCPplanSchema.getSerialNo();
          strPlanDate = prpCPplanSchema.getPlanDate();
          strCurrency = prpCPplanSchema.getCurrency();
          strPlanFee = prpCPplanSchema.getPlanFee();
        }
        else
        {
          prpCplanSchema = blPrpCplan.getArr(i);
          strSerialNo = prpCplanSchema.getSerialNo();
          strPlanDate = prpCplanSchema.getPlanDate();
          strCurrency = prpCplanSchema.getCurrency();
          strPlanFee = prpCplanSchema.getPlanFee();
        }
        strContents = "\"" + strBusinessNo + strSeparator
            + strSerialNo + strSeparator
            + strPlanDate + strSeparator
            + strPlanDate + strSeparator
            + strCurrency + strSeparator
            + strPlanFee + strSeparator
            + "" + strSeparator
            + "" + strSeparator
            + "" + strSeparator
            + "" + strSeparator
            + "" + "\"";
        vecPlanContents.add(strContents);
      }
      return vecPlanContents;
    }

    /**
    * ����ҵ��Ա�ӿ�����
    *@Author	³��
    *@param 	dbpool,dbPrpDuser
    *@return
    *@data      20041020
    */
    public String getDuser(DBPrpDuser dbPrpDuser,String strUserCode) throws Exception
    {
      String  strContents = "";
      String  strSeparator =	"\"^\"";
      strContents = "\"" + strUserCode + strSeparator
                  + dbPrpDuser.getUserName() + strSeparator
                  + dbPrpDuser.getComCode() + strSeparator
                  + ""    
                  + "\"" ;
      return strContents;
    }

    /**
    * ����������ͣ��˽ӿ�����
    *@Author	³��
    *@param 	dbpool,dbPrpDagent
    *@return
    *@data      20041020
    */
    public String getDagent(DBPrpDagent dbPrpDagent,String strUserCode) throws Exception
    {
      String  strContents = "";
      String  strSeparator =	"\"^\"";
      strContents = "\"" + strUserCode + strSeparator
                  + dbPrpDagent.getComCode() + strSeparator
                  + dbPrpDagent.getAgentCode() + strSeparator
                  + dbPrpDagent.getAgentName() + strSeparator
                  + dbPrpDagent.getAgentName() + strSeparator
                  + "" + strSeparator    
                  + "" + strSeparator    
                  + "" + strSeparator    
                  + "" + strSeparator    
                  + "" + strSeparator    
                  + "" + strSeparator    
                  + "\"" ;
      return strContents;
    }

    /**
     * ����XXXXXXX��ӿ�����
     *@Author  Īչ��
     *@param   Currency,PolicyNo
     *@return  strContents
     *@date    20041214
    */
    public String getItemkind(PrpCfeeSchema prpCfeeSchema) throws Exception
    {
      String    strContents   ="";
      String    strSeparator  = "|";
      String    strRiskCode   ="";
      String    strConditions ="select riskcode,kindCode,currency,amount,sum(premium) as premium"
                              +" from PrpCitemKind"
                              +" where  policyno='"+prpCfeeSchema.getPolicyNo()+"' and currency ='"+prpCfeeSchema.getCurrency()+"'"
                              +" group by riskcode,currency,kindCode,amount";

      BLPrpCitemKind blPrpCitemKind = new BLPrpCitemKind();
      PrpCitemKindSchema prpCitemKindSchema = new PrpCitemKindSchema();
      BLPrpCitemCar blPrpCitemCar = new BLPrpCitemCar();
      blPrpCitemKind.querySumBySQL(strConditions);

      for (int i=0; i<blPrpCitemKind.getSize();i++)
      {
        prpCitemKindSchema = blPrpCitemKind.getArr(i);
        
        String strKindCode = prpCitemKindSchema.getRiskCode() +  prpCitemKindSchema.getKindCode();

        strKindCode =blPrpAccCompare.translateCode("KindCode",strKindCode.trim());

        if(i==0)
        {
          strContents =  strKindCode
                      +  "="
                      +  prpCitemKindSchema.getPremium();
        }
        else
        {
          strContents = strContents + strSeparator
                      + strKindCode
                      + "="
                      + prpCitemKindSchema.getPremium();
        }
        
      }
      blPrpCitemCar.getData(prpCfeeSchema.getPolicyNo());
      
      String    strUseNatureCode ="";

      if(blPrpCitemCar.getSize() > 0)
      {
        if(blPrpCitemCar.getArr(0).getRiskCode().equals("DAB"))
          strUseNatureCode = "04";
        else if(blPrpCitemCar.getArr(0).getRiskCode().equals("DAE"))
          strUseNatureCode = "07";
        else if(blPrpCitemCar.getArr(0).getCarKindCode().equals("J0"))
          strUseNatureCode = "05";
        else if((blPrpCitemCar.getArr(0).getCarKindCode().equals("T0"))||(blPrpCitemCar.getArr(0).getCarKindCode().equals("TZ")))
          strUseNatureCode = "06";
        else if(blPrpCitemCar.getArr(0).getUseNatureCode().equals("84"))
          strUseNatureCode = "01";
        else if(blPrpCitemCar.getArr(0).getUseNatureCode().equals("85"))
          strUseNatureCode = "03";
        else
          strUseNatureCode = "02";

        strContents = strUseNatureCode + strSeparator + strContents ;
      }
      
      return strContents;
    }

    /**
     * ��������XXXXX��ӿ�����
     *@Author  Īչ��
     *@param   Currency,PolicyNo
     *@return  strContents
     *@date    20041214
    */
    public String getItemkind(PrpPfeeSchema prpPfeeSchema) throws Exception
    {
      String    strContents   ="";
      String	strSeparator  = "|";
      String    strRiskCode   ="";

      String    strConditions ="select RiskCode,KindCode,Currency,ChgAmount,sum(ChgPremium) as ChgPremium"
                              +" from PrpPitemKind"
                              +" where  EndorseNo='"+prpPfeeSchema.getEndorseNo()+"' and Currency ='"+prpPfeeSchema.getCurrency()+"'"
                              +" group by RiskCode,Currency,KindCode,ChgAmount";

      BLPrpPitemKind blPrpPitemKind = new BLPrpPitemKind();
      PrpPitemKindSchema prpPitemKindSchema = new PrpPitemKindSchema();
      blPrpPitemKind.querySumBySQL(strConditions);

      for (int i=0; i<blPrpPitemKind.getSize();i++)
      {
        prpPitemKindSchema = blPrpPitemKind.getArr(i);
        
        String strKindCode = prpPitemKindSchema.getRiskCode() +  prpPitemKindSchema.getKindCode();

        strKindCode =blPrpAccCompare.translateCode("KindCode",strKindCode.trim());

        if(i==0)
        {
          strContents =  strKindCode
                      +  "="
                      +  prpPitemKindSchema.getChgPremium();
        }
        else
        {
          strContents = strContents
                      + strSeparator
                      + strKindCode
                      + "="
                      + prpPitemKindSchema.getChgPremium();
        }
        
      }
      return strContents;
    }

    /**
     * ����ʵ��XXXXX��ӿ�����
     *@Author  Īչ��
     *@param   Currency,PolicyNo
     *@return  strContents
     *@date    20041214
    */
    public String getItemkind(PrpLcfeeSchema prpLcfeeSchema) throws Exception
    {
      String    strContents   ="";
      String	strSeparator  = "|";
      String    strRiskCode   ="";

      String    strConditions ="select sum(SumRealpay) as SumRealpay,KindCode,Currency4,RiskCode "
                              +" from ("
                              +" select RiskCode,SumRealpay,KindCode,Currency4 from PrpLloss where compensateno='"+prpLcfeeSchema.getCompensateNo()+"' and Currency4='"+prpLcfeeSchema.getCurrency()+"'"
                              +" union "
                              +" select RiskCode,SumRealpay,KindCode,Currency from PrpLcharge where compensateno='"+prpLcfeeSchema.getCompensateNo()+"' and Currency='"+prpLcfeeSchema.getCurrency()+"'"
                              +" union "
                              +" select RiskCode,SumRealpay,KindCode,Currency from PrpLpersonLoss where compensateno='"+prpLcfeeSchema.getCompensateNo()+"' and Currency='"+prpLcfeeSchema.getCurrency()+"'"
                              +") group by RiskCode,KindCode,Currency4";

      BLPrpLloss blPrpLloss = new BLPrpLloss();
      PrpLlossSchema prpLlossSchema = new PrpLlossSchema();
      blPrpLloss.querySumBySQL(strConditions);

      for (int i=0; i<blPrpLloss.getSize();i++)
      {
        prpLlossSchema = blPrpLloss.getArr(i);
        
        String strKindCode = prpLlossSchema.getRiskCode() +  prpLlossSchema.getKindCode();

        strKindCode =blPrpAccCompare.translateCode("KindCode",strKindCode.trim());

        if(i==0)
        {
          strContents =  strKindCode
                      +  "="
                      +  prpLlossSchema.getSumRealPay();
        }
        else
        {
          strContents = strContents
                      + strSeparator
                      + strKindCode
                      + "="
                      + prpLlossSchema.getSumRealPay();
        }
        
      }
      return strContents;
    }

    
    /**
     * ����׷���ӿ����ݵ��ӿ����ݽ�����PrpIfaceExchange��ں���
     * @param strBusinessNo��������
     * @param strTimes��׷������
     * @param strTypeCode���ӿ���������
     * @throws java.lang.Exception
     * @author wanghaoning
     */
    public void	saveIfaceExchange(String strBusinessNo, String strTimes,
                                  String strTypeCode) throws Exception
    {
      DbPool        dbpool        = new DbPool();
      DBPrpLreplevy dbPrpLreplevy = new DBPrpLreplevy();
      String        strContents   = "";

      
      try {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          dbPrpLreplevy.getInfo(dbpool, strBusinessNo, strTimes);
          strContents = this.getLreplevy(dbPrpLreplevy);
          dbpool.beginTransaction();
          this.saveIface(dbpool, strBusinessNo, strTypeCode, strContents);
          dbpool.commitTransaction();
      } catch (Exception e) {
          dbpool.rollbackTransaction();
      }
      finally {
          dbpool.close();
      }
    }

    /**
     * ����׷����������
     * @param dbPrpLreplevy��׷�����ݼ���
     * @return strContents
     * @throws java.lang.Exception
     * @author wanghaoning
     */
    public String getLreplevy(DBPrpLreplevy dbPrpLreplevy) throws Exception
    {
      String strLreplevyNo = "";
      String strContents   = "";
      String strSeparator  = "\"^\"";

      strLreplevyNo = dbPrpLreplevy.getClaimNo() + "-" + dbPrpLreplevy.getTimes();
      strContents = "\"" + strLreplevyNo + strSeparator
                  + dbPrpLreplevy.getPolicyNo() + strSeparator
                  + strLreplevyNo + strSeparator
                  + dbPrpLreplevy.getClaimNo() + strSeparator
                  + dbPrpLreplevy.getCurrency() + strSeparator
                  + dbPrpLreplevy.getSumValidFee() + strSeparator
                  + dbPrpLreplevy.getValidDate() + strSeparator
                  + "" + strSeparator
                  + "" + strSeparator
                  + "" + strSeparator
                  + "" + strSeparator
                  + "" + strSeparator
                  + "\"" ;
      return strContents;
    }
    

    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
