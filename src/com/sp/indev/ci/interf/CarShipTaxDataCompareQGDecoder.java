package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.prpall.blsvr.cb.BLPrpCcarshipTax;
import com.sp.prpall.blsvr.pg.BLPrpPcarshipTax;
import com.sp.prpall.blsvr.pg.BLPrpPhead;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.indiv.ci.blsvr.BLCICheckCarShipTaxDetailQG;
import com.sp.indiv.ci.blsvr.BLCITaxDataCompareQG;
import com.sp.indiv.ci.schema.CICheckCarShipTaxDetailQGSchema;
import com.sp.indiv.ci.schema.CITaxDataCompareQGSchema;


/**
 * 车船税对账查询请求数据的解码器
 * 
 */
public class CarShipTaxDataCompareQGDecoder {

    public static void main(String[] args) throws Exception {}

    /**
     * 解码
     * 
     * @return 车船税对账请求XML格式串
     * @throws Exception
     */
    public void decode(CarShipTaxCheckQG carShipTaxCheckQG ,String content) throws Exception {
        InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
        processBody(carShipTaxCheckQG, XMLUtils.getChildNodeByPath(document,"/PACKET/BODY"));
    }

    /**
     * 处理HEAD节
     * 
     * @param node
     *            Node
     * @throws Exception
     */
    protected void processHead(Node node) throws Exception {
        String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
        if (!responseCode.equals("1")) {
            String errorMessage = XMLUtils.getChildNodeValue(node,
                    "ERROR_MESSAGE");
            throw new Exception(errorMessage);
        }
    }

    /**
     * 处理BODY节
     * 
     * @param node
     *            Node
     * @throws Exception
     */
    protected void processBody(CarShipTaxCheckQG carShipTaxCheckQG , Node node)
            throws Exception {
        processCheckResultList(carShipTaxCheckQG.getBlCITaxDataCompareQG(), XMLUtils.getChildNodeByTagName(node,"CHECK_RESULT_LIST"));
    }


    
    /**
     * 处理CHECK_RESULT_LIST节
     * 
     * @param node
     *            Node
     * @throws Exception 
     */
    public void processCheckResultList(BLCITaxDataCompareQG blCITaxDataCompareQG , Node node)throws Exception {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CHECK_RESULT");
        for (int i = 0; i < nodes.length; i++) {
            processCheckResultData(blCITaxDataCompareQG, nodes[i]);
        }
    }
    
    /**
     * 处理CHECK_RESULT节
     * 
     * @param node
     *            Node
     * @throws Exception 
     */
    protected void processCheckResultData(BLCITaxDataCompareQG blCITaxDataCompareQG, Node node) 
    throws Exception {
        
        String confirmSequenceNo = XMLUtils.getChildNodeValue(node, "CONFIRM_SEQUENCE_NO");
        String amendConfirmNo    = XMLUtils.getChildNodeValue(node, "AMEND_CONFIRM_NO");
        String taxTotalPayIA     = XMLUtils.getChildNodeValue(node, "TAX_TOTAL_PAY_IA");
        String taxTotalPayLT     = XMLUtils.getChildNodeValue(node, "TAX_TOTAL_PAY_LT");
        String declareStatusIA   = XMLUtils.getChildNodeValue(node, "DECLARE_STATUS_IA");
        String declareStatusLT   = XMLUtils.getChildNodeValue(node, "DECLARE_STATUS_LT");
        
        CITaxDataCompareQGSchema schema = new CITaxDataCompareQGSchema();
        if(confirmSequenceNo!=null&&!"".equals(confirmSequenceNo.trim())){
            schema.setType("P");
            schema.setConFirmNo(confirmSequenceNo);
        }else if(amendConfirmNo!=null&&!"".equals(amendConfirmNo.trim())){
            schema.setType("E");
            schema.setConFirmNo(amendConfirmNo);
        }
        schema.setTaxToTalPayIA(taxTotalPayIA);
        schema.setTaxToTalPayLT(taxTotalPayLT);
        schema.setDeclareStatusIA(declareStatusIA);
        schema.setDeclareStatusLT(declareStatusLT);
        blCITaxDataCompareQG.setArr(schema);
    }
    
    
    /**
     * 解码
     * 
     * @return 车船税对账请求XML格式串
     * @throws Exception
     */
    public void decodeJZ(DbPool dbpool,CarShipTaxCheckQG carShipTaxCheckQG ,String content,String comcode) throws Exception {
        InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        processHeadJZ(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"),carShipTaxCheckQG);
        processBodyJZ(dbpool,carShipTaxCheckQG, XMLUtils.getChildNodeByPath(document,"/PACKET/BODY"),comcode);
    }
    /**
     * 处理HEAD节
     * 
     * @param node
     *            Node
     * @throws Exception
     */
    protected void processHeadJZ(Node node,CarShipTaxCheckQG carShipTaxCheckQG) throws Exception {
        String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
        if (!responseCode.equals("1")) {
            String errorMessage = XMLUtils.getChildNodeValue(node,
                    "ERROR_MESSAGE");
            throw new Exception("业务号对账失败:"+errorMessage);
        }
    }
    /**
     * 处理BODY节
     * 
     * @param node
     *            Node
     * @throws Exception
     */
    protected void processBodyJZ(DbPool dbpool,CarShipTaxCheckQG carShipTaxCheckQG , Node node,String comcode)
            throws Exception {
    	
    	if(new UtiPower().checkCarShipTaxComparison(comcode)){
    		processCheckResultListGX(dbpool,carShipTaxCheckQG.getBlCICheckCarShipTaxDetailQG(), XMLUtils.getChildNodeByTagName(node,"CHECK_RESULT_LIST"));
    	}else{
    		processCheckResultListJZ(dbpool,carShipTaxCheckQG.getBlCICheckCarShipTaxDetailQG(), XMLUtils.getChildNodeByTagName(node,"Check_Result_List"));
    	}
    	
    }


    
    /**
     * 处理CHECK_RESULT_LIST节
     * 
     * @param node
     *            Node
     * @throws Exception 
     */
    public void processCheckResultListJZ(DbPool dbpool,BLCICheckCarShipTaxDetailQG blCICheckCarShipTaxDetailQG , Node node)throws Exception {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "Check_Result");
        for (int i = 0; i < nodes.length; i++) {
        	processCheckResultDataJZ(dbpool,blCICheckCarShipTaxDetailQG, nodes[i], i);
        }
    }
    
    /**
     * 处理CHECK_RESULT节
     * 
     * @param node
     *            Node
     * @throws Exception 
     */
    protected void processCheckResultDataJZ(DbPool dbpool,BLCICheckCarShipTaxDetailQG blCICheckCarShipTaxDetailQG, Node node,int i) 
    throws Exception {
        
        String confirmSequenceNo = XMLUtils.getChildNodeValue(node, "Confirm_Sequence_No");
        String amendConfirmNo    = XMLUtils.getChildNodeValue(node, "Amend_Confirm_No");
        String strCalcTaxFlag    = "";
        String policyNo          = "";
        String type              = "";
        CICheckCarShipTaxDetailQGSchema schema = new CICheckCarShipTaxDetailQGSchema();
        
        BLCICheckCarShipTaxDetailQG blciCheckCarShipTaxDetailQG2 = new BLCICheckCarShipTaxDetailQG();
        BLPrpCcarshipTax blPrpCcarshipTax = new BLPrpCcarshipTax();
        if(confirmSequenceNo!=null&&!"".equals(confirmSequenceNo.trim())){
            blciCheckCarShipTaxDetailQG2.getData(dbpool, confirmSequenceNo);
            policyNo = blciCheckCarShipTaxDetailQG2.getArr(0).getCretiNo();
            schema.setType("P");
            schema.setCretiNo(policyNo);
            schema.setConFirmSequenceNo(confirmSequenceNo);
        }else if(amendConfirmNo!=null&&!"".equals(amendConfirmNo.trim())){
            BLPrpPhead blPrpPhead = new BLPrpPhead();
            blciCheckCarShipTaxDetailQG2.getData(dbpool, amendConfirmNo);
            blPrpPhead.getData(dbpool, blciCheckCarShipTaxDetailQG2.getArr(0).getCretiNo());
            policyNo = blPrpPhead.getArr(0).getPolicyNo();
            schema.setType("E");
            schema.setConFirmSequenceNo(amendConfirmNo);
        }
        String extendChar1       = "";
        String extendChar2       = "";
        extendChar1 = XMLUtils.getChildNodeValue(node, "Declare_Status_IA");
        extendChar2 = XMLUtils.getChildNodeValue(node, "Declare_Status_LT");
        schema.setExtendChar1(extendChar1);
        schema.setExtendChar2(extendChar2);
        blPrpCcarshipTax.getData(dbpool, policyNo);
        strCalcTaxFlag = blPrpCcarshipTax.getArr(0).getCalcTaxFlag();
        blCICheckCarShipTaxDetailQG.setArr(schema);
        if("2".equals(strCalcTaxFlag)){
        	processTaxAmountJZ(dbpool,blCICheckCarShipTaxDetailQG, XMLUtils.getChildNodeByTagName(node, "TaxAmount_IA"), i);
        }else if ("1".equals(strCalcTaxFlag)) {
        	processTaxAmountJZ(dbpool,blCICheckCarShipTaxDetailQG, XMLUtils.getChildNodeByTagName(node, "TaxAmount_LT"), i);
		}
    }
    protected void processTaxAmountJZ(DbPool dbpool,BLCICheckCarShipTaxDetailQG blCICheckCarShipTaxDetailQG,Node node,int i)
    throws Exception {
    	
        String strAnnualTaxDue   = XMLUtils.getChildNodeValue(node, "AnnualTaxDue");
        String strSumTaxDefault  = XMLUtils.getChildNodeValue(node, "SumTaxDefault");
        String strSumOverdue     = XMLUtils.getChildNodeValue(node, "SumOverdue");
        String strSumTax         = XMLUtils.getChildNodeValue(node, "SumTax");
        
        CICheckCarShipTaxDetailQGSchema schema = blCICheckCarShipTaxDetailQG.getArr(i);
        schema.setTaxAnnualTaxDue(strAnnualTaxDue);
        schema.setTaxSumTaxDeFault(strSumTaxDefault);
        schema.setTaxSumOverDue(strSumOverdue);
        schema.setTaxSumTax(strSumTax);
        blCICheckCarShipTaxDetailQG.getArr(i).setSchema(schema);
    }
    
    
    /**
     * 处理CHECK_RESULT_LIST节
     * 
     * @param node
     *            Node
     * @throws Exception 
     */
    public void processCheckResultListGX(DbPool dbpool,BLCICheckCarShipTaxDetailQG blCICheckCarShipTaxDetailQG , Node node)throws Exception {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CHECK_RESULT");
        for (int i = 0; i < nodes.length; i++) {
        	processCheckResultDataGX(dbpool,blCICheckCarShipTaxDetailQG, nodes[i], i);
        }
    }
    
    /**
     * 处理CHECK_RESULT节
     * 
     * @param node
     *            Node
     * @throws Exception 
     */
    protected void processCheckResultDataGX(DbPool dbpool,BLCICheckCarShipTaxDetailQG blCICheckCarShipTaxDetailQG, Node node,int i) 
    throws Exception {
        
        String confirmSequenceNo = XMLUtils.getChildNodeValue(node, "CONFIRM_SEQUENCE_NO");
        String amendConfirmNo    = XMLUtils.getChildNodeValue(node, "AMEND_CONFIRM_NO");
        String strCalcTaxFlag    = "";
        String policyNo          = "";
        String type              = "";
        CICheckCarShipTaxDetailQGSchema schema = new CICheckCarShipTaxDetailQGSchema();
        
        BLCICheckCarShipTaxDetailQG blciCheckCarShipTaxDetailQG2 = new BLCICheckCarShipTaxDetailQG();
        BLPrpCcarshipTax blPrpCcarshipTax = new BLPrpCcarshipTax();
        if(confirmSequenceNo!=null&&!"".equals(confirmSequenceNo.trim())){
            blciCheckCarShipTaxDetailQG2.getData(dbpool, confirmSequenceNo);
            policyNo = blciCheckCarShipTaxDetailQG2.getArr(0).getCretiNo();
            schema.setType("P");
            schema.setCretiNo(policyNo);
            schema.setConFirmSequenceNo(confirmSequenceNo);
        }else if(amendConfirmNo!=null&&!"".equals(amendConfirmNo.trim())){
            BLPrpPhead blPrpPhead = new BLPrpPhead();
            blciCheckCarShipTaxDetailQG2.getData(dbpool, amendConfirmNo);
            blPrpPhead.getData(dbpool, blciCheckCarShipTaxDetailQG2.getArr(0).getCretiNo());
            policyNo = blPrpPhead.getArr(0).getPolicyNo();
            schema.setType("E");
            schema.setConFirmSequenceNo(amendConfirmNo);
        }
        String extendChar1       = "";
        String extendChar2       = "";
        extendChar1 = XMLUtils.getChildNodeValue(node, "DECLARE_STATUS_IA");
        extendChar2 = XMLUtils.getChildNodeValue(node, "DECLARE_STATUS_LT");
        schema.setExtendChar1(extendChar1);
        schema.setExtendChar2(extendChar2);
        blPrpCcarshipTax.getData(dbpool, policyNo);
        strCalcTaxFlag = blPrpCcarshipTax.getArr(0).getCalcTaxFlag();
        blCICheckCarShipTaxDetailQG.setArr(schema);
        if("2".equals(strCalcTaxFlag)){
        	processTaxAmountJZ(dbpool,blCICheckCarShipTaxDetailQG, XMLUtils.getChildNodeByTagName(node, "TAXAMOUNT_IA"), i);
        }else if ("1".equals(strCalcTaxFlag)) {
        	processTaxAmountJZ(dbpool,blCICheckCarShipTaxDetailQG, XMLUtils.getChildNodeByTagName(node, "TAXAMOUNT_LT"), i);
		}
    }
    
}
