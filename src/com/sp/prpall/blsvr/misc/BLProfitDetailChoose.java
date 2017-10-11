package com.sp.prpall.blsvr.misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import java.util.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.prpall.schema.ProfitDetailChooseSchema;

/**
 * ����PrpMotorcade��BL��
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-07-17</p>
 * @Author     : X
 * @version 1.0
 */
public class BLProfitDetailChoose{
    private Vector schemas = new Vector();
    /**
     * ���캯��
     */
    public BLProfitDetailChoose(){
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
     *����һ��ProfitDetailChooseSchema��¼
     *@param iProfitDetailChooseSchema ProfitDetailChooseSchema
     *@throws Exception
     */
    public void setArr(ProfitDetailChooseSchema iProfitDetailChooseSchema) throws Exception
    {
       try
       {
         schemas.add(iProfitDetailChooseSchema);
       }
       catch(Exception e)
       {
         throw e;
       }
     }
    /**
     *�õ�һ��ProfitDetailChoose��¼
     *@param index �±�
     *@return һ��ProfitDetailChoose����
     *@throws Exception
     */
    public ProfitDetailChooseSchema getArr(int index) throws Exception
    {
     ProfitDetailChooseSchema profitDetailChooseSchema = null;
       try
       {
        profitDetailChooseSchema = (ProfitDetailChooseSchema)this.schemas.get(index);
       }
       catch(Exception e)
       {
         throw e;
       }
       return profitDetailChooseSchema;
     }
    /**
     *ɾ��һ��PrpMotorcadeSchema��¼
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
     * ������
     * @param args �����б�
     */
    public static void main(String[] args){
        
    }
}
