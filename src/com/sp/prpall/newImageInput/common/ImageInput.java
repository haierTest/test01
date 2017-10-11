package com.sp.prpall.newImageInput.common;

import com.sp.platform.dto.domain.PrpDriskConfigDto;
import com.sp.platform.ui.control.action.UIPrpDriskConfigAction;
import com.sp.prpall.blsvr.cb.BLPolicy;
import com.sp.prpall.blsvr.pg.BLEndorse;
import com.sp.prpall.blsvr.tb.BLProposal;
import com.sp.prpall.newImageInput.schema.ImageInputSchema;
import com.sp.prpall.schema.PrpCmainSchema;
import com.sp.prpall.schema.PrpPheadSchema;
import com.sp.prpall.schema.PrpPmainSchema;
import com.sp.prpall.schema.PrpTmainSchema;
import com.sp.utility.SysConfig;
import com.sp.prpall.pubfun.PubTools;
import com.sp.utility.string.Date;


public class ImageInput {
    
    public boolean isImageInputSaveCheck(BLProposal blProposal,String strEditType) throws Exception{
        boolean blnYxcdFlag = false;
        ImageInputSchema imageInputSchema = new ImageInputSchema();
        PrpTmainSchema prpTMainSchema = blProposal.getBLPrpTmain().getArr(0);
        
        imageInputSchema.setStrRiskCode(prpTMainSchema.getRiskCode());
        imageInputSchema.setStrComcode(prpTMainSchema.getComCode());
        imageInputSchema.setStrYxcdChannel(prpTMainSchema.getBusinessNature());
        imageInputSchema.setStrPolicyType(prpTMainSchema.getPolicyType());
        imageInputSchema.setStrChgPremium("0");
        imageInputSchema.setStrOperateSite(prpTMainSchema.getOperateSite());
        imageInputSchema.setStrEditType(strEditType);
        imageInputSchema.setStrStartDate(prpTMainSchema.getStartDate());
        imageInputSchema.setStrStartHour(prpTMainSchema.getStartHour());
        imageInputSchema.setStrEndDate(prpTMainSchema.getEndDate());
        imageInputSchema.setStrEndHour(prpTMainSchema.getEndHour());
        
        for(int i=0;i<blProposal.getBLPrpTinsured().getSize();i++){
            if("1".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredFlag()) || 
               "2".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredFlag()) ){
                if("2".equals(blProposal.getBLPrpTinsured().getArr(i).getInsuredType())){
                    imageInputSchema.setStrInsuredType(blProposal.getBLPrpTinsured().getArr(i).getInsuredType());
                    break;
                } 
            }
        }
        
        
        imageInputSchema.setStrProductCode(prpTMainSchema.getProductCode());
        
        boolean blnIsCode = false;
        PrpDriskConfigDto prpDriskConfigDtoKind = null; 
        
        boolean blnShortPeriod = false;
        
        String strShortPeriodSwitch = "";
        if("2700".equals(imageInputSchema.getStrRiskCode())){
            if(!"".equals(imageInputSchema.getStrProductCode()) && imageInputSchema.getStrProductCode().startsWith("P")){
                prpDriskConfigDtoKind = UIPrpDriskConfigAction.queryRiskConfig(imageInputSchema.getStrComcode(),
                                                                               imageInputSchema.getStrProductCode(),
                                                                               "YXCD_CASUALTY_KIND"); 
                
                if(prpDriskConfigDtoKind !=null && "1".equals(prpDriskConfigDtoKind.getConfigValue())){
                    blnIsCode = true;
                }
                
                strShortPeriodSwitch = SysConfig.getProperty("YXCD_CASUALTY_SHORTPERIOD_PRODUCT");
                if(strShortPeriodSwitch.indexOf(imageInputSchema.getStrProductCode())>-1){
                    blnShortPeriod = true;
                }
            }else{
                for(int i=0;i<blProposal.getBLPrpTitemKind().getSize();i++){
                    prpDriskConfigDtoKind = UIPrpDriskConfigAction.queryRiskConfig(imageInputSchema.getStrComcode(),
                                                                                   blProposal.getBLPrpTitemKind().getArr(i).getKindCode(),
                                                                                   "YXCD_CASUALTY_KIND"); 
                    
                    if(prpDriskConfigDtoKind !=null && "1".equals(prpDriskConfigDtoKind.getConfigValue())){
                        blnIsCode = true;
                    }
                    
                    strShortPeriodSwitch = SysConfig.getProperty("YXCD_CASUALTY_SHORTPERIOD_KIND");
                    if(strShortPeriodSwitch.indexOf(blProposal.getBLPrpTitemKind().getArr(i).getKindCode())>-1){
                        blnShortPeriod = true;
                    }
                }
            }
            imageInputSchema.setBlnKindCode(blnIsCode);
            imageInputSchema.setBlnShortPeriod(blnShortPeriod);
        }else{
            if("YAB0,2700".indexOf(imageInputSchema.getStrRiskCode()) == -1 
                 && "09,10".indexOf(imageInputSchema.getStrRiskCode().substring(0,2)) == -1){
                
                strShortPeriodSwitch = SysConfig.getProperty("YXCD_PROP_SHORTPERIOD");
                if(strShortPeriodSwitch.indexOf(blProposal.getBLPrpTmain().getArr(0).getRiskCode())>-1){
                    blnShortPeriod = true;
                }
            }
            imageInputSchema.setBlnShortPeriod(blnShortPeriod);
        }
        
        
        boolean blnClauseCode = false;
        for(int i=0;i<blProposal.getBLPrpTengage().getSize();i++){
            if("T0084".equals(blProposal.getBLPrpTengage().getArr(i).getClauseCode())){
                blnClauseCode = true;
                break;
            }
        }
        imageInputSchema.setBlnClauseCode(blnClauseCode);
        
        blnYxcdFlag = isImageInputProposalSave(imageInputSchema);
        return blnYxcdFlag;
    }
    
    /**
     * @description 批改时封装集中录单校验对象
     * @author gaohaifeng 20110303
     * @param  blEndorse  批单大对象
     * @param  blPolicy   XX大对象
     * @throws Exception 
     */
    public boolean isImageInputSaveCheck(BLEndorse blEndorse,BLPolicy blPolicy) throws Exception{
        boolean blYxcdFlag = false;
        ImageInputSchema imageInputSchema = new ImageInputSchema();
        PrpPmainSchema prpPMainSchema = blEndorse.getBLPrpPmain().getArr(0);
        PrpCmainSchema prpCMainSchema = blPolicy.getBLPrpCmain().getArr(0);
        PrpPheadSchema prpPhead = blEndorse.getBLPrpPhead().getArr(0);
        
        imageInputSchema.setStrRiskCode(prpCMainSchema.getRiskCode());
        imageInputSchema.setStrComcode(prpCMainSchema.getComCode());
        imageInputSchema.setStrYxcdChannel(prpCMainSchema.getBusinessNature());
        imageInputSchema.setStrPolicyType(prpCMainSchema.getPolicyType());
        imageInputSchema.setStrChgPremium(prpPMainSchema.getChgPremium());
        imageInputSchema.setStrOperateSite(prpCMainSchema.getOperateSite());
        imageInputSchema.setStrEditType("P");
        imageInputSchema.setStrStartDate(prpCMainSchema.getStartDate());
        imageInputSchema.setStrStartHour(prpCMainSchema.getStartHour());
        imageInputSchema.setStrEndDate(prpCMainSchema.getEndDate());
        imageInputSchema.setStrEndHour(prpCMainSchema.getEndHour());
        
        if(prpPhead.getFlag().length() >= 6){
            imageInputSchema.setStrflag(prpPhead.getFlag().substring(5,6)); 
        }else{
            imageInputSchema.setStrflag("");
        }
        
        
        for(int i=0;i<blPolicy.getBLPrpCinsured().getSize();i++){
            if("1".equals(blPolicy.getBLPrpCinsured().getArr(i).getInsuredFlag()) || 
               "2".equals(blPolicy.getBLPrpCinsured().getArr(i).getInsuredFlag()) ){
                if("2".equals(blPolicy.getBLPrpCinsured().getArr(i).getInsuredType())){
                    imageInputSchema.setStrInsuredType(blPolicy.getBLPrpCinsured().getArr(i).getInsuredType());
                    break;
                } 
            }
            
        }

        
        imageInputSchema.setStrProductCode(prpCMainSchema.getProductCode());
        
        boolean blnIsCode = false;
        PrpDriskConfigDto prpDriskConfigDtoKind = null;
        
        
        boolean blnShortPeriod = false;
        String strShortPeriodSwitch = "";
        if("2700".equals(imageInputSchema.getStrRiskCode())){
            if(!"".equals(imageInputSchema.getStrProductCode()) && imageInputSchema.getStrProductCode().startsWith("P")){
                prpDriskConfigDtoKind = UIPrpDriskConfigAction.queryRiskConfig(imageInputSchema.getStrComcode(),
                                                                               imageInputSchema.getStrProductCode(),
                                                                               "YXCD_CASUALTY_KIND"); 
                
                if(prpDriskConfigDtoKind !=null && "1".equals(prpDriskConfigDtoKind.getConfigValue())){
                    blnIsCode = true;
                }
                
                strShortPeriodSwitch = SysConfig.getProperty("YXCD_CASUALTY_SHORTPERIOD_PRODUCT");
                if(strShortPeriodSwitch.indexOf(imageInputSchema.getStrProductCode())>-1){
                    blnShortPeriod = true;
                }
            }else{
                for(int i=0;i<blPolicy.getBLPrpCitemKind().getSize();i++){
                    prpDriskConfigDtoKind = UIPrpDriskConfigAction.queryRiskConfig(imageInputSchema.getStrComcode(),
                                                                                   blPolicy.getBLPrpCitemKind().getArr(i).getKindCode(),
                                                                                   "YXCD_CASUALTY_KIND"); 
                    
                    if(prpDriskConfigDtoKind !=null && "1".equals(prpDriskConfigDtoKind.getConfigValue())){
                        blnIsCode = true;
                    }
                    
                    strShortPeriodSwitch = SysConfig.getProperty("YXCD_CASUALTY_SHORTPERIOD_KIND");
                    if(strShortPeriodSwitch.indexOf(blPolicy.getBLPrpCitemKind().getArr(i).getKindCode())>-1){
                        blnShortPeriod = true;
                    }
                }
            }
            imageInputSchema.setBlnKindCode(blnIsCode);
            imageInputSchema.setBlnShortPeriod(blnShortPeriod);
        }else{
            if("YAB0,2700".indexOf(imageInputSchema.getStrRiskCode()) == -1 
                    && "09,10".indexOf(imageInputSchema.getStrRiskCode().substring(0,2)) == -1){
                   
                   strShortPeriodSwitch = SysConfig.getProperty("YXCD_PROP_SHORTPERIOD");
                   if(strShortPeriodSwitch.indexOf(blPolicy.getBLPrpCmain().getArr(0).getRiskCode())>-1){
                       blnShortPeriod = true;
                   }
               }
               imageInputSchema.setBlnShortPeriod(blnShortPeriod);
        }
        
        
        boolean blnClauseCode = false;
        for(int i=0;i<blPolicy.getBLPrpCengage().getSize();i++){
            if("T0084".equals(blPolicy.getBLPrpCengage().getArr(i).getClauseCode())){
                blnClauseCode = true;
                break;
            }
        }
        imageInputSchema.setBlnClauseCode(blnClauseCode);
        
        blYxcdFlag = isImageInputEndorseSave(imageInputSchema);
        return blYxcdFlag;
    }
    
    /**
     * @description 校验是否为集中出单业务短期与特约控制
     * @author gaohaifeng 20110401
     * @param  imageInputSchema 封装好校验数据的对象
     * @return 集中出单标识  true：集中出单业务不允许XXXXX存，false：正常XXXXX存
     * @throws Exception 
     */
    public boolean checkShortPeriodAndClause(ImageInputSchema imageInputSchema)throws Exception{
        Date startDate = PubTools.stringToDate(imageInputSchema.getStrStartDate());
        int intStartHour = Integer.parseInt(imageInputSchema.getStrStartHour());
        Date endDate = PubTools.stringToDate(imageInputSchema.getStrEndDate());
        int intEndHour = Integer.parseInt(imageInputSchema.getStrEndHour());
        
        PrpDriskConfigDto prpDriskConfigDtoClause = UIPrpDriskConfigAction.queryRiskConfig(imageInputSchema.getStrComcode(),
                                                                   imageInputSchema.getStrRiskCode(),
                                                                   "YXCD_CLAUSE_SWITCH");
        
        if(prpDriskConfigDtoClause == null){
            prpDriskConfigDtoClause = UIPrpDriskConfigAction.queryRiskConfig(imageInputSchema.getStrComcode(),
                                                                             "0000",
                                                                             "YXCD_CLAUSE_SWITCH");
        }
        boolean blnProShortPeriodAndClause = false;
        
        if((PubTools.getMonthMinus(startDate, intStartHour, endDate, intEndHour) >= 12) ||
           (PubTools.getMonthMinus(startDate, intStartHour, endDate, intEndHour) < 12 && imageInputSchema.getBlnShortPeriod())){
            if(imageInputSchema.getBlnClauseCode()){
                
                if(prpDriskConfigDtoClause != null && "0".equals(prpDriskConfigDtoClause.getConfigValue())){
                    blnProShortPeriodAndClause = true;
                }
            }else{
                blnProShortPeriodAndClause = true;    
            }           
        }
        return blnProShortPeriodAndClause;
    }
    /**
     * @description 校验XX单是否为集中出单业务
     * @author gaohaifeng 20110303
     * @param  imageInputSchema 封装好校验数据的对象
     * @return 集中出单标识  true：集中出单业务不允许XXXXX存，false：正常XXXXX存
     * @throws Exception 
     */
    public boolean isImageInputProposalSave(ImageInputSchema imageInputSchema) throws Exception{
        boolean blnYxcdFlag = false;
        
        
        PrpDriskConfigDto prpDriskConfigDtoSwitch = UIPrpDriskConfigAction.queryRiskConfig(imageInputSchema.getStrComcode(),
                                                                   imageInputSchema.getStrRiskCode(),
                                                                   "YXCD_SWITCH");
        
        
        PrpDriskConfigDto prpDriskConfigDtoChannel = UIPrpDriskConfigAction.queryRiskConfig(imageInputSchema.getStrComcode(),
                                                                   imageInputSchema.getStrRiskCode(),
                                                                   "YXCD_CHANNEL");
        
        if(prpDriskConfigDtoChannel == null){
            prpDriskConfigDtoChannel = UIPrpDriskConfigAction.queryRiskConfig(imageInputSchema.getStrComcode(),
                                                                   "0000",
                                                                   "YXCD_CHANNEL");
        }
        
        
        if(!"JZLD".equals(imageInputSchema.getStrOperateSite())){	
        
            if(prpDriskConfigDtoSwitch != null && "1".equals(prpDriskConfigDtoSwitch.getConfigValue())){
                if((prpDriskConfigDtoChannel == null) || 
                   (prpDriskConfigDtoChannel != null && prpDriskConfigDtoChannel.getConfigValue().indexOf(","+imageInputSchema.getStrYxcdChannel()+",")>-1)){
                    
                    if("2700".equals(imageInputSchema.getStrRiskCode()) && "02".equals(imageInputSchema.getStrPolicyType()) 
                       && imageInputSchema.getBlnKindCode()){
                        if(checkShortPeriodAndClause(imageInputSchema)){
                            blnYxcdFlag = true;
                        }
                    }else if("YAB0,2700".indexOf(imageInputSchema.getStrRiskCode()) == -1 
                             && "09,10".indexOf(imageInputSchema.getStrRiskCode().substring(0,2)) == -1
                             && "2".equals(imageInputSchema.getStrInsuredType())){
                        if(checkShortPeriodAndClause(imageInputSchema)){
                            blnYxcdFlag = true;
                        }
                    }
                }
            }
        }
        return blnYxcdFlag;
        
    }
    /**
     * @description 校验批单是否为集中出单业务
     * @author gaohaifeng 20110303
     * @param  imageInputSchema 封装好校验数据的对象
     * @return 集中出单标识  true：集中出单业务不允许XXXXX存，false：正常XXXXX存
     * @throws Exception 
     */
    public boolean isImageInputEndorseSave(ImageInputSchema imageInputSchema) throws Exception{
        boolean blnYxcdFlag = false;
        
        
        PrpDriskConfigDto prpDriskConfigDtoSwitch = UIPrpDriskConfigAction.queryRiskConfig(imageInputSchema.getStrComcode(),
                                                                   imageInputSchema.getStrRiskCode(),
                                                                   "YXCD_ENDORSE_SWITCH");
        
        if(prpDriskConfigDtoSwitch == null){
            prpDriskConfigDtoSwitch = UIPrpDriskConfigAction.queryRiskConfig(imageInputSchema.getStrComcode(),
                                                                             "0000",
                                                                             "YXCD_ENDORSE_SWITCH");
        }
        
        PrpDriskConfigDto prpDriskConfigDtoChannel = UIPrpDriskConfigAction.queryRiskConfig(imageInputSchema.getStrComcode(),
                                                                   imageInputSchema.getStrRiskCode(),
                                                                   "YXCD_CHANNEL");
        
        if(prpDriskConfigDtoChannel == null){
            prpDriskConfigDtoChannel = UIPrpDriskConfigAction.queryRiskConfig(imageInputSchema.getStrComcode(),
                                                                   "0000",
                                                                   "YXCD_CHANNEL");
        }
        
        String strChgEndorseSwitch = SysConfig.getProperty("YXCD_CHG_ENDORSE_SWITCH");
        
        if("JZLD".equals(imageInputSchema.getStrOperateSite()) && "1".equals(imageInputSchema.getStrflag())){
            if(prpDriskConfigDtoSwitch != null && "1".equals(prpDriskConfigDtoSwitch.getConfigValue())){
                if((prpDriskConfigDtoChannel == null)||
                   (prpDriskConfigDtoChannel != null && prpDriskConfigDtoChannel.getConfigValue().indexOf(","+imageInputSchema.getStrYxcdChannel()+",")>-1)){
                    
                    if("2700".equals(imageInputSchema.getStrRiskCode())&&"02".equals(imageInputSchema.getStrPolicyType()) && imageInputSchema.getBlnKindCode()){
                        if(checkShortPeriodAndClause(imageInputSchema)){
                            if(0 == Double.parseDouble(imageInputSchema.getStrChgPremium())){
                                blnYxcdFlag = true;
                            }else{
                                if("1".equals(strChgEndorseSwitch)){
                                    blnYxcdFlag = true;
                                }
                            } 
                        }
                    }else if("YAB0,2700".indexOf(imageInputSchema.getStrRiskCode()) == -1 && "09,10".indexOf(imageInputSchema.getStrRiskCode().substring(0,2)) == -1
                              && "2".equals(imageInputSchema.getStrInsuredType())){
                        if(checkShortPeriodAndClause(imageInputSchema)){
                            if(0 == Double.parseDouble(imageInputSchema.getStrChgPremium())){
                                blnYxcdFlag = true;
                            }else{
                                if("1".equals(strChgEndorseSwitch)){
                                    blnYxcdFlag = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return blnYxcdFlag;
        
    }
}
