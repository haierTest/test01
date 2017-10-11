package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.indiv.ci.dbsvr.DBPrpitemkinddetail;
import com.sp.indiv.ci.schema.PrpitemkinddetailSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����prpitemkinddetail��BL��
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-03-26</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpitemkinddetail{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpitemkinddetail(){
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
     *����һ��PrpitemkinddetailSchema��¼
     *@param iPrpitemkinddetailSchema PrpitemkinddetailSchema
     *@throws Exception
     */
    public void setArr(PrpitemkinddetailSchema iPrpitemkinddetailSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpitemkinddetailSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpitemkinddetailSchema��¼
     *@param index �±�
     *@return һ��PrpitemkinddetailSchema����
     *@throws Exception
     */
    public PrpitemkinddetailSchema getArr(int index) throws Exception
    {
     PrpitemkinddetailSchema prpitemkinddetailSchema = null;
       try
       {
        prpitemkinddetailSchema = (PrpitemkinddetailSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpitemkinddetailSchema;
     }
    /**
     *ɾ��һ��PrpitemkinddetailSchema��¼
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
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()), bindValues);
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpitemkinddetail dbPrpitemkinddetail = new DBPrpitemkinddetail();
      if (iLimitCount > 0 && dbPrpitemkinddetail.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpitemkinddetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Prpitemkinddetail WHERE " + iWherePart; 
        schemas = dbPrpitemkinddetail.findByConditions(strSqlStatement, bindValues);
      }
    }
    /**
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, ArrayList bindValues) throws UserException,Exception
    {
       this.query(dbpool,iWherePart,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()),bindValues);
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@param bindValues �󶨲���
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount, ArrayList bindValues) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpitemkinddetail dbPrpitemkinddetail = new DBPrpitemkinddetail();
      if (iLimitCount > 0 && dbPrpitemkinddetail.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpitemkinddetail.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM Prpitemkinddetail WHERE " + iWherePart; 
        schemas = dbPrpitemkinddetail.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpitemkinddetail dbPrpitemkinddetail = new DBPrpitemkinddetail();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpitemkinddetail.setSchema((PrpitemkinddetailSchema)schemas.get(i));
      dbPrpitemkinddetail.insert(dbpool);
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
      
      try
      {
    	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
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
     * ��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param businessno businessno
     *@param businesstype businesstype
     *@param itemkindno itemkindno
     *@return ��
     */
    public void cancel(DbPool dbpool, String businessno, String businesstype) throws Exception
    {
    	String strSQL = " Delete From Prpitemkinddetail Where businessno = ?  And businesstype = ? ";
		dbpool.prepareInnerStatement(strSQL);
		dbpool.setString(1,businessno);
		dbpool.setString(2,businesstype);
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
    }
      
    /**
     * ����dbpool��ɾ������
     *@param businessno businessno
     *@param businesstype businesstype
     *@param itemkindno itemkindno
     *@return ��
     */
    public void cancel(String businessno, String businesstype) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
    	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool, businessno,businesstype);
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
     * ��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param businessno businessno
     *@param businesstype businesstype
     *@param itemkindno itemkindno
     *@return ��
     */
    public void cancel(DbPool dbpool, String businessno, String businesstype, String itemkindno) throws Exception
    {
        String strSQL = " Delete From Prpitemkinddetail Where businessno = ?" +
				        " And businesstype = ?" +
				        " And itemkindno = ?";
		dbpool.prepareInnerStatement(strSQL);
		int index = 1;
		dbpool.setString(index++,businessno);
		dbpool.setString(index++,businesstype);
		dbpool.setString(index++,itemkindno);
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
    }
      
    /**
     * ����dbpool��ɾ������
     *@param businessno businessno
     *@param businesstype businesstype
     *@param itemkindno itemkindno
     *@return ��
     */
    public void cancel(String businessno, String businesstype, String itemkindno) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
    	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool, businessno, businesstype, itemkindno);
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
     * ����dbpool����businessno��ȡ����
     *@param businessno
     *@return ��
     */
    public void getData(String businessno) throws Exception
    {
      DbPool dbpool = new DbPool();
      try {
    	  dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
    	  getData(dbpool, businessno);
	  } catch (Exception e) {
	  } finally {
			dbpool.close();
	  }
    }
    
    /**
     * ��dbpool����businessno��ȡ����
     *@param dbpool ���ӳ�
     *@param businessno businessno
     *@return ��
     */
    public void getData(DbPool dbpool, String businessno) throws Exception
    {
       String strWherePart = "";
       strWherePart = "businessno=?";
	   ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(businessno);
       query(dbpool, strWherePart, 0, arrWhereValue);
    }
    
    /**
     * ����dbpool����������ȡ����
     *@param businessno businessno
     *@param businesstype businesstype
     *@return ��
     */
    public void getData(String businessno, String businesstype) throws Exception
    {
      DbPool dbpool = new DbPool();
      try {
    	  dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
    	  getData(dbpool, businessno, businesstype);
	  } catch (Exception e) {
	  } finally {
			dbpool.close();
	  }
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param businessno businessno
     *@param businesstype businesstype
     *@return ��
     */
    public void getData(DbPool dbpool, String businessno, String businesstype) throws Exception
    {
       String strWherePart = "";
       strWherePart = "businessno=? AND businesstype=?";
	   ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(businessno);
       arrWhereValue.add(businesstype);
       query(dbpool, strWherePart, 0, arrWhereValue);
    }
    
    /**
     * ����dbpool����������ȡ����
     *@param businessno businessno
     *@param businesstype businesstype
     *@param itemkindno itemkindno
     *@return ��
     */
    public void getData(String businessno, String businesstype, String itemkindno) throws Exception
    {
      DbPool dbpool = new DbPool();
      try {
    	  dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
    	  getData(dbpool, businessno, businesstype, itemkindno);
	  } catch (Exception e) {
	  } finally {
		  dbpool.close();
	  }
    }
      
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param businessno businessno
     *@param businesstype businesstype
     *@param itemkindno itemkindno
     *@return ��
     */
    public void getData(DbPool dbpool, String businessno, String businesstype, String itemkindno) throws Exception
    {
       String strWherePart = "";
       strWherePart = "businessno=? AND businesstype=? AND itemkindno=?";
	   ArrayList arrWhereValue = new ArrayList();
       arrWhereValue.add(businessno);
       arrWhereValue.add(businesstype);
       arrWhereValue.add(itemkindno);
       query(dbpool, strWherePart, 0, arrWhereValue);
    }
    
    /**
	  * t��¼ת��Ϊc��¼
	  *@param
	  *@param iPolicyNo XX��
	  *@return ��
	  */
    public PrpitemkinddetailSchema Evaluate(PrpitemkinddetailSchema tPrpitemkinddetailSchema,String strPolicyNo)throws Exception{
    	PrpitemkinddetailSchema cPrpitemkinddetailSchema = new PrpitemkinddetailSchema();
    	
    	cPrpitemkinddetailSchema.setBusinessno(strPolicyNo);
    	cPrpitemkinddetailSchema.setBusinesstype("C");
    	cPrpitemkinddetailSchema.setItemkindno(tPrpitemkinddetailSchema.getItemkindno());
    	cPrpitemkinddetailSchema.setKindcode(tPrpitemkinddetailSchema.getKindcode());
    	cPrpitemkinddetailSchema.setKindcodedetail(tPrpitemkinddetailSchema.getKindcodedetail());
    	cPrpitemkinddetailSchema.setStartDate(tPrpitemkinddetailSchema.getStartDate());
    	cPrpitemkinddetailSchema.setStartHour(tPrpitemkinddetailSchema.getStartHour());
    	cPrpitemkinddetailSchema.setEndDate(tPrpitemkinddetailSchema.getEndDate());
    	cPrpitemkinddetailSchema.setEndHour(tPrpitemkinddetailSchema.getEndHour());
    	cPrpitemkinddetailSchema.setAmount(tPrpitemkinddetailSchema.getAmount());
    	cPrpitemkinddetailSchema.setBenchMarkPremium(tPrpitemkinddetailSchema.getBenchMarkPremium());
    	cPrpitemkinddetailSchema.setPremiun(tPrpitemkinddetailSchema.getPremiun());
    	
    	return cPrpitemkinddetailSchema;
    }
    
    /**
     * ����KindcodedetailѰ���±�
     *
     * @param iKindCode
     * @return �±�
     */
    public int search(String iKindCode) throws Exception {
        int icurr = 0;
        int iFindFlag = 0;
        for (int i = 0; i < schemas.size(); i++) {
            if (this.getArr(i).getKindcodedetail().trim().equals(iKindCode)) {
                icurr = i;
                iFindFlag = 1;
            }
        }
        if (iFindFlag == 0) {
            icurr = -1;
        }
        return icurr;

    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
