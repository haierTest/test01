 package com.sp.prpall.blsvr.jf;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tools.zip.ZipOutputStream;

import com.sp.payment.blsvr.jf.BLPrpJplanCommission;
import com.sp.prpall.blsvr.jf.BLPrpJplanFee;
import com.sp.sysframework.exceptionlog.UserException;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utility.SysConst;
import com.sp.utility.string.Money;
import com.sp.utility.string.Str;
import com.sp.payment.schema.PrpJpayRefRecSchema;
import com.sp.prpall.schema.PrpJplanFeeSchema;

public class CreateExcelOfZIP{
    Map payRefReasonMap = new HashMap();  

    
	/**
	 *组织Excel数据，添加到ZIP(应收XX)
	 *@param  request 
	 *@param  response 
	 *@param  blPrpJplanfee 需要写入Excel的数据 
	 *@param  fromPage 来自哪个页面（ysbfJSP=应收XX，yfsxfJPS=应付手续费，yfpkJSP=应付赔款）
	 *@return 查询返回的状态，0表示正常
	 *@throws Exception
	 */
	public int createExcelYSBF(HttpServletRequest request,
			HttpServletResponse response,BLPrpJplanFee blPrpJplanfee,String fromPage) throws Exception,UserException {
	    if(blPrpJplanfee.getSize()==0){
	        throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYSBF()","本次所导出的数据条数为0，请核对或重新输入条件后导出！");  
	    }
	    
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		HSSFWorkbook wb = new HSSFWorkbook();
		int recNum = blPrpJplanfee.getSize();
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));
		
		HSSFCellStyle styletitle = wb.createCellStyle();
		HSSFFont ftitle = wb.createFont();
		ftitle.setFontHeightInPoints((short) 12);
		ftitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		styletitle.setFont(ftitle);
		styletitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styletitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		
		HSSFCellStyle stylefield = wb.createCellStyle();
		HSSFFont ffield = wb.createFont();
		ffield.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		stylefield.setFont(ffield);
		stylefield.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		stylefield.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		
		HSSFCellStyle stylechara = wb.createCellStyle();
		stylechara.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		stylechara.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFCellStyle stylenumber = wb.createCellStyle();
		stylenumber.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		stylenumber.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		HSSFSheet sheet = wb.createSheet();
		
		wb.setSheetName(0, "应收XX", HSSFWorkbook.ENCODING_UTF_16);
		

		
		sheet.setColumnWidth((short) 0, (short) 4000);
		sheet.setColumnWidth((short) 1, (short) 4000);
		sheet.setColumnWidth((short) 2, (short) 4000);
		sheet.setColumnWidth((short) 3, (short) 4000);
		sheet.setColumnWidth((short) 4, (short) 4000);
		sheet.setColumnWidth((short) 5, (short) 4000);
		sheet.setColumnWidth((short) 6, (short) 4000);
		sheet.setColumnWidth((short) 7, (short) 4000);
		sheet.setColumnWidth((short) 8, (short) 4000);
		sheet.setColumnWidth((short) 9, (short) 4000);
		sheet.setColumnWidth((short) 10, (short) 4000);

		HSSFRow row = sheet.createRow(0);
		HSSFCell cellT0 = row.createCell((short) 0);
		cellT0.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT0.setCellValue("业务号");
		cellT0.setCellStyle(stylefield);

		HSSFCell cellT1 = row.createCell((short) 1);
		cellT1.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT1.setCellValue("费用类别");
		cellT1.setCellStyle(stylefield);
		
		HSSFCell cellT2 = row.createCell((short) 2);
		cellT2.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT2.setCellValue("XX号");
		cellT2.setCellStyle(stylefield);
		
		HSSFCell cellT3 = row.createCell((short) 3);
		cellT3.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT3.setCellValue("起XXXXX日期");
		cellT3.setCellStyle(stylefield);
		
		HSSFCell cellT4 = row.createCell((short) 4);
		cellT4.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT4.setCellValue("缴费期数");
		cellT4.setCellStyle(stylefield);
		
		HSSFCell cellT5 = row.createCell((short) 5);
		cellT5.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT5.setCellValue("XXXXX种");
		cellT5.setCellStyle(stylefield);
		
		HSSFCell cellT6 = row.createCell((short) 6);
		cellT6.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT6.setCellValue("被XX人");
		cellT6.setCellStyle(stylefield);
		
		HSSFCell cellT7 = row.createCell((short) 7);
		cellT7.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT7.setCellValue("应收币别");
		cellT7.setCellStyle(stylefield);
		
		HSSFCell cellT8 = row.createCell((short) 8);
		cellT8.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT8.setCellValue("应收金额");
		cellT8.setCellStyle(stylefield);
		
		HSSFCell cellT9 = row.createCell((short) 9);
		cellT9.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT9.setCellValue("实收金额");
		cellT9.setCellStyle(stylefield);
		
		HSSFCell cellT10 = row.createCell((short) 10);
		cellT10.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT10.setCellValue("剩余金额");
		cellT10.setCellStyle(stylefield);
		
		if(recNum>=65535){
            throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYSBF()","本次所导出的数据条数过多，超出Excel最大容量，请分批导出！");  
        }
		
		for (int i = 0; i < recNum; i++) { 
                HSSFRow rowData = sheet.createRow((short) i + 1);
                
                
                HSSFCell cellData0 = rowData.createCell((short) 0);
                cellData0.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData0.setCellStyle(stylechara);
                cellData0.setCellValue(blPrpJplanfee.getArr(i).getCertiNo());
                
                
                String strPayRefReasonCode = "";
                strPayRefReasonCode = blPrpJplanfee.getArr(i).getPayRefReason();
                String strPayRefReasonName = "";
                
                if(payRefReasonMap.get(strPayRefReasonCode)==null){
                    BLPrpDcode blPrpDcode = new BLPrpDcode();
                    strPayRefReasonName = blPrpDcode.translateCode("PayRefReason",strPayRefReasonCode,true);
                    payRefReasonMap.put(strPayRefReasonCode, strPayRefReasonName);
                }else{
                    strPayRefReasonName = (String)payRefReasonMap.get(strPayRefReasonCode);
                }
                
                HSSFCell cellData1 = rowData.createCell((short) 1);
                cellData1.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData1.setCellStyle(stylechara);
                cellData1.setCellValue(strPayRefReasonName);
                
                
                HSSFCell cellData2 = rowData.createCell((short) 2);
                cellData2.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData2.setCellStyle(stylechara);
                cellData2.setCellValue(blPrpJplanfee.getArr(i).getPolicyNo());
                
                
                HSSFCell cellData3 = rowData.createCell((short) 3);
                cellData3.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData3.setCellStyle(stylechara);
                cellData3.setCellValue(blPrpJplanfee.getArr(i).getStartDate());
                
                
                HSSFCell cellData4 = rowData.createCell((short) 4);
                cellData4.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData4.setCellStyle(stylechara);
                cellData4.setCellValue(blPrpJplanfee.getArr(i).getPayNo());
                
                
                HSSFCell cellData5 = rowData.createCell((short) 5);
                cellData5.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData5.setCellStyle(stylechara);
                cellData5.setCellValue(blPrpJplanfee.getArr(i).getRiskCode());
                
                
                HSSFCell cellData6 = rowData.createCell((short) 6);
                cellData6.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData6.setCellStyle(stylechara);
                cellData6.setCellValue(blPrpJplanfee.getArr(i).getInsuredName());
                
                
                HSSFCell cellData7 = rowData.createCell((short) 7);
                cellData7.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData7.setCellStyle(stylechara);
                cellData7.setCellValue(blPrpJplanfee.getArr(i).getCurrency1());;
                
                
                HSSFCell cellData8 = rowData.createCell((short) 8);
                cellData8.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData8.setCellStyle(stylechara);
                cellData8.setCellValue(new Money().toAccount(blPrpJplanfee.getArr(i).getPlanFee())); ;
                
                
                HSSFCell cellData9 = rowData.createCell((short) 9);
                cellData9.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData9.setCellStyle(stylechara);
                cellData9.setCellValue(new Money().toAccount(blPrpJplanfee.getArr(i).getRealPayRefFee())); ;
                
                double dbPlanFee = Double.parseDouble(blPrpJplanfee.getArr(i).getPlanFee());
                double dbRealPayRefFee = Double.parseDouble(Str.chgStrZero(blPrpJplanfee.getArr(i).getRealPayRefFee()));
                double dbOweFee = dbPlanFee - dbRealPayRefFee;    
                
                
                HSSFCell cellData10 = rowData.createCell((short) 10);
                cellData10.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData10.setCellStyle(stylechara);
                cellData10.setCellValue(decimalFormat.format(dbOweFee));
                
                
                
        }
		File file = new File(SysConst.getProperty("XML_ROOT_PATH"));  
		file.mkdirs();
		
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddHHmmss");
		String time=dateformat.format(new Date());
		
		String oldFileName=fromPage+time; 
		String newFileNme=file+"/"+oldFileName; 
		
		
		ArrayList listRef = new ArrayList();
		
		output(wb, newFileNme+".xls");  
		listRef.add(newFileNme+".xls"); 

		HashMap map = new HashMap();
		
		map.put(newFileNme, newFileNme+".xls");
		try {
			zipFile(map, newFileNme+".zip");
			listRef.add(newFileNme+".zip");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		FileInputStream fin = new FileInputStream(newFileNme+".zip");
		sendFileToClient(response, fin, oldFileName+".zip","text/plain;charset=GBK");
		
       
	   try {
		   for(int i =0;i<listRef.size();i++)
			   delFolder(listRef.get(i).toString());
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	   return 0;
	}
	
	
	
	
	
	/**
	 *组织Excel数据，添加到ZIP(应付手续费)
	 *@param  request 
	 *@param  response 
	 *@param  blPrpJplanfee 需要写入Excel的数据 
	 *@param  fromPage 来自哪个页面（ysbfJSP=应收XX，yfsxfJPS=应付手续费，yfpkJSP=应付赔款）
	 *@return 查询返回的状态，0表示正常
	 *@throws Exception
	 */
	public int createExcelYFSXF(HttpServletRequest request,
			HttpServletResponse response,BLPrpJplanCommission blPrpJplanCommission,String fromPage) throws Exception,UserException {
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		 if(blPrpJplanCommission.getSize()==0){
	         throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYFSXF()","本次所导出的数据条数为0，请核对或重新输入条件后导出！");  
	     }
		
		HSSFWorkbook wb = new HSSFWorkbook();
		int recNum = blPrpJplanCommission.getSize();

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));
		
		HSSFCellStyle styletitle = wb.createCellStyle();
		HSSFFont ftitle = wb.createFont();
		ftitle.setFontHeightInPoints((short) 12);
		ftitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		styletitle.setFont(ftitle);
		styletitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styletitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		
		HSSFCellStyle stylefield = wb.createCellStyle();
		HSSFFont ffield = wb.createFont();
		ffield.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		stylefield.setFont(ffield);
		stylefield.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		stylefield.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		
		HSSFCellStyle stylechara = wb.createCellStyle();
		stylechara.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		stylechara.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFCellStyle stylenumber = wb.createCellStyle();
		stylenumber.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		stylenumber.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		HSSFSheet sheet = wb.createSheet();
		
		wb.setSheetName(0, "应付手续费", HSSFWorkbook.ENCODING_UTF_16);
		

		
		sheet.setColumnWidth((short) 0, (short) 4000);
		sheet.setColumnWidth((short) 1, (short) 4000);
		sheet.setColumnWidth((short) 2, (short) 4000);
		sheet.setColumnWidth((short) 3, (short) 4000);
		sheet.setColumnWidth((short) 4, (short) 4000);
		sheet.setColumnWidth((short) 5, (short) 4000);
		sheet.setColumnWidth((short) 6, (short) 4000);
		sheet.setColumnWidth((short) 7, (short) 4000);
		sheet.setColumnWidth((short) 8, (short) 4000);
		sheet.setColumnWidth((short) 9, (short) 4000);
		sheet.setColumnWidth((short) 10, (short) 4000);
		sheet.setColumnWidth((short) 11, (short) 4000);
		sheet.setColumnWidth((short) 12, (short) 4000);
		sheet.setColumnWidth((short) 13, (short) 4000);
		sheet.setColumnWidth((short) 14, (short) 4000);

		HSSFRow row = sheet.createRow(0);
		HSSFCell cellT0 = row.createCell((short) 0);
		cellT0.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT0.setCellValue("业务号");
		cellT0.setCellStyle(stylefield);

		HSSFCell cellT1 = row.createCell((short) 1);
		cellT1.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT1.setCellValue("费用类别");
		cellT1.setCellStyle(stylefield);
		
		HSSFCell cellT2 = row.createCell((short) 2);
		cellT2.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT2.setCellValue("XX号");
		cellT2.setCellStyle(stylefield);
		
		HSSFCell cellT3 = row.createCell((short) 3);
		cellT3.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT3.setCellValue("起XXXXX日期");
		cellT3.setCellStyle(stylefield);
		
		HSSFCell cellT4 = row.createCell((short) 4);
		cellT4.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT4.setCellValue("缴费期数");
		cellT4.setCellStyle(stylefield);
		
		HSSFCell cellT5 = row.createCell((short) 5);
		cellT5.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT5.setCellValue("XXXXX种");
		cellT5.setCellStyle(stylefield);
		
		HSSFCell cellT6 = row.createCell((short) 6);
		cellT6.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT6.setCellValue("代理人代码");
		cellT6.setCellStyle(stylefield);
		
		HSSFCell cellT7 = row.createCell((short) 7);
		cellT7.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT7.setCellValue("代理人名称");
		cellT7.setCellStyle(stylefield);
		
		HSSFCell cellT8 = row.createCell((short) 8);
		cellT8.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT8.setCellValue("被XX人");
		cellT8.setCellStyle(stylefield);
		
		HSSFCell cellT9 = row.createCell((short) 9);
		cellT9.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT9.setCellValue("应付币别");
		cellT9.setCellStyle(stylefield);
		
		HSSFCell cellT10 = row.createCell((short) 10);
		cellT10.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT10.setCellValue("手续费比例(%)");
		cellT10.setCellStyle(stylefield);
		
		HSSFCell cellT11 = row.createCell((short) 11);
		cellT11.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT11.setCellValue("XX金额");
		cellT11.setCellStyle(stylefield);
		
		HSSFCell cellT12 = row.createCell((short) 12);
		cellT12.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT12.setCellValue("应付金额");
		cellT12.setCellStyle(stylefield);
		
		HSSFCell cellT13 = row.createCell((short) 13);
		cellT13.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT13.setCellValue("实付金额");
		cellT13.setCellStyle(stylefield);	
		
		HSSFCell cellT14 = row.createCell((short) 14);
		cellT14.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT14.setCellValue("剩余金额");
		cellT14.setCellStyle(stylefield);
		
	    if(recNum>=65535){
	        throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYFSXF()","本次所导出的数据条数过多，超出Excel最大容量，请分批导出！");  
	    }
		
		for (int i = 0; i < recNum; i++) { 
                HSSFRow rowData = sheet.createRow((short) i + 1);
                
                
                HSSFCell cellData0 = rowData.createCell((short) 0);
                cellData0.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData0.setCellStyle(stylechara);
                cellData0.setCellValue(blPrpJplanCommission.getArr(i).getCertiNo());
                
                
                String strPayRefReasonCode = "";
                strPayRefReasonCode = blPrpJplanCommission.getArr(i).getPayRefReason();
                String strPayRefReasonName = "";
                if(payRefReasonMap.get(strPayRefReasonCode)==null){
                    BLPrpDcode blPrpDcode = new BLPrpDcode();
                    strPayRefReasonName = blPrpDcode.translateCode("PayRefReason",strPayRefReasonCode,true);
                    payRefReasonMap.put(strPayRefReasonCode, strPayRefReasonName);
                }else{
                    strPayRefReasonName = (String)payRefReasonMap.get(strPayRefReasonCode);
                }
                
                HSSFCell cellData1 = rowData.createCell((short) 1);
                cellData1.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData1.setCellStyle(stylechara);
                cellData1.setCellValue(strPayRefReasonName);
                
                
                HSSFCell cellData2 = rowData.createCell((short) 2);
                cellData2.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData2.setCellStyle(stylechara);
                cellData2.setCellValue(blPrpJplanCommission.getArr(i).getPolicyNo());
                
                
                HSSFCell cellData3 = rowData.createCell((short) 3);
                cellData3.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData3.setCellStyle(stylechara);
                cellData3.setCellValue(blPrpJplanCommission.getArr(i).getStartDate());
                
                
                HSSFCell cellData4 = rowData.createCell((short) 4);
                cellData4.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData4.setCellStyle(stylechara);
                cellData4.setCellValue(blPrpJplanCommission.getArr(i).getPayNo());
                
                
                HSSFCell cellData5 = rowData.createCell((short) 5);
                cellData5.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData5.setCellStyle(stylechara);
                cellData5.setCellValue(blPrpJplanCommission.getArr(i).getRiskCode());
                
                
                HSSFCell cellData6 = rowData.createCell((short) 6);
                cellData6.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData6.setCellStyle(stylechara);
                cellData6.setCellValue(blPrpJplanCommission.getArr(i).getAgentCode());
                
                
                HSSFCell cellData7 = rowData.createCell((short) 7);
                cellData7.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData7.setCellStyle(stylechara);
                cellData7.setCellValue(blPrpJplanCommission.getArr(i).getAgentName());
                
                
                HSSFCell cellData8 = rowData.createCell((short) 8);
                cellData8.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData8.setCellStyle(stylechara);
                cellData8.setCellValue(blPrpJplanCommission.getArr(i).getInsuredName());
                
                
                HSSFCell cellData9 = rowData.createCell((short) 9);
                cellData9.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData9.setCellStyle(stylechara);
                cellData9.setCellValue(blPrpJplanCommission.getArr(i).getCurrency1());
                
                
                HSSFCell cellData10 = rowData.createCell((short) 10);
                cellData10.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData10.setCellStyle(stylechara);
                cellData10.setCellValue(blPrpJplanCommission.getArr(i).getDisRate());
                
                
                HSSFCell cellData11 = rowData.createCell((short) 11);
                cellData11.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData11.setCellStyle(stylechara);
                cellData11.setCellValue(new Money().toAccount(blPrpJplanCommission.getArr(i).getRealRefPremium()));
                

                
                HSSFCell cellData12 = rowData.createCell((short) 12);
                cellData12.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData12.setCellStyle(stylechara);
                cellData12.setCellValue(new Money().toAccount(blPrpJplanCommission.getArr(i).getPlanFee())); ;
                
                
                HSSFCell cellData13 = rowData.createCell((short) 13);
                cellData13.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData13.setCellStyle(stylechara);
                cellData13.setCellValue(new Money().toAccount(blPrpJplanCommission.getArr(i).getRealPayRefFee())); ;
                
                double dblRealRefPremium = Double.parseDouble(blPrpJplanCommission.getArr(i).getRealRefPremium());
                double dbPlanFee = Double.parseDouble(blPrpJplanCommission.getArr(i).getPlanFee());
                double dbRealPayRefFee = Double.parseDouble(Str.chgStrZero(blPrpJplanCommission.getArr(i).getRealPayRefFee()));
                double dbOweFee = dbPlanFee - dbRealPayRefFee;    
                
                
                HSSFCell cellData14 = rowData.createCell((short) 14);
                cellData14.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData14.setCellStyle(stylechara);
                cellData14.setCellValue(decimalFormat.format(dbOweFee));
                
                
                
        }
		File file = new File(SysConst.getProperty("XML_ROOT_PATH"));  
		file.mkdirs();
		
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddHHmmss");
		String time=dateformat.format(new Date());
		
		String oldFileName=fromPage+time; 
		String newFileNme=file+"/"+oldFileName; 
		
		
		ArrayList listRef = new ArrayList();
		output(wb, newFileNme+".xls");  
		listRef.add(newFileNme+".xls"); 

		HashMap map = new HashMap();
		
		map.put(newFileNme, newFileNme+".xls");
		try {
			zipFile(map, newFileNme+".zip");
			listRef.add(newFileNme+".zip");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		FileInputStream fin = new FileInputStream(newFileNme+".zip");
		sendFileToClient(response, fin, oldFileName+".zip","text/plain;charset=GBK");
		
       
	   try {
		   for(int i =0;i<listRef.size();i++)
			   delFolder(listRef.get(i).toString());
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	   return 0;
	}
	
	
	
	/**
	 *组织Excel数据，添加到ZIP(应付赔款)
	 *@param  request 
	 *@param  response 
	 *@param  blPrpJplanfee 需要写入Excel的数据 
	 *@param  fromPage 来自哪个页面（ysbfJSP=应收XX，yfsxfJPS=应付手续费，yfpkJSP=应付赔款）
	 *@return 查询返回的状态，0表示正常
	 *@throws Exception
	 */
	public int createExcelYFPK(HttpServletRequest request,
			HttpServletResponse response,BLPrpJplanFee blPrpJplanfee,String fromPage) throws Exception,UserException {
	    if(blPrpJplanfee.getSize()==0){
            throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYFPK()","本次所导出的数据条数为0，请核对或重新输入条件后导出！");  
        }
		
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		
		HSSFWorkbook wb = new HSSFWorkbook();
		int recNum = blPrpJplanfee.getSize();

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));
		
		HSSFCellStyle styletitle = wb.createCellStyle();
		HSSFFont ftitle = wb.createFont();
		ftitle.setFontHeightInPoints((short) 12);
		ftitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		styletitle.setFont(ftitle);
		styletitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styletitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		
		HSSFCellStyle stylefield = wb.createCellStyle();
		HSSFFont ffield = wb.createFont();
		ffield.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		stylefield.setFont(ffield);
		stylefield.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		stylefield.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		
		HSSFCellStyle stylechara = wb.createCellStyle();
		stylechara.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		stylechara.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFCellStyle stylenumber = wb.createCellStyle();
		stylenumber.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		stylenumber.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		HSSFSheet sheet = wb.createSheet();
		
		wb.setSheetName(0, "应付赔款", HSSFWorkbook.ENCODING_UTF_16);
		

		
		sheet.setColumnWidth((short) 0, (short) 4000);
		sheet.setColumnWidth((short) 1, (short) 4000);
		sheet.setColumnWidth((short) 2, (short) 4000);
		sheet.setColumnWidth((short) 3, (short) 4000);
		sheet.setColumnWidth((short) 4, (short) 4000);
		sheet.setColumnWidth((short) 5, (short) 4000);
		sheet.setColumnWidth((short) 6, (short) 4000);
		sheet.setColumnWidth((short) 7, (short) 4000);
		sheet.setColumnWidth((short) 8, (short) 4000);
		sheet.setColumnWidth((short) 9, (short) 4000);
		sheet.setColumnWidth((short) 10, (short) 4000);

		HSSFRow row = sheet.createRow(0);
		HSSFCell cellT0 = row.createCell((short) 0);
		cellT0.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT0.setCellValue("业务号");
		cellT0.setCellStyle(stylefield);

		HSSFCell cellT1 = row.createCell((short) 1);
		cellT1.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT1.setCellValue("费用类别");
		cellT1.setCellStyle(stylefield);
		
		HSSFCell cellT2 = row.createCell((short) 2);
		cellT2.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT2.setCellValue("XX号");
		cellT2.setCellStyle(stylefield);
		
		HSSFCell cellT3 = row.createCell((short) 3);
		cellT3.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT3.setCellValue("核赔日期");
		cellT3.setCellStyle(stylefield);
		
		HSSFCell cellT4 = row.createCell((short) 4);
		cellT4.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT4.setCellValue("缴费期数");
		cellT4.setCellStyle(stylefield);
		
		HSSFCell cellT5 = row.createCell((short) 5);
		cellT5.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT5.setCellValue("XXXXX种");
		cellT5.setCellStyle(stylefield);
		
		HSSFCell cellT6 = row.createCell((short) 6);
		cellT6.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT6.setCellValue("被XX人");
		cellT6.setCellStyle(stylefield);
		
		HSSFCell cellT7 = row.createCell((short) 7);
		cellT7.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT7.setCellValue("应收币别");
		cellT7.setCellStyle(stylefield);
		
		HSSFCell cellT8 = row.createCell((short) 8);
		cellT8.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT8.setCellValue("应收金额");
		cellT8.setCellStyle(stylefield);
		
		HSSFCell cellT9 = row.createCell((short) 9);
		cellT9.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT9.setCellValue("实收金额");
		cellT9.setCellStyle(stylefield);
		
		HSSFCell cellT10 = row.createCell((short) 10);
		cellT10.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT10.setCellValue("剩余金额");
		cellT10.setCellStyle(stylefield);
		
	    if(recNum>=65535){
	        throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYFPK()","本次所导出的数据条数过多，超出Excel最大容量，请分批导出！");  
	    }
		
		for (int i = 0; i < recNum; i++) { 
                HSSFRow rowData = sheet.createRow((short) i + 1);
                
                
                HSSFCell cellData0 = rowData.createCell((short) 0);
                cellData0.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData0.setCellStyle(stylechara);
                cellData0.setCellValue(blPrpJplanfee.getArr(i).getCertiNo());
                
                
                String strPayRefReasonCode = "";
                strPayRefReasonCode = blPrpJplanfee.getArr(i).getPayRefReason();
                String strPayRefReasonName = "";
                if(payRefReasonMap.get(strPayRefReasonCode)==null){
                    BLPrpDcode blPrpDcode = new BLPrpDcode();
                    strPayRefReasonName = blPrpDcode.translateCode("PayRefReason",strPayRefReasonCode,true);
                    payRefReasonMap.put(strPayRefReasonCode, strPayRefReasonName);
                }else{
                    strPayRefReasonName = (String)payRefReasonMap.get(strPayRefReasonCode);
                }
                
                HSSFCell cellData1 = rowData.createCell((short) 1);
                cellData1.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData1.setCellStyle(stylechara);
                cellData1.setCellValue(strPayRefReasonName);
                
                
                HSSFCell cellData12 = rowData.createCell((short) 2);
                cellData12.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData12.setCellStyle(stylechara);
                cellData12.setCellValue(blPrpJplanfee.getArr(i).getPolicyNo());
                
                
                HSSFCell cellData13 = rowData.createCell((short) 3);
                cellData13.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData13.setCellStyle(stylechara);
                cellData13.setCellValue(blPrpJplanfee.getArr(i).getUnderWriteDate());
                
                
                HSSFCell cellData14 = rowData.createCell((short) 4);
                cellData14.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData14.setCellStyle(stylechara);
                cellData14.setCellValue(blPrpJplanfee.getArr(i).getPayNo());
                
                
                HSSFCell cellData15 = rowData.createCell((short) 5);
                cellData15.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData15.setCellStyle(stylechara);
                cellData15.setCellValue(blPrpJplanfee.getArr(i).getRiskCode());
                
                
                HSSFCell cellData16 = rowData.createCell((short) 6);
                cellData16.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData16.setCellStyle(stylechara);
                cellData16.setCellValue(blPrpJplanfee.getArr(i).getInsuredName());
                
                
                HSSFCell cellData17 = rowData.createCell((short) 7);
                cellData17.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData17.setCellStyle(stylechara);
                cellData17.setCellValue(blPrpJplanfee.getArr(i).getCurrency1());;
                
                
                HSSFCell cellData18 = rowData.createCell((short) 8);
                cellData18.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData18.setCellStyle(stylechara);
                cellData18.setCellValue(new Money().toAccount(blPrpJplanfee.getArr(i).getPlanFee())); ;
                
                
                HSSFCell cellData19 = rowData.createCell((short) 9);
                cellData19.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData19.setCellStyle(stylechara);
                cellData19.setCellValue(new Money().toAccount(blPrpJplanfee.getArr(i).getRealPayRefFee())); ;
                
                double dbPlanFee = Double.parseDouble(blPrpJplanfee.getArr(i).getPlanFee());
                double dbRealPayRefFee = Double.parseDouble(Str.chgStrZero(blPrpJplanfee.getArr(i).getRealPayRefFee()));
                double dbOweFee = dbPlanFee - dbRealPayRefFee;    
                
                
                HSSFCell cellData110 = rowData.createCell((short) 10);
                cellData110.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData110.setCellStyle(stylechara);
                cellData110.setCellValue(decimalFormat.format(dbOweFee));
                
                
                
        }
		File file = new File(SysConst.getProperty("XML_ROOT_PATH"));  
		file.mkdirs();
		
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddHHmmss");
		String time=dateformat.format(new Date());
		
		String oldFileName=fromPage+time; 
		String newFileNme=file+"/"+oldFileName; 
		
		
		ArrayList listRef = new ArrayList();
		output(wb, newFileNme+".xls");  
		listRef.add(newFileNme+".xls"); 

		HashMap map = new HashMap();
		
		map.put(newFileNme, newFileNme+".xls");
		try {
			zipFile(map, newFileNme+".zip");
			listRef.add(newFileNme+".zip");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		FileInputStream fin = new FileInputStream(newFileNme+".zip");
		sendFileToClient(response, fin, oldFileName+".zip","text/plain;charset=GBK");
		
       
	   try {
		   for(int i =0;i<listRef.size();i++)
			   delFolder(listRef.get(i).toString());
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	   return 0;
	}
	
	/**
	 *组织Excel数据，添加到ZIP(预借发票信息)
	 *@param  request 
	 *@param  response 
	 *@param  blPrpJplanfee 需要写入Excel的数据 
	 *@param  fromPage 来自哪个页面（YJFP）
	 *@return 查询返回的状态，0表示正常
	 *@throws Exception
	 */
	public int createExcelYJFP(HttpServletRequest request,
			HttpServletResponse response,BLPrpJplanFee blPrpJplanFee,String fromPage) throws Exception,UserException {
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		 if(blPrpJplanFee.getSize()==0){
	         throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYJFP()","本次所导出的数据条数为0，请核对或重新输入条件后导出！");  
	     }
		
		HSSFWorkbook wb = new HSSFWorkbook();
		int recNum = blPrpJplanFee.getSize();

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-mm-dd"));
		
		HSSFCellStyle styletitle = wb.createCellStyle();
		HSSFFont ftitle = wb.createFont();
		ftitle.setFontHeightInPoints((short) 12);
		ftitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		styletitle.setFont(ftitle);
		styletitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		styletitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		
		HSSFCellStyle stylefield = wb.createCellStyle();
		HSSFFont ffield = wb.createFont();
		ffield.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		stylefield.setFont(ffield);
		stylefield.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		stylefield.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		
		HSSFCellStyle stylechara = wb.createCellStyle();
		stylechara.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		stylechara.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		HSSFCellStyle stylenumber = wb.createCellStyle();
		stylenumber.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		stylenumber.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		HSSFSheet sheet = wb.createSheet();
		
		wb.setSheetName(0, "预借发票信息", HSSFWorkbook.ENCODING_UTF_16);
		

		
		sheet.setColumnWidth((short) 0, (short) 4000);
		sheet.setColumnWidth((short) 1, (short) 4000);
		sheet.setColumnWidth((short) 2, (short) 4000);
		sheet.setColumnWidth((short) 3, (short) 4000);
		sheet.setColumnWidth((short) 4, (short) 4000);
		sheet.setColumnWidth((short) 5, (short) 4000);
		sheet.setColumnWidth((short) 6, (short) 4000);
		sheet.setColumnWidth((short) 7, (short) 4000);
		sheet.setColumnWidth((short) 8, (short) 4000);
		sheet.setColumnWidth((short) 9, (short) 4000);
		sheet.setColumnWidth((short) 10, (short) 4000);
		sheet.setColumnWidth((short) 11, (short) 4000);
		sheet.setColumnWidth((short) 12, (short) 4000);

		HSSFRow row = sheet.createRow(0);
		HSSFCell cellT0 = row.createCell((short) 0);
		cellT0.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT0.setCellValue("业务类型");
		cellT0.setCellStyle(stylefield);

		HSSFCell cellT1 = row.createCell((short) 1);
		cellT1.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT1.setCellValue("业务号");
		cellT1.setCellStyle(stylefield);
		
		HSSFCell cellT2 = row.createCell((short) 2);
		cellT2.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT2.setCellValue("XX号");
		cellT2.setCellStyle(stylefield);
						
		HSSFCell cellT3 = row.createCell((short) 3);
		cellT3.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT3.setCellValue("缴费期数");
		cellT3.setCellStyle(stylefield);
		
		HSSFCell cellT4 = row.createCell((short) 4);
		cellT4.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT4.setCellValue("业务归属员");
		cellT4.setCellStyle(stylefield);
		
		HSSFCell cellT5 = row.createCell((short) 5);
		cellT5.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT5.setCellValue("业务归属部门");
		cellT5.setCellStyle(stylefield);
		
		HSSFCell cellT6 = row.createCell((short) 6);
		cellT6.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT6.setCellValue("被XX人");
		cellT6.setCellStyle(stylefield);
		
		HSSFCell cellT7 = row.createCell((short) 7);
		cellT7.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT7.setCellValue("应收金额");
		cellT7.setCellStyle(stylefield);
		
		HSSFCell cellT8 = row.createCell((short) 8);
		cellT8.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT8.setCellValue("起XXXXX日期");
		cellT8.setCellStyle(stylefield);
		
		HSSFCell cellT9 = row.createCell((short) 9);
		cellT9.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT9.setCellValue("终XXXXX日期");
		cellT9.setCellStyle(stylefield);
		
		HSSFCell cellT10 = row.createCell((short) 10);
		cellT10.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT10.setCellValue("核XXXXX日期");
		cellT10.setCellStyle(stylefield);
		
		
		HSSFCell cellT11 = row.createCell((short) 11);
		cellT11.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT11.setCellValue("预计归还日期");
		cellT11.setCellStyle(stylefield);
		
		HSSFCell cellT12 = row.createCell((short) 12);
		cellT12.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT12.setCellValue("预借发票状态");
		cellT12.setCellStyle(stylefield);
		

		
	    if(recNum>=65535){
	        throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYFJX()","本次所导出的数据条数过多，超出Excel最大容量，请分批导出！");  
	    }
		
		for (int i = 0; i < recNum; i++) { 
                HSSFRow rowData = sheet.createRow((short) i + 1);
                
                
                HSSFCell cellData0 = rowData.createCell((short) 0);
                cellData0.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData0.setCellStyle(stylechara);
                if("P".equals(blPrpJplanFee.getArr(i).getCertiType())){
                	cellData0.setCellValue("XX");                	
                }else if("E".equals(blPrpJplanFee.getArr(i).getCertiType())){
                	cellData0.setCellValue("批单");
                }
                               
                
                HSSFCell cellData1 = rowData.createCell((short) 1);
                cellData1.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData1.setCellStyle(stylechara);
                cellData1.setCellValue(blPrpJplanFee.getArr(i).getCertiNo());
                
                
                HSSFCell cellData2 = rowData.createCell((short) 2);
                cellData2.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData2.setCellStyle(stylechara);
                cellData2.setCellValue(blPrpJplanFee.getArr(i).getPolicyNo());
                
                
                HSSFCell cellData3 = rowData.createCell((short) 3);
                cellData3.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData3.setCellStyle(stylechara);
                cellData3.setCellValue(blPrpJplanFee.getArr(i).getSerialNo());
                
                
                HSSFCell cellData4 = rowData.createCell((short) 4);
                cellData4.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData4.setCellStyle(stylechara);
                cellData4.setCellValue(blPrpJplanFee.getArr(i).getHandler1Code());
                
                
                HSSFCell cellData5 = rowData.createCell((short) 5);
                cellData5.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData5.setCellStyle(stylechara);
                cellData5.setCellValue(blPrpJplanFee.getArr(i).getComCode());
                
                
                HSSFCell cellData6 = rowData.createCell((short) 6);
                cellData6.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData6.setCellStyle(stylechara);
                cellData6.setCellValue(blPrpJplanFee.getArr(i).getInsuredName());
                
                
                HSSFCell cellData7 = rowData.createCell((short) 7);
                cellData7.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData7.setCellStyle(stylechara);
                cellData7.setCellValue(new Money().toAccount(blPrpJplanFee.getArr(i).getPlanFee()));
                
                
                HSSFCell cellData8 = rowData.createCell((short) 8);
                cellData8.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData8.setCellStyle(stylechara);
                cellData8.setCellValue(blPrpJplanFee.getArr(i).getStartDate());
                
                
                HSSFCell cellData9 = rowData.createCell((short) 9);
                cellData9.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData9.setCellStyle(stylechara);
                cellData9.setCellValue(blPrpJplanFee.getArr(i).getEndDate());
                
                
                HSSFCell cellData10 = rowData.createCell((short) 10);
                cellData10.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData10.setCellStyle(stylechara);
                cellData10.setCellValue(blPrpJplanFee.getArr(i).getUnderWriteDate());
                
                
                HSSFCell cellData11 = rowData.createCell((short) 11);
                cellData11.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData11.setCellStyle(stylechara);
                cellData11.setCellValue(blPrpJplanFee.getArr(i).getPackageDate());
                
                
                String strApproveStatus = blPrpJplanFee.getArr(i).getApproveStatus();
                String strApproveStatusName = "";
                if(strApproveStatus.equals("0")){
                	strApproveStatusName="待审核";
                }else if(strApproveStatus.equals("1")){
                	strApproveStatusName="审核通过";
                }else if(strApproveStatus.equals("2")){
                	strApproveStatusName="已驳回";
                }else if(strApproveStatus.equals("3")){
                	strApproveStatusName="已打印";
                }else if(strApproveStatus.equals("4")){
                	strApproveStatusName="已收回";
                }else if(strApproveStatus.equals("5")){
                	strApproveStatusName="已核销";
                }
                
                HSSFCell cellData12 = rowData.createCell((short) 12);
                cellData12.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData12.setCellStyle(stylechara);
                cellData12.setCellValue(strApproveStatusName);      
        }
		File file = new File(SysConst.getProperty("XML_ROOT_PATH"));  
		file.mkdirs();
		
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddHHmmss");
		String time=dateformat.format(new Date());
		
		String oldFileName=fromPage+time; 
		String newFileNme=file+"/"+oldFileName; 
		
		
		ArrayList listRef = new ArrayList();
		output(wb, newFileNme+".xls");  
		listRef.add(newFileNme+".xls"); 

		HashMap map = new HashMap();
		
		map.put(newFileNme, newFileNme+".xls");
		try {
			zipFile(map, newFileNme+".zip");
			listRef.add(newFileNme+".zip");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		FileInputStream fin = new FileInputStream(newFileNme+".zip");
		sendFileToClient(response, fin, oldFileName+".zip","text/plain;charset=GBK");
		
       
	   try {
		   for(int i =0;i<listRef.size();i++)
			   delFolder(listRef.get(i).toString());
	   } catch (Exception e) {
		   e.printStackTrace();
	   }
	   return 0;
	}
	
	
	
	/**
	 * 生成Excel
	 * @param wb Excel对象
	 * @param fileName 目标文件名
	 * @throws Exception
	 */
	public void output(HSSFWorkbook wb, String fileName) {
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(fileName);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fileOut!=null){
				try{
					fileOut.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 将多个文件生成压缩文件
	 * 
	 * @param originFiles  源文件
	 * @param destZipFilename 目标文件名
	 * @throws Exception
	 */
	public void zipFile(HashMap originFiles, String destZipFile) throws Exception {
		int BUFFER = 512;
		if (originFiles == null || originFiles.size() == 0
				|| destZipFile == null || destZipFile.trim().equals("")) {
			return;
		}
		BufferedInputStream bis = null;
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(
				new CheckedOutputStream(new FileOutputStream(destZipFile), new Adler32()));
			Iterator i = originFiles.values().iterator();
			while (i.hasNext()) {
				String fileName = (String) i.next();
				bis = new BufferedInputStream(new FileInputStream(fileName),BUFFER);
				org.apache.tools.zip.ZipEntry entry = new org.apache.tools.zip.ZipEntry(fileName.substring(fileName.lastIndexOf("/") + 1));
				zos.putNextEntry(entry);
				byte data[] = new byte[BUFFER];
				int nNumber = 0;
				while ((nNumber = bis.read(data, 0, BUFFER)) != -1) {
					zos.write(data, 0, nNumber);
				}
				bis.close();
			}
			zos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(bis!=null){
				bis.close();
			}
			if(zos!=null){
				zos.close();
			}
		}
	}
	
	
	/**
	 * 发送
	 * @param response httpServletResponse
	 * @param in inputStream
	 * @param fileName 显示文件名
	 * @param contentType contentType
	 * @throws Exception
	 */
	public void sendFileToClient(HttpServletResponse response,
			InputStream in, String fileName, String contentType)
			throws Exception {
		ServletOutputStream out = response.getOutputStream();
		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment;filename="+fileName);
		
	    
	     
		
		BufferedInputStream bis = new BufferedInputStream(in);
		BufferedOutputStream bos = new BufferedOutputStream(out);

		byte[] buff = new byte[2048];
		int bytesRead;
		try{
			
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			if (bis != null) {
				bis.close();
			}
			if (bos != null) {
				bos.close();
			}
		}
	}
	
	
	
	/**
	 * 删除指定目录，包括其中的任何文件和目录
	 * @param path
	 * @throws Exception
	 */
	public void delFolder(String path)throws Exception {
		try {
			File file = new File(path);
			if (file.isDirectory()) {
				String[] files = file.list();
				for (int i = 0; i < files.length; i++)
					delFolder(path + "\\" + files[i]);
				file.delete();
			} else {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	
}
