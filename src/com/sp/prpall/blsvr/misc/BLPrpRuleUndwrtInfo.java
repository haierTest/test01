package com.sp.prpall.blsvr.misc;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.dbsvr.misc.DBPrpRuleUndwrtInfo;
import com.sp.prpall.dbsvr.misc.DBTbItemDevice;
import com.sp.prpall.schema.PrpRuleUndwrtInfoSchema;

/**
 * ���������淵�غ�XXXXXǰ����Ϣ,�漰���õ�XXXXX�͹���Ϣ�⡢XXXXX������Ϣ�⡢XXXXX�Զ���Լ��
 * ����PrpRuleUndwrtInfo��BL��
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>@createdate 2015-01-06</p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpRuleUndwrtInfo{
	
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpRuleUndwrtInfo(){
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
     *����һ��PrpRuleUndwrtInfoSchema��¼
     *@param iPrpRuleUndwrtInfoSchema PrpRuleUndwrtInfoSchema
     *@throws Exception
     */
    public void setArr(PrpRuleUndwrtInfoSchema iPrpRuleUndwrtInfoSchema) throws Exception
    {
       try
       {
         schemas.add(iPrpRuleUndwrtInfoSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    
    /**
     *�õ�һ��PrpRuleUndwrtInfoSchema��¼
     *@param index �±�
     *@return һ��PrpRuleUndwrtInfoSchema����
     *@throws Exception
     */
    public PrpRuleUndwrtInfoSchema getArr(int index) throws Exception
    {
       PrpRuleUndwrtInfoSchema prpRuleUndwrtInfoSchema = null;
       try
       {
        prpRuleUndwrtInfoSchema = (PrpRuleUndwrtInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpRuleUndwrtInfoSchema;
     }
    
    /**
     *ɾ��һ��PrpRuleUndwrtInfoSchema��¼
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
      DBPrpRuleUndwrtInfo dbPrpRuleUndwrtInfo = new DBPrpRuleUndwrtInfo();
      if (iLimitCount > 0 && dbPrpRuleUndwrtInfo.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpRuleUndwrtInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpRuleUndwrtInfo WHERE " + iWherePart; 
        schemas = dbPrpRuleUndwrtInfo.findByConditions(strSqlStatement, bindValues);
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
      DBPrpRuleUndwrtInfo dbPrpRuleUndwrtInfo = new DBPrpRuleUndwrtInfo();
      if (iLimitCount > 0 && dbPrpRuleUndwrtInfo.getCount(iWherePart,bindValues) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpRuleUndwrtInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpRuleUndwrtInfo WHERE " + iWherePart; 
        schemas = dbPrpRuleUndwrtInfo.findByConditions(dbpool, strSqlStatement, bindValues);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpRuleUndwrtInfo dbPrpRuleUndwrtInfo = new DBPrpRuleUndwrtInfo();
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
      dbPrpRuleUndwrtInfo.setSchema((PrpRuleUndwrtInfoSchema)schemas.get(i));
      dbPrpRuleUndwrtInfo.insert(dbpool);
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
    	e.printStackTrace();
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
     *@param proposalNo ProposalNo
     *@param riskCode RiskCode
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancel(DbPool dbpool, String proposalNo, String serialNo) throws Exception
    {
        String strSqlStatement = " DELETE FROM PrpRuleUndwrtInfo WHERE proposalNo=? AND serialNo=?";
      	dbpool.prepareInnerStatement(strSqlStatement);
      	dbpool.setString(1, proposalNo);
      	dbpool.setString(2, serialNo);
  	    dbpool.executePreparedUpdate();
  	    dbpool.closePreparedStatement();
    }
    
    /**
     * ��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param proposalNo ProposalNo
     *@param riskCode RiskCode
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancel(DbPool dbpool, String proposalNo) throws Exception
    {
        String strSqlStatement = " DELETE FROM PrpRuleUndwrtInfo WHERE proposalNo=?";
      	dbpool.prepareInnerStatement(strSqlStatement);
      	dbpool.setString(1, proposalNo);
  	    dbpool.executePreparedUpdate();
  	    dbpool.closePreparedStatement();
    }
      
    /**
     * ����dbpool��ɾ������
     *@param proposalNo ProposalNo
     *@param riskCode RiskCode
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancel(String proposalNo,String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool, proposalNo,serialNo);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
    	e.printStackTrace();
        dbpool.rollbackTransaction();
      }
      finally
      {
        dbpool.close();
      }
    }
    
    
    /**
     * ����dbpool��ɾ������
     *@param proposalNo ProposalNo
     *@param riskCode RiskCode
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancelPrpRule(String proposalNo,String riskCode) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
    	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancelPrpRule(dbpool, proposalNo,riskCode);
        dbpool.commitTransaction(); 
      }
      catch (Exception e)
      {
    	e.printStackTrace();
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
     *@param proposalNo ProposalNo
     *@param riskCode RiskCode
     *@param serialNo SerialNo
     *@return ��
     */
    public void cancelPrpRule(DbPool dbpool, String proposalNo, String riskCode) throws Exception
    {
        String strSqlStatement = " DELETE FROM PrpRuleUndwrtInfo WHERE proposalNo=? AND riskCode=?";
      	dbpool.prepareInnerStatement(strSqlStatement);
      	dbpool.setString(1, proposalNo);
      	dbpool.setString(2, riskCode);
  	    dbpool.executePreparedUpdate();
  	    dbpool.closePreparedStatement();
    }
    
      
    /**
     * ����dbpool����������ȡ����
     *@param proposalNo ProposalNo
     *@param riskCode RiskCode
     *@param serialNo SerialNo
     *@return ��
     */
    public void getData(String proposalNo,String serialNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        getData(dbpool,proposalNo,serialNo);
      }
      catch (Exception e)
      {
        e.printStackTrace();
        throw e;
      }
      finally
      {
        dbpool.close();
      }
    }
    
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param proposalNo ProposalNo
     *@param riskCode RiskCode
     *@param serialNo SerialNo
     *@return ��
     */
    public void getData(DbPool dbpool, String proposalNo,String serialNo) throws Exception
    {
      String strWherePart = "proposalNo=? AND serialNo=?";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(proposalNo);
      arrWhereValue.add(serialNo);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@author liuweichang 20150106
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
        DBPrpRuleUndwrtInfo dbPrpRuleUndwrtInfo = new DBPrpRuleUndwrtInfo();
        if (iLimitCount > 0 && dbPrpRuleUndwrtInfo.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpRuleUndwrtInfo.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM PrpRuleUndwrtInfo WHERE " + iWherePart;
            schemas = dbPrpRuleUndwrtInfo.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    /**
     * ��dbpool����������ȡ����
     *@param dbpool ���ӳ�
     *@param proposalNo ProposalNo
     *@return ��
     */
    public void getData(DbPool dbpool, String proposalNo) throws Exception
    {
      String strWherePart = " proposalNo=?";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(proposalNo);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    /**
     * @author liuweichang-ghq 20150112
     * ���ݲ�����ȡSerialNo���ֵ
     * @param proposalNo
     * @param riskCode
     * @return
     * @throws Exception
     */
    public int getMaxSerialNo(String proposalNo) throws Exception{
    	int intResult = 0;
        DBPrpRuleUndwrtInfo dbPrpRuleUndwrtInfo = new DBPrpRuleUndwrtInfo();
        DbPool dbpool = new DbPool();
        try
        {
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
          String strWherePart = "ProposalNo=? ";
          ArrayList arrWhereValue = new ArrayList();
          arrWhereValue.add(proposalNo);
          intResult = dbPrpRuleUndwrtInfo.queryMaxSerialNo(dbpool,strWherePart,arrWhereValue);
        }catch (Exception e){
        	e.printStackTrace();
        	throw e;
        }finally{
          dbpool.close();
        }
    	return intResult;
    }
    
    /**
     * @author liuweichang-ghq 20150112
     * XXXXX����򷵻ص�ת�˹���ʾ
     * @param iListRuleInfo ת�˹���ʾ��Ϣ
     * @param iProposalNo XX����
     * @param iRiskCode XXXXX��
     * @param iRuleFlag �ӿڱ�ʶ(1�͹���Ϣ�⡢2������Ϣ�⡢3�Զ���Լ)
     * @param iRemark  (1�Ϲ���ʾ.2ת�˹���ʾ3�ʼ���ʾ)
     * @throws Exception
     */
    public void saveRuleUndwrtInfo(List<String> iListRuleInfo,String iProposalNo,String iRiskCode,String iRuleFlag,String iRemark) throws Exception{
    	int maxSerialNo = getMaxSerialNo(iProposalNo)+1;
    	for(int i=0;i<iListRuleInfo.size();i++){
    		PrpRuleUndwrtInfoSchema prpRuleUndwrtInfoSchema = new PrpRuleUndwrtInfoSchema();
    		prpRuleUndwrtInfoSchema.setProposalNo(iProposalNo);
    		prpRuleUndwrtInfoSchema.setRiskCode(iRiskCode);
    		prpRuleUndwrtInfoSchema.setSerialNo(""+(maxSerialNo++));
    		prpRuleUndwrtInfoSchema.setUndwrtInfo(iListRuleInfo.get(i));
    		prpRuleUndwrtInfoSchema.setFlag(iRuleFlag);
    		prpRuleUndwrtInfoSchema.setRemark(iRemark);
    		this.setArr(prpRuleUndwrtInfoSchema);
    	}
    	this.save();
    }
    
    /**
     * @author zhangpengfei  20150603
     * ����ɾ��������Ϣ
     * @param iProposalNo XX����/��ˮ��
     * @param iRiskCode XXXXX��
     * @param iRuleFlag �ӿڱ�ʶ(1�͹���Ϣ�⡢2������Ϣ�⡢3�Զ���Լ)
     * @throws Exception
     */
    public void delete(String iProposalNo,String iRiskCode,String iRuleFlag) throws Exception
    {
    	DbPool dbpool = new DbPool();
        try
        {
      	    dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            dbpool.beginTransaction();
            String strSqlStatement = " DELETE FROM PrpRuleUndwrtInfo WHERE proposalNo=? AND serialNo=? AND flag=?";
        	dbpool.prepareInnerStatement(strSqlStatement);
        	dbpool.setString(1, iProposalNo);
        	dbpool.setString(2, iRiskCode);
        	dbpool.setString(3, iRuleFlag);
    	    dbpool.executePreparedUpdate();
    	    dbpool.closePreparedStatement();
            dbpool.commitTransaction(); 
        }
        catch (Exception e)
        {
      	e.printStackTrace();
          dbpool.rollbackTransaction();
        }
        finally
        {
          dbpool.close();
        }
    }
    public void deleteRuleUndwrtInfo(String iProposalNo,String iRiskCode,String iRuleFlag) throws Exception{
    	    this.delete(iProposalNo,iRiskCode,iRuleFlag);
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
