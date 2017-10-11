package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.error.UserException;

public class VoucherReuploadDecoder {
	String errorMessage = "";
	/**
	 * @return 凭证补传解码
	 * @throws Exception
	 */
	public String decode(String content)
		throws UserException, Exception 
	{
		InputStream in    = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		String errorMessage = processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
		return errorMessage ;
	}

	/**
	 * 处理HEAD节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	/**
     * 处理HEAD节
     * @param node Node
     * @throws Exception
     */
    protected String processHead(Node node) throws Exception {
    	
    	
    	
    	
        


        String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
        errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
        if (responseCode.equals("1")) {
            errorMessage = "";
        }else{
        	throw new Exception(errorMessage);
        }
        return errorMessage;
    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
}
