package com.sp.prpall.blsvr.jf;

import java.util.Vector;
import com.sp.prpall.dbsvr.jf.DBPrpJQuotaInvoice;
import com.sp.prpall.schema.PrpJQuotaInvoiceSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

public class BLPrpJQuotaInvoice {

    private Vector schemas = new Vector();
    
    public BLPrpJQuotaInvoice() { }
    public void initArr() throws Exception
    {
      schemas = new Vector();
     
    }
    
    
    
    public void setArr(PrpJQuotaInvoiceSchema iPrpJQuotaInvoiceSchema) throws Exception
    {
      try
      {
        schemas.add(iPrpJQuotaInvoiceSchema);
      }
      catch(Exception e)
      {
        throw e;
      }
    }
    
    public int getSize() throws Exception
    {
      return this.schemas.size();
    }
    
    public PrpJQuotaInvoiceSchema getArr(int index) throws Exception
    {
        PrpJQuotaInvoiceSchema prpJQuotaInvoiceSchema = null;
      try
      {
          prpJQuotaInvoiceSchema = (PrpJQuotaInvoiceSchema)this.schemas.get(index);
      }
      catch(Exception e)
      {
        throw e;
      }
      return prpJQuotaInvoiceSchema;
    }

    
    public void save(DbPool dbpool) throws Exception
    {
        DBPrpJQuotaInvoice dbPrpJQuotaInvoice = new DBPrpJQuotaInvoice();
      int i = 0;
      for(i = 0; i< schemas.size(); i++)
      {
          dbPrpJQuotaInvoice.setSchema((PrpJQuotaInvoiceSchema)schemas.get(i));
          dbPrpJQuotaInvoice.insert(dbpool);
      }
    }

    /**
     *不带dbpool的XXXXX存方法
     *@param 无
     *@return 无
     */
    public void save() throws Exception
    {
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        dbpool.beginTransaction();
        save(dbpool);
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
    
    public void query(String iWherePart,int iLimitCount) throws UserException,Exception
    {
      String strSqlStatement = "";
      DBPrpJQuotaInvoice dbPrpJQuotaInvoice = new DBPrpJQuotaInvoice();
      if (iLimitCount > 0 && dbPrpJQuotaInvoice.getCount(iWherePart) > iLimitCount)
      {
        throw new UserException(-98,-1003,"BLPrpJplanFee.query");
      }else
      {
        initArr();
        strSqlStatement = " SELECT * FROM prpjquotainvoice WHERE " + iWherePart;
        schemas = dbPrpJQuotaInvoice.findByConditions(strSqlStatement);
      }
    }

}
