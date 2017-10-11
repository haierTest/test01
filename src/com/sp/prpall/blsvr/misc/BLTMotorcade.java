/****************************************************************************
 * DESC       ������TMotorcade�Ļ����߼�������
 * Author     : X
 * CREATEDATE ��2003-02-13
 * MODIFYLIST ��Name       Date            Reason/Contents
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
 * ����TMotorcade��Ļ����߼�������
 */
public class BLTMotorcade
{


  /**
   * һ��TMotorcadeSchema��¼
   */
  private Vector schemas = new Vector();


  /**
   *���캯��
   */
  public BLTMotorcade()
  {
  }


  /**
   *STUB-NONE����ʼ����¼
   *@param  ��
   *@return ��
   *@throws Exception
   */
  public void initArr() throws Exception
  {
    schemas = new Vector();
  }


  /**
   *STUB-NONE����һ��TMotorcadeSchema��¼
   *@param iTMotorcadeSchema TMotorcadeSchema����
   *@return ��
   *@throws Exception
   */
  public void setArr(TMotorcadeSchema iTMotorcadeSchema) throws Exception
  {
    schemas.add(iTMotorcadeSchema);
  }


  /**
   *STUB-NONE���õ�һ��TMotorcadeSchema��¼
   *@param  index �±�
   *@return һ��TMotorcadeSchema����
   *@throws Exception
   */
  public TMotorcadeSchema getArr(int index) throws Exception
  {
    return (TMotorcadeSchema)this.schemas.get(index);
  }


  /**
   *STUB-NONE��ɾ��һ��TMotorcadeSchema��¼
   *@param  index�±�
   *@return ��
   *@throws Exception
   */
  public void remove(int index) throws Exception
  {
    schemas.remove(index);
  }


  /**
   *STUB-NONE���õ�schemas��¼��
   *@param  ��
   *@return schemas��¼��
   *@throws Exception
   */
  public int getSize() throws Exception
  {
    return this.schemas.size();
  }


  /**
   *STUB-NONE����ȫ����¼���
   *@return ��¼��
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
   *STUB-NONE������¼�������һ�����
   *@param iStrMessage ��¼��
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
