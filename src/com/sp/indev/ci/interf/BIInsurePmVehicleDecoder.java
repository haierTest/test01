package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.schema.CIInsurePmVehicleSchema;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.sysframework.common.util.XMLUtils;

/**
 * ����XX��ѯ�������ݵĽ�����
 * add by fanjiangtao 2014-01-26
 */
public class BIInsurePmVehicleDecoder {
    
	private static Logger logger = Logger.getLogger(CIInsurePmVehicleDecoder.class);
	
    String errorMessage = "";
	
    /**
     * ����
     * @return XX��ѯ����XML��ʽ��
     * @throws Exception
     */
    public void decode(BLProposal blProposal,String content,String oprateSite)throws Exception
    {
		logger.info("[��ҵXXXXX���ܳ�����Ϣ��ѯ���ر���]:"+content);
    	InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        processHead(XMLUtils.getChildNodeByPath(document, "/Packet/Head"));
        processBody(blProposal, XMLUtils.getChildNodeByPath(document, "/Packet/Body"));
        saveRequestLog(blProposal,oprateSite);
    }

    /**
     * XXXXX��������־
     * @param blProposal
     * @throws Exception
     */
    protected void saveRequestLog(BLProposal blProposal,String operateSite) throws Exception
    {
        try 
        {
        	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String strInsertDate = sf.format(new Date());
        	for(int i = 0; i < blProposal.getBlCIInsurePmVehicle().getSize(); i++){
        		blProposal.getBlCIInsurePmVehicle().getArr(i).setValidFlag("1");
        		blProposal.getBlCIInsurePmVehicle().getArr(i).setInsertDate(strInsertDate);
        		blProposal.getBlCIInsurePmVehicle().getArr(i).setFlag("T");
        		blProposal.getBlCIInsurePmVehicle().getArr(i).setOperateSite(operateSite);
        	}
	        blProposal.getBlCIInsurePmVehicle().save();
        }
        catch (Exception e) 
        {
        	e.printStackTrace();
            throw e;
        }
    }

    /**
     * ����HEAD��
     * @param node Node
     * @throws Exception
     */
    protected void processHead(Node node) throws Exception {
        String responseCode = XMLUtils.getChildNodeValue(node, "ResponseCode");
        errorMessage = XMLUtils.getChildNodeValue(node, "ErrorMessage");
        if (!responseCode.equals("1")) {
            throw new Exception(errorMessage);
        }
    }

    /**
     * ����BODY��
     * @param node Node
     * @throws Exception
     */
    protected void processBody(BLProposal blProposal, Node node) 
    	throws Exception 
    {
        processVehicleList(blProposal, XMLUtils.getChildNodeByTagName(node, "VehicleList"));
    }




    /**
     * ����VEHICLE_LIST��
     * @param node Node
     */
    protected void processVehicleList(BLProposal blProposal, Node node)
    	throws Exception
    {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "VehicleData");
        
        for (int i = 0; i < nodes.length; i++) {
            processVehicle(blProposal, nodes[i]);
        }
    }

    /**
     * ����VEHICLE_DATA��
     * @param node Node
     */
    protected void processVehicle(BLProposal blProposal, Node node)
    	throws Exception
    {
    	try
        {
    		String strCarMark = XMLUtils.getChildNodeValue(node, "LicensePlateNo");
    		String strVehicleType = XMLUtils.getChildNodeValue(node, "LicensePlateType");
    		String strRackNo = XMLUtils.getChildNodeValue(node, "VIN");
    		String strEngineNo = XMLUtils.getChildNodeValue(node, "EngineNo");
    		String strVehicleStyle = XMLUtils.getChildNodeValue(node, "VehicleType");
    		String strPmUseType = XMLUtils.getChildNodeValue(node, "PmMotorUsageTypeCode");
    		String strIneffectualDate = XMLUtils.getChildNodeValue(node, "IneffectualDate");
    		String strRejectDate = XMLUtils.getChildNodeValue(node, "RejectDate");
    		String strVehicleRegisterDate = XMLUtils.getChildNodeValue(node, "FirstRegisterDate");
    		String strLastCheckDate = XMLUtils.getChildNodeValue(node, "LastCheckDate");
    		String strTransferDate = XMLUtils.getChildNodeValue(node, "TransferDate");
    		String strWholeWeight = XMLUtils.getChildNodeValue(node, "WholeWeight");
    		String strLimitLoadPerson = XMLUtils.getChildNodeValue(node, "RatedPassengerCapacity");
    		String strLimitLoad = XMLUtils.getChildNodeValue(node, "Tonnage");
    		String strDisplacement = XMLUtils.getChildNodeValue(node, "Displacement");
    		String strMadeFactory = XMLUtils.getChildNodeValue(node, "MadeFactory");
    		String strVehicleModel = XMLUtils.getChildNodeValue(node, "Model");
    		String strProducerType = XMLUtils.getChildNodeValue(node, "ProducerType");
    		String strVehicleBrand1 = XMLUtils.getChildNodeValue(node, "BrandCN");
    		String strVehicleBrand2 = XMLUtils.getChildNodeValue(node, "BrandEN");
    		String strHaulage = XMLUtils.getChildNodeValue(node, "Haulage");
    		String strVehicleColour = XMLUtils.getChildNodeValue(node, "VehicleColour");
    		String strSalePrice = XMLUtils.getChildNodeValue(node, "SalePrice");
    		String strPmFuelType = XMLUtils.getChildNodeValue(node, "PmFuelType");
    		String strStatus = XMLUtils.getChildNodeValue(node, "Status");
    		String strVehicleCategory = XMLUtils.getChildNodeValue(node, "MotorTypeCode");
    		String strOwnerName = XMLUtils.getChildNodeValue(node, "VehicleOwnerName");
    		
    		CIInsurePmVehicleSchema schema = new CIInsurePmVehicleSchema();  
    		schema.setCarMark(strCarMark);			
    		schema.setVehicleType(strVehicleType);			
    		schema.setRackNo(strRackNo);			
    		schema.setEngineNo(strEngineNo);			
    		schema.setVehicleStyle(strVehicleStyle);			
    		schema.setPmUseType(strPmUseType);			
    		schema.setIneffectualDate(correctDate(strIneffectualDate));			
    		schema.setRejectDate(correctDate(strRejectDate));			
    		schema.setVehicleRegisterDate(correctDate(strVehicleRegisterDate));			
    		schema.setLastCheckDate(correctDate(strLastCheckDate));			
    		schema.setTransferDate(correctDate(strTransferDate));			
    		schema.setWholeWeight(strWholeWeight);			
    		schema.setLimitLoadPerson(strLimitLoadPerson);			
    		schema.setLimitLoad(strLimitLoad);			
    		schema.setDisplacement(strDisplacement);			
    		schema.setMadeFactory(strMadeFactory);			
    		schema.setVehicleModel(strVehicleModel);			
    		schema.setProducerType(strProducerType);			
    		schema.setVehicleBrand1(strVehicleBrand1);			
    		schema.setVehicleBrand2(strVehicleBrand2);			
    		schema.setHaulage(strHaulage);			
    		schema.setVehicleColour(strVehicleColour);			
    		schema.setSalePrice(strSalePrice);			
    		schema.setPmFuelType(strPmFuelType);			
    		schema.setStatus(strStatus);			
    		schema.setVehicleCategory(strVehicleCategory);
    		schema.setOwnerName(strOwnerName);
    		blProposal.getBlCIInsurePmVehicle().setArr(schema);
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	throw e;
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

    /**
     * ��������ʱ���ʽ
     * @param dateString ����ǰ������ʱ��
     * @return �����������ʱ��
     */
    private String correctDateTime(String dateString) {
        String result = correctDate(dateString);
        if (dateString.length() >= 10) {
            result += " " + dateString.substring(8, 10);
        }
        if (dateString.length() >= 12) {
            result += ":" + dateString.substring(10, 12);
        }
        if (dateString.length() >= 14) {
            result += ":" + dateString.substring(12, 14);
        }
        return result;
    }
}
