package com.sp.indiv.ci.blsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;

import com.sp.prpall.dbsvr.tb.DBPrpTmain;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.indiv.ci.dbsvr.DBPrpNewCarRecord;
import com.sp.indiv.ci.schema.PrpNewCarRecordSchema;

/**
 * ����prpNewCarRecord��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-12-22</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLPrpNewCarRecord{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpNewCarRecord(){
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
     *����һ��PrpNewCarRecordSchema��¼
     *@param iPrpNewCarRecordSchema PrpNewCarRecordSchema
     *@throws Exception
     */
    public void setArr(PrpNewCarRecordSchema iPrpNewCarRecordSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpNewCarRecordSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpNewCarRecordSchema��¼
     *@param index �±�
     *@return һ��PrpNewCarRecordSchema����
     *@throws Exception
     */
    public PrpNewCarRecordSchema getArr(int index) throws Exception
    {
      PrpNewCarRecordSchema prpNewCarRecordSchema = null;
       try
       {
         prpNewCarRecordSchema = (PrpNewCarRecordSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpNewCarRecordSchema;
     }
    /**
     *ɾ��һ��PrpNewCarRecordSchema��¼
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
      DBPrpNewCarRecord dbPrpNewCarRecord = new DBPrpNewCarRecord();
      if (iLimitCount > 0 && dbPrpNewCarRecord.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpNewCarRecord.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpNewCarRecord WHERE " + iWherePart; 
        schemas = dbPrpNewCarRecord.findByConditions(strSqlStatement);
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
      DBPrpNewCarRecord dbPrpNewCarRecord = new DBPrpNewCarRecord();
      if (iLimitCount > 0 && dbPrpNewCarRecord.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpNewCarRecord.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpNewCarRecord WHERE " + iWherePart; 
        schemas = dbPrpNewCarRecord.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpNewCarRecord dbPrpNewCarRecord = new DBPrpNewCarRecord();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpNewCarRecord.setSchema((PrpNewCarRecordSchema)schemas.get(i));
        dbPrpNewCarRecord.insert(dbpool);
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
     *@param iEngineNo EngineNo
     *@return ��
     */
    public void cancel(DbPool dbpool,String iEngineNo) throws Exception
    {




  	  String strSqlStatement = "DELETE FROM PrpNewCarRecord WHERE EngineNo= ? ";
  	  dbpool.prepareInnerStatement(strSqlStatement);
  	  dbpool.setString(1, iEngineNo);
  	  dbpool.executePreparedUpdate();
  	  dbpool.closePreparedStatement();
      
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iEngineNo EngineNo
     *@return ��
     */
    public void cancel(String iEngineNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      try
      {
        dbpool.beginTransaction();
        cancel(dbpool,iEngineNo);
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
     * ��dbpool����EngineNo��ȡ����
     *@param iEngineNo EngineNo
     *@param iRackNo RackNo
     *@return ��
     */
    public void getData(String iEngineNo,String iRackNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iEngineNo,iRackNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����EngineNo��ȡ����
     *@param dbpool ���ӳ�
     *@param iEngineNo EngineNo
     *@return ��
     */
    public void getData(DbPool dbpool,String iEngineNo ,String iRackNo) throws Exception
    {
      
      
      
      
      String strWherePart = " EngineNo= ? and RackNo= ?";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iEngineNo);
      arrWhereValue.add(iRackNo);
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
        DBPrpNewCarRecord dbPrpNewCarRecord = new DBPrpNewCarRecord();
        if (iLimitCount > 0 && dbPrpNewCarRecord.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpNewCarRecord.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpNewCarRecord WHERE " + iWherePart;
            schemas = dbPrpNewCarRecord.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    /**
     * ����dbpool����XX�Ż�ȡ����
     *@param iEndorseNo XX��
     *@return ��
     */
    public void getDataByValidNo(String iEngineNo,String iRackNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
      getDataByEngineNo(dbpool,iEngineNo,iRackNo);
      dbpool.close();
    }
      
    /**
     * ��dbpool����XX�Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param ValidNo XXȷ����
     *@return ��
     */
    public void getDataByEngineNo(DbPool dbpool,String iEngineNo,String iRackNo) throws Exception
    {
    String strWherePart = "";
    strWherePart = "EngineNo= '" + iEngineNo + "'"+" and RackNo = '"+iRackNo+"'";
    query(dbpool,strWherePart,0);
    }
    /**
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param null null
     *@return ��
     */
    public void cancelByEngineNo(DbPool dbpool,String iEngineNo,String iRackNo) throws Exception
    {



      String strSqlStatement = "DELETE FROM PrpNewCarRecord WHERE EngineNo=  ? and RackNo = ?";
	  dbpool.prepareInnerStatement(strSqlStatement);
	  dbpool.setString(1, iEngineNo);
	  dbpool.setString(2, iRackNo);
	  dbpool.executePreparedUpdate();
	  dbpool.closePreparedStatement();
    }
    
    /**
     * ���ݲ�ѯ��������ѯSysConfig.CONST_SUNQUERYDATASOURCE����Դ��Ӧ������.���������¼����schemas���� ����dbpool
     * @author wangchuanzhong 20100531
     * @param iWherePart ��ѯ����
     * @param intPageNum ҳ��
     * @param intLineNumPage ÿҳ����
     * @return ��
     * @throws Exception
     */
    public void queryCarRecordInfo(String iWherePart, int intPageNum,int intLineNumPage) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConfig.CONST_SUNQUERYDATASOURCE);
        this.queryCarRecordInfo(dbpool,iWherePart,intPageNum,intLineNumPage);
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
     * @author wangchuanzhong 201005231
     * @param iWherePart ��ѯ����
     * @param intPageNum ҳ��
     * @param intLineNumPage ÿҳ����
     * @return ��
     * @throws Exception
     */
     public void queryCarRecordInfo(DbPool dbpool,String iWherePart, int intPageNum,int intLineNumPage
     		) throws UserException,Exception
     {
         
         if(iWherePart.indexOf("'null'")>-1){
             throw new Exception("��ѯ�����쳣������ϵϵͳ����Ա��");
         }
         
       StringBuffer strSqlStatement = new StringBuffer();
       int intStartNum = 0;
       int intEndNum = 0;

       intStartNum = (intPageNum - 1) * intLineNumPage;
       intEndNum = intPageNum * intLineNumPage;

       DBPrpNewCarRecord dbPrpNewCarRecord = new DBPrpNewCarRecord();
       initArr();
       strSqlStatement.append(" SELECT * FROM ( ");
       strSqlStatement.append("Select RowNum As LineNum,T.* From ( ");
       strSqlStatement.append("Select PrpNewCarRecord.* From PrpNewCarRecord Where ");
       strSqlStatement.append(iWherePart);
       strSqlStatement.append(") T Where RowNum<=");
       strSqlStatement.append(intEndNum);
       strSqlStatement.append(") Where LineNum>");
       strSqlStatement.append(intStartNum);
       schemas = dbPrpNewCarRecord.findByConditions(dbpool,strSqlStatement.toString());
     }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
