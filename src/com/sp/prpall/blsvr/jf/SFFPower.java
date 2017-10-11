package com.sp.prpall.blsvr.jf;

import java.sql.SQLException;
import java.util.*;

import javax.naming.NamingException;

import com.sp.platform.bl.action.domain.*;
import com.sp.platform.dto.domain.*;
import com.sp.platform.resource.dtofactory.domain.*;
import com.sp.platform.ui.control.action.IConstants;
import com.sp.sysframework.common.Constants;
import com.sp.sysframework.common.util.*;
import com.sp.sysframework.reference.DBManager;
import com.sp.utility.SysConfig;
import com.sp.utility.database.DbPool;
import com.sp.platform.bl.facade.BLUtiPlatConfigFacade;

public class SFFPower {
	
	public static final String PERFECT_COM_TREE = "perfectComTree";

	private static final String DILIMITER = "^";
    
    /** 用于缓存权限 */
    
    
	/** 用于组织缓存的Key */
	private static final int CHECK_POWER = 1;

	private static final int GET_POWER = 2;

	private static final int ADD_POWER = 3;

	private static final int ADD_CODE_POWER = 4;

	private static final int ADD_CUSTOMER_POWER = 5;

	private static final int ADD_RISK_POWER = 6;

	/** 用户缓存所有超级用户 */
	private static Map superUsers = Collections.synchronizedMap(new HashMap());

	/**
	 * 判断值是否为空
	 * 
	 * @param value 传入值
	 * @return 如果传入值等于null或trim()后长度为0则返回true，否则返回false
	 */
	private static boolean isEmpty(String value) {
		if (value == null || value.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 是否是超级用户
	 * @param dbManager DBManager
	 * @param comCode 机构代码
	 * @param userCode 用户代码
	 * @return 是返回true,否则返回false
	 * @throws Exception
	 */
	public static boolean isSuperUser(DBManager dbManager, String comCode, String userCode)
			throws Exception {
		String key = comCode + DILIMITER + userCode;
		if (superUsers.containsKey(key)) {
			return true;
		}
		boolean topCompany = new BLPrpDcompanyAction().isTopCompany(dbManager, comCode);
		if (topCompany == true && userCode.equals(StringUtils.newString('0', userCode.length()))
				&& userCode.length() >= 8) {
			superUsers.put(key, "");
			return true;
		}
		return false;
	}

	/**
	 * 尾部偶数截零法处理机构代码
	 * @param comCode 机构代码
	 * @return 尾部偶数截零法处理后的机构代码
	 */
	private static String tailEvenTrim(String comCode) {
		if (comCode == null || comCode.trim().length() == 0) {
			return "";
		}
		while (comCode.endsWith("00")) {
			comCode = comCode.substring(0, comCode.length() - 2);
		}

		return comCode;

	}

	/**
	 * 获取机构代码和除外机构代码组织的SQL语句
	 * 
	 * @param dbManager 资源管理器
	 * @param fieldName 表名.字段名
	 * @param comCode 机构代码
	 * @param exceptComCode 除外机构代码
	 * @param perfectComTree 是否是完美的树 *
	 * @param queryComType 查询的机构方式 <br>
	 *            参数值如下：
	 *            <ul>
	 *            <li>
	 * @see com.sp.sysframework.common.Constants#SELF_COMPANY </li>
	 *      <li>
	 * @see com.sp.sysframework.common.Constants#SAME_COMPANY </li>
	 *      <li>
	 * @see com.sp.sysframework.common.Constants#SUB_COMPANY </li>
	 *      </ul>
	 * @return 简单的SQL语句，没有用括号包围，且没有AND、OR等前缀
	 * @throws Exception
	 */
	public String getComCodeSQL(DBManager dbManager, String fieldName, String comCode,
			String exceptComCode, boolean perfectComTree, int queryComType) throws Exception {
		String comCodeSQL = "";
		if (isEmpty(comCode)) {
			throw new IllegalArgumentException("(代码)权限机构代码不能为空");
		}
		
		if (exceptComCode.indexOf(comCode) > -1) {
			return "1=0";
		}
		BLPrpDcompanyAction blPrpDcompanyAction = new BLPrpDcompanyAction();
		if (perfectComTree) {
			
			if (blPrpDcompanyAction.isTopCompany(dbManager, comCode) == false) {
				comCodeSQL += "(";
				switch (queryComType) {
				case Constants.SELF_COMPANY:
					comCodeSQL += fieldName + " = '" + comCode + "'";
					break;
				case Constants.SAME_COMPANY:
					Collection companys = blPrpDcompanyAction.findByComCode(dbManager, comCode,
							exceptComCode, queryComType);
					comCodeSQL += fieldName + " IN (";
					for (Iterator iter = companys.iterator(); iter.hasNext();) {
						PrpDcompanyDto element = (PrpDcompanyDto) iter.next();
						comCodeSQL += "'" + element.getComCode() + "'";
						if (iter.hasNext()) {
							comCodeSQL += ",";
						}
					}
					comCodeSQL += ")";
					break;
				case Constants.SUB_COMPANY:
					String companyCode = tailEvenTrim(comCode);
					comCodeSQL += fieldName + " LIKE '" + companyCode + "%'";
					break;
				}
				comCodeSQL += ")";

			}
			String[] values = StringUtils.split(exceptComCode, ",");
			for (int i = 0; i < values.length; i++) {
				String exceptCompanyCode = tailEvenTrim(values[i]);
				
				if (exceptCompanyCode.length() == 0) {
					comCodeSQL += " AND (1 = 0)";
					break; 
				}
				
				if (exceptComCode.length() == exceptCompanyCode.length()) {
					comCodeSQL += " AND (" + fieldName + " != '" + exceptCompanyCode + "')";
				} else {
					comCodeSQL += " AND (" + fieldName + " NOT LIKE '" + exceptCompanyCode + "%')";
				}

			}
		} else {
			
			Collection companys = blPrpDcompanyAction.findByComCode(dbManager, comCode,
					exceptComCode, queryComType);
			if (companys.size() == 0) {
				comCodeSQL = "1=0";
			} else {
				comCodeSQL = fieldName + " IN (";
				for (Iterator iter = companys.iterator(); iter.hasNext();) {
					PrpDcompanyDto element = (PrpDcompanyDto) iter.next();
					comCodeSQL += "'" + element.getComCode() + "'";
					if (iter.hasNext()) {
						comCodeSQL += ",";
					}
				}
				comCodeSQL += ")";
			}
		}

		if (comCodeSQL.trim().toUpperCase().startsWith("AND")) {
			comCodeSQL = comCodeSQL.trim().substring("AND".length()).trim();
		}
		if (comCodeSQL.trim().length() == 0) {
			comCodeSQL = "1=1";
		}
		String[] table = StringUtils.split(fieldName, ".");
		if (table[0].toLowerCase().equals("prpdcustomeridv")
				|| table[0].toLowerCase().equals("prpdcustomerunit")) {
			
			Collection upperCompanys = blPrpDcompanyAction.findByComCode(dbManager, comCode,
					exceptComCode, Constants.UPPER_COMPANY);
			comCodeSQL += " OR (" + fieldName + " IN (";
			for (Iterator iter = upperCompanys.iterator(); iter.hasNext();) {
				PrpDcompanyDto element = (PrpDcompanyDto) iter.next();
				comCodeSQL += "'" + element.getComCode() + "'";
				if (iter.hasNext()) {
					comCodeSQL += ",";
				}
			}
			comCodeSQL += ") AND lowerViewFlag = '1'";
			comCodeSQL = "(" + comCodeSQL;
			comCodeSQL += " ))";

		}
		return comCodeSQL;

	}

	private String addPowerImpl(DBManager dbManager, String userCode, String comCode,
			String gradeCodes, String tableName, String userCodeFields, String comCodeFields,
			String riskCode, boolean hasRiskCode, int queryComType, int type) throws Exception {
		if (isEmpty(userCode)) {
			throw new IllegalArgumentException("参数\"员工代码\"没有值");
		}
		if (isEmpty(tableName)) {
			throw new IllegalArgumentException("参数\"表名\"没有值");
		}
		if (isEmpty(comCode) && isEmpty(gradeCodes)) {
			throw new IllegalArgumentException("参数\"登录机构\"和\"登录岗位列表\"必须有一个有值");
		}
		if (isEmpty(userCodeFields) && isEmpty(comCodeFields)) {
			throw new IllegalArgumentException("参数\"用户字段名称\"和\"机构字段名称\"必须有一个有值");
		}
		if (hasRiskCode && isEmpty(riskCode)) {
			throw new IllegalArgumentException("调用带XXXXX种的服务时参数\"XXXXX种代码\"必须有值");
		}
		
		if (isSuperUser(dbManager, comCode, userCode)) {
			return "";
		}
		if (userCodeFields.length() > 0) {
			userCodeFields += ",";
		}
		userCodeFields += "Handler1Code";
		
		String[] userCodeFieldsArray = StringUtils.split(userCodeFields, ",");
		Map fieldMap = new HashMap();
		for (int i = 0; i < userCodeFieldsArray.length; i++) {
			if (dbManager.hasColumn(tableName, userCodeFieldsArray[i])) {
				fieldMap.put(userCodeFieldsArray[i].toLowerCase(), userCodeFieldsArray[i]);
			}
		}
		userCodeFieldsArray = new String[fieldMap.size()];
		fieldMap.values().toArray(userCodeFieldsArray);

		
		String[] comCodeFieldsArray = StringUtils.split(comCodeFields, ",");

		StringBuffer buffer = new StringBuffer();
		boolean perfectComTree = true; 
		
		UtiPlatConfigDto utiPlatConfigDto = new BLUtiPlatConfigFacade().findByPrimaryKey("platform", PERFECT_COM_TREE);
		
		if (utiPlatConfigDto != null) {
			perfectComTree = DataUtils.getBoolean(utiPlatConfigDto.getParamValue());
		}

		boolean tableHasRiskCode = true;
		
		if (hasRiskCode == false) {
			tableHasRiskCode = dbManager.hasColumn(tableName, "RiskCode");
		}
		
		String conditions = "UserCode = '" + userCode + "'";
		conditions += SqlUtils.convertString("ComCode", comCode);
		conditions += SqlUtils.convertString("GradeCode", gradeCodes);
		DBUtiUserGrade dbUtiUserGrade = new DBUtiUserGrade(dbManager);
		DBUtiUserGradePower dbUtiUserGradePower = new DBUtiUserGradePower(dbManager);
		Collection utiUserGrades = dbUtiUserGrade.findByConditions(conditions);
		
		for (Iterator iter = utiUserGrades.iterator(); iter.hasNext();) {
			UtiUserGradeDto utiUserGradeDto = (UtiUserGradeDto) iter.next();
			




			conditions = "UserCode = ? AND ComCode = ? AND GradeCode = ?";
			ArrayList arrWhereValue = new ArrayList();
			arrWhereValue.add(userCode);
			arrWhereValue.add(utiUserGradeDto.getComCode());
			arrWhereValue.add(utiUserGradeDto.getGradeCode());
			Collection utiUserGradePowers = dbUtiUserGradePower.findByConditions(conditions,arrWhereValue);
			
			
			for (Iterator iterator = utiUserGradePowers.iterator(); iterator.hasNext();) {
				UtiUserGradePowerDto utiUserGradePowerDto = (UtiUserGradePowerDto) iterator.next();
				String recordSQL = "";
				String permitRiskCode = utiUserGradePowerDto.getPermitRiskCode();
				
				if (!permitRiskCode.equals("*")) {
					
					if (hasRiskCode) {
						
						if (!(permitRiskCode.endsWith(riskCode) || permitRiskCode.indexOf(riskCode
								+ ",") > -1)) {
							continue;
						}
					} else {
						if (tableHasRiskCode) { 
							recordSQL += SqlUtils.convertString(tableName + ".RiskCode",
									utiUserGradePowerDto.getPermitRiskCode());
						}
					}
				}

				
				

				String comCodeSQL = "";
				for (int i = 0; i < comCodeFieldsArray.length; i++) {
					String field = comCodeFieldsArray[i];
					String permitComCode = "";
					String exceptComCodes = "";

					switch (type) {
					case ADD_CODE_POWER:
						
						permitComCode = utiUserGradePowerDto.getCodePermitComCode();
						exceptComCodes = utiUserGradePowerDto.getCodeExceptComCode();
						if (permitComCode.trim().length() == 0) {
							permitComCode = utiUserGradePowerDto.getPermitComCode();
						}
						break;
					case ADD_CUSTOMER_POWER:
						
						permitComCode = utiUserGradePowerDto.getCustomerPermitComCode();
						exceptComCodes = utiUserGradePowerDto.getCustomerExceptComCode();
						if (permitComCode.trim().length() == 0) {
							permitComCode = utiUserGradePowerDto.getPermitComCode();
						}
						break;
					default:
						permitComCode = utiUserGradePowerDto.getPermitComCode();
						exceptComCodes = utiUserGradePowerDto.getExceptComCode();
						break;
					}
					String comCodeFieldSQL = getComCodeSQL(dbManager, tableName + "." + field,
							permitComCode, exceptComCodes, perfectComTree, queryComType);
					if (i == 0) {
						comCodeSQL += "(" + comCodeFieldSQL + ")";
					} else {
						comCodeSQL += " OR (" + comCodeFieldSQL + ")";
					}
				}
				

				
				String userCodeSQL = "";
				for (int i = 0; i < userCodeFieldsArray.length; i++) {
					String field = userCodeFieldsArray[i];
					String userCodeFieldSQL = "";
					
					String[] permitUserCodes = StringUtils.split(utiUserGradePowerDto
							.getPermitUserCode(), ",");
					if(permitUserCodes.length==0){
						break;
					}
					for (int j = 0; j < permitUserCodes.length; j++) {
						String permitUserCode = permitUserCodes[j];
						if (j == 0) {
							userCodeFieldSQL += " (" + tableName + "." + field + " = '" + permitUserCode + "')";
						} else {
							userCodeFieldSQL += " OR (" + tableName + "." + field + " = '" + permitUserCode + "')";
						}
					}
					
					if (i == 0) {
						userCodeSQL += " OR (" + userCodeFieldSQL + ")";
					} else {
						userCodeSQL += " OR (" + userCodeFieldSQL + ")";
					}
				}
				if (userCodeSQL.trim().toUpperCase().startsWith("OR")) {
					userCodeSQL = userCodeSQL.trim().substring("OR".length()).trim();
				}
				if (!isEmpty(comCodeSQL) && !isEmpty(userCodeSQL)) {
					recordSQL += " AND ((" + comCodeSQL + ") OR (" + userCodeSQL + "))";
				} else if (!isEmpty(comCodeSQL)) {
					recordSQL += " AND (" + comCodeSQL + ")";
				} else if (!isEmpty(userCodeSQL)) {
					recordSQL += " AND (" + userCodeSQL + ")";
				}

				
				if (recordSQL.trim().length() > 0) {
					if (recordSQL.trim().toUpperCase().startsWith("AND")) {
						recordSQL = recordSQL.trim().substring("AND".length()).trim();
					}
					
					if (buffer.length() == 0) {
						buffer.append("(");
						buffer.append(recordSQL);
						buffer.append(")");
					} else {
						
						if (buffer.indexOf(recordSQL) == -1) {
							buffer.append(" OR (");
							buffer.append(recordSQL);
							buffer.append(")");
						}
					}
					recordSQL = "";
				}
			}

		}

		
		if (buffer.length() == 0) {
			String message = "不存在业务权限，无权执行此操作! <br>参数为：";
			message += "员工代码='" + userCode + "',";
			message += "登录机构代码='" + comCode + "',";
			message += "登录岗位代码='" + gradeCodes + "'";
			throw new IllegalAccessException(message);
		}
		
		buffer.insert(0, " AND ("); 
		buffer.append(")"); 
		return buffer.toString();
	}

	/**
	 * 业务权限的获取 与XXXXX种无关
	 * 
	 * @param dbManager DBManager
	 * @param userCode 员工代码
	 * @param comCode 登录机构
	 * @param gradeCodes 登录岗位列表（逗号分割)
	 * @param tableName 表名
	 * @param userCodeFields 用户字段名称（逗号分割)
	 * @param comCodeFields 机构字段名称（逗号分割)
	 * @param queryComType 查询的机构方式 <br>
	 *            参数值如下：
	 *            <ul>
	 *            <li>
	 * @see com.sp.sysframework.common.Constants#SELF_COMPANY </li>
	 *      <li>
	 * @see com.sp.sysframework.common.Constants#SAME_COMPANY </li>
	 *      <li>
	 * @see com.sp.sysframework.common.Constants#SUB_COMPANY </li>
	 *      </ul>
	 * @return 返回附加的权限语句
	 * 
	 * @throws Exception
	 */
	public String addPower(PrpDuserDto user, DBManager dbManager, String tableName,
			String userCodeFields, String comCodeFields, int queryComType) throws Exception {
		String userCode = user.getUserCode();
		String comCode = user.getLoginComCode();
		String gradeCodes = user.getLoginGradeCodes();
		String riskCode = user.getCurrentRiskCode();
		if (isEmpty(riskCode)) {
			throw new IllegalArgumentException("参数\"XXXXX种代码\"没有值");
		}
        String value = "";
        
        








            
            value = addPowerImpl(dbManager, userCode, comCode, gradeCodes, tableName,
                    userCodeFields, comCodeFields, riskCode, true, queryComType, ADD_POWER);



        
        return value;
    }

	/**
	 * 根据外部传入的员工代码+任务代码+权限组号获取查询条件附加权限(查询方式为本机构及同级机构及下级机构)
     * @param user PrpDuserDto
     * @param tableName 参数tableName
     * @param userCodeFields 参数userCodeFields
     * @param comCodeFields 参数comCodeFields
     * @throws Exception
     * @return String
	 */
	public String addPower(PrpDuserDto user, String tableName, String userCodeFields,
			String comCodeFields) throws Exception, SQLException, NamingException {
		DbPool dbpool = new DbPool();
		String value = "";
		
		try {
			dbpool.open(SysConfig.CONST_DDCCDATASOURCE);
			DBManager dbManager = dbpool.getDBManager(SysConfig.CONST_DDCCDATASOURCE);
			value = addPower(user, dbManager, tableName, userCodeFields, comCodeFields,
					IConstants.SUB_COMPANY);
		} catch (SQLException sqlException) {
			throw sqlException;
		} catch (NamingException namingException) {
			throw namingException;
		} finally {
			dbpool.close();
		}
		return value;
	}
}
