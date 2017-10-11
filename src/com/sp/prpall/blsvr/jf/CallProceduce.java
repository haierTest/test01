package com.sp.prpall.blsvr.jf;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import oracle.jdbc.driver.*;
import javax.naming.Context;     
import javax.sql.DataSource;  
import javax.naming.InitialContext;   

import java.awt.geom.Arc2D.Double;
import java.sql.*; 
import java.util.*; 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

 
  
public class CallProceduce{
	protected final Log logger = LogFactory.getLog(getClass());
	private Context ctx =null;
	
	private java.sql.Connection getConnection(){
		java.sql.Connection conn=null;
		try{
			javax.sql.DataSource ds = (javax.sql.DataSource)this.ctx.lookup("ddccDataSource");
			if(ds!=null)
				
				logger.info("已经获得数据源");
				
			conn = ds.getConnection();
			if(conn!=null)
				
				logger.info("已经获得连接");
				
		}catch(Exception e){
			e.printStackTrace();
		    
		    logger.info(e.toString());
		    
		}
		return conn;    
	}
	
	
	public String cancelPolicy(String inputStr1,String inputStr2,String inputStr3)throws Exception{
		String returnStr1;
		String returnStr2;
		String returnStr3;
		String returnStr="";
		java.sql.Connection conn=null;
		int flag=1;
		try{
			conn=this.getConnection();
			conn.setAutoCommit(false);
			
			logger.info("call one");
			
			CallableStatement cs = conn.prepareCall("{call TL_INVEST_PROC_shen.TL_INVEST_COUNTERACT(?,?,?,?)}");
			cs.registerOutParameter(4,java.sql.Types.CHAR);
			cs.setString(1,inputStr1);
			cs.setString(2,inputStr2);
			cs.setString(3,inputStr3);
			cs.execute();
			returnStr1=cs.getString(4);
			
			logger.info(returnStr1);
			
			String returnStr1Flag=returnStr1.substring(0,4);
			if(!returnStr1Flag.equals("true")){
				flag=0;
				conn.rollback();
				conn.setAutoCommit(true);
				return returnStr1;
			}else{
				returnStr=returnStr+returnStr1;
			}
			
			
			logger.info("call two");
			
			cs = conn.prepareCall("{call TL_INVEST_PROC_shen.TL_INVEST_PENALTY(?,?,?,?)}");
			cs.registerOutParameter(4,java.sql.Types.CHAR);
			cs.setString(1,inputStr1);
			cs.setString(2,inputStr2);
			cs.setString(3,inputStr3);
			cs.execute();
			returnStr2=cs.getString(4);
			
			logger.info(returnStr2);
			
			String returnStr2Flag=returnStr2.substring(0,4);
			if(!returnStr2Flag.equals("true")){
				flag=0;
				conn.rollback();
				conn.setAutoCommit(true);				
				return returnStr2;
			}else{
				returnStr=returnStr+"--"+returnStr2;
			}
			
			
			logger.info("call three");
			
			cs = conn.prepareCall("{call TL_INVEST_PROC_shen.tl_fess_process(?,?,?,?)}");
			cs.registerOutParameter(4,java.sql.Types.CHAR);
			cs.setString(1,inputStr1);
			cs.setString(2,inputStr2);
			cs.setString(3,inputStr3);
			cs.execute();
			returnStr3=cs.getString(4);
			
			logger.info(returnStr3);
			
			String returnStr3Flag=returnStr3.substring(0,4);
			if(!returnStr3Flag.equals("true")){
				flag=0;
		    	conn.rollback();
				conn.setAutoCommit(true);				
				return returnStr3;
		    }else{
		    	returnStr=returnStr+"--"+returnStr3;
		    }		
			
		}catch(Exception e){
			e.printStackTrace();
			returnStr="操作执行失败!";
			conn.rollback();
			conn.setAutoCommit(true);
			
			logger.error(e.toString());
			
			return returnStr;
		}finally{
			try{
				if(flag==1){
					conn.commit();
				}else{
					conn.rollback();
				}
				conn.setAutoCommit(true);
				conn.close();
			}catch(Exception ex){
				returnStr="连接数据库失败!";
			}
		}
		return returnStr;
	}
	
    
    public String givePayPolicy(String inputStr1,String inputStr2, String inputStr3){
		String returnStr;
		String returnStr1;
		String returnStr2;
		java.sql.Connection conn=null;
		try{
			conn=this.getConnection();
			conn.setAutoCommit(false);
			CallableStatement cs = conn.prepareCall("{call TL_INVEST_PROC.TL_MQ_GF(?,?,?)}");
			cs.registerOutParameter(3,java.sql.Types.CHAR);
			cs.setString(1,inputStr1);
			cs.setString(2,inputStr2);
			cs.execute();
			returnStr1=cs.getString(3);
			if(returnStr1==null){
				cs = conn.prepareCall("{call TL_INVEST_PROC.tl_mq_sy(?,?)}");
				cs.registerOutParameter(2,java.sql.Types.CHAR);
				cs.setString(1,inputStr1);
				cs.execute();
				returnStr2=cs.getString(2);
			}else{
				conn.rollback();
				conn.setAutoCommit(true);
				return returnStr1;
			}
			if(returnStr2==null){
				conn.commit();
				conn.setAutoCommit(true);				
				return "true";
			}else{
				conn.rollback();
				conn.setAutoCommit(true);				
				return returnStr2;
			}
		}catch(Exception e){
			e.printStackTrace();
			returnStr="调用存储过程失败!";
			
			logger.error(e.toString());
			
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				returnStr="连接数据库失败!";
				
				logger.error("连接数据库失败!");
				
			}
		}
		return "";
    }

	public CallProceduce(Context ctx){
	    this.ctx=ctx;
	}
	
	public CallProceduce(){
		try{
			Context ctx1 =new InitialContext();
			this.ctx=ctx1;
		}catch(Exception e){
			
			logger.error(e.toString());
			
		}	
	}
			
	/*调用TL_FEEDBACK_BJ存储过程
	*参数1 XX号
	*参数2 批单号
	*返回值 存储过程的返回
	*/
	public String callTL_FEEDBACK_BJ(String inputStr1,String inputStr2){
		String returnStr="";
		java.sql.Connection conn=null;
		try{
			conn=this.getConnection();
			CallableStatement cs = conn.prepareCall("{call TL_INVEST_PROC.TL_FEEDBACK_BJ(?,?,?)}");
			cs.registerOutParameter(3,java.sql.Types.CHAR);
			cs.setString(1,inputStr1);
			cs.setString(2,inputStr2);
			cs.execute();
			returnStr=cs.getString(3);
		}catch(Exception e){
			e.printStackTrace();
			returnStr="调用存储过程失败!";
			
			logger.error(e.toString());
			
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				returnStr="连接数据库失败!";
				
				logger.error("连接数据库失败!");
				
			}
		}
		return returnStr;
	}
	
	/*调用insert_fee_proc存储过程
	 *参数1 XX号
	 *参数2 批单号
	 *返回 存储过程的返回值
	*/
	public String callInsert_fee_proc(String inputStr1,String inputStr2){
		String returnStr="";
		java.sql.Connection conn=null;
		try{
			conn=this.getConnection();
			CallableStatement cs = conn.prepareCall("{call TL_INVEST_PROC.insert_fee_proc(?,?,?)}");
			cs.registerOutParameter(3,java.sql.Types.CHAR);
			cs.setString(1,inputStr1);
			cs.setString(2,inputStr2);
			cs.execute();
			returnStr=cs.getString(3);
		}catch(Exception e){
			e.printStackTrace();
			returnStr="调用存储过程失败!";
			
			logger.error(e.toString());
			
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				returnStr="连接数据库失败!";
				
				logger.error("连接数据库失败!");
				
			}
		}
		return returnStr;
	}
	
	/*调用tl_mq_sy存储过程
	 *参数1 XX号
	*/ 
	public void callTL_mq_sy(String inputStr1){
		java.sql.Connection conn=null;
		try{
			conn=this.getConnection();
			CallableStatement cs = conn.prepareCall("{call TL_INVEST_PROC.tl_mq_sy(?)}");
			cs.setString(1,inputStr1);
			cs.execute();
		}catch(Exception e){
			e.printStackTrace();
			
			logger.error(e.toString());
			
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				
				logger.error("连接数据库失败!");
				
			}
		}
	}
	
	/*调用TL_FEEDBACK_SY存储过程
	 *参数1 XX号
	*/
	public void callTL_FEEDBACK_SY(String inputStr1){
		java.sql.Connection conn=null;
		try{
			conn=this.getConnection();
			CallableStatement cs = conn.prepareCall("{call TL_INVEST_PROC.TL_FEEDBACK_SY(?)}");
			cs.setString(1,inputStr1);
			cs.execute();
		}catch(Exception e){
			e.printStackTrace();
			
			logger.error(e.toString());
			
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				
				logger.error("连接数据库失败!");
				
			}
		}
	}
	
	public String callGet_seq1(){
		java.sql.Connection conn=null;
		String returnStr="";
		try{
			conn=this.getConnection();
			CallableStatement cs = conn.prepareCall("{call TL_INVEST_PROC.get_seq(?)}");
			cs.registerOutParameter(1,java.sql.Types.CHAR);
			cs.execute();
			returnStr=cs.getString(1);
		}catch(Exception e){
			e.printStackTrace();
			returnStr="调用存储过程失败!";
			
			logger.error(e.toString());
			
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				returnStr="连接数据库失败!";
				
				logger.error("连接数据库失败!");
				
			}
		}
		return returnStr;
	}	
	/*调用TL_MQ_GF存储过程
	 *参数1 XX号
	 *参数2 批单号
	 *返回 存储过程返回
	 */
	public String callTL_MQ_GF(String inputStr1,String inputStr2){
		String returnStr="";
		java.sql.Connection conn=null;
		try{
			conn=this.getConnection();
			CallableStatement cs = conn.prepareCall("{call TL_INVEST_PROC.TL_MQ_GF(?,?,?)}");
			cs.registerOutParameter(3,java.sql.Types.CHAR);
			cs.setString(1,inputStr1);
			cs.setString(2,inputStr2);
			cs.execute();
			returnStr=cs.getString(3);
		}catch(Exception e){
			e.printStackTrace();
			returnStr="调用存储过程失败!";
			
			logger.error(e.toString());
			
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				returnStr="连接数据库失败!";
				
				logger.error("连接数据库失败!");
				
			}
		}
		return returnStr;
	}
	
	/*调用Tl_invest_proc存储过程
	 */
	/*public void callTL_invest_proc(){
		java.sql.Connection conn=null;
		try{
			conn=this.getConnection();
			CallableStatement cs = conn.prepareCall("{call tl_invest_proc()}");
			cs.execute();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
			}
		}
	}*/
	

	/*调用tl_write_log存储过程
	 *参数1 输入出错环节
	 *参数2 输出出错环节
	 *返回 存储过程的返回值
	*/
	/*public String callTL_write_log(String inputStr1,String inputStr2){
		String returnStr="";
		java.sql.Connection conn=null;
		try{
			conn=this.getConnection();
			CallableStatement cs = conn.prepareCall("{call tl_write_log(?,?,?)}");
			cs.registerOutParameter(3,java.sql.Types.CHAR);
			cs.setString(1,inputStr1);
			cs.setString(2,inputStr2);
			cs.execute();
			returnStr=cs.getString(3);
		}catch(Exception e){
			e.printStackTrace();
			returnStr="调用存储过程失败!";
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				returnStr="连接数据库失败!";
			}
		}
		return returnStr;
	}*/	
	public String callCreatevoucher_2901proc62(){
		java.sql.Connection conn=null;
		String returnStr="true";
		try{
			conn=this.getConnection();
			if(conn!=null){
				CallableStatement cs = conn.prepareCall("{call sinosoft.createvoucher_2901proc62()}");
				cs.execute();
				return returnStr;				
			}else{
				return "连接数据库失败!";
			}

		}catch(Exception e){
			e.printStackTrace();
			
			logger.error(e.toString());
			
			returnStr=e.toString();
			return returnStr;
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				
				logger.error("连接数据库失败!");
				
			}
		}
	}
	
	public String createvoucher_2901proc6_shen(){
		java.sql.Connection conn=null;
		String returnStr="true";
		try{
			conn=this.getConnection();
			if(conn!=null){
				CallableStatement cs = conn.prepareCall("{call sinosoft.createvoucher_2901proc62()}");
				cs.execute();
				return returnStr;				
			}else{
				return "连接数据库失败!";
			}

		}catch(Exception e){
			e.printStackTrace();
			
			logger.error(e.toString());
			
			returnStr=e.toString();
			return returnStr;
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				
				logger.error("连接数据库失败!");
				
			}
		}
	}
	/*************************************************************************/
	/*************************************************************************
	用于调用生成实收数据进生成凭证前的表prpjpayrefmain
	**************************************************************************/
	public String callCreatevoucher_2901proc6_data(String inputStr1,String inputStr2,String inputStr3){
		
		logger.info("callCreatevoucher_2901proc6_data>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		String returnStr="";
		java.sql.Connection conn=null;
		try{
			conn=this.getConnection();
			conn.setAutoCommit(false);
			CallableStatement cs = conn.prepareCall("{call TL_ACCOUNT_PROC6_PKG.createvoucher_2901proc6_data(?,?,?,?)}");
			cs.registerOutParameter(4,java.sql.Types.CHAR);
			cs.setString(1,inputStr1);
			cs.setString(2,inputStr2);
			cs.setString(3,inputStr3);
			cs.execute();
			returnStr=cs.getString(4);
			conn.commit();
			conn.setAutoCommit(true);
		}catch(Exception e){
			try{
				conn.rollback();
				conn.setAutoCommit(true);
			}catch(Exception es){
				
			}
			e.printStackTrace();
			returnStr="调用存储过程失败!"+e.toString();
			
			logger.error(e.toString());
			
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				returnStr="连接数据库失败!"+ex.toString();
				
				logger.error("连接数据库失败!");
				
			}
		}
		return returnStr;
	}	
	
	/*************************************************************************
	用于调用生成应收实收凭证数据
	**************************************************************************/
	public String callCreatevoucher_2901proc6_voucher(String inputStr1,String inputStr2,String inputStr3){
		
		logger.info("callCreatevoucher_2901proc6_voucher>>>>>>>>>>>>>>>>>>>");
		
		String returnStr="";
		java.sql.Connection conn=null;
		try{
			conn=this.getConnection();
			conn.setAutoCommit(false);
			CallableStatement cs = conn.prepareCall("{call TL_ACCOUNT_PROC6_PKG.createvoucher_2901proc6_vou(?,?,?,?)}");
			cs.registerOutParameter(4,java.sql.Types.CHAR);
			cs.setString(1,inputStr1);
			cs.setString(2,inputStr2);
			cs.setString(3,inputStr3);
			cs.execute();
			returnStr=cs.getString(4);
			conn.commit();
			conn.setAutoCommit(true);
		}catch(Exception e){
			try{
				conn.rollback();
				conn.setAutoCommit(true);
			}catch(Exception es){
				
			}
			e.printStackTrace();
			returnStr="调用存储过程失败!"+e.toString();
			
			logger.error(e.toString());
			
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				returnStr="连接数据库失败!"+ex.toString();
				
				logger.error("连接数据库失败!");
				
			}
		}
		return returnStr;
	}
	/*****************************************************************************/
	/*************************************************************************
	用于调用生成投资收益凭证制证
	**************************************************************************/
	public String callVoucher_procinvest_2901(String inputStr1,String inputStr2,String inputStr3,String inputStr4){
		String returnStr="";
		java.sql.Connection conn=null;
		
		logger.info("callVoucher_procinvest_2901 is called!>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.>>>>>>>>>>>");
		
		try{
			
			conn=this.getConnection();
			conn.setAutoCommit(false);
			CallableStatement cs = conn.prepareCall("{call TL_ACCOUNT_PROC6_PKG.voucher_procinvest_2901(?,?,?,?,?)}");
			cs.registerOutParameter(5,java.sql.Types.CHAR);
			cs.setString(1,inputStr1);
			cs.setString(2,inputStr2);
			cs.setString(3,inputStr3);
			cs.setString(4,inputStr4);
			cs.execute();
			returnStr=cs.getString(5);
			conn.commit();
			conn.setAutoCommit(true);
		}catch(Exception e){
			try{
				conn.rollback();
				conn.setAutoCommit(true);
			}catch(Exception es){
				
			}
			e.printStackTrace();
			returnStr="调用存储过程失败!"+e.toString();
			
			logger.error(e.toString());
			
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				returnStr="连接数据库失败!";
				
				logger.error("连接数据库失败!");
				
			}
		}
		return returnStr;
	}
	/*****************************************************************************/
	/*************************************************************************
	用于调用6元挂帐生成应收实收凭证数据
	**************************************************************************/
	public String callCreateGuaZhang(String inputStr1,String inputStr2,String inputStr3,String inputStr4){
		
		logger.info("callCreateGuaZhang>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		String returnStr="";
		java.sql.Connection conn=null;
		try{
			conn=this.getConnection();
			conn.setAutoCommit(false);
			CallableStatement cs = conn.prepareCall("{call TL_ACCOUNT_PROC6_PKG.createvoucher_2901proc6_data(?,?,?,?)}");
			cs.registerOutParameter(4,java.sql.Types.CHAR);
			cs.setString(1,inputStr1);
			cs.setString(2,inputStr2);
			cs.setString(3,inputStr3);
			cs.execute();
			returnStr=cs.getString(4);
			
			logger.info("createvoucher_2901proc6_data>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			
			if(returnStr==null||returnStr.length()<3){
				if(returnStr!=null){
					returnStr=returnStr.trim();
				}else{
					returnStr="";
				}
				CallableStatement cs1 = conn.prepareCall("{call TL_ACCOUNT_PROC6_PKG.createvoucher_2901proc6_vou(?,?,?,?,?)}");
				cs1.registerOutParameter(5,java.sql.Types.CHAR);
				cs1.setString(1,inputStr1);
				cs1.setString(2,inputStr2);
				cs1.setString(3,inputStr3);
				cs1.setString(4,inputStr4);
				cs1.execute();
				returnStr=returnStr+cs1.getString(5);
				conn.commit();
				
				logger.info("createvoucher_2901proc6_vou>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				
			}else{
				conn.rollback();
				conn.setAutoCommit(true);
			}
			conn.setAutoCommit(true);
		}catch(Exception e){
			try{
				conn.rollback();
				conn.setAutoCommit(true);
			}catch(Exception es){
				
			}
			e.printStackTrace();
			returnStr="调用存储过程失败!"+e.toString();
			
			logger.error(e.toString());
			
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				returnStr="连接数据库失败!"+ex.toString();
				
				logger.error("连接数据库失败!");
				
			}
		}
		return returnStr;
	}
	
	/*****************************************************************************/
	/*************************************************************************
	用于调用6元挂帐生成应收实收凭证数据
	**************************************************************************/
	public String callTBYW(String policyno,String endorseno,String enddate,double chgpermium,String handlercode,String validate,int hour,String ptext){
		
		logger.info("callCreateGuaZhang>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		String returnStr="";
		java.sql.Connection conn=null;
		try{
			conn=this.getConnection();
			conn.setAutoCommit(false);
			
			CallableStatement cs = conn.prepareCall("{call TL_FELLBACK_C_CP.TL_FELLBACK_C_CP_EXCUTE(?,?,?,?,?,?)}");
            cs.setString(1, policyno);
            cs.setString(2, endorseno);
            cs.setString(3, enddate);
            cs.setString(4, enddate);
            cs.setDouble(5, chgpermium);
            cs.setInt(6, hour);
			cs.execute();
			
			logger.info("call TL_FELLBACK_C_CP.TL_FELLBACK_C_CP_EXCUTE");
			

			cs = conn.prepareCall("{call TL_FELLBACK_CP_P.TL_CP_P_EXCUTE(?,?,?,?,?,?,?)}");
			cs.setString(1,policyno);
			cs.setString(2,endorseno);
			cs.setString(3,enddate);
			cs.setDouble(4,chgpermium);
			cs.setString(5,handlercode);
			cs.setString(6,validate);
			cs.setInt(7,hour);
			cs.execute();
			
			logger.info("call TL_FELLBACK_C_CP.TL_FELLBACK_C_CP_EXCUTE");
			

			
			Statement stmt=conn.createStatement();
			String []ptextline=ptext.split("!");
			String insertSql="";
			String temp="                                                                              ";
			for(int i=0;i<ptextline.length;i++){
				int index=ptextline[i].length();
				
				int zj = 0;                                   
				for(int k = 0; k < ptextline[i].length(); k++)
			    if(ptextline[i].charAt(k) > '\377')       
			        zj++;
				
				logger.info(zj);
				
				
				String bustr = temp.substring(index + zj, temp.length());
				
				ptextline[i] = ptextline[i] + bustr + "\r\n";
				
				insertSql="";
				insertSql = "insert into prpptext (ENDORSENO,POLICYNO,LINENO,ENDORSETEXT) values(";
				insertSql = insertSql + "'" + endorseno + "','" + policyno + "'," + (i + 1) + ",'" + ptextline[i] + "')";
				stmt.addBatch(insertSql);
			}
	        
			stmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		}catch(Exception e){
			try{
				conn.rollback();
				conn.setAutoCommit(true);
			}catch(Exception es){
				
			}
			e.printStackTrace();
			returnStr="调用存储过程失败!"+e.toString();
			
			logger.error(e.toString());
			
		}finally{
			try{
				conn.close();
			}catch(Exception ex){
				returnStr="连接数据库失败!"+ex.toString();
				
				logger.error("连接数据库失败!");
				
			}
		}
		return returnStr;
	}	
}  
