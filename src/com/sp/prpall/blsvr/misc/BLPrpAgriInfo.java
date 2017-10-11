package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.Vector;
import java.util.ArrayList;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.misc.DBPrpAgriInfo;
import com.sp.prpall.schema.PrpAgriInfoSchema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * ����PrpAgriInfo��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-05-30</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLPrpAgriInfo{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpAgriInfo(){
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
     *����һ��PrpAgriInfoSchema��¼
     *@param iPrpAgriInfoSchema PrpAgriInfoSchema
     *@throws Exception
     */
    public void setArr(PrpAgriInfoSchema iPrpAgriInfoSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpAgriInfoSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpAgriInfoSchema��¼
     *@param index �±�
     *@return һ��PrpAgriInfoSchema����
     *@throws Exception
     */
    public PrpAgriInfoSchema getArr(int index) throws Exception
    {
      PrpAgriInfoSchema prpAgriInfoSchema = null;
       try
       {
         prpAgriInfoSchema = (PrpAgriInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpAgriInfoSchema;
     }
    /**
     *ɾ��һ��PrpAgriInfoSchema��¼
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
     *���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart,ArrayList iWhereValue,int intPageNum,int intLineNumPage) throws UserException,Exception
    {
    	DbPool dbpool = new DbPool();
        try {
          dbpool.open(SysConfig.CONST_SUNQUERYDATASOURCE);
          this.query(dbpool, iWherePart, iWhereValue,intPageNum,intLineNumPage);      
        }
        catch (Exception e)
        {
          throw e;
        }
        finally {
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
    public void query(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpAgriInfo dbPrpAgriInfo = new DBPrpAgriInfo();
      if (iLimitCount > 0 && dbPrpAgriInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpAgriInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpAgriInfo WHERE " + iWherePart; 
        schemas = dbPrpAgriInfo.findByConditions(strSqlStatement);
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
      DBPrpAgriInfo dbPrpAgriInfo = new DBPrpAgriInfo();
      if (iLimitCount > 0 && dbPrpAgriInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpAgriInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpAgriInfo WHERE " + iWherePart; 
        schemas = dbPrpAgriInfo.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpAgriInfo dbPrpAgriInfo = new DBPrpAgriInfo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpAgriInfo.setSchema((PrpAgriInfoSchema)schemas.get(i));
        dbPrpAgriInfo.insert(dbpool);
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
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param iPolicyNO PolicyNO
     *@return ��
     */
    public void cancel(DbPool dbpool,String iPolicyNO) throws Exception
    {
      String strSqlStatement = " DELETE FROM PrpAgriInfo WHERE PolicyNO= ?";
      dbpool.prepareInnerStatement(strSqlStatement);
      dbpool.setString(1, iPolicyNO);
	  dbpool.executePreparedUpdate();
	  dbpool.closePreparedStatement();
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iPolicyNO PolicyNO
     *@return ��
     */
    public void cancel(String iPolicyNO ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iPolicyNO);
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
     * ��dbpool����PolicyNO��ȡ����
     *@param iPolicyNO PolicyNO
     *@return ��
     */
    public void getData(String iPolicyNO) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        getData(dbpool,iPolicyNO);
      }
      catch (Exception e)
      {
        
      }
      finally
      {
        dbpool.close();
      }
    }
      
    /**
     * ����dbpool����PolicyNO��ȡ����
     *@param dbpool ���ӳ�
     *@param iPolicyNO PolicyNO
     *@return ��
     */
    public void getData(DbPool dbpool,String iPolicyNO) throws Exception
    {
	    String strWherePart = " PolicyNO=? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iPolicyNO);
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
        DBPrpAgriInfo dbPrpAgriInfo = new DBPrpAgriInfo();
        if (iLimitCount > 0 && dbPrpAgriInfo.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
        	 
            throw new UserException(-98,-1003,"BLPrpAgriInfo.query");
        }else
        {       	 
            initArr();
            strSqlStatement = " SELECT * FROM PrpAgriInfo WHERE " + iWherePart;
            schemas = dbPrpAgriInfo.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@author liying 20110328
     *@param dbpool      ȫ�ֳ�
     *@param iWherePart  ��ѯ����,�����������Ѱ󶨱�����ʽ���ʺŸ���������ֵ����һ��
     *@param iWhereValue ��ѯ�������ֶ�ֵ
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool,String iWherePart,ArrayList iWhereValue, int intPageNum,int intLineNumPage) throws UserException,Exception
    {   	 
    	int intStartNum = 0;
        int intEndNum = 0;

        intStartNum = (intPageNum - 1) * intLineNumPage;
        intEndNum = intPageNum * intLineNumPage;
        
        StringBuffer strSqlStatement = new StringBuffer();
        DBPrpAgriInfo dbPrpAgriInfo = new DBPrpAgriInfo();
        strSqlStatement.append(" SELECT * FROM ( ");
        strSqlStatement.append("Select RowNum As LineNum, T.*From (  SELECT * FROM PrpAgriInfo WHERE ");
        strSqlStatement.append(iWherePart);
        strSqlStatement.append(") T Where RowNum<=");
        strSqlStatement.append(intEndNum);
        strSqlStatement.append(") Where LineNum>");
        strSqlStatement.append(intStartNum);
        schemas = dbPrpAgriInfo.findByConditions(dbpool,strSqlStatement.toString(),iWhereValue);
    }
    /**
     * ���ݲ�ѯ��������ѯSysConfig.CONST_SUNQUERYDATASOURCE����Դ��Ӧ������.���������¼����schemas���� ����dbpool
     * @param iWherePart ��ѯ����
     * @param intPageNum ҳ��
     * @param intLineNumPage ÿҳ����
     * @return ��
     * @throws Exception
     */
    public void queryInsureAgri(String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConfig.CONST_SUNQUERYDATASOURCE);
        this.queryInsureAgriOrder(dbpool,iWherePart,intPageNum,intLineNumPage);
      }
      catch (Exception e)
      {
        throw e;
      }
      finally {
        dbpool.close();
      }
    }
    /**
     * ���ݲ�ѯ��������ѯSysConfig.CONST_SUNQUERYDATASOURCE����Դ��Ӧ������.���������¼����schemas���� ����dbpool
     * @param iWherePart ��ѯ����
     * @param intPageNum ҳ��
     * @param intLineNumPage ÿҳ����
     * @return ��
     * @throws Exception
     */
    public void queryInsureAgri(String iWherePart) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConfig.CONST_SUNQUERYDATASOURCE);
        this.queryInsureAgriOrder(dbpool,iWherePart);
      }
      catch (Exception e)
      {
        throw e;
      }
      finally {
        dbpool.close();
      }
    }
    /**
     * ���ݲ�ѯ��������ѯSysConfig.CONST_SUNQUERYDATASOURCE����Դ��Ӧ������.���������¼����schemas���� ��dbpool
     * @param iWherePart ��ѯ����
     * @param intPageNum ҳ��
     * @param intLineNumPage ÿҳ����
     * @return ��
     * @throws Exception
     */
     public void queryInsureAgriOrder(DbPool dbpool,String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
     {
      
       StringBuffer strSqlStatement = new StringBuffer();
      
       int intStartNum = 0;
       int intEndNum = 0;

       intStartNum = (intPageNum - 1) * intLineNumPage;
       intEndNum = intPageNum * intLineNumPage;

       DBPrpCmain dbPrpCmain = new DBPrpCmain();
       DBPrpAgriInfo dbPrpAgriInfo = new DBPrpAgriInfo();
       initArr();
       
  
       strSqlStatement.append(" SELECT * FROM ( ");
       strSqlStatement.append("Select RowNum As LineNum, T.*From ( ");
       
       strSqlStatement.append("Select distinct prpAgriInfo.flag,prpAgriInfo.Policyno,prpAgriInfo.InsuredCode,prpAgriInfo.Insuredname," +
       
       
       "prpAgriInfo.AddressNo,prpAgriInfo.BankCode,prpAgriInfo.RiskName," +
       "prpAgriInfo.unitAmout,prpAgriInfo.proposalNo,prpAgriInfo.Rate, "+
       
       		"prpAgriInfo. LicenseNo,prpAgriInfo. Premium From" +
       		" prpAgriInfo, PrpCmain Where ");
       strSqlStatement.append(iWherePart);
       strSqlStatement.append(") T Where RowNum<=");
       strSqlStatement.append(intEndNum);
       strSqlStatement.append(") Where LineNum>");
       strSqlStatement.append(intStartNum);
       schemas = dbPrpAgriInfo.findByConditions(dbpool,strSqlStatement.toString(),"");
       
     }
     /**
      * ���ݲ�ѯ��������ѯSysConfig.CONST_SUNQUERYDATASOURCE����Դ��Ӧ������.���������¼����schemas���� ��dbpool
      * @param iWherePart ��ѯ����
      * @return ��
      * @throws Exception
      */
      public void queryInsureAgriOrder(DbPool dbpool,String iWherePart ) throws UserException,Exception
      {
        StringBuffer strSqlStatement = new StringBuffer();
        DBPrpAgriInfo dbPrpAgriInfo = new DBPrpAgriInfo();
        initArr();
        strSqlStatement.append("Select * From prpAgriInfo Where ");
        strSqlStatement.append(iWherePart);
        schemas = dbPrpAgriInfo.findByConditions(dbpool,strSqlStatement.toString());
      }
     
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
