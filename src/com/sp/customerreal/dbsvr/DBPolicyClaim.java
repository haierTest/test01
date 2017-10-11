package com.sp.customerreal.dbsvr;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import utils.system;

import com.sp.customerreal.schema.CustomerClaimSchema;
import com.sp.customerreal.schema.CustomerPolicySchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.taskmng.util.TaskMngUtil;
import com.sp.utility.database.DbPool;
public class DBPolicyClaim {
	
	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	 * XX个单 edit by linqian 20141216
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getSignPolicyData(DbPool dbpool) throws  Exception
	{
		


		String sql = "select a.*,(SELECT t.ventureflag FROM prpcmain t WHERE a.policyno=t.policyno) ventureflag, " +
					 "b.cif_custid,b.mobile1,b.mobile2,b.mobile3,b.mobile4,b.kxd1,b.kxd2,b.kxd3,b.kxd4,b.resource1,b.resource2,b.resource3,b.resource4,b.modifydate1,b.modifydate2,b.modifydate3,b.modifydate4," +
					 "(select p.codecname from  prpdcode  p where  p.CodeType='CarKind' and p.validstatus='1'  " +
				     "and (select carkindcode from prpcitemcar n where a.policyno=n.policyno)=p.codecode)carKindName  " +
				     "from prpcustomermessage a ,PRPCUSTOMERMESSAGE_CIFMOBILE b " +
				     "where a.exportflag='1' " +
		             "and a.MESSAGETYPE='1' and a.GROUPFLAG='0' " +
		             "and (a.GXCondition is null or a.GXCondition='0') and a.createddate > sysdate-365 " +
		             "and a.id = b.id(+) ";
		List list = new ArrayList();
		ResultSet resultSet = dbpool.query(sql);
		CustomerPolicySchema customerPolicySchema = null;
		while(resultSet.next())
		{
			customerPolicySchema = new CustomerPolicySchema();
			customerPolicySchema.setAddress(dbpool.getString(resultSet,"Address"));
			customerPolicySchema.setBirthDate(""+dbpool.getDateTime(resultSet,"BirthDate"));
			customerPolicySchema.setBusinessNature(dbpool.getString(resultSet,"BusinessNaturename"));
			customerPolicySchema.setComCName(dbpool.getString(resultSet,"CENTERORHANDLCOMNAME"));
			customerPolicySchema.setComCode(dbpool.getString(resultSet,"COMCODE"));
			customerPolicySchema.setComName(dbpool.getString(resultSet,"COMNAME"));
			customerPolicySchema.setComName1(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME1"));
			customerPolicySchema.setComName2(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME2"));
			customerPolicySchema.setCustomerCName(dbpool.getString(resultSet,"CUSTOMERNAME"));
			customerPolicySchema.setCustomerId(dbpool.getString(resultSet,"CUSTOMERCODE"));
			customerPolicySchema.setHandler(dbpool.getString(resultSet,"SURVEYORHANDLNAME"));
			customerPolicySchema.setHandlerCom(dbpool.getString(resultSet,"CENTERORHANDLCOMCODE"));
			customerPolicySchema.setHandlerCom1(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
			customerPolicySchema.setHandlerCom2(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE2"));
			customerPolicySchema.setHandlerId(dbpool.getString(resultSet,"SURVEYORHANDLID"));
			customerPolicySchema.setIdentifyNumber(dbpool.getString(resultSet,"IDENTIFYNUMBER"));
			customerPolicySchema.setIdentifyType(dbpool.getString(resultSet,"IDENTIFYTYPE"));
			customerPolicySchema.setLinkName(dbpool.getString(resultSet,"LinkName"));
			customerPolicySchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			customerPolicySchema.setOccupationCode(dbpool.getString(resultSet,"OCCUPATIONNAME"));
			customerPolicySchema.setOperator(dbpool.getString(resultSet,"OPERATORNAME"));
			customerPolicySchema.setOperatorId(dbpool.getString(resultSet,"OPERATORCODE"));
			customerPolicySchema.setOrganizeCode(dbpool.getString(resultSet,"ORGANIZECODE"));
			customerPolicySchema.setPhone(dbpool.getString(resultSet,"Phone"));
			customerPolicySchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
			customerPolicySchema.setSex(dbpool.getString(resultSet,"Sex"));
			customerPolicySchema.setUnderWriteEndDate(""+dbpool.getDateTime(resultSet,"UnderWriteEndDate"));
			customerPolicySchema.setId(dbpool.getString(resultSet,"id"));
			
			customerPolicySchema.setChannelType(dbpool.getString(resultSet,"channelType"));
			customerPolicySchema.setVentureflag(dbpool.getString(resultSet,"ventureflag"));
			
			customerPolicySchema.setCif_custid(dbpool.getString(resultSet,"cif_custid"));
			customerPolicySchema.setCan_custid(dbpool.getString(resultSet,"CUSTOMERCODE"));
			customerPolicySchema.setMobile1(dbpool.getString(resultSet,"mobile1"));
			customerPolicySchema.setMobile2(dbpool.getString(resultSet,"mobile2"));
			customerPolicySchema.setMobile3(dbpool.getString(resultSet,"mobile3"));
			customerPolicySchema.setMobile4(dbpool.getString(resultSet,"mobile"));
			customerPolicySchema.setKxd1(dbpool.getString(resultSet,"kxd1"));
			customerPolicySchema.setKxd2(dbpool.getString(resultSet,"kxd2"));
			customerPolicySchema.setKxd3(dbpool.getString(resultSet,"kxd3"));
			
			customerPolicySchema.setResource1(dbpool.getString(resultSet,"resource1"));
			customerPolicySchema.setResource2(dbpool.getString(resultSet,"resource2"));
			customerPolicySchema.setResource3(dbpool.getString(resultSet,"resource3"));
			
			customerPolicySchema.setModifydate1(""+dbpool.getDateTime(resultSet,"modifydate1"));
			customerPolicySchema.setModifydate2(""+dbpool.getDateTime(resultSet,"modifydate2"));
			customerPolicySchema.setModifydate3(""+dbpool.getDateTime(resultSet,"modifydate3"));
			
			
			customerPolicySchema.setBusinessNo(dbpool.getString(resultSet,"BUSINESSNO"));
			customerPolicySchema.setBrandName(dbpool.getString(resultSet,"BRANDNAME"));
			customerPolicySchema.setLicenseNo(dbpool.getString(resultSet,"LICENSENO"));
			customerPolicySchema.setCarKindName(dbpool.getString(resultSet,"carKindName"));
			customerPolicySchema.setGXCondition(dbpool.getString(resultSet,"GXCondition"));
			
			list.add(customerPolicySchema);
		}
		resultSet.close();
		return list;
	}
	/**
	 * 广西XX个单 add by linqian 20141216
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getSignPolicyGXData(DbPool dbpool) throws  Exception
	{
		


		String sql = "select a.*,(SELECT t.ventureflag FROM prpcmain t WHERE a.policyno=t.policyno) ventureflag, " +
				"(select p.codecname from  prpdcode  p where  p.CodeType='CarKind' and p.validstatus='1' " +
				"and (select carkindcode from prpcitemcar n where a.policyno=n.policyno)=p.codecode)carKindName " +
					
				     "from prpcustomermessage a  " +
				     "where a.exportflag='1' " +
		             "and a.MESSAGETYPE='1' and a.GROUPFLAG='0' " +  
		             "and a.GXCondition = '1' and a.createddate > sysdate-60";
		List list = new ArrayList();
		ResultSet resultSet = dbpool.query(sql);
		CustomerPolicySchema customerPolicySchema = null;
		while(resultSet.next())
		{
			customerPolicySchema = new CustomerPolicySchema();
			customerPolicySchema.setAddress(dbpool.getString(resultSet,"Address"));
			customerPolicySchema.setBirthDate(""+dbpool.getDateTime(resultSet,"BirthDate"));
			customerPolicySchema.setBusinessNature(dbpool.getString(resultSet,"BusinessNaturename"));
			customerPolicySchema.setComCName(dbpool.getString(resultSet,"CENTERORHANDLCOMNAME"));
			customerPolicySchema.setComCode(dbpool.getString(resultSet,"COMCODE"));
			customerPolicySchema.setComName(dbpool.getString(resultSet,"COMNAME"));
			customerPolicySchema.setComName1(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME1"));
			customerPolicySchema.setComName2(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME2"));
			customerPolicySchema.setCustomerCName(dbpool.getString(resultSet,"CUSTOMERNAME"));
			customerPolicySchema.setCustomerId(dbpool.getString(resultSet,"CUSTOMERCODE"));
			customerPolicySchema.setHandler(dbpool.getString(resultSet,"SURVEYORHANDLNAME"));
			customerPolicySchema.setHandlerCom(dbpool.getString(resultSet,"CENTERORHANDLCOMCODE"));
			customerPolicySchema.setHandlerCom1(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
			customerPolicySchema.setHandlerCom2(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE2"));
			customerPolicySchema.setHandlerId(dbpool.getString(resultSet,"SURVEYORHANDLID"));
			customerPolicySchema.setIdentifyNumber(dbpool.getString(resultSet,"IDENTIFYNUMBER"));
			customerPolicySchema.setIdentifyType(dbpool.getString(resultSet,"IDENTIFYTYPE"));
			customerPolicySchema.setLinkName(dbpool.getString(resultSet,"LinkName"));
			customerPolicySchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			customerPolicySchema.setOccupationCode(dbpool.getString(resultSet,"OCCUPATIONNAME"));
			customerPolicySchema.setOperator(dbpool.getString(resultSet,"OPERATORNAME"));
			customerPolicySchema.setOperatorId(dbpool.getString(resultSet,"OPERATORCODE"));
			customerPolicySchema.setOrganizeCode(dbpool.getString(resultSet,"ORGANIZECODE"));
			customerPolicySchema.setPhone(dbpool.getString(resultSet,"Phone"));
			customerPolicySchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
			customerPolicySchema.setSex(dbpool.getString(resultSet,"Sex"));
			customerPolicySchema.setUnderWriteEndDate(""+dbpool.getDateTime(resultSet,"UnderWriteEndDate"));
			customerPolicySchema.setId(dbpool.getString(resultSet,"id"));
			
			customerPolicySchema.setChannelType(dbpool.getString(resultSet,"channelType"));
			customerPolicySchema.setVentureflag(dbpool.getString(resultSet,"ventureflag"));
			
			customerPolicySchema.setCif_custid("1000000000");
			customerPolicySchema.setCan_custid(dbpool.getString(resultSet,"CUSTOMERCODE"));
			customerPolicySchema.setMobile1(dbpool.getString(resultSet,"Mobile"));
			customerPolicySchema.setMobile2("");
			customerPolicySchema.setMobile3("");
			customerPolicySchema.setMobile4(dbpool.getString(resultSet,"mobile"));
			customerPolicySchema.setKxd1("1.0");
			customerPolicySchema.setKxd2("");
			customerPolicySchema.setKxd3("");
			
			customerPolicySchema.setResource1("01");
			customerPolicySchema.setResource2("");
			customerPolicySchema.setResource3("");
			
			customerPolicySchema.setModifydate1(dbpool.getString(resultSet,"updateDate"));
			customerPolicySchema.setModifydate2("");
			customerPolicySchema.setModifydate3("");
			
			
			customerPolicySchema.setBusinessNo(dbpool.getString(resultSet,"BUSINESSNO"));
			customerPolicySchema.setBrandName(dbpool.getString(resultSet,"BRANDNAME"));
			customerPolicySchema.setLicenseNo(dbpool.getString(resultSet,"LICENSENO"));
			customerPolicySchema.setCarKindName(dbpool.getString(resultSet,"carKindName"));
			customerPolicySchema.setGXCondition(dbpool.getString(resultSet,"GXCondition"));
			
			list.add(customerPolicySchema);
		}
		resultSet.close();
		return list;
	}
	
	/**
	 * 广西XX个单 add by linqian 20141216
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getSignPolicyGXTData(DbPool dbpool,Map map) throws  Exception
	{
		


		String sql = "select a.*,(SELECT t.ventureflag FROM prpcmain t WHERE a.policyno=t.policyno) ventureflag, " +
				"(select p.codecname from  prpdcode  p where  p.CodeType='CarKind' and p.validstatus='1' " +
				"and (select carkindcode from prpcitemcar n where a.policyno=n.policyno)=p.codecode)carKindName " +
					
				     "from prpcustomermessage a  " +
				     "where a.exportflag='1' " +
		             "and a.MESSAGETYPE='1' and a.GROUPFLAG='0' " +  
		             "and a.GXCondition = '1'  " +
		             "and createddate between to_date('"+map.get("startTime")+
		    		 "','YYYY-MM-DD HH24:MI:SS') " +
		    			 "and to_date('" + map.get("endTime") + 
		    			 "','YYYY-MM-DD HH24:MI:SS') ";
		List list = new ArrayList();
		ResultSet resultSet = dbpool.query(sql);
		CustomerPolicySchema customerPolicySchema = null;
		while(resultSet.next())
		{
			customerPolicySchema = new CustomerPolicySchema();
			customerPolicySchema.setAddress(dbpool.getString(resultSet,"Address"));
			customerPolicySchema.setBirthDate(""+dbpool.getDateTime(resultSet,"BirthDate"));
			customerPolicySchema.setBusinessNature(dbpool.getString(resultSet,"BusinessNaturename"));
			customerPolicySchema.setComCName(dbpool.getString(resultSet,"CENTERORHANDLCOMNAME"));
			customerPolicySchema.setComCode(dbpool.getString(resultSet,"COMCODE"));
			customerPolicySchema.setComName(dbpool.getString(resultSet,"COMNAME"));
			customerPolicySchema.setComName1(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME1"));
			customerPolicySchema.setComName2(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME2"));
			customerPolicySchema.setCustomerCName(dbpool.getString(resultSet,"CUSTOMERNAME"));
			customerPolicySchema.setCustomerId(dbpool.getString(resultSet,"CUSTOMERCODE"));
			customerPolicySchema.setHandler(dbpool.getString(resultSet,"SURVEYORHANDLNAME"));
			customerPolicySchema.setHandlerCom(dbpool.getString(resultSet,"CENTERORHANDLCOMCODE"));
			customerPolicySchema.setHandlerCom1(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
			customerPolicySchema.setHandlerCom2(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE2"));
			customerPolicySchema.setHandlerId(dbpool.getString(resultSet,"SURVEYORHANDLID"));
			customerPolicySchema.setIdentifyNumber(dbpool.getString(resultSet,"IDENTIFYNUMBER"));
			customerPolicySchema.setIdentifyType(dbpool.getString(resultSet,"IDENTIFYTYPE"));
			customerPolicySchema.setLinkName(dbpool.getString(resultSet,"LinkName"));
			customerPolicySchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			customerPolicySchema.setOccupationCode(dbpool.getString(resultSet,"OCCUPATIONNAME"));
			customerPolicySchema.setOperator(dbpool.getString(resultSet,"OPERATORNAME"));
			customerPolicySchema.setOperatorId(dbpool.getString(resultSet,"OPERATORCODE"));
			customerPolicySchema.setOrganizeCode(dbpool.getString(resultSet,"ORGANIZECODE"));
			customerPolicySchema.setPhone(dbpool.getString(resultSet,"Phone"));
			customerPolicySchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
			customerPolicySchema.setSex(dbpool.getString(resultSet,"Sex"));
			customerPolicySchema.setUnderWriteEndDate(""+dbpool.getDateTime(resultSet,"UnderWriteEndDate"));
			customerPolicySchema.setId(dbpool.getString(resultSet,"id"));
			
			customerPolicySchema.setChannelType(dbpool.getString(resultSet,"channelType"));
			customerPolicySchema.setVentureflag(dbpool.getString(resultSet,"ventureflag"));
			
			customerPolicySchema.setCif_custid("1000000000");
			customerPolicySchema.setCan_custid(dbpool.getString(resultSet,"CUSTOMERCODE"));
			customerPolicySchema.setMobile1(dbpool.getString(resultSet,"Mobile"));
			customerPolicySchema.setMobile2("");
			customerPolicySchema.setMobile3("");
			customerPolicySchema.setMobile4(dbpool.getString(resultSet,"mobile"));
			customerPolicySchema.setKxd1("1.0");
			customerPolicySchema.setKxd2("");
			customerPolicySchema.setKxd3("");
			
			customerPolicySchema.setResource1("01");
			customerPolicySchema.setResource2("");
			customerPolicySchema.setResource3("");
			
			customerPolicySchema.setModifydate1(dbpool.getString(resultSet,"updateDate"));
			customerPolicySchema.setModifydate2("");
			customerPolicySchema.setModifydate3("");
			
			
			customerPolicySchema.setBusinessNo(dbpool.getString(resultSet,"BUSINESSNO"));
			customerPolicySchema.setBrandName(dbpool.getString(resultSet,"BRANDNAME"));
			customerPolicySchema.setLicenseNo(dbpool.getString(resultSet,"LICENSENO"));
			customerPolicySchema.setCarKindName(dbpool.getString(resultSet,"carKindName"));
			customerPolicySchema.setGXCondition(dbpool.getString(resultSet,"GXCondition"));
			
			list.add(customerPolicySchema);
		}
		resultSet.close();
		logger.info("本次导入的广西XX个单数量"+list.size());
		return list;
	}
	
	/**
	 * XX团单
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getGroupPolicyData(DbPool dbpool) throws  Exception
	{
		String sql = "select * from prpcustomermessage where exportflag='1' " +
				     "and MESSAGETYPE='1' and GROUPFLAG='1' ";
		List list = new ArrayList();
		ResultSet resultSet = dbpool.query(sql);
		CustomerPolicySchema customerPolicySchema = null;
		while(resultSet.next())
		{
			customerPolicySchema = new CustomerPolicySchema();
			customerPolicySchema.setAddress(dbpool.getString(resultSet,"Address"));
			customerPolicySchema.setBirthDate(""+dbpool.getDateTime(resultSet,"BirthDate"));
			customerPolicySchema.setBusinessNature(dbpool.getString(resultSet,"BusinessNaturename"));
			customerPolicySchema.setComCName(dbpool.getString(resultSet,"CENTERORHANDLCOMNAME"));
			customerPolicySchema.setComCode(dbpool.getString(resultSet,"COMCODE"));
			customerPolicySchema.setComName(dbpool.getString(resultSet,"COMNAME"));
			customerPolicySchema.setComName1(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME1"));
			customerPolicySchema.setComName2(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME2"));
			customerPolicySchema.setCustomerCName(dbpool.getString(resultSet,"CUSTOMERNAME"));
			customerPolicySchema.setCustomerId(dbpool.getString(resultSet,"CUSTOMERCODE"));
			customerPolicySchema.setHandler(dbpool.getString(resultSet,"SURVEYORHANDLNAME"));
			customerPolicySchema.setHandlerCom(dbpool.getString(resultSet,"CENTERORHANDLCOMCODE"));
			customerPolicySchema.setHandlerCom1(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
			customerPolicySchema.setHandlerCom2(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE2"));
			customerPolicySchema.setHandlerId(dbpool.getString(resultSet,"SURVEYORHANDLID"));
			customerPolicySchema.setIdentifyNumber(dbpool.getString(resultSet,"IDENTIFYNUMBER"));
			customerPolicySchema.setIdentifyType(dbpool.getString(resultSet,"IDENTIFYTYPE"));
			customerPolicySchema.setLinkName(dbpool.getString(resultSet,"LinkName"));
			customerPolicySchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			customerPolicySchema.setOccupationCode(dbpool.getString(resultSet,"OCCUPATIONNAME"));
			customerPolicySchema.setOperator(dbpool.getString(resultSet,"OPERATORNAME"));
			customerPolicySchema.setOperatorId(dbpool.getString(resultSet,"OPERATORCODE"));
			customerPolicySchema.setOrganizeCode(dbpool.getString(resultSet,"ORGANIZECODE"));
			customerPolicySchema.setPhone(dbpool.getString(resultSet,"Phone"));
			customerPolicySchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
			customerPolicySchema.setSex(dbpool.getString(resultSet,"Sex"));
			customerPolicySchema.setUnderWriteEndDate(""+dbpool.getDateTime(resultSet,"UnderWriteEndDate"));
			customerPolicySchema.setId(dbpool.getString(resultSet,"id"));
			list.add(customerPolicySchema);
		}
		resultSet.close();
		return list;
	}
	/**
	 * XXXXX个单
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List getSignClaimData(DbPool dbpool) throws  Exception
	{


		String sql = "select a.*,(SELECT t.ventureflag FROM prpcmain t WHERE a.policyno=t.policyno) ventureflag, " +
					 "b.cif_custid,b.mobile1,b.mobile2,b.mobile3,b.mobile4,b.kxd1,b.kxd2,b.kxd3,b.kxd4,b.resource1,b.resource2,b.resource3,b.resource4,b.modifydate1,b.modifydate2,b.modifydate3,b.modifydate4 " +
				     "from prpcustomermessage a ,PRPCUSTOMERMESSAGE_CIFMOBILE b " +
				     "where a.exportflag='1' " +
			         "and a.MESSAGETYPE='2' and a.GROUPFLAG='0' " +
			         "and a.id = b.id(+) ";
		List list = new ArrayList();
		ResultSet resultSet = dbpool.query(sql);
		CustomerClaimSchema customerClaimSchema = null;
		while(resultSet.next())
		{
			customerClaimSchema = new CustomerClaimSchema();
			customerClaimSchema.setAddress(dbpool.getString(resultSet,"Address"));
			customerClaimSchema.setBirthDate(""+dbpool.getDateTime(resultSet,"BirthDate"));
			customerClaimSchema.setCaseNo(dbpool.getString(resultSet,"CaseNo"));
			customerClaimSchema.setCenterCode(dbpool.getString(resultSet,"CENTERORHANDLCOMCODE"));
			customerClaimSchema.setCenterName(dbpool.getString(resultSet,"CENTERORHANDLCOMNAME"));
			customerClaimSchema.setClaimNo(dbpool.getString(resultSet,"ClaimNo"));
			customerClaimSchema.setComCName(dbpool.getString(resultSet,"COMNAME"));
			customerClaimSchema.setComCode(dbpool.getString(resultSet,"COMCODE"));
			customerClaimSchema.setComName1(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME1"));
			customerClaimSchema.setComName2(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME2"));
			customerClaimSchema.setCustomerCName(dbpool.getString(resultSet,"CUSTOMERNAME"));
			customerClaimSchema.setDamageDate(""+dbpool.getDateTime(resultSet,"DamageDate"));
			customerClaimSchema.setId(dbpool.getString(resultSet,"id"));
			customerClaimSchema.setIdentifyNumber(dbpool.getString(resultSet,"IdentifyNumber"));
			customerClaimSchema.setIdentifyType(dbpool.getString(resultSet,"IdentifyType"));
			customerClaimSchema.setInsurantName(dbpool.getString(resultSet,"InsurantName"));
			customerClaimSchema.setLicenseNo(dbpool.getString(resultSet,"LicenseNo"));
			customerClaimSchema.setLinkName(dbpool.getString(resultSet,"LinkName"));
			customerClaimSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			customerClaimSchema.setNotifyDate(""+dbpool.getDateTime(resultSet,"NotifyDate"));
			customerClaimSchema.setOccupationCode(dbpool.getString(resultSet,"OCCUPATIONNAME"));
			customerClaimSchema.setOpenNo(dbpool.getString(resultSet,"OpenNo"));
			customerClaimSchema.setOpenTime(""+dbpool.getDateTime(resultSet,"OpenTime"));
			customerClaimSchema.setOrganizeCode(dbpool.getString(resultSet,"OrganizeCode"));
			customerClaimSchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
			customerClaimSchema.setSex(dbpool.getString(resultSet,"Sex"));
			customerClaimSchema.setSurveyCom1(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
			customerClaimSchema.setSurveyCom2(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE2"));
			customerClaimSchema.setSurveyId(dbpool.getString(resultSet,"SURVEYORHANDLID"));
			customerClaimSchema.setSurveyMan(dbpool.getString(resultSet,"SURVEYORHANDLNAME"));
			customerClaimSchema.setTelePhone(dbpool.getString(resultSet,"phone"));
			
			customerClaimSchema.setChannelType(dbpool.getString(resultSet,"channelType"));
			customerClaimSchema.setVentureflag(dbpool.getString(resultSet,"ventureflag"));
			
			customerClaimSchema.setCif_custid(dbpool.getString(resultSet,"cif_custid"));
			customerClaimSchema.setCan_custid(dbpool.getString(resultSet,"CUSTOMERCODE"));
			customerClaimSchema.setMobile1(dbpool.getString(resultSet,"mobile1"));
			customerClaimSchema.setMobile2(dbpool.getString(resultSet,"mobile2"));
			customerClaimSchema.setMobile3(dbpool.getString(resultSet,"mobile3"));
			customerClaimSchema.setMobile4(dbpool.getString(resultSet,"mobile"));
			customerClaimSchema.setKxd1(dbpool.getString(resultSet,"kxd1"));
			customerClaimSchema.setKxd2(dbpool.getString(resultSet,"kxd2"));
			customerClaimSchema.setKxd3(dbpool.getString(resultSet,"kxd3"));
			
			customerClaimSchema.setResource1(dbpool.getString(resultSet,"resource1"));
			customerClaimSchema.setResource2(dbpool.getString(resultSet,"resource2"));
			customerClaimSchema.setResource3(dbpool.getString(resultSet,"resource3"));
			
			customerClaimSchema.setModifydate1(""+dbpool.getDateTime(resultSet,"modifydate1"));
			customerClaimSchema.setModifydate2(""+dbpool.getDateTime(resultSet,"modifydate2"));
			customerClaimSchema.setModifydate3(""+dbpool.getDateTime(resultSet,"modifydate3"));
			
			list.add(customerClaimSchema);
		}
		resultSet.close();
		return list;
	}
	/**
	 * XXXXX团单
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getGroupClaimData(DbPool dbpool) throws  Exception
	{
		String sql = "select * from prpcustomermessage where exportflag='1' " +
				     "and MESSAGETYPE='2' and GROUPFLAG='1' ";
		List list = new ArrayList();
		ResultSet resultSet = dbpool.query(sql);
		CustomerClaimSchema customerClaimSchema = null;
		while(resultSet.next())
		{
			customerClaimSchema = new CustomerClaimSchema();
			customerClaimSchema.setAddress(dbpool.getString(resultSet,"Address"));
			customerClaimSchema.setBirthDate(""+dbpool.getDateTime(resultSet,"BirthDate"));
			customerClaimSchema.setCaseNo(dbpool.getString(resultSet,"CaseNo"));
			customerClaimSchema.setCenterCode(dbpool.getString(resultSet,"CENTERORHANDLCOMCODE"));
			customerClaimSchema.setCenterName(dbpool.getString(resultSet,"CENTERORHANDLCOMNAME"));
			customerClaimSchema.setClaimNo(dbpool.getString(resultSet,"ClaimNo"));
			customerClaimSchema.setComCName(dbpool.getString(resultSet,"COMNAME"));
			customerClaimSchema.setComCode(dbpool.getString(resultSet,"COMCODE"));
			customerClaimSchema.setComName1(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME1"));
			customerClaimSchema.setComName2(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME2"));
			customerClaimSchema.setCustomerCName(dbpool.getString(resultSet,"CUSTOMERNAME"));
			customerClaimSchema.setDamageDate(""+dbpool.getDateTime(resultSet,"DamageDate"));
			customerClaimSchema.setId(dbpool.getString(resultSet,"id"));
			customerClaimSchema.setIdentifyNumber(dbpool.getString(resultSet,"IdentifyNumber"));
			customerClaimSchema.setIdentifyType(dbpool.getString(resultSet,"IdentifyType"));
			customerClaimSchema.setInsurantName(dbpool.getString(resultSet,"InsurantName"));
			customerClaimSchema.setLicenseNo(dbpool.getString(resultSet,"LicenseNo"));
			customerClaimSchema.setLinkName(dbpool.getString(resultSet,"LinkName"));
			customerClaimSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			customerClaimSchema.setNotifyDate(""+dbpool.getDateTime(resultSet,"NotifyDate"));
			customerClaimSchema.setOccupationCode(dbpool.getString(resultSet,"OCCUPATIONNAME"));
			customerClaimSchema.setOpenNo(dbpool.getString(resultSet,"OpenNo"));
			customerClaimSchema.setOpenTime(""+dbpool.getDateTime(resultSet,"OpenTime"));
			customerClaimSchema.setOrganizeCode(dbpool.getString(resultSet,"OrganizeCode"));
			customerClaimSchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
			customerClaimSchema.setSex(dbpool.getString(resultSet,"Sex"));
			customerClaimSchema.setSurveyCom1(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
			customerClaimSchema.setSurveyCom2(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE2"));
			customerClaimSchema.setSurveyId(dbpool.getString(resultSet,"SURVEYORHANDLID"));
			customerClaimSchema.setSurveyMan(dbpool.getString(resultSet,"SURVEYORHANDLNAME"));
			customerClaimSchema.setTelePhone(dbpool.getString(resultSet,"phone"));
			list.add(customerClaimSchema);
		}
		resultSet.close();
		return list;
	}
	/**
	 * XX个单
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	public void insertSignPolicyData(DbPool dbpool,CustomerPolicySchema customerPolicySchema)
	{
		String sql =    "insert into APP_BIZ_HXPSIGNDATA(" +
						"Address," +
						"BirthDate," +
						"BusinessNature," +
						"ComCName," +
						"ComCode," +
						"ComName," +
						"ComName1," +
						"ComName2," +
						"CustomerCName," +
						"CustomerId," +
						"Handler," +
						"HandlerCom," +
						"HandlerCom1," +
						"HandlerCom2," +
						"HandlerId," +
						"IdentifyNumber," +
						"IdentifyType," +
						"Mobile," +
						"OccupationCode," +
						"Operator," +
						"OperatorId," +
						"Phone," +
						"PolicyNo," +
						"Sex," +
						"UnderWriteEndDate," +
						"channelType," +
						"ventureflag," +
						"CREATEDATE, " +
						"BUSINESSNO, " +
						"BRANDNAME, " +
						"LICENSENO, " +
						"carKindName, " +
						"GXCondition " +
						") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?) ";
		try{
			dbpool.prepareInnerStatement(sql);
			int j = 0;
			dbpool.setString(++j,customerPolicySchema.getAddress());
			dbpool.setDateTime(++j,new DateTime(customerPolicySchema.getBirthDate()));
			dbpool.setString(++j,customerPolicySchema.getBusinessNature());
			dbpool.setString(++j,customerPolicySchema.getComCName());
			dbpool.setString(++j,customerPolicySchema.getComCode());
			dbpool.setString(++j,customerPolicySchema.getComName());
			dbpool.setString(++j,customerPolicySchema.getComName1());
			dbpool.setString(++j,customerPolicySchema.getComName2());
			dbpool.setString(++j,customerPolicySchema.getCustomerCName());
			dbpool.setString(++j,customerPolicySchema.getCustomerId());
			dbpool.setString(++j,customerPolicySchema.getHandler());
			dbpool.setString(++j,customerPolicySchema.getHandlerCom());
			dbpool.setString(++j,customerPolicySchema.getHandlerCom1());
			dbpool.setString(++j,customerPolicySchema.getHandlerCom2());
			dbpool.setString(++j,customerPolicySchema.getHandlerId());
			dbpool.setString(++j,customerPolicySchema.getIdentifyNumber());
			dbpool.setString(++j,customerPolicySchema.getIdentifyType());
			dbpool.setString(++j,customerPolicySchema.getMobile());
			dbpool.setString(++j,customerPolicySchema.getOccupationCode());
			dbpool.setString(++j,customerPolicySchema.getOperator());
			dbpool.setString(++j,customerPolicySchema.getOperatorId());
			dbpool.setString(++j,customerPolicySchema.getPhone());
			dbpool.setString(++j,customerPolicySchema.getPolicyNo());
			dbpool.setString(++j,customerPolicySchema.getSex());
			dbpool.setDateTime(++j,new DateTime(customerPolicySchema.getUnderWriteEndDate()));
			
			dbpool.setString(++j,customerPolicySchema.getChannelType());
			dbpool.setString(++j,customerPolicySchema.getVentureflag());
			
			dbpool.setString(++j,customerPolicySchema.getBusinessNo()); 
			dbpool.setString(++j,customerPolicySchema.getBrandName()); 
			dbpool.setString(++j,customerPolicySchema.getLicenseNo()); 
			dbpool.setString(++j,customerPolicySchema.getCarKindName()); 
			dbpool.setString(++j,customerPolicySchema.getGXCondition()); 
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
			customerPolicySchema.setExportFlag("2"); 
		}catch(Exception e)
		{
			customerPolicySchema.setExportFlag("3");
			logger.error("XXXXX真实性错误信息：",e);
			logger.error(e+" XX个单写入95510接口表出错的id号是："+customerPolicySchema.getId());
			
			String message = "DBPolicyClaim->insertSignPolicyData()->XX个单写入95510接口表出错的id号是："+customerPolicySchema.getId()+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPolicyClaim.insertSignPolicyData", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
	}
	/**
	 * XX团单
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	public void insertGroupPolicyData(DbPool dbpool,CustomerPolicySchema customerPolicySchema)
	{
		String sql =    "insert into APP_BIZ_HXGSIGNDATA(" +
						"Address," +
						"BusinessNature," +
						"ComCName," +
						"ComCode," +
						"ComName," +
						"ComName1," +
						"ComName2," +
						"CustomerCName," +
						"CustomerId," +
						"Handler," +
						"HandlerCom," +
						"HandlerCom1," +
						"HandlerCom2," +
						"HandlerId," +
						"LinkName," +
						"LINKMOBILE," +
						"Operator," +
						"OperatorId," +
						"OrganizeCode," +
						"LINKPHONE," +
						"PolicyNo," +
						"UnderWriteEndDate," +
						"CREATEDATE " +
						") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate) ";
		try{
			dbpool.prepareInnerStatement(sql);
			int j = 0;
			dbpool.setString(++j,customerPolicySchema.getAddress());
			dbpool.setString(++j,customerPolicySchema.getBusinessNature());
			dbpool.setString(++j,customerPolicySchema.getComCName());
			dbpool.setString(++j,customerPolicySchema.getComCode());
			dbpool.setString(++j,customerPolicySchema.getComName());
			dbpool.setString(++j,customerPolicySchema.getComName1());
			dbpool.setString(++j,customerPolicySchema.getComName2());
			dbpool.setString(++j,customerPolicySchema.getCustomerCName());
			dbpool.setString(++j,customerPolicySchema.getCustomerId());
			dbpool.setString(++j,customerPolicySchema.getHandler());
			dbpool.setString(++j,customerPolicySchema.getHandlerCom());
			dbpool.setString(++j,customerPolicySchema.getHandlerCom1());
			dbpool.setString(++j,customerPolicySchema.getHandlerCom2());
			dbpool.setString(++j,customerPolicySchema.getHandlerId());
			dbpool.setString(++j,customerPolicySchema.getLinkName());
			dbpool.setString(++j,customerPolicySchema.getMobile());
			dbpool.setString(++j,customerPolicySchema.getOperator());
			dbpool.setString(++j,customerPolicySchema.getOperatorId());
			dbpool.setString(++j,customerPolicySchema.getOrganizeCode());
			dbpool.setString(++j,customerPolicySchema.getPhone());
			dbpool.setString(++j,customerPolicySchema.getPolicyNo());
			dbpool.setDateTime(++j,new DateTime(customerPolicySchema.getUnderWriteEndDate()));
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
			customerPolicySchema.setExportFlag("2");
		}catch(Exception e)
		{
			customerPolicySchema.setExportFlag("3");
			logger.error("XXXXX真实性错误信息：",e);
			logger.error(e+"XX团单写入95510接口表出错的id号是："+customerPolicySchema.getId());
			
			String message = "DBPolicyClaim->insertGroupPolicyData()->XX团单->"+e.getMessage();
			TaskMngUtil.insertMidDataLog(customerPolicySchema.getId(), message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
	}
	/**
	 * XXXXX个单
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	public void insertSignClaimData(DbPool dbpool,CustomerClaimSchema customerClaimSchema )
	{
		String sql =    "insert into APP_BIZ_PCLAIMDATA(" +
						"Address," +
						"BirthDate," +
						"CaseNo," +
						"CenterCode," +
						"centerName," +
						"ClaimNo," +
						"ComCName," +
						"ComCode," +
						"ComName1," +
						"ComName2," +
						"DamageDate," +
						"IdentifyNumber," +
						"IdentifyType," +
						"InsurantName," +
						"LicenseNo," +
						"Mobile," +
						"NotifyDate," +
						"OccupationCode," +
						"OpenNo," +
						"OpenTime," +
						"PolicyNo," +
						"Sex," +
						"SurveyCom1," +
						"SurveyCom2," +
						"SurveyId," +
						"SurveyMan," +
						"TelePhone," +
						"channelType," +
						"ventureflag," +
						"CREATEDATE " +
						") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate) ";
		try{
			dbpool.prepareInnerStatement(sql);
			int j = 0;
			dbpool.setString(++j,customerClaimSchema.getAddress());
			dbpool.setDateTime(++j,new DateTime(customerClaimSchema.getBirthDate()));
			dbpool.setString(++j,customerClaimSchema.getCaseNo());
			dbpool.setString(++j,customerClaimSchema.getCenterCode());
			dbpool.setString(++j,customerClaimSchema.getCenterName());
			dbpool.setString(++j,customerClaimSchema.getClaimNo());
			dbpool.setString(++j,customerClaimSchema.getComCName());
			dbpool.setString(++j,customerClaimSchema.getComCode());
			dbpool.setString(++j,customerClaimSchema.getComName1());
			dbpool.setString(++j,customerClaimSchema.getComName2());
			dbpool.setDateTime(++j,new DateTime(customerClaimSchema.getDamageDate()));
			dbpool.setString(++j,customerClaimSchema.getIdentifyNumber());
			dbpool.setString(++j,customerClaimSchema.getIdentifyType());
			dbpool.setString(++j,customerClaimSchema.getInsurantName());
			dbpool.setString(++j,customerClaimSchema.getLicenseNo());
			dbpool.setString(++j,customerClaimSchema.getMobile());
			dbpool.setDateTime(++j,new DateTime(customerClaimSchema.getNotifyDate()));
			dbpool.setString(++j,customerClaimSchema.getOccupationCode());
			dbpool.setString(++j,customerClaimSchema.getOpenNo());
			dbpool.setDateTime(++j,new DateTime(customerClaimSchema.getOpenTime()));
			dbpool.setString(++j,customerClaimSchema.getPolicyNo());
			dbpool.setString(++j,customerClaimSchema.getSex());
			dbpool.setString(++j,customerClaimSchema.getSurveyCom1());
			dbpool.setString(++j,customerClaimSchema.getSurveyCom2());
			dbpool.setString(++j,customerClaimSchema.getSurveyId());
			dbpool.setString(++j,customerClaimSchema.getSurveyMan());
			dbpool.setString(++j,customerClaimSchema.getTelePhone());
			
			dbpool.setString(++j,customerClaimSchema.getChannelType());
			dbpool.setString(++j,customerClaimSchema.getVentureflag());
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
			customerClaimSchema.setExportFlag("2");
		}catch(Exception e)
		{
			customerClaimSchema.setExportFlag("3");
			logger.error("XXXXX真实性错误信息：",e);
			logger.error(e+" XXXXX个单写入95510接口表出错的id号是："+customerClaimSchema.getId());
			
			String message = "DBPolicyClaim->insertSignClaimData()->XXXXX个单写入95510接口表出错的id号是：->"+customerClaimSchema.getId()+e.getMessage();
			TaskMngUtil.insertMidDataLog(customerClaimSchema.getId(), message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
	}
	/**
	 * XXXXX团单
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	public void insertGroupClaimData(DbPool dbpool,CustomerClaimSchema customerClaimSchema)
	{
		String sql =    "insert into APP_BIZ_GCLAIMDATA(" +
						"Address," +
						"CaseNo," +
						"CenterCode," +
						"CenterName," +
						"ComCName," +
						"ComCode," +
						"ComName1," +
						"ComName2," +
						"CustomerCName," +
						"DamageDate," +
						"LicenseNo," +
						"LinkName," +
						"LINKMOBILE," +
						"OpenNo," +
						"OpenTime," +
						"OrganizeCode," +
						"SurveyCom1," +
						"SurveyCom2," +
						"SurveyId," +
						"SurveyMan," +
						"LINKPHONE," +
						"CREATEDATE " +
						") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate) ";
		try{
			dbpool.prepareInnerStatement(sql);
			int j = 0;
			dbpool.setString(++j,customerClaimSchema.getAddress());
			dbpool.setString(++j,customerClaimSchema.getCaseNo());
			dbpool.setString(++j,customerClaimSchema.getCenterCode());
			dbpool.setString(++j,customerClaimSchema.getCenterName());
			dbpool.setString(++j,customerClaimSchema.getComCName());
			dbpool.setString(++j,customerClaimSchema.getComCode());
			dbpool.setString(++j,customerClaimSchema.getComName1());
			dbpool.setString(++j,customerClaimSchema.getComName2());
			dbpool.setString(++j,customerClaimSchema.getCustomerCName());
			dbpool.setDateTime(++j,new DateTime(customerClaimSchema.getDamageDate()));
			dbpool.setString(++j,customerClaimSchema.getLicenseNo());
			dbpool.setString(++j,customerClaimSchema.getLinkName());
			dbpool.setString(++j,customerClaimSchema.getMobile());
			dbpool.setString(++j,customerClaimSchema.getOpenNo());
			dbpool.setDateTime(++j,new DateTime(customerClaimSchema.getOpenTime()));
			dbpool.setString(++j,customerClaimSchema.getOrganizeCode());
			dbpool.setString(++j,customerClaimSchema.getSurveyCom1());
			dbpool.setString(++j,customerClaimSchema.getSurveyCom2());
			dbpool.setString(++j,customerClaimSchema.getSurveyId());
			dbpool.setString(++j,customerClaimSchema.getSurveyMan());
			dbpool.setString(++j,customerClaimSchema.getTelePhone());
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
			customerClaimSchema.setExportFlag("2");
		}catch(Exception e)
		{
		customerClaimSchema.setExportFlag("3");
		logger.error("XXXXX真实性错误信息：",e);
		logger.error(e+" XXXXX团单写入95510接口表出错的id号是："+customerClaimSchema.getId());
		
		String message = "DBPolicyClaim->insertGroupClaimData()->XXXXX团单写入95510接口表出错的id号是：->"+customerClaimSchema.getId()+e.getMessage();
		TaskMngUtil.insertMidDataLog(customerClaimSchema.getId(), message,e,
				TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
		
		}
	}
	
	/**
	 * XXXXX100%回访，XXXXX个单
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List getSignClaim(DbPool dbpool) throws  Exception
	{


		String sql =  "SELECT a.*," +
				"(SELECT T.VENTUREFLAG FROM PRPCMAIN T WHERE a.POLICYNO = T.POLICYNO) VENTUREFLAG, " +
				" b.cif_custid,b.mobile1,b.mobile2,b.mobile3,b.mobile4,b.kxd1,b.kxd2,b.kxd3,b.kxd4,b.resource1,b.resource2,b.resource3,b.resource4,b.modifydate1,b.modifydate2,b.modifydate3,b.modifydate4  " +
				"FROM PRPCUSTOMERMESSAGE a,prpcustomermessage_cifmobile b " +
				"WHERE a.ISCONTENT = '2' " +
				"AND a.EXPORTMESFLAG = '0' " +
				"AND a.MESSAGETYPE = '2' " +
				"AND a.GROUPFLAG = '0' " +
				"AND a.id = b.id(+) ";
		List list = new ArrayList();
		ResultSet resultSet = dbpool.query(sql);
		CustomerClaimSchema customerClaimSchema = null;
		while(resultSet.next())
		{
			customerClaimSchema = new CustomerClaimSchema();
			customerClaimSchema.setAddress(dbpool.getString(resultSet,"Address"));
			customerClaimSchema.setBirthDate(""+dbpool.getDateTime(resultSet,"BirthDate"));
			customerClaimSchema.setCaseNo(dbpool.getString(resultSet,"CaseNo"));
			customerClaimSchema.setCenterCode(dbpool.getString(resultSet,"CENTERORHANDLCOMCODE"));
			customerClaimSchema.setCenterName(dbpool.getString(resultSet,"CENTERORHANDLCOMNAME"));
			customerClaimSchema.setClaimNo(dbpool.getString(resultSet,"ClaimNo"));
			customerClaimSchema.setComCName(dbpool.getString(resultSet,"COMNAME"));
			customerClaimSchema.setComCode(dbpool.getString(resultSet,"COMCODE"));
			customerClaimSchema.setComName1(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME1"));
			customerClaimSchema.setComName2(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME2"));
			customerClaimSchema.setCustomerCName(dbpool.getString(resultSet,"CUSTOMERNAME"));
			customerClaimSchema.setDamageDate(""+dbpool.getDateTime(resultSet,"DamageDate"));
			customerClaimSchema.setId(dbpool.getString(resultSet,"id"));
			customerClaimSchema.setIdentifyNumber(dbpool.getString(resultSet,"IdentifyNumber"));
			customerClaimSchema.setIdentifyType(dbpool.getString(resultSet,"IdentifyType"));
			customerClaimSchema.setInsurantName(dbpool.getString(resultSet,"InsurantName"));
			customerClaimSchema.setLicenseNo(dbpool.getString(resultSet,"LicenseNo"));
			customerClaimSchema.setLinkName(dbpool.getString(resultSet,"LinkName"));
			customerClaimSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			customerClaimSchema.setNotifyDate(""+dbpool.getDateTime(resultSet,"NotifyDate"));
			customerClaimSchema.setOccupationCode(dbpool.getString(resultSet,"OCCUPATIONNAME"));
			customerClaimSchema.setOpenNo(dbpool.getString(resultSet,"OpenNo"));
			customerClaimSchema.setOpenTime(""+dbpool.getDateTime(resultSet,"OpenTime"));
			customerClaimSchema.setOrganizeCode(dbpool.getString(resultSet,"OrganizeCode"));
			customerClaimSchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
			customerClaimSchema.setSex(dbpool.getString(resultSet,"Sex"));
			customerClaimSchema.setSurveyCom1(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
			customerClaimSchema.setSurveyCom2(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE2"));
			customerClaimSchema.setSurveyId(dbpool.getString(resultSet,"SURVEYORHANDLID"));
			customerClaimSchema.setSurveyMan(dbpool.getString(resultSet,"SURVEYORHANDLNAME"));
			customerClaimSchema.setTelePhone(dbpool.getString(resultSet,"phone"));
			
			customerClaimSchema.setChannelType(dbpool.getString(resultSet,"channelType"));
			customerClaimSchema.setVentureflag(dbpool.getString(resultSet,"ventureflag"));
			
			customerClaimSchema.setCif_custid(dbpool.getString(resultSet,"cif_custid"));
			customerClaimSchema.setCan_custid(dbpool.getString(resultSet,"CUSTOMERCODE"));
			customerClaimSchema.setMobile1(dbpool.getString(resultSet,"mobile1"));
			customerClaimSchema.setMobile2(dbpool.getString(resultSet,"mobile2"));
			customerClaimSchema.setMobile3(dbpool.getString(resultSet,"mobile3"));
			customerClaimSchema.setMobile4(dbpool.getString(resultSet,"mobile"));
			customerClaimSchema.setKxd1(dbpool.getString(resultSet,"kxd1"));
			customerClaimSchema.setKxd2(dbpool.getString(resultSet,"kxd2"));
			customerClaimSchema.setKxd3(dbpool.getString(resultSet,"kxd3"));
			
			customerClaimSchema.setResource1(dbpool.getString(resultSet,"resource1"));
			customerClaimSchema.setResource2(dbpool.getString(resultSet,"resource2"));
			customerClaimSchema.setResource3(dbpool.getString(resultSet,"resource3"));
			
			customerClaimSchema.setModifydate1(""+dbpool.getDateTime(resultSet,"modifydate1"));
			customerClaimSchema.setModifydate2(""+dbpool.getDateTime(resultSet,"modifydate2"));
			customerClaimSchema.setModifydate3(""+dbpool.getDateTime(resultSet,"modifydate3"));
			
			list.add(customerClaimSchema);
		}
		resultSet.close();
		return list;
	}
	/**
	 * XXXXX100%回访，XXXXX团单
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List getGroupClaim(DbPool dbpool) throws  Exception
	{


		String sql = "select a.*, " +
				"b.cif_custid,b.mobile1,b.mobile2,b.mobile3,b.mobile4,b.kxd1,b.kxd2,b.kxd3,b.kxd4,b.resource1,b.resource2,b.resource3,b.resource4,b.modifydate1,b.modifydate2,b.modifydate3,b.modifydate4  " +
				"from prpcustomermessage a ,prpcustomermessage_cifmobile b where a.ISCONTENT = '2' " +
				"and a.EXPORTMESFLAG='0' and a.MESSAGETYPE='2' and a.GROUPFLAG='1' AND a.id = b.id(+)";
		List list = new ArrayList();
		ResultSet resultSet = dbpool.query(sql);
		CustomerClaimSchema customerClaimSchema = null;
		while(resultSet.next())
		{
			customerClaimSchema = new CustomerClaimSchema();
			customerClaimSchema.setAddress(dbpool.getString(resultSet,"Address"));
			customerClaimSchema.setBirthDate(""+dbpool.getDateTime(resultSet,"BirthDate"));
			customerClaimSchema.setCaseNo(dbpool.getString(resultSet,"CaseNo"));
			customerClaimSchema.setCenterCode(dbpool.getString(resultSet,"CENTERORHANDLCOMCODE"));
			customerClaimSchema.setCenterName(dbpool.getString(resultSet,"CENTERORHANDLCOMNAME"));
			customerClaimSchema.setClaimNo(dbpool.getString(resultSet,"ClaimNo"));
			customerClaimSchema.setComCName(dbpool.getString(resultSet,"COMNAME"));
			customerClaimSchema.setComCode(dbpool.getString(resultSet,"COMCODE"));
			customerClaimSchema.setComName1(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME1"));
			customerClaimSchema.setComName2(dbpool.getString(resultSet,"SURVEYORHANDLCOMNAME2"));
			customerClaimSchema.setCustomerCName(dbpool.getString(resultSet,"CUSTOMERNAME"));
			customerClaimSchema.setDamageDate(""+dbpool.getDateTime(resultSet,"DamageDate"));
			customerClaimSchema.setId(dbpool.getString(resultSet,"id"));
			customerClaimSchema.setIdentifyNumber(dbpool.getString(resultSet,"IdentifyNumber"));
			customerClaimSchema.setIdentifyType(dbpool.getString(resultSet,"IdentifyType"));
			customerClaimSchema.setInsurantName(dbpool.getString(resultSet,"InsurantName"));
			customerClaimSchema.setLicenseNo(dbpool.getString(resultSet,"LicenseNo"));
			customerClaimSchema.setLinkName(dbpool.getString(resultSet,"LinkName"));
			customerClaimSchema.setMobile(dbpool.getString(resultSet,"Mobile"));
			customerClaimSchema.setNotifyDate(""+dbpool.getDateTime(resultSet,"NotifyDate"));
			customerClaimSchema.setOccupationCode(dbpool.getString(resultSet,"OCCUPATIONNAME"));
			customerClaimSchema.setOpenNo(dbpool.getString(resultSet,"OpenNo"));
			customerClaimSchema.setOpenTime(""+dbpool.getDateTime(resultSet,"OpenTime"));
			customerClaimSchema.setOrganizeCode(dbpool.getString(resultSet,"OrganizeCode"));
			customerClaimSchema.setPolicyNo(dbpool.getString(resultSet,"PolicyNo"));
			customerClaimSchema.setSex(dbpool.getString(resultSet,"Sex"));
			customerClaimSchema.setSurveyCom1(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE1"));
			customerClaimSchema.setSurveyCom2(dbpool.getString(resultSet,"SURVEYORHANDLCOMCODE2"));
			customerClaimSchema.setSurveyId(dbpool.getString(resultSet,"SURVEYORHANDLID"));
			customerClaimSchema.setSurveyMan(dbpool.getString(resultSet,"SURVEYORHANDLNAME"));
			customerClaimSchema.setTelePhone(dbpool.getString(resultSet,"phone"));
			
			customerClaimSchema.setCif_custid(dbpool.getString(resultSet,"cif_custid"));
			customerClaimSchema.setCan_custid(dbpool.getString(resultSet,"CUSTOMERCODE"));
			customerClaimSchema.setMobile1(dbpool.getString(resultSet,"mobile1"));
			customerClaimSchema.setMobile2(dbpool.getString(resultSet,"mobile2"));
			customerClaimSchema.setMobile3(dbpool.getString(resultSet,"mobile3"));
			customerClaimSchema.setMobile4(dbpool.getString(resultSet,"mobile"));
			customerClaimSchema.setKxd1(dbpool.getString(resultSet,"kxd1"));
			customerClaimSchema.setKxd2(dbpool.getString(resultSet,"kxd2"));
			customerClaimSchema.setKxd3(dbpool.getString(resultSet,"kxd3"));
			
			customerClaimSchema.setResource1(dbpool.getString(resultSet,"resource1"));
			customerClaimSchema.setResource2(dbpool.getString(resultSet,"resource2"));
			customerClaimSchema.setResource3(dbpool.getString(resultSet,"resource3"));
			
			customerClaimSchema.setModifydate1(""+dbpool.getDateTime(resultSet,"modifydate1"));
			customerClaimSchema.setModifydate2(""+dbpool.getDateTime(resultSet,"modifydate2"));
			customerClaimSchema.setModifydate3(""+dbpool.getDateTime(resultSet,"modifydate3"));
			
			list.add(customerClaimSchema);
		}
		resultSet.close();
		return list;
	}
	
	/**
	 * XXXXX100%回访，XXXXX个单
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	public void insertSignClaim(DbPool dbpool,CustomerClaimSchema customerClaimSchema )
	{
		String sql =    "insert into APP_BIZ_LMPCLAIMDATA(" +
						"Address," +
						"BirthDate," +
						"CaseNo," +
						"CenterCode," +
						"centerName," +
						"ClaimNo," +
						"ComCName," +
						"ComCode," +
						"ComName1," +
						"ComName2," +
						"DamageDate," +
						"IdentifyNumber," +
						"IdentifyType," +
						"InsurantName," +
						"LicenseNo," +
						"Mobile," +
						"NotifyDate," +
						"OccupationCode," +
						"OpenNo," +
						"OpenTime," +
						"PolicyNo," +
						"Sex," +
						"SurveyCom1," +
						"SurveyCom2," +
						"SurveyId," +
						"SurveyMan," +
						"TelePhone," +
						"channelType," +
						"ventureflag," +
						"CREATEDATE " +
						") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate) ";
		try{
			dbpool.prepareInnerStatement(sql);
			int j = 0;
			dbpool.setString(++j,customerClaimSchema.getAddress());
			dbpool.setDateTime(++j,new DateTime(customerClaimSchema.getBirthDate()));
			dbpool.setString(++j,customerClaimSchema.getCaseNo());
			dbpool.setString(++j,customerClaimSchema.getCenterCode());
			dbpool.setString(++j,customerClaimSchema.getCenterName());
			dbpool.setString(++j,customerClaimSchema.getClaimNo());
			dbpool.setString(++j,customerClaimSchema.getComCName());
			dbpool.setString(++j,customerClaimSchema.getComCode());
			dbpool.setString(++j,customerClaimSchema.getComName1());
			dbpool.setString(++j,customerClaimSchema.getComName2());
			dbpool.setDateTime(++j,new DateTime(customerClaimSchema.getDamageDate()));
			dbpool.setString(++j,customerClaimSchema.getIdentifyNumber());
			dbpool.setString(++j,customerClaimSchema.getIdentifyType());
			dbpool.setString(++j,customerClaimSchema.getInsurantName());
			dbpool.setString(++j,customerClaimSchema.getLicenseNo());
			dbpool.setString(++j,customerClaimSchema.getMobile());
			dbpool.setDateTime(++j,new DateTime(customerClaimSchema.getNotifyDate()));
			dbpool.setString(++j,customerClaimSchema.getOccupationCode());
			dbpool.setString(++j,customerClaimSchema.getOpenNo());
			dbpool.setDateTime(++j,new DateTime(customerClaimSchema.getOpenTime()));
			dbpool.setString(++j,customerClaimSchema.getPolicyNo());
			dbpool.setString(++j,customerClaimSchema.getSex());
			dbpool.setString(++j,customerClaimSchema.getSurveyCom1());
			dbpool.setString(++j,customerClaimSchema.getSurveyCom2());
			dbpool.setString(++j,customerClaimSchema.getSurveyId());
			dbpool.setString(++j,customerClaimSchema.getSurveyMan());
			dbpool.setString(++j,customerClaimSchema.getTelePhone());
			
			dbpool.setString(++j,customerClaimSchema.getChannelType());
			dbpool.setString(++j,customerClaimSchema.getVentureflag());
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
			customerClaimSchema.setExportMesFlag("2");
		}catch(Exception e)
		{
			customerClaimSchema.setExportMesFlag("3");
			logger.error("XXXXX真实性错误信息：",e);
			logger.error(e+" XXXXX个单写入95510接口表出错的id号是："+customerClaimSchema.getId());
			
			String message = "DBPolicyClaim->insertSignClaimData()->XXXXX个单写入95510接口表出错的id号是：->"+customerClaimSchema.getId()+e.getMessage();
			TaskMngUtil.insertMidDataLog(customerClaimSchema.getId(), message,e,
					TaskMngUtil.CLAIMISSATISFIED_JOBNAME);
			
		}
	}
	/**
	 * XXXXX100%回访，XXXXX团单
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	public void insertGroupClaim(DbPool dbpool,CustomerClaimSchema customerClaimSchema)
	{
		String sql =    "insert into APP_BIZ_LMGCLAIMDATA(" +
						"Address," +
						"CaseNo," +
						"CenterCode," +
						"CenterName," +
						"ComCName," +
						"ComCode," +
						"ComName1," +
						"ComName2," +
						"CustomerCName," +
						"DamageDate," +
						"LicenseNo," +
						"LinkName," +
						"LINKMOBILE," +
						"OpenNo," +
						"OpenTime," +
						"OrganizeCode," +
						"SurveyCom1," +
						"SurveyCom2," +
						"SurveyId," +
						"SurveyMan," +
						"LINKPHONE," +
						"CREATEDATE " +
						") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate) ";
		try{
			dbpool.prepareInnerStatement(sql);
			int j = 0;
			dbpool.setString(++j,customerClaimSchema.getAddress());
			dbpool.setString(++j,customerClaimSchema.getCaseNo());
			dbpool.setString(++j,customerClaimSchema.getCenterCode());
			dbpool.setString(++j,customerClaimSchema.getCenterName());
			dbpool.setString(++j,customerClaimSchema.getComCName());
			dbpool.setString(++j,customerClaimSchema.getComCode());
			dbpool.setString(++j,customerClaimSchema.getComName1());
			dbpool.setString(++j,customerClaimSchema.getComName2());
			dbpool.setString(++j,customerClaimSchema.getCustomerCName());
			dbpool.setDateTime(++j,new DateTime(customerClaimSchema.getDamageDate()));
			dbpool.setString(++j,customerClaimSchema.getLicenseNo());
			dbpool.setString(++j,customerClaimSchema.getLinkName());
			dbpool.setString(++j,customerClaimSchema.getMobile());
			dbpool.setString(++j,customerClaimSchema.getOpenNo());
			dbpool.setDateTime(++j,new DateTime(customerClaimSchema.getOpenTime()));
			dbpool.setString(++j,customerClaimSchema.getOrganizeCode());
			dbpool.setString(++j,customerClaimSchema.getSurveyCom1());
			dbpool.setString(++j,customerClaimSchema.getSurveyCom2());
			dbpool.setString(++j,customerClaimSchema.getSurveyId());
			dbpool.setString(++j,customerClaimSchema.getSurveyMan());
			dbpool.setString(++j,customerClaimSchema.getTelePhone());
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
			customerClaimSchema.setExportMesFlag("2");
		}catch(Exception e)
		{
		customerClaimSchema.setExportMesFlag("3");
		logger.error("XXXXX真实性错误信息：",e);
		logger.error(e+" XXXXX团单写入95510接口表出错的id号是："+customerClaimSchema.getId());
		
		String message = "DBPolicyClaim->insertGroupClaimData()->XXXXX团单写入95510接口表出错的id号是：->"+customerClaimSchema.getId()+e.getMessage();
		TaskMngUtil.insertMidDataLog(customerClaimSchema.getId(), message,e,
				TaskMngUtil.CLAIMISSATISFIED_JOBNAME);
		
		}
	}
	
	/**
	 * XX个单cif app_biz_cb_personl_tel
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	@SuppressWarnings("deprecation")
	public void insertCifcbTel(DbPool dbpool,CustomerPolicySchema customerPolicySchema)
	{
	        StringBuffer buffer = new StringBuffer(200);
	        buffer.append("INSERT INTO app_biz_cb_personl_tel (");
	        buffer.append("Id,");
	        buffer.append("Policyno,");
	        buffer.append("Cif_custid,");
	        buffer.append("Can_custid,");
	        buffer.append("Mobile1,");
	        buffer.append("Mobile2,");
	        buffer.append("Mobile3,");
	        buffer.append("Mobile4,");
	        buffer.append("Kxd1,");
	        buffer.append("Kxd2,");
	        buffer.append("Kxd3,");
	        buffer.append("Kxd4,");
	        buffer.append("Resource1,");
	        buffer.append("Resource2,");
	        buffer.append("Resource3,");
	        buffer.append("Resource4, ");
	        buffer.append("Modifydate1,");
	        buffer.append("Modifydate2,");
	        buffer.append("Modifydate3,");
	        buffer.append("Modifydate4 ");
	        buffer.append(") ");
	        if(logger.isDebugEnabled()){
	            StringBuffer debugBuffer =  new StringBuffer(buffer.length()*4);
	            debugBuffer.append(buffer.toString());
	            debugBuffer.append("VALUES(");
	            debugBuffer.append("'").append(customerPolicySchema.getId()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getPolicyNo()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getCif_custid()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getCan_custid()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getMobile1()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getMobile2()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getMobile3()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getMobile4()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getKxd1()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getKxd2()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getKxd3()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getKxd4()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getResource1()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getResource2()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getResource3()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getResource4()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getModifydate1()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getModifydate2()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getModifydate3()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getModifydate4()).append("')");
	            logger.debug(debugBuffer.toString());
	        }
	        buffer.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	    try{
	    	dbpool.prepareInnerStatement(buffer.toString());
	        int j = 0;
	        dbpool.setString(++j,customerPolicySchema.getId());
	        dbpool.setString(++j,customerPolicySchema.getPolicyNo());
	        dbpool.setString(++j,customerPolicySchema.getCif_custid());
	        dbpool.setString(++j,customerPolicySchema.getCan_custid());
	        dbpool.setString(++j,customerPolicySchema.getMobile1());
	        dbpool.setString(++j,customerPolicySchema.getMobile2());
	        dbpool.setString(++j,customerPolicySchema.getMobile3());
	        dbpool.setString(++j,customerPolicySchema.getMobile4());
	        dbpool.setString(++j,customerPolicySchema.getKxd1());
	        dbpool.setString(++j,customerPolicySchema.getKxd2());
	        dbpool.setString(++j,customerPolicySchema.getKxd3());
	        dbpool.setString(++j,customerPolicySchema.getKxd4());
	        dbpool.setString(++j,customerPolicySchema.getResource1());
	        dbpool.setString(++j,customerPolicySchema.getResource2());
	        dbpool.setString(++j,customerPolicySchema.getResource3());
	        dbpool.setString(++j,customerPolicySchema.getResource4());
	        dbpool.setDateTime(++j, new DateTime(customerPolicySchema.getModifydate1()));
			dbpool.setDateTime(++j, new DateTime(customerPolicySchema.getModifydate2()));
			dbpool.setDateTime(++j, new DateTime(customerPolicySchema.getModifydate3()));
			dbpool.setDateTime(++j, new DateTime(customerPolicySchema.getModifydate4()));
	        dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
	    }
		catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			logger.error(e+"写入95510接口表app_biz_cb_personl_tel出错的id号是："+customerPolicySchema.getId());
			String message = "DBPolicyClaim->insertCifTel()->写入95510接口表app_biz_cb_personl_tel出错的id号是："+customerPolicySchema.getId()+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPolicyClaim.insertSignPolicyData", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
		}
	}
	
	/**
	 * XXXXX个单cif app_biz_lp_personl_tel
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	@SuppressWarnings("deprecation")
	public void insertCiflpTel(DbPool dbpool,CustomerClaimSchema customerClaimSchema)
	{
	        StringBuffer buffer = new StringBuffer(200);
	        buffer.append("INSERT INTO app_biz_lp_personl_tel(");
	        buffer.append("Id,");
	        buffer.append("caseno,");
	        buffer.append("Policyno,");
	        buffer.append("Cif_custid,");
	        buffer.append("Can_custid,");
	        buffer.append("Mobile1,");
	        buffer.append("Mobile2,");
	        buffer.append("Mobile3,");
	        buffer.append("Mobile4,");
	        buffer.append("Kxd1,");
	        buffer.append("Kxd2,");
	        buffer.append("Kxd3,");
	        buffer.append("Kxd4,");
	        buffer.append("Resource1,");
	        buffer.append("Resource2,");
	        buffer.append("Resource3,");
	        buffer.append("Resource4, ");
	        buffer.append("Modifydate1,");
	        buffer.append("Modifydate2,");
	        buffer.append("Modifydate3,");
	        buffer.append("Modifydate4 ");
	        buffer.append(") ");
	        if(logger.isDebugEnabled()){
	            StringBuffer debugBuffer =  new StringBuffer(buffer.length()*4);
	            debugBuffer.append(buffer.toString());
	            debugBuffer.append("VALUES(");
	            debugBuffer.append("'").append(customerClaimSchema.getId()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getCaseNo()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getPolicyNo()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getCif_custid()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getCan_custid()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getMobile1()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getMobile2()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getMobile3()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getMobile4()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getKxd1()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getKxd2()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getKxd3()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getKxd4()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getResource1()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getResource2()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getResource3()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getResource4()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getModifydate1()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getModifydate2()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getModifydate3()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getModifydate4()).append("')");
	            logger.debug(debugBuffer.toString());
	        }
	        buffer.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	    try{
	    	dbpool.prepareInnerStatement(buffer.toString());
	        int j = 0;
	        dbpool.setString(++j,customerClaimSchema.getId());
	        dbpool.setString(++j,customerClaimSchema.getCaseNo());
	        dbpool.setString(++j,customerClaimSchema.getPolicyNo());
	        dbpool.setString(++j,customerClaimSchema.getCif_custid());
	        dbpool.setString(++j,customerClaimSchema.getCan_custid());
	        dbpool.setString(++j,customerClaimSchema.getMobile1());
	        dbpool.setString(++j,customerClaimSchema.getMobile2());
	        dbpool.setString(++j,customerClaimSchema.getMobile3());
	        dbpool.setString(++j,customerClaimSchema.getMobile4());
	        dbpool.setString(++j,customerClaimSchema.getKxd1());
	        dbpool.setString(++j,customerClaimSchema.getKxd2());
	        dbpool.setString(++j,customerClaimSchema.getKxd3());
	        dbpool.setString(++j,customerClaimSchema.getKxd4());
	        dbpool.setString(++j,customerClaimSchema.getResource1());
	        dbpool.setString(++j,customerClaimSchema.getResource2());
	        dbpool.setString(++j,customerClaimSchema.getResource3());
	        dbpool.setString(++j,customerClaimSchema.getResource4());
	        dbpool.setDateTime(++j, new DateTime(customerClaimSchema.getModifydate1()));
			dbpool.setDateTime(++j, new DateTime(customerClaimSchema.getModifydate2()));
			dbpool.setDateTime(++j, new DateTime(customerClaimSchema.getModifydate3()));
			dbpool.setDateTime(++j, new DateTime(customerClaimSchema.getModifydate4()));
	        dbpool.executePreparedUpdate();
	        dbpool.closePreparedStatement();
	    }
		catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			logger.error(e+"写入95510接口表app_biz_lp_personl_tel出错的id号是："+customerClaimSchema.getId());
			String message = "DBPolicyClaim->insertCiflpTel()->写入95510接口表app_biz_lp_personl_tel出错的id号是："+customerClaimSchema.getId()+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPolicyClaim.insertCiflpTel", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
		}
	}
	
	/**
	 * XXXXX100%回访个单cif APP_BIZ_LPM_PERSONL_TEL
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	@SuppressWarnings("deprecation")
	public void insertCifLPMTel(DbPool dbpool,CustomerClaimSchema customerClaimSchema)
	{
	        StringBuffer buffer = new StringBuffer(200);
	        buffer.append("INSERT INTO APP_BIZ_LPM_PERSONL_TEL(");
	        buffer.append("Id,");
	        buffer.append("caseno,");
	        buffer.append("Policyno,");
	        buffer.append("Cif_custid,");
	        buffer.append("Can_custid,");
	        buffer.append("Mobile1,");
	        buffer.append("Mobile2,");
	        buffer.append("Mobile3,");
	        buffer.append("Mobile4,");
	        buffer.append("Kxd1,");
	        buffer.append("Kxd2,");
	        buffer.append("Kxd3,");
	        buffer.append("Kxd4,");
	        buffer.append("Resource1,");
	        buffer.append("Resource2,");
	        buffer.append("Resource3,");
	        buffer.append("Resource4, ");
	        buffer.append("Modifydate1,");
	        buffer.append("Modifydate2,");
	        buffer.append("Modifydate3,");
	        buffer.append("Modifydate4 ");
	        buffer.append(") ");
	        if(logger.isDebugEnabled()){
	            StringBuffer debugBuffer =  new StringBuffer(buffer.length()*4);
	            debugBuffer.append(buffer.toString());
	            debugBuffer.append("VALUES(");
	            debugBuffer.append("'").append(customerClaimSchema.getId()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getCaseNo()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getPolicyNo()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getCif_custid()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getCan_custid()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getMobile1()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getMobile2()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getMobile3()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getMobile4()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getKxd1()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getKxd2()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getKxd3()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getKxd4()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getResource1()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getResource2()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getResource3()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getResource4()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getModifydate1()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getModifydate2()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getModifydate3()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getModifydate4()).append("')");
	            logger.debug(debugBuffer.toString());
	        }
	        buffer.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	    try{
	    	dbpool.prepareInnerStatement(buffer.toString());
	        int j = 0;
	        dbpool.setString(++j,customerClaimSchema.getId());
	        dbpool.setString(++j,customerClaimSchema.getCaseNo());
	        dbpool.setString(++j,customerClaimSchema.getPolicyNo());
	        dbpool.setString(++j,customerClaimSchema.getCif_custid());
	        dbpool.setString(++j,customerClaimSchema.getCan_custid());
	        dbpool.setString(++j,customerClaimSchema.getMobile1());
	        dbpool.setString(++j,customerClaimSchema.getMobile2());
	        dbpool.setString(++j,customerClaimSchema.getMobile3());
	        dbpool.setString(++j,customerClaimSchema.getMobile4());
	        dbpool.setString(++j,customerClaimSchema.getKxd1());
	        dbpool.setString(++j,customerClaimSchema.getKxd2());
	        dbpool.setString(++j,customerClaimSchema.getKxd3());
	        dbpool.setString(++j,customerClaimSchema.getKxd4());
	        dbpool.setString(++j,customerClaimSchema.getResource1());
	        dbpool.setString(++j,customerClaimSchema.getResource2());
	        dbpool.setString(++j,customerClaimSchema.getResource3());
	        dbpool.setString(++j,customerClaimSchema.getResource4());
	        dbpool.setDateTime(++j, new DateTime(customerClaimSchema.getModifydate1()));
			dbpool.setDateTime(++j, new DateTime(customerClaimSchema.getModifydate2()));
			dbpool.setDateTime(++j, new DateTime(customerClaimSchema.getModifydate3()));
			dbpool.setDateTime(++j, new DateTime(customerClaimSchema.getModifydate4()));
	        dbpool.executePreparedUpdate();
	        dbpool.closePreparedStatement();
	    }
		catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			logger.error(e+"写入95510接口表APP_BIZ_LPM_PERSONL_TEL出错的id号是："+customerClaimSchema.getId());
			String message = "DBPolicyClaim->insertCifLPMTel()->写入95510接口表APP_BIZ_LPM_PERSONL_TEL出错的id号是："+customerClaimSchema.getId()+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPolicyClaim.insertCifLPMTel", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
		}
	}
	
	/**
	 * XXXXX100%回访团单cif APP_BIZ_LPM_GROUP_TEL
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	@SuppressWarnings("deprecation")
	public void insertCifLPMGroupTel(DbPool dbpool,CustomerClaimSchema customerClaimSchema)
	{
	        StringBuffer buffer = new StringBuffer(200);
	        buffer.append("INSERT INTO APP_BIZ_LPM_GROUP_TEL(");
	        buffer.append("Id,");
	        buffer.append("caseno,");
	        buffer.append("Policyno,");
	        buffer.append("Cif_custid,");
	        buffer.append("Can_custid,");
	        buffer.append("Mobile1,");
	        buffer.append("Mobile2,");
	        buffer.append("Mobile3,");
	        buffer.append("Mobile4,");
	        buffer.append("Kxd1,");
	        buffer.append("Kxd2,");
	        buffer.append("Kxd3,");
	        buffer.append("Kxd4,");
	        buffer.append("Resource1,");
	        buffer.append("Resource2,");
	        buffer.append("Resource3,");
	        buffer.append("Resource4, ");
	        buffer.append("Modifydate1,");
	        buffer.append("Modifydate2,");
	        buffer.append("Modifydate3,");
	        buffer.append("Modifydate4 ");
	        buffer.append(") ");
	        if(logger.isDebugEnabled()){
	            StringBuffer debugBuffer =  new StringBuffer(buffer.length()*4);
	            debugBuffer.append(buffer.toString());
	            debugBuffer.append("VALUES(");
	            debugBuffer.append("'").append(customerClaimSchema.getId()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getCaseNo()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getPolicyNo()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getCif_custid()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getCan_custid()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getMobile1()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getMobile2()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getMobile3()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getMobile4()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getKxd1()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getKxd2()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getKxd3()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getKxd4()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getResource1()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getResource2()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getResource3()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getResource4()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getModifydate1()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getModifydate2()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getModifydate3()).append("',");
	            debugBuffer.append("'").append(customerClaimSchema.getModifydate4()).append("')");
	            logger.debug(debugBuffer.toString());
	        }
	        buffer.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	    try{
	    	dbpool.prepareInnerStatement(buffer.toString());
	        int j = 0;
	        dbpool.setString(++j,customerClaimSchema.getId());
	        dbpool.setString(++j,customerClaimSchema.getCaseNo());
	        dbpool.setString(++j,customerClaimSchema.getPolicyNo());
	        dbpool.setString(++j,customerClaimSchema.getCif_custid());
	        dbpool.setString(++j,customerClaimSchema.getCan_custid());
	        dbpool.setString(++j,customerClaimSchema.getMobile1());
	        dbpool.setString(++j,customerClaimSchema.getMobile2());
	        dbpool.setString(++j,customerClaimSchema.getMobile3());
	        dbpool.setString(++j,customerClaimSchema.getMobile4());
	        dbpool.setString(++j,customerClaimSchema.getKxd1());
	        dbpool.setString(++j,customerClaimSchema.getKxd2());
	        dbpool.setString(++j,customerClaimSchema.getKxd3());
	        dbpool.setString(++j,customerClaimSchema.getKxd4());
	        dbpool.setString(++j,customerClaimSchema.getResource1());
	        dbpool.setString(++j,customerClaimSchema.getResource2());
	        dbpool.setString(++j,customerClaimSchema.getResource3());
	        dbpool.setString(++j,customerClaimSchema.getResource4());
	        dbpool.setDateTime(++j, new DateTime(customerClaimSchema.getModifydate1()));
			dbpool.setDateTime(++j, new DateTime(customerClaimSchema.getModifydate2()));
			dbpool.setDateTime(++j, new DateTime(customerClaimSchema.getModifydate3()));
			dbpool.setDateTime(++j, new DateTime(customerClaimSchema.getModifydate4()));
	        dbpool.executePreparedUpdate();
	        dbpool.closePreparedStatement();
	    }
		catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			logger.error(e+"写入95510接口表APP_BIZ_LPM_GROUP_TEL出错的id号是："+customerClaimSchema.getId());
			String message = "DBPolicyClaim->insertCifLPMGroupTel()->写入95510接口表APP_BIZ_LPM_PERSONL_TEL出错的id号是："+customerClaimSchema.getId()+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPolicyClaim.insertCifLPMTel", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
		}
	}
	
	/**
	 * XX需求
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	public void insertNewPolicyData(DbPool dbpool,CustomerPolicySchema customerPolicySchema)
	{
		String sql =    "insert into APP_BIZ_HXFDXPSIGNDATA(" +
						"Address," +
						"BirthDate," +
						"BusinessNature," +
						"ComCName," +
						"ComCode," +
						"ComName," +
						"ComName1," +
						"ComName2," +
						"CustomerCName," +
						"CustomerId," +
						"Handler," +
						"HandlerCom," +
						"HandlerCom1," +
						"HandlerCom2," +
						"HandlerId," +
						"IdentifyNumber," +
						"IdentifyType," +
						"Mobile," +
						"OccupationCode," +
						"Operator," +
						"OperatorId," +
						"Phone," +
						"PolicyNo," +
						"Sex," +
						"UnderWriteEndDate," +
						"channelType," +
						"ventureflag," +
						"CREATEDATE," +
						"insurebranchid," +
						"insurebranchid2," +
						"surebranchname," +
						"surebranch2name," +
						"managername," +   
						"managertel" + 
						") values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?) ";
		try{
			dbpool.prepareInnerStatement(sql);
			int j = 0;
			dbpool.setString(++j,customerPolicySchema.getAddress());
			dbpool.setDateTime(++j,new DateTime(customerPolicySchema.getBirthDate()));
			dbpool.setString(++j,customerPolicySchema.getBusinessNature());
			dbpool.setString(++j,customerPolicySchema.getComCName());
			dbpool.setString(++j,customerPolicySchema.getComCode());
			dbpool.setString(++j,customerPolicySchema.getComName());
			dbpool.setString(++j,customerPolicySchema.getComName1());
			dbpool.setString(++j,customerPolicySchema.getComName2());
			dbpool.setString(++j,customerPolicySchema.getCustomerCName());
			dbpool.setString(++j,customerPolicySchema.getCustomerId());
			dbpool.setString(++j,customerPolicySchema.getHandler());
			dbpool.setString(++j,customerPolicySchema.getHandlerCom());
			dbpool.setString(++j,customerPolicySchema.getHandlerCom1());
			dbpool.setString(++j,customerPolicySchema.getHandlerCom2());
			dbpool.setString(++j,customerPolicySchema.getHandlerId());
			dbpool.setString(++j,customerPolicySchema.getIdentifyNumber());
			dbpool.setString(++j,customerPolicySchema.getIdentifyType());
			dbpool.setString(++j,customerPolicySchema.getMobile());
			dbpool.setString(++j,customerPolicySchema.getOccupationCode());
			dbpool.setString(++j,customerPolicySchema.getOperator());
			dbpool.setString(++j,customerPolicySchema.getOperatorId());
			dbpool.setString(++j,customerPolicySchema.getPhone());
			dbpool.setString(++j,customerPolicySchema.getPolicyNo());
			dbpool.setString(++j,customerPolicySchema.getSex());
			dbpool.setDateTime(++j,new DateTime(customerPolicySchema.getUnderWriteEndDate()));
			dbpool.setString(++j,customerPolicySchema.getChannelType());
			dbpool.setString(++j,customerPolicySchema.getVentureflag());
			dbpool.setString(++j,customerPolicySchema.getInsurebranchid());
			dbpool.setString(++j,customerPolicySchema.getInsurebranchid2());
			dbpool.setString(++j,customerPolicySchema.getSurebranchname());
			dbpool.setString(++j,customerPolicySchema.getSurebranch2name());
			dbpool.setString(++j,customerPolicySchema.getManagername());
			dbpool.setString(++j,customerPolicySchema.getManagertel());
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			logger.error(e+"XX需求95510接口表出错的id号是："+customerPolicySchema.getId());
			
			String message = "DBPolicyClaim->insertNewPolicyData()->XX需求写入95510接口表出错的id号是："+customerPolicySchema.getId()+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPolicyClaim.insertNewPolicyData", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
			
		}
	}
	
	/**
	 * XX需求APP_BIZ_HXFDX_PERSONL_TEL
	 * @param dbpool
	 * @param customerPolicySchema
	 */
	@SuppressWarnings("deprecation")
	public void insertCifNewPolicy(DbPool dbpool,CustomerPolicySchema customerPolicySchema)
	{
	        StringBuffer buffer = new StringBuffer(200);
	        buffer.append("INSERT INTO APP_BIZ_HXFDX_PERSONL_TEL(");
	        buffer.append("Id,");
	        buffer.append("Policyno,");
	        buffer.append("Cif_custid,");
	        buffer.append("Can_custid,");
	        buffer.append("Mobile1,");
	        buffer.append("Mobile2,");
	        buffer.append("Mobile3,");
	        buffer.append("Mobile4,");
	        buffer.append("Kxd1,");
	        buffer.append("Kxd2,");
	        buffer.append("Kxd3,");
	        buffer.append("Kxd4,");
	        buffer.append("Resource1,");
	        buffer.append("Resource2,");
	        buffer.append("Resource3,");
	        buffer.append("Resource4, ");
	        buffer.append("Modifydate1,");
	        buffer.append("Modifydate2,");
	        buffer.append("Modifydate3,");
	        buffer.append("Modifydate4 ");
	        buffer.append(") ");
	        if(logger.isDebugEnabled()){
	            StringBuffer debugBuffer =  new StringBuffer(buffer.length()*4);
	            debugBuffer.append(buffer.toString());
	            debugBuffer.append("VALUES(");
	            debugBuffer.append("'").append(customerPolicySchema.getId()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getPolicyNo()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getCif_custid()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getCan_custid()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getMobile1()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getMobile2()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getMobile3()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getMobile4()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getKxd1()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getKxd2()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getKxd3()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getKxd4()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getResource1()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getResource2()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getResource3()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getResource4()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getModifydate1()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getModifydate2()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getModifydate3()).append("',");
	            debugBuffer.append("'").append(customerPolicySchema.getModifydate4()).append("')");
	            logger.debug(debugBuffer.toString());
	        }
	        buffer.append("VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	    try{
	    	dbpool.prepareInnerStatement(buffer.toString());
	        int j = 0;
	        dbpool.setString(++j,customerPolicySchema.getId());
	        dbpool.setString(++j,customerPolicySchema.getPolicyNo());
	        dbpool.setString(++j,customerPolicySchema.getCif_custid());
	        dbpool.setString(++j,customerPolicySchema.getCan_custid());
	        dbpool.setString(++j,customerPolicySchema.getMobile1());
	        dbpool.setString(++j,customerPolicySchema.getMobile2());
	        dbpool.setString(++j,customerPolicySchema.getMobile3());
	        dbpool.setString(++j,customerPolicySchema.getMobile4());
	        dbpool.setString(++j,customerPolicySchema.getKxd1());
	        dbpool.setString(++j,customerPolicySchema.getKxd2());
	        dbpool.setString(++j,customerPolicySchema.getKxd3());
	        dbpool.setString(++j,customerPolicySchema.getKxd4());
	        dbpool.setString(++j,customerPolicySchema.getResource1());
	        dbpool.setString(++j,customerPolicySchema.getResource2());
	        dbpool.setString(++j,customerPolicySchema.getResource3());
	        dbpool.setString(++j,customerPolicySchema.getResource4());
	        dbpool.setDateTime(++j, new DateTime(customerPolicySchema.getModifydate1()));
			dbpool.setDateTime(++j, new DateTime(customerPolicySchema.getModifydate2()));
			dbpool.setDateTime(++j, new DateTime(customerPolicySchema.getModifydate3()));
			dbpool.setDateTime(++j, new DateTime(customerPolicySchema.getModifydate4()));
	        dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
	    }
		catch(Exception e)
		{
			logger.error("XXXXX真实性错误信息：",e);
			logger.error(e+"写入95510接口表app_biz_hxfdx_personl_tel出错的id号是："+customerPolicySchema.getId());
			String message = "DBPolicyClaim->insertCifNewPolicy()->写入95510接口表app_biz_hxfdx_personl_tel出错的id号是："+customerPolicySchema.getId()+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBPolicyClaim.insertCifNewPolicy", message,e,
					TaskMngUtil.CUSTOMERREALCOMPLETE_JOBNAME);
		}
	}
}
