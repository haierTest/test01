package com.sp.indiv.ci.interf;

import java.text.DecimalFormat;

import com.sp.indiv.ci.schema.CICarModelSchema;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.string.ChgData;

/**
 * 车型查询 请求的编码器
 * 
 */
public class CICarModelQueryEncoder {

	/**
	 * 编码
	 * 
	 * @return 车型查询请求XML格式串
	 * @throws Exception
	 */
	public String encode(CICarModelSchema ciCarModelSchema, String comcode) throws Exception {
		
		StringBuffer buf = new StringBuffer(4000);
		addXMLHead(buf);
		addPacket(buf, ciCarModelSchema, comcode);
		return buf.toString();
	}

	/**
	 * 添加XML文件头
	 * @param buf StringBuffer
	 */
	protected void addXMLHead(StringBuffer buf) {
		buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
	}

	/**
	 * 添加PACKET节
	 * @param buf StringBuffer
	 * @throws Exception
	 */
	protected void addPacket(StringBuffer buf, CICarModelSchema ciCarModelSchema, String comcode) throws Exception {
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
		addHead(buf, comcode);
		
		addBody(buf, ciCarModelSchema,comcode);
		buf.append("</PACKET>");

	}

	/**
	 * 添加HEAD节
	 * @param buf StringBuffer
	 * @throws Exception
	 */
	protected void addHead(StringBuffer buf, String comcode) throws Exception {
		buf.append("<HEAD>");
		addNode(buf, "REQUEST_TYPE", "39");
		addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + comcode.substring(0, 2) + "_USER"));
		addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + comcode.substring(0, 2) + "_PASSWORD"));
		buf.append("</HEAD>");
	}

	/**
	 * 添加BODY节
	 * @param buf StringBuffer
	 * @throws Exception
	 */
	protected void addBody(StringBuffer buf, CICarModelSchema ciCarModelSchema, String comcode) throws Exception {
		buf.append("<BODY>");
		buf.append("<BASE_PART>");
		addNode(buf, "CAR_MARK", ciCarModelSchema.getCarMark());
		addNode(buf, "VEHICLE_TYPE", ciCarModelSchema.getVehicleType());
		addNode(buf, "ENGINE_NO", ciCarModelSchema.getEngineNo());
		addNode(buf, "RACK_NO", ciCarModelSchema.getRackNo());
		addNode(buf, "NEW_VEHICLE_FLAG", ciCarModelSchema.getNewVehicleFlag());
		addNode(buf, "ECDEMIC_VEHICLE_FLAG", ciCarModelSchema.getEcdemicVehicleFlag());
		addNode(buf, "MADE_FACTORY", ciCarModelSchema.getMadeFactory());
		addNode(buf, "VEHICLE_BRAND", ciCarModelSchema.getVehicleBrand());
		addNode(buf, "VEHICLE_MODEL", ciCarModelSchema.getVenicleModel());
		addNode(buf, "PO_WEIGHT", ciCarModelSchema.getPoWeight());
		addNode(buf, "MADE_DATE", correctDate(ciCarModelSchema.getMadeDate()));
		addNode(buf, "REGISTER_DATE", correctDate(ciCarModelSchema.getRegisterDate()));
		addNode(buf, "VEHICLE_STYLE_DESC", ciCarModelSchema.getVehicleStyleDesc());
		addNode(buf, "LIMIT_LOAD_PERSON", ciCarModelSchema.getLimitLoadPerson());
		
		if(comcode.substring(0,2).equals("01")){
			addNode(buf, "LIMIT_LOAD", new DecimalFormat("0").format(Double.parseDouble(ChgData.chgStrZero(ciCarModelSchema.getLimitLoad()))*1000));
		}else{
			addNode(buf, "LIMIT_LOAD", ciCarModelSchema.getLimitLoad());
		}
		
        
		if(!"".equals(ciCarModelSchema.getExhaustCapacity())){
			
			addNode(buf, "EXHAUST_CAPACITY", ""+new DecimalFormat("0").format(Double.parseDouble(ChgData.chgStrZero(ciCarModelSchema.getExhaustCapacity()))*1000));
		}
		
		if(comcode.substring(0,2).equals("07")){
			addNode(buf, "SALES_CHANNEL_CODE", ciCarModelSchema.getSalesChannelCode());
			
			addNode(buf, "SALES_CHANNEL_REFER_CODE", "");
			
		}
		
		buf.append("</BASE_PART>");
		buf.append("</BODY>");
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
	 * @param dateString 8个字符的日期
	 * @return YYYYMMDD形式的日期
	 */
	private static String correctDate(String dateString) {
		String result = StringUtils.replace(dateString, "-", "");
		return result;
	}

}
