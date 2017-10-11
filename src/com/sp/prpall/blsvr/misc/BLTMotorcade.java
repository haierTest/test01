/****************************************************************************
 * DESC       ：定义TMotorcade的基本逻辑对象类
 * Author     : X
 * CREATEDATE ：2003-02-13
 * MODIFYLIST ：Name       Date            Reason/Contents
 *          ------------------------------------------------------
 *
 ****************************************************************************/


package com.sp.prpall.blsvr.misc;


import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import com.sp.utility.error.UserException;
import com.sp.utility.string.Str;
import com.sp.utility.*;
import com.sp.smc.wtc.Service;
import com.sp.smc.wtc.ServiceHome;
import com.sp.prpall.schema.TMotorcadeSchema;
import com.sp.utility.*;


/**
 * 定义TMotorcade表的基本逻辑对象类
 */
public class BLTMotorcade
{


  /**
   * 一组TMotorcadeSchema记录
   */
  private Vector schemas = new Vector();


  /**
   *构造函数
   */
  public BLTMotorcade()
  {
  }


  /**
   *STUB-NONE：初始化记录
   *@param  无
   *@return 无
   *@throws Exception
   */
  public void initArr() throws Exception
  {
    schemas = new Vector();
  }


  /**
   *STUB-NONE：加一条TMotorcadeSchema记录
   *@param iTMotorcadeSchema TMotorcadeSchema对象
   *@return 无
   *@throws Exception
   */
  public void setArr(TMotorcadeSchema iTMotorcadeSchema) throws Exception
  {
    schemas.add(iTMotorcadeSchema);
  }


  /**
   *STUB-NONE：得到一条TMotorcadeSchema记录
   *@param  index 下标
   *@return 一个TMotorcadeSchema对象
   *@throws Exception
   */
  public TMotorcadeSchema getArr(int index) throws Exception
  {
    return (TMotorcadeSchema)this.schemas.get(index);
  }


  /**
   *STUB-NONE：删除一条TMotorcadeSchema记录
   *@param  index下标
   *@return 无
   *@throws Exception
   */
  public void remove(int index) throws Exception
  {
    schemas.remove(index);
  }


  /**
   *STUB-NONE：得到schemas记录数
   *@param  无
   *@return schemas记录数
   *@throws Exception
   */
  public int getSize() throws Exception
  {
    return this.schemas.size();
  }


  /**
   *STUB-NONE：将全部记录打包
   *@return 记录串
   *@throws UserException
   *@throws Exception
   */
  public String encode() throws UserException,Exception
  {
    String            strReturn   = "";
    Enumeration       enuElement  = schemas.elements();
    TMotorcadeSchema tMotorcadeSchema;

    strReturn = this.getSize() + SysConfig.getProperty("PACKAGE_DATA_DELIMITER");

    while(enuElement.hasMoreElements())
    {
      tMotorcadeSchema = (TMotorcadeSchema)enuElement.nextElement();
      strReturn         = strReturn  + tMotorcadeSchema.encode();
    }
    return strReturn;
  }


  /**
   *STUB-NONE：将记录串解包成一组对象
   *@param iStrMessage 记录串
   *@throws UserException
   *@throws Exception
   */
  public void decode(String iStrMessage) throws UserException,Exception
  {
    int    intCounter                   = 0 ;  
    int    intIndex                     = 0;   
    String strRecord                    = "";  
    TMotorcadeSchema tMotorcadeSchema = null;

    this.initArr();

    
    intCounter = Integer.parseInt(iStrMessage.substring(0,iStrMessage.indexOf(SysConfig.getProperty("PACKAGE_DATA_DELIMITER"))).trim());

    
	   iStrMessage = iStrMessage.substring(iStrMessage.indexOf(SysConfig.getProperty("PACKAGE_DATA_DELIMITER")) + 1);

	   
	   for (int  i = 0; i < intCounter; i++)
	   {
	    tMotorcadeSchema = new TMotorcadeSchema();
	    intIndex  = Str.getPos(iStrMessage,SysConfig.getProperty("PACKAGE_DATA_DELIMITER"),TMotorcadeSchema.FIELDNUM);
	    strRecord = iStrMessage.substring(0,intIndex + 1);

      tMotorcadeSchema.decode(strRecord);

      this.setArr(tMotorcadeSchema);
      iStrMessage = iStrMessage.substring(intIndex + 1);

    }
  }

}
