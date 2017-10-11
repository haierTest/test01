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
     * ����
     * @return �걨��ѯ���󷵻ص�XML��ʽ��
     * @throws Exception
     */
    public void decode(String content, BLCIMotorcadeDeclare  blCIMotorcadeDeclare) 
    	throws Exception {  
    	

		logger.info("[�Ϻ��ų��걨��Ϣ��ѯ���ر���]:"+content);
		
    	InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
        processBody(blCIMotorcadeDeclare, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
        
    }

    /**
     * ����HEAD��
     * @param node Node
     * @throws Exception
     */
    protected void processHead(Node node) throws Exception {
        responseCode = XMLUtils.getChildNodeValue(node, "RESPONSE_CODE");
        errorMessage = XMLUtils.getChildNodeValue(node, "ERROR_MESSAGE");
    }    
    
    /**
     * ����BODY��
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
     * ����VehicleList
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
     * ����BASE_PART��
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
     * �������ڸ�ʽ
     * @param dateString 8���ַ�������
     * @return YYYY-MM-DD��ʽ������
     */
    private String correctDate(String dateString) {
    	if (dateString == null || dateString.equals("")){
    		return "";
    	}
        if (dateString.length() < 8) {
            throw new IllegalArgumentException(dateString + "�����ڸ�ʽ���ԣ�����Ϊ����8λ�Ĵ����ֵ��ַ���");
        }
        String result = dateString.substring(0, 4) + "-" + dateString.substring(4, 6) + "-"
                + dateString.substring(6, 8);
        return result;
    }
}
