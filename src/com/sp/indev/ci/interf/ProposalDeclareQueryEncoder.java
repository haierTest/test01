package com.sp.indiv.ci.interf;

import org.apache.log4j.Logger;
import com.sp.indiv.ci.blsvr.BLCIMotorcadeDeclare;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;


/** 
*   发送团车业务申报查询请求数据的编码器
*/
public class ProposalDeclareQueryEncoder{

	
	private static Logger logger = Logger.getLogger(ProposalDeclareQueryEncoder.class);
	
    /**
	 * 
	 *
	 * @return 申报查询请求XML格式串
	 * @throws Exception
	 */
    public String encode(BLCIMotorcadeDeclare  blCIMotorcadeDeclareSend) 
    	throws Exception 
    {    	
    	
        StringBuffer buf = new StringBuffer(4000);
        addXMLHead(buf);
        addPacket(buf, blCIMotorcadeDeclareSend);
        
        logger.info("[上海团车申报发送报文]:"+buf.toString());
       
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
    protected void addPacket(StringBuffer buf, BLCIMotorcadeDeclare  blCIMotorcadeDeclareSend) 
    	throws Exception 
    {
    	buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
        addHead(buf, blCIMotorcadeDeclareSend);
        addGroupData(buf, blCIMotorcadeDeclareSend);
        buf.append("</PACKET>");
    }
    /**
	 * 添加HEAD节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addHead(StringBuffer buf, BLCIMotorcadeDeclare  blCIMotorcadeDeclareSend) 
    	throws Exception 
    {
        String strComCode = blCIMotorcadeDeclareSend.getArr(0).getComCode();
    	buf.append("<HEAD>");
        addNode(buf, "REQUEST_TYPE", "42");
        addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_USER"));
        addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_PASSWORD"));
        buf.append("</HEAD>");
    }	
    
    /**
	 * 添加GroupData节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addGroupData(StringBuffer buf, BLCIMotorcadeDeclare  blCIMotorcadeDeclareSend) 
    	throws Exception 
    {
        buf.append("<GROUP_DATA>");
        addSingleData(buf, blCIMotorcadeDeclareSend);
        buf.append("</GROUP_DATA>");
    }
    
    /**
	 * 添加SingleData节
	 *
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
    protected void addSingleData(StringBuffer buf,BLCIMotorcadeDeclare  blCIMotorcadeDeclareSend) throws Exception {
    	int size=blCIMotorcadeDeclareSend.getSize();
    	for (int i = 0; i < size; i++) {
    		buf.append("<SINGLE_DATA>");
    		addNode(buf, "SINGLE_NO",String.valueOf((i+1)));
    		buf.append("<BODY>");
    		
    		
    		
    		if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+blCIMotorcadeDeclareSend.getArr(i).getCarMark()+",") > -1){
    		
    			addNode(buf, "CAR_MARK",     "");
    		}else{
    			addNode(buf, "CAR_MARK",     blCIMotorcadeDeclareSend.getArr(i).getCarMark());
    		}
    		
    		addNode(buf, "ENGINE_NO",    blCIMotorcadeDeclareSend.getArr(i).getEngineNo());
    		addNode(buf, "RACK_NO",      blCIMotorcadeDeclareSend.getArr(i).getRackNo());
    		addNode(buf, "OWNER",        blCIMotorcadeDeclareSend.getArr(i).getOwner());
    		addNode(buf, "POLICY_HOLDER",blCIMotorcadeDeclareSend.getArr(i).getPolicyHolder());
    		addNode(buf, "INSURED_NAME", blCIMotorcadeDeclareSend.getArr(i).getInsuredName());
    		
    		addNode(buf, "OPERATION_FLAG", blCIMotorcadeDeclareSend.getArr(i).getOperationFlag());
    		
			
    		addNode(buf, "GROUP_CODE", blCIMotorcadeDeclareSend.getArr(i).getGroupCode());
			
    		buf.append("</BODY>");
    		buf.append("</SINGLE_DATA>");
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
