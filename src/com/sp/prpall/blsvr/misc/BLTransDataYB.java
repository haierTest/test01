package com.sp.prpall.blsvr.misc;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sp.platform.dto.domain.BankInterFace_DetailDto;
import com.sp.platform.resource.dtofactory.domain.DBBankInterFace_Detail;
import com.sp.sysframework.reference.DBManager;
import com.sp.utiall.blsvr.BLPrpDcode;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;

public class BLTransDataYB {
	
	public BLTransDataYB() {
	}
    /** ��XXXXXͨת���ܷ���
     * @param strBankCode
     * @param strRiskCode
     * @param strEiesSource
     * @param strComCode
     * @return strReturnMessage
     * @throws Exception
     */
    public String transData(HttpServletRequest request,HttpServletResponse response,String iBankCode, String iRiskCode, 
    		String iEiesSource, String iComCode, String iVscode)throws Exception
    {
      DbPool dbPool = new DbPool();
      ArrayList bankInterFace_DetailDtoList = null;
      String strPolicyNo = "";
      ArrayList policyNoList = new ArrayList();
      String strReturnMessage = "";
      String strSessionId = request.getSession().getId();
      ArrayList resultList = new ArrayList();
      try {
        dbPool.open(SysConfig.CONST_DDCCDATASOURCE);
        bankInterFace_DetailDtoList = findDataBD(dbPool,iBankCode, iRiskCode, iEiesSource, iComCode);
        for (int i = 0; i < bankInterFace_DetailDtoList.size(); i++) 
        {
          try{
            BankInterFace_DetailDto bankInterFace_DetailDto = null;
            bankInterFace_DetailDto = (BankInterFace_DetailDto) bankInterFace_DetailDtoList.get(i);
            if("3006".equals(bankInterFace_DetailDto.getRiskCode())||"3007".equals(bankInterFace_DetailDto.getRiskCode()))
            {
              BLTransDataYB30 blTransDataYB30 = new BLTransDataYB30();
              strReturnMessage += blTransDataYB30.transData(dbPool,strSessionId, bankInterFace_DetailDto, iEiesSource, iVscode);					
            }else if("0302".equals(bankInterFace_DetailDto.getRiskCode()))
            {
              BLTransDataYB0302 blTransDataYB0302 = new BLTransDataYB0302();
              strReturnMessage += blTransDataYB0302.transData(dbPool,bankInterFace_DetailDto, iEiesSource, iVscode);
            }else if("2700".equals(bankInterFace_DetailDto.getRiskCode()))
            {
             BLTransDataYB2700 blTransDataYB2700 = new BLTransDataYB2700();
             strReturnMessage += blTransDataYB2700.transData(dbPool,bankInterFace_DetailDto, iEiesSource, iVscode);
            }
            String strRevolutionflag = bankInterFace_DetailDto.getRevolutionFlag();
            strPolicyNo = bankInterFace_DetailDto.getPolicyno();
            String strResult = "�쳣";
            if("1".equals(strRevolutionflag)){
              strResult = "�ɹ�";
            }else{
              strResult = "ʧ��";
            }
            policyNoList.add(strPolicyNo);
            resultList.add(strResult);                  
          }catch (Exception e) {
        	  e.printStackTrace();
        	  throw e;
      	  } 
	    }
        if(strReturnMessage.trim().length()>0){
        	strReturnMessage = "����XX����XX�쳣������ϵϵͳ����Ա��";
        }
        if(bankInterFace_DetailDtoList.size() <= 0) 
        {
          strReturnMessage = "û�п����ɵ�XX����XX��";
        } else {
          request.setAttribute("bankInterFace_DetailDtoList", bankInterFace_DetailDtoList);
          request.setAttribute("policyNoList", policyNoList);
          request.setAttribute("resultList", resultList);
        }
		  } catch (Exception e) {
			  e.printStackTrace();
			  throw e;
		  } finally {
			  dbPool.close();
		  }
    	
		  return strReturnMessage;
    	
    }
	/** ��ȡ�ӿڱ�Bankinterface_detail����
	 * @param dbManager
	 * @param strBankCode
	 * @param strRiskCode
	 * @param strEiesSource
	 * @param strComCode
	 * @return bankInterFace_DetailDtoList
	 * @throws Exception
	 */
  public ArrayList findDataBD(DbPool dbPool,String strBankCode, String strRiskCode, String strEiesSource,
    String strComCode) throws Exception {
    DBManager dbManager = null;
    dbManager = dbPool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);
    ArrayList bankInterFace_DetailDtoList = null;
    String strCondition = "";
    if ("Eies".equals(strEiesSource)){
			strCondition = " RevolutionFlag='0'" 
					+ " AND Invalid='0'" 
					+ " AND BfBankCode='" + strBankCode + "'" 
					+ " AND RiskCode='" + strRiskCode + "'" 
					+ " AND IsEiesFlag='1'" + " AND ComCode ='" + strComCode + "'"
					+ " ORDER BY UploadFileSeq,Trans_Seq"; 
    }
		
    else if ("WYEies".equals(strEiesSource)) {
      strCondition = " RevolutionFlag='0'" 
					+ " AND Invalid='0'" 
					+ " AND BfBankCode='" + strBankCode + "'" 
					+ " AND RiskCode='" + strRiskCode + "'" 
					+ " AND IsEiesFlag='2'" + " AND ComCode ='" + strComCode + "'"
					+ " ORDER BY UploadFileSeq,Trans_Seq"; 
    }
		
    else if ("ABC".equals(strEiesSource)) {
      strCondition = " RevolutionFlag='0'" 
					+ " AND Invalid='0'" 
					+ " AND RiskCode='" + strRiskCode + "'" 
					+ " AND bankCode='103'" + " AND ComCode ='" + strComCode + "'"
					/*ӦXXXXXҪ��˴�HandlerCode����XX��Ա����Ҫ���κ���Ȩ�޵���Ա����������XX*/
					/*+ " AND HandlerCode='" + userCode + "'" 
					/* �˴���[ORDER BY UploadFileSeq]�ǳ���Ҫ����Ϊjsp����ʾ����������˳�� */
					+ " ORDER BY UploadFileSeq,Trans_Seq"; 
    }
    
    else if("CCB".equals(strEiesSource)){
        strCondition = " RevolutionFlag='0'" 
            + " AND Invalid='0'" 
            + " AND BfBankCode='" + strBankCode + "'"
            + " AND RiskCode='" + strRiskCode + "'" 
            + " AND IsEiesFlag='1'" + " AND ComCode ='" + strComCode + "'"
            + " ORDER BY UploadFileSeq,Trans_Seq"; 
    }
    
    else {
      strCondition = " RevolutionFlag='0'" 
					+ " AND Invalid='0'" 
					+ " AND RiskCode='" + strRiskCode + "'" 
					+ " AND IsEiesFlag='0'" + " AND ComCode like'"
					+ strComCode.substring(0, 2) + "%'" 
					+ " ORDER BY UploadFileSeq,Trans_Seq"; 
    }
    try {
      DBBankInterFace_Detail dbBankInterFace_Detail = new DBBankInterFace_Detail(dbManager);
      bankInterFace_DetailDtoList = (ArrayList) dbBankInterFace_Detail.findByConditions(strCondition, 1, 100);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return bankInterFace_DetailDtoList;

  }
	
	/**��ȡѡ��ҳ�������б�����д��롢
	 * @return
	 * @throws Exception 
	 * @throws UserException 
	 */
  public void queryList(HttpServletRequest request,HttpServletResponse response) throws UserException, Exception{
    BLPrpDcode blPrpDcodeBank = new BLPrpDcode();
    String strWherePart1 = " CodeType='BankYinbao' And validstatus='1' ";
    blPrpDcodeBank.query(strWherePart1,0);
    BLPrpDcode blPrpDcodeRisk = new BLPrpDcode();
    String strWherePart2 = " CodeType='RiskCodeYB' And validstatus='1' ";
    blPrpDcodeRisk.query(strWherePart2,0);
    BLPrpDcode blPrpDcodeSource = new BLPrpDcode();
    String strWherePart3 = " CodeType='EiesSource' And validstatus='1' ";
    blPrpDcodeSource.query(strWherePart3,0);
    request.setAttribute("blPrpDcodeBank", blPrpDcodeBank);
    request.setAttribute("blPrpDcodeRisk", blPrpDcodeRisk);
    request.setAttribute("blPrpDcodeSource", blPrpDcodeSource);		
  }
	
}
