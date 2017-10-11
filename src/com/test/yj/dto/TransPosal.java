package com.test.yj.dto;


import java.util.*;
import com.sp.prpall.schema.*;
import com.sp.prpall.dbsvr.tb.*;
import com.sp.utiall.dbsvr.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import com.sp.utiall.schema.*;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.utility.error.UserException;
import com.sp.undwrt.bl.facade.BLTaskFacade;
import com.test.euse.*;
import java.text.*;


public class TransPosal{
	public ArrayList getPosal(String sqlwhere){
		DB db = DB.getDB();
		String sql="select ProposalNo,RiskCode,ClassCode,ComCode,MakeCom,HandlerCode,Handler1Code from prptmain where ";
		ArrayList list =db.queryAgtManager(sql+sqlwhere);
		return list;
	}
}