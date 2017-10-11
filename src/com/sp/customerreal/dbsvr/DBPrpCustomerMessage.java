package com.sp.customerreal.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.blsvr.BLPrpCustomerMessageMobile;
import com.sp.customerreal.schema.PrpCustomerMessageMobileSchema;
import com.sp.customerreal.schema.PrpCustomerMessageSchema;

import com.sp.sysframework.common.datatype.DateTime;
import com.sp.taskmng.util.TaskMngUtil;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

public class DBPrpCustomerMessage {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 *
	 * ȡXX�µ��Ÿ�������
	 *
	 * @param dbpool
	 * @return
	 * @throws Exception 
	 */
	public List findPrpCustomerMessageInfo(DbPool dbpool,Map map) throws Exception
	{
		List list = new ArrayList();
		
		
		String PrintDBPool = "0";
		try{
			PrintDBPool = SysConfig.getProperty("CustomerRealQueryDBPool");
			PrintDBPool="0";
		}catch(Exception e){
			PrintDBPool="0";
		}
		if("0".equals(PrintDBPool)){
		String message = "";
		String name = "";
		String licenseNo = "";
		
		String groupFlag = "";
		String GXCondition = "0";
		
		String sql = "select distinct PrpPrintPool.*, PRPCMAIN.CHANNELTYPE,prpcitemcar.carkindcode," +
				"prpcitemcar.brandName,prpCmain.othflag,prpcitemcar.usenaturecode,prpintefinfo.lastClaimCount " +
	     "from PrpPrintPool,PRPCMAIN,prpcitemcar,prpcinsured,prpintefinfo " +
	     "where PrpPrintPool.createddate between to_date('"+map.get("startTime")+
		 "','YYYY-MM-DD HH24:MI:SS') " +
			 "and to_date('" + map.get("endTime") + 
			 "','YYYY-MM-DD HH24:MI:SS') and PrpPrintPool.printstatus='1' and PrpPrintPool.classcode='05' " +
			 "and (PrpPrintPool.printtype='P' " +
			        "or (PrpPrintPool.printtype='E' " +
			        	"and PrpPrintPool.Comcode like '17%' " +
			        	"and PrpPrintPool.IsEndorMobile = '1' " +
			        	"and PrpPrintPool.Businessnature in('2', '3', '4', '9', 'A', 'G', 'H', 'M', 'N', 'P')  " +
			        	"and prpcinsured.INSUREDFLAG = '1' and prpcinsured.insuredtype ='1'" +
			        	"and prpcitemcar.carkindcode not like 'M%' and prpcitemcar.carkindcode not like 'J%'))  " +
			 "and PrpPrintPool.POLICYNO = prpcitemcar.policyno " +
			 "and PrpPrintPool.POLICYNO = PRPCMAIN.POLICYNO  " +
			 "and PrpPrintPool.POLICYNO=prpcinsured.POLICYNO " +
			 "and PrpPrintPool.POLICYNO = prpintefinfo.policyno(+) " ;
	     			
		logger.info("ȡ��sql:(" + sql);
		logger.info("startTime="+map.get("startTime"));
		logger.info("endTime="+map.get("endTime"));
		logger.info("ȡXX����sql��"+sql);
		ResultSet resultSet = dbpool.query(sql);
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		
		while(resultSet.next())
		{
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setBusinessNature(dbpool.getString(resultSet,"BUSINESSNATURE"));
			prpCustomerMessageSchema.setClassCode(dbpool.getString(resultSet,"CLASSCODE"));
			prpCustomerMessageSchema.setCreatedCode("admin");
			prpCustomerMessageSchema.setCustomerName(dbpool.getString(resultSet,"INSUREDNAME"));
			prpCustomerMessageSchema.setMakCom(dbpool.getString(resultSet,"MAKECOM"));
			prpCustomerMessageSchema.setMessageType("1");
			prpCustomerMessageSchema.setPolicyNo(dbpool.getString(resultSet,"POLICYNO"));
			prpCustomerMessageSchema.setRiskCode(dbpool.getString(resultSet,"RISKCODE"));
			prpCustomerMessageSchema.setUpdateCode("admin");
			
			prpCustomerMessageSchema.setBusinessNo(dbpool.getString(resultSet,"businessNo"));
			prpCustomerMessageSchema.setPrintType(dbpool.getString(resultSet,"printType"));
			prpCustomerMessageSchema.setCarKindCode(dbpool.getString(resultSet,"carKindCode"));
			prpCustomerMessageSchema.setBrandName(dbpool.getString(resultSet,"brandName"));
			
			
			prpCustomerMessageSchema.setOthflag(dbpool.getString(resultSet,"othflag"));
			prpCustomerMessageSchema.setUsenaturecode(dbpool.getString(resultSet,"usenaturecode"));
			prpCustomerMessageSchema.setLastClaimCount(dbpool.getString(resultSet,"lastClaimCount"));
			
			GXCondition= getGXCondition(dbpool,dbpool.getString(resultSet,"POLICYNO"),dbpool.getString(resultSet,"BusinessNO"));
			prpCustomerMessageSchema.setGXCondition(GXCondition);
			
			groupFlag = getGroupFlag(dbpool,dbpool.getString(resultSet,"POLICYNO"));
			
			if("".equals(groupFlag)){
				groupFlag = "0";
			}
			
			prpCustomerMessageSchema.setGroupFlag(groupFlag);
			
			prpCustomerMessageSchema.setUnderwriteEndDate(""+dbpool.getDateTime(resultSet,"UnderwriteEndDate"));
			prpCustomerMessageSchema.setLicenseNo(dbpool.getString(resultSet,"LICENSENO"));
			prpCustomerMessageSchema.setEngineNo(dbpool.getString(resultSet,"engineNo"));
			Map customerMap = new HashMap();
			
			customerMap = getInfo(groupFlag,dbpool.getString(resultSet,"POLICYNO"));
			
			Map CustomerFromPrpCinsuredMap = new HashMap();
			CustomerFromPrpCinsuredMap = getCustomerInfoFromPrpCinsured(dbpool, groupFlag, dbpool.getString(resultSet,"POLICYNO"));
			
			if("0".equals(groupFlag))
			{   
				if(CustomerFromPrpCinsuredMap.get("mobile")==null || "".equals(CustomerFromPrpCinsuredMap.get("mobile"))){
				    prpCustomerMessageSchema.setMobile((String)customerMap.get("mobile"));
				}else{
					prpCustomerMessageSchema.setMobile((String)CustomerFromPrpCinsuredMap.get("mobile"));
				}
				if(CustomerFromPrpCinsuredMap.get("customerCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCode"))){
					prpCustomerMessageSchema.setCustomerCode((String)customerMap.get("customerCode"));
				}else{
					prpCustomerMessageSchema.setCustomerCode((String)CustomerFromPrpCinsuredMap.get("customerCode"));
				}
				if(CustomerFromPrpCinsuredMap.get("customerCname")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCname"))){
					prpCustomerMessageSchema.setCustomerName((String)customerMap.get("customerCname"));
				}else{
					prpCustomerMessageSchema.setCustomerName((String)CustomerFromPrpCinsuredMap.get("customerCname"));
				}
				if(CustomerFromPrpCinsuredMap.get("sex")==null || "".equals(CustomerFromPrpCinsuredMap.get("sex"))){
					prpCustomerMessageSchema.setSex((String)customerMap.get("sex"));
				}else{
					prpCustomerMessageSchema.setSex((String)CustomerFromPrpCinsuredMap.get("sex"));
				}
				if(CustomerFromPrpCinsuredMap.get("birthDate")==null || "".equals(CustomerFromPrpCinsuredMap.get("birthDate"))){
					prpCustomerMessageSchema.setBirthDate((String)customerMap.get("birthDate"));
				}else{
					prpCustomerMessageSchema.setBirthDate((String)CustomerFromPrpCinsuredMap.get("birthDate"));
				}
				if(CustomerFromPrpCinsuredMap.get("identifyType")==null || "".equals(CustomerFromPrpCinsuredMap.get("identifyType"))){
					prpCustomerMessageSchema.setIdentifyType((String)customerMap.get("identifyType"));
				}else{
					prpCustomerMessageSchema.setIdentifyType((String)CustomerFromPrpCinsuredMap.get("identifyType"));
				}
				if(CustomerFromPrpCinsuredMap.get("identifyNumber")==null || "".equals(CustomerFromPrpCinsuredMap.get("identifyNumber"))){
					prpCustomerMessageSchema.setIdentifyNumber((String)customerMap.get("identifyNumber"));
				}else{
					prpCustomerMessageSchema.setIdentifyNumber((String)CustomerFromPrpCinsuredMap.get("identifyNumber"));
				}
				if(CustomerFromPrpCinsuredMap.get("occupationCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("occupationCode"))){
					prpCustomerMessageSchema.setOccupationName((String)customerMap.get("occupationCode"));
				}else{
					prpCustomerMessageSchema.setOccupationName((String)CustomerFromPrpCinsuredMap.get("occupationCode"));
				}
				name = "��";
				licenseNo = prpCustomerMessageSchema.getLicenseNo();
			}
			
			if("1".equals(groupFlag))
			{
				if(CustomerFromPrpCinsuredMap.get("linkName")==null || "".equals(CustomerFromPrpCinsuredMap.get("linkName"))){
					prpCustomerMessageSchema.setLinkName((String)customerMap.get("linkName"));
				}else{
					prpCustomerMessageSchema.setLinkName((String)CustomerFromPrpCinsuredMap.get("linkName"));
				}
				if(CustomerFromPrpCinsuredMap.get("mobile")==null || "".equals(CustomerFromPrpCinsuredMap.get("mobile"))){
					prpCustomerMessageSchema.setMobile((String)customerMap.get("mobile"));
				}else{
					prpCustomerMessageSchema.setMobile((String)CustomerFromPrpCinsuredMap.get("mobile"));
				}
				if(CustomerFromPrpCinsuredMap.get("linkPhone")==null || "".equals(CustomerFromPrpCinsuredMap.get("linkPhone"))){
					prpCustomerMessageSchema.setPhone((String)customerMap.get("linkPhone"));
				}else{
					prpCustomerMessageSchema.setPhone((String)CustomerFromPrpCinsuredMap.get("linkPhone"));
				}
				if(CustomerFromPrpCinsuredMap.get("customerCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCode"))){
					prpCustomerMessageSchema.setCustomerCode((String)customerMap.get("customerCode"));
				}else{
					prpCustomerMessageSchema.setCustomerCode((String)CustomerFromPrpCinsuredMap.get("customerCode"));
				}
				if(CustomerFromPrpCinsuredMap.get("customerCname")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCname"))){
					prpCustomerMessageSchema.setCustomerName((String)customerMap.get("customerCname"));
				}else{
					prpCustomerMessageSchema.setCustomerName((String)CustomerFromPrpCinsuredMap.get("customerCname"));
				}
				if(CustomerFromPrpCinsuredMap.get("organizeCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("organizeCode"))){
					prpCustomerMessageSchema.setOrganizeCode((String)customerMap.get("organizeCode"));
				}else{
					prpCustomerMessageSchema.setOrganizeCode((String)CustomerFromPrpCinsuredMap.get("organizeCode"));
				}
				if(CustomerFromPrpCinsuredMap.get("addressCname")==null || "".equals(CustomerFromPrpCinsuredMap.get("addressCname"))){
					prpCustomerMessageSchema.setAddress((String)customerMap.get("addressCname"));
				}else{
					prpCustomerMessageSchema.setAddress((String)CustomerFromPrpCinsuredMap.get("addressCname"));
				}
				name = "��λ";
				licenseNo = "";
			}
			






			
			
			String comCode = "";
			comCode = dbpool.getString(resultSet,"COMCODE");
			prpCustomerMessageSchema.setComCode(comCode);
			
			if(prpCustomerMessageSchema.getCustomerName()==null ||"".equals(prpCustomerMessageSchema.getCustomerName()))
			{
				
				prpCustomerMessageSchema.setCustomerName("��");
			}
			if(prpCustomerMessageSchema.getMobile()==null ||"".equals(prpCustomerMessageSchema.getMobile()))
			{
				
				prpCustomerMessageSchema.setMobile("��");
				
				
				prpCustomerMessageSchema.setMessageFlag("3");
				
				prpCustomerMessageSchema.setMessageFlag1("3");
			}
			
			if(((String)prpCustomerMessageSchema.getMobile()).length()!=11  ||  "0".equals(((String)prpCustomerMessageSchema.getMobile()).substring(0,1)))
			{
				
				
				prpCustomerMessageSchema.setMessageFlag("3");
				
				prpCustomerMessageSchema.setMessageFlag1("3");		
			}
			else 
			{
				
				prpCustomerMessageSchema.setMessageFlag("-2");
				
				
				if(prpCustomerMessageSchema.getGXCondition()!=null&&!"".equals(prpCustomerMessageSchema.getGXCondition())){
					if ("1".equals(prpCustomerMessageSchema.getGXCondition())) {
				    	prpCustomerMessageSchema.setMessageFlag("1");
				    	prpCustomerMessageSchema.setPhoneVisitFlag("1");
					}
				}
				
				prpCustomerMessageSchema.setMessageFlag1("-9");
				if(prpCustomerMessageSchema.getComCode()!=null&&!"".equals(prpCustomerMessageSchema.getComCode())){
					if (prpCustomerMessageSchema.getComCode().startsWith("0651")) {
				    	prpCustomerMessageSchema.setMessageFlag1("-2");
					}
					}
			}
			
			String channelType = "";
			channelType = dbpool.getString(resultSet,"CHANNELTYPE");			

			message = "�𾴵�XXXXX�����ã�"+name+"�ĳ���"+licenseNo+"�Ѿ����ҹ�˾˳��XX�������յ�XX������" +
					  "�����Ķ�XX�����XX��Ϣ���������ʣ��뼰ʱ���ҹ�˾���ػ�����ϵ�����µ�95510��";
			if(channelType.indexOf("N07")>-1||channelType.indexOf("N105")>-1){
				message = "�𾴵�XXXXX";
			}
			
			prpCustomerMessageSchema.setMessage(message);
			SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String str1 = (String) map.get("createTime");
			String str[]=str1.split(" ");
			String nowDay = sdf.format(new Date()); 
			str[0] = sdf.format(sdf.parse(str[0]));
			if(!nowDay.equals(str[0])){
				prpCustomerMessageSchema.setCreatedDate(str1);
			}else{
				prpCustomerMessageSchema.setCreatedDate(dateFormat.format(new Date()));
			}
			logger.info("nowDay:"+nowDay+"    str[0]:"+str[0]+"    str1:"+str1+"    CreatedDate:"+prpCustomerMessageSchema.getCreatedDate());
			list.add(prpCustomerMessageSchema);
		}
		logger.info("DBPrpCustomerMessage->findPrpCustomerMessageInfo()->�Ӵ�ӡ�ػ�ȡ�˶������ݣ�"+list.size());
		resultSet.close();
		}else{
			DbPool printQueryDBPool = new DbPool();
			try{
				
				printQueryDBPool.open(PrintDBPool);
				
				String message = "";
				String name = "";
				String licenseNo = "";
				
				String groupFlag = "";
				String GXCondition = "0";
				
				String sql = "select distinct PrpPrintPool.*, PRPCMAIN.CHANNELTYPE,prpcitemcar.carkindcode," +
						"prpcitemcar.brandName,prpCmain.othflag,prpcitemcar.usenaturecode,prpintefinfo.lastClaimCount " +
			     "from PrpPrintPool,PRPCMAIN,prpcitemcar,prpcinsured,prpintefinfo " +
			     "where PrpPrintPool.createddate between to_date('"+map.get("startTime")+
				 "','YYYY-MM-DD HH24:MI:SS') " +
					 "and to_date('" + map.get("endTime") + 
					 "','YYYY-MM-DD HH24:MI:SS') and PrpPrintPool.printstatus='1' and PrpPrintPool.classcode='05' " +
					 "and (PrpPrintPool.printtype='P' " +
					        "or (PrpPrintPool.printtype='E' " +
					        	"and PrpPrintPool.Comcode like '17%' " +
					        	"and PrpPrintPool.IsEndorMobile = '1' " +
					        	"and PrpPrintPool.Businessnature in('2', '3', '4', '9', 'A', 'G', 'H', 'M', 'N', 'P')  " +
					        	"and prpcinsured.INSUREDFLAG = '1' and prpcinsured.insuredtype ='1'" +
					        	"and prpcitemcar.carkindcode not like 'M%' and prpcitemcar.carkindcode not like 'J%'))  " +
					 "and PrpPrintPool.POLICYNO = prpcitemcar.policyno " +
					 "and PrpPrintPool.POLICYNO = PRPCMAIN.POLICYNO  " +
					 "and PrpPrintPool.POLICYNO=prpcinsured.POLICYNO " +
					 "and PrpPrintPool.POLICYNO = prpintefinfo.policyno(+) " ;
			     			
				logger.info("ȡ��sql:(" + sql);
				logger.info("startTime="+map.get("startTime"));
				logger.info("endTime="+map.get("endTime"));
				logger.info("ȡXX����sql��"+sql);
				ResultSet resultSet = printQueryDBPool.query(sql);
				PrpCustomerMessageSchema prpCustomerMessageSchema = null;
				
				while(resultSet.next())
				{
					prpCustomerMessageSchema = new PrpCustomerMessageSchema();
					prpCustomerMessageSchema.setBusinessNature(printQueryDBPool.getString(resultSet,"BUSINESSNATURE"));
					prpCustomerMessageSchema.setClassCode(printQueryDBPool.getString(resultSet,"CLASSCODE"));
					prpCustomerMessageSchema.setCreatedCode("admin");
					prpCustomerMessageSchema.setCustomerName(printQueryDBPool.getString(resultSet,"INSUREDNAME"));
					prpCustomerMessageSchema.setMakCom(printQueryDBPool.getString(resultSet,"MAKECOM"));
					prpCustomerMessageSchema.setMessageType("1");
					prpCustomerMessageSchema.setPolicyNo(printQueryDBPool.getString(resultSet,"POLICYNO"));
					prpCustomerMessageSchema.setRiskCode(printQueryDBPool.getString(resultSet,"RISKCODE"));
					prpCustomerMessageSchema.setUpdateCode("admin");
					
					prpCustomerMessageSchema.setBusinessNo(printQueryDBPool.getString(resultSet,"businessNo"));
					prpCustomerMessageSchema.setPrintType(printQueryDBPool.getString(resultSet,"printType"));
					prpCustomerMessageSchema.setCarKindCode(printQueryDBPool.getString(resultSet,"carKindCode"));
					prpCustomerMessageSchema.setBrandName(printQueryDBPool.getString(resultSet,"brandName"));
					
					
					prpCustomerMessageSchema.setOthflag(printQueryDBPool.getString(resultSet,"othflag"));
					prpCustomerMessageSchema.setUsenaturecode(printQueryDBPool.getString(resultSet,"usenaturecode"));
					prpCustomerMessageSchema.setLastClaimCount(printQueryDBPool.getString(resultSet,"lastClaimCount"));
					
					GXCondition= getGXCondition(dbpool,printQueryDBPool.getString(resultSet,"POLICYNO"),printQueryDBPool.getString(resultSet,"BusinessNO"));
					prpCustomerMessageSchema.setGXCondition(GXCondition);
					
					groupFlag = getGroupFlag(dbpool,printQueryDBPool.getString(resultSet,"POLICYNO"));
					
					if("".equals(groupFlag)){
						groupFlag = "0";
					}
					
					prpCustomerMessageSchema.setGroupFlag(groupFlag);
					
					prpCustomerMessageSchema.setUnderwriteEndDate(""+printQueryDBPool.getDateTime(resultSet,"UnderwriteEndDate"));
					prpCustomerMessageSchema.setLicenseNo(printQueryDBPool.getString(resultSet,"LICENSENO"));
					prpCustomerMessageSchema.setEngineNo(printQueryDBPool.getString(resultSet,"engineNo"));
					Map customerMap = new HashMap();
					
					customerMap = getInfo(groupFlag,printQueryDBPool.getString(resultSet,"POLICYNO"));
					
					Map CustomerFromPrpCinsuredMap = new HashMap();
					CustomerFromPrpCinsuredMap = getCustomerInfoFromPrpCinsured(dbpool, groupFlag, printQueryDBPool.getString(resultSet,"POLICYNO"));
					
					if("0".equals(groupFlag))
					{   
						if(CustomerFromPrpCinsuredMap.get("mobile")==null || "".equals(CustomerFromPrpCinsuredMap.get("mobile"))){
						    prpCustomerMessageSchema.setMobile((String)customerMap.get("mobile"));
						}else{
							prpCustomerMessageSchema.setMobile((String)CustomerFromPrpCinsuredMap.get("mobile"));
						}
						if(CustomerFromPrpCinsuredMap.get("customerCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCode"))){
							prpCustomerMessageSchema.setCustomerCode((String)customerMap.get("customerCode"));
						}else{
							prpCustomerMessageSchema.setCustomerCode((String)CustomerFromPrpCinsuredMap.get("customerCode"));
						}
						if(CustomerFromPrpCinsuredMap.get("customerCname")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCname"))){
							prpCustomerMessageSchema.setCustomerName((String)customerMap.get("customerCname"));
						}else{
							prpCustomerMessageSchema.setCustomerName((String)CustomerFromPrpCinsuredMap.get("customerCname"));
						}
						if(CustomerFromPrpCinsuredMap.get("sex")==null || "".equals(CustomerFromPrpCinsuredMap.get("sex"))){
							prpCustomerMessageSchema.setSex((String)customerMap.get("sex"));
						}else{
							prpCustomerMessageSchema.setSex((String)CustomerFromPrpCinsuredMap.get("sex"));
						}
						if(CustomerFromPrpCinsuredMap.get("birthDate")==null || "".equals(CustomerFromPrpCinsuredMap.get("birthDate"))){
							prpCustomerMessageSchema.setBirthDate((String)customerMap.get("birthDate"));
						}else{
							prpCustomerMessageSchema.setBirthDate((String)CustomerFromPrpCinsuredMap.get("birthDate"));
						}
						if(CustomerFromPrpCinsuredMap.get("identifyType")==null || "".equals(CustomerFromPrpCinsuredMap.get("identifyType"))){
							prpCustomerMessageSchema.setIdentifyType((String)customerMap.get("identifyType"));
						}else{
							prpCustomerMessageSchema.setIdentifyType((String)CustomerFromPrpCinsuredMap.get("identifyType"));
						}
						if(CustomerFromPrpCinsuredMap.get("identifyNumber")==null || "".equals(CustomerFromPrpCinsuredMap.get("identifyNumber"))){
							prpCustomerMessageSchema.setIdentifyNumber((String)customerMap.get("identifyNumber"));
						}else{
							prpCustomerMessageSchema.setIdentifyNumber((String)CustomerFromPrpCinsuredMap.get("identifyNumber"));
						}
						if(CustomerFromPrpCinsuredMap.get("occupationCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("occupationCode"))){
							prpCustomerMessageSchema.setOccupationName((String)customerMap.get("occupationCode"));
						}else{
							prpCustomerMessageSchema.setOccupationName((String)CustomerFromPrpCinsuredMap.get("occupationCode"));
						}
						name = "��";
						licenseNo = prpCustomerMessageSchema.getLicenseNo();
					}
					
					if("1".equals(groupFlag))
					{
						if(CustomerFromPrpCinsuredMap.get("linkName")==null || "".equals(CustomerFromPrpCinsuredMap.get("linkName"))){
							prpCustomerMessageSchema.setLinkName((String)customerMap.get("linkName"));
						}else{
							prpCustomerMessageSchema.setLinkName((String)CustomerFromPrpCinsuredMap.get("linkName"));
						}
						if(CustomerFromPrpCinsuredMap.get("mobile")==null || "".equals(CustomerFromPrpCinsuredMap.get("mobile"))){
							prpCustomerMessageSchema.setMobile((String)customerMap.get("mobile"));
						}else{
							prpCustomerMessageSchema.setMobile((String)CustomerFromPrpCinsuredMap.get("mobile"));
						}
						if(CustomerFromPrpCinsuredMap.get("linkPhone")==null || "".equals(CustomerFromPrpCinsuredMap.get("linkPhone"))){
							prpCustomerMessageSchema.setPhone((String)customerMap.get("linkPhone"));
						}else{
							prpCustomerMessageSchema.setPhone((String)CustomerFromPrpCinsuredMap.get("linkPhone"));
						}
						if(CustomerFromPrpCinsuredMap.get("customerCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCode"))){
							prpCustomerMessageSchema.setCustomerCode((String)customerMap.get("customerCode"));
						}else{
							prpCustomerMessageSchema.setCustomerCode((String)CustomerFromPrpCinsuredMap.get("customerCode"));
						}
						if(CustomerFromPrpCinsuredMap.get("customerCname")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCname"))){
							prpCustomerMessageSchema.setCustomerName((String)customerMap.get("customerCname"));
						}else{
							prpCustomerMessageSchema.setCustomerName((String)CustomerFromPrpCinsuredMap.get("customerCname"));
						}
						if(CustomerFromPrpCinsuredMap.get("organizeCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("organizeCode"))){
							prpCustomerMessageSchema.setOrganizeCode((String)customerMap.get("organizeCode"));
						}else{
							prpCustomerMessageSchema.setOrganizeCode((String)CustomerFromPrpCinsuredMap.get("organizeCode"));
						}
						if(CustomerFromPrpCinsuredMap.get("addressCname")==null || "".equals(CustomerFromPrpCinsuredMap.get("addressCname"))){
							prpCustomerMessageSchema.setAddress((String)customerMap.get("addressCname"));
						}else{
							prpCustomerMessageSchema.setAddress((String)CustomerFromPrpCinsuredMap.get("addressCname"));
						}
						name = "��λ";
						licenseNo = "";
					}
					






					
					
					String comCode = "";
					comCode = printQueryDBPool.getString(resultSet,"COMCODE");
					prpCustomerMessageSchema.setComCode(comCode);
					
					if(prpCustomerMessageSchema.getCustomerName()==null ||"".equals(prpCustomerMessageSchema.getCustomerName()))
					{
						
						prpCustomerMessageSchema.setCustomerName("��");
					}
					if(prpCustomerMessageSchema.getMobile()==null ||"".equals(prpCustomerMessageSchema.getMobile()))
					{
						
						prpCustomerMessageSchema.setMobile("��");
						
						
						prpCustomerMessageSchema.setMessageFlag("3");
						
						prpCustomerMessageSchema.setMessageFlag1("3");
					}
					
					if(((String)prpCustomerMessageSchema.getMobile()).length()!=11  ||  "0".equals(((String)prpCustomerMessageSchema.getMobile()).substring(0,1)))
					{
						
						
						prpCustomerMessageSchema.setMessageFlag("3");
						
						prpCustomerMessageSchema.setMessageFlag1("3");		
					}
					else 
					{
						
						prpCustomerMessageSchema.setMessageFlag("-2");
						
						
						if(prpCustomerMessageSchema.getGXCondition()!=null&&!"".equals(prpCustomerMessageSchema.getGXCondition())){
							if ("1".equals(prpCustomerMessageSchema.getGXCondition())) {
						    	prpCustomerMessageSchema.setMessageFlag("1");
						    	prpCustomerMessageSchema.setPhoneVisitFlag("1");
							}
						}
						
						prpCustomerMessageSchema.setMessageFlag1("-9");
						if(prpCustomerMessageSchema.getComCode()!=null&&!"".equals(prpCustomerMessageSchema.getComCode())){
							if (prpCustomerMessageSchema.getComCode().startsWith("0651")) {
						    	prpCustomerMessageSchema.setMessageFlag1("-2");
							}
							}
					}
					
					String channelType = "";
					channelType = printQueryDBPool.getString(resultSet,"CHANNELTYPE");			

					message = "�𾴵�XXXXX�����ã�"+name+"�ĳ���"+licenseNo+"�Ѿ����ҹ�˾˳��XX�������յ�XX������" +
							  "�����Ķ�XX�����XX��Ϣ���������ʣ��뼰ʱ���ҹ�˾���ػ�����ϵ�����µ�95510��";
					if(channelType.indexOf("N07")>-1||channelType.indexOf("N105")>-1){
						message = "�𾴵�XXXXX";
					}
					prpCustomerMessageSchema.setMessage(message);
					
					prpCustomerMessageSchema.setMessage(message);
					SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String str1 = (String) map.get("createTime");
					String str[]=str1.split(" ");
					String nowDay = sdf.format(new Date()); 
					str[0] = sdf.format(sdf.parse(str[0]));
					logger.info("nowDay:"+nowDay+"    str[0]:"+str[0]);
					if(!nowDay.equals(str[0])){
						prpCustomerMessageSchema.setCreatedDate(str1);
					}else{
						prpCustomerMessageSchema.setCreatedDate(dateFormat.format(new Date()));
					}
					
					list.add(prpCustomerMessageSchema);
				}
				logger.info("DBPrpCustomerMessage->findPrpCustomerMessageInfo()->�Ӵ�ӡ�ػ�ȡ�˶������ݣ�"+list.size());
				resultSet.close();
				
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception(e);
			}finally{
				try {
					if(printQueryDBPool!=null)
					{
						printQueryDBPool.close();
					}
				} catch (SQLException e){
					throw new Exception(e);
				}
			}
			
		}
		
		return list;
	}
	
	
	/**
	 *
	 * ȡXX�µ��Ÿ�������
	 *
	 * @param dbpool
	 * @return
	 * @throws Exception 
	 */
	public List findPrpCustomerMessageInfoGX(DbPool dbpool,Map map) throws Exception
	{
		List list = new ArrayList();
		
		String PrintDBPool = "0";
		try{
			PrintDBPool = SysConfig.getProperty("CustomerRealQueryDBPool");
		}catch(Exception e){
			PrintDBPool="0";
		}
		if("0".equals(PrintDBPool)){
		String message = "";
		String name = "";
		String licenseNo = "";
		
		String groupFlag = "";
		String GXCondition = "0";
		
		String sql = "select distinct PrpPrintPool.*, PRPCMAIN.CHANNELTYPE,prpcitemcar.carkindcode," +
				"prpcitemcar.brandName " +
	     "from PrpPrintPool,PRPCMAIN,prpcitemcar,prpcinsured " +
	     "where PrpPrintPool.createddate between to_date('"+map.get("startTime")+
		 "','YYYY-MM-DD HH24:MI:SS') " +
			 "and to_date('" + map.get("endTime") + 
			 "','YYYY-MM-DD HH24:MI:SS') and PrpPrintPool.printstatus='1' and PrpPrintPool.classcode='05' " +
			 "and (PrpPrintPool.printtype='P' " +
			        "or (PrpPrintPool.printtype='E' and PrpPrintPool.IsEndorMobile = '1')) " +
			        	"and PrpPrintPool.Comcode like '17%' " +
			        	"and PrpPrintPool.Businessnature in('2', '3', '4', '9', 'A', 'G', 'H', 'M', 'N', 'P')  " +
			        	"and prpcinsured.INSUREDFLAG = '1' and prpcinsured.insuredtype ='1'" +
			        	"and prpcitemcar.carkindcode not like 'M%' and prpcitemcar.carkindcode not like 'J%'  " +
			 "and PrpPrintPool.POLICYNO = prpcitemcar.policyno " +
			 "and PrpPrintPool.POLICYNO = PRPCMAIN.POLICYNO  " +
			 "and PrpPrintPool.POLICYNO=prpcinsured.POLICYNO " ;
	     			
		logger.info("ȡ��sql:(" + sql);
		logger.info("startTime="+map.get("startTime"));
		logger.info("endTime="+map.get("endTime"));
		logger.info("ȡXX����sql��"+sql);
		ResultSet resultSet = dbpool.query(sql);
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		
		while(resultSet.next())
		{
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setBusinessNature(dbpool.getString(resultSet,"BUSINESSNATURE"));
			prpCustomerMessageSchema.setClassCode(dbpool.getString(resultSet,"CLASSCODE"));
			prpCustomerMessageSchema.setCreatedCode("admin");
			prpCustomerMessageSchema.setCustomerName(dbpool.getString(resultSet,"INSUREDNAME"));
			prpCustomerMessageSchema.setMakCom(dbpool.getString(resultSet,"MAKECOM"));
			prpCustomerMessageSchema.setMessageType("1");
			prpCustomerMessageSchema.setPolicyNo(dbpool.getString(resultSet,"POLICYNO"));
			prpCustomerMessageSchema.setRiskCode(dbpool.getString(resultSet,"RISKCODE"));
			prpCustomerMessageSchema.setUpdateCode("admin");
			
			prpCustomerMessageSchema.setBusinessNo(dbpool.getString(resultSet,"businessNo"));
			prpCustomerMessageSchema.setPrintType(dbpool.getString(resultSet,"printType"));
			prpCustomerMessageSchema.setCarKindCode(dbpool.getString(resultSet,"carKindCode"));
			prpCustomerMessageSchema.setBrandName(dbpool.getString(resultSet,"brandName"));
			
			
			GXCondition= getGXCondition(dbpool,dbpool.getString(resultSet,"POLICYNO"),dbpool.getString(resultSet,"BusinessNO"));
			prpCustomerMessageSchema.setGXCondition(GXCondition);
			
			groupFlag = getGroupFlag(dbpool,dbpool.getString(resultSet,"POLICYNO"));
			
			if("".equals(groupFlag)){
				groupFlag = "0";
			}
			
			prpCustomerMessageSchema.setGroupFlag(groupFlag);
			
			prpCustomerMessageSchema.setUnderwriteEndDate(""+dbpool.getDateTime(resultSet,"UnderwriteEndDate"));
			prpCustomerMessageSchema.setLicenseNo(dbpool.getString(resultSet,"LICENSENO"));
			prpCustomerMessageSchema.setEngineNo(dbpool.getString(resultSet,"engineNo"));
			Map customerMap = new HashMap();
			
			customerMap = getInfo(groupFlag,dbpool.getString(resultSet,"POLICYNO"));
			
			Map CustomerFromPrpCinsuredMap = new HashMap();
			CustomerFromPrpCinsuredMap = getCustomerInfoFromPrpCinsured(dbpool, groupFlag, dbpool.getString(resultSet,"POLICYNO"));
			
			if("0".equals(groupFlag))
			{   
				if(CustomerFromPrpCinsuredMap.get("mobile")==null || "".equals(CustomerFromPrpCinsuredMap.get("mobile"))){
				    prpCustomerMessageSchema.setMobile((String)customerMap.get("mobile"));
				}else{
					prpCustomerMessageSchema.setMobile((String)CustomerFromPrpCinsuredMap.get("mobile"));
				}
				if(CustomerFromPrpCinsuredMap.get("customerCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCode"))){
					prpCustomerMessageSchema.setCustomerCode((String)customerMap.get("customerCode"));
				}else{
					prpCustomerMessageSchema.setCustomerCode((String)CustomerFromPrpCinsuredMap.get("customerCode"));
				}
				if(CustomerFromPrpCinsuredMap.get("customerCname")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCname"))){
					prpCustomerMessageSchema.setCustomerName((String)customerMap.get("customerCname"));
				}else{
					prpCustomerMessageSchema.setCustomerName((String)CustomerFromPrpCinsuredMap.get("customerCname"));
				}
				if(CustomerFromPrpCinsuredMap.get("sex")==null || "".equals(CustomerFromPrpCinsuredMap.get("sex"))){
					prpCustomerMessageSchema.setSex((String)customerMap.get("sex"));
				}else{
					prpCustomerMessageSchema.setSex((String)CustomerFromPrpCinsuredMap.get("sex"));
				}
				if(CustomerFromPrpCinsuredMap.get("birthDate")==null || "".equals(CustomerFromPrpCinsuredMap.get("birthDate"))){
					prpCustomerMessageSchema.setBirthDate((String)customerMap.get("birthDate"));
				}else{
					prpCustomerMessageSchema.setBirthDate((String)CustomerFromPrpCinsuredMap.get("birthDate"));
				}
				if(CustomerFromPrpCinsuredMap.get("identifyType")==null || "".equals(CustomerFromPrpCinsuredMap.get("identifyType"))){
					prpCustomerMessageSchema.setIdentifyType((String)customerMap.get("identifyType"));
				}else{
					prpCustomerMessageSchema.setIdentifyType((String)CustomerFromPrpCinsuredMap.get("identifyType"));
				}
				if(CustomerFromPrpCinsuredMap.get("identifyNumber")==null || "".equals(CustomerFromPrpCinsuredMap.get("identifyNumber"))){
					prpCustomerMessageSchema.setIdentifyNumber((String)customerMap.get("identifyNumber"));
				}else{
					prpCustomerMessageSchema.setIdentifyNumber((String)CustomerFromPrpCinsuredMap.get("identifyNumber"));
				}
				if(CustomerFromPrpCinsuredMap.get("occupationCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("occupationCode"))){
					prpCustomerMessageSchema.setOccupationName((String)customerMap.get("occupationCode"));
				}else{
					prpCustomerMessageSchema.setOccupationName((String)CustomerFromPrpCinsuredMap.get("occupationCode"));
				}
				name = "��";
				licenseNo = prpCustomerMessageSchema.getLicenseNo();
			}
			
			if("1".equals(groupFlag))
			{
				if(CustomerFromPrpCinsuredMap.get("linkName")==null || "".equals(CustomerFromPrpCinsuredMap.get("linkName"))){
					prpCustomerMessageSchema.setLinkName((String)customerMap.get("linkName"));
				}else{
					prpCustomerMessageSchema.setLinkName((String)CustomerFromPrpCinsuredMap.get("linkName"));
				}
				if(CustomerFromPrpCinsuredMap.get("mobile")==null || "".equals(CustomerFromPrpCinsuredMap.get("mobile"))){
					prpCustomerMessageSchema.setMobile((String)customerMap.get("mobile"));
				}else{
					prpCustomerMessageSchema.setMobile((String)CustomerFromPrpCinsuredMap.get("mobile"));
				}
				if(CustomerFromPrpCinsuredMap.get("linkPhone")==null || "".equals(CustomerFromPrpCinsuredMap.get("linkPhone"))){
					prpCustomerMessageSchema.setPhone((String)customerMap.get("linkPhone"));
				}else{
					prpCustomerMessageSchema.setPhone((String)CustomerFromPrpCinsuredMap.get("linkPhone"));
				}
				if(CustomerFromPrpCinsuredMap.get("customerCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCode"))){
					prpCustomerMessageSchema.setCustomerCode((String)customerMap.get("customerCode"));
				}else{
					prpCustomerMessageSchema.setCustomerCode((String)CustomerFromPrpCinsuredMap.get("customerCode"));
				}
				if(CustomerFromPrpCinsuredMap.get("customerCname")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCname"))){
					prpCustomerMessageSchema.setCustomerName((String)customerMap.get("customerCname"));
				}else{
					prpCustomerMessageSchema.setCustomerName((String)CustomerFromPrpCinsuredMap.get("customerCname"));
				}
				if(CustomerFromPrpCinsuredMap.get("organizeCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("organizeCode"))){
					prpCustomerMessageSchema.setOrganizeCode((String)customerMap.get("organizeCode"));
				}else{
					prpCustomerMessageSchema.setOrganizeCode((String)CustomerFromPrpCinsuredMap.get("organizeCode"));
				}
				if(CustomerFromPrpCinsuredMap.get("addressCname")==null || "".equals(CustomerFromPrpCinsuredMap.get("addressCname"))){
					prpCustomerMessageSchema.setAddress((String)customerMap.get("addressCname"));
				}else{
					prpCustomerMessageSchema.setAddress((String)CustomerFromPrpCinsuredMap.get("addressCname"));
				}
				name = "��λ";
				licenseNo = "";
			}
			






			
			
			String comCode = "";
			comCode = dbpool.getString(resultSet,"COMCODE");
			prpCustomerMessageSchema.setComCode(comCode);
			
			if(prpCustomerMessageSchema.getCustomerName()==null ||"".equals(prpCustomerMessageSchema.getCustomerName()))
			{
				
				prpCustomerMessageSchema.setCustomerName("��");
			}
			if(prpCustomerMessageSchema.getMobile()==null ||"".equals(prpCustomerMessageSchema.getMobile()))
			{
				
				prpCustomerMessageSchema.setMobile("��");
				
				
				prpCustomerMessageSchema.setMessageFlag("3");
				
				prpCustomerMessageSchema.setMessageFlag1("3");
			}
			
			if(((String)prpCustomerMessageSchema.getMobile()).length()!=11  ||  "0".equals(((String)prpCustomerMessageSchema.getMobile()).substring(0,1)))
			{
				
				
				prpCustomerMessageSchema.setMessageFlag("3");
				
				prpCustomerMessageSchema.setMessageFlag1("3");		
			}
			else 
			{
				
				prpCustomerMessageSchema.setMessageFlag("-2");
				
				
				if(prpCustomerMessageSchema.getGXCondition()!=null&&!"".equals(prpCustomerMessageSchema.getGXCondition())){
					if ("1".equals(prpCustomerMessageSchema.getGXCondition())) {
				    	prpCustomerMessageSchema.setMessageFlag("1");
				    	prpCustomerMessageSchema.setPhoneVisitFlag("1");
					}
				}
				
				prpCustomerMessageSchema.setMessageFlag1("-9");
				if(prpCustomerMessageSchema.getComCode()!=null&&!"".equals(prpCustomerMessageSchema.getComCode())){
					if (prpCustomerMessageSchema.getComCode().startsWith("0651")) {
				    	prpCustomerMessageSchema.setMessageFlag1("-2");
					}
					}
			}
			
			String channelType = "";
			channelType = dbpool.getString(resultSet,"CHANNELTYPE");			

			message = "�𾴵�XXXXX�����ã�"+name+"�ĳ���"+licenseNo+"�Ѿ����ҹ�˾˳��XX�������յ�XX������" +
					  "�����Ķ�XX�����XX��Ϣ���������ʣ��뼰ʱ���ҹ�˾���ػ�����ϵ�����µ�95510��";
			if(channelType.indexOf("N07")>-1||channelType.indexOf("N105")>-1){
				message = "�𾴵�XXXXX";
			}
			prpCustomerMessageSchema.setMessage(message);
			
			list.add(prpCustomerMessageSchema);
		}
		logger.info("DBPrpCustomerMessage->findPrpCustomerMessageInfo()->�Ӵ�ӡ�ػ�ȡ�˶������ݣ�"+list.size());
		resultSet.close();
		}else{
			DbPool printQueryDBPool = new DbPool();
			try{
				
				printQueryDBPool.open(PrintDBPool);
				
				String message = "";
				String name = "";
				String licenseNo = "";
				
				String groupFlag = "";
				String GXCondition = "0";
				
				String sql = "select distinct PrpPrintPool.*, PRPCMAIN.CHANNELTYPE,prpcitemcar.carkindcode," +
						"prpcitemcar.brandName " +
			     "from PrpPrintPool,PRPCMAIN,prpcitemcar,prpcinsured " +
			     "where PrpPrintPool.createddate between to_date('"+map.get("startTime")+
				 "','YYYY-MM-DD HH24:MI:SS') " +
					 "and to_date('" + map.get("endTime") + 
					 "','YYYY-MM-DD HH24:MI:SS') and PrpPrintPool.printstatus='1' and PrpPrintPool.classcode='05' " +
					 "and (PrpPrintPool.printtype='P' " +
					        "or (PrpPrintPool.printtype='E' and PrpPrintPool.IsEndorMobile = '1')) " +
					        	"and PrpPrintPool.Comcode like '17%' " +
					        	"and PrpPrintPool.Businessnature in('2', '3', '4', '9', 'A', 'G', 'H', 'M', 'N', 'P')  " +
					        	"and prpcinsured.INSUREDFLAG = '1' and prpcinsured.insuredtype ='1'" +
					        	"and prpcitemcar.carkindcode not like 'M%' and prpcitemcar.carkindcode not like 'J%'  " +
					 "and PrpPrintPool.POLICYNO = prpcitemcar.policyno " +
					 "and PrpPrintPool.POLICYNO = PRPCMAIN.POLICYNO  " +
					 "and PrpPrintPool.POLICYNO=prpcinsured.POLICYNO " ;
			     			
				logger.info("ȡ��sql:(" + sql);
				logger.info("startTime="+map.get("startTime"));
				logger.info("endTime="+map.get("endTime"));
				logger.info("ȡXX����sql��"+sql);
				ResultSet resultSet = printQueryDBPool.query(sql);
				PrpCustomerMessageSchema prpCustomerMessageSchema = null;
				
				while(resultSet.next())
				{
					prpCustomerMessageSchema = new PrpCustomerMessageSchema();
					prpCustomerMessageSchema.setBusinessNature(printQueryDBPool.getString(resultSet,"BUSINESSNATURE"));
					prpCustomerMessageSchema.setClassCode(printQueryDBPool.getString(resultSet,"CLASSCODE"));
					prpCustomerMessageSchema.setCreatedCode("admin");
					prpCustomerMessageSchema.setCustomerName(printQueryDBPool.getString(resultSet,"INSUREDNAME"));
					prpCustomerMessageSchema.setMakCom(printQueryDBPool.getString(resultSet,"MAKECOM"));
					prpCustomerMessageSchema.setMessageType("1");
					prpCustomerMessageSchema.setPolicyNo(printQueryDBPool.getString(resultSet,"POLICYNO"));
					prpCustomerMessageSchema.setRiskCode(printQueryDBPool.getString(resultSet,"RISKCODE"));
					prpCustomerMessageSchema.setUpdateCode("admin");
					
					prpCustomerMessageSchema.setBusinessNo(printQueryDBPool.getString(resultSet,"businessNo"));
					prpCustomerMessageSchema.setPrintType(printQueryDBPool.getString(resultSet,"printType"));
					prpCustomerMessageSchema.setCarKindCode(printQueryDBPool.getString(resultSet,"carKindCode"));
					prpCustomerMessageSchema.setBrandName(printQueryDBPool.getString(resultSet,"brandName"));
					
					
					GXCondition= getGXCondition(dbpool,printQueryDBPool.getString(resultSet,"POLICYNO"),printQueryDBPool.getString(resultSet,"BusinessNO"));
					prpCustomerMessageSchema.setGXCondition(GXCondition);
					
					groupFlag = getGroupFlag(dbpool,printQueryDBPool.getString(resultSet,"POLICYNO"));
					
					if("".equals(groupFlag)){
						groupFlag = "0";
					}
					
					prpCustomerMessageSchema.setGroupFlag(groupFlag);
					
					prpCustomerMessageSchema.setUnderwriteEndDate(""+printQueryDBPool.getDateTime(resultSet,"UnderwriteEndDate"));
					prpCustomerMessageSchema.setLicenseNo(printQueryDBPool.getString(resultSet,"LICENSENO"));
					prpCustomerMessageSchema.setEngineNo(printQueryDBPool.getString(resultSet,"engineNo"));
					Map customerMap = new HashMap();
					
					customerMap = getInfo(groupFlag,printQueryDBPool.getString(resultSet,"POLICYNO"));
					
					Map CustomerFromPrpCinsuredMap = new HashMap();
					CustomerFromPrpCinsuredMap = getCustomerInfoFromPrpCinsured(dbpool, groupFlag, printQueryDBPool.getString(resultSet,"POLICYNO"));
					
					if("0".equals(groupFlag))
					{   
						if(CustomerFromPrpCinsuredMap.get("mobile")==null || "".equals(CustomerFromPrpCinsuredMap.get("mobile"))){
						    prpCustomerMessageSchema.setMobile((String)customerMap.get("mobile"));
						}else{
							prpCustomerMessageSchema.setMobile((String)CustomerFromPrpCinsuredMap.get("mobile"));
						}
						if(CustomerFromPrpCinsuredMap.get("customerCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCode"))){
							prpCustomerMessageSchema.setCustomerCode((String)customerMap.get("customerCode"));
						}else{
							prpCustomerMessageSchema.setCustomerCode((String)CustomerFromPrpCinsuredMap.get("customerCode"));
						}
						if(CustomerFromPrpCinsuredMap.get("customerCname")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCname"))){
							prpCustomerMessageSchema.setCustomerName((String)customerMap.get("customerCname"));
						}else{
							prpCustomerMessageSchema.setCustomerName((String)CustomerFromPrpCinsuredMap.get("customerCname"));
						}
						if(CustomerFromPrpCinsuredMap.get("sex")==null || "".equals(CustomerFromPrpCinsuredMap.get("sex"))){
							prpCustomerMessageSchema.setSex((String)customerMap.get("sex"));
						}else{
							prpCustomerMessageSchema.setSex((String)CustomerFromPrpCinsuredMap.get("sex"));
						}
						if(CustomerFromPrpCinsuredMap.get("birthDate")==null || "".equals(CustomerFromPrpCinsuredMap.get("birthDate"))){
							prpCustomerMessageSchema.setBirthDate((String)customerMap.get("birthDate"));
						}else{
							prpCustomerMessageSchema.setBirthDate((String)CustomerFromPrpCinsuredMap.get("birthDate"));
						}
						if(CustomerFromPrpCinsuredMap.get("identifyType")==null || "".equals(CustomerFromPrpCinsuredMap.get("identifyType"))){
							prpCustomerMessageSchema.setIdentifyType((String)customerMap.get("identifyType"));
						}else{
							prpCustomerMessageSchema.setIdentifyType((String)CustomerFromPrpCinsuredMap.get("identifyType"));
						}
						if(CustomerFromPrpCinsuredMap.get("identifyNumber")==null || "".equals(CustomerFromPrpCinsuredMap.get("identifyNumber"))){
							prpCustomerMessageSchema.setIdentifyNumber((String)customerMap.get("identifyNumber"));
						}else{
							prpCustomerMessageSchema.setIdentifyNumber((String)CustomerFromPrpCinsuredMap.get("identifyNumber"));
						}
						if(CustomerFromPrpCinsuredMap.get("occupationCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("occupationCode"))){
							prpCustomerMessageSchema.setOccupationName((String)customerMap.get("occupationCode"));
						}else{
							prpCustomerMessageSchema.setOccupationName((String)CustomerFromPrpCinsuredMap.get("occupationCode"));
						}
						name = "��";
						licenseNo = prpCustomerMessageSchema.getLicenseNo();
					}
					
					if("1".equals(groupFlag))
					{
						if(CustomerFromPrpCinsuredMap.get("linkName")==null || "".equals(CustomerFromPrpCinsuredMap.get("linkName"))){
							prpCustomerMessageSchema.setLinkName((String)customerMap.get("linkName"));
						}else{
							prpCustomerMessageSchema.setLinkName((String)CustomerFromPrpCinsuredMap.get("linkName"));
						}
						if(CustomerFromPrpCinsuredMap.get("mobile")==null || "".equals(CustomerFromPrpCinsuredMap.get("mobile"))){
							prpCustomerMessageSchema.setMobile((String)customerMap.get("mobile"));
						}else{
							prpCustomerMessageSchema.setMobile((String)CustomerFromPrpCinsuredMap.get("mobile"));
						}
						if(CustomerFromPrpCinsuredMap.get("linkPhone")==null || "".equals(CustomerFromPrpCinsuredMap.get("linkPhone"))){
							prpCustomerMessageSchema.setPhone((String)customerMap.get("linkPhone"));
						}else{
							prpCustomerMessageSchema.setPhone((String)CustomerFromPrpCinsuredMap.get("linkPhone"));
						}
						if(CustomerFromPrpCinsuredMap.get("customerCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCode"))){
							prpCustomerMessageSchema.setCustomerCode((String)customerMap.get("customerCode"));
						}else{
							prpCustomerMessageSchema.setCustomerCode((String)CustomerFromPrpCinsuredMap.get("customerCode"));
						}
						if(CustomerFromPrpCinsuredMap.get("customerCname")==null || "".equals(CustomerFromPrpCinsuredMap.get("customerCname"))){
							prpCustomerMessageSchema.setCustomerName((String)customerMap.get("customerCname"));
						}else{
							prpCustomerMessageSchema.setCustomerName((String)CustomerFromPrpCinsuredMap.get("customerCname"));
						}
						if(CustomerFromPrpCinsuredMap.get("organizeCode")==null || "".equals(CustomerFromPrpCinsuredMap.get("organizeCode"))){
							prpCustomerMessageSchema.setOrganizeCode((String)customerMap.get("organizeCode"));
						}else{
							prpCustomerMessageSchema.setOrganizeCode((String)CustomerFromPrpCinsuredMap.get("organizeCode"));
						}
						if(CustomerFromPrpCinsuredMap.get("addressCname")==null || "".equals(CustomerFromPrpCinsuredMap.get("addressCname"))){
							prpCustomerMessageSchema.setAddress((String)customerMap.get("addressCname"));
						}else{
							prpCustomerMessageSchema.setAddress((String)CustomerFromPrpCinsuredMap.get("addressCname"));
						}
						name = "��λ";
						licenseNo = "";
					}
					






					
					
					String comCode = "";
					comCode = printQueryDBPool.getString(resultSet,"COMCODE");
					prpCustomerMessageSchema.setComCode(comCode);
					
					if(prpCustomerMessageSchema.getCustomerName()==null ||"".equals(prpCustomerMessageSchema.getCustomerName()))
					{
						
						prpCustomerMessageSchema.setCustomerName("��");
					}
					if(prpCustomerMessageSchema.getMobile()==null ||"".equals(prpCustomerMessageSchema.getMobile()))
					{
						
						prpCustomerMessageSchema.setMobile("��");
						
						
						prpCustomerMessageSchema.setMessageFlag("3");
						
						prpCustomerMessageSchema.setMessageFlag1("3");
					}
					
					if(((String)prpCustomerMessageSchema.getMobile()).length()!=11  ||  "0".equals(((String)prpCustomerMessageSchema.getMobile()).substring(0,1)))
					{
						
						
						prpCustomerMessageSchema.setMessageFlag("3");
						
						prpCustomerMessageSchema.setMessageFlag1("3");		
					}
					else 
					{
						
						prpCustomerMessageSchema.setMessageFlag("-2");
						
						
						if(prpCustomerMessageSchema.getGXCondition()!=null&&!"".equals(prpCustomerMessageSchema.getGXCondition())){
							if ("1".equals(prpCustomerMessageSchema.getGXCondition())) {
						    	prpCustomerMessageSchema.setMessageFlag("1");
						    	prpCustomerMessageSchema.setPhoneVisitFlag("1");
							}
						}
						
						prpCustomerMessageSchema.setMessageFlag1("-9");
						if(prpCustomerMessageSchema.getComCode()!=null&&!"".equals(prpCustomerMessageSchema.getComCode())){
							if (prpCustomerMessageSchema.getComCode().startsWith("0651")) {
						    	prpCustomerMessageSchema.setMessageFlag1("-2");
							}
							}
					}
					
					String channelType = "";
					channelType = printQueryDBPool.getString(resultSet,"CHANNELTYPE");			

					message = "�𾴵�XXXXX�����ã�"+name+"�ĳ���"+licenseNo+"�Ѿ����ҹ�˾˳��XX�������յ�XX������" +
							  "�����Ķ�XX�����XX��Ϣ���������ʣ��뼰ʱ���ҹ�˾���ػ�����ϵ�����µ�95510��";
					if(channelType.indexOf("N07")>-1||channelType.indexOf("N105")>-1){
						message = "�𾴵�XXXXX";
					}
					prpCustomerMessageSchema.setMessage(message);
					
					list.add(prpCustomerMessageSchema);
				}
				logger.info("DBPrpCustomerMessage->findPrpCustomerMessageInfo()->�Ӵ�ӡ�ػ�ȡ�˶������ݣ�"+list.size());
				resultSet.close();
			}
			catch(Exception e){
				e.printStackTrace();
				throw new Exception(e);
			}finally{
				try {
					if(printQueryDBPool!=null)
					{
						printQueryDBPool.close();
					}
				} catch (SQLException e){
					throw new Exception(e);
				}
			}
			
		}
		
				
		return list;
	}
	
	
	/**
	 * ��prpCustomerMessage��������
	 * @param dbpool
	 * @param prpCustomerMessageSchema
	 * @throws  Exception 
	 * @throws Exception 
	 */
	public void insertPrpCustomerMessage(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema) 
	{
		String sql = "insert into prpCustomerMessage(" +
		             "ID," +
		             "SENDSEQ," +
		             "CREATEDCODE," +
		             "UPDATECODE," +
		             "MESSAGETYPE," +
		             "POLICYNO," +
		             "CLASSCODE," +
		             "RISKCODE," +
		             "BUSINESSNATURE," +
		             "CUSTOMERNAME," +
		             "MESSAGE," +
		             "LICENSENO," +
		             "MOBILE," +
		             "MAKCOM," +
		             "GROUPFLAG," +
		             "CustomerCode," +
		             "Sex," +
		             "BirthDate," +
		             "IdentifyType," +
		             "IdentifyNumber," +
		             "LinkName," +
		             "OrganizeCode," +
		             "Address," +
		             "OccupationName," +
		             "Phone," +
		             "UnderwriteEndDate," +
		             "MessageFlag, " +
		             "engineno, " +
		             "SENDSEQSPECIFIC, " + 
		             "MessageFlag1," + 
		             "ComCode, " +
		             "BusinessNo, " +
		             "PrintType, " +
		             "CarKindCode, " +
		             "BrandName, " +
		             "GXCondition, " + 
		             "PhoneVisitFlag, " + 
		             "createddate " +
		             ") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) " ;
		try{	     
		dbpool.prepareInnerStatement(sql);
		int j = 0;
		dbpool.setString(++j,prpCustomerMessageSchema.getId());
		dbpool.setString(++j,prpCustomerMessageSchema.getSendSeq());
		dbpool.setString(++j,prpCustomerMessageSchema.getCreatedCode());
		dbpool.setString(++j,prpCustomerMessageSchema.getUpdateCode());
		dbpool.setString(++j,prpCustomerMessageSchema.getMessageType());
		dbpool.setString(++j,prpCustomerMessageSchema.getPolicyNo());
		dbpool.setString(++j,prpCustomerMessageSchema.getClassCode());
		dbpool.setString(++j,prpCustomerMessageSchema.getRiskCode());
		dbpool.setString(++j,prpCustomerMessageSchema.getBusinessNature());
		dbpool.setString(++j,prpCustomerMessageSchema.getCustomerName());
		dbpool.setString(++j,prpCustomerMessageSchema.getMessage());
		dbpool.setString(++j,prpCustomerMessageSchema.getLicenseNo());
		dbpool.setString(++j,prpCustomerMessageSchema.getMobile());
		dbpool.setString(++j,prpCustomerMessageSchema.getMakCom());
		dbpool.setString(++j,prpCustomerMessageSchema.getGroupFlag());
		dbpool.setString(++j,prpCustomerMessageSchema.getCustomerCode());
		dbpool.setString(++j,prpCustomerMessageSchema.getSex());
		dbpool.setDateTime(++j, new DateTime(prpCustomerMessageSchema.getBirthDate()));
		dbpool.setString(++j,prpCustomerMessageSchema.getIdentifyType());
		dbpool.setString(++j,prpCustomerMessageSchema.getIdentifyNumber());
		dbpool.setString(++j,prpCustomerMessageSchema.getLinkName());
		dbpool.setString(++j,prpCustomerMessageSchema.getOrganizeCode());
		dbpool.setString(++j,prpCustomerMessageSchema.getAddress());
		dbpool.setString(++j,prpCustomerMessageSchema.getOccupationName());
		dbpool.setString(++j,prpCustomerMessageSchema.getPhone());
		dbpool.setDateTime(++j,new DateTime(prpCustomerMessageSchema.getUnderwriteEndDate()));
		dbpool.setString(++j,prpCustomerMessageSchema.getMessageFlag());
		dbpool.setString(++j,prpCustomerMessageSchema.getEngineNo());
		
		dbpool.setString(++j,prpCustomerMessageSchema.getSendSeqSpecific());
		dbpool.setString(++j,prpCustomerMessageSchema.getMessageFlag1());
		dbpool.setString(++j,prpCustomerMessageSchema.getComCode());
		
		
		dbpool.setString(++j,prpCustomerMessageSchema.getBusinessNo());
		dbpool.setString(++j,prpCustomerMessageSchema.getPrintType());
		dbpool.setString(++j,prpCustomerMessageSchema.getCarKindCode());
		dbpool.setString(++j,prpCustomerMessageSchema.getBrandName());
		dbpool.setString(++j,prpCustomerMessageSchema.getGXCondition());
		if(prpCustomerMessageSchema.getGXCondition()!=null&&!"".equals(prpCustomerMessageSchema.getGXCondition())){
			if ("1".equals(prpCustomerMessageSchema.getGXCondition())) {
				dbpool.setString(++j,prpCustomerMessageSchema.getPhoneVisitFlag());
			}else{
				dbpool.setString(++j,"2");
			}
		}else{
			dbpool.setString(++j,"2");
		}
		dbpool.setDateTime(++j,new DateTime(prpCustomerMessageSchema.getCreatedDate(),DateTime.YEAR_TO_SECOND));
		
		
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->insertPrpCustomerMessage()->��prpCustomerMessage��������->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.insertPrpCustomerMessage", message,e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			
		}
	}
	
	/**
	 * @param groupFlag
	 * @param policyNo
	 * @return
	 * @throws Exception
	 */
	private Map getInfo(String groupFlag,String policyNo) throws Exception
	{
		Map map = new HashMap();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			
			if("0".equals(groupFlag))
			{
				map = getCustomerInfo(dbpool, policyNo);
			}
			
			if("1".equals(groupFlag))
			{
				map = getCustomerInfoGroup( dbpool, policyNo);
			}
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return map;
	}
	/**
	 * ��ȡ��XXXXX����Ϣ��������
	 * @param dbpool
	 * @param policyNo
	 * @return
	 * @throws Exception
	 */
	private Map getCustomerInfo(DbPool dbpool,String policyNo) throws Exception
	{
		Map map = new HashMap();
		
		if("1".equals(SysConfig.getProperty("CUSTOMER_DATA_SOURCE_SWITCH"))){
		    DbPool dbpoolNew = new DbPool();
		    try {
                dbpoolNew.open(SysConfig.CONST_CUSTOMERDATASOURCE);
                String sql = "SELECT insuredcode FROM prpcinsured where policyno = ? and insuredflag = '1' and InsuredType = '1'";
                String retInsuredcode = "";
                  dbpool.prepareInnerStatement(sql);
                  dbpool.setString(1,policyNo);
                  ResultSet resultSet = dbpool.executePreparedQuery();
                  while(resultSet.next())
                  {
                      retInsuredcode = dbpool.getString(resultSet,"insuredcode");
                  }
                      resultSet.close();
                  dbpool.closePreparedStatement();
                  if(retInsuredcode!=null && !"".equals(retInsuredcode)){
                      String querySql =    
                      "SELECT c.mobile, " +
                      "c.customercode,  " +
                      "c.customercname, " +
                      "decode(c.sex, '1', '��', '2', 'Ů') as sex, " +
                      "c.birthdate, " +
                      "decode(c.identifytype,'01','���֤','02','���ڱ�','03','����','04','����֤','05','��ʻִ��','06','����֤','����') as identifytype, " +
                      "c.identifynumber, " +
                      "c.occupationCode  " +
                      "FROM t_spis_customer_ind c " +
                      "where c.customercode = ? ";
                      dbpoolNew.prepareInnerStatement(querySql);
                      dbpoolNew.setString(1,retInsuredcode);
                      ResultSet resultSet1 = dbpoolNew.executePreparedQuery();
                      while(resultSet1.next())
                      {
                          map.put("mobile",dbpoolNew.getString(resultSet1,"mobile"));
                          map.put("customerCode",dbpoolNew.getString(resultSet1,"customercode"));
                          map.put("customerCname",dbpoolNew.getString(resultSet1,"customercname"));
                          map.put("sex",dbpoolNew.getString(resultSet1,"sex"));
                          map.put("birthDate",dbpoolNew.getDateTime(resultSet1,"birthdate")+"");
                          map.put("identifyType",dbpoolNew.getString(resultSet1,"identifytype"));
                          map.put("identifyNumber",dbpoolNew.getString(resultSet1,"identifynumber"));
                          map.put("occupationCode",dbpoolNew.getString(resultSet1,"occupationCode"));
                          break; 
                      }
                          resultSet1.close();
                          dbpoolNew.closePreparedStatement();
                  }
            } catch (Exception e) {
                
                e.printStackTrace();
            }finally{
                if(dbpoolNew!=null){
                    dbpoolNew.close();
                }
            }
		}else{
		String sql =    "SELECT c.mobile, " +
	        			"c.customercode,  " +
	        			"c.customercname, " +
	        			"decode(c.sex, '1', '��', '2', 'Ů') as sex, " +
	        			"c.birthdate, " +
	        			"decode(c.identifytype,'01','���֤','02','���ڱ�','03','����','04','����֤','05','��ʻִ��','06','����֤','����') as identifytype, " +
	        			"c.identifynumber, " +
	        			"c.occupationCode  " +
	        			"FROM prpcinsured b, t_spis_customer_ind c " +
	        			"where b.policyno = ? " +
	        			"and b.insuredcode = c.customercode " +
	        			"and b.insuredflag = '1' " +
	        			"and b.InsuredType = '1' ";
	    	  
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,policyNo);
		ResultSet resultSet = dbpool.executePreparedQuery();
		while(resultSet.next())
		{
			map.put("mobile",dbpool.getString(resultSet,"mobile"));
			map.put("customerCode",dbpool.getString(resultSet,"customercode"));
			map.put("customerCname",dbpool.getString(resultSet,"customercname"));
			map.put("sex",dbpool.getString(resultSet,"sex"));
			map.put("birthDate",dbpool.getDateTime(resultSet,"birthdate")+"");
			map.put("identifyType",dbpool.getString(resultSet,"identifytype"));
			map.put("identifyNumber",dbpool.getString(resultSet,"identifynumber"));
			map.put("occupationCode",dbpool.getString(resultSet,"occupationCode"));
			break;
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		}
		
		return map;
	}
	/**
	 * ��ȡ��ϵ����Ϣ���ŵ���
	 * @param dbpool
	 * @param policyNo
	 * @return
	 * @throws Exception
	 */
	private Map getCustomerInfoGroup(DbPool dbpool,String policyNo) throws Exception
	{
		Map map = new HashMap();
        
		if("1".equals(SysConfig.getProperty("CUSTOMER_DATA_SOURCE_SWITCH"))){
		    DbPool dbpoolNew = new DbPool();
		    List insuredcodeList = new ArrayList();
		    try {
                dbpoolNew.open(SysConfig.CONST_CUSTOMERDATASOURCE);
                String sql ="SELECT insuredcode from prpcinsured where policyno = ? ";
                dbpool.prepareInnerStatement(sql);
                dbpool.setString(1,policyNo);
                ResultSet resultSet = dbpool.executePreparedQuery();
                while(resultSet.next())
                {
                    insuredcodeList.add(dbpool.getString(resultSet,"insuredcode"));
                }
                resultSet.close();
                dbpool.closePreparedStatement();
                String querySql = "";
                if(insuredcodeList!=null && insuredcodeList.size()>0 ){
                    ResultSet resultSet1 = null;
                    for(int i = 0;i<insuredcodeList.size();i++){
                       querySql ="SELECT c.customercode," +
                       "c.customercname, " +
                       "c.organizecode, " +
                       "c.addresscname, " +
                       "d.linkname, " +
                       "d.linkmobile, " +
                       "d.linkphone " +
                       "FROM  t_spis_customer_unit c, T_SPIS_CUSTOMER_LINKMAN d " +
                       "where  c.customercode = ? " +
                       "and d.serialno = '1' " +
                       "and d.customercode = c.insuredcode ";
                       dbpoolNew.prepareInnerStatement(querySql);
                       dbpoolNew.setString(1,(String)insuredcodeList.get(i));
                       resultSet1 = dbpoolNew.executePreparedQuery();
                       while(resultSet1.next())
                       {
                           map.put("linkName",dbpoolNew.getString(resultSet1,"linkname"));
                           
                           map.put("mobile",dbpoolNew.getString(resultSet1,"linkmobile"));
                           map.put("linkPhone",dbpoolNew.getString(resultSet1,"linkphone"));
                           map.put("customerCode",dbpoolNew.getString(resultSet1,"customerCode"));
                           map.put("customerCname",dbpoolNew.getString(resultSet1,"customercname"));
                           map.put("organizeCode",dbpoolNew.getString(resultSet1,"organizecode"));
                           map.put("addressCname",dbpoolNew.getString(resultSet1,"addresscname"));
                           
                       }
                       resultSet1.close();
                       dbpoolNew.closePreparedStatement();
                    }
                }
            } catch (Exception e) {
                
                e.printStackTrace();
            }finally{
                if(dbpoolNew!=null){
                    dbpoolNew.close();
                }
            }
		}else{
		String sql =    "SELECT c.customercode, " +
	       				"c.customercname, " +
	       				"c.organizecode, " +
	       				"c.addresscname, " +
	       				"d.linkname, " +
	       				"d.linkmobile, " +
	       				"d.linkphone " +
	       				"FROM prpcinsured b, t_spis_customer_unit c, T_SPIS_CUSTOMER_LINKMAN d " +
	       				"where b.policyno = ?  " +
	       				"and b.insuredcode = c.customercode " +
	       				"and d.serialno = '1' " +
	       				"and d.customercode = b.insuredcode ";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,policyNo);
		ResultSet resultSet = dbpool.executePreparedQuery();
		while(resultSet.next())
		{
			map.put("linkName",dbpool.getString(resultSet,"linkname"));
			
			map.put("mobile",dbpool.getString(resultSet,"linkmobile"));
			map.put("linkPhone",dbpool.getString(resultSet,"linkphone"));
			map.put("customerCode",dbpool.getString(resultSet,"customerCode"));
			map.put("customerCname",dbpool.getString(resultSet,"customercname"));
			map.put("organizeCode",dbpool.getString(resultSet,"organizecode"));
			map.put("addressCname",dbpool.getString(resultSet,"addresscname"));
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		}
		 
		return map;
	}
	/**
	 * ����PrpCustomerMessage���messageflag��״̬
	 * @param dbpool
	 * @param sendSeq
	 * @param messageFlag
	 * @throws Exception
	 */
	public void updatePrpCustomerMessageFlagNew(DbPool dbpool,String sendSeq,String messageFlag ) 
	{
		String sql = "update PrpCustomerMessage set MessageFlag=? ,UpdateDate= sysdate,sendtime=sysdate where sendseq=? and messageflag ='-1' ";
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,messageFlag);
			dbpool.setString(2,sendSeq);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->updatePrpCustomerMessageFlagNew()->����PrpCustomerMessage���messageflag��״̬->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updatePrpCustomerMessageFlagNew", message,e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			
		}
	}
	
	/**
	 * ���Ͽ������Ѷ���  add by linqian 20131213
	 * ����PrpCustomerMessage���messageflag1��״̬
	 * @param dbpool
	 * @param sendSeq
	 * @param messageFlag
	 * @throws Exception
	 */
	public void updatePrpCustomerMessageFlagSpecific(DbPool dbpool,String sendSeqSpecific,String messageFlag1 ) 
	{
		String sql = "update PrpCustomerMessage set messageFlag1=? ,sendTimeSpecific=sysdate where sendSeqSpecific=? and messageflag1='-1' ";
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,messageFlag1);
			dbpool.setString(2,sendSeqSpecific);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->updatePrpCustomerMessageFlagSpecific()->����PrpCustomerMessage���messageflag1��״̬->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updatePrpCustomerMessageFlagSpecific", message,e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			
		}
	}
	/**
	 * ����PrpCustomerMessage���messageflag��״̬
	 * @param dbpool
	 * @param sendSeq
	 * @param messageFlag
	 * @throws Exception
	 * modify by yinwenchao for ����ʧ�ܵĶ���ÿ��24Сʱ�ܹ���������
	 */
	public void updatePrpCustomerMessageFlag(DbPool dbpool,String sendSeq,String messageFlag ) 
	{
		String sql = "";
		if ("2".equals(messageFlag)) {
			sql = "update PrpCustomerMessage set MessageFlag=? ,UpdateDate= sysdate,dxfailedtimes=(1+DECODE(dxfailedtimes,NULL,0,1,1,2,2,3,3,NULL)) where sendseq=? and messageflag ='0'";
		} else {
			
			sql = "update PrpCustomerMessage set MessageFlag=? ,UpdateDate= sysdate where sendseq=? and messageflag ='0' ";
		}
		try{
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,messageFlag);
		dbpool.setString(2,sendSeq);
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->updatePrpCustomerMessageFlag()->����PrpCustomerMessage���messageflag��״̬->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updatePrpCustomerMessageFlag", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
	}
	
	/**
	 * ����PrpCustomerMessage���messageflag��״̬
	 * @param dbpool
	 * @param sendSeq
	 * @param messageFlag
	 * @throws Exception
	 * ����ʧ�ܵĶ���ÿ��24Сʱ�ܹ���������
	 */
	public void updatePrpCustomerMessageFlag1(DbPool dbpool,String sendSeq,String messageFlag ) 
	{
		String sql = "";
		if ("2".equals(messageFlag)) {
			sql = "update PrpCustomerMessage set MessageFlag1=? ,dxfailedtimesSpecific=(1+DECODE(dxfailedtimesSpecific,NULL,0,1,1,2,2,3,3,NULL)) where sendseqSpecific=? and messageflag1='0'";
		} else {
			sql = "update PrpCustomerMessage set MessageFlag1=? where sendseqSpecific=? and messageflag1='0' ";
		}
		try{
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,messageFlag);
		dbpool.setString(2,sendSeq);
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->updatePrpCustomerMessageFlag1()->����PrpCustomerMessage���messageflag1��״̬->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updatePrpCustomerMessageFlag1", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
	}
	

	
	
	/**
	 * ��������PrpCustomerMessage
	 * @param dbpool
	 * @throws Exception 
	 */
	public List getPrpCustomerMessageOtherInfo(DbPool dbpool) throws Exception
	{
		List list = new ArrayList();
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		
		String sql = "select * from PrpCustomerMessage where MESSAGETYPE='1' " +
			 	     "and (PhoneVisitFlag='2' or(PhoneVisitFlag = '1' and GXCondition= '1' )) and rownum<1001 and SURVEYORHANDLCOMCODE1 is null and  createddate > sysdate-60 ";
		ResultSet resultSet = dbpool.query(sql);
		while(resultSet.next())
		{
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
			prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
			prpCustomerMessageSchema.setCustomerCode(dbpool.getString(resultSet,"CustomerCode"));
			prpCustomerMessageSchema.setOperatorCode(dbpool.getString(resultSet,"OperatorCode"));
			prpCustomerMessageSchema.setComCode(dbpool.getString(resultSet,"ComCode"));
			prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
			prpCustomerMessageSchema.setOccupationName(dbpool.getString(resultSet,"OccupationName"));
			prpCustomerMessageSchema.setSurveyOrHandlId(dbpool.getString(resultSet,"SurveyOrHandlId"));
			prpCustomerMessageSchema.setBusinessNature(dbpool.getString(resultSet,"BusinessNature"));
			prpCustomerMessageSchema = completeInfo(dbpool,prpCustomerMessageSchema);
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		return list;
	}
	
	/**
	 * ��������PrpCustomerMessage
	 * @param dbpool
	 * @throws Exception 
	 */
	public List getPrpCustomerMessageOtherInfoGX(DbPool dbpool) throws Exception
	{
		List list = new ArrayList();
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		
		String sql = "select * from PrpCustomerMessage where MESSAGETYPE='1' " +
			 	     "and PhoneVisitFlag = '1' and GXCondition= '1' and rownum<1001 and " +
			 	     "SURVEYORHANDLCOMCODE1 is null and  createddate > sysdate-60 ";
		ResultSet resultSet = dbpool.query(sql);
		while(resultSet.next())
		{
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
			prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
			prpCustomerMessageSchema.setCustomerCode(dbpool.getString(resultSet,"CustomerCode"));
			prpCustomerMessageSchema.setOperatorCode(dbpool.getString(resultSet,"OperatorCode"));
			prpCustomerMessageSchema.setComCode(dbpool.getString(resultSet,"ComCode"));
			prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
			prpCustomerMessageSchema.setOccupationName(dbpool.getString(resultSet,"OccupationName"));
			prpCustomerMessageSchema.setSurveyOrHandlId(dbpool.getString(resultSet,"SurveyOrHandlId"));
			prpCustomerMessageSchema.setBusinessNature(dbpool.getString(resultSet,"BusinessNature"));
			prpCustomerMessageSchema = completeInfo(dbpool,prpCustomerMessageSchema);
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		return list;
	}
	/**
	 * @param dbpool
	 * @param prpCustomerMessageSchema
	 * @return
	 * @throws Exception 
	 */
	private PrpCustomerMessageSchema completeInfo(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema) throws Exception
	{
		
		if("0".equals(prpCustomerMessageSchema.getGroupFlag()))
		{
			String temp = "";
			
			temp = getPhone(dbpool,prpCustomerMessageSchema.getCustomerCode());
			prpCustomerMessageSchema.setPhone(temp);
			
			temp = getAddess(dbpool,prpCustomerMessageSchema.getCustomerCode());
			prpCustomerMessageSchema.setAddress(temp);
			
			temp = getCodeName(dbpool,prpCustomerMessageSchema.getOccupationName(),"OccupationCode");
			prpCustomerMessageSchema.setOccupationName(temp);
		}
		
		Map map = getComCode(dbpool,prpCustomerMessageSchema.getPolicyNo());
		prpCustomerMessageSchema.setCenterOrHandlComCode((String)map.get("handlercom"));
		prpCustomerMessageSchema.setComCode((String)map.get("comcode"));
		prpCustomerMessageSchema.setComName((String)map.get("comcname"));
		prpCustomerMessageSchema.setSurveyOrHandlId((String)map.get("handler1code"));
		prpCustomerMessageSchema.setSurveyOrHandlName((String)map.get("handler"));
		prpCustomerMessageSchema.setOperatorCode((String)map.get("operatorcode"));
		prpCustomerMessageSchema.setOperatorName((String)map.get("operator"));
		prpCustomerMessageSchema.setChannelType((String)map.get("channelType"));
		
		String handlercom1 = "";
		String comname1 = "";
		String handlercom2 = "";
		String comname2 = "";
		String comCode = (String)map.get("comcode");
		String comname = queryPrpDcompnay(dbpool,(String)map.get("handlercom"));
		if(comCode!=null && comCode.length()==8)
		{
			handlercom1 = comCode.substring(0,2)+"000000";
			comname1 = queryPrpDcompnay(dbpool,handlercom1);
			handlercom2 = comCode.substring(0,4)+"0000";
			comname2 = queryPrpDcompnay(dbpool,handlercom2);
		}
		prpCustomerMessageSchema.setCenterOrHandlComName(comname);
		prpCustomerMessageSchema.setSurveyOrHandlComCode1(handlercom1);
		prpCustomerMessageSchema.setSurveyOrHandlComName1(comname1);
		prpCustomerMessageSchema.setSurveyOrHandlComCode2(handlercom2);
		prpCustomerMessageSchema.setSurveyOrHandlComName2(comname2);
		
		prpCustomerMessageSchema.setBusinessNatureName(getCodeName(dbpool,prpCustomerMessageSchema.getBusinessNature(),"BusinessNature"));
		return prpCustomerMessageSchema;
	}
	/**
	 * ��ȡ���루XXXXX��
	 * @param dbpool
	 * @param customerCode
	 * @return
	 * @throws Exception
	 */
	private String getPhone(DbPool dbpool,String customerCode) throws Exception
	{
		String phoneNumber = "";
		String sql = "select * from T_SPIS_CUSTOMER_PHONE where customercode=? and serialno = '1' ";
	    
		ResultSet resultSet = null;
        DbPool dbpoolNew = new DbPool();
		try {
		    if("1".equals(SysConfig.getProperty("CUSTOMER_DATA_SOURCE_SWITCH"))){
		        dbpoolNew.open(SysConfig.CONST_CUSTOMERDATASOURCE);
		        dbpoolNew.prepareInnerStatement(sql);
		        dbpoolNew.setString(1,customerCode);
	            resultSet = dbpoolNew.executePreparedQuery();
	            while(resultSet.next())
	            {
	                phoneNumber = dbpoolNew.getString(resultSet,"phonenumber");
	            }
	            dbpoolNew.closePreparedStatement();
		    }else{
		        dbpool.prepareInnerStatement(sql);
		        dbpool.setString(1,customerCode);
		        resultSet = dbpool.executePreparedQuery();
                while(resultSet.next())
                {
                	phoneNumber = dbpool.getString(resultSet,"phonenumber");
                }
		    }
            resultSet.close();
        } catch (Exception e) {
            
            e.printStackTrace();
            throw e;
        }finally {
            dbpoolNew.close();
        }
		
		dbpool.closePreparedStatement();
		return phoneNumber;
	}
	/**
	 * ��ȡ��ַ��XXXXX��
	 * @param dbpool
	 * @param customerCode
	 * @return
	 * @throws Exception
	 */
	private String getAddess(DbPool dbpool,String customerCode) throws Exception
	{
		String addressName = "";
		String sql = "select * from T_SPIS_CUSTOMER_LINKADDRESS where customercode=? and serialno = '1' ";
		ResultSet resultSet = null; 
		DbPool dbpoolNew = new DbPool();
		
		try {
            if("1".equals(SysConfig.getProperty("CUSTOMER_DATA_SOURCE_SWITCH"))){
                dbpoolNew.open(SysConfig.CONST_CUSTOMERDATASOURCE);
                dbpoolNew.prepareInnerStatement(sql);
                dbpoolNew.setString(1,customerCode);
                resultSet = dbpoolNew.executePreparedQuery();
                while(resultSet.next())
                {
                    addressName = dbpoolNew.getString(resultSet,"addressname");
                }
                dbpoolNew.closePreparedStatement();
            }else{
                
                dbpool.prepareInnerStatement(sql);
                dbpool.setString(1,customerCode);
                resultSet = dbpool.executePreparedQuery();
                while(resultSet.next())
                {
                	addressName = dbpool.getString(resultSet,"addressname");
                }
            }
            resultSet.close();
        } catch (Exception e) {
            
            e.printStackTrace();
            throw e;
        }finally{
            dbpoolNew.close();
        }
		dbpool.closePreparedStatement();
		return addressName;
	}
	/**
	 * ��ȡְҵ��XXXXX��/ ҵ����Դ����
	 * @param dbpool
	 * @param customerCode
	 * @return
	 * @throws Exception
	 */
	private String getCodeName(DbPool dbpool,String occupationCode,String codeType) throws Exception
	{
		String codeCname = "";
		String sql = "select * from prpdcode where codecode=? and codetype = ? ";
        
        ResultSet resultSet = null;
        DbPool dbpoolNew = new DbPool();
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpoolNew.open("platformNewDataSource");
                dbpoolNew.prepareInnerStatement(sql);
                dbpoolNew.setString(1,occupationCode);
                dbpoolNew.setString(2,codeType);
        		resultSet = dbpoolNew.executePreparedQuery();
            } else {
        		dbpool.prepareInnerStatement(sql);
        		dbpool.setString(1,occupationCode);
        		dbpool.setString(2,codeType);
        		resultSet = dbpool.executePreparedQuery();
            }
		    while(resultSet.next())
		    {
			    codeCname = dbpool.getString(resultSet,"codecname");
		    }
		resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            dbpoolNew.close();
        }
        
		dbpool.closePreparedStatement();
		return codeCname;
	}
	/**
	 * ���ݻ�������ȡ��������
	 * @param dbpool
	 * @param comCode
	 * @return
	 * @throws Exception
	 */
	private String  queryPrpDcompnay(DbPool dbpool,String comCode)  throws Exception
	{
		String comName = "";
		String sql = "select * from prpdcompany where comcode=? ";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,comCode);
		ResultSet resultSet = dbpool.executePreparedQuery();
		while(resultSet.next())
		{
			comName = dbpool.getString(resultSet,"comcname");
		}
		return comName;
	}
	/**
	 * ��ȡ������
	 * @param dbpool
	 * @param handler1Code
	 * @return
	 * @throws Exception
	 */
	private Map getComCode(DbPool dbpool,String policyNo) throws Exception
	{
		Map map = new HashMap();
















		String sql =   "SELECT " +
				       "e.comcode as HANDLERCOM," +
				       "a.comcode," +
				       "h.comcname," +
				       "a.handler1code," +
				       "a.operatorcode," +
				       "e.username as handler," +
				       "g.username as operator, " +
				       "a.channeltype " +
				       "FROM prpcmain a ,prpduser e, prpduser g, prpdcompany h " +
				       "where a.handler1code=e.usercode  and a.operatorcode = g.usercode " +
				       "and a.comcode = h.comcode and a.policyno = ? ";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,policyNo);
		ResultSet resultSet = dbpool.executePreparedQuery();
		while(resultSet.next())
		{
			map.put("handlercom",dbpool.getString(resultSet,"HANDLERCOM"));
			map.put("comcode",dbpool.getString(resultSet,"comcode"));
			map.put("comcname",dbpool.getString(resultSet,"comcname"));
			map.put("handler1code",dbpool.getString(resultSet,"handler1code"));
			map.put("operatorcode",dbpool.getString(resultSet,"operatorcode"));
			map.put("handler",dbpool.getString(resultSet,"handler"));
			map.put("operator",dbpool.getString(resultSet,"operator"));
			map.put("channelType",dbpool.getString(resultSet,"channeltype"));
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return map;
	}
	/**
	 * ������Ϣ
	 * @param dbpool
	 * @param prpCustomerMessageSchema
	 * @throws Exception
	 */
	public void completePrpCustomerMessage(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema)  
	{
		String sql = "";
		
		if("0".equals(prpCustomerMessageSchema.getGroupFlag()))
		{
			sql = 		"update PrpCustomerMessage set " +
			 			"SurveyOrHandlComCode1=?," +
			 			"SurveyOrHandlComName1=?," +
			 			"SurveyOrHandlComCode2=?," +
			 			"SurveyOrHandlComName2=?," +
			 			"CenterOrHandlComCode=?," +
			 			"CenterOrHandlComName=?," +
			 			"ComCode=?," +
			 			"ComName=? , " +
			 			"SurveyOrHandlId=?," +
			 			"SurveyOrHandlName=?," +
			 			"OperatorCode=?," +
			 			"OperatorName=?," +
			 			"BusinessNatureName=?," +
			 			"channelType=?," +
			 			"UpdateDate= sysdate, " +
			 			"phone=?," +
			 			"Address=?," +
			 			"OccupationName=? " +
			 			 "where id=? ";
		}
		
	    if("1".equals(prpCustomerMessageSchema.getGroupFlag()))
	    {
	    	sql =    "update PrpCustomerMessage set " +
			    	 "SurveyOrHandlComCode1=?," +
		 			 "SurveyOrHandlComName1=?," +
		 			 "SurveyOrHandlComCode2=?," +
		 			 "SurveyOrHandlComName2=?," +
		 			 "CenterOrHandlComCode=?," +
		 			 "CenterOrHandlComName=?," +
		 			 "ComCode=?," +
		 			 "ComName=? , " +
		 			 "SurveyOrHandlId=?," +
		 			 "SurveyOrHandlName=?," +
		 			 "OperatorCode=?," +
		 			 "OperatorName=?," +
		 			 "BusinessNatureName=?," +
		 			 "channelType=?," +
		 			 "UpdateDate= sysdate " +
						 "where id=? ";
	    }
		try{
		dbpool.prepareInnerStatement(sql);
		int j = 0;
		dbpool.setString(++j,prpCustomerMessageSchema.getSurveyOrHandlComCode1());
		dbpool.setString(++j,prpCustomerMessageSchema.getSurveyOrHandlComName1());
		dbpool.setString(++j,prpCustomerMessageSchema.getSurveyOrHandlComCode2());
		dbpool.setString(++j,prpCustomerMessageSchema.getSurveyOrHandlComName2());
		dbpool.setString(++j,prpCustomerMessageSchema.getCenterOrHandlComCode());
		dbpool.setString(++j,prpCustomerMessageSchema.getCenterOrHandlComName());
		dbpool.setString(++j,prpCustomerMessageSchema.getComCode());
		dbpool.setString(++j,prpCustomerMessageSchema.getComName());
		dbpool.setString(++j,prpCustomerMessageSchema.getSurveyOrHandlId());
		dbpool.setString(++j,prpCustomerMessageSchema.getSurveyOrHandlName());
		dbpool.setString(++j,prpCustomerMessageSchema.getOperatorCode());
		dbpool.setString(++j,prpCustomerMessageSchema.getOperatorName());
		dbpool.setString(++j,prpCustomerMessageSchema.getBusinessNatureName());
		dbpool.setString(++j,prpCustomerMessageSchema.getChannelType());
		if("0".equals(prpCustomerMessageSchema.getGroupFlag()))
		{
			dbpool.setString(++j,prpCustomerMessageSchema.getPhone());
			dbpool.setString(++j,prpCustomerMessageSchema.getAddress());
			dbpool.setString(++j,prpCustomerMessageSchema.getOccupationName());
				dbpool.setString(++j,prpCustomerMessageSchema.getId());
		}
		if("1".equals(prpCustomerMessageSchema.getGroupFlag()))
		{
				dbpool.setString(++j,prpCustomerMessageSchema.getId());
		}
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->completePrpCustomerMessage()->������Ϣ->"+e.getMessage();
			TaskMngUtil.insertMidDataLog(prpCustomerMessageSchema.getId(), message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
	}
	/**
	 * ��ȡ���ͳɹ�״̬
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getSenList(DbPool dbpool) throws Exception
	{
		List list = new ArrayList();
		


		String sql =  "SELECT * FROM (select to_char(t.createddate,'YYYY-MM-DD HH24:MI:SS') createddate1,t.* from prpCustomerMessage t where t.messageFlag='0' and t.IsRepeatPhone='0' and t.PhoneVisitFlag='2' " +
		              " and t.createddate > sysdate-60  and t.createddate <  sysdate-1/12  order by t.createddate asc) where rownum<3001 ";
		logger.info("��ȡ���ͳɹ�״̬(���϶�ʱ)sql: "+sql);
		ResultSet resultSet = dbpool.query(sql);
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		while(resultSet.next())
		{
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setSendSeq(dbpool.getString(resultSet,"SENDSEQ"));
			prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
			prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
			prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			prpCustomerMessageSchema.setMessageFlag(dbpool.getString(resultSet,"MessageFlag"));
			prpCustomerMessageSchema.setCreatedDate(dbpool.getString(resultSet,"CreatedDate1"));
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		return list;
	}
	
	/**
	 * ���Ͽ����ȡ���ͳɹ�״̬
	 * add by linqian 20131216
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getSenListSpecific(DbPool dbpool) throws Exception
	{
		List list = new ArrayList();
		


		String sql =  "SELECT * FROM (select to_char(t.createddate,'YYYY-MM-DD HH24:MI:SS') createddate1,t.* from prpCustomerMessage t where t.messageFlag1='0' and t.IsRepeatPhone='0' " +
		              " and t.createddate > sysdate-60  and t.createddate <  sysdate-1/12  order by t.createddate asc) where rownum<3001 ";
		logger.info("��ȡ���ͳɹ�״̬(���϶�ʱ)sql: "+sql);
		ResultSet resultSet = dbpool.query(sql);
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		while(resultSet.next())
		{
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setSendSeqSpecific(dbpool.getString(resultSet,"SENDSEQSpecific"));
			prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
			prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
			prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			prpCustomerMessageSchema.setMessageFlag1(dbpool.getString(resultSet,"MessageFlag1"));
			prpCustomerMessageSchema.setCreatedDate(dbpool.getString(resultSet,"CreatedDate1"));
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		return list;
	}
	
	
	/**
	 * ��ȡ���ͳɹ�״̬��ʱ��Σ�
	 * @param dbpool
	 * @param strBDate
	 * @param strNDate
	 * @return
	 * @throws Exception
	 */
	public List getDateSenList(DbPool dbpool,String strBDate,String strNDate) throws Exception
	{
		List list = new ArrayList();
		
		String sql = "select * from prpCustomerMessage where messageFlag='0' and IsRepeatPhone='0' and PhoneVisitFlag='2' and rownum<5001 " +
				     "and createddate  " +
					 "between to_date('"+strBDate+" 00:00:00','YYYY-MM-DD HH24:MI:SS') " +
					 "and to_date('"+strNDate+" 23:59:59','YYYY-MM-DD HH24:MI:SS') ";
		logger.info("��ȡ���ͳɹ�״̬��ʱ��Σ�"+sql);
		ResultSet resultSet = dbpool.query(sql);
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		while(resultSet.next())
		{
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setSendSeq(dbpool.getString(resultSet,"SENDSEQ"));
			prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
			prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
			prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			prpCustomerMessageSchema.setMessageFlag(dbpool.getString(resultSet,"MessageFlag"));
			prpCustomerMessageSchema.setCreatedDate(dbpool.getString(resultSet,"CreatedDate"));
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		return list;
	}
	
	/**
	 * ���Ͽ����ȡ���ͳɹ�״̬��ʱ��Σ�
	 * add by linqian 20131216
	 * @param dbpool
	 * @param strBDate
	 * @param strNDate
	 * @return
	 * @throws Exception
	 */
	public List getDateSenListSpecific(DbPool dbpool,String strBDate,String strNDate) throws Exception
	{
		List list = new ArrayList();
		
		String sql = "select * from prpCustomerMessage where messageFlag1='0' and IsRepeatPhone='0' and rownum<5001 " +
				     "and createddate  " +
					 "between to_date('"+strBDate+" 00:00:00','YYYY-MM-DD HH24:MI:SS') " +
					 "and to_date('"+strNDate+" 23:59:59','YYYY-MM-DD HH24:MI:SS') ";
		logger.info("��ȡ���ͳɹ�״̬��ʱ��Σ�"+sql);
		ResultSet resultSet = dbpool.query(sql);
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		while(resultSet.next())
		{
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setSendSeqSpecific(dbpool.getString(resultSet,"SENDSEQSPECIFIC"));
			prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
			prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
			prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			prpCustomerMessageSchema.setMessageFlag1(dbpool.getString(resultSet,"MessageFlag1"));
			prpCustomerMessageSchema.setCreatedDate(dbpool.getString(resultSet,"CreatedDate"));
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		return list;
	}
	
	/**
	 * ���Ͽ����ȡ��Ҫ�޸ĵ�ͬXXXXX״̬��ʱ��Σ�
	 * add by linqian 20131216
	 * @param dbpool
	 * @param strBDate
	 * @param strNDate
	 * @return
	 * @throws Exception
	 */
	public List getBuySimultaneity(DbPool dbpool,String messageType) throws Exception
	{
		List list = new ArrayList();
		String id="";
		
		String sql = "select t.id  " +
				"from PrpCustomerMessage t, prpcitemcar a, prpcmainSub b  " +
				"where t.messagetype =?  and t.MessageFlag1 = '-1'   and t.isrepeatphone = '0'   " +
				"and t.policyno = a.policyno   and t.policyno = b.policyno   " +
				"and a.relievingareacode = '8'   and t.riskCode != '0507'   and t.comCode like '0651%'  " +
				" and t.createddate > sysdate - 6 " +
				"union   " +
				"select t.id  from PrpCustomerMessage t, prpcitemcar a, prpcmainSub b, prpcmain c " +
				"where t.messagetype =?   and t.MessageFlag1 = '-1'   and t.isrepeatphone = '0'   " +
				"and t.policyno = a.policyno   and t.policyno != b.policyno and       " +
				"(t.policyno = c.policyno and c.enddate <= sysdate and       " +
				"(select count(1) from prpcmainSub where policyno = t.policyno) = 0)   " +
				"and a.policyno = c.policyno   and a.relievingareacode = '8'   and t.riskCode != '0507'   " +
				"and t.comCode like '0651%'   and t.createddate > sysdate - 6";
		logger.info("��ȡ������id"+sql);
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1, messageType);
		dbpool.setString(2, messageType);
		ResultSet resultSet = dbpool.executePreparedQuery();
		while(resultSet.next())
		{	
			id=dbpool.getString(resultSet,"id");
			list.add(id);
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return list;
	}
	
	/**
	 * ��ȡ�����б��Ժ������
	 * @param dbpool
	 * @return
	 * @throws Exception
	 *  
	 *  
	 */
	public List getPhoneList(DbPool dbpool,String groupFlag,String messageType) throws Exception
	{
		List list = new ArrayList();
		
		String sql = "select mobile  from prpCustomerMessage where messageflag='-1' " +


		
	             "and IsRepeatPhone='2' and PhoneVisitFlag='2' and groupflag=? and messagetype=? and createddate > sysdate-60 ";









		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,groupFlag);
		dbpool.setString(2, messageType);
		ResultSet resultSet = dbpool.executePreparedQuery();
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		while(resultSet.next())
		{
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"mobile"));

			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return list;
	}
	/**
	 * �ж��Ƿ�Ϊ���ظ����룬�ں���Ϊ������ǰ���£���XXXXX���ַ���
	 * @param dbpool
	 * @param prpCustomerMessageSchema
	 * @return
	 * @throws Exception
	 * 
	 */
	public List judgeNoRepeatPhoneList(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema) throws Exception
	{
		String groupFlag = prpCustomerMessageSchema.getGroupFlag();
		String name = "";
		if(groupFlag==null)
			return null;
		if("0".equals(groupFlag))
		{
			name = "customerName";
		}
		if("1".equals(groupFlag))
		{
			name = "linkName";
		}


		String sql = "select "+name+" from  prpCustomerMessage where mobile=?  and groupflag=? and messagetype=? group by " + name;
		List list = new ArrayList();
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,prpCustomerMessageSchema.getMobile());
		dbpool.setString(2,groupFlag);
		dbpool.setString(3,prpCustomerMessageSchema.getMessageType());
		ResultSet resultSet = dbpool.executePreparedQuery();
		int count = 0;
		while(resultSet.next())
		{
			count++;
			
			
			
			
			if(count>3)
			{
				prpCustomerMessageSchema.setIsRepeatPhone("1");
				
				
				prpCustomerMessageSchema.setMessageFlag("4");
				logger.info(prpCustomerMessageSchema.getMobile()+"��Դ"+prpCustomerMessageSchema.getMessageType()+"�ظ�����1");
				list.add(prpCustomerMessageSchema);
				break;
			}
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return list;
	}
	
	/**
	 * ȡ�ط�������������10%
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public void getVisitMap(DbPool dbpool,String comCode ) throws Exception
	{
		logger.info("����������"+comCode);
		List list = visitMapDetail(comCode);
		if(list!=null && list.size()>0)
		{
			judgeVisitMap( list ,list.size());
		}
	}
	/**
	 * ���������ȡ10%�Ļط�����
	 * modify by yinwenchao at 20120417 for riskcode ='0507'��Ϊ'0508'����ҵXXXXX�����������ݣ���ǿXXXXXһ�㲻�����2000Ԫ��ǰ���޳���Ͼ�û�н�ǿXXXXX����.
	 * modify by yinwenchao at 20120711 for (messagetype='1' AND riskcode = '0508')��Ϊ(messagetype='1')���������ǿ��0509��ȡ���������ſ�������.
	 * @param dbpool
	 * @param comCode
	 * @param count
	 * @return
	 * @throws Exception
	 */
	private List visitMapDetail(String comCode ) throws  Exception
	{
		
		String sql = "select * from PrpCustomerMessage where messageflag='1' and PhoneVisitFlag='2' and IsRepeatPhone='0' and SURVEYORHANDLCOMCODE1=?" +
				     " and createddate > sysdate-59 and ((messagetype='1') OR (messagetype='2' AND riskcode='0507')) and groupflag='0' ";





		List list = new ArrayList();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,comCode);
		ResultSet resultSet = dbpool.executePreparedQuery();
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		while(resultSet.next())
		{
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
				prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
			prpCustomerMessageSchema.setSurveyOrHandlComCode1(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
			prpCustomerMessageSchema.setCustomerCode(dbpool.getString(resultSet,"customercode"));
				prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
		        prpCustomerMessageSchema.setPhoneVisitFlag("0");	      
				prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"MessageType"));
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->visitMapDetail()->���������ȡ10%�Ļط�����->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.visitMapDetail", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return list;  
	}
	/**
	 * ���������ȡ10%�Ļط�����
	 * @param dbpool
	 * @param count
	 * @return
	 * @throws Exception
	 */
	private void judgeVisitMap(List list,int count) throws  Exception
	{
		if(list==null ||list.size()<1)
			return  ;
		Random r = new Random();   
		int result = 1;
		
		
		double percent = 10;
		int getCount = (int) Math.ceil(count/percent);
		
		int randomId = 0;  
		if(getCount<1)
		{
			getCount = getCount + 1;
		}
		logger.info("������(����10���ģ�ȡ1���ط�)��"+count+"ѭ��������"+getCount);
		BLPrpCustomerMessageMobile blPrpCustomerMessageMobile = new BLPrpCustomerMessageMobile();
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		PrpCustomerMessageMobileSchema prpCustomerMessageMobileSchema = null;
		Set set = new HashSet();
		
		
    	while(true) 
    	{   
    		result = 1; 
    		randomId = Math.abs(r.nextInt())% count;
    		set.add(new Integer(randomId));
    		prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(randomId);
    		logger.info("������"+prpCustomerMessageSchema.getSurveyOrHandlComCode1()+"   �������" + randomId);
    		logger.info("��һ���ж��Ƿ���Ҫ�طõ�XX�ţ�"+prpCustomerMessageSchema.getPolicyNo()+"  id�ţ�"+prpCustomerMessageSchema.getId()+" �ֻ��ţ�"+prpCustomerMessageSchema.getMobile());
    		prpCustomerMessageMobileSchema = new PrpCustomerMessageMobileSchema();
    		prpCustomerMessageMobileSchema.setMobile(prpCustomerMessageSchema.getMobile());
    		prpCustomerMessageMobileSchema.setMessageType(prpCustomerMessageSchema.getMessageType());
    		prpCustomerMessageMobileSchema.setId(prpCustomerMessageSchema.getId());
    		
    		result = blPrpCustomerMessageMobile.insertPrpCustomerMessageMobile(prpCustomerMessageMobileSchema);
    		if(result==0)
    		{
    			logger.info("id�ţ�"+prpCustomerMessageSchema.getId()+" �ֻ��ţ�"+prpCustomerMessageSchema.getMobile()+" ��Ҫ�طã��Ѳ���XXXXX�ֻ���");
    		}
    		if(set.size()>=getCount)
    		{
    			logger.info("�Ѿ�ѭ������"+set.size() + ",��Ӧѭ������" + getCount +"���˳������ѭ����");
    			break;
    		}
    	}
	}
	/**
	 * ���»ط�����
	 * @param dbpool
	 * @param prpCustomerMessageSchema
	 * @throws Exception
	 */
	public void updatePrpCustomerVisitStatues(DbPool dbpool,String id)  
	{
		String sql = "update PrpCustomerMessage set PhoneVisitFlag='1' ,UpdateDate= sysdate ,exportflag='1' where id=? ";
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,id);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->updatePrpCustomerVisitStatues()->���»ط�����->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updatePrpCustomerVisitStatues", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
	}
	
	/**
	 * ��ȡ��Ҫ������Ϣ�ĵ�������
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public int getCount(DbPool dbpool,String sql) throws  Exception
	{
		int count = 0;
		ResultSet resultSet = dbpool.query(sql);
		while(resultSet.next())
		{
			count = Integer.parseInt(dbpool.getString(resultSet,"count(*)"));
		}
		resultSet.close();
		return count;
	}
	
	/**
	 * ���º���֣��ͬXXXXX������״̬
	 * add by linqian 20140102
	 * @param dbpool
	 * @throws Exception
	 */
	public void updateBuySimultaneity(DbPool dbpool, String id)
			throws Exception {
		String sql = "update PrpCustomerMessage set messageFlag1='9' where id=? and isrepeatphone='0' and comCode like '0651%'";
		try {
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1, id);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			String message = "DBPrpCustomerMessage->updateBuySimultaneity()->����ͬXXXXX�����͵Ķ���״̬->"
					+ e.getMessage();
			TaskMngUtil.insertMidDataLog(
					"DBPrpCustomerMessage.updateBuySimultaneity", message, e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
		}
	}
	/**
	 * ���·��ظ�����״̬
	 * @param dbpool
	 * @throws Exception
	 */
	public void updatePrpCustomerNoRepeatMoblieStatues(DbPool dbpool) throws  Exception
	{
		String sql = "update PrpCustomerMessage set IsRepeatPhone='0',UpdateDate= sysdate where messageflag in('-1','5') and IsRepeatPhone='2' " +
				     "and PhoneVisitFlag='2' and createddate > sysdate-60 ";
		dbpool.update(sql);
	}
	
	/**
	 *���·��ظ�����״̬ 
	 * @param dbpool
	 * @param prpCustomerMessageSchema
	 * @throws Exception
	 * 
	 */
	public void updatePrpCustomerMoblieStatues(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema) throws Exception
	{   logger.info("updatePrpCustomerMoblieStatues->�����ظ�����->�ֻ���:"+prpCustomerMessageSchema.getMobile()+"��ʼ");
		String sql = "update PrpCustomerMessage set IsRepeatPhone=? ,messageflag=?, UpdateDate= sysdate where mobile=? and messageflag='-1' and IsRepeatPhone='2' " +
				     "and PhoneVisitFlag='2' and groupflag=? and messageType=? and createddate > sysdate-10";
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,prpCustomerMessageSchema.getIsRepeatPhone());
			dbpool.setString(2,prpCustomerMessageSchema.getMessageFlag());
			
			dbpool.setString(3,prpCustomerMessageSchema.getMobile());
			
			dbpool.setString(4,prpCustomerMessageSchema.getGroupFlag());
			dbpool.setString(5,prpCustomerMessageSchema.getMessageType());
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->updatePrpCustomerMoblieStatues()->���·��ظ�����״̬->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updatePrpCustomerMoblieStatues", message,e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			
		}
		 logger.info("updatePrpCustomerMoblieStatues->�����ظ�����->�ֻ���:"+prpCustomerMessageSchema.getMobile()+"����");
	}
	/**
	 * ��ȡ��������
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getLevelTwoComCode(DbPool dbpool) throws  Exception
	{
		String sql = "select * from prpdcompany where comcode like '%000000' ";
		List list = new ArrayList();
		String comCode = "";
		ResultSet resultSet = dbpool.query(sql);
		while(resultSet.next())
		{
			comCode = dbpool.getString(resultSet,"comcode");
			list.add(comCode);
		}
		resultSet.close();
		return list;
	}
	/**
	 * ���²���ط�����״̬
	 * modify by yinwenchao at 20120413 for XXXXX֤ÿ����Ȼ�¸�������ط�����3�����ѿ��Իطõ�û���е�90%������Ϊ3��������ȡ��
	 * @param dbpool
	 * @throws Exception 
	 * @throws  Exception 
	 */
	public void updatePrpCustomerNoVisitStatues(DbPool dbpool) throws  Exception
	{
		String sql = "update PrpCustomerMessage set PhoneVisitFlag='3' where PhoneVisitFlag='2' and IsRepeatPhone='0' and messageflag='1' " +
				     "and createddate > sysdate-60 ";
		dbpool.update(sql);
	}
	
	/**
	 * ���²���ط�����״̬
	 * add by linqian 20141216 ����Ϊ��Ҫ����
	 * @param dbpool
	 * @throws Exception 
	 * @throws  Exception 
	 */
	public void updatePrpCustomerGXVisitStatues(DbPool dbpool) throws  Exception
	{
		String sql = "update PrpCustomerMessage set  UpdateDate= sysdate ,exportflag='1' where PhoneVisitFlag='1' and messageflag='1' and " +
				     "messagetype='1'  " +
				     "and GXCondition='1'  " +
				     "and exportflag='0' " +
				     "and createddate > sysdate-60 ";
		dbpool.update(sql);
	}
	/**
	 * ���²���ط�����״̬���Ŷӣ�XX����������XX�������� modify by yinwenchao at 2011-08-15 for ����XX100%�طã�XXXXX��ʵ��ɸ�������������� N07
	 *                                              modify by yinwenchao at 2011-10-19 for ���»طõ������� N07
	 *                                              modify by yinwenchao at 2012-03-15 for XXXXX�������������XXXXX����
	 *                                              modify by yinwenchao at 2012-04-09 for ���ӳ��̡����������Ļط����ݣ�
	 *                                                                  ��XX��Ϣ��ȡҪ�����㵱��XXXX��2000Ԫ��
	 *                                                                  XXXXX��Ϣ��ȡҪ�����㵱���XXXXX������2�Ρ�
	 * @param dbpool
	 * @throws Exception 
	 * @throws  Exception 
	 */
	public void updatePrpCustomerGroupChannel(DbPool dbpool) throws  Exception
	{



		String sql = "update PrpCustomerMessage set PhoneVisitFlag='0' where PhoneVisitFlag='2' and IsRepeatPhone='0' and messageflag='1' " +
	     "and createddate > sysdate-15 and (groupflag='1' or (messagetype='1' and groupflag='0' and (SELECT P.SUMPREMIUM FROM PRPCMAIN P WHERE P.POLICYNO = PRPCUSTOMERMESSAGE.POLICYNO) < 2000) " +
	     "OR (MESSAGETYPE = '2' and groupflag='0' AND (TO_CHAR(DAMAGEDATE, 'yyyy') != TO_CHAR(SYSDATE, 'yyyy') or (SELECT COUNT(C.CLAIMNO) FROM C_CLAIM C WHERE C.POLICYNO = RPAD(PRPCUSTOMERMESSAGE.POLICYNO, 22, ' ')) > 2)))";
		dbpool.update(sql);
	}
	/**
	 * ���µ�����־
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	public void updatePrpCustomerMessageExportFlag(DbPool dbpool,String id,String exportFlag) 
	{
		String sql = "update PrpCustomerMessage set exportflag=? ,UpdateDate= sysdate  where id=? ";
		try{
			logger.info("�����Ƿ�д��95510�ӿڱ�----------id�ţ�"+id+"----״̬��"+exportFlag);
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,exportFlag);
			dbpool.setString(2,id);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			logger.error(e+"����PrpCustomerMessage������־ExportFlag�����id�ţ�"+id);
			
			String message = "DBPrpCustomerMessage->updatePrpCustomerMessageExportFlag()->���µ�����־->"+e.getMessage();
			TaskMngUtil.insertMidDataLog(id, message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
	}
	/**
	 * �ж��ظ�
	 * @param dbpool
	 * @param policyNo
	 * @return
	 * @throws Exception
	 */
	public int getCountPolicy(DbPool dbpool,String policyNo) throws  Exception
	{
		int count = 0;
		String sql = "SELECT count(*) FROM PrpCustomerMessage WHERE policyno =? and messagetype='1' and createddate > sysdate-365";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,policyNo);
		ResultSet resultSet = dbpool.executePreparedQuery();
		while(resultSet.next())
		{
			count = Integer.parseInt(dbpool.getString(resultSet,"count(*)"));
		}
		dbpool.closePreparedStatement();
		return count;
	}
	
	/**
	 * �ж��ظ� ������
	 * @param dbpool
	 * @param policyNo
	 * @return
	 * @throws Exception
	 */
	public int getCountBusinessNo(DbPool dbpool,String policyNo) throws  Exception
	{
		int count = 0;
		String sql = "SELECT count(*) FROM PrpCustomerMessage WHERE businessno =? and messagetype='1' and createddate > sysdate-365";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,policyNo);
		ResultSet resultSet = dbpool.executePreparedQuery();
		while(resultSet.next())
		{
			count = Integer.parseInt(dbpool.getString(resultSet,"count(*)"));
		}
		dbpool.closePreparedStatement();
		return count;
	}
	/**
	 * ���ظ����ŵ��ֻ��������ͳ�Ƹ���
	 * 
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getGroupPhoneList(DbPool dbpool,String messageType) throws  Exception
	{
		List list = new ArrayList();
		String sql = "SELECT mobile,count(*) FROM PrpCustomerMessage a where a.groupflag='1' " +
					 "and a.messageflag='-1' and messagetype=? and a.isrepeatphone='2' and a.phonevisitflag='2' group by mobile";
















		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,messageType);
		ResultSet resultSet = dbpool.executePreparedQuery();
		
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		logger.info("--���ظ����ŵ��ֻ��������ͳ�Ƹ���--"+sql);
		while(resultSet.next())
		{
			logger.info("��ʼ");
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"mobile"));
			prpCustomerMessageSchema.setNum(dbpool.getString(resultSet,"count(*)"));
			prpCustomerMessageSchema.setMessageType(messageType);
			logger.info(dbpool.getString(resultSet,"mobile")+"----"+dbpool.getString(resultSet,"count(*)"));
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return list;
	}
	
	/**
	 * ���ظ����ŵ��ֻ��������ͳ�Ƹ���
	 * add by linqian 20131231 ����XXXXX����
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getGroupPhoneListSpecific(DbPool dbpool,String messageType) throws  Exception
	{
		List list = new ArrayList();
		String sql = "SELECT mobile,count(*) FROM PrpCustomerMessage a where a.groupflag='1' " +
				 "and a.messageflag1='-1' and messageflag in('-1','5') and messagetype=? and a.isrepeatphone='2' and a.phonevisitflag='2' and comCode like '0651%' group by mobile";                                    
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,messageType);
		ResultSet resultSet = dbpool.executePreparedQuery();
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		logger.info("--����XXXXX������ظ����ŵ��ֻ��������ͳ�Ƹ���--"+sql);
		while(resultSet.next())
		{
			logger.info("��ʼ");
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"mobile"));
			prpCustomerMessageSchema.setNum(dbpool.getString(resultSet,"count(*)"));
			prpCustomerMessageSchema.setMessageType(messageType);
			logger.info(dbpool.getString(resultSet,"mobile")+"----"+dbpool.getString(resultSet,"count(*)"));
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return list;
	}
	/**
	 * �ŵ�����ϵ��ֻ��һ��������״̬ȫ��һ��
	 * @param dbpool
	 * @param prpCustomerMessageSchema
	 * modify at 20110914 for ͬʱ����GroupsendSeq����֪��ô���ŵ��Ǹ��������������ж�״̬�ġ�
	 */
	public void updateGroupPhoneMessageStatues(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema)
	{
		String sql = "update PrpCustomerMessage a set a.messageflag=? ,a.updatedate=sysdate ," +
				     "a.GroupsendSeq=(SELECT sendseq FROM (SELECT t.*,ROWNUM NO FROM PRPCUSTOMERMESSAGE t WHERE mobile=? and groupflag='1' and messageflag='-1' and t.isrepeatphone='2' and t.phonevisitflag='2' and messageType=?) WHERE  NO>?) " +
					 "where a.groupflag='1' and a.messageflag='-1' and a.isrepeatphone='2' and a.phonevisitflag='2' " +
					 "and mobile=?  and messageType=? and rownum<=?";
		logger.info("--���ظ����ŵ��ֻ�����ֻ����һ������--"+sql);
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,prpCustomerMessageSchema.getMessageFlag());
			dbpool.setString(2,prpCustomerMessageSchema.getMobile());
			dbpool.setString(3,prpCustomerMessageSchema.getMessageType());
			dbpool.setString(4,prpCustomerMessageSchema.getNum());
			dbpool.setString(5,prpCustomerMessageSchema.getMobile());
			dbpool.setString(6,prpCustomerMessageSchema.getMessageType());
			dbpool.setString(7,prpCustomerMessageSchema.getNum());
			logger.info(prpCustomerMessageSchema.getMessageFlag()+"--"+prpCustomerMessageSchema.getMobile()+"--"+prpCustomerMessageSchema.getNum());
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->updateGroupPhoneMessageStatues()->�ŵ�����ϵ��ֻ��һ��������״̬ȫ��һ��->"+e.getMessage();
			TaskMngUtil.insertMidDataLog(prpCustomerMessageSchema.getMobile(), message,e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			
		}
	}
	
	/**
	 * XX�����ŵ�����ϵ��ֻ��һ��������״̬ȫ��һ��
	 * @param dbpool
	 * @param prpCustomerMessageSchema
	 * modify at 20110914 for ͬʱ����GroupsendSeqSpecific����֪��ô���ŵ��Ǹ��������������ж�״̬�ġ�
	 */
	public void updateGroupPhoneMessageStatuesSpecific(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema)
	{  String sql = "update PrpCustomerMessage a set a.messageflag1=? ," +
		     "a.GroupsendSeqSpecific=(SELECT sendseqSpecific FROM (SELECT t.*,ROWNUM NO FROM PRPCUSTOMERMESSAGE t WHERE mobile=? and groupflag='1' and messageflag1='-1' and messageflag in('-1','5') and t.isrepeatphone='2' and t.phonevisitflag='2' and comCode like '0651%' and messageType=? ) WHERE  NO>?) " +
			 "where a.groupflag='1' and a.messageflag1='-1' and a.messageflag in('-1','5') and a.isrepeatphone='2' and a.phonevisitflag='2' and comCode like '0651%'" +
			 "and mobile=?  and messageType=? and rownum<=?";
		logger.info("--���ظ����ŵ��ֻ�����ֻ����һ������--"+sql);
		
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,prpCustomerMessageSchema.getMessageFlag1());
			dbpool.setString(2,prpCustomerMessageSchema.getMobile());
			dbpool.setString(3,prpCustomerMessageSchema.getMessageType());
			dbpool.setString(4,prpCustomerMessageSchema.getNum());
			dbpool.setString(5,prpCustomerMessageSchema.getMobile());
			dbpool.setString(6,prpCustomerMessageSchema.getMessageType());
			dbpool.setString(7,prpCustomerMessageSchema.getNum());
			logger.info(prpCustomerMessageSchema.getMessageFlag1()+"--"+prpCustomerMessageSchema.getMobile()+"--"+prpCustomerMessageSchema.getNum());
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
			
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->updateGroupPhoneMessageStatuesSpecific()->���Ͽ����ŵ�����ϵ��ֻ��һ��������״̬ȫ��һ��->"+e.getMessage();
			TaskMngUtil.insertMidDataLog(prpCustomerMessageSchema.getMobile(), message,e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			
		}
	}
	/**
	 * �ŵ���ϵ��ֻ����һ�����ţ�����״̬ȫ��һ��
	 * @param dbpool
	 * @param mobile
	 * @param messageFlag
	 * modify by yinwenchao for ����ʧ�ܵĶ���ÿ��24Сʱ�ܹ���������
	 */
	public void updateGroupMessageFlag(DbPool dbpool,String mobile,String messageFlag,String groupSendSeq)
	{


		String sql = "update PrpCustomerMessage set MessageFlag=? ,UpdateDate= sysdate,sendtime=sysdate " +

		          "where mobile=? and groupSendSeq=? and groupflag='1' ";
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,messageFlag);
			dbpool.setString(2,mobile);
			dbpool.setString(3,groupSendSeq);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->updateGroupMessageFlag()->�ŵ���ϵ��ֻ����һ�����ţ�����״̬ȫ��һ��->"+e.getMessage();
			TaskMngUtil.insertMidDataLog(mobile, message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
	}
	
	/**
	 * ����XXXXX�ŵ���ϵ��ֻ����һ�����ţ�����״̬ȫ��һ��
	 * add by linqian 20131216
	 * @param dbpool
	 * @param mobile
	 * @param messageFlag
	 */
	public void updateGroupMessageFlag1(DbPool dbpool,String mobile,String messageFlag,String groupSendSeq)
	{
		String sql = "update PrpCustomerMessage set MessageFlag1=?,sendtimespecific=sysdate " +
		          "where mobile=? and groupSendSeqSpecific=? and groupflag='1' ";
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,messageFlag);
			dbpool.setString(2,mobile);
			dbpool.setString(3,groupSendSeq);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->updateGroupMessageFlag1()->���Ͽ������Ѷ����ŵ���ϵ��ֻ����һ�����ţ�����״̬ȫ��һ��->"+e.getMessage();
			TaskMngUtil.insertMidDataLog(mobile, message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
	}
	
	/**
	 * ��XX��ϵ�˱�PrpCinsured��XXXXX���͹�ϵ������PrpCinsured�ֶεõ���1����/2��λXXXXX��
	 * @param dbpool
	 * @param mobile
	 * @param messageFlag
	 */
	public String getGroupFlag(DbPool dbpool,String policyNO)
	{
		
		String sql = "SELECT P.INSUREDTYPE FROM PRPCINSURED P WHERE P.INSUREDFLAG = '1' AND P.POLICYNO = ?";
		String groupFlag = "";
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,policyNO);
			ResultSet resultSet = dbpool.executePreparedQuery();
			while(resultSet.next())
			{
				
				String flag = dbpool.getString(resultSet,"INSUREDTYPE");
				
				if("1".equals(flag)){
					groupFlag = "0";
				}
				
				else{
					groupFlag = "1";
				}
			}
			resultSet.close();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
		}
		return groupFlag;
	}
	/**
	 * ��ȡ�Ƿ�����XXXXX��ʵ�Ա��θ�������(��������)��1������������0�ǲ���������
	 * add by linqian 20141216
	 * @param dbpool
	 * @param mobile
	 * @param messageFlag
	 */
	public String getGXCondition(DbPool dbpool,String policyNO,String businessNO)
	{
		
		String sql = "select * from PrpPrintPool a ,prpcitemcar b ,prpcinsured c where  a.Comcode like '17%' " +
				"and (a.printtype='P'  or  (a.IsEndorMobile = '1' and a.printtype='E' )) " +
				"and  a.Businessnature in('2', '3', '4', '9', 'A', 'G', 'H', 'M', 'N', 'P')  " +
				"and  c.INSUREDFLAG = '1' and c.insuredtype='1' " +
				"and  (b.carkindcode not like 'M%' and b.carkindcode not like 'J%') " +
				"and  a.classCode ='05' " +
				"and  a.POLICYNO = b.policyno  " +
				"and  a.POLICYNO = c.POLICYNO  " +
				"and  a.policyNO = ?" +
			    "and  a.businessNO = ? ";
		String GXCondition = "0";
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,policyNO);
			dbpool.setString(2,businessNO);
			ResultSet resultSet = dbpool.executePreparedQuery();
			while(resultSet.next())
			{
				
				GXCondition="1";
			}
			resultSet.close();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
		}
		return GXCondition;
	}
	/**
	 * ��XX��ϵ�˱�PrpCinsured�еõ�����XXXXX��Ϣ��groupFlag 0����/1��λXXXXX��
	 * @param dbpool
	 * @param mobile
	 * @param messageFlag
	 */
	public Map getCustomerInfoFromPrpCinsured(DbPool dbpool,String groupFlag,String policyNO)
	{
		Map map = new HashMap();
		String sql = "";
		
		if ("0".equals(groupFlag)) {
			sql = "SELECT P.MOBILE, "
					+ "P.INSUREDCODE customercode,  "
					+ "P.INSUREDNAME customercname, "
					
					
					
					
					+ "decode(P.IDENTIFYTYPE,'01','���֤','02','���ڱ�','03','����','04','����֤','05','��ʻִ��','06','����֤','����') as identifytype, "
					+ "P.IDENTIFYNUMBER, " + "P.OCCUPATIONCODE  "
					+ "FROM PRPCINSURED P "
					+ "where P.policyno = ? "
					+ "and P.INSUREDFLAG = '1' ";
		}
		
		else if ("1".equals(groupFlag)) {
			sql = "SELECT P.INSUREDCODE customercode, " +
				"P.INSUREDNAME customercname, " +
   				"P.IDENTIFYNUMBER organizecode, " +
   				"P.INSUREDADDRESS addresscname, " +
   				"P.LINKERNAME linkname, " +
   				"P.MOBILE linkmobile, " +
   				"P.PHONENUMBER linkphone " +
   				"FROM PRPCINSURED P " +
   				"where P.POLICYNO = ?  " +
   				"and P.INSUREDFLAG = '1' ";
		}

		try {
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1, policyNO);
			ResultSet resultSet = dbpool.executePreparedQuery();
			while (resultSet.next()) {
				if ("0".equals(groupFlag)) {
					map.put("mobile", dbpool.getString(resultSet, "mobile"));
					map.put("customerCode", dbpool.getString(resultSet,"customercode"));
					map.put("customerCname", dbpool.getString(resultSet,"customercname"));
					
					map.put("sex", "");
					
					map.put("birthDate", "");
					map.put("identifyType", dbpool.getString(resultSet,"identifytype"));
					map.put("identifyNumber", dbpool.getString(resultSet,"identifynumber"));
					map.put("occupationCode", dbpool.getString(resultSet,"occupationCode"));
					break;
				}
				if ("1".equals(groupFlag)) {
					map.put("linkName",dbpool.getString(resultSet,"linkname"));
					map.put("mobile",dbpool.getString(resultSet,"linkmobile"));
					map.put("linkPhone",dbpool.getString(resultSet,"linkphone"));
					map.put("customerCode",dbpool.getString(resultSet,"customerCode"));
					map.put("customerCname",dbpool.getString(resultSet,"customercname"));
					map.put("organizeCode",dbpool.getString(resultSet,"organizecode"));
					map.put("addressCname",dbpool.getString(resultSet,"addresscname"));
					break;
				}
			}
			resultSet.close();
			dbpool.closePreparedStatement();
		} catch (Exception e) {
			logger.error("XXXXX��ʵ�Դ�����Ϣ��", e);
			String message = "DBPrpCustomerMessage->getCustomerInfoFromPrpCinsured()->ȡ����XXXXX����->"+e.getMessage();
			TaskMngUtil.insertMidDataLog(policyNO, message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
		}
		return map;
	}
	
	/**
	 * ȡ�÷�����������24Сʱ���ϵ�ʧ������
	 * modify XXXXXXX�ֿ�,���Ͽ�������������� 20131212
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getFailedSendListSpecific(DbPool dbpool,String messageType) throws  Exception
	{
		List list = new ArrayList();
		
		String sql = "SELECT * FROM PrpCustomerMessage a where a.messageflag1='2' and a.dxfailedtimesSpecific is not NULL " +
				"and a.dxfailedtimesSpecific<3 AND a.SENDTIMESPECIFIC < (SYSDATE - 1) AND a.CREATEDDATE > (SYSDATE - 6) and a.comCode like '0651%' and a.messageType = ? ";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,messageType);
		ResultSet resultSet = dbpool.executePreparedQuery();
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		logger.info("--ȡ�÷�����������24Сʱ���ϵ�ʧ������--"+sql);
		while(resultSet.next())
		{
			logger.info("��ʼ");
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setSendSeqSpecific(dbpool.getString(resultSet,"SENDSEQSPECIFIC"));
			prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
			prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
			prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			prpCustomerMessageSchema.setMessageFlag1(dbpool.getString(resultSet,"MessageFlag1"));
			prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"MessageType"));
			logger.info(dbpool.getString(resultSet,"SENDSEQSPECIFIC")+"----"+dbpool.getString(resultSet,"id")+"----"+dbpool.getString(resultSet,"Mobile"));
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return list;
	}
	
	/**
	 * ȡ�÷�����������24Сʱ���ϵ�ʧ������
	 * modify XXXXXXX�ֿ� 20120105
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getFailedSendList(DbPool dbpool,String messageType) throws  Exception
	{
		List list = new ArrayList();
		
		
		
		String sql = "SELECT * FROM PrpCustomerMessage a where a.messageflag='2' and a.dxfailedtimes is not NULL " +
				"and a.dxfailedtimes<3 AND A.SENDTIME < (SYSDATE - 1) AND A.CREATEDDATE > (SYSDATE - 6) and a.messageType = ? ";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,messageType);
		ResultSet resultSet = dbpool.executePreparedQuery();
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		logger.info("--ȡ�÷�����������24Сʱ���ϵ�ʧ������--"+sql);
		while(resultSet.next())
		{
			logger.info("��ʼ");
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setSendSeq(dbpool.getString(resultSet,"SENDSEQ"));
			prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
			prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
			prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			prpCustomerMessageSchema.setMessageFlag(dbpool.getString(resultSet,"MessageFlag"));
			prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"MessageType"));
			logger.info(dbpool.getString(resultSet,"SENDSEQ")+"----"+dbpool.getString(resultSet,"id")+"----"+dbpool.getString(resultSet,"Mobile"));
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return list;
	}
	
	/**
	 * ����PrpCustomerMessage��ķ���ʧ�ܵ�����
	 * @param dbpool
	 * @param sendSeq
	 * @param messageFlag
	 * @throws Exception
	 * modify by yinwenchao for ����ʧ�ܵĶ���ÿ��24Сʱ�ܹ���������
	 */
	public void updateSendFailed(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema) 
	{
		
		String sql = "update PrpCustomerMessage t set t.sendseq=? ,t.messageflag='-1',t.phonevisitflag='2' where t.id=?";
		try{
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,prpCustomerMessageSchema.getSendSeq());
		dbpool.setString(2,prpCustomerMessageSchema.getId());
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
		
		if("1".equals(prpCustomerMessageSchema.getGroupFlag())){
			    String sql2 = "update PrpCustomerMessage t set t.GroupsendSeq=? where t.GroupsendSeq=?";
			    dbpool.prepareInnerStatement(sql2);
				dbpool.setString(1,prpCustomerMessageSchema.getSendSeq());
				dbpool.setString(2,prpCustomerMessageSchema.getGroupSendSeq());
				dbpool.executePreparedUpdate();
				dbpool.closePreparedStatement();
		}
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->updateSendFailed()->����PrpCustomerMessage��ķ���ʧ�ܵ�����->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updateSendFailed", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
	}
	
	/**
	 * ����PrpCustomerMessage��ķ���ʧ�ܵ�����
	 * @param dbpool
	 * @param sendSeqSpecific
	 * @param messageFlag1
	 * @throws Exception
	 * add by linqian 20131212����ʧ�ܵĶ���ÿ��24Сʱ�ܹ���������
	 */
	public void updateSendFailedSpecific(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema) 
	{
		
		String sql = "update PrpCustomerMessage t set t.sendseqSpecific=? ,t.messageflag1='-1' where t.id=?";
		try{
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,prpCustomerMessageSchema.getSendSeqSpecific());
		dbpool.setString(2,prpCustomerMessageSchema.getId());
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
		
		if("1".equals(prpCustomerMessageSchema.getGroupFlag())){
			    String sql2 = "update PrpCustomerMessage t set t.GroupsendSeqSpecific=? where t.GroupsendSeqSpecific=?";
			    dbpool.prepareInnerStatement(sql2);
				dbpool.setString(1,prpCustomerMessageSchema.getSendSeqSpecific());
				dbpool.setString(2,prpCustomerMessageSchema.getGroupSendSeqSpecific());
				dbpool.executePreparedUpdate();                                                                                                                                     
				dbpool.closePreparedStatement();
		}
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->updateSendFailedSpecific()->����PrpCustomerMessage��ķ���ʧ�ܵ�����->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updateSendFailedSpecific", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
	}
	
	/**
	 * �жϸ������Ƿ���3���طü�¼
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public int getChannelCount(DbPool dbpool,String channelType) throws Exception
	{
		int count = 0;
		String sql = "SELECT count(*) FROM PRPCUSTOMERMESSAGE T WHERE T.CHANNELTYPE = ? " +
			"AND t.PHONEVISITFLAG = '1' " +
			"AND T.CREATEDDATE BETWEEN TRUNC(SYSDATE, 'mm') " +
			"AND LAST_DAY(TO_DATE(TO_CHAR(SYSDATE, 'YYYY-MM-DD'), 'YYYY-MM-DD'))";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,channelType);
		ResultSet resultSet = dbpool.executePreparedQuery();
		while(resultSet.next())
		{
			count = Integer.parseInt(dbpool.getString(resultSet,"count(*)"));
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return count;
	}
	
	
	/**
	 * �жϸ������Ƿ���3���طü�¼
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public int getChannelCount(DbPool dbpool,String channelType,String messageType,String comCode) throws Exception
	{
		int count = 0;
		String sql = "SELECT count(*) FROM PRPCUSTOMERMESSAGE T WHERE T.CHANNELTYPE = ? AND T.MESSAGETYPE = ? AND T.SURVEYORHANDLCOMCODE1 = ?" +
			"AND t.PHONEVISITFLAG = '1' " +
			"AND T.CREATEDDATE BETWEEN TRUNC(SYSDATE, 'mm') " +
			"AND LAST_DAY(TO_DATE(TO_CHAR(SYSDATE, 'YYYY-MM-DD'), 'YYYY-MM-DD'))";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,channelType);
		dbpool.setString(2,messageType);
		dbpool.setString(3,comCode);
		ResultSet resultSet = dbpool.executePreparedQuery();
		while(resultSet.next())
		{
			count = Integer.parseInt(dbpool.getString(resultSet,"count(*)"));
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return count;
	}
	
	/**
	 * �жϸ������Ƿ���3���طü�¼,Ϊ���ֶ�ִ�д�����ʼ����ֹ����
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public int getChannelCountForServlet(DbPool dbpool,String channelType,String strBDate,String strNDate) throws Exception
	{
		int count = 0;
		String sql = "SELECT count(*) FROM PRPCUSTOMERMESSAGE T WHERE T.CHANNELTYPE = ? " +
			"AND t.PHONEVISITFLAG = '1' " +
			"AND T.CREATEDDATE BETWEEN TO_DATE('" +strBDate+" 00:00:00', 'YYYY-MM-DD HH24:MI:SS') "+
			"AND TO_DATE('" +strNDate+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,channelType);
		ResultSet resultSet = dbpool.executePreparedQuery();
		while(resultSet.next())
		{
			count = Integer.parseInt(dbpool.getString(resultSet,"count(*)"));
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return count;
	}
	
	
	/**
	 * �жϸ������Ƿ���3���طü�¼,Ϊ���ֶ�ִ�д�����ʼ����ֹ����
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public int getChannelCountForServlet(DbPool dbpool,String channelType,String strBDate,String strNDate,String messageType,String comCode) throws Exception
	{
		int count = 0;
		String sql = "SELECT count(*) FROM PRPCUSTOMERMESSAGE T WHERE T.CHANNELTYPE = ? AND T.MESSAGETYPE = ? AND T.SURVEYORHANDLCOMCODE1 = ? " +
			"AND t.PHONEVISITFLAG = '1' " +
			"AND T.CREATEDDATE BETWEEN TO_DATE('" +strBDate+" 00:00:00', 'YYYY-MM-DD HH24:MI:SS') "+
			"AND TO_DATE('" +strNDate+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,channelType);
		dbpool.setString(2,messageType);
		dbpool.setString(3,comCode);
		ResultSet resultSet = dbpool.executePreparedQuery();
		while(resultSet.next())
		{
			count = Integer.parseInt(dbpool.getString(resultSet,"count(*)"));
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return count;
	}
	
	/**
	 * ��ȡ��������
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getAllChannelType(DbPool dbpool) throws  Exception
	{
		String sql = "SELECT DISTINCT t.codecode FROM prpdcode t WHERE t.codetype='ChannelType'";
		List list = new ArrayList();
		String codecode = "";
        
        ResultSet resultSet = null;
        DbPool dbpoolNew = new DbPool();
        try {
            String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
            if ("1".equals(strSwitch)) {
                dbpoolNew.open("platformNewDataSource");
                resultSet = dbpoolNew.query(sql);
            } else {
        		resultSet = dbpool.query(sql);
            }
		    while(resultSet.next())
		    {
			    codecode = dbpool.getString(resultSet,"codecode");
			    list.add(codecode);
		    }
		    resultSet.close();
		} catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            dbpoolNew.close();
        }
        
		return list;
	}
	
	/**
	 * �����������ȡ�ط�����������������
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public void getChannelMap(DbPool dbpool,String channelType,int count ) throws Exception
	{
		logger.info("�貹����������룺"+channelType);
		
		List list = visitChannelMapDetail(channelType);
		
		judgeChannelVisitMap(list ,count);
	}
	
	/**
	 * �����������ȡ�ط�����������������
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public void getChannelMap(DbPool dbpool,String channelType,int count,String messageType,String comCode ) throws Exception
	{
		logger.info("�貹���������Դ:"+messageType+" ������"+comCode+" �������룺"+channelType);
		
		List list = visitChannelMapDetail(channelType,messageType,comCode);
		
		judgeChannelVisitMap(list ,count);
	}
	
	/**
	 * ����������ȡ�ط�����
	 * modify by yinwenchao at 20120711 for (messagetype='1' AND riskcode = '0508')��Ϊ(messagetype='1')���������ǿ��0509��ȡ���������ſ�������.
	 * @param dbpool
	 * @param comCode
	 * @param count
	 * @return
	 * @throws Exception
	 */
	private List visitChannelMapDetail(String channelType) throws  Exception
	{
		String sql = "SELECT T.* FROM PRPCUSTOMERMESSAGE T " +
				"WHERE T.CHANNELTYPE = ? " +
				"AND t.PHONEVISITFLAG = '3' " +
				"AND ((t.messagetype='1') OR (t.messagetype='2' AND t.riskcode='0507')) " +
				"AND T.CREATEDDATE BETWEEN TRUNC(SYSDATE, 'mm') " +
				   "AND LAST_DAY(TO_DATE(TO_CHAR(SYSDATE, 'YYYY-MM-DD'), 'YYYY-MM-DD'))";
		List list = new ArrayList();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,channelType);
			ResultSet resultSet = dbpool.executePreparedQuery();
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			while(resultSet.next())
			{
				prpCustomerMessageSchema = new PrpCustomerMessageSchema();
				prpCustomerMessageSchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
					prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
				prpCustomerMessageSchema.setSurveyOrHandlComCode1(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
				prpCustomerMessageSchema.setCustomerCode(dbpool.getString(resultSet,"customercode"));
					prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
				prpCustomerMessageSchema.setPhoneVisitFlag("0");
					prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"MessageType"));
				list.add(prpCustomerMessageSchema);
			}
			resultSet.close();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->visitMapDetail()->���������ȡ10%�Ļط�����->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.visitMapDetail", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return list;  
	}
	
	/**
	 * @Title: visitChannelMapDetail
	 * @Description: ����������ȡ�ط�����
	 * @param @param channelType
	 * @param @param messageType
	 * @param @param comCode
	 * @param @return
	 * @param @throws Exception    �趨�ļ�
	 * @return List    ��������
	 * @throws
	 */
	private List visitChannelMapDetail(String channelType, String messageType, String comCode ) throws  Exception
	{
		

		String sql = "SELECT T.* FROM PRPCUSTOMERMESSAGE T " +
				"WHERE T.CHANNELTYPE = ? AND T.SURVEYORHANDLCOMCODE1 = ? " +
				"AND t.PHONEVISITFLAG = '3' " +
				"AND t.messagetype='1' " +
				"AND T.CREATEDDATE BETWEEN TRUNC(SYSDATE, 'mm') " +
				   "AND LAST_DAY(TO_DATE(TO_CHAR(SYSDATE, 'YYYY-MM-DD'), 'YYYY-MM-DD')) ";

		if(messageType.equals("2")){
			sql = "SELECT T.* FROM PRPCUSTOMERMESSAGE T " +

					"WHERE T.CHANNELTYPE = ? AND T.SURVEYORHANDLCOMCODE1 = ? " +
					"AND t.PHONEVISITFLAG = '3' " +
					"AND t.messagetype='2' AND t.riskcode='0507' " +
					"AND T.CREATEDDATE BETWEEN TRUNC(SYSDATE, 'mm') " +
					   "AND LAST_DAY(TO_DATE(TO_CHAR(SYSDATE, 'YYYY-MM-DD'), 'YYYY-MM-DD')) ";

		}
		List list = new ArrayList();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,channelType);
			dbpool.setString(2,comCode);
			ResultSet resultSet = dbpool.executePreparedQuery();
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			while(resultSet.next())
			{
				prpCustomerMessageSchema = new PrpCustomerMessageSchema();
				prpCustomerMessageSchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
					prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
				prpCustomerMessageSchema.setSurveyOrHandlComCode1(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
				prpCustomerMessageSchema.setCustomerCode(dbpool.getString(resultSet,"customercode"));
					prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
				prpCustomerMessageSchema.setPhoneVisitFlag("0");
					prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"MessageType"));
				list.add(prpCustomerMessageSchema);
			}
			resultSet.close();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->visitMapDetail()->���������ȡ10%�Ļط�����->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.visitMapDetail", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return list;  
	}
	
	
	/**
	 * ȡ����Ҫ���������
	 * @param dbpool
	 * @param count
	 * @return
	 * @throws Exception
	 */
	private void judgeChannelVisitMap(List list,int count) throws  Exception
	{
		if(list==null ||list.size()<1)
			return  ;
		int result = 1;
		int getCount = 1;
		BLPrpCustomerMessageMobile blPrpCustomerMessageMobile = new BLPrpCustomerMessageMobile();
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		PrpCustomerMessageMobileSchema prpCustomerMessageMobileSchema = null;
    	for (int i = 0; i < list.size(); i++) 
    	{   
    		result = 1;
    		prpCustomerMessageSchema = (PrpCustomerMessageSchema)list.get(i);
    		logger.info("��һ���ж��Ƿ���Ҫ�طõ�XX�ţ�"+prpCustomerMessageSchema.getPolicyNo()+"  id�ţ�"+prpCustomerMessageSchema.getId()+" �ֻ��ţ�"+prpCustomerMessageSchema.getMobile());
    		prpCustomerMessageMobileSchema = new PrpCustomerMessageMobileSchema();
    		prpCustomerMessageMobileSchema.setMobile(prpCustomerMessageSchema.getMobile());
    		prpCustomerMessageMobileSchema.setMessageType(prpCustomerMessageSchema.getMessageType());
    		prpCustomerMessageMobileSchema.setId(prpCustomerMessageSchema.getId());
    		
    		result = blPrpCustomerMessageMobile.insertPrpCustomerMessageMobile(prpCustomerMessageMobileSchema);
    		if(result==0)
    		{
    			logger.info("id�ţ�"+prpCustomerMessageSchema.getId()+" �ֻ��ţ�"+prpCustomerMessageSchema.getMobile()+" ��Ҫ�طã��Ѳ���XXXXX�ֻ���");
    			getCount++;
    		}
    		if(getCount>count)
    		{
    			logger.info("�Ѿ�ѭ������"+ getCount + ",��Ӧѭ������" + count +"���˳������ѭ����");
    			break;
    		}
    	}
	}
	
	/**
	 * �����������ȡ�ط�����������������,Ϊ���ֶ�ִ�д�����ʼ����ֹ����
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public void getChannelMapForServlet(DbPool dbpool,String channelType,int count,String strBDate,String strNDate ) throws Exception
	{
		logger.info("�貹����������룺"+channelType);
		
		List list = visitChannelMapDetailForServlet(channelType,strBDate,strNDate);
		
		judgeChannelVisitMap(list ,count);
	}
	
	
	/**
	 * �����������ȡ�ط�����������������,Ϊ���ֶ�ִ�д�����ʼ����ֹ����
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public void getChannelMapForServlet(DbPool dbpool,String channelType,int count,String strBDate,String strNDate ,String messageType, String comCode ) throws Exception
	{
		logger.info("�貹���������Դ:"+messageType+" ������"+comCode+" �������룺"+channelType);
		
		List list = visitChannelMapDetailForServlet(channelType,strBDate,strNDate,messageType,comCode);
		
		judgeChannelVisitMap(list ,count);
	}
	
	/**
	 * ����������ȡ�ط�����,Ϊ���ֶ�ִ�д�����ʼ����ֹ����
	 * modify by yinwenchao at 20120711 for (messagetype='1' AND riskcode = '0508')��Ϊ(messagetype='1')���������ǿ��0509��ȡ���������ſ�������.
	 * @param dbpool
	 * @param comCode
	 * @param count
	 * @return
	 * @throws Exception
	 */
	private List visitChannelMapDetailForServlet(String channelType,String strBDate,String strNDate) throws  Exception
	{
		String sql = "SELECT T.* FROM PRPCUSTOMERMESSAGE T " +
				"WHERE T.CHANNELTYPE = ? " +
				"AND T.PHONEVISITFLAG = '3' " +
				"AND ((T.messagetype='1') OR (T.messagetype='2' AND T.riskcode='0507')) " +
				"AND T.CREATEDDATE BETWEEN TO_DATE('" +strBDate+" 00:00:00', 'YYYY-MM-DD HH24:MI:SS') "+
				"AND TO_DATE('" +strNDate+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')";
		List list = new ArrayList();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,channelType);
			ResultSet resultSet = dbpool.executePreparedQuery();
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			while(resultSet.next())
			{
				prpCustomerMessageSchema = new PrpCustomerMessageSchema();
				prpCustomerMessageSchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
					prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
				prpCustomerMessageSchema.setSurveyOrHandlComCode1(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
				prpCustomerMessageSchema.setCustomerCode(dbpool.getString(resultSet,"customercode"));
					prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
				prpCustomerMessageSchema.setPhoneVisitFlag("0");
					prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"MessageType"));
				list.add(prpCustomerMessageSchema);
			}
			resultSet.close();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->visitMapDetail()->���������ȡ10%�Ļط�����->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.visitMapDetail", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return list;  
	}
	
	/**
	 * ����������ȡ�ط�����,Ϊ���ֶ�ִ�д�����ʼ����ֹ����
	 * modify by yinwenchao at 20120711 for (messagetype='1' AND riskcode = '0508')��Ϊ(messagetype='1')���������ǿ��0509��ȡ���������ſ�������.
	 * @param dbpool
	 * @param comCode
	 * @param count
	 * @return
	 * @throws Exception
	 */
	private List visitChannelMapDetailForServlet(String channelType,String strBDate,String strNDate,String messageType, String comCode ) throws  Exception
	{
		String sql = "SELECT T.* FROM PRPCUSTOMERMESSAGE T " +
				"WHERE T.CHANNELTYPE = ? AND T.SURVEYORHANDLCOMCODE1 = ? " +
				"AND T.PHONEVISITFLAG = '3' " +
				"AND T.messagetype='1' " +
				"AND T.CREATEDDATE BETWEEN TO_DATE('" +strBDate+" 00:00:00', 'YYYY-MM-DD HH24:MI:SS') "+
				"AND TO_DATE('" +strNDate+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')";
		if(messageType.equals("2")){
			sql = "SELECT T.* FROM PRPCUSTOMERMESSAGE T " +
					"WHERE T.CHANNELTYPE = ? AND T.SURVEYORHANDLCOMCODE1 = ? " +
					"AND T.PHONEVISITFLAG = '3' " +
					"AND T.messagetype='2' AND T.riskcode='0507' " +
					"AND T.CREATEDDATE BETWEEN TO_DATE('" +strBDate+" 00:00:00', 'YYYY-MM-DD HH24:MI:SS') "+
					"AND TO_DATE('" +strNDate+" 23:59:59', 'YYYY-MM-DD HH24:MI:SS')";
		}
				
		List list = new ArrayList();
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,channelType);
			dbpool.setString(2,comCode);
			ResultSet resultSet = dbpool.executePreparedQuery();
			PrpCustomerMessageSchema prpCustomerMessageSchema = null;
			while(resultSet.next())
			{
				prpCustomerMessageSchema = new PrpCustomerMessageSchema();
				prpCustomerMessageSchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
					prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
				prpCustomerMessageSchema.setSurveyOrHandlComCode1(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
				prpCustomerMessageSchema.setCustomerCode(dbpool.getString(resultSet,"customercode"));
					prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
				prpCustomerMessageSchema.setPhoneVisitFlag("0");
					prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"MessageType"));
				list.add(prpCustomerMessageSchema);
			}
			resultSet.close();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->visitChannelMapDetailForServlet()->���������ȡ10%�Ļط�����->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.visitMapDetail", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}finally
		{
			try {
				if(dbpool!=null)
				{
					dbpool.close();
				}
			} catch (SQLException e) 
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
				throw new Exception(e);
			}
		}
		return list;  
	}
	
	/**
	 * XXXXX100%�طã���ȡ��Ҫ��ȡ�ظ����ŵ����ݣ�ÿ��3000��
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getClaimList(DbPool dbpool) throws Exception
	{
		List list = new ArrayList();
		
	    String sql = "SELECT * FROM " +
					 "(SELECT to_char(t.createddate,'YYYY-MM-DD HH24:MI:SS') createddate1,t.* FROM PRPCUSTOMERMESSAGE T WHERE T.MESSAGETYPE = '2' AND T.MESSAGEFLAG = '1' " +
					 "AND T.ISCONTENT = '-1' AND T.CREATEDDATE > TO_DATE(TO_CHAR(SYSDATE - 8, 'YYYY-MM-DD'), 'YYYY-MM-DD HH24:MI:SS') AND t.groupSendSeq is null " +
					 "and (T.READMESSAGE IS NULL OR T.READMESSAGE < SYSDATE - 1 / 4) " +
					 "ORDER BY CREATEDDATE ASC) " +
					 "WHERE ROWNUM < 3001";
		logger.info("��ȡ���ͳɹ�״̬(���϶�ʱ)sql: "+sql);
		ResultSet resultSet = dbpool.query(sql);
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		while(resultSet.next())
		{
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setSendSeq(dbpool.getString(resultSet,"SENDSEQ"));
			prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
			prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
			prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"messageType"));
			prpCustomerMessageSchema.setMessageFlag(dbpool.getString(resultSet,"MessageFlag"));
			prpCustomerMessageSchema.setCreatedDate(dbpool.getString(resultSet,"CreatedDate1"));
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		return list;
	}
	
	/**
	 * XXXXX100%�طã����¶��Żظ�����
	 * @param dbpool
	 * @param prpCustomerMessageSchema
	 * @throws Exception
	 */
	public void updatePrpCustomerClaim(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema) throws Exception
	{
		String sql = "UPDATE PRPCUSTOMERMESSAGE SET ISCONTENT = ? ,INCEPTTEXT=?,INCEPTTIME=?,READMESSAGE=sysdate WHERE ID = ?";
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,prpCustomerMessageSchema.getIsContent());
			dbpool.setString(2,prpCustomerMessageSchema.getInCepttext());
			dbpool.setString(3,prpCustomerMessageSchema.getInCepttime());
			dbpool.setString(4,prpCustomerMessageSchema.getId());
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->updatePrpCustomerClaim()->XXXXX100%�طã����¶��Żظ�����->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updatePrpCustomerMoblieStatues", message,e,
					TaskMngUtil.CLAIMISSATISFIED_JOBNAME);
			
		}
	}
	
	/**
	 * XXXXX100%�طã��ŵ���ϵ��ֻ����һ�����ţ�����״̬ȫ��һ��
	 * @param dbpool
	 * @param mobile
	 * @param messageFlag
	 */
	public void updateGroupClaim(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema)
	{
		String sql = "UPDATE PRPCUSTOMERMESSAGE SET ISCONTENT = ? ,INCEPTTEXT=?,INCEPTTIME=?,READMESSAGE=sysdate WHERE " +
				     "mobile=? and groupSendSeq=? and groupflag='1' ";
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,prpCustomerMessageSchema.getIsContent());
			dbpool.setString(2,prpCustomerMessageSchema.getInCepttext());
			dbpool.setString(3,prpCustomerMessageSchema.getInCepttime());
			dbpool.setString(4,prpCustomerMessageSchema.getMobile());
			dbpool.setString(5,prpCustomerMessageSchema.getSendSeq());
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBPrpCustomerMessage->updateGroupClaim()->�ŵ���ϵ��ֻ����һ�����ţ�����״̬ȫ��һ��->"+e.getMessage();
			TaskMngUtil.insertMidDataLog(prpCustomerMessageSchema.getMobile(), message,e,
					TaskMngUtil.CLAIMISSATISFIED_JOBNAME);
			
		}
	}
	
	/**
	 * XXXXX100%�طã����µ�����־
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	public void updateExportMesFlag(DbPool dbpool,String id,String exportFlag) 
	{
		String sql = "update PrpCustomerMessage set exportmesflag=? ,UpdateDate= sysdate  where id=? ";
		try{
			logger.info("�����Ƿ�д��95510�ӿڱ�----------id�ţ�"+id+"----״̬��"+exportFlag);
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,exportFlag);
			dbpool.setString(2,id);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			logger.error(e+"����PrpCustomerMessage������־ExportFlag�����id�ţ�"+id);
			
			String message = "DBPrpCustomerMessage->updatePrpCustomerMessageExportFlag()->���µ�����־->"+e.getMessage();
			TaskMngUtil.insertMidDataLog(id, message,e,
					TaskMngUtil.CLAIMISSATISFIED_JOBNAME);
			
		}
	}
	
	/**
	 *�ط�ǰ�����طõ�����־EXPORTFLAG=0
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	public void updateDXhuifang(DbPool dbpool) 
	{
		String sql = "update PrpCustomerMessage set EXPORTFLAG='0' " +
				"where EXPORTFLAG = '1' AND MESSAGETYPE = '1' AND GROUPFLAG = '0' " +
				"AND CHANNELTYPE in ('N071','N075','N105') " +
				"and createddate > sysdate-7 ";
		try{
			logger.info("�ط�ǰ���ĵ����طñ�־");
			dbpool.prepareInnerStatement(sql);

			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			String message = "DBPrpCustomerMessage->updateDXhuifang()->�ط�ǰ�����طõ�����־EXPORTFLAG=0->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updateDXhuifang", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
		}
	}
	
	/**
	 * XX��������ȣ���ȡ��Ҫ��ȡ�ظ����ŵ����ݣ�ÿ��3000��
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getDxmydList(DbPool dbpool) throws Exception
	{
		List list = new ArrayList();
		
	    String sql = "SELECT * FROM " +
					 "(SELECT to_char(t.createddate,'YYYY-MM-DD HH24:MI:SS') createddate1,t.* FROM PRPCUSTOMERMESSAGE T " +
					 "WHERE T.MESSAGETYPE = '1' AND T.MESSAGEFLAG = '1' AND T.CHANNELTYPE in ('N071','N075','N105') " +
					 "AND T.ISCONTENT = '-1' AND T.createddate BETWEEN TO_DATE(TO_CHAR(SYSDATE - 2, 'YYYY-MM-DD'), 'YYYY-MM-DD') " +
					 "AND TO_DATE(TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD'), 'YYYY-MM-DD') " +
					 "AND t.groupSendSeq is null " +
					 "and (T.READMESSAGE IS NULL OR T.READMESSAGE < SYSDATE - 1 / 4) " +
					 "ORDER BY CREATEDDATE ASC) " +
					 "WHERE ROWNUM < 3001";
		logger.info("��ȡ���ͳɹ�״̬(���϶�ʱ)sql: "+sql);
		ResultSet resultSet = dbpool.query(sql);
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		while(resultSet.next())
		{
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setSendSeq(dbpool.getString(resultSet,"SENDSEQ"));
			prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
			prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
			prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"messageType"));
			prpCustomerMessageSchema.setMessageFlag(dbpool.getString(resultSet,"MessageFlag"));
			prpCustomerMessageSchema.setCreatedDate(dbpool.getString(resultSet,"CreatedDate1"));
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		return list;
	}
	
	/**
	 * XX��������ȣ��ֶ���ȡ��Ҫ��ȡ�ظ����ŵ����ݣ�ÿ��3000��
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getDxmydListBySelf(DbPool dbpool,String sql) throws Exception
	{
		List list = new ArrayList();
		
		logger.info("��ȡ���ͳɹ�״̬(���϶�ʱ)sql: "+sql);
		ResultSet resultSet = dbpool.query(sql);
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		while(resultSet.next())
		{
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setSendSeq(dbpool.getString(resultSet,"SENDSEQ"));
			prpCustomerMessageSchema.setId(dbpool.getString(resultSet,"id"));
			prpCustomerMessageSchema.setGroupFlag(dbpool.getString(resultSet,"GroupFlag"));
			prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			prpCustomerMessageSchema.setMessageType(dbpool.getString(resultSet,"messageType"));
			prpCustomerMessageSchema.setMessageFlag(dbpool.getString(resultSet,"MessageFlag"));
			prpCustomerMessageSchema.setCreatedDate(dbpool.getString(resultSet,"CreatedDate1"));
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		return list;
	}
	
	/**
	 * ��ȡ�����б��Ժ������
	 * @param dbpool
	 * @return
	 * @throws Exception
	 * ������20130619��XXXXX�ж��ظ�����ʱ©���ݣ���дXXXXX�ж��ظ������߼�
	 */
	public List getPhoneListNew(DbPool dbpool,String groupFlag,String messageType) throws Exception
	{
		List list = new ArrayList();
		String sql = "select distinct mobile from prpCustomerMessage where messageflag='-1' " +
	             "and IsRepeatPhone='2' and PhoneVisitFlag='2' and groupflag=? " +
	             "and messagetype=? and createddate > sysdate-60 ";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,groupFlag);
		dbpool.setString(2, messageType);
		ResultSet resultSet = dbpool.executePreparedQuery();
		PrpCustomerMessageSchema prpCustomerMessageSchema = null;
		while(resultSet.next())
		{
			prpCustomerMessageSchema = new PrpCustomerMessageSchema();
			prpCustomerMessageSchema.setMobile(dbpool.getString(resultSet,"mobile"));
			list.add(prpCustomerMessageSchema);
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return list;
	}
	/**
	 * �ж��Ƿ�Ϊ���ظ����룬�ں���Ϊ������ǰ���£���XXXXX���ַ���
	 * @param dbpool
	 * @param prpCustomerMessageSchema
	 * @return
	 * @throws Exception
	 * ������20130619��XXXXX�ж��ظ�����ʱ©���ݣ���дXXXXX�ж��ظ������߼�
	 */
	public List judgeNoRepeatPhoneListNew(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema) throws Exception
	{
		String groupFlag = prpCustomerMessageSchema.getGroupFlag();
		String name = "";
		String sql = "select count(DISTINCT "+name+") count from  prpCustomerMessage where mobile=?  and groupflag=? and messagetype=? AND ROWNUM < 20 and createddate > sysdate-365";
		if(groupFlag==null)
			return null;
		if("0".equals(groupFlag))
		{
			name = "customerName";
			sql = "select count(DISTINCT "+name+") namecount from  prpCustomerMessage where mobile=?  and groupflag=? and messagetype=? AND ROWNUM < 20 and createddate > sysdate-365";
		}
		if("1".equals(groupFlag))
		{
			name = "linkName";
			sql = "select count(DISTINCT "+name+") namecount from  prpCustomerMessage where mobile=?  and groupflag=? and messagetype=? AND LINKNAME IS NOT NULL AND ROWNUM < 20 and createddate > sysdate-365";
		}
		List list = new ArrayList();
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,prpCustomerMessageSchema.getMobile());
		dbpool.setString(2,groupFlag);
		dbpool.setString(3,prpCustomerMessageSchema.getMessageType());
		ResultSet resultSet = dbpool.executePreparedQuery();
		int count = 0;
		while(resultSet.next())
		{
			count = Integer.parseInt(dbpool.getString(resultSet,"namecount"));
			logger.info(prpCustomerMessageSchema.getMobile()+"��Ӧ����"+count);
			
			
			
			
			if(count>3)
			{
				prpCustomerMessageSchema.setIsRepeatPhone("1");
				prpCustomerMessageSchema.setMessageFlag("4");
				logger.info(prpCustomerMessageSchema.getMobile()+"��Դ"+prpCustomerMessageSchema.getMessageType()+"�ظ�����1");
				list.add(prpCustomerMessageSchema);
			}
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return list;
	}
	
	
	/**
	 * ��ĩ�ط�ǰ�����طõ�����־EXPORTFLAG=0
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	public void updateDXHFMonth(DbPool dbpool) 
	{
		String sql = "update PrpCustomerMessage set EXPORTFLAG='0' " +
				"where EXPORTFLAG = '1' AND MESSAGETYPE = '1' AND GROUPFLAG = '0' " +
				"AND CHANNELTYPE in ('N071','N075','N105') " +
				"and createddate > sysdate-35 ";
		try{
			logger.info("�ط�ǰ���ĵ����طñ�־");
			dbpool.prepareInnerStatement(sql);

			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			String message = "DBPrpCustomerMessage->updateDXHFMonth()->��ĩ�ط�ǰ�����طõ�����־EXPORTFLAG=0->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.updateDXHFMonth", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
		}
	}
	
	
	/**
	 * ��ѯPrpCustomerMessage���issuccess��GXCondition��״̬
	 * @param dbpool
	 * @param sql
	 * @throws Exception
	 */
	public Map queryPrpCustomerMessageIssuccess(DbPool dbpool,String sql )  throws Exception
	{
		String querySQL = "";
		String issuccess = "";
		String GXCondition = "";
		Map map = new HashMap();
		if (sql != null&&!"".equals(sql)) {
			querySQL = sql;
		}
		ResultSet resultSet = dbpool.query(querySQL);
		if(resultSet.next()){
			issuccess = dbpool.getString(resultSet,"ISSUCCESS");
			GXCondition = dbpool.getString(resultSet,"GXCondition");
		}
		map.put("issuccess",issuccess);
		map.put("GXCondition",GXCondition);
		resultSet.close();
		return map;
	}
	
	/**
	 * ɾ��PrpCustomerMessage��ָ��ʱ��������
	 * @param dbpool
	 * @param map
	 * @throws Exception
	 */
	public void delPrpCustomerMessageInfo(DbPool dbpool, Map map) {
		String sql = "delete from PrpCustomerMessage " +
				"where MESSAGETYPE = '1' AND messageflag in ('-2','-1','3','4','5') and sendtime is null " +
				"AND createddate between to_date('"+map.get("startTime")+"','YYYY-MM-DD HH24:MI:SS') " +
		        "and to_date('" + map.get("endTime") + "','YYYY-MM-DD HH24:MI:SS') " ;
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			String message = "DBPrpCustomerMessage->delPrpCustomerMessageInfo()->ɾ��PrpCustomerMessage��ָ��ʱ��������->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPrpCustomerMessage.delPrpCustomerMessageInfo", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
		}
		
	}
}
