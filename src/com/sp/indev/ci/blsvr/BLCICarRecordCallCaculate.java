package com.sp.indiv.ci.blsvr;

import com.sp.prpall.schema.NewCarRecordSchema;
import com.sp.utility.error.UserException;
import com.sp.indiv.bj.NewCarRecordQueryEncoder;
import com.sp.indiv.bj.NewCarRecordQueryDecoder;
import com.sp.indiv.ci.interf.EbaoProxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BLCICarRecordCallCaculate {
	Log logger = LogFactory.getLog(getClass());
	public NewCarRecordSchema query(NewCarRecordSchema newCarRecordSchema) throws UserException, Exception {
		String requestXML = new NewCarRecordQueryEncoder().encode(newCarRecordSchema);
		
		logger.info("新车备案requestXML=="+requestXML);
		
		String comCode = newCarRecordSchema.getComCode();
		String responseXML = EbaoProxy.getInstance().request(requestXML, comCode);
		String responseXMLreplace = responseXML.replaceFirst("GBK", "GB2312");
		
		logger.info("新车备案responseXML=="+responseXMLreplace);
		
		
		new NewCarRecordQueryDecoder().decode(newCarRecordSchema, responseXMLreplace);
		return newCarRecordSchema;
	}
}