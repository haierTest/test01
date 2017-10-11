package com.sp.prpall.blsvr.misc;

import java.util.Vector;
import java.util.ArrayList;
import java.util.List;

import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.visa.dto.custom.VisaInfoDto;
import com.sp.prpall.dbsvr.misc.DBPrpActivedCardHis;
import com.sp.prpall.schema.PrpActivedCardHisSchema;

/**
 * ����PrpActivedCardHis��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-08-04</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 */
public class BLPrpActivedCardHis{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpActivedCardHis(){
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
     *����һ��PrpActivedCardHisSchema��¼
     *@param iPrpActivedCardHisSchema PrpActivedCardHisSchema
     *@throws Exception
     */
    public void setArr(PrpActivedCardHisSchema iPrpActivedCardHisSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpActivedCardHisSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��PrpActivedCardHisSchema��¼
     *@param index �±�
     *@return һ��PrpActivedCardHisSchema����
     *@throws Exception
     */
    public PrpActivedCardHisSchema getArr(int index) throws Exception
    {
     PrpActivedCardHisSchema prpActivedCardHisSchema = null;
       try
       {
        prpActivedCardHisSchema = (PrpActivedCardHisSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpActivedCardHisSchema;
     }
    /**
     *ɾ��һ��PrpActivedCardHisSchema��¼
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
      DBPrpActivedCardHis dbPrpActivedCardHis = new DBPrpActivedCardHis();
      if (iLimitCount > 0 && dbPrpActivedCardHis.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpActivedCardHis.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpActivedCardHis WHERE " + iWherePart; 
        schemas = dbPrpActivedCardHis.findByConditions(strSqlStatement);
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
      DBPrpActivedCardHis dbPrpActivedCardHis = new DBPrpActivedCardHis();
      if (iLimitCount > 0 && dbPrpActivedCardHis.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpActivedCardHis.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpActivedCardHis WHERE " + iWherePart; 
        schemas = dbPrpActivedCardHis.findByConditions(dbpool,strSqlStatement);
      }
    }
    
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@author echaonan 20100804
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
        DBPrpActivedCardHis dbPrpActivedCardHis = new DBPrpActivedCardHis();
        if (iLimitCount > 0 && dbPrpActivedCardHis.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpActivedCardHis.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpActivedCardHis WHERE " + iWherePart;
            schemas = dbPrpActivedCardHis.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    } 
    
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpActivedCardHis dbPrpActivedCardHis = new DBPrpActivedCardHis();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpActivedCardHis.setSchema((PrpActivedCardHisSchema)schemas.get(i));
      dbPrpActivedCardHis.insert(dbpool);
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
     *@param iVisaCode VisaCode
     *@return ��
     */
    public void cancel(DbPool dbpool,String iVisaCode) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpActivedCardHis WHERE VisaCode= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iVisaCode);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iVisaCode VisaCode
     *@return ��
     */
    public void cancel(String iVisaCode ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iVisaCode);
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
     * ��dbpool����VisaCode��ȡ����
     *@param iVisaCode VisaCode
     *@return ��
     */
    public void getData(String iVisaCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      getData(dbpool,iVisaCode);
      dbpool.close();
    }
      
    /**
     * ����dbpool����VisaCode��ȡ����
     *@param dbpool ���ӳ�
     *@param iVisaCode VisaCode
     *@return ��
     */
    public void getData(DbPool dbpool,String iVisaCode) throws Exception
    {
      String strWherePart = "";
      strWherePart = "VisaCode= '" + iVisaCode + "'";
      query(dbpool,strWherePart,0);
    }
    
    /**
     * ���¹�����У�����ˮ�ż��ϣ���ʷ�Ѽ������ݲ�����У��
     * @param listVisaInfo
     * @return ȥ����ʷ�Ѽ������ݺ󼯺�
     * @throws Exception
     */
    public List dealHisVisa(List listVisaInfo) throws Exception
    {
    	List listReturn = new ArrayList();
    	VisaInfoDto visaInfoDto = new VisaInfoDto();
    	
    	String iWherePart = "";
    	ArrayList listWhereValue = null;
    	
    	iWherePart = " VISACODE = ? AND BILLSTARTNO = ? AND BILLENDNO = ?";
    	DbPool dbpool = new DbPool();
        try{
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        	for(int i=0;i<listVisaInfo.size();i++)
    	    {   
    	    	listWhereValue = new ArrayList();
    		    visaInfoDto = (VisaInfoDto)listVisaInfo.get(i);
    		    listWhereValue.add(visaInfoDto.getVisaCode());
    		    listWhereValue.add(visaInfoDto.getStartSerialNo());
    		    listWhereValue.add(visaInfoDto.getEndSerialNo());
    	        this.query(dbpool, iWherePart, listWhereValue, 0);
    		    if(this.getSize() > 0)
    		    {
    			    continue;
    		    }
    		    listReturn.add(visaInfoDto);
    	    }
        }catch(Exception e)
        {
        	e.printStackTrace();
        	throw e;
        }finally
        {
          dbpool.close();
        }
		return listReturn;
    }
    
    public void delete(DbPool dbpool,String strSQL ,ArrayList iWhereValue) throws Exception{
    if(iWhereValue != null)
    {
      dbpool.prepareInnerStatement(strSQL);
      for(int i=0;i<iWhereValue.size();i++)
        {
          dbpool.setString(i+1,(iWhereValue.get(i)).toString());
        }
      	dbpool.executePreparedQuery();
    }else
    {
         dbpool.delete(strSQL);
    }      
   }  
      
    /**
     * ����dbpool��ɾ������
     *@param iVisaCode VisaCode
     *@return ��
     */
    public void detele(String strSQL ,ArrayList iWhereValue) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        strSQL = "DELETE FROM PrpActivedCardHis WHERE " + strSQL ;
        delete(dbpool,strSQL , iWhereValue);
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
    public void query(String strSQL ,ArrayList iWhereValue) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        query(dbpool,strSQL,iWhereValue, 0);
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
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
