package com.sp.indiv.ci.interf;

import java.sql.ResultSet;
import com.sp.sysframework.common.util.StringUtils;
import com.sp.utility.StringConvertor;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;

public class InsursdDemandExportFS {

	public String encode(String comCode, String flag, String startDate, String endDate) throws Exception {
		String buf = "";
	    buf = query(comCode, flag, startDate, endDate);
		return buf.substring(0,buf.length()-2);
	}

	protected String query(String comCode, String flag, String startDate, String endDate) throws Exception {
		DbPool dbpool = new DbPool();
		ResultSet resultSet = null;
		StringBuffer strSQL = new StringBuffer();
		StringBuffer result = new StringBuffer();
		StringBuffer buf = new StringBuffer();
		
		String strSwitch = SysConfig.getProperty("DB_SPLIT_SWITCH");
		DbPool dbpoolNew = new DbPool();
		String strRule = "";
		try {
	        if ("1".equals(strSwitch)) {
	        	dbpoolNew.open("platformNewDataSource");            
	        } else {
	        	dbpoolNew.open("ggSunDBDataSource");
	        }
	        String strSql = "select rule from utiplatconfigrule where paramcode = 'CICARLICENSENO"+comCode.substring(0, 2)+"'";
	        ResultSet resultSetNew = dbpoolNew.query(strSql);
	        if (resultSetNew.next()) {
		        strRule = resultSetNew.getString("rule");
	        }
	        resultSetNew.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dbpoolNew.close();
		}
		
		try {
			dbpool.open(SysConfig.getProperty("ggSunDBDataSource"));
			if ("CI".equals(flag)) {
                strSQL.append("select v.validno as validno, ");
                strSQL.append("       m.policyno as policyno, ");
                strSQL.append("       to_char(m.inputdate, 'YYYY-MM-DD') as inputdate, ");
                strSQL.append("       case ");
                strSQL.append("         when m.immevalidflag = '1' then to_char(m.immevalidstartdate, 'YYYY-MM-DD HH') ");
                strSQL.append("         else to_char(m.startdate, 'YYYY-MM-DD') || ' 00' ");
                strSQL.append("       end as startdate, ");
                strSQL.append("       case ");
                strSQL.append("         when m.immevalidflag = '1' then to_char(m.immevalidenddate, 'YYYY-MM-DD HH') ");
                strSQL.append("         when m.endhour = '24' then to_char(m.enddate+1, 'YYYY-MM-DD') || ' 00' ");
                strSQL.append("         else to_char(m.enddate, 'YYYY-MM-DD') || ' 00' ");
                strSQL.append("       end as enddate, ");
                strSQL.append("       case ");
                strSQL.append("         when (select count(*) from prpphead where prpphead.policyno = m.policyno and prpphead.endortype = '71') > 0 then '1' ");
                strSQL.append("         else '0' ");
                strSQL.append("       end as changecarownerflag, ");
                strSQL.append("       c.licenseno as licenseno, ");
                strSQL.append("       c.engineno as engineno, ");
                strSQL.append("       c.frameno as frameno, ");
                strSQL.append("       to_char(c.enrolldate, 'YYYY-MM-DD') as enrolldate, ");
                strSQL.append("       case ");
                strSQL.append("         when c.licenseno = 'ĞÂ³µ' then '1' ");
                strSQL.append("         else '0' ");
                strSQL.append("       end as nolicenseflag, ");
                strSQL.append("       case ");
                strSQL.append("         when substr(c.licenseno,0,1)  = 'ĞÂ' then '0' ");
                strSQL.append("         when '"+comCode.substring(0, 2)+"' in ('09','11','29','31','37') then ");
                strSQL.append("           case");
        		
                
                strSQL.append("             when (select instr(('"+strRule+"'),substr(c.licenseno,0,2),1,1) from dual) > 0 then '0' ");
        		
                strSQL.append("             else '1' ");
                strSQL.append("           end ");
                strSQL.append("         when '"+comCode.substring(0, 2)+"' not in ('09','11','29','31','37') then ");
                strSQL.append("           case ");
        		
                
                strSQL.append("             when (select instr(('"+strRule+"'),substr(c.licenseno,0,1),1,1) from dual) > 0 then '0' ");
        		
                strSQL.append("             else '1' ");
                strSQL.append("           end ");
                strSQL.append("       end as outlandcarflag, ");
                strSQL.append("       case ");
                strSQL.append("         when c.newdeviceflag in ('N1', 'N2', 'N3', 'N6', 'N7', 'N8') then null ");
                strSQL.append("         when c.newdeviceflag = 'N4' then '1' ");
                strSQL.append("         when c.newdeviceflag = 'N5' then '2' ");
                strSQL.append("         when c.newdeviceflag = 'N9' then '3' ");
                strSQL.append("         when c.newdeviceflag = 'N10' then '4' ");
                strSQL.append("         when c.newdeviceflag = 'N11' then '5' ");
                strSQL.append("         when c.newdeviceflag = '1' then '1' ");
                strSQL.append("         else null ");
                strSQL.append("       end as specialcarflag ");
                strSQL.append("  from ciinsurevalid v, prpcmain m, prpcitemcar c ");
                strSQL.append(" where m.comcode like '"+comCode.substring(0, 2)+"%' ");
                strSQL.append("   and m.startdate >= to_date('"+startDate+"', 'YYYY-MM-DD') ");
                strSQL.append("   and m.startdate <= to_date('"+endDate+"', 'YYYY-MM-DD') ");
                strSQL.append("   and m.policyno = v.policyno ");
                strSQL.append("   and m.policyno = c.policyno ");
                strSQL.append("   and m.riskcode = '0507' ");
			}
			else if ("BI".equals(flag)) {
                strSQL.append("select v.validno as validno, ");
                strSQL.append("       m.policyno as policyno, ");
                strSQL.append("       to_char(m.inputdate, 'YYYY-MM-DD') as inputdate, ");
                strSQL.append("       case ");
                strSQL.append("         when m.immevalidflag = '1' then to_char(m.immevalidstartdate, 'YYYY-MM-DD HH:MM') ");
                strSQL.append("         else to_char(m.startdate, 'YYYY-MM-DD') || ' 00:00' ");
                strSQL.append("       end as startdate, ");
                strSQL.append("       case ");
                strSQL.append("         when m.immevalidflag = '1' then to_char(m.immevalidenddate, 'YYYY-MM-DD HH:MM') ");
                strSQL.append("         when m.endhour = '24' then to_char(m.enddate+1, 'YYYY-MM-DD') || ' 00:00' ");
                strSQL.append("         else to_char(m.enddate, 'YYYY-MM-DD') || ' 00:00' ");
                strSQL.append("       end as enddate, ");
                strSQL.append("       case ");
                strSQL.append("         when (select count(*) from prpphead where prpphead.policyno = m.policyno and prpphead.endortype = '71') > 0 then '1' ");
                strSQL.append("         else '0' ");
                strSQL.append("       end as changecarownerflag, ");
                strSQL.append("       c.licenseno as licenseno, ");
                strSQL.append("       c.engineno as engineno, ");
                strSQL.append("       c.frameno as frameno, ");
                strSQL.append("       to_char(c.enrolldate, 'YYYY-MM-DD') as enrolldate, ");
                strSQL.append("       c.nolicenseflag as nolicenseflag, ");
                strSQL.append("       c.newcarflag as newcarflag, ");
                strSQL.append("       c.chgownerflag as chgownerflag, ");
                strSQL.append("       c.outlandcarflag as outlandcarflag, ");
                strSQL.append("       c.loanvehicleflag as loanvehicleflag, ");
                strSQL.append("       case ");
                strSQL.append("         when m.contractno is null then '0' ");
                strSQL.append("         else '1' ");
                strSQL.append("       end as motorflag ");
                strSQL.append("  from ciinsurevalid v, prpcmain m, prpcitemcar c ");
                strSQL.append(" where m.comcode like '"+comCode.substring(0, 2)+"%' ");
                strSQL.append("   and m.startdate >= to_date('"+startDate+"', 'YYYY-MM-DD') ");
                strSQL.append("   and m.startdate <= to_date('"+endDate+"', 'YYYY-MM-DD') ");
                strSQL.append("   and m.policyno = v.policyno ");
                strSQL.append("   and m.policyno = c.policyno ");
                strSQL.append("   and m.riskcode in ('0508','0509') ");
			}
			resultSet = dbpool.query(strSQL.toString());
			while (resultSet.next()) {
			    result = new StringBuffer();
			    result.append(StringConvertor.changeNullToEmpty(resultSet.getString("validno")) + "\t");
			    result.append(StringConvertor.changeNullToEmpty(resultSet.getString("policyno")) + "\t");
			    result.append(StringConvertor.changeNullToEmpty(resultSet.getString("inputdate")) + "\t");
			    result.append(StringConvertor.changeNullToEmpty(resultSet.getString("startdate")) + "\t");
			    result.append(StringConvertor.changeNullToEmpty(resultSet.getString("enddate")) + "\t");
			    result.append(StringConvertor.changeNullToEmpty(resultSet.getString("changecarownerflag")) + "\t");
			    result.append(StringConvertor.changeNullToEmpty(resultSet.getString("licenseno")) + "\t");
			    result.append(StringConvertor.changeNullToEmpty(resultSet.getString("engineno")) + "\t");
			    result.append(StringConvertor.changeNullToEmpty(resultSet.getString("frameno")) + "\t");
			    result.append(StringConvertor.changeNullToEmpty(resultSet.getString("enrolldate")) + "\t");
			    result.append(StringConvertor.changeNullToEmpty(resultSet.getString("nolicenseflag")) + "\t");
			    if("CI".equals(flag)){
			        result.append(StringConvertor.changeNullToEmpty(resultSet.getString("outlandcarflag")) + "\t");
			        result.append(StringConvertor.changeNullToEmpty(resultSet.getString("specialcarflag")) + "\r\n");
			    }else if("BI".equals(flag)){
			        result.append(StringConvertor.changeNullToEmpty(resultSet.getString("newcarflag")) + "\t");
			        result.append(StringConvertor.changeNullToEmpty(resultSet.getString("chgownerflag")) + "\t");
			        result.append(StringConvertor.changeNullToEmpty(resultSet.getString("outlandcarflag")) + "\t");
			        result.append(StringConvertor.changeNullToEmpty(resultSet.getString("loanvehicleflag")) + "\t");
			        result.append(StringConvertor.changeNullToEmpty(resultSet.getString("motorflag")) + "\r\n");
			    }
			    buf.append(result.toString());
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			dbpool.close();
		}
		return buf.toString();
	}

	private String correctDate(String dateString) {
		String result = StringUtils.replace(dateString, "-", "");
		return result;
	}

	public String translateName(String endDate, String flag) throws Exception {
	    StringBuffer name = new StringBuffer();
	    if("CI".equals(flag)){
	        name.append("YGBXiainsuredata");
	        name.append(correctDate(endDate));
	        name.append("01");
	    }
	    else if("BI".equals(flag)){
	        name.append("YGBXcainsuredata");
            name.append(correctDate(endDate));
            name.append("02");
        }
		return name.toString();
	}
}
