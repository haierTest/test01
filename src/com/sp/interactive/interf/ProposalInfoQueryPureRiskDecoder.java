package com.sp.interactive.interf;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import com.sp.indiv.carplatform.schema.CarModelQuerySchema;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.schema.PrpTitemCarExtSchema;
import com.sp.prpall.schema.PrpTitemCarSchema;
import com.sp.prpall.schema.PrpTmainSchema;

public class ProposalInfoQueryPureRiskDecoder {

	

	public void decode(Document doc,  Map paramMap, BLProposal  bLProposal)throws Exception{
		Element root = doc.getRootElement();
		


		parseHead(root.element("HEAD"), paramMap,bLProposal);
        parseBody(root.element("BODY"), paramMap, bLProposal);
	}



/**
 * 处理HEAD节
 * @param head
 * @param paramMap
 * @param BLProposalMap
 * @throws Exception
 */
public void parseHead(Element head,  Map paramMap, BLProposal bLProposal)throws Exception{
	Element TRANSTYPE = head.element("REQUESTTYPE");
	Element REQUESTTYPECODE = head.element("REQUESTTYPECODE");
	Element COMCODE = head.element("COMCODE");
	paramMap.put("REQUESTTYPE", TRANSTYPE.getText());
	paramMap.put("REQUESTTYPECODE", REQUESTTYPECODE.getText());
	paramMap.put("COMCODE", COMCODE.getText());
	
}
/**
 * 处理BODY节
 * @param body
 * @param paramMap
 * @param BLProposalMap
 * @throws Exception
 */

public void parseBody(Element body,Map paramMap,  BLProposal bLProposal)throws Exception{
	String VEHICLECODE = body.elementText("VEHICLECODE");
    paramMap.put("VEHICLECODE", VEHICLECODE);
	if(body.element("CAR_LIST")!=null){
		
		parseCarModelInfo(body.element("CAR_LIST"),   bLProposal);
	}
}

/**
 * @author wsy 201503-30
 * @param NewEngageList
 * @param paramMap
 * @param bLProposalMap
 */



/**
 * 
 * @param CAR_LIST
 * @param blProposal
 * @throws Exception 
 */
	private void parseCarModelInfo(Element CAR_LIST, BLProposal blProposal) throws Exception {
		Iterator iterator = CAR_LIST.elementIterator();
	
	while(iterator.hasNext()){
		
		
	    Element CAR_DATA = (Element)iterator.next();
		String license_No = CAR_DATA.elementText("LICENSENO");
		String license_Type  = CAR_DATA.elementText("LICENSETYPE");
		String engin_No  = CAR_DATA.elementText("ENGINENO");
		String VINNO  = CAR_DATA.elementText("VINNO");
		String use_Type  = CAR_DATA.elementText("USENATURECODE");
		String car_Kind_Code  = CAR_DATA.elementText("CARKINDCODE");
		String enroll_Date  = CAR_DATA.elementText("ENROLLDATE");
		String startDateBI  = CAR_DATA.elementText("STARTDATEBI");
		String vehicle_Tonnage  = CAR_DATA.elementText("TONCOUNT");
		String exhausts_cale=CAR_DATA.elementText("EXHAUSTSCALE");
		String seat_Count  = CAR_DATA.elementText("SEATCOUNT");
		String brand_Name  = CAR_DATA.elementText("BRANDNAME");
		String pur_Chaseprice  = CAR_DATA.elementText("PURCHASEPRICE");
       












		
		
		 
	     PrpTitemCarSchema prpTitemCarSchema = new PrpTitemCarSchema();
		 prpTitemCarSchema.setLicenseNo(license_No);
		 prpTitemCarSchema.setLicenseKindCode(license_Type);
		 prpTitemCarSchema.setEngineNo(engin_No);
		 prpTitemCarSchema.setVINNo(VINNO);
		 prpTitemCarSchema.setUseNatureCode(use_Type);
		 prpTitemCarSchema.setCarKindCode(car_Kind_Code);
		 prpTitemCarSchema.setEnrollDate(enroll_Date);
		 prpTitemCarSchema.setTonCount(vehicle_Tonnage);
		 prpTitemCarSchema.setExhaustScale(exhausts_cale);
		 prpTitemCarSchema.setSeatCount(seat_Count);
		 prpTitemCarSchema.setBrandName(brand_Name);
		 prpTitemCarSchema.setPurchasePrice(pur_Chaseprice);
		 PrpTmainSchema prpTmainSchema=new PrpTmainSchema();
		 prpTmainSchema.setStartDate(startDateBI);
		
		 blProposal.getBLPrpTitemCar().setArr(prpTitemCarSchema);
		 blProposal.getBLPrpTmain().setArr(prpTmainSchema);
		
    }
  }	
}