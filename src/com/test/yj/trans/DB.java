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
        
        C_OWN_CNM="法定";
       
        
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
        /*因为插入数据太多这里需要优化用一个数据库连接就可以了*/
	    
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
			C_OWN_CNM="法定";
			
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
			
				C_CRT_CDE=(String)list.get(12);            /*代理操作用户*/
				C_SLS_CDE=C_CRT_CDE;
				C_PLY_APP_NO=(String)list.get(0);          /*XX单号*/
	            C_PLY_NO=(String)list.get(0);              /*XX号*/
	            /*XXXXX种代码和XX*/
	            if(tempTableName.equals("07")){
	            	C_INSRNC_CDE="2708";
	            	N_AMTtemp="400000.00";
	            }else{
	            	C_INSRNC_CDE="2721";
	            	N_AMTtemp="500000.00";
	            }
	            C_BSNS_TYP="2";                            /*业务来源*/
	            C_DPT_CDE="01530000";                      /*机构编码*/
	            
	            
	            
				C_AGT_CDE="U01043000047";                  /*代理编码*/
				C_AGT_NO="U01043000047-02";                /*代理协议号*/
				C_APP_CNM=(String)list.get(2);             /*XX人名*/
				C_INSRNT_CNM=(String)list.get(2);          /*被XXXXX人名*/
				/*被XXXXX人证件类型和证件号*/
				C_INSRNT_TELTEMP=(String)list.get(3);
				if(C_INSRNT_TELTEMP.equals("身份证")){
					C_INSRNT_TEL="1";
					C_INSRNT_ID=(String)list.get(4);
				}else if(C_INSRNT_TELTEMP.equals("军官证")){
					C_INSRNT_TEL="2";
					C_INSRNT_ID=(String)list.get(4);
				}else if(C_INSRNT_TELTEMP.equals("学生证")){
					C_INSRNT_TEL="3";
					C_INSRNT_ID=(String)list.get(4);
				}else if(C_INSRNT_TELTEMP.equals("台胞证")){
					C_INSRNT_TEL="4";
					C_INSRNT_ID=(String)list.get(4);
				}else if(C_INSRNT_TELTEMP.equals("护照")){
					C_INSRNT_TEL="5";
					C_INSRNT_ID=(String)list.get(4);
				}else if(C_INSRNT_TELTEMP.equals("港澳返乡证")){
					C_INSRNT_TEL="6";
					C_INSRNT_ID=(String)list.get(4);       
				}else if(C_INSRNT_TELTEMP.equals("出生年月(未成年使用)")){
					C_INSRNT_TEL="9";
					C_INSRNT_ID=(String)list.get(4);       /*这种情况为未成年人出生日期*/
				}else{
					C_INSRNT_TEL="9";
					C_INSRNT_ID=(String)list.get(4);
					C_FLIGHT_NO=(String)list.get(4);       /*这种情况为航班号*/
				}
				C_INSRNT_ADDR="";                          /*被XX人地址*/  
				C_INSRNT_ZIP="";                           /*被XX邮编*/
				C_OWN_CNM=(String)list.get(8);             /*受益人姓名*/
	            D_APP_DATE=(String)list.get(13);           /*XX日期*/
	            D_APP_DATE=D_APP_DATE.substring(0,19);
	            D_SIGN_DATE=D_APP_DATE;                    /*签单日期*/
	            D_USE_DATE=D_APP_DATE;                     /*单证消号日期*/
	            /*XX起期*/
	            D_INSBGN_DATE=(String)list.get(5);
	            D_INSBGN_DATE=D_INSBGN_DATE.replaceAll("年","-");
	            D_INSBGN_DATE=D_INSBGN_DATE.replaceAll("月","-");
	            D_INSBGN_DATE=D_INSBGN_DATE.replaceAll("日"," 00:00:00");
	            /*XX止期*/
	            D_INSEND_DATE=(String)list.get(6);
	            D_INSEND_DATE=D_INSEND_DATE.replaceAll("年","-");
	            D_INSEND_DATE=D_INSEND_DATE.replaceAll("月","-");
	            D_INSEND_DATE=D_INSEND_DATE.replaceAll("日"," 23:59:59");
	            D_FLIGHT_DATE=D_INSBGN_DATE;              /*起飞日期*/
	            
	            
	            N_AMT=Double.parseDouble(N_AMTtemp);      /*XX*/
		        N_PRM=Double.parseDouble(N_PRMtemp);      /*XX*/
		        N_CMM_PROP=Double.parseDouble(N_CMM_PROPtemp);/*手续费比率*/
		        N_RATIO=Double.parseDouble(N_RATIOtemp);  /*费率*/
		        N_COUNT=Integer.parseInt(N_COUNTtemp);    /*份数*/
	            C_CUR_CDE="1";                            /*币种*/
				C_PAY_MODE="110";                         /*支付方式*/
				C_STTL_CDE="1";                           /*争议解决方式*/
				C_APPNT=(String)list.get(14);             /*备注*/
				C_VCH_TYP="ABK097A32007A";                /*单证类型*/
				C_VCH_NO=(String)list.get(1);             /*流水号*/
				
				
				C_BNFC_TEL="";                            /*受益人证件类型*/
				BeneficiariesPaperTyleTemp=(String)list.get(9);
				if(BeneficiariesPaperTyleTemp.equals("身份证")){
					C_BNFC_TEL="1";
					C_BNFC_ADD=(String)list.get(10);
				}else if(BeneficiariesPaperTyleTemp.equals("军官证")){
					C_BNFC_TEL="2";
					C_BNFC_ADD=(String)list.get(10);
				}else if(BeneficiariesPaperTyleTemp.equals("学生证")){
					C_BNFC_TEL="3";
					C_BNFC_ADD=(String)list.get(10);
				}else if(BeneficiariesPaperTyleTemp.equals("台胞证")){
					C_BNFC_TEL="4";
					C_BNFC_ADD=(String)list.get(10);
				}else if(BeneficiariesPaperTyleTemp.equals("护照")){
					C_BNFC_TEL="5";
					C_BNFC_ADD=(String)list.get(10);
				}else if(BeneficiariesPaperTyleTemp.equals("港澳返乡证")){
					C_BNFC_TEL="6";
					C_BNFC_ADD=(String)list.get(10);       
				}else if(BeneficiariesPaperTyleTemp.equals("出生年月(未成年使用)")){
					C_BNFC_TEL="9";
					C_BNFC_ADD=(String)list.get(10);       /*这种情况为未成年人出生日期*/
				}else{
					C_BNFC_TEL="9";
					C_BNFC_ADD=(String)list.get(10);
					C_FLIGHT_NO=(String)list.get(10);      /*这种情况为航班号*/
				}
				/*如果受益人证件号为法定把证件号清空*/
				if(C_BNFC_ADD.equals("法定")){
					C_BNFC_ADD=" ";
				}
				C_SALEGRP_CDE=(String)list.get(11);       /*受益人与被XX人关系*/ 
				/*受益人与被XX人关系为法定则清空*/
				if(C_SALEGRP_CDE.equals("法定")){
					C_SALEGRP_CDE="";
				}         
				C_INSRNC_COMPNY_CDE=(String)list.get(0);  /*防伪严正代码*/
				C_CHG_FLAG="0";                           /*转入业务系统标志*/
				C_VCH_STAT="2";                           /*单证状态*/
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
					exceMSSQLUpdate("update "+tableName+" set Kind='未插入借口表' where FWYZM='"+C_PLY_NO+"' and BDYZM='"+C_VCH_NO+"'");
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
/*插入更新*/
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

