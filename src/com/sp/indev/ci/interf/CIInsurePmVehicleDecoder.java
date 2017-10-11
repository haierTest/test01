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
 * 发送XX查询请求数据的解码器
 * add by fanjiangtao 2014-01-26
 */
public class CIInsurePmVehicleDecoder {
    
	private static Logger logger = Logger.getLogger(CIInsurePmVehicleDecoder.class);
	
    String errorMessage = "";
	
    /**
     * 解码
     * @return XX查询请求XML格式串
     * @throws Exception
     */
    public void decode(BLProposal blProposal,String content,String oprateSite)throws Exception
    {
		logger.info("[交管车辆信息查询返回报文]:"+content);
    	InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
        processBody(blProposal, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
        saveRequestLog(blProposal,oprateSite);
    }

    /**
     * XXXXX存请求日志
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
     * 处理HEAD节
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
     * 处理BODY节
     * @param node Node
     * @throws Exception
     */
    protected void processBody(BLProposal blProposal, Node node) 
    	throws Exception 
    {
        processVehicleList(blProposal, XMLUtils.getChildNodeByTagName(node, "VEHICLE_LIST"));
    }




    /**
     * 处理VEHICLE_LIST节
     * @param node Node
     */
    protected void processVehicleList(BLProposal blProposal, Node node)
    	throws Exception
    {
        Node[] nodes = XMLUtils.getChildNodesByTagName(node, "VEHICLE_DATA");
        
        for (int i = 0; i < nodes.length; i++) {
            processVehicle(blProposal, nodes[i]);
        }
    }

    /**
     * 处理VEHICLE_DATA节
     * @param node Node
     */
    protected void processVehicle(BLProposal blProposal, Node node)
    	throws Exception
    {
    	try
        {
    		String strCarMark = XMLUtils.getChildNodeValue(node, "CAR_MARK");
    		String strVehicleType = XMLUtils.getChildNodeValue(node, "VEHICLE_TYPE");
    		String strRackNo = XMLUtils.getChildNodeValue(node, "RACK_NO");
    		String strEngineNo = XMLUtils.getChildNodeValue(node, "ENGINE_NO");
    		String strVehicleStyle = XMLUtils.getChildNodeValue(node, "VEHICLE_STYLE");
    		String strPmUseType = XMLUtils.getChildNodeValue(node, "PM_USE_TYPE");
    		String strIneffectualDate = XMLUtils.getChildNodeValue(node, "INEFFECTUAL_DATE");
    		String strRejectDate = XMLUtils.getChildNodeValue(node, "REJECT_DATE");
    		String strVehicleRegisterDate = XMLUtils.getChildNodeValue(node, "VEHICLE_REGISTER_DATE");
    		String strLastCheckDate = XMLUtils.getChildNodeValue(node, "LAST_CHECK_DATE");
    		String strTransferDate = XMLUtils.getChildNodeValue(node, "TRANSFER_DATE");
    		String strWholeWeight = XMLUtils.getChildNodeValue(node, "WHOLE_WEIGHT");
    		String strLimitLoadPerson = XMLUtils.getChildNodeValue(node, "LIMIT_LOAD_PERSON");
    		String strLimitLoad = XMLUtils.getChildNodeValue(node, "LIMIT_LOAD");
    		String strDisplacement = XMLUtils.getChildNodeValue(node, "DISPLACEMENT");
    		String strMadeFactory = XMLUtils.getChildNodeValue(node, "MADE_FACTORY");
    		String strVehicleModel = XMLUtils.getChildNodeValue(node, "VEHICLE_MODEL");
    		String strProducerType = XMLUtils.getChildNodeValue(node, "PRODUCER_TYPE");
    		String strVehicleBrand1 = XMLUtils.getChildNodeValue(node, "VEHICLE_BRAND_1");
    		String strVehicleBrand2 = XMLUtils.getChildNodeValue(node, "VEHICLE_BRAND_2");
    		String strHaulage = XMLUtils.getChildNodeValue(node, "HAULAGE");
    		String strVehicleColour = XMLUtils.getChildNodeValue(node, "VEHICLE_COLOUR");
    		String strSalePrice = XMLUtils.getChildNodeValue(node, "SALE_PRICE");
    		String strPmFuelType = XMLUtils.getChildNodeValue(node, "PM_FUEL_TYPE");
    		String strStatus = XMLUtils.getChildNodeValue(node, "STATUS");
    		String strVehicleCategory = XMLUtils.getChildNodeValue(node, "VEHICLE_CATEGORY");
    		String strOwnerName = XMLUtils.getChildNodeValue(node, "OWNER_NAME");
    		
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

    /**
     * 纠正日期时间格式
     * @param dateString 修正前的日期时间
     * @return 修正后的日期时间
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
