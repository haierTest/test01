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
 * 重发批单确认信息
 *
 * @author 程凯
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
   * 重新发送XX确认
   *
   * @param dbPool
   *            不进行同一事物，则传null
   * @param columnName
   *            列名，可以为空
   * @param certiNo
   *            列的查询条件。
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

        
        logger.info("重发批单确认数据更新CIInsureValid表成功  , 批改查询码 : [" + cIEndorValidSchema.getDemandNo() + "]");
        
      }
    }
    
    logger.info("===========================");
    logger.info("重发批单确认数据总共　 [ " + count + " ] 条");
    logger.info("重发批单确认数据成功了 [ " + successCount + " ] 条");
    logger.info("重发批单确认数据失败了 [ " + failCount + " ] 条");
    logger.info("===========================");
    
  }

  /**
   * 重写基类的endoce方法，判断是否要自行开起事物。
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
   * 获得批改确认表信息
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
   * 按查询码，更新CIEndorValid表 @param dbpool @param cIEndorValidSchema 如果不传DbPool
   * 则自行控制事物 @throws Exception
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
   * 获得BLEndorse大对象
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
   * 获得所有XX重发的对象
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
   * 解码并判断返回是否成功的类型 1成功,0不成功
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
   * 处理HEAD节,返回是否成功的标志
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
   * 处理BODY节
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
   * 处理BASE_PART节
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
