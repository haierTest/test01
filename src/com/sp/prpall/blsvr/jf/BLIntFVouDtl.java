package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.sp.prpall.dbsvr.jf.DBIntFVouDtl;
import com.sp.prpall.schema.IntFVouDtlSchema;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utiall.blsvr.BLPrpDrisk;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����BLIntFVouDtl��BL��
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>@createdate 2007-12-27</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLIntFVouDtl {
	private Vector schemas = new Vector();
    /**
     * ���캯��
     */
    public BLIntFVouDtl(){
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
     *����һ��IntFVouDtlSchema��¼
     *@param iIntFVouDtlSchema IntFVouDtlSchema
     *@throws Exception
     */
    public void setArr(IntFVouDtlSchema iIntFVouDtlSchema) throws Exception
    {
      try
      {
        schemas.add(iIntFVouDtlSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }

    /**
     *�õ�һ��IntFVouDtlSchema��¼
     *@param index �±�
     *@return һ��IntFVouDtlSchema����
     *@throws Exception
     */
    public IntFVouDtlSchema getArr(int index) throws Exception
    {
    	IntFVouDtlSchema intFVouDtlSchema = null;
      try
      {
    	  intFVouDtlSchema = (IntFVouDtlSchema)this.schemas.get(index);
      }
      catch(Exception e)
      {
        throw e;
      }
      return intFVouDtlSchema;
    }

    /**
     *ɾ��һ��IntFVouDtlSchema��¼
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
      DBIntFVouDtl dbIntFVouDtl = new DBIntFVouDtl();
      if (iLimitCount > 0 && dbIntFVouDtl.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntFVouDtl.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntFVouDtl WHERE " + iWherePart;
        schemas = dbIntFVouDtl.findByConditions(strSqlStatement);
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
      DBIntFVouDtl dbIntFVouDtl = new DBIntFVouDtl();
      if (iLimitCount > 0 && dbIntFVouDtl.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLIntFVouDtl.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM IntFVouDtl WHERE " + iWherePart;
        schemas = dbIntFVouDtl.findByConditions(dbpool,strSqlStatement);
      }
    }
    
    /**
     *��Ŀ���뷭�� 
     *@param index ����
     *@param isChinese �Ƿ���ʾ����
     *@return String �������ַ���
     *@throws UserException
     *@throws Exception
     */
   public String translateCode(int index,boolean isChinese)throws UserException,Exception{
	   StringBuffer buffer = new StringBuffer(200);
	   BLPrpDcode blPrpDcode = new BLPrpDcode();
	   BLPrpDrisk blPrpDrisk= new BLPrpDrisk();
	   String segment3 = this.getArr(index).getSegment3();
	   String segment2 = this.getArr(index).getSegment2();
	   String segment4 = this.getArr(index).getSegment4();
	   String segment5 = this.getArr(index).getSegment5();
	   String segment6 = this.getArr(index).getSegment6();
	   String segment7 = this.getArr(index).getSegment7();
	   String attribute10 = this.getArr(index).getAttribute10();
	   String attribute1 = this.getArr(index).getAttribute1();
	   buffer.append((segment3.equals("0")||segment3 == null?segment3:blPrpDcode.translateCode("SI_P_COA_ACC",segment3,isChinese)) + ".");
	   buffer.append((segment2.equals("0")||segment2 == null?segment2:blPrpDcode.translateCode("SI_P_COA_CC",segment2,isChinese)) + ".");
	   buffer.append((segment4.equals("0")||segment4 == null?segment4:blPrpDcode.translateCode("SI_P_COA_BACC",segment4,isChinese)) + ".");
	   buffer.append((segment5.equals("0")||segment5 == null?segment5:blPrpDcode.translateCode("SI_P_COA_SUBACC",segment5,isChinese)) + ".");
	   buffer.append((segment6.equals("0")||segment6 == null?segment6:blPrpDrisk.translateCode(segment6,isChinese)) + ".");
	   buffer.append((segment7.equals("0")||segment7 == null?segment7:blPrpDcode.translateCode("SI_P_COA_CHAN",segment7,isChinese)) + ".");
	   buffer.append((attribute10.equals("0")||attribute10 == null?attribute10:blPrpDcode.translateCode("SI_P_CFS_ITEM",attribute10,isChinese)) + "."); 
	   buffer.append(attribute1);
	   return buffer.toString();
   }
}
