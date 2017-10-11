
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
 * ����˰���˲�ѯ�������ݵĽ�����
 * 
 */
public class CarShipTaxCheckQGDecoder {

    public static void main(String[] args) throws Exception {}

    /**
     * ����
     * 
     * @return ����˰��������XML��ʽ��
     * @throws Exception
     */
    public void decode(CarShipTaxCheckQG carShipTaxCheckQG ,String content) throws Exception {
        InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
        processBody(carShipTaxCheckQG, XMLUtils.getChildNodeByPath(document,"/PACKET/BODY"));
    }

    /**
     * ����HEAD��
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
     * ����BODY��
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
     * ����BASE_PART��
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
