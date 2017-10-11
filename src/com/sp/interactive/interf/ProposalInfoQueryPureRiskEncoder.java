package com.sp.interactive.interf;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.sp.indiv.bi.blsvr.BLCIInsureCarModel;
import com.sp.indiv.bi.schema.CIInsureCarModelSchema;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.utility.error.UserException;

public class ProposalInfoQueryPureRiskEncoder {

	/**
	 * 返回报文的编译
	 * 
	 * @param doc
	 * @param paramMap
	 * @param blProposalMap
	 * @throws Exception
	 * @throws Exception
	 */
	public String encode(Map paramMap, Map  blProposalMap) throws Exception {

		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("PACKET");
		root.addAttribute("type", "RESPONSE");
		root.addAttribute("version", "1.0");

		addHead(root, paramMap, blProposalMap);
		addBody(root, paramMap, blProposalMap);
		return formateXml(doc.asXML());

	}

	/**
	 * 报文头部信息
	 * 
	 * @param root
	 * @param paramMap
	 * @param blProposalMap
	 * @throws Exception
	 */
	public void addHead(Element root, Map paramMap, Map  blProposalMap)
			throws Exception {
		Element head = root.addElement("HEAD");

		Element REQUESTTYPE = head.addElement("TRANSNO");
		Element transexeDate = head.addElement("TRANSEXEDATE");
		Element transexeTime = head.addElement("TRANSEXETIME");
		Element responseTypeCode = head.addElement("RESPONSETYPECODE");
		Element responseCode = head.addElement("ERRORCODE");
		Element responseMessage = head.addElement("ERRORMESSAGE");
		String requestType = (String) paramMap.get("REQUESTTYPE");
		String REQUESTTYPECODE = (String) paramMap.get("REQUESTTYPECODE");
		REQUESTTYPE.addText(requestType);
		
		SimpleDateFormat formate = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String timeString = formate.format(currentTime);
        
		SimpleDateFormat formatedate = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		String dateString = formatedate.format(currentDate);
		responseMessage.addText("成功");
		responseCode.addText("000000");
		responseTypeCode.addText(REQUESTTYPECODE);
		transexeDate.addText(dateString);
		transexeTime.addText(timeString);

	}

	public String formateXml(String xmlStr) throws IOException,
			DocumentException {
		String encoding = "GBK";
		Document doc = DocumentHelper.parseText(xmlStr);

		StringWriter writer = new StringWriter();
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setTrimText(false);
		format.setEncoding(encoding);
		format.setExpandEmptyElements(true);

		XMLWriter xmlwriter = new XMLWriter(writer, format);
		xmlwriter.write(doc);
		
		return writer.toString().replaceAll("&lt;", "＜");

	}

	/**
	 * 报文体信息
	 * 
	 * @param root
	 * @param paramMap
	 * @param blProposalMap
	 * @throws Exception
	 */
	public void addBody(Element root, Map paramMap, Map  blProposalMap)
			throws Exception {


		BLProposal bLProposal=new BLProposal();
		bLProposal=(BLProposal)blProposalMap.get("blProposal");
		BLCIInsureCarModel bLCIInsureCarModel=bLProposal.getBlCIInsureCarModel();
		Element body = root.addElement("BODY");
		Element vehiCleCode = body.addElement("VEHICLECODE");
		String strvehiCleCode = (String) paramMap.get("VEHICLECODE");
		vehiCleCode.addText(strvehiCleCode == null ? "" : strvehiCleCode);
		processCoveragePremiumItem(body, bLCIInsureCarModel);
	}

	protected void processCoveragePremiumItem(Element body,
			BLCIInsureCarModel bLCIInsureCarModel) throws Exception {
		Element carList = body.addElement("PURERISKPREMIUM_LIST");
		if (bLCIInsureCarModel != null) {
			int lengthBI = bLCIInsureCarModel.getSize();
			if (lengthBI > 0) {
				for (int i = 0; i < lengthBI; i++) {
					
					Element PureRiskPreData = carList
							.addElement("PURERISKPREMIUM_DATA");
					CIInsureCarModelSchema CIInsureCarModelSchema = bLCIInsureCarModel
							.getArr(i);
					addPureRiskPreData(PureRiskPreData, CIInsureCarModelSchema);
				}
			}
		}

	}

	public void addPureRiskPreData(Element PureRiskPreData,
			CIInsureCarModelSchema cIInsureCarModelSchema) throws Exception {
		
		Element LICENSENO = PureRiskPreData.addElement("KINDCODE");
		String CoverageCode = cIInsureCarModelSchema.getCoverageCode();
		LICENSENO.addText(CoverageCode);
		
		Element LICENSETYPE = PureRiskPreData.addElement("PURERISKPREMIUM");
		String strPureRiskPremium = cIInsureCarModelSchema.getPureRiskPremium();
		LICENSETYPE.addText(strPureRiskPremium);
		
		Element PURERISKPREMIUMFLAG=PureRiskPreData.addElement("PURERISKPREMIUMFLAG");
		String strPureRiskPemiumFlag=cIInsureCarModelSchema.getPureRiskPremiumFlag();
		PURERISKPREMIUMFLAG.addText(strPureRiskPemiumFlag);
	}

	
	public String encodeException(Map paramMap, Map blProposalMap, Exception e)
			throws IOException, DocumentException, UserException {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("PACKET");
		root.addAttribute("type", "RESPONSE");
		root.addAttribute("version", "1.0");

		Element head = root.addElement("HEAD");
		Element REQUESTTYPE = head.addElement("REQUESTTYPE");
		String requestType = (String) paramMap.get("REQUESTTYPE");
		REQUESTTYPE.addText(requestType);

		Element responseCode = head.addElement("RESPONSECODE");
		Element responseMessage = head.addElement("RESPONSEMESSAGE");
		if ((String) paramMap.get("Error_Code") == null
				|| "".equals((String) paramMap.get("Error_Code"))) {
			responseCode.addText("100000");
			responseMessage.addText(e.getMessage());
		} else {
			responseCode.addText((String) paramMap.get("Error_Code"));
			responseMessage.addText((String) paramMap.get("Error_Message"));
		}

		return formateXml(doc.asXML());
	}

}