package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import java.text.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJrefRec;
import com.sp.prpall.schema.PrpJrefRecSchema;
import com.sp.prpall.pubfun.Bill;
import com.sp.account.interf.*;
import com.sp.utility.string.ChgDate;
import com.sp.prpall.dbsvr.lp.DBPrpLcompensate;
import com.sp.prpall.dbsvr.pg.DBPrpPfee;
import com.sp.prpall.dbsvr.lp.DBPrpLprepay;
import com.sp.prpall.dbsvr.lp.DBPrpLcfee;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;

/**
 * ����PrpJrefRec��BL��
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-07-24</p>
 * @Author     : X
 * @version 1.0
 */
public class BLPrpJrefRec{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */
    public BLPrpJrefRec(){
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
     *����һ��PrpJrefRecSchema��¼
     *@param iPrpJrefRecSchema PrpJrefRecSchema
     *@throws Exception
     */
    public void setArr(PrpJrefRecSchema iPrpJrefRecSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJrefRecSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpJrefRecSchema��¼
     *@param index �±�
     *@return һ��PrpJrefRecSchema����
     *@throws Exception
     */
    public PrpJrefRecSchema getArr(int index) throws Exception
    {
     PrpJrefRecSchema prpJrefRecSchema = null;
       try
       {
        prpJrefRecSchema = (PrpJrefRecSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJrefRecSchema;
     }
    /**
     *ɾ��һ��PrpJrefRecSchema��¼
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
      DBPrpJrefRec dbPrpJrefRec = new DBPrpJrefRec();
      if (iLimitCount > 0 && dbPrpJrefRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJrefRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJrefRec WHERE " + iWherePart;
        schemas = dbPrpJrefRec.findByConditions(strSqlStatement);
      }
    }
    
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas�����ȴ��������ȡ�����Ϣ�����δȡ�����ٴ���ʷ��Ϣ���ȡ��
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void queryCurrentAndHistory(String iWherePart) throws UserException,Exception
    {
       
       query(iWherePart);
       
       if(this.getSize() == 0){
    	   queryHistory(iWherePart);
       }
    }  
    
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����,����ʷ��Ϣ���ȡ
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart) throws UserException,Exception
    {
       this.queryHistory(iWherePart,Integer.parseInt(SysConfig.getProperty("QUERY_LIMIT_COUNT").trim()));
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas���󣬴���ʷ��Ϣ���ȡ
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void queryHistory(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJrefRec dbPrpJrefRec = new DBPrpJrefRec();
      if (iLimitCount > 0 && dbPrpJrefRec.getCountHistory(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJrefRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJrefRec WHERE " + iWherePart;
        schemas = dbPrpJrefRec.findByConditionsHistory(strSqlStatement);
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
      DBPrpJrefRec dbPrpJrefRec = new DBPrpJrefRec();
      if (iLimitCount > 0 && dbPrpJrefRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJrefRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJrefRec WHERE " + iWherePart;
        schemas = dbPrpJrefRec.findByConditions(dbpool,strSqlStatement);
      }
    }

    public void query(DbPool dbpool, String iWherePart, int intPageNum,
                      int intPageCount) throws UserException, Exception {
      String strSqlStatement = "";
      int intStartNum = 0;
      int intEndNum = 0;

      intStartNum = (intPageNum - 1) * intPageCount;
      intEndNum = intPageNum * intPageCount;

      DBPrpJrefRec dbPrpJrefRec = new DBPrpJrefRec();
      initArr();
      /*
           strSqlStatement = " SELECT * FROM PrpJrefRec WHERE RowId In ( " +
         "Select rid From (Select T.RowId rid,RowNum As LineNum From (" +
         "Select RowId From PrpJrefRec Where " + iWherePart +
         ") T Where RowNum<=" + intEndNum + ") Where LineNum>" + intStartNum +
         ") Order By RegistNo DESC";
       */
      strSqlStatement = " SELECT * FROM ( " +
          "Select RowNum As LineNum,T.* From ( " +
          "Select * From PrpJrefRec Where " + iWherePart +
          ") T Where RowNum<=" + intEndNum + ") Where LineNum>" +
          intStartNum + " Order By ReceiptNo DESC";

      schemas = dbPrpJrefRec.findByConditions(dbpool, strSqlStatement);
    }

    public void query(String iWherePart, int intPageNum, int intPageCount) throws
        UserException, Exception {
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        this.query(dbpool, iWherePart, intPageNum, intPageCount);
      }
      catch (Exception e) {
        throw e;
      }
      finally {
        dbpool.close();
      }
    }


    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJrefRec dbPrpJrefRec = new DBPrpJrefRec();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJrefRec.setSchema((PrpJrefRecSchema)schemas.get(i));
      dbPrpJrefRec.insert(dbpool);
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
    public void cancel(DbPool dbpool,String strReceiptNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpJrefRec WHERE ReceiptNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, strReceiptNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }

    /**
     * ����dbpool��ɾ������
     *@param null null
     *@return ��
     */
    public void cancel(String strReceiptNo ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,strReceiptNo);
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
    public void getData(String strReceiptNo) throws Exception
    {
        DbPool dbpool = new DbPool();

  	try {
  		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
  		getData(dbpool, strReceiptNo);
  	} catch (Exception e) {
  		
  	}finally {
  		dbpool.close();
  	}

    }

    /**
     * ����dbpool����null��ȡ����
     *@param dbpool ���ӳ�
     *@param null null
     *@return ��
     */
    public void getData(DbPool dbpool,String strReceiptNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "ReceiptNo= '" + strReceiptNo + "'";
      query(dbpool,strWherePart,0);
    }

    /**
     * @modify LIJIBIN ���ӷ��ز���strReceiptNo
     * @param iComCode
     * @param iRiskCode
     * @param iSessionID
     * @return strReceiptNo �վݺ�
     * @throws Exception
     */
    public String saveReceipt(String iComCode,String iRiskCode,String iSessionID,String iCancelFlag) throws Exception
    {
      String strReceiptNo = "";
      Bill bill = new Bill();
      BLSffToAccount blSffToAccount =new BLSffToAccount();
      PrpJrefRecSchema prpJrefRecSchema = new PrpJrefRecSchema();
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      DBPrpJrefRec dbPrpJrefRec = null;

      prpJrefRecSchema = this.getArr(0);
      if(iCancelFlag.equals("1"))
      {
        this.cancelReceipt(iCancelFlag);
        return prpJrefRecSchema.getReceiptNo();
      }

      if((prpJrefRecSchema.getRealPayDate()!=null && !prpJrefRecSchema.getRealPayDate().equals(""))||
         !prpJrefRecSchema.getCertiType().equals("E"))
      {
        dbPrpCmain.getInfo(prpJrefRecSchema.getPolicyNo().trim());
        
        if (dbPrpCmain.getOthFlag().trim().substring(6, 7).equals("Y")) {
          
          BLPrpJpayRec blPrpJpayRec = new BLPrpJpayRec();
          String strWhere = " PolicyNo='" + prpJrefRecSchema.getPolicyNo().trim() +
              "' AND SUBSTR(Flag,1,1)<>'1'";
          blPrpJpayRec.getSumFeeByPolicyNo(strWhere);
          if (blPrpJpayRec.getSize() == 0)
            throw new UserException( -98, -1, "BLPrpJrefRec.saveReceipt",
                                    "XX " + prpJrefRecSchema.getPolicyNo() +
                                    " ��û���յ�XX�����������Ѵ���");
          else if (Double.parseDouble(blPrpJpayRec.getArr(0).getRealPayFee()) <=
                   0)
            throw new UserException( -98, -1, "BLPrpJrefRec.saveReceipt",
                                    "XX " + prpJrefRecSchema.getPolicyNo() +
                                    " ��û���յ�XX�����������Ѵ���");
          
          else if (Double.parseDouble(prpJrefRecSchema.getPayFee()) * Double.parseDouble(prpJrefRecSchema.getExchangeRate())
                   - Double.parseDouble(blPrpJpayRec.getArr(0).getRealPayFee()) > 0.005 &&
                   prpJrefRecSchema.getCertiType().equals("E"))
            throw new UserException( -98, -1, "BLPrpJrefRec.saveReceipt",
                                    "XX " + prpJrefRecSchema.getPolicyNo() +
                                    " �����˷ѽ�����XXʵ�ս����������Ѵ���");

        }
      }
      
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        for(int i=0; i<this.getSize(); i++)
        {
          prpJrefRecSchema = this.getArr(i);
          dbPrpJrefRec = new DBPrpJrefRec();
          dbPrpJrefRec.getInfoForUpdate(dbpool,prpJrefRecSchema.getReceiptNo());
          if(dbPrpJrefRec.getFlag().length()>0&&dbPrpJrefRec.getFlag().substring(0,1).equals("1"))
          {
            throw new UserException(-98,-1,"BLPrpJrefRec.saveReceipt","���վ��Ѿ�ע�������������κδ���");
          }
          if(dbPrpJrefRec.getTransFlag().equals("1"))
          {
            throw new UserException(-98,-1,"BLPrpJrefRec.saveReceipt","���վ��Ѿ��������ȷ�ϣ�����������");
          }

          strReceiptNo = prpJrefRecSchema.getReceiptNo();

          if(strReceiptNo==null||strReceiptNo.length()==0)
          {
            
            double dblPayFee = 0.0;
            if (prpJrefRecSchema.getCertiType().equals("E")) {
              DBPrpPfee dbPrpPfee = new DBPrpPfee();
              dbPrpPfee.getInfoForUpdate(dbpool, prpJrefRecSchema.getCertiNo(),
                                         prpJrefRecSchema.getCurrency1());
              dblPayFee = Math.abs(Double.parseDouble(dbPrpPfee.getChgPremium()));
            }
            if (prpJrefRecSchema.getCertiType().equals("Y")) {
              DBPrpLprepay dbPrpLprepay = new DBPrpLprepay();
              dbPrpLprepay.getInfoForUpdate(dbpool, prpJrefRecSchema.getCertiNo());
              dblPayFee = Math.abs(Double.parseDouble(dbPrpLprepay.getSumPrePaid()));
            }
            if (prpJrefRecSchema.getCertiType().equals("C")) {
              DBPrpLcfee dbPrpLcfee = new DBPrpLcfee();
              dbPrpLcfee.getInfoForUpdate(dbpool, prpJrefRecSchema.getCertiNo(),
                                          prpJrefRecSchema.getPolicyNo(),
                                          prpJrefRecSchema.getCurrency1());
              dblPayFee = Math.abs(Double.parseDouble(dbPrpLcfee.getSumPaid()));
            }
            double sumPayFee = this.getSumPayFeeByCertiNo(dbpool,
                "CertiNo='" + prpJrefRecSchema.getCertiNo() +
                "' AND substr(Flag,1,1)<>'1'");

            if (dblPayFee <= sumPayFee)
              throw new UserException( -98, -1, "BLPrpJrefRec.saveReceipt",
                                      "���վ��Ѿ�ǩ���ɹ��������ٴ�ǩ����");
            

            ChgDate chgDate = new ChgDate();
            int intYear = Integer.parseInt(chgDate.getCurrentTime("yyyy"));
            strReceiptNo = bill.getNo("prpjrefrec",iRiskCode,iComCode,intYear,iSessionID);
            prpJrefRecSchema.setReceiptNo(strReceiptNo);
          }
          else
          {
            if (!prpJrefRecSchema.getRealPayFee().equals(""))
            {
              
              String strPayReason = prpJrefRecSchema.getPayReason();
              /****LIJIBIN ADD BEGIN �ж��Ƿ��Ԥ��XX ֻ������ 20040225****/
              if(prpJrefRecSchema.getCertiType().equals("E"))
              {
                
                DBPrpPhead dbPrpPhead = new DBPrpPhead();
                
                
                dbPrpPhead.getInfo(dbpool,prpJrefRecSchema.getCertiNo());
                com.sp.utility.string.Date date1 = new com.sp.utility.string.Date(dbPrpPhead.getValidDate());
                
                com.sp.utility.string.Date date2 = new com.sp.utility.string.Date(prpJrefRecSchema.getRealPayDate());

                if(date1.get(date1.YEAR)>date2.get(date2.YEAR)||date1.get(date1.YEAR)==date2.get(date2.YEAR)&&date1.get(date1.MONTH)>date2.get(date2.MONTH))
                  prpJrefRecSchema.setPayReason("P20");
              }
              /****LIJIBIN ADD END   �ж��Ƿ��Ԥ��XX ֻ������ 20040225****/

              blSffToAccount.refToRawVoucher(dbpool,prpJrefRecSchema,iCancelFlag);
              prpJrefRecSchema.setTransFlag("1"); 
              prpJrefRecSchema.setPayReason(strPayReason);  
              if(prpJrefRecSchema.getCertiType().equals("J")||prpJrefRecSchema.getCertiType().equals("C"))
              {
                DBPrpLcompensate dbPrpLcompensate = new DBPrpLcompensate();
                dbPrpLcompensate.getInfo(dbpool,prpJrefRecSchema.getCertiNo());
                if(dbPrpLcompensate.getSumPrePaid().length()>0&&Double.parseDouble(dbPrpLcompensate.getSumPrePaid())!=0)
                {
                  blSffToAccount.compensateToRawVoucher(dbpool,dbPrpLcompensate);
                }
              }
            }

          }
          this.cancel(dbpool,strReceiptNo);
        }
        save(dbpool);

        dbpool.commitTransaction();
      }
      catch (UserException ue)
      {
        dbpool.rollbackTransaction();
        throw ue;
      }
      catch (Exception e)
      {
        dbpool.rollbackTransaction();
        
        bill.putNo("prpjrefrec",strReceiptNo);
        
        throw e;
      }
      finally {
        dbpool.close();
      }
      return strReceiptNo;
    }

    /**
     * ���վ�XXXXX��
     * @Author     : X
     * @param iRiskCode String XXXXX�ִ���
     * @param iSessionID String
     * @param iCancelFlag String
     * @throws Exception
     * @return String
     */
    public String saveReceiptAll(String iRiskCode,String iSessionID,String iCancelFlag) throws Exception
    {
      String[] strReceiptNo = null;
      String iComCode = "";
      Bill bill = new Bill();
      BLSffToAccount blSffToAccount =new BLSffToAccount();
      PrpJrefRecSchema prpJrefRecSchema = new PrpJrefRecSchema();
      DBPrpCmain dbPrpCmain = new DBPrpCmain();
      DBPrpJrefRec dbPrpJrefRec = null;

      if(iCancelFlag.equals("1"))
      {
        this.cancelReceiptAll(iCancelFlag);
        return this.getArr(0).getReceiptNo();
      }
      try
      {
        for (int i = 0; i < this.getSize(); i++) {
          prpJrefRecSchema = this.getArr(i);
          dbPrpCmain.getInfo(prpJrefRecSchema.getPolicyNo().trim());
          
          if (dbPrpCmain.getOthFlag().trim().substring(6, 7).equals("Y")) {
            
            BLPrpJpayRec blPrpJpayRec = new BLPrpJpayRec();
            String strWhere = " PolicyNo='" + prpJrefRecSchema.getPolicyNo().trim() +
                "' AND SUBSTR(Flag,1,1)<>'1'";
            blPrpJpayRec.getSumFeeByPolicyNo(strWhere);
            if (blPrpJpayRec.getSize() == 0)
              throw new UserException( -98, -1, "BLPrpJrefRec.saveReceiptAll",
                                      "XX " + prpJrefRecSchema.getPolicyNo() +
                                      " ��û���յ�XX�����������Ѵ���");
            else if (Double.parseDouble(blPrpJpayRec.getArr(0).getRealPayFee()) <= 0)
              throw new UserException( -98, -1, "BLPrpJrefRec.saveReceipt",
                                      "XX " + prpJrefRecSchema.getPolicyNo() +
                                      " ��û���յ�XX�����������Ѵ���");
            
            else if (Double.parseDouble(prpJrefRecSchema.getPayFee()) * Double.parseDouble(prpJrefRecSchema.getExchangeRate())
                     - Double.parseDouble(blPrpJpayRec.getArr(0).getRealPayFee()) > 0.005 &&
                     prpJrefRecSchema.getCertiType().equals("E"))
              throw new UserException( -98, -1, "BLPrpJrefRec.saveReceiptAll",
                                  "XX " + prpJrefRecSchema.getPolicyNo() +
                                  " �����˷ѽ�����XXʵ�ս����������Ѵ���");

          }
        }
      }
      catch(UserException ue)
      {
        throw ue;
      }
      catch(Exception e)
      {
        throw e;
      }

     DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        strReceiptNo = new String[this.getSize()];
        for(int i=0; i<this.getSize(); i++)
        {
          prpJrefRecSchema = this.getArr(i);
          dbPrpJrefRec = new DBPrpJrefRec();
          dbPrpJrefRec.getInfoForUpdate(dbpool,prpJrefRecSchema.getReceiptNo());
          if(dbPrpJrefRec.getFlag().length()>0&&dbPrpJrefRec.getFlag().substring(0,1).equals("1"))
          {
            throw new UserException(-98,-1,"BLPrpJrefRec.saveReceipt","���վ��Ѿ�ע�������������κδ���");
          }
          if(dbPrpJrefRec.getTransFlag().equals("1"))
          {
            throw new UserException(-98,-1,"BLPrpJrefRec.saveReceipt","���վ��Ѿ��������ȷ�ϣ�����������");
          }

          strReceiptNo[i] = prpJrefRecSchema.getReceiptNo();

          if(strReceiptNo[i]==null||strReceiptNo[i].length()==0)
          {
            
            double dblPayFee = 0.0;
            if (prpJrefRecSchema.getCertiType().equals("E")) {
              DBPrpPfee dbPrpPfee = new DBPrpPfee();
              dbPrpPfee.getInfoForUpdate(dbpool, prpJrefRecSchema.getCertiNo(),
                                         prpJrefRecSchema.getCurrency1());
              dblPayFee = Math.abs(Double.parseDouble(dbPrpPfee.getChgPremium()));
            }
            if (prpJrefRecSchema.getCertiType().equals("Y")) {
              DBPrpLprepay dbPrpLprepay = new DBPrpLprepay();
              dbPrpLprepay.getInfoForUpdate(dbpool, prpJrefRecSchema.getCertiNo());
              dblPayFee = Math.abs(Double.parseDouble(dbPrpLprepay.getSumPrePaid()));
            }
            if (prpJrefRecSchema.getCertiType().equals("C")) {
              DBPrpLcfee dbPrpLcfee = new DBPrpLcfee();
              dbPrpLcfee.getInfoForUpdate(dbpool, prpJrefRecSchema.getCertiNo(),
                                          prpJrefRecSchema.getPolicyNo(),
                                          prpJrefRecSchema.getCurrency1());
              dblPayFee = Math.abs(Double.parseDouble(dbPrpLcfee.getSumPaid()));
            }
            double sumPayFee = this.getSumPayFeeByCertiNo(dbpool,
                "CertiNo='" + prpJrefRecSchema.getCertiNo() +
                "' AND substr(Flag,1,1)<>'1'");

            if (dblPayFee <= sumPayFee)
              throw new UserException( -98, -1, "BLPrpJrefRec.saveReceipt",
                                      "���վ��Ѿ�ǩ���ɹ��������ٴ�ǩ����");
            
            iComCode = prpJrefRecSchema.getComCode().trim();
            ChgDate chgDate = new ChgDate();
            int intYear = Integer.parseInt(chgDate.getCurrentTime("yyyy"));
            strReceiptNo[i] = bill.getNo("prpjrefrec",iRiskCode,iComCode,intYear,iSessionID);
            prpJrefRecSchema.setReceiptNo(strReceiptNo[i]);
            prpJrefRecSchema.setContractNo(strReceiptNo[0]);
            this.setArr(prpJrefRecSchema,i);
          }
          else
          {
            if (!prpJrefRecSchema.getRealPayFee().equals(""))
            {
              String strPayReason = prpJrefRecSchema.getPayReason();
              /****LIJIBIN ADD BEGIN �ж��Ƿ��Ԥ��XX ֻ������ 20040225****/
              if(prpJrefRecSchema.getCertiType().equals("E"))
              {
                
                DBPrpPhead dbPrpPhead = new DBPrpPhead();
                
                
                dbPrpPhead.getInfo(dbpool,prpJrefRecSchema.getCertiNo());
                com.sp.utility.string.Date date1 = new com.sp.utility.string.Date(dbPrpPhead.getValidDate());
                
                com.sp.utility.string.Date date2 = new com.sp.utility.string.Date(prpJrefRecSchema.getRealPayDate());

                if(date1.get(date1.YEAR)>date2.get(date2.YEAR)||date1.get(date1.YEAR)==date2.get(date2.YEAR)&&date1.get(date1.MONTH)>date2.get(date2.MONTH))
                  prpJrefRecSchema.setPayReason("P20");
              }
              /****LIJIBIN ADD END   �ж��Ƿ��Ԥ��XX ֻ������ 20040225****/

              blSffToAccount.refToRawVoucher(dbpool,prpJrefRecSchema,iCancelFlag);
              prpJrefRecSchema.setTransFlag("1"); 
              prpJrefRecSchema.setPayReason(strPayReason);
              if(prpJrefRecSchema.getCertiType().equals("J")||prpJrefRecSchema.getCertiType().equals("C"))
              {
                DBPrpLcompensate dbPrpLcompensate = new DBPrpLcompensate();
                dbPrpLcompensate.getInfo(dbpool,prpJrefRecSchema.getCertiNo());
                if(dbPrpLcompensate.getSumPrePaid().length()>0&&Double.parseDouble(dbPrpLcompensate.getSumPrePaid())!=0)
                {
                  blSffToAccount.compensateToRawVoucher(dbpool,dbPrpLcompensate);
                }
              }
            }

          }
          this.cancel(dbpool,strReceiptNo[i]);
        }
        save(dbpool);

        dbpool.commitTransaction();
      }
      catch (UserException ue)
      {
        dbpool.rollbackTransaction();
        ue.printStackTrace();
        for(int i=0;i<this.getSize();i++)
        {
          if(strReceiptNo[i] != null && !strReceiptNo[i].equals(""))
          {
            bill.putNo("prpjpayrec", strReceiptNo[i]);
          }
        }
        throw ue;
      }
      catch (Exception e)
      {
        e.printStackTrace();
        dbpool.rollbackTransaction();
        for(int i=0;i<this.getSize();i++)
        {
          if(strReceiptNo[i] != null && !strReceiptNo[i].equals(""))
          {
            bill.putNo("prpjpayrec", strReceiptNo[i]);
          }
        }
        throw e;
      }
      finally {
        dbpool.close();
      }
      return strReceiptNo[0];
    }


    /**
     *����һ��PrpJrefRecSchema��¼
     *@param iPrpJrefRecSchema PrpJrefRecSchema
     *@throws Exception
     */
    public void setArr(PrpJrefRecSchema iPrpJrefRecSchema,int index) throws Exception
    {
       try
       {
         schemas.setElementAt(iPrpJrefRecSchema,index);
       }
       catch(Exception e)
       {
         throw e;
       }
     }

    /**
     *@desc   ע���վ�
     *@Author     : X
     *@param
     *@return
     */
    public void cancelReceipt(String iCancelFlag) throws Exception
    {
      DbPool dbpool = new DbPool();
      DBPrpJrefRec dbPrpJrefRec = new DBPrpJrefRec();
      PrpJrefRecSchema prpJrefRecSchema = new PrpJrefRecSchema();
      BLSffToAccount blSffToAccount = new BLSffToAccount();
      int intLength = 0;

      try {
    	  dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        prpJrefRecSchema = this.getArr(0);
        dbPrpJrefRec.getInfo(dbpool,prpJrefRecSchema.getReceiptNo());
        if(dbPrpJrefRec.getFlag().length()>0&&dbPrpJrefRec.getFlag().equals("1"))
        {
          throw new Exception("�Ѿ�����ע��������������");
        }

        if(dbPrpJrefRec.getTransFlag().equals("1"))
        {
          blSffToAccount.refToRawVoucher(dbpool,prpJrefRecSchema,iCancelFlag);
        }

        if (!dbPrpJrefRec.getRealPayDate().trim().equals("")) { 
          
          dbPrpJrefRec.setRealPayDate("");
          dbPrpJrefRec.setRealPayFee("");
          dbPrpJrefRec.setTransFlag("0");
          dbPrpJrefRec.setTransDate("");
          dbPrpJrefRec.setTransTime("");
        }
        else {          
          
          intLength = dbPrpJrefRec.getFlag().length();
          if (intLength < 2)
            dbPrpJrefRec.setFlag("1");
          else
            dbPrpJrefRec.setFlag("1"+dbPrpJrefRec.getFlag().substring(1, intLength));
        }
        dbPrpJrefRec.update(dbpool);

      }
      catch(Exception e)
      {
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     * ע�����վ�
     * @Author     : X
     * @param iCancelFlag String
     * @throws Exception
     */
    public void cancelReceiptAll(String iCancelFlag) throws Exception
    {
      DbPool dbpool = new DbPool();
      DBPrpJrefRec dbPrpJrefRec = new DBPrpJrefRec();
      PrpJrefRecSchema prpJrefRecSchema = new PrpJrefRecSchema();
      BLSffToAccount blSffToAccount = new BLSffToAccount();
      int intLength = 0;

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        for(int i=0; i<this.getSize(); i++)
        {
          prpJrefRecSchema = this.getArr(i);
          dbPrpJrefRec.getInfo(dbpool, prpJrefRecSchema.getReceiptNo());
          if (dbPrpJrefRec.getFlag().length() > 0 &&
              dbPrpJrefRec.getFlag().equals("1")) {
            throw new Exception("�Ѿ�����ע��������������");
          }

          if (dbPrpJrefRec.getTransFlag().equals("1")) {
            blSffToAccount.refToRawVoucher(dbpool, prpJrefRecSchema, iCancelFlag);
          }

          if (!dbPrpJrefRec.getRealPayDate().trim().equals("")) { 
            
            dbPrpJrefRec.setRealPayDate("");
            dbPrpJrefRec.setRealPayFee("");
            dbPrpJrefRec.setTransFlag("0");
            dbPrpJrefRec.setTransDate("");
            dbPrpJrefRec.setTransTime("");
          }
          else { 
            
            intLength = dbPrpJrefRec.getFlag().length();
            if (intLength < 2)
              dbPrpJrefRec.setFlag("1");
            else
              dbPrpJrefRec.setFlag("1" +
                                   dbPrpJrefRec.getFlag().substring(1, intLength));
          }
          dbPrpJrefRec.update(dbpool);
        }
        dbpool.commitTransaction();
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
     *@desc   ��ȡÿ��XX�������/�˷�
     *@Author     : X
     *@param  iWherePart ��ѯ����
     *@return ��
     */
    public void getSumFeeByPolicyNo(String iWherePart) throws Exception {
      String strSQL = "";
      strSQL = "SELECT SUM(RealPayFee) SumPayFee,PolicyNo FROM PrpJrefRec WHERE "
          + iWherePart + " GROUP BY PolicyNo";
      DbPool dbpool = new DbPool();
      PrpJrefRecSchema prpJrefRecSchema = null;
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        ResultSet resultSet = dbpool.query(strSQL);
        while (resultSet.next()) {
          prpJrefRecSchema = new PrpJrefRecSchema();
          prpJrefRecSchema.setRealPayFee("" + resultSet.getDouble("SumPayFee"));
          prpJrefRecSchema.setPolicyNo(resultSet.getString("PolicyNo"));
          this.setArr(prpJrefRecSchema);
        }
        resultSet.close();
      }
      catch (Exception e) {
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     *@desc   ��ȡÿ��XX�������/�˷�
     *@Author     : X
     *@param  iWherePart ��ѯ����
     *@return ��
     */
    public double getSumPayFeeByCertiNo(DbPool dbpool,String iWherePart) throws Exception
    {
      String strSQL = "";
      strSQL = "SELECT SUM(PayFee) FROM PrpJrefRec WHERE " + iWherePart;
      double dblPayFee = 0.0;

      try {
        ResultSet resultSet = dbpool.query(strSQL);
        if(resultSet.next())
        {
          dblPayFee = resultSet.getDouble(1);
          resultSet.close();
        }
      }
      catch (Exception e) {
        throw e;
      }
      return dblPayFee;
    }

    /**
     *@Author     : X
     *@desc   �޸ĸ����վ�
     *@param  ��
     *@return ��
     */
    public void modifyReceipt() throws Exception
    {
      DbPool dbpool = new DbPool();
      DBPrpJrefRec dbPrpJrefRec = null;
      PrpJrefRecSchema prpJrefRecSchema = null;
      BLSffToAccount blSffToAccount = new BLSffToAccount();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        for (int i = 0; i < this.getSize(); i++)
        {
          prpJrefRecSchema = new PrpJrefRecSchema();
          prpJrefRecSchema = this.getArr(i);
          dbPrpJrefRec = new DBPrpJrefRec();
          dbPrpJrefRec.getInfo(prpJrefRecSchema.getReceiptNo());

          dbPrpJrefRec.setPayWay(prpJrefRecSchema.getPayWay());
          dbPrpJrefRec.setReceiptName(prpJrefRecSchema.getReceiptName());
          dbPrpJrefRec.setCurrency2(prpJrefRecSchema.getCurrency2());
          dbPrpJrefRec.setExchangeRate(prpJrefRecSchema.getExchangeRate());
          dbPrpJrefRec.setInvoiceNo(prpJrefRecSchema.getInvoiceNo());
          dbPrpJrefRec.setRemark(prpJrefRecSchema.getRemark());
          
          if (!prpJrefRecSchema.getRealPayDate().trim().equals(""))
          {
            dbPrpJrefRec.setIdentifyType(prpJrefRecSchema.getIdentifyType());
            dbPrpJrefRec.setIdentifyNumber(prpJrefRecSchema.getIdentifyNumber());
            dbPrpJrefRec.setBankCode(prpJrefRecSchema.getBankCode());
            dbPrpJrefRec.setAccountNo(prpJrefRecSchema.getAccountNo());
            dbPrpJrefRec.setCheckNo(prpJrefRecSchema.getCheckNo());
            dbPrpJrefRec.setRealPayFee(prpJrefRecSchema.getRealPayFee());
          }
          dbPrpJrefRec.update(dbpool);

          
          if (dbPrpJrefRec.getTransFlag().equals("1")) {
            blSffToAccount.modifyRawVoucher(dbpool, prpJrefRecSchema);
          }
        }
        dbpool.commitTransaction();
      }
      catch (Exception e) {
        dbpool.rollbackTransaction();
        throw e;
      }
      finally {
        dbpool.close();
      }
    }

       /* add by liuning begin 20040114 */
     /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void queryRefSum(String iWherePart,String iOtherCondition,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJrefRec dbPrpJrefRec = new DBPrpJrefRec();
      if (iLimitCount > 0 && dbPrpJrefRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJrefRec.queryRefSum");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT RealPayDate,Currency2,OperatorCode,RiskCode ,PayWay,sum(RealPayFee) as RealPayFee FROM PrpJrefRec WHERE " + iWherePart+iOtherCondition;
        schemas = dbPrpJrefRec.findRefSumByConditions(strSqlStatement);
      }
    }
      /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void queryRefSum(DbPool dbpool,String iWherePart,String iOtherCondition,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJrefRec dbPrpJrefRec = new DBPrpJrefRec();
      if (iLimitCount > 0 && dbPrpJrefRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJrefRec.queryRefSum");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT RealPayDate,Currency2,OperatorCode,RiskCode ,PayWay,sum(RealPayFee) as RealPayFee FROM PrpJrefRec WHERE " + iWherePart+iOtherCondition;
        schemas = dbPrpJrefRec.findRefSumByConditions(dbpool,strSqlStatement);
      }
    }
    /* add by liuning end 20040114 */

    /**
     *@desc   ���վݷ�̯Ӧ���վ�
     *@Author     : X
     *@param  iSumPayFee ��XX
     */
    public void premAlloc(double iSumPayFee) throws Exception
    {
      double dblSumPayFee  = 0.0;   
      double dblUnitPayFee = 0.0;   
      double dblMaxPayFee  = 0.0;   
      DecimalFormat decimalFormat = new DecimalFormat("0.00");
      try
      {
        for(int i=0; i<this.getSize(); i++)
        {
          if(!this.getArr(i).getPayFee().equals(""))
            dblSumPayFee += Double.parseDouble(this.getArr(i).getPayFee());
        }
        dblUnitPayFee = iSumPayFee/dblSumPayFee;
        dblSumPayFee = 0.0;
        double dblPayFee = 0.0;
        for(int i=0; i<this.getSize(); i++)
        {
          dblPayFee = 0.0;
          if(!this.getArr(i).getPayFee().equals(""))
            dblPayFee = Double.parseDouble(this.getArr(i).getPayFee())*dblUnitPayFee;
          this.getArr(i).setPayFee(decimalFormat.format(dblPayFee));
          dblSumPayFee += Double.parseDouble(this.getArr(i).getPayFee());
        }
        
        if(Math.abs(iSumPayFee-dblSumPayFee)>0.005)
        {
          dblPayFee = Double.parseDouble(this.getArr(0).getPayFee());
          dblPayFee += iSumPayFee-dblSumPayFee;
          this.getArr(0).setPayFee(decimalFormat.format(dblPayFee));
        }
      }
      catch(Exception e)
      {
        throw e;
      }

    }

    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void queryPrpJpayRec(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      boolean blFlag = false;
      DBPrpJrefRec dbPrpJrefRec = new DBPrpJrefRec();
      PrpJrefRecSchema prpJrefRecSchema = new PrpJrefRecSchema();
      PrpJrefRecSchema prpJrefRecSchemaTmp = new PrpJrefRecSchema();
      Vector schemasTmp = new Vector();
      if (iLimitCount > 0 && dbPrpJrefRec.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJrefRec.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJrefRec WHERE " + iWherePart;
        schemasTmp = dbPrpJrefRec.findByConditions(strSqlStatement);
      }
      for(int i=0;i<schemasTmp.size();i++)
      {
        prpJrefRecSchema = new PrpJrefRecSchema();
        prpJrefRecSchema = (PrpJrefRecSchema)schemasTmp.get(i);
        if(this.getSize()>0)
        {
          blFlag = false;
          for (int j = 0; j < this.getSize(); j++) {
            prpJrefRecSchemaTmp = new PrpJrefRecSchema();
            prpJrefRecSchemaTmp = this.getArr(j);
           if(prpJrefRecSchema.getContractNo().trim().equals(prpJrefRecSchemaTmp.getContractNo().trim()))
            {
               prpJrefRecSchemaTmp.setPayFee("" +
                  (Double.parseDouble(prpJrefRecSchemaTmp.getPayFee())+
                  Double.parseDouble(prpJrefRecSchema.getPayFee())));
              prpJrefRecSchemaTmp.setRealPayFee("" +
                  (Double.parseDouble(prpJrefRecSchemaTmp.getRealPayFee())+
                  Double.parseDouble(prpJrefRecSchema.getRealPayFee())));
              schemas.setElementAt(prpJrefRecSchemaTmp,j);
              blFlag = true;
              break;
            }
          }
          if(!blFlag)
          {
            schemas.add(prpJrefRecSchema);
          }
        }
        else
        {
          schemas.add(prpJrefRecSchema);
        }
      }
    }


    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
