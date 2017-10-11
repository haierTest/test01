package com.inducts.prpall.dao;

import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.inducts.prpall.dto.*;
import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class BankInterfaceDAO {
	Log logger = LogFactory.getLog(getClass());
	public boolean insertBandInfoSumAndDetail(List l,List ll) 
	throws Exception{
        String createfileseq = "";
        
        logger.info("imcoming -----------");
        
        DbPool dbpool = new DbPool();
        dbpool.open(SysConfig.getProperty("DDCCDATASOURCE"));
        try
        {
            dbpool.beginTransaction();
            StringBuffer sb= new StringBuffer(300);
            Iterator it = l.iterator();      
            BankInterfaceDetailDTO ddto=null;
            int i=0;
            while (it.hasNext()){
            	sb.delete(0,sb.length());
            	ddto=(BankInterfaceDetailDTO)it.next();
            	if(i==0)
            	{
            		createfileseq = ddto.getUploadFileSeq();
            	}            	
            	sb.append("insert into bankinterface_detail");
            	sb.append("(appliname, appidno, estateaddress, investcount, sumpremium, ");
            	sb.append(" bfbankcode, bfaccountno, bankbranchcode, investdate, proposalno, ");
            	sb.append("idtype, mobilecode, email, postcode, address, estatepostcode,");
            	sb.append("usefor, relation, insuredname, insuredadress, insuredphone, ");
            	sb.append("bankcode,counterperson,phone,policyno,printno,sumamount,invalid,dataoutdate,");
            	sb.append("insurepostcode, insureterm, operatedate, poundagerate, makecom,");
            	sb.append("comcode, handlercode, startdate, enddate, handler1code, operflag,");
            	sb.append("currency, classcode, riskcode, riskname, uploaddate, uploadfileseq, appseq,userdesc,TRANS_SEQ,iseiesflag)");           	
            	sb.append("values (");
            	sb.append("'"+ddto.getAppliName()+"',");
            	sb.append("'"+ddto.getAppIdno()+"',");
            	sb.append("'"+ddto.getEstateAddress()+"',");
            	sb.append("'"+ddto.getInvestCount()+"',");
            	sb.append("'"+ddto.getSumPremium()+"',");
            	
            	sb.append("'"+ddto.getBfBankCode()+"',");
            	sb.append("'"+ddto.getBfAccountNo()+"',");
            	sb.append("'"+ddto.getBankBranchCode()+"',");
            	sb.append("to_date('"+ddto.getInvestDate()+"','yyyy-mm-dd'),");
            	sb.append("'"+ddto.getProposalNo()+"',");
            	
            	sb.append("'"+ddto.getIdType()+"',");
            	sb.append("'"+ddto.getMobileCode()+"',");
            	sb.append("'"+ddto.getEmail()+"',");
            	sb.append("'"+ddto.getPostCode()+"',");
            	sb.append("'"+ddto.getAddress()+"',");
            	sb.append("'"+ddto.getEstatePostCode()+"',");
            	
            	sb.append("'"+ddto.getUseFor()+"',");
            	sb.append("'"+ddto.getRelation()+"',");
            	sb.append("'"+ddto.getInsuredName()+"',");
            	sb.append("'"+ddto.getInsuredAdress()+"',");
            	sb.append("'"+ddto.getInsuredPhone()+"',");
            	
            	sb.append("'"+ddto.getBankcode()+"',");
            	sb.append("'"+ddto.getCounterperson()+"',");
            	sb.append("'"+ddto.getPhone()+"',");
            	sb.append("'"+ddto.getPolicyno()+"',");
            	sb.append("'"+ddto.getPrintno()+"',");
            	sb.append("'"+ddto.getSumamount()+"',");
            	sb.append("'"+ddto.getInvalid()+"',");
            	sb.append("to_date('"+ddto.getDataoutdate()+"','yyyy-mm-dd'),");
            	
            	
            	sb.append("'"+ddto.getInsurePostCode()+"',");
            	sb.append("'"+ddto.getInsureTerm()+"',");
            	sb.append("to_date('"+ddto.getOperateDate()+"','yyyy-mm-dd'),");
            	
            	
            	sb.append("'"+ddto.getPoundageRate()+"',");
            	sb.append("'"+ddto.getMakeCom()+"',");
            	
            	sb.append("'"+ddto.getComCode()+"',");
            	sb.append("'"+ddto.getHandlerCode()+"',");
            	sb.append("to_date('"+ddto.getStartDate()+"','yyyy-mm-dd'),");
            	
            	sb.append("to_date('"+ddto.getEndDate()+"','yyyy-mm-dd'),");
            	
            	sb.append("'"+ddto.getHandler1code()+"',");
            	sb.append("'"+ddto.getOperFlag()+"',");
            	
            	sb.append("'"+ddto.getCurrency()+"',");
            	sb.append("'"+ddto.getClassCode()+"',");
            	sb.append("'"+ddto.getRiskCode()+"',");
            	sb.append("'"+ddto.getRiskName()+"',");
            	sb.append("sysdate,");
            	
            	sb.append("'"+ddto.getUploadFileSeq()+"',");
            	sb.append("'"+String.valueOf(i)+"',");
            	sb.append("'"+ddto.getUseForOther()+"',");
            	sb.append("seq_trans_id.nextval,"); 
                
            	sb.append("'0')");
                
            	
            	logger.info(sb.toString());
            	
            	dbpool.insert(sb.toString());
            	i++;
            }            
            Iterator iterator = ll.iterator();
            BankInterfaceSumDTO dto=null;
            
            while(iterator.hasNext()){
            	sb.delete(0,sb.length());
                dto=(BankInterfaceSumDTO)iterator.next();
            
                sb.append(" insert into bankinterface_sum ");
                sb.append(" (bankcode, branchbankcode, polcount, sumprem, annulcount, total,operatedate,");
                sb.append(" riskcode, procdate, operflag,dataflag,investcount,createfileseq)  ");
                sb.append(" values (");
                sb.append("'"+dto.getBankCode()+"',");
                sb.append("'"+dto.getBranchBankCode()+"',");
                sb.append("'"+dto.getPolcount()+"',");
                sb.append("'"+dto.getSumprem()+"',");
                sb.append("'"+dto.getAnnulCount()+"',");
                sb.append("'"+dto.getTotal()+"',");
                sb.append("'"+dto.getOperatedate()+"',");                
                sb.append("'"+dto.getRiskCode()+"',");
                sb.append("sysdate,");
                sb.append("'"+dto.getOperFlag()+"',");
                
                sb.append("'"+dto.getDataflag()+"',");            
                sb.append("'"+dto.getInvestcount()+"',");

                sb.append("'"+createfileseq+"')");
            
            

	            
	            logger.info(sb.toString());
	            
                dbpool.insert(sb.toString());
          }
            
            
            
            
            
            dbpool.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            dbpool.rollbackTransaction();
            return false;
            
            
        } finally {
            dbpool.close();
        }
        return true;
	}
}
