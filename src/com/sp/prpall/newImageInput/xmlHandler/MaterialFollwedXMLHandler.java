package com.sp.prpall.newImageInput.xmlHandler;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sp.prpall.imageInput.blsvr.BLWfImageDeal;
import com.sp.prpall.imageInput.common.ImageInputDeal;
import com.sp.prpall.imageInput.schema.WfImageDealSchema;
import com.sp.prpall.newImageInput.blsvr.BLPrpMaterial;
import com.sp.prpall.newImageInput.schema.ImageBodySchema;
import com.sp.prpall.newImageInput.schema.ImageHeadSchema;
import com.sp.prpall.newImageInput.schema.ImageItemSchema;
import com.sp.prpall.newImageInput.schema.ImagePacketSchema;
import com.sp.prpall.newImageInput.schema.ImageTaskSchema;
import com.sp.prpall.newImageInput.schema.PrpNewMaterialSchema;
import com.sp.sysframework.common.datatype.DateTime;
import com.sp.utiall.blsvr.BLPrpDuser;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;

public class MaterialFollwedXMLHandler {
	public static String imageDeal(String strData) throws Exception {
		String returnString = "";
		Document document;
		try {
			document = DocumentHelper.parseText(strData);
			Element packetElement = document.getRootElement();
			Element headElement = packetElement.element("Head");
			Element bodyElement = packetElement.element("Body");
			String requestType = headElement.elementText("RequestType");
			if ("img01".equalsIgnoreCase(requestType)) {
				returnString = processRequestTypeImage01(bodyElement, requestType);
			} else if ("img02".equalsIgnoreCase(requestType)) {
				returnString = processRequestTypeImage02(bodyElement, requestType);
			}
		} catch (Exception e) {
			returnString = MaterialFollwedXMLHandler.encodeReturnMessage("0", "1", "1000", "服务器异常:" + e.toString());
		}
		return returnString;
	}

	public static String processRequestTypeImage02(Element bodyElement, String requestType) throws Exception {
		String returnString = "";
		ImageInputDeal imageInputDeal = new ImageInputDeal();

		StringBuffer successTask = new StringBuffer();
		StringBuffer failTask = new StringBuffer();
		boolean sucFlag = false;
		boolean failFlag = false;
		BLWfImageDeal blWfImageDeal = new BLWfImageDeal();
		List ts = bodyElement.elements("ImageTask");

		if (ts != null) {
			for (int i = 0; i < ts.size(); i++) {
				Element image = (Element) ts.get(i);
				String strFlowId = image.elementText("FlowId");
				String operatorCode = image.elementText("OperatorCode");

				BLPrpDuser blPrpDuser = new BLPrpDuser();
				String uName = blPrpDuser.translateCode(operatorCode, true);

				String iWhereClause = "flowId='" + strFlowId + "'";
				blWfImageDeal.query(iWhereClause);
				if (blWfImageDeal.getSize() <= 0) {
					if (failFlag) {
						failTask.append(",");
					}
					failTask.append(strFlowId + ":没有此条任务");
					failFlag = true;
				} else {
					try {
						String result = imageInputDeal.ImagePolicySubmit(blWfImageDeal, operatorCode, uName);
						if (result != null) {
							if (result.indexOf("不能") >= 0 || result.indexOf("失败") >= 0) {
								if (failFlag) {
									failTask.append(",");
								}
								failTask.append(strFlowId + ":" + result);
								failFlag = true;
							} else {
								if (sucFlag) {
									successTask.append(",");
								}
								successTask.append(strFlowId);
								sucFlag = true;
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						if (failFlag) {
							failTask.append(",");
						}
						failTask.append(strFlowId + ":服务器异常:" + e.toString());
						failFlag = true;
					}
				}
			}
			returnString = encodeReturnMessage(requestType, "0", failTask.toString(), successTask.toString());
		} else {
			returnString = encodeReturnMessage(requestType, "1", "0000", "生成任务对象失败");
		}
		return returnString;
	}
	
    public static String ASCICodeToString(String str){
        
	  String newStr = "";
	  String[]chars=str.split(" "); 
	        for(int i=1;i<chars.length;i++){  
	        	newStr = newStr+(char)Integer.parseInt(chars[i]); 
	        }  
	        
	        return newStr;
	 } 
    
    
	public static String processRequestTypeImage01(Element bodyElement, String requestType) throws Exception {
		WfImageDealSchema wfImageDealSchema = null;
		BLPrpMaterial blPrpMaterial = new BLPrpMaterial();

		String returnString = "";

		DbPool dbPool = new DbPool();

		
		ImagePacketSchema packet = decodeXML2Scheme(bodyElement, requestType);

		if (packet != null) {
			
			String flowStatus = packet.getBody().getImageTask().getFlowStatus();
			if (flowStatus != null && flowStatus.length() != 0) {
				flowStatus = flowStatus.trim();
			}

			
			String operateCode = packet.getBody().getImageTask().getOperatorCode();
			BLPrpDuser blPrpDuser = new BLPrpDuser();

			String strUserName = blPrpDuser.translateCode(operateCode, true);
			
			String strBatchFlag = packet.getBody().getImageTask().getBatchFlag(); 
			
			
			String strExplain = packet.getBody().getImageTask().getExplain();
			strExplain = ASCICodeToString(" "+strExplain);						
			
			
			String strRemarks  = packet.getBody().getImageTask().getRemarks();
			strRemarks = ASCICodeToString(" "+strRemarks);	
			

			try {
				dbPool.open(SysConst.getProperty("DDCCDATASOURCE"));
				dbPool.beginTransaction();

				if ("0".equals(flowStatus)) {
					
					wfImageDealSchema = generateWFImageDealSchema(packet, strUserName,strBatchFlag,strExplain,strRemarks);
					
					List materials = generatePrpMaterials(packet, strUserName);
					
					String businessNo = generatePrpMaterialBusinessNo(packet);
					
					wfImageDealSchema.setBusinessNo("");
					wfImageDealSchema.setImageNo(businessNo);

					
					String strRiskCode = wfImageDealSchema.getRiskCode();
					if (strRiskCode != null) {
						strRiskCode = strRiskCode.trim();
						if (strRiskCode.startsWith("27")) {
							wfImageDealSchema.setRiskCode("2700");
						}
					}

					
					String iWherePart = "flowId='" + wfImageDealSchema.getFlowID()+"'";
					BLWfImageDeal blWfImageDeal = new BLWfImageDeal();
					blWfImageDeal.query(dbPool, iWherePart);

					if (blWfImageDeal.getSize() == 0) {
						blWfImageDeal.setArr(wfImageDealSchema);
						blWfImageDeal.save(dbPool);
					} else {
						blWfImageDeal.cancel(dbPool, wfImageDealSchema.getFlowID());
						blWfImageDeal.setArr(wfImageDealSchema);
						blWfImageDeal.save(dbPool);
					}

					if (blPrpMaterial.getSize() > 0) {
						blPrpMaterial.clearArr();
					}

					for (int i = 0; i < materials.size(); i++) {
						PrpNewMaterialSchema prpNewMaterialSchema = (PrpNewMaterialSchema) materials.get(i);
						prpNewMaterialSchema.setBusinessNo(businessNo);
						blPrpMaterial.setArr(prpNewMaterialSchema);
					}
					
					blPrpMaterial.cancel(dbPool, businessNo);
					blPrpMaterial.save(dbPool);
					returnString = encodeReturnMessage(requestType, "1", "0000", "成功生成初始任务");

				} else if ("1".equals(flowStatus)) {
					ImageTaskSchema imageTaskSchema = packet.getBody().getImageTask();
					String strImgStatus = imageTaskSchema.getImgStatus();
					String strFlowId = imageTaskSchema.getFlowId();
					String strBusinessNo = "";
					String strQuerySQL = " flowId='" + strFlowId+"'";
					BLWfImageDeal blWfImageDeal = new BLWfImageDeal();
					blWfImageDeal.query(strQuerySQL);
					if (blWfImageDeal.getSize() == 1) {
						
						String bizNo= blWfImageDeal.getArr(0).getBusinessNo();
						if(bizNo!=null&&bizNo.length()!=0){
							strBusinessNo=bizNo;
						}else{
						    strBusinessNo = blWfImageDeal.getArr(0).getImageNo();
						}
						
						if(strBusinessNo==null||strBusinessNo.length()==0){
							throw new Exception("任务flowId="+strFlowId+",对应的影像编号不存在");
						}
						

						if (blPrpMaterial.getSize() > 0) {
							blPrpMaterial.clearArr();
						}
						
						if ("1".equals(strImgStatus)) {
							List materials = generatePrpMaterials(packet, strUserName);
							dbPool.beginTransaction();
							
							blPrpMaterial.cancel(dbPool, strBusinessNo);

							for (int i = 0; i < materials.size(); i++) {
								PrpNewMaterialSchema prpNewMaterialSchema = (PrpNewMaterialSchema) materials.get(i);
								prpNewMaterialSchema.setBusinessNo(strBusinessNo);
								
								prpNewMaterialSchema.setSerialNo(blPrpMaterial.getMaxSerialNo(strBusinessNo)+1);
								
								blPrpMaterial.setArr(prpNewMaterialSchema);
							}
							blPrpMaterial.save(dbPool);
						} else if ("2".equals(strImgStatus)) {
							List materials = generatePrpMaterials(packet, strUserName);
							dbPool.beginTransaction();
							
							for (int i = 0; i < materials.size(); i++) {
								PrpNewMaterialSchema prpNewMaterialSchema = (PrpNewMaterialSchema) materials.get(i);
								
								if ("D".equals(prpNewMaterialSchema.getFileStatus())) {
									blPrpMaterial.deletePrpMaterialBySyaFileName(dbPool, strBusinessNo, prpNewMaterialSchema.getSysFileName().trim());
								} else {
									
									blPrpMaterial.deletePrpMaterialBySyaFileName(dbPool, strBusinessNo, prpNewMaterialSchema.getSysFileName().trim());
									prpNewMaterialSchema.setSerialNo(blPrpMaterial.getMaxSerialNo(strBusinessNo)+1);
									
									prpNewMaterialSchema.setBusinessNo(strBusinessNo);
									blPrpMaterial.setArr(prpNewMaterialSchema);
								}
							}
							blPrpMaterial.save(dbPool);
							returnString = encodeReturnMessage(requestType, "1", "0000", "更新成功");
						}
					} else {
						returnString = encodeReturnMessage(requestType, "0", "1000", "flowId为" + imageTaskSchema.getFlowId() + "的任务不存在");
					}

				}
				dbPool.commitTransaction();
			} catch (Exception e) {
				returnString = encodeReturnMessage(requestType, "0", "1000", "服务器出现异常:" + e.toString());
				e.printStackTrace();
				try {
					dbPool.rollbackTransaction();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			} finally {
				try {
					dbPool.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return returnString;
	}

	public static String encodeReturnMessage(String rType, String rCode, String eCode, String eMessage) {
		Document document = DocumentHelper.createDocument(); 
		document.setXMLEncoding("GBK");

		Element packet = document.addElement("Packet");
		packet.addAttribute("type", "RESPONSE");
		packet.addAttribute("version", "1.0");

		Element head = packet.addElement("Head");
		Element requestType = head.addElement("ResquestType"); 
		requestType.setText(rType);
		Element responseCode = head.addElement("ResponseCode");
		responseCode.setText(rCode);
		Element errorCode = head.addElement("ErrorCode");
		errorCode.setText(eCode);
		Element errorMessage = head.addElement("ErrorMessage");
		errorMessage.setText(eMessage);

		return document.asXML();
	}

	public static ImagePacketSchema decodeXML2Scheme(Element bodyElement, String requestType) throws DocumentException {
		ImagePacketSchema packet = new ImagePacketSchema();
		ImageHeadSchema head = new ImageHeadSchema();
		ImageBodySchema body = new ImageBodySchema();
		ImageTaskSchema task = new ImageTaskSchema();
		ImageItemSchema item = null;

		head.setRequestType(requestType);

		Element taskElement = bodyElement.element("ImageTask");
		task.setFlowId(taskElement.elementText("FlowId"));
		task.setBarCode(taskElement.elementText("BarCode"));
		task.setAppliName(taskElement.elementText("AppliName"));
		task.setDocType(taskElement.elementText("DocType"));
		task.setClassCode(taskElement.elementText("ClassCode"));
		task.setRiskCode(taskElement.elementText("RiskCode"));
		task.setBusinessType(taskElement.elementText("BusinessType"));
		task.setComCode(taskElement.elementText("ComCode"));
		task.setOperatorCode(taskElement.elementText("OperatorCode"));
		task.setFlowStatus(taskElement.elementText("FlowStatus"));
		task.setImgStatus(taskElement.elementText("ImgStatus"));
		
		task.setBatchFlag(taskElement.elementText("BatchFlag"));  
		
		
		task.setExplain(taskElement.elementText("Explain"));  
		
		
		task.setRemarks(taskElement.elementText("Remarks"));  
		
		

		Element images = taskElement.element("Images");
		List imgItems = new ArrayList();
		List items = images.elements("ImageItem");
		if (items != null) {
			for (int i = 0; i < items.size(); i++) {
				Element image = (Element) items.get(i);
				item = new ImageItemSchema();
				item.setSerialNo(image.elementText("SerialNo"));
				item.setSysFileName(image.elementText("SysFileName"));
				item.setFileType(image.elementText("FileType"));
				item.setFileStatus(image.elementText("FileStatus"));
				item.setFilePath(image.elementText("FilePath"));
				item.setFileName(image.elementText("FileName"));
				item.setDiscription(image.elementText("Discription"));
				imgItems.add(item);
			}

			task.setImages(imgItems);
		}

		body.setImageTask(task);
		packet.setHead(head);
		packet.setBody(body);
		return packet;
	}

	private static List generatePrpMaterials(ImagePacketSchema packet, String strUserName) {
		List materials = new ArrayList();
		List items = packet.getBody().getImageTask().getImages();
		PrpNewMaterialSchema prpNewMaterialSchema = null;
		ImageTaskSchema imageTaskSchema = packet.getBody().getImageTask();

		for (int i = 0; i < items.size(); i++) {
			prpNewMaterialSchema = new PrpNewMaterialSchema();
			ImageItemSchema imageItem = (ImageItemSchema) items.get(i);

			String strNo = imageItem.getSerialNo();
			int serialNo = 0;
			if (strNo != null && strNo.length() != 0) {
				serialNo = Integer.parseInt(strNo.trim());

				prpNewMaterialSchema.setSerialNo(serialNo);
				prpNewMaterialSchema.setSysFileName(imageItem.getSysFileName());
				prpNewMaterialSchema.setFileName(imageItem.getFileName().trim());
				prpNewMaterialSchema.setFilePath(imageItem.getFilePath());
				prpNewMaterialSchema.setFileType(imageItem.getFileType());
				prpNewMaterialSchema.setFileStatus(imageItem.getFileStatus());
				prpNewMaterialSchema.setDescription(imageItem.getDiscription());
				prpNewMaterialSchema.setOperatorCode(imageTaskSchema.getOperatorCode());
				prpNewMaterialSchema.setOperatorName(strUserName);
				prpNewMaterialSchema.setOperateTime(DateTime.current().toString().substring(0, 19));
				prpNewMaterialSchema.setFollowUpFlag("1");
				prpNewMaterialSchema.setBarCode(imageTaskSchema.getBarCode());
				materials.add(prpNewMaterialSchema);
			}
		}
		return (materials.size() == 0 ? null : materials);
	}
    
	private static WfImageDealSchema generateWFImageDealSchema(ImagePacketSchema packet, String strUserName,String strBatchFlag,String strExplain,String strRemark) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ImageTaskSchema imageTaskSchema = packet.getBody().getImageTask();
		WfImageDealSchema wfImageDealSchema = new WfImageDealSchema();
		wfImageDealSchema.setFlowID(imageTaskSchema.getFlowId());
		wfImageDealSchema.setLogNo("1");
		wfImageDealSchema.setAppliName(imageTaskSchema.getAppliName());
		wfImageDealSchema.setBarCode(imageTaskSchema.getBarCode());
		wfImageDealSchema.setDocType(imageTaskSchema.getDocType());
		wfImageDealSchema.setClassCode(imageTaskSchema.getClassCode());
		wfImageDealSchema.setRiskCode(imageTaskSchema.getRiskCode());
		wfImageDealSchema.setBusinessType(imageTaskSchema.getBusinessType());
		wfImageDealSchema.setComCode(imageTaskSchema.getComCode());
		wfImageDealSchema.setOperatorCode(imageTaskSchema.getOperatorCode());
		wfImageDealSchema.setOperatorName(strUserName);
		wfImageDealSchema.setFlowStatus(imageTaskSchema.getFlowStatus());
		wfImageDealSchema.setFlowInTime(sdf.format(new Date()));
		wfImageDealSchema.setHandleTime(sdf.format(new Date()));
		wfImageDealSchema.setSubmitTime(sdf.format(new Date()));
		wfImageDealSchema.setBusinessStatus("1");
		wfImageDealSchema.setFirstInTime(sdf.format(new Date()));
		wfImageDealSchema.setScanUserCode(imageTaskSchema.getOperatorCode());
		wfImageDealSchema.setScanUserName(strUserName);
		wfImageDealSchema.setBatchFlag(strBatchFlag);
		wfImageDealSchema.setExplain(strExplain);
		wfImageDealSchema.setRemark(strRemark);
		return wfImageDealSchema;
	}
	
	private static String generatePrpMaterialBusinessNo(ImagePacketSchema packet) {
		GregorianCalendar calendar = new GregorianCalendar();
		Date now = new Date();
		calendar.setTime(now);
		String strComCode = packet.getBody().getImageTask().getComCode();
		String strRiskCode = packet.getBody().getImageTask().getRiskCode();
		StringBuffer buf = new StringBuffer();
		if (strComCode.length() >= 4) {
			buf.append(strComCode.substring(0, 4));
		} else {
			buf.append(strComCode);
		}
		buf.append(strRiskCode);
		buf.append(calendar.get(Calendar.YEAR));
		buf.append(calendar.get(Calendar.MONTH));
		buf.append(calendar.get(Calendar.DAY_OF_MONTH));
		buf.append(calendar.get(Calendar.HOUR_OF_DAY));
		buf.append(calendar.get(Calendar.MINUTE));
		buf.append(calendar.get(Calendar.SECOND));
		buf.append(calendar.get(Calendar.MILLISECOND));
		return buf.toString();
	}

}
