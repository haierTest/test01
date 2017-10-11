package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefDetail;
import com.sp.prpall.dbsvr.jf.DBPrpJpayRefMain;
import com.sp.prpall.dbsvr.jf.DBPrpJplanFee;
import com.sp.prpall.schema.PrpJpayRefDetailSchema;
import com.sp.prpall.schema.PrpJpayRefMainSchema;
import com.sp.prpall.schema.PrpJplanFeeSchema;

/**
 * ����PrpJpayRefDetail��BL��
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>@createdate 2005-05-27</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJpayRefDetail{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */
    public BLPrpJpayRefDetail(){
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
     *����һ��PrpJpayRefDetailSchema��¼
     *@param iPrpJpayRefDetailSchema PrpJpayRefDetailSchema
     *@throws Exception
     */
    public void setArr(PrpJpayRefDetailSchema iPrpJpayRefDetailSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpJpayRefDetailSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpJpayRefDetailSchema��¼
     *@param index �±�
     *@return һ��PrpJpayRefDetailSchema����
     *@throws Exception
     */
    public PrpJpayRefDetailSchema getArr(int index) throws Exception
    {
     PrpJpayRefDetailSchema prpJpayRefDetailSchema = null;
       try
       {
        prpJpayRefDetailSchema = (PrpJpayRefDetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpJpayRefDetailSchema;
     }
    /**
     *ɾ��һ��PrpJpayRefDetailSchema��¼
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
      DBPrpJpayRefDetail dbPrpJpayRefDetail = new DBPrpJpayRefDetail();
      if (iLimitCount > 0 && dbPrpJpayRefDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRefDetail WHERE " + iWherePart;
        schemas = dbPrpJpayRefDetail.findByConditions(strSqlStatement);
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
      DBPrpJpayRefDetail dbPrpJpayRefDetail = new DBPrpJpayRefDetail();
      if (iLimitCount > 0 && dbPrpJpayRefDetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJpayRefDetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpJpayRefDetail WHERE " + iWherePart;
        schemas = dbPrpJpayRefDetail.findByConditions(dbpool,strSqlStatement);
      }
    }

    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpJpayRefDetail dbPrpJpayRefDetail = new DBPrpJpayRefDetail();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpJpayRefDetail.setSchema((PrpJpayRefDetailSchema)schemas.get(i));
      dbPrpJpayRefDetail.insert(dbpool);
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
     *�ո���ȷ�� SangMingqian 20050615(����ƾ֤��Ϣ�д�����)
     *@param strCondition ɸѡ����,index ѡ�м�¼,
             strPayRefDate �ո�ȷ������,strPayRefUnit �ո�ȷ�ϵ�λ,strPayRefCode �ո�ȷ����
     *@throws Exception
    */
    public void payRefVerity(String strCondition,String[] index,String strPayRefDate,String strPayRefUnit,String strPayRefCode) throws UserException,SQLException,Exception
    {
      BLPrpJpayRefDetail blPrpJpayRefDetail = new  BLPrpJpayRefDetail();
      BLPrpJpayRefMain blPrpJpayRefMain = new BLPrpJpayRefMain();
      BLPrpJpayRefRec blPrpJpayRefRec = new BLPrpJpayRefRec();
      BLPrpJplanFee blPrpJplanfee = new BLPrpJplanFee();

      PrpJpayRefMainSchema prpJpayRefMainSchema = null;
      PrpJplanFeeSchema prpJplanFeeSchema = null;

      DBPrpJpayRefDetail dbPrpJpayRefDetail = new DBPrpJpayRefDetail();
      DBPrpJpayRefMain dbPrpJpayRefMain = new DBPrpJpayRefMain();
      DBPrpJplanFee dbPrpJplanFee = new DBPrpJplanFee();

      blPrpJpayRefMain.queryWithRec(strCondition,0);
      
      String strConditionRec = "1=1";
      strConditionRec = strConditionRec + " AND ( 1=0 ";
      
      String strConditionFee = "1=1";
      strConditionFee = strConditionFee + " AND ( 1=0 ";

      DbPool dbpool = new DbPool();
      
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();

        
        this.save(dbpool);

        
        for(int i = 0;i<index.length;i++){
          prpJpayRefMainSchema = blPrpJpayRefMain.getArr(Integer.parseInt(index[i]));

          dbPrpJpayRefMain = new DBPrpJpayRefMain();
          dbPrpJpayRefMain.setSchema(prpJpayRefMainSchema);
          dbPrpJpayRefMain.setPayRefCode(strPayRefCode);
          dbPrpJpayRefMain.setPayRefUnit(strPayRefUnit);
          dbPrpJpayRefMain.setPayRefDate(strPayRefDate);
          dbPrpJpayRefMain.update(dbpool);

          strConditionRec += " OR PayRefNo='"+ blPrpJpayRefMain.getArr(Integer.parseInt(index[i])).getPayRefNo() +"' ";
        }
        strConditionRec += " ) ";

        
        blPrpJpayRefRec.query(strConditionRec,0);
        for (int j=0;j<blPrpJpayRefRec.getSize();j++){
          strConditionFee += " OR (CertiType='" + blPrpJpayRefRec.getArr(j).getCertiType() + "' AND CertiNo='"
                              + blPrpJpayRefRec.getArr(j).getCertiNo() + "' AND SerialNo='"
                              + blPrpJpayRefRec.getArr(j).getSerialNo() + "' AND PayRefReason='"
                              + blPrpJpayRefRec.getArr(j).getPayRefReason() + "')";
        }
        strConditionFee += " ) ";
        blPrpJplanfee.query(strConditionFee,0);
        for(int k=0;k<blPrpJplanfee.getSize();k++){
          prpJplanFeeSchema=blPrpJplanfee.getArr(k);
          dbPrpJplanFee = new DBPrpJplanFee();
          dbPrpJplanFee.setSchema(prpJplanFeeSchema);
          dbPrpJplanFee.setRealPayRefFee(prpJplanFeeSchema.getPayRefFee());
          dbPrpJplanFee.update(dbpool);
        }

        dbpool.commitTransaction();
        dbpool.close();
      }
      catch(UserException userexception){
        dbpool.rollbackTransaction();
        throw userexception;
      }
      catch(SQLException sqlexception){
        dbpool.rollbackTransaction();
        throw sqlexception;
      }
      catch(Exception exception){
        dbpool.rollbackTransaction();
        throw exception;
      }
      finally {
        dbpool.close();
      }
    }

    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
