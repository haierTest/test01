package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.indiv.ci.blsvr.BLCIAssemblePayNoQG;
import com.sp.indiv.ci.schema.CIAssemblePayNoQGSchema;



/**
 * 车船税对账查询请求数据的解码器
 * 
 */
public class CarShipTaxAssembleQGDecoder {

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
        processCheckResultList(carShipTaxCheckQG.getBlCIAssemblePayNoQG(), XMLUtils.getChildNodeByTagName(node,"RECEIPT_INFO_LIST"));
    }


    /**
     * 处理CHECK_RESULT_LIST节
     * 
     * @param node
     *            Node
     * @throws Exception 
     */
    public void processCheckResultList(BLCIAssemblePayNoQG blCIAssemblePayNoQG, Node node)throws Exception {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "RECEIPT_INFO");
        for (int i = 0; i < nodes.length; i++) {
            processReceiptData(blCIAssemblePayNoQG, nodes[i]);
        }
    }
    
    /**
     * 处理RECEIPT_INFO节
     * 
     * @param node
     *            Node
     * @throws Exception 
     */
    protected void processReceiptData(BLCIAssemblePayNoQG blCIAssemblePayNoQG, Node node) 
    throws Exception {
        
        String taxReceiptNo = XMLUtils.getChildNodeValue(node, "TAX_RECEIPT_NO");
        String deductStatus = XMLUtils.getChildNodeValue(node, "DEDUCT_STATUS");
        String deductAmount = XMLUtils.getChildNodeValue(node, "DEDUCT_AMOUNT");
        String deductDate   = XMLUtils.getChildNodeValue(node, "DEDUCT_DATE");
        
        CIAssemblePayNoQGSchema schema = new CIAssemblePayNoQGSchema();
        schema.setTaxReceiptNo(taxReceiptNo);
        schema.setDeductStatus(deductStatus);
        schema.setDeductAmout(deductAmount);
        schema.setDeductDate(deductDate);
        blCIAssemblePayNoQG.setArr(schema);

    }
    
    
}
