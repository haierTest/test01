package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCIMotorcadeDeclare;
import com.sp.indiv.ci.dbsvr.DBCIMotorcadeDeclare;
import com.sp.indiv.ci.schema.CIMotorcadeDeclareSchema;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.database.DbPool;

/**
 * �����ų�ҵ���걨��ѯ�������ݵĽ�����
 */
public class ProposalDeclareQueryDecoder{
	
	
	
	private static Logger logger = Logger.getLogger(ProposalDeclareQueryDecoder.class);
	
	
	String errorMessage = "";
	String responseCode ="";
	
	/**
     * ����
     * @return �걨��ѯ���󷵻ص�XML��ʽ��
     * @throws Exception
     */
    public void decode(DbPool dbpool, String content, BLCIMotorcadeDeclare  blCIMotorcadeDeclareBack) 
    	throws Exception {  
    	
    	
		logger.info("[�Ϻ��ų��걨���ر���]:"+content);
		
		
    	InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
        processBody(blCIMotorcadeDeclareBack, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
        
        if(responseCode!=""&&!"0".equals(responseCode)){
        	if(blCIMotorcadeDeclareBack.getArr(0).getOperationFlag()!=null&&
        		!"1".equals(blCIMotorcadeDeclareBack.getArr(0).getOperationFlag())){
        		saveSuccessDeclareInfor(dbpool,blCIMotorcadeDeclareBack);
        	}
        }
        
        
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
    protected void processBody(BLCIMotorcadeDeclare  blCIMotorcadeDeclareBack, Node node) 
	throws Exception {
        
    	try {
    		processBasePart(blCIMotorcadeDeclareBack, XMLUtils.getChildNodeByTagName(node, "BASE_PART"));
		} catch (Exception e) {
			 throw new Exception(errorMessage);
		}
	} 	
    
    /**
     * ����BASE_PART��
     * @param node Node
     * @throws Exception
     */
    protected void processBasePart(BLCIMotorcadeDeclare  blCIMotorcadeDeclareBack,Node node) 
    	throws Exception {  
        String strPassVehicle ="";
        String strFailVehicle="";
        Node[] nodes=null;
    	String strCarMark="";
    	String strRackNo="";
    	String strErroMessage="";
    	String strGroupCode="";
    	String strGroupStartDate="";
    	String strGroupEndDate="";
        CIMotorcadeDeclareSchema ciMotorcadeDeclareSchema=null;
        if(responseCode!=null&&!"".equals(responseCode)){
        	if("1".equals(responseCode)){  
                
        		strGroupCode		= XMLUtils.getChildNodeValue(node, "GROUP_CODE");
        		strGroupStartDate	= XMLUtils.getChildNodeValue(node, "GROUP_START_DATE");
        		strGroupEndDate	= XMLUtils.getChildNodeValue(node, "GROUP_END_DATE");
                if(strGroupStartDate.length()>8){
                	strGroupStartDate = correctDate(strGroupStartDate.substring(0,8));
                }
                if(strGroupEndDate.length()>8){
                	strGroupEndDate = correctDate(strGroupEndDate.substring(0,8));
                }
                strPassVehicle= XMLUtils.getChildNodeValue(node, "PASS_VEHICLE");  
        		for(int i=0; i<blCIMotorcadeDeclareBack.getSize(); i++){
        			ciMotorcadeDeclareSchema=blCIMotorcadeDeclareBack.getArr(i);
        			ciMotorcadeDeclareSchema.setPassVehicle(strPassVehicle);
        			ciMotorcadeDeclareSchema.setGroupCode(strGroupCode);
        			ciMotorcadeDeclareSchema.setGroupStartDate(strGroupStartDate);
        			ciMotorcadeDeclareSchema.setGroupEndDate(strGroupEndDate);
        			ciMotorcadeDeclareSchema.setResponseCode(responseCode);
        		}
        	}else if("2".equals(responseCode)){ 
                
                
        		strGroupCode 		= XMLUtils.getChildNodeValue(node, "GROUP_CODE");
        		strGroupStartDate 	= XMLUtils.getChildNodeValue(node, "GROUP_START_DATE");
        		strGroupEndDate 	= XMLUtils.getChildNodeValue(node, "GROUP_END_DATE");
                if(strGroupStartDate.length()>8){
                	strGroupStartDate = correctDate(strGroupStartDate.substring(0,8));
                }
                if(strGroupEndDate.length()>8){
                	strGroupEndDate = correctDate(strGroupEndDate.substring(0,8));
                }
                strPassVehicle= XMLUtils.getChildNodeValue(node, "PASS_VEHICLE");  
                
            	nodes = XMLUtils.getChildNodesByTagName(node, "SUCCESS_LIST");
                for(int i=0;i<nodes.length;i++){
	              	strCarMark = XMLUtils.getChildNodeValue(nodes[i], "CAR_MARK");  
	                strRackNo  = XMLUtils.getChildNodeValue(nodes[i], "RACK_NO");   
	                for(int j=0; j<blCIMotorcadeDeclareBack.getSize(); j++){
	                	ciMotorcadeDeclareSchema=blCIMotorcadeDeclareBack.getArr(j);
	                	
	                	ciMotorcadeDeclareSchema.setResponseCode(responseCode);
	                	
	                	if(strRackNo.equals(ciMotorcadeDeclareSchema.getRackNo())){
	                    	
	                		if(!"".equals(strCarMark)){
	                			ciMotorcadeDeclareSchema.setCarMark(strCarMark);
	                		}
	                		
	                		ciMotorcadeDeclareSchema.setRackNo(strRackNo);
	                		ciMotorcadeDeclareSchema.setPassVehicle(strPassVehicle);
	        				ciMotorcadeDeclareSchema.setGroupCode(strGroupCode);
	        				ciMotorcadeDeclareSchema.setGroupStartDate(strGroupStartDate);
	        				ciMotorcadeDeclareSchema.setGroupEndDate(strGroupEndDate);
	                	}
	                }
                }
                
                node  = XMLUtils.getChildNodeByTagName(node, "FAILURE_GROUP");
                strFailVehicle=XMLUtils.getChildNodeValue(node, "FAILURE_VEHICLE");  
                nodes = XMLUtils.getChildNodesByTagName(node, "FAILURE_LIST");
                for (int i = 0; i < nodes.length; i++) {
                	strCarMark=XMLUtils.getChildNodeValue(nodes[i], "CAR_MARK");          
                	strRackNo=XMLUtils.getChildNodeValue(nodes[i], "RACK_NO");             
                	strErroMessage=XMLUtils.getChildNodeValue(nodes[i], "ERROR_MESSAGE");  
                	for(int j=0; j<blCIMotorcadeDeclareBack.getSize(); j++){
	                	ciMotorcadeDeclareSchema=blCIMotorcadeDeclareBack.getArr(j);
	                	
	                	ciMotorcadeDeclareSchema.setResponseCode(responseCode);
	                	
	                	if(strRackNo.equals(ciMotorcadeDeclareSchema.getRackNo())){
	                		
	                		if(!"".equals(strCarMark)){
	                			ciMotorcadeDeclareSchema.setCarMark(strCarMark);
	                		}
	                    	
	                    	ciMotorcadeDeclareSchema.setRackNo(strRackNo);
	                    	ciMotorcadeDeclareSchema.setErrorMessage(strErroMessage);
	                    	ciMotorcadeDeclareSchema.setFailureVehicle(strFailVehicle);
	                    	ciMotorcadeDeclareSchema.setGroupCode(strGroupCode);
	        				ciMotorcadeDeclareSchema.setGroupStartDate(strGroupStartDate);
	        				ciMotorcadeDeclareSchema.setGroupEndDate(strGroupEndDate);
	                	}
	                }
				}
        	}else if("0".equals(responseCode)){
        		try {
        				
	                	node  = XMLUtils.getChildNodeByTagName(node, "FAILURE_GROUP");
	                	nodes = XMLUtils.getChildNodesByTagName(node, "FAILURE_LIST");
		                for (int i = 0; i < nodes.length; i++) {
		                	strCarMark=XMLUtils.getChildNodeValue(nodes[i], "CAR_MARK");          
		                	strRackNo=XMLUtils.getChildNodeValue(nodes[i], "RACK_NO");             
		                	strErroMessage=XMLUtils.getChildNodeValue(nodes[i], "ERROR_MESSAGE");  
		                	for(int j=0; j<blCIMotorcadeDeclareBack.getSize(); j++){
			                	ciMotorcadeDeclareSchema=blCIMotorcadeDeclareBack.getArr(j);
			                	
			                	ciMotorcadeDeclareSchema.setResponseCode(responseCode);
			                	
			                	if(strRackNo.equals(ciMotorcadeDeclareSchema.getRackNo())){
			                		
			                		if(!"".equals(strCarMark)){
			                			ciMotorcadeDeclareSchema.setCarMark(strCarMark);
			                		}
			                		
			                    	ciMotorcadeDeclareSchema.setRackNo(strRackNo);
			                    	ciMotorcadeDeclareSchema.setErrorMessage(strErroMessage);
			                	}
			                }
						}
        			} catch (Exception e) {
        				throw new Exception(strErroMessage);	
        			}    
        	}else{
        		throw new Exception(strErroMessage);
        	}
        }else{
    		throw new Exception("�������ʹ������!");
    	}
    }

    /**
     * 
     * @param blCIMotorcadeDeclareBack
     * @throws Exception
     */
    protected void saveSuccessDeclareInfor(DbPool dbpool, BLCIMotorcadeDeclare  blCIMotorcadeDeclareBack) throws Exception{
    	CIMotorcadeDeclareSchema ciMotorcadeDeclareSchema = null;
    	DBCIMotorcadeDeclare dbCIMotorcadeDeclare = new DBCIMotorcadeDeclare();
    	ResultSet resultSet=null;
		for(int i=0; i<blCIMotorcadeDeclareBack.getSize(); i++){
			ciMotorcadeDeclareSchema = blCIMotorcadeDeclareBack.getArr(i);
			if("".equals(ciMotorcadeDeclareSchema.getErrorMessage())){
				resultSet = dbpool.query("select Seq_CiMotorcadeDeclare_SN.nextval from dual");
	            if(resultSet.next()){
	            	ciMotorcadeDeclareSchema.setSerialNo(resultSet.getString(1));
	                resultSet.close();
	            }
				dbCIMotorcadeDeclare.setSchema(ciMotorcadeDeclareSchema);
		        dbCIMotorcadeDeclare.insert(dbpool);
			}
		}
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
