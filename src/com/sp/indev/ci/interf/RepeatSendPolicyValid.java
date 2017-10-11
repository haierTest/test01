package com.sp.indiv.ci.interf;

import java.io.*;
import java.util.*;

import org.w3c.dom.*;
import com.sp.indiv.ci.blsvr.*;
import com.sp.indiv.ci.dbsvr.*;
import com.sp.indiv.ci.schema.*;
import com.sp.prpall.blsvr.cb.*;
import com.sp.sysframework.common.datatype.*;
import com.sp.sysframework.common.util.*;
import com.sp.utility.*;
import com.sp.utility.database.*;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 重复发送XX确认信息
 *
 * @author 程凯
 */
public class RepeatSendPolicyValid
    extends PolicyValidEncoder {
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
      RepeatSendPolicyValid repeatPolicyValid = new RepeatSendPolicyValid();
      repeatPolicyValid.repeatPolicyValid(dbpool, "", "","");
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
  public void repeatPolicyValid(DbPool dbPool, String columnName,
                                String certiNo, String conditions) throws
      Exception {
    String strWhere = ""; 	
    String request 	= ""; 	
    String contet 	= ""; 	
    String comCode 	= "";
    String flagTmp  = "";	
    int count 			= 0;
    int successCount 	= 0;
    int failCount 		= 0;

    DateTime current = new DateTime(DateTime.current(), DateTime.YEAR_TO_SECOND); 

    int validType = -1; 
    String remark = ""; 

    strWhere = " SELECT * FROM CIInsureValid WHERE ValidNo = ProposalNo AND SUBSTR(Flag, 0, 1) = '1'";

    if (certiNo != null && !certiNo.equals("") && columnName != null
        && !columnName.equals("")) {
      strWhere += " AND " + columnName + " = '" + certiNo + "'";
    }

    if (conditions != null && !conditions.equals("")) {
      strWhere += " AND " + conditions;
    }

    BLCIInsureValid blCIInsureValid = getRepeatCollection(dbPool, strWhere);
    CIInsureValidSchema cIInsureValidSchema = null; 

    BLPolicy blPolicy = null;

    if (blCIInsureValid != null) {
      for (int i = 0; i < blCIInsureValid.getSize(); i++) { 
        count += 1; 

        cIInsureValidSchema = blCIInsureValid.getArr(i);
        flagTmp		= cIInsureValidSchema.getFlag();
        blPolicy 	= getBLPolicyDate(dbPool, cIInsureValidSchema.getPolicyNo());
        comCode 	= blPolicy.getBLPrpCmain().getArr(0).getComCode();

        request = endoce(dbPool,blPolicy);
        contet = EbaoProxy.getInstance().request(request, comCode);
        validType = decode(contet, cIInsureValidSchema);

        if (validType == 1) { 
          successCount += 1; 

          cIInsureValidSchema.setValidTime(current.toString());
          
  		  
          cIInsureValidSchema.setFlag("0" + flagTmp.substring(1, flagTmp.length())); 
          
        }
        else {
          failCount += 1; 
          
          logger.info(contet);
          
        }

        remark = cIInsureValidSchema.getRemark().split("-")[0];

        if (remark == null || remark.equals("")) { 
          
          remark = "1" + "-" + current.toString() + "-XX确认";
        }
        else {
          remark = "" + (new Integer(remark).intValue() + 1) + "-"
              + current.toString() + "-XX确认";
        }

        cIInsureValidSchema.setRemark(remark);

        
        updateCIInsureValidByProposalNo(dbPool, cIInsureValidSchema);

        
        logger.info("重发XX确认数据更新CIInsureValid表成功 , XX查询码 : [" + cIInsureValidSchema.getDemandNo() + "]");
        
      }
    }
    
    logger.info("===========================");
    logger.info("重发XX确认数据总共　 [ " + count + " ] 条");
    logger.info("重发XX确认数据成功了 [ " + successCount + " ] 条");
    logger.info("重发XX确认数据失败了 [ " + failCount + " ] 条");
    logger.info("===========================");
    
  }

  /**
   * 重写基类的endoce方法，判断是否要自行开起事物。
   * @param dbPool
   * @param blpolicy
   * @return
   * @throws java.lang.Exception
   */
  private String endoce(DbPool dbPool, BLPolicy blpolicy) throws Exception {
    
    StringBuffer buf = new StringBuffer(4000);
    getCIInsureDemand(dbPool, blpolicy);
    addXMLHead(buf);
    addPacket(buf, blpolicy);
    return buf.toString();
  }

  /**
   * 获得XX查询主信息
   * @param blPolicy
   * @throws Exception
   */
  private void getCIInsureDemand(DbPool dbPool, BLPolicy blPolicy) 
  	throws Exception 
  {
	
    
        				
    
    
	BLCIInsureDemand blciInsureDemand = new BLCIInsureDemand();
	
	String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
	if(!"1".equals(strSwitch)){
	    blciInsureDemand.getData(dbPool, blPolicy.getBLPrpCmain().getArr(0).getProposalNo());
	}else{
	  
		ArrayList iWhereValue=new ArrayList();
		iWhereValue.add(blPolicy.getBLPrpCitemCar().getArr(0).getDemandNo());
		blciInsureDemand.query(dbPool," DemandNo = ?",iWhereValue,Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
		
	}
	
	if(blciInsureDemand.getSize()>0){
		CIInsureDemandSchema cIInsureDemandSchema = blciInsureDemand.getArr(0);
		BLCIInsureDemand blCIInsureDemand = new BLCIInsureDemand();
	    blCIInsureDemand.setArr(cIInsureDemandSchema);
	    blPolicy.setBLCIInsureDemand(blCIInsureDemand);
	}
    
  }

  /*
   * 按XX号，更新CIInsureValid表 @param dbpool @param cIInsureValidSchema
   * 如果不传DbPool 则自行控制事物
   * @throws
   * Exception
   */
  private void updateCIInsureValidByProposalNo(DbPool dbPool,
                                               CIInsureValidSchema
                                               cIInsureValidSchema) throws
      Exception 
  {
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

      
      dbPool.update(strSQL);
  }

  /*
   * 获得Policy大对象
   */
  private BLPolicy getBLPolicyDate(DbPool dbPool, String policyNo) 
  	throws Exception 
  {
      BLPolicy blPolicy = new BLPolicy();
      
      blPolicy.getData(dbPool, policyNo);
      return blPolicy;
  }

  /**
   * 获得所有XX重发的对象
   *
   * @param dbPool
   * @param querySQL
   * @return
   * @throws Exception
   */
  private BLCIInsureValid getRepeatCollection(DbPool dbPool, String strWhere) 
  		throws Exception 
  {
      BLCIInsureValid blCIInsureValid = new BLCIInsureValid();
      DBCIInsureValid dbCIInsureValid = new DBCIInsureValid();
      CIInsureValidSchema schema = null;

      Vector vector = dbCIInsureValid.findByConditions(dbPool, strWhere);
      Iterator it = vector.iterator();
      while (it.hasNext())
      {
        schema = (CIInsureValidSchema) it.next();
        blCIInsureValid.setArr(schema);
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
  protected int decode(String content, CIInsureValidSchema cIInsureValidSchema) 
  		throws Exception 
  {
    InputStream in = new ByteArrayInputStream(content.getBytes());
    Document document = XMLUtils.parse(in);

    
    int validType = 1;
    validType = processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
    if (validType == 1) {
      processBody(cIInsureValidSchema, XMLUtils.getChildNodeByPath(
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
   * @param cIInsureValidSchema
   * @param node
   * @throws Exception
   */
  protected void processBody(CIInsureValidSchema cIInsureValidSchema,
                             Node node) 
  	throws Exception 
  {
    processBasePart(cIInsureValidSchema, XMLUtils.getChildNodeByTagName(node, "BASE_PART"));
  }

  /**
   * 处理BASE_PART节,在CIInsureValidSchema对象中XXXXX存XX确认码
   *
   * @param cIInsureValidSchema
   * @param node
   * @throws Exception
   */
  protected void processBasePart(CIInsureValidSchema cIInsureValidSchema,
                                 Node node) throws Exception {
    
    
    
    
    String confirmSequenceNo = XMLUtils.getChildNodeValue(node, "CONFIRM_SEQUENCE_NO");
    cIInsureValidSchema.setValidNo(confirmSequenceNo);
  }
}
