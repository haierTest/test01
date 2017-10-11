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
import java.util.Vector;
import com.sp.prpall.dbsvr.pg.DBPrpPhead;
import com.sp.prpall.schema.PrpPheadSchema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 重发XX注销信息
 *
 * @author 程凯
 */
public class RepeatSendPolicyCancel
    extends PolicyCancelEncoder {

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
         RepeatSendPolicyCancel repeatSendPolicyCancel = new RepeatSendPolicyCancel();
      repeatSendPolicyCancel.repeatSendPolicyCancel(dbpool, "", "", "");
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
  public void repeatSendPolicyCancel(DbPool dbPool, String columnName,
                                     String certiNo, String conditions) throws
      Exception {
    String strWhere = ""; 
    String request = ""; 
    String contet = ""; 
    String comCode = "";
    String flagTmp = "";	

    int count = 0;
    int successCount = 0;
    int failCount = 0;

    DateTime current = new DateTime(DateTime.current(),
                                    DateTime.YEAR_TO_SECOND); 

    int validType = -1; 
    String remark = ""; 

    strWhere = " SELECT * FROM CIInsureValid WHERE SUBSTR(Flag,2,1) = '1' AND SUBSTR(Flag, 1, 1) = '0'";

    if (certiNo != null && !certiNo.equals("") && columnName != null
        && !columnName.equals("")) {
      strWhere += " AND " + columnName + " = '" + certiNo + "'";
    }

    if (conditions != null && !conditions.equals("")) {
      strWhere += " AND " + conditions;
    }

    BLCIInsureValid blCIInsureValid = getRepeatCollection(dbPool, strWhere);
    CIInsureValidSchema cIInsureValidSchema = null; 

    BLEndorse blEndorse = null;

    if (blCIInsureValid != null) {
      for (int i = 0; i < blCIInsureValid.getSize(); i++) { 
        count += 1; 

        cIInsureValidSchema = blCIInsureValid.getArr(i);
        blEndorse 	= getEndorseDate(dbPool, cIInsureValidSchema .getPolicyNo());
        flagTmp		= cIInsureValidSchema.getFlag();
        comCode 	= blEndorse.getBLPrpPmain().getArr(0).getComCode();
        request 	= encode(dbPool, blEndorse);
        contet 		= EbaoProxy.getInstance().request(request, comCode);
        validType 	= decode(contet, cIInsureValidSchema);

        if (validType == 1) { 
          successCount += 1; 
          cIInsureValidSchema.setValidTime(current.toString());
          
  		  
          cIInsureValidSchema.setFlag("00" + flagTmp.substring(2, flagTmp.length()));     
        }
        else {
          failCount += 1; 
        }

        remark = cIInsureValidSchema.getRemark().split("-")[0];

        
        if (remark == null || remark.equals("")) {
          remark = "1" + "-" + current.toString() + "-XX注销";
        }
        else {
          remark = "" + (new Integer(remark).intValue() + 1) + "-"
              + current.toString() + "-XX注销";
        }

        cIInsureValidSchema.setRemark(remark);

        
        updateCIInsureValidByProposalNo(dbPool, cIInsureValidSchema);

        
        logger.info("重发XX注销数据更新CIInsureValid表成功 , XX查询码 : [" + cIInsureValidSchema.getDemandNo() + "]");
        
      }
    }
    
    logger.info("===========================");
    logger.info("重发XX注销数据总共　 [ " + count + " ] 条");
    logger.info("重发XX注销数据成功了 [ " + successCount + " ] 条");
    logger.info("重发XX注销数据失败了 [ " + failCount + " ] 条");
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
      getCIInsureValid(dbPool, blEndorse); 
      addXMLHead(buf);
      addPacket(buf, blEndorse);
      return buf.toString();
  }

  /**
   * 获得XX确认主信息
   *
   * @param blEndorse
   * @throws Exception
   */
  private void getCIInsureValid(DbPool dbPool, BLEndorse blEndorse) throws
      Exception {
    String sqlWhere = " Select * from CIInsureValid WHERE PolicyNo = '"
        + blEndorse.getBLPrpPhead().getArr(0).getPolicyNo() + "'";
    DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
    Vector vector = dbCIInsureValid.findByConditions(dbPool, sqlWhere);

    CIInsureValidSchema cIInsureValidSchema = (CIInsureValidSchema) vector
        .get(0);

    BLCIInsureValid blCIInsureValid = new BLCIInsureValid();
    blCIInsureValid.setArr(cIInsureValidSchema);

    blEndorse.setBLCIInsureValid(blCIInsureValid);
  }

  /*
   * 按XX号，更新CIInsureValid表
   * @param dbpool 如果不传DbPool* 则自行控制事物
   * @param cIInsureValidSchema
   * @throws Exception
   */
  private void updateCIInsureValidByProposalNo(DbPool dbPool,
                                               CIInsureValidSchema
                                               cIInsureValidSchema) throws
      Exception {
    String strSQL = " Update CIInsureValid Set" + " ValidNo = " + "'"
        + cIInsureValidSchema.getValidNo() + "'" + "," + " DemandNo = "
        + "'" + cIInsureValidSchema.getDemandNo() + "'" + ","
        + " ProposalNo = " + "'" + cIInsureValidSchema.getProposalNo()
        + "'" + "," + " PolicyNo = " + "'"
        + cIInsureValidSchema.getPolicyNo() + "'" + ","
        + " InsureMarkNo = " + "'"
        + cIInsureValidSchema.getInsureMarkNo() + "'" + ","
        + " BussinessNature = " + "'"
        + cIInsureValidSchema.getBussinessNature() + "'" + ","
        + " OperateDate = " + "'"
        + cIInsureValidSchema.getOperateDate() + "'" + ","
        + " Clauses = " + "'" + cIInsureValidSchema.getClauses() + "'"
        + "," + " HandlerName = " + "'"
        + cIInsureValidSchema.getHandlerName() + "'" + ","
        + " OperatorName = " + "'"
        + cIInsureValidSchema.getOperatorName() + "'" + ","
        + " Currency = " + "'" + cIInsureValidSchema.getCurrency()
        + "'" + "," + " Premium = " + "'"
        + cIInsureValidSchema.getPremium() + "'" + ","
        + " ChangeReason = " + "'"
        + cIInsureValidSchema.getChangeReason() + "'" + ","
        + " ChangeDetail = " + "'"
        + cIInsureValidSchema.getChangeDetail() + "'" + ","
        + " ComCode = " + "'" + cIInsureValidSchema.getComCode() + "'"
        + "," + " ValidTime = " + "'"
        + cIInsureValidSchema.getValidTime() + "'" + "," + " Remark = "
        + "'" + cIInsureValidSchema.getRemark() + "'" + ","
        + " Flag = " + "'" + cIInsureValidSchema.getFlag() + "'"
        + " Where ProposalNo = " + "'"
        + cIInsureValidSchema.getProposalNo() + "'";

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
  private BLEndorse getEndorseDate(DbPool dbPool, String policyNo) throws
      Exception {
    BLEndorse blEndorse = new BLEndorse();

    DBPrpPhead dbPrpPhead = new DBPrpPhead();
    PrpPheadSchema prpPheadSchema = null;

    String strWhere = " SELECT * FROM PrpPhead WHERE PolicyNO = '" + policyNo +
        "' AND EndorType = '19'";
    String endorseNo = "";

    if (dbPool != null) {
      
      Vector vector = dbPrpPhead.findByConditions(dbPool, strWhere);
      prpPheadSchema = (PrpPheadSchema) vector.get(0);

      endorseNo = prpPheadSchema.getEndorseNo();

      
      blEndorse.getData(dbPool, endorseNo);
    }
    else {
      DbPool newDbPool = new DbPool();
      try {
        newDbPool.open(SysConfig.CONST_DDCCDATASOURCE);
        newDbPool.beginTransaction();

        
        Vector vector = dbPrpPhead.findByConditions(dbPool, strWhere);
        prpPheadSchema = (PrpPheadSchema) vector.get(0);

        endorseNo = prpPheadSchema.getEndorseNo();

        
        blEndorse.getData(dbPool, endorseNo);

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
  private BLCIInsureValid getRepeatCollection(DbPool dbPool, String strWhere) throws
      Exception {
    BLCIInsureValid blCIInsureValid = new BLCIInsureValid();
    DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
    CIInsureValidSchema schema = null;

    if (dbPool != null) {
      
      Vector vector = dbCIInsureValid.findByConditions(dbPool, strWhere);
      Iterator it = vector.iterator();
      while (it.hasNext()) {
        schema = (CIInsureValidSchema) it.next();
        blCIInsureValid.setArr(schema);
      }
    }
    else {
      DbPool newDbPool = new DbPool();
      try {
        newDbPool.open(SysConfig.CONST_DDCCDATASOURCE);
        newDbPool.beginTransaction();

        Vector vector = dbCIInsureValid.findByConditions(dbPool, strWhere);
        Iterator it = vector.iterator();
        while (it.hasNext()) {
          schema = (CIInsureValidSchema) it.next();
          blCIInsureValid.setArr(schema);
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
    return blCIInsureValid;
  }

  /**
   * 解码并判断返回是否成功的类型 1成功,0不成功
   *
   * @param content
   * @param cIInsureValidSchema
   * @return
   * @throws Exception
   */
  protected int decode(String content, CIInsureValidSchema cIInsureValidSchema) throws
      Exception {
    InputStream in = new ByteArrayInputStream(content.getBytes());
    Document document = XMLUtils.parse(in);

    
    
    int validType = 1;
    

    validType = processHead(XMLUtils.getChildNodeByPath(document,
        "/PACKET/HEAD"));

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
}
