package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.indiv.ci.blsvr.BLCICheckCarShipTaxDetailQG;
import com.sp.indiv.ci.schema.CICheckCarShipTaxDetailQGSchema;


/**
 * 车船税对账查询请求数据的解码器
 * 
 */
public class CarShipTaxCheckQGDedailDecoder {

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
        processBasePart(carShipTaxCheckQG.getBlCICheckCarShipTaxDetailQG(), XMLUtils.getChildNodeByTagName(node,"BASE_PART"));
        processPolicyList(carShipTaxCheckQG.getBlCICheckCarShipTaxDetailQG(), XMLUtils.getChildNodeByTagName(node,"POLICY_LIST"));
        processAmendList(carShipTaxCheckQG.getBlCICheckCarShipTaxDetailQG(), XMLUtils.getChildNodeByTagName(node,"AMEND_LIST"));
    }

    /**
     * 处理BASE_PART节
     * 
     * @param node
     *            Node
     * @throws Exception
     */
    protected void processBasePart(BLCICheckCarShipTaxDetailQG blCICheckCarShipTaxDetailQG , Node node)
            throws Exception {
        
        String taxDeclareQueryNo = XMLUtils.getChildNodeValue(node, "TAX_POLICY_COUNT");
        String taxPolicyCount    = XMLUtils.getChildNodeValue(node, "TAX_AMEND_COUNT");

    }
    
    /**
     * 处理POLICY_LIST节
     * 
     * @param node
     *            Node
     * @throws Exception 
     */
    protected void processPolicyList(BLCICheckCarShipTaxDetailQG blCICheckCarShipTaxDetailQG, Node node) 
    throws Exception {
        Node[] nodes= XMLUtils.getChildNodesByTagName(node, "POLICY_DATA");
        
        for (int i = 0; i < nodes.length; i++) {
            processPolicyData(blCICheckCarShipTaxDetailQG, nodes[i]);
        }
    }
    /**
     * 处理AMEND_LIST节
     * 
     * @param node
     *            Node
     * @throws Exception 
     */
    protected void processAmendList(BLCICheckCarShipTaxDetailQG blCICheckCarShipTaxDetailQG, Node node) 
    throws Exception {
        Node[] nodes= XMLUtils.getChildNodesByTagName(node, "AMEND_DATA");
        
        for (int i = 0; i < nodes.length; i++) {
            processAmendData(blCICheckCarShipTaxDetailQG, nodes[i]);
        }
    }
    /**
     * 处理POLICY_DATA节
     * 
     * @param node
     *            Node
     * @throws Exception 
     */
    protected void processPolicyData(BLCICheckCarShipTaxDetailQG blCICheckCarShipTaxDetailQG, Node node) 
    throws Exception {
        
        String confirmSequenceNo = XMLUtils.getChildNodeValue(node, "CONFIRM_SEQUENCE_NO");
        String proposalNo        = XMLUtils.getChildNodeValue(node, "PROPOSAL_NO");
        String poliycyNo         = XMLUtils.getChildNodeValue(node, "POLICY_NO");
        String taxConditionCode  = XMLUtils.getChildNodeValue(node, "TAX_CONDITION_CODE");
        String taxAnnualTaxdue   = XMLUtils.getChildNodeValue(node, "TAX_ANNUAL_TAX_DUE");
        String taxSumTaxDefault  = XMLUtils.getChildNodeValue(node, "TAX_SUM_TAX_DEFAULT");
        String taxSumOverDue     = XMLUtils.getChildNodeValue(node, "TAX_SUM_OVER_DUE");
        String taxSumTax         = XMLUtils.getChildNodeValue(node, "TAX_SUM_TAX");
        
        CICheckCarShipTaxDetailQGSchema schema = new CICheckCarShipTaxDetailQGSchema();
        schema.setConFirmSequenceNo(confirmSequenceNo);
        schema.setProposalNo(proposalNo);
        schema.setCretiNo(poliycyNo);
        schema.setTaxConditionCode(taxConditionCode);
        schema.setTaxAnnualTaxDue(taxAnnualTaxdue);
        schema.setTaxSumTaxDeFault(taxSumTaxDefault);
        schema.setTaxSumOverDue(taxSumOverDue);
        schema.setTaxSumTax(taxSumTax);
        schema.setType("P");
        schema.setSerialNo("1");
        blCICheckCarShipTaxDetailQG.setArr(schema);
    }
    
    /**
     * 处理AMEND_DATA节
     * 
     * @param node
     *            Node
     * @throws Exception 
     */
    protected void processAmendData(BLCICheckCarShipTaxDetailQG blCICheckCarShipTaxDetailQG, Node node) 
    throws Exception {
        
        String amendConfirmNo           = XMLUtils.getChildNodeValue(node, "AMEND_CONFIRM_NO");
        String amendPolicyNo            = XMLUtils.getChildNodeValue(node, "AMEND_POLICY_NO");
        String taxConditionCode         = XMLUtils.getChildNodeValue(node, "TAX_CONDITION_CODE");
        String taxAnnualTaxDueChange    = XMLUtils.getChildNodeValue(node, "TAX_ANNUAL_TAX_DUE_CHANGE");
        String taxSumTaxDefaultChange   = XMLUtils.getChildNodeValue(node, "TAX_SUM_TAX_DEFAULT_CHANGE");
        String taxSumOverDueChange      = XMLUtils.getChildNodeValue(node, "TAX_SUM_OVER_DUE_CHANGE");
        String taxSumTaxChange          = XMLUtils.getChildNodeValue(node, "TAX_SUM_TAX_CHANGE");
        
        CICheckCarShipTaxDetailQGSchema schema = new CICheckCarShipTaxDetailQGSchema();
        schema.setConFirmSequenceNo(amendConfirmNo);
        schema.setCretiNo(amendPolicyNo);
        schema.setTaxConditionCode(taxConditionCode);
        schema.setTaxAnnualTaxDue(taxAnnualTaxDueChange);
        schema.setTaxSumTaxDeFault(taxSumTaxDefaultChange);
        schema.setTaxSumOverDue(taxSumOverDueChange);
        schema.setTaxSumTax(taxSumTaxChange);
        schema.setType("E");
        schema.setSerialNo("1");
        blCICheckCarShipTaxDetailQG.setArr(schema);
    }
}
