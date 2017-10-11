package com.test.yj.trans;

import java.sql.*;
import java.util.*;
import java.text.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import com.sp.sysframework.reference.DBManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class DB{
  private static String CLASSNAME=null;
  private static String URL=null;      
  private static String USER=null;     
  private static String PWD=null;      
  private java.util.Properties prodata = new Properties();
  
  protected final static  Log logger = LogFactory.getLog(DB.class);
  
  private DB(){
  }
    public ArrayList query111(String sql){	
      /*String query="select FWYZM,BDYZM,Names,PaperTyle,"
                  +" PaperNumber,BeginTime,EndTime,Kind,"
                  +" Beneficiaries,BeneficiariesPaperTyle,"
                  +" BeneficiariesPaperNumber,"
                  +" Respecting,operations,fulldate,Remarks from "+tableName+""
                  +" where KindSate='1' and Remarks<>'1'  and fulldate>'2007-02-11'";*/	
  	  Connection conn=null;
  	  ResultSet rs=null;
  	  ArrayList list =new ArrayList();
  	  ArrayList listItem =null;
  	  Statement stmt=null;
  	  try{
  	  	 conn=this.getMSSQLConnection();
  	  	 if(conn==null){
  	  	 	return new ArrayList();
  	  	 }
  	  	 stmt = conn.createStatement();
  	  	 rs=stmt.executeQuery(sql);
  	  	 ResultSetMetaData rsmd = rs.getMetaData();
  	  	 int colCount = rsmd.getColumnCount();
         while(rs.next()){
         	 listItem=new ArrayList();
         	 for(int i=1;i<=colCount;i++){
         	 	listItem.add(rs.getString(i));
         	 }
             list.add(listItem);
         }
         
         return list;
  	  }catch(Exception e){
  		 
  	     logger.error(e.toString());
  	     
  	  	 return list;
  	  }finally{
  	  	 try{
  	  	 	rs.close();
  	  	 
  	  	 }catch(Exception ee){
  	  	     
  	  	     logger.error(ee.toString());
  	  	     
  	  	 }
  	  	 try{
  	  	 	stmt.close();
  	  	 	
  	  	 }catch(Exception ee2){
  	  		 
  	  	     logger.error(ee2.toString());
  	  	     
  	  	 	
  	  	 }
  	  	 try{
  	  	 	conn.close();
  	  	 }catch(Exception ee3){
  	  		 
  	  	     logger.error(ee3.toString());
  	  	     
  	  	 	return list;
  	  	 }
  	  	 
  	  }	
   }
  public static void main(String args[]){
     DB db =DB.getDB();
    ArrayList listbb=new ArrayList();	
    listbb=db.query111("select * from aa0607 where fulldate>'2007-04-27'");
    
    logger.info(listbb.size());
    
    /*listbb=db.query111("select * from aa0623 where PaperNumber='610123195711020312'");
    listbb=db.query111("select * from ab0607 where PaperNumber='610123195711020312'");
    listbb=db.query111("select * from ab0623 where PaperNumber='610123195711020312'");
    listbb=db.query111("select * from ac0607 where PaperNumber='610123195711020312'");
    listbb=db.query111("select * from ac0623 where PaperNumber='610123195711020312'");
    listbb=db.query111("select * from ad0607 where PaperNumber='610123195711020312'");
    listbb=db.query111("select * from ad0623 where PaperNumber='610123195711020312'");
    listbb=db.query111("select * from ae0607 where PaperNumber='610123195711020312'");
    listbb=db.query111("select * from ae0623 where PaperNumber='610123195711020312'");
    listbb=db.query111("select * from af0607 where PaperNumber='610123195711020312'");
    listbb=db.query111("select * from af0623 where PaperNumber='610123195711020312'");
     */
  }
  
  public static DB getDB(){
  	 return new DB();
  }
  
  public Connection getOracleConnection() {
    Connection conn = null;
    Context ctx=null;
    try {
       ctx = new InitialContext();
       javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("ddccDataSource"); 
       conn = ds.getConnection();    	    	
    }catch (Exception e) {
      
      logger.error(e.toString());
      
    }
    return conn;
  }
  
  
  public Connection getMSSQLConnection() {
    Connection conn = null;
    try {
      if(CLASSNAME==null){
      	 
    	 prodata.load(this.getClass().getResourceAsStream(
           "data.properties"));
         CLASSNAME=prodata.getProperty("Driver");
         URL=prodata.getProperty("DSN");
         USER=prodata.getProperty("UID");
         PWD=prodata.getProperty("PWD");
         
         logger.info(URL);
         logger.info(USER);
         logger.info(PWD);
         
      }
      Class.forName(CLASSNAME).newInstance();
      conn = DriverManager.getConnection(URL,USER,PWD);	       	    	
    }catch (Exception e) {
      
      logger.error(e.toString());
      
    }
    return conn;
  }	
  
  
    public void  insert(ArrayList listResult, String tableName){
		String tempTableName=tableName.substring(4,6);
		String C_INSRNT_TELTEMP="";
		String BeneficiariesPaperTyleTemp="";
        String C_CRT_CDE ="";
        String C_PLY_APP_NO="";
        String C_PLY_NO="";
        String C_INSRNC_CDE="";
        String C_BSNS_TYP="";
        String C_DPT_CDE="";
        
        String C_SLS_CDE="";
        String C_AGT_CDE="";
        String C_AGT_NO="";
        String C_APP_CNM="";
        String C_INSRNT_CNM="";
        String C_INSRNT_ADDR="";
        String C_INSRNT_ZIP="";
        String C_INSRNT_TEL="";
        String C_INSRNT_ID="";
        String C_OWN_CNM="";
        
        String C_BNFC_TEL="";
        String C_BNFC_ADD="";
        String C_SALEGRP_CDE="";
        String C_INSRNC_COMPNY_CDE="";
        
        C_OWN_CNM="����";
       
        
        String D_APP_DATE="";
        String D_SIGN_DATE="";
        String D_INSBGN_DATE="";
        String D_INSEND_DATE="";
        String D_FLIGHT_DATE="";
        
           
        String C_FLIGHT_NO="";
        
        
        String N_AMTtemp="";
        String N_PRMtemp="20.0000";
        String N_CMM_PROPtemp ="20.0000";
        String N_RATIOtemp="0.0000";
        String N_COUNTtemp="1";
        
        
        double N_AMT=0.00d;
        double N_PRM=0.00d;
        double N_CMM_PROP=0.0000d;
        double N_RATIO=0.0000;
        int N_COUNT=1;
       	
       	String C_CUR_CDE="";
       	String C_PAY_MODE="";
       	String C_STTL_CDE="";
       	String C_APPNT="";
       	String C_VCH_TYP="";
       	String C_VCH_NO="";
        
       	
       	String D_USE_DATE="";
        String C_CHG_FLAG="";
        String C_VCH_STAT="";
        ArrayList list = null;
        String insertSql ="";
        String insertFlag="";
        Connection connOracle=null;
        connOracle = getOracleConnection();
        Statement stmtOracle = null;	
        
		logger.info("connOracle:"+connOracle);
	    
		try{
			stmtOracle = connOracle.createStatement();
		}catch(SQLException eeeee){
		}
        /*��Ϊ��������̫��������Ҫ�Ż���һ�����ݿ����ӾͿ�����*/
	    
        logger.info(listResult.size());
        
        try{
        for(int j=0;j<listResult.size();j++){
        	C_INSRNT_TELTEMP="";
			BeneficiariesPaperTyleTemp="";
			C_CRT_CDE ="";
			C_PLY_APP_NO="";
			C_PLY_NO="";
			C_INSRNC_CDE="";
			C_BSNS_TYP="";
			C_DPT_CDE="";
			
			C_SLS_CDE="";
			C_AGT_CDE="";
			C_AGT_NO="";
			C_APP_CNM="";
			C_INSRNT_CNM="";
			C_INSRNT_ADDR="";
			C_INSRNT_ZIP="";
			C_INSRNT_TEL="";
			C_INSRNT_ID="";
			
			C_BNFC_TEL="";
			C_BNFC_ADD="";
			C_SALEGRP_CDE="";
			C_INSRNC_COMPNY_CDE="";
			C_OWN_CNM="����";
			
			D_APP_DATE="";
			D_SIGN_DATE="";
			D_INSBGN_DATE="";
			D_INSEND_DATE="";
			D_FLIGHT_DATE="";
			C_FLIGHT_NO="";
			N_AMTtemp="";
			N_PRMtemp="20.0000";
			N_CMM_PROPtemp ="20.0000";
			N_RATIOtemp="0.0000";
			N_COUNTtemp="1";
			
			N_AMT=0.00d;
			N_PRM=0.00d;
			N_CMM_PROP=0.0000d;
			N_RATIO=0.0000;
			N_COUNT=1;
			
			C_CUR_CDE="";
			C_PAY_MODE="";
			C_STTL_CDE="";
			C_APPNT="";
			C_VCH_TYP="";
			C_VCH_NO="";
			
			D_USE_DATE="";
			C_CHG_FLAG="";
			C_VCH_STAT="";
			insertSql ="";
			insertFlag="";			
        	list =(ArrayList)listResult.get(j);
			
				C_CRT_CDE=(String)list.get(12);            /*��������û�*/
				C_SLS_CDE=C_CRT_CDE;
				C_PLY_APP_NO=(String)list.get(0);          /*XX����*/
	            C_PLY_NO=(String)list.get(0);              /*XX��*/
	            /*XXXXX�ִ����XX*/
	            if(tempTableName.equals("07")){
	            	C_INSRNC_CDE="2708";
	            	N_AMTtemp="400000.00";
	            }else{
	            	C_INSRNC_CDE="2721";
	            	N_AMTtemp="500000.00";
	            }
	            C_BSNS_TYP="2";                            /*ҵ����Դ*/
	            C_DPT_CDE="01530000";                      /*��������*/
	            
	            
	            
				C_AGT_CDE="U01043000047";                  /*�������*/
				C_AGT_NO="U01043000047-02";                /*����Э���*/
				C_APP_CNM=(String)list.get(2);             /*XX����*/
				C_INSRNT_CNM=(String)list.get(2);          /*��XXXXX����*/
				/*��XXXXX��֤�����ͺ�֤����*/
				C_INSRNT_TELTEMP=(String)list.get(3);
				if(C_INSRNT_TELTEMP.equals("���֤")){
					C_INSRNT_TEL="1";
					C_INSRNT_ID=(String)list.get(4);
				}else if(C_INSRNT_TELTEMP.equals("����֤")){
					C_INSRNT_TEL="2";
					C_INSRNT_ID=(String)list.get(4);
				}else if(C_INSRNT_TELTEMP.equals("ѧ��֤")){
					C_INSRNT_TEL="3";
					C_INSRNT_ID=(String)list.get(4);
				}else if(C_INSRNT_TELTEMP.equals("̨��֤")){
					C_INSRNT_TEL="4";
					C_INSRNT_ID=(String)list.get(4);
				}else if(C_INSRNT_TELTEMP.equals("����")){
					C_INSRNT_TEL="5";
					C_INSRNT_ID=(String)list.get(4);
				}else if(C_INSRNT_TELTEMP.equals("�۰ķ���֤")){
					C_INSRNT_TEL="6";
					C_INSRNT_ID=(String)list.get(4);       
				}else if(C_INSRNT_TELTEMP.equals("��������(δ����ʹ��)")){
					C_INSRNT_TEL="9";
					C_INSRNT_ID=(String)list.get(4);       /*�������Ϊδ�����˳�������*/
				}else{
					C_INSRNT_TEL="9";
					C_INSRNT_ID=(String)list.get(4);
					C_FLIGHT_NO=(String)list.get(4);       /*�������Ϊ�����*/
				}
				C_INSRNT_ADDR="";                          /*��XX�˵�ַ*/  
				C_INSRNT_ZIP="";                           /*��XX�ʱ�*/
				C_OWN_CNM=(String)list.get(8);             /*����������*/
	            D_APP_DATE=(String)list.get(13);           /*XX����*/
	            D_APP_DATE=D_APP_DATE.substring(0,19);
	            D_SIGN_DATE=D_APP_DATE;                    /*ǩ������*/
	            D_USE_DATE=D_APP_DATE;                     /*��֤��������*/
	            /*XX����*/
	            D_INSBGN_DATE=(String)list.get(5);
	            D_INSBGN_DATE=D_INSBGN_DATE.replaceAll("��","-");
	            D_INSBGN_DATE=D_INSBGN_DATE.replaceAll("��","-");
	            D_INSBGN_DATE=D_INSBGN_DATE.replaceAll("��"," 00:00:00");
	            /*XXֹ��*/
	            D_INSEND_DATE=(String)list.get(6);
	            D_INSEND_DATE=D_INSEND_DATE.replaceAll("��","-");
	            D_INSEND_DATE=D_INSEND_DATE.replaceAll("��","-");
	            D_INSEND_DATE=D_INSEND_DATE.replaceAll("��"," 23:59:59");
	            D_FLIGHT_DATE=D_INSBGN_DATE;              /*�������*/
	            
	            
	            N_AMT=Double.parseDouble(N_AMTtemp);      /*XX*/
		        N_PRM=Double.parseDouble(N_PRMtemp);      /*XX*/
		        N_CMM_PROP=Double.parseDouble(N_CMM_PROPtemp);/*�����ѱ���*/
		        N_RATIO=Double.parseDouble(N_RATIOtemp);  /*����*/
		        N_COUNT=Integer.parseInt(N_COUNTtemp);    /*����*/
	            C_CUR_CDE="1";                            /*����*/
				C_PAY_MODE="110";                         /*֧����ʽ*/
				C_STTL_CDE="1";                           /*��������ʽ*/
				C_APPNT=(String)list.get(14);             /*��ע*/
				C_VCH_TYP="ABK097A32007A";                /*��֤����*/
				C_VCH_NO=(String)list.get(1);             /*��ˮ��*/
				
				
				C_BNFC_TEL="";                            /*������֤������*/
				BeneficiariesPaperTyleTemp=(String)list.get(9);
				if(BeneficiariesPaperTyleTemp.equals("���֤")){
					C_BNFC_TEL="1";
					C_BNFC_ADD=(String)list.get(10);
				}else if(BeneficiariesPaperTyleTemp.equals("����֤")){
					C_BNFC_TEL="2";
					C_BNFC_ADD=(String)list.get(10);
				}else if(BeneficiariesPaperTyleTemp.equals("ѧ��֤")){
					C_BNFC_TEL="3";
					C_BNFC_ADD=(String)list.get(10);
				}else if(BeneficiariesPaperTyleTemp.equals("̨��֤")){
					C_BNFC_TEL="4";
					C_BNFC_ADD=(String)list.get(10);
				}else if(BeneficiariesPaperTyleTemp.equals("����")){
					C_BNFC_TEL="5";
					C_BNFC_ADD=(String)list.get(10);
				}else if(BeneficiariesPaperTyleTemp.equals("�۰ķ���֤")){
					C_BNFC_TEL="6";
					C_BNFC_ADD=(String)list.get(10);       
				}else if(BeneficiariesPaperTyleTemp.equals("��������(δ����ʹ��)")){
					C_BNFC_TEL="9";
					C_BNFC_ADD=(String)list.get(10);       /*�������Ϊδ�����˳�������*/
				}else{
					C_BNFC_TEL="9";
					C_BNFC_ADD=(String)list.get(10);
					C_FLIGHT_NO=(String)list.get(10);      /*�������Ϊ�����*/
				}
				/*���������֤����Ϊ������֤�������*/
				if(C_BNFC_ADD.equals("����")){
					C_BNFC_ADD=" ";
				}
				C_SALEGRP_CDE=(String)list.get(11);       /*�������뱻XX�˹�ϵ*/ 
				/*�������뱻XX�˹�ϵΪ���������*/
				if(C_SALEGRP_CDE.equals("����")){
					C_SALEGRP_CDE="";
				}         
				C_INSRNC_COMPNY_CDE=(String)list.get(0);  /*��α��������*/
				C_CHG_FLAG="0";                           /*ת��ҵ��ϵͳ��־*/
				C_VCH_STAT="2";                           /*��֤״̬*/
	        	insertSql="insert into T_ACCIDENT_POLICY (C_CRT_CDE,C_PLY_APP_NO,C_PLY_NO,C_INSRNC_CDE,C_BSNS_TYP,"
								+"C_DPT_CDE,C_SLS_CDE,C_AGT_CDE,C_AGT_NO,C_APP_CNM,C_INSRNT_CNM,C_INSRNT_ADDR,C_INSRNT_ZIP,"
								+"C_INSRNT_TEL,C_INSRNT_ID,C_BNFC_CNM,D_APP_DATE,D_SIGN_DATE,D_INSBGN_DATE,D_INSEND_DATE,"
								+"D_FLIGHT_DATE,C_FLIGHT_NO,N_AMT,N_PRM,N_CMM_PROP,N_RATIO,N_COUNT,C_CUR_CDE,C_PAY_MODE,"
								+"C_STTL_CDE,C_REMARK,C_VCH_TYP,C_VCH_NO,D_USE_DATE,C_CHG_FLAG,C_VCH_STAT,C_BNFC_TEL,C_BNFC_ADD,C_SALEGRP_CDE,C_INSRNC_COMPNY_CDE)" 
								+"values('"+C_CRT_CDE.trim()+"','"+C_PLY_APP_NO.trim()+"','"+C_PLY_NO.trim()+"','"+C_INSRNC_CDE.trim()+"','"
								+C_BSNS_TYP.trim()+"','"+C_DPT_CDE.trim()+"','"+C_SLS_CDE+"','"+C_AGT_CDE.trim()+"','"+C_AGT_NO.trim()+"','"
								+C_APP_CNM.trim()+"','"+C_INSRNT_CNM.trim()+"','"+C_INSRNT_ADDR.trim()+"','"+C_INSRNT_ZIP.trim()+"','"
								+C_INSRNT_TEL.trim()+"','"+C_INSRNT_ID.trim()+"','"+C_OWN_CNM+"',"
								+"to_date('"+D_APP_DATE+"','yyyy-mm-dd hh24:mi:ss'),"
								+"to_date('"+D_SIGN_DATE+"','yyyy-mm-dd hh24:mi:ss'),"
								+"to_date('"+D_INSBGN_DATE+"','yyyy-mm-dd hh24:mi:ss'),"
								+"to_date('"+D_INSEND_DATE+"','yyyy-mm-dd hh24:mi:ss'),"
								+"to_date('"+D_FLIGHT_DATE+"','yyyy-mm-dd hh24:mi:ss'),'"
								+C_FLIGHT_NO.trim()+"',"
								+N_AMT+","+N_PRM+","+N_CMM_PROP+","+N_RATIO+","+N_COUNT+",'"
								+C_CUR_CDE.trim()+"','110','"+C_STTL_CDE.trim()+"','"+C_APPNT.trim()+"','"+C_VCH_TYP.trim()+"','"+C_VCH_NO.trim()+"',"
								+"to_date('"+D_USE_DATE.trim()+"','yyyy-mm-dd hh24:mi:ss'),'"+C_CHG_FLAG.trim()+"','"+C_VCH_STAT.trim()+"','"+C_BNFC_TEL.trim()+"','"
								+C_BNFC_ADD.trim()+"','"+C_SALEGRP_CDE.trim()+"','"+C_INSRNC_COMPNY_CDE.trim()+"')";
				
			  	try{
			      	int f = stmtOracle.executeUpdate(insertSql);
			      	if(f>0)
			      	  insertFlag = "true";
			      	else
			      	  insertFlag = "false";  
			  	}catch (SQLException ex) {
			  	    
			  	  	logger.error(ex.toString());
			  	    
			     	insertFlag = "false";
			    } 				
				if(!insertFlag.equals("true")){
					exceMSSQLUpdate("update "+tableName+" set Kind='δ�����ڱ�' where FWYZM='"+C_PLY_NO+"' and BDYZM='"+C_VCH_NO+"'");
				}
			
	    }
	    }catch(Exception exp){
	    }finally{
			try{
				stmtOracle.close();
				
			}catch(SQLException exx){
		    }
		    try{
		    	connOracle.close();
		    }catch(Exception exp2){
		    	
		    }  
	    } 		
    } 
    
    public ArrayList query(String tableName){
    
      String query="select FWYZM,BDYZM,Names,PaperTyle,"
                  +" PaperNumber,BeginTime,EndTime,Kind,"
                  +" Beneficiaries,BeneficiariesPaperTyle,"
                  +" BeneficiariesPaperNumber,"
                  +" Respecting,operations,fulldate,Remarks from "+tableName+""
                  +" where KindSate='1'  and Remarks<>'1'  and fulldate >'2007-03-20 18:00:00'";
                  
  	  Connection conn=null;
  	  ResultSet rs=null;
  	  ArrayList list =new ArrayList();
  	  ArrayList listItem =null;
  	  Statement stmt=null;
  	  try{
  	  	 conn=this.getMSSQLConnection();
  	  	 if(conn==null){
  	  	 	return new ArrayList();
  	  	 }
  	  	 stmt = conn.createStatement();
  	  	 rs=stmt.executeQuery(query);
  	  	 ResultSetMetaData rsmd = rs.getMetaData();
  	  	 int colCount = rsmd.getColumnCount();
         while(rs.next()){
         	 listItem=new ArrayList();
         	 for(int i=1;i<=colCount;i++){
         	 	listItem.add(rs.getString(i));
         	 }
             list.add(listItem);
         }
         stmt.executeUpdate("update "+tableName+" set Remarks='1' where KindSate='1' and Remarks<>'1'  and fulldate >'2007-03-20 18:00:00'");
         return list;
  	  }catch(Exception e){
  	     
  	  	 logger.error(e.toString());
  	     
  	  	 return list;
  	  }finally{
  	  	 try{
  	  	 	rs.close();
  	  	 	
  	  	 }catch(Exception ee){
  	  	    
  	  	 	logger.error(ee.toString());
  	        
  	  	 	
  	  	 }
  	  	 try{
  	  	 	stmt.close();
  	  	 	
  	  	 }catch(Exception ee2){
  	  	    
  	  	 	logger.error(ee2.toString());
  	      
  	  	 	
  	  	 }
  	  	 try{
  	  	 	conn.close();
  	  	 }catch(Exception ee3){
  	  	    
  	  	 	logger.info(ee3.toString());
  	        
  	  	 	return list;
  	  	 }
  	  }	
   }
/*�������*/
  public String exceOracleUpdate(String sql){
  	  Connection conn=null;
  	  Statement stmt =null;
  	  try{
  	  	conn = this.getOracleConnection();
      	stmt = conn.createStatement();
      	int f = stmt.executeUpdate(sql);
      	if(f>0)
      	  return "true";
      	else
      	  return "false";  
  	  }catch (SQLException ex) {
  	    
  	  	logger.error(ex.toString());
  	    
     	return ex.toString();
      }finally{
      	try{
      		stmt.close();
      		
      	}catch(SQLException exx){
      		
      	}
      	try{
      		conn.close();
      	}catch(SQLException exx2){
      		return exx2.toString();
      	}  
      }    
  } 
   
  public String exceMSSQLUpdate(String sql){
  	  Connection conn=null;
  	  Statement stmt=null;
  	  try{
  	  	conn = this.getMSSQLConnection();
      	stmt = conn.createStatement();
      	int f = stmt.executeUpdate(sql);
      	if(f>0)
      	  return "true";
      	else
      	  return "false";  
  	  }catch (SQLException ex) {
  	    
  	  	logger.error(ex.toString());
  	    
     	return ex.toString();
      }finally{
      	try{
      		stmt.close();
      		
      	}catch(SQLException exx){
      	
      	}
      	try{
      		conn.close();
      	}catch(SQLException exx2){
      		return exx2.toString();
      	}  
      }    
  }   
}

