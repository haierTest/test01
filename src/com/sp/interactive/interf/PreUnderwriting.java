package com.sp.interactive.interf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.dbsvr.tb.DBPrpTitemCarExt;
import com.sp.prpall.schema.ProposalSchema;
import com.sp.prpall.schema.PrpIntefInfoSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sunshine.ruleapp.client.RuleResultClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.firsttech.model.RuleInfo;
import com.firsttech.model.CheckSaveResult;


/**
 * ����XX�ɹ�֮�󣬽�������XX���������Ԥ��XXXXX�ӿ�
 * @author qilin
 * */

public class PreUnderwriting{
	Log logger = LogFactory.getLog(getClass());
	
		public String getRuleResult(BLProposal blProposal) throws Exception{
			
			Map mapResult = null;
			RuleInfo[] ruleInfos=null;
			CheckSaveResult result= null;
			List resultInfos = new ArrayList();
			String errorMessage = "";
			try {
				
				ProposalSchema proposalSchema = new ProposalSchema();
				proposalSchema .proposalToSchema(blProposal);
				
				
				
				
				RuleResultClient ruleResultClient = new RuleResultClient();
				
				mapResult = ruleResultClient.getRuleResult(proposalSchema,"carProSave");
				
				if (mapResult == null) {
					throw new Exception("���������쳣�����ؿ�ֵ");
				} else{
            		
            		if(new UtiPower().checkChannelFirewall(proposalSchema.getPrpTmain().getComCode())
            				&&!((Boolean)(mapResult.get("isRenewalValid"))).booleanValue()){
            			resultInfos = (ArrayList)mapResult.get("renewalInfo");
            		}else{
            			resultInfos = (ArrayList)mapResult.get("ruleInfos");
            		}
            		
            		String OperateSite = blProposal.getBLPrpTmain().getArr(0).getOperateSite();
            		
            		 if(SysConfig.getProperty("App_OperateSite").indexOf(","+OperateSite+",")>-1){
            			String strEcdemicVehicleKind = (String)mapResult.get("licenseInfo");
                 		String strPayRateCarKind = (String)mapResult.get("carKindOfFinalBusiness");
                 		String strProposalNo = blProposal.getBLPrpTitemCarExt().getArr(0).getProposalNo();
                 		String strItemNo = blProposal.getBLPrpTitemCarExt().getArr(0).getItemNo();
                 		DBPrpTitemCarExt dbPrpTitemCarExt = new DBPrpTitemCarExt();
                 		dbPrpTitemCarExt.getInfo(strProposalNo, strItemNo);
                 		dbPrpTitemCarExt.setEcdemicVehicleKind(strEcdemicVehicleKind); 
                 		dbPrpTitemCarExt.setPayRateCarKind(strPayRateCarKind);
                 		dbPrpTitemCarExt.update();



            		 }
            		
            		
            		
            		
            	}
            	if(mapResult!=null && "-1".equals(mapResult.get("retCode"))){
        	       throw new Exception("���������쳣��ͨ�Ŵ���");
            	}
            	if(result!=null){
    				ruleInfos =  result.getRuleInfos();
    				errorMessage = "XXXXX�����:"+ruleInfos;
    			}
    			if(resultInfos!=null && resultInfos.size()>0){
    				errorMessage = "XXXXX�����:"+resultInfos;
    			}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("���������쳣��" + e.getMessage());
			}
			return errorMessage;
		}
	
}
