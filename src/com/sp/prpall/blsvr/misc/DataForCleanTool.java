package com.sp.prpall.blsvr.misc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.sp.utiall.dbsvr.DBPrpdExpiresDataForClean;
import com.sp.utility.SysConfig;
import com.sp.utility.SysConst;
import com.sp.utility.database.DbPool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DataForCleanTool {

	protected final Log logger = LogFactory.getLog(getClass());
    /**
     * @param args
     */
    public static void main(String[] args) {
        

    }

    public String dataForClean(String path)throws Exception{
    	
    	logger.info("进入洗清");
    	
        File in = new File(path);
        BufferedReader rd = new BufferedReader(new FileReader(in));
        String itemtemp="";
        String fengeFlag="\\|";
        DbPool dbpool = new DbPool();
        String[] record = new String[5];
    	String strSQL = " Update PrpdExpiresDataForClean Set ValidFlag = ? Where PolicyNo = ?" ;
        String returnFlag="";
    	int lineno = 1; 
        try{
			
			
			if ("1".equals(SysConfig.getProperty("PLAT_FORM_NEW_DATA_SOURCE"))) {
				dbpool.open(SysConfig.CONST_PLATFORMNEWDATASOURCE);
			} else {
				dbpool.open(SysConst.getProperty("DDCCDATASOURCE"));
			}
			
			dbpool.beginTransaction();
            while(true){
                itemtemp=rd.readLine();
                if(itemtemp==null || itemtemp.trim().equals(""))
                break;
                record=itemtemp.split(fengeFlag);
                if(record.length==6)
                {
                 if(record[5]!=null&&record[5].equals("1")){
                	dbpool.prepareInnerStatement(strSQL);
            		dbpool.setString(1, record[5]);
            		dbpool.setString(2, record[0]);
            		dbpool.executePreparedUpdate();
                  }
                }else{
                	returnFlag="出现错误的行数为:"+lineno;
                	dbpool.rollbackTransaction();
                	return returnFlag;
                }
                lineno++;
            }
            dbpool.closePreparedStatement();
            dbpool.commitTransaction();
        }
        catch(Exception e){
            dbpool.rollbackTransaction();
            throw e;
        }
        finally{
            dbpool.close();
        }
        return returnFlag;
    }
    
    
}
