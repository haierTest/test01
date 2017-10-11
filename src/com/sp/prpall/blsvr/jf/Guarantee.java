package com.sp.prpall.blsvr.jf;

import com.sp.utility.SysConfig;

import com.sp.utility.database.DbPool;

import java.sql.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Guarantee {

  protected final Log logger = LogFactory.getLog(getClass());
  DbPool db = null;
  public Guarantee(){
    db = new DbPool();
  }

  public static void main(){

  }

  public String build(String procName,String comCode, String riskCode){
    CallableStatement cstmt = null;
    Statement stmt = null;
    Connection conn = null;
    String returnStr = "";
    try{
      stmt= db.open(SysConfig.CONST_DDCCDATASOURCE);
      conn = stmt.getConnection();
      String procedure = "{call "+procName+"(?,?,?)}";
      cstmt = conn.prepareCall(procedure);
      
      logger.info("===========call procedure PKG_2901MAIN=procName="+procName+"=comCode="+comCode);
      
      cstmt.setString(1,comCode);
      cstmt.setString(2,riskCode);
      cstmt.registerOutParameter(3,1);
      cstmt.executeUpdate();
      returnStr = cstmt.getString(3);
    }catch(Exception e){
      e.printStackTrace();
      try{
        conn.rollback();
        cstmt.close();
        conn.close();
        stmt.close();
      }catch(SQLException e2){
        e2.printStackTrace();
      }

    }finally{
      try{
        cstmt.close();
        conn.close();
        stmt.close();
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
    return returnStr;
  }

}
