package com.sp.prpall.blsvr.misc;

import java.util.HashMap;
import java.util.Vector;

import com.sp.payment.blsvr.jf.BLPrpJplanCommission;
import com.sp.payment.schema.PrpJplanCommissionSchema;
import com.sp.prpall.blsvr.jf.BLPrpQueryPaymentService;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.prpall.blsvr.pg.BLPrpPhead;
import com.sp.prpall.dbsvr.misc.DBPrpCPayablefee;
import com.sp.prpall.schema.PrpCPayablefeeSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����prpCPayablefee��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-11-13</p> 
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 *              5.getData��������try��catch��finally�쳣����;
 */
public class BLPrpCPayablefee{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpCPayablefee(){
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
     *����һ��PrpCPayablefeeSchema��¼
     *@param iPrpCPayablefeeSchema PrpCPayablefeeSchema
     *@throws Exception
     */
    public void setArr(PrpCPayablefeeSchema iPrpCPayablefeeSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpCPayablefeeSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    /**
     *�õ�һ��PrpCPayablefeeSchema��¼
     *@param index �±�
     *@return һ��PrpCPayablefeeSchema����
     *@throws Exception
     */
    public PrpCPayablefeeSchema getArr(int index) throws Exception
    {
      PrpCPayablefeeSchema prpCPayablefeeSchema = null;
       try
       {
         prpCPayablefeeSchema = (PrpCPayablefeeSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return prpCPayablefeeSchema;
     }
    /**
     *ɾ��һ��PrpCPayablefeeSchema��¼
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
      DBPrpCPayablefee dbPrpCPayablefee = new DBPrpCPayablefee();
      if (iLimitCount > 0 && dbPrpCPayablefee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCPayablefee.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCPayablefee WHERE " + iWherePart; 
        schemas = dbPrpCPayablefee.findByConditions(strSqlStatement);
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
      DBPrpCPayablefee dbPrpCPayablefee = new DBPrpCPayablefee();
      if (iLimitCount > 0 && dbPrpCPayablefee.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpCPayablefee.query");
      }
      else
      {
        initArr();
        strSqlStatement = " SELECT * FROM PrpCPayablefee WHERE " + iWherePart; 
        schemas = dbPrpCPayablefee.findByConditions(dbpool,strSqlStatement);
      }
    }
      
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
      DBPrpCPayablefee dbPrpCPayablefee = new DBPrpCPayablefee();
      
      int i = 0;
      
      for(i = 0; i< schemas.size(); i++)
      {
        dbPrpCPayablefee.setSchema((PrpCPayablefeeSchema)schemas.get(i));
        dbPrpCPayablefee.insert(dbpool);
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
    public void cancel(DbPool dbpool,String iPolicyNo) throws Exception
    {




    	String strSqlStatement = " DELETE FROM PrpCPayablefee WHERE PolicyNo= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iPolicyNo);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iPolicyNo XX��
     *@return ��
     */
    public void cancel(String iPolicyNo ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,iPolicyNo);
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
    public void getData(String iPolicyNo) throws Exception
    {
      DbPool dbpool = new DbPool();
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        getData(dbpool,iPolicyNo);
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
     * ����dbpool����XX�Ż�ȡ����
     *@param dbpool ���ӳ�
     *@param iPolicyNo XX��
     *@return ��
     */
    public void getData(DbPool dbpool,String iPolicyNo) throws Exception
    {
      String strWherePart = "";
      strWherePart = "PolicyNo= '" + iPolicyNo + "'";
      query(dbpool,strWherePart,0);
    }
    
    /**
     *@author gaohaifeng 20101115
     *@description ����dbpool�ĸ��·���
     *@param dbpool ���ӳ�
     *@return ��
     */
    public void update()throws Exception{
        DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            this.update(dbpool);
        } catch (Exception e) {
            dbpool.rollbackTransaction();
        } finally {
            dbpool.close();
        }
    }
    /**
     * @author gaohaifeng 20101115
     * @description ��dbpool�ĸ��·���
     * @param dbpool���ӳ�
     * @return ��
     */
    public void update(DbPool dbpool)throws Exception{
        DBPrpCPayablefee dbPrpCPayablefee = new DBPrpCPayablefee();
        for (int i = 0; i < schemas.size(); i++) {
            dbPrpCPayablefee.setSchema((PrpCPayablefeeSchema) schemas.get(i));
            dbPrpCPayablefee.update(dbpool);
        }
    }
    
    /**
     *@description �Ϲ����ʱ�����������ѳ�����Ĵ���
     *             
     *@param dbpool ���ӳ�
     *@param iPrpCPayablefeeSchema Ӧ�������ѳ�������
     *@return ��
     */
    public void updateNormal(BLEndorse blEndorse)throws Exception{
        DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            updateNormal(dbpool, blEndorse);
        } catch (Exception e) {
            throw e;
        } finally {
            dbpool.close();
        }
    }
    /**
     * @description �Ϲ����ʱ�����������ѳ�����Ĵ���
     * @param dbpool���ӳ�
     * @param iPrpCPayablefeeSchemaӦ�������ѳ�������
     * @return ��
     */
    public void updateNormal(DbPool dbpool,BLEndorse blEndorse)throws Exception{
    	
    	String strEndorseNo = blEndorse.getBLPrpPhead().getArr(0).getEndorseNo();
    	String strPolicyNo = blEndorse.getBLPrpPhead().getArr(0).getPolicyNo();
    	String strPayableFee = "";  
    	String strCanclePayFee = "";  
    	String strDisRate = blEndorse.getBLPrpCommission().getArr(0).getDisRate();  
    	String strOperateDate = blEndorse.getBLPrpPhead().getArr(0).getEndorDate();  
    	String strOperateCode = blEndorse.getBLPrpPhead().getArr(0).getOperatorCode();  
    	
    	BLPrpJplanCommission blPrpJplanCommission = new BLPrpJplanCommission();
    	
    	HashMap map = new HashMap();
        String strSwitch = BLPrpQueryPaymentService.queryMirrorSwitch();
        if ("1".equals(strSwitch)){
            map=paySplitGetPayCommissionFee(strPolicyNo);
        }else{
            map=blPrpJplanCommission.getPayCommissionFee(strPolicyNo);
        }
        
    	strPayableFee = (String)map.get("PLAN_FEE");  
    	strCanclePayFee = (-1.0 * Double.parseDouble((String)map.get("PLAN_FEE")))+"";  
    	  
        DBPrpCPayablefee dbPrpCPayablefee = new DBPrpCPayablefee();
        int intCount = dbPrpCPayablefee.getInfo(dbpool, strPolicyNo);
        if(intCount ==100){
            throw new UserException(-98,-1003,"BLPrpCPayablefee.updateNormal");
        }
        this.schemas.clear();
        PrpCPayablefeeSchema prpCPayablefeeSchema = new PrpCPayablefeeSchema();
        prpCPayablefeeSchema.setSchema(dbPrpCPayablefee);
        prpCPayablefeeSchema.setEndorseno(strEndorseNo);
        prpCPayablefeeSchema.setPayableFee(strPayableFee);
        prpCPayablefeeSchema.setCanclePayFee(strCanclePayFee);
        prpCPayablefeeSchema.setDisrate(strDisRate);
        prpCPayablefeeSchema.setOperatedate(strOperateDate);
        prpCPayablefeeSchema.setOperatorcode(strOperateCode);
        prpCPayablefeeSchema.setFlag("1");
        
        this.setArr(prpCPayablefeeSchema);
        this.update(dbpool);

    }
    
    /**
     *@description ���������ѳ�����,ʹ�仹ԭ��Ĭ��״̬
     *             
     *@param dbpool ���ӳ�
     *@param iPrpCPayablefeeSchema Ӧ�������ѳ�������
     *@return ��
     */
    public void backToDefault(String strEndorseNo)throws Exception{
        DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            backToDefault(dbpool, strEndorseNo);
        } catch (Exception e) {
            throw e;
        } finally {
            dbpool.close();
        }
    }
    /**
     * @description �Ϲ����ʱ�����������ѳ�����Ĵ���
     * @param dbpool���ӳ�
     * @param iPrpCPayablefeeSchemaӦ�������ѳ�������
     * @return ��
     */
    public void backToDefault(DbPool dbpool,String strEndorseNo)throws Exception{
    	
    	BLPrpPhead blPrpPhead = new BLPrpPhead();
    	blPrpPhead.getData(dbpool, strEndorseNo);
    	
    	if(blPrpPhead.getSize()>0){
    		String strPolicyNo = blPrpPhead.getArr(0).getPolicyNo();

            DBPrpCPayablefee dbPrpCPayablefee = new DBPrpCPayablefee();
            int intCount = dbPrpCPayablefee.getInfo(dbpool, strPolicyNo);
            if(intCount == 0){
            	this.schemas.clear();
                PrpCPayablefeeSchema prpCPayablefeeSchema = new PrpCPayablefeeSchema();
                prpCPayablefeeSchema.setSchema(dbPrpCPayablefee);
                prpCPayablefeeSchema.setEndorseno("");
                prpCPayablefeeSchema.setPayableFee("");
                prpCPayablefeeSchema.setCanclePayFee("");
                prpCPayablefeeSchema.setDisrate("");
                prpCPayablefeeSchema.setOperatedate("");
                prpCPayablefeeSchema.setOperatorcode("");
                prpCPayablefeeSchema.setFlag("0");
                
                this.setArr(prpCPayablefeeSchema);
                this.update(dbpool);
            }
    	}
    }
    
    /**
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
    /**
     * @description:�ո���ֿⷽ���������ݿ��ʽ����
     * @param strPolicyNo
     * @throws Exception
     * @author XuJingyu
     */
    public HashMap paySplitGetPayCommissionFee( String strPolicyNo) throws Exception {
        String strRealPayRefFee = "0";
        String strPlanFee = "0";
        HashMap hsMap  =new HashMap();
        StringBuffer buffer = new StringBuffer(200);
        
        buffer.append(" SELECT SUM(RealPayRefFee) RealPayRefFee,");
        buffer.append(" SUM(PlanFee-RealPayRefFee) PlanFee ");
        buffer.append(" FROM PrpJplanCommission ");
        buffer.append(" WHERE CertiType = 'S' ");
        buffer.append(" AND PolicyNo = '"+strPolicyNo+"' ");
        buffer.append(" AND PayFlag = '1' ");      
        Vector<PrpJplanCommissionSchema> vectorPrpJplanCommissionSchemas=BLPrpQueryPaymentService.queryByServlet(BLPrpQueryPaymentService.PrpJplanCommission,buffer.toString());
        for(PrpJplanCommissionSchema schema :vectorPrpJplanCommissionSchemas ){
            try{
                strRealPayRefFee = schema.getRealPayRefFee();
                strPlanFee = schema.getPlanFee();
                hsMap.put("REAL_PAY_FEE", strRealPayRefFee);
                hsMap.put("PLAN_FEE", strPlanFee);
            } catch (Exception exception) {
                throw exception;
            }              
        } 
        return (HashMap)hsMap;
    }
}
