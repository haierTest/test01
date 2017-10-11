package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.dbsvr.DBCIEndorValid;
import com.sp.indiv.ci.schema.CIEndorValidSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

/**
 * 发送批改确认返回数据的解码器
 * 
 */
public class EndorseDummyValidDecoder {
	
	
	private static Logger logger = Logger.getLogger(EndorseDummyValidDecoder.class);
	

    /**
     * 解码
     * 
     * @return XX查询请求XML格式串
     * @throws Exception
     */
    public void decode(DbPool dbPool, 
                       BLEndorse blEndorse, 
                       String content)
        throws SQLException, UserException, Exception 
    {
    	
        logger.info("[批改预确认返回报文]:"+content);
        
        
        InputStream in    = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);

        
        int type = -1;
        try
        {
            type = processHead(blEndorse, XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
        }
        catch(Exception ex1)
        {
            throw new Exception("解析平台返回串错误。批单号：" + blEndorse.getBLPrpPmain().getArr(0).getEndorseNo() 
                                + "，请与管理员联系！" + ex1.getMessage());
        }
        
        if(type != 1)
        {
            throw new Exception(blEndorse.getBLCIEndorValid().getArr(0).getErrorMessage());
        }
        
    }

    /**
     * 处理HEAD节
     * 
     * @param node
     *            Node
     * @throws Exception
     */
    
    protected int processHead(BLEndorse blEndorse, Node node) throws Exception 
    {
        
        int type = 1;
        String responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");    
        /*modi by liujia start
          reason:将原来两句代码移出来，因为平台重复XX开关为开时发生重复XX交易返回正常
        */
        String errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");    
        blEndorse.getBLCIEndorValid().getArr(0).setErrorMessage(errorMessage);
        /*modi by liujia end*/
        if (!responseCode.equals("1")) {
            type = 0;
        }
        return type;
    }
    
}