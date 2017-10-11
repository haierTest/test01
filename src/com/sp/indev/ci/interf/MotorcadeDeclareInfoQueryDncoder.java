package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCIMotorcadeDeclare;
import com.sp.indiv.ci.schema.CIMotorcadeDeclareSchema;
import com.sp.sysframework.common.util.XMLUtils;

public class MotorcadeDeclareInfoQueryDncoder {

	
	private static Logger logger = Logger.getLogger(MotorcadeDeclareInfoQueryDncoder.class);
	
	String errorMessage = "";
	String responseCode ="";
	
	/**
     * 解码
     * @return 申报查询请求返回的XML格式串
     * @throws Exception
     */
    public void decode(String content, BLCIMotorcadeDeclare  blCIMotorcadeDeclare) 
    	throws Exception {  
    	

		logger.info("[上海团车申报信息查询返回报文]:"+content);
		
    	InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
        processBody(blCIMotorcadeDeclare, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
        
    }

    /**
     * 处理HEAD节
     * @param node Node
     * @throws Exception
     */
    protected void processHead(Node node) throws Exception {
        responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
        errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
    }    
    
    /**
     * 处理BODY节
     * @param node Node
     * @throws Exception
     */
    protected void processBody(BLCIMotorcadeDeclare  blCIMotorcadeDeclare, Node node) 
	throws Exception {
        
    	if("1".equals(responseCode)){
    		processBasePart(blCIMotorcadeDeclare, XMLUtils.getChildNodeByTagName(node, "BASE_PART"));
		}else{
			 throw new Exception(errorMessage);
		}
	} 	
    
    /**
     * 处理VehicleList
     * @param blCIMotorcadeDeclare
     * @param node
     * @throws Exception
     */
    protected void processBasePart(BLCIMotorcadeDeclare  blCIMotorcadeDeclare, Node node) throws Exception{
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "VEHICLE_LIST");
    	for(int i=0; i<nodes.length; i++){
    		processVehicleList(blCIMotorcadeDeclare, nodes[i]);
    	}
    }
    
    
    /**
     * 处理BASE_PART节
     * @param node Node
     * @throws Exception
     */
    protected void processVehicleList(BLCIMotorcadeDeclare  blCIMotorcadeDeclare, Node node) 
    	throws Exception {  

    	String strGroupStartDate="";
    	String strGroupEndDate="";
        CIMotorcadeDeclareSchema ciMotorcadeDeclareSchema = new CIMotorcadeDeclareSchema();
        ciMotorcadeDeclareSchema.setGroupCode(XMLUtils.getChildNodeValue(node, "GROUP_CODE"));
        
        strGroupStartDate	= XMLUtils.getChildNodeValue(node, "GROUP_START_DATE");
        if(strGroupStartDate.length()>8){
        	strGroupStartDate = correctDate(strGroupStartDate.substring(0,8));
        }
        ciMotorcadeDeclareSchema.setGroupStartDate(strGroupStartDate);
        
        strGroupEndDate	= XMLUtils.getChildNodeValue(node, "GROUP_END_DATE");
        if(strGroupEndDate.length()>8){
        	strGroupEndDate = correctDate(strGroupEndDate.substring(0,8));
        }
        ciMotorcadeDeclareSchema.setGroupEndDate(strGroupEndDate);
        
        ciMotorcadeDeclareSchema.setCarMark(XMLUtils.getChildNodeValue(node, "CAR_MARK"));
        ciMotorcadeDeclareSchema.setRackNo(XMLUtils.getChildNodeValue(node, "RACK_NO"));
        ciMotorcadeDeclareSchema.setOwner(XMLUtils.getChildNodeValue(node, "OWNER"));
        ciMotorcadeDeclareSchema.setPolicyHolder(XMLUtils.getChildNodeValue(node, "POLICY_HOLDER"));
        ciMotorcadeDeclareSchema.setInsuredName(XMLUtils.getChildNodeValue(node, "INSURED_NAME"));
        ciMotorcadeDeclareSchema.setOperationFlag(XMLUtils.getChildNodeValue(node, "STATUS"));
        blCIMotorcadeDeclare.setArr(ciMotorcadeDeclareSchema);
    }

    
    /**
     * 纠正日期格式
     * @param dateString 8个字符的日期
     * @return YYYY-MM-DD形式的日期
     */
    private String correctDate(String dateString) {
    	if (dateString == null || dateString.equals("")){
    		return "";
    	}
        if (dateString.length() < 8) {
            throw new IllegalArgumentException(dateString + "的日期格式不对，必须为大于8位的纯数字的字符串");
        }
        String result = dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-"
                + dateString.substring(6, 8);
        return result;
    }
}
