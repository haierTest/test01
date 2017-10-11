package com.sp.prpall.newImageInput.common;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import com.sp.platform.dto.domain.PrpDriskConfigDto;
import com.sp.platform.dto.domain.PrpDuserDto;
import com.sp.platform.ui.control.action.UIPowerNewAction;
import com.sp.platform.ui.control.action.UIPrpDriskConfigAction;
import com.sp.prpall.blsvr.tj.BLStatCreate;
import com.sp.prpall.imageInput.blsvr.BLWfImageDeal;
import com.sp.prpall.imageInput.blsvr.BLWfImageTrack;
import com.sp.prpall.imageInput.dbsvr.DBWfImageDeal;
import com.sp.prpall.imageInput.dbsvr.DBWfImageTrack;
import com.sp.prpall.imageInput.schema.WfImageDealSchema;
import com.sp.prpall.imageInput.schema.WfImageTrackSchema;

import com.sp.sysframework.reference.DBManager;
import com.sp.undwrt.dto.domain.PrpMaterialDto;
import com.sp.undwrt.resource.dtofactory.domain.DBPrpMaterial;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;

public class NewImageInputDeal {

	public String ImagePolicySubmit(BLWfImageDeal blWfImageDeal,String UserCode,String UserName) throws Exception {
		
		String reultMessage = "提交成功！";
		WfImageDealSchema wfImageDealSchmaOld = new WfImageDealSchema();
		BLWfImageTrack blWfImageTrack = new BLWfImageTrack();
		DBWfImageDeal dbWfImageDeal = new DBWfImageDeal();
		SimpleDateFormat   sdf   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DbPool dbPool = new DbPool();
		try{
		  dbPool.open(SysConst.getProperty("DDCCDATASOURCE"));
		  for(int i=0;i<blWfImageDeal.getSize();i++){
			 wfImageDealSchmaOld=blWfImageDeal.getArr(i);
			 
			 if("1".equals(wfImageDealSchmaOld.getBusinessStatus())||"5".equals(wfImageDealSchmaOld.getBusinessStatus())
					 ||"6".equals(wfImageDealSchmaOld.getBusinessStatus())||"7".equals(wfImageDealSchmaOld.getBusinessStatus())){
			 
			   dbPool.beginTransaction();
			   blWfImageTrack.setArr(generateWfImageTrack(wfImageDealSchmaOld));
			   blWfImageTrack.save(dbPool);
			   dbWfImageDeal.setSchema(wfImageDealSchmaOld); 
			   dbWfImageDeal.setBusinessStatus("2");
			   dbWfImageDeal.setFlowStatus("0");
			   
			   
			   /*dbWfImageDeal.setOperatorCode("");
			   dbWfImageDeal.setOperatorName("");*/
			   dbWfImageDeal.setOperatorCode(UserName);
			   dbWfImageDeal.setOperatorName(UserCode);
			   
			   
			   dbWfImageDeal.setSubmitTime(sdf.format(new Date()));
			   dbWfImageDeal.setFlowInTime(sdf.format(new Date()));
			   dbWfImageDeal.setHandleTime(sdf.format(new Date()));
			   dbWfImageDeal.update(dbPool); 
			   dbPool.commitTransaction();
			 }else{
				 reultMessage=wfImageDealSchmaOld.getBusinessNo()+"此状态下不能提交";
				 break;
			 }
		  }
		  
		}catch(Exception e){
		  e.printStackTrace();
		  dbPool.rollbackTransaction();
		  reultMessage="提交失败！";
		}finally{
		  dbPool.close();
		}
		return reultMessage;
	}
	
	public WfImageTrackSchema generateWfImageTrack(WfImageDealSchema wfImageDealSchma) throws Exception {
		
		WfImageTrackSchema wfImageTrackSchema=  new WfImageTrackSchema();
		BLWfImageTrack blWfImageTrack = new  BLWfImageTrack();
		blWfImageTrack.query("FlowId='"+wfImageDealSchma.getFlowID()+"'");
		wfImageTrackSchema.setFlowID(wfImageDealSchma.getFlowID());
		wfImageTrackSchema.setLogNo(String.valueOf(blWfImageTrack.getSize()+1));
		wfImageTrackSchema.setAppliName(wfImageDealSchma.getAppliName());
		wfImageTrackSchema.setClassCode(wfImageDealSchma.getClassCode()); 
		wfImageTrackSchema.setRiskCode(wfImageDealSchma.getRiskCode());
		wfImageTrackSchema.setComCode(wfImageDealSchma.getComCode());
		wfImageTrackSchema.setScanUserCode(wfImageDealSchma.getScanUserCode());
		wfImageTrackSchema.setScanUserName(wfImageDealSchma.getScanUserName());
		wfImageTrackSchema.setOperatorCode(wfImageDealSchma.getOperatorCode());
		wfImageTrackSchema.setOperatorName(wfImageDealSchma.getOperatorName());
		wfImageTrackSchema.setFlowInTime(wfImageDealSchma.getFlowInTime());
		wfImageTrackSchema.setHandleTime(wfImageDealSchma.getHandleTime());
		wfImageTrackSchema.setBusinessStatus(wfImageDealSchma.getBusinessStatus());
		wfImageTrackSchema.setFlowStatus(wfImageDealSchma.getFlowStatus());
		wfImageTrackSchema.setBusinessType(wfImageDealSchma.getBusinessType());
		wfImageTrackSchema.setBusinessNo(wfImageDealSchma.getBusinessNo());
		wfImageTrackSchema.setImageNo(wfImageDealSchma.getImageNo());
		wfImageTrackSchema.setFirstInTime(wfImageDealSchma.getFirstInTime());
		wfImageTrackSchema.setRemark(wfImageDealSchma.getRemark());
		
		wfImageTrackSchema.setPolicyNo(wfImageDealSchma.getPolicyNo());
		wfImageTrackSchema.setEditType(wfImageDealSchma.getEditType());
		
		
		wfImageTrackSchema.setFlag(wfImageDealSchma.getFlag());
		
		
		
		wfImageTrackSchema.setBarCode(wfImageDealSchma.getBarCode());
		wfImageTrackSchema.setDocType(wfImageDealSchma.getDocType());
		
		return wfImageTrackSchema;
	}
   
   public String ImagePolicyBack(String businessNo,String userCode, String userName, String Remark) throws Exception {
		
		String reultMessage = "打回成功！";
		String querySql = "ImageNo='"+businessNo+"'";
		
		WfImageDealSchema wfImageDealSchmaOld = new WfImageDealSchema();
		BLWfImageTrack blWfImageTrack = new BLWfImageTrack();
		DBWfImageDeal dbWfImageDeal = new DBWfImageDeal();
		SimpleDateFormat   sdf   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DbPool dbPool = new DbPool();
		try{
		  dbPool.open(SysConst.getProperty("DDCCDATASOURCE"));
		  BLWfImageDeal blWfImageDeal = new BLWfImageDeal();
		  blWfImageDeal.query(querySql);
		  for(int i=0;i<blWfImageDeal.getSize();i++){
			 dbPool.beginTransaction();
			 wfImageDealSchmaOld=blWfImageDeal.getArr(i); 
			 blWfImageTrack.setArr(generateWfImageTrack(wfImageDealSchmaOld));
			 blWfImageTrack.save(dbPool);
			 dbWfImageDeal.setSchema(wfImageDealSchmaOld); 
			 dbWfImageDeal.setBusinessStatus("5");
			 dbWfImageDeal.setFlowStatus("1");
			 
			 
			 
			 dbWfImageDeal.setRemark(Remark);
			 dbWfImageDeal.setOperatorCode(userCode);
			 dbWfImageDeal.setOperatorName(userName);
			 dbWfImageDeal.setFlowInTime(sdf.format(new Date()));
			 dbWfImageDeal.setHandleTime(sdf.format(new Date()));
			 dbWfImageDeal.update(dbPool); 
			 dbPool.commitTransaction();
		  }
		  
		}catch(Exception e){
		  e.printStackTrace();
		  dbPool.rollbackTransaction();
		  reultMessage="打回失败！";
		}finally{
		  dbPool.close();
		}
		return reultMessage;
	}
   
   public void ImagePolicyUpdate(String businessNo, String userCode, String userName) throws Exception{
	   String querySql = "ImageNo='"+businessNo+"'";
	   DBWfImageDeal dbWfImageDeal = new DBWfImageDeal();
	   BLWfImageDeal blWfImageDeal = new BLWfImageDeal();
	   BLWfImageTrack blWfImageTrack = new BLWfImageTrack();
	   WfImageDealSchema wfImageDealSchma = new WfImageDealSchema();
	   blWfImageDeal.query(querySql);
	   SimpleDateFormat   sdf   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   DbPool dbPool = new DbPool();
	   try{
		   dbPool.open(SysConst.getProperty("DDCCDATASOURCE"));
		   if(blWfImageDeal.getSize()>0){
			   dbPool.beginTransaction();
			   wfImageDealSchma=blWfImageDeal.getArr(0);
			   blWfImageTrack.setArr(generateWfImageTrack(wfImageDealSchma));
			   if(!"3".equals(wfImageDealSchma.getBusinessStatus())){
			     blWfImageTrack.save(dbPool);
		       }
			   wfImageDealSchma.setBusinessStatus("3");
			   wfImageDealSchma.setOperatorCode(userCode);
			   wfImageDealSchma.setOperatorName(userName);
			   wfImageDealSchma.setFlowInTime(sdf.format(new Date()));
			   wfImageDealSchma.setHandleTime(sdf.format(new Date()));
			   dbWfImageDeal.setSchema(wfImageDealSchma);
			   dbWfImageDeal.update(dbPool);
			   dbPool.commitTransaction();
		   }
	   }catch(Exception e){
		   e.printStackTrace();
		   dbPool.rollbackTransaction();
	   }finally{
		   dbPool.close();
	   }
	   
   }
   
   public String ImagePolicyWithdrawn(String[] businessNo,String userCode,String userName) throws Exception{
	   String reultMessage = "撤回成功！";
	   String[] arrBusinessNo = businessNo;
		
		WfImageDealSchema wfImageDealSchmaOld = new WfImageDealSchema();
		BLWfImageTrack blWfImageTrack = null;
		DBWfImageDeal dbWfImageDeal = new DBWfImageDeal();
		SimpleDateFormat   sdf   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DbPool dbPool = new DbPool();
		try{
		  dbPool.open(SysConst.getProperty("DDCCDATASOURCE"));
	      for(int j=0;j<arrBusinessNo.length;j++){
		  String querySql = "ImageNo='"+arrBusinessNo[j]+"'";
		  BLWfImageDeal blWfImageDeal = new BLWfImageDeal();
		  blWfImageDeal.query(querySql);
		  if(blWfImageDeal.getSize()>0){
			 wfImageDealSchmaOld=blWfImageDeal.getArr(0);
			 
			 if("2".equals(wfImageDealSchmaOld.getBusinessStatus()) && (wfImageDealSchmaOld.getBusinessNo() ==null 
					 || "".equals(wfImageDealSchmaOld.getBusinessNo())
					 || "null".equals(wfImageDealSchmaOld.getBusinessNo()))){
				 blWfImageTrack= new BLWfImageTrack();
				 dbPool.beginTransaction(); 
				 blWfImageTrack.setArr(generateWfImageTrack(wfImageDealSchmaOld));
				 blWfImageTrack.save(dbPool);
				 dbWfImageDeal.setSchema(wfImageDealSchmaOld); 
				 dbWfImageDeal.setBusinessStatus("6");
				 dbWfImageDeal.setFlowStatus("1");
				 dbWfImageDeal.setFlowInTime(sdf.format(new Date()));
				 dbWfImageDeal.setHandleTime(sdf.format(new Date()));
				 dbWfImageDeal.setOperatorCode(userCode);
				 dbWfImageDeal.setOperatorName(userName);
				 dbWfImageDeal.update(dbPool);
				 dbPool.commitTransaction(); 
			 }
			 else{
				 reultMessage=wfImageDealSchmaOld.getBusinessNo()+"此状态下不能撤回";
				 break;
			 }
		   }else{
			   reultMessage="数据已经处理完毕，不能撤回";
		   }
	      } 
		}catch(Exception e){
		  e.printStackTrace();
		  dbPool.rollbackTransaction();
		  reultMessage="撤回失败！";
		}finally{
		  dbPool.close();
		}
		return reultMessage;
	   
   }
    
   
   public void ImagePolicyFinish(String strBusniessNo, String strBizNo, String userCode, String userName,String strAppliName) throws Exception {
	   String querySql = "ImageNo='"+strBusniessNo+"'";
	   String queryMaterial = "BusinessNo='"+strBusniessNo+"'";
	   BLWfImageDeal blWfImageDeal = new BLWfImageDeal();
	   BLWfImageTrack blWfImageTrack = new BLWfImageTrack();
	   DBWfImageTrack dbWfImageTrack = new DBWfImageTrack();
	   DBManager dbManager = new DBManager();
	   WfImageDealSchema wfImageDealSchma = new WfImageDealSchema();
	   WfImageTrackSchema wfImageTrackSchma = new WfImageTrackSchema();
	   DBWfImageDeal dbWfImageDeal = new DBWfImageDeal();
	   PrpMaterialDto prpMaterialDto=null;
	   blWfImageDeal.query(querySql);
	   SimpleDateFormat   sdf   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   DbPool dbPool = new DbPool();
	   try{
		   dbPool.open(SysConst.getProperty("DDCCDATASOURCE"));
		   dbManager =dbPool.getDBManager(SysConst.getProperty("DDCCDATASOURCE"));
		   DBPrpMaterial dbPrpMaterial = new DBPrpMaterial(dbManager);
		   if(blWfImageDeal.getSize()>0){
			   dbPool.beginTransaction();
			   wfImageDealSchma=blWfImageDeal.getArr(0);
			   if(!"M".equals(wfImageDealSchma.getEditType())){
				   wfImageDealSchma.setBusinessNo(strBizNo);
				   wfImageDealSchma.setAppliName(strAppliName);
	           }
			   blWfImageTrack.setArr(generateWfImageTrack(wfImageDealSchma));
			   blWfImageTrack.save(dbPool);
			   dbWfImageDeal.setSchema(wfImageDealSchma); 
			   dbWfImageDeal.setBusinessStatus("4");
	           dbWfImageDeal.setFlowStatus("0");
			   dbWfImageDeal.setOperatorCode(userCode);
			   dbWfImageDeal.setOperatorName(userName);
			   dbWfImageDeal.setFlowInTime(sdf.format(new Date()));
			   dbWfImageDeal.setHandleTime(sdf.format(new Date()));
			   dbWfImageDeal.update(dbPool); 
			   
			   if(!"M".equals(wfImageDealSchma.getEditType())){
				   blWfImageTrack.query(querySql);
				   for(int i=0;i<blWfImageTrack.getSize();i++){
					   wfImageTrackSchma=blWfImageTrack.getArr(i);
					   wfImageTrackSchma.setBusinessNo(strBizNo);
					   wfImageTrackSchma.setAppliName(strAppliName);
					   dbWfImageTrack.setSchema(wfImageTrackSchma);
					   dbWfImageTrack.update(dbPool);
				   }
				   Collection collection=dbPrpMaterial.findByConditions(queryMaterial);
				   Iterator it=collection.iterator();
				   while(it.hasNext()){
					   prpMaterialDto = (PrpMaterialDto)it.next();
					   dbPrpMaterial.delete(strBusniessNo, prpMaterialDto.getSerialNO());
					   prpMaterialDto.setBusinessNo(strBizNo);
					   dbPrpMaterial.insert(prpMaterialDto);
				   }  
			   }
			   dbPool.commitTransaction();
		   }
	   }catch(Exception e){
		   e.printStackTrace();
		   dbPool.rollbackTransaction();
	   }finally{
		   dbPool.close();
	   }
   }
   
  
   public String ImageQueryCondition(String strBusinessNo,String riskcode,String strAppliName,String strBarCode,String strInputStartDate,String strInputEndDate,
		   String strQueryConfine,String strUserType,String[] nodeStatus,String PowerUserCode,String strComCode,String requestComCode,String strProposalNo,
		   String strPolicyNo,String strBusinessType,PrpDuserDto user) throws Exception{
	   
	   String strReturn = "";
	   StringBuffer strWhere_Buffer = new StringBuffer(500); 
	   strWhere_Buffer.append(" 1=1 ");
	   
	   strWhere_Buffer.append(" AND BarCode IS NOT NULL");
	   
	   if(strBusinessNo!=null&&!"".equals(strBusinessNo)){
		   strBusinessNo = strBusinessNo.trim();
		   strWhere_Buffer.append(" AND ImageNo LIKE '%");
		   strWhere_Buffer.append(strBusinessNo);
		   strWhere_Buffer.append("%'"); 
	   }
	   
	   if(strProposalNo!=null&&!"".equals(strProposalNo)){
			strProposalNo = strProposalNo.trim();
			
			strWhere_Buffer.append(" AND BusinessNo = '");
			strWhere_Buffer.append(strProposalNo);
			strWhere_Buffer.append("'"); 
			
	   }
	  
	    
	    if(strBusinessType!=null&&!"".equals(strBusinessType)){
	    	strBusinessType = strBusinessType.trim();
	      strWhere_Buffer.append(" AND BusinessType='");
	      strWhere_Buffer.append(strBusinessType);
	      strWhere_Buffer.append("'"); 
	    }
	    if(strPolicyNo!=null&&!"".equals(strPolicyNo)){
	      strPolicyNo = strPolicyNo.trim();
	      strWhere_Buffer.append(" AND PolicyNo='");
	      strWhere_Buffer.append(strPolicyNo);
	      strWhere_Buffer.append("'");
	    }
	    
	   if(riskcode!=null&&!"".equals(riskcode)){
		   riskcode = riskcode.trim();
		   strWhere_Buffer.append(" AND RiskCode='");
		   strWhere_Buffer.append(riskcode);
		   strWhere_Buffer.append("'"); 
	   }
	    
	   if(strAppliName!=null&&!"".equals(strAppliName)){
		   strAppliName= strAppliName.trim();
		   strWhere_Buffer.append(" AND AppliName LIKE '%");
		   strWhere_Buffer.append(strAppliName);
		   strWhere_Buffer.append("%'"); 
	   }
	   if(strBarCode!=null&&!"".equals(strBarCode)){
		   strBarCode= strBarCode.trim();
		   strWhere_Buffer.append(" AND BarCode LIKE '%");
		   strWhere_Buffer.append(strBarCode);
		   strWhere_Buffer.append("%'"); 
	   }
	 
	   if(strInputStartDate!=null&&!"".equals(strInputStartDate)&&strInputEndDate!=null
			   &&!"".equals(strInputEndDate)){
		   strWhere_Buffer.append(" AND FirstInTime BETWEEN to_date('");
		   strWhere_Buffer.append(strInputStartDate+" 00:00:00");
		   strWhere_Buffer.append("','yyyy-mm-dd hh24:mi:ss')");
		   strWhere_Buffer.append(" AND to_date('");
		   strWhere_Buffer.append(strInputEndDate+" 23:59:59");
		   strWhere_Buffer.append("','yyyy-mm-dd hh24:mi:ss')");
	   }
	   
	   if("0".equals(strQueryConfine)) 
	   {











		   strWhere_Buffer.append(" AND OperatorCode = '");
		   strWhere_Buffer.append(user.getUserCode());
		   strWhere_Buffer.append("'");
	   }else if("2".equals(strQueryConfine)){
		   String strReqComCode = new BLStatCreate().tailEvenTrim(requestComCode);
		   

		   if("1".equals(strUserType)){
		   
			   
			   strWhere_Buffer.append(" AND ComCode Like '");
			   strWhere_Buffer.append(strReqComCode);
			   strWhere_Buffer.append("%' ");
			   
			   if(nodeStatus.length==1&&"0".equals(nodeStatus[0])){
				   strWhere_Buffer.append(UIPowerNewAction.addNewPower(user, "WfImageTrack", "ComCode", strComCode)); 
			   }else{
				   strWhere_Buffer.append(UIPowerNewAction.addNewPower(user, "WfImageDeal", "ComCode", strComCode)); 
			   }
		   

		   }else{
		   
			   strWhere_Buffer.append(" AND ComCode = '");
			   strWhere_Buffer.append(requestComCode);
			   strWhere_Buffer.append("' ");
		   }
		   if(nodeStatus.length>0){
		     strWhere_Buffer.append(" AND BusinessStatus in ('");
		     if(nodeStatus.length>1){
			     for (int m=0;m<nodeStatus.length;m++){
				     if(m!=nodeStatus.length-1){
					     strWhere_Buffer.append(nodeStatus[m]);
					     strWhere_Buffer.append("','");
				     }else{
					     strWhere_Buffer.append(nodeStatus[m]);
				     }
			     } 
		     }else if(nodeStatus.length==1){
			     strWhere_Buffer.append(nodeStatus[0]);
		     }
		   }
		   strWhere_Buffer.append("') ");
	   }
	   
	   
	   strReturn = strWhere_Buffer.toString();
	   return strReturn;
   }
   
   public String ImageSaveCheck(String kindCode) throws Exception {
	   String strReturn=""; 
	   String kindCodeScope = SysConfig.getProperty("KINDCODESCOPE");
	   if(kindCodeScope.indexOf(kindCode)>-1){
		   strReturn="1";
	   }else{
		   strReturn="0";
	   }
	   return strReturn;
   }
   
   /**
    *@description 集中录单2700条款开关配置校验方法
    *@param  strComcode  归属机构
    *@param  strKindCode 条款代码
    *@exception Exception
    *@return 1:集中录单条款
    *@author gaohaifeng 20110311
    */
   public String imageSaveCheck(String strComCode,String strKindCode) throws Exception {
       
       String strReturn = ""; 
       PrpDriskConfigDto prpDriskConfigDtoKind = UIPrpDriskConfigAction.queryRiskConfig(strComCode,strKindCode,"YXCD_CASUALTY_KIND");
       if(prpDriskConfigDtoKind != null && "1".equals(prpDriskConfigDtoKind.getConfigValue())){
           strReturn = "1";
       }else{
           strReturn = "0";
       }
       return strReturn;
   }
   
   public String ImagePolicyDrop(String imageNo) throws Exception {
	   String strReturn="处理完毕";
	   String querySql = "ImageNo='"+imageNo+"'";
	   DBWfImageDeal dbWfImageDeal = new DBWfImageDeal();
	   BLWfImageDeal blWfImageDeal = new BLWfImageDeal();
	   BLWfImageTrack blWfImageTrack = new BLWfImageTrack();
	   WfImageDealSchema wfImageDealSchma = new WfImageDealSchema();
		SimpleDateFormat   sdf   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   blWfImageDeal.query(querySql);
	   DbPool dbPool = new DbPool();
	   try{
		   dbPool.open(SysConst.getProperty("DDCCDATASOURCE"));
		   if(blWfImageDeal.getSize()>0){
			   dbPool.beginTransaction();
			   wfImageDealSchma=blWfImageDeal.getArr(0);
			   blWfImageTrack.setArr(generateWfImageTrack(wfImageDealSchma));
			   blWfImageTrack.save(dbPool);
			   wfImageDealSchma.setBusinessStatus("2");
			   wfImageDealSchma.setOperatorCode("");
			   wfImageDealSchma.setOperatorName("");
			   wfImageDealSchma.setFlowInTime(sdf.format(new Date()));
			   wfImageDealSchma.setHandleTime(sdf.format(new Date()));
			   dbWfImageDeal.setSchema(wfImageDealSchma);
			   dbWfImageDeal.update(dbPool);
			   dbPool.commitTransaction();
		   }
	   }catch(Exception e){
		   e.printStackTrace();
		   dbPool.rollbackTransaction();
		   strReturn="处理失败";
	   }finally{
		   dbPool.close();
	   }
	   
	   return strReturn;
   }
   
   public void ImagePolicyUnderwriteSubmit(DBManager dbManager, String businessNo,String userCode, String userName) throws Exception {
	   
	   String querySql = "BusinessNo='"+businessNo+"'";
		
		WfImageDealSchema wfImageDealSchmaOld = new WfImageDealSchema();
		BLWfImageTrack blWfImageTrack = new BLWfImageTrack();
		DBWfImageDeal dbWfImageDeal = new DBWfImageDeal();
		SimpleDateFormat   sdf   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DbPool dbPool = new DbPool();
		try{
		  dbPool.setDBManager(dbManager);
		  BLWfImageDeal blWfImageDeal = new BLWfImageDeal();
		  blWfImageDeal.query(querySql);
		  for(int i=0;i<blWfImageDeal.getSize();i++){
			 dbPool.beginTransaction();
			 wfImageDealSchmaOld=blWfImageDeal.getArr(i); 
			 blWfImageTrack.setArr(generateWfImageTrack(wfImageDealSchmaOld));
			 blWfImageTrack.save(dbPool);
			 dbWfImageDeal.setSchema(wfImageDealSchmaOld); 
			 dbWfImageDeal.setBusinessStatus("8");
			 dbWfImageDeal.setFlowStatus("0");
			 
			 
			 
			 dbWfImageDeal.setOperatorCode(userCode);
			 dbWfImageDeal.setOperatorName(userName);
			 dbWfImageDeal.setFlowInTime(sdf.format(new Date()));
			 dbWfImageDeal.setHandleTime(sdf.format(new Date()));
			 
			 dbWfImageDeal.setRemark("");
			 
			 dbWfImageDeal.update(dbPool); 
			 dbPool.commitTransaction();
		  }
		  
		}catch(Exception e){
			
			dbManager.rollbackTransaction();
			e.printStackTrace();
			throw e;
			
		}finally{
		}
   }
   
   public void ImagePolicyUnderwriteBack(DBManager dbManager, String businessNo,String userCode, String userName) throws Exception {
		
		String querySql = "BusinessNo='"+businessNo+"'";
		
		WfImageDealSchema wfImageDealSchmaOld = new WfImageDealSchema();
		BLWfImageTrack blWfImageTrack = new BLWfImageTrack();
		DBWfImageDeal dbWfImageDeal = new DBWfImageDeal();
		SimpleDateFormat   sdf   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DbPool dbPool = new DbPool();
		try{
		  dbPool.setDBManager(dbManager);
		  BLWfImageDeal blWfImageDeal = new BLWfImageDeal();
		  blWfImageDeal.query(querySql);
		  for(int i=0;i<blWfImageDeal.getSize();i++){

			 wfImageDealSchmaOld=blWfImageDeal.getArr(i); 
			 blWfImageTrack.setArr(generateWfImageTrack(wfImageDealSchmaOld));
			 blWfImageTrack.save(dbPool);
			 dbWfImageDeal.setSchema(wfImageDealSchmaOld); 
			 dbWfImageDeal.setBusinessStatus("7");
			 dbWfImageDeal.setFlowStatus("1");
			 
			 
			 
			 dbWfImageDeal.setEditType("M");
			 dbWfImageDeal.setOperatorCode(userCode);
			 dbWfImageDeal.setOperatorName(userName);
			 dbWfImageDeal.setFlowInTime(sdf.format(new Date()));
			 dbWfImageDeal.setHandleTime(sdf.format(new Date()));
			 dbWfImageDeal.update(dbPool); 

		  }
		  
		}catch(Exception e){
			
			dbManager.rollbackTransaction();
			e.printStackTrace();
			throw e;
			
		}finally{
			
		}
	}
   
   public void ImagePolicyUnderwriteBack(DBManager dbManager, String businessNo,String userCode, String userName,String back_falg) throws Exception {
		
		String querySql = "BusinessNo='"+businessNo+"'";
		WfImageDealSchema wfImageDealSchmaOld = new WfImageDealSchema();
		BLWfImageTrack blWfImageTrack = new BLWfImageTrack();
		DBWfImageDeal dbWfImageDeal = new DBWfImageDeal();
		SimpleDateFormat   sdf   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DbPool dbPool = new DbPool();
		try{
		  dbPool.setDBManager(dbManager);
		  BLWfImageDeal blWfImageDeal = new BLWfImageDeal();
		  blWfImageDeal.query(querySql);
		  for(int i=0;i<blWfImageDeal.getSize();i++){

			 wfImageDealSchmaOld=blWfImageDeal.getArr(i); 
			 blWfImageTrack.setArr(generateWfImageTrack(wfImageDealSchmaOld));
			 blWfImageTrack.save(dbPool);
			 dbWfImageDeal.setSchema(wfImageDealSchmaOld); 
			 if(back_falg.equals("0")){
				 dbWfImageDeal.setBusinessStatus("9");
			 }else{
				 dbWfImageDeal.setBusinessStatus("7");
			 }
			 dbWfImageDeal.setFlowStatus("1");
			 
			 
			 
			 dbWfImageDeal.setEditType("M");
			 dbWfImageDeal.setOperatorCode(userCode);
			 dbWfImageDeal.setOperatorName(userName);
			 dbWfImageDeal.setFlowInTime(sdf.format(new Date()));
			 dbWfImageDeal.setHandleTime(sdf.format(new Date()));
			 
			 dbWfImageDeal.setRemark("");
			 
			 dbWfImageDeal.update(dbPool); 

		  }
		  
		}catch(Exception e){
			
			dbManager.rollbackTransaction();
			e.printStackTrace();
			throw e;
			
		}finally{
			
		}
	}
   
   
   public void ImagePolicyUnderwriteFinish(DBManager dbManager, String strBusniessNo, String userCode, String userName) throws Exception {
	   
	   String querySql = "BusinessNo='"+strBusniessNo+"'";
	   
	   WfImageTrackSchema wfImageTrackSchmafinish = null;
	   WfImageTrackSchema wfImageTrackSchma = null;
	   WfImageDealSchema  wfImageDealSchma = null;
	   BLWfImageTrack blWfImageTrack = new BLWfImageTrack();
	   SimpleDateFormat   sdf   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   DbPool dbPool = new DbPool();
	   try{
		  dbPool.setDBManager(dbManager);
		  BLWfImageDeal blWfImageDeal = new BLWfImageDeal();
		  blWfImageDeal.query(querySql);
		  
		  for(int i=0;i<blWfImageDeal.getSize();i++){
			  wfImageDealSchma=blWfImageDeal.getArr(0);
			  wfImageTrackSchma=generateWfImageTrack(wfImageDealSchma);
			  wfImageTrackSchmafinish = generateWfImageTrack(wfImageDealSchma);
			  wfImageTrackSchmafinish.setBusinessStatus("0");
			  wfImageTrackSchmafinish.setLogNo(""+(Integer.parseInt(wfImageTrackSchma.getLogNo())+1));
			  wfImageTrackSchmafinish.setOperatorCode(userCode);
			  wfImageTrackSchmafinish.setOperatorName(userName);
			  blWfImageTrack.setArr(wfImageTrackSchma);
			  blWfImageTrack.setArr(wfImageTrackSchmafinish);
			  blWfImageTrack.save(dbPool);			   
			  blWfImageDeal.cancel(dbPool, wfImageDealSchma.getFlowID());  
		  }
	   }catch(Exception e){
		     
			 dbManager.rollbackTransaction();
			 e.printStackTrace();
			 throw e;
			 
	   }
   }
   
 
   public void imageUpdateBusinessStatus(String strBusniessNo, String userCode, String userName,String status,String remark) throws Exception{
	   String querySql = "BusinessNo='"+strBusniessNo+"'";
		
		WfImageDealSchema wfImageDealSchmaOld = new WfImageDealSchema();
		BLWfImageTrack blWfImageTrack = new BLWfImageTrack();
		DBWfImageDeal dbWfImageDeal = new DBWfImageDeal();
		SimpleDateFormat   sdf   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DbPool dbPool = new DbPool();
		DBManager dbManager = new DBManager();
		try{
			dbPool.open(SysConst.getProperty("DDCCDATASOURCE"));
			dbManager =dbPool.getDBManager(SysConst.getProperty("DDCCDATASOURCE"));
			BLWfImageDeal blWfImageDeal = new BLWfImageDeal();
			blWfImageDeal.query(querySql);
			for(int i=0;i<blWfImageDeal.getSize();i++){
			 dbPool.beginTransaction();
			 wfImageDealSchmaOld=blWfImageDeal.getArr(i); 
			 blWfImageTrack.setArr(generateWfImageTrack(wfImageDealSchmaOld));
			 blWfImageTrack.save(dbPool);
			 dbWfImageDeal.setSchema(wfImageDealSchmaOld); 
			 dbWfImageDeal.setBusinessStatus(status);
			 dbWfImageDeal.setFlowStatus("0");
			 
			 
			 
			 dbWfImageDeal.setOperatorCode(userCode);
			 dbWfImageDeal.setOperatorName(userName);
			 dbWfImageDeal.setFlowInTime(sdf.format(new Date()));
			 dbWfImageDeal.setHandleTime(sdf.format(new Date()));
			 dbWfImageDeal.setRemark(remark);
			 dbWfImageDeal.update(dbPool); 
			 dbPool.commitTransaction();
		  }
		  
		}catch(Exception e){
			
			dbManager.rollbackTransaction();
			e.printStackTrace();
			throw e;
			
		}finally{
			 dbPool.close();
		}
	   
   }
   
   public String imageUpdateBusinessStatusNew(String[] strBusniessNo, String userCode, String userName,String status,String remark) throws Exception{
	   
	   String reultMessage = "返回录单成功！";
	   String[] arrBusinessNo = strBusniessNo;
	   WfImageDealSchema wfImageDealSchmaOld = new WfImageDealSchema();
	   BLWfImageTrack blWfImageTrack = new BLWfImageTrack();
	   DBWfImageDeal dbWfImageDeal = new DBWfImageDeal();
	   SimpleDateFormat   sdf   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	   DbPool dbPool = new DbPool();
	   DBManager dbManager = new DBManager();
	   try{
		   dbPool.open(SysConst.getProperty("DDCCDATASOURCE"));
		   dbManager =dbPool.getDBManager(SysConst.getProperty("DDCCDATASOURCE"));
		   for(int j=0;j<arrBusinessNo.length;j++){
			   String querySql = "ImageNo='"+arrBusinessNo[j]+"'";
			   BLWfImageDeal blWfImageDeal = new BLWfImageDeal();
			   blWfImageDeal.query(querySql);
			   for(int i=0;i<blWfImageDeal.getSize();i++){
			   dbPool.beginTransaction();
			   wfImageDealSchmaOld=blWfImageDeal.getArr(i); 
			   blWfImageTrack.setArr(generateWfImageTrack(wfImageDealSchmaOld));
			   blWfImageTrack.save(dbPool);
			   dbWfImageDeal.setSchema(wfImageDealSchmaOld); 
			   dbWfImageDeal.setBusinessStatus(status);
			   dbWfImageDeal.setFlowStatus("0");
			   
			   
			   
			   dbWfImageDeal.setOperatorCode(userCode);
			   dbWfImageDeal.setOperatorName(userName);
			   dbWfImageDeal.setFlowInTime(sdf.format(new Date()));
			   dbWfImageDeal.setHandleTime(sdf.format(new Date()));
			   dbWfImageDeal.setRemark(remark);
			   dbWfImageDeal.update(dbPool); 
			   dbPool.commitTransaction();
			   }
		   }
	   }catch(Exception e){
		   
		   dbManager.rollbackTransaction();
		   e.printStackTrace();
		   reultMessage="返回录单失败！";
		   throw e;
		   
	   }finally{
		   dbPool.close();
	   }
	return reultMessage;
	   
   }
   
}
