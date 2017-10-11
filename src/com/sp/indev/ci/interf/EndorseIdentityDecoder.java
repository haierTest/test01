package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.sysframework.common.util.XMLUtils;

public class EndorseIdentityDecoder {

	private static Logger logger = Logger.getLogger(EndorseIdentityDecoder.class);
	
	String errorMessage = "";
	
	public void decode(BLPolicy iPolicy, String content) throws Exception {
		logger.info("[�������֤�ɼ����ر���]:"+content);
		
		InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        
        processHead(iPolicy,XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
        
	}
	
    protected void processHead(BLPolicy iPolicy,Node node) throws Exception {
    	String requestType = XMLUtils.getChildNodeValue(node, "REQUEST_TYPE");
        String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
        errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
        if (!responseCode.equals("1")) {
            throw new Exception(errorMessage);
        }
    }
}
