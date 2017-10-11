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
	 *��֯Excel���ݣ���ӵ�ZIP(Ӧ��XX)
	 *@param  request 
	 *@param  response 
	 *@param  blPrpJplanfee ��Ҫд��Excel������ 
	 *@param  fromPage �����ĸ�ҳ�棨ysbfJSP=Ӧ��XX��yfsxfJPS=Ӧ�������ѣ�yfpkJSP=Ӧ����
	 *@return ��ѯ���ص�״̬��0��ʾ����
	 *@throws Exception
	 */
	public int createExcelYSBF(HttpServletRequest request,
			HttpServletResponse response,BLPrpJplanFee blPrpJplanfee,String fromPage) throws Exception,UserException {
	    if(blPrpJplanfee.getSize()==0){
	        throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYSBF()","��������������������Ϊ0����˶Ի��������������󵼳���");  
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
		
		wb.setSheetName(0, "Ӧ��XX", HSSFWorkbook.ENCODING_UTF_16);
		

		
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
		cellT0.setCellValue("ҵ���");
		cellT0.setCellStyle(stylefield);

		HSSFCell cellT1 = row.createCell((short) 1);
		cellT1.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT1.setCellValue("�������");
		cellT1.setCellStyle(stylefield);
		
		HSSFCell cellT2 = row.createCell((short) 2);
		cellT2.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT2.setCellValue("XX��");
		cellT2.setCellStyle(stylefield);
		
		HSSFCell cellT3 = row.createCell((short) 3);
		cellT3.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT3.setCellValue("��XXXXX����");
		cellT3.setCellStyle(stylefield);
		
		HSSFCell cellT4 = row.createCell((short) 4);
		cellT4.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT4.setCellValue("�ɷ�����");
		cellT4.setCellStyle(stylefield);
		
		HSSFCell cellT5 = row.createCell((short) 5);
		cellT5.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT5.setCellValue("XXXXX��");
		cellT5.setCellStyle(stylefield);
		
		HSSFCell cellT6 = row.createCell((short) 6);
		cellT6.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT6.setCellValue("��XX��");
		cellT6.setCellStyle(stylefield);
		
		HSSFCell cellT7 = row.createCell((short) 7);
		cellT7.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT7.setCellValue("Ӧ�ձұ�");
		cellT7.setCellStyle(stylefield);
		
		HSSFCell cellT8 = row.createCell((short) 8);
		cellT8.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT8.setCellValue("Ӧ�ս��");
		cellT8.setCellStyle(stylefield);
		
		HSSFCell cellT9 = row.createCell((short) 9);
		cellT9.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT9.setCellValue("ʵ�ս��");
		cellT9.setCellStyle(stylefield);
		
		HSSFCell cellT10 = row.createCell((short) 10);
		cellT10.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT10.setCellValue("ʣ����");
		cellT10.setCellStyle(stylefield);
		
		if(recNum>=65535){
            throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYSBF()","�����������������������࣬����Excel��������������������");  
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
	 *��֯Excel���ݣ���ӵ�ZIP(Ӧ��������)
	 *@param  request 
	 *@param  response 
	 *@param  blPrpJplanfee ��Ҫд��Excel������ 
	 *@param  fromPage �����ĸ�ҳ�棨ysbfJSP=Ӧ��XX��yfsxfJPS=Ӧ�������ѣ�yfpkJSP=Ӧ����
	 *@return ��ѯ���ص�״̬��0��ʾ����
	 *@throws Exception
	 */
	public int createExcelYFSXF(HttpServletRequest request,
			HttpServletResponse response,BLPrpJplanCommission blPrpJplanCommission,String fromPage) throws Exception,UserException {
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		 if(blPrpJplanCommission.getSize()==0){
	         throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYFSXF()","��������������������Ϊ0����˶Ի��������������󵼳���");  
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
		
		wb.setSheetName(0, "Ӧ��������", HSSFWorkbook.ENCODING_UTF_16);
		

		
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
		cellT0.setCellValue("ҵ���");
		cellT0.setCellStyle(stylefield);

		HSSFCell cellT1 = row.createCell((short) 1);
		cellT1.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT1.setCellValue("�������");
		cellT1.setCellStyle(stylefield);
		
		HSSFCell cellT2 = row.createCell((short) 2);
		cellT2.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT2.setCellValue("XX��");
		cellT2.setCellStyle(stylefield);
		
		HSSFCell cellT3 = row.createCell((short) 3);
		cellT3.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT3.setCellValue("��XXXXX����");
		cellT3.setCellStyle(stylefield);
		
		HSSFCell cellT4 = row.createCell((short) 4);
		cellT4.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT4.setCellValue("�ɷ�����");
		cellT4.setCellStyle(stylefield);
		
		HSSFCell cellT5 = row.createCell((short) 5);
		cellT5.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT5.setCellValue("XXXXX��");
		cellT5.setCellStyle(stylefield);
		
		HSSFCell cellT6 = row.createCell((short) 6);
		cellT6.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT6.setCellValue("�����˴���");
		cellT6.setCellStyle(stylefield);
		
		HSSFCell cellT7 = row.createCell((short) 7);
		cellT7.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT7.setCellValue("����������");
		cellT7.setCellStyle(stylefield);
		
		HSSFCell cellT8 = row.createCell((short) 8);
		cellT8.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT8.setCellValue("��XX��");
		cellT8.setCellStyle(stylefield);
		
		HSSFCell cellT9 = row.createCell((short) 9);
		cellT9.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT9.setCellValue("Ӧ���ұ�");
		cellT9.setCellStyle(stylefield);
		
		HSSFCell cellT10 = row.createCell((short) 10);
		cellT10.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT10.setCellValue("�����ѱ���(%)");
		cellT10.setCellStyle(stylefield);
		
		HSSFCell cellT11 = row.createCell((short) 11);
		cellT11.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT11.setCellValue("XX���");
		cellT11.setCellStyle(stylefield);
		
		HSSFCell cellT12 = row.createCell((short) 12);
		cellT12.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT12.setCellValue("Ӧ�����");
		cellT12.setCellStyle(stylefield);
		
		HSSFCell cellT13 = row.createCell((short) 13);
		cellT13.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT13.setCellValue("ʵ�����");
		cellT13.setCellStyle(stylefield);	
		
		HSSFCell cellT14 = row.createCell((short) 14);
		cellT14.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT14.setCellValue("ʣ����");
		cellT14.setCellStyle(stylefield);
		
	    if(recNum>=65535){
	        throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYFSXF()","�����������������������࣬����Excel��������������������");  
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
	 *��֯Excel���ݣ���ӵ�ZIP(Ӧ�����)
	 *@param  request 
	 *@param  response 
	 *@param  blPrpJplanfee ��Ҫд��Excel������ 
	 *@param  fromPage �����ĸ�ҳ�棨ysbfJSP=Ӧ��XX��yfsxfJPS=Ӧ�������ѣ�yfpkJSP=Ӧ����
	 *@return ��ѯ���ص�״̬��0��ʾ����
	 *@throws Exception
	 */
	public int createExcelYFPK(HttpServletRequest request,
			HttpServletResponse response,BLPrpJplanFee blPrpJplanfee,String fromPage) throws Exception,UserException {
	    if(blPrpJplanfee.getSize()==0){
            throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYFPK()","��������������������Ϊ0����˶Ի��������������󵼳���");  
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
		
		wb.setSheetName(0, "Ӧ�����", HSSFWorkbook.ENCODING_UTF_16);
		

		
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
		cellT0.setCellValue("ҵ���");
		cellT0.setCellStyle(stylefield);

		HSSFCell cellT1 = row.createCell((short) 1);
		cellT1.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT1.setCellValue("�������");
		cellT1.setCellStyle(stylefield);
		
		HSSFCell cellT2 = row.createCell((short) 2);
		cellT2.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT2.setCellValue("XX��");
		cellT2.setCellStyle(stylefield);
		
		HSSFCell cellT3 = row.createCell((short) 3);
		cellT3.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT3.setCellValue("��������");
		cellT3.setCellStyle(stylefield);
		
		HSSFCell cellT4 = row.createCell((short) 4);
		cellT4.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT4.setCellValue("�ɷ�����");
		cellT4.setCellStyle(stylefield);
		
		HSSFCell cellT5 = row.createCell((short) 5);
		cellT5.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT5.setCellValue("XXXXX��");
		cellT5.setCellStyle(stylefield);
		
		HSSFCell cellT6 = row.createCell((short) 6);
		cellT6.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT6.setCellValue("��XX��");
		cellT6.setCellStyle(stylefield);
		
		HSSFCell cellT7 = row.createCell((short) 7);
		cellT7.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT7.setCellValue("Ӧ�ձұ�");
		cellT7.setCellStyle(stylefield);
		
		HSSFCell cellT8 = row.createCell((short) 8);
		cellT8.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT8.setCellValue("Ӧ�ս��");
		cellT8.setCellStyle(stylefield);
		
		HSSFCell cellT9 = row.createCell((short) 9);
		cellT9.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT9.setCellValue("ʵ�ս��");
		cellT9.setCellStyle(stylefield);
		
		HSSFCell cellT10 = row.createCell((short) 10);
		cellT10.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT10.setCellValue("ʣ����");
		cellT10.setCellStyle(stylefield);
		
	    if(recNum>=65535){
	        throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYFPK()","�����������������������࣬����Excel��������������������");  
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
	 *��֯Excel���ݣ���ӵ�ZIP(Ԥ�跢Ʊ��Ϣ)
	 *@param  request 
	 *@param  response 
	 *@param  blPrpJplanfee ��Ҫд��Excel������ 
	 *@param  fromPage �����ĸ�ҳ�棨YJFP��
	 *@return ��ѯ���ص�״̬��0��ʾ����
	 *@throws Exception
	 */
	public int createExcelYJFP(HttpServletRequest request,
			HttpServletResponse response,BLPrpJplanFee blPrpJplanFee,String fromPage) throws Exception,UserException {
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		 if(blPrpJplanFee.getSize()==0){
	         throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYJFP()","��������������������Ϊ0����˶Ի��������������󵼳���");  
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
		
		wb.setSheetName(0, "Ԥ�跢Ʊ��Ϣ", HSSFWorkbook.ENCODING_UTF_16);
		

		
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
		cellT0.setCellValue("ҵ������");
		cellT0.setCellStyle(stylefield);

		HSSFCell cellT1 = row.createCell((short) 1);
		cellT1.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT1.setCellValue("ҵ���");
		cellT1.setCellStyle(stylefield);
		
		HSSFCell cellT2 = row.createCell((short) 2);
		cellT2.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT2.setCellValue("XX��");
		cellT2.setCellStyle(stylefield);
						
		HSSFCell cellT3 = row.createCell((short) 3);
		cellT3.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT3.setCellValue("�ɷ�����");
		cellT3.setCellStyle(stylefield);
		
		HSSFCell cellT4 = row.createCell((short) 4);
		cellT4.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT4.setCellValue("ҵ�����Ա");
		cellT4.setCellStyle(stylefield);
		
		HSSFCell cellT5 = row.createCell((short) 5);
		cellT5.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT5.setCellValue("ҵ���������");
		cellT5.setCellStyle(stylefield);
		
		HSSFCell cellT6 = row.createCell((short) 6);
		cellT6.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT6.setCellValue("��XX��");
		cellT6.setCellStyle(stylefield);
		
		HSSFCell cellT7 = row.createCell((short) 7);
		cellT7.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT7.setCellValue("Ӧ�ս��");
		cellT7.setCellStyle(stylefield);
		
		HSSFCell cellT8 = row.createCell((short) 8);
		cellT8.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT8.setCellValue("��XXXXX����");
		cellT8.setCellStyle(stylefield);
		
		HSSFCell cellT9 = row.createCell((short) 9);
		cellT9.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT9.setCellValue("��XXXXX����");
		cellT9.setCellStyle(stylefield);
		
		HSSFCell cellT10 = row.createCell((short) 10);
		cellT10.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT10.setCellValue("��XXXXX����");
		cellT10.setCellStyle(stylefield);
		
		
		HSSFCell cellT11 = row.createCell((short) 11);
		cellT11.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT11.setCellValue("Ԥ�ƹ黹����");
		cellT11.setCellStyle(stylefield);
		
		HSSFCell cellT12 = row.createCell((short) 12);
		cellT12.setEncoding(HSSFCell.ENCODING_UTF_16);
		cellT12.setCellValue("Ԥ�跢Ʊ״̬");
		cellT12.setCellStyle(stylefield);
		

		
	    if(recNum>=65535){
	        throw new UserException(-98,-1003,"CreateExcelOfZIP.createExcelYFJX()","�����������������������࣬����Excel��������������������");  
	    }
		
		for (int i = 0; i < recNum; i++) { 
                HSSFRow rowData = sheet.createRow((short) i + 1);
                
                
                HSSFCell cellData0 = rowData.createCell((short) 0);
                cellData0.setEncoding(HSSFCell.ENCODING_UTF_16);
                cellData0.setCellStyle(stylechara);
                if("P".equals(blPrpJplanFee.getArr(i).getCertiType())){
                	cellData0.setCellValue("XX");                	
                }else if("E".equals(blPrpJplanFee.getArr(i).getCertiType())){
                	cellData0.setCellValue("����");
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
                	strApproveStatusName="�����";
                }else if(strApproveStatus.equals("1")){
                	strApproveStatusName="���ͨ��";
                }else if(strApproveStatus.equals("2")){
                	strApproveStatusName="�Ѳ���";
                }else if(strApproveStatus.equals("3")){
                	strApproveStatusName="�Ѵ�ӡ";
                }else if(strApproveStatus.equals("4")){
                	strApproveStatusName="���ջ�";
                }else if(strApproveStatus.equals("5")){
                	strApproveStatusName="�Ѻ���";
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
	 * ����Excel
	 * @param wb Excel����
	 * @param fileName Ŀ���ļ���
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
	 * ������ļ�����ѹ���ļ�
	 * 
	 * @param originFiles  Դ�ļ�
	 * @param destZipFilename Ŀ���ļ���
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
	 * ����
	 * @param response httpServletResponse
	 * @param in inputStream
	 * @param fileName ��ʾ�ļ���
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
	 * ɾ��ָ��Ŀ¼���������е��κ��ļ���Ŀ¼
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
