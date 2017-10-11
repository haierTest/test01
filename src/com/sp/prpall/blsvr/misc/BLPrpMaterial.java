package com.sp.prpall.blsvr.misc;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.prpall.dbsvr.misc.DBPrpMaterial;
import com.sp.prpall.schema.PrpMaterialSchema;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ����PrpMaterial��BL��
 * ��pdm��ȡ���ݿ���Ϣ,�������ݿ�����ɶ�Ӧ��BL��
 * <p>Title: SinosoftJavaTools v1.4</p>
 * <p>Description: �п���JavaԴ�����ɹ���</p>
 * <p>Company: Sinosoft</p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>@createdate 2010-11-24</p>
 * @FirstAuthor: Zhouxianli(JavaTools v1.0)
 * @SecondAuthor: Yangkun(JavaTools v1.1->v1.2->v1.3->v1.4)
 * @LastVersion: v1.4.3
 * @UpdateList: 1.������CIXXXXXƽ̨������ɴ���;
 *              2.���������������cancel()��getdata()����ʱ��ȡpdm���������Ϊnull������;
 *              3.��������������ಿ��import��Ϊnull������;
 *              4.����log4j��־bl�����Զ���ʼ��;
 *              5.getData��������try��catch��finally�쳣����;
 */
public class BLPrpMaterial{
    private final Log logger = LogFactory.getLog(getClass());
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */       
    public BLPrpMaterial(){
    }

    /**
     *��ʼ����¼
     *@param ��
     *@return ��
     *@throws Exception
     */
    public void initArr() throws Exception {
		schemas = new Vector();
	}
    /**
	 * ����һ��PrpMaterialSchema��¼
	 * 
	 * @param iPrpMaterialSchema
	 *            PrpMaterialSchema
	 * @throws Exception
	 */
    public void setArr(PrpMaterialSchema iPrpMaterialSchema) throws Exception {
		try {
			schemas.add(iPrpMaterialSchema);
		} catch (Exception e) {
			throw e;
		}
	}
    /**
	 * �õ�һ��PrpMaterialSchema��¼
	 * 
	 * @param index
	 *            �±�
	 * @return һ��PrpMaterialSchema����
	 * @throws Exception
	 */
    public PrpMaterialSchema getArr(int index) throws Exception {
		PrpMaterialSchema prpMaterialSchema = null;
		try {
			prpMaterialSchema = (PrpMaterialSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpMaterialSchema;
	}
    /**
	 * ɾ��һ��PrpMaterialSchema��¼
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
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(String iWherePart, int iLimitCount) throws UserException,
			Exception {
		String strSqlStatement = "";
		DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();
		if (iLimitCount > 0 && dbPrpMaterial.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpMaterial.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpMaterial WHERE " + iWherePart;
			schemas = dbPrpMaterial.findByConditions(strSqlStatement);
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
     *���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
     *@param dbpool ȫ�ֳ�
     *@param iWherePart ��ѯ����(���������־�)
     *@param iLimitCount ��¼������(iLimitCount=0: ������)
     *@throws UserException
     *@throws Exception
     */
    public void query(DbPool dbpool, String iWherePart, int iLimitCount)
			throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();
		if (iLimitCount > 0 && dbPrpMaterial.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpMaterial.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpMaterial WHERE " + iWherePart;
			schemas = dbPrpMaterial.findByConditions(dbpool, strSqlStatement);
		}
	}
    /**
     * ���ղ�ѯ�����ͼ�¼����������ѯ�����õ�һ���¼�������������¼����schemas����
     * @param strSQL        sql���
     * @param rowNumCount   ��ѯ����
     * @param iLimitCount   ��¼������(iLimitCount=0: ������)
     * @throws UserException
     * @throws Exception
     * @author Administrator fanjiangtao 2013-12-19
     */
    public void query(String strSQL,int rowNumCount,int iLimitCount)throws UserException, Exception{
        String strSqlStatement = "";
        if(iLimitCount > 0 && rowNumCount > iLimitCount){
            throw new UserException(-98, -1003, "BLPrpMaterial.query");
        }else{
            initArr();
            strSqlStatement = strSQL +rowNumCount;
            DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();
            schemas = dbPrpMaterial.findByConditions(strSqlStatement);
        }
    }
    /**
     * ���ղ�ѯ�����ͼ�¼����������ѯ�����õ�һ���¼�������������¼����schemas����
     * @param dbpool            ȫ�ֳ� 
     * @param strSQL            sql���
     * @param rowNumCount       ��ѯ����
     * @param iLimitCount       ��¼������(iLimitCount=0: ������)
     * @throws UserException
     * @throws Exception
     * @author Administrator    fanjingtao 20131219
     */
    public void query(DbPool dbpool,String strSQL,int rowNumCount,int iLimitCount)throws UserException, Exception{
        String strSqlStatement = "";
        if(iLimitCount > 0 && rowNumCount > iLimitCount){
            throw new UserException(-98, -1003, "BLPrpMaterial.query");
        }else{
            initArr();
            strSqlStatement = strSQL +rowNumCount;
            DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();
            schemas = dbPrpMaterial.findByConditions(dbpool,strSqlStatement);
        }
    }
      
    /**
	 * ��dbpool��save����
	 * 
	 * @param ��
	 * @return ��
	 */
    public void save(DbPool dbpool) throws Exception {
		DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();

		int i = 0;

		for (i = 0; i < schemas.size(); i++) {
			dbPrpMaterial.setSchema((PrpMaterialSchema) schemas.get(i));
			dbPrpMaterial.insert(dbpool);
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
	 * @param iBusinessNo
	 *            BusinessNo
	 * @return ��
	 */
    public void cancel(DbPool dbpool, String iBusinessNo) throws Exception {
		String strSqlStatement = "";

		strSqlStatement = " DELETE FROM PrpMaterial WHERE BusinessNo= '"
				+ iBusinessNo + "'";

		dbpool.delete(strSqlStatement);
	}
      
    /**
	 * ����dbpool��ɾ������
	 * 
	 * @param iBusinessNo
	 *            BusinessNo
	 * @return ��
	 */
    public void cancel(String iBusinessNo) throws Exception {
		DbPool dbpool = new DbPool();

		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			dbpool.beginTransaction();
			cancel(dbpool, iBusinessNo);
			dbpool.commitTransaction();
		} catch (Exception e) {
			dbpool.rollbackTransaction();
		} finally {
			dbpool.close();
		}
	}
      
    /**
	 * ��dbpool����BusinessNo��ȡ����
	 * 
	 * @param iBusinessNo
	 *            BusinessNo
	 * @return ��
	 */
    public void getData(String iBusinessNo) throws Exception {
		DbPool dbpool = new DbPool();
		try {
			dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			getData(dbpool, iBusinessNo);
		} catch (Exception e) {
			
		} finally {
			dbpool.close();
		}
	}
      
    /**
	 * ����dbpool����BusinessNo��ȡ����
	 * 
	 * @param dbpool
	 *            ���ӳ�
	 * @param iBusinessNo
	 *            BusinessNo
	 * @return ��
	 */
    public void getData(DbPool dbpool, String iBusinessNo) throws Exception {
		String strWherePart = "";
		strWherePart = "BusinessNo= '" + iBusinessNo + "'";
		query(dbpool, strWherePart, 0);
	}
    /**
     * �����ѯ ����dbpool
     * @author wangchuanzhong 20101125
     * @param iWherePart ��ѯ����
     * @param intPageNum ҳ��
     * @param intLineNumPage ÿҳ����
     * @return ��
     * @throws Exception
     */
    public void queryWithoutOrder(String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
    {
      DbPool dbpool = new DbPool();
      try {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        this.queryWithoutOrder(dbpool,iWherePart,intPageNum,intLineNumPage);
      }catch (Exception e)
      {
        throw e;
      }finally {
        dbpool.close();
      }
    }
    /**
     * �����ѯ ��dbpool
     * @author wangchuanzhong 20101125
     * @param dbpool
     * @param iWherePart ��ѯ����
     * @param intPageNum ҳ��
     * @param intLineNumPage ÿҳ����
     * @return ��
     * @throws Exception
     */
     public void queryWithoutOrder(DbPool dbpool,String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
     {
       StringBuffer strSqlStatement = new StringBuffer();
       int intStartNum = 0;
       int intEndNum = 0;

       intStartNum = (intPageNum - 1) * intLineNumPage;
       intEndNum = intPageNum * intLineNumPage;

       DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();
       initArr();
       
       strSqlStatement.append(" SELECT * FROM ( ");
       strSqlStatement.append("Select RowNum As LineNum,M.* From ( ");
       strSqlStatement.append("Select * From PrpMaterial PM Where ");
       strSqlStatement.append(iWherePart);
       strSqlStatement.append(" And PM.SerialNo =(Select MAX(Serialno) From PrpMaterial inPM Where inPM.BusinessNo = PM.BusinessNo)");
       strSqlStatement.append(") M Where RowNum<=");
       strSqlStatement.append(intEndNum);
       strSqlStatement.append(") Where LineNum>");
       strSqlStatement.append(intStartNum);
       
       schemas = dbPrpMaterial.findByConditions(dbpool,strSqlStatement.toString());
     }
     
     
     /**
      * �����ѯ ��dbpool
      * @author ronglijun 20140617
      * @param dbpool
      * @param iWherePart ��ѯ����
      * @param intPageNum ҳ��
      * @param intLineNumPage ÿҳ����
      * @return ��
      * @throws Exception
      */
      public void queryByOrderNew(DbPool dbpool,String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
      {
        StringBuffer strSqlStatement = new StringBuffer();
        int intStartNum = 0;
        int intEndNum = 0;
        intStartNum = (intPageNum - 1) * intLineNumPage;
        intEndNum = intPageNum * intLineNumPage;
        DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();
        initArr();
        strSqlStatement.append(" SELECT x.* from( ");
        strSqlStatement.append("SELECT z.*,rownum numbers from( ");
        strSqlStatement.append("select * from PrpMaterial where ");
        strSqlStatement.append(iWherePart);
        strSqlStatement.append(" order by Serialno ) z  ");
        strSqlStatement.append("  Where RowNum<=");
        strSqlStatement.append(intEndNum);
        strSqlStatement.append(") x Where x.numbers>");
        strSqlStatement.append(intStartNum);
        schemas = dbPrpMaterial.findByConditions(dbpool,strSqlStatement.toString());
      }

     
 
     /**
      * y���ѯ ��dbpool
      * @author ronglijun 20101125
      * @param dbpool
      * @param iWherePart ��ѯ����
      * @param intPageNum ҳ��
      * @param intLineNumPage ÿҳ����
      * @return ��
      * @throws Exception
      */
      public void queryByOrder(String iWherePart,int intPageNum,int intLineNumPage) throws UserException,Exception
      {  
          DbPool dbpool = new DbPool();
          try {
            dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
            this.queryByOrderNew(dbpool,iWherePart,intPageNum,intLineNumPage);
          }catch (Exception e)
          {
            throw e;
          }finally {
            dbpool.close();
          }
      }
      
     
     /**
      * ����dbpool�ĸ��·���
      *@return ��
      */
     public void update() throws Exception
     {
       DbPool dbpool = new DbPool();

         try {
          dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
         dbpool.beginTransaction();
         update(dbpool);
         dbpool.commitTransaction();
       }
       catch (Exception e)
       {
         dbpool.rollbackTransaction();
       }
       finally {
         dbpool.close();
       }
     }
       /**
       *��dbpool�ĸ��·���
       *@param dbpool    ���ӳ�
       *@return ��
       */
      public void update(DbPool dbpool) throws Exception
      {
    	  DBPrpMaterial dbPrpMaterial = new DBPrpMaterial();

        int i = 0;

        for(i = 0; i< schemas.size(); i++)
        {
        	dbPrpMaterial.setSchema((PrpMaterialSchema)schemas.get(i));
          dbPrpMaterial.update(dbpool);
         }
      }
    
     
    /**
	 * ������
	 * 
	 * @param args
	 *            �����б�
	 */
    public static void main(String[] args){
        
    }
}
