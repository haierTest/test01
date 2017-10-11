package com.sp.prpall.blsvr.misc;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.naming.NamingException;
import com.sp.sysframework.exceptionlog.UserException;
import com.sp.sysframework.reference.AppConfig;
import com.sp.utiall.schema.PrpdExpiresDataForCleanSchema;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import com.sp.utility.string.ChgDate;
import com.test.euse.DB;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CatchOutPolicyForAcount {
	protected final Log logger = LogFactory.getLog(getClass());
	public CatchOutPolicyForAcount() {
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		/*String strRenewalYesday = "";
		String strLogout = "";
		String strQuit = "";
		String strCurrentDate = new ChgDate().getCurrentTime("yyyy-MM-dd");

		
		String strSqlRenewalYesday =  "select a.PolicyNo, a.OldPolicyNo, b.SumAmount, b.ComCode from PrpCrenewal a, Prpcmainorigin b where a.policyno = b.policyno and b.classcode = '29'   and to_char(b.UnderWriteEndDate,'yyyy-mm-dd') = to_char(sysdate - 1, 'yyyy-mm-dd') order by b.comcode";
		
		String strSqlLogout = "select a.policyno,a.oldpolicyno,b.sumamount,b.comcode from prpcrenewal a,prpcmainorigin b,prpphead c where a.policyno=b.policyno and c.classcode='29' and a.policyno in (select policyno from prpphead where to_char(underwriteenddate,'yyyy-mm-dd')=to_char(sysdate-1,'yyyy-mm-dd') and endortype = '19')";
		
		String strSqlQuit = "select a.policyno,a.oldpolicyno,b.sumamount,b.comcode from prpcrenewal a,prpcmainorigin b,prpphead c where a.policyno=b.policyno and c.classcode='29' and c.endortype='19' and a.policyno in (select policyno from prpphead where to_char(prpphead.underwriteenddate,'yyyy-mm-dd') between(to_char(b.underwriteenddate,'yyyy-mm-dd')) and (to_char(b.underwriteenddate,'yyyy-mm-dd')))";
		
		Vector vectorRenewalYesday = new Vector();
		Vector vectorLogout = new Vector();
		Vector vectorQuit = new Vector();
		CatchOutPolicyForAcount catchOutPolicyForAcount = new CatchOutPolicyForAcount();
		
		vectorRenewalYesday = catchOutPolicyForAcount.findByConditions(strSqlRenewalYesday);
		vectorLogout = catchOutPolicyForAcount.findByConditions(strSqlLogout);
		vectorQuit = catchOutPolicyForAcount.findByConditions(strSqlQuit);
		
		if(vectorRenewalYesday.size()>0)
			strRenewalYesday = catchOutPolicyForAcount.jointStr(vectorRenewalYesday,"0");
		if(vectorLogout.size()>0)
			strLogout = catchOutPolicyForAcount.jointStr(vectorLogout,"1");
		if(vectorQuit.size()>0)
			strQuit = catchOutPolicyForAcount.jointStr(vectorQuit,"2");
		String strTotal = strRenewalYesday + strLogout + strQuit;
		if(strTotal!=null){
			catchOutPolicyForAcount.WriteTxt(strTotal);
			
		}	
		else{
			
		}*/
		
	}
	
    /**
     * ����SQL����ȡ�����
     * @param startDate ��ʼʱ��
     * @param endDate ��ֹʱ��
     * @return Vector ��ѯ�����¼��
     * @throws SQLException    ���ݿ�����쳣��
     * @throws NamingException �����쳣��
     */
    public Vector findByConditions(String str) throws
    Exception,SQLException,NamingException{
    	Vector vector = new Vector();
    	DbPool dbpool = new DbPool();
    	
    	dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
    	try{
    		vector=findByConditions(dbpool,str);
    		dbpool.close();
    	}
    	catch(SQLException sqlException){
    		dbpool.close();
    		throw sqlException;
    	}
    	catch(NamingException namingException){
    		dbpool.close();
    		throw namingException;
    	}
    	finally{
    		dbpool.close();
    	}
    	return vector;
    }
    /**
     * ���ݿ�ʼʱ�����ֹʱ����Ҷ�������
     * @param startDate ��ʼʱ�䣬��ҳ���Ͽ���ȥ�õ�
     * @param endDate ��ֹʱ�䣬��ҳ���Ͽ���ȥ�õ�
     * @return Vector 
     * @throws SQLException    ���ݿ�����쳣��
     * @throws NamingException �����쳣��
     */
    public Vector findByConditions(DbPool dbpool,String str)
    throws SQLException,NamingException{
 		ResultSet resultSet = dbpool.executeQuery(str);
        Vector vectorObject = new Vector();
 		while(resultSet.next()){
 			Vector vector = new Vector();
 			vector.add(resultSet.getString("policyNo"));
 			vector.add(resultSet.getString("OldPolicyNo"));
 			vector.add(resultSet.getString("SumAmount"));
 			vector.add(resultSet.getString("ComCode"));
            vectorObject.add(vector);
 		}
 		resultSet.close();
 		return vectorObject;
    }
    /**
     * �ѵõ���Vector�����е�����д��txt�У��ȰѶ��������ƴ��string
     * @param ���PrpdExpiresDataForCleanDto��Ϣ��vector
     */
    public String  jointStr(Vector vector,String flag){
		String strInfo = "";
		int i = vector.size();
		
		logger.info("vector.size()="+i);
		
		for(int j=0;j<i;j++){
			String str = "";
			
			Vector v = (Vector)vector.get(j);
			for(int a=0;v.size()-a>0;a++){
				str = (String)v.get(a);
				strInfo = strInfo + str + "|";
			}
			strInfo = strInfo + flag +'\n';	
			
			logger.info(strInfo);
			
		}
		return strInfo;
	}
	
    /**
     * ���Ѿ�ƴ�Ӻõ��ַ����ŵ��ļ���
     * @param str ƴ�Ӻõ��ַ���
     * @param fileName �ļ������֣���ҳ���Ͽ���ȡ����
     * @throws FileNotFoundException
     */
    public void WriteTxt(String str) throws FileNotFoundException{
    	String strCurrentDate = new ChgDate().getCurrentTime("yyyy-MM-dd");
    	String addressSave = AppConfig.get("sysconst.EIES_ACCOUNTFILEUP_URL")+"\\"+strCurrentDate+"-AccountFile.txt";
    	File file = new File(addressSave);
    	PrintStream ps = new PrintStream(new FileOutputStream(file));
    	ps.println(str);
    	ps.close();
    	
    }
    /* add by hezhuan begin at 20080916 */
    /**
     * ��������XXXXX���ʴ���ĳɳ鵥��ʽ
     * @param accountDate ��XXXXXʱ��(ҳ�洫��)
     * @return ����ArrayList
     */  
	public ArrayList catchAccountPolicy(String accountDate){
		DB db = DB.getDB();
		DbPool dbpool=null;
		dbpool = new DbPool();
		String sql="";		
		try{
		    dbpool.open(SysConfig.CONST_DDCCDATASOURCE);    
			sql = "select a.OldPolicyNo, a.PolicyNo, b.SumAmount, b.ComCode"
                +" from PrpCrenewal a, Prpcmainorigin b"
                +" where a.policyno = b.policyno"
                +" and b.classcode = '29' and b.underwriteenddate = to_date('"+accountDate+"','yyyy-mm-dd')";
		    sql=sql+" order by b.comcode";
		    
		    logger.info("SQL-----------0000000------"+sql);
		    
		    ArrayList catchPolicy= db.queryAgtManager(sql,dbpool);
		    return catchPolicy;
		}catch(Exception e){
			return new ArrayList();
		}finally{
		    if(dbpool != null){
			   try{
			        dbpool.close();
			   }catch(Exception e){
			   }
			}
		}
	}
	
    /**
     * ��������XXXXX���ʴ���ĳɳ鵥��ʽ
     * @param accountDate ����ͨ��ʱ��(ҳ�洫��)
     * @param accountType ��������
     * @return ����ArrayList
     */ 
	public ArrayList catchAccountPolicy(String accountDate,String accountType){
		DB db = DB.getDB();
		DbPool dbpool=null;
		dbpool = new DbPool();
		String sql="";		
		try{
		    dbpool.open(SysConfig.CONST_DDCCDATASOURCE);    
			sql = "select a.OldPolicyNo, a.PolicyNo, b.SumAmount, b.ComCode"
                +" from prpcrenewal a,prpcmainorigin b,prpphead c"
                +" where a.policyno = b.policyno and b.policyno=c.policyno"
                +" and c.classcode='29' and c.underwriteenddate=to_date('"+accountDate+"','yyyy-mm-dd')";
                if(accountType.equals("1")){  	
   		        sql=sql+" and c.endortype = '19'";
                }
                if(accountType.equals("2")){  	
       		     sql=sql+" and c.endortype = '21' and ROUND(TO_NUMBER(c.validdate - b.startdate))<=9";
                 }
		    sql=sql+" order by b.comcode";
		    
		    logger.info("SQL-----------QQQQQQ------"+sql);
		    
		    ArrayList catchPolicy= db.queryAgtManager(sql,dbpool);
		    return catchPolicy;
		}catch(Exception e){
			return new ArrayList();
		}finally{
		    if(dbpool != null){
			   try{
			        dbpool.close();
			   }catch(Exception e){
			   }
			}
		}
	}
	/* add by hezhuan end at 20080916 */
}
