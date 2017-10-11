package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBTBCoins;
import com.sp.prpall.schema.TBCoinsSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

public class BLTBCoins {
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */
    public BLTBCoins(){
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
     *����һ��PrpTcoinsSchema��¼
     *@param iPrpTcoinsSchema PrpTcoinsSchema
     *@throws Exception
     */
    public void setArr(TBCoinsSchema iPrpTcoinsSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpTcoinsSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpTcoinsSchema��¼
     *@param index �±�
     *@return һ��PrpTcoinsSchema����
     *@throws Exception
     */
    public TBCoinsSchema getArr(int index) throws Exception
    {
    	TBCoinsSchema prpTcoinsSchema = null;
       try
       {
        prpTcoinsSchema = (TBCoinsSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpTcoinsSchema;
     }
    /**
     *ɾ��һ��PrpTcoinsSchema��¼
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
      DBTBCoins dbPrpTcoins = new DBTBCoins();
      if (iLimitCount > 0 && dbPrpTcoins.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpTcoins.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM tb_coins WHERE " + iWherePart;
        schemas = dbPrpTcoins.findByConditions(strSqlStatement);
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
      DBTBCoins dbPrpTcoins = new DBTBCoins();
      if (iLimitCount > 0 && dbPrpTcoins.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpTcoins.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM tb_coins WHERE " + iWherePart;
        schemas = dbPrpTcoins.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
    	DBTBCoins dbPrpTcoins = new DBTBCoins();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpTcoins.setSchema((TBCoinsSchema)schemas.get(i));
      dbPrpTcoins.insert(dbpool);
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
     *@param iProposalNo XX����
     *@return ��
     */
    public void cancel(DbPool dbpool,String iProposalNo) throws Exception
    {

    	String strSqlStatement = " DELETE FROM tb_coins WHERE PolicyNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iProposalNo);
	    dbpool.executePreparedUpdate();
	    dbpool.closePreparedStatement();

    }

    /**
     * ����dbpool��ɾ������
     *@param iProposalNo XX����
     *@return ��
     */
    public void cancel(String iProposalNo ) throws Exception
    {
      DbPool dbpool = new DbPool();

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        cancel(dbpool,iProposalNo);
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
     * ��dbpool����XX���Ż�ȡ����
     *@param iProposalNo XX����
     *@return ��
     */
    public void getData(String iProposalNo) throws Exception
    {
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
            getData(dbpool, iProposalNo);
        } catch (Exception e) {
        } finally {
            dbpool.close();
        }
    }

    /**
     * ����dbpool����XX���Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iProposalNo XX����
     *@return ��
     */
    public void getData(DbPool dbpool,String iProposalNo) throws Exception
    {
        String strWherePart = " PolicyNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iProposalNo);
        query(dbpool, strWherePart, arrWhereValue, 0);
    }

    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
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
        DBTBCoins dbPrpTcoins = new DBTBCoins();
        if (iLimitCount > 0 && dbPrpTcoins.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLTBCoins.java");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM tb_coins WHERE " + iWherePart;
            schemas = dbPrpTcoins.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
  
    /**
     *@desc ��ʾ������/�������ӷ��ҷ��е�����
     *@param iCoinsFlag ����XXXXX���ͣ�0/1/2/3/4 ��/����/�ӹ�/����/����
     *@param iFlag ��־��1/2 ������/�������ӷ�
     *@throws UserException
     *@throws Exception
     */
    public String showSelfRate(String iCoinsFlag, String iFlag)
    throws UserException,Exception {
        String strProportionFlag = "";
        String strSelfRate = "100";

        int i = 0;

        if(iCoinsFlag.equals("1")||iCoinsFlag.equals("3")) {
            if (this.getSize() > 0 && iFlag.equals("1") && this.getArr(0).getProportionFlag().length() > 0) {
                strProportionFlag = this.getArr(0).getProportionFlag().substring(0, 1);
            } else if (this.getSize() > 0 && iFlag.equals("2") && this.getArr(0).getProportionFlag().length() > 1) {
                strProportionFlag = this.getArr(0).getProportionFlag().substring(1, 2);
            }
            for (i = 0; i < this.getSize(); i++) {
                if(this.getArr(i).getCoinsType().equals("1")) {
                    strSelfRate = this.getArr(i).getCoinsRate();
                    break;
                }
            }
            
            if (strProportionFlag.equals("1") || strProportionFlag.equals("2")) {
                strSelfRate = "100";
            }
        } else if(iCoinsFlag.equals("0")) {
            strSelfRate = "";
        }
        return strSelfRate;
    }

    /**
     *@desc ��ʾ�����ѡ��������ӷѵļ��뷽ʽ
     *@param iCoinsFlag ����XXXXX���ͣ�0/1/2/3/4 ��/����/�ӹ�/����/����
     *@param iFlag ��־��1/2 ������/�������ӷ�
     *@throws UserException
     *@throws Exception
     */
    public String showProportionFlag(String iCoinsFlag, String iFlag)
    throws UserException, Exception {
        String strProportionFlag = "";

        if (this.getSize() > 0 && iFlag.equals("1") && this.getArr(0).getProportionFlag().length() > 0) {
            strProportionFlag = this.getArr(0).getProportionFlag().substring(0, 1);
        } else if (this.getSize() > 0 && iFlag.equals("2") && this.getArr(0).getProportionFlag().length() > 1) {
            strProportionFlag = this.getArr(0).getProportionFlag().substring(1, 2);
        }
        if (iCoinsFlag.equals("1") || iCoinsFlag.equals("3")) {
            if (strProportionFlag.equals("0")) {
                strProportionFlag = "�ݶ����";
            } else if (strProportionFlag.equals("1")) {
                strProportionFlag = "ȫ�����";
            } else if (strProportionFlag.equals("2")) {
                strProportionFlag = "ȫ��е�";
            }
        } else if(!iCoinsFlag.equals("0")) {
            strProportionFlag = "�ݶ����";
        }
        return strProportionFlag;
    }

    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
