package com.sp.interactive.interf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.nutz.ssdb4j.spi.Response;

import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.blsvr.tb.BLPrpTitemKind;
import com.sp.prpall.blsvr.tb.BLPrpTprofitDetail;
import com.sp.prpall.schema.PrpTitemCarExtSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;

public class CheckRuleInfoTM {

	public void checkRule(BLProposal blProposal, Response ruleInfo, Map paramMap) throws Exception {
		String strRuleInfo = ruleInfo.asString();
		Document doc = DocumentHelper.parseText(strRuleInfo);
		Element root = doc.getRootElement();
	    Map bodyMap = new HashMap();
	    List itemKindMap = new ArrayList();
	    parseDody(root.element("BODY"), bodyMap,itemKindMap);
		Element body = root.element("BODY");
		List kindLists = body.elements("ITEMKIND_LIST");
		Iterator iterator = kindLists.iterator();
		while(iterator.hasNext()){
			Element itemKindList = (Element) iterator.next();
			parseitemKind(itemKindList,bodyMap,itemKindMap);
		}
		if(blProposal.getBLPrpTmain().getArr(0).getRiskCode().equals("0507")){
			
			String NOUSEPROFITCODECI = (String) bodyMap.get("NOUSEPROFITCODECI"); 
			blProposal.getBLPrpTitemCarExt().getArr(0).setNouseProfitCode(NOUSEPROFITCODECI);
			
			String ISMODIFYGLASSSPECIES = (String)bodyMap.get("ISMODIFYGLASSSPECIES") ;
			if(ISMODIFYGLASSSPECIES!=null&&ISMODIFYGLASSSPECIES.toLowerCase().equals("false")){
				 BLPrpTitemKind itemKind = blProposal.getBLPrpTitemKind();
				 for(int i = 0;i<itemKind.getSize();i++){
					  itemKind.getArr(i).setModeCode((String)bodyMap.get("GLASSSPECIES"));
				 }
			}
		}else{
			
			
			
			String CARPRICE = blProposal.getBLPrpTitemCar().getArr(0).getPurchasePrice();
			
			
			
			
				
			
			
			
				

			
			
			
			String NOUSEPROFITCODEBI = (String) bodyMap.get("NOUSEPROFITCODEBI");
			blProposal.getBLPrpTitemCarExt().getArr(0).setNouseProfitCode(NOUSEPROFITCODEBI);
			
			String SPECIALISTRATE = "";
			
			String SPECIALISTSTATUS  = "";
			
			String ISMODIFYGLASSSPECIES = (String)bodyMap.get("ISMODIFYGLASSSPECIES") ;
			if(ISMODIFYGLASSSPECIES.toLowerCase().equals("false")){
				 BLPrpTitemKind itemKind = blProposal.getBLPrpTitemKind();
				 for(int i = 0;i<itemKind.getSize();i++){
					  itemKind.getArr(i).setModeCode((String)bodyMap.get("GLASSSPECIES"));
				 }
			}
		}
		
		 BLPrpTitemKind itemKind = blProposal.getBLPrpTitemKind();
		 List list1 = itemKindMap;
		for(int i = 0 ; i<itemKind.getSize();i++){
			PrpTitemKindSchema prpTitemKindSchema = itemKind.getArr(i);
			Iterator it = list1.iterator();
			
			List list = new ArrayList();
			while (it.hasNext()){
				Map kindDataMap = (Map) it.next();
				if(prpTitemKindSchema.getKindCode().equals(kindDataMap.get("KINDCODE"))){
					
					String ISINSURE = (String) kindDataMap.get("ISINSURE");
					
					String ISUPINSURE = (String) kindDataMap.get("ISUPINSURE");
					if(ISINSURE.toLowerCase().equals("true") && ISUPINSURE.toLowerCase().equals("false")){
						
						String AMOUNT = (String) kindDataMap.get("AMOUNT");
						String ISUPAMOUNT = (String) kindDataMap.get("ISUPAMOUNT");
						
						String ISINSUREPER = (String) kindDataMap.get("ISINSUREPER");
						String ISUPINSUREPER = (String) kindDataMap.get("ISINSUREPER");
						
						String VALUE = (String) kindDataMap.get("VALUE");
						String ISUPVALUE = (String) kindDataMap.get("VALUE");
						if(ISUPAMOUNT.toLowerCase().equals("fasle")){
							blProposal.getBLPrpTitemKind().getArr(i).setAmount(AMOUNT);
						}
						if(ISUPVALUE.toLowerCase().equals("fasle")){
							blProposal.getBLPrpTitemKind().getArr(i).setValue(VALUE);
						}
						String Flag = itemKind.getArr(i).getFlag();
						if(ISUPINSUREPER.toLowerCase().equals("fasle")){
							
						String Added_MPlan = ISINSUREPER.toLowerCase().equals("false")?"0":"1";
						if(Added_MPlan != null && !Added_MPlan.equals("")){
							int length = Flag.length();
							if(length == 5){
								Flag = Flag.substring(0,4)+Added_MPlan;
							}else if(length > 5){
								String str05 = Flag.substring(0,4);
								String str6_ = Flag.substring(5);
								Added_MPlan.concat(str6_);
								Flag = str05 + Added_MPlan.concat(str6_);
							}else{
								for(int m = Flag.length(); m<4;m++){
									Flag =Flag+" "; 
								}
								Flag = Flag + Added_MPlan;
							}
						}
					}	
						 blProposal.getBLPrpTitemKind().getArr(i).setFlag(Flag);
					}
					
                    if(ISINSURE.toLowerCase().equals("false") && ISUPINSURE.toLowerCase().equals("false")){
                    	itemKind.remove(i);
                    	list.add("不可投XX别，已删除......"+itemKind.getArr(i).getKindCode()+",");
					}
				}
				
			}
			
			if(list.size()>0){
				paramMap.put("INSUREKINDCODE", list);
			}





		}
		
	}
	private void parseitemKind(Element itemKindList, Map bodyMap, List itemKindMap) {
		Map itemKind = new HashMap();
		Iterator iterator = itemKindList.elementIterator();
		while(iterator.hasNext()){
			Element kindData = (Element) iterator.next();
			String KINDCODE = kindData.elementText("KINDCODE");
			String ISINSURE=kindData.elementText("ISINSURE");
			String ISUPINSURE=kindData.elementText("ISUPINSURE");
			String AMOUNT=kindData.elementText("AMOUNT");
			String ISUPAMOUNT=kindData.elementText("ISUPAMOUNT");
			String ISINSUREPER=kindData.elementText("ISINSUREPER");
			String ISUPINSUREPER=kindData.elementText("ISUPINSUREPER");
			String VALUE=kindData.elementText("VALUE");
			String ISUPVALUE=kindData.elementText("ISUPVALUE");
			
			itemKind.put("KINDCODE",KINDCODE);
			itemKind.put("ISINSURE",ISINSURE);
			itemKind.put("ISUPINSURE",ISUPINSURE);
			itemKind.put("AMOUNT",AMOUNT);
			itemKind.put("ISUPAMOUNT",ISUPAMOUNT);
			itemKind.put("ISINSUREPER",ISINSUREPER);
			itemKind.put("ISUPINSUREPER",ISUPINSUREPER);
			itemKind.put("VALUE",VALUE);
			itemKind.put("ISUPVALUE",ISUPVALUE);
		}
		itemKindMap.add(itemKind);
	}

	private void parseDody(Element body, Map bodyMap, List itemKindMap) {
		String	INFOTRANSNO	= body.elementText("INFOTRANSNO");
		String	OPERATESITE	= body.elementText("OPERATESITE");
		String	CARPRICE =	body.elementText("CARPRICE");
		String	PURCHASEPRICE =	body.elementText("PURCHASEPRICE");

		
		System.out.println("CARPRICE  == " + CARPRICE);
		String	TAXRELIFFLAG	= body.elementText("TAXRELIFFLAG");
		String	FREERATE = body.elementText("FREERATE");
		String	FUELTYPE = 	body.elementText("FUELTYPE");
		String	CUTRATEREASON = body.elementText("CUTRATEREASON");
		String	NOUSEPROFITCODECI = body.elementText("NOUSEPROFITCODECI");
		String	NOUSEPROFITCODEBI = body.elementText("NOUSEPROFITCODEBI");
		String	STARTDATEPRECISION = body.elementText("STARTDATEPRECISION");
		String	ISCOMPLIANCECI = body.elementText("ISCOMPLIANCECI");
		String	ISCOMPLIANCEBI = body.elementText("ISCOMPLIANCEBI");
		String	MESSAGEINFOCI = body.elementText("MESSAGEINFOCI");
		String	MESSAGEINFOBI = body.elementText("MESSAGEINFOBI");
		String	SPECIALISTRATE = body.elementText("SPECIALISTRATE");
		String	SPECIALISTSTATUS = body.elementText("SPECIALISTSTATUS");
		String	GLASSSPECIES = body.elementText("GLASSSPECIES");
		String	ISMODIFYGLASSSPECIES = body.elementText("ISMODIFYGLASSSPECIES");
		bodyMap.put("PURCHASEPRICE",PURCHASEPRICE);
		bodyMap.put("INFOTRANSNO",INFOTRANSNO);
		bodyMap.put("OPERATESITE",OPERATESITE);
		bodyMap.put("CARPRICE",CARPRICE);
		bodyMap.put("TAXRELIFFLAG",TAXRELIFFLAG);
		bodyMap.put("FREERATE",FREERATE);
		bodyMap.put("FUELTYPE",FUELTYPE);
		bodyMap.put("CUTRATEREASON",CUTRATEREASON);
		bodyMap.put("NOUSEPROFITCODECI",NOUSEPROFITCODECI);
		bodyMap.put("NOUSEPROFITCODEBI",NOUSEPROFITCODEBI);
		bodyMap.put("STARTDATEPRECISION",STARTDATEPRECISION);
		bodyMap.put("ISCOMPLIANCECI",ISCOMPLIANCECI);
		bodyMap.put("ISCOMPLIANCEBI",ISCOMPLIANCEBI);
		bodyMap.put("MESSAGEINFOCI",MESSAGEINFOCI);
		bodyMap.put("MESSAGEINFOBI",MESSAGEINFOBI);
		bodyMap.put("SPECIALISTRATE",SPECIALISTRATE);
		bodyMap.put("MESSAGEINFOCI",MESSAGEINFOCI);
		bodyMap.put("SPECIALISTSTATUS",SPECIALISTSTATUS);
		bodyMap.put("GLASSSPECIES",GLASSSPECIES);
		bodyMap.put("ISMODIFYGLASSSPECIES",ISMODIFYGLASSSPECIES);

	}
	
	
}
