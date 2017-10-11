package com.sp.interactive.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.carshiptaxtj.schema.CICarShipTaxTransactionSchema;
import com.sp.indiv.ci.schema.CICarShipTaxQGDemandSchema;
import com.sp.indiv.ci.schema.CICarshipTaxDemandSchema;
import com.sp.indiv.ci.schema.CIInsureDemandSchema;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PremCIInsureDemandSchema;
import com.sp.prpall.schema.PrpCarRelationSchema;
import com.sp.prpall.schema.PrpIntefInfoSchema;
import com.sp.prpall.schema.PrpLifeTableInfoSchema;
import com.sp.prpall.schema.PrpPhoneSaleExpenseSchema;
import com.sp.prpall.schema.PrpTNewEngageSchema;
import com.sp.prpall.schema.PrpTTrafficDetailSchema;
import com.sp.prpall.schema.PrpTcarDeviceSchema;
import com.sp.prpall.schema.PrpTcarDriverSchema;
import com.sp.prpall.schema.PrpTcarshipTaxSchema;
import com.sp.prpall.schema.PrpTcarshipTaxTJSchema;
import com.sp.prpall.schema.PrpTexpenseSchema;
import com.sp.prpall.schema.PrpTfeeSchema;
import com.sp.prpall.schema.PrpTinsuredNatureSchema;
import com.sp.prpall.schema.PrpTinsuredSchema;
import com.sp.prpall.schema.PrpTitemCarExtSchema;
import com.sp.prpall.schema.PrpTitemCarSchema;
import com.sp.prpall.schema.PrpTitemKindSchema;
import com.sp.prpall.schema.PrpTlimitSchema;
import com.sp.prpall.schema.PrpTmainAgriSchema;
import com.sp.prpall.schema.PrpTmainExtrasSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.prpall.schema.PrpTmainSubSchema;
import com.sp.prpall.schema.PrpTplanSchema;
import com.sp.prpall.schema.PrpTprofitDetailSchema;
import com.sp.prpall.schema.PrpTprofitSchema;
import com.sp.prpall.schema.PrpTrenewalSchema;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utiall.schema.PrpDcustomerIdvSchema;
import com.sp.utiall.schema.PrpDcustomerSchema;
import com.sp.utiall.schema.PrpDcustomerUnitSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.string.ChgData;

/**
 * 电话销售解码器
 * 
 */
public class InformationCollectionCoderHelp {


    /**
     * 解码
     * @return XX查询请求XML格式串
     * @throws Exception
     */
    public void decode(ArrayList blProposalList, String content,Map paramMap)throws Exception 
    {
    	InputStream in = new ByteArrayInputStream(content.getBytes());
        Document document = XMLUtils.parse(in);
        processHead(paramMap,XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
        processBody(paramMap,blProposalList, XMLUtils.getChildNodeByPath(document, "/PACKET/BODY"));
        

    }


    /**
     * 处理HEAD节
     * @param node Node
     * @throws Exception
     */
    public void processHead(Map paramMap,Node node) throws Exception {
    	String	TRANSNO	= XMLUtils.getChildNodeValue(node, "TRANSNO");
    	String	TRANSEXEDATE	= XMLUtils.getChildNodeValue(node, "TRANSEXEDATE");
    	String	TRANSEXETIME	= XMLUtils.getChildNodeValue(node, "TRANSEXETIME");
    	String	COMCODE	= XMLUtils.getChildNodeValue(node, "COMCODE");
    	String	TRANSTYPE	= XMLUtils.getChildNodeValue(node, "TRANSTYPE");
    	String	REQUESTTYPECODE	= XMLUtils.getChildNodeValue(node, "REQUESTTYPECODE");
    	paramMap.put("requestType", TRANSTYPE);
    }

    /**
     * 处理BODY节
     * @param node Node
     * @throws Exception
     */
    public void processBody(Map paramMap,ArrayList blProposalList, Node node) 
    	throws Exception 
    {  
    	String INFOTRANSNO = XMLUtils.getChildNodeValue(node, "INFOTRANSNO");
    	String  mainsub="";
    	paramMap.put("InfoTransNo",INFOTRANSNO);
    	BLProposal   blProposalBI = new BLProposal(); 
    	BLProposal   blProposalCI = new BLProposal(); 
    	mainsub=processPrpTmainDataBC(blProposalBI,blProposalCI, node);
    	paramMap.put("mainsub",mainsub);
    	
























        if(!"".equals(mainsub)&&mainsub!=null){
        	if("1".equals(mainsub)){
        		
            	
        		
        		
            	processPrpTexpenseList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "EXPENSE_LIST"));
            	
                processPrpLifeTableInfoData(blProposalBI, node);
              

              
                processPrpTinsuredList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "INSURED_LIST"));
                processPrpTitemCarList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "CAR_LIST"));
                processPrpTcarowerList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "INSURED_LIST"));
                processPrpTcarDriverList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "DRIVER_LIST"));
                processPrpTcarDeviceList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "DEVICE_LIST"));
                processPrpTrenewalList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "RENEWAL_LIST"));
                proceePrpTmainSubList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "MAINSUB_LIST"));
                processPrpTitemKindList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "KIND_LIST"));
                processPrpTprofitDetailList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "PROFITDETAIL_LIST"));
                processPrpTTrafficDetailList(blProposalBI,XMLUtils.getChildNodeByTagName(node, "TRAFFICDETAIL_LIST"));
                processPrpIntefInfoList(blProposalBI,XMLUtils.getChildNodeByTagName(node, "PRPINTEFINFO_LIST"));
                processPrpPhoneSaleExpenseList(blProposalBI,XMLUtils.getChildNodeByTagName(node, "PRPPHONESALEEXPENSE_LIST"));
                processPrpTMainAgriList(blProposalBI,XMLUtils.getChildNodeByTagName(node, "MAINAGRI_LIST"));
                processRelateCarProposalList(blProposalBI,XMLUtils.getChildNodeByTagName(node, "RELATECARPROPOSAL_LIST"));
                
                
                if(XMLUtils.getChildNodeByTagName(node, "PRPTNEWENGAGE_LIST")!=null){
                	processPrptnewengageList(blProposalBI,XMLUtils.getChildNodeByTagName(node, "PRPTNEWENGAGE_LIST"));
                }
                
                
            	
        		for(int i=0;i<blProposalBI.getBLPrpTitemKind().getSize();i++){
        			if("BZ".equals(blProposalBI.getBLPrpTitemKind().getArr(i).getKindCode())){
        				blProposalBI.getBLPrpTitemKind().remove(i);
        			}
        		}
        		
        		
        		
        		
        		
        		
            	
            	processCICarShipTaxDemandData(blProposalCI, node);
            	
            	processCIInsureValidData(blProposalCI, node);
            	processCICarShipTaxTransactionData(blProposalCI, node);
            	

            	
            	
            	processPrpTexpenseList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "EXPENSE_LIST"));
            	
                processPrpLifeTableInfoData(blProposalCI, node);
              

              
                processPrpTinsuredList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "INSURED_LIST"));
                processPrpTitemCarList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "CAR_LIST"));
                processPrpTcarowerList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "INSURED_LIST"));
                processPrpTcarDriverList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "DRIVER_LIST"));
                processPrpTcarDeviceList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "DEVICE_LIST"));
                processPrpTrenewalList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "RENEWAL_LIST"));
                proceePrpTmainSubList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "MAINSUB_LIST"));
                processPrpTcarShipTaxTJList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "CARTAXTJ_LIST"));
                processPrpTcarShipTaxList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "CARTAX_LIST"));
                processPrpTitemKindList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "KIND_LIST"));
                processPrpTTrafficDetailList(blProposalCI,XMLUtils.getChildNodeByTagName(node, "TRAFFICDETAIL_LIST"));
                processPrpIntefInfoList(blProposalCI,XMLUtils.getChildNodeByTagName(node, "PRPINTEFINFO_LIST"));
                processPrpPhoneSaleExpenseList(blProposalCI,XMLUtils.getChildNodeByTagName(node, "PRPPHONESALEEXPENSE_LIST"));
                processPrpTMainAgriList(blProposalCI,XMLUtils.getChildNodeByTagName(node, "MAINAGRI_LIST"));
                processRelateCarProposalList(blProposalCI,XMLUtils.getChildNodeByTagName(node, "RELATECARPROPOSAL_LIST"));
              
              
                if(XMLUtils.getChildNodeByTagName(node, "PRPTNEWENGAGE_LIST")!=null){
                	processPrptnewengageList(blProposalCI,XMLUtils.getChildNodeByTagName(node, "PRPTNEWENGAGE_LIST"));
                }
                
                
        		
            	for(int i=0;i<blProposalCI.getBLPrpTitemKind().getSize();i++){
        			if(!"BZ".equals(blProposalCI.getBLPrpTitemKind().getArr(i).getKindCode())){
        				blProposalCI.getBLPrpTitemKind().remove(i);
        				i--;
        			}
        		}
            	
            	for(int i=0;i<blProposalBI.getBLPrpIntefInfo().getSize();i++){
        			if(!blProposalBI.getBLPrpTmain().getArr(0).getRiskCode().equals(blProposalBI.getBLPrpIntefInfo().getArr(i).getRiskCode())){
        				blProposalBI.getBLPrpIntefInfo().remove(i);
        				i--;
        			}
        		}
            	for(int i=0;i<blProposalCI.getBLPrpIntefInfo().getSize();i++){
        			if(!blProposalCI.getBLPrpTmain().getArr(0).getRiskCode().equals(blProposalCI.getBLPrpIntefInfo().getArr(i).getRiskCode())){
        				blProposalCI.getBLPrpIntefInfo().remove(i);
        				i--;
        			}
        		}
            	
            	
            	
            	if(blProposalBI!=null && blProposalBI.getBLPrpTexpense()!=null&&blProposalBI.getBLPrpTexpense().getSize()>0){
            		for(int i=0;i<blProposalBI.getBLPrpTexpense().getSize();i++){
            			if(!blProposalBI.getBLPrpTmain().getArr(0).getRiskCode().equals(blProposalBI.getBLPrpTexpense().getArr(i).getRiskCode())){
            				blProposalBI.getBLPrpTexpense().remove(i);
            				i--;
            			}
            		}
            	}
            	if(blProposalCI!=null && blProposalCI.getBLPrpTexpense()!=null&&blProposalCI.getBLPrpTexpense().getSize()>0){
            		for(int i=0;i<blProposalCI.getBLPrpTexpense().getSize();i++){
            			if(!blProposalCI.getBLPrpTmain().getArr(0).getRiskCode().equals(blProposalCI.getBLPrpTexpense().getArr(i).getRiskCode())){
            				blProposalCI.getBLPrpTexpense().remove(i);
            				i--;
            			}
            		}
            	}
            	
            	
            	
            	blProposalCI.getBLPrpTitemCar().getArr(0).setDemandNo(blProposalCI.getBLPrpTmain().getArr(0).getDemandNo());
         		
        		 blProposalList.add(blProposalBI);
            	 blProposalList.add(blProposalCI);
        	}
        	if("2".equals(mainsub)){
        		

        		
        		
            	processPrpTexpenseList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "EXPENSE_LIST"));
            	
                processPrpLifeTableInfoData(blProposalBI, node);
              

              
                processPrpTinsuredList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "INSURED_LIST"));
                processPrpTitemCarList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "CAR_LIST"));
                processPrpTcarowerList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "INSURED_LIST"));
                processPrpTcarDriverList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "DRIVER_LIST"));
                processPrpTcarDeviceList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "DEVICE_LIST"));
                processPrpTrenewalList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "RENEWAL_LIST"));
                proceePrpTmainSubList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "MAINSUB_LIST"));
                processPrpTitemKindList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "KIND_LIST"));
                processPrpTprofitDetailList(blProposalBI, XMLUtils.getChildNodeByTagName(node, "PROFITDETAIL_LIST"));
                processPrpTTrafficDetailList(blProposalBI,XMLUtils.getChildNodeByTagName(node, "TRAFFICDETAIL_LIST"));
                processPrpIntefInfoList(blProposalBI,XMLUtils.getChildNodeByTagName(node, "PRPINTEFINFO_LIST"));
                processPrpPhoneSaleExpenseList(blProposalBI,XMLUtils.getChildNodeByTagName(node, "PRPPHONESALEEXPENSE_LIST"));
                processPrpTMainAgriList(blProposalBI,XMLUtils.getChildNodeByTagName(node, "MAINAGRI_LIST"));
                processRelateCarProposalList(blProposalBI,XMLUtils.getChildNodeByTagName(node, "RELATECARPROPOSAL_LIST"));
            	
                
                processPrptnewengageList(blProposalBI,XMLUtils.getChildNodeByTagName(node, "PRPTNEWENGAGE_LIST"));
                
        		for(int i=0;i<blProposalBI.getBLPrpTitemKind().getSize();i++){
        			if("BZ".equals(blProposalBI.getBLPrpTitemKind().getArr(i).getKindCode())){
        				blProposalBI.getBLPrpTitemKind().remove(i);
        			}
        		}
        		blProposalList.add(blProposalBI);
        	}
        	if("3".equals(mainsub)){
        		
            	processCICarShipTaxDemandData(blProposalCI, node);
            	
            	
            	processPrpTexpenseList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "PRPTEXPENSE_LIST"));
            	
            	processCICarShipTaxTransactionData(blProposalCI, node);
            	

            	
            	processPrpTexpenseData(blProposalCI, node);
                processPrpLifeTableInfoData(blProposalCI, node);
              

              
                processPrpTinsuredList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "INSURED_LIST"));
                processPrpTitemCarList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "CAR_LIST"));
                processPrpTcarowerList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "INSURED_LIST"));
                processPrpTcarDriverList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "DRIVER_LIST"));
                processPrpTcarDeviceList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "DEVICE_LIST"));
                processPrpTrenewalList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "RENEWAL_LIST"));
                proceePrpTmainSubList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "MAINSUB_LIST"));
                processPrpTcarShipTaxTJList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "CARTAXTJ_LIST"));
                processPrpTcarShipTaxList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "CARTAX_LIST"));
                processPrpTitemKindList(blProposalCI, XMLUtils.getChildNodeByTagName(node, "KIND_LIST"));
                processPrpTTrafficDetailList(blProposalCI,XMLUtils.getChildNodeByTagName(node, "TRAFFICDETAIL_LIST"));
                processPrpIntefInfoList(blProposalCI,XMLUtils.getChildNodeByTagName(node, "PRPINTEFINFO_LIST"));
                processPrpPhoneSaleExpenseList(blProposalCI,XMLUtils.getChildNodeByTagName(node, "PRPPHONESALEEXPENSE_LIST"));
                processPrpTMainAgriList(blProposalCI,XMLUtils.getChildNodeByTagName(node, "MAINAGRI_LIST"));
                processRelateCarProposalList(blProposalCI,XMLUtils.getChildNodeByTagName(node, "RELATECARPROPOSAL_LIST"));
                
				
                processPrptnewengageList(blProposalCI,XMLUtils.getChildNodeByTagName(node, "PRPTNEWENGAGE_LIST"));
               	
			    
            	for(int i=0;i<blProposalCI.getBLPrpTitemKind().getSize();i++){
        			if(!"BZ".equals(blProposalCI.getBLPrpTitemKind().getArr(i).getKindCode())){
        				blProposalCI.getBLPrpTitemKind().remove(i);
        				i--;
        			}
        		}
            	
            	blProposalCI.getBLPrpTitemCar().getArr(0).setDemandNo(blProposalCI.getBLPrpTmain().getArr(0).getDemandNo());
         		
        		blProposalList.add(blProposalCI);
        	}
        }
    }
    
    
    
    
    public void processProposalData(ArrayList blProposalList, Node node) 
    throws Exception 
    {
       
       try{ 
       BLProposal   blProposal = new BLProposal();  
       processPrptmainList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTMAIN_LIST"));
       processPrptmainExtrasList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTMAIN_LIST"));
       processPrpTTrafficDetailList(blProposal,XMLUtils.getChildNodeByTagName(node, "PRPTTRAFFICDETAIL_LIST"));
       processPrpTfeeList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTFEE_LIST"));
       processPrpTinsuredList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTINSURED_LIST"));
       processPrpTitemCarList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTITEMCAR_LIST"));
       processPrpTitemCarExtList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTITEMCAREXT_LIST"));
       
       processPrpTitemKindList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTITEMKIND_LIST"));
       processPrpTlimitList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTLIMIT_LIST"));
       processPrpTprofitList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTPROFIT_LIST"));
       processPrpTprofitDetailList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTPROFITDETAIL_LIST"));
       processPrpTexpenseList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTEXPENSE_LIST"));
       processPrpTcarDriverList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTCARDRIVER_LIST"));
       processPrpTcarDeviceList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTCARDEVICE_LIST"));
       processPrpTrenewalList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTRENEWAL_LIST"));
       processPrpTcarShipTaxTJList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTCARSHIPTAXTJ_LIST"));
       processPrpTcarShipTaxList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTCARSHIPTAX_LIST"));
       proceePrpTmainSubList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTMAINSUB_LIST"));
       processPrpTplanList(blProposal, XMLUtils.getChildNodeByTagName(node, "PRPTPLAN_LIST"));
       
       
       
       processCICarShipTaxDemandList(blProposal,XMLUtils.getChildNodeByTagName(node, "CICARSHIPTAXDEMAND_LIST"));
       processCICarShipTaxTransactionList(blProposal,XMLUtils.getChildNodeByTagName(node, "CICARSHIPTAXTRANSACTION_LIST"));
       processCIInsureDemandList(blProposal,XMLUtils.getChildNodeByTagName(node, "CIINSUREDEMAND_LIST"));
       processCIInsureValidList(blProposal,XMLUtils.getChildNodeByTagName(node, "CIINSUREVALID_LIST"));
       
       processCICarShipTaxQGDemandList(blProposal,XMLUtils.getChildNodeByTagName(node, "CICARSHIPTAXQGDEMAND_LIST"));
       
       
       processPrpLifeTableInfoList(blProposal,XMLUtils.getChildNodeByTagName(node, "PRPLIFETABLEINFO_LIST"));
       
       
       processPrpIntefInfoList(blProposal,XMLUtils.getChildNodeByTagName(node, "PRPINTEFINFO_LIST"));
       
       
       processPrpPhoneSaleExpenseList(blProposal,XMLUtils.getChildNodeByTagName(node, "PRPPHONESALEEXPENSE_LIST"));
       
       
       processPrpTMainAgriList(blProposal,XMLUtils.getChildNodeByTagName(node, "MAINAGRI_LIST"));
       
       
       processRelateCarProposalList(blProposal,XMLUtils.getChildNodeByTagName(node, "RELATECARPROPOSAL_LIST"));
       
       
     
       processPrptnewengageList(blProposal,XMLUtils.getChildNodeByTagName(node, "PRPTNEWENGAGE_LIST"));
       
       blProposalList.add(blProposal);
       }catch(Exception e){
           e.printStackTrace();
       }
    } 
    
    /**
     *  处理PRPLIFETABLEINFO_LIST节点
     * @param blProposal
     * @param node
     * @throws Exception
     * @author renbaodan 2010-12-22
     */
    private void processPrpLifeTableInfoList(BLProposal blProposal, Node node)throws Exception {
		
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "PRPLIFETABLEINFO_DATA");
    	for(int i=0; i<nodes.length; i++){
    		processPrpLifeTableInfoData(blProposal,nodes[i]);
    	}
	} 
    
    /**
     *  处理PRPINTEFINFO_LIST节点
     * @param blProposal
     * @param node
     * @throws Exception
     * @author renbaodan 2010-12-17
     */
    private void processPrpIntefInfoList(BLProposal blProposal, Node node)throws Exception {
		
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "PRPINTEFINFO_DATA");
    	String riskcode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
    	for(int i=0; i<nodes.length; i++){
    		processPrpIntefInfoData(blProposal,nodes[i]);
    	}
	}    
    
    /**
     *  处理PRPLIFETABLEINFO_DATA节点
     * @param blProposal
     * @param node
     * @throws Exception
     * @author renbaodan 2010-12-22
     */
	private void processPrpLifeTableInfoData(BLProposal blProposal, Node node)throws Exception{
		
     
        String depositRate=XMLUtils.getChildNodeValue(node, "DEPOSITRATE");
        String finalPayRate=XMLUtils.getChildNodeValue(node, "FINALPAYRATE");
        String finalBusinessClass=XMLUtils.getChildNodeValue(node, "FINALBUSINESSCLASS");
        String profitMarginBC=XMLUtils.getChildNodeValue(node, "PROFITMARGINBC");
        String profitBusinessClassBC=XMLUtils.getChildNodeValue(node, "PROFITBUSINESSCLASSBC");
        String finalBusinessClassBC=XMLUtils.getChildNodeValue(node, "FINALBUSINESSCLASSBC");
        
        String costMargin=XMLUtils.getChildNodeValue(node, "COSTMARGIN");
        String costBusinessClass=XMLUtils.getChildNodeValue(node, "COSTBUSINESSCLASS");
        String costMarginBC=XMLUtils.getChildNodeValue(node, "COSTMARGINBC");
        String costBusinessClassBC=XMLUtils.getChildNodeValue(node, "COSTBUSINESSCLASSBC");
        
        
        PrpLifeTableInfoSchema schema = new PrpLifeTableInfoSchema();
        schema.setDepositRate(depositRate);
        schema.setFinalPayRate(finalPayRate);
        schema.setFinalBusinessClass(finalBusinessClass);
        schema.setProfitMarginBC(profitMarginBC);
        schema.setProfitBusinessClassBC(profitBusinessClassBC);
        schema.setFinalBusinessClassBC(finalBusinessClassBC);
        schema.setCostMargin(costMargin);
        schema.setCostBusinessClass(costBusinessClass);
        schema.setCostMarginBC(costMarginBC);
        schema.setCostBusinessClassBC(costBusinessClassBC);
        blProposal.getBlPrpLifeTableInfo().setArr(schema);
	}
    /**
     *  处理PRPINTEFINFO_DATA节点
     * @param blProposal
     * @param node
     * @throws Exception
     * @author renbaodan 2010-12-17
     */
	private void processPrpIntefInfoData(BLProposal blProposal, Node node)throws Exception{
		
		String businessNo = XMLUtils.getChildNodeValue(node, "BUSINESSNO");
		String policyNo = XMLUtils.getChildNodeValue(node, "POLICYNO");
		String lastClaimCount = XMLUtils.getChildNodeValue(node, "LASTCLAIMCOUNT");
		String lastClaimCountOrigin = XMLUtils.getChildNodeValue(node, "LASTCLAIMCOUNTORIGIN");
		String lastPaySum = XMLUtils.getChildNodeValue(node, "LASTPAYSUM");
		String lastPaySumOrigin = XMLUtils.getChildNodeValue(node, "LASTPAYSUMORIGIN");
		String lastPayRate = XMLUtils.getChildNodeValue(node, "LASTPAYRATE");
		String lastPayRateOrigin = XMLUtils.getChildNodeValue(node, "LASTPAYRATEORIGIN");
		String hisClaimCount = XMLUtils.getChildNodeValue(node, "HISCLAIMCOUNT");
		String hisClaimCountOrigin = XMLUtils.getChildNodeValue(node, "HISCLAIMCOUNTORIGIN");
		String hisPaySum = XMLUtils.getChildNodeValue(node, "HISPAYSUM");
		String hisPaySumOrigin = XMLUtils.getChildNodeValue(node, "HISPAYSUMORIGIN");

		
		PrpIntefInfoSchema schema = new PrpIntefInfoSchema();
		
		String riskCode = XMLUtils.getChildNodeValue(node, "RISKCODE");
		schema.setRiskCode(riskCode);
		
		
		schema.setBusinessNo(businessNo);
		
		schema.setPolicyNo("新XXXXX");
		if(blProposal.getBLPrpTrenewal().getSize() > 0){
			PrpTrenewalSchema prpTrenewalSchema = blProposal.getBLPrpTrenewal().getArr(0);
			String oldPolicyNo = prpTrenewalSchema.getOldPolicyNo();
			if(oldPolicyNo != null && !"".equals(oldPolicyNo)){
				schema.setPolicyNo(oldPolicyNo);
			}
		}
		
		schema.setLastClaimCount(lastClaimCount);
		schema.setLastClaimCountOrigin(lastClaimCountOrigin);
		schema.setLastPaySum(lastPaySum);
		schema.setLastPaySumOrigin(lastPaySumOrigin);
		schema.setLastPayRate(lastPayRate);
		schema.setLastPayRateOrigin(lastPayRateOrigin);
		schema.setHisClaimCount(hisClaimCount);
		schema.setHisClaimCountOrigin(hisClaimCountOrigin);
		schema.setHisPaySum(hisPaySum);
		schema.setHisPaySumOrigin(hisPaySumOrigin);
		
		blProposal.getBLPrpIntefInfo().setArr(schema);
	}
    
    /**
     *  处理CICARSHIPTAXQGDEMAND_LIST节点
     * @param blProposal
     * @param node
     * @throws Exception
     * @author yangxiaodong  2009-04-13
     */
    private void processCICarShipTaxQGDemandList(BLProposal blProposal, Node node)throws Exception {
		
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CICARSHIPTAXQGDEMAND_DATA");
    	for(int i=0; i<nodes.length; i++){
    		processCICarShipTaxQGDemandData(blProposal,nodes[i]);
    	}
	}
    
    

    /**
     *  处理CICARSHIPTAXQGDEMAND_DATA节点
     * @param blProposal
     * @param node
     * @throws Exception
     * @author yangxiaodong  2009-04-13
     */
	private void processCICarShipTaxQGDemandData(BLProposal blProposal, Node node)throws Exception{
		
		String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
		String DemandNo = XMLUtils.getChildNodeValue(node, "DEMANDNO");
		String PolicyNo = XMLUtils.getChildNodeValue(node, "POLICYNO");
		String TaxType = XMLUtils.getChildNodeValue(node, "TAXTYPE");
		String SerialNo = XMLUtils.getChildNodeValue(node, "SERIALNO");
		String TaxTermTypeCode = XMLUtils.getChildNodeValue(node, "TAXTERMTYPECODE");
		String TaxConditionCode = XMLUtils.getChildNodeValue(node, "TAXCONDITIONCODE");
		String TaxRegistryNumber = XMLUtils.getChildNodeValue(node, "TAXREGISTRYNUMBER");
		String TaxPayerName = XMLUtils.getChildNodeValue(node, "TAXPAYERNAME");
		String TaxPayerIdentificationCode = XMLUtils.getChildNodeValue(node, "TAXPAYERIDENTIFICATIONCODE");
		String TaxLocationCode = XMLUtils.getChildNodeValue(node, "TAXLOCATIONCODE");
		String TaxStartDate = XMLUtils.getChildNodeValue(node, "TAXSTARTDATE");
		String TaxEndDate = XMLUtils.getChildNodeValue(node, "TAXENDDATE");
		String DeclareDate = XMLUtils.getChildNodeValue(node, "DECLAREDATE");
		String TaxDepartmentCode = XMLUtils.getChildNodeValue(node, "TAXDEPARTMENTCODE");
		String TaxDepartment = XMLUtils.getChildNodeValue(node, "TAXDEPARTMENT");
		String TaxDocumentNumber = XMLUtils.getChildNodeValue(node, "TAXDOCUMENTNUMBER");
		String AnnualTaxAmount = XMLUtils.getChildNodeValue(node, "ANNUALTAXAMOUNT");
		String AppliedArea = XMLUtils.getChildNodeValue(node, "APPLIEDAREA");
		String TaxRateIdentifier = XMLUtils.getChildNodeValue(node, "TAXRATEIDENTIFIER");
		String TaxItemDetailCode = XMLUtils.getChildNodeValue(node, "TAXITEMDETAILCODE");
		String TaxUnitTypeCode = XMLUtils.getChildNodeValue(node, "TAXUNITTYPECODE");
		String UnitRate = XMLUtils.getChildNodeValue(node, "UNITRATE");
		String TaxRateStartDate = XMLUtils.getChildNodeValue(node, "TAXRATESTARTDATE");
		String TaxRateEndDate = XMLUtils.getChildNodeValue(node, "TAXRATEENDDATE");
		String DeductionDueCode = XMLUtils.getChildNodeValue(node, "DEDUCTIONDUECODE");
		String DeductionDueType = XMLUtils.getChildNodeValue(node, "DEDUCTIONDUETYPE");
		String DeductionDueProportion = XMLUtils.getChildNodeValue(node, "DEDUCTIONDUEPROPORTION");
		String Deduction = XMLUtils.getChildNodeValue(node, "DEDUCTION");
		String DeductionDocumentNumber = XMLUtils.getChildNodeValue(node, "DEDUCTIONDOCUMENTNUMBER");
		String DeductionDepartmentCode = XMLUtils.getChildNodeValue(node, "DEDUCTIONDEPARTMENTCODE");
		String DeductionDepartment = XMLUtils.getChildNodeValue(node, "DEDUCTIONDEPARTMENT");
		String TaxDue = XMLUtils.getChildNodeValue(node, "TAXDUE");
		String ExceedDate = XMLUtils.getChildNodeValue(node, "EXCEEDDATE");
		String ExceedDaysCount = XMLUtils.getChildNodeValue(node, "EXCEEDDAYSCOUNT");
		String OverDue = XMLUtils.getChildNodeValue(node, "OVERDUE");
		String TotalAmount = XMLUtils.getChildNodeValue(node, "TOTALAMOUNT");
		String AnnualTaxDue = XMLUtils.getChildNodeValue(node, "ANNUALTAXDUE");
		String SumTaxDefault = XMLUtils.getChildNodeValue(node, "SUMTAXDEFAULT");
		String SumOverdue = XMLUtils.getChildNodeValue(node, "SUMOVERDUE");
		String SumTax = XMLUtils.getChildNodeValue(node, "SUMTAX");
		String TaxDescription = XMLUtils.getChildNodeValue(node, "TAXDESCRIPTION");
		
		CICarShipTaxQGDemandSchema schema = new CICarShipTaxQGDemandSchema();
		schema.setProposalNo(ProposalNo);
		schema.setDemandNo(DemandNo);
		schema.setPolicyNo(PolicyNo);
		schema.setTaxType(TaxType);
		schema.setSerialNo(SerialNo);
		schema.setTaxTermTypeCode(TaxTermTypeCode);
		schema.setTaxConditionCode(TaxConditionCode);
		schema.setTaxRegistryNumber(TaxRegistryNumber);
		schema.setTaxPayerName(TaxPayerName);
		schema.setTaxPayerIdentificationCode(TaxPayerIdentificationCode);
		schema.setTaxLocationCode(TaxLocationCode);
		schema.setTaxStartDate(TaxStartDate);
		schema.setTaxEndDate(TaxEndDate);
		schema.setDeclareDate(DeclareDate);
		schema.setTaxDepartmentCode(TaxDepartmentCode);
		schema.setTaxDepartment(TaxDepartment);
		schema.setTaxDocumentNumber(TaxDocumentNumber);
		schema.setAnnualTaxAmount(AnnualTaxAmount);
		schema.setAppliedArea(AppliedArea);
		schema.setTaxRateIdentifier(TaxRateIdentifier);
		schema.setTaxItemDetailCode(TaxItemDetailCode);
		schema.setTaxUnitTypeCode(TaxUnitTypeCode);
		schema.setUnitRate(UnitRate);
		schema.setTaxRateStartDate(TaxRateStartDate);
		schema.setTaxRateEndDate(TaxRateEndDate);
		schema.setDeductionDueCode(DeductionDueCode);
		schema.setDeductionDueType(DeductionDueType);
		schema.setDeductionDueProportion(DeductionDueProportion);
		schema.setDeduction(Deduction);
		schema.setDeductionDocumentNumber(DeductionDocumentNumber);
		schema.setDeductionDepartmentCode(DeductionDepartmentCode);
		schema.setDeductionDepartment(DeductionDepartment);
		schema.setTaxDue(TaxDue);
		schema.setExceedDate(ExceedDate);
		schema.setExceedDaysCount(ExceedDaysCount);
		schema.setOverDue(OverDue);
		schema.setTotalAmount(TotalAmount);
		schema.setAnnualTaxDue(AnnualTaxDue);
		schema.setSumTaxDefault(SumTaxDefault);
		schema.setSumOverdue(SumOverdue);
		schema.setSumTax(SumTax);
		schema.setTaxDescription(TaxDescription);
		blProposal.getBLCICarShipTaxQGDemand().setArr(schema);
	}
	

	/**
     *  处理CIInsureValid_List节点
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processCIInsureValidList(BLProposal blProposal, Node node)throws Exception {
		
		Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CIINSUREVALID_DATA");
		for(int i=0; i<nodes.length; i++){
			processCIInsureValidData(blProposal,nodes[i]);
		}
	}

	public void processCIInsureValidData(BLProposal blProposal, Node node)throws Exception {
		
		String ValidNo = XMLUtils.getChildNodeValue(node, "VALIDNO");
		String DemandNo = XMLUtils.getChildNodeValue(node, "DEMANDNO");
		String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
		String PolicyNo = XMLUtils.getChildNodeValue(node, "POLICYNO");
		String InsureMarkNo = XMLUtils.getChildNodeValue(node, "INSUREMARKNO");
		String BussinessNature = XMLUtils.getChildNodeValue(node, "BUSSINESSNATURE");
		String OperateDate = XMLUtils.getChildNodeValue(node, "OPERATEDATE");
		String Clauses = XMLUtils.getChildNodeValue(node, "CLAUSES");
		String HandlerName = XMLUtils.getChildNodeValue(node, "HANDLERNAME");
		String OperatorName = XMLUtils.getChildNodeValue(node, "OPERATORNAME");
		String Currency = XMLUtils.getChildNodeValue(node, "CURRENCY");
		String Premium = XMLUtils.getChildNodeValue(node, "PREMIUM");
		String ChangeReason = XMLUtils.getChildNodeValue(node, "CHANGEREASON");
		String ChangeDetail = XMLUtils.getChildNodeValue(node, "CHANGEDETAIL");
		String ComCode = XMLUtils.getChildNodeValue(node, "COMCODE");
		String ValidTime = XMLUtils.getChildNodeValue(node, "VALIDTIME");
		String Remark = XMLUtils.getChildNodeValue(node, "REMARK");
		String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
		String ReinsureFlag = XMLUtils.getChildNodeValue(node, "REINSUREFLAG");
		
		CIInsureValidSchema schema = new CIInsureValidSchema();
		schema.setValidNo(ValidNo);
		schema.setDemandNo(DemandNo);
		schema.setProposalNo(ProposalNo);
		schema.setPolicyNo(PolicyNo);
		schema.setInsureMarkNo(InsureMarkNo);
		schema.setBussinessNature(BussinessNature);
		schema.setOperateDate(OperateDate);
		schema.setClauses(Clauses);
		schema.setHandlerName(HandlerName);
		schema.setOperatorName(OperatorName);
		schema.setCurrency(Currency);
		schema.setPremium(Premium);
		schema.setChangeDetail(ChangeDetail);
		schema.setChangeReason(ChangeReason);
		schema.setComCode(ComCode);
		schema.setValidTime(ValidTime);
		schema.setRemark(Remark);
		schema.setFlag(Flag);
		schema.setReinsureFlag(ReinsureFlag);
		blProposal.getBLCIInsureValid().setArr(schema);
	}

	/**
     *  处理CIInsureDemand_List节点
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processCIInsureDemandList(BLProposal blProposal, Node node) throws Exception {
		
        
		Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CIINSUREDEMAND_DATA");
		for(int i=0; i<nodes.length; i++){
			processCIInsureDemandData(blProposal,nodes[i]);
		}
	}

	public void processCIInsureDemandData(BLProposal blProposal, Node node)throws Exception {
        
		
		String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
		String DemandNo = XMLUtils.getChildNodeValue(node, "DEMANDNO");
		String PolicyNo = XMLUtils.getChildNodeValue(node, "POLICYNO");
		String LicenseNo =XMLUtils.getChildNodeValue(node, "LICENSENO");
		String LicenseType = XMLUtils.getChildNodeValue(node, "LICENSETYPE");
		String UseNatureCode = XMLUtils.getChildNodeValue(node, "USENATURECODE");
		String FrameNo = XMLUtils.getChildNodeValue(node, "FRAMENO");
		String EngineNo = XMLUtils.getChildNodeValue(node, "ENGINENO");
		String LicenseColorCode = XMLUtils.getChildNodeValue(node, "LICENSECOLORCODE");
		String CarOwner = XMLUtils.getChildNodeValue(node, "CAROWNER");
		String EnrollDate = XMLUtils.getChildNodeValue(node, "ENROLLDATE");
		String MakeDate = XMLUtils.getChildNodeValue(node, "MAKEDATE");
		String SeatCount = XMLUtils.getChildNodeValue(node, "SEATCOUNT");
		String TonCount = XMLUtils.getChildNodeValue(node, "TONCOUNT");
		String ValidCheckDate = XMLUtils.getChildNodeValue(node, "VALIDCHECKDATE");
		String ManufacturerName = XMLUtils.getChildNodeValue(node, "MANUFACTURERNAME");
		String ModelCode = XMLUtils.getChildNodeValue(node, "MODELCODE");
		String BrandCName = XMLUtils.getChildNodeValue(node, "BRANDCNAME");
		String BrandName = XMLUtils.getChildNodeValue(node, "BRANDNAME");
		String CarKindCode = XMLUtils.getChildNodeValue(node, "CARKINDCODE");
		String CheckDate = XMLUtils.getChildNodeValue(node, "CHECKDATE");
		String EndValidDate = XMLUtils.getChildNodeValue(node, "ENDVALIDDATE");
		String CarStatus = XMLUtils.getChildNodeValue(node, "CARSTATUS");
		String Haulage = XMLUtils.getChildNodeValue(node, "HAULAGE");
		String StartDate = XMLUtils.getChildNodeValue(node, "STARTDATE");
		String EndDate = XMLUtils.getChildNodeValue(node, "ENDDATE");
		String Amount = XMLUtils.getChildNodeValue(node, "AMOUNT");
		String Premium = XMLUtils.getChildNodeValue(node, "PREMIUM");
		String BenchmarkPremium = XMLUtils.getChildNodeValue(node, "BENCHMARKPREMIUM");
		String PeccancyCoeff = XMLUtils.getChildNodeValue(node, "PECCANCYCOEFF");
		String ClaimCoeff = XMLUtils.getChildNodeValue(node, "CLAIMCOEFF");
		String DriverCoeff = XMLUtils.getChildNodeValue(node, "DRIVERCOEFF");
		String DistrictCoeff = XMLUtils.getChildNodeValue(node, "DISTRICTCOEFF");
		String CommissionRate = XMLUtils.getChildNodeValue(node, "COMMISSIONRATE");
		String BasePremium = XMLUtils.getChildNodeValue(node, "BASEPREMIUM");
		String ComCode = XMLUtils.getChildNodeValue(node, "COMCODE");
		String OperatorCode = XMLUtils.getChildNodeValue(node, "OPERATORCODE");
		String DemandTime = XMLUtils.getChildNodeValue(node, "DEMANDTIME");
		String Remark = XMLUtils.getChildNodeValue(node, "REMARK");
		String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
		String BillDate = XMLUtils.getChildNodeValue(node, "BILLDATE");
		String ReinsureFlag = XMLUtils.getChildNodeValue(node, "REINSUREFLAG");
		String LastBillDate = XMLUtils.getChildNodeValue(node, "LASTBILLDATE");
		String RatefloatFlag = XMLUtils.getChildNodeValue(node, "RATEFLOATFLAG");
		String ClaimAdjustReason = XMLUtils.getChildNodeValue(node, "CLAIMADJUSTREASON");
		String PeccancyAdjustReason = XMLUtils.getChildNodeValue(node, "PECCANCYADJUSTREASON");
		String DriverRateReason = XMLUtils.getChildNodeValue(node, "DRIVERRATEREASON");
		String DistrictRateReason = XMLUtils.getChildNodeValue(node, "DISTRICTRATEREASON");
		String LastYearStartDate = XMLUtils.getChildNodeValue(node, "LASTYEARSTARTDATE");
		String LastYearEndDate = XMLUtils.getChildNodeValue(node, "LASTYEARENDDATE");
		String RestricFlag = XMLUtils.getChildNodeValue(node, "RESTRICFLAG");
		String PreferentialPremium = XMLUtils.getChildNodeValue(node, "PREFERENTIALPREMIUM");
		String PreferentialFormula = XMLUtils.getChildNodeValue(node, "PREFERENTIALFORMULA");
		String PreferentialDay = XMLUtils.getChildNodeValue(node, "PREFERENTIALDAY");
		
		
		String checkCode = XMLUtils.getChildNodeValue(node, "CHECKCODE");  
		String questionAnswer = XMLUtils.getChildNodeValue(node, "QUESTIONANSWER"); 
		

		
		CIInsureDemandSchema schema = new CIInsureDemandSchema();
         PremCIInsureDemandSchema schema1 = new PremCIInsureDemandSchema();
		schema.setProposalNo(ProposalNo);
		schema.setPolicyNo(PolicyNo);
		schema.setLicenseNo(LicenseNo);
		schema.setDemandNo(DemandNo);
		schema.setLicenseType(LicenseType);
		schema.setUseNatureCode(UseNatureCode);
		schema.setFrameNo(FrameNo);
		schema.setEngineNo(EngineNo);
		schema.setLicenseColorCode(LicenseColorCode);
		schema.setCarOwner(CarOwner);
        if(EnrollDate!=null&&EnrollDate.equals(""))
		schema.setEnrollDate(EnrollDate);
		schema.setMakeDate(MakeDate);
		schema.setSeatCount(SeatCount);
		schema.setTonCount(TonCount);
		schema.setValidCheckDate(ValidCheckDate);
		schema.setManufacturerName(ManufacturerName);
		schema.setModelCode(ModelCode);
		schema.setBrandCName(BrandCName);
		schema.setBrandName(BrandName);
		schema.setCarKindCode(CarKindCode);
		schema.setCheckDate(CheckDate);
		schema.setEndValidDate(EndValidDate);
		schema.setCarStatus(CarStatus);
		schema.setHaulage(Haulage);
		schema.setStartDate(StartDate);
		schema.setEndDate(EndDate);
		schema.setAmount(Amount);
		schema.setPremium(Premium);
		schema.setBenchmarkPremium(BenchmarkPremium);
		schema.setPeccancyAdjustReason(PeccancyAdjustReason);
		schema.setPeccancyCoeff(PeccancyCoeff);
		schema.setClaimCoeff(ClaimCoeff);
		schema.setDriverCoeff(DriverCoeff);
		schema.setDistrictCoeff(DistrictCoeff);
		schema.setCommissionRate(CommissionRate);
		schema.setBasePremium(BasePremium);
		schema.setComCode(ComCode);
		schema.setOperatorCode(OperatorCode);
		schema.setDemandTime(DemandTime);
		schema.setRemark(Remark);
		schema.setFlag(Flag);
		schema.setBillDate(BillDate);
		schema.setReinsureFlag(ReinsureFlag);
		schema.setLastBillDate(LastBillDate);
		schema.setRateFloatFlag(RatefloatFlag);
		schema.setClaimAdjustReason(ClaimAdjustReason);
		schema.setDriverRateReason(DriverRateReason);
		schema.setDistrictRateReason(DistrictRateReason);
		schema.setLastYearStartDate(LastYearStartDate);
		schema.setLastYearEndDate(LastYearEndDate);
		schema.setRestricFlag(RestricFlag);
		schema.setPreferentialPremium(PreferentialPremium);
		schema.setPreferentialFormula(PreferentialFormula);
		schema.setPreferentialDay(PreferentialDay);
		
		schema.setCheckCode(checkCode);
		if(blProposal.getBLPrpTmain().getSize() >0){
			blProposal.getBLPrpTmain().getArr(0).setDemandNo(DemandNo);
			blProposal.getBLPrpTmain().getArr(0).setAnswer(questionAnswer);
			
			schema.setChannelType(blProposal.getBLPrpTmain().getArr(0).getChannelType());
			
		}
		
        blProposal.getBLPrpCIInsureDemand().setArr(schema1);
       
		blProposal.getBLCIInsureDemand().setArr(schema);
		
		if(blProposal.getBLPrpTitemCar().getSize()>0){
		    blProposal.getBLPrpTitemCar().getArr(0).setDemandNo(DemandNo);
		}
		
	}

	/**
     *  处理CICarShipTaxTransaction_List节点
     * @param blProposal
     * @param node
     * @throws Exception
     */
	public void processCICarShipTaxTransactionList(BLProposal blProposal, Node node) throws Exception{
		
		Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CICARSHIPTAXTRANSACTION_DATA");
		for(int i=0; i<nodes.length; i++){
			processCICarShipTaxTransactionData(blProposal, nodes[i]);
		}
	}

	public void processCICarShipTaxTransactionData(BLProposal blProposal, Node node)throws Exception {
		
		String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
		String PolicyNo = XMLUtils.getChildNodeValue(node, "POLICYNO");
		String EndorseNo = XMLUtils.getChildNodeValue(node, "ENDORSENO");
		String TransactionNo = XMLUtils.getChildNodeValue(node, "TRANSACTIONNO");
		String OldTransactionNo = XMLUtils.getChildNodeValue(node, "OLDTRANSACTIONNO");
		String TransactionTime = XMLUtils.getChildNodeValue(node, "TRANSACTIONTIME");
		String WorkDate = XMLUtils.getChildNodeValue(node, "WORKDATE");
		String SuccessDeal = XMLUtils.getChildNodeValue(node, "SUCCESSDEAL");
		String SuccessFee = XMLUtils.getChildNodeValue(node, "SUCCESSFEE");
		String PacketNo = XMLUtils.getChildNodeValue(node, "PACKETNO");
		String OldPacketNo = XMLUtils.getChildNodeValue(node, "OLDPACKETNO");
		String ReturnCode = XMLUtils.getChildNodeValue(node, "RETURNCODE");
		String PaidFreeCertificate = XMLUtils.getChildNodeValue(node, "PAIDFREECERTIFICATE");
		String TaxActual = XMLUtils.getChildNodeValue(node, "TAXACTUAL");
		String PreviousPay = XMLUtils.getChildNodeValue(node, "PREVIOUSPAY");
		String LateFee = XMLUtils.getChildNodeValue(node, "LATEFEE");
		String SumTax = XMLUtils.getChildNodeValue(node, "SUMTAX");
		String ReturnInfo = XMLUtils.getChildNodeValue(node, "RETURNINFO");
		String ExtendChar1 = XMLUtils.getChildNodeValue(node, "EXTENDCHAR1");
		String ExtendChar2 = XMLUtils.getChildNodeValue(node, "EXTENDCHAR2");
		String ExtendNum1 = XMLUtils.getChildNodeValue(node, "EXTENDNUM1");
		String ExtendNum2 = XMLUtils.getChildNodeValue(node, "EXTENDNUM2");
		String ExtendDate1 = XMLUtils.getChildNodeValue(node, "EXTENDDATE1");
		String ExtendDate2 = XMLUtils.getChildNodeValue(node, "EXTENDDATE2");
		String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
		
		CICarShipTaxTransactionSchema  schema = new CICarShipTaxTransactionSchema();
		schema.setProposalNo(ProposalNo);
		schema.setPolicyNo(PolicyNo);
		schema.setEndorseNo(EndorseNo);
		schema.setTransactionNo(TransactionNo);
		schema.setTransactionTime(TransactionTime);
		schema.setOldTransactionNo(OldTransactionNo);
		schema.setTransactionTime(TransactionTime);
		schema.setWorkDate(WorkDate);
		schema.setSuccessDeal(SuccessDeal);
		schema.setSuccessFee(SuccessFee);
		schema.setPacketNo(PacketNo);
		schema.setOldPacketNo(OldPacketNo);
		schema.setReturnCode(ReturnCode);
		schema.setPaidFreeCertificate(PaidFreeCertificate);
		schema.setTaxActual(TaxActual);
		schema.setPreviousPay(PreviousPay);
		schema.setLateFee(LateFee);
		schema.setSumTax(SumTax);
		schema.setReturnInfo(ReturnInfo);
		schema.setExtendChar1(ExtendChar1);
		schema.setExtendChar2(ExtendChar2);
		schema.setExtendDate1(ExtendDate1);
		schema.setExtendDate2(ExtendDate2);
		schema.setExtendNum1(ExtendNum1);
		schema.setExtendNum2(ExtendNum2);
		schema.setFlag(Flag);
		blProposal.getBLCICarShipTaxTransaction().setArr(schema);
	}

	/**
     *  处理CICarShipTaxDemand_List节点
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processCICarShipTaxDemandList(BLProposal blProposal, Node node) 
      throws Exception{
		
		Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CICARSHIPTAXDEMAND_DATA");
		for(int i=0; i < nodes.length; i++) {
			processCICarShipTaxDemandData(blProposal, nodes[i]);
		}
	}

	public void processCICarShipTaxDemandData(BLProposal blProposal, Node node) throws Exception{
		
	 String proposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
	 String demandNo = XMLUtils.getChildNodeValue(node, "DEMANDNO");
	 String policyNo = XMLUtils.getChildNodeValue(node, "POLICYNO");
	 String taxNo = XMLUtils.getChildNodeValue(node, "TAXNO");
	 String taxFlag = XMLUtils.getChildNodeValue(node, "TAXFLAG");
	 String licenseCategory = XMLUtils.getChildNodeValue(node, "LICENSECATEGORY");
	 String CompleteKerbMass = XMLUtils.getChildNodeValue(node, "COMPLETEKERBMASS");
	 String TaxpayerCertiType = XMLUtils.getChildNodeValue(node, "TAXPAYERCERTITYPE");
	 String IdentifyNumber = XMLUtils.getChildNodeValue(node, "IDENTIFYNUMBER");
	 String PaidCertificate = XMLUtils.getChildNodeValue(node, "PAIDCERTIFICATE");
	 String FreeCertificate = XMLUtils.getChildNodeValue(node, "FREECERTIFICATE");
	 String TaxComCode = XMLUtils.getChildNodeValue(node, "TAXCOMCODE");
	 String TaxComName = XMLUtils.getChildNodeValue(node, "TAXCOMNAME");
	 String PayerCode = XMLUtils.getChildNodeValue(node, "PAYERCODE");
	 String TaxAbateReason = XMLUtils.getChildNodeValue(node, "TAXABATEREASON");
	 String TaxAbateAmount = XMLUtils.getChildNodeValue(node, "TAXABATEAMOUNT");
	 String TaxActual = XMLUtils.getChildNodeValue(node, "TAXACTUAL");
	 String PreviousPay = XMLUtils.getChildNodeValue(node, "PREVIOUSPAY");
	 String LateFee = XMLUtils.getChildNodeValue(node, "LATEFEE");
	 String SumTax = XMLUtils.getChildNodeValue(node, "SUMTAX");
	 String TaxType = XMLUtils.getChildNodeValue(node, "TAXTYPE");
	 String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
	 String CarNumber = XMLUtils.getChildNodeValue(node, "CARNUMBER");
	 String PayStartDate = XMLUtils.getChildNodeValue(node, "PAYSTARTDATE");
	 String PayEndDate = XMLUtils.getChildNodeValue(node, "PAYENDDATE");
	 
	 CICarshipTaxDemandSchema schema = new CICarshipTaxDemandSchema();
	 schema.setProposalNo(proposalNo);
	 schema.setDemandNo(demandNo);
	 schema.setPolicyNo(policyNo);
	 schema.setTaxNo(taxNo);
	 schema.setFlag(Flag);
	 schema.setTaxFlag(taxFlag);
	 
	 schema.setLicenseCategory(licenseCategory);
	 schema.setCompleteKerbMass(CompleteKerbMass);
	 schema.setTaxpayerCertiType(TaxpayerCertiType);
	 schema.setIdentifyNumber(IdentifyNumber);
	 schema.setPaidCertificate(PaidCertificate);
	 schema.setFreeCertificate(FreeCertificate);
	 schema.setTaxComCode(TaxComCode);
	 schema.setTaxComName(TaxComName);
	 schema.setPayerCode(PayerCode);
	 schema.setTaxAbateReason(TaxAbateReason);
	 schema.setTaxAbateAmount(TaxAbateAmount);
	 schema.setTaxActual(TaxActual);
	 schema.setPreviousPay(PreviousPay);
	 schema.setLateFee(LateFee);
	 schema.setSumTax(SumTax);
	 schema.setTaxType(TaxType);
	 
	 
	 blProposal.getBLCICarshipTaxDemand().setArr(schema);
	}
	/**
	 * @author liwenlong-wb
	 * 增加主表扩展表的存储
	 * @param blProposal
	 * @param node
	 * @throws Exception
	 */
	public void processPrptmainExtrasList(BLProposal blProposal, Node node)throws Exception{
		 Node[] nodes = XMLUtils.getChildNodesByTagName(node, "PRPTMAIN_DATA");
         for (int i = 0; i < nodes.length; i++) {
             processPrpTmainExtrasData(blProposal, nodes[i]);
         }
	}
	/**
	 * @author liwenlong-wb
	 * 增加主表扩展表的存储
	 * @param blProposal
	 * @param node
	 * @throws Exception
	 */
	public void processPrpTmainExtrasData (BLProposal blProposal, Node node) 
    	throws Exception {
		
		
    	String proposalNo 	=  XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String riskCode 	=  XMLUtils.getChildNodeValue(node, "RISKCODE");
    	
    	String UNID 	=  XMLUtils.getChildNodeValue(node, "UNID");
    	
    	
    	String PayMethod = XMLUtils.getChildNodeValue(node, "PAYMETHODWX");
    	
    	
    	String UNIDFlag = XMLUtils.getChildNodeValue(node, "UNIDFLAG");
    	
    	
		String IntegralRate = XMLUtils.getChildNodeValue(node, "INTEGRALRATE");
		
    	PrpTmainExtrasSchema prpTmainExtras = new PrpTmainExtrasSchema();
    	prpTmainExtras.setProposalNo(proposalNo);
    	prpTmainExtras.setRiskCode(riskCode);
    	
    	prpTmainExtras.setUNID(UNID);
    	
    	
    	prpTmainExtras.setPayMethod(PayMethod);
    	
    	
    	prpTmainExtras.setUNIDFlag(UNIDFlag);
    	
    	
    	prpTmainExtras.setIntegralRate(IntegralRate); 
    	
    	blProposal.getBLPrpTmainExtras().setArr(prpTmainExtras);
		
    }
	/**
     *  处理prptamin_list节
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrptmainList(BLProposal blProposal, Node node)throws Exception{
    	
    	  Node[] nodes = XMLUtils.getChildNodesByTagName(node, "PRPTMAIN_DATA");
          
          for (int i = 0; i < nodes.length; i++) {
              processPrpTmainData(blProposal, nodes[i]);
          }
    	
    	
    }
    /**
     * 处理prptamin_DATA节
     * @param node Node
     * @throws Exception
     */
    public void processPrpTmainData(BLProposal blProposal, Node node) 
    	throws Exception 
    {
    	
    	String proposalNo 	=  XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String classCode  	=  XMLUtils.getChildNodeValue(node, "CLASSCODE");
    	String riskCode 	=  XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String contractNo 	=  XMLUtils.getChildNodeValue(node, "CONTRACTNO");
    	String policySort	=  XMLUtils.getChildNodeValue(node, "POLICYSORT");
    	String printNo 		=  XMLUtils.getChildNodeValue(node, "PRINTNO");
    	String businessNature= XMLUtils.getChildNodeValue(node, "BUSINESSNATURE");
    	String language 	=  XMLUtils.getChildNodeValue(node, "LANGUAGE");
    	String policyType	=  XMLUtils.getChildNodeValue(node, "POLICYTYPE"); 
    	String appliCode 	=  XMLUtils.getChildNodeValue(node, "APPLICODE"); 
    	String appliName 	=  XMLUtils.getChildNodeValue(node, "APPLINAME");
    	String appliAddress =  XMLUtils.getChildNodeValue(node, "APPLIADDRESS");
    	String insuredCode 	=  XMLUtils.getChildNodeValue(node, "INSURECODE");
    	String insuredName 	=  XMLUtils.getChildNodeValue(node, "INSUREDNAME");
    	String insuredAddress= XMLUtils.getChildNodeValue(node, "INSUREDADDRESS");
    	String operateDate 	=  XMLUtils.getChildNodeValue(node, "OPERATEDATE");
    	String startDate 	=  XMLUtils.getChildNodeValue(node, "STARTDATE");
    	String startHour  	=  XMLUtils.getChildNodeValue(node, "STARTHOUR");
    	String pureRate 	=  XMLUtils.getChildNodeValue(node, "PURERATE");
    	String disRate 		=  XMLUtils.getChildNodeValue(node, "DISRATE");
    	String discount 	=  XMLUtils.getChildNodeValue(node, "DISCOUNT");
    	String currency 	=  XMLUtils.getChildNodeValue(node, "CURRENCY");
    	String sumValue 	=  XMLUtils.getChildNodeValue(node, "SUMVALUE");
    	String sumAmount 	=  XMLUtils.getChildNodeValue(node, "SUMAMOUNT");
    	String sumDiscount 	=  XMLUtils.getChildNodeValue(node, "SUMDISCOUNT");
    	String sumPremium 	=  XMLUtils.getChildNodeValue(node, "SUMPREMIUM");
    	String sumSubPrem 	=  XMLUtils.getChildNodeValue(node, "SUMSUBPREM");
    	String sumQuantity 	=  XMLUtils.getChildNodeValue(node, "SUMQUANTITY");
    	String judicalCode 	=  XMLUtils.getChildNodeValue(node, "JUDICALCODE");
    	String judicalScope =  XMLUtils.getChildNodeValue(node, "JUDICALSCOPE");
    	String autoTransRenewFlag =XMLUtils.getChildNodeValue(node, "AUTOTRANSRENEWFLAG");
    	String argueSolution =  XMLUtils.getChildNodeValue(node, "ARGUESOLUTION");
    	String arbitBoardName=  XMLUtils.getChildNodeValue(node, "ARBITBOARDNAME");
    	String payTimes  	=  XMLUtils.getChildNodeValue(node, "PAYTIMES");
    	String endorseTimes =  XMLUtils.getChildNodeValue(node, "ENDORSETIMES");
    	String claimTimes  	=  XMLUtils.getChildNodeValue(node, "CLAIMTIMES");
    	String makeCom 		=  XMLUtils.getChildNodeValue(node, "MAKECOM");
    	String operateSite 	=  XMLUtils.getChildNodeValue(node, "OPERATESITE");
    	String comCode 		=  		XMLUtils.getChildNodeValue(node, "COMCODE");
    	String handlerCode 	=  XMLUtils.getChildNodeValue(node, "HANDLERCODE");
    	String handler1Code 	=  XMLUtils.getChildNodeValue(node, "HANDLER1CODE");
    	String approverCode 	=  XMLUtils.getChildNodeValue(node, "APPROVERCODE");
    	String underWriteCode 	=  XMLUtils.getChildNodeValue(node, "UNDERWIRTECODE");
    	String underWriteName 	=  XMLUtils.getChildNodeValue(node, "UNDERWRITENAME");
    	String operatorCode 	=  XMLUtils.getChildNodeValue(node, "OPERATORCODE");
    	String inputDate 	=  XMLUtils.getChildNodeValue(node, "INPUTDATE");
    	String inputHour 	=  XMLUtils.getChildNodeValue(node, "INPUTHOUR");
    	String underWriteEndDate 	=  XMLUtils.getChildNodeValue(node, "UNDERWRITEENDDATE");
    	String statisticsYM 	=  XMLUtils.getChildNodeValue(node, "STATISTICSYM");
    	String agentCode 	=  XMLUtils.getChildNodeValue(node, "AGENTCODE");
    	String coinsFlag 	=  XMLUtils.getChildNodeValue(node, "COINSFLAG");
    	String reinsFlag 	=  XMLUtils.getChildNodeValue(node, "REINSFLAG");
    	String allinsFlag 	=  XMLUtils.getChildNodeValue(node, "ALLINSFALG");
    	String underWriteFlag 	=  XMLUtils.getChildNodeValue(node, "UNDERWRITEFLAG");
    	String othFlag  	=  XMLUtils.getChildNodeValue(node, "OTHFLAG");
    	String flag  	=  XMLUtils.getChildNodeValue(node, "FLAG");
    	String disRate1 	=  XMLUtils.getChildNodeValue(node, "DISRATE1");
    	String businessFlag 	=  XMLUtils.getChildNodeValue(node, "BUSINESSFLAG");
    	String updaterCode 	=  XMLUtils.getChildNodeValue(node, "UPDATECODE");
    	String updateDate 	=  XMLUtils.getChildNodeValue(node, "UPDATEDATE");
    	String updateHour 	=  XMLUtils.getChildNodeValue(node, "UPDATEHOUR");
    	String signDate 	=  XMLUtils.getChildNodeValue(node, "SIGNDATE");
    	String shareHolderFlag 	=  XMLUtils.getChildNodeValue(node, "SHAREHOLDERFLAG");
    	String agreementNo 	=  XMLUtils.getChildNodeValue(node, "AGREEMENTNO");
    	String inquiryNo 	=  XMLUtils.getChildNodeValue(node, "INQUIRYNO");
    	String payMode 	=  XMLUtils.getChildNodeValue(node, "PAYMODE");
    	String remark 	=  XMLUtils.getChildNodeValue(node, "REMARK");
    	String policyNo 	=  XMLUtils.getChildNodeValue(node, "POLICYNO");
    	String visaCode 	=  XMLUtils.getChildNodeValue(node, "VISACODE");
    	String manualType 	=  XMLUtils.getChildNodeValue(node, "MANUALTYPE");
    	String businessKind 	=  XMLUtils.getChildNodeValue(node, "BUSINESSKIND");
    	String businessClass 	=  XMLUtils.getChildNodeValue(node, "BUSINESSCLASS");
    	String manualFlag 	=  XMLUtils.getChildNodeValue(node, "MANUALFLAG");
    	String businessRemark1 	=  XMLUtils.getChildNodeValue(node, "BUSINESSREMARK1");
    	String businessRemark2 	=  XMLUtils.getChildNodeValue(node, "BUSINESSREMARK2");
    	String domesticFlag 	=  XMLUtils.getChildNodeValue(node, "DOMESTICFLAG");
    	String ventureFlag 	=  XMLUtils.getChildNodeValue(node, "VENTUREFLAG");
        String EndDate      =XMLUtils.getChildNodeValue(node, "ENDDATE");
    	String bidFlag 		=  XMLUtils.getChildNodeValue(node, "BIDFLAG");
    	String agriFlag 	=  XMLUtils.getChildNodeValue(node, "AGRIFLAG");
    	String agriNature 	=  XMLUtils.getChildNodeValue(node, "AGRINATURE");
    	
    	String productCode  =  XMLUtils.getChildNodeValue(node, "PRODUCTCODE");
    	String quantityCasualty  =  XMLUtils.getChildNodeValue(node, "QUANTITYCASUALTY");
    	String casualtyFlag  =  XMLUtils.getChildNodeValue(node, "CASUALTYFLAG");
    	
        String profitMargin =XMLUtils.getChildNodeValue(node, "PROFITMARGIN");
        String riskPermium =XMLUtils.getChildNodeValue(node, "RISKPREMIUM");
        String expensespace=XMLUtils.getChildNodeValue(node, "EXPENSESPACE");
        String payMethod=XMLUtils.getChildNodeValue(node, "PAYMETHOD");
        String profitbusinessKind=XMLUtils.getChildNodeValue(node, "PROFITBUSINESSKIND");
        String feeClass=XMLUtils.getChildNodeValue(node, "FEECLASS");
        
        String ip = XMLUtils.getChildNodeValue(node, "COMPUTERIP");
        
        
        
        String channelType=XMLUtils.getChildNodeValue(node, "CHANNELTYPE");
        
        
        String zeroProfitPremium=XMLUtils.getChildNodeValue(node, "ZEROPROFITSPREMIUM");
        
        
        String fixedProfitMargin=XMLUtils.getChildNodeValue(node, "FIXEDPROFITMARGIN");
        String immeValiFlag=XMLUtils.getChildNodeValue(node, "IMMEVALIDFLAG");
        
        
        String immeValidStartDate=XMLUtils.getChildNodeValue(node, "IMMEVALIDSTARTDATE");
        String immeValidEndDate=XMLUtils.getChildNodeValue(node, "IMMEVALIDENDDATE");
        
        
        String sendLastPolicyNo=XMLUtils.getChildNodeValue(node, "SENDLASTPOLICYNO");
        
        
        String appliType = XMLUtils.getChildNodeValue(node, "APPLITYPE");
        
		
		String ReinsureFlagBom = XMLUtils.getChildNodeValue(node, "REINSUREFLAGBOM");
		
		
		String BusinessModel = XMLUtils.getChildNodeValue(node, "BUSINESSMODEL");
		
		
		String KindAdjustReason = XMLUtils.getChildNodeValue(node, "KINDADJUSTREASON");
		
		
		String ServiceArea = XMLUtils.getChildNodeValue(node, "SERVICEAREA");
		
		
        
        PrpTmainSchema schema = new PrpTmainSchema();

        schema.setProposalNo(proposalNo);

        schema.setProfitMargin(profitMargin);
        schema.setRiskPremium(riskPermium);
        schema.setExpenseSpace(expensespace);
        schema.setProfitBusinessKind(profitbusinessKind);
        schema.setFeeClass(feeClass);
        
        schema.setClassCode(classCode);
        schema.setRiskCode(riskCode);
        schema.setContractNo(contractNo);
        schema.setPolicySort(policySort);
        schema.setPrintNo(printNo);
        schema.setBusinessNature(businessNature);
        schema.setLanguage(language);
        schema.setPolicyType(policyType);
        schema.setAppliCode(appliCode);
        schema.setAppliName(appliName);
        schema.setAppliAddress(appliAddress);
        schema.setInsuredCode(insuredCode);
        schema.setInsuredName(insuredName);
        
        schema.setInsuredAddress(insuredAddress);
        schema.setOperateDate(operateDate);
        schema.setStartDate(startDate);
        schema.setStartHour(startHour);
        schema.setPureRate(pureRate);
        schema.setDisRate(disRate);
        schema.setDiscount(discount);
        schema.setCurrency(currency);
        schema.setSumValue(sumValue);
        schema.setSumAmount(sumAmount);
        schema.setSumDiscount(sumDiscount);
        schema.setSumPremium(sumPremium);
        schema.setSumSubPrem(sumSubPrem);
        schema.setSumQuantity(sumQuantity);
        schema.setJudicalCode(judicalCode);
        schema.setJudicalScope(judicalScope);
        schema.setAutoTransRenewFlag(autoTransRenewFlag);
        schema.setArgueSolution(argueSolution);
        schema.setArbitBoardName(arbitBoardName);
        schema.setPayTimes(payTimes);
        schema.setEndorseTimes(endorseTimes);
        schema.setClaimTimes(claimTimes);
        schema.setMakeCom(makeCom);
        schema.setOperateSite(operateSite);
        schema.setComCode(comCode);
        schema.setHandlerCode(handlerCode);
        schema.setHandler1Code(handler1Code);
        schema.setApproverCode(approverCode);
        schema.setUnderWriteCode(underWriteCode);
        schema.setUnderWriteName(underWriteName);
        schema.setOperatorCode(operatorCode);
        schema.setInputDate(inputDate);
        schema.setInputHour(inputHour);
        if(underWriteEndDate!=null&&!underWriteEndDate.trim().equals(""))
        schema.setUnderWriteEndDate(underWriteEndDate.trim());
        
        schema.setStatisticsYM(startDate);
        
        schema.setServiceArea(ServiceArea);
        
        schema.setCoinsFlag("0");
        schema.setReinsFlag(reinsFlag);
        schema.setAllinsFlag("0");
        schema.setUnderWriteFlag("0");
        schema.setOthFlag(othFlag);
        schema.setFlag(flag);
        schema.setDisRate1(disRate1);
        schema.setBusinessFlag(businessFlag);
        schema.setEndDate(EndDate);
        
        schema.setEndHour("24");
        
        schema.setUpdaterCode(operatorCode);
        schema.setUpdateDate(updateDate);
        schema.setUpdateHour(updateHour);
        schema.setSignDate(signDate);
        schema.setShareHolderFlag(shareHolderFlag);
        schema.setAgreementNo(agreementNo);
        schema.setInquiryNo(inquiryNo);
        schema.setPayMode(payMode);
        schema.setRemark(remark);
        schema.setPolicyNo(policyNo);
        schema.setVisaCode(visaCode);
        schema.setManualType(manualType);
        schema.setBusinessKind(businessKind);
        schema.setBusinessClass(businessClass);
        
        
        manualFlag="0";
        schema.setManualFlag(manualFlag);
        schema.setBusinessRemark1(businessRemark1);
        schema.setBusinessRemark2(businessRemark2);
        schema.setDomesticFlag(domesticFlag);
        
        schema.setVentureFlag(ventureFlag);
        
        schema.setBidFlag(bidFlag);
        
        
        
        schema.setAgriFlag(agriFlag);
        schema.setAgriNature(agriNature);
        schema.setGroupPurchaseFlag("0");
        schema.setPayMethod(payMethod);
        
        schema.setChannelType(channelType);
        
        
        schema.setZeroProfitPremium(zeroProfitPremium);
        
        
        schema.setFixedProfitMargin(fixedProfitMargin);
        schema.setImmeValidFlag(immeValiFlag);
        
        
        schema.setImmeValidStartDate(immeValidStartDate);
        schema.setImmeValidEndDate(immeValidEndDate);
        
        
        schema.setSendLastPolicyNo(sendLastPolicyNo);
        

        
        
        schema.setIP(ip);
        
        
        schema.setProductCode(productCode);
        schema.setCasualtyFlag(casualtyFlag);
        schema.setQuantityCasualty(quantityCasualty);
        
        
        schema.setAppliType(appliType);
        
        
		schema.setReinsureFlagBom(ReinsureFlagBom);
		
		
		schema.setBusinessModel(BusinessModel);
		
		schema.setKindAdjustReason(KindAdjustReason);
        blProposal.getBLPrpTmain().setArr(schema);

    }

    public String processPrpTmainDataBC(BLProposal blProposalbi,BLProposal blProposalci, Node node) 
        	throws Exception 
        {
        	
	    	String riskCodebi ="";
	    	String riskCodeci ="";
	    	String mainsub="";
        	 riskCodebi 	=  XMLUtils.getChildNodeValue(node, "RISKCODEBI");
        	 riskCodeci 	=  XMLUtils.getChildNodeValue(node, "RISKCODECI");
        	 if(!"".equals(riskCodebi)&&riskCodebi!=null&&!"".equals(riskCodeci)&&riskCodeci!=null){
        		 mainsub="1";
        	 }
        	 if(!"".equals(riskCodebi)&&riskCodebi!=null&&("".equals(riskCodeci)||riskCodeci==null)){
        		 mainsub="2";
        	 }
        	 
        	 if(("".equals(riskCodebi)||riskCodebi==null)&&!"".equals(riskCodeci)&&riskCodeci!=null){
        		 mainsub="3";
        	 }
        	String classCode  	=  XMLUtils.getChildNodeValue(node, "CLASSCODE");
        	String contractNo 	=  XMLUtils.getChildNodeValue(node, "CONTRACTNO");
        	String policySort	=  XMLUtils.getChildNodeValue(node, "POLICYSORT");
        	String printNo 		=  XMLUtils.getChildNodeValue(node, "PRINTNO");
        	String infoTransNo 		=  XMLUtils.getChildNodeValue(node, "INFOTRANSNO");
        	String businessNature= XMLUtils.getChildNodeValue(node, "BUSINESSNATURE");
        	String language 	=  XMLUtils.getChildNodeValue(node, "LANGUAGE");
        	String policyType	=  XMLUtils.getChildNodeValue(node, "POLICYTYPE"); 
        	String appliCode 	=  XMLUtils.getChildNodeValue(node, "APPLICODE"); 
        	String appliName 	=  XMLUtils.getChildNodeValue(node, "APPLINAME");
        	String appliAddress =  XMLUtils.getChildNodeValue(node, "APPLIADDRESS");
        	String insuredCode 	=  XMLUtils.getChildNodeValue(node, "INSURECODE");
        	String insuredName 	=  XMLUtils.getChildNodeValue(node, "INSUREDNAME");
        	String insuredAddress= XMLUtils.getChildNodeValue(node, "INSUREDADDRESS");
        	String operateDate 	=  XMLUtils.getChildNodeValue(node, "OPERATEDATE");
        	String startDatebi 	=  XMLUtils.getChildNodeValue(node, "STARTDATEBI");
        	String startDateci 	=  XMLUtils.getChildNodeValue(node, "STARTDATECI");
        	String endDatebi 	=  XMLUtils.getChildNodeValue(node, "ENDDATEBI");
        	String endDateci 	=  XMLUtils.getChildNodeValue(node, "ENDDATECI");
        	
        
        	String pureRatebi 	=  XMLUtils.getChildNodeValue(node, "PURERATEBI");
        	String pureRateci 	=  XMLUtils.getChildNodeValue(node, "PURERATECI");
        	
        	String disRatebi		=  XMLUtils.getChildNodeValue(node, "DISRATEBI");
        	String disRateci 		=  XMLUtils.getChildNodeValue(node, "DISRATECI");
        	String discountbi 	=  XMLUtils.getChildNodeValue(node, "DISCOUNTBI");
        	String discountci 	=  XMLUtils.getChildNodeValue(node, "DISCOUNTCI");
        	String currency 	=  XMLUtils.getChildNodeValue(node, "CURRENCY");
        	String sumValue 	=  XMLUtils.getChildNodeValue(node, "SUMVALUE");
        	String sumAmountbi 	=  XMLUtils.getChildNodeValue(node, "SUMAMOUNTBI");
        	String sumAmountci 	=  XMLUtils.getChildNodeValue(node, "SUMAMOUNTCI");
        	
        	String sumProfitci 	=  XMLUtils.getChildNodeValue(node, "SUMPROFITCI");
        	String sumProfitbi 	=  XMLUtils.getChildNodeValue(node, "SUMPROFITBI");
        	
        	String validnoci 	=  XMLUtils.getChildNodeValue(node, "VALIDNOCI");
        	String validnobi 	=  XMLUtils.getChildNodeValue(node, "VALIDNOBI");
        	
        	
        	
        	
        	String sumDiscount 	=  XMLUtils.getChildNodeValue(node, "SUMDISCOUNT");
        	String sumPremiumci 	=  XMLUtils.getChildNodeValue(node, "SUMPREMIUMCI");
        	String sumPremiumbi 	=  XMLUtils.getChildNodeValue(node, "SUMPREMIUMBI");
        	
        	String sumSubPrembi 	=  XMLUtils.getChildNodeValue(node, "SUMSUBPREMBI");
        	String sumSubPremci 	=  XMLUtils.getChildNodeValue(node, "SUMSUBPREMCI");
        	
        	String sumQuantity 	=  XMLUtils.getChildNodeValue(node, "SUMQUANTITY");
        	String judicalCode 	=  XMLUtils.getChildNodeValue(node, "JUDICALCODE");
        	String judicalScope =  XMLUtils.getChildNodeValue(node, "JUDICALSCOPE");
        	String autoTransRenewFlag =XMLUtils.getChildNodeValue(node, "AUTOTRANSRENEWFLAG");
        	String argueSolution =  XMLUtils.getChildNodeValue(node, "ARGUESOLUTION");
        	String arbitBoardName=  XMLUtils.getChildNodeValue(node, "ARBITBOARDNAME");
        	String payTimes  	=  XMLUtils.getChildNodeValue(node, "PAYTIMES");
        	String endorseTimes =  XMLUtils.getChildNodeValue(node, "ENDORSETIMES");
        	String claimTimes  	=  XMLUtils.getChildNodeValue(node, "CLAIMTIMES");
        	String makeCom 		=  XMLUtils.getChildNodeValue(node, "MAKECOM");
        	String operateSite 	=  XMLUtils.getChildNodeValue(node, "OPERATESITE");
        	String comCode 		=  		XMLUtils.getChildNodeValue(node, "COMCODE");
        	String handlerCode 	=  XMLUtils.getChildNodeValue(node, "HANDLERCODE");
        	String handler1Code 	=  XMLUtils.getChildNodeValue(node, "HANDLER1CODE");
        	String approverCode 	=  XMLUtils.getChildNodeValue(node, "APPROVERCODE");
        	String underWriteCode 	=  XMLUtils.getChildNodeValue(node, "UNDERWIRTECODE");
        	String underWriteName 	=  XMLUtils.getChildNodeValue(node, "UNDERWRITENAME");
        	String operatorCode 	=  XMLUtils.getChildNodeValue(node, "OPERATORCODE");
        	String inputDate 	=  XMLUtils.getChildNodeValue(node, "INPUTDATE");
        	String inputHour 	=  XMLUtils.getChildNodeValue(node, "INPUTHOUR");
        	String underWriteEndDate 	=  XMLUtils.getChildNodeValue(node, "UNDERWRITEENDDATE");
        	String statisticsYM 	=  XMLUtils.getChildNodeValue(node, "STATISTICSYM");
        	String agentCode 	=  XMLUtils.getChildNodeValue(node, "AGENTCODE");
        	String coinsFlag 	=  XMLUtils.getChildNodeValue(node, "COINSFLAG");
        	String reinsFlag 	=  XMLUtils.getChildNodeValue(node, "REINSFLAG");
        	String allinsFlag 	=  XMLUtils.getChildNodeValue(node, "ALLINSFALG");
        	String underWriteFlag 	=  XMLUtils.getChildNodeValue(node, "UNDERWRITEFLAG");
        	
        	String othFlagCI 	=  XMLUtils.getChildNodeValue(node, "OTHFLAGCI");
        	String othFlagBI 	=  XMLUtils.getChildNodeValue(node, "OTHFLAGBI");
        	String flag  	=  XMLUtils.getChildNodeValue(node, "FLAG");
        	String disRate1bi 	=  XMLUtils.getChildNodeValue(node, "DISRATE1BI");
        	String disRate1ci	=  XMLUtils.getChildNodeValue(node, "DISRATE1CI");
        	String demandnoci	=  XMLUtils.getChildNodeValue(node, "DEMANDNOCI");
        	String demandnobi	=  XMLUtils.getChildNodeValue(node, "DEMANDNOBI");
        	String businessFlag 	=  XMLUtils.getChildNodeValue(node, "BUSINESSFLAG");
        	String updaterCode 	=  XMLUtils.getChildNodeValue(node, "UPDATECODE");
        	String updateDate 	=  XMLUtils.getChildNodeValue(node, "UPDATEDATE");
        	String updateHour 	=  XMLUtils.getChildNodeValue(node, "UPDATEHOUR");
        	String signDate 	=  XMLUtils.getChildNodeValue(node, "SIGNDATE");
        	String shareHolderFlag 	=  XMLUtils.getChildNodeValue(node, "SHAREHOLDERFLAG");
        	String agreementNo 	=  XMLUtils.getChildNodeValue(node, "AGREEMENTNO");
        	String inquiryNo 	=  XMLUtils.getChildNodeValue(node, "INQUIRYNO");
        	String payMode 	=  XMLUtils.getChildNodeValue(node, "PAYMODE");
        	String remark 	=  XMLUtils.getChildNodeValue(node, "REMARK");
        	String policyNo 	=  XMLUtils.getChildNodeValue(node, "POLICYNO");
        	String visaCode 	=  XMLUtils.getChildNodeValue(node, "VISACODE");
        	String manualType 	=  XMLUtils.getChildNodeValue(node, "MANUALTYPE");
        	String businessKindci 	=  XMLUtils.getChildNodeValue(node, "BUSINESSKINDCI");
        	String businessKindbi 	=  XMLUtils.getChildNodeValue(node, "BUSINESSKINDBI");
        	
        	
        	String businessClassbi 	=  XMLUtils.getChildNodeValue(node, "BUSINESSCLASSBI");
        	String businessClassci	=  XMLUtils.getChildNodeValue(node, "BUSINESSCLASSCI");
        	
        	
        	String manualFlag 	=  XMLUtils.getChildNodeValue(node, "MANUALFLAG");
        	String businessRemark1 	=  XMLUtils.getChildNodeValue(node, "BUSINESSREMARK1");
        	String businessRemark2 	=  XMLUtils.getChildNodeValue(node, "BUSINESSREMARK2");
        	String domesticFlag 	=  XMLUtils.getChildNodeValue(node, "DOMESTICFLAG");
        	String ventureFlag 	=  XMLUtils.getChildNodeValue(node, "VENTUREFLAG");
            String EndDatebi     =XMLUtils.getChildNodeValue(node, "ENDDATEBI");
            String EndDateci      =XMLUtils.getChildNodeValue(node, "ENDDATECI");
        	String bidFlag 		=  XMLUtils.getChildNodeValue(node, "BIDFLAG");
        	String agriFlag 	=  XMLUtils.getChildNodeValue(node, "AGRIFLAG");
        	String agriNature 	=  XMLUtils.getChildNodeValue(node, "AGRINATURE");
        	
        	String productCode  =  XMLUtils.getChildNodeValue(node, "PRODUCTCODE");
        	String quantityCasualty  =  XMLUtils.getChildNodeValue(node, "QUANTITYCASUALTY");
        	String casualtyFlag  =  XMLUtils.getChildNodeValue(node, "CASUALTYFLAG");
        	
            String profitMarginbi =XMLUtils.getChildNodeValue(node, "PROFITMARGINBI");
            String profitMarginci =XMLUtils.getChildNodeValue(node, "PROFITMARGINCI");
            
            String riskPermiumbi =XMLUtils.getChildNodeValue(node, "RISKPREMIUMBI");
            String riskPermiumci =XMLUtils.getChildNodeValue(node, "RISKPREMIUMCI");
            
            
            String expensespacebi=XMLUtils.getChildNodeValue(node, "EXPENSESPACEBI");
            String expensespaceci=XMLUtils.getChildNodeValue(node, "EXPENSESPACECI");
            String payMethodbi=XMLUtils.getChildNodeValue(node, "PAYMETHODBI");
            String payMethodci=XMLUtils.getChildNodeValue(node, "PAYMETHODCI");
            String profitbusinessKindbi=XMLUtils.getChildNodeValue(node, "PROFITBUSINESSKINDBI");
            String profitbusinessKindci=XMLUtils.getChildNodeValue(node, "PROFITBUSINESSKINDCI");
            String feeClassbi=XMLUtils.getChildNodeValue(node, "FEECLASSBI");
            String feeClassci=XMLUtils.getChildNodeValue(node, "FEECLASSCI");
            
            String salessalaryfeeci=XMLUtils.getChildNodeValue(node, "SALESSALARYFEECI");
            String salessalaryfeebi=XMLUtils.getChildNodeValue(node, "SALESSALARYFEEBI");
            
            String managefeefeeci=XMLUtils.getChildNodeValue(node, "MANAGEFEEFEECI");
            String managefeefeebi=XMLUtils.getChildNodeValue(node, "MANAGEFEEFEEBI");
            
            String disratefeeci=XMLUtils.getChildNodeValue(node, "DISRATEFEECI");
            String disratefeebi=XMLUtils.getChildNodeValue(node, "DISRATEFEEBI");
            
            
            
            String ip = XMLUtils.getChildNodeValue(node, "COMPUTERIP");
            
            
            
            String channelType=XMLUtils.getChildNodeValue(node, "CHANNELTYPE");
            
            
            String zeroProfitPremiumci=XMLUtils.getChildNodeValue(node, "ZEROPROFITSPREMIUMCI");
            String zeroProfitPremiumbi=XMLUtils.getChildNodeValue(node, "ZEROPROFITSPREMIUMBI");
            
            
            String fixedProfitMarginbi=XMLUtils.getChildNodeValue(node, "FIXEDPROFITMARGINBI");
            
            String fixedProfitMarginci=XMLUtils.getChildNodeValue(node, "FIXEDPROFITMARGINCI");
            String immeValiFlagbi=XMLUtils.getChildNodeValue(node, "IMMEVALIDFLAGBI");
            String immeValiFlagci=XMLUtils.getChildNodeValue(node, "IMMEVALIDFLAGCI");
            
            
            String immeValidStartDatebi=XMLUtils.getChildNodeValue(node, "IMMEVALIDSTARTDATEBI");
            String immeValidStartDateci=XMLUtils.getChildNodeValue(node, "IMMEVALIDSTARTDATECI");
            String immeValidEndDatebi=XMLUtils.getChildNodeValue(node, "IMMEVALIDENDDATEBI");
            String immeValidEndDateci=XMLUtils.getChildNodeValue(node, "IMMEVALIDENDDATECI");
            
            
            String sendLastPolicyNo=XMLUtils.getChildNodeValue(node, "SENDLASTPOLICYNO");
            
            
            String appliType = XMLUtils.getChildNodeValue(node, "APPLITYPE");
            
    		
    		String ReinsureFlagBom = XMLUtils.getChildNodeValue(node, "REINSUREFLAGBOM");
    		
    		
    		String BusinessModel = XMLUtils.getChildNodeValue(node, "BUSINESSMODEL");
    		
    		
    		String KindAdjustReason = XMLUtils.getChildNodeValue(node, "KINDADJUSTREASON");
    		
    		String firbennefitInfo = XMLUtils.getChildNodeValue(node, "FIRBENNEFITINFO");
    		
    		
    		String ModelInfoID = XMLUtils.getChildNodeValue(node, "MODELINFOID");

    		
    		
    		
    		String ServiceArea = XMLUtils.getChildNodeValue(node, "SERVICEAREA");
    		
        	  
    		
    		PrpTmainSchema schemabi =null;
    		PrpTmainSchema schemaci =null;
    		if(!"".equals(riskCodebi)&&riskCodebi!=null){
    			PrpTNewEngageSchema schemabi2 = new PrpTNewEngageSchema();
            	schemabi2.setFirbennefitInfo(firbennefitInfo);
            	blProposalbi.getBlPrpTNewEngage().setArr(schemabi2);
    			schemabi = new PrpTmainSchema();


    			
    			schemabi.setDemandNo(demandnobi);
    			
    			schemabi.setInfoTransNo(infoTransNo);
                schemabi.setProfitMargin(profitMarginbi);
                schemabi.setRiskPremium(riskPermiumbi);
                schemabi.setExpenseSpace(expensespacebi);
                schemabi.setProfitBusinessKind(profitbusinessKindbi);
                schemabi.setFeeClass(feeClassbi);
                
                schemabi.setClassCode(classCode);
                schemabi.setRiskCode(riskCodebi);
                schemabi.setContractNo(contractNo);
                schemabi.setPolicySort(policySort);
                schemabi.setPrintNo(printNo);
                schemabi.setBusinessNature(businessNature);
                schemabi.setLanguage(language);
                schemabi.setPolicyType(policyType);
                schemabi.setAppliCode(appliCode);
                schemabi.setAppliName(appliName);
                schemabi.setAppliAddress(appliAddress);
                schemabi.setInsuredCode(insuredCode);
                schemabi.setInsuredName(insuredName);
                schemabi.setEndDate(endDatebi);
                schemabi.setInsuredAddress(insuredAddress);
                schemabi.setOperateDate(operateDate);
                schemabi.setStartDate(startDatebi);
                schemabi.setStartHour("0");
                schemabi.setPureRate(pureRatebi);
                schemabi.setDisRate(disRatebi);
                schemabi.setDiscount(discountbi);
                schemabi.setCurrency(currency);
                schemabi.setSumValue(sumValue);
                schemabi.setSumAmount(sumAmountbi);
                schemabi.setSumDiscount(sumDiscount);
                schemabi.setSumPremium(sumPremiumbi);
                schemabi.setSumSubPrem(sumSubPrembi);
                schemabi.setSumQuantity(sumQuantity);
                schemabi.setJudicalCode(judicalCode);
                schemabi.setJudicalScope(judicalScope);
                schemabi.setAutoTransRenewFlag(autoTransRenewFlag);
                schemabi.setArgueSolution(argueSolution);
                schemabi.setArbitBoardName(arbitBoardName);
                schemabi.setPayTimes(payTimes);
                schemabi.setEndorseTimes(endorseTimes);
                schemabi.setClaimTimes(claimTimes);
                schemabi.setMakeCom(makeCom);
                schemabi.setOperateSite(operateSite);
                schemabi.setComCode(comCode);
                schemabi.setHandlerCode(handlerCode);
                schemabi.setHandler1Code(handler1Code);
                schemabi.setApproverCode(approverCode);
                schemabi.setUnderWriteCode(underWriteCode);
                schemabi.setUnderWriteName(underWriteName);
                schemabi.setOperatorCode(operatorCode);
                schemabi.setInputDate(inputDate);
                schemabi.setInputHour(inputHour);
                if(underWriteEndDate!=null&&!underWriteEndDate.trim().equals(""))
                schemabi.setUnderWriteEndDate(underWriteEndDate.trim());
                
                schemabi.setStatisticsYM(startDatebi);
                
                
                schemabi.setServiceArea(ServiceArea);
                
                schemabi.setCoinsFlag("0");
                schemabi.setReinsFlag(reinsFlag);
                schemabi.setAllinsFlag("0");
                schemabi.setUnderWriteFlag("0");
                schemabi.setOthFlag(othFlagBI);
                schemabi.setFlag(flag);
                schemabi.setDisRate1(disRate1bi);
                schemabi.setBusinessFlag(businessFlag);
                schemabi.setEndDate(EndDatebi);
                
                schemabi.setEndHour("24");
                
                schemabi.setUpdaterCode(operatorCode);
                schemabi.setUpdateDate(updateDate);
                schemabi.setUpdateHour(updateHour);
                schemabi.setSignDate(signDate);
                schemabi.setShareHolderFlag(shareHolderFlag);
                schemabi.setAgreementNo(agreementNo);
                schemabi.setInquiryNo(inquiryNo);
                schemabi.setPayMode(payMode);
                schemabi.setRemark(remark);
                schemabi.setPolicyNo(policyNo);
                schemabi.setVisaCode(visaCode);
                schemabi.setManualType(manualType);
                schemabi.setBusinessKind(businessKindbi);
                schemabi.setBusinessClass(businessClassbi);
                
                
                manualFlag="0";
                schemabi.setManualFlag(manualFlag);
                schemabi.setBusinessRemark1(businessRemark1);
                schemabi.setBusinessRemark2(businessRemark2);
                schemabi.setDomesticFlag(domesticFlag);
                
                schemabi.setVentureFlag(ventureFlag);
                
                schemabi.setBidFlag(bidFlag);
                
                
                
                schemabi.setAgriFlag(agriFlag);
                schemabi.setAgriNature(agriNature);
                schemabi.setGroupPurchaseFlag("0");
                schemabi.setPayMethod(payMethodbi);
                
                schemabi.setChannelType(channelType);
                
                
                schemabi.setZeroProfitPremium(zeroProfitPremiumbi);
                
                
                schemabi.setFixedProfitMargin(fixedProfitMarginbi);
                schemabi.setImmeValidFlag(immeValiFlagbi);
                
                
                schemabi.setImmeValidStartDate(immeValidStartDatebi);
                schemabi.setImmeValidEndDate(immeValidEndDatebi);
                
                
                schemabi.setSendLastPolicyNo(sendLastPolicyNo);
                

                
                
                schemabi.setIP(ip);
                
                
                schemabi.setProductCode(productCode);
                schemabi.setCasualtyFlag(casualtyFlag);
                schemabi.setQuantityCasualty(quantityCasualty);
                
                
                schemabi.setAppliType(appliType);
                
                
        		schemabi.setReinsureFlagBom(ReinsureFlagBom);
        		
        		
        		schemabi.setBusinessModel(BusinessModel);
        		
        		schemabi.setKindAdjustReason(KindAdjustReason);
        		
        		
        		schemabi.setModelInfoID(ModelInfoID);
        		
        		
        		blProposalbi.getBLPrpTmain().setArr(schemabi);
        		
        		PrpTmainExtrasSchema prpTmainExtras = new PrpTmainExtrasSchema();
        		String UNID = XMLUtils.getChildNodeValue(node, "UNID");
        		prpTmainExtras.setUNID(UNID);
        		
        		String UNIDFLAG = XMLUtils.getChildNodeValue(node, "UNIDFLAG");
        		prpTmainExtras.setUNIDFlag(UNIDFLAG);
        		
        		blProposalbi.getBLPrpTmainExtras().setArr(prpTmainExtras);
        		
    		}
    		
    		if(!"".equals(riskCodeci)&&riskCodeci!=null){
    			PrpTNewEngageSchema schemaci1 = new PrpTNewEngageSchema();
            	schemaci1.setFirbennefitInfo(firbennefitInfo);
            	blProposalci.getBlPrpTNewEngage().setArr(schemaci1);   
    			schemaci = new PrpTmainSchema();
    			
    			schemaci.setDemandNo(demandnoci);
    			
    			 schemaci.setProfitMargin(profitMarginci);
                 schemaci.setRiskPremium(riskPermiumci);
                 schemaci.setExpenseSpace(expensespaceci);
                 schemaci.setProfitBusinessKind(profitbusinessKindci);
                 schemaci.setFeeClass(feeClassci);
                 
                 schemaci.setClassCode(classCode);
                 schemaci.setRiskCode(riskCodeci);
                 schemaci.setContractNo(contractNo);
                 schemaci.setInfoTransNo(infoTransNo);
                 schemaci.setPolicySort(policySort);
                 schemaci.setPrintNo(printNo);
                 schemaci.setBusinessNature(businessNature);
                 schemaci.setLanguage(language);
                 schemaci.setPolicyType(policyType);
                 schemaci.setAppliCode(appliCode);
                 schemaci.setAppliName(appliName);
                 schemaci.setAppliAddress(appliAddress);
                 schemaci.setInsuredCode(insuredCode);
                 schemaci.setInsuredName(insuredName);
                 schemaci.setEndDate(endDateci);
                 schemaci.setInsuredAddress(insuredAddress);
                 schemaci.setOperateDate(operateDate);
                 schemaci.setStartDate(startDateci);
                 schemaci.setStartHour("0");
                 schemaci.setPureRate(pureRateci);
                 schemaci.setDisRate(disRateci);
                 schemaci.setDiscount(discountci);
                 schemaci.setCurrency(currency);
                 schemaci.setSumValue(sumValue);
                 schemaci.setSumAmount(sumAmountci);
                 schemaci.setSumDiscount(sumDiscount);
                 schemaci.setSumPremium(sumPremiumci);
                 schemaci.setSumSubPrem(sumSubPremci);
                 schemaci.setSumQuantity(sumQuantity);
                 schemaci.setJudicalCode(judicalCode);
                 schemaci.setJudicalScope(judicalScope);
                 schemaci.setAutoTransRenewFlag(autoTransRenewFlag);
                 schemaci.setArgueSolution(argueSolution);
                 schemaci.setArbitBoardName(arbitBoardName);
                 schemaci.setPayTimes(payTimes);
                 schemaci.setEndorseTimes(endorseTimes);
                 schemaci.setClaimTimes(claimTimes);
                 schemaci.setMakeCom(makeCom);
                 schemaci.setOperateSite(operateSite);
                 schemaci.setComCode(comCode);
                 schemaci.setHandlerCode(handlerCode);
                 schemaci.setHandler1Code(handler1Code);
                 schemaci.setApproverCode(approverCode);
                 schemaci.setUnderWriteCode(underWriteCode);
                 schemaci.setUnderWriteName(underWriteName);
                 schemaci.setOperatorCode(operatorCode);
                 schemaci.setInputDate(inputDate);
                 schemaci.setInputHour(inputHour);
                 if(underWriteEndDate!=null&&!underWriteEndDate.trim().equals(""))
                 schemaci.setUnderWriteEndDate(underWriteEndDate.trim());
                 
                 schemaci.setStatisticsYM(startDateci);
                 
                 
                schemaci.setServiceArea(ServiceArea);
                 
                 schemaci.setCoinsFlag("0");
                 schemaci.setReinsFlag(reinsFlag);
                 schemaci.setAllinsFlag("0");
                 schemaci.setUnderWriteFlag("0");
                 schemaci.setOthFlag(othFlagCI);
                 schemaci.setFlag(flag);
                 schemaci.setDisRate1(disRate1ci);
                 schemaci.setBusinessFlag(businessFlag);
                 schemaci.setEndDate(EndDateci);
                 
                 schemaci.setEndHour("24");
                 
                 schemaci.setUpdaterCode(operatorCode);
                 schemaci.setUpdateDate(updateDate);
                 schemaci.setUpdateHour(updateHour);
                 schemaci.setSignDate(signDate);
                 schemaci.setShareHolderFlag(shareHolderFlag);
                 schemaci.setAgreementNo(agreementNo);
                 schemaci.setInquiryNo(inquiryNo);
                 schemaci.setPayMode(payMode);
                 schemaci.setRemark(remark);
                 schemaci.setPolicyNo(policyNo);
                 schemaci.setVisaCode(visaCode);
                 schemaci.setManualType(manualType);
                 schemaci.setBusinessKind(businessKindci);
                 schemaci.setBusinessClass(businessClassci);
                 
                 
                 manualFlag="0";
                 schemaci.setManualFlag(manualFlag);
                 schemaci.setBusinessRemark1(businessRemark1);
                 schemaci.setBusinessRemark2(businessRemark2);
                 schemaci.setDomesticFlag(domesticFlag);
                 
                 schemaci.setVentureFlag(ventureFlag);
                 
                 schemaci.setBidFlag(bidFlag);
                 
                 
                 
                 schemaci.setAgriFlag(agriFlag);
                 schemaci.setAgriNature(agriNature);
                 schemaci.setGroupPurchaseFlag("0");
                 schemaci.setPayMethod(payMethodci);
                 
                 schemaci.setChannelType(channelType);
                 
                 
                 schemaci.setZeroProfitPremium(zeroProfitPremiumci);
                 
                 
                 schemaci.setFixedProfitMargin(fixedProfitMarginci);
                 schemaci.setImmeValidFlag(immeValiFlagci);
                 
                 
                 schemaci.setImmeValidStartDate(immeValidStartDateci);
                 schemaci.setImmeValidEndDate(immeValidEndDateci);
                 
                 
                 schemaci.setSendLastPolicyNo(sendLastPolicyNo);
                 

                 
                 
                 schemaci.setIP(ip);
                 
                 
                 schemaci.setProductCode(productCode);
                 schemaci.setCasualtyFlag(casualtyFlag);
                 schemaci.setQuantityCasualty(quantityCasualty);
                 
                 
                 schemaci.setAppliType(appliType);
                 
                 
         		schemaci.setReinsureFlagBom(ReinsureFlagBom);
         		
         		
         		schemaci.setBusinessModel(BusinessModel);
         		
         		schemaci.setKindAdjustReason(KindAdjustReason);
         		
          		
        		schemaci.setModelInfoID(ModelInfoID);
        		
         		blProposalci.getBLPrpTmain().setArr(schemaci);
          		
        		PrpTmainExtrasSchema prpTmainExtras = new PrpTmainExtrasSchema();
        		String UNID = XMLUtils.getChildNodeValue(node, "UNID");
        		prpTmainExtras.setUNID(UNID);
        		
        		String UNIDFLAG = XMLUtils.getChildNodeValue(node, "UNIDFLAG");
        		prpTmainExtras.setUNIDFlag(UNIDFLAG);
        		
        		blProposalci.getBLPrpTmainExtras().setArr(prpTmainExtras);
        		
    		
    		}
    		
           return mainsub;

        }
    /**
     * @desc  处理prptfeelist
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTfeeList(BLProposal blProposal, Node node)
    throws Exception{
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "PRPTFEE_DATA");
    	for(int i=0;i<nodes.length;i++){
            processPrpTfeeData(blProposal,nodes[i]);
    	}
    }
    /**
     * @desc  prptfee_Data
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTfeeData(BLProposal blProposal, Node node) 
    throws Exception{
    	
    	String proposalNo  = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String riskCode  = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String currency  = XMLUtils.getChildNodeValue(node, "CURRENCY");
    	String amount  = XMLUtils.getChildNodeValue(node, "AMOUNT");
    	String premium  = XMLUtils.getChildNodeValue(node, "PREMIUM");
    	String flag  = XMLUtils.getChildNodeValue(node, "FLAG");
    	String currency1  = XMLUtils.getChildNodeValue(node, "CURRENCY1");
    	String exchangeRate1  = XMLUtils.getChildNodeValue(node, "EXCHANGERATE1");
    	String amount1  = XMLUtils.getChildNodeValue(node, "AMOUNT1");
    	String premium1  = XMLUtils.getChildNodeValue(node, "PREMIUM1");
    	String currency2  = XMLUtils.getChildNodeValue(node, "CURRENCY2");
    	String exchangeRate2  = XMLUtils.getChildNodeValue(node, "EXCHANGERATE2");
    	String amount2  = XMLUtils.getChildNodeValue(node, "AMOUNT2");
    	String premium2  = XMLUtils.getChildNodeValue(node, "PREMIUM2");
    	
    	PrpTfeeSchema schema  = new PrpTfeeSchema();
    	schema.setProposalNo(proposalNo);
    	schema.setRiskCode(riskCode);
    	schema.setCurrency(currency);
    	schema.setAmount(amount);
    	schema.setPremium(premium);
    	schema.setFlag(flag);
    	schema.setCurrency1(currency1);
    	schema.setExchangeRate1("1.0000");
    	schema.setAmount1(amount1);
    	schema.setPremium1(premium1);
    	schema.setCurrency2(currency2);
    	schema.setExchangeRate2("1.0000");
    	schema.setAmount2(amount2);
    	schema.setPremium2(premium2);
    	
    	blProposal.getBLPrpTfee().setArr(schema);
    }
    /**
     * @desc  处理prptfeeinsured_list
     * @param blProposal
     * @param node
     * @throws Exception 
     */
    public void processPrpTinsuredList(BLProposal blProposal,Node node) 
    throws Exception{
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "INSURED_DATA");
    	for(int i=0 ; i <nodes.length ; i++ ){
    		processPrpTinsuredData(blProposal,nodes[i]);
    	}
    }
    public void processPrpTcarowerList(BLProposal blProposal,Node node) 
    	    throws Exception{
    	    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "INSURED_DATA");
    	    	for(int i=0 ; i <nodes.length ; i++ ){
    	    		processPrpTinsuredDataower(blProposal,nodes[i]);
    	    	}
    	    }
    /**
     * @desc  处理prptinsured_DATA
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTinsuredData(BLProposal blProposal,Node node) 
    throws Exception{
    	
    	String proposalNo  = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String riskCode  = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String serialNo  = XMLUtils.getChildNodeValue(node, "SERIALNO");
    	String language  = XMLUtils.getChildNodeValue(node, "LANGUAGE");
    	String insuredType  = XMLUtils.getChildNodeValue(node, "INSUREDTYPE");
    	String insuredCode  = XMLUtils.getChildNodeValue(node, "INSUREDCODE");
    	String insuredName  = XMLUtils.getChildNodeValue(node, "INSUREDNAME");
    	String insuredAddress  = XMLUtils.getChildNodeValue(node, "INSUREDADDRESS");
    	String insuredNature  = XMLUtils.getChildNodeValue(node, "INSUREDNATURE");
    	String insuredFlag  = XMLUtils.getChildNodeValue(node, "INSUREDFLAG");
    
    	String insuredIdentity   = XMLUtils.getChildNodeValue(node, "INSUREDIDENTITY");
    	String relateSerialNo  = XMLUtils.getChildNodeValue(node, "RELATESERIALNO");
    	String identifyType   = XMLUtils.getChildNodeValue(node, "IDENTIFYTYPE");
        if(identifyType==null||"".equals(identifyType))
            identifyType="99";
    	String identifyNumber  = XMLUtils.getChildNodeValue(node, "INDENTIFYNUM");
    	String creditLevel  = XMLUtils.getChildNodeValue(node, "CREDITLEVEL");
    	String possessNature  = XMLUtils.getChildNodeValue(node, "POSSESSNATURE");
    	String businessSource  = XMLUtils.getChildNodeValue(node, "BUSINESSSOURCE");
    	String businessSort  = XMLUtils.getChildNodeValue(node, "BUSINESSSORT");
    	String occupationCode  = XMLUtils.getChildNodeValue(node, "OCCUPATIONCODE");
    	String educationCode  = XMLUtils.getChildNodeValue(node, "EDUCATIONCODE");
    	String bank  = XMLUtils.getChildNodeValue(node, "BANK");
    	String accountName   = XMLUtils.getChildNodeValue(node, "ACCOUNTNAME");
    	String account  = XMLUtils.getChildNodeValue(node, "ACCOUNT");
    	String linkerName  = XMLUtils.getChildNodeValue(node, "LINKERNAME");
    	String postAddress  = XMLUtils.getChildNodeValue(node, "POSTADDRESS");
    	
    	String postCode  = XMLUtils.getChildNodeValue(node, "LINKPOSTCODE");
    	

    	String phoneNumber  = XMLUtils.getChildNodeValue(node, "PHONENUMBER");
    	String mobile  = XMLUtils.getChildNodeValue(node, "LINKMOBILE");
    	String email  = XMLUtils.getChildNodeValue(node, "EMAIL");
    	String benefitFlag  = XMLUtils.getChildNodeValue(node, "BENEFITFLAG");
    	String benefitRate  = XMLUtils.getChildNodeValue(node, "BENEFITRATE");
    	String flag  = XMLUtils.getChildNodeValue(node, "FALG");
    	String occupationGrade  = XMLUtils.getChildNodeValue(node, "OCCUPATIONGRADE");
    	String sex=XMLUtils.getChildNodeValue(node, "SEX");
        String age=XMLUtils.getChildNodeValue(node, "AGE");
        
        String Blood=XMLUtils.getChildNodeValue(node, "BLOOD");
        String DrivingCarType=XMLUtils.getChildNodeValue(node, "DRIVINGCARTYPE");
        String DrivingCarYears=XMLUtils.getChildNodeValue(node, "DRIVINGCARYEARS");
        String FastLinker=XMLUtils.getChildNodeValue(node, "FASTLINKER");
        String FastLinkerTel=XMLUtils.getChildNodeValue(node, "FASTLINKERTEL");
        String Education=XMLUtils.getChildNodeValue(node, "EDUCATION");
        String Birthday=XMLUtils.getChildNodeValue(node, "BIRTHDAY");
        String Marriage=XMLUtils.getChildNodeValue(node, "MARRIAGE");
        String Unit=XMLUtils.getChildNodeValue(node, "UNIT");
        String PartTimeOccupationCode=XMLUtils.getChildNodeValue(node, "PARTTIMEOCCUPATIONCODE");
        String UnitScale=XMLUtils.getChildNodeValue(node, "UNITSCALE");
        String OccupationCode=XMLUtils.getChildNodeValue(node, "OCCUPATIONCODE");
        
        String IdentifyUsefulLifeStart=XMLUtils.getChildNodeValue(node, "IDENTIFYUSEFULLIFESTART");
        String IdentifyUsefulLife=XMLUtils.getChildNodeValue(node, "IDENTIFYUSEFULLIFE");
        String LaunderRiskRank=XMLUtils.getChildNodeValue(node, "LAUNDERRISKRANK");
        String Nationality=XMLUtils.getChildNodeValue(node, "NATIONALITY");
        
        
    	PrpTinsuredSchema schema  = new  PrpTinsuredSchema();
        
    	
	    if("0".equals(insuredFlag)){}else{
	        if("3".equals(insuredNature) && "1".equals(insuredFlag)){
	        
	        	PrpTinsuredNatureSchema natureSchema = new PrpTinsuredNatureSchema();
	        	natureSchema.setProposalNo(proposalNo);
	            natureSchema.setAge(age);
	            natureSchema.setSex(sex);
	            
	            natureSchema.setOccupationCode(OccupationCode);
	            natureSchema.setInsuredFlag(insuredFlag);
	            natureSchema.setSerialNo(serialNo);
	            natureSchema.setBlood(Blood);
	            natureSchema.setDrivingCarType(DrivingCarType);
	            natureSchema.setDrivingCarYears(DrivingCarYears);
	            natureSchema.setFastLinker(FastLinker);
	            natureSchema.setFastLinkerTel(FastLinkerTel);
	            natureSchema.setEducation(Education);
	            if(Birthday!=null&&!Birthday.trim().equals(""))
	            natureSchema.setBirthday(Birthday);
	            natureSchema.setMarriage(Marriage);
	            natureSchema.setUnit(Unit);
	            natureSchema.setPartTimeOccupationCode(PartTimeOccupationCode);
	            blProposal.getBLPrpTinsuredNature().setArr(natureSchema);
	        }
	            
	        
	        schema.setUnitScale(UnitScale);
	        schema.setProposalNo(proposalNo);
	    	schema.setRiskCode(riskCode);
	    	schema.setSerialNo(serialNo);
	    	schema.setLanguage(language);
	    	schema.setInsuredType(insuredType);
	    	schema.setInsuredCode(insuredCode);
	    	schema.setInsuredName(insuredName);
	    	schema.setInsuredAddress(insuredAddress);
	    	schema.setInsuredNature(insuredNature);
	    	schema.setInsuredFlag(insuredFlag);
	    	schema.setInsuredIdentity(insuredIdentity);
	    	schema.setRelateSerialNo(relateSerialNo);
	    	schema.setIdentifyType(identifyType);
	    	schema.setIdentifyNumber(identifyNumber);
	    	schema.setCreditLevel(creditLevel);
	    	schema.setPossessNature(possessNature);
	    	schema.setBusinessSource(businessSource);
	    	schema.setBusinessSort(businessSort);
	    	schema.setOccupationCode(occupationCode);
	    	schema.setEducationCode(educationCode);
	    	schema.setBank(bank);
	    	schema.setAccountName(accountName);
	    	schema.setAccount(account);
	    	schema.setLinkerName(linkerName);
	    	schema.setPostAddress(postAddress);
	    	schema.setPostCode(postCode);
	    	schema.setPhoneNumber(phoneNumber);
	    	schema.setMobile(mobile);
	    	schema.setEmail(email);
	    	schema.setBenefitFlag(benefitFlag);
	    	schema.setBenefitRate(benefitRate);
	    	schema.setFlag(flag);
	    	schema.setOccupationGrade(occupationGrade);
	    	
	    	schema.setIdentifyUseFullife(IdentifyUsefulLife);
	    	schema.setIdentifyUseFullifeStart(IdentifyUsefulLifeStart);
	    	schema.setLaunderRiskRank(LaunderRiskRank);
	    	schema.setNationality(Nationality);
	    	
	    	
	    	blProposal.getBLPrpTinsured().setArr(schema);
	    	}
    }
    
    public void processPrpTinsuredDataower(BLProposal blProposal,Node node) 
    	    throws Exception{
    	    	
    	    	String proposalNo  = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	    	String riskCode  = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	    	String serialNo  = XMLUtils.getChildNodeValue(node, "SERIALNO");
    	    	String language  = XMLUtils.getChildNodeValue(node, "LANGUAGE");
    	    	String insuredType  = XMLUtils.getChildNodeValue(node, "INSUREDTYPE");
    	    	String insuredCode  = XMLUtils.getChildNodeValue(node, "INSUREDCODE");
    	    	String insuredName  = XMLUtils.getChildNodeValue(node, "INSUREDNAME");
    	    	String insuredAddress  = XMLUtils.getChildNodeValue(node, "INSUREDADDRESS");
    	    	String insuredNature  = XMLUtils.getChildNodeValue(node, "INSUREDNATURE");
    	    	String insuredFlag  = XMLUtils.getChildNodeValue(node, "INSUREDFLAG");
    	    	
    	    	String indentifyNum = XMLUtils.getChildNodeValue(node,"INDENTIFYNUM");
    	    	
    	    															
    	    	String insuredIdentity   = XMLUtils.getChildNodeValue(node, "INSUREDIDENTITY");
    	    	String relateSerialNo  = XMLUtils.getChildNodeValue(node, "RELATESERIALNO");
    	    	String identifyType   = XMLUtils.getChildNodeValue(node, "IDENTIFYTYPE");
    	        if(identifyType==null||"".equals(identifyType))
    	            identifyType="99";
    	    	String identifyNumber  = XMLUtils.getChildNodeValue(node, "IDENTIFYNUMBER");
    	    	String creditLevel  = XMLUtils.getChildNodeValue(node, "CREDITLEVEL");
    	    	String possessNature  = XMLUtils.getChildNodeValue(node, "POSSESSNATURE");
    	    	String businessSource  = XMLUtils.getChildNodeValue(node, "BUSINESSSOURCE");
    	    	String businessSort  = XMLUtils.getChildNodeValue(node, "BUSINESSSORT");
    	    	String occupationCode  = XMLUtils.getChildNodeValue(node, "OCCUPATIONCODE");
    	    	String educationCode  = XMLUtils.getChildNodeValue(node, "EDUCATIONCODE");
    	    	String bank  = XMLUtils.getChildNodeValue(node, "BANK");
    	    	String accountName   = XMLUtils.getChildNodeValue(node, "ACCOUNTNAME");
    	    	String account  = XMLUtils.getChildNodeValue(node, "ACCOUNT");
    	    	String linkerName  = XMLUtils.getChildNodeValue(node, "LINKERNAME");
    	    	String postAddress  = XMLUtils.getChildNodeValue(node, "POSTADDRESS");
    	    	
    	    	String postCode  = XMLUtils.getChildNodeValue(node, "LINKPOSTCODE");
    	    	

    	    	String phoneNumber  = XMLUtils.getChildNodeValue(node, "PHONENUMBER");
    	    	String mobile  = XMLUtils.getChildNodeValue(node, "MOBILE");
    	    	String email  = XMLUtils.getChildNodeValue(node, "EMAIL");
    	    	String benefitFlag  = XMLUtils.getChildNodeValue(node, "BENEFITFLAG");
    	    	String benefitRate  = XMLUtils.getChildNodeValue(node, "BENEFITRATE");
    	    	String flag  = XMLUtils.getChildNodeValue(node, "FALG");
    	    	String occupationGrade  = XMLUtils.getChildNodeValue(node, "OCCUPATIONGRADE");
    	    	String sex=XMLUtils.getChildNodeValue(node, "SEX");
    	        String age=XMLUtils.getChildNodeValue(node, "AGE");
    	        
    	        String Blood=XMLUtils.getChildNodeValue(node, "BLOOD");
    	        String DrivingCarType=XMLUtils.getChildNodeValue(node, "DRIVINGCARTYPE");
    	        String DrivingCarYears=XMLUtils.getChildNodeValue(node, "DRIVINGCARYEARS");
    	        String FastLinker=XMLUtils.getChildNodeValue(node, "FASTLINKER");
    	        String FastLinkerTel=XMLUtils.getChildNodeValue(node, "FASTLINKERTEL");
    	        String Education=XMLUtils.getChildNodeValue(node, "EDUCATION");
    	        String Birthday=XMLUtils.getChildNodeValue(node, "BIRTHDAY");
    	        String Marriage=XMLUtils.getChildNodeValue(node, "MARRIAGE");
    	        String Unit=XMLUtils.getChildNodeValue(node, "UNIT");
    	        String PartTimeOccupationCode=XMLUtils.getChildNodeValue(node, "PARTTIMEOCCUPATIONCODE");
    	        String UnitScale=XMLUtils.getChildNodeValue(node, "UNITSCALE");
    	        String OccupationCode=XMLUtils.getChildNodeValue(node, "OCCUPATIONCODE");
    	        
    	        String IdentifyUsefulLifeStart=XMLUtils.getChildNodeValue(node, "IDENTIFYUSEFULLIFESTART");
    	        String IdentifyUsefulLife=XMLUtils.getChildNodeValue(node, "IDENTIFYUSEFULLIFE");
    	        String LaunderRiskRank=XMLUtils.getChildNodeValue(node, "LAUNDERRISKRANK");
    	        String Nationality=XMLUtils.getChildNodeValue(node, "NATIONALITY");
    	        
    	        
    	    	PrpTinsuredSchema schema  = new  PrpTinsuredSchema();
    	        
    	    	
    	    	if("0".equals(insuredFlag)){
    	    		PrpTitemCarSchema  PrpTitemCarSchema=blProposal.getBLPrpTitemCar().getArr(0);
    	    		PrpTitemCarSchema.setCarOwner(insuredName);
    			    PrpTitemCarSchema.setCarInsuredRelation("1");
    	         	PrpTitemCarSchema.setCarOwnerNature(insuredNature);
    	         	
    	    	    PrpTitemCarSchema.setInsuredTypeCode(identifyType);
    	         	PrpTitemCarSchema.setOwnerAddress(indentifyNum);
    	         	
    	    	}else{}
    	    }
    /**
     * @desc 处理 prptitemcar_list
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTitemCarList(BLProposal blProposal,Node node)
    throws Exception{
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "CAR_DATA");
    	for(int i =0 ; i < nodes.length ; i++ ){
    		processPrpTitemCarData(blProposal,nodes[i]);
    		
    		processCarExtData(blProposal,nodes[i]);
    		
    	}
    }
    /**
     * @desc   处理 prptitemcar_data 
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTitemCarData(BLProposal blProposal,Node node) 
    throws Exception{
    	
    	String proposalNo  = XMLUtils.getChildNodeValue(node, "PROPODSLNO");
    	String riskCode  = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String itemNo  = XMLUtils.getChildNodeValue(node, "ITEMNO");
    	String insuredTypeCode  = XMLUtils.getChildNodeValue(node, "INSUREDTYPECODE");
    	String carInsuredRelation  = XMLUtils.getChildNodeValue(node, "CARINSUREDRELATION");
    	String carOwner  = XMLUtils.getChildNodeValue(node, "CAROWNER");
    	String clauseType  = XMLUtils.getChildNodeValue(node, "CLAUSETYPE");
    	String agreeDriverFlag  = XMLUtils.getChildNodeValue(node, "AGREEDRIVERFLAG");
        if(agreeDriverFlag==null||"".equals(agreeDriverFlag))
            agreeDriverFlag  = XMLUtils.getChildNodeValue(node, "AgreeDriverFlag");
    	String newDeviceFlag  = XMLUtils.getChildNodeValue(node, "NEWDEVICEFLAG");
    	String carPolicyno  = XMLUtils.getChildNodeValue(node, "CARPOLICYNO");
    	String licenseNo  = XMLUtils.getChildNodeValue(node, "LICENSENO");
    	String licenseColorCode  = XMLUtils.getChildNodeValue(node, "LICENSECOLORCODE");
    	String carKindCode  = XMLUtils.getChildNodeValue(node, "CARKINDCODE");
    	String HKFlag  = XMLUtils.getChildNodeValue(node, "HKFLAG");
    	String HKLicenseNo  = XMLUtils.getChildNodeValue(node, "HKLICENSENO");
    	String engineNo  = XMLUtils.getChildNodeValue(node, "ENGINENO");
    	String VINNo  = XMLUtils.getChildNodeValue(node, "VINNO");
    	String frameNo  = XMLUtils.getChildNodeValue(node, "FRAMENO");
    	String runAreaCode  = XMLUtils.getChildNodeValue(node, "RUNAREACODE");
    	String runAreaName  = XMLUtils.getChildNodeValue(node, "RUNAREANAME");
    	String runMiles  = XMLUtils.getChildNodeValue(node, "RUNMILES");
    	String enrollDate  = XMLUtils.getChildNodeValue(node, "ENROLLDATE");
    	String useYears  = XMLUtils.getChildNodeValue(node, "USEYEARS");
    	String modelCode  = XMLUtils.getChildNodeValue(node, "MODELCODE");
    	String brandName  = XMLUtils.getChildNodeValue(node, "BRANDNAME");
    	String countryNature  = XMLUtils.getChildNodeValue(node, "COUNTRYNATURE");
    	String countryCode  = XMLUtils.getChildNodeValue(node, "COUNTRYCODE");
    	String useNatureCode  = XMLUtils.getChildNodeValue(node, "USENATURECODE");
    	String businessClassCode  = XMLUtils.getChildNodeValue(node, "BUSINESSCLASSCODE");
    	String seatCount  = XMLUtils.getChildNodeValue(node, "SEATCOUNT");
    	String tonCount  = XMLUtils.getChildNodeValue(node, "TONCOUNT");
    	String exhaustScale  = XMLUtils.getChildNodeValue(node, "EXHAUSTSCALE");
    	String colorCode  = XMLUtils.getChildNodeValue(node, "COLORCODE");
    	String safeDevice  = XMLUtils.getChildNodeValue(node, "SAFEDEVICE");
    	String parkSite  = XMLUtils.getChildNodeValue(node, "PARKSITE");
    	String ownerAddress  = XMLUtils.getChildNodeValue(node, "OWNERADDRESS");
    	String otherNature  = XMLUtils.getChildNodeValue(node, "OTHERNATURE");
    	String rateCode  = XMLUtils.getChildNodeValue(node, "RATECODE");
    	String makeDate  = XMLUtils.getChildNodeValue(node, "MAKEDATE");
    	String makeDateNew= XMLUtils.getChildNodeValue(node, "MAKEDATENEW");
    	String currency  = XMLUtils.getChildNodeValue(node, "CURRENCY");
    	String purchasePrice  = XMLUtils.getChildNodeValue(node, "PURCHASEPRICE");
    	String actualValue  = XMLUtils.getChildNodeValue(node, "ACTUALVALUE");
    	String invoiceNo  = XMLUtils.getChildNodeValue(node, "INVOICENO");
    	String carLoanFlag  = XMLUtils.getChildNodeValue(node, "CARLOANFLAG");
    	String insurerCode  = XMLUtils.getChildNodeValue(node, "INSURERCODE");
    	String lastInsurer  = XMLUtils.getChildNodeValue(node, "LASTINSURER");
    	String carCheckStatus  = XMLUtils.getChildNodeValue(node, "CARCHECKSTATUS");
    	String carChecker  = XMLUtils.getChildNodeValue(node, "CARCHECKER");
    	String carCheckTime  = XMLUtils.getChildNodeValue(node, "CARCHECKTIME");
    	String specialTreat  = XMLUtils.getChildNodeValue(node, "SPECIALTREAT");
    	String relievingAreaCode  = XMLUtils.getChildNodeValue(node, "RELIEVINGAREACODE");
    	String addonCount  = XMLUtils.getChildNodeValue(node, "ADDONCOUNT");
    	String carDealerCode  = XMLUtils.getChildNodeValue(node, "CARDEALERCODE");
    	String carDealerName  = XMLUtils.getChildNodeValue(node, "CARDEALERNAME");
    	String remark  = XMLUtils.getChildNodeValue(node, "REMARK");
    	String flag  = XMLUtils.getChildNodeValue(node, "FLAG");
    	String carCheckReason  = XMLUtils.getChildNodeValue(node, "CARCHECKREASON");
    	String lViolatedtimes  = XMLUtils.getChildNodeValue(node, "LVIOLATEDTIMES");
    	String sViolatedtimes  = XMLUtils.getChildNodeValue(node, "SVIOLATEDTIMES");
    	String licenseKindCode  = XMLUtils.getChildNodeValue(node, "LICENSETYPE");
        String mainCarKindCode  = XMLUtils.getChildNodeValue(node, "MAINCARKINDCODE");
        String printBrandName = XMLUtils.getChildNodeValue(node, "PRINTBRANDNAME");
        String RestricFlagL = XMLUtils.getChildNodeValue(node, "RESTRICFLAGL");
        String RestricFlag = XMLUtils.getChildNodeValue(node, "RESTRICFLAG");
        
        String PurchasePriceOrigin =XMLUtils.getChildNodeValue(node, "PURCHASEPRICEORIGIN");
        String PurchasePriceNoTaxFlag  =XMLUtils.getChildNodeValue(node, "PURCHASEPRICENOTAXFLAG");
        
        
        String newCarFlag = XMLUtils.getChildNodeValue(node, "NEWCARFLAG");
        String outLandCarFlag  =XMLUtils.getChildNodeValue(node, "OUTLANDCARFLAG");
        String chgOwnerFlag  =XMLUtils.getChildNodeValue(node, "CHGOWNERFLAG");
        String noLicenseFlag  =XMLUtils.getChildNodeValue(node, "NOLICENSEFLAG");
        String loanVehicleFlag  =XMLUtils.getChildNodeValue(node, "LOANVEHICLEFLAG");
        String chgOwnerDate  =XMLUtils.getChildNodeValue(node, "CHGOWNERDATE");
        String LicenseCategory  =XMLUtils.getChildNodeValue(node, "LICENSECATEGORY");
        String SearchseQuenceNo  =XMLUtils.getChildNodeValue(node, "SEARCHSEQUENCENO");
        String CompleteKerbMass  =XMLUtils.getChildNodeValue(node, "COMPLETEKERBMASS");
        String strMadeFactory=XMLUtils.getChildNodeValue(node, "MADEFACTORY");
        
        
        String carOwnerNature=XMLUtils.getChildNodeValue(node, "CAROWNERNATURE");
        
        
        String purchasePriceLB=XMLUtils.getChildNodeValue(node, "PURCHASEPRICELB");
        
        
        String seatCountLB = XMLUtils.getChildNodeValue(node, "SEATCOUNTLB");
        String tonCountLB = XMLUtils.getChildNodeValue(node, "TONCOUNTLB");
        String exhaustScaleLB = XMLUtils.getChildNodeValue(node, "EXHAUSTSCALELB");
        
    	
    	PrpTitemCarSchema schema = new PrpTitemCarSchema();
    	schema.setProposalNo(proposalNo);
    	schema.setRiskCode(riskCode);
    	schema.setItemNo(itemNo);
    	schema.setInsuredTypeCode(insuredTypeCode);
    	schema.setCarInsuredRelation(carInsuredRelation);
    	schema.setCarOwner(carOwner);
    	schema.setClauseType(clauseType);
        
        
    	schema.setAgreeDriverFlag(agreeDriverFlag);
    	schema.setNewDeviceFlag(newDeviceFlag);
    	schema.setCarPolicyno(carPolicyno);
    	schema.setLicenseNo(licenseNo);
    	schema.setLicenseColorCode(licenseColorCode);
    	schema.setCarKindCode(carKindCode);
    	schema.setHKFlag(HKFlag);
    	schema.setHKLicenseNo(HKLicenseNo);
    	schema.setEngineNo(engineNo);
    	schema.setVINNo(VINNo);
    	schema.setFrameNo(frameNo);
    	schema.setRunAreaCode(runAreaCode);
    	schema.setRunAreaName(runAreaName);
    	schema.setRunMiles(runMiles);
    	schema.setEnrollDate(enrollDate);
    	schema.setUseYears(useYears);
    	schema.setModelCode(modelCode);
    	schema.setBrandName(brandName);
    	schema.setCountryNature(countryNature);
    	schema.setCountryCode(countryCode);
    	schema.setUseNatureCode(useNatureCode);
    	schema.setBusinessClassCode(businessClassCode);
    	schema.setSeatCount(seatCount);
    	schema.setTonCount(tonCount);
    	schema.setExhaustScale(exhaustScale);
    	schema.setColorCode(colorCode);
    	schema.setSafeDevice(safeDevice);
    	schema.setParkSite(parkSite);
    	schema.setOwnerAddress(ownerAddress);
        if(riskCode.equals("0507")){
        schema.setOtherNature(" "+otherNature);
        }
        else{
            schema.setOtherNature(otherNature);
        }
    	schema.setRateCode(rateCode);
        if(!"".equals(makeDate.trim())){
    	   schema.setMakeDate(makeDate);
        }
        
        UtiPower utiPower = new UtiPower();







        
    	schema.setCarUsage(printBrandName);
        currency="CNY";
    	schema.setCurrency(currency);
    	schema.setPurchasePrice(purchasePrice);
    	schema.setActualValue(actualValue);
    	schema.setInvoiceNo(invoiceNo);
    	schema.setCarLoanFlag(carLoanFlag);
    	schema.setInsurerCode(insurerCode);
    	schema.setLastInsurer(lastInsurer);
        
    	schema.setCarCheckStatus(carCheckStatus);
    	schema.setCarChecker(carChecker);
    	schema.setCarCheckTime(carCheckTime);
    	schema.setSpecialTreat(specialTreat);
    	schema.setRelievingAreaCode(relievingAreaCode);
    	schema.setAddonCount(addonCount);
    	schema.setCarDealerCode(carDealerCode);
    	schema.setCarDealerName(carDealerName);
    	schema.setRemark(remark);
    	schema.setFlag(flag);
    	schema.setCarCheckReason(carCheckReason);
        lViolatedtimes="0";
    	schema.setLViolatedTimes(lViolatedtimes);
        sViolatedtimes="0";
    	schema.setSViolatedTimes(sViolatedtimes);
    	schema.setLicenseKindCode(licenseKindCode);
    	
        schema.setMainCarKindCode(mainCarKindCode);
        
        schema.setPurchasePriceLB(purchasePriceLB);
        
        
        
        if(RestricFlagL!=null&&!RestricFlagL.trim().equals(""))
            schema.setRestricFlag(RestricFlagL);
        else
            schema.setRestricFlag(RestricFlag);
        
        schema.setPurchasePriceOrigin(PurchasePriceOrigin);
        schema.setPurchasePriceNoTaxFlag(PurchasePriceNoTaxFlag);
        
        
        schema.setNewCarFlag(newCarFlag);
        schema.setOutLandCarFlag(outLandCarFlag);
        schema.setChgOwnerFlag(chgOwnerFlag);
        schema.setNoLicenseFlag(noLicenseFlag);
        schema.setLoanVehicleFlag(loanVehicleFlag);
        schema.setChgOwnerDate(chgOwnerDate);
        schema.setLicenseCategory(LicenseCategory);
        schema.setSearchSequenceNo(SearchseQuenceNo);
        if (CompleteKerbMass!=null&&!"".equals(CompleteKerbMass)) {
        	int iCompleteKerbMass = (int)Double.parseDouble(CompleteKerbMass);
			schema.setCompleteKerbMass(String.valueOf(iCompleteKerbMass));
		}
		schema.setMadeFactory(strMadeFactory);
        
        
        schema.setCarOwnerNature(carOwnerNature);
        
        
        schema.setSeatCountLB(seatCountLB);
        schema.setTonCountLB(tonCountLB);
        schema.setExhaustScaleLB(exhaustScaleLB);
        if(blProposal.getBLPrpTmain().getArr(0).getRiskCode().equals("0507")){
        	schema.setMakeDate(blProposal.getBLPrpTmain().getArr(0).getOperateDate());
        }
        
     	blProposal.getBLPrpTitemCar().setArr(schema);
     	
     	if(!blProposal.getBLPrpTmain().getArr(0).getComCode().equals("")&&!"".equals(blProposal.getBLPrpTmain().getArr(0).getStartDate())){
     	
        if(utiPower.checkBIInsure(blProposal.getBLPrpTmain().getArr(0).getRiskCode(),blProposal.getBLPrpTmain().getArr(0).getComCode())){
        	newCarFlag  = PubTools.getNewCarFlag(blProposal);
        	blProposal.getBLPrpTitemCar().getArr(0).setNewCarFlag(newCarFlag);
        }
     	}
     	

        
    }
    /**
     * desc   处理prpTitemCarExt_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void  processPrpTitemCarExtList(BLProposal blProposal,Node node) 
    throws Exception{
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "PRPTITEMCAREXT_DATA");
    	for(int  i = 0 ; i < nodes.length ; i ++ ){
    		processPrpTitemCarExtData(blProposal,nodes[i]);
    	}
    }
    /**
     * @desc  处理prpTitemCarExt_DATA
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTitemCarExtData(BLProposal blProposal,Node node) 
    throws Exception{
    	
       
    	String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String RiskCode = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String ItemNo = XMLUtils.getChildNodeValue(node, "ITEMNO");
    	String LastDamagedA = XMLUtils.getChildNodeValue(node, "LASTDAMAGEDA");
    	String LastDamagedB = XMLUtils.getChildNodeValue(node, "LASTDAMAGEDB");
    	String ThisDamagedA = XMLUtils.getChildNodeValue(node, "THISDAMAGEDA");
    	String ThisDamagedB = XMLUtils.getChildNodeValue(node, "THISDAMAGEDB");
    	String CarGoodsType = XMLUtils.getChildNodeValue(node, "CARGOODSTYPE");
    	String NoClaimFavorType = XMLUtils.getChildNodeValue(node, "NOCLAIMFAVORTYPE");
    	String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
    	String LastDamagedTimes = XMLUtils.getChildNodeValue(node, "LASTDAMAGEDTIMES");
    	String ThisDamagedTimes = XMLUtils.getChildNodeValue(node, "THISDAMAGEDTIMES");
    	String DamagedFactorGrade = XMLUtils.getChildNodeValue(node, "DAMAGEDFACTORGRADE");
    	String noneFluctuateFlag = XMLUtils.getChildNodeValue(node, "NONEFLUCTUATEFLAG");
    	String lastOffenceCI = XMLUtils.getChildNodeValue(node, "LASTOFFENCECI");
    	String thisOffenceCI = XMLUtils.getChildNodeValue(node, "THISOFFENCECI");
    	String noOffYearsCI = XMLUtils.getChildNodeValue(node, "NOOFFYEARSCI");
    	String damFloatRatioCI = XMLUtils.getChildNodeValue(node, "DAMFLOATRATIOCI");
    	String offFloatRatioCI = XMLUtils.getChildNodeValue(node, "OFFFLOATRATIOCI");
        
        String noDamageYears=XMLUtils.getChildNodeValue(node, "NODAMAGEYEARS");
        
        
        String inDoorFlag=XMLUtils.getChildNodeValue(node, "INDOORFLAG");
        String claimAmountValue=XMLUtils.getChildNodeValue(node, "CLAIMAMOUNTVALUE");
        
        
        String controlFlag=XMLUtils.getChildNodeValue(node, "CONTROLFLAG");
        
        
        String fuelType=XMLUtils.getChildNodeValue(node, "FUELTYPE");
        
        
        String buyCarDate=XMLUtils.getChildNodeValue(node, "BUYCARDATE");
        
        
        String brandECode=XMLUtils.getChildNodeValue(node, "BRANDECODE");
        
        
        String licenceDate=XMLUtils.getChildNodeValue(node, "LICENCEDATE");
        
        
        String shieldFirewallFlag=XMLUtils.getChildNodeValue(node, "SHIELDFIREWALLFLAG");
        
        String fundAmount=XMLUtils.getChildNodeValue(node, "FUNDAMOUNT"); 
        String fundRate=XMLUtils.getChildNodeValue(node, "FUNDRATE");
        
        String PayRateCarKind=XMLUtils.getChildNodeValue(node, "PAYRATECARKIND");
        
    	PrpTitemCarExtSchema schema = new PrpTitemCarExtSchema();
    	schema.setProposalNo(ProposalNo);
    	schema.setRiskCode(RiskCode);
    	schema.setItemNo(ItemNo);
    	schema.setLastDamagedA(LastDamagedA);
    	schema.setLastDamagedB(LastDamagedB);
    	schema.setThisDamagedA(ThisDamagedA);
    	schema.setThisDamagedB(ThisDamagedB);
    	schema.setCarGoodsType(CarGoodsType);
    	schema.setNoClaimFavorType(NoClaimFavorType);
    	schema.setFlag(Flag);
    	schema.setLastDamagedTimes(LastDamagedTimes);
    	schema.setThisDamagedTimes(ThisDamagedTimes);
    	schema.setDamagedFactorGrade(DamagedFactorGrade);
    	schema.setNoneFluctuateFlag(noneFluctuateFlag);
    	schema.setLastOffenceCI(lastOffenceCI);
    	schema.setThisOffenceCI(thisOffenceCI);
    	schema.setNoOffYearsCI(noOffYearsCI);
    	schema.setDamFloatRatioCI(damFloatRatioCI);
    	schema.setOffFloatRatioCI(offFloatRatioCI);
        
    	schema.setNoDamageYears(noDamageYears);
        
    	
    	schema.setInDoorFlag(inDoorFlag);
    	schema.setClaimAmountValue(claimAmountValue);
    	
        
    	schema.setControlFlag(controlFlag);
        
    	schema.setFuelType(fuelType);
    	
    	schema.setBuyCarDate(buyCarDate);
    	
    	
    	schema.setBrandECode(brandECode);
    	
    	
    	schema.setLicenceDate(licenceDate);
    	
    	
    	
    	schema.setShieldFirewall(shieldFirewallFlag);  
    	
    	
    	schema.setFundAmount(fundAmount);
    	schema.setFundRate(fundRate);
    	
    	
    	schema.setPayRateCarKind(PayRateCarKind);
    	
    	blProposal.getBLPrpTitemCarExt().setArr(schema);
    }
    /**
     * @desc  处理PrpTinsuredNature_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processsPrpTinsuredNatureList(BLProposal blProposal,Node node)
    throws Exception{
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "PRPTINSREDNATURE_DATA");
    	for(int i = 0; i < nodes.length ; i++ ){
    		processsPrpTinsuredNatureData( blProposal, nodes[i]);
    	}
    }
    /**
     * @desc  处理PrpTinsuredNature_DATA
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processsPrpTinsuredNatureData(BLProposal blProposal,Node node)
    throws Exception{
    	String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String SerialNo = XMLUtils.getChildNodeValue(node, "SERIALNO");
    	String InsuredFlag = XMLUtils.getChildNodeValue(node, "INSUREDFLAG");
    	String Sex = XMLUtils.getChildNodeValue(node, "SEX");
    	String Age = XMLUtils.getChildNodeValue(node, "AGE");
    	String Birthday  = XMLUtils.getChildNodeValue(node, "BIRTHDAY");
    	String Health = XMLUtils.getChildNodeValue(node, "HEALTH");
    	String JobTitle = XMLUtils.getChildNodeValue(node, "JOBTITLE");
    	String LoacalWorkYears = XMLUtils.getChildNodeValue(node, "LOACALWORKYEARS");
    	String Education = XMLUtils.getChildNodeValue(node, "EDUCATION");
    	String TotalWorkYears = XMLUtils.getChildNodeValue(node, "TOTALWORKYEARS");
    	String Unit = XMLUtils.getChildNodeValue(node, "UNIT");
    	String UnitPhoneNumber = XMLUtils.getChildNodeValue(node, "UNITPHONENUMBER");
    	String UnitAddress = XMLUtils.getChildNodeValue(node, "UNITADDRESS");
    	String UnitPostCode = XMLUtils.getChildNodeValue(node, "UNITPOSTCODE");
    	String UnitType = XMLUtils.getChildNodeValue(node, "UNITTYPE");
    	String DutyLevel = XMLUtils.getChildNodeValue(node, "DUTYLEVEL");
    	String DutyType = XMLUtils.getChildNodeValue(node, "DUTYTYPE");
    	String OccupationCode = XMLUtils.getChildNodeValue(node, "OCCUPATIONCODE");
    	String HouseProperty = XMLUtils.getChildNodeValue(node, "HOUSEPROPERTY");
    	String LocalPoliceStation = XMLUtils.getChildNodeValue(node, "LOCALPOLICESTATION");
    	String RoomAddress = XMLUtils.getChildNodeValue(node, "ROOMADDRESS");
    	String RoomPostCode = XMLUtils.getChildNodeValue(node, "ROOMPOSTCODE");
    	String SelfMonthIncome = XMLUtils.getChildNodeValue(node, "SELFMONTHINCOME");
    	String FamilyMonthIncome = XMLUtils.getChildNodeValue(node, "FAMILTMONTHINCOME");
    	String IncomeSource = XMLUtils.getChildNodeValue(node, "INCOMESOURCE");
    	String RoomPhone = XMLUtils.getChildNodeValue(node, "ROOMPHONE");
    	String Mobile = XMLUtils.getChildNodeValue(node, "MOBILE");
    	String FamilySumQuantity = XMLUtils.getChildNodeValue(node, "FAMILYSUMQUANTITY");
    	String Marriage = XMLUtils.getChildNodeValue(node, "MARRIAGE");
    	String SpouseName = XMLUtils.getChildNodeValue(node, "SPOUSENAME");
    	String SpouseBornDate = XMLUtils.getChildNodeValue(node, "SPOUSEBORNDATE");
    	String SpouseID = XMLUtils.getChildNodeValue(node, "SPOUSEID");
    	String SpouseUnit = XMLUtils.getChildNodeValue(node, "SPOUSEUNIT");
    	String SpouseJobTitle = XMLUtils.getChildNodeValue(node, "SPOUSEJOBTUTLE");
    	String SpouseUnitPhone = XMLUtils.getChildNodeValue(node, "SPOUSEUNITPHONE");
    	String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
    	String Weight = XMLUtils.getChildNodeValue(node, "WEIGHT");
    	String Stature = XMLUtils.getChildNodeValue(node, "STATURE");
    	
    	PrpTinsuredNatureSchema schema = new PrpTinsuredNatureSchema();
    	schema.setProposalNo(ProposalNo);
    	schema.setSerialNo(SerialNo);
    	schema.setInsuredFlag(InsuredFlag);
    	schema.setSex(Sex);
    	schema.setAge(Age);
    	schema.setBirthday(Birthday);
    	schema.setHealth(Health);
    	schema.setJobTitle(JobTitle);
    	schema.setLoacalWorkYears(LoacalWorkYears);
    	schema.setEducation(Education);
    	schema.setTotalWorkYears(TotalWorkYears);
    	schema.setUnit(Unit);
    	schema.setUnitPhoneNumber(UnitPhoneNumber);
    	schema.setUnitAddress(UnitAddress);
    	schema.setUnitPostCode(UnitPostCode);
    	schema.setUnitType(UnitType);
    	schema.setDutyLevel(DutyLevel);
    	schema.setDutyType(DutyType);
    	schema.setOccupationCode(OccupationCode);
    	schema.setHouseProperty(HouseProperty);
    	schema.setLocalPoliceStation(LocalPoliceStation);
    	schema.setRoomAddress(RoomAddress);
    	schema.setRoomPostCode(RoomPostCode);
    	schema.setSelfMonthIncome(SelfMonthIncome);
    	schema.setFamilyMonthIncome(FamilyMonthIncome);
    	schema.setIncomeSource(IncomeSource);
    	schema.setRoomPhone(RoomPhone);
    	schema.setMobile(Mobile);
    	schema.setFamilySumQuantity(FamilySumQuantity);
    	schema.setMarriage(Marriage);
    	schema.setSpouseBornDate(SpouseBornDate);
    	schema.setSpouseName(SpouseName);
    	schema.setSpouseID(SpouseID);
    	schema.setSpouseUnit(SpouseUnit);
    	schema.setSpouseJobTitle(SpouseJobTitle);
    	schema.setSpouseUnitPhone(SpouseUnitPhone);
    	schema.setFlag(Flag);
    	schema.setWeight(Weight);
    	schema.setStature(Stature);
    	
    	blProposal.getBLPrpTinsuredNature().setArr(schema);
    }
    /**
     * @desc 处理PrpTitemKind_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTitemKindList(BLProposal blProposal,Node node) throws Exception{
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "KIND_DATA");
    	for(int  i=0 ; i < nodes.length ; i++ ){
    		processPrpTitemKindData(blProposal,nodes[i]);
    	}
    }
    /**
     * @desc 处理PrpTitemKind_DATA
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTitemKindData(BLProposal blProposal,Node node) throws Exception{
    	
        
    	String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String RiskCode = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String ItemKindNo  = XMLUtils.getChildNodeValue(node, "ITEMKINDNO");
    	String FamilyNo = XMLUtils.getChildNodeValue(node, "FAMILYNO");
    	String FamilyName = XMLUtils.getChildNodeValue(node, "FAMILYNAME");
    	String RationType = XMLUtils.getChildNodeValue(node, "RATIONCODE");
    	String KindCode  = XMLUtils.getChildNodeValue(node, "KINDCODE");
    	String KindName = XMLUtils.getChildNodeValue(node, "KINDNAME");
        BLPrpDcode blPrpDcode = new BLPrpDcode();
        if(KindCode!=null)
        KindCode=KindCode.trim();
        KindName=blPrpDcode.translateCode("KindCode",KindCode,true);
        
    	String ItemNo = XMLUtils.getChildNodeValue(node, "ITEMNO");
    	String ItemCode = XMLUtils.getChildNodeValue(node, "ITEMCODE");
    	String ItemDetailName = XMLUtils.getChildNodeValue(node, "ITEMDETAILNAME");
    	String ModeCode = XMLUtils.getChildNodeValue(node, "MODECODE");
        
    	String ModeName = XMLUtils.getChildNodeValue(node, "MODENAME");
    	String StartDate = XMLUtils.getChildNodeValue(node, "STARTDATE");
    	
    	String StartHour = XMLUtils.getChildNodeValue(node, "STARTHOUR");
    	
    	String EndDate = XMLUtils.getChildNodeValue(node, "ENDDATE");
    	String EndHour = XMLUtils.getChildNodeValue(node, "ENDHOUR");
    	String Model = XMLUtils.getChildNodeValue(node, "MODEL");
    	String BuyDate = XMLUtils.getChildNodeValue(node, "BUYDATE");
    	String AddressNo  = XMLUtils.getChildNodeValue(node, "ADDRESSNO");
    	String CalculateFlag = XMLUtils.getChildNodeValue(node, "CALCULATEFLAG");
    	String Currency = XMLUtils.getChildNodeValue(node, "CURRENCY");
    	String UnitAmount = XMLUtils.getChildNodeValue(node, "UNITAMOUNT");
    	String Quantity = XMLUtils.getChildNodeValue(node, "QUANTITY");
    	String Unit = XMLUtils.getChildNodeValue(node, "UNIT");
    	String Value = XMLUtils.getChildNodeValue(node, "VALUE");
    	String Amount = XMLUtils.getChildNodeValue(node, "AMOUNT");
    	String RatePeriod = XMLUtils.getChildNodeValue(node, "RATEPERIOD");
    	String Rate = XMLUtils.getChildNodeValue(node, "RATE");
    	String ShortRateFlag = XMLUtils.getChildNodeValue(node, "SHORTRATEFLAG");
    	String ShortRate = XMLUtils.getChildNodeValue(node, "SHORTRATE");
        
    	String BasePremium = XMLUtils.getChildNodeValue(node, "BASEPREMIUM");
    	String BenchMarkPremium = XMLUtils.getChildNodeValue(node, "BENCHMARKPREMIUM");
    	if(BenchMarkPremium==null||BenchMarkPremium.equals(""))
            BenchMarkPremium="0.00";
        String Discount = XMLUtils.getChildNodeValue(node, "DISCOUNT");
        
    	String AdjustRate = XMLUtils.getChildNodeValue(node, "ADJUSTRATE");
    	String Premium = XMLUtils.getChildNodeValue(node, "PREMIUM");
    	String DeductibleRate = XMLUtils.getChildNodeValue(node, "DEDUCTIBLERATE");
    	String Deductible = XMLUtils.getChildNodeValue(node, "DEDUCTIBLE");
    	String Flag = XMLUtils.getChildNodeValue(node, "KINDFLAG");
    	
    	String RiskPremium = XMLUtils.getChildNodeValue(node, "RISKPREMIUM");
    	
    	
    	PrpTitemKindSchema schema = new PrpTitemKindSchema();
    	schema.setProposalNo(ProposalNo);
    	schema.setRiskCode(RiskCode);
    	schema.setItemKindNo(ItemKindNo);
    	schema.setFamilyNo(FamilyNo);
    	schema.setFamilyName(FamilyName);
    	schema.setRationType(RationType);
    	schema.setKindCode(KindCode);
    	schema.setKindName(KindName);
    	schema.setItemNo(ItemNo);
        ItemCode="0001";
    	schema.setItemCode(ItemCode);
    	schema.setItemDetailName(ItemDetailName);
    	schema.setModeCode(ModeCode);
    	schema.setModeName(ModeName);
    	schema.setStartDate(StartDate);
    	schema.setStartHour(StartHour);
    	schema.setEndDate(EndDate);
    	schema.setEndHour("24");
    	schema.setModel(Model);
        if(BuyDate!=null&&!BuyDate.trim().equals(""))
    	schema.setBuyDate(BuyDate);
    	schema.setAddressNo(AddressNo);
    	schema.setCalculateFlag(CalculateFlag);
    	schema.setCurrency(Currency);
    	schema.setUnitAmount(UnitAmount);
    	schema.setQuantity(Quantity);
    	schema.setUnit(Unit);
    	schema.setValue(Value);
    	schema.setAmount(Amount);
    	schema.setRatePeriod(RatePeriod);
    	schema.setRate(Rate);
    	schema.setShortRateFlag(ShortRateFlag);
    	schema.setShortRate(ShortRate);
    	schema.setBasePremium(BasePremium);
    	schema.setBenchMarkPremium(BenchMarkPremium);
    	schema.setDiscount(Discount);
    	schema.setAdjustRate(AdjustRate);
    	schema.setPremium(Premium);
    	schema.setDeductibleRate(DeductibleRate);
    	schema.setDeductible(Deductible);
        
        if(Flag.length()<6){
            Flag=" "+Flag;
        }
      
    	schema.setFlag(Flag);
        
    	
        schema.setItemDetailName("车辆");
        
        schema.setRiskPremium(RiskPremium);
        
        
        Double finalCompensateRate = 0.0;
        if(Double.parseDouble(ChgData.chgStrZero(RiskPremium))!=0&&Double.parseDouble(ChgData.chgStrZero(Premium))!=0)
        {
            finalCompensateRate= Double.parseDouble(ChgData.chgStrZero(RiskPremium))/Double.parseDouble(ChgData.chgStrZero(Premium)) * 100;
        }
        schema.setFinalCompensateRate(new DecimalFormat("0.00").format(finalCompensateRate));
        
    	blProposal.getBLPrpTitemKind().setArr(schema);
    }
    /**
     * @desc   处理 PrpTlimit_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTlimitList(BLProposal blProposal,Node node) 
    throws Exception{
    	Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "PRPTLIMIT_DATA");
    	for(int i=0 ; i < nodes.length ; i++){
    		processPrpTlimitData(blProposal,nodes[i]);
    	}
    }
    /**
     * @desc 处理 PrpTlimit_DATA
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTlimitData(BLProposal blProposal,Node node)
    throws Exception{
    	String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String RiskCode = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String LimitGrade = XMLUtils.getChildNodeValue(node, "LIMITGRADE");
    	String LimitNo  = XMLUtils.getChildNodeValue(node, "LIMITNO");
    	String LimitType = XMLUtils.getChildNodeValue(node, "LIMITTYPE");
    	String Currency = XMLUtils.getChildNodeValue(node, "CURRENCY");
    	String LimitFee = XMLUtils.getChildNodeValue(node, "LIMITFEE");
    	String CalculateFlag = XMLUtils.getChildNodeValue(node, "CALCULATEFLAG");
    	String LimitFlag = XMLUtils.getChildNodeValue(node, "LIMITFLAG");
    	String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
    	PrpTlimitSchema schema= new PrpTlimitSchema();
    	schema.setProposalNo(ProposalNo);
    	schema.setRiskCode(RiskCode);
    	schema.setLimitGrade(LimitGrade);
    	schema.setLimitNo(LimitNo);
    	schema.setLimitType(LimitType);
    	schema.setCurrency(Currency);
    	schema.setLimitFee(LimitFee);
    	schema.setCalculateFlag(CalculateFlag);
    	schema.setLimitFlag(LimitFlag);
    	schema.setFlag(Flag);
    	blProposal.getBLPrpTlimit().setArr(schema);
    }
    /**
     * @desc  处理PrpTprofit_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTprofitList(BLProposal blProposal,Node node)
    throws Exception{
    	Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "PRPTPROFIT_DATA");
        for(int i=0 ; i < nodes.length ; i++){
    		processPrpTprofitData(blProposal,nodes[i]);
    	}
    }
    /**
     * @desc  处理PrpTprofit_DATA
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTprofitData(BLProposal blProposal,Node node)
    throws Exception{
    	
    	String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String RiskCode = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String ProfitType = XMLUtils.getChildNodeValue(node, "PROFITTYPE");
    	String ItemKindNo  = XMLUtils.getChildNodeValue(node, "ITEMKINDNO");
    	String KindCode  = XMLUtils.getChildNodeValue(node, "KINDCODE");
    	String Currency = XMLUtils.getChildNodeValue(node, "CURRENCY");
    	String Discount = XMLUtils.getChildNodeValue(node, "DISCOUNT");
    	String TotalProfit = XMLUtils.getChildNodeValue(node, "TOTALPROFIT");
    	String MinusFlag = XMLUtils.getChildNodeValue(node, "MINUSFLAG");
    	String HandlerCode = XMLUtils.getChildNodeValue(node, "HANDLERCODE");
    	String ApproverCode = XMLUtils.getChildNodeValue(node, "APPROVERCODE");
    	String OperatorCode = XMLUtils.getChildNodeValue(node, "OPERATORCODE");
    	String InputDate = XMLUtils.getChildNodeValue(node, "INPUTDATE");
    	String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
    	
    	PrpTprofitSchema schema = new PrpTprofitSchema();
    	schema.setProposalNo(ProposalNo);
    	schema.setRiskCode(RiskCode);
    	schema.setProfitType(ProfitType);
    	schema.setItemKindNo(ItemKindNo);
    	schema.setKindCode(KindCode);
    	schema.setCurrency(Currency);
    	schema.setDiscount(Discount);
    	schema.setTotalProfit(TotalProfit);
    	schema.setMinusFlag(MinusFlag);
    	schema.setHandlerCode(HandlerCode);
    	schema.setApproverCode(ApproverCode);
    	schema.setOperatorCode(OperatorCode);
    	schema.setInputDate(InputDate);
    	schema.setFlag(Flag);
    	
       
    	blProposal.getBLPrpTprofit().setArr(schema);
    }
    /**
     * @Desc 处理PrpTprofit_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTprofitDetailList(BLProposal blProposal,Node node)
    throws Exception{
    	Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "PROFITDETAIL_DATA");
        BLPrpDcode blPrpDcode = new BLPrpDcode();
    	for(int i=0 ; i < nodes.length ; i++){
    		processPrpTprofitDetailData(blProposal,nodes[i],blPrpDcode);
    	}
    }
    /**
     * @desc 处理PrpTprofit_DATA
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTprofitDetailData(BLProposal blProposal,Node node,BLPrpDcode blPrpDcode)
    throws Exception{

    	String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String RiskCode  = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String ProfitType = XMLUtils.getChildNodeValue(node, "PROFITTYPE");
    	String ProfitPeriod = XMLUtils.getChildNodeValue(node, "PROFITPERIOD");
    	String ItemKindNo = XMLUtils.getChildNodeValue(node, "ITEMKINDNO");
    	String KindCode = XMLUtils.getChildNodeValue(node, "KINDCODE");
    	String KindName = XMLUtils.getChildNodeValue(node, "KINDNAME");
    	String ProfitCode = XMLUtils.getChildNodeValue(node, "PROFITCODE");
    	String ProfitName = XMLUtils.getChildNodeValue(node, "PROFITNAME");
    	String SerialNo = XMLUtils.getChildNodeValue(node, "SERIALNO");
    	String Condition = XMLUtils.getChildNodeValue(node, "CONDITION");
    	String FieldValue = XMLUtils.getChildNodeValue(node, "FIELDVALUE");
    	String ProfitRate = XMLUtils.getChildNodeValue(node, "PROFITRATE");
    	String ChooseFlag = XMLUtils.getChildNodeValue(node, "CHOOSEFLAG");
    	String Flag =  XMLUtils.getChildNodeValue(node, "FLAG");
    	String  ConditionCode= XMLUtils.getChildNodeValue(node, "CONDITIONCODE");
    	
    	PrpTprofitDetailSchema schema = new PrpTprofitDetailSchema();
    	schema.setProposalNo(ProposalNo);
    	schema.setRiskCode(RiskCode);
    	schema.setProfitType(ProfitType);
    	schema.setProfitPeriod(ProfitPeriod);
    	schema.setItemKindNo(ItemKindNo);
    	schema.setKindCode(KindCode);
        KindName=blPrpDcode.translateCode("KindCode",KindCode,true);
    	schema.setKindName(KindName);
        if(ProfitCode!=null&&!ProfitCode.trim().equals(""))
    	schema.setProfitCode(ProfitCode);
        
    	schema.setProfitName(ProfitName);
    	schema.setSerialNo(SerialNo);
    	schema.setCondition(Condition);
        FieldValue="0.00";
    	schema.setFieldValue(FieldValue);
    	schema.setProfitRate(ProfitRate);
        if(ProfitRate!=null&&!ProfitRate.trim().equals(""))
    	schema.setProfitRate(ProfitRate);
        ChooseFlag="1";
    	schema.setChooseFlag(ChooseFlag);
    	schema.setFlag(Flag);
    	schema.setConditionCode(ConditionCode);
        
    	
    	blProposal.getBLPrpTprofitDetail().setArr(schema);
    }
    /**
     * @desc  处理PrpTexpense_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTexpenseList(BLProposal blProposal,Node node) 
    throws Exception{
    	
    	Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "EXPENSE_DATA");
    	
    	for(int i=0 ; i < nodes.length ; i++){
    		processPrpTexpenseData(blProposal,nodes[i]);
    	}
    }
    /**
     * @desc 处理PrpTexpense_DATA
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTexpenseData(BLProposal blProposal,Node node) 
    throws Exception{
    	String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String RiskCode = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String ManageFeeRate = XMLUtils.getChildNodeValue(node, "MANAGEFEERATE");
    	String MaxManageFeeRate = XMLUtils.getChildNodeValue(node, "MAXMANAGEFEERATE");
    	String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
    	String Currency = XMLUtils.getChildNodeValue(node, "CURRENCY");
    	String salesSalaryRate = XMLUtils.getChildNodeValue(node, "SALESSALARYRATE");
    	String teamFeeRate = XMLUtils.getChildNodeValue(node, "TEAMFEERATE");
    	String officeFeeRate = XMLUtils.getChildNodeValue(node, "OFFICEFEERATE");
    	String otherRate = XMLUtils.getChildNodeValue(node, "OTHERRATE");
    	
    	PrpTexpenseSchema schema = new PrpTexpenseSchema();
    	schema.setProposalNo(ProposalNo);
    	schema.setRiskCode(RiskCode);
    	schema.setManageFeeRate(ManageFeeRate);
    	schema.setMaxManageFeeRate(MaxManageFeeRate);
    	schema.setFlag(Flag);
    	schema.setCurrency(Currency);
    	schema.setSalesSalaryRate(salesSalaryRate);
    	schema.setTeamFeeRate(teamFeeRate);
    	schema.setOfficeFeeRate(officeFeeRate);
    	schema.setOtherRate(otherRate);
    	
    	blProposal.getBLPrpTexpense().setArr(schema);
    }
    /**
     * @desc 处理 PrpTcarDriver_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTcarDriverList(BLProposal blProposal,Node node)
    throws Exception{
    	Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "DRIVER_DATA");
    	for(int i=0 ; i < nodes.length ; i++){
    		processPrpTcarDriverData(blProposal,nodes[i]);
    	}
    }
   /**
    * @desc 处理 PrpTcarDriver_DATA
    * @param blProposal
    * @param node
    * @throws Exception
    */
    
    public void processPrpTcarDriverData(BLProposal blProposal,Node node) 
    throws Exception{
    	String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String RiskCode = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String ItemNo = XMLUtils.getChildNodeValue(node, "ITEMNO");
    	String SerialNo = XMLUtils.getChildNodeValue(node, "SERIALNO");
    	String DrivingLicenseNo = XMLUtils.getChildNodeValue(node, "DRIVINGLICENSENO");
    	String ChangelessFlag = XMLUtils.getChildNodeValue(node, "CHANGELESSFLAG");
    	String DriverName = XMLUtils.getChildNodeValue(node, "DRIVERNAME");
    	String IdentifyNumber = XMLUtils.getChildNodeValue(node, "IDENTIFYNUMBER");
    	String Sex = XMLUtils.getChildNodeValue(node, "SEX");
    	String Age = XMLUtils.getChildNodeValue(node, "AGE");
    	String Marriage = XMLUtils.getChildNodeValue(node, "MARRIAGE");
    	String DriverAddress = XMLUtils.getChildNodeValue(node, "DRIVERADDRESS");
    	String PossessNature = XMLUtils.getChildNodeValue(node, "POSSESSNATURE");
    	String BusinessSource = XMLUtils.getChildNodeValue(node, "BUSINESSSOURCE");
    	String Peccancy = XMLUtils.getChildNodeValue(node, "PECCANCY");
    	String AcceptLicenseDate = XMLUtils.getChildNodeValue(node, "ACCEPTLICENSEDATE");
    	String ReceiveLicenseYear = XMLUtils.getChildNodeValue(node, "RECEIVELICENSEYEAR");
    	String DrivingYears = XMLUtils.getChildNodeValue(node, "DRIVINGYEARS");
    	String CauseTroubleTimes = XMLUtils.getChildNodeValue(node, "CAUSETROUBLETIMES");
    	String AwardLicenseOrgan = XMLUtils.getChildNodeValue(node, "AWARDLICENSEORGAN");
    	String DrivingCarType = XMLUtils.getChildNodeValue(node, "DRIVINGCARTYPE");
    	String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
    	String AppliYearType = XMLUtils.getChildNodeValue(node, "APPLIYEARTYPE");
        
        String Birthday = XMLUtils.getChildNodeValue(node, "BIRTHDAY");
        String OccupationCode = XMLUtils.getChildNodeValue(node, "OCCUPATIONCODE");
        String Unit = XMLUtils.getChildNodeValue(node, "UNIT");
        String UnitScale = XMLUtils.getChildNodeValue(node, "UNITSCALE");
        String PartTimeOccupationCode = XMLUtils.getChildNodeValue(node, "PARTTIMEOCCUPATIONCODE");
        
        String IdentifyType = XMLUtils.getChildNodeValue(node, "IDENTIFYTYPE");
        
    	
    	PrpTcarDriverSchema schema = new PrpTcarDriverSchema();
        if(Birthday!=null&&!Birthday.trim().equals(""))
        schema.setBirthday(Birthday);
        schema.setOccupationCode(OccupationCode);
        schema.setUnit(Unit);
        schema.setUnitScale(UnitScale);
        schema.setPartTimeOccupationCode(PartTimeOccupationCode);
    	schema.setProposalNo(ProposalNo);
    	schema.setRiskCode(RiskCode);
    	schema.setItemNo(ItemNo);
    	schema.setSerialNo(SerialNo);
    	schema.setDrivingLicenseNo(DrivingLicenseNo);
    	schema.setChangelessFlag(ChangelessFlag);
    	schema.setDriverName(DriverName);
    	schema.setIdentifyNumber(IdentifyNumber);
    	schema.setSex(Sex);
    	schema.setAge(Age);
    	schema.setMarriage(Marriage);
    	schema.setDriverAddress(DriverAddress);
    	schema.setPossessNature(PossessNature);
    	schema.setBusinessSource(BusinessSource);
    	schema.setPeccancy(Peccancy);
        if(AcceptLicenseDate!=null&&!AcceptLicenseDate.trim().equals("")&&AcceptLicenseDate.length()>9)
    	schema.setAcceptLicenseDate(AcceptLicenseDate);
    	schema.setReceiveLicenseYear(ReceiveLicenseYear);
    	schema.setDrivingYears(DrivingYears);
    	schema.setCauseTroubleTimes(CauseTroubleTimes);
    	schema.setAwardLicenseOrgan(AwardLicenseOrgan);
    	schema.setDrivingCarType(DrivingCarType);
    	schema.setFlag(Flag);
    	schema.setAppliYearType(AppliYearType);
    	
    	schema.setIdentifyType(IdentifyType);
    	
    	
        
    	blProposal.getBLPrpTcarDriver().setArr(schema);
    }
    /**
     * @desc 处理PrpTcarDevice_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTcarDeviceList(BLProposal blProposal,Node node)
    throws Exception{
    	Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "DEVICE_DATA");
    	for(int i=0 ; i < nodes.length ; i++){
    		processPrpTcarDeviceData(blProposal,nodes[i]);
    	}
    }
    /**
     * @desc 处理PrpTcarDevice_DATA
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTcarDeviceData(BLProposal blProposal,Node node) 
    throws Exception{
    	String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String RiskCode = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String ItemNo = XMLUtils.getChildNodeValue(node, "ITEMNO");
    	String SerialNo = XMLUtils.getChildNodeValue(node, "SERIALNO");
    	String DeviceName = XMLUtils.getChildNodeValue(node, "DEVICENAME");
    	String Currency = XMLUtils.getChildNodeValue(node, "CURRENCY");
    	String Quantity = XMLUtils.getChildNodeValue(node, "QUANTITY");
    	String PurchasePrice = XMLUtils.getChildNodeValue(node, "PURCHASEPRICE");
    	String ActualValue = XMLUtils.getChildNodeValue(node, "ACTUALVALUE");
    	String Remark = XMLUtils.getChildNodeValue(node, "REMARK");
    	String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
    	String BuyDate = XMLUtils.getChildNodeValue(node, "BUYDATE");
    	PrpTcarDeviceSchema schema = new PrpTcarDeviceSchema ();
    	schema.setProposalNo(ProposalNo);
    	schema.setRiskCode(RiskCode);
    	schema.setItemNo(ItemNo);
    	schema.setSerialNo(SerialNo);
    	schema.setDeviceName(DeviceName);
    	schema.setCurrency(Currency);
    	schema.setQuantity(Quantity);
    	schema.setPurchasePrice(PurchasePrice);
    	schema.setActualValue(ActualValue);
    	schema.setRemark(Remark);
    	schema.setFlag(Flag);
    	schema.setBuyDate(BuyDate);
    	blProposal.getBLPrpTcarDevice().setArr(schema);
    }
    /**
     * @desc 处理 PRPTRENEWAL_LIST
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTrenewalList(BLProposal blProposal,Node node)
    throws Exception{
    	Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "RENEWAL_DATA");
    	for(int i=0 ; i < nodes.length ; i++){
    		processPrpTrenewalData(blProposal,nodes[i]);
    	}
    }
    /**
     * @desc  处理 PRPTRENEWAL_DATA
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTrenewalData(BLProposal blProposal,Node node) 
    throws Exception{
    	String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String OldPolicyNo = XMLUtils.getChildNodeValue(node, "OLDPOLICYNO");
    	String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
    	PrpTrenewalSchema schema = new PrpTrenewalSchema();
    	schema.setProposalNo(ProposalNo);
    	schema.setOldPolicyNo(OldPolicyNo);
    	schema.setFlag(Flag);
    	blProposal.getBLPrpTrenewal().setArr(schema);
    }
    
    
    /**
     * @desc  处理PrpTTrafficDetail_LIST
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTTrafficDetailList(BLProposal blProposal,Node node)throws Exception{
        Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "TRAFFICDETAIL_DATA");
        
        for(int i=0 ; i < nodes.length ; i++){
            processPrpTTrafficDetailData(blProposal,nodes[i]);
        }
    }
    
    public void processPrpTTrafficDetailData(BLProposal blProposal,Node node)throws Exception{
        String proposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
        String riskCode = XMLUtils.getChildNodeValue(node, "RISKCODE");
        String itemNo = XMLUtils.getChildNodeValue(node, "ITEMNO");
        String serialno = XMLUtils.getChildNodeValue(node, "SERIALNO");
        String trafficCode = XMLUtils.getChildNodeValue(node, "TRAFFICCODE");
        String trafficType = XMLUtils.getChildNodeValue(node, "TRAFFICTYPE");
        String accidentType = XMLUtils.getChildNodeValue(node, "ACCIDENTTYPE");
        String accidentDate = XMLUtils.getChildNodeValue(node, "ACCIDENTDATE");
        String transgressTime = XMLUtils.getChildNodeValue(node, "TRANSGRESSTIME");
        String businessType = XMLUtils.getChildNodeValue(node, "BUSINESSTYPE");
        String indemnityduty = XMLUtils.getChildNodeValue(node, "INDEMNITYDUTY");
        String floatration = XMLUtils.getChildNodeValue(node, "FLOATRATIO");
        String sumpaid = XMLUtils.getChildNodeValue(node, "SUMPAID");
        String flag = XMLUtils.getChildNodeValue(node, "FLAG");
        PrpTTrafficDetailSchema prpTTrafficDetail = new PrpTTrafficDetailSchema();
        prpTTrafficDetail.setProposalNo(proposalNo);
        prpTTrafficDetail.setRiskCode(riskCode);
        prpTTrafficDetail.setItemNo(itemNo);
        prpTTrafficDetail.setSerialNo(serialno);
        prpTTrafficDetail.setTrafficCode(trafficCode);
        prpTTrafficDetail.setTrafficType(trafficType);
        prpTTrafficDetail.setAccidentType(accidentType);

        prpTTrafficDetail.setAccidentDate(accidentDate);
        prpTTrafficDetail.setTransGressTime(transgressTime);
        prpTTrafficDetail.setBusinessType(businessType);
        prpTTrafficDetail.setIndemnityDuty(indemnityduty);
        prpTTrafficDetail.setFloatRatio(floatration);
        prpTTrafficDetail.setSumPaid(sumpaid);
        prpTTrafficDetail.setFlag(flag);
        blProposal.getBLPrpTTrafficDetail().setArr(prpTTrafficDetail);
    }
    /**
     * @desc  处理PrpTcarShipTaxTJ_LIST
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTcarShipTaxTJList(BLProposal blProposal,Node node) 
    throws Exception{
    	Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "CARTAXTJ_DATA");
    	for(int i=0 ; i < nodes.length ; i++){
    		processPrpTcarShipTaxTJData(blProposal,nodes[i]);
    	}
    }
    /**
     * @DESC 处理PrpTcarShipTaxTJ_data
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTcarShipTaxTJData(BLProposal blProposal,Node node)
    throws Exception{
    	String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String SerialNo = XMLUtils.getChildNodeValue(node, "SERIALNO");
    	String RiskCode = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String TaxComCode = XMLUtils.getChildNodeValue(node, "TAXCOMCODE");
    	String PaidFreeCertificate = XMLUtils.getChildNodeValue(node, "PAIDFREECERTIFICATE");
    	String LicenseType = XMLUtils.getChildNodeValue(node, "LICENSETYPE");
    	String LicenseNo = XMLUtils.getChildNodeValue(node, "LICENSENO");
    	String EngineNo = XMLUtils.getChildNodeValue(node, "ENGINENO");
    	String ExhaustScale = XMLUtils.getChildNodeValue(node, "EXHAUSTSCALE");
    	String SeatCount = XMLUtils.getChildNodeValue(node, "SEATCOUNT");
    	String EnrollDate = XMLUtils.getChildNodeValue(node, "ENROLLDATE");
    	String LicenseCategory = XMLUtils.getChildNodeValue(node, "LICENSECATEGORY");
    	String CompleteKerbMass = XMLUtils.getChildNodeValue(node, "COMPLETEKERBMASS");
    	String TaxActual = XMLUtils.getChildNodeValue(node, "TAXACTUAL");
    	String PreviousPay = XMLUtils.getChildNodeValue(node, "PREVIOUSPAY");
    	String LateFee = XMLUtils.getChildNodeValue(node, "LATEFEE");
    	String SumPayTax = XMLUtils.getChildNodeValue(node, "SUMPAYTAX");
    	String TaxType = XMLUtils.getChildNodeValue(node, "TAXTYPE");
    	String CarOwnerNature = XMLUtils.getChildNodeValue(node, "CAROWNERNATURE");
    	String ReliefFlag = XMLUtils.getChildNodeValue(node, "RELIEFFLAG");
    	String EnTaxNo = XMLUtils.getChildNodeValue(node, "ENTAXNO");
    	String TaxActual2 = XMLUtils.getChildNodeValue(node, "TAXACTUAL2");
    	String PreviousPay2 = XMLUtils.getChildNodeValue(node, "PREVIOUSPAY2");
    	String CarOwnerName = XMLUtils.getChildNodeValue(node, "CAROWNERNAME");
    	String IdentifyType = XMLUtils.getChildNodeValue(node, "IDENTIFYTYPE");
    	String IdentifyNumber = XMLUtils.getChildNodeValue(node, "IDENTIFYNUMBER");
    	String AddressName = XMLUtils.getChildNodeValue(node, "ADDRESSNAME");
    	String PostAddress = XMLUtils.getChildNodeValue(node, "POSTADDRESS");
    	String PostCode = XMLUtils.getChildNodeValue(node, "POSTCODE");
    	String PhoneNumber = XMLUtils.getChildNodeValue(node, "PHONENUMBER");
    	String CountryCode = XMLUtils.getChildNodeValue(node, "COUNTRYCODE");
    	String CompanyName = XMLUtils.getChildNodeValue(node, "COMPANYNAME");
    	String UnitCertiNo = XMLUtils.getChildNodeValue(node, "UNITCERTINO");
    	String RegisteredAddress = XMLUtils.getChildNodeValue(node, "REGISTEREDADDRESS");
    	String CarAddressCode = XMLUtils.getChildNodeValue(node, "CARADDRESSCODE");
    	String TransactionDate = XMLUtils.getChildNodeValue(node, "TRANSACTIONDATE");
    	String PreLicenseNo = XMLUtils.getChildNodeValue(node, "PRELICENSENO");
    	String FinalFlag = XMLUtils.getChildNodeValue(node, "FINALFLAG");
    	String CalculateFlag = XMLUtils.getChildNodeValue(node, "CALCULATEFLAG");
    	String ExtendChar1 = XMLUtils.getChildNodeValue(node, "EXTENDCHAR1");
    	String ExtendChar2 = XMLUtils.getChildNodeValue(node, "EXTENDCHAR2");
    	String ExtendNum1 = XMLUtils.getChildNodeValue(node, "EXTENDNUM1");
    	String ExtendNum2 = XMLUtils.getChildNodeValue(node, "EXTENDNUM2");
    	String ExtendDate1 = XMLUtils.getChildNodeValue(node, "EXTENDDATE1");
    	String ExtendDate2 = XMLUtils.getChildNodeValue(node, "EXTENDDATE2");
    	String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
    	String PayTaxDate = XMLUtils.getChildNodeValue(node, "PAYTAXDATE");
    	String TaxNum = XMLUtils.getChildNodeValue(node, "TAXNUM");
    	String TaxNumType = XMLUtils.getChildNodeValue(node, "TAXNUMTYPE");
    	
    	String FrameNo = XMLUtils.getChildNodeValue(node, "FRAMENO");
    	
    	
    	PrpTcarshipTaxTJSchema schema = new PrpTcarshipTaxTJSchema();
    	schema.setProposalNo(ProposalNo);
    	schema.setSerialNo(SerialNo);
    	schema.setRiskCode(RiskCode);
    	schema.setTaxComCode(TaxComCode);
    	schema.setPaidFreeCertificate(PaidFreeCertificate);
    	schema.setLicenseType(LicenseType);
    	schema.setLicenseNo(LicenseNo);
    	schema.setEngineNo(EngineNo);
    	schema.setExhaustScale(ExhaustScale);
    	schema.setSeatCount(SeatCount);
    	schema.setEnrollDate(EnrollDate);
    	schema.setLicenseCategory(LicenseCategory);
    	schema.setCompleteKerbMass(CompleteKerbMass);
    	schema.setTaxActual(TaxActual);
    	schema.setPreviousPay(PreviousPay);
    	schema.setLateFee(LateFee);
    	schema.setSumPayTax(SumPayTax);
    	schema.setTaxType(TaxType);
    	schema.setCarOwnerNature(CarOwnerNature);
    	schema.setReliefFlag(ReliefFlag);
    	schema.setEnTaxNo(EnTaxNo);
    	schema.setTaxActual2(TaxActual2);
    	schema.setPreviousPay2(PreviousPay2);
    	schema.setCarOwnerName(CarOwnerName);
    	schema.setIdentifyType(IdentifyType);
    	schema.setIdentifyNumber(IdentifyNumber);
    	schema.setAddressName(AddressName);
    	schema.setPostAddress(PostAddress);
    	schema.setPostCode(PostCode);
    	schema.setPhoneNumber(PhoneNumber);
    	schema.setCountryCode(CountryCode);
    	schema.setCompanyName(CompanyName);
    	schema.setUnitCertiNo(UnitCertiNo);
    	schema.setRegisteredAddress(RegisteredAddress);
    	schema.setCarAddressCode(CarAddressCode);
    	schema.setTransactionDate(TransactionDate);
    	schema.setPreLicenseNo(PreLicenseNo);
    	schema.setFinalFlag(FinalFlag);
    	schema.setCalculateFlag(CalculateFlag);
    	schema.setExtendChar1(ExtendChar1);
    	schema.setExtendChar2(ExtendChar2);
    	schema.setExtendNum1(ExtendNum1);
    	schema.setExtendNum2(ExtendNum2);
    	schema.setExtendDate1(ExtendDate1);
    	schema.setExtendDate2(ExtendDate2);
    	schema.setFlag(Flag);
    	schema.setPayTaxDate(PayTaxDate);
    	schema.setTaxNum(TaxNum);
    	schema.setTaxNumType(TaxNumType);
    	
    	schema.setFrameNo(FrameNo);
    	
    	
    	blProposal.getBLPrpTcarshipTaxTJ().setArr(schema);
    }
    /**
     * @desc  处理PrpTcarShipTax_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTcarShipTaxList(BLProposal blProposal,Node node) 
    throws Exception{
    	Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "CARTAX_DATA");
    	for(int i=0 ; i < nodes.length ; i++){
    		processPrpTcarShipTaxData(blProposal,nodes[i]);
    	}
    }
    /**
     * @desc   处理PrpTcarShipTax_data
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTcarShipTaxData(BLProposal blProposal,Node node) 
    throws Exception{
    	
       
    	String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String SerialNo = XMLUtils.getChildNodeValue(node, "SERIALNO");
    	String RiskCode = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String AreaCode = XMLUtils.getChildNodeValue(node, "AREACODEv");
    	String LicenseNo = XMLUtils.getChildNodeValue(node, "LICENSENO");
    	String LicenseType = XMLUtils.getChildNodeValue(node, "LICENSETYPE");
        
    	String CarKindCode = XMLUtils.getChildNodeValue(node, "CARKINDCODE");
    	String TaxItemCode = XMLUtils.getChildNodeValue(node, "TAXITEMCODE");
    	String TaxItemName = XMLUtils.getChildNodeValue(node, "TAXITEMNAME");
    	String TaxItemDetailCode = XMLUtils.getChildNodeValue(node, "TAXITEMDETAILCODE");
    	String TaxItemDetailName = XMLUtils.getChildNodeValue(node, "TAXITEMDETAILNAME");
    	String BaseTaxation = XMLUtils.getChildNodeValue(node, "BASETAXATION");
    	String TaxpayerCode = XMLUtils.getChildNodeValue(node, "TAXPAYERCODE");
    	String TaxpayerName = XMLUtils.getChildNodeValue(node, "TAXPAYERNAME");
    	String IdentifyNumber = XMLUtils.getChildNodeValue(node, "IDENTIFYNUMBER");
    	String TaxpayerIdentifier = XMLUtils.getChildNodeValue(node, "TAXPAYERIDENTIFIER");
    	String TaxRelifFlag = XMLUtils.getChildNodeValue(node, "TAXRELIFFLAG");
    	String RelifReason = XMLUtils.getChildNodeValue(node, "RELIFREASON");
    	String FreeRate = XMLUtils.getChildNodeValue(node, "FREERATE");
    	String FreeRateText = XMLUtils.getChildNodeValue(node, "FREERATETEXT");
    	String TaxComCode = XMLUtils.getChildNodeValue(node, "TAXCOMCODE");
    	String TaxComName = XMLUtils.getChildNodeValue(node, "TAXCOMNAME");
    	String PaidFreeCertificate = XMLUtils.getChildNodeValue(node, "PAIDFREECERTIFICATE");
    	String TaxUnit = XMLUtils.getChildNodeValue(node, "TAXUNIT");
    	String TaxUnitText = XMLUtils.getChildNodeValue(node, "TAXUNITTEXT");
    	String CompleteKerbMass = XMLUtils.getChildNodeValue(node, "COMPLETEKERBMASS");
    	String CalculateMode = XMLUtils.getChildNodeValue(node, "CALCULATEMODE");
    	String CalculateFlag = XMLUtils.getChildNodeValue(node, "CALCULATEFLAG");
    	String PayLastYear = XMLUtils.getChildNodeValue(node, "PAYLASTYEAR");
    	String HisPolicyEndDate = XMLUtils.getChildNodeValue(node, "HISPOLICYENDDATE");
    	String TaxDue = XMLUtils.getChildNodeValue(node, "TAXDUE");
    	String TaxActual = XMLUtils.getChildNodeValue(node, "TAXACTUAL");
    	String TaxRelief = XMLUtils.getChildNodeValue(node, "TAXRELIEF");
    	String PayStartDate = XMLUtils.getChildNodeValue(node, "PAYSTARTDATE");
    	String PayEndDate = XMLUtils.getChildNodeValue(node, "PAYENDDATE");
    	String PreviousPay = XMLUtils.getChildNodeValue(node, "PREVIOUSPAY");
    	String LateFee = XMLUtils.getChildNodeValue(node, "LATEFEE");
    	String LateFeeStartDate = XMLUtils.getChildNodeValue(node, "LATEFEESTARTDATE");
    	String LateFeeEndDate = XMLUtils.getChildNodeValue(node, "LATEFEEENDDATE");
    	
    	String PayLastYearEnd = XMLUtils.getChildNodeValue(node, "PAYLASTYEAREND");
    	
    	String SumPayTax = XMLUtils.getChildNodeValue(node, "SUMPAYTAX");
    	String LeviedDate = XMLUtils.getChildNodeValue(node, "LEVIEDDATE");
    	String PayTaxTimes = XMLUtils.getChildNodeValue(node, "PAYTAXTIMES");
    	String FinalFlag = XMLUtils.getChildNodeValue(node, "FINALFLAG");
    	String ExtendChar1 = XMLUtils.getChildNodeValue(node, "EXTENDCHAR1");
    	String ExtendChar2 = XMLUtils.getChildNodeValue(node, "EXTENDCHAR2");
    	String ExtendNum1 = XMLUtils.getChildNodeValue(node, "EXTENDNUM1");
    	String ExtendNum2 = XMLUtils.getChildNodeValue(node, "EXTENDNUM2");
    	String ExtendDate1 = XMLUtils.getChildNodeValue(node, "EXTENDDATE1");
    	String ExtendDate2 = XMLUtils.getChildNodeValue(node, "EXTENDDATE2");
    	String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
    	String PayBalanceFee = XMLUtils.getChildNodeValue(node, "PAYBALANCEFEE");
    	String CalFeeFlag = XMLUtils.getChildNodeValue(node, "CALFEEFLAG");
        String CertificateDate = XMLUtils.getChildNodeValue(node, "CERTIFICATEDATE");
        String TaxFlag = XMLUtils.getChildNodeValue(node, "TAXFLAG");
        
        String CalcTaxFlag = XMLUtils.getChildNodeValue(node, "CALCTAXFLAG");
        
    	
    	PrpTcarshipTaxSchema schema = new PrpTcarshipTaxSchema();
    	schema.setProposalNo(ProposalNo);
    	schema.setSerialNo(SerialNo);
    	schema.setRiskCode(RiskCode);
    	schema.setAreaCode(AreaCode);
    	schema.setLicenseNo(LicenseNo);
    	schema.setLicenseType(LicenseType);
    	schema.setCarKindCode(CarKindCode);
    	schema.setTaxItemCode(TaxItemCode);
    	schema.setTaxItemName(TaxItemName);
    	schema.setTaxItemDetailCode(TaxItemDetailCode);
    	schema.setTaxItemDetailName(TaxItemDetailName);
    	schema.setBaseTaxation(BaseTaxation);
    	schema.setTaxpayerCode(TaxpayerCode);
    	schema.setTaxpayerName(TaxpayerName);
    	schema.setIdentifyNumber(IdentifyNumber);
    	schema.setTaxpayerIdentifier(TaxpayerIdentifier);
    	schema.setTaxRelifFlag(TaxRelifFlag);
    	schema.setRelifReason(RelifReason);
    	schema.setFreeRate(FreeRate);
    	schema.setFreeRateText(FreeRateText);
    	schema.setTaxComCode(TaxComCode);
    	schema.setTaxComName(TaxComName);
    	schema.setPaidFreeCertificate(PaidFreeCertificate);
    	schema.setTaxUnit(TaxUnit);
    	schema.setTaxUnitText(TaxUnitText);
    	schema.setCompleteKerbMass(CompleteKerbMass);
    	schema.setCalculateMode(CalculateMode);
    	schema.setCalculateFlag(CalculateFlag);
    	schema.setPayLastYear(PayLastYear);
    	schema.setHisPolicyEndDate(HisPolicyEndDate);
    	schema.setTaxDue(TaxDue);
    	schema.setTaxActual(TaxActual);
    	schema.setTaxRelief(TaxRelief);
    	schema.setPayStartDate(PayStartDate);
    	schema.setPayEndDate(PayEndDate);
    	schema.setPreviousPay(PreviousPay);
    	schema.setLateFee(LateFee);
    	schema.setLateFeeStartDate(LateFeeStartDate);
    	schema.setLateFeeEndDate(LateFeeEndDate);
    	
    	schema.setPayLastYearEnd(PayLastYearEnd);
    	
    	schema.setSumPayTax(SumPayTax);
    	schema.setLeviedDate(LeviedDate);
    	schema.setPayTaxTimes(PayTaxTimes);
    	schema.setFinalFlag(FinalFlag);
    	schema.setExtendChar1(ExtendChar1);
    	schema.setExtendChar2(ExtendChar2);
    	schema.setExtendNum1(ExtendNum1);
    	schema.setExtendNum2(ExtendNum2);
    	schema.setExtendDate1(ExtendDate1);
    	schema.setExtendDate2(ExtendDate2);
    	schema.setFlag(Flag);
    	schema.setPayBalanceFee(PayBalanceFee);
    	schema.setCalFeeFlag(CalFeeFlag);
        schema.setCertificateDate(CertificateDate);
        schema.setTaxFlag(TaxFlag);
        
        schema.setCalcTaxFlag(CalcTaxFlag);
        
    	blProposal.getBLPrpTcarshipTax().setArr(schema);
    }
    /**
     * @desc  处理prptmainsub_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void proceePrpTmainSubList(BLProposal blProposal,Node node) 
    throws Exception{
    	Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "MAINSUB_DATA");
    	for(int i=0 ; i < nodes.length ; i++){
    		proceePrpTmainSubData(blProposal,nodes[i]);
    	}
    }
    /**
     * @desc  处理prptmainsub_data
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void proceePrpTmainSubData(BLProposal blProposal,Node node) 
    throws Exception{
    	
    	String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String MainPolicyNo = XMLUtils.getChildNodeValue(node, "MAINPOLICYNO");
    	String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
    	String Remark = XMLUtils.getChildNodeValue(node, "REMARK");
    	
    	String BIPolicyNo = XMLUtils.getChildNodeValue(node, "BIPOLICYNO");
    	
    	PrpTmainSubSchema schema = new PrpTmainSubSchema();
    	schema.setProposalNo(ProposalNo);
    	schema.setMainPolicyNo(MainPolicyNo);
    	schema.setFlag(Flag);
    	schema.setRemark(Remark);
    	schema.setBIPolicyNo(BIPolicyNo);
    	blProposal.getBLPrpTmainSub().setArr(schema);
    }
    /**
     * @desc 处理PrpTplan_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTplanList(BLProposal blProposal,Node node) 
    throws Exception{
    	Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "PRPTPLAN_DATA");
    	for(int i=0 ; i < nodes.length ; i++){
    		processPrpTplanData(blProposal,nodes[i]);
    	}
    }
    /**
     * @desc 处理PrpTplan_data
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTplanData(BLProposal blProposal,Node node)
    throws Exception{
    	String ProposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String EndorseNo = XMLUtils.getChildNodeValue(node, "ENDORSENO");
    	String SerialNo = XMLUtils.getChildNodeValue(node, "SERIALNO");
    	String PayNo = XMLUtils.getChildNodeValue(node, "PAYNO");
    	String PayReason = XMLUtils.getChildNodeValue(node, "PAYREASON");
    	String PlanDate = XMLUtils.getChildNodeValue(node, "PLANDATE");
    	String Currency = XMLUtils.getChildNodeValue(node, "CURRENCY");
    	String PlanFee = XMLUtils.getChildNodeValue(node, "PLANFEE");
    	String DelinquentFee = XMLUtils.getChildNodeValue(node, "DELINQUENTFEE");
    	String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
    	String PlanStartDate = XMLUtils.getChildNodeValue(node, "PLANSTARTDATE");
    	PrpTplanSchema schema = new PrpTplanSchema();
    	schema.setProposalNo(ProposalNo);
    	schema.setEndorseNo(EndorseNo);
    	schema.setSerialNo(SerialNo);
    	schema.setPayNo(PayNo);
    	schema.setPayReason(PayReason);
    	schema.setPlanDate(PlanDate);
    	schema.setCurrency(Currency);
    	schema.setPlanFee(PlanFee);
    	schema.setDelinquentFee(DelinquentFee);
    	schema.setFlag(Flag);
    	schema.setPlanStartDate(PlanStartDate);
    	blProposal.getBLPrpTplan().setArr(schema);
    }
    /**
     * @desc 处理PrpDcustomer_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpDcustomerList(BLProposal blProposal,Node node) 
    throws Exception{
    	Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "PRPDCUSTOMER_DATA");
    	for(int i=0 ; i < nodes.length ; i++){
    		processPrpDcustomerData(blProposal,nodes[i]);
    	}
    }
    /**
     * @desc 处理PrpDcustomer_data
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpDcustomerData(BLProposal blProposal,Node node)
    throws Exception{
    	
    	String CustomerType = XMLUtils.getChildNodeValue(node, "CUSTOMERTYPE");
    	String CustomerCode = XMLUtils.getChildNodeValue(node, "CUSTOMERCODE");
    	String ShortHandCode = XMLUtils.getChildNodeValue(node, "SHORTHANDCODE");
    	String CustomerCName = XMLUtils.getChildNodeValue(node, "CUSTOMERCNAME");
    	String CustomerEName = XMLUtils.getChildNodeValue(node, "CUSTOMERENAME");
    	String AddressCName = XMLUtils.getChildNodeValue(node, "ADDRESSCNAME");
    	String AddressEName = XMLUtils.getChildNodeValue(node, "ADDRESSENAME");
    	String OrganizeCode = XMLUtils.getChildNodeValue(node, "ORGANIZECODE");
    	String FatherCode = XMLUtils.getChildNodeValue(node, "FATHERCODE");
    	String BlackState = XMLUtils.getChildNodeValue(node, "BLACKSTATE");
    	String CustomerKind = XMLUtils.getChildNodeValue(node, "CUSTOMERKIND");
    	String CustomerFlag = XMLUtils.getChildNodeValue(node, "CUSTOMERFLAG");
    	String ArticleCode = XMLUtils.getChildNodeValue(node, "ARTICLECODE");
    	String ValidStatus = XMLUtils.getChildNodeValue(node, "VALIDSTATUS");
    	PrpDcustomerSchema schema = new PrpDcustomerSchema();
    	schema.setCustomerType(CustomerType);
    	schema.setCustomerCode(CustomerCode);
    	schema.setShortHandCode(ShortHandCode);
    	schema.setCustomerCName(CustomerCName);
    	schema.setCustomerEName(CustomerEName);
    	schema.setAddressCName(AddressCName);
    	schema.setAddressEName(AddressEName);
    	schema.setOrganizeCode(OrganizeCode);
    	schema.setFatherCode(FatherCode);
    	schema.setBlackState(BlackState);
    	schema.setCustomerKind(CustomerKind);
    	schema.setCustomerFlag(CustomerFlag);
    	schema.setArticleCode(ArticleCode);
    	schema.setValidStatus(ValidStatus);
    	
    }
    /**
     * @desc  处理PrpDcustomerIdv_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpDcustomerIdvList(BLProposal blProposal,Node node)
    throws Exception{
    	Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "PRPDCUSTOMERIDV_DATA");
    	for(int i=0 ; i < nodes.length ; i++){
    		processPrpDcustomerIdvData(blProposal,nodes[i]);
    	}
    }
    /**
     * @desc  处理PrpDcustomerIdv_data
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpDcustomerIdvData(BLProposal blProposal,Node node) 
    throws Exception{
    	String CustomerCode = XMLUtils.getChildNodeValue(node, "CUSTOMERCODE");
    	String Password = XMLUtils.getChildNodeValue(node, "PASSWORD");
    	String ShortHandCode = XMLUtils.getChildNodeValue(node, "SHORTHANDCODE");
    	String CustomerCName = XMLUtils.getChildNodeValue(node, "CUSTOMERCNAME");
    	String CustomerEName = XMLUtils.getChildNodeValue(node, "CUSTOMERENAME");
    	String AddressCName = XMLUtils.getChildNodeValue(node, "ADDRESSCNAME");
    	String AddressEName = XMLUtils.getChildNodeValue(node, "ADDRESSENAME");
    	String IdentifyType = XMLUtils.getChildNodeValue(node, "IDENTIFYTYPE");
    	String IdentifyNumber = XMLUtils.getChildNodeValue(node, "IDENTIFYNUMBER");
    	String CreditLevel = XMLUtils.getChildNodeValue(node, "CREDITLEVEL");
    	String Sex = XMLUtils.getChildNodeValue(node, "SEX");
    	String Age = XMLUtils.getChildNodeValue(node, "AGE");
    	String Health = XMLUtils.getChildNodeValue(node, "HEALTH");
    	String OccupationCode = XMLUtils.getChildNodeValue(node, "OCCUPATIONCODE");
    	String EducationCode = XMLUtils.getChildNodeValue(node, "EDUCATIONCODE");
    	String Unit = XMLUtils.getChildNodeValue(node, "UNIT");
    	String UnitAddress = XMLUtils.getChildNodeValue(node, "UNITADDRESS");
    	String CustomerKind = XMLUtils.getChildNodeValue(node, "CUSTOMERKIND");
    	String CustomerFlag = XMLUtils.getChildNodeValue(node, "CUSTOMERFLAG");
    	String PhoneNumber = XMLUtils.getChildNodeValue(node, "PHONENUMBER");
    	String FaxNumber = XMLUtils.getChildNodeValue(node, "FAXNUMBER");
    	String Mobile = XMLUtils.getChildNodeValue(node, "MOBILE");
    	String LinkAddress = XMLUtils.getChildNodeValue(node, "LINKADDRESS");
    	String PostCode = XMLUtils.getChildNodeValue(node, "POSTCODE");
    	String Pager = XMLUtils.getChildNodeValue(node, "PAGER");
    	String Email = XMLUtils.getChildNodeValue(node, "EMAIL");
    	String Bank = XMLUtils.getChildNodeValue(node, "BANK");
    	String Account = XMLUtils.getChildNodeValue(node, "ACCOUNT");
    	String DeathDate = XMLUtils.getChildNodeValue(node, "DEATHDATE");
    	String BlackState = XMLUtils.getChildNodeValue(node, "BLACKSTATE");
    	String NewCustomerCode = XMLUtils.getChildNodeValue(node, "NEWCUSTOMERCODE");
    	String ValidStatus = XMLUtils.getChildNodeValue(node, "VALIDSTATUS");
    	String ArticleCode = XMLUtils.getChildNodeValue(node, "ARTICLECODE");
    	String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
    	String NetAddress = XMLUtils.getChildNodeValue(node, "NETADDRESS");
    	String LowerViewFlag = XMLUtils.getChildNodeValue(node, "LOWERVIEWFLAG");
    	String HandlerCode = XMLUtils.getChildNodeValue(node, "HANDLERCODE");
    	String OperatorCode = XMLUtils.getChildNodeValue(node, "OPERATORCODE");
    	String InputDate = XMLUtils.getChildNodeValue(node, "INPUTDATE");
    	String UpdaterCode = XMLUtils.getChildNodeValue(node, "UPDATERCODE");
    	String UpdateDate = XMLUtils.getChildNodeValue(node, "UPDATEDATE");
    	String Comcode = XMLUtils.getChildNodeValue(node, "COMCODE");
    	String TopLevelFlag = XMLUtils.getChildNodeValue(node, "TOPLEVELFLAG");
    	String BirthDate = XMLUtils.getChildNodeValue(node, "BIRTHDATE");
    	PrpDcustomerIdvSchema schema = new PrpDcustomerIdvSchema();
    	schema.setCustomerCode(CustomerCode);
    	schema.setPassword(Password);
    	schema.setShortHandCode(ShortHandCode);
    	schema.setCustomerCName(CustomerCName);
    	schema.setCustomerEName(CustomerEName);
    	schema.setAddressCName(AddressCName);
    	schema.setAddressEName(AddressEName);
    	schema.setIdentifyType(IdentifyType);
    	schema.setIdentifyNumber(IdentifyNumber);
    	schema.setCreditLevel(CreditLevel);
    	schema.setSex(Sex);
    	schema.setAge(Age);
    	schema.setHealth(Health);
    	schema.setOccupationCode(OccupationCode);
    	schema.setEducationCode(EducationCode);
    	schema.setUnit(Unit);
    	schema.setUnitAddress(UnitAddress);
    	schema.setCustomerKind(CustomerKind);
    	schema.setCustomerFlag(CustomerFlag);
    	schema.setPhoneNumber(PhoneNumber);
    	schema.setFaxNumber(FaxNumber);
    	schema.setMobile(Mobile);
    	schema.setLinkAddress(LinkAddress);
    	schema.setPostCode(PostCode);
    	schema.setPager(Pager);
    	schema.setEmail(Email);
    	schema.setBank(Bank);
    	schema.setAccount(Account);
    	schema.setDeathDate(DeathDate);
    	schema.setBlackState(BlackState);
    	schema.setNewCustomerCode(NewCustomerCode);
    	schema.setValidStatus(ValidStatus);
    	schema.setArticleCode(ArticleCode);
    	schema.setFlag(Flag);
    	schema.setNetAddress(NetAddress);
    	schema.setLowerViewFlag(LowerViewFlag);
    	schema.setHandlerCode(HandlerCode);
    	schema.setOperatorCode(OperatorCode);
    	schema.setInputDate(InputDate);
    	schema.setUpdaterCode(UpdaterCode);
    	schema.setUpdateDate(UpdateDate);
    	schema.setComcode(Comcode);
    	schema.setTopLevelFlag(TopLevelFlag);
    	schema.setBirthDate(BirthDate);
    	
    }
    /**
     * @desc  处理PrpDcustomerUnit_List
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpDcustomerUnitList(BLProposal blProposal,Node node) 
    throws Exception{
    	Node[]  nodes = XMLUtils.getChildNodesByTagName(node, "PRPDCUSTOMERUNIT_DATA");
    	for(int i=0 ; i < nodes.length ; i++){
    		processPrpDcustomerUnitData(blProposal,nodes[i]);
    	}
    }
    /**
     * @desc 处理PrpDcustomerUnit_data
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpDcustomerUnitData(BLProposal blProposal,Node node)
    throws Exception{
    	String CustomerCode = XMLUtils.getChildNodeValue(node, "CUSTOMERCODE");
    	String Password = XMLUtils.getChildNodeValue(node, "PASSWORD");
    	String ShortHandCode = XMLUtils.getChildNodeValue(node, "SHORTHANDCODE");
    	String CustomerCName = XMLUtils.getChildNodeValue(node, "CUSTOMERCNAME");
    	String CustomerEName = XMLUtils.getChildNodeValue(node, "CUSTOMERENAME");
    	String AddressCName = XMLUtils.getChildNodeValue(node, "ADDRESSCNAME");
    	String AddressEName = XMLUtils.getChildNodeValue(node, "ADDRESSENAME");
    	String PossessNature = XMLUtils.getChildNodeValue(node, "POSSESSNATURE");
    	String BusinessSource = XMLUtils.getChildNodeValue(node, "BUSINESSSOURCE");
    	String BusinessSort = XMLUtils.getChildNodeValue(node, "BUSINESSSORT");
    	String CustomerKind = XMLUtils.getChildNodeValue(node, "CUSTOMERKIND");
    	String CustomerFlag = XMLUtils.getChildNodeValue(node, "CUSTOMERFLAG");
    	String OrganizeCode = XMLUtils.getChildNodeValue(node, "ORGANIZECODE");
    	String CreditLevel = XMLUtils.getChildNodeValue(node, "CREDITLEVEL");
    	String LeaderName = XMLUtils.getChildNodeValue(node, "LEADERNAME");
    	String PhoneNumber = XMLUtils.getChildNodeValue(node, "PHONENUMBER");
    	String FaxNumber = XMLUtils.getChildNodeValue(node, "FAXNUMBER");
    	String Mobile = XMLUtils.getChildNodeValue(node, "MOBILE");
    	String NetAddress = XMLUtils.getChildNodeValue(node, "NETADDRESS");
    	String EmailAddress = XMLUtils.getChildNodeValue(node, "EMAILADDRESS");
    	String PostAddress = XMLUtils.getChildNodeValue(node, "POSTADDRESS");
    	String PostCode = XMLUtils.getChildNodeValue(node, "POSTCODE");
    	String LinkerName = XMLUtils.getChildNodeValue(node, "LINKERNAME");
    	String Bank = XMLUtils.getChildNodeValue(node, "BANK");
    	String Account = XMLUtils.getChildNodeValue(node, "ACCOUNT");
    	String IndustryCode = XMLUtils.getChildNodeValue(node, "INDUSTRYCODE");
    	String EconomyCode = XMLUtils.getChildNodeValue(node, "ECONOMYCODE");
    	String MeasureCode = XMLUtils.getChildNodeValue(node, "MEASURECODE");
    	String FatherCode = XMLUtils.getChildNodeValue(node, "FATHERCODE");
    	String SponsorName = XMLUtils.getChildNodeValue(node, "SPONSORNAME");
    	String BusinessRange = XMLUtils.getChildNodeValue(node, "BUSINESSRANGE");
    	String RegistFund = XMLUtils.getChildNodeValue(node, "REGISTFUND");
    	String RegionCode = XMLUtils.getChildNodeValue(node, "REGIONCODE");
    	String BlackState = XMLUtils.getChildNodeValue(node, "BLACKSTATE");
    	String NewCustomerCode = XMLUtils.getChildNodeValue(node, "NEWCUSTOMERCODE");
    	String ValidStatus = XMLUtils.getChildNodeValue(node, "VALIDSTATUS");
    	String ArticleCode = XMLUtils.getChildNodeValue(node, "ARTICLECODE");
    	String Flag = XMLUtils.getChildNodeValue(node, "FLAG");
    	String CustomerShortName = XMLUtils.getChildNodeValue(node, "CUSTOMERSHORTNAME");
    	String EmploySum = XMLUtils.getChildNodeValue(node, "EMPLOYSUM");
    	String ShareHolderFlag = XMLUtils.getChildNodeValue(node, "SHAREHOLDERFLAG");
    	String RevenueCode = XMLUtils.getChildNodeValue(node, "REVENUECODE");
    	String WordRiskRank = XMLUtils.getChildNodeValue(node, "WORDRISKRANK");
    	String LowerViewFlag = XMLUtils.getChildNodeValue(node, "LOWERVIEWFLAG");
    	String HandlerCode = XMLUtils.getChildNodeValue(node, "HANDLERCODE");
    	String OperatorCode = XMLUtils.getChildNodeValue(node, "OPERATORCODE");
    	String InputDate = XMLUtils.getChildNodeValue(node, "INPUTDATE");
    	String UpdaterCode = XMLUtils.getChildNodeValue(node, "UPDATERCODE");
    	String UpdateDate = XMLUtils.getChildNodeValue(node, "UPDATEDATE");
    	String Comcode = XMLUtils.getChildNodeValue(node, "COMCODE");
    	String TopLevelFlag = XMLUtils.getChildNodeValue(node, "TOPLEVELFLAG");
    	String CareerRiskGrade = XMLUtils.getChildNodeValue(node, "CAREERRISKGRADE");
    	
    	PrpDcustomerUnitSchema schema  =new PrpDcustomerUnitSchema();
    	schema.setCustomerCode(CustomerCode);
    	schema.setPassword(Password);
    	schema.setShortHandCode(ShortHandCode);
    	schema.setCustomerCName(CustomerCName);
    	schema.setCustomerEName(CustomerEName);
    	schema.setAddressCName(AddressCName);
    	schema.setAddressEName(AddressEName);
    	schema.setPossessNature(PossessNature);
    	schema.setBusinessSource(BusinessSource);
    	schema.setBusinessSort(BusinessSort);
    	schema.setCustomerKind(CustomerKind);
    	schema.setCustomerFlag(CustomerFlag);
    	schema.setOrganizeCode(OrganizeCode);
    	schema.setCreditLevel(CreditLevel);
    	schema.setLeaderName(LeaderName);
    	schema.setPhoneNumber(PhoneNumber);
    	schema.setFaxNumber(FaxNumber);
    	schema.setMobile(Mobile);
    	schema.setNetAddress(NetAddress);
    	schema.setEmailAddress(EmailAddress);
    	schema.setPostAddress(PostAddress);
    	schema.setPostCode(PostCode);
    	schema.setLinkerName(LinkerName);
    	schema.setBank(Bank);
    	schema.setAccount(Account);
    	schema.setIndustryCode(IndustryCode);
    	schema.setEconomyCode(EconomyCode);
    	schema.setMeasureCode(MeasureCode);
    	schema.setFatherCode(FatherCode);
    	schema.setSponsorName(SponsorName);
    	schema.setBusinessRange(BusinessRange);
    	schema.setRegistFund(RegistFund);
    	schema.setRegionCode(RegionCode);
    	schema.setBlackState(BlackState);
    	schema.setNewCustomerCode(NewCustomerCode);
    	schema.setValidStatus(ValidStatus);
    	schema.setArticleCode(ArticleCode);
    	schema.setFlag(Flag);
    	schema.setCustomerShortName(CustomerShortName);
    	schema.setEmploySum(EmploySum);
    	schema.setShareHolderFlag(ShareHolderFlag);
    	schema.setRevenueCode(RevenueCode);
    	schema.setWordRiskRank(WordRiskRank);
    	schema.setLowerViewFlag(LowerViewFlag);
    	schema.setHandlerCode(HandlerCode);
    	schema.setOperatorCode(OperatorCode);
    	schema.setInputDate(InputDate);
    	schema.setUpdaterCode(UpdaterCode);
    	schema.setUpdateDate(UpdateDate);
    	schema.setComcode(Comcode);
    	schema.setTopLevelFlag(TopLevelFlag);
    	schema.setCareerRiskGrade(CareerRiskGrade);
    	
    }

   
    /**
     * @desc 处理processPrpPhoneSaleExpense_List
     * @param blProposal
     * @param node
     * @throws Exception
     */ 
    private void processPrpPhoneSaleExpenseList(BLProposal blProposal, Node node)throws Exception {
		
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "PRPPHONESALEEXPENSE_DATA");
    	for(int i=0; i<nodes.length; i++){
    		processPrpPrpPhoneSaleExpenseData(blProposal,nodes[i]);
    	}
	}
    
    /**
     * @desc 处理processPrpPrpPhoneSaleExpense_Data
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpPrpPhoneSaleExpenseData(BLProposal blProposal,Node node)
    throws Exception{
    	
    	String DeliveryRateBI = XMLUtils.getChildNodeValue(node, "DELIVERYRATEBI");
    	String PromotionRateBI = XMLUtils.getChildNodeValue(node, "PROMOTIONRATEBI");
    	String DeliveryRateCI = XMLUtils.getChildNodeValue(node, "DELIVERYRATECI");
    	String PromotionRateCI = XMLUtils.getChildNodeValue(node, "PROMOTIONRATECI");
    	String riskCode = blProposal.getBLPrpTmain().getArr(0).getRiskCode();
    	PrpPhoneSaleExpenseSchema schema = new PrpPhoneSaleExpenseSchema();   	
    	schema.setRiskCode(blProposal.getBLPrpTmain().getArr(0).getRiskCode());
    	schema.setProposalNo(blProposal.getBLPrpTmain().getArr(0).getProposalNo());
    	schema.setPolicyNo(blProposal.getBLPrpTmain().getArr(0).getPolicyNo());
    	schema.setChannelType(blProposal.getBLPrpTmain().getArr(0).getChannelType());
    	schema.setVentureFlag(blProposal.getBLPrpTmain().getArr(0).getVentureFlag());
    	if(riskCode.equals(SysConfig.getProperty("RISK_CI"))){
    		schema.setDeliveryRate(DeliveryRateCI);
        	schema.setPromotionRate(PromotionRateCI);	
    		schema.setComDeliveryRate(DeliveryRateBI);
        	schema.setComPromotionRate(PromotionRateBI);
    	}else {
    		schema.setDeliveryRate(DeliveryRateBI);
        	schema.setPromotionRate(PromotionRateBI);	
    		schema.setComDeliveryRate(DeliveryRateCI);
        	schema.setComPromotionRate(PromotionRateCI);
    	}
    	blProposal.getBLPrpPhoneSaleExpense().setArr(schema);
    }
  
    
  
    /**
     * @desc 处理processPrpPhoneSaleMainAgri_List
     * @param blProposal
     * @param node
     * @throws Exception
     */ 
    private void processPrpTMainAgriList(BLProposal blProposal, Node node)throws Exception {
		
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "MAINAGRI_DATA");
    	for(int i=0; i<nodes.length; i++){
    		processPrpTMainAgriData(blProposal,nodes[i]);
    	}
	}
    
    /**
     * @desc 处理processPrpPhoneSaleMainAgri_Data
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrpTMainAgriData(BLProposal blProposal,Node node)
    throws Exception{
    	
    	String InsureAgri = XMLUtils.getChildNodeValue(node, "INSUREAGRI");
    	String ItemAgri = XMLUtils.getChildNodeValue(node, "ITEMAGRI");
    	String RiskCode = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	PrpTmainAgriSchema schema = new PrpTmainAgriSchema();   	
    	schema.setInsureAgri(InsureAgri);
    	schema.setItemAgri(ItemAgri);
    	schema.setRiskCode(RiskCode);
    	blProposal.getBLPrpTmainAgri().setArr(schema);
    }
  
    
    
  
    /**
     * @desc 处理processRelateCarProposal_List
     * @param blProposal
     * @param node
     * @throws Exception
     */ 
    private void processRelateCarProposalList(BLProposal blProposal, Node node)throws Exception {
		
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "RELATECARPROPOSAL_DATA");
    	for(int i=0; i<nodes.length; i++){
    		processRelateCarProposalData(blProposal,nodes[i]);
    	}
	}
    /**
     * @desc 处理processRelateCarProposal_Data
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processRelateCarProposalData(BLProposal blProposal,Node node)
    throws Exception{  	
    	String riskCode = XMLUtils.getChildNodeValue(node, "RISKCODE");
    	String proposalNo = XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	
    	String flag = XMLUtils.getChildNodeValue(node, "FLAG"); 
    	
    	PrpCarRelationSchema schema = new PrpCarRelationSchema();
    	schema.setRiskCode(riskCode);
    	schema.setProposalNo(proposalNo);
    	
    	schema.setFlag(flag);
    	
    	blProposal.getBlPrpCarRelation().setArr(schema);    	
    }
    
    
    
    /**
     * @desc 处理processPrptnewengageList
     * @param blProposal
     * @param node
     * @throws Exception
     */ 
    private void processPrptnewengageList(BLProposal blProposal, Node node)throws Exception {
    	Node[] nodes = XMLUtils.getChildNodesByTagName(node, "PRPTNEWENGAGE_DATA");
    	for(int i=0; i<nodes.length; i++){
    		processPrptnewengageData(blProposal,nodes[i]);
    	}
	}
    
    /**
     * @desc 处理processPrptnewengageData
     * @param blProposal
     * @param node
     * @throws Exception
     */
    public void processPrptnewengageData(BLProposal blProposal,Node node)throws Exception{  	
    	String strPROPOSALNO	= XMLUtils.getChildNodeValue(node, "PROPOSALNO");
    	String strFIRBENNEFITINFO	= XMLUtils.getChildNodeValue(node, "FIRBENNEFITINFO");
    	String strMANAGERNAME	= XMLUtils.getChildNodeValue(node, "MANAGERNAME");
    	String strMANAGERMOBILENO	= XMLUtils.getChildNodeValue(node, "MANAGERMOBILENO");
    	String strSUNSHINECUSTOMER	= XMLUtils.getChildNodeValue(node, "SUNSHINECUSTOMER");
    	String strSPECIALIZE	= XMLUtils.getChildNodeValue(node, "SPECIALIZE");
    	String strFLAG	= XMLUtils.getChildNodeValue(node, "FLAG");
    	
    	
    	if(blProposal.getBlPrpTNewEngage().getSize()>0){
    		blProposal.getBlPrpTNewEngage().getArr(0).setProposalNo(strPROPOSALNO);
    		blProposal.getBlPrpTNewEngage().getArr(0).setFirbennefitInfo(strFIRBENNEFITINFO);
    		blProposal.getBlPrpTNewEngage().getArr(0).setManagerName(strMANAGERNAME);
    		blProposal.getBlPrpTNewEngage().getArr(0).setManagerMobileNo(strMANAGERMOBILENO);
    		blProposal.getBlPrpTNewEngage().getArr(0).setSunShineCustomer(strSUNSHINECUSTOMER);	
    		blProposal.getBlPrpTNewEngage().getArr(0).setSpecialize(strSPECIALIZE);
    		blProposal.getBlPrpTNewEngage().getArr(0).setFlag(strFLAG);
    	}else{
    		PrpTNewEngageSchema prpTNewEngageSchema=new PrpTNewEngageSchema();
    		prpTNewEngageSchema.setProposalNo(strPROPOSALNO);
        	prpTNewEngageSchema.setFirbennefitInfo(strFIRBENNEFITINFO);
        	prpTNewEngageSchema.setManagerName(strMANAGERNAME);
        	prpTNewEngageSchema.setManagerMobileNo(strMANAGERMOBILENO);
        	prpTNewEngageSchema.setSunShineCustomer(strSUNSHINECUSTOMER);	
        	prpTNewEngageSchema.setSpecialize(strSPECIALIZE);
        	prpTNewEngageSchema.setFlag(strFLAG);
        	blProposal.getBlPrpTNewEngage().setArr(prpTNewEngageSchema);   	
    	}	
    	
    	
    	
    }
    
    
   
    /**
     * @desc   处理 prptitemcar_data 
     * @param blProposal
     * @param node
     * @throws Exception
     */
    private  void processCarExtData(BLProposal blProposal,Node node) 
    throws Exception{
    	
    	String strDAMAGEDFACTORGRADE  = XMLUtils.getChildNodeValue(node, "DAMAGEDFACTORGRADE");
    	
    	PrpTitemCarExtSchema prpTitemCarExtSchema = new PrpTitemCarExtSchema();
    	
    	prpTitemCarExtSchema.setDamagedFactorGrade(strDAMAGEDFACTORGRADE);
    	if(prpTitemCarExtSchema.getDamagedFactorGrade()==null||"".equals(prpTitemCarExtSchema.getDamagedFactorGrade())){
			prpTitemCarExtSchema.setDamagedFactorGrade("4");
		}
    	
    	



    	prpTitemCarExtSchema.setFuelType( XMLUtils.getChildNodeValue(node,"FUELTYPE"));
    	prpTitemCarExtSchema.setBuyCarDate( XMLUtils.getChildNodeValue(node,"ENROLLDATE").trim());  
    	prpTitemCarExtSchema.setNoneFluctuateFlag( XMLUtils.getChildNodeValue(node,"NEWDEVICEFLAG"));
    	if(prpTitemCarExtSchema.getNoneFluctuateFlag()==null||"".equals(prpTitemCarExtSchema.getNoneFluctuateFlag())){
    		prpTitemCarExtSchema.setNoneFluctuateFlag("0");
    	}
    	
    	
    	
    	
        

    	blProposal.getBLPrpTitemCarExt().setArr(prpTitemCarExtSchema);
    	
    	
    }
  
    
}


