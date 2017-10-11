package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * ���ͽ��׻����������ݵĽ�����
 * 
 */
public class ProposalRepealAfterAuditingDecoder {

	String errorMessage = "";
	/**
	 * @return ���׻��˽���
	 * @throws Exception
	 */
	public void decode(BLProposal blProposal, 
					   String content)
		throws UserException, Exception 
	{
		InputStream in    = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
	}

	/**
	 * ����HEAD��
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	/**
     * ����HEAD��
     * @param node Node
     * @throws Exception
     */
    protected void processHead(Node node) throws Exception {






        String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
        errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
        if (!responseCode.equals("1")) {
            throw new Exception(errorMessage);
        }
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
