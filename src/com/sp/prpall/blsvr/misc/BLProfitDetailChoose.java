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
 * 定义PrpMotorcade的BL类
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>@createdate 2003-07-17</p>
 * @Author     : X
 * @version 1.0
 */
public class BLProfitDetailChoose{
    private Vector schemas = new Vector();
    /**
     * 构造函数
     */
    public BLProfitDetailChoose(){
    }

    /**
     *初始化记录
     *@param 无
     *@return 无
     *@throws Exception
     */
    public void initArr() throws Exception
    {
       schemas = new Vector();
     }
    /**
     *增加一条ProfitDetailChooseSchema记录
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
     *得到一条ProfitDetailChoose记录
     *@param index 下标
     *@return 一个ProfitDetailChoose对象
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
     *删除一条PrpMotorcadeSchema记录
     *@param index 下标
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
     *得到schemas记录数
     *@return schemas记录数
     *@throws Exception
     */
    public int getSize() throws Exception
    {
        return this.schemas.size();
    }
     /**
     * 主函数
     * @param args 参数列表
     */
    public static void main(String[] args){
        
    }
}
