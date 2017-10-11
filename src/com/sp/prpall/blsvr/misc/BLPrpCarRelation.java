package com.sp.prpall.blsvr.misc;

import java.util.*;

import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.misc.DBPrpCarRelation;
import com.sp.prpall.schema.PrpCarRelationSchema;

/**
 * ����PrpCarRelation-XXXXX�����������BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-11-07</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLPrpCarRelation{

    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpCarRelation(){
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
     *����һ��PrpCarRelationSchema��¼
     *@param iPrpCarRelationSchema PrpCarRelationSchema
     *@throws Exception
     */
    public void setArr(PrpCarRelationSchema iPrpCarRelationSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpCarRelationSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpCarRelationSchema��¼
     *@param index �±�
     *@return һ��PrpCarRelationSchema����
     *@throws Exception
     */
    public PrpCarRelationSchema getArr(int index) throws Exception
    {
      PrpCarRelationSchema prpCarRelationSchema = null;
       try
       {
         prpCarRelationSchema = (PrpCarRelationSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpCarRelationSchema;
     }
    /**
     *ɾ��һ��PrpCarRelationSchema��¼
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
      DBPrpCarRelation dbPrpCarRelation = new DBPrpCarRelation();
      if (iLimitCount > 0 && dbPrpCarRelation.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCarRelation.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCarRelation WHERE " + iWherePart; 
        schemas = dbPrpCarRelation.findByConditions(strSqlStatement);
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
      DBPrpCarRelation dbPrpCarRelation = new DBPrpCarRelation();
      if (iLimitCount > 0 && dbPrpCarRelation.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCarRelation.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCarRelation WHERE " + iWherePart; 
        schemas = dbPrpCarRelation.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpCarRelation dbPrpCarRelation = new DBPrpCarRelation();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpCarRelation.setSchema((PrpCarRelationSchema)schemas.get(i));
        dbPrpCarRelation.insert(dbpool);
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
     *@param iPolicyNo XX��
     *@return ��
     */
    public void cancel(DbPool dbpool,String iOrderNo ,String iSerialNo ) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpCarRelation WHERE OrderNo= '" + iOrderNo + "' and SerialNo = '"+iSerialNo+"'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iPolicyNo XX��
     *@return ��
     */
    public void cancel( String iOrderNo ,String iSerialNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iOrderNo , iSerialNo);
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
     * ��dbpool����XX�Ż�ȡ����
     *@param iPolicyNo XX��
     *@return ��
     */
    public void getData( String iOrderNo ,String iSerialNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iOrderNo,iSerialNo);
      dbpool.close();
    }
      
    /**
     * ����dbpool����XX�Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iPolicyNo XX��
     *@return ��
     */
    public void getData(DbPool dbpool,String iOrderNo ,String iSerialNo ) throws Exception
    {
      String strWherePart = "";
      strWherePart = "OrderNo= '" + iOrderNo + "' and SerialNo = '"+iSerialNo+"'";
      query(dbpool,strWherePart,0);
    }
    
    /**
     * ����dbpool����XX�Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iPolicyNo XX��
     *@return ��
     */
    public void getDataByProposalNoBI(DbPool dbpool,String iProposalNo ) throws Exception
    {
      String strWherePart = "";
      strWherePart = " ProposalNo= '" + iProposalNo + "'";
      query(dbpool,strWherePart,0);
      if(schemas.size()>0){
	      PrpCarRelationSchema prpCarRelationSchema = (PrpCarRelationSchema)schemas.get(0);
	      getData(dbpool,prpCarRelationSchema.getOrderNo(),"3");
      }
    }
    /**
     * ����dbpool����XX�Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iPolicyNo XX��
     *@return ��
     */
    public void getDataByProposalNoBI(String iProposalNo ) throws Exception
    {
        DbPool dbpool = new DbPool();
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        getDataByProposalNoBI(dbpool,iProposalNo);
        dbpool.close();
    }
    /**
     * ����Ƿ����ڹ����⽡XXXXX����ҵ��
     *@param iProductCode ��Ʒ����
     *@return boolean
     */
    public boolean checkCarRelation(String iProductCode ) throws Exception
    {
    	boolean isCarRelation = false;
    	if(SysConfig.getProperty("CheckCarRelation").indexOf(","+iProductCode+",")>-1){
    		isCarRelation = true;
    	}
    	return isCarRelation;
    }
    
    /**
     * ��ȡPrpCarRelationSchema�����б�
     * ���ݲ�ѯ��������ѯSysConfig.CONST_SUNQUERYDATASOURCE����Դ��Ӧ������.���������¼����schemas���� ����dbpool
     * @author sizhijin 20120905
     * @param iWherePart ��ѯ����
     * @param intPageNum ҳ��
     * @param intLineNumPage ÿҳ����
     * @return ��
     * @throws Exception
     */
    public void queryHasItemCarInfo(String iWherePart, int intPageNum,int intLineNumPage) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConfig.CONST_SUNQUERYDATASOURCE);
        this.queryHasItemCarInfo(dbpool,iWherePart,intPageNum,intLineNumPage);
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
     * ��ȡPrpCarRelationSchema�����б�
     * ��ѯ���������豸��Ϣ���������ѯprpCarRelation��PrpCitemCar�� ��dbpool
     * @author sizhijin 20120905
     * @param dbpool     ���ӳ�
     * @param iWherePart ��ѯ����
     * @param intPageNum ҳ��
     * @param intLineNumPage ÿҳ����
     * @return ��
     * @throws Exception
     */
     public void queryHasItemCarInfo(DbPool dbpool,String iWherePart, int intPageNum,int intLineNumPage
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

       DBPrpCarRelation dbPrpCarRelation = new DBPrpCarRelation();
       initArr();
       
       strSqlStatement.append(" SELECT * FROM ( ");
       strSqlStatement.append("Select RowNum As LineNum,C.* From ( ");
       strSqlStatement.append("Select PrpCarRelation.* From PrpCarRelation,PrpCitemCar Where ");
       strSqlStatement.append(iWherePart);
       strSqlStatement.append(") C Where RowNum<=");
       strSqlStatement.append(intEndNum);
       strSqlStatement.append(") Where LineNum>");
       strSqlStatement.append(intStartNum);
       schemas = dbPrpCarRelation.findByConditions(dbpool,strSqlStatement.toString());
     }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
