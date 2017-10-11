package com.sp.indiv.ci.interf;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.sp.indiv.bi.pub.TransCode;
import com.sp.indiv.ci.blsvr.BLCIInsureValid;
import com.sp.indiv.ci.blsvr.BLCIMotorcadeDeclare;
import com.sp.indiv.ci.dbsvr.DBCIInsureValid;
import com.sp.indiv.ci.schema.*;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.cb.BLPrpCitemCar;
import com.sp.prpall.blsvr.cb.BLPrpCprofitDetail;
import com.sp.prpall.schema.*;
import com.sp.prpall.pubfun.PubTools;
import com.sp.prpall.pubfun.TransCodeCI;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utility.SysConfig;
import com.sp.utility.UtiPower;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgData;
import com.sp.utility.string.ChgDate;
import com.sp.utility.string.Str;

/**
 * 发送批改查询请求数据的编码器
 *
 */
public class EndorseQueryEncoder {
     
	
	private static Logger logger = Logger.getLogger(EndorseQueryEncoder.class);
	
    /**
     * 编码
     *p
     * @return XX查询请求XML格式串
     * @throws Exception
     */
    public String encode(DbPool dbPool, 
                         BLPolicy blPolicy) 
        throws Exception 
    {
        
        StringBuffer buf = new StringBuffer(4000);
        addXMLHead(buf);
        addPacket(dbPool, buf, blPolicy);
        
        logger.info("[批改查询发送报文]:"+buf.toString());
        
        return buf.toString();
    }
    
    /**
     * 编码
     * @author liuweichang-ghq 20140624 新增不加dbpool的方法
     * @return XX查询请求XML格式串
     * @throws Exception
     */
    public String encode(BLPolicy blPolicy) 
    throws Exception 
    {
    	
    	StringBuffer buf = new StringBuffer(4000);
    	addXMLHead(buf);
    	addPacket(buf, blPolicy);
    	
    	logger.info("[批改查询发送报文]:"+buf.toString());
    	
    	return buf.toString();
    }

    /**
     * 添加XML文件头
     *
     * @param buf
     *            StringBuffer
     */
    protected void addXMLHead(StringBuffer buf) {
        buf.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
    }

    /**
     * 添加PACKET节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addPacket(DbPool dbPool, 
                             StringBuffer buf, 
                             BLPolicy blPolicy)
            throws Exception 
    {
        buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
        addHead(buf,blPolicy);
        addBody(dbPool, buf, blPolicy);
        buf.append("</PACKET>");
    }
    /**
     * 添加PACKET节
     * @author liuweichang-ghq 20140624 新增不加dbpool的方法
     * @param buf StringBuffer
     * @throws Exception
     */
    protected void addPacket(StringBuffer buf, 
    		BLPolicy blPolicy)
    throws Exception 
    {
    	buf.append("<PACKET type=\"REQUEST\" version=\"1.0\" >");
    	addHead(buf,blPolicy);
    	addBody(buf, blPolicy);
    	buf.append("</PACKET>");
    }

    /**
     * 添加HEAD节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addHead(StringBuffer buf,BLPolicy blPolicy) throws Exception {
        
        
        
        
        
        
        String strComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();

        buf.append("<HEAD>");
        addNode(buf, "REQUEST_TYPE", "04");
        addNode(buf, "USER", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_USER"));
        addNode(buf, "PASSWORD", AppConfig.get("sysconst.CI_INSURED_" + strComCode.substring(0, 2) + "_PASSWORD"));
        buf.append("</HEAD>");
    }

    /**
     * 添加BODY节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addBody(DbPool dbPool, StringBuffer buf, BLPolicy blPolicy)
            throws Exception {
        buf.append("<BODY>");
        addBasePart(dbPool, buf, blPolicy);
        
        if(blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0,2).equals("07")
        		&& !blPolicy.getBLPrpCmain().getArr(0).getRiskCode().equals("0507")){
        	addBusiCoverageList(buf, blPolicy); 
        	
        	addAdjustList(buf, blPolicy);
        	
        }
        
        
    	String strRiskcode = blPolicy.getBLPrpCmain().getArr(0).getRiskCode();
    	String strComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();
        if(new UtiPower().checkCIInsureBJ(strRiskcode, strComCode)
        		&&!blPolicy.getBLPrpCmain().getArr(0).getRiskCode().equals("0507")){
        	addBusiCoverageList(buf, blPolicy);
        	addPolicyDate(buf, blPolicy);
        }
        

        addDriverList(buf, blPolicy);
        
        UtiPower utiPower = new UtiPower();
        if (utiPower.checkStopCom(blPolicy.getBLPrpCmain().getArr(0).getComCode())) {
			addStopTravelInfo(dbPool, buf, blPolicy);
		}
        
        
        addPhList(buf, blPolicy);
        addInsuredList(buf, blPolicy);
        
        
        addVehicleTaxation(buf, blPolicy);
        
        buf.append("</BODY>");
        }
    
    
    /**
     * 添加BODY节
     * @author liuweichang-ghq 20140624 新增不加dbpool的方法
     * @param buf StringBuffer
     * @throws Exception
     */
    protected void addBody(StringBuffer buf, BLPolicy blPolicy)
    throws Exception {
    	buf.append("<BODY>");
    	addBasePart(buf,blPolicy);
    	
    	if(blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0,2).equals("07")
    			&& !blPolicy.getBLPrpCmain().getArr(0).getRiskCode().equals("0507")){
    		addBusiCoverageList(buf, blPolicy); 
    		
    		addAdjustList(buf, blPolicy);
    		
    	}
    	
    	
    	String strRiskcode = blPolicy.getBLPrpCmain().getArr(0).getRiskCode();
    	String strComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();
    	if(new UtiPower().checkCIInsureBJ(strRiskcode, strComCode)
    			&&!blPolicy.getBLPrpCmain().getArr(0).getRiskCode().equals("0507")){
    		addBusiCoverageList(buf, blPolicy);
    		addPolicyDate(buf, blPolicy);
    	}
    	
    	
    	addDriverList(buf, blPolicy);
    	
    	UtiPower utiPower = new UtiPower();
    	if (utiPower.checkStopCom(blPolicy.getBLPrpCmain().getArr(0).getComCode())) {
    	}
    	
    	
    	addPhList(buf, blPolicy);
    	addInsuredList(buf, blPolicy);
    	
    	
    	addVehicleTaxation(buf, blPolicy);
    	
    	buf.append("</BODY>");
    }
    
    /**
     * 添加BASE_PART节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    
    protected void addBasePart(DbPool dbPool, 
                               StringBuffer buf, 
                               BLPolicy blPolicy)
        throws Exception 
    {
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
    	
        
        
        PrpTinsuredSchema prpInsuredSchemaAppli   = null; 
        PrpTinsuredSchema prpInsuredSchemaInsured = null; 
        CIInsureQuerySchema ciInsureQuerySchema   = null; 
        
        
        
        
        String strEndorseType = "";
        
        
        
        String strComCode           = ""; 
        String strQuerySequenceNo   = ""; 
        String strDistrictCode      = ""; 
        String strCarMark           = ""; 
        String strVehicleType       = ""; 
        String strVehicleCategory   = ""; 
        
        String strUseType           = ""; 
        String strEngineNo          = ""; 
        String strFrameNo           = ""; 
        String strRackNo            = ""; 
        String strUseAges           = ""; 
        String strMileages          = ""; 
        String strNewVehicleFlag    = ""; 
        String strEcdemicVehicleFlag = ""; 
        String strMadeFactory       = ""; 
        String strVehicleBrand      = ""; 
        String strVehicleModel      = ""; 
        String strDriverNum         = ""; 
        String strValidDate         = ""; 
        String strLicenseNo         = ""; 
        String strLicenseKindCode   = ""; 
        String strCarOwner          = ""; 
        String strIdentifyNumber    = ""; 
        
        String strUseYears          = ""; 
        String strRunMiles          = ""; 
        String strCarKindCode       = ""; 
        
        
        
        String strPolicyHolderName      = "";   
        String strPolicyHolderCertiType = "";   
        String strPolicyHolderCertiCode = "";   
        String strInsuredName           = "";   
        String strInsuredCertiType      = "";   
        String strInsuredCertiCode      = "";   
        /**<<<<<< added by harry on 22/08/07 发送批改类型 是过户发送01 为是发送空**/
        String strEndorType = "";
        /**added by harry on 22/08/07 发送批改类型 是过户发送01 为是发送空 >>>>>>**/
        
        UtiPower utiPower = new UtiPower();
        
        try 
        {
            String sqlWhere = " Select * from CIInsureValid WHERE PolicyNo = '" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo() + "'";
            DBCIInsureValid dbCIInsureValid             = new DBCIInsureValid();
            Vector vector                               = dbCIInsureValid.findByConditions(dbPool, sqlWhere);
            CIInsureValidSchema cIInsureValidSchema     = (CIInsureValidSchema) vector.get(0);
            BLCIInsureValid blCIInsureValid             = new BLCIInsureValid();
            blCIInsureValid.setArr(cIInsureValidSchema);
            blPolicy.setBLCIInsureValid(blCIInsureValid);
        }
        catch (Exception ex) 
        {
        	ex.printStackTrace();
            throw new Exception("根据XX号(" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo() + ")查询CIInsureValid表失败。详细信息：" + ex);
        }

        ChgDate chgDate = new ChgDate();
        String currentDate = chgDate.getCurrentTime("yyyy-MM-dd HH:mm"); 

        strComCode      = blPolicy.getBLPrpCmain().getArr(0).getComCode();
        
        
        if(utiPower.checkStartUp0802(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(),blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
            strUseType      = encoderUseTypeNew(blPolicy);
            strVehicleCategory = encoderVehicleCategoryNew(blPolicy);
        }else{
            strUseType      = encoderUseType(blPolicy);
            strVehicleCategory = encoderVehicleCategory(blPolicy);            
        }
        
        strEngineNo     = blPolicy.getBLPrpCitemCar().getArr(0).getEngineNo();
        strFrameNo      = blPolicy.getBLPrpCitemCar().getArr(0).getFrameNo();
        strUseAges      = blPolicy.getBLPrpCitemCar().getArr(0).getUseYears();
        strMileages     = "" + ((int) Double.parseDouble(blPolicy.getBLPrpCitemCar().getArr(0).getRunMiles()));
        strVehicleBrand = blPolicy.getBLPrpCitemCar().getArr(0).getBrandName();
        strVehicleModel = blPolicy.getBLPrpCitemCar().getArr(0).getModelCode();
        strVehicleType  = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseKindCode();
        strDriverNum    = "0";
        strRackNo       = blPolicy.getBLPrpCitemCar().getArr(0).getFrameNo();
        strValidDate    = blPolicy.getBLPrpCitemKind().getArr(0).getEndDate();   
        
        
        if((utiPower.checkCIInsureSH(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), strComCode)
        		||utiPower.checkCIInsureBJ(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), strComCode))
        		&& !blPolicy.getBLPrpCmain().getArr(0).getRiskCode().equals("0507")){
        	strValidDate = blPolicy.getBLPrpCmain().getArr(0).getEndDate();
        }
        
        
        strValidDate    = strValidDate.substring(0, 4) + strValidDate.substring(5, 7) + strValidDate.substring(8, 10);
        
        
        if(blPolicy.getBLCIInsureQuery().getSize() >0 && utiPower.checkStopCom(strComCode)){
        	String strValidDateTemp = blPolicy.getBLCIInsureQuery().getArr(0).getValidDate();
        	if(strValidDateTemp != null && !"".equals(strValidDateTemp)){
        		strValidDate =correctDate(blPolicy.getBLCIInsureQuery().getArr(0).getValidDate());
        	}
        }
        
        strLicenseNo    = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseNo(); 
        strLicenseKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseKindCode();
        strCarOwner     = blPolicy.getBLPrpCitemCar().getArr(0).getCarOwner();
        
        
        strUseYears     = blPolicy.getBLPrpCitemCar().getArr(0).getUseYears();
        
        strCarKindCode  = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
        
        
        
        
        if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+strLicenseNo.trim()+",") > -1 || 
        
           "".equals(strLicenseNo.trim()))
        {
            strLicenseNo = "";
        }
        
        
        
        if(strComCode.substring(0,2).equals("31")){
            if("外地转入".equals(strLicenseNo.trim())||"本地转出".equals(strLicenseNo.trim())||
                    "本地过户".equals(strLicenseNo.trim())||"过户车辆".equals(strLicenseNo.trim())){
                        strLicenseNo="";
            }
        }
        
        buf.append("<BASE_PART>");
        addNode(buf, "CONFIRM_SEQUENCE_NO", blPolicy.getBLCIInsureValid().getArr(0).getValidNo()); 
        addNode(buf, "AMEND_QUERY_NO", "");                     
        
        
        if(strComCode.substring(0,2).equals("01")    || 
           strComCode.substring(0,2).equals("07"))
        {
            addNode(buf, "EFFECTIVE_DATE", strValidDate);   
        }
        else 
        {
            
            addNode(buf, "EFFECTIVE_DATE", strValidDate + "00");    
        }
        
        
        
        
        





        
        
        if(blPolicy.getBLCIInsureQuery().getSize() >0)
        {
        
        
        
        strEndorseType = blPolicy.getBLCIInsureQuery().getArr(0).getCIInsureQueryFlag();
        ciInsureQuerySchema = blPolicy.getBLCIInsureQuery().getArr(0);
        
        /**<<<<<< added by harry on 22/08/07 发送批改类型 是过户发送01 为是发送空**/
        strEndorType = blPolicy.getBLCIInsureQuery().getArr(0).getChangOwnerFlag();
        if(strEndorType.equals("71"))
        {
            strEndorType = "01";
        }else{
        	strEndorType = "";
        }
        /**added by harry on 22/08/07 发送批改类型 是过户发送01 为是发送空 >>>>>>**/
        
        if(strComCode.substring(0,2).equals("01")    || 
           strComCode.substring(0,2).equals("07"))
        {
            
            if(strEndorseType.trim().indexOf("03") > -1)
            {
                
            	
            	
                
                
                
                
                
                
                
                
                String strEndorseTypeItemList = blPolicy.getBLCIInsureQuery().getArr(0).getEndorseTypeItemList();
                
                
                
                if(strEndorseTypeItemList.indexOf("08")>-1){
                    String strVehicleCategoryOld= ""; 
                    if(strEndorseTypeItemList.indexOf("02")>-1){
                        
                    }else{
                        BLPolicy policyOld          = new BLPolicy();
                                                                     
                        policyOld.getData(blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
                        
                        if(utiPower.checkStartUp0802(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(),blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
                           strVehicleCategoryOld=encoderVehicleCategoryNew(policyOld);
                        }else{
                            strVehicleCategoryOld=encoderVehicleCategory(policyOld);
                        }
                        
                        
                        if(!strVehicleCategoryOld.equals(strVehicleCategory)){
                           strEndorseTypeItemList=strEndorseTypeItemList+",02";
                        }
                    }
                }
                
                
                
                if(strEndorType.equals("01")){
            		
            		if(strEndorseTypeItemList.indexOf("01")>-1){
            			addNode(buf, "USE_TYPE", strUseType);              
            		}
            		addNode(buf, "OWNER_NAME", strCarOwner);               
            		
            		if(strEndorseTypeItemList.indexOf("03")>-1){
            			addNode(buf, "CAR_MARK", strLicenseNo);                 
            		}
            		if(strEndorseTypeItemList.indexOf("04")>-1){
            			addNode(buf, "VEHICLE_TYPE", strVehicleType);           
            		}
            		
            	}
                
            	else{
                   if(strEndorseTypeItemList.indexOf("01")>-1) 
            	     addNode(buf, "USE_TYPE", strUseType);                   
                   
                   if(strEndorseTypeItemList.indexOf("02")>-1){
                	 
                	 
                	 if(checkTransUseType(strCarKindCode) && strEndorseTypeItemList.indexOf("01")<0){
                       addNode(buf, "USE_TYPE", strUseType);
                	 }
                     addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);   
                   }
                   
                   if(strEndorseTypeItemList.indexOf("03")>-1)
                     addNode(buf, "CAR_MARK", strLicenseNo);                 
                   if(strEndorseTypeItemList.indexOf("04")>-1)
                     addNode(buf, "VEHICLE_TYPE", strVehicleType);           
                   if(strEndorseTypeItemList.indexOf("05")>-1)
                     addNode(buf, "ENGINE_NO", strEngineNo);                 
                   if(strEndorseTypeItemList.indexOf("06")>-1)
                     addNode(buf, "RACK_NO", strRackNo);                     
                   if(strEndorseTypeItemList.indexOf("07")>-1)
                     addNode(buf, "OWNER_NAME", strCarOwner);                
                   
                   
                   
                   
                   
                   if(strComCode.substring(0,2).equals("07")){
                	   addNode(buf, "USE_AGES", strUseYears);                
                	   addNode(buf, "MILEAGES", strRunMiles);                
                	   addNode(buf, "MADE_FACTORY", "");                     
                	   addNode(buf, "VEHICLE_BRAND", strVehicleBrand);       
                	   
                	   BLPrpCitemCar blPrpCitemCarOld = new BLPrpCitemCar();
                       blPrpCitemCarOld.getData(blPolicy.getBLPrpCitemCar().getArr(0).getPolicyNo());
                       String strVehicleModelOld = blPrpCitemCarOld.getArr(0).getModelCode();
                       if(strVehicleModel.equals(strVehicleModelOld)){
                    	   strVehicleModel = "";
                       }
                	   addNode(buf, "VEHICLE_MODEL", strVehicleBrand);       
                	   addNode(buf, "VEHICLE_CODE",strVehicleModel);
                	   addNode(buf, "OTHERS","");
                	   
                   }
                   
                   
                   String strRiskCode  = blPolicy.getBLPrpCmain().getArr(0).getRiskCode();
                   if(new UtiPower().checkCIInsureBJ(strRiskCode,strComCode)){
                	   addNode(buf, "ASSUMPTION","");         
                	   addNode(buf, "OTHERS","");             
                   }
                   

                }
                
            }
            buf.append("</BASE_PART>");
            
            
            
            if((strEndorseType.trim().indexOf("01") > -1||(strComCode.substring(0,2).equals("07")&&strEndorType.equals("01")))&&!new UtiPower().checkCIInsureBJ(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(),strComCode))
            {
                strPolicyHolderName = ciInsureQuerySchema.getAppliName();
                strPolicyHolderCertiType = "";  
                strPolicyHolderCertiCode = "";  
                
                buf.append("<PH_LIST> <PH_DATA>");
                addNode(buf, "POLICY_HOLDER", strPolicyHolderName);     
                addNode(buf, "CERTI_TYPE", strPolicyHolderCertiType);   
                addNode(buf, "CERTI_CODE", strPolicyHolderCertiCode);   
                buf.append("</PH_DATA> </PH_LIST>");
            }
            
            
            
            if((strEndorseType.trim().indexOf("02") > -1||(strComCode.substring(0,2).equals("07")&&strEndorType.equals("01")))&&!new UtiPower().checkCIInsureBJ(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(),strComCode))
            {
                strInsuredName = ciInsureQuerySchema.getInsuredName();
                strInsuredCertiType = "01";  
                strInsuredCertiCode = ciInsureQuerySchema.getIdentifyNumber();
                
                buf.append("<INSURED_LIST> <INSURED_DATA>");
                addNode(buf, "INSURED_NAME", strInsuredName);           
                addNode(buf, "CERTI_TYPE", strInsuredCertiType);        
                addNode(buf, "CERTI_CODE", strInsuredCertiCode);        
                buf.append("</INSURED_DATA> </INSURED_LIST>");
            }
            
            
            if(new UtiPower().checkCIInsureBJ(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(),strComCode)){
            	strPolicyHolderName = ciInsureQuerySchema.getAppliName();
                strPolicyHolderCertiType = "01";  
                strPolicyHolderCertiCode = "";  
                String strPolicyTelephone = "";
                strInsuredName = ciInsureQuerySchema.getInsuredName();
                strInsuredCertiType = "01";  
                strInsuredCertiCode = ciInsureQuerySchema.getIdentifyNumber();
                String strInsuredTelephone = "";
                for (int i = 0; i < blPolicy.getBLPrpCinsured().getSize(); i++) {
					if("1".equals(blPolicy.getBLPrpCinsured().getArr(i).getInsuredFlag())){
						if("".equals(strInsuredName)||strInsuredName==null){
							strInsuredName = blPolicy.getBLPrpCinsured().getArr(i).getInsuredName();
						}
						strInsuredCertiType = translate(blPolicy.getBLPrpCinsured().getArr(i).getIdentifyType());
						strInsuredTelephone = blPolicy.getBLPrpCinsured().getArr(i).getMobile();
						strInsuredCertiCode	= blPolicy.getBLPrpCinsured().getArr(i).getIdentifyNumber();
					}
					if("2".equals(blPolicy.getBLPrpCinsured().getArr(i).getInsuredFlag())){
						if("".equals(strPolicyHolderName)||strPolicyHolderName==null){
							strPolicyHolderName = blPolicy.getBLPrpCinsured().getArr(i).getInsuredName();
						}
						strPolicyHolderCertiType = translate(blPolicy.getBLPrpCinsured().getArr(i).getIdentifyType());
						strPolicyTelephone = blPolicy.getBLPrpCinsured().getArr(i).getMobile();
						strPolicyHolderCertiCode = blPolicy.getBLPrpCinsured().getArr(i).getIdentifyNumber();
					}
				}
                
                buf.append("<PH_LIST> <PH_DATA>");
                addNode(buf, "POLICY_HOLDER", strPolicyHolderName);     
                addNode(buf, "CERTI_TYPE", strPolicyHolderCertiType);   
                addNode(buf, "CERTI_CODE", strPolicyHolderCertiCode);   
                addNode(buf, "TELEPHONE", strPolicyTelephone);
                buf.append("</PH_DATA> </PH_LIST>");
                buf.append("<INSURED_LIST> <INSURED_DATA>");
                addNode(buf, "INSURED_NAME", strInsuredName);           
                addNode(buf, "CERTI_TYPE", strInsuredCertiType);        
                addNode(buf, "CERTI_CODE", strInsuredCertiCode);        
                addNode(buf, "TELEPHONE", strInsuredTelephone);
                buf.append("</INSURED_DATA> </INSURED_LIST>");
            }
           
           

        }
        else
        {   
        	
        	if(!new UtiPower().checkStopCom(strComCode)){
        		
            	if("Y".equals(blPolicy.getBLPrpCitemCar().getArr(0).getHKFlag())){
            		strLicenseNo = blPolicy.getBLPrpCitemCar().getArr(0).getHKLicenseNo();
            	}
            	
            	
                
                
                addNode(buf, "USE_TYPE", strUseType);                   
                addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);   
                addNode(buf, "CAR_MARK", strLicenseNo);                 
                addNode(buf, "VEHICLE_TYPE", strVehicleType);           
                addNode(buf, "ENGINE_NO", strEngineNo);                 
                addNode(buf, "RACK_NO", strRackNo);                     



                addNode(buf, "AMEND_DRIVER", "0");                      
                addNode(buf, "DRIVER_NUM", strDriverNum);               
                
                /**<<<<<< added by harry on 22/08/07 发送批改类型**/
                
                if(SysConfig.getProperty("EXPERIMENTALCOM").trim().indexOf(strComCode.substring(0,2))>-1){
                	
                	addNode(buf, "ENDOR_TYPE", strEndorType);               
                }
                /**added by harry on 22/08/07 发送批改类型 >>>>>>**/
                
                EndorseCarShipTaxQueryEncoder endorseCarShipTaxQueryEncoder = new EndorseCarShipTaxQueryEncoder();
                endorseCarShipTaxQueryEncoder.encode(blPolicy,buf,"BASE_PART");
                
              
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        		String nowTime=format.format(new Date());
                if(utiPower.checkSinoCarPlatformSZ(strComCode, nowTime)){
                	if("0".equals(blPolicy.getBLPrpCitemCar().getArr(0).getCarOwnerNature())){
                		addNode(buf, "CERTI_TYPE", translate(blPolicy.getBLPrpCitemCar().getArr(0).getInsuredTypeCode()));       
                	}else {
                		addNode(buf, "CERTI_TYPE", "10");
					}
                	addNode(buf, "CERTI_CODE", "");       
                	String strEnrollDate = "";
                	strEnrollDate = blPolicy.getBLPrpCitemCar().getArr(0).getEnrollDate();
                	addNode(buf, "VEHICLE_REGISTER_DATE", correctDate(strEnrollDate));               
                	String strNum = "0";
                	if(blPolicy.getBLPrpCitemCar().getArr(0).getFrameNo().length()<17){
                		strNum = "1";
                	}
                	addNode(buf, "RACK_NO_FLAG", strNum);               
                }
                
                if (new UtiPower().savePlatformAdjustSwitchJS(strComCode)) {
                	strCarMark = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseNo();
                	strVehicleBrand = blPolicy.getBLPrpCitemCar().getArr(0).getBrandName();
                	strEcdemicVehicleFlag = TransCodeCI.getEcdemicVehicleFlag(strComCode, strCarMark.trim());
                	if("Y".equals(blPolicy.getBLPrpCitemCar().getArr(0).getHKFlag())){
                		strEcdemicVehicleFlag = "2";
                	}
                    String strChgOwnerDate = blPolicy.getBLPrpCitemCar().getArr(0).getChgOwnerDate();
                    addNode(buf, "ECDEMIC_VEHICLE_FLAG", strEcdemicVehicleFlag);
                    addNode(buf, "VEHICLE_MODEL", strVehicleBrand);
                    addNode(buf, "TRANSFER_DATE", correctDate(TransCode.correctDate(strChgOwnerDate)));
                }
                
                
                buf.append("</BASE_PART>");
        	}else{
        		buf.append("</BASE_PART>");
        		buf.append("<CAR>");
        		
            	if("Y".equals(blPolicy.getBLPrpCitemCar().getArr(0).getHKFlag())){
            		strLicenseNo = blPolicy.getBLPrpCitemCar().getArr(0).getHKLicenseNo();
            	}
            	
            	
                
                
                addNode(buf, "USE_TYPE", strUseType);                   
                addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);   
                addNode(buf, "CAR_MARK", strLicenseNo);                 
                addNode(buf, "VEHICLE_TYPE", strVehicleType);           
                addNode(buf, "ENGINE_NO", strEngineNo);                 
                addNode(buf, "RACK_NO", strRackNo);                     



                addNode(buf, "AMEND_DRIVER", "0");                      
                addNode(buf, "DRIVER_NUM", strDriverNum);               
                
                
                if(SysConfig.getProperty("EXPERIMENTALCOM").trim().indexOf(strComCode.substring(0,2))>-1){
                	addNode(buf, "ENDOR_TYPE", strEndorType);               
                }
                
                EndorseCarShipTaxQueryEncoder endorseCarShipTaxQueryEncoder = new EndorseCarShipTaxQueryEncoder();
                endorseCarShipTaxQueryEncoder.encode(blPolicy,buf,"BASE_PART");
                
        		buf.append("</CAR>");
        	}
        	
        }
        }
        
        
        else if(strComCode.substring(0,2).equals("07") && blPolicy.getBLCIInsureQuery().getSize() <=0){
        
        	addNode(buf, "OTHERS","1");
        	buf.append("</BASE_PART>");
        }
        else if(strComCode.substring(0,2).equals("01") && blPolicy.getBLCIInsureQuery().getSize() <=0){
        	addNode(buf, "OTHERS","1");
        	if("1".equals(SysConfig.getProperty("CommissionPlatformBJ"))){
        	addNode(buf, "COMMISSION_RATE",new DecimalFormat("0.00")
        		.format(Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCmain().getArr(0).getDisRate()))/100));
        	}
        	buf.append("</BASE_PART>");
        }
        
        
        
        else if(blPolicy.getBLCIInsureQuery().getSize() <=0){
        
        	
        	
        	if(!new UtiPower().checkStopCom(strComCode)){
        		if("Y".equals(blPolicy.getBLPrpCitemCar().getArr(0).getHKFlag())){
            		strLicenseNo = blPolicy.getBLPrpCitemCar().getArr(0).getHKLicenseNo();
            	}

                addNode(buf, "USE_TYPE", strUseType);                   
                addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);   
                addNode(buf, "CAR_MARK", strLicenseNo);                 
                addNode(buf, "VEHICLE_TYPE", strVehicleType);           
                addNode(buf, "ENGINE_NO", strEngineNo);                 
                addNode(buf, "RACK_NO", strRackNo);                     
                addNode(buf, "AMEND_DRIVER", "0");                      
                addNode(buf, "DRIVER_NUM", strDriverNum);               
                
                if(SysConfig.getProperty("EXPERIMENTALCOM").trim().indexOf(strComCode.substring(0,2))>-1){
                	
                	addNode(buf, "ENDOR_TYPE", strEndorType);               
                }
                EndorseCarShipTaxQueryEncoder endorseCarShipTaxQueryEncoder = new EndorseCarShipTaxQueryEncoder();
                endorseCarShipTaxQueryEncoder.encode(blPolicy,buf,"BASE_PART");
                
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        		String nowTime=format.format(new Date());
                if(utiPower.checkSinoCarPlatformSZ(strComCode,nowTime)){
                	BLPrpCitemCar blPrpCitemCar = new BLPrpCitemCar();
                	blPrpCitemCar.getData(blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
                	if("0".equals(blPolicy.getBLPrpCitemCar().getArr(0).getCarOwnerNature())){
                		addNode(buf, "CERTI_TYPE", translate(blPolicy.getBLPrpCitemCar().getArr(0).getInsuredTypeCode()));       
                	}else {
                		addNode(buf, "CERTI_TYPE", "10");
					}
                	addNode(buf, "CERTI_CODE", "");
                	String strEnrollDate = "";
                	strEnrollDate = blPolicy.getBLPrpCitemCar().getArr(0).getEnrollDate();
                	addNode(buf, "VEHICLE_REGISTER_DATE", correctDate(strEnrollDate));               
                	String strNum = "0";
                	if(blPolicy.getBLPrpCitemCar().getArr(0).getFrameNo().length()<17){
                		strNum = "1";
                	}
                	addNode(buf, "RACK_NO_FLAG", strNum);               
                }
                
                
                if (new UtiPower().savePlatformAdjustSwitchJS(strComCode)) {
                	strCarMark = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseNo();
                	strVehicleBrand = blPolicy.getBLPrpCitemCar().getArr(0).getBrandName();
                	strEcdemicVehicleFlag = TransCodeCI.getEcdemicVehicleFlag(strComCode, strCarMark.trim());
                	if("Y".equals(blPolicy.getBLPrpCitemCar().getArr(0).getHKFlag())){
                		strEcdemicVehicleFlag = "2";
                	}
                    String strChgOwnerDate = blPolicy.getBLPrpCitemCar().getArr(0).getChgOwnerDate();
                    addNode(buf, "ECDEMIC_VEHICLE_FLAG", strEcdemicVehicleFlag);
                    addNode(buf, "VEHICLE_MODEL", strVehicleBrand);
                    addNode(buf, "TRANSFER_DATE", correctDate(TransCode.correctDate(strChgOwnerDate)));
                }
                
                buf.append("</BASE_PART>");
        	}else{
        		buf.append("</BASE_PART>");
        		buf.append("<CAR>");
        		
            	if("Y".equals(blPolicy.getBLPrpCitemCar().getArr(0).getHKFlag())){
            		strLicenseNo = blPolicy.getBLPrpCitemCar().getArr(0).getHKLicenseNo();
            	}
            	
            	
                
                
                addNode(buf, "USE_TYPE", strUseType);                   
                addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);   
                addNode(buf, "CAR_MARK", strLicenseNo);                 
                addNode(buf, "VEHICLE_TYPE", strVehicleType);           
                addNode(buf, "ENGINE_NO", strEngineNo);                 
                addNode(buf, "RACK_NO", strRackNo);                     



                addNode(buf, "AMEND_DRIVER", "0");                      
                addNode(buf, "DRIVER_NUM", strDriverNum);               
                
                
                if(SysConfig.getProperty("EXPERIMENTALCOM").trim().indexOf(strComCode.substring(0,2))>-1){
                	addNode(buf, "ENDOR_TYPE", strEndorType);               
                }
                
                EndorseCarShipTaxQueryEncoder endorseCarShipTaxQueryEncoder = new EndorseCarShipTaxQueryEncoder();
                endorseCarShipTaxQueryEncoder.encode(blPolicy,buf,"BASE_PART");
                
        		buf.append("</CAR>");
        	}
        	
        }
        
        else
        {
            throw new Exception ("批改查询失败：没有批改XX人信息、被XXXXX人信息、车辆信息不能与交强XXXXX平台交互！");
        }
        
    }
    
    
    
    
    /**
     * 添加BASE_PART节
     * @author liuweichang-ghq 20140624 新增不带dbpool方法
     * @param bufStringBuffer
     * @throws Exception
     */
    
    protected void addBasePart(StringBuffer buf, 
                               BLPolicy blPolicy)
        throws Exception 
    {
        PrpTinsuredSchema prpInsuredSchemaAppli   = null; 
        PrpTinsuredSchema prpInsuredSchemaInsured = null; 
        CIInsureQuerySchema ciInsureQuerySchema   = null; 
        
        
        
        
        String strEndorseType = "";
        
        
        
        String strComCode           = ""; 
        String strQuerySequenceNo   = ""; 
        String strDistrictCode      = ""; 
        String strCarMark           = ""; 
        String strVehicleType       = ""; 
        String strVehicleCategory   = ""; 
        
        String strUseType           = ""; 
        String strEngineNo          = ""; 
        String strFrameNo           = ""; 
        String strRackNo            = ""; 
        String strUseAges           = ""; 
        String strMileages          = ""; 
        String strNewVehicleFlag    = ""; 
        String strEcdemicVehicleFlag = ""; 
        String strMadeFactory       = ""; 
        String strVehicleBrand      = ""; 
        String strVehicleModel      = ""; 
        String strDriverNum         = ""; 
        String strValidDate         = ""; 
        String strLicenseNo         = ""; 
        String strLicenseKindCode   = ""; 
        String strCarOwner          = ""; 
        String strIdentifyNumber    = ""; 
        
        String strUseYears          = ""; 
        String strRunMiles          = ""; 
        String strCarKindCode       = ""; 
        
        
        
        String strPolicyHolderName      = "";   
        String strPolicyHolderCertiType = "";   
        String strPolicyHolderCertiCode = "";   
        String strInsuredName           = "";   
        String strInsuredCertiType      = "";   
        String strInsuredCertiCode      = "";   
        /**<<<<<< added by harry on 22/08/07 发送批改类型 是过户发送01 为是发送空**/
        String strEndorType = "";
        /**added by harry on 22/08/07 发送批改类型 是过户发送01 为是发送空 >>>>>>**/
        
        UtiPower utiPower = new UtiPower();
        
        try 
        {
            String sqlWhere = " Select * from CIInsureValid WHERE PolicyNo = '" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo() + "'";
            DBCIInsureValid dbCIInsureValid             = new DBCIInsureValid();
            Vector vector                               = dbCIInsureValid.findByConditions(sqlWhere);
            CIInsureValidSchema cIInsureValidSchema     = (CIInsureValidSchema) vector.get(0);
            BLCIInsureValid blCIInsureValid             = new BLCIInsureValid();
            blCIInsureValid.setArr(cIInsureValidSchema);
            blPolicy.setBLCIInsureValid(blCIInsureValid);
        }
        catch (Exception ex) 
        {
            throw new Exception("根据XX号(" + blPolicy.getBLPrpCmain().getArr(0).getPolicyNo() + ")查询CIInsureValid表失败。详细信息：" + ex);
        }

        ChgDate chgDate = new ChgDate();
        String currentDate = chgDate.getCurrentTime("yyyy-MM-dd HH:mm"); 

        strComCode      = blPolicy.getBLPrpCmain().getArr(0).getComCode();
        
        
        if(utiPower.checkStartUp0802(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(),blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
            strUseType      = encoderUseTypeNew(blPolicy);
            strVehicleCategory = encoderVehicleCategoryNew(blPolicy);
        }else{
            strUseType      = encoderUseType(blPolicy);
            strVehicleCategory = encoderVehicleCategory(blPolicy);            
        }
        
        strEngineNo     = blPolicy.getBLPrpCitemCar().getArr(0).getEngineNo();
        strFrameNo      = blPolicy.getBLPrpCitemCar().getArr(0).getFrameNo();
        strUseAges      = blPolicy.getBLPrpCitemCar().getArr(0).getUseYears();
        strMileages     = "" + ((int) Double.parseDouble(blPolicy.getBLPrpCitemCar().getArr(0).getRunMiles()));
        strVehicleBrand = blPolicy.getBLPrpCitemCar().getArr(0).getBrandName();
        strVehicleModel = blPolicy.getBLPrpCitemCar().getArr(0).getModelCode();
        strVehicleType  = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseKindCode();
        strDriverNum    = "0";
        strRackNo       = blPolicy.getBLPrpCitemCar().getArr(0).getFrameNo();
        strValidDate    = blPolicy.getBLPrpCitemKind().getArr(0).getEndDate();   
        
        
        if((utiPower.checkCIInsureSH(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), strComCode)
        		||utiPower.checkCIInsureBJ(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(), strComCode))
        		&& !blPolicy.getBLPrpCmain().getArr(0).getRiskCode().equals("0507")){
        	strValidDate = blPolicy.getBLPrpCmain().getArr(0).getEndDate();
        }
        
        
        strValidDate    = strValidDate.substring(0, 4) + strValidDate.substring(5, 7) + strValidDate.substring(8, 10);
        
        
        if(blPolicy.getBLCIInsureQuery().getSize() >0 && utiPower.checkStopCom(strComCode)){
        	String strValidDateTemp = blPolicy.getBLCIInsureQuery().getArr(0).getValidDate();
        	if(strValidDateTemp != null && !"".equals(strValidDateTemp)){
        		strValidDate =correctDate(blPolicy.getBLCIInsureQuery().getArr(0).getValidDate());
        	}
        }
        
        strLicenseNo    = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseNo(); 
        strLicenseKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseKindCode();
        strCarOwner     = blPolicy.getBLPrpCitemCar().getArr(0).getCarOwner();
        
        
        strUseYears     = blPolicy.getBLPrpCitemCar().getArr(0).getUseYears();
        
        strCarKindCode  = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
        
        
        
        
        if(SysConfig.getProperty("NewLicenseNoFlag").indexOf(","+strLicenseNo.trim()+",") > -1 || 
        
           "".equals(strLicenseNo.trim()))
        {
            strLicenseNo = "";
        }
        
        
        
        if(strComCode.substring(0,2).equals("31")){
            if("外地转入".equals(strLicenseNo.trim())||"本地转出".equals(strLicenseNo.trim())||
                    "本地过户".equals(strLicenseNo.trim())||"过户车辆".equals(strLicenseNo.trim())){
                        strLicenseNo="";
            }
        }
        
        buf.append("<BASE_PART>");
        addNode(buf, "CONFIRM_SEQUENCE_NO", blPolicy.getBLCIInsureValid().getArr(0).getValidNo()); 
        addNode(buf, "AMEND_QUERY_NO", "");                     
        
        
        if(strComCode.substring(0,2).equals("01")    || 
           strComCode.substring(0,2).equals("07"))
        {
            addNode(buf, "EFFECTIVE_DATE", strValidDate);   
        }
        else 
        {
            
            addNode(buf, "EFFECTIVE_DATE", strValidDate + "00");    
        }
        
        
        
        
        





        
        
        if(blPolicy.getBLCIInsureQuery().getSize() >0)
        {
        
        
        
        strEndorseType = blPolicy.getBLCIInsureQuery().getArr(0).getCIInsureQueryFlag();
        ciInsureQuerySchema = blPolicy.getBLCIInsureQuery().getArr(0);
        
        /**<<<<<< added by harry on 22/08/07 发送批改类型 是过户发送01 为是发送空**/
        strEndorType = blPolicy.getBLCIInsureQuery().getArr(0).getChangOwnerFlag();
        if(strEndorType.equals("71"))
        {
            strEndorType = "01";
        }else{
        	strEndorType = "";
        }
        /**added by harry on 22/08/07 发送批改类型 是过户发送01 为是发送空 >>>>>>**/
        
        if(strComCode.substring(0,2).equals("01")    || 
           strComCode.substring(0,2).equals("07"))
        {
            
            if(strEndorseType.trim().indexOf("03") > -1)
            {
                
            	
            	
                
                
                
                
                
                
                
                
                String strEndorseTypeItemList = blPolicy.getBLCIInsureQuery().getArr(0).getEndorseTypeItemList();
                
                
                
                if(strEndorseTypeItemList.indexOf("08")>-1){
                    String strVehicleCategoryOld= ""; 
                    if(strEndorseTypeItemList.indexOf("02")>-1){
                        
                    }else{
                        BLPolicy policyOld          = new BLPolicy();
                                                                     
                        policyOld.getData(blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
                        
                        if(utiPower.checkStartUp0802(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(),blPolicy.getBLPrpCmain().getArr(0).getOperateDate())){
                           strVehicleCategoryOld=encoderVehicleCategoryNew(policyOld);
                        }else{
                            strVehicleCategoryOld=encoderVehicleCategory(policyOld);
                        }
                        
                        
                        if(!strVehicleCategoryOld.equals(strVehicleCategory)){
                           strEndorseTypeItemList=strEndorseTypeItemList+",02";
                        }
                    }
                }
                
                
                
                if(strEndorType.equals("01")){
            		
            		if(strEndorseTypeItemList.indexOf("01")>-1){
            			addNode(buf, "USE_TYPE", strUseType);              
            		}
            		addNode(buf, "OWNER_NAME", strCarOwner);               
            		
            		if(strEndorseTypeItemList.indexOf("03")>-1){
            			addNode(buf, "CAR_MARK", strLicenseNo);                 
            		}
            		if(strEndorseTypeItemList.indexOf("04")>-1){
            			addNode(buf, "VEHICLE_TYPE", strVehicleType);           
            		}
            		
            	}
                
            	else{
                   if(strEndorseTypeItemList.indexOf("01")>-1) 
            	     addNode(buf, "USE_TYPE", strUseType);                   
                   
                   if(strEndorseTypeItemList.indexOf("02")>-1){
                	 
                	 
                	 if(checkTransUseType(strCarKindCode) && strEndorseTypeItemList.indexOf("01")<0){
                       addNode(buf, "USE_TYPE", strUseType);
                	 }
                     addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);   
                   }
                   
                   if(strEndorseTypeItemList.indexOf("03")>-1)
                     addNode(buf, "CAR_MARK", strLicenseNo);                 
                   if(strEndorseTypeItemList.indexOf("04")>-1)
                     addNode(buf, "VEHICLE_TYPE", strVehicleType);           
                   if(strEndorseTypeItemList.indexOf("05")>-1)
                     addNode(buf, "ENGINE_NO", strEngineNo);                 
                   if(strEndorseTypeItemList.indexOf("06")>-1)
                     addNode(buf, "RACK_NO", strRackNo);                     
                   if(strEndorseTypeItemList.indexOf("07")>-1)
                     addNode(buf, "OWNER_NAME", strCarOwner);                
                   
                   
                   
                   
                   
                   if(strComCode.substring(0,2).equals("07")){
                	   addNode(buf, "USE_AGES", strUseYears);                
                	   addNode(buf, "MILEAGES", strRunMiles);                
                	   addNode(buf, "MADE_FACTORY", "");                     
                	   addNode(buf, "VEHICLE_BRAND", strVehicleBrand);       
                	   
                	   BLPrpCitemCar blPrpCitemCarOld = new BLPrpCitemCar();
                       blPrpCitemCarOld.getData(blPolicy.getBLPrpCitemCar().getArr(0).getPolicyNo());
                       String strVehicleModelOld = blPrpCitemCarOld.getArr(0).getModelCode();
                       if(strVehicleModel.equals(strVehicleModelOld)){
                    	   strVehicleModel = "";
                       }
                	   addNode(buf, "VEHICLE_MODEL", strVehicleBrand);       
                	   addNode(buf, "VEHICLE_CODE",strVehicleModel);
                	   addNode(buf, "OTHERS","");
                	   
                   }
                   
                   
                   String strRiskCode  = blPolicy.getBLPrpCmain().getArr(0).getRiskCode();
                   if(new UtiPower().checkCIInsureBJ(strRiskCode,strComCode)){
                	   addNode(buf, "ASSUMPTION","");         
                	   addNode(buf, "OTHERS","");             
                   }
                   

                }
                
            }
            buf.append("</BASE_PART>");
            
            
            
            if(strEndorseType.trim().indexOf("01") > -1||(strComCode.substring(0,2).equals("07")&&strEndorType.equals("01")))
            {
                strPolicyHolderName = ciInsureQuerySchema.getAppliName();
                strPolicyHolderCertiType = "";  
                strPolicyHolderCertiCode = "";  
                
                buf.append("<PH_LIST> <PH_DATA>");
                addNode(buf, "POLICY_HOLDER", strPolicyHolderName);     
                addNode(buf, "CERTI_TYPE", strPolicyHolderCertiType);   
                addNode(buf, "CERTI_CODE", strPolicyHolderCertiCode);   
                buf.append("</PH_DATA> </PH_LIST>");
            }
            
            
            
            if(strEndorseType.trim().indexOf("02") > -1||(strComCode.substring(0,2).equals("07")&&strEndorType.equals("01")))
            {
                strInsuredName = ciInsureQuerySchema.getInsuredName();
                strInsuredCertiType = "01";  
                strInsuredCertiCode = ciInsureQuerySchema.getIdentifyNumber();
                
                buf.append("<INSURED_LIST> <INSURED_DATA>");
                addNode(buf, "INSURED_NAME", strInsuredName);           
                addNode(buf, "CERTI_TYPE", strInsuredCertiType);        
                addNode(buf, "CERTI_CODE", strInsuredCertiCode);        
                buf.append("</INSURED_DATA> </INSURED_LIST>");
            }
            
            
            if(new UtiPower().checkCIInsureBJ(blPolicy.getBLPrpCmain().getArr(0).getRiskCode(),strComCode)){
            	strPolicyHolderName = ciInsureQuerySchema.getAppliName();
                strPolicyHolderCertiType = "01";  
                strPolicyHolderCertiCode = "";  
                String strPolicyTelephone = "";
                strInsuredName = ciInsureQuerySchema.getInsuredName();
                strInsuredCertiType = "01";  
                strInsuredCertiCode = ciInsureQuerySchema.getIdentifyNumber();
                String strInsuredTelephone = "";
                for (int i = 0; i < blPolicy.getBLPrpCinsured().getSize(); i++) {
					if("1".equals(blPolicy.getBLPrpCinsured().getArr(i).getInsuredFlag())){
						strInsuredTelephone = blPolicy.getBLPrpCinsured().getArr(i).getMobile();
						strInsuredCertiCode = blPolicy.getBLPrpCinsured().getArr(i).getIdentifyNumber();
					}
					if("2".equals(blPolicy.getBLPrpCinsured().getArr(i).getInsuredFlag())){
						strPolicyTelephone = blPolicy.getBLPrpCinsured().getArr(i).getMobile();
						strPolicyHolderCertiCode = blPolicy.getBLPrpCinsured().getArr(i).getIdentifyNumber();
					}
				}
                
                buf.append("<PH_LIST> <PH_DATA>");
                addNode(buf, "POLICY_HOLDER", strPolicyHolderName);     
                addNode(buf, "CERTI_TYPE", strPolicyHolderCertiType);   
                addNode(buf, "CERTI_CODE", strPolicyHolderCertiCode);   
                addNode(buf, "TELEPHONE", strPolicyTelephone);
                buf.append("</PH_DATA> </PH_LIST>");
                buf.append("<INSURED_LIST> <INSURED_DATA>");
                addNode(buf, "INSURED_NAME", strInsuredName);           
                addNode(buf, "CERTI_TYPE", strInsuredCertiType);        
                addNode(buf, "CERTI_CODE", strInsuredCertiCode);        
                addNode(buf, "TELEPHONE", strInsuredTelephone);
                buf.append("</INSURED_DATA> </INSURED_LIST>");
            }
           
           

        }
        else
        {   
        	
        	if(!new UtiPower().checkStopCom(strComCode)){
        		
            	if("Y".equals(blPolicy.getBLPrpCitemCar().getArr(0).getHKFlag())){
            		strLicenseNo = blPolicy.getBLPrpCitemCar().getArr(0).getHKLicenseNo();
            	}
            	
            	
                
                
                addNode(buf, "USE_TYPE", strUseType);                   
                addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);   
                addNode(buf, "CAR_MARK", strLicenseNo);                 
                addNode(buf, "VEHICLE_TYPE", strVehicleType);           
                addNode(buf, "ENGINE_NO", strEngineNo);                 
                addNode(buf, "RACK_NO", strRackNo);                     



                addNode(buf, "AMEND_DRIVER", "0");                      
                addNode(buf, "DRIVER_NUM", strDriverNum);               
                
                /**<<<<<< added by harry on 22/08/07 发送批改类型**/
                
                if(SysConfig.getProperty("EXPERIMENTALCOM").trim().indexOf(strComCode.substring(0,2))>-1){
                	
                	addNode(buf, "ENDOR_TYPE", strEndorType);               
                }
                /**added by harry on 22/08/07 发送批改类型 >>>>>>**/
                
                EndorseCarShipTaxQueryEncoder endorseCarShipTaxQueryEncoder = new EndorseCarShipTaxQueryEncoder();
                endorseCarShipTaxQueryEncoder.encode(blPolicy,buf,"BASE_PART");
                
              
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        		String nowTime=format.format(new Date());
                if(utiPower.checkSinoCarPlatformSZ(strComCode, nowTime)){
                	if("0".equals(blPolicy.getBLPrpCitemCar().getArr(0).getCarOwnerNature())){
                		addNode(buf, "CERTI_TYPE", translate(blPolicy.getBLPrpCitemCar().getArr(0).getInsuredTypeCode()));       
                	}else {
                		addNode(buf, "CERTI_TYPE", "10");
					}
                	addNode(buf, "CERTI_CODE", "");       
                	String strEnrollDate = "";
                	strEnrollDate = blPolicy.getBLPrpCitemCar().getArr(0).getEnrollDate();
                	addNode(buf, "VEHICLE_REGISTER_DATE", correctDate(strEnrollDate));               
                	String strNum = "0";
                	if(blPolicy.getBLPrpCitemCar().getArr(0).getFrameNo().length()<17){
                		strNum = "1";
                	}
                	addNode(buf, "RACK_NO_FLAG", strNum);               
                }
                
                if (new UtiPower().savePlatformAdjustSwitchJS(strComCode)) {
                	strCarMark = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseNo();
                	strVehicleBrand = blPolicy.getBLPrpCitemCar().getArr(0).getBrandName();
                	strEcdemicVehicleFlag = TransCodeCI.getEcdemicVehicleFlag(strComCode, strCarMark.trim());
                	if("Y".equals(blPolicy.getBLPrpCitemCar().getArr(0).getHKFlag())){
                		strEcdemicVehicleFlag = "2";
                	}
                    String strChgOwnerDate = blPolicy.getBLPrpCitemCar().getArr(0).getChgOwnerDate();
                    addNode(buf, "ECDEMIC_VEHICLE_FLAG", strEcdemicVehicleFlag);
                    addNode(buf, "VEHICLE_MODEL", strVehicleBrand);
                    addNode(buf, "TRANSFER_DATE", correctDate(TransCode.correctDate(strChgOwnerDate)));
                }
                
                
                buf.append("</BASE_PART>");
        	}else{
        		buf.append("</BASE_PART>");
        		buf.append("<CAR>");
        		
            	if("Y".equals(blPolicy.getBLPrpCitemCar().getArr(0).getHKFlag())){
            		strLicenseNo = blPolicy.getBLPrpCitemCar().getArr(0).getHKLicenseNo();
            	}
            	
            	
                
                
                addNode(buf, "USE_TYPE", strUseType);                   
                addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);   
                addNode(buf, "CAR_MARK", strLicenseNo);                 
                addNode(buf, "VEHICLE_TYPE", strVehicleType);           
                addNode(buf, "ENGINE_NO", strEngineNo);                 
                addNode(buf, "RACK_NO", strRackNo);                     



                addNode(buf, "AMEND_DRIVER", "0");                      
                addNode(buf, "DRIVER_NUM", strDriverNum);               
                
                
                if(SysConfig.getProperty("EXPERIMENTALCOM").trim().indexOf(strComCode.substring(0,2))>-1){
                	addNode(buf, "ENDOR_TYPE", strEndorType);               
                }
                
                EndorseCarShipTaxQueryEncoder endorseCarShipTaxQueryEncoder = new EndorseCarShipTaxQueryEncoder();
                endorseCarShipTaxQueryEncoder.encode(blPolicy,buf,"BASE_PART");
                
        		buf.append("</CAR>");
        	}
        	
        }
        }
        
        
        else if(strComCode.substring(0,2).equals("07") && blPolicy.getBLCIInsureQuery().getSize() <=0){
        
        	addNode(buf, "OTHERS","1");
        	buf.append("</BASE_PART>");
        }
        else if(strComCode.substring(0,2).equals("01") && blPolicy.getBLCIInsureQuery().getSize() <=0){
        	addNode(buf, "OTHERS","1");
        	if("1".equals(SysConfig.getProperty("CommissionPlatformBJ"))){
        	addNode(buf, "COMMISSION_RATE",new DecimalFormat("0.00")
        		.format(Double.parseDouble(ChgData.chgStrZero(blPolicy.getBLPrpCmain().getArr(0).getDisRate()))/100));
        	}
        	buf.append("</BASE_PART>");
        }
        
        
        
        else if(blPolicy.getBLCIInsureQuery().getSize() <=0){
        
        	
        	
        	if(!new UtiPower().checkStopCom(strComCode)){
        		if("Y".equals(blPolicy.getBLPrpCitemCar().getArr(0).getHKFlag())){
            		strLicenseNo = blPolicy.getBLPrpCitemCar().getArr(0).getHKLicenseNo();
            	}

                addNode(buf, "USE_TYPE", strUseType);                   
                addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);   
                addNode(buf, "CAR_MARK", strLicenseNo);                 
                addNode(buf, "VEHICLE_TYPE", strVehicleType);           
                addNode(buf, "ENGINE_NO", strEngineNo);                 
                addNode(buf, "RACK_NO", strRackNo);                     
                addNode(buf, "AMEND_DRIVER", "0");                      
                addNode(buf, "DRIVER_NUM", strDriverNum);               
                
                if(SysConfig.getProperty("EXPERIMENTALCOM").trim().indexOf(strComCode.substring(0,2))>-1){
                	
                	addNode(buf, "ENDOR_TYPE", strEndorType);               
                }
                EndorseCarShipTaxQueryEncoder endorseCarShipTaxQueryEncoder = new EndorseCarShipTaxQueryEncoder();
                endorseCarShipTaxQueryEncoder.encode(blPolicy,buf,"BASE_PART");
                
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
        		String nowTime=format.format(new Date());
                if(utiPower.checkSinoCarPlatformSZ(strComCode,nowTime)){
                	BLPrpCitemCar blPrpCitemCar = new BLPrpCitemCar();
                	blPrpCitemCar.getData(blPolicy.getBLPrpCmain().getArr(0).getPolicyNo());
                	if("0".equals(blPolicy.getBLPrpCitemCar().getArr(0).getCarOwnerNature())){
                		addNode(buf, "CERTI_TYPE", translate(blPolicy.getBLPrpCitemCar().getArr(0).getInsuredTypeCode()));       
                	}else {
                		addNode(buf, "CERTI_TYPE", "10");
					}
                	addNode(buf, "CERTI_CODE", "");
                	String strEnrollDate = "";
                	strEnrollDate = blPolicy.getBLPrpCitemCar().getArr(0).getEnrollDate();
                	addNode(buf, "VEHICLE_REGISTER_DATE", correctDate(strEnrollDate));               
                	String strNum = "0";
                	if(blPolicy.getBLPrpCitemCar().getArr(0).getFrameNo().length()<17){
                		strNum = "1";
                	}
                	addNode(buf, "RACK_NO_FLAG", strNum);               
                }
                
                
                if (new UtiPower().savePlatformAdjustSwitchJS(strComCode)) {
                	strCarMark = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseNo();
                	strVehicleBrand = blPolicy.getBLPrpCitemCar().getArr(0).getBrandName();
                	strEcdemicVehicleFlag = TransCodeCI.getEcdemicVehicleFlag(strComCode, strCarMark.trim());
                	if("Y".equals(blPolicy.getBLPrpCitemCar().getArr(0).getHKFlag())){
                		strEcdemicVehicleFlag = "2";
                	}
                    String strChgOwnerDate = blPolicy.getBLPrpCitemCar().getArr(0).getChgOwnerDate();
                    addNode(buf, "ECDEMIC_VEHICLE_FLAG", strEcdemicVehicleFlag);
                    addNode(buf, "VEHICLE_MODEL", strVehicleBrand);
                    addNode(buf, "TRANSFER_DATE", correctDate(TransCode.correctDate(strChgOwnerDate)));
                }
                
                buf.append("</BASE_PART>");
        	}else{
        		buf.append("</BASE_PART>");
        		buf.append("<CAR>");
        		
            	if("Y".equals(blPolicy.getBLPrpCitemCar().getArr(0).getHKFlag())){
            		strLicenseNo = blPolicy.getBLPrpCitemCar().getArr(0).getHKLicenseNo();
            	}
            	
            	
                
                
                addNode(buf, "USE_TYPE", strUseType);                   
                addNode(buf, "VEHICLE_CATEGORY", strVehicleCategory);   
                addNode(buf, "CAR_MARK", strLicenseNo);                 
                addNode(buf, "VEHICLE_TYPE", strVehicleType);           
                addNode(buf, "ENGINE_NO", strEngineNo);                 
                addNode(buf, "RACK_NO", strRackNo);                     



                addNode(buf, "AMEND_DRIVER", "0");                      
                addNode(buf, "DRIVER_NUM", strDriverNum);               
                
                
                if(SysConfig.getProperty("EXPERIMENTALCOM").trim().indexOf(strComCode.substring(0,2))>-1){
                	addNode(buf, "ENDOR_TYPE", strEndorType);               
                }
                
                EndorseCarShipTaxQueryEncoder endorseCarShipTaxQueryEncoder = new EndorseCarShipTaxQueryEncoder();
                endorseCarShipTaxQueryEncoder.encode(blPolicy,buf,"BASE_PART");
                
        		buf.append("</CAR>");
        	}
        	
        }
        
        else
        {
            throw new Exception ("批改查询失败：没有批改XX人信息、被XXXXX人信息、车辆信息不能与交强XXXXX平台交互！");
        }
        
    }
    
    
    
    
    
    
    /**
     * 添加STOP_TRAVEL_INFO节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addStopTravelInfo(DbPool dbPool, StringBuffer buf, BLPolicy blPolicy) throws Exception {
		String strStopTravelStratDate = ""; 
		String strStopTravelEndDate = ""; 
		String strReqDate = ""; 
		String strRecoverTravelDate = ""; 
		String strStopTravelType = ""; 

		String strStopTravelStratHour = ""; 
		String strStopTravelEndHour = ""; 
		String strValidHour = ""; 
		String strStopTravelTypeFlag = ""; 
		String strValidStatus = "";
		ChgDate chgDate = new ChgDate();
		CIInsureQuerySchema ciInsureQuerySchema = new CIInsureQuerySchema();
		if (blPolicy.getBLCIInsureQuery().getSize()>0) {
			ciInsureQuerySchema = blPolicy.getBLCIInsureQuery().getArr(0);
			strValidStatus = ciInsureQuerySchema.getValidStatus();
			if (!"".equals(strValidStatus) && null != strValidStatus) {
				strStopTravelStratDate = ciInsureQuerySchema.getStopTravelStratDate();
				strStopTravelEndDate = ciInsureQuerySchema.getStopTravelEndDate();
				strStopTravelStratHour = TransCode.correctHour(ciInsureQuerySchema.getStopTravelStratHour());
				strStopTravelEndHour = TransCode.correctHour(ciInsureQuerySchema.getStopTravelEndHour());
				strValidHour = TransCode.correctHour(ciInsureQuerySchema.getValidHour());

				strStopTravelStratDate = TransCode.correctDate(strStopTravelStratDate) + strStopTravelStratHour;
				strStopTravelEndDate = TransCode.correctDate(strStopTravelEndDate) + strStopTravelEndHour;
				
				strReqDate = chgDate.getCurrentTime("yyyyMMddHH"); 
				strRecoverTravelDate = TransCode.correctDate(ciInsureQuerySchema.getRecoverTravelDate())
						+ TransCode.correctHour(ciInsureQuerySchema.getStopTravelEndHour());
				strStopTravelTypeFlag = ciInsureQuerySchema.getStopTravelTypeFlag();
				if (!"".equals(strStopTravelTypeFlag) && "1".equals(strStopTravelTypeFlag)) {
					strStopTravelType = "1";
				} else if (!"".equals(strStopTravelTypeFlag) && "3".equals(strStopTravelTypeFlag)) {
					strStopTravelType = "2";
				}
				buf.append("<STOP_TRAVEL_INFO>");
				addNode(buf, "STOPTRAVEL_STARTDATE", strStopTravelStratDate);
				addNode(buf, "STOPTRAVEL_ENDDATE", strStopTravelEndDate);
				addNode(buf, "REQ_DATE", strReqDate);
				addNode(buf, "RECOVER_TRAVEL_DATE", strRecoverTravelDate);
				addNode(buf, "STOPT_RAVEL_TYPE", strStopTravelType);
				buf.append("</STOP_TRAVEL_INFO>");
			}
		}

	}
    
    
    
    /**
     * 添加STOP_TRAVEL_INFO节
     * @author liuweichang-ghq 20140623 增加不带dbpool的方法
     * @param bufStringBuffer
     * @throws Exception
     */
    protected void addStopTravelInfo(StringBuffer buf, BLPolicy blPolicy) throws Exception {
		String strStopTravelStratDate = ""; 
		String strStopTravelEndDate = ""; 
		String strReqDate = ""; 
		String strRecoverTravelDate = ""; 
		String strStopTravelType = ""; 

		String strStopTravelStratHour = ""; 
		String strStopTravelEndHour = ""; 
		String strValidHour = ""; 
		String strStopTravelTypeFlag = ""; 
		String strValidStatus = "";
		ChgDate chgDate = new ChgDate();
		CIInsureQuerySchema ciInsureQuerySchema = new CIInsureQuerySchema();
		if (blPolicy.getBLCIInsureQuery().getSize()>0) {
			ciInsureQuerySchema = blPolicy.getBLCIInsureQuery().getArr(0);
			strValidStatus = ciInsureQuerySchema.getValidStatus();
			if (!"".equals(strValidStatus) && null != strValidStatus) {
				strStopTravelStratDate = ciInsureQuerySchema.getStopTravelStratDate();
				strStopTravelEndDate = ciInsureQuerySchema.getStopTravelEndDate();
				strStopTravelStratHour = TransCode.correctHour(ciInsureQuerySchema.getStopTravelStratHour());
				strStopTravelEndHour = TransCode.correctHour(ciInsureQuerySchema.getStopTravelEndHour());
				strValidHour = TransCode.correctHour(ciInsureQuerySchema.getValidHour());

				strStopTravelStratDate = TransCode.correctDate(strStopTravelStratDate) + strStopTravelStratHour;
				strStopTravelEndDate = TransCode.correctDate(strStopTravelEndDate) + strStopTravelEndHour;
				
				strReqDate = chgDate.getCurrentTime("yyyyMMddHH"); 
				strRecoverTravelDate = TransCode.correctDate(ciInsureQuerySchema.getRecoverTravelDate())
						+ TransCode.correctHour(ciInsureQuerySchema.getStopTravelEndHour());
				strStopTravelTypeFlag = ciInsureQuerySchema.getStopTravelTypeFlag();
				if (!"".equals(strStopTravelTypeFlag) && "1".equals(strStopTravelTypeFlag)) {
					strStopTravelType = "1";
				} else if (!"".equals(strStopTravelTypeFlag) && "3".equals(strStopTravelTypeFlag)) {
					strStopTravelType = "2";
				}
				buf.append("<STOP_TRAVEL_INFO>");
				addNode(buf, "STOPTRAVEL_STARTDATE", strStopTravelStratDate);
				addNode(buf, "STOPTRAVEL_ENDDATE", strStopTravelEndDate);
				addNode(buf, "REQ_DATE", strReqDate);
				addNode(buf, "RECOVER_TRAVEL_DATE", strRecoverTravelDate);
				addNode(buf, "STOPT_RAVEL_TYPE", strStopTravelType);
				buf.append("</STOP_TRAVEL_INFO>");
			}
		}

	}
    
    

    /**
     * 添加DRIVER_LIST节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addDriverList(StringBuffer buf, BLPolicy blPolicy)
            throws Exception {
        buf.append("<DRIVER_LIST>");
        
        buf.append("</DRIVER_LIST>");
    }

    /**
     * 添加DRIVER节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addDriver(StringBuffer buf, BLPolicy blPolicy)
            throws Exception {
        
        
        
        
        
        
        
        
        
        

        String strLicenseNo     = ""; 
        String strCertiType     = ""; 
        String strIsMaster      = ""; 
        String strArea          = ""; 
        String strGender        = ""; 
        String strDriverPeriod  = ""; 
        String strAge           = ""; 
        String strDriverType    = ""; 
        strLicenseNo = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseNo();
        
        
        for (int i = 0; i < blPolicy.getBLPrpCcarDriver().getSize(); i++) {
		if(blPolicy.getBLPrpCcarDriver().getArr(i).getIdentifyNumber()!=null 
				&& !"".equals(blPolicy.getBLPrpCcarDriver().getArr(i).getIdentifyNumber())){
			strCertiType = carDriverIdentifyTypeEncoder(blPolicy.getBLPrpCcarDriver().getArr(i).getIdentifyType());
			strLicenseNo = blPolicy.getBLPrpCcarDriver().getArr(i).getIdentifyNumber();
			strIsMaster = (i==0)?"1":"2";
			strGender = blPolicy.getBLPrpCcarDriver().getArr(i).getSex();
			strDriverPeriod = blPolicy.getBLPrpCcarDriver().getArr(i).getDrivingYears();
			strAge = blPolicy.getBLPrpCcarDriver().getArr(i).getAge();
			strDriverType = blPolicy.getBLPrpCcarDriver().getArr(i).getDrivingCarType();
		}
		
        buf.append("<DRIVER>");
        addNode(buf, "LICENSE_NO", strLicenseNo); 
        addNode(buf, "CERTI_TYPE", strCertiType); 
        addNode(buf, "IS_MASTER", strIsMaster); 
        addNode(buf, "AREA", strArea); 
        addNode(buf, "GENDER", strGender); 
        addNode(buf, "DRIVER_PERIOD", strDriverPeriod); 
        addNode(buf, "AGE", strDriverPeriod); 
        addNode(buf, "DRIVER_TYPE", strDriverType); 
        buf.append("</DRIVER>");
        
        if(blPolicy.getBLPrpCcarDriver().getArr(i).getIdentifyNumber()==null 
				|| "".equals(blPolicy.getBLPrpCcarDriver().getArr(i).getIdentifyNumber())){
        	break;
        }
        }
        
    }
    
    
    /**
     * 添加BUSI_COVERAGE_LIST节 批改商业三者XXXXXXXXXX种信息BUSI_COVERAGE_LIST
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addBusiCoverageList(StringBuffer buf, BLPolicy blPolicy)
            throws Exception {
		buf.append("<BUSI_COVERAGE_LIST>");
		addBusiCoverageData(buf, blPolicy);
		buf.append("</BUSI_COVERAGE_LIST>");
    }
    
    /**
     * 添加BUSI_COVERAGE_DATA节
     *
     * @param buf
     *            StringBuffer
     * @throws Exception
     */
    protected void addBusiCoverageData(StringBuffer buf, BLPolicy blPolicy)
            throws Exception {










    	
    	String strCoverageType  = ""; 
    	String strCoverageCode  = ""; 
    	String strKindCode      = ""; 
    	String strAmount        = ""; 
    	String strChgPremium    = ""; 
    	String strStartDate     = ""; 
    	String strEndDate       = ""; 
    	String strRiskCode      = ""; 
    	int iPolicyKindCount    = blPolicy.getBLPrpCitemKind().getSize();
		for(int i = 0;i<iPolicyKindCount;i++){
			if(blPolicy.getBLPrpCitemKind().getArr(i).getFlag().charAt(0) == ' '){
				continue;
			}
			strKindCode   = blPolicy.getBLPrpCitemKind().getArr(i).getKindCode();
			strRiskCode   = blPolicy.getBLPrpCitemKind().getArr(i).getRiskCode();
			strCoverageType = encodeCoverageType(strKindCode);
			
			if(new UtiPower().checkCIInsureBJ(strRiskCode, blPolicy.getBLPrpCmain().getArr(0).getComCode())){
				strCoverageCode = encodeCoverageCodeBJ(strKindCode, strRiskCode);
			}else{
				strCoverageCode = encodeCoverageCode(strKindCode, strRiskCode);
			}
			
			strAmount     = blPolicy.getBLPrpCitemKind().getArr(i).getAmount();
			strChgPremium = blPolicy.getBLPrpCitemKind().getArr(i).getChgPremium();
			strStartDate = blPolicy.getBLPrpCitemKind().getArr(i).getStartDate();
			strStartDate = StringUtils.replace(strStartDate, "-", "");
			strEndDate = blPolicy.getBLPrpCitemKind().getArr(i).getEndDate();
			strEndDate = StringUtils.replace(strEndDate, "-", "");

			buf.append("<BUSI_COVERAGE_DATA>");
            addNode(buf, "COVERAGE_TYPE", strCoverageType);       
            addNode(buf, "COVERAGE_CODE", strCoverageCode);       
            addNode(buf, "COM_COVERAGE_CODE", strKindCode);       
            addNode(buf, "LIMIT_AMOUNT", strAmount);              
            
            addNode(buf, "BASED_PREMIUM", blPolicy.getBLPrpCitemKind().getArr(i).getBenchMarkPremium());
            
            addNode(buf, "BUSI_COVERAGE_PREMIUM", "" + Str.round(Double.parseDouble(strChgPremium), 2)); 
            addNode(buf, "START_DATE", strStartDate);             
            addNode(buf, "END_DATE", strEndDate);                 
            buf.append("</BUSI_COVERAGE_DATA>");
    	}
    }
    
    
    private void addAdjustList(StringBuffer buf, BLPolicy blPolicy)throws Exception {
    	String strOtherNature2 = "";
    	if(blPolicy.getBLPrpCitemCar().getArr(0).getOtherNature()!=null && blPolicy.getBLPrpCitemCar().getArr(0).getOtherNature().length()>2){
    		strOtherNature2 = blPolicy.getBLPrpCitemCar().getArr(0).getOtherNature().substring(1,2);
    	}
    	buf.append("<ADJUST_LIST>");
    	
    	String operateDate=blPolicy.getBLPrpCmain().getArr(0).getInputDate();
    	String contractNo=blPolicy.getBLPrpCmain().getArr(0).getContractNo();
    	String groupCode="";
    	String policyNo=blPolicy.getBLPrpCmain().getArr(0).getPolicyNo();
    	String vehicleAmountAdjust="1.0";   
    	double douVehicleAmountAdjust=1.0D;
    	String experienceAdjust="1.0";      
    	double douExperienceAdjust=1.0D;
    	String motorcadeMangeAdjust="1.0";   
    	double douMotorcadeMangeAdjust=1.0D;
    	PrpCprofitDetailSchema  prpCprofitDetailSchema=null;
    	BLCIMotorcadeDeclare blCIMotorcadeDeclare=new BLCIMotorcadeDeclare();
    	blCIMotorcadeDeclare.query(" contractNo='"+contractNo+"'");	
		if(blCIMotorcadeDeclare.getSize()>0){
			groupCode=blCIMotorcadeDeclare.getArr(0).getGroupCode();
		}
		BLPrpCprofitDetail blPrpCprofitDetail=new BLPrpCprofitDetail();
		blPrpCprofitDetail.getData(policyNo);
    	if((contractNo!=null&&!"".equals(contractNo))
        	&&(new UtiPower().checkSHDeclareQuery(operateDate))
        	&&(groupCode!=null&&!"".equals(groupCode))){
    			for (int i = 0; i < blPrpCprofitDetail.getSize(); i++) {
    				prpCprofitDetailSchema=blPrpCprofitDetail.getArr(i);
        			if("C10".equals(prpCprofitDetailSchema.getProfitCode())){  
        				vehicleAmountAdjust=prpCprofitDetailSchema.getProfitRate();
        				if(vehicleAmountAdjust!=null&&!"".equals(vehicleAmountAdjust)){
        					douVehicleAmountAdjust=Double.parseDouble(vehicleAmountAdjust);
        					douVehicleAmountAdjust=1+douVehicleAmountAdjust;
        				}
        			}else if("C11".equals(prpCprofitDetailSchema.getProfitCode())){  
        				experienceAdjust=prpCprofitDetailSchema.getProfitRate();
        				if(experienceAdjust!=null&&!"".equals(experienceAdjust)){
        					douExperienceAdjust=Double.parseDouble(experienceAdjust);
        					douExperienceAdjust=1+douExperienceAdjust;
        				}
        			}else if("C12".equals(prpCprofitDetailSchema.getProfitCode())){  
        				motorcadeMangeAdjust=prpCprofitDetailSchema.getProfitRate();
        				if(motorcadeMangeAdjust!=null&&!"".equals(motorcadeMangeAdjust)){
        					douMotorcadeMangeAdjust=Double.parseDouble(motorcadeMangeAdjust);
        					douMotorcadeMangeAdjust=1+douMotorcadeMangeAdjust;
        				}
        			}
				}
    		  addNode(buf, "SAFE_ADJUST", "1.0");
              addNode(buf, "NONCLAIM_ADJUST","1.0" );
              addNode(buf, "LOYALTY_ADJUST", "1.0");
              addNode(buf, "MULTI_COVERAGE_ADJUST", "1.0");
              addNode(buf, "AVERAGE_MILE_ADJUST", "1.0");
              addNode(buf, "THE_AREA_ADJUST", "1.0");
              addNode(buf, "VEHICLE_AMOUNT_ADJUST", ""+douVehicleAmountAdjust);
              addNode(buf, "THE_DRIVER_ADJUST", "1.0");
              addNode(buf, "DRIVER_GENDER_ADJUST", "1.0");
              addNode(buf, "DRIVER_PERIOD_ADJUST", "1.0");
              addNode(buf, "DRIVER_AGE_ADJUST", "1.0");
              addNode(buf, "EXPERIENCE_ADJUST", ""+douExperienceAdjust);
              addNode(buf, "MANAGE_LEVEL_ADJUST", "");
              addNode(buf, "VEHICLE_LOSS_ADJUST", "1.0");
              addNode(buf, "ABSOLUTE_FRANCHISE_ADJUST", "1.0");
              addNode(buf, "MOTORCADE_MANAGE_ADJUST", ""+douMotorcadeMangeAdjust);
              addNode(buf, "MOTORCADE_ADJUST", "");
              addNode(buf, "SALE_CHANNEL_ADJUST", "1.0");
              addNode(buf, "GROUP_BUY_ADJUST", "1.0");
              addNode(buf, "DETAILS_ADJUST", "1.0");
              addNode(buf, "CROSS_SALE_ADJUST", "1.0");
              addNode(buf, "SERVICE_LOG_ADJUST", "1.0");
              
    	}else{
            addNode(buf, "SAFE_ADJUST", encodeSafeAdjust(blPolicy.getBLPrpCitemCar().getArr(0).getAddonCount()));
            addNode(buf, "NONCLAIM_ADJUST", encodeNonclaimAdjust(blPolicy.getBLPrpCitemCarExt().getArr(0).getDamagedFactorGrade()));
            addNode(buf, "LOYALTY_ADJUST", encodeLoyaltyAdjust(strOtherNature2));
            addNode(buf, "MULTI_COVERAGE_ADJUST", "");
            addNode(buf, "AVERAGE_MILE_ADJUST", "");
            addNode(buf, "THE_AREA_ADJUST", "");
            addNode(buf, "VEHICLE_AMOUNT_ADJUST", "");
            addNode(buf, "THE_DRIVER_ADJUST", "");
            addNode(buf, "DRIVER_GENDER_ADJUST", "");
            addNode(buf, "DRIVER_PERIOD_ADJUST", "");
            addNode(buf, "DRIVER_AGE_ADJUST", "");
            addNode(buf, "EXPERIENCE_ADJUST", "");
            addNode(buf, "MANAGE_LEVEL_ADJUST", "");
            addNode(buf, "VEHICLE_LOSS_ADJUST", "");
            addNode(buf, "ABSOLUTE_FRANCHISE_ADJUST", "");
            addNode(buf, "MOTORCADE_MANAGE_ADJUST", "");
            addNode(buf, "MOTORCADE_ADJUST", "");
            addNode(buf, "SALE_CHANNEL_ADJUST", "");
            addNode(buf, "GROUP_BUY_ADJUST", "");
            addNode(buf, "DETAILS_ADJUST", "");
            addNode(buf, "CROSS_SALE_ADJUST", "");
            addNode(buf, "SERVICE_LOG_ADJUST", "");
    	}
    	buf.append("</ADJUST_LIST>");
    	
    }
    
    
    private void addPolicyDate(StringBuffer buf, BLPolicy blPolicy)throws Exception {
    	String strPurchasePrice = "";
    	String strModelCode = "";
    	String strBrandName = "";
    	String strFrameNo = "";
    	String strEngineNo = "";
    	String strEnrollDate = "";
    	String strSeatCount = "";
    	String strTonCount = "";
    	String strLicenseCategory = "";
    	String strStartDate = "";
    	strPurchasePrice = blPolicy.getBLPrpCitemCar().getArr(0).getPurchasePrice();
    	strModelCode = blPolicy.getBLPrpCitemCar().getArr(0).getModelCode();
    	strBrandName = blPolicy.getBLPrpCitemCar().getArr(0).getBrandName();
    	strFrameNo = blPolicy.getBLPrpCitemCar().getArr(0).getFrameNo();
    	strEngineNo = blPolicy.getBLPrpCitemCar().getArr(0).getEngineNo();
    	strEnrollDate = blPolicy.getBLPrpCitemCar().getArr(0).getEnrollDate();
    	strSeatCount = blPolicy.getBLPrpCitemCar().getArr(0).getSeatCount();
    	strTonCount = blPolicy.getBLPrpCitemCar().getArr(0).getTonCount();
    	DecimalFormat ideciamlFormat = new DecimalFormat("0");
    	strTonCount = ideciamlFormat.format(Double.parseDouble(strTonCount)*1000);
    	strLicenseCategory = blPolicy.getBLPrpCitemCar().getArr(0).getLicenseCategory();
    	strStartDate = blPolicy.getBLPrpCmain().getArr(0).getStartDate();
    	strStartDate = StringUtils.replace(strStartDate, "-", "");
    	
    	buf.append("<BUSI_POLICY_DATA>");
        addNode(buf, "VEHICLE_PRICE", strPurchasePrice);
        addNode(buf, "VEHICLE_CODE", strModelCode);
        addNode(buf, "R_VEHICLE_NAME", strBrandName);
        addNode(buf, "RACK_NO", strFrameNo);
        addNode(buf, "ENGINE_NO", strEngineNo);
        addNode(buf, "VEHICLE_REGISTER_DATE", correctDate(strEnrollDate));
        addNode(buf, "LIMIT_LOAD_PERSON", strSeatCount);
        addNode(buf, "LIMIT_LOAD", strTonCount);
        addNode(buf, "VEHICLE_STYLE", strLicenseCategory);
        addNode(buf, "START_DATE", strStartDate);
        buf.append("</BUSI_POLICY_DATA>");
    }
    
    
    
    private void addPhList(StringBuffer buf, BLPolicy blPolicy)throws Exception {
    	String appliName="";
    	String certiType="";
    	String certiCode="";
    	String riskCode=blPolicy.getBLPrpCmain().getArr(0).getRiskCode();
    	String comCode=blPolicy.getBLPrpCmain().getArr(0).getComCode();
    	String operateDate=blPolicy.getBLPrpCmain().getArr(0).getInputDate();
    	String contractNo=blPolicy.getBLPrpCmain().getArr(0).getContractNo();
    	if(new UtiPower().checkSHDeclare(comCode,riskCode,operateDate)){
	    		if(contractNo!=null&&!"".equals(contractNo)){
			    	for (int i = 0; i < blPolicy.getBLPrpCinsured().getSize(); i++) {
			    		if("2".equals(blPolicy.getBLPrpCinsured().getArr(i).getInsuredFlag())){
			    			appliName=blPolicy.getBLPrpCinsured().getArr(i).getInsuredName();
			    			certiType=blPolicy.getBLPrpCinsured().getArr(i).getIdentifyType();
			    			certiCode=blPolicy.getBLPrpCinsured().getArr(i).getIdentifyNumber();
			    			break;
			    		}
					}
			    	buf.append("<PH_LIST><PH_DATA>");
			    	addNode(buf, "POLICY_HOLDER", appliName);   	
			    	addNode(buf, "CERTI_TYPE", certiType);   		
			    	addNode(buf, "CERTI_CODE", certiCode);   		
			    	buf.append("</PH_DATA></PH_LIST>");
	    		}
	    	}
    }
    private void addInsuredList(StringBuffer buf, BLPolicy blPolicy)throws Exception {
    	String insuredName="";
    	String certiType="";
    	String certiCode="";
    	String riskCode=blPolicy.getBLPrpCmain().getArr(0).getRiskCode();
    	String comCode=blPolicy.getBLPrpCmain().getArr(0).getComCode();
    	String operateDate=blPolicy.getBLPrpCmain().getArr(0).getInputDate();
    	String contractNo=blPolicy.getBLPrpCmain().getArr(0).getContractNo();
    	if(new UtiPower().checkSHDeclare(comCode,riskCode,operateDate)){
	    		if(contractNo!=null&&!"".equals(contractNo)){
			    	for (int i = 0; i < blPolicy.getBLPrpCinsured().getSize(); i++) {
			    		if("1".equals(blPolicy.getBLPrpCinsured().getArr(i).getInsuredFlag())){
			    			insuredName=blPolicy.getBLPrpCinsured().getArr(i).getInsuredName();
			    			certiType=blPolicy.getBLPrpCinsured().getArr(i).getIdentifyType();
			    			certiCode=blPolicy.getBLPrpCinsured().getArr(i).getIdentifyNumber();
			    			break;
			    		}
					}
			    	buf.append("<INSURED_LIST><INSURED_DATA>");
			    	addNode(buf, "INSURED_NAME", insuredName);   	
			    	addNode(buf, "CERTI_TYPE", certiType);   		
			    	addNode(buf, "CERTI_CODE", certiCode);   		
			    	buf.append("</INSURED_DATA></INSURED_LIST>");
	    		}
	    	}
    }
    
    
    
    private String encodeSafeAdjust(String strAddonCount){
   	 if("1".equals(strAddonCount)){
   		 return "0.9";
   	 }else {
   		 return "1.0";
   	 }
    }
    private String encodeNonclaimAdjust(String strDamagedFactorGrade){
   	 if("11".equals(strDamagedFactorGrade)){
   		 return "0.7";
   	 }else if("12".equals(strDamagedFactorGrade)){
   		 return "0.8";
   	 }else if("13".equals(strDamagedFactorGrade)){
   		 return "0.9";
   	 }else if("14".equals(strDamagedFactorGrade)){
   		 return "1.0";
   	 }else if("15".equals(strDamagedFactorGrade)){
   		 return "1.1";
   	 }else if("16".equals(strDamagedFactorGrade)){
   		 return "1.2";
   	 }else if("17".equals(strDamagedFactorGrade)){
   		 return "1.3";
   	 }else{
   		 return "";
   	 }
    }
    private String encodeLoyaltyAdjust(String strOtherNature2){
   	 if("1".equals(strOtherNature2)){
   		 return "0.9";
   	 }else{
   		 return "1.0";
   	 }
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

    
    private String encoderVehicleCategory(BLPolicy blPolicy) 
        throws Exception 
    {
        String strVehicleCategory   = "";
        String strCarKindCode       = "";
        double exhaustScale         = 0;
        double toncount             = 0;
        int seatcount               = 0;
        String intUseType           = "";
		
		String strComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();
		
        intUseType  = blPolicy.getBLPrpCitemCar().getArr(0).getUseNatureCode();
        seatcount   = new Integer(blPolicy.getBLPrpCitemCar().getArr(0).getSeatCount()).intValue();
        toncount = Double.parseDouble(blPolicy.getBLPrpCitemCar().getArr(0).getTonCount());
        
        
        
        exhaustScale = Double.parseDouble(blPolicy.getBLPrpCitemCar().getArr(0).getExhaustScale());
        strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
        if (strCarKindCode.equals("A0")) {
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

        } else if (strCarKindCode.equals("H0") ||
                   strCarKindCode.equals("H1") ||
                   strCarKindCode.equals("H2")) {
        	
			if(SysConfig.getProperty("CI_SINOSOFT_0806").trim().indexOf(
					strComCode.substring(0, 2).trim()) > -1 && (strCarKindCode.equals("H1")
				|| strCarKindCode.equals("H2"))){
				if(strCarKindCode.equals("H1")){	
					strVehicleCategory = "93";
				}else if(strCarKindCode.equals("H2")){
					strVehicleCategory = "94";
				}
			} else if (toncount >= 0 && toncount < 2) {
                strVehicleCategory = "21";
            } else if (toncount >= 2 && toncount < 5) {
                strVehicleCategory = "22";
            } else if (toncount >= 5 && toncount < 10) {
                strVehicleCategory = "23";
            } else if (toncount >= 10) {
                strVehicleCategory = "24";
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
        else if (strCarKindCode.equals("TP") || strCarKindCode.equals("TQ")
                || strCarKindCode.equals("TR") || strCarKindCode.equals("TS")) {
            
            strVehicleCategory = "30";  
            
          
            
            
            
            
            
            
            
          
            
            
         }else if (strCarKindCode.equals("T1") ||
                   strCarKindCode.equals("T2") ||
                   strCarKindCode.equals("T3") ||
                   strCarKindCode.equals("T5")||
                   strCarKindCode.equals("TE")||
                   strCarKindCode.equals("TF")||
                   strCarKindCode.equals("TG")||
                   strCarKindCode.equals("TH")||
                   strCarKindCode.equals("TI")||
                   strCarKindCode.equals("TJ")||
                   strCarKindCode.equals("TK")||
                   strCarKindCode.equals("TL")||
                   strCarKindCode.equals("TM")||
                   strCarKindCode.equals("TN")||
                   strCarKindCode.equals("TD")||
                   strCarKindCode.equals("T7")) {
            

            strVehicleCategory = "40";
        } else if (strCarKindCode.equals("T4") ||
                   strCarKindCode.equals("T6") ||
                   strCarKindCode.equals("T8") ||
                   strCarKindCode.equals("T9")||
                   strCarKindCode.equals("TO")||
               strCarKindCode.equals("TU")||
               strCarKindCode.equals("TV")||
               strCarKindCode.equals("TW")||
               strCarKindCode.equals("TX")||
               strCarKindCode.equals("TY")||
               strCarKindCode.equals("TZ")||
               strCarKindCode.equals("TC")) {
            
            
            strVehicleCategory = "50";  
            
        } else if (strCarKindCode.equals("TT")) {
            
            strVehicleCategory = "60";
        } else if (strCarKindCode.equals("M0")||
                   strCarKindCode.equals("M1")||
                   strCarKindCode.equals("M3")||
                   strCarKindCode.equals("M4")) {
            
            if (exhaustScale >= 0 && exhaustScale <= 0.05) {
                strVehicleCategory = "71";
            } else if (exhaustScale > 0.05 && exhaustScale <= 0.25) {
                strVehicleCategory = "72";
            } else if (exhaustScale > 0.25) {
                strVehicleCategory = "73";
            }
    } else if (strCarKindCode.equals("M2")) {
            
            strVehicleCategory = "73";  
        } else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J1")||strCarKindCode.equals("J2")||strCarKindCode.equals("J3"))&&intUseType.trim().equals("7A")) {
            
            if (exhaustScale >= 0 && exhaustScale < 14.7) {
                strVehicleCategory = "81";
            } else if (exhaustScale >= 14.7) {
                strVehicleCategory = "82";
            }
        }else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J1")||strCarKindCode.equals("J2")||strCarKindCode.equals("J3"))&&intUseType.trim().equals("7B")) {
            
            if (exhaustScale >= 0 && exhaustScale <= 14.7) {
                strVehicleCategory = "91";
            } else if (exhaustScale > 14.7) {
                strVehicleCategory = "92";
            }

        }else if (strCarKindCode.equals("G1"))          
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
        
        /*
         * switch (seatcount){ case 0: {
         *
         * break; } }
         */

        return strVehicleCategory;
    }

    
    private String encoderVehicleCategoryNew(BLPolicy blPolicy) 
    throws Exception 
    {
        
        String strVehicleCategory   = "";
        String strCarKindCode       = "";
        double exhaustScale         = 0;
        double toncount             = 0;
        int seatcount               = 0;
        String intUseType           = "";
        intUseType  = blPolicy.getBLPrpCitemCar().getArr(0).getUseNatureCode();
        seatcount   = new Integer(blPolicy.getBLPrpCitemCar().getArr(0).getSeatCount()).intValue();
        toncount = Double.parseDouble(blPolicy.getBLPrpCitemCar().getArr(0).getTonCount());
        exhaustScale = Double.parseDouble(blPolicy.getBLPrpCitemCar().getArr(0).getExhaustScale());
        strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
		
		String strComCode = blPolicy.getBLPrpCmain().getArr(0).getComCode();
		
        
        logger.info("===============================================================");
        logger.info("=========================车辆种类=========================="+strCarKindCode);
        
        
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
                  strCarKindCode.equals("H8")) {
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
        	
        	if(new UtiPower().checkCIStartUp1001(blPolicy.getBLPrpCmain().getArr(0).getComCode(), blPolicy.getBLPrpCmain().getArr(0).getOperateDate()))
        	{
        		if(strCarKindCode.equals("H1")){
        			if(exhaustScale <=14.7){
        				strVehicleCategory = "BA";
        			}else if( exhaustScale > 14.7 && exhaustScale <= 17.6){
        				strVehicleCategory = "BB" ;
        			}else if(exhaustScale >17.6 && exhaustScale <= 50){
        				strVehicleCategory = "BC";
        			}else if(exhaustScale >50 && exhaustScale <= 80){
        				strVehicleCategory = "BD";
        			}else if(exhaustScale > 80){
        				strVehicleCategory = "BE";
        			}
        		}else if(strCarKindCode.equals("H2")){
        			if(exhaustScale <= 14.7){
        				strVehicleCategory = "CA";
        			}else if(exhaustScale >14.7 && exhaustScale <=17.6){
        				strVehicleCategory = "CB";
        			}else if(exhaustScale >17.6 && exhaustScale <=50){
        				strVehicleCategory = "CC";
        			}else if(exhaustScale > 50 && exhaustScale <= 80){
        				strVehicleCategory = "CD";
        			}else if(exhaustScale > 80){
        				strVehicleCategory = "CE";
        			}
        		}
        	}else{
            
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
        	
        }
        
        else if (strCarKindCode.equals("H11") || 
                strCarKindCode.equals("H12") ||
                strCarKindCode.equals("H21")||
                strCarKindCode.equals("H22"))   
        {
			if(strCarKindCode.equals("H11")){	
				strVehicleCategory = "BF";
			}else if(strCarKindCode.equals("H12")){
				strVehicleCategory = "BG";
			}else if(strCarKindCode.equals("H21")){
				strVehicleCategory = "CF";
			}else if(strCarKindCode.equals("H22")){
				strVehicleCategory = "CG";
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
                 strCarKindCode.equals("TQ")  || 
                 strCarKindCode.equals("TR")) {
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
            
            if("19".equals(strComCode.substring(0, 2))){
            	if(exhaustScale == 0.25){
            		strVehicleCategory = "73";
            	}
            }
            
        }
        
        else if(strCarKindCode.equals("M6"))
        {
            strVehicleCategory = "73";
        }
        
        
        
        else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J2"))&&intUseType.trim().equals("7A")) {   
            if (exhaustScale >= 0 && exhaustScale <= 14.7) {
                strVehicleCategory = "81";
            } else if (exhaustScale > 14.7) {
                strVehicleCategory = "82";
            }
        }
        else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J2"))&&intUseType.trim().equals("7B")) {
            
            if (exhaustScale >= 0 && exhaustScale <= 14.7) {
                strVehicleCategory = "91";
            } else if (exhaustScale > 14.7) {
                strVehicleCategory = "92";
            }
        }
        
        
        
        else if(strCarKindCode.equals("J3")){
        	if(exhaustScale <= 14.7){
        		strVehicleCategory = "9A";
        	}else if(exhaustScale >14.7 && exhaustScale <= 17.6){
        		strVehicleCategory = "9B";
        	}
        }
        
        else if(strCarKindCode.equals("J4")){
        	if(exhaustScale <= 14.7 ){
        		strVehicleCategory = "AA";
        	}else if(exhaustScale >14.7 && exhaustScale<= 17.6){
        		strVehicleCategory = "AB";
        	}else if(exhaustScale > 17.6 && exhaustScale <= 50){
        		strVehicleCategory = "AC";
        	}else if(exhaustScale > 50 && exhaustScale <=80){
        		strVehicleCategory = "AD";
        	}else if(exhaustScale > 80){
        		strVehicleCategory = "AE";
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
        
        
        
        /* delete by luogang 20100106 begin reason:按原规则计算*/
		String strRiskCode = blPolicy.getBLPrpCmain().getArr(0).getRiskCode();
        if(new UtiPower().checkCIInsureBJ(strRiskCode, strComCode)){      
        	if(strCarKindCode.equals("T12")){

        	}else if(strCarKindCode.equals("H1")){      
        		strVehicleCategory = "29";
        	}else if(strCarKindCode.equals("TS")){        

        	}
        }
        /*delete by luogang 20100106 end */
       
       


        return strVehicleCategory;
        /*
        else if (strCarKindCode.equals("M2")) {
            
            strVehicleCategory = "73";  
        } else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J1")||strCarKindCode.equals("J2")||strCarKindCode.equals("J3"))&&intUseType.trim().equals("7A")) {
            
            if (exhaustScale >= 0 && exhaustScale < 14.7) {
                strVehicleCategory = "81";
            } else if (exhaustScale >= 14.7) {
                strVehicleCategory = "82";
            }
        }else if ((strCarKindCode.equals("J0")||strCarKindCode.equals("J1")||strCarKindCode.equals("J2")||strCarKindCode.equals("J3"))&&intUseType.trim().equals("7B")) {
            
            if (exhaustScale >= 0 && exhaustScale <= 14.7) {
                strVehicleCategory = "91";
            } else if (exhaustScale > 14.7) {
                strVehicleCategory = "92";
            }

        }else if (strCarKindCode.equals("G1"))          
        {
            strVehicleCategory = "31";
        }

        return strVehicleCategory;*/
    }
    
    
    
    private String encoderUseType(BLPolicy blPolicy) throws Exception {
        String strUseType = "";
        String intUseType = "";
        intUseType = blPolicy.getBLPrpCitemCar().getArr(0).getUseNatureCode();
        String strCarKindCode = blPolicy.getBLPrpCitemCar().getArr(0).getCarKindCode();
        
        if(strCarKindCode.equals("A0"))
        {
            if(intUseType.trim().equals("8B"))          
            {
                strUseType = "220";
            }
            else if(intUseType.trim().equals("8C"))     
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
            else if(intUseType.trim().equals("9A"))     
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
        
        else if(strCarKindCode.equals("H0") ||
                strCarKindCode.equals("H1") ||
                strCarKindCode.equals("H2") ||
                strCarKindCode.equals("G0"))
        {
            if(intUseType.trim().equals("86") ||    
               intUseType.trim().equals("87") ||    
               intUseType.trim().equals("9D") ||    
               intUseType.trim().equals("9E") ||    
               intUseType.trim().equals("9A") ||    
               intUseType.trim().equals("9B") ||    
               intUseType.trim().equals("9C")       
               )
            {
                strUseType = "100";     
            }
            else if(
                    intUseType.trim().equals("8B") ||       
                    intUseType.trim().equals("8C") ||           
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
        	
	    	if(new UtiPower().checkSinoCIversion5(blPolicy.getBLPrpCmain().getArr(0).getComCode().substring(0, 2))){
	    		if("T".equals(strCarKindCode.substring(0,1))||strCarKindCode.equals("H1")||strCarKindCode.equals("H2")){
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
	    		else{
	    			strUseType = "000";
	    		}
	    	}else{
	    		strUseType = "000";
	    	}
	        
	       
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
    private String correctDate(String dateString) {
        String result = StringUtils.replace(dateString, "-", "");
        return result;
    }

    /**
     * 纠正日期时间格式
     *
     * @param dateString
     *            修正前的日期时间
     * @return 修正后的日期时间
     */
    private String correctDateTime(String dateString) {
        String result = correctDate(dateString);
        result = StringUtils.replace(dateString, " ", "");
        result = StringUtils.replace(dateString, ":", "");
        return result;
    }
    
    
    /**
     * 平台XXXXX种代码转换
     * @param strKindCode
     * @param strRiskCode
     * @return
     * @throws Exception
     */
    private String encodeCoverageCode(String strKindCode, String strRiskCode) throws Exception{
    	String strCoverageCode = "";
    	if("0509".equals(strRiskCode)){
    		 if("A".equals(strKindCode)){
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
    	     if("A".equals(strKindCode)){
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
    /**
     * 平台XXXXX种代码转换
     * @param strKindCode
     * @return
     * @throws Exception
     */
    private String encodeCoverageType(String strKindCode) throws Exception{
    	String strCoverageType ="";
    	 if("B".equals(strKindCode)){
     		 strCoverageType = "2";
     	 }else if("A".equals(strKindCode) || "D3".equals(strKindCode) 
     			 || "D4".equals(strKindCode) || "G1".equals(strKindCode)){
     		 strCoverageType = "3";
     	 }else{
     		 strCoverageType = "9";
     	 }
    	return strCoverageType;
    }
    
    
    
    
	
    private boolean checkTransUseType(String strCarKindCode){
    	boolean bReturn = false;
    	if(strCarKindCode != null && !strCarKindCode.equals("") && 
    			(strCarKindCode.charAt(0) == 'T' || strCarKindCode.charAt(0) == 'J' || 
    					strCarKindCode.charAt(0) == 'M' || strCarKindCode.equals("H0") || 
    					strCarKindCode.equals("H1"))){
    		bReturn = true;
    	}
    	return bReturn;
    }
    
    
    
    public void addVehicleTaxation(StringBuffer buf, BLPolicy blPolicy) throws Exception {
        EndorseCarShipTaxQueryEncoder endorseCarShipTaxQueryEncoder = new EndorseCarShipTaxQueryEncoder();
        endorseCarShipTaxQueryEncoder.encode(blPolicy,buf,"VehicleTaxation");
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
    	     }else if("G1".equals(strKindCode)){
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
   
    
    private String translate(String InsuredTypeCode) throws Exception{
    	String strInsuredTypeCode = InsuredTypeCode;
    	if("01".equals(InsuredTypeCode)){
    		strInsuredTypeCode = "01";
    	}else if("03".equals(InsuredTypeCode)){
    		strInsuredTypeCode = "02";
    	}else if("04".equals(InsuredTypeCode)){
    		strInsuredTypeCode = "03";
    	}else if("99".equals(InsuredTypeCode)){
    		strInsuredTypeCode = "10";
    	}else{
    		strInsuredTypeCode = "99";
    	}
    	return strInsuredTypeCode;
    }
    
	
	public String carDriverIdentifyTypeEncoder(String IdentifyType){
		if("03".equals(IdentifyType)){
			return "02";
		}else if("04".equals(IdentifyType)){
			return "03";
		}else if("02,05,06,07".indexOf(IdentifyType)>-1){
			return "99";
		}
		return IdentifyType;
	}
	
}