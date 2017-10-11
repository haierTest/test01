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
	 * 初始化记录
	 * 
	 * @param 无
	 * @return 无
	 * @throws Exception
	 */
	public void initArr() throws Exception {
		schemas = new Vector();
	}

	/**
	 * 增加一条WfImageDealSchema记录
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
	 * 得到一条WfImageDealSchema记录
	 * 
	 * @param index
	 *            下标
	 * @return 一个WfImageDealSchema对象
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
	 * 删除一条WfImageDealSchema记录
	 * 
	 * @param index
	 *            下标
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
	 * 得到schemas记录数
	 * 
	 * @return schemas记录数
	 * @throws Exception
	 */
	public int getSize() throws Exception {
		return this.schemas.size();
	}

	/**
	 * 带dbpool的save方法
	 * 
	 * @param 无
	 * @return 无
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
     *带dbpool的删除方法
     *@param dbpool    连接池
     *@param iFlowID FlowID
     *@return 无
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
     * 不带dbpool的删除方法
     *@param iFlowID FlowID
     *@return 无
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
     *不带DbPoll的删除方法
     *@param dbpool 连接池
     *@param iBusinessNo 业务号
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
