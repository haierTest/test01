package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;
import java.util.Vector;

import com.sp.prpall.dbsvr.misc.DBTBCust;
import com.sp.prpall.schema.PCustSchema;
import com.sp.prpall.schema.TBCustSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����TB_CUST��BL�� ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>
 * Title: SinosoftJavaTools v1.4
 * </p>
 * <p>
 * Description: �п���JavaԴ�����ɹ���
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * 
 * @createdate 2009-11-06
 *             </p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 */
public class BLTBCust {
	private Vector schemas = new Vector();

	/**
	 * ���캯��
	 */
	public BLTBCust() {
	}

	/**
	 * ��ʼ����¼
	 * 
	 * @param ��
	 * @return ��
	 * @throws Exception
	 */
	public void initArr() throws Exception {
		schemas = new Vector();
	}

	/**
	 * ����һ��TBCustSchema��¼
	 * 
	 * @param TBCustSchema
	 *            TBCustSchema
	 * @throws Exception
	 */
	public void setArr(TBCustSchema iTBCustSchema) throws Exception {
		try {
			schemas.add(iTBCustSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �õ�һ��TBCustSchema��¼
	 * 
	 * @param index
	 *            �±�
	 * @return һ��TBCustSchema����
	 * @throws Exception
	 */
	public TBCustSchema getArr(int index) throws Exception {
		TBCustSchema tnCustSchema = null;
		try {
			tnCustSchema = (TBCustSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return tnCustSchema;
	}

	/**
	 * ɾ��һ��TB_CUSTSchema��¼
	 * 
	 * @param index
	 *            �±�
	 * @throws Exception
	 */
	public void remove(int index) throws Exception {
		try {
			this.schemas.remove(index);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �õ�schemas��¼��
	 * 
	 * @return schemas��¼��
	 * @throws Exception
	 */
	public int getSize() throws Exception {
		return this.schemas.size();
	}

	/**
	 * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
	 * 
	 * @param iWherePart
	 *            ��ѯ����(���������־�)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart) throws UserException, Exception {
		this.query(iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * 
	 * @param iWherePart
	 *            ��ѯ����(���������־�)
	 * @param iLimitCount
	 *            ��¼������(iLimitCount=0: ������)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart, int iLimitCount) throws UserException,
			Exception {
		String strSqlStatement = "";
		DBTBCust dbTBCust = new DBTBCust();
		if (iLimitCount > 0 && dbTBCust.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLTBCust.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM TB_CUST WHERE " + iWherePart;
			schemas = dbTBCust.findByConditions(strSqlStatement);
		}
	}

	/**
	 * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
	 * 
	 * @param dbpool
	 *            ȫ�ֳ�
	 * @param iWherePart
	 *            ��ѯ����(���������־�)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart) throws UserException,
			Exception {
		this.query(dbpool, iWherePart, Integer.parseInt(SysConst.getProperty(
				"QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * 
	 * @param dbpool
	 *            ȫ�ֳ�
	 * @param iWherePart
	 *            ��ѯ����(���������־�)
	 * @param iLimitCount
	 *            ��¼������(iLimitCount=0: ������)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, int iLimitCount)
			throws UserException, Exception {
		String strSqlStatement = "";
		DBTBCust dbTBCust = new DBTBCust();
		if (iLimitCount > 0 && dbTBCust.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLTBCust.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM TB_CUST WHERE " + iWherePart;
			schemas = dbTBCust.findByConditions(dbpool, strSqlStatement);
		}
	}

	/**
	 * ��dbpool��save����
	 * 
	 * @param ��
	 * @return ��
	 */
	public void save(DbPool dbpool) throws Exception {
		DBTBCust dbTBCust = new DBTBCust();

		int i = 0;

		for (i = 0; i < schemas.size(); i++) {
			dbTBCust.setSchema((TBCustSchema) schemas.get(i));
			dbTBCust.insert(dbpool);
		}
	}

	/**
	 * ����dbpool��XXXXX�淽��
	 * 
	 * @param ��
	 * @return ��
	 */
	public void save() throws Exception {
		DbPool dbpool = new DbPool();

		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			dbpool.beginTransaction();
			save(dbpool);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}

	/**
	 * ��dbpool��ɾ������
	 * 
	 * @param dbpool
	 *            ���ӳ�
	 * @param iCUSTID
	 *            CUSTID
	 * @return ��
	 */
	public void cancel(DbPool dbpool, String iCUSTID) throws Exception {





		String strSqlStatement = " DELETE FROM TB_CUST WHERE CUSTID= ?";
    	dbpool.prepareInnerStatement(strSqlStatement);
    	dbpool.setString(1, iCUSTID);
	dbpool.executePreparedUpdate();
	dbpool.closePreparedStatement();
     
	}

	/**
	 * ����dbpool��ɾ������
	 * 
	 * @param iCUSTID
	 *            CUSTID
	 * @return ��
	 */
	public void cancel(String iCUSTID) throws Exception {
		DbPool dbpool = new DbPool();

		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			dbpool.beginTransaction();
			cancel(dbpool, iCUSTID);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}

	/**
	 * ��dbpool����CUSTID��ȡ����
	 * 
	 * @param iCUSTID
	 *            CUSTID
	 * @return ��
	 */
	public void getData(String iCUSTID) throws Exception {
		DbPool dbpool = new DbPool();
		dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
		getData(dbpool, iCUSTID);
		dbpool.close();
	}

	/**
	 * ����dbpool����CUSTID��ȡ����
	 * 
	 * @param dbpool
	 *            ���ӳ�
	 * @param iCUSTID
	 *            CUSTID
	 * @return ��
	 */
	public void getData(DbPool dbpool, String iCUSTID) throws Exception {
        
        
        
        
        String strWherePart = " CUSTID= ? ";
        ArrayList arrWhereValue = new ArrayList();
        arrWhereValue.add(iCUSTID);
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
        DBTBCust dbTBCust = new DBTBCust();
        if (iLimitCount > 0 && dbTBCust.getCount(dbpool,iWherePart,iWhereValue) > iLimitCount)
        {
            throw new UserException(-98,-1003,"BLTBCust.query");
        }else
        {
            initArr();
            strSqlStatement = " SELECT * FROM TB_CUST WHERE " + iWherePart;
            schemas = dbTBCust.findByConditions(dbpool,strSqlStatement,iWhereValue);
        }
    }
	
	/**
	 * ������
	 * 
	 * @param args
	 *            �����б�
	 */
	public static void main(String[] args) {
		
	}
	/**
	 * ��dbpool��update���������ݹ���ҵ��������tb_cust��д�ֶε���
	 * 
	 * @param ��
	 * @return ��
	 * @author duguangrun 2015330
	 */
	public void writeBacknsuredcodeTB_Cust(String insuredCode,String custId,DbPool dbpool) throws Exception {

			String updateSQL = "update tb_cust set insuredcode = ? where custId = ? ";
	    	dbpool.prepareInnerStatement(updateSQL);
	    	dbpool.setString(1, insuredCode);
	    	dbpool.setString(2, custId);
	    	dbpool.executePreparedUpdate();
	    	dbpool.closePreparedStatement();
	}
	/**
	 * 
	 * XXXXX������list�����ݹ���ҵ��������ӻ��滻��ʱ���á�
	 * @param p_CUSTSchemaList
	 * @author duguangrun  20150409
	 * @throws Exception 
	 */
	public void saveAll(ArrayList<PCustSchema> p_CUSTSchemaList) throws Exception {
		DbPool dbpool = new DbPool();

		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			dbpool.beginTransaction();
		for (PCustSchema p_CUSTSchema : p_CUSTSchemaList) {
			DBTBCust dbTBCust = new DBTBCust();
			dbTBCust.setCustID(p_CUSTSchema.getCUSTID());						
			dbTBCust.setCustName(p_CUSTSchema.getCUSTNAME());					
			dbTBCust.setBirthDay(p_CUSTSchema.getBIRTHDAY());					
			dbTBCust.setSex(p_CUSTSchema.getSEX());								
			dbTBCust.setAge(p_CUSTSchema.getAGE());								
			dbTBCust.setIDNo(p_CUSTSchema.getIDNO());							
			dbTBCust.setIDType(p_CUSTSchema.getIDTYPE());						
			dbTBCust.setPhone(p_CUSTSchema.getPHONE());							
			dbTBCust.setBankAccNo(p_CUSTSchema.getBANKACCNO());					
			dbTBCust.setAccName(p_CUSTSchema.getACCNAME());						
			dbTBCust.setOccupationType(p_CUSTSchema.getOCCUPATIONTYPE());		
			dbTBCust.setWorkType(p_CUSTSchema.getWORKTYPE());					
			dbTBCust.setBenefScale(p_CUSTSchema.getBENEFSCALE());				
			dbTBCust.setCustType(p_CUSTSchema.getCUSTTYPE());					
			dbTBCust.setPolicyNo(p_CUSTSchema.getPOLICYNO());					
			dbTBCust.setInsuredID(p_CUSTSchema.getINSUREDID());					
			dbTBCust.setModifyDate(p_CUSTSchema.getUPDATEDATE());				
			dbTBCust.setRelateItemKindNo(p_CUSTSchema.getFAMILYNO());			
				dbTBCust.setInsuredCode(p_CUSTSchema.getINSUREDCODE());				
				dbTBCust.setFlag("1");												
				dbTBCust.insert(dbpool);
			}
			dbpool.commitTransaction();
		}catch (Exception e) {
			e.printStackTrace();
			dbpool.rollbackTransaction();
		}finally{
			dbpool.close();
		}
	}
	/**
	 * �޸�flag״ֵ̬�������ж���ɾ��״̬
	 * @param dbpoolJT
	 * @param p_custList
	 * @throws Exception 
	 */
	public void updateFalg(DbPool dbpoolJT, ArrayList<PCustSchema> p_CUSTSchemaList) throws Exception {
		String flag = "0";
		String updateSQL = "UPDATE tb_cust SET flag = ? WHERE custId = ? ";
		String custId = "";
		if (p_CUSTSchemaList!=null) {
			for (PCustSchema p_CUSTSchema : p_CUSTSchemaList) {
				if("105".equals(p_CUSTSchema.getENDORTYPE())){
					custId = p_CUSTSchema.getINSUREDID();
				}else{
					custId = p_CUSTSchema.getCUSTID();
				}
					dbpoolJT.prepareInnerStatement(updateSQL);
					dbpoolJT.setString(1, flag);
					dbpoolJT.setString(2, custId);
			    	dbpoolJT.executePreparedUpdate();
			}
		}
	}	
}
