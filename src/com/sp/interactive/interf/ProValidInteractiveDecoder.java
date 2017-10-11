package com.sp.interactive.interf;

import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.utility.database.DbPool;

/**
 * 解析XX确认发送的报文
 * @author qilin
 * */

public class ProValidInteractiveDecoder {
	public void decode(Map paramMap,Document doc)throws Exception 
	{
		Element root = doc.getRootElement();
		parseHead(root.element("HEAD"),paramMap);
		parseBody(root.element("BODY"),paramMap);
	}

	/**
	 * 处理HEAD节点
	 * */
	private void parseHead(Element head,Map paramMap) {
      String requestType = head.elementText("REQUESTTYPE");
      paramMap.put("REQUESTTYPE", requestType);

		
	}

	/**
	 * 处理BODY节点
	 * */
	private void parseBody(Element body, Map paramMap) {
		String businessNo = body.elementText("PriceNo");
		paramMap.put("Price_No", businessNo);
		
		
	}

}



