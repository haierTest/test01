package com.sp.prpall.blsvr.jf;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.sp.sysframework.common.util.XMLUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.utility.string.ChgDate;
import com.sp.indiv.ci.interf.EbaoProxy;
import com.sp.prpall.schema.PrpJPayBookSchema;
import com.sp.prpall.schema.PrpJPayBookDetailSchema;
import com.sp.prpall.schema.PrpJPayBookRateItemSchema;
import com.sp.prpall.dbsvr.jf.DBPrpJPayBook;
import com.sp.prpall.dbsvr.jf.DBPrpJPayBookDetail;
import com.sp.prpall.blsvr.jf.BLPrpJPayBookDetail;
import com.sp.prpall.blsvr.jf.BLPrpJPayBookRateItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ����PrpJPayBook��BL�� <p> Copyright: Copyright (c) 2005 </p> <p>
 * @createdate 2007-12-27 </p>
 * @author BLGenerator
 * @version 1.0
 */
public class BLPrpJPayBook {
	private Vector schemas = new Vector();
	protected final Log logger = LogFactory.getLog(getClass());





	/**
	 * ���캯��
	 */
	public BLPrpJPayBook() {
	}

	/**
	 * ��ʼ����¼
	 * @param ��
	 * @return ��
	 * @throws Exception
	 */
	public void initArr() throws Exception {
		schemas = new Vector();
	}

	/**
	 * ����һ��PrpJPayBookSchema��¼
	 * @param PrpJPayBookSchema PrpJPayBookSchema
	 * @throws Exception
	 */
	public void setArr(PrpJPayBookSchema iPrpJPayBookSchema) throws Exception {
		try {
			schemas.add(iPrpJPayBookSchema);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �õ�һ��PrpJPayBookSchema��¼
	 * @param index �±�
	 * @return һ��PrpJPayBookSchema����
	 * @throws Exception
	 */
	public PrpJPayBookSchema getArr(int index) throws Exception {
		PrpJPayBookSchema prpJPayBookSchema = null;
		try {
			prpJPayBookSchema = (PrpJPayBookSchema) this.schemas.get(index);
		} catch (Exception e) {
			throw e;
		}
		return prpJPayBookSchema;
	}

	/**
	 * ɾ��һ��PrpJPayBookSchema��¼
	 * @param index �±�
	 * @throws Exception
	 */
	public void remove(int index) throws Exception {
		try {
			this.schemas.remove(index);
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * �õ�schemas��¼��
	 * @return schemas��¼��
	 * @throws Exception
	 */
	public int getSize() throws Exception {
		return this.schemas.size();
	}

	/**
	 * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
	 * @param iWherePart ��ѯ����(���������־�)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart) throws UserException, Exception {
		this.query(iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT").trim()));
	}

	/**
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * @param iWherePart ��ѯ����(���������־�)
	 * @param iLimitCount ��¼������(iLimitCount=0: ������)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(String iWherePart, int iLimitCount) throws UserException, Exception {
		String strSqlStatement = "";
		DBPrpJPayBook dbPrpJPayBook = new DBPrpJPayBook();
		if (iLimitCount > 0 && dbPrpJPayBook.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "DBPrpJPayBook.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJPayBook WHERE " + iWherePart;
			schemas = dbPrpJPayBook.findByConditions(strSqlStatement);
		}
	}

	/**
	 * ���ղ�ѯ�����õ�һ���¼�������������¼����schemas����
	 * @param dbpool ȫ�ֳ�
	 * @param iWherePart ��ѯ����(���������־�)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart) throws UserException, Exception {
		this.query(dbpool, iWherePart, Integer.parseInt(SysConst.getProperty("QUERY_LIMIT_COUNT")
				.trim()));
	}

	/**
	 * ���ղ�ѯ�����ͼ�¼�����Ƶõ�һ���¼�������������¼����schemas����
	 * @param dbpool ȫ�ֳ�
	 * @param iWherePart ��ѯ����(���������־�)
	 * @param iLimitCount ��¼������(iLimitCount=0: ������)
	 * @throws UserException
	 * @throws Exception
	 */
	public void query(DbPool dbpool, String iWherePart, int iLimitCount) throws UserException,
			Exception {
		String strSqlStatement = "";
		DBPrpJPayBook dbPrpJPayBook = new DBPrpJPayBook();
		if (iLimitCount > 0 && dbPrpJPayBook.getCount(iWherePart) > iLimitCount) {
			throw new UserException(-98, -1003, "BLPrpJPayBook.query");
		} else {
			initArr();
			strSqlStatement = " SELECT * FROM PrpJPayBook WHERE " + iWherePart;
			schemas = dbPrpJPayBook.findByConditions(dbpool, strSqlStatement);
		}
	}
	
    /**
     *��dbpool��save����
     *@param ��
     *@return ��
     */
    public void save(DbPool dbpool) throws Exception
    {
    	DBPrpJPayBook dbPrpJPayBook = new DBPrpJPayBook();
    	int i = 0;
    	for(i = 0; i< schemas.size(); i++)
    	{
    		dbPrpJPayBook.setSchema((PrpJPayBookSchema)schemas.get(i));
    		dbPrpJPayBook.insert(dbpool);
    	}
    }
    
    /**
     *����dbpool��XXXXX�淽��
     *@param ��
     *@return ��
     */
    public void save() throws Exception
    {
    	DbPool dbpool = new DbPool();
    	try {
    		dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
    		dbpool.beginTransaction();
    		save(dbpool);
    		dbpool.commitTransaction();
    	}
    	catch (Exception e)
    	{
    		dbpool.rollbackTransaction();
    	}
    	finally {
    		dbpool.close();
    	}
    }
	
    /**
     *����dbpool�ķ���,���ɽɿ���
     *@return 1���ɹ�,0��ʧ��
     */
    public String createBook(String declareNo,String OperatorCode) throws Exception
    {
    	DbPool dbpool = new DbPool();
    	String message = "";

        try {
            dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
	        dbpool.beginTransaction();
	        message = createBook(dbpool,declareNo,OperatorCode);
	        dbpool.commitTransaction();
	    }
        catch (Exception e)
        {
        	dbpool.rollbackTransaction();
        	throw e;
        }
        finally {
        	dbpool.close();
        }
        return message;
    }
    
    /**
     * @author zhanglingjian ���ɽɿ���
     * @param dbpool ���ݿ����ӳ�
     * @param declareNo �걨���
     * @param OperatorCode ����Ա����
     * @throws UserException
     * @throws Exception
     */
    public String createBook(DbPool dbpool,String declareNo,String OperatorCode) throws UserException,Exception
    {
    	ChgDate chgDate = new ChgDate();
        String currentDate = chgDate.getCurrentTime("yyyy-MM-dd");
    	String message = "";
    	String sendXML = "";
    	String receiveXML = "";
    	DBPrpJPayBook dbPrpJpayBook = new DBPrpJPayBook();
    	dbPrpJpayBook.getInfo(dbpool,declareNo);
    	dbPrpJpayBook.setOperatorCode(OperatorCode);
    	dbPrpJpayBook.setOperateDate(currentDate);
    	sendXML = buildSendXML(declareNo,dbPrpJpayBook.getCenterCode());
    	receiveXML = EbaoProxy.getInstance().request(sendXML, dbPrpJpayBook.getCenterCode());
    	message = readXML(dbpool,receiveXML,dbPrpJpayBook);
    	return message;
    }
    
	/**
	 * @desc ���ɷ��͵� XML�ļ�
	 * @param declareNo �걨���
	 * @return path ����XML�ļ���·��
	 * @throws Exception
	 */
	public String buildSendXML(String declareNo,String centerCode) throws Exception {
		Element root = new Element("PACKET");
        Document doc = new Document(root);
		String sendXML = "";
		
		String strCreateRequestType = "33";
		String strUser = AppConfig.get("sysconst.CI_INSURED_01_USER");
		String strPassword = AppConfig.get("sysconst.CI_INSURED_01_PASSWORD");
		String strComputeCode = AppConfig.get("sysconst.CI_INSURED_01_COMPUTER_CODE");
		String strPageLen = "";
		String strPageNum = "";
		
		Element head = new Element("HEAD");
		head.addContent(new Element("REQUEST_TYPE").setText(strCreateRequestType));
		head.addContent(new Element("USER").setText(strUser));
		head.addContent(new Element("PASSWORD").setText(strPassword));
		root.addContent(head);
		
		Element body = new Element("BODY");
		Element basePart = new Element("BASE_PART");
		basePart.addContent(new Element("DECLARE_NO").setText(declareNo));
		basePart.addContent(new Element("PAGE_LENGTH").setText(strPageLen));
		basePart.addContent(new Element("PAGE_NUMBER").setText(strPageNum));
		basePart.addContent(new Element("COMPUTER_CODE").setText(strComputeCode));
		body.addContent(basePart);
		root.addContent(body);
		
		root.setAttribute("type", "REQUEST");
		root.setAttribute("version", "1.0");
		
		Format format = Format.getCompactFormat();
		format.setEncoding("GBK");
		format.setIndent("   ");
		XMLOutputter Out = new XMLOutputter(format);
		sendXML = Out.outputString(doc);
		
		logger.info("�����ļ�:"+sendXML);
		
		return sendXML;
	}
	
    /**
     * @ ��ƽ̨��������XML�ķ���
     * @param sendXMLPath
     * @param centerCode
     * @throws UserException
     * @throws Exception
     */
	public String request(String sendXMLPath,String centerCode) throws UserException, Exception {
		StringBuffer bufferRequest = new StringBuffer();
		int len = 0;
		byte[] buffer = new byte[10240];
		
		
		String receiveXMLPath = "";
		String separator = java.io.File.separator;
		receiveXMLPath = SysConst.getProperty("CREATE_RECEIVE_XML_PATH");
		receiveXMLPath += separator + centerCode;
		
		File dirFile = new File(receiveXMLPath);
		if (!dirFile.exists()) {
			try {
				dirFile.mkdir();
			} catch (Exception e) {
				throw new Exception("���㵥λ��Ŀ¼�����ڣ�����ʧ�ܣ�");
			}
		}
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = df.parse(new ChgDate().getCurrentTime("yyyy-MM-dd HH:mm:ss"));
    	df.applyPattern("yyyyMMddHHmmss");
    	receiveXMLPath += separator + df.format(date).toString() + ".xml";
    	
		ByteArrayOutputStream byteArrayData = new ByteArrayOutputStream();
		File file = new File(sendXMLPath);
		BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
		while ((len = input.read(buffer)) != -1) {
			byteArrayData.write(buffer, 0, len);
		}
		input.close();
		
		
		bufferRequest.append(new String(byteArrayData.toByteArray())); 
		
		String strURL=AppConfig.get("sysconst.CI_INSURED_01_URL");
		URL url = new URL(strURL); 
	    HttpURLConnection urlConn = (HttpURLConnection)url.openConnection(); 
	    int intResponseCode = HttpURLConnection.HTTP_OK;
	    
	    urlConn.setRequestMethod("POST");
	    urlConn.setDoOutput(true);
	    urlConn.setDoInput(true);
	    
	    try
	    {
	    	urlConn.connect();
	    }
	    catch(IOException ioex)
	    {
	    	ioex.printStackTrace();
	    	throw new UserException(-2,0,"BLPrpJPayBook.request","����ͬҵƽ̨ʧ��,����ϵͳ����Ա��ϵ!");
	    }
	    
	    PrintStream output = new PrintStream(urlConn.getOutputStream());
	    output.print(byteArrayData.toString());
	    output.flush();
	    output.close();
	    
	    logger.info("����:"+byteArrayData.toString());
	    
	    
	    try
	    {
	    	intResponseCode = urlConn.getResponseCode();
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    	throw new UserException(-2,0,"BLPrpJPayBook.request","ͬҵƽ̨�����쳣,����ϵͳ����Ա��ϵ!");
	    }
	    if( intResponseCode!=HttpURLConnection.HTTP_OK )
	    {
	    	System.err.println( "request "+ urlConn.getURL() +" fail. response: code="+intResponseCode +
	                          ", message="+urlConn.getResponseMessage() );
	    	throw new UserException(-2,0,"BLPrpJPayBook.request","ͬҵƽ̨�����쳣,����ϵͳ����Ա��ϵ!");
	    }
	    
	    input = new BufferedInputStream(urlConn.getInputStream());
	    FileOutputStream fos=new FileOutputStream(receiveXMLPath);
	    int count = 0;
	    while( (count=input.read(buffer))!=-1 )
	    {
	      fos.write(buffer,0,count);
	    }
	    fos.close();
	    input.close();

	    
	    urlConn.disconnect();
	    
	    return receiveXMLPath;
	}
	
    /**
     * @ ��ȡƽ̨���ص����XML��Ϣ
     * @param dbpool
     * @param receiveXMLPath
     * @param strDate
     * @param number
     * @throws Exception
     */
	public String readXML(DbPool dbpool,String receiveXML,DBPrpJPayBook dbPrpJPayBook) 
	 throws Exception {
		
		logger.info("------receiveXML--------"+receiveXML);
		
		String message = "";
		SAXBuilder sb = new SAXBuilder();
		InputStream in = new ByteArrayInputStream(receiveXML.getBytes());
		InputStreamReader isr = new InputStreamReader(in,"gbk");
		Document doc = sb.build(isr);
		
		BLPrpJPayBookDetail blPrpJPayBookDetail = new BLPrpJPayBookDetail();
		BLPrpJPayBookRateItem blPrpJPayBookRateItem = new BLPrpJPayBookRateItem();
		
		Element root = doc.getRootElement(); 
		String responseCode = root.getChild("HEAD").getChildText("RESPONSE_CODE");
		if(responseCode.equals("0") || responseCode.equals("E"))
		{
			dbPrpJPayBook.setErrorMessage(root.getChild("HEAD").getChildText("ERROR_MESSAGE"));
			dbPrpJPayBook.setBookStatus("3");
			dbPrpJPayBook.update(dbpool);
			message = responseCode + "���ɽɿ���ʧ�ܣ�" + root.getChild("HEAD").getChildText("ERROR_MESSAGE");
		}else
		{
			dbPrpJPayBook.setBookStatus("1");
			dbPrpJPayBook.update(dbpool);
			
			List payBookList = root.getChild("BODY").getChildren("PAY_BOOK_ARRAY_LIST");
			for(int i = 0;i < payBookList.size();i++)
			{
				List payBookArray = ((Element)payBookList.get(i)).getChildren("PAY_BOOK_ARRAY");
				for(int j = 0;j < payBookArray.size();j++)
				{
					PrpJPayBookDetailSchema prpJPayBookDetailSchema = new PrpJPayBookDetailSchema();
					prpJPayBookDetailSchema.setDeclareNo(dbPrpJPayBook.getDeclareNo());
					prpJPayBookDetailSchema.setSerialNo(""+(j+1));
					prpJPayBookDetailSchema.setCenterCode(dbPrpJPayBook.getCenterCode());
					prpJPayBookDetailSchema.setPaymentCode(((Element)payBookArray.get(j)).getChildText("PAYMENT_CODE"));
					prpJPayBookDetailSchema.setPaymentName(((Element)payBookArray.get(j)).getChildText("PAYMENT_NAME"));
					prpJPayBookDetailSchema.setPaymentBank(((Element)payBookArray.get(j)).getChildText("PAYMENT_BANK"));
					prpJPayBookDetailSchema.setPaymentAccount(((Element)payBookArray.get(j)).getChildText("PAYMENT_ACCOUNT"));
					prpJPayBookDetailSchema.setDepartment(((Element)payBookArray.get(j)).getChildText("DEPARTMENT"));
					prpJPayBookDetailSchema.setExchequer(((Element)payBookArray.get(j)).getChildText("EXCHEQUER"));
					prpJPayBookDetailSchema.setTotalTax(((Element)payBookArray.get(j)).getChildText("TOTAL_TAX"));
					prpJPayBookDetailSchema.setRemark(((Element)payBookArray.get(j)).getChildText("REMARK"));
					blPrpJPayBookDetail.setArr(prpJPayBookDetailSchema);
					
					List ratePayingItemArray = ((Element)payBookArray.get(i)).getChild("RATEPAYING_ITEM_ARRAY_LIST").getChildren("RATEPAYING_ITEM_ARRAY");
					for(int m = 0;m < ratePayingItemArray.size();m++)
					{
						PrpJPayBookRateItemSchema prpJPayBookRateItemSchema = new PrpJPayBookRateItemSchema();
						prpJPayBookRateItemSchema.setDeclareNo(dbPrpJPayBook.getDeclareNo());
						prpJPayBookRateItemSchema.setSerialNo(""+(m+1));
						prpJPayBookRateItemSchema.setCenterCode(dbPrpJPayBook.getCenterCode());
						prpJPayBookRateItemSchema.setSubjectName(((Element)ratePayingItemArray.get(m)).getChildText("SUBJECT_NAME"));
						prpJPayBookRateItemSchema.setTaxationNumber(((Element)ratePayingItemArray.get(m)).getChildText("TAXATION_NUMBER"));
						prpJPayBookRateItemSchema.setRecordTaxFee(((Element)ratePayingItemArray.get(m)).getChildText("RECORD_TAX_FEE"));
						prpJPayBookRateItemSchema.setActualTaxFee(((Element)ratePayingItemArray.get(m)).getChildText("ACTUAL_TAX_FEE"));
						blPrpJPayBookRateItem.setArr(prpJPayBookRateItemSchema);
					}
				}
			}
			blPrpJPayBookDetail.save(dbpool);
			blPrpJPayBookRateItem.save(dbpool);
			message = responseCode + "���ɽɿ���ɹ�";
		}
		
		return message;
	}
	
    /**
     * ������
     */
	public static void main(String args[]) throws UserException, Exception {
		
	}
}
