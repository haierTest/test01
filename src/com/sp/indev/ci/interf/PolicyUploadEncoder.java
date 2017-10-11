package com.sp.indiv.ci.interf;

import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPrpCcarDriver;
import com.sp.prpall.blsvr.cb.BLPrpCitemKind;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.schema.PrpCcarDriverSchema;
import com.sp.prpall.schema.PrpCitemCarSchema;
import com.sp.prpall.schema.PrpCitemKindSchema;
import com.sp.prpall.schema.PrpCmainSchema;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;

public class PolicyUploadEncoder extends PlatFormEncoder{

	public String encode(DbPool dbPool,BLPolicy blPolicy) throws Exception {
		StringBuffer buf = new StringBuffer(4000);
		addXMLHead(buf);
		addPacket(buf, blPolicy);
		return buf.toString();
	}
	/**
	 * 添加PACKET节
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addPacket(StringBuffer buf, BLPolicy blPolicy)
			throws Exception {
		String strComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();
		buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
		addHead(buf, "23", strComCode);
		addBody(buf, blPolicy);
		buf.append("</PACKET>");

	}
	/**
	 * 添加BODY节
	 * @param buf StringBuffer
	 * @throws Exception
	 */
	protected void addBody(StringBuffer buf, BLPolicy blPolicy)
			throws Exception {
		buf.append("<BODY>");
		addBasePart(buf, blPolicy);
		addOwnerList(buf, blPolicy);
		addPhList(buf, blPolicy);
		addInsuredList(buf, blPolicy);
		addDriverList(buf, blPolicy);
		addCoverageList(buf, blPolicy);
		buf.append("</BODY>");
	}
	/**
	 * 添加BASE_PART节
	 * 
	 * @param buf
	 *            StringBuffer
	 * @throws Exception
	 */
	protected void addBasePart(StringBuffer buf, BLPolicy blPolicy)
			throws Exception {
				
		String strCarMark = ""; 
		String strVehicleType = ""; 
		String strVehicleCategory = ""; 
		String strUseType = ""; 
		String strEngineNo = ""; 
		String strRackNo = ""; 
		String strUseAges = ""; 
		String strMileages = ""; 
		String strNewVehicleFlag = "0"; 
		String strEcdemicVehicleFlag = "0"; 
		String strMadeFactory = ""; 
		String strVehicleBrand = ""; 
		String strVehicleModel = ""; 
		String policyNo = ""; 
		String billDate = ""; 
		String tpMark = ""; 
		String Assumption = ""; 
		String agent = ""; 
		String operator = ""; 
		String salesChannel = ""; 
		String salesChannelCode = ""; 
		String currency = ""; 
		String actualPremium = ""; 
		String affirmant = ""; 
		String changeReasonCode = ""; 
		String changeReasonDesc = ""; 
		
		String strPurchasePrice = "";
		String strActualValue = "";		
		String strRunMiles = "";
		String strSumDiscount = ""; 
		String strRiskCode = "";
		String strComCode = "";	
		changeReasonCode = "9";
		
		
		PrpCmainSchema prpCmainSchema = blPolicy.getBLPrpCmain().getArr(0);
		PrpCitemCarSchema prpCitemCarSchema = blPolicy.getBLPrpCitemCar().getArr(0);
		policyNo = prpCmainSchema.getPolicyNo();
		billDate = prpCmainSchema.getSignDate();
		billDate = StringUtils.replace(billDate, "-", "");
		operator = prpCmainSchema.getOperatorCode();
		salesChannel = encodeSalesChannel(prpCmainSchema.getBusinessNature());
		salesChannelCode = prpCmainSchema.getComCode();
		actualPremium = prpCmainSchema.getSumPremium();
		affirmant = prpCmainSchema.getHandlerCode();
		strCarMark = prpCitemCarSchema.getLicenseNo();
		strUseType = encoderUseTypeNew(blPolicy);
		strVehicleCategory = encoderVehicleCategoryNew(blPolicy);
		strEngineNo = prpCitemCarSchema.getEngineNo();
		strUseAges = prpCitemCarSchema.getUseYears();
		strMileages = "" + ((int) Double.parseDouble(prpCitemCarSchema.getRunMiles()));
		strVehicleBrand = prpCitemCarSchema.getBrandName();
		strVehicleModel = prpCitemCarSchema.getModelCode();
		strVehicleType = prpCitemCarSchema.getLicenseKindCode();
		strRackNo = prpCitemCarSchema.getFrameNo();
		
		strPurchasePrice = prpCitemCarSchema.getPurchasePrice();
		strActualValue = prpCitemCarSchema.getActualValue();
		strRunMiles = prpCitemCarSchema.getRunMiles();
		strSumDiscount = prpCmainSchema.getSumDiscount();
		strRiskCode = prpCmainSchema.getRiskCode();
		strComCode = prpCmainSchema.getComCode();
		
		if (strCarMark != null && !strCarMark.equals("新车") && strCarMark.charAt(0) != '沪') {
			strEcdemicVehicleFlag = "1";
		}
		
		
		if (strCarMark != null && SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+strCarMark+",") > -1) {
		
			strNewVehicleFlag = "1";
			strCarMark = "";
	    }
		buf.append("<BASE_PART>");
		addNode(buf, "CONFIRM_SEQUENCE_NO", "");
		addNode(buf, "CAR_MARK", strCarMark);
		addNode(buf, "VEHICLE_TYPE", strVehicleType);
		addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);
		addNode(buf, "USE_TYPE", strUseType);
		addNode(buf, "ENGINE_NO", strEngineNo);
		addNode(buf, "RACK_NO", strRackNo);
		addNode(buf, "USE_AGES", strUseAges);
		addNode(buf, "MILEAGES", strMileages);
		addNode(buf, "NEW_VEHICLE_FLAG", strNewVehicleFlag);
		addNode(buf, "ECDEMIC_VEHICLE_FLAG", strEcdemicVehicleFlag);
		addNode(buf, "MADE_FACTORY", strMadeFactory);
		addNode(buf, "VEHICLE_BRAND", strVehicleBrand);
		addNode(buf, "VEHICLE_MODEL", strVehicleModel);
		addNode(buf, "POLICY_NO", policyNo);
		addNode(buf, "TP_MARK", "");
		addNode(buf, "SALES_CHANNEL", salesChannel);
		addNode(buf, "SALES_CHANNEL_CODE", salesChannelCode);
		addNode(buf, "BILL_DATE", billDate);
		addNode(buf, "ASSUMPTION", "");
		addNode(buf, "AGENT", "");
		addNode(buf, "OPERATOR", operator);
		addNode(buf, "CURRENCY", "");
		addNode(buf, "ACTUAL_PREMIUM", actualPremium);
		addNode(buf, "AFFIRMANT", affirmant);
		
		addNode(buf, "CHANGE_REASON_CODE",changeReasonCode);
		addNode(buf, "CHANGE_RENSON_DESC", changeReasonDesc);
		if(new UtiPower().checkCIInsureBJ(strRiskCode, strComCode)&&!"0507".equals(strRiskCode))
		{
			addNode(buf, "POLICY_VEHICLE_PRICE", strPurchasePrice);
			addNode(buf, "BUSI_PREFERENTIAL_PREMIUM", strSumDiscount);
			addNode(buf, "AVERAGE_MILE", strRunMiles);
			addNode(buf, "ACTUAL_VEHICLE_PRICE", strActualValue);
		}
		

		buf.append("</BASE_PART>");
	}
	/**
     * 添加DRIVER_LIST节
     * @param buf StringBuffer
     * @throws Exception
     */
    protected void addDriverList(StringBuffer buf, BLPolicy blPolicy) throws Exception {
        buf.append("<DRIVER_LIST>"); 
        addDriver(buf, blPolicy); 
        buf.append("</DRIVER_LIST>");
    }

    /**
     * 添加DRIVER节
     * @param buf StringBuffer
     * @throws Exception
     */
    protected void addDriver(StringBuffer buf, BLPolicy blPolicy) throws Exception 
    {
    	BLPrpCcarDriver blPrpCcarDriver = blPolicy.getBLPrpCcarDriver();
        String strLicenseNo = ""; 
        String strCertiType = ""; 
        String strIsMaster = ""; 
        String strArea = ""; 
        String strGender = ""; 
        String strDriverPeriod = ""; 
        String strAge = ""; 
        String strDriverType = ""; 
        if(blPrpCcarDriver.getSize()>0)
        {
        	PrpCcarDriverSchema prpCcarDriverSchema = blPrpCcarDriver.getArr(0);
        	strLicenseNo = prpCcarDriverSchema.getIdentifyNumber();
        	strCertiType = "01";
        	strGender = prpCcarDriverSchema.getSex();       	
        	strDriverPeriod = prpCcarDriverSchema.getDrivingYears();
        	strAge = prpCcarDriverSchema.getAge();
        	strDriverType = prpCcarDriverSchema.getDrivingCarType();
        }
        buf.append("<DRIVER>");
        addNode(buf, "LICENSE_NO", strLicenseNo);
        addNode(buf, "CERTI_TYPE", strCertiType);
        addNode(buf, "IS_MASTER", strIsMaster);
        addNode(buf, "AREA", strArea);
        addNode(buf, "GENDER", strGender);
        addNode(buf, "DRIVER_PERIOD", strDriverPeriod);
        addNode(buf, "AGE", strAge);
        addNode(buf, "DRIVER_TYPE", strDriverType);
        buf.append("</DRIVER>");
    }
    /**
	 * 
	 * @param buf
	 * @param blPolicy
	 * @throws Exception
	 */
	protected void addCoverageList(StringBuffer buf, BLPolicy blPolicy)
	throws Exception {
		
		boolean isInsureBJ = new UtiPower().checkCIInsureBJ(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), blPolicy.getBLPrpCmain().getArr(0).getComCode());
		buf.append("<COVERAGE_LIST>");
		BLPrpCitemKind blPrpCitemKind = blPolicy.getBLPrpCitemKind();
		for(int i=0;i<blPrpCitemKind.getSize();i++)
		{
			PrpCitemKindSchema prpCitemKindSchema = blPrpCitemKind.getArr(i);
			if(isInsureBJ){
				addCoverageDataBJ(buf,prpCitemKindSchema);
			}else{
				addCoverageData(buf,prpCitemKindSchema);
			}
			
		}
		
		buf.append("</COVERAGE_LIST>");
	}
	/**
	 * 
	 * @param buf
	 * @param blPolicy
	 * @throws Exception
	 */
	protected void addCoverageData(StringBuffer buf, PrpCitemKindSchema prpCitemKindSchema)
	throws Exception {	
		String coverageType = "";
		String coverageCode = "";
		String limitAmount = "";
		String strRiskCode = prpCitemKindSchema.getRiskCode();
		String strKindCode = prpCitemKindSchema.getKindCode();
		limitAmount        = prpCitemKindSchema.getAmount();
		coverageType = encodeCoverageType(strKindCode);
		coverageCode = encodeCoverageCode(strKindCode,strRiskCode);
		buf.append("<COVERAGE>");			
		addNode(buf, "COVERAGE_TYPE", coverageType);
		addNode(buf, "COVERAGE_CODE", coverageCode);
		addNode(buf, "COM_COVERAGE_CODE", strKindCode);
		addNode(buf, "LIMIT_AMOUNT", limitAmount);
		addNode(buf, "START_DATE", correctDate(prpCitemKindSchema.getStartDate()));
		addNode(buf, "END_DATE", correctDate(prpCitemKindSchema.getEndDate()));
		
		addNode(buf, "STANDARD_PREMIUM", prpCitemKindSchema.getPremium());
		
		
		addNode(buf, "BASED_PREMIUM", prpCitemKindSchema.getBenchMarkPremium());
		
		buf.append("</COVERAGE>");		
	}
	
	/**
	 * add by luogang 20091231 begin reason:XXXXX商业XXXXXXXXXX别列表
	 * @param buf
	 * @param prpCitemKindSchema
	 * @throws Exception
	 */
	protected void addCoverageDataBJ(StringBuffer buf, PrpCitemKindSchema prpCitemKindSchema)
	throws Exception {	
		String coverageType = "";
		String coverageCode = "";
		String limitAmount = "";
		String strRiskCode = prpCitemKindSchema.getRiskCode();
		String strKindCode = prpCitemKindSchema.getKindCode();
		limitAmount        = prpCitemKindSchema.getAmount();
		coverageType = encodeCoverageType(strKindCode);
		coverageCode = encodeCoverageCodeBJ(strKindCode,strRiskCode);
		buf.append("<COVERAGE>");			
		addNode(buf, "COVERAGE_TYPE", coverageType);
		addNode(buf, "COVERAGE_CODE", coverageCode);
		addNode(buf, "COM_COVERAGE_CODE", strKindCode);
		addNode(buf, "LIMIT_AMOUNT", limitAmount);
		addNode(buf, "START_DATE", correctDate(prpCitemKindSchema.getStartDate()));
		addNode(buf, "END_DATE", correctDate(prpCitemKindSchema.getEndDate()));
		
		addNode(buf, "STANDARD_PREMIUM", prpCitemKindSchema.getPremium());
		
		
		addNode(buf, "BASED_PREMIUM", prpCitemKindSchema.getBenchMarkPremium());
		
		buf.append("</COVERAGE>");		
	}
	
	
	public static void addNode(StringBuffer buffer, String name, String value) {
        value = StringUtils.replace(value, "<", "&lt;");
        value = StringUtils.replace(value, ">", "&gt;");
        value = StringUtils.replace(value, "&", "&amp;");
        value = StringUtils.replace(value, "'", "&apos;");
        value = StringUtils.replace(value, "\"", "&quot;");

        buffer.append("<");
        buffer.append(name);
        buffer.append(">");
        buffer.append(value);
        buffer.append("</");
        buffer.append(name);
        buffer.append(">");
        buffer.append("\r\n");
    }
	
	
    private String encoderUseTypeNew(BLPolicy blPolicy) throws Exception {
        String strUseType = "";
        String intUseType = "";
        intUseType = blPolicy.getBLPrpCitemCar().getArr(0).getUseNatureCode();
        String strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
        
        if(strCarKindCode.equals("A0")||
           strCarKindCode.equals("A1")||
           strCarKindCode.equals("A2")||
           strCarKindCode.equals("A3")||
           strCarKindCode.equals("A4"))
        {
            if(intUseType.trim().equals("8B"))          
            {
                strUseType = "220";
            }
            else if(intUseType.trim().equals("8E")||intUseType.trim().equals("8F"))
            {
                strUseType = "230";
            }
            else if(intUseType.trim().equals("8A"))     
            {
                strUseType = "210";
            }
            else if(intUseType.trim().equals("9B"))     
            {
                strUseType = "102";
            }
            else if(intUseType.trim().equals("9C"))     
            {
                strUseType = "103";
            }
            else if(intUseType.trim().equals("9F")||intUseType.trim().equals("9G"))     
            {
                strUseType = "101";
            }
            else if(intUseType.trim().equals("86"))     
            {
                strUseType = "100";
            }
            else if(intUseType.trim().equals("87"))     
            {
                strUseType = "101";
            }
            else if(intUseType.trim().equals("9D"))     
            {
                
                
            }
            else if(intUseType.trim().equals("9E"))     
            {
                
                if(PubTools.compareDate(blPolicy.getBLPrpCmain().getArr(0).getOperateDate().trim(), "2007-04-13") < 0)
                {
                    strUseType = "102";                 
                }
                else
                {
                    strUseType = "103";                 
                }
                
            }
        }
        
        else if(strCarKindCode.equals("H0")||
                 strCarKindCode.equals("H3")||
                 strCarKindCode.equals("H4")||
                 strCarKindCode.equals("H5")||
                 strCarKindCode.equals("H6")||
                 strCarKindCode.equals("H7")||
                 strCarKindCode.equals("H8")||
                 strCarKindCode.equals("G0"))
        {
            if(intUseType.trim().equals("86") ||    
               intUseType.trim().equals("87") ||    
               intUseType.trim().equals("9D") ||    
               intUseType.trim().equals("9E") ||    
               intUseType.trim().equals("9F") ||    
               intUseType.trim().equals("9G") ||    
               intUseType.trim().equals("9B") ||    
               intUseType.trim().equals("9C")       
               )
            {
                strUseType = "100";     
            }
            else if(
                    intUseType.trim().equals("8B") ||       
                    intUseType.trim().equals("8E") ||       
                    intUseType.trim().equals("8F") ||       
                    intUseType.trim().equals("8D")          
                    )
            {
                strUseType = "200";     
            }

        }
        
        
        else
        {
            strUseType = "000";
        }

        return strUseType;
    }
    
    /**
	 * 纠正日期格式
	 * 
	 * @param dateString
	 *            8个字符的日期
	 * @return YYYYMMDD形式的日期
	 */
	public static String correctDate(String dateString) {
		String result = StringUtils.replace(dateString, "-", "");
		return result;
	}
	
	private String encodeSalesChannel(String strBusinessNature) throws Exception{
   	 String strSalesChannel = ""; 
     
     
   	 
   	 if("0".equals(strBusinessNature)){
   		 strSalesChannel = "10";
   	 }else if("1".equals(strBusinessNature) || "I".equals(strBusinessNature)){
   		 strSalesChannel = "22";
   	 }else if("8".equals(strBusinessNature)){
   		 strSalesChannel = "12";
   	 }else if("A".equals(strBusinessNature) || "B".equals(strBusinessNature) || "3".equals(strBusinessNature)){
   		 strSalesChannel = "23";
   	 }else if("2".equals(strBusinessNature)){
   		 strSalesChannel = "25";
   	 }else if("4".equals(strBusinessNature) || "5".equals(strBusinessNature)){
   		 strSalesChannel = "26";
   	 }else if("6".equals(strBusinessNature)||"J".equals(strBusinessNature)){
   		 strSalesChannel = "11";
   	 }else if("9".equals(strBusinessNature)){
   		 strSalesChannel = "24";
   	 }else if("G".equals(strBusinessNature)){
		 strSalesChannel = "21";
   	 }else if("H".equals(strBusinessNature)){
		 strSalesChannel = "21";
   	 }else if("F".equals(strBusinessNature)){
		 strSalesChannel = "24";
   	 }else{
   		 strSalesChannel = "99";                
   	 }
   	 
   	 
   	 
   	 return strSalesChannel;
    }
	
	private String encoderVehicleCategoryNew(BLPolicy blPolicy) 
	throws Exception 
	{
	    String strVehicleCategory = "";
	    String strCarKindCode = "";
	    double exhaustScale = 0;
	    double toncount = 0;
	    int seatcount = 0;
	    String intUseType = "";
	    intUseType = blPolicy.getBLPrpCitemCar().getArr(0).getUseNatureCode();
	    seatcount = new Integer(blPolicy.getBLPrpCitemCar().getArr(0).getSeatCount()).intValue();
	    toncount = Double.parseDouble(blPolicy.getBLPrpCitemCar().getArr(0).getTonCount());
	    exhaustScale = Double.parseDouble(blPolicy.getBLPrpCitemCar().getArr(0).getExhaustScale());
	    strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
	    
		    String strComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();
		    
	    
	    if (strCarKindCode.equals("A0")||
	        strCarKindCode.equals("A1")||
	        strCarKindCode.equals("A2")||
	        strCarKindCode.equals("A3")||
	        strCarKindCode.equals("A4")) {
	        if (seatcount >= 0 && seatcount < 6) {
	            strVehicleCategory = "11";
	        } else if (seatcount >= 6 && seatcount < 10) {
	            strVehicleCategory = "12";
	        } else if (seatcount >= 10 && seatcount < 20) {
	            strVehicleCategory = "13";
	        } else if (seatcount >= 20 && seatcount < 36) {
	            strVehicleCategory = "14";
	        } else if (seatcount >= 36) {
	            strVehicleCategory = "15";
	        }
	    }
	    
	    
	    
	    else if (strCarKindCode.equals("H0")||
	              strCarKindCode.equals("H3")||
	              strCarKindCode.equals("H4")||
	              strCarKindCode.equals("H5")||
	              strCarKindCode.equals("H6")||
	              strCarKindCode.equals("H7")||
	              strCarKindCode.equals("H8"))	
	    {
	        if (toncount >= 0 && toncount < 2) {
	            strVehicleCategory = "21";
	        } else if (toncount >= 2 && toncount < 5) {
	            strVehicleCategory = "22";
	        } else if (toncount >= 5 && toncount < 10) {
	            strVehicleCategory = "23";
	        } else if (toncount >= 10) {
	            strVehicleCategory = "24";
	        }
	
	    }
	    
	    else if (strCarKindCode.equals("H1")||
	              strCarKindCode.equals("H2"))
	    {
	        
			      if(SysConfig.getProperty("CI_SINOSOFT_0806").trim().indexOf(
					    strComCode.substring(0, 2).trim()) > -1){
				      if(strCarKindCode.equals("H1")){	
					      strVehicleCategory = "93";
				      }else if(strCarKindCode.equals("H2")){
					      strVehicleCategory = "94";
				      }
			      }else{
				      strVehicleCategory = "92";
			      }
			      
	    }    
	    
	    else if (strCarKindCode.equals("G0"))		
	    {
	    	if (toncount >= 0 && toncount < 2) {
	            strVehicleCategory = "25";
	        } else if (toncount >= 2 && toncount < 5) {
	            strVehicleCategory = "26";
	        } else if (toncount >= 5 && toncount < 10) {
	            strVehicleCategory = "27";
	        } else if (toncount >= 10) {
	            strVehicleCategory = "28";
	        }
	    }
	    
	    else if (strCarKindCode.equals("TP") ||
	    		  strCarKindCode.equals("TQ") ||
	    		  strCarKindCode.equals("TR")){
	        strVehicleCategory = "30";
	    }
	    
	    
	    else if ( strCarKindCode.equals("T10")||
	               strCarKindCode.equals("T11")||
	               strCarKindCode.equals("T1") ||
	    		   strCarKindCode.equals("T2") ||
	    		   strCarKindCode.equals("T7") ||
	               strCarKindCode.equals("TD") ||
	               strCarKindCode.equals("TE") ||
	               strCarKindCode.equals("TF") ||
	               strCarKindCode.equals("TG") ||
	               strCarKindCode.equals("TH") ||
	               strCarKindCode.equals("TK") ||
	               strCarKindCode.equals("TS") ||
	               strCarKindCode.equals("T12")||
	               strCarKindCode.equals("TL") ||
	               strCarKindCode.equals("TN")) {
	        strVehicleCategory = "40";
	    }
	    
	    
	    else if ( strCarKindCode.equals("T8")||
	    		   strCarKindCode.equals("T6")||
	    		   strCarKindCode.equals("T4")||
	    		   strCarKindCode.equals("T5")||
	    		   strCarKindCode.equals("T3")||
	    		   strCarKindCode.equals("TC")||
	    		   strCarKindCode.equals("TO")||
	    		   strCarKindCode.equals("TI")||
	    		   strCarKindCode.equals("TV")||
	    		   strCarKindCode.equals("TW")||
	    		   strCarKindCode.equals("TX")||
	               strCarKindCode.equals("TY")) {
	        strVehicleCategory = "50";
	        
	        UtiPower utiPower = new UtiPower();
	        if(utiPower.checkStartUp0805(blPolicy.getBLPrpCmain().getArr(0).getOperateDate())&&strCarKindCode.equals("T3")){
	            strVehicleCategory = "40";
	        }
	        
	    } 
	    else if (strCarKindCode.equals("TT")) {
	        
	        strVehicleCategory = "60";
	    } 
	    
	    
	    else if ( strCarKindCode.equals("M1")||
	               strCarKindCode.equals("M5")) {
	        
	        if (exhaustScale >= 0 && exhaustScale <= 0.05) {
	            strVehicleCategory = "71";
	        } else if (exhaustScale > 0.05 && exhaustScale <= 0.25) {
	            strVehicleCategory = "72";
	        } else if (exhaustScale > 0.25) {
	            strVehicleCategory = "73";
	        }
	    }
	    
	    else if(strCarKindCode.equals("M6"))
	    {
	        strVehicleCategory = "73";
	    }
	    
	    
	    else if (strCarKindCode.equals("J0")&&intUseType.trim().equals("7A")) {   
	        if (exhaustScale >= 0 && exhaustScale <= 14.7) {
	            strVehicleCategory = "81";
	        } else if (exhaustScale > 14.7) {
	            strVehicleCategory = "82";
	        }
	    }
	    else if (strCarKindCode.equals("J0")&&intUseType.trim().equals("7B")) {
	        
	        if (exhaustScale >= 0 && exhaustScale <= 14.7) {
	            strVehicleCategory = "91";
	        } else if (exhaustScale > 14.7) {
	            strVehicleCategory = "92";
	        }
	    }
	    else if (strCarKindCode.equals("G1"))
	    {
	        strVehicleCategory = "31";
	    }
	    
	    else if(strCarKindCode.equals("G2"))
	    {
	        strVehicleCategory = "41";
	        
	        
	    }else if(strCarKindCode.equals("G3"))
	    {
	        strVehicleCategory = "51";
	    }
	    
	    return strVehicleCategory;
	}
	
	private String encodeCoverageType(String strKindCode) throws Exception{
    	String strCoverageType ="";
    	if("BZ".equals(strKindCode)){
     		 strCoverageType = "1";        
     	 }else if("B".equals(strKindCode)){
     		 strCoverageType = "2";
     	 }else if("A".equals(strKindCode) || "D3".equals(strKindCode) || "D4".equals(strKindCode) || "G1".equals(strKindCode)){
     		 strCoverageType = "3";
     	 }else{
     		 strCoverageType = "9";
     	 }
    	return strCoverageType;
    }
	
	private String encodeCoverageCode(String strKindCode, String strRiskCode) throws Exception{
    	String strCoverageCode = "";
    	if("0509".equals(strRiskCode)){
    		if("BZ".equals(strKindCode)){
        		strCoverageCode = "A02";
        	 }else if("A".equals(strKindCode)){
        		strCoverageCode = "C11";
        	 }else if("B".equals(strKindCode)){
        		strCoverageCode = "B11";
        	 }else if("G1".equals(strKindCode)){
        		strCoverageCode = "D11";
        	 }else if("D3".equals(strKindCode) || "D4".equals(strKindCode)){
        		strCoverageCode = "E11";
        	 }else{
        		strCoverageCode = "F11";
        	 }
    	}else{
    	     if("BZ".equals(strKindCode)){
    		    strCoverageCode = "A02";
    	     }else if("A".equals(strKindCode)){
    		    strCoverageCode = "C01";
    	     }else if("B".equals(strKindCode)){
    		    strCoverageCode = "B01";
    	     }else if("G1".equals(strKindCode)){
    		    strCoverageCode = "D01";
    	     }else if("D3".equals(strKindCode) || "D4".equals(strKindCode)){
    		    strCoverageCode = "E01";
    	     }else{
    		    strCoverageCode = "F01";
    	     }
    	}
    	return strCoverageCode;
    }
    
    private String encodeCoverageCodeBJ(String strKindCode, String strRiskCode) throws Exception{
    	String strCoverageCode = "";
    	if("0509".equals(strRiskCode)){
    		if("BZ".equals(strKindCode)){
        		strCoverageCode = "CLIBJ2";
        	 }else if("A".equals(strKindCode)){
        		strCoverageCode = "C11";
        	 }else if("B".equals(strKindCode)){
        		strCoverageCode = "B11";
        	 }else if("G1".equals(strKindCode)){
        		strCoverageCode = "D11";
        	 }else if("D3".equals(strKindCode) || "D4".equals(strKindCode)){
        		strCoverageCode = "E11";
        	 
        	 }else if("Z".equals(strKindCode)){
    		    strCoverageCode = "G11";
    		 
        	 }else{
        		strCoverageCode = "F11";
        	 }
    	}else{
    	     if("BZ".equals(strKindCode)){
    		    strCoverageCode = "CLIBJ2";
    	     }else if("A".equals(strKindCode)){
    		    strCoverageCode = "C01";
    	     }else if("B".equals(strKindCode)){
    		    strCoverageCode = "B01";
    		 
    	     }else if("G1".equals(strKindCode)|| "G".equals(strKindCode)){
    		    strCoverageCode = "D01";
    		 
    	     }else if("D3".equals(strKindCode) || "D4".equals(strKindCode)){
    		    strCoverageCode = "E01";
    	     }else if("Z".equals(strKindCode)){
     		    strCoverageCode = "G01";
    	     }else{
    		    strCoverageCode = "F01";
    	     }
    	}
    	return strCoverageCode;
    }
    
}
