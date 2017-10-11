package com.sp.indiv.ci.interf;

import com.sp.utiall.blsvr.BLPrpDagent;
import com.sp.utiall.blsvr.BLPrpDcompany;

public class SaleChannelHandle {
	/*
	 * @desc ����ҵ����Դ�ж�������ҵ����ֱ��ҵ�� @param blProposalXX��ҵ���� @return �������� @author
	 * zhengxiaoluo 20090615
	 */
	public String returnSaleChannelCode(String iBusinessNature, String iComCode, String iAgentCode) throws Exception {
		String saleChannelCode = "";

		
		
		
		
		String agentBussinessType = "2,3,4,5,9,A,B,F";


		
		if (agentBussinessType.indexOf(iBusinessNature) > -1) {
			BLPrpDagent blPrpDagent = new BLPrpDagent();
			blPrpDagent.query("AgentCode ='"+iAgentCode+"'");
			if(blPrpDagent.getSize()>0){
				saleChannelCode = blPrpDagent.getArr(0).getSaleChannelCode();
			}
		}else{
			BLPrpDcompany blPrpDcompany = new BLPrpDcompany();
			blPrpDcompany.query("validstatus='1' and salechannelcode is not null CONNECT BY NOCYCLE PRIOR uppercomcode = comcode START WITH comcode = '"+iComCode+"'");
			if(blPrpDcompany.getSize()>0){
				saleChannelCode = blPrpDcompany.getArr(0).getSaleChannelCode();
			}
		}
		return saleChannelCode;
	}
}
