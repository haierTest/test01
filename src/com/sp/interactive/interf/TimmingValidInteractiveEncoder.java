package com.sp.interactive.interf;

import java.io.IOException;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.payment.blsvr.jf.BLPrpJplanFee;
import com.sp.payment.blsvr.jf.BLPrpJplanFeePre;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.jf.BLPrpJpoaInfo;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.schema.PrpCcarshipTaxSchema;
import com.sp.prpall.schema.PrpCitemKindSchema;
import com.sp.prpall.schema.PrpCmainSchema;
import com.sp.prpall.schema.PrpJpoaInfoSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.utiall.dbsvr.DBUtiKey;
import com.sp.utility.string.Str;

/**
 * XX确认定时发送
 * @author qilin
 * */

public class TimmingValidInteractiveEncoder {
	
	public String encode(Map paramMap,List policyList)throws Exception 
	{
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("Packet");
		root.addAttribute("type", "REQUEST");
		root.addAttribute("version", "1.0");

		addHead(root,paramMap,policyList);
		addBody(root,paramMap,policyList);
		

		return formateXml(doc.asXML());
	}
	/**
	 * 处理HEAD节点
	 * */
	private void addHead(Element root,Map paramMap,List policyList) {
		Element head = root.addElement("Request_Header");
		 
		 SimpleDateFormat formate= new SimpleDateFormat("HH:mm:ss");
		 Date currentTime=new Date();
		 String timeString=formate.format(currentTime);

		 
		 SimpleDateFormat formatedate= new SimpleDateFormat("yyyy-MM-dd");
		 Date currentDate=new Date();
		 String dateString=formatedate.format(currentDate);
		 
		Element TRANS_NO = head.addElement("Trans_No");
		String transNo = getOrderNo();
		TRANS_NO.addText(transNo);
         
		
		Element TransExe_Date = head.addElement("TransExe_Date");
		String time = dateString;
		TransExe_Date.addText(time);
		
		Element TransExe_Time = head.addElement("TransExe_Time");
		String time1=timeString;
		TransExe_Time.addText(time1);
		
		Element REQUESTTYPE = head.addElement("Trans_Type");
		REQUESTTYPE.addText("04");
		
		Element Request_Type_Code = head.addElement("Request_Type_Code");
		String requestTypeCode = "01";
		Request_Type_Code.addText(requestTypeCode);
		
		
	}

	/**
	 * 处理BODY节点
	 * @throws Exception 
	 * */
	private void addBody(Element root, Map paramMap,List policyList) throws Exception {
		Element body = root.addElement("Body").addElement("Base_Part");
		addPolicyList(body,policyList,paramMap); 
		Element CompanyCode = body.addElement("Company_Code");
		String companyCode = "A";
		CompanyCode.addText(companyCode);
		Element TotalPayment = body.addElement("Total_Payment");
		String totalPayment = (String) paramMap.get("Sum_Money");
		TotalPayment.addText(totalPayment);
		
	}
	
	private void addPolicyList(Element body, List policyList,Map paramMap) throws Exception {

    		Element Policy_List = body.addElement("Policy_List");
    		int length = policyList.size();
    		if (length > 0) {
    			Double sumMoney1=0.00;
	    		for (int i = 0; i < length; i++) {
	    			BLPolicy blPolicy =(BLPolicy) policyList.get(i);
	    			PrpCmainSchema  prpCmainSchema = blPolicy.getBLPrpCmain().getArr(0);
	    			String riskcode = prpCmainSchema.getRiskCode();
	    			PrpCcarshipTaxSchema prpCcarshipTaxSchema = null;
	    			if("0507".equals(riskcode)){
	    			   prpCcarshipTaxSchema = blPolicy.getBLPrpCcarshipTax().getArr(0);
	    			}
	    			BLCIInsureValid blCIInsureValid = blPolicy.getBLCIInsureValid();
	    			Element PolicyData = Policy_List.addElement("Policy_Data");	    			
					addPolicyData(PolicyData, prpCmainSchema,prpCcarshipTaxSchema,blPolicy,blCIInsureValid,paramMap);
					Double sumMoney = Double.parseDouble((String) paramMap.get("SumMoney"));
					sumMoney1 += sumMoney;
					DecimalFormat decimalFormat1 =new DecimalFormat("0.00");
					String sum_Money = decimalFormat1.format(sumMoney1);
					paramMap.put("Sum_Money", sum_Money);
				}

    		}

	}
	private void addPolicyData(Element policyData, PrpCmainSchema  prpCmainSchema,
			PrpCcarshipTaxSchema prpCcarshipTaxSchema,BLPolicy blPolicy,
			BLCIInsureValid blCIInsureValid,Map paramMap) throws Exception {
		
		Element RiskFlag = policyData.addElement("Risk_Flag");
		String riskCode = prpCmainSchema.getRiskCode();
		String riskFlag = "";
		if("0507".equals(riskCode)){
		     riskFlag = "2";
		}
		
		if("0508".equals(riskCode) || "0528".equals(riskCode)){
	         riskFlag = "1";	
		}
		
		RiskFlag.addText(riskFlag);
		
		Element SuccessFlag = policyData.addElement("Success_Flag");
		String successFlag = "1";
		SuccessFlag.addText(successFlag);
		
		Element Message = policyData.addElement("Message");
		String message = "";
		Message.addText(message);
		
		Element ProposalNo = policyData.addElement("Proposal_No");
		String proposalNo = prpCmainSchema.getProposalNo();
		ProposalNo.addText(proposalNo);
		
		Element PolicyNo = policyData.addElement("Policy_No");
		String policyNo = prpCmainSchema.getPolicyNo();
		PolicyNo.addText(policyNo);
		
		Element ConfirmSequenceNo = policyData.addElement("Confirm_Sequence_No");
		blCIInsureValid.query("policyNo = '"+policyNo+"'");
		CIInsureValidSchema cIInsureValidSchema = blCIInsureValid.getArr(0);
		String confirmSequenceNo = cIInsureValidSchema.getValidNo();
		ConfirmSequenceNo.addText(confirmSequenceNo);
		
		Element StartDate = policyData.addElement("Start_Date");
		String startDate = prpCmainSchema.getStartDate();
		StartDate.addText(startDate);
		
		Element EndDate = policyData.addElement("End_Date");
		String endDate = prpCmainSchema.getEndDate();
		EndDate.addText(endDate);
		
		double sumBenchMarkPremium=0.00;
		for(int p=0;p<blPolicy.getBLPrpCitemKind().getSize();p++){
			PrpCitemKindSchema prpCitemKindSchema = blPolicy.getBLPrpCitemKind().getArr(p);
			String benchMarkPremium = prpCitemKindSchema.getBenchMarkPremium();
			double benchMarkPremium1 = Double.parseDouble(benchMarkPremium);
			sumBenchMarkPremium+=benchMarkPremium1;
		}
		DecimalFormat decimalFormat =new DecimalFormat("0.00");
		Element BenchMarkPremium = policyData.addElement("BenchMark_Premium");
		String benchMarkPremium =decimalFormat.format(sumBenchMarkPremium);
		BenchMarkPremium.addText(benchMarkPremium);
		
		Element Premium = policyData.addElement("Premium");
		String premium = prpCmainSchema.getSumPremium();
		Premium.addText(premium);
		
		PrpCitemKindSchema prpCitemKindSchema = blPolicy.getBLPrpCitemKind().getArr(0);
		Element DisCount = policyData.addElement("DisCount");
		String disCount = prpCitemKindSchema.getDiscount();
		DisCount.addText(disCount);
		
		String sumPayTax = "";
		if("0507".equals(riskCode)){
			
			Element TaxActual = policyData.addElement("Tax_Actual");
			String taxActual = prpCcarshipTaxSchema.getTaxActual();
			TaxActual.addText(taxActual);
			
			Element PreviousPay = policyData.addElement("Previous_Pay");
			String previousPay = prpCcarshipTaxSchema.getPreviousPay();
			PreviousPay.addText(previousPay);
			
			Element LateFee = policyData.addElement("Late_Fee");
			String lateFee = prpCcarshipTaxSchema.getLateFee();
			LateFee.addText(lateFee);
			
			Element PayBalanceFee = policyData.addElement("Pay_Balance_Fee");
			String payBalanceFee = prpCcarshipTaxSchema.getPayBalanceFee();
			PayBalanceFee.addText(payBalanceFee);
			
			Element SumPayTax = policyData.addElement("Sum_Pay_Tax");
			sumPayTax = prpCcarshipTaxSchema.getSumPayTax();
			SumPayTax.addText(sumPayTax);
			
		}
		else{
			
			Element TaxActual = policyData.addElement("Tax_Actual");
			String taxActual = "0.00";
			TaxActual.addText(taxActual);
			
			Element PreviousPay = policyData.addElement("Previous_Pay");
			String previousPay = "0.00";
			PreviousPay.addText(previousPay);
			
			Element LateFee = policyData.addElement("Late_Fee");
			String lateFee = "0.00";
			LateFee.addText(lateFee);
			
			Element PayBalanceFee = policyData.addElement("Pay_Balance_Fee");
			String payBalanceFee = "0.00";
			PayBalanceFee.addText(payBalanceFee);
			
			Element SumPayTax = policyData.addElement("Sum_Pay_Tax");
			sumPayTax = "0.00";
			SumPayTax.addText(sumPayTax);
			
		}
	
		
		BLPrpJplanFeePre blPrpJplanFeePre = new BLPrpJplanFeePre();
		blPrpJplanFeePre.query("policyNo='"+policyNo+"'");
		String poacode = blPrpJplanFeePre.getArr(0).getPoaCode();
		Element PaymentNo = policyData.addElement("Payment_No");
		String paymentNo =poacode;
		PaymentNo.addText(paymentNo);		
		
		
		Element PosNo = policyData.addElement("Pos_No");
		String posNo = "";
		PosNo.addText(posNo);
		
		Element BankPaymentNo = policyData.addElement("Bank_Payment_No");
		String bankPaymentNo = "";
		BankPaymentNo.addText(bankPaymentNo);
		
		DecimalFormat decimalFormat1 =new DecimalFormat("0.00");
		String sumMoney = decimalFormat1.format(Double.parseDouble(premium)+ Double.parseDouble(sumPayTax));
		paramMap.put("SumMoney", sumMoney);
		
	}
	public String formateXml(String xmlStr) throws IOException, DocumentException{
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
	
   
	public static   String getOrderNo(){				
		SimpleDateFormat formatedate= new SimpleDateFormat("yyyyMMdd");
		Date currentDate=new Date();
		String dateString=formatedate.format(currentDate);
		String transNo = "000000000000000000"+dateString+getCharAndNumr(10);
		return transNo;
		
    }
	private static String getCharAndNumr(int length) {
		 String val = "";     
		    Random random = new Random();     
		    for(int i = 0; i < length; i++)     
		    {     
		        String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; 
		        if("char".equalsIgnoreCase(charOrNum)) 
		        {     
		            int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; 
		            val += (char) (choice + random.nextInt(26));     
		        }     
		        else if("num".equalsIgnoreCase(charOrNum)) 
		        {     
		            val += String.valueOf(random.nextInt(10));     
		        }     
		    }     
		    return val; 
	}
  
 

}
