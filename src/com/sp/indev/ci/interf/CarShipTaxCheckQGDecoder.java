
package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;


import com.sp.sysframework.common.util.XMLUtils;
import com.sp.indiv.ci.blsvr.BLCICheckCarShipTaxQG;
import com.sp.indiv.ci.schema.CICheckCarShipTaxQGSchema;
;

/**
 * 车船税对账查询请求数据的解码器
 * 
 */
public class CarShipTaxCheckQGDecoder {

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
        processBasePart(carShipTaxCheckQG.getBlCICheckCarShipTaxQG(), XMLUtils.getChildNodeByTagName(node,"BASE_PART"));

    }

    /**
     * 处理BASE_PART节
     * 
     * @param node
     *            Node
     * @throws Exception
     */
    protected void processBasePart(BLCICheckCarShipTaxQG blCICheckCarShipTaxQG , Node node)
            throws Exception {
        
        String taxDeclareQueryNo = XMLUtils.getChildNodeValue(node,"TAX_DECLARE_QUERY_NO");
        String taxPolicyCount    = XMLUtils.getChildNodeValue(node, "TAX_POLICY_COUNT");
        String taxPolicyMoney    = XMLUtils.getChildNodeValue(node, "TAX_POLICY_MONEY");
        String taxAmendCount     = XMLUtils.getChildNodeValue(node, "TAX_AMEND_COUNT");
        String taxAmendMoney     = XMLUtils.getChildNodeValue(node, "TAX_AMEND_MONEY");
        
        CICheckCarShipTaxQGSchema schema = new CICheckCarShipTaxQGSchema();
        schema.setTaxDeclareQueryNo(taxDeclareQueryNo);
        schema.setTaxPolicyCount(taxPolicyCount);
        schema.setTaxPolicyMoney(taxPolicyMoney);
        schema.setTaxAmendCount(taxAmendCount);
        schema.setTaxAmendMoney(taxAmendMoney);
        
        blCICheckCarShipTaxQG.setArr(schema);
    }
}
