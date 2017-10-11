package com.sp.customerreal.dbsvr;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sp.customerreal.schema.FlexSeCmSchema;
import com.sp.customerreal.schema.PrpCustomerMessageSchema;
import com.sp.sysframework.reference.AppConfig;
import com.sp.taskmng.util.TaskMngUtil;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;

public class DBSendMessage {
	private final Log logger = LogFactory.getLog(getClass());
	/**
	 * ����Flex_Se_Cm
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List findFlexSeCmInfo(DbPool dbpool,String messageType) throws Exception
	{
		List list = new ArrayList();
		
		String sql = "select * from PrpCustomerMessage where MessageFlag='-1' and isrepeatphone='0' and " +
				     "phonevisitflag='2' and messageType = ? and createddate > sysdate-60 ";



		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,messageType);
		ResultSet resultSet =  dbpool.executePreparedQuery();
		
		FlexSeCmSchema flexSeCmSchema = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String day = simpleDateFormat.format(new Date());
		String company =  AppConfig.get("sysconst.SHORTINS_CUSTOMER_COMPANY");
		
		if(company==null || "".equals(company))
		{
			logger.info("ȡ�������ѻ����������Ͷ���!");
			return null;
		}
		while(resultSet.next())
		{
			flexSeCmSchema = new FlexSeCmSchema();
			flexSeCmSchema.setCompany(company);
			flexSeCmSchema.setId(dbpool.getString(resultSet,"SendSeq")); 
			
			flexSeCmSchema.setPhoneCode(dbpool.getString(resultSet,"mobile"));
			
			flexSeCmSchema.setSendText(dbpool.getString(resultSet,"Message"));
			flexSeCmSchema.setSendTime(day);
			flexSeCmSchema.setServiceId("1001");
			if("1".equals(dbpool.getString(resultSet,"MessageType")))
			{
				flexSeCmSchema.setSmsType("X01");
			}
			if("2".equals(dbpool.getString(resultSet,"MessageType")))
			{
				flexSeCmSchema.setSmsType("X02");
			}
			flexSeCmSchema.setUserId("cx");
			list.add(flexSeCmSchema);
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return list;
	}
	
	/**����֣�ݿ���������Ѷ������ݹ��� add by linqian 20131213
	 * ����Flex_Se_Cm
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List findFlexSeCmInfoSpecific(DbPool dbpool,String messageType) throws Exception
	{
		List list = new ArrayList();
		String message="f";
		String sql = "select * from PrpCustomerMessage where MessageFlag1='-1' and isrepeatphone='0' " +
				"and comCode like'0651%' and messageType = ? and createddate > sysdate-60" ;
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,messageType);
		ResultSet resultSet =  dbpool.executePreparedQuery();
		FlexSeCmSchema flexSeCmSchema = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String day = simpleDateFormat.format(new Date());
		
		String company =  AppConfig.get("sysconst.CUSTOMER_SPECIALFEE_COMPANY");
		
		if(company==null || "".equals(company))
		{
			logger.info("ȡ�������ѻ����������Ͷ���!");
			return null;
		}
		while(resultSet.next())
		{
			flexSeCmSchema = new FlexSeCmSchema();
			flexSeCmSchema.setCompany(company);
			flexSeCmSchema.setId(dbpool.getString(resultSet,"SendSeqSpecific")); 
			flexSeCmSchema.setPhoneCode(dbpool.getString(resultSet,"mobile"));
			flexSeCmSchema.setSendText(message);
			flexSeCmSchema.setSendTime(day);
			flexSeCmSchema.setServiceId("1001");
			if("1".equals(dbpool.getString(resultSet,"MessageType")))
			{
				flexSeCmSchema.setSmsType("C51");
			}
			flexSeCmSchema.setUserId("cx");
			list.add(flexSeCmSchema);
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return list;
	}
	
	/**����֣�ݿ���������Ѷ������ݹ��� add by linqian 20131218
	 * ����Flex_Se_Cm
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List findFlexSeCmInfoSpecificH(DbPool dbpool,String messageType,String strBDate,String strNDate) throws Exception
	{
		List list = new ArrayList();
		String message="fd";
		String sql = "select * from PrpCustomerMessage where MessageFlag1='-1' and isrepeatphone='0' " +
				"and comCode like'0651%' and messageType = ? and createddate > sysdate-60 " +
				"and createddate between to_date('"+strBDate+" 00:00:00','YYYY-MM-DD HH24:MI:SS') " +
			     "and to_date('"+strNDate+" 23:59:59','YYYY-MM-DD HH24:MI:SS') ";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,messageType);
		ResultSet resultSet =  dbpool.executePreparedQuery();
		FlexSeCmSchema flexSeCmSchema = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		String day = simpleDateFormat.format(new Date());
		
		String company =  AppConfig.get("sysconst.CUSTOMER_SPECIALFEE_COMPANY");
		
		if(company==null || "".equals(company))
		{
			logger.info("ȡ�������ѻ����������Ͷ���!");
			return null;
		}
		while(resultSet.next())
		{
			flexSeCmSchema = new FlexSeCmSchema();
			flexSeCmSchema.setCompany(company);
			flexSeCmSchema.setId(dbpool.getString(resultSet,"SendSeqSpecific")); 
			flexSeCmSchema.setPhoneCode(dbpool.getString(resultSet,"mobile"));
			flexSeCmSchema.setSendText(message);
			flexSeCmSchema.setSendTime(day);
			flexSeCmSchema.setServiceId("1001");
			if("1".equals(dbpool.getString(resultSet,"MessageType")))
			{
				flexSeCmSchema.setSmsType("C51");
			}
			flexSeCmSchema.setUserId("cx");
			list.add(flexSeCmSchema);
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return list;
	}
	
	/**
	 * Flex_Se_Cm ����
	 * @param dbpool
	 * @param flexSeCmSchema
	 * @throws Exception
	 */
	public int  getCount(DbPool dbpool,FlexSeCmSchema flexSeCmSchema) 
	{
		String sql = "select count(*) from  Flex_Se_Cm where id =? ";
		int count = 0;
		try{
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,flexSeCmSchema.getId());
			ResultSet result =  dbpool.executePreparedQuery();
			while(result.next())
			{
				count = Integer.parseInt(dbpool.getString(result,"count(*)"));
			}
			result.close();
			dbpool.closePreparedStatement();
		}catch(Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			
			String message = "DBSendMessage->getCount()->Flex_Se_Cm ����->"+e.getMessage();
			TaskMngUtil.insertMidDataLog("DBSendMessage.getCount", message, e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			
		}
		return count;
	}
	/**
	 * ����PrpCustomerMessage
	 * @param dbpool
	 * @param flexSeCmSchema
	 * @throws Exception
	 */
	public void update(FlexSeCmSchema flexSeCmSchema) throws Exception  
	{
		
		String sql = "update PrpCustomerMessage set messageflag='0',UpdateDate= sysdate where sendseq=? and messageflag ='-1' " ;
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,flexSeCmSchema.getId());
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
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
	}
	
	/**
	 * ����PrpCustomerMessage
	 * ���Ͽ��������ظ���id������
	 * @param dbpool
	 * @param flexSeCmSchema
	 * @throws Exception
	 */
	public void updateSpecific(FlexSeCmSchema flexSeCmSchema) throws Exception  
	{
		
		String sql = "update PrpCustomerMessage set messageflag1='0' where sendseqSpecific=? and messageflag1 ='-1' " ;
		DbPool dbpool = new DbPool();
		try{
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			dbpool.prepareInnerStatement(sql);
			dbpool.setString(1,flexSeCmSchema.getId());
			dbpool.executePreparedUpdate();
			dbpool.closePreparedStatement();
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
	}
	
	/**
	 * Flex_Se_Cm �������
	 * @param dbpool
	 * @param flexSeCmSchema
	 * @throws Exception
	 */
	public void insertFlexSeCm(DbPool dbpool,FlexSeCmSchema flexSeCmSchema) 
	{
		String sql = "insert into Flex_Se_Cm(id,PhoneCode,SendText," +
				     "ServiceId,SmsType,UserId,SendTime,Company) values(?,?,?,?,?,?,?,?) ";
		try{
		dbpool.prepareInnerStatement(sql);
		int j = 0;
		dbpool.setString(++j,flexSeCmSchema.getId());
		dbpool.setString(++j,flexSeCmSchema.getPhoneCode());
		dbpool.setString(++j,flexSeCmSchema.getSendText());
		dbpool.setString(++j,flexSeCmSchema.getServiceId());
		dbpool.setString(++j,flexSeCmSchema.getSmsType());
		dbpool.setString(++j,flexSeCmSchema.getUserId());
		dbpool.setString(++j,flexSeCmSchema.getSendTime());
		dbpool.setString(++j,flexSeCmSchema.getCompany());
		dbpool.executePreparedUpdate();
		dbpool.closePreparedStatement();
		flexSeCmSchema.setStatus("0");
		}catch(Exception e)
		{
			flexSeCmSchema.setStatus("-1");
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			logger.error(e+"����ű�Flex_Se_Cm�������ݳ���id�ţ�"+flexSeCmSchema.getId());
			
			String message = "DBSendMessage->insertFlexSeCm()->����ű�Flex_Se_Cm�������ݳ���id�ţ�->"+flexSeCmSchema.getId()+e.getMessage();
			TaskMngUtil.insertMidDataLog(flexSeCmSchema.getId(), message,e,
					TaskMngUtil.CUSTOMERREALRUN_JOBNAME);
			
		}
	}
	/**
	 * ���ݶ���id��ȡflex_se_cm����״̬
	 * @param dbpool
	 * @param prpCustomerMessageSchema
	 * @return
	 * @throws Exception
	 */
	public PrpCustomerMessageSchema getMessageStatus(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema) throws Exception
	{
		String sql = "select * from duanxin.flex_send_out where id=?  ";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,prpCustomerMessageSchema.getSendSeq());
		ResultSet resultSet = dbpool.executePreparedQuery();
		int resultCount = 0;
		while(resultSet.next())
		{
			String temp = dbpool.getString(resultSet,"status");
			logger.info("flex_send_out:"+prpCustomerMessageSchema.getSendSeq()+" ; ״̬��"+temp);
			if(temp!=null && !"".equals(temp))
			{
				if("0".equals(temp))
				{
					prpCustomerMessageSchema.setMessageFlag("99");
				}
				else
				{
					prpCustomerMessageSchema.setMessageFlag(temp);
				}
			}
			resultCount = 1;
		}
		if(resultCount ==0)
		{
			logger.info("flex_send_out "+prpCustomerMessageSchema.getSendSeq()+" û�м�¼");
			String temp1 = getMessageStatusCM( dbpool, prpCustomerMessageSchema);
			prpCustomerMessageSchema.setMessageFlag(temp1);
			logger.info("flex_send_out "+prpCustomerMessageSchema.getSendSeq()+" û�м�¼" +temp1);
		}





		resultSet.close();
		dbpool.closePreparedStatement();
		return prpCustomerMessageSchema;	
	}
	/**
	 * ���ݶ���id��ȡflex_se_cm����״̬
	 * @param dbpool
	 * @param prpCustomerMessageSchema
	 * @return
	 * @throws Exception
	 */
	public String getMessageStatusCM(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema) throws Exception
	{
		String sql = "select * from  flex_se_cm where id=?  ";
		String status = "";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,prpCustomerMessageSchema.getSendSeq());
		ResultSet resultSet = dbpool.executePreparedQuery();
		long days = 0L;
		while(resultSet.next())
		{
			status = dbpool.getString(resultSet,"status");
			logger.info("flex_se_cm:"+prpCustomerMessageSchema.getSendSeq()+" ; ״̬��"+status);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			Date d1 = df.parse(df.format(new Date()));
			Date d0 = new Date(prpCustomerMessageSchema.getCreatedDate());
			Date d2 = df.parse(df.format(d0));
			long diff = d1.getTime() - d2.getTime();
			days = diff / (1000 * 60 * 60 * 24);
			logger.info("�������ڣ�"+d1+",���ݽ���ʱ�䣺"+d2+",���������"+days);
		}
		catch (Exception e)
		{
			logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
		} 
		if("0".equals(status) && days > 3)
		{
			status = "88";
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		return status;	
	}
	/**
	 * ȡ����״̬��ʼֵ
	 * @param dbpool
	 * @return
	 * @throws Exception
	 */
	public List getFlexSeCmIniSendFlag(DbPool dbpool ) throws  Exception
	{
		String sql = "select * from flex_se_cm where to_date(sendtime,'yyyy-mm-dd hh24:mi:ss') >sysdate-60 and status='0' ";
		ResultSet resultSet = dbpool.query(sql);
		FlexSeCmSchema flexSeCmSchema = null;
		List list = new ArrayList();
		while(resultSet.next())
		{
			flexSeCmSchema = new FlexSeCmSchema();
			flexSeCmSchema.setId(dbpool.getString(resultSet,"id"));
			flexSeCmSchema.setStatus(dbpool.getString(resultSet,"status"));
			list.add(flexSeCmSchema);
		}
		return list;
	}
	/**
	 * ���ݶ���id��ȡflex_se_cm_re����״̬
	 * @param dbpool
	 * @param prpCustomerMessageSchema
	 * @return
	 * @throws Exception
	 * add for �¶���ƽ̨����״̬��flex_se_cm_re����ȡ��
	 */
	public PrpCustomerMessageSchema getMessageStatusCMRE(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema) throws Exception
	{
		String sql = "select * from  flex_se_cm_re where id=?  ";
		String status = "0";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,prpCustomerMessageSchema.getSendSeq());
		ResultSet resultSet = dbpool.executePreparedQuery();
		long days = 0L;
		while(resultSet.next())
		{
			status = dbpool.getString(resultSet,"status");
			logger.info("flex_se_cm_re:"+prpCustomerMessageSchema.getSendSeq()+" ; ״̬��"+status);
		}
		if("0".equals(status)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try
			{
				Date d1 = df.parse(df.format(new Date()));


				Date d2 = df.parse(prpCustomerMessageSchema.getCreatedDate());
				long diff = d1.getTime() - d2.getTime();
				days = diff / (1000 * 60 * 60 * 24);
				logger.info(prpCustomerMessageSchema.getSendSeq()+"�������ڣ�"+d1+",���ݽ���ʱ�䣺"+d2+",���������"+days);
			}
			catch (Exception e)
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			}
		}
		if("0".equals(status) && days > 3)
		{
			status = "88";
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		prpCustomerMessageSchema.setMessageFlag(status);
		return prpCustomerMessageSchema;	
	}
	
	/**
	 * ���Ͽ�����ݶ���id��ȡflex_se_cm_re����״̬
	 * add by linqian 20131216
	 * @param dbpool
	 * @param prpCustomerMessageSchema
	 * @return
	 * @throws Exception
	 * add for �¶���ƽ̨����״̬��flex_se_cm_re����ȡ��
	 */
	public PrpCustomerMessageSchema getMessageStatusHNSpecific(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema) throws Exception
	{
		String sql = "select * from  flex_se_cm_re where id=?  ";
		String status = "0";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,prpCustomerMessageSchema.getSendSeqSpecific());
		ResultSet resultSet = dbpool.executePreparedQuery();
		long days = 0L;
		while(resultSet.next())
		{
			status = dbpool.getString(resultSet,"status");
			logger.info("flex_se_cm_re:"+prpCustomerMessageSchema.getSendSeqSpecific()+" ; ״̬��"+status);
		}
		if("0".equals(status)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try
			{
				Date d1 = df.parse(df.format(new Date()));
				Date d2 = df.parse(prpCustomerMessageSchema.getCreatedDate());
				long diff = d1.getTime() - d2.getTime();
				days = diff / (1000 * 60 * 60 * 24);
				logger.info(prpCustomerMessageSchema.getSendSeqSpecific()+"�������ڣ�"+d1+",���ݽ���ʱ�䣺"+d2+",���������"+days);
			}
			catch (Exception e)
			{
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			}
		}
		if("0".equals(status) && days > 3)
		{
			status = "88";
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		prpCustomerMessageSchema.setMessageFlag1(status);
		return prpCustomerMessageSchema;	
	}
		
	/**
	 * �����ֻ������ȡFLEX_INCEPT_CM �ظ�����
	 * @param dbpool
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public PrpCustomerMessageSchema getClaimContext(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema) throws Exception
	{
		String sql = "SELECT t.* FROM  FLEX_INCEPT_CM t " +
				"WHERE TO_DATE(T.Incepttime, 'YYYY-MM-DD HH24:MI:SS') > to_date(to_char(SYSDATE-7,'YYYY-MM-DD'),'YYYY-MM-DD HH24:MI:SS') " +
				"AND t.phonecode = ? " +
				"ORDER BY T.Incepttime Desc  ";
		String status = "-1";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,prpCustomerMessageSchema.getMobile());
		ResultSet resultSet = dbpool.executePreparedQuery();


		long days = 0L;
		while(resultSet.next())
		{
			status = dbpool.getString(resultSet,"incepttext");
			prpCustomerMessageSchema.setInCepttext(dbpool.getString(resultSet,"incepttext"));
			prpCustomerMessageSchema.setInCepttime(dbpool.getString(resultSet,"incepttime"));

			
			if(status.indexOf("2151")>-1){
				status="1";
			}else if(status.indexOf("2152")>-1){
				status="2";
			}else{
				status="1";
			}
			logger.info("flex_incept_cm:"+prpCustomerMessageSchema.getId()+" mobile:"+prpCustomerMessageSchema.getMobile()+" ����״̬��"+status);
			break;
		}
		if("-1".equals(status)){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try
			{
				Date d1 = df.parse(df.format(new Date()));

				Date d2 = df.parse(prpCustomerMessageSchema.getCreatedDate());
				long diff = d1.getTime() - d2.getTime();
				days = diff / (1000 * 60 * 60 * 24);
				logger.info(prpCustomerMessageSchema.getId()+" �绰"+prpCustomerMessageSchema.getMobile()+"�������ڣ�"+d1+",���ݽ���ʱ�䣺"+d2+",���������"+days);
			}
			catch (Exception e)
			{
				logger.info(prpCustomerMessageSchema.getCreatedDate());
				logger.error("XXXXX��ʵ�Դ�����Ϣ��",e);
			}
		}
		
		if("-1".equals(status) && days > 7)
		{
			status = "0";
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		prpCustomerMessageSchema.setIsContent(status);
		return prpCustomerMessageSchema;	
	}
	
	/**
	 * �����ֻ������ȡFLEX_INCEPT_CM �ظ�����
	 * @param dbpool
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public PrpCustomerMessageSchema getDxmydContext(DbPool dbpool,PrpCustomerMessageSchema prpCustomerMessageSchema) throws Exception
	{
		String sql = "SELECT t.* FROM  FLEX_INCEPT_CM t " +
				"WHERE TO_DATE(T.Incepttime, 'YYYY-MM-DD HH24:MI:SS') > to_date(to_char(SYSDATE-5,'YYYY-MM-DD'),'YYYY-MM-DD HH24:MI:SS') " +
				"AND t.phonecode = ? " +
				"ORDER BY T.Incepttime Desc  ";
		String status = "-1";
		dbpool.prepareInnerStatement(sql);
		dbpool.setString(1,prpCustomerMessageSchema.getMobile());
		ResultSet resultSet = dbpool.executePreparedQuery();
		while(resultSet.next())
		{
			status = dbpool.getString(resultSet,"incepttext");
			prpCustomerMessageSchema.setInCepttext(dbpool.getString(resultSet,"incepttext"));
			prpCustomerMessageSchema.setInCepttime(dbpool.getString(resultSet,"incepttime"));
			
			if(status.indexOf("2172")>-1){
				status="1";
			}else if(status.indexOf("2171")>-1){
				status="3";
			}else if(status.indexOf("2173")>-1){
				status="2";
			}else{
				status="0";
			}
			logger.info("flex_incept_cm:"+prpCustomerMessageSchema.getId()+" mobile:"+prpCustomerMessageSchema.getMobile()+" ����״̬��"+status);
			break;
		}
		resultSet.close();
		dbpool.closePreparedStatement();
		prpCustomerMessageSchema.setIsContent(status);
		return prpCustomerMessageSchema;	
	}
}
