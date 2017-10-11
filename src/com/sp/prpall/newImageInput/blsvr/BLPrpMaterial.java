package com.sp.prpall.newImageInput.blsvr;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.prpall.newImageInput.dbsvr.DBPrpNewMaterial;
import com.sp.prpall.newImageInput.schema.PrpNewMaterialSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;

public class BLPrpMaterial {
	private final Log logger = LogFactory.getLog(getClass());
	private Vector schemas = new Vector();

	public BLPrpMaterial() {
		super();
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
	 * ����һ��WfImageDealSchema��¼
	 * 
	 * @param iWfImageDealSchema
	 *            WfImageDealSchema
	 * @throws Exception
	 */
	public void setArr(PrpNewMaterialSchema prpNewMaterialSchema)
			throws Exception {
		try {
			schemas.add(prpNewMaterialSchema);
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void clearArr() throws Exception{
		try {
			schemas.clear();
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �õ�һ��WfImageDealSchema��¼
	 * 
	 * @param index
	 *            �±�
	 * @return һ��WfImageDealSchema����
	 * @throws Exception
	 */
	public PrpNewMaterialSchema getArr(int index) throws Exception {
		PrpNewMaterialSchema prpNewMaterialSchema = null;
		try {
			prpNewMaterialSchema = (PrpNewMaterialSchema) this.schemas
					.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpNewMaterialSchema;
	}

	/**
	 * ɾ��һ��WfImageDealSchema��¼
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
	 * ��dbpool��save����
	 * 
	 * @param ��
	 * @return ��
	 */
	public void save(DbPool dbpool) throws Exception {
		DBPrpNewMaterial dbPrpNewMaterial = new DBPrpNewMaterial();

		int i = 0;

		for (i = 0; i < schemas.size(); i++) {
			dbPrpNewMaterial.setSchema((PrpNewMaterialSchema) schemas.get(i));
			dbPrpNewMaterial.insert(dbpool);
		}
	}
	
    
    /**
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param iFlowID FlowID
     *@return ��
     */
    public void cancel(DbPool dbpool,String BusinessNo) throws Exception
    {
      String strSqlStatement = "";
      
      strSqlStatement = " DELETE FROM PrpMaterial WHERE BusinessNo=?";
      dbpool.prepareInnerStatement(strSqlStatement);
      dbpool.setString(1,BusinessNo);
      dbpool.executePreparedUpdate();
      dbpool.closePreparedStatement();
    }
      
    /**
     * ����dbpool��ɾ������
     *@param iFlowID FlowID
     *@return ��
     */
    public void cancel(String barCode ) throws Exception
    {
      DbPool dbpool = new DbPool();
      
      try
      {
        dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
        dbpool.beginTransaction();
        cancel(dbpool,barCode);
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
    
    public void deletePrpMaterialBySyaFileName(DbPool dbpool,String businessNo,String syaFileName) throws Exception{
    	DBPrpNewMaterial dbPrpNewMaterial=new DBPrpNewMaterial();
    	dbPrpNewMaterial.delete(dbpool, businessNo, syaFileName);
    }
    /**
     *����DbPoll��ɾ������
     *@param dbpool ���ӳ�
     *@param iBusinessNo ҵ���
     *@throws Exception 
     */
    public void deletePrpMaterialBySyaFileName(String businessNo,String syaFileName) throws Exception{
    	DbPool dbpool = new DbPool();
        try {
          dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
          dbpool.beginTransaction();
          this.deletePrpMaterialBySyaFileName(dbpool,businessNo,syaFileName);
          dbpool.commitTransaction();
        }
        catch (Exception e)
        {
          throw e;
        }
        finally {
          dbpool.close();
        }
    	
    }
    
    public int getMaxSerialNo(String businessNo){
    	DBPrpNewMaterial dbPrpNewMaterial = new DBPrpNewMaterial();
    	int max=0;
    	try {
			max=dbPrpNewMaterial.getMaxNo(businessNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return max;
    }
      
}
