package com.sp.indiv.ci.interf;

import org.apache.log4j.Logger;

import com.sp.indiv.ci.blsvr.BLCIMotorcadeDeclare;
import com.sp.indiv.ci.schema.CIMotorcadeDeclareSchema;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;

public class MotorcadeDeclareInfoQueryEncoder {



		private static Logger logger = Logger.getLogger(MotorcadeDeclareInfoQueryEncoder.class);

	    /**
		 * 
		 *
		 * @return 申报查询请求XML格式串
		 * @throws Exception
		 */
	    public String encode(BLCIMotorcadeDeclare  blCIMotorcadeDeclare) 
	    	throws Exception {    	
	    	
	    	
	        StringBuffer buf = new StringBuffer(4000);
	        addXMLHead(buf);
	        addPacket(buf, blCIMotorcadeDeclare);
	        logger.info("[上海团车申报信息查询发送报文]:"+buf.toString());
	        return buf.toString();
	    }
		
	    /**
		 * 添加XML文件头
		 *
		 * @param buf
		 *            StringBuffer
		 */
	    protected void addXMLHead(StringBuffer buf) 
	    	throws Exception
	    {
	        buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
	    }	
	    
	    /**
		 * 添加PACKET节
		 *
		 * @param buf
		 *            StringBuffer
		 * @throws Exception
		 */
	    protected void addPacket(StringBuffer buf, BLCIMotorcadeDeclare  blCIMotorcadeDeclare) 
	    	throws Exception {
	    	
	    	buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
	        addHead(buf, blCIMotorcadeDeclare);
	        addBody(buf, blCIMotorcadeDeclare);
	        buf.append("</PACKET>");
	    }
	    /**
		 * 添加HEAD节
		 *
		 * @param buf
		 *            StringBuffer
		 * @throws Exception
		 */
	    protected void addHead(StringBuffer buf, BLCIMotorcadeDeclare  blCIMotorcadeDeclare) 
	    	throws Exception {
	        String strComCode = blCIMotorcadeDeclare.getArr(0).getComCode();
	    	buf.append("<HEAD>");
	        addNode(buf, "REQUEST_TYPE", "43");
	        addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_USER"));
	        addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_PASSWORD"));
	        buf.append("</HEAD>");
	    }	
	    
	    protected void addBody(StringBuffer buf, BLCIMotorcadeDeclare  blCIMotorcadeDeclare) throws Exception {
	    	if(blCIMotorcadeDeclare.getSize()>0){
	    		CIMotorcadeDeclareSchema ciMotorcadeDeclareSchema = blCIMotorcadeDeclare.getArr(0);
		    	buf.append("<BODY>");
		    	addNode(buf, "GROUP_CODE",     ciMotorcadeDeclareSchema.getGroupCode());
		    	
	    		
		    	if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+ciMotorcadeDeclareSchema.getCarMark()+",") > -1){
		    	
		    		addNode(buf, "CAR_MARK",     "");
	    		}else{
	    			addNode(buf, "CAR_MARK",     ciMotorcadeDeclareSchema.getCarMark());
	    		}
	    		addNode(buf, "RACK_NO",        ciMotorcadeDeclareSchema.getRackNo());
	    		addNode(buf, "OWNER",          ciMotorcadeDeclareSchema.getOwner());
	    		addNode(buf, "POLICY_HOLDER",  ciMotorcadeDeclareSchema.getPolicyHolder());
	    		addNode(buf, "INSURED_NAME",   ciMotorcadeDeclareSchema.getInsuredName());

	    		buf.append("</BODY>");
	    	}else{
	    		throw new Exception("查询的团车条件不能为空！");
	    	}
		}
	    
	    
	    public static void addNode(StringBuffer buffer, String name, String value) {
	        value = StringUtils.replace(value, "<", "&lt;");
	        value = StringUtils.replace(value, ">", "&gt;");
	        value = StringUtils.replace(value, "&", "&amp;");
	        value = StringUtils.replace(value, "\'", "&apos;");
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

}
