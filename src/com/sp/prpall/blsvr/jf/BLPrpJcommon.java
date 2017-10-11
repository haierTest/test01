package com.sp.prpall.blsvr.jf;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.*;
import com.sp.prpall.pubfun.PubTools;
import com.sp.account.blsvr.BLAccBookCurrency;

/**
 * ����PrpJcommon��BL��
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2006-07-28</p>
 * @Author     : lijibin
 * @version 1.0
 */
public class BLPrpJcommon{
    private Vector schemas = new Vector();
    private BLAccBookCurrency blAccBookCurrency = new BLAccBookCurrency();
    /**
     * ���캯��
     */
    public BLPrpJcommon(){
    }

    public BLAccBookCurrency getBLAccBookCurrency(){
        return this.blAccBookCurrency;
    }

    /**
     *��dbpool��ɾ������
     *@param dbpool    ���ӳ�
     *@param null null
     *@return ��
     */
    public void getAccCurrExch(DbPool dbpool,String iComCode,String iBaseCurrency,String iExchDate) throws Exception
    {
      
      
      String wherepart = " CenterCode= '00000000' AND BranchCode='00000000' AND AccBookType= '02' ";
      wherepart += " AND AccBookCode in ('11','12')";
      blAccBookCurrency.query(dbpool,wherepart);
      
      double dblExchRate = 1;
      for(int i=0; i<blAccBookCurrency.getSize(); i++)
      {
          dblExchRate = PubTools.getExchangeRate(dbpool, iBaseCurrency,
              blAccBookCurrency.getArr(i).getCurrencyCode(), iExchDate);
          dblExchRate = Str.round(dblExchRate,4);
          blAccBookCurrency.getArr(i).setFlag(""+dblExchRate);
      }
    }

    /**
     * ����dbpool��ɾ������
     *@param null null
     *@return ��
     */
    public void getAccCurrExch(String iComCode, String iBaseCurrency, String iExchDate) throws Exception
    {
      DbPool dbpool = new DbPool();
        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
        getAccCurrExch(dbpool,iComCode,iBaseCurrency,iExchDate);
      }
      catch (Exception e)
      {
        throw e;
      }
      finally {
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
