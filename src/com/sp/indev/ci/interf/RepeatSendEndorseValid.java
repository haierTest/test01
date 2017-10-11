package com.sp.indiv.ci.interf;

import java.io.*;
import java.util.*;

import org.w3c.dom.*;
import com.sp.indiv.ci.blsvr.*;
import com.sp.indiv.ci.dbsvr.*;
import com.sp.indiv.ci.schema.*;
import com.sp.prpall.blsvr.pg.*;
import com.sp.sysframework.common.datatype.*;
import com.sp.sysframework.common.util.*;
import com.sp.utility.*;
import com.sp.utility.database.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * �ط�����ȷ����Ϣ
 *
 * @author �̿�
 */
public class RepeatSendEndorseValid
    extends EndorseValidEncoder {

 Log logger = LogFactory.getLog(getClass());
  /**
   * @throws Exception
   *
   */
  public static void main(String[] args) throws Exception {
    /*	AppConfig.configure("d:/temp/config/appconfig");
     String strUrl = "";
     String strUser = "";
     String strPwd = "";
     DbPool dbpool = new DbPool();
     try { 
      strUrl = "jdbc:oracle:thin:@192.168.60.117:1521:devdb";
      strUser = "sunnytest";
      strPwd = "sunnytest";
      dbpool.openOra(strUrl, strUser, strPwd);
         RepeatSendEndorseValid repeatSendEndorseValid = new RepeatSendEndorseValid();
      repeatSendEndorseValid.repeatSendEndorseValid(dbpool, "", "", "");
     } catch (Exception ex) {
      throw ex;
     } finally {
      dbpool.close();
     }*/
  }

  /**
   * ���·���XXȷ��
   *
   * @param dbPool
   *            ������ͬһ�����null
   * @param columnName
   *            ����������Ϊ��
   * @param certiNo
   *            �еĲ�ѯ������
   * @throws Exception
   */
  public void repeatSendEndorseValid(DbPool dbPool, String columnName,
                                     String certiNo, String conditions) throws
      Exception {
    String strWhere = ""; 
    String request = ""; 
    String contet = ""; 
    String comCode = "";

    int count = 0;
    int successCount = 0;
    int failCount = 0;

    DateTime current = new DateTime(DateTime.current(),
                                    DateTime.YEAR_TO_SECOND); 

    int validType = -1; 
    String remark = ""; 

    strWhere = "SELECT * FROM CIEndorValid WHERE ValidNo = EndorseNo AND ValidStatus = '1'";

    if (certiNo != null && !certiNo.equals("") && columnName != null
        && !columnName.equals("")) {
      strWhere += " AND " + columnName + " = '" + certiNo + "'";
    }

    if (conditions != null && !conditions.equals("")) {
      strWhere += " AND " + conditions;
    }

    BLCIEndorValid blCIEndorValid = getRepeatCollection(dbPool, strWhere);
    CIEndorValidSchema cIEndorValidSchema = null; 

    BLEndorse blEndorse = null;

    if (blCIEndorValid != null) {
      for (int i = 0; i < blCIEndorValid.getSize(); i++) { 
        count += 1; 

        cIEndorValidSchema = blCIEndorValid.getArr(i);

        blEndorse = getBLEndorseDate(dbPool, cIEndorValidSchema
                                     .getEndorseNo());

        comCode = blEndorse.getBLPrpPmain().getArr(0).getComCode();

        request = encode(dbPool, blEndorse);
        contet = EbaoProxy.getInstance().request(request, comCode);
        validType = decode(contet, cIEndorValidSchema);

        if (validType == 1) { 
          successCount += 1; 

          cIEndorValidSchema.setValidTime(current.toString());
          cIEndorValidSchema.setValidStatus("0"); 
        }
        else {
          
          logger.info(contet);
          
          failCount += 1; 
        }

        remark = cIEndorValidSchema.getFlag().split("-")[0];

        if (remark == null || remark.equals("")) { 
          
          remark = "1";
        }
        else {
          remark = "" + (new Integer(remark).intValue() + 1);
        }

        cIEndorValidSchema.setFlag(remark);

        
        updateCIEndorValidByDemandNo(dbPool, cIEndorValidSchema);

        
        logger.info("�ط�����ȷ�����ݸ���CIInsureValid��ɹ�  , ���Ĳ�ѯ�� : [" + cIEndorValidSchema.getDemandNo() + "]");
        
      }
    }
    
    logger.info("===========================");
    logger.info("�ط�����ȷ�������ܹ��� [ " + count + " ] ��");
    logger.info("�ط�����ȷ�����ݳɹ��� [ " + successCount + " ] ��");
    logger.info("�ط�����ȷ������ʧ���� [ " + failCount + " ] ��");
    logger.info("===========================");
    
  }

  /**
   * ��д�����endoce�������ж��Ƿ�Ҫ���п������
   * @param dbPool
   * @param blpolicy
   * @return
   * @throws java.lang.Exception
   */
  private String encode(DbPool dbPool, 
		  				BLEndorse blEndorse,
		  				String strFlag) 
  	throws Exception 
  {
      
      StringBuffer buf = new StringBuffer(4000);
      
      getCIEndorValid(dbPool, blEndorse);
      addXMLHead(buf);
      addPacket(dbPool,buf, blEndorse);
      return buf.toString();
  }

  /**
   * �������ȷ�ϱ���Ϣ
   *
   * @param blEndorse
   * @throws Exception
   */
  private void getCIEndorValid(DbPool dbPool, BLEndorse blEndorse) throws
      Exception {
    String sqlWhere = " Select * from CIEndorValid WHERE EndorseNo = '"
        + blEndorse.getBLPrpPhead().getArr(0).getEndorseNo() + "'";
    DBCIEndorValid dbCIEndorValid = new DBCIEndorValid();
    Vector vector = dbCIEndorValid.findByConditions(dbPool, sqlWhere);

    CIEndorValidSchema cIEndorValidSchema = (CIEndorValidSchema) vector
        .get(0);

    BLCIEndorValid blCIEndorValid = new BLCIEndorValid();
    blCIEndorValid.setArr(cIEndorValidSchema);

    blEndorse.setBLCIEndorValid(blCIEndorValid);
  }

  /*
   * ����ѯ�룬����CIEndorValid�� @param dbpool @param cIEndorValidSchema �������DbPool
   * �����п������� @throws Exception
   */
  private void updateCIEndorValidByDemandNo(DbPool dbPool,
                                            CIEndorValidSchema
                                            cIEndorValidSchema) throws
      Exception {
    String strSQL = " Update CIEndorValid Set"
        + " ValidNo = " + "'" + cIEndorValidSchema.getValidNo() + "'" + ","
        + " EndorseNo = " + "'" + cIEndorValidSchema.getEndorseNo() + "'" + ","
        + " PolicyNo = " + "'" + cIEndorValidSchema.getPolicyNo() + "'"
        + "," + " ChgPremium = " + "'"
        + cIEndorValidSchema.getChgPremium() + "'" + "," + " Ptext = "
        + "'" + cIEndorValidSchema.getPtext() + "'" + ","
        + " ValidTime = " + "'" + cIEndorValidSchema.getValidTime()
        + "'" + "," + " ValidStatus = " + "'"
        + cIEndorValidSchema.getValidStatus() + "'" + "," + " Flag = "
        + "'" + cIEndorValidSchema.getFlag() + "'"
        + " Where DemandNo = " + "'" + cIEndorValidSchema.getDemandNo()
        + "'";

    if (dbPool != null) {
      
      dbPool.update(strSQL);
    }
    else {
      DbPool newDbPool = new DbPool();
      try {
        newDbPool.open(SysConfig.CONST_DDCCDATASOURCE);
        newDbPool.beginTransaction();

        
        newDbPool.update(strSQL);

        newDbPool.commitTransaction();
      }
      catch (Exception exception) {
        newDbPool.rollbackTransaction();
        exception.printStackTrace();
      }
      finally {
        newDbPool.close();
      }
    }
  }

  /*
   * ���BLEndorse�����
   */
  private BLEndorse getBLEndorseDate(DbPool dbPool, String endorseNo) throws
      Exception {
    BLEndorse blEndorse = new BLEndorse();
    if (dbPool != null) {
      
      blEndorse.getData(dbPool, endorseNo);
    }
    else {
      DbPool newDbPool = new DbPool();
      try {
        newDbPool.open(SysConfig.CONST_DDCCDATASOURCE);
        newDbPool.beginTransaction();

        
        blEndorse.getData(newDbPool, endorseNo);

        newDbPool.commitTransaction();
      }
      catch (Exception exception) {
        newDbPool.rollbackTransaction();
        exception.printStackTrace();
      }
      finally {
        newDbPool.close();
      }
    }
    return blEndorse;
  }

  /**
   * �������XX�ط��Ķ���
   *
   * @param dbPool
   * @param querySQL
   * @return
   * @throws Exception
   */
  private BLCIEndorValid getRepeatCollection(DbPool dbPool, String strWhere) throws
      Exception {
    BLCIEndorValid blCIEndorValid = new BLCIEndorValid();
    DBCIEndorValid dbCIEndorValid = new DBCIEndorValid();
    CIEndorValidSchema schema = null;

    if (dbPool != null) {
      
      Vector vector = dbCIEndorValid.findByConditions(dbPool,strWhere);
      Iterator it = vector.iterator();
        while (it.hasNext()){
          schema = (CIEndorValidSchema) it.next();
          blCIEndorValid.setArr(schema);
        }
    }
    else {
      DbPool newDbPool = new DbPool();
      try {
        newDbPool.open(SysConfig.CONST_DDCCDATASOURCE);
        newDbPool.beginTransaction();

        
        Vector vector = dbCIEndorValid.findByConditions(dbPool,strWhere);
        Iterator it = vector.iterator();
          while (it.hasNext()){
            schema = (CIEndorValidSchema) it.next();
            blCIEndorValid.setArr(schema);
          }

        newDbPool.commitTransaction();
      }
      catch (Exception exception) {
        newDbPool.rollbackTransaction();
        exception.printStackTrace();
      }
      finally {
        newDbPool.close();
      }
    }
    return blCIEndorValid;
  }

  /**
   * ���벢�жϷ����Ƿ�ɹ������� 1�ɹ�,0���ɹ�
   *
   * @param content
   * @param cIInsureValidSchema
   * @return
   * @throws Exception
   */
  protected int decode(String content, CIEndorValidSchema cIEndorValidSchema) throws
      Exception {
    InputStream in = new ByteArrayInputStream(content.getBytes());
    Document document = XMLUtils.parse(in);

    
    
    int validType = 1;
    

    validType = processHead(XMLUtils.getChildNodeByPath(document,
        "/PACKET/HEAD"));

    if (validType == 1) {
      processBody(cIEndorValidSchema, XMLUtils.getChildNodeByPath(
          document, "/PACKET/BODY"));
    }

    return validType;
  }

  /**
   * ����HEAD��,�����Ƿ�ɹ��ı�־
   *
   * @param node
   * @return
   * @throws Exception
   */
  protected int processHead(Node node) throws Exception {
    
    
    
    
    

    
    
    int validType = 1;
    

    String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
    if (!responseCode.equals("1")) {
      validType = 0;
    }
    return validType;
  }

  /**
   * ����BODY��
   *
   * @param node
   *            Node
   * @throws Exception
   */
  protected void processBody(CIEndorValidSchema cIEndorValidSchema, Node node) throws
      Exception {
    processBasePart(cIEndorValidSchema, XMLUtils.getChildNodeByTagName(
        node, "BASE_PART"));
  }

  /**
   * ����BASE_PART��
   *
   * @param node
   *            Node
   * @throws Exception
   */
  protected void processBasePart(CIEndorValidSchema cIEndorValidSchema,
                                 Node node) throws Exception {
    
    
    

    
    String amendConfirmNo = XMLUtils.getChildNodeValue(node,
        "AMEND_CONFIRM_NO");

    
    cIEndorValidSchema.setValidNo(amendConfirmNo);
    
    cIEndorValidSchema.setValidStatus("0");
  }
}
