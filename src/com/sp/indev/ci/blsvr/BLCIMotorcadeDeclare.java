package com.sp.indiv.ci.blsvr;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.indiv.ci.dbsvr.DBCIMotorcadeDeclare;
import com.sp.indiv.ci.schema.CIMotorcadeDeclareSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����CIMotorcadeDeclare-�ų��걨�ӿڱ��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-03-19</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 *              5.getData��������try��catch��finally�쳣����;
 */
public class BLCIMotorcadeDeclare{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLCIMotorcadeDeclare(){
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
     *����һ��CIMotorcadeDeclareSchema��¼
     *@param iCIMotorcadeDeclareSchema CIMotorcadeDeclareSchema
     *@throws Exception
     */
    public void setArr(CIMotorcadeDeclareSchema iCIMotorcadeDeclareSchema) throws Exception
    {
      try
      {
        schemas.add(iCIMotorcadeDeclareSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��CIMotorcadeDeclareSchema��¼
     *@param index �±�
     *@return һ��CIMotorcadeDeclareSchema����
     *@throws Exception
     */
    public CIMotorcadeDeclareSchema getArr(int index) throws Exception
    {
      CIMotorcadeDeclareSchema cIMotorcadeDeclareSchema = null;
       try
       {
         cIMotorcadeDeclareSchema = (CIMotorcadeDeclareSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return cIMotorcadeDeclareSchema;
     }
    /**
     *ɾ��һ��CIMotorcadeDeclareSchema��¼
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
      DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
      if (iLimitCount > 0 && dbCIMotorcadeDeclare.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIMotorcadeDeclare.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIMotorcadeDeclare WHERE " + iWherePart; 
        schemas = dbCIMotorcadeDeclare.findByConditions(strSqlStatement);
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
      DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
      if (iLimitCount > 0 && dbCIMotorcadeDeclare.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLCIMotorcadeDeclare.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM CIMotorcadeDeclare WHERE " + iWherePart; 
        schemas = dbCIMotorcadeDeclare.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbCIMotorcadeDeclare.setSchema((CIMotorcadeDeclareSchema)schemas.get(i));
        dbCIMotorcadeDeclare.insert(dbpool);
      }
    }
    
    public void saveBySeq(DbPool dbpool) throws Exception
    {
      DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbCIMotorcadeDeclare.setSchema((CIMotorcadeDeclareSchema)schemas.get(i));
        dbCIMotorcadeDeclare.insertBySeq(dbpool);
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
        
        String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
          dbpool.open("platformNewDataSource");
        } else {
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        }
        
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
     *@param iSerialNo ���к�
     *@return ��
     */
    public void cancel(DbPool dbpool,String iSerialNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM CIMotorcadeDeclare WHERE SerialNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iSerialNo);
    	dbpool.executePreparedUpdate();
    	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iSerialNo ���к�
     *@return ��
     */
    public void cancel(String iSerialNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        
        String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
          dbpool.open("platformNewDataSource");
        } else {
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        }
        
        dbpool.beginTransaction();
        cancel(dbpool,iSerialNo);
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
     * ��dbpool�������кŻ�ȡ����
     *@param iSerialNo ���к�
     *@return ��
     */
    public void getData(String iSerialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        
        String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
        if ("1".equals(strSwitch)) {
          dbpool.open("platformNewDataSource");
        } else {
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        }
        
        getData(dbpool,iSerialNo);
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
     * ����dbpool�������кŻ�ȡ����
     *@param dbpool ���ӳ�
     *@param iSerialNo ���к�
     *@return ��
     */
    public void getData(DbPool dbpool,String iSerialNo) throws Exception
    {
        
        
        
        
        String strWherePart = " SerialNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iSerialNo);
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
        DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
        if (iLimitCount > 0 && dbCIMotorcadeDeclare.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLCIMotorcadeDeclare.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM CIMotorcadeDeclare WHERE " + iWherePart;
            schemas = dbCIMotorcadeDeclare.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
    
    /**
     * ��������Ĳ�������XX����
     *@param iProposalNo XX����
     *@param iContractNo Э���
     *@param iRackno ���ܺ�
     *@param iCarmark ���ƺ�
     *@param iEngineNo ��������
     *@return ��
     */
    public void updateProposalNo(String iProposalNo,String iContractNo,String iRackno,String iCarmark,String iEngineNo) throws Exception{
    	 DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
    	 dbCIMotorcadeDeclare.updateProposalNo(iProposalNo,iContractNo,iRackno,iCarmark,iEngineNo);
    }
    
    /**
     * ���CIMotorcadeDeclare���XX����
     *@param iProposalNo XX����
     *@return ��
     */
    public void cancelProposalNo(String iProposalNo) throws Exception{
    	DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
    	dbCIMotorcadeDeclare.cancelProposalNo(iProposalNo);
    }
}
