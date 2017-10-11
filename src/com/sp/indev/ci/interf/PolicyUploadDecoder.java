package com.sp.indiv.ci.interf;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.schema.CIInsureValidSchema;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.sysframework.common.util.XMLUtils;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

/**
 * 发送XX查询请求数据的解码器
 * 
 */
public class PolicyUploadDecoder extends PlatFormEncoder{

	/**
	 * 解码
	 * 
	 * @return XX查询请求XML格式串
	 * @throws Exception
	 */
	public void decode(DbPool dbPool, BLPolicy blPolicy, String content)
			throws Exception {
		InputStream in = new ByteArrayInputStream(content.getBytes());
		Document document = XMLUtils.parse(in);
		
		
		int type = 1;
		
		type = processHead(XMLUtils.getChildNodeByPath(document, "/PACKET/HEAD"));
				
		if (type == 1){
			processBody(blPolicy, XMLUtils.getChildNodeByPath(document,"/PACKET/BODY"));
		}
		
		saveCIInsureValid(dbPool, blPolicy);
	}

	/**
	 * 处理BODY节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBody(BLPolicy blPolicy, Node node) throws Exception {
		processBasePart(blPolicy, XMLUtils.getChildNodeByTagName(node,"BASE_PART"));
	}

	/**
	 * 处理BASE_PART节
	 * 
	 * @param node
	 *            Node
	 * @throws Exception
	 */
	protected void processBasePart(BLPolicy blPolicy, Node node)
			throws Exception {
		
		String confirmSequenceNo = XMLUtils.getChildNodeValue(node,"CONFIRM_SEQUENCE_NO");		
		
		CIInsureValidSchema schema = new CIInsureValidSchema();	
		schema.setPolicyNo(blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
		schema.setProposalNo(blPolicy.getBLPrpCmain().getArr(0).getProposalNo());
		schema.setValidNo(confirmSequenceNo);
		schema.setDemandNo(confirmSequenceNo);
		schema.setValidTime(String.valueOf(DateTime.current()));
		schema.setComCode(blPolicy.getBLPrpCmain().getArr(0).getComCode());
		schema.setFlag("01");
		
		blPolicy.getBLCIInsureValid().setArr(schema);
		
	}

	/**
	 * XXXXX存XX确认主信息
	 * 
	 * @param dbPool
	 * @param blpolicy
	 * @throws Exception
	 */
	private void saveCIInsureValid(DbPool dbPool, BLPolicy blPolicy)
			throws Exception {
		BLCIInsureValid blCIInsureValid = blPolicy.getBLCIInsureValid();

		if (dbPool != null) {
			
			blCIInsureValid.save(dbPool);			
		} else {
			DbPool newDbPool = new DbPool();
			try {
				newDbPool.open(SysConfig.getProperty("DDCCDATASOURCE"));
				newDbPool.beginTransaction();
				
				blCIInsureValid.save(newDbPool);				
				newDbPool.commitTransaction();
			} catch (Exception exception) {
				newDbPool.rollbackTransaction();
				exception.printStackTrace();
				throw exception;
			} finally {
				newDbPool.close();
			}
		}
	}
}

