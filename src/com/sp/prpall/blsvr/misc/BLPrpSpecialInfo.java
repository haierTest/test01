package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBPrpSpecialInfo;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpSpecialInfoSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;

/**
 * ����prpSpecialInfo��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>@createdate 2011-01-06</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.2
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 */
public class BLPrpSpecialInfo{

    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpSpecialInfo(){
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
     *����һ��PrpSpecialInfoSchema��¼
     *@param iPrpSpecialInfoSchema PrpSpecialInfoSchema
     *@throws Exception
     */
    public void setArr(PrpSpecialInfoSchema iPrpSpecialInfoSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpSpecialInfoSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpSpecialInfoSchema��¼
     *@param index �±�
     *@return һ��PrpSpecialInfoSchema����
     *@throws Exception
     */
    public PrpSpecialInfoSchema getArr(int index) throws Exception
    {
      PrpSpecialInfoSchema prpSpecialInfoSchema = null;
       try
       {
         prpSpecialInfoSchema = (PrpSpecialInfoSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpSpecialInfoSchema;
     }
    /**
     *ɾ��һ��PrpSpecialInfoSchema��¼
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
      DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();
      if (iLimitCount > 0 && dbPrpSpecialInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpSpecialInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpSpecialInfo WHERE " + iWherePart; 
        schemas = dbPrpSpecialInfo.findByConditions(strSqlStatement);
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
      DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();
      if (iLimitCount > 0 && dbPrpSpecialInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpSpecialInfo.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpSpecialInfo WHERE " + iWherePart; 
        schemas = dbPrpSpecialInfo.findByConditions(dbpool,strSqlStatement);
      }
    }
    
    /**
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
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
        DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();
        if (iLimitCount > 0 && dbPrpSpecialInfo.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpSpecialInfo.query");
        }else
        {
        	initArr();
            strSqlStatement = " SELECT * FROM PrpSpecialInfo WHERE " + iWherePart;
            schemas = dbPrpSpecialInfo.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
    
    public void query(String iWherePart,ArrayList iWhereValue, int iLimitCount) throws UserException,Exception
    {
        String strSqlStatement = "";
        DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();
        if (iLimitCount > 0 && dbPrpSpecialInfo.getCount(iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLPrpSpecialInfo.query");
        }else
        {
        	initArr();
            strSqlStatement = " SELECT * FROM PrpSpecialInfo WHERE " + iWherePart; 
            schemas = dbPrpSpecialInfo.findByConditions(strSqlStatement,iWhereValue);
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

      DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();
      if (iLimitCount > 0 && dbPrpSpecialInfo.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpPrintPool.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM ( " +
         "Select RowNum As LineNum,T.* From ( " +
         "Select * From prpSpecialInfo Where " + iWherePart +
         ") T Where RowNum<=" + intEndNum + ") Where LineNum>" +
         intStartNum ;
        schemas = dbPrpSpecialInfo.findByConditions(strSqlStatement);
      }
    }
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpSpecialInfo.setSchema((PrpSpecialInfoSchema)schemas.get(i));
        dbPrpSpecialInfo.insert(dbpool);
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
     *��dbpool��update����
     *@param ��
     *@return ��
     */
    public void update(DbPool dbpool) throws Exception
    {
      DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();

      int i = 0;

      for(i = 0; i< schemas.size(); i++)
      {
    	  dbPrpSpecialInfo.setSchema((PrpSpecialInfoSchema)schemas.get(i));
    	  dbPrpSpecialInfo.update(dbpool);
      }
    }

    /**
     *����dbpool�ĸ��·���
     *@param ��
     *@return ��
     */
    public void update() throws Exception
    {
      DbPool dbpool = new DbPool();

      dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));

      try
      {
        dbpool.beginTransaction();
        update(dbpool);
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
     *@param iProposalNo ProposalNo
     *@return ��
     */
    public void cancel(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpSpecialInfo WHERE ProposalNo= '" + iProposalNo + "'";
      
      dbpool.delete(strSqlStatement);
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iProposalNo ProposalNo
     *@return ��
     */
    public void cancel(String iProposalNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iProposalNo);
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
      
    public void getData(String iProposalNo) throws Exception
    {
    	
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			getData(dbpool, iProposalNo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbpool.close();
		}
		
    }
      
    /**
     * ����dbpool����ProposalNo��ȡ����
     *@param dbpool ���ӳ�
     *@param iProposalNo ProposalNo
     *@return ��
     */
    public void getData(DbPool dbpool,String iProposalNo) throws Exception
    {
      String strWherePart = " ProposalNo= ? ";
      ArrayList arrWhereValue = new ArrayList();
      arrWhereValue.add(iProposalNo);
      query(dbpool, strWherePart, arrWhereValue, 0);
    }
    
    /**
     *�����Ƿ�鵽����ҵ���ͬ�ţ����δ�鵽��Ϊ����XX
     *@param dbpool      ȫ�ֳ�
     *@param prpSpecialInfoSchema  ����ҵ��
     *@return ����ҵ���ͬ��
     *@throws Exception
     */
    public String getSpecialContractNo(PrpSpecialInfoSchema prpSpecialInfoSchema)throws Exception{
    	
		String[] columns1 = new String[4];
		String[] columns2 = new String[4];
		String[] operators = new String[4];
		ArrayList arrWhereValueAppli = new ArrayList(4);
		ArrayList arrWhereValueInsured= new ArrayList(4);
		columns1[0] = "AppliName";
		columns1[1] = "StartDate";
		columns1[2] = "SpecialBusiness";
		columns1[3] = "UnderWriteStatus";
		
		columns2[0] = "InsuredName";
		columns2[1] = "StartDate";
		columns2[2] = "SpecialBusiness";
		columns2[3] = "UnderWriteStatus";
		
		operators[0] = "=";
		operators[1] = ">";
		operators[2] = "=";
		operators[3] = "=";
		String wherePartAppli = PubTools.getSwhere(columns1, operators);
		String wherePartInsured = PubTools.getSwhere(columns2, operators);
		
		BLPrpSpecialInfo blPrpSpecialInfoAppli = new BLPrpSpecialInfo();
		BLPrpSpecialInfo blPrpSpecialInfoInsured = new BLPrpSpecialInfo();
		
		arrWhereValueAppli.add(prpSpecialInfoSchema.getAppliName());
		arrWhereValueAppli.add("");
		arrWhereValueAppli.add(prpSpecialInfoSchema.getSpecialBusiness());
		arrWhereValueAppli.add("1");
		arrWhereValueInsured.add(prpSpecialInfoSchema.getInsuredName());
		arrWhereValueInsured.add("");
		arrWhereValueInsured.add(prpSpecialInfoSchema.getSpecialBusiness());
		arrWhereValueInsured.add("1");
		
		java.util.Date d = new java.util.Date(); 
		
		java.text.SimpleDateFormat dformat = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
		java.util.Date stratdate = dformat.parse(prpSpecialInfoSchema.getStartDate()); 
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(stratdate);
		if("a".equals(prpSpecialInfoSchema.getSpecialBusiness())){
			
			calendar.add(Calendar.DAY_OF_MONTH,-30); 
			d = calendar.getTime(); 
			String datetime = dformat.format(d); 
			arrWhereValueAppli.set(1,datetime);
			arrWhereValueInsured.set(1,datetime);
		}else if("8".equals(prpSpecialInfoSchema.getSpecialBusiness())){
			
    		calendar.add(Calendar.MONTH,-12); 
    		d = calendar.getTime();
    		String datetime = dformat.format(d);
    		arrWhereValueAppli.set(1,datetime);
    		arrWhereValueInsured.set(1,datetime);
		}
    	
    	
  	    blPrpSpecialInfoAppli.query(wherePartAppli, arrWhereValueAppli, 0);
  	    
  	    
  	    if(blPrpSpecialInfoAppli.getSize()>0){
  	      return  blPrpSpecialInfoAppli.getArr(0).getSpecialContractNo();
  	    }else{
	      blPrpSpecialInfoInsured.query(wherePartInsured, arrWhereValueInsured, 0);
	      
	  	  
	  	  if(blPrpSpecialInfoInsured.getSize()>0){
	  	   	return  blPrpSpecialInfoInsured.getArr(0).getSpecialContractNo(); 
	  	  }else{
	  	   	return "";
	  	  }
  	    }
    }
    /**
     *ͳ��ҵ�񣬲�ѯ��¼�������ҵ���ͬ���Ƿ���ȷ
     *@param strSpecialContractNo  ����ҵ���ͬ��
     *@return ϵͳ�Ѵ���¼������ҵ���ͬ�ŵĸ���
     *@throws Exception
     */
    public int getSpecialContractNoCount(String strSpecialBusiness,String strSpecialContractNo )throws Exception{
    	String[] columns1 = new String[3];
		String[] operators = new String[3];
		ArrayList arrWhereValue = new ArrayList();
		columns1[0] = "SpecialBusiness";
		columns1[1] = "SpecialContractNo";
		columns1[2] = "UnderWriteStatus";
		operators[0] = "=";
		operators[1] = "=";
		operators[2] = "=";
		String wherePart = PubTools.getSwhere(columns1, operators);
		arrWhereValue.add(strSpecialBusiness);
		arrWhereValue.add(strSpecialContractNo);
		arrWhereValue.add("1");
		DBPrpSpecialInfo dbPrpSpecialInfo = new DBPrpSpecialInfo();
		try{
		return dbPrpSpecialInfo.getCount(wherePart,arrWhereValue);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
    }
    /**
     *˫�����ͨ�������������XX����������ҵ���Ų���дBLprpSpecialInfo�����ͨ��UnderWriteStatus��Ϊ1
     *@param iProposalNo  XX����
     *@return ��
     *@throws Exception
     */
    public void echoBLprpSpecialInfo(DbPool dbpool,String iProposalNo) throws Exception{
    	BLPrpSpecialInfo blPrpSpecialInfo = new BLPrpSpecialInfo();
    	PrpSpecialInfoSchema prpSpecialInfoSchema = new  PrpSpecialInfoSchema();
    	blPrpSpecialInfo.getData(dbpool,iProposalNo);
    	
    	if(blPrpSpecialInfo.getSize()>0){
    		prpSpecialInfoSchema = blPrpSpecialInfo.getArr(0);
    		
    		if("1".equals(prpSpecialInfoSchema.getFirstFlag())){
    			String strSpecialContractNo = new ChgDate().getCurrentTime("yyyyMMddHHmmss")
    			                                 +prpSpecialInfoSchema.getComCode().substring(0,4)
    			                                 +prpSpecialInfoSchema.getSpecialBusiness();
    			prpSpecialInfoSchema.setSpecialContractNo(strSpecialContractNo);
    		}
    		prpSpecialInfoSchema.setUnderWriteStatus("1");
    		blPrpSpecialInfo.setArr(prpSpecialInfoSchema);
    		blPrpSpecialInfo.update(dbpool);
    	}
    }

    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
