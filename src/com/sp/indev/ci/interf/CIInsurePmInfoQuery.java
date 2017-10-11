package com.sp.indiv.ci.interf;

import com.sp.prpall.blsvr.tb.BLProposal;

public class CIInsurePmInfoQuery {

	public void queryVehicleInfo(BLProposal blProposal, String comCode, String carMark, String FrameNo, String operateSite) throws Exception{
        String strRequestXML = "";
        CIInsurePmVehicleEncoder ciInsurePmVehicleEncoder = new CIInsurePmVehicleEncoder();
        strRequestXML = ciInsurePmVehicleEncoder.encode(carMark, FrameNo.substring(FrameNo.length()-6), comCode);

        String strResponseXML = EbaoProxy.getInstance().request(strRequestXML, comCode);
        CIInsurePmVehicleDecoder ciInsurePmVehicleDecoder = new CIInsurePmVehicleDecoder();
        ciInsurePmVehicleDecoder.decode(blProposal,strResponseXML,operateSite);
	}

	public void queryDriverInfo(BLProposal blProposal, String comCode, String driverLicense, String operateSite) throws Exception{
        String strRequestXML = "";
        CIInsurePmDriverEncoder ciInsurePmDriverEncoder = new CIInsurePmDriverEncoder();
        strRequestXML = ciInsurePmDriverEncoder.encode(comCode, driverLicense);

        String strResponseXML = EbaoProxy.getInstance().biRequest(strRequestXML, comCode);
        CIInsurePmDriverDecoder ciInsurePmDriverDecoder = new CIInsurePmDriverDecoder();
        ciInsurePmDriverDecoder.decode(blProposal,strResponseXML,operateSite);
	}
	public void queryVehicleInfoBI(BLProposal blProposal, String comCode, String carMark, String FrameNo, String operateSite) throws Exception{
        String strRequestXML = "";
        BICIInsurePmVehicleEncoder biciInsurePmVehicleEncoder = new BICIInsurePmVehicleEncoder();
        strRequestXML = biciInsurePmVehicleEncoder.encode(carMark, FrameNo.substring(FrameNo.length()-6), comCode);

        String strResponseXML = EbaoProxy.getInstance().biRequest(strRequestXML, comCode);
        BIInsurePmVehicleDecoder biInsurePmVehicleDecoder = new BIInsurePmVehicleDecoder();
        biInsurePmVehicleDecoder.decode(blProposal,strResponseXML,operateSite);
	}

}
