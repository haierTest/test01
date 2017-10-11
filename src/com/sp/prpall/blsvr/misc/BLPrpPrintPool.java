package com.sp.prpall.blsvr.misc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.naming.NamingException;

import com.sp.prpall.dbsvr.cb.DBPrpCmain;
import com.sp.prpall.dbsvr.misc.DBPrpPrintPool;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;
import com.sp.prpall.schema.PrpPrintPoolSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;


/**
 * ����PrpPrintPool��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-01-21</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4) 
 * @LastVersion: v1.4.1
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 */
public class BLPrpPrintPool{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpPrintPool(){
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
     *����һ��PrpPrintPoolSchema��¼
     *@param iPrpPrintPoolSchema PrpPrintPoolSchema
     *@throws Exception
     */
    public void setArr(PrpPrintPoolSchema iPrpPrintPoolSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpPrintPoolSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpPrintPoolSchema��¼
     *@param index �±�
     *@return һ��PrpPrintPoolSchema����
     *@throws Exception
     */
    public PrpPrintPoolSchema getArr(int index) throws Exception
    {
      PrpPrintPoolSchema prpPrintPoolSchema = null;
       try
       {
         prpPrintPoolSchema = (PrpPrintPoolSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpPrintPoolSchema;
     }
    /**
     *ɾ��һ��PrpPrintPoolSchema��¼
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
      DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
      if (iLimitCount > 0 && dbPrpPrintPool.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPrintPool.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpPrintPool WHERE " + iWherePart; 
        schemas = dbPrpPrintPool.findByConditions(strSqlStatement);
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
      DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
      if (iLimitCount > 0 && dbPrpPrintPool.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPrintPool.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpPrintPool WHERE " + iWherePart; 
        schemas = dbPrpPrintPool.findByConditions(dbpool,strSqlStatement);
      }
    }
    
    /**
     * ���ݲ�ѯ��������ѯSysConfig.CONST_SUNQUERYDATASOURCE����Դ��Ӧ������.���������¼����schemas����
     * @param iWherePart ��ѯ����
     * @param intPageNum ҳ��
     * @param intLineNumPage ÿҳ����
     * @return ��
     * @throws Exception
     */
    public void query(String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
    {
      try {
        this.query(iWherePart,intPageNum,intLineNumPage,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
      }
      catch (Exception e)
      {
        throw e;
      }
    }
    
    /**
     *���ղ�ѯ�����õ�һ���¼�������ڷ�ҳ��ѯ�����������¼����schemas����
     *@param iWherePart ��ѯ����(�����������־�)
     *@param intPageNum ҳ��
     *@param intLineNumPage ÿҳ����
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@return ��
     *@throws Exception
     */
    public void query(String iWherePart,int intPageNum,int intLineNumPage,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      int intStartNum = 0;
      int intEndNum = 0;
      
      intStartNum = (intPageNum - 1) * intLineNumPage;
      
      intEndNum = intPageNum * intLineNumPage;

      DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
      if (iLimitCount > 0 && dbPrpPrintPool.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPrintPool.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM ( " +
         "Select RowNum As LineNum,T.* From ( " +
         "Select * From PrpPrintPool Where " + iWherePart +
         ") T Where RowNum<=" + intEndNum + ") Where LineNum>" +
         intStartNum ;
        schemas = dbPrpPrintPool.findByConditions(strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
      
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpPrintPool.setSchema((PrpPrintPoolSchema)schemas.get(i));
        dbPrpPrintPool.insert(dbpool);
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
     *@param iBusinessNo ҵ���
     *@return ��
     */
    public void cancel(DbPool dbpool,String iBusinessNo) throws Exception
    {
      



    	String strSqlStatement = " DELETE FROM PrpPrintPool WHERE BusinessNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iBusinessNo);
	    dbpool.executePreparedUpdate();
	    dbpool.closePreparedStatement();
        
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iBusinessNo ҵ���
     *@return ��
     */
    public void cancel(String iBusinessNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iBusinessNo);
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
     * ��dbpool����ҵ��Ż�ȡ����
     *@param iBusinessNo ҵ���
     *@return ��
     */
    public void getData(String iBusinessNo) throws Exception
    { 
      DbPool dbpool = new DbPool();
      try
      {
			
			String PrintDBPool = "0";
			try{
				PrintDBPool = SysConfig.getProperty("SunPrintQueryDBPool");
			}catch(Exception e){
				PrintDBPool="0";
			}
			if("0".equals(PrintDBPool)){
				dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			}else{
				dbpool.open(PrintDBPool);
			}
			
        getData(dbpool,iBusinessNo);
      }catch( Exception e ){  
      }finally
      {
        dbpool.close();
      }
      
    }
      
    /**
     * ����dbpool����ҵ��Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iBusinessNo ҵ���
     *@return ��
     */
    public void getData(DbPool dbpool,String iBusinessNo) throws Exception
    {
        
        
        
        
        String strWherePart = " BusinessNo= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iBusinessNo);
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
        DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
        if (iLimitCount > 0 && dbPrpPrintPool.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpPrintPool.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpPrintPool WHERE " + iWherePart;
            schemas = dbPrpPrintPool.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ������Ȩ
     * @param strBusinessNos  ��Ȩҵ�������
     * @return 
     * @throws Exception
     * @throws SQLException
     * @throws NamingException
     */
    public String batchAuthorize(String[] strBusinessNos) throws
        Exception,SQLException,NamingException{
        String strMessage = "";
        DbPool dbpool = new DbPool();
        
        
        try{
        	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        	dbpool.beginTransaction();
        	strMessage  = batchAuthorize(dbpool, strBusinessNos);
        	dbpool.commitTransaction();
        }catch(SQLException sqlException){
            dbpool.rollbackTransaction();
        	throw sqlException;
        }catch(NamingException namingException){
            dbpool.rollbackTransaction();
        	throw namingException;
        }finally{
        	dbpool.close();
        }
        
        return strMessage;
    }
    /**
     * ������Ȩ
     * @param dbpool
     * @param strBusinessNos  ��Ȩҵ�������
     * @return
     * @throws UserException
     * @throws Exception
     */
    public String batchAuthorize(DbPool dbpool, String[] strBusinessNos) throws UserException,Exception
    {
    	StringBuffer bufMessage = new StringBuffer();
    	DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
    	DBPrpCmain dbPrpCmain = new DBPrpCmain();
    	DBPrpPhead dbPrpPhead = new DBPrpPhead();
    	
    	String strBusinessNo = null;
    	String strBizType = null;
    	String strGroupFlag = null;
    	int intReturnPool = 0;
    	int intReturn = 0;
    	
    	for(int i=0;i<strBusinessNos.length;i++)
        {
    		strBusinessNo = strBusinessNos[i];
    		if(!"none".equals(strBusinessNo))
    		{
    			intReturnPool = dbPrpPrintPool.getInfo(dbpool, strBusinessNo);
        		
                if(intReturnPool == 100)
                {
                	bufMessage.append("�޴�ҵ���:");
                	bufMessage.append(strBusinessNo);
                	bufMessage.append("<br>");
                	continue;
                }
                strBizType = dbPrpPrintPool.getPrintType();
                if("P".equals(strBizType))
                {
                  intReturn = dbPrpCmain.getInfo(dbpool, strBusinessNo);
                  if(intReturn == 100)
                  {
                	  bufMessage.append("XX��:");
                	  bufMessage.append(strBusinessNo);
                	  bufMessage.append("���޾���ҵ�����ݣ�");
                	  bufMessage.append("<br>");
                	  continue;
                	  
                  }
                }else
                {
                  intReturn = dbPrpPhead.getInfo(dbpool, strBusinessNo);
                  if(intReturn == 100)
                  {
                	  bufMessage.append("������:");
                	  bufMessage.append(strBusinessNo);
                	  bufMessage.append("���޾���ҵ�����ݣ�");
                	  bufMessage.append("<br>");
                	  continue;
                  }
                }
                strGroupFlag = dbPrpPrintPool.getGroupFlag();
                if(!"1".equals(strGroupFlag))
                {
                	bufMessage.append("ҵ���:");
              	    bufMessage.append(strBusinessNo);
              	    bufMessage.append("Ϊ�Ǽ��д�ӡҵ�񣬲��ܽ�����Ȩ������");
              	    bufMessage.append("<br>");
              	    continue;
                }
                
                
                dbPrpPrintPool.setGroupFlag("0");
                dbPrpPrintPool.update(dbpool);
            }
        }
    	return bufMessage.toString();
    }
    
    /**
     * @description ������Ȩ����
     * @author gaohaifeng 20110402
     * @param strBusinessNos  ��Ȩҵ�������
     * @param strAuthorizeCode  ��Ȩ�˴���
     * @return
     * @throws UserException
     * @throws Exception
     */
    public String batchAuthorize(String[] strBusinessNos,String strAuthorizeCode) throws
        Exception,SQLException,NamingException{
        String strMessage = "";
        DbPool dbpool = new DbPool();
        
        try{
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            dbpool.beginTransaction();
            strMessage  = batchAuthorize(dbpool, strBusinessNos, strAuthorizeCode);
            dbpool.commitTransaction();
        }catch(SQLException sqlException){
            dbpool.rollbackTransaction();
            throw sqlException;
        }catch(NamingException namingException){
            dbpool.rollbackTransaction();
            throw namingException;
        }finally{
            dbpool.close();
        }
        return strMessage;
    }
    
    /**
     * @description ������Ȩ����
     * @author gaohaifeng 20110402
     * @param dbpool
     * @param strBusinessNos  ��Ȩҵ�������
     * @param strAuthorizeCode  ��Ȩ�˴���
     * @return
     * @throws UserException
     * @throws Exception
     */
    public String batchAuthorize(DbPool dbpool, String[] strBusinessNos,String strAuthorizeCode) throws UserException,Exception
    {
        StringBuffer bufMessage = new StringBuffer();
        DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
        DBPrpCmain dbPrpCmain = new DBPrpCmain();
        DBPrpPhead dbPrpPhead = new DBPrpPhead();
        ChgDate chgDate = new ChgDate();
        String strBusinessNo = null;
        String strBizType = null;
        String strGroupFlag = null;
        int intReturnPool = 0;
        int intReturn = 0;
        
        for(int i=0;i<strBusinessNos.length;i++)
        {
            strBusinessNo = strBusinessNos[i];
            if(!"none".equals(strBusinessNo))
            {
                intReturnPool = dbPrpPrintPool.getInfo(dbpool, strBusinessNo);
                
                if(intReturnPool == 100)
                {
                    bufMessage.append("�޴�ҵ���:");
                    bufMessage.append(strBusinessNo);
                    bufMessage.append("<br>");
                    continue;
                }
                strBizType = dbPrpPrintPool.getPrintType();
                if("P".equals(strBizType))
                {
                  intReturn = dbPrpCmain.getInfo(dbpool, strBusinessNo);
                  if(intReturn == 100)
                  {
                      bufMessage.append("XX��:");
                      bufMessage.append(strBusinessNo);
                      bufMessage.append("���޾���ҵ�����ݣ�");
                      bufMessage.append("<br>");
                      continue;
                      
                  }
                }else
                {
                  intReturn = dbPrpPhead.getInfo(dbpool, strBusinessNo);
                  if(intReturn == 100)
                  {
                      bufMessage.append("������:");
                      bufMessage.append(strBusinessNo);
                      bufMessage.append("���޾���ҵ�����ݣ�");
                      bufMessage.append("<br>");
                      continue;
                  }
                }
                strGroupFlag = dbPrpPrintPool.getGroupFlag();
                if(!"1".equals(strGroupFlag))
                {
                    bufMessage.append("ҵ���:");
                    bufMessage.append(strBusinessNo);
                    bufMessage.append("Ϊ�Ǽ��д�ӡҵ�񣬲��ܽ�����Ȩ������");
                    bufMessage.append("<br>");
                    continue;
                }
                
                
                dbPrpPrintPool.setGroupFlag("0");
                dbPrpPrintPool.setAuthorizeCode(strAuthorizeCode);
                dbPrpPrintPool.setAuthorizeDate(chgDate.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
                dbPrpPrintPool.update(dbpool);
            }
        }
        return bufMessage.toString();
    }
    
    
    /**
     * ���ݲ�ѯ��������ѯDDCCDATASOURCE����Դ��Ӧ������.���������¼����schemas����
     * @param iWherePart      ��ѯ����
     * @param arrWhereValue   ��ѯ����������ֵ
     * @param intPageNum      ҳ��
     * @param intLineNumPage  ÿҳ����
     * @return ��
     * @throws Exception
     */
    public void queryWithBindVariable(String iWherePart,ArrayList arrWhereValue, int intPageNum,int intLineNumPage)
            throws UserException,Exception
    {
    	DbPool dbpool = new DbPool();
    	
        try{
            
            
                
            
      		
      		String PrintDBPool = "0";
      		try{
      			PrintDBPool = SysConfig.getProperty("SunPrintQueryDBPool");
      		}catch(Exception e){
      			PrintDBPool="0";
      		}
      		if("0".equals(PrintDBPool)){
      			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
      		}else{
      			dbpool.open(PrintDBPool);
      		}
      		
            
            
        	queryWithBindVariable(dbpool, iWherePart,arrWhereValue, intPageNum,intLineNumPage,
        			Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
        }catch(SQLException sqlException){
        	throw sqlException;
        }catch(NamingException namingException){
        	throw namingException;
        }finally{
        	dbpool.close();
        }
    }
    
    /**
     * ���ղ�ѯ�����õ�һ���¼�������ڷ�ҳ��ѯ�����������¼����schemas����
     * @param strWherePart    ��ѯ����(�����������־�)
     * @param arrWhereValue   ��ѯ����������ֵ
     * @param intPageNum      ҳ��
     * @param intLineNumPage  ÿҳ����
     * @param iLimitCount     ��¼������(iLimitCount=0: ������)
     * @throws UserException
     * @throws Exception
     */
    public void queryWithBindVariable(DbPool dbpool,String strWherePart,ArrayList arrWhereValue, int intPageNum,int intLineNumPage,
    		int iLimitCount) throws UserException,Exception
    {
    	StringBuffer bufSqlStatement = new StringBuffer();
        int intStartNum = 0;
        int intEndNum = 0;
        
        intStartNum = (intPageNum - 1) * intLineNumPage;
        
        intEndNum = intPageNum * intLineNumPage;

        DBPrpPrintPool dbPrpPrintPool = new DBPrpPrintPool();
        if (iLimitCount > 0 && dbPrpPrintPool.getCount(dbpool,strWherePart,arrWhereValue) > iLimitCount)
        {
      	  throw new UserException(-98,-1003,"BLPrpPrintPool.query");
        }else
        {
        	initArr();
            bufSqlStatement.append(" SELECT * FROM ( ");
            bufSqlStatement.append("Select RowNum As LineNum,T.* From ( ");
            bufSqlStatement.append("Select * From PrpPrintPool Where ");
            bufSqlStatement.append(strWherePart);
            bufSqlStatement.append(") T Where RowNum<=");
            bufSqlStatement.append(intEndNum);
            bufSqlStatement.append(") Where LineNum>");
            bufSqlStatement.append(intStartNum);
            schemas = dbPrpPrintPool.findByConditions(dbpool, bufSqlStatement.toString(), arrWhereValue);
        }
    }
    
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
