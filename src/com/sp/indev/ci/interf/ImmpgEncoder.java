package com.sp.indiv.ci.interf;

import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.prpall.blsvr.cb.BLPrpCPitemCar;
import com.sp.prpall.blsvr.cb.BLPrpCitemKind;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.prpall.pubfun.PubTools;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.database.DbPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.sp.utility.SysConfig;

public class ImmpgEncoder {

	Log logger = LogFactory.getLog(getClass());
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

	/**
	 * 编码
	 *
	 * @return 直接XX请求XML格式串
	 * @throws Exception
	 */
	public String encode(DbPool dbPool, BLEndorse blEndorse) throws Exception {
		
		StringBuffer buf = new StringBuffer(4000);
		
		addXMLHead(buf);
		addPacket(buf, blEndorse);
		return buf.toString();
	}

	/**
	 * 添加XML文件头
	 *
	 * @param buf
	 *            StringBuffer
	 */
	protected void addXMLHead(StringBuffer buf) throws Exception {
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
	}

	/**
	 * 添加PACKET节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addPacket(StringBuffer buf, BLEndorse blEndorse)
			throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
		addHead(buf, blEndorse);
		addBody(buf, blEndorse);
		buf.append("</PACKET>");

	}

	/**
	 * 添加HEAD节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addHead(StringBuffer buf, BLEndorse blEndorse)
			throws Exception {
		String strComCode = blEndorse.getBLPrpPmain().getArr(0).getComCode();
		
		logger.info("************333********strComCode=" + strComCode);
		

		buf.append("<HEAD>");
		addNode(buf, "REQUEST_TYPE", "09");
		addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_"
				+ strComCode.substring(0, 2) + "_USER"));
		addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_"
				+ strComCode.substring(0, 2) + "_PASSWORD"));
		buf.append("</HEAD>");
	}

	/**
	 * 添加BODY节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBody(StringBuffer buf, BLEndorse blEndorse)
			throws Exception {
		buf.append("<BODY>");
		addBasePart(buf, blEndorse);
		buf.append("</BODY>");
	}

	/**
	 * 添加BASE_PART节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBasePart(StringBuffer buf, BLEndorse blEndorse)
			throws Exception {
		String querySequenceNo = ""; 
		String policyNo = ""; 
		String EndorseNo = ""; 
		String strChgPremium = ""; 
		String strDate = ""; 
		String strValidDate 		= ""; 
		String strEngineNo = ""; 
		String strRackNo = ""; 
		String strCarMark = ""; 
		String strNewVehicleFlag = ""; 
		String strEcdemicVehicleFlag = ""; 
		String strComCode = ""; 
		String strVehicleType = ""; 
		String useNatureCode = ""; 
		String carKindCode = ""; 
		String carKindCodeT = ""; 
		int seatcount = 0;
		double toncount = 0;
		double exhaustScale = 0;
		BLCIInsureValid blciinsureValid = new BLCIInsureValid();
		BLPrpCPitemCar blPrpCPitemCar = new BLPrpCPitemCar();
		BLPrpCitemKind blPrpCitemKind = new BLPrpCitemKind();
		
		policyNo = blEndorse.getBLPrpPmain().getArr(0).getPolicyNo();
		EndorseNo = blEndorse.getBLPrpPmain().getArr(0).getEndorseNo();
		strChgPremium = blEndorse.getBLPrpPmain().getArr(0).getChgPremium();
		
		strDate = this.correctDate(blEndorse.getBLPrpPhead().getArr(0)
				.getUnderWriteEndDate());
		
		blPrpCitemKind.query("policyNo='" + policyNo + "'");
		if (blPrpCitemKind.getSize() > 0) {
		strValidDate 	= blPrpCitemKind.getArr(0).getEndDate();   
		strValidDate 	= strValidDate.substring(0, 4) + strValidDate.substring(5, 7) + strValidDate.substring(8, 10);
		}
		

		blPrpCPitemCar.query("policyNo='" + policyNo + "'");
		if (blPrpCPitemCar.getSize() > 0) {
			useNatureCode = blPrpCPitemCar.getArr(0).getUseNatureCode(); 
			carKindCode = blPrpCPitemCar.getArr(0).getCarKindCode(); 

			seatcount = new Integer(blPrpCPitemCar.getArr(0).getSeatCount())
					.intValue();
			toncount = Double.parseDouble(blPrpCPitemCar.getArr(0)
					.getTonCount());
			exhaustScale = Double.parseDouble(blPrpCPitemCar.getArr(0)
					.getExhaustScale());
			
			logger.info("*****************************原始车辆种类*****carKindCode="
					+ carKindCode);
			logger.info("**************************原始车辆使用性质代码*useNatureCode="
					+ useNatureCode);
			logger.info("**********************************seatcount="
					+ seatcount);
			logger.info("**********************************toncount="
					+ toncount);
			logger.info("**********************************exhaustScale="
					+ exhaustScale);
			
		}
        
		carKindCodeT = encoderVehicleCategory(useNatureCode, seatcount,
				toncount, exhaustScale, carKindCode);
		useNatureCode = encoderUseType(useNatureCode, carKindCode, blEndorse);
		
		logger.info("****************转码后的车辆种类********carKindCodeT="+ carKindCodeT );
		logger.info("****************转码后的车辆使用性质代码***useNatureCode=" + useNatureCode);
		

		blciinsureValid.query("policyNo='" + policyNo + "'");
		if (blciinsureValid.getSize() > 0) {
			querySequenceNo = blciinsureValid.getArr(0).getValidNo(); 
		}
		strEngineNo = blEndorse.getBLPrpPitemCar().getArr(0).getEngineNo();
		strRackNo = blEndorse.getBLPrpPitemCar().getArr(0).getFrameNo();
		strCarMark = blEndorse.getBLPrpPitemCar().getArr(0).getLicenseNo();
		strVehicleType = blEndorse.getBLPrpPitemCar().getArr(0)
				.getLicenseKindCode();
		
		if (SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+strCarMark.trim()+",") > -1 || "".equals(strCarMark.trim())) {
		
			strNewVehicleFlag = "1";
		} else {
			strNewVehicleFlag = "0";
		}

		strComCode = blEndorse.getBLPrpPmain().getArr(0).getComCode();
		if (strComCode.substring(0, 2).equals("07")) {
			if ("沪".equals(strCarMark.trim().substring(0, 1))
					|| "新".equals(strCarMark.trim().substring(0, 1))) {
				strEcdemicVehicleFlag = "0";
			} else {
				strEcdemicVehicleFlag = "1";
			}
		}

		if (strComCode.substring(0, 2).equals("01")) {
			if ("京".equals(strCarMark.substring(0, 1))
					|| "新".equals(strCarMark.trim().substring(0, 1))) {
				strEcdemicVehicleFlag = "0";
			} else {
				strEcdemicVehicleFlag = "1";
			}
		}
		
		if (strComCode.substring(0, 2).equals("19")) {
			if ("浙".equals(strCarMark.substring(0, 1))
					|| "新".equals(strCarMark.trim().substring(0, 1))) {
				strEcdemicVehicleFlag = "0";
			} else {
				strEcdemicVehicleFlag = "1";
			}
		}
		if (strComCode.substring(0, 2).equals("03")) {
			if ("苏".equals(strCarMark.substring(0, 1))
					|| "新".equals(strCarMark.trim().substring(0, 1))) {
				strEcdemicVehicleFlag = "0";
			} else {
				strEcdemicVehicleFlag = "1";
			}
		}
		
		if (strComCode.substring(0, 2).equals("18")) {
			if ("湘".equals(strCarMark.substring(0, 1))
					|| "新".equals(strCarMark.trim().substring(0, 1))) {
				strEcdemicVehicleFlag = "0";
			} else {
				strEcdemicVehicleFlag = "1";
			}
		}
		if (strComCode.substring(0, 2).equals("31")) {
			if ("辽B".equals(strCarMark.substring(0, 1))
					|| "新".equals(strCarMark.trim().substring(0, 1))) {
				strEcdemicVehicleFlag = "0";
			} else {
				strEcdemicVehicleFlag = "1";
			}
		}
		if (strComCode.substring(0, 2).equals("29")) {
			if ("浙B".equals(strCarMark.substring(0, 1))
					|| "新".equals(strCarMark.trim().substring(0, 1))) {
				strEcdemicVehicleFlag = "0";
			} else {
				strEcdemicVehicleFlag = "1";
			}
		}
		

		
		if ("新车".equals(strCarMark.trim())) {
			strCarMark = "";
		}

		
		logger.info("=========获得各个参数xxx***** policyNo=" + policyNo
				+ " EndorseNo=" + EndorseNo +"  strValidDate="+strValidDate+ " strChgPremium=" + strChgPremium
				+ " strDate=" + strDate + " querySequenceNo=" + querySequenceNo
				+ " strEngineNo=" + strEngineNo);
		
		
		buf.append("<BASE_PART>");
		addNode(buf, "CONFIRM_SEQUENCE_NO", querySequenceNo);
		addNode(buf, "AMEND_CONFIRM_NO", "");
		
		 
		
		if(strComCode.substring(0,2).equals("01")    || 
           strComCode.substring(0,2).equals("07"))
		{
			addNode(buf, "EFFECTIVE_DATE", strDate); 	
		}
		else 
		{
			
			addNode(buf, "EFFECTIVE_DATE", strDate + "24"); 	
		}
		
		addNode(buf, "USE_TYPE", useNatureCode);
		addNode(buf, "VEHICLE_CATEGORY", carKindCodeT);
		addNode(buf, "CAR_MARK", strCarMark);
		addNode(buf, "VEHICLE_TYPE", strVehicleType);
		addNode(buf, "ENGINE_NO", strEngineNo);
		addNode(buf, "RACK_NO", strRackNo);
		addNode(buf, "OWNER_NAME", "");
		addNode(buf, "CERTI_TYPE", "");
		addNode(buf, "CERTI_CODE", "");
		addNode(buf, "AMEND_DRIVER", "");
		addNode(buf, "DRIVER_NUM", "0");
		addNode(buf, "ACTUAL_AMEND_PREMIUM", strChgPremium);
		addNode(buf, "REASON", "");
		addNode(buf, "CHANGE_REASON_CODE", "");
		addNode(buf, "CHANGE_RENSON_DESC", "");
		addNode(buf, "ENDOR_TIME", strDate);
		addNode(buf, "AMEND_POLICY_NO", EndorseNo);
		buf.append("</BASE_PART>");

	}

	public static void addNode(StringBuffer buffer, String name, String value) {
		value = StringUtils.replace(value, "<", "&lt;");
		value = StringUtils.replace(value, ">", "&gt;");
		value = StringUtils.replace(value, "&", "&amp;");
		value = StringUtils.replace(value, "'", "&apos;");
		value = StringUtils.replace(value, "\"", "&quot;");

		buffer.append("<");
		buffer.append(name);
		buffer.append(">");
		buffer.append(value);
		buffer.append("</");
		buffer.append(name);
		buffer.append(">");
		buffer.append("\r\n");
	}

	/**
	 * 纠正日期格式
	 *
	 * @param dateString
	 *            8个字符的日期
	 * @return YYYYMMDD形式的日期
	 */
	private String correctDate(String dateString) {
		String result = StringUtils.replace(dateString, "-", "");
		return result;
	}

	/**
	 * 纠正日期时间格式
	 *
	 * @param dateString
	 *            修正前的日期时间
	 * @return 修正后的日期时间
	 */
	private String correctDateTime(String dateString) {
		String result = correctDate(dateString);
		result = StringUtils.replace(dateString, " ", "");
		result = StringUtils.replace(dateString, ":", "");
		return result;
	}

	
	private String encoderUseType(String useNatureCode, String strCarKindCode,
			BLEndorse blEndorse) throws Exception {
		String strUseType = "";
		String intUseType = "";
		intUseType = useNatureCode;
		
		if (strCarKindCode.equals("A0")) {
			if (intUseType.trim().equals("8B")) 
			{
				strUseType = "220";
			} else if (intUseType.trim().equals("8C")) 
			{
				strUseType = "230";
			} else if (intUseType.trim().equals("8A")) 
			{
				strUseType = "210";
			} else if (intUseType.trim().equals("9B")) 
			{
				strUseType = "102";
			} else if (intUseType.trim().equals("9C")) 
			{
				strUseType = "103";
			} else if (intUseType.trim().equals("9A")) 
			{
				strUseType = "101";
			} else if (intUseType.trim().equals("86")) 
			{
				strUseType = "100";
			} else if (intUseType.trim().equals("87")) 
			{
				strUseType = "101";
			} else if (intUseType.trim().equals("9D")) 
			{
				
				
			} else if (intUseType.trim().equals("9E")) 
			{
				
				if (PubTools.compareDate(blEndorse.getBLPrpPmain().getArr(0)
						.getOperateDate().trim(), "2007-04-13") < 0) {
					strUseType = "102"; 
				} else {
					strUseType = "103"; 
				}
			}
		}
		
		else if (strCarKindCode.equals("H0") || 
				 strCarKindCode.equals("H1") ||
				 strCarKindCode.equals("H2") ||
				 strCarKindCode.equals("G0")) {
			if (intUseType.trim().equals("86") || 
					intUseType.trim().equals("87") || 
					intUseType.trim().equals("9D") || 
					intUseType.trim().equals("9E") || 
					intUseType.trim().equals("9A") || 
					intUseType.trim().equals("9B") || 
					intUseType.trim().equals("9C") 
			) {
				strUseType = "100"; 
			} else if (
			intUseType.trim().equals("8B") || 
					intUseType.trim().equals("8C") || 
					intUseType.trim().equals("8D") 

			) {
				strUseType = "200"; 
			}

		}
		
		else {
			strUseType = "000";
		}

		return strUseType;
	}

	private String encoderVehicleCategory(String intUseType, int seatcount,
			double toncount, double exhaustScale, String strCarKindCode)
			throws Exception {
		String strVehicleCategory = "";

		if (strCarKindCode.equals("A0")) {
			if (seatcount >= 0 && seatcount < 6) {
				strVehicleCategory = "11";
			} else if (seatcount >= 6 && seatcount < 10) {
				strVehicleCategory = "12";
			} else if (seatcount >= 10 && seatcount < 20) {
				strVehicleCategory = "13";
			} else if (seatcount >= 20 && seatcount < 36) {
				strVehicleCategory = "14";
			} else if (seatcount >= 36) {
				strVehicleCategory = "15";
			}

		} else if (strCarKindCode.equals("H0") ||
				   strCarKindCode.equals("H1") ||
				   strCarKindCode.equals("H2")) 
		{
			if (toncount >= 0 && toncount < 2) {
				strVehicleCategory = "21";
			} else if (toncount >= 2 && toncount < 5) {
				strVehicleCategory = "22";
			} else if (toncount >= 5 && toncount < 10) {
				strVehicleCategory = "23";
			} else if (toncount >= 10) {
				strVehicleCategory = "24";
			}

		} else if (strCarKindCode.equals("G0")) 
		{
			if (toncount >= 0 && toncount < 2) {
				strVehicleCategory = "25";
			} else if (toncount >= 2 && toncount < 5) {
				strVehicleCategory = "26";
			} else if (toncount >= 5 && toncount < 10) {
				strVehicleCategory = "27";
			} else if (toncount >= 10) {
				strVehicleCategory = "28";
			}
		} else if (strCarKindCode.equals("TP") || strCarKindCode.equals("TQ")
				|| strCarKindCode.equals("TR") || strCarKindCode.equals("TS")) {
			
			strVehicleCategory = "30";
		} else if (strCarKindCode.equals("T1") || strCarKindCode.equals("T2")
				|| strCarKindCode.equals("T3") || strCarKindCode.equals("T5")
				|| strCarKindCode.equals("TE") || strCarKindCode.equals("TF")
				|| strCarKindCode.equals("TG") || strCarKindCode.equals("TH")
				|| strCarKindCode.equals("TI") || strCarKindCode.equals("TJ")
				|| strCarKindCode.equals("TK") || strCarKindCode.equals("TL")
				|| strCarKindCode.equals("TM") || strCarKindCode.equals("TN")
				|| strCarKindCode.equals("TD") || strCarKindCode.equals("T7")) {
			
			
			strVehicleCategory = "40";
		} else if (strCarKindCode.equals("T4") || strCarKindCode.equals("T6")
				|| strCarKindCode.equals("T8") || strCarKindCode.equals("T9")
				|| strCarKindCode.equals("TO") || strCarKindCode.equals("TU")
				|| strCarKindCode.equals("TV") || strCarKindCode.equals("TW")
				|| strCarKindCode.equals("TX") || strCarKindCode.equals("TY")
				|| strCarKindCode.equals("TZ") || strCarKindCode.equals("TC")) {
			
			
			strVehicleCategory = "50";
		} else if (strCarKindCode.equals("TT")) {
			
			strVehicleCategory = "60";
		} else if (strCarKindCode.equals("M0") || strCarKindCode.equals("M1")
				|| strCarKindCode.equals("M3") || strCarKindCode.equals("M4")) {
			
			if (exhaustScale >= 0 && exhaustScale <= 0.05) {
				strVehicleCategory = "71";
			} else if (exhaustScale > 0.05 && exhaustScale <= 0.25) {
				strVehicleCategory = "72";
			} else if (exhaustScale > 0.25) {
				strVehicleCategory = "73";
			}
		} else if (strCarKindCode.equals("M2")) {
			
			strVehicleCategory = "73";
		} else if ((strCarKindCode.equals("J0") || strCarKindCode.equals("J1")
				|| strCarKindCode.equals("J2") || strCarKindCode.equals("J3"))
				&& intUseType.trim().equals("7A")) {
			
			
			if (exhaustScale >= 0 && exhaustScale <= 14.7) {
				strVehicleCategory = "81";
			} else if (exhaustScale > 14.7) {
				strVehicleCategory = "82";
			}
		} else if ((strCarKindCode.equals("J0") || strCarKindCode.equals("J1")
				|| strCarKindCode.equals("J2") || strCarKindCode.equals("J3"))
				&& intUseType.trim().equals("7B")) {
			
			if (exhaustScale >= 0 && exhaustScale <= 14.7) {
				strVehicleCategory = "91";
			} else if (exhaustScale > 14.7) {
				strVehicleCategory = "92";
			}
		} else if (strCarKindCode.equals("G1")) 
		{
			strVehicleCategory = "31";
		}

		return strVehicleCategory;
	}

}
