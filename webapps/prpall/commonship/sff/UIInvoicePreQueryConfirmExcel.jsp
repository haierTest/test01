<%@page errorPage="/UIErrorPage"%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page import="com.sp.utility.string.Money"%>
<%@page import="com.sp.utility.error.UserException"%>
<%@page import="com.sp.payment.utility.StrEx"%>
<%@page import="com.sp.prpall.blsvr.jf.BLPrpJplanFee"%>
<%@page import="com.sp.payment.blsvr.jf.SFFPower"%>
<%@page import="com.sp.prpall.dbsvr.jf.DBPrpJplanFee"%>
<%@page import="com.sp.prpall.schema.PrpJplanFeeSchema"%>
<%@page import="com.sp.utiall.blsvr.BLPrpDuser"%>
<%@page import="com.sp.platform.ui.control.action.UIPowerAction"%>
<%@page import="com.sp.platform.dto.domain.PrpDuserDto"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.sp.prpall.blsvr.jf.BLPrpJInvoicePre"%>
<%@page import="com.sp.prpall.dbsvr.jf.DBPrpJInvoicePre"%>
<%@page import="com.sp.prpall.schema.PrpJInvoicePreSchema"%>
<%@page import="com.sp.utility.SysConfig"%>
<%@page import="com.sp.utility.error.UserException"%>
<%@page import="com.sp.utility.string.*"%>
<%@page import="com.sp.utiall.blsvr.*"%>
<%@page import="com.sp.utiall.blsvr.*"%>
<%@page import="com.sp.payment.blsvr.jf.*"%>
<%@page import="com.sp.payment.dbsvr.jf.*"%>

<%@page import="com.sp.prpall.blsvr.jf.CreateExcelOfZIP"%>

<% 
  PrpDuserDto user = (PrpDuserDto) (session.getAttribute("user"));
  BLPrpJplanFee blPrpJplanFee = new BLPrpJplanFee();
  ArrayList bindValues = new ArrayList();
  String[] strCertiType = request.getParameterValues("CertiType");
  String[] strCertiNo = request.getParameterValues("CertiNo");
  String[] strSerialNo = request.getParameterValues("SerialNo");
  String[] strPayrefReason = request.getParameterValues("PayRefReason");
  String[] strConditionNew = request.getParameterValues("Conditions");
  String strFromPage = request.getParameter("frompage");
  
  String[] strPlanRevertDate = request.getParameterValues("PlanRevertDate");
  
  String strCondition = "";
  
  BLPrpJplanFee blPrpJplanFeeNew = new BLPrpJplanFee();
 
  
  
 
   for(int i=0;i<strCertiNo.length;i++){
	  PrpJplanFeeSchema prpJplanFeeSchema = new PrpJplanFeeSchema();
	  
	  
	  
	  
	  
	  
	  blPrpJplanFee.getNewInfo(strCertiType[i],strCertiNo[i],strSerialNo[i],strPayrefReason[i]);
	 
 	  prpJplanFeeSchema.setCertiType(blPrpJplanFee.getArr(0).getCertiType());
      prpJplanFeeSchema.setCertiNo(blPrpJplanFee.getArr(0).getCertiNo());
      prpJplanFeeSchema.setPolicyNo(blPrpJplanFee.getArr(0).getPolicyNo());
      prpJplanFeeSchema.setSerialNo(blPrpJplanFee.getArr(0).getSerialNo());
      prpJplanFeeSchema.setPayRefReason(blPrpJplanFee.getArr(0).getPayRefReason());
      prpJplanFeeSchema.setClaimNo(blPrpJplanFee.getArr(0).getClaimNo());
      prpJplanFeeSchema.setClassCode(blPrpJplanFee.getArr(0).getClassCode());
      prpJplanFeeSchema.setRiskCode(blPrpJplanFee.getArr(0).getRiskCode());
      prpJplanFeeSchema.setContractNo(blPrpJplanFee.getArr(0).getContractNo());
      prpJplanFeeSchema.setAppliCode(blPrpJplanFee.getArr(0).getAppliCode());
      prpJplanFeeSchema.setAppliName(blPrpJplanFee.getArr(0).getAppliName());
      prpJplanFeeSchema.setInsuredCode(blPrpJplanFee.getArr(0).getInsuredCode());
      prpJplanFeeSchema.setInsuredName(blPrpJplanFee.getArr(0).getInsuredName());
      prpJplanFeeSchema.setStartDate(blPrpJplanFee.getArr(0).getStartDate());
      prpJplanFeeSchema.setEndDate(blPrpJplanFee.getArr(0).getEndDate());
      prpJplanFeeSchema.setValidDate(blPrpJplanFee.getArr(0).getValidDate());
      prpJplanFeeSchema.setPayNo(blPrpJplanFee.getArr(0).getPayNo());
      prpJplanFeeSchema.setCurrency1(blPrpJplanFee.getArr(0).getCurrency1());
      prpJplanFeeSchema.setPlanFee(blPrpJplanFee.getArr(0).getPlanFee());
      prpJplanFeeSchema.setPlanDate(blPrpJplanFee.getArr(0).getPlanDate());
      prpJplanFeeSchema.setComCode(blPrpJplanFee.getArr(0).getComCode());
      prpJplanFeeSchema.setMakeCom(blPrpJplanFee.getArr(0).getMakeCom());
      prpJplanFeeSchema.setAgentCode(blPrpJplanFee.getArr(0).getAgentCode());
      prpJplanFeeSchema.setHandler1Code(blPrpJplanFee.getArr(0).getHandler1Code());
      prpJplanFeeSchema.setHandlerCode(blPrpJplanFee.getArr(0).getHandlerCode());
      prpJplanFeeSchema.setUnderWriteDate(blPrpJplanFee.getArr(0).getUnderWriteDate());
      prpJplanFeeSchema.setCoinsFlag(blPrpJplanFee.getArr(0).getCoinsFlag());
      prpJplanFeeSchema.setCoinsCode(blPrpJplanFee.getArr(0).getCoinsCode());
      prpJplanFeeSchema.setCoinsName(blPrpJplanFee.getArr(0).getCoinsName());
      prpJplanFeeSchema.setCoinsType(blPrpJplanFee.getArr(0).getCoinsType());
      prpJplanFeeSchema.setCenterCode(blPrpJplanFee.getArr(0).getCenterCode());
      prpJplanFeeSchema.setBranchCode(blPrpJplanFee.getArr(0).getBranchCode());
      prpJplanFeeSchema.setAccBookType(blPrpJplanFee.getArr(0).getAccBookType());
      prpJplanFeeSchema.setAccBookCode(blPrpJplanFee.getArr(0).getAccBookCode());
      prpJplanFeeSchema.setYearMonth(blPrpJplanFee.getArr(0).getYearMonth());
      prpJplanFeeSchema.setVoucherNo(blPrpJplanFee.getArr(0).getVoucherNo());
      prpJplanFeeSchema.setExchangeRate(blPrpJplanFee.getArr(0).getExchangeRate());
      prpJplanFeeSchema.setPlanFeeCNY(blPrpJplanFee.getArr(0).getPlanFeeCNY());
      prpJplanFeeSchema.setPayRefFee(blPrpJplanFee.getArr(0).getPayRefFee());
      prpJplanFeeSchema.setRealPayRefFee(blPrpJplanFee.getArr(0).getRealPayRefFee());
      prpJplanFeeSchema.setFlag(blPrpJplanFee.getArr(0).getFlag());
      prpJplanFeeSchema.setCarNatureCode(blPrpJplanFee.getArr(0).getCarNatureCode());
      prpJplanFeeSchema.setUseNatureCode(blPrpJplanFee.getArr(0).getUseNatureCode());
      prpJplanFeeSchema.setCarProperty(blPrpJplanFee.getArr(0).getCarProperty());
      prpJplanFeeSchema.setFlag1(blPrpJplanFee.getArr(0).getFlag1());
      prpJplanFeeSchema.setFlag2(blPrpJplanFee.getArr(0).getFlag2());
      prpJplanFeeSchema.setFlag3(blPrpJplanFee.getArr(0).getFlag3());
      prpJplanFeeSchema.setTaxSettleFlag(blPrpJplanFee.getArr(0).getTaxSettleFlag());
      prpJplanFeeSchema.setTaxSettleDate(blPrpJplanFee.getArr(0).getTaxSettleDate());
      prpJplanFeeSchema.setPayRefDate(blPrpJplanFee.getArr(0).getPayRefDate());
      prpJplanFeeSchema.setTaxpayerCode(blPrpJplanFee.getArr(0).getTaxpayerCode());
      prpJplanFeeSchema.setTaxpayerName(blPrpJplanFee.getArr(0).getTaxpayerName());
      prpJplanFeeSchema.setIdentifyNumber(blPrpJplanFee.getArr(0).getIdentifyNumber());
      prpJplanFeeSchema.setOperateSequence(blPrpJplanFee.getArr(0).getOperateSequence());
      prpJplanFeeSchema.setBusinessNature(blPrpJplanFee.getArr(0).getBusinessNature());
      prpJplanFeeSchema.setPoaType(blPrpJplanFee.getArr(0).getPoaType());
      prpJplanFeeSchema.setPoaCode(blPrpJplanFee.getArr(0).getPoaCode());
      prpJplanFeeSchema.setPoaDate(blPrpJplanFee.getArr(0).getPoaDate());
      prpJplanFeeSchema.setProposalNo(blPrpJplanFee.getArr(0).getProposalNo());
      prpJplanFeeSchema.setToStatus(blPrpJplanFee.getArr(0).getToStatus());
      prpJplanFeeSchema.setToCenterCode(blPrpJplanFee.getArr(0).getToCenterCode());
      prpJplanFeeSchema.setToComCode(blPrpJplanFee.getArr(0).getToComCode());
      prpJplanFeeSchema.setToUserCode(blPrpJplanFee.getArr(0).getToUserCode());
      prpJplanFeeSchema.setToDesc(blPrpJplanFee.getArr(0).getToDesc());
      prpJplanFeeSchema.setAutoRefFlag(blPrpJplanFee.getArr(0).getAutoRefFlag());
      prpJplanFeeSchema.setInvPrintConFlag(blPrpJplanFee.getArr(0).getInvPrintConFlag());
      prpJplanFeeSchema.setInvPrintFlag(blPrpJplanFee.getArr(0).getInvPrintFlag());
      prpJplanFeeSchema.setInvPrintTimes(blPrpJplanFee.getArr(0).getInvPrintTimes());
      prpJplanFeeSchema.setPayBankId(blPrpJplanFee.getArr(0).getPayBankId());
      prpJplanFeeSchema.setPrpCplanSerialno(blPrpJplanFee.getArr(0).getPrpCplanSerialno());
      prpJplanFeeSchema.setChannelType(blPrpJplanFee.getArr(0).getChannelType());
      
      
      
      prpJplanFeeSchema.setLicenseNo(blPrpJplanFee.getArr(0).getLicenseNo());
      prpJplanFeeSchema.setOperatorCode(blPrpJplanFee.getArr(0).getOperatorCode());
      prpJplanFeeSchema.setAppliType(blPrpJplanFee.getArr(0).getAppliType());
      
      prpJplanFeeSchema.setInPutDate(blPrpJplanFee.getArr(0).getInPutDate());
      prpJplanFeeSchema.setApproveStatus(blPrpJplanFee.getArr(0).getApproveStatus());
      prpJplanFeeSchema.setPackageNo(blPrpJplanFee.getArr(0).getPackageNo());
      prpJplanFeeSchema.setPackageDate(strPlanRevertDate[i]);
      blPrpJplanFeeNew.setArr(prpJplanFeeSchema);
      
  } 
   
   
  CreateExcelOfZIP createExcelOfZIP = new CreateExcelOfZIP();
  createExcelOfZIP.createExcelYJFP(request,response,blPrpJplanFeeNew,strFromPage);
  
%>





















